package com.example.electronicgamingplatform.mapper;

import com.example.electronicgamingplatform.entity.Customer;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 客户数据访问接口
 */
@Mapper
public interface CustomerMapper {

    /**
     * 查询所有客户
     */
    List<Customer> selectAllCustomers();

    /**
     * 新增客户
     */
    int insertCustomer(Customer customer);
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
    int updateCustomer(Customer customer);

    /**
     * 根据ID删除客户
     */
    int deleteCustomerById(Long id);
    
    /**
     * 查询客户年龄分布
     */
    Map<String, Object> getAgeDistribution();
}
