package com.rechel.user.service.impl;

import com.rechel.user.dao.IBlogDao;
import com.rechel.user.entity.Blog;
import com.rechel.user.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Rechel
 * @Date: 2019/1/7 下午7:40
 */
@Service
public class BlogServiceImpl implements IBlogService {

    @Autowired
    IBlogDao blogDao;

    @Override
    public int addBlog(Blog blog) {

        return blogDao.addBlog(blog);
    }

    @Override
    public List<Blog> getBlogs() {

        return blogDao.getBlogs();
    }


    @Override
    public List<Blog> getBlogsByUserId(int userId) {
        return null;
    }

    @Override
    public Blog getBlogById(int id) {
        return blogDao.getBlogById(id);
    }

    @Override
    public void editBlog(Blog blog) {


        blogDao.editBlog(blog);
    }

    @Override
    public void deleteBlog(int id) {

        blogDao.deleteBlogById(id);
    }
}
