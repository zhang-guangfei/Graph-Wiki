package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.purchase.PurchaseDeleteDto;
import com.sales.ops.dto.purchase.PurchaseModifyApplyInfoDto;
import com.sales.ops.dto.purchase.RequestCancelDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.feign.purchase.PurchaseBatchEditFeignApi;
import com.sales.ops.feign.purchase.PurchaseDeleteValidateFeignApi;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.OpsOrderResultBean;
import com.smc.smccloud.core.model.enums.OpsCommonoSalesApplyTypeEnum;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.ExcelHelper;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.purchase.PurchaseModifyMapper;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.enums.PurchaseModifyCommonEnum;
import com.smc.smccloud.model.enums.PurchaseModifySourceEnum;
import com.smc.smccloud.model.enums.PurchaseModifyStatusEnum;
import com.smc.smccloud.model.enums.PurchaseModifyTypeEnum;
import com.smc.smccloud.model.ordermodify.PurchaseModifyVO;
import com.smc.smccloud.model.purchase.PurchaseModifyApproveVo;
import com.smc.smccloud.model.purchase.PurchaseModifyDO;
import com.smc.smccloud.model.purchase.PurchaseModifyRequest;
import com.smc.smccloud.model.salestask.OpsSalesCommonParamVO;
import com.smc.smccloud.model.salestask.UpTaskInfoVO;
import com.smc.smccloud.model.stock.DealReturnOpsParam;
import com.smc.smccloud.model.stock.DealReturnOpsParamVO;
import com.smc.smccloud.service.CommonService;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import com.smc.smccloud.service.OrderModifyServiceFeignApi;
import com.smc.smccloud.service.PurchaseModifyApplyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author smc
 * @since 2023-10-24
 */
@Service
@Slf4j
public class PurchaseModifyApplyServiceImpl extends ServiceImpl<PurchaseModifyMapper, PurchaseModifyDO> implements PurchaseModifyApplyService {

    @Resource
    private PurchaseModifyMapper purchaseModifyMapper;

    @Resource
    private CommonService commonService;

    @Resource
    private PurchaseBatchEditFeignApi purchaseBatchEditFeignApi;

    @Resource
    private PurchaseDeleteValidateFeignApi purchaseDeleteValidateFeignApi;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Resource
    private OrderModifyServiceFeignApi orderModifyServiceFeignApi;

    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public ResultVo<PageInfo<PurchaseModifyDO>> findAll(PurchaseModifyRequest purchaseModifyRequest, Page page) {
        LambdaQueryWrapper<PurchaseModifyDO> queryWrapper = new LambdaQueryWrapper<>();
        // 申请号
        queryWrapper.eq(PublicUtil.isNotEmpty(purchaseModifyRequest.getApplyNo()), PurchaseModifyDO::getApplyNo, purchaseModifyRequest.getApplyNo());
        // 订单号
        queryWrapper.like(PublicUtil.isNotEmpty(purchaseModifyRequest.getOrderFno()), PurchaseModifyDO::getOrderFno, purchaseModifyRequest.getOrderFno());
        // 变更类别
        queryWrapper.eq(PublicUtil.isNotEmpty(purchaseModifyRequest.getModifyType()), PurchaseModifyDO::getModifyType, purchaseModifyRequest.getModifyType());
        // 状态
        queryWrapper.eq(PublicUtil.isNotEmpty(purchaseModifyRequest.getStatus()), PurchaseModifyDO::getStatus, purchaseModifyRequest.getStatus());
        // 部门代码
        queryWrapper.eq(PublicUtil.isNotEmpty(purchaseModifyRequest.getDeptNo()), PurchaseModifyDO::getDeptNo, purchaseModifyRequest.getDeptNo());
        // 运输方式
        queryWrapper.eq(PublicUtil.isNotEmpty(purchaseModifyRequest.getTransType()), PurchaseModifyDO::getTransType, purchaseModifyRequest.getTransType());
        // 供应商
        queryWrapper.eq(PublicUtil.isNotEmpty(purchaseModifyRequest.getSupplierId()), PurchaseModifyDO::getSupplierId, purchaseModifyRequest.getSupplierId());
        // 型号
        queryWrapper.like(PublicUtil.isNotEmpty(purchaseModifyRequest.getModelno()), PurchaseModifyDO::getModelno, purchaseModifyRequest.getModelno());
        // 日期筛选
        // 判断开始日期和结束日期 结束日期+1
        if (PublicUtil.isNotEmpty(purchaseModifyRequest.getApplyTimeStart())) {
            queryWrapper.ge(PublicUtil.isNotEmpty(purchaseModifyRequest.getApplyTimeStart()), PurchaseModifyDO::getApplyTime, purchaseModifyRequest.getApplyTimeStart()+" 00:00:00");
            queryWrapper.le(PublicUtil.isNotEmpty(purchaseModifyRequest.getApplyTimeEnd()), PurchaseModifyDO::getApplyTime, purchaseModifyRequest.getApplyTimeEnd() + " 23:59:59");
        }
        if (PublicUtil.isNotEmpty(purchaseModifyRequest.getHandleTimeStart())) {
            queryWrapper.ge(PublicUtil.isNotEmpty(purchaseModifyRequest.getHandleTimeStart()), PurchaseModifyDO::getHandleTime, purchaseModifyRequest.getHandleTimeStart()+" 00:00:00");
            queryWrapper.le(PublicUtil.isNotEmpty(purchaseModifyRequest.getHandleTimeEnd()), PurchaseModifyDO::getHandleTime, purchaseModifyRequest.getHandleTimeEnd()+ " 23:59:59");
        }
        // todo,需要去掉去除多余空格
        if (!CollectionUtils.isEmpty(purchaseModifyRequest.getOrderNoList())) {
            queryWrapper.in(PublicUtil.isNotEmpty(purchaseModifyRequest.getOrderNoList()), PurchaseModifyDO::getOrderFno, purchaseModifyRequest.getOrderNoList());
        }
        // bug12360,2024-02-26 部门代码查询，按照list
        if (!CollectionUtils.isEmpty(purchaseModifyRequest.getDeptNoList())) {
            queryWrapper.in(PublicUtil.isNotEmpty(purchaseModifyRequest.getDeptNoList()), PurchaseModifyDO::getDeptNo, purchaseModifyRequest.getDeptNoList());
        }
        PageInfo<PurchaseModifyDO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> purchaseModifyMapper.selectList(queryWrapper));

