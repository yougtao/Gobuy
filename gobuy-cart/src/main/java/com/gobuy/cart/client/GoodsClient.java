package com.gobuy.cart.client;

import com.gobuy.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi {
}
