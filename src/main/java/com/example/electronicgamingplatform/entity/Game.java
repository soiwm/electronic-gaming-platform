package com.example.electronicgamingplatform.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Game {
    private Long id;          // 游戏ID（自增）
    private String name;      // 游戏名称（前端传递）
    private String type;      // 游戏类型（前端传递）
    private String description; // 游戏描述（前端传递）
    private BigDecimal price;     // 游戏价格（前端传递）
    private String logo;       // 游戏logo图片路径（前端传递）
    private Integer installed; // 是否已安装（0-未安装，1-已安装）
    private Integer favorite;  // 是否已收藏（0-未收藏，1-已收藏）
    private LocalDateTime purchaseTime; // 购买时间
    // 可根据需要添加更多字段（如创建时间、状态等）

    public Game() {
    }

    public Game(Long id, String name, String type, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
    }

    public Game(Long id, String name, String type, String description, BigDecimal price, String logo) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.logo = logo;
    }

    public Game(Long id, String name, String type, String description, BigDecimal price, String logo, Integer installed, Integer favorite) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.logo = logo;
        this.installed = installed;
        this.favorite = favorite;
    }

    public Game(Long id, String name, String type, String description, BigDecimal price, String logo, Integer installed, Integer favorite, LocalDateTime purchaseTime) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.logo = logo;
        this.installed = installed;
        this.favorite = favorite;
        this.purchaseTime = purchaseTime;
    }
}