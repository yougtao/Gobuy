package com.gobuy.item.mapper;

import com.gobuy.item.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category> {

    @Select("select category_id as id,name,parent_id,is_parent,sort from category,category_brand where category_id = id and brand_id = #{id}")
    List<Category> queryByBrandId(Integer id);

    @Select("select name from category where id = #{cid}")
    String queryName(Integer cid);
}
