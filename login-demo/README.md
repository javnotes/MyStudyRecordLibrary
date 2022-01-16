# 登录功能的实现

## 编写 Html 页面

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
## 新建 Controller 类

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

## 设计登录用户表

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
## 编写用户实体类

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
## 编写 Mapper 接口

新建包 mapper，再新建接口 AdminUserMapper。

```Plain Text
public interface AdminUserMapper {
    // 登录方法：参数为 userName 和 password，返回对象的类型为 AdminUser​
    AdminUser login(@Param("userName") String userName, @Param("password") String password);

}
```
## 编写 Mapper 映射文件

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
## Service业务层代码的实现

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
## Controller 类新增方法

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
## 注意

**注意Mapper 接口的路径**是与下面两处对应的：

1. 使用 @MapperScan 修饰项目启动类

```java
@MapperScan("ltd.newbee.mall.mapper") //添加 @MapperScan 注解
```

2. 配置文件中配置

```xml
mybatis.mapper-locations=classpath:mapper/*Mapper.xml
```

## 参考链接

[https://juejin.cn/book/6844733814074245133/section/6844733814141353991](https://juejin.cn/book/6844733814074245133/section/6844733814141353991)

# 设置登录拦截器

在用户访问后台管理系统的资源时，需要进行身份认证和访问拦截，所以需要实现访问拦截的功能。

## 定义拦截器 Interceptor

定义一个 Interceptor 的两种方式，这里采用第一种方式：

> 新建类要实现 Spring 的 HandlerInterceptor 接口
>
> 新建类继承实现了 HandlerInterceptor 接口的实现类，例如已经提供的实现了 HandlerInterceptor 接口的抽象类 HandlerInterceptorAdapter

新建 interceptor 包，再新建 AdminLoginInterceptor 类，此类需实现 HandlerInterceptor 接口。

```java
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台系统身份验证拦截器
 * 在类声明上方添加 @Component 注解，使其注册到 IOC 容器中。
 */
@Component
public class AdminLoginInterceptor implements HandlerInterceptor {
    /**
     * 在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理。
     * 在请求的预处理过程中，程序会读取当前 Session 对象中是否存在 loginUser 对象。
     * 如果不存在则返回 false 并跳转至登录页面；如果已经存在则返回 true，继续后续处理流程。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("进入拦截器...");
        String requestServletPath = request.getServletPath();
        if (requestServletPath.startsWith("/admin") && null == request.getSession().getAttribute("loginUser")) {
            request.getSession().setAttribute("errorMsg", "请登陆");
            response.sendRedirect(request.getContextPath() + "/admin/login");
            System.out.println("未登录，拦截成功...");
            return false;
        } else {
            request.getSession().removeAttribute("errorMsg");
            System.out.println("已登录，放行...");
            return true;
        }
    }

    /**
     * 在业务处理器处理请求执行完成后，生成视图之前执行。
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在 DispatcherServlet 完全处理完请求后被调用，可用于清理资源等，返回处理（已经渲染了页面）
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
```

## 配置拦截器

需要对该拦截器进行配置以使其生效。在 Spring Boot 2.x 版本中，通过实现 WebMvcConfigurer 接口来配置拦截器。

新建 config 包，再新建 NeeBeeMallWebMvcConfigurer 类，该类需要实现 WebMvcConfigurer 接口。

```java
import ltd.newbee.mall.interceptor.AdminLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册 AdminLoginInterceptor 登录拦截器，并对该拦截器所拦截的路径进行配置。
 *
 * @author luf
 */
@Configuration
public class NeeBeeMallWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor; // 注册 AdminLoginInterceptor 登录拦截器

    /**
     * 由于后端管理系统的所有请求路径都以 /admin 开头的，所以配置该拦截器需要拦截的路径为 /admin/**。
     * 登录页面和部分静态资源文件也是以 /admin 开头的，但是这些页面是不需要拦截的，因此需要将这些路径排除。
     * addPathPatterns()：添加路径拦截规则。
     * aexcludePathPatterns()：排除路径拦截规则。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/admin为前缀的url路径（后台登陆拦截）
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/dist/**")
                .excludePathPatterns("/admin/plugins/**");
    }
}
```

## 修改 HTML 页面

### 修改 sidebar.html 页面

在侧边栏中加入 “修改密码” 和 “退出登录” 两个栏目。

```html
<li class="nav-header">系统管理</li>
<li class="nav-item">
    <a th:href="@{/admin/profile}"
       th:class="${path}=='profile'?'nav-link active':'nav-link'">
        <i class="fa fa-user-secret nav-icon"></i>
        <p>修改密码</p>
    </a>
</li>
<li class="nav-item">
    <a th:href="@{/admin/logout}" class="nav-link">
        <i class="fa fa-sign-out nav-icon"></i>
        <p>安全退出</p>
    </a>
</li>
```

### 新建 profile.html 模板文件

用于管理员信息的显示

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<header th:replace="admin/header::header-fragment"></header>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
    <!-- 引入页面头header-fragment -->
    <div th:replace="admin/header::header-nav"></div>
    <!-- 引入工具栏sidebar-fragment -->
    <div th:replace="admin/sidebar::sidebar-fragment(${path})"></div>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <div class="container-fluid">
            </div><!-- /.container-fluid -->
        </div>
        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-6">
                        <div class="card card-primary card-outline">
                            <div class="card-header">
                                <h3 class="card-title">基本信息</h3>
                            </div> <!-- /.card-body -->
                            <div class="card-body">
                                <form role="form" id="userNameForm">
                                    <div class="form-group col-sm-8">
                                        <div class="alert alert-danger" id="updateUserName-info" style="display: none;"></div>
                                    </div>
                                    <!-- text input -->
                                    <div class="form-group">
                                        <label>登陆名称</label>
                                        <input type="text" class="form-control" id="loginUserName"
                                               name="loginUserName"
                                               placeholder="请输入登陆名称" required="true" th:value="${loginUserName}">
                                    </div>
                                    <div class="form-group">
                                        <label>昵称</label>
                                        <input type="text" class="form-control" id="nickName"
                                               name="nickName"
                                               placeholder="请输入昵称" required="true" th:value="${nickName}">
                                    </div>
                                    <div class="card-footer">
                                        <button type="button" id="updateUserNameButton" onsubmit="return false;"
                                                class="btn btn-danger float-right">确认修改
                                        </button>
                                    </div>
                                </form>
                            </div><!-- /.card-body -->
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card card-primary card-outline">
                            <div class="card-header">
                                <h3 class="card-title">修改密码</h3>
                            </div> <!-- /.card-body -->
                            <div class="card-body">
                                <form role="form" id="userPasswordForm">
                                    <div class="form-group col-sm-8">
                                        <div class="alert alert-danger updatePassword-info" id="updatePassword-info" style="display: none;"></div>
                                    </div>
                                    <!-- input states -->
                                    <div class="form-group">
                                        <label class="control-label"><i class="fa fa-key"></i> 原密码</label>
                                        <input type="text" class="form-control" id="originalPassword"
                                               name="originalPassword"
                                               placeholder="请输入原密码" required="true">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label"><i class="fa fa-key"></i> 新密码</label>
                                        <input type="text" class="form-control" id="newPassword" name="newPassword"
                                               placeholder="请输入新密码" required="true">
                                    </div>
                                    <div class="card-footer">
                                        <button type="button" id="updatePasswordButton" onsubmit="return false;"
                                                class="btn btn-danger float-right">确认修改
                                        </button>
                                    </div>
                                </form>
                            </div><!-- /.card-body -->
                        </div>
                    </div>
                </div>
            </div><!-- /.container-fluid -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <!-- 引入页脚footer-fragment -->
    <div th:replace="admin/footer::footer-fragment"></div>
</div>
<!-- jQuery -->
<script th:src="@{/admin/plugins/jquery/jquery.min.js}"></script>
<!-- jQuery UI 1.11.4 -->
<script th:src="@{/admin/plugins/jQueryUI/jquery-ui.min.js}"></script>
<!-- Bootstrap 4 -->
<script th:src="@{/admin/plugins/bootstrap/js/bootstrap.bundle.min.js}"></script>
<!-- AdminLTE App -->
<script th:src="@{/admin/dist/js/adminlte.min.js}"></script>
<!-- public.js -->
<script th:src="@{/admin/dist/js/public.js}"></script>
<!-- profile -->
<script th:src="@{/admin/dist/js/profile.js}"></script>
</body>
</html>
```

## 编辑控制层

在 AdminController 类中，新增代码：

```java
@GetMapping("/profile")
public String profile(HttpServletRequest request) {
    Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
    AdminUser adminUser = adminUserService.getUserDetailById(loginUserId);
    if (adminUser == null) {
        return "admin/login";
    }
    request.setAttribute("path", "profile");
    request.setAttribute("loginUserName", adminUser.getLoginUserName());
    request.setAttribute("nickName", adminUser.getNickName());
    return "admin/profile";
}

@PostMapping("/profile/password")
@ResponseBody
public String passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                             @RequestParam("newPassword") String newPassword) {
    if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
        return "参数不能为空";
    }
    Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
    if (adminUserService.updatePassword(loginUserId, originalPassword, newPassword)) {
        //修改成功后清空Session中的数据，前端控制跳转至登录页
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "success";
    } else {
        return "修改失败";
    }
}

