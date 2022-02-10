package com.example.EasyExcelDemo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @description: TODO 类描述
 * @author: luf
 * @date: 2022/2/10
 **/
@Data
public class Policy {

    @ExcelProperty("保单号")
    private String cntr_no;

    @ExcelProperty("客户姓名")
    private String cust_name;

    @ExcelProperty("证件号")
    private String id;
}
