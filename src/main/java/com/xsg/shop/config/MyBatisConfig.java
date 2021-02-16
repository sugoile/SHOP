package com.xsg.shop.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
/**
 * MyBatis配置文件
 * Created by xsg on 2020/2/12 14:57
 */
@Configuration
@MapperScan({"com.xsg.shop.mbg.mapper","com.xsg.shop.dao"})
public class MyBatisConfig {
}
