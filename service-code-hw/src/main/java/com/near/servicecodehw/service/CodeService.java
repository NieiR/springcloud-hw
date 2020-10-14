package com.near.servicecodehw.service;

/**
 * @author near
 * @description 大概描述所属模块和介绍
 * @date 2020-10-12 13:50
 **/
public interface CodeService {
    boolean sendCode(String email);

    Integer validate(String email, String code);
}
