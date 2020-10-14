package com.near.serviceuserhw.bean.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author near
 * @description 大概描述所属模块和介绍
 * @date 2020-10-12 14:05
 **/
@Setter
@Getter
public class User implements Serializable {
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private Integer id;
    private String email;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
