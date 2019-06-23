package cn.itweknow.sbmybatis.mapper;


import cn.itweknow.sbmybatis.model.dao.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    /**
     * 新增
     * @param student
     * @return
     */
    int insert(Student student);


    @Insert("insert into t_student(name,age,clazz_id,number) values " +
            "(#{name},#{age},#{clazzId},#{number})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int annoInsert(Student student);


    /**
     * 更新
     * @param student
     * @return
     */
    int updateIgnoreNullById(@Param("student") Student student);


    @UpdateProvider(type = StudentDAOProvider.class, method = "updateIgnoreNullByPrimaryKey")
    int annoUpdateIgnoreNullById(@Param("student") Student student);

    /**
     * 查询
     * @param id
     * @return
     */
    Student selectById(@Param("id") int id);


    @Select("select * from t_student where id = #{id}")
    Student annoSelectById(Integer id);

    @Select("select *from t_student")
    List<Student> findAll();

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(@Param("id") int id);


    @Delete("delete from t_student where id = #{id}")
    int annoDeleteById(Integer id);


    @Select("select * from t_student where clazz_id = #{clazzId}")
    List<Student> findByClazzId(Integer clazzId);


}