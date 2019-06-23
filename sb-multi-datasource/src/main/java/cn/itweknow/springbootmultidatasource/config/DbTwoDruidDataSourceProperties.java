package cn.itweknow.springbootmultidatasource.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ganchaoyang
 * @date 2019/1/22 18:43
 * @description
 */
@ConfigurationProperties(prefix = "dbtwo.druid.datasource")
public class DbTwoDruidDataSourceProperties extends DruidDataSourceProperties{
}
