package com.sales.ops.serviceimpl.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.rabbitmq.RabbitMqMessage;
import com.sales.ops.common.rabbitmq.constants.Constants;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.PoConfigDao;
import com.sales.ops.db.extdao.TblWorkDayYearDao;
import com.sales.ops.dto.po.PoOriginConfigDto;
import com.sales.ops.dto.po.core.*;
import com.sales.ops.dto.purchase.RequestPurchaseInfo;
import com.sales.ops.enums.OrderTypeEnum;
import com.sales.ops.enums.PurchaseTypeEnum;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.rabbitmq.SendMessage;
import com.sales.ops.service.core.TransService;
import com.sales.ops.serviceimpl.model.OpsSysLogDO;
import com.smc.smccloud.core.utils.DateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * @author ：C14717
 * @version: $
 * @description： 运输方式
 * @date ：Created in 2025/12/15 12:48
 */
@Service
public class TransServiceImpl implements TransService {

    @Autowired
    private PoConfigDao poConfigDao;
    @Autowired
    private InventorySupplierMapper inventorySupplierMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private TblWorkDayYearDao tblWorkDayYearDao;
    @Autowired
    private TblWorkdayYearMapper tblWorkdayYearMapper;

    @Autowired
    private OpsPoWarehouseDeliverydayMapper opsPoWarehouseDeliverydayMapper;
    @Autowired
    private OpsWarehouseSalesbranchConfigMapper opsWarehouseSalesbranchConfigMapper;

    @Autowired
    private SendMessage sendMessage;

