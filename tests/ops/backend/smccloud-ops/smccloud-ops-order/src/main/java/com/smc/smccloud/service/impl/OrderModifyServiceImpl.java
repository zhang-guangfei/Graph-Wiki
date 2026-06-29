package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.sales.ops.common.errorEnum.OrderCancelCodeEnum;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.purchase.ModifyPurchaseDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.RcvOrderStatusEnum;
import com.sales.ops.feign.OpsOrderFeignApi;
import com.sales.ops.feign.PurchaseFeignApi;
import com.sales.ops.feign.PurchaseModifyFeignApi;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.OpsOrderResultBean;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.enums.*;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.*;
import com.smc.smccloud.model.OrderModify.OrderModifyDO;
import com.smc.smccloud.model.Purchase.OpsPurchaseOrderDO;
import com.smc.smccloud.model.adapter.order.OrderDeleteReturn;
import com.smc.smccloud.model.enums.CallBackSMSApplyTypeEnum;
import com.smc.smccloud.model.enums.OpsSalesTaskHandStatus;
import com.smc.smccloud.model.enums.OpsSalesTaskReturnStatus;
import com.smc.smccloud.model.inventory.OpsInventoryLogVO;
import com.smc.smccloud.model.order.RcvMasterDO;
import com.smc.smccloud.model.order.*;
import com.smc.smccloud.model.order.orderEdit.UpApproveReplayVO;
import com.smc.smccloud.model.order.orderEdit.UpPurOrderSupplierVO;
import com.smc.smccloud.model.order.orderdel.SalesErpOrderDeleteResultVO;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.ordermodify.*;
import com.smc.smccloud.model.receiveorder.*;
import com.smc.smccloud.model.salestask.OpsSalesCommonParamVO;
import com.smc.smccloud.model.salestask.UpTaskInfoVO;
import com.smc.smccloud.model.shikomi.ShikomiVO;
import com.smc.smccloud.model.stock.DealReturnOpsParam;
import com.smc.smccloud.model.stock.DealReturnOpsParamVO;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
@Slf4j
@Service
public class OrderModifyServiceImpl implements OrderModifyService {

    @Resource
    private RcvdetailMapper rcvdetailMapper;
    @Resource
    private RcvmasterMapper rcvmasterMapper;
    @Resource
    private OrderModifyMapper orderModifyMapper;
    @Autowired
    @Lazy
    private ReceiveOrderService receiveOrderService;
    @Resource
    private OpsOrderFeignApi opsOrderFeignApi;
    @Resource
    private OrderLogFeignApi orderLogFeignApi;
    @Resource
    private SMSOrderServiceFeignApi smsOrderServiceFeignApi;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Resource
    private RedissonUtil redissonUtil;

    @Resource
    private CommonService commonService;

    @Resource
    private HttpServletResponse response;
    @Resource
    private OrderStateService orderStateService;
    @Resource
    private OpsPurchaseOrderMapper opsPurchaseOrderMapper;
    @Resource
    private ProductServiceFeignApi productServiceFeignApi;
    @Resource
    private ProductPriceMapper productPriceMapper;
    @Resource
    private SalesDataModiDataMapper salesDataModiDataMapper;
    @Resource
    private OpsInventoryLogMapper opsInventoryLogMapper;
    @Resource
    private OpsInventoryLogBakMapper opsInventoryLogBakMapper;
    @Resource
    private DailyInventoryMapper dailyInventoryMapper;
    @Resource
    private StockLogMapper stockLogMapper;
    @Resource
    private RedisManager redisManager;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private SalesNotickTaskService salesNotickTaskService;

    @Resource
    private SMSAdapterService smsAdapterService;

    @Resource
    private PurchaseModifyFeignApi purchaseModifyFeignApi;

    @Resource
    private PurchaseFeignApi purchaseFeignApi;

    @Resource
    private OrderStatusMapper orderStatusMapper;

