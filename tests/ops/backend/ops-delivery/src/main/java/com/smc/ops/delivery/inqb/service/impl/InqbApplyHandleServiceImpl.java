package com.smc.ops.delivery.inqb.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inqb.*;
import com.sales.ops.dto.inquiry.InquiryApplyVerifyReurn;
import com.sales.ops.dto.util.DataLengthUtil;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.enums.OrderSplitTypeEnum;
import com.sales.ops.feign.inqb.InqbGroupPurchaseFeignApi;
import com.sales.ops.feign.inquiry.WmCKRuleFeignApi;
import com.smc.ops.delivery.inqb.service.InqbApplyCommonService;
import com.smc.ops.delivery.inqb.service.InqbApplyHandleService;
import com.smc.ops.delivery.inquiry.mapper.InquiryWorkDayYearMapper;
import com.smc.ops.delivery.mapper.inqb.InqbApplyMapper;
import com.smc.ops.delivery.mapper.inqb.InqbCodeConfigMapper;
import com.smc.ops.delivery.mapper.inqb.InqbDetailMapper;
import com.smc.ops.delivery.mapper.inqb.InqbOpscoreMapper;
import com.smc.ops.delivery.model.inqb.OpsInqbApplyDO;
import com.smc.ops.delivery.model.inqb.OpsInqbDetailDO;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 催促订单处理
 *
 * @author B91717
 * @date 2024-06-13
 */
@Service
@Slf4j
public class InqbApplyHandleServiceImpl implements InqbApplyHandleService {

    @Resource
    private InqbOpscoreMapper inqbOpscoreMapper;

    @Resource
    private WmCKRuleFeignApi wmCKRuleFeignApi;

    @Resource
    private InqbApplyCommonService inquiryApplyCommonService;

    @Resource
    private InqbCodeConfigMapper opsInqbCodeConfigMapper;

    @Resource
    private InqbApplyMapper opsInqbApplyMapper;

    @Resource
    private InqbDetailMapper inqbDetailMapper;

    @Resource
    private InqbGroupPurchaseFeignApi inqbGroupPurchaseFeignApi;

    @Resource
    private InquiryWorkDayYearMapper inquiryWorkDayMonthMapper;


    /**
     * INQB申请校验
     * ①型号必须是供应商可生产的型号，查询product delivery表
     * ②最终用户，型号为查询条件，如果存在3日内的申请单则不允许问询。
     *
     * @param modelno
     * @param enduser
     * @return
     */
    @Override
    public ResultVo<String> inqbVerify(String modelno, String enduser,Integer quantity) {
        // bug 17306 申请数量的校验，范围：0＜数量＜9999999 b91717
        if (quantity <= 0 || quantity > 9999999) {
            return ResultVo.failure(InqbVerifyMsgEnum.UPQTY.getDesc());
        }
        // 先校验product delivery表中，是否存在该型号，校验是否为可生产清单中型号
        List<ProductDelivery> deliveryList = inqbOpscoreMapper.getProductDelivery(modelno);
        if (CollectionUtils.isEmpty(deliveryList)) {
            return ResultVo.failure(InqbVerifyMsgEnum.DELIVERYNULL.getDesc()); // 主供应商表不存在该型号时，返回校验失败
        }
        // 校验最终用户，型号为查询条件，如果存在3日内的申请单则不允许问询。
        List<OpsInqbApplyDO> inqbApplies = opsInqbApplyMapper.selectThreeDaysApply(modelno, enduser, InqbCommonEnum.DIFFDAYVALID.getType());
        if (CollectionUtils.isNotEmpty(inqbApplies)) {
            return ResultVo.failure(InqbVerifyMsgEnum.ITEMNONULL.getDesc());
        }
        return ResultVo.success("校验成功");
    }


