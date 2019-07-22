package cn.itweknow.mybatisconfigspringbootstarter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mybatis.config")
public class MyBatisProperties {


    private String mapperXmlLocation;


    public String getMapperXmlLocation() {
        return mapperXmlLocation;
    }

    public void setMapperXmlLocation(String mapperXmlLocation) {
        this.mapperXmlLocation = mapperXmlLocation;
    }

}
