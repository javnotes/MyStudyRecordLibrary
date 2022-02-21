package ltd.newbee.mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 所有以 “/upload/” 开头的静态资源在请求时都会映射到 D 盘的 upload 目录下
 * 设置静态资源映射路径时，路径前需要添加 “file:” 前缀
 */
@Configuration
public class NeeBeeMallWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 上传文件的保存路径.注意：addResourceLocations 后面的路径要以 / 结尾啊，不然会拼接到文件名的前面
        // addResourceHandler 添加映射路径，addResourceLocations 来指定路径。
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:/Users/luf/Documents/upload-demo/");

    }
}