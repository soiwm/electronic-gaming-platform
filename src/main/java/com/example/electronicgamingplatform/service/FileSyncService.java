package com.example.electronicgamingplatform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 文件同步服务
 * 用于将上传的文件同步到多个前端项目目录
 */
@Service
public class FileSyncService {
    
    private static final Logger logger = LoggerFactory.getLogger(FileSyncService.class);
    
    // 主要上传路径（electronic-gaming-platform-ui）
    @Value("${upload.path:../electronic-gaming-platform-ui/public/images/game}")
    private String primaryUploadPath;
    
    // 客户端UI路径（electronic-gaming-platform-client-ui）
    private String clientUploadPath = "../electronic-gaming-platform-client-ui/public/images/game";
    
    /**
     * 同步文件到客户端UI目录
     * @param fileName 文件名
     * @return 是否同步成功
     */
    public boolean syncToClient(String fileName) {
        try {
            Path sourceFile = Paths.get(primaryUploadPath, fileName);
            Path targetDir = Paths.get(clientUploadPath);
            Path targetFile = targetDir.resolve(fileName);
            
            // 确保目标目录存在
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
                logger.info("创建客户端UI目录: {}", targetDir);
            }
            
            // 复制文件
            Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
            logger.info("文件同步成功: {} -> {}", sourceFile, targetFile);
            
            return true;
        } catch (IOException e) {
            logger.error("文件同步失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 从客户端UI目录删除文件
     * @param fileName 文件名
     * @return 是否删除成功
     */
    public boolean deleteFromClient(String fileName) {
        try {
            Path targetFile = Paths.get(clientUploadPath, fileName);
            
            if (Files.exists(targetFile)) {
                Files.delete(targetFile);
                logger.info("从客户端UI目录删除文件: {}", targetFile);
            }
            
            return true;
        } catch (IOException e) {
            logger.error("从客户端UI目录删除文件失败: {}", e.getMessage(), e);
            return false;
        }
    }
}