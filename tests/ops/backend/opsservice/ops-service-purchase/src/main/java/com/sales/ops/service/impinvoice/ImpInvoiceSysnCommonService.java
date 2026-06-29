package com.sales.ops.service.impinvoice;

import com.sales.ops.db.entity.OpsImpinvoiceFailLog;
import com.sales.ops.dto.util.CommonResult;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author B91717
 * @description: impInvoice同步时，用到的公共的转换方法
 */
public interface ImpInvoiceSysnCommonService {

    String getWarehouseCodeBySMCCode(String smcCode, int extNote);


    /**
     * 计算预计到达日期
     *
     * @param supplierCode 供应商
     * @param transType    运输方式
     * @param dlvDate      出厂纳期
     * @return
     */
    Date calcEsArrivalDate(String supplierCode, String transType, Date dlvDate);

    /**
     * 计算预计到货日期
     * @param warehouseCode
     * @param supplierCode
     * @param transType
     * @param dlvDate
     * @return
     */
    Date calcEsArrivalDate(String warehouseCode, String supplierCode, String transType, Date dlvDate);

    /**
     * 发票类型转换
     * @param supplierCode
     * @return
     */
    int invoiceTypeConvert(String supplierCode,String type);

    Map<String, String> getSuppilyNameMapper();


    /**
     * 获取关务状态转换
     * @param statusCode
     * @return
     */
    public Integer getGWMasterStatus(String statusCode);

    public boolean isNoCommercialValue(String smcCode);

    public String getSupplierCodeFromJPWhereCode(String whereCode);
    //中国制造根据仓库代码获取供应商代码
    public String convertCNMSupplierCode(String code);

//    ResultVo<Date> getLastExecTimeFromSyncMidManage(String jobName);
//
//    ResultVo<String> upSyncMidManageLastExcTime(String jobName, Date date);

    /**
     * 同步适配器数据时，异常记录写入异常表中
     * @param sourceType
     * @param sourceId
     * @param errorDataStr
     * @param remark
     * @return
     */
    ResultVo<String> insertPoFailLog(String sourceType,String sourceId,String sourceTable,String errorDataStr,String remark);

    ResultVo<List<OpsImpinvoiceFailLog>> getUnHandleFailLog(String sourceType);

    ResultVo<String> updateHandleFailLog(List<OpsImpinvoiceFailLog> list);

    // 根据适配器供应商转换为ops供应商
    ResultVo<String> getOpsSupplierByConfig(String code, String codeType);

    /**
     * imp导入异常时，写入异常表的同时，增加异常邮件的发送
     * @param message
     */
    CommonResult impInvoiceMailMessage(String message);

}
