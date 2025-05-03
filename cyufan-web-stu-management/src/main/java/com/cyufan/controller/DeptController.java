package com.cyufan.controller;

import com.cyufan.pojo.Dept;
import com.cyufan.pojo.Result;
import com.cyufan.services.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * 根据ID删除部门 - 简单参数接收: 方式一 (HttpServletRequest)
     */
    @DeleteMapping("/depts")
    public Result delete(@RequestParam("id") Integer id) {
        System.out.println("删除的id:" + id);
        deptService.deleteById(id);
        return Result.success();
    }
}
