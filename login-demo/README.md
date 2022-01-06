# 编写 Html 页面
新建目录 templates/admin，再新建 HTML 页面 login.html。

在表单 form 中定义登录的请求路径：<form th:action="@{/admin/login}" method="post">，请求方式为 POST。

```html
<form th:action="@{/admin/login}" method="post">
    <div th:if="${not #strings.isEmpty(session.errorMsg)}" class="form-group">
        <div class="alert alert-danger" th:text="${session.errorMsg}"></div>
    </div>
    <div class="form-group has-feedback">
        <span class="fa fa-user form-control-feedback"></span>
        <input type="text" id="userName" name="userName" class="form-control" placeholder="请输入账号"
               required="true">
    </div>
    <div class="form-group has-feedback">
        <span class="fa fa-lock form-control-feedback"></span>
        <input type="password" id="password" name="password" class="form-control" placeholder="请输入密码"
               required="true">
    </div>
    <div class="row">
        <div class="col-6">
            <input type="text" class="form-control" name="verifyCode" placeholder="请输入验证码" required="true">
        </div>
        <div class="col-6">
            <img alt="点击图片刷新！" class="pointer" th:src="@{/common/kaptcha}"
                 onclick="this.src='/common/kaptcha?d='+new Date()*1">
        </div>
    </div>
    <div class="form-group has-feedback"></div>
    <div class="row">
        <div class="col-8">
        </div>
        <div class="col-4">
            <button type="submit" class="btn btn-primary btn-block btn-flat">登录
            </button>
        </div>
    </div>
</form>
```
# 新建 Controller 类
新建包 controller，再新建类 AdminController。

