package com.example.electronicgamingplatform.service;

import com.example.electronicgamingplatform.entity.UserGameLibrary;
import com.example.electronicgamingplatform.vo.GameLibraryVO;

import java.util.List;

/**
 * 游戏库服务接口
 */
public interface LibraryService {

    /**
     * 获取用户游戏库列表
     * @param userId 用户ID
     * @return 游戏列表
     */
    List<GameLibraryVO> getUserGames(Long userId);

    /**
     * 启动游戏（模拟）
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 是否成功
     */
    boolean launchGame(Long userId, Long gameId);

    /**
     * 安装游戏
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 是否成功
     */
    boolean installGame(Long userId, Long gameId);

    /**
     * 卸载游戏
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 是否成功
     */
    boolean uninstallGame(Long userId, Long gameId);

    /**
     * 切换收藏状态
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 是否成功
     */
    boolean toggleFavorite(Long userId, Long gameId);

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
     * 根据ID查询游戏
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 游戏对象
     */
    GameLibraryVO selectGameById(Long userId, Long gameId);

    /**
     * 检查用户是否已购买游戏
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 是否已购买
     */
    boolean checkUserPurchasedGame(Long userId, Long gameId);
}