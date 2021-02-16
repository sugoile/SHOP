package com.xsg.shop.dto;

import com.xsg.shop.validator.FlagValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * 后台管理员参数类
 * Created by xsg on 2020/3/26 10:40
 */
@Setter
@Getter
public class UmsAdminparam {
    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "密码不能为空")
    private String password;
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式不合法")
    private String email;
    @ApiModelProperty(value = "备注")
    private String note;
    @ApiModelProperty(value = "是否开启使用(0关闭，1开启)")
    @FlagValidator(value = {"0", "1"}, message = "开启使用状态不正确")
    private Integer status;
}
