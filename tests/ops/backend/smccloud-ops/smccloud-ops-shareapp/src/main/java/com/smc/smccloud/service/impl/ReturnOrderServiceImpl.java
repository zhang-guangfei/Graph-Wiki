package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.inventory.CreInvMoveForReturnOrderDto;
import com.sales.ops.dto.inventory.WmWTDoConfirmDto;
import com.sales.ops.dto.inventory.WmWTDoItemConfirmDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.sales.ops.feign.OpsWmFeignApi;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.enums.OrderTypeEnum;
import com.smc.smccloud.core.model.enums.SampleBalApplyHandStatusEnum;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.csstock.CsExpdetailMapper;
import com.smc.smccloud.mapper.csstock.CsImpdataMapper;
import com.smc.smccloud.mapper.returnorder.ReturnOrderMapper;
import com.smc.smccloud.mapper.salesInvoice.SalesInvoiceMidInfoMapper;
import com.smc.smccloud.mapper.sampleorder.SampleBalApplyMapper;
import com.smc.smccloud.mapper.sampleorder.SampleOrderApplyMapper;
import com.smc.smccloud.mapper.sampleorder.SamplebalMapper;
import com.smc.smccloud.mapper.warehouse.OpsWarehouseSalesBranchConfigMapper;
import com.smc.smccloud.model.CalTransDayEntity;
import com.smc.smccloud.model.HROrganizationVO;
import com.smc.smccloud.model.OpsEventBusDO;
import com.smc.smccloud.model.OpsOrderAssignResultDO;
import com.smc.smccloud.model.csstock.CsExpdetailDO;
import com.smc.smccloud.model.csstock.CsImpdataDO;
import com.smc.smccloud.model.csstock.WarehouseDO;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.enums.ReturnOrderStatusEnum;
import com.smc.smccloud.model.enums.SampleBalOptCodeEnum;
import com.smc.smccloud.model.invoice.SalesInvoiceDTO;
import com.smc.smccloud.model.invoice.SalesInvoiceRequest;
import com.smc.smccloud.model.opsreturn.RefuseRcvRequest;
import com.smc.smccloud.model.opsreturn.ReturnOrderBackCallVO;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.receiveorder.RcvDetailVO;
import com.smc.smccloud.model.receiveorder.RcvMasterVO;
import com.smc.smccloud.model.receiveorder.RcvOrderViewVO;
import com.smc.smccloud.model.returnorder.*;
import com.smc.smccloud.model.salesinvoice.SalesInvoiceMidInfoDO;
import com.smc.smccloud.model.sampleorder.SampleBalApplyDO;
import com.smc.smccloud.model.sampleorder.SamplebalDO;
import com.smc.smccloud.model.trans.TransOrderCancelDTO;
import com.smc.smccloud.model.warehouse.WareHouseInfo;
import com.smc.smccloud.model.warehouse.WarehouseQueryDTO;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import com.smc.smccloud.service.*;
import com.smc.smccloud.utils.JasperHelper;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author smc
 * @since 2021-11-06
 */
@Service
@Slf4j
public class ReturnOrderServiceImpl extends ServiceImpl<ReturnOrderMapper, ReturnOrderDO> implements ReturnOrderService {

    @Resource
    private ReturnOrderMapper returnOrderMapper;
    @Resource
    private CsImpdataMapper csImpdataMapper;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private JavaMailSenderImpl javaMailSender;
    @Resource
    private ReceiveOrderServiceFeignApi receiveOrderServiceFeignApi;
    @Resource
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;
    @Resource
    private SalesInvoiceMidInfoMapper salesInvoiceMidInfoMapper;
    @Resource
    private SalesInvoiceService salesInvoiceService;
    @Resource
    private CreateTokenForOtherServer createTokenForOtherServer;
    @Resource
    private SampleBalService sampleBalService;
    @Resource
    private SampleOrderApplyMapper sampleOrderApplyMapper;
    @Resource
    private CommonService commonService;
    @Resource
    private OpsWmFeignApi opsWmFeignApi;
    @Resource
    private SamplebalMapper samplebalMapper;
    @Resource
    private CsExpdetailMapper csExpdetailMapper;
    @Resource
    private OpsWarehouseSalesBranchConfigMapper opsWarehouseSalesBranchConfigMapper;
    @Resource
    private CsStockApplyService csStockApplyService;
    @Resource
    private HttpServletResponse response;
    @Resource
    private TransStockFeignApi transStockFeignApi;
    @Resource
    private OpsWarehouseService opsWarehouseService;

    @Resource
    private SampleBalApplyMapper sampleBalApplyMapper;

    @Resource
    private OpsOrderAssignResultService opsOrderAssignResultService;

    @Resource
    private OrderLogFeignApi orderLogFeignApi;

    @Resource
    private RedisManager redisManager;

    @Resource
    private OpsCommonService opsCommonService;

    @Value("${menhu.url}")
    private String menHuUrl;
    @Value("${file.base}")
    private String serverPath;
    @Resource
    private EventService eventService;

    @Resource
    private RedissonUtil redissonUtil;

