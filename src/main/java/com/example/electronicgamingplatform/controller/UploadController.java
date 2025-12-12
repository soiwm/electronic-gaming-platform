package com.example.electronicgamingplatform.controller;

import com.example.electronicgamingplatform.common.Result;
import com.example.electronicgamingplatform.service.FileSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传控制器
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    // 从配置文件读取上传路径，默认为项目根目录下的uploads文件夹
    @Value("${upload.path:uploads}")
    private String uploadPath;

    // 从配置文件读取访问URL前缀，默认为/uploads
    @Value("${upload.url-prefix:/uploads}")
    private String uploadUrlPrefix;
    
    // 注入文件同步服务
    @Autowired
    private FileSyncService fileSyncService;

    /**
     * 上传游戏Logo
     * @param file 上传的文件
     * @return 文件访问路径
     */
    @PostMapping("/game/logo")
    public Result<String> uploadGameLogo(@RequestParam("file") MultipartFile file) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }

        // 检查文件类型
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            return Result.error("只支持JPG和PNG格式的图片");
        }

        // 检查文件大小（限制为2MB）
        if (file.getSize() > 2 * 1024 * 1024) {
            return Result.error("文件大小不能超过2MB");
        }

        try {
            // 创建上传目录（如果不存在）
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = generateUniqueFilename(extension);

            // 保存文件到主要目录
            Path filePath = uploadDir.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath);
            
            // 同步文件到客户端UI目录
            boolean syncSuccess = fileSyncService.syncToClient(newFilename);
            if (!syncSuccess) {
                // 同步失败不影响上传，只记录日志
                System.err.println("警告：文件同步到客户端UI目录失败，但文件已成功上传到主目录");
            }

            // 构建访问URL（直接使用文件名，不带logo子目录）
            String fileUrl = uploadUrlPrefix + "/" + newFilename;
            
            return Result.success(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 生成唯一文件名
     * @param extension 文件扩展名
     * @return 唯一文件名
     */
    private String generateUniqueFilename(String extension) {
        // 使用时间戳和UUID生成唯一文件名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return timestamp + "_" + uuid + extension;
    }
}