package com.example.electronicgamingplatform.mapper.impl;

import com.example.electronicgamingplatform.entity.Game;
import com.example.electronicgamingplatform.mapper.GameMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 游戏 Mapper 实现类（模拟数据库操作，用 Map 存储数据）
 */
@Repository // 标识为数据访问组件，让 Spring 扫描管理
public class GameMapperImpl implements GameMapper {

    // 模拟数据库表：用 ConcurrentHashMap 存储游戏数据（线程安全）
    private static final ConcurrentHashMap<Long, Game> GAME_MAP = new ConcurrentHashMap<>();
    // 模拟自增主键：原子类保证线程安全
    private static final AtomicLong ID_GENERATOR = new AtomicLong(4); // 从 4 开始（前 3 条为初始测试数据）

    // 初始化测试数据（模拟数据库初始数据）
    static {
        GAME_MAP.put(1L, new Game(1L, "王者荣耀", "MOBA", "5v5 公平竞技游戏"));
        GAME_MAP.put(2L, new Game(2L, "和平精英", "射击", "百人竞技生存游戏"));
        GAME_MAP.put(3L, new Game(3L, "原神", "开放世界", "二次元冒险游戏"));
    }

    /**
     * 查询所有游戏（模拟 SELECT * FROM game）
     */
    @Override
    public List<Game> selectAllGames() {
        // 从 Map 中获取所有游戏数据，转为 List 返回
        return new ArrayList<>(GAME_MAP.values());
    }

    /**
     * 新增游戏（模拟 INSERT INTO game (...) VALUES (...)）
     */
    @Override
    public boolean insertGame(Game game) {
        // 生成自增 ID
        Long id = ID_GENERATOR.incrementAndGet();
        game.setId(id);
        // 存入 Map（模拟插入数据库）
        GAME_MAP.put(id, game);
        return true; // 模拟插入成功
    }

    /**
     * 根据 ID 删除游戏（模拟 DELETE FROM game WHERE id = ?）
     */
    @Override
    public boolean deleteGameById(Long id) {
        // 从 Map 中删除（模拟删除数据库记录）
        Game removed = GAME_MAP.remove(id);
        return removed != null; // 有数据被删除则返回 true
    }
}
