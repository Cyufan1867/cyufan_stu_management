package com.cyufan.mapper;

import com.cyufan.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("select count(*) from student where clazz_id = #{id}")
    Integer countByClazzId(Integer id);

    /**
     * 动态条件查询
     */
    List<Student> list(String name, Integer degree, Integer clazzId);
}
