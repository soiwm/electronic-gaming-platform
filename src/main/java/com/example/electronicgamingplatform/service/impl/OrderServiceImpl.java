package com.example.electronicgamingplatform.service.impl;

import com.example.electronicgamingplatform.entity.Order;
import com.example.electronicgamingplatform.entity.Game;
import com.example.electronicgamingplatform.entity.Customer;
import com.example.electronicgamingplatform.entity.UserGameLibrary;
import com.example.electronicgamingplatform.vo.OrderVO;
import com.example.electronicgamingplatform.mapper.OrderMapper;
import com.example.electronicgamingplatform.mapper.GameMapper;
import com.example.electronicgamingplatform.mapper.CustomerMapper;
import com.example.electronicgamingplatform.service.OrderService;
import com.example.electronicgamingplatform.service.GameService;
import com.example.electronicgamingplatform.service.UserGameLibraryService;
import com.example.electronicgamingplatform.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 订单服务实现类（调用Mapper层，解耦数据存储）
 */
@Service
public class OrderServiceImpl implements OrderService {

    // 注入Mapper层（依赖注入）
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private GameMapper gameMapper;
    
    @Autowired
    private CustomerMapper customerMapper;
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    private UserGameLibraryService userGameLibraryService;

    @Override
    public List<OrderVO> getAllOrders() {
        List<OrderVO> orderVOList = new ArrayList<>();
        
        // 获取所有订单
        List<Order> orders = orderMapper.getAllOrders();
        
        // 转换为OrderVO并关联游戏和客户信息
        for (Order order : orders) {
            OrderVO orderVO = new OrderVO();
            
            // 复制订单基本信息
            orderVO.setId(order.getId());
            orderVO.setAmount(order.getAmount());
            orderVO.setCreateTime(order.getCreateTime());
            orderVO.setUpdateTime(order.getUpdateTime());
            
            // 关联客户信息
            Customer customer = customerMapper.selectCustomerById(order.getCustomerId());
            if (customer != null) {
                orderVO.setCustomerName(customer.getName());
            } else {
                orderVO.setCustomerName("未知客户");
            }
            
            // 关联游戏信息
            Game game = gameMapper.selectGameById(order.getGameId());
            if (game != null) {
                orderVO.setGameName(game.getName());
            } else {
                orderVO.setGameName("未知游戏");
            }
            
            orderVOList.add(orderVO);
        }
        
        return orderVOList;
    }

    @Override
    public Order getOrderById(Long id) {
        // 调用Mapper层查询
        return orderMapper.getOrderById(id);
    }

    @Override
    public boolean addOrder(Order order) {
        // 1. 验证必填项
        if (order.getCustomerId() == null || order.getGameId() == null) {
            return false;
        }

        // 2. 验证客户和游戏是否存在
        Customer customer = customerMapper.selectCustomerById(order.getCustomerId());
        if (customer == null) {
            throw new RuntimeException("客户ID不存在: " + order.getCustomerId());
        }
        
        Game game = gameMapper.selectGameById(order.getGameId());
        if (game == null) {
            throw new RuntimeException("游戏ID不存在: " + order.getGameId());
        }

        // 3. 创建新的订单对象，避免传入ID
        Order newOrder = new Order();
        newOrder.setCustomerId(order.getCustomerId());
        newOrder.setGameId(order.getGameId());
        
        // 4. 设置时间
        newOrder.setCreateTime(LocalDateTime.now());
        newOrder.setUpdateTime(LocalDateTime.now());

        // 5. 如果未提供金额，使用游戏价格
        if (order.getAmount() == null) {
            if (game.getPrice() != null) {
                newOrder.setAmount(game.getPrice());
            } else {
                newOrder.setAmount(new BigDecimal("49.99")); // 默认价格
            }
        } else {
            newOrder.setAmount(order.getAmount());
        }

        // 6. 调用Mapper层新增
        int result = orderMapper.addOrder(newOrder);
        return result > 0;
    }

    @Override
    public boolean updateOrder(Order order) {
        // 1. 验证订单是否存在
        if (order.getId() == null || orderMapper.getOrderById(order.getId()) == null) {
            return false;
        }

        Order existingOrder = orderMapper.getOrderById(order.getId());

        // 2. 更新字段
        if (order.getCustomerId() != null) {
            existingOrder.setCustomerId(order.getCustomerId());
        }
        if (order.getGameId() != null) {
            existingOrder.setGameId(order.getGameId());
        }
        if (order.getAmount() != null) {
            existingOrder.setAmount(order.getAmount());
        }
//        if (order.getStatus() != null) {
//            existingOrder.setStatus(order.getStatus());
//        }
        existingOrder.setUpdateTime(LocalDateTime.now());

        // 3. 调用Mapper层更新
        int result = orderMapper.updateOrder(existingOrder);
        return result > 0;
    }

    @Override
    public boolean removeOrderById(Long id) {
        // 1. 验证ID
        if (id == null || orderMapper.getOrderById(id) == null) {
            return false;
        }

        // 2. 调用Mapper层删除
        int result = orderMapper.deleteOrderById(id);
        return result > 0;
    }

    @Override
    public List<Map<String, Object>> getSalesRanking() {
        // 1. 从Mapper层获取按游戏ID分组的销量统计
        List<Map<String, Object>> gameSalesList = orderMapper.getOrderGroupByGameId();

        // 2. 为每个游戏添加游戏名称
        for (Map<String, Object> item : gameSalesList) {
            Long gameId = ((Number) item.get("gameId")).longValue();
            
            // 获取真实游戏名称
            Game game = gameService.getGameById(gameId);
            String gameName = game != null ? game.getName() : "未知游戏";
            item.put("gameName", gameName);
        }

        // 3. 返回前10个游戏
        return gameSalesList.subList(0, Math.min(10, gameSalesList.size()));
    }

