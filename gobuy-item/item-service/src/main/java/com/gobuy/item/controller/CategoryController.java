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

    @GetMapping("names")
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids") List<Integer> ids) {
        List<String> list = categoryService.queryNameByIds(ids);
        if (list == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(list);
    }

    @GetMapping("bid/{id}")
    public ResponseEntity<List<Category>> queryByBrand(@PathVariable("id") Integer id) {
        List<Category> categories = categoryService.queryByBrandId(id);
        if (categories == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(categories);
    }


    @PostMapping
    public ResponseEntity<Integer> addCategory(@RequestBody Category category) {
        Integer id = categoryService.add(category);
        return ResponseEntity.ok(id);
    }


    @PutMapping
    public ResponseEntity<Boolean> editCategory(@RequestBody Category category) {
        Boolean bool = categoryService.edit(category);
        if (bool != null && bool)
            return ResponseEntity.ok(true);
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("sort")
    public ResponseEntity<Boolean> sortCategory(@RequestBody List<Category> list) {
        Boolean bool = categoryService.sort(list);
        if (bool != null && bool)
            return ResponseEntity.ok(true);
        return ResponseEntity.badRequest().build();
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        if (id == null)
            return ResponseEntity.badRequest().build();

        Boolean bool = categoryService.delete(id);
        return ResponseEntity.ok(bool);
    }

}// end
