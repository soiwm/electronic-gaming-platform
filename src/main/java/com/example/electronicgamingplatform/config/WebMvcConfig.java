package com.example.electronicgamingplatform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 从配置文件读取上传路径
    @Value("${upload.path:../electronic-gaming-platform-ui/public/images/game}")
    private String uploadPath;

    // 从配置文件读取访问URL前缀
    @Value("${upload.url-prefix:/images/game}")
    private String uploadUrlPrefix;

    /**
     * 配置静态资源处理
     * @param registry 资源处理器注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置文件上传的静态资源访问路径（指向前端目录）
        registry.addResourceHandler(uploadUrlPrefix + "/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}