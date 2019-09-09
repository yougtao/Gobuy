package com.gobuy.item.mapper;

import com.gobuy.item.pojo.Spu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpuMapper extends Mapper<Spu> {
    @Update("update spu_detail set spu_specs = #{specs} where spu_id = #{id} ")
    void saveSpecification(@Param("id") Integer id, @Param("specs") String specs);

    @Select("select specifications from spu_detail where spu_id = #{id}")
    String querySpecification(Integer id);

    @Select("select spu_id from spu_detail")
    List<Integer> getIds();
}
