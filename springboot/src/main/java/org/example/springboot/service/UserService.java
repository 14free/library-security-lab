package org.example.springboot.service;

import org.example.springboot.mapper.UserMapper;
import org.example.springboot.shujvku.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    
    public User login(String username, String password) {
        Example example = new Example(User.class);
        example.createCriteria()
                .andEqualTo("username", username)
                .andEqualTo("password", password);
        List<User> users = userMapper.selectByExample(example);
        return users.isEmpty() ? null : users.get(0);
    }

    public User register(User user) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", user.getUsername());
        List<User> existUsers = userMapper.selectByExample(example);

        if (!existUsers.isEmpty()) {
            throw new RuntimeException("用户名已存在");
        }

        userMapper.insert(user);
        return user;
    }

    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