    /**
     * bugid:18836 20251217 c14717
     * 计算制造指定出荷日
     * @param list
     * @param testDate
     * @return
     */
    @Override
    public List<OpsRequestpurchase> calDlvInfo(List<OpsRequestpurchase> list, Date testDate){
        DateTime today = (new DateTime());
        for (OpsRequestpurchase item : list) {
            // bug13941 判断阀汇流板订货时供应商是否支持
            if (!StringUtils.equals("0", item.getSpecmark())) {
                Supplier supplier = supplierMapper.selectByPrimaryKey(item.getSupplierid());
                if(Objects.nonNull(supplier) && !supplier.getSpecmarkFlag()){
                    item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                    if (StringUtils.isBlank(item.getInterceptmsg()))
                        item.setInterceptmsg("该供应商不可采购阀汇流板产品！");
                    else
                        item.setInterceptmsg("该供应商不可采购阀汇流板产品！" + item.getInterceptmsg());
                }
            }

            // 请购携带的订单信息
            RequestPurchaseInfo info = JSONObject.parseObject(item.getInfojson(), RequestPurchaseInfo.class);
            Boolean outStock = false;
            Long configId = 0L;
            //计算日期
            if (OrderTypeEnum.BIN.equals(item.getOrdtype())){
                TransAndExportDateParamDto param = new TransAndExportDateParamDto(item);
                PoCalTransAndExportDateResultDto resultDto = getCalTransIdAndExportDate(param);
                if(Objects.nonNull(resultDto)){
                    item.setTranstype(resultDto.getCalTransId());
                }
                continue;
            }else if (PurchaseTypeEnum.PRE.getTypeCode().equals(item.getPurchasetype())){
                TransAndExportDateParamDto param = new TransAndExportDateParamDto(item);
                if(Objects.nonNull(info) && Objects.nonNull(info.getAirCustomer())){
                    param.setRecoredAir(info.getAirCustomer());
                }
                param.setTestDate(testDate);
                param.setPreTransId(item.getTranstype());

                PoCalTransAndExportDateResultDto resultDto = getCalTransIdAndExportDate(param);
                if(Objects.nonNull(resultDto)){
                    configId = resultDto.getConfigId();
                    item.setTranstype(resultDto.getCalTransId());
                    item.setHopeexportdate(resultDto.getCalDate());
                }

            }else if (PurchaseTypeEnum.SALE.getTypeCode().equals(item.getPurchasetype())){
                TransAndExportDateParamDto param = new TransAndExportDateParamDto(item);
                if(Objects.nonNull(info) && Objects.nonNull(info.getAirCustomer())){
                    param.setRecoredAir(info.getAirCustomer());
                }
                param.setTestDate(testDate);

                PoCalTransAndExportDateResultDto resultDto = getCalTransIdAndExportDate(param);
                if(Objects.nonNull(resultDto)){
                    item.setTranstype(resultDto.getCalTransId());
                    item.setHopeexportdate(resultDto.getCalDate());
                    // 用于收敛品拦截
                    item.setSupplierinventory(resultDto.getSupplierInventory());
                    configId = resultDto.getConfigId();
                    if(Objects.nonNull(resultDto.getOutInv())){
                        outStock = resultDto.getOutInv();
                    }
                }
            } else {
                if (item.getTranstype() == null) {
                    item.setTranstype("0");
                }
                if (item.getHopeexportdate() == null) {
                    // 增加了时间修改
                    item.setHopeexportdate(today.plusDays(28).toDate());
                }
            }

            // 若有inqB，则使用回复的货期计算指定出荷日
            // bug14624根据INQB号计算货期
            // bug15097 增加判断是否货期是因为供应商有库存计算得到
            if (!outStock && info != null && info.getInqbDetail() != null) {
                // 判断供应商及有效期，若可用则使用inqB对应的货期，并给inqno字段赋值为供应商回复号
                if (StringUtils.equals(info.getInqbDetail().getSupplierCode(), item.getSupplierid())) {
                    if (info.getInqbDetail().getExpirationDate() != null
                            && info.getInqbDetail().getExpirationDate().compareTo(today.toDate()) >= 0
                            && info.getInqbDetail().getReplyDeliveryDays() != null
                            && StringUtils.isNotBlank(info.getInqbDetail().getReplyNo())) {
                        item.setHopeexportdate(plusWorkday(today.toDate(),
                                info.getInqbDetail().getReplyDeliveryDays(), item.getSupplierid()));
                        item.setInqno(info.getInqbDetail().getReplyNo());
                        // 若为13点后接单，则出库日往后延长一天
                        LocalTime localTime = LocalTime.now();
                        // bug12373 自动发单后将此时间变为下午一点后，则将出库日增加一天
                        LocalTime dealTime = LocalTime.of(13, 0, 0);
                        if (localTime.isAfter(dealTime) && item.getHopeexportdate() != null) {
                            LocalDate ex = new LocalDate(DateUtils.truncate(item.getHopeexportdate(), Calendar.DATE));
                            item.setHopeexportdate(ex.plusDays(1).toDate());
                        }

                        // 指定制造出库日若为节假日，则延后至工作日
                        if (item.getHopeexportdate() != null) {
                            Date day = workday(item.getHopeexportdate(), item.getSupplierid());
                            item.setHopeexportdate(day);
                        }
                    } else {
                        item.setInqno(null);
                    }
                } else {
                    item.setInqno(null);
                }
            } else {
                item.setInqno(null);
            }
            // 异步记录日志
            sendLogToRabbitMq(item,configId,testDate);
            // bugid:19013 c14717 20250917
            if(!RequestPurchaseStatusEnum.LANJIE.equals(item.getStatecode())){
                if(Objects.isNull(item.getHopeexportdate()) || StringUtils.isBlank(item.getTranstype())){
                    item.setStatecode(RequestPurchaseStatusEnum.LANJIE);
                    item.setInterceptmsg("无法指定纳期、运输方式，请联系IT");
                }
            }
        }
        return list;
    }


