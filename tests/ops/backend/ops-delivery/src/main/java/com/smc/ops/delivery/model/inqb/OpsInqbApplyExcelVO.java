package com.smc.ops.delivery.model.inqb;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class OpsInqbApplyExcelVO {

    @ExcelProperty("催促申请号")
    private String inqbApplyNo;

    @ExcelProperty("门户申请号")
    private String sourcesApplyNo;

    @ExcelProperty("型号")
    private String modelNo;

    @ExcelProperty("数量")
    private Integer quantity;

    @ExcelProperty("最终用户")
    private String endUser;

    @ExcelProperty("用户所属部门")
    private String userDept;

    @ExcelProperty("催促状态")
    private String inqbStatus;

    @ExcelProperty("有效状态")
    private String inqbValidity;

    @ExcelProperty("有效期")
    private Date expirationDate;

    // bug17611 INQ-B导出问题,字段类型错误
    @ExcelProperty("期望货期")
    private Integer expectedDeliveryDate;

    @ExcelProperty("催促描述")
    private String description;

    @ExcelProperty("催促日期")
    private Date inqbDate;

    @ExcelProperty("催促原因")
    private String inqbClassify;

    @ExcelProperty("催促部门")
    private String inqbDept;

    @ExcelProperty("催促人")
    private String inqbUser;

    @ExcelProperty("催促人姓名")
    private String inqbUserName;

    @ExcelProperty("拆分类型")
    private String splitType;

    @ExcelProperty("期望供应商")
    private  String supplierCode;

    @ExcelProperty("子项型号")
    private String subModel;

    @ExcelProperty("子项数量")
    private Integer subQty;

    @ExcelProperty("子项申请号")
    private String taskNo;

    @ExcelProperty("是否催促")
    private String handleResult;

//    @ExcelProperty("供应商")
//    private String replyDept;

    /**
     * 以下直接获取子项的信息即可
     */
    @ExcelProperty("回复部门")
    private String replySupplierDept;

    @ExcelProperty("回复号")
    private String replyNo;

    @ExcelProperty("回复货期")
    private Integer replyDeliveryDays;

    @ExcelProperty("回复人")
    private String replyUser;

    @ExcelProperty("回复时间")
    private Date replyTime;

    @ExcelProperty("回复原因代码")
    private String replyReasonCode;

    @ExcelProperty("回复原因")
    private String replyReason;

    @ExcelProperty("回复备注")
    private String replyRemark;




}
