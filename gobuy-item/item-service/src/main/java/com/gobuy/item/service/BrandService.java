package com.gobuy.item.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gobuy.common.pojo.PageResult;
import com.gobuy.item.mapper.BrandMapper;
import com.gobuy.item.mapper.SpuMapper;
import com.gobuy.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    private final BrandMapper brandMapper;
    private final SpuMapper spuMapper;

    public BrandService(BrandMapper brandMapper, SpuMapper spuMapper) {
        this.brandMapper = brandMapper;
        this.spuMapper = spuMapper;
    }


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

    // 查询brands
    public List<Brand> queryBrand(Integer cid) {
        return brandMapper.queryBrandByCategory(cid);
    }

    // 根据id查询brand
    public Brand queryBrandById(Integer bid) {
        return brandMapper.selectByPrimaryKey(bid);
    }

    // 查询brands
    public List<Brand> queryBrandsByIds(List<Integer> ids) {
        return brandMapper.selectByIdList(ids);
    }


    //保存Brand
    @Transactional
    public Boolean saveBrand(Brand brand, List<Integer> cids) {
        if (brandMapper.insertSelective(brand) != 1)
            return false;

        for (Integer cid : cids)
            brandMapper.insertCategoryAndBrand(cid, brand.getId());
        return true;
    }

    // 更新brand
    @Transactional
    public Boolean updateBrand(Brand brand, List<Integer> cids) {
        // 先更新brand信息
        brandMapper.updateByPrimaryKeySelective(brand);
        // 查询原先 牌子的类目
        List<Integer> oldCategory = brandMapper.queryCategory(brand.getId());

        // 删掉新 类目中没有的
        for (Integer cate : oldCategory) {
            if (!cids.contains(cate))
                brandMapper.deleteCategory(cate, brand.getId());
        }

        // 取新-旧的差集
        cids.removeAll(oldCategory);

        // 添加进数据库
        for (Integer cid : cids)
            brandMapper.insertCategoryAndBrand(cid, brand.getId());

        return true;
    }

    // 删除单个brand
    @Transactional
    public Boolean deleteBrand(Integer bid) {
        // 删除brand
        brandMapper.deleteByPrimaryKey(bid);
        // 删除关联的category
        brandMapper.deleteByBrand(bid);
        // 删除spu中的brand信息
        spuMapper.removeBrand(bid);
        return true;
    }

    // 删除多个brand
    @Transactional
    public Boolean deleteBrands(String[] bids) {
        for (String bid : bids) {
            // 查询category
            brandMapper.deleteByBrand(Integer.valueOf(bid));
            brandMapper.deleteByPrimaryKey(bid);
            // 删除spu中的brand信息
            spuMapper.removeBrand(Integer.valueOf(bid));
        }
        return true;
    }


}// end

