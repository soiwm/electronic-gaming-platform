package com.example.electronicgamingplatform.mapper;

import com.example.electronicgamingplatform.entity.UserGameLibrary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户游戏库Mapper接口
 */
@Mapper
public interface UserGameLibraryMapper {

    /**
     * 添加用户游戏库记录
     * @param userGameLibrary 用户游戏库对象
     * @return 影响的行数
     */
    int addUserGameLibrary(UserGameLibrary userGameLibrary);

    /**
     * 根据用户ID和游戏ID查询记录
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 用户游戏库记录
     */
    UserGameLibrary getUserGameLibrary(@Param("userId") Long userId, @Param("gameId") Long gameId);

    /**
     * 根据用户ID查询所有游戏库记录
     * @param userId 用户ID
     * @return 用户游戏库记录列表
     */
    List<UserGameLibrary> getUserGameLibraryByUserId(@Param("userId") Long userId);

    /**
     * 更新安装状态
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @param isInstalled 安装状态：0-未安装，1-已安装
     * @return 影响的行数
     */
    int updateInstallStatus(@Param("userId") Long userId, @Param("gameId") Long gameId, @Param("isInstalled") Integer isInstalled);

    /**
     * 更新收藏状态
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @param isFavorite 收藏状态：0-未收藏，1-已收藏
     * @return 影响的行数
     */
    int updateFavoriteStatus(@Param("userId") Long userId, @Param("gameId") Long gameId, @Param("isFavorite") Integer isFavorite);

    /**
     * 检查用户是否已购买游戏
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 购买记录数量
     */
    int checkUserPurchasedGame(@Param("userId") Long userId, @Param("gameId") Long gameId);
}