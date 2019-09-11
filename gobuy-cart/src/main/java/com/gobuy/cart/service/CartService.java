package com.gobuy.cart.service;


import com.gobuy.auth.entity.UserInfo;
import com.gobuy.cart.client.GoodsClient;
import com.gobuy.cart.interceptor.LoginInterceptor;
import com.gobuy.cart.pojo.Cart;
import com.gobuy.common.utils.JsonUtils;
import com.gobuy.item.pojo.Sku;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final StringRedisTemplate redisTemplate;
    private final GoodsClient goodsClient;


    private static final String KEY_PREFIX = "gobuy-cart:uid:";     // redis 中的key前缀
    static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public CartService(StringRedisTemplate redisTemplate, GoodsClient goodsClient) {
        this.redisTemplate = redisTemplate;
        this.goodsClient = goodsClient;
    }


    // 添加Cart
    public Boolean addCart(Cart cart) {
        UserInfo user = LoginInterceptor.getLoginUser();

        String key = KEY_PREFIX + user.getId();
        BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(key);

        // 查询是否存在
        Long skuId = cart.getSkuId();
        Integer num = cart.getNum();
        Boolean boo = hashOperations.hasKey(skuId.toString());
        if (boo != null && boo) {
            String json = (String) hashOperations.get(skuId.toString());
            cart = JsonUtils.parse(json, cart.getClass());

            // 修改数量
            cart.setNum(cart.getNum() + num);
        } else {
            cart.setUserId(user.getId());
            Sku sku = goodsClient.querySku(skuId);
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            cart.setPrice(sku.getPrice());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
        }

        hashOperations.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));

        return true;
    }

    public List<Cart> queryCarts() {
        UserInfo user = LoginInterceptor.getLoginUser();

        String key = KEY_PREFIX + user.getId();

        if (!redisTemplate.hasKey(key))
            return null;

        BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(key);
        List<Object> carts = hashOperations.values();
        if (CollectionUtils.isEmpty(carts))
            return null;

        // 转换购物车数据格式
        return carts.stream().map(o -> JsonUtils.parse(o.toString(), Cart.class)).collect(Collectors.toList());
    }


    public Boolean updateCartNum(Long skuId, Integer num) {
        UserInfo user = LoginInterceptor.getLoginUser();

        String key = KEY_PREFIX + user.getId();
        BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(key);

        String json = (String) hashOperations.get(skuId.toString());
        Cart cart = JsonUtils.parse(json, Cart.class);
        cart.setNum(num);
        hashOperations.put(skuId.toString(), JsonUtils.serialize(cart));
        return true;
    }

    // 删除购物车商品
    public Boolean deleteCart(Long id) {
        UserInfo user = LoginInterceptor.getLoginUser();

        String key = KEY_PREFIX + user.getId();
        BoundHashOperations<String, Object, Object> hashOperations = redisTemplate.boundHashOps(key);
        hashOperations.delete(String.valueOf(id));
        return true;
    }

}// end
