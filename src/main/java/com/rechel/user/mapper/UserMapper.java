package com.rechel.user.mapper;

import com.rechel.user.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from t_user")
    public List<User> selectUser();

    @Select("select * from t_user where id=#{id}")
    public List<User> selectUserById(int id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("INSERT INTO t_user (id, usernum, username, fullname, password, phone, email, createtime, createby, lastupdatetime) " +
            "VALUES (#{id},#{usernum},#{username},#{fullname},#{password},#{phone},#{email},#{createtime},#{createby},#{lastupdatetime})")
    public void insert(User user);

    @Delete("delete * from user where id=#{id}")
    public void delete(int id);

}
