package com.example.electronicgamingplatform.config;

import com.example.electronicgamingplatform.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类，用于注册拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**") // 拦截所有/api/路径的请求
                .excludePathPatterns(
                        "/api/auth/**", // 排除登录相关接口
                        "/api/games/**", // 排除游戏列表接口（允许未登录用户查看）
                        "/api/upload/**" // 排除文件上传接口
                );
    }
}