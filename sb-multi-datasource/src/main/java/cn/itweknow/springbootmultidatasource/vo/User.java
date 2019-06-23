package cn.itweknow.springbootmultidatasource.vo;

import cn.itweknow.springbootmultidatasource.dao.City;

/**
 * @author ganchaoyang
 * @date 2019/1/22 19:03
 * @description
 */
public class User {

    private Integer id;

    private String userName;

    private String description;

    private City city;


    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public User setDescription(String description) {
        this.description = description;
        return this;
    }

    public City getCity() {
        return city;
    }

    public User setCity(City city) {
        this.city = city;
        return this;
    }
}
