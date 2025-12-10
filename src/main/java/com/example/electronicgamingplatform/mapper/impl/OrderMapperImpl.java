package com.example.electronicgamingplatform.mapper.impl;

import com.example.electronicgamingplatform.entity.Order;
import com.example.electronicgamingplatform.entity.Game;
import com.example.electronicgamingplatform.entity.Customer;
import com.example.electronicgamingplatform.mapper.OrderMapper;
import com.example.electronicgamingplatform.mapper.GameMapper;
import com.example.electronicgamingplatform.mapper.CustomerMapper;
import com.example.electronicgamingplatform.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * 订单Mapper实现类（内存版，模拟数据库操作）
 */
@Repository
public class OrderMapperImpl implements OrderMapper {

    // 内存存储订单数据（模拟数据库表）
    private static Map<Long, Order> orderMap = new HashMap<>();
    private static Long nextId = 1L;

    // 注入GameMapper和CustomerMapper
    @Autowired
    private GameMapper gameMapper;
    
    @Autowired
    private CustomerMapper customerMapper;

    // 初始化测试数据（原Service层的测试数据迁移至此）
    static {
        orderMap.put(1L, new Order(1L, 1L, 1L, 2, new BigDecimal("99.98"), 1));
        orderMap.put(2L, new Order(2L, 2L, 2L, 1, new BigDecimal("59.99"), 1));
        orderMap.put(3L, new Order(3L, 3L, 3L, 3, new BigDecimal("149.97"), 2));
        orderMap.put(4L, new Order(4L, 1L, 4L, 1, new BigDecimal("39.99"), 3));
        orderMap.put(5L, new Order(5L, 2L, 5L, 2, new BigDecimal("79.98"), 3));
        orderMap.put(6L, new Order(6L, 4L, 1L, 1, new BigDecimal("49.99"), 1));
        orderMap.put(7L, new Order(7L, 5L, 2L, 4, new BigDecimal("239.96"), 2));
        orderMap.put(8L, new Order(8L, 3L, 3L, 2, new BigDecimal("99.98"), 3));
        orderMap.put(9L, new Order(9L, 4L, 4L, 1, new BigDecimal("39.99"), 1));
        orderMap.put(10L, new Order(10L, 5L, 5L, 3, new BigDecimal("119.97"), 2));
        nextId = 11L; // 初始化后自增ID从11开始
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>(orderMap.values());
    }

    @Override
    public List<OrderVO> getAllOrderVOs() {
        List<OrderVO> orderVOList = new ArrayList<>();
        
        // 获取所有订单
        List<Order> orders = getAllOrders();
        
        // 转换为OrderVO并关联游戏和客户信息
        for (Order order : orders) {
            OrderVO orderVO = new OrderVO();
            
            // 复制订单基本信息
            orderVO.setId(order.getId());
            orderVO.setAmount(order.getAmount());
            orderVO.setCreateTime(order.getCreateTime());
            orderVO.setUpdateTime(order.getUpdateTime());
            
            
            // 关联客户信息
            Customer customer = customerMapper.selectCustomerById(order.getCustomerId());
            if (customer != null) {
                orderVO.setCustomerName(customer.getName());
            } else {
                orderVO.setCustomerName("未知客户");
            }
            
            // 关联游戏信息
            Game game = gameMapper.selectGameById(order.getGameId());
            if (game != null) {
                orderVO.setGameName(game.getName());
            } else {
                orderVO.setGameName("未知游戏");
            }
            
            orderVOList.add(orderVO);
        }
        
        return orderVOList;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderMap.get(id);
    }

    @Override
    public int addOrder(Order order) {
        orderMap.put(order.getId(), order);
        return 1; // 模拟数据库插入成功返回1
    }

    @Override
    public int updateOrder(Order order) {
        if (orderMap.containsKey(order.getId())) {
            orderMap.put(order.getId(), order);
            return 1; // 模拟更新成功
        }
        return 0; // 模拟更新失败
    }

    @Override
    public int deleteOrderById(Long id) {
        if (orderMap.containsKey(id)) {
            orderMap.remove(id);
            return 1; // 模拟删除成功
        }
        return 0; // 模拟删除失败
    }

    @Override
    public List<Order> getOrderGroupByGameId() {
        return new ArrayList<>(orderMap.values()); // 返回所有订单，供Service层分组统计
    }

    // 提供获取自增ID的方法（供Service层调用）
    public Long getNextId() {
        return nextId++;
    }
}