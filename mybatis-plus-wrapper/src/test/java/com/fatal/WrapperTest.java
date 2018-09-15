package com.fatal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatal.entity.User;
import com.fatal.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @author: Fatal
 * @date: 2018/9/14 0014 20:58
 * @desc: 条件构造器
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class WrapperTest {

    @Autowired
    private UserMapper userMapper;  // 这里会报错，是idea的一个bug

    /**
     * lambda 的优势，不用手写字段名
     */

    /**
     * 排除字段
     * method: excludeColumns(Class<T> entityClass, String... excludeColumns)
     * Condition: 实体字节码对象，需要排除的所有字符串（可变参数）
     * sql: SELECT name,age,role_id,id
     *      FROM user
     *      WHERE id = 1
     */
    @Test
    public void excludeColumns() {
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .eq("id", 1l)
                .excludeColumns(User.class, "email"));
        Assert.assertNotNull(user);
        System.out.println(user);
    }

    /**
     * 查询指定字段
     * method: select(String... sqlSelect)
     * Condition: 需查询的所有字段（可变参数）
     * sql: SELECT name,email
     *      FROM user
     *      WHERE id = 1
     */
    @Test
    public void select() {
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .eq("id", 1l)
                .select("name","email"));
        Assert.assertNotNull(user);
        System.out.println(user);
    }

    /**
     * 封装所有查询条件
     * method: allEq(Map<R, Object> params)
     * Condition: map 类型的参数, key 是字段名, value 是字段值
     * sql: SELECT id,name,age,email,role_id
     *      FROM user
     *      WHERE name = 'Jone' AND age = 18
     */
    @Test
    public void allEq() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("age",18);
        map.put("name", "Jone");
        List<User> users = userMapper.selectList(new QueryWrapper<User>()
                .allEq(map));
        Assert.assertNotNull(users);
        print(users);
    }

    /**
     * 封装所有查询条件带参数过滤器
     * method: allEq(BiPredicate<R, V> filter, Map<R, V> params)
     * Condition: lambda表达式，存放字段名和字段值的map
     * 析：字段过滤接口，传入多参数时允许对参数进行过滤
     * sql: SELECT id,name,age,email,role_id
     *      FROM user
     *      WHERE age = 18
     */
    @Test
    public void allEqWithFilter() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("age",18);
        map.put("name", "Jone");
        List<User> users = userMapper.selectList(new QueryWrapper<User>()
                .allEq((s,o) -> !"name".equals(s), map));
        Assert.assertNotNull(users);
        print(users);
    }


    /**
     * method: This between(R column, Object val1, Object val2)
     * BETWEEN 值1 AND 值2
     * sql: SELECT id,name,age,email,role_id
     *      FROM user WHERE age
     *      BETWEEN 18 AND 22
     */
    @Test
    public void between() {
        /*List<User> users = userMapper.selectList(new QueryWrapper<User>()
                .between("age", 18, 22));*/
        List<User> users = userMapper.selectList(new QueryWrapper<User>()
                .lambda()
                .between(User::getAge, 18, 22));
        Assert.assertNotNull(users);
        print(users);
    }

    // ================================    lambda     ================================

    /**
     * 普通查询
     */
    @Test
    public void ordinary() {
        // 普通条件构造器
        List<User> plainUsers = userMapper.selectList(new QueryWrapper<User>()
                .eq("role_id", 2L));

        // lambda 条件构造器
        List<User> lambdaUsers = userMapper.selectList(new QueryWrapper<User>()
                .lambda()
                .eq(User::getRoleId, 2L));
        Assert.assertEquals(plainUsers.size(), lambdaUsers.size());
        print(plainUsers);
    }

    /**
     * 带子查询(sql注入)
     * sql: SELECT id,name,age,email,role_id
     *      FROM user
     *      WHERE role_id IN (select id from role where id = 2)
     */
    @Test
    public void inSql() {
        // 普通条件构造器
        List<User> plainUsers2 = userMapper.selectList(new QueryWrapper<User>()
                .inSql("role_id", "select id from role where id = 2"));

        // lambda 条件构造器
        List<User> lambdaUsers2 = userMapper.selectList(new QueryWrapper<User>()
                .lambda()
                .inSql(User::getRoleId, "select id from role where id = 2"));
        Assert.assertEquals(plainUsers2.size(), lambdaUsers2.size());
        print(plainUsers2);
    }

    /**
     * 带嵌套查询
     * sql: SELECT id,name,age,email,role_id
     *      FROM user
     *      WHERE ( role_id = ? OR role_id = ? ) AND ( age >= ? )
     */
    @Test
    public void nested() {
        // 普通条件构造器
        List<User> plainUsers3 = userMapper.selectList(new QueryWrapper<User>()
                .nested(i -> i.eq("role_id", 2L).or().eq("role_id", 3L))
                .and(i -> i.ge("age", 20)));

        // lambda 条件构造器
        List<User> lambdaUsers3 = userMapper.selectList(new QueryWrapper<User>()
                .lambda()
                .nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L))
                .and(i -> i.ge(User::getAge, 20)));
        Assert.assertEquals(plainUsers3.size(), lambdaUsers3.size());
        print(plainUsers3);
    }

    /**
     * 自定义(sql注入)
     * sql: SELECT id,name,age,email,role_id
     *      FROM user
     *      WHERE role_id = 2
     */
    @Test
    public void apply() {
        List<User> plainUsers4 = userMapper.selectList(new QueryWrapper<User>()
                .apply("role_id = 2"));
        print(plainUsers4);
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }
}
