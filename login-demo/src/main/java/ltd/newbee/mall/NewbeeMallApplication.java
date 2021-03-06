package ltd.newbee.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luf
 */
@SpringBootApplication
@MapperScan("ltd.newbee.mall.mapper") //添加 @MapperScan 注解
public class NewbeeMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewbeeMallApplication.class, args);
	}

}
