package com.cyufan.services.impl;

import com.cyufan.mapper.EmpMapper;
import com.cyufan.pojo.Emp;
import com.cyufan.pojo.PageResult;
import com.cyufan.services.EmpService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 员工管理
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageResult page(Integer page, Integer pageSize) {
//        long total = empMapper.count();
//        Integer start = (page - 1) * pageSize;
//        List<Emp> empList = empMapper.list(start, pageSize);
//        return new PageResult(total, empList);
        PageHelper.startPage(page,pageSize);
        List<Emp> empList = empMapper.list();
        Page<Emp> p = (Page<Emp>) empList;

        return new PageResult(p.getTotal(),p.getResult());
    }
}
