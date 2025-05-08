package com.cyufan.services;

import com.cyufan.pojo.Clazz;
import com.cyufan.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;

public interface ClazzService {

    /**
     * 条件分页查询
     *
     * @param name
     * @param begin
     * @param end
     * @param page
     * @param pageSize
     * @return
     */
    PageResult page(String name, LocalDate begin, LocalDate end, Integer page, Integer pageSize);

    /**
     * 添加班级信息
     * @param clazz
     */
    void save(Clazz clazz);
}
