package com.example.electronicgamingplatform.service.impl;

import com.example.electronicgamingplatform.entity.UserGameLibrary;
import com.example.electronicgamingplatform.mapper.UserGameLibraryMapper;
import com.example.electronicgamingplatform.mapper.CustomerMapper;
import com.example.electronicgamingplatform.service.UserGameLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户游戏库服务实现类
 */
@Service
public class UserGameLibraryServiceImpl implements UserGameLibraryService {

    @Autowired
    private UserGameLibraryMapper userGameLibraryMapper;
    
    @Autowired
    private CustomerMapper customerMapper;

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
        
        int result = userGameLibraryMapper.addUserGameLibrary(userGameLibrary);
        return result > 0;
    }

    @Override
    public UserGameLibrary getUserGameLibrary(Long userId, Long gameId) {
        if (userId == null || gameId == null) {
            return null;
        }
        return userGameLibraryMapper.getUserGameLibrary(userId, gameId);
    }

    @Override
    public List<UserGameLibrary> getUserGameLibraryByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        return userGameLibraryMapper.getUserGameLibraryByUserId(userId);
    }

    @Override
    public boolean updateInstallStatus(Long userId, Long gameId, Integer isInstalled) {
        if (userId == null || gameId == null || isInstalled == null) {
            return false;
        }
        int result = userGameLibraryMapper.updateInstallStatus(userId, gameId, isInstalled);
        return result > 0;
    }

    @Override
    public boolean updateFavoriteStatus(Long userId, Long gameId, Integer isFavorite) {
        if (userId == null || gameId == null || isFavorite == null) {
            return false;
        }
        int result = userGameLibraryMapper.updateFavoriteStatus(userId, gameId, isFavorite);
        return result > 0;
    }

    @Override
    public boolean checkUserPurchasedGame(Long userId, Long gameId) {
        if (userId == null || gameId == null) {
            return false;
        }
        int count = userGameLibraryMapper.checkUserPurchasedGame(userId, gameId);
        return count > 0;
    }
}