    /**
     *  运输方式选择器
     * 1.如果传型号和供应商会判断型号选择器
     * @param translation 是翻译 返回ops_po_transtype数据
     * @param supplier  JP + modelNo 进入型号选择器
     * @param modelNo
     * @param warehoue
     * @param qty
     * @param ordType
     * @return
     */
    @Override
    public List<TransTypeDto> getTransIds(String supplier, String warehoue, String modelNo, Integer qty, String ordType,Boolean translation){

        List<TransTypeDto> result = new ArrayList<>();
        // 1.ops所有运输方式
        List<TransTypeDto> poTransType = poConfigDao.getPoTransType();
        // 用于解释翻译
        if(Objects.nonNull(translation) && translation){
            return poTransType;
        }
        Map<String, String> idNameMap = poTransType.stream()
                .collect(Collectors.toMap(TransTypeDto::getTransId, TransTypeDto::getTransName));
        // 2.仓库供应商选择器
        List<TransTypeDto> poCalCoreTrans = null;
        if(StringUtils.isNotBlank(warehoue) && StringUtils.isNotBlank(supplier)){
            poCalCoreTrans = poConfigDao.getPoCalCoreTrans(warehoue, supplier);
        }else if(StringUtils.isBlank(warehoue) && StringUtils.isNotBlank(supplier)){
            poCalCoreTrans = poConfigDao.getPoCalCoreTransBySupplier( supplier);
        }else if (StringUtils.isNotBlank(warehoue) && StringUtils.isBlank(supplier)){
            poCalCoreTrans = poConfigDao.getPoCalCoreTransByWarehouse(warehoue);
        }else {
            poCalCoreTrans = poConfigDao.getPoCalCoreTransAll();
        }
        List<String> poCalCoreTransIds = poCalCoreTrans.stream()
                .map(TransTypeDto::getTransId)  // 1. 映射：提取每个对象的 transId
                .collect(Collectors.toList());
        // 获取手动自动map
        Map<String, Integer> transUsageMap = poCalCoreTrans.stream()
                .collect(Collectors.toMap(
                        TransTypeDto::getTransId,       // Key
                        TransTypeDto::getUsageType,     // Value
                        (existingValue, newValue) -> newValue // 冲突解决策略：保留新值
                ));
        // 初始化列表应用模式 自动
        List<String> collectDefalutList = new ArrayList<>();
        // 初始化列表应用模式 特殊
        List<String> collectSpceialList = new ArrayList<>();
        if(StringUtils.isNotBlank(supplier) && supplier.equals("JP") && StringUtils.isNotBlank(modelNo)){
            //3.型号选择器
            String porductNonStandard = poConfigDao.getPorductNonStandard(modelNo);// 海运优先 海运和快船
            if(StringUtils.isNotBlank(porductNonStandard)){
                List<String> bigSizeList = new ArrayList<>();
                bigSizeList.add("0");
                //bigSizeList.add("4");
                poCalCoreTransIds.retainAll(bigSizeList);
            }

            String inspectionsGroup = poConfigDao.getInspectionsGroup(modelNo); // 温控器必须海运
            if(StringUtils.isNotBlank(inspectionsGroup)){
                List<String> spceialList = new ArrayList<>();
                spceialList.add("0");
                poCalCoreTransIds.retainAll(spceialList);
            }

            // 4.型号选择器 精确匹配
            List<PoTransTypeCoreDto> transTypeCores = poConfigDao.getTransTypeCore(modelNo);
            for(PoTransTypeCoreDto obj : transTypeCores){
                if(Objects.nonNull(obj.getCompareGreater()) && Objects.nonNull(qty)){
                    if(obj.getCompareGreater()){
                        if(qty > obj.getCompareQuantity()){
                            if(obj.getUsageType() == 0){
                                collectDefalutList.add(obj.getTransId());
                            }else {
                                collectSpceialList.add(obj.getTransId());
                            }
                        }
                    }else {
                        if(qty < obj.getCompareQuantity()){
                            if(obj.getUsageType() == 0){
                                collectDefalutList.add(obj.getTransId());
                            }else {
                                collectSpceialList.add(obj.getTransId());
                            }
                        }
                    }
                }else {
                    if(obj.getUsageType() == 0){
                        collectDefalutList.add(obj.getTransId());
                    }else {
                        collectSpceialList.add(obj.getTransId());
                    }
                }
            }
            // 5.型号选择器 模糊匹配
            List<PoTransTypeCoreDto> transTypeMatchCore = poConfigDao.getTransTypeMatchCore();
            for(PoTransTypeCoreDto obj : transTypeMatchCore){
                if (Pattern.matches(obj.getModelNo(), modelNo)){
                    if(Objects.nonNull(obj.getCompareGreater()) && Objects.nonNull(qty)){
                        if(obj.getCompareGreater()){
                            if(qty > obj.getCompareQuantity()){
                                if(obj.getUsageType() == 0){
                                    collectDefalutList.add(obj.getTransId());
                                }else {
                                    collectSpceialList.add(obj.getTransId());
                                }
                            }
                        }else {
                            if(qty < obj.getCompareQuantity()){
                                if(obj.getUsageType() == 0){
                                    collectDefalutList.add(obj.getTransId());
                                }else {
                                    collectSpceialList.add(obj.getTransId());
                                }
                            }
                        }
                    }else {
                        if(obj.getUsageType() == 0){
                            collectDefalutList.add(obj.getTransId());
                        }else {
                            collectSpceialList.add(obj.getTransId());
                        }
                    }
                }
            }
        }
        if(CollectionUtils.isNotEmpty(collectDefalutList)){
            poCalCoreTransIds.retainAll(collectDefalutList);
        }
        // 6.DR/CR订单不可以走快船
        if(StringUtils.isNotBlank(ordType)){
            if(OrderTypeEnum.DR.equals(ordType) || OrderTypeEnum.CR.equals(ordType)){
                // 排除 快船
                poCalCoreTransIds.removeIf(s -> s.equals("4"));
            }
        }
        // 7.返回结果 应用模式是默认 手动或自动 G是自动 ops_po_cal_core_config
        for(String transId : poCalCoreTransIds){
            TransTypeDto transTypeDto = new TransTypeDto(transId,idNameMap.get(transId),transUsageMap.get(transId));
            result.add(transTypeDto);
        }
        // 8.返回应用模式 手动
        for(String transId : collectSpceialList){
            TransTypeDto transTypeDto = new TransTypeDto(transId,idNameMap.get(transId),1);
            result.add(transTypeDto);
        }
        // 9.校验如果返回结果不包含空运，需要删除掉快递
        if(result.stream().noneMatch(s -> s.getTransId().equals("1"))){
            result.removeIf(s -> s.getTransId().equals("G"));
        }
        return result;
    }


