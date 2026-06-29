package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInqbCodeConfig;
import com.sales.ops.db.entity.OpsInquiryCodeConfig;
import com.sales.ops.db.entity.Orderdlvdata;
import com.sales.ops.dto.inqb.InqbApplyVerifyReurn;
import com.sales.ops.dto.inqb.InqbQueryRequestParam;
import com.sales.ops.dto.inquiry.InquiryApplyVerifyReurn;
import com.sales.ops.dto.inquiry.InquiryCodeConfigBySuppily;
import com.sales.ops.dto.inquiry.InquiryOrderVerifyReurn;
import com.sales.ops.dto.inquiry.InquiryWorkdayCondition;
import com.sales.ops.dto.order.OpsOrderModifyDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.feign.OpsOrderFeignApi;
import com.sales.ops.feign.inqb.InqbAdapterFeignApi;
import com.sales.ops.feign.inquiry.InquiryAdapterFeignApi;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.InventoryTypeEnum;
import com.smc.smccloud.core.model.enums.OrderSplitTypeEnum;
import com.smc.smccloud.core.model.enums.OrderTypeEnum;
import com.smc.smccloud.core.model.enums.PurchaseTypeEnum;
import com.smc.smccloud.core.model.enums.*;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.CommonFormulaUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.Constants;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.OrderModify.OrderModifyDO;
import com.smc.smccloud.model.OrderSales.OrderDlvDataDO;
import com.smc.smccloud.model.OrderSales.OrderDlvDataVO;
import com.smc.smccloud.model.adapter.order.*;
import com.smc.smccloud.model.adapter.order.status.OrderItemStatusVO;
import com.smc.smccloud.model.adapter.orderEnum.StatusEnumInfo;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.deliveryType.OpsDeliveryTypeDO;
import com.smc.smccloud.model.enums.OrderStockTypeEnum;
import com.smc.smccloud.model.expdetail.ExpdetailDO;
import com.smc.smccloud.model.invoice.SalesInvoiceDO;
import com.smc.smccloud.model.order.*;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.ordermodify.OrderDeliveryModifyInfo;
import com.smc.smccloud.model.orderstate.DelOrderVO;
import com.smc.smccloud.model.orderstate.HandDelOrderStatusVO;
import com.smc.smccloud.model.orderstate.OrderStateDO;
import com.smc.smccloud.model.orderstate.OrderStateEnum;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import com.smc.smccloud.model.receiveorder.RcvDetailVO;
import com.smc.smccloud.model.supplier.SupplierVo;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author lyc
 * @Date 2022/3/9 17:01
 * @Descripton TODO
 */

@Service
@Slf4j
public class SMSAdapterServiceImpl implements SMSAdapterService {

    @Resource
    private RcvdetailMapper rcvdetailMapper;
    @Resource
    private RcvmasterMapper rcvmasterMapper;
    @Resource
    private OrderDlvDataService orderDlvDataService;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private OrderDlvDataMapper orderDlvDataMapper;
    @Resource
    private OrderStateMapper orderStateMapper;
    @Resource
    private OrderModifyMapper orderModifyMapper;
    @Resource
    private SalesInvoiceMapper salesInvoiceMapper;
    @Resource
    private ExpdetailMapper expdetailMapper;
    @Resource
    private CommonService commonService;
    @Resource
    private OpsOrderFeignApi opsOrderFeignApi;
    @Resource
    private RcvDetailMapperReadOnlyMapper rcvDetailMapperReadOnlyMapper;
    @Resource
    private RcvMasterReadOnlyMapper rcvMasterReadOnlyMapper;
    @Resource
    private OrderDlvDataMapperReadOnlyMapper orderDlvDataMapperReadOnlyMapper;
    @Resource
    private OrderStateMapperReadOnlyMapper orderStateMapperReadOnlyMapper;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private SMSOrderServiceFeignApi smsOrderServiceFeignApi;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private DictCommonService dictCommonService;
    @Resource
    private OpsDeliveryTypeMapper opsDeliveryTypeMapper;
    @Resource
    private RedisManager redisManager;

    @Autowired
    @Lazy
    private ReceiveOrderService receiveOrderService;
    @Resource
    private BinServiceFeignApi binServiceFeignApi;

    @Resource
    private InquiryAdapterFeignApi inquiryAdapterFeignApi;

    @Resource
    private InqbAdapterFeignApi inqbAdapterFeignApi;



