package com.sales.ops.serviceimpl.impinvoice;

import com.alibaba.fastjson.JSON;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.rabbitmq.RabbitMqMessage;
import com.sales.ops.db.dao.ImpInvoiceDetailMapper;
import com.sales.ops.db.dao.ImpInvoiceDetailPackMapper;
import com.sales.ops.db.dao.ImpInvoiceMasterMapper;
import com.sales.ops.db.dao.InvoiceSplitMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.ImpInvoiceErrorDao;
import com.sales.ops.db.extdao.ImpInvoiceSysnDao;
import com.sales.ops.dto.invoice.*;
import com.sales.ops.dto.purchase.PurchaseReplyInfo;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.enums.invoice.*;
import com.sales.ops.feign.WmPurchaseFeignApi;
import com.sales.ops.feign.delivery.ImpInvoiceGroupFeignApi;
import com.sales.ops.rabbitmq.SendMessage;
import com.sales.ops.service.impinvoice.ImpInvoiceSysnCommonService;
import com.sales.ops.service.impinvoice.ImpInvoiceSysnService;
import com.sales.ops.service.po.PurchaseConvertService;
import com.sales.ops.serviceimpl.config.redisson.PoRedissonLock;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.BigDecimalUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.invoice.ImpInvoiceProcessDTO;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.orderstate.OrderStateEnum;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.sales.ops.db.extdao.SplitSmcCodeDao;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author B91717
 * @date 2024/3/14 17:22
 */
@Slf4j
@Service
public class ImpInvoiceSysnServiceImpl implements ImpInvoiceSysnService {
    @Autowired
    private ImpInvoiceMasterMapper impInvoiceMasterMapper;

    @Resource
    private ImpInvoiceDetailMapper impInvoiceDetailMapper;

    @Resource
    private ImpInvoiceDetailPackMapper impInvoiceDetailPackMapper;

    @Resource
    private ImpInvoiceSysnCommonService impInvoiceCommonService;
    @Resource
    private ImpInvoiceErrorDao impInvoiceErrorDao;

    @Resource
    private WmPurchaseFeignApi wmPurchaseFeignApi;

    @Resource
    private SendMessage sendMessage;

    @Resource
    private PurchaseConvertService purchaseConvertDeliveryService;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Resource
    private ImpInvoiceGroupFeignApi impInvoiceGroupFeignApi;

    @Resource
    private ImpInvoiceSysnDao impInvoiceSysnDao;

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    @Resource
    private PoRedissonLock redissonUtil;

    @Autowired
    private SplitSmcCodeDao splitSmcCodeDao;

    @Autowired
    private InvoiceSplitMapper invoiceSplitMapper;

//    @Resource
//    private OrderStateService orderStateService;


    /**
     * impOrderNo\price\remark
     */

    /**
     * 定时读取ops_po_delivery_invoice_log_from_supplier表，根据code写入imp_invoice三个主子表中
     */
    @Override
    public ResultVo<String> impInvoiceSysn() {
        log.info("开始执行新impinvoice导入" );
        long start = System.currentTimeMillis();
        // 判断job是否允许执行
        String jobName = ImpInvoiceConstants.IMP_INVOICE_SYSN;
        ResultVo<DataTypeVO> idResult = dictDataServiceFeignApi.getDataTypeCodesInfo(ImpInvoiceCommonEnum.IMPSYSN_DICTID.getCode(), ImpInvoiceCommonEnum.IMPSYSN_DICTNAME.getCode());
        if (idResult.isSuccess() && StringUtils.isNotBlank(idResult.getData().getExtNote1())) {
            DataTypeVO dataCodes = idResult.getData();
            Long maxId = Long.valueOf(dataCodes.getExtNote1()); // 获取上次最大id值
            int pageSize = Integer.valueOf(dataCodes.getExtNote2()); // 获取每次取前N行
            Long lockLeaseTime = Long.valueOf("1800");  // 默认先设置为30分钟
            if (StringUtils.isNotBlank(dataCodes.getExtNote3())) {
                lockLeaseTime = Long.valueOf(dataCodes.getExtNote3()); // 锁的过期时间，切换为字典配置
            }
            // redis，加锁时长，切换为字典配置
            if (!redissonUtil.tryLock(ImpInvoiceConstants.REDIS_KEY_IMP_INVOICE, 1, lockLeaseTime)) {
                return ResultVo.failure("新impinvoice导入中,请稍后重试");
            }
            try {
                ResultVo<String> returnResultVo;
                StringBuilder successMsg = new StringBuilder(); //原因返回
                StringBuilder errorMsg = new StringBuilder(); //原因返回
                // 通过feign调用delivery接口，查询待处理数据
                ResultVo<List<OpsPoInvoiceDataDto>> selectReadResult = impInvoiceGroupFeignApi.getDeliveryInvoiceList(maxId, pageSize);
                if (!selectReadResult.isSuccess()) {
                    log.error(jobName + "查询待处理数据失败！");
                    return ResultVo.failure(selectReadResult.getMessage());
                }
                List<OpsPoInvoiceDataDto> opsPoInvoiceDataDtos = selectReadResult.getData();
                if (CollectionUtils.isEmpty(opsPoInvoiceDataDtos)) {
                    return ResultVo.successMsg("暂无待处理数据");
                }
                List<OpsPoInvoiceDataDto> todoList;
                List<String> sourceTypeList = opsPoInvoiceDataDtos.stream().map(OpsPoInvoiceDataDto::getSourceType).distinct().collect(Collectors.toList()); // 取出不同的sourceType
                // 配置发票导入后续操作的总体开关
                Boolean invoiceFollowSwitch = false;
                // bug14838 imp_invoice导入程序优化,新旧程序增加imp导入后操作开关
                ResultVo<DataTypeVO> newDataType = dictDataServiceFeignApi.getDataTypeCodesInfo("6004", "1");
                if (newDataType != null && "1".equals(newDataType.getData().getExtNote1())) {
                    invoiceFollowSwitch = true;
                }
                for (String sourceType : sourceTypeList) { // 循环处理不同供应商分类的数据
                    try {
                        // 根据不同供应商类别进行分组
                        todoList = opsPoInvoiceDataDtos.stream().filter(deliveryInvoiceLogFromSupplierDO -> deliveryInvoiceLogFromSupplierDO.getSourceType().equals(sourceType)).collect(Collectors.toList());
                        if (sourceType.equalsIgnoreCase(SourceTypeDescEnum.shipf.getDesc()) || sourceType.equalsIgnoreCase(SourceTypeDescEnum.PSI_JPInvoice.getDesc())) { // shipf文件导入操作
                            returnResultVo = insertJPL0Data(todoList, sourceType, invoiceFollowSwitch);
                        } else if (sourceType.equalsIgnoreCase(SourceTypeDescEnum.CMInvoice.getDesc()) || sourceType.equalsIgnoreCase(SourceTypeDescEnum.PSI_CMInvoice.getDesc())) { //CM导入分包数据操作
                            returnResultVo = insertManuData(todoList, sourceType, invoiceFollowSwitch);
                        } else if (sourceType.equalsIgnoreCase(SourceTypeDescEnum.GZInvoice.getDesc()) || sourceType.equalsIgnoreCase(SourceTypeDescEnum.PSI_GZInvoice.getDesc())) { //GZ导入分包数据操作
                            returnResultVo = insertGZData(todoList, sourceType, invoiceFollowSwitch);
                        } else if (sourceType.equalsIgnoreCase(SourceTypeDescEnum.queryImportInvoiceInfo.getDesc()) || sourceType.equalsIgnoreCase(SourceTypeDescEnum.PSI_GWInvoice.getDesc())){ // 关务接口导入操作
                            returnResultVo = insertGWData(todoList, sourceType, invoiceFollowSwitch);
                        } else {
                            returnResultVo = insertPsiData(todoList, sourceType, invoiceFollowSwitch);
                        }
                        if (returnResultVo.isSuccess()) {
                            successMsg.append(returnResultVo.getMessage()).append("; ");
                        } else {
                            errorMsg.append(returnResultVo.getMessage()).append("; ");
                        }
                    } catch (Exception e) {
                        log.error("交付系统同步impInvoicedata失败,数据源sourceType {}", e.getMessage(), e);
                        return ResultVo.failure(e.getMessage());
                    }
                }
                // 取出查询集合中的最大id号，用于回更使用
                OptionalLong maxIdOptional = opsPoInvoiceDataDtos.stream()
                        .mapToLong(OpsPoInvoiceDataDto::getDeliveryInvoiceId)
                        .max();
                Long updateMaxId = maxIdOptional.getAsLong();
                // bug15480 需要校验 更新id是否大于字典中最大id 异常：当取出异常的数据时，会将ID回更到历史异常的id，这样会导致一直重复读取
                if (updateMaxId > maxId) {
                    dataCodes.setExtNote1(String.valueOf(updateMaxId));
                    ResultVo<Integer> i = dictDataServiceFeignApi.updateDataParam(dataCodes); // 写入成功后，更新最大号
                }
                return ResultVo.success("交付系统同步impInvoicedatach成功", successMsg.toString());
            } finally {
                long end = System.currentTimeMillis();
                long time = (end - start) / 1000;
                log.info("新impinvoice导入完成，共计用时：{}", Long.toString(time));
                redissonUtil.unlock(ImpInvoiceConstants.REDIS_KEY_IMP_INVOICE);
            }
        } else {
            log.error("获取impinvoice同步id字典 {} 失败！", ImpInvoiceCommonEnum.IMPSYSN_DICTID.getCode());
            return ResultVo.failure("获取impinvoice同步id字典失败：" + ImpInvoiceCommonEnum.IMPSYSN_DICTID.getCode());
        }
    }


    /**
     * bug18335 【联调测试-OPS接入A点数据】AIMS推送的A点的交付数据，OPS接入GPS后，3张imp 没接入进去（之前看到的数据都是Gz接口过来的数据）
     * @param psiInsertList
     * @param codeType
     * @param invoiceFollowSwitch
     * @return
     */
    public ResultVo<String> insertPsiData(List<OpsPoInvoiceDataDto> psiInsertList, String codeType, Boolean invoiceFollowSwitch) {
        log.error("ImpInvoice交付系统同步-有部分尚未配置的来源系统，名称为：" + codeType);
        return ResultVo.success("交付系统同步-有部分尚未配置的来源系统，无需导入！");
    }


    /**
     * 日本shipF文件导入分包数据
     * code = L
     * 1.写入表 imp_invoice_master、Imp_invoice_detail_pack
     */
    public ResultVo<String> insertJPL0Data(List<OpsPoInvoiceDataDto> jpL0List, String codeType, Boolean invoiceFollowSwitch) {
        // bug16765 imp发票导入部署准备,日本的箱单导入增加开关，新旧程序同时用一个开关， 0：旧系统 1：新系统
        ResultVo<DataTypeVO>  dataTypeResultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("6004", "5");
        if (dataTypeResultVo == null || !"1".equals(dataTypeResultVo.getData().getExtNote1())) {
            log.info("新系统日本shif文件导入已关闭,请用旧程序进行导入！");
            return ResultVo.success("新系统日本shif文件导入已关闭,请用旧程序进行导入！");
        }
        if (!redissonUtil.tryLock(ImpInvoiceConstants.REDIS_KEY_IMP_JP_INVOICE, 1, 1200)) {
            return ResultVo.failure("新impinvoice日本shif导入中,请稍后重试");
        }
        StringBuilder sbMsg = new StringBuilder();
        ResultVo<String> saveResult;
        try {
            OpsPoDeliveryInvoiceLogFromSupplierDto deliveryInvoiceLog;
            List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> barcodeFromSupplierDOS;
            // todo suppilyId，需要通过字典进行转换，JPO-JP
            for (OpsPoInvoiceDataDto jpPoInvoiceDataDto : jpL0List) {
                deliveryInvoiceLog = jpPoInvoiceDataDto.getOpsPoDeliveryInvoiceLogFromSupplierDto();
                barcodeFromSupplierDOS = jpPoInvoiceDataDto.getOpsPoDeliveryInvoiceBarcodeFromSupplierDtos();
                if (CollectionUtils.isEmpty(barcodeFromSupplierDOS) || deliveryInvoiceLog.getId() == null) {
                    //记录异常表中
                    log.error("日本ship文件导入箱单失败：主表或明细表数据为空,请稍后重试");
                    impInvoiceCommonService.insertPoFailLog("日本ship文件导入箱单失败:" + codeType, String.valueOf(deliveryInvoiceLog.getId()), "ops_po_delivery_invoice_log_from_supplier", "主表或明细表数据为空,请稍后重试", codeType + ":导入ImpInvoice表报错");
                    continue;
                }
                log.info("IMP JP SHPINF : {}", jpPoInvoiceDataDto.getOpsPoDeliveryInvoiceLogFromSupplierDto().getInvoiceNo());
                // 1.1.保存发票明细
                try {
                    //start  bugid 19186 处理合并smcCode判断  拆分deliveryInvoiceLog 和 barcodeFromSupplierDOS 循环
                    Map<String, List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto>> stringListMap = splitInvoiceByBarcode(deliveryInvoiceLog, barcodeFromSupplierDOS);
                    if(Objects.nonNull(stringListMap)){
                        Iterator<String> iterator = stringListMap.keySet().iterator();
                        // 原始发票号 原始发票id
                        String oriInvoiceNo = deliveryInvoiceLog.getInvoiceNo();
                        String billNo = commonServiceFeignApi.generatorBillNo("23").getData();
                        if (PublicUtil.isEmpty(billNo)) {
                            log.error( "addSplitInvoiceTable:获取原始发票ID失败" );
                        }
                        while (iterator.hasNext()){
                            String splitInvoiceNo = iterator.next();
                            // 拆分发票号
                            deliveryInvoiceLog.setInvoiceNo(splitInvoiceNo);

                            List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> barcodeList = stringListMap.get(splitInvoiceNo);
                            deliveryInvoiceLog.setSplitInvoiceId(null);
                            // 写入impInvoiceMaster、impInvoiceDetailPack
                            saveResult = saveImpInvoicePack(deliveryInvoiceLog, barcodeList, invoiceFollowSwitch,barcodeFromSupplierDOS);
                            // 写入拆分发票关联表
                            if(Objects.nonNull(deliveryInvoiceLog.getSplitInvoiceId())){
                                addSplitInvoiceTable(oriInvoiceNo,splitInvoiceNo,deliveryInvoiceLog.getSplitInvoiceId(),billNo);
                            }
                            sbMsg.append(saveResult.getMessage()).append(";");
                            if (!saveResult.isSuccess()) {
                                log.error(saveResult.getMessage());
                                continue;
                            }
                        }
                    // end
                    }else {
                        saveResult = saveImpInvoicePack(deliveryInvoiceLog, barcodeFromSupplierDOS, invoiceFollowSwitch,null);
                        sbMsg.append(saveResult.getMessage()).append(";");
                        if (!saveResult.isSuccess()) {
                            log.error(saveResult.getMessage());
                            continue;
                        }
                    }
                } catch (Exception e) {
                    log.error("日本ship文件导入箱单失败：" + e.getMessage());
                    log.error(deliveryInvoiceLog.toString());
                    sbMsg.append(deliveryInvoiceLog.getInvoiceNo())
                            .append("导入箱单失败：" + e.getMessage());
                    // todo 是否加入异常处理表
                    impInvoiceCommonService.insertPoFailLog(ImpInvoiceConstants.INVOICE_SYSN_ERROR + codeType, String.valueOf(deliveryInvoiceLog.getId()), "ops_po_delivery_invoice_log_from_supplier", e.getMessage(), codeType + ":导入ImpInvoice表报错");
                }
            }
        } catch (Exception e) {
            log.error("SHIPF文件导入分包明细 {}", e.getMessage(), e);
            return ResultVo.failure(e.getMessage());
        } finally {
            redissonUtil.unlock(ImpInvoiceConstants.REDIS_KEY_IMP_JP_INVOICE);
        }
        return ResultVo.success("SHIPF文件导入分包明细导入完毕.", sbMsg.toString());
    }


    /**
     * bugid 19186 处理合并smcCode判断
     * 写入拆分发票管理表
     * @param oriInvoiceNo
     * @param splitInvoiceNo
     * @param splitInvoiceId
     */
    public void addSplitInvoiceTable(String oriInvoiceNo,String splitInvoiceNo,Long splitInvoiceId ,String billNo ){
        InvoiceSplit re = new InvoiceSplit();
        re.setSplitInvoiceId(splitInvoiceId);
        re.setSplitInvoiceNo(splitInvoiceNo);
        re.setOriginalInvoiceNo(oriInvoiceNo);
        // 验证重复
        String sInvoiceNo = splitSmcCodeDao.getInvoiceSplitById(splitInvoiceId);
        if(StringUtils.isNotBlank(sInvoiceNo)){
            log.error("写入拆分发票管理表,拆分发票id已存在");
            return;
        }
        re.setMergeInvoiceId(Long.parseLong(billNo));
        invoiceSplitMapper.insertSelective(re);
    }


