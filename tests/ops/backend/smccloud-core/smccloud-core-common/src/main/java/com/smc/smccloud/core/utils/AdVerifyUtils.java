package com.smc.smccloud.core.utils;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.apache.commons.lang3.StringUtils;

import javax.naming.AuthenticationException;

public class AdVerifyUtils {

    public AdVerifyUtils() {
    }

    public static ResultVo<String> errorCodeVerify(String key) {
        ResultVo<String> result = ResultVo.failure();
        if (StringUtils.contains(key, "525")) {
            result.code("525");
            result.message("用户不存在！");
        }

        if (StringUtils.contains(key, "52e")) {
            result.code("52e");
            result.message("密码错误！");
        }

        if (StringUtils.contains(key, "530")) {
            result.code("530");
            result.message("此时不允许登录！");
        }

        if (StringUtils.contains(key, "531")) {
            result.code("531");
            result.message("在此工作站上不允许登录！");
        }

        if (StringUtils.contains(key, "532")) {
            result.code("532");
            result.message("密码过期，请登录门户网站，修改密码！");
        }

        if (StringUtils.contains(key, "533")) {
            result.code("533");
            result.message("账户已禁用，请联系人力资源部！");
        }

        if (StringUtils.contains(key, "701")) {
            result.code("701");
            result.message("账户已过期，请联系人力资源部！");
        }

        if (StringUtils.contains(key, "773")) {
            result.code("773");
            result.message("请登录门户网站，修改密码！");
        }

        if (StringUtils.contains(key, "775")) {
            result.code("775");
            result.message("账户已锁定，请10分钟后登录！");
        }

        return result;
    }

    public static void errorCodeVerifyException(String key) throws AuthenticationException {
        if (StringUtils.contains(key, "525")) {
            throw new AuthenticationException("用户不存在");
        } else if (StringUtils.contains(key, "52e")) {
            throw new AuthenticationException("密码错误！");
        } else if (StringUtils.contains(key, "530")) {
            throw new AuthenticationException("此时不允许登录！");
        } else if (StringUtils.contains(key, "531")) {
            throw new AuthenticationException("在此工作站上不允许登录！");
        } else if (StringUtils.contains(key, "532")) {
            throw new AuthenticationException("密码过期，请修改密码！");
        } else if (StringUtils.contains(key, "533")) {
            throw new AuthenticationException("账户已禁用，请联系人力资源部！");
        } else if (StringUtils.contains(key, "701")) {
            throw new AuthenticationException("账户已过期，请联系人力资源部！");
        } else if (StringUtils.contains(key, "773")) {
            throw new AuthenticationException("请修改密码！");
        } else if (StringUtils.contains(key, "775")) {
            throw new AuthenticationException("账户已被锁定，请10分钟后登录！");
        }
    }
}
