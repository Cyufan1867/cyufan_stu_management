package com.cyufan.services;

import com.cyufan.pojo.Dept;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeptService {
    /**
     * 查询所有部门
     */
    public List<Dept> findAll();

    /**
     * 根据id删除部门
     */
    void deleteById(Integer id);
}