    /**
     * 订单修改页面前端查询orderModify功能
     *
     * @return
     * @updater C12961 2025-05-07
     */
    public ResultVo<PageInfo<OrderModifyVO>> listOrderModifyByPage(OrderModifyRequest request) {
        if (request == null) {
            return null;
        }
        log.info("订单修改 ordermodify 查询条件 : {}", JSONObject.toJSONString(request));

        if (StringUtils.isNotBlank(request.getModelNo())) {
            request.setModelNo(request.getModelNo() + "%");
        }

        if (StringUtils.isNotBlank(request.getRemark())) {
            request.setRemark("%" + request.getRemark() + "%");
        }

        if (request.getIsGt500w() != null) {
            request.setIsGt500wFlag(request.getIsGt500w() ? "1" : "0");
        }

        if (request.getSendProcess() != null) {
            request.setSendProcessFlag(request.getSendProcess() ? "1" : "0");
        }

        if (StringUtils.isBlank(request.getFromDateStr())) {
            request.setFromDateStr(null);
        }
        if (StringUtils.isBlank(request.getToDateStr())) {
            request.setToDateStr(null);
        }
        //仅查询orderModify表的方法 查询条件不带型号和营业所
        if (StringUtils.isBlank(request.getModelNo()) && CollectionUtils.isEmpty(request.getDeptNoList())) {
            //仅查询orderModify表
            List<OrderModifyDO> orderModifyDOS = listOrderModifyDO(request);
            // 获取总数
            long total = ((Page<?>) orderModifyDOS).getTotal();
            //查询其他表，并将数据封装到OrderModifyVO中
            List<OrderModifyVO> orderModifyVOS = getOrderModifyVOS(orderModifyDOS);
            //添加分页参数
            PageInfo<OrderModifyVO> orderModifyVOPageInfo = new PageInfo<>(orderModifyVOS);
            orderModifyVOPageInfo.setTotal(total);
            return ResultVo.success(orderModifyVOPageInfo);
        } else {
            //查询条件带型号或营业所
            if (StringUtils.isBlank(request.getModifyType())) {
                throw new BusinessException("需要填写变更类别");
            }
            List<Long> ids = new ArrayList<>();
            PageHelper.startPage(request.getPageNum(), request.getPageSize());
            //多表查询orderModify表，拿到id
            if (StringUtils.isNotBlank(request.getModifyType())) {
                if ("A".equals(request.getModifyType())) {
                    ids = orderModifyMapper.queryModifyIdsForZD(request, request.getOrderNos());
                } else {
                    ids = orderModifyMapper.queryModifyIdsForNotZD(request, request.getOrderNos());
                }
            }
            // 获取分页总数
            long total = ((Page<?>) ids).getTotal();
            List<OrderModifyVO> orderModifyVOS = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(ids)) {
                //分批查询orderModify表
                if (ids.size() > 1000) {
                    for (int i = 0; i < ids.size(); i += 1000) {
                        List<Long> subIds = ids.subList(i, Math.min(i + 1000, ids.size()));
                        List<OrderModifyDO> subOrderModifyDOS = orderModifyMapper.selectBatchIds(subIds);
                        orderModifyVOS.addAll(getOrderModifyVOS(subOrderModifyDOS));
                    }
                } else {
                    List<OrderModifyDO> orderModifyDOS = orderModifyMapper.selectBatchIds(ids);
                    orderModifyVOS.addAll(getOrderModifyVOS(orderModifyDOS));
                }
            }
            PageInfo<OrderModifyVO> orderModifyVOPageInfo = new PageInfo<>(orderModifyVOS);
            orderModifyVOPageInfo.setTotal(total); // 设置总数
            return ResultVo.success(orderModifyVOPageInfo);
        }
    }

    private List<OrderModifyDO> listOrderModifyDO(OrderModifyRequest request) {
        LambdaQueryWrapper<OrderModifyDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(request.getCancelStrategy())) {
            queryWrapper.eq(OrderModifyDO::getCancelStrategy, request.getCancelStrategy());
        }
        if (StringUtils.isNotBlank(request.getModifyType())) {
            queryWrapper.eq(OrderModifyDO::getModifyType, request.getModifyType());
        }
        if (StringUtils.isNotBlank(request.getStatus())) {
            queryWrapper.eq(OrderModifyDO::getStatus, request.getStatus());
        }
        if (StringUtils.isNotBlank(request.getChangeType())) {
            queryWrapper.eq(OrderModifyDO::getChangeType, request.getChangeType());
        }
        if (StringUtils.isNotBlank(request.getApplyNo())) {
            queryWrapper.eq(OrderModifyDO::getApplyNo, request.getApplyNo());
        }
        if (StringUtils.isNotBlank(request.getRemark())) {
            queryWrapper.like(OrderModifyDO::getAnswerText, request.getRemark());
        }
        if (StringUtils.isNotBlank(request.getIsGt500wFlag()) && "1".equals(request.getIsGt500wFlag())) {
            queryWrapper.like(OrderModifyDO::getSpecial, "\"isGt500w\":true");
        }
        if (StringUtils.isNotBlank(request.getSendProcessFlag()) && "1".equals(request.getSendProcessFlag())) {
            queryWrapper.like(OrderModifyDO::getSpecial, "\"secondApproval\":true");
        }
        if (CollectionUtils.isNotEmpty(request.getOrderNos())) {
            queryWrapper.in(OrderModifyDO::getOrderNo, request.getOrderNos());
        }
        if (StringUtils.isNotBlank(request.getFromDateStr()) && StringUtils.isNotBlank(request.getToDateStr())) {
            if (StringUtils.isNotBlank(request.getDateType())) {
                if ("1".equals(request.getDateType())) {
                    queryWrapper.ge(OrderModifyDO::getApplyTime, request.getFromDateStr());
                    queryWrapper.le(OrderModifyDO::getApplyTime, request.getToDateStr());
                } else {
                    queryWrapper.ge(OrderModifyDO::getAnswerTime, request.getFromDateStr());
                    queryWrapper.le(OrderModifyDO::getAnswerTime, request.getToDateStr());
                }
            }
        }
        //apply_time 排序
        queryWrapper.orderByDesc(OrderModifyDO::getApplyTime);
        if (request.getPageNum() != null && request.getPageSize() != 0) {
            PageHelper.startPage(request.getPageNum(), request.getPageSize());
        }
        List<OrderModifyDO> orderModifyDOS = orderModifyMapper.selectList(queryWrapper);
        return orderModifyDOS;
    }

    private List<OrderModifyVO> getOrderModifyVOS(List<OrderModifyDO> orderModifyDOS) {
        if (CollectionUtils.isEmpty(orderModifyDOS)) {
            return new ArrayList<>();
        }
        List<OrderModifyVO> orderModifyVoS = BeanCopyUtil.copyList(orderModifyDOS, OrderModifyVO.class);
        List<OrderModifyVO> orderModifyVOPageS = new ArrayList<>();
        for (OrderModifyVO vo : orderModifyVoS) {
            vo.setHopeExportDate(null);
            vo.setTransType("");
            vo.setSupplierId("");
            String[] split = vo.getOrderNo().split("-");
            String orderId = split[0].trim();
            Integer orderItem = Integer.parseInt(split[1]);
            Integer splitNo = null;
            if (split.length > 2) {
                splitNo = Integer.parseInt(split[2]);
            }
            LambdaQueryWrapper<RcvDetailDO> queryDetail = new LambdaQueryWrapper<>();
            queryDetail.eq(RcvDetailDO::getRorderNo, orderId);
            queryDetail.eq(RcvDetailDO::getRorderItem, orderItem);
            RcvDetailDO rcvDetailDO = rcvdetailMapper.selectOne(queryDetail);
            if (Objects.nonNull(rcvDetailDO)) {
                LambdaQueryWrapper<RcvMasterDO> queryMaster = new LambdaQueryWrapper<>();
                queryMaster.eq(RcvMasterDO::getRorderNo, orderId);
                RcvMasterDO rcvMasterDO = rcvmasterMapper.selectOne(queryMaster);
                CustomerVO customerVO = opsCommonService.getCustomerByCustomerNo(rcvMasterDO.getEndUser());
                //查询orderStatus表
                vo.setModelNo(rcvDetailDO.getModelNo());
                vo.setQuantity(rcvDetailDO.getQuantity());
                vo.setProdFlag(rcvDetailDO.getProdFlag());
                vo.setEndUser(rcvMasterDO.getEndUser());
                if (Objects.nonNull(customerVO)) {
                    if (StringUtils.isNotBlank(customerVO.getHlCode())) {
                        vo.setCustomerDeptNo(customerVO.getHlCode());
                    } else {
                        vo.setCustomerDeptNo(customerVO.getHRUnitId());
                    }
                }
                OrderStatusDO orderStatusDO = null;
                boolean isZD = StringUtils.isNotBlank(vo.getModifyType()) && "A".equals(vo.getModifyType());
                if (isZD) {
                    LambdaQueryWrapper<OrderStatusDO> orderStatusWrapper = new LambdaQueryWrapper<>();
                    orderStatusWrapper.eq(OrderStatusDO::getOrderId, orderId)
                            .eq(OrderStatusDO::getOrderItem, orderItem);
                    if (splitNo != null) {
                        if (OrderSplitTypeEnum.modelNoSplit.getCode().equals(rcvDetailDO.getProdFlag())
                                || !OrderSpecExpType.include(rcvDetailDO.getExpDlvType(), OrderSpecExpType.AssChildToExport)) {
                            orderStatusWrapper.eq(OrderStatusDO::getSplitNo, 1);
                            orderStatusWrapper.eq(OrderStatusDO::getPcoItem, splitNo);
                        } else {
                            orderStatusWrapper.eq(OrderStatusDO::getSplitNo, splitNo);
                            orderStatusWrapper.eq(OrderStatusDO::getSplitNo, 0);
                        }
                    }
                    List<OrderStatusDO> orderStatusDOList = orderStatusMapper.selectList(orderStatusWrapper);
                    if (CollectionUtils.isNotEmpty(orderStatusDOList)) {
                        orderStatusDO = orderStatusDOList.get(0);
                    }
                }
                if (orderStatusDO != null) {
                    vo.setModelNo(orderStatusDO.getModelno());
                    vo.setQuantity(orderStatusDO.getQty());
                }
                orderModifyVOPageS.add(vo);
            }
        }
        List<OrderModifyVO> orderModifyVOS = commonHandData(orderModifyVOPageS);
        return orderModifyVOS;
    }

    public List<OrderModifyVO> commonHandData(List<OrderModifyVO> list) {

        for (OrderModifyVO item : list) {
            if (StringUtils.isNotBlank(item.getOrderNo())) {
                OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullPONo(item.getOrderNo());
                item.setRorderNo(orderNoInfo.getOrderNo());
                item.setRorderItem(orderNoInfo.getItemNo());
            }
            if (StringUtils.isNotBlank(item.getModifyType())) {
                item.setModifyTypeName(OrderModifyTypeEnum.getNameByCode(item.getModifyType()));
            }
            if (StringUtils.isNotBlank(item.getChangeType())) {
                item.setChangeTypeName(OrderModifyTypeEnum.getNameByCode(item.getChangeType()));
            }
            if (!Objects.isNull(item.getStatus())) {
                item.setStatusName(OrderModifyEnum.getCodeNameByCode(String.valueOf(item.getStatus())));
            }
            if (StringUtils.isNotBlank(item.getDeptNo())) {
                item.setDeptNo("[" + item.getDeptNo() + "]" + opsCommonService.getDeptNameByNo(item.getDeptNo()));
            }
            if (StringUtils.isNotBlank(item.getCustomerDeptNo())) {
                item.setCustomerDeptNo("[" + item.getCustomerDeptNo() + "]" + opsCommonService.getDeptNameByNo(item.getCustomerDeptNo()));
            }

            // 出库区分
            if ("CG".equals(item.getStockType())) {
                item.setStockCode("[" + item.getStockCode() + "]" + opsCommonService.getSupplierNameByCode(item.getStockCode()));
            } else {
                item.setStockCode("[" + item.getStockCode() + "]" + opsCommonService.getWarehouseNameByCode(item.getStockCode()));
            }

            if (StringUtils.isNotBlank(item.getOrderStatus())) {
                if (StringUtils.isNotBlank(item.getModifyType())) {
                    if (item.getModifyType().equals(OrderModifyTypeEnum.bgysfs.getCode()) ||
                            item.getModifyType().equals(OrderModifyTypeEnum.bgccz.getCode())) {
                        item.setOrderStatus(PurchaseOrderStatusEnum2.getNameByCode(item.getOrderStatus()));
                    } else if (item.getModifyType().startsWith(OrderModifyTypeEnum.ddzd.getCode())) {
                        item.setOrderStatus(RCVOrderStatusEnum.getName(Integer.parseInt(item.getOrderStatus())));
                    } else {
                        item.setOrderStatus(RCVOrderStatusEnum.getName(Integer.parseInt(item.getOrderStatus())));
                    }
                }
            }

            if (StringUtils.isNotBlank(item.getProdFlag())) {
                item.setProdFlag(OrderSplitTypeEnum.getCodeName(item.getProdFlag()));
            }

            if (StringUtils.isNotBlank(item.getEndUser())) {
                item.setEndUser("[" + item.getEndUser() + "]" + opsCommonService.getCustomerNameByNo(item.getEndUser()));
            }

            if (StringUtils.isNotBlank(item.getApplyPersonNo())) {
                item.setApplyPersonNo("[" + item.getApplyPersonNo() + "]" + opsCommonService.getEmpSaleNameByNo(item.getApplyPersonNo()));
            }

            if (StringUtils.isNotBlank(item.getAnswerPns())) {
                item.setAnswerPns("[" + item.getAnswerPns() + "]" + opsCommonService.getEmpSaleNameByNo(item.getAnswerPns()));
            }

            if (OrderModifyTypeEnum.bgysfs.getCode().equals(item.getModifyType())) {
                item.setChangeMessage(OPSTransTypeEnum.getNameByCode(item.getChangeMessage()));
            }

            if (StringUtils.isNotBlank(item.getSpecial())) {
                SpecialVO specialVO = JSONObject.parseObject(item.getSpecial(), SpecialVO.class);
                item.setIsGt500w(specialVO.getIsGt500w() == null ? false : specialVO.getIsGt500w());
                item.setIsGt500wName(item.getIsGt500w() ? "是" : "否");

                item.setSecondProcess(specialVO.getSecondApproval() == null ? false : specialVO.getSecondApproval());
                item.setSecondProcessName(item.getSecondProcess() ? "是" : "否");

                item.setTransfer(specialVO.getTransferSpecial() == null ? false : specialVO.getTransferSpecial());
                item.setTransferName(item.getTransfer() ? "是" : "否");

                item.setEndUserForTransferSpecial(specialVO.getEndUserForTransferSpecial());
            } else {
                item.setIsGt500wName("否");
                item.setSecondProcessName("否");
                item.setTransferName(item.getTransfer() ? "是" : "否");
            }
            item.setSource(StringUtils.isBlank(item.getApplyNo()) ? "OPS" : "销售门户");
            if (item.getBinflag() != null) {
                item.setBinflag("1".equals(item.getBinflag()) ? "是" : "否");
            } else {
                item.setBinflag("空");
            }
            //采购状态
            if (StringUtils.isNotBlank(item.getPoStateCode())) {
                item.setPoStateCode(PurchaseOrderStatusEnum2.getNameByCode(item.getPoStateCode()));
            }
            if (item.getOrderDate() != null) {
                item.setOrderDateStr(cn.hutool.core.date.DateUtil.formatDateTime(item.getOrderDate()));
            }
        }
        return list;
    }

    public OrderModifyVO conventOrderModifyWithPur(OrderModifyVO orderModifyVO) {
        if (StringUtils.isNotBlank(orderModifyVO.getModifyType()) &&
                (orderModifyVO.getModifyType().equals(OrderModifyTypeEnum.bgysfs.getCode()) || orderModifyVO.getModifyType().equals(OrderModifyTypeEnum.bgccz.getCode()))) {

            CommonResult<List<ModifyPurchaseDto>> purchaseT = purchaseModifyFeignApi.getPurchaseT(Collections.singletonList(orderModifyVO.getOrderNo()));
            if (purchaseT.isSuccess() && purchaseT.getData() != null) {
                for (ModifyPurchaseDto item : purchaseT.getData()) {
                    orderModifyVO.setQuantity(item.getQty());
                    orderModifyVO.setModelNo(item.getModelNo());
                    String endUser = "";
                    if (StringUtils.isNotBlank(item.getCustomerNo())) {
                        endUser = item.getCustomerNo();
                    }
                    if (StringUtils.isNotBlank(item.getUserNo())) {
                        endUser = item.getUserNo();
                    }
                    CustomerVO customerByCustomerNo = opsCommonService.getCustomerByCustomerNo(endUser);
                    String deptNo = "";
                    if (StringUtils.isNotBlank(customerByCustomerNo.getHRUnitId())) {
                        deptNo = customerByCustomerNo.getHRUnitId();
                    }
                    if (StringUtils.isNotBlank(customerByCustomerNo.getHlCode())) {
                        deptNo = customerByCustomerNo.getHlCode();
                    }
                    String deptNameByNo = opsCommonService.getDeptNameByNo(deptNo);
                    orderModifyVO.setCustomerDeptNo("[" + deptNo + "]" + deptNameByNo);
                    orderModifyVO.setEndUser("[" + endUser + "]" + customerByCustomerNo.getName());
                    break;
                }
            }
        }
        return orderModifyVO;
    }


    @Override
    public void exportOrderModifyData(OrderModifyRequest request) {
        //1.设置查询条件
        if (request == null) {
            return;
        }
        log.info("订单修改导出 ordermodify 查询条件 : {}", JSONObject.toJSONString(request));
        if (StringUtils.isNotBlank(request.getModelNo())) {
            request.setModelNo(request.getModelNo() + "%");
        }
        if (StringUtils.isNotBlank(request.getRemark())) {
            request.setRemark("%" + request.getRemark() + "%");
        }
        if (request.getIsGt500w() != null) {
            request.setIsGt500wFlag(request.getIsGt500w() ? "1" : "0");
        }
        if (request.getSendProcess() != null) {
            request.setSendProcessFlag(request.getSendProcess() ? "1" : "0");
        }
        if (StringUtils.isBlank(request.getFromDateStr())) {
            request.setFromDateStr(null);
        }
        if (StringUtils.isBlank(request.getToDateStr())) {
            request.setToDateStr(null);
        }
        if (request.getPageNum() != null || request.getPageSize() != null) {
            request.setPageNum(null);
            request.setPageSize(null);
        }

        try {
            //2.查询数据
            List<OrderModifyDO> orderModifyDOS = listOrderModifyDO(request);
            //查询外联表，补充数据其他字段
            List<OrderModifyVO> orderModifyVOS = getOrderModifyVOSForExport(orderModifyDOS, request);
            // 将数据进行字典翻译
            // 把字典翻译这一步放到筛选后再做，不然用部门代码筛选会失败
            orderModifyVOS = commonHandData(orderModifyVOS);
            List<OrderModifyVO> notSplitList = orderModifyVOS.stream().filter(item -> OrderSplitTypeEnum.noSplit.getCodeName().equals(item.getProdFlag())).collect(Collectors.toList());
            List<OrderModifyVO> splitList = orderModifyVOS.stream().filter(item -> !OrderSplitTypeEnum.noSplit.getCodeName().equals(item.getProdFlag())).collect(Collectors.toList());
            //3.读取模板
            String path = "templates/exportOrderModify.xlsx";
            ExcelUtil excel = new ExcelUtil(path);
            // 向模板中写入不拆分数据
            writeNotSplitOrderToExcel(excel, notSplitList);
            // 向模板中写入拆分数据
            writeSplitOrderToExcel(excel, splitList);
            //导出文件
            excel.writeToResponse(response, "exportOrderModifyData.xlsx");
        } catch (Exception e) {
            log.error("订单修改导出异常", e);
        }
    }

    /**
     * 导出和查询的字段不一样，文档：bug17423
     *
     * @param orderModifyDOS
     * @return
     */
    private List<OrderModifyVO> getOrderModifyVOSForExport(List<OrderModifyDO> orderModifyDOS, OrderModifyRequest request) {
        if (CollectionUtils.isEmpty(orderModifyDOS)) {
            return new ArrayList<>();
        }
        List<OrderModifyVO> orderModifyVoS = BeanCopyUtil.copyList(orderModifyDOS, OrderModifyVO.class);
        List<OrderModifyVO> orderModifyVOPageS = new ArrayList<>();
        for (OrderModifyVO vo : orderModifyVoS) {
            vo.setHopeExportDate(null);
            vo.setTransType("");
            vo.setSupplierId("");
            String[] split = vo.getOrderNo().split("-");
            RcvDetailDO rcvDetailDO;
            if (split.length >= 2) {
                String orderId = split[0].trim();
                Integer orderItem = Integer.parseInt(split[1]);
                LambdaQueryWrapper<RcvDetailDO> queryDetail = new LambdaQueryWrapper<>();
                queryDetail.eq(RcvDetailDO::getRorderNo, orderId);
                queryDetail.eq(RcvDetailDO::getRorderItem, orderItem);
                rcvDetailDO = rcvdetailMapper.selectOne(queryDetail);
            } else {
                LambdaQueryWrapper<RcvDetailDO> queryDetail = new LambdaQueryWrapper<>();
                queryDetail.eq(RcvDetailDO::getRorderFno, vo.getOrderNo());
                rcvDetailDO = rcvdetailMapper.selectOne(queryDetail);
            }
            if (Objects.nonNull(rcvDetailDO)) {
                LambdaQueryWrapper<RcvMasterDO> queryMaster = new LambdaQueryWrapper<>();
                queryMaster.eq(RcvMasterDO::getRorderNo, rcvDetailDO.getRorderNo());
                RcvMasterDO rcvMasterDO = rcvmasterMapper.selectOne(queryMaster);
                CustomerVO customerVO = opsCommonService.getCustomerByCustomerNo(rcvMasterDO.getEndUser());
                vo.setModelNo(rcvDetailDO.getModelNo());
                vo.setQuantity(rcvDetailDO.getQuantity());
                vo.setProdFlag(rcvDetailDO.getProdFlag());
                //订货日期
                vo.setOrderDate(rcvDetailDO.getAllotTime());
                vo.setEndUser(rcvMasterDO.getEndUser());
                if (Objects.nonNull(customerVO)) {
                    //HL所
                    if (StringUtils.isNotBlank(customerVO.getHlCode())) {
                        vo.setCustomerDeptNo(customerVO.getHlCode());
                    } else {
                        vo.setCustomerDeptNo(customerVO.getHRUnitId());
                    }
                    //营业所
                    if (StringUtils.isNotBlank(customerVO.getHRUnitId())) {
                        vo.setCustomerDeptNo2(customerVO.getHRUnitId());
                    }
                }
                if (StringUtils.isNotBlank(vo.getCustomerDeptNo())) {
                    //查询营业部
                    HROrganizationDO secondDept = opsCommonService.findSalesDepartment(vo.getCustomerDeptNo());
                    if (secondDept != null) {
                        vo.setSecondDeptNo(secondDept.getName());
                    }
                }
                //筛选条件：部门代码
                if (CollectionUtils.isNotEmpty(request.getDeptNoList())) {
                    if (!request.getDeptNoList().contains(vo.getCustomerDeptNo2()) || StringUtils.isBlank(vo.getCustomerDeptNo2())) {
                        continue;
                    }
                }
                List<ResultPurchaseDTO> resultList = rcvdetailMapper.getAssignResultAndPoInfoByOrderNo(rcvDetailDO.getRorderNo(), rcvDetailDO.getRorderItem());
                if (resultList.isEmpty()) {
                    //查询已删除的result
                    List<OpsOrderAssignResultDO> resultDOList = findDeletedByOrder(rcvDetailDO);
                    //联查purchase表
                    for (OpsOrderAssignResultDO resultDO : resultDOList) {
                        List<ResultPurchaseDTO> resultItemList = rcvdetailMapper.getAssignResultAndPoInfoById(resultDO.getId());
                        //收集联查后的表
                        resultList.addAll(resultItemList);
                    }
                }

                for (ResultPurchaseDTO resultDTO : resultList) {
                    OrderModifyVO vo2 = new OrderModifyVO();
                    BeanCopyUtil.copy(vo, vo2);
                    vo2.setModelNo(resultDTO.getModelno());
                    vo2.setQuantity(resultDTO.getQuantity());
                    vo2.setStockType(resultDTO.getStockType());
                    vo2.setStockCode(resultDTO.getStockCode());
                    String poFno = null;
                    if (StringUtils.isNotBlank(resultDTO.getOrderNo())) {
                        poFno = resultDTO.getOrderNo();
                        if (resultDTO.getItemNo() != null) {
                            poFno += "-" + resultDTO.getItemNo();
                            if (resultDTO.getSplitItemNo() != null) {
                                poFno += "-" + resultDTO.getSplitItemNo();
                            }
                        }
                    }
                    vo2.setAssociateNo(poFno);
                    vo2.setDeliveryTime(resultDTO.getDeliveryTime());
                    vo2.setBinflag(resultDTO.getBinflag());
                    vo2.setSupplierOrderNo(resultDTO.getSupplierOrderNo());
                    vo2.setPoStateCode(resultDTO.getPoStateCode());
                    //筛选条件：型号
                    if (StringUtils.isNotBlank(request.getModelNo()) && !resultDTO.getModelno().contains(request.getModelNo())) {
                        continue;
                    }
                    orderModifyVOPageS.add(vo2);
                }
            }
        }
        return orderModifyVOPageS;
    }

    public List<OpsOrderAssignResultDO> findDeletedByOrder(RcvDetailDO rcvDetailDO) {
        List<OpsOrderAssignResultDO> list = rcvdetailMapper.getOrderAssignResultIncludeDeleted(rcvDetailDO.getRorderNo(), rcvDetailDO.getRorderItem());
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        Date maxUpdateTime = list.get(0).getUpdateTime();
        List<OpsOrderAssignResultDO> resultList = list.stream()
                .filter(item -> maxUpdateTime.equals(item.getUpdateTime())).collect(Collectors.toList());
        return resultList;
    }

    //写入不拆分数据到Excel
    private void writeNotSplitOrderToExcel(ExcelUtil excel, List<OrderModifyVO> notSplitList) {
        excel.openSheet(0);
        excel.updsheetName(0, "不拆分订单");
        Workbook wb = excel.getWorkBook();
        Font font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setBold(true);
        font.setColor((short) 10);
        font.setFontHeightInPoints((short) 11);
        int cel;
        int row = 1;
        for (OrderModifyVO item : notSplitList) {
            cel = 0;
            if (StringUtils.isNotBlank(item.getSpecial())) {
                SpecialVO specialVO = JSONObject.parseObject(item.getSpecial(), SpecialVO.class);
                item.setSecondProcess(specialVO.getSecondApproval() == null ? false : specialVO.getSecondApproval());
                item.setSecondProcessName(item.getSecondProcess() ? "是" : "否");
            } else {
                item.setSecondProcessName("否");
            }
            excel.setCellValue(row, cel++, item.getModifyTypeName()); // 变更类型
            excel.setCellValue(row, cel++, item.getStatusName()); // 处理状态
            excel.setCellValue(row, cel++, item.getSecondProcessName()); // 二次审批
            excel.setCellValue(row, cel++, item.getOrderNo()); // 业务单号
            excel.setCellValue(row, cel++, item.getModelNo()); // 型号
            excel.setCellValue(row, cel++, item.getQuantity()); // 数量
            excel.setCellValue(row, cel++, item.getSecondDeptNo()); // 二级部
            excel.setCellValue(row, cel++, item.getCustomerDeptNo()); // 客户所属营业所
            excel.setCellValue(row, cel++, item.getChangeMessage()); // 申请内容
            excel.setCellValue(row, cel++, item.getChangeTypeName()); // 变更内容
            excel.setCellValue(row, cel++, item.getResponsibleParty()); // 责任方
            excel.setCellValue(row, cel++, item.getCancelStrategy());// 删单对策
            excel.setCellValue(row, cel++, item.getCancelReason()); // 删单原因
            excel.setCellValue(row, cel++, item.getReason()); // 变更理由
            excel.setCellValue(row, cel++, item.getSupplierOrderNo()); // 日本手配号
            excel.setCellValue(row, cel++, item.getOrderDateStr()); // 订货日期
            excel.setCellValue(row, cel++, item.getDeliveryTime()); // 返信日期
            excel.setCellValue(row, cel++, item.getBinflag()); // 供应商BIN
            excel.setCellValue(row, cel++, ""); // 催促次数
            excel.setCellValue(row, cel++, item.getStockCode()); // 出库区分
            excel.setCellValue(row, cel++, item.getPoStateCode()); // 订单状态
            excel.setCellValue(row, cel++, item.getProdFlag()); // 拆分标识
            excel.setCellValue(row, cel++, item.getApplyNo()); // 申请号
            excel.setCellValue(row, cel++, item.getEndUser()); // 用户名/代码
            excel.setCellValue(row, cel++, item.getApplyPersonNo()); // 申请人
            excel.setCellValue(row, cel++, item.getApplyTime()); // 申请时间
            excel.setCellValue(row, cel++, item.getAnswerPns()); // 业务处理人
            excel.setCellValue(row, cel++, item.getAnswerText()); // 业务审批回复内容
            excel.setCellValue(row, cel, item.getAnswerTime()); // 业务处理时间
            row++;
        }
    }


    private void writeSplitOrderToExcel(ExcelUtil excel, List<OrderModifyVO> notSplitList) {
        excel.openSheet(1);
        excel.updsheetName(1, "拆分订单");
        Workbook wb = excel.getWorkBook();
        Font font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setBold(true);
        font.setColor((short) 10);
        font.setFontHeightInPoints((short) 11);
        int cel;
        int row = 1;
        for (OrderModifyVO item : notSplitList) {
            cel = 0;
            if (StringUtils.isNotBlank(item.getSpecial())) {
                SpecialVO specialVO = JSONObject.parseObject(item.getSpecial(), SpecialVO.class);
                item.setSecondProcess(specialVO.getSecondApproval() == null ? false : specialVO.getSecondApproval());
                item.setSecondProcessName(item.getSecondProcess() ? "是" : "否");
            } else {
                item.setSecondProcessName("否");
            }
            excel.setCellValue(row, cel++, item.getModifyTypeName()); // 变更类型
            excel.setCellValue(row, cel++, item.getStatusName()); // 处理状态
            excel.setCellValue(row, cel++, item.getSecondProcessName()); // 二次审批
            excel.setCellValue(row, cel++, item.getOrderNo()); // 业务单号
            excel.setCellValue(row, cel++, item.getAssociateNo()); // 关联单号
            excel.setCellValue(row, cel++, item.getModelNo()); // 拆分型号
            excel.setCellValue(row, cel++, item.getQuantity()); // 拆分数量
            excel.setCellValue(row, cel++, item.getSecondDeptNo()); // 二级部
            excel.setCellValue(row, cel++, item.getCustomerDeptNo()); // 客户所属营业所
            excel.setCellValue(row, cel++, item.getChangeMessage()); // 申请内容
            excel.setCellValue(row, cel++, item.getChangeTypeName()); // 变更内容
            excel.setCellValue(row, cel++, item.getResponsibleParty()); // 责任方
            excel.setCellValue(row, cel++, item.getCancelStrategy());// 删单对策
            excel.setCellValue(row, cel++, item.getCancelReason()); // 删单原因
            excel.setCellValue(row, cel++, item.getReason()); // 变更理由
            excel.setCellValue(row, cel++, item.getSupplierOrderNo()); // 日本手配号
            excel.setCellValue(row, cel++, item.getOrderDateStr()); // 订货日期
            excel.setCellValue(row, cel++, item.getDeliveryTime()); // 返信日期
            excel.setCellValue(row, cel++, item.getBinflag()); // 供应商BIN
            excel.setCellValue(row, cel++, ""); // 催促次数
            excel.setCellValue(row, cel++, item.getStockCode()); // 出库区分
            excel.setCellValue(row, cel++, item.getPoStateCode()); // 订单状态
            excel.setCellValue(row, cel++, item.getProdFlag()); // 拆分标识
            excel.setCellValue(row, cel++, item.getApplyNo()); // 申请号
            excel.setCellValue(row, cel++, item.getEndUser()); // 用户名/代码
            excel.setCellValue(row, cel++, item.getApplyPersonNo()); // 申请人
            excel.setCellValue(row, cel++, item.getApplyTime()); // 申请时间
            excel.setCellValue(row, cel++, item.getAnswerPns()); // 业务处理人
            excel.setCellValue(row, cel++, item.getAnswerText()); // 业务审批回复内容
            excel.setCellValue(row, cel, item.getAnswerTime()); // 业务处理时间
            row++;
        }
    }


    private Map<String, String> getStatusMap() {
        Map<String, String> map = new HashMap<>();
        ResultVo<List<DataCodeVO>> list = dictDataServiceFeignApi.getDataCodes("1001");
        for (DataCodeVO vo : list.getData()) {
            map.put(vo.getCode(), vo.getCodeName());
        }
        return map;
    }

    private Map<String, String> getApproveStatusDesc() {
        Map<String, String> map = new HashMap<>();
        ResultVo<List<DataCodeVO>> list = dictDataServiceFeignApi.getDataCodes("1012");
        for (DataCodeVO vo : list.getData()) {
            map.put(vo.getCode(), vo.getCodeName());
        }
        return map;
    }

    private Map<String, String> getOrderModifyType() {
        Map<String, String> map = new HashMap<>();
        ResultVo<List<DataCodeVO>> list = dictDataServiceFeignApi.getDataCodes("1008");
        for (DataCodeVO vo : list.getData()) {
            map.put(vo.getCode(), vo.getCodeName());
        }
        return map;
    }

    /**
     * 订单修改审批
     */
    @Override
    @DS("sharedb")
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> approveOrderModify(ApproveOrderModifyDTO dto) {
        if (dto.getApproveItems() == null || dto.getApproveItems().isEmpty()) {
            return ResultVo.failure("没有需要审批的数据！");
        }
        LoginUserDTO userDTO = null;
        try {
            userDTO = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            userDTO = new LoginUserDTO();
            userDTO.setUserNo("ops");
        }
        int count = 0;
        List<OrderCancelResult> cancelResultList = new ArrayList<>(dto.getApproveItems().size());
        OrderCancelResult cancelResult;
        StringBuilder msg = new StringBuilder();
        OrderModifyDO info;
        OrderModifyDO updateDO;

        for (OrderModifyVO vo : dto.getApproveItems()) {
            //查找有无修改单数据
            info = orderModifyMapper.selectById(vo.getId());
            if (info == null) {
                msg.append(vo.getOrderNo()).append("该订单不存在");
                continue;
            }
            if (!(info.getStatus() == 2 || info.getStatus() == 5)) {
                msg.append(info.getOrderNo()).append("该状态不能处理;");
                continue;
            }
            String modifyTypeDesc = "";
            // 执行修改
            if (dto.getStatus() == 6) {
                boolean update = false;
                switch (vo.getModifyType()) {
                    case "A":
                        modifyTypeDesc = "订单变更地址:";
                        update = Approve_UpdateRcvDetail(vo);
                        break;
                    case "M":
                        modifyTypeDesc = "订单变更客户型号:";
                        update = Approve_UpdateRcvDetail(vo);
                        break;
                    case "P":
                        modifyTypeDesc = "订单变更单价:";
                        update = Approve_UpdateRcvDetail(vo);
                        break;
                    case "O":
                        modifyTypeDesc = "订单变更客户订单号:";
                        update = Approve_UpdateRcvDetail(vo);
                        break;
                    case "T":
                        modifyTypeDesc = "订单变更运输方式:";
                        update = Approve_UpdateRcvMaster(vo);
                        break;
                    case "U":
                        modifyTypeDesc = "订单变更用户:";
                        update = Approve_UpdateRcvMaster(vo);
                        break;
                    case "C":
                        modifyTypeDesc = "订单取消:";
                        ResultVo<String> resultVo = Approve_CancelModifyOrder(vo, dto.getApproveReason(), userDTO.getUserNo());
                        update = resultVo.isSuccess();
                        // 订单取消返回门户消息
                        cancelResult = new OrderCancelResult();
                        cancelResult.setNo(info.getApplyNo());
                        cancelResult.setOrderNo(info.getOrderNo());
                        cancelResult.setResult(update ? "2" : "3");
                        cancelResult.setMessage(resultVo.getMessage());
                        cancelResult.setHandlePsnNo(userDTO.getUserNo());
                        cancelResult.setHandlePsnName(userDTO.getRealName());
                        cancelResult.setHandleRemark(dto.getApproveReason());
                        cancelResultList.add(cancelResult);
                        break;
                    default:
                        break;
                }
                if (update) {
                    updateDO = new OrderModifyDO();
                    updateDO.setId(info.getId());
                    updateDO.setAnswerText(dto.getApproveReason());
                    updateDO.setAnswerTime(DateUtil.getNow());
                    updateDO.setStatus(dto.getStatus());
                    orderModifyMapper.updateById(updateDO);
                    // 记录到日志表
                    sendorderLog(vo.getOrderNo(), modifyTypeDesc + dto.getApproveReason());
                    count++;
                }
            } else {
                msg.append(info.getOrderNo()).append("变更失败，或者接单表不存在该订单；");
            }
        }

        if (CollectionUtils.isNotEmpty(cancelResultList)) {
            ResultVo<Boolean> sendMsgResult = smsOrderServiceFeignApi.sendOrderCancelReturnMessage(cancelResultList);
            if (!sendMsgResult.isSuccess()) {
                throw new BusinessException("订单取消处理消息回调门户失败");
            }
        }

        if (count > 0) {
            return ResultVo.success("共处理" + count + "条数据成功！" + msg);
        } else {
            return ResultVo.success("处理失败！" + msg);
        }
    }

    @Override
    @DS("sharedb")
    // @Transactional(rollbackFor = Exception.class)
    public ResultVo<List<OrderDeleteReturn>> applyOrderCancel(List<OrderModifyVO> list) {

        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.failure("申请数据为空");
        }

        log.info("删单队列数据打印: {}", JSONUtil.toJsonStr(list));

        List<OrderDeleteReturn> resultList = new ArrayList<>(list.size());
        // 返回结果封装类
        OrderDeleteReturn result;
        RcvDetailVO rcvDetailVO;
        OrderModifyDO modifyDO;

        for (OrderModifyVO info : list) {
            boolean toCancel = true;
            result = new OrderDeleteReturn();
            result.setNo(info.getApplyNo());
            result.setOrderNo(info.getOrderNo());
            resultList.add(result);

            if (StringUtils.isBlank(info.getOrderNo())) {
                result.setResult(CancelOrderToSalesStatus.apply_fail.getCode());
                result.setMessage("订单号不能为空");
                continue;
            }
            ResultVo<RcvDetailVO> orderDetail = receiveOrderService.findOrderDetail(info.getOrderNo());
            if (!orderDetail.isSuccess() || orderDetail.getData() == null) {
                result.setResult(CancelOrderToSalesStatus.del_success.getCode());
                result.setMessage("订单号不存在");
                continue;
            }
            rcvDetailVO = orderDetail.getData();
            // 不可删除
            Boolean aBoolean = canDelete(rcvDetailVO.getStatus(), rcvDetailVO.getExpQty());
            if (!aBoolean) {
                result.setResult(CancelOrderToSalesStatus.del_fail.getCode());
                result.setMessage("订单状态为: " + RCVOrderStatusEnum.getName(rcvDetailVO.getStatus()) + ",不可删除");
                toCancel = false;
                info.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode()));
                info.setRemark(OrderCancelCodeEnum.STATUS_NOT_DEL.getDesc());
            }
            // 订单状态-已删除
            if (rcvDetailVO.getStatus() == RcvOrderStatusEnum.CANCEL.getType()) {
                result.setResult(CancelOrderToSalesStatus.apply_fail.getCode());
                result.setMessage("已删除");
                toCancel = false;
                info.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode()));
                info.setRemark(OrderCancelCodeEnum.ALREADY_DEL.getDesc());
            }
            // 特价号
            // info.setSpAnswerno(rcvDetailVO.getSpecOfferNo());
            // 手配号
            String stateSupplierOrderNo = orderStateService.getStateSupplierOrderNo(info.getOrderNo());
            if (StringUtils.isNotBlank(stateSupplierOrderNo)) {
                info.setSupplierOrderNo(stateSupplierOrderNo);
            }
            // update by LiYingChao from bug 9489 in 2023-02-03
            // 从采购表取出库区分写入orderModify
