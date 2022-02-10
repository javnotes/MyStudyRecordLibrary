package com.example.EasyExcelDemo.controller;

import com.alibaba.excel.EasyExcel;
import com.example.EasyExcelDemo.entity.Policy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO 类描述
 * @author: luf
 * @date: 2022/2/10
 **/
@Controller
@RequestMapping("/list")
public class ExportController {

    // 模拟从数据库查询出来的数据
    private List<Policy> data() {
        List<Policy> list = new ArrayList<Policy>();
        for (int i = 0; i < 10; i++) {
            Policy policy = new Policy();
            policy.setCntr_no("2022441100578000" + i + i);
            policy.setCust_name("客户姓名" + i + i);
            policy.setId("37280113948000" + i + i);
            list.add(policy);
        }
        return list;
    }

    // 导出
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        //   Student.class 是按导出类  data()应为数据库查询数据，这里只是模拟
        EasyExcel.write(response.getOutputStream(), Policy.class).sheet("模板").doWrite(data());
    }
}
