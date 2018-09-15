package com.fatal;

import com.fatal.entity.User;
import com.fatal.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TenantTests {

    @Autowired
    private UserMapper mapper;

    @Test
    public void aInsert() {
        User user = new User();
        user.setName("一一");
        Assert.assertTrue(mapper.insert(user) > 0);
        user = mapper.selectById(user.getId());
        Assert.assertTrue(1 == user.getTenantId());
    }


    @Test
    public void bDelete() {
        Assert.assertTrue(mapper.deleteById(3L) > 0);
    }


    @Test
    public void cUpdate() {
        Assert.assertTrue(mapper.updateById(new User().setId(1L).setName("mp")) > 0);
    }

    @Test
    public void dSelect() {
        List<User> userList = mapper.selectList(null);
        userList.forEach(u -> Assert.assertTrue(1 == u.getTenantId()));
    }
}
