package com.example.electronicgamingplatform.entity;

import lombok.Data;

@Data
public class CustomerLoginRequest {
    private String phone;      // 客户手机号（登录账号）
    private String password;   // 登录密码
}