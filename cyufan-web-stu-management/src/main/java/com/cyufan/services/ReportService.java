package com.cyufan.services;

import com.cyufan.pojo.JobOption;
import org.springframework.stereotype.Service;

public interface ReportService {

    /**
     * 统计各个职位的员工人数
     * @return
     */
    JobOption getEmpJobData();
}
