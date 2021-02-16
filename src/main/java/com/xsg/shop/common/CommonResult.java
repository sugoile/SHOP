package com.xsg.shop.common;

import lombok.Getter;
import lombok.Setter;

/**
 * 一些controller通用返回对象
 * Created by xsg on 2020/2/14 14:22
 */
@Getter
@Setter
public class CommonResult <T>{

    private Long Code;
    private  String message;
    private  T data;

    protected CommonResult(){};

    protected CommonResult(Long code, String message, T data) {
        Code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * success
     * 成功返回的结果
     */

    public static <T> CommonResult<T> success(T data){
            return new CommonResult<T>(ResultCode.SUCCESS.GetCode(),ResultCode.SUCCESS.GetMessage(),data);
    }


    /**
     * failed
     * 失败返回的结果(有提示信息时)
     */
    public static <T> CommonResult<T> failed(String message){
        return new CommonResult<T>(ResultCode.FAILED.GetCode(),message,null);
    }

    /**
     *failed
     * 失败返回结果(没有提示信息时)
     */
    public static <T> CommonResult<T> failed(){
        return new CommonResult<T>(ResultCode.FAILED.GetCode(),ResultCode.FAILED.GetMessage(),null);
    }

    /**
     * failed
     * 传入数据错误导致的失败(有错误提示)
     */
    public static <T> CommonResult<T> validateFailed(T data){
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.GetCode(), ResultCode.VALIDATE_FAILED.GetMessage(), data);
    }

    /**
     * failed
     * 传入数据错误导致的失败(没有错误提示)
     */
    public static <T> CommonResult<T> validateFailed(){
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.GetCode(), ResultCode.VALIDATE_FAILED.GetMessage(),null);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.GetCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.GetCode(), ResultCode.UNAUTHORIZED.GetMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.GetCode(), ResultCode.FORBIDDEN.GetMessage(), data);
    }

}
