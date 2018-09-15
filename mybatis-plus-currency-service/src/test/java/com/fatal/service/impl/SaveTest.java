package com.fatal.service.impl;

import com.fatal.entity.User;
import com.fatal.service.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author: Fatal
 * @date: 2018/8/31 0031 22:47
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SaveTest {

    @Autowired
    private IUserService userService;

    /**
     * 需注意的是：
     * 1.属性值为null的不会植入sql中不需要更新的字段的对应属性值为null即可
     */

    /**
     * 插入
     */
    @Test
    public void save() {
        User user = new User().setName("米彩").setEmail("123@qq.com").setAge(16);
        Assert.assertTrue(userService.save(user));
        System.out.println(user);
    }


    /**
     * 插入（批量）
     */
    @Test
    public void saveBatch() {
        List<User> users = Stream.of(
                new User().setName("米彩1").setEmail("123@qq.com").setAge(16),
                new User().setName("米彩2").setEmail("123@qq.com").setAge(17),
                new User().setName("米彩3").setEmail("123@qq.com").setAge(18),
                new User().setName("米彩4").setEmail("123@qq.com").setAge(19),
                new User().setName("米彩5").setEmail("123@qq.com").setAge(20)
        ).collect(Collectors.toList());
        Assert.assertTrue(userService.saveBatch(users));
        users.forEach(System.out::println);
    }


    /**
     * 测试插入效率（批量）   10000 条为例
     *  batchSize       耗时
     *  1               17412ms、17785ms、17987ms
     *  30（默认）       7752ms、8093ms、7717ms
     *  100             6987ms、7003ms、7005ms
     *  500             6679ms、6625ms、6627ms
     *  1000            5797ms、6014ms、5900ms
     *  2000            6081ms、6273ms、6028ms
     *  3000            6608ms、6219ms、6068ms
     *  5000            6276ms、6187ms、6123ms
     *  10000           6428ms、6303ms、6096ms
     *  数据分析：出口向下的抛物线，在1/10左右效率较高
     *  一般下选择默认即可，真需要效率就数据库优化。
     */
    @Test
    public void testSaveBatchEffe() {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            User user = new User().setName("米彩" + i).setEmail("123@qq.com").setAge(i);
            users.add(user);
        }
        long start = System.currentTimeMillis();
//        userService.saveBatch(users, 1);
        userService.saveBatch(users);       // 默认30
//        userService.saveBatch(users, 100);
//        userService.saveBatch(users, 500);
//        userService.saveBatch(users, 1000);
//        userService.saveBatch(users, 2000);
//        userService.saveBatch(users, 3000);
//        userService.saveBatch(users, 5000);
//        userService.saveBatch(users, 100000);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
    }

}