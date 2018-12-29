package com.shaoming.sys.controller;

import com.shaoming.sys.model.SysUser;



/**
 * Controller基类
 * Created by wangfan on 2018-02-22 上午 11:29.
 */
public class BaseController {

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
