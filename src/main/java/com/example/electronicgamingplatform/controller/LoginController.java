package com.example.electronicgamingplatform.controller;

import com.example.electronicgamingplatform.common.Result;
import com.example.electronicgamingplatform.entity.LoginRequest;
import com.example.electronicgamingplatform.entity.LoginResponse;
import com.example.electronicgamingplatform.util.JwtUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    // 模拟用户存储（实际项目中应从数据库查询）
    private static final Map<String, String> USERS = new HashMap<>();

    // 初始化测试用户
    static {
        USERS.put("admin", "admin123");
        USERS.put("user", "user123");
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        // 验证用户名密码
        String password = USERS.get(request.getUsername());
        if (password == null || !password.equals(request.getPassword())) {
            return Result.error("用户名或密码错误");
        }

        // 生成令牌
        String token = JwtUtils.generateToken(request.getUsername());
        return Result.success(new LoginResponse(token, request.getUsername()));

    }

    @PostMapping("/logout")
    public Result<String> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            JwtUtils.invalidateToken(token.substring(7));
        }
        return Result.success("退出成功");
    }
}