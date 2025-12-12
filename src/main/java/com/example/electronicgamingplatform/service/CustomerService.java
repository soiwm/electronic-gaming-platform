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
    
    /**
     * 根据手机号和密码验证客户
     * @param phone 客户手机号
     * @param password 客户密码
     * @return 验证通过返回客户信息，否则返回null
     */
    Customer authenticateCustomer(String phone, String password);
    
    /**
     * 根据手机号查询客户
     * @param phone 客户手机号
     * @return 客户信息，不存在则返回null
     */
    Customer getCustomerByPhone(String phone);
}
