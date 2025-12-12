package com.example.electronicgamingplatform.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户游戏库实体类
 */
@Data
public class UserGameLibrary {
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 游戏ID
     */
    private Long gameId;

    /**
     * 是否安装：0-未安装，1-已安装
     */
    private Integer isInstalled;

    /**
     * 是否收藏：0-未收藏，1-已收藏
     */
    private Integer isFavorite;

    /**
     * 购买时间
     */
    private LocalDateTime purchaseTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    // 无参构造方法（MyBatis反射实例化需要）
    public UserGameLibrary() {}

    /**
     * 有参构造函数
     */
    public UserGameLibrary(Long userId, String userName, Long gameId, Integer isInstalled, Integer isFavorite) {
        this.userId = userId;
        this.userName = userName;
        this.gameId = gameId;
        this.isInstalled = isInstalled;
        this.isFavorite = isFavorite;
        this.purchaseTime = LocalDateTime.now();
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}