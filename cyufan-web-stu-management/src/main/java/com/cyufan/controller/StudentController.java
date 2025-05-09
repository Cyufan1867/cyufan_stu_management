package com.cyufan.controller;

import com.cyufan.pojo.PageResult;
import com.cyufan.pojo.Result;
import com.cyufan.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 条件分页查询
     */
    @GetMapping
    public Result page(String name,
                       Integer degree,
                       Integer clazzId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        PageResult pageResult = studentService.page(name, degree, clazzId, page, pageSize);
        return Result.success(pageResult);
    }
}
