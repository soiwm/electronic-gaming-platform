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
}