    /**
     * 行业查询接口 -> 对应门户订单数据模块
     *
     * @param condition
     * @param pageNumber
     * @param pageSize
     * @return
     */
//    @Override
//    public ResultVo<PageInfo<IndCodeEntity>> queryRcvOrderWithCustomerByIndCode(SMSSearchListInfo condition, int pageNumber, int pageSize) {
//
//        if (condition == null) {
//            return ResultVo.failure("参数不可为空");
//        }
//
//        if (StringUtils.isNotBlank(condition.getOperator())) {
//            if (condition.getOperator().startsWith("ACC") || condition.getOperator().startsWith("acc")) {
//                return queryRcvOrderWithCustomerByIndCodeForAgent(condition,pageNumber,pageSize);
//            }
//        }
//
//        if (StringUtils.isNotBlank(condition.getOrderNo())) {
//            condition.setOrderNo(condition.getOrderNo() + "%");
//        }
//
//        if (StringUtils.isNotBlank(condition.getModelNo())) {
//            condition.setModelNo(condition.getModelNo() + "%");
//        }
//        if (StringUtils.isNotBlank(condition.getRemark())) {
//            condition.setRemark(condition.getRemark() + "%");
//        }
//        if (StringUtils.isNotBlank(condition.getCustProductNo())) {
//            condition.setCustProductNo(condition.getCustProductNo() + "%");
//        }
//        if (condition.getOrderDateStart() != null) {
//            condition.setOrderDateStartStr(DateUtil.getStartDateTimeStr(condition.getOrderDateStart()));
//        }
//
//        if (condition.getOrderDateEnd() != null) {
//            condition.setOrderDateEndStr(DateUtil.getEndDateTimeStr(condition.getOrderDateEnd()));
//        }
//
//
//        if (pageNumber == 0) {
//            pageNumber = 1;
//        }
//        if (pageSize == 0) {
//            pageSize = 10;
//        }
//        long lstart = System.currentTimeMillis();
//
//        List<String> types = new ArrayList<>();
//        if (StringUtils.isBlank(condition.getCustomerType())) {
//            types.add(OrderTypeEnum.saleOrder.getCode());
//            types.add(OrderTypeEnum.ypOrder.getCode());
//        } else {
//            types.add(condition.getCustomerType());
//        }
//        // 订单状态集合
//        List<String> status = new ArrayList<>();
//        // 采购供应商
//        List<String> purchasingSupplier = new ArrayList<>();
//        if (StringUtils.isNotBlank(condition.getStatus())) {
//
//            if (String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()).equals(condition.getStatus())) {
//                condition.setIntercepter(1);
//            } else {
//                status.add(condition.getStatus());
//                condition.setIntercepter(null);
//            }
//        }
//        // 快捷查询类型
//        if (StringUtils.isNotBlank(condition.getSearchType())) {
//            switch (condition.getSearchType()) {
//                case "1": // 日本采购中
//                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
//                    purchasingSupplier.add("JP");
//                    break;
//                case "2":  // 制造采购中
//                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
//                    ResultVo<List<SupplierVo>> chinaSuppliers = commonServiceFeignApi.findChinaSuppliers();
//                    if (chinaSuppliers.isSuccess() && chinaSuppliers.getData() != null) {
//                        for (SupplierVo item : chinaSuppliers.getData()) {
//                            purchasingSupplier.add(item.getCompanyId());
//                        }
//                    }
//                    break;
//                case "3": // 信用已拦截
//                    status.add(String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()));
//                    break;
//                default:
//                    break;
//            }
//        }
//        // 未发货状态
//        if (StringUtils.isNotBlank(condition.getDeliveryStatus())) {
//            switch (condition.getDeliveryStatus()) {
//                case "1":
//                    status.addAll(noSendOrderStatusList());
//                    break;
//                case "2":
//                    status.add(String.valueOf(RCVOrderStatusEnum.IN_DELIVERY.getCode()));
//                    break;
//                case "3":
//                    status.add(String.valueOf(RCVOrderStatusEnum.WAITING_DELIVERY.getCode()));
//                    break;
//                default:
//                    break;
//            }
//        }
//        // 排序字段转换
//        if (StringUtils.isNotBlank(condition.getProperty())) {
//            condition.setProperty(getSortFieldForOrderQuery4(condition.getProperty(), condition.getOrder()));
//        }
//
//        // 权限集合
//        List<String> userAuth = new ArrayList<>();
//        List<String> deptAuth = new ArrayList<>();
//        List<String> customerAuth = new ArrayList<>();
//        List<String> employeeAuth = new ArrayList<>();
//        List<String> inCodeAuth = new ArrayList<>();
//        if (condition.getDataAuthoritySearchCondition() != null) {
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getUserNoListByDataAuthority())) {
//                userAuth = condition.getDataAuthoritySearchCondition().getUserNoListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
//                deptAuth = condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
//                customerAuth = condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getUserIdListByDataAuthority())) {
//                employeeAuth = condition.getDataAuthoritySearchCondition().getUserIdListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority())) {
//                inCodeAuth = condition.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority();
//            }
//        }
//        List<String> finalUserAuth = userAuth;
//        List<String> finalDeptAuth = deptAuth;
//        List<String> finalCustomerAuth = customerAuth;
//        List<String> finalEmployeeAuth = employeeAuth;
//        List<String> finalInCodeAuth = inCodeAuth;
//
//        log.info("门户行业接单查询参数 : {} , 订单类型: {}, 订单状态: {}, 供应商: {} ",
//                JSONObject.toJSONString(condition), JSONObject.toJSONString(types), JSONObject.toJSONString(status),
//                JSONObject.toJSONString(purchasingSupplier));
//
//
//        if (StringUtils.isNotBlank(condition.getOrderNo()) && condition.getOrderNo().length() < 7) {
//            PageInfo<IndCodeEntity> objectPageInfo = new PageInfo<>();
//            return ResultVo.success(objectPageInfo);
//        }
//
//        PageInfo<IndCodeEntity> pageInfo = null;
//        try {
//            pageInfo = PageHelper.startPage(pageNumber, pageSize).doSelectPageInfo(() -> {
//                rcvDetailMapperReadOnlyMapper.queryRcvOrderByIndCode(condition, types, status, purchasingSupplier, finalUserAuth,finalDeptAuth,finalCustomerAuth,finalEmployeeAuth,finalInCodeAuth);
//            });
//        } catch (Exception e) {
//            if (e.getMessage().substring(25,100).contains("Timeout")) {
//                return ResultVo.failure("查询超时");
//            } else {
//                e.printStackTrace();
//                throw new RuntimeException(e.getMessage());
//            }
//        }
//
//        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
//
//            long lend = System.currentTimeMillis();
//            long consume = (lend - lstart) / 1000;
//            log.info("行业接单列表查询接口 SQL消耗时间(s) ==> " + consume);
//            List<IndCodeEntity> indCodeEntities = commonInCodeDataHand(pageInfo.getList());
//            pageInfo.setList(indCodeEntities);
////            Map<String, String> nameMap = new HashMap<>(pageInfo.getList().size());
////            String name;
////            List<String> noInterceptList = noIntercepterList();
////            for (IndCodeEntity smsOrder : pageInfo.getList()) {
////
////                if (smsOrder.getStatus() != null ) {
////                    if (noInterceptList.contains(String.valueOf(smsOrder.getStatus()))) {
////                        smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
////                    } else {
////                        // 判断是否拦截,若是拦截则显示信用拦截,反之显示订单状态
////                        if (smsOrder.getIntercept() != null) {
////                            if (smsOrder.getIntercept()) {
////                                smsOrder.setStatusName("信用拦截");
////                            } else {
////                                // 处理状态名称
////                                smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
////                            }
////                        } else {
////                            smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
////                        }
////                    }
////                }
////
////                smsOrder.setMainOrderNo(smsOrder.getRorderNo());
////
////                if (StringUtils.isNotBlank(smsOrder.getEmployeeNo())) {
////                    // 制单人id
////                    smsOrder.setCreateId(smsOrder.getEmployeeNo());
////                    // 制单人名称
////                    if (!nameMap.containsKey(smsOrder.getEmployeeNo())) {
////                        name = commonService.getEmpSaleNameByNo(smsOrder.getEmployeeNo());
////                        if (StringUtils.isNotBlank(name)) {
////                            nameMap.put(smsOrder.getEmployeeNo(), name);
////                        }
////                    }
////                    smsOrder.setCreateUserName(nameMap.getOrDefault(smsOrder.getEmployeeNo(), ""));
////                }
////
////                // 客户名称
////                if (StringUtils.isNotBlank(smsOrder.getCustomerNo())) {
////                    if (!nameMap.containsKey(smsOrder.getCustomerNo())) {
////                        name = commonService.getCustomerNameByNo(smsOrder.getCustomerNo());
////                        if (StringUtils.isNotBlank(name)) {
////                            nameMap.put(smsOrder.getCustomerNo(), name);
////                        }
////                    }
////                    smsOrder.setCustomerName(nameMap.get(smsOrder.getCustomerNo()));
////                }
////
////                // 客户担当名称
////                if (StringUtils.isNotBlank(smsOrder.getEmpSale())) {
////                    if (!nameMap.containsKey(smsOrder.getEmpSale())) {
////                        name = commonService.getEmpSaleNameByNo(smsOrder.getEmpSale());
////                        if (StringUtils.isNotBlank(name)) {
////                            nameMap.put(smsOrder.getEmpSale(), name);
////                        }
////                    }
////                    smsOrder.setEmpSaleName(nameMap.get(smsOrder.getEmpSale()));
////                }
////
////                // 用户名称
////                if (StringUtils.isNotBlank(smsOrder.getUserNo())) {
////                    if (!nameMap.containsKey(smsOrder.getUserNo())) {
////                        name = commonService.getCustomerNameByNo(smsOrder.getUserNo());
////                        if (StringUtils.isNotBlank(name)) {
////                            nameMap.put(smsOrder.getUserNo(), name);
////                        }
////                    }
////                    smsOrder.setUserName(nameMap.get(smsOrder.getUserNo()));
////                }
////
////                // 最终用户名称
////                if (StringUtils.isNotBlank(smsOrder.getEndUser())) {
////                    if (!nameMap.containsKey(smsOrder.getEndUser())) {
////                        name = commonService.getCustomerNameByNo(smsOrder.getEndUser());
////                        if (StringUtils.isNotBlank(name)) {
////                            nameMap.put(smsOrder.getEndUser(), name);
////                        }
////                    }
////                    smsOrder.setEndUserName(nameMap.get(smsOrder.getEndUser()));
////                }
////
////                // 如果最终用户价格为空 则取价格
////                if (smsOrder.getPriceEndUser() == null || smsOrder.getPriceEndUser().compareTo(BigDecimal.ZERO) == 0) {
////                    smsOrder.setPriceEndUser(smsOrder.getPrice());
////                }
////
////                // E率
////                if (smsOrder.getNtaxPice() != null && smsOrder.getNtaxPice().compareTo(BigDecimal.ZERO) != 0) {
////                    smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(smsOrder.getNtaxPice(), smsOrder.getEPrice()));
////                } else {
////                    // 不含税单价
////                    if (smsOrder.getPrice() == null) {
////                        smsOrder.setPrice(BigDecimal.ZERO);
////                    }
////                    BigDecimal noTaxPrice = CommonFormulaUtil.calcNoTaxPrice(smsOrder.getPrice(), smsOrder.getTaxRate());
////                    smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(noTaxPrice, smsOrder.getEPrice()));
////                }
////
////                // hl部门名称
////                if (StringUtils.isNotBlank(smsOrder.getHlCode())) {
////                    if (!nameMap.containsKey(smsOrder.getHlCode())) {
////                        name = commonService.getDeptNameByNo(smsOrder.getHlCode());
////                        if (StringUtils.isNotBlank(name)) {
////                            nameMap.put(smsOrder.getHlCode(), name);
////                        }
////                    }
////                    smsOrder.setHlCode(smsOrder.getHlCode());
////                    smsOrder.setHlCodeName(nameMap.get(smsOrder.getHlCode()));
////                } else {
////                    if (!nameMap.containsKey(smsOrder.getHrUnitId())) {
////                        name = commonService.getDeptNameByNo(smsOrder.getHrUnitId());
////                        if (StringUtils.isNotBlank(name)) {
////                            nameMap.put(smsOrder.getHrUnitId(), name);
////                        }
////                    }
////                    smsOrder.setHlCode(smsOrder.getHrUnitId());
////                    smsOrder.setHlCodeName(nameMap.get(smsOrder.getHrUnitId()));
////                }
////                // 出库区分类名称
////                if (StringUtils.isNotBlank(smsOrder.getStockType()) && StringUtils.isNotBlank(smsOrder.getStockCode())) {
////                    smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(smsOrder.getStockType()) + "[" + smsOrder.getStockCode() + "]");
////                } else if (StringUtils.isBlank(smsOrder.getStockType())) {
////                    smsOrder.setStockTypeName(smsOrder.getStockCode());
////                } else {
////                    smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(smsOrder.getStockType()));
////                }
////            }
//        }
//        long lend = System.currentTimeMillis();
//        long consume = (lend - lstart) / 1000;
//        log.info("行业接单列表查询接口  消耗时间(s) ==> " + consume);
//        return ResultVo.success(pageInfo);
//
//    }

    @Override
    public ResultVo<PageInfo<IndCodeEntity>> queryRcvOrderWithCustomerByIndCodeForAgent(SMSSearchListInfo condition, int pageNumber, int pageSize) {
        if (condition == null) {
            return ResultVo.failure("参数不可为空");
        }

        PageInfo<IndCodeEntity> pageInfo = null;
        if (CollectionUtils.isEmpty(condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
            pageInfo = new PageInfo<>();
            return ResultVo.success(pageInfo);
        }

        if (StringUtils.isNotBlank(condition.getOrderNo())) {
            condition.setOrderNo(condition.getOrderNo() + "%");
        }

        if (StringUtils.isNotBlank(condition.getModelNo())) {
            condition.setModelNo(condition.getModelNo() + "%");
        }
        if (StringUtils.isNotBlank(condition.getRemark())) {
            condition.setRemark(condition.getRemark() + "%");
        }
        if (StringUtils.isNotBlank(condition.getCustProductNo())) {
            condition.setCustProductNo(condition.getCustProductNo() + "%");
        }
        if (condition.getOrderDateStart() != null) {
            condition.setOrderDateStartStr(DateUtil.getStartDateTimeStr(condition.getOrderDateStart()));
        }

        if (condition.getOrderDateEnd() != null) {
            condition.setOrderDateEndStr(DateUtil.getEndDateTimeStr(condition.getOrderDateEnd()));
        }

        if (pageNumber == 0) {
            pageNumber = 1;
        }
        if (pageSize == 0) {
            pageSize = 10;
        }
        long lstart = System.currentTimeMillis();

        List<String> types = new ArrayList<>();
        if (StringUtils.isBlank(condition.getCustomerType())) {
            types.add(OrderTypeEnum.saleOrder.getCode());
            types.add(OrderTypeEnum.ypOrder.getCode());
        } else {
            types.add(condition.getCustomerType());
        }
        // 订单状态集合
        List<String> status = new ArrayList<>();
        // 采购供应商
        List<String> purchasingSupplier = new ArrayList<>();
        if (StringUtils.isNotBlank(condition.getStatus())) {

            if (String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()).equals(condition.getStatus())) {
                condition.setIntercepter(1);
            } else {
                status.add(condition.getStatus());
                condition.setIntercepter(null);
            }
        }
        // 快捷查询类型
        if (StringUtils.isNotBlank(condition.getSearchType())) {
            switch (condition.getSearchType()) {
                case "1": // 日本采购中
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
                    purchasingSupplier.add("JP");
                    break;
                case "2":  // 制造采购中
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
                    ResultVo<List<SupplierVo>> chinaSuppliers = commonServiceFeignApi.findChinaSuppliers();
                    if (chinaSuppliers.isSuccess() && chinaSuppliers.getData() != null) {
                        for (SupplierVo item : chinaSuppliers.getData()) {
                            purchasingSupplier.add(item.getCompanyId());
                        }
                    }
                    break;
                case "3": // 信用已拦截
                    status.add(String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()));
                    break;
                default:
                    break;
            }
        }
        // 未发货状态
        if (StringUtils.isNotBlank(condition.getDeliveryStatus())) {
            switch (condition.getDeliveryStatus()) {
                case "1":
                    status.addAll(noSendOrderStatusList());
                    break;
                case "2":
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_DELIVERY.getCode()));
                    break;
                case "3":
                    status.add(String.valueOf(RCVOrderStatusEnum.WAITING_DELIVERY.getCode()));
                    break;
                default:
                    break;
            }
        }
        // 排序字段转换
        if (StringUtils.isNotBlank(condition.getProperty())) {
            condition.setProperty(getSortFieldForOrderQueryWithAgent(condition.getProperty(), condition.getOrder()));
        }

        // 部门权限
        List<String> deptNos = new ArrayList<>();
        if (condition.getDataAuthoritySearchCondition() != null) {
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
                deptNos = condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
            }
        }
        List<String> finalDeptNos = deptNos;

        log.info("门户行业接单查询参数 : {} , 订单类型: {}, 订单状态: {}, 供应商: {} ",
                JSONObject.toJSONString(condition), JSONObject.toJSONString(types), JSONObject.toJSONString(status),
                JSONObject.toJSONString(purchasingSupplier));


        if (StringUtils.isNotBlank(condition.getOrderNo()) && condition.getOrderNo().length() < 7) {
            PageInfo<IndCodeEntity> objectPageInfo = new PageInfo<>();
            return ResultVo.success(objectPageInfo);
        }

        try {
            pageInfo = PageHelper.startPage(pageNumber, pageSize).doSelectPageInfo(() -> {
                rcvDetailMapperReadOnlyMapper.queryRcvOrderByIndCodeForAgent(condition, types, status, purchasingSupplier, finalDeptNos);
            });
        } catch (Exception e) {
            if (e.getMessage().substring(25,100).contains("Timeout")) {
                return ResultVo.failure("查询超时");
            } else {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {

            long lend = System.currentTimeMillis();
            long consume = (lend - lstart) / 1000;
            log.info("行业接单列表查询接口 c view SQL消耗时间(s) ==> " + consume);
            List<IndCodeEntity> indCodeEntities = commonInCodeDataHand(pageInfo.getList());
            pageInfo.setList(indCodeEntities);
//            Map<String, String> nameMap = new HashMap<>(pageInfo.getList().size());
//            String name;
//            List<String> noInterceptList = noIntercepterList();
//            for (IndCodeEntity smsOrder : pageInfo.getList()) {
//
//                if (smsOrder.getStatus() != null ) {
//                    if (noInterceptList.contains(String.valueOf(smsOrder.getStatus()))) {
//                        smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
//                    } else {
//                        // 判断是否拦截,若是拦截则显示信用拦截,反之显示订单状态
//                        if (smsOrder.getIntercept() != null) {
//                            if (smsOrder.getIntercept()) {
//                                smsOrder.setStatusName("信用拦截");
//                            } else {
//                                // 处理状态名称
//                                smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
//                            }
//                        } else {
//                            smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
//                        }
//                    }
//                }
//
//
//                if (StringUtils.isNotBlank(smsOrder.getEmployeeNo())) {
//                    // 制单人id
//                    smsOrder.setCreateId(smsOrder.getEmployeeNo());
//                    // 制单人名称
//                    if (!nameMap.containsKey(smsOrder.getEmployeeNo())) {
//                        name = commonService.getEmpSaleNameByNo(smsOrder.getEmployeeNo());
//                        if (StringUtils.isNotBlank(name)) {
//                            nameMap.put(smsOrder.getEmployeeNo(), name);
//                        }
//                    }
//                    smsOrder.setCreateUserName(nameMap.getOrDefault(smsOrder.getEmployeeNo(), ""));
//                }
//
//                smsOrder.setMainOrderNo(smsOrder.getRorderNo());
//
//                // 客户名称
//                if (StringUtils.isNotBlank(smsOrder.getCustomerNo())) {
//                    if (!nameMap.containsKey(smsOrder.getCustomerNo())) {
//                        name = commonService.getCustomerNameByNo(smsOrder.getCustomerNo());
//                        if (StringUtils.isNotBlank(name)) {
//                            nameMap.put(smsOrder.getCustomerNo(), name);
//                        }
//                    }
//                    smsOrder.setCustomerName(nameMap.get(smsOrder.getCustomerNo()));
//                }
//                // 用户名称
//                if (StringUtils.isNotBlank(smsOrder.getUserNo())) {
//                    if (!nameMap.containsKey(smsOrder.getUserNo())) {
//                        name = commonService.getCustomerNameByNo(smsOrder.getUserNo());
//                        if (StringUtils.isNotBlank(name)) {
//                            nameMap.put(smsOrder.getUserNo(), name);
//                        }
//                    }
//                    smsOrder.setUserName(nameMap.get(smsOrder.getUserNo()));
//                }
//
//                // 最终用户名称
//                if (StringUtils.isNotBlank(smsOrder.getEndUser())) {
//                    if (!nameMap.containsKey(smsOrder.getEndUser())) {
//                        name = commonService.getCustomerNameByNo(smsOrder.getEndUser());
//                        if (StringUtils.isNotBlank(name)) {
//                            nameMap.put(smsOrder.getEndUser(), name);
//                        }
//                    }
//                    smsOrder.setEndUserName(nameMap.get(smsOrder.getEndUser()));
//                }
//
//                // 如果最终用户价格为空 则取价格
//                if (smsOrder.getPriceEndUser() == null || smsOrder.getPriceEndUser().compareTo(BigDecimal.ZERO) == 0) {
//                    smsOrder.setPriceEndUser(smsOrder.getPrice());
//                }
//
//                // E率
//                if (smsOrder.getNtaxPice() != null && smsOrder.getNtaxPice().compareTo(BigDecimal.ZERO) != 0) {
//                    smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(smsOrder.getNtaxPice(), smsOrder.getEPrice()));
//                } else {
//                    // 不含税单价
//                    if (smsOrder.getPrice() == null) {
//                        smsOrder.setPrice(BigDecimal.ZERO);
//                    }
//                    BigDecimal noTaxPrice = CommonFormulaUtil.calcNoTaxPrice(smsOrder.getPrice(), smsOrder.getTaxRate());
//                    smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(noTaxPrice, smsOrder.getEPrice()));
//                }
//
//                // hl部门名称
//                if (StringUtils.isNotBlank(smsOrder.getHlCode())) {
//                    if (!nameMap.containsKey(smsOrder.getHlCode())) {
//                        name = commonService.getDeptNameByNo(smsOrder.getHlCode());
//                        if (StringUtils.isNotBlank(name)) {
//                            nameMap.put(smsOrder.getHlCode(), name);
//                        }
//                    }
//                    smsOrder.setHlCode(smsOrder.getHlCode());
//                    smsOrder.setHlCodeName(nameMap.get(smsOrder.getHlCode()));
//                } else {
//                    if (!nameMap.containsKey(smsOrder.getHrUnitId())) {
//                        name = commonService.getDeptNameByNo(smsOrder.getHrUnitId());
//                        if (StringUtils.isNotBlank(name)) {
//                            nameMap.put(smsOrder.getHrUnitId(), name);
//                        }
//                    }
//                    smsOrder.setHlCode(smsOrder.getHrUnitId());
//                    smsOrder.setHlCodeName(nameMap.get(smsOrder.getHrUnitId()));
//                }
//                // 出库区分类名称
//                if (StringUtils.isNotBlank(smsOrder.getStockType()) && StringUtils.isNotBlank(smsOrder.getStockCode())) {
//                    smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(smsOrder.getStockType()) + "[" + smsOrder.getStockCode() + "]");
//                } else if (StringUtils.isBlank(smsOrder.getStockType())) {
//                    smsOrder.setStockTypeName(smsOrder.getStockCode());
//                } else {
//                    smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(smsOrder.getStockType()));
//                }
//            }
        }
        long lend = System.currentTimeMillis();
        long consume = (lend - lstart) / 1000;
        log.info("行业接单列表查询接口  消耗时间(s) ==> " + consume);
        return ResultVo.success(pageInfo);
    }

//    @Override
//    public ResultVo<List<IndCodeEntity>> exportIndCodeData(SMSSearchListInfo condition) {
//
//        if (condition == null) {
//            return ResultVo.failure("参数不可为空");
//        }
//
//        if (StringUtils.isNotBlank(condition.getOperator())) {
//            if (condition.getOperator().startsWith("ACC") || condition.getOperator().startsWith("acc")) {
//                return exportIndCodeDataForAgent(condition);
//            }
//        }
//
//        if (StringUtils.isNotBlank(condition.getOrderNo())) {
//            condition.setOrderNo(condition.getOrderNo() + "%");
//        }
//
//        if (StringUtils.isNotBlank(condition.getModelNo())) {
//            condition.setModelNo(condition.getModelNo() + "%");
//        }
//        if (StringUtils.isNotBlank(condition.getRemark())) {
//            condition.setRemark(condition.getRemark() + "%");
//        }
//        if (StringUtils.isNotBlank(condition.getCustProductNo())) {
//            condition.setCustProductNo(condition.getCustProductNo() + "%");
//        }
//        if (condition.getOrderDateStart() != null) {
//            condition.setOrderDateStartStr(DateUtil.getStartDateTimeStr(condition.getOrderDateStart()));
//        }
//
//        if (condition.getOrderDateEnd() != null) {
//            condition.setOrderDateEndStr(DateUtil.getEndDateTimeStr(condition.getOrderDateEnd()));
//        }
//
//        long lstart = System.currentTimeMillis();
//
//        List<String> types = new ArrayList<>();
//        if (StringUtils.isBlank(condition.getCustomerType())) {
//            types.add(OrderTypeEnum.saleOrder.getCode());
//            types.add(OrderTypeEnum.ypOrder.getCode());
//        } else {
//            types.add(condition.getCustomerType());
//        }
//        // 订单状态集合
//        List<String> status = new ArrayList<>();
//        // 采购供应商
//        List<String> purchasingSupplier = new ArrayList<>();
//        if (StringUtils.isNotBlank(condition.getStatus())) {
//
//            if (String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()).equals(condition.getStatus())) {
//                condition.setIntercepter(1);
//            } else {
//                status.add(condition.getStatus());
//                condition.setIntercepter(null);
//            }
//        }
//        // 快捷查询类型
//        if (StringUtils.isNotBlank(condition.getSearchType())) {
//            switch (condition.getSearchType()) {
//                case "1": // 日本采购中
//                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
//                    purchasingSupplier.add("JP");
//                    break;
//                case "2":  // 制造采购中
//                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
//                    ResultVo<List<SupplierVo>> chinaSuppliers = commonServiceFeignApi.findChinaSuppliers();
//                    if (chinaSuppliers.isSuccess() && chinaSuppliers.getData() != null) {
//                        for (SupplierVo item : chinaSuppliers.getData()) {
//                            purchasingSupplier.add(item.getCompanyId());
//                        }
//                    }
//                    break;
//                case "3": // 信用已拦截
//                    status.add(String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()));
//                    break;
//                default:
//                    break;
//            }
//        }
//        // 未发货状态
//        if (StringUtils.isNotBlank(condition.getDeliveryStatus())) {
//            switch (condition.getDeliveryStatus()) {
//                case "1":
//                    status.addAll(noSendOrderStatusList());
//                    break;
//                case "2":
//                    status.add(String.valueOf(RCVOrderStatusEnum.IN_DELIVERY.getCode()));
//                    break;
//                case "3":
//                    status.add(String.valueOf(RCVOrderStatusEnum.WAITING_DELIVERY.getCode()));
//                    break;
//                default:
//                    break;
//            }
//        }
//        // 排序字段转换
//        if (StringUtils.isNotBlank(condition.getProperty())) {
//            condition.setProperty(getSortFieldForOrderQuery4(condition.getProperty(), condition.getOrder()));
//        }
//
//        // 权限集合
//        List<String> userAuth = new ArrayList<>();
//        List<String> deptAuth = new ArrayList<>();
//        List<String> customerAuth = new ArrayList<>();
//        List<String> employeeAuth = new ArrayList<>();
//        List<String> inCodeAuth = new ArrayList<>();
//        if (condition.getDataAuthoritySearchCondition() != null) {
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getUserNoListByDataAuthority())) {
//                userAuth = condition.getDataAuthoritySearchCondition().getUserNoListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
//                deptAuth = condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
//                customerAuth = condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getUserIdListByDataAuthority())) {
//                employeeAuth = condition.getDataAuthoritySearchCondition().getUserIdListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority())) {
//                inCodeAuth = condition.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority();
//            }
//        }
//        List<String> finalUserAuth = userAuth;
//        List<String> finalDeptAuth = deptAuth;
//        List<String> finalCustomerAuth = customerAuth;
//        List<String> finalEmployeeAuth = employeeAuth;
//        List<String> finalInCodeAuth = inCodeAuth;
//
//        log.info("门户行业接单查询导出参数 : {} , 订单类型: {}, 订单状态: {}, 供应商: {} ",
//                JSONObject.toJSONString(condition), JSONObject.toJSONString(types), JSONObject.toJSONString(status),
//                JSONObject.toJSONString(purchasingSupplier));
//
//
//        if (StringUtils.isNotBlank(condition.getOrderNo()) && condition.getOrderNo().length() < 7) {
//            return ResultVo.success(new ArrayList<>());
//        }
//
//        List<IndCodeEntity> indCodeEntities = null;
//        try {
//            indCodeEntities = rcvDetailMapperReadOnlyMapper.exportInCode(condition, types, status, purchasingSupplier, finalUserAuth,finalDeptAuth,finalCustomerAuth,finalEmployeeAuth,finalInCodeAuth);
//        } catch (Exception e) {
//            if (e.getMessage().substring(25,100).contains("Timeout")) {
//                return ResultVo.failure("查询超时");
//            } else {
//                e.printStackTrace();
//                throw new RuntimeException(e.getMessage());
//            }
//        }
//        if (CollectionUtils.isEmpty(indCodeEntities)) {
//            return ResultVo.success(new ArrayList<>());
//        }
//        indCodeEntities = commonInCodeDataHand(indCodeEntities);
////        Map<String, String> nameMap = new HashMap<>(indCodeEntities.size());
////        String name;
////
////        List<String> noInterceptList = noIntercepterList();
////
////        for (IndCodeEntity smsOrder : indCodeEntities) {
////
////            if (smsOrder.getStatus() != null ) {
////                if (noInterceptList.contains(String.valueOf(smsOrder.getStatus()))) {
////                    smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
////                } else {
////                    // 判断是否拦截,若是拦截则显示信用拦截,反之显示订单状态
////                    if (smsOrder.getIntercept() != null) {
////                        if (smsOrder.getIntercept()) {
////                            smsOrder.setStatusName("信用拦截");
////                        } else {
////                            // 处理状态名称
////                            smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
////                        }
////                    } else {
////                        smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
////                    }
////                }
////            }
////
////            smsOrder.setMainOrderNo(smsOrder.getRorderNo());
////
////            if (StringUtils.isNotBlank(smsOrder.getEmployeeNo())) {
////                // 制单人id
////                smsOrder.setCreateId(smsOrder.getEmployeeNo());
////                // 制单人名称
////                if (!nameMap.containsKey(smsOrder.getEmployeeNo())) {
////                    name = commonService.getEmpSaleNameByNo(smsOrder.getEmployeeNo());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(smsOrder.getEmployeeNo(), name);
////                    }
////                }
////                smsOrder.setCreateUserName(nameMap.getOrDefault(smsOrder.getEmployeeNo(), ""));
////            }
////
////            // 客户名称
////            if (StringUtils.isNotBlank(smsOrder.getCustomerNo())) {
////                if (!nameMap.containsKey(smsOrder.getCustomerNo())) {
////                    name = commonService.getCustomerNameByNo(smsOrder.getCustomerNo());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(smsOrder.getCustomerNo(), name);
////                    }
////                }
////                smsOrder.setCustomerName(nameMap.get(smsOrder.getCustomerNo()));
////            }
////
////            // 客户担当名称
////            if (StringUtils.isNotBlank(smsOrder.getEmpSale())) {
////                if (!nameMap.containsKey(smsOrder.getEmpSale())) {
////                    name = commonService.getEmpSaleNameByNo(smsOrder.getEmpSale());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(smsOrder.getEmpSale(), name);
////                    }
////                }
////                smsOrder.setEmpSaleName(nameMap.get(smsOrder.getEmpSale()));
////            }
////
////            // 用户名称
////            if (StringUtils.isNotBlank(smsOrder.getUserNo())) {
////                if (!nameMap.containsKey(smsOrder.getUserNo())) {
////                    name = commonService.getCustomerNameByNo(smsOrder.getUserNo());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(smsOrder.getUserNo(), name);
////                    }
////                }
////                smsOrder.setUserName(nameMap.get(smsOrder.getUserNo()));
////            }
////
////            // 最终用户名称
////            if (StringUtils.isNotBlank(smsOrder.getEndUser())) {
////                if (!nameMap.containsKey(smsOrder.getEndUser())) {
////                    name = commonService.getCustomerNameByNo(smsOrder.getEndUser());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(smsOrder.getEndUser(), name);
////                    }
////                }
////                smsOrder.setEndUserName(nameMap.get(smsOrder.getEndUser()));
////            }
////
////            // 如果最终用户价格为空 则取价格
////            if (smsOrder.getPriceEndUser() == null || smsOrder.getPriceEndUser().compareTo(BigDecimal.ZERO) == 0) {
////                smsOrder.setPriceEndUser(smsOrder.getPrice());
////            }
////
////            // E率
////            if (smsOrder.getNtaxPice() != null && smsOrder.getNtaxPice().compareTo(BigDecimal.ZERO) != 0) {
////                smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(smsOrder.getNtaxPice(), smsOrder.getEPrice()));
////            } else {
////                // 不含税单价
////                if (smsOrder.getPrice() == null) {
////                    smsOrder.setPrice(BigDecimal.ZERO);
////                }
////                BigDecimal noTaxPrice = CommonFormulaUtil.calcNoTaxPrice(smsOrder.getPrice(), smsOrder.getTaxRate());
////                smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(noTaxPrice, smsOrder.getEPrice()));
////            }
////
////            // hl部门名称
////            if (StringUtils.isNotBlank(smsOrder.getHlCode())) {
////                if (!nameMap.containsKey(smsOrder.getHlCode())) {
////                    name = commonService.getDeptNameByNo(smsOrder.getHlCode());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(smsOrder.getHlCode(), name);
////                    }
////                }
////                smsOrder.setHlCode(smsOrder.getHlCode());
////                smsOrder.setHlCodeName(nameMap.get(smsOrder.getHlCode()));
////            } else {
////                if (!nameMap.containsKey(smsOrder.getHrUnitId())) {
////                    name = commonService.getDeptNameByNo(smsOrder.getHrUnitId());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(smsOrder.getHrUnitId(), name);
////                    }
////                }
////                smsOrder.setHlCode(smsOrder.getHrUnitId());
////                smsOrder.setHlCodeName(nameMap.get(smsOrder.getHrUnitId()));
////            }
////            // 出库区分类名称
////            if (StringUtils.isNotBlank(smsOrder.getStockType()) && StringUtils.isNotBlank(smsOrder.getStockCode())) {
////                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(smsOrder.getStockType()) + "[" + smsOrder.getStockCode() + "]");
////            } else if (StringUtils.isBlank(smsOrder.getStockType())) {
////                smsOrder.setStockTypeName(smsOrder.getStockCode());
////            } else {
////                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(smsOrder.getStockType()));
////            }
////        }
//
//        long lend = System.currentTimeMillis();
//        long consume = (lend - lstart) / 1000;
//        log.info("行业接单列表查询导出接口 消耗时间(s) ==> " + consume);
//        return ResultVo.success(indCodeEntities);
//    }

    @Override
    public ResultVo<List<IndCodeEntity>> exportIndCodeDataForAgent(SMSSearchListInfo condition) {
        if (condition == null) {
            return ResultVo.failure("参数不可为空");
        }

        if (CollectionUtils.isEmpty(condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
            return ResultVo.success(new ArrayList<>());
        }

        if (StringUtils.isNotBlank(condition.getOrderNo())) {
            condition.setOrderNo(condition.getOrderNo() + "%");
        }

        if (StringUtils.isNotBlank(condition.getModelNo())) {
            condition.setModelNo(condition.getModelNo() + "%");
        }
        if (StringUtils.isNotBlank(condition.getRemark())) {
            condition.setRemark(condition.getRemark() + "%");
        }
        if (StringUtils.isNotBlank(condition.getCustProductNo())) {
            condition.setCustProductNo(condition.getCustProductNo() + "%");
        }
        if (condition.getOrderDateStart() != null) {
            condition.setOrderDateStartStr(DateUtil.getStartDateTimeStr(condition.getOrderDateStart()));
        }

        if (condition.getOrderDateEnd() != null) {
            condition.setOrderDateEndStr(DateUtil.getEndDateTimeStr(condition.getOrderDateEnd()));
        }

        long lstart = System.currentTimeMillis();

        List<String> types = new ArrayList<>();
        if (StringUtils.isBlank(condition.getCustomerType())) {
            types.add(OrderTypeEnum.saleOrder.getCode());
            types.add(OrderTypeEnum.ypOrder.getCode());
        } else {
            types.add(condition.getCustomerType());
        }
        // 订单状态集合
        List<String> status = new ArrayList<>();
        // 采购供应商
        List<String> purchasingSupplier = new ArrayList<>();

        // add by LiYingChao from bug 8478 in 20221026
        if (StringUtils.isNotBlank(condition.getStatus())) {
            // status.add(condition.getStatus());
            if (String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()).equals(condition.getStatus())) {
                condition.setIntercepter(1);
            } else {
                status.add(condition.getStatus());
                condition.setIntercepter(null);
            }
        }
        // 快捷查询类型
        if (StringUtils.isNotBlank(condition.getSearchType())) {
            switch (condition.getSearchType()) {
                case "1": // 日本采购中
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
                    purchasingSupplier.add("JP");
                    break;
                case "2":  // 制造采购中
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
                    ResultVo<List<SupplierVo>> chinaSuppliers = commonServiceFeignApi.findChinaSuppliers();
                    if (chinaSuppliers.isSuccess() && chinaSuppliers.getData() != null) {
                        for (SupplierVo item : chinaSuppliers.getData()) {
                            purchasingSupplier.add(item.getCompanyId());
                        }
                    }
                    break;
                case "3": // 信用已拦截
                    status.add(String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()));
                    break;
                default:
                    break;
            }
        }
        // 未发货状态
        if (StringUtils.isNotBlank(condition.getDeliveryStatus())) {
            switch (condition.getDeliveryStatus()) {
                case "1":
                    status.addAll(noSendOrderStatusList());
                    break;
                case "2":
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_DELIVERY.getCode()));
                    break;
                case "3":
                    status.add(String.valueOf(RCVOrderStatusEnum.WAITING_DELIVERY.getCode()));
                    break;
                default:
                    break;
            }
        }
        // 排序字段转换
        if (StringUtils.isNotBlank(condition.getProperty())) {
            condition.setProperty(getSortFieldForOrderQueryWithAgent(condition.getProperty(), condition.getOrder()));
        }

        // 部门权限
        List<String> deptNos = new ArrayList<>();
        if (condition.getDataAuthoritySearchCondition() != null) {
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
                deptNos = condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
            }
        }
        List<String> finalDeptNos = deptNos;

        log.info("门户行业接单查询导出参数 : {} , 订单类型: {}, 订单状态: {}, 供应商: {} ",
                JSONObject.toJSONString(condition), JSONObject.toJSONString(types), JSONObject.toJSONString(status),
                JSONObject.toJSONString(purchasingSupplier));


        if (StringUtils.isNotBlank(condition.getOrderNo()) && condition.getOrderNo().length() < 7) {
            return ResultVo.success(new ArrayList<>());
        }

        List<IndCodeEntity> indCodeEntities = null;
        try {
            indCodeEntities = rcvDetailMapperReadOnlyMapper.exportInCodeForAgent(condition, types, status, purchasingSupplier, finalDeptNos);
        } catch (Exception e) {
            if (e.getMessage().substring(25,100).contains("Timeout")) {
                return ResultVo.failure("查询超时");
            } else {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        if (CollectionUtils.isEmpty(indCodeEntities)) {
            return ResultVo.success(new ArrayList<>());
        }
        indCodeEntities = commonInCodeDataHand(indCodeEntities);

//        Map<String, String> nameMap = new HashMap<>(indCodeEntities.size());
//        String name;
//        List<String> noInterceptList = noIntercepterList();
//        for (IndCodeEntity smsOrder : indCodeEntities) {
//
//            if (smsOrder.getStatus() != null ) {
//                if (noInterceptList.contains(String.valueOf(smsOrder.getStatus()))) {
//                    smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
//                } else {
//                    // 判断是否拦截,若是拦截则显示信用拦截,反之显示订单状态
//                    if (smsOrder.getIntercept() != null) {
//                        if (smsOrder.getIntercept()) {
//                            smsOrder.setStatusName("信用拦截");
//                        } else {
//                            // 处理状态名称
//                            smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
//                        }
//                    } else {
//                        smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
//                    }
//                }
//            }
//
//
//            if (StringUtils.isNotBlank(smsOrder.getEmployeeNo())) {
//                // 制单人id
//                smsOrder.setCreateId(smsOrder.getEmployeeNo());
//                // 制单人名称
//                if (!nameMap.containsKey(smsOrder.getEmployeeNo())) {
//                    name = commonService.getEmpSaleNameByNo(smsOrder.getEmployeeNo());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(smsOrder.getEmployeeNo(), name);
//                    }
//                }
//                smsOrder.setCreateUserName(nameMap.getOrDefault(smsOrder.getEmployeeNo(), ""));
//            }
//
//            smsOrder.setMainOrderNo(smsOrder.getRorderNo());
//            // 客户名称
//            if (StringUtils.isNotBlank(smsOrder.getCustomerNo())) {
//                if (!nameMap.containsKey(smsOrder.getCustomerNo())) {
//                    name = commonService.getCustomerNameByNo(smsOrder.getCustomerNo());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(smsOrder.getCustomerNo(), name);
//                    }
//                }
//                smsOrder.setCustomerName(nameMap.get(smsOrder.getCustomerNo()));
//            }
//            // 用户名称
//            if (StringUtils.isNotBlank(smsOrder.getUserNo())) {
//                if (!nameMap.containsKey(smsOrder.getUserNo())) {
//                    name = commonService.getCustomerNameByNo(smsOrder.getUserNo());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(smsOrder.getUserNo(), name);
//                    }
//                }
//                smsOrder.setUserName(nameMap.get(smsOrder.getUserNo()));
//            }
//
//            // 最终用户名称
//            if (StringUtils.isNotBlank(smsOrder.getEndUser())) {
//                if (!nameMap.containsKey(smsOrder.getEndUser())) {
//                    name = commonService.getCustomerNameByNo(smsOrder.getEndUser());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(smsOrder.getEndUser(), name);
//                    }
//                }
//                smsOrder.setEndUserName(nameMap.get(smsOrder.getEndUser()));
//            }
//
//            // 如果最终用户价格为空 则取价格
//            if (smsOrder.getPriceEndUser() == null || smsOrder.getPriceEndUser().compareTo(BigDecimal.ZERO) == 0) {
//                smsOrder.setPriceEndUser(smsOrder.getPrice());
//            }
//
//            // E率
//            if (smsOrder.getNtaxPice() != null && smsOrder.getNtaxPice().compareTo(BigDecimal.ZERO) != 0) {
//                smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(smsOrder.getNtaxPice(), smsOrder.getEPrice()));
//            } else {
//                // 不含税单价
//                if (smsOrder.getPrice() == null) {
//                    smsOrder.setPrice(BigDecimal.ZERO);
//                }
//                BigDecimal noTaxPrice = CommonFormulaUtil.calcNoTaxPrice(smsOrder.getPrice(), smsOrder.getTaxRate());
//                smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(noTaxPrice, smsOrder.getEPrice()));
//            }
//
//            // hl部门名称
//            if (StringUtils.isNotBlank(smsOrder.getHlCode())) {
//                if (!nameMap.containsKey(smsOrder.getHlCode())) {
//                    name = commonService.getDeptNameByNo(smsOrder.getHlCode());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(smsOrder.getHlCode(), name);
//                    }
//                }
//                smsOrder.setHlCode(smsOrder.getHlCode());
//                smsOrder.setHlCodeName(nameMap.get(smsOrder.getHlCode()));
//            } else {
//                if (!nameMap.containsKey(smsOrder.getHrUnitId())) {
//                    name = commonService.getDeptNameByNo(smsOrder.getHrUnitId());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(smsOrder.getHrUnitId(), name);
//                    }
//                }
//                smsOrder.setHlCode(smsOrder.getHrUnitId());
//                smsOrder.setHlCodeName(nameMap.get(smsOrder.getHrUnitId()));
//            }
//            // 出库区分类名称
//            if (StringUtils.isNotBlank(smsOrder.getStockType()) && StringUtils.isNotBlank(smsOrder.getStockCode())) {
//                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(smsOrder.getStockType()) + "[" + smsOrder.getStockCode() + "]");
//            } else if (StringUtils.isBlank(smsOrder.getStockType())) {
//                smsOrder.setStockTypeName(smsOrder.getStockCode());
//            } else {
//                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(smsOrder.getStockType()));
//            }
//        }

        long lend = System.currentTimeMillis();
        long consume = (lend - lstart) / 1000;
        log.info("行业接单列表查询导出接口 消耗时间(s) ==> " + consume);
        return ResultVo.success(indCodeEntities);
    }

    /**
     * 接单查询(rcvdetail,ops_customer)
     */
//    @Override
//    public ResultVo<PageInfo<SMSOrder>> queryOrderWithCustomer(SMSSearchListInfo condition, int pageNumber, int pageSize) {
//
//        log.info("接单列表查询参数 : " + JSON.toJSONString(condition));
//
//        if (condition == null) {
//            return ResultVo.failure("参数不可为空");
//        }
//
//        long lstart = System.currentTimeMillis();
//
//        if (pageNumber == 0) {
//            pageNumber = 1;
//        }
//        if (pageSize == 0) {
//            pageSize = 10;
//        }
//
//        if (StringUtils.isNotBlank(condition.getOperator())) {
//            if (condition.getOperator().startsWith("ACC") || condition.getOperator().startsWith("acc")) {
//                return queryOrderForAgent(condition,pageNumber,pageSize);
//            }
//        }
//
//        if (StringUtils.isNotBlank(condition.getOrderNo())) {
//            condition.setOrderNo(condition.getOrderNo() + "%");
//        }
//
//        if (StringUtils.isNotBlank(condition.getModelNo())) {
//            condition.setModelNo(condition.getModelNo() + "%");
//        }
//        if (StringUtils.isNotBlank(condition.getRemark())) {
//            condition.setRemark(condition.getRemark() + "%");
//        }
//        if (StringUtils.isNotBlank(condition.getCustProductNo())) {
//            condition.setCustProductNo(condition.getCustProductNo() + "%");
//        }
//
//        if (StringUtils.isNotBlank(condition.getPurchaseNo())) {
//            condition.setPurchaseNo(condition.getPurchaseNo() + "%");
//        }
//
//        if (condition.getOrderDateStart() != null) {
//            condition.setOrderDateStartStr(DateUtil.getStartDateTimeStr(condition.getOrderDateStart()));
//        }
//
//        if (condition.getOrderDateEnd() != null) {
//            condition.setOrderDateEndStr(DateUtil.getEndDateTimeStr(condition.getOrderDateEnd()));
//        }
//
//        List<String> types = new ArrayList<>();
//        if (StringUtils.isBlank(condition.getCustomerType())) {
//            types.add(OrderTypeEnum.saleOrder.getCode());
//            types.add(OrderTypeEnum.ypOrder.getCode());
//        } else {
//            types.add(condition.getCustomerType());
//        }
//
//        // 订单状态集合
//        List<String> status = new ArrayList<>();
//        // 采购供应商
//        List<String> purchasingSupplier = new ArrayList<>();
//
//        if (StringUtils.isNotBlank(condition.getStatus())) {
//            if (String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()).equals(condition.getStatus())) {
//                condition.setIntercepter(1);
//            } else {
//                status.add(condition.getStatus());
//                condition.setIntercepter(null);
//            }
//        }
//
//        // 快捷查询类型
//        if (StringUtils.isNotBlank(condition.getSearchType())) {
//            switch (condition.getSearchType()) {
//                case "1":
//                    // 日本采购中
//                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
//                    purchasingSupplier.add("JP");
//                    break;
//                case "2":
//                    // 制造采购中
//                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
//                    ResultVo<List<SupplierVo>> chinaSuppliers = commonServiceFeignApi.findChinaSuppliers();
//                    if (chinaSuppliers.isSuccess() && chinaSuppliers.getData() != null) {
//                        for (SupplierVo item : chinaSuppliers.getData()) {
//                            purchasingSupplier.add(item.getCompanyId());
//                        }
//                    }
//                    break;
//                case "3":
//                    // 信用已拦截
//                    status.add(String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()));
//                    break;
//                default:
//                    break;
//            }
//        }
//        // 未发货状态
//        if (StringUtils.isNotBlank(condition.getDeliveryStatus())) {
//            switch (condition.getDeliveryStatus()) {
//                case "1":
//                    status.addAll(noSendOrderStatusList());
//                    break;
//                case "2":
//                    status.add(String.valueOf(RCVOrderStatusEnum.IN_DELIVERY.getCode()));
//                    break;
//                case "3":
//                    status.add(String.valueOf(RCVOrderStatusEnum.WAITING_DELIVERY.getCode()));
//                    break;
//                default:
//                    break;
//            }
//        }
//
//        // 排序字段转换
//        if (StringUtils.isNotBlank(condition.getProperty())) {
//            condition.setProperty(getSortFieldForOrderQuery4(condition.getProperty(), condition.getOrder()));
//        }
//
//        // 权限集合
//        List<String> userAuth = new ArrayList<>();
//        List<String> deptAuth = new ArrayList<>();
//        List<String> customerAuth = new ArrayList<>();
//        List<String> employeeAuth = new ArrayList<>();
//        List<String> inCodeAuth = new ArrayList<>();
//        if (condition.getDataAuthoritySearchCondition() != null) {
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getUserNoListByDataAuthority())) {
//                userAuth = condition.getDataAuthoritySearchCondition().getUserNoListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
//                deptAuth = condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
//                customerAuth = condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getUserIdListByDataAuthority())) {
//                employeeAuth = condition.getDataAuthoritySearchCondition().getUserIdListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority())) {
//                inCodeAuth = condition.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority();
//            }
//        }
//        List<String> finalUserAuth = userAuth;
//        List<String> finalDeptAuth = deptAuth;
//        List<String> finalCustomerAuth = customerAuth;
//        List<String> finalEmployeeAuth = employeeAuth;
//        List<String> finalInCodeAuth = inCodeAuth;
//
//        PageInfo<RcvOrderWithCustomerForViewDO> pageInfo = null;
//        try {
//
//            pageInfo = PageHelper.startPage(pageNumber, pageSize)
//                    .doSelectPageInfo(() -> rcvDetailMapperReadOnlyMapper.queryOrderWithCustomer(condition, types, status, purchasingSupplier, finalUserAuth,finalDeptAuth,finalCustomerAuth,finalEmployeeAuth,finalInCodeAuth));
//        } catch (Exception e) {
//            if (e.getMessage().substring(25,100).contains("Timeout")) {
//                return ResultVo.failure("查询超时");
//            } else {
//                e.printStackTrace();
//                throw new RuntimeException(e.getMessage());
//            }
//        }
//
//        long lend = System.currentTimeMillis();
//        long consume = (lend - lstart) / 1000;
//        log.info("接单列表查询 SQL消耗时间(s) => " + consume);
//
//        if (CollectionUtils.isEmpty(pageInfo.getList())) {
//            return ResultVo.success(BeanCopyUtil.pageDto2Vo(pageInfo, SMSOrder.class));
//        }
//        List<SMSOrder> list = commonDataHand(pageInfo.getList());
////        List<SMSOrder> list = new ArrayList<>(pageInfo.getList().size());
////        SMSOrder smsOrder;
////        Map<String, String> nameMap = new HashMap<>(pageInfo.getList().size());
////        String name;
////
////        List<String> noInterceptList = noIntercepterList();
////
////        for (RcvOrderWithCustomerForViewDO item : pageInfo.getList()) {
////            smsOrder = BeanCopyUtil.copy(item, SMSOrder.class);
////            smsOrder.setOrderNo(item.getRorderFno()); // 完整订单号
////            smsOrder.setHddno(item.getOrOrderNo()); // 合同订单号
////
////            // add by LiYingChao from bug 9285 in 2023-1-6
////            smsOrder.setExpTime(item.getShipTime()); // 发货时间
////
////            if (item.getStatus() != null) {
////                if (noInterceptList.contains(String.valueOf(item.getStatus()))) {
////                    smsOrder.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
////                } else {
////                    // 判断是否拦截,若是拦截则显示信用拦截,反之显示订单状态
////                    if (item.getIntercept() != null && item.getIntercept()) {
////                        smsOrder.setStatusName("信用拦截");
////                    } else {
////                        smsOrder.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
////                    }
////                }
////
////                if (item.getStatus() == 9) {
////                    // 删除状态名称
////                    smsOrder.setDeleteStatusName(DeleteStatusEnum.alreadyDel.getCodeName());
////                } else {
////                    smsOrder.setDeleteStatusName(DeleteStatusEnum.noDel.getCodeName());
////                }
////            }
////
////
////            if (StringUtils.isNotBlank(item.getEmployeeNo())) {
////                // 制单人id
////                smsOrder.setCreateId(item.getEmployeeNo());
////                // 制单人名称
////                if (!nameMap.containsKey(item.getEmployeeNo())) {
////                    name = commonService.getEmpSaleNameByNo(item.getEmployeeNo());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(item.getEmployeeNo(), name);
////                    }
////                }
////                smsOrder.setCreateUserName(nameMap.getOrDefault(item.getEmployeeNo(), ""));
////            }
////
////
////            // 出库区分类名称
////            if (StringUtils.isNotBlank(item.getStockType()) && StringUtils.isNotBlank(item.getStockCode())) {
////                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(item.getStockType()) + "[" + item.getStockCode() + "]");
////            } else if (StringUtils.isBlank(item.getStockType())) {
////                smsOrder.setStockTypeName(item.getStockCode());
////            } else {
////                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(item.getStockType()));
////            }
////
////            // 返回阀汇流板标识名称 （0:正常订货;1:底板;2:组装原件）
////            smsOrder.setSpecMark(SpecMarkEnum.getCodeName(item.getSpecMark()));
////            // 出库区分的库存类别名称
////            smsOrder.setInventoryTypeCodeName(InventoryTypeEnum.getName(item.getInventoryTypeCode()));
////            // 拆分标识名称
////            smsOrder.setProdFlagName(OrderSplitTypeEnum.getCodeName(item.getProdFlag()));
////            // 承运商名称
////            if (StringUtils.isNotBlank(item.getCarrierId())) {
////                smsOrder.setCarrierName(CarrierEnum.getNameByCode(item.getCarrierId()));
////            }
////            // 客户名称
////            if (StringUtils.isNotBlank(item.getCustomerNo())) {
////                if (!nameMap.containsKey(item.getCustomerNo())) {
////                    name = commonService.getCustomerNameByNo(item.getCustomerNo());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(item.getCustomerNo(), name);
////                    }
////                }
////                smsOrder.setCustomerName(nameMap.getOrDefault(item.getCustomerNo(), ""));
////            }
////
////            // 用户名称
////            if (StringUtils.isNotBlank(item.getUserNo())) {
////                if (!nameMap.containsKey(item.getUserNo())) {
////                    name = commonService.getCustomerNameByNo(item.getUserNo());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(item.getUserNo(), name);
////                    }
////                }
////                smsOrder.setUserName(nameMap.getOrDefault(item.getUserNo(), ""));
////            }
////
////            // 最终用户名称
////            if (StringUtils.isNotBlank(item.getEndUser())) {
////                if (!nameMap.containsKey(item.getEndUser())) {
////                    name = commonService.getCustomerNameByNo(item.getEndUser());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(item.getEndUser(), name);
////                    }
////                }
////                smsOrder.setEndUserName(nameMap.getOrDefault(item.getEndUser(), ""));
////            }
////
////            // 部门名称
////            if (StringUtils.isNotBlank(item.getHlCode())) {
////                if (!nameMap.containsKey(item.getHlCode())) {
////                    name = commonService.getDeptNameByNo(item.getHlCode());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(item.getHlCode(), name);
////                    }
////                }
////                smsOrder.setSaleDeptNo(item.getHlCode());
////                smsOrder.setSaleDeptNoName(nameMap.get(item.getHlCode()));
////            } else {
////                if (!nameMap.containsKey(item.getHrUnitId())) {
////                    name = commonService.getDeptNameByNo(item.getHrUnitId());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(item.getHrUnitId(), name);
////                    }
////                }
////                smsOrder.setSaleDeptNo(item.getHrUnitId());
////                smsOrder.setSaleDeptNoName(nameMap.get(item.getHrUnitId()));
////            }
////
////            // 如果最终用户价格为空 则取价格
////            if (item.getPriceEndUser() == null || item.getPriceEndUser().compareTo(BigDecimal.ZERO) == 0) {
////                smsOrder.setPriceEndUser(item.getPrice());
////            }
////            // E率
////            if (smsOrder.getDiscount() == null || smsOrder.getDiscount().compareTo(BigDecimal.ZERO) == 0) {
////                if (item.getNtaxPice() != null && item.getNtaxPice().compareTo(BigDecimal.ZERO) != 0) {
////                    smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(item.getNtaxPice(), item.getEPrice()));
////                } else {
////                    // 不含税单价
////                    if (item.getPrice() == null) {
////                        item.setPrice(BigDecimal.ZERO);
////                    }
////                    BigDecimal noTaxPrice = CommonFormulaUtil.calcNoTaxPrice(item.getPrice(), item.getTaxRate());
////                    smsOrder.setNtaxPice(noTaxPrice); //不含税单价
////                    smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(noTaxPrice, item.getEPrice())); // e率
////                }
////            }
////
////            list.add(smsOrder);
////        }
//
//        PageInfo<SMSOrder> smsOrderPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, SMSOrder.class);
//        smsOrderPageInfo.setList(list);
//
//        lend = System.currentTimeMillis();
//        consume = (lend - lstart) / 1000;
//        log.info("执行接单列表查询接口  执行结束耗时(s) => " + consume);
//
//        return ResultVo.success(smsOrderPageInfo);
//    }

    @Override
    public ResultVo<PageInfo<SMSOrder>> queryOrderForAgent(SMSSearchListInfo condition, int pageNumber, int pageSize) {
        log.info("接单列表查询参数 : " + JSON.toJSONString(condition));

        if (condition == null) {
            return ResultVo.failure("参数不可为空");
        }

        long lstart = System.currentTimeMillis();

        if (pageNumber == 0) {
            pageNumber = 1;
        }
        if (pageSize == 0) {
            pageSize = 10;
        }

        PageInfo<RcvOrderWithCustomerForViewDO> pageInfo = null;

        if (CollectionUtils.isEmpty(condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
            pageInfo = new PageInfo<>();
            return ResultVo.success(BeanCopyUtil.pageDto2Vo(pageInfo, SMSOrder.class));
        }

        if (StringUtils.isNotBlank(condition.getOrderNo())) {
            condition.setOrderNo(condition.getOrderNo() + "%");
        }

        if (StringUtils.isNotBlank(condition.getModelNo())) {
            condition.setModelNo(condition.getModelNo() + "%");
        }
        if (StringUtils.isNotBlank(condition.getRemark())) {
            condition.setRemark(condition.getRemark() + "%");
        }
        if (StringUtils.isNotBlank(condition.getCustProductNo())) {
            condition.setCustProductNo(condition.getCustProductNo() + "%");
        }

        if (StringUtils.isNotBlank(condition.getPurchaseNo())) {
            condition.setPurchaseNo(condition.getPurchaseNo() + "%");
        }

        if (condition.getOrderDateStart() != null) {
            condition.setOrderDateStartStr(DateUtil.getStartDateTimeStr(condition.getOrderDateStart()));
        }

        if (condition.getOrderDateEnd() != null) {
            condition.setOrderDateEndStr(DateUtil.getEndDateTimeStr(condition.getOrderDateEnd()));
        }

        List<String> types = new ArrayList<>();
        if (StringUtils.isBlank(condition.getCustomerType())) {
            types.add(OrderTypeEnum.saleOrder.getCode());
            types.add(OrderTypeEnum.ypOrder.getCode());
        } else {
            types.add(condition.getCustomerType());
        }

        // 订单状态集合
        List<String> status = new ArrayList<>();
        // 采购供应商
        List<String> purchasingSupplier = new ArrayList<>();

        if (StringUtils.isNotBlank(condition.getStatus())) {

            if (String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()).equals(condition.getStatus())) {
                condition.setIntercepter(1);
            } else {
                status.add(condition.getStatus());
                condition.setIntercepter(null);
            }
        }

        // 快捷查询类型
        if (StringUtils.isNotBlank(condition.getSearchType())) {
            switch (condition.getSearchType()) {
                case "1":
                    // 日本采购中
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
                    purchasingSupplier.add("JP");
                    break;
                case "2":
                    // 制造采购中
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
                    ResultVo<List<SupplierVo>> chinaSuppliers = commonServiceFeignApi.findChinaSuppliers();
                    if (chinaSuppliers.isSuccess() && chinaSuppliers.getData() != null) {
                        for (SupplierVo item : chinaSuppliers.getData()) {
                            purchasingSupplier.add(item.getCompanyId());
                        }
                    }
                    break;
                case "3":
                    // 信用已拦截
                    status.add(String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()));
                    break;
                default:
                    break;
            }
        }
        // 未发货状态
        if (StringUtils.isNotBlank(condition.getDeliveryStatus())) {
            switch (condition.getDeliveryStatus()) {
                case "1":
                    status.addAll(noSendOrderStatusList());
                    break;
                case "2":
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_DELIVERY.getCode()));
                    break;
                case "3":
                    status.add(String.valueOf(RCVOrderStatusEnum.WAITING_DELIVERY.getCode()));
                    break;
                default:
                    break;
            }
        }
        // 排序字段转换
        if (StringUtils.isNotBlank(condition.getProperty())) {
            condition.setProperty(getSortFieldForOrderQueryWithAgent(condition.getProperty(), condition.getOrder()));
        }

        try {
            pageInfo = PageHelper.startPage(pageNumber, pageSize)
                    .doSelectPageInfo(() -> rcvDetailMapperReadOnlyMapper.queryOrderByAgent(condition, types, status, purchasingSupplier));
        } catch (Exception e) {
            if (e.getMessage().substring(25,100).contains("Timeout")) {
                return ResultVo.failure("查询超时");
            } else {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        long lend = System.currentTimeMillis();
        long consume = (lend - lstart) / 1000;
        log.info("接单列表查询 SQL消耗时间(s) => " + consume);

        if (CollectionUtils.isEmpty(pageInfo.getList())) {
            return ResultVo.success(BeanCopyUtil.pageDto2Vo(pageInfo, SMSOrder.class));
        }
        List<SMSOrder> list = commonDataHand(pageInfo.getList());
//        List<SMSOrder> list = new ArrayList<>(pageInfo.getList().size());
//        SMSOrder smsOrder;
//        Map<String, String> nameMap = new HashMap<>(pageInfo.getList().size());
//        String name;
//
//        List<String> noInterceptList = noIntercepterList();
//
//        for (RcvOrderWithCustomerForViewDO item : pageInfo.getList()) {
//            smsOrder = BeanCopyUtil.copy(item, SMSOrder.class);
//            smsOrder.setOrderNo(item.getRorderFno()); // 完整订单号
//
//            smsOrder.setHddno(item.getOrOrderNo()); // 合同订单号
//
//            if (item.getStatus() != null) {
//                if (noInterceptList.contains(String.valueOf(item.getStatus()))) {
//                    smsOrder.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
//                } else {
//                    // 判断是否拦截,若是拦截则显示信用拦截,反之显示订单状态
//                    if (item.getIntercept() != null) {
//                        if (item.getIntercept()) {
//                            smsOrder.setStatusName("信用拦截");
//                        } else {
//                            // 处理状态名称
//                            smsOrder.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
//                        }
//                    } else {
//                        smsOrder.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
//                    }
//                }
//
//                if (item.getStatus() == 9) {
//                    // 删除状态名称
//                    smsOrder.setDeleteStatusName(DeleteStatusEnum.alreadyDel.getCodeName());
//                } else {
//                    smsOrder.setDeleteStatusName(DeleteStatusEnum.noDel.getCodeName());
//                }
//            }
//
//            if (StringUtils.isNotBlank(item.getEmployeeNo())) {
//                // 制单人id
//                smsOrder.setCreateId(item.getEmployeeNo());
//                // 制单人名称
//                if (!nameMap.containsKey(item.getEmployeeNo())) {
//                    name = commonService.getEmpSaleNameByNo(item.getEmployeeNo());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getEmployeeNo(), name);
//                    }
//                }
//                smsOrder.setCreateUserName(nameMap.getOrDefault(item.getEmployeeNo(), ""));
//            }
//
//            // 出库区分类名称
//            if (StringUtils.isNotBlank(item.getStockType()) && StringUtils.isNotBlank(item.getStockCode())) {
//                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(item.getStockType()) + "[" + item.getStockCode() + "]");
//            } else if (StringUtils.isBlank(item.getStockType())) {
//                smsOrder.setStockTypeName(item.getStockCode());
//            } else {
//                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(item.getStockType()));
//            }
//
//            // 返回阀汇流板标识名称 （0:正常订货;1:底板;2:组装原件）
//            smsOrder.setSpecMark(SpecMarkEnum.getCodeName(item.getSpecMark()));
//            // 出库区分的库存类别名称
//            smsOrder.setInventoryTypeCodeName(InventoryTypeEnum.getName(item.getInventoryTypeCode()));
//            // 拆分标识名称
//            smsOrder.setProdFlagName(OrderSplitTypeEnum.getCodeName(item.getProdFlag()));
//            // 承运商名称
//            if (StringUtils.isNotBlank(item.getCarrierId())) {
//                smsOrder.setCarrierName(CarrierEnum.getNameByCode(item.getCarrierId()));
//            }
//            // 客户名称
//            if (StringUtils.isNotBlank(item.getCustomerNo())) {
//                if (!nameMap.containsKey(item.getCustomerNo())) {
//                    name = commonService.getCustomerNameByNo(item.getCustomerNo());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getCustomerNo(), name);
//                    }
//                }
//                smsOrder.setCustomerName(nameMap.getOrDefault(item.getCustomerNo(), ""));
//            }
//            // smsOrder.setCustomerName(item.getName());
//            // 用户名称
//            if (StringUtils.isNotBlank(item.getUserNo())) {
//                if (!nameMap.containsKey(item.getUserNo())) {
//                    name = commonService.getCustomerNameByNo(item.getUserNo());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getUserNo(), name);
//                    }
//                }
//                smsOrder.setUserName(nameMap.getOrDefault(item.getUserNo(), ""));
//            }
//
//            // 最终用户名称
//            if (StringUtils.isNotBlank(item.getEndUser())) {
//                if (!nameMap.containsKey(item.getEndUser())) {
//                    name = commonService.getCustomerNameByNo(item.getEndUser());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getEndUser(), name);
//                    }
//                }
//                smsOrder.setEndUserName(nameMap.getOrDefault(item.getEndUser(), ""));
//            }
//
//            // 部门名称
//            if (StringUtils.isNotBlank(item.getHlCode())) {
//                if (!nameMap.containsKey(item.getHlCode())) {
//                    name = commonService.getDeptNameByNo(item.getHlCode());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getHlCode(), name);
//                    }
//                }
//                smsOrder.setSaleDeptNo(item.getHlCode());
//                smsOrder.setSaleDeptNoName(nameMap.get(item.getHlCode()));
//            } else {
//                if (!nameMap.containsKey(item.getHrUnitId())) {
//                    name = commonService.getDeptNameByNo(item.getHrUnitId());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getHrUnitId(), name);
//                    }
//                }
//                smsOrder.setSaleDeptNo(item.getHrUnitId());
//                smsOrder.setSaleDeptNoName(nameMap.get(item.getHrUnitId()));
//            }
//
//            // 如果最终用户价格为空 则取价格
//            if (item.getPriceEndUser() == null || item.getPriceEndUser().compareTo(BigDecimal.ZERO) == 0) {
//                smsOrder.setPriceEndUser(item.getPrice());
//            }
//            // E率
//            if (smsOrder.getDiscount() == null || smsOrder.getDiscount().compareTo(BigDecimal.ZERO) == 0) {
//                if (item.getNtaxPice() != null && item.getNtaxPice().compareTo(BigDecimal.ZERO) != 0) {
//                    smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(item.getNtaxPice(), item.getEPrice()));
//                } else {
//                    // 不含税单价
//                    if (item.getPrice() == null) {
//                        item.setPrice(BigDecimal.ZERO);
//                    }
//                    BigDecimal noTaxPrice = CommonFormulaUtil.calcNoTaxPrice(item.getPrice(), item.getTaxRate());
//                    smsOrder.setNtaxPice(noTaxPrice);
//                    smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(noTaxPrice, item.getEPrice()));
//                }
//            }
//
//            list.add(smsOrder);
//        }

        PageInfo<SMSOrder> smsOrderPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, SMSOrder.class);
        smsOrderPageInfo.setList(list);

        lend = System.currentTimeMillis();
        consume = (lend - lstart) / 1000;
        log.info("执行接单列表查询接口  执行结束耗时(s) => " + consume);

        return ResultVo.success(smsOrderPageInfo);
    }

