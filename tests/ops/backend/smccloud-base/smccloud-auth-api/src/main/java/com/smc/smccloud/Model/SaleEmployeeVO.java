package com.smc.smccloud.Model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/4/8 10:59
 * @Descripton TODO
 */
@Data
public class SaleEmployeeVO {

    /**
     * serialVersionUID:
     *
     * @since JDK 1.8
     */
    private static final long serialVersionUID = 4873228120289275456L;

    /**
     * 员工id
     */
    private String id;
    /**
     * 员工姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String cellPhone;
    /**
     * 邮箱
     */
    private String Email;

    /**
     * 状态
     */
    private String status;

}
