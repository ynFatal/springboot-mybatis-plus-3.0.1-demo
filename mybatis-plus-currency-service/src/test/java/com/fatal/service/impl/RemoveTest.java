package com.fatal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.fatal.entity.User;
import com.fatal.service.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Fatal
 * @date: 2018/9/2 0002 11:20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RemoveTest {

    @Autowired
    private IUserService userService;

    @Test
    public void fun() {
        Map<String, String> columnMap = LambdaUtils.getColumnMap("com.fatal.entity.User");
        System.out.println(columnMap);
    }

    /**
     * condition: QueryWrapper
     */
    @Test
    public void remove() {
        boolean record = userService.remove(new QueryWrapper<User>()
                .lambda()
                .eq(User::getHobbyName, 1l));
        Assert.assertTrue(record);
    }

    /**
     * condition: 主键
     */
    @Test
    public void removeById() {
        boolean record = userService.removeById(1l);
        Assert.assertTrue(record);
    }

    /**
     * condition: 主键集合
     */
    @Test
    public void removeByIds() {
        List<Long> longs = Arrays.asList(2l, 3l);
        boolean record = userService.removeByIds(longs);
        Assert.assertTrue(record);
    }

    /**
     * condition: Map  k: 字段   v: 值
     */
    @Test
    public void removeByMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("email", "ab@c.c");
        boolean record = userService.removeByMap(columnMap);
        Assert.assertTrue(record);
    }

}