//    @Override
//    public ResultVo<List<SMSOrder>> exportRcvOrder(SMSSearchListInfo condition) {
//
//        log.info("接单列表查询导出参数 : " + JSON.toJSONString(condition));
//
//        if (condition == null) {
//            return ResultVo.failure("参数不可为空");
//        }
//
//        long lstart = System.currentTimeMillis();
//
//        if (StringUtils.isNotBlank(condition.getOperator())) {
//            if (condition.getOperator().startsWith("ACC") || condition.getOperator().startsWith("acc")) {
//                return exportRcvOrderForAgent(condition);
//            }
//        }
//
//        if (StringUtils.isNotBlank(condition.getOrderNo())) {
//            condition.setOrderNo(condition.getOrderNo() + "%");
//        }
//
//        if (StringUtils.isNotBlank(condition.getModelNo())) {
//            condition.setModelNo(condition.getModelNo() + "%");
//        }
//        if (StringUtils.isNotBlank(condition.getRemark())) {
//            condition.setRemark(condition.getRemark() + "%");
//        }
//        if (StringUtils.isNotBlank(condition.getCustProductNo())) {
//            condition.setCustProductNo(condition.getCustProductNo() + "%");
//        }
//
//        if (StringUtils.isNotBlank(condition.getPurchaseNo())) {
//            condition.setPurchaseNo(condition.getPurchaseNo() + "%");
//        }
//
//        if (condition.getOrderDateStart() != null) {
//            condition.setOrderDateStartStr(DateUtil.getStartDateTimeStr(condition.getOrderDateStart()));
//        }
//
//        if (condition.getOrderDateEnd() != null) {
//            condition.setOrderDateEndStr(DateUtil.getEndDateTimeStr(condition.getOrderDateEnd()));
//        }
//
//
//        List<String> types = new ArrayList<>();
//        if (StringUtils.isBlank(condition.getCustomerType())) {
//            types.add(OrderTypeEnum.saleOrder.getCode());
//            types.add(OrderTypeEnum.ypOrder.getCode());
//        } else {
//            types.add(condition.getCustomerType());
//        }
//
//        // 订单状态集合
//        List<String> status = new ArrayList<>();
//        // 采购供应商
//        List<String> purchasingSupplier = new ArrayList<>();
//
//        if (StringUtils.isNotBlank(condition.getStatus())) {
//
//            if (String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()).equals(condition.getStatus())) {
//                condition.setIntercepter(1);
//            } else {
//                status.add(condition.getStatus());
//                condition.setIntercepter(null);
//            }
//        }
//
//        // 快捷查询类型
//        if (StringUtils.isNotBlank(condition.getSearchType())) {
//            switch (condition.getSearchType()) {
//                case "1":
//                    // 日本采购中
//                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
//                    purchasingSupplier.add("JP");
//                    break;
//                case "2":
//                    // 制造采购中
//                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
//                    ResultVo<List<SupplierVo>> chinaSuppliers = commonServiceFeignApi.findChinaSuppliers();
//                    if (chinaSuppliers.isSuccess() && chinaSuppliers.getData() != null) {
//                        for (SupplierVo item : chinaSuppliers.getData()) {
//                            purchasingSupplier.add(item.getCompanyId());
//                        }
//                    }
//                    break;
//                case "3":
//                    // 信用已拦截
//                    status.add(String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()));
//                    break;
//                default:
//                    break;
//            }
//        }
//        // 未发货状态
//        if (StringUtils.isNotBlank(condition.getDeliveryStatus())) {
//            switch (condition.getDeliveryStatus()) {
//                case "1":
//                    status.addAll(noSendOrderStatusList());
//                    break;
//                case "2":
//                    status.add(String.valueOf(RCVOrderStatusEnum.IN_DELIVERY.getCode()));
//                    break;
//                case "3":
//                    status.add(String.valueOf(RCVOrderStatusEnum.WAITING_DELIVERY.getCode()));
//                    break;
//                default:
//                    break;
//            }
//        }
//        // 排序字段转换
//        if (StringUtils.isNotBlank(condition.getProperty())) {
//            condition.setProperty(getSortFieldForOrderQuery4(condition.getProperty(), condition.getOrder()));
//        }
//
//        // 权限集合
//        List<String> userAuth = new ArrayList<>();
//        List<String> deptAuth = new ArrayList<>();
//        List<String> customerAuth = new ArrayList<>();
//        List<String> employeeAuth = new ArrayList<>();
//        List<String> inCodeAuth = new ArrayList<>();
//        if (condition.getDataAuthoritySearchCondition() != null) {
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getUserNoListByDataAuthority())) {
//                userAuth = condition.getDataAuthoritySearchCondition().getUserNoListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
//                deptAuth = condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
//                customerAuth = condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getUserIdListByDataAuthority())) {
//                employeeAuth = condition.getDataAuthoritySearchCondition().getUserIdListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority())) {
//                inCodeAuth = condition.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority();
//            }
//        }
//        List<String> finalUserAuth = userAuth;
//        List<String> finalDeptAuth = deptAuth;
//        List<String> finalCustomerAuth = customerAuth;
//        List<String> finalEmployeeAuth = employeeAuth;
//        List<String> finalInCodeAuth = inCodeAuth;
//
//        List<RcvOrderWithCustomerForViewDO> rcvOrderWithCustomerForViewDOS = null;
//        try {
//            rcvOrderWithCustomerForViewDOS = rcvDetailMapperReadOnlyMapper.exportRcvOrder(condition, types, status, purchasingSupplier,finalUserAuth,finalDeptAuth,finalCustomerAuth,finalEmployeeAuth,finalInCodeAuth);
//        } catch (Exception e) {
//            if (e.getMessage().substring(25,100).contains("Timeout")) {
//                return ResultVo.failure("查询超时");
//            } else {
//                e.printStackTrace();
//                throw new RuntimeException(e.getMessage());
//            }
//        }
//
//        long lend = System.currentTimeMillis();
//        long consume = (lend - lstart) / 1000;
//        log.info("接单列表查询导出 SQL消耗时间(s) => " + consume);
//
//        if (CollectionUtils.isEmpty(rcvOrderWithCustomerForViewDOS)) {
//            return ResultVo.success();
//        }
//        List<SMSOrder> list = commonDataHand(rcvOrderWithCustomerForViewDOS);
////        List<SMSOrder> list = new ArrayList<>(rcvOrderWithCustomerForViewDOS.size());
////        SMSOrder smsOrder;
////        Map<String, String> nameMap = new HashMap<>(rcvOrderWithCustomerForViewDOS.size());
////        String name;
////
////        List<String> noInterceptList = noIntercepterList();
////
////        for (RcvOrderWithCustomerForViewDO item : rcvOrderWithCustomerForViewDOS) {
////            smsOrder = BeanCopyUtil.copy(item, SMSOrder.class);
////            smsOrder.setOrderNo(item.getRorderFno()); // 完整订单号
////            smsOrder.setHddno(item.getOrOrderNo()); // 合同订单号
////
////            if (item.getStatus() != null) {
////                if (noInterceptList.contains(String.valueOf(item.getStatus()))) {
////                    smsOrder.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
////                } else {
////                    // 判断是否拦截,若是拦截则显示信用拦截,反之显示订单状态
////                    if (item.getIntercept() != null) {
////                        if (item.getIntercept()) {
////                            smsOrder.setStatusName("信用拦截");
////                        } else {
////                            // 处理状态名称
////                            smsOrder.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
////                        }
////                    } else {
////                        smsOrder.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
////                    }
////                }
////
////                if (item.getStatus() == 9) {
////                    // 删除状态名称
////                    smsOrder.setDeleteStatusName(DeleteStatusEnum.alreadyDel.getCodeName());
////                } else {
////                    smsOrder.setDeleteStatusName(DeleteStatusEnum.noDel.getCodeName());
////                }
////            }
////
////            if (StringUtils.isNotBlank(item.getEmployeeNo())) {
////                // 制单人id
////                smsOrder.setCreateId(item.getEmployeeNo());
////                // 制单人名称
////                if (!nameMap.containsKey(item.getEmployeeNo())) {
////                    name = commonService.getEmpSaleNameByNo(item.getEmployeeNo());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(item.getEmployeeNo(), name);
////                    }
////                }
////                smsOrder.setCreateUserName(nameMap.getOrDefault(item.getEmployeeNo(), ""));
////            }
////
////            // 出库区分类名称
////            if (StringUtils.isNotBlank(item.getStockType()) && StringUtils.isNotBlank(item.getStockCode())) {
////                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(item.getStockType()) + "[" + item.getStockCode() + "]");
////            } else if (StringUtils.isBlank(item.getStockType())) {
////                smsOrder.setStockTypeName(item.getStockCode());
////            } else {
////                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(item.getStockType()));
////            }
////
////            // 返回阀汇流板标识名称 （0:正常订货;1:底板;2:组装原件）
////            smsOrder.setSpecMark(SpecMarkEnum.getCodeName(item.getSpecMark()));
////            // 出库区分的库存类别名称
////            smsOrder.setInventoryTypeCodeName(InventoryTypeEnum.getName(item.getInventoryTypeCode()));
////            // 拆分标识名称
////            smsOrder.setProdFlagName(OrderSplitTypeEnum.getCodeName(item.getProdFlag()));
////            // 承运商名称
////            if (StringUtils.isNotBlank(item.getCarrierId())) {
////                smsOrder.setCarrierName(CarrierEnum.getNameByCode(item.getCarrierId()));
////            }
////            // 客户名称
////            if (StringUtils.isNotBlank(item.getCustomerNo())) {
////                if (!nameMap.containsKey(item.getCustomerNo())) {
////                    name = commonService.getCustomerNameByNo(item.getCustomerNo());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(item.getCustomerNo(), name);
////                    }
////                }
////                smsOrder.setCustomerName(nameMap.getOrDefault(item.getCustomerNo(), ""));
////            }
////
////            // 用户名称
////            if (StringUtils.isNotBlank(item.getUserNo())) {
////                if (!nameMap.containsKey(item.getUserNo())) {
////                    name = commonService.getCustomerNameByNo(item.getUserNo());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(item.getUserNo(), name);
////                    }
////                }
////                smsOrder.setUserName(nameMap.getOrDefault(item.getUserNo(), ""));
////            }
////
////            // 最终用户名称
////            if (StringUtils.isNotBlank(item.getEndUser())) {
////                if (!nameMap.containsKey(item.getEndUser())) {
////                    name = commonService.getCustomerNameByNo(item.getEndUser());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(item.getEndUser(), name);
////                    }
////                }
////                smsOrder.setEndUserName(nameMap.getOrDefault(item.getEndUser(), ""));
////            }
////
////            // 部门名称
////            if (StringUtils.isNotBlank(item.getHlCode())) {
////                if (!nameMap.containsKey(item.getHlCode())) {
////                    name = commonService.getDeptNameByNo(item.getHlCode());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(item.getHlCode(), name);
////                    }
////                }
////                smsOrder.setSaleDeptNo(item.getHlCode());
////                smsOrder.setSaleDeptNoName(nameMap.get(item.getHlCode()));
////            } else {
////                if (!nameMap.containsKey(item.getHrUnitId())) {
////                    name = commonService.getDeptNameByNo(item.getHrUnitId());
////                    if (StringUtils.isNotBlank(name)) {
////                        nameMap.put(item.getHrUnitId(), name);
////                    }
////                }
////                smsOrder.setSaleDeptNo(item.getHrUnitId());
////                smsOrder.setSaleDeptNoName(nameMap.get(item.getHrUnitId()));
////            }
////
////            // 如果最终用户价格为空 则取价格
////            if (item.getPriceEndUser() == null || item.getPriceEndUser().compareTo(BigDecimal.ZERO) == 0) {
////                smsOrder.setPriceEndUser(item.getPrice());
////            }
////            // E率
////            if (smsOrder.getDiscount() == null || smsOrder.getDiscount().compareTo(BigDecimal.ZERO) == 0) {
////                if (item.getNtaxPice() != null && item.getNtaxPice().compareTo(BigDecimal.ZERO) != 0) {
////                    smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(item.getNtaxPice(), item.getEPrice()));
////                } else {
////                    // 不含税单价
////                    if (item.getPrice() == null) {
////                        item.setPrice(BigDecimal.ZERO);
////                    }
////                    BigDecimal noTaxPrice = CommonFormulaUtil.calcNoTaxPrice(item.getPrice(), item.getTaxRate());
////                    smsOrder.setNtaxPice(noTaxPrice);
////                    smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(noTaxPrice, item.getEPrice()));
////                }
////            }
////            list.add(smsOrder);
////        }
//        lend = System.currentTimeMillis();
//        consume = (lend - lstart) / 1000;
//        log.info("执行接单列表查询导出接口接口  执行结束耗时(s) => " + consume);
//
//        return ResultVo.success(list);
//    }

    @Override
    public ResultVo<List<SMSOrder>> exportRcvOrderForAgent(SMSSearchListInfo condition) {
        log.info("接单列表查询导出参数 : " + JSON.toJSONString(condition));

        if (condition == null) {
            return ResultVo.failure("参数不可为空");
        }

        if (CollectionUtils.isEmpty(condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
            return ResultVo.success(new ArrayList<>());
        }

        if (StringUtils.isNotBlank(condition.getOrderNo())) {
            condition.setOrderNo(condition.getOrderNo() + "%");
        }

        if (StringUtils.isNotBlank(condition.getModelNo())) {
            condition.setModelNo(condition.getModelNo() + "%");
        }
        if (StringUtils.isNotBlank(condition.getRemark())) {
            condition.setRemark(condition.getRemark() + "%");
        }
        if (StringUtils.isNotBlank(condition.getCustProductNo())) {
            condition.setCustProductNo(condition.getCustProductNo() + "%");
        }

        if (condition.getOrderDateStart() != null) {
            condition.setOrderDateStartStr(DateUtil.getStartDateTimeStr(condition.getOrderDateStart()));
        }

        if (condition.getOrderDateEnd() != null) {
            condition.setOrderDateEndStr(DateUtil.getEndDateTimeStr(condition.getOrderDateEnd()));
        }

        long lstart = System.currentTimeMillis();


        List<String> types = new ArrayList<>();
        if (StringUtils.isBlank(condition.getCustomerType())) {
            types.add(OrderTypeEnum.saleOrder.getCode());
            types.add(OrderTypeEnum.ypOrder.getCode());
        } else {
            types.add(condition.getCustomerType());
        }

        // 订单状态集合
        List<String> status = new ArrayList<>();
        // 采购供应商
        List<String> purchasingSupplier = new ArrayList<>();

        if (StringUtils.isNotBlank(condition.getStatus())) {

            if (String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()).equals(condition.getStatus())) {
                condition.setIntercepter(1);
            } else {
                status.add(condition.getStatus());
                condition.setIntercepter(null);
            }
        }

        // 快捷查询类型
        if (StringUtils.isNotBlank(condition.getSearchType())) {
            switch (condition.getSearchType()) {
                case "1":
                    // 日本采购中
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
                    purchasingSupplier.add("JP");
                    break;
                case "2":
                    // 制造采购中
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
                    ResultVo<List<SupplierVo>> chinaSuppliers = commonServiceFeignApi.findChinaSuppliers();
                    if (chinaSuppliers.isSuccess() && chinaSuppliers.getData() != null) {
                        for (SupplierVo item : chinaSuppliers.getData()) {
                            purchasingSupplier.add(item.getCompanyId());
                        }
                    }
                    break;
                case "3":
                    // 信用已拦截
                    status.add(String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()));
                    break;
                default:
                    break;
            }
        }
        // 未发货状态
        if (StringUtils.isNotBlank(condition.getDeliveryStatus())) {
            switch (condition.getDeliveryStatus()) {
                case "1":
                    status.addAll(noSendOrderStatusList());
                    break;
                case "2":
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_DELIVERY.getCode()));
                    break;
                case "3":
                    status.add(String.valueOf(RCVOrderStatusEnum.WAITING_DELIVERY.getCode()));
                    break;
                default:
                    break;
            }
        }
        // 排序字段转换
        if (StringUtils.isNotBlank(condition.getProperty())) {
            condition.setProperty(getSortFieldForOrderQueryWithAgent(condition.getProperty(), condition.getOrder()));
        }

        log.info("接单列表查询导出处理后参数 => " + JSON.toJSONString(condition));

        List<RcvOrderWithCustomerForViewDO> rcvOrderWithCustomerForViewDOS = null;
        try {
            rcvOrderWithCustomerForViewDOS = rcvDetailMapperReadOnlyMapper.exportRcvOrderForAgent(condition, types, status, purchasingSupplier);
        } catch (Exception e) {
            if (e.getMessage().substring(25,100).contains("Timeout")) {
                return ResultVo.failure("查询超时");
            } else {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        long lend = System.currentTimeMillis();
        long consume = (lend - lstart) / 1000;
        log.info("接单列表查询导出 SQL消耗时间(s) => " + consume);

        if (CollectionUtils.isEmpty(rcvOrderWithCustomerForViewDOS)) {
            return ResultVo.success();
        }
        List<SMSOrder> list = commonDataHand(rcvOrderWithCustomerForViewDOS);
//        List<SMSOrder> list = new ArrayList<>(rcvOrderWithCustomerForViewDOS.size());
//        SMSOrder smsOrder;
//        Map<String, String> nameMap = new HashMap<>(rcvOrderWithCustomerForViewDOS.size());
//        String name;
//
//        List<String> noInterceptList = noIntercepterList();
//
//        for (RcvOrderWithCustomerForViewDO item : rcvOrderWithCustomerForViewDOS) {
//            smsOrder = BeanCopyUtil.copy(item, SMSOrder.class);
//            smsOrder.setOrderNo(item.getRorderFno()); // 完整订单号
//            smsOrder.setHddno(item.getOrOrderNo()); // 合同订单号
//
//            // add by LiYingChao from bug 9285 in 2023-1-6
//            smsOrder.setExpTime(item.getShipTime()); // 发货时间
//
//            if (item.getStatus() != null) {
//                if (noInterceptList.contains(String.valueOf(item.getStatus()))) {
//                    smsOrder.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
//                } else {
//                    // 判断是否拦截,若是拦截则显示信用拦截,反之显示订单状态
//                    if (item.getIntercept() != null) {
//                        if (item.getIntercept()) {
//                            smsOrder.setStatusName("信用拦截");
//                        } else {
//                            // 处理状态名称
//                            smsOrder.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
//                        }
//                    } else {
//                        smsOrder.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
//                    }
//                }
//
//                if (item.getStatus() == 9) {
//                    // 删除状态名称
//                    smsOrder.setDeleteStatusName(DeleteStatusEnum.alreadyDel.getCodeName());
//                } else {
//                    smsOrder.setDeleteStatusName(DeleteStatusEnum.noDel.getCodeName());
//                }
//            }
//
//            if (StringUtils.isNotBlank(item.getEmployeeNo())) {
//                // 制单人id
//                smsOrder.setCreateId(item.getEmployeeNo());
//                // 制单人名称
//                if (!nameMap.containsKey(item.getEmployeeNo())) {
//                    name = commonService.getEmpSaleNameByNo(item.getEmployeeNo());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getEmployeeNo(), name);
//                    }
//                }
//                smsOrder.setCreateUserName(nameMap.getOrDefault(item.getEmployeeNo(), ""));
//            }
//
//            // 出库区分类名称
//            if (StringUtils.isNotBlank(item.getStockType()) && StringUtils.isNotBlank(item.getStockCode())) {
//                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(item.getStockType()) + "[" + item.getStockCode() + "]");
//            } else if (StringUtils.isBlank(item.getStockType())) {
//                smsOrder.setStockTypeName(item.getStockCode());
//            } else {
//                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(item.getStockType()));
//            }
//
//            // 返回阀汇流板标识名称 （0:正常订货;1:底板;2:组装原件）
//            smsOrder.setSpecMark(SpecMarkEnum.getCodeName(item.getSpecMark()));
//            // 出库区分的库存类别名称
//            smsOrder.setInventoryTypeCodeName(InventoryTypeEnum.getName(item.getInventoryTypeCode()));
//            // 拆分标识名称
//            smsOrder.setProdFlagName(OrderSplitTypeEnum.getCodeName(item.getProdFlag()));
//            // 承运商名称
//            if (StringUtils.isNotBlank(item.getCarrierId())) {
//                smsOrder.setCarrierName(CarrierEnum.getNameByCode(item.getCarrierId()));
//            }
//            // 客户名称
//            if (StringUtils.isNotBlank(item.getCustomerNo())) {
//                if (!nameMap.containsKey(item.getCustomerNo())) {
//                    name = commonService.getCustomerNameByNo(item.getCustomerNo());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getCustomerNo(), name);
//                    }
//                }
//                smsOrder.setCustomerName(nameMap.getOrDefault(item.getCustomerNo(), ""));
//            }
//            // smsOrder.setCustomerName(item.getName());
//            // 用户名称
//            if (StringUtils.isNotBlank(item.getUserNo())) {
//                if (!nameMap.containsKey(item.getUserNo())) {
//                    name = commonService.getCustomerNameByNo(item.getUserNo());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getUserNo(), name);
//                    }
//                }
//                smsOrder.setUserName(nameMap.getOrDefault(item.getUserNo(), ""));
//            }
//
//            // 最终用户名称
//            if (StringUtils.isNotBlank(item.getEndUser())) {
//                if (!nameMap.containsKey(item.getEndUser())) {
//                    name = commonService.getCustomerNameByNo(item.getEndUser());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getEndUser(), name);
//                    }
//                }
//                smsOrder.setEndUserName(nameMap.getOrDefault(item.getEndUser(), ""));
//            }
//
//            // 部门名称
//            if (StringUtils.isNotBlank(item.getHlCode())) {
//                if (!nameMap.containsKey(item.getHlCode())) {
//                    name = commonService.getDeptNameByNo(item.getHlCode());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getHlCode(), name);
//                    }
//                }
//                smsOrder.setSaleDeptNo(item.getHlCode());
//                smsOrder.setSaleDeptNoName(nameMap.get(item.getHlCode()));
//            } else {
//                if (!nameMap.containsKey(item.getHrUnitId())) {
//                    name = commonService.getDeptNameByNo(item.getHrUnitId());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getHrUnitId(), name);
//                    }
//                }
//                smsOrder.setSaleDeptNo(item.getHrUnitId());
//                smsOrder.setSaleDeptNoName(nameMap.get(item.getHrUnitId()));
//            }
//
//            // 如果最终用户价格为空 则取价格
//            if (item.getPriceEndUser() == null || item.getPriceEndUser().compareTo(BigDecimal.ZERO) == 0) {
//                smsOrder.setPriceEndUser(item.getPrice());
//            }
//            // E率
//            if (smsOrder.getDiscount() == null || smsOrder.getDiscount().compareTo(BigDecimal.ZERO) == 0) {
//                if (item.getNtaxPice() != null && item.getNtaxPice().compareTo(BigDecimal.ZERO) != 0) {
//                    smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(item.getNtaxPice(), item.getEPrice()));
//                } else {
//                    // 不含税单价
//                    if (item.getPrice() == null) {
//                        item.setPrice(BigDecimal.ZERO);
//                    }
//                    BigDecimal noTaxPrice = CommonFormulaUtil.calcNoTaxPrice(item.getPrice(), item.getTaxRate());
//                    smsOrder.setNtaxPice(noTaxPrice);
//                    smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(noTaxPrice, item.getEPrice()));
//                }
//            }
//
//            list.add(smsOrder);
//        }
        lend = System.currentTimeMillis();
        consume = (lend - lstart) / 1000;
        log.info("执行接单列表查询导出接口接口  执行结束耗时(s) => " + consume);

        return ResultVo.success(list);
    }


    // 获取接单状态
    public Integer findRcvDetailStatus(String rordeFno) {
        if (StringUtils.isBlank(rordeFno)) {
            return null;
        }
        LambdaQueryWrapper<RcvDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(RcvDetailDO::getStatus).eq(RcvDetailDO::getRorderFno, rordeFno);
        RcvDetailDO rcvDetailDO = rcvDetailMapperReadOnlyMapper.selectOne(queryWrapper);
        return rcvDetailDO.getStatus();
    }

    @Override
    public ResultVo<Order> detail(String orderNo) {

        if (StringUtils.isBlank(orderNo)) {
            return ResultVo.failure("参数不可为空");
        }
        log.info("接单查询详情 : 参数: " + orderNo);
        if (orderNo.contains("-")) {
            orderNo = orderNo.split("-")[0];
        } else {
            if (orderNo.length() == 10) {
                orderNo = orderNo.substring(0, 7);
            }
        }
        LambdaQueryWrapper<RcvMasterDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RcvMasterDO::getRorderNo, orderNo);
        RcvMasterDO rcvMasterDO = rcvMasterReadOnlyMapper.selectOne(queryWrapper);
        if (rcvMasterDO == null) {
            log.error("门户订单明细查询接口: 根据" + orderNo + "未查到数据");
            return ResultVo.success();
        }
        Order order = new Order();
        order.setOrderNo(rcvMasterDO.getRorderNo());
        order.setPurchaseNo(rcvMasterDO.getPurchaseNo());
        order.setCustomerNo(rcvMasterDO.getCustomerNo());

        //  add by LiYingChao from bug8420 in 2022/10/21
        // CustomerVO customerVO = getCustomerByCustomerNo(rcvMasterDO.getEndUser());
        CustomerVO customerVO = commonService.getCustomerInfoByNo(rcvMasterDO.getEndUser());
        if (StringUtils.isNotBlank(customerVO.getHlCode())) {
            order.setHolonNo(customerVO.getHlCode());
        } else {
            order.setHolonNo(customerVO.getHRUnitId());
        }

        String deptName = commonService.getDeptNameByNo(customerVO.getHlCode());
        if (StringUtils.isBlank(deptName)) {
            deptName = commonService.getDeptNameByNo(customerVO.getHRUnitId());
        }
        order.setHolonName(deptName);

        order.setUserNo(rcvMasterDO.getUserNo());
        order.setEmpSale(rcvMasterDO.getEmployee());

        // 客户名称,发票类型,客户类型
        CustomerVO customerInfoByNo = commonService.getCustomerInfoByNo(rcvMasterDO.getCustomerNo());
        if (customerInfoByNo != null) {
            order.setCustomerName(customerInfoByNo.getName());
            order.setInvoiceType(InvoiceTypeEnum.getCodeName(customerInfoByNo.getInvoiceType()));
            order.setCstmType(CstmTypeEnum.getTypeName(customerInfoByNo.getCustomerType()));
        }

        // 用户名称
        if (StringUtils.isNotBlank(rcvMasterDO.getUserNo())) {
            if (rcvMasterDO.getCustomerNo().equals(rcvMasterDO.getUserNo())) {
                order.setUserName(order.getCustomerName());
            } else {
                order.setUserName(commonService.getCustomerNameByNo(rcvMasterDO.getUserNo()));
            }
        }
        // 客户担当名称
        if (StringUtils.isNotBlank(order.getEmpSale())) {
            order.setEmpSaleName(commonService.getEmpSaleNameByNo(order.getEmpSale()));
        }
        if (rcvMasterDO.getRordDate() != null) {
            order.setOrderDate(rcvMasterDO.getRordDate());  // 订单日期
        }
        // 返回申请人名称 (对应内勤)
        order.setApplyPersonNo(rcvMasterDO.getEmployeeNo());  // 申请人编号
        if (StringUtils.isNotBlank(rcvMasterDO.getEmployeeNo())) {
            order.setApplyPersonName(commonService.getEmpSaleNameByNo(rcvMasterDO.getEmployeeNo()));
        }

        order.setQuotationNo(rcvMasterDO.getQuotationNo());
        order.setHddno(rcvMasterDO.getOrOrderNo());  // 合同订单号

        // 返回集约方式名称
        order.setIntensiveNo(rcvMasterDO.getDlvType());
        order.setIntensive(IntensiveEnum.getName(order.getIntensiveNo()));
        // 返回出货方式名称
        order.setDeliveryEntireNo(rcvMasterDO.getDlvEntire());
        order.setDeliveryEntire(DeliveryEntireEnum.getName(order.getDeliveryEntireNo()));
        // 运费承担方
        if (TransFeeEnum.SMC.getCode().equals(rcvMasterDO.getTransFee())) {
            order.setFee(TransFeeEnum.SMC.getName());
        } else if (TransFeeEnum.GK.getCode().equals(rcvMasterDO.getTransFee())) {
            order.setFee(TransFeeEnum.GK.getName());
        } else if (TransFeeEnum.QD.getCode().equals(rcvMasterDO.getTransFee())) {
            order.setFee(TransFeeEnum.QD.getName());
        }

        LambdaQueryWrapper<RcvDetailDO> queryDetail = new LambdaQueryWrapper<>();
        queryDetail.eq(RcvDetailDO::getRorderNo, orderNo).orderByAsc(RcvDetailDO::getRorderItem);
        List<RcvDetailDO> rcvDetailDOList = rcvDetailMapperReadOnlyMapper.selectList(queryDetail);
        if (CollectionUtils.isNotEmpty(rcvDetailDOList)) {
            // 订单数量
            int sum = rcvDetailDOList.stream().filter(item -> item.getQuantity() != null).collect(Collectors.toList()).stream().mapToInt(RcvDetailDO::getQuantity).sum();
            order.setOrderQuantity(sum);
            // 订单金额
            BigDecimal amount = rcvDetailDOList.stream().map(RcvDetailDO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            order.setOrderAmount(amount);
            // 发出数量
            int expNum = rcvDetailDOList.stream().filter(item -> item.getExpQty() != null).collect(Collectors.toList()).stream().mapToInt(RcvDetailDO::getExpQty).sum();
            // 未发货数量
            order.setUndeliveredQuantity(sum - expNum);
        }
        // 设置子项
        List<OrderItem> list = setItemList(rcvMasterDO.getRorderNo(), rcvDetailDOList);
        order.setItemList(list);
        if (CollectionUtils.isEmpty(order.getItemList())) {
            order.setCanChangeDeliveryModel(false); // 更改发货方式标识 true：可，false：不可
        } else {
            List<String> statusList = list.stream().map(OrderItem::getOrderStatusCode).collect(Collectors.toList());
            Boolean canChangeDeliveryModel = isCanChangeDeliveryModel(statusList, getNoCanSendOrderMethod());
            order.setCanChangeDeliveryModel(canChangeDeliveryModel);
        }
        return ResultVo.success(order);
    }


    /**
     * 判断是否可变更发货方式  update by Lyc from bug 10567 in 20230425
     * @param orderStatus 订单状态
     * @param noCanStautus 不可以变更的状态
     * @return
     */
    public Boolean isCanChangeDeliveryModel(List<String> orderStatus,List<Integer> noCanStautus ) {
        if(CollectionUtils.isEmpty(orderStatus) || CollectionUtils.isEmpty(noCanStautus)) {
            return false;
        }
        for (String item : orderStatus) {
            if (StringUtils.isNotBlank(item) && !noCanStautus.contains(Integer.valueOf(item))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 不可以变更的状态
     * @return
     */
    public List<Integer> getNoCanSendOrderMethod() {
        // 出库处理,已发货,已退货,已开票,异常订单,暂不处理,接单未处理   [是否可变更发货方式]
        return Arrays.asList((int) RcvOrderStatusEnum.CKING.getType(),
                (int) RcvOrderStatusEnum.CKED.getType(), (int) RcvOrderStatusEnum.RETURN.getType(),
                (int) RcvOrderStatusEnum.INVOICE.getType(), (int) RcvOrderStatusEnum.EXCEPT.getType(),
                (int) RcvOrderStatusEnum.UNDEAL.getType(),
                (int) RcvOrderStatusEnum.INIT.getType());
    }

    /**
     * add by LiYingChao from bug8420 in 2022/10/21
     * 根据客户查客户信息
     * @return
     */
    public CustomerVO getCustomerByCustomerNo(String customerNo) {
        if (StringUtils.isBlank(customerNo)) {
            return null;
        }
        return rcvdetailMapper.getCustomerByCustomerNo(customerNo);
    }

    @Override
    public ResultVo<List<StatusEnumInfo>> orderStatus() {
        List<StatusEnumInfo> list = new ArrayList<>();
        StatusEnumInfo statusEnumInfo;

        // 从字典获取接单状态配置
        ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(com.smc.smccloud.model.Constants.DICT_CLASSCODE_RCVORDERSTATUS);
        if (!dataCodes.isSuccess() || org.apache.commons.collections4.CollectionUtils.isEmpty(dataCodes.getData())) {
            return ResultVo.failure("获取状态失败.");
        }
        for(DataCodeVO item : dataCodes.getData()) {
            statusEnumInfo = new StatusEnumInfo();
            statusEnumInfo.setLabel(item.getCodeName());
            statusEnumInfo.setValue(item.getCode());
            list.add(statusEnumInfo);
        }
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<List<OrderItem>> getOrderItemStatus(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return ResultVo.failure("订单号不能为空");
        }

        LambdaQueryWrapper<RcvDetailDO> queryDetail;
        List<RcvDetailDO> rcvDetailDOList;

        if (orderNo.contains("-")) {
            String[] orderArr = orderNo.split("-");
            queryDetail = new LambdaQueryWrapper<>();
            queryDetail.eq(RcvDetailDO::getRorderNo, orderArr[0])
                    .eq(RcvDetailDO::getRorderItem, orderArr[1]);
            rcvDetailDOList = rcvdetailMapper.selectList(queryDetail);
        } else {
            queryDetail = new LambdaQueryWrapper<>();
            queryDetail.eq(RcvDetailDO::getRorderNo, orderNo);
            rcvDetailDOList = rcvdetailMapper.selectList(queryDetail);
        }

        if (CollectionUtils.isEmpty(rcvDetailDOList)) {
            return ResultVo.failure("订单号不存在");
        }
        List<OrderItem> itemList = new ArrayList<>(rcvDetailDOList.size());
        OrderItem item;
        for (RcvDetailDO detail : rcvDetailDOList) {
            item = new OrderItem();
            item.setOrderNo(detail.getRorderFno());
            item.setOrderStatusCode(detail.getStatus().toString());
            item.setOrderStatus(RCVOrderStatusEnum.getName(detail.getStatus()));
            item.setCanChangeDeliveryDate(true);
            item.setCanChangeDeliveryModel(true);
            if (detail.getStatus().equals(RCVOrderStatusEnum.IN_DELIVERY.getCode())
                    || detail.getStatus().equals(RCVOrderStatusEnum.DELIVERED.getCode())
                    || detail.getStatus().equals(RCVOrderStatusEnum.RETURN.getCode())
                    || detail.getStatus().equals(RCVOrderStatusEnum.CANCEL.getCode())) {
                item.setCanChangeDeliveryDate(false);
                item.setCanChangeDeliveryModel(false);
            }
            itemList.add(item);
        }
        return ResultVo.success(itemList);
    }

    @Override
    public ResultVo<OrderSchedule> orderSchedule(String orderNo) {
        log.info("接单查询进度追踪参数 {}", orderNo);
        if (StringUtils.isBlank(orderNo)) {
            return ResultVo.failure("订单参数不可为空");
        }
        LambdaQueryWrapper<OrderStateDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OrderStateDO::getOrderNo, orderNo);//.orderByDesc(OrderStateDO::getUpdateTime);
        List<OrderStateDO> orderStateDOS = orderStateMapperReadOnlyMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(orderStateDOS)) {
            return ResultVo.success();
        }
        OrderSchedule orderSchedule = new OrderSchedule();
        OrderStateDO stateDO = orderStateDOS.get(0);
        orderSchedule.setOrderDate(stateDO.getOrderDate());
        orderSchedule.setOrderNo(stateDO.getOrderNo());
        orderSchedule.setQuantity(stateDO.getQuantity());
        orderSchedule.setDlvDate(stateDO.getCustDlvDate());
        orderSchedule.setTransChannel(OPSTransTypeEnum.getNameByCode(stateDO.getPoTransType()));
        orderSchedule.setChangeTransChannel(OPSTransTypeEnum.getNameByCode(stateDO.getTransType()));
        orderSchedule.setInvoiceNo(stateDO.getPoInvoiceNo());
        //  -- 补库订单号
        // orderSchedule.setJpProcessFinishDate(stateDO.getPoShipDate());
        orderSchedule.setManufactureOrderDate(stateDO.getReceiveDate());
        orderSchedule.setCreateOrderDate(stateDO.getBeginProduceDate());
        orderSchedule.setWmsDlvDate(stateDO.getPoShipDate());
        // -- 入库日期
        orderSchedule.setOrderType(OrderTypeEnum.getCodeName(String.valueOf(stateDO.getOrderType())));
        return ResultVo.success(orderSchedule);
    }


//    @Override
//    public ResultVo<PageInfo<OrderDeleteReq>> findDelOrder(OrderDeleteCondition condition, Page page) {
//        if (condition == null) {
//            return ResultVo.failure("参数不可为空");
//        }
//        log.info("删单详情接口参数(处理前) => " + JSON.toJSONString(condition));
//        if (StringUtils.isNotBlank(condition.getStatus())) {
//            if ("业务处理中".equals(condition.getStatus())) {
//                condition.setStatus("5");
//            } else if ("已删单".equals(condition.getStatus())) {
//                condition.setStatus("6");
//            } else if ("已否决".equals(condition.getStatus())) {
//                condition.setStatus("7");
//            }
//        }
//        // 判断权限
//        List<String> userAuth = new ArrayList<>();
//        List<String> deptAuth = new ArrayList<>();
//        List<String> customerAuth = new ArrayList<>();
//        if (condition.getDataAuthoritySearchCondition() != null) {
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getUserIdListByDataAuthority())) {
//                userAuth = condition.getDataAuthoritySearchCondition().getUserIdListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
//                deptAuth = condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
//                customerAuth = condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority();
//            }
//        }
//        List<String> finalUserAuth = userAuth;
//        List<String> finalDeptAuth = deptAuth;
//        List<String> finalCustomerAuth = customerAuth;
//
//        log.info("删单详情接口参数(处理后) => " + JSON.toJSONString(condition));
//        LambdaQueryWrapper<OrderModifyDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper
//                .apply(CollectionUtils.isNotEmpty(finalUserAuth) || CollectionUtils.isNotEmpty(finalDeptAuth) || CollectionUtils.isNotEmpty(finalCustomerAuth), "( 1=1 ")
//                // 判断用户权限
//                .in(CollectionUtils.isNotEmpty(finalUserAuth), OrderModifyDO::getUserNo, finalUserAuth)
//                .or(CollectionUtils.isNotEmpty(finalUserAuth) && (CollectionUtils.isNotEmpty(finalDeptAuth) || CollectionUtils.isNotEmpty(finalCustomerAuth)))
//                // 判断部门权限
//                .in(CollectionUtils.isNotEmpty(finalDeptAuth), OrderModifyDO::getDeptNo, finalDeptAuth)
//                .or(CollectionUtils.isNotEmpty(finalDeptAuth) && CollectionUtils.isNotEmpty(finalCustomerAuth))
//                // 判断客户权限
//                .in(CollectionUtils.isNotEmpty(finalCustomerAuth), OrderModifyDO::getCustomerNo, finalCustomerAuth)
//                .apply(CollectionUtils.isNotEmpty(finalUserAuth) || CollectionUtils.isNotEmpty(finalDeptAuth) || CollectionUtils.isNotEmpty(finalCustomerAuth), " 1=1 ) ")
//                .likeRight(StringUtils.isNotBlank(condition.getOrderNo()), OrderModifyDO::getOrderNo, condition.getOrderNo())
//                .ge(null != condition.getApplyDateStart(), OrderModifyDO::getApplyTime, condition.getApplyDateStart())
//                .le(null != condition.getApplyDateEnd(), OrderModifyDO::getApplyTime, condition.getApplyDateEnd())
//                .likeRight(StringUtils.isNotBlank(condition.getCustomerNo()), OrderModifyDO::getCustomerNo, condition.getCustomerNo())
//                .likeRight(StringUtils.isNotBlank(condition.getUserNo()), OrderModifyDO::getUserNo, condition.getUserNo())
//                .likeRight(StringUtils.isNotBlank(condition.getModelNo()), OrderModifyDO::getCurModelno, condition.getModelNo())
//                .eq(OrderModifyDO::getModifyType, "C").eq(OrderModifyDO::getApplyNo, "-")
//                .eq(StringUtils.isNotBlank(condition.getStatus()), OrderModifyDO::getStatus, condition.getStatus())
//                .orderByDesc(OrderModifyDO::getApplyTime);
//
//        PageInfo<OrderModifyDO> orderModifyDOPageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
//                .doSelectPageInfo(() -> orderModifyMapper.selectList(queryWrapper));
//        List<OrderDeleteReq> list = new ArrayList<>();
//
//        System.out.println("orderModifyDOPageInfo.getList() = " + orderModifyDOPageInfo.getList().size());
//        System.out.println("orderModifyDOPageInfo.tojson() = " + JSONObject.toJSONString(orderModifyDOPageInfo.getList()));
//        if (orderModifyDOPageInfo.getList() != null && CollectionUtils.isNotEmpty(orderModifyDOPageInfo.getList())) {
//            OrderDeleteReq orderDeleteReq;
//            for (OrderModifyDO orderModifyDO : orderModifyDOPageInfo.getList()) {
//                orderDeleteReq = new OrderDeleteReq();
//                orderDeleteReq.setOrderNo(orderModifyDO.getOrderNo());
//                orderDeleteReq.setApplyPersonNo(orderModifyDO.getApplyPsn());
//                orderDeleteReq.setApplyTime(orderModifyDO.getApplyTime());
//                orderDeleteReq.setCustomerNo(orderModifyDO.getCustomerNo());
//                orderDeleteReq.setUserNo(orderModifyDO.getUserNo());
//                if (StringUtils.isNotBlank(orderModifyDO.getCustomerNo())) {
//                    orderDeleteReq.setCustomerName(commonService.getCustomerNameByNo(orderModifyDO.getCustomerNo()));
//                }
//                if (StringUtils.isNotBlank(orderModifyDO.getUserNo())) {
//                    orderDeleteReq.setUserName(commonService.getCustomerNameByNo(orderModifyDO.getUserNo()));
//                }
//                orderDeleteReq.setModelNo(orderModifyDO.getCurModelno());
//                orderDeleteReq.setQuantity(orderModifyDO.getCurQty());
//                orderDeleteReq.setPrice(orderModifyDO.getCurPrice());
//                orderDeleteReq.setApplyStatus(orderDeleteConventStatus(String.valueOf(orderModifyDO.getStatus()))); // 处理状态
//                orderDeleteReq.setMessage(orderModifyDO.getReason()); // 删单原因
//                // 根据订单号获取订单状态
//                orderDeleteReq.setOrderStatus(getOrderStatus(orderModifyDO.getOrderNo())); // 订单状态
//                orderDeleteReq.setHandleId(orderModifyDO.getApprovePsn());
//                list.add(orderDeleteReq);
//            }
//        }
//        PageInfo<OrderDeleteReq> orderDeleteReqPageInfo = BeanCopyUtil.pageDto2Vo(orderModifyDOPageInfo, OrderDeleteReq.class);
//        orderDeleteReqPageInfo.setList(list);
//        return ResultVo.success(orderDeleteReqPageInfo);
//    }

    @Override
    public ResultVo<PageInfo<OrderDeleteReq>> findDelOrder2(OrderDeleteCondition condition, Page page) {
        if (condition == null) {
            return ResultVo.failure("参数不可为空");
        }

        log.info("删单详情接口参数(处理前) => " + JSON.toJSONString(condition));

        if (condition.getApplyDateEnd() != null) {
            condition.setApplyDateEnd(DateUtil.getEndTime(condition.getApplyDateEnd()));
        }

        if (StringUtils.isNotBlank(condition.getOperator())) {
            if (condition.getOperator().startsWith("ACC") || condition.getOperator().startsWith("acc")) {
                return findDelOrderForAgent(condition,page);
            }
        }

        if (StringUtils.isNotBlank(condition.getStatus()) && !"已删单".equals(condition.getStatus())) {
            return ResultVo.success(new PageInfo<>());
        }

        // 判断权限
        List<String> userAuth = new ArrayList<>();
        List<String> deptAuth = new ArrayList<>();
        List<String> customerAuth = new ArrayList<>();
        List<String> inCodeAuth = new ArrayList<>();
        if (condition.getDataAuthoritySearchCondition() != null) {
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getUserNoListByDataAuthority())) {
                userAuth = condition.getDataAuthoritySearchCondition().getUserNoListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
                deptAuth = condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
                customerAuth = condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority())) {
                inCodeAuth = condition.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority();
            }
        }
        List<String> finalUserAuth = userAuth;
        List<String> finalDeptAuth = deptAuth;
        List<String> finalCustomerAuth = customerAuth;
        List<String> finalInCodeAuth = inCodeAuth;
        if (StringUtils.isNotBlank(condition.getOrderNo())) {
            condition.setOrderNo(condition.getOrderNo()+"%");
        }
        if (StringUtils.isNotBlank(condition.getCustomerNo())) {
            condition.setCustomerNo(condition.getCustomerNo()+"%");
        }
        if (StringUtils.isNotBlank(condition.getUserNo())) {
            condition.setUserNo(condition.getUserNo()+"%");
        }
        if (StringUtils.isNotBlank(condition.getModelNo())) {
            condition.setModelNo(condition.getModelNo()+"%");
        }
        PageInfo<DelOrderVO> pageInfo;
        try {
            pageInfo = PageHelper.startPage(page.getPageNumber(),page.getPageSize()).doSelectPageInfo(() -> {
                orderStateMapperReadOnlyMapper.findDelOrder(condition,finalDeptAuth,finalUserAuth,finalCustomerAuth,finalInCodeAuth);
            });
        } catch (Exception e) {
            if (e.getMessage().substring(25,100).contains("Timeout")) {
                return ResultVo.failure("查询超时");
            } else {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        if (CollectionUtils.isEmpty(pageInfo.getList())) {
            return ResultVo.success(BeanCopyUtil.pageDto2Vo(pageInfo,OrderDeleteReq.class));
        }
        List<OrderDeleteReq> list = new ArrayList<>(pageInfo.getList().size());
        OrderDeleteReq orderDeleteReq;
        Map<String, String> nameMap = new HashMap<>(pageInfo.getList().size());
        String name;
        for (DelOrderVO item : pageInfo.getList()) {
            orderDeleteReq=new OrderDeleteReq();
            orderDeleteReq.setOrderNo(item.getOrderNo());
            orderDeleteReq.setApplyTime(item.getStateDate());
            orderDeleteReq.setCustomerNo(item.getCustomerNo());
            // 客户名称
            if (StringUtils.isNotBlank(item.getCustomerNo())) {
                if (!nameMap.containsKey(item.getCustomerNo())) {
                    name = commonService.getCustomerNameByNo(item.getCustomerNo());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(item.getCustomerNo(), name);
                    }
                }
                orderDeleteReq.setCustomerName(nameMap.get(item.getCustomerNo()));
            }
            orderDeleteReq.setUserNo(item.getUserNo());
            // 用户名称
            if (StringUtils.isNotBlank(item.getUserNo())) {
                if (!nameMap.containsKey(item.getUserNo())) {
                    name = commonService.getCustomerNameByNo(item.getUserNo());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(item.getUserNo(), name);
                    }
                }
                orderDeleteReq.setUserName(nameMap.get(item.getUserNo()));
            }
            orderDeleteReq.setModelNo(item.getModelNo());
            orderDeleteReq.setQuantity(item.getQuantity());
            orderDeleteReq.setPrice(item.getPrice());
            orderDeleteReq.setOrderStatus("订单删除");
            orderDeleteReq.setHandleId(item.getOptUserNo());
            orderDeleteReq.setHandleName(item.getOptUserName());
            orderDeleteReq.setMessage(item.getStateDes());
            orderDeleteReq.setApplyStatus("已删单");
            list.add(orderDeleteReq);
        }
        PageInfo<OrderDeleteReq> orderDeleteReqPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, OrderDeleteReq.class);
        orderDeleteReqPageInfo.setList(list);
        return ResultVo.success(orderDeleteReqPageInfo);
    }

    @Override
    public ResultVo<PageInfo<OrderDeleteReq>> findDelOrderForAgent(OrderDeleteCondition condition, Page page) {
        if (condition == null) {
            return ResultVo.failure("参数不可为空");
        }
        log.info("删单详情接口参数(处理前) => " + JSON.toJSONString(condition));

        if (condition.getApplyDateEnd() != null) {
            condition.setApplyDateEnd(DateUtil.getEndTime(condition.getApplyDateEnd()));
        }

        if (CollectionUtils.isEmpty(condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
            return ResultVo.success(new PageInfo<>());
        }

        if (StringUtils.isNotBlank(condition.getStatus()) && !"已删单".equals(condition.getStatus())) {
            return ResultVo.success(new PageInfo<>());
        }

        // 判断权限
        List<String> userAuth = new ArrayList<>();
        List<String> deptAuth = new ArrayList<>();
        List<String> customerAuth = new ArrayList<>();
        List<String> inCodeAuth = new ArrayList<>();
        if (condition.getDataAuthoritySearchCondition() != null) {
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getUserNoListByDataAuthority())) {
                userAuth = condition.getDataAuthoritySearchCondition().getUserNoListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
                deptAuth = condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
                customerAuth = condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority())) {
                inCodeAuth = condition.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority();
            }
        }
        List<String> finalUserAuth = userAuth;
        List<String> finalDeptAuth = deptAuth;
        List<String> finalCustomerAuth = customerAuth;
        List<String> finalInCodeAuth = inCodeAuth;
        if (StringUtils.isNotBlank(condition.getOrderNo())) {
            condition.setOrderNo(condition.getOrderNo()+"%");
        }
        if (StringUtils.isNotBlank(condition.getCustomerNo())) {
            condition.setCustomerNo(condition.getCustomerNo()+"%");
        }
        if (StringUtils.isNotBlank(condition.getUserNo())) {
            condition.setUserNo(condition.getUserNo()+"%");
        }
        if (StringUtils.isNotBlank(condition.getModelNo())) {
            condition.setModelNo(condition.getModelNo()+"%");
        }
        PageInfo<DelOrderVO> pageInfo = null;
        try {
            pageInfo = PageHelper.startPage(page.getPageNumber(),page.getPageSize()).doSelectPageInfo(() -> {
                orderStateMapperReadOnlyMapper.findDelOrderForAgent(condition,finalDeptAuth,finalUserAuth,finalCustomerAuth,finalInCodeAuth);
            });
        } catch (Exception e) {
            if (e.getMessage().substring(25,100).contains("Timeout")) {
                return ResultVo.failure("查询超时");
            } else {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        if (CollectionUtils.isEmpty(pageInfo.getList())) {
            return ResultVo.success(BeanCopyUtil.pageDto2Vo(pageInfo,OrderDeleteReq.class));
        }
        List<OrderDeleteReq> list = new ArrayList<>(pageInfo.getList().size());
        OrderDeleteReq orderDeleteReq;
        Map<String, String> nameMap = new HashMap<>(pageInfo.getList().size());
        String name;
        for (DelOrderVO item : pageInfo.getList()) {
            orderDeleteReq=new OrderDeleteReq();
            orderDeleteReq.setOrderNo(item.getOrderNo());
            orderDeleteReq.setApplyTime(item.getStateDate());
            orderDeleteReq.setCustomerNo(item.getCustomerNo());
            // 客户名称
            if (StringUtils.isNotBlank(item.getCustomerNo())) {
                if (!nameMap.containsKey(item.getCustomerNo())) {
                    name = commonService.getCustomerNameByNo(item.getCustomerNo());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(item.getCustomerNo(), name);
                    }
                }
                orderDeleteReq.setCustomerName(nameMap.get(item.getCustomerNo()));
            }
            orderDeleteReq.setUserNo(item.getUserNo());
            // 用户名称
            if (StringUtils.isNotBlank(item.getUserNo())) {
                if (!nameMap.containsKey(item.getUserNo())) {
                    name = commonService.getCustomerNameByNo(item.getUserNo());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(item.getUserNo(), name);
                    }
                }
                orderDeleteReq.setUserName(nameMap.get(item.getUserNo()));
            }
            orderDeleteReq.setModelNo(item.getModelNo());
            orderDeleteReq.setQuantity(item.getQuantity());
            orderDeleteReq.setPrice(item.getPrice());
            orderDeleteReq.setOrderStatus("订单删除");
            orderDeleteReq.setHandleId(item.getOptUserNo());
            orderDeleteReq.setHandleName(item.getOptUserName());
            orderDeleteReq.setMessage(item.getStateDes());
            orderDeleteReq.setApplyStatus("已删单");
            list.add(orderDeleteReq);
        }
        PageInfo<OrderDeleteReq> orderDeleteReqPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, OrderDeleteReq.class);
        orderDeleteReqPageInfo.setList(list);
        return ResultVo.success(orderDeleteReqPageInfo);
    }

    public String getOrderStatus(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }
        LambdaQueryWrapper<RcvDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RcvDetailDO::getRorderFno, orderNo);
        RcvDetailDO rcvDetailDO = rcvdetailMapper.selectOne(queryWrapper);
        if (rcvDetailDO == null || rcvDetailDO.getStatus() == null) {
            return null;
        }
        return RCVOrderStatusEnum.getName(rcvDetailDO.getStatus());

    }

    public String orderDeleteConventStatus(String status) {
        if (StringUtils.isBlank(status)) {
            return "";
        }
        switch (status) {
            case "5":
                status = "业务处理中";
                break;
            case "6":
                status = "已删单";
                break;
            case "7":
                status = "已否决";
                break;
            default:
                status = "";
                break;
        }
        return status;
    }

    @Override
    public ResultVo<PageInfo<PurchaseOrderBean>> listOrderStateWithCustomer(PurchaseOrderCondition request, int pageNumber, int pageSize) {
        log.info("查询交货期状态处理前参数 : " + JSONObject.toJSONString(request));

        long lstart = System.currentTimeMillis();

        if (StringUtils.isNotBlank(request.getOrderNo())) {
            request.setOrderNo(request.getOrderNo().trim());
        }

        if (pageNumber == 0) {
            pageNumber = 1;
        }
        if (pageSize == 0) {
            pageSize = 10;
        }

        if (StringUtils.isNotBlank(request.getOperator())) {
            if (request.getOperator().startsWith("ACC") || request.getOperator().startsWith("acc")) {
                return listOrderStateWithCustomerForAgent(request,pageNumber,pageSize);
            }
        }

        if (StringUtils.isNotBlank(request.getInvoiceNo())) {
            request.setInvoiceNo(request.getInvoiceNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getPurchaseNo())) {
            request.setPurchaseNo(request.getPurchaseNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getSupplierOrderNo())) {
            request.setSupplierOrderNo(request.getSupplierOrderNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getOrderNo())) {
            if (!request.getExactSearchFlag()) {
                request.setOrderNo(request.getOrderNo() + "%");
            }
        } else {
            request.setOrderNo("");
        }
        if (StringUtils.isNotBlank(request.getModelNo())) {
            request.setModelNo(request.getModelNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getCustomerNo())) {
            request.setCustomerNo(request.getCustomerNo() + "%");
        }
        // 排序字段转换
        if (StringUtils.isNotBlank(request.getProperty())) {
            request.setProperty(getSortFieldForOrderStateWithCustomer2(request.getProperty(), request.getOrder()));
        }
        // 供应商分类
        if (StringUtils.isNotBlank(request.getPurchaseType()) && StringUtils.isNotBlank(request.getSupplier())) {
            return ResultVo.failure("选择了供货地,供应商分类可不选.");
        }
        if (request.getPurchaseType() != null) {
            if (request.getPurchaseType().equals("制造")) {
                request.setChinaSupplierList(ChinaSuppplierEnum.getAllCNSupplier());
            } else if (request.getPurchaseType().equals("日本及海外")) {
                request.setNotChinasupplierList(ChinaSuppplierEnum.getAllCNSupplier());
            }
        }
        // 判断权限
        List<String> userAuth = new ArrayList<>();
        List<String> deptAuth = new ArrayList<>();
        List<String> customerAuth = new ArrayList<>();
        List<String> employeeAuth = new ArrayList<>();
        List<String> inCodeAuth = new ArrayList<>();
        if (request.getDataAuthoritySearchCondition() != null) {
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getUserNoListByDataAuthority())) {
                userAuth = request.getDataAuthoritySearchCondition().getUserNoListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
                deptAuth = request.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
                customerAuth = request.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getUserIdListByDataAuthority())) {
                employeeAuth = request.getDataAuthoritySearchCondition().getUserIdListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority())) {
                inCodeAuth = request.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority();
            }
        }

        List<String> finalUserAuth = userAuth;
        List<String> finalDeptAuth = deptAuth;
        List<String> finalCustomerAuth = customerAuth;
        List<String> finalEmployeeAuth = employeeAuth;
        List<String> finalInCodeAuth = inCodeAuth;

        PageInfo<PurchaseOrderBean> pageInfo = null;
       try {
            pageInfo = PageHelper.startPage(pageNumber, pageSize).doSelectPageInfo(() -> {
                orderStateMapperReadOnlyMapper.listOrderStateWithCustomer(request, finalDeptAuth, finalUserAuth, finalCustomerAuth,finalInCodeAuth,finalEmployeeAuth);
            });
        } catch (Exception e) {
           if (e.getMessage().substring(25,100).contains("Timeout")) {
               return ResultVo.failure("查询超时");
           } else {
               e.printStackTrace();
               throw new RuntimeException(e.getMessage());
           }
        }

        if (CollectionUtils.isEmpty(pageInfo.getList())) {
            return ResultVo.success(pageInfo);
        }
        long lend = System.currentTimeMillis();
        log.info("交货期查询接口 SQL执行消耗时间: " + (lend - lstart) + "(ms) " + (lend - lstart) / 1000 + "(s)");
        List<PurchaseOrderBean> list = commonOrderStateDataHand(pageInfo.getList());
        pageInfo.setList(list);
