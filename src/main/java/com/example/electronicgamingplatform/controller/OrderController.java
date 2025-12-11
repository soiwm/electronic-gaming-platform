package com.example.electronicgamingplatform.controller;

import com.example.electronicgamingplatform.common.Result;
import com.example.electronicgamingplatform.entity.Order;
import com.example.electronicgamingplatform.vo.OrderVO;
import com.example.electronicgamingplatform.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// 接口前缀：/order（对应前端 /api/order/* → 代理后 → /order/*）
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 1. 查询所有订单（前端列表展示）
     * 请求方式：GET
     * 接口路径：/order/list
     */
    @GetMapping("/list")
    public Result<List<OrderVO>> getOrderList() {
        List<OrderVO> orderVOList = orderService.getAllOrders();
        return Result.success(orderVOList); // 返回统一响应格式
    }

    /**
     * 2. 根据ID查询订单（前端详情/修改回显）
     * 请求方式：GET
     * 接口路径：/order/{id}
     */
    @GetMapping("/{id}")
    public Result<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return Result.error("订单不存在或ID无效");
        }
        return Result.success(order);
    }

    /**
     * 3. 新增订单（前端添加功能）
     * 请求方式：POST
     * 接口路径：/order/add
     * 请求体：Order 对象（JSON格式）
     */
    @PostMapping("/add")
    public Result<String> addOrder(@RequestBody Order order) {
        boolean success = orderService.addOrder(order);
        if (success) {
            return Result.success("订单添加成功");
        } else {
            return Result.error("订单添加失败：客户ID、游戏ID、数量为必填项");
        }
    }

    /**
     * 4. 更新订单（前端修改功能）
     * 请求方式：PUT
     * 接口路径：/order/update
     * 请求体：Order 对象（JSON格式，含ID）
     */
    @PutMapping("/update")
    public Result<String> updateOrder(@RequestBody Order order) {
        boolean success = orderService.updateOrder(order);
        if (success) {
            return Result.success("订单更新成功");
        } else {
            return Result.error("订单更新失败：ID无效或必填项为空");
        }
    }

    /**
     * 5. 删除订单（前端删除功能）
     * 请求方式：DELETE
     * 接口路径：/order/delete/{id}
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteOrder(@PathVariable Long id) {
        boolean success = orderService.removeOrderById(id);
        if (success) {
            return Result.success("订单删除成功");
        } else {
            return Result.error("订单删除失败：ID无效或订单不存在");
        }
    }

    /**
     * 6. 查询用户订单列表
     * 请求方式：GET
     * 接口路径：/order/user/list
     */
    @GetMapping("/user/list")
    public Result<List<OrderVO>> getUserOrders() {
        List<OrderVO> orderVOList = orderService.getUserOrders();
        return Result.success(orderVOList);
    }
}

