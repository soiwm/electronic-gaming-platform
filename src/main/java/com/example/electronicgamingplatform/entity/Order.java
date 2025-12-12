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
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 下单时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    // 无参构造方法（MyBatis反射实例化需要）
    public Order() {}

    /**
     * 有参构造函数
     */
    public Order(Long Id, Long customerId, Long gameId, BigDecimal amount) {
        this.id = Id;
        this.customerId = customerId;
        this.gameId = gameId;
        this.amount = amount;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}