    /**
     * 退货申请(从门户)
     */
    @Override
    @DS("sharedb")
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> saveReturnOrder(List<ReturnOrderVO> list) {

        log.info("提交退货订单 " + list.size() + " 项");
        log.info("退货数据: " + JSONObject.toJSONString(list));
        log.info("开始执行添加退货单==>");
        long l = System.currentTimeMillis();
        if (list.isEmpty()) {
            return ResultVo.failure("参数列表为空,新增失败");
        }

        String applyNo = list.get(0).getApplyNo();
        if (StringUtils.isBlank(applyNo)) {
            return ResultVo.failure("申请号为空.");
        }

        String lockKey = "ops:redisson:return_order:"+applyNo;

        String requestWarehouseCode = list.get(0).getRequestWarehouseCode();
        WarehouseDO warehouseDO = opsWarehouseService.getWareHouseInfoByCode(requestWarehouseCode);

        if (warehouseDO == null ) {
            return ResultVo.failure(requestWarehouseCode+"仓库不存在");
        }

        // bug11099  增加退货能力校验
        if (warehouseDO.getReturnableFlag() == null || warehouseDO.getReturnableFlag() != 1) {
            log.error("申请" + applyNo + ":" + warehouseDO.getWarehouseName() + "没有退货能力");
            return ResultVo.failure("申请" + applyNo + ":" + warehouseDO.getWarehouseName() + "没有退货能力");
        }

        String faileReason = "";
        List<String> orderNoList = new ArrayList<>();
        int num = 1;
        try {

            // 加锁 过期时间20分钟
            redissonUtil.lock(lockKey, 60*20);

            LambdaQueryWrapper<ReturnOrderDO> queryReturnOrder;
            ReturnOrderDO returnOrderDO;
            ResultVo<RcvDetailVO> orderDetail;
            // add by LiYingChao from bugId 8421 in 20221103
            // 根据退货仓库获取退货收货人/电话
//            String requestWarehouseCode = list.get(0).getRequestWarehouseCode();
            if (StringUtils.isBlank(requestWarehouseCode)) {
                return ResultVo.failure("要求退货仓库不可为空");
            }
            LambdaQueryWrapper<ReturnOrderDO> queryWrapper;

            for (ReturnOrderVO item : list) {
                if (StringUtils.isBlank(item.getCustomerNo()) && StringUtils.isBlank(item.getUserNo())) {
                    faileReason = item.getApplyNo() + " " + item.getOrderNo() + "客户编码/用户编码.不可同时为空";
                    return ResultVo.failure(faileReason);
                }
                if (StringUtils.isBlank(item.getRequestWarehouseCode())) {
                    faileReason = item.getApplyNo() + " " + item.getOrderNo() + "要求退货仓库不可为空";
                    return ResultVo.failure(faileReason);
                }
                boolean specialModel = opsCommonService.isSpecialModel(item.getModelNo());
                if (specialModel) {
                    if (!"KLS".equalsIgnoreCase(item.getRequestWarehouseCode())) {
                        return ResultVo.failure("LS订单退回仓库只能是KLS");
                    }
                }

                returnOrderDO = BeanCopyUtil.copy(item, ReturnOrderDO.class);
                if ("是".equals(item.getIsSpecialReserve())) {
                    returnOrderDO.setToUserStock(1);
                } else {
                    returnOrderDO.setToUserStock(0);
                }
                // 2023/07/27 WuJiaWen Bug11600
                if ("是".equals(item.getWhetherSellByDate())) {
                    returnOrderDO.setIsWarrantyPeriod(1);
                } else {
                    returnOrderDO.setIsWarrantyPeriod(0);
                }
                returnOrderDO.setIsAssOrder(false);

                orderDetail = receiveOrderServiceFeignApi.findOrderDetail(item.getOrderNo());
                if (orderDetail.isSuccess() && orderDetail.getData() != null) {
                    returnOrderDO.setOrderType(orderDetail.getData().getOrderType());
                    // 非委托在库， 判断是否组装订单
                    if (orderDetail.getData().getOrderType() != null) {
                        if (StringUtils.isNotBlank(orderDetail.getData().getProdFlag())) {
                            if ("2".equals(orderDetail.getData().getProdFlag())) {
                                returnOrderDO.setIsAssOrder(true);
                            } else {
                                returnOrderDO.setIsAssOrder(false);
                            }
                        }
                    }
                }
                // returnOrderDO.setRequestWarehouseCode(requestWareHouseCode);
                returnOrderDO.setCreateTime(new Date());
                returnOrderDO.setUpdateTime(new Date());
                if (returnOrderDO.getApplyNo().startsWith("CS")) {
                    returnOrderDO.setStatus(3);
                } else {
                    returnOrderDO.setStatus(2);
                }
                returnOrderDO.setExtStatus(0);

                returnOrderDO.setContactPsn(StringUtils.isBlank(warehouseDO.getRcvLinkman()) ? warehouseDO.getLinkMan() : warehouseDO.getRcvLinkman());
                returnOrderDO.setContactTelno(StringUtils.isBlank(warehouseDO.getRcvLinkTel()) ? warehouseDO.getLinkMobile() : warehouseDO.getRcvLinkTel());

                // 判断是否已经存在，存在则更新，不存在则新增
                queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ReturnOrderDO::getApplyNo, item.getApplyNo());
                queryWrapper.eq(ReturnOrderDO::getOrderNo, item.getOrderNo());
                queryWrapper.ne(ReturnOrderDO::getStatus, ReturnOrderStatusEnum.cancelReturn.getCode());
                List<ReturnOrderDO> returnOrderDOS = returnOrderMapper.selectList(queryWrapper);

                ReturnOrderDO orderDO = null;

                if (CollectionUtils.isNotEmpty(returnOrderDOS)) {
                    if(returnOrderDOS.size() > 1) {
                        return ResultVo.failure("申请单号:" + item.getApplyNo() + "订单号:" + item.getOrderNo() + "重复");
                    } else {
                        orderDO = returnOrderDOS.get(0);
                    }
                }

                if (orderDO != null) {
                    returnOrderDO.setId(orderDO.getId());
                    int update = returnOrderMapper.updateById(returnOrderDO);
                    // int update = returnOrderMapper.update(returnOrderDO, queryWrapper);
                    if (update != 1) {
                        faileReason = item.getApplyNo() + "-" + item.getOrderNo() + "修改退货失败";
                        return ResultVo.failure(faileReason);
                    }
                } else {
                    int insert = returnOrderMapper.insert(returnOrderDO);
                    if (insert != 1) {
                        faileReason = item.getApplyNo() + "-" + item.getOrderNo() + "新增退货失败";
                        return ResultVo.failure(faileReason);
                    }
                }
                orderNoList.add(item.getOrderNo());
            }

            //申请驳回导致的多余数据，状态改为取消
            num += list.size();
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ReturnOrderDO::getApplyNo, applyNo);
            queryWrapper.notIn(ReturnOrderDO::getOrderNo, orderNoList);
            List<ReturnOrderDO> doList = returnOrderMapper.selectList(queryWrapper);
            for (ReturnOrderDO orderDO : doList) {
                orderDO.setStatus(9);
                orderDO.setUpdateTime(new Date());
                orderDO.setItemNo(num++);
                orderDO.setRemark("驳回导致的冗余数据");
                returnOrderMapper.updateById(orderDO);
            }
            log.info("添加退货单执行完毕==> " + (System.currentTimeMillis() - l) / 1000 + " s");
        } catch (Exception e) {
            log.error("提交退货订单发送异常:", e);
            throw new BusinessException(e.getMessage(), e);
        } finally {
            // 释放锁
            redissonUtil.unlock(lockKey);
        }
        return ResultVo.success("操作成功!!");
    }


    public ResultVo<String> getWarehouseCode(String customerNo) {
        if (StringUtils.isBlank(customerNo)) {
            return ResultVo.failure("客户代码不可为空");
        }
        CustomerVO customerInfoByNo = commonService.getCustomerInfoByNo(customerNo);
        if (customerInfoByNo == null || StringUtils.isBlank(customerInfoByNo.getHRUnitId())) {
            return ResultVo.failure("根据客户获取夤营业所失败");
        }
        // 根据holon所转营业所
        String hrUnitId = customerInfoByNo.getHRUnitId();
        ResultVo<HROrganizationVO> hrOrganByDeptNo = commonServiceFeignApi.findHrOrganByDeptNo(hrUnitId);
        if (!hrOrganByDeptNo.isSuccess() || hrOrganByDeptNo.getData() == null || StringUtils.isBlank(hrOrganByDeptNo.getData().getParentId())) {
            return ResultVo.failure("获取营业所失败");
        }
        ResultVo<HROrganizationVO> resultVo = commonServiceFeignApi.findHrOrganByDeptNo(hrOrganByDeptNo.getData().getParentId());
        if (!resultVo.isSuccess() || resultVo.getData() == null) {
            return ResultVo.failure("获取营业所失败");
        }
        List<CalTransDayEntity> wareHouseType = opsWarehouseSalesBranchConfigMapper.getWareHouseType(resultVo.getData().getId());
        if (CollectionUtils.isEmpty(wareHouseType) || StringUtils.isBlank(wareHouseType.get(0).getWarehouseCode())) {
            return ResultVo.failure(customerNo + "客户下面的" + resultVo.getData().getId() + "营业所没有找到收货仓库.");
        }
        return ResultVo.success(wareHouseType.get(0).getWarehouseCode());
    }

    public static String judgeOrderType(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }
        if (orderNo.startsWith("1")) {  // 销售订单
            return OrderTypeEnum.saleOrder.getCode();
        } else if (orderNo.startsWith("CN")) { // 国内集团销售订单
            return OrderTypeEnum.gnjtOrder.getCode();
        } else if (orderNo.startsWith("CM")) { //  一般贸易订单
            return OrderTypeEnum.saleOrder.getCode();
        } else if (orderNo.startsWith("PT")) { // 先行补库订单
            return OrderTypeEnum.xxbkOrder.getCode();
        } else if (orderNo.startsWith("71")) { // DR采购订单
            return OrderTypeEnum.drcgOrder.getCode();
        } else if (orderNo.startsWith("72")) { // CR采购订单
            return OrderTypeEnum.crcgOrder.getCode();
        } else if (orderNo.startsWith("S")) { // 样品订单
            return OrderTypeEnum.ypOrder.getCode();
        }
        return null;
    }

    /**
     * 退货列表查询
     */
    @Override
    public ResultVo<PageInfo<ReturnOrderVO>> findAll(ReturnOrderRequest returnOrderRequest, Page page) {

        LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(PublicUtil.isNotEmpty(returnOrderRequest.getApplyNo()), ReturnOrderDO::getApplyNo, returnOrderRequest.getApplyNo());
        queryWrapper.like(PublicUtil.isNotEmpty(returnOrderRequest.getOrderNo()), ReturnOrderDO::getOrderNo, returnOrderRequest.getOrderNo());
        queryWrapper.like(PublicUtil.isNotEmpty(returnOrderRequest.getModelNo()), ReturnOrderDO::getModelNo, returnOrderRequest.getModelNo());
        if (ArrayUtils.isNotEmpty(returnOrderRequest.getStatus())) {
            queryWrapper.in(ReturnOrderDO::getStatus, Arrays.asList(returnOrderRequest.getStatus()));
        }
        queryWrapper.ge(PublicUtil.isNotEmpty(returnOrderRequest.getApplyTimeStart()), ReturnOrderDO::getApplyTime, returnOrderRequest.getApplyTimeStart());
        queryWrapper.le(PublicUtil.isNotEmpty(returnOrderRequest.getApplyTimeEnd()), ReturnOrderDO::getApplyTime, returnOrderRequest.getApplyTimeEnd());
        queryWrapper.ge(PublicUtil.isNotEmpty(returnOrderRequest.getReceiveTimeStart()), ReturnOrderDO::getReceiveTime, returnOrderRequest.getReceiveTimeStart());
        queryWrapper.le(PublicUtil.isNotEmpty(returnOrderRequest.getReceiveTimeEnd()), ReturnOrderDO::getReceiveTime, returnOrderRequest.getReceiveTimeEnd());
        queryWrapper.ge(PublicUtil.isNotEmpty(returnOrderRequest.getInTimeStart()), ReturnOrderDO::getInTime, returnOrderRequest.getInTimeStart());
        queryWrapper.le(PublicUtil.isNotEmpty(returnOrderRequest.getInTimeEnd()), ReturnOrderDO::getInTime, returnOrderRequest.getInTimeEnd());
        // update by LiYingChao from bugid 9240 in 2023/1/3
        queryWrapper.eq(PublicUtil.isNotEmpty(returnOrderRequest.getRequestWarehouseCode()), ReturnOrderDO::getRequestWarehouseCode, returnOrderRequest.getRequestWarehouseCode());
//        if (StringUtils.isBlank(returnOrderRequest.getApplyNo()) && StringUtils.isBlank(returnOrderRequest.getOrderNo())) {
//            queryWrapper.eq(PublicUtil.isNotEmpty(returnOrderRequest.getRequestWarehouseCode()), ReturnOrderDO::getRequestWarehouseCode, returnOrderRequest.getRequestWarehouseCode());
//        }
        queryWrapper.orderByAsc(ReturnOrderDO::getItemNo);
        PageInfo<ReturnOrderDO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> returnOrderMapper.selectList(queryWrapper));


        PageInfo<ReturnOrderVO> pageInfo1 = BeanCopyUtil.pageDto2Vo(pageInfo, ReturnOrderVO.class);

        if (CollectionUtils.isNotEmpty(pageInfo1.getList())) {
            Map<String, String> nameMap = new HashMap<>(pageInfo1.getList().size());
            for (ReturnOrderVO item : pageInfo1.getList()) {

                // 部门名称
                if (!nameMap.containsKey(item.getDeptNo())) {
                    nameMap.put(item.getDeptNo(), commonService.getDeptNameByNo(item.getDeptNo()));
                }
                item.setDeptName(item.getDeptNo() + "[" + nameMap.get(item.getDeptNo()) + "]");

                // 要求退货仓库
                if (StringUtils.isNotBlank(item.getRequestWarehouseCode())) {
                    if (!nameMap.containsKey(item.getRequestWarehouseCode())) {
                        nameMap.put(item.getRequestWarehouseCode(), commonService.getWarehouseNameByCode(item.getRequestWarehouseCode()));
                    }
                    item.setRequestWarehouseCodeName(nameMap.get(item.getRequestWarehouseCode()) + "[" + item.getRequestWarehouseCode() + "]");
                }
                // 收货仓库
                if (StringUtils.isNotBlank(item.getRcvWarehouseCode())) {
                    if (!nameMap.containsKey(item.getRcvWarehouseCode())) {
                        nameMap.put(item.getRcvWarehouseCode(), commonService.getWarehouseNameByCode(item.getRcvWarehouseCode()));
                    }
                    item.setRcvWarehouseCodeName(nameMap.get(item.getRcvWarehouseCode()) + "[" + item.getRcvWarehouseCode() + "]");
                }
                // 修改人
                if (StringUtils.isNotBlank(item.getUpdateUser())) {
                    item.setUpdateUser(commonService.getEmpSaleNameByNo(item.getUpdateUser()) + "[" + item.getUpdateUser() + "]");
                }

                // 收货人
                if (StringUtils.isNotBlank(item.getConsignee())) {
                    item.setConsignee(commonService.getEmpSaleNameByNo(item.getConsignee()) + "[" + item.getConsignee() + "]");
                }

                // 申请人
                if (StringUtils.isNotBlank(item.getApplicant())) {
                    item.setApplicant(commonService.getEmpSaleNameByNo(item.getApplicant()) + "[" + item.getApplicant() + "]");
                }
                // 客户代码/名称
                if (StringUtils.isNotBlank(item.getCustomerNo())) {
                    item.setCustomerNo(item.getCustomerNo() + "[" + commonService.getCustomerNameByNo(item.getCustomerNo()) + "]");
                }
                // 用户代码/名称
                if (StringUtils.isNotBlank(item.getUserNo())) {
                    item.setUserNo(item.getUserNo() + "[" + commonService.getCustomerNameByNo(item.getUserNo()) + "]");
                }
                // 客户担当
                if (StringUtils.isNotBlank(item.getEmployee())) {
                    item.setEmployee(commonService.getEmpSaleNameByNo(item.getEmployee()) + "[" + item.getEmployee() + "]");
                }

                if (item.getStatus() != null) {
                    item.setStatusName(ReturnOrderStatusEnum.getCodeNameByCode(String.valueOf(item.getStatus())));
                }
                item.setToUserStockName(item.getToUserStock() == 0 ? "否" : "是");

                if (item.getInvoiceDate() != null) {
                    item.setInvoiceDateStr(DateUtil.dateToDateTimeString(item.getInvoiceDate()));
                }

                if (item.getApplyTime() != null) {
                    item.setApplyTimeStr(DateUtil.dateToDateTimeString(item.getApplyTime()));
                }

                if (item.getInTime() != null) {
                    item.setInTimeStr(DateUtil.dateToDateTimeString(item.getInTime()));
                }

                if (item.getReceiveTime() != null) {
                    item.setReceiveTimeStr(DateUtil.dateToDateTimeString(item.getReceiveTime()));
                }

            }
        }

        return ResultVo.success(pageInfo1);
    }

    /**
     * 退货列表导出
     *
     * @param returnOrderRequest
     */
    @Override
    public void exportReturnOrder(ReturnOrderRequest returnOrderRequest) {

        LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(PublicUtil.isNotEmpty(returnOrderRequest.getApplyNo()), ReturnOrderDO::getApplyNo, returnOrderRequest.getApplyNo());
        queryWrapper.like(PublicUtil.isNotEmpty(returnOrderRequest.getOrderNo()), ReturnOrderDO::getOrderNo, returnOrderRequest.getOrderNo());
        queryWrapper.like(PublicUtil.isNotEmpty(returnOrderRequest.getModelNo()), ReturnOrderDO::getModelNo, returnOrderRequest.getModelNo());
        if (ArrayUtils.isNotEmpty(returnOrderRequest.getStatus())) {
            queryWrapper.in(ReturnOrderDO::getStatus, Arrays.asList(returnOrderRequest.getStatus()));
        }
        queryWrapper.ge(PublicUtil.isNotEmpty(returnOrderRequest.getApplyTimeStart()), ReturnOrderDO::getApplyTime, returnOrderRequest.getApplyTimeStart());
        queryWrapper.le(PublicUtil.isNotEmpty(returnOrderRequest.getApplyTimeEnd()), ReturnOrderDO::getApplyTime, returnOrderRequest.getApplyTimeEnd());
        queryWrapper.ge(PublicUtil.isNotEmpty(returnOrderRequest.getReceiveTimeStart()), ReturnOrderDO::getReceiveTime, returnOrderRequest.getReceiveTimeStart());
        queryWrapper.le(PublicUtil.isNotEmpty(returnOrderRequest.getReceiveTimeEnd()), ReturnOrderDO::getReceiveTime, returnOrderRequest.getReceiveTimeEnd());
        queryWrapper.ge(PublicUtil.isNotEmpty(returnOrderRequest.getInTimeStart()), ReturnOrderDO::getInTime, returnOrderRequest.getInTimeStart());
        queryWrapper.le(PublicUtil.isNotEmpty(returnOrderRequest.getInTimeEnd()), ReturnOrderDO::getInTime, returnOrderRequest.getInTimeEnd());
        queryWrapper.eq(PublicUtil.isNotEmpty(returnOrderRequest.getRequestWarehouseCode()), ReturnOrderDO::getRequestWarehouseCode, returnOrderRequest.getRequestWarehouseCode());
//        if (StringUtils.isBlank(returnOrderRequest.getApplyNo()) && StringUtils.isBlank(returnOrderRequest.getOrderNo())) {
//            queryWrapper.eq(PublicUtil.isNotEmpty(returnOrderRequest.getRequestWarehouseCode()), ReturnOrderDO::getRequestWarehouseCode, returnOrderRequest.getRequestWarehouseCode());
//        }
        queryWrapper.orderByAsc(ReturnOrderDO::getItemNo);

        List<ReturnOrderDO> returnOrderDOList = returnOrderMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(returnOrderDOList)) {
            return;
        }
        List<ReturnOrderVO> returnOrderVOS = BeanCopyUtil.copyList(returnOrderDOList, ReturnOrderVO.class);

        String path = "template/exportReturnOrderData.xlsx";
        ExcelUtil excel = new ExcelUtil(path);
        excel.openSheet(0);
        // 向模板中写入数据
        int row = 1;
        int cel;

        Map<String, String> nameMap = new HashMap<>(returnOrderDOList.size());
        for (ReturnOrderVO item : returnOrderVOS) {

            // 部门名称
            if (StringUtils.isNotBlank(item.getDeptNo())) {
                if (!nameMap.containsKey(item.getDeptNo())) {
                    nameMap.put(item.getDeptNo(), item.getDeptNo() + "[" + commonService.getDeptNameByNo(item.getDeptNo()) + "]");
                }
                item.setDeptName(nameMap.get(item.getDeptNo()));
            }

            // 要求退货仓库名称
            if (StringUtils.isNotBlank(item.getRequestWarehouseCode())) {
                if (!nameMap.containsKey(item.getRequestWarehouseCode())) {
                    nameMap.put(item.getRequestWarehouseCode(), item.getRequestWarehouseCode() + "[" + commonService.getWarehouseNameByCode(item.getRequestWarehouseCode()) + "]");
                }
                item.setRequestWarehouseCodeName(nameMap.get(item.getRequestWarehouseCode()));
            }

            // 实际退货仓库名称
            if (StringUtils.isNotBlank(item.getRcvWarehouseCode())) {
                if (!nameMap.containsKey(item.getRcvWarehouseCode())) {
                    nameMap.put(item.getRcvWarehouseCode(), item.getRcvWarehouseCode() + "[" + commonService.getWarehouseNameByCode(item.getRcvWarehouseCode()) + "]");
                }
                item.setRcvWarehouseCodeName(nameMap.get(item.getRcvWarehouseCode()));
            }

            // 状态名称
            if (item.getStatus() != null) {
                String statusName = ReturnOrderStatusEnum.getCodeNameByCode(String.valueOf(item.getStatus()));
                item.setStatusName(statusName);
            }
            // 收货人
            if (StringUtils.isNotBlank(item.getConsignee())) {
                if (!nameMap.containsKey(item.getConsignee())) {
                    nameMap.put(item.getConsignee(), item.getConsignee() + "[" + commonService.getEmpSaleNameByNo(item.getConsignee()) + "]");
                }
                item.setConsignee(nameMap.get(item.getConsignee()));
            }
            // 客户代码/名称
            if (StringUtils.isNotBlank(item.getCustomerNo())) {
                if (!nameMap.containsKey(item.getCustomerNo())) {
                    nameMap.put(item.getCustomerNo(), item.getCustomerNo() + "[" + commonService.getCustomerNameByNo(item.getCustomerNo()) + "]");
                }
                item.setCustomerNo(nameMap.get(item.getCustomerNo()));
            }
            // 客户担当
            if (StringUtils.isNotBlank(item.getEmployee())) {
                if (!nameMap.containsKey(item.getEmployee())) {
                    nameMap.put(item.getEmployee(), item.getEmployee() + "[" + commonService.getEmpSaleNameByNo(item.getEmployee()) + "]");
                }
                item.setEmployee(nameMap.get(item.getEmployee()));
            }

            // 申请人
            if (StringUtils.isNotBlank(item.getApplicant())) {
                if (!nameMap.containsKey(item.getApplicant())) {
                    nameMap.put(item.getApplicant(), item.getApplicant() + "[" + commonService.getEmpSaleNameByNo(item.getApplicant()) + "]");
                }
                item.setApplicant(nameMap.get(item.getApplicant()));
            }

            if (item.getInvoiceDate() != null) {
                item.setInvoiceDateStr(DateUtil.dateToDateTimeString(item.getInvoiceDate()));
            }

            if (item.getApplyTime() != null) {
                item.setApplyTimeStr(DateUtil.dateToDateTimeString(item.getApplyTime()));
            }

            if (item.getInTime() != null) {
                item.setInTimeStr(DateUtil.dateToDateTimeString(item.getInTime()));
            }

            if (item.getReceiveTime() != null) {
                item.setReceiveTimeStr(DateUtil.dateToDateTimeString(item.getReceiveTime()));
            }

            cel = 0;
            excel.setCellValue(row, cel++, item.getApplyNo());
            excel.setCellValue(row, cel++, item.getItemNo());
            excel.setCellValue(row, cel++, item.getOrderNo());
            excel.setCellValue(row, cel++, item.getStatusName());
            excel.setCellValue(row, cel++, item.getModelNo());
            excel.setCellValue(row, cel++, item.getOrderQty());
            excel.setCellValue(row, cel++, item.getApplyQty());
            excel.setCellValue(row, cel++, item.getApplyBadqty());
            excel.setCellValue(row, cel++, item.getRcvFineqty());
            excel.setCellValue(row, cel++, item.getReturnQty());
            excel.setCellValue(row, cel++, item.getCustomerNo());
            excel.setCellValue(row, cel++, item.getEmployee());
            excel.setCellValue(row, cel++, item.getExpStockCode());
            excel.setCellValue(row, cel++, item.getRequestWarehouseCodeName());
            excel.setCellValue(row, cel++, item.getRcvWarehouseCodeName());
            excel.setCellValue(row, cel++, item.getExpStocktype());
            excel.setCellValue(row, cel++, item.getSalesPrice());
            excel.setCellValue(row, cel++, item.getFeeRate());
            excel.setCellValue(row, cel++, item.getReturnFee());
            excel.setCellValue(row, cel++, item.getInvoiceNo());
            excel.setCellValue(row, cel++, item.getExpressNo());
            excel.setCellValue(row, cel++, item.getInvoiceDateStr());
            excel.setCellValue(row, cel++, item.getRcvBadqty());
            excel.setCellValue(row, cel++, item.getApplyTimeStr());
            excel.setCellValue(row, cel++, item.getApplicant());
            excel.setCellValue(row, cel++, item.getApplicantTel());
            excel.setCellValue(row, cel++, item.getApplicantEmail());
            excel.setCellValue(row, cel++, item.getLocationNo());
            excel.setCellValue(row, cel++, item.getToUserStock() == 0 ? "否" : "是");
            excel.setCellValue(row, cel++, item.getInTimeStr());
            excel.setCellValue(row, cel++, item.getReceiveTimeStr());
            excel.setCellValue(row, cel++, item.getDeptName());
            excel.setCellValue(row, cel++, item.getConsignee());
            excel.setCellValue(row, cel++, item.getReason());
            excel.setCellValue(row, cel++, item.getReturnType());
            excel.setCellValue(row, cel++, item.getDutyValue());
            excel.setCellValue(row, cel++, item.getProDangerLevel());
            row++;
        }
        excel.writeToResponse(response, "ExportSampleBalData.xlsx");

    }

    /**
     * 预览打印退货单
     */
    @Override
    public void printReturnOrder(PrintReturnOrderParams params, HttpServletResponse response) {

        if (params.getApplyNo().length != 1) {
            return;
        }
        String[] applyNos = params.getApplyNo();
        String applyNo = applyNos[0]; // 申请单号

        QueryWrapper<ReturnOrderDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apply_no", applyNo);
        List<ReturnOrderDO> returnOrderDOList = returnOrderMapper.selectList(queryWrapper);

        if (returnOrderDOList.isEmpty()) {
            return;
        }

        List<ReturnOrderVO> list = BeanCopyUtil.copyList(returnOrderDOList, ReturnOrderVO.class);
        ReturnOrderVO returnOrderVO = list.get(0);

        if (PublicUtil.isEmpty(returnOrderVO.getCustomerNo())) {
            return;
        }
        // 获取退货公司
        ResultVo<CustomerVO> resultVo = commonServiceFeignApi.findCustomerByCustomerNo(returnOrderVO.getCustomerNo());
        if (!resultVo.isSuccess() || resultVo.getData() == null) {
            return;
        }
        String returnCompanty = resultVo.getData().getName();

        // 获取收货地址信息
        if (PublicUtil.isEmpty(returnOrderVO.getRcvWarehouseCode())) {
            return;
        }
        WarehouseQueryDTO warehouseQueryDTO = new WarehouseQueryDTO();
        warehouseQueryDTO.setWarehouseCode(returnOrderVO.getRcvWarehouseCode());
        ResultVo<List<WarehouseVO>> listResultVo = commonServiceFeignApi.listWarehouse(warehouseQueryDTO);
        if (!listResultVo.isSuccess() || listResultVo.getData() == null) {
            return;
        }
        WarehouseVO warehouseVO = listResultVo.getData().get(0);

        PrintReturnOrderDTO printReturnOrderDTO = new PrintReturnOrderDTO();
        List<PrintReturnOrderVO> printReturnOrderVOList = new ArrayList<>();

        Integer[] arr = {1, 2, 9};
        for (ReturnOrderVO item : list) {
            PrintReturnOrderVO printReturnOrderVO = new PrintReturnOrderVO();
            // 编辑中 . 审批确认中 . 取消 ==> 不打印
            if (Arrays.asList(arr).contains(item.getStatus())) {
                continue;
            }
            printReturnOrderVO.setOrderNo(item.getOrderNo());
            printReturnOrderVO.setModelNo(item.getModelNo());
            printReturnOrderVO.setReturnQty(item.getReturnQty() == null ? 0 : item.getReturnQty());
            printReturnOrderVO.setRcvFineQty(item.getRcvFineqty() == null ? 0 : item.getRcvFineqty());
            printReturnOrderVO.setRcvBadQty(item.getRcvBadqty() == null ? 0 : item.getRcvBadqty());

            if (item.getIsAssOrder()) {
                printReturnOrderVO.setExpStockType("是");
            } else {
                printReturnOrderVO.setExpStockType("否");
            }
            printReturnOrderVO.setRemark(item.getRemark());
            printReturnOrderVOList.add(printReturnOrderVO);
        }
        printReturnOrderDTO.setReturnOrderData(printReturnOrderVOList);

        Map<String, Object> map = new HashMap<>();
        map.put("img", Constants.IMG);
        map.put("applyNo", applyNo);
        map.put("passTime", dateConvert(returnOrderVO.getPassTime()));
        map.put("returnCompany", returnCompanty);
        map.put("printTime", dateConvert(new Date()));
        map.put("contactPsn", returnOrderVO.getContactPsn());
        map.put("contactTelno", returnOrderVO.getContactTelno());
        map.put("warehouseName", warehouseVO.getWarehouseName());
        map.put("adress", warehouseVO.getAdress());
        map.put("linkman", warehouseVO.getLinkman());
        map.put("smcPhone", warehouseVO.getSmcPhone());

        List<PrintReturnOrderDTO> printReturnOrderDTOList = new ArrayList<>(1);
        printReturnOrderDTOList.add(printReturnOrderDTO);

        InputStream inputStream = FileUtil.getTemplate("template/jasper/returnOrder.jasper");
        String fileName = applyNo + ".pdf";
        try {
            JasperHelper.showPdf(fileName, inputStream, response, map, printReturnOrderDTOList);
        } catch (JRException | IOException e) {
            log.error("预览打印退货单发生异常,", e);
        }
    }

    /**
     * 收货登记-回调门户
     */
    @Override
    public ResultVo<String> dealReturnOrder(List<ReturnOrderBackCallVO> orderBackCallVOS) {

        String authorization = createTokenForOtherServer.getMHToken();

        HttpResponse response;

        String json = null;
        if (!orderBackCallVOS.isEmpty()) {
            json = JSONUtil.toJsonStr(orderBackCallVOS);
        }
        try {
            String dealReturnOrderUrl = menHuUrl + "/saleManageSystem/opsReturnOrder/dealReturnOrder";
            response = HttpUtil.createPost(dealReturnOrderUrl)
                    .header("Authorization", authorization)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .charset(StandardCharsets.UTF_8)
                    .body(json)
                    .execute();
            String strResponse = response.body();
            JSONObject jsonObject = JSON.parseObject(strResponse);
            System.out.println("jsonObject = " + jsonObject);
            if (jsonObject.getString("success").equals("false")) {
                return ResultVo.failure(jsonObject.getString("message"));
            } else {
                return ResultVo.success(jsonObject.getString("message"));
            }
        } catch (Exception e) {
            throw new BusinessException("退货回调门户接口 error=> {} ", e);
        }
    }


    /**
     * 拒绝收货
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> refuseRcv(RefuseRcvRequest refuseRcvRequest) {

        log.info("包裹拒收参数: " + JSONObject.toJSONString(refuseRcvRequest));
        if (refuseRcvRequest.getIds().isEmpty()) {
            return ResultVo.failure("请选择需要拒收的退货单.");
        }
        List<String> ids = refuseRcvRequest.getIds().stream().distinct().collect(Collectors.toList());

        // 未被拒收的单号/数量
        StringBuilder optErrorOrderNo = new StringBuilder();
        int optErrorCount = 0;
        // 被拒收的单号/数量
        StringBuilder optSuccessOrderNo = new StringBuilder();
        int optSuccessCount = 0;
        // 整单拒收
        if (refuseRcvRequest.getAllRefuseRcv() == 1) {
            for (String id : ids) {
                ReturnOrderDO returnOrderDO = returnOrderMapper.selectById(id);
                if (null == returnOrderDO) {
                    return ResultVo.failure(id + "该退货单不存在.");
                }

                LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ReturnOrderDO::getApplyNo, returnOrderDO.getApplyNo())
                        .eq(ReturnOrderDO::getStatus, 3);
                List<ReturnOrderDO> returnOrderDOList = returnOrderMapper.selectList(queryWrapper);

                if (CollectionUtils.isEmpty(returnOrderDOList)) {
                    return ResultVo.failure(returnOrderDO.getApplyNo() + "该申请不存在.");
                }

                for (ReturnOrderDO orderDO : returnOrderDOList) {
                    if (orderDO.getStatus() != 3) {
                        optErrorOrderNo.append(returnOrderDO.getOrderNo() + ";");
                        optErrorCount++;
                        continue;
                    }
                    // 修改退货单状态并回调门户退货接口
                    upReturnStatusAndDealReturn(orderDO);

                    optSuccessOrderNo.append(orderDO.getOrderNo() + ";");
                    optSuccessCount++;
                }
            }
        } else {
            for (String id : ids) {
                ReturnOrderDO returnOrderDO = returnOrderMapper.selectById(id);
                if (null == returnOrderDO) {
                    return ResultVo.failure(id + "该退货单不存在.");
                }
                if (returnOrderDO.getStatus() != 3) {
                    optErrorOrderNo.append(returnOrderDO.getOrderNo() + ";");
                    optErrorCount++;
                    continue;
                }
                //修改退货单状态并回调门户退货接口
                upReturnStatusAndDealReturn(returnOrderDO);

                optSuccessOrderNo.append(returnOrderDO.getOrderNo() + ";");
                optSuccessCount++;
            }
        }
        if (optErrorCount > 0 && optSuccessCount > 0) {
            return ResultVo.success("操作完毕,拒收:" + optSuccessCount + "项, 订单号: " + optSuccessOrderNo.toString() + "\n" + optErrorCount + "项未被拒收. 订单号: " + optErrorOrderNo.toString());
        } else if (optErrorCount > 0 && optSuccessCount == 0) {
            return ResultVo.success("操作完毕," + optErrorCount + "项未被拒收. 订单号: " + optErrorOrderNo.toString());
        } else if (optErrorCount == 0 && optSuccessCount > 0) {
            return ResultVo.success("操作完毕,拒收:" + optSuccessCount + "项, 订单号: " + optSuccessOrderNo.toString());
        } else {
            return ResultVo.success("操作成功");
        }
    }

    /**
     * 修改退货单状态并回调门户退货接口
     *
     * @return
     */
    public void upReturnStatusAndDealReturn(ReturnOrderDO returnOrderDO) {
        LambdaUpdateWrapper<ReturnOrderDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(ReturnOrderDO::getUpdateTime,new Date())
                .set(ReturnOrderDO::getStatus, 4) // 包裹拒收
                .set(ReturnOrderDO::getExtStatus, 2) // 1是同步了仓库已收货,2是同步了订单取消和完全退货[最终状态]
                .eq(ReturnOrderDO::getId, returnOrderDO.getId());
        returnOrderMapper.update(null, updateWrapper);
        // 退货回调门户接口
        List<ReturnOrderBackCallVO> orderBackCallVOS = new ArrayList<>();
        ReturnOrderBackCallVO item = new ReturnOrderBackCallVO();
        item.setApplyNo(returnOrderDO.getApplyNo());
        item.setOrderNo(returnOrderDO.getOrderNo());
        item.setAllowReturnQuantity(0);
        item.setNotAllowReturnQuantity(0);
        item.setSignDate(new Date());
        item.setDealDate(new Date());
        item.setStatus(4); // 包裹拒收
        orderBackCallVOS.add(item);
        ResultVo<String> resultVo = dealReturnOrder(orderBackCallVOS);
        log.info("[门户回调接口]退货回调门户接口 响应: " + JSONObject.toJSONString(resultVo));

        // 调库取消
        if (returnOrderDO.getOrderType() == 3 && returnOrderDO.getApplyNo().startsWith("CS")) {
            ResultVo<String> stringResultVo = this.cancelCsReturnOrder(returnOrderDO.getApplyNo(), returnOrderDO.getOrderNo());
            if (!stringResultVo.isSuccess()) {
                throw new RuntimeException(stringResultVo.getMessage());
            }
        }

    }
    //状态为6已完成时
    //同步已收货的订单到cs_impdata
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> sysReturnOrderToImpData() {
        LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ReturnOrderDO::getStatus, 6);
        queryWrapper.eq(ReturnOrderDO::getExtStatus, 0);
        queryWrapper.eq(ReturnOrderDO::getOrderType, 3);
        List<ReturnOrderDO> list = returnOrderMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.success("暂无数据需要同步");
        }

        CsImpdataDO csImpdataDO;

        for (ReturnOrderDO orderDO : list) {
            if (PublicUtil.isEmpty(orderDO.getReturnQty()) || orderDO.getReturnQty() == 0) {
                continue;
            }
            csImpdataDO = new CsImpdataDO();
            csImpdataDO.setAgentNo(orderDO.getCustomerNo());
            csImpdataDO.setWarehouseCode(orderDO.getExpStockCode());
            csImpdataDO.setOrderNo(orderDO.getOrderNo());
            csImpdataDO.setStatus(2);
            csImpdataDO.setImpDate(new Date());
            csImpdataDO.setShipDate(new Date());
            csImpdataDO.setModelNo(orderDO.getModelNo());
            csImpdataDO.setQuantity(-orderDO.getReturnQty());
            csImpdataDO.setImpType(2);
            csImpdataDO.setReceiveTime(orderDO.getReceiveTime());
            csImpdataDO.setUserNo(orderDO.getUserNo());
            csImpdataDO.setApplyType(Integer.valueOf(orderDO.getExpStocktype()));
            csImpdataMapper.insert(csImpdataDO);

            //扣减数量
            this.deductLeftQty(orderDO);
            orderDO.setExtStatus(1);
            orderDO.setUpdateTime(new Date());
            returnOrderMapper.updateById(orderDO);

            // 回改状态
            if (orderDO.getApplyNo().contains("CS")) {
                Integer id = Integer.valueOf(orderDO.getApplyNo().replaceAll("[^0-9]", ""));
                csStockApplyService.updateCsReturnApplyStatus(id);
            }

            // 改transOrder状态
            csStockApplyService.updateTransOrderStatus(csImpdataDO);
        }

        return ResultVo.success("同步数据成功");
    }

    private void deductLeftQty(ReturnOrderDO orderDO) {
        LambdaQueryWrapper<CsImpdataDO> query = new LambdaQueryWrapper<>();
        query.eq(CsImpdataDO::getWarehouseCode, orderDO.getExpStockCode());
        query.eq(CsImpdataDO::getModelNo, orderDO.getModelNo());
        query.eq(CsImpdataDO::getStatus, 2);
        query.gt(CsImpdataDO::getLeftQty, 0);
        query.orderByAsc(CsImpdataDO::getShipDate);
        List<CsImpdataDO> doList = csImpdataMapper.selectList(query);
        Integer returnQty = orderDO.getReturnQty();
        for (CsImpdataDO impdataDO : doList) {
            if (returnQty >= impdataDO.getLeftQty()) {
                returnQty = returnQty - impdataDO.getLeftQty();
                impdataDO.setLeftQty(0);
                csImpdataMapper.updateById(impdataDO);
                continue;
            }
            if (returnQty < impdataDO.getLeftQty()) {
                impdataDO.setLeftQty(impdataDO.getLeftQty() - returnQty);
                csImpdataMapper.updateById(impdataDO);
                break;
            }
        }
    }

    /**
     * 判断拆分型号的数量能够整除
     * 例如 一个单有3个拆分型号 计算数量时, 如果不能被整除 则该单都不可以收
     */
    public boolean isCanReturnBySplitModelNo(ReturnOrderDO returnOrder) {
        String fullOrderNo = returnOrder.getOrderNo();
        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(fullOrderNo);
        // 是组装订单
        if (returnOrder.getIsAssOrder()) {
            // 获取拆分型号的数量，查询型号拆分的 rcvdetail，如果不是型号拆分则返回null
            ResultVo<RcvDetailVO> rcvDetailWithIsAssOrder = receiveOrderServiceFeignApi.findRcvDetailWithIsAssOrder(fullOrderNo);
            if (rcvDetailWithIsAssOrder.getData() == null) {
                return false;
            }
            // 去 ops_order_assign_result 获取 拆分的型号.数量
            List<OpsOrderAssignResultDO> orderAssignList = getOrderAssignList(orderNoInfo.getOrderNo(), String.valueOf(orderNoInfo.getItemNo()));
            if (CollectionUtils.isEmpty(orderAssignList)) {
                return false;
            }
            RcvDetailVO data = rcvDetailWithIsAssOrder.getData();
            // 更改库存数量
            for (OpsOrderAssignResultDO item : orderAssignList) {
                if (item.getQuantity() == null || data.getQuantity() == null) {
                    return false;
                }
                // 判断数量是否能被整除
                if (!PublicUtil.exactDivisor(item.getQuantity(), data.getQuantity())) {
                    return false;
                }
            }
        }
        return true;
    }

    //每个型号返回一条数据，有几个型号就返回几条数据，其他字段取本型号的第一条数据的，不管有没有用
    public List<OpsOrderAssignResultDO> getOrderAssignList(String orderNo,String itemNo) {
        ResultVo<List<OpsOrderAssignResultDO>> orderAssignResult = opsOrderAssignResultService.findOrderAssignResult(orderNo, itemNo);
        if (!orderAssignResult.isSuccess() || CollectionUtils.isEmpty(orderAssignResult.getData())) {
            return null;
        }
        Map<String,OpsOrderAssignResultDO> map = new HashMap<>();
        for (OpsOrderAssignResultDO item : orderAssignResult.getData()) {
            if (item.getQuantity() == null) {
                item.setQuantity(0);
            }
            if (map.containsKey(item.getModelno())) {
                OpsOrderAssignResultDO opsOrderAssignResultDO = map.get(item.getModelno());

                opsOrderAssignResultDO.setQuantity(item.getQuantity()+opsOrderAssignResultDO.getQuantity());
            } else {
                map.put(item.getModelno(),item);
            }
        }
        List<OpsOrderAssignResultDO> list = new ArrayList<>();
        for (String key : map.keySet()) {
            OpsOrderAssignResultDO opsOrderAssignResultDO = map.get(key);
            list.add(opsOrderAssignResultDO);
        }
        return list;
    }

    // 生成退货单模板
    // 有什么用？？
    public ResultVo<InputStream> generateReturnOrderTemplate(PrintReturnOrderParams params) {
        String reson = "[生成退货单模板] ";
        if (params.getApplyNo().length != 1) {
            return ResultVo.failure(reson + "一次只能一个申请号");
        }
        String[] applyNos = params.getApplyNo();
        String applyNo = applyNos[0]; // 申请单号

        QueryWrapper<ReturnOrderDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apply_no", applyNo);
        List<ReturnOrderDO> returnOrderDOList = returnOrderMapper.selectList(queryWrapper);

        if (returnOrderDOList.isEmpty()) {
            return ResultVo.failure(reson + applyNo + "申请号暂未查出退货单数据");
        }

        List<ReturnOrderVO> list = BeanCopyUtil.copyList(returnOrderDOList, ReturnOrderVO.class);
        ReturnOrderVO returnOrderVO = list.get(0);

        if (PublicUtil.isEmpty(returnOrderVO.getCustomerNo())) {
            return ResultVo.failure(reson + "客户代码不可为空");
        }
        // 获取退货公司
        ResultVo<CustomerVO> resultVo = commonServiceFeignApi.findCustomerByCustomerNo(returnOrderVO.getCustomerNo());
        if (!resultVo.isSuccess() || resultVo.getData() == null) {
            return ResultVo.failure(reson + "未获取到退货公司");
        }
        String returnCompanty = resultVo.getData().getName();

        // 获取收货地址信息
        if (PublicUtil.isEmpty(returnOrderVO.getRequestWarehouseCode())) {
            return ResultVo.failure(reson + "收货仓库不可为空");
        }
        WarehouseQueryDTO warehouseQueryDTO = new WarehouseQueryDTO();
        warehouseQueryDTO.setWarehouseCode(returnOrderVO.getRequestWarehouseCode());
        ResultVo<List<WarehouseVO>> listResultVo = commonServiceFeignApi.listWarehouse(warehouseQueryDTO);
        if (!listResultVo.isSuccess() || listResultVo.getData() == null) {
            return ResultVo.failure(reson + "根据仓库编码获取收货地址信息失败");
        }
        WarehouseVO warehouseVO = listResultVo.getData().get(0);

        PrintReturnOrderDTO printReturnOrderDTO = new PrintReturnOrderDTO();
        List<PrintReturnOrderVO> printReturnOrderVOList = new ArrayList<>();

        Integer[] arr = {1, 2, 9};
        for (ReturnOrderVO item : list) {
            PrintReturnOrderVO printReturnOrderVO = new PrintReturnOrderVO();
            // 编辑中 . 审批确认中 . 取消 ==> 不打印
            if (Arrays.asList(arr).contains(item.getStatus())) {
                continue;
            }
            printReturnOrderVO.setOrderNo(item.getOrderNo());
            printReturnOrderVO.setModelNo(item.getModelNo());
            printReturnOrderVO.setReturnQty(item.getApplyQty() == null ? 0 : item.getApplyQty());
            printReturnOrderVO.setRcvFineQty(item.getRcvFineqty() == null ? 0 : item.getRcvFineqty());
            printReturnOrderVO.setRcvBadQty(item.getRcvBadqty() == null ? 0 : item.getRcvBadqty());

            if (item.getIsAssOrder()) {
                printReturnOrderVO.setExpStockType("是");
            } else {
                printReturnOrderVO.setExpStockType("否");
            }
            printReturnOrderVO.setRemark(StringUtils.isBlank(item.getRemark()) ? "" : item.getRemark());
            printReturnOrderVOList.add(printReturnOrderVO);
        }

        if (printReturnOrderVOList.isEmpty()) {
            return ResultVo.failure(reson + applyNo + "该申请下的订单状态为[编辑中(1).审批确认中(2).取消(9)]不进行打印退货单");
        }

        printReturnOrderDTO.setReturnOrderData(printReturnOrderVOList);

        Map<String, Object> map = new HashMap<>();
        map.put("img", Constants.IMG);
        map.put("applyNo", applyNo);
        map.put("passTime", dateConvert(returnOrderVO.getPassTime()));
        map.put("returnCompany", returnCompanty);
        map.put("printTime", dateConvert(new Date()));
        map.put("contactPsn", returnOrderVO.getContactPsn());
        map.put("contactTelno", returnOrderVO.getContactTelno());
        map.put("warehouseName", warehouseVO.getWarehouseName());
        map.put("adress", warehouseVO.getAdress());
        map.put("linkman", warehouseVO.getLinkman());
        map.put("smcPhone", warehouseVO.getSmcPhone());

        List<PrintReturnOrderDTO> printReturnOrderDTOList = new ArrayList<>(1);
        printReturnOrderDTOList.add(printReturnOrderDTO);

        InputStream inputStream = FileUtil.getTemplate("template/jasper/returnOrder.jasper");

        try {
            InputStream templateInputStream = JasperHelper.savePdfToInputStrem(inputStream, map, printReturnOrderDTOList);
            return ResultVo.success(templateInputStream);
        } catch (JRException e) {
            log.error("生成退货单模板发生异常:", e);
        }
        return ResultVo.failure("生成退货单模板失败");
    }

    // 发送退货单邮件给申请人
    public Boolean sendEmailForApplicant(String fileName, InputStream inputStream, String applicantEmail, String applyNo) {
        Map<String, InputStream> attachment = new HashMap<>(1);
        attachment.put(fileName, inputStream);

        /**
         * 正式时需要注释掉下面代码
         */
        applicantEmail = "2355766573@qq.com;m_yutong@163.com";

        // 抄送人
        String cc = null;
        // 主题
        String subject = "申请号为" + applyNo + "的退货单";
        String content = "申请号为" + applyNo + "的退货单";

        return EmailUtil.send(javaMailSender, applicantEmail, cc, subject, content, attachment);
    }


    private String dateConvert(Date date) {
        if (date == null) {
            return null;
        }
        String dateStr = DateUtil.dateToDateTimeString(date);
        return dateStr.replaceAll("-", "/"); // 打印时间
    }

    @Override
    public ResultVo<String> returnOrderConfirm(String applyNo, String orderNo, Integer status) {
        LoginUserDTO loginAuthDto = SMCApp.getLoginAuthDto();
        //退货数据写入在途表
        List<CreInvMoveForReturnOrderDto> returnOrderDtos = new ArrayList<>();

        LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ReturnOrderDO::getApplyNo, applyNo);
        queryWrapper.eq(ReturnOrderDO::getOrderNo, orderNo);
        queryWrapper.eq(ReturnOrderDO::getStatus, status);
        ReturnOrderDO returnOrder = returnOrderMapper.selectOne(queryWrapper);

        if (returnOrder == null) {
            return ResultVo.failure(orderNo + ",该退货申请不存在.");
        }
        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(returnOrder.getOrderNo());

        if (returnOrder.getIsAssOrder()) {
            // 获取拆分型号的数量
            ResultVo<RcvDetailVO> rcvDetailWithIsAssOrder = receiveOrderServiceFeignApi.findRcvDetailWithIsAssOrder(orderNo);
            if (rcvDetailWithIsAssOrder.getData() == null) {
                return ResultVo.failure(orderNo + "收货失败,未能获取拆分型号的数量.");
            }
            // 去 ops_order_assign_result 获取 拆分的型号.数量
            List<OpsOrderAssignResultDO> orderAssItemList = getOrderAssignList(orderNoInfo.getOrderNo(), String.valueOf(orderNoInfo.getItemNo()));
            RcvDetailVO data = rcvDetailWithIsAssOrder.getData();
            // 更改库存数量
            int splitNo = 1;//拆分项
            for (OpsOrderAssignResultDO item : orderAssItemList) {
                if (item.getQuantity() != null && data.getQuantity() != null) {
                    // 是否被整除 被整除才可以继续走下去
                    if (!PublicUtil.exactDivisor(item.getQuantity(), data.getQuantity())) {
                        return ResultVo.failure("拆分数量不正确");
                    }
                } else {
                    return ResultVo.failure("拆分数量为空");
                }

                // 计算数量
                int qty = (item.getQuantity() / data.getQuantity()) * returnOrder.getReturnQty();
                //生成在途数据用于物流收货
                CreInvMoveForReturnOrderDto inputDto = new CreInvMoveForReturnOrderDto();
                //前端填写仓库号
                inputDto.setWarehouseCode(returnOrder.getRcvWarehouseCode());
                inputDto.setQty(qty);//良品rcv_fineQty
                inputDto.setModelNo(item.getModelno());
                inputDto.setApplyNo(returnOrder.getApplyNo());

                if (returnOrder.getOrderType() == 3 || returnOrder.getOrderNo().startsWith("RC")) {
                    if (StringUtils.isNotBlank(returnOrder.getUserNo())) {
                        inputDto.setCustomerNo(returnOrder.getUserNo());
                    } else {
                        inputDto.setCustomerNo(returnOrder.getCustomerNo());
                    }
                } else {
                    String endUserFromRcvMaster = getEndUserFromRcvMaster(returnOrder.getOrderNo());
                    inputDto.setCustomerNo(endUserFromRcvMaster);
                }
                inputDto.setItemNo(returnOrder.getItemNo());//项号
                inputDto.setOrderNo(orderNoInfo.getOrderNo());//订单号
                inputDto.setOrderItem(orderNoInfo.getItemNo());//项号
                inputDto.setSplitItemNo(splitNo);//拆分项
                inputDto.setPsnNo(loginAuthDto.getUserNo());
                inputDto.setToUserStock(returnOrder.getToUserStock() == 1 ? true : false);
                log.info("退货确认订单号:" + orderNoInfo.getOrderNo() + ",项号:" + orderNoInfo.getItemNo() + ",拆分项号:" + splitNo);
                returnOrderDtos.add(inputDto);
                splitNo++;//拆分项自增
            }

        } else {
            //生成在途数据用于物流收货
            CreInvMoveForReturnOrderDto inputDto = new CreInvMoveForReturnOrderDto();
            //前端填写仓库号
            inputDto.setWarehouseCode(returnOrder.getRcvWarehouseCode());
            inputDto.setQty(returnOrder.getReturnQty());//良品rcv_fineQty
            inputDto.setModelNo(returnOrder.getModelNo());
            inputDto.setApplyNo(returnOrder.getApplyNo());
            if (returnOrder.getOrderType() == 3 || returnOrder.getOrderNo().startsWith("RC")) {
                if (StringUtils.isNotBlank(returnOrder.getUserNo())) {
                    inputDto.setCustomerNo(returnOrder.getUserNo());
                } else {
                    inputDto.setCustomerNo(returnOrder.getCustomerNo());
                }
            } else {
                String endUserFromRcvMaster = getEndUserFromRcvMaster(returnOrder.getOrderNo());
                inputDto.setCustomerNo(endUserFromRcvMaster);
            }
            inputDto.setItemNo(returnOrder.getItemNo());//项号
            inputDto.setOrderNo(orderNoInfo.getOrderNo());//订单号
            inputDto.setOrderItem(orderNoInfo.getItemNo());//项号
            inputDto.setSplitItemNo(orderNoInfo.getSplitItem());//拆分项
            inputDto.setPsnNo(loginAuthDto.getUserNo());
            inputDto.setToUserStock(returnOrder.getToUserStock() == 1 ? true : false);
            returnOrderDtos.add(inputDto);
        }


        //按单申请生成一个退货单发票号
        if (returnOrderDtos.size() > 0) {
            CommonResult<String> result = opsWmDispatchForOrderFeignApi.handReturnOrderConfirm(returnOrderDtos);
            log.info("[退货管理]生成在途数据用于物流收货 : " + JSONObject.toJSONString(result) + " 请求参数: " + JSONObject.toJSONString(returnOrderDtos));
            if (!result.isSuccess()) {
                // return ResultVo.failure(result.getMessage());
                throw new RuntimeException("[退货管理]生成在途数据用于物流收货异常:" + result.getMessage());
            }
        }

        return ResultVo.success("[退货管理]生成在途数据成功");
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> importInvoiceDataToEvent(String orderNo, String applyNo) {

        LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ReturnOrderDO::getOrderNo, orderNo);
        queryWrapper.eq(ReturnOrderDO::getApplyNo, applyNo);
        queryWrapper.eq(ReturnOrderDO::getStatus, 5);
        queryWrapper.orderByDesc(ReturnOrderDO::getUpdateTime);//取最新的一条
        List<ReturnOrderDO> returnOrderDOList = returnOrderMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(returnOrderDOList)) {
            return ResultVo.failure("退货表不存在:" + orderNo);
        }
        ReturnOrderDO orderDO = returnOrderDOList.get(0);
        ResultVo<String> resultVo = csStockApplyService.updateSalesInvoiceMidInfo(orderDO);

        if (!resultVo.isSuccess()) {
            throw new RuntimeException("保存进event失败" + orderNo + "," + resultVo.getMessage());
        }

        ResultVo<String> confirm = this.returnOrderConfirm(applyNo, orderNo, 5);
        if (!confirm.isSuccess()) {
            throw new RuntimeException(confirm.getMessage());
        }

        orderDO.setStatus(6);
        orderDO.setUpdateTime(new Date());
        returnOrderMapper.updateById(orderDO);
        int insert = insertEvent(orderDO);
        if (insert != 1) {
            throw new RuntimeException("保存进event表失败" + orderDO.getOrderNo());
        }
        return ResultVo.success("成功");
    }
    private int insertEvent(ReturnOrderDO orderDO) {
        ReturnOrderJsonVO jsonVO = new ReturnOrderJsonVO();
        jsonVO.setQuantity(String.valueOf(orderDO.getReturnQty()));
        jsonVO.setOptDate(DateUtil.dateToString(orderDO.getApplyTime()));
        String jsonString = JSON.toJSONString(jsonVO);

        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderDO.getOrderNo());

        OpsEventBusDO eventBusDO = new OpsEventBusDO();
        eventBusDO.setOrderId(orderNoInfo.getOrderNo());
        eventBusDO.setOrderItem(String.valueOf(orderNoInfo.getItemNo()));
        eventBusDO.setDealFlag(0);
        eventBusDO.setCreator("shareapp");
        eventBusDO.setCreTime(new Date());
        eventBusDO.setEventCode("CUSTOMER_ORDER_RETURN");
        eventBusDO.setVersion(0);
        eventBusDO.setParams(jsonString);
        return eventService.insert(eventBusDO);
    }

    private int insertEvent(String orderNo,Integer returnQty,Date applyTime) {
        ReturnOrderJsonVO jsonVO = new ReturnOrderJsonVO();
        jsonVO.setQuantity(String.valueOf(returnQty));
        jsonVO.setOptDate(DateUtil.dateToString(applyTime));
        String jsonString = JSON.toJSONString(jsonVO);

        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderNo);

        OpsEventBusDO eventBusDO = new OpsEventBusDO();
        eventBusDO.setOrderId(orderNoInfo.getOrderNo());
        eventBusDO.setOrderItem(String.valueOf(orderNoInfo.getItemNo()));
        eventBusDO.setDealFlag(0);
        eventBusDO.setCreator("shareapp-receiveGoods");
        eventBusDO.setCreTime(new Date());
        eventBusDO.setEventCode("CUSTOMER_ORDER_RETURN");
        eventBusDO.setVersion(0);
        eventBusDO.setParams(jsonString);
        return eventService.insert(eventBusDO);
    }

    /**
     * 将退货已开票的状态，更新到申请表 状态从5仓库已收货改成6完成退货
     * 同步导入salesinvoice_mid_info state_code=0的字段，取后改成1
     * <p>
     * applyno + applyno_item 对应退货申请的申请号和项号
     */
    @Override
    public ResultVo<String> importInvoiceData() {
        LambdaQueryWrapper<SalesInvoiceMidInfoDO> query = new LambdaQueryWrapper<>();
        query.eq(SalesInvoiceMidInfoDO::getStateCode, 0);
        query.eq(SalesInvoiceMidInfoDO::getIsNew, 1);
        List<SalesInvoiceMidInfoDO> list = salesInvoiceMidInfoMapper.selectList(query);

        LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        for (SalesInvoiceMidInfoDO infoDO : list) {
            queryWrapper.clear();
            queryWrapper.eq(ReturnOrderDO::getApplyNo, infoDO.getApplyNo());
            queryWrapper.eq(ReturnOrderDO::getItemNo, infoDO.getApplynoItem());
            queryWrapper.eq(ReturnOrderDO::getStatus, 5);// 仓库已收货
            queryWrapper.orderByDesc(ReturnOrderDO::getUpdateTime);//取最新的一条
            List<ReturnOrderDO> returnOrderDOList = returnOrderMapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(returnOrderDOList)) {
                continue;
            }
            ReturnOrderDO orderDO = returnOrderDOList.get(0);
            ResultVo<String> resultVo = this.importInvoiceDataToEvent(orderDO.getOrderNo(), orderDO.getApplyNo());
        }
        return ResultVo.success("同步成功");
    }

    private ResultVo<String> returnWTOrderToWMS(List<ReturnOrderDO> doList) {
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        WmWTDoConfirmDto dto;
        UserDto user;
        WmWTDoItemConfirmDto confirmDto;
        for (ReturnOrderDO orderDO : doList) {
            log.info("服务备库退货数据推送:" + orderDO.getOrderNo());
            long time1 = System.currentTimeMillis() / 1000;
            user = new UserDto();
            user.setUserName(userDTO.getUserName());
            user.setIp(IpUtil.getIpAddress());
            //组报文，调用wm委托在库出库接口
            dto = new WmWTDoConfirmDto();
            //用十位单号查doId
            OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderDO.getOrderNo());
            CsExpdetailDO expdetailDO = csExpdetailMapper.getCsReturnDoId(orderNoInfo.getOrderNo(), String.valueOf(orderNoInfo.getItemNo()));
            if (expdetailDO == null) {
                throw new RuntimeException("[服务备库退货]未查询到DOID");
            }
            dto.setDeliveryOrderCode(expdetailDO.getDoId());
            dto.setWarehouseCode(orderDO.getRcvWarehouseCode());
            dto.setInvoice(orderDO.getExpressNo());
            dto.setSender(orderDO.getApplicant());
            dto.setUserDto(user);
            confirmDto = new WmWTDoItemConfirmDto();
            confirmDto.setBarCode("RC" + time1);
            confirmDto.setQty(Double.valueOf(orderDO.getReturnQty()));
            confirmDto.setLogisticsCode("SF");
            confirmDto.setExpressCode(orderDO.getOrderNo());
            long time2 = System.currentTimeMillis() / 1000;
            confirmDto.setPackageCode("RC" + time2);
            dto.setItems(confirmDto);
            CommonResult commonResult = opsWmFeignApi.wmWTDoConfirm(dto);
            if (!commonResult.isSuccess()) {
                throw new RuntimeException(commonResult.getMessage());
            }
        }
        // 调用wm退货ro处理接口推送至物流
        ResultVo<String> resultVo = this.returnOrderListToWMS(doList);
        if (!resultVo.isSuccess()) {
            throw new RuntimeException(resultVo.getMessage());
        }

        return ResultVo.success("同步成功");
    }

    @Override
    public ResultVo<List<WareHouseInfo>> getWareHouseByCustomerNo(String customerNo) {

        if (StringUtils.isBlank(customerNo)) {
            return ResultVo.failure("客户代码不可为空");
        }
        CustomerVO customerInfoByNo = commonService.getCustomerInfoByNo(customerNo);
        if (customerInfoByNo == null || StringUtils.isBlank(customerInfoByNo.getHRUnitId())) {
            return ResultVo.failure("根据客户获取营业所失败");
        }
        // 根据holon所转营业所
        String hrUnitId = customerInfoByNo.getHRUnitId();

        ResultVo<String> deptNoByHRSalesDeptNo = commonServiceFeignApi.getDeptNoByHRSalesDeptNo(hrUnitId);
        if (!deptNoByHRSalesDeptNo.isSuccess()) {
            return ResultVo.failure("获取营业所失败");
        }

        List<WareHouseInfo> wareHouseType = opsWarehouseSalesBranchConfigMapper.getWareHouseInfoByDeptNo(deptNoByHRSalesDeptNo.getData());
        return ResultVo.success(wareHouseType);
    }

    /**
     * 收货登记
     *
     * @param reGisterDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> receiveGoods(RcvOrderReGisterDTO reGisterDTO) {
        //1.检查状态数据
        if (reGisterDTO == null || StringUtils.isBlank(reGisterDTO.getApplyNo())
                || StringUtils.isBlank(reGisterDTO.getOrderNo()) || StringUtils.isBlank(reGisterDTO.getModelNo())) {
            return ResultVo.failure("申请号/订单号/型号.不可为空");
        }
        if (StringUtils.isBlank(reGisterDTO.getWarehouseCode())) {
            return ResultVo.failure("请选择库房");
        }
        if (reGisterDTO.getGoodNumber() == null) {
            reGisterDTO.setGoodNumber(0);
        }
        if (reGisterDTO.getBadNumber() == null) {
            reGisterDTO.setBadNumber(0);
        }
        if (reGisterDTO.getGoodNumber() + reGisterDTO.getBadNumber() == 0) {
            return ResultVo.failure("收货时,退货数量不可是0");
        }
        if (reGisterDTO.getGoodNumber() + reGisterDTO.getBadNumber() > reGisterDTO.getApplyNumber()) {
            return ResultVo.failure("良品数量+不良品数量不能大于申请数量!");
        }

        // 2.1.销售订单写入开票中间表
        LoginUserDTO loginAuthDto = SMCApp.getLoginAuthDto();
        int count = 0;
        int notRcvNum = 0;
        int statusWithNotRcvOrderNum = 0;
        StringBuilder notRcvOrderNo = new StringBuilder(); // 拆分型号数量不可被整除的单据
        StringBuilder notReturnOrder = new StringBuilder(); // 状态不能进行收货的订单
        // 整单接收
        if (reGisterDTO.getIsRcvTogether()) {
            // 查询该申请号下所有的退货单
            LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ReturnOrderDO::getApplyNo, reGisterDTO.getApplyNo());
            queryWrapper.eq(ReturnOrderDO::getStatus, 3);
            List<ReturnOrderDO> returnOrderDOList = returnOrderMapper.selectList(queryWrapper);
            if (CollectionUtil.isEmpty(returnOrderDOList)) {
                return ResultVo.failure(reGisterDTO.getApplyNo() + "该退货申请不存在.");
            }
            for (ReturnOrderDO returnOrder : returnOrderDOList) {

                if (returnOrder.getStatus() != 3) {
                    statusWithNotRcvOrderNum++;
                    notReturnOrder.append(returnOrder.getOrderNo() + ";");
                    continue;
                }
                if (returnOrder.getOrderType() == null) {
                    return ResultVo.failure(returnOrder.getOrderNo() + "数据错误,订单类型不可为空.");
                }
                if (returnOrder.getOrderType() != 1 && returnOrder.getOrderType() != 3 && returnOrder.getOrderType() != 9
                        && returnOrder.getOrderType() != 11 && returnOrder.getOrderType() != 12) {
                    if (returnOrder.getOrderType() != null) {
                        return ResultVo.failure(returnOrder.getOrderNo() + "订单类型: " + OrderTypeEnum.getCodeName(String.valueOf(returnOrder.getOrderType())) + "该订单类型不可进行退货");
                    } else {
                        return ResultVo.failure(returnOrder.getOrderNo() + "订单类型为空,请检查数据是否正确.");
                    }
                }

                /**
                 * 判断拆分型号的数量能够整除
                 * 例如 一个单有3个拆分型号 计算数量时, 如果不能被整除 则该单都不可以收
                 */
                boolean canReturnBySplitModelNo = isCanReturnBySplitModelNo(returnOrder);
                if (!canReturnBySplitModelNo) {
                    notRcvNum++;
                    notRcvOrderNo.append(returnOrder.getOrderNo() + ";");
                    continue;
                }

                // 整单接收 收货数默认等于申请数量
                reGisterDTO.setGoodNumber(returnOrder.getApplyQty());
                reGisterDTO.setBadNumber(0);

                // 判断该订单的总退货数量是否已经大于了下单数量
                queryWrapper.clear();
                queryWrapper.eq(ReturnOrderDO::getOrderNo, returnOrder.getOrderNo()).in(ReturnOrderDO::getStatus, "5", "6");
                List<ReturnOrderDO> list = returnOrderMapper.selectList(queryWrapper);
                Integer number = reGisterDTO.getGoodNumber();
                int sum = list.stream().mapToInt(item -> Optional.ofNullable(item.getReturnQty()).orElse(0)).sum();
                if (sum + number > returnOrder.getOrderQty()) {
                    return ResultVo.failure("该订单的总退货数量大于下单数量，不可退货");
                }

                try {
                    // 写入开票中间表 委托在库补库单不需退开票
                    if (returnOrder.getOrderType() != 3) {

                        if (returnOrder.getRcvFineqty() != null && returnOrder.getReturnFee() != null && returnOrder.getSalesPrice() != null) {
                            if (returnOrder.getFeeRate() == null || returnOrder.getFeeRate().compareTo(BigDecimal.ZERO) == 0) {
                                BigDecimal multiply = (returnOrder.getReturnFee().divide(returnOrder.getSalesPrice(), 3, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(returnOrder.getRcvFineqty()));
                                returnOrder.setFeeRate(multiply);
                            }
                        }
                        //样品订单无发票号不写入
                        if (returnOrder.getOrderType() == 9 && StringUtils.isBlank(returnOrder.getInvoiceNo())) {
                            log.info("退货确认:" + returnOrder.getOrderNo() + "不写入开票表");
                        } else {
                            // 2.1.销售订单和样品订单写入开票中间表
                            ResultVo<String> resultVo1 = salesInvoiceService.insertSalesInvoiceMidInfo(returnOrder, reGisterDTO);
                            if (!resultVo1.isSuccess()) {
                                throw new BusinessException(resultVo1.getMessage());
                                // throw new RuntimeException(resultVo1.getMessage());
                            }
                        }
                    }
                    // 写入样品结转表 (数量负数)
                    // 2.2.样品订单接入结转表
                    if (returnOrder.getOrderType() == 9) {
                        try {
                            returnOrder.setRcvFineqty(reGisterDTO.getGoodNumber());
                            sampleBalService.insertSampleBal(returnOrder);
                        } catch (Exception e) {
                            log.error("样品退货写入结转表发生异常:", e);
                            log.error("样品退货写入结转表失败" + JSON.toJSONString(returnOrder));
                            throw new BusinessException("样品退货写入结转表失败.", e);
                            // throw new RuntimeException("样品退货写入结转表失败.");
                        }
                    }

                    // 2.3委托在库退货订单调用调拨do接口,减委托在库库存
                    if (returnOrder.getOrderType() == 3) {
                        returnOrder.setReturnQty(reGisterDTO.getGoodNumber());
                        returnOrder.setRcvWarehouseCode(reGisterDTO.getWarehouseCode());
                        returnOrder.setExpressNo(reGisterDTO.getExpressNo());
//                        ResultVo<String> resultVo = this.returnWTOrderToWMS1(returnOrder);
//                        if (!resultVo.isSuccess()) {
//                            throw new BusinessException(resultVo.getMessage());
//                        }
                    }

                    // 2.4 样品订单直接生成物流在途数据
                    if (returnOrder.getOrderType() == 9 && StringUtils.isBlank(returnOrder.getInvoiceNo())) {
                        returnOrder.setReturnQty(reGisterDTO.getGoodNumber());
                        returnOrder.setRcvWarehouseCode(reGisterDTO.getWarehouseCode());
                        //直接改接单表状态和数量
                        csStockApplyService.updateSalesInvoiceMidInfo(returnOrder);
                        ResultVo<String> resultVo = this.returnOrderToWMS(returnOrder);
                        if (!resultVo.isSuccess()) {
                            throw new BusinessException(resultVo.getMessage());
                            // throw new RuntimeException(resultVo.getMessage());
                        }
                    }
                    if (returnOrder.getOrderType() == 9) {
                        // 2.5  将签收日期和签收数量回改到结转申请确认表
                        LambdaUpdateWrapper<SampleBalApplyDO> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper
                                .eq(SampleBalApplyDO::getOrderNo,returnOrder.getOrderNo())
                                .eq(SampleBalApplyDO::getHandStatus, SampleBalApplyHandStatusEnum.alreadyHand.getCode())
                                .set(SampleBalApplyDO::getSignQty,reGisterDTO.getGoodNumber())
                                .set(SampleBalApplyDO::getSignTime, null == reGisterDTO.getDlvDate() ? new Date() : reGisterDTO.getDlvDate())
                                .set(SampleBalApplyDO::getUpdateTime,new Date());
                        sampleBalApplyMapper.update(null,updateWrapper);
                    }

                } catch (RuntimeException e) {
                    log.error("收货处理异常信息: " + e);
                    throw new BusinessException(e.getMessage(), e);
                }

                // 3.退货回调门户接口 && 4.更新收货数量和状态
                ResultVo<String> resultVo = confirmReturnOrder1(returnOrder, reGisterDTO, loginAuthDto);
                if (!resultVo.isSuccess()) {
                    return ResultVo.failure(resultVo.getMessage());
                }
                count++;
                // 15680去掉确定收货时写入的全部事件，应该只保留样品订单的事件2025-08-15
                //insertEvent(returnOrder);
            }
        } else { // 非整单接收
            LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ReturnOrderDO::getId, reGisterDTO.getId());
            ReturnOrderDO returnOrderDO = returnOrderMapper.selectOne(queryWrapper);
            if (returnOrderDO == null) {
                return ResultVo.failure(reGisterDTO.getApplyNo() + "该退货申请不存在.");
            }
            if (returnOrderDO.getOrderType() == null) {
                return ResultVo.failure(returnOrderDO.getOrderNo() + "数据错误,订单类型不可为空.");
            }
            if (returnOrderDO.getOrderType() != 1 && returnOrderDO.getOrderType() != 3 && returnOrderDO.getOrderType() != 9
                    && returnOrderDO.getOrderType() != 11 && returnOrderDO.getOrderType() != 12) {
                return ResultVo.failure(OrderTypeEnum.getCodeName(returnOrderDO.getOrderType().toString()) + "该订单类型不可进行退货");
            }

            if (returnOrderDO.getStatus() != 3) {
                return ResultVo.success("申请号:" + reGisterDTO.getApplyNo() + "订单号:" + reGisterDTO.getOrderNo() + "状态不是待收货，不可进行收货.");
            }

            boolean canReturnBySplitModelNo = isCanReturnBySplitModelNo(returnOrderDO);
            if (!canReturnBySplitModelNo) {
                return ResultVo.failure(returnOrderDO.getOrderNo() + "因拆分组装型号数量不能被整除,不可收货.");
            }

            // 判断该订单的总退货数量是否已经大于了下单数量
            queryWrapper.clear();
            queryWrapper.eq(ReturnOrderDO::getOrderNo, returnOrderDO.getOrderNo());
            queryWrapper.in(ReturnOrderDO::getStatus, "5", "6");
            List<ReturnOrderDO> list = returnOrderMapper.selectList(queryWrapper);
            Integer number = reGisterDTO.getGoodNumber();
            int sum = list.stream().mapToInt(item -> Optional.ofNullable(item.getReturnQty()).orElse(0)).sum();
            if (sum + number > returnOrderDO.getOrderQty()) {
                return ResultVo.failure("该订单的总退货数量大于下单数量，不可退货");
            }

            try {
                // 先回调门户 防止写入中间表后无法回滚
//                ResultVo<String> orderMH = this.dealReturnOrderMH(returnOrderDO, reGisterDTO);
//                if (!orderMH.isSuccess()) {
//                    throw new RuntimeException(orderMH.getMessage());
//                }
                // 写入开票中间表 委托在库补库单不需退开票
                if (returnOrderDO.getOrderType() != 3) {

                    if (returnOrderDO.getRcvFineqty() != null && returnOrderDO.getReturnFee() != null && returnOrderDO.getSalesPrice() != null) {
                        if (returnOrderDO.getFeeRate() == null || returnOrderDO.getFeeRate().compareTo(BigDecimal.ZERO) == 0) {
                            BigDecimal multiply = (returnOrderDO.getReturnFee().divide(returnOrderDO.getSalesPrice(), 3, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(returnOrderDO.getRcvFineqty()));
                            returnOrderDO.setFeeRate(multiply);
                        }
                    }
                    //样品订单无发票号不写入
                    if (returnOrderDO.getOrderType() == 9 && StringUtils.isBlank(returnOrderDO.getInvoiceNo())) {
                        log.info("退货确认:" + returnOrderDO.getOrderNo() + "不写入开票表");
                    } else {
                        // 2.1.销售订单写入开票中间表
                        ResultVo<String> resultVo1 = salesInvoiceService.insertSalesInvoiceMidInfo(returnOrderDO, reGisterDTO);
                        if (!resultVo1.isSuccess()) {
                            throw new BusinessException(resultVo1.getMessage());
                            // throw new RuntimeException(resultVo1.getMessage());
                        }
                    }
                }
                // 写入样品结转表 (数量负数)
                // 2.2.样品订单接入结转表
                if (returnOrderDO.getOrderType() == 9) {
                    try {
                        returnOrderDO.setRcvFineqty(reGisterDTO.getGoodNumber());
                        sampleBalService.insertSampleBal(returnOrderDO);
                    } catch (Exception e) {
                        log.error("样品退货写入结转表失败" + JSON.toJSONString(returnOrderDO));
                        throw new BusinessException("样品退货写入结转表失败.", e);
                        // throw new RuntimeException("样品退货写入结转表失败.");
                    }
                }

                // 2.3委托在库退货订单调用调拨do接口,减委托在库库存
                if (returnOrderDO.getOrderType() == 3) {
                    returnOrderDO.setReturnQty(reGisterDTO.getGoodNumber());
                    returnOrderDO.setRcvWarehouseCode(reGisterDTO.getWarehouseCode());
                    returnOrderDO.setExpressNo(reGisterDTO.getExpressNo());
//                    ResultVo<String> resultVo = this.returnWTOrderToWMS1(returnOrderDO);
//                    if (!resultVo.isSuccess()) {
//                        throw new BusinessException(resultVo.getMessage());
//                    }
                }

                // 2.4 样品订单无发票直接生成物流在途数据
                if (returnOrderDO.getOrderType() == 9 && StringUtils.isBlank(returnOrderDO.getInvoiceNo())) {
                    returnOrderDO.setReturnQty(reGisterDTO.getGoodNumber());
                    returnOrderDO.setRcvWarehouseCode(reGisterDTO.getWarehouseCode());
                    //直接改接单表状态和数量
                    csStockApplyService.updateSalesInvoiceMidInfo(returnOrderDO);
                    ResultVo<String> resultVo = this.returnOrderToWMS(returnOrderDO);
                    if (!resultVo.isSuccess()) {
                        throw new BusinessException(resultVo.getMessage());
                        // throw new RuntimeException(resultVo.getMessage());
                    }
                }
                if (returnOrderDO.getOrderType() == 9) {
                    // 2.5  将签收日期和签收数量回改到结转申请确认表
                    LambdaUpdateWrapper<SampleBalApplyDO> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper
                            .eq(SampleBalApplyDO::getOrderNo,returnOrderDO.getOrderNo())
                            .eq(SampleBalApplyDO::getHandStatus, SampleBalApplyHandStatusEnum.alreadyHand.getCode())
                            .set(SampleBalApplyDO::getSignQty,reGisterDTO.getGoodNumber())
                            .set(SampleBalApplyDO::getSignTime, null == reGisterDTO.getDlvDate() ? new Date() : reGisterDTO.getDlvDate())
                            .set(SampleBalApplyDO::getUpdateTime,new Date());
                    sampleBalApplyMapper.update(null,updateWrapper);
                }
            } catch (RuntimeException e) {
                log.error("收货登记发生异常: ", e);
                throw new BusinessException(e.getMessage(), e);
                // throw new RuntimeException(e.getMessage());
            }

            // 3.退货回调门户接口 && 4.更新收货数量和状态
            ResultVo<String> resultVo = confirmReturnOrder1(returnOrderDO, reGisterDTO, loginAuthDto);
            if (!resultVo.isSuccess()) {
                return ResultVo.failure(resultVo.getMessage());
            }
            count++;
            // 15680去掉确定收货时写入的全部事件，应该只保留样品订单的事件2025-08-15
            //insertEvent(returnOrder);
        }
        String returnMsg = "";
        if (notRcvNum > 0 && statusWithNotRcvOrderNum > 0) {
            returnMsg = count + "项,收货成功." + notRcvOrderNo.toString() + "因拆分组装型号数量不能被整除,不可收货." + notReturnOrder.toString() + "因状态不允许再次收货";
        } else if (notRcvNum > 0 && statusWithNotRcvOrderNum == 0) {
            returnMsg = count + "项,收货成功." + notRcvOrderNo.toString() + "因拆分组装型号数量不能被整除,不可收货.";
        } else if (notRcvNum == 0 && statusWithNotRcvOrderNum > 0) {
            returnMsg = count + "项,收货成功." + notReturnOrder.toString() + "因状态不允许再次收货";
        } else {
            returnMsg = count + "项,收货成功.";
        }

        return ResultVo.success(returnMsg);
    }

    /**
     * 1.先查中间表，找出所有未处理的，
     * 2.联查returnOrder表，通过申请号，不要已删单，不要委托在库，只要状态是5已收货的
     * 3.处理退货流程 todo
     * 4.更新中间表，更新退货表
     *
     * 1.查returnOrder表，只要委托在库，状态5已收货的
     * 2.委托在库处理退货流程 todo
     * 3.更新退货表
     * 4.委托订单退货事件写入
     * @return
     */
    @Override
    public ResultVo<String> impInvoiceResult() {
        // 1.先查中间表，找出所有未处理的：
        // is_new =1 财务已处理，state_code=0 还未下发至WMS
        LambdaQueryWrapper<SalesInvoiceMidInfoDO> query = new LambdaQueryWrapper<>();
        query.eq(SalesInvoiceMidInfoDO::getStateCode, 0);
        query.eq(SalesInvoiceMidInfoDO::getIsNew, 1);
        query.ne(SalesInvoiceMidInfoDO::getType, "DF");  // DF 删单二次审批 只删客单不删采购 type 是 DF
        List<SalesInvoiceMidInfoDO> list = salesInvoiceMidInfoMapper.selectList(query);
        LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        //2.收集returnOrder表数据：
        // 联查returnOrder表，通过申请号，不要已删单，不要委托在库，只要状态是5已收货的
        List<ReturnOrderDO> orderDOList = new ArrayList<>();
        for (SalesInvoiceMidInfoDO infoDO : list) {
            queryWrapper.clear();
            queryWrapper.eq(ReturnOrderDO::getApplyNo, infoDO.getApplyNo());
            queryWrapper.eq(ReturnOrderDO::getItemNo, infoDO.getApplynoItem());
            queryWrapper.ne(ReturnOrderDO::getStatus, 9);
            queryWrapper.ne(ReturnOrderDO::getOrderType, 3);
            ReturnOrderDO orderDO = returnOrderMapper.selectOne(queryWrapper);
            if (orderDO == null) {
                continue;
            }
            if (orderDO.getStatus() != 5) {
                continue;
            }
            orderDOList.add(orderDO);
        }
        //wm处理退货程序
        ResultVo<String> resultVo = this.returnOrderListToWMS(orderDOList);
        if (!resultVo.isSuccess()) {
            throw new RuntimeException(resultVo.getMessage());
        }

        for (ReturnOrderDO orderDO : orderDOList) {
            ResultVo<String> resultVo1 = csStockApplyService.updateSalesInvoiceMidInfo(orderDO);
            if (!resultVo1.isSuccess()) {
                throw new RuntimeException(resultVo1.getMessage());
            }
            orderDO.setStatus(6);
            orderDO.setInTime(new Date());
            orderDO.setUpdateTime(new Date());
            returnOrderMapper.updateById(orderDO);
            // 15680 2025-08-15 增加普通订单的退货事件写入:CUSTOMER_ORDER_RETURN
            insertEvent(orderDO);
        }
        //二.处理委托在库退货
        queryWrapper.clear();
        queryWrapper.eq(ReturnOrderDO::getOrderType, 3);
        queryWrapper.eq(ReturnOrderDO::getStatus, 5);
        List<ReturnOrderDO> doList = returnOrderMapper.selectList(queryWrapper);
        this.returnWTOrderToWMS(doList);
        for (ReturnOrderDO orderDO : doList) {
            orderDO.setStatus(6);
            orderDO.setInTime(new Date());
            orderDO.setUpdateTime(new Date());
            returnOrderMapper.updateById(orderDO);
        }
        //写入委托在库订单的退货事件:CUSTOMER_ORDER_RETURN
        for (ReturnOrderDO orderDO : doList) {
            insertEvent(orderDO);
        }
        return ResultVo.success("成功");
    }


    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> cancelReturnOrder(CancelReturnOrderVO cancelReturnOrderVO) {
        if (StringUtils.isBlank(cancelReturnOrderVO.getReason())) {
            return ResultVo.failure("取消原因不可为空!");
        }
        if (cancelReturnOrderVO.getIds() == null || cancelReturnOrderVO.getIds().length <= 0) {
            return ResultVo.failure("未传入ID");
        }

        LoginUserDTO loginAuthDto;
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto = new LoginUserDTO();
            loginAuthDto.setUserNo("opsCancel");
        }

        LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ReturnOrderDO::getId, Arrays.asList(cancelReturnOrderVO.getIds()));
        queryWrapper.ne(ReturnOrderDO::getOrderType, 3);

        List<ReturnOrderDO> list = returnOrderMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            return ResultVo.failure("未查询到可取消的退货订单");
        }

        Boolean cancelStatus = true;
        StringBuilder orderno = new StringBuilder();
        StringBuilder errorNo = new StringBuilder();
        StringBuilder errorNo1 = new StringBuilder();
        StringBuilder errorNo2 = new StringBuilder();
        LambdaUpdateWrapper<ReturnOrderDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<SalesInvoiceMidInfoDO> updateWrapper = new LambdaUpdateWrapper<>();
        for (ReturnOrderDO orderDO : list) {
            if (orderDO.getOrderType() == 9 && (loginAuthDto.getUserNo().equals(orderDO.getCreateUser()) || loginAuthDto.getUserNo().equals(orderDO.getApplicant()))) {
                if (orderDO.getStatus() == 3) {
                    lambdaUpdateWrapper.clear();
                    lambdaUpdateWrapper
                            .eq(ReturnOrderDO::getId, orderDO.getId())
                            .set(ReturnOrderDO::getStatus, 9)
                            .set(ReturnOrderDO::getUpdateTime, new Date())
                            .set(ReturnOrderDO::getUpdateUser, loginAuthDto.getUserNo())
                            .set(ReturnOrderDO::getRemark, cancelReturnOrderVO.getReason());
                    returnOrderMapper.update(null, lambdaUpdateWrapper);
                    orderno.append(orderDO.getOrderNo() + ";");
                    continue;
                }
            }

            if (orderDO.getStatus() != 5) {
                errorNo2.append(orderDO.getOrderNo() + ";");
                continue;
            }

            // 查询财务是否已经处理
            LambdaQueryWrapper<SalesInvoiceMidInfoDO> query = new LambdaQueryWrapper<>();
            query.eq(SalesInvoiceMidInfoDO::getApplyNo, orderDO.getApplyNo());
            query.eq(SalesInvoiceMidInfoDO::getApplynoItem, orderDO.getItemNo());

            SalesInvoiceMidInfoDO midInfoDO = salesInvoiceMidInfoMapper.selectOne(query);
            if (midInfoDO == null) {
                errorNo.append(orderDO.getOrderNo() + ";");
                continue;
            }
            if ("1".equals(midInfoDO.getIsNew())) {
                errorNo1.append(orderDO.getOrderNo() + ";");
                continue;
            }

            lambdaUpdateWrapper.clear();
            lambdaUpdateWrapper
                    .eq(ReturnOrderDO::getId, orderDO.getId())
                    .set(ReturnOrderDO::getStatus, 7)
                    .set(ReturnOrderDO::getUpdateTime, new Date())
                    .set(ReturnOrderDO::getUpdateUser, loginAuthDto.getUserNo())
                    .set(ReturnOrderDO::getRemark, cancelReturnOrderVO.getReason());
            returnOrderMapper.update(null, lambdaUpdateWrapper);
            orderno.append(orderDO.getOrderNo() + ";");

            updateWrapper.clear();
            updateWrapper.eq(SalesInvoiceMidInfoDO::getId, midInfoDO.getId())
                    .set(SalesInvoiceMidInfoDO::getUpdateTime, new Date())
                    .set(SalesInvoiceMidInfoDO::getIsNew, 9);
            salesInvoiceMidInfoMapper.update(null, updateWrapper);

            // 写入日志
            OrderLogVO orderLogVO = new OrderLogVO();
            orderLogVO.setOrderNo(orderDO.getOrderNo());
            orderLogVO.setOptTime(new Date());
            orderLogVO.setOptType(orderDO.getOrderType());
            orderLogVO.setDescription(loginAuthDto.getUserNo()+"取消退货");
            orderLogVO.setOptUserId(loginAuthDto.getUserNo());
            orderLogVO.setOptUserName(loginAuthDto.getUserName());
            orderLogFeignApi.addOrderLog(orderLogVO);

        }
        if (StringUtils.isNotBlank(orderno)) {
            orderno.insert(0, "订单: {");
            orderno.append("} 取消退货成功。");
        } else {
            cancelStatus = false;
        }
        if (StringUtils.isNotBlank(errorNo2)) {
            errorNo2.insert(0, "订单: {");
            errorNo2.append("} 状态不是仓库已收货，取消失败.");
            orderno.append(errorNo2);
        }
        if (StringUtils.isNotBlank(errorNo)) {
            errorNo.insert(0, "订单: {");
            errorNo.append("} 开票表中不存在。");
            orderno.append(errorNo);
        }
        if (StringUtils.isNotBlank(errorNo1)) {
            errorNo1.insert(0, "订单: {");
            errorNo1.append("} 财务已处理不可取消");
            orderno.append(errorNo1);
        }

        if (cancelStatus) {
            return ResultVo.success(orderno.toString());
        } else {
            return ResultVo.failure(orderno.toString());
        }
    }

    @Override
    public ResultVo<String> cancelReturnOrderToMenHu(CancelReturnToMenHuDTO cancelReturnToMenHuDTO) {

        if (cancelReturnToMenHuDTO == null || StringUtils.isBlank(cancelReturnToMenHuDTO.getApplyNo())
                || StringUtils.isBlank(cancelReturnToMenHuDTO.getOrderNo())) {
            return ResultVo.failure("申请号&订单号,不可为空");
        }


        return null;
    }

    @Override
    public ResultVo<String> updateReturnOrderStatus(UpdateOrderStatusDTO updateOrderStatusDTO) {

        if (updateOrderStatusDTO == null || StringUtils.isBlank(updateOrderStatusDTO.getApplyNo())) {
            return ResultVo.failure("暂无申请单");
        }

        log.info("进入门户审批完成修改退货状态接口: " + JSONObject.toJSONString(updateOrderStatusDTO));

        LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ReturnOrderDO::getApplyNo, updateOrderStatusDTO.getApplyNo());
        Integer count = returnOrderMapper.selectCount(queryWrapper);
        //  1) : 判断该申请是否存在ops
        if (count == 0) {
            return ResultVo.failure("ops未查到该申请,请重新提交申请 : " + updateOrderStatusDTO.getApplyNo());
        }

        //查询该申请号下是否有已收货的订单
        // bug10612 WuJiaWen 2023/04/26
        queryWrapper.in(ReturnOrderDO::getStatus, 5, 6);
        List<ReturnOrderDO> list = returnOrderMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            return ResultVo.failure("物流已收货，不允许取消:" + updateOrderStatusDTO.getApplyNo());
        }

        // 为1代表审批通过
        if (updateOrderStatusDTO.getApplyResult() == 1) {
            // 修改该退货申请的状态为待收货
            LambdaUpdateWrapper<ReturnOrderDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .eq(ReturnOrderDO::getApplyNo, updateOrderStatusDTO.getApplyNo())
                    .ne(ReturnOrderDO::getStatus,"9")
                    .set(ReturnOrderDO::getUpdateTime, new Date())
                    .set(ReturnOrderDO::getStatus, "3");
            try {
                returnOrderMapper.update(null, updateWrapper);
            } catch (Exception e) {
                log.error("退货申请" + updateOrderStatusDTO.getApplyNo() + "修改审批通过状态失败", e);
                return ResultVo.failure("退货申请" + updateOrderStatusDTO.getApplyNo() + "修改审批通过状态失败");
            }
            log.info("退货申请" + updateOrderStatusDTO.getApplyNo() + "修改审批通过状态");
            return ResultVo.success(updateOrderStatusDTO.getApplyNo() + "修改申请状态成功.");
        } else if (updateOrderStatusDTO.getApplyResult() == 2 || updateOrderStatusDTO.getApplyResult() == 3) {
            // 审批否决/作废, 修改状态为取消
            LambdaUpdateWrapper<ReturnOrderDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .eq(ReturnOrderDO::getApplyNo, updateOrderStatusDTO.getApplyNo())
                    .set(ReturnOrderDO::getUpdateTime, new Date())
                    .set(ReturnOrderDO::getStatus, "9");
//            if (updateOrderStatusDTO.getApplyResult() == 3) {
//                updateWrapper.eq(ReturnOrderDO::getStatus, 3);
//            }
            try {
                returnOrderMapper.update(null, updateWrapper);
            } catch (Exception e) {
                log.error("退货申请" + updateOrderStatusDTO.getApplyNo() + "修改取消状态失败", e);
                return ResultVo.failure("退货申请" + updateOrderStatusDTO.getApplyNo() + "修改取消状态失败");
            }
            log.info("退货申请" + updateOrderStatusDTO.getApplyNo() + "修改申请状态成功");
            return ResultVo.success(updateOrderStatusDTO.getApplyNo() + "修改申请状态成功.");
        } else {
            log.info(updateOrderStatusDTO.getApplyNo() + "请传入有效编码进行变更状态 1:已通过、2、已否决、3、已作废 ");
            return ResultVo.failure(updateOrderStatusDTO.getApplyNo() + "请传入有效编码进行变更状态 1:已通过、2、已否决、3、已作废 ");
        }
    }

