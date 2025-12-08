package com.example.electronicgamingplatform.service.impl;

import com.example.electronicgamingplatform.entity.Customer;
import com.example.electronicgamingplatform.mapper.CustomerMapper;
import com.example.electronicgamingplatform.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户业务逻辑实现类
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 查询所有客户
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerMapper.selectAllCustomers(); // 调用 Mapper 层方法
    }

    /**
     * 新增客户
     */
    @Override
    public boolean addCustomer(Customer customer) {
        // 业务逻辑校验
        if (customer.getUsername() == null || customer.getUsername().trim().isEmpty()) {
            return false;
        }
        if (customer.getPhone() == null || customer.getPhone().trim().isEmpty()) {
            return false;
        }
        return customerMapper.insertCustomer(customer); // 调用 Mapper 层方法
    }
}
