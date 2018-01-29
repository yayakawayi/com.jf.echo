package com.jf.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00      2017/7/9 22:27      陈飞(fly)                    新建
 * <br>
 * *************************************************************************************************<br>
 */
public class ShiroDbRealm extends AuthorizingRealm {

//    @Autowired
//    private SysUserService sysUserService;

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String account = token.getUsername();
//        SysUser sysUser;
//        try {
//            sysUser = sysUserService.findUniqueBy("account", account);
//            if (sysUser == null) {
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        ShiroUser shiroUser = new ShiroUser(sysUser.getId(),
//                account, sysUser.getName());
//        String salt = sysUser.getSalt();
//        String md5Psd = sysUser.getPassword();
        return null;
//        new SimpleAuthenticationInfo(shiroUser,
//                md5Psd, ByteSource.Util.bytes(salt), getName());

    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

}
