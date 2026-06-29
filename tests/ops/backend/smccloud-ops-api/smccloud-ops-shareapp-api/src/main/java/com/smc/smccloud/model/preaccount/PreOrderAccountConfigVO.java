package com.smc.smccloud.model.preaccount;

import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2024/7/8 9:01
 * @Descripton TODO
 */
@Data
public class PreOrderAccountConfigVO {

    /**ID*/
    public Integer id ;

    // 优先级( 值越小优先级越高)
    public Integer priority ;

    // 优先级( 值越小优先级越高)
    public String inventoryTypeCode ;

    // 客户代码
    public String customerNo ;

    // PPL号
    public String ppl ;

    // 项目代码
    public String projectCode ;

    // 群代码
    public String groupCustomerNo ;

    public String salesInfoNo ;

    // 是否允许延期
    public Boolean isDelay ;

    // 延期MAX天数
    public Integer delayMaxDay ;

    public Boolean delFlag ;

    public Date createTime ;

    public String creator ;

    public Date modifyTime ;

    public String modifier ;




}
