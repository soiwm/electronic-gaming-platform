package com.example.electronicgamingplatform.service;

import com.example.electronicgamingplatform.entity.UserGameLibrary;

import java.util.List;

/**
 * 用户游戏库服务接口
 */
public interface UserGameLibraryService {

    /**
     * 添加用户游戏库记录
     * @param userGameLibrary 用户游戏库对象
     * @return 是否成功
     */
    boolean addUserGameLibrary(UserGameLibrary userGameLibrary);

    /**
     * 根据用户ID和游戏ID查询记录
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 用户游戏库记录
     */
    UserGameLibrary getUserGameLibrary(Long userId, Long gameId);

    /**
     * 根据用户ID查询所有游戏库记录
     * @param userId 用户ID
     * @return 用户游戏库记录列表
     */
    List<UserGameLibrary> getUserGameLibraryByUserId(Long userId);

    /**
     * 更新安装状态
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @param isInstalled 安装状态：0-未安装，1-已安装
     * @return 是否成功
     */
    boolean updateInstallStatus(Long userId, Long gameId, Integer isInstalled);

    /**
     * 更新收藏状态
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @param isFavorite 收藏状态：0-未收藏，1-已收藏
     * @return 是否成功
     */
    boolean updateFavoriteStatus(Long userId, Long gameId, Integer isFavorite);

    /**
     * 检查用户是否已购买游戏
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 是否已购买
     */
    boolean checkUserPurchasedGame(Long userId, Long gameId);
}