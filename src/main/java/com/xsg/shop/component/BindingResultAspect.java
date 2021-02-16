package com.xsg.shop.component;

import com.xsg.shop.common.CommonResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * BingdingResult的一些切面操作
 * Created by xsg on 2020/2/22 23:07
 */
@Aspect
@Component
@Order(2)
public class BindingResultAspect {

    //关于pointcut方法的书写
    @Pointcut("execution(public * com.xsg.shop.controller.*.*(..))")
    public void BindingResult(){
    }

    //环绕通知
    @Around("BindingResult()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
        //接收传来的通知
        Object[] args = joinPoint.getArgs();

        for(Object arg : args){
            //判断传来的是否是BindingResult类型
            if(arg instanceof BindingResult){
                BindingResult result = (BindingResult) arg;

                if(result.hasErrors()){
                   FieldError error =  result.getFieldError();

                   if(error != null){
                       return CommonResult.validateFailed(error.getDefaultMessage());
                   }
                   else{
                       return CommonResult.validateFailed();
                   }
                }
            }
        }

        //其余放行
        return joinPoint.proceed();
    }

}
