package com.smc.smccloud.model.binorder;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author wuweidong
 * @create 2023/6/2 10:33
 * @description
 */
@Data
public class BinTrialSalesBranchConfigVO {

    public Long id ;

    public Long jobId ;

    public  String jobNo;

    public String salesBranchId ;

    public String branchName;

    public String companyName;

    public String areaName;

    public String warehouseCode ;

    public String warehouseCodeUpdate ;

    public  String warehouseName;

    public String warehouseMaster;
    public String warehouseMasterUpdate;
    public String masterName;
    public String createUser ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date createTime ;

    public String updateUser ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date updateTime ;

}
