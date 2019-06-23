package cn.itweknow.springbootmultidatasource.controller;

import cn.itweknow.springbootmultidatasource.service.UserService;
import cn.itweknow.springbootmultidatasource.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ganchaoyang
 * @date 2019/1/22 19:15
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-by-id")
    public User getUserById(@RequestParam("id") int id) {
        return userService.findUserWithCityById(id);
    }

}
