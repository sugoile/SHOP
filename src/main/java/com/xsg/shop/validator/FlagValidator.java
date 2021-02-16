package com.xsg.shop.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 用户验证状态是否在指定范围内的注解
 * Created by xsg on 2020/2/23 16:02
 */

@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FlagValidatorClass.class)
public @interface FlagValidator {
    //注解的传入value方法 默认为空
    String[] value() default {};

    String message() default "not find this flag";

    /**
     * groups 和 payload是验证必带的参数
     *
     * @return
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
