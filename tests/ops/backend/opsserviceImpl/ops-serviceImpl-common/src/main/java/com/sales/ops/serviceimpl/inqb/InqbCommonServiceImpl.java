package com.sales.ops.serviceimpl.inqb;

import com.alibaba.fastjson.JSONObject;
import com.sales.ops.db.dao.OpsInqbApplyMapper;
import com.sales.ops.db.dao.OpsInqbDetailMapper;
import com.sales.ops.db.entity.OpsInqbApply;
import com.sales.ops.db.entity.OpsInqbApplyExample;
import com.sales.ops.db.entity.OpsInqbDetail;
import com.sales.ops.db.entity.OpsInqbDetailExample;
import com.sales.ops.dto.inqb.*;
import com.sales.ops.feign.inqb.InqbJobFeignApi;
import com.sales.ops.service.inqb.InqbCommonService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class InqbCommonServiceImpl implements InqbCommonService {


    @Resource
    private OpsInqbApplyMapper opsInqbApplyMapper;

    @Resource
    private OpsInqbDetailMapper opsInqbDetailMapper;

    @Resource
    private InqbJobFeignApi inqbJobFeignApi;

    /**
     * 1.获取INQB申请信息明细
     * 根据提供的INQB申请号，获取INQB申请信息明细
     *
     * @param applyNo
     */
    @Override
    public ResultVo<OpsInqbInfo> getInqbApplyInfo(String applyNo) {
        if (StringUtils.isBlank(applyNo)) {
            return ResultVo.failure("请求的INQB为空，请修正后重试");
        }
        OpsInqbInfo opsInqbInfo = new OpsInqbInfo();
        OpsInqbApplyExample applyExample = new OpsInqbApplyExample();
        OpsInqbApplyExample.Criteria criteria = applyExample.createCriteria().andInqbApplyNoEqualTo(applyNo);
        List<OpsInqbApply> opsInqbApplies = opsInqbApplyMapper.selectByExample(applyExample); //查询主表信息
        if (CollectionUtils.isEmpty(opsInqbApplies)) {
            return ResultVo.failure("获取INQB信息失败，未查询到INQB申请信息！");
        }
        OpsInqbApply inqbApplyReturn = opsInqbApplies.get(0);
        if (!(inqbApplyReturn.getInqbStatus().equals(InqbStatusEnum.YIDAFU.getType()) || inqbApplyReturn.getInqbStatus().equals(InqbStatusEnum.CUICUZHONG.getType()))) {
            return ResultVo.failure("获取INQB信息失败，当前INQB申请状态不可被使用！");
        }
        // 查询申请子表信息
        OpsInqbDetailExample detailExample = new OpsInqbDetailExample();
        // 筛选子表中 实际采购的，且可用的明细项
        OpsInqbDetailExample.Criteria detailCriteria = detailExample.createCriteria().andInqbApplyNoEqualTo(applyNo).andHandleResultEqualTo(InqbDetailStatusEnum.HANDLEVALID.getType()).andStatusEqualTo(InqbDetailStatusEnum.VALID.getType());
        List<OpsInqbDetail> opsInqbDetails = opsInqbDetailMapper.selectByExample(detailExample);
        if (CollectionUtils.isEmpty(opsInqbDetails)) {
            return ResultVo.failure("获取INQB信息失败，未查询到有效的INQB申请明细信息！");
        }
        opsInqbDetails.removeIf(opsInqbDetail -> !opsInqbDetail.getStatus().equals(InqbDetailStatusEnum.VALID.getType()));
        opsInqbInfo.setOpsInqbApply(inqbApplyReturn);
        opsInqbInfo.setOpsInqbDetailList(opsInqbDetails);
        return ResultVo.success(opsInqbInfo);
    }

    /**
     * 2.根据传入信息，更新INQ-B表的使用状态，并写入使用数量。
     *  todo 子项使用一次，即失效？不管是否使用完整数量？整单也变更为已使用
     *  todo 使用信息是否需要回传门户？
     *
     * @param opsInqbUsageInfo
     */
    @Override
    public ResultVo<String> updateInqbUsageInfo(OpsInqbUsageInfo opsInqbUsageInfo) {
        try {
            ResultVo<String> updateReasultVo = updateInqbTable(opsInqbUsageInfo);
            if (updateReasultVo.isSuccess()) {
                List<String> applyNoList = new ArrayList<>(Collections.singletonList(opsInqbUsageInfo.getInqbApplyNo()));
                ResultVo<String> callbackResultVo = inqbJobFeignApi.callbackSalesToTask(applyNoList);
                if (!callbackResultVo.isSuccess()) {
                    log.error("INQB回传门户状态失败：", callbackResultVo.getMessage());
                }
            }
            return ResultVo.success("INQB占用成功");
        } catch (Exception e) {
            log.error("INQB更新使用信息失败：", e.getMessage());
            return ResultVo.failure("更新INQB使用信息失败:" + e.getMessage());
        }
    }

    /**
     * 由于事务限制，需要将更新inqb表的操作放在外部调用，先提交事务后，再回传给门户最新的信息
     *
     * @param opsInqbUsageInfo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> updateInqbTable(OpsInqbUsageInfo opsInqbUsageInfo) {
        if (StringUtils.isBlank(opsInqbUsageInfo.getInqbApplyNo()) || opsInqbUsageInfo.getItemNo() == null) {
            return ResultVo.failure("请求的INQB申请号信息为空，请修正后重试");
        }
        // 查询申请子表信息
        OpsInqbDetailExample detailExample = new OpsInqbDetailExample();
        OpsInqbDetailExample.Criteria detailCriteria = detailExample.createCriteria().andInqbApplyNoEqualTo(opsInqbUsageInfo.getInqbApplyNo()).andItemNoEqualTo(opsInqbUsageInfo.getItemNo()); //根据申请号+子项号查询子表信息
        List<OpsInqbDetail> opsInqbDetailList = opsInqbDetailMapper.selectByExample(detailExample);
        if (CollectionUtils.isEmpty(opsInqbDetailList)) {
            return ResultVo.failure("占用失败，未查询到INQB申请号的明细信息！");
        }
        OpsInqbDetail opsInqbDetail = opsInqbDetailList.get(0);
        if (opsInqbDetail.getStatus().equals(InqbDetailStatusEnum.INVALID.getType()) || opsInqbDetail.getHandleResult().equals(InqbDetailStatusEnum.HANDLEINVALID.getType())) {
            return ResultVo.failure("占用失败，当前INQB申请明细状态不可被占用！");
        }
        int updateCount = opsInqbDetail.getUseQty() + opsInqbUsageInfo.getUseQty();
        if (opsInqbDetail.getUseQty() == opsInqbDetail.getQuantity() || updateCount > opsInqbDetail.getQuantity()) {
            return ResultVo.failure("占用失败，当前INQB申请明细已被占用！");
        }
        // 校验成功，查询主表信息
        OpsInqbApplyExample applyExample = new OpsInqbApplyExample();
        OpsInqbApplyExample.Criteria criteria = applyExample.createCriteria().andInqbApplyNoEqualTo(opsInqbUsageInfo.getInqbApplyNo());
        List<OpsInqbApply> opsInqbApplies = opsInqbApplyMapper.selectByExample(applyExample); //查询主表信息
        if (CollectionUtils.isEmpty(opsInqbApplies)) {
            return ResultVo.failure("占用失败,未查询到INQB的申请信息！");
        }
        OpsInqbApply inqbApplyReturn = opsInqbApplies.get(0);
        if (!inqbApplyReturn.getInqbStatus().equals(InqbStatusEnum.YIDAFU.getType())) {
            return ResultVo.failure("占用失败，当前INQB申请状态不可被使用！");
        }
        String infoJson = JSONObject.toJSONString(opsInqbUsageInfo); //请求转换为JSON存储
        log.info("INQB使用成功，详情信息：" + infoJson);
        // 校验成功，更新主子表信息
        OpsInqbDetail opsInqbDetailUpdate = new OpsInqbDetail();
        opsInqbDetailUpdate.setId(opsInqbDetail.getId());
        opsInqbDetailUpdate.setUseQty(updateCount);
        opsInqbDetailUpdate.setStatus(InqbDetailStatusEnum.INVALID.getType());
        opsInqbDetailUpdate.setUpdateTime(new Date());
        opsInqbDetailUpdate.setUpdateUser("OrderUse");
        opsInqbDetailUpdate.setRemark(infoJson);
        opsInqbDetailMapper.updateByPrimaryKeySelective(opsInqbDetailUpdate);
        // 更新主表信息
        OpsInqbApply opsInqbApplyUpdate = new OpsInqbApply();
        opsInqbApplyUpdate.setId(inqbApplyReturn.getId());
        opsInqbApplyUpdate.setInqbStatus(InqbStatusEnum.YISHIYONG.getType());
        opsInqbApplyUpdate.setUpdateTime(new Date());
        opsInqbApplyUpdate.setUpdateUser("OrderUse");
        opsInqbApplyUpdate.setRemark(infoJson);
        if (StringUtils.isNotBlank(inqbApplyReturn.getInqbValidity()) && inqbApplyReturn.getInqbValidity().equals(InqbValidityEnum.VALID.getType())) {
            // bug14986 INQB有效性，当状态变为已回复时，更新主表的有效性状态
            opsInqbApplyUpdate.setInqbValidity(InqbValidityEnum.INVALID.getType());
        }
        opsInqbApplyMapper.updateByPrimaryKeySelective(opsInqbApplyUpdate);
        return ResultVo.success("INQB占用成功");
    }


}
