package com.cyufan.services;

import com.cyufan.pojo.ClazzCountOption;
import com.cyufan.pojo.JobOption;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface ReportService {

    /**
     * 统计各个职位的员工人数
     * @return
     */
    JobOption getEmpJobData();

    /**
     * 统计员工性别信息
     */
    List<Map> getEmpGenderData();

    /**
     * 统计学历人数
     */
    List<Map> getStudentDegreeData();

    /**
     * 统计班级人数
     */
    ClazzCountOption getStudentCountData();
}
