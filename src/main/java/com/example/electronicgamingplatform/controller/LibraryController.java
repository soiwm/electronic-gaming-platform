package com.example.electronicgamingplatform.controller;

import com.example.electronicgamingplatform.common.Result;
import com.example.electronicgamingplatform.entity.UserGameLibrary;
import com.example.electronicgamingplatform.service.LibraryService;
import com.example.electronicgamingplatform.util.UserContext;
import com.example.electronicgamingplatform.vo.GameLibraryVO;
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
    public Result<List<GameLibraryVO>> getUserGames() {
        try {
            // 从UserContext获取当前登录用户ID
            Long userId = UserContext.getCurrentUserId();
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            List<GameLibraryVO> gameList = libraryService.getUserGames(userId);
            return Result.success(gameList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取用户游戏库失败：" + e.getMessage());
        }
    }

    /**
     * 检查用户是否已购买游戏
     * 请求方式：GET
     * 接口路径：/library/check/{gameId}
     */
    @GetMapping("/check/{gameId}")
    public Result<Boolean> checkUserPurchasedGame(@PathVariable Long gameId) {
        try {
            // 从UserContext获取当前登录用户ID
            Long userId = UserContext.getCurrentUserId();
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            boolean isPurchased = libraryService.checkUserPurchasedGame(userId, gameId);
            return Result.success(isPurchased);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("检查购买状态失败：" + e.getMessage());
        }
    }

    /**
     * 启动游戏（模拟）
     * 请求方式：POST
     * 接口路径：/library/launch/{gameId}
     */
    @PostMapping("/launch/{gameId}")
    public Result<String> launchGame(@PathVariable Long gameId) {
        try {
            // 从UserContext获取当前登录用户ID
            Long userId = UserContext.getCurrentUserId();
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            boolean success = libraryService.launchGame(userId, gameId);
            if (success) {
                return Result.success("游戏启动成功");
            } else {
                return Result.error("游戏启动失败，游戏不存在或未购买");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("启动游戏失败：" + e.getMessage());
        }
    }

    /**
     * 安装游戏
     * 请求方式：POST
     * 接口路径：/library/install/{gameId}
     */
    @PostMapping("/install/{gameId}")
    public Result<String> installGame(@PathVariable Long gameId) {
        try {
            // 从UserContext获取当前登录用户ID
            Long userId = UserContext.getCurrentUserId();
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            boolean success = libraryService.installGame(userId, gameId);
            if (success) {
                return Result.success("游戏安装成功");
            } else {
                return Result.error("游戏安装失败，游戏不存在或未购买");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("安装游戏失败：" + e.getMessage());
        }
    }

    /**
     * 卸载游戏
     * 请求方式：POST
     * 接口路径：/library/uninstall/{gameId}
     */
    @PostMapping("/uninstall/{gameId}")
    public Result<String> uninstallGame(@PathVariable Long gameId) {
        try {
            // 从UserContext获取当前登录用户ID
            Long userId = UserContext.getCurrentUserId();
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            boolean success = libraryService.uninstallGame(userId, gameId);
            if (success) {
                return Result.success("游戏卸载成功");
            } else {
                return Result.error("游戏卸载失败，游戏不存在或未购买");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("卸载游戏失败：" + e.getMessage());
        }
    }

    /**
     * 切换收藏状态
     * 请求方式：POST
     * 接口路径：/library/favorite/{gameId}
     */
    @PostMapping("/favorite/{gameId}")
    public Result<String> toggleFavorite(@PathVariable Long gameId) {
        try {
            // 从UserContext获取当前登录用户ID
            Long userId = UserContext.getCurrentUserId();
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            boolean success = libraryService.toggleFavorite(userId, gameId);
            if (success) {
                return Result.success("收藏状态更新成功");
            } else {
                return Result.error("收藏状态更新失败，游戏不存在或未购买");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新收藏状态失败：" + e.getMessage());
        }
    }

    /**
     * 添加用户游戏库记录
     * 请求方式：POST
     * 接口路径：/library/add
     */
    @PostMapping("/add")
    public Result<String> addUserGameLibrary(@RequestBody UserGameLibrary userGameLibrary) {
        try {
            // 从UserContext获取当前登录用户ID
            Long userId = UserContext.getCurrentUserId();
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            // 设置用户ID
            userGameLibrary.setUserId(userId);
            
            boolean success = libraryService.addUserGameLibrary(userGameLibrary);
            if (success) {
                return Result.success("添加游戏到库成功");
            } else {
                return Result.error("添加游戏到库失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加游戏到库失败：" + e.getMessage());
        }
    }

    /**
     * 根据用户ID和游戏ID查询记录
     * 请求方式：GET
     * 接口路径：/library/game/{gameId}
     */
    @GetMapping("/game/{gameId}")
    public Result<UserGameLibrary> getUserGameLibrary(@PathVariable Long gameId) {
        try {
            // 从UserContext获取当前登录用户ID
            Long userId = UserContext.getCurrentUserId();
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            UserGameLibrary userGameLibrary = libraryService.getUserGameLibrary(userId, gameId);
            return Result.success(userGameLibrary);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取游戏库记录失败：" + e.getMessage());
        }
    }
}