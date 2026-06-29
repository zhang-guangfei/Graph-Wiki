package com.smc.smccloud.model.binorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author wuweidong
 * @create 2023/6/2 10:28
 * @description
 */
@Data
public class BinTrialJobManageVO {

    public Long id ;
    public String jobNo;
    public String jobName ;
    public String jobDescription ;
    public String warehouseCode ;
    public String warehouseMaster ;

    public int status ;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date planExecuteDate ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date executeStartTime ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date executeEndTime ;


    public int isDeleted ;

    public String resultTableName ;


    public String createUser ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date createTime ;


    public String updateUser ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date updateTime ;
}
