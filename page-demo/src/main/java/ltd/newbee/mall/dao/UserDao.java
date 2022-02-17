package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.User;
import ltd.newbee.mall.util.PageQueryUtil;

import java.util.List;

/**
 * @author 13
 * MyBatis 测试
 */
public interface UserDao {

    /**
     * 返回分页数据列表
     *
     * @param pageUtil
     * @return
     */
    List<User> findUsers(PageQueryUtil pageUtil);

    /**
     * 返回数据总数
     *
     * @param pageUtil
     * @return
     */
    int getTotalUser(PageQueryUtil pageUtil);

    /**
     * 返回数据列表
     *
     * @return
     */
    List<User> findAllUsers();

    /**
     * 添加
     *
     * @param User
     * @return
     */
    int insertUser(User User);

    /**
     * 修改
     *
     * @param User
     * @return
     */
    int updUser(User User);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delUser(Integer id);
}