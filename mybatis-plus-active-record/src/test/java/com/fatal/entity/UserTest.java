package com.fatal.entity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: Fatal
 * @date: 2018/9/12 0012 20:18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Test
    public void insert() {
        User user = new User().setName("张三")
                .setAge(18)
                .setEmail("1234@qq.com");
        Assert.assertEquals(true,user.insert());
    }

    @Test
    public void updateById() {
        User user = new User().setId(1L)
                .setName("张三")
                .setAge(19)
                .setEmail("1234@qq.com");
        Assert.assertEquals(true, user.updateById());
    }

    @Test
    public void update() {
        User user = new User().setId(1L)
                .setName("张三")
                .setAge(22)
                .setEmail("1234@qq.com");
        Assert.assertEquals(true, user.update(new UpdateWrapper<User>()
            .lambda()
            .eq(User::getId, 1l)));
    }

    @Test
    public void insertOrUpdate() {
        // 添加
        User user1 = new User().setName("张三")
                .setAge(23)
                .setEmail("1234@qq.com");
        Assert.assertEquals(true,user1.insertOrUpdate());

        // 添加
        User user2 = new User().setId(1l)
                .setName("张三")
                .setAge(25)
                .setEmail("1234@qq.com");
        Assert.assertEquals(true,user2.insertOrUpdate());
    }

    @Test
    public void deleteById() {
        Assert.assertEquals(true, new User().setId(2l).deleteById());
    }

    @Test
    public void deleteByIdWithParam() {
        Assert.assertEquals(true, new User().deleteById(3l));
    }

    @Test
    public void delete() {
        Assert.assertEquals(true, new User()
                .delete(new QueryWrapper<User>()
                    .lambda()
                    .eq(User::getId, 4l)));
    }

    @Test
    public void selectById() {
        User user = new User().setId(1l).selectById();
        Assert.assertNotNull(user);
    }

    @Test
    public void selectByIdWithParam() {
        User user = new User().selectById(1l);
        Assert.assertNotNull(user);
    }

    @Test
    public void selectOne() {
        User user = new User().selectOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getId, 1l));
        Assert.assertNotNull(user);
    }

    /**
     * 计数
     */
    @Test
    public void selectCount() {
        int count = new User().selectCount(new QueryWrapper<User>()
                .lambda()
                .eq(User::getEmail, "1234@qq.com"));
        Assert.assertNotEquals(0, count);
        System.out.println(count);
    }

    /**
     * 查询所有
     */
    @Test
    public void selectAll() {
        List<User> users = new User().selectAll();
        Assert.assertNotEquals(0, users.size());
        print(users);
    }

    /**
     * 查询集合
     */
    @Test
    public void selectList() {
        List<User> users = new User().selectList(new QueryWrapper<User>()
                .lambda()
                .eq(User::getEmail, "1234@qq.com"));
        Assert.assertNotEquals(0, users.size());
        print(users);
    }

    /**
     * 分页
     */
    @Test
    public void selectPage() {
        IPage<User> page = new User().selectPage(new Page<>(1, 3), new QueryWrapper<User>()
                .lambda()
                .eq(User::getEmail, "1234@qq.com"));
        Assert.assertNotEquals(0, page.getRecords().size());
        print(page.getRecords());
    }

    private void print(List list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }
}