    @Override
    public Map<String, Integer> getAgeDistribution() {
        // 从数据库获取真实年龄分布数据
        Map<String, Object> ageData = customerMapper.getAgeDistribution();
        
        // 转换为前端需要的格式
        Map<String, Integer> ageDistribution = new HashMap<>();
        
        // 处理嵌套Map结构
        Map<String, Object> actualAgeData;
        if (ageData != null && ageData.size() > 0) {
            // 如果ageData包含嵌套Map，获取其值
            Object firstValue = ageData.values().iterator().next();
            if (firstValue instanceof Map) {
                actualAgeData = (Map<String, Object>) firstValue;
            } else {
                actualAgeData = ageData;
            }
        } else {
            actualAgeData = ageData;
        }
        
        // 标准化数据键名，确保大小写不影响结果
        Map<String, Object> normalizedData = new HashMap<>();
        if (actualAgeData != null) {
            for (Map.Entry<String, Object> entry : actualAgeData.entrySet()) {
                normalizedData.put(entry.getKey().toLowerCase(), entry.getValue());
            }
        }
        
        // 提取年龄分布数据
        ageDistribution.put("under18", ((Number) normalizedData.getOrDefault("under18", 0)).intValue());
        ageDistribution.put("age18to35", ((Number) normalizedData.getOrDefault("age18to35", 0)).intValue());
        ageDistribution.put("age35to50", ((Number) normalizedData.getOrDefault("age35to50", 0)).intValue());
        ageDistribution.put("over50", ((Number) normalizedData.getOrDefault("over50", 0)).intValue());
        
        return ageDistribution;
    }

    @Override
    public List<OrderVO> getUserOrders() {
        System.out.println("OrderService - getUserOrders方法被调用");
        
        List<OrderVO> orderVOList = new ArrayList<>();
        
        // 从UserContext获取当前登录用户ID
        Long customerId = UserContext.getCurrentUserId();
        System.out.println("OrderService - 从UserContext获取的用户ID: " + customerId);
        
        if (customerId == null) {
            // 如果用户未登录，返回空列表
            System.out.println("OrderService - 用户未登录，返回空列表");
            return orderVOList;
        }
        
        System.out.println("OrderService - 开始查询用户ID为 " + customerId + " 的订单");
        List<Order> orders = orderMapper.getOrdersByCustomerId(customerId);
        System.out.println("OrderService - 查询到 " + orders.size() + " 条订单记录");
        
        // 转换为OrderVO并关联游戏和客户信息
        for (Order order : orders) {
            OrderVO orderVO = new OrderVO();
            
            // 复制订单基本信息
            orderVO.setId(order.getId());
            orderVO.setAmount(order.getAmount());
            orderVO.setCreateTime(order.getCreateTime());
            orderVO.setUpdateTime(order.getUpdateTime());
            
            // 关联客户信息
            Customer customer = customerMapper.selectCustomerById(order.getCustomerId());
            if (customer != null) {
                orderVO.setCustomerName(customer.getName());
            } else {
                orderVO.setCustomerName("未知客户");
            }
            
            // 关联游戏信息
            Game game = gameMapper.selectGameById(order.getGameId());
            if (game != null) {
                orderVO.setGameName(game.getName());
            } else {
                orderVO.setGameName("未知游戏");
            }
            
            orderVOList.add(orderVO);
        }
        
        System.out.println("OrderService - 返回 " + orderVOList.size() + " 条订单VO记录");
        return orderVOList;
    }

    @Override
    @Transactional
    public boolean createOrderAndAddToLibrary(Long customerId, Long gameId) {
        try {
            // 1. 检查用户是否已经购买过该游戏
            boolean alreadyPurchased = userGameLibraryService.checkUserPurchasedGame(customerId, gameId);
            if (alreadyPurchased) {
                System.out.println("用户已购买过该游戏，无需重复购买");
                return false;
            }
            
            // 2. 创建订单
            Order order = new Order();
            order.setCustomerId(customerId);
            order.setGameId(gameId);
            
            // 获取游戏价格
            Game game = gameMapper.selectGameById(gameId);
            if (game == null) {
                System.out.println("游戏不存在，ID: " + gameId);
                return false;
            }
            
            order.setAmount(game.getPrice());
            order.setCreateTime(LocalDateTime.now());
            order.setUpdateTime(LocalDateTime.now());
            
            int orderResult = orderMapper.addOrder(order);
            if (orderResult <= 0) {
                System.out.println("创建订单失败");
                return false;
            }
            
            // 3. 添加到用户游戏库
            Customer customer = customerMapper.selectCustomerById(customerId);
            if (customer == null) {
                System.out.println("客户不存在，ID: " + customerId);
                return false;
            }
            
            UserGameLibrary userGameLibrary = new UserGameLibrary();
            userGameLibrary.setUserId(customerId);
            userGameLibrary.setUserName(customer.getName());
            userGameLibrary.setGameId(gameId);
            userGameLibrary.setIsInstalled(0); // 默认未安装
            userGameLibrary.setIsFavorite(0); // 默认未收藏
            userGameLibrary.setPurchaseTime(LocalDateTime.now()); // 设置购买时间
            userGameLibrary.setCreateTime(LocalDateTime.now());
            userGameLibrary.setUpdateTime(LocalDateTime.now());
            
            boolean libraryResult = userGameLibraryService.addUserGameLibrary(userGameLibrary);
            if (!libraryResult) {
                System.out.println("添加到用户游戏库失败");
                return false;
            }
            
            System.out.println("创建订单并添加到用户游戏库成功");
            return true;
            
        } catch (Exception e) {
            System.out.println("创建订单并添加到用户游戏库时发生异常: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}