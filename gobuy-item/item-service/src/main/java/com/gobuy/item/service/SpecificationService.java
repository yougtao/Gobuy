package com.gobuy.item.service;


import com.gobuy.item.mapper.SpecificationMapper;
import com.gobuy.item.pojo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecificationService {
    @Autowired
    SpecificationMapper specificationMapper;

    public Specification query(Integer specificationId) {
        return specificationMapper.selectByPrimaryKey(specificationId);
    }

    // 插入
    public Boolean insert(Integer cid, String spec) {
        Specification record = new Specification();
        record.setCategoryId(cid);
        record.setSpecifications(spec);
        int count = specificationMapper.insert(record);
        return count == 1;
    }

    // 编辑
    public Boolean save(Integer cid, String specifications) {
        Specification record = new Specification();
        record.setCategoryId(cid);
        record.setSpecifications(specifications);

        return specificationMapper.updateByPrimaryKey(record) == 1;
    }
}// end
