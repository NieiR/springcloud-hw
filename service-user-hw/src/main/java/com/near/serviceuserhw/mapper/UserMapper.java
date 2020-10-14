package com.near.serviceuserhw.mapper;

import com.near.serviceuserhw.bean.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author near
 * @description 大概描述所属模块和介绍
 * @date 2020-10-12 14:07
 **/
@Mapper
public interface UserMapper {
    @Select("select * from auth_user where email = #{email}")
    User findByEmail(String email);

    @Insert({"insert into auth_user(email,password) values('${user.email}','${user.password}')"})
    int add(@Param("user") User user);

}
