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
        CUSTOMER_MAP.put(1L, new Customer(1L, "张三", "1", "13800138000", "北京市朝阳区", "zhangsan@example.com"));
        CUSTOMER_MAP.put(2L, new Customer(2L, "李四", "2", "13900139000", "上海市浦东新区", "lisi@example.com"));
        CUSTOMER_MAP.put(3L, new Customer(3L, "王五", "1", "13700137000", "广州市天河区", "wangwu@example.com"));
    }

    /**
     * 查询所有客户
     */
    @Override
    public List<Customer> selectAllCustomers() {
        // 转换 Map 的值为 List 返回（前端需要列表格式）
        return new ArrayList<>(CUSTOMER_MAP.values());
    }

    /**
     * 根据ID查询客户
     */
    @Override
    public Customer selectCustomerById(Long id) {
        // 从 Map 中根据 ID 查找客户
        return CUSTOMER_MAP.get(id);
    }

    /**
     * 新增客户
     */
    @Override
    public boolean insertCustomer(Customer customer) {
        // 生成自增ID
        Long newId = ID_GENERATOR.incrementAndGet();
        customer.setId(newId);
        // 存入 Map（模拟插入数据库）
        CUSTOMER_MAP.put(newId, customer);
        return true; // 新增成功返回 true
    }

    /**
     * 更新客户
     */
    @Override
    public boolean updateCustomer(Customer customer) {
        // 校验客户ID是否存在
        if (customer.getId() == null || !CUSTOMER_MAP.containsKey(customer.getId())) {
            return false; // ID不存在，更新失败
        }
        // 覆盖原有数据（模拟更新数据库）
        CUSTOMER_MAP.put(customer.getId(), customer);
        return true; // 更新成功返回 true
    }

    /**
     * 根据ID删除客户
     */
    @Override
    public boolean deleteCustomerById(Long id) {
        // 从 Map 中删除客户（返回删除前的对象，不为空则删除成功）
        Customer removed = CUSTOMER_MAP.remove(id);
        return removed != null;
    }
}

