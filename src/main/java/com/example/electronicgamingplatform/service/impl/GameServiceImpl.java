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
        if (game == null || game.getName() == null || game.getName().trim().isEmpty()) {
            return false;
        }
        
        try {
            // 检查游戏名称是否已存在
            Game existingGame = gameMapper.selectGameByName(game.getName());
            if (existingGame != null) {
                return false; // 游戏名称已存在
            }
            
            // 调用 Mapper 层新增
            Integer result = gameMapper.insertGame(game);
            // 如果插入成功，MyBatis会自动将生成的ID设置到game对象中
            return result != null && result > 0;
        } catch (Exception e) {
            // 记录错误日志
            e.printStackTrace();
            return false;
        }
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

    /**
     * 根据 ID 查询游戏（调用 Mapper 的查询方法）
     */
    @Override
    public Game getGameById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return gameMapper.selectGameById(id);
    }

    /**
     * 修改游戏信息（调用 Mapper 的更新方法）
     */
    @Override
    public boolean updateGame(Game game) {
        // 业务校验
        if (game == null || game.getId() == null || game.getId() <= 0) {
            return false;
        }
        
        // 检查游戏是否存在
        Game existingGame = gameMapper.selectGameById(game.getId());
        if (existingGame == null) {
            return false;
        }
        
        // 名称不能为空
        if (game.getName() == null || game.getName().trim().isEmpty()) {
            return false;
        }
        
        // 如果修改了名称，检查新名称是否与其他游戏重复
        if (!game.getName().equals(existingGame.getName())) {
            Game gameWithSameName = gameMapper.selectGameByName(game.getName());
            if (gameWithSameName != null && !gameWithSameName.getId().equals(game.getId())) {
                return false; // 其他游戏已使用该名称
            }
        }
        
        try {
            // 调用 Mapper 层更新
            Integer result = gameMapper.updateGame(game);
            return result != null && result > 0;
        } catch (Exception e) {
            // 记录错误日志
            e.printStackTrace();
            return false;
        }
    }
}
