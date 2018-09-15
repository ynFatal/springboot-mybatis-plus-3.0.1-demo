package com.fatal.mapper;

import com.fatal.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: Fatal
 * @date: 2018/9/12 0012 13:10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;  // 这里没事，idea的一个bug

    @Test
    public void fun() {
        List<User> users = userMapper.selectList(null);
        Assert.assertNotEquals(0, users.size());
        users.forEach(System.out::print);
    }

}