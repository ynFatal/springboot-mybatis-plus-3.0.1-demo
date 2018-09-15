package com.fatal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatal.entity.User;
import com.fatal.service.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: Fatal
 * @date: 2018/9/2 0002 8:28
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SaveOrUpdateTest {

    @Autowired
    private IUserService userService;

    /**
     * 需注意的是：
     * 1.属性值为null的不会植入sql中不需要更新的字段的对应属性值为null即可
     * 2.根据有无id判断是插入还是更新，所以这里不需要条件构造器
     */

    /**
     * 插入或更新
     */
    @Test
    public void saveOrUpdate() {
        // 插入
        User user = new User().setName("米彩").setEmail("123@qq.com").setAge(16);
        Assert.assertTrue(userService.save(user));

        // 更新
        User userGet = userService.getOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getName, "米彩"));
        userGet.setEmail("234@qq.com");
        Assert.assertTrue(userService.saveOrUpdate(userGet));
    }


    /**
     * 插入或更新（批量）
     */
    @Test
    public void saveOrUpdateBatch() {
        // 插入
        List<User> users = Stream.of(
                new User().setName("米彩1").setEmail("123@qq.com").setAge(16),
                new User().setName("米彩2").setEmail("123@qq.com").setAge(17),
                new User().setName("米彩3").setEmail("123@qq.com").setAge(18),
                new User().setName("米彩4").setEmail("123@qq.com").setAge(19),
                new User().setName("米彩5").setEmail("123@qq.com").setAge(20)
        ).collect(Collectors.toList());
        Assert.assertTrue(userService.saveOrUpdateBatch(users));

        // 更新
        IPage<User> page = userService.page(new Page<>(1, 5), null);
        if (page != null) {
            List<User> records = page.getRecords();
            if (!CollectionUtils.isEmpty(records)) {
                List<User> collect = records.stream()
                        .map(e -> e.setAge(123))
                        .collect(Collectors.toList());
                Assert.assertTrue(userService.saveOrUpdateBatch(collect));
            }
        }
    }


}
