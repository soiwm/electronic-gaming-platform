package com.example.electronicgamingplatform.mapper;

import com.example.electronicgamingplatform.entity.UserGameLibrary;
import com.example.electronicgamingplatform.vo.GameLibraryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 游戏库数据访问接口
 */
@Mapper
public interface LibraryMapper {

    /**
     * 获取用户游戏库列表
     * @param userId 用户ID
     * @return 游戏列表
     */
    List<GameLibraryVO> selectUserGames(@Param("userId") Long userId);

    /**
     * 根据ID查询游戏
     * @param id 游戏ID
     * @param userId 用户ID
     * @return 游戏对象
     */
    GameLibraryVO selectGameById(Long id, Long userId);

    /**
     * 添加用户游戏库记录
     * @param userGameLibrary 用户游戏库实体
     * @return 影响的行数
     */
    int addUserGameLibrary(UserGameLibrary userGameLibrary);

    /**
     * 查询用户游戏库
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 用户游戏库记录
     */
    UserGameLibrary getUserGameLibrary(@Param("userId") Long userId, @Param("gameId") Long gameId);

    /**
     * 更新安装状态
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @param installStatus 安装状态 (0: 未安装, 1: 已安装)
     * @return 影响的行数
     */
    int updateInstallStatus(@Param("userId") Long userId, @Param("gameId") Long gameId, @Param("installStatus") Integer installStatus);

    /**
     * 更新收藏状态
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @param favoriteStatus 收藏状态 (0: 未收藏, 1: 已收藏)
     * @return 影响的行数
     */
    int updateFavoriteStatus(@Param("userId") Long userId, @Param("gameId") Long gameId, @Param("favoriteStatus") Integer favoriteStatus);

    /**
     * 检查用户是否已购买游戏
     * @param userId 用户ID
     * @param gameId 游戏ID
     * @return 购买记录数
     */
    int checkUserPurchasedGame(@Param("userId") Long userId, @Param("gameId") Long gameId);
}