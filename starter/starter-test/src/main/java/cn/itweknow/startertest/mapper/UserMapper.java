package cn.itweknow.startertest.mapper;

import cn.itweknow.startertest.dao.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User selectById(@Param("id") Integer id);

}
