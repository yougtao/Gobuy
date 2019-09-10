package com.gobuy.item.controller;


import com.gobuy.common.pojo.PageResult;
import com.gobuy.item.pojo.Brand;
import com.gobuy.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandService brandService;


    /*
     * 查询品牌
     * */
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

    /*
     * 根据category查询品牌信息
     * */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable("cid") Integer cid) {
        List<Brand> list = brandService.queryBrand(cid);
        if (list == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(list);
    }

    /*
     * 根据ids查询brands
     * */
    @RequestMapping("list")
    public ResponseEntity<List<Brand>> queryBrandsByIds(@RequestParam("ids") List<Integer> ids) {
        List<Brand> list = brandService.queryBrandsByIds(ids);
        if (list == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(list);
    }


    /*
     * 根据id查询brand
     * */
    @GetMapping("{bid}")
    public ResponseEntity<Brand> queryBrand(@PathVariable("bid") Integer bid) {
        Brand brand = brandService.queryBrandById(bid);
        if (brand == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(brand);
    }

    /*
     * 添加品牌
     * */
    @PostMapping
    public ResponseEntity<Boolean> addBrand(Brand brand, @RequestParam("categories") List<Integer> cids) {
        Boolean bool = brandService.saveBrand(brand, cids);
        return ResponseEntity.ok(bool);
    }


    /*
     * 编辑保存品牌
     * */
    @PutMapping
    public ResponseEntity<Boolean> editBrand(Brand brand, @RequestParam("categories") List<Integer> cids) {
        Boolean bool = brandService.updateBrand(brand, cids);
        return ResponseEntity.ok(bool);
    }


    /*
     * 删除品牌
     * */
    @DeleteMapping("bid/{ids}")
    public ResponseEntity<Boolean> deleteBrand(@PathVariable("ids") String ids) {
        String[] strings = ids.split("-");
        Boolean bool = brandService.deleteBrand(strings);
        return ResponseEntity.ok(bool);
    }

}// end
