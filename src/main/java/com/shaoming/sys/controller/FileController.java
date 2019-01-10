package com.shaoming.sys.controller;

import com.shaoming.comm.utils.Base64ImgUtil;
import com.shaoming.comm.vm.ResultVM;
import com.shaoming.sys.model.SysUser;
import com.shaoming.sys.service.SysUserService;
import org.apache.catalina.connector.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/web/Img")
public class FileController extends BaseController{
    @Autowired
    private SysUserService sysUserService;

    @RequiresPermissions({"power_user"})
    @PostMapping("/uploadBase64Img")
    public ResultVM uploadBase64Img(String base64Img) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(user)) return ResultVM.error("页面停留过久 请刷新重试！");
        try {
            String strImg = base64Img.split(",")[1];//去掉base的前缀
            String imgFilePath = System.getProperty("user.dir")+ "\\src\\main\\java\\static\\assets\\headIcon\\"+user.getMobile()+".jpg";
            if (Base64ImgUtil.GenerateImage(strImg,imgFilePath)){
                user.setHeadIcon(imgFilePath);
                if(!sysUserService.insertOrUpdate(user)) return ResultVM.error("上传图片失败");
            }else {
                return ResultVM.error("上传图片失败");
            }
        }catch (Exception e){
            log.info(e.getMessage(),e);
        }

        return ResultVM.ok("操作成功");
    }

}
