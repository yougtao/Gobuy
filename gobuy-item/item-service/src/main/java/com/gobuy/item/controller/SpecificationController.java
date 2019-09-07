package com.gobuy.item.controller;


import com.gobuy.item.pojo.Specification;
import com.gobuy.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    SpecificationService specificationService;


    @GetMapping("{id}")
    public ResponseEntity<String> query(@PathVariable("id") Integer specificationId) {
        Specification record = specificationService.query(specificationId);
        if (record == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(record.getSpecifications());
    }


    /*
     * 修改
     * */
    @PutMapping
    public ResponseEntity<Boolean> edit(@RequestParam("categoryId") Integer cid, @RequestParam("specifications") String specifications) {
        Boolean bool = specificationService.save(cid, specifications);
        return ResponseEntity.ok(bool);
    }


    /*
     * 添加
     * */
    @PostMapping
    public ResponseEntity<Boolean> add(@RequestParam("categoryId") Integer cid, @RequestParam("specifications") String specifications) {
        Boolean bool = specificationService.insert(cid, specifications);
        return ResponseEntity.ok(bool);
    }

}
