package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.CarrierEnum;
import com.smc.smccloud.core.model.enums.RCVOrderStatusEnum;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.expdetail.ExpdetailSalesVO;
import com.smc.smccloud.model.expdetail.ExpdetailVO;
import com.smc.smccloud.model.order.RcvOrderViewDO;
import com.smc.smccloud.model.orderstate.*;
import com.smc.smccloud.model.adapter.order.OrderDetailDTO;
import com.smc.smccloud.model.adapter.order.OrderDetailVO;
import com.smc.smccloud.model.order.RcvDetailDO;
import com.smc.smccloud.model.order.RcvMasterDO;
import com.smc.smccloud.model.orderstate.OrderStateDetailDTO;
import com.smc.smccloud.model.orderstate.OrderSubStateDTO;
import com.smc.smccloud.service.ExpdetailService;
import com.smc.smccloud.service.OpsCommonService;
import com.smc.smccloud.service.SMSOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author edp04
 * @title: SMSOrderServiceImpl
 * @date 2022/05/11 10:20
 */
@Service
@Slf4j
public class SMSOrderServiceImpl implements SMSOrderService {

    @Resource
    private OrderStateMapper orderStateMapper;
    @Resource
    private RcvorderViewMapper rcvorderViewMapper;

    @Resource
    private ExpdetailMapper expdetailMapper;

    @Resource
    private OpsCommonService opsCommonService;

