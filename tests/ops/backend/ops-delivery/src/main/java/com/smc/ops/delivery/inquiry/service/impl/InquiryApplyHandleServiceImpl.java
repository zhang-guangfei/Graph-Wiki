package com.smc.ops.delivery.inquiry.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.delivery.ExpPoDto;
import com.sales.ops.dto.delivery.InqAExpPoRequestDto;
import com.sales.ops.dto.expdetail.DataCodeDto;
import com.sales.ops.dto.inqb.InqbCommonEnum;
import com.sales.ops.dto.inquiry.*;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.enums.*;
import com.sales.ops.feign.inquiry.WmCKRuleFeignApi;
import com.sales.ops.feign.purchase.PurchaseBatchEditFeignApi;
import com.smc.ops.delivery.inquiry.mapper.*;
import com.smc.ops.delivery.inquiry.service.InquiryApplyHandleService;
import com.smc.ops.delivery.inquiry.service.InquiryApplyService;
import com.smc.ops.delivery.inquiry.service.InquiryManuDataService;
import com.smc.ops.delivery.inquiry.utils.InquiryUtils;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 催促订单处理
 *
 * @author B91717
 * @date 2023-12-05
 */
@Service
@Slf4j
public class InquiryApplyHandleServiceImpl implements InquiryApplyHandleService {

    @Resource
    private PurchaseBatchEditFeignApi purchaseBatchEditFeignApi;


    @Resource
    private InquiryApplyMapper inquiryApplyMapper;

    @Resource
    private InquiryPoHandleMapper inquiryPoHandleMapper;

    @Resource
    private OpsRcvdetailMapper opsRcvdetailMapper;

    @Resource
    private InquiryDetailMapper inquiryDetailMapper;

//    @Resource
//    private OPSRedisUtils opsRedisUtils;

    @Resource
    private InquiryManuMidMapper inquiryManuMidMapper;

    @Resource
    private InquiryManuDataService inquiryManuDataService;

    @Resource
    private RedisManager redisManager;

    @Resource
    private InquiryApplyService inquiryApplyService;

    @Resource
    private InquiryWorkDayYearMapper tblWorkDayYearMapper;

    @Resource
    private InquiryReasonParamConfigMapper inquiryReasonParamConfigMapper;

    @Resource
    private OpsAssignReasultMapper opsAssignReasultMapper;

    @Resource
    private WmCKRuleFeignApi wmCKRuleFeignApi;

    /**
     * @param
     * @return 校验规则
     * 当催促类型为采购催促时，校验规则如下：
     * 1.	采购单状态处于已删除状态不可催促；
     * 2.	采购单状态处于已到货状态不可催促"
     * 3.	采购单状态处于已供应商未接单状态不可催促"
     * 4.	采购单状态处于供应商接单异常状态不可催促"
     * 1-4总结 采购单已接单 或者运输中 且到货数量<采购数量可催促，其余均不可催促
     * 5.	采购单处于已捆包状态（此状态怎么获取，在delivery数据中有一个表* 的数据）不可催促 暂不判断
     * 6.	采购单客单且供应商为中国制造和广州制造出库区分为在库时不可催促；客单且供应商为日本的，可以催促；补库单不论出库区分和供应商是什么都可催促"
     * 7.	采购单供应商为日本时且催促级别为HoT-2级别，且已在此级别催促2次时不可再催促（业务课和营业所催促同一个订单时，催促次数统计在一起）"
     * 8.	制造目前是3次不可再催促"
     * 9.	同一个业务单据问询间隔不足24H时，不可再催促。"
     * 10. 当供应商为日本时，校验 催促货期是否为日本节假日，是节假日时不能催促
     * 11. 2024-06-20 校验是否为休日针对所有供应商，没有休日表配置的不做校验，如为休日需提醒且不许提交。
     * 12. 当催促次数<=2时，催促级别为INQ-A；当催促次数>2且<=4时，催促级别为HOT-1；当催促次数>4且<=6时，催促级别为HOT-2；
     * 13. 给日本推送数据时增加催促级别字段，INQ-A时推送日本hotline字段为空；HOT-1时推送日本hotline字段为1；HOT-2时推送日本hotline字段为2；
     */
    @Override
    public ResultVo<List<InquiryApplyVerifyReurn>> purchaseInquiryVerify(List<String> orderList, String type) throws Exception {
        List<InquiryApplyVerifyReurn> returnList = new ArrayList<>();
        InquiryApplyVerifyReurn inquiryApplyVerifyReurn;
        Date nowDate = new Date();
        for (String orderFno : orderList) {
            orderFno = orderFno.trim();
            inquiryApplyVerifyReurn = new InquiryApplyVerifyReurn();
            inquiryApplyVerifyReurn.setOrderNo(orderFno);
            if (StringUtils.isBlank(orderFno)) {
//                return ResultVo.failure(InquiryVerifyMsg.ORDERNONULL.getDesc());
                inquiryApplyVerifyReurn.setCanPress(false);
                inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.ORDERNONULL.getDesc());
                returnList.add(inquiryApplyVerifyReurn);
                continue;
            }
            List<String> ordernoList = new ArrayList<>(Collections.singletonList(orderFno));
            // 调用feign接口，查询采购单信息
            // bug14435 INQA对接日本修改，需要新增存储两个字段，pono和lineItem,用于发送日本时采购单号
            CommonResult<List<InquiryQueryPurchaseDto>> result = purchaseBatchEditFeignApi.getPurchaseOrder(ordernoList);
            if (Objects.isNull(result) || CollectionUtils.isEmpty(result.getData())) {
                inquiryApplyVerifyReurn.setCanPress(false);
                inquiryApplyVerifyReurn.setCheckFailureMsg(StringUtils.isBlank(result.getMessage()) ? InquiryVerifyMsg.UNPURCHASE.getDesc() : result.getMessage());
                returnList.add(inquiryApplyVerifyReurn);
                continue;
            }
            if (!result.isSuccess()) {
                inquiryApplyVerifyReurn.setCanPress(false);
                inquiryApplyVerifyReurn.setCheckFailureMsg(result.getMessage());
                returnList.add(inquiryApplyVerifyReurn);
                continue;
            }
            InquiryQueryPurchaseDto opsPurchaseorders = result.getData().get(0);
            // 增加状态字典翻译
            List<DataCodeDto> dataCodes = inquiryApplyService.getTbldata(InquiryConstants.DICT_CLASSCODE_PURCHASE);
            if (!CollectionUtils.isEmpty(dataCodes)) {
                Map<String, String> purchaseStatusEnum = dataCodes.stream().collect(Collectors.toMap(DataCodeDto::getCode, DataCodeDto::getCodeName, (val1, val2) -> val2));
                inquiryApplyVerifyReurn.setOrderStatusDesc(purchaseStatusEnum.get(opsPurchaseorders.getStatecode()));
            }
            // 先校验 催促次数
            List<OpsInquiryApply> applyList = inquiryApplyMapper.selectRecentApply(orderFno);
            int inquiryCount = applyList.size(); //获取催促次数
            inquiryApplyVerifyReurn.setInquiryCount(inquiryCount+1); // 催促次数
            // 校验采购单状态，不符合的状态，返回当前的采购状态，提示当前采购单状态为XXX不可进行催促
            if (!(opsPurchaseorders.getStatecode().equals(PurchaseOrderStatusEnum.YIJIEDAN) || opsPurchaseorders.getStatecode().equals(PurchaseOrderStatusEnum.YUNSHUZHONG))) {
                inquiryApplyVerifyReurn.setCanPress(false);
                inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.STATUSERROR.getDesc() + inquiryApplyVerifyReurn.getOrderStatusDesc());
                returnList.add(inquiryApplyVerifyReurn);
                continue;
            }
            inquiryApplyVerifyReurn.setOrderNo(orderFno);
            // bug14435 INQA对接日本修改,增加pono相关字段
            inquiryApplyVerifyReurn.setPono(opsPurchaseorders.getPono());
            inquiryApplyVerifyReurn.setLineitem(opsPurchaseorders.getLineitem());
            inquiryApplyVerifyReurn.setRorderNo(opsPurchaseorders.getOrderno());
            inquiryApplyVerifyReurn.setRorderItem(opsPurchaseorders.getItemno());
            inquiryApplyVerifyReurn.setSplititemno(opsPurchaseorders.getSplititemno());
            inquiryApplyVerifyReurn.setModelNo(opsPurchaseorders.getModelno());
            inquiryApplyVerifyReurn.setQuantity(opsPurchaseorders.getQuantity());
            inquiryApplyVerifyReurn.setCustomerNo(opsPurchaseorders.getCustomerno());
            inquiryApplyVerifyReurn.setEndUser(opsPurchaseorders.getUserno());
            inquiryApplyVerifyReurn.setOrderStatus(Integer.parseInt(opsPurchaseorders.getStatecode()));
            inquiryApplyVerifyReurn.setSupplierOrderNo(opsPurchaseorders.getReplyorderno()); //增加供应商接单号
            inquiryApplyVerifyReurn.setHopeExportDate(opsPurchaseorders.getHopeexportdate());
            inquiryApplyVerifyReurn.setDlvModdate(opsPurchaseorders.getDlvmoddate());
            inquiryApplyVerifyReurn.setSupplierId(opsPurchaseorders.getSupplierid());
            inquiryApplyVerifyReurn.setOrderType(opsPurchaseorders.getOrdtype());
            // bug14490 INQA对接日本修改,需要新增传输字段
            inquiryApplyVerifyReurn.setSmccode(opsPurchaseorders.getSmccode());
            inquiryApplyVerifyReurn.setPurchasetype(opsPurchaseorders.getPurchasetype());
            if (InquiryTypeEnum.PURCHASE.getType().equalsIgnoreCase(type)) { // 采购催促时，回复单位默认为当前的供应商
                inquiryApplyVerifyReurn.setReplyDept(opsPurchaseorders.getSupplierid());
            }
            // bug14826，INQA增加手配号的校验，若无手配号则不可催促（对所有供应商适用）
            if (StringUtils.isBlank(opsPurchaseorders.getReplyorderno())) {
                inquiryApplyVerifyReurn.setCanPress(false);
                inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.REPLYNONULL.getDesc());
                returnList.add(inquiryApplyVerifyReurn);
                continue;
            }
            // 2024-03-04 先进行供应校验，当催促发送配置表中不存在时，提示错误
            List<OpsInquiryAdapterConfig> adapterConfigList = inquiryPoHandleMapper.selectAdapterBySupplier(inquiryApplyVerifyReurn.getSupplierId());
            if (CollectionUtils.isEmpty(adapterConfigList)) {
                inquiryApplyVerifyReurn.setCanPress(false);
                inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.NOSUPPILYCONFIG.getDesc() + inquiryApplyVerifyReurn.getSupplierId());
                returnList.add(inquiryApplyVerifyReurn);
                continue;
            }
            // 运输中的采购订单，校验采购到货数量，qtyTrans>=采购数量时，是完纳出荷不可催促， qtyTrans<采购数量时 分纳出荷可以催促
            if (opsPurchaseorders.getStatecode().equals(PurchaseOrderStatusEnum.YUNSHUZHONG) && opsPurchaseorders.getQtytrans() != null && opsPurchaseorders.getQtytrans() >= opsPurchaseorders.getQuantity()) {
                inquiryApplyVerifyReurn.setCanPress(false);
                inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.QTYRECEIVE.getDesc());
                returnList.add(inquiryApplyVerifyReurn);
                continue;
            }
            Set<String> suppilyList = new HashSet<String>(InquiryVerifyMsg.manuSuppilyList());
            Set<String> manuGzSet = new HashSet<String>(InquiryVerifyMsg.manuGzSuppilyList());
            if (opsPurchaseorders.getPurchasetype().equalsIgnoreCase(PurchaseTypeEnum.SALE.getTypeCode()) && !opsPurchaseorders.getMergeflag()) {
                // 重新组合订单号+项号，查询客户采购单号信息
                String rorderno = opsPurchaseorders.getOrderno() + '-' + opsPurchaseorders.getItemno();
                List<Rcvdetail> rcvdetails = opsRcvdetailMapper.getRcvdetailByOrderno(rorderno);
                if (!CollectionUtils.isEmpty(rcvdetails)) {
                    // 取得接单中的客户采购单号 corderno字段
                    inquiryApplyVerifyReurn.setPurchaseNo(rcvdetails.get(0).getCorderNo());
                }

                // 采购单客单且供应商为中国制造和广州制造出库区分为在库时不可催促；客单且供应商为日本的，可以催促
//            if (manuGzSet.contains(opsPurchaseorders.getSupplierid()) && StringUtils.isNotBlank(opsPurchaseorders.getInventorytypecode()) && opsPurchaseorders.getInventorytypecode().equals(InventoryTypeEnum.TY.getType())) {
//                inquiryApplyVerifyReurn.setCanPress(false);
//                inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.INVENTORYTYPEERROR.getDesc());
//                returnList.add(inquiryApplyVerifyReurn);
//                continue;
//            }
            }
            if (!CollectionUtils.isEmpty(applyList)) {
                // 超过6次不让催促
                if (applyList.size() >= 6) {
                    inquiryApplyVerifyReurn.setCanPress(false);
                    inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.OVERSIZE.getDesc());
                    returnList.add(inquiryApplyVerifyReurn);
//                    return ResultVo.success(inquiryApplyVerifyReurn);
                    continue;
                }
                if (suppilyList.contains(opsPurchaseorders.getSupplierid()) && applyList.size() >= 3) {
                    inquiryApplyVerifyReurn.setCanPress(false);
                    inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.MANUOVERSIZE.getDesc());
                    returnList.add(inquiryApplyVerifyReurn);
