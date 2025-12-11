package com.example.electronicgamingplatform.mapper;

import com.example.electronicgamingplatform.entity.Order;
import com.example.electronicgamingplatform.vo.OrderVO;

import java.util.List;

/**
 * 订单数据访问层接口
 */
public interface OrderMapper {

    /**
     * 获取所有订单
     */
    List<Order> getAllOrders();

    /**
     * 获取所有订单（包含游戏名和客户名）
     */
    List<OrderVO> getAllOrderVOs();

    /**
     * 根据ID查询订单
     */
    Order getOrderById(Long id);

    /**
     * 新增订单
     */
    int addOrder(Order order);

    /**
     * 更新订单
     */
    int updateOrder(Order order);

    /**
     * 删除订单
     */
    int deleteOrderById(Long id);

    /**
     * 按游戏ID统计销量
     */
    List<Order> getOrderGroupByGameId();

    /**
     * 根据客户ID查询订单
     * @param customerId 客户ID
     * @return 订单列表
     */
    List<Order> getOrdersByCustomerId(Long customerId);
}