package com.example.electronicgamingplatform.service.impl;

import com.example.electronicgamingplatform.entity.Game;
import com.example.electronicgamingplatform.mapper.GameMapper;
import com.example.electronicgamingplatform.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游戏业务逻辑实现类（调用 Mapper 操作数据）
 */
@Service // 标识为业务组件，让 Spring 扫描管理
public class GameServiceImpl implements GameService {

    // 注入 GameMapper（Spring 自动装配实现类）
    @Autowired
    private GameMapper gameMapper;

    /**
     * 查询所有游戏（调用 Mapper 的查询方法）
     */
    @Override
    public List<Game> getAllGames() {
        return gameMapper.selectAllGames(); // 调用 Mapper 层方法
    }

    /**
     * 新增游戏（调用 Mapper 的插入方法）
     */
    @Override
    public boolean addGame(Game game) {
        // 业务逻辑校验（如游戏名称不能为空）
        if (game.getName() == null || game.getName().trim().isEmpty()) {
            return false;
        }
        return gameMapper.insertGame(game); // 调用 Mapper 层方法
    }

    /**
     * 删除游戏（调用 Mapper 的删除方法）
     */
    @Override
    public boolean removeGameById(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        return gameMapper.deleteGameById(id); // 调用 Mapper 层方法
    }
}