//                    return ResultVo.success(inquiryApplyVerifyReurn);
                    continue;
                }
                // 校验时间间隔
                if (applyList.get(0).getInquiryTime() != null && timeDiff(applyList.get(0).getInquiryTime(),nowDate) < 24) {
                    inquiryApplyVerifyReurn.setCanPress(false);
                    inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.OVERTIME.getDesc());
                    returnList.add(inquiryApplyVerifyReurn);
//                    return ResultVo.success(inquiryApplyVerifyReurn);
                    continue;
                }
                // 校验第8点
                if ("JP".equals(opsPurchaseorders.getSupplierid())) {
                    long levelCount = applyList.stream().filter(a -> "HoT-2".equals(a.getInquiryLevel())).count(); // 校验日本HoT级别
                    if ("HoT-2".equals(applyList.get(0).getInquiryLevel()) && levelCount >= 2) {
                        inquiryApplyVerifyReurn.setCanPress(false);
                        inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.JPOVERSIZE.getDesc());
                        returnList.add(inquiryApplyVerifyReurn);
//                        return ResultVo.success(inquiryApplyVerifyReurn);
                        continue;
                    }
                }
            }
            // 针对中国制造，接单日期是否需要校验 replyOrderDate = null, 且制造接单24小时内，不可催促
            // long mills = Math.abs((new Date()).getTime() - orderitem.getData().getReplyOrderDate().getTime());
            //	long diff = TimeUnit.DAYS.convert(mills, TimeUnit.MILLISECONDS);
            // 中国制造供应商校验,tips 初次查询时进行校验，插入时不再进行制造表的校验
            OpsPurchaseinvoice purchaseinvoiceData = opsRcvdetailMapper.getPurchaseinvoice(opsPurchaseorders.getPono(), opsPurchaseorders.getLineitem());
            if (suppilyList.contains(opsPurchaseorders.getSupplierid()) && InquiryTypeEnum.PURCHASE.getType().equals(type)) {
                // 2024-11-06 当存在制造回复货期时，需要校验replyOrderDate与当前日期时间差，且制造接单24小时内，不可催促
                // bug 16157 WTSR2024001614-中国制造INQ-A问询,制造接单24小时内限制为工作日时间，计算时需跳过周末及节假日
                if (StringUtils.isNotBlank(opsPurchaseorders.getPono())) {
                    if (purchaseinvoiceData != null && purchaseinvoiceData.getReplyorderdate()!=null ) {
                        // 将上一次催促日期解析为日期 和 时间, yyyy-MM-dd HH:mm:ss
                        String lastInquiryTime = DateUtil.dateToDateTimeString(purchaseinvoiceData.getReplyorderdate());
                        // 计算上一次催促日期的 下一个工作日
                        Date manuDiff = tblWorkDayYearMapper.getCNWorkDayDiff(lastInquiryTime, Integer.parseInt(InqbCommonEnum.DIFFDAYVALID.getType()));
                        if (manuDiff != null) {
                            // 按照查询结果拼接为 时分秒格式
                            Date manuDiffTime = DateUtil.stringToDateTime(DateUtil.dateToDateString(manuDiff)+" "+ lastInquiryTime.substring(11));
                            if (DateUtil.isAfterDate(manuDiffTime, nowDate)) { //判断时间差
                                inquiryApplyVerifyReurn.setCanPress(false);
                                inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.MANUOVERTIME.getDesc());
                                returnList.add(inquiryApplyVerifyReurn);
                                continue;
                            }
                        }
                    }
                }
                // 校验制造接单表是否存在数据
                String pono = null;
                String lineitem = null;
                if (orderFno.contains("-")) {
                    pono = orderFno;
                    lineitem = "1";
                } else {
                    if (orderFno.length() != 10) {
                        continue;
                    }
                    pono = orderFno.substring(0, 7);
                    lineitem = orderFno.substring(7);
                }
                String checkRemark = inquiryManuDataService.getCheckRemark(pono, lineitem);
                if (StringUtils.isBlank(checkRemark)) {
                    inquiryApplyVerifyReurn.setCanPress(false);
                    inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.ZHILINGNULL.getDesc());
                    returnList.add(inquiryApplyVerifyReurn);
                    continue;
                } else if (StringUtils.equals("现场", checkRemark)) {
                    // 查询催促次数
                    List<Date> dates = inquiryManuDataService.getCountByNo(orderFno);
                    // 校验是否还有未回复数据
                    if (!CollectionUtils.isEmpty(dates) && dates.stream().anyMatch(Objects::isNull)) {
                        inquiryApplyVerifyReurn.setCanPress(false);
                        inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.MANUNOREPLY.getDesc());
                        returnList.add(inquiryApplyVerifyReurn);
                        continue;
                    }
                } else {
                    inquiryApplyVerifyReurn.setCanPress(false);
                    inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.MANUSTATUSERROR.getDesc());
                    returnList.add(inquiryApplyVerifyReurn);
                    continue;
                }
            }
            // 返信纳期的校验，以及转换
            // bug14504 针对日本返信为以下三个不确定返信时，不允许提交催促申请 4444-04-04、9999-09-09、9900-09-09
            if (StringUtils.isNotBlank(opsPurchaseorders.getSrcDeliveryTime())) {
                Set<String> errorDlvDateList = new HashSet<String>(InquiryDlvDateEnum.errorDlvdateList());
                String dateStr = opsPurchaseorders.getSrcDeliveryTime();
                if (errorDlvDateList.contains(dateStr)) {
                    inquiryApplyVerifyReurn.setCanPress(false);
                    inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.JPDLVDATEERROR.getDesc());
                    returnList.add(inquiryApplyVerifyReurn);
                    continue;
                }
                inquiryApplyVerifyReurn.setDlvmoddateStr(dateStr);
                // 后期如果需要将特殊格式的返信进行转换，如：222222、333333、555555、666666等，转换为 22/22/22、33/33/33
            }
            // 对应委托WTSR2024001329，当供应商为日本且通过接到SG或VN的出库区分时点开始拦截并提示不可催促
            if ("JP".equals(opsPurchaseorders.getSupplierid())) {
                Set<String> jpConvertSet = new HashSet<String>(InquiryVerifyMsg.jpConvertList());
                if (StringUtils.isBlank(purchaseinvoiceData.getProducefactory()) || !jpConvertSet.contains(purchaseinvoiceData.getProducefactory())) {
                    Set<String> jpVnSgSet = new HashSet<String>(InquiryVerifyMsg.jpVnSgList());
                    if (StringUtils.isNotBlank(purchaseinvoiceData.getProducefactory()) && jpVnSgSet.contains(purchaseinvoiceData.getProducefactory())) {
                        inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.JPTRANSGVN.getDesc());
                    } else {
                        inquiryApplyVerifyReurn.setCheckFailureMsg(InquiryVerifyMsg.JPProducefactory.getDesc());
                    }
                    inquiryApplyVerifyReurn.setCanPress(false);
                    returnList.add(inquiryApplyVerifyReurn);
                    continue;
                }
            }
            // bug 14826 当inquiryCount催促次数<=2时，催促级别为INQ-A；当inquiryCount催促次数>2且<=4时，催促级别为HOT-1；当inquiryCount催促次数>4且<=6时，催促级别为HOT-2；
            inquiryApplyVerifyReurn.setInquiryLevel(determineInquiryLevel(opsPurchaseorders.getSupplierid(), inquiryCount+1));
            // 申请号是否需要返回？
            inquiryApplyVerifyReurn.setCanPress(true);
            returnList.add(inquiryApplyVerifyReurn);