    // 制造指定出荷日计算
    @Override
    public PoCalTransAndExportDateResultDto getCalTransIdAndExportDate(TransAndExportDateParamDto param){
        // 1.参数校验
        if(Objects.isNull(param)){
            return null;
        }
        if(StringUtils.isBlank(param.getSupplierId()) || StringUtils.isBlank(param.getModelNo())|| Objects.isNull(param.getOrderQty())
                || Objects.isNull(param.getHopeDeliveryDate()) || StringUtils.isBlank(param.getPurchasetype())
                || StringUtils.isBlank(param.getrWarehouse()) || StringUtils.isBlank(param.getpWarehouse())){
            return null;
        }

        Long configId  = 0L;
        param.setCalDate(null);
        param.setCalTransId(null);
        // 2.通用运输方式选择器
        List<TransTypeDto> transIds = getTransIds(param.getSupplierId(), param.getpWarehouse(), param.getModelNo(), param.getOrderQty(), param.getOrdType(),false);
        if(CollectionUtils.isEmpty(transIds)){
            return null;
        }
        transIds.removeIf(s -> s.getUsageType() == 1);
        if(CollectionUtils.isEmpty(transIds)){
            return null;
        }
        // 3. Bin订单
        if (OrderTypeEnum.BIN.equals(param.getOrdType())){
            if(CollectionUtils.isNotEmpty(transIds) && transIds.size() == 1 && transIds.get(0).getTransId().equals("1")){
                return new PoCalTransAndExportDateResultDto("1",null,0L,null,null);
            }
            return null;
        }

        // 4. 必须空运标识，如果运输方式选择器存在则 只能选空运
        if(param.getRecoredAir()){
            boolean air = transIds.stream()
                    .anyMatch(dto -> dto.getTransId() != null && dto.getTransId().equals("1"));
            if(air){
                transIds.removeIf(s -> !"1".equals(s.getTransId()));
            }
        }

        // 5.先行在库计算
        if (PurchaseTypeEnum.PRE.getTypeCode().equals(param.getPurchasetype())){
            if(StringUtils.isNotBlank(param.getPreTransId())){
                //5.1 先行在库运输方式如果包含在计算运输方式内，选择先行在库的运输方式。否则取计算的运输方式第一条
                boolean containTransId = transIds.stream()
                        .anyMatch(dto -> dto.getTransId() != null && dto.getTransId().equals(param.getPreTransId()));
                if(containTransId){
                    transIds.removeIf(s -> !param.getPreTransId().equals(s.getTransId()));
                }else {
                    if (!transIds.isEmpty() && transIds.size() != 1) {
                        transIds.subList(1, transIds.size()).clear();
                    }
                }
            }
            //5.2 获取计算规则
            List<PoCalCoreDto> poCalCores = getPoCalCore(param.getpWarehouse(), param.getSupplierId(), null, false, null, transIds);
            if(CollectionUtils.isNotEmpty(poCalCores)){
                // 5.3 计算最终制造指定出荷日和运输方式
                return calCore(poCalCores,param,configId,null,null);
            }

        // 6.销售订单 计算
        } else if (PurchaseTypeEnum.SALE.getTypeCode().equals(param.getPurchasetype())){
            // 6.1 初始化数据
            boolean outStock = false;//出库存
            String  invClass = null;//出库区分
            InventorySupplier invSupplier = null;
            // 6.2 获取供应商
            Supplier supplier = supplierMapper.selectByPrimaryKey(param.getSupplierId());
            // 6.3 获取原产国
            String poOrigin = getPoOriginConfig(param.getModelNo(), param.getSupplierId());
            // 6.3 获取出供应商库存标识和供应商出库区分标识
            if (supplier.getEnableinventory() && StringUtils.isBlank(poOrigin)) {
                // bug13659 增加SHIKOMI判断,使用SHIKOMI则不判断库存
                boolean shikomiflag = false;
                if (supplier.getEnableShikomiCal()) {
                    if (StringUtils.isNotBlank(param.getShikomianswerno())) {
                        shikomiflag = true;
                    }
                }
                // 是否有库存
                if (!shikomiflag) {
                    // 2 查询供应商库存
                    InventorySupplierKey key = new InventorySupplierKey();
                    key.setModelno(param.getModelNo());
                    key.setSupplierid(param.getSupplierId());
                    invSupplier = inventorySupplierMapper.selectByPrimaryKey(key);

                    if(Objects.nonNull(invSupplier)){
                        if (invSupplier.getQuantity() - invSupplier.getQuantityprepare() >= param.getOrderQty()) {
                            outStock = true;
                            invClass = invSupplier.getInventoryClass();
                        }
                    }
                }
            }
            // 6.4 获取计算规则
            List<PoCalCoreDto> poCalCores = getPoCalCore(param.getpWarehouse(), param.getSupplierId(), invClass, outStock, poOrigin, transIds);
            if(CollectionUtils.isNotEmpty(poCalCores)){
                // 6.5 预占供应商库存
                if (outStock) {
                    // bug8999 马腾 增加供应商库存预约数量
                    InventorySupplier updatekey = new InventorySupplier();
                    updatekey.setModelno(param.getModelNo());
                    updatekey.setSupplierid(param.getSupplierId());
                    updatekey.setQuantityprepare(invSupplier.getQuantityprepare() + param.getOrderQty());
                    inventorySupplierMapper.updateByPrimaryKeySelective(updatekey);
                }
                Integer supplierInventory = null;
                if(Objects.nonNull(invSupplier)){
                    supplierInventory = invSupplier.getQuantity() - invSupplier.getQuantityprepare();
                }
                // 6.5 计算最终制造指定出荷日和运输方式
                return calCore(poCalCores,param,configId,outStock,supplierInventory);
            }
        }
        return null;
    }

