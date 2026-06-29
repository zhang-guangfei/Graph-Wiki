package com.sales.ops.serviceimpl.ips;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OpsRequisitionCNDao;
import com.sales.ops.db.extdao.PurchaseOrderDao;
import com.sales.ops.dto.ips.*;
import com.sales.ops.dto.purchase.RequestPurchaseInfo;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.enums.PurchaseTypeEnum;
import com.sales.ops.enums.TradeCompanyIdEnum;
import com.sales.ops.enums.ipsPurchase.IpsEndUserNsEnum;
import com.sales.ops.enums.ipsPurchase.IpsPurchaseCommonEnum;
import com.sales.ops.enums.ipsPurchase.IpsPurchaseSendSwitchEnum;
import com.sales.ops.feign.inqb.InqbCommonFeignApi;
import com.sales.ops.feign.ips.IpsPurchaseSendFeignApi;
import com.sales.ops.service.ips.IpsPurchaseSendCommonService;
import com.sales.ops.service.mail.PurchaseMailService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.purchase.PurchaseExportTxtService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.OPSTransTypeEnum;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import com.smc.smccloud.service.OpsAttachedFileManageFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IpsPurchaseSendCommonServiceImpl implements IpsPurchaseSendCommonService {

    @Resource
    private OPSRedisUtils opsRedisUtils;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Resource
    private OpsWarehouseMapper opsWarehouseMapper;

    @Resource
    private AdapterOpsTranslationConfigMapper adapterOpsTranslationConfigMapper;

    @Resource
    private BindataMapper bindataMapper;

    @Resource
    private RcvdetailMapper rcvdetailMapper;


    @Resource
    private RcvmasterMapper rcvmasterMapper;

    @Resource
    private OpsRequisitionCNDao opsRequisitionCNDao;

    @Resource
    private PurchaseOrderDao purchaseOrderDao;

    @Resource
    private InqbCommonFeignApi inqbCommonFeignApi;

    @Resource
    private OpsPurchaseinvoiceMapper opsPurchaseinvoiceMapper;

    @Resource
    private PurchaseExportTxtService purchaseExportTxtService;

    @Resource
    private PurchaseMailService purchaseMailService;

    @Resource
    private IpsPurchaseSendFeignApi ipsPurchaseSendFeignApi;

    @Resource
    private OpsMaxinvoiceMapper opsMaxinvoiceMapper;

    @Resource
    private BasePoService basePoService;

    @Resource
    private OpsAttachedFileManageFeignApi opsAttachedFileManageFeignApi;

    @Resource
    private OpsPrepareOrderMapper opsPrepareOrderMapper;


    /**
     * 根据OpsPurchaseinvoice信息，转换为IPS写入的格式
     * 提取出公共方法
     * @param invoiceList
     * @return
     * @throws Exception
     */
    @Override
    public List<IpsReceiveOrderAllOriginalInfoDto> ipsSendOrderConvert(List<OpsPurchaseinvoice> invoiceList, Map<String, String> manuSupplierConfig,String suppily, String batchNo,Integer supplierPayment, String warehouseFlag) throws Exception {
        List<IpsReceiveOrderAllOriginalInfoDto> ipsReceiveOrderAllOriginalInfoDtos = new ArrayList<>(); //定义返回大集合
        IpsReceiveOrderAllOriginalInfoDto ipsReceiveOrderAllOriginalInfoDto;
        IpsReceiveOrderContentAddDto ipsReceiveOrderContentAddDto;
        IpsReceiveContentInfoDto ipsReceiveContentInfoDto; // addinfo,json实体
        IpsProductInfoDto productInfo; //产品信息的json
        IpsPackageInfoDto packageInfo; // 包装信息的json
        IpsDeliveryInfoDto deliveryInfo; // 发货信息的json
        IpsCustomerInfoDto customerInfo; // 客户信息的json
        IpsEnduserInfoDto enduserInfo; // 最终用户信息的json
        IpsSupplierInfoDto ipsSupplierInfoDto; // 供应商信息的json
        IpsAddEmailInfoDto ipsAddEmailInfoDto; //附件清单的json
        // 生成当前处理的批次号
        int batchNum = invoiceList.size();
        if (StringUtils.isBlank(batchNo)) {
            ResultVo<String> batchNoResultVo = this.generateIpsOrderBatchNo(suppily);
            if (batchNoResultVo.isSuccess()) {
                batchNo = batchNoResultVo.getData();
            }
        }
        // bug21116 读取字典6014配置，公共方法中已经内置了有效性及排序
        ResultVo<List<DataCodeVO>> dictResult =  null;
        try{
            dictResult = dictDataServiceFeignApi.getDataCodes(IpsPurchaseCommonEnum.IPS_REMARK_CONVERT_DICT.getCode());
        } catch (Exception e) {
            log.error("获取字典6014配置失败");
        }
        // 循环OPS采购数据，循环为IPS接单赋值
        for (OpsPurchaseinvoice opsPurchaseinvoice : invoiceList) {
            ipsReceiveOrderAllOriginalInfoDto = new IpsReceiveOrderAllOriginalInfoDto();
            // 1. 开始为主字段赋值
            ipsReceiveOrderContentAddDto = new IpsReceiveOrderContentAddDto();
            // 1.1查询接单信息,非必填的
//            RcvdetailExample rcvdetailExample = new RcvdetailExample();
//            rcvdetailExample.createCriteria().andRorderNoEqualTo(opsPurchaseinvoice.getOrderno()).andRorderItemEqualTo(opsPurchaseinvoice.getItemno());
//            List<Rcvdetail> rcvdetails = rcvdetailMapper.selectByExample(rcvdetailExample);
//            if (!CollectionUtils.isEmpty(rcvdetails)) {
//                Rcvdetail rcvdetailInfo = rcvdetails.get(0);
//                ipsReceiveOrderContentAddDto.setModelName(rcvdetailInfo.getProductName()); //  品名从rcvdetail中获取
//            }
            ipsReceiveOrderContentAddDto.setSmccode(opsPurchaseinvoice.getSmccode());
            ipsReceiveOrderContentAddDto.setSourceSystem(IpsPurchaseCommonEnum.IPS_SEND_SOURCE_SYSTEM.getCode());
            // 采购方
            ipsReceiveOrderContentAddDto.setPurchaseUnitCode(TradeCompanyIdEnum.CN0.getCode()); // OPS的采购方默认写CN0
            // 1.2 收货相关信息
            deliveryInfo = new IpsDeliveryInfoDto();
            String warehouseCode = opsPurchaseinvoice.getReceivewarehouseid();
            if (StringUtils.isNotBlank(warehouseFlag)) {
                warehouseCode = opsPurchaseinvoice.getHopereceivewarehouse();
            }
            IpsOrderWarehouseInfoDto ipsOrderWarehouseInfoDto = this.getWarehouseInfo(warehouseCode,IpsPurchaseCommonEnum.IPS_WAREHOUSE_CONVERT_DICT.getCode());
            if (ipsOrderWarehouseInfoDto != null) {
//                ipsReceiveOrderContentAddDto.setReceivingWarehouse(ipsOrderWarehouseInfoDto.getReceivingWarehouse()); // IPS收货仓代码
                ipsReceiveOrderContentAddDto.setReceivingAddressCode(ipsOrderWarehouseInfoDto.getReceivingAddressId()); // IPS地址id
                ipsReceiveOrderContentAddDto.setReceivingAddress(ipsOrderWarehouseInfoDto.getReceivingAddress()); // IPS收货地址
                ipsReceiveOrderContentAddDto.setPlantmarkAddress(ipsOrderWarehouseInfoDto.getPlantmarkAddress());
                ipsReceiveOrderContentAddDto.setReceivingConnector(ipsOrderWarehouseInfoDto.getReceivingConnector());
                ipsReceiveOrderContentAddDto.setReceivingDepartmentTeleNo(ipsOrderWarehouseInfoDto.getReceivingDepartmentTeleNo());
                ipsReceiveOrderContentAddDto.setRequestDepartmentNo(ipsOrderWarehouseInfoDto.getRequestDepartmentNo());
                ipsReceiveOrderContentAddDto.setRequestDepartmentName(ipsOrderWarehouseInfoDto.getRequestDepartmentName());
                ipsReceiveOrderContentAddDto.setRequestPersonName(ipsOrderWarehouseInfoDto.getRequestPersonName());
                ipsReceiveOrderContentAddDto.setRequestPersonNo(ipsOrderWarehouseInfoDto.getRequestPersonNo());
                ipsReceiveOrderContentAddDto.setRequestPersonTele(ipsOrderWarehouseInfoDto.getRequestPersonTele());
                ipsReceiveOrderContentAddDto.setRequestPersonEmail(ipsOrderWarehouseInfoDto.getRequestPersonEmail());
                ipsReceiveOrderContentAddDto.setRequestAddress(ipsOrderWarehouseInfoDto.getRequestAddress());
                // 2025-10-27 海外发单新增字段信息
                deliveryInfo.setEnglishLinkman(ipsOrderWarehouseInfoDto.getEnglishLinkman());
                deliveryInfo.setEnglishAddress(ipsOrderWarehouseInfoDto.getEnglishAddress());
            }
            // 1.3 订单信息
            ipsReceiveOrderContentAddDto.setSourceOrderNo(opsPurchaseinvoice.getPono());
            ipsReceiveOrderContentAddDto.setSourceItemNo(opsPurchaseinvoice.getLineitem().toString());
            ipsReceiveOrderContentAddDto.setOrderMasterNum("1");
//            ipsReceiveOrderContentAddDto.setMaterialNo(""); //cproductno 物料编码
            ipsReceiveOrderContentAddDto.setModelNo(opsPurchaseinvoice.getModelno());
            ipsReceiveOrderContentAddDto.setQty(BigDecimal.valueOf(opsPurchaseinvoice.getQuantity()));
            ipsReceiveOrderContentAddDto.setUnit(IpsPurchaseCommonEnum.IPS_SEND_UNIT.getCode());
            // 1.4 供应商代码与IPS供应商之间的转换
            ResultVo<String> opsSupplierByConfig = this.getIpsTranstypeByConfig(opsPurchaseinvoice.getSupplierid(),"supplierid");
            if (opsSupplierByConfig.isSuccess()) {
                ipsReceiveOrderContentAddDto.setExpSupplierNo(opsSupplierByConfig.getData()); //
            }
            // 1.5 运输方式代码与IPS之间的转换
            // bug18095  当发单制造时，OPS的运输方式为空时，中国制造及广州制造默认为陆运 B91717
            if (StringUtils.isBlank(opsPurchaseinvoice.getTranstype())) {
                opsPurchaseinvoice.setTranstype(OPSTransTypeEnum.lendTran.getCode());
            }
            ResultVo<String> transportTypeByConfig = this.getIpsTranstypeByConfig(opsPurchaseinvoice.getTranstype(),"psi_transtype");
            if (opsSupplierByConfig.isSuccess()) {
                ipsReceiveOrderContentAddDto.setSpecifiedDeliveryWay(transportTypeByConfig.getData()); //
            }
            // 1.6 期望出货日的转换
            String dlvdate = DateUtil.dateToDateString(opsPurchaseinvoice.getHopeexportdate());
            LocalTime localTime = LocalTime.now();
            LocalTime dealTime = LocalTime.of(12, 00, 0);
            if (localTime.isBefore(dealTime) && !opsPurchaseinvoice.getSupplierid().equals("CM")
                    && !opsPurchaseinvoice.getSupplierid().equals("CT")) {
                dlvdate = dlvdate + " 10:00:00";
            } else {
                dlvdate = dlvdate + " 15:00:00";
            }
            ipsReceiveOrderContentAddDto.setSpecifiedDeliveryDate(DateUtil.stringToDateTime(dlvdate));
            ipsReceiveOrderContentAddDto.setRequestTime(opsPurchaseinvoice.getPurchasedate());
            ipsReceiveOrderContentAddDto.setPurchaseOrderType(opsPurchaseinvoice.getPurchasetype());

            //2. 主字段赋值完成，开始为附加信息赋值
            ipsReceiveContentInfoDto = new IpsReceiveContentInfoDto();
            // 2.1 开始为产品信息进行赋值
            productInfo = new IpsProductInfoDto();
            productInfo.setRohscode(opsPurchaseinvoice.getGreencode());
            productInfo.setHscode(opsPurchaseinvoice.getHscode());
            if (StringUtils.isNotBlank(opsPurchaseinvoice.getSpecmark())) {
                productInfo.setManagementCode(opsPurchaseinvoice.getSpecmark());
            }
            // 转成3c的格式
            if (opsPurchaseinvoice.getAdd3c().equals("1")) {
                productInfo.setIs3C(IpsPurchaseCommonEnum.IPS_SEND_3C_FLAG.getCode());
            }
            productInfo.setImportfobprice(opsPurchaseinvoice.getImportfobpriceoriginal());
            ipsReceiveContentInfoDto.setProductInfo(productInfo);
            // 2.2 开始为发货要求信息进行赋值
//            deliveryInfo = new IpsDeliveryInfoDto();
            if (opsPurchaseinvoice.getPurchasetype().equals(PurchaseTypeEnum.SALE.getTypeCode())) {
                deliveryInfo.setSortNo(opsPurchaseinvoice.getCustomerno());
                if (opsPurchaseinvoice.getSupplierid().equalsIgnoreCase("JP")) {
                    deliveryInfo.setShelfNo(opsPurchaseinvoice.getDeptno());
                } else {
//                    deliveryInfo.setShelfNo(opsPurchaseinvoice.getCustomerno());
                }
                if (opsPurchaseinvoice.getSplititemno() == null) {
                    deliveryInfo.setGateNo("AAA");
                    deliveryInfo.setZoneMark("AAA");
                } else {
                    deliveryInfo.setGateNo("999");
                    deliveryInfo.setZoneMark("999");
                }
            } else {
                if (StringUtils.isNotBlank(opsPurchaseinvoice.getLocationno())) {
                    if (opsPurchaseinvoice.getSupplierid().equalsIgnoreCase("JP")) {
                        deliveryInfo.setShelfNo(String.format("%-10s",
                                opsPurchaseinvoice.getReceivewarehouseid() + "-" + opsPurchaseinvoice.getLocationno().substring(0, 6).toUpperCase()));
                    } else {
//                        deliveryInfo.setShelfNo(opsPurchaseinvoice.getLocationno());
                    }
//                    customerInfo.setCustomerShelfNo(opsPurchaseinvoice.getLocationno());
                    deliveryInfo.setSortNo(opsPurchaseinvoice.getLocationno());
                    deliveryInfo.setGateNo(opsPurchaseinvoice.getLocationno().substring(0, 2));
                    deliveryInfo.setZoneMark(opsPurchaseinvoice.getLocationno().substring(0, 2));
                }
            }
            deliveryInfo.setPlace(opsPurchaseinvoice.getDeliveryflag());
            deliveryInfo.setSubCode(opsPurchaseinvoice.getSubCode());
            // 若配置表中没有，则不传，现在广州未配置因为不赋值，后续如需配置则直接修改数据库数据即可
//            if (manuSupplierConfig.containsKey(opsPurchaseinvoice.getSupplierid())) {
//                deliveryInfo.setPlace(manuSupplierConfig.get(opsPurchaseinvoice.getSupplierid()));
//            }
            ipsReceiveContentInfoDto.setDeliveryInfo(deliveryInfo);
            // 2.3 开始为包装信息进行赋值
            packageInfo = new IpsPackageInfoDto();
            // 获取bindata数据
            BindataExample exampleBin = new BindataExample();
            exampleBin.createCriteria().andModelnoEqualTo(opsPurchaseinvoice.getModelno());
            List<Bindata> bindataList = bindataMapper.selectByExample(exampleBin);
            if (!CollectionUtils.isEmpty(bindataList)) {
                Bindata bindata = bindataList.get(0);
                // 写入BoxType外箱箱型,BoxFixedQty外箱数量,PackType网筐品番
                packageInfo.setBoxType(bindata.getCasetype());
                // 20250318 先注释掉字段发送
                packageInfo.setPackType(bindata.getMeshtype());
                if (bindata.getIncaseqty() != null) {
                    packageInfo.setBoxFixedQty(String.valueOf(bindata.getIncaseqty()));
                }
            }
            ipsReceiveContentInfoDto.setPackageInfo(packageInfo);
            // 2.4 开始为最终用户信息进行赋值 ,  最终用户不能为空
            enduserInfo = new IpsEnduserInfoDto(); // 最终用户信息的json
            enduserInfo.setPurchaseEnduserNo(StringUtils.isBlank(opsPurchaseinvoice.getEndUser())
                    ? opsPurchaseinvoice.getCustomerno() : opsPurchaseinvoice.getEndUser());
            List<OpsCustomerProperty> userVipCode = new ArrayList<>();
            // 查询vip信息 BUG20054,切换为使用最终用户
            // 日本的 vipcode标识 直接根据vipcode字段判断即可，无需再通过客户来判断
            if (opsPurchaseinvoice.getSupplierid().equalsIgnoreCase("JP")) {
                if (StringUtils.isNotBlank(opsPurchaseinvoice.getVipcode())) {
                    enduserInfo.setEnduserVipCode(opsPurchaseinvoice.getVipcode());
                    enduserInfo.setEnduserVipPriority("0");
                }
            } else { //其余供应商按照客户来判断
                userVipCode = purchaseOrderDao
                        .getVIPCode(StringUtils.isNotBlank(opsPurchaseinvoice.getEndUser()) ? opsPurchaseinvoice.getEndUser()
                                : opsPurchaseinvoice.getCustomerno(), opsPurchaseinvoice.getSupplierid());
                // vipcode信息，单独去表里查询
                if (CollectionUtils.isNotEmpty(userVipCode)) {
                    enduserInfo.setEnduserVipCode(userVipCode.get(0).getVipcode());
                    enduserInfo.setEnduserVipPriority("0");
                }
            }
            // 获取json附加的信息
            if (StringUtils.isNotBlank(opsPurchaseinvoice.getInfojson())) {
                RequestPurchaseInfo info = JSONObject.parseObject(opsPurchaseinvoice.getInfojson(),
                        RequestPurchaseInfo.class);
                if (StringUtils.isNotBlank(info.getCorderno())) {
                    enduserInfo.setEnduserOrderNo(info.getCorderno());
                }
                if (StringUtils.isNotBlank(info.getCproductno())) {
                    enduserInfo.setEnduserModelNo(info.getCproductno()); //客户型号即物料编码
                }
            }
            // 用户所属营业所,需要按照客户代码去找对应的营业所
            enduserInfo.setEnduserSalesDepartmentNo(opsPurchaseinvoice.getDeptno());
            // bug 18271 OPS给PMS发单计算南北方标识的修改
            // bug19257 VBA型号订单由于客户的南北方标识不同导致分配错误,根据smccode来配置发单南北方客户
//            ResultVo<String> orderAreaInfo = inqbCommonFeignApi.findOrderAreaInfo(enduserInfo.getPurchaseEnduserNo(), opsPurchaseinvoice.getDeptno());
            String customerArea = this.getIpsCustomerTypeBySmccode(opsPurchaseinvoice.getSmccode());
            if (StringUtils.isNotBlank(customerArea)) {
                enduserInfo.setEnduserNs(IpsEndUserNsEnum.getCodeByDesc(customerArea));
//                log.info("南北方客户smccode转换：" + opsPurchaseinvoice.getSmccode() +"-"+ customerArea );
            }
            ipsReceiveContentInfoDto.setEnduserInfo(enduserInfo);
            // 2.5 开始为客户信息进行赋值
            customerInfo = new IpsCustomerInfoDto(); // 客户信息的json
            customerInfo.setPaymentDay("D180");
            customerInfo.setInqbNo(opsPurchaseinvoice.getInqno());
            // bug 13517 发送给psi的日期，格式采用年月日——时分秒的格式
            customerInfo.setIssueDate(DateUtil.dateToDateTimeString(new Date()));
            // bug 13517 增加客户订单号及客户型号4、、 写OPS订单号
            customerInfo.setCustomerOrderNo(opsPurchaseinvoice.getPono());
            customerInfo.setCustomerItemNo(opsPurchaseinvoice.getLineitem().toString());
            customerInfo.setShikomiNo(opsPurchaseinvoice.getShikomianswerno());
            customerInfo.setCustomerRemarks(opsPurchaseinvoice.getRemark());
            customerInfo.setCustomerServerremark(opsPurchaseinvoice.getServerremark());
            //            customerInfo.setTradeStyle(opsPurchaseinvoice.getDeliveryflag());
//            customerInfo.setWarehouseId(opsPurchaseinvoice.getHopereceivewarehouse());
//            customerInfo.setCustomerBarcode(opsPurchaseinvoice.getBarcode());
            customerInfo.setCustomerOrderType(opsPurchaseinvoice.getOrdtype());
            customerInfo.setSalesInformationNo(opsPurchaseinvoice.getSalesinfono());
            if (StringUtils.isNotBlank(opsPurchaseinvoice.getSalesinfono())) {
                customerInfo.setPriorInformationNumber(opsPurchaseinvoice.getSalesinfono().substring(0, 7));
                customerInfo.setPriorInformationLineNumber(opsPurchaseinvoice.getSalesinfono().substring(7));
            }
            // 查询交易主体
            RcvmasterExample rcvmasterExample = new RcvmasterExample();
            rcvmasterExample.createCriteria().andRorderNoEqualTo(opsPurchaseinvoice.getOrderno());
            List<Rcvmaster> rcvmasters = rcvmasterMapper.selectByExample(rcvmasterExample);
            if (CollectionUtils.isNotEmpty(rcvmasters)) {
                customerInfo.setCompanyCode(rcvmasters.get(0).getTradeCompanyid());
//                // 根据交易主体翻译出客户代码
//                if (StringUtils.isNotBlank(TradeCompanyIdEnum.getCustomerno(rcvmasters.get(0).getTradeCompanyid()))) {
//                    customerInfo.setCustomerNo(TradeCompanyIdEnum.getCustomerno(rcvmasters.get(0).getTradeCompanyid()));
//                }
            }
            // remarks信息
            String remarks = this.jpRemarkGenerator(opsPurchaseinvoice, userVipCode, dictResult);
            customerInfo.setRemarks(remarks);
            // 获取客户名称，即收货方名称
            ResultVo<String> accountResultVo = this.getIpsAccepterBySmccodeSupplierid(opsPurchaseinvoice.getSmccode(), opsPurchaseinvoice.getSupplierid());
            if (accountResultVo.isSuccess()) {
                customerInfo.setCustomerName(accountResultVo.getData());
            }
            customerInfo.setCustomerNo(opsPurchaseinvoice.getCustomerno());

            if (StringUtils.isNotBlank(opsPurchaseinvoice.getPrepareorderno()) && opsPurchaseinvoice.getPrepareorderno().startsWith("77")) {
                OpsPrepareOrderExample example = new OpsPrepareOrderExample();
                example.createCriteria().andOrderFnoEqualTo(opsPurchaseinvoice.getPrepareorderno()).andDelFlagEqualTo( false);
                List<OpsPrepareOrder> opsPrepareOrders = opsPrepareOrderMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(opsPrepareOrders)) {
                    customerInfo.setPrepareOrderNo(opsPrepareOrders.get(0).getManuOrderNo());
                }
            }
            customerInfo.setCustomerDeliveryDate(opsPurchaseinvoice.getHopedeliverydate()); // bug20877 WTSR2026000464-客户交货期推送给制造系统
            // customerInfo.setPrepareOrderNo(opsPurchaseinvoice.getPrepareorderno()); // bug20023 采购自动发单给psi时,传递准备订单号
            ipsReceiveContentInfoDto.setCustomerInfo(customerInfo);
            // 2.6 开始为供应商信息进行赋值
            ipsSupplierInfoDto = new IpsSupplierInfoDto();
            ipsSupplierInfoDto.setSupplierpartno(opsPurchaseinvoice.getSupplierpartno());
            ipsSupplierInfoDto.setSupplierPaymentday(supplierPayment);
            ipsSupplierInfoDto.setSupplierAllocationPriority(opsPurchaseinvoice.getSupplierAssignType());
            ipsReceiveContentInfoDto.setSupplierInfo(ipsSupplierInfoDto);
            // 2.7 当存在最低售价的附件时，需要增加对PSI的传输
            if (StringUtils.isNotBlank(opsPurchaseinvoice.getInfojson())) {
                ResultVo<IpsAddEmailInfoDto> psiEmailInfoResultVo = this.generatePsiEmailInfo(opsPurchaseinvoice);
                if (psiEmailInfoResultVo.isSuccess()) {
                    ipsAddEmailInfoDto = psiEmailInfoResultVo.getData();
                    ipsReceiveContentInfoDto.setAddEmailInfo(Arrays.asList(ipsAddEmailInfoDto)); // 将实体转换为list
                }
            }
            // 3.为contentInfo赋值
            ipsReceiveOrderContentAddDto.setContentInfo(JSONObject.toJSONString(ipsReceiveContentInfoDto));
            // 4.开始为原始表赋值
            // 获取当前系统IP
            ipsReceiveOrderAllOriginalInfoDto.setSourceType(InetAddress.getLocalHost().getHostAddress());
            ipsReceiveOrderAllOriginalInfoDto.setSourceSystem("OPS");
            ipsReceiveOrderAllOriginalInfoDto.setSrcOrderNo(opsPurchaseinvoice.getPono());
            ipsReceiveOrderAllOriginalInfoDto.setSrcOrderItemNo(opsPurchaseinvoice.getLineitem().toString());
            ipsReceiveOrderAllOriginalInfoDto.setContent(JSONObject.toJSONString(ipsReceiveOrderContentAddDto));
            ipsReceiveOrderAllOriginalInfoDto.setBatchNo(batchNo);
            ipsReceiveOrderAllOriginalInfoDto.setBatchNum(batchNum);
            ipsReceiveOrderAllOriginalInfoDto.setCreateTime(new Date());
            ipsReceiveOrderAllOriginalInfoDto.setCreateUser("OPS");
            ipsReceiveOrderAllOriginalInfoDtos.add(ipsReceiveOrderAllOriginalInfoDto);
        }
        return ipsReceiveOrderAllOriginalInfoDtos;
    }

    /**
     * 根据配置，获取OPS运输方式与IPS运输方式的转换
     * 根据配置，获取转换适配器供应商为IPS供应商
     * bug20054，发送PSI时，运输方式转换，不能用配置表
     * @return
     */
    @Override
    public ResultVo<String> getIpsTranstypeByConfig(String code, String codeType) {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(codeType)) {
            return ResultVo.failure("入参不可为空");
        }
        String key ="";
        if (codeType.equalsIgnoreCase("psi_transtype")) {
            key = IpsPurchaseCommonEnum.REDIS_KEY_TRANSTYPE_CONVERT.getCode() + code;
        } else {
            key = IpsPurchaseCommonEnum.REDIS_KEY_SUPPLIER_ADAPTER.getCode() + code;
        }
        Boolean hasKey = opsRedisUtils.hasKey(key);
        if (hasKey != null && hasKey) {
            Object o = opsRedisUtils.get(key);
            if(o != null) {
                return ResultVo.success(o.toString());
            }
        }
        AdapterOpsTranslationConfigExample example = new AdapterOpsTranslationConfigExample();
        example.createCriteria().andCodeTypeEqualTo(codeType).andOpsCodeEqualTo(code);
        List<AdapterOpsTranslationConfig> opsImpinvoiceFailLogDOList = adapterOpsTranslationConfigMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(opsImpinvoiceFailLogDOList)) {
            AdapterOpsTranslationConfig opsImpinvoiceFailLogDO = opsImpinvoiceFailLogDOList.get(0);
            opsRedisUtils.set(key,opsImpinvoiceFailLogDO.getAdapterCode(),60 * 60 * 24);
            return ResultVo.success(opsImpinvoiceFailLogDO.getAdapterCode());
        }
        return ResultVo.failure("未获取到"+code+"对应的IPS配置信息的转换");
    }

    @Override
    public IpsOrderWarehouseInfoDto getWarehouseInfo(String warehouseCode, String dictCode) {
        IpsOrderWarehouseInfoDto ipsOrderWarehouseInfoDto = new IpsOrderWarehouseInfoDto();
        // 是否从redis中获取字典明细
        if (StringUtils.isBlank(warehouseCode) || StringUtils.isBlank(dictCode)) {
            return null;
        }
        String key = IpsPurchaseCommonEnum.REDIS_KEY_WAREHOUSE_INFO.getCode() + warehouseCode;
        Boolean hasKey = opsRedisUtils.hasKey(key);
        if (hasKey != null && hasKey) {
            Object o = opsRedisUtils.get(key);
            if(o != null) {
                return JSONObject.parseObject(o.toString(), IpsOrderWarehouseInfoDto.class);
            }
        }
        ResultVo<DataTypeVO> dictResult = dictDataServiceFeignApi.getDataTypeCodesInfo(dictCode, warehouseCode);
        if (dictResult.isSuccess() && dictResult.getData() != null) {
            DataTypeVO warehouseInfo = dictResult.getData();
            ipsOrderWarehouseInfoDto.setOpsWarehouseCode(warehouseInfo.getCode());
//            ipsOrderWarehouseInfoDto.setReceivingWarehouse(warehouseInfo.getExtNote1()); // IPS收货仓代码
            ipsOrderWarehouseInfoDto.setReceivingAddressId(warehouseInfo.getExtNote1()); // IPS地址id
            ipsOrderWarehouseInfoDto.setReceivingAddress(warehouseInfo.getExtNote3()); // IPS收货地址
            // 剩余信息查询OPS——warehouse表，联系电话等等
            // 1.1查询接单信息,非必填的
            OpsWarehouseExample opsWarehouseExample = new OpsWarehouseExample();
            opsWarehouseExample.createCriteria().andWarehouseCodeEqualTo(warehouseCode);
            List<OpsWarehouse> warehouseList = opsWarehouseMapper.selectByExample(opsWarehouseExample);
            if (!CollectionUtils.isEmpty(warehouseList)) {
                OpsWarehouse opsWarehouse = warehouseList.get(0);
                ipsOrderWarehouseInfoDto.setReceivingConnector(opsWarehouse.getLinkman());
                ipsOrderWarehouseInfoDto.setReceivingDepartmentTeleNo(opsWarehouse.getLinkPhone());
//                ipsOrderWarehouseInfoDto.setPlantmarkAddress(opsWarehouse.getAdress());
                ipsOrderWarehouseInfoDto.setEnglishAddress(opsWarehouse.getEnglishAddress());
                ipsOrderWarehouseInfoDto.setEnglishLinkman(opsWarehouse.getEnglishLinkman());
            }
            opsRedisUtils.set(key, JSON.toJSONString(ipsOrderWarehouseInfoDto),60 * 60 * 24);
        }
        return ipsOrderWarehouseInfoDto;
    }

    @Override
    public ResultVo<String> generateIpsOrderBatchNo(String suppiler) {
        String localDate =  new SimpleDateFormat("yyyyMMdd").format(new Date());
        String key = IpsPurchaseCommonEnum.REDIS_KEY_IPS_BATCHNO.getCode() + suppiler;
        String batchReturn = null;
//        String batchReturn = purchaseOrderMasterMapper.getMaxBatchNo(localDate);
        try {
            Boolean hasKey = opsRedisUtils.hasKey(key);
            if (hasKey != null && hasKey) {
                Object redisNow = opsRedisUtils.get(key);
                // redis中是否存在有效的请购单号
                if (redisNow != null && StringUtils.isNotBlank(redisNow.toString())) {
                    batchReturn = redisNow.toString();
                }
            }
        } catch (Exception e) {
            log.error("IPS接单批次号生成失败" + e.toString());
        }
        // 递增申请号
        batchReturn = IpsBatchnoUtils.createIncrementIdIps(suppiler, new Date(), "yyMMdd", 4, batchReturn);
        try {
            // 尽力尝试回写Redis，即使失败也不影响申请号的返回
            opsRedisUtils.set(key, batchReturn, 60 * 60 * 24); // 回写redis
        } catch (Exception ex) {
            log.error("IPS接单批次号写入Redis失败，原因：" + ex.getMessage(), ex);
        }
        return ResultVo.success(batchReturn);
    }

    /**
     * 生成给PMS的订单备注的信息
     * @param opsPurchaseinvoice
     * @return
     */
    public String jpRemarkGenerator(OpsPurchaseinvoice opsPurchaseinvoice,List<OpsCustomerProperty> userVipCode,ResultVo<List<DataCodeVO>> dictResult) {
        // 追加 JPRemarks--21 备注 TSL/富士康/vIp/客户类别/组装子项
        StringBuffer returnRemarks = new StringBuffer();
            
        // BUG21116 新增：处理销售订单备注转换前缀（字典6014配置）
        String prefixPart = buildRemarkPrefixFromDict(opsPurchaseinvoice.getRemark(), dictResult);
        // 原有逻辑：VIP信息
        if (CollectionUtils.isNotEmpty(userVipCode)) {
            if (StringUtils.isBlank(returnRemarks)) {
                returnRemarks.append("重点客户:" + userVipCode.get(0).getVipcode());
            } else {
                returnRemarks.append("/重点客户:").append(userVipCode.get(0).getVipcode());
            }
        }
        // 最终用户逇营业所名称，
        String deptName = opsRequisitionCNDao.getDepartName(opsPurchaseinvoice.getDeptno());
        if (StringUtils.isNotBlank(deptName)) {
            if (CollectionUtils.isNotEmpty(userVipCode)) {
                returnRemarks.append("[" + deptName + ","
                        + userVipCode.get(0).getCustomerGroup() + "]");
            } else {
                returnRemarks.append("[" + deptName + "]");
            }
        } else if (CollectionUtils.isNotEmpty(userVipCode)) {
            returnRemarks.append("[" + userVipCode.get(0).getCustomerGroup() + "]");
        }
        if (opsPurchaseinvoice.getModelno().getBytes().length > 30) {
            returnRemarks.append("/" + opsPurchaseinvoice.getModelno());
        }
            
        // 合并前缀与原有备注
        String existingPart = returnRemarks.toString();
        if (StringUtils.isNotBlank(prefixPart) && StringUtils.isNotBlank(existingPart)) {
            return prefixPart + "," + existingPart;
        }
        if (StringUtils.isNotBlank(prefixPart)) {
            return prefixPart;
        }
        return existingPart != null ? existingPart : "";
    }
        
    /**
     * 根据字典6014配置，从原始备注中提取并转换前缀部分
     * @param sourceRemark 原始备注
     * @return 转换后的前缀字符串，多条用逗号分隔
     */
    private String buildRemarkPrefixFromDict(String sourceRemark,ResultVo<List<DataCodeVO>> dictResult) {
        if (StringUtils.isBlank(sourceRemark)) {
            return null;
        }
        if (!dictResult.isSuccess() || CollectionUtils.isEmpty(dictResult.getData())) {
            return null;
        }
        try {
            // 根据ExtNote1进行排序
            List<String> mappedTexts = new ArrayList<>();
            Set<String> addedSet = new HashSet<>(); // 用于去重
            // 遍历字典规则，执行包含匹配
            for (DataCodeVO rule : dictResult.getData()) {
                String containsText = rule.getCode(); // 字段1：包含字符串
                String mappedText = rule.getCodeName(); // 字段2：转换后字符串
                // 跳过无效配置
                if (StringUtils.isBlank(containsText) || StringUtils.isBlank(mappedText) ) {
                    continue;
                }
                // 使用 Pattern.compile().matcher().find() 进行正则匹配
//                if (sourceRemark.contains(containsText))
                Pattern pattern = Pattern.compile(Pattern.quote(containsText), Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(sourceRemark);
                if (matcher.find()) {
                    // 匹配成功
                    // 去重检查
                    if (!addedSet.contains(mappedText)) {
                        // 执行包含匹配
                        mappedTexts.add(mappedText);
                        addedSet.add(mappedText);
                    }
                }
            }
            // 拼接结果
            if (CollectionUtils.isEmpty(mappedTexts)) {
                return null;
            }
            return String.join(",", mappedTexts);
        } catch (Exception e) {
            log.error("处理销售订单备注转换规则时发生异常，降级使用原逻辑", e);
            return null;
        }
    }

    /**
     * 根据smccode和suppilyid获取收货人信息
     * bug
     * @return
     */
    public ResultVo<String> getIpsAccepterBySmccodeSupplierid(String smccode, String supplierid) {
        if (StringUtils.isBlank(supplierid) || StringUtils.isBlank(supplierid)) {
            return ResultVo.failure("入参不可为空");
        }
        String key = IpsPurchaseCommonEnum.REDIS_KEY_IPS_MANU_ACCEPTER.getCode() + smccode + supplierid;
        Boolean hasKey = opsRedisUtils.hasKey(key);
        if (hasKey != null && hasKey) {
            Object o = opsRedisUtils.get(key);
            if(o != null) {
                return ResultVo.success(o.toString());
            }
        }
        String accp = purchaseOrderDao.getAccepter(smccode, supplierid);
        if (StringUtils.isNotBlank(accp)) {
            opsRedisUtils.set(key,accp,60 * 60 * 24);
            return ResultVo.success(accp);
        }
        return ResultVo.failure("未获取到"+smccode+"对应的IPS的accepter配置信息的转换");
    }

    /**
     * 根据smccode获取南北方客户的标识
     * 读取字典6013的配置，默认为南方客户
     * @param smccode
     * @return
     */
    public String getIpsCustomerTypeBySmccode(String smccode) {
        if (StringUtils.isBlank(smccode) || StringUtils.isBlank(smccode)) {
            return null;
        }
        String key = IpsPurchaseCommonEnum.REDIS_KEY_IPS_MANU_CUSTOMERTYPE.getCode() + smccode;
        Boolean hasKey = opsRedisUtils.hasKey(key);
        if (hasKey != null && hasKey) {
            Object o = opsRedisUtils.get(key);
            if(o != null) {
                return o.toString();
            }
        }
        String type = IpsEndUserNsEnum.SOUTH.getCodeName();
        // redis中不存在时，查询字典明细
        ResultVo<DataTypeVO>  dictResult = dictDataServiceFeignApi.getDataTypeCodesInfo(IpsPurchaseCommonEnum.IPS_CUSTOMERTYPE_DICT.getCode(), smccode);
        if (dictResult.isSuccess()) {
            type = dictResult.getData().getExtNote1();
        }
        opsRedisUtils.set(key,type,60 * 60 * 24);
        return type;
    }



    // 回更采购表批次号cnno，公共方法提出
    @Override
    public void updatePurchaseInvoiceStatus(List<OpsPurchaseinvoice> invoiceList, String cnNo, String filePath) {
        // bug14866先更新采购再调用接口
        // 更新invoice 状态
        OpsPurchaseinvoice updatestateCode = new OpsPurchaseinvoice();
        updatestateCode.setStatecode(PurchaseOrderStatusEnum.YIFASONG);
        updatestateCode.setCnno(cnNo);
        updatestateCode.setSendtime(new Date());
        updatestateCode.setUpdatetime(new Date());
        if (StringUtils.isNotBlank(filePath)) {
            updatestateCode.setFilepath(filePath);
        }
        List<Long> idList = invoiceList.stream().map(a -> a.getId()).collect(Collectors.toList());
        OpsPurchaseinvoiceExample example2 = new OpsPurchaseinvoiceExample();
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
                // bug14866 更新purchaseInvoice增加更新三次重试，避免被牺牲
                int trynum = 3;
                boolean updateresult = false;
                while (!updateresult && trynum > 0) {
                    try {
                        opsPurchaseinvoiceMapper.updateByExampleSelective(updatestateCode, example2);
                        updateresult = true;
                    } catch (Exception e) {
                        trynum--;
                        if (trynum == 0 && !updateresult) {
                            log.error("采购发单更新purchaseinvoice三次尝试均失败。", e);
                            throw e;
                        }
                    }
                }
            }
        }
    }


    /**
     * 获取字典配置的新旧发单标识
     *
     * @param supplierid
     * @return
     */
    @Override
    public ResultVo<PsiDataType> getSwitchflag(String supplierid) {
        String swithchConfigValue;
        // 1.首先查询字典中的开关配置信息
        ResultVo<DataTypeVO> dictResult = dictDataServiceFeignApi.getDataTypeCodesInfo(IpsPurchaseCommonEnum.IPS_PURCHASE_SEND_DICT.getCode(), supplierid);
        DataTypeVO dictData = dictResult.getData();
        if (dictResult.isSuccess() && StringUtils.isNotBlank(dictResult.getData().getExtNote1())) {
            // 获取字典配置的值,后续使用
            swithchConfigValue = dictResult.getData().getExtNote1();
            // 判断dcit 配置的值，是否在字典中
            if (!IpsPurchaseSendSwitchEnum.isValidCode(swithchConfigValue)) {
                swithchConfigValue = IpsPurchaseSendSwitchEnum.OLD.getCode();
            }
            dictData.setExtNote1(swithchConfigValue);
        }
        // 转换后返回结果
        PsiDataType detailDO = BeanCopyUtil.copy(dictData, PsiDataType.class);
        return ResultVo.success(detailDO);
    }

    /**
     * 根据jsonInfo信息，转换生成PSI所需要的附件格式
     * @param purchaseinvoice
     * @return
     */
    @Override
    public ResultVo<IpsAddEmailInfoDto> generatePsiEmailInfo(OpsPurchaseinvoice purchaseinvoice) {
        // -------- 开始组装数据
        List<PsiFileInfoDto> fileList = new ArrayList<PsiFileInfoDto>();
        // 是否最低售价
        int flag = 0;
        // 标识：0发给日本、1发给业务
        int flagTo = 0;
        // 合并的情况
        List<OpsRequestpurchase> rList = basePoService.getRequestPurchaseByPurchase(purchaseinvoice.getOrderno(), purchaseinvoice.getItemno(),
                purchaseinvoice.getSplititemno());
        if (CollectionUtils.isNotEmpty(rList)) {
            OpsRequestpurchase i = rList.get(0);
            // 判断是否有最低售价
            if (StringUtils.isNotBlank(i.getInfojson())) {
                RequestPurchaseInfo info = JSONObject.parseObject(i.getInfojson(), RequestPurchaseInfo.class);
                if (info.isMinPrice()) {
                    flag = 1;
                    if (CollectionUtils.isNotEmpty(info.getFileList())) {
                        info.getFileList().forEach(a -> {
                            PsiFileInfoDto temp = new PsiFileInfoDto();
                            a.setBusinessType("po");
                            a.setCreateUser("po");
                            if (i.getSplititemno() == null) {
                                a.setBusinessKeyValue(i.getOrderno() + i.getItemno().toString());
                            } else {
                                a.setBusinessKeyValue(i.getOrderno() + i.getItemno().toString() + i.getSplititemno());
                            }
                            temp = temp.convert(a);
                            fileList.add(temp);
                        });
                        opsAttachedFileManageFeignApi.insertFileInfo(info.getFileList()); //  需要注意测试时，先将次步骤注释掉，等上正式时再打开注释
                    }
                }
            }
            if (StringUtils.equals("A", i.getWmtag())) {
                flagTo = 1;
            }
        }
        // 没有最低售价订单
        if (flag == 0 || CollectionUtils.isEmpty(fileList)) {
            return ResultVo.failure("暂无最低售价附件需要发送");
        }
        // 组装邮件发送的内容
        // 生成Psi实体内容
        IpsAddEmailInfoDto addEmailInfoDto = new IpsAddEmailInfoDto();
        String mailJp = "";
        String mailSales = "";
        String cc = "";
        String textToJP = "<h4>李文延先生，您好！</h4><h4>附件是价格限制品的合同。</h4><h4>价格高于最低售价，请查收。</h4><h4>订单请接入，谢谢！</h4><h4>系统自动发送，回复请联络 smccnorder@smc.com.cn wangyanping@smc.com.cn</h4>";
        String textToSales = "<h4>您好！</h4><h4>附件为型号拆分订单对应的最低售价附件，请确认！</h4>";
        ResultVo<List<DataCodeVO>> mail = dictDataServiceFeignApi.getDataCodes("3004");
        if (mail.isSuccess() && mail.getData() != null && mail.getData().size() == 3) {
            for (DataCodeVO v : mail.getData()) {
                if (StringUtils.equals("jp", v.getCode())) {
                    mailJp = v.getCodeName();
                }
                if (StringUtils.equals("sales", v.getCode())) {
                    mailSales = v.getCodeName();
                }
                if (StringUtils.equals("cc", v.getCode())) {
                    cc = v.getCodeName();
                    if (StringUtils.isNotBlank(v.getExtNote1())) {
                        cc = cc + "," + v.getExtNote1();
                    }
                    if (StringUtils.isNotBlank(v.getExtNote2())) {
                        cc = cc + "," + v.getExtNote2();
                    }
                    if (StringUtils.isNotBlank(v.getExtNote3())) {
                        cc = cc + "," + v.getExtNote3();
                    }
                }
            }
        } else {
            mailJp = "mateng@smc.com.cn";
            mailSales = "mateng@smc.com.cn";
            cc = "hesai@smc.com.cn";
            textToJP = "字典中未获取到邮箱信息，需要手工进行处理，需确认是正式数据还是测试数据，是否需要发送至日本。";
            textToSales = "字典中未获取到邮箱信息，需要手工进行处理，需确认是正式数据还是测试数据，是否需要发送至业务确认。";
        }
        addEmailInfoDto.setCc(cc);
        if (purchaseinvoice.getSplititemno() == null) {
            addEmailInfoDto.setSubject("订单号" + purchaseinvoice.getOrderno() + "-" + purchaseinvoice.getItemno().toString() + "价格限制品的订单合同");
        } else {
            addEmailInfoDto.setSubject("订单号" + purchaseinvoice.getOrderno() + "-" + purchaseinvoice.getItemno().toString() + "-"
                    + purchaseinvoice.getSplititemno().toString() + "价格限制品的订单合同");
        }
        if (flagTo == 0) {
            // 发送邮件给日本
            addEmailInfoDto.setBody(textToJP);
            addEmailInfoDto.setTo(mailJp);
        } else {
            // 型号拆分订货发邮件给业务
            addEmailInfoDto.setTo(mailSales);
            addEmailInfoDto.setBody(textToSales);
        }
        // 需要发邮件的附件实体
        if (CollectionUtils.isNotEmpty(fileList)) {
            addEmailInfoDto.setFileList(fileList);
        }
        // 推送数据的同时，需要调用
        return ResultVo.success(addEmailInfoDto);
    }


}
