/*
  严肃声明：
  开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
  本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
  可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
  Copyright (c) 2019-2020 十三 all rights reserved.
  版权所有，侵权必究！
 */
package ltd.newbee.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
@RestController
public class JdbcController {

    //已经自动配置，因此可以直接通过 @Autowired 注入进来
    @Autowired
    JdbcTemplate jdbcTemplate;

    // 新增一条记录
    @GetMapping("/insert")
    public String insert(String type, String name) {
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(name)) {
            return "参数异常";
        }
        jdbcTemplate.execute("insert into jdbc_test(`ds_type`,`ds_name`) value (\"" + type + "\",\"" + name + "\")");
        return "SQL执行完毕";
    }

    // 删除一条记录
    @GetMapping("/delete")
    public String delete(int id) {
        if (id < 0) {
            return "参数异常";
        }
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from jdbc_test where ds_id = \"" + id + "\"");
        if (CollectionUtils.isEmpty(result)) {
            return "不存在该记录，删除失败";
        }
        jdbcTemplate.execute("delete from jdbc_test where ds_id=\"" + id + "\"");
        return "SQL执行完毕";
    }

    // 修改一条记录
    @GetMapping("/update")
    public String update(int id, String type, String name) {
        if (id < 0 || StringUtils.isEmpty(type) || StringUtils.isEmpty(name)) {
            return "参数异常";
        }
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from jdbc_test where ds_id = \"" + id + "\"");
        if (CollectionUtils.isEmpty(result)) {
            return "不存在该记录，无法修改";
        }
        jdbcTemplate.execute("update jdbc_test set ds_type=\"" + type + "\", ds_name= \"" + name + "\" where ds_id=\"" + id + "\"");
        return "SQL执行完毕";
    }

    // 查询所有记录
    @GetMapping("/queryAll")
    public List<Map<String, Object>> queryAll() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from jdbc_test");
        return list;
    }
}