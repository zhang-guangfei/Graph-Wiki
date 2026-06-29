package com.smc.smccloud.core.model.constants;

public class BaseExceptionCode {

    public static String 实体不存在 = "000";
    public static String 无权限_拒绝访问 = "001";
    public static String 编码不能为空 = "002";
    public static String 解密错误 = "003";
    public static String 加密错误 = "004";
    public static String 生成密钥错误 = "005";
    public static String FTP服务器登录失败 = "006";
    public static String FTP服务器退出失败 = "007";
    public static String FTP服务器关闭失败 = "008";
    public static String 文件上传失败 = "009";
    public static String 文件下载失败 = "010";
    public static String 文件删除失败 = "011";
    public static String 包未加注解 = "012";
    public static String 验证码错误 = "013";
    public static String 登录错误 = "014";
    public static String 类路径错误 = "015";
    public static String 导出错误 = "016";
    public static String 绑定错误 = "017";
    public static String 凭据失效 = "018";
    public static String 未授权用户 = "019";
    public static String 用户名已存在 = "020";
    public static String 邮件发送失败 = "021";
    public static String 用户管理_密码错误次数 = "022";
    public static String 用户管理_用户已被锁定 = "023";

    public BaseExceptionCode() {
    }
}
