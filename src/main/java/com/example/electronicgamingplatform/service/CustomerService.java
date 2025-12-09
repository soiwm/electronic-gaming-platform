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

    /**
     * 根据ID查询客户
     */
    Customer getCustomerById(Long id);

    /**
     * 更新客户信息
     */
    boolean updateCustomer(Customer customer);

    /**
     * 根据ID删除客户
     */
    boolean removeCustomerById(Long id);
}
