package com.shaoming.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shaoming.comm.utils.StringUtil;

import com.shaoming.comm.utils.code.Captcha;
import com.shaoming.comm.utils.code.SpecCaptcha;
import com.shaoming.comm.vm.PageVM;
import com.shaoming.comm.vm.ResultVM;
import com.shaoming.sys.model.SysRole;
import com.shaoming.sys.model.SysUser;
import com.shaoming.sys.model.SysUserRole;
import com.shaoming.sys.service.SysRoleService;
import com.shaoming.sys.service.SysUserRoleService;
import com.shaoming.sys.service.SysUserService;
import com.shaoming.sys.vo.SysRoleVo;
import com.shaoming.sys.vo.SysUserVo;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ShaoMing on 2018/4/20
 */
@Slf4j
@RestController
@RequestMapping("/web/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    CacheManager cacheManager;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResultVM login(@RequestParam("userName") String userName,
                          @RequestParam("password") String password,
                          @RequestParam("yzcode") String yzcode,
                          @RequestParam(name = "rememberMe", defaultValue = "false") Boolean rememberMe,HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        //String captcha = (String)request.getSession().getAttribute("code");
        //System.out.println(captcha);
         //CaptchaUtil.out();
        try {
           String code = redisTemplate.opsForValue().get("code").toString();
           if (!code.equalsIgnoreCase(yzcode)){
               return ResultVM.error("验证码不正确！");
           }
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e) {
            return ResultVM.error("用户名不存在！");
        } catch (IncorrectCredentialsException e) {
            return ResultVM.error("用户名或密码错误！");
        } catch (ExcessiveAttemptsException e) {
            return ResultVM.error("密码输入错误次数过多，请稍后再试！");
        } catch (LockedAccountException e) {
            return ResultVM.error("用户账号已被锁定！");
        }
        return ResultVM.ok("登录成功！");
    }
    /**
     * 查看当前用户登录信息
     */
    @RequiresPermissions({"power_user"})
    @GetMapping("/userInfo")
    public ResultVM userInfo(){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return ResultVM.ok(user);
    }

    @GetMapping("/code")
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/gif");
        //String path = this.getClass().getClassLoader().getResource("/").getPath();
        String code="";

         //String path = SysUserController.class.getResource("/").getPath();
        System.out.println(System.getProperty("user.dir"));
        File file = new File(System.getProperty("user.dir")+ "/src/main/java/static/assets/images/code.png");//验证码路径
        try {
            if(file.exists() || file.isFile()) {
                file.delete();
            }
            OutputStream outputStream = new FileOutputStream(file);

            // 三个参数分别为宽、高、位数
            Captcha gifCaptcha = new SpecCaptcha(150,40,5);// png格式验证码

            // 设置字体
            gifCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置

            // 设置类型，纯数字、纯字母、字母数字混合
            //            TYPE_DEFAULT	数字和字母混合
            //            TYPE_ONLY_NUMBER	纯数字
            //            TYPE_ONLY_CHAR	纯字母
            gifCaptcha.setCharType(Captcha.TYPE_DEFAULT);

            // 生成的验证码
            code= gifCaptcha.text();

            // 输出图片流
            gifCaptcha.out(outputStream);
            // 验证码存入session
            //request.getSession().setAttribute("captcha", gifCaptcha.text().toLowerCase());

            redisTemplate.boundValueOps("code").set(code);

            ImageIO.write(ImageIO.read(file),"gif",response.getOutputStream());
        }catch (Exception e){
            log.info("验证码获取失败"+e.getMessage(),e);
           // return ResultVM.error("验证码获取失败！");
        }
    }


    /**
     * 验证码验证
     */
    @PostMapping("verification/code")
    @ResponseBody
    public ResultVM verificationCode(String yzcode){
        try {
            String code = redisTemplate.opsForValue().get("code").toString();
            if (!code.equalsIgnoreCase(yzcode)){
                return ResultVM.error("验证码不正确！");
            }
        }catch (Exception e){

            log.info(e.getMessage() ,e);
            return ResultVM.error("验证码不正确！");
        }
        return ResultVM.ok("验证码成功！");
    }
    @GetMapping("/noLogin")
    public ResultVM noLogin(){
        return ResultVM.error(300,"请先登录！");
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public ResultVM logout(){
        Subject subject = SecurityUtils.getSubject();
        // 判断用户是否已登录
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return ResultVM.ok("登出成功！");
    }

    /**
     * 新增管理员
     *   需要后台用户管理权限
     */
    @RequiresPermissions({"power_user"})
    @PostMapping("/addSysUser")
    public ResultVM addSysUser(@RequestBody SysUser user){
        String userName = user.getUserName();
        String mobile = user.getMobile();
        String email = user.getEmail();
        String password = user.getPassword();
        if (StringUtils.isEmpty(userName))
            return ResultVM.error("用户名必填！");
        if (StringUtils.isEmpty(password))
            return ResultVM.error("密码必填！");
        userName = userName.trim();
        password = password.trim();
        // 判断改用户名是否存在
        SysUser sysUser = sysUserService.selectOne(new EntityWrapper<SysUser>().where("user_name={0}", userName));
        if (sysUser != null)
            return ResultVM.error("该用户名已被使用！");
        sysUser = sysUserService.selectOne(new EntityWrapper<SysUser>().addFilterIfNeed(!StringUtils.isEmpty(mobile),"mobile={0}", mobile));
        if (sysUser != null)
            return ResultVM.error("该手机号已被使用！");
        sysUser = sysUserService.selectOne(new EntityWrapper<SysUser>().addFilterIfNeed(!StringUtils.isEmpty(email),"email={0}", email));
        if (sysUser != null)
            return ResultVM.error("该邮箱已被使用！");
        // 生成盐
        String salt = StringUtil.createRandomString(6);
        user.setSalt(salt);
        String algorithmName = "MD5"; // 加密方式
        int hashIterations = 2; // 加密次数（MD5次数，需要与ShiroConfig中配置的一致）
        user.setUserName(userName);
        user.setMobile(mobile == null ? null : mobile.trim());
        user.setEmail(email == null ? null : email.trim());
        user.setPassword(String.valueOf(new SimpleHash(algorithmName, password, salt, hashIterations)));
        boolean bool = sysUserService.insert(user);
        return bool ? ResultVM.ok("添加成功！") : ResultVM.error("系统错误！");
    }

    /**
     * 删除后台用户
     *   需要后台用户管理权限
     */
    @RequiresPermissions({"power_user"})
    @GetMapping("/deleteSysUser")
    public ResultVM deleteSysUser(@RequestParam(name = "id") Integer id){
        if (id == 1)
            return ResultVM.error("该用户不得删除！");
        SysUser user = sysUserService.selectById(id);
        if (user == null || "删除".equals(user.getTbStatus()))
            return ResultVM.error("该用户不存在或已删除！");
        user.setTbStatus("删除");
        sysUserService.updateById(user);
        // 删除用户所对应的用户角色关系数据
        sysUserRoleService.delete(new EntityWrapper<SysUserRole>().where("user_id={0}", id));
        return ResultVM.ok("删除成功！");
    }

    /**
     * 编辑用户信息
     *   需要后台用户管理权限
     */
    @RequiresPermissions({"power_user"})
    @PostMapping("/editSysUser")
    public ResultVM editSysUser(SysUser user){
        Integer userId = user.getId();
        if (StringUtils.isEmpty(user.getUserName())) {
            return ResultVM.error("用户名不能为空！");
        }
        if (userId != null) {
            SysUser sysUser = sysUserService.selectById(user.getId());
            if (sysUser == null || "删除".equals(sysUser.getTbStatus()))
                return ResultVM.error("该用户不存在或已删除！");
            if (StringUtils.isEmpty(user.getPassword())) {
                user.setPassword(null);
            }
//            } else {
//                String salt = sysUser.getSalt();
//                user.setSalt(salt);
//                String password = user.getPassword(); //密码原值
//                String algorithmName = "MD5"; // 加密方式
//                int hashIterations = 2; // 加密次数（MD5次数，需要与ShiroConfig中配置的一致）
//                user.setPassword(String.valueOf(new SimpleHash(algorithmName, password, salt, hashIterations)));
//            }
        }else {
            SysUser sysUser = sysUserService.selectOne(new EntityWrapper<SysUser>().where("user_name={0}", user.getUserName()));
            if (sysUser != null)
                return ResultVM.error("该用户名已被使用！");
            sysUser = sysUserService.selectOne(new EntityWrapper<SysUser>().addFilterIfNeed(!StringUtils.isEmpty(user.getMobile()),"mobile={0}", user.getMobile()));
            if (sysUser != null)
                return ResultVM.error("该手机号已被使用！");
            sysUser = sysUserService.selectOne(new EntityWrapper<SysUser>().addFilterIfNeed(!StringUtils.isEmpty(user.getEmail()),"email={0}", user.getEmail()));
            if (sysUser != null)
                return ResultVM.error("该邮箱已被使用！");
            if (StringUtils.isEmpty(user.getPassword())){
                user.setPassword("123456");
            }
            String salt = StringUtil.createRandomString(6);
            user.setSalt(salt);
            String algorithmName = "MD5"; // 加密方式
            int hashIterations = 2; // 加密次数（MD5次数，需要与ShiroConfig中配置的一致）
            user.setPassword(String.valueOf(new SimpleHash(algorithmName, user.getPassword(), salt, hashIterations)));
        }
        boolean bool = sysUserService.insertOrUpdate(user);
        return bool ? ResultVM.ok("操作成功！") : ResultVM.error("系统错误！");
    }

    /**
     * 重置用户密码
     * @param id
     * @return
     */
    @RequiresPermissions({"power_user"})
    @PostMapping("/resetUserPwd")
    public ResultVM resetUserPwd(@RequestParam(name = "id") Integer id){
        SysUser user = new SysUser();
        try {
            user.setId(id);
          //  user.setPassword("123456");
            String salt = StringUtil.createRandomString(6);
            user.setSalt(salt);
            String algorithmName = "MD5"; // 加密方式
            int hashIterations = 2; // 加密次数（MD5次数，需要与ShiroConfig中配置的一致）
            user.setPassword(String.valueOf(new SimpleHash(algorithmName, "123456", salt, hashIterations)));
            boolean bool = sysUserService.updateById(user);
        }catch (Exception e){
            log.info("操作失败"+e.getMessage(),e);
            ResultVM.error("操作失败！");
        }
        return ResultVM.ok("操作成功！");
    }
    /**
     * 分页查询后台用户列表
     *   需要后台用户管理权限
     */
    @RequiresPermissions({"power_user"})
    @GetMapping("/queryUserList")
    public ResultVM queryUserList(@RequestParam(name = "searchValue", required = false) String searchValue,
                                  @RequestParam(name = "page", defaultValue = "1") Integer page,
                                  @RequestParam(name = "size", defaultValue = "20") Integer size){
        PageVM pageVM = new PageVM();
        Wrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.where("tb_status != '删除'");
        wrapper.addFilterIfNeed(!StringUtils.isEmpty(searchValue), "(user_name like {0} or mobile like {0} or email like {0})", "%" + searchValue + "%");
        Page<SysUser> userPage = sysUserService.selectPage(new Page<SysUser>(page, size), wrapper); // 你这里为啥一定要把枚举给加上，我这里不用
        // 查询数所有角色
        List<SysRole> roleList = sysRoleService.selectList(null);
        Map<Integer, SysRole> roleMap = new HashMap<>();
        if (roleList!=null && roleList.size()>0) {
            for (SysRole role : roleList) {
                roleMap.put(role.getId(), role);
            }
        }
        // 获取用户记录
        List<SysUser> records = userPage.getRecords();
        // 定义返回值
        List<SysUserVo> userVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(records)){
            SysUserVo vo;
            List<SysRole> roles;
            for (SysUser record : records) {
                vo = new SysUserVo();
                roles = new ArrayList<>();
                BeanUtils.copyProperties(record, vo);
                if (record.getId().equals(1)) {
                    roles.add(roleMap.get(1));
                } else {
                    List<SysUserRole> userRoles = sysUserRoleService.selectList(new EntityWrapper<SysUserRole>().where("user_id = {0}", record.getId()));
                    if (!CollectionUtils.isEmpty(userRoles)) {
                        for (SysUserRole userRole : userRoles) {
                            roles.add(roleMap.get(userRole.getRoleId()));
                        }
                    }
                }
                vo.setRoleList(roles);
                userVos.add(vo);
            }
        }
        pageVM.setPage(page);
        pageVM.setSize(size);
        pageVM.setTotal(userPage.getTotal());
        pageVM.setRecords(userVos);
        return ResultVM.ok(pageVM);
    }

    /**
     * 给用户设置所属角色
     *   需要后台用户管理权限
     */
    @RequiresPermissions({"power_user"})
    @PostMapping("/editUserRole")
    public ResultVM editUserRole(@RequestParam(name = "userId") Integer userId,
                                 @RequestParam(name = "roleIds", required = false) List<Integer> roleIds){
        // 硬删除该用户当前所属用户角色关系数据
        sysUserRoleService.delete(new EntityWrapper<SysUserRole>().where("user_id={0}", userId));
        if (roleIds == null || roleIds.size() == 0)
            return ResultVM.ok("设置成功！");
        SysUserRole userRole;
        for (Integer roleId : roleIds) {
            userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            sysUserRoleService.insert(userRole);
        }
        return ResultVM.ok("设置成功！");
    }

    /**
     * 获取用户所拥有的角色
     */
    @RequiresPermissions({"power_user"})
    @GetMapping("/queryUserRole")
    public ResultVM queryUserRole(@RequestParam(name = "userId") Integer userId){
        // 获取用户当前所属角色
        List<SysUserRole> userRoles = sysUserRoleService.selectList(new EntityWrapper<SysUserRole>().where("user_id={0}", userId));
        List<Integer> roleIds = new ArrayList<>();
        if (userRoles!=null && userRoles.size()>0){
            for (SysUserRole userRole : userRoles) {
                roleIds.add(userRole.getRoleId());
            }
        }
        // 获取所有角色
        List<SysRole> roles = sysRoleService.selectList(new EntityWrapper<SysRole>().where("tb_status != '删除'"));
        if (roles == null || roles.size() == 0)
            return ResultVM.ok();
        List<SysRoleVo> voList = new ArrayList<>();
        SysRoleVo vo;
        for (SysRole role : roles) {
            vo = new SysRoleVo();
            BeanUtils.copyProperties(role, vo);
            if (roleIds.contains(role.getId()))
                vo.setChecked(true);
            else
                vo.setChecked(false);
            voList.add(vo);
        }
        return ResultVM.ok(voList);
    }

    /**
     * 修改用户状态
     * @param user
     * @return
     */
    //@RequestMapping
    @RequiresPermissions({"power_user"})
    @RequestMapping("/editSysUserStatus")
    public ResultVM editUser(String id,String tbStatus){
        SysUser user = new SysUser();
        if (id == null)
            return ResultVM.error("传入参数有误！");
        if (StringUtils.isEmpty(tbStatus))
            return ResultVM.error("传入参数有误！");
        user.setId(Integer.parseInt(id));
        if ("0".equals(tbStatus)){
            user.setTbStatus("正常");
        }else if("1".equals(tbStatus)){
            user.setTbStatus("禁用");
        }
        boolean bool = sysUserService.updateById(user);
        return bool ? ResultVM.ok("修改成功！") : ResultVM.error("系统错误！");
    }

    /**
     *
     * @param oldPwd 原密码
     * @param newPwd 新密码
     * @param principalCollection
     * @return
     */
    @RequiresPermissions({"power_user"})
    @PostMapping("/updatePwd")
    public ResultVM updatePwd(String oldPwd,String newPwd,PrincipalCollection principalCollection){
        try {
            SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
            String pwd = String.valueOf(new SimpleHash("MD5", user.getPassword(), user.getSalt(), 2));
            String md5OldPwd = String.valueOf(new SimpleHash("MD5", oldPwd, user.getSalt(), 2));

            if (!md5OldPwd.equals(pwd)) {
                return ResultVM.error("原密码不正确！");
            }
            String md5NewPwd = String.valueOf(new SimpleHash("MD5", newPwd, user.getSalt(), 2));
            SysUser user2 = new SysUser();
            user2.setId(user.getId());
            user2.setPassword(md5NewPwd);
            boolean bool = sysUserService.updateById(user2);
            return bool ? ResultVM.ok("修改成功！") : ResultVM.error("系统错误！");
        }catch (Exception e){
            log.info(e.getMessage(),e);
           return ResultVM.error("系统错误！");
        }


    }
    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";//加密方式
        String password = "123456";//密码原值
        String salt = "F54GSD";//盐值
        int hashIterations = 2;//加密2次
        String result = String.valueOf(new SimpleHash(hashAlgorithmName, password, salt, hashIterations));
        System.out.println(result);
//        String salt = StringUtil.createRandomString(6);
//        log.error("=========["+salt+"]========");
    }

}
