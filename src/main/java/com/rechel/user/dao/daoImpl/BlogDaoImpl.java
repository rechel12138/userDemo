package com.rechel.user.dao.daoImpl;

import com.rechel.user.dao.IBlogDao;
import com.rechel.user.entity.Blog;
import com.rechel.user.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * @Author: Rechel
 * @Date: 2019/1/7 下午7:45
 */
@Repository
public class BlogDaoImpl implements IBlogDao {
    @Autowired
    BlogMapper blogMapper;

    @Override
    public List<Blog> getBlogs() {

        return blogMapper.selectBlog();
    }

    @Override
    public int addBlog(Blog blog) {
        blogMapper.insert(blog);
        return blog.getId();
    }

    @Override
    public List<Blog> getBlogsByUserId(int userId) {
        return null;
    }

    @Override
    public Blog getBlogById(int id) {
        return blogMapper.selectBlogById(id);
    }

    @Override
    public void deleteBlogById(int id) {

        blogMapper.delete(id);
    }

    @Override
    public void editBlog(Blog blog) {

        blogMapper.update(blog);
    }
}