    //  计算最终制造指定出荷日和运输方式
    public PoCalTransAndExportDateResultDto calCore(List<PoCalCoreDto> poCalCores,TransAndExportDateParamDto param ,Long configId,Boolean outStock, Integer supplierInventory){
        for (PoCalCoreDto c : poCalCores) {
            calSaleOrder(c,param);
            if (StringUtils.isNotBlank(param.getCalTransId()) && param.getCalDate() != null) {
                configId = c.getId();
                break;
            }
        }
        // 保底计算
        configId = checkResult(poCalCores,param,configId);
        return new PoCalTransAndExportDateResultDto(param.getCalTransId(),param.getCalDate(),configId,outStock,supplierInventory);
    }

    // 保底计算
    public Long checkResult(List<PoCalCoreDto> list,TransAndExportDateParamDto item,Long configId){
        if (StringUtils.isNotBlank(item.getCalTransId()) && item.getCalDate() != null) {
            return configId;
        }else {
            // 取最后一条作为计算依据
            if (!list.isEmpty() && list.size() != 1) {
                list.subList(0, list.size() - 1).clear();
            }
            PoCalCoreDto calCore = list.get(0);
            Date now = DateUtil.getNow();
            if(Objects.nonNull(item.getTestDate())){
                now = item.getTestDate();
            }
            LocalDate today = new LocalDate(now);
            if(calCore.getEnableInventory()){
                if (item.getCalDate() == null){
                    item.setCalDate(plusWorkday(today.toDate(), calCore.getCompensationDays(), item.getSupplierId()));
                }
            }
            item.setCalTransId(calCore.getTransId());
            if (item.getCalDate() == null) {
                // bug13659 变更为加工作日
                item.setCalDate(
                        plusWorkday(today.toDate(), calCore.getCompensationDays(), item.getSupplierId()));
            }
            return calCore.getId();
        }
    }

