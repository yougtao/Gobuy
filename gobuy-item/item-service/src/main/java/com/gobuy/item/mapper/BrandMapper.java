package com.gobuy.item.mapper;

import com.gobuy.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BrandMapper extends Mapper<Brand> {

    @Insert({"insert into category_brand(category_id, brand_id) VALUES(#{cid}, #{bid})",})
    void insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Integer id);
}
