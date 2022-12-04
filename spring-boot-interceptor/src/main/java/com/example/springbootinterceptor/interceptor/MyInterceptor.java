package com.example.springbootinterceptor.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/*
 * MyInterceptor 中的方法执行顺序为
 * preHandle – Controller 方法 – postHandle – afterCompletion ，
 * 所以拦截器实际上可以对 Controller 方法执行前后进行拦截监控。
 * 最后还有一个非常重要的注意点， preHandle 需要返回布尔类型的值。
 * preHandle 返回 true 时，对控制器方法的请求才能到达控制器，继而到达 postHandle 和 afterCompletion 方法；
 * 如果 preHandle 返回 false ，后面的方法都不会执行。
 */

/**
 * 自定义拦截器类
 */
@Slf4j
public class MyInterceptor implements HandlerInterceptor {// 实现HandlerInterceptor接口

    private void handleFalseResponse(HttpServletResponse response)
            throws Exception {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write("{\"message\":\"请先登录\"}");
        response.getWriter().flush();
    }
    /**
     * 访问控制器方法前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getRequestURI().contains("/login")) {// 登录方法直接放行
            return true;
        } else {// 其他方法需要先检验是否存在Session
            if (request.getSession().getAttribute("LOGIN_NAME") == null) {//未登录的不允许访问
                // 拦截处理
                handleFalseResponse(response);
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 访问控制器方法后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info(new Date() + "--postHandle:" + request.getRequestURL());
    }

    /**
     * postHandle方法执行完成后执行，一般用于释放资源
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info(new Date() + "--afterCompletion:" + request.getRequestURL());
    }
}

