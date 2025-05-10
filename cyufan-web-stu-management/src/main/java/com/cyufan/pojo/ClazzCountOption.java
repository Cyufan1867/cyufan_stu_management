package com.cyufan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClazzCountOption {
    private List clazzList; //职位列表
    private List dataList; //人数列表
}
