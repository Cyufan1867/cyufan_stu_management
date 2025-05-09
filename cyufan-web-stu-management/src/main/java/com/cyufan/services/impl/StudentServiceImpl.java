package com.cyufan.services.impl;

import com.cyufan.mapper.StudentMapper;
import com.cyufan.pojo.PageResult;
import com.cyufan.pojo.Student;
import com.cyufan.services.StudentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult page(String name, Integer degree, Integer clazzId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        List<Student> studentList = studentMapper.list(name, degree, clazzId);
        Page<Student> p = (Page<Student>) studentList;

        return new PageResult(p.getTotal(), p.getResult());
    }
}
