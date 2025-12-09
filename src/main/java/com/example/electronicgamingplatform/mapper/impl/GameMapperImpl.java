package com.example.electronicgamingplatform.mapper.impl;

import com.example.electronicgamingplatform.entity.Game;
import com.example.electronicgamingplatform.mapper.GameMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 游戏 Mapper 实现类
 */
@Repository // 标识为数据访问组件，让 Spring 扫描管理
public class GameMapperImpl implements GameMapper {

    // 模拟数据库表：用 ConcurrentHashMap 存储游戏数据（线程安全）
    private static final ConcurrentHashMap<Long, Game> GAME_MAP = new ConcurrentHashMap<>();
    // 模拟自增主键：原子类保证线程安全
    private static final AtomicLong ID_GENERATOR = new AtomicLong(4); // 从 4 开始（前 3 条为初始测试数据）

    // 初始化测试数据（模拟数据库初始数据）
    static {
        GAME_MAP.put(1L, new Game(1L, "Dota2", "MOBA", "每一天全球有数百万玩家化为一百余名Dota英雄展开大战。" +
                "不论是游戏时间刚满10小时还是1000小时，比赛中总能找到新鲜感。" +
                "定期的更新则保证游戏性、功能和英雄都能持续发展，Dota 2已真正地焕发了生命",0D));
        GAME_MAP.put(2L, new Game(2L, "Apex Lengends", "射击", "由Respawn Entertainment开发制作，" +
                "屡获殊荣的《Apex Legends》，是一款免费大逃杀英雄射击游戏。在这款革命性的新一代大逃杀英雄射击游戏中，掌控日益丰富且拥有强大技能的传奇角色，" +
                "深度体验战术小队玩法及创新游戏元素。",0D));
        GAME_MAP.put(3L, new Game(3L, "赛博朋克2077", "开放世界", "《赛博朋克 2077》是一款开放世界动作冒险 RPG 游戏。" +
                "故事发生在暗黑未来的夜之城，一座五光十色、危机四伏的超级大都会，权力更迭和无尽的身体改造是这里不变的主题。",298D));
    }

    /**
     * 查询所有游戏
     */
    @Override
    public List<Game> selectAllGames() {
        // 从 Map 中获取所有游戏数据，转为 List 返回
        return new ArrayList<>(GAME_MAP.values());
    }

    /**
     * 新增游戏
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
     * 根据 ID 删除游戏
     */
    @Override
    public boolean deleteGameById(Long id) {
        // 从 Map 中删除（模拟删除数据库记录）
        Game removed = GAME_MAP.remove(id);
        return removed != null; // 有数据被删除则返回 true
    }
    /**
     * 根据 ID 查询游戏
     */
    @Override
    public Game selectGameById(Long id) {
        return GAME_MAP.get(id); // 从 Map 中查询单个游戏
    }

    /**
     * 修改游戏信息
     */
    @Override
    public boolean updateGame(Game game) {
        if (game.getId() == null || !GAME_MAP.containsKey(game.getId())) {
            return false; // 游戏 ID 不存在，更新失败
        }
        GAME_MAP.put(game.getId(), game); // 覆盖原有数据（模拟更新数据库）
        return true;
    }
}
