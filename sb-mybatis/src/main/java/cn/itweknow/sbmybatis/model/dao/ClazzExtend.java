package cn.itweknow.sbmybatis.model.dao;

import java.util.List;

/**
 * @author ganchaoyang
 * @date 2019/6/1611:11
 */
public class ClazzExtend extends Clazz {

    List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
