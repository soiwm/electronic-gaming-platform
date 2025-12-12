package com.example.electronicgamingplatform.mapper;

import com.example.electronicgamingplatform.entity.Game;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 游戏数据访问接口（定义数据操作方法）
 */
@Mapper
public interface GameMapper {

    /**
     * 查询所有游戏
     */
    List<Game> selectAllGames();

    /**
     * 新增游戏
     */
    Integer insertGame(Game game);

    /**
     * 根据 ID 删除游戏
     */
    boolean deleteGameById(Long id);
    /**
     * 根据 ID 查询游戏
     */
    Game selectGameById(Long id);
    
    /**
     * 根据名称查询游戏
     */
    Game selectGameByName(String name);
    /**
     * 修改游戏信息
     */
    Integer updateGame(Game game);
}