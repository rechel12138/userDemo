package com.rechel.user.dao;

import com.rechel.user.entity.Blog;

import java.util.List;

/**
 * @Author: Rechel
 * @Date: 2019/1/7 下午7:44
 */

public interface IBlogDao {
    public List<Blog> getBlogs();

    public int addBlog(Blog blog);

    public List<Blog> getBlogsByUserId(int userId);

    public Blog getBlogById(int id);

    public void deleteBlogById(int id);

    public void editBlog(Blog blog);
}
