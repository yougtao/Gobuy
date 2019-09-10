package com.gobuy.item.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gobuy.common.pojo.PageResult;
import com.gobuy.item.mapper.*;
import com.gobuy.item.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private StockMapper stockMapper;


    // 分页查询goods
    public PageResult<SpuBo> querySpuByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key, Boolean saleable) {
        // 设置分页
        PageHelper.startPage(page, Math.min(rows, 100));

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();

        // 过滤已删除
        criteria.andEqualTo("valid", true);

        // 过滤上下架
        criteria.andEqualTo("saleable", saleable);

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

    // 单个spu详情
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

    // 根据spu id 查询sku
    public List<Sku> querySkuBySpuId(Integer pid) {
        Sku sku = new Sku();
        sku.setSpuId(pid);

        return skuMapper.select(sku);
    }

    // 查询spu detail
    public SpuDetail querySpuDetail(Integer id) {
        return spuDetailMapper.selectByPrimaryKey(id);
    }

    // 添加商品
    @Transactional
    public Boolean addGoods(SpuBo spuBo) {
        // 保存spu
        Calendar calendar = Calendar.getInstance();
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
        spuBo.setCreateTime(timestamp);
        spuBo.setUpdateTime(timestamp);
        spuMapper.insertSelective(spuBo);

        // 保存spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        spuDetailMapper.insert(spuDetail);

        // 保存skus
        List<Sku> skus = spuBo.getSkus();
        skus.forEach(sku -> {
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(timestamp);
            sku.setUpdateTime(timestamp);

            Integer stock_num = sku.getStock();
            sku.setStock(null);

            // 保存skus信息
            skuMapper.insertSelective(sku);

            // 保存skus库存信息
            Stock stock = new Stock();
            stock.setSku_id(sku.getId());
            stock.setStock(stock_num);
            stockMapper.insert(stock);
        });
        return true;
    }

    // 单个删除goods, 即将valid字段设为0
    public Boolean delete(Integer id) {
        Spu record = new Spu();
        record.setId(id);
        record.setValid(false);
        return spuMapper.updateByPrimaryKeySelective(record) == 1;
    }

    // 上架 下架
    @Transactional
    public Boolean shelf(Integer id) {
        Spu record = spuMapper.selectByPrimaryKey(id);
        if (record == null)
            return null;

        Spu spu = new Spu();
        spu.setId(id);
        spu.setSaleable(!record.getSaleable());
        return spuMapper.updateByPrimaryKeySelective(spu) == 1;
    }


}// end
