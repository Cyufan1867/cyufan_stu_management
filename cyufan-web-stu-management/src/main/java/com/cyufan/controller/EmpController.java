package com.cyufan.controller;

import com.cyufan.pojo.Emp;
import com.cyufan.pojo.EmpQueryParam;
import com.cyufan.pojo.PageResult;
import com.cyufan.pojo.Result;
import com.cyufan.services.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {
        log.info("查询请求参数： {}", empQueryParam);
        PageResult pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result save(@RequestBody Emp emp) {
        log.info("保存员工信息： {}", emp);
        empService.save(emp);
        return Result.success();
    }
}
