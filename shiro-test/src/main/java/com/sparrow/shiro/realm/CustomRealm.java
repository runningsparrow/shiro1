package com.sparrow.shiro.realm;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class CustomRealm extends AuthorizingRealm {

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String userName = (String) principals.getPrimaryPrincipal();

        //从数据库或者缓存中获取角色
        Set<String> roles = getRolesByUserName(userName);

        Set<String> permissions = getPermissionsByUserName(userName);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);

        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionsByUserName(String userName){

        Set<String> sets = new HashSet<>();
        sets.add("user:delete");
        sets.add("user:add");

        return sets;


    }

    private Set<String> getRolesByUserName(String userName){
        Set<String> sets = new HashSet<>();

        sets.add("admin");
        sets.add("user");

        return sets;
    }




    //创建一个集合用于测试
    Map<String,String> userMap = new HashMap<String, String>(16);

    {
//        userMap.put("Mark","123456");

        //此处修改密码为 md5加密的密文用于加密测试
//        userMap.put("Mark","e10adc3949ba59abbe56e057f20f883e");


        //此处修改密码为 md5加密的密文用于加密测试, 同时加盐的md5值
        userMap.put("Mark","283538989cef48f3d7d8a1c1bdf2008f");

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
                "Mark",password,"customRealm"
        );
        //设置加盐 用于加盐的md5
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Mark"));

        return authenticationInfo;
    }

    /*
    模拟数据库查询凭证
     */
    private String getPasswordByUserName(String userName){
        return userMap.get(userName);
    }
}
