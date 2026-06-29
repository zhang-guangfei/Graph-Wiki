package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.CstmTypeEnum;
import com.smc.smccloud.core.model.enums.DeliveryPlaceEnum;
import com.smc.smccloud.core.model.enums.OrderTypeEnum;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.ExpdetailMapper;
import com.smc.smccloud.mapper.ExpdetailMapperReadOnly;
import com.smc.smccloud.mapper.ExpdetailMapperReadOnlyMapper;
import com.smc.smccloud.mapper.OpsImpdataformanuMapper;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.expdetail.ExpdetailDO;
import com.smc.smccloud.model.expdetail.ExpdetailRequest;
import com.smc.smccloud.model.expdetail.ExpdetailVO;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author smc
 * @since 2022-01-20
 */
@Slf4j
@Service
public class ExpdetailServiceImpl implements ExpdetailService {

    @Resource
    private ExpdetailMapper expdetailMapper;
    @Resource
    private CommonService commonService;

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Resource
    private ExpdetailMapperReadOnlyMapper expdetailMapperReadOnlyMapper;

    @Resource
    private ExpdetailMapperReadOnly expdetailMapperReadOnly;

    @Resource
    private DictCommonService dictCommonService;
    @Resource
    private EventService eventService;
    @Resource
    private OpsImpdataformanuMapper opsImpdataformanuMapper;

//    @Override
//    public ResultVo<PageInfo<ExpdetailVO>> listExpdetail(ExpdetailRequest request) {
//        log.info("发货查询参数 , params = {}", JSONObject.toJSONString(request));
//        long startTimer = System.currentTimeMillis();
//
//        if (request.getToDate() != null) {
//            request.setStrToDate(DateUtil.dateToDateString(request.getToDate()) + " 23:59:59");
//        }
//        // 起始时间
//        if (request.getFromDate() != null) {
//            request.setStrFromDate(DateUtil.dateToDateString(request.getFromDate()) + " 00:00:00");
//        }
//
//        // 判断权限
//        List<String> employeeAuth = new ArrayList<>();
//        List<String> deptAuth = new ArrayList<>();
//        List<String> customerAuth = new ArrayList<>();
//        List<String> userNoAuth = new ArrayList<>();
//        if (request.getDataAuthoritySearchCondition() != null) {
//            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getUserIdListByDataAuthority())) {
//                employeeAuth = request.getDataAuthoritySearchCondition().getUserIdListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority())) {
//                deptAuth = request.getDataAuthoritySearchCondition().getDeptCodeListByDataAuthority();
////                if (CollectionUtils.isNotEmpty(request.getDeptCodes())) {
////                    deptAuth.addAll(request.getDeptCodes());
////                }
//            }
//            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
//                customerAuth = request.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority();
//            }
//            if (CollectionUtils.isNotEmpty(request.getDataAuthoritySearchCondition().getUserNoListByDataAuthority())) {
//                userNoAuth = request.getDataAuthoritySearchCondition().getUserNoListByDataAuthority();
//            }
//        }
//
//        List<String> finalEmployeeAuth = employeeAuth;
//        List<String> finalDeptAuth = deptAuth;
//        List<String> finalCustomerAuth = customerAuth;
//        List<String> finalUserNoAuth = userNoAuth;
//        List<String> orderType = Arrays.asList("1", "9");
//
//        LambdaQueryWrapper<ExpdetailDO> query = new LambdaQueryWrapper<>();
//        query
//                // .eq(PublicUtil.isNotEmpty(request.getOrderType()), ExpdetailDO::getOrderType, request.getOrderType())
//                // 单据状态
//                .eq(PublicUtil.isNotEmpty(request.getStatus()), ExpdetailDO::getSignStatus, request.getStatus())
//                // 开票号
//                .likeRight(PublicUtil.isNotEmpty(request.getInvoiceNo()), ExpdetailDO::getInvoiceNo, request.getInvoiceNo())
//                // 发货单号
//                .likeRight(PublicUtil.isNotEmpty(request.getDeliveryNo()), ExpdetailDO::getDeliveryNo, request.getDeliveryNo())
//                // 订单号
//                .likeRight(PublicUtil.isNotEmpty(request.getOrderNo()), ExpdetailDO::getOrderFno, request.getOrderNo())
//                // 子订单号
//                .likeRight(PublicUtil.isNotEmpty(request.getOrderFno()), ExpdetailDO::getOrderFno, request.getOrderFno())
//                // 客户代码
//                .eq(PublicUtil.isNotEmpty(request.getCustomerNo()), ExpdetailDO::getCustomerNo, request.getCustomerNo())
//                // 用户代码
//                .eq(PublicUtil.isNotEmpty(request.getUserNo()), ExpdetailDO::getUserNo, request.getUserNo())
//                // 货物条码
//                .eq(PublicUtil.isNotEmpty(request.getBarcode()), ExpdetailDO::getBarcode, request.getBarcode())
//                .in(ExpdetailDO::getOrderType, orderType)
//                // 客户订单号
//                .likeRight(PublicUtil.isNotEmpty(request.getCorderNo()), ExpdetailDO::getCorderNo, request.getCorderNo())
//                // 快递单号
//                .likeRight(PublicUtil.isNotEmpty(request.getExpressNo()), ExpdetailDO::getExpressNo, request.getExpressNo())
//                // 合同订单号
//                .likeRight(PublicUtil.isNotEmpty(request.getOrOrderNo()), ExpdetailDO::getOrOrderNo, request.getOrOrderNo())
//                // .in(CollectionUtils.isNotEmpty(request.getDeptCodes()),ExpdetailDO::getDeptNo,request.getDeptCodes())
//                // 发货日期范围
//                .ge(PublicUtil.isNotEmpty(request.getStrFromDate()), ExpdetailDO::getShipDate, request.getStrFromDate())
//                .le(PublicUtil.isNotEmpty(request.getStrToDate()), ExpdetailDO::getShipDate, request.getStrToDate());
//        if (StringUtils.isNotBlank(request.getWarehouseCode()) && request.getWarehouseCode().startsWith("S")) {
//            // 发货仓库
//            query.likeRight(ExpdetailDO::getWarehouseCode, "S");
//        } else {
//            query.eq(StringUtils.isNotBlank(request.getWarehouseCode()), ExpdetailDO::getWarehouseCode, request.getWarehouseCode());
//        }
//        if (!CollectionUtil.isEmpty(finalDeptAuth) || !CollectionUtil.isEmpty(finalCustomerAuth) ||
//                !CollectionUtil.isEmpty(finalUserNoAuth)) {
//            query.and(wrapper -> wrapper.in(!finalDeptAuth.isEmpty(), ExpdetailDO::getDeptNo, finalDeptAuth)
//                    .or(!finalCustomerAuth.isEmpty(), x -> x.in(ExpdetailDO::getCustomerNo, finalCustomerAuth))
//                    .or(!finalUserNoAuth.isEmpty(), x -> x.in(ExpdetailDO::getUserNo, finalUserNoAuth)));
//        }
//
//        // 按发货日期降序
//        query.orderByDesc(ExpdetailDO::getShipDate);
//
//        PageInfo<ExpdetailDO> info = PageHelper.startPage(request.getPageNum(), request.getPageSize())
//                .doSelectPageInfo(() -> expdetailMapperReadOnlyMapper.selectList(query));
//
//        long curTimer = System.currentTimeMillis();
//        log.info("发货查询数据库耗时(s): " + (curTimer - startTimer) / 1000);
//        PageInfo<ExpdetailVO> pageInfo = BeanCopyUtil.pageDto2Vo(info, ExpdetailVO.class);
//        Map<String, CustomerVO> customerMap = new HashMap<>(pageInfo.getList().size());
//        Map<String, String> nameMap = new HashMap<>();
//        String name;
//        CustomerVO customerInfo;
//
//        for (ExpdetailVO vo : pageInfo.getList()) {
//
//            vo.setOrderQty(vo.getQuantity());
//
//            if (customerMap.containsKey(vo.getCustomerNo())) {
//                vo.setCustomerName(customerMap.get(vo.getCustomerNo()).getName());
//                vo.setCustomerType(CstmTypeEnum.getTypeName(customerMap.get(vo.getCustomerNo()).getCustomerType()));
//            } else {
//                customerInfo = commonService.getCustomerInfoByNo(vo.getCustomerNo());
//                if (customerInfo != null) {
//                    vo.setCustomerName(customerInfo.getName());
//                    vo.setCustomerType(CstmTypeEnum.getTypeName(customerInfo.getCustomerType()));
//                    customerMap.put(vo.getCustomerNo(), customerInfo);
//                    nameMap.put(vo.getCustomerNo(), customerInfo.getName());
//                }
//            }
//
//            if (!nameMap.containsKey(vo.getUserNo())) {
//                name = commonService.getCustomerNameByNo(vo.getUserNo());
//                if (StringUtils.isNotBlank(name)) {
//                    nameMap.put(vo.getUserNo(), name);
//                }
//            }
//            vo.setUserName(nameMap.get(vo.getUserNo()));
//
//            vo.setDlvSite(DeliveryPlaceEnum.getName(vo.getDlvSite()));
//            if (vo.getSignStatus() != null) {
//                vo.setSignStatusName(vo.getSignStatus() == 1 ? "未签收" : "已签收");
//            }
//            if (StringUtils.isNotBlank(vo.getDeptNo())) {
//                vo.setDeptName(commonService.getDeptNameByNo(vo.getDeptNo()));
//            }
//        }
//        long endTimer = System.currentTimeMillis();
//        log.info("发货查询结束耗时(s): " + (endTimer - startTimer) / 1000);
//        return ResultVo.success(pageInfo);
//    }

