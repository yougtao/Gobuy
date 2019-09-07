package com.gobuy.item.controller;


import com.gobuy.common.pojo.PageResult;
import com.gobuy.item.pojo.SpuBo;
import com.gobuy.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/*
 * 商品相关的业务
 * */
@Controller
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", defaultValue = "true") Boolean saleable) {

        PageResult<SpuBo> result = goodsService.querySpuByPage(page, rows, sortBy, desc, key, saleable);
        if (result == null)
            return ResponseEntity.badRequest().build();
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

    /*
     * 修改spu
     * */
    @PutMapping
    public ResponseEntity<Boolean> editSpu(SpuBo spuBo) {
        Boolean bool = goodsService.edit(spuBo);
        if (bool == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(bool);
    }
}