    @Override
    public ResultVo<List<OrderDetailVO>> listOrderDetailForAgent(OrderDetailDTO detailDTO) {
        if (CollectionUtil.isEmpty(detailDTO.getAgentNo())) {
            return ResultVo.failure("请输入代理商代码");
        }

//        List<RcvDetailDO> list = new ArrayList<>();
//        QueryWrapper<RcvDetailDO> detailQuery = new QueryWrapper<>();
//        QueryWrapper<RcvMasterDO> masterQuery = new QueryWrapper<>();
//        OrderDetailVO detailVO;
//        RcvMasterDO masterDO;
//        List<OrderDetailVO> detailVOList = new ArrayList<>();
//
//        if (PublicUtil.isNotEmpty(detailDTO.getOrder_nos())) {
//            RcvDetailDO detailDO;
//            for (String orderNo : detailDTO.getOrder_nos()) {
//                detailQuery.clear();
//                detailQuery.eq("rorder_fno", orderNo);
//                if (detailDTO.getDate_type() == 1) {
//                    detailQuery.between("create_time", detailDTO.getFrom_time(), detailDTO.getTo_time());
//                }
//                if (detailDTO.getDate_type() == 2) {
//                    detailQuery.between("ship_time", detailDTO.getFrom_time(), detailDTO.getTo_time());
//                }
//                if (detailDTO.getDate_type() == 3) {
//                    detailQuery.between("update_time", detailDTO.getFrom_time(), detailDTO.getTo_time());
//                }
//                if (PublicUtil.isNotEmpty(detailDTO.getOrder_state()) && detailDTO.getOrder_state() == 9) {
//                    detailQuery.eq("status", 9);
//                }
//                detailDO = rcvdetailMapper.selectOne(detailQuery);
//                if (detailDO == null) {
//                    continue;
//                }
//
//                masterQuery.clear();
//                masterQuery.eq("rorder_no", detailDO.getRorderNo());
//                masterQuery.eq("customer_no", detailDTO.getAgentNo());
//                masterDO = rcvmasterMapper.selectOne(masterQuery);
//                if (masterDO == null) {
//                    continue;
//                }
//
//                list.add(detailDO);
//            }
//        }
//        if (PublicUtil.isNotEmpty(detailDTO.getCorder_nos())) {
//            List<RcvDetailDO> doList;
//            for (String corderNo : detailDTO.getCorder_nos()) {
//                detailQuery.clear();
//                detailQuery.eq("corder_no", corderNo);
//                if (detailDTO.getDate_type() == 1) {
//                    detailQuery.between("create_time", detailDTO.getFrom_time(), detailDTO.getTo_time());
//                }
//                if (detailDTO.getDate_type() == 2) {
//                    detailQuery.between("ship_time", detailDTO.getFrom_time(), detailDTO.getTo_time());
//                }
//                if (detailDTO.getDate_type() == 3) {
//                    detailQuery.between("update_time", detailDTO.getFrom_time(), detailDTO.getTo_time());
//                }
//                if (PublicUtil.isNotEmpty(detailDTO.getOrder_state()) && detailDTO.getOrder_state() == 9) {
//                    detailQuery.eq("status", 9);
//                }
//                doList = rcvdetailMapper.selectList(detailQuery);
//                if (PublicUtil.isEmpty(doList)) {
//                    continue;
//                }
//
//                masterQuery.clear();
//                masterQuery.eq("rorder_no", doList.get(0).getRorderNo());
//                masterQuery.eq("customer_no", detailDTO.getAgentNo());
//                masterDO = rcvmasterMapper.selectOne(masterQuery);
//                if (masterDO == null) {
//                    continue;
//                }
//                list.addAll(doList);
//            }
//        }
        log.info("listOrderDetailForAgent 查询参数: {}", detailDTO);
//        if (PublicUtil.isNotEmpty(detailDTO.getTo_time())) {
//            Date endTime = DateUtil.getEndTime(detailDTO.getTo_time());
//            detailDTO.setTo_time(endTime);
//        }
        LambdaQueryWrapper<RcvOrderViewDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(RcvOrderViewDO::getCustomerNo, detailDTO.getAgentNo())
                .in(PublicUtil.isNotEmpty(detailDTO.getOrder_nos()), RcvOrderViewDO::getRorderFno, detailDTO.getOrder_nos())
                .in(PublicUtil.isNotEmpty(detailDTO.getCorder_nos()), RcvOrderViewDO::getCorderNo, detailDTO.getCorder_nos())
                .between(detailDTO.getDate_type() == 1, RcvOrderViewDO::getRordDate, detailDTO.getFrom_time(), detailDTO.getTo_time())
                .between(detailDTO.getDate_type() == 2, RcvOrderViewDO::getShipTime, detailDTO.getFrom_time(), detailDTO.getTo_time())
                .between(detailDTO.getDate_type() == 3, RcvOrderViewDO::getUpdateTime, detailDTO.getFrom_time(), detailDTO.getTo_time());

        if (PublicUtil.isNotEmpty(detailDTO.getOrder_state()) && detailDTO.getOrder_state() == 9) {
            queryWrapper.eq(RcvOrderViewDO::getStatus, 9);
        }

        List<RcvOrderViewDO> list = rcvorderViewMapper.selectList(queryWrapper);

        // 批量订单号查询
        //ResultVo<ExpdetailVO> resultVo;
        OrderDetailVO detailVO;
        List<OrderDetailVO> detailVOList = new ArrayList<>(list.size());
        for (RcvOrderViewDO detailDO : list) {
            detailVO = new OrderDetailVO();
            detailVO.setOrder_no(detailDO.getRorderFno());
            detailVO.setPrice(detailDO.getPrice());
            detailVO.setModel_no(detailDO.getModelNo());
            detailVO.setQuantity(detailDO.getQuantity());
            detailVO.setOrder_date(detailDO.getRordDate());
            detailVO.setCmodel_no(detailDO.getCproductNo());
            detailVO.setCorder_no(detailDO.getCorderNo());
            detailVO.setModel_name(detailDO.getProductName());
            detailVO.setEprice(detailDO.getEPrice());
            detailVO.setAgent_salesprice(detailDO.getPriceUser());
            detailVO.setUser_no(detailDO.getUserNo());
            detailVO.setExp_stock(detailDO.getStockCode());
            detailVO.setCsend_date(detailDO.getWmsDlvDate());
            detailVO.setRemark(detailDO.getRemark());
            detailVO.setTax_rate(detailDO.getTaxRate());
            detailVO.setShip_date(detailDO.getShipTime());
            detailVO.setStatus(String.valueOf(detailDO.getStatus()));
            detailVO.setStatus_desc(RCVOrderStatusEnum.getName(detailDO.getStatus()));
            detailVO.setOrder_psn(detailDO.getEmployeeNo());
            detailVO.setExpress_no(detailDO.getExpressNo());
            detailVO.setProjectNo(detailDO.getProjectNo());
            detailVO.setEndUserNo(detailDO.getEndUser());
            if (StringUtils.isNotBlank(detailDO.getEndUser())) {
                detailVO.setEndUserName(opsCommonService.getCustomerNameByNo(detailDO.getEndUser()));
            }
//            resultVo = expdetailService.listExpdetailByOrderNo(detailDO.getRorderFno());
//            if (resultVo.isSuccess()) {
//                detailVO.setExpress_no(resultVo.getData().getExpressNo());
//            }
            detailVOList.add(detailVO);
        }

        return ResultVo.success(detailVOList);
    }