    @Override
    public ResultVo<PageInfo<ExpdetailVO>> listExpdetailWithCustomer(ExpdetailRequest request) {

        log.info("发货查询参数 , params = {}", JSONObject.toJSONString(request));

        long startTimer = System.currentTimeMillis();

        if (StringUtils.isNotBlank(request.getOperator())) {
            if (request.getOperator().startsWith("ACC") || request.getOperator().startsWith("acc")) {
                return listExpdetailWithCustomerForAgent(request);
            }
        }

        // 起始时间
        if (request.getFromDate() != null) {
            request.setStrFromDate(DateUtil.dateToDateString(request.getFromDate()) + " 00:00:00");
        }

        // endTime
        if (request.getToDate() != null) {
            request.setStrToDate(DateUtil.dateToDateString(request.getToDate()) + " 23:59:59");
        }

        if (StringUtils.isNotBlank(request.getInvoiceNo())) {
            request.setInvoiceNo(request.getInvoiceNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getDeliveryNo())) {
            request.setDeliveryNo(request.getDeliveryNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getOrderNo())) {
            request.setOrderNo(request.getOrderNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getCorderNo())) {
            request.setCorderNo(request.getCorderNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getCmodelNo())) {
            request.setCmodelNo(request.getCmodelNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getExpressNo())) {
            request.setExpressNo(request.getExpressNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getOrOrderNo())) {
            request.setOrOrderNo(request.getOrOrderNo() + "%");
        }

        if (StringUtils.isNotBlank(request.getModelNo())) {
            request.setModelNo(request.getModelNo()+"%");
        }
        // update by LiyingChao from bug 9298 in 2023-1-9
        if (StringUtils.isNotBlank(request.getWarehouseCode())) {
            ResultVo<List<String>> resultVo = dictCommonService.getWarehouseCodeByWarehouseType(request.getWarehouseCode());
            if (!resultVo.isSuccess() || CollectionUtils.isEmpty(resultVo.getData())) {
                List<String> warehouseCodeList = new ArrayList<>();
                warehouseCodeList.add(request.getWarehouseCode());
                request.setWarehouseCodes(warehouseCodeList);
            } else {
                request.setWarehouseCodes(resultVo.getData());
            }
        }

//        if (StringUtils.isNotBlank(request.getWarehouseCode()) && request.getWarehouseCode().startsWith("S")) {
//            request.setWarehouseCode("S%");
//        }

        List<String> orderType = Arrays.asList("1", "9");

        if (CollectionUtils.isNotEmpty(request.getOrderType())) {
            orderType = request.getOrderType();
        }

        // 权限集合
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

        PageInfo<ExpdetailVO> pageInfo = null;
        try {
            List<String> finalOrderType = orderType;
            pageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize())
                    .doSelectPageInfo(() -> expdetailMapperReadOnly.listExpdetail(request, finalOrderType, finalUserAuth, finalDeptAuth, finalCustomerAuth, finalInCodeAuth));
        } catch (Exception e) {
            if (e.getMessage().substring(25, 100).contains("Timeout")) {
                return ResultVo.failure("查询超时");
            } else {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }


        long curTimer = System.currentTimeMillis();
        log.info("发货查询数据库耗时(s): " + (curTimer - startTimer) / 1000);

        if (CollectionUtils.isEmpty(pageInfo.getList())) {
            return ResultVo.success(pageInfo);
        }

        Map<String, CustomerVO> customerMap = new HashMap<>(pageInfo.getList().size());
        Map<String, String> nameMap = new HashMap<>();
        String name;
        CustomerVO customerInfo;

        // -----------------  Add by LiYingChao from 禅道bug8376 in 2022-10-19  --------------
        // 从字典表获取仓库分类
        ResultVo<List<DataCodeVO>> dataCodes = dictDataServiceFeignApi.getDataCodes("4011");
        Map<String, String> map = new HashMap<>();
        if (dataCodes.isSuccess() && dataCodes.getData() != null) {
            for (DataCodeVO item : dataCodes.getData()) {
                map.put(item.getCode(), item.getCodeName());
            }
        }
        for (ExpdetailVO vo : pageInfo.getList()) {

            if (vo.getOrderType() != null) {
                vo.setOrderTypeName(OrderTypeEnum.getCodeName(String.valueOf(vo.getOrderType())));
            }
            vo.setOrderQty(vo.getQuantity());

            if (customerMap.containsKey(vo.getCustomerNo())) {
                vo.setCustomerName(customerMap.get(vo.getCustomerNo()).getName());
                vo.setCustomerType(CstmTypeEnum.getTypeName(customerMap.get(vo.getCustomerNo()).getCustomerType()));
            } else {
                customerInfo = commonService.getCustomerInfoByNo(vo.getCustomerNo());
                if (customerInfo != null) {
                    vo.setCustomerName(customerInfo.getName());
                    vo.setCustomerType(CstmTypeEnum.getTypeName(customerInfo.getCustomerType()));
                    customerMap.put(vo.getCustomerNo(), customerInfo);
                    nameMap.put(vo.getCustomerNo(), customerInfo.getName());
                }
            }

            // -----------------  Add by LiYingChao from 禅道bug8376 in 2022-10-19  --------------
            if (StringUtils.isNotBlank(vo.getWarehouseCode())) {
                vo.setWarehouseCodeName(map.get(conventWareHouseCode(vo.getWarehouseCode())));
            }

            if (!nameMap.containsKey(vo.getUserNo())) {
                name = commonService.getCustomerNameByNo(vo.getUserNo());
                if (StringUtils.isNotBlank(name)) {
                    nameMap.put(vo.getUserNo(), name);
                }
            }
            vo.setUserName(nameMap.get(vo.getUserNo()));

            if (!nameMap.containsKey(vo.getEndUser())) {
                name = commonService.getCustomerNameByNo(vo.getEndUser());
                if (StringUtils.isNotBlank(name)) {
                    nameMap.put(vo.getEndUser(), name);
                }
            }
            vo.setEndUserName(nameMap.get(vo.getEndUser()));

            vo.setDlvSite(DeliveryPlaceEnum.getName(vo.getDlvSite()));
            if (vo.getSignStatus() != null) {
                vo.setSignStatusName(vo.getSignStatus() == 1 ? "未签收" : "已签收");
            }

            if (StringUtils.isNotBlank(vo.getHlCode())) {
                vo.setDeptNo(vo.getHlCode());
                vo.setDeptName(commonService.getDeptNameByNo(vo.getHlCode()));
            } else if (StringUtils.isNotBlank(vo.getHrUnitId())) {
                vo.setDeptNo(vo.getHrUnitId());
                vo.setDeptName(commonService.getDeptNameByNo(vo.getHrUnitId()));
            }
        }
        long endTimer = System.currentTimeMillis();
        log.info("发货查询结束耗时(s): " + (endTimer - startTimer) / 1000);
        return ResultVo.success(pageInfo);
    }

