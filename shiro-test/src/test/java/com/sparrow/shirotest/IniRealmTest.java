package com.sparrow.shirotest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class IniRealmTest{

    @Test
    public void testAuthentication(){

        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //1 构建SecrurityManager 环境

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //将新建的账号传给securitymanager
        defaultSecurityManager.setRealm(iniRealm);

        //2 主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken( "Joe","123456");
        //登陆
        subject.login(token);
        //登陆之后认证为true
        System.out.println("isAuthentiated:" + subject.isAuthenticated());

        //检查角色
        subject.checkRole("admin");

        //检查多个角色
//        subject.checkRoles("admin","user");

        //检查权限
        subject.checkPermission("user:update");

        subject.logout();

        System.out.println("logout isAuthenticated : "+subject.isAuthenticated());


    }
}
