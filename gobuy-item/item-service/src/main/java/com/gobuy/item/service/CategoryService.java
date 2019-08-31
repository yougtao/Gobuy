package com.gobuy.item.service;


import com.gobuy.item.mapper.CategoryMapper;
import com.gobuy.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    public List<Category> queryByParentId(Integer id) {
        Category category = new Category();
        category.setParentId(id);

        return categoryMapper.select(category);
    }

    public Boolean delete(int id) {
        Category category = new Category();
        int i = categoryMapper.delete(category);

        return i == 1;
    }

}
