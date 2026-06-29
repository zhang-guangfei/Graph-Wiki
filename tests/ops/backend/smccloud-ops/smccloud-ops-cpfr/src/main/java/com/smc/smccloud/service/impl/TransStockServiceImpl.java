package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.TransOrder;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.order.TransOrderDto;
import com.sales.ops.dto.order.TransOrderDtoForMove;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.enums.TransFromTypeEnum;
import com.smc.smccloud.core.model.enums.TransStatusEnum;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.TransOrderMapper;
import com.smc.smccloud.model.trans.TransOrderCancelDTO;
import com.smc.smccloud.model.trans.TransOrderDO;
import com.smc.smccloud.model.trans.TransOrderRequest;
import com.smc.smccloud.model.trans.TransOrderVO;
import com.smc.smccloud.service.PreStockFeignApi;
import com.smc.smccloud.service.TransStockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;

@Service
@Slf4j
public class TransStockServiceImpl implements TransStockService {

    @Resource
    private TransOrderMapper transOrderMapper;
    @Resource
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;
    @Resource
    private HttpServletResponse response;

    @Resource
    private PreStockFeignApi preStockFeignApi;


    @Override
    public ResultVo<String> transStock(List<TransOrderVO> voList) {
        if (CollectionUtils.isEmpty(voList)) {
            return ResultVo.failure("调库项不能为空");
        }
        //获取登录用户
        UserDto user = new UserDto();
        try {
            LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
            user.setUserName(userDTO.getUserNo());
        } catch (Exception e) {
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            user.setUserName("BinDB");
        }
        user.setIp(IpUtil.getIpAddress());
        //转换vo
        List<TransOrder> list = BeanCopyUtil.copyList(voList, TransOrder.class);

        //构造参数
        TransOrderDto dto = new TransOrderDto();
        dto.setTransOrderList(list);
        dto.setUserDto(user);
        //调用接口
        try {
            log.info("发起调拨申请 {} data = {}", voList.get(0).getFromNo(), JSON.toJSONString(dto));
            long startTime = System.currentTimeMillis();
            CommonResult<String> transResult = opsWmDispatchForOrderFeignApi.createTransOrder(dto);
            log.info("调拨申请耗时: {}毫秒, 响应: {}", System.currentTimeMillis() - startTime, JSON.toJSONString(transResult));
            return ResultVo.success(transResult.getData(), "调拨成功");
        } catch (Exception e) {
            log.error("调拨失败: {}", e.getMessage(), e);
            throw new BusinessException("调拨失败: " + e.getMessage(), e);
        }
    }

    /**
     * 此接口为批量事务接口，批量提交事务，如果其中一个事务失败，则全部回滚
     * @param voList
     * @return
     */
    @Override
    public ResultVo<String> transStockAll(List<TransOrderVO> voList) {
        if (CollectionUtils.isEmpty(voList)) {
            return ResultVo.failure("调库项不能为空");
        }
        //获取登录用户
        UserDto user = new UserDto();
        try {
            LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
            user.setUserName(userDTO.getUserNo());
        } catch (Exception e) {
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            user.setUserName("BinDB");
        }
        user.setIp(IpUtil.getIpAddress());
        //转换vo
        List<TransOrder> list = BeanCopyUtil.copyList(voList, TransOrder.class);

        //构造参数
        TransOrderDto dto = new TransOrderDto();
        dto.setTransOrderList(list);
        dto.setUserDto(user);
        //调用接口
        try {
            log.info("发起调拨申请 {} data = {}", voList.get(0).getFromNo(), JSON.toJSONString(dto));
            long startTime = System.currentTimeMillis();
            CommonResult<String> transResult = opsWmDispatchForOrderFeignApi.createTransOrderBatch(dto);
            log.info("调拨申请耗时: {}毫秒, 响应: {}", System.currentTimeMillis() - startTime, JSON.toJSONString(transResult));
            return ResultVo.success(transResult.getData(), "调拨成功");
        } catch (Exception e) {
            log.error("调拨失败: {}", e.getMessage(), e);
            throw new BusinessException("调拨失败: " + e.getMessage(), e);
        }
    }

