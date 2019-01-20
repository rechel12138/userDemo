package com.rechel.user.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author: Rechel
 * @Date: 2019/1/7 下午7:45
 */
@Data
public class User {


    private int id;

    private String usernum;
    private String username;
    private String fullname;
    private String password;
    private String phone;
    private String email;
    private Date createtime;
    private String createby;
    private Date lastupdatetime;
    private List<Role> roles;
}