@PostMapping("/profile/name")
@ResponseBody
public String nameUpdate(HttpServletRequest request, @RequestParam("loginUserName") String loginUserName,
                         @RequestParam("nickName") String nickName) {
    if (StringUtils.isEmpty(loginUserName) || StringUtils.isEmpty(nickName)) {
        return "参数不能为空";
    }
    Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
    if (adminUserService.updateName(loginUserId, loginUserName, nickName)) {
        return "success";
    } else {
        return "修改失败";
    }
}

@GetMapping("/logout")
public String logout(HttpServletRequest request) {
    request.getSession().removeAttribute("loginUserId");
    request.getSession().removeAttribute("loginUser");
    request.getSession().removeAttribute("errorMsg");
    return "admin/login";
}
```

## 编辑 Service 层

在业务层 AdminUserServiceImpl 类中新增对应的方法：

```java
@Override
public AdminUser getUserDetailById(Integer loginUserId) {
  return adminUserMapper.selectByPrimaryKey(loginUserId);
}

@Override
public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
  AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
  //当前用户非空才可以进行更改
  if (adminUser != null) {
    String originalPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8");
    String newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8");
    //比较原密码是否正确
    if (originalPasswordMd5.equals(adminUser.getLoginPassword())) {
      //设置新密码并修改
      adminUser.setLoginPassword(newPasswordMd5);
      if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
        //修改成功则返回true
        return true;
      }
    }
  }
  return false;
}

