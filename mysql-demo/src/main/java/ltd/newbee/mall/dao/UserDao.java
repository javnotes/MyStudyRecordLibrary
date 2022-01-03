package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.User;

import java.util.List;

public interface UserDao {
    // 返回数据列表
    List<User> findAllUsers();

    // 添加
    int insertUser(User User);

    // 修改
    int updUser(User User);

    // 删除
    int delUser(Integer id);
}