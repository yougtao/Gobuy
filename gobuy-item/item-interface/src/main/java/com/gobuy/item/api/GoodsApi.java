package com.gobuy.item.api;


import com.gobuy.common.pojo.PageResult;
import com.gobuy.item.pojo.Sku;
import com.gobuy.item.pojo.SpuBo;
import com.gobuy.item.pojo.SpuDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("goods")
public interface GoodsApi {
    /**
     * 分页查询商品
     */
    @GetMapping("spu/page")
    PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", defaultValue = "true") Boolean saleable,
            @RequestParam(value = "key", required = false) String key);

    /**
     * 根据spu商品id查询详情
     */
    @GetMapping("spu/detail/{id}")
    SpuDetail querySpuDetailById(@PathVariable("id") Integer id);

    /**
     * 根据spu的id查询skus
     */
    @GetMapping("sku/list")
    List<Sku> querySkuBySpuId(@RequestParam("id") Integer id);

    // 根据sku id查询sku
    @GetMapping("sku/{id}")
    Sku querySku(@PathVariable("id") Long id);
}