    public void calSaleOrder(PoCalCoreDto calCore, TransAndExportDateParamDto item) {
        // 订货日期 A now
        Date now = DateUtil.getNow();
        if(Objects.nonNull(item.getTestDate())){
            now = item.getTestDate();
        }
        int hour = DateUtil.getHour(now);
        int w = 0;//13点前发单是0天；13点后发单是1天
        if (hour >= 13) {
            w = 1;
        }
        // 通关天数 C 包含运输天数
        int c = calCore.getTransDay();
        // 营业物流入出库天数 D
        int d = calCore.getWmsOperateDay();
        // 营业所别运输天数+仓库间运输天数
        int e1AddE2 = transOtherDay(item.getDeptNo(), item.getpWarehouse(), item.getrWarehouse());
        // 客户交货期 F
        Date f = item.getHopeDeliveryDate();
        // 计算过程数据
        Date z = DateUtil.addDay(f, -(c+d+e1AddE2+w));
        Date a = now;
        //Workday（Z-A） 计算差多少工作日 比较 c.getConditionJudgmentDays()
        int diffWorkDays = subWorkday(z,a,item.getSupplierId());
        LocalDate today = new LocalDate(now);
        // 存在库存计算指定制造出荷日
        if(calCore.getEnableInventory()){
            if (item.getCalDate() == null){
                item.setCalDate(plusWorkday(today.toDate(), calCore.getCompensationDays(), item.getSupplierId()));
            }
        }
        // 使用偏移量并且没有在库数量满足时  || 偏移量=true 大于等于条件 ，偏移量=false 大于的条件
        if (calCore.getEnableStdDeliveryDay() && (item.getCalDate() == null)) {
            if (calCore.getCompareGreater()) {
                if (diffWorkDays >= calCore.getConditionJudgmentDays()) {
                    item.setCalTransId(calCore.getTransId());
                    if (item.getCalDate() == null) {
                        item.setCalDate(getWorkday(z,item.getSupplierId()));
                    }
                }
            } else {
                if (diffWorkDays < calCore.getConditionJudgmentDays()) {
                    item.setCalTransId(calCore.getTransId());
                    if (item.getCalDate() == null) {
                        // bug13659 变更为加工作日
                        item.setCalDate(
                                plusWorkday(today.toDate(), calCore.getCompensationDays(), item.getSupplierId()));
                    }
                }
            }
        } else {
            if (calCore.getCompareGreater()) {
                if (diffWorkDays > calCore.getConditionJudgmentDays()) {
                    item.setCalTransId(calCore.getTransId());
                    if (item.getCalDate() == null) {
                        item.setCalDate(getWorkday(z,item.getSupplierId()));
                    }
                }
            } else {
                if (diffWorkDays <= calCore.getConditionJudgmentDays()) {
                    item.setCalTransId(calCore.getTransId());
                    if (item.getCalDate() == null) {
                        // bug13659 变更为加工作日
                        item.setCalDate(
                                plusWorkday(today.toDate(), calCore.getCompensationDays(), item.getSupplierId()));
                    }
                }
            }
        }
    }


