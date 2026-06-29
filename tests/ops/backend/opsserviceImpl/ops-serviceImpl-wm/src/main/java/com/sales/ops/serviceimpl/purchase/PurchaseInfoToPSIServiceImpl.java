package com.sales.ops.serviceimpl.purchase;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.util.DateUtils;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.ImpInvoiceDetailMapper;
import com.sales.ops.db.dao.ImpInvoiceMasterMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.ImpInvoiceMasterDao;
import com.sales.ops.dto.ips.IpsSignDataBatchPushVO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.ipsPurchase.IpsPurchaseCommonEnum;
import com.sales.ops.feign.delivery.OpsDeliveryDataToIpsFeignApi;
import com.sales.ops.service.purchase.PurchaseInfoToPSIService;
import com.sales.ops.serviceimpl.event.v3.deliveryPush.OrderDeliveryPushEventServiceImpl;
import com.sales.ops.serviceimpl.po.PurchaseImpInfoToPSIServiceImpl;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class PurchaseInfoToPSIServiceImpl implements PurchaseInfoToPSIService {

    private ImpInvoiceDetailMapper impinvoicedetailMapper;
    private ImpInvoiceMasterMapper impInvoiceMasterMapper;
    private OpsDeliveryDataToIpsFeignApi opsDeliveryDataToIpsFeignApi;
    private ImpInvoiceMasterDao impInvoiceMasterDao;
    private TransactionTemplate transactionTemplate;
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    private static final String SOURCE_SYSTEM = "OPS";
    private PurchaseImpInfoToPSIServiceImpl purchaseImpInfoToPSIService;

    //optStatus中的两位是独立更新的，其中第一位代表发票入库推送状态，第二位代表发票签收推送状态，0代表未推送，1代表已推送，不能将两位联合起来看成四种状态码
    public enum OptStatus{
        INIT('0', "未推送"),
        PUSHED('1', "已推送"),
        ;
        private Character code;
        private String desc;

        OptStatus(Character code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Character getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }

    @Override
    public String pushPurchaseInvoiceInfoToPSI() {
        //查询该推送 【发票签收时间】的发票数据
        //bug 19413.38 2.将imp_invoice_master表的opt_status改为编码为 0：未发票签收，1已发票签收
        List<ImpInvoiceMaster> impInvoiceMasterSignDate = impInvoiceMasterDao.getImpInvoiceMasterSignDateV3();
        if (CollectionUtils.isEmpty(impInvoiceMasterSignDate)) {
            return "总计:0, 成功:0, 失败:0";
        }
        int total = impInvoiceMasterSignDate.size();
        int successCount = 0;
        int failCount = 0;
        List<String> failInvoiceNos = new ArrayList<>();
        for (ImpInvoiceMaster impInvoiceMaster : impInvoiceMasterSignDate) {
            try {
                // 单条master一个事务：推送 + 更新状态在同一个事务中
                transactionTemplate.executeWithoutResult(status -> {
                    try {
                        //推送发票签收信息
                        // bug 19413.38 1.将推送发票签收时间和发票入库时间改为只推送发票签收时间 imp_invoice_master.arrive_date
                        getIpsReceiveSignImpInfoFromAll(impInvoiceMaster, OrderDeliveryPushEventServiceImpl.PoInvoiceOperation.SIGN.getCode());
                        //更新推送状态为已推送发票签收时间
                        ImpInvoiceMaster update = new ImpInvoiceMaster();
                        update.setId(impInvoiceMaster.getId());
                        update.setOptStatus(OptStatus.PUSHED.getCode().toString());
                        update.setUpdateTime(new Date());
                        update.setUpdateUser("AUTO_JOB");
                        impInvoiceMasterMapper.updateByPrimaryKeySelective(update);
                    } catch (OpsException e) {
                        // 标记事务回滚
                        status.setRollbackOnly();
                        throw new RuntimeException(e);
                    }
                });
                successCount++;
            } catch (Exception e) {
                failCount++;
                failInvoiceNos.add(impInvoiceMaster.getInvoiceNo());
                log.error("推送发票入库信息失败, invoiceNo={}", impInvoiceMaster.getInvoiceNo(), e);
            }
        }
        //返回结果，包含成功和失败的条数，以及失败的发票号
        String summary = String.format("总计:%d, 成功:%d, 失败:%d", total, successCount, failCount);
        if (!failInvoiceNos.isEmpty()) {
            summary += ", 失败发票号:" + failInvoiceNos;
        }
        return summary;
    }

    private void getIpsReceiveSignImpInfoFromAll(ImpInvoiceMaster impInvoiceMaster, String type) throws OpsException {
        //查询此发票的所有detail，并按单号去重，得到采购单条数
        Map<String, List<ImpInvoiceDetail>> impInvoiceDetailMap = getImpInvoiceDetails(impInvoiceMaster.getInvoiceNo(), impInvoiceMaster.getId());
        //批次号和当前时间
        int batchNum = impInvoiceDetailMap.size();
        Date now = new Date();
        //OPS20260126140000100NX1795-51
        //系统缩称+ YYYYMMDDHHmmssSSS+invoiceNo (20+n位)
        String batchNo = "OPS" + DateUtils.format(now, "yyyyMMddHHmmssSSS") + impInvoiceMaster.getInvoiceNo();

        List<IpsReceiveSignImpInfoFromAll> signList = new ArrayList<>();
        int qtyAll = 0;
        for (Map.Entry<String, List<ImpInvoiceDetail>> entry : impInvoiceDetailMap.entrySet()) {
            String orderNo = entry.getKey();
            List<ImpInvoiceDetail> impInvoiceList = impInvoiceDetailMap.get(orderNo);
            //数量取和
            int qty = 0;
            ImpInvoiceDetail impInvoiceDetail = impInvoiceList.get(0);
            for (ImpInvoiceDetail detail : impInvoiceList) {
                Integer quantity = detail.getQuantity();
                if (quantity != null) {
                    qty += quantity;
                }
            }
            //创建数据,发票入库推送子表数据
            IpsReceiveSignImpInfoFromAll info = createIpsReceiveSignImpInfoFromAll(impInvoiceMaster, impInvoiceDetail, qty, batchNo, type, impInvoiceMaster.getUpdateUser());
            //统一推送
            signList.add(info);
            qtyAll += qty;
        }
        // bug 19413.38 3.增加推送目标表：ips_receive_invoice_master_from_all，作为发票主表，一条发票推送一次
        IpsReceiveInvoiceMasterFromAll invoiceMaster = createIpsReceiveInvoiceMasterFromAll(impInvoiceMaster, qtyAll,
                batchNum, batchNo, type, impInvoiceMaster.getUpdateUser());
        // bug 19413.38 4.修改发票明细表推送方式，将单条ips_receive_sign_imp_info_from_all改为多条批量推送，和ips_receive_invoice_master_from_all一起推送，使用同一个接口和同一个事务
        pushSignImpInfoToPSI(invoiceMaster, signList);
    }
    // bug 19413.38 4.修改发票明细表推送方式，将单条ips_receive_sign_imp_info_from_all改为多条批量推送，和ips_receive_invoice_master_from_all一起推送，使用同一个接口和同一个事务
    private void pushSignImpInfoToPSI(IpsReceiveInvoiceMasterFromAll invoiceMaster, List<IpsReceiveSignImpInfoFromAll> signList) throws OpsException {
        IpsSignDataBatchPushVO pushVO = new IpsSignDataBatchPushVO();
        pushVO.setInvoiceMaster(invoiceMaster);
        pushVO.setSignList(signList);
        CommonResult<Void> result = opsDeliveryDataToIpsFeignApi.pushOpsSignDataBatch(pushVO);
        if (!result.isSuccess()) {
            throw Exceptions.OpsException("推送ips签收信息失败:" + result.getMessage());
        }
    }


    // 字段详细取值见bug 19413
    // sheet:外部系统的签收入库信息 表2ips_receive_sign_imp_info_from_*
    // https://smc.sharepoint.cn/:x:/r/sites/ITDept/Shared%20Documents/03%E9%A1%B9%E7%9B%AE%E7%AE%A1%E7%90%86%E6%96%87%E6%A1%A3/22%20PSI%E9%A1%B9%E7%9B%AE/05%20PSI%E5%AF%B9%E6%8E%A5%E5%A4%96%E9%83%A8%E7%B3%BB%E7%BB%9F_%E5%90%88%E5%B9%B6%E7%89%88V1.0.xlsx?d=w797c400b7d0e4abfbe40aeab5f98a193&csf=1&web=1&e=chIS4B
    //创建单条数据到psi
    private IpsReceiveSignImpInfoFromAll createIpsReceiveSignImpInfoFromAll(ImpInvoiceMaster impInvoiceMaster, ImpInvoiceDetail imp, int qty,  String batchNo, String operateType, String operator) {
        IpsReceiveSignImpInfoFromAll info = new IpsReceiveSignImpInfoFromAll();
        info.setSourceSystem(SOURCE_SYSTEM);
        String supplierSystem = OrderDeliveryPushEventServiceImpl.SupplierCodeEnum.getDescByCode(impInvoiceMaster.getSupplierCode());
        info.setSupplierSystem(supplierSystem);
        info.setSrcOrderNo(imp.getPono());
        info.setSrcOrderItem(imp.getPoitemno() != null ? imp.getPoitemno().toString() : null);
        info.setOrderNo(imp.getPono());
        info.setOrderItem(imp.getPoitemno() != null ? imp.getPoitemno().toString() : null);
        info.setModelNo(imp.getModelNo());
        info.setDataType(operateType);
        info.setQty(new BigDecimal(qty));
        info.setDeviationQty(null);
        info.setDeviationReason(null);
        info.setProcessStatus("1"); // 0未签收\未入库，1已签收\已入库，
        info.setProcessPlace(impInvoiceMaster.getReceiveWarehouseCode());
        if (OrderDeliveryPushEventServiceImpl.PoInvoiceOperation.SIGN.getCode().equals(operateType)) {
            info.setProcessDate(impInvoiceMaster.getArriveDate());
        } else if (OrderDeliveryPushEventServiceImpl.PoInvoiceOperation.COMFIRM.getCode().equals(operateType)) {
            info.setProcessDate(impInvoiceMaster.getConfirmDate());
        }
        info.setProcessorCode(operator);
        info.setCreateTime(new Date());
        info.setCreateUser("OPS");
        info.setInvoiceNo(imp.getInvoiceNo());

        info.setInvoiceBatchNo(batchNo);
        info.setAdditionInfo(null);
        return info;
    }

    // sheet:外部系统的签收入库信息 表1 ips_receive_invoice_master_from_all
    // https://smc.sharepoint.cn/:x:/r/sites/ITDept/Shared%20Documents/03%E9%A1%B9%E7%9B%AE%E7%AE%A1%E7%90%86%E6%96%87%E6%A1%A3/22%20PSI%E9%A1%B9%E7%9B%AE/05%20PSI%E5%AF%B9%E6%8E%A5%E5%A4%96%E9%83%A8%E7%B3%BB%E7%BB%9F_%E5%90%88%E5%B9%B6%E7%89%88V1.0.xlsx?d=w797c400b7d0e4abfbe40aeab5f98a193&csf=1&web=1&e=chIS4B
    private IpsReceiveInvoiceMasterFromAll createIpsReceiveInvoiceMasterFromAll(ImpInvoiceMaster impInvoiceMaster, int qty, int num,
                                                                                String batchNo, String operateType, String operator) throws OpsException {
        IpsReceiveInvoiceMasterFromAll info = new IpsReceiveInvoiceMasterFromAll();
        //data_type	0：OPS只回传签收数据
        //inovice_batch_no	发票批次号
        //invoce_batch_barcode_num	发票批次数量，对应明细表中的数量
        //purchase_unit_code	CN0
        //source_system	OPS
        //supplier_no	imp_invoice_master.supplier_code
        //invoice_no	imp_invoice_master.invoice_no
        //invdate	1.imp_invoice_master.invoice_date
        //	2.第1点为空时，取create_time
        //receiving_address_code	imp_invoice_master.arrived_warehouse_code
        //state	1
        //qty_sum	imp_invoice_master.total_qty
        //box_qty	imp_invoice_master.box_qty
        //order_num	imp_invoice_master.order_qty
        //weight_sum	imp_invoice_master.weight
        //invoice_amount	imp_invoice_master.amount
        info.setDataType(operateType);
        info.setInvoiceBatchNo(batchNo);
        info.setInvoiceBatchBarcodeNum(num);
        info.setPurchaseUnitCode("CN0");
        info.setSourceSystem(SOURCE_SYSTEM);
        //SupplierNo需要用配置表转换 参考代码：com.sales.ops.serviceimpl.impinvoice.ImpInvoiceSysnCommonServiceImpl#getOpsSupplierByConfig
        ResultVo<String> resultVo = purchaseImpInfoToPSIService.getAdapterSupplierByConfig(impInvoiceMaster.getSupplierCode(), "supplierid");
        if (!resultVo.isSuccess()) {
            throw Exceptions.OpsException("OPS供应商转换为PSI供应商代码失败:" + impInvoiceMaster.getSupplierCode() + ", " + resultVo.getMessage());
        }
        info.setSupplierNo(resultVo.getData());
        info.setInvoiceNo(impInvoiceMaster.getInvoiceNo());
        info.setInvdate(impInvoiceMaster.getInvoiceDate() != null ? impInvoiceMaster.getInvoiceDate() : impInvoiceMaster.getCreateTime());
        //转换为PSI地址 字典6011
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo(
                IpsPurchaseCommonEnum.IPS_WAREHOUSE_CONVERT_DICT.getCode(),impInvoiceMaster.getArrivedWarehouseCode());
        if (dataTypeCodesInfo.isSuccess() && dataTypeCodesInfo.getData() != null && StringUtils.isNotBlank(dataTypeCodesInfo.getData().getExtNote1())) {
            info.setReceivingAddressCode(dataTypeCodesInfo.getData().getExtNote1());
        } else {
            throw Exceptions.OpsException("OPS仓库转换为PSI仓库代码失败," + IpsPurchaseCommonEnum.IPS_WAREHOUSE_CONVERT_DICT.getCode() + ":" + impInvoiceMaster.getArrivedWarehouseCode() + ", " + dataTypeCodesInfo.getMessage());
        }
        info.setState("1");// 当前无实际用途，默认传1
        info.setQtySum((double) qty);
        info.setBoxQty(impInvoiceMaster.getBoxQty() != null ? impInvoiceMaster.getBoxQty().doubleValue() : 0d);
        info.setOrderNum(impInvoiceMaster.getOrderQty() != null ? impInvoiceMaster.getOrderQty().doubleValue() : 0d);
        info.setWeightSum(impInvoiceMaster.getWeight() != null ? impInvoiceMaster.getWeight().doubleValue() : 0d);
        info.setInvoiceAmount(impInvoiceMaster.getAmount() != null ? impInvoiceMaster.getAmount().doubleValue() : 0d);
        HashMap<String, String> additionInfo = new HashMap<>();
        //{
        //    "plantMark":"","AM"
        //    "gwInvoiceNo":imp_invoice_master.cinvoice_no
        //    "processStatus": "1"，
        //    "processDate":"2026-05-13 11:48:00:000"
        //    "processorCode":""
        //}
        additionInfo.put("plantMark", "AM");
        additionInfo.put("gwInvoiceNo", impInvoiceMaster.getCinvoiceNo());
        additionInfo.put("processStatus",operateType);
        //先看操作类型，再看是否为空
        Date processDate = null;
        if (OrderDeliveryPushEventServiceImpl.PoInvoiceOperation.SIGN.getCode().equals(operateType)) {
            processDate = impInvoiceMaster.getArriveDate();
        } else if (OrderDeliveryPushEventServiceImpl.PoInvoiceOperation.COMFIRM.getCode().equals(operateType)) {
            processDate = impInvoiceMaster.getConfirmDate();
        }
        String processDateStr = processDate != null ? DateUtil.format(processDate, "yyyy-MM-dd HH:mm:ss:SSS") : null;
        additionInfo.put("processDate", processDateStr);
        additionInfo.put("processorCode", operator);
        info.setAddtionInfo(JSONUtil.toJsonStr(additionInfo));
        info.setCreateTime(new Date());
        info.setCreateUser("OPS");
        return info;
    }

    private Map<String, List<ImpInvoiceDetail>> getImpInvoiceDetails(String invoiceNo, Long invoiceId) throws OpsException {
        ImpInvoiceDetailExample ImpDetailEx = new ImpInvoiceDetailExample();
        ImpInvoiceDetailExample.Criteria criteria = ImpDetailEx.createCriteria();
        criteria.andInvoiceNoEqualTo(invoiceNo)
                .andInvoiceIdEqualTo(invoiceId)
        ;
        List<ImpInvoiceDetail> impInvoiceList = impinvoicedetailMapper.selectByExample(ImpDetailEx);
        if (CollectionUtils.isEmpty(impInvoiceList)) {
            throw Exceptions.OpsException("查询发票子表失败impInvoiceDetail：" + invoiceNo);
        }
        Map<String, List<ImpInvoiceDetail>> map = new HashMap<>();
        for (ImpInvoiceDetail impInvoiceDetail : impInvoiceList) {
            if (!map.containsKey(impInvoiceDetail.getFullOrderNo())) {
                List<ImpInvoiceDetail> list = new ArrayList<>();
                map.put(impInvoiceDetail.getFullOrderNo(), list);
            }
            map.get(impInvoiceDetail.getFullOrderNo()).add(impInvoiceDetail);
        }

        return map;
    }

}