    /**
     * OPS,INQB 写入接口
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @DS("opsdb")
    public ResultVo<String> inqApplyAdd(List<InqbSalesApplyAddParam> list) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.failure(InqbVerifyMsgEnum.LISTNULL.getDesc());
        }
        StringBuilder errMsg = new StringBuilder(); // 错误订单原因返回
        int result;
        List<String> ordernos;
        InquiryApplyVerifyReurn inquiryApplyVerifyReurn;
        for (InqbSalesApplyAddParam inquiryApplyAddParam : list) {
            try {
                if (InqbCommonEnum.SALES_DATATYPE.getType().equals(inquiryApplyAddParam.getDataSources())) {
                    if (StringUtils.isBlank(inquiryApplyAddParam.getBatchNo())) { // 校验批次号是否为空
                        errMsg.append(inquiryApplyAddParam.getEndUser())
                                .append(", ")
                                .append(inquiryApplyAddParam.getModelNo())
                                .append(", ")
                                .append(InqbVerifyMsgEnum.BATCHNONULL.getDesc());
                        continue;
                    }
                }
                // todo INQB催促分类码是否需要校验空？
//                if (StringUtils.isBlank(inquiryApplyAddParam.getInqbClassify())) {
//                    errMsg.append(inquiryApplyAddParam.getEndUser())
//                            .append(", ")
//                            .append(inquiryApplyAddParam.getModelNo())
//                            .append(", ")
//                            .append(InqbVerifyMsgEnum.REASONNULL.getDesc());
//                    continue;
//                }
                // 调用催促校验方法
                // todo 是否需要先进行批量校验，一个校验失败全都失败，不写入数据库？
                ResultVo<String> verifyResult = inqbVerify(inquiryApplyAddParam.getModelNo(), inquiryApplyAddParam.getEndUser(), inquiryApplyAddParam.getQuantity());
                if (!verifyResult.isSuccess()) { // 返回校验失败原因
                    errMsg.append(inquiryApplyAddParam.getEndUser())
                            .append(", ")
                            .append(inquiryApplyAddParam.getModelNo())
                            .append(", ")
                            .append(verifyResult.getMessage());
                    continue;
                }
            } catch (Exception e) {
                // 返回写入失败信息，回传给门户
                log.error("inqApplyAdd error", e);
            }
        }
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return ResultVo.failure("处理失败的INQ-B信息" + errMsg.toString());
        }
        for (InqbSalesApplyAddParam inquiryApplyAddParamInsert : list) {
            // 校验成功，开始进行订单分配结果的校验
            ResultVo<InqbAllotMergeResult> inqbAllotMergeResultResultVo = orderAllocation(inquiryApplyAddParamInsert);
            if (!inqbAllotMergeResultResultVo.isSuccess()) { // 如果订单分配失败，则返回订单分配失败原因
                errMsg.append(inquiryApplyAddParamInsert.getEndUser())
                        .append(", ")
                        .append(inquiryApplyAddParamInsert.getModelNo())
                        .append(", ")
                        .append(inqbAllotMergeResultResultVo.getMessage());
                continue;
            }
            InqbAllotMergeResult inqbAllotMergeResult = inqbAllotMergeResultResultVo.getData(); // 拿到分配结果，进行申请表写入
            // 查询申请部门所属分公司，写入中国制造需要用到
            if (StringUtils.isBlank(inquiryApplyAddParamInsert.getInqbDept())) {
                errMsg.append(inquiryApplyAddParamInsert.getEndUser())
                        .append(", ")
                        .append(inquiryApplyAddParamInsert.getModelNo())
                        .append(", ")
                        .append("申请部门不能为空，请补充后重试！");
                continue;
            }
            // bug14973 inqB申请补充需求，发送制造时订单区分改为按照客户所属分公司划分南北方订单
            String deptProvince = getDepartProvince(inquiryApplyAddParamInsert.getEndUser());
            // 写入申请主子表 和 集团采购处理表,bug15029，写入申请表顺序优化，先写入OPS申请主子表，再调接口写入handle表
            inqbTableInsert(inqbAllotMergeResult, inquiryApplyAddParamInsert, deptProvince);
        }
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return ResultVo.failure("处理失败的INQ-B信息" + errMsg.toString());
        }
        return ResultVo.success("INQ-B申请插入成功，共计写入" + list.size() + "项！");
    }

    /**
     * 门户接入 inqB申请接口
     * 新增时，也需要给门户返回全部的分配结果清单
     *
     * @param inqbSalesApplyAddParam
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @DS("opsdb")
    public ResultVo<InqbSalesApplyAddReturn> salesInqbAdd(InqbSalesApplyAddParam inqbSalesApplyAddParam) throws Exception {
        InqbSalesApplyAddReturn salesApplyReturn = BeanCopyUtil.copy(inqbSalesApplyAddParam, InqbSalesApplyAddReturn.class);
        if (Objects.isNull(inqbSalesApplyAddParam)) {
            // 新增失败时，返回失败状态及原因
            salesApplyReturn.setStatus(InqbSalesReturnStatusEnum.ADDERROR.getType());
            salesApplyReturn.setStatusDescription(InqbVerifyMsgEnum.LISTNULL.getDesc());
            return ResultVo.success(salesApplyReturn);
        }
        if (Objects.isNull(inqbSalesApplyAddParam.getExpectedDeliveryDate())) {
            // 新增失败时，返回失败状态及原因
            salesApplyReturn.setStatus(InqbSalesReturnStatusEnum.ADDERROR.getType());
            salesApplyReturn.setStatusDescription(InqbVerifyMsgEnum.LISTNULL.getDesc());
            return ResultVo.success(salesApplyReturn);
        }
        // 校验申请的天数是否符合预期,超出三位的日期时，返回错误
        if (inqbSalesApplyAddParam.getExpectedDeliveryDate() < 1 || inqbSalesApplyAddParam.getExpectedDeliveryDate()> 999) {
            // 新增失败时，返回失败状态及原因
            salesApplyReturn.setStatus(InqbSalesReturnStatusEnum.ADDERROR.getType());
            salesApplyReturn.setStatusDescription(InqbVerifyMsgEnum.DELIVERYDATEUPMAX.getDesc());
            return ResultVo.success(salesApplyReturn);
        }
        // 校验description字段是否大于30个字符，超过时返回错误
        if (StringUtils.isNotBlank(inqbSalesApplyAddParam.getDescription()) && !DataLengthUtil.isValidLength(inqbSalesApplyAddParam.getDescription(),30)) {
            // 新增失败时，返回失败状态及原因
            salesApplyReturn.setStatus(InqbSalesReturnStatusEnum.ADDERROR.getType());
            salesApplyReturn.setStatusDescription(InqbVerifyMsgEnum.REMARKUPMAX.getDesc());
            return ResultVo.success(salesApplyReturn);
        }
        // 调用新增接口
        List<InqbSalesApplyAddParam> callParam = new ArrayList<>(Collections.singletonList(inqbSalesApplyAddParam));
        ResultVo<String> resultVo = inqApplyAdd(callParam);
        if (resultVo.isSuccess()) {
            // 查询分配的明细
            List<OpsInqbApply> opsInqbApplies = opsInqbApplyMapper.getApplyBySourceApplyNo(inqbSalesApplyAddParam);
            // 查询子项列表
            List<OpsInqbDetail> opsInqbDetails = inqbDetailMapper.getInqbDetailByApplyNo(opsInqbApplies.get(0).getInqbApplyNo());
            if (CollectionUtils.isEmpty(opsInqbApplies) || CollectionUtils.isEmpty(opsInqbDetails)) {
                salesApplyReturn.setSourcesApplyNo(inqbSalesApplyAddParam.getSourcesApplyNo());
                salesApplyReturn.setStatus(InqbSalesReturnStatusEnum.ADDERROR.getType());
                salesApplyReturn.setStatusDescription("未查找到对应的申请数据，请重试");
            } else {
                OpsInqbApply opsInqbApply = opsInqbApplies.get(0);
                // 根据门户要求，第一次新增时，不返回整单有效状态
                if (StringUtils.isNotBlank(opsInqbApply.getInqbValidity())) {
                    opsInqbApply.setInqbValidity(null);
                }
                salesApplyReturn.setOpsInqbApply(opsInqbApply);
                salesApplyReturn.setOpsInqbDetailList(opsInqbDetails);
                salesApplyReturn.setInqbStatusDesc(InqbStatusEnum.parse(opsInqbApplies.get(0).getInqbStatus()).getDesc());
                // 新增成功时，返回成功状态
                salesApplyReturn.setStatus(InqbSalesReturnStatusEnum.ADDSUCCESS.getType());
                salesApplyReturn.setStatusDescription(InqbSalesReturnStatusEnum.ADDSUCCESS.getDesc());
                salesApplyReturn.setSplitType(InqbSplitTypeEnum.parse(opsInqbDetails.get(0).getSplitType()).getSplitDesc());
//                salesApplyReturn.setInqbValidityDesc(InqbValidityEnum.parse(opsInqbApplies.get(0).getInqbValidity()).getDesc());
            }
        } else {
            // 新增失败时，返回失败状态及原因
            salesApplyReturn.setSourcesApplyNo(inqbSalesApplyAddParam.getSourcesApplyNo());
            salesApplyReturn.setStatus(InqbSalesReturnStatusEnum.ADDERROR.getType());
            salesApplyReturn.setStatusDescription(resultVo.getMessage());
        }
        return ResultVo.success(salesApplyReturn);
    }

    /**
     * 接口内部处理流程包括，调用订单分配接口，根据返回分配结果，只有采购部分子项需要插入售前货期申请表。（预留强制问询字段，如果勾选则在库部分也进行问询）
     * 接入申请时，先调用订单分配算法（随发），根据分配结果写入申请详情表
     */
    public ResultVo<InqbAllotMergeResult> orderAllocation(InqbSalesApplyAddParam inqbSalesApplyAddParam) {
        // todo 查询最终用户所在所，（最小单位到所/驻在所，为HL时向上提一级），
        OpsCustomer opsCustomer = inquiryApplyCommonService.getCustomerByCustomerNo(inqbSalesApplyAddParam.getEndUser());
        if (opsCustomer == null) {
            log.error(inqbSalesApplyAddParam.getSourcesApplyNo() + "获取客户信息失败");
            ResultVo.failure(inqbSalesApplyAddParam.getSourcesApplyNo() + "：获取客户信息失败");
        }
        ResultVo<String> deptVo = inquiryApplyCommonService.findDeptNoByHRSalesDeptNo(opsCustomer.getHrunitid());
        if (!deptVo.isSuccess()) {
            log.error(inqbSalesApplyAddParam.getSourcesApplyNo() + "获取客户所属部门失败");
            ResultVo.failure("获取客户所属部门失败");
        }
        String deptNo = deptVo.getData();
        OpsInqbOrderAllotRequest allorderAllotRequest = new OpsInqbOrderAllotRequest();
        allorderAllotRequest.setModelNo(inqbSalesApplyAddParam.getModelNo());
        allorderAllotRequest.setDeptNo(deptNo);
        allorderAllotRequest.setEndUser(inqbSalesApplyAddParam.getEndUser());
        allorderAllotRequest.setQuantity(inqbSalesApplyAddParam.getQuantity());
        // 调用订单分配接口，获取分配结果
//        ResultVo<List<OpsInqbOrderAllotResult>> opsInqbOrderAllotResult = wmCKRuleFeignApi.inqBGetCKRule(allorderAllotRequest);
        // 打印参数
        log.info("INQ-B申请接入参数：" + JSON.toJSONString(inqbSalesApplyAddParam));
        // 增加重试机制
        ResultVo<List<OpsInqbOrderAllotResult>> opsInqbOrderAllotResult = this.wmAllotFeign(allorderAllotRequest);
        log.info("门户申请号：" + inqbSalesApplyAddParam.getSourcesApplyNo() + "订单分配接口返回结果：" + JSON.toJSONString(opsInqbOrderAllotResult));
        if (!opsInqbOrderAllotResult.isSuccess()) {
            return ResultVo.failure(opsInqbOrderAllotResult.getMessage());
        }
        List<OpsInqbOrderAllotResult> data = opsInqbOrderAllotResult.getData();
        InqbAllotMergeResult inqbMergeResult = new InqbAllotMergeResult();
        Boolean isCG = false; // 设置默认的采购类别
        // 循环校验出库类别，判断是否需要生成催促问询单
        for (OpsInqbOrderAllotResult inqbOrderAllotResult : data) {
            if (StringUtils.isBlank(inqbOrderAllotResult.getStockType())) {
                log.error(inqbSalesApplyAddParam.getModelNo() + "订单分配接口返回结果出库类别为空");
                return ResultVo.failure("订单分配接口返回结果出库类别为空");
            }
            // 校验出库类别是否为采购
            if (inqbOrderAllotResult.getStockType().equalsIgnoreCase(InqbCommonEnum.STOCKTYPECG.getType())) {
                isCG = true;
            }
        }
        inqbMergeResult.setCG(isCG);
        inqbMergeResult.setStatus(InqbStatusEnum.BUKEWENXUN.getType());
        if (isCG) {
            inqbMergeResult.setStatus(InqbStatusEnum.DAICHULI.getType());
        }
        inqbMergeResult.setProdFlag(data.get(0).getProdFlag());
        inqbMergeResult.setQuantity(inqbSalesApplyAddParam.getQuantity());
        inqbMergeResult.setEndUser(inqbSalesApplyAddParam.getEndUser());
        inqbMergeResult.setModelNo(inqbSalesApplyAddParam.getModelNo());
        inqbMergeResult.setDeptNo(deptNo);
        inqbMergeResult.setDetailList(data); // 返回子项明细
        return ResultVo.success(inqbMergeResult);
    }

