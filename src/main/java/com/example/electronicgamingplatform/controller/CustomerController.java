package com.example.electronicgamingplatform.controller;

import com.example.electronicgamingplatform.common.Result;
import com.example.electronicgamingplatform.entity.Customer;
import com.example.electronicgamingplatform.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// 接口前缀：/customer（对应前端 /api/customer/* → 代理后 → /customer/*）
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 客户列表查询
     */
    @GetMapping("/list")
    public Result<List<Customer>> getCustomerList() {
        List<Customer> customerList = customerService.getAllCustomers(); // 调用 Service 层
        return Result.success(customerList);
    }

    /**
     * 新增客户
     */
    @PostMapping("/add")
    public Result<String> addCustomer(@RequestBody Customer customer) {
        boolean success = customerService.addCustomer(customer); // 调用 Service 层
        if (success) {
            return Result.success("客户添加成功");
        } else {
            return Result.error("客户姓名或电话不能为空");
        }
    }
}