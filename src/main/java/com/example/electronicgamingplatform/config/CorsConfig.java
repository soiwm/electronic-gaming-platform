package com.example.electronicgamingplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration  // 标识为配置类
public class CorsConfig {

    @Bean  // 注入跨域过滤器
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 1. 允许前端域名（本地开发时允许 localhost:8081，生产环境可指定具体域名）
        config.addAllowedOriginPattern("*");  // 开发环境用 * 方便，生产环境替换为 "http://你的前端域名"
        // 2. 允许携带 Cookie（如果前端需要登录态）
        config.setAllowCredentials(true);
        // 3. 允许所有请求方法（GET/POST/PUT/DELETE 等）
        config.addAllowedMethod("*");
        // 4. 允许所有请求头（如 Token、Content-Type 等）
        config.addAllowedHeader("*");
        // 5. 允许前端访问的响应头（可选）
        config.addExposedHeader("*");

        // 配置哪些接口需要跨域（/** 表示所有接口）
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
