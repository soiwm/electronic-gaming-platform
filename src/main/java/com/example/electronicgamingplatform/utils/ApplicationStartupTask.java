package com.example.electronicgamingplatform.utils;

import com.example.electronicgamingplatform.service.FileSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * 应用启动时执行的任务
 * 用于同步现有的logo文件到客户端UI目录
 */
@Component
public class ApplicationStartupTask implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupTask.class);
    
    @Autowired
    private FileSyncService fileSyncService;
    
    @Value("${upload.path:../electronic-gaming-platform-ui/public/images/game}")
    private String primaryUploadPath;
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("开始同步现有logo文件到客户端UI目录...");
        
        Path uploadDir = Paths.get(primaryUploadPath);
        if (!Files.exists(uploadDir)) {
            logger.warn("主上传目录不存在: {}", uploadDir);
            return;
        }
        
        try (Stream<Path> files = Files.walk(uploadDir, 1)) {
            files.filter(Files::isRegularFile)
                 .forEach(file -> {
                     String fileName = file.getFileName().toString();
                     boolean success = fileSyncService.syncToClient(fileName);
                     if (success) {
                         logger.info("同步文件成功: {}", fileName);
                     } else {
                         logger.error("同步文件失败: {}", fileName);
                     }
                 });
        } catch (IOException e) {
            logger.error("同步现有logo文件时发生错误", e);
        }
        
        logger.info("现有logo文件同步完成");
    }
}