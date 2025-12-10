package com.example.electronicgamingplatform.controller;

import com.example.electronicgamingplatform.common.Result;
import com.example.electronicgamingplatform.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

// 接口前缀：/statistics（对应前端 /api/statistics/* → 代理后 → /statistics/*）
@RestController
@RequestMapping("/statistics")
class StatisticsController {

    @Autowired
    private OrderService orderService;

    /**
     * 1. 获取销量排行榜（前端统计报表）
     * 请求方式：GET
     * 接口路径：/statistics/sales-ranking
     */
    @GetMapping("/sales-ranking")
    public Result<List<Map<String, Object>>> getSalesRanking() {
        List<Map<String, Object>> salesRanking = orderService.getSalesRanking();
        return Result.success(salesRanking);
    }

    /**
     * 2. 获取用户年龄分级统计（前端统计报表）
     * 请求方式：GET
     * 接口路径：/statistics/age-distribution
     */
    @GetMapping("/age-distribution")
    public Result<Map<String, Integer>> getAgeDistribution() {
        Map<String, Integer> ageDistribution = orderService.getAgeDistribution();
        return Result.success(ageDistribution);
    }
}
