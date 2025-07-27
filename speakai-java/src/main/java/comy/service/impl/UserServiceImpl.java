package comy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import comy.dao.User;
import comy.service.UserService;
import comy.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author myh
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-07-24 22:13:28
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
}