    // 判断是否为工作日，不是则返回最近的工作日
    // bug 10513 马腾 判断日本供应商时使用日本工作日字段
    public Date workday(Date day, String supplierId) {
        TblWorkdayYearExample ex = new TblWorkdayYearExample();
        if ("JP".equals(supplierId)) {
            ex.createCriteria().andWorkdayDateLessThanOrEqualTo(day).andOptflagJpEqualTo("0");
        } else {
            ex.createCriteria().andWorkdayDateLessThanOrEqualTo(day).andOptflagEqualTo("0");
        }
        ex.setOrderByClause("Workday_date desc");
        List<TblWorkdayYear> temp = tblWorkdayYearMapper.selectByExample(ex);
        if (temp.size() > 0) {
            LocalDate today = new LocalDate(DateUtils.truncate(new Date(), Calendar.DATE));
            // 若为当天或是否差距五天以上，则取之后的工作日
            if (temp.get(0).getWorkdayDate().compareTo(today.toDate()) < 1
                    || temp.get(0).getWorkdayDate().compareTo(today.plusDays(5).toDate()) < 1) {
                ex.clear();
                if ("JP".equals(supplierId)) {
                    ex.createCriteria().andWorkdayDateGreaterThanOrEqualTo(day).andOptflagJpEqualTo("0");
                } else {
                    ex.createCriteria().andWorkdayDateGreaterThanOrEqualTo(day).andOptflagEqualTo("0");
                }
                ex.setOrderByClause("Workday_date asc");
                temp = tblWorkdayYearMapper.selectByExample(ex);
                if (temp.size() > 0) {
                    return temp.get(0).getWorkdayDate();
                }
            } else {
                return temp.get(0).getWorkdayDate();
            }
        }
        return day;
    }

    /**
     *  计算 z到a相差几个工作日
     * @param z
     * @param a
     * @param supplierId
     * @return
     */
    public Integer subWorkday(Date z,Date a, String supplierId) {
        TblWorkdayYearExample ex = new TblWorkdayYearExample();
        // 增加了时间修改
        if ("JP".equals(supplierId)) {
            ex.createCriteria().andWorkdayDateGreaterThanOrEqualTo(a).andOptflagJpEqualTo("0")
                    .andWorkdayDateLessThan(z);
        } else {
            ex.createCriteria().andWorkdayDateGreaterThanOrEqualTo(a).andOptflagEqualTo("0")
                    .andWorkdayDateLessThan(z);
        }
        ex.setOrderByClause("Workday_date");
        List<TblWorkdayYear> temp = tblWorkdayYearMapper.selectByExample(ex);
        if (temp != null) {
            return temp.size();
        }
        return 0;
    }

    /**
     * 获取工作时间 如何是工作时间则返回，否则返回前一天工作日
     * @param z
     * @param supplierId
     * @return
     */
    public Date getWorkday(Date z, String supplierId) {
        String s = DateUtil.dateToDateString(z);
        List<TblWorkdayYear> list = null;

        if ("JP".equals(supplierId)) {
            list = tblWorkDayYearDao.getSubDaysJpWorkDay(s, 1);
        } else {
            list = tblWorkDayYearDao.getSubDaysCNWorkDay(s, 1);
        }
        return list.get(0).getWorkdayDate();
    }

    // bug13659 WTSR2024000085-指定制造出荷日计算方法优化
    public Date plusWorkday(Date today, Integer day, String supplierId) {
        String s = DateUtil.dateToDateString(today);
        List<TblWorkdayYear> list = null;
        if ("JP".equals(supplierId)) {
            list = tblWorkDayYearDao.getAddDaysJPWorkDay(s, day+1);
        } else {
            list = tblWorkDayYearDao.getAddDaysCNWorkDay(s, day+1);
        }
        return list.get(day).getWorkdayDate();
    }

    // 营业所别运输天数+仓库间运输天数
    public int transOtherDay(String deptNo, String warehouseCode, String requestWare) {
        // 营业所别
        OpsWarehouseSalesbranchConfigExample ex = new OpsWarehouseSalesbranchConfigExample();
        ex.createCriteria().andSalesBranchIdEqualTo(deptNo).andWarehouseCodeEqualTo(requestWare);
        List<OpsWarehouseSalesbranchConfig> temp = opsWarehouseSalesbranchConfigMapper.selectByExample(ex);
        // 仓库间运输天数
        int tr = 0;
        if (requestWare != null && !warehouseCode.equals(requestWare)) {
            OpsPoWarehouseDeliverydayKey key = new OpsPoWarehouseDeliverydayKey();
            key.setFromwarehouse(warehouseCode);
            key.setTowarehouse(requestWare);
            OpsPoWarehouseDeliveryday day = opsPoWarehouseDeliverydayMapper.selectByPrimaryKey(key);
            if (day != null) {
                tr = day.getDeliveryday();
            } else {
                tr = 3;
            }
        }
        if (temp.size() == 1) {
            return tr + temp.get(0).getDeliveryDay();
        }
        return tr;
    }

