package com.rechel.user.entity;

import lombok.Data;

import java.util.Set;

/**
 * @Author: Rechel
 * @Date: 2019/1/20 下午11:37
 */
@Data
public class Role {
    private int id;

    private String name;

    private Set<User> users;
}