    @Override
    public ResultVo<OrderStateDetailDTO> listOrderStateByNo(String order_no) {
        if (PublicUtil.isEmpty(order_no)) {
            return ResultVo.failure();
        }
        QueryWrapper<OrderStateDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", order_no);

        OrderStateDetailDTO detailDTO = new OrderStateDetailDTO();
        List<OrderSubStateDTO> dtoList = new ArrayList<>();
        OrderSubStateDTO stateDTO;
        List<OrderStateDO> list = orderStateMapper.selectList(queryWrapper);

        if (PublicUtil.isEmpty(list)) {
            return ResultVo.success(detailDTO);
        }
        detailDTO.setStatus(String.valueOf(list.get(0).getStateCode()));
        detailDTO.setModel_no(list.get(0).getModelNo());
        detailDTO.setQuantity(list.get(0).getQuantity());
        detailDTO.setStatus_desc(list.get(0).getStateDes());
        for (OrderStateDO stateDO : list) {
            stateDTO = new OrderSubStateDTO();
            stateDTO.setStatus(String.valueOf(stateDO.getStateCode()));
            stateDTO.setModel_no(stateDO.getModelNo());
            stateDTO.setQuantity(stateDO.getQuantity());
            stateDTO.setStatus_desc(stateDO.getStateDes());
            dtoList.add(stateDTO);
        }
        detailDTO.setSub_statuses(dtoList);

        return ResultVo.success(detailDTO);
    }

    @Override
    public ResultVo<List<ExpdetailSalesVO>> listOrderExpdetailWithSales(OrderDetailDTO detailDTO) {
        if(Objects.isNull(detailDTO)) {
            return ResultVo.success();
        }
        if (CollectionUtil.isEmpty(detailDTO.getAgentNo())) {
            return ResultVo.failure("请输入代理商代码");
        }

        log.info("[门户lifeTeam]发货查询参数:{}", JSONObject.toJSONString(detailDTO));

        if (detailDTO.getFrom_time() != null) {
            detailDTO.setFromTimeStr(DateUtil.dateToDateTimeString(detailDTO.getFrom_time()));
        }
        if (detailDTO.getTo_time() != null) {
            detailDTO.setToTimeStr(DateUtil.dateToDateTimeString(detailDTO.getTo_time()));
        }

        List<ExpdetailSalesVO> expdetailSalesVOS = expdetailMapper.listOrderExpdetailWithSales(detailDTO);
        if (CollectionUtils.isEmpty(expdetailSalesVOS)) {
            return ResultVo.success();
        }
        for (ExpdetailSalesVO item : expdetailSalesVOS) {
            if (StringUtils.isNotBlank(item.getAgentNo())) {
                item.setAgentNoName(opsCommonService.getCustomerNameByNo(item.getAgentNo()));
            }
            // 分销商代码
            if (StringUtils.isNotBlank(item.getUserNo())) {
                item.setUserNoName(opsCommonService.getCustomerNameByNo(item.getUserNo()));
            }
            // 用户代码
            if (StringUtils.isNotBlank(item.getEndUserNo())) {
                item.setEndUserNoName(opsCommonService.getCustomerNameByNo(item.getEndUserNo()));
            }
            if (StringUtils.isNotBlank(item.getWarehouseCode())) {
                item.setWarehouseCodeName(opsCommonService.getWarehouseNameByCode(item.getWarehouseCode()));
            }
            if (StringUtils.isNotBlank(item.getExpressCompany())) {
                if (item.getExpressCompany().startsWith("SF")) {
                    item.setExpressCompanyName("顺丰");
                } else {
                    item.setExpressCompanyName(CarrierEnum.getNameByCode(item.getExpressCompany()));
                }
            }
        }
        return ResultVo.success(expdetailSalesVOS);
    }
}
