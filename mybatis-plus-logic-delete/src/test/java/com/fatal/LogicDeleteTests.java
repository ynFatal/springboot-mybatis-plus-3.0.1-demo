package com.fatal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatal.entity.User;
import com.fatal.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicDeleteTests {

    @Autowired
    private UserMapper userMapper;  // 这里会报错，但并不影响使用

    /**
     * sql: UPDATE user SET is_delete=1
     *      WHERE id=1 AND is_delete=0
     */
    @Test
    public void testLogicDeleteById() {
        userMapper.deleteById(1);
    }

    /**
     * 我在逻辑删除注入器中设置 updateById() 不要有逻辑删除
     * sql: UPDATE user SET
     *          name='lisi',
     *          age=20,
     *          email='test2@baomidou.com',
     *          is_delete=0
     *      WHERE id=2
     */
    @Test
    public void testUpdate() {
        User user = userMapper.selectById(2);
        user.setName("lisi");
        userMapper.updateById(user);
    }

    /**
     * sql: UPDATE user SET
     *          is_delete=1
     *      WHERE id IN ( 1 , 2 , 3 ) AND is_delete=0
     */
    @Test
    public void testLogicDeleteBatchIds() {
        userMapper.deleteBatchIds(Arrays.asList(1, 2, 3));
    }

    /**
     * sql: UPDATE user SET
     *          is_delete=1
     *      WHERE is_delete=0 AND age = 2
     */
    @Test
    public void testLogicDelete() {
        userMapper.delete(new QueryWrapper<User>().eq("age", 2));
    }

}
