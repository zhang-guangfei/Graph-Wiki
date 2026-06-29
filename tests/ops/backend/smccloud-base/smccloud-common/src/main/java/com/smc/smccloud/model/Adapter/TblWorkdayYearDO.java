package com.smc.smccloud.model.Adapter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2022-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Tbl_WorkDay_Year")
public class TblWorkdayYearDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工作日
     */
    @TableId(value = "Workday_date", type = IdType.AUTO)
    private String workdayDate;

    /**
     * 操作日期
     */
    @TableField("Optdate")
    private String Optdate;

    /**
     * 操作担当
     */
    @TableField("Username")
    private String Username;

    /**
     * 节假日标识
     */
    @TableField("OptFlag")
    private String OptFlag;

    /**
     * 节假日标识_日本
     */
    @TableField("OptFlag_JP")
    private String optflagJp;

    @TableField("OptFlag_CN")
    private String optflagCn;

    @TableField("OptFlag_AM")
    private String OptFlagAM;


}
