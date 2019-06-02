import com.lucky.spring_jt.entity.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test {
    private JdbcTemplate jdbcTemplate;
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        jdbcTemplate = (JdbcTemplate)context.getBean("jdbcTemplate");
    }
    @org.junit.Test
    public void testExecute(){
        jdbcTemplate.execute("create table user1(id int,name varchar(20))");
    }

    @org.junit.Test
    public void testUpdate(){
        String sql = "insert into student(id, name,sex) values(?,?,?)";
        jdbcTemplate.update(sql,new Object[]{1003,"张飞","男"});
    }

    @org.junit.Test
    public void testUpdate2(){
        String sql = "update student set sex=? where id=?";
        jdbcTemplate.update(sql,"女",1003);
    }

    @org.junit.Test
    public void testBatchUpdate(){
        String[] sqls={
                "insert into student(id, name,sex) values(2001,'关羽','女')",
                "insert into student(id, name,sex) values(3002, '刘备','男')",
                "update student set sex='女' where id=2001"
        };
        jdbcTemplate.batchUpdate(sqls);
    }

    @org.junit.Test
    public void testBatchUpdate2(){
        String sql = "insert into selection(student,course) values(?,?)";
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[]{1003,1003});
        list.add(new Object[]{1003,1001});
        jdbcTemplate.batchUpdate(sql,list);
    }

    @org.junit.Test
    public void testQuerySimple1(){
        String sql = "select count(*) from student";
        int count = jdbcTemplate.queryForObject(sql,Integer.class);
        System.out.println(count);
    }

    @org.junit.Test
    public void testQuerySimple2(){
        String sql = "select name from student where sex=? and id=?";
        String names = jdbcTemplate.queryForObject(sql, new Object[]{"女", 1003}, String.class);
        System.out.println(names);
    }

    @org.junit.Test
    public void testQuerySimple3(){
        String sql = "select name from student where sex=?";
        List<String> names = jdbcTemplate.queryForList(sql,String.class,"女");
        System.out.println(names);
    }

    @org.junit.Test
    public void testQueryMap1(){
        String sql = "select name, sex from student where id = ?";
        Map<String,Object> stu = jdbcTemplate.queryForMap(sql,1003);
        System.out.println(stu);
    }

    @org.junit.Test
    public void testQueryMap2(){
        String sql = "select * from student";
        List<Map<String,Object>> stus = jdbcTemplate.queryForList(sql);
        System.out.println(stus);
    }

    @org.junit.Test
    public void testQueryEntity1(){
        String sql = "select * from student where id = ?";
        Student student = jdbcTemplate.queryForObject(sql, new RowMapper<Student>(){
            //匿名内部类的形式，一般不建议这么写
            @Override
            public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                //定义映射关系
                Student stu = new Student();
                stu.setId(resultSet.getInt("id"));
                stu.setName(resultSet.getString("name"));
                stu.setSex(resultSet.getString("sex"));
                stu.setBorn(resultSet.getDate("born"));
                return stu;
            }
        }, 1003);
        System.out.println(student);
    }

    @org.junit.Test
    public void testQueryEntity2(){
        String sql = "select * from student";
        List<Student> stus = jdbcTemplate.query(sql,new StudentRowMapper());
        System.out.println(stus);
    }


    //一般， 声明一个类来描述这个映射关系，私有的class，隐藏实现的细节
    private class StudentRowMapper implements RowMapper<Student>{
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student stu = new Student();
            stu.setId(resultSet.getInt("id"));
            stu.setName(resultSet.getString("name"));
            stu.setSex(resultSet.getString("sex"));
            stu.setBorn(resultSet.getDate("born"));
            return stu;
        }
    }

}
