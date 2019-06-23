package cn.itweknow.sbmybatis.mapper;

import cn.itweknow.sbmybatis.model.dao.Student;

import java.util.Map;

/**
 * @author ganchaoyang
 * @date 2019/6/2215:53
 */
public class StudentDAOProvider {


    public String updateIgnoreNullByPrimaryKey(Map<String, Object> map) throws Exception {
        Student student = (Student) map.get("student");

        if (student == null || student.getId() == null) {
            throw new Exception("the primaryKey can not be null.");
        }
        // 拼装sql语句
        StringBuilder updateStrSb = new StringBuilder("update t_student set ");
        StringBuilder setStrSb = new StringBuilder();
        if (student.getName() != null) {
            setStrSb.append("name = #{student.name},");
        }
        if (student.getNumber() != null) {
            setStrSb.append("number = #{student.number},");
        }
        if (student.getAge() != null) {
            setStrSb.append("age = #{student.age},");
        }
        if (student.getClazzId() != null) {
            setStrSb.append("clazz_id = #{student.clazzId},");
        }

        if (setStrSb.length() > 0) {
            updateStrSb.append(setStrSb.substring(0, setStrSb.length()-1)).append(" where id = #{student.id}");
        } else {
            throw new Exception("none element to update.");
        }

        return updateStrSb.toString();
    }

}
