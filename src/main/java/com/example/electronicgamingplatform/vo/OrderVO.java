package com.example.electronicgamingplatform.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单视图对象（VO）
 * 用于前端展示，包含关联的游戏名和客户名
 */
@Data
public class OrderVO {
    /**
     * 订单ID
     */
    private Long id;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 游戏名称
     */
    private String gameName;

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

    /**
     * 无参构造函数
     */
    public OrderVO() {
    }

    /**
     * 有参构造函数
     */
    public OrderVO(Long id, String customerName, String gameName, 
                   BigDecimal amount, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.customerName = customerName;
        this.gameName = gameName;
        this.amount = amount;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}