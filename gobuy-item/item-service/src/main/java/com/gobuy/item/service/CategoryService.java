package com.gobuy.item.service;


import com.gobuy.item.mapper.CategoryMapper;
import com.gobuy.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    // 查询子分类
    public List<Category> queryByParentId(Integer id) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("parentId", id);
        example.setOrderByClause("sort");

        return categoryMapper.selectByExample(example);
    }

    // 根据品牌ID查询
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


    // 增加category
    public Integer add(Category category) {
        category.setId(null);
        if (categoryMapper.insert(category) == 1)
            return category.getId();
        return null;
    }

    // 修改name
    public Boolean edit(Category category) {
        return categoryMapper.updateByPrimaryKeySelective(category) == 1;
    }

    // 排序
    @Transactional
    public Boolean sort(List<Category> list) {
        list.forEach(category -> categoryMapper.updateByPrimaryKeySelective(category));
        return true;
    }

    public Boolean delete(int id) {
        return categoryMapper.deleteByPrimaryKey(id) == 1;
    }


}// end
