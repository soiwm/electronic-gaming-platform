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
                "定期的更新则保证游戏性、功能和英雄都能持续发展，Dota 2已真正地焕发了生命",0D, "/images/game/dota2-logo.png"));
        GAME_MAP.put(2L, new Game(2L, "Apex Lengends", "射击", "由Respawn Entertainment开发制作，" +
                "屡获殊荣的《Apex Legends》，是一款免费大逃杀英雄射击游戏。在这款革命性的新一代大逃杀英雄射击游戏中，掌控日益丰富且拥有强大技能的传奇角色，" +
                "深度体验战术小队玩法及创新游戏元素。",0D, "/images/game/apex-legends-logo.png"));
        GAME_MAP.put(3L, new Game(3L, "赛博朋克2077", "开放世界", "《赛博朋克 2077》是一款开放世界动作冒险 RPG 游戏。" +
                "故事发生在暗黑未来的夜之城，一座五光十色、危机四伏的超级大都会，权力更迭和无尽的身体改造是这里不变的主题。",298D, "/images/game/cyberpunk2077-logo.png"));
        GAME_MAP.put(4L, new Game(4L, "原神", "开放世界", "《原神》是一款开放世界冒险游戏，玩家将在提瓦特大陆展开自由探索，" +
                "邂逅性格各异、能力独特的伙伴，一同对抗强敌，踏上寻找失散亲人的旅程，逐步发掘「原神」的真相。",0D, "/images/game/genshin-impact-logo.png"));
        GAME_MAP.put(5L, new Game(5L, "艾尔登法环", "角色扮演", "《艾尔登法环》是由FromSoftware开发的黑暗幻想风格动作角色扮演游戏，" +
                "玩家将在名为「交界地」的奇幻世界中，踏上成为艾尔登之王的征程，体验高自由度的探索和极具挑战性的战斗。",298D, "/images/game/elden-ring-logo.png"));
        GAME_MAP.put(6L, new Game(6L, "英雄联盟", "MOBA", "《英雄联盟》是一款多人在线战术竞技游戏，玩家将选择不同英雄组成队伍，" +
                "通过配合与操作摧毁敌方水晶枢纽取得胜利，全球拥有海量玩家和职业赛事体系。",0D, "/images/game/lol-logo.png"));
        GAME_MAP.put(7L, new Game(7L, "绝地求生", "射击", "《绝地求生》是经典的大逃杀类射击游戏，百名玩家空降荒岛，搜集物资、淘汰对手，" +
                "在不断缩小的安全区中争夺最后生存的机会，开创了大逃杀玩法的先河。",98D, "/images/game/pubg-logo.png"));
        GAME_MAP.put(8L, new Game(8L, "塞尔达传说：王国之泪", "开放世界", "《塞尔达传说：王国之泪》延续了海拉鲁大陆的冒险，玩家将操控林克解锁全新的究极手、通天术等能力，" +
                "在天空、地面、地底三层空间中自由探索，解开塞尔达失踪的谜团。",429D, "/images/game/zelda-totk-logo.png"));
        GAME_MAP.put(9L, new Game(9L, "CS2", "射击", "《反恐精英 2》是经典射击游戏CS的续作，采用起源2引擎打造，" +
                "保留了经典的反恐精英vs恐怖分子对战模式，优化了画面和物理效果，是电竞领域的常青树。",0D, "/images/game/cs2-logo.png"));
        GAME_MAP.put(10L, new Game(10L, "星露谷物语", "模拟经营", "《星露谷物语》是一款温馨的模拟经营游戏，玩家将继承爷爷的破旧农场，" +
                "通过种植作物、养殖动物、挖矿钓鱼、结交村民等方式，打造属于自己的田园生活。",48D, "/images/game/stardew-valley-logo.png"));
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
