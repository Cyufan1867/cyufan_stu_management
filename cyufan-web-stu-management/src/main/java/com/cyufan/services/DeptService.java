package com.cyufan.services;

import com.cyufan.pojo.Dept;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DeptService {
    /**
     * 查询所有部门
     */
    public List<Dept> findAll();

    /**
     * 根据id删除部门
     */
    void deleteById(Integer id);

    /**
     * 新增部门
     */
    void save(Dept dept);

    /**
     * 根据id查询部门
     */
    Dept getById(Integer id);

    /**
     * 修改部门
     */
    void update(Dept dept);
}
