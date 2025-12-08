package com.example.electronicgamingplatform.service;

import com.example.electronicgamingplatform.entity.Game;

import java.util.List;

/**
 * 游戏业务逻辑接口
 */
public interface GameService {

    /**
     * 查询所有游戏
     */
    List<Game> getAllGames();

    /**
     * 新增游戏
     */
    boolean addGame(Game game);

    /**
     * 根据 ID 删除游戏
     */
    boolean removeGameById(Long id);
}
