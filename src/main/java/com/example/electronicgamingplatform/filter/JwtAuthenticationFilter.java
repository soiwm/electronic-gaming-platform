package com.example.electronicgamingplatform.filter;

import com.example.electronicgamingplatform.util.JwtUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

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
        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("未提供有效令牌");
            return;
        }

        // 验证令牌
        token = token.substring(7); // 去除"Bearer "前缀
        if (!JwtUtils.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("令牌无效或已过期");
            return;
        }

        // 令牌有效，继续处理请求
        filterChain.doFilter(request, response);
    }
}