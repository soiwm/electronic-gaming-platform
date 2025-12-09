package com.example.electronicgamingplatform.mapper;



import com.example.electronicgamingplatform.entity.Game;
import java.util.List;

/**
 * 游戏数据访问接口（定义数据操作方法）
 */
public interface GameMapper {

    /**
     * 查询所有游戏
     */
    List<Game> selectAllGames();

    /**
     * 新增游戏
     */
    boolean insertGame(Game game);

    /**
     * 根据 ID 删除游戏
     */
    boolean deleteGameById(Long id);
    /**
     * 根据 ID 查询游戏
     */
    Game selectGameById(Long id);
    /**
     * 修改游戏信息
     */
    boolean updateGame(Game game);
}