    @Override
    public ResultVo<String> createTransOrderForMove(List<TransOrderDtoForMove> voList) {
        if (CollectionUtils.isEmpty(voList)) {
            return ResultVo.failure("调库项不能为空");
        }

        Map<String, String> map = new HashMap<>();
        for (TransOrderDtoForMove move : voList) {
            try {
                log.info("调用预占在途接口：参数 -->{}",JSON.toJSONString(move));
                CommonResult<String> transResult = opsWmDispatchForOrderFeignApi.createTransOrderForMove(move);
                log.info("调用预占在途接口：返回 -->{}",JSON.toJSONString(transResult));
                String msg = "预占在途成功";
                if (!transResult.isSuccess()) {
                    msg = transResult.getMessage();
                }
                map.put(String.join("-", move.getTransNo(), move.getItemNo().toString()), msg);
            } catch (Exception e) {
                log.error("调拨失败: {}", e.getMessage(), e);
                map.put(String.join("-", move.getTransNo(), move.getItemNo().toString()), "调拨失败:调用接口失败.");
            }
        }
        return ResultVo.success(JSON.toJSONString(map));
    }

    @Override
    public ResultVo<PageInfo<TransOrderVO>> findTransOrder(TransOrderRequest request) {

        LambdaQueryWrapper<TransOrderDO> queryWrapper = this.getQueryWrapper(request);

        PageInfo<TransOrderVO> pageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize())
                .doSelectPageInfo(() -> transOrderMapper.selectList(queryWrapper));

