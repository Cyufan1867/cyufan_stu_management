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
}
