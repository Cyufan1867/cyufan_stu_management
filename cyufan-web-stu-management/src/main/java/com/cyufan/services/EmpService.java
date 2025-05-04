package com.cyufan.services;

import com.cyufan.pojo.PageResult;

/**
 * 分页查询
 * @param page 页码
 * @param pageSize 每页记录数
 */
public interface EmpService {
    PageResult page(Integer page, Integer pageSize);
}
