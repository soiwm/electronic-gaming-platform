package com.example.electronicgamingplatform.entity;

import lombok.Data;

@Data
public class Customer {
    private Long id;          // 客户ID（自增）
    private String name;      // 客户姓名
    private String gender;    // 性别（1-男，2-女）
    private String phone;     // 联系电话
    private String address;   // 联系地址
    private String email;     // 邮箱（选填）

    // 构造方法（用于初始化测试数据）
    public Customer(Long id, String name, String gender, String phone, String address, String email) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }
}