@Override
public Boolean updateName(Integer loginUserId, String loginUserName, String nickName) {
  AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
  //当前用户非空才可以进行更改
  if (adminUser != null) {
    //设置新名称并修改
    adminUser.setLoginUserName(loginUserName);
    adminUser.setNickName(nickName);
    if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
      //修改成功则返回true
      return true;
    }
  }
  return false;
}
```

## 使用 Ajax

在管理员昵称修改、管理员密码修改两个功能中，在前端请求时使用的是 Ajax 方式，对应的 JS 代码在 static/admin/dist/js/profile.js 文件中。

代码主要功能就是对输入的信息进行正则验证，然后封装数据并向后端发送请求来实现修改名称和密码的功能。

```javascript
$(function () {
    //修改个人信息
    $('#updateUserNameButton').click(function () {
        $("#updateUserNameButton").attr("disabled",true);
        var userName = $('#loginUserName').val();
        var nickName = $('#nickName').val();
        if (validUserNameForUpdate(userName, nickName)) {
            //Ajax提交数据
            var params = $("#userNameForm").serialize();
            $.ajax({
                type: "POST",
                url: "/admin/profile/name",
                data: params,
                success: function (r) {
                    $("#updateUserNameButton").attr("disabled",false);
                    console.log(r);
                    if (r == 'success') {
                        alert('修改成功');
                    } else {
                        alert('修改失败');
                    }
                }
            });
        }else{
            $("#updateUserNameButton").attr("disabled",false);
        }
    });
  
    //修改密码
    $('#updatePasswordButton').click(function () {
        $("#updatePasswordButton").attr("disabled",true);
        var originalPassword = $('#originalPassword').val();
        var newPassword = $('#newPassword').val();
        if (validPasswordForUpdate(originalPassword, newPassword)) {
            var params = $("#userPasswordForm").serialize();
            $.ajax({
                type: "POST",
                url: "/admin/profile/password",
                data: params,
                success: function (r) {
                    $("#updatePasswordButton").attr("disabled",false);
                    console.log(r);
                    if (r == 'success') {
                        alert('修改成功');
                        window.location.href = '/admin/login';
                    } else {
                        alert('修改失败');
                    }
                }
            });
        }else {
            $("#updatePasswordButton").attr("disabled",false);
        }
    });
})

/**
 * 名称验证
 */
function validUserNameForUpdate(userName, nickName) {
    if (isNull(userName) || userName.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入登陆名称！");
        return false;
    }
    if (isNull(nickName) || nickName.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("昵称不能为空！");
        return false;
    }
    if (!validUserName(userName)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的登录名！");
        return false;
    }
    if (!validCN_ENString2_18(nickName)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的昵称！");
        return false;
    }
    return true;
}

/**
 * 密码验证
 */
function validPasswordForUpdate(originalPassword, newPassword) {
    if (isNull(originalPassword) || originalPassword.trim().length < 1) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("请输入原密码！");
        return false;
    }
    if (isNull(newPassword) || newPassword.trim().length < 1) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("新密码不能为空！");
        return false;
    }
    if (!validPassword(newPassword)) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("请输入符合规范的密码！");
        return false;
    }
    return true;
}
```

## 总结

Controller 层的代码会接收前端传输过来的参数并做基本的参数判断，然后调用业务层的方法完成功能实现。

业务层代码则做具体的逻辑处理，最后调用 Dao 层的方法并执行对应的 SQL 语句完成实际的入库操作。

## 参考资料

https://juejin.cn/book/6844733814074245133/section/6844733814141370381