    /**
     *   bugid 19186 处理合并smcCode判断
     *   3c 冷干机拆发票
     * @param deliveryInvoiceLog
     * @param priceDetailList
     * @return
     */
    public Map<String , List<OpsPoInvoicePriceDetailLogFromSupplierDto>> splitInvoiceByPrice(OpsPoDeliveryInvoiceLogFromSupplierDto deliveryInvoiceLog
            ,List<OpsPoInvoicePriceDetailLogFromSupplierDto> priceDetailList,Map<String,String> receiveWarehouseMap){
        String supplierCode = deliveryInvoiceLog.getIssuerCode();
        // 供应商为日本
        if(StringUtils.isBlank(supplierCode) || !"JP".equals(supplierCode)){
            return null;
        }
        Map<String,List<OpsPoInvoicePriceDetailLogFromSupplierDto>> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(priceDetailList)){
            for(OpsPoInvoicePriceDetailLogFromSupplierDto priceDetail : priceDetailList){
                OrderNoInfo orderNoInfo = new OrderNoInfo();
                try {
                    orderNoInfo = purchaseConvertDeliveryService.convertPoNoFormPurchase(priceDetail.getPono(), String.valueOf(priceDetail.getLineItem()));
                } catch (Exception e) {
                    log.error("关务接口导入发票失败，发票号：" + deliveryInvoiceLog.getInvoiceNo() + "错误信息：" + e.getMessage());
                    impInvoiceCommonService.insertPoFailLog("Imp子项订单号解析错误，导入错误", String.valueOf(deliveryInvoiceLog.getId()), "ops_po_delivery_invoice_log_from_supplier", e.getMessage(), "ImpInvoiceSysn子项导入错误");
                    continue;
                }
                OpsPurchaseinvoice purchaseinvoice = splitSmcCodeDao.getSubCodeByOrderNo(orderNoInfo.getOrderNo(), orderNoInfo.getItemNo()
                        , orderNoInfo.getSplitItem() == null ? 0 : orderNoInfo.getSplitItem());
                if(Objects.nonNull(purchaseinvoice) && StringUtils.isNotBlank(purchaseinvoice.getSubCode())){
                    String splitInvoiceNo = deliveryInvoiceLog.getInvoiceNo()+"-" + purchaseinvoice.getReceivewarehouseid();
                    if(!receiveWarehouseMap.containsKey(splitInvoiceNo)){
                        receiveWarehouseMap.put(splitInvoiceNo,purchaseinvoice.getReceivewarehouseid());
                    }
                    if(map.containsKey(splitInvoiceNo)){
                        map.get(splitInvoiceNo).add(priceDetail);
                    }else {
                        List<OpsPoInvoicePriceDetailLogFromSupplierDto> list = new ArrayList<>();
                        list.add(priceDetail);
                        map.put(splitInvoiceNo,list);
                    }
                }
            }
        }
        if(!map.isEmpty()){
            return map;
        }
        return null;
    }

    /**
     * bugid 19186 处理合并smcCode判断
     * 3c 冷干机拆发票
     * @param deliveryInvoiceLog
     * @param barcodeList
     * @return
     */
    public Map<String,List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto>>  splitInvoiceByBarcode(OpsPoDeliveryInvoiceLogFromSupplierDto deliveryInvoiceLog
            , List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> barcodeList){
        Map<String,List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto>> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(barcodeList)){
            for (OpsPoDeliveryInvoiceBarcodeFromSupplierDto barcodeFromSupplierDO : barcodeList) {
                OrderNoInfo orderNoInfo = new OrderNoInfo();
                try {
                    orderNoInfo = purchaseConvertDeliveryService.convertPoNoFormPurchase(barcodeFromSupplierDO.getPono(), barcodeFromSupplierDO.getLineItem().toString());
                } catch (Exception e) {
                    log.error("SHIF文件子项导入失败，发票号：" + deliveryInvoiceLog.getInvoiceNo()+ "，订单号："+ barcodeFromSupplierDO.getPono() + "，错误信息：" + e.getMessage());
                    impInvoiceCommonService.insertPoFailLog("Imp子项订单号解析错误，导入错误", String.valueOf(deliveryInvoiceLog.getId()), "ops_po_delivery_invoice_log_from_supplier", e.getMessage(), "ImpInvoiceSysn子项导入错误");
                    continue;
                }

                // bugid 19186 合并smccode ship文件如果存在subCoe 需变更purchaseInvoice subCode和收货仓
                if(StringUtils.isNotBlank(barcodeFromSupplierDO.getSubCode())){
                    String warehouseCodeBySubCode = splitSmcCodeDao.getWarehouseCodeBySubCode(barcodeFromSupplierDO.getSubCode());
                    splitSmcCodeDao.updatePurchaseInvoiceByOrderNo(orderNoInfo.getOrderNo(), orderNoInfo.getItemNo()
                            , orderNoInfo.getSplitItem() == null ? 0 : orderNoInfo.getSplitItem(),barcodeFromSupplierDO.getSubCode(),warehouseCodeBySubCode);
                    String splitInvoiceNo = deliveryInvoiceLog.getInvoiceNo()+"-" + warehouseCodeBySubCode;
                    if(map.containsKey(splitInvoiceNo)){
                        map.get(splitInvoiceNo).add(barcodeFromSupplierDO);
                    }else {
                        List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> list = new ArrayList<>();
                        list.add(barcodeFromSupplierDO);
                        map.put(splitInvoiceNo,list);
                    }
                }else {
                    OpsPurchaseinvoice purchaseinvoice = splitSmcCodeDao.getSubCodeByOrderNo(orderNoInfo.getOrderNo(), orderNoInfo.getItemNo()
                            , orderNoInfo.getSplitItem() == null ? 0 : orderNoInfo.getSplitItem());
                    if(Objects.nonNull(purchaseinvoice) && StringUtils.isNotBlank(purchaseinvoice.getSubCode())){
                        String splitInvoiceNo = deliveryInvoiceLog.getInvoiceNo()+"-" + purchaseinvoice.getReceivewarehouseid();
                        if(map.containsKey(splitInvoiceNo)){
                            map.get(splitInvoiceNo).add(barcodeFromSupplierDO);
                        }else {
                            List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> list = new ArrayList<>();
                            list.add(barcodeFromSupplierDO);
                            map.put(splitInvoiceNo,list);
                        }
                    }
                }
            }
        }
        if(!map.isEmpty()){
            return map;
        }
        return null;
    }


    //    @Transactional
    public ResultVo<String> saveImpInvoicePack(OpsPoDeliveryInvoiceLogFromSupplierDto deliveryInvoiceLog
            , List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> barcodeList, Boolean invoiceFollowSwitch, List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> totalBarcodeList) {
        ImpInvoiceMaster invoiceMasterDto;
        List<ImpInvoiceDetailPack> impInvoiceDetailPackDtos;
        ImpInvoiceDetailPack detailPackDto;
        int successItem = 0;
        int faultItem = 0;
        ResultVo<String> resultVo = impInvoiceCommonService.getOpsSupplierByConfig(deliveryInvoiceLog.getIssuerCode(),"supplierid"); // 2024-05-09根据适配器供应商转换为OPS的供应商
        if (!resultVo.isSuccess()) {
            log.error("新impInvoice导入供应商转换失败：{}", resultVo.getMessage());
            return ResultVo.failure(resultVo.getMessage());
        }
        String supplierCode = resultVo.getData();
        if (!CollectionUtils.isEmpty(barcodeList)) {
            Date createdDate = new Date();
            //jp-ship设置master表参数
            invoiceMasterDto = new ImpInvoiceMaster();
            invoiceMasterDto.setInvoiceNo(deliveryInvoiceLog.getInvoiceNo());
            invoiceMasterDto.setCreateUser(ImpInvoiceCommonEnum.JPSHIP.getCode());
            invoiceMasterDto.setStatus(IImpInvoiceMasterStatusEnum.PREARRIVAL.getType());
            invoiceMasterDto.setShipDate(barcodeList.get(0).getShipTime());
            invoiceMasterDto.setSupplierCode(supplierCode);
            //  transtype转换
            if (StringUtils.isNotBlank(barcodeList.get(0).getTranstypeCode())) {
                // bug 15251 WTSR2024001000-发单前运输方式修改增加【Courier快递】选项
                ResultVo<String> transTypeResultVo = impInvoiceCommonService.getOpsSupplierByConfig(barcodeList.get(0).getTranstypeCode(),"transtype"); // 2024-05-09根据适配器供应商转换为OPS的供应商
                if (!transTypeResultVo.isSuccess()) {
                    log.error("新impInvoice导入transtype转换失败：{}", transTypeResultVo.getMessage());
                    return ResultVo.failure(transTypeResultVo.getMessage());
                }
                invoiceMasterDto.setTransType(transTypeResultVo.getData());
            }
            invoiceMasterDto.setInvoiceType(impInvoiceCommonService.invoiceTypeConvert(invoiceMasterDto.getSupplierCode(), "")); // 发票类型的转换
            invoiceMasterDto.setBoxQty(barcodeList.size()); // 箱数汇总
            invoiceMasterDto.setWeight(barcodeList.stream()
                    .map(barcode -> {
                        BigDecimal netWeight = Optional.ofNullable(barcode.getNetWeight()).orElse(BigDecimal.ZERO);
                        int quantity = Optional.ofNullable(barcode.getQuantity()).orElse(0);
                        return BigDecimalUtil.mul(netWeight, BigDecimal.valueOf(quantity));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            invoiceMasterDto.setOrderQty((int) barcodeList.stream().map(OpsPoDeliveryInvoiceBarcodeFromSupplierDto::getPono).distinct().count()); //不同订单的数量
            if(!CollectionUtils.isEmpty(totalBarcodeList)){
                invoiceMasterDto.setBoxQty(totalBarcodeList.size()); // 箱数汇总
                invoiceMasterDto.setWeight(totalBarcodeList.stream().map(OpsPoDeliveryInvoiceBarcodeFromSupplierDto::getNetWeight).reduce(BigDecimal.ZERO, BigDecimal::add)); //重量求和
                invoiceMasterDto.setOrderQty((int) totalBarcodeList.stream().map(OpsPoDeliveryInvoiceBarcodeFromSupplierDto::getPono).distinct().count()); //不同订单的数量
            }
            // 拆分发票仅拆数量
            invoiceMasterDto.setTotalQty(barcodeList.stream().mapToInt(OpsPoDeliveryInvoiceBarcodeFromSupplierDto::getQuantity).sum()); // 总数量
            invoiceMasterDto.setCreateTime(createdDate);
            // 组合detail字段明细
            impInvoiceDetailPackDtos = new ArrayList<>(barcodeList.size());
            for (OpsPoDeliveryInvoiceBarcodeFromSupplierDto barcodeFromSupplierDO : barcodeList) {
                detailPackDto = new ImpInvoiceDetailPack();
                detailPackDto.setStatus((short) 1);
                // 解析orderno等信息
//                OrderNoInfo orderNoInfo = new OrderNoInfo().convertJPOrder(barcodeFromSupplierDO.getPono(), barcodeFromSupplierDO.getLineItem().toString());
                // bug 13606 优化采购pono解析成orderno
                OrderNoInfo orderNoInfo = new OrderNoInfo();
                try {
                    orderNoInfo = purchaseConvertDeliveryService.convertPoNoFormPurchase(barcodeFromSupplierDO.getPono(), barcodeFromSupplierDO.getLineItem().toString());
                } catch (Exception e) {
                    log.error("SHIF文件子项导入失败，发票号：" + deliveryInvoiceLog.getInvoiceNo()+ "，订单号："+ barcodeFromSupplierDO.getPono() + "，错误信息：" + e.getMessage());
                    impInvoiceCommonService.insertPoFailLog("Imp子项订单号解析错误，导入错误", String.valueOf(deliveryInvoiceLog.getId()), "ops_po_delivery_invoice_log_from_supplier", e.getMessage(), "ImpInvoiceSysn子项导入错误");
                    continue;
                }
                detailPackDto.setOrderNo(orderNoInfo.getOrderNo());
                detailPackDto.setItemNo(orderNoInfo.getItemNo());
                detailPackDto.setSplitItemNo(orderNoInfo.getSplitItem());
                detailPackDto.setFullOrderNo(orderNoInfo.getFullOrderNo());
                detailPackDto.setPono(orderNoInfo.getPoNo());
                detailPackDto.setPoitemno(orderNoInfo.getPoItemNo());
                // bug 15480日本pack表导入时，原始单号取数字段变更
                detailPackDto.setImpOrderNo(orderNoInfo.getOrFullOrderNo());
                if (orderNoInfo.getOrderType() != null) {
                    detailPackDto.setOrderType(orderNoInfo.getOrderType().toString());
                }
                detailPackDto.setOrigin(barcodeFromSupplierDO.getOrigin());
//                detailPackDto.setPrice(contentData.getPrice()); // price字段不写了，后期导入价格数据时更新
                detailPackDto.setProductCode(barcodeFromSupplierDO.getProductCode());
                detailPackDto.setRemark(barcodeFromSupplierDO.getImpOrderNo()); // todo 确定imporderno拼接方式，考虑自己通过-来拆分
                detailPackDto.setShelfCode(barcodeFromSupplierDO.getShelfCode());
//                    detailPackDto.setCurrency(contentData.getCurrency());  // jp Currency信息，为空
//                    detailPackDto.setEnName(contentData.getCNName()); // jpCNName信息，为空
                detailPackDto.setShipMethod(barcodeFromSupplierDO.getTranstypeCode());
                detailPackDto.setFromCode(""); // jp FromCode信息，均为空字符串
                detailPackDto.setCaseNo(barcodeFromSupplierDO.getCaseNo());
                detailPackDto.setBarcode(barcodeFromSupplierDO.getBarcode());
                detailPackDto.setInvoiceNo(deliveryInvoiceLog.getInvoiceNo());
                detailPackDto.setModelNo(barcodeFromSupplierDO.getModelNo());
                detailPackDto.setQuantity(barcodeFromSupplierDO.getQuantity());
                detailPackDto.setShipDate(barcodeFromSupplierDO.getShipTime());
                detailPackDto.setSupplierCode(supplierCode);
                detailPackDto.setWeight(barcodeFromSupplierDO.getNetWeight());
                detailPackDto.setRohsCode(barcodeFromSupplierDO.getRoshCode());
                detailPackDto.setCreateUser(ImpInvoiceCommonEnum.SHPINF.getCode());
                detailPackDto.setUpdateUser(ImpInvoiceCommonEnum.SHPINF.getCode());
                impInvoiceDetailPackDtos.add(detailPackDto);
            }
            //1.检查是否存在
            ImpInvoiceMaster existMasterDO = getExistImpInvoiceMasterByInvoiceNo(invoiceMasterDto.getSupplierCode(), invoiceMasterDto.getInvoiceNo(), invoiceMasterDto.getShipDate());
            if (existMasterDO != null) {
                if (existMasterDO.getStatus() != 1
                        && existMasterDO.getStatus() != 2
                        && existMasterDO.getStatus() != 9) {
                    // bug 16096 属于无商业价值发票，新旧导入程序中，不同数据源导入顺序不一致，会造成差异,新系统增加判断
                    if (!(existMasterDO.getStatus() == IImpInvoiceMasterStatusEnum.PACK.getType() && existMasterDO.getRemark().contains("无商业价值"))) {
                        log.error(ImpInvoiceCommonEnum.JPSHIPERROR1.getCode() + deliveryInvoiceLog.getInvoiceNo() + ",deliveryInvoiceLog表id:" + deliveryInvoiceLog.getId());
                        return ResultVo.failure(ImpInvoiceCommonEnum.JPSHIPERROR1.getCode() + deliveryInvoiceLog.getInvoiceNo());
                    }
                }
                invoiceMasterDto.setId(existMasterDO.getId());
                invoiceMasterDto.setShipDate(existMasterDO.getShipDate());
                invoiceMasterDto.setShipment(existMasterDO.getShipment());
                if (existMasterDO.getPrearriveDate() != null) {
                    invoiceMasterDto.setPrearriveDate(existMasterDO.getPrearriveDate());
                }
            }
            //预计到货日为空的，计算预计到货日期
            if ((existMasterDO == null || existMasterDO.getPrearriveDate() == null)
                    && invoiceMasterDto.getShipDate() != null && invoiceMasterDto.getPrearriveDate() == null) {
                // 如果收货仓库为空时，用订单查查询
                if (StringUtils.isBlank(invoiceMasterDto.getReceiveWarehouseCode())) {
                    ImpInvoiceDetailPack detailDO = impInvoiceDetailPackDtos.get(0);
                    // bug15074 2024-09-02撤销对SplitItemNo的赋值，保持原值
//                    if (detailDO.getSplitItemNo() == null) {
//                        detailDO.setSplitItemNo(0);
//                    }
                    invoiceMasterDto.setReceiveWarehouseCode(
                            impInvoiceSysnDao.getReceiveWarehouseCodeByOrderNo(detailDO.getOrderNo(), detailDO.getItemNo(), detailDO.getSplitItemNo() == null ? 0 : detailDO.getSplitItemNo()));
                }
                invoiceMasterDto.setPrearriveDate(impInvoiceCommonService.calcEsArrivalDate(invoiceMasterDto.getReceiveWarehouseCode(),
                        invoiceMasterDto.getSupplierCode(),
                        invoiceMasterDto.getTransType(), invoiceMasterDto.getShipDate()));
            }
            // 2.保存到导入发票主表，当主表存在时 更新主表中数据
            if (invoiceMasterDto.getId() == null) {
                invoiceMasterDto.setDataType(2);
                String billNo = commonServiceFeignApi.generatorBillNo("21").getData();
                if (PublicUtil.isEmpty(billNo)) {
//                        updateAdapterHandleResult(opsPoAdapterDataDto, codeType, HandleResultEnum.notAccepted.getCode(), ImpInvoiceCommonEnum.JPSHIPERROR2.getCode());
                    log.error(ImpInvoiceCommonEnum.JPSHIPERROR2.getCode() + ",deliveryInvoiceLog表id:" + deliveryInvoiceLog.getId());
                    return ResultVo.failure(ImpInvoiceCommonEnum.JPSHIPERROR2.getCode() + deliveryInvoiceLog.getInvoiceNo());
                }
                invoiceMasterDto.setId(Long.parseLong(billNo));
                deliveryInvoiceLog.setSplitInvoiceId(invoiceMasterDto.getId());
                invoiceMasterDto.setCreateUser(ImpInvoiceCommonEnum.JPSHIP.getCode());
                int insert = impInvoiceMasterMapper.insertSelective(invoiceMasterDto);
                if (insert != 1) {
                    log.error(ImpInvoiceCommonEnum.JPSHIPERROR3.getCode() + ",deliveryInvoiceLog表id:" + deliveryInvoiceLog.getId());
                    return ResultVo.failure(ImpInvoiceCommonEnum.JPSHIPERROR3.getCode() + deliveryInvoiceLog.getInvoiceNo());
                }
            } else { //更新master表
                invoiceMasterDto.setDataType(existMasterDO.getDataType() | 2);
                ImpInvoiceMaster updMasterDO = new ImpInvoiceMaster();
                updMasterDO.setId(existMasterDO.getId());
                updMasterDO.setDataType(invoiceMasterDto.getDataType());
                updMasterDO.setOrderQty(invoiceMasterDto.getOrderQty());
                updMasterDO.setTotalQty(invoiceMasterDto.getTotalQty());
                updMasterDO.setBoxQty(invoiceMasterDto.getBoxQty());
                updMasterDO.setUpdateTime(createdDate);
                updMasterDO.setUpdateUser(ImpInvoiceCommonEnum.JPSHIP.getCode());
                impInvoiceMasterMapper.updateByPrimaryKeySelective(updMasterDO);
            }
            ImpInvoiceDetailPack exitPackID;
            successItem = 0;
            faultItem = 0;
            // 3.写入到分包明细表
            for (ImpInvoiceDetailPack impInvoiceDetailDO : impInvoiceDetailPackDtos) {
                impInvoiceDetailDO.setInvoiceId(invoiceMasterDto.getId()); //分包表invoice_id,为主表的id
                exitPackID = getImpInvoiceDetailPack(invoiceMasterDto.getId(),
                        impInvoiceDetailDO.getFullOrderNo(),
                        impInvoiceDetailDO.getBarcode());
                if (exitPackID != null) {
                    log.info("JPship导入已存在pack: {} {}", exitPackID.getOrderNo(), exitPackID.getId());
                    faultItem++;
                    continue;
                }
                addImpInvoiceDetailPack(impInvoiceDetailDO);
                successItem++;
            }
            // 加入发票导入后事件的全局开关，控制导入IMP表的后续操作
            if (invoiceFollowSwitch) {
                List<OpsImpInvoiceDetailVO> invoiceDetails = BeanCopyUtil.copyList(impInvoiceDetailPackDtos,
                        OpsImpInvoiceDetailVO.class);
                // 2.生成在途预到货数据  shipF没有导关务发票数据，按箱单数据生成在途
                if ((invoiceMasterDto.getDataType() & 1) == 0) {
                    // 是否启动箱单预到货,1启动,0不启动
                    ResultVo<DataTypeVO> dataTypeResultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "14");
                    if (dataTypeResultVo != null && "1".equals(dataTypeResultVo.getData().getExtNote1())) {
                        toUpdatePOTransStatus(invoiceDetails, invoiceMasterDto);
                    }
                }
                // 3更新订单状态
                try {
                    toUpdateOrderState(invoiceMasterDto, invoiceDetails);
                } catch (Exception e) {
                    log.error("更新货期失败:{} {}", e.getMessage(), invoiceMasterDto.toString());
                }
                // todo bug14838 需要追加日本的自动入库逻辑
                // 发送发票入库消息
            }
        }
        return ResultVo.success(deliveryInvoiceLog.getInvoiceNo(),
                deliveryInvoiceLog.getInvoiceNo() + "增加项数：" + successItem + ",存在项数：" + faultItem);
    }

    /**
     * @param gzlist 1.数据源是广州制造
     *               2.转换后 写入表 imp_invoice_master、Imp_invoice_detail、Imp_invoice_detail_pack
     *               3. 三方的只写L0, 自报的 写L0和P1
     */
    public ResultVo<String> insertGZData(List<OpsPoInvoiceDataDto> gzlist, String codeType, Boolean invoiceFollowSwitch) {
        if (!redissonUtil.tryLock(ImpInvoiceConstants.REDIS_KEY_IMP_GP_INVOICE, 1, 1200)) {
            return ResultVo.failure("新impinvoice广州制造导入中,请稍后重试");
        } // redis锁
        try {
            StringBuilder sbMsg = new StringBuilder();
            ImpInvoiceMaster invoiceMasterDto;
            List<ImpInvoiceDetail> impInvoiceDetails;
            ImpInvoiceDetail invoiceDetail;
            // 定义集团采购适配器中间表
            OpsPoDeliveryInvoiceLogFromSupplierDto opsPoDeliveryInvoiceLog; //总表
            List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> barcodeFromSupplierDOList; // barcode子表
            OpsPoInvoicePriceLogFromSupplierDto poInvoicePriceLogFromSupplierDO; // 价格总表
            List<OpsPoInvoicePriceDetailLogFromSupplierDto> poInvoicePriceDetailList; // 价格子表
            //  2024-06-10 现状，L和P 都只写一条数据，不需要再进行手工合并了
            Map<String, String> supplierNameMap = impInvoiceCommonService.getSuppilyNameMapper();
            for (OpsPoInvoiceDataDto gzOpsPoInvoiceDataDto : gzlist) { // 根据发票号，循环处理数据，同时处理L、P1
                opsPoDeliveryInvoiceLog = gzOpsPoInvoiceDataDto.getOpsPoDeliveryInvoiceLogFromSupplierDto();
                barcodeFromSupplierDOList = gzOpsPoInvoiceDataDto.getOpsPoDeliveryInvoiceBarcodeFromSupplierDtos();
                poInvoicePriceLogFromSupplierDO = gzOpsPoInvoiceDataDto.getOpsPoInvoicePriceLogFromSupplierDto();
                poInvoicePriceDetailList = gzOpsPoInvoiceDataDto.getOpsPoInvoicePriceDetailLogFromSupplierDtos();
                if (CollectionUtils.isEmpty(barcodeFromSupplierDOList) || CollectionUtils.isEmpty(poInvoicePriceDetailList) || poInvoicePriceLogFromSupplierDO == null || opsPoDeliveryInvoiceLog.getId() == null) {
                    log.error("适配器中间表有数据为空，无法写入" + ",deliveryInvoiceLog表总表id:" + poInvoicePriceLogFromSupplierDO.getId());
                    //记录异常表中
                    impInvoiceCommonService.insertPoFailLog("适配器中间表有数据为空，无法写入:" + codeType, String.valueOf(opsPoDeliveryInvoiceLog.getId()), "ops_po_delivery_invoice_log_from_supplier", "主表或明细表数据为空,请稍后重试", codeType + ":导入ImpInvoice表报错");
                    continue;
                }
                String invoiceNo = opsPoDeliveryInvoiceLog.getInvoiceNo(); // 发票号
                try {
                    ResultVo<String> resultVo = impInvoiceCommonService.getOpsSupplierByConfig(opsPoDeliveryInvoiceLog.getIssuerCode(),"supplierid"); // 2024-05-09根据适配器供应商转换为OPS的供应商
                    if (!resultVo.isSuccess()) {
//                    return ResultVo.failure(resultVo.getMessage());
                        log.error("广州制造发票号:" + invoiceNo + ",id:" + opsPoDeliveryInvoiceLog.getId() + "错误信息：" + resultVo.getMessage());
                        impInvoiceCommonService.insertPoFailLog("ImpInvoiceSysn导入错误-" + codeType, String.valueOf(opsPoDeliveryInvoiceLog.getId()), "ops_po_delivery_invoice_log_from_supplier", resultVo.getMessage(), codeType + ":导入ImpInvoice表报错");
                        continue;
                    }
                    String supplierCode = resultVo.getData();
                    invoiceMasterDto = new ImpInvoiceMaster(); //发票主表
                    String receiveWarehouseCode; // 出库仓的转换
                    boolean isNewMaster = true;
                    invoiceMasterDto.setInvoiceNo(opsPoDeliveryInvoiceLog.getInvoiceNo());
                    invoiceMasterDto.setShipDate(DateUtil.getDate(opsPoDeliveryInvoiceLog.getShipDate())); // DateUtil.getDate(oneItem.getExecuteTime()) 是否需要截取到 年月日
                    invoiceMasterDto.setInvoiceDate(DateUtil.getDate(opsPoDeliveryInvoiceLog.getInvoiceDate())); // DateUtil.getDate(oneItem.getExecuteTime()) 是否需要截取到 年月日
                    invoiceMasterDto.setPrearriveDate(DateUtil.getToday()); // 需要截取到 年月日
                    invoiceMasterDto.setSupplierCode(supplierCode);
                    if (StringUtils.isNotBlank(poInvoicePriceLogFromSupplierDO.getTransType())) {
                        // bug 15251 WTSR2024001000-发单前运输方式修改增加【Courier快递】选项
                        ResultVo<String> transTypeResultVo = impInvoiceCommonService.getOpsSupplierByConfig(poInvoicePriceLogFromSupplierDO.getTransType(),"transtype"); // 2024-05-09根据适配器供应商转换为OPS的供应商
                        if (!transTypeResultVo.isSuccess()) {
                            log.error("新impInvoice导入transtype转换失败：{}", transTypeResultVo.getMessage());
                            continue;
                        }
                        invoiceMasterDto.setTransType(transTypeResultVo.getData());
//                        invoiceMasterDto.setTransType(ImpGpsTranstypeEnum.getOpsCodeByGps(poInvoicePriceLogFromSupplierDO.getTransType()));
                    }
                    // 写入价格信息
                    invoiceMasterDto.setTotalQty(poInvoicePriceLogFromSupplierDO.getTotalQty());
                    invoiceMasterDto.setOrderQty((int) poInvoicePriceDetailList.stream().map(OpsPoInvoicePriceDetailLogFromSupplierDto::getPono).distinct().count()); //统计不同订单数量
                    invoiceMasterDto.setBoxQty(poInvoicePriceLogFromSupplierDO.getBoxQty());
                    invoiceMasterDto.setWeight(poInvoicePriceLogFromSupplierDO.getWeight());
                    invoiceMasterDto.setAmount(poInvoicePriceLogFromSupplierDO.getAmount());
                    invoiceMasterDto.setShipment(supplierNameMap.getOrDefault(invoiceMasterDto.getSupplierCode(), ""));  // 供应商名称转换
                    invoiceMasterDto.setUpdateTime(new Date());
                    // 校验主表中是否存在
                    ImpInvoiceMaster exitMasterDO = getExistImpInvoiceMasterByInvoiceNo(invoiceMasterDto.getSupplierCode(),
                            invoiceMasterDto.getInvoiceNo(), invoiceMasterDto.getShipDate());
                    if (exitMasterDO != null) {
                        if (exitMasterDO.getStatus() == 3 || exitMasterDO.getStatus() == 6 || exitMasterDO.getStatus() == 9) {
                            log.error("当前master表状态不可再重新导入,发票号：" + opsPoDeliveryInvoiceLog.getInvoiceNo() + ",deliveryInvoiceLog表id:" + opsPoDeliveryInvoiceLog.getId());
                            continue;
                        }
                        invoiceMasterDto.setId(exitMasterDO.getId());
                        isNewMaster = false;
                    }
                    invoiceMasterDto.setUpdateUser(ImpInvoiceCommonEnum.GZIMP.getCode());
                    invoiceMasterDto.setStatus(IImpInvoiceMasterStatusEnum.BEIMP.getType());
                    invoiceMasterDto.setCurrency(poInvoicePriceLogFromSupplierDO.getCurrency());
                    invoiceMasterDto.setExchangeRate(poInvoicePriceLogFromSupplierDO.getExchangeRate());
                    invoiceMasterDto.setInvoiceType(InvoiceTypeAdapterEnum.CM.getCode());
                    invoiceMasterDto.setRemark(ImpInvoiceCommonEnum.GZIMP.getCodeName());
                    invoiceMasterDto.setReceiveWarehouseCode(barcodeFromSupplierDOList.get(0).getReceiveWarehouseId()); // 查找barcode子表中的 warehouseCode
                    if (StringUtils.isNotBlank(invoiceMasterDto.getReceiveWarehouseCode()) && invoiceMasterDto.getReceiveWarehouseCode().startsWith("CN0-")) {
                        invoiceMasterDto.setReceiveWarehouseCode(invoiceMasterDto.getReceiveWarehouseCode().substring(4)); // "CN0-".length() == 3
                    }
                    //3.保存到导入发票主表
                    if (invoiceMasterDto.getId() == null) {
                        invoiceMasterDto.setDataType(1);
                        invoiceMasterDto.setCreateUser(ImpInvoiceCommonEnum.GZIMP.getCode());
                        invoiceMasterDto.setCreateTime(new Date());
                        String billNo = commonServiceFeignApi.generatorBillNo("21").getData();
                        if (PublicUtil.isEmpty(billNo)) {
                            log.error("生成单号异常" + ",deliveryInvoiceLog表id:" + opsPoDeliveryInvoiceLog.getId());
                            continue;
                        }
                        invoiceMasterDto.setId(Long.parseLong(billNo));
                        impInvoiceMasterMapper.insertSelective(invoiceMasterDto);
                    } else {
                        impInvoiceMasterMapper.updateByPrimaryKeySelective(invoiceMasterDto);
                    }
                    // 需要先写入master表，获取主表id信息
//                List<ImpInvoiceDetailDto> invoiceDetails = new ArrayList<>(poInvoicePriceDetailList.size());
                    List<OpsImpInvoiceDetailVO> listItemVO = new ArrayList<>();
                    OpsPoDeliveryInvoiceBarcodeFromSupplierDto barcodeFromSupplierDO;
                    ImpInvoiceDetail detailDO;
                    OpsImpInvoiceDetailVO impInvoiceDetailModel;
                    OrderNoInfo orderNoInfo;
                    String username;
                    int successItem = 0;
                    int faultItem = 0;
                    HashMap<String, Long> barcodeHashMap = new HashMap<String, Long>();
                    for (OpsPoInvoicePriceDetailLogFromSupplierDto item : poInvoicePriceDetailList) {
                        detailDO = new ImpInvoiceDetail();
                        detailDO.setInvoiceNo(invoiceMasterDto.getInvoiceNo());
                        detailDO.setInvoiceId(invoiceMasterDto.getId());
                        detailDO.setFromId(item.getFromId());
                        if (!isNewMaster) {
                            // gz的判断是否存在的方式，需要变更
                            ImpInvoiceDetail existDetailDO = getImpInvoiceDetailByFromId(invoiceMasterDto.getId(), detailDO.getFromId()); // 根据masterId和 fromID 查找是否存在相同子项
                            if (existDetailDO != null) {
                                detailDO.setId(existDetailDO.getId());
                            }
                        }
                        // 用priceDetail的数据去匹配barcode
//                        barcodeFromSupplierDO = barcodeFromSupplierDOList.stream().filter(barcode -> barcode.getPono().equals(item.getPono())
//                                && barcode.getModelNo().equals(item.getModelNo())
//                                && barcode.getLineItem().equals(item.getLineItem()) && barcode.getQuantity().equals(item.getQuantity())
//                                && barcode.getNetWeight().equals(item.getWeight())
//                                && barcode.getFromId().equals(item.getFromId())
//                                && barcode.getSupplierSystemExecTime().equals(item.getSupplierSystemExecTime()) // todo 新增抽取时间匹配
//                        ).collect(Collectors.toList()).get(0); // 筛选barcode表信息
                        // bug 18335 为了兼容新IPS取数，所以将barcode匹配规则，改为与中国制造的取数模式一致，用pono进行取值
//                        List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> barcodeFromSupplierDtos = barcodeFromSupplierDOList.stream().filter(barcode -> barcode.getPono().equals(item.getPono())
//                                && barcode.getModelNo().equals(item.getModelNo())
//                                && barcode.getLineItem().equals(item.getLineItem()) && barcode.getQuantity().equals(item.getQuantity())
//                                && barcode.getNetWeight().equals(item.getWeight())).collect(Collectors.toList()); // 筛选barcode表信息
                        // bug bug18428
                        List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> barcodeFromSupplierDtos = barcodeFromSupplierDOList.stream()
                                .filter(barcode -> Objects.equals(barcode.getPono(), item.getPono())
                                        && Objects.equals(barcode.getModelNo(), item.getModelNo())
                                        && Objects.equals(barcode.getLineItem(), item.getLineItem())
                                        && Objects.equals(barcode.getQuantity(), item.getQuantity())
                                        && Objects.equals(barcode.getNetWeight(), item.getWeight()))
                                .collect(Collectors.toList());
                        if (CollectionUtils.isEmpty(barcodeFromSupplierDtos)) {
                            log.error("广州制造impinvoice导入错误，未找到对应的barcode信息，订单号：" + item.getImpOrderNo() + ",ops_po_invoice_price_detail_log_from_supplier表id:" + item.getId());
                            faultItem++;
                            continue;
                        }
                        barcodeFromSupplierDO = barcodeFromSupplierDtos.get(0); // 默认取第一条
                        if (barcodeFromSupplierDtos.size() > 1) {
                            for (OpsPoDeliveryInvoiceBarcodeFromSupplierDto barcode : barcodeFromSupplierDtos) {
                                // 校验当前barcode是否已经使用过
                                if (!barcodeHashMap.containsKey(barcode.getBarcode())) {
                                    barcodeFromSupplierDO = barcode; // 跳出循环
                                    break;
                                }
                            }
                        }
                        // 开始进行barcode取数匹配
                        if (PublicUtil.isEmpty(barcodeFromSupplierDO) || PublicUtil.isEmpty(barcodeFromSupplierDO.getBarcode())) {
                            String billNo = commonServiceFeignApi.generatorBillNo("20").getData();
                            detailDO.setBarcode(billNo);
                        } else {
                            detailDO.setBarcode(barcodeFromSupplierDO.getBarcode());
                            detailDO.setCaseNo(barcodeFromSupplierDO.getCaseNo()); // barcode表 caseNo
                            detailDO.setSnCode(barcodeFromSupplierDO.getSnCode()); // barcode表 caseNo
                            barcodeHashMap.put(barcodeFromSupplierDO.getBarcode(), barcodeFromSupplierDO.getId()); // 记录已经使用的barcode
                        }
                         // barcode数据存在时，则不导入
                        int barcodeCount = getImpInvoiceDetailByBarCode(detailDO.getBarcode());
                        if (barcodeCount > 0) {
                            log.error("生成barcode单号已经存在" + ",ops_po_invoice_price_detail_log_from_supplier表id:" + item.getId());
                            faultItem++;
                            continue;
                        }
                        detailDO.setModelNo(item.getModelNo());
                        detailDO.setImpModelNo(item.getModelNo());
                        detailDO.setQuantity(item.getQuantity());
                        detailDO.setPrice(item.getUnitPrice());
                        // amount为空时，计算合计金额
                        detailDO.setAmount(Optional.ofNullable(item.getAmount()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) == 0 ? BigDecimalUtil.mul(item.getQuantity(), item.getUnitPrice()) : item.getAmount());
                        if (item.getPono().length() < 10) {
                            log.error("改单号长度小于10位，请确认" + "订单PO号" + item.getPono() + ",ops_po_invoice_price_detail_log_from_supplier表id:" + item.getId());
                            faultItem++;
                            continue;
                        }
                        detailDO.setImpOrderNo(barcodeFromSupplierDO.getImpOrderNo());
                        orderNoInfo = new OrderNoInfo().convertPOOrder(item.getPono(), item.getLineItem().toString()); // 根据pono,lineitem 解析订单号
                        detailDO.setOrderNo(orderNoInfo.getOrderNo());
                        detailDO.setItemNo(orderNoInfo.getItemNo());
                        detailDO.setSplitItemNo(orderNoInfo.getSplitItem());
                        detailDO.setPono(orderNoInfo.getPoNo());
                        detailDO.setPoitemno(orderNoInfo.getPoItemNo());
                        detailDO.setFullOrderNo(orderNoInfo.getFullOrderNo());

                        detailDO.setWeight(item.getWeight());
                        detailDO.setSupplierCode(invoiceMasterDto.getSupplierCode());
                        detailDO.setShipDate(barcodeFromSupplierDO != null ? barcodeFromSupplierDO.getShipTime() : opsPoDeliveryInvoiceLog.getShipDate());
                        detailDO.setCurrency(invoiceMasterDto.getCurrency());
                        detailDO.setOverseaInvoiceNo(invoiceMasterDto.getInvoiceNo());
                        detailDO.setStatus((short) 1);
                        detailDO.setRemark(ImpInvoiceCommonEnum.GZIMP.getCodeName());
                        detailDO.setUpdateTime(new Date());
                        if (detailDO.getId() == null) {
                            detailDO.setCreateUser(ImpInvoiceCommonEnum.GZIMP.getCode());
                            detailDO.setCreateTime(new Date());
                            impInvoiceDetailMapper.insertSelective(detailDO);
                        } else {
                            detailDO.setUpdateUser(ImpInvoiceCommonEnum.GZIMP.getCode());
                            impInvoiceDetailMapper.updateByPrimaryKeySelective(detailDO);
                        }
                        successItem++;
                        impInvoiceDetailModel = BeanCopyUtil.copy(detailDO, OpsImpInvoiceDetailVO.class);
                        listItemVO.add(impInvoiceDetailModel);
                    }
                    updateImpInvoiceQty(invoiceMasterDto.getId(),invoiceMasterDto);
                    //复制发票数据到分包数据里面
                    copyToInvoicedetailPack(invoiceMasterDto.getId());
                    // 加入发票导入后事件的全局开关，控制导入IMP表的后续操作
                    if (invoiceFollowSwitch) {
                        toProcessInvoice(invoiceMasterDto, listItemVO);
                    }
                    sbMsg.append(invoiceNo + "增加项数：" + successItem + ",存在项数：" + faultItem).append(";");
                } catch (Exception e) {
                    log.error("广州制造导入ImpInvoice失败：" + e.getMessage());
                    log.error(invoiceNo);
                    sbMsg.append(invoiceNo)
                            .append("导入箱单失败：" + e.getMessage());
                    // todo 是否加入异常处理表
                    impInvoiceCommonService.insertPoFailLog("ImpInvoiceSysn导入错误-" + codeType, opsPoDeliveryInvoiceLog.getId().toString(), "ops_po_delivery_invoice_log_from_supplier", e.getMessage(), codeType + ":导入ImpInvoice表报错");
                }
            }
            return ResultVo.success("广州制造发票明细导入完毕.", sbMsg.toString());
        } finally {
            redissonUtil.unlock(ImpInvoiceConstants.REDIS_KEY_IMP_GP_INVOICE);
        }
    }

    /**
     * @param manulist 1.数据源是中国制造
     *                 2.转换后 写入表 imp_invoice_master、Imp_invoice_detail、Imp_invoice_detail_pack
     *                 3. 三方的只写L, 自报的写L & P1
     */
    public ResultVo<String> insertManuData(List<OpsPoInvoiceDataDto> manulist, String codeType, Boolean invoiceFollowSwitch) {
        if (!redissonUtil.tryLock(ImpInvoiceConstants.REDIS_KEY_IMP_CN_INVOICE, 1, 1200)) {
            return ResultVo.failure("新impinvoice中国制造导入中,请稍后重试");
        }
        try {
            StringBuilder sbMsg = new StringBuilder();
            ImpInvoiceMaster invoiceMasterDto;
            List<ImpInvoiceDetail> impInvoiceDetails;
            ImpInvoiceDetail invoiceDetail;
            OpsPoInvoicePriceLogFromSupplierDto poInvoicePriceLogFromSupplierDO; // 价格主表
            List<OpsPoInvoicePriceDetailLogFromSupplierDto> poInvoicePriceDetailList; // 价格子表
            List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> barcodeFromSupplierDOList; // barcode明细表
            Map<String, String> supplierNameMap = impInvoiceCommonService.getSuppilyNameMapper();
            // 现状，L和P 都只写一条数据，不需要再进行合并了
            for (OpsPoInvoiceDataDto manuInvoiceDataDto : manulist) {
                OpsPoDeliveryInvoiceLogFromSupplierDto deliveryInvoiceLog = manuInvoiceDataDto.getOpsPoDeliveryInvoiceLogFromSupplierDto();
                barcodeFromSupplierDOList = manuInvoiceDataDto.getOpsPoDeliveryInvoiceBarcodeFromSupplierDtos();
                if (deliveryInvoiceLog == null) {
                    log.error("发票数据导入中国制造，适配器中间表有数据为空，无法写入");
                    continue;
                }
                String invoiceNo = deliveryInvoiceLog.getInvoiceNo(); // 发票号
                invoiceMasterDto = new ImpInvoiceMaster();
                String receiveWarehouseCode;
                boolean isNewMaster = true;
                int successItem = 0;
                int faultItem = 0;
                List<OpsImpInvoiceDetailVO> listItemVO = new ArrayList<>();
                // 供应商转换
                ResultVo<String> resultVo = impInvoiceCommonService.getOpsSupplierByConfig(deliveryInvoiceLog.getIssuerCode(),"supplierid"); // 2024-05-09根据适配器供应商转换为OPS的供应商
                if (!resultVo.isSuccess()) {
                    impInvoiceCommonService.insertPoFailLog("ImpInvoiceSysn导入供应商转换失败-" + codeType, String.valueOf(deliveryInvoiceLog.getId()), "ops_po_delivery_invoice_log_from_supplier", resultVo.getMessage(), codeType + ":导入ImpInvoice表报错");
                    continue;
                }
                String supplierCode = resultVo.getData();
                try {
                    // 中国和广州制造新增
                    invoiceMasterDto.setSupplierCode(supplierCode);
                    // transtype转换
                    if (StringUtils.isNotBlank(barcodeFromSupplierDOList.get(0).getTranstypeCode())) {
                        // bug 15251 WTSR2024001000-发单前运输方式修改增加【Courier快递】选项
                        ResultVo<String> transTypeResultVo = impInvoiceCommonService.getOpsSupplierByConfig(barcodeFromSupplierDOList.get(0).getTranstypeCode(),"transtype"); // 2024-05-09根据适配器供应商转换为OPS的供应商
                        if (!transTypeResultVo.isSuccess()) {
                            log.error("新impInvoice导入transtype转换失败：{}", transTypeResultVo.getMessage());
                            continue;
                        }
                        invoiceMasterDto.setTransType(transTypeResultVo.getData());
//                        invoiceMasterDto.setTransType(ImpGpsTranstypeEnum.getOpsCodeByGps(barcodeFromSupplierDOList.get(0).getTranstypeCode()));
                    }
                    invoiceMasterDto.setShipDate(DateUtil.getDate(deliveryInvoiceLog.getShipDate())); // DateUtil.getDate(oneItem.getExecuteTime()) 是否需要截取到 年月日
                    invoiceMasterDto.setInvoiceDate(DateUtil.getDate(deliveryInvoiceLog.getInvoiceDate())); // DateUtil.getDate(oneItem.getExecuteTime()) 是否需要截取到 年月日
                    invoiceMasterDto.setShipment(supplierNameMap.getOrDefault(invoiceMasterDto.getSupplierCode(), ""));  // 供应商名称转换
                    invoiceMasterDto.setInvoiceNo(deliveryInvoiceLog.getInvoiceNo());
                    invoiceMasterDto.setPrearriveDate(DateUtil.getToday()); // 是否需要截取到 年月日
                    invoiceMasterDto.setRemark(ImpInvoiceCommonEnum.CNIMP.getCodeName() + deliveryInvoiceLog.getSmccode());
                    invoiceMasterDto.setCurrency(ImpInvoiceCommonEnum.CNY.getCode());
                    invoiceMasterDto.setExchangeRate(new BigDecimal("1"));
                    invoiceMasterDto.setUpdateTime(new Date());
                    invoiceMasterDto.setUpdateUser(ImpInvoiceCommonEnum.CNIMP.getCode());
                    // 校验主表中是否存在
                    ImpInvoiceMaster exitMasterDO = getExistImpInvoiceMasterByInvoiceNo(invoiceMasterDto.getSupplierCode(),
                            invoiceMasterDto.getInvoiceNo(), invoiceMasterDto.getShipDate());
                    if (exitMasterDO != null) {
                        if (exitMasterDO.getStatus() == 3 || exitMasterDO.getStatus() == 6 || exitMasterDO.getStatus() == 9) {
                            log.error("当前master表状态不可再重新导入" + ",deliveryInvoiceLog表id:" + deliveryInvoiceLog.getId());
                            continue;
                        }
                        invoiceMasterDto.setId(exitMasterDO.getId());
                        isNewMaster = false;
                    }
                    // todo 这段代码适配器中已经实现，是否可以不用判断了
//                if ("TZ".equalsIgnoreCase(invoiceMasterDto.getSupplierCode())) { //根据smccode 获取仓库代码
//                    receiveWarehouseCode = impInvoiceCommonService.getWarehouseCodeBySMCCode(deliveryInvoiceLog.getSmccode(), 3);
//                } else {
//                    receiveWarehouseCode = impInvoiceCommonService.getWarehouseCodeBySMCCode(deliveryInvoiceLog.getSmccode(), 2);
//                }
                    // 收货仓的判断
                    receiveWarehouseCode = barcodeFromSupplierDOList.get(0).getReceiveWarehouseId();
                    // 写入子项表
                    if (StringUtils.isBlank(receiveWarehouseCode)) { // 仓库代码为空时，根据订单号的获取仓库代码
                        receiveWarehouseCode = impInvoiceSysnDao.getReceiveWarehouseCode(barcodeFromSupplierDOList.get(0).getPono().trim());
                    }
                    if (StringUtils.isNotBlank(receiveWarehouseCode) && receiveWarehouseCode.startsWith("CN0-")) {
                        receiveWarehouseCode = receiveWarehouseCode.substring(4); // "CN0-".length() == 3
                    }
                    invoiceMasterDto.setReceiveWarehouseCode(receiveWarehouseCode);
                    // 校验是否为三方，如果是三方，只存在L数据，导入master表和pack表
                    if (deliveryInvoiceLog.getIsGw().equals(1)) {
                        //                invoiceMasterDto.setBoxQty(barcodeFromSupplierDOList.size()); // 箱数汇总
//                invoiceMasterDto.setWeight(barcodeFromSupplierDOList.stream().map(OpsPoDeliveryInvoiceBarcodeFromSupplierDO::getNetWeight).reduce(BigDecimal.ZERO, BigDecimal::add)); //重量求和
//                invoiceMasterDto.setOrderQty((int) barcodeFromSupplierDOList.stream().map(OpsPoDeliveryInvoiceBarcodeFromSupplierDO::getPono).distinct().count()); //不同订单的数量
//                invoiceMasterDto.setTotalQty(barcodeFromSupplierDOList.stream().mapToInt(OpsPoDeliveryInvoiceBarcodeFromSupplierDO::getQuantity).sum()); // 总数量

                        // 写入状态信息
                        if (isNewMaster) {
                            invoiceMasterDto.setStatus(IImpInvoiceMasterStatusEnum.PREARRIVAL.getType());
                        }
                        invoiceMasterDto.setInvoiceType(InvoiceTypeAdapterEnum.TRIPARTITE.getCode());
                        invoiceMasterDto.setDataType(2);

                        //3.保存到导入发票主表
                        if (invoiceMasterDto.getId() == null) {
                            invoiceMasterDto.setCreateUser(invoiceMasterDto.getUpdateUser());
                            invoiceMasterDto.setCreateTime(new Date());
                            String billNo = commonServiceFeignApi.generatorBillNo("21").getData();
                            if (PublicUtil.isEmpty(billNo)) {
                                log.error("生成单号异常" + ",deliveryInvoiceLog表id:" + deliveryInvoiceLog.getId());
                                continue;
                            }
                            invoiceMasterDto.setId(Long.parseLong(billNo));
                            impInvoiceMasterMapper.insertSelective(invoiceMasterDto);
                        } else {
                            impInvoiceMasterMapper.updateByPrimaryKeySelective(invoiceMasterDto);
                        }
                        // 需要先写入master表，获取主表id信息
                        ImpInvoiceDetailPack detailDO;
                        OrderNoInfo orderNoInfo;
                        for (OpsPoDeliveryInvoiceBarcodeFromSupplierDto item : barcodeFromSupplierDOList) {
                            detailDO = new ImpInvoiceDetailPack();
                            detailDO.setInvoiceNo(invoiceMasterDto.getInvoiceNo());
                            detailDO.setInvoiceId(invoiceMasterDto.getId());
                            if (PublicUtil.isEmpty(item.getBarcode())) {
                                String billNo = commonServiceFeignApi.generatorBillNo("20").getData();
                                detailDO.setBarcode(billNo);
                            } else {
                                detailDO.setBarcode(item.getBarcode());
                            }
                            int barcodeCount = getImpInvoiceDetailPackByBarCode(detailDO.getBarcode());  // barcode数据存在时，则不导入
                            if (barcodeCount > 0) {
                                log.error("生成barcode单号已经存在" + ",ops_po_invoice_price_detail_log_from_supplier表id:" + item.getId());
                                faultItem++;
                                continue;
                            }
                            detailDO.setCaseNo(item.getCaseNo());
                            detailDO.setSnCode(item.getSnCode());
                            detailDO.setModelNo(item.getModelNo());
                            detailDO.setImpModelNo(item.getModelNo());
                            detailDO.setQuantity(item.getQuantity());
//                    detailDO.setPrice(item.getprice());  // todo barcode表中无 价格数据？？如何获取价格数据,pack表无法获取价格数据，从detail表获取
                            if (item.getImpOrderNo().length() < 10) {
                                log.error("改单号长度小于10位，请确认" + "订单PO号" + item.getImpOrderNo() + ",ops_po_invoice_price_detail_log_from_supplier表id:" + item.getId());
                                faultItem++;
                                continue;
                            }
                            detailDO.setImpOrderNo(item.getImpOrderNo()); // todo,是否可以存储 原始单号字段，由适配器自己解析，或者新增 ImpOrderNo、OrderNo、ItemNo、SplitItemNo、PoNo、PoItemNo、FullOrderNo
                            orderNoInfo = new OrderNoInfo().convertJPOrder(item.getPono(), item.getLineItem().toString()); // 根据pono,lineitem 解析订单号
                            detailDO.setOrderNo(orderNoInfo.getOrderNo());
                            detailDO.setItemNo(orderNoInfo.getItemNo());
                            detailDO.setSplitItemNo(orderNoInfo.getSplitItem());
                            detailDO.setPono(orderNoInfo.getPoNo());
                            detailDO.setPoitemno(orderNoInfo.getPoItemNo());
                            detailDO.setFullOrderNo(orderNoInfo.getFullOrderNo());
                            detailDO.setWeight(item.getNetWeight());
                            detailDO.setSupplierCode(invoiceMasterDto.getSupplierCode());  // 读取主表的suppilyCode? todo suppilyCode字段保存到子表了
                            detailDO.setShipDate(item.getShipTime());
                            detailDO.setCurrency(invoiceMasterDto.getCurrency());
                            detailDO.setOverseaInvoiceNo(invoiceMasterDto.getInvoiceNo()); // todo 三方的订单无法读取价格子表，barcode表也需要新增 invoice_original字段
                            detailDO.setStatus((short) 1);
                            detailDO.setRemark(ImpInvoiceCommonEnum.CNIMP.getCodeName());
                            detailDO.setUpdateTime(new Date());
                            detailDO.setCreateUser(ImpInvoiceCommonEnum.CNIMP.getCode());
                            detailDO.setCreateTime(new Date());
                            impInvoiceDetailPackMapper.insertSelective(detailDO);
                            successItem++;
                            OpsImpInvoiceDetailVO detailVO = BeanCopyUtil.copy(detailDO, OpsImpInvoiceDetailVO.class);
                            listItemVO.add(detailVO);
                        }
                        updateImpInvoiceQty(invoiceMasterDto.getId(),invoiceMasterDto); // 更新总数字段
                    } else {
                        poInvoicePriceLogFromSupplierDO = manuInvoiceDataDto.getOpsPoInvoicePriceLogFromSupplierDto();
                        poInvoicePriceDetailList = manuInvoiceDataDto.getOpsPoInvoicePriceDetailLogFromSupplierDtos();
                        if (CollectionUtils.isEmpty(poInvoicePriceDetailList)) {
                            log.error("未找到对应价格子项，无法写入" + ",deliveryInvoiceLog表id:" + poInvoicePriceLogFromSupplierDO.getId());
                            continue;
                        }
                        // 写入价格信息
                        invoiceMasterDto.setTotalQty(poInvoicePriceLogFromSupplierDO.getTotalQty());
                        invoiceMasterDto.setOrderQty((int) poInvoicePriceDetailList.stream().map(OpsPoInvoicePriceDetailLogFromSupplierDto::getPono).distinct().count()); //统计不同订单数量
                        invoiceMasterDto.setBoxQty(poInvoicePriceLogFromSupplierDO.getBoxQty());
                        invoiceMasterDto.setWeight(poInvoicePriceLogFromSupplierDO.getWeight());
                        invoiceMasterDto.setAmount(poInvoicePriceLogFromSupplierDO.getAmount());
                        invoiceMasterDto.setCurrency(poInvoicePriceLogFromSupplierDO.getCurrency());
                        invoiceMasterDto.setExchangeRate(poInvoicePriceLogFromSupplierDO.getExchangeRate());
                        // 写入状态信息
                        invoiceMasterDto.setStatus(IImpInvoiceMasterStatusEnum.BEIMP.getType());
                        invoiceMasterDto.setInvoiceType(InvoiceTypeAdapterEnum.CM.getCode());
                        //3.保存到导入发票主表
                        if (invoiceMasterDto.getId() == null) {
                            invoiceMasterDto.setDataType(1);
                            invoiceMasterDto.setCreateUser(invoiceMasterDto.getUpdateUser());
                            invoiceMasterDto.setCreateTime(new Date());
                            String billNo = commonServiceFeignApi.generatorBillNo("21").getData();
                            if (PublicUtil.isEmpty(billNo)) {
                                log.error("生成单号异常" + ",deliveryInvoiceLog表id:" + deliveryInvoiceLog.getId());
                                continue;
                            }
                            invoiceMasterDto.setId(Long.parseLong(billNo));
                            impInvoiceMasterMapper.insertSelective(invoiceMasterDto);
                        } else {
                            impInvoiceMasterMapper.updateByPrimaryKeySelective(invoiceMasterDto);
                        }
                        // 需要先写入master表，获取主表id信息
                        List<ImpInvoiceDetail> invoiceDetails = new ArrayList<>(poInvoicePriceDetailList.size());
                        ImpInvoiceDetail detailDO;
                        OpsPoDeliveryInvoiceBarcodeFromSupplierDto barcodeFromSupplierDO;
                        OrderNoInfo orderNoInfo;
                        String username;
                        HashMap<String, Long> barcodeHashMap = new HashMap<String, Long>();
                        for (OpsPoInvoicePriceDetailLogFromSupplierDto item : poInvoicePriceDetailList) {
                            detailDO = new ImpInvoiceDetail();
                            detailDO.setInvoiceNo(invoiceMasterDto.getInvoiceNo());
                            detailDO.setInvoiceId(invoiceMasterDto.getId());
                            // 读取barcode表信息
                            // 由于中国制造没有fromid的信息，所以通过订单号、型号、行号、数量、重量筛选可能会出现多条记录，记录已经使用的barcode，使用过后取下一条循环判断
//                            List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> barcodeFromSupplierDtos = barcodeFromSupplierDOList.stream().filter(barcode -> barcode.getPono().equals(item.getPono())
//                                    && barcode.getModelNo().equals(item.getModelNo())
//                                    && barcode.getLineItem().equals(item.getLineItem()) && barcode.getQuantity().equals(item.getQuantity())
//                                    && barcode.getNetWeight().equals(item.getWeight())).collect(Collectors.toList()); // 筛选barcode表信息
                            // bug18428
                            List<OpsPoDeliveryInvoiceBarcodeFromSupplierDto> barcodeFromSupplierDtos = barcodeFromSupplierDOList.stream()
                                    .filter(barcode -> Objects.equals(barcode.getPono(), item.getPono())
                                            && Objects.equals(barcode.getModelNo(), item.getModelNo())
                                            && Objects.equals(barcode.getLineItem(), item.getLineItem())
                                            && Objects.equals(barcode.getQuantity(), item.getQuantity())
                                            && Objects.equals(barcode.getNetWeight(), item.getWeight())).collect(Collectors.toList());
                            if (CollectionUtils.isEmpty(barcodeFromSupplierDtos)) {
                                log.error("中国制造impinvoice导入错误，未找到对应的barcode信息，订单号：" + item.getImpOrderNo() + ",ops_po_invoice_price_detail_log_from_supplier表id:" + item.getId());
                                faultItem++;
                                continue;
                            }
                            barcodeFromSupplierDO = barcodeFromSupplierDtos.get(0); // 默认取第一条
                            if (barcodeFromSupplierDtos.size() > 1) {
                                for (OpsPoDeliveryInvoiceBarcodeFromSupplierDto barcode : barcodeFromSupplierDtos) {
                                    // 校验当前barcode是否已经使用过
                                    if (!barcodeHashMap.containsKey(barcode.getBarcode())) {
                                        barcodeFromSupplierDO = barcode;
                                        break;
                                    }
                                }
                            }
                            if (PublicUtil.isEmpty(barcodeFromSupplierDO) || PublicUtil.isEmpty(barcodeFromSupplierDO.getBarcode())) {
                                String billNo = commonServiceFeignApi.generatorBillNo("20").getData();
                                detailDO.setBarcode(billNo);
                            } else {
                                detailDO.setBarcode(barcodeFromSupplierDO.getBarcode());
                                detailDO.setCaseNo(barcodeFromSupplierDO.getCaseNo()); // barcode表 caseNo
                                detailDO.setSnCode(barcodeFromSupplierDO.getSnCode()); // barcode表 caseNo
                                barcodeHashMap.put(barcodeFromSupplierDO.getBarcode(), barcodeFromSupplierDO.getId()); // 记录已经使用的barcode
                            }
                            // barcode数据存在时，则不导入
                            int barcodeCount = getImpInvoiceDetailByBarCode(detailDO.getBarcode());
                            if (barcodeCount > 0) {
                                log.error("生成barcode单号已经存在" + ",ops_po_invoice_price_detail_log_from_supplier表id:" + item.getId());
                                faultItem++;
                                continue;
                            }
                            detailDO.setModelNo(item.getModelNo());
                            detailDO.setImpModelNo(item.getModelNo());
                            detailDO.setQuantity(item.getQuantity());
                            detailDO.setPrice(item.getUnitPrice());
                            // amount为空时，计算合计金额
                            detailDO.setAmount(Optional.ofNullable(item.getAmount()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) == 0 ? BigDecimalUtil.mul(item.getQuantity(), item.getUnitPrice()) : item.getAmount());
                            // TODO 原始订单号的校验
                            if (item.getImpOrderNo().length() < 10) {
                                log.error("改单号长度小于10位，请确认" + "订单PO号" + item.getImpOrderNo() + ",ops_po_invoice_price_detail_log_from_supplier表id:" + item.getId());
                                faultItem++;
                                continue;
                            }
                            detailDO.setImpOrderNo(item.getImpOrderNo());
                            orderNoInfo = new OrderNoInfo().convertJPOrder(item.getPono(), item.getLineItem().toString()); // 根据pono,lineitem 解析订单号
                            detailDO.setOrderNo(orderNoInfo.getOrderNo());
                            detailDO.setItemNo(orderNoInfo.getItemNo());
                            detailDO.setSplitItemNo(orderNoInfo.getSplitItem());
                            detailDO.setPono(orderNoInfo.getPoNo());
                            detailDO.setPoitemno(orderNoInfo.getPoItemNo());
                            detailDO.setFullOrderNo(orderNoInfo.getFullOrderNo());
                            detailDO.setWeight(item.getWeight());
                            detailDO.setSupplierCode(invoiceMasterDto.getSupplierCode());  // 读取主表的suppilyCode?
                            detailDO.setShipDate(barcodeFromSupplierDO != null ? barcodeFromSupplierDO.getShipTime() : deliveryInvoiceLog.getShipDate());
                            detailDO.setCurrency(invoiceMasterDto.getCurrency());
                            detailDO.setOverseaInvoiceNo(item.getInvoiceOriginal()); // todo 读取原始发票号
                            detailDO.setStatus((short) 1);
                            detailDO.setRemark(ImpInvoiceCommonEnum.CNIMP.getCodeName());
                            detailDO.setUpdateTime(new Date());
                            detailDO.setCreateUser(ImpInvoiceCommonEnum.CNIMP.getCode());
                            detailDO.setCreateTime(new Date());
                            impInvoiceDetailMapper.insertSelective(detailDO);
                            successItem++;
                            OpsImpInvoiceDetailVO detailVO = BeanCopyUtil.copy(detailDO, OpsImpInvoiceDetailVO.class);
                            listItemVO.add(detailVO);
                        }
                        updateImpInvoiceQty(invoiceMasterDto.getId(),invoiceMasterDto); // 更新总数字段
                        //复制发票数据到分包数据里面
                        copyToInvoicedetailPack(invoiceMasterDto.getId());
                    }
                    // 加入发票导入后事件的全局开关，控制导入IMP表的后续操作
                    if (invoiceFollowSwitch) {
                        toProcessInvoice(invoiceMasterDto, listItemVO);
                    }
                    // 统计项数
                    sbMsg.append(invoiceNo + "增加项数：" + successItem + ",存在项数：" + faultItem).append(";");
                } catch (Exception e) {
                    log.error("中国制造导入发票失败，发票号：key" + e.getMessage());
                    sbMsg.append(invoiceNo + ",")
                            .append("导入箱单失败：" + e.getMessage());
                    impInvoiceCommonService.insertPoFailLog("ImpInvoiceSysn导入错误-" + codeType, String.valueOf(deliveryInvoiceLog.getId()), "ops_po_invoice_price_detail_log_from_supplier", e.getMessage(), codeType + ":导入ImpInvoice表报错");
                }
            }
            return ResultVo.success("中国制造发票明细导入完毕.", sbMsg.toString());
        }  finally {
            redissonUtil.unlock(ImpInvoiceConstants.REDIS_KEY_IMP_CN_INVOICE);
        }
    }

    /**
     * 关务的返回
     * 1.写入表 imp_invoice_master、Imp_invoice_detail_pack
     * 2. 关务只存在价格数据PO&P1,不存在箱单数据
     */
    public ResultVo<String> insertGWData(List<OpsPoInvoiceDataDto> gwList, String codeType, Boolean invoiceFollowSwitch) {
        if (!redissonUtil.tryLock(ImpInvoiceConstants.REDIS_KEY_IMP_GW_INVOICE, 1, 1200)) {
            return ResultVo.failure("新impinvoice,关务发票正在导入中，请稍后重试");
        }
        try {
            StringBuilder sbMsg = new StringBuilder();
            /*List<ImpInvoiceDetail> impInvoiceDetails;
            ImpInvoiceDetail invoiceDetail;
            Map<String, String> supplierNameMap = impInvoiceCommonService.getSuppilyNameMapper();*/
            OpsPoInvoicePriceLogFromSupplierDto poInvoicePriceLogFromSupplierDO;
            OpsPoDeliveryInvoiceLogFromSupplierDto deliveryInvoiceLog; //总表
            List<OpsPoInvoicePriceDetailLogFromSupplierDto> poInvoicePriceDetailList; // 价格子表
            for (OpsPoInvoiceDataDto gwOpsPoInvoiceDataDto : gwList) {
                try {
                    deliveryInvoiceLog = gwOpsPoInvoiceDataDto.getOpsPoDeliveryInvoiceLogFromSupplierDto();
                    poInvoicePriceLogFromSupplierDO = gwOpsPoInvoiceDataDto.getOpsPoInvoicePriceLogFromSupplierDto();
                    poInvoicePriceDetailList = gwOpsPoInvoiceDataDto.getOpsPoInvoicePriceDetailLogFromSupplierDtos();
                    if (CollectionUtils.isEmpty(poInvoicePriceDetailList) || poInvoicePriceLogFromSupplierDO == null || deliveryInvoiceLog == null) {
                        log.error("关务发票数据导入，适配器中间表有数据为空，无法写入" + ",deliveryInvoiceLog表总表id:" + poInvoicePriceLogFromSupplierDO.getId());
                        //记录异常表中
                        impInvoiceCommonService.insertPoFailLog("关务发票数据导入，适配器中间表有数据为空，无法写入:" + codeType, String.valueOf(deliveryInvoiceLog.getId()), "ops_po_delivery_invoice_log_from_supplier", "主表或明细表数据为空,请稍后重试", codeType + ":导入ImpInvoice表报错");
                        continue;
                    }
                    // 关务导入发票，供应商代码的转换，需要配置在对应的数据中
                    if (StringUtils.isNotBlank(deliveryInvoiceLog.getIssuerCode()) && deliveryInvoiceLog.getIssuerCode().length()>2) {
                        ResultVo<String> resultVo = impInvoiceCommonService.getOpsSupplierByConfig(deliveryInvoiceLog.getIssuerCode(), "supplierid"); // 2024-05-09根据适配器供应商转换为OPS的供应商
                        if (!resultVo.isSuccess()) {
                            impInvoiceCommonService.insertPoFailLog("ImpInvoiceSysn导入错误-" + codeType, String.valueOf(deliveryInvoiceLog.getId()), "ops_po_invoice_price_detail_log_from_supplier", resultVo.getMessage(), codeType + ":导入ImpInvoice表报错,供应商代码转换错误!");
                            continue;
                        }
                        deliveryInvoiceLog.setIssuerCode(resultVo.getData());
                    }
                    //start  bugid 19186 处理合并smcCode判断
                    Map<String,String> receiveWarehouseMap = new HashMap<>();
                    Map<String, List<OpsPoInvoicePriceDetailLogFromSupplierDto>> stringListMap = splitInvoiceByPrice(deliveryInvoiceLog, poInvoicePriceDetailList,receiveWarehouseMap);
                    if(Objects.nonNull(stringListMap)){
                        Iterator<String> iterator = stringListMap.keySet().iterator();
                        // 原始发票号 原始发票Id
                        String oriInvoiceNo = deliveryInvoiceLog.getInvoiceNo();
                        String billNo = commonServiceFeignApi.generatorBillNo("23").getData();
                        if (PublicUtil.isEmpty(billNo)) {
                            log.error( "addSplitInvoiceTable:获取原始发票ID失败" );
                        }
                        while (iterator.hasNext()){
                            String splitInvoiceNo = iterator.next();
                            // 拆分发票号
                            deliveryInvoiceLog.setInvoiceNo(splitInvoiceNo);

                            List<OpsPoInvoicePriceDetailLogFromSupplierDto> priceDetailList = stringListMap.get(splitInvoiceNo);
                            deliveryInvoiceLog.setSplitInvoiceId(null);
                            poInvoicePriceLogFromSupplierDO.setInvoiceNo(splitInvoiceNo);
                            // 仅拆分数量
                            poInvoicePriceLogFromSupplierDO.setTotalQty( priceDetailList.stream()
                                    .map(OpsPoInvoicePriceDetailLogFromSupplierDto::getQuantity)
                                    .filter(Objects::nonNull) // 排除 null
                                    .mapToInt(Integer::intValue)
                                    .sum());
                            // 写入数据
                            insertGWDataDetail(deliveryInvoiceLog,poInvoicePriceLogFromSupplierDO
                                    ,priceDetailList,gwOpsPoInvoiceDataDto,codeType,invoiceFollowSwitch,sbMsg,poInvoicePriceDetailList,receiveWarehouseMap);
                            // 写入拆分发票关联表
                            if(Objects.nonNull(deliveryInvoiceLog.getSplitInvoiceId())){
                                addSplitInvoiceTable(oriInvoiceNo,splitInvoiceNo,deliveryInvoiceLog.getSplitInvoiceId(),billNo);
                            }

                        }
                        // end
                    }else {
                        insertGWDataDetail(deliveryInvoiceLog,poInvoicePriceLogFromSupplierDO
                                ,poInvoicePriceDetailList,gwOpsPoInvoiceDataDto,codeType,invoiceFollowSwitch,sbMsg,null,receiveWarehouseMap);
                    }

                } catch (Exception e) {
                    log.error("关务接口导入发票失败，发票号：" + gwOpsPoInvoiceDataDto.getOpsPoDeliveryInvoiceLogFromSupplierDto().getInvoiceNo() + "错误信息：" + e.getMessage());
                    impInvoiceCommonService.insertPoFailLog("ImpInvoiceSysn导入错误-" + codeType, String.valueOf(gwOpsPoInvoiceDataDto.getDeliveryInvoiceId()), "ops_po_delivery_invoice_log_from_supplier", e.getMessage(), codeType + ":导入ImpInvoice表报错");
                }
            }
            return ResultVo.success("关务接口发票明细导入完毕.", sbMsg.toString());
        } finally {
            redissonUtil.unlock(ImpInvoiceConstants.REDIS_KEY_IMP_GW_INVOICE);
        }
    }

    public void insertGWDataDetail(OpsPoDeliveryInvoiceLogFromSupplierDto deliveryInvoiceLog
            ,OpsPoInvoicePriceLogFromSupplierDto poInvoicePriceLogFromSupplierDO
            ,List<OpsPoInvoicePriceDetailLogFromSupplierDto> poInvoicePriceDetailList
            ,OpsPoInvoiceDataDto gwOpsPoInvoiceDataDto, String codeType, Boolean invoiceFollowSwitch
            ,StringBuilder sbMsg,List<OpsPoInvoicePriceDetailLogFromSupplierDto> totalPriceDetailList,Map<String,String> receiveWarehouseMap) throws OpsException{
        String supplierCode = deliveryInvoiceLog.getIssuerCode();
        ImpInvoiceMaster invoiceMasterDto = new ImpInvoiceMaster();
        String receiveWarehouseCode = null; // 出库仓的转换
        boolean isNewInvoice = true;
        Date now = new Date();
        invoiceMasterDto.setInvoiceNo(deliveryInvoiceLog.getInvoiceNo());
        invoiceMasterDto.setCinvoiceNo(deliveryInvoiceLog.getGwInvoiceNo());
        invoiceMasterDto.setShipDate(deliveryInvoiceLog.getShipDate() == null ? deliveryInvoiceLog.getInvoiceDate() : deliveryInvoiceLog.getShipDate());
        invoiceMasterDto.setPrearriveDate(deliveryInvoiceLog.getEstArrivalTime());
        invoiceMasterDto.setInvoiceDate(deliveryInvoiceLog.getInvoiceDate());

        invoiceMasterDto.setSupplierCode(supplierCode);
        invoiceMasterDto.setInvoiceType(impInvoiceCommonService.invoiceTypeConvert(invoiceMasterDto.getSupplierCode(), "GW")); // 关务发票类型转换
        // 临时加 解决三方的关务发票号没有加年月
        if (invoiceMasterDto.getInvoiceType() != null && invoiceMasterDto.getInvoiceNo().length() <= 5
                && invoiceMasterDto.getInvoiceType().compareTo(InvoiceTypeAdapterEnum.TRIPARTITE.getCode()) == 0
                && invoiceMasterDto.getShipDate().compareTo(DateUtil.stringToDate("2022-10-01")) < 0) {
            invoiceMasterDto.setInvoiceNo(deliveryInvoiceLog.getGwInvoiceNo());
        }
        ImpInvoiceMaster exitMasterDO = null;
        // bugid 19186 处理合并smcCode判断
        if(CollectionUtils.isEmpty(totalPriceDetailList)){
            exitMasterDO = getExistImpInvoiceMasterByGWInvoiceNo(invoiceMasterDto.getCinvoiceNo());
        }
        if (exitMasterDO == null) {
            exitMasterDO = getExistImpInvoiceMasterByInvoiceNo(invoiceMasterDto.getSupplierCode(), invoiceMasterDto.getInvoiceNo(), invoiceMasterDto.getShipDate());
        }
        log.info("GWData:" + JSON.toJSONString(invoiceMasterDto));
        boolean isSimpleUpd = false; // 判断是否已经发票入库
        if (exitMasterDO != null) {
            log.info("GWexist:" + JSON.toJSONString(exitMasterDO));
            if ("6".equals(exitMasterDO.getGwStatecode())) {
//                        return ResultVo.failure("发票已经成本入库不能重新导入" + invoiceMasterDto.getInvoiceNo());
                log.error("发票已经成本入库不能重新导入" + invoiceMasterDto.getInvoiceNo() + ",deliveryInvoiceLog表id:" + deliveryInvoiceLog.getId());
                return;
            }
            if (exitMasterDO.getStatus().equals(IImpInvoiceMasterStatusEnum.IMPED.getType())
                    || exitMasterDO.getStatus().equals(IImpInvoiceMasterStatusEnum.COSTED.getType())) {
                isSimpleUpd = true;
            }
            invoiceMasterDto.setId(exitMasterDO.getId());
            if (exitMasterDO.getDataType() == null) {
                invoiceMasterDto.setDataType(1);
            } else {
                invoiceMasterDto.setDataType(exitMasterDO.getDataType() | 1);
            }
            isNewInvoice = false;
        } else {
            if (StringUtils.isBlank(invoiceMasterDto.getSupplierCode())) {  // 关务的供应商代码为空，则不新增导入
//                        return ResultVo.failure("供应商代码为空不导入" + masterDO.getInvoiceNo() + " ," + masterDO.getCInvoiceNo());
                log.error("供应商代码为空不导入" + invoiceMasterDto.getInvoiceNo() + " ," + invoiceMasterDto.getCinvoiceNo() + ",deliveryInvoiceLog表id:" + deliveryInvoiceLog.getId());
                return;
            }
            invoiceMasterDto.setDataType(1);
        }
        invoiceMasterDto.setStatus(impInvoiceCommonService.getGWMasterStatus(deliveryInvoiceLog.getGwInvoiceStatus())); //设置主表的状态
        invoiceMasterDto.setUpdateTime(now);
        invoiceMasterDto.setUpdateUser(ImpInvoiceCommonEnum.GWIMP.getCode());

        // 写入价格信息
        invoiceMasterDto.setExchangeRate(poInvoicePriceLogFromSupplierDO.getExchangeRate());
        invoiceMasterDto.setTotalQty(poInvoicePriceLogFromSupplierDO.getTotalQty());
        invoiceMasterDto.setOrderQty((int) poInvoicePriceDetailList.stream().map(OpsPoInvoicePriceDetailLogFromSupplierDto::getPono).distinct().count()); //统计不同订单数量
        if(!CollectionUtils.isEmpty(totalPriceDetailList)){
            invoiceMasterDto.setOrderQty((int) totalPriceDetailList.stream().map(OpsPoInvoicePriceDetailLogFromSupplierDto::getPono).distinct().count()); //统计不同订单数量
        }

        invoiceMasterDto.setBoxQty(poInvoicePriceLogFromSupplierDO.getBoxQty());// todo 子表无
        invoiceMasterDto.setWeight(poInvoicePriceLogFromSupplierDO.getWeight());
        invoiceMasterDto.setAmount(poInvoicePriceLogFromSupplierDO.getAmount());
        invoiceMasterDto.setGrossWeight(poInvoicePriceLogFromSupplierDO.getGrossWeight()); // todo 子表无
        invoiceMasterDto.setCustomsFee(poInvoicePriceLogFromSupplierDO.getCustomsFee());
        invoiceMasterDto.setVatFee(poInvoicePriceLogFromSupplierDO.getVatFee());
        invoiceMasterDto.setTransFee(poInvoicePriceLogFromSupplierDO.getTransFee());
        invoiceMasterDto.setAmountRmb(BigDecimalUtil.mul(invoiceMasterDto.getAmount(), invoiceMasterDto.getExchangeRate()));
        //  transtype转换
        if (StringUtils.isNotBlank(poInvoicePriceLogFromSupplierDO.getTransType())) {
            // bug 15251 WTSR2024001000-发单前运输方式修改增加【Courier快递】选项
            ResultVo<String> transTypeResultVo = impInvoiceCommonService.getOpsSupplierByConfig(poInvoicePriceLogFromSupplierDO.getTransType(),"transtype"); // 2024-05-09根据适配器供应商转换为OPS的供应商
            if (!transTypeResultVo.isSuccess()) {
                log.error("新impInvoice导入transtype转换失败：{}", transTypeResultVo.getMessage());
                return;
            }
            invoiceMasterDto.setTransType(transTypeResultVo.getData());
//                        invoiceMasterDto.setTransType(ImpGpsTranstypeEnum.getOpsCodeByGps(poInvoicePriceLogFromSupplierDO.getTransType()));
        }
        invoiceMasterDto.setShipment(poInvoicePriceLogFromSupplierDO.getShipment() + poInvoicePriceLogFromSupplierDO.getImportCustomer());
        invoiceMasterDto.setPlantmark(poInvoicePriceLogFromSupplierDO.getPlantMark());
        invoiceMasterDto.setWeight(poInvoicePriceLogFromSupplierDO.getWeight());
        invoiceMasterDto.setCurrency(poInvoicePriceLogFromSupplierDO.getCurrency());
        invoiceMasterDto.setBargainType(poInvoicePriceLogFromSupplierDO.getBargainType());
        invoiceMasterDto.setExciseTax(poInvoicePriceLogFromSupplierDO.getExciseTax());
        invoiceMasterDto.setGwStatecode(poInvoicePriceLogFromSupplierDO.getGwStatusCode());
        StringBuffer remark = new StringBuffer();
        remark.append(Optional.ofNullable(poInvoicePriceLogFromSupplierDO.getPaymentTerm()).orElse("")).append(" ")
                .append(Optional.ofNullable(poInvoicePriceLogFromSupplierDO.getSmccode()).orElse("")).append(" ")
                .append(Optional.ofNullable(poInvoicePriceLogFromSupplierDO.getShipment()).orElse("")).append(" ")
                .append(Optional.ofNullable(poInvoicePriceLogFromSupplierDO.getImportCustomer()).orElse(""));

        invoiceMasterDto.setRemark(remark.toString());
        invoiceMasterDto.setReceiveDate(deliveryInvoiceLog.getActArrivalTime());
        // 收货仓为拆分发票的最终目的仓  bugid 19186 处理合并smcCode判断
        if(receiveWarehouseMap.containsKey(invoiceMasterDto.getInvoiceNo())){
            receiveWarehouseCode = receiveWarehouseMap.get(invoiceMasterDto.getInvoiceNo());
        }
        if (StringUtils.isBlank(receiveWarehouseCode)){
            // 根据smccode转仓库代码,三方的发票，北京仓入库
            if (invoiceMasterDto.getInvoiceType() != null && invoiceMasterDto.getInvoiceType().compareTo(InvoiceTypeAdapterEnum.TRIPARTITE.getCode()) == 0) {
                receiveWarehouseCode = impInvoiceCommonService.getWarehouseCodeBySMCCode(poInvoicePriceLogFromSupplierDO.getSmccode(), 2);
            } else {
                receiveWarehouseCode = impInvoiceCommonService.getWarehouseCodeBySMCCode(poInvoicePriceLogFromSupplierDO.getSmccode(), 1);
            }
        }

        if (StringUtils.isBlank(receiveWarehouseCode)) {
            // bug 13606 优化采购pono解析成orderno
            OrderNoInfo masterorderNoInfo = purchaseConvertDeliveryService.convertPoNoFormPurchase(
                    poInvoicePriceDetailList.get(0).getPono(), String.valueOf(poInvoicePriceDetailList.get(0).getLineItem()));
            receiveWarehouseCode = impInvoiceSysnDao.getReceiveWarehouseCode(masterorderNoInfo.getPoNo());
            if (StringUtils.isNotBlank(receiveWarehouseCode)) {
                invoiceMasterDto.setReceiveWarehouseCode(receiveWarehouseCode);
            }
        } else {
            invoiceMasterDto.setReceiveWarehouseCode(receiveWarehouseCode);
        }
        if (StringUtils.isNotBlank(poInvoicePriceLogFromSupplierDO.getPaymentTerm()) && poInvoicePriceLogFromSupplierDO.getPaymentTerm().contains("天")) {
            String paymentTerm = poInvoicePriceLogFromSupplierDO.getPaymentTerm().substring(0, poInvoicePriceLogFromSupplierDO.getPaymentTerm().indexOf("天")).trim();
            invoiceMasterDto.setPayDay(Integer.parseInt(paymentTerm));
        }
        invoiceMasterDto.setCustomsDate(deliveryInvoiceLog.getCustomsDate());
        invoiceMasterDto.setDeclarationNo(deliveryInvoiceLog.getCustomsDeclarationNo());
        if ("1".equalsIgnoreCase(invoiceMasterDto.getGwStatecode()) || "2".equalsIgnoreCase(invoiceMasterDto.getGwStatecode())) {
            invoiceMasterDto.setStatus(2);// 已报关可以发票入库
        }
        // bug 14223 需要增加对到港时间赋值
        if (deliveryInvoiceLog.getArrivalPortDate()!= null) {
            invoiceMasterDto.setPortArrivedate(deliveryInvoiceLog.getArrivalPortDate());
        }
        // <!--add by WuWeiDong 20240304 bug 12279 更新无偿品发票--> 判断SMCCode是否为无商业价值类型 或paymenterm是无偿 if(true) { invoiceType=8, status=7
        if (impInvoiceCommonService.isNoCommercialValue(poInvoicePriceLogFromSupplierDO.getSmccode())
                || Optional.ofNullable(poInvoicePriceLogFromSupplierDO.getPaymentTerm()).orElse("").contains("无偿")) {
            invoiceMasterDto.setInvoiceType(InvoiceTypeAdapterEnum.Free.getCode());
            invoiceMasterDto.setStatus(IImpInvoiceMasterStatusEnum.PACK.getType());
            if (!Optional.ofNullable(poInvoicePriceLogFromSupplierDO.getPaymentTerm()).orElse("").contains("无偿")) {
                invoiceMasterDto.setRemark(Optional.ofNullable(invoiceMasterDto.getRemark()).orElse("") + "无商业价值");
            }
        }
        if (invoiceMasterDto.getId() == null) {
            invoiceMasterDto.setCreateTime(invoiceMasterDto.getUpdateTime());
            invoiceMasterDto.setCreateUser(invoiceMasterDto.getUpdateUser());
            String billNo = commonServiceFeignApi.generatorBillNo("21").getData();
            if (PublicUtil.isEmpty(billNo)) {
                log.error("生成单号异常" + ",deliveryInvoiceLog表id:" + deliveryInvoiceLog.getId());
                return;
            }
            invoiceMasterDto.setId(Long.parseLong(billNo));
            deliveryInvoiceLog.setSplitInvoiceId(invoiceMasterDto.getId());
            impInvoiceMasterMapper.insertSelective(invoiceMasterDto);
        } else {
            // 其他在途不能再更改
            if (exitMasterDO.getStatus() != IImpInvoiceMasterStatusEnum.PREARRIVAL.getType() && exitMasterDO.getStatus() != IImpInvoiceMasterStatusEnum.DELETED.getType()) {
                invoiceMasterDto.setStatus(exitMasterDO.getStatus());
            }
            // 已发票入库不更新仓库
            if (isSimpleUpd) {
                // 如果已经发票入库了，重新导入只更改：金额、费用和关务状态这几个字段 Bug-8785
                exitMasterDO.setAmount(invoiceMasterDto.getAmount());
                exitMasterDO.setAmountRmb(invoiceMasterDto.getAmountRmb());
                exitMasterDO.setCustomsFee(invoiceMasterDto.getCustomsFee());
                exitMasterDO.setVatFee(invoiceMasterDto.getVatFee());
                exitMasterDO.setTransFee(invoiceMasterDto.getTransFee());
                exitMasterDO.setOtherFee(invoiceMasterDto.getOtherFee());
                exitMasterDO.setExciseTax(invoiceMasterDto.getExciseTax());
                exitMasterDO.setGwStatecode(invoiceMasterDto.getGwStatecode());
                // bug14513 已发票入库需要追加BargainType的更新
                exitMasterDO.setBargainType(invoiceMasterDto.getBargainType());
                // bug19004 预计到货日更新需求WTSR2025000995,增加预计到货日更新，增加更新“到厂日期”字段
//                            exitMasterDO.setPrearriveDate(invoiceMasterDto.getPrearriveDate());
                exitMasterDO.setReceiveDate(invoiceMasterDto.getReceiveDate());
                impInvoiceMasterMapper.updateByPrimaryKeySelective(exitMasterDO);
            } else {
                impInvoiceMasterMapper.updateByPrimaryKeySelective(invoiceMasterDto);
            }
        }

        List<OpsImpInvoiceDetailVO> listItemVO = new ArrayList<>();
        OpsImpInvoiceDetailVO impInvoiceDetailModel;
        ImpInvoiceDetail detailDO;
        ImpInvoiceDetail existDetailDO;
        OrderNoInfo orderNoInfo;
        boolean hasUpdateDetail = false;
        int successItem = 0;
        int faultItem = 0;
        for (OpsPoInvoicePriceDetailLogFromSupplierDto item : poInvoicePriceDetailList) {
            detailDO = new ImpInvoiceDetail();
            detailDO.setInvoiceId(invoiceMasterDto.getId());
            detailDO.setFromId(item.getFromId());
            detailDO.setPrice(item.getUnitPrice());
            detailDO.setCustomsFee(item.getCustomsFee());
            detailDO.setExciseTax(item.getExciseTax());
            detailDO.setOtherFee(item.getOtherTax());
            detailDO.setTransFee(item.getTrafficInsurance());
            detailDO.setVatFee(item.getVatFee());
            if (!isNewInvoice) {
                existDetailDO = getImpInvoiceDetailByFromId(invoiceMasterDto.getId(), detailDO.getFromId());
                if (existDetailDO != null) {
                    detailDO.setId(existDetailDO.getId());
                }
                if (isSimpleUpd) {
                    if (detailDO.getId() != null) {
                        detailDO.setUpdateUser(ImpInvoiceCommonEnum.GWIMP.getCode());
                        impInvoiceDetailMapper.updateByPrimaryKeySelective(detailDO);
                        continue;
                    }
                }
            }
            detailDO.setInvoiceNo(invoiceMasterDto.getInvoiceNo());
            detailDO.setShipDate(invoiceMasterDto.getShipDate());
            detailDO.setShipMethod(invoiceMasterDto.getTransType());
            detailDO.setSupplierCode(invoiceMasterDto.getSupplierCode());
            detailDO.setCurrency(invoiceMasterDto.getCurrency());
            orderNoInfo = new OrderNoInfo();
            /**orderNoInfo = new OrderNoInfo().convertPOOrder(item.getPono(), String.valueOf(item.getLineItem()));*/
            try {
                orderNoInfo = purchaseConvertDeliveryService.convertPoNoFormPurchase(item.getPono(), String.valueOf(item.getLineItem()));
            } catch (Exception e) {
                log.error("关务接口导入发票失败，发票号：" + gwOpsPoInvoiceDataDto.getOpsPoDeliveryInvoiceLogFromSupplierDto().getInvoiceNo() + "错误信息：" + e.getMessage());
                impInvoiceCommonService.insertPoFailLog("Imp子项订单号解析错误-" + codeType, String.valueOf(gwOpsPoInvoiceDataDto.getDeliveryInvoiceId()), "ops_po_delivery_invoice_log_from_supplier", e.getMessage(), codeType + ":导入ImpInvoice表报错");
                return;
            }
            detailDO.setOrderNo(orderNoInfo.getOrderNo());
            if (orderNoInfo.getOrderNo().length() > 20) {
                detailDO.setOrderNo(orderNoInfo.getOrderNo().substring(0, 20));
            }
            detailDO.setItemNo(orderNoInfo.getItemNo());
            detailDO.setImpOrderNo(orderNoInfo.getOrFullOrderNo());
            detailDO.setFullOrderNo(orderNoInfo.getFullOrderNo());
            detailDO.setSplitItemNo(orderNoInfo.getSplitItem());
            detailDO.setPono(orderNoInfo.getPoNo());
            detailDO.setPoitemno(orderNoInfo.getPoItemNo());
            detailDO.setOrderType(orderNoInfo.getOrderType() != null ? orderNoInfo.getOrderType().toString() : null);
            detailDO.setStatus((short) 1);
            detailDO.setModelNo(item.getModelNo());
            detailDO.setQuantity(item.getQuantity());
            detailDO.setEnName(item.getDescriptionCustoms());
            detailDO.setWeight(item.getWeight());
            detailDO.setOrigin(impInvoiceCommonService.getSupplierCodeFromJPWhereCode(item.getCountryOrigin()));
            detailDO.setImpModelNo(item.getModelNo());
            detailDO.setNoncommercial(item.getNonCommercial());
            if (item.getNonCommercial() != null && "*".equalsIgnoreCase(item.getNonCommercial())) {
                detailDO.setNoncommercial("1");
            }
            detailDO.setAmount(item.getAmount());
            detailDO.setOverseaInvoiceNo(item.getInvoiceOriginal());
            if (PublicUtil.isNotEmpty(detailDO.getOverseaInvoiceNo())
                    && (detailDO.getOverseaInvoiceNo().startsWith("DR000")
                    || "*".equals(detailDO.getOverseaInvoiceNo()))) {
                detailDO.setNoncommercial("1");
                detailDO.setStatus((short) 7);
            }
            if (detailDO.getId() == null) {
                detailDO.setCreateUser(invoiceMasterDto.getUpdateUser());
                detailDO.setCreateTime(invoiceMasterDto.getUpdateTime());
                impInvoiceDetailMapper.insertSelective(detailDO);
            } else {
                hasUpdateDetail = true;
                detailDO.setUpdateUser(invoiceMasterDto.getUpdateUser());
                detailDO.setUpdateTime(invoiceMasterDto.getUpdateTime());
                impInvoiceDetailMapper.updateByPrimaryKeySelective(detailDO);
            }
            impInvoiceDetailModel = BeanCopyUtil.copy(detailDO, OpsImpInvoiceDetailVO.class);
            listItemVO.add(impInvoiceDetailModel);
            // 日本的需要更新pack表中的price字段信息
            if ("JP".equalsIgnoreCase(invoiceMasterDto.getSupplierCode())) {
                updateShifPricePack(detailDO);
            }
        }
        // 重复导入关务发票状态为【预计到货|已到货待入库】时,明细可能重复录入,需删除之前录入的明细
        if (!hasUpdateDetail && (IImpInvoiceMasterStatusEnum.PREARRIVAL.getType().equals(invoiceMasterDto.getStatus())
                || IImpInvoiceMasterStatusEnum.BEIMP.getType().equals(invoiceMasterDto.getStatus()))) {
            ImpInvoiceDetailExample detailExample = new ImpInvoiceDetailExample();
            detailExample.createCriteria().andInvoiceIdEqualTo(invoiceMasterDto.getId()).andUpdateTimeLessThan(now);
            impInvoiceDetailMapper.deleteByExample(detailExample);
//                    LambdaQueryWrapper<ImpInvoiceDetailDto> detailQuery = Wrappers.lambdaQuery();
//                    detailQuery.eq(ImpInvoiceDetailDto::getInvoiceId, invoiceMasterDto.getId()).lt(ImpInvoiceDetailDto::getUpdateTime,
//                            now);
//                    impInvoiceDetailMapper.delete(detailQuery);
        }
        // 三国的默认复制发票数据到分包数据里面
        if (PublicUtil.isNotEmpty(invoiceMasterDto.getSupplierCode())
                && !ImpInvoiceCommonEnum.NOTCOPYDETAILTOPACK.getCode().contains(invoiceMasterDto.getSupplierCode())) {
            copyToInvoicedetailPack(invoiceMasterDto.getId());
        }
        // 加入发票导入后事件的全局开关，控制导入IMP表的后续操作
        if (invoiceFollowSwitch) {
            // 3.检查发票差异
            checkImpInvoiceError(invoiceMasterDto.getId());
            sbMsg.append(deliveryInvoiceLog.getInvoiceNo() + "增加项数：" + poInvoicePriceDetailList.size()).append(";");
            // 4.更新采购在途数量 把数据传递采购结果和更新订单状态
            if (invoiceMasterDto.getInvoiceType() != null && invoiceMasterDto.getInvoiceType().compareTo(InvoiceTypeAdapterEnum.CM.getCode()) != 0) {
                toUpdatePOTransStatus(listItemVO, invoiceMasterDto);
            }
            // 更新订单状态，已经放到新交付中实现，此处不需要再调用
            // 5.更新订单状态
            if (invoiceMasterDto.getStatus() == 1 || invoiceMasterDto.getStatus() == 2) {
                toUpdateOrderState(invoiceMasterDto, listItemVO);
            }
            // 6.如果三方发票，发送发票入库消息
            // todo bug14838 三国的发票 也需要追加自动入库逻辑
            if (invoiceMasterDto.getInvoiceType() != null && invoiceMasterDto.getInvoiceType().compareTo(InvoiceTypeAdapterEnum.TRIPARTITE.getCode()) == 0) {
                // 发送发票入库消息
                // 发送发票入库消息
                ImpInvoiceProcessDTO processDTO = new ImpInvoiceProcessDTO();
                processDTO.setInvoiceId(invoiceMasterDto.getId());
                processDTO.setProcessType(3);
                this.sendInvoiceProcessMsgToMQ(processDTO);
            }
        }
    }


    /**
     * 按供应商的发票号查询是否存在相同的发票
     */
//    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ImpInvoiceMaster getExistImpInvoiceMasterByInvoiceNo(String supplierCode, String invoiceNo, Date shipDate) {
        if (shipDate == null) {
            shipDate = DateUtil.getToday();
        }
        Date fromDate = DateUtil.addDay(shipDate, -60); // 限制时间60天内
//        ImpInvoiceMasterTempExample example = new ImpInvoiceMasterTempExample();
//        ImpInvoiceMasterTempExample.Criteria invoiceMasterCriteria = example.createCriteria();
//        invoiceMasterCriteria.andInvoiceNoEqualTo(invoiceNo).andStatusNotEqualTo(9);
//        if (StringUtils.isNotBlank(supplierCode)) {
//            invoiceMasterCriteria.andSupplierCodeEqualTo(supplierCode);
//        }
//        // 创建子查询条件
//        ImpInvoiceMasterTempExample.Criteria subCriteria = example.createCriteria();
//        subCriteria.andShipDateGreaterThanOrEqualTo(fromDate);
//        ImpInvoiceMasterTempExample.Criteria subCriteria2 = example.createCriteria();
//        subCriteria2.andCreateTimeGreaterThanOrEqualTo(fromDate);
//        // 使用 OR 条件正确地组合两个子查询
//        // 查询符合条件的请购单
//        List<ImpInvoiceMaster> list = impInvoiceMasterMapper.selectByExample(example);
        List<ImpInvoiceMaster> list = impInvoiceSysnDao.selectExitMaster(invoiceNo, "9", fromDate, supplierCode);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    /**
     * 按供应商的发票号查询 barcode子表中是否存在
     *
     * @param invoiceId
     * @param fullOrderNo
     * @param barcode
     * @return
     */
//    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ImpInvoiceDetailPack getImpInvoiceDetailPack(Long invoiceId, String fullOrderNo, String barcode) {
        ImpInvoiceDetailPackExample example = new ImpInvoiceDetailPackExample();
        ImpInvoiceDetailPackExample.Criteria invoiceCriteria = example.createCriteria();
        invoiceCriteria.andInvoiceIdEqualTo(invoiceId).andBarcodeEqualTo(barcode).andFullOrderNoEqualTo(fullOrderNo);
        List<ImpInvoiceDetailPack> list = impInvoiceDetailPackMapper.selectByExample(example);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 供应商为日本，根据关务导入的发票，来更新日本的pack表的price字段
     */
    public void updateShifPricePack(ImpInvoiceDetail impInvoiceDetailTemp) {
        ImpInvoiceDetailPackExample example = new ImpInvoiceDetailPackExample();
        ImpInvoiceDetailPackExample.Criteria invoiceCriteria = example.createCriteria();
        invoiceCriteria.andInvoiceIdEqualTo(impInvoiceDetailTemp.getInvoiceId()).andFullOrderNoEqualTo(impInvoiceDetailTemp.getFullOrderNo())
                .andModelNoEqualTo(impInvoiceDetailTemp.getModelNo()).andQuantityEqualTo(impInvoiceDetailTemp.getQuantity()).andPriceIsNull();
        List<ImpInvoiceDetailPack> list = impInvoiceDetailPackMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            log.info("更新pack表价格，impInvoiceDetailTemp:" + impInvoiceDetailTemp);
            List<Long> idList = list.stream().map(a -> a.getId()).collect(Collectors.toList());
            ImpInvoiceDetailPackExample example2 = new ImpInvoiceDetailPackExample();
            example2.createCriteria().andIdIn(idList);
            ImpInvoiceDetailPack updatePack = new ImpInvoiceDetailPack();
            updatePack.setPrice(impInvoiceDetailTemp.getPrice());
            updatePack.setUpdateTime(new Date());
            impInvoiceDetailPackMapper.updateByExampleSelective(updatePack, example2);
        }
    }

    //    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Boolean addImpInvoiceDetailPack(ImpInvoiceDetailPack impInvoiceDetailPackDO) {
        if (impInvoiceDetailPackDO == null) {
            return false;
        }
        if (impInvoiceDetailPackDO.getBarcode() == null || "".equals(impInvoiceDetailPackDO.getBarcode())) {
            ResultVo<String> stringResultVo = commonServiceFeignApi.generatorBillNo("20");
            if (!stringResultVo.isSuccess() || stringResultVo.getData() == null) {
                return false;
            }
            impInvoiceDetailPackDO.setBarcode(stringResultVo.getData());
        }
        if (PublicUtil.isEmpty(impInvoiceDetailPackDO.getCaseNo())) {
            impInvoiceDetailPackDO.setCaseNo("000");
        }
        if (impInvoiceDetailPackDO.getWeight() == null || impInvoiceDetailPackDO.getWeight().compareTo(BigDecimal.ZERO) == 0) {   // 重量为空或者0时，查product_physics表获取型号的重量
            ProductPhysics productPhysics = impInvoiceSysnDao.getProductPhysicsByModelNo(impInvoiceDetailPackDO.getModelNo());
            if (productPhysics != null) {
                impInvoiceDetailPackDO.setWeight(BigDecimal.valueOf(Optional.ofNullable(productPhysics.getWeight()).orElse(BigDecimal.ZERO).doubleValue()));
            }
        }
        try {
            impInvoiceDetailPackDO.setCreateTime(new Date());
            impInvoiceDetailPackMapper.insertSelective(impInvoiceDetailPackDO);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Integer getImpInvoiceDetailByBarCode(String barcode) {
        ImpInvoiceDetailExample example = new ImpInvoiceDetailExample();
        ImpInvoiceDetailExample.Criteria detailCriteria = example.createCriteria();
        detailCriteria.andBarcodeEqualTo(barcode);
        return Math.toIntExact(impInvoiceDetailMapper.countByExample(example));
//        QueryWrapper<ImpInvoiceDetailDto> detailQuery = new QueryWrapper<>();
//        detailQuery.eq("barcode", barcode);
//        return impInvoiceDetailMapper.selectCount(detailQuery);
    }

    private Integer getImpInvoiceDetailPackByBarCode(String barcode) {
        ImpInvoiceDetailPackExample example = new ImpInvoiceDetailPackExample();
        ImpInvoiceDetailPackExample.Criteria detailCriteria = example.createCriteria();
        detailCriteria.andBarcodeEqualTo(barcode);
        return Math.toIntExact(impInvoiceDetailPackMapper.countByExample(example));
//        QueryWrapper<ImpInvoiceDetailPackDto> detailPackQuery = new QueryWrapper<>();
//        detailPackQuery.eq("barcode", barcode);
//        return impInvoiceDetailPackMapper.selectCount(detailPackQuery);
    }

    //    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ImpInvoiceMaster getExistImpInvoiceMasterByGWInvoiceNo(String gwCinvoiceNo) {
        ImpInvoiceMasterExample example = new ImpInvoiceMasterExample();
        ImpInvoiceMasterExample.Criteria masterCriteria = example.createCriteria();
        masterCriteria.andCinvoiceNoEqualTo(gwCinvoiceNo);
        List<ImpInvoiceMaster> list = impInvoiceMasterMapper.selectByExample(example);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
    }


    //    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ImpInvoiceDetail getImpInvoiceDetailByFromId(Long invoiceId, Long fromid) {
        ImpInvoiceDetailExample example = new ImpInvoiceDetailExample();
        ImpInvoiceDetailExample.Criteria detailCriteria = example.createCriteria();
        detailCriteria.andInvoiceIdEqualTo(invoiceId).andFromIdEqualTo(fromid);
        List<ImpInvoiceDetail> list = impInvoiceDetailMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> copyToInvoicedetailPack(Long invoiceId) {
        try {
            ImpInvoiceMaster inoviceMasterDO = impInvoiceMasterMapper.selectByPrimaryKey(invoiceId);
            if (inoviceMasterDO == null) {
                return ResultVo.failure("该发票不存在" + invoiceId);
            }
            if (!(inoviceMasterDO.getStatus() == IImpInvoiceMasterStatusEnum.PREARRIVAL.getType() || inoviceMasterDO.getStatus() == IImpInvoiceMasterStatusEnum.BEIMP.getType() || inoviceMasterDO.getStatus() == IImpInvoiceMasterStatusEnum.EDIT.getType())) {
                return ResultVo.failure("当前状态不可以复制分包明细" + inoviceMasterDO.getInvoiceNo());
            }

            //查找所有明细
            ImpInvoiceDetailExample example = new ImpInvoiceDetailExample();
            example.createCriteria().andInvoiceIdEqualTo(invoiceId);
            example.setOrderByClause("full_order_no asc");
            List<ImpInvoiceDetail> details = impInvoiceDetailMapper.selectByExample(example);
            int count = 0;
//            LambdaQueryWrapper<ImpInvoiceDetailPack> queryPackWrapper = new LambdaQueryWrapper<>();
            ImpInvoiceDetailPackExample packExample = new ImpInvoiceDetailPackExample();
            String fullOrderNo = "";
            ImpInvoiceDetailPack packDO;
            for (ImpInvoiceDetail info : details) {
                //重复完整订单号第一次判断不存在的都加进去，第一次存在都不加
                packExample.clear();
                if (StringUtils.isBlank(info.getBarcode())) {
                    if (!fullOrderNo.equals(info.getFullOrderNo())) {
                        packExample.createCriteria().andInvoiceIdEqualTo(info.getInvoiceId()).andPonoEqualTo(info.getPono()).andPoitemnoEqualTo(info.getPoitemno()).andFullOrderNoEqualTo(info.getFullOrderNo());
                        //判断分包数据中是否存在该订单数据，存在则跳过
                        if (impInvoiceDetailPackMapper.countByExample(packExample) > 0) {
                            continue;
                        }
                        fullOrderNo = info.getFullOrderNo();
                    }
                } else {
                    packExample.createCriteria().andInvoiceIdEqualTo(info.getInvoiceId()).andBarcodeEqualTo(info.getBarcode());
                    if (impInvoiceDetailPackMapper.countByExample(packExample) > 0) {
                        continue;
                    }
                }
                //不存在则复制到分包数据中
                packDO = new ImpInvoiceDetailPack();
                packDO.setInvoiceId(info.getInvoiceId());
                packDO.setOrderNo(info.getOrderNo());
                packDO.setItemNo(info.getItemNo());
                packDO.setPono(info.getPono());
                packDO.setPoitemno(info.getPoitemno());
                packDO.setSplitItemNo(info.getSplitItemNo());
                packDO.setFullOrderNo(info.getFullOrderNo());
                packDO.setImpOrderNo(String.format("%s-%s", info.getOrderNo(), info.getItemNo()));
                packDO.setCaseNo(info.getCaseNo());
                packDO.setBarcode(info.getBarcode());
                packDO.setCurrency(info.getCurrency());
                packDO.setEnName(info.getEnName());
                //来源代码
                packDO.setFromCode(info.getFromCode());
                packDO.setInvoiceNo(info.getInvoiceNo());
                packDO.setModelNo(info.getModelNo());
                packDO.setOrderType(info.getOrderType());
                packDO.setOrigin(info.getOrigin());
                packDO.setPrice(info.getPrice());
                packDO.setProductCode(info.getProductCode());
                packDO.setQuantity(info.getQuantity());
                packDO.setRemark("fromINV");
                packDO.setShipDate(info.getShipDate());
                packDO.setShipMethod(info.getShipMethod());
                packDO.setStatus(info.getStatus());
                packDO.setSupplierCode(info.getSupplierCode());
                packDO.setWeight(info.getWeight());
                packDO.setShelfCode(info.getShelfCode());
                packDO.setRohsCode(info.getRohsCode());
                packDO.setSnCode(info.getSnCode());
                //packDO.setCreateUser(SMCApp.getLoginAuthDto().getUserNo());
                packDO.setCreateUser("fromInv");
                Boolean aBoolean = addImpInvoiceDetailPack(packDO);
                if (!aBoolean) {
                    return ResultVo.failure();
                }
                count++;
            }
            if (count > 0) {
                ImpInvoiceMaster updatemaster = new ImpInvoiceMaster();
                updatemaster.setId(invoiceId);
                updatemaster.setDataType(3);
                impInvoiceMasterMapper.updateByPrimaryKeySelective(updatemaster);
            }
            return ResultVo.success(String.valueOf(count), "复制到分包数据成功！" + count);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
//    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<Integer> checkImpInvoiceError(Long invoiceId) {
        try {
//            LambdaQueryWrapper<ImpInvoiceMasterDto> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.select(ImpInvoiceMasterDto::getId, ImpInvoiceMasterDto::getStatus);
//            queryWrapper.eq(ImpInvoiceMasterDto::getId, invoiceId);
//            ImpInvoiceMasterDto masterDO = impInvoiceMasterMapper.selectOne(queryWrapper);
            ImpInvoiceMasterExample example = new ImpInvoiceMasterExample();
            ImpInvoiceMasterExample.Criteria masterCriteria = example.createCriteria();
            masterCriteria.andIdEqualTo(invoiceId);
            List<ImpInvoiceMaster> masterTempList = impInvoiceMasterMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(masterTempList)) {
                return ResultVo.failure("未找到该发票");
            }
            ImpInvoiceMaster masterDO = masterTempList.get(0);
            Map<String, Object> params;
            Object countObj;
            if (masterDO.getStatus() == 1 || masterDO.getStatus() == 2 || masterDO.getStatus() == 10) {
                Integer count = 0;
                params = new HashMap<>(4);
                params.put("invoiceId", invoiceId);
                params.put("Count", 0);
                impInvoiceErrorDao.checkImpInvoiceError(params);
                countObj = params.get("Count");
                if (countObj != null) {
                    count = Integer.valueOf(countObj.toString());
                }
                return ResultVo.success(count);
            }
            return ResultVo.failure("状态错误，请检查");
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * 调用采购接口生成，预到货数据
     *
     * @param invoiceDetails
     * @param master
     */
    private void toUpdatePOTransStatus(List<OpsImpInvoiceDetailVO> invoiceDetails, ImpInvoiceMaster master) {
        // todo 需要配置新的开关，来控制新旧程序启动
        ResultVo<DataTypeVO> newDataType = dictDataServiceFeignApi.getDataTypeCodesInfo("6004", "3");
        if (newDataType != null && !"1".equals(newDataType.getData().getExtNote1())) {
            log.info("新交付系统,发票预到货开关已关闭请在旧程序重试,{}", master.getInvoiceNo());
            return;
        }

        if (master.getPrearriveDate() == null) {
            log.info("不生成发票预到货，预到货日期为空,{}", master.getInvoiceNo());
            return;
        }
        // add by A78027 20221130 bug 10303
        if (PublicUtil.isEmpty(master.getSupplierCode())) {
            log.info("不生成发票预到货，供应商为空,{}", master.getInvoiceNo());
            return;
        }
        // 是否启动发票预到货,1启动，其它不启动 bug8530
        ResultVo<DataTypeVO> dataTypeResultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "9");
        if (dataTypeResultVo != null && !"1".equals(dataTypeResultVo.getData().getExtNote1())) {
            log.info("启动发票预到货已关闭,{}", master.getInvoiceNo());
            return;
        }

        // end bug8530
        Map<String, PurchaseReplyInfo> poMaps = new HashMap<>(invoiceDetails.size());
        PurchaseReplyInfo info;
        String key;
        Date impdate = new Date();
        for (OpsImpInvoiceDetailVO orderVo : invoiceDetails) {
            key = orderVo.getPono() + "~" + orderVo.getPoitemno();
            if (poMaps.containsKey(key)) {
                info = poMaps.get(key);
                info.setQtyTrans(orderVo.getQuantity() + info.getQtyTrans());
            } else {
                info = new PurchaseReplyInfo();
                // 改成3个订单号
                info.setOrderno(orderVo.getOrderNo());
                info.setItemno(orderVo.getItemNo());
                info.setSplitno(orderVo.getSplitItemNo());
                info.setPono(orderVo.getPono());
                info.setLineitem(orderVo.getPoitemno());
                info.setTranstype(master.getTransType());
                info.setImpdate(master.getPrearriveDate());
                info.setInvoiceno(orderVo.getInvoiceNo()); // 原始发票号
                info.setInvoiceid(orderVo.getInvoiceId());
                info.setModelno(orderVo.getModelNo());
                // bug13550 使用主表供应商
                info.setSupplierid(master.getSupplierCode());
                info.setQtyTrans(orderVo.getQuantity()); // 运输中数量
                info.setCustomsdate(master.getCustomsDate()); // todo 2024-5-20现状是有 报关时间的传输，是否增加报关单号的赋值
                info.setPortarrivedate(master.getPortArrivedate());
                info.setDeclarationNo(master.getDeclarationNo()); // bug14223 交付系统关务发票导入时，报关单号字段的赋值
                // 19307bugid：排除日本供应商
                //info.setDeliverytimeAFact(master.getShipDate()); // bug15074 【采购交付】采购交付的fact表中字段没有根据适配器中最新数据进行更新
                if(StringUtils.isNotBlank(master.getSupplierCode()) && !"JP".equals(master.getSupplierCode())){
                    info.setDeliverytimeAFact(master.getShipDate());
                }
                info.setReceiveDate(master.getReceiveDate());//bugid:15666   WTSR2024000564
                poMaps.put(key, info);
            }
        }
        log.info("新impinvoice开始调用BJ发票预到货接口数据");
        log.info(JSON.toJSONString(poMaps.values()));
        try {
            // 初始系统不执行
            CommonResult<String> updateInvoice = wmPurchaseFeignApi.updateInvoice(new ArrayList<>(poMaps.values()));
            if (!updateInvoice.isSuccess()) {
                log.error("新impinvoice调用BJ发票预到货接口失败: {} {}", master.getInvoiceNo(), updateInvoice.getMessage());
            }
        } catch (Exception e) {
            log.error("新impinvoice调用BJ发票预到货接口失败updateInvoice：" + e.getMessage());
            impInvoiceCommonService.insertPoFailLog("发票号："+master.getInvoiceNo()+",新impinvoice调用发票预到货接口失败updateInvoice：", String.valueOf(master.getId()), "impInvoiceMaster", e.getMessage(), "新impinvoice调用BJ发票预到货接口失败updateInvoice");
        }
    }

    /**
     * 2024-05-16 需要发送入库消息到MQ中
     *
     * @param dto
     * @return
     */
    private boolean sendInvoiceProcessMsgToMQ(ImpInvoiceProcessDTO dto) {
        // todo 需要配置新的开关，来控制新旧程序启动
        ResultVo<DataTypeVO> newDataType = dictDataServiceFeignApi.getDataTypeCodesInfo("6004", "2");
        if (newDataType != null && !"1".equals(newDataType.getData().getExtNote1())) {
            log.info("新交付系统,发送入库消息开关已关闭请在旧程序重试");
            return false;
        }
        RabbitMqMessage mqMsg = new RabbitMqMessage();
        mqMsg.setContent(JSON.toJSONString(dto));
        mqMsg.setFlag(ImpInvoiceConstants.OPS_INVOICE_PROSECC);
        mqMsg.setDataType(ImpInvoiceConstants.OPS_INVOICE);
        mqMsg.setSystem(ImpInvoiceConstants.OPS);
        log.info("新impInvoice开始发送发票入库信息MQ,报文信息为：" + JSON.toJSONString(mqMsg));
        Boolean sendResult = sendMessage.sendInvoiceProcessMsg(mqMsg);
        log.info("新impInvoice发送发票入库信息MQ完成,发送结果为：" + sendResult);
        return sendResult;
    }

    private boolean updateImpInvoiceQty(Long invoiceId, ImpInvoiceMaster newMasterDO) {
        ImpInvoiceMaster masterDO = impInvoiceSysnDao.getTotalImpInvoiceDetail(invoiceId);
        ImpInvoiceMaster updMasterDo = new ImpInvoiceMaster();
        updMasterDo.setId(invoiceId);
        updMasterDo.setTotalQty(masterDO.getTotalQty());
        updMasterDo.setOrderQty(masterDO.getOrderQty());
        // bug 当数据源给的数据为空时，再去计算值，否则直接使用数据源的值即可
        if (newMasterDO.getBoxQty() == null) {
            updMasterDo.setBoxQty(masterDO.getBoxQty());
        }
        if (newMasterDO.getWeight() == null) {
            updMasterDo.setWeight(masterDO.getWeight());
        }
        updMasterDo.setAmount(masterDO.getAmount());
        return impInvoiceMasterMapper.updateByPrimaryKeySelective(updMasterDo) == 1;
    }

    public ResultVo<String> toUpdateOrderState(ImpInvoiceMaster masterDO, List<OpsImpInvoiceDetailVO> invoiceDetails) throws OpsException {
        // todo 需要配置新的开关，来控制新旧程序启动
        ResultVo<DataTypeVO> newDataType = dictDataServiceFeignApi.getDataTypeCodesInfo("6004", "4");
        if (newDataType != null && !"1".equals(newDataType.getData().getExtNote1())) {
            log.info("新交付系统,订单事件消息传输开关已关闭请在旧程序重试");
            return ResultVo.failure("新交付系统,订单事件消息传输开关已关闭请在旧程序重试");
        }
        Date esArrivateDate = masterDO.getPrearriveDate();
        Date shipDate = masterDO.getShipDate();
        // String stateDesc;
        OrderStateVO orderStateVO = new OrderStateVO();
        if (shipDate == null) {
            shipDate = com.smc.smccloud.core.utils.DateUtil.getToday();// 暂时默认导入的当天
        }
        StringBuilder sbStateDes = new StringBuilder();
        if (esArrivateDate == null) {
            esArrivateDate = impInvoiceCommonService.calcEsArrivalDate(masterDO.getSupplierCode(), masterDO.getTransType(),
                    shipDate);
        }

        if (masterDO.getStatus().equals(3))// 已发票入库
        {
            orderStateVO.setStateCode(OrderStateEnum.InvoiceImpStock.getCode());
            if (masterDO.getArriveDate() != null)// 已收货
            {
                sbStateDes.append(com.smc.smccloud.core.utils.DateUtil.dateToDateString(masterDO.getArriveDate())).append("已收货")
                        .append(masterDO.getInvoiceNo()).append(" ");
                orderStateVO.setActArrivalDate(masterDO.getArriveDate());
                orderStateVO.setStateCode(OrderStateEnum.ReceivedPOOrder.getCode());
                orderStateVO.setStateDate(masterDO.getArriveDate());
            } else {
                if (masterDO.getConfirmDate() != null) {
                    sbStateDes.append(com.smc.smccloud.core.utils.DateUtil.dateToDateString(masterDO.getConfirmDate()));
                    orderStateVO.setStateDate(masterDO.getConfirmDate());
                }
                sbStateDes.append("已发票入库");
                if (esArrivateDate != null) {
                    sbStateDes.append(",预计到货日期").append(com.smc.smccloud.core.utils.DateUtil.dateToDateString(esArrivateDate));
                }
            }
            sbStateDes.append(" ").append(masterDO.getInvoiceNo());
        } else {
            if (PublicUtil.isNotEmpty(masterDO.getShipment())) {
                sbStateDes.append(masterDO.getShipment()).append(" ");
            } else {
                sbStateDes.append(masterDO.getSupplierCode()).append(" ");
            }

            sbStateDes.append(com.smc.smccloud.core.utils.DateUtil.dateToDateString(shipDate)).append("已发出").append(masterDO.getInvoiceNo())
                    .append(" ");
            if (esArrivateDate != null) {
                sbStateDes.append(",预计到货日期").append(com.smc.smccloud.core.utils.DateUtil.dateToDateString(esArrivateDate));
            }
            orderStateVO.setStateCode(OrderStateEnum.SupplierShipped.getCode());
            orderStateVO.setStateDate(shipDate);

            // add by A78027 from bug11091 in 20230625
            if (masterDO.getCustomsDate() != null) {
                orderStateVO.setStateCode(OrderStateEnum.InvoiceDeclaredCustoms.getCode());
                sbStateDes.append(",").append(com.smc.smccloud.core.utils.DateUtil.dateToDateString(masterDO.getCustomsDate())).append("已报关");
            }
        }
        orderStateVO.setStateDes(sbStateDes.toString());
        orderStateVO.setProvider(masterDO.getSupplierCode());
        orderStateVO.setSupplierCode(masterDO.getSupplierCode());
        orderStateVO.setEsArrivalDate(esArrivateDate);
        orderStateVO.setPoInvoiceNo(masterDO.getInvoiceNo());
        orderStateVO.setPoShipDate(shipDate);
        // add by A78027 20230411 bug 10270
        // 国内(三方)工厂发票，出厂日=发货日
        if (masterDO.getInvoiceType() != null && (masterDO.getInvoiceType() == 3 || masterDO.getInvoiceType() == 4)) {
            orderStateVO.setPoFacExpdate(shipDate);
        }

        // add by lyc 20240506 14402bug  增加传输报关日期、到港日期
        orderStateVO.setCustomsDate(masterDO.getCustomsDate());
        orderStateVO.setPortArriveDate(masterDO.getPortArrivedate());
        // 发送订单状态
        this.sendOrderState(invoiceDetails, orderStateVO);
        return ResultVo.success();
    }

    /**
     * 统一发送订单状态
     *
     * @param orders       订单号
     * @param orderStateVO 统一订单状态,设置公共的发货状态信息
     * @return
     */
    public ResultVo<String> sendOrderState(List<OpsImpInvoiceDetailVO> orders, OrderStateVO orderStateVO) throws OpsException {
        // log.info("开始发送订单状态：" + orderStateVO.getStateDes() + " count=" +
        // orders.size());

        Set<String> orderNos = new HashSet<>(orders.size());
        for (OpsImpInvoiceDetailVO order : orders) {
            if (StringUtils.isBlank(order.getFullOrderNo())) {
                continue;
            }
            if (orderNos.contains(order.getFullOrderNo())) {
                continue;
            }
            orderStateVO.setOrderNo(order.getFullOrderNo());
            orderStateVO.setRorderNo(order.getOrderNo());
            orderStateVO.setSplitNo(order.getSplitItemNo());
            orderStateVO.setItemNo(order.getItemNo());
            orderStateVO.setModelNo(order.getModelNo());
            if (order.getOrderType() != null) {
                orderStateVO.setOrderType(Integer.parseInt(order.getOrderType()));
            }
            orderNos.add(orderStateVO.getOrderNo());
            this.addOrderState(orderStateVO); //
        }
        return ResultVo.success();
    }

    public ResultVo<String> addOrderState(OrderStateVO orderStateVO) {
        if (null == orderStateVO) {
            return ResultVo.failure("保存订单状态失败");
        }
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setContent(JSON.toJSONString(orderStateVO));
        rabbitMqMessage.setFlag(ImpInvoiceConstants.OPS_MQ_ORDER_STATE);
        rabbitMqMessage.setDataType(ImpInvoiceConstants.OPS_MQ_ORDER);
        rabbitMqMessage.setSystem(ImpInvoiceConstants.OPS);
        log.info("新impInvoice开始发送订单状态MQ,报文信息为：" + JSON.toJSONString(rabbitMqMessage));
        boolean sendResult = sendMessage.sendOrderStateMsg(rabbitMqMessage);
        if (sendResult) {
            log.info("新impInvoice发送订单状态MQ完成,发送结果为：" + sendResult);
            return ResultVo.success("发送成功");
        }
        return ResultVo.failure("发送失败");
    }

    // 中国制造及广州制造的发票入库操作
    private void toProcessInvoice(ImpInvoiceMaster masterDO, List<OpsImpInvoiceDetailVO> invoiceDetails) {
        // 更新采购发票在途状态
        // 发送订单状态
        try {
            toUpdateOrderState(masterDO, invoiceDetails);
        } catch (Exception ex) {
            log.error("更新货期状态失败,{}", JSON.toJSONString(masterDO));
        }
        // 发送发票入库消息
        ImpInvoiceProcessDTO processDTO = new ImpInvoiceProcessDTO();
        processDTO.setInvoiceId(masterDO.getId());
        processDTO.setProcessType(3);
        this.sendInvoiceProcessMsgToMQ(processDTO);
    }


}
