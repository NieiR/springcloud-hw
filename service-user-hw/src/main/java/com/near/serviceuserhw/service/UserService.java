package com.near.serviceuserhw.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author near
 * @description 大概描述所属模块和介绍
 * @date 2020-10-12 14:11
 **/
public interface UserService {
    boolean register(String email, String password, String code);

    String info(String token);

    boolean isRegistered(String email);

    String login(String email, String password, HttpServletResponse response);
}
