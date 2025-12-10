package com.example.electronicgamingplatform.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 */
@Data
public class Order {
    /**
     * 订单ID
     */
    private Long id;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 游戏ID
     */
    private Long gameId;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 订单状态（0-待支付，1-已支付，2-已发货，3-已完成，4-已取消）
     */
    private Integer status;

    /**
     * 下单时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    /**
     * 有参构造函数
     */
    public Order(Long Id, Long customerId, Long gameId, Integer quantity, BigDecimal amount, Integer status) {
        this.id = Id;
        this.customerId = customerId;
        this.gameId = gameId;
        this.quantity = quantity;
        this.amount = amount;
        this.status = status;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

}