    /**
     * 判断规则
     * 1. 非拆分：整型号出在库，分配结果写入详情表，申请状态：不可问询
     * 2. 拆分：	2.1 型号拆分，所有子型号都出库存，不可问询。至少有一个子型号采购，则可以问询，拆分结果均保存于详情表
     * 2.2 数量拆分，有采购部分，则全数量进行问询，详细表只存全数量采购拆分
     */
    @Transactional(rollbackFor = Exception.class)
    @DS("opsdb")
    public int inqbTableInsert(InqbAllotMergeResult inqbAllotMergeResult, InqbSalesApplyAddParam inqbSalesApplyAddParam, String deptProvince) {
        int detailReasult = 0;
        OpsInqbApplyDO opsInqbApply = new OpsInqbApplyDO();
        if (StringUtils.isNotBlank(inqbSalesApplyAddParam.getSourcesApplyNo())) {
            opsInqbApply.setSourcesApplyNo(inqbSalesApplyAddParam.getSourcesApplyNo()); // 设置源申请号
        }
        opsInqbApply.setBatchNo(inqbSalesApplyAddParam.getBatchNo());
        opsInqbApply.setDataSources(inqbSalesApplyAddParam.getDataSources());
        opsInqbApply.setModelNo(inqbSalesApplyAddParam.getModelNo());
        opsInqbApply.setQuantity(inqbSalesApplyAddParam.getQuantity());
        opsInqbApply.setEndUser(inqbSalesApplyAddParam.getEndUser());
        opsInqbApply.setExpectedDeliveryDate(inqbSalesApplyAddParam.getExpectedDeliveryDate());
        opsInqbApply.setDescription(inqbSalesApplyAddParam.getDescription());
        opsInqbApply.setInqbDate(inqbSalesApplyAddParam.getInqbDate());
        opsInqbApply.setInqbType(inqbSalesApplyAddParam.getInqbType());
        opsInqbApply.setInqbClassify(inqbSalesApplyAddParam.getInqbClassify());
        opsInqbApply.setInqbDept(inqbSalesApplyAddParam.getInqbDept());
        opsInqbApply.setInqbUser(inqbSalesApplyAddParam.getInqbUser());
        opsInqbApply.setInqbUserName(inqbSalesApplyAddParam.getInqbUserName());
        opsInqbApply.setCreateTime(new Date());
        opsInqbApply.setCreateUser(inqbSalesApplyAddParam.getInqbUser());
        opsInqbApply.setInqbStatus(InqbStatusEnum.CUICUZHONG.getType());
        opsInqbApply.setUserDept(inqbAllotMergeResult.getDeptNo());
        opsInqbApply.setInqbValidity(InqbValidityEnum.INVALID.getType()); // 初始写入时，默认的整单状态为失效
        // 生成申请号
        ResultVo<String> resultVo = inquiryApplyCommonService.generateInqbApplyNo();
        if (resultVo.isSuccess()) { // todo 申请号获取失败时，是否直接抛出异常
            opsInqbApply.setInqbApplyNo(resultVo.getData());
        }
        opsInqbApplyMapper.insert(opsInqbApply); //写入申请表数据
        // 先写子表数据，再写主表
        OpsInqbDetailDO opsInqbDetail;
        int itemNo = 0;
        Boolean detailCompletion = false; // 判断子项是否已经生成完毕
        List<OpsInqbOrderAllotResult> detailList = inqbAllotMergeResult.getDetailList();
        // 子项号顺序生成（在库采购排序）
        detailList.sort(Comparator.comparing(OpsInqbOrderAllotResult::getStockType));
        // 根据拆分情况，循环写入detail表中
        for (OpsInqbOrderAllotResult opsInqbOrderAllotResult : detailList) {
            if (!detailCompletion) {
                opsInqbDetail = new OpsInqbDetailDO();
                itemNo++;
                opsInqbDetail.setInqbApplyNo(opsInqbApply.getInqbApplyNo());
                opsInqbDetail.setItemNo(itemNo);
                opsInqbDetail.setSplitType(opsInqbOrderAllotResult.getProdFlag());
                if (opsInqbOrderAllotResult.getBomid() != null) {
                    opsInqbDetail.setBomId(opsInqbOrderAllotResult.getBomid().toString());
                }
                opsInqbDetail.setStockCode(opsInqbOrderAllotResult.getStockType());
                opsInqbDetail.setModelNo(opsInqbOrderAllotResult.getModelNo());
                opsInqbDetail.setQuantity(opsInqbOrderAllotResult.getQuantity());
                opsInqbDetail.setEndUser(inqbSalesApplyAddParam.getEndUser());
                opsInqbDetail.setUserDept(inqbAllotMergeResult.getDeptNo());
                opsInqbDetail.setExpectedDeliveryDate(inqbSalesApplyAddParam.getExpectedDeliveryDate());
                opsInqbDetail.setCreateTime(new Date());
                opsInqbDetail.setCreateUser(inqbSalesApplyAddParam.getInqbUser());
                opsInqbDetail.setStatus(InqbDetailStatusEnum.INVALID.getType()); // 记录使用状态，默认为不可用状态,只有采购的部分才为可用
                opsInqbDetail.setHandleResult(InqbDetailStatusEnum.HANDLEINVALID.getType().toString()); // 是否生成问询单，默认为不生成，有采购时才生成
                // 数量拆分，有采购部分，则全数量进行问询，详细表只存全数量采购拆分
                if (inqbAllotMergeResult.getCG() && inqbAllotMergeResult.getProdFlag().equals(OrderSplitTypeEnum.ASSQTY.getSplitType())) {
                    opsInqbDetail.setQuantity(inqbSalesApplyAddParam.getQuantity());
                    // 采购部分去查询 product——delivery表的主供应商
                    List<ProductDelivery> deliveryList = inqbOpscoreMapper.getProductDelivery(opsInqbOrderAllotResult.getModelNo());
                    if (CollectionUtils.isNotEmpty(deliveryList)) {
                        opsInqbDetail.setSupplierCode(deliveryList.get(0).getSupplyid());
                        opsInqbDetail.setHandleResult(InqbDetailStatusEnum.HANDLEVALID.getType().toString());
                        opsInqbDetail.setStatus(InqbDetailStatusEnum.VALID.getType()); // 记录使用状态，0：否，1：是
                        opsInqbDetail.setQuantity(inqbSalesApplyAddParam.getQuantity());
                    }
                    detailCompletion = true; //跳出循环，详细表只存全数量采购拆分
                } else if (opsInqbOrderAllotResult.getStockType().equalsIgnoreCase(InqbCommonEnum.STOCKTYPECG.getType())) { // 非数量拆分，按照正常规则进行推送
                    // 采购部分去查询 product——delivery表的主供应商
                    List<ProductDelivery> deliveryList = inqbOpscoreMapper.getProductDelivery(opsInqbOrderAllotResult.getModelNo());
                    if (CollectionUtils.isNotEmpty(deliveryList)) {
                        opsInqbDetail.setSupplierCode(deliveryList.get(0).getSupplyid());
                        opsInqbDetail.setHandleResult(InqbDetailStatusEnum.HANDLEVALID.getType().toString());
                        opsInqbDetail.setStatus(InqbDetailStatusEnum.VALID.getType()); // 记录使用状态，0：否，1：是
                    }
                }
                detailReasult = detailReasult + inqbDetailMapper.insert(opsInqbDetail);
            }
        }
        // bug15029,子表写入完成后，再写入hadle表中，避免出现事务不一致情况
        List<OpsInqbDetailDO> opsInqbDetailDos = inqbDetailMapper.findInqbDetailDOByApplyNo(opsInqbApply.getInqbApplyNo());
        if (CollectionUtils.isNotEmpty(opsInqbDetailDos)) {
            // 调用集团采购接口,写入handle表
            for (OpsInqbDetailDO opsInqbDetailData : opsInqbDetailDos) {
                if (StringUtils.isNotBlank(opsInqbDetailData.getSupplierCode())) {
                    // todo,针对不同供应商，进行问询分类码的转换
                    if (StringUtils.isNotBlank(opsInqbApply.getInqbClassify())) {
                        System.out.println("问询分类码：" + opsInqbApply.getInqbClassify());
                    }
                    // 采购部分，需要写到适配器对接表中，批量调用还是单条循环调用
                    OpsInqbHandle opsInqbHandle = opsInqbHandle = new OpsInqbHandle();
                    opsInqbHandle.setSupplierCode(opsInqbDetailData.getSupplierCode());
                    opsInqbHandle.setModelNo(opsInqbDetailData.getModelNo());
                    opsInqbHandle.setQuantity(opsInqbDetailData.getQuantity());
                    opsInqbHandle.setEndUser(opsInqbDetailData.getEndUser());
                    opsInqbHandle.setExpectedDeliveryDate(inqbSalesApplyAddParam.getExpectedDeliveryDate());
                    opsInqbHandle.setDescription(inqbSalesApplyAddParam.getDescription());
                    opsInqbHandle.setInqbDate(inqbSalesApplyAddParam.getInqbDate());
                    opsInqbHandle.setInqbType(inqbSalesApplyAddParam.getInqbType());
                    opsInqbHandle.setInqbClassify(inqbSalesApplyAddParam.getInqbClassify());
                    opsInqbHandle.setInqbDept(inqbSalesApplyAddParam.getInqbDept());
                    opsInqbHandle.setInqbUser(inqbSalesApplyAddParam.getInqbUser());
                    opsInqbHandle.setInqbUserName(inqbSalesApplyAddParam.getInqbUserName());
                    // 对接供应商时，期望的货期天数及期望货期时间都留存，根据申请日期+工作日天数进行计算
                    Date expectedDeliveryParse;
                    if (opsInqbDetailData.getSupplierCode().equalsIgnoreCase("JP")) {
                        expectedDeliveryParse = inquiryWorkDayMonthMapper.getJPWorkDayDiff(DateUtil.dateToString(inqbSalesApplyAddParam.getInqbDate()), inqbSalesApplyAddParam.getExpectedDeliveryDate());
                    } else {
                        expectedDeliveryParse = inquiryWorkDayMonthMapper.getCNWorkDayDiff(DateUtil.dateToString(inqbSalesApplyAddParam.getInqbDate()), inqbSalesApplyAddParam.getExpectedDeliveryDate());
                    }
                    if (expectedDeliveryParse != null) {
                        opsInqbHandle.setExpectedDelivery(expectedDeliveryParse);
                    }
                    opsInqbHandle.setDeptProvince(deptProvince);
                    // 先写入detail表中，再写入handle表中
                    // todo 调用集团采购接口，并记录返回的taskno
                    ResultVo<String> groupResult = inqbGroupPurchaseFeignApi.addInqbHandle(opsInqbHandle);
                    if (groupResult.isSuccess()) {
                        // 回更taskno
                        opsInqbDetailData.setTaskNo(groupResult.getData());
                        inqbDetailMapper.updateById(opsInqbDetailData);
                    } else { // 未配置供应商时，保留拆分记录，但是不生成问询单
                        log.error("订单号" + groupResult.getMessage());
                        opsInqbDetailData.setRemark(groupResult.getMessage());
                        if (groupResult.getMessage().equals(InqbVerifyMsgEnum.SUPPILYCONFIGNULL.getDesc())) {
                            opsInqbDetailData.setHandleResult(InqbDetailStatusEnum.HANDLEINVALID.getType().toString());
                            opsInqbDetailData.setStatus(InqbDetailStatusEnum.INVALID.getType()); // 记录使用状态，0：否，1：是
                            inqbDetailMapper.updateById(opsInqbDetailData);
                        }
                    }
                }
            }
        }
        return detailReasult;
    }

