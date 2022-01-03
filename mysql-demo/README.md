# Spring Boot 连接 MySQL 实践

## 连接检测

### 配置数据源信息

编写 ``application.properties``

```properties
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/test_db?useUnicode=true&serverTimezone=Asia/Shanghai&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowMultiQueries=true&useAffectedRows=true
spring.datasource.username=root
spring.datasource.password=abc123456
```

### 测试类

```java
@SpringBootTest
class NewbeeMallApplicationTests {

    // 注入数据源对象
    @Autowired
    private DataSource defaultDataSource;

    @Test
    public void datasourceClassTest() throws SQLException {
        // 获取数据源类型
        System.out.println("默认数据源为：" + defaultDataSource.getClass());
    }

    @Test
    public void datasourceTest() throws SQLException {
        // 获取数据库连接对象
        Connection connection = defaultDataSource.getConnection();
        System.out.print("获取连接：");
        // 判断连接对象是否为空
        System.out.println(connection != null);
        connection.close();
    }
}
```

## 操作MySQL

Spring Boot 提供 JdbcTemplate 对象来简化开发者对于数据库的操作。

JdbcTemplate 是 Spring 对 JDBC 的封装，目的是使 JDBC 更加易于使用。

JdbcTemplate 对象是通过自动配置机制注册到 IOC 容器中

> JdbcTemplate 的自动配置类是 JdbcTemplateAutoConfiguration，该自动配置类也在 spring-boot-autoconfigure-2.3.7.RELEASE.jar 中的 org.springframework.boot.autoconfigure.jdbc 包中。
>
> 类JdbcTemplateAutoConfiguration 在 DataSourceAutoConfiguration 自动配置之后，会使用 IOC 容器中的 dataSource 对象作为构造参数创建一个 JdbcTemplate 对象并注册到 IOC 容器中。

在正确配置数据源之后，我们就可以直接在代码中使用 JdbcTemplate 对象进行数据库操作。 

## 准备测试信息

```sql
DROP TABLE IF EXISTS `jdbc_test`;

CREATE TABLE `jdbc_test` (
  `ds_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ds_type` varchar(100) DEFAULT NULL COMMENT '数据源类型',
  `ds_name` varchar(100) DEFAULT NULL COMMENT '数据源名称',
  PRIMARY KEY (`ds_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8;

/*Data for the table `jdbc_test` */
insert  into `jdbc_test`(`ds_id`,`ds_type`,`ds_name`) values (1,'com.zaxxer.hikari.HikariDataSource','hikari数据源'),(2,'org.apache.commons.dbcp2.BasicDataSource','dbcp2数据源');
```

## 创建 Controller

为了演示方便，新建 controller 包，并新建 JdbcController 类，直接注入 JdbcTemplate 对象。

再在类中创建 CRUD 方法。

```java
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
```

**测试**

http://localhost:8080/insert?type=test&name=测试类

http://localhost:8080/delete?id=3

http://localhost:8080/update?id=2&type=BasicDataSource&name=数据源修改测试

# Spring Boot 整合 ORM 框架——MyBatis

MyBatis 的结构图：

![image-20220103201520029](https://vuffy.oss-cn-shenzhen.aliyuncs.com/img/image-20220103201520029.png)

MyBatis 的优点如下：

> 封装了 JDBC 大部分操作，减少开发人员工作量；
>
> 相比一些自动化的 ORM 框架，“半自动化”使得开发人员可以自由的编写 SQL 语句，灵活度更高；
>
> Java 代码与 SQL 语句分离，降低维护难度；
>
> 自动映射结果集，减少重复的编码工作；
>
> 开源社区十分活跃，文档齐全，学习成本不高。

**mybatis-springboot-starter**

MyBatis 团队提供的 MyBatis 整合 Spring Boot 项目的场景启动器。

> 构建独立的 MyBatis 应用程序
>
> 零模板
>
> 更少的 XML 配置代码甚至无 XML 配置

## 引入依赖

需要有：数据源依赖、JDBC 依赖、mybatis-springboot-starter 依赖。

```sql
<!-- 引入 MyBatis 场景启动器，包含其自动配置类及 MyBatis 3 相关依赖 -->
<!-- https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.2.1</version>
</dependency>
```

## 配置文件 application.properties

mybatis.config-location

> 配置 mybatis-config.xml 路径，mybatis-config.xml 中配置 MyBatis 基础属性，如果项目中配置了 mybatis-config.xml 文件需要设置该参数

mybatis.mapper-locations

> 配置 Mapper 文件对应的 XML 文件路径

mybatis.type-aliases-package

> 配置项目中实体类包路径

```properties
mybatis.config-location=classpath:mybatis-config.xml
mybatis.mapper-locations=classpath:mapper/*Dao.xml
mybatis.type-aliases-package=ltd.newbee.mall.entity
```

开发时只配置 mapper-locations 即可：

```properties
spring.datasource.name=newbee-mall-datasource
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/test_db?useUnicode=true&serverTimezone=Asia/Shanghai&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowMultiQueries=true&useAffectedRows=true
spring.datasource.username=root
spring.datasource.password=abc123456

mybatis.mapper-locations=classpath:mapper/*Mapper.xml
```

## 启动类增加 Mapper 扫描

在启动类中添加对 Mapper 包扫描 @MapperScan，Spring Boot 启动的时候会自动加载包路径下的 Mapper 接口。

```java
@SpringBootApplication
@MapperScan("ltd.newbee.mall.dao") //添加 @Mapper 注解
public class NewbeeMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## 准备数据

```sql
DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '登录名',
  `password` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Data for the table `jdbc_test` */

