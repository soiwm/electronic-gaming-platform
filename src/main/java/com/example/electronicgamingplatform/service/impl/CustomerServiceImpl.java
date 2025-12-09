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
        // 直接调用 Mapper 层方法（无额外业务逻辑）
        return customerMapper.selectAllCustomers();
    }

    /**
     * 根据ID查询客户
     */
    @Override
    public Customer getCustomerById(Long id) {
        // 业务校验：ID不能为空且大于0
        if (id == null || id <= 0) {
            return null;
        }
        // 调用 Mapper 层查询
        return customerMapper.selectCustomerById(id);
    }

    /**
     * 新增客户
     */
    @Override
    public boolean addCustomer(Customer customer) {
        // 业务校验：必填字段不能为空
        if (customer == null
                || customer.getName() == null || customer.getName().trim().isEmpty()
                || customer.getGender() == null || (!"1".equals(customer.getGender()) && !"2".equals(customer.getGender()))
                || customer.getPhone() == null || customer.getPhone().trim().isEmpty()) {
            return false; // 校验失败，返回 false
        }
        // 调用 Mapper 层新增
        return customerMapper.insertCustomer(customer);
    }

    /**
     * 更新客户
     */
    @Override
    public boolean updateCustomer(Customer customer) {
        // 业务校验：ID必填 + 核心字段不能为空
        if (customer == null || customer.getId() == null || customer.getId() <= 0
                || customer.getName() == null || customer.getName().trim().isEmpty()
                || customer.getGender() == null || (!"1".equals(customer.getGender()) && !"2".equals(customer.getGender()))
                || customer.getPhone() == null || customer.getPhone().trim().isEmpty()) {
            return false; // 校验失败，返回 false
        }
        // 调用 Mapper 层更新
        return customerMapper.updateCustomer(customer);
    }

    /**
     * 删除客户
     */
    @Override
    public boolean removeCustomerById(Long id) {
        // 业务校验：ID不能为空且大于0
        if (id == null || id <= 0) {
            return false;
        }
        // 调用 Mapper 层删除
        return customerMapper.deleteCustomerById(id);
    }
}
