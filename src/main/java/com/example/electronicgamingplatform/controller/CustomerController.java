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
     * 1. 查询所有客户（前端列表展示）
     * 请求方式：GET
     * 接口路径：/customer/list
     */
    @GetMapping("/list")
    public Result<List<Customer>> getCustomerList() {
        List<Customer> customerList = customerService.getAllCustomers();
        return Result.success(customerList); // 返回统一响应格式
    }

    /**
     * 2. 根据ID查询客户（前端详情/修改回显）
     * 请求方式：GET
     * 接口路径：/customer/{id}
     */
    @GetMapping("/{id}")
    public Result<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            return Result.error("客户不存在或ID无效");
        }
        return Result.success(customer);
    }

    /**
     * 3. 新增客户（前端添加功能）
     * 请求方式：POST
     * 接口路径：/customer/add
     * 请求体：Customer 对象（JSON格式）
     */
    @PostMapping("/add")
    public Result<String> addCustomer(@RequestBody Customer customer) {
        boolean success = customerService.addCustomer(customer);
        if (success) {
            return Result.success("客户添加成功");
        } else {
            return Result.error("客户添加失败：姓名、性别、电话为必填项");
        }
    }

    /**
     * 4. 更新客户（前端修改功能）
     * 请求方式：PUT
     * 接口路径：/customer/update
     * 请求体：Customer 对象（JSON格式，含ID）
     */
    @PutMapping("/update")
    public Result<String> updateCustomer(@RequestBody Customer customer) {
        boolean success = customerService.updateCustomer(customer);
        if (success) {
            return Result.success("客户更新成功");
        } else {
            return Result.error("客户更新失败：ID无效或必填项为空");
        }
    }

    /**
     * 5. 删除客户（前端删除功能）
     * 请求方式：DELETE
     * 接口路径：/customer/delete/{id}
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteCustomer(@PathVariable Long id) {
        boolean success = customerService.removeCustomerById(id);
        if (success) {
            return Result.success("客户删除成功");
        } else {
            return Result.error("客户删除失败：ID无效或客户不存在");
        }
    }
}