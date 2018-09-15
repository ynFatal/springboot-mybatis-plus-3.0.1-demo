package com.fatal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatal.entity.User;
import com.fatal.service.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author: Fatal
 * @date: 2018/9/2 0002 8:04
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class QueryTest {

    @Autowired
    private IUserService userService;

    /**
     * 需注意的是：
     * 1.使用条件构造器是都加泛型（其实加泛型和实体类可以二选一，不过加泛型看上去优雅点）
     * 2.使用lambda排除字段的时候，条件查询器需要一个实体类作为参数，这也许是mp的一个bug
     */

    /**
     * condition: QueryWrapper
     * return: 返回实体
     * 析：默认获得查询的第一条数据
     */
    @Test
    public void getOne() {

        // =======================  字段上 =======================
        // 要求：条件构造器上必须要传空的实体作为参数，底层拿这个参数去获得字节码对象

        // 只需要email字段
        User one1 = userService.getOne(new QueryWrapper<User>(null, "email"));
        Assert.assertNotNull(one1);
        System.out.println(one1);
        User one = userService.getOne(new QueryWrapper<User>().select("name"));
        Assert.assertNotNull(one);
        System.out.println(one);

        // 不需要email字段
        User one2 = userService.getOne(new QueryWrapper<User>().excludeColumns(User.class,"email"));
        Assert.assertNotNull(one2);
        System.out.println(one2);

        // lambda的excludeColumns该版本不完善，切勿使用
        User one3 = userService.getOne(new QueryWrapper<User>(new User())
                .lambda()
                .excludeColumns(User::getEmail));
        Assert.assertNotNull(one3);
        System.out.println(one3);

        // =======================  查询条件上 =======================
        // 这里有两种方式：一种是手写数据库字段，一种是使用`lambda表达式`获得数据库字段
        // 条件的话有很多：eq、like、in、...自己去看

        User one4 = userService.getOne(new QueryWrapper<User>()
                .eq("name", "Jone"));
        Assert.assertNotNull(one4);
        System.out.println(one4);

        User one5 = userService.getOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getName, "Jone"));
        Assert.assertNotNull(one5);
        System.out.println(one5);

    }

    /**
     * condition: id
     * return: 返回实体
     */
    @Test
    public void getById() {
        User user = userService.getById(1L);
        Assert.assertNotNull(user);
    }

    /**
     * condition: QueryWrapper
     * return: Map
     */
    @Test
    public void getMap() {
        Map<String, Object> map = userService.getMap(new QueryWrapper<User>(new User())
                .lambda()
                .excludeColumns(User::getAge)
                .eq(User::getId, 2));
        Assert.assertNotNull(map);
    }

    /**
     * condition: QueryWrapper
     * return: obj
     */
    @Test
    public void getObj() {
        Object obj = userService.getObj(new QueryWrapper<User>(new User())
                .lambda()
                .excludeColumns(User::getAge)
                .eq(User::getId, 2));
        Assert.assertNotNull(obj);
    }

    //  ===================================     List       =======================================

    /**
     * condition: QueryWrapper
     * return: 返回实体集合
     */
    @Test
    public void list() {
        List<User> list = userService.list(new QueryWrapper<User>(new User())
                .lambda()
                .excludeColumns(User::getAge));
        Assert.assertNotEquals(0, list.size());
        print(list);
    }

    /**
     *  condition: 主键集合
     *  return: 返回实体集合
     */
    @Test
    public void listByIds() {
        List<Long> longs = Arrays.asList(1l, 2l, 3l, 4l, 5l);
        ArrayList<User> list = (ArrayList<User>) userService.listByIds(longs);
        Assert.assertNotEquals(0, list.size());
        print(list);
    }

    /**
     *  condition: Map  k: 字段   v: 值
     *  return: 返回实体集合
     */
    @Test
    public void listByMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("email", "ab@c.c");
        ArrayList<User> list = (ArrayList<User>) userService.listByMap(columnMap);
        Assert.assertNotEquals(0, list.size());
        print(list);
    }


    /**
     * condition: QueryWrapper
     * return: List<Map<String, Object>>
     */
    @Test
    public void listMaps() {
        List<Map<String, Object>> maps = userService.listMaps(new QueryWrapper<User>(new User())
                .lambda()
                .excludeColumns(User::getAge));
        Assert.assertNotEquals(0, maps.size());
        print(maps);
    }

    /**
     * condition: QueryWrapper
     * return: List<Object>
     */
    @Test
    public void listObjs() {
        List<Object> objects = userService.listObjs(new QueryWrapper<User>(new User())
                .lambda()
                .excludeColumns(User::getAge));
        Assert.assertNotEquals(0, objects.size());
        print(objects);
    }

    private void print(List list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }

}