//            OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(info.getOrderNo());
//            LambdaQueryWrapper<OpsPurchaseOrderDO> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper
//                    .eq(OpsPurchaseOrderDO::getOrderNo,orderNoInfo.getOrderNo())
//                    .eq(OpsPurchaseOrderDO::getItemNo,orderNoInfo.getItemNo());
//            if(orderNoInfo.getSplitItem() != null && orderNoInfo.getSplitItem() != 0) {
//                queryWrapper.eq(OpsPurchaseOrderDO::getSplitItemNo,orderNoInfo.getSplitItem());
//            }
//            // update by LiYingChao from bug 9742 in 2023-02-21
//            List<OpsPurchaseOrderDO> opsPurchaseOrderDOS = opsPurchaseOrderMapper.selectList(queryWrapper);
//            if (CollectionUtils.isNotEmpty(opsPurchaseOrderDOS) && StringUtils.isNotBlank(opsPurchaseOrderDOS.get(0).getSupplierId()) ) {
//                info.setStockCode(opsPurchaseOrderDOS.get(0).getSupplierId());
//            } else {
//                info.setStockCode(rcvDetailVO.getStockCode());
//            }
//            RcvMasterVO rcvmasterVO = receiveOrderService.findOrderMaster(rcvDetailVO.getRorderNo()).getData();
//            if (rcvmasterVO != null) {
//                info.setOrderDate(rcvmasterVO.getRordDate());
//            }

            modifyDO = BeanCopyUtil.copy(info, OrderModifyDO.class);
            int i = 0;
            OrderModifyDO orderModifyInfo = null;
            LambdaQueryWrapper<OrderModifyDO> orderModifyQuery = new LambdaQueryWrapper<>();
            orderModifyQuery.select(OrderModifyDO::getId, OrderModifyDO::getStatus);
            orderModifyQuery.eq(OrderModifyDO::getOrderNo, info.getOrderNo())
                    .eq(OrderModifyDO::getModifyType, OrderModifyTypeEnum.cancel_order.getCode());
            List<OrderModifyDO> orderModifylist = orderModifyMapper.selectList(orderModifyQuery);
            if (CollectionUtil.isNotEmpty(orderModifylist)) {
                orderModifyInfo = orderModifylist.get(0);
            }
            if (orderModifyInfo != null) {
                modifyDO.setId(orderModifyInfo.getId());
                modifyDO.setRemark("");
                i = orderModifyMapper.updateById(modifyDO);
            } else {
                // 写入orderModify
                modifyDO.setCreateTime(new Date());
                i = orderModifyMapper.insert(modifyDO);

            }
            if (i != 1) {
                modifyDO.setId(null);
                result.setResult(CancelOrderToSalesStatus.apply_fail.getCode());
                result.setMessage("订单" + modifyDO.getOrderNo() + "申请失败.");
                toCancel = false;
            }
            if (toCancel) {
                // 执行订单取消处理
                ThreadLocalMapUtil.clear();
                ResultVo<String> approveResult = cancelRcvOrder(rcvDetailVO.getRorderNo(), rcvDetailVO.getRorderItem().toString(), "门户提交自动删除", info.getApplyPersonNo());
                log.info("删单接口返回结果: {}", JSONUtil.toJsonStr(approveResult));
                if (approveResult.isSuccess()) {
                    info.setStatus(Integer.parseInt(OrderModifyEnum.finish.getCode()));
                    result.setResult(CancelOrderToSalesStatus.del_success.getCode());
                    info.setRemark(approveResult.getData());
                    result.setMessage("BJ自动删除成功");
                } else if ("201".equals(approveResult.getCode())) {  //北京返回需要手动删除的才处理
                    info.setStatus(Integer.parseInt(OrderModifyEnum.waitHand.getCode())); // 待处理
                    // info.setRemark("BJ需手动:" + "(" + approveResult.getMessage() + ")");
                    info.setRemark(approveResult.getMessage());
                    result.setResult(CancelOrderToSalesStatus.apply_success.getCode());
                    result.setMessage(approveResult.getMessage() + " 请联系相关业务人员.");
                } else if ("202".equals(approveResult.getCode())) {
                    info.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode())); // 不能变更
                    result.setResult(CancelOrderToSalesStatus.del_fail.getCode());
                    // info.setRemark("不能变更BJ:" + approveResult.getMessage());
                    info.setRemark(approveResult.getMessage());
                    result.setMessage("不能变更BJ:" + approveResult.getMessage());
                } else if ("203".equals(approveResult.getCode())) {
                    info.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode())); // 不能变更
                    result.setResult(CancelOrderToSalesStatus.del_fail.getCode());
                    // info.setRemark("不能变更BJ:" + approveResult.getMessage());
                    info.setRemark(approveResult.getMessage());
                    result.setMessage("不能变更BJ:" + approveResult.getMessage());
                } else {
                    info.setStatus(Integer.parseInt(OrderModifyEnum.notHand.getCode())); // 暂不处理
                    result.setResult(CancelOrderToSalesStatus.apply_fail.getCode());
                    result.setMessage(approveResult.getMessage());
                    info.setRemark(approveResult.getMessage());
                    continue;
                }
            }
            result.setHandlePsnNo("System");
            result.setHandleRemark(info.getRemark());
            if (i == 1 && modifyDO.getId() != null && StringUtils.isNotBlank(info.getRemark())) {
                if (info.getRemark().length() > 50) {
                    info.setRemark(info.getRemark().substring(0, 49));
                }
                LambdaUpdateWrapper<OrderModifyDO> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(OrderModifyDO::getId, modifyDO.getId())
                        .set(OrderModifyDO::getStatus, info.getStatus())
                        .set(OrderModifyDO::getRemark, info.getRemark());
                if (info.getStatus() != Integer.parseInt(OrderModifyEnum.waitHand.getCode())) {
                    updateWrapper.set(OrderModifyDO::getApplyPersonNo, "System")
                            .set(OrderModifyDO::getAnswerPns, "System")
                            .set(OrderModifyDO::getAnswerTime, new Date());
                }
                orderModifyMapper.update(null, updateWrapper);
            }
        }
        return ResultVo.success(resultList);
    }

    public Boolean canDelete(Integer status, Integer expQty) {
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
     * 获取门户订单取消处理状态
     *
     * @param cancelStatus 订单取消状态: 1-编辑中; 2-待处理; 4-不通过； 5-处理中; 6-完成; 7-不能变更; 9-取消
     * @return 处理状态
     */
    private String getAdapterOrderCancelStatus(String cancelStatus) {
        if (StringUtils.isBlank(cancelStatus)) {
            return null;
        }
        // 处理结果: 0-申请成功; 1-申请失败; 2-删除成功; 3-删除失败
        String status = null;
        if ("2".equals(cancelStatus) || "5".equals(cancelStatus)) {
            status = "0"; // 0-申请成功
        }
        if ("6".equals(cancelStatus)) {
            status = "2"; // 2-删除成功
        }
        return status;
    }

    /**
     * 审批变更明细项
     */
    private boolean Approve_UpdateRcvDetail(OrderModifyVO vo) {
//        LambdaUpdateWrapper<RcvDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
//        switch (vo.getModifyType()) {
//            case "A":
//                updateWrapper.set(RcvDetailDO::getAddressNo, vo.getAddressNo());
//                break;
//            case "P":
//                updateWrapper.set(RcvDetailDO::getPrice, vo.getPrice());
//                break;
//        }
//        updateWrapper.eq(RcvDetailDO::getRorderFno, vo.getOrderNo());
//        return rcvdetailMapper.update(null, updateWrapper) > 0;
        return false;
    }

    /**
     * 审批变更主体项
     */
    private boolean Approve_UpdateRcvMaster(OrderModifyVO vo) {
//        LambdaUpdateWrapper<RcvMasterDO> updateWrapper = new LambdaUpdateWrapper<>();
//        switch (vo.getModifyType()) {
////            case "O":
////                updateWrapper.set(RcvMasterDO::getCorderNo, vo.getCorderNo);
////                break;
////            case "T":
////                updateWrapper.set(RcvMasterDO::getTransChannel, vo.getTransType());
////                break;
//            case "U":
//                updateWrapper.set(RcvMasterDO::getUserNo, vo.getUserNo());
//                updateWrapper.set(RcvMasterDO::getEndUser, vo.getEndUser());
//                break;
//        }
//
//        updateWrapper.eq(RcvMasterDO::getRorderNo, vo.getOrderNo().substring(0, 7));
//        return rcvmasterMapper.update(null, updateWrapper) > 0;
        return false;
    }

    /**
     * 审批取消
     */
    private ResultVo<String> Approve_CancelModifyOrder(OrderModifyVO vo, String reason, String optUser) {

        Integer status = receiveOrderService.getRcvDetailStatus(vo.getOrderNo());
        if (status == 1) {
            return receiveOrderService.cancelRevDetail(vo.getOrderNo());
        } else {
            String orderNo;
            String itemNo;
            OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(vo.getOrderNo());
            orderNo = orderNoInfo.getOrderNo();
            itemNo = orderNoInfo.getItemNo().toString();
            return this.cancelRcvOrder(orderNo, itemNo, reason, optUser);
        }
    }

    /**
     * 发送日志记录消息
     */
    private void sendorderLog(String orderNo, String desc) {
        // 记录到日志表
        OrderLogVO orderLogVO = new OrderLogVO();
        orderLogVO.setOrderNo(orderNo);
        orderLogVO.setOptType(15);
        orderLogVO.setDescription(desc);
        orderLogVO.setOptTime(new Date());
        orderLogVO.setIp(IpUtil.getIpAddress());
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        orderLogVO.setOptUserName(userDTO.getUserName());
        orderLogVO.setOptUserId(userDTO.getUserNo());
        orderLogFeignApi.sendOrderLogMsgToMQ(orderLogVO);
    }

    /**
     * 直接审批
     */
    @Override
    @DS("sharedb")
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> modifyOrders(ModifyRcvOrderDTO info) {

        if (info.getModifyItems() == null || info.getModifyItems().size() <= 0) {
            return ResultVo.failure("没有需要修改的数据！");
        }
        //分类修改
        switch (info.getModifyType()) {
            case "A":
            case "M":
            case "O":
            case "P":
                //修改接单明细表
                return ModifyOrder_UpdateRcvDetail(info);
            case "T":
            case "U":
                //修改接单主表
                return ModifyOrder_UpdateRcvMaster(info);
            case "C":
                //取消订单
                return ModifyOrder_Cancel(info);
        }
        return ResultVo.failure("修改失败");
    }

    private ResultVo<String> ModifyOrder_Cancel(ModifyRcvOrderDTO info) {
        String msg = "取消订单成功";
        for (ReceiveOrderVO vo : info.getModifyItems()) {
            Integer status = receiveOrderService.getRcvDetailStatus(vo.getRorderFno());
            //状态为1的直接修改接单明细表状态
            if (status == 1) {
                ResultVo<String> result = receiveOrderService.cancelRevDetail(vo.getRorderFno());
                if (!result.isSuccess()) {
                    return ResultVo.failure("取消订单失败");
                }
            } else {
                //其它状态调用北京接口取消
//                CancelForOrderDto dto = new CancelForOrderDto();
//                dto.setOrderId(vo.getRorderNo());
//                dto.setOrderItem(vo.getRorderItem().toString());
//                dto.setReason(info.getModifyReason());
//                opsWmDispatchForOrderFeignApi.cancelRcvOrder(dto);
                ResultVo<String> result = cancelRcvOrder(vo.getRorderNo(), vo.getRorderItem().toString(), info.getModifyReason(), "ops");
                if (!result.isSuccess()) {
                    return ResultVo.failure(result.getMessage());
                }
                msg = result.getData();
            }
            // 记录到日志表
            sendorderLog(vo.getRorderFno(), "取消订单：" + info.getModifyReason() + ";" + info.getModifyRemark());
        }
        return ResultVo.success();
    }

    private ResultVo<String> cancelRcvOrder(String orderNo, String itemNo, String reason, String optUser) {
        try {
            CancelForOrderDto dto = new CancelForOrderDto();
            dto.setOrderId(orderNo);
            dto.setOrderItem(itemNo);
            UserDto userDTO = new UserDto();
            userDTO.setUserName(optUser);
            dto.setUserDto(userDTO);
            dto.setReason(reason);
            CommonResult<String> result = opsOrderFeignApi.autoCancelRcvOrder(dto);
            log.info("opsOrderFengnApi.autoCancelRcvOrder result = {}, params = {}", JSON.toJSONString(result), JSON.toJSONString(dto));
            if (result.isSuccess()) {
                return ResultVo.success(result.getData(), result.getMessage());
            } else {
                return ResultVo.failure(String.valueOf(result.getCode()), result.getMessage());
            }
        } catch (Exception e) {
            log.error("BJ取消订单接口失败：" + e.getMessage());
            throw new BusinessException("BJ取消订单接口失败: " + e.getMessage(), e);
        }
    }

    private ResultVo<String> ModifyOrder_UpdateRcvDetail(ModifyRcvOrderDTO info) {
        LambdaUpdateWrapper<RcvDetailDO> updateWrapper;
        for (ReceiveOrderVO vo : info.getModifyItems()) {
            String modifyTypeDesc = "";
            updateWrapper = new LambdaUpdateWrapper<>();

            switch (info.getModifyType()) {
                case "A":
                    modifyTypeDesc = "订单变更地址:";
                    updateWrapper.set(RcvDetailDO::getAddressNo, vo.getAddressNo());
                    break;
                case "M":
                    modifyTypeDesc = "订单变更客户型号:";
                    updateWrapper.set(RcvDetailDO::getCproductNo, vo.getCproductNo());
                    break;
                case "O":
                    modifyTypeDesc = "订单变更客户订单号:";
                    updateWrapper.set(RcvDetailDO::getCorderNo, vo.getCorderNo());
                    break;
                case "P":
                    modifyTypeDesc = "订单变更单价:";
                    updateWrapper.set(RcvDetailDO::getPrice, vo.getPrice());
                    break;
            }
            updateWrapper.eq(RcvDetailDO::getRorderFno, vo.getRorderFno());
            rcvdetailMapper.update(null, updateWrapper);
            // 记录到日志表
            sendorderLog(vo.getRorderFno(), modifyTypeDesc + info.getModifyReason() + ";" + info.getModifyRemark());
        }
        return ResultVo.success("共修改" + info.getModifyItems().size() + "条数据成功！");
    }

    private ResultVo<String> ModifyOrder_UpdateRcvMaster(ModifyRcvOrderDTO info) {
        Set<String> orders = new HashSet<>();
        LambdaUpdateWrapper<RcvMasterDO> updateWrapper;

        for (ReceiveOrderVO vo : info.getModifyItems()) {
            if (!orders.contains(vo.getRorderNo())) {   //只修改第一条
                orders.add(vo.getRorderNo());
                String modifyTypeDesc = "";
                updateWrapper = new LambdaUpdateWrapper<>();
                switch (info.getModifyType()) {
//                    case "O":
//                        modifyTypeDesc="订单变更客户订单号:";
//                        updateWrapper.set(RcvMasterDO::getCorderNo, vo.getCorderNo());
//                        break;
//                    case "T":
//                        modifyTypeDesc = "订单变更运输:";
//                        updateWrapper.set(RcvMasterDO::getTransChannel, vo.getTransChannel());
//                        break;
                    case "U":
                        modifyTypeDesc = "订单变更用户:";
                        updateWrapper.set(RcvMasterDO::getUserNo, vo.getUserNo());
                        updateWrapper.set(RcvMasterDO::getEndUser, vo.getEndUser());
                        break;
                }

                updateWrapper.eq(RcvMasterDO::getRorderNo, vo.getRorderNo());
                rcvmasterMapper.update(null, updateWrapper);
                // 记录到日志表
                sendorderLog(vo.getRorderFno(), modifyTypeDesc + info.getModifyReason() + ";" + info.getModifyRemark());
            }
        }
        return ResultVo.success("共修改" + orders.size() + "条数据成功！");
    }

    @Override
    public ResultVo<List<ReceiveOrderVO>> listRcvModifyOrders(ReceiveOrderRequest request) {
        List<ReceiveOrderVO> list = rcvdetailMapper.selectJoinList(ReceiveOrderVO.class,
                new MPJLambdaWrapper<>()
                        .select(RcvDetailDO.class, tableFieldInfo -> !tableFieldInfo.getColumn().equals("id"))
                        .select(RcvMasterDO.class, tableFieldInfo -> !tableFieldInfo.getColumn().equals("rorder_no")
                                && !tableFieldInfo.getColumn().equals("create_time") && !tableFieldInfo.getColumn().equals("create_user")
                                && !tableFieldInfo.getColumn().equals("update_time") && !tableFieldInfo.getColumn().equals("update_user"))
                        .selectAs(RcvMasterDO::getId, ReceiveOrderVO::getRmId)
                        .leftJoin(RcvMasterDO.class, RcvMasterDO::getRorderNo, RcvDetailDO::getRorderNo)
                        .in(request.getRorderNos() != null && request.getRorderNos().size() > 0, RcvMasterDO::getRorderNo, request.getRorderNos())
                        .or()
                        .in(request.getRorderNos() != null && request.getRorderNos().size() > 0, RcvDetailDO::getRorderFno, request.getRorderNos())
        );
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.success(Collections.emptyList(), "暂无数据");
        }
        return ResultVo.success(list);
    }

    /**
     * 订单修改驳回
     *
     * @param dto
     */
    @Override
    @DS("sharedb")
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> returnOrderModify(ApproveOrderModifyDTO dto) {
        if (CollectionUtils.isEmpty(dto.getIds())) {
            return ResultVo.failure("请选择需要操作的数据.");
        }
        if (StringUtils.isBlank(dto.getApproveReason())) {
            return ResultVo.failure("请填写原因");
        }
        String key = dto.getIds().toString();
        // add by Lyc from bug 10079 in 2023-03-21
        redissonUtil.lock(key, 60);
        try {
            StringBuilder msg = new StringBuilder();
            String typeDesc = dto.getStatus() == Integer.parseInt(OrderModifyEnum.notHand.getCode()) ? "暂不处理" : "驳回,不能变更";
            List<OrderCancelResult> resultList = new ArrayList<>();
            OrderCancelResult result;
            int count = 0;
            int zdCount = 0;
            LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
            for (Integer id : dto.getIds()) {
                //查找有无修改单数据
                OrderModifyDO info = orderModifyMapper.selectById(id);
                if (info == null) {
                    msg.append(id).append("该订单修改不存在");
                    continue;
                }
                if (info.getStatus() != Integer.parseInt(OrderModifyEnum.waitHand.getCode())
                        && info.getStatus() != Integer.parseInt(OrderModifyEnum.handing.getCode())
                        && info.getStatus() != Integer.parseInt(OrderModifyEnum.notHand.getCode())) {
                    msg.append(info.getOrderNo()).append("该状态不能处理;");
                    continue;
                }

                // 订单转订/变更采购信息 否决
                ResultVo<String> resultVo = null;
                if (StringUtils.isNotBlank(info.getModifyType())) {
                    if (info.getModifyType().startsWith(OrderModifyTypeEnum.ddzd.getCode()) || info.getModifyType().equals(OrderModifyTypeEnum.ddhy.getCode())) {
                        resultVo = rejectZZOrderModify(info, userDTO.getUserNo(), CallBackSMSApplyTypeEnum.ORDER_MODIFY.getCode(), dto.getApproveReason(), dto.getStatus());
                        zdCount++;
                    }
                    if (info.getModifyType().equals(OrderModifyTypeEnum.bgccz.getCode()) || info.getModifyType().equals(OrderModifyTypeEnum.bgysfs.getCode())) {
                        resultVo = rejectZZOrderModify(info, userDTO.getUserNo(), CallBackSMSApplyTypeEnum.PURCHASE_ORDER_CHANGE.getCode(), dto.getApproveReason(), dto.getStatus());
                        zdCount++;
                    }
                    if (resultVo != null && !resultVo.isSuccess()) {
                        msg.append(info.getOrderNo()).append(resultVo.getMessage());
                        continue;
                    }
                }

                OrderModifyDO updateOrderModify = new OrderModifyDO();
                updateOrderModify.setStatus(dto.getStatus());
                // update bu LiYingChao from bugid 9235 in 2023-1-4
                if (StringUtils.isBlank(dto.getApproveReason())) {
                    dto.setApproveReason("");
                }
//                if (StringUtils.isBlank(info.getAnswerText()) || PublicUtil.getWordByteLen(info.getAnswerText()) + PublicUtil.getWordByteLen(dto.getApproveReason()) >= 200 ) {
//
//                } else {
//                    updateOrderModify.setAnswerText(Optional.ofNullable(info.getAnswerText()).orElse("") + dto.getApproveReason());
//                }
                updateOrderModify.setAnswerText(dto.getApproveReason());
                updateOrderModify.setAnswerTime(DateUtil.getNow());
                updateOrderModify.setAnswerPns(userDTO.getUserNo());
                updateOrderModify.setId(info.getId());
                int updcount = 0;
                try {
                    updcount = orderModifyMapper.updateById(updateOrderModify);
                } catch (Exception e) {
                    throw new BusinessException(info.getOrderNo() + "变更失败:" + e.getMessage());
                }
                if (updcount == 1) {
                    // 记录到日志表
                    sendorderLog(info.getOrderNo(), "订单" + typeDesc + " 回复:" + dto.getApproveReason());
                    if (!OrderModifyTypeEnum.cancel_order.getCode().equals(info.getModifyType())) {
                        continue;
                    }
                    result = new OrderCancelResult();
                    result.setNo(info.getApplyNo());
                    result.setOrderNo(info.getOrderNo());
                    result.setResult(dto.getStatus() == Integer.parseInt(OrderModifyEnum.notHand.getCode()) ? CancelOrderToSalesStatus.apply_success.getCode() : CancelOrderToSalesStatus.del_fail.getCode());
                    result.setHandlePsnNo(Optional.ofNullable(userDTO.getUserNo()).orElse(""));
                    result.setHandlePsnName(Optional.ofNullable(userDTO.getRealName()).orElse(""));
                    result.setHandleRemark(typeDesc + "：" + Optional.ofNullable(dto.getApproveReason()).orElse(""));
                    result.setMessage(typeDesc + "：" + Optional.ofNullable(dto.getApproveReason()).orElse(""));
                    resultList.add(result);
                    count++;
                }

                // 订单类型为删单时 否决和暂不处理回调门户 否决->删除失败 暂不处理->申请成功
                if (StringUtils.isNotBlank(info.getModifyType()) && OrderModifyTypeEnum.cancel_order.getCode().equals(info.getModifyType())) {
                    // 门户进来的单才进行回调门户
                    String special = info.getSpecial();
                    SpecialVO specialVO = JSONObject.parseObject(special, SpecialVO.class);
                    ResultVo<String> resultVo1;
                    if (String.valueOf(dto.getStatus()).equals(OrderModifyEnum.bnbg.getCode())) {
                        resultVo1 = callBackSalesWithFouJueZbcl(info, userDTO.getUserNo(), CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(),
                                dto.getApproveReason(), Integer.parseInt(CancelOrderToSalesStatus.del_fail.getCode()), specialVO == null || specialVO.getSecondApproval() == null ? false : specialVO.getSecondApproval());
                    } else {
                        resultVo1 = callBackSalesWithFouJueZbcl(info, userDTO.getUserNo(), CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode(),
                                dto.getApproveReason(), Integer.parseInt(CancelOrderToSalesStatus.confirming.getCode()), specialVO == null || specialVO.getSecondApproval() == null ? false : specialVO.getSecondApproval());
                    }
                    log.info("否决暂不处理修改task表回调参数结果: 订单修改参数 {}, 返回结果 {}", JSONUtil.toJsonStr(info), JSONUtil.toJsonStr(resultVo1));
                }
            }

            if (zdCount > 0) {
                return ResultVo.success(typeDesc + "成功" + count + "条数据");
            }

            if (count > 0) {
                ResultVo<Boolean> sendMsgResult = smsOrderServiceFeignApi.sendOrderCancelReturnMessage(resultList);
                return ResultVo.success("", typeDesc + "成功: " + count + "条数据" + msg);
            } else {
                return ResultVo.failure(typeDesc + "失败；" + msg);
            }
        } finally {
            redissonUtil.unlock(key);
        }
    }

    public ResultVo<String> callBackSalesWithFouJueZbcl(OrderModifyDO orderModifyDO, String optUserNo, int applyType, String answerTest, int status, Boolean secondProcess) {
        OpsSalesCommonParamVO bean = new OpsSalesCommonParamVO();
        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        dealReturnOpsParamVO.setApplyType(applyType);

        DealReturnOpsParam param = new DealReturnOpsParam();
        SalesErpOrderDeleteResultVO vo = new SalesErpOrderDeleteResultVO();
        vo.setApplyItemNo(orderModifyDO.getApplyNo());
        vo.setOrderNo(orderModifyDO.getOrderNo());
        vo.setSecondProcess(secondProcess);
        vo.setStatus(String.valueOf(status));
        vo.setStatusDescription(answerTest);
        param.setSalesErpOrderDeleteResultVo(vo);
        dealReturnOpsParamVO.setDealReturnOpsParam(param);
        bean.setData(dealReturnOpsParamVO);
        UpTaskInfoVO upTaskInfoVO = new UpTaskInfoVO();
        upTaskInfoVO.setBatchNo(orderModifyDO.getBatchNo());
        upTaskInfoVO.setOptUserNo(optUserNo);
        upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_success.getCode());
        upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(bean));
        upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
        salesNotickTaskService.upTaskInfoByBatchNo(upTaskInfoVO);
        return ResultVo.success("操作成功");
    }

    public ResultVo<String> rejectZZOrderModify(OrderModifyDO orderModifyDO, String optUserNo, int applyType, String answerTest, int status) {

        /**
         * 1. 判断批次号是否为空
         * 3. 判断状态 只有待处理和暂不处理能否决
         * 4. 回更modify表 状态 回复内容 回复人 回复时间 更新时间
         * 5. 回更task表 处理状态 操作人 失败的话异常信息 回调参数
         */
        if (StringUtils.isBlank(orderModifyDO.getBatchNo())) {
            return ResultVo.failure(orderModifyDO.getOrderNo() + "批次号为空");
        }
        LambdaUpdateWrapper<OrderModifyDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(OrderModifyDO::getBatchNo, orderModifyDO.getBatchNo())
                .set(OrderModifyDO::getStatus, status)
                .set(StringUtils.isNotBlank(answerTest), OrderModifyDO::getAnswerText, answerTest)
                .set(OrderModifyDO::getAnswerPns, optUserNo)
                .set(OrderModifyDO::getAnswerTime, new Date())
                .set(OrderModifyDO::getUpdateTime, new Date());
        orderModifyMapper.update(null, updateWrapper);

        UpTaskInfoVO upTaskInfoVO = new UpTaskInfoVO();
        upTaskInfoVO.setBatchNo(orderModifyDO.getBatchNo());
        upTaskInfoVO.setOptUserNo(optUserNo);
        OpsSalesCommonParamVO vo = new OpsSalesCommonParamVO();
        DealReturnOpsParamVO dealReturnOpsParamVO;
        if (String.valueOf(status).equals(OrderModifyEnum.notHand.getCode())) {
            dealReturnOpsParamVO = conventCallBackParam(2, orderModifyDO.getApplyNo(), "", applyType, optUserNo + "进行暂不处理,原因:" + answerTest);
        } else {
            dealReturnOpsParamVO = conventCallBackParam(0, orderModifyDO.getApplyNo(), "", applyType, optUserNo + "进行否决,原因:" + answerTest);
        }
        vo.setData(dealReturnOpsParamVO);
        upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_success.getCode());
        upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(vo));
        upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
        salesNotickTaskService.upTaskInfoByBatchNo(upTaskInfoVO);
        return ResultVo.success("操作成功");
    }


    private ShikomiVO getShikomiLotQty(String orderNo, Integer itemNo, Integer splitNo) {
        LambdaQueryWrapper<OpsPurchaseOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsPurchaseOrderDO::getOrderNo, orderNo);
        queryWrapper.eq(OpsPurchaseOrderDO::getItemNo, itemNo);
        queryWrapper.eq(PublicUtil.isNotEmpty(splitNo), OpsPurchaseOrderDO::getSplitItemNo, splitNo);

        List<OpsPurchaseOrderDO> list = opsPurchaseOrderMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        if (list.size() > 1) {
            log.info(orderNo + "-" + itemNo);
        }
        OpsPurchaseOrderDO orderDO = list.get(0);
        if (orderDO == null || StringUtils.isBlank(orderDO.getShikomiAnswerNo())) {
            return null;
        }

        // 查shikomi表
        ResultVo<ShikomiVO> shikomiVOResult = productServiceFeignApi.getShikomiDataByNo(orderDO.getShikomiAnswerNo(), orderDO.getModelNo(), "");
        if (!shikomiVOResult.isSuccess()) {
            log.error("查询参数:" + orderDO.getShikomiAnswerNo() + "," + orderDO.getModelNo() + "。未查询到shikomi信息，" + "订单号：" + orderNo + ",项号" + itemNo);
            return null;
        }

        return shikomiVOResult.getData();
    }

    /**
     * 查询预约的采购订单的LotQty
     *
     * @param orderNo
     * @param itemNo
     * @param modelNo
     * @return
     */
    private OpsPurchaseOrderDO getPrePurchaseOrderLotQty(String orderNo, Integer itemNo, String modelNo) {
        OpsOrderAssignResultDO opsOrderAssignResultDO = rcvdetailMapper.getOrderAssignResult(orderNo, itemNo, modelNo);
        if (opsOrderAssignResultDO == null) {
            return null;
        }

        if (PublicUtil.isEmpty(opsOrderAssignResultDO.getAssociateNo())
                || PublicUtil.isEmpty(opsOrderAssignResultDO.getAssociateNoItem())) {
            return null;
        }

        return getPurchaseOrderLotQty(opsOrderAssignResultDO.getAssociateNo(),
                opsOrderAssignResultDO.getAssociateNoItem(), null, modelNo);
    }

    private OpsPurchaseOrderDO getPurchaseOrderLotQty(String orderNo, Integer itemNo, Integer splitNo, String modelNo) {
        LambdaQueryWrapper<OpsPurchaseOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsPurchaseOrderDO::getOrderNo, orderNo);
        queryWrapper.eq(OpsPurchaseOrderDO::getItemNo, itemNo);
        queryWrapper.eq(PublicUtil.isNotEmpty(splitNo), OpsPurchaseOrderDO::getSplitItemNo, splitNo);
        queryWrapper.eq(PublicUtil.isNotEmpty(modelNo), OpsPurchaseOrderDO::getModelNo, modelNo);

        List<OpsPurchaseOrderDO> list = opsPurchaseOrderMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        OpsPurchaseOrderDO orderDO = list.get(0);
        if (orderDO == null) {
            return null;
        }

        // 查shikomi表
        if (StringUtils.isNotBlank(orderDO.getShikomiAnswerNo())) {
            ResultVo<ShikomiVO> shikomiVOResult = productServiceFeignApi.getShikomiDataByNo(orderDO.getShikomiAnswerNo(), orderDO.getModelNo(), "");
            if (shikomiVOResult.isSuccess() && shikomiVOResult.getData().getLotQty() != null && shikomiVOResult.getData().getLotQty() != 0) {
                orderDO.setQtyReceive(shikomiVOResult.getData().getLotQty());
                orderDO.setRemark("shikomi");
                return orderDO;
            }
        }
        orderDO.setRemark("PO");
        orderDO.setQtyReceive(orderDO.getQuantity());
        return orderDO;
    }

    /**
     * 查询接单的采购的批量数量
     *
     * @param orderNo
     * @param itemNo
     * @return
     */
    private ResultVo<Integer> getRcvOrderPOLotQty(String orderNo, Integer itemNo) {
        //1.查询接单表，判断是否有shikomi号，有查询shikomi的批量数量

        //2.查询ops_inventory_log和ops_report.dbo.stocklog 查询出库对应的库存表id, 返回最大的lotQty


        //最后不存在接单处理
        return ResultVo.success(0);
    }

    /**
     * 计算入库批量E价
     *
     * @return
     */
    @Override
    public ResultVo<String> calcImportLotEPrice() {
        //从redis找上次计算的时间
        Date fromDate = null;
        Object o = redisManager.get("ops:modifyTime");
        if (o == null) {
            fromDate = DateUtil.stringToDateTime("2022-12-01 09:16:55");
        } else {
            fromDate = JSON.parseObject(JSON.toJSONString(o), Date.class);
        }

        //查询ops_inventory_log有批量E加的入库清单
        //判断是采购入库的 ，查采购的数量和shikomi
        Date startTime = new Date();
        StockLogDO stockLogDO;
        Map<String, String> map = new HashMap<>();
        List<OpsInventoryLogVO> list = opsInventoryLogMapper.listInventoryLogCGOrerNo(fromDate);
        for (OpsInventoryLogVO logVO : list) {
            if (map.containsKey(logVO.getOrderNo() + "-" + logVO.getItemNo() + "-" + logVO.getInventoryId())) {
                continue;
            }

            stockLogDO = new StockLogDO();
            BigDecimal allEprice = BigDecimal.ZERO;

            OpsPurchaseOrderDO purchaseOrderDO = this.getPurchaseOrderLotQty(logVO.getOrderNo(), logVO.getItemNo(), null, logVO.getModelno());

            if (purchaseOrderDO == null) {
                log.error("订单:" + logVO.getOrderNo() + "-" + logVO.getItemNo() + "错误");
                continue;
            }
            int lotQty = 0;
            if ("po".equals(purchaseOrderDO.getRemark())) {
                stockLogDO.setLotType(1);
                stockLogDO.setLotQty(purchaseOrderDO.getQtyReceive());
                lotQty = purchaseOrderDO.getQtyReceive();
            } else {
                stockLogDO.setLotType(2);
                stockLogDO.setLotQty(purchaseOrderDO.getQtyReceive());
                stockLogDO.setShikomiNo(purchaseOrderDO.getShikomiAnswerNo());
                lotQty = purchaseOrderDO.getQtyReceive();
            }

            Integer specialModelQty = this.getSpecialModelQty(logVO.getModelno());
            if (specialModelQty != null) {
                lotQty = specialModelQty;
            }

            allEprice = this.getModelLotPrice(logVO.getModelno(), lotQty);
            if (allEprice == null || allEprice.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            log.info("1.1订单:" + logVO.getOrderNo() + "-" + logVO.getItemNo() + "计算E价:" + allEprice);

            LambdaQueryWrapper<StockLogDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StockLogDO::getOrderNo, logVO.getOrderNo());
            queryWrapper.eq(StockLogDO::getItemNo, logVO.getItemNo());
            queryWrapper.eq(StockLogDO::getInventoryId, logVO.getInventoryId());
            Integer count = stockLogMapper.selectCount(queryWrapper);
            if (count > 0) {
                continue;
            }

            stockLogDO.setOut(false);
            stockLogDO.setPropertyId(Optional.ofNullable(logVO.getPropertyId()).orElse(0L));
            stockLogDO.setInventoryId(logVO.getInventoryId());
            stockLogDO.setOrderNo(logVO.getOrderNo());
            stockLogDO.setItemNo(logVO.getItemNo());
            stockLogDO.setImpType(1);
            stockLogDO.setLotPrice(allEprice);
            stockLogDO.setModelNo(logVO.getModelno());
            stockLogDO.setQty(purchaseOrderDO.getQuantity());
            stockLogDO.setSplitItemNo(purchaseOrderDO.getSplitItemNo());
            stockLogMapper.insert(stockLogDO);
            map.put(logVO.getOrderNo() + "-" + logVO.getItemNo() + "-" + logVO.getInventoryId(), stockLogDO.getModelNo());
        }

        //其他调拨入库，查对应来源的入库的采购数量
        List<OpsInventoryLogVO> voList = opsInventoryLogMapper.listInventoryLogDBOrerNo(fromDate);
        for (OpsInventoryLogVO logVO : voList) {
            if (map.containsKey(logVO.getOrderNo() + "-" + logVO.getItemNo() + "-" + logVO.getInventoryId())) {
                continue;
            }
            stockLogDO = new StockLogDO();
            BigDecimal eprice = BigDecimal.ZERO;

            Integer sumQty = opsInventoryLogMapper.getInventoryLogImpSumQty(logVO.getOrderNo(), logVO.getItemNo(), logVO.getModelno());
            if (sumQty == null || sumQty.compareTo(0) == 0) {
                continue;
            }

            //退货入库
            if ("THRK".equalsIgnoreCase(logVO.getVoucherType())) {
                log.info("退货" + logVO.getOrderNo() + ",item" + logVO.getItemNo());
                ResultVo<SalesDataModiDataDO> salesLotQtyResult = getSalesOrderLotQty(logVO.getOrderNo(), logVO.getItemNo(), logVO.getModelno());
                if (salesLotQtyResult == null || !salesLotQtyResult.isSuccess()) {
                    continue;
                }
                if (salesLotQtyResult.getData() != null && salesLotQtyResult.getData().getQuantityLot().compareTo(0) > 0) {
                    stockLogDO.setModelNo(logVO.getModelno());
                    stockLogDO.setQty(sumQty);
                    stockLogDO.setLotPrice(salesLotQtyResult.getData().getEpriceLot());
                    stockLogDO.setOut(false);
                    stockLogDO.setImpType(7);//退货入库
                    stockLogDO.setInventoryId(logVO.getInventoryId());
                    stockLogDO.setPropertyId(logVO.getPropertyId());
                    stockLogDO.setOrderNo(logVO.getOrderNo());
                    stockLogDO.setItemNo(logVO.getItemNo());
                    stockLogDO.setSplitItemNo(0);
                    stockLogDO.setLotQty(salesLotQtyResult.getData().getQuantityLot());
                    stockLogMapper.insert(stockLogDO);
                }
            } else {

                //入库单对应的入库单的批量数量
                ResultVo<Integer> lotQtyResult = getTransOrderImpELotQty(logVO.getOrderNo(), logVO.getItemNo(),
                        sumQty, logVO.getModelno(), logVO.getCreTime());
                if (lotQtyResult == null || lotQtyResult.getData() == null || lotQtyResult.getData() == 0) {
                    continue;
                }

                Integer lotQty = lotQtyResult.getData();
                stockLogDO.setLotQty(lotQty);
                if (lotQty.compareTo(sumQty) < 0) {
                    lotQty = sumQty;
                    stockLogDO.setLotQty(sumQty);
                }

                eprice = this.getModelLotPrice(logVO.getModelno(), lotQty);
                if (eprice == null || eprice.compareTo(BigDecimal.ZERO) <= 0) {
                    log.info("订单：" + logVO.getOrderNo() + "-" + logVO.getItemNo() + "查询不到E价，数量" + lotQty);
                    continue;
                }


                System.out.println("2.订单:" + logVO.getOrderNo() + "-" + logVO.getItemNo() + "数量：" + sumQty + ",log数量：" + lotQty + "计算E价:" + eprice + lotQtyResult.getMessage());

                LambdaQueryWrapper<StockLogDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(StockLogDO::getOrderNo, logVO.getOrderNo());
                queryWrapper.eq(StockLogDO::getItemNo, logVO.getItemNo());
                queryWrapper.eq(StockLogDO::getInventoryId, logVO.getInventoryId());
                List<StockLogDO> stockLogDOList = stockLogMapper.selectList(queryWrapper);
                if (stockLogDOList.size() > 0) {
                    if (stockLogDO.getLotQty().compareTo(stockLogDOList.get(0).getLotQty()) > 0) {
                        for (StockLogDO logDO : stockLogDOList) {
                            logDO.setLotQty(stockLogDO.getLotQty());
                            logDO.setUpdateTime(new Date());
                            stockLogMapper.updateById(logDO);
                        }
                    }
                    continue;
                }

                stockLogDO.setModelNo(logVO.getModelno());
                stockLogDO.setQty(sumQty);
                stockLogDO.setLotPrice(eprice);
                stockLogDO.setOut(false);
                stockLogDO.setImpType(2);
                stockLogDO.setInventoryId(logVO.getInventoryId());
                stockLogDO.setPropertyId(logVO.getPropertyId());
                stockLogDO.setOrderNo(logVO.getOrderNo());
                stockLogDO.setItemNo(logVO.getItemNo());
                stockLogDO.setSplitItemNo(0);
                stockLogMapper.insert(stockLogDO);
            }
            map.put(logVO.getOrderNo() + "-" + logVO.getItemNo() + "-" + logVO.getInventoryId(), stockLogDO.getModelNo());
        }

        redisManager.set("ops:modifyTime", startTime);
        return ResultVo.success("总计算哪段时间内多少条记录");
    }

    /**
     * 查询调拨单的来源的出库库存对应的入库LotQty
     *
     * @param outOrderNo
     * @param outItemNo
     * @param modelNo
     * @return
     */
    public ResultVo<Integer> getTransOrderImpELotQty(String outOrderNo, Integer outItemNo, Integer outQty, String modelNo, Date expTime) {
        //1.查调库的原出库InventoryId
        Long inventoryId = getOutInventoryId(outOrderNo, outItemNo, modelNo);
        if (inventoryId == null) {
            return ResultVo.failure("无出库记录");
        }

        //查询之前的入库记录
        List<InventoryLogDTO> impListDTO = opsInventoryLogMapper.listInventoryAndStockLog(modelNo,
                inventoryId, expTime);
        if (impListDTO == null || impListDTO.isEmpty()) {
            return ResultVo.failure("无之前的入库记录");
        }
        Integer lotQty = 0;
        int sumQty = 0;
        for (InventoryLogDTO dto : impListDTO) {

            if (dto.getLotQty() == null || dto.getLotQty() == 0) {
                continue;
            }

            if (lotQty.compareTo(dto.getLotQty()) < 0) {
                lotQty = dto.getLotQty();
            }

            sumQty = sumQty + dto.getQuantity();
            if (sumQty > outQty) {
                return ResultVo.success(lotQty);
            }
        }
        if (lotQty == 0) {
            Date creTime = DateUtil.addDay(expTime, -1);
            String stringDate = DateUtil.dateToDateTimeString(creTime).substring(0, 10);

            Integer stockQty = dailyInventoryMapper.getStockQty(modelNo, inventoryId, stringDate);
            if (stockQty != null && stockQty.compareTo(0) > 0) {
                return ResultVo.success(stockQty, "前一天在库" + stockQty);
            }
        }
        return ResultVo.success(lotQty, "入库记录");
    }

    public ResultVo<SalesDataModiDataDO> getSalesOrderLotQty(String orderNo, Integer itemNo, String modelNo) {
        if (PublicUtil.isEmpty(orderNo)) {
            return ResultVo.failure("订单号不能为空");
        }
        LambdaQueryWrapper<SalesDataModiDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(SalesDataModiDataDO::getRorderNO, orderNo);
        queryWrapper.eq(SalesDataModiDataDO::getModelNo, modelNo);
        List<SalesDataModiDataDO> list = salesDataModiDataMapper.selectList(queryWrapper);
        if (!list.isEmpty()) {
            SalesDataModiDataDO data = list.get(0);
            return ResultVo.success(data);
        }
        return ResultVo.failure("没有登记变更订单E率salesDataModiData");
    }


    public ResultVo<Integer> calcExportLotQtyByOrder(RcvDetailDO detailDO) {

        int lotQty = 0;

        //1.特殊登记的型号
        Integer specialModelQty = this.getSpecialModelQty(detailDO.getModelNo());
        if (specialModelQty != null) {
            lotQty = specialModelQty;
            return ResultVo.success(lotQty, "特殊登记");
        }


        // 2.有shikomi的使用shikomi的数量计算E价
        if (StringUtils.isNotBlank(detailDO.getShikomiNo())) {
            // 查shikomi表
            ResultVo<ShikomiVO> shikomiVOResult = productServiceFeignApi.getShikomiDataByNo(detailDO.getShikomiNo(), detailDO.getModelNo(), "");
            if (!shikomiVOResult.isSuccess() || shikomiVOResult.getData().getLotQty() == 0) {
                log.error("查询参数:" + detailDO.getShikomiNo() + "," + detailDO.getModelNo() + "。未查询到shikomi信息，" + "订单号：" + detailDO.getRorderNo() + ",项号" + detailDO.getRorderItem());
            } else {
                lotQty = shikomiVOResult.getData().getLotQty();
                return ResultVo.success(lotQty, "订单shikomi" + detailDO.getShikomiNo());
            }
        }

        // 3.采购的以采购表为准
        OpsPurchaseOrderDO orderDO = new OpsPurchaseOrderDO();
        if ("CG".equals(detailDO.getStockType())) {
            // 采购单查采购表是否带了shikomi
            orderDO = this.getPurchaseOrderLotQty(detailDO.getRorderNo(), detailDO.getRorderItem(), null, null);
            if (orderDO == null) {
                return ResultVo.failure("没有查到采购单");
            }
            lotQty = orderDO.getQtyReceive();
            return ResultVo.success(lotQty, "采购" + orderDO.getRemark());
        }
        //4.预约在途
        if ("T".equalsIgnoreCase(detailDO.getStockType())) {
            //预约采购
            orderDO = getPrePurchaseOrderLotQty(detailDO.getRorderNo(), detailDO.getRorderItem(), detailDO.getModelNo());
            if (orderDO != null && orderDO.getQtyReceive() != null) {
                return ResultVo.success(orderDO.getQtyReceive(), "预约采购" + orderDO.getRemark());
            }
        }
        //查出在库id
        Long inventoryId = getOutInventoryId(detailDO.getRorderNo(), detailDO.getRorderItem(), detailDO.getModelNo());
        if (inventoryId == null) {
            return ResultVo.failure("无法查到出库ID");
        }

        int qty = 0;

        //5.查最近入库的批量
        List<InventoryLogDTO> dtoList = opsInventoryLogMapper.listInventoryAndStockLog(detailDO.getModelNo(),
                inventoryId, detailDO.getShipTime());
        lotQty = 0;
        for (InventoryLogDTO logDTO : dtoList) {
            if (logDTO.getLotQty() == null || logDTO.getLotQty().compareTo(0) == 0) {
                continue;
            }

//                if (logDTO.getLotPrice().compareTo(price) < 0 || price.compareTo(BigDecimal.ZERO) == 0) {
//                    price = logDTO.getLotPrice();
//                }
            if (logDTO.getLotQty().compareTo(lotQty) > 0) {
                lotQty = logDTO.getLotQty();
            }
            qty += logDTO.getQuantity();
            if (qty >= detailDO.getQuantity()) {
                //lotQty = dtoList.stream().filter(item -> item.getLotQty()!=null).mapToInt(InventoryLogDTO::getLotQty).max().getAsInt();
                break;
            }
        }
        if (lotQty > 0) {
            return ResultVo.success(lotQty, "历史入库");
        }

        // 6.stocklog表没数据就去DailyInventory表取货齐前一天的库存数再算E价
        Date creTime = DateUtil.addDay(detailDO.getShipTime(), -1);
        String stringDate = DateUtil.dateToDateTimeString(creTime).substring(0, 10);

        Integer stockQty = dailyInventoryMapper.getStockQty(detailDO.getModelNo(), inventoryId, stringDate);
        if (stockQty != null && stockQty.compareTo(0) > 0) {
            return ResultVo.success(stockQty, "前一天在库" + stockQty);
        }

        return ResultVo.failure("无法查到");
    }


    @Override
    public ResultVo<String> updateOrderModifyInfo(OrderModifyVO info) {
        if (Objects.isNull(info)) {
            return ResultVo.failure("参数为空");
        }
        LambdaUpdateWrapper<OrderModifyDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(OrderModifyDO::getBatchNo, info.getBatchNo())
                .set(info.getStatus() != null, OrderModifyDO::getStatus, info.getStatus())
                .set(StringUtils.isNotBlank(info.getReason()), OrderModifyDO::getReason, info.getReason())
                .set(StringUtils.isNotBlank(info.getRemark()), OrderModifyDO::getRemark, info.getRemark())
                .set(StringUtils.isNotBlank(info.getUpdateUser()), OrderModifyDO::getUpdateUser, info.getUpdateUser())
                .set(StringUtils.isNotBlank(info.getAnswerText()), OrderModifyDO::getAnswerText, info.getAnswerText())
                .set(StringUtils.isNotBlank(info.getAnswerPns()), OrderModifyDO::getAnswerPns, info.getAnswerPns())
                .set(info.getAnswerTime() != null, OrderModifyDO::getAnswerTime, info.getAnswerTime())
                .set(OrderModifyDO::getUpdateTime, new Date());
        orderModifyMapper.update(null, updateWrapper);
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> upApproveReplay(UpApproveReplayVO info) {
        if (Objects.isNull(info) || CollectionUtils.isEmpty(info.getBatchNo())) {
            return ResultVo.failure("入参不可为空.");
        }
        List<String> batchNoList = info.getBatchNo().stream()
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(batchNoList)) {
            return ResultVo.success("所选数据批次号为空");
        }
        LambdaQueryWrapper<OrderModifyDO> queryWrapper;
        StringBuilder errMsg = new StringBuilder();
        for (String item : batchNoList) {
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OrderModifyDO::getBatchNo, item);
            List<OrderModifyDO> orderModifyDOS = orderModifyMapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(orderModifyDOS)) {
                continue;
            }
            OrderModifyDO orderModifyDO = orderModifyDOS.get(0);
            DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
            OpsSalesCommonParamVO opsSalesCommonParamVO = new OpsSalesCommonParamVO();
            // 回改任务表信息
            UpTaskInfoVO upTaskInfoVO = new UpTaskInfoVO();
            upTaskInfoVO.setBatchNo(item);
            upTaskInfoVO.setOptUserNo(info.getOptUserNo());
            if (StringUtils.isBlank(orderModifyDO.getModifyType())) {
                errMsg.append(orderModifyDO.getOrderNo()).append("变更类型为空");
                upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
                upTaskInfoVO.setErrorMsg(orderModifyDO.getOrderNo() + "变更类型为空");
                dealReturnOpsParamVO = conventCallBackParam(0, orderModifyDO.getApplyNo(), info.getNewOrderNo(),
                        CallBackSMSApplyTypeEnum.ORDER_MODIFY.getCode(), orderModifyDO.getOrderNo() + "变更类型为空");
                opsSalesCommonParamVO.setData(dealReturnOpsParamVO);
                upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(opsSalesCommonParamVO));
                upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
                salesNotickTaskService.upTaskInfoByBatchNo(upTaskInfoVO);
                continue;
            }
            if (!orderModifyDO.getModifyType().startsWith(OrderModifyTypeEnum.ddzd.getCode())) {
                errMsg.append(orderModifyDO.getOrderNo()).append("变更类型不是转订");
                upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
                upTaskInfoVO.setErrorMsg(orderModifyDO.getOrderNo() + "变更类型不是转订");
                dealReturnOpsParamVO = conventCallBackParam(0, orderModifyDO.getApplyNo(), info.getNewOrderNo(), CallBackSMSApplyTypeEnum.ORDER_MODIFY.getCode(),
                        orderModifyDO.getOrderNo() + "变更类型不是转订");
                opsSalesCommonParamVO.setData(dealReturnOpsParamVO);
                upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(opsSalesCommonParamVO));
                upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
                salesNotickTaskService.upTaskInfoByBatchNo(upTaskInfoVO);
                continue;
            }
            if (!OrderModifyEnum.waitHand.getCode().equals(String.valueOf(orderModifyDO.getStatus()))
                    && !OrderModifyEnum.notHand.getCode().equals(String.valueOf(orderModifyDO.getStatus()))) {
                errMsg.append(orderModifyDO.getOrderNo()).append("状态不是待处理或者暂不处理;");
                upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
                upTaskInfoVO.setErrorMsg(orderModifyDO.getOrderNo() + "状态不是待处理或者暂不处理");
                dealReturnOpsParamVO = conventCallBackParam(0, orderModifyDO.getApplyNo(), info.getNewOrderNo(),
                        CallBackSMSApplyTypeEnum.ORDER_MODIFY.getCode(), orderModifyDO.getOrderNo() + "状态不是待处理或者暂不处理");
                opsSalesCommonParamVO.setData(dealReturnOpsParamVO);
                upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(opsSalesCommonParamVO));
                upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
                salesNotickTaskService.upTaskInfoByBatchNo(upTaskInfoVO);
                continue;
            }
            LambdaUpdateWrapper<OrderModifyDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .eq(OrderModifyDO::getBatchNo, item)
                    .set(OrderModifyDO::getStatus, OrderModifyEnum.finish.getCode())
                    .set(StringUtils.isNotBlank(info.getAnswerText()), OrderModifyDO::getAnswerText, info.getAnswerText())
                    .set(OrderModifyDO::getAnswerPns, info.getOptUserNo())
                    .set(OrderModifyDO::getAnswerTime, new Date())
                    .set(OrderModifyDO::getUpdateTime, new Date());
            orderModifyMapper.update(null, updateWrapper);

            upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_success.getCode());
            dealReturnOpsParamVO = conventCallBackParam(1, orderModifyDO.getApplyNo(), info.getNewOrderNo(),
                    CallBackSMSApplyTypeEnum.ORDER_MODIFY.getCode(), info.getAnswerText());
            opsSalesCommonParamVO.setData(dealReturnOpsParamVO);
            upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(opsSalesCommonParamVO));
            upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
            salesNotickTaskService.upTaskInfoByBatchNo(upTaskInfoVO);

        }
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return ResultVo.failure("处理失败的订单信息" + errMsg.toString());
        }
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> upPoApproveReplay(UpApproveReplayVO info) {
        if (Objects.isNull(info) || CollectionUtils.isEmpty(info.getBatchNo())) {
            return ResultVo.failure("入参不可为空.");
        }
        List<String> batchNoList = info.getBatchNo().stream()
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(batchNoList)) {
            return ResultVo.failure("所选数据批次号为空");
        }
        LambdaQueryWrapper<OrderModifyDO> queryWrapper;
        StringBuilder errMsg = new StringBuilder();
        for (String item : batchNoList) {
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OrderModifyDO::getBatchNo, item);
            List<OrderModifyDO> orderModifyDOS = orderModifyMapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(orderModifyDOS)) {
                continue;
            }
            OrderModifyDO orderModifyDO = orderModifyDOS.get(0);

            info.setNewOrderNo(orderModifyDO.getOrderNo());

            DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
            OpsSalesCommonParamVO opsSalesCommonParamVO = new OpsSalesCommonParamVO();
            // 回改任务表信息
            UpTaskInfoVO upTaskInfoVO = new UpTaskInfoVO();
            upTaskInfoVO.setBatchNo(item);
            upTaskInfoVO.setOptUserNo(info.getOptUserNo());
            if (StringUtils.isBlank(orderModifyDO.getModifyType())) {
                errMsg.append(orderModifyDO.getOrderNo()).append("变更类型为空");
                upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
                upTaskInfoVO.setErrorMsg(orderModifyDO.getOrderNo() + "变更类型为空");
                dealReturnOpsParamVO = conventCallBackParam(0, orderModifyDO.getApplyNo(), info.getNewOrderNo(),
                        CallBackSMSApplyTypeEnum.PURCHASE_ORDER_CHANGE.getCode(), orderModifyDO.getOrderNo() + "变更类型为空");
                opsSalesCommonParamVO.setData(dealReturnOpsParamVO);
                upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(opsSalesCommonParamVO));
                upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
                salesNotickTaskService.upTaskInfoByBatchNo(upTaskInfoVO);
                continue;
            }
            if (!orderModifyDO.getModifyType().equals(OrderModifyTypeEnum.bgccz.getCode()) &&
                    !orderModifyDO.getModifyType().equals(OrderModifyTypeEnum.bgysfs.getCode())) {
                errMsg.append(orderModifyDO.getOrderNo()).append("变更类型不是变更采购信息");
                upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
                upTaskInfoVO.setErrorMsg(orderModifyDO.getOrderNo() + "变更类型不是变更采购信息");
                dealReturnOpsParamVO = conventCallBackParam(0, orderModifyDO.getApplyNo(), info.getNewOrderNo(),
                        CallBackSMSApplyTypeEnum.PURCHASE_ORDER_CHANGE.getCode(), orderModifyDO.getOrderNo() + "变更类型不是变更采购信息");
                opsSalesCommonParamVO.setData(dealReturnOpsParamVO);
                upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(opsSalesCommonParamVO));
                upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
                salesNotickTaskService.upTaskInfoByBatchNo(upTaskInfoVO);
                continue;
            }
            if (!OrderModifyEnum.waitHand.getCode().equals(String.valueOf(orderModifyDO.getStatus()))
                    && !OrderModifyEnum.notHand.getCode().equals(String.valueOf(orderModifyDO.getStatus()))) {
                errMsg.append(orderModifyDO.getOrderNo()).append("状态不是待处理或者暂不处理;");
                upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
                upTaskInfoVO.setErrorMsg(orderModifyDO.getOrderNo() + "状态不是待处理或者暂不处理");
                dealReturnOpsParamVO = conventCallBackParam(0, orderModifyDO.getApplyNo(), info.getNewOrderNo(),
                        CallBackSMSApplyTypeEnum.PURCHASE_ORDER_CHANGE.getCode(), orderModifyDO.getOrderNo() + "状态不是待处理或者暂不处理");
                opsSalesCommonParamVO.setData(dealReturnOpsParamVO);
                upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(opsSalesCommonParamVO));
                upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
                salesNotickTaskService.upTaskInfoByBatchNo(upTaskInfoVO);
                continue;
            }
            LambdaUpdateWrapper<OrderModifyDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .eq(OrderModifyDO::getBatchNo, item)
                    .set(OrderModifyDO::getStatus, OrderModifyEnum.finish.getCode())
                    .set(StringUtils.isNotBlank(info.getAnswerText()), OrderModifyDO::getAnswerText, info.getAnswerText())
                    .set(OrderModifyDO::getAnswerPns, info.getOptUserNo())
                    .set(OrderModifyDO::getAnswerTime, new Date())
                    .set(OrderModifyDO::getUpdateTime, new Date());
            orderModifyMapper.update(null, updateWrapper);

            upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_success.getCode());
            dealReturnOpsParamVO = conventCallBackParam(1, orderModifyDO.getApplyNo(), info.getNewOrderNo(),
                    CallBackSMSApplyTypeEnum.PURCHASE_ORDER_CHANGE.getCode(), info.getAnswerText());
            opsSalesCommonParamVO.setData(dealReturnOpsParamVO);
            upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(opsSalesCommonParamVO));
            upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
            salesNotickTaskService.upTaskInfoByBatchNo(upTaskInfoVO);

        }
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return ResultVo.failure("处理失败的订单信息" + errMsg.toString());
        }
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<OpsSalesCommonParamVO> upPurOrderSupplier(UpPurOrderSupplierVO upPurOrderSupplierVO) {

        if (upPurOrderSupplierVO == null || StringUtils.isBlank(upPurOrderSupplierVO.getOrderno())) {
            return ResultVo.failure("请选择需要操作的数据");
        }

        if (Objects.isNull(upPurOrderSupplierVO.getBatchNo())) {
            return ResultVo.failure("批次号为空.");
        }
        if (StringUtils.isBlank(upPurOrderSupplierVO.getOptUser())) {
            return ResultVo.failure("获取当前登录信息异常,请重新登录.");
        }

        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(upPurOrderSupplierVO.getOrderno());

        LambdaQueryWrapper<OpsPurchaseOrderDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(OpsPurchaseOrderDO::getOrderNo, orderNoInfo.getOrderNo())
                .eq(OpsPurchaseOrderDO::getItemNo, orderNoInfo.getItemNo())
                .eq(orderNoInfo.getSplitItem() != null, OpsPurchaseOrderDO::getSplitItemNo, orderNoInfo.getSplitItem());
        OpsPurchaseOrderDO opsPurchaseOrderDO = opsPurchaseOrderMapper.selectOne(lambdaQueryWrapper);
        if (opsPurchaseOrderDO == null) {
            return ResultVo.failure("未找到" + upPurOrderSupplierVO.getOrderno() + "相关采购单信息");
        }

        // 执行业务接口
        OpsPurchaseorder opsPurchaseorder = conventData(opsPurchaseOrderDO);

        if (StringUtils.isNotBlank(upPurOrderSupplierVO.getSupplierid())) {
            opsPurchaseorder.setSupplierid(upPurOrderSupplierVO.getSupplierid());
        }
        if (StringUtils.isNotBlank(upPurOrderSupplierVO.getTranstype())) {
            opsPurchaseorder.setTranstype(upPurOrderSupplierVO.getTranstype());
        }
        if (StringUtils.isNotBlank(upPurOrderSupplierVO.getHopeexportdate())) {
            opsPurchaseorder.setHopeexportdate(DateUtil.stringToDate(upPurOrderSupplierVO.getHopeexportdate()));
        }
        if (StringUtils.isNotBlank(upPurOrderSupplierVO.getInformation())) {
            opsPurchaseorder.setInformation(upPurOrderSupplierVO.getInformation());
        }
        if (StringUtils.isNotBlank(upPurOrderSupplierVO.getGreencode())) {
            opsPurchaseorder.setGreencode(upPurOrderSupplierVO.getGreencode());
        }

        CommonResult stringCommonResult = purchaseFeignApi.updateTrans(opsPurchaseorder);

        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        // 回改任务表信息
        UpTaskInfoVO upTaskInfoVO = new UpTaskInfoVO();
        OpsSalesCommonParamVO opsSalesCommonParamVO = new OpsSalesCommonParamVO();

        // 回调参数
        upTaskInfoVO.setBatchNo(upPurOrderSupplierVO.getBatchNo());
        upTaskInfoVO.setOptUserNo(upPurOrderSupplierVO.getOptUser());

        OrderModifyVO orderModifyVO = new OrderModifyVO();
        orderModifyVO.setBatchNo(upPurOrderSupplierVO.getBatchNo());
        orderModifyVO.setUpdateUser(upPurOrderSupplierVO.getOptUser());
        orderModifyVO.setAnswerPns(upPurOrderSupplierVO.getOptUser());
        orderModifyVO.setAnswerTime(new Date());

        if (stringCommonResult == null) {
            dealReturnOpsParamVO = conventCallBackParam(0, upPurOrderSupplierVO.getApplyNo(), null,
                    CallBackSMSApplyTypeEnum.ORDER_MODIFY.getCode(), "订单转订->变更供应商无返回结果");
            orderModifyVO.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode()));
            orderModifyVO.setRemark("变更供应商无返回结果");
            orderModifyVO.setAnswerText("变更供应商无返回结果");
            upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
            upTaskInfoVO.setReturnResult("变更供应商无返回结果");
        } else {
            upTaskInfoVO.setReturnResult(JSONUtil.toJsonStr(stringCommonResult));
            if (stringCommonResult.isSuccess()) {
                orderModifyVO.setStatus(Integer.parseInt(OrderModifyEnum.finish.getCode()));
                orderModifyVO.setRemark((String) stringCommonResult.getData());
                orderModifyVO.setAnswerText(orderModifyVO.getRemark());
                dealReturnOpsParamVO = conventCallBackParam(1, upPurOrderSupplierVO.getApplyNo(), null,
                        CallBackSMSApplyTypeEnum.ORDER_MODIFY.getCode(), (String) stringCommonResult.getData());
                upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_success.getCode());
            } else {
                orderModifyVO.setStatus(Integer.parseInt(OrderModifyEnum.bnbg.getCode()));
                orderModifyVO.setRemark(stringCommonResult.getMessage());
                orderModifyVO.setAnswerText(orderModifyVO.getRemark());
                dealReturnOpsParamVO = conventCallBackParam(0, upPurOrderSupplierVO.getApplyNo(), null,
                        CallBackSMSApplyTypeEnum.ORDER_MODIFY.getCode(), stringCommonResult.getMessage());
                upTaskInfoVO.setHandleStatus(OpsSalesTaskHandStatus.hand_error.getCode());
            }
        }

        opsSalesCommonParamVO.setData(dealReturnOpsParamVO);
        upTaskInfoVO.setErrorMsg("");
        upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(opsSalesCommonParamVO));
        upTaskInfoVO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
        salesNotickTaskService.upTaskInfoByBatchNo(upTaskInfoVO);
        updateOrderModifyInfo(orderModifyVO);
        return ResultVo.success(opsSalesCommonParamVO);
    }


    @Override
    public ResultVo<String> updateOrderModifyStatusById(UpdateOrderModifyParam info) {
        if (info.getId() == null) {
            return ResultVo.failure("入参不可为空");
        }
        LambdaUpdateWrapper<OrderModifyDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(OrderModifyDO::getId, info.getId()).set(OrderModifyDO::getStatus, info.getStatus());
        orderModifyMapper.update(null, updateWrapper);
        return ResultVo.success("状态更新完毕");
    }


    public OpsPurchaseorder conventData(OpsPurchaseOrderDO opsPurchaseOrderDO) {
        OpsPurchaseorder dto = new OpsPurchaseorder();
        dto.setId(opsPurchaseOrderDO.getId());
        dto.setOrderno(opsPurchaseOrderDO.getOrderNo());
        dto.setItemno(opsPurchaseOrderDO.getItemNo());
        dto.setSplititemno(opsPurchaseOrderDO.getSplitItemNo());
        dto.setMergeflag(opsPurchaseOrderDO.getMergeflag());
        dto.setModelno(opsPurchaseOrderDO.getModelNo());
        dto.setQuantity(opsPurchaseOrderDO.getQuantity());
        dto.setStdprice(BigDecimal.valueOf(opsPurchaseOrderDO.getStdPrice()));
        dto.setTranstype(opsPurchaseOrderDO.getTransType());
        dto.setPurchasedate(opsPurchaseOrderDO.getPurchaseDate());
        dto.setHopedeliverydate(opsPurchaseOrderDO.getHopeDeliveryDate());
        dto.setSupplierid(opsPurchaseOrderDO.getSupplierId());
        dto.setStatecode(opsPurchaseOrderDO.getStateCode());
        dto.setOrdtype(opsPurchaseOrderDO.getOrdType());
        dto.setSpecmark(opsPurchaseOrderDO.getSpecMark());
        dto.setReceivewarehouseid(opsPurchaseOrderDO.getReceiveWarehouseId());
        dto.setRemark(opsPurchaseOrderDO.getRemark());
        dto.setHopeexportdate(opsPurchaseOrderDO.getHopeExportDate());
        dto.setInqno(opsPurchaseOrderDO.getInqNo());
        dto.setShikomianswerno(opsPurchaseOrderDO.getShikomiAnswerNo());
        dto.setOperatorid(opsPurchaseOrderDO.getOperatorId());
        dto.setDeptno(opsPurchaseOrderDO.getDeptNo());
        dto.setApplyDeptNo(opsPurchaseOrderDO.getApplyDeptNo());
        dto.setSmccode(opsPurchaseOrderDO.getSmcCode());
        dto.setInvoiceno(opsPurchaseOrderDO.getInvoiceNo());
        dto.setHscode(opsPurchaseOrderDO.getHsCode());
        dto.setGreencode(opsPurchaseOrderDO.getGreenCode());
        dto.setProducttype(opsPurchaseOrderDO.getProductType());
        dto.setCustomerno(opsPurchaseOrderDO.getCustomerNo());
        dto.setUserno(opsPurchaseOrderDO.getUserNo());
        dto.setSalesinfono(opsPurchaseOrderDO.getSalesInfoNo());
        dto.setPurchasetype(opsPurchaseOrderDO.getPurchaseType());
        dto.setSupplierpartno(opsPurchaseOrderDO.getSupplierPartNo());
        dto.setImportfobpriceoriginal(BigDecimal.valueOf(opsPurchaseOrderDO.getImportFobPriceOriginal()));
        dto.setImportcurrencyid(opsPurchaseOrderDO.getImportCurrencyId());
        dto.setServerremark(opsPurchaseOrderDO.getServerremark());
        dto.setUpdatetime(opsPurchaseOrderDO.getUpdateTime());
        dto.setCorderno(opsPurchaseOrderDO.getCorderNO());
        dto.setInventorypropertyid(opsPurchaseOrderDO.getInventoryPropertyId());
        dto.setReplyorderno(opsPurchaseOrderDO.getReplyOrderNo());
        dto.setDlvmoddate(opsPurchaseOrderDO.getDlvModDate());
        dto.setQtyreceive(opsPurchaseOrderDO.getQtyReceive());
        dto.setFinishdate(opsPurchaseOrderDO.getFinishDate());
        dto.setPortArrivedate(opsPurchaseOrderDO.getPortArrivedate());
        dto.setCustomsDate(opsPurchaseOrderDO.getCustomsDate());
        dto.setBeginProduceDate(opsPurchaseOrderDO.getBeginProduceDate());
        dto.setPoPrice(opsPurchaseOrderDO.getPoPrice());
        dto.setInspectiontype(opsPurchaseOrderDO.getInspectiontype());
        dto.setOperator(opsPurchaseOrderDO.getOperator());
        dto.setHopereceivewarehouse(opsPurchaseOrderDO.getHopereceivewarehouse());
        dto.setInformation(opsPurchaseOrderDO.getInformation());
        dto.setFilepath(opsPurchaseOrderDO.getFilepath());
        dto.setSendversion(opsPurchaseOrderDO.getSendversion());
        return dto;
    }

    public DealReturnOpsParamVO conventCallBackParam(int isSuccess, String applyNo, String newOrderNo, int applyType, String desc) {

        // 最外层实体
        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        dealReturnOpsParamVO.setApplyType(applyType);

        OpsOrderResultBean bean = new OpsOrderResultBean();
        bean.setApplyNo(applyNo);
        bean.setResultDescription(desc);
        if (isSuccess == 1) {
            bean.setResult("成功");
        } else if (isSuccess == 0) {
            bean.setResult("失败");
        } else if (isSuccess == 2) {
            bean.setResult("确认中");
            bean.setResultDescription("暂不处理");
        }
        if (StringUtils.isNotBlank(newOrderNo)) {
            bean.setNewOrderNo(newOrderNo);
        }
        OpsSalesCommonParamVO vo = new OpsSalesCommonParamVO();
        vo.setData(bean);
        DealReturnOpsParam param = new DealReturnOpsParam();
        param.setOpsSalesCommonParamVo(vo);
        dealReturnOpsParamVO.setDealReturnOpsParam(param);
        return dealReturnOpsParamVO;
    }


    private BigDecimal getModelLotPrice(String modelNo, Integer lotQty) {
        BigDecimal eprice = productPriceMapper.getLotEPrice(modelNo, lotQty);
        if (eprice == null || eprice.compareTo(BigDecimal.ZERO) <= 0) {
            eprice = productPriceMapper.getEPriceOfMinQty(modelNo);
        }
        return eprice;
    }

    /***
     * 查询最近入库最低的E价
     * @param modelNo
     * @param inventoryId
     * @return
     */
    private BigDecimal getLastMinImportEPrice(String modelNo, Long inventoryId, Date shipTime, Integer totalQty) {
        Integer qty = 0;
        BigDecimal minEPrice = null;
        if (inventoryId == null) {
            return minEPrice;
        }

        //出在库,查发货前的入库记录
        List<InventoryLogDTO> dtoList = opsInventoryLogMapper.listInventoryAndStockLog(modelNo, inventoryId, shipTime);
        for (InventoryLogDTO logDTO : dtoList) {
            if (logDTO.getLotQty() == null || logDTO.getLotPrice() == null) {
                continue;
            }
            if (minEPrice == null || logDTO.getLotPrice().compareTo(minEPrice) < 0) {
                minEPrice = logDTO.getLotPrice();
            }
            // 取大于出库数量的记录
            qty += logDTO.getQuantity();
            if (qty.compareTo(totalQty) > 0) {
                break;
            }
        }
        return minEPrice;
    }


    /**
     * 获取特殊备案型号E价
     *
     * @param modelNo
     * @return
     */
    private Integer getSpecialModelQty(String modelNo) {
        Object o = redisManager.hGet("ops:modifyModels", modelNo);

        if (o == null) {
            return null;
        }
        Integer quantity = Integer.valueOf(o.toString());

        return quantity;
    }

    public Long getOutInventoryId(String orderNo, Integer itemNo, String modelNo) {
        Long inventoryId = opsInventoryLogMapper.getOutInventoryIdByOrder(orderNo, itemNo, modelNo);
        if (inventoryId == null) {
            inventoryId = opsInventoryLogBakMapper.getOutInventoryIdByOrderFromBak(orderNo, itemNo, modelNo);
        }
        return inventoryId;
    }

    @Override
    public ResultVo<String> calcExportLotEPrice() {
        //从redis找上次计算的时间
        Date fromDate = null;
        Integer successCount = 0;
        Object o = redisManager.get("ops:modifyExportPriceTime");
        if (o == null) {
            fromDate = DateUtil.stringToDateTime("2025-09-10 00:00:00");
        } else {
            fromDate = JSON.parseObject(JSON.toJSONString(o), Date.class);
        }
        Date startTime = new Date();
        // 非组装订单
        List<RcvDetailDO> list = rcvdetailMapper.listRcvOrderModify(fromDate);
        SalesDataModiDataDO modiDataDO;
        String fromType = null;
        for (RcvDetailDO detailDO : list) {
            //BigDecimal allEprice = BigDecimal.ZERO;
            ResultVo<Integer> resultVOLotQty = calcExportLotQtyByOrder(detailDO);
            if (!resultVOLotQty.isSuccess() || resultVOLotQty.getData() == null
                    || resultVOLotQty.getData().compareTo(0) <= 0) {
                continue;
            }
            Integer lotQty = resultVOLotQty.getData();
            BigDecimal eprice = this.getModelLotPrice(detailDO.getModelNo(), lotQty);
            if (eprice == null || eprice.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            log.info("非组装订单:" + detailDO.getRorderNo() + "-"
                    + detailDO.getRorderItem() + "型号:" + detailDO.getModelNo() + " E价为:" + eprice + "原E价" + detailDO.getEPrice() + "订单数量:" + detailDO.getQuantity() + "Log 数量" + lotQty + " : " + resultVOLotQty.getMessage());

            //lotQty大于订单数量，或者E价低于订单的E价
            if (lotQty > detailDO.getQuantity()
                    || detailDO.getEPrice() == null || detailDO.getEPrice().compareTo(BigDecimal.ZERO) == 0
                    || eprice.compareTo(detailDO.getEPrice()) < 0) {

                // 用于计算的LOT数量小于原数量，直接跳过

                LambdaQueryWrapper<SalesDataModiDataDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SalesDataModiDataDO::getRorderNO, detailDO.getRorderFno());
                Integer count = salesDataModiDataMapper.selectCount(queryWrapper);
                // 已存在不添加
                if (count > 0) {
                    continue;
                }

                modiDataDO = new SalesDataModiDataDO();
                modiDataDO.setRorderNO(detailDO.getRorderFno());
                modiDataDO.setModelNo(detailDO.getModelNo());
                modiDataDO.setQuantity(detailDO.getQuantity());
                modiDataDO.setEpriceLot(eprice);
                modiDataDO.setEPriceBaseLot(detailDO.getEPrice());
                modiDataDO.setQuantityLot(lotQty);
                modiDataDO.setUpdateTime(new Date());
                modiDataDO.setUserName("OPS");
                salesDataModiDataMapper.insert(modiDataDO);

                successCount = successCount + 1;
            }
        }


        StringBuilder sbMsg = new StringBuilder();
        sbMsg.append("非组装订单成功订单数：").append(successCount);
        ResultVo<String> resultVo = calcExportLotEPriceAssOrder(fromDate);
        sbMsg.append(resultVo.getMessage());

        redisManager.set("ops:modifyExportPriceTime", startTime);
        sbMsg.append("截止时间：").append(DateUtil.dateToDateTimeString(startTime));


        return ResultVo.success(sbMsg.toString());
    }

    @Override
    public ResultVo<String> calcExportLotEPriceAssOrder(Date fromDate) {
        Integer successCount = 0;
        // 组装订单
        List<RcvDetailDO> splitOrderList = rcvdetailMapper.listSplitModelRcvOrder(fromDate);
        for (RcvDetailDO detailDO : splitOrderList) {
            ResultVo<List<OpsOrderAssignResultVO>> resultVo = receiveOrderService.getOrderAssItemsByModelNo(detailDO.getRorderNo(), detailDO.getRorderItem());
            if (!resultVo.isSuccess()) {
                log.error("未查询到订单:" + detailDO.getRorderFno() + ",型号:" + detailDO.getModelNo() + "的拆分信息");
                continue;
            }
            List<OpsOrderAssignResultVO> voList = resultVo.getData();
            BigDecimal allEprice = BigDecimal.ZERO;
            BigDecimal eprice = BigDecimal.ZERO;
            BigDecimal totalEAmount = BigDecimal.ZERO;//子项的E金额
            int quantityLot = 0;
            //子项有批量E价
            boolean hasAssLot = false;
            for (OpsOrderAssignResultVO statusVO : voList) {
                int lotQty = 0;
                // int qty = 0;
                //int quantity = statusVO.getQty() / detailDO.getQuantity();
                BigDecimal modelEPrice = null;

                //是否有批量E价
                //LambdaQueryWrapper<ProductPriceDO> productPriceQryWrapper = new LambdaQueryWrapper<>();
                //productPriceQryWrapper.eq(ProductPriceDO::getModelNo, statusVO.getModelno());
                Integer count = productPriceMapper.hasLotProductPrice(statusVO.getModelno());
                //如果没有批量E价,使用1个的E价
                if (count.compareTo(0) == 0) {
                    //ResultVo<String> lotPrice = productServiceFeignApi.getModelLotPrice(statusVO.getModelno(), 1);
                    modelEPrice = getModelLotPrice(statusVO.getModelno(), 1);
                    if (modelEPrice != null && modelEPrice.compareTo(BigDecimal.ZERO) > 0) {
                        totalEAmount = BigDecimalUtil.add(totalEAmount, BigDecimalUtil.mul(modelEPrice, statusVO.getQuantity()));
                        continue;
                    } else {
                        totalEAmount = BigDecimal.ZERO;
                        break;
                    }
                } else {
                    hasAssLot = true;

                    RcvDetailDO assOrderDO = new RcvDetailDO();
                    assOrderDO.setRorderNo(detailDO.getRorderNo());
                    assOrderDO.setRorderItem(detailDO.getRorderItem());
                    assOrderDO.setModelNo(statusVO.getModelno());
                    assOrderDO.setQuantity(statusVO.getQuantity());
                    assOrderDO.setStockType(statusVO.getStockType());
                    assOrderDO.setShipTime(detailDO.getShipTime());
                    ResultVo<Integer> resultVoLotQty = calcExportLotQtyByOrder(assOrderDO);

                    if (resultVoLotQty.isSuccess() && resultVoLotQty.getData().compareTo(0) > 0) {
                        log.info("组装子项" + detailDO.getRorderFno() + "子型号" + statusVO.getModelno() + ",原数量:" + detailDO.getQuantity()
                                + ", lot数量:" + resultVoLotQty.getData() + resultVoLotQty.getMessage());

                        lotQty = resultVoLotQty.getData();
                        modelEPrice = getModelLotPrice(statusVO.getModelno(), lotQty);
                        if (modelEPrice == null || modelEPrice.compareTo(BigDecimal.ZERO) <= 0) {
                            totalEAmount = BigDecimal.ZERO;
                            break;
                        }
                        totalEAmount = BigDecimalUtil.add(totalEAmount, BigDecimalUtil.mul(modelEPrice, statusVO.getQuantity()));
                    } else {
                        totalEAmount = BigDecimal.ZERO;
                        break;
                    }
                }
            }


            // 用于计算的LOT数量小于原订单数量，直接跳过
            if (!hasAssLot || totalEAmount == null || totalEAmount.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            BigDecimal orderEPrice = BigDecimalUtil.div(totalEAmount, detailDO.getQuantity(), 2);
            log.info("E组装订单" + detailDO.getRorderFno() + " E价为:" + orderEPrice + ",原E价为:" + detailDO.getEPrice() + ",原数量:" + detailDO.getQuantity() + ",has lot:" + hasAssLot);
            if (detailDO.getEPrice() != null && orderEPrice.compareTo(detailDO.getEPrice()) >= 0) {
                continue;
            }
            allEprice = orderEPrice;
            quantityLot = detailDO.getQuantity();

            LambdaQueryWrapper<SalesDataModiDataDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SalesDataModiDataDO::getRorderNO, detailDO.getRorderFno());
            Integer count = salesDataModiDataMapper.selectCount(queryWrapper);
            // 已存在不添加
            if (count > 0) {
                continue;
            }

            Integer specialModelQty = this.getSpecialModelQty(detailDO.getModelNo());
            if (specialModelQty != null) {
                quantityLot = specialModelQty;
                allEprice = this.getModelLotPrice(detailDO.getModelNo(), specialModelQty);
            }

            SalesDataModiDataDO modiDataDO = new SalesDataModiDataDO();
            modiDataDO.setRorderNO(detailDO.getRorderFno());
            modiDataDO.setModelNo(detailDO.getModelNo());
            modiDataDO.setQuantity(detailDO.getQuantity());
            modiDataDO.setEpriceLot(allEprice);
            modiDataDO.setEPriceBaseLot(detailDO.getEPrice());
            modiDataDO.setQuantityLot(quantityLot);
            modiDataDO.setUpdateTime(new Date());
            modiDataDO.setUserName("OPS");
            salesDataModiDataMapper.insert(modiDataDO);
            successCount = successCount + 1;
        }

        return ResultVo.success("组装订单：" + successCount.toString());
    }


}
