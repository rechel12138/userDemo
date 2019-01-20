package com.rechel.user.mapper;

import com.rechel.user.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.HashMap;
import java.util.List;
/**
 * @Author: Rechel
 * @Date: 2019/1/7 下午7:45
 */
@Mapper
public interface UserMapper {

    @Select("select * from t_user")
    public List<User> selectUser();

//    @Select("select * from t_user where id=#{id}")
//    public List<User> selectUserById(int id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("INSERT INTO t_user (id, usernum, username, fullname, password, phone, email, createtime, createby, lastupdatetime) " +
            "VALUES (#{id},#{usernum},#{username},#{fullname},#{password},#{phone},#{email},#{createtime},#{createby},#{lastupdatetime})")
    public void insert(User user);

    @Delete("delete * from user where id=#{id}")
    public void delete(int id);

    @Select("select * from t_user where username=#{username}")
    public User findByUsername(String username);

    @Select("select * from t_user where id = #{id}")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="username",property="username"),
            @Result(column="id",property="roles",
                    many=@Many(
                            select="com.rechel.user.mapper.RoleMapper.getAllRolesByUserId",
                            fetchType= FetchType.LAZY
                    )
            )
    })
    public User selectUserById(int id);


    @Select("select * from t_user where username = #{userName}")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="username",property="username"),
            @Result(column="id",property="roles",
                    many=@Many(
                            select="com.rechel.user.mapper.RoleMapper.getAllRolesByUserId",
                            fetchType= FetchType.LAZY
                    )
            )
    })
    public User selectUserByUserName(String userName);

}