insert  into `tb_user`(`id`,`name`,`password`) values (1,'Spring Boot','123456'),(2,'MyBatis','123456'),(3,'Thymeleaf','123456'),(4,'Java','123456'),(5,'MySQL','123456'),(6,'IDEA','123456');
```

## 新建实体类和 Mapper 接口

新建 entity 包，并在包中新建 User 类，将 tb_user 中的字段映射到该实体类中

```
public class User {
    private Integer id;
    private String name;
    private String password;
		...get、set
}
```

新建 dao 包，并在包中新建 UserDao 接口，并定义增删改查四个方法

```java
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
```

## 创建 Mapper 接口的 XML 映射文件

在 resources 目录下新建 mapper 目录，并在 mapper 目录下新建 Mapper 接口的映射文件 UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--  定义映射文件与 Mapper 接口的对应关系  -->
<mapper namespace="ltd.newbee.mall.dao.UserDao">
    <!--  配置表结构和实体类的对应关系  -->
    <resultMap type="ltd.newbee.mall.entity.User" id="UserResult">
        <result property="id" column="id"/>
        <result property="name" column="name"/>
        <result property="password" column="password"/>
    </resultMap>
    <select id="findAllUsers" resultMap="UserResult">
        select id, name, password
        from tb_user
        order by id desc
    </select>
    <insert id="insertUser" parameterType="ltd.newbee.mall.entity.User">
        insert into tb_user(name, password)
        values (#{name}, #{password})
    </insert>
    <update id="updUser" parameterType="ltd.newbee.mall.entity.User">
        update tb_user
        set name=#{name},
            password=#{password}
        where id = #{id}
    </update>
    <delete id="delUser" parameterType="int">
        delete
        from tb_user
        where id = #{id}
    </delete>
</mapper>
```

## 创建对应 Controller——调用Dao方法

在 controller 包下新建 MyBatisController 类，并新增 4 个方法分别接收对于 tb_user 表的增删改查请求

```java
@RestController
public class MyBatisController {

    @Resource
    UserDao userDao;

    // 查询所有记录
    @GetMapping("/users/mybatis/queryAll")
    public List<User> queryAll() {
        return userDao.findAllUsers();
    }

    // 新增一条记录
    @GetMapping("/users/mybatis/insert")
    public Boolean insert(String name, String password) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            return false;
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        return userDao.insertUser(user) > 0;
    }

    // 修改一条记录
    @GetMapping("/users/mybatis/update")
    public Boolean insert(Integer id, String name, String password) {
        if (id == null || id < 1 || StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            return false;
        }
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        return userDao.updUser(user) > 0;
    }

    // 删除一条记录
    @GetMapping("/users/mybatis/delete")
    public Boolean insert(Integer id) {
        if (id == null || id < 1) {
            return false;
        }
        return userDao.delUser(id) > 0;
    }
}
```

## 测试

浏览器访问

```html
http://localhost:8080/users/mybatis/insert?name=十三&password=1234567

http://localhost:8080/delete?id=3

http://localhost:8080/users/mybatis/update?id=1&name=book01&password=12345678

http://localhost:8080/users/mybatis/queryAll
```

# 参考链接

[Spring Boot 实践之数据库操作](https://juejin.cn/book/6844733814074245133/section/6844733814128771086)

[Spring Boot 实践之整合 Mybatis 操作数据库](https://juejin.cn/book/6844733814074245133/section/6844733814132965390)