    @Override
    public List<OpsInqbCodeConfig> getInqbCodeConfig() throws Exception {
        // 2024-11-13优化INQB的催促原因配置，存在redis中，直接从redis中取，每天凌晨更新一次，避免每次都从数据库中取
        List<OpsInqbCodeConfig> opsInqbCodeConfigs = null;
        ResultVo<List<OpsInqbCodeConfig>> resultVo = inquiryApplyCommonService.getInqbCodeConfigFromRedis();
        if(resultVo.isSuccess()) {
            opsInqbCodeConfigs = resultVo.getData();
        }
        return opsInqbCodeConfigs;
    }

    /**
     * 门户提交校验的初始接口
     *
     * @param inqbSalesApplyVerifyReurn
     * @return
     * @throws Exception
     */
    @Override
    public ResultVo<InqbApplyVerifyReurn> salesInqbValid(InqbApplyVerifyReurn inqbSalesApplyVerifyReurn) throws Exception {
        // 先进行初始的提交校验，工作日天数、次数等
        ResultVo<String> verifyResult = inqbVerify(inqbSalesApplyVerifyReurn.getModelNo(), inqbSalesApplyVerifyReurn.getEndUser(), inqbSalesApplyVerifyReurn.getQuantity());
        if (!verifyResult.isSuccess()) { // 返回校验失败原因
            inqbSalesApplyVerifyReurn.setCanPress(false);
            inqbSalesApplyVerifyReurn.setCheckFailureMsg(verifyResult.getMessage());
            return ResultVo.success(inqbSalesApplyVerifyReurn);
        }
        // 校验整型号是否有库存
        List<InqbInventoryVerifyDto>  inventoryVerifyDtos =  inqbOpscoreMapper.getInventoryQty(inqbSalesApplyVerifyReurn.getModelNo(),inqbSalesApplyVerifyReurn.getQuantity(),inqbSalesApplyVerifyReurn.getEndUser());
        if (!CollectionUtils.isEmpty(inventoryVerifyDtos)) {
            inqbSalesApplyVerifyReurn.setCanPress(false);
            inqbSalesApplyVerifyReurn.setCheckFailureMsg(InqbVerifyMsgEnum.INVENTORYLESS.getDesc());
            return ResultVo.success(inqbSalesApplyVerifyReurn);
        }
        // 整型号无库存时，判断是否拆分
        List<ProductBomDetail>  bomDetails =  inqbOpscoreMapper.getMdelNoBom(inqbSalesApplyVerifyReurn.getModelNo());
        if (!CollectionUtils.isEmpty(bomDetails)) {
            // 循环判断拆分型号，是否都存在库存，都有库存时则校验失败
            int inventorySize = 0;
            for (ProductBomDetail productBomDetail : bomDetails) {
                // bug15067 INQB 拆分库存校验问题,需要按照拆分子项的配套*请求数量进行校验
                int avaQty = productBomDetail.getQuantity() * inqbSalesApplyVerifyReurn.getQuantity();
                List<InqbInventoryVerifyDto>  inventoryVerifyDto =  inqbOpscoreMapper.getInventoryQty(productBomDetail.getModelno(),avaQty,inqbSalesApplyVerifyReurn.getEndUser());
                if (!CollectionUtils.isEmpty(inventoryVerifyDto)) {
                    // 存在库存时，记录条数
                    inventorySize = inventorySize + 1;
                }
            }
            if (inventorySize == bomDetails.size()) {
                inqbSalesApplyVerifyReurn.setCanPress(false);
                inqbSalesApplyVerifyReurn.setCheckFailureMsg(InqbVerifyMsgEnum.INVENTORYLESS.getDesc());
                return ResultVo.success(inqbSalesApplyVerifyReurn);
            }
        }
        // 都没有库存时，回复可以催促
        inqbSalesApplyVerifyReurn.setCanPress(true);
        return ResultVo.success(inqbSalesApplyVerifyReurn);
    }

