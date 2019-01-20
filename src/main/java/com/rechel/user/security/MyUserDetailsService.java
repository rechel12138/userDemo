package com.rechel.user.security;

import com.rechel.user.entity.Role;
import com.rechel.user.mapper.RoleMapper;
import com.rechel.user.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Rechel
 * @Date: 2019/1/7 下午7:45
 */
@Component
public class MyUserDetailsService implements UserDetailsService {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
//    @Autowired
//    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("用户名: {}", username);

        com.rechel.user.entity.User myUser = userMapper.selectUserByUserName(username);
        String password = passwordEncoder.encode(myUser.getPassword());
        logger.info("密码: {}", password);

        List<Role> roles = myUser.getRoles();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //用于添加用户的权限。只要把用户权限添加到authorities 就万事大吉。
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            logger.info("角色名 : {}", role.getName());
        }

        // 参数分别是：用户名，密码，用户权限
        User user = new User(username, password, authorities);
        return user;
    }


}
