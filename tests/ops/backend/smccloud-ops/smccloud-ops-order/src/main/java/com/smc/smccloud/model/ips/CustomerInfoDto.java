package com.smc.smccloud.model.ips;

import lombok.Data;

/**
 * @description:
 * "customerName" :"",//客户名称
 * "customerOrderNo": "",//日本 订单号
 * "customerItemNo":"",//日本订单项号
 * "customerKogo":"",//日本工号
 * "customerShelfNo":"",//日本货架号
 * "customerRemarks ":"",//日本备注
 * "issueDate":"",//作成日
 * "customerSerialNo ":"",//日本连编号
 * "accumOrder ":"",//累计受注
 * "shikomiNo": "",//SHIKOMI号
 * "customerReserved ": "",//重要管理客户识别
 * "warehouseId": "",//仓库ID
 * "tradeStyle": "",//贸易区分
 * "customerBarcode": "",//日本入库号
 * "customerConfigNo ": "",//日本部署
 * "customerArrangeNo": "",//日本手配
 * "customerArrangeItem": "",//日本手配项号
 * "memo": "",//备忘录编号
 * "customerRemarks": "",//荷姿/ 备注
 * "simpleModel": "",//简易品番
 * "customerShelfNo2": "",//日本货架号2
 * "labelBarcode": "",//条型码
 * "contractNo": "",//订货合同号
 * "contractRemnant": "",//订货合同剩余数量
 * "goodsCode ": "",//制品号
 * "duty": "",//税号
 * "originQualify ": "",//原产资格
 * "priorInformationNumber": "",//先前情报号
 * "priorInformationLineNumber ": "",//先前情报线
 * "originQualifySign": "",//原产资格记号
 * "customerProductName": "",//客户品名
 * "arrangeNo": "",// 手配号
 * "arrangeItem": "",// 手配项号
 * "shikomiNo": "",
 * @author: B91717
 * @time: 2024/12/17 16:33
 */
@Data
public class CustomerInfoDto {

    private String customerName; // 客户名称
    private String customerOrderNo; // 日本 订单号
    private String customerItemNo; // 日本订单项号
    private String customerKogo; // 日本工号
    private String customerShelfNo; // 日本货架号
    private String customerRemarks; // 日本备注
    private String issueDate; // 作成日
    private Integer customerSerialNo; // 日本连编号
    private String accumOrder; // 累计受注
    private String shikomiNo; // SHIKOMI号
    private String customerReserved; // 重要管理客户识别
    private String warehouseId; // 仓库ID
    private String tradeStyle; // 贸易区分
    private String customerBarcode; // 日本入库号
    private String customerConfigNo; // 日本部署
    private String customerArrangeNo; // 日本手配
    private String customerArrangeItem; // 日本手配項号
    private String memo; // 备忘录编号
    private String remarks; // 荷姿/ 备注
    private String simpleModel; // 简易品番
    private String customerShelfNo2; // 日本货架号2
    private String labelBarcode; // 条型码
    private String contractNo; // 订货合同号
    private Integer contractRemnant; // 订货合同剩余数量
    private String goodsCode; // 制品号
    private String duty; // 税号
    private String originQualify; // 原产资格
    private String priorInformationNumber; // 先前情报号
    private String priorInformationLineNumber; // 先前情报线
    private String originQualifySign; // 原产资格記号
    private String customerProductName; // 客户品名
    private String arrangeNo; // 手配号
    private String arrangeItem; // 手配項号




}
