package com.example.electronicgamingplatform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Customer {
    private Long id;          // 客户ID（自增）
    private String name;      // 客户姓名
    private String gender;    // 性别（1-男，2-女）
    private String phone;     // 联系电话
    private String address;   // 联系地址
    private String email;     // 邮箱（选填）
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthDate; // 出生年月

    // 无参构造方法（MyBatis反射实例化需要）
    public Customer() {}

    // 构造方法（用于初始化测试数据）
    public Customer(Long id, String name, String gender, String phone, String address, String email, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.birthDate = birthDate;
    }
}

