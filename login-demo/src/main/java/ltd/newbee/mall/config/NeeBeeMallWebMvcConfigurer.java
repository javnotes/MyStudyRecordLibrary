package ltd.newbee.mall.config;

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
