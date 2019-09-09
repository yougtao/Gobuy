package com.gobuy.search.client;


import com.gobuy.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "item-service")
public interface BrandClient extends BrandApi {

}