        return ResultVo.success(pageInfo);
    }

    //    <!--add by WuWeiDong 20230703  bug 11267  批量取消 -->
    @Override
    public ResultVo<String> cancelTransOrder(TransOrderCancelDTO dto) {
        LoginUserDTO loginAuthDto = SMCApp.getLoginAuthDto();
        LambdaQueryWrapper<TransOrderDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(TransOrderDO::getId, TransOrderDO::getTransNo, TransOrderDO::getItemNo, TransOrderDO::getStatus, TransOrderDO::getTransType,
                TransOrderDO::getFromId, TransOrderDO::getFromType);
        if (CollectionUtils.isNotEmpty(dto.getIds())) {
            queryWrapper.in(TransOrderDO::getId, dto.getIds());
        } else {
            queryWrapper.eq(TransOrderDO::getTransNo, dto.getTransNo())
                    .eq(TransOrderDO::getItemNo, dto.getItemNo())
                    .eq(TransOrderDO::getTransType, dto.getTransType());
        }
        List<TransOrderDO> transOrderList = transOrderMapper.selectList(queryWrapper);
        StringBuilder errorMessage = new StringBuilder();
        int errorCount = 0;
        for (TransOrderDO transOrderInfo : transOrderList) {

            if (transOrderInfo.getStatus().compareTo(6) == 0 || transOrderInfo.getStatus().compareTo(9) == 0) {
                // return ResultVo.failure("调库已完成，不可取消");
                errorMessage.append(transOrderInfo.getItemNo()).append("项").append("调库已完成，不可取消。").append("</br>");
                errorCount++;

            } else {
                CancelForOrderDto orderDto = new CancelForOrderDto();
                orderDto.setOrderId(transOrderInfo.getTransNo());
                orderDto.setOrderItem(String.valueOf(transOrderInfo.getItemNo()));
                orderDto.setReason(dto.getReason());
                if (transOrderInfo.getTransType().compareTo(1) == 0) {
                    orderDto.setOrderType("TKCK");
                }
                if (transOrderInfo.getTransType().compareTo(2) == 0) {
                    orderDto.setOrderType("ZHCK");
                }

                UserDto userDto = new UserDto();
                userDto.setUserName(loginAuthDto.getUserName());
                userDto.setIp(IpUtil.getIpAddress());
                orderDto.setUserDto(userDto);
                CommonResult<String> commonResult = opsWmDispatchForOrderFeignApi.cancelTKCKForOrder(orderDto);
                if (!commonResult.isSuccess()) {
                    log.error("调用wm-service取消调拨失败: params = {}, 响应 {}", JSON.toJSONString(orderDto), JSON.toJSONString(commonResult));
                    errorMessage.append(transOrderInfo.getItemNo()).append("项").append("调用wm-service取消调拨失败：").append(commonResult.getMessage()).append("</br>");
                    errorCount++;
                } else {
                    //    <!--add by WuWeiDong 20231127  bug 12563 取消预约BIN生产在途，调库， -->
                    //先行在库(3),委托在库(4) 取消退先行在库的数量，更新状态。
                    List<Integer> fromTypes = Arrays.asList(3, 4);
                    ResultVo<String> resultVo = ResultVo.success();
                    if (Objects.nonNull(transOrderInfo.getFromId()) && fromTypes.contains(transOrderInfo.getFromType())) {
                        String orderNo = String.join("-", transOrderInfo.getTransNo(), transOrderInfo.getItemNo().toString());

                        resultVo = preStockFeignApi.purchaseOrderCancelHandle(orderNo);
                        if (!resultVo.isSuccess()) {
                            if (resultVo.getMessage().contains("处理项不存在")) {
                                resultVo = ResultVo.success();
                            } else {
                                errorMessage.append(transOrderInfo.getItemNo()).append("项").append("调用purchaseOrderCancelHandle取消调拨失败：").append(resultVo.getMessage()).append("</br>");
                                errorCount++;
                            }
                        }

                    }
                    if (resultVo.isSuccess()) {
                        TransOrderDO orderDO = new TransOrderDO();
                        LambdaUpdateWrapper<TransOrderDO> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper.eq(TransOrderDO::getId, transOrderInfo.getId())
                                .set(TransOrderDO::getStatus, 9)
                                .set(TransOrderDO::getUpdateTime, DateUtil.getNow())
                                .set(TransOrderDO::getUpdateUser, loginAuthDto.getUserNo());
                        transOrderMapper.update(null, updateWrapper);
                    }
                }
            }
        }
        if (errorCount > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("操作完成，有").append(errorCount).append("/").append(transOrderList.size()).append("处理错误。</br>")
                    .append(errorMessage);
            return ResultVo.failure(stringBuilder.toString());
        } else {
            return ResultVo.success("操作完成。");
        }

    }

    @Override
    public void exportTransData(TransOrderRequest request) {
        try {
            LambdaQueryWrapper<TransOrderDO> queryWrapper = this.getQueryWrapper(request);

            String path = "templates/transOrder.xlsx";
            InputStream inputStream = FileUtil.getTemplate(path);
            ExcelUtil excel = new ExcelUtil(inputStream);
            excel.openSheet(0);
            request.setPageNum(1);
            request.setPageSize(2000);
            int row = 2;
            int cel;

            while (true) {
                PageHelper.startPage(request.getPageNum(), request.getPageSize());
                List<TransOrderDO> doList = transOrderMapper.selectList(queryWrapper);
                PageInfo<TransOrderDO>  pageInfo = PageInfo.of(doList);
                if (pageInfo.getTotal() > 150000) {
                    throw new BusinessException("数据超过15W，请缩小日期范围。 ");
                }
                for (TransOrderDO orderDO : pageInfo.getList()) {
                    cel = 0;
                    excel.setCellValue(row, cel++, orderDO.getTransNo());
                    excel.setCellValue(row, cel++, orderDO.getInvoiceNo());
                    excel.setCellValue(row, cel++, orderDO.getItemNo());
                    if (orderDO.getTransType().compareTo(1) == 0) {
                        excel.setCellValue(row, cel++, "调库");
                    } else if (orderDO.getTransType().compareTo(2) == 0) {
                        excel.setCellValue(row, cel++, "组换");
                    } else if (orderDO.getTransType().compareTo(3) == 0) {
                        excel.setCellValue(row, cel++, "采购");
                    }
//            excel.setCellValue(row, cel++, orderDO.getTransType());
                    excel.setCellValue(row, cel++, orderDO.getModelNo());
                    excel.setCellValue(row, cel++, orderDO.getQuantity());
                    excel.setCellValue(row, cel++, orderDO.getShipQty());
                    excel.setCellValue(row, cel++, orderDO.getShipDate());
                    excel.setCellValue(row, cel++, TransStatusEnum.getName(orderDO.getStatus()));
                    excel.setCellValue(row, cel++, orderDO.getFromNo());
                    excel.setCellValue(row, cel++, TransFromTypeEnum.getName(orderDO.getFromType()));
                    excel.setCellValue(row, cel++, orderDO.getFromWarehouseCode());
                    excel.setCellValue(row, cel++, orderDO.getFromInventoryTypeCode());
                    excel.setCellValue(row, cel++, orderDO.getFromPpl());
                    excel.setCellValue(row, cel++, orderDO.getFromProjectCode());
                    excel.setCellValue(row, cel++, orderDO.getFromGroupCustomerNo());
                    excel.setCellValue(row, cel++, orderDO.getFromSalesInfoNo());
                    excel.setCellValue(row, cel++, orderDO.getFromCustomerNo());
                    excel.setCellValue(row, cel++, orderDO.getToWarehouseCode());
                    excel.setCellValue(row, cel++, orderDO.getToInventoryTypeCode());
                    excel.setCellValue(row, cel++, orderDO.getToPpl());
                    excel.setCellValue(row, cel++, orderDO.getToProjectCode());
                    excel.setCellValue(row, cel++, orderDO.getToGroupCustomerNo());
                    excel.setCellValue(row, cel++, orderDO.getToSalesInfoNo());
                    excel.setCellValue(row, cel++, orderDO.getToCustomerNo());
                    excel.setCellValue(row, cel++, orderDO.getCreateTime());
                    excel.setCellValue(row, cel++, orderDO.getFinishTime());
                    excel.setCellValue(row, cel, orderDO.getCreateUser());
                    row++;
                }

                excel.getSxssfSheet().flushRows();
                if (pageInfo.isIsLastPage()) {
                    break;
                }
                request.setPageNum(request.getPageNum() + 1);
            }
            excel.writeToResponse(response, "transOrder.xlsx");
        } catch (Exception e) {
            log.error("导出失败: params = {}, {}", request, e.getMessage(), e);
            throw new BusinessException("导出失败：" + e.getMessage());
        }
    }

    private LambdaQueryWrapper<TransOrderDO> getQueryWrapper(TransOrderRequest request) {
        LambdaQueryWrapper<TransOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getTransType()), TransOrderDO::getTransType, request.getTransType())
                .eq(PublicUtil.isNotEmpty(request.getModelNo()), TransOrderDO::getModelNo, request.getModelNo())
                .eq(PublicUtil.isNotEmpty(request.getFromNo()), TransOrderDO::getFromNo, request.getFromNo())
                .eq(PublicUtil.isNotEmpty(request.getFromType()), TransOrderDO::getFromType, request.getFromType())
                .eq(PublicUtil.isNotEmpty(request.getStatus()), TransOrderDO::getStatus, request.getStatus());

        if (PublicUtil.isNotEmpty(request.getTransNo()) && request.getTransNo().contains("-")) {
            String[] transSplit = request.getTransNo().split("-");
            queryWrapper.eq(TransOrderDO::getTransNo, transSplit[0]);
            queryWrapper.eq(TransOrderDO::getItemNo, transSplit[1]);
        } else {
            queryWrapper.eq(PublicUtil.isNotEmpty(request.getTransNo()), TransOrderDO::getTransNo, request.getTransNo());
        }

        if (PublicUtil.isNotEmpty(request.getWarhouseType())) {
            if (request.getWarhouseType() == 1) {
                queryWrapper.eq(PublicUtil.isNotEmpty(request.getWarehouseCode()), TransOrderDO::getFromWarehouseCode, request.getWarehouseCode());
            } else if (request.getWarhouseType() == 2) {
                queryWrapper.eq(PublicUtil.isNotEmpty(request.getWarehouseCode()), TransOrderDO::getToWarehouseCode, request.getWarehouseCode());
            }
        }
        if (PublicUtil.isNotEmpty(request.getDateType()) && PublicUtil.isNotEmpty(request.getBeginDate())) {
            if (request.getDateType() == 1) {
                queryWrapper.between(TransOrderDO::getCreateTime, request.getBeginDate(), request.getEndDate());
            } else if (request.getDateType() == 2) {
                queryWrapper.between(TransOrderDO::getFinishTime, request.getBeginDate(), request.getEndDate());
            } else {
                queryWrapper.between(TransOrderDO::getShipDate, request.getBeginDate(), request.getEndDate());
            }
        }
        queryWrapper.orderByAsc(TransOrderDO::getId);
        return queryWrapper;
    }

}
