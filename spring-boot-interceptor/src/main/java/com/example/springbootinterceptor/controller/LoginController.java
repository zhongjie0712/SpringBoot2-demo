package com.example.springbootinterceptor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 */
@RestController
public class LoginController {
    /**
     * 登录方法
     */
    @RequestMapping("/login")
    public boolean login(HttpServletRequest request, String username, String password) {
        // http://127.0.0.1:8080/login?username=imooc&password=123
        if ("imooc".equals(username) && "123".equals(password)) {
            // 登录成功，则添加Session并存储登录用户名
            request.getSession().setAttribute("LOGIN_NAME", username);
            return true;
        }
        return false;
    }

    /**
     * 获取登录人员信息
     */
    @RequestMapping("/info")
    public String info(HttpServletRequest request) {
        return "您就是传说中的：" + request.getSession().getAttribute("LOGIN_NAME");
    }
}

