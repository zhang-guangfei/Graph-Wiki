package com.smc.smccloud.model.borrowstock;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2021-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("brw_detail")
public class BrwDetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 借货申请id
     */
    private Integer brwId;

    /**
     * 项号
     */
    private Integer itemId;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 型号名称
     */
    private String modelName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 库存id
     */
    @TableField("Inventory_id")
    private Long inventoryId;

    /**
     * 库存key
     */
    private String inventoryKeys;

    /**
     * 归还类型
     */
    private String returnType;

    private Integer returnQty;

    private Integer optStatus;

    private Integer expQty;

    /**
     * 晚归还原因
     */
    private String lateReturnReason;

    /**
     * 晚归还日期
     */
    private Date lateReplyDate;

    /**
     * 晚归还回复人
     */
    private String lateReplyPsn;

    @TableField( value = "create_time" , fill = FieldFill.INSERT)
    private Date createTime;

    @TableField( value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    private String updateUser;

    private String createUser;

    private String warehouseCode;

    private Date shipDate;
}
