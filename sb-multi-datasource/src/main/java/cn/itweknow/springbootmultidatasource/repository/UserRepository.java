package cn.itweknow.springbootmultidatasource.repository;

import cn.itweknow.springbootmultidatasource.dao.User;
import cn.itweknow.springbootmultidatasource.dao.mapper.dbtwo.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author ganchaoyang
 * @date 2019/1/22 18:59
 * @description
 */
@Repository("userRepository")
public class UserRepository {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public User findById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

}
