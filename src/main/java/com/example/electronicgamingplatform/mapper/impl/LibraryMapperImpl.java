package com.example.electronicgamingplatform.mapper.impl;

import com.example.electronicgamingplatform.entity.Game;
import com.example.electronicgamingplatform.mapper.LibraryMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 游戏库数据访问实现类（使用内存模拟数据库）
 */
@Repository
public class LibraryMapperImpl implements LibraryMapper {

    // 模拟数据库表：用户游戏库
    private static final Map<Long, Game> USER_GAME_MAP = new ConcurrentHashMap<>();

    // 初始化测试数据
    static {
        // 添加一些测试游戏到用户游戏库
        USER_GAME_MAP.put(1L, new Game(1L, "赛博朋克2077", "RPG", "未来世界的开放世界RPG游戏", 298.0, "/images/game/default-game.svg"));
        USER_GAME_MAP.put(2L, new Game(2L, "艾尔登法环", "动作", "宫崎英高与乔治·R·R·马丁合作的开放世界动作RPG", 298.0, "/images/game/default-game.svg"));
        USER_GAME_MAP.put(3L, new Game(3L, "荒野大镖客2", "冒险", "西部题材的开放世界冒险游戏", 279.0, "/images/game/default-game.svg"));
        USER_GAME_MAP.put(4L, new Game(4L, "巫师3：狂猎", "RPG", "基于小说改编的奇幻RPG游戏", 127.0, "/images/game/default-game.svg"));
        USER_GAME_MAP.put(5L, new Game(5L, "只狼：影逝二度", "动作", "日本战国背景的动作冒险游戏", 267.0, "/images/game/default-game.svg"));
    }

    @Override
    public List<Game> selectUserGames() {
        return new ArrayList<>(USER_GAME_MAP.values());
    }

    @Override
    public Game selectGameById(Long id) {
        return USER_GAME_MAP.get(id);
    }
}