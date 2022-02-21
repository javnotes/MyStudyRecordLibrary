package ltd.newbee.mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class NeeBeeMallWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 上传文件的保存路径：注意：addResourceLocations 后面的路径要以 / 结尾啊，不然会拼接到文件名的前面

        //addResourceHandler 添加映射路径，addResourceLocations 来指定路径。想指定外部的目录也很简单，直接 addResourceLocations 指定即可
        registry.addResourceHandler("/upload-demo/**")
                .addResourceLocations("file:/Users/luf/Documents/upload-demo/");

    }
}