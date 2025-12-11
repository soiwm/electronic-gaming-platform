package com.example.electronicgamingplatform.controller;

import com.example.electronicgamingplatform.common.Result;
import com.example.electronicgamingplatform.entity.Customer;
import com.example.electronicgamingplatform.entity.CustomerLoginRequest;
import com.example.electronicgamingplatform.entity.LoginRequest;
import com.example.electronicgamingplatform.entity.LoginResponse;
import com.example.electronicgamingplatform.service.CustomerService;
import com.example.electronicgamingplatform.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private CustomerService customerService;

    // 模拟用户存储（实际项目中应从数据库查询）
    private static final Map<String, String> USERS = new HashMap<>();

    // 初始化测试用户
    static {
        USERS.put("admin", "admin123");
        USERS.put("user", "user123");
    }

    /**
     * 管理员登录
     */
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

    /**
     * 客户登录
     */
    @PostMapping("/customer/login")
    public Result<LoginResponse> customerLogin(@RequestBody CustomerLoginRequest request) {
        // 验证客户手机号和密码
        Customer customer = customerService.authenticateCustomer(request.getPhone(), request.getPassword());
        if (customer == null) {
            return Result.error("手机号或密码错误");
        }

        // 生成令牌
        String token = JwtUtils.generateToken(customer.getPhone());
        return Result.success(new LoginResponse(token, customer.getName()));
    }

    @PostMapping("/logout")
    public Result<String> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            JwtUtils.invalidateToken(token.substring(7));
        }
        return Result.success("退出成功");
    }
}