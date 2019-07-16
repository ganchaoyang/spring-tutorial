package cn.itweknow.sbdatasource.mapper;

import cn.itweknow.sbdatasource.dao.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User selectById(@Param("id") Integer id);

}
