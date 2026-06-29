package com.smc.ops.delivery.inqb.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sales.ops.db.entity.OpsInqbApply;
import com.sales.ops.db.entity.OpsInqbDetail;
import com.sales.ops.dto.inqb.*;
import com.sales.ops.dto.inquiry.InquiryWorkdayCondition;
import com.sales.ops.dto.util.DateUtil;
import com.smc.ops.delivery.inqb.service.InqbApplyCommonService;
import com.smc.ops.delivery.inqb.service.InqbJobHandleService;
import com.smc.ops.delivery.inquiry.mapper.InquiryWorkDayYearMapper;
import com.smc.ops.delivery.mapper.inqb.InqbApplyMapper;
import com.smc.ops.delivery.mapper.inqb.InqbDetailMapper;
import com.smc.ops.delivery.mapper.inqb.InqbHandlelogMapper;
import com.smc.ops.delivery.mapper.inqb.InqbSalesNoticeTaskMapper;
import com.smc.ops.delivery.model.DataTypeVO;
import com.smc.ops.delivery.model.inqb.OpsInqbApplyDO;
import com.smc.ops.delivery.model.inqb.OpsInqbDetailDO;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 催促订单处理
 *
 * @author B91717
 * @date 2024-06-13
 */
@Service
@Slf4j
public class InqbJobHandleServiceImpl implements InqbJobHandleService {

    @Resource
    private InqbApplyMapper inqbApplyMapper;

    @Resource
    private InqbDetailMapper inqbDetailMapper;

    @Resource
    private InqbHandlelogMapper inqbHandlelogMapper;

    @Resource
    private InqbApplyCommonService inqbApplyCommonService;

    @Resource
    private InquiryWorkDayYearMapper inquiryWorkDayYearMapper;
    @Resource
    private InqbSalesNoticeTaskMapper inqbSalesNoticeTaskMapper;

