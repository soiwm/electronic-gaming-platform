package com.example.electronicgamingplatform.controller;

import com.example.electronicgamingplatform.common.Result;
import com.example.electronicgamingplatform.entity.Game;
import com.example.electronicgamingplatform.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 接口前缀：/library（对应前端 /api/library/* → 代理后变为 /library/*）
@RestController
@RequestMapping("/library")
public class LibraryController {

    // 注入 LibraryService（Spring 自动装配实现类）
    @Autowired
    private LibraryService libraryService;

    /**
     * 获取用户游戏库列表
     * 请求方式：GET
     * 接口路径：/library/user/games
     */
    @GetMapping("/user/games")
    public Result<List<Game>> getUserGames() {
        List<Game> gameList = libraryService.getUserGames();
        return Result.success(gameList);
    }

    /**
     * 启动游戏（模拟）
     * 请求方式：POST
     * 接口路径：/library/launch/{gameId}
     */
    @PostMapping("/launch/{gameId}")
    public Result<String> launchGame(@PathVariable Long gameId) {
        boolean success = libraryService.launchGame(gameId);
        if (success) {
            return Result.success("游戏启动成功");
        } else {
            return Result.error("游戏启动失败，游戏不存在或未购买");
        }
    }

    /**
     * 安装游戏
     * 请求方式：POST
     * 接口路径：/library/install/{gameId}
     */
    @PostMapping("/install/{gameId}")
    public Result<String> installGame(@PathVariable Long gameId) {
        boolean success = libraryService.installGame(gameId);
        if (success) {
            return Result.success("游戏安装成功");
        } else {
            return Result.error("游戏安装失败，游戏不存在或未购买");
        }
    }

    /**
     * 卸载游戏
     * 请求方式：POST
     * 接口路径：/library/uninstall/{gameId}
     */
    @PostMapping("/uninstall/{gameId}")
    public Result<String> uninstallGame(@PathVariable Long gameId) {
        boolean success = libraryService.uninstallGame(gameId);
        if (success) {
            return Result.success("游戏卸载成功");
        } else {
            return Result.error("游戏卸载失败，游戏不存在或未购买");
        }
    }

    /**
     * 切换收藏状态
     * 请求方式：POST
     * 接口路径：/library/favorite/{gameId}
     */
    @PostMapping("/favorite/{gameId}")
    public Result<String> toggleFavorite(@PathVariable Long gameId) {
        boolean success = libraryService.toggleFavorite(gameId);
        if (success) {
            return Result.success("收藏状态更新成功");
        } else {
            return Result.error("收藏状态更新失败，游戏不存在或未购买");
        }
    }
}