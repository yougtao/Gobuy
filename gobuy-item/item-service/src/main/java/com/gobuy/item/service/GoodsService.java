package com.gobuy.item.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gobuy.common.pojo.PageResult;
import com.gobuy.item.mapper.BrandMapper;
import com.gobuy.item.mapper.SkuMapper;
import com.gobuy.item.mapper.SpuDetailMapper;
import com.gobuy.item.mapper.SpuMapper;
import com.gobuy.item.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;


    // 查询spu
    public PageResult<SpuBo> querySpuByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key, Boolean saleable) {
        // 设置分页
        PageHelper.startPage(page, Math.min(rows, 100));

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();

        // 过滤上下架
        criteria.orEqualTo("saleable", saleable);

        // 模糊查询
        if (StringUtils.isNotBlank(key))
            criteria.andLike("title", "%" + key + "%").orLike("sub_title", "%" + key + "%");

        Page<Spu> pageInfo = (Page<Spu>) spuMapper.selectByExample(example);

        List<SpuBo> list = pageInfo.getResult().stream().map(spu -> {
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu, spuBo);

            List<String> names = categoryService.queryNameByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(names, "/"));

            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand == null ? "" : brand.getName());
            return spuBo;
        }).collect(Collectors.toList());
        return new PageResult<>(pageInfo.getTotal(), list);
    }


    public SpuBo querySpu(Integer id) {
        SpuBo spuBo = new SpuBo();

        // 先查询基本spu信息
        Spu spu = spuMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(spu, spuBo);

        // 查询category
        List<String> names = categoryService.queryNameByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        spuBo.setCname(StringUtils.join(names, "/"));

        // 查询brand
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
        spuBo.setBname(brand == null ? "" : brand.getName());

        // 查询sku列表
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId", id);
        List<Sku> skuList = skuMapper.selectByExample(example);
        spuBo.setSkus(skuList);

        // 查询detail
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(id);
        spuBo.setSpuDetail(spuDetail);

        return spuBo;
    }

    // 编辑spu
    public Boolean edit(SpuBo spuBo) {
        Spu spu = new Spu();
        BeanUtils.copyProperties(spuBo, spu);

        // 保存spu
        spuMapper.updateByPrimaryKeySelective(spu);

        // 保存sku信息


        // 保存spuDetail
        return null;
    }
}// end
