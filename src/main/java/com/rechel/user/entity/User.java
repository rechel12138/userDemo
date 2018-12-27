package com.rechel.user.entity;

import lombok.Data;

import java.util.Date;


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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", usernum='" + usernum + '\'' +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", createtime=" + createtime +
                ", createby='" + createby + '\'' +
                ", lastupdatetime=" + lastupdatetime +
                '}';
    }
}