//        Map<String, String> nameMap = new HashMap<>(pageInfo.getList().size());
//        String name;
//        for (PurchaseOrderBean item : pageInfo.getList()) {
//            // 客户名称
//            if (StringUtils.isNotBlank(item.getCustomerNo()) && !nameMap.containsKey(item.getCustomerNo())) {
//                name = commonService.getCustomerNameByNo(item.getCustomerNo());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getCustomerNo(), name);
//                }
//            }
//            item.setCustomerName(nameMap.get(item.getCustomerNo()));
//            // 用户名称
//            if (StringUtils.isNotBlank(item.getUserNo()) && !nameMap.containsKey(item.getUserNo())) {
//                name = commonService.getCustomerNameByNo(item.getUserNo());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getUserNo(), name);
//                }
//            }
//            item.setUserName(nameMap.get(item.getUserNo()));
//
//            // 最终用户
//            if (StringUtils.isNotBlank(item.getEndUser()) && !nameMap.containsKey(item.getEndUser())) {
//                name = commonService.getCustomerNameByNo(item.getEndUser());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getEndUser(), name);
//                }
//            }
//            item.setEndUserName(nameMap.get(item.getEndUser()));
//
//            item.setOrderTypeName(OrderTypeEnum.getCodeName(item.getOrderType()));
//
//            if (item.getIntercept() != null && item.getIntercept() && !isSendOrderStatusForOrderState(Integer.valueOf(item.getStateCode()))) {
//                item.setStateCodeName("信用拦截");
//                item.setStateDes("信用拦截");
//            } else {
//                if (StringUtils.isNotBlank(item.getStateCode())) {
//                    item.setStateCodeName(OrderStateEnum.getStateNameByCode(Integer.parseInt(item.getStateCode())));
//                }
//            }
//
//            if (StringUtils.isBlank(item.getSupplierCode())) {
//                item.setSupplierCode(item.getSupplierNo());
//            }
//
//            // 供应商名称
//            if (StringUtils.isNotBlank(item.getSupplierCode()) && !nameMap.containsKey(item.getSupplierCode())) {
//                name = commonService.getSupplierNameByCode(item.getSupplierCode());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getSupplierCode(), name);
//                }
//            }
//            item.setSupplierCodeName(nameMap.get(item.getSupplierCode()));
//            // 运输方式名称
//            item.setPoTransTypeName(OPSTransTypeEnum.getNameByCode(item.getTransType()));
//            // 采购类型名称
//            item.setPurchaseTypeName(PurchaseTypeEnum.getName(item.getPurchaseType()));
//            // 仓库名称
//            if (StringUtils.isNotBlank(item.getWarehouseCode()) && !nameMap.containsKey(item.getWarehouseCode())) {
//                name = commonService.getWarehouseNameByCode(item.getWarehouseCode());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getWarehouseCode(), name);
//                }
//            }
//            item.setWarehouseCodeName(nameMap.get(item.getWarehouseCode()));
//
//            // hl部门名称
//            if (StringUtils.isNotBlank(item.getHlCode())) {
//                if (!nameMap.containsKey(item.getHlCode())) {
//                    name = commonService.getDeptNameByNo(item.getHlCode());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getHlCode(), name);
//                    }
//                }
//                item.setDeptNo(item.getHlCode());
//                item.setDeptName(nameMap.get(item.getHlCode()));
//            } else {
//                if (!nameMap.containsKey(item.getHrUnitId())) {
//                    name = commonService.getDeptNameByNo(item.getHrUnitId());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getHrUnitId(), name);
//                    }
//                }
//                item.setDeptNo(item.getHrUnitId());
//                item.setDeptName(nameMap.get(item.getHrUnitId()));
//            }
//            // add by LiYingChao from bug 9002/8993 in 2022/12/12
//            // 订单所属部门
//            if (StringUtils.isNotBlank(item.getOrderDeptNo())) {
//                if (!nameMap.containsKey(item.getOrderDeptNo())) {
//                    String deptNameByNo = commonService.getDeptNameByNo(item.getOrderDeptNo());
//                    if (StringUtils.isNotBlank(deptNameByNo)) {
//                        nameMap.put(item.getOrderDeptNo(),deptNameByNo);
//                    }
//                }
//                item.setOrderDeptName(nameMap.get(item.getOrderDeptNo()));
//            }
//        }
        long endTime = System.currentTimeMillis();
        log.info("交货期查询接口 查询结束消耗时间: " + (endTime - lstart) + "(ms) " + (endTime - lstart) / 1000 + "(s)");
        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<PageInfo<PurchaseOrderBean>> listOrderStateWithCustomerForAgent(PurchaseOrderCondition request, int pageNumber, int pageSize) {
        log.info("查询交货期状态处理前参数 : " + JSONObject.toJSONString(request));

        long lstart = System.currentTimeMillis();

        if (StringUtils.isNotBlank(request.getOrderNo())) {
            request.setOrderNo(request.getOrderNo().trim());
        }

        if (pageNumber == 0) {
            pageNumber = 1;
        }
        if (pageSize == 0) {
            pageSize = 10;
        }

        PageInfo<PurchaseOrderBean> pageInfo = null;

        if (CollectionUtils.isEmpty(request.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
            pageInfo = new PageInfo<>();
            return ResultVo.success(pageInfo);
        }

        if (StringUtils.isNotBlank(request.getInvoiceNo())) {
            request.setInvoiceNo(request.getInvoiceNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getPurchaseNo())) {
            request.setPurchaseNo(request.getPurchaseNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getSupplierOrderNo())) {
            request.setSupplierOrderNo(request.getSupplierOrderNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getOrderNo())) {
            if (!request.getExactSearchFlag()) {
                request.setOrderNo(request.getOrderNo() + "%");
            }
        } else {
            request.setOrderNo("");
        }
        if (StringUtils.isNotBlank(request.getModelNo())) {
            request.setModelNo(request.getModelNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getCustomerNo())) {
            request.setCustomerNo(request.getCustomerNo() + "%");
        }
        // 排序字段转换
        if (StringUtils.isNotBlank(request.getProperty())) {
            request.setProperty(getSortFieldForOrderStateWithCustomer2(request.getProperty(), request.getOrder()));
        }
        // 供应商分类
        if (StringUtils.isNotBlank(request.getPurchaseType()) && StringUtils.isNotBlank(request.getSupplier())) {
            return ResultVo.failure("选择了供货地,供应商分类可不选.");
        }
        if (request.getPurchaseType() != null) {
            if (request.getPurchaseType().equals("制造")) {
                request.setChinaSupplierList(ChinaSuppplierEnum.getAllCNSupplier());
            } else if (request.getPurchaseType().equals("日本及海外")) {
                request.setNotChinasupplierList(ChinaSuppplierEnum.getAllCNSupplier());
            }
        }
        // 判断权限
        List<String> userAuth = new ArrayList<>();
        List<String> deptAuth = new ArrayList<>();
        List<String> customerAuth = new ArrayList<>();
        List<String> employeeAuth = new ArrayList<>();
        List<String> inCodeAuth = new ArrayList<>();
        if (request.getDataAuthoritySearchCondition() != null) {
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getUserNoListByDataAuthority())) {
                userAuth = request.getDataAuthoritySearchCondition().getUserNoListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
                deptAuth = request.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
                customerAuth = request.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getUserIdListByDataAuthority())) {
                employeeAuth = request.getDataAuthoritySearchCondition().getUserIdListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority())) {
                inCodeAuth = request.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority();
            }
        }

        List<String> finalUserAuth = userAuth;
        List<String> finalDeptAuth = deptAuth;
        List<String> finalCustomerAuth = customerAuth;
        List<String> finalEmployeeAuth = employeeAuth;
        List<String> finalInCodeAuth = inCodeAuth;

        try {
            pageInfo = PageHelper.startPage(pageNumber, pageSize).doSelectPageInfo(() -> {
                orderStateMapperReadOnlyMapper.listOrderStateWithCustomerForAgent(request, finalDeptAuth, finalUserAuth, finalCustomerAuth,finalInCodeAuth);
            });
        } catch (Exception e) {
            if (e.getMessage().substring(25,100).contains("Timeout")) {
                return ResultVo.failure("查询超时");
            } else {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        if (CollectionUtils.isEmpty(pageInfo.getList())) {
            return ResultVo.success(pageInfo);
        }
        long lend = System.currentTimeMillis();
        log.info("交货期查询接口 SQL执行消耗时间: " + (lend - lstart) + "(ms) " + (lend - lstart) / 1000 + "(s)");
        List<PurchaseOrderBean> list = commonOrderStateDataHand(pageInfo.getList());
        pageInfo.setList(list);
//        Map<String, String> nameMap = new HashMap<>(pageInfo.getList().size());
//        String name;
//        for (PurchaseOrderBean item : pageInfo.getList()) {
//            // 客户名称
//            if (StringUtils.isNotBlank(item.getCustomerNo()) && !nameMap.containsKey(item.getCustomerNo())) {
//                name = commonService.getCustomerNameByNo(item.getCustomerNo());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getCustomerNo(), name);
//                }
//            }
//            item.setCustomerName(nameMap.get(item.getCustomerNo()));
//            // 用户名称
//            if (StringUtils.isNotBlank(item.getUserNo()) && !nameMap.containsKey(item.getUserNo())) {
//                name = commonService.getCustomerNameByNo(item.getUserNo());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getUserNo(), name);
//                }
//            }
//            item.setUserName(nameMap.get(item.getUserNo()));
//
//            // 最终用户
//            if (StringUtils.isNotBlank(item.getEndUser()) && !nameMap.containsKey(item.getEndUser())) {
//                name = commonService.getCustomerNameByNo(item.getEndUser());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getEndUser(), name);
//                }
//            }
//            item.setEndUserName(nameMap.get(item.getEndUser()));
//
//            item.setOrderTypeName(OrderTypeEnum.getCodeName(item.getOrderType()));
//
//            if (item.getIntercept() != null && item.getIntercept() && !isSendOrderStatusForOrderState(Integer.valueOf(item.getStateCode()))) {
//                item.setStateCodeName("信用拦截");
//                item.setStateDes("信用拦截");
//            } else {
//                if (StringUtils.isNotBlank(item.getStateCode())) {
//                    item.setStateCodeName(OrderStateEnum.getStateNameByCode(Integer.parseInt(item.getStateCode())));
//                }
//            }
//
//            // 供应商名称
//            if (StringUtils.isNotBlank(item.getSupplierCode()) && !nameMap.containsKey(item.getSupplierCode())) {
//                name = commonService.getSupplierNameByCode(item.getSupplierCode());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getSupplierCode(), name);
//                }
//            }
//            item.setSupplierCodeName(nameMap.get(item.getSupplierCode()));
//            // 运输方式名称
//            item.setPoTransTypeName(OPSTransTypeEnum.getNameByCode(item.getTransType()));
//            // 采购类型名称
//            item.setPurchaseTypeName(PurchaseTypeEnum.getName(item.getPurchaseType()));
//            // 仓库名称
//            if (StringUtils.isNotBlank(item.getWarehouseCode()) && !nameMap.containsKey(item.getWarehouseCode())) {
//                name = commonService.getWarehouseNameByCode(item.getWarehouseCode());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getWarehouseCode(), name);
//                }
//            }
//            item.setWarehouseCodeName(nameMap.get(item.getWarehouseCode()));
//
//            // hl部门名称
//            if (StringUtils.isNotBlank(item.getHlCode())) {
//                if (!nameMap.containsKey(item.getHlCode())) {
//                    name = commonService.getDeptNameByNo(item.getHlCode());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getHlCode(), name);
//                    }
//                }
//                item.setDeptNo(item.getHlCode());
//                item.setDeptName(nameMap.get(item.getHlCode()));
//            } else {
//                if (!nameMap.containsKey(item.getHrUnitId())) {
//                    name = commonService.getDeptNameByNo(item.getHrUnitId());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getHrUnitId(), name);
//                    }
//                }
//                item.setDeptNo(item.getHrUnitId());
//                item.setDeptName(nameMap.get(item.getHrUnitId()));
//            }
//            // 订单所属部门
//            if (StringUtils.isNotBlank(item.getOrderDeptNo())) {
//                if (!nameMap.containsKey(item.getOrderDeptNo())) {
//                    String deptNameByNo = commonService.getDeptNameByNo(item.getOrderDeptNo());
//                    if (StringUtils.isNotBlank(deptNameByNo)) {
//                        nameMap.put(item.getOrderDeptNo(),deptNameByNo);
//                    }
//                }
//                item.setOrderDeptName(nameMap.get(item.getOrderDeptNo()));
//            }
//        }
        long endTime = System.currentTimeMillis();
        log.info("交货期查询接口 查询结束消耗时间: " + (endTime - lstart) + "(ms) " + (endTime - lstart) / 1000 + "(s)");
        return ResultVo.success(pageInfo);
    }


    @Override
    public ResultVo<List<StatusEnumInfo>> getOrderStateStatus() {

        List<StatusEnumInfo> list = new ArrayList<>();
        StatusEnumInfo statusEnumInfo;
        // 从字典获取交货期状态配置
        ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(com.smc.smccloud.model.Constants.DICT_CLASSCODE_ORDERSTATE);
        if (!dataCodes.isSuccess() || org.apache.commons.collections4.CollectionUtils.isEmpty(dataCodes.getData())) {
            return ResultVo.failure("获取状态失败.");
        }
        for(DataCodeVO item : dataCodes.getData()) {
            statusEnumInfo = new StatusEnumInfo();
            statusEnumInfo.setLabel(item.getCodeName());
            statusEnumInfo.setValue(item.getCode());
            list.add(statusEnumInfo);
        }
        return ResultVo.success(list);
    }

    /**
     * CONVERT(VARCHAR(10),order_date,121)
     * add by LiYingChao from bugid 8636 in 2022/11/14
     */
    private String getSortFieldForOrderStateWithCustomer2(String property, String order) {
        String str;
        if (StringUtils.isBlank(property)) {
            str = " CONVERT(VARCHAR,order_date,112) desc , rorder_no " + order + " , item_no ";
        }
        switch (property) {
            case "orderNo":
                str = " CONVERT(VARCHAR,order_date,112) desc , rorder_no " + order + " , item_no ";
                break;
            case "orderTypeName":
                str = "purchase_type";
                break;
            case "startDate":
                str = "CONVERT(VARCHAR,order_date,112)";
                break;
            case "endDate":
                str = "CONVERT(VARCHAR,order_date,112)";
                break;
            case "smcCode":
                str = "supplier_code";
                break;
            case "deliveryDateJP":
                str = "po_dlv_date";
                break;
            case "expectDateGC":
                str = "po_ship_date";
                break;
            case "deliveryChangeDateA":
                str = "po_reply_dateA";
                break;
            case "deliveryChangeDateB":
                str = "po_reply_dateB";
                break;
            case "deliveryChangeDateC":
                str = "po_reply_dateC";
                break;
            case "remarkDateGC":
                str = "supplier_orderNo";
                break;
            case "produceHolon":
                str = "state_des";
                break;
            case "invoiceNo":
                str = "po_invoice_no";
                break;
            case "inDateInFact":
                str = "po_ship_date";
                break;
            case "inStoreDate":
                str = "poRcvTime";
                break;
            case "changeTransType":
                str = "trans_Type";
                break;
            case "urgeNoA":
                str = "inqA";
                break;
            case "inqB":
                str = "inqB";
                break;
            case "shikomiNo":
                str = "shikomi_no";
                break;
            case "deliveryExceptionReason":
                str = "state_des";
                break;
            case "corderNo":
                str = "corderNo";
                break;
            default:
                str = " CONVERT(VARCHAR,order_date,112) desc , rorder_no " + order + " , item_no ";
                break;

        }
        return str;
    }

    private String getSortFieldForOrderQueryWithAgent(String property, String order) {
        if (StringUtils.isBlank(property)) {
            return " ROrdDate desc , rd.rorder_no " + order + " , rd.rorder_item";
        }
        String str = " ROrdDate desc , rd.rorder_no " + order + " , rd.rorder_item";
        switch (property) {
            case "orderNo":
                str = " ROrdDate desc , rd.rorder_no " + order + " , rd.rorder_item";
                break;
            case "orderDate":
                str = "ROrdDate";
                break;
            case "customerNo":
                str = "rm.customer_no";
                break;
            case "userNo":
                str = "user_no";
                break;
            case "endUser":
                str = "end_user";
                break;
            case "price":
                str = "rd.pricce";
                break;
            case "orderAmount":
                str = "rd.amount";
                break;
            case "salesDeptNo":
                str = "dept_No";
                break;
            case "orderType":
                str = "rd.order_type";
                break;
        }
        return str;
    }

    private String getSortFieldForOrderQuery4(String property, String order) {
        if (StringUtils.isBlank(property)) {
            return " ROrdDate desc , rorder_no " + order + " , rorder_item";
        }
        String str = " ROrdDate desc , rorder_no " + order + " , rorder_item";
        switch (property) {
            case "orderNo":
                str = " ROrdDate desc , rorder_no " + order + " , rorder_item";
                break;
            case "orderDate":
                str = "ROrdDate";
                break;
            case "customerNo":
                str = "customer_no";
                break;
            case "userNo":
                str = "user_no";
                break;
            case "endUser":
                str = "end_user";
                break;
            case "price":
                str = "pricce";
                break;
            case "orderAmount":
                str = "amount";
                break;
            case "salesDeptNo":
                str = "dept_No";
                break;
            case "orderType":
                str = "order_type";
                break;
            case "hlCode":
                str = "HLCode";
                break;
        }
        return str;
    }

    public List<OrderItem> setItemList(String orderNo, List<RcvDetailDO> rcvDetailDOList) {

        if (CollectionUtils.isEmpty(rcvDetailDOList)) {
            return Collections.emptyList();
        }
        List<OrderItem> orderItemList = new ArrayList<>(rcvDetailDOList.size());
        OrderItem orderItem;
        // 收货地址实体类
        OrderDlvDataVO orderDlvDataVO = new OrderDlvDataVO();
        orderDlvDataVO.setOrderNo(orderNo);
        orderDlvDataVO.setItemNo(0);
        OrderDlvDataVO orderDlvInfo = orderDlvDataService.findOrderDlvInfo(orderDlvDataVO);
        DeliveryAddressInfo addressInfo = setDeliveryAddressInfo(orderDlvInfo);

        List<String> noInterceptList = noIntercepterList();

        for (RcvDetailDO item : rcvDetailDOList) {
            orderItem = new OrderItem();
            if (StringUtils.isNotBlank(item.getStockType())) {
                orderItem.setExpInvCode(com.smc.smccloud.model.enums.OrderStockTypeEnum.getTypeName(item.getStockType()));
            }
            orderItem.setExpInvCode(item.getStockCode());

            // 更改货期标识
            orderItem.setCanChangeDeliveryDate(canChangeDlvDate(item.getStatus()));

            // 是否可变更特发
            if (item.getStatus() != null && StringUtils.isNotBlank(item.getProdFlag())) {
                if (canUpSepc(item.getStatus(),Integer.parseInt(item.getProdFlag()))) {
                    orderItem.setCanUpSpec(true);
                } else {
                    orderItem.setCanUpSpec(false);
                }
            } else {
                orderItem.setCanUpSpec(false);
            }

            orderItem.setIntercept(item.getIntercept());
            orderItem.setInterceptTime(item.getInterceptTime());

            orderItem.setOrderNo(item.getRorderFno());

            if (item.getStatus() != null) {
                if (noInterceptList.contains(String.valueOf(item.getStatus()))) {
                    orderItem.setOrderStatus(RCVOrderStatusEnum.getName(item.getStatus()));
                } else {
                    // 判断是否拦截,若是拦截则显示信用拦截,反之显示订单状态
                    if (item.getIntercept() != null && item.getIntercept()) {
                        orderItem.setOrderStatus("信用拦截");
                    } else {
                        orderItem.setOrderStatus(RCVOrderStatusEnum.getName(item.getStatus()));
                    }
                }
                orderItem.setOrderStatusCode(item.getStatus().toString());
            }

            if (item.getDlvDate() != null) {
                orderItem.setDlvDate(item.getDlvDate());
                orderItem.setCustomerChangeDate(item.getDlvDate()); // 变更客户货期
            }
            if (item.getCdlvDate() != null) {
                orderItem.setDlvDate(item.getCdlvDate());
            }
            if (item.getWmsDlvDate() != null) {
                orderItem.setWarehouseSendDate(item.getWmsDlvDate()); // 物流发货期
            }
            orderItem.setModelNo(item.getModelNo());
            orderItem.setCustomerProductNo(item.getCproductNo());
            orderItem.setQuantity(Optional.ofNullable(item.getQuantity()).orElse(0));
            orderItem.setPrice(Optional.ofNullable(item.getPrice()).orElse(BigDecimal.ZERO));
            orderItem.setUserPrice(Optional.ofNullable(item.getPriceUser()).orElse(BigDecimal.ZERO));
            // E率
            orderItem.setERate(CommonFormulaUtil.calcDiscount(CommonFormulaUtil.calcNoTaxPrice(orderItem.getPrice(), item.getTaxRate()), item.getEPrice()));
            // 用户E率
            orderItem.setUserERate(CommonFormulaUtil.calcDiscount(CommonFormulaUtil.calcNoTaxPrice(orderItem.getUserPrice(), item.getTaxRate()), item.getEPrice()));
            orderItem.setEndUserPrice(Optional.ofNullable(item.getPriceEndUser()).orElse(BigDecimal.ZERO));
            orderItem.setSpecOfferNo(item.getSpecOfferNo());
            orderItem.setPreSaleOrderNo(item.getSalesInfoNo());
            orderItem.setShikomi(item.getShikomiNo());
            orderItem.setExpInvCode(item.getStockCode());
            orderItem.setRemark(item.getRemark());
            orderItem.setShipDate(item.getShipTime());
            orderItem.setOrderTypeName(OrderTypeEnum.getCodeName(String.valueOf(item.getOrderType())));
            // 运单号
            orderItem.setTrackOrderNo(item.getExpressNo());

            if (StringUtils.isNotBlank(item.getCarrierId())) {
                // 承运商名称
                orderItem.setCarrier(opsCommonService.getCarrierNameByCode(item.getCarrierId()));
                // 承运商编码
                orderItem.setCarrierNo(item.getCarrierId());
            }
            // 发票号
            // 开票日期
            orderItem.setBillingDate(item.getInvoiceTime());
            orderItem.setProdFlag(item.getProdFlag()); // 拆分标识
            // 能否删单
            orderItem.setCanDelete(canDelete(item.getStatus(),item.getExpQty()));

            orderItem.setRohs10(OrderSpecExpType.include(item.getExpDlvType(), OrderSpecExpType.ROSH10Product) ? "rohs10" : "");
            if (OrderSpecExpType.include(item.getExpDlvType(), OrderSpecExpType.AssChildToExport)) {
                orderItem.setAssemble("子项特发");
            } else if (OrderSpecExpType.include(item.getExpDlvType(), OrderSpecExpType.OneItemToAssemble)) {
                orderItem.setAssemble("组装指令");
            } else {
                orderItem.setAssemble("");
            }
            orderItem.setSpecialNormal(OrderSpecExpType.include(item.getExpDlvType(), OrderSpecExpType.ApplySpecExport) ? "特发" : "普通");

            Boolean aBoolean = canDelivery(item.getStatus());
            // 是否装箱
            orderItem.setPackingFlag(aBoolean);
            // 是否发货
            orderItem.setDeliveryFlag(aBoolean);
            // 是否出组装波次
            orderItem.setChubociFlag(aBoolean);
            // 是否变更
            orderItem.setCanChangeDeliveryModel(canChangeShipMethods(item.getStatus()));

            orderItem.setDeliveryAddressInfo(addressInfo);
            if (addressInfo != null && StringUtils.isNotBlank(addressInfo.getOptCarrier())) {
                orderItem.setOptCarrier(addressInfo.getOptCarrier());
            }
            if (StringUtils.isNotBlank(item.getAddressNo()) && isNum(item.getAddressNo())) {
                // OrderDlvDataVO orderDlvData = orderDlvDataService.findOrderDlvInfo(orderDlvDataVO);
                OrderDlvDataDO orderDlvDataDO = orderDlvDataMapperReadOnlyMapper.selectById(item.getAddressNo());
                DeliveryAddressInfo deliveryAddressInfo = setDeliveryAddressInfo(BeanCopyUtil.copy(orderDlvDataDO, OrderDlvDataVO.class));
                orderItem.setDeliveryAddressInfo(deliveryAddressInfo);
                orderItem.setOptCarrier(deliveryAddressInfo.getOptCarrier());
            }
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    /**
     * 是否装箱/发货/出组装波次
     */
    public Boolean canDelivery(Integer status) {
        if (status == null) {
            return false;
        }
        List<Integer> list = Arrays.asList((int) RcvOrderStatusEnum.CKED.getType(), (int) RcvOrderStatusEnum.RETURN.getType(), (int) RcvOrderStatusEnum.INVOICE.getType());

        if (list.contains(status)) {
            return true;
        }
        return false;

    }

    public List<OrderItem> getOrderItemByOrerNo(String orderNo) {

        if (StringUtils.isBlank(orderNo)) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<RcvDetailDO> queryDetail = new LambdaQueryWrapper<>();
        queryDetail.eq(RcvDetailDO::getRorderNo, orderNo);
        List<RcvDetailDO> rcvDetailDOList = rcvdetailMapper.selectList(queryDetail);

        if (CollectionUtils.isEmpty(rcvDetailDOList)) {
            return Collections.emptyList();
        }
        List<OrderItem> orderItemList = new ArrayList<>(rcvDetailDOList.size());
        OrderItem orderItem;

        // 收货地址实体类
        OrderDlvDataVO orderDlvDataVO = new OrderDlvDataVO();
        orderDlvDataVO.setOrderNo(orderNo);
        orderDlvDataVO.setItemNo(0);
        OrderDlvDataVO orderDlvInfo = orderDlvDataService.findOrderDlvInfo(orderDlvDataVO);
        DeliveryAddressInfo addressInfo = setDeliveryAddressInfo(orderDlvInfo);

        for (RcvDetailDO item : rcvDetailDOList) {
            orderItem = new OrderItem();
            if (StringUtils.isNotBlank(item.getStockType())) {
                orderItem.setExpInvCode(com.smc.smccloud.model.enums.OrderStockTypeEnum.getTypeName(item.getStockType()));
            }
            orderItem.setExpInvCode(item.getStockCode());

            orderItem.setOrderNo(item.getRorderFno());
            // 设置订单状态名称
            orderItem.setOrderStatus(RCVOrderStatusEnum.getName(item.getStatus()));
            orderItem.setOrderStatusCode(item.getStatus().toString());
            if (item.getDlvDate() != null) {
                orderItem.setDlvDate(item.getDlvDate());
                orderItem.setCustomerChangeDate(item.getDlvDate()); // 变更客户货期
            }
            if (item.getCdlvDate() != null) {
                orderItem.setDlvDate(item.getCdlvDate());
            }
            if (item.getWmsDlvDate() != null) {
                orderItem.setWarehouseSendDate(item.getWmsDlvDate()); // 物流发货期
            }
            orderItem.setModelNo(item.getModelNo());
            orderItem.setCustomerProductNo(item.getCproductNo());
            orderItem.setQuantity(Optional.ofNullable(item.getQuantity()).orElse(0));
            orderItem.setPrice(Optional.ofNullable(item.getPrice()).orElse(BigDecimal.ZERO));
            orderItem.setUserPrice(Optional.ofNullable(item.getPriceUser()).orElse(BigDecimal.ZERO));
            // E率
            orderItem.setERate(CommonFormulaUtil.calcDiscount(CommonFormulaUtil.calcNoTaxPrice(orderItem.getPrice(), item.getTaxRate()), item.getEPrice()));
            // 用户E率
            orderItem.setUserERate(CommonFormulaUtil.calcDiscount(CommonFormulaUtil.calcNoTaxPrice(orderItem.getUserPrice(), item.getTaxRate()), item.getEPrice()));
            orderItem.setEndUserPrice(Optional.ofNullable(item.getPriceEndUser()).orElse(BigDecimal.ZERO));
            orderItem.setSpecOfferNo(item.getSpecOfferNo());
            orderItem.setPreSaleOrderNo(item.getSalesInfoNo());
            orderItem.setShikomi(item.getShikomiNo());
            orderItem.setExpInvCode(item.getStockCode());
            orderItem.setRemark(item.getRemark());
            orderItem.setShipDate(item.getShipTime());
            orderItem.setOrderTypeName(OrderTypeEnum.getCodeName(String.valueOf(item.getOrderType())));


            // 已出库才有运单号,承运商
            if (item.getStatus() == 7 && StringUtils.isNotBlank(item.getCarrierId())) {
                // 运单号
                orderItem.setTrackOrderNo(item.getExpressNo());
                // 承运商名称
                orderItem.setCarrier(opsCommonService.getCarrierNameByCode(item.getCarrierId()));
                // 承运商编码
                orderItem.setCarrierNo(item.getCarrierId());
            }
            // 发票号
            // 开票日期
            orderItem.setBillingDate(item.getInvoiceTime());
            orderItem.setProdFlag(item.getProdFlag()); // 拆分标识

            orderItem.setRohs10(OrderSpecExpType.include(item.getExpDlvType(), OrderSpecExpType.ROSH10Product) ? "rohs10" : "");
            if (OrderSpecExpType.include(item.getExpDlvType(), OrderSpecExpType.AssChildToExport)) {
                orderItem.setAssemble("子项特发");
            } else if (OrderSpecExpType.include(item.getExpDlvType(), OrderSpecExpType.OneItemToAssemble)) {
                orderItem.setAssemble("组装指令");
            } else {
                orderItem.setAssemble("");
            }
            orderItem.setSpecialNormal(OrderSpecExpType.include(item.getExpDlvType(), OrderSpecExpType.ApplySpecExport) ? "特发" : "普通");

            // 是否可删单
            orderItem.setCanDelete(canDelete(item.getStatus(),item.getExpQty()));
            // 是否可以更改货期标识
            orderItem.setCanChangeDeliveryDate(canChangeDlvDate(item.getStatus()));
            Boolean aBoolean = canDelivery(item.getStatus());
            // 是否装箱
            orderItem.setPackingFlag(aBoolean);
            // 是否发货
            orderItem.setDeliveryFlag(aBoolean);
            // 是否出组装波次
            orderItem.setChubociFlag(aBoolean);

            orderItem.setDeliveryAddressInfo(addressInfo);
            if (addressInfo != null && StringUtils.isNotBlank(addressInfo.getOptCarrier())) {
                orderItem.setOptCarrier(addressInfo.getOptCarrier());
            }
            if (StringUtils.isNotBlank(item.getAddressNo()) && isNum(item.getAddressNo().trim())) {
                OrderDlvDataDO orderDlvDataDO = orderDlvDataMapper.selectById(item.getAddressNo());
                orderItem.setDeliveryAddressInfo(setDeliveryAddressInfo(BeanCopyUtil.copy(orderDlvDataDO, OrderDlvDataVO.class)));
            }
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    public static boolean isNum(String str) {

        Pattern pattern = Pattern.compile("^-?[0-9]+");
        if (pattern.matcher(str).matches()) {
            //数字
            return true;
        } else {
            //非数字
            return false;
        }
    }

    public DeliveryAddressInfo setDeliveryAddressInfo(OrderDlvDataVO orderDlvInfo) {
        if (orderDlvInfo == null) {
            return new DeliveryAddressInfo();
        }
        DeliveryAddressInfo deliveryAddressInfo = new DeliveryAddressInfo();
        // 收货地址
        deliveryAddressInfo.setDlvAddress(orderDlvInfo.getDlvAddress());
        // 联系人
        deliveryAddressInfo.setContactPerson(orderDlvInfo.getContactPsn());
        // 联系电话
        deliveryAddressInfo.setContactPhone(orderDlvInfo.getTelNo());
        // 客户名称
        deliveryAddressInfo.setCustomerName(orderDlvInfo.getCstmName());
        // 发货方式
        deliveryAddressInfo.setDlvFlag(orderDlvInfo.getDlvFlag());
        // 地址id
        deliveryAddressInfo.setAddressId(String.valueOf(orderDlvInfo.getId()));
        deliveryAddressInfo.setEmail(orderDlvInfo.getEmail());
        // 省.市.区.邮编
        deliveryAddressInfo.setProvince(orderDlvInfo.getProvince());
        deliveryAddressInfo.setCity(orderDlvInfo.getCity());
        deliveryAddressInfo.setRegion(orderDlvInfo.getDistrict());
        deliveryAddressInfo.setPostCode(StringUtils.isBlank(orderDlvInfo.getPostCode()) ? "" : orderDlvInfo.getPostCode().trim());
        deliveryAddressInfo.setPersonId(orderDlvInfo.getIDCard());
        deliveryAddressInfo.setOptCarrier(opsCommonService.getCarrierNameByCode(orderDlvInfo.getCarrierId()));
        return deliveryAddressInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> updateOrderDeliveryInfo(OrderDeliveryModifyInfo info) {
        if (CollectionUtils.isEmpty(info.getOrderNoList())) {
            return ResultVo.failure("更新订单号为空");
        }
        if (info.getRcvMasterVO() != null) {
            RcvMasterDO rcvMasterDO = BeanCopyUtil.copy(info.getRcvMasterVO(), RcvMasterDO.class);
            LambdaUpdateWrapper<RcvMasterDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.in(RcvMasterDO::getRorderNo, info.getOrderNoList());
            rcvmasterMapper.update(rcvMasterDO, updateWrapper);
        }
        if (info.getOrderDlvDataVO() != null) {
            OrderDlvDataDO dlvData = BeanCopyUtil.copy(info.getOrderDlvDataVO(), OrderDlvDataDO.class);
            LambdaUpdateWrapper<OrderDlvDataDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.in(OrderDlvDataDO::getOrderNo, info.getOrderNoList());
            orderDlvDataMapper.update(dlvData, updateWrapper);
        }
        return ResultVo.success("修改完成");
    }

    @Override
    public ResultVo<Integer> updateOrderDetail(RcvDetailVO rcvDetailVO) {

        return ResultVo.failure("不可以调用");
        /*if (rcvDetailVO == null) {
            return ResultVo.failure("订单修改数据不能为空");
        }
        int result = 0;
        RcvDetailDO rcvDetailDO = BeanCopyUtil.copy(rcvDetailVO, RcvDetailDO.class);
        LambdaQueryWrapper<RcvDetailDO> updateWrapper;
        if (rcvDetailVO.getRorderNo().length() == 7) {
            updateWrapper = new LambdaQueryWrapper<>();
            updateWrapper.eq(RcvDetailDO::getRorderNo, rcvDetailVO.getRorderNo());
            result = rcvdetailMapper.update(rcvDetailDO, updateWrapper);
        } else if (rcvDetailVO.getRorderNo().length() == 10) {
            updateWrapper = new LambdaQueryWrapper<>();
            updateWrapper.eq(RcvDetailDO::getRorderNo, rcvDetailVO.getRorderNo().substring(0, 7));
            updateWrapper.eq(RcvDetailDO::getRorderItem, rcvDetailVO.getRorderNo().substring(7));
            result = rcvdetailMapper.update(rcvDetailDO, updateWrapper);
        }
        return ResultVo.success(result);*/
    }

    @Override
    public ResultVo<List<OrderItemStatusVO>> findItemsListStatus(List<String> orderNoList) {
        if (CollectionUtils.isEmpty(orderNoList)) {
            return ResultVo.failure("参数不可为空");
        }

        LambdaQueryWrapper<RcvDetailDO> query = Wrappers.lambdaQuery();
        query.select(RcvDetailDO::getRorderNo,RcvDetailDO::getRorderItem, RcvDetailDO::getRorderFno, RcvDetailDO::getStatus, RcvDetailDO::getStockCode,RcvDetailDO::getExpQty,RcvDetailDO::getProdFlag);
        query.in(RcvDetailDO::getRorderFno, orderNoList);
        List<RcvDetailDO> rcvDetailDOList = rcvdetailMapper.selectList(query);
        if (CollectionUtils.isEmpty(rcvDetailDOList)) {
            return ResultVo.success(Collections.emptyList());
        }

        List<OrderItemStatusVO> itemList = new ArrayList<>(rcvDetailDOList.size());
        OrderItemStatusVO orderItem;

        for (RcvDetailDO rcvDetailDO : rcvDetailDOList) {
            orderItem = new OrderItemStatusVO();
            orderItem.setMainOrderNo(rcvDetailDO.getRorderNo());
            orderItem.setOrderNo(rcvDetailDO.getRorderFno());
            orderItem.setOrderStatus(RCVOrderStatusEnum.getName(rcvDetailDO.getStatus()));
            orderItem.setOrderStatusCode(rcvDetailDO.getStatus().toString());
            orderItem.setExpInvCode(rcvDetailDO.getStockCode());

            // 是否可删单
            orderItem.setCanDelete(canDelete(rcvDetailDO.getStatus(),rcvDetailDO.getExpQty()));

            Boolean bool = canChangeShipMethods(rcvDetailDO.getStatus());
            orderItem.setCanChangeDeliveryModel(bool);  // 是否可更改出货方式
            orderItem.setCanIntensiveNo(bool); // 是否可变更集约方式

            // 是否可更改货期
            orderItem.setCanChangeDeliveryDate(canChangeDlvDate(rcvDetailDO.getStatus()));

            // 是否可变更子项特发
            if (rcvDetailDO.getStatus() != null && StringUtils.isNotBlank(rcvDetailDO.getProdFlag())) {
                if (canUpSepc(rcvDetailDO.getStatus(),Integer.parseInt(rcvDetailDO.getProdFlag()))) {
                    orderItem.setCanUpSpec(true);
                } else {
                    orderItem.setCanUpSpec(false);
                }
            } else {
                orderItem.setCanUpSpec(false);
            }

            Boolean aBoolean = canDelivery(rcvDetailDO.getStatus());
            // 是否装箱
            orderItem.setPackingFlag(aBoolean);
            // 是否组装波次
            orderItem.setChubociFlag(aBoolean);
            // 最晚货期
            CommonResult modifyDlvDateLimit = opsOrderFeignApi.getModifyDlvDateLimit(rcvDetailDO.getRorderNo(), rcvDetailDO.getRorderItem());
            if (modifyDlvDateLimit !=null && modifyDlvDateLimit.getData() != null) {
                orderItem.setLatestDeliveryDate((Date) modifyDlvDateLimit.getData());
            }

            itemList.add(orderItem);
        }
        return ResultVo.success(itemList);
    }


    /**
     * 是否可变更货期
     * @param status
     * @return
     */
    public Boolean canChangeDlvDate(Integer status) {
        if (status == null) {
            return false;
        }
        // 出库处理,已发货,已退货,已开票,已删单 ,异常订单,暂不处理,接单未处理   [是否可变更交货期]
        List<Integer> canNotChangeDeliveryDate = Arrays.asList((int) RcvOrderStatusEnum.CKING.getType(),
                (int) RcvOrderStatusEnum.CKED.getType(), (int) RcvOrderStatusEnum.RETURN.getType(),
                (int) RcvOrderStatusEnum.INVOICE.getType(),(int) RcvOrderStatusEnum.CANCEL.getType(),
                (int) RcvOrderStatusEnum.EXCEPT.getType(),(int) RcvOrderStatusEnum.UNDEAL.getType(),
                (int) RcvOrderStatusEnum.INIT.getType());

        if (canNotChangeDeliveryDate.contains(status)) {
           return false;
        } else {
           return true;
        }
    }

    /**
     * 是否可删单
     */
    public Boolean canDelete(Integer status,Integer expQty) {
        if (status == null) {
            return false;
        }
//        // 出库处理,已发货,已退货,已开票,已删单 ,异常订单,暂不处理  [是否可删单]
//        List<Integer> canNotDelivery = Arrays.asList((int) RcvOrderStatusEnum.CKING.getType(),
//                (int) RcvOrderStatusEnum.CKED.getType(), (int) RcvOrderStatusEnum.RETURN.getType(),
//                (int) RcvOrderStatusEnum.INVOICE.getType(),(int) RcvOrderStatusEnum.CANCEL.getType(),
//                (int) RcvOrderStatusEnum.EXCEPT.getType(),(int) RcvOrderStatusEnum.UNDEAL.getType());

        // 2 3 4 5 6 12 14
        List<String> canDelOrderStatus = opsCommonService.canDelOrderStatus();
        // 是否可删单
        if (canDelOrderStatus.contains(status.toString())) {
            if (expQty != null && expQty != 0 && status == (int) RcvOrderStatusEnum.CKING.getType()) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * update by LiYingChao from bug
     * 是否变更出货方式
     * @param status
     * @return
     */
    public Boolean canChangeShipMethods(Integer status) {

        if (status == null) {
            return false;
        }

        List<Integer> canNotUpDateShipMethod = getNoCanSendOrderMethod();

        // 是否可更改出货方式
        if (canNotUpDateShipMethod.contains(status)) {
           return false;
        } else {
           return true;
        }

    }


    public Boolean canUpSepc(int status,int prodFlag) {
        List<Integer> canUpSpec = Arrays.asList((int) RcvOrderStatusEnum.CGING.getType(),
                (int) RcvOrderStatusEnum.INTCP.getType(), (int) RcvOrderStatusEnum.DBING.getType(),
                (int) RcvOrderStatusEnum.WAITCK.getType());

        if (canUpSpec.contains(status) && prodFlag == 2) {
            return true;
        }
        return false;
    }

    @Override
    public List<SalesInvoice> invoiceList(List<String> orderNoList) {
        if (CollectionUtils.isEmpty(orderNoList)) {
            return null;
        }

        List<SalesInvoice> list = new ArrayList<>(orderNoList.size());
        SalesInvoice invoice;
        List<SalesInvoiceDO> invoiceDOList = salesInvoiceMapper.selectInvoiceList(orderNoList);

        for (SalesInvoiceDO invoiceDO : invoiceDOList) {
            invoice = new SalesInvoice();
            invoice.setId(String.valueOf(invoiceDO.getId()));
            invoice.setTradecompanyid(invoiceDO.getTradeCompanyId());
            invoice.setRorderno(invoiceDO.getROrderNo());
            invoice.setModelno(invoiceDO.getModelNo());
            invoice.setQuantity(invoiceDO.getQuantity());
            invoice.setPrice(invoiceDO.getPrice());
            invoice.setCustomerno(invoiceDO.getCustomerNo());
            invoice.setUserno(invoiceDO.getUserNo());
            invoice.setDlventire(invoiceDO.getDlvEntire());
            invoice.setAmount(invoiceDO.getAmount());
            invoice.setTaxamount(invoiceDO.getTaxAmount());
            invoice.setNtaxamount(invoiceDO.getNTaxAmount());
//            invoice.setInvflag(invoiceDO.getInvFlag());
            invoice.setOptdate(invoiceDO.getOptDate());
            invoice.setOptcode(invoiceDO.getOptCode());
//            invoice.setClassflag(invoiceDO.getClassFlag());
            invoice.setInvoiceno(invoiceDO.getInvoiceNo());
            invoice.setInvdate(invoiceDO.getInvDate());
            invoice.setOptstate(invoiceDO.getOptState());
            invoice.setStockno(invoiceDO.getStockNo());
            invoice.setInvflag1(invoiceDO.getInvFlag1());
            invoice.setProdflag(invoiceDO.getProdFlag());
            invoice.setBillno(invoiceDO.getBillNo());
            invoice.setUsername(invoiceDO.getUsername());
            invoice.setOpttime(invoiceDO.getOptTime());
            invoice.setReceiveEmp(invoiceDO.getReceive_Emp());
            invoice.setReceiveCust(invoiceDO.getReceive_Cust());
            invoice.setReceiveDate(invoiceDO.getReceive_date());
            invoice.setReceiveRemark(invoiceDO.getReceive_Remark());
            invoice.setReceiveOptstate(invoiceDO.getReceive_OptState());
            invoice.setPurchaseno(invoiceDO.getPurchaseNo());
            invoice.setAgentpriceFlag(invoiceDO.getAgentPrice_Flag());
            invoice.setDeptno(invoiceDO.getDeptNo());
            invoice.setDiscountamt(invoiceDO.getDiscountAmt());
            invoice.setOrdtype(invoiceDO.getOrdType());
            invoice.setInvoicecode(invoiceDO.getInvoiceCode());
//            invoice.setInvtype(invoiceDO.getInvType());
            invoice.setInvoicenoJp(invoiceDO.getInvoiceNoJp());
            invoice.setTaxrate(invoiceDO.getTaxRate());
            invoice.setNtaxdiscountamt(invoiceDO.getNTaxDiscountAmt());
            invoice.setTaxdiscountamt(invoiceDO.getTaxDiscountAmt());
            invoice.setInserttime(invoiceDO.getInsertTime());
            invoice.setCanceldate(invoiceDO.getCancelDate());
            invoice.setExtracted(invoiceDO.getExtracted());
            invoice.setExtracttime(invoiceDO.getExtractTime());
            invoice.setInvoicegroupkey(invoiceDO.getInvoiceGroupKey());
            if ("0".equals(invoiceDO.getInvType())) {
                invoice.setInvtype("增票");
            }
            if ("2".equals(invoiceDO.getInvType())) {
                invoice.setInvtype("普票");
            }
            if ("0".equals(invoiceDO.getClassFlag())) {
                invoice.setClassflag("正常");
            }
            if ("1".equals(invoiceDO.getClassFlag())) {
                invoice.setClassflag("合开");
            }
            invoice.setInvflag(SalesInvoiceFlagEnum.getName(invoiceDO.getInvFlag()));

            list.add(invoice);
        }

        return list;
    }

    @Override
    public List<OrderDeliveryInfo> findDeliveryInfo(List<String> orderNoList) {
        if (CollectionUtils.isEmpty(orderNoList)) {
            return null;
        }
        List<OrderDeliveryInfo> infoList = new ArrayList<>(orderNoList.size());
        OrderDeliveryInfo info;
        List<ExpdetailDO> detailList = expdetailMapper.selectExpDetailList(orderNoList);

        for (ExpdetailDO expdetailDO : detailList) {
            info = new OrderDeliveryInfo();
            info.setOrderNo(expdetailDO.getOrderFno());
            info.setDeliveryNo(expdetailDO.getDeliveryNo());
            info.setModelNo(expdetailDO.getModelNo());
            info.setQuantity(expdetailDO.getQuantity());
            info.setPrice(expdetailDO.getPrice());
            info.setDlvDate(expdetailDO.getDlvDate());
            info.setWarehouseSendDate(expdetailDO.getShipDate());
            info.setExpInvCode(expdetailDO.getStockCode());
            info.setDeliveryDate(expdetailDO.getShipDate());
//                info.setDeliveryStatus();
            info.setExpressNo(expdetailDO.getExpressNo());
            info.setExpressCompanyNo(expdetailDO.getExpressCompany());
            info.setExpressCompanyName(CarrierEnum.getNameByCode(info.getExpressCompanyNo()));

            infoList.add(info);
        }

        return infoList;
    }

    @Override
    public List<OrderItemData> getItemList(List<String> orderNoList, boolean calCanPress) {
        if (CollectionUtils.isEmpty(orderNoList)) {
            return null;
        }

        LambdaQueryWrapper<OrderStateDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(OrderStateDO::getOrderNo, orderNoList);
        List<OrderStateDO> stateList = orderStateMapper.selectList(queryWrapper);

        List<OrderItemData> list = new ArrayList<>(stateList.size());
        String orderNo;
        OrderItemData itemData;
        Map<String, String> customerNameMap = new HashMap<>(stateList.size());
        String name;
        List<Integer> canPressStateList = Arrays.asList(20, 21, 22, 25);

        if (CollectionUtils.isEmpty(stateList)) {
            for (String order : orderNoList) {
                itemData = new OrderItemData();
                itemData.setOrderNo(order);
                itemData.setCanPress(false);
                itemData.setPressMsg("未查询到采购数据");
                list.add(itemData);
            }

            return list;
        }

        for (OrderStateDO stateDO : stateList) {
            orderNo = stateDO.getOrderNo();
            itemData = new OrderItemData();
            if (orderNo.contains("-")) {
                String[] split = orderNo.split("-");
                itemData.setMainOrderNo(split[0]);
                itemData.setItemNo(split[1]);
            } else {
                itemData.setMainOrderNo(orderNo.substring(0, orderNo.length() - 3));
                itemData.setItemNo(orderNo.substring(orderNo.length() - 3));
            }
            itemData.setOrderNo(stateDO.getOrderNo());
            itemData.setCustomerNo(stateDO.getCustomerNo());
            if (PublicUtil.isNotEmpty(itemData.getCustomerNo())) {
                if (!customerNameMap.containsKey(itemData.getCustomerNo())) {
                    name = commonService.getCustomerNameByNo(itemData.getCustomerNo());
                    customerNameMap.put(itemData.getCustomerNo(), name);
                }
                itemData.setCustomerName(customerNameMap.getOrDefault(itemData.getCustomerNo(), ""));
            }
            itemData.setDlvDate(stateDO.getCustDlvDate());
            itemData.setModelNo(stateDO.getModelNo());
            itemData.setQuantity(stateDO.getQuantity());
            itemData.setOrderStatus(stateDO.getStateDes());
            itemData.setOrderStatusCode(String.valueOf(stateDO.getStateCode()));
            itemData.setCorderNo(stateDO.getCorderNo());

            if (PublicUtil.isNotEmpty(stateDO.getSupplierCode()) &&
                    com.smc.smccloud.model.Constants.BJ_SUPPLIER_CODES.contains(stateDO.getSupplierCode())
                    && PublicUtil.isNotEmpty(stateDO.getSupplierRcvTime())) {
                if (canPressStateList.contains(stateDO.getStateCode())) {
                    itemData.setCanPress(true);
                } else {
                    itemData.setCanPress(false);
                    itemData.setPressMsg("非采购中:" + OrderStateStatusEnum.getName(stateDO.getStateCode().toString()) + "状态");
                }
            } else {
                itemData.setCanPress(false);
                itemData.setPressMsg("非制造单未接单不可以催");
            }

            itemData.setShikomi(stateDO.getShikomiNo());
            // itemData.setWarehouseSendDate(stateDO.getWmsDlvDate());
            itemData.setWarehouseSendDate(stateDO.getCustShipDate());
            itemData.setInvoiceNo(stateDO.getPoInvoiceNo());
            itemData.setOrdTransType(stateDO.getTransType());
            itemData.setUserNo(stateDO.getUserNo());
            //查询发货信息
            /*List<String> deList = new ArrayList<>(1);
            deList.add(orderNo);
            List<OrderDeliveryInfo> info = findDeliveryInfo(deList);
            if (PublicUtil.isNotEmpty(info)) {
                itemData.setOrderDeliveryInfo(info.get(0));
            }*/

            list.add(itemData);
        }
        return list;
    }

    @Override
    public ResultVo<List<PurchaseOrderBean>> exportListOrderState(PurchaseOrderCondition request) {
        log.info("查询交货期状态导出处理前参数 : " + JSONObject.toJSONString(request));

        long lstart = System.currentTimeMillis();

        if (StringUtils.isNotBlank(request.getOrderNo())) {
            request.setOrderNo(request.getOrderNo().trim());
        }

        if (StringUtils.isNotBlank(request.getOperator())) {
            if (request.getOperator().startsWith("ACC") || request.getOperator().startsWith("acc")) {
                return exportListOrderStateForAgent(request);
            }
        }

        if (StringUtils.isNotBlank(request.getInvoiceNo())) {
            request.setInvoiceNo(request.getInvoiceNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getPurchaseNo())) {
            request.setPurchaseNo(request.getPurchaseNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getSupplierOrderNo())) {
            request.setSupplierOrderNo(request.getSupplierOrderNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getOrderNo())) {
            if (!request.getExactSearchFlag()) {
                request.setOrderNo(request.getOrderNo() + "%");
            }
        } else {
            request.setOrderNo("");
        }
        if (StringUtils.isNotBlank(request.getModelNo())) {
            request.setModelNo(request.getModelNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getCustomerNo())) {
            request.setCustomerNo(request.getCustomerNo() + "%");
        }
        // 排序字段转换
        if (StringUtils.isNotBlank(request.getProperty())) {
            request.setProperty(getSortFieldForOrderStateWithCustomer2(request.getProperty(), request.getOrder()));
        }
        // 供应商分类
        if (StringUtils.isNotBlank(request.getPurchaseType()) && StringUtils.isNotBlank(request.getSupplier())) {
            return ResultVo.failure("选择了供货地,供应商分类可不选.");
        }
        if (request.getPurchaseType() != null) {
            if (request.getPurchaseType().equals("制造")) {
                request.setChinaSupplierList(ChinaSuppplierEnum.getAllCNSupplier());
            } else if (request.getPurchaseType().equals("日本及海外")) {
                request.setNotChinasupplierList(ChinaSuppplierEnum.getAllCNSupplier());
            }
        }
        // 判断权限
        List<String> userAuth = new ArrayList<>();
        List<String> deptAuth = new ArrayList<>();
        List<String> customerAuth = new ArrayList<>();
        List<String> employeeAuth = new ArrayList<>();
        List<String> inCodeAuth = new ArrayList<>();
        if (request.getDataAuthoritySearchCondition() != null) {
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getUserNoListByDataAuthority())) {
                userAuth = request.getDataAuthoritySearchCondition().getUserNoListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
                deptAuth = request.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
                customerAuth = request.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getUserIdListByDataAuthority())) {
                employeeAuth = request.getDataAuthoritySearchCondition().getUserIdListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority())) {
                inCodeAuth = request.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority();
            }
        }

        List<String> finalUserAuth = userAuth;
        List<String> finalDeptAuth = deptAuth;
        List<String> finalCustomerAuth = customerAuth;
        List<String> finalEmployeeAuth = employeeAuth;
        List<String> finalInCodeAuth = inCodeAuth;

        List<PurchaseOrderBean> list;
        try {
            list = orderStateMapperReadOnlyMapper.exportListOrderStateWithCustomer(request, finalDeptAuth, finalUserAuth, finalCustomerAuth, finalInCodeAuth,finalEmployeeAuth);
        } catch (Exception e) {
            if (e.getMessage().substring(25,100).contains("Timeout")) {
                return ResultVo.failure("查询超时");
            } else {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.success(list);
        }
        long lend = System.currentTimeMillis();
        log.info("交货期导出接口 SQL执行消耗时间: " + (lend - lstart) + "(ms) " + (lend - lstart) / 1000 + "(s)");
        list = commonOrderStateDataHand(list);
//        Map<String, String> nameMap = new HashMap<>(list.size());
//        String name;
//        for (PurchaseOrderBean item : list) {
//            // 客户名称
//            if (StringUtils.isNotBlank(item.getCustomerNo()) && !nameMap.containsKey(item.getCustomerNo())) {
//                name = commonService.getCustomerNameByNo(item.getCustomerNo());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getCustomerNo(), name);
//                }
//            }
//            item.setCustomerName(nameMap.get(item.getCustomerNo()));
//            // 用户名称
//            if (StringUtils.isNotBlank(item.getUserNo()) && !nameMap.containsKey(item.getUserNo())) {
//                name = commonService.getCustomerNameByNo(item.getUserNo());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getUserNo(), name);
//                }
//            }
//            item.setUserName(nameMap.get(item.getUserNo()));
//
//            // 最终用户
//            if (StringUtils.isNotBlank(item.getEndUser()) && !nameMap.containsKey(item.getEndUser())) {
//                name = commonService.getCustomerNameByNo(item.getEndUser());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getEndUser(), name);
//                }
//            }
//            item.setEndUserName(nameMap.get(item.getEndUser()));
//
//            item.setOrderTypeName(OrderTypeEnum.getCodeName(item.getOrderType()));
//
//            if (item.getIntercept() != null && item.getIntercept() && !isSendOrderStatusForOrderState(Integer.valueOf(item.getStateCode()))) {
//                item.setStateCodeName("信用拦截");
//                item.setStateDes("信用拦截");
//            } else {
//                if (StringUtils.isNotBlank(item.getStateCode())) {
//                    item.setStateCodeName(OrderStateEnum.getStateNameByCode(Integer.parseInt(item.getStateCode())));
//                }
//            }
//
//            if (StringUtils.isBlank(item.getSupplierCode())) {
//                item.setSupplierCode(item.getSupplierNo());
//            }
//
//            // 供应商名称
//            if (StringUtils.isNotBlank(item.getSupplierCode()) && !nameMap.containsKey(item.getSupplierCode())) {
//                name = commonService.getSupplierNameByCode(item.getSupplierCode());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getSupplierCode(), name);
//                }
//            }
//            item.setSupplierCodeName(nameMap.get(item.getSupplierCode()));
//            // 运输方式名称
//            item.setPoTransTypeName(OPSTransTypeEnum.getNameByCode(item.getTransType()));
//            // 采购类型名称
//            item.setPurchaseTypeName(PurchaseTypeEnum.getName(item.getPurchaseType()));
//            // 仓库名称
//            if (StringUtils.isNotBlank(item.getWarehouseCode()) && !nameMap.containsKey(item.getWarehouseCode())) {
//                name = commonService.getWarehouseNameByCode(item.getWarehouseCode());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getWarehouseCode(), name);
//                }
//            }
//            item.setWarehouseCodeName(nameMap.get(item.getWarehouseCode()));
//
//            // hl部门名称
//            if (StringUtils.isNotBlank(item.getHlCode())) {
//                if (!nameMap.containsKey(item.getHlCode())) {
//                    name = commonService.getDeptNameByNo(item.getHlCode());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getHlCode(), name);
//                    }
//                }
//                item.setDeptNo(item.getHlCode());
//                item.setDeptName(nameMap.get(item.getHlCode()));
//            } else {
//                if (!nameMap.containsKey(item.getHrUnitId())) {
//                    name = commonService.getDeptNameByNo(item.getHrUnitId());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getHrUnitId(), name);
//                    }
//                }
//                item.setDeptNo(item.getHrUnitId());
//                item.setDeptName(nameMap.get(item.getHrUnitId()));
//            }
//            // add by LiYingChao from bug 9002/8993 in 2022/12/12
//            // 订单所属部门
//            if (StringUtils.isNotBlank(item.getOrderDeptNo())) {
//                if (!nameMap.containsKey(item.getOrderDeptNo())) {
//                    String deptNameByNo = commonService.getDeptNameByNo(item.getOrderDeptNo());
//                    if (StringUtils.isNotBlank(deptNameByNo)) {
//                        nameMap.put(item.getOrderDeptNo(),deptNameByNo);
//                    }
//                }
//                item.setOrderDeptName(nameMap.get(item.getOrderDeptNo()));
//            }
//        }
        long endTime = System.currentTimeMillis();
        log.info("交货期导出接口 查询结束消耗时间: " + (endTime - lstart) + "(ms) " + (endTime - lstart) / 1000 + "(s)");
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<List<PurchaseOrderBean>> exportListOrderStateForAgent(PurchaseOrderCondition request) {
        log.info("查询交货期状态导出处理前参数 : " + JSONObject.toJSONString(request));

        long lstart = System.currentTimeMillis();

        if (StringUtils.isNotBlank(request.getOrderNo())) {
            request.setOrderNo(request.getOrderNo().trim());
        }

        if (CollectionUtils.isEmpty(request.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
            return ResultVo.success(new ArrayList<>());
        }

        if (StringUtils.isNotBlank(request.getInvoiceNo())) {
            request.setInvoiceNo(request.getInvoiceNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getPurchaseNo())) {
            request.setPurchaseNo(request.getPurchaseNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getSupplierOrderNo())) {
            request.setSupplierOrderNo(request.getSupplierOrderNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getOrderNo())) {
            if (!request.getExactSearchFlag()) {
                request.setOrderNo(request.getOrderNo() + "%");
            }
        } else {
            request.setOrderNo("");
        }
        if (StringUtils.isNotBlank(request.getModelNo())) {
            request.setModelNo(request.getModelNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getCustomerNo())) {
            request.setCustomerNo(request.getCustomerNo() + "%");
        }
        // 排序字段转换
        if (StringUtils.isNotBlank(request.getProperty())) {
            request.setProperty(getSortFieldForOrderStateWithCustomer2(request.getProperty(), request.getOrder()));
        }
        // 供应商分类
        if (StringUtils.isNotBlank(request.getPurchaseType()) && StringUtils.isNotBlank(request.getSupplier())) {
            return ResultVo.failure("选择了供货地,供应商分类可不选.");
        }
        if (request.getPurchaseType() != null) {
            if (request.getPurchaseType().equals("制造")) {
                request.setChinaSupplierList(ChinaSuppplierEnum.getAllCNSupplier());
            } else if (request.getPurchaseType().equals("日本及海外")) {
                request.setNotChinasupplierList(ChinaSuppplierEnum.getAllCNSupplier());
            }
        }
        // 判断权限
        List<String> userAuth = new ArrayList<>();
        List<String> deptAuth = new ArrayList<>();
        List<String> customerAuth = new ArrayList<>();
        List<String> employeeAuth = new ArrayList<>();
        List<String> inCodeAuth = new ArrayList<>();
        if (request.getDataAuthoritySearchCondition() != null) {
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getUserNoListByDataAuthority())) {
                userAuth = request.getDataAuthoritySearchCondition().getUserNoListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
                deptAuth = request.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
                customerAuth = request.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getUserIdListByDataAuthority())) {
                employeeAuth = request.getDataAuthoritySearchCondition().getUserIdListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority())) {
                inCodeAuth = request.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority();
            }
        }

        List<String> finalUserAuth = userAuth;
        List<String> finalDeptAuth = deptAuth;
        List<String> finalCustomerAuth = customerAuth;
        List<String> finalEmployeeAuth = employeeAuth;
        List<String> finalInCodeAuth = inCodeAuth;

        List<PurchaseOrderBean> list;
        try {
            list = orderStateMapperReadOnlyMapper.exportListOrderStateWithCustomerForAgent(request, finalDeptAuth, finalUserAuth, finalCustomerAuth, finalInCodeAuth);
        } catch (Exception e) {
            if (e.getMessage().substring(25,100).contains("Timeout")) {
                return ResultVo.failure("查询超时");
            } else {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.success(list);
        }
        long lend = System.currentTimeMillis();
        log.info("交货期导出接口 SQL执行消耗时间: " + (lend - lstart) + "(ms) " + (lend - lstart) / 1000 + "(s)");
        list = commonOrderStateDataHand(list);
//        Map<String, String> nameMap = new HashMap<>(list.size());
//        String name;
//        for (PurchaseOrderBean item : list) {
//            // 客户名称
//            if (StringUtils.isNotBlank(item.getCustomerNo()) && !nameMap.containsKey(item.getCustomerNo())) {
//                name = commonService.getCustomerNameByNo(item.getCustomerNo());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getCustomerNo(), name);
//                }
//            }
//            item.setCustomerName(nameMap.get(item.getCustomerNo()));
//            // 用户名称
//            if (StringUtils.isNotBlank(item.getUserNo()) && !nameMap.containsKey(item.getUserNo())) {
//                name = commonService.getCustomerNameByNo(item.getUserNo());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getUserNo(), name);
//                }
//            }
//            item.setUserName(nameMap.get(item.getUserNo()));
//
//            // 最终用户
//            if (StringUtils.isNotBlank(item.getEndUser()) && !nameMap.containsKey(item.getEndUser())) {
//                name = commonService.getCustomerNameByNo(item.getEndUser());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getEndUser(), name);
//                }
//            }
//            item.setEndUserName(nameMap.get(item.getEndUser()));
//
//            item.setOrderTypeName(OrderTypeEnum.getCodeName(item.getOrderType()));
//
//
//            if (item.getIntercept() != null && item.getIntercept() && !isSendOrderStatusForOrderState(Integer.valueOf(item.getStateCode())) ) {
//                item.setStateCodeName("信用拦截");
//                item.setStateDes("信用拦截");
//            } else {
//                if (StringUtils.isNotBlank(item.getStateCode())) {
//                    item.setStateCodeName(OrderStateEnum.getStateNameByCode(Integer.parseInt(item.getStateCode())));
//                }
//            }
//
//            // 供应商名称
//            if (StringUtils.isNotBlank(item.getSupplierCode()) && !nameMap.containsKey(item.getSupplierCode())) {
//                name = commonService.getSupplierNameByCode(item.getSupplierCode());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getSupplierCode(), name);
//                }
//            }
//            item.setSupplierCodeName(nameMap.get(item.getSupplierCode()));
//            // 运输方式名称
//            item.setPoTransTypeName(OPSTransTypeEnum.getNameByCode(item.getTransType()));
//            // 采购类型名称
//            item.setPurchaseTypeName(PurchaseTypeEnum.getName(item.getPurchaseType()));
//            // 仓库名称
//            if (StringUtils.isNotBlank(item.getWarehouseCode()) && !nameMap.containsKey(item.getWarehouseCode())) {
//                name = commonService.getWarehouseNameByCode(item.getWarehouseCode());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(item.getWarehouseCode(), name);
//                }
//            }
//            item.setWarehouseCodeName(nameMap.get(item.getWarehouseCode()));
//
//            // hl部门名称
//            if (StringUtils.isNotBlank(item.getHlCode())) {
//                if (!nameMap.containsKey(item.getHlCode())) {
//                    name = commonService.getDeptNameByNo(item.getHlCode());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getHlCode(), name);
//                    }
//                }
//                item.setDeptNo(item.getHlCode());
//                item.setDeptName(nameMap.get(item.getHlCode()));
//            } else {
//                if (!nameMap.containsKey(item.getHrUnitId())) {
//                    name = commonService.getDeptNameByNo(item.getHrUnitId());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getHrUnitId(), name);
//                    }
//                }
//                item.setDeptNo(item.getHrUnitId());
//                item.setDeptName(nameMap.get(item.getHrUnitId()));
//            }
//            // 订单所属部门
//            if (StringUtils.isNotBlank(item.getOrderDeptNo())) {
//                if (!nameMap.containsKey(item.getOrderDeptNo())) {
//                    String deptNameByNo = commonService.getDeptNameByNo(item.getOrderDeptNo());
//                    if (StringUtils.isNotBlank(deptNameByNo)) {
//                        nameMap.put(item.getOrderDeptNo(),deptNameByNo);
//                    }
//                }
//                item.setOrderDeptName(nameMap.get(item.getOrderDeptNo()));
//            }
//        }
        long endTime = System.currentTimeMillis();
        log.info("交货期查询接口 查询结束消耗时间: " + (endTime - lstart) + "(ms) " + (endTime - lstart) / 1000 + "(s)");
        return ResultVo.success(list);
    }

    /**
     * 批量变更交货期
     */
    @Override
    public ResultVo<Map<String,UpdateOrderInfoResultVO>> batchUpdateDlvDate(List<OrderDelivery> orderDeliveryDate) {

        if (CollectionUtils.isEmpty(orderDeliveryDate)) {
            return null;
        }

        log.info("门户接单查询->批量变更交货期(batchUpdateDlvDate): {}",JSONUtil.toJsonPrettyStr(orderDeliveryDate));

        UpdateOrderInfoResultVO resultVO;
        OpsOrderModifyDto opsOrderModifyDto;
        CommonResult commonResult;
        Orderdlvdata orderdlvdata = new Orderdlvdata();
        Map<String,UpdateOrderInfoResultVO> map = new HashMap<>(orderDeliveryDate.size());
        for (OrderDelivery item : orderDeliveryDate) {

            OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo());
            opsOrderModifyDto = new OpsOrderModifyDto();
            opsOrderModifyDto.setOrderId(orderNoInfo.getOrderNo());
            opsOrderModifyDto.setOrderItem(orderNoInfo.getItemNo());

            /**
             * 批量变更
             */
            if (StringUtils.isBlank(item.getAssemble()) && StringUtils.isBlank(item.getOptCarrier())
                    && StringUtils.isBlank(item.getSpecialNormal()) && item.getDeliveryAddressInfo() == null
                    && StringUtils.isBlank(item.getDeptNo())) {
                opsOrderModifyDto.setMaster(false);
                if (item.getWarehouseSendDate() != null) {
                    // 是否修改货期
                    opsOrderModifyDto.setUpdateDate(true);
                    // 设置客户货期
                    if (item.getCustomerChangeDate() == null) {
                        opsOrderModifyDto.setDlvDate(item.getDlvDate());
                    } else {
                        opsOrderModifyDto.setDlvDate(item.getCustomerChangeDate());
                    }
                    // 设置物流货期
                    opsOrderModifyDto.setWmsDlvDate(item.getWarehouseSendDate());
                }
                opsOrderModifyDto.setUpdateAddress(false);
                UserDto userDto = new UserDto();
                userDto.setUserName(item.getLoginUserId());
                opsOrderModifyDto.setUserDto(userDto);
                opsOrderModifyDto.setReason("门户申请变更");
                log.info("订单子单修改(批量) 参数 = {}", JSONObject.toJSONString(opsOrderModifyDto));
                commonResult = opsOrderFeignApi.modifyRcvOrder(opsOrderModifyDto);

            }
            else
                {
                // 是否修改主项
                opsOrderModifyDto.setMaster(Boolean.FALSE);
                if (item.getCustomerChangeDate() != null || item.getWarehouseSendDate() != null) {
                    // 是否修改货期
                    opsOrderModifyDto.setUpdateDate(true);
                    // 设置客户货期
                    if (item.getCustomerChangeDate() == null) {
                        opsOrderModifyDto.setDlvDate(item.getDlvDate());
                    } else {
                        opsOrderModifyDto.setDlvDate(item.getCustomerChangeDate());
                    }
                    // 设置物流货期
                    opsOrderModifyDto.setWmsDlvDate(item.getWarehouseSendDate());
                }
                // 设置承运商
                opsOrderModifyDto.setCarrier(opsCommonService.getCarrierCodeByName(item.getOptCarrier()));
                // 是否特发
                if ("普通".equals(item.getSpecialNormal())) {
                    opsOrderModifyDto.setDlvSpecial(false);
                } else if ("特发".equals(item.getSpecialNormal())) {
                    opsOrderModifyDto.setDlvSpecial(true);
                }

                // 是否拆分子项发货
                if ("子项特发".equals(item.getAssemble())) {
                    opsOrderModifyDto.setDlvSplit(Boolean.TRUE);
                } else {
                    opsOrderModifyDto.setDlvSplit(Boolean.FALSE);
                }
                // 变更发货地址
                DeliveryAddressInfo addressInfo = item.getDeliveryAddressInfo();
                if (null == addressInfo) {
                    opsOrderModifyDto.setUpdateAddress(false);
                } else {
                    Class<? extends DeliveryAddressInfo> aClass = addressInfo.getClass();
                    Field[] fields = aClass.getDeclaredFields();
                    try {
                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (null != field.get(addressInfo) && StringUtils.isBlank(field.get(addressInfo).toString())) {
                                field.set(addressInfo, null);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if(StringUtils.isBlank(addressInfo.getDlvFlag())){
                        resultVO = new UpdateOrderInfoResultVO();
                        resultVO.setSuccess(false);
                        resultVO.setCode("500");
                        resultVO.setMessage("发货方式不能为空");
                        log.info("修改地址[batchUpdateAddress]{},{}", orderNoInfo.getFullOrderNo(), JSON.toJSONString(resultVO));
                        map.put(orderNoInfo.getFullOrderNo(), resultVO);
                        continue;
                    }
                    // 是否修改地址
                    opsOrderModifyDto.setUpdateAddress(true);
                    orderdlvdata.setOrderno(orderNoInfo.getOrderNo());
                    orderdlvdata.setItemno(orderNoInfo.getItemNo());
                    // 收货地址
                    orderdlvdata.setDlvaddress(addressInfo.getDlvAddress());
                    // 联系人
                    orderdlvdata.setContactpsn(addressInfo.getContactPerson());
                    // 联系电话
                    orderdlvdata.setTelno(addressInfo.getContactPhone());
                    // 省.市.区
                    orderdlvdata.setProvince(addressInfo.getProvince());
                    orderdlvdata.setCity(addressInfo.getCity());
                    orderdlvdata.setDistrict(addressInfo.getRegion());
                    // 身份证号
                    orderdlvdata.setIdcard(addressInfo.getPersonId());
                    // 客户名称
                    orderdlvdata.setCstmname(addressInfo.getCustomerName());
                    orderdlvdata.setPostcode(addressInfo.getPostCode());
                    // 发货方式  发货方式-直发客户/直发营业所（1：直发客户；2：直发营业所;3:自提)
                    orderdlvdata.setDlvflag(addressInfo.getDlvFlag());
                    opsOrderModifyDto.setAddress(orderdlvdata);
                }
                // 操作人
                UserDto userDto = new UserDto();
                userDto.setUserName(item.getLoginUserId());
                opsOrderModifyDto.setUserDto(userDto);
                // 变更原因
                opsOrderModifyDto.setReason("门户申请变更");
                log.info("订单子单修改 参数 = {}", JSONObject.toJSONString(opsOrderModifyDto));
                commonResult = opsOrderFeignApi.modifyRcvOrder(opsOrderModifyDto);
            }

            log.info("订单子单修改 结果 = {}", JSON.toJSONString(commonResult));
            if (commonResult == null) {
                return null;
            }
            resultVO = new UpdateOrderInfoResultVO();
            if ("200".equals(String.valueOf(commonResult.getCode()))) {
                resultVO.setSuccess(true);
            } else {
                resultVO.setSuccess(false);
            }
            resultVO.setCode(String.valueOf(commonResult.getCode()));
           //  resultVO.setMessage(commonResult.getMessage());
            Map<String, String> resultMap = (Map<String, String>) commonResult.getData();
            if (resultMap.containsKey("变更交货期")) {
                String val = resultMap.get("变更交货期");
                resultVO.setMessage(val);
            }
            map.put(orderNoInfo.getFullOrderNo(), resultVO);
        }
        return ResultVo.success(map);
    }

    @Override
    public ResultVo<OrderEditResult> orderEdit(OrderEditVO orderDeliveryDate) {
        //参数校验
        if (orderDeliveryDate == null) {
            return ResultVo.failure("修改参数不可为空");
        } else {
            DeliveryAddressInfo deliveryAddressInfo = orderDeliveryDate.getDeliveryAddressInfo();
            if (StringUtils.isBlank(deliveryAddressInfo.getDlvFlag())) {
                return ResultVo.failure("发货方式不可为空");
            } else {
                List<String> dlvFlagEnumValues = new ArrayList<>();
                for (DlvSiteEnum value : DlvSiteEnum.values()) {
                    dlvFlagEnumValues.add(value.getCode().toString());
                }
                if (!dlvFlagEnumValues.contains(deliveryAddressInfo.getDlvFlag())) {
                    return ResultVo.failure("发货方式解析失败，只能为1直发客户，2直发营业所，3自提");
                }
            }
        }

        log.info("门户接单查询->订单修改 门户接单查询 -> 订单修改 {}",JSONUtil.toJsonPrettyStr(orderDeliveryDate));

        OrderEditResult resultVO;
        OpsOrderModifyDto opsOrderModifyDto;
        CommonResult commonResult;
        Orderdlvdata orderdlvdata = new Orderdlvdata();
        OrderEditVO item = orderDeliveryDate;
        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo());
        opsOrderModifyDto = new OpsOrderModifyDto();
        opsOrderModifyDto.setOrderId(orderNoInfo.getOrderNo());
        opsOrderModifyDto.setOrderItem(orderNoInfo.getItemNo());

        // 是否修改主项
        if (StringUtils.isNotBlank(item.getIntensiveNo())) {
            opsOrderModifyDto.setMaster(Boolean.TRUE);
            opsOrderModifyDto.setDlvType(item.getIntensiveNo());
        } else {
            opsOrderModifyDto.setMaster(Boolean.FALSE);
        }

        if (item.getCustomerChangeDate() != null || item.getWarehouseSendDate() != null) {
            // 是否修改货期
            opsOrderModifyDto.setUpdateDate(true);
            // 设置客户货期
            if (item.getCustomerChangeDate() == null) {
                opsOrderModifyDto.setDlvDate(item.getDlvDate());
            } else {
                opsOrderModifyDto.setDlvDate(item.getCustomerChangeDate());
            }
            // 设置物流货期
            opsOrderModifyDto.setWmsDlvDate(item.getWarehouseSendDate());
        }

        if (StringUtils.isNotBlank(item.getOptCarrier())) {
            // 设置承运商
            opsOrderModifyDto.setCarrier(opsCommonService.getCarrierCodeByName(item.getOptCarrier()));
        }

        // 是否特发
        if ("普通".equals(item.getSpecialNormal())) {
            opsOrderModifyDto.setDlvSpecial(false);
        } else if ("特发".equals(item.getSpecialNormal())) {
            opsOrderModifyDto.setDlvSpecial(true);
        }

        // 是否拆分子项发货
        if ("子项特发".equals(item.getAssemble())) {
            opsOrderModifyDto.setDlvSplit(Boolean.TRUE);
        } else {
            opsOrderModifyDto.setDlvSplit(Boolean.FALSE);
        }
        // 变更发货地址
        DeliveryAddressInfo addressInfo = item.getDeliveryAddressInfo();
        if (null == addressInfo) {
            opsOrderModifyDto.setUpdateAddress(false);
        } else {
            if(StringUtils.isBlank(addressInfo.getDlvFlag())){
                return ResultVo.failure("发货方式不可为空");
            }
            Class<? extends DeliveryAddressInfo> aClass = addressInfo.getClass();
            Field[] fields = aClass.getDeclaredFields();
            try {
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (null != field.get(addressInfo) && StringUtils.isBlank(field.get(addressInfo).toString())) {
                        field.set(addressInfo, null);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // 是否修改地址
            opsOrderModifyDto.setUpdateAddress(true);
            orderdlvdata.setOrderno(orderNoInfo.getOrderNo());
            orderdlvdata.setItemno(orderNoInfo.getItemNo());
            // 收货地址
            orderdlvdata.setDlvaddress(addressInfo.getDlvAddress());
            // 联系人
            orderdlvdata.setContactpsn(addressInfo.getContactPerson());
            // 联系电话
            orderdlvdata.setTelno(addressInfo.getContactPhone());
            // 省.市.区
            orderdlvdata.setProvince(addressInfo.getProvince());
            orderdlvdata.setCity(addressInfo.getCity());
            orderdlvdata.setDistrict(addressInfo.getRegion());
            // 身份证号
            orderdlvdata.setIdcard(addressInfo.getPersonId());
            // 客户名称
            orderdlvdata.setCstmname(addressInfo.getCustomerName());

            if (StringUtils.isNotBlank(addressInfo.getEmail())) {
                orderdlvdata.setEmail(addressInfo.getEmail().replaceAll("/",";"));
            }

            orderdlvdata.setPostcode(addressInfo.getPostCode());
            // 发货方式  发货方式-直发客户/直发营业所（1：直发客户；2：直发营业所;3:自提)
            orderdlvdata.setDlvflag(addressInfo.getDlvFlag());
            opsOrderModifyDto.setAddress(orderdlvdata);
        }
        // 操作人
        UserDto userDto = new UserDto();
        userDto.setUserName(item.getLoginUserId());
        opsOrderModifyDto.setUserDto(userDto);
        // 变更原因
        opsOrderModifyDto.setReason("门户申请变更");
        log.info("订单子单修改 参数 = {}", JSONObject.toJSONString(opsOrderModifyDto));
        commonResult = opsOrderFeignApi.modifyRcvOrder(opsOrderModifyDto);

        log.info("订单子单修改 结果 = {}", JSON.toJSONString(commonResult));
        if (commonResult == null) {
            return ResultVo.failure("订单修改接口返回null");
        }
        resultVO = new OrderEditResult();
        if ("200".equals(String.valueOf(commonResult.getCode()))) {
            resultVO.setSuccess(true);
        } else {
            resultVO.setSuccess(false);
        }
        resultVO.setCode(String.valueOf(commonResult.getCode()));
        resultVO.setMessage(commonResult.getMessage());
        Map<String, String> resultMap = (Map<String, String>) commonResult.getData();
        resultVO.setData(resultMap);
        return ResultVo.success(resultVO);
    }

    @Override
    public ResultVo<List<DlvEntireVO>> getDlvEntireStatusByFullOrderNo(List<String> orderNoList) {

        if (CollectionUtils.isEmpty(orderNoList)) {
            return ResultVo.success();
        }
        List<DlvEntireVO> list = new ArrayList<>();
        for (String orderNo : orderNoList) {
            if (StringUtils.isBlank(orderNo)) {
                continue;
            }
            OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderNo);
            if (orderNoInfo == null) {
                continue;
            }
            DlvEntireVO dlvEntireVO = getDlvEntireByOrderNo(orderNoInfo.getOrderNo());
            if (dlvEntireVO == null) {
                continue;
            }
            if (StringUtils.isNotBlank(dlvEntireVO.getDlvEntire()) && "2".equals(dlvEntireVO.getDlvEntire()))
            {
                dlvEntireVO.setIsDlvEntire(true);
            } else {
                dlvEntireVO.setIsDlvEntire(false);
            }
            dlvEntireVO.setOrderNo(orderNo);
            list.add(dlvEntireVO);
        }
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<String> handDelOrderStatusToMenHu(HandDelOrderStatusVO handDelOrderStatusVO) {

        if (handDelOrderStatusVO == null || CollectionUtils.isEmpty(handDelOrderStatusVO.getIds())) {
            return ResultVo.failure("请选择操作数据.");
        }
        if (StringUtils.isBlank(handDelOrderStatusVO.getOptUserNo())) {
            return ResultVo.failure("请重新登录.");
        }
        if (StringUtils.isBlank(handDelOrderStatusVO.getOptUserName())) {
            handDelOrderStatusVO.setOptUserName(handDelOrderStatusVO.getOptUserNo());
        }

        StringBuilder errMsg = new StringBuilder();
        StringBuilder okMsg = new StringBuilder();
        OrderModifyDO orderModifyDO = null;
        RcvDetailDO rcvDetailDO = null;
        LambdaQueryWrapper<OrderModifyDO> queryOrderModify = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<RcvDetailDO> queryRcvDetail = new LambdaQueryWrapper<>();
        LambdaUpdateWrapper<OrderModifyDO> upOrderModify = new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<OrderStateDO> upOrderState = new LambdaUpdateWrapper<>();
        int count = 0;
        int errCount = 0;
        for (String id : handDelOrderStatusVO.getIds()) {
            queryOrderModify.clear();
            queryOrderModify.eq(OrderModifyDO::getId,id);
            orderModifyDO = orderModifyMapper.selectOne(queryOrderModify);
            if (orderModifyDO == null) {
                continue;
            }
            queryRcvDetail.clear();
            queryRcvDetail.eq(RcvDetailDO::getRorderFno,orderModifyDO.getOrderNo());
            rcvDetailDO = rcvdetailMapper.selectOne(queryRcvDetail);
            if (rcvDetailDO == null || rcvDetailDO.getStatus() != 9) {
                errMsg.append(orderModifyDO.getOrderNo()+"未删单;");
                errCount++;
                continue;
            }
            List<OrderCancelResult> cancelResultList = new ArrayList<>(1);
            OrderCancelResult cancelResult = new OrderCancelResult();
            cancelResult.setOrderNo(orderModifyDO.getOrderNo());
            cancelResult.setResult("2");
            cancelResult.setMessage("ops人工补推删单");
            cancelResult.setHandlePsnNo(handDelOrderStatusVO.getOptUserNo());
            cancelResult.setHandlePsnName(handDelOrderStatusVO.getOptUserName());
            cancelResult.setHandleRemark("已删单");
            cancelResultList.add(cancelResult);
            log.info("删单[客户单] 回调给门户队列的接口 => {} , 操作人: {}",JSONObject.toJSONString(cancelResult), handDelOrderStatusVO.getOptUserNo());
            ResultVo<Boolean> resultVo = smsOrderServiceFeignApi.sendOrderCancelReturnMessage(cancelResultList);
            if (resultVo.isSuccess()) {
                count++;
                // 变更order_modify的状态
                upOrderModify.clear();
                upOrderModify.eq(OrderModifyDO::getId,id).set(OrderModifyDO::getStatus,6);
                int update = orderModifyMapper.update(null, upOrderModify);
                if (update == 1) {
                    // 变更order_state的状态
                    upOrderState.clear();
                    upOrderState.eq(OrderStateDO::getOrderNo,orderModifyDO.getOrderNo())
                            .set(OrderStateDO::getStateCode,90)
                            .set(OrderStateDO::getStateDes,"已删单")
                            .set(OrderStateDO::getUpdateTime,new Date());
                    orderStateMapper.update(null,upOrderState);
                }
                okMsg.append(orderModifyDO.getOrderNo()+";");
                // 记录日志表
                OrderLogVO log = new OrderLogVO();
                log.setOptUserName(handDelOrderStatusVO.getOptUserNo());
                log.setDescription("手动回传门户删单状态");
                log.setCreateTime(new Date());
                log.setOptTime(new Date());
                log.setOptType(1);
                log.setOrderNo(orderModifyDO.getOrderNo());
                orderLogService.sendOrderLogMsgToMQ(log);
            } else {
                errMsg.append(orderModifyDO.getOrderNo()+"门户返回失败;");
                errCount++;
            }
        }
        if (count > 0 && errCount > 0) {
            return ResultVo.failure("处理完毕,成功处理: "+count+"条数据,处理失败:" + errCount + "条 " +errMsg.toString());
        }
        if (count > 0 && errCount == 0) {
            return ResultVo.success("处理完毕,成功处理: "+count+"条数据 : "+ okMsg.toString());
        }
        if (count == 0 && errCount > 0) {
            return ResultVo.failure("处理完毕,处理失败: "+errCount+"条数据 : "+ errMsg.toString());
        }
        return ResultVo.success("请重新选择数据进行操作");
    }

    @Override
    public ResultVo<List<OpsDeliveryTypeDO>> findDeliveryType(String dlvType) {
        if (StringUtils.isBlank(dlvType)) {
            return ResultVo.failure("参数不可为空");
        }
        String key = Constants.DLV_TYPE + dlvType;
        if (redisManager.hasKey(key)) {
            Object o = redisManager.get(key);
            if (o != null) {
                List<OpsDeliveryTypeDO> opsDeliveryTypeDOS = JSONArray.parseArray(o.toString(), OpsDeliveryTypeDO.class);
                return ResultVo.success(opsDeliveryTypeDOS);
            }
        }
        LambdaQueryWrapper<OpsDeliveryTypeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OpsDeliveryTypeDO::getStatus,"1")
                .eq(OpsDeliveryTypeDO::getDeliveryPriorityHigh,dlvType);
        List<OpsDeliveryTypeDO> opsDeliveryTypeDOS = opsDeliveryTypeMapper.selectList(queryWrapper);
        redisManager.set(key, JSONArray.toJSONString(opsDeliveryTypeDOS));
        return ResultVo.success(opsDeliveryTypeDOS);
    }

    @Override
    public ResultVo<AmountAndEdiscountVO> quertTotalAmountAndEdiscount(SMSSearchListInfo condition) {

        log.info("查询总金额和E率参数 {}", JSONUtil.toJsonStr(condition));

        if (condition == null) {
            return ResultVo.failure("参数不可为空");
        }

        AmountAndEdiscountVO amountAndEdiscountVO = new AmountAndEdiscountVO();

        if (StringUtils.isNotBlank(condition.getOperator())) {
            if (condition.getOperator().startsWith("ACC") || condition.getOperator().startsWith("acc")) {
                return quertTotalAmountAndEdiscountByAcc(condition);
            }
        }

        if (StringUtils.isNotBlank(condition.getOrderNo())) {
            condition.setOrderNo(condition.getOrderNo() + "%");
        }

        if (StringUtils.isNotBlank(condition.getModelNo())) {
            condition.setModelNo(condition.getModelNo() + "%");
        }
        if (StringUtils.isNotBlank(condition.getRemark())) {
            condition.setRemark(condition.getRemark() + "%");
        }
        if (StringUtils.isNotBlank(condition.getCustProductNo())) {
            condition.setCustProductNo(condition.getCustProductNo() + "%");
        }

        if (StringUtils.isNotBlank(condition.getPurchaseNo())) {
            condition.setPurchaseNo(condition.getPurchaseNo() + "%");
        }

        if (condition.getOrderDateStart() == null || condition.getOrderDateEnd() == null) {
            return ResultVo.success(amountAndEdiscountVO);
        }
        if (!DateUtils.isSameDay(condition.getOrderDateStart(), condition.getOrderDateEnd())) {
            return ResultVo.success(amountAndEdiscountVO);
        }
        if (condition.getOrderDateStart() != null) {
            condition.setOrderDateStartStr(DateUtil.getStartDateTimeStr(condition.getOrderDateStart()));
        }

        if (condition.getOrderDateEnd() != null) {
            condition.setOrderDateEndStr(DateUtil.getEndDateTimeStr(condition.getOrderDateEnd()));
        }

        long lstart = System.currentTimeMillis();

        List<String> types = new ArrayList<>();
        if (StringUtils.isBlank(condition.getCustomerType())) {
            types.add(OrderTypeEnum.saleOrder.getCode());
            types.add(OrderTypeEnum.ypOrder.getCode());
        } else {
            types.add(condition.getCustomerType());
        }
        // 订单状态集合
        List<String> status = new ArrayList<>();
        // 采购供应商
        List<String> purchasingSupplier = new ArrayList<>();
        if (StringUtils.isNotBlank(condition.getStatus())) {

            if (String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()).equals(condition.getStatus())) {
                condition.setIntercepter(1);
            } else {
                status.add(condition.getStatus());
                condition.setIntercepter(null);
            }
        }
        // 快捷查询类型
        if (StringUtils.isNotBlank(condition.getSearchType())) {
            switch (condition.getSearchType()) {
                case "1": // 日本采购中
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
                    purchasingSupplier.add("JP");
                    break;
                case "2":  // 制造采购中
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
                    ResultVo<List<SupplierVo>> chinaSuppliers = commonServiceFeignApi.findChinaSuppliers();
                    if (chinaSuppliers.isSuccess() && chinaSuppliers.getData() != null) {
                        for (SupplierVo item : chinaSuppliers.getData()) {
                            purchasingSupplier.add(item.getCompanyId());
                        }
                    }
                    break;
                case "3": // 信用已拦截
                    status.add(String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()));
                    break;
                default:
                    break;
            }
        }
        // 未发货状态
        if (StringUtils.isNotBlank(condition.getDeliveryStatus())) {
            switch (condition.getDeliveryStatus()) {
                case "1":
                    status.addAll(noSendOrderStatusList());
                    break;
                case "2":
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_DELIVERY.getCode()));
                    break;
                case "3":
                    status.add(String.valueOf(RCVOrderStatusEnum.WAITING_DELIVERY.getCode()));
                    break;
                default:
                    break;
            }
        }
        // 排序字段转换
//        if (StringUtils.isNotBlank(condition.getProperty())) {
//            condition.setProperty(getSortFieldForOrderQuery4(condition.getProperty(), condition.getOrder()));
//        }

        // 权限集合
        List<String> userAuth = new ArrayList<>();
        List<String> deptAuth = new ArrayList<>();
        List<String> customerAuth = new ArrayList<>();
        List<String> employeeAuth = new ArrayList<>();
        List<String> inCodeAuth = new ArrayList<>();
        if (condition.getDataAuthoritySearchCondition() != null) {
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getUserNoListByDataAuthority())) {
                userAuth = condition.getDataAuthoritySearchCondition().getUserNoListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
                deptAuth = condition.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
                customerAuth = condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getUserIdListByDataAuthority())) {
                employeeAuth = condition.getDataAuthoritySearchCondition().getUserIdListByDataAuthority();
            }
            if (CollectionUtils.isNotEmpty(condition.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority())) {
                inCodeAuth = condition.getDataAuthoritySearchCondition().getIndCodeListByDataAuthority();
            }
        }
        List<String> finalUserAuth = userAuth;
        List<String> finalDeptAuth = deptAuth;
        List<String> finalCustomerAuth = customerAuth;
        List<String> finalEmployeeAuth = employeeAuth;
        List<String> finalInCodeAuth = inCodeAuth;


        if (StringUtils.isNotBlank(condition.getOrderNo()) && condition.getOrderNo().length() < 7) {
            return ResultVo.success(amountAndEdiscountVO);
        }
        amountAndEdiscountVO = rcvDetailMapperReadOnlyMapper.queryAmountAndEdiscount2(condition, types, status, purchasingSupplier, finalUserAuth, finalDeptAuth, finalCustomerAuth, finalEmployeeAuth, finalInCodeAuth);
