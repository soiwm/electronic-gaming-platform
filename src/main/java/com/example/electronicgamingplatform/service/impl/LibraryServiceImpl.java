package com.example.electronicgamingplatform.service.impl;

import com.example.electronicgamingplatform.entity.Game;
import com.example.electronicgamingplatform.entity.UserGameLibrary;
import com.example.electronicgamingplatform.mapper.LibraryMapper;
import com.example.electronicgamingplatform.mapper.UserGameLibraryMapper;
import com.example.electronicgamingplatform.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游戏库服务实现类
 */
@Service
public class LibraryServiceImpl implements LibraryService {

    private static final Logger logger = LoggerFactory.getLogger(LibraryServiceImpl.class);

    @Autowired
    private LibraryMapper libraryMapper;
    
    @Autowired
    private UserGameLibraryMapper userGameLibraryMapper;

    @Override
    public List<Game> getUserGames() {
        List<Game> games = libraryMapper.selectUserGames();
        logger.info("获取用户游戏库列表，共{}个游戏", games.size());
        for (Game game : games) {
            logger.info("游戏ID: {}, 名称: {}, 购买时间: {}", game.getId(), game.getName(), game.getPurchaseTime());
        }
        return games;
    }

    @Override
    public boolean launchGame(Long gameId) {
        Game game = libraryMapper.selectGameById(gameId);
        if (game == null) {
            return false;
        }
        // 模拟启动游戏
        return true;
    }

    @Override
    public boolean installGame(Long gameId) {
        // 假设当前登录用户ID为1，实际应用中应从Session或SecurityContext获取
        Long userId = 1L;
        
        // 检查游戏是否存在
        Game game = libraryMapper.selectGameById(gameId);
        if (game == null) {
            return false;
        }
        
        // 检查用户是否已购买该游戏
        int count = userGameLibraryMapper.checkUserPurchasedGame(userId, gameId);
        if (count == 0) {
            return false;
        }
        
        // 更新安装状态为已安装
        int result = userGameLibraryMapper.updateInstallStatus(userId, gameId, 1);
        return result > 0;
    }

    @Override
    public boolean uninstallGame(Long gameId) {
        // 假设当前登录用户ID为1，实际应用中应从Session或SecurityContext获取
        Long userId = 1L;
        
        // 检查游戏是否存在
        Game game = libraryMapper.selectGameById(gameId);
        if (game == null) {
            return false;
        }
        
        // 检查用户是否已购买该游戏
        int count = userGameLibraryMapper.checkUserPurchasedGame(userId, gameId);
        if (count == 0) {
            return false;
        }
        
        // 更新安装状态为未安装
        int result = userGameLibraryMapper.updateInstallStatus(userId, gameId, 0);
        return result > 0;
    }

    @Override
    public boolean toggleFavorite(Long gameId) {
        // 假设当前登录用户ID为1，实际应用中应从Session或SecurityContext获取
        Long userId = 1L;
        
        // 检查游戏是否存在
        Game game = libraryMapper.selectGameById(gameId);
        if (game == null) {
            return false;
        }
        
        // 检查用户是否已购买该游戏
        int count = userGameLibraryMapper.checkUserPurchasedGame(userId, gameId);
        if (count == 0) {
            return false;
        }
        
        // 获取当前收藏状态
        UserGameLibrary userGameLibrary = userGameLibraryMapper.getUserGameLibrary(userId, gameId);
        if (userGameLibrary == null) {
            return false;
        }
        
        // 切换收藏状态
        int newFavoriteStatus = userGameLibrary.getIsFavorite() == 1 ? 0 : 1;
        int result = userGameLibraryMapper.updateFavoriteStatus(userId, gameId, newFavoriteStatus);
        return result > 0;
    }
}