    /**
     * bugid：17244 C14717 20250418
     * 获取配置文件
     * @param warehouseCode 仓库号
     * @param supplierId 供应商id
     * @param invClass 供应商出库区分（JP: WH F1 F2）
     * @param origin 供应商产地（JP:VN IN ）
     * @return
     */
    public List<PoCalCoreDto> getPoCalCore(String warehouseCode, String supplierId , String invClass
            , boolean outStock, String origin,List<TransTypeDto> transIds){
        //无出库区分无供应商产地的判断
        if(StringUtils.isBlank(invClass) && StringUtils.isBlank(origin)){
            return poConfigDao.getPoCalCoreByOutStock(warehouseCode,supplierId,outStock,transIds);
        }
        //供应商出库区分（JP: WH F1 F2） 先判断库存，注意顺序
        if(StringUtils.isNotBlank(invClass)){
            return poConfigDao.getPoCalCoreByInvClass(warehouseCode,supplierId,invClass,transIds);
        }
        //供应商产地（JP:VN IN ）
        if(StringUtils.isNotBlank(origin)){
            return poConfigDao.getPoCalCoreByOrigin(warehouseCode,supplierId,origin,transIds);
        }
        return null;
    }


    /**
     * bugid：17244 C14717 20250418
     * 日本供应商 产地是越南和印度的型号，返回产地信息，否则返回空
     * @param modelNo
     * @param supplierId
     * @return
     */
    public String getPoOriginConfig(String modelNo,String supplierId){
        if ("JP".equals(supplierId)){
            PoOriginConfigDto poOriginConfig = poConfigDao.getPoOriginConfig(modelNo);
            if(Objects.nonNull(poOriginConfig) && StringUtils.isNotBlank(poOriginConfig.getPurchaseOrigin())){
                return poOriginConfig.getPurchaseOrigin();
            }
        }
        return null;
    }


    /**
     * 记录日志
     */
    public static final String OPS_SYSLOG_CREATE = "sms-syslog-create";
    public static final String SYS_COMMON_LOG = "ops:rabbitmq:sysLog";
    private void sendLogToRabbitMq(OpsRequestpurchase item,Long configId,Date today) {
        if (item == null) {
            return;
        }
        RequestPurchaseInfo info = JSONObject.parseObject(item.getInfojson(), RequestPurchaseInfo.class);
        OpsSysLogDO sysLogBean = new OpsSysLogDO();
        sysLogBean.setCreatetime(DateUtil.getNow());
        sysLogBean.setTitle("HopeExp");
        sysLogBean.setRequesturi(item.getOrderno()+"-"+item.getItemno());
        JSONObject obj = new JSONObject();
        if(Objects.nonNull(info)){
            if(Objects.nonNull(info.getAirCustomer())){
                obj.put("airCustomer",info.getAirCustomer());
            }
            if(Objects.nonNull(info.getInqbDetail())){
                obj.put("inqbDetail",true);
            }
        }
        obj.put("cId",configId);
        obj.put("hopeExp",DateUtil.dateToDateString(item.getHopeexportdate()));
        obj.put("hopeDel",DateUtil.dateToDateString(item.getHopedeliverydate()));
        if(Objects.isNull(today)){
            today = DateUtil.getNow();
        }
        obj.put("today",DateUtil.dateToDateString(today));
        sysLogBean.setParams(obj.toJSONString());

        // 推送数据到mq
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setContent(JSON.toJSONString(sysLogBean));
        rabbitMqMessage.setFlag(OPS_SYSLOG_CREATE);
        rabbitMqMessage.setDataType(SYS_COMMON_LOG);
        rabbitMqMessage.setSystem(Constants.OPS);
        boolean success = sendMessage.sendCommonLogMsg(rabbitMqMessage);

    }

}
