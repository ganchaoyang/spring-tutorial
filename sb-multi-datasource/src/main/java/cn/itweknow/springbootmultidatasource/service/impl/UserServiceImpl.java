package cn.itweknow.springbootmultidatasource.service.impl;

import cn.itweknow.springbootmultidatasource.dao.City;
import cn.itweknow.springbootmultidatasource.repository.CityRepository;
import cn.itweknow.springbootmultidatasource.repository.UserRepository;
import cn.itweknow.springbootmultidatasource.service.UserService;
import cn.itweknow.springbootmultidatasource.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ganchaoyang
 * @date 2019/1/22 19:11
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public User findUserWithCityById(int id) {
        User result = new User();
        cn.itweknow.springbootmultidatasource.dao.User user = userRepository.findById(id);
        result.setId(user.getId()).setUserName(user.getUserName()).setDescription(user.getDescription());
        // 查询城市。
        if (null != user.getCityId()) {
            City city = cityRepository.findById(user.getCityId());
            result.setCity(city);
        }
        return result;
    }
}
