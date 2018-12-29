package com.shaoming.comm.utils.code;

import java.io.FileOutputStream;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
         Captcha captcha = new SpecCaptcha(150,40,5);// png格式验证码
         captcha.out(new FileOutputStream("D:/1.png"));
//        Captcha captcha  = new GifCaptcha(150,40,5);
//        //gif格式动画验证码
//        captcha.out(new FileOutputStream("D:/test.gif"));
    }
}
