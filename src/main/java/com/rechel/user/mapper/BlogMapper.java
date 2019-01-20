package com.rechel.user.mapper;

import com.rechel.user.entity.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * @Author: Rechel
 * @Date: 2019/1/7 下午7:45
 */
@Mapper
public interface BlogMapper {

    @Select("select * from t_blog")
    public List<Blog> selectBlog();

    @Select("select * from t_blog where id=#{id}")
    public Blog selectBlogById(int id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO t_blog ( userid, title, content) VALUES (#{userId},#{title},#{content})")
    public void insert(Blog blog);

    @Update("update t_blog set title=#{title},content=#{content} where id=#{id}")
    public void update(Blog blog);

    @Delete("delete from t_blog where id=#{id}")
    public void delete(int id);

}
