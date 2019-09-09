package com.gobuy.item.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("spec")
public interface SpecificationApi {
    /**
     * 查询商品分类对应的规格参数模板
     */
    @GetMapping("{id}")
    ResponseEntity<String> querySpecificationByCategoryId(@PathVariable("id") Integer id);
}
