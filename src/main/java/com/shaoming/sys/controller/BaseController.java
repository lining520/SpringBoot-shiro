package com.shaoming.sys.controller;

import com.shaoming.sys.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Controller基类
 * Created by wangfan on 2018-02-22 上午 11:29.
 */
public class BaseController {
    public static final Logger log = LoggerFactory.getLogger(BaseController.class);
    /**
     * 获取当前登录的user
     */
//    public SysUser getLoginUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object object = authentication.getPrincipal();
//            if (object != null) {
//                return (User) object;
//            }
//        }
//        return null;
//    }

    /**
     * 获取当前登录的userId
     */
//    public String getLoginUserId() {
//        User loginUser = getLoginUser();
//        return loginUser == null ? null : loginUser.getUserId();
//    }

}