    public String getDepartProvince(String customerno) {
        String returnProvince = InqbDeptProvinceEnum.OTHERProvince.getDesc();
        //  bug14973 inqB申请补充需求，发送制造时订单区分改为按照客户所属分公司划分南北方订单
        OpsCustomer opsCustomer = inquiryApplyCommonService.getCustomerByCustomerNo(customerno);
        if (opsCustomer != null) {
            String subjectivities = opsCustomer.getTradesubjectid(); // 获取客户所属交易主体
            Set<String> southList = new HashSet<String>(InqbDeptProvinceEnum.southList()); // 南方
            Set<String> northList = new HashSet<String>(InqbDeptProvinceEnum.northList()); // 北方
            if (southList.contains(subjectivities)) {
                returnProvince = InqbDeptProvinceEnum.SHProvince.getDesc();
            } else if (northList.contains(subjectivities)) {
                returnProvince = InqbDeptProvinceEnum.BJProvince.getDesc();
            } else {
                returnProvince = InqbDeptProvinceEnum.OTHERProvince.getDesc();
            }
        }
//        ResultVo<OrganizationDto> resultVo = inquiryApplyCommonService.getHrOrganMasterInfoByCode(inqbDept);
//        String returnProvince = InqbDeptProvinceEnum.OTHERProvince.getDesc();
//        if (resultVo.isSuccess()) {
//            OrganizationDto organizationDto = resultVo.getData();
//            Set<String> southList = new HashSet<String>(InqbDeptProvinceEnum.southList()); // 南方
//            Set<String> northList = new HashSet<String>(InqbDeptProvinceEnum.northList()); // 北方
//            if (southList.contains(organizationDto.getCompanyName()) || southList.contains(organizationDto.getParentName())) {
//                returnProvince = InqbDeptProvinceEnum.SHProvince.getDesc();
//            } else if (northList.contains(organizationDto.getCompanyName())) {
//                returnProvince = InqbDeptProvinceEnum.BJProvince.getDesc();
//            } else {
//                returnProvince = InqbDeptProvinceEnum.OTHERProvince.getDesc();
//            }
//        }
        return returnProvince;
    }

    /**
     * 调用WM接口，增加重试机制防止出现第一次调用超时的情况
     * @param allorderAllotRequest
     * @return
     */
    public ResultVo<List<OpsInqbOrderAllotResult>> wmAllotFeign(OpsInqbOrderAllotRequest allorderAllotRequest) {
        for (int i = 0; i < InqbConstants.TRY_COUNT; i++) {
            try {
                return wmCKRuleFeignApi.inqBGetCKRule(allorderAllotRequest);
            } catch (Exception e) {
                log.error("调用WM接口失败，第" + (i + 1) + "次重试", e);
                if (i < 2) {
                    try {
                        Thread.sleep(InqbConstants.RETRY_INTERVAL * (1 << i));  // 指数退避
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }
        return ResultVo.failure("调用接口失败,已达到最大重试次数");
    }
}
