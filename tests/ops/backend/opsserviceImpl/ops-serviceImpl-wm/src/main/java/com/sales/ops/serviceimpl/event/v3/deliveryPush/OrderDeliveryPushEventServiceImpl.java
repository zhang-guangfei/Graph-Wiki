package com.sales.ops.serviceimpl.event.v3.deliveryPush;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.util.DateUtils;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.ImpInvoiceDetailMapper;
import com.sales.ops.db.dao.ImpInvoiceMasterMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.ips.IpsReceiveDeliveryInfoFromAllVO;
import com.sales.ops.dto.ips.OpsVManuorderToSales;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.OrderStockTypeEnum;
import com.sales.ops.enums.OrderTypeEnum;
import com.sales.ops.enums.RcvOrderStatusEnum;
import com.sales.ops.feign.delivery.OpsDeliveryDataToIpsFeignApi;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.order.BaseOrderAssignResultService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.ExpdetailService;
import com.sales.ops.serviceimpl.event.v3.status.enums.OrderStatusEnum;
import com.sales.ops.serviceimpl.event.v3.status.enums.OrderStatusItemEnum;
import com.sales.ops.serviceimpl.event.v3.status.service.OrderStatusService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class OrderDeliveryPushEventServiceImpl implements OrderDeliveryPushEventService {

    private OpsDeliveryDataToIpsFeignApi opsDeliveryDataToIpsFeignApi;
    private BaseCustomerOrderService baseCustomerOrderService;
    private BaseOrderAssignResultService baseOrderAssignResultService;

    private BaseDoService baseDoService;
    private BasePoService basePoService;
    private ExpdetailService expdetailService;
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    private OrderStatusService orderStatusService;
    private ImpInvoiceDetailMapper impinvoicedetailMapper;
    private ImpInvoiceMasterMapper impInvoiceMasterMapper;

    private static final String FACT_SUPPLIER = "CN0";
    private static final Short ORDER_TYPE = Short.parseShort(OrderTypeEnum.JITUAN);
    private static final String DICT_CLASS_NO_ADDRESS_CODE = "4602";
    private static final String SOURCE_SYSTEM = "OPS";

    public enum PoInvoiceOperation {
        SIGN("0", "发票签收数据"),
        COMFIRM("1", "发票入库数据"),
        ;

        private String code;
        private String desc;

        PoInvoiceOperation(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }

    enum IdentificationCode {

        ALLOT_FAILURE("P", "接入失败"), //订单接入失败
        BEFORE_ALLOT("F", "接入订单"), //订单分配前
        NOT_ALLOT("DX", "暂不处理"), //订单分配前
        AFTER_ALLOT("I", "订单已分配"), //订单分配后
        ES_DELIVERY_DATE("N", ""), //写入预计送达日
        FACT_DATE("C", ""), //WMS有称重打印时间
        CARRIERS_INFO("H", ""), //承运商交接信息
        IMPDATA("A", ""),//impdataForManu产生当日发货数据时
        INVOICED("AB", ""),//客单开票后
        CANCEL("D", ""),//客单删除后
        ;

        private String code;
        private String desc;

        IdentificationCode(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }


    enum TransTypeEnum {

        T0("0", "海运"),
        T1("1", "空运"),
        T2("2", "陆运"),
        T4("4", "快船"),


        ;

        private String code;
        private String desc;

        TransTypeEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDescByCode(String code) {
            for (TransTypeEnum transTypeEnum : TransTypeEnum.values()) {
                if (transTypeEnum.getCode().equals(code)) {
                    return transTypeEnum.getDesc();
                }
            }
            return null;
        }
    }

    enum StockTypeEnum {
        N("N", "0"),
        T("T", "1"),
        CG("CG", "2"),
        ;

        StockTypeEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDescByCode(String code) {
            for (StockTypeEnum stockTypeEnum : StockTypeEnum.values()) {
                if (stockTypeEnum.getCode().equals(code)) {
                    return stockTypeEnum.getDesc();
                }
            }
            return code;
        }
    }

    enum UnusualTypeEnum {
        BEFORE_RCV("0", "接单前异常"),
        AFTER_RCV("1", "接单后异常"),
        ;
        private String code;
        private String desc;

        UnusualTypeEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }

    class AddtionInfo {
        private UnusualOrderInfo unusualOrderInfo;
        private FactManufacturerInfo factManufacturerInfo;

        public AddtionInfo(UnusualOrderInfo unusualOrderInfo) {
            this.unusualOrderInfo = unusualOrderInfo;
        }

        public AddtionInfo() {
        }

        public UnusualOrderInfo getUnusualOrderInfo() {
            return unusualOrderInfo;
        }

        public void setUnusualOrderInfo(UnusualOrderInfo unusualOrderInfo) {
            this.unusualOrderInfo = unusualOrderInfo;
        }

        public FactManufacturerInfo getFactManufacturerInfo() {
            return factManufacturerInfo;
        }

        public void setFactManufacturerInfo(FactManufacturerInfo factManufacturerInfo) {
            this.factManufacturerInfo = factManufacturerInfo;
        }
    }


    class UnusualOrderInfo {

        String srcOrderUnusualReasonCode;
        String srcOrderUnusualReason;
        String unusual_type; //0:接单前异常 1:接单后异常


        public String getSrcOrderUnusualReasonCode() {
            return srcOrderUnusualReasonCode;
        }

        public void setSrcOrderUnusualReasonCode(String srcOrderUnusualReasonCode) {
            this.srcOrderUnusualReasonCode = srcOrderUnusualReasonCode;
        }

        public String getSrcOrderUnusualReason() {
            return srcOrderUnusualReason;
        }

        public void setSrcOrderUnusualReason(String srcOrderUnusualReason) {
            this.srcOrderUnusualReason = srcOrderUnusualReason;
        }

        public String getUnusual_type() {
            return unusual_type;
        }

        public void setUnusual_type(String unusual_type) {
            this.unusual_type = unusual_type;
        }
    }


    class FactManufacturerInfo {
        //预到货日
        private String promiseDateFromFactManuA;
        //预到货发票
        private String factManuInvoiceNo;

        private String factManuTransType;

        public String getPromiseDateFromFactManuA() {
            return promiseDateFromFactManuA;
        }

        public void setPromiseDateFromFactManuA(String promiseDateFromFactManuA) {
            this.promiseDateFromFactManuA = promiseDateFromFactManuA;
        }

        public String getFactManuInvoiceNo() {
            return factManuInvoiceNo;
        }

        public void setFactManuInvoiceNo(String factManuInvoiceNo) {
            this.factManuInvoiceNo = factManuInvoiceNo;
        }

        public String getFactManuTransType() {
            return factManuTransType;
        }

        public void setFactManuTransType(String factManuTransType) {
            this.factManuTransType = factManuTransType;
        }
    }

    public enum SupplierCodeEnum {

        JP("JP", "JP0"),//日本
        CN("CN", "CNC"),//中国工厂
        CM("CM", "CNM"),//北京工厂
        CT("CT", "CNT"),//天津工厂
        GZ("GZ", "CNG"),//广州工厂
        TZ("TZ", "CNSH"),//上海工厂
        YZ("YZ", "CNYZ"),//制六
        //("", "CNCZ"),//常州工厂
        //("", "CN0"),//自动化

        ;

        SupplierCodeEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDescByCode(String code) {
            for (SupplierCodeEnum supplierCodeEnum : SupplierCodeEnum.values()) {
                if (supplierCodeEnum.getCode().equals(code)) {
                    return supplierCodeEnum.getDesc();
                }
            }
            return code;
        }
    }

    enum PushFlagEnum {

        PUSH("1"),
        NOT_PUSH("0"),
        ;


        private String pushFlag;

        PushFlagEnum(String number) {
            this.pushFlag = number;
        }

        public String getCode() {
            return pushFlag;
        }

    }


    @Override
    public void handleAllotFailure(String orderNo, Integer orderItem, IpsReceiveDeliveryInfoFromAllVO data) throws OpsException {
        //data.setPurchaseUnitCode(); 报文中已经有了，从OrderSalesService.pushEvent写入
        String customerNo = data.getFactSupplier();
        //订单接单失败，先看客户代码是否为空
        // 如果客户代码不为空，则看字典中是否推送
        if (StringUtils.isNotBlank(customerNo) && !includeCustomerFromDict(customerNo)) {
            return;
        }
        // 19529 如果订单接单失败，且客户代码为空，则推送
        data.setFactSupplier(FACT_SUPPLIER);
        data.setSourceSystem(SOURCE_SYSTEM);
        data.setIdentificationCode(IdentificationCode.ALLOT_FAILURE.getCode());
        data.setOrderStatusDescription(IdentificationCode.ALLOT_FAILURE.getDesc() + ":" + data.getRemark());
        data.setOrdRemainingQty(data.getQuantity());
        data.setShippedQty(0d);
        data.setCreateTime(new Date());
        data.setCreateUser(SOURCE_SYSTEM);
        data.setDelflag(0);
        if (data.getSupplierOperateTime() == null) {
            data.setSupplierOperateTime(new Date());
        }
        //data.setremark//报文中已有
        pushDeliveryInfoToPSI(data);
    }


    @Override
    public void handleBeforeAllot(String orderNo, Integer orderItem, IpsReceiveDeliveryInfoFromAllVO vo) throws OpsException {
        RcvView rcvView = baseCustomerOrderService.findRcvViewByNo(orderNo, orderItem);
        if (rcvView == null || !ORDER_TYPE.equals(rcvView.getOrderType())) {
            return;
        }
        //18673 2025-08-18 如果字典中不推送，则直接忽略
        if (!includeCustomerFromDict(rcvView.getCustomerNo())) {
            return;
        }
        IpsReceiveDeliveryInfoFromAllVO data = initData(rcvView, IdentificationCode.BEFORE_ALLOT);
        data.setSupplierOperateTime(rcvView.getRorddate());
        pushDeliveryInfoToPSI(data);
    }

    @Override
    public void handleNotAllot(String orderNo, Integer orderItem, CommonResult<String> errorMsg) throws OpsException {
        //19090 增加报文，异常说明和编码
        UnusualOrderInfo unusualOrderInfo = new UnusualOrderInfo();
        unusualOrderInfo.setUnusual_type(UnusualTypeEnum.AFTER_RCV.getCode());
        unusualOrderInfo.setSrcOrderUnusualReasonCode(errorMsg.getMessage());
        unusualOrderInfo.setSrcOrderUnusualReason(errorMsg.getData());
        RcvView rcvView = baseCustomerOrderService.findRcvViewByNo(orderNo, orderItem);
        if (rcvView == null || !ORDER_TYPE.equals(rcvView.getOrderType())) {
            return;
        }
        //18673 2025-08-18 如果字典中不推送，则直接忽略
        if (!includeCustomerFromDict(rcvView.getCustomerNo())) {
            return;
        }
        IpsReceiveDeliveryInfoFromAllVO data = initData(rcvView, IdentificationCode.NOT_ALLOT);
        data.setSupplierOperateTime(rcvView.getRorddate());
        data.setAddtionInfo(JSONUtil.toJsonStr(new AddtionInfo(unusualOrderInfo)));
        //异常原因描述
        data.setOrderStatusDescription(errorMsg.getData());
        pushDeliveryInfoToPSI(data);
    }

    @Override
    public void handleAfterAllot(String orderNo, Integer orderItem) throws OpsException {
        RcvView rcvView = baseCustomerOrderService.findRcvViewByNo(orderNo, orderItem);
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(orderNo, orderItem);
        if (rcvView == null || !ORDER_TYPE.equals(rcvView.getOrderType())) {
            return;
        }
        //18673 2025-08-18 如果字典中不推送，则直接忽略
        if (!includeCustomerFromDict(rcvView.getCustomerNo())) {
            return;
        }
        IpsReceiveDeliveryInfoFromAllVO data = initData(rcvView, IdentificationCode.AFTER_ALLOT);
        getStockInfo(data, rcvView);
        data.setSupplierOperateTime(rcvdetail.getAllotTime());
        log.info("handleAfterAllot:{}", JSONUtil.toJsonStr(data));
        pushDeliveryInfoToPSI(data);
    }

    //写入预计送达日
    @Override
    public void handleEsDeliveryDate(String orderNo, Integer orderItem) throws OpsException {
        RcvView rcvView = baseCustomerOrderService.findRcvViewByNo(orderNo, orderItem);
        if (rcvView == null || !ORDER_TYPE.equals(rcvView.getOrderType())) {
            return;
        }
        //18673 2025-08-18 如果字典中不推送，则直接忽略
        if (!includeCustomerFromDict(rcvView.getCustomerNo())) {
            return;
        }
        if (rcvView.getEstimatedDeliveryDay() == null) {
            return;
        }
        IpsReceiveDeliveryInfoFromAllVO data = initData(rcvView, IdentificationCode.ES_DELIVERY_DATE);
        getStockInfo(data, rcvView);
        getInfoFromPo(data, rcvView);
        //订单状态描述
        RcvOrderStatusEnum rcvStatusEnum = RcvOrderStatusEnum.getEnumByType(rcvView.getStatus());
        if (rcvStatusEnum != null) {
            data.setOrderStatusDescription("当前订单状态：" + rcvStatusEnum.getDesc());
        }
        data.setTransType(TransTypeEnum.T2.getCode());
        //获取订单采购单描述信息
        getInfoFromOrderState(data, rcvView);
        pushDeliveryInfoToPSI(data);
    }

    //WMS有称重打印时间时
    @Override
    public void handleFactDate(String orderNo, Integer orderItem) throws OpsException {
        RcvView rcvView = baseCustomerOrderService.findRcvViewByNo(orderNo, orderItem);
        if (rcvView == null || !ORDER_TYPE.equals(rcvView.getOrderType())) {
            return;
        }
        //18673 2025-08-18 如果字典中不推送，则直接忽略
        if (!includeCustomerFromDict(rcvView.getCustomerNo())) {
            return;
        }
        IpsReceiveDeliveryInfoFromAllVO data = initData(rcvView, IdentificationCode.FACT_DATE);
        getStockInfo(data, rcvView);
        getInfoFromPo(data, rcvView);
        List<OpsDo> jycks = baseDoService.findAllJYCKByOrder(rcvView.getRorderNo(), rcvView.getRorderItem().toString());
        Date date = jycks.stream().map(OpsDo::getWmsTime).filter(Objects::nonNull).min(Comparator.naturalOrder()).orElse(null);
        data.setSupplierOperateTime(date);
        data.setShippingMethod(rcvView.getDlvEntire());
        //订单状态描述
        RcvOrderStatusEnum rcvStatusEnum = RcvOrderStatusEnum.getEnumByType(rcvView.getStatus());
        if (rcvStatusEnum != null) {
            data.setOrderStatusDescription("当前订单状态：" + rcvStatusEnum.getDesc());
        }
        pushDeliveryInfoToPSI(data);
    }

    @Override
    public void handleInvoiced(String orderNo, Integer orderItem) throws OpsException {
        RcvView rcvView = baseCustomerOrderService.findRcvViewByNo(orderNo, orderItem);
        if (rcvView == null || !ORDER_TYPE.equals(rcvView.getOrderType())) {
            return;
        }
        //18673 2025-08-18 如果字典中不推送，则直接忽略
        if (!includeCustomerFromDict(rcvView.getCustomerNo())) {
            return;
        }
        if (rcvView.getEstimatedDeliveryDay() == null) {
            return;
        }
        IpsReceiveDeliveryInfoFromAllVO data = initData(rcvView, IdentificationCode.INVOICED);
        getStockInfo(data, rcvView);
        getInfoFromPo(data, rcvView);
        //订单状态描述
        RcvOrderStatusEnum rcvStatusEnum = RcvOrderStatusEnum.getEnumByType(rcvView.getStatus());
        if (rcvStatusEnum != null) {
            data.setOrderStatusDescription("当前订单状态：" + rcvStatusEnum.getDesc());
        }
        //获取订单采购单描述信息
        getInfoFromOrderState(data, rcvView);
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(orderNo, orderItem);
        data.setSupplierOperateTime(rcvdetail.getInvoiceTime());
        pushDeliveryInfoToPSI(data);
    }

    @Override
    public void handleOrderCancel(String orderNo, Integer orderItem, CancelForOrderDto cancelDto) throws OpsException {
        RcvView rcvView = baseCustomerOrderService.findRcvViewByNo(orderNo, orderItem);
        if (rcvView == null || !ORDER_TYPE.equals(rcvView.getOrderType())) {
            return;
        }
        //18673 2025-08-18 如果字典中不推送，则直接忽略
        if (!includeCustomerFromDict(rcvView.getCustomerNo())) {
            return;
        }
        IpsReceiveDeliveryInfoFromAllVO data = initData(rcvView, IdentificationCode.CANCEL);
        if (cancelDto != null) {
            StringBuilder desc = new StringBuilder();
            if (ObjectUtil.isNotNull(cancelDto.getUserDto())) {
                desc.append(cancelDto.getUserDto().getUserName());
            }
            if (org.apache.commons.lang3.StringUtils.isNotBlank(cancelDto.getOrderType()) && com.smc.smccloud.core.model.enums.OrderTypeEnum.getCodeName(cancelDto.getOrderType()) != null) {
                desc.append("做" + com.smc.smccloud.core.model.enums.OrderTypeEnum.getCodeName(cancelDto.getOrderType()));
            }
            desc.append("取消订单");
            if (org.apache.commons.lang3.StringUtils.isNotBlank(cancelDto.getDuty())) {
                desc.append(" 责任人：" + cancelDto.getDuty());
            }
            if (org.apache.commons.lang3.StringUtils.isNotBlank(cancelDto.getReason())) {
                desc.append(" 原因：" + cancelDto.getReason());
            }
            data.setOrderStatusDescription(desc.toString());
        }
        pushDeliveryInfoToPSI(data);
    }

    //发票入库操作，推送发票入库时间
    @Deprecated//19413 方案1 已废弃，使用xxljob
    @Override
    public void handlePoInvoiceConfirm(String orderNo, Integer orderItem, OpsPurchaseinvoice po, String operator) throws OpsException {
        if (po == null || StringUtils.isBlank(po.getInvoiceno()) || po.getInvoiceid() == null) {
            throw Exceptions.OpsException("查询采购发票号失败");
        }
        String invoiceNo = po.getInvoiceno();
        Long invoiceid = po.getInvoiceid();
        String orderno = po.getOrderno();
        Integer itemno = po.getItemno();
        Integer splititemno = po.getSplititemno();
        IpsReceiveSignImpInfoFromAll info = getIpsReceiveSignImpInfoFromAll(invoiceNo, invoiceid, orderno, itemno, splititemno, PoInvoiceOperation.COMFIRM.getCode(), operator);
        pushSignImpInfoToPSI(info);
    }

    //唯一不同的就是类型
    @Deprecated
    @Override
    public void handlePoInvoiceSign(String orderNo, Integer orderItem, OpsPurchaseinvoice po, String operator) throws OpsException {
        if (po == null || StringUtils.isBlank(po.getInvoiceno()) || po.getInvoiceid() == null) {
            throw Exceptions.OpsException("查询采购发票号失败");
        }
        String invoiceNo = po.getInvoiceno();
        Long invoiceid = po.getInvoiceid();
        String orderno = po.getOrderno();
        Integer itemno = po.getItemno();
        Integer splititemno = po.getSplititemno();
        IpsReceiveSignImpInfoFromAll info = getIpsReceiveSignImpInfoFromAll(invoiceNo, invoiceid, orderno, itemno, splititemno, PoInvoiceOperation.SIGN.getCode(), operator);
        pushSignImpInfoToPSI(info);
    }

    // 采购单号+发票号，推送一次数据给psi
    private IpsReceiveSignImpInfoFromAll getIpsReceiveSignImpInfoFromAll(String invoiceNo, Long invoiceId,
                                                                         String orderno, Integer itemno, Integer splititemno,
                                                                         String type, String operator) throws OpsException {
        //1.查发票主表，一条
        ImpInvoiceMaster impInvoiceMaster = getImpInvoiceMaster(invoiceNo);
        // 2.查发票子表，一条或多条
        List<ImpInvoiceDetail> impInvoiceList = getImpInvoiceDetails(invoiceNo, invoiceId, orderno, itemno, splititemno);
        int qty = 0;
        ImpInvoiceDetail impInvoiceDetail = impInvoiceList.get(0);
        for (ImpInvoiceDetail detail : impInvoiceList) {
            Integer quantity = detail.getQuantity();
            if (quantity != null) {
                qty += quantity;
            }
        }
        //3.查询此发票的所有detail，并按单号去重，得到采购单条数
        Integer batchNum = getBatchNum(invoiceNo, invoiceId);
        //4.创建推送数据
        return createIpsReceiveSignImpInfoFromAll(impInvoiceMaster, impInvoiceDetail, qty, batchNum, type, operator);
    }

    private List<ImpInvoiceDetail> getImpInvoiceDetails(String invoiceNo, Long invoiceId, String orderno, Integer itemno, Integer splititemno) throws OpsException {
        ImpInvoiceDetailExample ImpDetailEx = new ImpInvoiceDetailExample();

        ImpInvoiceDetailExample.Criteria criteria = ImpDetailEx.createCriteria();
        criteria.andInvoiceNoEqualTo(invoiceNo)
                .andInvoiceIdEqualTo(invoiceId)
                .andOrderNoEqualTo(orderno)
                .andItemNoEqualTo(itemno)
        ;
        if (splititemno != null) {
            criteria.andSplitItemNoEqualTo(splititemno);
        } else {
            criteria.andSplitItemNoIsNull();
        }
        List<ImpInvoiceDetail> impInvoiceList = impinvoicedetailMapper.selectByExample(ImpDetailEx);

        if (CollectionUtils.isEmpty(impInvoiceList)) {
            throw Exceptions.OpsException("查询发票子表失败impInvoiceDetail：" + invoiceNo);
        }
        return impInvoiceList;
    }
    //查询此发票的所有detail，并按采购单号去重，得到采购单条数
    private Integer getBatchNum(String invoiceNo, Long invoiceId) throws OpsException {
        ImpInvoiceDetailExample ImpDetailEx = new ImpInvoiceDetailExample();
        ImpInvoiceDetailExample.Criteria criteria = ImpDetailEx.createCriteria();
        criteria.andInvoiceNoEqualTo(invoiceNo)
                .andInvoiceIdEqualTo(invoiceId)
        ;
        List<ImpInvoiceDetail> impInvoiceList = impinvoicedetailMapper.selectByExample(ImpDetailEx);
        if (CollectionUtils.isEmpty(impInvoiceList)) {
            throw Exceptions.OpsException("查询发票子表失败impInvoiceDetail：" + invoiceNo);
        }
        Set<String> orderNos = new HashSet<>();
        for (ImpInvoiceDetail impInvoiceDetail : impInvoiceList) {
            orderNos.add(impInvoiceDetail.getFullOrderNo());
        }
        return orderNos.size();
    }

    private ImpInvoiceMaster getImpInvoiceMaster(String invoiceNo) throws OpsException {
        ImpInvoiceMasterExample impMasterEx = new ImpInvoiceMasterExample();
        impMasterEx.createCriteria().andInvoiceNoEqualTo(invoiceNo)
        ;
        List<ImpInvoiceMaster> impInvoiceMasters = impInvoiceMasterMapper.selectByExample(impMasterEx);
        if (CollectionUtils.isEmpty(impInvoiceMasters)) {
            throw Exceptions.OpsException("查询发票主表失败impInvoiceMasters：" + invoiceNo);
        }
        ImpInvoiceMaster impInvoiceMaster = impInvoiceMasters.get(0);
        return impInvoiceMaster;
    }

    private IpsReceiveSignImpInfoFromAll createIpsReceiveSignImpInfoFromAll(ImpInvoiceMaster impInvoiceMaster, ImpInvoiceDetail imp, int qty, int num, String operateType, String operator) {
        IpsReceiveSignImpInfoFromAll info = new IpsReceiveSignImpInfoFromAll();
        info.setSourceSystem(SOURCE_SYSTEM);
        String supplierSystem = SupplierCodeEnum.getDescByCode(impInvoiceMaster.getSupplierCode());
        info.setSupplierSystem(supplierSystem);
        info.setSrcOrderNo(imp.getPono());
        info.setSrcOrderItem(imp.getPoitemno().toString());
        info.setOrderNo(imp.getPono());
        info.setOrderItem(imp.getPoitemno().toString());
        info.setModelNo(imp.getModelNo());
        info.setDataType(operateType);
        info.setQty(new BigDecimal(qty));
        info.setDeviationQty(null);
        info.setDeviationReason(null);
        info.setProcessStatus("1"); // 0未签收\未入库，1已签收\已入库，
        info.setProcessPlace(impInvoiceMaster.getReceiveWarehouseCode());
        if (PoInvoiceOperation.SIGN.getCode().equals(operateType)) {
            info.setProcessDate(impInvoiceMaster.getArriveDate());
        } else if (PoInvoiceOperation.COMFIRM.getCode().equals(operateType)) {
            info.setProcessDate(impInvoiceMaster.getConfirmDate());
        }
        info.setProcessorCode(operator);
        info.setCreateTime(new Date());
        info.setCreateUser("OPS");
        info.setInvoiceNo(imp.getInvoiceNo());
        //OPS20260126140000100NX1795-51
        //系统缩称+ YYYYMMDDHHmmssSSS+invoiceNo (20+n位)
        String batchNo = "OPS" + DateUtils.format(new Date(), "yyyyMMddHHmmssSSS") + imp.getInvoiceNo();
        info.setInvoiceBatchNo(batchNo);
        info.setAdditionInfo(null);
        return info;
    }

    // 18673:推送前增加判断，purchaseUnitCode不能为空,如果为空则抛出异常，事件处理失败
    private void pushDeliveryInfoToPSI(IpsReceiveDeliveryInfoFromAllVO data) throws OpsException {
        //获取字典开关
        ResultVo<DataTypeVO> dict = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "30");
        boolean success = dict.isSuccess();
        if (!success || dict.getData() == null) {
            throw Exceptions.OpsException("获取数据字典失败:" + dict.getMessage());
        }
        String extNote1 = dict.getData().getExtNote1();
        //推给制造视图
        if (extNote1.equals("2") || extNote1.equals("3")) {
            String identificationCode = data.getIdentificationCode();
            OpsVManuorderToSales opsVManuorderToSales = new OpsVManuorderToSales();
            if (StringUtils.isBlank(data.getSupplierSalesOrderNo()) || StringUtils.isBlank(data.getSupplierSalesOrderItem())) {
                throw Exceptions.OpsException("订单号或订单行号不能为空");
            }
            String orderNo = data.getSupplierSalesOrderNo() + "-" + data.getSupplierSalesOrderItem();
            opsVManuorderToSales.setSalesOrderNo(orderNo);
            //如果是删单，设置取消状态为1
            if (IdentificationCode.CANCEL.getCode().equals(identificationCode)) {
                opsVManuorderToSales.setSalesCancelStatus(1);
                opsVManuorderToSales.setSalesStatus(3);
                opsVManuorderToSales.setSalesCancelTime(new Date());
                opsVManuorderToSales.setSalesUpdateTime(new Date());
                opsVManuorderToSales.setSalesCancelReason(data.getOrderStatusDescription());
                opsDeliveryDataToIpsFeignApi.updateVManuorderToSales(opsVManuorderToSales);
            } else {
                //如果不是删单，则更新以下字段
                opsVManuorderToSales.setSalesCancelStatus(0);
                opsVManuorderToSales.setSalesStatus(2);
                opsVManuorderToSales.setSalesCancelTime(null);
                opsVManuorderToSales.setSalesUpdateTime(new Date());
                opsVManuorderToSales.setSalesCancelReason(null);

                opsVManuorderToSales.setSalesRemark(data.getOrderStatusDescription());//描述信息
                opsVManuorderToSales.setSalesordernoJp(data.getFactManufacturerOrder());// 订单号
                String addtionInfo = data.getAddtionInfo();
                if (StringUtils.isNotBlank(addtionInfo)) {
                    AddtionInfo addtion = BeanUtil.toBean(addtionInfo, AddtionInfo.class);
                    if (addtion != null && addtion.getFactManufacturerInfo() != null) {
                        FactManufacturerInfo factManufacturerInfo = addtion.getFactManufacturerInfo();
                        if (factManufacturerInfo != null && StringUtils.isNotBlank(factManufacturerInfo.getPromiseDateFromFactManuA())) {
                            String date = factManufacturerInfo.getPromiseDateFromFactManuA();
                            DateTime dateTime = DateUtil.parseDate(date);
                            opsVManuorderToSales.setExpectedArrivalDate(dateTime); //日本预到货日
                            opsVManuorderToSales.setExpectedArrivalinvNo(factManufacturerInfo.getFactManuInvoiceNo());// 日本发票号
                        }
                    }
                }
                DateTime salesDeliveryTime = DateUtil.parseDate(data.getPromiseDateFromSupplierArrived());
                opsVManuorderToSales.setSalesDeliveryTime(salesDeliveryTime); //返信日期
                opsVManuorderToSales.setExpInvCode(data.getSupplierExpinvCode()); //出库区分编码
                opsVManuorderToSales.setTransType(data.getTransType()); // 运输方式
                opsDeliveryDataToIpsFeignApi.updateVManuorderToSales(opsVManuorderToSales);
            }
        }
        //推给PSI
        if (extNote1.equals("1") || extNote1.equals("3")) {
            //  开关等于1或3
            if (StringUtils.isBlank(data.getPurchaseUnitCode())) {
                throw Exceptions.OpsException("purchase_unit_code不能为空");
            }
            CommonResult<IpsReceiveDeliveryInfoFromAllVO> result = opsDeliveryDataToIpsFeignApi.pushOpsDeliveryData(data);
            if (!result.isSuccess()) {
                throw Exceptions.OpsException("推送ips交付信息失败:" + result.getMessage());
            }
        }
    }

    private void pushSignImpInfoToPSI(IpsReceiveSignImpInfoFromAll data) throws OpsException {
        CommonResult<IpsReceiveSignImpInfoFromAll> result = opsDeliveryDataToIpsFeignApi.pushOpsSignData(data);
        if (!result.isSuccess()) {
            throw Exceptions.OpsException("推送ips签收信息失败:" + result.getMessage());
        }
    }

    public Boolean includeCustomerFromDict(String customerNo) throws OpsException {
        //客户代码为空时增加校验，不忽略,抛出异常 19529
        if (StringUtils.isBlank(customerNo)) {
            throw Exceptions.OpsException("获取报文客户代码失败：factSupplier");
        }
        ResultVo<List<DataCodeVO>> resultVo = dictDataServiceFeignApi.getDataCodes(DICT_CLASS_NO_ADDRESS_CODE);
        List<DataCodeVO> dictData = resultVo.getData();
        if (dictData == null || dictData.isEmpty()) {
            throw Exceptions.OpsException("获取客户代码推送列表失败:dict" + DICT_CLASS_NO_ADDRESS_CODE);
        }
        for (DataCodeVO dataCodeVO : dictData) {
            if (StringUtils.equals(dataCodeVO.getCode(), customerNo)) {
                if (StringUtils.equals(PushFlagEnum.PUSH.getCode(), dataCodeVO.getExtNote2())) {
                    return true;
                }
            }
        }
        return false;
    }


    private IpsReceiveDeliveryInfoFromAllVO initData(RcvView rcvView, IdentificationCode identificationCode) throws OpsException {
        IpsReceiveDeliveryInfoFromAllVO data = new IpsReceiveDeliveryInfoFromAllVO();
        data.setSourceSystem(SOURCE_SYSTEM);
        data.setPurchaseUnitCode(rcvView.getPurchaseUnitCode());
        if (StringUtils.isNotBlank(rcvView.getExtOrderNo())) {
            data.setOrderNo(rcvView.getExtOrderNo());
            data.setOrderItem(rcvView.getExtOrderItem());
        } else {
            throw Exceptions.OpsException("获取订单号失败");
        }
        data.setIdentificationCode(identificationCode.getCode());
        data.setOrderStatusDescription(identificationCode.getDesc());
        data.setModel(rcvView.getModelNo());
        data.setQuantity(rcvView.getQuantity().doubleValue());
        data.setOrdRemainingQty(rcvView.getQuantity().doubleValue());
        data.setShippedQty(0d);
        data.setSupplierSalesOrderNo(rcvView.getRorderNo());
        data.setSupplierSalesOrderItem(rcvView.getRorderItem().toString());
        data.setPromiseShippingMethod(rcvView.getDlvEntire());
        data.setFactSupplier(FACT_SUPPLIER);
        if (rcvView.getExpectedDeliveryTime() != null) {
            data.setPromiseDateFromSupplierA(DateUtil.formatDateTime(rcvView.getExpectedDeliveryTime()));
        }
        if (rcvView.getEstimatedDeliveryDay() != null) {
            data.setPromiseDateFromSupplierArrived(DateUtil.formatDateTime(rcvView.getEstimatedDeliveryDay()));
        }
        data.setCreateTime(new Date());
        data.setCreateUser(SOURCE_SYSTEM);
        data.setDelflag(0);
        return data;
    }

    private void getStockInfo(IpsReceiveDeliveryInfoFromAllVO data, RcvView rcvView) {
        String stockType = StockTypeEnum.getDescByCode(rcvView.getStockType());
        data.setSupplierExpinvCode(stockType);
        data.setSupplierExpinvRemark(rcvView.getStockCode());
        if (rcvView.getStockType().equals(OrderStockTypeEnum.CG.getType()) && StringUtils.isNotBlank(rcvView.getStockType())) {
            String supplier = SupplierCodeEnum.getDescByCode(rcvView.getStockCode());
            data.setFactManufacturer(supplier);
        }
    }

    //从result表中获取po号
    private void getInfoFromPo(IpsReceiveDeliveryInfoFromAllVO data, RcvView rcvView) {
        if (StringUtils.isNotBlank(rcvView.getStockType()) && rcvView.getStockType().equals(OrderStockTypeEnum.CG.getType())) {
            List<OpsOrderAssignResult> assignResults = baseOrderAssignResultService.findByOrder(rcvView.getRorderNo(), rcvView.getRorderItem());
            OpsOrderAssignResult cgResult = null;
            for (OpsOrderAssignResult result : assignResults) {
                if (OrderStockTypeEnum.CG.getType().equals(result.getStockType())) {
                    if (StringUtils.isNotBlank(result.getAssociateNo())) {
                        cgResult = result;
                    }
                }
            }
            if (cgResult != null) {
                OpsPurchaseorder purchase = basePoService.getPurchase(cgResult.getAssociateNo(), cgResult.getAssociateNoItem(), cgResult.getAssociateNoSplitNo());
                if (purchase != null) {
                    //供应商
                    String supplier = SupplierCodeEnum.getDescByCode(purchase.getSupplierid());
                    data.setFactManufacturer(supplier);
                    //供应商接单号
                    if (StringUtils.isNotBlank(purchase.getReplyorderno())) {
                        if (!StringUtils.equals(purchase.getReplyorderno(), "system")) {
                            data.setFactManufacturerOrder(purchase.getReplyorderno());
                        }
                    }
                    //邸说：ops没有拆，先不填
                    data.setFactManufacturerItem(null);
                    List<OpsPurchaseinvoice> purchaseInvoices = basePoService.getPurchaseInvoicesByNo(cgResult.getAssociateNo(), cgResult.getAssociateNoItem(), cgResult.getAssociateNoSplitNo());
                    if (CollectionUtils.isNotEmpty(purchaseInvoices)) {
                        purchaseInvoices.sort(Comparator.comparing(OpsPurchaseinvoice::getId).reversed());
                        OpsPurchaseinvoice purchaseinvoice = purchaseInvoices.get(0);
                        //供应商返信理由
                        data.setReasonCodeFromSupplier(purchaseinvoice.getSrcDeliveryTime());
                        data.setReasonFromSupplier(purchaseinvoice.getSrcDeliveryTime());
                        //日本预到货日和日本预到货发票
                        AddtionInfo addtionInfo = new AddtionInfo();
                        FactManufacturerInfo factManufacturerInfo = new FactManufacturerInfo();
                        if (purchaseinvoice.getImdatetheory() != null) {
                            factManufacturerInfo.setPromiseDateFromFactManuA(DateUtil.formatDateTime(purchaseinvoice.getImdatetheory()));
                        }
                        if (StringUtils.isNotBlank(purchaseinvoice.getTranstypeFact())) {
                            factManufacturerInfo.setFactManuTransType(purchaseinvoice.getTranstypeFact());
                        } else if (StringUtils.isNotBlank(purchaseinvoice.getTranstype())) {
                            factManufacturerInfo.setFactManuTransType(purchaseinvoice.getTranstype());
                        }
                        factManufacturerInfo.setFactManuInvoiceNo(purchaseinvoice.getInvoiceno());
                        addtionInfo.setFactManufacturerInfo(factManufacturerInfo);
                        data.setAddtionInfo(JSONUtil.toJsonStr(addtionInfo));
                    }
                }
            }
        }
    }

    //"当前订单状态：{对外采购},采购单：{9900004080-329},当前状态：{采购已发票确认},供应商：SMC（日本）,发票号：TJS178,预计到物流日：2025-10-29 下午，实际到港日：2025-10-24"
    private void getInfoFromOrderState(IpsReceiveDeliveryInfoFromAllVO data, RcvView rcvView) {
        List<OrderStatusItem> statusViewList = orderStatusService.findOrderStatusItemList(rcvView.getRorderNo(), rcvView.getRorderItem());
        StringBuilder stringBuilder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(statusViewList)) {
            for (OrderStatusItem vo : statusViewList) {
                if (OrderStatusEnum.Purchase.getCode().equals(vo.getStatus()) || OrderStatusEnum.RequestIntercept.getCode().equals(vo.getStatus())) {
                    //采购单号
                    stringBuilder.append(",采购单：").append(vo.getAssociateNo())
                            .append(",");
                    //采购单当前状态
                    OrderStatusItemEnum statusItemEnum = OrderStatusItemEnum.getEnumByCode(vo.getStatusDesc());
                    if (statusItemEnum != null) {
                        stringBuilder.append("当前状态：")
                                .append(statusItemEnum.getDesc())
                                .append(",");
                    }
                    //详细信息
                    String statusInfo = vo.getStatusInfo();
                    if (statusInfo != null) {
                        JSON json = JSONUtil.parse(statusInfo);
                        Map<String, String> map = json.toBean(Map.class);
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            stringBuilder.append(entry.getKey())
                                    .append("：")
                                    .append(entry.getValue())
                                    .append(",");
                        }
                    }
                }
                data.setSupplierOperateTime(vo.getCreTime());
            }
        }
        String orderInfo = stringBuilder.toString();
        if (StringUtils.isNotBlank(orderInfo)) {
            orderInfo = data.getOrderStatusDescription() + orderInfo;
            data.setOrderStatusDescription(orderInfo);
        }
    }


}