    public String conventWareHouseCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        if (code.startsWith("WT")) {
            return "WT";
        }
        if (code.startsWith("S") && !code.equals("SCZ")) {
            return "SUB";
        }
        return code;
    }

    @Override
    public ResultVo<PageInfo<ExpdetailVO>> listExpdetailWithCustomerForAgent(ExpdetailRequest request) {
        log.info("发货查询参数 , params = {}", JSONObject.toJSONString(request));

        long startTimer = System.currentTimeMillis();

        PageInfo<ExpdetailVO> pageInfo = null;
        if (CollectionUtils.isEmpty(request.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
            pageInfo = new PageInfo<>();
            return ResultVo.success(pageInfo);
        }

        // 起始时间
        if (request.getFromDate() != null) {
            request.setStrFromDate(DateUtil.dateToDateString(request.getFromDate()) + " 00:00:00");
        }

        // endTime
        if (request.getToDate() != null) {
            request.setStrToDate(DateUtil.dateToDateString(request.getToDate()) + " 23:59:59");
        }

        if (StringUtils.isNotBlank(request.getInvoiceNo())) {
            request.setInvoiceNo(request.getInvoiceNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getDeliveryNo())) {
            request.setDeliveryNo(request.getDeliveryNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getOrderNo())) {
            request.setOrderNo(request.getOrderNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getCorderNo())) {
            request.setCorderNo(request.getCorderNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getExpressNo())) {
            request.setExpressNo(request.getExpressNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getOrOrderNo())) {
            request.setOrOrderNo(request.getOrOrderNo() + "%");
        }

        if (StringUtils.isNotBlank(request.getModelNo())) {
            request.setModelNo(request.getModelNo() + "%");
        }
        // update by LiyingChao from bug 9298 in 2023-1-9
        if (StringUtils.isNotBlank(request.getWarehouseCode())) {
            ResultVo<List<String>> resultVo = dictCommonService.getWarehouseCodeByWarehouseType(request.getWarehouseCode());
            if (!resultVo.isSuccess() || CollectionUtils.isEmpty(resultVo.getData())) {
                List<String> warehouseCodeList = new ArrayList<>();
                warehouseCodeList.add(request.getWarehouseCode());
                request.setWarehouseCodes(warehouseCodeList);
            } else {
                request.setWarehouseCodes(resultVo.getData());
            }
        }

//        if (StringUtils.isNotBlank(request.getWarehouseCode()) && request.getWarehouseCode().startsWith("S")) {
//            request.setWarehouseCode("S%");
//        }

        List<String> orderType = Arrays.asList("1", "9");

        try {
            pageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize())
                    .doSelectPageInfo(() -> expdetailMapperReadOnly.listExpdetailForAgent(request, orderType));
        } catch (Exception e) {
            if (e.getMessage().substring(25, 100).contains("Timeout")) {
                return ResultVo.failure("查询超时");
            } else {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }


        long curTimer = System.currentTimeMillis();
        log.info("发货查询数据库耗时(s): " + (curTimer - startTimer) / 1000);

        if (CollectionUtils.isEmpty(pageInfo.getList())) {
            return ResultVo.success(pageInfo);
        }

        Map<String, CustomerVO> customerMap = new HashMap<>(pageInfo.getList().size());
        Map<String, String> nameMap = new HashMap<>();
        String name;
        CustomerVO customerInfo;

        // -----------------  Add by LiYingChao from 禅道bug8376 in 2022-10-19  --------------
        // 从字典表获取仓库分类
        ResultVo<List<DataCodeVO>> dataCodes = dictDataServiceFeignApi.getDataCodes("4011");
        Map<String, String> map = new HashMap<>();
        if (dataCodes.isSuccess() && dataCodes.getData() != null) {
            for (DataCodeVO item : dataCodes.getData()) {
                map.put(item.getCode(), item.getCodeName());
            }
        }
        // ------------------------------------------------------------------------------
        for (ExpdetailVO vo : pageInfo.getList()) {

            vo.setOrderQty(vo.getQuantity());

            if (customerMap.containsKey(vo.getCustomerNo())) {
                vo.setCustomerName(customerMap.get(vo.getCustomerNo()).getName());
                vo.setCustomerType(CstmTypeEnum.getTypeName(customerMap.get(vo.getCustomerNo()).getCustomerType()));
            } else {
                customerInfo = commonService.getCustomerInfoByNo(vo.getCustomerNo());
                if (customerInfo != null) {
                    vo.setCustomerName(customerInfo.getName());
                    vo.setCustomerType(CstmTypeEnum.getTypeName(customerInfo.getCustomerType()));
                    customerMap.put(vo.getCustomerNo(), customerInfo);
                    nameMap.put(vo.getCustomerNo(), customerInfo.getName());
                }
            }

            if (!nameMap.containsKey(vo.getUserNo())) {
                name = commonService.getCustomerNameByNo(vo.getUserNo());
                if (StringUtils.isNotBlank(name)) {
                    nameMap.put(vo.getUserNo(), name);
                }
            }
            vo.setUserName(nameMap.get(vo.getUserNo()));

            // -----------------  Add by LiYingChao from  禅道bug8376 in 2022-10-19  --------------
            if (StringUtils.isNotBlank(vo.getWarehouseCode())) {
                vo.setWarehouseCodeName(map.get(conventWareHouseCode(vo.getWarehouseCode())));
            }

            if (!nameMap.containsKey(vo.getEndUser())) {
                name = commonService.getCustomerNameByNo(vo.getEndUser());
                if (StringUtils.isNotBlank(name)) {
                    nameMap.put(vo.getEndUser(), name);
                }
            }
            vo.setEndUserName(nameMap.get(vo.getEndUser()));

            vo.setDlvSite(DeliveryPlaceEnum.getName(vo.getDlvSite()));
            if (vo.getSignStatus() != null) {
                vo.setSignStatusName(vo.getSignStatus() == 1 ? "未签收" : "已签收");
            }

            if (StringUtils.isNotBlank(vo.getHlCode())) {
                vo.setDeptNo(vo.getHlCode());
                vo.setDeptName(commonService.getDeptNameByNo(vo.getHlCode()));
            } else if (StringUtils.isNotBlank(vo.getHrUnitId())) {
                vo.setDeptNo(vo.getHrUnitId());
                vo.setDeptName(commonService.getDeptNameByNo(vo.getHrUnitId()));
            }
        }
        long endTimer = System.currentTimeMillis();
        log.info("发货查询结束耗时(s): " + (endTimer - startTimer) / 1000);
        return ResultVo.success(pageInfo);
    }


    @Override
    public ResultVo<ExpdetailVO> findOne(Long id) {
        QueryWrapper<ExpdetailDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        ExpdetailDO expdetailDO = expdetailMapper.selectOne(queryWrapper);
        if (expdetailDO != null) {
            return ResultVo.success(BeanCopyUtil.copy(expdetailDO, ExpdetailVO.class));
        }
        return ResultVo.failure("未查到数据");
    }

    @Override
    public ResultVo<String> updateExpDetail(ExpdetailVO expdetailVO) {
        if (expdetailVO == null) {
            return ResultVo.failure("更新失败");
        }
        ExpdetailDO expdetailDO = BeanCopyUtil.copy(expdetailVO, ExpdetailDO.class);
        try {
            expdetailDO.setUpdateTime(new Date());
            expdetailMapper.updateById(expdetailDO);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (PublicUtil.isEmpty(expdetailVO.getId())) {
//            if (PublicUtil.isEmpty(expdetailVO.getOrderNo()) || PublicUtil.isEmpty(expdetailVO.getItemNo())) {
//                return ResultVo.failure("更新失败");
//            }
//            QueryWrapper<ExpdetailDO> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("order_no",expdetailVO.getOrderNo());
//            queryWrapper.eq("item_no",expdetailVO.getItemNo());
//            ExpdetailDO expdetailDO = expdetailMapper.selectOne(queryWrapper);
//            if ( expdetailDO != null ) {
//                expdetailVO.setId(expdetailDO.getId());
//                expdetailVO.setOptCode(2);
//                expdetailVO.setUpdateTime(new Date());
//                ExpdetailDO expdetailDO1 = BeanCopyUtil.copy(expdetailVO, ExpdetailDO.class);
//                try {
//                    expdetailMapper.updateById(expdetailDO1);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            ExpdetailDO expdetailDO = BeanCopyUtil.copy(expdetailVO, ExpdetailDO.class);
//            try {
//                expdetailDO.setUpdateTime(new Date());
//                expdetailMapper.updateById(expdetailDO);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        return ResultVo.success("更新成功");
    }

    @Override
    public ResultVo<List<ExpdetailVO>> findExpDetailByOrderType(ExpdetailRequest request) {
        if (CollectionUtils.isEmpty(request.getOrderType()) || request.getOptCode() == null) {
            return ResultVo.failure("订单类型参数不可为空.");
        }

        LambdaQueryWrapper<ExpdetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(ExpdetailDO::getOrderType, request.getOrderType())
                .eq(ExpdetailDO::getOptCode, request.getOptCode())
                .ge(StringUtils.isNotBlank(request.getStrFromDate()), ExpdetailDO::getShipDate, request.getStrFromDate());
        List<ExpdetailDO> expdetailDOS = expdetailMapper.selectList(queryWrapper);
        if (expdetailDOS.isEmpty()) {
            return ResultVo.failure("暂无数据");
        }
        return ResultVo.success(BeanCopyUtil.copyList(expdetailDOS, ExpdetailVO.class));
    }

    @Override
    public ResultVo<ExpdetailVO> listExpdetailByOrderNo(String orderNo) {
        if (PublicUtil.isEmpty(orderNo)) {
            return ResultVo.failure();
        }
        LambdaQueryWrapper<ExpdetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExpdetailDO::getOrderFno, orderNo);

        ExpdetailDO expdetailDO = expdetailMapper.selectOne(queryWrapper);
        if (expdetailDO == null) {
            return ResultVo.failure("未查询到发货数据");
        }
        ExpdetailVO expdetailVO = BeanCopyUtil.copy(expdetailDO, ExpdetailVO.class);

        return ResultVo.success(expdetailVO);
    }


    @Override
    public ResultVo<List<ExpdetailVO>> exportExpdetail(ExpdetailRequest request) {
        log.info("发货查询导出参数 , params = {}", JSONObject.toJSONString(request));

        long startTimer = System.currentTimeMillis();

        List<String> orderType = Arrays.asList("1", "9");

        if (StringUtils.isNotBlank(request.getOperator())) {
            if (request.getOperator().startsWith("ACC") || request.getOperator().startsWith("acc")) {
                return exportExpdetailForAgent(request);
            }
        }

        // 起始时间
        if (request.getFromDate() != null) {
            request.setStrFromDate(DateUtil.dateToDateString(request.getFromDate()) + " 00:00:00");
        }

        // endTime
        if (request.getToDate() != null) {
            request.setStrToDate(DateUtil.dateToDateString(request.getToDate()) + " 23:59:59");
        }

        if (StringUtils.isNotBlank(request.getInvoiceNo())) {
            request.setInvoiceNo(request.getInvoiceNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getDeliveryNo())) {
            request.setDeliveryNo(request.getDeliveryNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getOrderNo())) {
            request.setOrderNo(request.getOrderNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getCorderNo())) {
            request.setCorderNo(request.getCorderNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getExpressNo())) {
            request.setExpressNo(request.getExpressNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getOrOrderNo())) {
            request.setOrOrderNo(request.getOrOrderNo() + "%");
        }

        // update by LiyingChao from bug 9298 in 2023-1-9
        if (StringUtils.isNotBlank(request.getWarehouseCode())) {
            ResultVo<List<String>> resultVo = dictCommonService.getWarehouseCodeByWarehouseType(request.getWarehouseCode());
            if (!resultVo.isSuccess() || CollectionUtils.isEmpty(resultVo.getData())) {
                List<String> warehouseCodeList = new ArrayList<>();
                warehouseCodeList.add(request.getWarehouseCode());
                request.setWarehouseCodes(warehouseCodeList);
            } else {
                request.setWarehouseCodes(resultVo.getData());
            }
        }

//        if (StringUtils.isNotBlank(request.getWarehouseCode()) && request.getWarehouseCode().startsWith("S")) {
//            request.setWarehouseCode("S%");
//        }

        // 权限集合
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

        List<ExpdetailVO> list = null;
        try {
            list = expdetailMapperReadOnly.exportExpdetail(request, orderType, finalUserAuth, finalDeptAuth, finalCustomerAuth, finalInCodeAuth);
        } catch (Exception e) {
            if (e.getMessage().substring(25, 100).contains("Timeout")) {
                return ResultVo.failure("查询超时");
            } else {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        long curTimer = System.currentTimeMillis();
        log.info("发货导出查询数据库耗时(s): " + (curTimer - startTimer) / 1000);

        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.success(list);
        }

        Map<String, CustomerVO> customerMap = new HashMap<>(list.size());
        Map<String, String> nameMap = new HashMap<>();
        String name;
        CustomerVO customerInfo;

        // 从字典表获取仓库分类
        ResultVo<List<DataCodeVO>> dataCodes = dictDataServiceFeignApi.getDataCodes("4011");
        Map<String, String> map = new HashMap<>();
        if (dataCodes.isSuccess() && dataCodes.getData() != null) {
            for (DataCodeVO item : dataCodes.getData()) {
                map.put(item.getCode(), item.getCodeName());
            }
        }

        for (ExpdetailVO vo : list) {

            vo.setOrderQty(vo.getQuantity());

            if (customerMap.containsKey(vo.getCustomerNo())) {
                vo.setCustomerName(customerMap.get(vo.getCustomerNo()).getName());
                vo.setCustomerType(CstmTypeEnum.getTypeName(customerMap.get(vo.getCustomerNo()).getCustomerType()));
            } else {
                customerInfo = commonService.getCustomerInfoByNo(vo.getCustomerNo());
                if (customerInfo != null) {
                    vo.setCustomerName(customerInfo.getName());
                    vo.setCustomerType(CstmTypeEnum.getTypeName(customerInfo.getCustomerType()));
                    customerMap.put(vo.getCustomerNo(), customerInfo);
                    nameMap.put(vo.getCustomerNo(), customerInfo.getName());
                }
            }

            if (StringUtils.isNotBlank(vo.getWarehouseCode())) {
                vo.setWarehouseCodeName(map.get(conventWareHouseCode(vo.getWarehouseCode())));
            }

            if (!nameMap.containsKey(vo.getUserNo())) {
                name = commonService.getCustomerNameByNo(vo.getUserNo());
                if (StringUtils.isNotBlank(name)) {
                    nameMap.put(vo.getUserNo(), name);
                }
            }
            vo.setUserName(nameMap.get(vo.getUserNo()));

            if (!nameMap.containsKey(vo.getEndUser())) {
                name = commonService.getCustomerNameByNo(vo.getEndUser());
                if (StringUtils.isNotBlank(name)) {
                    nameMap.put(vo.getEndUser(), name);
                }
            }
            vo.setEndUserName(nameMap.get(vo.getEndUser()));

            vo.setDlvSite(DeliveryPlaceEnum.getName(vo.getDlvSite()));
            if (vo.getSignStatus() != null) {
                vo.setSignStatusName(vo.getSignStatus() == 1 ? "未签收" : "已签收");
            }

            if (StringUtils.isNotBlank(vo.getHlCode())) {
                vo.setDeptNo(vo.getHlCode());
                vo.setDeptName(commonService.getDeptNameByNo(vo.getHlCode()));
            } else if (StringUtils.isNotBlank(vo.getHrUnitId())) {
                vo.setDeptNo(vo.getHrUnitId());
                vo.setDeptName(commonService.getDeptNameByNo(vo.getHrUnitId()));
            }
        }
        long endTimer = System.currentTimeMillis();
        log.info("发货导出结束耗时(s): " + (endTimer - startTimer) / 1000);
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<List<ExpdetailVO>> exportExpdetailForAgent(ExpdetailRequest request) {
        log.info("发货查询导出参数 , params = {}", JSONObject.toJSONString(request));

        long startTimer = System.currentTimeMillis();

        if (CollectionUtils.isEmpty(request.getDataAuthoritySearchCondition().getCustomerCodeListByDataAuthority())) {
            return ResultVo.success(new ArrayList<>());
        }

        // 起始时间
        if (request.getFromDate() != null) {
            request.setStrFromDate(DateUtil.dateToDateString(request.getFromDate()) + " 00:00:00");
        }

        // endTime
        if (request.getToDate() != null) {
            request.setStrToDate(DateUtil.dateToDateString(request.getToDate()) + " 23:59:59");
        }

        if (StringUtils.isNotBlank(request.getInvoiceNo())) {
            request.setInvoiceNo(request.getInvoiceNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getDeliveryNo())) {
            request.setDeliveryNo(request.getDeliveryNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getOrderNo())) {
            request.setOrderNo(request.getOrderNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getCorderNo())) {
            request.setCorderNo(request.getCorderNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getExpressNo())) {
            request.setExpressNo(request.getExpressNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getOrOrderNo())) {
            request.setOrOrderNo(request.getOrOrderNo() + "%");
        }

        // update by LiyingChao from bug 9298 in 2023-1-9
        if (StringUtils.isNotBlank(request.getWarehouseCode())) {
            ResultVo<List<String>> resultVo = dictCommonService.getWarehouseCodeByWarehouseType(request.getWarehouseCode());
            if (!resultVo.isSuccess() || CollectionUtils.isEmpty(resultVo.getData())) {
                List<String> warehouseCodeList = new ArrayList<>();
                warehouseCodeList.add(request.getWarehouseCode());
                request.setWarehouseCodes(warehouseCodeList);
            } else {
                request.setWarehouseCodes(resultVo.getData());
            }
        }

//        if (StringUtils.isNotBlank(request.getWarehouseCode()) && request.getWarehouseCode().startsWith("S")) {
//            request.setWarehouseCode("S%");
//        }

        List<String> orderType = Arrays.asList("1", "9");

        List<ExpdetailVO> list = null;
        try {
            list = expdetailMapperReadOnly.exportExpdetailForAgent(request, orderType);
        } catch (Exception e) {
            if (e.getMessage().substring(25, 100).contains("Timeout")) {
                return ResultVo.failure("查询超时");
            } else {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        long curTimer = System.currentTimeMillis();
        log.info("发货导出查询数据库耗时(s): " + (curTimer - startTimer) / 1000);

        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.success(list);
        }

        Map<String, CustomerVO> customerMap = new HashMap<>(list.size());
        Map<String, String> nameMap = new HashMap<>();
        String name;
        CustomerVO customerInfo;

        // 从字典表获取仓库分类
        ResultVo<List<DataCodeVO>> dataCodes = dictDataServiceFeignApi.getDataCodes("4011");
        Map<String, String> map = new HashMap<>();
        if (dataCodes.isSuccess() && dataCodes.getData() != null) {
            for (DataCodeVO item : dataCodes.getData()) {
                map.put(item.getCode(), item.getCodeName());
            }
        }

        for (ExpdetailVO vo : list) {

            vo.setOrderQty(vo.getQuantity());

            if (customerMap.containsKey(vo.getCustomerNo())) {
                vo.setCustomerName(customerMap.get(vo.getCustomerNo()).getName());
                vo.setCustomerType(CstmTypeEnum.getTypeName(customerMap.get(vo.getCustomerNo()).getCustomerType()));
            } else {
                customerInfo = commonService.getCustomerInfoByNo(vo.getCustomerNo());
                if (customerInfo != null) {
                    vo.setCustomerName(customerInfo.getName());
                    vo.setCustomerType(CstmTypeEnum.getTypeName(customerInfo.getCustomerType()));
                    customerMap.put(vo.getCustomerNo(), customerInfo);
                    nameMap.put(vo.getCustomerNo(), customerInfo.getName());
                }
            }

            if (StringUtils.isNotBlank(vo.getWarehouseCode())) {
                vo.setWarehouseCodeName(map.get(conventWareHouseCode(vo.getWarehouseCode())));
            }

            if (!nameMap.containsKey(vo.getUserNo())) {
                name = commonService.getCustomerNameByNo(vo.getUserNo());
                if (StringUtils.isNotBlank(name)) {
                    nameMap.put(vo.getUserNo(), name);
                }
            }
            vo.setUserName(nameMap.get(vo.getUserNo()));

            if (!nameMap.containsKey(vo.getEndUser())) {
                name = commonService.getCustomerNameByNo(vo.getEndUser());
                if (StringUtils.isNotBlank(name)) {
                    nameMap.put(vo.getEndUser(), name);
                }
            }
            vo.setEndUserName(nameMap.get(vo.getEndUser()));

            vo.setDlvSite(DeliveryPlaceEnum.getName(vo.getDlvSite()));
            if (vo.getSignStatus() != null) {
                vo.setSignStatusName(vo.getSignStatus() == 1 ? "未签收" : "已签收");
            }

            if (StringUtils.isNotBlank(vo.getHlCode())) {
                vo.setDeptNo(vo.getHlCode());
                vo.setDeptName(commonService.getDeptNameByNo(vo.getHlCode()));
            } else if (StringUtils.isNotBlank(vo.getHrUnitId())) {
                vo.setDeptNo(vo.getHrUnitId());
                vo.setDeptName(commonService.getDeptNameByNo(vo.getHrUnitId()));
            }
        }
        long endTimer = System.currentTimeMillis();
        log.info("发货导出结束耗时(s): " + (endTimer - startTimer) / 1000);
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<String> callExpExpdetailForGZ() {
        expdetailMapper.callExpExpdetailForGZ();
        return ResultVo.success("成功");
    }

    @Override
    public ResultVo<String> updateExpdetailOptCodeById(Long id, String optCode) {
        if (id == null || StringUtils.isBlank(optCode)) {
            return ResultVo.failure("id与状态不能为空");
        }
        LambdaUpdateWrapper<ExpdetailDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ExpdetailDO::getUpdateTime, new Date()).set(ExpdetailDO::getOptCode, 2).eq(ExpdetailDO::getId, id);
        expdetailMapper.update(null, updateWrapper);
        return ResultVo.success("更新成功");
    }

}
