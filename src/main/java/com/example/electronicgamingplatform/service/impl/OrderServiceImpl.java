package com.example.electronicgamingplatform.service.impl;

import com.example.electronicgamingplatform.entity.Order;
import com.example.electronicgamingplatform.entity.Game;
import com.example.electronicgamingplatform.entity.Customer;
import com.example.electronicgamingplatform.vo.OrderVO;
import com.example.electronicgamingplatform.mapper.OrderMapper;
import com.example.electronicgamingplatform.mapper.GameMapper;
import com.example.electronicgamingplatform.mapper.CustomerMapper;
import com.example.electronicgamingplatform.mapper.impl.OrderMapperImpl;
import com.example.electronicgamingplatform.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 订单服务实现类（调用Mapper层，解耦数据存储）
 */
@Service
public class OrderServiceImpl implements OrderService {

    // 注入Mapper层（依赖注入）
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private GameMapper gameMapper;
    
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<OrderVO> getAllOrders() {
        List<OrderVO> orderVOList = new ArrayList<>();
        
        // 获取所有订单
        List<Order> orders = orderMapper.getAllOrders();
        
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
        // 调用Mapper层查询
        return orderMapper.getOrderById(id);
    }

    @Override
    public boolean addOrder(Order order) {
        // 1. 验证必填项
        if (order.getCustomerId() == null || order.getGameId() == null || order.getQuantity() == null) {
            return false;
        }

        // 2. 设置ID和时间（从Mapper层获取自增ID）
        Long nextId = ((OrderMapperImpl) orderMapper).getNextId();
        order.setId(nextId);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());

        // 3. 默认状态为待支付
        if (order.getStatus() == null) {
            order.setStatus(0);
        }

        // 4. 计算订单金额（假设游戏单价为49.99）
        if (order.getAmount() == null) {
            order.setAmount(new BigDecimal("49.99").multiply(new BigDecimal(order.getQuantity())));
        }

        // 5. 调用Mapper层新增
        int result = orderMapper.addOrder(order);
        return result > 0;
    }

    @Override
    public boolean updateOrder(Order order) {
        // 1. 验证订单是否存在
        if (order.getId() == null || orderMapper.getOrderById(order.getId()) == null) {
            return false;
        }

        Order existingOrder = orderMapper.getOrderById(order.getId());

        // 2. 更新字段
        if (order.getCustomerId() != null) {
            existingOrder.setCustomerId(order.getCustomerId());
        }
        if (order.getGameId() != null) {
            existingOrder.setGameId(order.getGameId());
        }
        if (order.getQuantity() != null) {
            existingOrder.setQuantity(order.getQuantity());
        }
        if (order.getAmount() != null) {
            existingOrder.setAmount(order.getAmount());
        }
        if (order.getStatus() != null) {
            existingOrder.setStatus(order.getStatus());
        }
        existingOrder.setUpdateTime(LocalDateTime.now());

        // 3. 调用Mapper层更新
        int result = orderMapper.updateOrder(existingOrder);
        return result > 0;
    }

    @Override
    public boolean removeOrderById(Long id) {
        // 1. 验证ID
        if (id == null || orderMapper.getOrderById(id) == null) {
            return false;
        }

        // 2. 调用Mapper层删除
        int result = orderMapper.deleteOrderById(id);
        return result > 0;
    }

    @Override
    public List<Map<String, Object>> getSalesRanking() {
        // 1. 从Mapper层获取所有订单
        List<Order> allOrders = orderMapper.getOrderGroupByGameId();

        // 2. 按游戏ID分组统计销量
        Map<Long, Integer> gameSalesMap = new HashMap<>();
        for (Order order : allOrders) {
            Long gameId = order.getGameId();
            Integer quantity = order.getQuantity();
            gameSalesMap.put(gameId, gameSalesMap.getOrDefault(gameId, 0) + quantity);
        }

        // 3. 转换为列表并排序
        List<Map<String, Object>> salesRanking = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : gameSalesMap.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("gameId", entry.getKey());
            item.put("gameName", "游戏" + entry.getKey()); // 模拟游戏名称
            item.put("sales", entry.getValue());
            salesRanking.add(item);
        }

        // 4. 按销量降序排序，取前10
        salesRanking.sort((a, b) -> (Integer) b.get("sales") - (Integer) a.get("sales"));
        return salesRanking.subList(0, Math.min(10, salesRanking.size()));
    }

    @Override
    public Map<String, Integer> getAgeDistribution() {
        // 模拟年龄分布数据（若需真实数据，可在Mapper层扩展查询）
        Map<String, Integer> ageDistribution = new HashMap<>();
        ageDistribution.put("under18", 15);
        ageDistribution.put("age18to35", 45);
        ageDistribution.put("age35to50", 25);
        ageDistribution.put("over50", 15);
        return ageDistribution;
    }

    @Override
    public List<OrderVO> getUserOrders() {
        List<OrderVO> orderVOList = new ArrayList<>();
        
        // 获取用户订单（模拟当前用户ID为1）
        Long customerId = 1L;
        List<Order> orders = orderMapper.getOrdersByCustomerId(customerId);
        
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
}