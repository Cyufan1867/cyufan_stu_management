package com.cyufan.services;

import com.cyufan.pojo.PageResult;
import com.cyufan.pojo.Student;

public interface StudentService {

    /**
     * 条件分页查询
     */
    PageResult page(String name, Integer degree, Integer clazzId, Integer page, Integer pageSize);

    /**
     * 添加学生信息
     */
    void save(Student student);
}
