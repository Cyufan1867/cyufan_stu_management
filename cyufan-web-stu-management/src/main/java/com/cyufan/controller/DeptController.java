package com.cyufan.controller;

import com.cyufan.pojo.Dept;
import com.cyufan.pojo.Result;
import com.cyufan.services.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 新增部门 - POST http://localhost:8080/depts   请求参数：{"name":"研发部"}
     */
    @PostMapping("/depts")
    public Result save(@RequestBody Dept dept) {
        System.out.println("新增的部门：" + dept);
        deptService.save(dept);
        return Result.success();
    }

    /**
     * 根据ID查询 - GET http://localhost:8080/depts/1
     */
    @GetMapping("/depts/{id}")
    public Result getById(@PathVariable Integer id) {
        System.out.println("查询的id:" + id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }
}
