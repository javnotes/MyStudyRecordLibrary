package ltd.newbee.mall.interceptor;

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
