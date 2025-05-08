package com.cyufan.services.impl;

import com.cyufan.mapper.ClazzMapper;
import com.cyufan.pojo.Clazz;
import com.cyufan.pojo.PageResult;
import com.cyufan.services.ClazzService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public PageResult page(String name, LocalDate begin, LocalDate end, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Clazz> dataList = clazzMapper.list(name, begin, end);
        Page<Clazz> p = (Page<Clazz>) dataList;
        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public void save(Clazz clazz){
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
    }

    @Override
    public Clazz getInfo(Integer id) {
        return clazzMapper.getInfo(id);
    }
}
