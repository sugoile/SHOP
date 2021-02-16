package com.xsg.shop.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 用于实现状态的处理返回类
 * Created by xsg on 2020/2/23 16:06
 */
public class FlagValidatorClass implements ConstraintValidator<FlagValidator,Integer> {
    private String[] values;

    //初始化
    @Override
    public void initialize(FlagValidator constraintAnnotation) {
        //将注解内配置的值赋值给临时变量
        this.values = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        //为true则状态无误
        boolean isvalid = false;

        //验证处理
        //没有传参的情况(即使用默认值)
        if(value == null){
            return true;
        }
        for(int i = 0; i < values.length; i++){
            if(values[i].equals(String.valueOf(value))){
                isvalid = true;
                break;
            }
        }

        return isvalid;
    }
}