        if (!CollectionUtils.isEmpty(pageInfo.getList())) {
            Map<String, String> nameMap = new HashMap<>(pageInfo.getList().size());
            List<PurchaseModifyApplyInfoDto> purchaseModifyApplyInfoDtos = new ArrayList<>();
            CommonResult<List<PurchaseModifyApplyInfoDto>> purchaseResult = purchaseBatchEditFeignApi.getRequestInfo(pageInfo.getList().stream().map(p -> p.getOrderFno()).collect(Collectors.toList()));
            if (purchaseResult.isSuccess()) {
                purchaseModifyApplyInfoDtos = purchaseResult.getData();
            }
            PurchaseModifyApplyInfoDto opsPurchaseorder;
            for (PurchaseModifyDO item : pageInfo.getList()) {
//                // 部门名称
//                if (!nameMap.containsKey(item.getDeptNo())) {
//                    nameMap.put(item.getDeptNo(), commonService.getDeptNameByNo(item.getDeptNo()));
//                }
//                item.setDeptName(item.getDeptNo() + "[" + nameMap.get(item.getDeptNo()) + "]");

                // 处理人
                if (StringUtils.isNotBlank(item.getHandler())) {
                    item.setHandler(commonService.getEmpSaleNameByNo(item.getHandler()) + "[" + item.getHandler() + "]");
                }
                // 申请人
                if (StringUtils.isNotBlank(item.getApplyPersonNo())) {
                    item.setApplyPersonNo(commonService.getEmpSaleNameByNo(item.getApplyPersonNo()) + "[" + item.getApplyPersonNo() + "]");
                }
                // 前端显示运输方式转换
//                if(OrderModifyTypeEnum.bgysfs.getCode().equals(item.getModifyType())) {
//                    item.setApplyContent(OPSTransTypeEnum.getNameByCode(item.getApplyContent()));
//                }
                // bug 12360,前端显示 采购单状态等信息，采购实时查询
                List<PurchaseModifyApplyInfoDto> purchaseList =  purchaseModifyApplyInfoDtos.stream().filter(s -> s.getOrderFno().equals(item.getOrderFno())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(purchaseList)) {
                    opsPurchaseorder = purchaseList.get(0);
                    item.setPurchaseStatecode(opsPurchaseorder.getStatecode());
                    item.setModelno(opsPurchaseorder.getModelno());
                    item.setQuantity(opsPurchaseorder.getQuantity());
                    item.setSupplierId(opsPurchaseorder.getSupplierid());
                    item.setTransType(opsPurchaseorder.getTranstype());
                    item.setHopeExportDate(opsPurchaseorder.getHopeexportdate());
                }
            }
        }
        PageInfo<PurchaseModifyDO> pageInfo1 = BeanCopyUtil.pageDto2Vo(pageInfo, PurchaseModifyDO.class);
        return ResultVo.success(pageInfo1);
    }

    /**
     * 变更运输方式功能，只返回给门户变更结果，操作由业务自行去变更
     * 变更运输方式的订单，全都来源于门户
     *
     * @param info
     * @return
     */
    @Override
    @DS("sharedb")
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> transTypeDeal(PurchaseModifyApproveVo info) {
        if (Objects.isNull(info) || CollectionUtils.isEmpty(info.getIdList())) {
            return ResultVo.failure("入参不可为空.");
        }
        // 运输方式订单，是否需要验证批次号
        if (CollectionUtils.isEmpty(info.getBatchNo())) {
            return ResultVo.failure("所选数据批次号为空");
        }
        LambdaQueryWrapper<PurchaseModifyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PublicUtil.isNotEmpty(info.getIdList()), PurchaseModifyDO::getId, info.getIdList());
        List<PurchaseModifyDO> purchaseModifyDOS = purchaseModifyMapper.selectList(queryWrapper);
        PurchaseModifyDO updateDo;
        int result;
        if (!CollectionUtils.isEmpty(purchaseModifyDOS)) {
            StringBuilder errMsg = new StringBuilder();
            for (PurchaseModifyDO purchaseModifyDO : purchaseModifyDOS) {
                // 校验变更类别是否为空
                if (StringUtils.isBlank(purchaseModifyDO.getModifyType())) {
                    errMsg.append(purchaseModifyDO.getOrderFno()).append("变更类型为空;");
                    continue;
                }
                // 校验是否为未处理状态
                if (!(purchaseModifyDO.getStatus() == PurchaseModifyStatusEnum.waitHand.getCode() || purchaseModifyDO.getStatus() == PurchaseModifyStatusEnum.notHand.getCode())) {
                    errMsg.append(purchaseModifyDO.getOrderFno()).append("状态不是待处理或者暂不处理;");
                    continue;
                }
                // todo 是否需要通过批次号，给订单修改，传输事件
                // 更新状态
                updateDo = new PurchaseModifyDO();
                updateDo.setId(purchaseModifyDO.getId());
                updateDo.setStatus(PurchaseModifyStatusEnum.finish.getCode());
                updateDo.setHandler(info.getOptUserNo());
                updateDo.setHandleTime(new Date());
                updateDo.setUpdateUser(info.getOptUserNo());
                updateDo.setUpdateTime(new Date());
                // 追加备注
                updateDo.setHandleResult((StringUtils.isBlank(info.getText()) ? PurchaseModifyStatusEnum.finish.getCodeName() : PurchaseModifyStatusEnum.finish.getCodeName() + "," + info.getText()));
                // 更新状态
                result = purchaseModifyMapper.updateById(updateDo);
                // 变更运输方式的请求，直接回传事件给 order_task表
                if (result > 0 && StringUtils.isNotBlank(purchaseModifyDO.getBatchNo())) {
                    // send msg to order_task
                    sendMsgToOrderTask(purchaseModifyDO, info.getOptUserNo(), false, null, info.getText());
                }
            }
            if (StringUtils.isNotBlank(errMsg.toString())) {
                return ResultVo.failure("处理失败的订单信息" + errMsg.toString());
            }
            return ResultVo.success("处理成功");
        } else {
            return ResultVo.failure("根据勾选数据，未找到对应申请，请刷新重试");
        }
    }

    @Override
    @DS("sharedb")
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> suppilyDateDeal(PurchaseModifyApproveVo info) {
        if (Objects.isNull(info) || CollectionUtils.isEmpty(info.getIdList())) {
            return ResultVo.failure("入参不可为空.");
        }
        LambdaQueryWrapper<PurchaseModifyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PublicUtil.isNotEmpty(info.getIdList()), PurchaseModifyDO::getId, info.getIdList());
        List<PurchaseModifyDO> purchaseModifyDOS = purchaseModifyMapper.selectList(queryWrapper);
        PurchaseModifyDO updateDo;
        PurchaseModifyRequest request;
        List<OpsPurchaseorder> verifyList;
        List<OpsRequestpurchase> requestpurchaseList;
        String handleMsg;
        Boolean isChange ;
        int result = 0;
        if (!CollectionUtils.isEmpty(purchaseModifyDOS)) {
            StringBuilder errMsg = new StringBuilder();
            for (PurchaseModifyDO purchaseModifyDO : purchaseModifyDOS) {
                // 校验变更类别是否为空
                if (StringUtils.isBlank(purchaseModifyDO.getModifyType())) {
                    errMsg.append(purchaseModifyDO.getOrderFno()).append("-变更类型为空;");
                    continue;
                }
                // 校验是否为未处理状态
                if (!(Objects.equals(purchaseModifyDO.getStatus(), PurchaseModifyStatusEnum.waitHand.getCode()) || purchaseModifyDO.getStatus().equals(PurchaseModifyStatusEnum.notHand.getCode()))) {
                    errMsg.append(purchaseModifyDO.getOrderFno()).append("-状态不是待处理或者暂不处理;");
                    continue;
                }
                updateDo = new PurchaseModifyDO();
                updateDo.setId(purchaseModifyDO.getId());
                updateDo.setHandler(info.getOptUserNo());
                updateDo.setHandleTime(new Date());
                updateDo.setUpdateUser(info.getOptUserNo());
                updateDo.setUpdateTime(new Date());
                // 校验采购单状态
                request = new PurchaseModifyRequest();
                request.setModifyType(purchaseModifyDO.getModifyType());
                request.setOrderNo(purchaseModifyDO.getOrderFno());
                // 先查询采购表是否存在
                verifyList = purchaseModifyMapper.getPurchaseOrder(request);
                if (CollectionUtils.isEmpty(verifyList) && verifyList.size() == 0) {
                    // 校验是否存在请购单，以及请购单的状态
                    requestpurchaseList = purchaseModifyMapper.getRequestPurchase(request);
                    if (!CollectionUtils.isEmpty(requestpurchaseList)) {
                        // 校验请购单状态，是否为请购中，状态变为暂不处理
                        updateDo.setStatus(PurchaseModifyStatusEnum.notHand.getCode());
                        updateDo.setHandleResult("该单正在请购处理中，请到请购查询界面编辑修改");
                        errMsg.append(purchaseModifyDO.getOrderFno()).append("-该单正在请购处理中，请到请购查询界面编辑修改;");
                        purchaseModifyMapper.updateById(updateDo);
                        continue;
                    }
                    updateDo.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                    updateDo.setHandleResult("未找到相关采购单信息;");
                    errMsg.append(purchaseModifyDO.getOrderFno()).append("-未找到相关采购单信息;");
                    // 更新状态
                    result = purchaseModifyMapper.updateById(updateDo);
                    // 不能变更时，直接回传事件给 order_task表
                    if (result > 0 && StringUtils.isNotBlank(purchaseModifyDO.getBatchNo()) && Objects.equals(updateDo.getStatus(), PurchaseModifyStatusEnum.bnbg.getCode())) {
                        purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                        // send msg to order_task
                        sendMsgToOrderTask(purchaseModifyDO, info.getOptUserNo(), true, updateDo.getHandleResult(), null);
                    }
                    continue;
                }
                if (!(verifyList.get(0).getStatecode().equals(PurchaseOrderStatusEnum.YIFASONG) || verifyList.get(0).getStatecode().equals(PurchaseOrderStatusEnum.YIJIEDAN))) {
                    updateDo.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                    updateDo.setHandleResult("采购状态不可变更;");
                    // 采购状态不可变更
                    errMsg.append(purchaseModifyDO.getOrderFno()).append("-采购状态不是已发送或者已接单，不可转订;");
                    // 更新状态
                    result = purchaseModifyMapper.updateById(updateDo);
                    // 不能变更时，直接回传事件给 order_task表
                    if (result > 0 && StringUtils.isNotBlank(purchaseModifyDO.getBatchNo()) && Objects.equals(updateDo.getStatus(), PurchaseModifyStatusEnum.bnbg.getCode())) {
                        purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                        purchaseModifyDO.setHandleResult(updateDo.getHandleResult());
                        // send msg to order_task
                        sendMsgToOrderTask(purchaseModifyDO, info.getOptUserNo(), true, updateDo.getHandleResult(), null);
                    }
                    continue;
                }
                // 2023-12-07 校验，当采购转定内容，与原始订单内容一致时，提示无需变更，状态变更为完成
                isChange = false;
                switch (purchaseModifyDO.getModifyType()) {
                    case "A":
                        if (purchaseModifyDO.getApplyContent().equals(verifyList.get(0).getSupplierid())) {
                            isChange = true;
                        }
                        break;
                    case "D":
                        try {
                            if (ft.parse(purchaseModifyDO.getApplyContent()).equals(verifyList.get(0).getHopeexportdate())) {
                                isChange = true;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    // todo 后期可能会接入运输方式变更操作
                    default:
                }
                if (isChange) {
                    // 更新状态为处理中
                    updateDo.setStatus(PurchaseModifyStatusEnum.finish.getCode());
                    // 追加备注
                    updateDo.setHandleResult("当采购转定内容，与原始订单内容一致,无需再处理");
                    // 更新状态
                    result = purchaseModifyMapper.updateById(updateDo);
                    errMsg.append(purchaseModifyDO.getOrderFno()).append("-当采购转定内容，与原始订单内容一致,无需再处理,当前申请单已经变更为完成;");
                    // 变更运输方式的请求，直接回传事件给 order_task表
                    if (result > 0 && StringUtils.isNotBlank(purchaseModifyDO.getBatchNo())) {
                        purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.finish.getCode());
                        purchaseModifyDO.setHandleResult(updateDo.getHandleResult());
                        // send msg to order_task
                        sendMsgToOrderTask(purchaseModifyDO, info.getOptUserNo(), false, null, info.getText());
                    }
                    continue;
                }
                // 更新状态为处理中
                updateDo.setStatus(PurchaseModifyStatusEnum.handing.getCode());
                // 追加备注
                updateDo.setHandleResult((StringUtils.isBlank(info.getText()) ? PurchaseModifyStatusEnum.finish.getCodeName() : PurchaseModifyStatusEnum.finish.getCodeName() + "," + info.getText()));
                // 更新状态
                purchaseModifyMapper.updateById(updateDo);
            }
            if (StringUtils.isNotBlank(errMsg.toString())) {
                return ResultVo.failure("处理失败的订单信息" + errMsg.toString());
            }
            return ResultVo.success("处理成功");
        } else {
            return ResultVo.failure("根据勾选数据，未找到对应申请，请刷新重试");
        }
    }

    @Override
    @DS("sharedb")
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> deleteDeal(PurchaseModifyApproveVo info) {
        // 校验入参是否为空
        if (Objects.isNull(info) || CollectionUtils.isEmpty(info.getIdList())) {
            return ResultVo.failure("入参不可为空.");
        }
        LambdaQueryWrapper<PurchaseModifyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PublicUtil.isNotEmpty(info.getIdList()), PurchaseModifyDO::getId, info.getIdList());
        List<PurchaseModifyDO> purchaseModifyDOS = purchaseModifyMapper.selectList(queryWrapper);
        PurchaseModifyDO updateDo;
        PurchaseModifyRequest request;
        List<OpsRequestpurchase> requestList;
        List<OpsPurchaseorder> purchaseList;
        int result;
        if (!CollectionUtils.isEmpty(purchaseModifyDOS)) {
            StringBuilder errMsg = new StringBuilder();
            for (PurchaseModifyDO purchaseModifyDO : purchaseModifyDOS) {
                // 校验变更类别是否为空
                if (StringUtils.isBlank(purchaseModifyDO.getModifyType())) {
                    errMsg.append(purchaseModifyDO.getOrderFno()).append("变更类型为空;");
                    continue;
                }
                // 校验是否为未处理状态
                if (!(purchaseModifyDO.getStatus() == PurchaseModifyStatusEnum.waitHand.getCode() || purchaseModifyDO.getStatus() == PurchaseModifyStatusEnum.notHand.getCode())) {
                    errMsg.append(purchaseModifyDO.getOrderFno()).append("状态不是待处理或者暂不处理;");
                    continue;
                }
                updateDo = new PurchaseModifyDO();
                updateDo.setId(purchaseModifyDO.getId());
                updateDo.setHandler(info.getOptUserNo());
                updateDo.setHandleTime(new Date());
                updateDo.setUpdateUser(info.getOptUserNo());
                updateDo.setUpdateTime(new Date());
                // 校验采购单状态
                request = new PurchaseModifyRequest();
                request.setModifyType(purchaseModifyDO.getModifyType());
                request.setOrderNo(purchaseModifyDO.getOrderFno());
                // 先查询采购表是否存在
                purchaseList = purchaseModifyMapper.getPurchaseOrder(request);
                if (!CollectionUtils.isEmpty(purchaseList)) {
                    // 校验是否为已接单状态
                    if (!purchaseList.get(0).getStatecode().equals(PurchaseOrderStatusEnum.YIJIEDAN)) {
                        if (purchaseList.get(0).getStatecode().equals(PurchaseOrderStatusEnum.YIFASONG)) {
                            updateDo.setStatus(PurchaseModifyStatusEnum.notHand.getCode());
                            updateDo.setHandleResult("请在接单后删除!");
                            errMsg.append(purchaseModifyDO.getOrderFno()).append("请在接单后删除!");
                        } else {
                            // 采购状态不可变更
                            updateDo.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                            updateDo.setHandleResult("采购单状态不可变更");
                            errMsg.append(purchaseModifyDO.getOrderFno()).append("采购状态不是已接单，不可删单;");
                        }
                        result = purchaseModifyMapper.updateById(updateDo);
                        // 不能变更时，直接回传事件给 order_task表
                        if (result > 0 && StringUtils.isNotBlank(purchaseModifyDO.getBatchNo()) && Objects.equals(updateDo.getStatus(), PurchaseModifyStatusEnum.bnbg.getCode())) {
                            purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                            // send msg to order_task
                            sendMsgToOrderTask(purchaseModifyDO, info.getOptUserNo(), true, updateDo.getHandleResult(), null);
                        }
                        continue;
                    }
                } else {
                    // 查询请购表是否存在
                    requestList = purchaseModifyMapper.getRequestPurchase(request);
                    if (CollectionUtils.isEmpty(requestList)) {
                        // 采购状态不可变更
                        errMsg.append(purchaseModifyDO.getOrderFno()).append("未找到对应的采购单数据，不可删单;");
                        updateDo.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                        updateDo.setHandleResult("当前请购状态不可变更");
                        result = purchaseModifyMapper.updateById(updateDo);
                        // 不能变更时，直接回传事件给 order_task表
                        if (result > 0 && StringUtils.isNotBlank(purchaseModifyDO.getBatchNo()) && Objects.equals(updateDo.getStatus(), PurchaseModifyStatusEnum.bnbg.getCode())) {
                            purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                            // send msg to order_task
                            sendMsgToOrderTask(purchaseModifyDO, info.getOptUserNo(), true, updateDo.getHandleResult(), null);
                        }
                        continue;
                    }
                    // 2024-01-26 修改，请购状态为 1 4 5 时，也可以进行删单操作
                    Set<String> purchaseStatus = new HashSet<String>(PurchaseModifyCommonEnum.purchaseStatusList());
                    if (!purchaseStatus.contains(requestList.get(0).getStatecode())) {
                        // 采购状态不可变更
                        errMsg.append(purchaseModifyDO.getOrderFno()).append("当前请购状态不能删单，请稍后重试;");
                        updateDo.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                        updateDo.setHandleResult("当前请购状态不可变更");
                        result = purchaseModifyMapper.updateById(updateDo);
                        // 不能变更时，直接回传事件给 order_task表
                        if (result > 0 && StringUtils.isNotBlank(purchaseModifyDO.getBatchNo()) && Objects.equals(updateDo.getStatus(), PurchaseModifyStatusEnum.bnbg.getCode())) {
                            purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                            purchaseModifyDO.setHandleResult(updateDo.getHandleResult());
                            // send msg to order_task
                            sendMsgToOrderTask(purchaseModifyDO, info.getOptUserNo(), true, updateDo.getHandleResult(), null);
                        }
                        continue;
                    }
                }
                // 更新处理状态
                updateDo.setStatus(PurchaseModifyStatusEnum.handing.getCode());
                // 追加备注
                updateDo.setHandleResult((StringUtils.isBlank(info.getText()) ? PurchaseModifyStatusEnum.finish.getCodeName() : PurchaseModifyStatusEnum.finish.getCodeName() + "," + info.getText()));
                // 更新状态
                purchaseModifyMapper.updateById(updateDo);
            }
            if (StringUtils.isNotBlank(errMsg.toString())) {
                return ResultVo.failure("处理失败的订单信息" + errMsg.toString());
            }
            return ResultVo.success("处理成功");
        } else {
            return ResultVo.failure("根据勾选数据，未找到对应申请，请刷新重试");
        }
    }

    /**
     *
     * @param info
     * @return
     * 否决和暂不处理操作
     * 否决：暂不处理和待处理可否决
     * 暂不处理：待处理 可操作暂不处理
     */
    @Override
    @DS("sharedb")
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> layasideData(PurchaseModifyApproveVo info) {
        // 校验入参是否为空
        if (Objects.isNull(info) || CollectionUtils.isEmpty(info.getIdList())) {
            return ResultVo.failure("入参不可为空.");
        }
        LambdaQueryWrapper<PurchaseModifyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PublicUtil.isNotEmpty(info.getIdList()), PurchaseModifyDO::getId, info.getIdList());
        List<PurchaseModifyDO> purchaseModifyDOS = purchaseModifyMapper.selectList(queryWrapper);
        String typeDesc = info.getStatus() == PurchaseModifyStatusEnum.notHand.getCode() ? "暂不处理," : "不能变更,";
        PurchaseModifyDO updateDo;
        int result;
        if (!CollectionUtils.isEmpty(purchaseModifyDOS)) {
            StringBuilder errMsg = new StringBuilder();
            for (PurchaseModifyDO purchaseModifyDO : purchaseModifyDOS) {
                // 校验是否为未处理状态,只有 待处理 可操作暂不处理
                if (Objects.equals(info.getStatus(), PurchaseModifyStatusEnum.notHand.getCode()) && !Objects.equals(purchaseModifyDO.getStatus(), PurchaseModifyStatusEnum.waitHand.getCode())) {
                    errMsg.append(purchaseModifyDO.getOrderFno()).append("状态不是待处理，不可变更为-暂不处理;");
                    continue;
                }
                // 否决：暂不处理和待处理可否决 2024-02-27 B91717
                if (Objects.equals(info.getStatus(), PurchaseModifyStatusEnum.bnbg.getCode()) && !(Objects.equals(purchaseModifyDO.getStatus(), PurchaseModifyStatusEnum.waitHand.getCode()) || Objects.equals(purchaseModifyDO.getStatus(), PurchaseModifyStatusEnum.notHand.getCode()))) {
                    errMsg.append(purchaseModifyDO.getOrderFno()).append("状态不是待处理或暂不处理，无法否决;");
                    continue;
                }
                updateDo = new PurchaseModifyDO();
                updateDo.setId(purchaseModifyDO.getId());
                updateDo.setStatus(info.getStatus());
                updateDo.setHandler(info.getOptUserNo());
                updateDo.setHandleTime(new Date());
                updateDo.setUpdateUser(info.getOptUserNo());
                updateDo.setUpdateTime(new Date());
                // 追加备注
                updateDo.setHandleResult((StringUtils.isBlank(info.getText()) ? typeDesc : typeDesc + info.getText()));
                // 更新状态
                result = purchaseModifyMapper.updateById(updateDo);
                if (result == 1) {
                    // 门户的订单，回传给订单task表状态，回传task状态为 不能变更 error =true
                    // bug 15557 WTSR2024000937-删单申请处理状态显示修改,采购修改中暂不处理状态回写task回调门户，状态和业务回复字段均需回写
                    // && !Objects.equals(updateDo.getStatus(), PurchaseModifyStatusEnum.notHand.getCode())
                    if (StringUtils.isNotBlank(purchaseModifyDO.getBatchNo())) {
                        purchaseModifyDO.setStatus(updateDo.getStatus());
                        purchaseModifyDO.setHandleResult(updateDo.getHandleResult());
                        // 调用通用方法
                        sendMsgToOrderTask(purchaseModifyDO, info.getOptUserNo(), true, updateDo.getHandleResult(), null);
                    }
                }
            }
            if (StringUtils.isNotBlank(errMsg.toString())) {
                return ResultVo.failure("处理失败的订单信息" + errMsg.toString());
            }
            return ResultVo.success("处理成功");
        } else {
            return ResultVo.failure("根据勾选数据，未找到对应申请，请刷新重试");
        }
    }

    /**
     * excel批量
     *
     * @param file
     * @param loginUser
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @DS("sharedb")
    public ResultVo<String> importFile(MultipartFile file, String loginUser) throws Exception {
        if (file == null) {
            return ResultVo.failure("请上传文件:");
        }
        if (StringUtils.isBlank(loginUser)) {
            return ResultVo.failure("操作人为空:");
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(filename) && !filename.matches("^.+\\.(?i)(xlsx)$")
                && !filename.matches("^.+\\.(?i)(xls)$")) {
            return ResultVo.failure("文件格式错误,请按照模板文件格式进行导入:");
        }
        ExcelHelper excel = new ExcelHelper(file.getInputStream());
        if (excel == null) {
            return ResultVo.failure("未读取到文件:");
        }
        Sheet sheet = excel.getSheet();
        Row rows;
        PurchaseModifyDO purchaseModifyDO;
        PurchaseModifyApplyInfoDto purchaseInfo;
        // 获取变更类别清单，用于转换
        Map<String, String> modifyTypeMap = getModifyTypes();
        int count = 0;
        for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
            rows = sheet.getRow(j);
            if (rows == null) {
                break;
            }
            // 完整采购单号
            String orderno = excel.getCellString(rows.getCell(0)).trim();
            if (StringUtils.isBlank(orderno)) {
                return ResultVo.failure("第" + j + "行的采购单号为空.请仔细检查数据");
            }
            // modifyType 变更类别
            String modifyType = excel.getCellString(rows.getCell(1)).trim();
            if (StringUtils.isBlank(modifyType)) {
                return ResultVo.failure("第" + j + "行的变更类别为空.请仔细检查数据");
            }
            modifyType = modifyTypeMap.get(modifyType);
            if (StringUtils.isBlank(modifyType)) {
                return ResultVo.failure("第" + j + "行的变更类别名称未找到对应 类别代码.请仔细检查数据");
            }
            // 申请变更内容
            String applyContent = excel.getCellString(rows.getCell(2)).trim();
            if (!modifyType.equals(PurchaseModifyTypeEnum.cancel.getCode()) && StringUtils.isBlank(applyContent)) {
                return ResultVo.failure("第" + j + "行的申请内容.请仔细检查数据");
            }
            // 申请理由
            String applyReason = excel.getCellString(rows.getCell(3));
            if (StringUtils.isBlank(applyReason)) {
                return ResultVo.failure("第" + j + "行的申请理由.请仔细检查数据");
            }
            // 查询采购单的相关信息
            CommonResult<List<PurchaseModifyApplyInfoDto>> result = purchaseBatchEditFeignApi.getRequestInfo(Collections.singletonList(orderno));
            if (result.isSuccess() && result.getData() != null) {
                purchaseModifyDO = new PurchaseModifyDO();
                // 获取采购相关字段信息
                purchaseInfo = result.getData().get(0);
                purchaseModifyDO.setModelno(purchaseInfo.getModelno());
                purchaseModifyDO.setQuantity(purchaseInfo.getQuantity());
                purchaseModifyDO.setDeptNo(purchaseInfo.getDeptno());
                purchaseModifyDO.setPurchaseStatecode(purchaseInfo.getStatecode());
                purchaseModifyDO.setSupplierId(purchaseInfo.getSupplierid());
                purchaseModifyDO.setNetweight(purchaseInfo.getNetweight());
                purchaseModifyDO.setTransType(purchaseInfo.getTranstype());
                purchaseModifyDO.setHopeExportDate(purchaseInfo.getHopeexportdate());
                purchaseModifyDO.setCustomerNo(purchaseInfo.getCustomerno());
                purchaseModifyDO.setUserNo(purchaseInfo.getUserno());
                purchaseModifyDO.setEndUser(purchaseInfo.getEndUser());
                purchaseModifyDO.setOrderNo(purchaseInfo.getOrderno());
                purchaseModifyDO.setItemNo(purchaseInfo.getItemno());
                purchaseModifyDO.setSplitItemNo(purchaseInfo.getSplititemno());
                // 对特殊字符进行格式化
                applyReason = replaceSpace(applyReason);
                purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.waitHand.getCode());
                purchaseModifyDO.setOrderFno(orderno);
                purchaseModifyDO.setModifyType(modifyType);
                purchaseModifyDO.setApplyContent(applyContent);
                purchaseModifyDO.setApplyReason(applyReason);
                purchaseModifyDO.setApplyPersonNo(loginUser);
                purchaseModifyDO.setApplyTime(new Date());
                purchaseModifyDO.setCreateUser(loginUser);
                purchaseModifyDO.setCreateTime(new Date());
                // 生成采购修改申请号
                purchaseModifyDO.setApplyNo(generateApplyNo(modifyType));
                // bug12771 增加来源 source_type字段
                purchaseModifyDO.setSourceType(PurchaseModifySourceEnum.cgxg.getCode());
                //bug14745 订单修改和采购修改的增加手配号赋值
                purchaseModifyDO.setSupplierOrderno(purchaseInfo.getReplyorderno());
                // 插入采购变更表
                purchaseModifyMapper.insert(purchaseModifyDO);
                count++;
            } else {
                throw Exceptions.OpsException("订单号：" + orderno + result.getMessage());
            }
        }
        return ResultVo.success("批量导入完毕.共计" + count + "条");
    }

    /**
     * 提供写入采购变更记录表的接口，用于记录所有采购模块的删单 以及 转定操作
     */
    public void insertBatch(PurchaseModifyApplyInfoDto purchaseInfo) {
        PurchaseModifyDO purchaseModifyDO = new PurchaseModifyDO();
        // 获取采购相关字段信息
        purchaseModifyDO.setModelno(purchaseInfo.getModelno());
        purchaseModifyDO.setQuantity(purchaseInfo.getQuantity());
        purchaseModifyDO.setDeptNo(purchaseInfo.getDeptno());
        purchaseModifyDO.setPurchaseStatecode(purchaseInfo.getStatecode());
        // 原始供应商
        purchaseModifyDO.setSupplierId(purchaseInfo.getSupplierid());
        purchaseModifyDO.setNetweight(purchaseInfo.getNetweight());
        purchaseModifyDO.setTransType(purchaseInfo.getTranstype());
        // 原始出库日
        purchaseModifyDO.setHopeExportDate(purchaseInfo.getHopeexportdate());
        purchaseModifyDO.setCustomerNo(purchaseInfo.getCustomerno());
        purchaseModifyDO.setUserNo(purchaseInfo.getUserno());
        purchaseModifyDO.setEndUser(purchaseInfo.getEndUser());
        purchaseModifyDO.setOrderNo(purchaseInfo.getOrderno());
        purchaseModifyDO.setItemNo(purchaseInfo.getItemno());
        purchaseModifyDO.setSplitItemNo(purchaseInfo.getSplititemno());
        // 对特殊字符进行格式化
        purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.finish.getCode());

        String orderFno = purchaseInfo.getOrderno() + "-" + purchaseInfo.getItemno();
        if (purchaseInfo.getSplititemno() != null) {
            orderFno = orderFno + "-" + purchaseInfo.getSplititemno();
        }
        purchaseModifyDO.setOrderFno(orderFno);
        purchaseModifyDO.setModifyType("变更类别");
        purchaseModifyDO.setApplyContent("变更内容");
        purchaseModifyDO.setApplyReason("申请理由");
        purchaseModifyDO.setApplyPersonNo("变更人");
        purchaseModifyDO.setApplyTime(null);
        purchaseModifyDO.setCreateUser("变更人");
        purchaseModifyDO.setCreateTime(new Date());
        // bug12771 增加来源 source_type字段
        purchaseModifyDO.setSourceType(PurchaseModifySourceEnum.cgxg.getCode());
        // 插入采购变更表
        purchaseModifyMapper.insert(purchaseModifyDO);
    }

    /**
     * 自动作业处理
     *
     * @throws Exception
     */
    @Override
    public ResultVo<String> handlePurchaseModify() throws Exception {
        // 查询 处理中的清单，调用采购 转定/删单接口
        LambdaQueryWrapper<PurchaseModifyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PurchaseModifyDO::getStatus, PurchaseModifyStatusEnum.handing.getCode());
        // 待处理集合
        List<PurchaseModifyDO> handleList = purchaseModifyMapper.selectList(queryWrapper);
        List<PurchaseModifyDO> toDoList;
        List<OpsPurchaseorder> purchaseList;
        List<OpsRequestpurchase> requestList;
        PurchaseModifyRequest request;
        OpsPurchaseorder verifyPurchase;
        // 采购删单请求实体
        RequestCancelDto requestCancelDto;
        CommonResult commonResult;
        PurchaseDeleteDto purchaseDeleteDto;
        int successNum = 0;
        int faileNum = 0;
        if (!CollectionUtils.isEmpty(handleList)) {
            List<String> modifyTypeList = handleList.stream().map(PurchaseModifyDO::getModifyType).distinct().collect(Collectors.toList());
            for (String modifytype : modifyTypeList) {
                // 按类别删选
                toDoList = handleList.stream().filter(modifyDo -> modifyDo.getModifyType().equals(modifytype)).collect(Collectors.toList());
                // 区分 是删单还是 转定,调用不同的采购接口
                if (modifytype.equals(PurchaseModifyTypeEnum.cancel.getCode())) {
                    // 操作采购删单
                    for (PurchaseModifyDO purchaseModifyDO : toDoList) {
                        try {
                            request = new PurchaseModifyRequest();
                            request.setModifyType(modifytype);
                            request.setOrderNo(purchaseModifyDO.getOrderFno());
                            // 查询请购表是否存在
                            requestList = purchaseModifyMapper.getRequestPurchase(request);
                            // 先查询采购表是否存在
                            purchaseList = purchaseModifyMapper.getPurchaseOrder(request);
                            requestCancelDto = new RequestCancelDto();
                            if (!CollectionUtils.isEmpty(purchaseList)) {
                                // 校验是否为已接单状态
                                if (!purchaseList.get(0).getStatecode().equals(PurchaseOrderStatusEnum.YIJIEDAN)) {
                                    // 已发送状态，暂不处理
                                    if (purchaseList.get(0).getStatecode().equals(PurchaseOrderStatusEnum.YIFASONG)) {
                                        purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.notHand.getCode());
                                        purchaseModifyDO.setHandleResult("请在接单后删除");
                                    } else {
                                        // 采购状态不可变更
                                        purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                                        purchaseModifyDO.setHandleResult("采购单状态不可变更");
                                    }
                                    purchaseModifyDO.setUpdateUser("system");
                                    updatePurchaseModify(purchaseModifyDO);
                                    // 回写task
                                    if (StringUtils.isNotBlank(purchaseModifyDO.getBatchNo()) && !Objects.equals(purchaseModifyDO.getStatus(), PurchaseModifyStatusEnum.notHand.getCode())) {
                                        sendMsgToOrderTask(purchaseModifyDO, purchaseModifyDO.getHandler(), true, purchaseModifyDO.getHandleResult(), purchaseModifyDO.getHandleResult());
                                    }
                                    faileNum++;
                                    continue;
                                }
                                requestCancelDto.setMergeflag(purchaseList.get(0).getMergeflag());
                                requestCancelDto.setStatecode(purchaseList.get(0).getStatecode());
                                requestCancelDto.setCanceltype("1");
                            } else {
                                if (CollectionUtils.isEmpty(requestList)) {
                                    // 采购状态不可变更
                                    purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                                    purchaseModifyDO.setHandleResult("状态不可变更！");
                                    purchaseModifyDO.setUpdateUser("system");
                                    updatePurchaseModify(purchaseModifyDO);
                                    if (StringUtils.isNotBlank(purchaseModifyDO.getBatchNo())) {
                                        sendMsgToOrderTask(purchaseModifyDO, purchaseModifyDO.getHandler(), true, purchaseModifyDO.getHandleResult(), purchaseModifyDO.getHandleResult());
                                    }
                                    faileNum++;
                                    continue;
                                }
                                // 2024-01-26 修改，请购状态为 1 4 5 时，也可以进行删单操作
                                Set<String> purchaseStatus = new HashSet<String>(PurchaseModifyCommonEnum.purchaseStatusList());
                                if (!purchaseStatus.contains(requestList.get(0).getStatecode())) {
                                    // 采购状态不可变更
                                    purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.notHand.getCode());
                                    purchaseModifyDO.setHandleResult("当前请购状态不能删单，请稍后重试");
                                    purchaseModifyDO.setUpdateUser("system");
                                    updatePurchaseModify(purchaseModifyDO);
//                                if (StringUtils.isNotBlank(purchaseModifyDO.getBatchNo())) {
//                                    sendMsgToOrderTask(purchaseModifyDO, purchaseModifyDO.getHandler(), true, purchaseModifyDO.getHandleResult(), purchaseModifyDO.getHandleResult());
//                                }
                                    faileNum++;
                                    continue;
                                }
                            }
                            // 构造采购删单实体
                            purchaseDeleteDto = new PurchaseDeleteDto();
                            purchaseDeleteDto.setOrderno(purchaseModifyDO.getOrderNo());
                            purchaseDeleteDto.setItemno(purchaseModifyDO.getItemNo());
                            purchaseDeleteDto.setSplititemno(purchaseModifyDO.getSplitItemNo());
                            purchaseDeleteDto.setRequestpurchaselist(requestList);
                            // 调用wm接口，校验采购是否可以删单
                            commonResult = purchaseDeleteValidateFeignApi.purchaseDelete(purchaseDeleteDto);
                            if (commonResult.isSuccess()) {
                                requestCancelDto.setOrderno(purchaseModifyDO.getOrderNo());
                                requestCancelDto.setItemno(purchaseModifyDO.getItemNo());
                                requestCancelDto.setSplititemno(purchaseModifyDO.getSplitItemNo());
                                requestCancelDto.setOperator(StringUtils.isBlank(purchaseModifyDO.getHandler()) ? "batchModi" : "many" + purchaseModifyDO.getHandler());
                                requestCancelDto.setReason(purchaseModifyDO.getApplyReason());
                                log.info("采购批量变更-执行采购删单 {} data  {}", purchaseModifyDO.getApplyNo(), JSON.toJSONString(purchaseModifyDO));
                                // 操作采购删单
                                commonResult = purchaseBatchEditFeignApi.cancelPurchase(requestCancelDto);
                                if (commonResult.isSuccess()) {
                                    // 删单成功 返回成功
                                    purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.finish.getCode());
                                    purchaseModifyDO.setHandleResult(PurchaseModifyStatusEnum.finish.getCodeName());
                                    purchaseModifyDO.setUpdateUser("system");
                                    updatePurchaseModify(purchaseModifyDO);
                                    successNum++;
                                    // 门户的订单，回传给订单task表状态
                                    if (StringUtils.isNotBlank(purchaseModifyDO.getBatchNo())) {
                                        // 调用通用方法
                                        sendMsgToOrderTask(purchaseModifyDO, purchaseModifyDO.getHandler(), false, null, purchaseModifyDO.getHandleResult());
                                    }
                                } else {
                                    // 采购状态不可变更
                                    purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                                    purchaseModifyDO.setHandleResult(PurchaseModifyStatusEnum.bnbg.getCodeName() + commonResult.getMessage());
                                    purchaseModifyDO.setUpdateUser("system");
                                    updatePurchaseModify(purchaseModifyDO);
                                    log.error("采购批量变更 {},采购删单失败，原因: {}", purchaseModifyDO.getApplyNo(), commonResult.getMessage());
                                    faileNum++;
                                    // 门户的订单，回传给订单task表状态
                                    if (StringUtils.isNotBlank(purchaseModifyDO.getBatchNo())) {
                                        // 调用通用方法
                                        sendMsgToOrderTask(purchaseModifyDO, purchaseModifyDO.getHandler(), true, commonResult.getMessage(), commonResult.getMessage());
                                    }
                                }
                            } else {
                                // 采购状态不可变更
                                purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                                purchaseModifyDO.setHandleResult(PurchaseModifyStatusEnum.bnbg.getCodeName() + commonResult.getMessage());
                                purchaseModifyDO.setUpdateUser("system");
                                updatePurchaseModify(purchaseModifyDO);
                                log.error("采购批量变更 {},采购删单失败，原因: {}", purchaseModifyDO.getApplyNo(), commonResult.getMessage());
                                faileNum++;
                                // 门户的订单，回传给订单task表状态
                                if (StringUtils.isNotBlank(purchaseModifyDO.getBatchNo())) {
                                    // 调用通用方法
                                    sendMsgToOrderTask(purchaseModifyDO, purchaseModifyDO.getHandler(), true, commonResult.getMessage(), commonResult.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            purchaseModifyDO.setHandleResult(e.getMessage());
                            purchaseModifyDO.setUpdateUser("system");
                            updatePurchaseModify(purchaseModifyDO);
                            log.error("采购批量变更 {},采购删单失败，原因: {}", purchaseModifyDO.getApplyNo(), e.getMessage());
                        }
                    }
                }
                // 操作采购转定
                else {
                    for (PurchaseModifyDO purchaseModifyDO : toDoList) {
                        try {
                            // 校验表更内容是否为空
                            if (StringUtils.isBlank(purchaseModifyDO.getApplyContent())) {
                                // 申请变更内容不能为空
                                purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                                purchaseModifyDO.setHandleResult(PurchaseModifyStatusEnum.bnbg.getCodeName() + "-申请变更内容不能为空！");
                                purchaseModifyDO.setUpdateUser("system");
                                updatePurchaseModify(purchaseModifyDO);
                                if (StringUtils.isNotBlank(purchaseModifyDO.getBatchNo())) {
                                    sendMsgToOrderTask(purchaseModifyDO, purchaseModifyDO.getHandler(), true, purchaseModifyDO.getHandleResult(), purchaseModifyDO.getHandleResult());
                                }
                                faileNum++;
                                continue;
                            }
                            request = new PurchaseModifyRequest();
                            request.setModifyType(modifytype);
                            request.setOrderNo(purchaseModifyDO.getOrderFno());
                            // 先查询采购表是否存在
                            purchaseList = purchaseModifyMapper.getPurchaseOrder(request);
                            if (CollectionUtils.isEmpty(purchaseList)) {
                                // 查询请购表是否存在
                                requestList = purchaseModifyMapper.getRequestPurchase(request);
                                if (!CollectionUtils.isEmpty(requestList)) {
                                    if (requestList.get(0).getStatecode().equals(RequestPurchaseStatusEnum.CHULIZHONG)) {
                                        // 采购状态不可变更
                                        purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.notHand.getCode());
                                        purchaseModifyDO.setHandleResult("请在接单后删除");
                                        purchaseModifyDO.setUpdateUser("system");
                                        updatePurchaseModify(purchaseModifyDO);
//                                    if (StringUtils.isNotBlank(purchaseModifyDO.getBatchNo())) {
//                                        sendMsgToOrderTask(purchaseModifyDO, purchaseModifyDO.getHandler(), true, purchaseModifyDO.getHandleResult(), purchaseModifyDO.getHandleResult());
//                                    }
                                        faileNum++;
                                        continue;
                                    }
                                }
                                // 采购状态不可变更
                                purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                                purchaseModifyDO.setHandleResult("未找到相关采购单信息或者已经删单！");
                                purchaseModifyDO.setUpdateUser("system");
                                updatePurchaseModify(purchaseModifyDO);
                                if (StringUtils.isNotBlank(purchaseModifyDO.getBatchNo())) {
                                    sendMsgToOrderTask(purchaseModifyDO, purchaseModifyDO.getHandler(), true, purchaseModifyDO.getHandleResult(), purchaseModifyDO.getHandleResult());
                                }
                                faileNum++;
                                continue;
                            }
                            verifyPurchase = purchaseList.get(0);
                            if (!(verifyPurchase.getStatecode().equals(PurchaseOrderStatusEnum.YIFASONG) || verifyPurchase.getStatecode().equals(PurchaseOrderStatusEnum.YIJIEDAN))) {
                                // 采购状态不可变更
                                purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                                purchaseModifyDO.setHandleResult(PurchaseModifyStatusEnum.bnbg.getCodeName() + "-采购状态不是已发送或者已接单，不可操作转定");
                                purchaseModifyDO.setUpdateUser("system");
                                updatePurchaseModify(purchaseModifyDO);
                                if (StringUtils.isNotBlank(purchaseModifyDO.getBatchNo())) {
                                    sendMsgToOrderTask(purchaseModifyDO, purchaseModifyDO.getHandler(), true, purchaseModifyDO.getHandleResult(), purchaseModifyDO.getHandleResult());
                                }
                                faileNum++;
                                continue;
                            }
                            //  调用采购转定接口
                            verifyPurchase.setOperator(StringUtils.isBlank(purchaseModifyDO.getHandler()) ? "batchModi" : "many" + purchaseModifyDO.getHandler());
                            if (StringUtils.isNotBlank(purchaseModifyDO.getApplyReason())) {
                                verifyPurchase.setInformation(StringUtils.isBlank(verifyPurchase.getInformation()) ? purchaseModifyDO.getApplyReason() : verifyPurchase.getInformation().concat(purchaseModifyDO.getApplyReason()));
                            }
                            switch (modifytype) {
                                case "A":
                                    verifyPurchase.setSupplierid(purchaseModifyDO.getApplyContent());
                                    break;
                                case "D":
                                    verifyPurchase.setHopeexportdate(ft.parse(purchaseModifyDO.getApplyContent()));
                                    break;
                                default:
                                    // 先不支持修改运输方式
//                                verifyPurchase.setTranstype(purchaseModifyDO.getApplyContent());
                            }
                            log.info("采购批量变更-执行采购转定 {} data  {}", purchaseModifyDO.getApplyNo(), JSON.toJSONString(purchaseModifyDO));
                            commonResult = purchaseBatchEditFeignApi.updateTrans(verifyPurchase);
                            if (commonResult.isSuccess()) {
                                // 转定完成 返回成功
                                purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.finish.getCode());
                                purchaseModifyDO.setHandleResult(PurchaseModifyStatusEnum.finish.getCodeName());
                                purchaseModifyDO.setUpdateUser("system");
                                updatePurchaseModify(purchaseModifyDO);
                                successNum++;
                                // 门户的订单，回传给订单task表状态
                                if (StringUtils.isNotBlank(purchaseModifyDO.getBatchNo())) {
                                    // 调用通用方法
                                    sendMsgToOrderTask(purchaseModifyDO, purchaseModifyDO.getHandler(), false, null, purchaseModifyDO.getHandleResult());
                                }
                            } else {
                                // 采购状态不可变更
                                purchaseModifyDO.setStatus(PurchaseModifyStatusEnum.bnbg.getCode());
                                purchaseModifyDO.setHandleResult(PurchaseModifyStatusEnum.bnbg.getCodeName() + commonResult.getMessage());
                                purchaseModifyDO.setUpdateUser("system");
                                updatePurchaseModify(purchaseModifyDO);
                                log.error("采购批量变更 {},采购转定失败，原因: {}", purchaseModifyDO.getApplyNo(), commonResult.getMessage());
                                faileNum++;
                                // 门户的订单，回传给订单task表状态
                                if (StringUtils.isNotBlank(purchaseModifyDO.getBatchNo())) {
                                    // 调用通用方法
                                    sendMsgToOrderTask(purchaseModifyDO, purchaseModifyDO.getHandler(), true, purchaseModifyDO.getHandleResult(), purchaseModifyDO.getHandleResult());
                                }
                            }
                        } catch (Exception e) {
                            purchaseModifyDO.setHandleResult(e.getMessage());
                            purchaseModifyDO.setUpdateUser("system");
                            updatePurchaseModify(purchaseModifyDO);
                            log.error("采购批量变更 {},采购转定失败，原因: {}", purchaseModifyDO.getApplyNo(), e.getMessage());
                        }
                    }
                }
            }
            return ResultVo.success("批量采购执行成功,共计 变更成功" + successNum + "条；" + "变更失败：" + faileNum + "条。");
        }
        return ResultVo.success("暂无待处理数据");
    }

    @Override
    public ResultVo<String> insertPurchaseModify(PurchaseModifyVO vo) {
        if (vo == null) {
            return ResultVo.failure("入参不可为空");
        }
        PurchaseModifyDO purchaseModifyDO = BeanCopyUtil.copy(vo, PurchaseModifyDO.class);
        try {
            purchaseModifyMapper.insert(purchaseModifyDO);
        } catch (Exception e) {
            log.error("写入purchaseModify发生异常 {}",e.getMessage(),e);
            return ResultVo.failure("写入purchaseModify发生异常"+e.getMessage());
        }
        return ResultVo.success("插入成功");
    }

    /**
     * 字符串中，特殊字符，替换为空格
     */
    private String replaceSpace(String content) {
        byte[] space = new byte[]{(byte) 0xc2, (byte) 0xa0};
        String UTFSpace = null;
        try {
            UTFSpace = new String(space, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        content = content.replace(UTFSpace, " ");
        return content;
    }

    /**
     * 生成 采购修改申请号随机数
     *
     * @return
     */
    private String generateApplyNo(String modifyType) {
        Random random = new Random();
        int randomNumber = random.nextInt(90) + 10;
        return modifyType + System.currentTimeMillis() + randomNumber;
    }


    /**
     * 通用 更新 purchase_modify方法
     */
    private Integer updatePurchaseModify(PurchaseModifyDO purchaseModifyDO) {
        // 更新状态
        PurchaseModifyDO updateDo = new PurchaseModifyDO();
        updateDo.setId(purchaseModifyDO.getId());
        updateDo.setStatus(purchaseModifyDO.getStatus());
        updateDo.setUpdateUser(purchaseModifyDO.getUpdateUser());
        updateDo.setUpdateTime(new Date());
        updateDo.setHandleTime(new Date());
        // 追加备注
        updateDo.setHandleResult(purchaseModifyDO.getHandleResult());
        // 更新状态
        return purchaseModifyMapper.updateById(updateDo);
    }

    /**
     * 变更类别字典
     *
     * @return
     */
    private Map<String, String> getModifyTypes() {
        // 获取变更类别字典
        ResultVo<List<DataCodeVO>> dataTypeCodesInfo = dictDataServiceFeignApi.getDataCodes("2081");
        if (dataTypeCodesInfo.isSuccess()) {
            List<DataCodeVO> list = dataTypeCodesInfo.getData();
            return list.stream().collect(Collectors.toMap(
                    DataCodeVO::getCodeName, DataCodeVO::getCode,
                    (val1, val2) -> val2
            ));
        }
        return null;
    }

    /**
     * 通用 采购单状态的校验，判断是否可以删单或者转定
     */
    private String verifyPurchaseStateCode(PurchaseModifyDO purchaseModifyDO) {
        return null;
    }

    /**
     * 回传给 order_task的事件状态
     *  2024-02-26，修改传输给task类型，变更：指定出荷日、运输方式 对应 3，变更供应商对应 2
     */
    private void sendMsgToOrderTask(PurchaseModifyDO purchaseModifyDO, String optUserNo, Boolean iserror, String errorMsg, String text) {
        UpTaskInfoVO upTaskInfoVO = new UpTaskInfoVO();
        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
//        // 批次号
        upTaskInfoVO.setBatchNo(purchaseModifyDO.getBatchNo());
//        // 操作人
        upTaskInfoVO.setOptUserNo(optUserNo);
        // 2024-02-26 回传task时，增加变更类型的判断 B91717
        int salesApplyType = OpsCommonoSalesApplyTypeEnum.cg_modify.getCode();
        if (purchaseModifyDO.getModifyType().equalsIgnoreCase(PurchaseModifyTypeEnum.bggys.getCode())) {
            salesApplyType = OpsCommonoSalesApplyTypeEnum.order_modify.getCode();
        }
        if (iserror) {
            int isSuccess = 0;
            // 异常信息
            upTaskInfoVO.setErrorMsg(errorMsg);
            if (purchaseModifyDO.getStatus()!=null && purchaseModifyDO.getStatus() == PurchaseModifyStatusEnum.notHand.getCode()) { // 暂不处理状态时，回传状态为暂不处理
                isSuccess = 2;
            }
            dealReturnOpsParamVO = conventCallBackParam(isSuccess, purchaseModifyDO.getApplyNo(), salesApplyType, purchaseModifyDO.getOrderFno() + errorMsg);
        } else {
            dealReturnOpsParamVO = conventCallBackParam(1, purchaseModifyDO.getApplyNo(), salesApplyType, purchaseModifyDO.getOrderFno() + text);
        }
        OpsSalesCommonParamVO vo = new OpsSalesCommonParamVO();
        vo.setData(dealReturnOpsParamVO);
        upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(vo));
        upTaskInfoVO.setReturnStatus("0");
        orderModifyServiceFeignApi.upTaskInfoByBatchNo(upTaskInfoVO);
    }

    public DealReturnOpsParamVO conventCallBackParam(int isSuccess, String applyNo, int applyType, String desc) {
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
//            bean.setResultDescription("暂不处理");
        }
        OpsSalesCommonParamVO vo = new OpsSalesCommonParamVO();
        vo.setData(bean);
        DealReturnOpsParam param = new DealReturnOpsParam();
        param.setOpsSalesCommonParamVo(vo);
        dealReturnOpsParamVO.setDealReturnOpsParam(param);
        return dealReturnOpsParamVO;
    }

}
