package com.cyufan.services;

import com.cyufan.pojo.PageResult;
import com.cyufan.pojo.Student;

import java.util.List;

public interface StudentService {

    /**
     * 条件分页查询
     */
    PageResult page(String name, Integer degree, Integer clazzId, Integer page, Integer pageSize);

    /**
     * 添加学生信息
     */
    void save(Student student);

    /**
     * 根据ID查询学生信息
     */
    Student getInfo(Integer id);

    /**
     * 修改学生信息
     */
    void update(Student student);

    /**
     * 删除学生信息
     */
    void delete(List<Integer> ids);
}
