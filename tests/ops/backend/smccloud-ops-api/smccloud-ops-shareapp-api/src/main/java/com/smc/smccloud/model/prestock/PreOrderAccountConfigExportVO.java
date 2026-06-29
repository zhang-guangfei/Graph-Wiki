package com.smc.smccloud.model.prestock;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * @Author lyc
 * @Date 2024/11/26 16:15
 * @Descripton TODO
 */
@Data
public class PreOrderAccountConfigExportVO {

    @ExcelProperty("优先级")
    private Integer priority ;

    @ExcelProperty("库存类型")
    private String inventoryTypeCode ;

    @ExcelProperty("库存描述")
    private String inventoryTypeCodeName ;

    @ExcelProperty("客户代码")
    private String customerNo ;
    @ExcelProperty("客户名称")
    private String customerName;

    @ExcelProperty("客户群代码")
    private String groupCustomerNo ;

    @ExcelProperty("pplNo")
    private String pplNo ;

    @ExcelProperty("PJ")
    private String projectCode ;

    @ExcelProperty("是否延迟")
    private String isDelayStr ;

    @ExcelProperty("延期MAX天数")
    private Integer delayMaxDay ;

}
