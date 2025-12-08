package com.example.electronicgamingplatform.common;

import lombok.Data;  // 需导入 lombok 依赖（简化 getter/setter）

// 统一返回结果类（T 为返回数据类型）
@Data
public class Result<T> {
    // 状态码：200=成功，500=失败，404=未找到等
    private int code;
    // 提示信息
    private String msg;
    // 返回数据
    private T data;

    // 静态方法：快速创建成功响应（带数据）
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    // 静态方法：快速创建成功响应（无数据）
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    // 静态方法：快速创建失败响应
    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}