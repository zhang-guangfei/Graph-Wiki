package com.smc.smccloud.web.rpc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.mapper.sampleorder.SampleBalApplyMapper;
import com.smc.smccloud.model.order.SmsSendOpsDetailTaskBean;
import com.smc.smccloud.model.sampleorder.*;
import com.smc.smccloud.service.SampleBalService;
import com.smc.smccloud.service.SampleOrderApplyFeignApi;
import com.smc.smccloud.service.SampleOrderApplyService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SampleOrderApplyFeignClient implements SampleOrderApplyFeignApi {

    @Resource
    private SampleOrderApplyService sampleOrderApplyService;

    @Resource
    private SampleBalService sampleBalService;

    @Resource
    private SampleBalApplyMapper sampleBalApplyMapper;

    @Override
    public ResultVo<List<SampleOrderReturnDTO>> addSampleOrder(SampleOrderDTO sampleOrderDTO) {
        return sampleOrderApplyService.addSampleOrder(sampleOrderDTO);
    }

//    @Override
//    public ResultVo<String> addSampleOrderByList(List<SampleOrderVO> sampleOrderVOList) {
//        return sampleOrderApplyService.addSampleOrderByList(sampleOrderVOList);
//    }

    @Override
    public ResultVo<String> autoGenerateSampleBalData() {
        return sampleOrderApplyService.autoGenerateSampleBalData();
    }

    @Override
    public ResultVo<String> autoCreateOrerBySampleOrder() {
        return sampleOrderApplyService.autoCreateOrerBySampleOrder();
    }

    @Override
    public ResultVo<String> autoOrderCarryTurn() {
        return sampleOrderApplyService.autoOrderCarryTurn();
    }

    @Override
    public ResultVo<String> autoInsertSales() {
        return sampleOrderApplyService.autoInsertSales();
    }

    @Override
    public ResultVo<String> autoToSalesInvoice() {
       return sampleOrderApplyService.autoToSalesInvoice();
    }

    @Override
    public ResultVo<String> insertSampleBalApply(SampleBalApplyVO info) {

        return sampleBalService.insertSampleBalApply(info);
    }

    @Override
    public ResultVo<String> findHandSampleBalApply(FindHandSampleBalHandVO vo) {
        return sampleBalService.findHandSampleBalApply(vo);
    }

    @Override
    public ResultVo<String> checkRcvQty(CheckRcvQtyVO checkRcvQtyVO) {
        return sampleBalService.checkRcvQty(checkRcvQtyVO);
    }

    @Override
    public ResultVo<String> insertReturnOrder(SmsSendOpsDetailTaskBean bean) {
        return sampleBalService.insertReturnOrder(bean);
    }

    @Override
    public ResultVo<String> sureApplySampleBalApi(List<String> ids) {
        return sampleBalService.sureApplySampleBal(ids);
    }

    @Override
    public ResultVo<SampleBalApplyVO> getSampleBalApplyInfo(SampleBalApplyVO info) {
        LambdaQueryWrapper<SampleBalApplyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SampleBalApplyDO::getSampleBalApplyNo,info.getSampleBalApplyNo())
                .eq(SampleBalApplyDO::getOrderNo,info.getOrderNo())
                .orderByDesc(SampleBalApplyDO::getId);
        List<SampleBalApplyDO> sampleBalApplyDOS = sampleBalApplyMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(sampleBalApplyDOS)) {
            return ResultVo.failure("暂无数据");
        }
        return ResultVo.success(BeanCopyUtil.copy(sampleBalApplyDOS.get(0),SampleBalApplyVO.class));
    }
}
