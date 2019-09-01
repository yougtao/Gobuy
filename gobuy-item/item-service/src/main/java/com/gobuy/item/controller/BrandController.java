package com.gobuy.item.controller;


import com.gobuy.common.pojo.PageResult;
import com.gobuy.item.pojo.Brand;
import com.gobuy.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key) {
        PageResult<Brand> result = brandService.queryBrandByPage(page, rows, sortBy, desc, key);
        if (result == null || result.getItems().size() == 0)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Boolean> addBrand(Brand brand, @RequestParam("categories") List<Long> cids) {
        Boolean bool = brandService.saveBrand(brand, cids);
        return ResponseEntity.ok(bool);
    }

}// end
