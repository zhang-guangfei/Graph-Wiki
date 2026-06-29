package com.sales.ops.service.ips;

import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.dto.ips.IpsAddEmailInfoDto;
import com.sales.ops.dto.ips.IpsOrderWarehouseInfoDto;
import com.sales.ops.dto.ips.IpsReceiveOrderAllOriginalInfoDto;
import com.sales.ops.dto.ips.PsiDataType;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.List;
import java.util.Map;

/**
 * @author B91717
 * 向IPS发采购单时，用到的公共的方法类
 */
public interface IpsPurchaseSendCommonService {


    /**
     * 根据OpsPurchaseinvoice信息，转换为IPS写入的格式
     * 提取出公共方法
     * @param invoiceList
     * @return
     * @throws Exception
     */
    public List<IpsReceiveOrderAllOriginalInfoDto> ipsSendOrderConvert(List<OpsPurchaseinvoice> invoiceList, Map<String, String> manuSupplierConfig, String suppily, String batchNo,Integer supplierPayment, String warehouseFlag) throws Exception;


    /**
     * 根据配置，获取OPS运输方式与IPS运输方式的转换
     * 根据配置，获取转换适配器供应商为IPS供应商
     * @return
     */
    public ResultVo<String> getIpsTranstypeByConfig(String code, String codeType);

    /**
     * 根据OPS的仓库代码，去获取IPS发单所需的仓库信息
     * @param warehouseCode
     * @param dictCode
     * @return
     */
    public IpsOrderWarehouseInfoDto getWarehouseInfo(String warehouseCode, String dictCode);


    /**
     * 生成IPS发单的批次号,根据供应商生成
     * @param suppily
     * @return
     */
    public ResultVo<String> generateIpsOrderBatchNo(String suppily);

    // 回更采购表批次号cnno，公共方法提出
    public void updatePurchaseInvoiceStatus(List<OpsPurchaseinvoice> invoiceList, String cnNo, String filePath);


    /**
     * 获取字典配置的新旧发单标识
     * @param supplierid
     * @return
     */
    public ResultVo<PsiDataType> getSwitchflag(String supplierid);

    public ResultVo<IpsAddEmailInfoDto> generatePsiEmailInfo(OpsPurchaseinvoice purchaseinvoice);
}
