package com.example.electronicgamingplatform.service.impl;

import com.example.electronicgamingplatform.entity.Game;
import com.example.electronicgamingplatform.entity.UserGameLibrary;
import com.example.electronicgamingplatform.mapper.LibraryMapper;
import com.example.electronicgamingplatform.mapper.CustomerMapper;
import com.example.electronicgamingplatform.service.LibraryService;
import com.example.electronicgamingplatform.vo.GameLibraryVO;
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
    private CustomerMapper customerMapper;

    @Override
    public List<GameLibraryVO> getUserGames(Long userId) {
        List<GameLibraryVO> games = libraryMapper.selectUserGames(userId);
        logger.info("获取用户游戏库列表，共{}个游戏", games.size());
        for (GameLibraryVO game : games) {
            logger.info("游戏ID: {}, 名称: {}, 购买时间: {}", game.getId(), game.getName(), game.getPurchaseTime());
        }
        return games;
    }

    @Override
    public boolean launchGame(Long userId, Long gameId) {
        GameLibraryVO game = libraryMapper.selectGameById(gameId, userId);
        if (game == null) {
            return false;
        }
        
        // 检查用户是否已购买该游戏
        if (!checkUserPurchasedGame(userId, gameId)) {
            return false;
        }
        
        // 模拟启动游戏
        return true;
    }

    @Override
    public boolean installGame(Long userId, Long gameId) {
        // 检查游戏是否存在
        GameLibraryVO game = libraryMapper.selectGameById(gameId, userId);
        if (game == null) {
            return false;
        }
        
        // 检查用户是否已购买该游戏
        if (!checkUserPurchasedGame(userId, gameId)) {
            return false;
        }
        
        // 更新安装状态为已安装
        int result = libraryMapper.updateInstallStatus(userId, gameId, 1);
        return result > 0;
    }

    @Override
    public boolean uninstallGame(Long userId, Long gameId) {
        // 检查游戏是否存在
        GameLibraryVO game = libraryMapper.selectGameById(gameId, userId);
        if (game == null) {
            return false;
        }
        
        // 检查用户是否已购买该游戏
        if (!checkUserPurchasedGame(userId, gameId)) {
            return false;
        }
        
        // 更新安装状态为未安装
        int result = libraryMapper.updateInstallStatus(userId, gameId, 0);
        return result > 0;
    }

    @Override
    public boolean toggleFavorite(Long userId, Long gameId) {
        // 检查游戏是否存在
        GameLibraryVO game = libraryMapper.selectGameById(gameId, userId);
        if (game == null) {
            return false;
        }
        
        // 检查用户是否已购买该游戏
        if (!checkUserPurchasedGame(userId, gameId)) {
            return false;
        }
        
        // 获取当前收藏状态
        UserGameLibrary userGameLibrary = libraryMapper.getUserGameLibrary(userId, gameId);
        if (userGameLibrary == null) {
            return false;
        }
        
        // 切换收藏状态
        int newFavoriteStatus = userGameLibrary.getIsFavorite() == 1 ? 0 : 1;
        int result = libraryMapper.updateFavoriteStatus(userId, gameId, newFavoriteStatus);
        return result > 0;
    }

    @Override
    public boolean addUserGameLibrary(UserGameLibrary userGameLibrary) {
        if (userGameLibrary == null) {
            return false;
        }
        
        // 如果用户名为空，根据用户ID查询用户名
        if (userGameLibrary.getUserName() == null || userGameLibrary.getUserName().isEmpty()) {
            String userName = customerMapper.selectCustomerById(userGameLibrary.getUserId()).getName();
            userGameLibrary.setUserName(userName);
        }
        
        int result = libraryMapper.addUserGameLibrary(userGameLibrary);
        return result > 0;
    }

    @Override
    public UserGameLibrary getUserGameLibrary(Long userId, Long gameId) {
        if (userId == null || gameId == null) {
            return null;
        }
        return libraryMapper.getUserGameLibrary(userId, gameId);
    }

    @Override
    public boolean checkUserPurchasedGame(Long userId, Long gameId) {
        if (userId == null || gameId == null) {
            return false;
        }
        int count = libraryMapper.checkUserPurchasedGame(userId, gameId);
        return count > 0;
    }

    @Override
    public GameLibraryVO selectGameById(Long userId, Long gameId) {
        if (userId == null || gameId == null) {
            return null;
        }
        return libraryMapper.selectGameById(gameId, userId);
    }
}