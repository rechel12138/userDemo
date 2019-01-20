package com.rechel.user.web;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.rechel.user.entity.User;
import com.rechel.user.mapper.UserMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Rechel
 * @since 2018-09-03
 */
@RestController
@Slf4j
@RequestMapping("/user")
@Api(value = "user-api", tags = {"用户表相关接口"})
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping("getUsers")
    String getUsers() {
        List<User> list = userMapper.selectUser();
        return list.get(0).toString();
    }

    @PostMapping("saveUser")
    public void saveUser() {

        User user = new User();
        user.setCreateby("rechel1");
        user.setCreatetime(new Date());
        user.setLastupdatetime(new Date());
        user.setEmail("weinanbu@gmail.com");
        user.setFullname("步伟男");
        user.setPassword("123");
        user.setPhone("13552479935");
        user.setUsernum("1000");
        user.setUsername("rechel1");

        userMapper.insert(user);
    }


    @GetMapping("login")
    public void login(){

    }


}
