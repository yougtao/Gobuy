package com.gobuy.item.service;


import com.gobuy.item.mapper.CategoryMapper;
import com.gobuy.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return categoryMapper.deleteByPrimaryKey(id) == 1;

    }

    /*
     * 根据品牌ID查询
     * */
    public List<Category> queryByBrandId(Integer id) {
        return categoryMapper.queryByBrandId(id);
    }


    // 根据ids查询所属category name
    public List<String> queryNameByIds(List<Integer> ids) {
        List<String> lists = new ArrayList<>();
        for (Integer id : ids)
            lists.add(categoryMapper.queryName(id));
        return lists;
    }
}
