package com.sales.ops.serviceimpl.purchase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.conf.OpsUrlConfig;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.OkHttpAddTokenUtil;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OpsRequisitionCNDao;
import com.sales.ops.db.extdao.PurchaseOrderDao;
import com.sales.ops.dto.ips.IpsReceiveOrderAllOriginalInfoDto;
import com.sales.ops.dto.purchase.OrderToManuDto;
import com.sales.ops.dto.purchase.SMCOrderDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.PurchaseCtcEnum;
import com.sales.ops.enums.PurchaseInvoiceStatusEnum;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.enums.PurchaseTypeEnum;
import com.sales.ops.enums.ipsPurchase.IpsPurchaseCommonEnum;
import com.sales.ops.enums.ipsPurchase.IpsPurchaseSendSwitchEnum;
import com.sales.ops.feign.CtcSmcOrderToFeignApi;
import com.sales.ops.feign.ips.IpsPurchaseSendFeignApi;
import com.sales.ops.service.ips.IpsPurchaseSendCommonService;
import com.sales.ops.service.purchase.PurchaseExportTxtService;
import com.sales.ops.service.purchase.PurchaseSendEmail;
import com.sales.ops.service.purchase.PurchaseSendOrderManuService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 发送工厂订单到制造
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseSendOrderManuServiceImpl implements PurchaseSendOrderManuService {

    private final static Logger logger = LoggerFactory.getLogger(PurchaseSendOrderManuServiceImpl.class);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private MaxcodeMapper maxcodeMapper;

    @Autowired
    private OpsPurchaseinvoiceMapper opsPurchaseinvoiceMapper;

    @Autowired
    private BindataMapper bindataMapper;

    @Autowired
    private OpsRequisitionCNDao opsRequisitionCNDao;

    @Autowired
    private PurchaseOrderDao purchaseOrderDao;

    @Autowired
    private YyLongmodelexchangeMapper yyLongmodelexchangeMapper;

    @Autowired
    private OpsTomanuConfigMapper opsTomanuConfigMapper;

    @Autowired
    private OkHttpAddTokenUtil okHttpAddTokenUtil;

    @Autowired
    private CtcSmcOrderToFeignApi ctcSmcOrderToFeignApi;

    @Autowired
    private PurchaseExportTxtService purchaseExportTxtService;

    @Autowired
    private PurchaseSendEmail purchaseSendEmail;

    @Autowired
    private OpsUrlConfig opsApiConfig;

    @Autowired
    private OpsPurchasetoctcMapper opsPurchasetoctcMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Resource
    private IpsPurchaseSendCommonService ipsPurchaseSendCommonService;

    @Resource
    private IpsPurchaseSendFeignApi ipsPurchaseSendFeignApi;

    /**
     * 工厂订单发送，写关务接口
     */
    @Override
    public Integer sendOrderToManuInterface() throws Exception {
        // 重新计算非当天采购单的出荷日
        purchaseExportTxtService.reCalExportday(1);
        // 采购预处理
        purchaseExportTxtService.sendPre(1);

        return operateManufacture(null);
    }


    @Override
    public Integer operateManufacture(String versionLike) throws Exception {
        // bug13927 使用配置获取制造对应的供应商代码合集
        SupplierExample suex = new SupplierExample();
        suex.createCriteria().andSupplierClassEqualTo("MANU");
        List<Supplier> supplier = supplierMapper.selectByExample(suex);
        if (CollectionUtils.isEmpty(supplier)) {
            return null;
        }
        // 查询符合条件的请购单
        List<String> suppilyList = supplier.stream().map(Supplier::getId).collect(Collectors.toList());
        OpsPurchaseinvoiceExample example = new OpsPurchaseinvoiceExample();
        if (StringUtils.isNotBlank(versionLike)) {
            example.createCriteria().andStatecodeEqualTo(PurchaseInvoiceStatusEnum.DAICHULI).andCnnoIsNull()
                    .andSmccodeIsNotNull().andSupplieridIn(suppilyList).andSendversionLike(versionLike);
        } else {
            example.createCriteria().andStatecodeEqualTo(PurchaseInvoiceStatusEnum.DAICHULI).andCnnoIsNull()
                    .andSmccodeIsNotNull().andSupplieridIn(suppilyList);
        }
        // todo 发单时间，应该统一
        Date sendDate = new Date();
        // 取得刚刚更新的数据
        List<OpsPurchaseinvoice> invoiceList = opsPurchaseinvoiceMapper.selectByExample(example);
        // 判断是否存在待处理的数据
        if (CollectionUtils.isNotEmpty(invoiceList)) {
            // bug16807 【IPS对接改造】新增OPS采购中国制造体系（含广州制造、中国制造、制六等）订单推送IPS相关需求 2025-02-26
            // 增加字典开关的配置, 字典值：0= 只发老系统，1 = 只发送新系统，2=新老同时发
            // 1.首先查询字典中的开关配置信息
            List<String> oldSysSuppilyList = new ArrayList<>(); // 旧接口需要发送的供应商
            List<String> newSysSuppilyList = new ArrayList<>(); // 新接口需要发送的供应商
            Map<String, String> supplierBatchNoMap = new HashMap<>(); // 不同供应商
            ResultVo<List<DataCodeVO>> listDictResult = dictDataServiceFeignApi.getDataCodes(IpsPurchaseCommonEnum.IPS_PURCHASE_SEND_DICT.getCode());
            if (listDictResult.isSuccess() && CollectionUtils.isNotEmpty(listDictResult.getData())) {
                List<DataCodeVO> dataCodeVOList = listDictResult.getData();
                for (DataCodeVO item : dataCodeVOList) {
                    // 增加字典开关的配置, 字典值：0= 只发老系统，1 = 只发送新系统，2=新老同时发
                    if (IpsPurchaseSendSwitchEnum.OLD.getCode().equals(item.getExtNote1())) {
                        oldSysSuppilyList.add(item.getCode());
                    } else if (IpsPurchaseSendSwitchEnum.NEW.getCode().equals(item.getExtNote1())) {
                        newSysSuppilyList.add(item.getCode());
                    } else if (IpsPurchaseSendSwitchEnum.ALL.getCode().equals(item.getExtNote1())) {
                        oldSysSuppilyList.add(item.getCode());
                        newSysSuppilyList.add(item.getCode());
                    }
                    // bug18697 OPS向制六和上海特注品采购的订单发送批次号优化,需要重新生成的批次，重新写入map中
                    if (StringUtils.isNotBlank(item.getExtNote2())) {
                        supplierBatchNoMap.put(item.getCode(), item.getExtNote2());
                    }
                }
            }
            // 分别筛选出，新旧程序需要处理的订单
            List<OpsPurchaseinvoice> oldSysInvoiceList = invoiceList.stream().filter(item -> oldSysSuppilyList.contains(item.getSupplierid())).collect(Collectors.toList());
            Set<Long> oldSysIdList = new HashSet<>();
            if (CollectionUtils.isNotEmpty(oldSysInvoiceList)) {
                oldSysIdList = oldSysInvoiceList.stream().map(a -> a.getId()).collect(Collectors.toSet()); //获取旧系统的id集合，用于判断是否写入订单到老系统
            }
            List<OpsPurchaseinvoice> newSysInvoiceList = invoiceList.stream().filter(item -> newSysSuppilyList.contains(item.getSupplierid())).collect(Collectors.toList());
            // 取得未处理的数据，更新对应的cnno,便于后续更新使用 BN
            // 获得最大的CNno,更新到表中
            String cnNo = orderMaxCode();
            // 定义制造返回接口List
            List<OrderToManuDto> orderToManuDtos = new ArrayList<>();
            // 定义CTC订单写入的LIst
            List<SMCOrderDto> oldSmcOrderDtoList = new ArrayList<SMCOrderDto>();
            List<SMCOrderDto> newSmcOrderDtoList = new ArrayList<SMCOrderDto>();

            List<OpsTomanuConfig> opsTomanuConfigsList = opsTomanuConfigMapper.selectByExample(null);
            Map<String, String> ctcSupplierConfig = new HashMap<String, String>();
            if (CollectionUtils.isNotEmpty(opsTomanuConfigsList)) {
                opsTomanuConfigsList.forEach(a -> {
                    ctcSupplierConfig.put(a.getSupplierid(), a.getRequesttype());
                });
            }
            OrderToManuDto orderToManuDto;
            for (OpsPurchaseinvoice opsInvoiceTest : invoiceList) {
                orderToManuDto = new OrderToManuDto();
                SMCOrderDto smcOrderDto = new SMCOrderDto();
                // 插入cnno
                // opsInvoiceTest.setCnno(cnNo);
                // opsInvoiceTest.setStatecode(PurchaseOrderStatusEnum.YIFASONG);
                // opsInvoiceTest.setSendtime(new Date());
                // 判断型号是否大于30位
                String modelNoSend = opsInvoiceTest.getModelno();
                // B1717,2022-10-18 修改，将制造发单超长替换型号，从>=30改位>30 bug8371
                if (StringUtils.isNotBlank(modelNoSend) && modelNoSend.getBytes().length > 30) {
                    longModel(opsInvoiceTest.getModelno());
                    modelNoSend = getLongModel(modelNoSend);
                }

                // 追加 JPRemarks--21 备注 富士康/vIp/客户类别/组装子项
                orderToManuDto.setJpRemarks("");
                // bug12349备注增加客户所属集团
                // bug 20054,用end_user替换userno
                List<OpsCustomerProperty> userVipCode = purchaseOrderDao
                        .getVIPCode(StringUtils.isNotBlank(opsInvoiceTest.getEndUser()) ? opsInvoiceTest.getEndUser()
                                : opsInvoiceTest.getCustomerno(), opsInvoiceTest.getSupplierid());

                if (CollectionUtils.isNotEmpty(userVipCode)) {
                    if (StringUtils.isBlank(orderToManuDto.getJpRemarks())) {
                        orderToManuDto.setJpRemarks("重点客户:" + userVipCode.get(0).getVipcode());
                    } else {
                        orderToManuDto.setJpRemarks(
                                orderToManuDto.getJpRemarks() + "/" + "重点客户:" + userVipCode.get(0).getVipcode());
                    }
                    // bug 12485 2023-11-2新增 end_user,vipcode,vipPriority 字段
                    orderToManuDto.setVipCode(userVipCode.get(0).getVipcode());
                    orderToManuDto.setVipPriority(0);
                }
                // bug12485
//                orderToManuDto.setEndUser(StringUtils.isBlank(opsInvoiceTest.getUserno())
//                        ? opsInvoiceTest.getCustomerno() : opsInvoiceTest.getUserno());
                orderToManuDto.setEndUser(StringUtils.isBlank(opsInvoiceTest.getEndUser())
                        ? opsInvoiceTest.getCustomerno() : opsInvoiceTest.getEndUser()); //bug19576 增加end_user复制
                // new 获取营业所名称
                // 2022-03-09 修改营业所名称以及组装标识，暂时不写入备注字段
                // bug12349备注增加客户所属集团
                String deptName = opsRequisitionCNDao.getDepartName(opsInvoiceTest.getDeptno());
                if (StringUtils.isNotBlank(deptName)) {
                    if (CollectionUtils.isNotEmpty(userVipCode))
                        orderToManuDto.setJpRemarks(orderToManuDto.getJpRemarks() + "[" + deptName + ","
                                + userVipCode.get(0).getCustomerGroup() + "]");
                    else {
                        orderToManuDto.setJpRemarks(orderToManuDto.getJpRemarks() + "[" + deptName + "]");
                    }
                } else if (CollectionUtils.isNotEmpty(userVipCode)) {
                    orderToManuDto.setJpRemarks(
                            orderToManuDto.getJpRemarks() + "[" + userVipCode.get(0).getCustomerGroup() + "]");
                }
                if (opsInvoiceTest.getModelno().getBytes().length > 30) {
                    orderToManuDto.setJpRemarks(orderToManuDto.getJpRemarks() + "/" + opsInvoiceTest.getModelno());
                }

                if (StringUtils.isNotBlank(opsInvoiceTest.getPurchasetype())) {
                    if (opsInvoiceTest.getPurchasetype().equals(PurchaseTypeEnum.SALE.getTypeCode())) {
                        orderToManuDto.setJpShelfNo(opsInvoiceTest.getCustomerno());
                        orderToManuDto.setSortNo(opsInvoiceTest.getCustomerno());
                        if (opsInvoiceTest.getSplititemno() == null) {
                            orderToManuDto.setGateNo("AAA");
                            orderToManuDto.setZoneMark("AAA");
                        } else {
                            orderToManuDto.setGateNo("999");
                            orderToManuDto.setZoneMark("999");
                        }
                    } else {
                        if (StringUtils.isNotBlank(opsInvoiceTest.getLocationno())) {
                            orderToManuDto.setJpShelfNo(opsInvoiceTest.getLocationno());
                            orderToManuDto.setSortNo(opsInvoiceTest.getLocationno());
                            orderToManuDto.setGateNo(opsInvoiceTest.getLocationno().substring(0, 2));
                            orderToManuDto.setZoneMark(opsInvoiceTest.getLocationno().substring(0, 2));
                        }
                    }
                }
                // 增加营业所名称到备注

                // 查询配置表中的 requestType
                orderToManuDto.setRequestType("0");
                if (ctcSupplierConfig.containsKey(opsInvoiceTest.getSupplierid())) {
                    orderToManuDto.setRequestType(ctcSupplierConfig.get(opsInvoiceTest.getSupplierid()));
                }

                // 2022-09-08 新增 jPSerialNoMark
                orderToManuDto.setJpSerialNoMark("01");
                if (opsInvoiceTest.getSupplierid().equals("YZ")) {
                    orderToManuDto.setJpSerialNoMark("CN");
                }
                // bug 10425, 制造发单取消推送SamleModel 信息 B91717
                // bindata数据
                BindataExample exampleBin = new BindataExample();
                exampleBin.createCriteria().andModelnoEqualTo(opsInvoiceTest.getModelno());
                List<Bindata> bindataList = bindataMapper.selectByExample(exampleBin);
                if (!CollectionUtils.isEmpty(bindataList)) {
                    Bindata bindata = bindataList.get(0);
                    // 写入BoxType外箱箱型,BoxFixedQty外箱数量,PackType网筐品番
                    orderToManuDto.setBoxType(bindata.getCasetype());
                    if (bindata.getIncaseqty() != null) {
                        // orderToManuDto.setBoxFixedQty(new
                        // BigDecimal(bindata.getIncaseqty()));
                        orderToManuDto.setBoxFixedQty(BigDecimal.valueOf((int) (bindata.getIncaseqty())));
                    }
                    // 2022-09-09 暂时先注释掉，超长
                    // orderToManuDto.setPackType(bindata.getMeshtype());
                }
                // 绿标
                if (StringUtils.isNotBlank(opsInvoiceTest.getGreencode())) {
                    orderToManuDto.setGreenMark(opsInvoiceTest.getGreencode());
                }
                orderToManuDto.setContractNo("");
                if (StringUtils.isNotBlank(opsInvoiceTest.getShikomianswerno())) {
                    orderToManuDto.setContractNo(opsInvoiceTest.getShikomianswerno());
                }
                // 追加接收方备注
                // bug 9920 优化制造接收方字段，改位通过配置表读取 B91717
                String accp = purchaseOrderDao.getAccepter(opsInvoiceTest.getSmccode(), opsInvoiceTest.getSupplierid());
                if (StringUtils.isBlank(accp)) {
                    logger.warn("订单号：" + opsInvoiceTest.getOrderno() + "-" + opsInvoiceTest.getItemno()
                            + ",接收方Accepter为空，请补充ops_po_destination_config表数据");
                    throw Exceptions.OpsException("订单号：" + opsInvoiceTest.getOrderno() + "-"
                            + opsInvoiceTest.getItemno() + ",接收方Accepter为空，请补充ops_po_destination_config表数据");
                }
                orderToManuDto.setAccepter(accp);
                orderToManuDto.setAcceptCustomerCode(opsInvoiceTest.getSmccode());
                orderToManuDto.setCustomerCode(opsInvoiceTest.getSmccode());
                orderToManuDto.setSalesModel(modelNoSend);
                orderToManuDto.setRequestModel(modelNoSend);
                // orderToManuDto.setInstrDlvyDate(opsInvoiceTest.getHopeexportdate());
                orderToManuDto.setDlvyWay("LAND");
                orderToManuDto.setInstrType("P/O");
                // orderToManuDto.setInstrDlvyDate(opsInvoiceTest.getPurchasedate());
                orderToManuDto.setIssueDate(sendDate);
                orderToManuDto.setPurchaseType(opsInvoiceTest.getPurchasetype());
                orderToManuDto.setRequestNo("YY" + opsInvoiceTest.getPono());
                orderToManuDto.setItemNo(opsInvoiceTest.getLineitem().toString());
                // 日本订单项号
                orderToManuDto.setJpOrderNo(opsInvoiceTest.getPono());
                orderToManuDto.setJpItemNo(opsInvoiceTest.getLineitem().toString());
                orderToManuDto.setJpKoGo(cnNo);
                orderToManuDto.setRequestQty(opsInvoiceTest.getQuantity());
                // 订单纳期
                // b备注，北京工厂、天津工厂出荷,统一指定为15：00：00,其余根据时间来判断
                Date ReqDlvyDate = null;
                ReqDlvyDate = simpleDateFormat
                        .parse(simpleDateFormat2.format(opsInvoiceTest.getHopeexportdate()) + " 15:00:00");
                LocalTime localTime = LocalTime.now();
                LocalTime dealTime = LocalTime.of(12, 00, 0);
                if (localTime.isBefore(dealTime) && !opsInvoiceTest.getSupplierid().equals("CM")
                        && !opsInvoiceTest.getSupplierid().equals("CT")) {
                    ReqDlvyDate = simpleDateFormat
                            .parse(simpleDateFormat2.format(opsInvoiceTest.getHopeexportdate()) + " 10:00:00");
                }
                orderToManuDto.setReqDlvyDate(ReqDlvyDate);
                // 写入操作人为，ops
                orderToManuDto.setOperator("OpsOrder");
                // 新增写入部门代码
                orderToManuDto.setSalesOfficeCode(opsInvoiceTest.getDeptno());

                // 写入CTC中间表
                smcOrderDto.setOrderNo("YY" + opsInvoiceTest.getPono());
                smcOrderDto.setPrjno(opsInvoiceTest.getLineitem().toString());
                smcOrderDto.setModelNo(modelNoSend);
                smcOrderDto.setQty(opsInvoiceTest.getQuantity());
                smcOrderDto.setRemarks(orderToManuDto.getJpRemarks());
                smcOrderDto.setDeliverydate(simpleDateFormat.format(ReqDlvyDate));
                smcOrderDto.setOrderdate(simpleDateFormat.format(sendDate));
                smcOrderDto.setDbname("SMCPDM");
                smcOrderDto.setStatus("0");
                smcOrderDto.setProducePlace(orderToManuDto.getRequestType());
                // 筛选oldSysInvoiceList中是否存在该条数据,写入旧的CTC和旧接口list
                if (oldSysIdList.contains(opsInvoiceTest.getId())) {
                    orderToManuDtos.add(orderToManuDto);
                    oldSmcOrderDtoList.add(smcOrderDto);
                } else { // 否则只写入新的CTC list,判断旧的里面是否已经存在了，如果存在则不写入新CTC list
                    newSmcOrderDtoList.add(smcOrderDto);
                }
            }
            // 以下提出公共方法
            // bug14866先更新采购再调用接口
            // 更新invoice 状态
//            OpsPurchaseinvoice updatestateCode = new OpsPurchaseinvoice();
//            updatestateCode.setStatecode(PurchaseOrderStatusEnum.YIFASONG);
//            updatestateCode.setCnno(cnNo);
//            updatestateCode.setSendtime(new Date());
//            List<Long> idList = invoiceList.stream().map(a -> a.getId()).collect(Collectors.toList());
//            OpsPurchaseinvoiceExample example2 = new OpsPurchaseinvoiceExample();
//            List<Long> temp = new ArrayList<>();
//            // 超过2000条时，分批操作
//            for (int i = 0; i < idList.size(); i++) {
//                if (i % 2000 == 0) {
//                    temp = null;
//                    if (i + 2000 < idList.size()) {
//                        temp = idList.subList(i, i + 2000);
//                    } else {
//                        temp = idList.subList(i, idList.size());
//                    }
//                    example2.clear();
//                    example2.createCriteria().andIdIn(temp);
//                    // opsPurchaseinvoiceMapper.updateByExampleSelective(updatestateCode,
//                    // example2);
//                    // bug14866 更新purchaseInvoice增加更新三次重试，避免被牺牲
//                    int trynum = 3;
//                    boolean updateresult = false;
//                    while (!updateresult && trynum > 0) {
//                        try {
//                            opsPurchaseinvoiceMapper.updateByExampleSelective(updatestateCode, example2);
//                            updateresult = true;
//                        } catch (Exception e) {
//                            trynum--;
//                            if (trynum == 0 && !updateresult) {
//                                logger.error("采购发单MANU更新purchaseinvoice三次尝试均失败。", e);
//                                throw e;
//                            }
//                        }
//                    }
//                }
//            }
            JSONObject result = null;
            // 掉用关务接口 2022-09-08 配置接口到配置文件
            // 打印发送json信息
            if (!CollectionUtils.isEmpty(orderToManuDtos)) {
                // 以下提出公共方法,先更新采购表状态
//                updatePurchaseInvoiceStatus(oldSysInvoiceList, cnNo);
                ipsPurchaseSendCommonService.updatePurchaseInvoiceStatus(oldSysInvoiceList, cnNo, null);
                logger.info("发送到老生管系统的json信息：" + JSON.toJSONString(orderToManuDtos));
                result = okHttpAddTokenUtil.toManuOrder(opsApiConfig.getManuApi(), orderToManuDtos,
                        opsApiConfig.getManuTokenApi(), opsApiConfig.getManuTokenName(), opsApiConfig.getManuTokenPsd());
                String resultCode = result.getString("code");
                // 调用邮件发送通用方法，给业务提示发送多少条数据，以及CN号
                if (resultCode.equals("0")) {
                    // 写入CTC表中
                    // 判断如果是测试环境，不写CTC表
//                    if (StringUtils.containsIgnoreCase(opsApiConfig.getManuTokenApi(), "236")) {
                    // 写入CTC中间表之前，先插入备份表，防止写入出错后，后续可人工处理该部分数据
                    JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(oldSmcOrderDtoList));
                    ctcSmcOrderToFeignApi.insertSmcOrderCtc(jsonArray);
//                    }
                    // 邮件发送信息
                    String message = "本次中国工厂订单发送，CNNO:" + cnNo + "," + result.getString("message");
                    purchaseSendEmail.mailMessage(1, message);
                } else {
                    String message = "中国工厂订单发送错误：" + result.getString("message");
                    purchaseSendEmail.mailMessage(3, message);
                    throw Exceptions.OpsException(result.getString("message"));
                }
            }
            // 调用发送IPS的接口
            if (!CollectionUtils.isEmpty(newSysInvoiceList)) {
                // String cnNoIps = orderMaxCode(); // 发送IPS时，重新生成一个CNNO,防止新旧方法出现重复批次号，影响老生管接单
                // bug 18697 OPS向制六和上海特注品采购的订单发送批次号优化
                Map<String,List<OpsPurchaseinvoice>> newPsipBatchNoMap = new HashMap<>(); // 定义psi发单批次的map,key为批次号配置，value为采购单列表
                List<OpsPurchaseinvoice> newPsiInvoiceList;
                List<OpsPurchaseinvoice> oldPsiInvoiceList = new ArrayList<>();
                // 组装新的发单map
                for (OpsPurchaseinvoice psiInvoiceEntity : newSysInvoiceList) {
                    // 如果供应商在特殊批次号配置表中，则单独使用特殊的清单
                    if (supplierBatchNoMap.containsKey(psiInvoiceEntity.getSupplierid())) {
                        // 查看newPsipBatchNoMap 中是否存在该配置，存在时获取后追加
                        if (newPsipBatchNoMap.containsKey(supplierBatchNoMap.get(psiInvoiceEntity.getSupplierid()))) {
                            newPsiInvoiceList = newPsipBatchNoMap.get(supplierBatchNoMap.get(psiInvoiceEntity.getSupplierid()));
                            newPsiInvoiceList.add(psiInvoiceEntity);
                            newPsipBatchNoMap.put(supplierBatchNoMap.get(psiInvoiceEntity.getSupplierid()), newPsiInvoiceList);
                        } else {
                            // 不存在时，创建新的
                            newPsiInvoiceList = new ArrayList<>();
                            newPsiInvoiceList.add(psiInvoiceEntity);
                            newPsipBatchNoMap.put(supplierBatchNoMap.get(psiInvoiceEntity.getSupplierid()), newPsiInvoiceList);
                        }
                    } else {
                        // 不在特殊批次号配置表中，则使用原始的清单
                        oldPsiInvoiceList.add(psiInvoiceEntity);
                    }
                }
                List<IpsReceiveOrderAllOriginalInfoDto> ipsSendOrderInsert = new ArrayList<>();
                Map<String,List<OpsPurchaseinvoice>> newPsipBatchNoUpdateMap = new HashMap<>(); // 用于回更采购表，CNNO批次号字段来使用
                if (!CollectionUtils.isEmpty(oldPsiInvoiceList)) { // 旧的发送模式的清单
                    // 调用转换方法
                    List<IpsReceiveOrderAllOriginalInfoDto> oldPsiSendOrderConvert = ipsPurchaseSendCommonService
                            .ipsSendOrderConvert(oldPsiInvoiceList, ctcSupplierConfig,"CN", cnNo, null,null);
                    ipsSendOrderInsert.addAll(oldPsiSendOrderConvert);
                    newPsipBatchNoUpdateMap.put(cnNo, oldPsiInvoiceList);
                }
                // 组装完成，循环map列表
                for (Map.Entry<String, List<OpsPurchaseinvoice>> entry : newPsipBatchNoMap.entrySet()) {
                    List<IpsReceiveOrderAllOriginalInfoDto> psiSendOrderConvert = ipsPurchaseSendCommonService
                            .ipsSendOrderConvert(entry.getValue(), ctcSupplierConfig, entry.getKey(), null, null,null);
                    if (!CollectionUtils.isEmpty(psiSendOrderConvert)) { // 转换完成时，加入待发送清单中
                        ipsSendOrderInsert.addAll(psiSendOrderConvert);
                        newPsipBatchNoUpdateMap.put(psiSendOrderConvert.get(0).getBatchNo(), entry.getValue());
                    }
                }
                // 打印发送json信息
                logger.info("发送到IPS的json信息：" + JSON.toJSONString(ipsSendOrderInsert));
                // 数据组装完成，调用feign接口，发送到IPS表中
                CommonResult<String> ipsResult = ipsPurchaseSendFeignApi.insertPurchaseOrderToIps(ipsSendOrderInsert);
                if (ipsResult.isSuccess()) {
                    // 以下提出公共方法,先更新采购表状态，根据map遍历
                    for (Map.Entry<String, List<OpsPurchaseinvoice>> entry : newPsipBatchNoUpdateMap.entrySet()) {
                        ipsPurchaseSendCommonService.updatePurchaseInvoiceStatus(entry.getValue(), entry.getKey(), null);
                    }
//                    updatePurchaseInvoiceStatus(newSysInvoiceList, cnNo);
//                    if (StringUtils.containsIgnoreCase(opsApiConfig.getManuTokenApi(), "236")) {
                    JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(newSmcOrderDtoList));
                    ctcSmcOrderToFeignApi.insertSmcOrderCtc(jsonArray);
//                    }
                    // 邮件发送信息
                    String message = "本次中国工厂订单共计发送："+ ipsSendOrderInsert.size() + "项,CNNO: "+ cnNo;
                    purchaseSendEmail.mailMessage(1, message);
                } else {
                    // 回更采购表的状态，并提示异常邮件，下次发单继续进行
                    String message = "中国工厂订单发送错误：" + ipsResult.getMessage();
                    purchaseSendEmail.mailMessage(3, message);
                    throw Exceptions.OpsException(result.getString("message")); // 发送失败时，回滚
                }
            }
            return invoiceList.size();
        } else {
            logger.info("没有未发送数据请重试");
            return 0;
        }
    }

    /**
     * CTC订单状态更新，已完成，更新ctc订单状态
     *
     * @return
     */
    @Override
    public void updateCtcFinish() throws OpsException {
        // bug8426
        // 查询当天需要更新完成状态的数据
        OpsPurchasetoctcExample opsPurchasetoctcExample = new OpsPurchasetoctcExample();
        OpsPurchasetoctcExample.Criteria criteria = opsPurchasetoctcExample.createCriteria();
        criteria.andDealtypeEqualTo(PurchaseCtcEnum.FINISH.getType()).andStatusEqualTo("0");
        List<OpsPurchasetoctc> opsPurchaseorders = opsPurchasetoctcMapper.selectByExample(opsPurchasetoctcExample);

        if (CollectionUtils.isNotEmpty(opsPurchaseorders)) {
            opsPurchaseorders.forEach(opsPurchaseorder -> {
                String pono = "YY";
                // bug15422增加广州订单写到CTC表，广州订单前面不写YY前缀
                if (StringUtils.equals("GZ", opsPurchaseorder.getSupplierid())) {
                    pono = "";
                }
                if (opsPurchaseorder.getSplititemno() == null) {
                    pono = pono + opsPurchaseorder.getOrderno() + "-" + opsPurchaseorder.getItemno().toString();
                } else {
                    pono = pono + opsPurchaseorder.getOrderno() + "-" + opsPurchaseorder.getItemno().toString() + "-"
                            + opsPurchaseorder.getSplititemno().toString();
                }
                OpsPurchasetoctc updatedata = new OpsPurchasetoctc();
                if (StringUtils.isNotBlank(pono) && opsPurchaseorder.getFinishdate() != null) {
                    try {
                        ctcSmcOrderToFeignApi.updateSmcOrderFinishCtc(pono, opsPurchaseorder.getFinishdate());
                        updatedata.setStatus("1");
                        updatedata.setId(opsPurchaseorder.getId());
                        opsPurchasetoctcMapper.updateByPrimaryKeySelective(updatedata);
                    } catch (Exception e) {
                        // 异常处理，当处理失败时，将中间表置为失败状态
                        updatedata.setStatus("2");
                        updatedata.setRemark(e.getMessage());
                        updatedata.setId(opsPurchaseorder.getId());
                        opsPurchasetoctcMapper.updateByPrimaryKeySelective(updatedata);
                    }
                }
            });
        }
    }

    /**
     * CTC订单状态更新，删除时，更新ctc订单状态
     *
     * @return
     */
    @Override
    public void updateCtcDel() throws OpsException {
        // bug 8426
        // 查询当天需要更新完成状态的数据
        OpsPurchasetoctcExample opsPurchasetoctcExample = new OpsPurchasetoctcExample();
        OpsPurchasetoctcExample.Criteria criteria = opsPurchasetoctcExample.createCriteria();
        criteria.andDealtypeEqualTo(PurchaseCtcEnum.DELETE.getType()).andStatusEqualTo("0");
        List<OpsPurchasetoctc> opsPurchaseorders = opsPurchasetoctcMapper.selectByExample(opsPurchasetoctcExample);

        if (CollectionUtils.isNotEmpty(opsPurchaseorders)) {
            List<String> ordernos = new ArrayList<>();
            opsPurchaseorders.forEach(opsPurchaseorder -> {
                String pono = "YY";
                // bug15422增加广州订单写到CTC表，广州订单前面不写YY前缀
                if (StringUtils.equals("GZ", opsPurchaseorder.getSupplierid())) {
                    pono = "";
                }
                if (opsPurchaseorder.getSplititemno() == null) {
                    pono = pono + opsPurchaseorder.getOrderno() + "-" + opsPurchaseorder.getItemno().toString();
                } else {
                    pono = pono + opsPurchaseorder.getOrderno() + "-" + opsPurchaseorder.getItemno().toString() + "-"
                            + opsPurchaseorder.getSplititemno().toString();
                }
                ordernos.add(pono);
            });
            List<Long> idList = opsPurchaseorders.stream().map(a -> a.getId()).collect(Collectors.toList());
            OpsPurchasetoctcExample example2 = new OpsPurchasetoctcExample();
            try {
                ctcSmcOrderToFeignApi.updateSmcOrderDelCtc(ordernos);
                // 更新已处理状态
                OpsPurchasetoctc opsPurchasetoctc = new OpsPurchasetoctc();
                opsPurchasetoctc.setStatus("1");
                List<Long> temp = new ArrayList<>();
                // 超过2000条时，分批操作
                for (int i = 0; i < idList.size(); i++) {
                    if (i % 2000 == 0) {
                        temp = null;
                        if (i + 2000 < idList.size()) {
                            temp = idList.subList(i, i + 2000);
                        } else {
                            temp = idList.subList(i, idList.size());
                        }
                        example2.clear();
                        example2.createCriteria().andIdIn(temp);
                        opsPurchasetoctcMapper.updateByExampleSelective(opsPurchasetoctc, example2);
                    }
                }
            } catch (Exception e) {
                // 异常处理，当处理失败时，将中间表置为失败状态
                example2.createCriteria().andIdIn(idList);
                OpsPurchasetoctc error = new OpsPurchasetoctc();
                error.setStatus("2");
                error.setRemark(e.getMessage());
                opsPurchasetoctcMapper.updateByPrimaryKeySelective(error);
            }
        }

    }

    /**
     * 获取最大的CNNO
     *
     * @return CNNO
     */
    public String orderMaxCode() {
        String orderBgegin = "CN";
        Maxcode maxCode = maxcodeMapper.selectByPrimaryKey("PS");
        String orderCode = maxCode.getPickcode();// 表中存在的最大订单号
        // 获取当前时间
        LocalDate date = LocalDate.now();
        // 获取年份,后两位
        String year = String.valueOf(date.getYear()).substring(2, 4);
        // 获取月份
        String month = String.format("%02d", date.getMonthValue());
        if (orderCode.substring(2, 4).equalsIgnoreCase(year) && orderCode.substring(4, 6).equalsIgnoreCase(month)) {
            AtomicInteger val = new AtomicInteger(Integer.valueOf(orderCode.substring(6)));
            int nextVal = val.incrementAndGet();
            String i = String.format("%03d", nextVal);
            orderCode = orderCode.substring(0, 6) + i;
        } else {
            if (!orderCode.substring(2, 4).equalsIgnoreCase(year)) {
                orderCode = orderBgegin + year + "01001";
            }
            if (!orderCode.substring(4, 6).equalsIgnoreCase(month)) {
                orderCode = orderCode.substring(0, 4) + month + "001";
            }
        }
        maxCode.setPickcode(orderCode);
        final int result = maxcodeMapper.updateByPrimaryKeySelective(maxCode);
        return orderCode;
    }

    public void longModel(String modelNo) {
        // 取得超长型号表，超长型号写入表中
        YyLongmodelexchangeExample yyLongmodelexchangeExample = new YyLongmodelexchangeExample();
        yyLongmodelexchangeExample.createCriteria().andYymodelEqualTo(modelNo);
        List<YyLongmodelexchange> yyLongmodelexchangeList = yyLongmodelexchangeMapper
                .selectByExample(yyLongmodelexchangeExample);
        if (CollectionUtils.isEmpty(yyLongmodelexchangeList)) {
            YyLongmodelexchange yyLongmodelexchange = new YyLongmodelexchange();
            yyLongmodelexchange.setYymodel(modelNo);
            yyLongmodelexchangeMapper.insertSelective(yyLongmodelexchange);
        }

    }

    public Date getDateStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
    }

    public Date getDateEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date zero = calendar.getTime();
        return zero;
    }

    public String getLongModel(String Modelno) {
        YyLongmodelexchangeExample yyLongmodelexchangeExample = new YyLongmodelexchangeExample();
        yyLongmodelexchangeExample.createCriteria().andYymodelEqualTo(Modelno);
        List<YyLongmodelexchange> yyLongmodelexchangeList = yyLongmodelexchangeMapper
                .selectByExample(yyLongmodelexchangeExample);
        return yyLongmodelexchangeList.get(0).getModel();
    }

