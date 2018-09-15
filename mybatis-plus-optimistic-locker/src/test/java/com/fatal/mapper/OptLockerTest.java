package com.fatal.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatal.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Fatal
 * @date: 2018/9/15 0015 11:39
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OptLockerTest {

    @Autowired
    private UserMapper userMapper; // 这里可能会报错，但不影响使用

    /**
     * sql: UPDATE user
     *      SET ..., version=v+1
     *      WHERE id=6 AND version=v
     */


    /**
     * sql: UPDATE user
     *      SET age=19, version=2
     *      WHERE id=6 AND version=1
     */
    @Test
    public void testUpdateByIdSuccess() {
        User user = new User();
        user.setAge(18);
        user.setEmail("test@baomidou.com");
        user.setName("optlocker");
        user.setVersion(1);
        userMapper.insert(user);
        Long id = user.getId();

        User userUpdate = new User();
        userUpdate.setId(id);
        userUpdate.setAge(19);
        userUpdate.setVersion(1);
        Assert.assertEquals("Should update success", 1, userMapper.updateById(userUpdate));
        Assert.assertEquals("Should version = version+1", 2, userUpdate.getVersion().intValue());
    }

    /**
     * sql: UPDATE user
     *      SET age=19, version=1
     *      WHERE id=11 AND version=0
     */
    @Test
    public void testUpdateByIdFail() {
        User user = new User();
        user.setAge(18);
        user.setEmail("test@baomidou.com");
        user.setName("optlocker");
        user.setVersion(1);
        userMapper.insert(user);
        Long id = user.getId();

        User userUpdate = new User();
        userUpdate.setId(id);
        userUpdate.setAge(19);
        userUpdate.setVersion(0);
        Assert.assertEquals("Should update failed due to incorrect version(actually 1, but 0 passed in)", 0, userMapper.updateById(userUpdate));
    }

    @Test
    public void testUpdateByIdSuccWithNoVersion() {
        User user = new User();
        user.setAge(18);
        user.setEmail("test@baomidou.com");
        user.setName("optlocker");
        user.setVersion(1);
        userMapper.insert(user);
        Long id = user.getId();

        User userUpdate = new User();
        userUpdate.setId(id);
        userUpdate.setAge(19);
        userUpdate.setVersion(null);
        Assert.assertEquals("Should update success as no version passed in", 1, userMapper.updateById(userUpdate));
        User updated = userMapper.selectById(id);
        Assert.assertEquals("Version not changed", 1, updated.getVersion().intValue());
        Assert.assertEquals("Age updated", 19, updated.getAge().intValue());
    }

    /**
     * 批量更新带乐观锁
     * <p>
     * update(et,ew) et:必须带上version的值才会触发乐观锁
     */
    @Test
    public void testUpdateByEntitySucc() {
        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.eq("version", 1);
        int count = userMapper.selectCount(ew);

        User entity = new User();
        entity.setAge(28);
        entity.setVersion(1);

        Assert.assertEquals("updated records should be same", count, userMapper.update(entity, null));
        ew = new QueryWrapper<>();
        ew.eq("version", 1);
        Assert.assertEquals("No records found with version=1", 0, (int)userMapper.selectCount(ew));
        ew = new QueryWrapper<>();
        ew.eq("version", 2);
        Assert.assertEquals("All records with version=1 should be updated to version=2", count, (int)userMapper.selectCount(ew));
    }

}