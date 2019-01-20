package com.rechel.user.entity;

import lombok.Data;

import java.util.Date;


@Data
public class Blog {


    private int id;

    private int userId;
    private String title;
    private String content;


}
