package com.fatal;

import com.fatal.entity.User;
import com.fatal.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AutoFillTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * sql: INSERT INTO
     *      user ( id, name, age, email, operator )
     *      VALUES ( 1040828709994520577, 'Tom', 1, 'tom@qq.com', 'Jerry' )
     */
    @Test
    public void insert(){
        User user = new User(null,"Tom",1,"tom@qq.com",null);
        userMapper.insert(user);
        log.info("query user:{}",userMapper.selectById(user.getId()));
    }

    /**
     * sql: UPDATE
     *      user SET name='Jone',
     *          age=12,
     *          email='test1@baomidou.com',
     *          operator='Tom'
     *      WHERE id=1
     */
    @Test
    public void update(){
        User beforeUser = userMapper.selectById(1L);
        log.info("before user:{}",beforeUser);
        beforeUser.setAge(12);
        userMapper.updateById(beforeUser);
        log.info("query user:{}",userMapper.selectById(1L));
    }

}
