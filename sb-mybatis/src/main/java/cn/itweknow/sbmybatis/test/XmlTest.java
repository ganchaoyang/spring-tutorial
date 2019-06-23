package cn.itweknow.sbmybatis.test;

import cn.itweknow.sbmybatis.mapper.ClazzMapper;
import cn.itweknow.sbmybatis.mapper.StudentMapper;
import cn.itweknow.sbmybatis.model.dao.ClazzExtend;
import cn.itweknow.sbmybatis.model.dao.Student;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ganchaoyang
 * @date 2019/6/1610:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class XmlTest {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ClazzMapper clazzMapper;

    @Test
    public void testInsert() {
        Student student = new Student();
        student.setAge(20);
        student.setName("张三");
        student.setNumber("201912");
        student.setClazzId(1);
        studentMapper.insert(student);
        System.out.println(student.getId());
    }


    @Test
    public void testSelectWithStudentsById() {
        ClazzExtend result = clazzMapper.selectWithStudentsById(1);
        System.out.println(JSON.toJSONString(result));
    }


}
