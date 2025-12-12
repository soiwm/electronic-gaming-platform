package com.example.electronicgamingplatform.util;

import com.example.electronicgamingplatform.entity.Customer;
import com.example.electronicgamingplatform.service.CustomerService;

/**
 * 用户上下文工具类，用于获取当前登录用户信息
 */
public class UserContext {
    private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();
    private static final ThreadLocal<String> currentUserPhone = new ThreadLocal<>();
    
    /**
     * 设置当前用户ID
     */
    public static void setCurrentUserId(Long userId) {
        currentUserId.set(userId);
    }
    
    /**
     * 获取当前用户ID
     */
    public static Long getCurrentUserId() {
        return currentUserId.get();
    }
    
    /**
     * 设置当前用户手机号
     */
    public static void setCurrentUserPhone(String phone) {
        currentUserPhone.set(phone);
    }
    
    /**
     * 获取当前用户手机号
     */
    public static String getCurrentUserPhone() {
        return currentUserPhone.get();
    }
    
    /**
     * 清除当前用户信息
     */
    public static void clear() {
        currentUserId.remove();
        currentUserPhone.remove();
    }
}