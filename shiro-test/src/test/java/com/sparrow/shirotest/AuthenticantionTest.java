package com.sparrow.shirotest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticantionTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){

        //无角色case
//        simpleAccountRealm.addAccount("Joe","123456");

        //增加角色case
//        simpleAccountRealm.addAccount("Joe","123456","admin");

        //可以增加多个角色
        simpleAccountRealm.addAccount("Joe","123456","admin","user");
    }

    @Test
    public void testAuthentication(){

        //1 构建SecrurityManager 环境

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //将新建的账号传给securitymanager
        defaultSecurityManager.setRealm(simpleAccountRealm);

        //2 主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken( "Joe","123456");
        //登陆
        subject.login(token);
        //登陆之后认证为true
        System.out.println("isAuthentiated:" + subject.isAuthenticated());

        //检查角色
//        subject.checkRole("admin");

        //检查多个角色
        subject.checkRoles("admin","user");



//        //登出
//        subject.logout();
//        //登出之后变认证false
//        System.out.println("isAuthentiated:" + subject.isAuthenticated());


    }
}
