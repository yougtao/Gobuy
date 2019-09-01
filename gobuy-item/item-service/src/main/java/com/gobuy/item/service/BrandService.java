package com.gobuy.item.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gobuy.common.pojo.PageResult;
import com.gobuy.item.mapper.BrandMapper;
import com.gobuy.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    BrandMapper brandMapper;

    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        // 分页
        PageHelper.startPage(page, rows);

        // 过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }

        // 排序
        if (StringUtils.isNotBlank(sortBy)) {
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        // 查询
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);
        return new PageResult<>(pageInfo);
    }


    /*
     * 保存Brand
     * */
    @Transactional
    public Boolean saveBrand(Brand brand, List<Long> cids) {
        if (brandMapper.insertSelective(brand) != 1)
            return false;

        for (Long cid : cids)
            brandMapper.insertCategoryAndBrand(cid, brand.getId());
        return true;
    }

}
