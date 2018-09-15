package com.fatal.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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

/**
 * @author: Fatal
 * @date: 2018/9/2 0002 11:30
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UpdateTest {

    @Autowired
    private IUserService userService;

    /**
     * 需注意的是：
     * 1.属性值为null的不会植入sql中，所以不需要更新的字段的对应属性值为null即可
     */

    /**
     * condition: UpdateWrapper
     */
    @Test
    public void update() {
        User user = userService.getById(1l);
        user.setEmail("123@qq.com");

        // lambda条件构造
        boolean record = userService.update(user, new UpdateWrapper<User>()
                .lambda()
                .eq(User::getId, 1l));
        Assert.assertTrue(record);

        // set
        // 会覆盖实体参数属性值
        boolean record1 = userService.update(user, new UpdateWrapper<User>()
                .set("age", 20l)
                .eq("id", 1l));
        Assert.assertTrue(record1);

        // selSql   和set一样会覆盖实体参数属性值
        // SQL 更新字段内容，例如：name='1',age=2
        boolean record2 = userService.update(user, new UpdateWrapper<User>()
                .setSql("age=21")
                .eq("id", 1l));
        Assert.assertTrue(record2);
    }

    /**
     * condition: 主键
     */
    @Test
    public void updateById() {
        User user = userService.getById(1l);
        user.setEmail("456@qq.com");
        boolean record = userService.updateById(user);
        Assert.assertTrue(record);
    }

    /**
     * （批量）
     * condition: 主键
     * batchSize: 默认（一般默认就好）
     */
    @Test
    public void updateBatchById() {
        IPage<User> page = userService.page(new Page<>(1, 5), null);
        if (page != null) {
            List<User> records = page.getRecords();
            if (!CollectionUtils.isEmpty(records)) {
                List<User> collect = records.stream()
                        .map(e -> e.setEmail("123@qq.com"))
                        .collect(Collectors.toList());
                boolean record = userService.updateBatchById(collect);
                Assert.assertTrue(record);
            }
        }
    }

    /**
     * （批量）
     * condition: 主键
     * batchSize: 自定义
     */
    @Test
    public void updateBatchByIdAndBatchSizeCustom() {
        IPage<User> page = userService.page(new Page<>(1, 5), null);
        if (page != null) {
            List<User> records = page.getRecords();
            if (!CollectionUtils.isEmpty(records)) {
                List<User> collect = records.stream()
                        .map(e -> e.setEmail("456@qq.com"))
                        .collect(Collectors.toList());
                boolean record = userService.updateBatchById(collect, 5);
                Assert.assertTrue(record);
            }
        }
    }

}
