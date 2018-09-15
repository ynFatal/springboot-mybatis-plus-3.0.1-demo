package com.fatal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fatal.entity.User;
import com.fatal.mapper.UserMapper;
import com.fatal.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * User 服务实现类
 * @author: Fatal
 * @date: 2018/9/12 0012 15:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
