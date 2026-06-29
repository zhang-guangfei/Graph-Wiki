package com.sales.ops.dto.ips;

/**
 * @description:
"userInfo":{ // 用户信息
"userOrderNo": "", //用户订单号
"userItemNo":"", // 用户订单项号
"userModelNo":"", //用户型号
},
 * @author: B91717
 * @time: 2024/12/17 16:38
 */
public class IpsUserInfoDto {

    private String userOrderNo; // VIP代码
    private String userItemNo; // VIP优先级
    private String userModelNo; // 最终用户订单号

    public String getUserOrderNo() {
        return userOrderNo;
    }

    public void setUserOrderNo(String userOrderNo) {
        this.userOrderNo = userOrderNo;
    }

    public String getUserItemNo() {
        return userItemNo;
    }

    public void setUserItemNo(String userItemNo) {
        this.userItemNo = userItemNo;
    }

    public String getUserModelNo() {
        return userModelNo;
    }

    public void setUserModelNo(String userModelNo) {
        this.userModelNo = userModelNo;
    }
}
