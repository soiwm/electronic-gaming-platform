package com.example.electronicgamingplatform.filter;

import com.example.electronicgamingplatform.entity.Customer;
import com.example.electronicgamingplatform.service.CustomerService;
import com.example.electronicgamingplatform.util.JwtUtils;
import com.example.electronicgamingplatform.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomerService customerService;
    
    // 不需要认证的路径
    private static final String[] WHITE_LIST = {"/auth/login", "/auth/logout", "/auth/customer/login"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 检查是否是白名单路径
        String path = request.getRequestURI();
        for (String whitePath : WHITE_LIST) {
            if (path.startsWith(whitePath)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 获取令牌
        String token = request.getHeader("Authorization");
        System.out.println("JWT过滤器 - 请求路径: " + path);
        System.out.println("JWT过滤器 - Authorization头: " + token);
        
        if (token == null || !token.startsWith("Bearer ")) {
            System.out.println("JWT过滤器 - 请求中没有有效的Authorization头");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("未提供有效令牌");
            return;
        }

        // 验证令牌
        token = token.substring(7); // 去除"Bearer "前缀
        System.out.println("JWT过滤器 - 提取的token: " + token);
        
        if (!JwtUtils.validateToken(token)) {
            System.out.println("JWT过滤器 - token验证失败");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("令牌无效或已过期");
            return;
        }
        
        System.out.println("JWT过滤器 - token验证成功");
        
        // 从token中获取用户名（这里是手机号）
        String phone = JwtUtils.getUsernameFromToken(token);
        System.out.println("JWT过滤器 - 从token获取的手机号: " + phone);
        
        // 根据手机号查询用户信息
        Customer customer = customerService.getCustomerByPhone(phone);
        System.out.println("JWT过滤器 - 查询到的客户信息: " + (customer != null ? "ID=" + customer.getId() + ", 姓名=" + customer.getName() : "null"));
        
        if (customer != null) {
            // 设置当前用户信息到上下文
            UserContext.setCurrentUserId(customer.getId());
            UserContext.setCurrentUserPhone(customer.getPhone());
            System.out.println("JWT过滤器 - 已设置用户上下文 - ID: " + customer.getId() + ", 手机号: " + customer.getPhone());
        } else {
            System.out.println("JWT过滤器 - 未找到客户信息，手机号: " + phone);
        }

        // 令牌有效，继续处理请求
        filterChain.doFilter(request, response);
    }
}