方法 login() 拦截的请求方式为 GET，路径为 /admin/login，即在浏览器中访问 [http://localhost:8080/admin/login](https://link.juejin.cn/?target=http%3A%2F%2Flocalhost%3A8080%2Fadmin%2Flogin%20%22http://localhost:8080/admin/login%22) 时，会访问 HTML 页面 login.html。

```java
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }
}
```


# 设计登录用户表
```Plain Text
CREATE DATABASE /*!32312 IF NOT EXISTS*/`newbee_mall_db ` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `newbee_mall_db `;
DROP TABLE IF EXISTS `tb_newbee_mall_admin_user`;
CREATE TABLE `tb_newbee_mall_admin_user`  (
  `admin_user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `login_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆名称',
  `login_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆密码',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员显示昵称',
  `locked` tinyint(4) NULL DEFAULT 0 COMMENT '是否锁定 0未锁定 1已锁定无法登陆',
  PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
​
INSERT INTO `tb_newbee_mall_admin_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '十三', 0);
INSERT INTO `tb_newbee_mall_admin_user` VALUES (2, 'newbee-admin1', 'e10adc3949ba59abbe56e057f20f883e', '新蜂01', 0);
INSERT INTO `tb_newbee_mall_admin_user` VALUES (3, 'newbee-admin2', 'e10adc3949ba59abbe56e057f20f883e', '新蜂02', 0);
```
# 编写用户实体类
新建包 entity，再新建类 AdminUser。

```Plain Text
import lombok.Getter;
import lombok.Setter;
​
@Getter
@Setter
public class AdminUser {
    private Integer adminUserId;
​
    private String loginUserName;
​
    private String loginPassword;
​
    private String nickName;
​
    private Byte locked;
​
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", adminUserId=").append(adminUserId);
        sb.append(", loginUserName=").append(loginUserName);
        sb.append(", loginPassword=").append(loginPassword);
        sb.append(", nickName=").append(nickName);
        sb.append(", locked=").append(locked);
        sb.append("]");
        return sb.toString();
    }
}
```
# 编写 Mapper 接口
新建包 mapper，再新建接口 AdminUserMapper。

```Plain Text
public interface AdminUserMapper {
    // 登录方法：参数为 userName 和 password，返回对象的类型为 AdminUser​
    AdminUser login(@Param("userName") String userName, @Param("password") String password);

}
```
# 编写 Mapper 映射文件
**即创建接口 AdminUserMapper 的映射文件 AdminUserMapper.xml**

在 resources 目录下，新建目录 mapper，再新建接口 AdminUserMapper 的映射文件 AdminUserMapper.xml，然后编写该映射文件。

其中，**定义映射文件 XXMapper.xml 与接口 XXMapper.java 的对应关系：**

```xml
<mapper namespace="dao.AdminUserMapper">
```
**配置表结构和实体类的对应关系：**

```xml
<resultMap id="BaseResultMap" type="ltd.newbee.mall.entity.AdminUser">
  <id column="admin_user_id" jdbcType="INTEGER" property="adminUserId" />
  <result column="login_user_name" jdbcType="VARCHAR" property="loginUserName" />
  <result column="login_password" jdbcType="VARCHAR" property="loginPassword" />
  <result column="nick_name" jdbcType="VARCHAR" property="nickName" />
  <result column="locked" jdbcType="TINYINT" property="locked" />
</resultMap>
```
**按照接口中声明的方法，编写具体的 SQL 语句，最终映射文件 AdminUserMapper.xml 如下：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="dao.AdminUserMapper">
  <resultMap id="BaseResultMap" type="entity.AdminUser">
    <id column="admin_user_id" jdbcType="INTEGER" property="adminUserId" />
    <result column="login_user_name" jdbcType="VARCHAR" property="loginUserName" />
    <result column="login_password" jdbcType="VARCHAR" property="loginPassword" />
    <result column="nick_name" jdbcType="VARCHAR" property="nickName" />
    <result column="locked" jdbcType="TINYINT" property="locked" />
  </resultMap>
  <sql id="Base_Column_List">
    admin_user_id, login_user_name, login_password, nick_name, locked
  </sql>

  <select id="login" resultMap="BaseResultMap">
    select
    <include refid="Base_Column_List" />
    from tb_newbee_mall_admin_user
    where login_user_name = #{userName,jdbcType=VARCHAR} AND login_password=#{password,jdbcType=VARCHAR} AND locked = 0
  </select>
</mapper>
```
# Service业务层代码的实现
新建包 service，再新建接口 AdminUserService：

```java
public interface AdminUserService {

    AdminUser login(String userName, String password);
  
} 
```
在包 service 中新建包 impl，再新建类 AdminUserServiceImpl，且该类继承了接口 AdminUserService，该类为业务层代码的实现类，用于编写具体的方法实现。

login () 方法的具体实现逻辑：先将 password 参数转换为 MD5 格式，再调用数据层的查询方法获取 AdminUser 对象。

```java
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser login(String userName, String password) {
        String passwordMd5 = MD5Util.MD5Encode(password, "UTF-8");
        return adminUserMapper.login(userName, passwordMd5);
    }
}
```
其中，工具类 MD5Util 代码：

```java
import java.security.MessageDigest;

public class MD5Util {

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
}
```
# Controller 类新增方法
login() 方法处理登录请求，即 from 表单提交时，请求由该方法处理

```java
@PostMapping(value = "/login")
public String login(@RequestParam("userName") String userName,
                    @RequestParam("password") String password,
                    @RequestParam("verifyCode") String verifyCode,
                    HttpSession session) {
  if (StringUtils.isEmpty(verifyCode)) {
    session.setAttribute("errorMsg", "验证码不能为空");
    return "admin/login";
  }
  if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
    session.setAttribute("errorMsg", "用户名或密码不能为空");
    return "admin/login";
  }
  String kaptchaCode = session.getAttribute("verifyCode") + "";
  if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
    session.setAttribute("errorMsg", "验证码错误");
    return "admin/login";
  }
  AdminUser adminUser = adminUserService.login(userName, password);
  if (adminUser != null) {
    session.setAttribute("loginUser", adminUser.getNickName());
    session.setAttribute("loginUserId", adminUser.getAdminUserId());
    //session过期时间设置为7200秒，即2小时
    return "redirect:/admin/index";
  } else {
    session.setAttribute("errorMsg", "登录失败");
    return "admin/login";
  }
}
```
# 参考链接
[https://juejin.cn/book/6844733814074245133/section/6844733814141353991](https://juejin.cn/book/6844733814074245133/section/6844733814141353991)