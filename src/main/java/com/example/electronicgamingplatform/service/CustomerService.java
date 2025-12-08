package com.example.electronicgamingplatform.service;

import com.example.electronicgamingplatform.entity.Customer;

import java.util.List;

/**
 * 客户业务逻辑接口
 */
public interface CustomerService {

    /**
     * 查询所有客户
     */
    List<Customer> getAllCustomers();

    /**
     * 新增客户
     */
    boolean addCustomer(Customer customer);
}
