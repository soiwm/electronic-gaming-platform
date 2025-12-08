package com.example.electronicgamingplatform.mapper.impl;

import com.example.electronicgamingplatform.entity.Customer;
import com.example.electronicgamingplatform.mapper.CustomerMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 客户 Mapper 实现类（模拟数据库操作）
 */
@Repository
public class CustomerMapperImpl implements CustomerMapper {

    // 模拟数据库表：存储客户数据
    private static final ConcurrentHashMap<Long, Customer> CUSTOMER_MAP = new ConcurrentHashMap<>();
    // 模拟自增主键
    private static final AtomicLong ID_GENERATOR = new AtomicLong(3);

    // 初始化测试数据
    static {
        CUSTOMER_MAP.put(1L, new Customer(1L, "张三", "13800138000", "zhangsan@example.com"));
        CUSTOMER_MAP.put(2L, new Customer(2L, "李四", "13900139000", "lisi@example.com"));
        CUSTOMER_MAP.put(3L, new Customer(3L, "王五", "13700137000", "wangwu@example.com"));
    }

    /**
     * 查询所有客户
     */
    @Override
    public List<Customer> selectAllCustomers() {
        return new ArrayList<>(CUSTOMER_MAP.values());
    }

    /**
     * 新增客户
     */
    @Override
    public boolean insertCustomer(Customer customer) {
        Long id = ID_GENERATOR.incrementAndGet();
        customer.setId(id);
        CUSTOMER_MAP.put(id, customer);
        return true;
    }
}
