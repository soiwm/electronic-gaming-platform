package com.example.electronicgamingplatform.entity;

import lombok.Data;

@Data
public class Game {
    private Long id;          // 游戏ID（自增）
    private String name;      // 游戏名称（前端传递）
    private String type;      // 游戏类型（前端传递）
    private String description; // 游戏描述（前端传递）
    private Double price;     // 游戏价格（前端传递）
    // 可根据需要添加更多字段（如创建时间、状态等）

    public Game(Long id, String name, String type, String description, Double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
    }
}