//        if (CollectionUtils.isEmpty(viewDOList)) {
//            return ResultVo.success(amountAndEdiscountVO);
//        }
//        BigDecimal totalAmount = viewDOList.stream().map(RcvOrderWithCustomerForViewDO::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//        BigDecimal totalNtaxAmount = viewDOList.stream().map(RcvOrderWithCustomerForViewDO::getTotalNtaxAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//        BigDecimal totalEprice = viewDOList.stream().map(RcvOrderWithCustomerForViewDO::getEDiscount).reduce(BigDecimal.ZERO, BigDecimal::add);
//        BigDecimal subtract = totalEprice.subtract(BigDecimal.valueOf(1));
//        amountAndEdiscountVO.setTotalAmount(totalAmount.setScale(2, RoundingMode.HALF_UP));
//        amountAndEdiscountVO.setEDiscount(totalNtaxAmount.divide(subtract,3, RoundingMode.HALF_UP));
        return ResultVo.success(amountAndEdiscountVO);
    }

    /**
     * 2024-01-24
     * 门户调用，催促模块校验接口
     *
     * @param orderNoList
     * @return
     */
    @Override
    public ResultVo<List<InquiryApplyVerifyReurn>> getInquiryVerify(List<String> orderNoList) {
        if (CollectionUtils.isEmpty(orderNoList)) {
            return ResultVo.failure("催促请求订单号为空，请重试！");
        }
        // feign调用催促模块，催促查验接口
        return inquiryAdapterFeignApi.purchaseInquiryVerify(orderNoList);
    }

    /*
     * 门户调用，订单催促校验接口 bugid 14912 C14717
     * @param orderNoList
     * @return
     */
    @Override
    public ResultVo<List<InquiryOrderVerifyReurn>> inqAOrderVerify(List<String> orderNoList) {
        if (CollectionUtils.isEmpty(orderNoList)) {
            return ResultVo.failure("催促请求订单号为空，请重试！");
        }
        // feign调用催促模块，催促查验接口
        return inquiryAdapterFeignApi.orderInquiryVerify(orderNoList);
    }

    public ResultVo<AmountAndEdiscountVO> quertTotalAmountAndEdiscountByAcc(SMSSearchListInfo condition) {
        if (condition == null) {
            return ResultVo.failure("参数不可为空");
        }

        long lstart = System.currentTimeMillis();

        AmountAndEdiscountVO amountAndEdiscountVO = new AmountAndEdiscountVO();
        if (condition.getDataAuthoritySearchCondition() == null
                || CollectionUtils.isEmpty(condition.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
            return ResultVo.success(amountAndEdiscountVO);
        }

        if (StringUtils.isNotBlank(condition.getOrderNo())) {
            condition.setOrderNo(condition.getOrderNo() + "%");
        }

        if (StringUtils.isNotBlank(condition.getModelNo())) {
            condition.setModelNo(condition.getModelNo() + "%");
        }
        if (StringUtils.isNotBlank(condition.getRemark())) {
            condition.setRemark(condition.getRemark() + "%");
        }
        if (StringUtils.isNotBlank(condition.getCustProductNo())) {
            condition.setCustProductNo(condition.getCustProductNo() + "%");
        }

        if (StringUtils.isNotBlank(condition.getPurchaseNo())) {
            condition.setPurchaseNo(condition.getPurchaseNo() + "%");
        }

        if (condition.getOrderDateStart() == null || condition.getOrderDateEnd() == null) {
            return ResultVo.success(amountAndEdiscountVO);
        }
        if (!DateUtils.isSameDay(condition.getOrderDateStart(), condition.getOrderDateEnd())) {
            return ResultVo.success(amountAndEdiscountVO);
        }

        if (condition.getOrderDateStart() != null) {
            condition.setOrderDateStartStr(DateUtil.getStartDateTimeStr(condition.getOrderDateStart()));
        }

        if (condition.getOrderDateEnd() != null) {
            condition.setOrderDateEndStr(DateUtil.getEndDateTimeStr(condition.getOrderDateEnd()));
        }

        List<String> types = new ArrayList<>();
        if (StringUtils.isBlank(condition.getCustomerType())) {
            types.add(OrderTypeEnum.saleOrder.getCode());
            types.add(OrderTypeEnum.ypOrder.getCode());
        } else {
            types.add(condition.getCustomerType());
        }

        // 订单状态集合
        List<String> status = new ArrayList<>();
        // 采购供应商
        List<String> purchasingSupplier = new ArrayList<>();

        if (StringUtils.isNotBlank(condition.getStatus())) {

            if (String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()).equals(condition.getStatus())) {
                condition.setIntercepter(1);
            } else {
                status.add(condition.getStatus());
                condition.setIntercepter(null);
            }
        }

        // 快捷查询类型
        if (StringUtils.isNotBlank(condition.getSearchType())) {
            switch (condition.getSearchType()) {
                case "1":
                    // 日本采购中
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
                    purchasingSupplier.add("JP");
                    break;
                case "2":
                    // 制造采购中
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode()));
                    ResultVo<List<SupplierVo>> chinaSuppliers = commonServiceFeignApi.findChinaSuppliers();
                    if (chinaSuppliers.isSuccess() && chinaSuppliers.getData() != null) {
                        for (SupplierVo item : chinaSuppliers.getData()) {
                            purchasingSupplier.add(item.getCompanyId());
                        }
                    }
                    break;
                case "3":
                    // 信用已拦截
                    status.add(String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode()));
                    break;
                default:
                    break;
            }
        }
        // 未发货状态
        if (StringUtils.isNotBlank(condition.getDeliveryStatus())) {
            switch (condition.getDeliveryStatus()) {
                case "1":
                    status.addAll(noSendOrderStatusList());
                    break;
                case "2":
                    status.add(String.valueOf(RCVOrderStatusEnum.IN_DELIVERY.getCode()));
                    break;
                case "3":
                    status.add(String.valueOf(RCVOrderStatusEnum.WAITING_DELIVERY.getCode()));
                    break;
                default:
                    break;
            }
        }
        // 排序字段转换
