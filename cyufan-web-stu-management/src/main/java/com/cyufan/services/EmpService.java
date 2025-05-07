package com.cyufan.services;

import com.cyufan.pojo.Emp;
import com.cyufan.pojo.EmpQueryParam;
import com.cyufan.pojo.PageResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 分页查询
 * @param page 页码
 * @param pageSize 每页记录数
 */
public interface EmpService {
    /**
     * 分页查询
     */
//    PageResult page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);
    PageResult page(EmpQueryParam empQueryParam);

    /**
     * 添加员工
     * @param emp
     */
    void save(Emp emp);
    /**
     * 删除员工
     * @param ids
     */
    void deleteByIds(List<Integer> ids);
    /**
     * 根据ID查询员工的详细信息
     */
    Emp getInfo(Integer id);
}
