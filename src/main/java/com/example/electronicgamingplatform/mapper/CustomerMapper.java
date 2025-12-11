package com.example.electronicgamingplatform.mapper;

import com.example.electronicgamingplatform.entity.Customer;

import java.util.List;

/**
 * 客户数据访问接口
 */
public interface CustomerMapper {

    /**
     * 查询所有客户
     */
    List<Customer> selectAllCustomers();

    /**
     * 新增客户
     */
    boolean insertCustomer(Customer customer);
    /**
     * 根据ID查询客户
     */
    Customer selectCustomerById(Long id);

    /**
     * 根据手机号查询客户
     */
    Customer selectCustomerByPhone(String phone);

    /**
     * 更新客户信息
     */
    boolean updateCustomer(Customer customer);

    /**
     * 根据ID删除客户
     */
    boolean deleteCustomerById(Long id);
}
