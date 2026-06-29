package com.smc.smccloud.model.prestock;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author wuweidong
 * @create 2024/1/2 15:00
 * @description
 */
@Data
public class PreOrderAccountConfigDTO {
    /**ID*/
    public Integer id ;

    public Integer priority ;

    public String inventoryTypeCode ;

    public String customerNo ;
    public String customerName;

    public String ppl ;

    public String projectCode ;
    public String groupCustomerNo ;

    public String salesInfoNo ;

    public Boolean isDelay ;

    public Integer delayMaxDay ;

    public Boolean delFlag ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date createTime ;

    public String creator ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date modifyTime ;
    public String modifier ;
}
