package com.smc.smccloud.model.pd;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2023-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_as_pd_borrow_data")
public class OpsAsPdBorrowDataDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 盘点批次号
     */
    @TableField("pd_batch_no")
    private String pdBatchNo;

    /**
     * 仓库代码
     */
    @TableField("warehouse_code")
    private String warehouseCode;

    /**
     * 数据类型1借库数据
     */
    @TableField("pd_data_type")
    private String pdDataType;

    /**
     * 完整单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 数量
     */
    @TableField("qty")
    private Integer qty;

    /**
     * 借库日期
     */
    @TableField("borrow_date")
    private Date borrowDate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(exist = false)
    private Date borrowDateUI;

    /**
     * 借库担当
     */
    @TableField("borrow_person")
    private String borrowPerson;

    /**
     * 借库部门
     */
    @TableField("borrow_dept")
    private String borrowDept;

    /**
     * 1 作废 0 有效
     */
    @TableField("del_flag")
    private Boolean delFlag;

    @TableField("remark")
    private String remark;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date updateTimeUI;


}
