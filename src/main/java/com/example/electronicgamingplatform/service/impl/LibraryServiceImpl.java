package com.example.electronicgamingplatform.service.impl;

import com.example.electronicgamingplatform.entity.Game;
import com.example.electronicgamingplatform.mapper.LibraryMapper;
import com.example.electronicgamingplatform.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游戏库服务实现类
 */
@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private LibraryMapper libraryMapper;

    @Override
    public List<Game> getUserGames() {
        return libraryMapper.selectUserGames();
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
    public boolean uninstallGame(Long gameId) {
        Game game = libraryMapper.selectGameById(gameId);
        if (game == null) {
            return false;
        }
        // 模拟卸载游戏
        return true;
    }
}