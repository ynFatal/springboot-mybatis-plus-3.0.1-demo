package com.fatal.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fatal.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Fatal
 * @date: 2018/9/12 0012 14:36
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;  // 这里没事，idea的一个bug

    @Test
    public void insert() {
        User user = new User().setName("小米").setAge(3).setEmail("abc@mp.com");
        Assert.assertTrue(userMapper.insert(user) > 0);
//         成功直接拿会写的 ID
        System.err.println("插入成功 ID 为：" + user.getId());
    }


    @Test
    public void delete() {
        Assert.assertTrue(userMapper.deleteById(3L) > 0);
    }


    @Test
    public void update() {
        Assert.assertTrue(userMapper.updateById(new User().setId(1L).setEmail("ab@c.c")) > 0);
        Assert.assertTrue(userMapper.update(new User().setName("mp"),
                new UpdateWrapper<User>().lambda()
                        .set(User::getAge, 3)
                        .eq(User::getId, 2)) > 0);
    }

    @Test
    public void select() {
        Assert.assertEquals("ab@c.c", userMapper.selectById(1L).getEmail());
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getId, 2));
        Assert.assertEquals("mp", user.getName());
        Assert.assertTrue(3 == user.getAge());
        System.out.println(user);
    }

}