//        if (StringUtils.isNotBlank(condition.getProperty())) {
//            condition.setProperty(getSortFieldForOrderQueryWithAgent(condition.getProperty(), condition.getOrder()));
//        }
        amountAndEdiscountVO = rcvDetailMapperReadOnlyMapper.queryAmountAndEdiscountByAgent2(condition, types, status, purchasingSupplier);
//        if (CollectionUtils.isEmpty(viewDOList)) {
//            return ResultVo.success(amountAndEdiscountVO);
//        }
//        BigDecimal totalAmount = viewDOList.stream().map(RcvOrderWithCustomerForViewDO::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//        BigDecimal totalNtaxAmount = viewDOList.stream().map(RcvOrderWithCustomerForViewDO::getTotalNtaxAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//        BigDecimal totalEprice = viewDOList.stream().map(RcvOrderWithCustomerForViewDO::getEDiscount).reduce(BigDecimal.ZERO, BigDecimal::add);
//        BigDecimal subtract = totalEprice.subtract(BigDecimal.valueOf(1));
//        amountAndEdiscountVO.setTotalAmount(totalAmount.setScale(2, RoundingMode.HALF_UP));
//        amountAndEdiscountVO.setEDiscount(totalNtaxAmount.divide(subtract,3, RoundingMode.HALF_UP));
        return ResultVo.success(amountAndEdiscountVO);
    }

    public DlvEntireVO getDlvEntireByOrderNo(String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            return null;
        }
        return rcvmasterMapper.getDlvEntireByOrOrderNo(orderNo);
    }

    private String convertCarriterFromName(String name) {
        if (StringUtils.isEmpty(name)) {
            return "NON";
        }
        if ("物流自选".contains(name)) {
            return "NON";
        }
        if ("顺丰特快".contains(name)) {
            return "SF";
        }
        return name;
    }

    /**
     *  不是信用拦截的状态
     * @return
     */
    public List<String> noIntercepterList() {
        List<String> noInterceptList = new ArrayList<>();
        noInterceptList.add(String.valueOf(RCVOrderStatusEnum.DELIVERED.getCode()));
        noInterceptList.add(String.valueOf(RCVOrderStatusEnum.RETURN.getCode()));
        noInterceptList.add(String.valueOf(RCVOrderStatusEnum.CANCEL.getCode()));
        // noInterceptList.add(String.valueOf(RCVOrderStatusEnum.ERROR.getCode()));
        noInterceptList.add(String.valueOf(RCVOrderStatusEnum.INVOICE.getCode()));
        return noInterceptList;
    }


    // 设置子单状态
    public OrderItemData setItemInfo(OrderItemData orderItem, boolean calCanPress) {
        // 设置初始值
        orderItem.setCanChangeDeliveryDate(true);
        orderItem.setCanChangeDeliveryModel(true);
        orderItem.setCanDelete(true);

        // 设置:62已出货，63已开发票；endinvoice_sh.optcode=2/3/4/9 不可更改出货方式，不可更改货期
        if (StringUtils.equals(orderItem.getBjCode(), "6")
                && (StringUtils.equals(orderItem.getOptState(), "1") || StringUtils.equals(orderItem.getOptState(), "2")
                || StringUtils.equals(orderItem.getOptState(), "3"))) {
            if (StringUtils.isNotBlank(orderItem.getShCode())) {
                if (StringUtils.equals(orderItem.getShCode(), "2") || StringUtils.equals(orderItem.getShCode(), "3")
                        || StringUtils.equals(orderItem.getShCode(), "4")
                        || StringUtils.equals(orderItem.getShCode(), "9")) {
                    orderItem.setCanChangeDeliveryDate(false);
                    orderItem.setCanChangeDeliveryModel(false);
                    orderItem.setCanDelete(false);
                }
            } else {
                orderItem.setCanChangeDeliveryDate(false);
                orderItem.setCanChangeDeliveryModel(false);
                orderItem.setCanDelete(false);
            }
        }
        if (StringUtils.equals(orderItem.getOptState(), "9")) {
            orderItem.setCanChangeDeliveryDate(false);
            orderItem.setCanChangeDeliveryModel(false);
            orderItem.setCanDelete(false);
        }
        if (StringUtils.equals(orderItem.getBjCode(), "5")) {
            orderItem.setCanDelete(false);
        }
        if (calCanPress) {
            // 判断是否可催促
            OrderItemData item = this.canPress(orderItem.getOrderNo());
            orderItem.setCanPress(item.isCanPress());
            orderItem.setPressMsg(item.getPressMsg());
        }

        return orderItem;
    }

    public OrderItemData setItemInfoSplit(OrderItemData orderItem) {
        // 设置初始值
        orderItem.setCanChangeDeliveryDate(false);
        orderItem.setCanChangeDeliveryModel(false);
        orderItem.setCanDelete(false);

        // 判断是否可催促
        OrderItemData item = this.canPress(orderItem.getOrderNo());
        orderItem.setCanPress(item.isCanPress());
        orderItem.setPressMsg(item.getPressMsg());

        return orderItem;
    }

    public OrderItemData canPress(String orderNo) {
        OrderItemData item = new OrderItemData();
        item.setOrderNo(orderNo);
        item.setCanPress(false);
//        item.setPressMsg("此订单不可催促");
        // 判断采购表
//        List<OrderItemData> can = orderItemDao.getCanPressItem(orderNo);
//        if (can != null && can.size() > 0) {
//            item.setModelNo(can.get(0).getModelNo());
//            item.setQuantity(can.get(0).getQuantity());
//            PressCheckInfo info = new PressCheckInfo();
//            info.setRequisition_No(orderNo);
//            info.setModel(item.getModelNo());
//            // 判断制造状态
//            PressCheckInfo check = manufactureAdapterApiService.check(info).getData();
//            if (check == null || check.isReturn_Sign()) {
//                if (check != null) {
//                    item.setPressMsg(check.getReturn_Info());
//                }
//            } else {
//                // 若可催促，则判断催促次数是否大于三次；
//                Integer pressCount = Dao.countByOrderNo(orderNo);
//                if (pressCount > 2) {
//                    item.setPressMsg("订单" + orderNo + "已超催促次数，不可再催促。");
//                } else {
//                    item.setCanPress(true);
//                    item.setPressMsg("");
//                }
//            }
//
//        }
        return item;
    }

    /**
     * 未发货状态
     */
    public List<String> noSendOrderStatusList() {
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(RCVOrderStatusEnum.WAITING_PROCESS.getCode())); // 接单未处理
        list.add(String.valueOf(RCVOrderStatusEnum.IN_PURCHASE.getCode())); // 对外采购
        list.add(String.valueOf(RCVOrderStatusEnum.PURCHASE_INTERCEPT.getCode())); // 采购拦截
        list.add(String.valueOf(RCVOrderStatusEnum.IN_TRANSFER.getCode())); // 仓间调拨
        list.add(String.valueOf(RCVOrderStatusEnum.WAITING_DELIVERY.getCode())); // 等待出库
        list.add(String.valueOf(RCVOrderStatusEnum.IN_DELIVERY.getCode())); // 出库处理
        list.add(String.valueOf(RCVOrderStatusEnum.ERROR.getCode())); // 异常订单
        list.add(String.valueOf(RCVOrderStatusEnum.NO_PROCESS.getCode())); // 暂不处理
        list.add(String.valueOf(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode())); // 信用拦截
        list.add(String.valueOf(RCVOrderStatusEnum.RESOLVE.getCode())); // 二次处理
        return list;
    }

    // true 已发货
    // add by LiYingChao from bugid 9163 in 2022-12-29
    public Boolean isSendOrderStatus(String statusDesc) {
        if (StringUtils.isBlank(statusDesc)) {
            return false;
        }
        List<String> sendOrderStatus = new ArrayList<>();
        sendOrderStatus.add(OrderStatusDetailEnum.CANCEL.getState()); // 已删单
        sendOrderStatus.add(OrderStatusDetailEnum.WM_DELIVERY.getState()); // 已发货
        sendOrderStatus.add(OrderStatusDetailEnum.IPS_INVOICED.getState()); // 已开票
        return sendOrderStatus.contains(statusDesc);
    }

    public Boolean isSendOrderStatusForOrderState(Integer code) {
        if (code == null) {
            return false;
        }
        List<Integer> sendOrderStatus = new ArrayList<>();
        sendOrderStatus.add(OrderStateEnum.Shipped.code());
        sendOrderStatus.add(OrderStateEnum.Returned.getCode());
        sendOrderStatus.add(OrderStateEnum.CanceledNotConfirm.getCode());
        sendOrderStatus.add(OrderStateEnum.Invoiced.getCode());
        return sendOrderStatus.contains(code);

    }

    /**
     * 接单查询,导出共用数据数据
     * @return
     */
    public List<SMSOrder> commonDataHand(List<RcvOrderWithCustomerForViewDO> rcvOrderWithCustomerForViewDOS) {
        List<SMSOrder> list = new ArrayList<>(rcvOrderWithCustomerForViewDOS.size());
        SMSOrder smsOrder;
        Map<String, String> nameMap = new HashMap<>(rcvOrderWithCustomerForViewDOS.size());
        String name;
        List<String> noInterceptList = noIntercepterList();
        for (RcvOrderWithCustomerForViewDO item : rcvOrderWithCustomerForViewDOS) {
            smsOrder = BeanCopyUtil.copy(item, SMSOrder.class);
            smsOrder.setOrderNo(item.getRorderFno()); // 完整订单号
            smsOrder.setHddno(item.getOrOrderNo()); // 合同订单号

            if (item.getStatus() != null) {
                if (noInterceptList.contains(String.valueOf(item.getStatus()))) {
                    smsOrder.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
                } else {
                    // 判断是否拦截,若是拦截则显示信用拦截,反之显示订单状态
                    if (item.getIntercept() != null && item.getIntercept()) {
                        smsOrder.setStatusName("信用拦截");
                    } else {
                        smsOrder.setStatusName(RCVOrderStatusEnum.getName(item.getStatus()));
                    }
                }

                if (item.getStatus() == 9) {
                    // 删除状态名称
                    smsOrder.setDeleteStatusName(DeleteStatusEnum.alreadyDel.getCodeName());
                } else {
                    smsOrder.setDeleteStatusName(DeleteStatusEnum.noDel.getCodeName());
                }
            }

            if (StringUtils.isNotBlank(item.getEmployeeNo())) {
                // 制单人id
                smsOrder.setCreateId(item.getEmployeeNo());
                // 制单人名称
                if (!nameMap.containsKey(item.getEmployeeNo())) {
                    name = opsCommonService.getEmpSaleNameByNo(item.getEmployeeNo());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(item.getEmployeeNo(), name);
                    }
                }
                smsOrder.setCreateUserName(nameMap.getOrDefault(item.getEmployeeNo(), ""));
            }

            // 出库区分类名称
            if (StringUtils.isNotBlank(item.getStockType()) && StringUtils.isNotBlank(item.getStockCode())) {
                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(item.getStockType()) + "[" + item.getStockCode() + "]");
            } else if (StringUtils.isBlank(item.getStockType())) {
                smsOrder.setStockTypeName(item.getStockCode());
            } else {
                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(item.getStockType()));
            }

            // 返回阀汇流板标识名称 （0:正常订货;1:底板;2:组装原件）
            smsOrder.setSpecMark(SpecMarkEnum.getCodeName(item.getSpecMark()));
            // 出库区分的库存类别名称
            smsOrder.setInventoryTypeCodeName(InventoryTypeEnum.getName(item.getInventoryTypeCode()));
            // 拆分标识名称
            smsOrder.setProdFlagName(OrderSplitTypeEnum.getCodeName(item.getProdFlag()));
            // 承运商名称
            if (StringUtils.isNotBlank(item.getCarrierId())) {
                smsOrder.setCarrierName(opsCommonService.getCarrierNameByCode(item.getCarrierId()));
            }
            // 客户名称
            if (StringUtils.isNotBlank(item.getCustomerNo())) {
                if (!nameMap.containsKey(item.getCustomerNo())) {
                    name = opsCommonService.getCustomerNameByNo(item.getCustomerNo());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(item.getCustomerNo(), name);
                    }
                }
                smsOrder.setCustomerName(nameMap.getOrDefault(item.getCustomerNo(), ""));
            }
            // 用户名称
            if (StringUtils.isNotBlank(item.getUserNo())) {
                if (!nameMap.containsKey(item.getUserNo())) {
                    name = opsCommonService.getCustomerNameByNo(item.getUserNo());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(item.getUserNo(), name);
                    }
                }
                smsOrder.setUserName(nameMap.getOrDefault(item.getUserNo(), ""));
            }

            // 最终用户名称
