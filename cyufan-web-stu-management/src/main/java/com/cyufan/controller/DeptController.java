package com.cyufan.controller;

import com.cyufan.pojo.Dept;
import com.cyufan.pojo.Result;
import com.cyufan.services.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门管理控制器
 */
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
     * 查询部门列表
     */
    @RequestMapping("/depts")
    public Result list() {
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }
}
