package com.smc.smccloud.util;


import java.lang.annotation.*;

/**
 *
 * ClassName: CheckAuth <br/>
 * Function: 验证http请求是否携带身份令牌. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2021年1月12日 下午2:16:48 <br/>
 *
 * @version
 * @since JDK 1.8
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuth {

}