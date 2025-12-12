package com.example.electronicgamingplatform.controller;

import com.example.electronicgamingplatform.common.Result;
import com.example.electronicgamingplatform.entity.UserGameLibrary;
import com.example.electronicgamingplatform.service.UserGameLibraryService;
import com.example.electronicgamingplatform.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户游戏库控制器
 */
@RestController
@RequestMapping("/userGameLibrary")
public class UserGameLibraryController {

    @Autowired
    private UserGameLibraryService userGameLibraryService;

    /**
     * 获取用户游戏库列表
     * 请求方式：GET
     * 接口路径：/userGameLibrary/list
     */
    @GetMapping("/list")
    public Result<List<UserGameLibrary>> getUserGameLibraryList() {
        try {
            // 从UserContext获取当前登录用户ID
            Long userId = UserContext.getCurrentUserId();
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            List<UserGameLibrary> userGameLibraryList = userGameLibraryService.getUserGameLibraryByUserId(userId);
            return Result.success(userGameLibraryList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取用户游戏库失败：" + e.getMessage());
        }
    }

    /**
     * 检查用户是否已购买游戏
     * 请求方式：GET
     * 接口路径：/userGameLibrary/check/{gameId}
     */
    @GetMapping("/check/{gameId}")
    public Result<Boolean> checkUserPurchasedGame(@PathVariable Long gameId) {
        try {
            // 从UserContext获取当前登录用户ID
            Long userId = UserContext.getCurrentUserId();
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            boolean isPurchased = userGameLibraryService.checkUserPurchasedGame(userId, gameId);
            return Result.success(isPurchased);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("检查购买状态失败：" + e.getMessage());
        }
    }

    /**
     * 更新安装状态
     * 请求方式：PUT
     * 接口路径：/userGameLibrary/install/{gameId}
     */
    @PutMapping("/install/{gameId}")
    public Result<String> updateInstallStatus(@PathVariable Long gameId, @RequestParam Integer isInstalled) {
        try {
            // 从UserContext获取当前登录用户ID
            Long userId = UserContext.getCurrentUserId();
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            boolean success = userGameLibraryService.updateInstallStatus(userId, gameId, isInstalled);
            if (success) {
                return Result.success(isInstalled == 1 ? "游戏安装成功" : "游戏卸载成功");
            } else {
                return Result.error("更新安装状态失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新安装状态失败：" + e.getMessage());
        }
    }

    /**
     * 更新收藏状态
     * 请求方式：PUT
     * 接口路径：/userGameLibrary/favorite/{gameId}
     */
    @PutMapping("/favorite/{gameId}")
    public Result<String> updateFavoriteStatus(@PathVariable Long gameId, @RequestParam Integer isFavorite) {
        try {
            // 从UserContext获取当前登录用户ID
            Long userId = UserContext.getCurrentUserId();
            if (userId == null) {
                return Result.error("用户未登录");
            }
            
            boolean success = userGameLibraryService.updateFavoriteStatus(userId, gameId, isFavorite);
            if (success) {
                return Result.success(isFavorite == 1 ? "游戏收藏成功" : "取消收藏成功");
            } else {
                return Result.error("更新收藏状态失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新收藏状态失败：" + e.getMessage());
        }
    }
}