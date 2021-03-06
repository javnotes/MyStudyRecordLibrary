package ltd.newbee.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd/newbee-mall
 */
@SpringBootApplication
@MapperScan("ltd.newbee.mall.dao") //添加 @MapperScan 注解
public class NewbeeMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewbeeMallApplication.class, args);
	}

}
