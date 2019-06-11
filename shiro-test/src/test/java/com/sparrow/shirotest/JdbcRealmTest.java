package com.sparrow.shirotest;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmTest {

    //创建数据池
    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro1");
        dataSource.setUsername("root");
        dataSource.setPassword("sparrow1");
    }

    @Test
    public void testAuthentication(){


        //创建jdbcrealm对象
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //打开检查权限的开关，否则 下面 subject.checkPermission("user:update"); 会报错
        jdbcRealm.setPermissionsLookupEnabled(true);


        //1 构建SecrurityManager 环境

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //将新建的账号传给securitymanager
        defaultSecurityManager.setRealm(jdbcRealm);

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
        subject.checkRoles("admin","user");

        //检查权限
        subject.checkPermission("user:update");

        subject.logout();

        System.out.println("logout isAuthenticated : "+subject.isAuthenticated());


    }
}
