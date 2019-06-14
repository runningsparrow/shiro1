package com.sparrow.shiro.realm;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.Map;


public class CustomRealm extends AuthorizingRealm {

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    //创建一个集合用于测试
    Map<String,String> userMap = new HashMap<String, String>(16);

    {
        userMap.put("Mark","123456");

        //设置一个类属性named的值
        super.setName("customRealm");
    }


    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //1，从主题穿过来的认证信息中，获得用户名
        String userName = (String) token.getPrincipal();

        //2，通过用户名到数据中去获取凭证
        String password =  getPasswordByUserName(userName);
        //如果密码不存在，返回null
        if(password == null){
            return null;
        }
        //如果走到这里，说明密码存在
        //则创建一个返回对象
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                "mark",password,"customRealm"
        );
        return authenticationInfo;
    }

    /*
    模拟数据库查询凭证
     */
    private String getPasswordByUserName(String userName){
        return userMap.get(userName);
    }
}
