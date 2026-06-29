package com.smc.smccloud.core.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/4/27
 * @Descripton 客户组
 */
public enum GroupCustomerEnum {

    A0000("A0000","SMC（北京）","223110","BJ"),
    C1D70("C1D70","SMC（中国）","223110","BJ"),
    C07GR("C07GR","SMC（天津）","223120","BJ"),
    C1D72("C1D72","SMC（广州）","223110","GZ"),
    C1D71("C1D71","SMC（上海）","241400","BJ");

    private String customerName;
    private String dlvDeptNo;
    private  String area;
    private  String customerNo;


    GroupCustomerEnum(String customerNo,String customerName, String dlvDeptNol,String area) {
        this.customerName = customerName;
        this.dlvDeptNo = dlvDeptNol;
        this.area=area;
        this.customerNo=customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDlvDeptNo() {
        return dlvDeptNo;
    }

    /**
     * 判断是否在枚举里面
     */

    public static boolean exitGroupCustomerEnum(String customerNo) {
        List<String> custList = new ArrayList<>();
        for (GroupCustomerEnum item : GroupCustomerEnum.values()) {
            if(item.customerNo.equalsIgnoreCase(customerNo))
            {
                return true;
            }
        }
        return false;
    }

    public static  boolean IsCustomerOfBJArea(String  customerNo)
    {
        for (GroupCustomerEnum item : GroupCustomerEnum.values()) {
             if(item.area.equalsIgnoreCase("BJ"))
             {
                 return true;
             }
        }
        return false;
    }

}
