package com.gobuy.item.mapper;

import com.gobuy.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand>, SelectByIdListMapper<Brand, Integer> {

    @Insert("insert into category_brand(category_id, brand_id) VALUES(#{cid}, #{bid})")
    void insertCategoryAndBrand(@Param("cid") Integer cid, @Param("bid") Integer id);

    @Select("select category_id from category_brand where brand_id = #{bid}")
    List<Integer> queryCategory(Integer bid);

    @Delete("delete from category_brand where category_id = #{cid} and brand_id = #{bid}")
    void deleteCategory(Integer cid, Integer bid);

    @Delete("delete from category_brand where brand_id = #{bid}")
    void deleteByBrand(String bid);

    @Select("select id,name,image,letter from brand, category_brand where category_id = #{cid} and brand_id = id")
    List<Brand> queryBrandByCategory(Integer cid);

}
