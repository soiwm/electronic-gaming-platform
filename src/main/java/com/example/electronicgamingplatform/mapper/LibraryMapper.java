package com.example.electronicgamingplatform.mapper;

import com.example.electronicgamingplatform.entity.Game;

import java.util.List;

/**
 * 游戏库数据访问接口
 */
public interface LibraryMapper {

    /**
     * 获取用户游戏库列表
     * @return 游戏列表
     */
    List<Game> selectUserGames();

    /**
     * 根据ID查询游戏
     * @param id 游戏ID
     * @return 游戏对象
     */
    Game selectGameById(Long id);
}