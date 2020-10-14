package com.near.serviceemailhw.service;

/**
 * @author near
 * @description 大概描述所属模块和介绍
 * @date 2020-10-12 13:25
 **/
public interface EmailService {
    boolean sendEmail(String email,String code);
}
