package com.example.electronicgamingplatform.entity;

import lombok.Data;

@Data
public class Customer {
    private Long id;          // 客户ID（自增）
    private String username;  // 客户姓名（前端传递）
    private String phone;     // 客户电话（前端传递）
    private String email;     // 客户邮箱（前端传递）
    // 可根据需要添加更多字段

    public Customer(Long id, String username, String phone, String email) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.email = email;
    }
}
