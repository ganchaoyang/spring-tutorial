package cn.itweknow.sbmybatis.test;

import cn.itweknow.sbmybatis.mapper.ClazzMapper;
import cn.itweknow.sbmybatis.mapper.StudentMapper;
import cn.itweknow.sbmybatis.model.dao.ClazzExtend;
import cn.itweknow.sbmybatis.model.dao.Student;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author ganchaoyang
 * @date 2019/6/2215:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnoTest {

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
        studentMapper.annoInsert(student);
        System.out.println(student.getId());
    }

    @Test
    public void testUpdateIgnoreNull() {
        Student student = new Student();
        student.setAge(22);
        student.setName("张三");
        student.setNumber("201912");
        student.setClazzId(1);
        student.setId(10);
        studentMapper.annoUpdateIgnoreNullById(student);
    }

    @Test
    public void testSelectById() {
        Student student = studentMapper.annoSelectById(9);
        System.out.println(JSON.toJSONString(student));
    }

    @Test
    public void deleteById() {
        studentMapper.annoDeleteById(10);
    }

    @Test
    public void testSelectPage() {
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(1, 10);
        //紧跟着的第一个select方法会被分页
        List<Student> list = studentMapper.findAll();
        PageInfo page = new PageInfo(list);
        System.out.println(JSON.toJSONString(page));
    }


    @Test
    public void testSelectComplex() {
        ClazzExtend clazzExtend = clazzMapper.annoSelectWithStudentsById(1);
        System.out.println(JSON.toJSONString(clazzExtend));
    }

}
