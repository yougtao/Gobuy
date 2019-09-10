package com.gobuy.item.controller;


import com.gobuy.common.pojo.PageResult;
import com.gobuy.item.pojo.Sku;
import com.gobuy.item.pojo.SpuBo;
import com.gobuy.item.pojo.SpuDetail;
import com.gobuy.item.service.CategoryService;
import com.gobuy.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * 商品相关的业务
 * */
@Controller
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                                            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy, @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
                                                            @RequestParam(value = "key", required = false) String key, @RequestParam(value = "saleable", defaultValue = "true") Boolean saleable) {
        PageResult<SpuBo> result = goodsService.querySpuByPage(page, rows, sortBy, desc, key, saleable);
        if (result == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(result);
    }

    @GetMapping("spu/{id}")
    public ResponseEntity<SpuBo> querySpu(@PathVariable("id") Integer id) {
        SpuBo result = goodsService.querySpu(id);
        if (result == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(result);
    }

    @GetMapping("spu/detail/{id}")
    public ResponseEntity<SpuDetail> querySpuDetailById(@PathVariable("id") Integer id) {
        SpuDetail spuDetail = goodsService.querySpuDetail(id);
        if (spuDetail == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(spuDetail);
    }

    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Integer id) {
        List<Sku> skuList = goodsService.querySkuBySpuId(id);
        if (skuList == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(skuList);
    }


    /*
     * 添加商品spu
     * */
    @PostMapping
    public ResponseEntity<Boolean> addGoods(@RequestBody SpuBo spuBo) {
        Boolean bool = goodsService.addGoods(spuBo);
        if (bool == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(bool);
    }


    /*
     * 修改商品信息spu
     * */
    @PutMapping
    public ResponseEntity<Boolean> editSpu(SpuBo spuBo) {
        Boolean bool = goodsService.edit(spuBo);
        if (bool == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(bool);
    }

    // 上架 下架goods
    @PutMapping("shelf/{id}")
    public ResponseEntity<Boolean> soldGoods(@PathVariable("id") Integer id) {
        Boolean bool = goodsService.shelf(id);
        if (bool == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(bool);
    }


    /*
     * 删除单个商品spu
     * */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteGoods(@PathVariable("id") Integer id) {
        Boolean bool = goodsService.delete(id);
        if (bool == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(bool);
    }


}// end
