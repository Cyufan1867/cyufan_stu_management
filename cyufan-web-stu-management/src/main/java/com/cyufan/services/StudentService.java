package com.cyufan.services;

import com.cyufan.pojo.PageResult;

public interface StudentService {

    /**
     * 条件分页查询
     */
    PageResult page(String name, Integer degree, Integer clazzId, Integer page, Integer pageSize);
}
