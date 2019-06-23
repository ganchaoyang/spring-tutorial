package cn.itweknow.sbmybatis.mapper;


import cn.itweknow.sbmybatis.model.dao.ClazzExtend;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface ClazzMapper {


    /**
     * 根据id查询班级，并带出其学生信息
     * @param id
     * @return
     */
    ClazzExtend selectWithStudentsById(int id);

    @Select("select * from t_clazz where id = #{id}")
    @Results(id = "link", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(property = "students", column = "id", javaType = List.class,
                    many = @Many(fetchType = FetchType.EAGER, select = "cn.itweknow.sbmybatis.mapper.StudentMapper.findByClazzId"))
    })
    ClazzExtend annoSelectWithStudentsById(Integer id);
}