//    public void updatePurchaseInvoiceStatus(List<OpsPurchaseinvoice> invoiceList, String cnNo) {
//        // bug14866先更新采购再调用接口
//        // 更新invoice 状态
//        OpsPurchaseinvoice updatestateCode = new OpsPurchaseinvoice();
//        updatestateCode.setStatecode(PurchaseOrderStatusEnum.YIFASONG);
//        updatestateCode.setCnno(cnNo);
//        updatestateCode.setSendtime(new Date());
//        List<Long> idList = invoiceList.stream().map(a -> a.getId()).collect(Collectors.toList());
//        OpsPurchaseinvoiceExample example2 = new OpsPurchaseinvoiceExample();
//        List<Long> temp = new ArrayList<>();
//        // 超过2000条时，分批操作
//        for (int i = 0; i < idList.size(); i++) {
//            if (i % 2000 == 0) {
//                temp = null;
//                if (i + 2000 < idList.size()) {
//                    temp = idList.subList(i, i + 2000);
//                } else {
//                    temp = idList.subList(i, idList.size());
//                }
//                example2.clear();
//                example2.createCriteria().andIdIn(temp);
//                // opsPurchaseinvoiceMapper.updateByExampleSelective(updatestateCode,
//                // example2);
//                // bug14866 更新purchaseInvoice增加更新三次重试，避免被牺牲
//                int trynum = 3;
//                boolean updateresult = false;
//                while (!updateresult && trynum > 0) {
//                    try {
//                        opsPurchaseinvoiceMapper.updateByExampleSelective(updatestateCode, example2);
//                        updateresult = true;
//                    } catch (Exception e) {
//                        trynum--;
//                        if (trynum == 0 && !updateresult) {
//                            logger.error("采购发单MANU更新purchaseinvoice三次尝试均失败。", e);
//                            throw e;
//                        }
//                    }
//                }
//            }
//        }
//    }

    /**
     * 写入制造表时，同时写到备份表，已便查验
     */
    public void insertIntoTemp(OrderToManuDto orderToManuDto) {
        // Temprequisition temprequisition = new Temprequisition();
        // temprequisition.setAccepter(orderToManuDto.getAccepter());
        // temprequisition.setAcceptcustomercode(orderToManuDto.getAcceptCustomerCode());
        // temprequisition.setCustomercode(orderToManuDto.getCustomerCode());
        // temprequisition.setRequestmodel(orderToManuDto.getRequestModel());
        // temprequisition.setInstrdlvydate(orderToManuDto.getInstrDlvyDate());
        // temprequisition.setDlvyway(orderToManuDto.getDlvyWay());
        // temprequisition.setInstrtype(orderToManuDto.getInstrType());
        // temprequisition.setIssuedate(orderToManuDto.getIssueDate());
        // temprequisition.setPurchasetype(orderToManuDto.getPurchaseType());
        // temprequisition.setRequestno(orderToManuDto.getRequestNo());
        // temprequisition.setItemno(orderToManuDto.getItemNo());
        // //日本订单项号
        // temprequisition.setJporderno(orderToManuDto.getJpOrderNo());
        // temprequisition.setJpitemno(orderToManuDto.getJpItemNo());
        // temprequisition.setJpkogo(orderToManuDto.getJpKoGo());
        // temprequisition.setRequestqty(orderToManuDto.getRequestQty());
        // temprequisition.setJpremarks(orderToManuDto.getJpRemarks());
        // temprequisition.setRequesttype(Byte.parseByte(orderToManuDto.getRequestType()));
        // temprequisition.setJpshelfno(orderToManuDto.getJpShelfNo());
        // temprequisition.setSortno(orderToManuDto.getSortNo());
        // temprequisition.setGateno(orderToManuDto.getGateNo());
        // temprequisition.setZonemark(orderToManuDto.getZoneMark());
        // temprequisitionMapper.insertSelective(temprequisition);
    }

}
