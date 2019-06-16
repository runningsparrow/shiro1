package com.sparrow.shirotest;

import com.sparrow.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomRealmTest {

    @Test
    public void testAuthentication(){


        CustomRealm customRealm = new CustomRealm();


        //1 构建SecrurityManager 环境

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //将新建的账号传给securitymanager
        defaultSecurityManager.setRealm(customRealm);

        //added for shiro 加密 ====start
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //设置加密算法
        matcher.setHashAlgorithmName("md5");
        //设置加密次数
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);


        //added for shiro 加密 ====end



        //2 主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken( "Mark","123456");
        //登陆
        subject.login(token);
        //登陆之后认证为true
        System.out.println("isAuthentiated:" + subject.isAuthenticated());

//        //检查角色
//        subject.checkRole("admin");
                //检查角色
        subject.checkRole("admin");
//
//        //检查多个角色
////        subject.checkRoles("admin","user");
//
//        //检查权限
//        subject.checkPermission("user:update");
        //        //检查权限
        subject.checkPermission("user:add");

        subject.logout();

        System.out.println("logout isAuthenticated : "+subject.isAuthenticated());


    }

    //测试 md5
    public static void main(String[] args){
        //此种为未加盐写法
//        Md5Hash md5Hash = new Md5Hash("123456");
        //加盐的加密写法
        Md5Hash md5Hash = new Md5Hash("123456","Mark");
        System.out.println(md5Hash.toString());

    }

}
