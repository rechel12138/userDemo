package com.rechel.user.service;

import com.rechel.user.entity.Blog;

import java.util.List;

/**
 * @Author: Rechel
 * @Date: 2019/1/7 下午7:40
 */
public interface IBlogService {

    public List<Blog> getBlogs();

    public List<Blog> getBlogsByUserId(int userId);

    public Blog getBlogById(int id);


    public int addBlog(Blog blog);

    public void editBlog(Blog blog);

    public void deleteBlog(int id);

}
