package com.fatal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fatal.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Fatal
 * @date: 2018/9/15 0015 12:00
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
