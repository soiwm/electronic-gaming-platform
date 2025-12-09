package com.example.electronicgamingplatform.controller;

import com.example.electronicgamingplatform.common.Result;
import com.example.electronicgamingplatform.entity.Game;
import com.example.electronicgamingplatform.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// 接口前缀：/game（对应前端 /api/game/* → 代理后变为 /game/*）
@RestController
@RequestMapping("/game")
public class GameController {

    // 注入 GameService（Spring 自动装配实现类）
    @Autowired
    private GameService gameService;

    /**
     * 根据 ID 查询游戏
     */
    @GetMapping("/{id}")
    public Result<Game> getGameById(@PathVariable Long id) {
        Game game = gameService.getGameById(id);
        if (game == null) {
            return Result.error("游戏不存在");
        }
        return Result.success(game);
    }

    /**
     * 修改游戏信息
     */
    @PutMapping("/update")
    public Result<String> updateGame(@RequestBody Game game) {
        boolean success = gameService.updateGame(game);
        if (!success) {
            return Result.error("游戏名称不能为空或游戏不存在");
        }
        return Result.success("游戏更新成功");
    }

    /**
     * 游戏列表查询（调用 Service → Mapper → 模拟数据）
     */
    @GetMapping("/list")
    public Result<List<Game>> getGameList() {
        List<Game> gameList = gameService.getAllGames(); // 调用 Service 层
        return Result.success(gameList);
    }

    /**
     * 新增游戏
     */
    @PostMapping("/add")
    public Result<String> addGame(@RequestBody Game game) {
        boolean success = gameService.addGame(game); // 调用 Service 层
        if (success) {
            return Result.success("游戏添加成功");
        } else {
            return Result.error("游戏名称不能为空");
        }
    }

    /**
     * 删除游戏
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteGame(@PathVariable Long id) {
        boolean success = gameService.removeGameById(id); // 调用 Service 层
        if (success) {
            return Result.success("游戏删除成功");
        } else {
            return Result.error("游戏 ID 无效");
        }
    }
}
