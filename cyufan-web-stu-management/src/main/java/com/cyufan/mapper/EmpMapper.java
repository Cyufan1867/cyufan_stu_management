package com.cyufan.mapper;

import com.cyufan.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmpMapper {
    /**
     * 查询所有的员工及其对应的部门名称
     */
    @Select("select e.* ,d.name deptName from emp as e left join dept as d on e.dept_id = d.id")
    public List<Emp> list();
}