//    public ResultVo<String> dealReturnOrderMH(ReturnOrderDO returnOrder, RcvOrderReGisterDTO reGisterDTO) {
//        // 3.退货回调门户接口
//        List<ReturnOrderBackCallVO> orderBackCallVOS = new ArrayList<>();
//        ReturnOrderBackCallVO item = new ReturnOrderBackCallVO();
//        item.setApplyNo(returnOrder.getApplyNo());
//        item.setOrderNo(returnOrder.getOrderNo());
//        item.setAllowReturnQuantity(reGisterDTO.getGoodNumber());
//        item.setNotAllowReturnQuantity(reGisterDTO.getBadNumber());
//        item.setShelves(reGisterDTO.getLocationNo());
//        item.setSignDate(new Date());
//        item.setDealDate(new Date());
//        item.setStatus(5); // 已收货
//        item.setRequestWareHouseCode(returnOrder.getRequestWarehouseCode());
//        orderBackCallVOS.add(item);
//        ResultVo<String> mhResultVo = dealReturnOrder(orderBackCallVOS);
//        log.info("[门户回调接口]退货回调门户接口 响应: " + JSONObject.toJSONString(mhResultVo) + "  参数: " + JSONObject.toJSONString(orderBackCallVOS));
//        if (!mhResultVo.isSuccess()) {
//            throw new RuntimeException("退货回调门户接口响应失败:" + mhResultVo.getMessage());
//        }
//        return ResultVo.success("回调门户成功");
//    }

    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> confirmReturnOrder1(ReturnOrderDO returnOrder, RcvOrderReGisterDTO reGisterDTO, LoginUserDTO loginAuthDto) {
        try {

            // 回改退货单(良品数量.坏品数量.退货入库数量)
            LambdaUpdateWrapper<ReturnOrderDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(ReturnOrderDO::getId, returnOrder.getId());
            if (returnOrder.getOrderType() == 9 && StringUtils.isBlank(returnOrder.getInvoiceNo())) {
                updateWrapper.set(ReturnOrderDO::getInTime, new Date());
                updateWrapper.set(ReturnOrderDO::getStatus, 6); // 完成退货
            } else {
                updateWrapper.set(ReturnOrderDO::getStatus, 5); // 仓库已收货
            }
            //add by WuJiaWen 2022/10/19 bug 8377
            String receiveLog = "收货人:" + (StringUtils.isNotBlank(loginAuthDto.getUserNo()) ? loginAuthDto.getUserNo() : "") +
                    "收良品数量" + (reGisterDTO.getGoodNumber() == null ? 0 : reGisterDTO.getGoodNumber()) +
                    ",收不良品数量" + (reGisterDTO.getBadNumber() == null ? 0 : reGisterDTO.getBadNumber()) +
                    ",收货仓库:" + reGisterDTO.getWarehouseCode();
            updateWrapper
                    .set(ReturnOrderDO::getRcvFineqty, reGisterDTO.getGoodNumber() == null ? 0 : reGisterDTO.getGoodNumber()) // 收到良品数量
                    .set(ReturnOrderDO::getRcvBadqty, reGisterDTO.getBadNumber() == null ? 0 : reGisterDTO.getBadNumber()) // 收到坏品数量
                    .set(ReturnOrderDO::getReturnQty, reGisterDTO.getGoodNumber() == null ? 0 : reGisterDTO.getGoodNumber()) // 退货入库数量默认等于良品数量
                    .set(ReturnOrderDO::getRcvWarehouseCode, reGisterDTO.getWarehouseCode())
                    .set(ReturnOrderDO::getLocationNo, reGisterDTO.getLocationNo())
                    .set(ReturnOrderDO::getUpdateUser, StringUtils.isNotBlank(loginAuthDto.getUserNo()) ? loginAuthDto.getUserNo() : "")
                    .set(ReturnOrderDO::getConsignee, StringUtils.isNotBlank(loginAuthDto.getUserNo()) ? loginAuthDto.getUserNo() : "")
                    .set(ReturnOrderDO::getReceiveTime, null == reGisterDTO.getDlvDate() ? new Date() : reGisterDTO.getDlvDate())
                    .set(ReturnOrderDO::getUpdateTime, new Date())
                    .set(ReturnOrderDO::getReceiveNote, receiveLog)
                    .set(ReturnOrderDO::getExpressNo, reGisterDTO.getExpressNo());
            int update = returnOrderMapper.update(null, updateWrapper);
            if (update == 0) {
                throw new RuntimeException(reGisterDTO.getApplyNo() + "组装订单收货失败");
            }
            //15680写入样品订单的退货事件：CUSTOMER_ORDER_RETURN
            // 样品订单完成退货6，应该写入事件，但其他订单没有完成退货5，不应该写入事件 2025-08-15
            //报文的数量：此处的returnOrder实体是更新前的实体，应该取reGisterDTO.getGoodNumber()
            if (returnOrder.getOrderType() == 9 && StringUtils.isBlank(returnOrder.getInvoiceNo())) {
                int returnQty = reGisterDTO.getGoodNumber() == null ? 0 : reGisterDTO.getGoodNumber();
                insertEvent(returnOrder.getOrderNo(), returnQty, returnOrder.getApplyTime());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResultVo.success();
    }


    @Override
    public ResultVo<String> dealReturnOrderToMH() {
        LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ReturnOrderDO::getExtStatus, 0);
//        queryWrapper.in(ReturnOrderDO::getStatus, 5,6);
        queryWrapper.eq(ReturnOrderDO::getStatus, 5);
        queryWrapper.ne(ReturnOrderDO::getOrderType, 3);
        List<ReturnOrderDO> list = returnOrderMapper.selectList(queryWrapper);

        LambdaUpdateWrapper<ReturnOrderDO> updateWrapper = new LambdaUpdateWrapper<>();
        for (ReturnOrderDO orderDO : list) {
            List<ReturnOrderBackCallVO> orderBackCallVOS = new ArrayList<>();
            ReturnOrderBackCallVO item = new ReturnOrderBackCallVO();
            if ("GZIt2".equals(orderDO.getCreateUser()) && orderDO.getApplyNo().startsWith("TH")) {
                item.setApplyNo("TH_2O" + orderDO.getApplyNo());
            } else {
                item.setApplyNo(orderDO.getApplyNo());
            }
            item.setOrderNo(orderDO.getOrderNo());
            item.setAllowReturnQuantity(orderDO.getRcvFineqty());
            item.setNotAllowReturnQuantity(orderDO.getRcvBadqty());
            item.setShelves(orderDO.getLocationNo());
            item.setSignDate(new Date());
            item.setDealDate(new Date());
            item.setStatus(5); // 已收货
            item.setRequestWareHouseCode(orderDO.getRequestWarehouseCode());
            orderBackCallVOS.add(item);

            ResultVo<String> mhResultVo = dealReturnOrder(orderBackCallVOS);
            log.info("[门户回调接口]退货回调门户接口 响应: " + JSONObject.toJSONString(mhResultVo) + "  参数: " + JSONObject.toJSONString(orderBackCallVOS));
            if (!mhResultVo.isSuccess()) {
                throw new RuntimeException("退货回调门户接口响应失败:" + mhResultVo.getMessage());
            }
            updateWrapper.clear();
            updateWrapper.eq(ReturnOrderDO::getId, orderDO.getId());
            updateWrapper.set(ReturnOrderDO::getExtStatus, 1);
            updateWrapper.set(ReturnOrderDO::getUpdateTime,new Date());
            returnOrderMapper.update(null, updateWrapper);
        }

        // 退货取消的
        queryWrapper.clear();
        queryWrapper.eq(ReturnOrderDO::getExtStatus, 1);
//        queryWrapper.eq(ReturnOrderDO::getStatus, 7);
        queryWrapper.in(ReturnOrderDO::getStatus, 6, 7);
        queryWrapper.ne(ReturnOrderDO::getOrderType, 3);
        List<ReturnOrderDO> doList = returnOrderMapper.selectList(queryWrapper);

        List<ReturnOrderBackCallVO> backCallVOList;
        if (CollectionUtil.isEmpty(doList)) {
            return ResultVo.success("定时回调门户成功");
        }

        ReturnOrderBackCallVO backCallVO;
        for (ReturnOrderDO orderDO : doList) {
            backCallVOList = new ArrayList<>();
            backCallVO = new ReturnOrderBackCallVO();
            if (orderDO.getStatus() == 6) {
                backCallVO.setStatus(7);
            }
            if (orderDO.getStatus() == 7) {
                backCallVO.setStatus(6);
            }
            if ("GZIt2".equals(orderDO.getCreateUser()) && orderDO.getApplyNo().startsWith("TH")) {
                backCallVO.setApplyNo("TH_2O" + orderDO.getApplyNo());
            } else {
                backCallVO.setApplyNo(orderDO.getApplyNo());
            }
            backCallVO.setApplyNo(orderDO.getApplyNo());
            backCallVO.setOrderNo(orderDO.getOrderNo());
            backCallVO.setAllowReturnQuantity(orderDO.getRcvFineqty());
            backCallVO.setNotAllowReturnQuantity(orderDO.getRcvBadqty());
            backCallVO.setShelves(orderDO.getLocationNo());
            backCallVO.setRequestWareHouseCode(orderDO.getRequestWarehouseCode());
            backCallVO.setSignDate(new Date());
            backCallVO.setDealDate(new Date());

            backCallVOList.add(backCallVO);

            ResultVo<String> resultVo = dealReturnOrder(backCallVOList);
            if (!resultVo.isSuccess()) {
                throw new RuntimeException("退货回调门户接口响应失败:" + resultVo.getMessage());
            }

            updateWrapper.clear();
            updateWrapper.eq(ReturnOrderDO::getId, orderDO.getId());
            updateWrapper.set(ReturnOrderDO::getExtStatus, 2).set(ReturnOrderDO::getUpdateTime,new Date());
            returnOrderMapper.update(null, updateWrapper);
        }

        return ResultVo.success("定时回调门户成功");
    }

    @Override
    public ResultVo<String> cancelCsReturnOrder(String applyNo, String orderNo) {
        LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ReturnOrderDO::getApplyNo, applyNo);
        queryWrapper.eq(ReturnOrderDO::getOrderNo, orderNo);
        queryWrapper.eq(ReturnOrderDO::getStatus, 4);

        ReturnOrderDO orderDO = returnOrderMapper.selectOne(queryWrapper);
        if (orderDO == null) {
            return ResultVo.failure("拒收失败，请检查状态是否有误");
        }

        TransOrderCancelDTO dto = new TransOrderCancelDTO();
        dto.setTransType(1);
        dto.setReason("退货包裹拒收");
        dto.setTransNo(orderDO.getOrderNo().split("-")[0]);
        dto.setItemNo(orderDO.getItemNo());
        ResultVo<String> resultVo = transStockFeignApi.cancelTransOrder(dto);
        if (!resultVo.isSuccess()) {
            return ResultVo.failure(resultVo.getMessage());
        }

        return ResultVo.success("取消成功");
    }

    @Override
    public ResultVo<String> creatApplyNo() {
        ResultVo<String> resultVo = commonServiceFeignApi.generatorBillNo("22");

        if (!resultVo.isSuccess() || StringUtils.isBlank(resultVo.getData())) {
            return ResultVo.failure("退货申请号生成失败!");
        }
        return ResultVo.success(resultVo.getData());
    }

    @Override
    public ResultVo<String> addReturnOrder(String jsonData, MultipartFile[] fileList) {
        ReturnOrderApplyDTO dto = JSON.parseObject(jsonData, ReturnOrderApplyDTO.class);
        if (StringUtils.isBlank(dto.getApplyNo())) {
            return ResultVo.failure("请输入申请号");
        }
        if (StringUtils.isBlank(dto.getInvoiceNo()) && dto.getInvoiceDate() != null) {
            return ResultVo.failure("发票号不能为空");
        }
        if (dto.getInvoiceDate() == null && StringUtils.isNotBlank(dto.getInvoiceNo())) {
            return ResultVo.failure("开票日期不能为空");
        }
        ResultVo<RcvOrderViewVO> resultVo = receiveOrderServiceFeignApi.getRcvOrderDataByNo(dto.getOrderNo());
        if (!resultVo.isSuccess()) {
            return ResultVo.failure("订单号错误，请检查");
        }
        int applyQty = 0;
        int returnQty = 0;
        LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(ReturnOrderDO::getApplyNo, dto.getApplyNo());
        queryWrapper.eq(ReturnOrderDO::getOrderNo, dto.getOrderNo());
        ReturnOrderDO orderdo = returnOrderMapper.selectOne(queryWrapper);
        if (orderdo != null && orderdo.getItemNo() != dto.getItemNo()) {
            return ResultVo.failure("该申请号下已存在订单号:" + dto.getOrderNo() + "，请勿重复添加！");
        }

        queryWrapper.clear();
        queryWrapper.eq(ReturnOrderDO::getOrderNo, dto.getOrderNo());
        queryWrapper.in(ReturnOrderDO::getStatus, 2, 3);
        List<ReturnOrderDO> list = returnOrderMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            applyQty = list.stream().mapToInt(ReturnOrderDO::getApplyQty).sum();
        }
        queryWrapper.clear();
        queryWrapper.eq(ReturnOrderDO::getOrderNo, dto.getOrderNo());
        queryWrapper.in(ReturnOrderDO::getStatus, 5, 6);
        List<ReturnOrderDO> doList = returnOrderMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(doList)) {
            returnQty = doList.stream().mapToInt(ReturnOrderDO::getReturnQty).sum();
        }
        if (dto.getItemNo() < 1) {
            return ResultVo.failure("项号不能小于1");
        }
        // bug11099  增加退货能力校验
        WarehouseDO info = opsWarehouseService.getWarehouseInfoByCode(dto.getWarehouseCode());
        if (info.getReturnableFlag() == null || info.getReturnableFlag() != 1) {
            return ResultVo.failure(info.getWarehouseName() + "没有退货能力");
        }

        queryWrapper.clear();
        queryWrapper.eq(ReturnOrderDO::getApplyNo, dto.getApplyNo());
        queryWrapper.eq(ReturnOrderDO::getItemNo, dto.getItemNo());

        ReturnOrderDO orderDO = returnOrderMapper.selectOne(queryWrapper);
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        if (orderDO != null) {
            if (!orderDO.getOrderNo().equals(dto.getOrderNo())) {
                return ResultVo.failure("该申请号和项号已存在订单:" + orderDO.getOrderNo());
            }
            if (orderDO.getStatus() != 3) {
                return ResultVo.failure("只有待收货的退货单可以修改！");
            }
            if (!userDTO.getUserNo().equals(orderDO.getApplicant()) && !userDTO.getUserNo().equals(orderDO.getCreateUser())) {
                return ResultVo.failure("只有申请人可以修改！");
            }
            if (dto.getApplyQty() > (resultVo.getData().getQuantity() - (returnQty + applyQty) + orderDO.getApplyQty())) {
                return ResultVo.failure("可退数量不足，请修改申请数量");
            }

            ResultVo<String> result = checkQtyWithSmpleBal(dto.getOrderNo(), dto.getApplyQty());
            if (!result.isSuccess()) {
                return ResultVo.failure(result.getMessage());
            }

            orderDO.setApplyQty(dto.getApplyQty());
            orderDO.setRequestWarehouseCode(dto.getWarehouseCode());
            orderDO.setSalesPrice(resultVo.getData().getPrice());
            orderDO.setFeeRate(dto.getFeeRate());
            orderDO.setReturnFee(BigDecimalUtil.mul(dto.getFeeRate(), orderDO.getSalesPrice()));
            orderDO.setToUserStock(dto.getToUserStock());
//            orderDO.setExpStockCode(dto.getExpStockCode());
            orderDO.setCustomerNo(dto.getCustomerNo());
            orderDO.setUserNo(dto.getUserNo());
            orderDO.setInvoiceNo(dto.getInvoiceNo());
            orderDO.setInvoiceDate(dto.getInvoiceDate());
//            orderDO.setDutyType(String.valueOf(dto.getDutyType()));
            orderDO.setApplicant(dto.getApplicant());
            orderDO.setReason(dto.getReason());
            orderDO.setEmployee(dto.getEmployeeNo());
            orderDO.setUpdateUser(userDTO.getUserNo());
//            orderDO.setDutyValue(ReturnDutyTypeEnum.getNameByCode(dto.getDutyType()));
            orderDO.setUpdateTime(new Date());
            int update = returnOrderMapper.updateById(orderDO);
            return update == 1 ? ResultVo.success("修改成功") : ResultVo.failure("修改失败");
        } else {
            if (dto.getApplyQty() > (resultVo.getData().getQuantity() - (returnQty + applyQty))) {
                return ResultVo.failure("可退数量不足，请修改申请数量");
            }

            ResultVo<String> result = checkQtyWithSmpleBal(dto.getOrderNo(), dto.getApplyQty());
            if (!result.isSuccess()) {
                return ResultVo.failure(result.getMessage());
            }

            ReturnOrderDO returnOrderDO = BeanCopyUtil.copy(dto, ReturnOrderDO.class);
            returnOrderDO.setStatus(3);
            returnOrderDO.setReason("退货");
            returnOrderDO.setApplyTimes(1);
            returnOrderDO.setExtStatus(0);
            returnOrderDO.setOrderQty(resultVo.getData().getQuantity());
            returnOrderDO.setSalesPrice(resultVo.getData().getPrice());
            returnOrderDO.setToUserStock(dto.getToUserStock());
            returnOrderDO.setOrderType(resultVo.getData().getOrderType());
            returnOrderDO.setRequestWarehouseCode(dto.getWarehouseCode());
//            returnOrderDO.setDutyType(String.valueOf(dto.getDutyType()));
//            returnOrderDO.setDutyValue(ReturnDutyTypeEnum.getNameByCode(dto.getDutyType()));
            returnOrderDO.setCreateUser(userDTO.getUserNo());
            returnOrderDO.setApplicant(dto.getApplicant());
            returnOrderDO.setApplyTime(new Date());
            returnOrderDO.setEmployee(dto.getEmployeeNo());
            if (resultVo.getData().getOrderType() != null && resultVo.getData().getOrderType() != 3
                    && resultVo.getData().getOrderType() != 21) {
                if (StringUtils.isNotBlank(resultVo.getData().getProdFlag())) {
                    if ("2".equals(resultVo.getData().getProdFlag())) {
                        returnOrderDO.setIsAssOrder(true);
                    } else {
                        returnOrderDO.setIsAssOrder(false);
                    }
                }
            } else {
                returnOrderDO.setIsAssOrder(false);
            }
            returnOrderDO.setReturnFee(BigDecimalUtil.mul(dto.getFeeRate(), returnOrderDO.getSalesPrice()));

            // 该申请号已存在的文件不重复添加
            String fileName = "";
            String realName = "";
            queryWrapper.clear();
            queryWrapper.eq(ReturnOrderDO::getApplyNo, dto.getApplyNo());
            List<ReturnOrderDO> orderList = returnOrderMapper.selectList(queryWrapper);
            if (CollectionUtil.isNotEmpty(orderList)) {
                fileName = orderList.get(0).getFileName();
            }
            if (fileList != null && fileList.length > 0) {
                for (MultipartFile file : fileList) {
                    String[] split = file.getOriginalFilename().split("\\.");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                    String format = formatter.format(new Date());
                    realName = split[0] + "_" + format + "." + split[1];

                    if (StringUtils.isBlank(returnOrderDO.getFileName())) {
                        returnOrderDO.setFileName(file.getOriginalFilename());
                        returnOrderDO.setFileRealName(realName);
                    } else {
                        returnOrderDO.setFileName(returnOrderDO.getFileName() + ";" + file.getOriginalFilename());
                        returnOrderDO.setFileRealName(returnOrderDO.getFileRealName() + ";" + realName);
                    }
                    if (StringUtils.isNotBlank(fileName) && fileName.contains(file.getOriginalFilename())) {
                        returnOrderDO.setFileRealName(orderList.get(0).getFileRealName());
                        continue;
                    }
                    this.uploadReturnFile(file, returnOrderDO.getApplyNo(), realName);
                }
            }

            int insert = returnOrderMapper.insert(returnOrderDO);

            return insert == 1 ? ResultVo.success("添加成功") : ResultVo.failure("添加失败");
        }
    }

    @Override
    public void downloadReturnFile(String fileName, String applyNo) {
        LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ReturnOrderDO::getApplyNo, applyNo);
        List<ReturnOrderDO> list = returnOrderMapper.selectList(queryWrapper);
        String datePath = DateUtil.getYearMonthCode(list.get(0).getCreateTime());

        try {
            // path是指欲下载的文件的路径。
            File file = new File(serverPath + datePath + "/" + applyNo + "/" + fileName);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.resetBuffer();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ResultVo<String> syncReturnInvoiceInfo() {
        LambdaQueryWrapper<ReturnOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ReturnOrderDO::getStatus, 3)
                .ne(ReturnOrderDO::getOrderType, 3)
                .isNull(ReturnOrderDO::getInvoiceNo);

        List<ReturnOrderDO> list = returnOrderMapper.selectList(queryWrapper);

        int count = 0;
        SalesInvoiceRequest request;
        for (ReturnOrderDO orderDO : list) {
            request = new SalesInvoiceRequest();
            request.setCustomerNo(orderDO.getCustomerNo());
            request.setUserNo(orderDO.getUserNo());
            request.setOrderNos(Arrays.asList(orderDO.getOrderNo()));
            ResultVo<List<SalesInvoiceDTO>> resultVo = receiveOrderServiceFeignApi.getInvoiceForReturn(request);

            if (CollectionUtil.isEmpty(resultVo.getData())) {
                continue;
            }
            SalesInvoiceDTO invoiceDTO = resultVo.getData().get(0);
            if (StringUtils.isBlank(invoiceDTO.getInvoiceNo())) {
                continue;
            }

            orderDO.setInvoiceNo(invoiceDTO.getInvoiceNo());
            orderDO.setInvoiceDate(invoiceDTO.getInvoiceDate());
            String msg = orderDO.getReceiveNote() == null ? "" : orderDO.getReceiveNote() + ";";
            orderDO.setReceiveNote(msg + "系统自动更新发票信息,发票号:" + invoiceDTO.getInvoiceNo() + ",开票时间:" + DateUtil.dateToDateTimeString(invoiceDTO.getInvoiceDate()));
            orderDO.setUpdateTime(new Date());
            returnOrderMapper.updateById(orderDO);
            count++;
        }
        return ResultVo.success("成功更新" + count + "条数据");
    }

    @Override
    public void saveTableToRedis(String optUser, String uiViewId,String jsonTableStr) {
        if(StringUtils.isBlank(optUser) || StringUtils.isBlank(uiViewId)) {
            return;
        }
        if (!redisManager.hasKey(com.smc.smccloud.model.constants.Constants.ui_view_id+"all:")) {
            redisManager.set(com.smc.smccloud.model.constants.Constants.ui_view_id+"all:",jsonTableStr);
        }
        redisManager.set(com.smc.smccloud.model.constants.Constants.ui_view_id+optUser+uiViewId,jsonTableStr);
    }

    @Override
    public ResultVo<String> getTableFromRedis(String optUser, String uiViewId) {
        Object o = redisManager.get(com.smc.smccloud.model.constants.Constants.ui_view_id + optUser + uiViewId);
        if(o == null) {
            return ResultVo.failure("暂无设置");
        }
        return ResultVo.success(o.toString());
    }

    @Override
    public ResultVo<String> getAllTableFromRedis(String optUser, String uiViewId) {
        Object o = redisManager.get(com.smc.smccloud.model.constants.Constants.ui_view_id+"all:");
        if(o == null) {
            return ResultVo.failure("暂无设置");
        }
        return ResultVo.success(o.toString());
    }


    public ResultVo<String> returnWTOrderToWMS1(ReturnOrderDO returnOrder) {
        log.info("委托在库退货数据推送:" + returnOrder.getOrderNo());
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        long time1 = System.currentTimeMillis() / 1000;
        UserDto user = new UserDto();
        user.setUserName(userDTO.getUserName());
        user.setIp(IpUtil.getIpAddress());
        WmWTDoConfirmDto dto = new WmWTDoConfirmDto();

        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(returnOrder.getOrderNo());
        CsExpdetailDO expdetailDO = csExpdetailMapper.getCsReturnDoId(orderNoInfo.getOrderNo(), String.valueOf(orderNoInfo.getItemNo()));
        if (expdetailDO == null) {
            throw new RuntimeException("[服务备库退货]未查询到DOID");
        }
        dto.setDeliveryOrderCode(expdetailDO.getDoId());
        dto.setWarehouseCode(returnOrder.getRcvWarehouseCode());
        dto.setInvoice(returnOrder.getExpressNo());
        dto.setSender(returnOrder.getApplicant());
        dto.setUserDto(user);
        WmWTDoItemConfirmDto confirmDto = new WmWTDoItemConfirmDto();
        confirmDto.setBarCode("RC" + time1);
        confirmDto.setQty(Double.valueOf(returnOrder.getReturnQty()));
        confirmDto.setLogisticsCode("SF");
        confirmDto.setExpressCode(returnOrder.getOrderNo());
        long time2 = System.currentTimeMillis() / 1000;
        confirmDto.setPackageCode("RC" + time2);
        dto.setItems(confirmDto);
        CommonResult commonResult = opsWmFeignApi.wmWTDoConfirm(dto);
        if (!commonResult.isSuccess()) {
            throw new RuntimeException(commonResult.getMessage());
        }

        // 推送至物流
        ResultVo<String> resultVo = this.returnOrderToWMS(returnOrder);
        if (!resultVo.isSuccess()) {
            throw new RuntimeException(resultVo.getMessage());
        }

        return ResultVo.success("同步成功");
    }

    /**
     * 将退货单推送至WMS
     *
     * @return
     */
    private ResultVo<String> returnOrderToWMS(ReturnOrderDO returnOrder) {
        LoginUserDTO loginAuthDto = SMCApp.getLoginAuthDto();
        //退货数据写入在途表
        List<CreInvMoveForReturnOrderDto> returnOrderDtos = new ArrayList<>();
        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(returnOrder.getOrderNo());

        // 判断是否为拆分型号
        if (returnOrder.getIsAssOrder()) {
            int splitNo = 1;
            // 获取拆分型号的数量
            ResultVo<RcvDetailVO> rcvDetailWithIsAssOrder = receiveOrderServiceFeignApi.findRcvDetailWithIsAssOrder(returnOrder.getOrderNo());
            if (rcvDetailWithIsAssOrder.getData() == null) {
                return ResultVo.failure(returnOrder.getOrderNo() + "收货失败,未能获取拆分型号的数量.");
            }
            // 去 ops_order_assign_result 获取 拆分的型号.数量
            List<OpsOrderAssignResultDO> orderAssignList = getOrderAssignList(orderNoInfo.getOrderNo(), String.valueOf(orderNoInfo.getItemNo()));
            if (CollectionUtil.isEmpty(orderAssignList)) {
                return ResultVo.failure(returnOrder.getOrderNo() + "收货失败,未能获取拆分型号.");
            }
            RcvDetailVO data = rcvDetailWithIsAssOrder.getData();
            // 更改库存数量
            for (OpsOrderAssignResultDO item : orderAssignList) {
                if (item.getQuantity() != null && data.getQuantity() != null) {
                    // 是否被整除 被整除才可以继续走下去
                    if (!PublicUtil.exactDivisor(item.getQuantity(), data.getQuantity())) {
                        return ResultVo.failure("拆分数量不正确");
                    }
                } else {
                    return ResultVo.failure("拆分数量为空");
                }

                // 计算数量
                int qty = (item.getQuantity() / data.getQuantity()) * returnOrder.getReturnQty();
                //生成在途数据用于物流收货
                CreInvMoveForReturnOrderDto inputDto = new CreInvMoveForReturnOrderDto();
                //前端填写仓库号
                inputDto.setWarehouseCode(returnOrder.getRcvWarehouseCode());
                inputDto.setQty(qty);//良品rcv_fineQty
                inputDto.setModelNo(item.getModelno());
                if (returnOrder.getOrderType() == 3 || returnOrder.getOrderNo().startsWith("RC")) {
                    if (StringUtils.isNotBlank(returnOrder.getUserNo())) {
                        inputDto.setCustomerNo(returnOrder.getUserNo());
                    } else {
                        inputDto.setCustomerNo(returnOrder.getCustomerNo());
                    }
                } else {
                    String endUserFromRcvMaster = getEndUserFromRcvMaster(returnOrder.getOrderNo());
                    inputDto.setCustomerNo(endUserFromRcvMaster);
                }
                inputDto.setApplyNo(returnOrder.getOrderNo().substring(0,7)+PublicUtil.getRandomStrForThreelen());
                inputDto.setItemNo(returnOrder.getItemNo());//项号
                inputDto.setOrderNo(orderNoInfo.getOrderNo());//订单号
                inputDto.setOrderItem(orderNoInfo.getItemNo());//项号
                inputDto.setSplitItemNo(splitNo);//拆分项
                inputDto.setPsnNo(loginAuthDto.getUserNo());
                inputDto.setToUserStock(returnOrder.getToUserStock() == 1 ? true : false);
                log.info("退货确认订单号:" + orderNoInfo.getOrderNo() + ",项号:" + orderNoInfo.getItemNo() + ",拆分项号:" + splitNo);
                returnOrderDtos.add(inputDto);
                splitNo ++;
            }

        } else {
            //生成在途数据用于物流收货
            CreInvMoveForReturnOrderDto inputDto = new CreInvMoveForReturnOrderDto();
            //前端填写仓库号
            inputDto.setWarehouseCode(returnOrder.getRcvWarehouseCode());
            inputDto.setQty(returnOrder.getReturnQty());//良品rcv_fineQty
            inputDto.setModelNo(returnOrder.getModelNo());
            inputDto.setApplyNo(returnOrder.getOrderNo().substring(0,7)+PublicUtil.getRandomStrForThreelen());
            if (returnOrder.getOrderType() == 3 || returnOrder.getOrderNo().startsWith("RC")) {
                if (StringUtils.isNotBlank(returnOrder.getUserNo())) {
                    inputDto.setCustomerNo(returnOrder.getUserNo());
                } else {
                    inputDto.setCustomerNo(returnOrder.getCustomerNo());
                }
            } else {
                String endUserFromRcvMaster = getEndUserFromRcvMaster(returnOrder.getOrderNo());
                inputDto.setCustomerNo(endUserFromRcvMaster);
            }
            inputDto.setItemNo(returnOrder.getItemNo());//项号
            inputDto.setOrderNo(orderNoInfo.getOrderNo());//订单号
            inputDto.setOrderItem(orderNoInfo.getItemNo());//项号
            inputDto.setSplitItemNo(orderNoInfo.getSplitItem());//拆分项
            inputDto.setPsnNo(loginAuthDto.getUserNo());
            inputDto.setToUserStock(returnOrder.getToUserStock() == 1 ? true : false);
            returnOrderDtos.add(inputDto);
        }

        //按单申请生成一个退货单发票号
        if (returnOrderDtos.size() > 0) {
            CommonResult<String> result = opsWmDispatchForOrderFeignApi.handReturnOrderConfirm(returnOrderDtos);
            log.info("[退货管理]生成在途数据用于物流收货 : " + JSONObject.toJSONString(result) + " 请求参数: " + JSONObject.toJSONString(returnOrderDtos));
            if (!result.isSuccess()) {
                throw new RuntimeException("[退货管理]生成在途数据用于物流收货异常:" + result.getMessage());
            }
        }

        return ResultVo.success("[退货管理]生成在途数据成功");
    }

    /**
     *
     * @params List<ReturnOrderDO> list
     * @handle
     * 1.将退货表数据按申请单号[ApplyNo]分组，
     * 2.1.遍历所有申请号[ApplyNo]分组，每个申请号都打包成一组退货数据[List<ReturnOrderDto>]
     *      每条退货表数据[ReturnOrderDO]都查询拆分子项，并获取拆分型号和数量信息，组装成[List<ReturnOrderDto>]
     *          dto数据来源：
     *              from return: 退货申请号，申请项号，客户代码，退货仓库，是否去用户仓库
     *              from splitInfo: 订单号，订单项号，订单拆分项号，拆分数量，拆分型号，
     *  2.2.每组ApplyNo都调用一次wm接口，生成一张发票，发票规则 applyNo+MMddHHmm,规则由wm管理
     *  2.3 如果查询拆分信息过程中有数据异常，则直接中断后续所有分组的执行，并返回错误信息，不回滚
     *  3. 如果有一组调用wm接口返回失败，则直接抛出异常，
     * @api opsWmDispatchForOrderFeignApi.handReturnOrderConfirm(returnOrderDtos);
     * @return
     * ResultVo.success("[退货管理]生成在途数据成功");
     * ResultVo.failure("拆分数量为空");
     * ResultVo.failure("xxxxxx")，提示错误信息，错误判断较严格
     * @throw throw new RuntimeException("[退货管理]生成在途数据用于物流收货异常:" + result.getMessage());
     */
    private ResultVo<String> returnOrderListToWMS(List<ReturnOrderDO> list) {
        LoginUserDTO loginAuthDto = SMCApp.getLoginAuthDto();
        //退货数据写入在途表
        List<CreInvMoveForReturnOrderDto> returnOrderDtos;

        Map<String, String> map = new HashMap<>();
        for (ReturnOrderDO orderDO : list) {
            if (map.containsKey(orderDO.getApplyNo())) {
                continue;
            }
            returnOrderDtos = new ArrayList<>();
            int splitNo;
            // 申请号一样的为一组
            List<ReturnOrderDO> doList = list.stream().filter(item -> item.getApplyNo().equals(orderDO.getApplyNo())).collect(Collectors.toList());
            for (ReturnOrderDO returnOrderDO : doList) {
                OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(returnOrderDO.getOrderNo());
                // 判断是否为拆分型号
                if (returnOrderDO.getIsAssOrder()) {
                    // 获取拆分型号的数量
                    ResultVo<RcvDetailVO> rcvDetailWithIsAssOrder = receiveOrderServiceFeignApi.findRcvDetailWithIsAssOrder(returnOrderDO.getOrderNo());
                    if (rcvDetailWithIsAssOrder.getData() == null) {
                        return ResultVo.failure(returnOrderDO.getOrderNo() + "收货失败,未能获取拆分型号的数量.");
                    }
                    // 获取 拆分的型号.数量
                    List<OpsOrderAssignResultDO> orderAssignList = getOrderAssignList(orderNoInfo.getOrderNo(), String.valueOf(orderNoInfo.getItemNo()));
                    if (CollectionUtil.isEmpty(orderAssignList)) {
                        return ResultVo.failure(returnOrderDO.getOrderNo() + "收货失败,未能获取拆分型号.");
                    }
                    RcvDetailVO data = rcvDetailWithIsAssOrder.getData();
                    // 更改库存数量
                    splitNo = 1;
                    for (OpsOrderAssignResultDO item : orderAssignList) {
                        if (item.getQuantity() != null && data.getQuantity() != null) {
                            // 是否被整除 被整除才可以继续走下去
                            if (!PublicUtil.exactDivisor(item.getQuantity(), data.getQuantity())) {
                                return ResultVo.failure("拆分数量不正确");
                            }
                        } else {
                            return ResultVo.failure("拆分数量为空");
                        }
                        // 计算数量
                        int qty = (item.getQuantity() / data.getQuantity()) * returnOrderDO.getReturnQty();
                        //生成在途数据用于物流收货
                        CreInvMoveForReturnOrderDto inputDto = new CreInvMoveForReturnOrderDto();
                        //前端填写仓库号
                        inputDto.setWarehouseCode(returnOrderDO.getRcvWarehouseCode());
                        inputDto.setQty(qty);//良品rcv_fineQty
                        inputDto.setModelNo(item.getModelno());
                        inputDto.setApplyNo(returnOrderDO.getApplyNo());
                        if (returnOrderDO.getOrderType() == 3 || returnOrderDO.getOrderNo().startsWith("RC")) {
                            if (StringUtils.isNotBlank(returnOrderDO.getUserNo())) {
                                inputDto.setCustomerNo(returnOrderDO.getUserNo());
                            } else {
                                inputDto.setCustomerNo(returnOrderDO.getCustomerNo());
                            }
                        } else {
                            String endUserFromRcvMaster = getEndUserFromRcvMaster(returnOrderDO.getOrderNo());
                            inputDto.setCustomerNo(endUserFromRcvMaster);
                        }
                        inputDto.setItemNo(returnOrderDO.getItemNo());//项号
                        inputDto.setOrderNo(orderNoInfo.getOrderNo());//订单号
                        inputDto.setOrderItem(orderNoInfo.getItemNo());//项号
                        inputDto.setSplitItemNo(splitNo);//拆分项
                        inputDto.setPsnNo(loginAuthDto.getUserNo());
                        inputDto.setToUserStock(returnOrderDO.getToUserStock() == 1 ? true : false);
                        log.info("退货确认订单号:" + orderNoInfo.getOrderNo() + ",项号:" + orderNoInfo.getItemNo() + ",拆分项号:" + splitNo);
                        returnOrderDtos.add(inputDto);
                        splitNo ++;
                    }

                } else {
                    //生成在途数据用于物流收货
                    CreInvMoveForReturnOrderDto inputDto = new CreInvMoveForReturnOrderDto();
                    //前端填写仓库号
                    inputDto.setWarehouseCode(returnOrderDO.getRcvWarehouseCode());
                    inputDto.setQty(returnOrderDO.getReturnQty());//良品rcv_fineQty
                    inputDto.setModelNo(returnOrderDO.getModelNo());
                    inputDto.setApplyNo(returnOrderDO.getApplyNo());
                    if (returnOrderDO.getOrderType() == 3 || returnOrderDO.getOrderNo().startsWith("RC")) {
                        if (StringUtils.isNotBlank(returnOrderDO.getUserNo())) {
                            inputDto.setCustomerNo(returnOrderDO.getUserNo());
                        } else {
                            inputDto.setCustomerNo(returnOrderDO.getCustomerNo());
                        }
                    } else {
                        String endUserFromRcvMaster = getEndUserFromRcvMaster(returnOrderDO.getOrderNo());
                        inputDto.setCustomerNo(endUserFromRcvMaster);
                    }
                    inputDto.setItemNo(returnOrderDO.getItemNo());//项号
                    inputDto.setOrderNo(orderNoInfo.getOrderNo());//订单号
                    inputDto.setOrderItem(orderNoInfo.getItemNo());//项号
                    inputDto.setSplitItemNo(orderNoInfo.getSplitItem());//拆分项
                    inputDto.setPsnNo(loginAuthDto.getUserNo());
                    inputDto.setToUserStock(returnOrderDO.getToUserStock() == 1 ? true : false);
                    returnOrderDtos.add(inputDto);
                }
            }
            //按单申请生成一个退货单发票号
            if (returnOrderDtos.size() > 0) {
                CommonResult<String> result = opsWmDispatchForOrderFeignApi.handReturnOrderConfirm(returnOrderDtos);
                log.info("[退货管理]生成在途数据用于物流收货 : " + JSONObject.toJSONString(result) + " 请求参数: " + JSONObject.toJSONString(returnOrderDtos));
                if (!result.isSuccess()) {
                    throw new RuntimeException("[退货管理]生成在途数据用于物流收货异常:" + result.getMessage());
                }
            }
            map.put(orderDO.getApplyNo(), orderDO.getOrderNo());
        }
        return ResultVo.success("[退货管理]生成在途数据成功");
    }

    public String getEndUserFromRcvMaster(String orderNo) {
        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderNo);
        ResultVo<RcvMasterVO> orderMaster = receiveOrderServiceFeignApi.findOrderMaster(orderNoInfo.getOrderNo());
        if(orderMaster == null || !orderMaster.isSuccess() || orderMaster.getData() == null) {
            return null;
        }
        return orderMaster.getData().getEndUser();
    }

    /**
     * 上传附件
     *
     * @param multipartFile
     * @return
     */
    public Boolean uploadReturnFile(MultipartFile multipartFile, String applyNo, String fileRealName) {
        OutputStream os = null;
        try {
            InputStream stream = multipartFile.getInputStream();
            String datePath = DateUtil.getYearMonthCode(new Date());
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(serverPath + datePath + "/" + applyNo);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileRealName);
            while ((len = stream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            log.error("文件上传时发生异常", e);
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
            } catch (IOException e) {
                log.error("文件上传时发生异常", e);
            }
        }
        return true;
    }

    // 根据申请类型计算结转类型
    public String calCostType(String appType) {
        if (StringUtils.isBlank(appType)) {
            return null;
        }
        String costType = "";
        switch (appType) {
            case "3":
                costType = "102";
                break;
            case "4":
                costType = "103";
                break;
            case "5":
                costType = "201";
                break;
        }
        return costType;

    }

    /**
     * 样品申请退货时根据订单号去校验结转表的数量
     *
     * @param orderNo
     * @return
     */
    public ResultVo<String> checkQtyWithSmpleBal(String orderNo, int applyQty) {
        if (StringUtils.isBlank(orderNo)) {
            return ResultVo.failure("请输入订单号");
        }
        List<String> canPassCheckCode = new ArrayList<>();
        canPassCheckCode.add(SampleBalOptCodeEnum.DJZ.getCode()); // 待结转
        canPassCheckCode.add(SampleBalOptCodeEnum.DZC.getCode()); // 待转出
        canPassCheckCode.add(SampleBalOptCodeEnum.NHJZ.getCode()); // 已内耗结转
        canPassCheckCode.add(SampleBalOptCodeEnum.YZCPD.getCode());
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SamplebalDO::getRorderNo, orderNo)
                .in(SamplebalDO::getOptCode, canPassCheckCode);
        List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(samplebalDOList)) {
            return ResultVo.failure("该订单没结转数据,无法进行退货");
        }

        // 查询上次申请的退货数量
        List<String> noRcvOrderStatus = new ArrayList<>();
        noRcvOrderStatus.add(ReturnOrderStatusEnum.edit.getCode()); // 编辑中
        noRcvOrderStatus.add(ReturnOrderStatusEnum.approval.getCode()); // 审批中
        noRcvOrderStatus.add(ReturnOrderStatusEnum.waitRcv.getCode()); // 待收货
        LambdaQueryWrapper<ReturnOrderDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(ReturnOrderDO::getOrderNo, orderNo)
                .in(ReturnOrderDO::getStatus, noRcvOrderStatus);
        List<ReturnOrderDO> returnOrderDOS = returnOrderMapper.selectList(lambdaQueryWrapper);
        int returnOrderApplyQty = returnOrderDOS.stream().mapToInt(ReturnOrderDO::getApplyQty).sum();

        int canReturnQty = samplebalDOList.stream().mapToInt(SamplebalDO::getQuantity).sum();

        if (applyQty - canReturnQty - returnOrderApplyQty > 0) {
            return ResultVo.failure("请核对退货申请数量,退货申请数量超出样品结转数量,无法申请退货, 目前该单最多可以申请" + (canReturnQty - returnOrderApplyQty) + "个");
        }
        return ResultVo.success("数量校验通过");
    }

}
