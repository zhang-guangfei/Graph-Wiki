package com.smc.smccloud.model.binorder;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author wuweidong
 * @create 2023/6/2 10:01
 * @description
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_bin_trial_salesbranch_config")
public class BinTrialSalesBranchConfigDO
{
    private static final long serialVersionUID = 1L;
    /**ID*/
    @TableId(value = "id", type = IdType.AUTO)
    public Long id ;


    @TableField("job_id")
    public Long jobId ;

    @TableField("sales_branch_id")
    public String salesBranchId ;

    @TableField("warehouse_code")
    public String warehouseCode ;

    @TableField("warehouse_code_update")
    public String warehouseCodeUpdate ;

    @TableField("warehouse_master")
    public String warehouseMaster;

    @TableField("warehouse_master_update")
    public String warehouseMasterUpdate ;

    @TableField("create_user")
    public String createUser ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    public Date createTime;

    @TableField("update_user")
    public String updateUser ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("update_time")
    public Date updateTime ;

}