    /**
     * 刷新有效状态
     * 方案：1.查询出所有可用的INQB单据，筛选失效日期小于等于当前日期的，更新状态为不可用
     * 2.根据子项的状态，同步刷新整单的可用状态
     *
     * @return
     */
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> updateInqbValidity() throws Exception {
        // 日次刷新一下缓存中的InqbCommonEnum.CODECONFIGKEY.getType()信息，重新从表中查询赋值
        inqbApplyCommonService.refreshInqbCodeConfig();
        // 查询出所有可用的INQB单据，筛选失效日期小于等于当前日期的，更新状态为不可用
        Date today = new Date();
        List<String> applynoList = null;
        LambdaQueryWrapper<OpsInqbDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsInqbDetailDO::getStatus, InqbDetailStatusEnum.VALID.getType())
                .le(OpsInqbDetailDO::getExpirationDate, today); // 筛选失效日期小于等于当前日期的
        List<OpsInqbDetailDO> opsList = inqbDetailMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(opsList)) {
            opsList.forEach(opsInqbDetailDO -> {
                opsInqbDetailDO.setStatus(InqbDetailStatusEnum.INVALID.getType());
                opsInqbDetailDO.setUpdateUser("定时刷新有效状态");
                opsInqbDetailDO.setUpdateTime(today);
                inqbDetailMapper.updateById(opsInqbDetailDO);
            });
            // 写入applynoList
            applynoList = opsList.stream().map(OpsInqbDetailDO::getInqbApplyNo).collect(Collectors.toList());
        }
        // 刷新主表的可用状态
        LambdaQueryWrapper<OpsInqbApplyDO> applyWrapper = new LambdaQueryWrapper<>();
        applyWrapper.eq(OpsInqbApplyDO::getInqbStatus, InqbStatusEnum.YIDAFU.getType())
                .le(OpsInqbApplyDO::getExpirationDate, today); // 筛选失效日期小于等于当前日期的
        List<OpsInqbApplyDO> applyList = inqbApplyMapper.selectList(applyWrapper);
        if (CollectionUtils.isNotEmpty(opsList)) {
            applyList.forEach(opsInqbApplyDO -> {
                opsInqbApplyDO.setInqbStatus(InqbStatusEnum.YIGUOQI.getType());
                if (opsInqbApplyDO.getInqbValidity().equals(InqbValidityEnum.VALID.getType())) {
                    // bug14986 INQB有效性，当状态变为已回复时，更新主表的有效性状态
                    opsInqbApplyDO.setInqbValidity(InqbValidityEnum.INVALID.getType());
                }
                opsInqbApplyDO.setUpdateUser("定时刷新有效状态");
                opsInqbApplyDO.setUpdateTime(today);
                inqbApplyMapper.updateById(opsInqbApplyDO);
            });
            // 写入applynoList
            applynoList.addAll(applyList.stream().map(OpsInqbApplyDO::getInqbApplyNo).collect(Collectors.toList()));
        }
        // 回传门户,异常的消息
        if (CollectionUtils.isNotEmpty(applynoList)) {
            // applynoList去重
            applynoList = applynoList.stream().distinct().collect(Collectors.toList());
            callbackSalesToTask(applynoList);
        }
        // 刷新时，需要回传门户的状态
        return ResultVo.success("INQB有效状态刷新成功");
    }


    /**
     * INQ-B 从集团采购系统获取回复信息
     * 1.查询集团采购的中间表，根据上次记录的id获取到最新的回复信息
     * 2.筛选状态已回复/异常的数据
     * 3.根据taskno回更INQB申请主子表的回复数据
     * 4.根据回复的货期，计算失效日期，并更新主子表
     * 5.回复信息回传ops-task表
     *
     * @return
     * @throws Exception
     */
    @Override
    public ResultVo<String> getInqbGroupReply() throws Exception {
        // 获取上次记录的id, todo 上线时增加正式表中的字典
        DataTypeVO dataCodes = inqbApplyCommonService.getInqbDataCodesInfo(InqbConstants.DICT_CLASSCODE_INQBID, InqbConstants.DICT_CLASSCODE_INQBCODE);
        if (dataCodes == null) {
            throw new BusinessException("获取数据字典失败" + InqbConstants.DICT_CLASSCODE_INQBID);
        }
        Long maxId = Long.valueOf(dataCodes.getExtNote1()); // 获取上次最大id值
        List<OpsInqbHandle> opsInqbHandleList = inqbHandlelogMapper.getReplyHandleData(maxId, InqbHandleStatusEnum.YIHUIFU.getType());
        if (CollectionUtils.isEmpty(opsInqbHandleList)) {
            return ResultVo.success("暂估最新的INQB回复信息");
        }
        // 取出查询集合中的最大id号
        OptionalLong maxIdOptional = opsInqbHandleList.stream()
                .mapToLong(OpsInqbHandle::getId)
                .max();
        Long updateMaxId = maxIdOptional.getAsLong();
        // 调用更新方法
        ResultVo<String> returnMsg = updateReplyData(opsInqbHandleList);
        // 调用门户的回更操作
        if (!returnMsg.isSuccess()) {
            return ResultVo.failure(returnMsg.getMessage());
        }
        // 回调task给门户
        ResultVo<String> callbackMsg = callbackToTask(opsInqbHandleList);
        if (callbackMsg.isSuccess()) {
            inqbApplyCommonService.updateInqbExtNote1(dataCodes.getId(), String.valueOf(updateMaxId), String.valueOf(maxId));
        }
        return ResultVo.success(returnMsg.getData());
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> updateReplyData(List<OpsInqbHandle> returnList) {
        int successCount = 0;
        int errorCount = 0;
        StringBuilder errorMessage = new StringBuilder();
        // 更新到 handle表中
        OpsInqbDetailDO opsDetailDO;
        OpsInqbApplyDO opsInqbApplyDO;
        InquiryWorkdayCondition inquiryWorkdayCondition;
        List<String> applyNoList = new ArrayList<>();
        for (OpsInqbHandle renturnHandle : returnList) {
            try {
                // 对接中国制造供应商时，期望的货期天数及期望货期时间都留存，根据申请日期+工作日天数进行计算,反推出来工作日天数
                Set<String> suppilyList = new HashSet<String>(InqbCommonEnum.manuSuppilyList());
                if (suppilyList.contains(renturnHandle.getSupplierCode()) && renturnHandle.getReplyDeliveryDate() != null) {
                    int expectedDeliveryDateParse;
                    // bug16078 INQB获取中国制造回复信息时，针对回复时间为空需要处理
                    if (renturnHandle.getReplyTime() == null) {
                        log.error("INQB催促回调中国制造回复时间（ReplyTime）为空，请检查回复数据", JSONUtil.toJsonStr(renturnHandle));
                        errorCount++;
                        errorMessage.append("INQB催促回调中国制造回复时间（ReplyTime）为空，请检查回复数据:").append(JSONUtil.toJsonStr(renturnHandle));
                        continue;
                    }
                    if (DateUtil.dateToDateString(renturnHandle.getReplyTime()).equals(DateUtil.dateToDateString(renturnHandle.getReplyDeliveryDate()))) {
                        expectedDeliveryDateParse = 0;
                    } else {
                        expectedDeliveryDateParse = inquiryWorkDayYearMapper.getCNWorkDayCount(renturnHandle.getReplyTime(), renturnHandle.getReplyDeliveryDate());
                        // 查询工作日差异天数为0时，调换回复时间与回复货期重新计算天数，并将回复天数置为负数
                        if (expectedDeliveryDateParse == 0) {
                            expectedDeliveryDateParse = -inquiryWorkDayYearMapper.getCNWorkDayCount(renturnHandle.getReplyDeliveryDate(), renturnHandle.getReplyTime());
                        }
                    }
                    renturnHandle.setReplyDeliveryDays(expectedDeliveryDateParse);
                }
                // 单条更新，根据ID进行更新
                // 查询INQB子表中，催促中的催促订单，去回调结果
                LambdaQueryWrapper<OpsInqbDetailDO> detailUpdateWrapper = new LambdaQueryWrapper<>();
                detailUpdateWrapper.eq(OpsInqbDetailDO::getTaskNo, renturnHandle.getTaskNo())
                        .eq(OpsInqbDetailDO::getSupplierCode, renturnHandle.getSupplierCode())
                        .eq(OpsInqbDetailDO::getHandleResult, InqbDetailStatusEnum.HANDLEVALID.getType().toString())
                        .isNull(OpsInqbDetailDO::getReplyDeliveryDays);
                opsDetailDO = inqbDetailMapper.selectOne(detailUpdateWrapper);
                Date expirationDate = null;
                if (opsDetailDO != null) {
                    // 10-10 当回复的货期为空时，先不计算截止日期，等回复了货期再进行计算
                    if (renturnHandle.getReplyDeliveryDays()!=null) {
                        // 根据回复日期计算有效截止日
                        inquiryWorkdayCondition = new InquiryWorkdayCondition();
                        inquiryWorkdayCondition.setCountry(renturnHandle.getSupplierCode());
                        inquiryWorkdayCondition.setDay(renturnHandle.getReplyTime());
                        ResultVo<Date> dateResult = inqbApplyCommonService.calExpirationDate(inquiryWorkdayCondition);
                        if (!dateResult.isSuccess()) {
                            log.error("INQB催促回调获取工作日配置信息失败:", dateResult.getMessage());
                            errorCount++;
                            errorMessage.append("INQB催促回调获取工作日配置信息失败: ").append(dateResult.getMessage());
                            continue; // 计算回复货期失败时，返回错误
                        }
                        expirationDate = dateResult.getData();
                        opsDetailDO.setExpirationDate(expirationDate);
                        // 判断回复的日期，与当前日期做对比，小于等于当前日期，则回传状态为过期，不影响整单的状态
                        if (expirationDate.before(new Date())) {
                            opsDetailDO.setStatus(InqbDetailStatusEnum.INVALID.getType());
                        }
                    }
                    opsDetailDO.setReplySupplierDept(renturnHandle.getReplySupplierDept());
                    opsDetailDO.setReplyDept(renturnHandle.getReplyDept());
                    opsDetailDO.setReplyNo(renturnHandle.getReplyNo());
                    opsDetailDO.setReplyDeliveryDays(renturnHandle.getReplyDeliveryDays());
                    opsDetailDO.setReplyAcceptHl(renturnHandle.getReplyAcceptHl());
                    opsDetailDO.setReplyUser(renturnHandle.getReplyUser());
                    opsDetailDO.setReplyTime(renturnHandle.getReplyTime());
                    // todo 回复的分类码需要在OPS转换？
                    opsDetailDO.setReplyReasonCode(renturnHandle.getReplyReasonCode()); // 回复原因代码
                    opsDetailDO.setReplyReason(renturnHandle.getReplyReason());
                    // BUG14973，新增回复原因字段，记录回复的原因
                    opsDetailDO.setReplyRemark(renturnHandle.getReplyRemark()); //回复备注
                    opsDetailDO.setUpdateTime(new Date());
                    opsDetailDO.setUpdateUser("system");
                    int detailCount = inqbDetailMapper.updateById(opsDetailDO);
                    if (detailCount > 0) {
                        // 子表更新完成，更新主表数据
                        LambdaQueryWrapper<OpsInqbApplyDO> applyWrapper = new LambdaQueryWrapper<>();
                        applyWrapper.eq(OpsInqbApplyDO::getInqbApplyNo, opsDetailDO.getInqbApplyNo())
                                .ne(OpsInqbApplyDO::getInqbStatus, InqbStatusEnum.YIDAFU.getType());
                        List<OpsInqbApplyDO> opsInqbApplyDOS = inqbApplyMapper.selectList(applyWrapper);
                        if (CollectionUtils.isNotEmpty(opsInqbApplyDOS)) {
                            opsInqbApplyDO = opsInqbApplyDOS.get(0);
                            if (expirationDate != null) {
                                opsInqbApplyDO.setInqbStatus(InqbStatusEnum.BUFENDAFU.getType());
                            }
                            // 查询INQB子表中，查询是否还有去采购但是未回复货期的单据
                            LambdaQueryWrapper<OpsInqbDetailDO> detailInfoWrapper = new LambdaQueryWrapper<>();
                            detailInfoWrapper.eq(OpsInqbDetailDO::getInqbApplyNo, opsInqbApplyDO.getInqbApplyNo())
                                    .eq(OpsInqbDetailDO::getHandleResult, InqbDetailStatusEnum.HANDLEVALID.getType().toString())
                                    .isNull(OpsInqbDetailDO::getReplyDeliveryDays);
                            if (inqbDetailMapper.selectCount(detailInfoWrapper) == 0) { // 判断是部分回复还是全部回复
                                opsInqbApplyDO.setInqbStatus(InqbStatusEnum.YIDAFU.getType());
                                // bug14986 INQB有效性，当状态变为已回复时，更新主表的有效性状态
                                opsInqbApplyDO.setInqbValidity(InqbValidityEnum.VALID.getType());
                            }
                            // 2024-10-10,当子项回复货期为空时，则不更新主表的回复信息
                            // 校验主表的回复状态，主表中保留对应子项中最晚的有效截止日期,当主表中有效截止日为空，或者早于当前截止日时，则更新主表的回复状态
                            if (expirationDate != null && (opsInqbApplyDO.getExpirationDate() == null || opsInqbApplyDO.getExpirationDate().before(expirationDate))) {
                                opsInqbApplyDO.setReplyDeliveryDays(renturnHandle.getReplyDeliveryDays());
                                opsInqbApplyDO.setExpirationDate(expirationDate);
                            }
                            opsInqbApplyDO.setReplyAcceptHl(renturnHandle.getReplyAcceptHl());
                            opsInqbApplyDO.setReplyReasonCode(renturnHandle.getReplyReasonCode()); // 回复原因代码
                            opsInqbApplyDO.setReplyReason(renturnHandle.getReplyReason());// 回复原因
                            opsInqbApplyDO.setReplyRemark(renturnHandle.getReplyRemark()); //回复备注
                            opsInqbApplyDO.setReplySupplierDept(renturnHandle.getReplySupplierDept());
                            opsInqbApplyDO.setReplyDept(renturnHandle.getReplyDept());
                            opsInqbApplyDO.setReplyNo(renturnHandle.getReplyNo());
                            opsInqbApplyDO.setReplyUser(renturnHandle.getReplyUser());
                            opsInqbApplyDO.setReplyTime(renturnHandle.getReplyTime());
                            opsInqbApplyDO.setUpdateTime(new Date());
                            opsInqbApplyDO.setUpdateUser("system");
                            successCount += inqbApplyMapper.updateById(opsInqbApplyDO);
                            applyNoList.add(opsInqbApplyDO.getInqbApplyNo());
                        }
                    }
                }
            } catch (Exception e) {
                log.error("INQB催促回调失败: {}", e.getMessage(), e);
                errorCount++;
                errorMessage.append("INQB催促回调失败: ").append(e.getMessage());
            }
        }
        if (StringUtils.isNotBlank(errorMessage.toString())) {
            return ResultVo.failure(errorMessage.toString());
        }
        return ResultVo.success("INQB催促回调执行成功，回调成功: " + successCount + " 项,回调失败" + errorCount + "项。");
//        return ResultVo.success(applyNoList);
    }


    /**
     * 回调成功时，回传给task表事件处理，回传事件是否放到公共目录中
     */
    public ResultVo<String> callbackToTask(List<OpsInqbHandle> opsInqbHandleList) throws Exception {
        List<InqbSalesApplyAddReturn> reurns = new ArrayList<>();
        InqbSalesApplyAddReturn returnVo;
        OpsInqbApplyDO opsInqbApplyDO;
        OpsInqbApply opsInqbApplyreturn;
        List<OpsInqbDetailDO> details = new ArrayList<>();
        List<String> applyNoList = new ArrayList<>(); // INQB主单申请号集合
        int successNum = 0;
        int failNum = 0;
        StringBuilder errMsg = new StringBuilder();
        List<String> tasknoList = opsInqbHandleList.stream().map(OpsInqbHandle::getTaskNo).collect(Collectors.toList());
        // 根据taskno查询出所有的INQB申请的主子项明细，然后构建返回实体，返回给门户
        for (OpsInqbHandle opsInqbHandle : opsInqbHandleList) {
            //根据taskno，查询子项明细信息
            List<OpsInqbDetail> opsInqbDetails = inqbDetailMapper.getInqbDetailByTaskNo(opsInqbHandle.getTaskNo());
            if (CollectionUtils.isEmpty(opsInqbDetails)) {
                errMsg.append("根据taskno查询子项明细信息失败，taskno: ").append(opsInqbHandle.getTaskNo());
                continue; // 当前主申请号已经回传时，跳过
            }
            String inqbApplyNo = opsInqbDetails.get(0).getInqbApplyNo();
            if (applyNoList.contains(inqbApplyNo)) {
                continue; // 当前主申请号已经回传时，跳过
            }
            boolean isError = false;
            returnVo = new InqbSalesApplyAddReturn(); //初始化回调实体
            List<OpsInqbApply> opsInqbApplies = inqbApplyMapper.getApplyByApplyNo(inqbApplyNo);
            if (CollectionUtils.isNotEmpty(opsInqbApplies) && opsInqbApplies.size() == 1) {
                opsInqbApplyreturn = opsInqbApplies.get(0);
                if (opsInqbHandle.getStatus().equals(InqbHandleStatusEnum.YIHUIFU.getType())) {
                    returnVo.setStatus(InqbSalesReturnStatusEnum.REPLYSUCCESS.getType());
                    returnVo.setStatusDescription(InqbSalesReturnStatusEnum.REPLYSUCCESS.getDesc());
                }
                if (opsInqbHandle.getStatus().equals(InqbHandleStatusEnum.WENXUNZHONG.getType())) {
                    returnVo.setStatus(InqbSalesReturnStatusEnum.ADDSUCCESS.getType());
                    returnVo.setStatusDescription(InqbSalesReturnStatusEnum.ADDSUCCESS.getDesc());
                }
                if (opsInqbHandle.getStatus().equals(InqbHandleStatusEnum.ERROR.getType())) { //异常时，返回错误信息？
                    returnVo.setSourcesApplyNo(opsInqbApplyreturn.getSourcesApplyNo());
                    returnVo.setStatus(InqbSalesReturnStatusEnum.REPLYERROR.getType());
                    returnVo.setStatusDescription(opsInqbHandle.getHandleResult());
                    isError = true;
                }
                returnVo.setSplitType(InqbSplitTypeEnum.parse(opsInqbDetails.get(0).getSplitType()).getSplitDesc());
                if (StringUtils.isNotBlank(opsInqbApplyreturn.getInqbValidity())) {
                    opsInqbApplyreturn.setInqbValidity(InqbValidityEnum.parse(opsInqbApplyreturn.getInqbValidity()).getDesc());
                }
                returnVo.setOpsInqbApply(opsInqbApplyreturn);
                returnVo.setOpsInqbDetailList(opsInqbDetails);
                returnVo.setInqbStatusDesc(InqbStatusEnum.parse(opsInqbApplyreturn.getInqbStatus()).getDesc());
//                returnVo.setInqbValidityDesc(InqbValidityEnum.parse(opsInqbApplyreturn.getInqbValidity()).getDesc());
                ResultVo<String> taskResult = sendMsgToOrderTask(returnVo, isError);
                if (taskResult.isSuccess()) {
                    successNum++;
                } else {
                    failNum++;
                    errMsg.append(taskResult.getMessage());
                }
                reurns.add(returnVo);
            }
        }
        return ResultVo.success("催促信息回调成功，共计回写成功" + successNum + "条；回写失败" + failNum + "条，错误信息 " + errMsg.toString());
    }

    /**
     * 回传事件是否放到公共目录中
     */
    @Override
    public ResultVo<String> callbackSalesToTask(List<String> applynoList) throws Exception {
        if (CollectionUtils.isEmpty(applynoList)) {
            return ResultVo.failure("催促信息回调成功回写失败:申请单号为空");
        }
        List<InqbSalesApplyAddReturn> reurns = new ArrayList<>();
        InqbSalesApplyAddReturn returnVo;
        OpsInqbApply opsInqbApplyreturn;
        int successNum = 0;
        int failNum = 0;
        StringBuilder errMsg = new StringBuilder();
        // 根据applyno查询出所有的INQB申请的主子项明细，然后构建返回实体，返回给门户
        for (String applyNo : applynoList) {
            //根据applno，查询子项明细信息
            List<OpsInqbDetail> opsInqbDetails = inqbDetailMapper.getInqbDetailByApplyNo(applyNo);
            if (CollectionUtils.isEmpty(opsInqbDetails)) {
                errMsg.append("根据申请号查询子项明细信息失败，申请号: ").append(applyNo);
                continue; // 当前主申请号已经回传时，跳过
            }
            boolean isError = false;
            returnVo = new InqbSalesApplyAddReturn(); //初始化回调实体
            List<OpsInqbApply> opsInqbApplies = inqbApplyMapper.getApplyByApplyNo(applyNo);
            if (CollectionUtils.isNotEmpty(opsInqbApplies) && opsInqbApplies.size() == 1) {
                opsInqbApplyreturn = opsInqbApplies.get(0);
                returnVo.setStatus(InqbSalesReturnStatusEnum.REPLYSUCCESS.getType());
                returnVo.setStatusDescription(InqbSalesReturnStatusEnum.REPLYSUCCESS.getDesc());
                if (StringUtils.isNotBlank(opsInqbApplyreturn.getInqbValidity())) {
                    opsInqbApplyreturn.setInqbValidity(InqbValidityEnum.parse(opsInqbApplyreturn.getInqbValidity()).getDesc());
                }
                returnVo.setOpsInqbApply(opsInqbApplyreturn);
                returnVo.setOpsInqbDetailList(opsInqbDetails);
                returnVo.setInqbStatusDesc(InqbStatusEnum.parse(opsInqbApplyreturn.getInqbStatus()).getDesc());
                returnVo.setSplitType(InqbSplitTypeEnum.parse(opsInqbDetails.get(0).getSplitType()).getSplitDesc());
//                returnVo.setInqbValidityDesc(InqbValidityEnum.parse(opsInqbApplyreturn.getInqbValidity()).getDesc());
                ResultVo<String> taskResult = sendMsgToOrderTask(returnVo, isError);
                if (taskResult.isSuccess()) {
                    successNum++;
                } else {
                    failNum++;
                    errMsg.append(taskResult.getMessage());
                }
                reurns.add(returnVo);
            }
        }
        return ResultVo.success("催促信息回调成功，共计回写成功" + successNum + "条；回写失败" + failNum + "条，错误信息 " + errMsg.toString());
    }


    private ResultVo<String> sendMsgToOrderTask(InqbSalesApplyAddReturn inqbReturn, Boolean iserror) {
        InqbUpTaskInfoVO upTaskInfoVO = new InqbUpTaskInfoVO();
        upTaskInfoVO.setBatchNo(inqbReturn.getOpsInqbApply().getBatchNo());
        upTaskInfoVO.setOptUserNo(inqbReturn.getOpsInqbApply().getReplyUser());
        if (iserror) {
            upTaskInfoVO.setErrorMsg(inqbReturn.getStatusDescription()); // 异常信息
        }
        // 最外层实体
        InqbDealReturnOpsParamVO dealReturnOpsParamVO = new InqbDealReturnOpsParamVO();
        dealReturnOpsParamVO.setApplyType(11);
        InqbDealReturnOpsParam param = new InqbDealReturnOpsParam();
        param.setCommonReturnParam(inqbReturn);
        dealReturnOpsParamVO.setDealReturnOpsParam(param);
        InqbOpsSalesCommonParamVO vo = new InqbOpsSalesCommonParamVO();
        vo.setData(dealReturnOpsParamVO);
        upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(vo));
        upTaskInfoVO.setReturnStatus("0");
        return upTaskInfoByBatchNo(upTaskInfoVO);
    }

    public ResultVo<String> upTaskInfoByBatchNo(InqbUpTaskInfoVO param) {
        if (Objects.isNull((param)) || StringUtils.isBlank(param.getBatchNo())) {
            return ResultVo.failure("批次号不可为空.");
        }
        InqbUpTaskInfoVO taskInfo = inqbSalesNoticeTaskMapper.getSalesNoticeTask(param.getBatchNo());
        if (taskInfo != null) {
            param.setId(taskInfo.getId());
            int update = inqbSalesNoticeTaskMapper.updateSalesNoticeTask(param);
            if (update > 0) {
                return ResultVo.success("操作成功.");
            }
        }
        return ResultVo.failure("操作失败.");
    }

}
