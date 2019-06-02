package com.lucky.spring_jt.dao;

import com.lucky.spring_jt.dao.impl.StudentDaoImpl;
import com.lucky.spring_jt.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class SpringDemo {

    @Resource(name="studentDaoImpl")
    private  StudentDaoImpl studentDao;

    @Test
    public void demo(){
        List<Student> students= studentDao.selectAll();
        System.out.println(students);
    }


}
