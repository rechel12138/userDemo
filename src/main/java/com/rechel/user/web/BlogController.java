package com.rechel.user.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rechel.user.entity.Blog;
import com.rechel.user.mapper.BlogMapper;
import com.rechel.user.service.IBlogService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@Slf4j
@RequestMapping("/blog")
@Api(value = "blog-api", tags = {"Blog相关接口"})
public class BlogController {


    @Autowired
    IBlogService blogService;
    @Autowired
    BlogMapper blogMapper;

    /**
     * 获取所有博客
     *
     * @return
     */
    @GetMapping("getBlogs")
    List<Blog> getBlogs() {
        List<Blog> list = blogService.getBlogs();
        return list;
    }


    /**
     * 添加新的博客
     *
     * @param title
     * @param content
     * @param userId
     * @return
     */
    @PostMapping("addBlog")
    public String saveBlog(@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("userId") int userId) {

        Blog blog = new Blog();
        blog.setUserId(userId);
        blog.setTitle(title);
        blog.setContent(content);
        int id = blogService.addBlog(blog);

        return "ok";

    }

    /**
     * 编辑博客
     *
     * @param title
     * @param content
     * @param userId
     * @param id
     * @return
     */
    @PostMapping("editBlog")
    public String editBlog(@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("userId") int userId, @RequestParam("id") int id) {

        Blog blog = blogService.getBlogById(id);
        blog.setContent(content);
        blog.setTitle(title);
        blogService.editBlog(blog);

        return "ok";
    }

    @PostMapping("deleteBlog")
    public String deleteBlog(@RequestParam("id") int id) {

        blogService.deleteBlog(id);

        return "ok";

    }


    @GetMapping("/getBlogsPage")
    public PageInfo<Blog> getBlogsPage(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        //1. 在参数里接受当前是第几页 start ，以及每页显示多少条数据 size。 默认值分别是0和5。
        //2. 根据start,size进行分页，并且设置id 排序
        PageHelper.startPage(start, size, "id asc");
        //3. 因为PageHelper的作用，这里就会返回当前分页的集合了
        List<Blog> cs = blogService.getBlogs();
        System.out.println("-------" + cs.size());
        //4. 根据返回的集合，创建PageInfo对象
        PageInfo<Blog> page = new PageInfo<>(cs);


        return page;
    }
}