//            if (StringUtils.isNotBlank(item.getEndUser())) {
//                if (!nameMap.containsKey(item.getEndUser())) {
//                    name = opsCommonService.getCustomerNameByNo(item.getEndUser());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(item.getEndUser(), name);
//                    }
//                }
//                smsOrder.setEndUserName(nameMap.getOrDefault(item.getEndUser(), ""));
//            }
            smsOrder.setEndUserName(item.getName());

            // 部门名称
            if (StringUtils.isNotBlank(item.getHlCode())) {
                if (!nameMap.containsKey(item.getHlCode())) {
                    name = opsCommonService.getDeptNameByNo(item.getHlCode());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(item.getHlCode(), name);
                    }
                }
                smsOrder.setSaleDeptNo(item.getHlCode());
                smsOrder.setSaleDeptNoName(nameMap.get(item.getHlCode()));
            } else {
                if (!nameMap.containsKey(item.getHrUnitId())) {
                    name = opsCommonService.getDeptNameByNo(item.getHrUnitId());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(item.getHrUnitId(), name);
                    }
                }
                smsOrder.setSaleDeptNo(item.getHrUnitId());
                smsOrder.setSaleDeptNoName(nameMap.get(item.getHrUnitId()));
            }

            // 如果最终用户价格为空 则取价格
            if (item.getPriceEndUser() == null || item.getPriceEndUser().compareTo(BigDecimal.ZERO) == 0) {
                smsOrder.setPriceEndUser(item.getPrice());
            }
            // E率
            if (smsOrder.getDiscount() == null || smsOrder.getDiscount().compareTo(BigDecimal.ZERO) == 0) {
                if (item.getNtaxPice() != null && item.getNtaxPice().compareTo(BigDecimal.ZERO) != 0) {
                    smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(item.getNtaxPice(), item.getEPrice()));
                } else {
                    // 不含税单价
                    if (item.getPrice() == null) {
                        item.setPrice(BigDecimal.ZERO);
                    }
                    BigDecimal noTaxPrice = CommonFormulaUtil.calcNoTaxPrice(item.getPrice(), item.getTaxRate());
                    smsOrder.setNtaxPice(noTaxPrice);
                    smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(noTaxPrice, item.getEPrice()));
                }
            }
            // 最低售价标识
            smsOrder.setHasLowMinPrice(OrderSpecExpType.include(item.getExpDlvType(),OrderSpecExpType.MINPRICE));
            list.add(smsOrder);
        }
        return list;
    }


    /**
     * 订单数据 查询/导出共用数据数据
     */
    public List<IndCodeEntity> commonInCodeDataHand(List<IndCodeEntity> indCodeEntities) {
        Map<String, String> nameMap = new HashMap<>(indCodeEntities.size());
        String name;
        List<String> noInterceptList = noIntercepterList();
        for (IndCodeEntity smsOrder : indCodeEntities) {

            if (smsOrder.getStatus() != null ) {
                if (noInterceptList.contains(String.valueOf(smsOrder.getStatus()))) {
                    smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
                } else {
                    // 判断是否拦截,若是拦截则显示信用拦截,反之显示订单状态
                    if (smsOrder.getIntercept() != null) {
                        if (smsOrder.getIntercept()) {
                            smsOrder.setStatusName("信用拦截");
                        } else {
                            // 处理状态名称
                            smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
                        }
                    } else {
                        smsOrder.setStatusName(RCVOrderStatusEnum.getName(smsOrder.getStatus()));
                    }
                }
            }

            smsOrder.setMainOrderNo(smsOrder.getRorderNo());

            if (StringUtils.isNotBlank(smsOrder.getEmployeeNo())) {
                // 制单人id
                smsOrder.setCreateId(smsOrder.getEmployeeNo());
                // 制单人名称
                if (!nameMap.containsKey(smsOrder.getEmployeeNo())) {
                    name = opsCommonService.getEmpSaleNameByNo(smsOrder.getEmployeeNo());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(smsOrder.getEmployeeNo(), name);
                    }
                }
                smsOrder.setCreateUserName(nameMap.getOrDefault(smsOrder.getEmployeeNo(), ""));
            }

            // 客户名称
            if (StringUtils.isNotBlank(smsOrder.getCustomerNo())) {
                if (!nameMap.containsKey(smsOrder.getCustomerNo())) {
                    name = opsCommonService.getCustomerNameByNo(smsOrder.getCustomerNo());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(smsOrder.getCustomerNo(), name);
                    }
                }
                smsOrder.setCustomerName(nameMap.get(smsOrder.getCustomerNo()));
            }

            // 客户担当名称
            if (StringUtils.isNotBlank(smsOrder.getEmpSale())) {
                if (!nameMap.containsKey(smsOrder.getEmpSale())) {
                    name = opsCommonService.getEmpSaleNameByNo(smsOrder.getEmpSale());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(smsOrder.getEmpSale(), name);
                    }
                }
                smsOrder.setEmpSaleName(nameMap.get(smsOrder.getEmpSale()));
            }

            // 用户名称
            if (StringUtils.isNotBlank(smsOrder.getUserNo())) {
                if (!nameMap.containsKey(smsOrder.getUserNo())) {
                    name = opsCommonService.getCustomerNameByNo(smsOrder.getUserNo());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(smsOrder.getUserNo(), name);
                    }
                }
                smsOrder.setUserName(nameMap.get(smsOrder.getUserNo()));
            }

            // 最终用户名称
