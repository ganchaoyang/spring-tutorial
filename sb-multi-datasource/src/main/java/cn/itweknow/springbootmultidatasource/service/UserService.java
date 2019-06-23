package cn.itweknow.springbootmultidatasource.service;

import cn.itweknow.springbootmultidatasource.vo.User;

/**
 * @author ganchaoyang
 * @date 2019/1/22 19:09
 * @description
 */
public interface UserService {

    /**
     * 根据用户id查询用户信息（带城市信息）
     * @param id
     * @return
     */
    User findUserWithCityById(int id);

}
