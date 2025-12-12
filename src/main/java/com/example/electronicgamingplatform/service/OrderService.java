package com.example.electronicgamingplatform.service;

import com.example.electronicgamingplatform.entity.Order;
import com.example.electronicgamingplatform.vo.OrderVO;

import java.util.List;
import java.util.Map;

/**
 * 订单服务接口
 */
public interface OrderService {

    /**
     * 获取所有订单
     * @return 订单VO列表
     */
    List<OrderVO> getAllOrders();

    /**
     * 根据ID获取订单
     * @param id 订单ID
     * @return 订单对象
     */
    Order getOrderById(Long id);

    /**
     * 添加订单
     * @param order 订单对象
     * @return 是否添加成功
     */
    boolean addOrder(Order order);

    /**
     * 更新订单
     * @param order 订单对象
     * @return 是否更新成功
     */
    boolean updateOrder(Order order);

    /**
     * 根据ID删除订单
     * @param id 订单ID
     * @return 是否删除成功
     */
    boolean removeOrderById(Long id);

    /**
     * 获取销量排行榜（前十）
     * @return 销量排行榜数据
     */
    List<Map<String, Object>> getSalesRanking();

    /**
     * 获取用户年龄分级统计
     * @return 年龄分级统计数据
     */
    Map<String, Integer> getAgeDistribution();

    /**
     * 获取用户订单列表
     * @return 用户订单VO列表
     */
    List<OrderVO> getUserOrders();

    /**
     * 创建订单并添加到用户游戏库
     * @param customerId 客户ID
     * @param gameId 游戏ID
     * @return 是否创建成功
     */
    boolean createOrderAndAddToLibrary(Long customerId, Long gameId);
}