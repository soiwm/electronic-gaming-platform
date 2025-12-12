package com.example.electronicgamingplatform.interceptor;

import com.example.electronicgamingplatform.entity.Customer;
import com.example.electronicgamingplatform.service.CustomerService;
import com.example.electronicgamingplatform.util.JwtUtils;
import com.example.electronicgamingplatform.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWT拦截器，用于验证token并设置当前用户信息
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private CustomerService customerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取token
        String token = request.getHeader("Authorization");
        System.out.println("JWT拦截器 - 请求路径: " + request.getRequestURI());
        System.out.println("JWT拦截器 - Authorization头: " + token);
        
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            System.out.println("JWT拦截器 - 提取的token: " + token);
            
            // 验证token
            if (JwtUtils.validateToken(token)) {
                System.out.println("JWT拦截器 - token验证成功");
                // 从token中获取用户名（这里是手机号）
                String phone = JwtUtils.getUsernameFromToken(token);
                System.out.println("JWT拦截器 - 从token获取的手机号: " + phone);
                
                // 根据手机号查询用户信息
                Customer customer = customerService.getCustomerByPhone(phone);
                System.out.println("JWT拦截器 - 查询到的客户信息: " + (customer != null ? "ID=" + customer.getId() + ", 姓名=" + customer.getName() : "null"));
                
                if (customer != null) {
                    // 设置当前用户信息到上下文
                    UserContext.setCurrentUserId(customer.getId());
                    UserContext.setCurrentUserPhone(customer.getPhone());
                    System.out.println("JWT拦截器 - 已设置用户上下文 - ID: " + customer.getId() + ", 手机号: " + customer.getPhone());
                } else {
                    System.out.println("JWT拦截器 - 未找到客户信息，手机号: " + phone);
                }
            } else {
                System.out.println("JWT拦截器 - token验证失败");
            }
        } else {
            System.out.println("JWT拦截器 - 请求中没有有效的Authorization头");
        }
        
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除用户上下文
        UserContext.clear();
    }
}