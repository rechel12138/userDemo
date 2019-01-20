package com.rechel.user.mapper;

import com.rechel.user.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Rechel
 * @Date: 2019/1/20 下午11:54
 */
@Mapper
public interface RoleMapper {


    @Select("select * from sys_role where id in (select sys_role_id from sys_role_user where sys_user_id=#{userId})" )
    public List<Role> getAllRolesByUserId(int userId);



}
