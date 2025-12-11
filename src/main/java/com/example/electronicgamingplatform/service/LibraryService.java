package com.example.electronicgamingplatform.service;

import com.example.electronicgamingplatform.entity.Game;

import java.util.List;

/**
 * 游戏库服务接口
 */
public interface LibraryService {

    /**
     * 获取用户游戏库列表
     * @return 游戏列表
     */
    List<Game> getUserGames();

    /**
     * 启动游戏（模拟）
     * @param gameId 游戏ID
     * @return 是否成功
     */
    boolean launchGame(Long gameId);

    /**
     * 卸载游戏（模拟）
     * @param gameId 游戏ID
     * @return 是否成功
     */
    boolean uninstallGame(Long gameId);
}