//            return ResultVo.success(inquiryApplyVerifyReurn);
        }
        return ResultVo.success(returnList);
    }

    /**
     * 订单催促的校验接口
     * 可以催促时，展示拆分信息列表，不可催促时，前端提示不可催促原因，格式同采购催促校验
     * 订单催促的具体规则如下：
     * 1.可对接单表中的销售订单、国内集团销售订单、样品订单进行整单催促，如果输入的订单类别不在这三种类别中，提示订单类型失败
     * 2.
     * @param ordernoList
     * @param inquiryType
     * @return
     */
    @Override
    public ResultVo<List<InquiryOrderVerifyReurn>> orderInquiryVerify(List<String> ordernoList, String inquiryType) throws Exception {
        List<InquiryOrderVerifyReurn> orderVerifyReurnList = new ArrayList<>();
        InquiryOrderVerifyReurn inquiryOrderVerifyReurn;
        InquiryOrderMasterDto inquiryOrderMaster;
        for(String orderno : ordernoList) {
            inquiryOrderVerifyReurn = new InquiryOrderVerifyReurn();
            inquiryOrderMaster = new InquiryOrderMasterDto();
            inquiryOrderMaster.setOrderNo(orderno);
            // 首先查询接单表,得到接单信息
            List<RcvView> rcvdetails = opsRcvdetailMapper.getRcvViewByOrderno(orderno);
            if (CollectionUtils.isEmpty(rcvdetails)) { //未查询到接单信息时，提示前端错误
                inquiryOrderMaster.setCanPress(false);
                inquiryOrderMaster.setCheckFailureMsg(InquiryVerifyMsg.UNORDERINFO.getDesc());
                inquiryOrderVerifyReurn.setInquiryOrderMaster(inquiryOrderMaster);
                orderVerifyReurnList.add(inquiryOrderVerifyReurn);
                continue;
            }
            RcvView orderRcvdetail = rcvdetails.get(0);
            // 对主单信息进行赋值
            inquiryOrderMaster.setCustomerNo(orderRcvdetail.getCustomerNo());
            inquiryOrderMaster.setUserno(orderRcvdetail.getUserNo());
            inquiryOrderMaster.setEndUser(orderRcvdetail.getEndUser());
            inquiryOrderMaster.setModelNo(orderRcvdetail.getModelNo());
            inquiryOrderMaster.setQuantity(orderRcvdetail.getQuantity());
            if (StringUtils.isNotBlank(orderRcvdetail.getStockType())) {
                inquiryOrderMaster.setStockDesc(OrderStockTypeEnum.getDescFromType(orderRcvdetail.getStockType()));
            }
            if (StringUtils.isNotBlank(orderRcvdetail.getStockCode())) {
                inquiryOrderMaster.setStockDesc(inquiryOrderMaster.getStockDesc()+ '【' + orderRcvdetail.getStockCode() + '】');
            }
            // 出库区分显示，在后台组合号返回前端
            inquiryOrderMaster.setStockCode(orderRcvdetail.getStockCode());
            inquiryOrderMaster.setStockType(orderRcvdetail.getStockType());
            inquiryOrderMaster.setPurchaseNo(orderRcvdetail.getCorderNo());
            inquiryOrderMaster.setOrderStatus(Integer.valueOf(orderRcvdetail.getStatus())); // 订单状态
            inquiryOrderMaster.setOrderStatusDesc(RcvOrderStatusEnum.getEnumByType(orderRcvdetail.getStatus()).getDesc());
            inquiryOrderMaster.setOrderType(String.valueOf(orderRcvdetail.getOrderType()));
            inquiryOrderMaster.setHopeExportDate(orderRcvdetail.getCdlvDate());
            inquiryOrderMaster.setInquiryType(InquiryTypeEnum.ORDER.getType());
            inquiryOrderMaster.setRorderNo(orderRcvdetail.getRorderNo());
            inquiryOrderMaster.setRorderItem(orderRcvdetail.getRorderItem());
            // 可对接单表中的销售订单、国内集团销售订单、样品订单进行整单催促，如果输入的订单类别不在这三种类别中，提示订单类型失败
            if (!(OrderTypeEnum.KEHU.equals(orderRcvdetail.getOrderType().toString()) || OrderTypeEnum.JITUAN.equals(orderRcvdetail.getOrderType().toString()) || OrderTypeEnum.YANGPIN.equals(orderRcvdetail.getOrderType().toString()))) {
                inquiryOrderMaster.setCanPress(false);
                inquiryOrderMaster.setCheckFailureMsg(InquiryVerifyMsg.ORDERTYPEERROR.getDesc()+orderRcvdetail.getOrderType()); // todo 此处需要增加字典翻译
                inquiryOrderVerifyReurn.setInquiryOrderMaster(inquiryOrderMaster);
                orderVerifyReurnList.add(inquiryOrderVerifyReurn);
                continue;
            }
            // todo 对当前订单的状态进行校验，如果状态不是对外采购，提示订单状态失败？ 此处需要完善校验规则
            if (orderRcvdetail.getStatus()> RcvOrderStatusEnum.CKING.getType()) {
                inquiryOrderMaster.setCanPress(false);
                inquiryOrderMaster.setCheckFailureMsg(InquiryVerifyMsg.ORDERSTATUSERROR.getDesc()+orderRcvdetail.getStatus()); // todo 此处需要增加字典翻译
                inquiryOrderVerifyReurn.setInquiryOrderMaster(inquiryOrderMaster);
                orderVerifyReurnList.add(inquiryOrderVerifyReurn);
                continue;
            }
            // 查询订单的分配明细，因为涉及子项的状态，所以改为查询ops_order_assign_result表
            List<OpsOrderAssignResult>  orderAssignResultList = opsAssignReasultMapper.getAssignResult(orderRcvdetail.getRorderNo(), orderRcvdetail.getRorderItem());
            if (CollectionUtils.isEmpty(orderAssignResultList)) {
                inquiryOrderMaster.setCanPress(false);
                inquiryOrderMaster.setCheckFailureMsg(InquiryVerifyMsg.ORDERASSIGNRESULTNULL.getDesc());
                inquiryOrderVerifyReurn.setInquiryOrderMaster(inquiryOrderMaster);
                orderVerifyReurnList.add(inquiryOrderVerifyReurn);
                continue;
            }

            Boolean canInq = false; // 整单是否可催促的标识，默认为fasle,当子项中有任何一项可催时，变更为true,返回前端可以催促
            List<InquiryOrderDetailDto> inquiryOrderDetailDtos = new ArrayList<>();
            InquiryOrderDetailDto inquiryOrderDetailDto;
            // 获取供应商的转换
            Map<String, String> warehouseSupplierMap = getWarehouseSupplier();
            for (OpsOrderAssignResult orderAssignResult : orderAssignResultList) {
                inquiryOrderDetailDto = new InquiryOrderDetailDto();
                inquiryOrderDetailDto.setRorderSplitno(orderAssignResult.getOrderNo()+"-"+orderAssignResult.getOrderItem().toString()); // 带订单拆分单号的订单拆分项号，可能与采购的项号不一致，所以单独设置字段,只用于前端显示
                if (orderAssignResultList.size() > 1) { // 存在拆分时，拼接采购单号
                    //todo 没有拆分单号, 暂时用seqNo单号代替  by C12961
                    // 如果存在拆分单号，则拼接拆分单号
                    inquiryOrderDetailDto.setRorderSplitno(inquiryOrderDetailDto.getRorderSplitno().concat("-"+orderAssignResult.getSeqno().toString()));
                }
                // 对拆分结果进行赋值
                inquiryOrderDetailDto.setStockCode(orderAssignResult.getStockCode());
                inquiryOrderDetailDto.setStockType(orderAssignResult.getStockType());
                inquiryOrderDetailDto.setInventoryTypeCode(orderAssignResult.getInventoryTypeCode());
                String stockTypeDesc = OrderStockTypeEnum.getDescFromType(inquiryOrderDetailDto.getStockType());
                String inventoryTypeDesc = StringUtils.isBlank(inquiryOrderDetailDto.getInventoryTypeCode())? orderAssignResult.getInventoryTypeCode(): InventoryTypeEnum.parse(inquiryOrderDetailDto.getInventoryTypeCode()).getDesc();
                String stockCodeDesc = StringUtils.isBlank(inquiryOrderDetailDto.getStockCode())? inquiryOrderDetailDto.getStockCode() : warehouseSupplierMap.get(inquiryOrderDetailDto.getStockCode());
                // 子项的状态及状态信息需要翻译
                if (StringUtils.isNotBlank(inquiryOrderDetailDto.getInventoryTypeCode())) {
                    inquiryOrderDetailDto.setInventoryDesc(stockTypeDesc + "【" + stockCodeDesc + '-' + inventoryTypeDesc + "】" );
                } else if (StringUtils.isNotBlank(inquiryOrderDetailDto.getStockCode())) {
                    inquiryOrderDetailDto.setInventoryDesc(stockTypeDesc + "【" + stockCodeDesc + "】" );
                } else if (StringUtils.isNotBlank(inquiryOrderDetailDto.getStockType())) {
                    inquiryOrderDetailDto.setInventoryDesc(stockTypeDesc);
                }

                inquiryOrderDetailDto.setModelNo(orderAssignResult.getModelno());
                inquiryOrderDetailDto.setQuantity(orderAssignResult.getQuantity());
                // todo 状态无法获取  by C12961
                //String statusDesc = OrderStatusEnum.getDescByState(orderStatus.getStatus());
                //inquiryOrderDetailDto.setOrderStatusDesc(StringUtils.isNotBlank(statusDesc)? statusDesc :orderStatus.getStatus());
                // 循环校验，是否存在采购，如果为采购，再去校验采购校验的信息，返回给前端 是否可催促
                if (StringUtils.isBlank(orderAssignResult.getStockType())) {
                    log.error("订单分配接口返回结果出库类别为空，订单号为：" + orderno );
                    inquiryOrderDetailDto.setCanPress(false);
                    inquiryOrderDetailDto.setCheckFailureMsg(InquiryVerifyMsg.ORDERASSIGNRESULTNULL.getDesc());
                    inquiryOrderDetailDtos.add(inquiryOrderDetailDto);
                    continue;
                }
                inquiryOrderDetailDto.setCanPress(false); // 先设置默认的值，后面根据校验结果进行修改
                inquiryOrderDetailDto.setCheckFailureMsg(InquiryVerifyMsg.STOCKTYPENOCG.getDesc());
                // 校验出库类别是否为采购,供应商不为空,且采购单号不为空
                if (orderAssignResult.getStockType().equalsIgnoreCase(InqbCommonEnum.STOCKTYPECG.getType()) && StringUtils.isNotBlank(orderAssignResult.getAssociateNo())) {
                    // 如果存在采购单，构建采购单号
                    String purchaseOrderNo =  orderAssignResult.getAssociateNo()+"-"+orderAssignResult.getAssociateNoItem();
                    if (orderAssignResult.getAssociateNoSplitNo() != 0) {
                        purchaseOrderNo = purchaseOrderNo.concat("-" + orderAssignResult.getAssociateNoSplitNo());
                    }
                    ResultVo<List<InquiryApplyVerifyReurn>> purchaseResultVo;
                    try {
                        // 调用采购催促校验，校验采购单是否可催促
                        purchaseResultVo = purchaseInquiryVerify(Arrays.asList(purchaseOrderNo), InquiryTypeEnum.PURCHASE.getType());
                    } catch (Exception e) {
                        inquiryOrderDetailDto.setCanPress(false);
                        inquiryOrderDetailDto.setCheckFailureMsg(e.getMessage());
                        inquiryOrderDetailDtos.add(inquiryOrderDetailDto);
                        continue;
                    }
                    InquiryApplyVerifyReurn inquiryPurchaseVerify = purchaseResultVo.getData().get(0);  // 获取校验结果
                    inquiryOrderDetailDto.setCanPress(inquiryPurchaseVerify.getCanPress());
                    inquiryOrderDetailDto.setCheckFailureMsg(inquiryPurchaseVerify.getCheckFailureMsg());
                    // 不可催促时，只返回错误原因，可催促时，返回实际的催促信息
                    if (inquiryPurchaseVerify.getCanPress()) {
                        canInq = true; // 设置整单可催促状态为可催促
                        // 子项赋值，每个可催促子项可当做一个采购催促订单，为新增时的赋值做准备
                        inquiryOrderDetailDto.setOrderNo(inquiryPurchaseVerify.getOrderNo());
                        inquiryOrderDetailDto.setCustomerNo(inquiryPurchaseVerify.getCustomerNo());
                        inquiryOrderDetailDto.setEndUser(inquiryPurchaseVerify.getEndUser());
                        inquiryOrderDetailDto.setHopeExportDate(inquiryPurchaseVerify.getHopeExportDate());
                        inquiryOrderDetailDto.setDlvModdate(inquiryPurchaseVerify.getDlvModdate());
                        inquiryOrderDetailDto.setDlvmoddateStr(inquiryPurchaseVerify.getDlvmoddateStr());
                        inquiryOrderDetailDto.setPurchaseNo(inquiryPurchaseVerify.getPurchaseNo());
                        inquiryOrderDetailDto.setReplyDept(inquiryPurchaseVerify.getReplyDept());
                        inquiryOrderDetailDto.setInquiryLevel(inquiryPurchaseVerify.getInquiryLevel());
                        inquiryOrderDetailDto.setInquiryCount(inquiryPurchaseVerify.getInquiryCount());
                        inquiryOrderDetailDto.setSupplierId(inquiryPurchaseVerify.getSupplierId());
                        inquiryOrderDetailDto.setPono(inquiryPurchaseVerify.getPono());
                        inquiryOrderDetailDto.setLineitem(inquiryPurchaseVerify.getLineitem());
                        inquiryOrderDetailDto.setRorderNo(inquiryPurchaseVerify.getRorderNo());
                        inquiryOrderDetailDto.setRorderItem(inquiryPurchaseVerify.getRorderItem());
                        inquiryOrderDetailDto.setSplititemno(inquiryPurchaseVerify.getSplititemno());
                        inquiryOrderDetailDto.setSupplierOrderNo(inquiryPurchaseVerify.getSupplierOrderNo()); //增加供应商接单号
                        inquiryOrderDetailDto.setOrderType(inquiryPurchaseVerify.getOrderType());
                        inquiryOrderDetailDto.setSmccode(inquiryPurchaseVerify.getSmccode());
                        inquiryOrderDetailDto.setPurchasetype(inquiryPurchaseVerify.getPurchasetype());
                        inquiryOrderDetailDto.setReplyDept(inquiryPurchaseVerify.getReplyDept());
                        inquiryOrderDetailDto.setOrderStatus(inquiryPurchaseVerify.getOrderStatus()); // 子项采购单的状态
                        if (StringUtils.isNotBlank(inquiryPurchaseVerify.getOrderStatusDesc()) && StringUtils.isNotBlank(inquiryOrderDetailDto.getOrderStatusDesc())) {
                            inquiryOrderDetailDto.setOrderStatusDesc(inquiryOrderDetailDto.getOrderStatusDesc().concat("-").concat(inquiryPurchaseVerify.getOrderStatusDesc()));
                        }
                        if (StringUtils.isNotBlank(inquiryPurchaseVerify.getOrderStatusDesc()) && StringUtils.isBlank(inquiryOrderDetailDto.getOrderStatusDesc())) {
                            inquiryOrderDetailDto.setOrderStatusDesc(inquiryPurchaseVerify.getOrderStatusDesc());
                        }
                    }
                }
                inquiryOrderDetailDtos.add(inquiryOrderDetailDto);
            }
            inquiryOrderVerifyReurn.setInquiryOrderDetails(inquiryOrderDetailDtos);
            // 校验整单的是否可催促状态，子项一个可催，则整单可催
            if (!canInq) {
                inquiryOrderMaster.setCanPress(false);
                inquiryOrderMaster.setCheckFailureMsg(InquiryVerifyMsg.ITEMNONEINQ.getDesc());
                inquiryOrderVerifyReurn.setInquiryOrderMaster(inquiryOrderMaster);
                orderVerifyReurnList.add(inquiryOrderVerifyReurn);
                continue;
            }
            inquiryOrderMaster.setCanPress(true);
            inquiryOrderVerifyReurn.setInquiryOrderMaster(inquiryOrderMaster);
            orderVerifyReurnList.add(inquiryOrderVerifyReurn);
        }
        return ResultVo.success(orderVerifyReurnList);
    }

    @Override
    public ResultVo<String> orderInquiryAdd(List<InquiryApplyAddParam> list) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.failure(InquiryVerifyMsg.LISTNULL.getDesc());
        }
        StringBuilder errMsg = new StringBuilder(); // 错误订单原因返回
        StringBuilder detailErrMsg = new StringBuilder(); // 错误订单原因返回
        int result;
        List<String> ordernos;
        InquiryOrderMasterDto inquiryOrderMasterDto;
        InquiryOrderVerifyReurn inquiryApplyVerifyReurn;
        InquiryOrderApplyAddDto inquiryApplyAddDto;
        List<InquiryOrderApplyAddDto> inquiryApplyAddDtoList = new ArrayList<>();
        InqAExpPoRequestDto inqAExpPoRequestDto; //货期校验实体
        // 循环校验订单是否可催
        for (InquiryApplyAddParam inquiryApplyAddParam : list) {
            if ("sales".equals(inquiryApplyAddParam.getDataSources())) { // 门户的催促单时,校验是否存在批次号，以便后期能够回传
                if (StringUtils.isBlank(inquiryApplyAddParam.getBatchNo())) {
                    errMsg.append(inquiryApplyAddParam.getOrderNo()).append(InquiryVerifyMsg.BATCHNONULL.getDesc());
                    continue;
                }
            }
            if (inquiryApplyAddParam.getHopeDeliveryDate() == null) {
                errMsg.append(inquiryApplyAddParam.getOrderNo()).append(InquiryVerifyMsg.HOPEDATENULL.getDesc());
                continue;
            }
            if (StringUtils.isBlank(inquiryApplyAddParam.getInquiryReasonType()) || StringUtils.isBlank(inquiryApplyAddParam.getInquiryReason())) {
                errMsg.append(inquiryApplyAddParam.getOrderNo()).append(InquiryVerifyMsg.REASONNULL.getDesc());
                continue;
            }
            // 调用订单催促校验方法
            ordernos = new ArrayList<>(Collections.singletonList(inquiryApplyAddParam.getOrderNo()));
            ResultVo<List<InquiryOrderVerifyReurn>> verifyResult = orderInquiryVerify(ordernos, InquiryTypeEnum.ORDER.getType());
            if (!verifyResult.isSuccess()) {
                errMsg.append(inquiryApplyAddParam.getOrderNo()).append(verifyResult.getMessage());
                continue;
            }
            inquiryApplyVerifyReurn = verifyResult.getData().get(0);
            inquiryOrderMasterDto = inquiryApplyVerifyReurn.getInquiryOrderMaster();
            if (!inquiryOrderMasterDto.getCanPress()) { //订单校验失败时，返回催促失败原因
                errMsg.append(inquiryApplyAddParam.getOrderNo()).append(inquiryOrderMasterDto.getCheckFailureMsg());
                continue;
            }
            // 如果订单整单校验通过时，调用货期计算接口，计算子项货期
            inqAExpPoRequestDto = new InqAExpPoRequestDto();
            inqAExpPoRequestDto.setOrderNo(inquiryOrderMasterDto.getRorderNo());
            inqAExpPoRequestDto.setOrderItem(inquiryOrderMasterDto.getRorderItem());
            inqAExpPoRequestDto.setExpDate(inquiryApplyAddParam.getHopeDeliveryDate());
            log.info("INQ-A订单催促申请接入参数：" + JSON.toJSONString(inquiryApplyAddParam));
            // 调用wm的子项货期计算接口
            ResultVo<List<ExpPoDto>> expdateResults = wmCKRuleFeignApi.inqAGetPoExpDate(inqAExpPoRequestDto);
            log.info("INQ-A订单催促申请订单号：" + inquiryApplyAddParam.getOrderNo() + "订单货期分配接口返回结果：" + JSON.toJSONString(expdateResults));
            if (!expdateResults.isSuccess()) {
                errMsg.append(inquiryApplyAddParam.getOrderNo()).append(inquiryOrderMasterDto.getCheckFailureMsg());
                continue;
            }
            List<ExpPoDto> resultsData = expdateResults.getData();
            if (CollectionUtils.isEmpty(resultsData)) {
                errMsg.append(inquiryApplyAddParam.getOrderNo()).append(InquiryVerifyMsg.CALEXPDATENULL.getDesc());
                continue;
            }
            List<InquiryOrderDetailDto> inquiryOrderDetailDtos = inquiryApplyVerifyReurn.getInquiryOrderDetails();
            // 循环对子项的分配结果进行校验,其中有一个校验通过时，即可进行催促
            Boolean canInq = false;
            for (InquiryOrderDetailDto inquiryOrderDetailDto : inquiryOrderDetailDtos) {
                if (inquiryOrderDetailDto.getCanPress()) {
                    if (inquiryOrderDetailDto.getSupplierId().equalsIgnoreCase("JP")) { // bug14826,发送日本时，增加对备注信息的校验，不能包含中文及中文字符，只能是半角英数字
                        // inquiryApplyAddParam.getInquiryRemark().matches("^[a-zA-Z0-9]+$") 校验是否全部为英文数字
                        // bug 14853，INQA追加校验修改
                        if (StringUtils.isNotBlank(inquiryApplyAddParam.getInquiryRemark()) && inquiryApplyAddParam.getInquiryRemark().matches(InquiryConstants.AS400_CN_RESTRICT)) {
                            log.info("inquiryApplyAddParam.getInquiryRemark()={}", inquiryApplyAddParam.getInquiryRemark());
                            detailErrMsg.append(inquiryOrderDetailDto.getOrderNo()).append(InquiryVerifyMsg.AS400LANGUAGEERROR.getDesc());
                            inquiryOrderDetailDto.setCanPress(false);
                            inquiryOrderDetailDto.setCheckFailureMsg(InquiryVerifyMsg.AS400LANGUAGEERROR.getDesc());
                            continue;
                        }
                    }
                    // 根据型号，去resultsData中匹配子项货期
                    ExpPoDto expPoDto = resultsData.stream().filter(exp -> exp.getModelNo().equalsIgnoreCase(inquiryOrderDetailDto.getModelNo())).findFirst().orElse(null);
                    if (expPoDto == null) {
                        detailErrMsg.append(inquiryOrderDetailDto.getOrderNo()).append(InquiryVerifyMsg.CALEXPDATENULL.getDesc());
                        inquiryOrderDetailDto.setCanPress(false);
                        inquiryOrderDetailDto.setCheckFailureMsg(InquiryVerifyMsg.CALEXPDATENULL.getDesc());
                        continue;
                    }
                    inquiryOrderDetailDto.setHopeDeliveryDate(expPoDto.getExpPoDate());
                    // bug14504 INQA对接日本修改,节假日校验针对所有供应商
                    Set<String> workDaySet = new HashSet<String>(InquiryVerifyMsg.manuGzJPSuppilyList());
                    if (workDaySet.contains(inquiryOrderDetailDto.getSupplierId())) {
                        // 校验 催促货期是否为节假日，是节假日时不能催促
                        InquiryWorkdayCondition inquiryWorkdayCondition = new InquiryWorkdayCondition();
                        inquiryWorkdayCondition.setDay(inquiryOrderDetailDto.getHopeDeliveryDate());
                        inquiryWorkdayCondition.setCountry(inquiryOrderDetailDto.getSupplierId().equalsIgnoreCase("JP") ? inquiryOrderDetailDto.getSupplierId() : "CN");
                        ResultVo<Boolean> workDay = getWorkday(inquiryWorkdayCondition);
                        if (workDay.isSuccess() && workDay.getData()) {
                            detailErrMsg.append(inquiryOrderDetailDto.getOrderNo()).append(InquiryVerifyMsg.JPWORKDAYERROR.getDesc());
                            inquiryOrderDetailDto.setCanPress(false);
                            inquiryOrderDetailDto.setCheckFailureMsg(InquiryVerifyMsg.JPWORKDAYERROR.getDesc());
                            continue;
                        }
                    }
                    List<OpsInquiryReasonParamConfig> inquiryReasonParamConfigs = inquiryReasonParamConfigMapper.getReasonParamConfigBySuppily(inquiryApplyAddParam.getInquiryReasonType(), inquiryOrderDetailDto.getSupplierId());
                    if (!inquiryReasonParamConfigs.isEmpty()) {
                        // bug14504,催促原因参数的校验,需要联查参数配置表校验
                        if (StringUtils.isBlank(inquiryApplyAddParam.getInquiryReasonParam())) {
                            detailErrMsg.append(inquiryOrderDetailDto.getOrderNo()).append(InquiryVerifyMsg.REASONPARAMNULL.getDesc());
                            inquiryOrderDetailDto.setCanPress(false);
                            inquiryOrderDetailDto.setCheckFailureMsg(InquiryVerifyMsg.REASONPARAMNULL.getDesc());
                            continue;
                        }
                        OpsInquiryReasonParamConfig opsInquiryReasonParamConfig = inquiryReasonParamConfigs.get(0);
                        // 校验是否固定长度，是的话校验参数值是否符合要求,否则校验最大值是否符合要求
                        if (opsInquiryReasonParamConfig.getIsFixedLength() && Integer.parseInt(opsInquiryReasonParamConfig.getDataLength()) != inquiryApplyAddParam.getInquiryReasonParam().length()) {
                            detailErrMsg.append(inquiryOrderDetailDto.getOrderNo()).append(InquiryVerifyMsg.REASONPARAMUPLENGTH.getDesc());
                            inquiryOrderDetailDto.setCanPress(false);
                            inquiryOrderDetailDto.setCheckFailureMsg(InquiryVerifyMsg.REASONPARAMUPLENGTH.getDesc());
                            continue;
                        }
                        if (!opsInquiryReasonParamConfig.getIsFixedLength() && inquiryApplyAddParam.getInquiryReasonParam().length() > Integer.parseInt(opsInquiryReasonParamConfig.getDataLength())) {
                            detailErrMsg.append(inquiryOrderDetailDto.getOrderNo()).append(InquiryVerifyMsg.REASONPARAMUPLENGTH.getDesc());
                            inquiryOrderDetailDto.setCanPress(false);
                            inquiryOrderDetailDto.setCheckFailureMsg(InquiryVerifyMsg.REASONPARAMUPLENGTH.getDesc());
                            continue;
                        }
                    }
                    canInq = true; //全部校验通过后，设置canPress为true
                }
            }
            if (!canInq) {
                errMsg.append(detailErrMsg.toString());
                continue; // continue还是break？
            }
            //验证和写入分离，当全部校验通过时，再插入，否则返回错误
            inquiryApplyAddDto = new InquiryOrderApplyAddDto();
            inquiryApplyAddDto.setInquiryApplyAddParam(inquiryApplyAddParam);
            inquiryApplyAddDto.setInquiryOrderVerifyReurn(inquiryApplyVerifyReurn);
            inquiryApplyAddDtoList.add(inquiryApplyAddDto);
        }
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return ResultVo.failure("处理失败的催促订单:" + errMsg.toString());
        }
        // 校验完成，执行插入
        for (InquiryOrderApplyAddDto addDto : inquiryApplyAddDtoList) {
            try {
                // 写入申请主表 和 采购关联表
                inquiryOrderInsert(addDto.getInquiryApplyAddParam(), addDto.getInquiryOrderVerifyReurn());
            } catch (Exception e) {
                log.error("订单催促申请插入失败，原因：{}", e.getMessage());
                return ResultVo.failure("订单催促申请插入失败，订单号:" + addDto.getInquiryApplyAddParam().getOrderNo() +",原因：" + e.getMessage());
            }
        }
        return ResultVo.success("订单催促申请插入成功，共计写入" + inquiryApplyAddDtoList.size() + "项！");
    }

    /**
     * 订单催促，写入方法
     * @param inquiryApplyAddParam
     * @param inquiryOrderVerifyReurn
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @DS("opsdb")
    public int inquiryOrderInsert(InquiryApplyAddParam inquiryApplyAddParam, InquiryOrderVerifyReurn inquiryOrderVerifyReurn) {
        int result = 0;
        InquiryOrderMasterDto inquiryOrderMasterDto = inquiryOrderVerifyReurn.getInquiryOrderMaster();
        List<InquiryOrderDetailDto> inquiryOrderDetailDtoList = inquiryOrderVerifyReurn.getInquiryOrderDetails();
        String applyno = inquiryApplyAddParam.getInquiryApplyNo(); // 申请号，门户的申请号记录在 source_apply_no字段中
        if ("sales".equals(inquiryApplyAddParam.getDataSources())) { // 门户的催促单时,校验是否存在批次号，以便后期能够回传
            inquiryOrderMasterDto.setSourcesApplyNo(applyno);
            applyno = generateApplyNo(inquiryApplyAddParam.getInquiryType());
        }
        if (StringUtils.isBlank(applyno)) {
            applyno = generateApplyNo(inquiryApplyAddParam.getInquiryType());
        } else {
            // 校验在表中是否存在相同申请号，有的话重新再获取申请号
            if (StringUtils.isNotBlank(inquiryApplyMapper.getApplyNoUnique(applyno))) {
                applyno = generateApplyNo(inquiryApplyAddParam.getInquiryType());
            }
        }
        OpsInquiryApply opsInquiryApply =  BeanCopyUtil.copy(inquiryOrderMasterDto, InquiryOrderMasterDto.class);//主表的实体转换
        opsInquiryApply.setInquiryStatus(InquiryStatusEnum.DAICHULI.getType());
        opsInquiryApply.setBatchNo(inquiryApplyAddParam.getBatchNo());
        opsInquiryApply.setDataSources(inquiryApplyAddParam.getDataSources());
        opsInquiryApply.setHopeDeliveryDate(inquiryApplyAddParam.getHopeDeliveryDate());
        opsInquiryApply.setInquiryReasonType(inquiryApplyAddParam.getInquiryReasonType());
        opsInquiryApply.setInquiryReason(inquiryApplyAddParam.getInquiryReason());
        opsInquiryApply.setInquiryDescription(inquiryApplyAddParam.getInquiryDescription());
        opsInquiryApply.setInquiryRemark(inquiryApplyAddParam.getInquiryRemark());
        opsInquiryApply.setInquiryType(inquiryApplyAddParam.getInquiryType());
        opsInquiryApply.setInquiryTime(inquiryApplyAddParam.getInquiryTime());
        opsInquiryApply.setInquiryDept(inquiryApplyAddParam.getInquiryDept());
        opsInquiryApply.setInquiryUser(inquiryApplyAddParam.getInquiryUser());
        opsInquiryApply.setInquiryUserName(inquiryApplyAddParam.getInquiryUserName());
        opsInquiryApply.setInquiryReasonParam(inquiryApplyAddParam.getInquiryReasonParam());
        opsInquiryApply.setInquiryApplyNo(applyno);
        opsInquiryApply.setCreateUser(inquiryApplyAddParam.getInquiryUser());
        opsInquiryApply.setCreateTime(new Date());

        OpsInquiryDetail opsOrderInquiryDetail;
        OpsInquiryPoHandle opsOrderInquiryPoHandle;
        int itemNo = 0;
        Boolean detailCompletion = false; // 判断子项是否已经生成完毕
        // 子项号顺序生成(可催促排序)
        inquiryOrderDetailDtoList.sort(Comparator.comparing(InquiryOrderDetailDto::getCanPress).reversed());
        for (InquiryOrderDetailDto inquiryOrderDetailDto : inquiryOrderDetailDtoList) {
            opsOrderInquiryDetail = new OpsInquiryDetail();
            itemNo++;
            // 写入申请子表
            opsOrderInquiryDetail.setInquiryApplyNo(applyno);
            opsOrderInquiryDetail.setItemNo(itemNo);
            opsOrderInquiryDetail.setFptype(InquiryTypeEnum.PURCHASE.getType().equals(inquiryApplyAddParam.getInquiryType()) ? InquiryTypeEnum.PURCHASE.getDesc() : InquiryTypeEnum.ORDER.getDesc());
            opsOrderInquiryDetail.setOrderNo(inquiryOrderDetailDto.getOrderNo());
            opsOrderInquiryDetail.setRorderSplitno(inquiryOrderDetailDto.getRorderSplitno()); // 保留订单的拆分单号明细信息
            opsOrderInquiryDetail.setModelNo(inquiryOrderDetailDto.getModelNo());
            opsOrderInquiryDetail.setQuantity(inquiryOrderDetailDto.getQuantity());
            opsOrderInquiryDetail.setSupplierId(inquiryOrderDetailDto.getSupplierId());
            opsOrderInquiryDetail.setHopeExportDate(inquiryOrderDetailDto.getHopeExportDate());
            opsOrderInquiryDetail.setHopeDeliveryDate(inquiryOrderDetailDto.getHopeDeliveryDate());
            opsOrderInquiryDetail.setDlvModdate(inquiryOrderDetailDto.getDlvModdate());
            opsOrderInquiryDetail.setReplyDept(inquiryOrderDetailDto.getReplyDept());
            opsOrderInquiryDetail.setSupplierOrderNo(inquiryOrderDetailDto.getSupplierOrderNo()); // 初始写入供应商接单号
            if (inquiryOrderDetailDto.getOrderStatus() != null) {
                opsOrderInquiryDetail.setOrderStatus(inquiryOrderDetailDto.getOrderStatus().toString());
            }
            // bug14435 INQA对接日本修改,增加pono字段
            opsOrderInquiryDetail.setPono(inquiryOrderDetailDto.getPono());
            opsOrderInquiryDetail.setLineitem(inquiryOrderDetailDto.getLineitem());
            opsOrderInquiryDetail.setRorderNo(inquiryOrderDetailDto.getRorderNo());
            opsOrderInquiryDetail.setRorderItem(inquiryOrderDetailDto.getRorderItem());
            opsOrderInquiryDetail.setSplititemno(inquiryOrderDetailDto.getSplititemno());
            // bug14490 INQA对接日本修改,需要新增传输字段
            opsOrderInquiryDetail.setSmccode(inquiryOrderDetailDto.getSmccode());
            opsOrderInquiryDetail.setPurchasetype(inquiryOrderDetailDto.getPurchasetype());
            opsOrderInquiryDetail.setInquiryLevel(inquiryOrderDetailDto.getInquiryLevel());
            opsOrderInquiryDetail.setCreateTime(new Date());
            opsOrderInquiryDetail.setCreateUser(inquiryApplyAddParam.getInquiryUser());
            // 校验子项是否可催促，可催促时生成任务号，并写入
            if (inquiryOrderDetailDto.getCanPress()) {
                // 生成任务号
                String taskno = generatorTaskNo(inquiryOrderDetailDto.getSupplierId(), inquiryApplyAddParam.getInquiryType());
                opsOrderInquiryDetail.setTaskNo(taskno);
                // 写入inquiryPoHandle 采购处理表中
                opsOrderInquiryPoHandle = new OpsInquiryPoHandle();
                opsOrderInquiryPoHandle.setInquiryApplyNo(opsOrderInquiryDetail.getInquiryApplyNo()); // 写入po处理表的申请号，applyno + itemNo
                opsOrderInquiryPoHandle.setItemNo(opsOrderInquiryDetail.getItemNo());// 写入po处理表的申请号，applyno + itemNo
                opsOrderInquiryPoHandle.setTaskNo(taskno);
                opsOrderInquiryPoHandle.setInquiryStatus(InquiryStatusEnum.DAICHULI.getType());
                opsOrderInquiryPoHandle.setOrderNo(inquiryOrderDetailDto.getOrderNo());
                opsOrderInquiryPoHandle.setModelNo(inquiryOrderDetailDto.getModelNo());
                opsOrderInquiryPoHandle.setQuantity(inquiryOrderDetailDto.getQuantity());
                opsOrderInquiryPoHandle.setHopeDeliveryDate(inquiryOrderDetailDto.getHopeDeliveryDate());
                opsOrderInquiryPoHandle.setCustomerNo(inquiryOrderDetailDto.getCustomerNo());
                opsOrderInquiryPoHandle.setEndUser(inquiryOrderDetailDto.getEndUser());
                opsOrderInquiryPoHandle.setPurchaseNo(inquiryOrderDetailDto.getPurchaseNo());
                opsOrderInquiryPoHandle.setOrderStatus(inquiryOrderDetailDto.getOrderStatus());
                opsOrderInquiryPoHandle.setHopeExportDate(inquiryOrderDetailDto.getHopeExportDate());
                opsOrderInquiryPoHandle.setDlvModdate(inquiryOrderDetailDto.getDlvModdate());
                opsOrderInquiryPoHandle.setSupplierId(inquiryOrderDetailDto.getSupplierId());
                opsOrderInquiryPoHandle.setReplyDept(inquiryOrderDetailDto.getReplyDept());
                opsOrderInquiryPoHandle.setCreateTime(new Date());
                opsOrderInquiryPoHandle.setCreateUser(inquiryApplyAddParam.getInquiryUser());
                opsOrderInquiryPoHandle.setSupplierOrderNo(inquiryOrderDetailDto.getSupplierOrderNo()); // 初始写入供应商接单号
                // bug14435 INQA对接日本修改,增加pono字段
                opsOrderInquiryPoHandle.setPono(inquiryOrderDetailDto.getPono());
                opsOrderInquiryPoHandle.setLineitem(inquiryOrderDetailDto.getLineitem());
                opsOrderInquiryPoHandle.setRorderNo(inquiryOrderDetailDto.getRorderNo());
                opsOrderInquiryPoHandle.setRorderItem(inquiryOrderDetailDto.getRorderItem());
                opsOrderInquiryPoHandle.setSplititemno(inquiryOrderDetailDto.getSplititemno());
                // bug14490 INQA对接日本修改,需要新增传输字段
                opsOrderInquiryPoHandle.setSmccode(inquiryOrderDetailDto.getSmccode());
                opsOrderInquiryPoHandle.setPurchasetype(inquiryOrderDetailDto.getPurchasetype());
                opsOrderInquiryPoHandle.setInquiryLevel(inquiryOrderDetailDto.getInquiryLevel());

                opsOrderInquiryPoHandle.setInquiryReasonType(inquiryApplyAddParam.getInquiryReasonType());
                opsOrderInquiryPoHandle.setInquiryReason(inquiryApplyAddParam.getInquiryReason());
                opsOrderInquiryPoHandle.setInquiryDescription(inquiryApplyAddParam.getInquiryDescription());
                opsOrderInquiryPoHandle.setInquiryRemark(inquiryApplyAddParam.getInquiryRemark());
                opsOrderInquiryPoHandle.setInquiryType(inquiryApplyAddParam.getInquiryType());
                opsOrderInquiryPoHandle.setInquiryTime(inquiryApplyAddParam.getInquiryTime());
                opsOrderInquiryPoHandle.setInquiryDept(inquiryApplyAddParam.getInquiryDept());
                opsOrderInquiryPoHandle.setInquiryUser(inquiryApplyAddParam.getInquiryUser());
                opsOrderInquiryPoHandle.setInquiryUserName(inquiryApplyAddParam.getInquiryUserName());
                opsOrderInquiryPoHandle.setInquiryReasonParam(inquiryApplyAddParam.getInquiryReasonParam());
                List<OpsInquiryPoHandle> opsInquiryPoHandles = new ArrayList<>(Collections.singletonList(opsOrderInquiryPoHandle));
                inquiryPoHandleMapper.inquiryPoHandleInsert(opsInquiryPoHandles); // 写入handle表
                // 主表中，补充子项相关信息，选取子项中采购部分的信息展示
                opsInquiryApply.setDlvModdate(inquiryOrderDetailDto.getDlvModdate());
                opsInquiryApply.setSupplierOrderNo(inquiryOrderDetailDto.getSupplierOrderNo());
                opsInquiryApply.setReplyDept(inquiryOrderDetailDto.getReplyDept());
                opsInquiryApply.setPono(inquiryOrderDetailDto.getPono());
                opsInquiryApply.setLineitem(inquiryOrderDetailDto.getLineitem());
                opsInquiryApply.setRorderNo(inquiryOrderDetailDto.getRorderNo());
                opsInquiryApply.setRorderItem(inquiryOrderDetailDto.getRorderItem());
                opsInquiryApply.setSplititemno(inquiryOrderDetailDto.getSplititemno());
                opsInquiryApply.setSmccode(inquiryOrderDetailDto.getSmccode());
                opsInquiryApply.setPurchasetype(inquiryOrderDetailDto.getPurchasetype());
                opsInquiryApply.setInquiryLevel(inquiryOrderDetailDto.getInquiryLevel());
            }
            inquiryDetailMapper.inquiryDetailInsert(opsOrderInquiryDetail);
        }
        List<OpsInquiryApply> opsInquiryApplies = new ArrayList<>(Collections.singletonList(opsInquiryApply));
        return inquiryApplyMapper.inquiryApplyInsert(opsInquiryApplies);
    }

    /**
     *
     * 订单催促，在前端输入催促货期后，调用此接口进行校验
     * 计算出的子项货期中，有一项不可能满足，则返回提示信息，提示用户修改货期
     * @param requestList
     * @return
     * @throws Exception
     */
    @Override
    public ResultVo<List<InquiryOrderMasterDto>> deliveryDateInquiryVerify(List<InquiryApplyAddParam> requestList) throws Exception {
        if (CollectionUtils.isEmpty(requestList)) {
            return ResultVo.failure(InquiryVerifyMsg.LISTNULL.getDesc());
        }
        List<InquiryOrderMasterDto> inquiryOrderVerifyReurnList = new ArrayList<>();
        InquiryOrderMasterDto inquiryOrderMaster;
        InqAExpPoRequestDto inqAExpPoRequest;
        for (InquiryApplyAddParam inquiryApplyAddParam : requestList) {
            inquiryOrderMaster = new InquiryOrderMasterDto();
            // 循环校验订单货期
            if (StringUtils.isBlank(inquiryApplyAddParam.getOrderNo()) || inquiryApplyAddParam.getHopeDeliveryDate() == null) {
                inquiryOrderMaster.setCanPress(false);
                inquiryOrderMaster.setCheckFailureMsg(InquiryVerifyMsg.ORDERNODELIVERYNULL.getDesc());
                inquiryOrderVerifyReurnList.add(inquiryOrderMaster);
                continue;
            }
//            // bug14504,催促日期的校验，不能超过当前日期+一年
//            ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
//            ZonedDateTime hopeDelivery = inquiryApplyAddParam.getHopeDeliveryDate().toInstant().atZone(ZoneId.systemDefault());
//            // Use Duration.between to calculate the difference between two dates
//            long daysDifference = Duration.between(now, hopeDelivery).toDays();
//            if (daysDifference > 365) {
//                inquiryOrderMaster.setCanPress(false);
//                inquiryOrderMaster.setCheckFailureMsg(inquiryApplyAddParam.getOrderNo().concat(InquiryVerifyMsg.OVER1YEAR.getDesc()));
//                inquiryOrderVerifyReurnList.add(inquiryOrderMaster);
//                continue;
//            }
            inquiryOrderMaster.setOrderNo(inquiryApplyAddParam.getOrderNo());
            inquiryOrderMaster.setHopeDeliveryDate(inquiryApplyAddParam.getHopeDeliveryDate());
            // 查询接单信息，获取主单号及项号
            List<RcvView> rcvdetails = opsRcvdetailMapper.getRcvViewByOrderno(inquiryApplyAddParam.getOrderNo());
            if (CollectionUtils.isEmpty(rcvdetails)) { //未查询到接单信息时，提示前端错误
                inquiryOrderMaster.setCanPress(false);
                inquiryOrderMaster.setCheckFailureMsg(InquiryVerifyMsg.UNORDERINFO.getDesc());
                inquiryOrderVerifyReurnList.add(inquiryOrderMaster);
                continue;
            }
            RcvView orderRcvdetail = rcvdetails.get(0);
            inqAExpPoRequest = new InqAExpPoRequestDto();
            inqAExpPoRequest.setOrderNo(orderRcvdetail.getRorderNo());
            inqAExpPoRequest.setOrderItem(orderRcvdetail.getRorderItem());
            inqAExpPoRequest.setExpDate(inquiryApplyAddParam.getHopeDeliveryDate());
            // 调用wm的子项货期计算接口
            ResultVo<List<ExpPoDto>> results = wmCKRuleFeignApi.inqAGetPoExpDate(inqAExpPoRequest);
            if (!results.isSuccess()) {
                inquiryOrderMaster.setCanPress(false);
                inquiryOrderMaster.setCheckFailureMsg(results.getMessage());
                inquiryOrderVerifyReurnList.add(inquiryOrderMaster);
                continue;
            }
            List<ExpPoDto> resultsData = results.getData();
            if (CollectionUtils.isEmpty(resultsData)) {
                inquiryOrderMaster.setCanPress(false);
                inquiryOrderMaster.setCheckFailureMsg(InquiryVerifyMsg.CALEXPDATENULL.getDesc());
                inquiryOrderVerifyReurnList.add(inquiryOrderMaster);
                continue;
            }
            // 查询订单的分配明细，因为涉及子项的状态，所以改为查询ops_order_assign_result表
            List<OpsOrderAssignResult>  opsOrderAssignResultList = opsAssignReasultMapper.getAssignResult(orderRcvdetail.getRorderNo(), orderRcvdetail.getRorderItem());
            Set<String> workDaySet = new HashSet<String>(InquiryVerifyMsg.manuGzJPSuppilyList());
            // 循环校验计算的货期
            // todo 前端关于采购货期的校验，是否都需要加到后端中,是否大于当前日期，等等
            for (ExpPoDto expPoDto : resultsData) {
                if (expPoDto.getExpPoDate().before(new Date())) { // 如果计算的发货日小于系统当天日期，则整单不可催促
                    inquiryOrderMaster.setCanPress(false);
                    inquiryOrderMaster.setCheckFailureMsg("订单催促计算的采购货期:" + DateUtil.dateToString(expPoDto.getExpPoDate())+InquiryVerifyMsg.CALEXPDATEBEFOR.getDesc());
                    break;
                }
                // 超过一年校验
                ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
                ZonedDateTime hopeDelivery = expPoDto.getExpPoDate().toInstant().atZone(ZoneId.systemDefault());
                long daysDifference = Duration.between(now, hopeDelivery).toDays();
                if (daysDifference > 365) {
                    inquiryOrderMaster.setCanPress(false);
                    inquiryOrderMaster.setCheckFailureMsg(inquiryApplyAddParam.getOrderNo().concat(InquiryVerifyMsg.OVER1YEAR.getDesc()));
                    break;
                }
                // 从opsOrderAssignResultList中根据型号，匹配供应商等信息
                OpsOrderAssignResult opsOrderAssignResult = opsOrderAssignResultList.stream().filter(item -> item.getModelno().equals(expPoDto.getModelNo())).findFirst().orElse(null);
                if (opsOrderAssignResult != null && StringUtils.isNotBlank(opsOrderAssignResult.getStockCode()) && workDaySet.contains(opsOrderAssignResult.getStockCode())) {
                    // 校验 催促货期是否为节假日，是节假日时不能催促
                    InquiryWorkdayCondition inquiryWorkdayCondition = new InquiryWorkdayCondition();
                    inquiryWorkdayCondition.setDay(expPoDto.getExpPoDate());
                    inquiryWorkdayCondition.setCountry(opsOrderAssignResult.getStockCode().equalsIgnoreCase("JP") ? opsOrderAssignResult.getStockCode() : "CN");
                    ResultVo<Boolean> workDay = getWorkday(inquiryWorkdayCondition);
                    if (workDay.isSuccess() && workDay.getData()) {
                        log.info("校验订单:" + inquiryApplyAddParam.getOrderNo() + "的货期:" + DateUtil.dateToString(expPoDto.getExpPoDate()) + "为节假日");
                        inquiryOrderMaster.setCanPress(false);
                        inquiryOrderMaster.setCheckFailureMsg("订单催促计算的采购货期:" + DateUtil.dateToString(expPoDto.getExpPoDate()).concat(InquiryVerifyMsg.JPWORKDAYERROR.getDesc()));
                        break;
                    }
                }
                // todo 返信纳期的校验
            }
            if (inquiryOrderMaster.getCanPress() == null) {
                inquiryOrderMaster.setCanPress(true);
            }
            inquiryOrderVerifyReurnList.add(inquiryOrderMaster);
        }
        return ResultVo.success(inquiryOrderVerifyReurnList);
    }


    /**
     * 门户的催促新增调用接口
     *
     * @param list
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @DS("opsdb")
    public ResultVo<String> purchaseInquiryAdd(List<InquiryApplyAddParam> list) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.failure(InquiryVerifyMsg.LISTNULL.getDesc());
        }
        StringBuilder errMsg = new StringBuilder(); // 错误订单原因返回
        int result;
        List<String> ordernos;
        InquiryApplyVerifyReurn inquiryApplyVerifyReurn;
        InquiryApplyAddDto inquiryApplyAddDto;
        List<InquiryApplyAddDto> inquiryApplyAddDtoList = new ArrayList<>();
        for (InquiryApplyAddParam inquiryApplyAddParam : list) {
            if ("sales".equals(inquiryApplyAddParam.getDataSources())) {
                // 校验变更类别是否为空
                if (StringUtils.isBlank(inquiryApplyAddParam.getBatchNo())) {
                    errMsg.append(inquiryApplyAddParam.getOrderNo()).append(InquiryVerifyMsg.BATCHNONULL.getDesc());
                    continue;
                }
            }
            if (inquiryApplyAddParam.getHopeDeliveryDate() == null) {
                errMsg.append(inquiryApplyAddParam.getOrderNo()).append(InquiryVerifyMsg.HOPEDATENULL.getDesc());
                continue;
            }
            if (StringUtils.isBlank(inquiryApplyAddParam.getInquiryReasonType()) || StringUtils.isBlank(inquiryApplyAddParam.getInquiryReason())) {
                errMsg.append(inquiryApplyAddParam.getOrderNo()).append(InquiryVerifyMsg.REASONNULL.getDesc());
                continue;
            }
            // bug14504,催促日期的校验，不能超过当前日期+一年
            ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
            ZonedDateTime hopeDelivery = inquiryApplyAddParam.getHopeDeliveryDate().toInstant().atZone(ZoneId.systemDefault());
            // Use Duration.between to calculate the difference between two dates
            long daysDifference = Duration.between(now, hopeDelivery).toDays();
            if (daysDifference > 365) {
                errMsg.append(inquiryApplyAddParam.getOrderNo()).append(InquiryVerifyMsg.OVER1YEAR.getDesc());
                continue;
            }
            // 调用催促校验方法
            ordernos = new ArrayList<>(Collections.singletonList(inquiryApplyAddParam.getOrderNo()));
            ResultVo<List<InquiryApplyVerifyReurn>> verifyResult = purchaseInquiryVerify(ordernos, "0");
            if (!verifyResult.isSuccess()) {
                errMsg.append(inquiryApplyAddParam.getOrderNo()).append(verifyResult.getMessage());
                continue;
            }
            inquiryApplyVerifyReurn = verifyResult.getData().get(0);
            // bug13618,校验失败的订单，不可进行提交
            if (!inquiryApplyVerifyReurn.getCanPress()) {
                errMsg.append(inquiryApplyAddParam.getOrderNo()).append(inquiryApplyVerifyReurn.getCheckFailureMsg());
                continue;
            }
            if (inquiryApplyVerifyReurn.getSupplierId().equalsIgnoreCase("JP")) { // bug14826,发送日本时，增加对备注信息的校验，不能包含中文及中文字符，只能是半角英数字
                // inquiryApplyAddParam.getInquiryRemark().matches("^[a-zA-Z0-9]+$") 校验是否全部为英文数字
                // bug 14853，INQA追加校验修改
                if (StringUtils.isNotBlank(inquiryApplyAddParam.getInquiryRemark())) {
                    if (inquiryApplyAddParam.getInquiryRemark().matches(InquiryConstants.AS400_CN_RESTRICT)) {
                        log.info("发送AS400时，催促备注信息不能包含中文字符，inquiryApplyAddParam.getInquiryRemark()={}", inquiryApplyAddParam.getInquiryRemark());
                        errMsg.append(inquiryApplyAddParam.getOrderNo()).append(InquiryVerifyMsg.AS400LANGUAGEERROR.getDesc());
                        continue;
                    }
                    //bug 16422 校验供应商为日本时的备注长度，不能超过30个字符
                    if (inquiryApplyAddParam.getInquiryRemark().length() > 30) {
                        log.info("发送AS400时，催促备注信息不能超过30个字符，inquiryApplyAddParam.getInquiryRemark()={}", inquiryApplyAddParam.getInquiryRemark());
                        errMsg.append(inquiryApplyAddParam.getOrderNo()).append(InquiryVerifyMsg.AS400REMARKOVERLENGTH.getDesc());
                    }
                }
            }
            // bug14504 INQA对接日本修改,节假日校验针对所有供应商
            Set<String> workDaySet = new HashSet<String>(InquiryVerifyMsg.manuGzJPSuppilyList());
            if (workDaySet.contains(inquiryApplyVerifyReurn.getSupplierId())) {
                // 校验 催促货期是否为节假日，是节假日时不能催促
                InquiryWorkdayCondition inquiryWorkdayCondition = new InquiryWorkdayCondition();
                inquiryWorkdayCondition.setDay(inquiryApplyAddParam.getHopeDeliveryDate());
                inquiryWorkdayCondition.setCountry(inquiryApplyVerifyReurn.getSupplierId().equalsIgnoreCase("JP") ? inquiryApplyVerifyReurn.getSupplierId() : "CN");
                ResultVo<Boolean> workDay = getWorkday(inquiryWorkdayCondition);
                if (workDay.isSuccess() && workDay.getData()) {
                    errMsg.append(inquiryApplyAddParam.getOrderNo()).append(InquiryVerifyMsg.JPWORKDAYERROR.getDesc());
                    continue;
                }
            }
            List<OpsInquiryReasonParamConfig> inquiryReasonParamConfigs = inquiryReasonParamConfigMapper.getReasonParamConfigBySuppily(inquiryApplyAddParam.getInquiryReasonType(), inquiryApplyVerifyReurn.getSupplierId());
            if (!inquiryReasonParamConfigs.isEmpty()) {
                // bug14504,催促原因参数的校验,需要联查参数配置表校验
                if (StringUtils.isBlank(inquiryApplyAddParam.getInquiryReasonParam())) {
                    errMsg.append(inquiryApplyAddParam.getOrderNo()).append(InquiryVerifyMsg.REASONPARAMNULL.getDesc());
                    continue;
                }
                OpsInquiryReasonParamConfig opsInquiryReasonParamConfig = inquiryReasonParamConfigs.get(0);
                // 校验是否固定长度，是的话校验参数值是否符合要求,否则校验最大值是否符合要求
                if (opsInquiryReasonParamConfig.getIsFixedLength() && Integer.parseInt(opsInquiryReasonParamConfig.getDataLength()) != inquiryApplyAddParam.getInquiryReasonParam().length()) {
                    errMsg.append(inquiryApplyAddParam.getOrderNo()).append(InquiryVerifyMsg.REASONPARAMUPLENGTH.getDesc());
                    continue;
                }
                if (!opsInquiryReasonParamConfig.getIsFixedLength() && inquiryApplyAddParam.getInquiryReasonParam().length() > Integer.parseInt(opsInquiryReasonParamConfig.getDataLength())) {
                    errMsg.append(inquiryApplyAddParam.getOrderNo()).append(InquiryVerifyMsg.REASONPARAMUPLENGTH.getDesc());
                    continue;
                }
            }
            // 2024-6-27 验证和写入分离，当全部校验通过时，再插入，否则返回错误
            inquiryApplyAddDto = new InquiryApplyAddDto();
            inquiryApplyAddDto.setInquiryApplyAddParam(inquiryApplyAddParam);
            inquiryApplyAddDto.setInquiryApplyVerifyReurn(inquiryApplyVerifyReurn);
            inquiryApplyAddDtoList.add(inquiryApplyAddDto);
        }
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return ResultVo.failure("处理失败的催促订单:" + errMsg.toString());
        }
        // 校验完成，执行插入
        InquiryApplyAddParam inquiryApplyAddParamInsert;
        InquiryApplyVerifyReurn inquiryApplyVerifyReurnInsert;
        for (InquiryApplyAddDto addDto : inquiryApplyAddDtoList) {
            inquiryApplyAddParamInsert = addDto.getInquiryApplyAddParam();
            inquiryApplyVerifyReurnInsert = addDto.getInquiryApplyVerifyReurn();
            // 2024-6-27 验证和写入分离，当全部校验通过时，再插入，否则返回错误
            String applyno = inquiryApplyAddParamInsert.getInquiryApplyNo();
            // todo 待门户对接上线时，再开放这部分代码,不影响中途程序发布
//            if ("sales".equals(inquiryApplyAddParamInsert.getDataSources())) { // 门户的催促单时,校验是否存在批次号，以便后期能够回传
//                inquiryApplyVerifyReurnInsert.setSourcesApplyNo(applyno);
//                applyno = generateApplyNo(inquiryApplyAddParamInsert.getInquiryType());
//            }
            if (StringUtils.isBlank(applyno)) {
                // todo 生成申请单号,修改为从redis中读取
                applyno = generateApplyNo(inquiryApplyAddParamInsert.getInquiryType());
            } else {
                // 校验在表中是否存在相同申请号，有的话重新再获取申请号
                if (StringUtils.isNotBlank(inquiryApplyMapper.getApplyNoUnique(applyno))) {
                    applyno = generateApplyNo(inquiryApplyAddParamInsert.getInquiryType());
                }
            }
            inquiryApplyVerifyReurnInsert.setInquiryApplyNo(applyno);
            // 写入申请主表 和 采购关联表
            inquiryTableInsert(inquiryApplyAddParamInsert, inquiryApplyVerifyReurnInsert);
        }
        return ResultVo.success("催促申请插入成功，共计写入" + inquiryApplyAddDtoList.size() + "项！");
    }


    /**
     * 催促新增接口,新方法
     * 2024-09-25 新增，根据催促类别区分，订单催促、采购催促区分开，返回实体为两个合并的
     * @param inquiryApplyAddParam
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @DS("opsdb")
    public ResultVo<InquirySalesApplyReurn> salesInquiryAddNew(InquiryApplyAddParam inquiryApplyAddParam) throws Exception {
        if (inquiryApplyAddParam == null || StringUtils.isBlank(inquiryApplyAddParam.getInquiryType())) {
            return ResultVo.failure(InquiryVerifyMsg.LISTNULL.getDesc());
        }
        // 校验门户的申请子项号是否为空
        if (inquiryApplyAddParam.getItemno() == null || inquiryApplyAddParam.getItemno() == 0) {
            return ResultVo.failure(inquiryApplyAddParam.getOrderNo().concat(InquiryVerifyMsg.ITEMNONULL.getDesc()));
        }
        String orginApplyno = inquiryApplyAddParam.getInquiryApplyNo();
        inquiryApplyAddParam.setInquiryApplyNo(inquiryApplyAddParam.getInquiryApplyNo() + "-" + inquiryApplyAddParam.getItemno()); // 2024-03-26 增加门户申请子项号,-拼接显示在前端
        InquirySalesApplyReurn returnVo = new InquirySalesApplyReurn(); //构建返回实体
        returnVo.setInquiryType(inquiryApplyAddParam.getInquiryType());
        List<InquiryApplyAddParam> callParam = new ArrayList<>(Collections.singletonList(inquiryApplyAddParam)); // 调用新增接口
        if (inquiryApplyAddParam.getInquiryType().equals(InquiryTypeEnum.PURCHASE.getType())) {
            InquiryApplyVerifyReurn purchaseReturnVo = new InquiryApplyVerifyReurn(); //构建返回实体
            purchaseReturnVo.setInquiryApplyNo(inquiryApplyAddParam.getInquiryApplyNo()); // todo 回传门户时，返回门户原始申请号信息，是否需要改变
            purchaseReturnVo.setSourcesApplyNo(inquiryApplyAddParam.getInquiryApplyNo()); // 返回门户时，源申请号设置为门户申请号
            purchaseReturnVo.setItemno(inquiryApplyAddParam.getItemno());
            purchaseReturnVo.setBatchNo(inquiryApplyAddParam.getBatchNo());
            purchaseReturnVo.setOrderNo(inquiryApplyAddParam.getOrderNo());
            purchaseReturnVo.setModelNo(inquiryApplyAddParam.getModelNo());
            purchaseReturnVo.setQuantity(inquiryApplyAddParam.getQuantity());
            purchaseReturnVo.setHopeDeliveryDate(inquiryApplyAddParam.getHopeDeliveryDate());
            purchaseReturnVo.setInquiryReasonType(inquiryApplyAddParam.getInquiryReasonType());
            purchaseReturnVo.setInquiryDept(inquiryApplyAddParam.getInquiryDept());
            purchaseReturnVo.setInquiryUser(inquiryApplyAddParam.getInquiryUser());
            purchaseReturnVo.setInquiryUserName(inquiryApplyAddParam.getInquiryUserName());
            ResultVo<String> resultVo = purchaseInquiryAdd(callParam);
            if (resultVo.isSuccess()) {
                // 新增成功时，返回成功状态
                purchaseReturnVo.setStatus(SalesInquiryReturnEnum.ADDSUCCESS.getType());
                purchaseReturnVo.setStatusDescription(SalesInquiryReturnEnum.ADDSUCCESS.getDesc());
                // 2024-03-26新增成功时，返回催促次数
                List<OpsInquiryApply> applyList = inquiryApplyMapper.selectRecentApply(inquiryApplyAddParam.getOrderNo());
                if (!CollectionUtils.isEmpty(applyList)) {
                    purchaseReturnVo.setInquiryCount(applyList.size());
                    purchaseReturnVo.setReplyNo(applyList.get(0).getReplyNo());
                }
            } else {
                // 新增失败时，返回失败状态及原因
                purchaseReturnVo.setStatus(SalesInquiryReturnEnum.ADDERROR.getType());
                purchaseReturnVo.setStatusDescription(resultVo.getMessage());
            }
            returnVo.setInquiryApplyVerifyReurn(purchaseReturnVo);
        }
        else {
            ResultVo<String> resultVo = orderInquiryAdd(callParam); // 调用订单催促新增接口
            InquiryOrderReurn orderReturnVo = new InquiryOrderReurn();
            InquiryOrderMasterDto inquiryOrderMasterDto = new InquiryOrderMasterDto();
            if (resultVo.isSuccess()) {
                // 查询分配的明细
                List<InquiryOrderMasterDto> opsInquiryApplies = inquiryApplyMapper.getInquiryApplyByParameter(inquiryApplyAddParam);
                // 查询子项列表
                List<InquiryOrderDetailReturnDto> opsDetailList =  inquiryDetailMapper.selectInquiryDetailReturn(opsInquiryApplies.get(0).getInquiryApplyNo());
                if (CollectionUtils.isEmpty(opsDetailList) || CollectionUtils.isEmpty(opsDetailList)) {
                    // 子项查询失败时，返回处理信息为处理失败
                    inquiryOrderMasterDto.setSourcesApplyNo(inquiryApplyAddParam.getInquiryApplyNo()); // 返回门户时，源申请号设置为门户申请号
                    inquiryOrderMasterDto.setStatus(SalesInquiryReturnEnum.ADDERROR.getType());
                    inquiryOrderMasterDto.setStatusDescription("未查找到对应的申请数据，请重试");
                } else {
                    inquiryOrderMasterDto = opsInquiryApplies.get(0); // 主表转换为返回实体信息
                    inquiryOrderMasterDto.setStatus(SalesInquiryReturnEnum.ADDSUCCESS.getType());
                    inquiryOrderMasterDto.setStatusDescription(SalesInquiryReturnEnum.ADDSUCCESS.getDesc());
                    // todo 催促次数统计时，采购单催促与订单催促的次数统计在一起吗？
                    List<OpsInquiryApply> applyList = inquiryApplyMapper.selectRecentApply(inquiryApplyAddParam.getOrderNo());
                    if (!CollectionUtils.isEmpty(applyList)) {
                        inquiryOrderMasterDto.setInquiryCount(applyList.size());
                    }
                    orderReturnVo.setInquiryOrderDetails(opsDetailList); // 设置子项信息
                }
            } else {
                // 新增失败时，返回失败状态及原因
                inquiryOrderMasterDto.setInquiryApplyNo(inquiryApplyAddParam.getInquiryApplyNo()); // todo 回传门户时，返回门户原始申请号信息，是否需要改变
                inquiryOrderMasterDto.setSourcesApplyNo(inquiryApplyAddParam.getInquiryApplyNo()); // 返回门户时，源申请号设置为门户申请号
                inquiryOrderMasterDto.setBatchNo(inquiryApplyAddParam.getBatchNo());
                inquiryOrderMasterDto.setOrderNo(inquiryApplyAddParam.getOrderNo());
                inquiryOrderMasterDto.setModelNo(inquiryApplyAddParam.getModelNo());
                inquiryOrderMasterDto.setQuantity(inquiryApplyAddParam.getQuantity());
                inquiryOrderMasterDto.setHopeDeliveryDate(inquiryApplyAddParam.getHopeDeliveryDate());
                inquiryOrderMasterDto.setInquiryReasonType(inquiryApplyAddParam.getInquiryReasonType());
                inquiryOrderMasterDto.setInquiryDept(inquiryApplyAddParam.getInquiryDept());
                inquiryOrderMasterDto.setInquiryUser(inquiryApplyAddParam.getInquiryUser());
                inquiryOrderMasterDto.setInquiryUserName(inquiryApplyAddParam.getInquiryUserName());
                inquiryOrderMasterDto.setStatus(SalesInquiryReturnEnum.ADDERROR.getType());
                inquiryOrderMasterDto.setStatusDescription(resultVo.getMessage());
            }
            orderReturnVo.setInquiryOrderMaster(inquiryOrderMasterDto); // 设置主单信息
            returnVo.setInquiryOrderReurn(orderReturnVo);
        }
        return ResultVo.success(returnVo);
    }

    /**
     * 催促新增接口
     * @param inquiryApplyAddParam
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @DS("opsdb")
    public ResultVo<InquiryApplyVerifyReurn> salesInquiryAdd(InquiryApplyAddParam inquiryApplyAddParam) throws Exception {
        if (inquiryApplyAddParam == null) {
            return ResultVo.failure(InquiryVerifyMsg.LISTNULL.getDesc());
        }
        // 校验门户的申请子项号是否为空
        if (inquiryApplyAddParam.getItemno() == null || inquiryApplyAddParam.getItemno() == 0) {
            return ResultVo.failure(inquiryApplyAddParam.getOrderNo().concat(InquiryVerifyMsg.ITEMNONULL.getDesc()));
        }
        String orginApplyno = inquiryApplyAddParam.getInquiryApplyNo();
        // 2024-03-26 增加门户申请子项号,-拼接显示在前端
        inquiryApplyAddParam.setInquiryApplyNo(inquiryApplyAddParam.getInquiryApplyNo() + "-" + inquiryApplyAddParam.getItemno());
        InquiryApplyVerifyReurn returnVo = new InquiryApplyVerifyReurn(); //构建返回实体
        // 调用新增接口
        List<InquiryApplyAddParam> callParam = new ArrayList<>(Collections.singletonList(inquiryApplyAddParam));
        ResultVo<String> resultVo = purchaseInquiryAdd(callParam);
        returnVo.setInquiryApplyNo(orginApplyno); // 回传门户时，返回门户原始申请号
        returnVo.setItemno(inquiryApplyAddParam.getItemno());
        returnVo.setBatchNo(inquiryApplyAddParam.getBatchNo());
        returnVo.setOrderNo(inquiryApplyAddParam.getOrderNo());
        returnVo.setModelNo(inquiryApplyAddParam.getModelNo());
        returnVo.setQuantity(inquiryApplyAddParam.getQuantity());
        returnVo.setHopeDeliveryDate(inquiryApplyAddParam.getHopeDeliveryDate());
        returnVo.setInquiryReasonType(inquiryApplyAddParam.getInquiryReasonType());
        returnVo.setInquiryDept(inquiryApplyAddParam.getInquiryDept());
        returnVo.setInquiryUser(inquiryApplyAddParam.getInquiryUser());
        returnVo.setInquiryUserName(inquiryApplyAddParam.getInquiryUserName());
        if (resultVo.isSuccess()) {
            // 新增成功时，返回成功状态
            returnVo.setStatus(SalesInquiryReturnEnum.ADDSUCCESS.getType());
            returnVo.setStatusDescription(SalesInquiryReturnEnum.ADDSUCCESS.getDesc());
            // 2024-03-26新增成功时，返回催促次数
            List<OpsInquiryApply> applyList = inquiryApplyMapper.selectRecentApply(inquiryApplyAddParam.getOrderNo());
            if (!CollectionUtils.isEmpty(applyList)) {
                returnVo.setInquiryCount(applyList.size());
            }
        } else {
            // 新增失败时，返回失败状态及原因
            returnVo.setStatus(SalesInquiryReturnEnum.ADDERROR.getType());
            returnVo.setStatusDescription(resultVo.getMessage());
        }
        return ResultVo.success(returnVo);
    }


    private int inquiryTableInsert(InquiryApplyAddParam inquiryApplyAddParam, InquiryApplyVerifyReurn inquiryApplyVerifyReurn) {
        // 生成任务号
        String taskno = generatorTaskNo(inquiryApplyVerifyReurn.getSupplierId(), inquiryApplyAddParam.getInquiryType());
        int result;
        OpsInquiryApply opsInquiryApply = new OpsInquiryApply();
        opsInquiryApply.setInquiryStatus(InquiryStatusEnum.DAICHULI.getType());
        opsInquiryApply.setBatchNo(inquiryApplyAddParam.getBatchNo());
        opsInquiryApply.setOrderNo(inquiryApplyAddParam.getOrderNo());
        opsInquiryApply.setDataSources(inquiryApplyAddParam.getDataSources());
        opsInquiryApply.setQuantity(inquiryApplyAddParam.getQuantity());
        opsInquiryApply.setHopeDeliveryDate(inquiryApplyAddParam.getHopeDeliveryDate());
        opsInquiryApply.setInquiryReasonType(inquiryApplyAddParam.getInquiryReasonType());
        opsInquiryApply.setInquiryReason(inquiryApplyAddParam.getInquiryReason());
        opsInquiryApply.setInquiryDescription(inquiryApplyAddParam.getInquiryDescription());
        opsInquiryApply.setInquiryRemark(inquiryApplyAddParam.getInquiryRemark());
        opsInquiryApply.setInquiryType(inquiryApplyAddParam.getInquiryType());
        opsInquiryApply.setInquiryTime(inquiryApplyAddParam.getInquiryTime());
        opsInquiryApply.setInquiryDept(inquiryApplyAddParam.getInquiryDept());
        opsInquiryApply.setInquiryUser(inquiryApplyAddParam.getInquiryUser());
        opsInquiryApply.setInquiryUserName(inquiryApplyAddParam.getInquiryUserName());
        // 申请号，由ops端生成
        opsInquiryApply.setInquiryApplyNo(inquiryApplyVerifyReurn.getInquiryApplyNo());
        opsInquiryApply.setModelNo(inquiryApplyVerifyReurn.getModelNo());
        opsInquiryApply.setCustomerNo(inquiryApplyVerifyReurn.getCustomerNo());
        opsInquiryApply.setEndUser(inquiryApplyVerifyReurn.getEndUser());
        opsInquiryApply.setPurchaseNo(inquiryApplyVerifyReurn.getPurchaseNo());
        opsInquiryApply.setOrderStatus(inquiryApplyVerifyReurn.getOrderStatus());
        opsInquiryApply.setHopeExportDate(inquiryApplyVerifyReurn.getHopeExportDate());
        opsInquiryApply.setDlvModdate(inquiryApplyVerifyReurn.getDlvModdate());
        opsInquiryApply.setCreateTime(new Date());
        opsInquiryApply.setCreateUser(inquiryApplyAddParam.getInquiryUser());
        opsInquiryApply.setSupplierOrderNo(inquiryApplyVerifyReurn.getSupplierOrderNo());
        opsInquiryApply.setReplyDept(inquiryApplyVerifyReurn.getReplyDept());
        opsInquiryApply.setOrderType(inquiryApplyVerifyReurn.getOrderType());
        // bug14435 INQA对接日本修改,增加pono字段
        opsInquiryApply.setPono(inquiryApplyVerifyReurn.getPono());
        opsInquiryApply.setLineitem(inquiryApplyVerifyReurn.getLineitem());
        opsInquiryApply.setRorderNo(inquiryApplyVerifyReurn.getRorderNo());
        opsInquiryApply.setRorderItem(inquiryApplyVerifyReurn.getRorderItem());
        opsInquiryApply.setSplititemno(inquiryApplyVerifyReurn.getSplititemno());
        // bug14490 INQA对接日本修改,需要新增传输字段
        opsInquiryApply.setSmccode(inquiryApplyVerifyReurn.getSmccode());
        opsInquiryApply.setPurchasetype(inquiryApplyVerifyReurn.getPurchasetype());
        opsInquiryApply.setInquiryReasonParam(inquiryApplyAddParam.getInquiryReasonParam());
        // bug14826,增加催促级别的推送
        opsInquiryApply.setInquiryLevel(inquiryApplyVerifyReurn.getInquiryLevel());
        opsInquiryApply.setSourcesApplyNo(inquiryApplyVerifyReurn.getSourcesApplyNo());
        List<OpsInquiryApply> list = new ArrayList<>();
        list.add(opsInquiryApply);
        result = inquiryApplyMapper.inquiryApplyInsert(list);
        if (result > 0) {
            // 写入申请子表
            OpsInquiryDetail opsInquiryDetail = new OpsInquiryDetail();
            opsInquiryDetail.setInquiryApplyNo(inquiryApplyVerifyReurn.getInquiryApplyNo());
            opsInquiryDetail.setTaskNo(taskno);
            // 子表的项号 默认为1
            opsInquiryDetail.setItemNo(1);
            // 先查询子表中是否存在 同一申请号的单据,存在的话 子项号+1
            List<OpsInquiryDetail> inquiryDetails = inquiryDetailMapper.selectInquiryDetail(inquiryApplyVerifyReurn.getInquiryApplyNo());
            if (!CollectionUtils.isEmpty(inquiryDetails)) {
                opsInquiryDetail.setItemNo(inquiryDetails.size() + 1);
            }
            opsInquiryDetail.setFptype(InquiryTypeEnum.PURCHASE.getType().equals(inquiryApplyAddParam.getInquiryType()) ? InquiryTypeEnum.PURCHASE.getDesc() : InquiryTypeEnum.ORDER.getDesc());
            opsInquiryDetail.setReplyDept(inquiryApplyVerifyReurn.getReplyDept());
            opsInquiryDetail.setOrderNo(inquiryApplyAddParam.getOrderNo());
            opsInquiryDetail.setCreateTime(new Date());
            opsInquiryDetail.setCreateUser("system");
            opsInquiryDetail.setSupplierOrderNo(inquiryApplyVerifyReurn.getSupplierOrderNo()); // 初始写入供应商接单号
            // bug14435 INQA对接日本修改,增加pono字段
            opsInquiryDetail.setPono(inquiryApplyVerifyReurn.getPono());
            opsInquiryDetail.setLineitem(inquiryApplyVerifyReurn.getLineitem());
            opsInquiryDetail.setRorderNo(inquiryApplyVerifyReurn.getRorderNo());
            opsInquiryDetail.setRorderItem(inquiryApplyVerifyReurn.getRorderItem());
            opsInquiryDetail.setSplititemno(inquiryApplyVerifyReurn.getSplititemno());
            // bug14490 INQA对接日本修改,需要新增传输字段
            opsInquiryDetail.setSmccode(inquiryApplyVerifyReurn.getSmccode());
            opsInquiryDetail.setPurchasetype(inquiryApplyVerifyReurn.getPurchasetype());
            opsInquiryDetail.setInquiryLevel(inquiryApplyVerifyReurn.getInquiryLevel());
            inquiryDetailMapper.inquiryDetailInsert(opsInquiryDetail);
            // 写入inquiryPoHandle 采购处理表中
            OpsInquiryPoHandle opsInquiryPoHandle = new OpsInquiryPoHandle();
            opsInquiryPoHandle.setTaskNo(taskno);
            opsInquiryPoHandle.setInquiryStatus(InquiryStatusEnum.DAICHULI.getType());
            opsInquiryPoHandle.setOrderNo(inquiryApplyAddParam.getOrderNo());
            opsInquiryPoHandle.setQuantity(inquiryApplyAddParam.getQuantity());
            opsInquiryPoHandle.setHopeDeliveryDate(inquiryApplyAddParam.getHopeDeliveryDate());
            opsInquiryPoHandle.setInquiryReasonType(inquiryApplyAddParam.getInquiryReasonType());
            opsInquiryPoHandle.setInquiryReason(inquiryApplyAddParam.getInquiryReason());
            opsInquiryPoHandle.setInquiryDescription(inquiryApplyAddParam.getInquiryDescription());
            opsInquiryPoHandle.setInquiryRemark(inquiryApplyAddParam.getInquiryRemark());
            opsInquiryPoHandle.setInquiryType(inquiryApplyAddParam.getInquiryType());
            opsInquiryPoHandle.setInquiryTime(inquiryApplyAddParam.getInquiryTime());
            opsInquiryPoHandle.setInquiryDept(inquiryApplyAddParam.getInquiryDept());
            opsInquiryPoHandle.setInquiryUser(inquiryApplyAddParam.getInquiryUser());
            opsInquiryPoHandle.setInquiryUserName(inquiryApplyAddParam.getInquiryUserName());

            opsInquiryPoHandle.setInquiryApplyNo(inquiryApplyVerifyReurn.getInquiryApplyNo());
            opsInquiryPoHandle.setItemNo(opsInquiryDetail.getItemNo());
            opsInquiryPoHandle.setModelNo(inquiryApplyVerifyReurn.getModelNo());
            opsInquiryPoHandle.setCustomerNo(inquiryApplyVerifyReurn.getCustomerNo());
            opsInquiryPoHandle.setEndUser(inquiryApplyVerifyReurn.getEndUser());
            opsInquiryPoHandle.setPurchaseNo(inquiryApplyVerifyReurn.getPurchaseNo());
            opsInquiryPoHandle.setOrderStatus(inquiryApplyVerifyReurn.getOrderStatus());
            opsInquiryPoHandle.setHopeExportDate(inquiryApplyVerifyReurn.getHopeExportDate());
            opsInquiryPoHandle.setDlvModdate(inquiryApplyVerifyReurn.getDlvModdate());
            opsInquiryPoHandle.setSupplierId(inquiryApplyVerifyReurn.getSupplierId());
            opsInquiryPoHandle.setReplyDept(inquiryApplyVerifyReurn.getReplyDept());
            opsInquiryPoHandle.setCreateTime(new Date());
            opsInquiryPoHandle.setCreateUser(inquiryApplyAddParam.getInquiryUser());
            opsInquiryPoHandle.setSupplierOrderNo(inquiryApplyVerifyReurn.getSupplierOrderNo()); // 初始写入供应商接单号
            // bug14435 INQA对接日本修改,增加pono字段
            opsInquiryPoHandle.setPono(inquiryApplyVerifyReurn.getPono());
            opsInquiryPoHandle.setLineitem(inquiryApplyVerifyReurn.getLineitem());
            opsInquiryPoHandle.setRorderNo(inquiryApplyVerifyReurn.getRorderNo());
            opsInquiryPoHandle.setRorderItem(inquiryApplyVerifyReurn.getRorderItem());
            opsInquiryPoHandle.setSplititemno(inquiryApplyVerifyReurn.getSplititemno());
            // bug14490 INQA对接日本修改,需要新增传输字段
            opsInquiryPoHandle.setSmccode(inquiryApplyVerifyReurn.getSmccode());
            opsInquiryPoHandle.setPurchasetype(inquiryApplyVerifyReurn.getPurchasetype());
            opsInquiryPoHandle.setInquiryReasonParam(inquiryApplyAddParam.getInquiryReasonParam());
            opsInquiryPoHandle.setInquiryLevel(inquiryApplyVerifyReurn.getInquiryLevel());
            List<OpsInquiryPoHandle> opsInquiryPoHandles = new ArrayList<>(Collections.singletonList(opsInquiryPoHandle));
            result = inquiryPoHandleMapper.inquiryPoHandleInsert(opsInquiryPoHandles);
        }
        return result;
    }

    /**
     * 生成申请单号
     * 采购催促 PO+年月日+5位连编
     * 订单催促 DD+年月日+5位连编
     * 四位连编生成规则？
     */
    @Override
    public String generateApplyNo(String inquiryType) {
        String inquiryCode = InquiryTypeEnum.PURCHASE.getType().equals(inquiryType) ? InquiryTypeEnum.PURCHASE.getDesc() : InquiryTypeEnum.ORDER.getDesc();
        String localDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        String key = InquiryTypeEnum.PURCHASE.getType().equals(inquiryType) ? "ops:inquiry:applyno" : "ops:inquiry:orderapplyno";
        String applnoReturn = inquiryApplyMapper.getMaxApplyNo(localDate, inquiryType);
        try {
            if (redisManager.hasKey(key)) {
                Object redisNow = redisManager.get(key);
                // redis中是否存在有效的申请号
                if (redisNow != null && StringUtils.isNotBlank(redisNow.toString())) {
                    applnoReturn = redisNow.toString();
                }
            }
        } catch (Exception e) {
            log.error("催促生成申请号导入失败" + e.toString());
        }
        // 递增申请号
        applnoReturn = InquiryUtils.createIncrementId(inquiryCode, new Date(), "yyyyMMdd", 5, applnoReturn);
        try {
            // 尽力尝试回写Redis，即使失败也不影响申请号的返回
            redisManager.set(key, applnoReturn, 60 * 60 * 24); // 回写redis
        } catch (Exception ex) {
            log.error("催促生成申请号写入Redis失败，原因：" + ex.getMessage(), ex);
        }
        return applnoReturn;
    }

    // todo 生成taskno规则变更
    //生成taskNo任务号
    public String generatorTaskNo(String suppily, String inquiryType) {
//        String inquiryCode = InquiryTypeEnum.PURCHASE.getType().equals(inquiryType) ? InquiryTypeEnum.PURCHASE.getDesc() : InquiryTypeEnum.ORDER.getDesc();
//        Random random = new Random();
//        int randomNumber = random.nextInt(90) + 10;
//        return suppily + inquiryCode + System.currentTimeMillis() + randomNumber;
        String inquiryCode = InquiryTypeEnum.OTHERRE.getDesc();
        String localDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String key = "ops:inquiry:taskno";
        String taskNoReturn = inquiryPoHandleMapper.getMaxTaskNo(localDate, inquiryType);
        try {
            if (redisManager.hasKey(key)) {
                Object redisNow = redisManager.get(key);
                // redis中是否存在有效的申请号
                if (redisNow != null && StringUtils.isNotBlank(redisNow.toString())) {
                    taskNoReturn = redisNow.toString();
                }
            }
        } catch (Exception e) {
            log.error("催促生成申请号导入失败" + e.toString());
        }
        // 递增申请号
        taskNoReturn = InquiryUtils.createIncrementId(inquiryCode, new Date(), "yyyyMMdd", 5, taskNoReturn);
        try {
            // 尽力尝试回写Redis，即使失败也不影响申请号的返回
            redisManager.set(key, taskNoReturn, 60 * 60 * 24); // 回写redis
        } catch (Exception ex) {
            log.error("催促生成申请号写入Redis失败，原因：" + ex.getMessage(), ex);
        }
        return taskNoReturn;
    }

    /**
     * 校验与当前时间的时间差
     *
     * @return
     */
    public long timeDiff(Date startTime,Date endTime) {
        Long nowTime = endTime.getTime();
        // 天数差
        long dateDiff = (nowTime - startTime.getTime()) / (24 * 60 * 60 * 1000);
        // 小时差
        return (nowTime - startTime.getTime()) / (60 * 60 * 1000);
    }

    /**
     * 新增是否节假日接口，提供给门户
     * 0: 非节假日，1：节假日
     *
     * @param info
     * @return
     */
    @Override
    public ResultVo<Boolean> getWorkday(InquiryWorkdayCondition info) {
        Boolean result = false; // 默认为 工作日
        if (info.getDay() == null || StringUtils.isBlank(info.getCountry())) {
            return ResultVo.failure("请求参数为空，请补充后重试");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TblWorkdayYear workdayYear = tblWorkDayYearMapper.getWorkDay(dateFormat.format(info.getDay()));
        if (workdayYear == null) {
            return ResultVo.failure("当前日期，未维护节假日信息，请联系IT");
        }
        // 判断国别代码
//        String day = info.getCountry().equalsIgnoreCase("CN") ? workdayYear.getOptflag():workdayYear.getOptflagJp();
        result = (info.getCountry().equalsIgnoreCase("CN") ? workdayYear.getOptflag() : workdayYear.getOptflagJp()).equalsIgnoreCase("1");
        return ResultVo.success(result);
    }

    /**
     * 根据不同供应商返回可选的催促原因配置
     *
     * @param suppilyList
     */
    @Override
    public ResultVo<List<InquiryCodeConfigBySuppily>> getReasonBySuppilyBatch(List<String> suppilyList) {
        if (suppilyList != null && !suppilyList.isEmpty()) {
            // 根据suppilyList去重
            suppilyList = suppilyList.stream().distinct().collect(Collectors.toList());
            List<InquiryCodeConfigBySuppily> returnList = new ArrayList<>();
            InquiryCodeConfigBySuppily inquiryCodeConfigBySuppily;
            for (String suppily : suppilyList) {
                inquiryCodeConfigBySuppily = new InquiryCodeConfigBySuppily();
                inquiryCodeConfigBySuppily.setSupplierCode(suppily);
                ResultVo<List<OpsInquiryCodeConfig>> resultVo = inquiryApplyService.getReasonBySuppily(suppily); // 调用单条处理接口查询
                if (resultVo.isSuccess() && !CollectionUtils.isEmpty(resultVo.getData())) {
                    inquiryCodeConfigBySuppily.setOpsInquiryCodeConfigs(resultVo.getData());
                }
                returnList.add(inquiryCodeConfigBySuppily);
            }
            return ResultVo.success(returnList);
        }
        return ResultVo.failure("请求供应商代码为空，不能获取催促原因配置，请检查！");
    }

    /**
     * 根据催促次数，计算催促级别
     */
    public String determineInquiryLevel(String suppily, int inquiryCount) {
        if (suppily.equals("JP")) {
            if (inquiryCount <= 2) {
                return InquiryALevel.INQ_A.getType();
            } else if (inquiryCount <= 4) {
                return InquiryALevel.HOT_1.getType();
            } else if (inquiryCount <= 6) {
                return InquiryALevel.HOT_2.getType();
            } else {
                // 对于超出范围的值，默认返回INQ_A，也可以根据实际需求调整
                return InquiryALevel.INQ_A.getType();
            }
        } else {
            return InquiryALevel.INQ_A.getType();
        }
    }

    // 获取供应商仓库的配置
    private Map<String, String> getWarehouseSupplier() {
    List<Map<String,String>> list = opsRcvdetailMapper.getWarehouseSupplier();
    return list.stream().collect(Collectors.toMap(
            m -> m.get("code"), // 外层 Map 的键
            m -> m.get("codename") // 外层 Map 的值
    ));
    }

}