//            if (StringUtils.isNotBlank(smsOrder.getEndUser())) {
//                if (!nameMap.containsKey(smsOrder.getEndUser())) {
//                    name = opsCommonService.getCustomerNameByNo(smsOrder.getEndUser());
//                    if (StringUtils.isNotBlank(name)) {
//                        nameMap.put(smsOrder.getEndUser(), name);
//                    }
//                }
//                smsOrder.setEndUserName(nameMap.get(smsOrder.getEndUser()));
//            }
            smsOrder.setEndUserName(smsOrder.getName());

            // 如果最终用户价格为空 则取价格
            if (smsOrder.getPriceEndUser() == null || smsOrder.getPriceEndUser().compareTo(BigDecimal.ZERO) == 0) {
                smsOrder.setPriceEndUser(smsOrder.getPrice());
            }

            // E率
            if (smsOrder.getNtaxPice() != null && smsOrder.getNtaxPice().compareTo(BigDecimal.ZERO) != 0) {
                smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(smsOrder.getNtaxPice(), smsOrder.getEPrice()));
            } else {
                // 不含税单价
                if (smsOrder.getPrice() == null) {
                    smsOrder.setPrice(BigDecimal.ZERO);
                }
                BigDecimal noTaxPrice = CommonFormulaUtil.calcNoTaxPrice(smsOrder.getPrice(), smsOrder.getTaxRate());
                smsOrder.setDiscount(CommonFormulaUtil.calcDiscount(noTaxPrice, smsOrder.getEPrice()));
            }

            // hl部门名称
            if (StringUtils.isNotBlank(smsOrder.getHlCode())) {
                if (!nameMap.containsKey(smsOrder.getHlCode())) {
                    name = opsCommonService.getDeptNameByNo(smsOrder.getHlCode());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(smsOrder.getHlCode(), name);
                    }
                }
                smsOrder.setHlCode(smsOrder.getHlCode());
                smsOrder.setHlCodeName(nameMap.get(smsOrder.getHlCode()));
            } else {
                if (!nameMap.containsKey(smsOrder.getHrUnitId())) {
                    name = opsCommonService.getDeptNameByNo(smsOrder.getHrUnitId());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(smsOrder.getHrUnitId(), name);
                    }
                }
                smsOrder.setHlCode(smsOrder.getHrUnitId());
                smsOrder.setHlCodeName(nameMap.get(smsOrder.getHrUnitId()));
            }
            // 出库区分类名称
            if (StringUtils.isNotBlank(smsOrder.getStockType()) && StringUtils.isNotBlank(smsOrder.getStockCode())) {
                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(smsOrder.getStockType()) + "[" + smsOrder.getStockCode() + "]");
            } else if (StringUtils.isBlank(smsOrder.getStockType())) {
                smsOrder.setStockTypeName(smsOrder.getStockCode());
            } else {
                smsOrder.setStockTypeName(OrderStockTypeEnum.getTypeName(smsOrder.getStockType()));
            }
        }
        return indCodeEntities;
    }

    /**
     * 交货期查询,导出共用数据处理
     * @return
     */
    public List<PurchaseOrderBean> commonOrderStateDataHand(List<PurchaseOrderBean> list) {
        Map<String, String> nameMap = new HashMap<>(list.size());
        String name;

        // 从字典获取交货期状态配置
        ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(com.smc.smccloud.model.Constants.DICT_CLASSCODE_ORDERSTATE);
        if (!dataCodes.isSuccess() || org.apache.commons.collections4.CollectionUtils.isEmpty(dataCodes.getData())) {
            return null;
        }
        Map<String,String> dictMap = new HashMap<>();
        for (DataCodeVO item : dataCodes.getData()) {
            dictMap.put(item.getCode(),item.getCodeName());
        }

        for (PurchaseOrderBean item : list) {
            // 设置实际出荷日
            item.setPoShipDate(item.getPoFacExpdate());
            // 客户名称
            if (StringUtils.isNotBlank(item.getCustomerNo()) && !nameMap.containsKey(item.getCustomerNo())) {
                name = opsCommonService.getCustomerNameByNo(item.getCustomerNo());
                if (StringUtils.isNotBlank(name)) {
                    nameMap.put(item.getCustomerNo(), name);
                }
            }
            item.setCustomerName(nameMap.get(item.getCustomerNo()));
            // 用户名称
            if (StringUtils.isNotBlank(item.getUserNo()) && !nameMap.containsKey(item.getUserNo())) {
                name = opsCommonService.getCustomerNameByNo(item.getUserNo());
                if (StringUtils.isNotBlank(name)) {
                    nameMap.put(item.getUserNo(), name);
                }
            }
            item.setUserName(nameMap.get(item.getUserNo()));

            // 最终用户
            if (StringUtils.isNotBlank(item.getEndUser()) && !nameMap.containsKey(item.getEndUser())) {
                name = opsCommonService.getCustomerNameByNo(item.getEndUser());
                if (StringUtils.isNotBlank(name)) {
                    nameMap.put(item.getEndUser(), name);
                }
            }
            item.setEndUserName(nameMap.get(item.getEndUser()));

            item.setOrderTypeName(OrderTypeEnum.getCodeName(item.getOrderType()));

            if (item.getIntercept() != null && item.getIntercept() && !isSendOrderStatusForOrderState(Integer.valueOf(item.getStateCode()))) {
                item.setStateCodeName("信用拦截");
                item.setStateDes("信用拦截");
            } else {
                if (StringUtils.isNotBlank(item.getStateCode())) {
                    // item.setStateCodeName(OrderStateEnum.getStateNameByCode(Integer.parseInt(item.getStateCode())));
                    // 设置状态编码
                    String code = String.valueOf(item.getStateCode());
                    item.setStateCodeName(dictMap.getOrDefault(code, code));
                }
            }

            if (StringUtils.isBlank(item.getSupplierCode())) {
                item.setSupplierCode(item.getSupplierNo());
            }

            // 供应商名称
            if (StringUtils.isNotBlank(item.getSupplierCode()) && !nameMap.containsKey(item.getSupplierCode())) {
                name = opsCommonService.getSupplierNameByCode(item.getSupplierCode());
                if (StringUtils.isNotBlank(name)) {
                    nameMap.put(item.getSupplierCode(), name);
                }
            }
            item.setSupplierCodeName(nameMap.get(item.getSupplierCode()));
            // 运输方式名称
            item.setPoTransTypeName(OPSTransTypeEnum.getNameByCode(item.getTransType()));
            // 采购类型名称
            item.setPurchaseTypeName(PurchaseTypeEnum.getName(item.getPurchaseType()));
            // 仓库名称
            if (StringUtils.isNotBlank(item.getWarehouseCode()) && !nameMap.containsKey(item.getWarehouseCode())) {
                name = opsCommonService.getWarehouseNameByCode(item.getWarehouseCode());
                if (StringUtils.isNotBlank(name)) {
                    nameMap.put(item.getWarehouseCode(), name);
                }
            }
            item.setWarehouseCodeName(nameMap.get(item.getWarehouseCode()));

            // hl部门名称
            if (StringUtils.isNotBlank(item.getHlCode())) {
                if (!nameMap.containsKey(item.getHlCode())) {
                    name = opsCommonService.getDeptNameByNo(item.getHlCode());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(item.getHlCode(), name);
                    }
                }
                item.setDeptNo(item.getHlCode());
                item.setDeptName(nameMap.get(item.getHlCode()));
            } else {
                if (!nameMap.containsKey(item.getHrUnitId())) {
                    name = opsCommonService.getDeptNameByNo(item.getHrUnitId());
                    if (StringUtils.isNotBlank(name)) {
                        nameMap.put(item.getHrUnitId(), name);
                    }
                }
                item.setDeptNo(item.getHrUnitId());
                item.setDeptName(nameMap.get(item.getHrUnitId()));
            }
            // add by LiYingChao from bug 9002/8993 in 2022/12/12
            // 订单所属部门
            if (StringUtils.isNotBlank(item.getOrderDeptNo())) {
                if (!nameMap.containsKey(item.getOrderDeptNo())) {
                    String deptNameByNo = opsCommonService.getDeptNameByNo(item.getOrderDeptNo());
                    if (StringUtils.isNotBlank(deptNameByNo)) {
                        nameMap.put(item.getOrderDeptNo(),deptNameByNo);
                    }
                }
                item.setOrderDeptName(nameMap.get(item.getOrderDeptNo()));
            }
            // 特殊纳期翻译
            item.setPoReplyDateStr(PoReplyDateStrEnum.getDescByCode(item.getPoReplyDate()).getCode());

            if (item.getPoReplyDateA() != null) {
                item.setPoReplyDateAStr(PoReplyDateStrEnum.getDescByCode(item.getPoReplyDateA()).getCode());
            }

            if (item.getPoReplyDateB() != null) {
                item.setPoReplyDateBStr(PoReplyDateStrEnum.getDescByCode(item.getPoReplyDateB()).getCode());
            }

            if (item.getPoReplyDateC() != null) {
                item.setPoReplyDateCStr(PoReplyDateStrEnum.getDescByCode(item.getPoReplyDateC()).getCode());
            }

        }
        return list;
    }

    /**
     * 新增是否节假日接口，提供给门户
     *  0: 非节假日，1：节假日
     *
     * @param info
     * @return
     */
    @Override
    public ResultVo<Boolean> getWorkday(InquiryWorkdayCondition info) {
        return inquiryAdapterFeignApi.isWorkday(info);
    }

    /**
     * bug14109 提供给门户不用供应商返回查询原因的清单
     * @param suppily
     * @return
     */
    @Override
    public ResultVo<List<OpsInquiryCodeConfig>> getInquiryReasonBySuppily(String suppily) {
        return inquiryAdapterFeignApi.getReasonBySuppily(suppily);
    }

    @Override
    public ResultVo<List<InquiryCodeConfigBySuppily>> getInquiryReasonBySuppilyBatch(List<String> suppilyList) {
        return inquiryAdapterFeignApi.getReasonBySuppilyBatch(suppilyList);
    }

    /**
     * 提供给门户查询inqb可用的清单
     *
     * @param inqbQueryRequestParam
     * @return
     */
    @Override
    public ResultVo<List<InqbQueryRequestParam>> findInqbUsageList(List<InqbQueryRequestParam> inqbQueryRequestParam) {
        return inqbAdapterFeignApi.findInqbUsageList(inqbQueryRequestParam);
    }


    /**
     * 门户催促新增校验接口，判断传输参数的是否可催促，初始校验
     *
     * @param inqbApplyVerify
     * @return
     */
    @Override
    public ResultVo<InqbApplyVerifyReurn> inqbAddValid(InqbApplyVerifyReurn inqbApplyVerify) {
        return inqbAdapterFeignApi.salesInqbAddValid(inqbApplyVerify);
    }

    @Override
    public ResultVo<List<UpOrderInfoVO>> canUpOrderInfo(List<String> fullOrderNos) {
        if (CollectionUtils.isEmpty(fullOrderNos)) {
            return ResultVo.failure("业务单号不可为空");
        }
        List<UpOrderInfoVO> upOrderInfoVOList = new ArrayList<>();
        for (String fullOrderNo: fullOrderNos) {
            UpOrderInfoVO upOrderInfoVO = new UpOrderInfoVO();

            ResultVo<RcvDetailVO> orderDetail = receiveOrderService.findOrderDetail(fullOrderNo);
            if (orderDetail == null || !orderDetail.isSuccess()) {
                return ResultVo.failure(fullOrderNo+"未能获取订单信息");
            }
            RcvDetailVO rcvDetailVO = orderDetail.getData();

            List<Integer> notSendOrderNoList = new ArrayList<>();
            notSendOrderNoList.add(RCVOrderStatusEnum.RESOLVE.getCode());
            notSendOrderNoList.add(RCVOrderStatusEnum.IN_PURCHASE.getCode());
            notSendOrderNoList.add(RCVOrderStatusEnum.PURCHASE_INTERCEPT.getCode());
            notSendOrderNoList.add(RCVOrderStatusEnum.IN_TRANSFER.getCode());
            notSendOrderNoList.add(RCVOrderStatusEnum.WAITING_DELIVERY.getCode());
            notSendOrderNoList.add(RCVOrderStatusEnum.CREDIT_INTERCEPT.getCode());
            Boolean canUpFlag = false;
            if (notSendOrderNoList.contains(rcvDetailVO.getStatus())) {
                canUpFlag = true;
            }

            // 能否变更货期
            upOrderInfoVO.setCanChangeDeliveryDate(canUpFlag);
            // 能否可以变更普通特发
            upOrderInfoVO.setCanUpSpec(canUpFlag);
            // 能否变更承运商
            upOrderInfoVO.setCanUpCarrier(canUpFlag);
            // 能否变更地址
            upOrderInfoVO.setCanUpAddress(canUpFlag);
            // 是否可变更子项特发
            if (rcvDetailVO.getStatus() != null && StringUtils.isNotBlank(rcvDetailVO.getProdFlag())) {
                upOrderInfoVO.setCanUpSpecItem(canUpSepc(rcvDetailVO.getStatus(), Integer.parseInt(rcvDetailVO.getProdFlag())));
            } else {
                upOrderInfoVO.setCanUpSpecItem(false);
            }

            if (canUpFlag) {
                // 最晚货期
                CommonResult modifyDlvDateLimit = opsOrderFeignApi.getModifyDlvDateLimit(rcvDetailVO.getRorderNo(), rcvDetailVO.getRorderItem());
                if (modifyDlvDateLimit !=null && modifyDlvDateLimit.getData() != null) {
                    upOrderInfoVO.setLatestDeliveryDate((Date) modifyDlvDateLimit.getData());
                }
            }



            if (upOrderInfoVO.getCanUpCarrier()) {
                List<SelectCommonEntitty> list = new ArrayList<>();
                SelectCommonEntitty selectCommonEntitty = new SelectCommonEntitty();
                selectCommonEntitty.setCode(CarrierEnum.SF.getCode());
                selectCommonEntitty.setCodeName(CarrierEnum.SF.getName());

                SelectCommonEntitty selectCommonEntitty2 = new SelectCommonEntitty();
                selectCommonEntitty2.setCode(CarrierEnum.NON.getCode());
                selectCommonEntitty2.setCodeName(CarrierEnum.NON.getName());

                list.add(selectCommonEntitty);
                list.add(selectCommonEntitty2);

                upOrderInfoVO.setCarrierList(list);
            }
            upOrderInfoVOList.add(upOrderInfoVO);
        }
        return ResultVo.success(upOrderInfoVOList);
    }
    @Override
    public ResultVo<List<UpOrderMasterInfoVO>> canUpMasterOrderInfo(List<String> mainOrderNos) {

        if(CollectionUtils.isEmpty(mainOrderNos)) {
            return ResultVo.failure("入参不可为空");
        }

        List<UpOrderMasterInfoVO> masterInfoVOS = new ArrayList<>();

        for(String mainOrderNo : mainOrderNos) {
            LambdaQueryWrapper<RcvDetailDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RcvDetailDO::getRorderNo,mainOrderNo);

            List<RcvDetailDO> rcvDetailDOS = rcvdetailMapper.selectList(lambdaQueryWrapper);
            if (CollectionUtils.isEmpty(rcvDetailDOS)) {
                return ResultVo.failure(mainOrderNo+"未能获取到订单信息");
            }

            UpOrderMasterInfoVO upOrderMasterInfoVO = new UpOrderMasterInfoVO();

            List<Integer> statusList = rcvDetailDOS.stream().map(RcvDetailDO::getStatus).collect(Collectors.toList());
            Boolean canChangeDeliveryModel = isCanChangeDeliveryModel(statusList.stream().map(String::valueOf).collect(Collectors.toList()),
                    getNoCanSendOrderMethod());
            upOrderMasterInfoVO.setCanChangeDeliveryModel(canChangeDeliveryModel);

            if (upOrderMasterInfoVO.getCanChangeDeliveryModel()) {
                // 获取能变更哪些出货方式
                LambdaQueryWrapper<RcvMasterDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(RcvMasterDO::getRorderNo,mainOrderNo);
                RcvMasterDO rcvMasterDO = rcvmasterMapper.selectOne(queryWrapper);
                if (rcvMasterDO != null) {
                    ResultVo<List<OpsDeliveryTypeDO>> deliveryType = findDeliveryType(DeliveryEntireEnum.getName(rcvMasterDO.getDlvEntire()));
                    if (deliveryType != null && deliveryType.isSuccess()) {
                        List<OpsDeliveryTypeDO> data = deliveryType.getData();
                        upOrderMasterInfoVO.setDeliveryTypeVOList(BeanCopyUtil.copyList(data, OpsDeliveryTypeVO.class));
                    }
                }
            }
            masterInfoVOS.add(upOrderMasterInfoVO);
        }


        return ResultVo.success(masterInfoVOS);
    }


    @Override
    public ResultVo<List<OpsInqbCodeConfig>> getInqbSendReason(){
        return inqbAdapterFeignApi.getInqbSendReason();
    }
}
