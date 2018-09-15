package com.fatal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatal.entity.User;
import com.fatal.enums.AgeEnum;
import com.fatal.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EnumTests {

    @Autowired
    private UserMapper mapper;

    /**
     * INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
     * Parameters: 1036251750023081985(Long), K神(String), 1(Integer), abc@mp.com(String)
     * 拿到值并转为包装类型
     */
    @Test
    public void insert() {
        User user = new User();
        user.setName("K神");
        user.setAge(AgeEnum.ONE);
        user.setEmail("abc@mp.com");
        Assert.assertTrue(mapper.insert(user) > 0);
        // 成功直接拿会写的 ID
        System.err.println("插入成功 ID 为：" + user.getId());
    }

    @Test
    public void delete() {
        Assert.assertTrue(mapper.delete(new QueryWrapper<User>()
                .lambda()
                .eq(User::getAge, AgeEnum.TWO)) > 0);
    }

    @Test
    public void update() {
        Assert.assertTrue(mapper.update(new User().setAge(AgeEnum.TWO),
                new QueryWrapper<User>()
                        .eq("age", AgeEnum.THREE)) > 0);
    }

    @Test
    public void select() {
        User user = mapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getId, 2));
        Assert.assertEquals("Jack", user.getName());
        Assert.assertTrue(AgeEnum.THREE == user.getAge());
    }

}
