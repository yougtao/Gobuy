package com.gobuy.item.controller;


import com.gobuy.item.pojo.Category;
import com.gobuy.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> query(@RequestParam(value = "pid", defaultValue = "0") Integer id) {

        List<Category> list = categoryService.queryByParentId(id);
        if (list == null || list.size() < 1)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(list);
    }


    @DeleteMapping("cid/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        if (id == null)
            return ResponseEntity.badRequest().build();

        Boolean bool = categoryService.delete(id);
        return ResponseEntity.ok(bool);
    }

}
