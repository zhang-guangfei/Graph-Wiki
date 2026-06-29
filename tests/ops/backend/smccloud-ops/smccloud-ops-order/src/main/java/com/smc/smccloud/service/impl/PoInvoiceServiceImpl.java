package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.purchase.PurchaseReplyInfo;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.WmPurchaseFeignApi;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.mapper.impInvoice.PoInvoiceDetailMapper;
import com.smc.smccloud.mapper.impInvoice.PoInvoiceMasterMapper;
import com.smc.smccloud.model.Constants;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.adapter.order.POOrderNODTO;
import com.smc.smccloud.model.adapter.order.POOrderNOVO;
import com.smc.smccloud.model.enums.ImpInvoiceMasterStatusEnum;
import com.smc.smccloud.model.enums.InvoiceTypeEnum;
import com.smc.smccloud.model.enums.OpsPoInvoiceStatusEnum;
import com.smc.smccloud.model.invoice.*;
import com.smc.smccloud.model.order.OpsImpDataDO;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.supplier.SupplierVo;
import com.smc.smccloud.service.*;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author edp02 @Date 2021/12/22 16:36
 */
@Slf4j
@Service
public class PoInvoiceServiceImpl implements PoInvoiceService {

    @Resource
    private PoInvoiceMasterMapper poInvoiceMasterMapper;
    @Resource
    private PoInvoiceDetailMapper poInvoiceDetailMapper;
    @Resource
    private HttpServletResponse response;
    @Resource
    private WmPurchaseFeignApi wmPurchaseFeignApi;
    @Resource
    private ImpdataMapper impdataMapper;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private ImpInvoiceDetailPackMapper impInvoiceDetailPackMapper;
    @Resource
    private ImpInvoiceMasterMapper impInvoiceMasterMapper;
    @Resource
    private CommonMapper commonMapper;
    @Resource
    private VSalesManuorderMapper vSalesManuorderMapper;
    @Resource
    private SalesImportService salesImportService;
    @Resource
    private OPSTExportRequestToSalesMapper opstExportRequestToSalesMapper;
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Resource
    private OPSVExportInvoicePriceToSalesMapper opSVExportInvoicePriceToSalesMapper;
    @Resource
    private PoInvoiceService poInvoiceService;
    @Resource
    private ImpInvoiceDetailMapper impInvoiceDetailMapper;
    @Resource
    private OpsPurchaseInvoiceMapper opsPurchaseInvoiceMapper;
    @Resource
    private DictCommonService dictCommonService;
    @Resource
    private OpsImpDataMapper opsImpDataMapper;

    @Override
    public ResultVo<PageInfo<OpsPoInvoiceVO>> listPoInvoice(PoInvoiceMasterRequest request) {
        LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapper = getPoInvoiceQryWrapper(request);
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<OpsPoInvoiceDO> list = poInvoiceMasterMapper.selectList(queryWrapper);
        PageInfo<OpsPoInvoiceDO> pageInfo = PageInfo.of(list);
        PageInfo<OpsPoInvoiceVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, OpsPoInvoiceVO.class);
        return ResultVo.success(voPageInfo);
    }

    //    <!--add by WuWeiDong 20230109 bug 9276 -->
    @Override
    public ResultVo<String> updatePoInvoiceDetailCost(PoInvoiceDetailVO poInvoiceDetailVO) {
        OpsPoInvoiceVO queryPoInvoiceVO = poInvoiceService.getOpsPoInvoice(poInvoiceDetailVO.getInvoiceId()).getData();
        OpsPoInvoiceDO queryPoInvoiceDO = BeanCopyUtil.copy(queryPoInvoiceVO, OpsPoInvoiceDO.class);
        if (PublicUtil.isEmpty(queryPoInvoiceDO)) {
            return ResultVo.failure(poInvoiceDetailVO.getInvoiceId().toString() + "发票Id不存在！");
        } else if (queryPoInvoiceDO.getStatus() >= 3) {
            String statusDesc = dictCommonService.getDataTypeCodesInfo("2043", queryPoInvoiceDO.getStatus().toString()).getData().getCodeName();
            return ResultVo.failure(poInvoiceDetailVO.getInvoiceId().toString() + "当前状态不能更新！状态：" + statusDesc);
        } else {
            return poInvoiceService.updatePoInvoiceDetailFee(poInvoiceDetailVO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> updatePoInvoiceDetailFee(PoInvoiceDetailVO poInvoiceDetailVO) {
        LambdaUpdateWrapper<PoInvoiceDetailDO> poInvoiceDetailDOLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        if (PublicUtil.isNotEmpty(poInvoiceDetailVO.getId()) && poInvoiceDetailVO.getId() > 0) {
            poInvoiceDetailDOLambdaUpdateWrapper.eq(PoInvoiceDetailDO::getId, poInvoiceDetailVO.getId());

        } else {
            poInvoiceDetailDOLambdaUpdateWrapper.eq(PoInvoiceDetailDO::getInvoiceId, poInvoiceDetailVO.getInvoiceId())
                    .eq(PoInvoiceDetailDO::getInvoiceNo, poInvoiceDetailVO.getInvoiceNo())
                    .eq(PoInvoiceDetailDO::getOrderNo, poInvoiceDetailVO.getOrderNo());
        }
        poInvoiceDetailDOLambdaUpdateWrapper.ne(PoInvoiceDetailDO::getStatus, "9")
                .set(PoInvoiceDetailDO::getPrice, Optional.ofNullable(poInvoiceDetailVO.getPrice()).orElse(BigDecimal.ZERO))
                .set(PoInvoiceDetailDO::getAmount, Optional.ofNullable(poInvoiceDetailVO.getAmount()).orElse(BigDecimal.ZERO))
                .set(PoInvoiceDetailDO::getPriceRmb, Optional.ofNullable(poInvoiceDetailVO.getPriceRmb()).orElse(BigDecimal.ZERO))
                .set(PoInvoiceDetailDO::getAmountRmb, Optional.ofNullable(poInvoiceDetailVO.getAmountRmb()).orElse(BigDecimal.ZERO))
                .set(PoInvoiceDetailDO::getCustomsFee, Optional.ofNullable(poInvoiceDetailVO.getCustomsFee()).orElse(BigDecimal.ZERO))
                .set(PoInvoiceDetailDO::getTransFee, Optional.ofNullable(poInvoiceDetailVO.getTransFee()).orElse(BigDecimal.ZERO))
                .set(PoInvoiceDetailDO::getExciseTax, Optional.ofNullable(poInvoiceDetailVO.getExciseTax()).orElse(BigDecimal.ZERO))
                .set(PoInvoiceDetailDO::getOtherFee, Optional.ofNullable(poInvoiceDetailVO.getOtherFee()).orElse(BigDecimal.ZERO))
                .set(PoInvoiceDetailDO::getPriceTotal, Optional.ofNullable(poInvoiceDetailVO.getPriceTotal()).orElse(BigDecimal.ZERO))
                .set(PoInvoiceDetailDO::getAmountTotal, Optional.ofNullable(poInvoiceDetailVO.getAmountTotal()).orElse(BigDecimal.ZERO))
                .set(StringUtils.isNotBlank(poInvoiceDetailVO.getNonCommercial()), PoInvoiceDetailDO::getNonCommercial, poInvoiceDetailVO.getNonCommercial())
                .set(PoInvoiceDetailDO::getRemark, "修改订单成本！")
                .set(PoInvoiceDetailDO::getUpdateTime, DateUtil.getNow())
                .set(PoInvoiceDetailDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo());

        boolean result = poInvoiceDetailMapper.update(null, poInvoiceDetailDOLambdaUpdateWrapper) > 0;
        if (result) {
            OpsPoInvoiceDO opsPoInvoiceDO = poInvoiceService.getPoInvoiceDetailAmount(poInvoiceDetailVO.getInvoiceId()).getData();
            opsPoInvoiceDO.setInvoiceId(poInvoiceDetailVO.getInvoiceId());
            result = poInvoiceService.updatePoInvoiceMasterFee(opsPoInvoiceDO);
            if (result) {
                return ResultVo.success("修改成本成功！");
            } else {
                return ResultVo.failure("修改失败，主表没有数据更新！");
            }
        } else {
            return ResultVo.failure("修改失败，明细没有数据更新！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePoInvoiceMasterFee(OpsPoInvoiceDO opsPoInvoiceDO) {
        LambdaUpdateWrapper<OpsPoInvoiceDO> opsPoInvoiceDOLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        opsPoInvoiceDOLambdaUpdateWrapper.eq(OpsPoInvoiceDO::getInvoiceId, opsPoInvoiceDO.getInvoiceId())
                .set(OpsPoInvoiceDO::getAmount, opsPoInvoiceDO.getAmount())
                .set(OpsPoInvoiceDO::getAmountRmb, opsPoInvoiceDO.getAmountRmb())
                .set(OpsPoInvoiceDO::getCustomsFee, opsPoInvoiceDO.getCustomsFee())
                .set(OpsPoInvoiceDO::getExcisetax, opsPoInvoiceDO.getExcisetax())
                .set(OpsPoInvoiceDO::getTransFee, opsPoInvoiceDO.getTransFee())
                .set(OpsPoInvoiceDO::getOtherFee, opsPoInvoiceDO.getOtherFee())
                .set(OpsPoInvoiceDO::getAmounttotal, opsPoInvoiceDO.getAmounttotal())
                .set(OpsPoInvoiceDO::getUpdateTime, DateUtil.getNow())
                .set(OpsPoInvoiceDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo());
        // SMCApp.getLoginAuthDto().getUserNo()
        return poInvoiceMasterMapper.update(null, opsPoInvoiceDOLambdaUpdateWrapper) > 0;

    }

    @Override
    public ResultVo<BigDecimal> getExchangeRateByinvoiceId(long invoiceId) {
        LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(OpsPoInvoiceDO::getExchangeRate)
                .eq(OpsPoInvoiceDO::getInvoiceId, invoiceId);
        OpsPoInvoiceDO opsPoInvoiceDO = poInvoiceMasterMapper.selectOne(queryWrapper);
        if (PublicUtil.isEmpty(opsPoInvoiceDO))
            return ResultVo.failure(String.valueOf(invoiceId) + "没有发票数据！");
        else
            return ResultVo.success(opsPoInvoiceDO.getExchangeRate());

    }

    @Override
    public ResultVo<OpsPoInvoiceVO> getOpsPoInvoice(Long invoiceId) {
        QueryWrapper<OpsPoInvoiceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("invoice_id", invoiceId);
        OpsPoInvoiceDO poInvoiceDO = poInvoiceMasterMapper.selectOne(queryWrapper);
        if (PublicUtil.isEmpty(poInvoiceDO))
            return ResultVo.failure(String.valueOf(invoiceId) + "没有发票数据！");
        else {
            OpsPoInvoiceVO opsPoInvoiceVO = BeanCopyUtil.copy(poInvoiceDO, OpsPoInvoiceVO.class);
            return ResultVo.success(opsPoInvoiceVO);
        }
    }

    @Override
    public void monthlyInventorySummary() {
        commonMapper.execCalMonthlyInventorySummary();
    }


    private LambdaQueryWrapper<OpsPoInvoiceDO> getPoInvoiceQryWrapper(PoInvoiceMasterRequest request) {
        if (PublicUtil.isNotEmpty(request.getToDate())) {
            request.setToDate(DateUtil.addDay(request.getToDate(), 1));
        }
        LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(PublicUtil.isNotEmpty(request.getInvoiceNo()), OpsPoInvoiceDO::getInvoiceNo, request.getInvoiceNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getInvoiceId()), OpsPoInvoiceDO::getInvoiceId, request.getInvoiceId());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getSupplierCode()), OpsPoInvoiceDO::getSupplierCode, request.getSupplierCode());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getStatus()), OpsPoInvoiceDO::getStatus, request.getStatus());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getId()), OpsPoInvoiceDO::getId, request.getId());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getCurrencyCode()), OpsPoInvoiceDO::getCurrencyCode, request.getCurrencyCode());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getInvoiceType()), OpsPoInvoiceDO::getInvoiceType, request.getInvoiceType());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getReceiveWarehouseCode()), OpsPoInvoiceDO::getReceiveWarehouseCode, request.getReceiveWarehouseCode());

        if (request.getQryDateType() == 1) {
            queryWrapper.ge(PublicUtil.isNotEmpty(request.getFromDate()), OpsPoInvoiceDO::getImpDate, request.getFromDate());
            queryWrapper.lt(PublicUtil.isNotEmpty(request.getToDate()), OpsPoInvoiceDO::getImpDate, request.getToDate());
        } else if (request.getQryDateType() == 2) {
            queryWrapper.ge(PublicUtil.isNotEmpty(request.getFromDate()), OpsPoInvoiceDO::getCostTime, request.getFromDate());
            queryWrapper.lt(PublicUtil.isNotEmpty(request.getToDate()), OpsPoInvoiceDO::getCostTime, request.getToDate());
        } else if (request.getQryDateType() == 3) {
            queryWrapper.ge(PublicUtil.isNotEmpty(request.getFromDate()), OpsPoInvoiceDO::getReceiveTime, request.getFromDate());
            queryWrapper.lt(PublicUtil.isNotEmpty(request.getToDate()), OpsPoInvoiceDO::getReceiveTime, request.getToDate());
        } else {
            queryWrapper.ge(PublicUtil.isNotEmpty(request.getFromDate()), OpsPoInvoiceDO::getInvoiceDate, request.getFromDate());
            queryWrapper.lt(PublicUtil.isNotEmpty(request.getToDate()), OpsPoInvoiceDO::getInvoiceDate, request.getToDate());
        }
//        queryWrapper.eq(PublicUtil.isNotEmpty(request.getCostDate()), OpsPoInvoiceDO::getCostTime, request.getCostDate());
        queryWrapper.orderByDesc(OpsPoInvoiceDO::getId);
        return queryWrapper;
    }

    @Override
    public ResultVo<PageInfo<PoInvoiceDetailVO>> listPoInvoiceDetail(PoInvoiceDetailRequest request) {
        LambdaQueryWrapper<PoInvoiceDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PoInvoiceDetailDO::getInvoiceId, request.getInvoiceId())
                .ne(PoInvoiceDetailDO::getStatus, "9")
                .eq(PublicUtil.isNotEmpty(request.getModelNo()), PoInvoiceDetailDO::getModelNo, request.getModelNo())
                .eq(PublicUtil.isNotEmpty(request.getOrderNo()), PoInvoiceDetailDO::getOrderNo, request.getOrderNo());

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<PoInvoiceDetailDO> list = poInvoiceDetailMapper.selectList(queryWrapper);
        PageInfo<PoInvoiceDetailDO> pageInfo = PageInfo.of(list);
        PageInfo<PoInvoiceDetailVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, PoInvoiceDetailVO.class);
        return ResultVo.success(voPageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> updatePoinvoice(OpsPoInvoiceVO vo) {
        try {
            //LambdaUpdateWrapper<OpsPoInvoiceDO> updateWrapper = new LambdaUpdateWrapper<>();
            if (PublicUtil.isEmpty(vo.getId())) {
                return ResultVo.failure("请选择要更新的id");
            }

            OpsPoInvoiceDO opsPoInvoiceDO = poInvoiceMasterMapper.selectById(vo.getId());
            if (opsPoInvoiceDO == null) {
                return ResultVo.failure("发票不存在id=" + vo.getId());
            }
            if (opsPoInvoiceDO.getStatus() == POInvoiceState.CostBalanced.code()) {
                return ResultVo.failure("已成本结算不能再修改");
            }
            if (vo.getAmount() == null) {
                return ResultVo.failure("必须输入原金额！");
            }

            //1-查询明细合计金额
            OpsPoInvoiceDO updateDO = new OpsPoInvoiceDO();
            updateDO.setId(vo.getId());
            updateDO.setCurrencyCode(vo.getCurrencyCode());
            updateDO.setExchangeRate(vo.getExchangeRate());
            updateDO.setAmount(Optional.ofNullable(vo.getAmount()).orElse(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP));
            updateDO.setAmountadjust(Optional.ofNullable(vo.getAmountadjust()).orElse(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP));
            updateDO.setAmountRmb(Optional.ofNullable(vo.getAmountRmb()).orElse(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP));
            updateDO.setCustomsFee(Optional.ofNullable(vo.getCustomsFee()).orElse(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP));
            updateDO.setExcisetax(Optional.ofNullable(vo.getExcisetax()).orElse(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP));
            updateDO.setTransFee(Optional.ofNullable(vo.getTransFee()).orElse(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP));
            updateDO.setOtherFee(Optional.ofNullable(vo.getOtherFee()).orElse(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP));
            updateDO.setInvoiceDate(vo.getInvoiceDate());
            updateDO.setInvoiceNo(vo.getInvoiceNo());
            updateDO.setCostTime(vo.getCostTime());
            if (PublicUtil.isEmpty(opsPoInvoiceDO.getReceiveTime())) {
                updateDO.setReceiveTime(vo.getReceiveTime());
            }
            if (vo.getIsFinishCost()) {
                updateDO.setStatus(3);
            }
            updateDO.setUpdateUser(SMCApp.getLoginAuthDto().getUserNo());
            int result = poInvoiceMasterMapper.updateById(updateDO);
            if (result != 1) {
                return ResultVo.failure("更新错误");
            }

            //平摊修改金额
//        if (vo.getAmountadjust().compareTo(BigDecimal.ZERO) > 0) {
//            LambdaQueryWrapper<PoInvoiceDetailDO> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(PoInvoiceDetailDO::getInvoiceId, vo.getInvoiceId());
//            int detailCount = poInvoiceDetailMapper.selectCount(queryWrapper);
//            if (detailCount > 0) {
//                BigDecimal adjustAmount = vo.getAmountadjust().divide(BigDecimal.valueOf(detailCount), 3, BigDecimal.ROUND_HALF_UP);
//                LambdaUpdateWrapper<PoInvoiceDetailDO> updDetailWrapper = new LambdaUpdateWrapper<>();
//                updDetailWrapper.eq(PoInvoiceDetailDO::getInvoiceId, vo.getInvoiceId());
//                PoInvoiceDetailDO detailDO = new PoInvoiceDetailDO();
//                detailDO.setAmountAdjust(adjustAmount);
//                poInvoiceDetailMapper.update(detailDO, updDetailWrapper);
//            }
//        }
            updateDO.setInvoiceId(opsPoInvoiceDO.getInvoiceId());
            //人民币金额为空先更新人民币金额
            if (PublicUtil.isEmpty(opsPoInvoiceDO.getAmountRmb())) {
                updatePODetailRMBAmount(updateDO.getInvoiceId());
            }
            //分摊
            adjustDetailDifferentAmt(updateDO, 1);

            return ResultVo.success("更新成功。");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 异步调整明细金额
     *
     * @param InvoiceId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> AsyncAdjustDetailDifferentAmt(Long InvoiceId) {
//        LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapperPOInvoice = new LambdaQueryWrapper<>();
//        queryWrapperPOInvoice.eq(OpsPoInvoiceDO::getInvoiceId, InvoiceId);
//        OpsPoInvoiceDO poInvoiceDO = poInvoiceMasterMapper.selectOne(queryWrapperPOInvoice);
//        ResultVo<String> result=adjustDetailDifferentAmt(poInvoiceDO, 1);

        //更新明细合计
        poInvoiceDetailMapper.updatePoInvoiceDetailAmountTotal(InvoiceId);
        //更新合计
        poInvoiceMasterMapper.updatepoInvoiceMasterAmountTotal(InvoiceId);

        return ResultVo.success("更新成功。");
    }

    /**
     * 定时调整明细金额
     *
     * @return
     */
    @Override
    public ResultVo<String> synAdjustDetailDifferentAmt() {
        LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsPoInvoiceDO::getStatus, OpsPoInvoiceStatusEnum.RECEIVED.getType())
                .and(i -> i.eq(OpsPoInvoiceDO::getAmount, 0).or().eq(OpsPoInvoiceDO::getAmountRmb, 0));

        List<OpsPoInvoiceDO> list = poInvoiceMasterMapper.selectList(queryWrapper);
        for (OpsPoInvoiceDO info : list) {
            adjustDetailDifferentAmt(info, 1);
        }
        return ResultVo.success("");
    }

    /**
     * 调整明细金额
     *
     * @param toUpdateDO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> adjustDetailDifferentAmt(OpsPoInvoiceDO toUpdateDO, Integer type) {
        //1.查询明细总金额
        OpsPoInvoiceDO detailSumInfo = poInvoiceMasterMapper.getPoInvoiceDetailAmount(toUpdateDO.getInvoiceId());

        //没有明细项退出
        if (detailSumInfo == null) {
            return ResultVo.failure("");
        }
        //最小差异分摊金额,大于该值才用金额比例分摊,小于该值修改最大项
        BigDecimal minAvgDiffAmt = new BigDecimal("10");


        //存在无商业价值关税金额则转移到其他金额里，再分配
//        BigDecimal noCustomsFee = poInvoiceMasterMapper.getNonCommercialCustomsFee(toUpdateDO.getInvoiceId());
//        if(noCustomsFee!=null && noCustomsFee.compareTo(BigDecimal.ZERO)>0) {
//            toUpdateDO.setOtherFee(BigDecimalUtil.add(toUpdateDO.getOtherFee(),noCustomsFee));
//            toUpdateDO.setCustomsFee(BigDecimalUtil.sub(toUpdateDO.getCustomsFee(),noCustomsFee));
//        }

        //更新ECode
        poInvoiceMasterMapper.updatepoInvoiceDeatailECode(toUpdateDO.getInvoiceId());

        //人民币金额为空先更新人民币金额
        if (detailSumInfo.getAmountRmb() == null || detailSumInfo.getAmountRmb().compareTo(BigDecimal.ZERO) == 0) {
            updatePODetailRMBAmount(toUpdateDO.getInvoiceId());
            LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapperPOInvoice = new LambdaQueryWrapper<>();
            queryWrapperPOInvoice.eq(OpsPoInvoiceDO::getInvoiceId, toUpdateDO.getInvoiceId());
            OpsPoInvoiceDO poInvoiceDO = poInvoiceMasterMapper.selectOne(queryWrapperPOInvoice);
            toUpdateDO.setAmountRmb(poInvoiceDO.getAmountRmb());
            detailSumInfo = poInvoiceMasterMapper.getPoInvoiceDetailAmount(toUpdateDO.getInvoiceId());
        }

        LambdaQueryWrapper<PoInvoiceDetailDO> countWrapper = new LambdaQueryWrapper<>();
        countWrapper.eq(PoInvoiceDetailDO::getInvoiceId, toUpdateDO.getInvoiceId())
                .ne(PoInvoiceDetailDO::getStatus, "9")
                .ne(PoInvoiceDetailDO::getImpType, 1);
        int count = poInvoiceDetailMapper.selectCount(countWrapper);

        //变更了原发票金额
        if (Optional.ofNullable(toUpdateDO.getAmount()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) != 0 &&
                Optional.ofNullable(toUpdateDO.getAmount()).orElse(BigDecimal.ZERO).compareTo(Optional.ofNullable(detailSumInfo.getAmount()).orElse(BigDecimal.ZERO)) != 0) {

            BigDecimal diffAmt = BigDecimalUtil.sub(toUpdateDO.getAmount(), detailSumInfo.getAmount());

            // 成本入库原金额误差可分摊金额,差异大于systemAmt不分摊 bug8977
            BigDecimal systemAmt = BigDecimal.ZERO;
            ResultVo<DataTypeVO> dataTypeResultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "6");

            if (dataTypeResultVo != null) {
                systemAmt = new BigDecimal(dataTypeResultVo.getData().getExtNote1()).abs();
            }

            if (systemAmt.compareTo(new BigDecimal("10")) <= 0) {  //系统设置数systemAmt小于10将金额分摊到最多的项上
                if (diffAmt.abs().compareTo(systemAmt) <= 0)//小于systemAmt将差异金额分摊到最多的项上
                {
                    updPoInvoiceDetailAmount(toUpdateDO.getInvoiceId(), diffAmt);
                }
            } else {
                if (diffAmt.abs().compareTo(new BigDecimal("10")) <= 0)//小于10将差异金额分摊到最多的项上
                {
                    updPoInvoiceDetailAmount(toUpdateDO.getInvoiceId(), diffAmt);
                } else if (diffAmt.abs().compareTo(systemAmt) <= 0) {// bug8977原金额少于等于systemAmt平均分摊，大于systemAmt不调整
                    BigDecimal avAmount = BigDecimalUtil.div(diffAmt, count, 2);
                    //存在比平摊金额低的项先分配完
                    if (avAmount.compareTo(BigDecimal.ZERO) < 0) {
                        LambdaQueryWrapper<PoInvoiceDetailDO> diffWrapper = new LambdaQueryWrapper<>();
                        diffWrapper.select(PoInvoiceDetailDO::getId, PoInvoiceDetailDO::getAmount);
                        diffWrapper.eq(PoInvoiceDetailDO::getInvoiceId, toUpdateDO.getInvoiceId())
                                .ne(PoInvoiceDetailDO::getStatus, "9")
                                .ne(PoInvoiceDetailDO::getImpType, 1)
                                .lt(PoInvoiceDetailDO::getAmount, avAmount.negate());
                        List<PoInvoiceDetailDO> difflist = poInvoiceDetailMapper.selectList(diffWrapper);
                        if (CollectionUtil.isNotEmpty(difflist)) {
                            PoInvoiceDetailDO upddiffDO = new PoInvoiceDetailDO();
                            for (PoInvoiceDetailDO diffDO : difflist) {
                                diffAmt = BigDecimalUtil.sub(diffAmt, diffDO.getAmount());
                                upddiffDO.setId(diffDO.getId());
                                upddiffDO.setAmount(BigDecimal.ZERO);
                                poInvoiceDetailMapper.updateById(upddiffDO);
                            }
                            avAmount = BigDecimalUtil.div(diffAmt, (count - difflist.size()), 2);  //重算平均金额
                        }
                    }

                    poInvoiceDetailMapper.updatePoInvoiceDetailAmount(toUpdateDO.getInvoiceId(), avAmount);
                }
            }
        }
        //本币金额
        if (Optional.ofNullable(toUpdateDO.getAmountRmb()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) != 0 &&
                Optional.ofNullable(toUpdateDO.getAmountRmb()).orElse(BigDecimal.ZERO).compareTo(Optional.ofNullable(detailSumInfo.getAmountRmb()).orElse(BigDecimal.ZERO)) != 0) {
            BigDecimal diffAmt = BigDecimalUtil.sub(toUpdateDO.getAmountRmb(), detailSumInfo.getAmountRmb());
            if (Math.abs(diffAmt.intValue()) < 10)//小于10将差异金额分摊到最多的项上
            {
                PoInvoiceDetailDO maxamountInfo = poInvoiceDetailMapper.getMaxPoInvoiceAmountRmb(toUpdateDO.getInvoiceId());
                PoInvoiceDetailDO updMaxAmount = new PoInvoiceDetailDO();
                updMaxAmount.setId(maxamountInfo.getId());
                updMaxAmount.setAmountRmb(maxamountInfo.getAmountRmb().add(diffAmt));
                poInvoiceDetailMapper.updateById(updMaxAmount);

            } else {//平均分摊
                BigDecimal avAmount = BigDecimalUtil.div(diffAmt, count, 2);

                //存在比平摊金额低的项先分配完
                if (avAmount.compareTo(BigDecimal.ZERO) < 0) {
                    LambdaQueryWrapper<PoInvoiceDetailDO> diffWrapper = new LambdaQueryWrapper<>();
                    diffWrapper.select(PoInvoiceDetailDO::getId, PoInvoiceDetailDO::getAmountRmb);
                    diffWrapper.eq(PoInvoiceDetailDO::getInvoiceId, toUpdateDO.getInvoiceId())
                            .ne(PoInvoiceDetailDO::getStatus, "9")
                            .ne(PoInvoiceDetailDO::getImpType, 1)
                            .lt(PoInvoiceDetailDO::getAmountRmb, avAmount.negate());
                    List<PoInvoiceDetailDO> difflist = poInvoiceDetailMapper.selectList(diffWrapper);
                    if (CollectionUtil.isNotEmpty(difflist)) {
                        PoInvoiceDetailDO upddiffDO = new PoInvoiceDetailDO();
                        for (PoInvoiceDetailDO diffDO : difflist) {
                            diffAmt = BigDecimalUtil.sub(diffAmt, diffDO.getAmountRmb());
                            upddiffDO.setId(diffDO.getId());
                            upddiffDO.setAmountRmb(BigDecimal.ZERO);
                            poInvoiceDetailMapper.updateById(upddiffDO);
                        }
                        avAmount = BigDecimalUtil.div(diffAmt, (count - difflist.size()), 2);  //重算平均金额
                    }
                }
                poInvoiceDetailMapper.updatePoInvoiceDetailAmountRmb(toUpdateDO.getInvoiceId(), avAmount);
            }
        }

        //关税金额
        if (Optional.ofNullable(toUpdateDO.getCustomsFee()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) != 0 &&
                Optional.ofNullable(toUpdateDO.getCustomsFee()).orElse(BigDecimal.ZERO).compareTo(Optional.ofNullable(detailSumInfo.getCustomsFee()).orElse(BigDecimal.ZERO)) != 0) {
            BigDecimal diffAmt = BigDecimalUtil.sub(toUpdateDO.getCustomsFee(), detailSumInfo.getCustomsFee());
            if (diffAmt.abs().compareTo(minAvgDiffAmt) <= 0)//小于10将差异金额分摊到最多的项上
            {
                if (diffAmt.compareTo(BigDecimal.ZERO) != 0) {
                    PoInvoiceDetailDO maxamountInfo = poInvoiceDetailMapper.getMaxPoInvoiceCustomsFee(toUpdateDO.getInvoiceId());
                    PoInvoiceDetailDO updMaxAmount = new PoInvoiceDetailDO();
                    updMaxAmount.setId(maxamountInfo.getId());
                    updMaxAmount.setCustomsFee(maxamountInfo.getCustomsFee().add(diffAmt));
                    poInvoiceDetailMapper.updateById(updMaxAmount);
                }
            } else {
                // add by A78027 20230414 bug 10449
                if (detailSumInfo.getCustomsFee().compareTo(BigDecimal.ZERO) == 0) {
                    //当前关税都是0,按金额分摊
                    if (Optional.ofNullable(toUpdateDO.getAmount()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) != 0
                            && diffAmt.compareTo(BigDecimal.ZERO) != 0) {
                        poInvoiceDetailMapper.updatePoInvoiceDetailCustomsFee(toUpdateDO.getInvoiceId(), diffAmt, toUpdateDO.getAmount());
                    }
                } else {
                    //按关税分摊
                    poInvoiceDetailMapper.updatePoInvoiceDetailCustomsFeeByCustomsFeeRate(toUpdateDO.getInvoiceId(), diffAmt, detailSumInfo.getCustomsFee());
                }
            }
        }

        //消费税金额
        if (Optional.ofNullable(toUpdateDO.getExcisetax()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) != 0 &&
                Optional.ofNullable(toUpdateDO.getExcisetax()).orElse(BigDecimal.ZERO).compareTo(Optional.ofNullable(detailSumInfo.getExcisetax()).orElse(BigDecimal.ZERO)) != 0) {
            BigDecimal diffAmt = BigDecimalUtil.sub(toUpdateDO.getExcisetax(), detailSumInfo.getExcisetax());
            if (diffAmt.abs().compareTo(minAvgDiffAmt) <= 0)//小于10将差异金额分摊到最多的项上
            {
                if (diffAmt.compareTo(BigDecimal.ZERO) != 0) {
                    PoInvoiceDetailDO maxamountInfo = poInvoiceDetailMapper.getMaxPoInvoiceExciseTax(toUpdateDO.getInvoiceId());
                    PoInvoiceDetailDO updMaxAmount = new PoInvoiceDetailDO();
                    updMaxAmount.setId(maxamountInfo.getId());
                    updMaxAmount.setExciseTax(maxamountInfo.getExciseTax().add(diffAmt));
                    poInvoiceDetailMapper.updateById(updMaxAmount);
                }

            } else {//为0,按项金额进行分摊
                if (detailSumInfo.getExcisetax().compareTo(BigDecimal.ZERO) == 0) {
                    if (Optional.ofNullable(toUpdateDO.getAmount()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) != 0
                            && diffAmt.compareTo(BigDecimal.ZERO) != 0) {
                        poInvoiceDetailMapper.updatePoInvoiceDetailExciseTax(toUpdateDO.getInvoiceId(), diffAmt, toUpdateDO.getAmount());
                    }
                } else {
                    poInvoiceDetailMapper.updatePoInvoiceDetailExciseTaxByExciseTaxRate(toUpdateDO.getInvoiceId(), diffAmt, detailSumInfo.getExcisetax());
                }
            }
        }


        //运费
        if (Optional.ofNullable(toUpdateDO.getTransFee()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) != 0 &&
                Optional.ofNullable(toUpdateDO.getTransFee()).orElse(BigDecimal.ZERO).compareTo(Optional.ofNullable(detailSumInfo.getTransFee()).orElse(BigDecimal.ZERO)) != 0) {
            BigDecimal diffAmt = BigDecimalUtil.sub(toUpdateDO.getTransFee(), detailSumInfo.getTransFee());
            if (diffAmt.abs().compareTo(minAvgDiffAmt) <= 0)//小于10将差异金额分摊到最多的项上
            {
                PoInvoiceDetailDO maxamountInfo = poInvoiceDetailMapper.getMaxPoInvoiceTransfee(toUpdateDO.getInvoiceId());
                PoInvoiceDetailDO updMaxAmount = new PoInvoiceDetailDO();
                updMaxAmount.setId(maxamountInfo.getId());
                updMaxAmount.setTransFee(maxamountInfo.getTransFee().add(diffAmt));
                poInvoiceDetailMapper.updateById(updMaxAmount);

            } else {//按比例分摊
                if (detailSumInfo.getTransFee().compareTo(BigDecimal.ZERO) == 0) {
                    if (Optional.ofNullable(toUpdateDO.getAmount()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) != 0
                            && diffAmt.compareTo(BigDecimal.ZERO) != 0) {
                        poInvoiceDetailMapper.updatePoInvoiceDetailTransfee(toUpdateDO.getInvoiceId(), diffAmt, toUpdateDO.getAmount());
                    }
                } else {
                    poInvoiceDetailMapper.updatePoInvoiceDetailTransfeeByTransFeeRate(toUpdateDO.getInvoiceId(), diffAmt, detailSumInfo.getTransFee());
                }
            }
        }

        //其他费用
        if (Optional.ofNullable(toUpdateDO.getOtherFee()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) != 0 &&
                Optional.ofNullable(toUpdateDO.getOtherFee()).orElse(BigDecimal.ZERO).compareTo(Optional.ofNullable(detailSumInfo.getOtherFee()).orElse(BigDecimal.ZERO)) != 0) {
            BigDecimal diffAmt = BigDecimalUtil.sub(toUpdateDO.getOtherFee(), detailSumInfo.getOtherFee());
            if (diffAmt.abs().compareTo(minAvgDiffAmt) <= 0)//小于10将差异金额分摊到最多的项上
            {
                PoInvoiceDetailDO maxamountInfo = poInvoiceDetailMapper.getMaxPoInvoiceOtherfee(toUpdateDO.getInvoiceId());
                PoInvoiceDetailDO updMaxAmount = new PoInvoiceDetailDO();
                updMaxAmount.setId(maxamountInfo.getId());
                updMaxAmount.setOtherFee(maxamountInfo.getOtherFee().add(diffAmt));
                poInvoiceDetailMapper.updateById(updMaxAmount);

            } else {//按比例分摊
                if (detailSumInfo.getOtherFee().compareTo(BigDecimal.ZERO) == 0) {
                    if (Optional.ofNullable(toUpdateDO.getAmount()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) != 0
                            && diffAmt.compareTo(BigDecimal.ZERO) != 0) {
                        poInvoiceDetailMapper.updatePoInvoiceDetailOtherfee(toUpdateDO.getInvoiceId(), diffAmt, toUpdateDO.getAmount());
                    }
                } else {
                    poInvoiceDetailMapper.updatePoInvoiceDetailOtherfeeByOtherfeeRate(toUpdateDO.getInvoiceId(), diffAmt, detailSumInfo.getOtherFee());
                }
            }
        }

        //更新明细合计
        poInvoiceDetailMapper.updatePoInvoiceDetailAmountTotal(toUpdateDO.getInvoiceId());
        //更新合计
        poInvoiceMasterMapper.updatepoInvoiceMasterAmountTotal(toUpdateDO.getInvoiceId());
        if (type == 1) {
            adjustDetailDifferentAmt(toUpdateDO, 2);
        }
        return ResultVo.success("");
    }

    public void updPoInvoiceDetailAmount(Long invoiceId, BigDecimal diffAmt) {
        PoInvoiceDetailDO maxamountInfo = poInvoiceDetailMapper.getMaxPoInvoiceAmount(invoiceId);
        PoInvoiceDetailDO updMaxAmount = new PoInvoiceDetailDO();
        updMaxAmount.setId(maxamountInfo.getId());
        updMaxAmount.setAmount(maxamountInfo.getAmount().add(diffAmt));
        poInvoiceDetailMapper.updateById(updMaxAmount);
    }


    /**
     * 匹配增值税发票金额
     *
     * @param invoiceId
     * @return
     */
    public boolean costAccounting(Long invoiceId, PoInvoiceToCostDTO dto) {
        //先查是不是国内工厂成本数据
        LambdaQueryWrapper<OpsPoInvoiceDO> queryMasterWrapper = new LambdaQueryWrapper<>();
        queryMasterWrapper.eq(OpsPoInvoiceDO::getInvoiceId, invoiceId);
        queryMasterWrapper.and(i -> i.eq(OpsPoInvoiceDO::getStatus, OpsPoInvoiceStatusEnum.RECEIVED.getType())
                        .or().eq(OpsPoInvoiceDO::getStatus, OpsPoInvoiceStatusEnum.COST.getType()))
                .eq(OpsPoInvoiceDO::getSupplierCode, "GZ");
//                .in(OpsPoInvoiceDO::getSupplierCode,Arrays.asList(Constants.COUNTRY_FACTORY));
        int masterCount = poInvoiceMasterMapper.selectCount(queryMasterWrapper);
        if (masterCount <= 0) {
            return true;
        }
        //查找有无匹配数据
        Integer existCount = poInvoiceMasterMapper.getValueDataCount(invoiceId);
        if (existCount <= 0) {
            return false;
        }
        LambdaQueryWrapper<PoInvoiceDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PoInvoiceDetailDO::getInvoiceId, invoiceId)
                .ne(PoInvoiceDetailDO::getStatus, "9");
        List<PoInvoiceDetailDO> detaillist = poInvoiceDetailMapper.selectList(queryWrapper);

        int count = 0;
        for (PoInvoiceDetailDO detailDO : detaillist) {
            BigDecimal price = poInvoiceMasterMapper.getValueInvoicePrice(detailDO.getOrderNo(), detailDO.getOverseaInvoiceNo());
            if (price != null && price.compareTo(BigDecimal.ZERO) > 0 && price.compareTo(detailDO.getPrice()) != 0) {
//                UpdateWrapper<PoInvoiceDetailDO> updatePoInvoiceDetailWrapper = new UpdateWrapper<>();
//                updatePoInvoiceDetailWrapper.eq("invoice_id", detailDO.getInvoiceId());
                PoInvoiceDetailDO poInvoiceDetailDO = new PoInvoiceDetailDO();
                poInvoiceDetailDO.setId(detailDO.getId());
                poInvoiceDetailDO.setPrice(price.setScale(4, BigDecimal.ROUND_HALF_UP));
                poInvoiceDetailDO.setPriceRmb(poInvoiceDetailDO.getPrice());
                poInvoiceDetailDO.setAmount(BigDecimalUtil.mul(price, detailDO.getQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP));
                poInvoiceDetailDO.setAmountRmb(poInvoiceDetailDO.getAmount());
                poInvoiceDetailMapper.updateById(poInvoiceDetailDO);
                count++;
            }
        }
        if (count > 0) {
            //更新明细合计
            poInvoiceDetailMapper.updatePoInvoiceDetailAmountTotal(invoiceId);
            //更新主项合计
            OpsPoInvoiceDO masterDO = poInvoiceMasterMapper.getPoInvoiceDetailAmount(invoiceId);
            UpdateWrapper<OpsPoInvoiceDO> updatemaserWrapper = new UpdateWrapper<>();
            updatemaserWrapper.eq("invoice_id", invoiceId);
            OpsPoInvoiceDO updmaster = new OpsPoInvoiceDO();
            updmaster.setAmount(masterDO.getAmount());
            updmaster.setAmountRmb(masterDO.getAmountRmb());
            updmaster.setAmounttotal(masterDO.getAmounttotal());
            poInvoiceMasterMapper.update(updmaster, updatemaserWrapper);
        }
        //比较总金额与增值税发票一样的才允许成本结算
        OpsPoInvoiceDO masterDO = poInvoiceMasterMapper.getPoInvoiceDetailAmount(invoiceId);
        if (masterDO == null) {
            return false;
        }
        List<PoInvoiceDetailDO> oversealist = poInvoiceDetailMapper.listoverseaInvoiceNo(invoiceId);
        BigDecimal sumAmount = BigDecimal.ZERO;
        List<String> overseaNoList = new ArrayList<>();
        List<String> orderNolist = new ArrayList<>();
        for (PoInvoiceDetailDO oversea : oversealist) {
            if (StringUtils.isNotBlank(oversea.overseaInvoiceNo) && !overseaNoList.contains(oversea.overseaInvoiceNo)) {
                overseaNoList.add(oversea.overseaInvoiceNo);
            }
            orderNolist.add(oversea.getOrderNo());
            if (overseaNoList.isEmpty() || orderNolist.isEmpty()) {
                return false;
            }
            dto = new PoInvoiceToCostDTO();
            dto.setOverseaNoList(overseaNoList);
            dto.setOrderNolist(orderNolist);
            sumAmount = poInvoiceDetailMapper.getValueAmount(overseaNoList, orderNolist);
//            BigDecimal amount=poInvoiceDetailMapper.getValueAmount(overseaInvoiceNo);
//            sumAmount=BigDecimalUtil.add(sumAmount,amount);
        }
        if (masterDO.getAmount().compareTo(sumAmount) != 0) {
            log.info(invoiceId + "发货金额:" + masterDO.getAmount() + "增值税发票金额:" + sumAmount);
            return false;
        }
        return true;
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> autoDataToCost() {
        LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(OpsPoInvoiceDO::getInvoiceId);
        queryWrapper.eq(OpsPoInvoiceDO::getStatus, OpsPoInvoiceStatusEnum.RECEIVED.getType());
        List<OpsPoInvoiceDO> list = poInvoiceMasterMapper.selectList(queryWrapper);
        if (list == null) {
            return ResultVo.success("");
        }
        for (OpsPoInvoiceDO info : list) {
            PoInvoiceToCostDTO dto = null;
            boolean istrue = costAccounting(info.getInvoiceId(), dto);
            if (istrue) {
                ResultVo<String> resultVo = poInvoiceService.doDataToCost(info.getInvoiceId(), null);
                if (resultVo.isSuccess() && dto != null) {
                    //更新增值税发票状态
                    poInvoiceDetailMapper.updateValueInvoiceStatus(dto.getOverseaNoList(), dto.getOrderNolist());
                }
            }
        }
        return ResultVo.success();
    }


    @Override
    public ResultVo<String> exportDataToCost(PoInvoiceToCostDTO dto) {
        try {
            int count = 0;
            String msg = "";
            if (dto == null || dto.getInvoiceIds() == null || dto.getInvoiceIds().length < 1) {
                return ResultVo.failure("请选择数据再结算");
            }
            for (Long invoiceId : dto.getInvoiceIds()) {
                PoInvoiceToCostDTO costdto = null;
                costAccounting(invoiceId, costdto);
                ResultVo<String> resultVo = poInvoiceService.doDataToCost(invoiceId, dto.getCostDate());
                if (!resultVo.isSuccess()) {
                    msg = msg + resultVo.getMessage() + ";";
                } else {
                    count++;
                }
            }
            if (count > 0) {
                return ResultVo.success(String.valueOf(count), "结算" + count + "条数据成功！" + msg);
            } else {
                return ResultVo.failure("结算失败！" + msg);
            }
        } catch (Exception e) {
            log.error("[成本结算错误：]" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> doDataToCost(Long invoiceId, Date costDate) {
        try {
            UpdateWrapper<OpsPoInvoiceDO> updateMasterWrapper;
            LambdaQueryWrapper<OpsPoInvoiceDO> queryMasterWrapper = new LambdaQueryWrapper<>();
            queryMasterWrapper.eq(OpsPoInvoiceDO::getInvoiceId, invoiceId);
            queryMasterWrapper.and(i -> i.eq(OpsPoInvoiceDO::getStatus, OpsPoInvoiceStatusEnum.RECEIVED.getType())
                    .or().eq(OpsPoInvoiceDO::getStatus, OpsPoInvoiceStatusEnum.COST.getType())
                    .or().eq(OpsPoInvoiceDO::getStatus, OpsPoInvoiceStatusEnum.IMP.getType()));
            OpsPoInvoiceDO invoiceMasterDO = poInvoiceMasterMapper.selectOne(queryMasterWrapper);
            if (null == invoiceMasterDO) {
                return ResultVo.failure(invoiceId + "无有效数据或已结算！");
            }
            if (invoiceMasterDO.getInvoiceType() == InvoiceTypeEnum.THREE_COUNTRY.getCode() && "CNY".equalsIgnoreCase(invoiceMasterDO.getCurrencyCode())
                    && !"HK".equalsIgnoreCase(invoiceMasterDO.getSupplierCode())) {
                return ResultVo.failure(invoiceMasterDO.getInvoiceNo() + "三国发票类型（除香港）货币是CNY不能成本结算！");
            }
            if (costDate == null) {
                costDate = DateUtil.getDate(invoiceMasterDO.getReceiveTime());
            }

            //非调整发票，要检查金额不能为0
            if (invoiceMasterDO.getInvoiceType() == null
                    || invoiceMasterDO.getInvoiceType().compareTo(InvoiceTypeEnum.ADJUST.getCode()) == 0) {
                if (Optional.ofNullable(invoiceMasterDO.getAmounttotal()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) == 0) {
                    return ResultVo.failure(invoiceMasterDO.getInvoiceNo() + "金额不能等于0！");
                }
                //存在明细项为0的不能结转
                LambdaQueryWrapper<PoInvoiceDetailDO> queryDetailWrapper = new LambdaQueryWrapper<>();
                queryDetailWrapper.eq(PoInvoiceDetailDO::getInvoiceId, invoiceId)
                        .ne(PoInvoiceDetailDO::getStatus, "9")
                        .ne(PoInvoiceDetailDO::getImpType, 1)
                        .le(PoInvoiceDetailDO::getPrice, BigDecimal.ZERO);
                int exsitCount = poInvoiceDetailMapper.selectCount(queryDetailWrapper);
                if (exsitCount > 0) {
                    return ResultVo.failure(invoiceId + "存在无金额的项，不能结算！");
                }
            }
            //<!--edit by WuWeiDong 20230428 bug 10637 -->
            OpsPoInvoiceDO detailSum = poInvoiceMasterMapper.getPoInvoiceDetailSum(invoiceMasterDO.getInvoiceId());
            if (Optional.ofNullable(detailSum.getAmounttotal()).orElse(BigDecimal.ZERO).compareTo(Optional.ofNullable(invoiceMasterDO.getAmounttotal()).orElse(BigDecimal.ZERO)) != 0) {
                return ResultVo.failure(invoiceMasterDO.getInvoiceNo() + "主表和明细总金额不相等，不能结算！");
            }
            if (Optional.ofNullable(detailSum.getAmount()).orElse(BigDecimal.ZERO).compareTo(Optional.ofNullable(invoiceMasterDO.getAmount()).orElse(BigDecimal.ZERO)) != 0) {
                return ResultVo.failure(invoiceMasterDO.getInvoiceNo() + "主表和明细原金额不相等，不能结算！");
            }

            LambdaQueryWrapper<PoInvoiceDetailDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PoInvoiceDetailDO::getInvoiceId, invoiceMasterDO.getInvoiceId())
                    .ne(PoInvoiceDetailDO::getStatus, "9")
                    .eq(PoInvoiceDetailDO::getImpType, 0);
            List<PoInvoiceDetailDO> detailList = poInvoiceDetailMapper.selectList(queryWrapper);

            if (CollectionUtil.isEmpty(detailList)) {
                return ResultVo.failure(invoiceId + "更新错误,无明细数据。");
            }
            //    <!--add by WuWeiDong 20230202  型号去重后，用批量型号查询，减少数据查询 -->
            List<String> modelList = detailList.stream().map(PoInvoiceDetailDO::getModelNo).distinct().collect(Collectors.toList());
            Map<String, String> ecodeMap = new HashMap<>();
            for (Integer i = 0; i < modelList.size(); i++) {
                String modelNo = modelList.get(i);
                if (modelNo.startsWith("*")) {
                    modelNo = modelNo.substring(1);
                }
                modelList.set(i, modelNo);
            }

            // 处理参数超长 bugid:16819   c14717 20250219
            List<Map<String, String>> modelEcodeList = new ArrayList<>();
            for (int i = 0; i < modelList.size(); i++) {
                if (i % 1500 == 0) {
                    List<String> temp = new ArrayList<String>();
                    if (i + 1500 < modelList.size()) {
                        temp = modelList.subList(i, i + 1500);
                    } else {
                        temp = modelList.subList(i, modelList.size());
                    }
                    List<Map<String, String>> l = poInvoiceMasterMapper.getECodeByModelNos(temp);
                    if (l != null && l.size() > 0) {
                        modelEcodeList.addAll(l);
                    }
                }
            }
            //List<Map<String, String>> modelEcode = poInvoiceMasterMapper.getECodeByModelNos(modelList);
            for (Map<String, String> map : modelEcodeList) {
                ecodeMap.put(map.get("ModelNo"), map.get("ECode"));
            }

            //更新ECode,如果型号是*开头，去掉*号去查Ecode begin bug8726
            for (PoInvoiceDetailDO info : detailList) {
                String modelNo = info.getModelNo();
                if (modelNo.startsWith("*")) {
                    modelNo = modelNo.substring(1);
                }
                String ecode = ecodeMap.get(modelNo);

                //    <!--add by WuWeiDong 20230202  task 9450 -->
                //  ops_po_invoice_detail存在错误型号，这样就导致Ecode不存在，如果不存在时，使用ops_impdata订单对应的入库型号取在查一次型号的ECode
                if (PublicUtil.isEmpty(ecode)) {
                    OrderNoInfo orderNoInfo = new OrderNoInfo();
                    orderNoInfo.convertFullOrderNo(info.getOrderNo());
                    LambdaQueryWrapper<OpsImpDataDO> opsImpDataDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    opsImpDataDOLambdaQueryWrapper.eq(OpsImpDataDO::getOrderNo, orderNoInfo.getOrderNo())
                            .eq(OpsImpDataDO::getItemNo, orderNoInfo.getItemNo())
                            .select(OpsImpDataDO::getModelNo);
                    List<OpsImpDataDO> opsImpDataDOS = opsImpDataMapper.selectList(opsImpDataDOLambdaQueryWrapper);
                    if (PublicUtil.isNotEmpty(opsImpDataDOS)) {
                        modelNo = opsImpDataDOS.get(0).getModelNo();
                        ecode = ecodeMap.get(modelNo);
                        if (PublicUtil.isEmpty(ecode)) {
                            ecode = poInvoiceMasterMapper.getECodeByModelNo(modelNo);
                            ecodeMap.put(modelNo, ecode);
                        }
                    }

                }
                info.setEcode(ecode);
                //    <!--add by WuWeiDong 20231012  bug 12137 取主表的供应商-->
                info.setSupplierCode(invoiceMasterDO.getSupplierCode());
            }//end bug8726
            return poInvoiceService.updateDataToCost(invoiceMasterDO, detailList, costDate);


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //    <!--add by WuWeiDong 20230202  拆开读写分离，减少事务过长 -->
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> updateDataToCost(OpsPoInvoiceDO invoiceMasterDO, List<PoInvoiceDetailDO> detailList, Date costDate) {
        //    <!--add by WuWeiDong 20230223 增加更新时间和操作人员 -->
        Date now = new Date();
        String userNo = SMCApp.getLoginAuthDtoForSysUser().getUserNo();
        LambdaUpdateWrapper<OpsPoInvoiceDO> updateMasterWrapper = new LambdaUpdateWrapper();
        updateMasterWrapper.eq(OpsPoInvoiceDO::getInvoiceId, invoiceMasterDO.getInvoiceId())
                .set(OpsPoInvoiceDO::getStatus, OpsPoInvoiceStatusEnum.COST.getType())
                .set(OpsPoInvoiceDO::getCostTime, costDate)
                .set(OpsPoInvoiceDO::getUpdateUser, userNo)
                .set(OpsPoInvoiceDO::getUpdateTime, now);
        int result = poInvoiceMasterMapper.update(null, updateMasterWrapper);
        if (1 == result) {
            LambdaUpdateWrapper<ImpInvoiceMasterDO> updatePoInvoiceWrapper = new LambdaUpdateWrapper<>();
            updatePoInvoiceWrapper.eq(ImpInvoiceMasterDO::getId, invoiceMasterDO.getInvoiceId());
            updatePoInvoiceWrapper.eq(ImpInvoiceMasterDO::getStatus, ImpInvoiceMasterStatusEnum.IMPED.getType())
                    .set(ImpInvoiceMasterDO::getStatus, ImpInvoiceMasterStatusEnum.COSTED.getType())
                    .set(ImpInvoiceMasterDO::getUpdateUser, userNo)
                    .set(ImpInvoiceMasterDO::getUpdateTime, now);
            impInvoiceMasterMapper.update(null, updatePoInvoiceWrapper);

            for (PoInvoiceDetailDO info : detailList) {
                PoInvoiceDetailDO updPoDetail = new PoInvoiceDetailDO();
                updPoDetail.setId(info.getId());
                updPoDetail.setEcode(info.ecode);
                updPoDetail.setImpDate(costDate);
                updPoDetail.setCurrency(invoiceMasterDO.getCurrencyCode());
                updPoDetail.setUpdateUser(userNo);
                updPoDetail.setUpdateTime(now);
                poInvoiceDetailMapper.updateById(updPoDetail);
            }

            salesImportService.addSalesImp(detailList, costDate, invoiceMasterDO);
            return ResultVo.success("更新成功");
        }
        return ResultVo.failure(invoiceMasterDO.getInvoiceId() + "主表更新错误");
    }

    private Map<String, String> getDataCodesMap(String classCode) {
        Map<String, String> map = new HashMap<>();
        ResultVo<List<DataCodeVO>> listResult = dictDataServiceFeignApi.getDataCodes(classCode);
        for (DataCodeVO vo : listResult.getData()) {
            map.put(vo.getCode(), vo.getCodeName());
        }
        return map;
    }

    @Override
    public void exportPoinvoice(PoInvoiceMasterRequest request) {

        ResultVo<String> resultVo = this.checkQueryParam(request);
        if (!resultVo.isSuccess()) {
            throw new BusinessException(resultVo.getMessage());
        }
        LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapper = getPoInvoiceQryWrapper(request);
        PageHelper.startPage(1, 15000);
        List<OpsPoInvoiceDO> listPage = poInvoiceMasterMapper.selectList(queryWrapper);
        PageInfo<OpsPoInvoiceDO> pageInfo = PageInfo.of(listPage);
        List<OpsPoInvoiceDO> list = pageInfo.getList();
        Map<String, String> statusMap = getDataCodesMap("2043");
        Map<String, String> invoiceTypeMap = getDataCodesMap("2064");
        String path = "templates/OpsPoInvoice.xlsx";
        InputStream inputStream = FileUtil.getTemplate(path);
        ExcelUtil excel = new ExcelUtil(inputStream);
        excel.openSheet(0);

        // 向模板中写入数据
        int row = 1;
        int cel;
        for (OpsPoInvoiceDO data : list) {
            cel = 0;
            excel.setCellValue(row, cel++, data.getInvoiceId());
            excel.setCellValue(row, cel++, data.getSupplierCode());
            excel.setCellValue(row, cel++, data.getInvoiceNo());
            excel.setCellValue(row, cel++, data.getAmount());
            excel.setCellValue(row, cel++, data.getAmountRmb());
            excel.setCellValue(row, cel++, data.getAmounttotal());
            excel.setCellValue(row, cel++, data.getCustomsFee());
            excel.setCellValue(row, cel++, data.getTransFee());
            excel.setCellValue(row, cel++, data.getExcisetax());
            excel.setCellValue(row, cel++, data.getOtherFee());
            excel.setCellValue(row, cel++, data.getVatFee());
            excel.setCellValueNoRule(row, cel++, data.getExchangeRate());
            excel.setCellValue(row, cel++, data.getCurrencyCode());
            excel.setCellValue(row, cel++, statusMap.getOrDefault(data.getStatus().toString(), data.getStatus().toString()));
            excel.setCellValue(row, cel++, data.getCostTime());
            excel.setCellValue(row, cel++, data.getInvoiceDate());
            excel.setCellValue(row, cel++, data.getImpDate());
            excel.setCellValue(row, cel++, data.getReceiveTime());
//            excel.setCellValue(row, cel++, data.getAmountadjust());
            excel.setCellValue(row, cel++, data.getReceiveWarehouseCode());
            excel.setCellValue(row, cel++, data.getShipDate());
            if (data.getInvoiceType() != null) {
                excel.setCellValue(row, cel++, invoiceTypeMap.getOrDefault(data.getInvoiceType(), data.getInvoiceType().toString()));
            }

//            excel.setCellValue(row, cel++, data.getCustomsDate());
//            excel.setCellValue(row, cel++, data.getCreateTime());
//            excel.setCellValue(row, cel++, data.getUpdateTime());
//            excel.setCellValue(row, cel++, data.getUpdateUser());
            row++;
        }
        String fileName = "OpsPoInvoice.xlsx";
        excel.writeToResponse(response, fileName);
    }

//    @Override
//    public void exportPoinvoice(PoInvoiceMasterRequest request) {
//        String fromdate = null;
//        String todate = null;
//        if (PublicUtil.isNotEmpty(request.getFromDate())) {
//            fromdate = DateUtil.getFormatDate(request.getFromDate(), "yyyy-MM-dd");
//        }
//        if (PublicUtil.isNotEmpty(request.getToDate())) {
//            todate = DateUtil.getFormatDate(DateUtil.addDay(request.getToDate(), 1), "yyyy-MM-dd");
//        }
//        List<PoInvoiceDetailReportVO> list = poInvoiceMasterMapper.listPoInvoiceMasterAndDetail(request, fromdate, todate);
//
//        Map<String, SupplierVo> supplierMap = getSupplierMap();
//
//        String path = "templates/OpsPoInvoiceNew.xlsx";
//        InputStream inputStream = FileUtil.getTemplate(path);
//        ExcelUtil excel = new ExcelUtil(inputStream);
//        excel.openSheet(0);
//
//        // 向模板中写入数据
//        int row = 1;
//        int cel;
//        for (PoInvoiceDetailReportVO data : list) {
//            cel = 0;
//            String supplierName = data.getSuppliercode();
//            if (supplierMap.containsKey(data.getSuppliercode())) {
//                supplierName = supplierMap.get(data.getSuppliercode()).getName();
//            }
//            String supplierName1 = supplierName;
//            String supplierName2 = supplierName;
//            if (supplierName.contains("（") && supplierName.contains("）")) {
//                supplierName1 = supplierName.substring(supplierName.indexOf("（") + 1, supplierName.indexOf("）"));
//                supplierName2 = supplierName.replace("（", " ");
//                supplierName2 = supplierName2.replace("）", "");
//            }
//            excel.setCellValue(row, cel++, supplierName1);
//            excel.setCellValue(row, cel++, data.getInvoiceno());
//            excel.setCellValue(row, cel++, data.getOrderno());
//            excel.setCellValue(row, cel++, data.getOverseainvoiceno());
//            excel.setCellValue(row, cel++, data.getCosttime() == null ? "" : DateUtil.getFormatDate(data.getCosttime(), "yyyy-MM-dd"));
//            excel.setCellValue(row, cel++, data.getAmount());
//            excel.setCellValue(row, cel++, data.getExchangerate());
//            excel.setCellValue(row, cel++, data.getCurrencycode());
//            excel.setCellValue(row, cel++, data.getAmountrmb());
//            excel.setCellValue(row, cel++, data.getCustomsfee());
//            excel.setCellValue(row, cel++, data.getTransfee());
//            excel.setCellValue(row, cel++, data.getOtherfee());
//            excel.setCellValue(row, cel++, data.getAmounttotal());
//            excel.setCellValue(row, cel++, data.getInvoicedate() == null ? "" : DateUtil.getFormatDate(data.getInvoicedate(), "yyyy-MM-dd"));
//            excel.setCellValue(row, cel++, data.getPayday());
//            if (PublicUtil.isNotEmpty(data.getInvoicedate()) && PublicUtil.isNotEmpty(data.getPayday())) {
//                excel.setCellValue(row, cel++, DateUtil.addDay(data.getInvoicedate(), data.getPayday()));
//            } else {
//                excel.setCellValue(row, cel++, data.getInvoicedate());
//            }
//            excel.setCellValue(row, cel++, 0);
//            excel.setCellValue(row, cel++, 0);
//            excel.setCellValue(row, cel++, supplierName2);
//            excel.setCellValue(row, cel, "0106");
//            row++;
//        }
//        String fileName = "OpsPoInvoiceNew.xlsx";
//        excel.writeToResponse(response, fileName);
//    }

    @Override
    public void exportPoInvoiceDetail(PoInvoiceDetailRequest request) {

        LambdaQueryWrapper<PoInvoiceDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PoInvoiceDetailDO::getInvoiceId, request.getInvoiceId())
                .ne(PoInvoiceDetailDO::getStatus, "9")
                .eq(PublicUtil.isNotEmpty(request.getModelNo()), PoInvoiceDetailDO::getModelNo, request.getModelNo())
                .eq(PublicUtil.isNotEmpty(request.getOrderNo()), PoInvoiceDetailDO::getOrderNo, request.getOrderNo());

        List<PoInvoiceDetailDO> list = poInvoiceDetailMapper.selectList(queryWrapper);

        String path = "templates/PoInvoiceDetail.xlsx";
        InputStream inputStream = FileUtil.getTemplate(path);
        ExcelUtil excel = new ExcelUtil(inputStream);
        excel.openSheet(0);

        // 向模板中写入数据
        int row = 1;
        int cel;
        for (PoInvoiceDetailDO data : list) {
            cel = 0;
            excel.setCellValue(row, cel++, data.getInvoiceId());
            excel.setCellValue(row, cel++, data.getInvoiceNo());
            excel.setCellValue(row, cel++, data.getOrderNo());
            excel.setCellValue(row, cel++, data.getModelNo());
            excel.setCellValue(row, cel++, data.getQuantity());
            excel.setCellValue(row, cel++, data.getPrice());
            excel.setCellValue(row, cel++, data.getAmount());
            excel.setCellValue(row, cel++, data.getAmountRmb());
            excel.setCellValue(row, cel++, data.getCustomsFee());
            excel.setCellValue(row, cel++, data.getTransFee());
            excel.setCellValue(row, cel++, data.getExciseTax());
            excel.setCellValue(row, cel++, data.getOtherFee());
            excel.setCellValue(row, cel++, data.getPriceTotal());
            excel.setCellValue(row, cel++, data.getAmountTotal());
            excel.setCellValue(row, cel++, data.getProdCountry());
            excel.setCellValue(row, cel++, data.getWeight());
            excel.setCellValue(row, cel++, data.getNonCommercial());
            excel.setCellValue(row, cel++, data.getOverseaInvoiceNo());
            excel.setCellValue(row, cel, data.getEcode());
            excel.setCellValue(row, cel, data.getRemark());
            row++;
        }
        String fileName = "PoInvoiceDetail.xlsx";
        excel.writeToResponse(response, fileName);
    }

    private Map<String, SupplierVo> getSupplierMap() {
        Map<String, SupplierVo> map = new HashMap<>();
        ResultVo<List<SupplierVo>> supplierlist = commonServiceFeignApi.findSupplierInfo();
        for (SupplierVo vo : supplierlist.getData()) {
            map.put(vo.getId(), vo);
        }
        return map;
    }

    @Override
    @Transactional
    public ResultVo<String> updateOpsInvoice(Integer invoiceId) {
        try {
            LambdaQueryWrapper<ImpDataDO> queryImpDataWrapper = new LambdaQueryWrapper<>();
            queryImpDataWrapper.eq(ImpDataDO::getInvoiceId, invoiceId);
            List<ImpDataDO> implist = impdataMapper.selectList(queryImpDataWrapper);
            if (implist.isEmpty()) {
                return ResultVo.success("没数据！");
            }
            int count = 0;
//        Integer itemNo = 0;
            PurchaseReplyInfo info;
            List<PurchaseReplyInfo> list;
            for (ImpDataDO impDO : implist) {
//            itemNo++;
                info = new PurchaseReplyInfo();
                info.setBarCode(impDO.getBarcode());
                info.setCaseNo(impDO.getCaseNo());
                info.setImpdate(impDO.getImpdate());
                info.setInvoiceid(impDO.getInvoiceId());
                info.setInvoiceno(impDO.getInvoiceNo());
                //info.setPono(impDO.getOrderNo());
                info.setReplyorderno(impDO.getOrderNo());
                info.setModelno(impDO.getModelNo());
                info.setQtyreceive(impDO.getQuantity());
                info.setSupplierid(impDO.getSupplierCode());
                info.setLineitem(impDO.getPoItem());
                info.setPono(impDO.getPoNo());
                if (PublicUtil.isEmpty(impDO.getBarcode())) {
                    String billNo = commonServiceFeignApi.generatorBillNo("20").getData();
                    info.setBarCode(billNo);
                } else {
                    info.setBarCode(impDO.getBarcode());
                }
//            list.add(info);
                list = new ArrayList<>(1);
                list.add(info);
                boolean isupdate = toupdateInvoice(list, impDO.getId());
                if (!isupdate) {
                    boolean seconupdate = toupdateInvoice(list, impDO.getId());
                    if (seconupdate) {
                        count++;
                    }
                } else {
                    count++;
                }

            }
            if (count > 0) {
                return ResultVo.success("发送北京接口成功！");
            } else {
                return ResultVo.failure("发送北京接口失败！");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean toupdateInvoice(List<PurchaseReplyInfo> list, Long id) {
        CommonResult<String> updateInvoice = wmPurchaseFeignApi.updateInvoice(list);
        if (!updateInvoice.isSuccess()) {
            log.error("发票数据导入失败: {}", updateInvoice.getMessage());
//                throw new BusinessException("updateInvoice fail, " + updateInvoice.getMessage());
            return false;
        }
        LambdaUpdateWrapper<ImpDataDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ImpDataDO::getId, id);
        ImpDataDO updateDO = new ImpDataDO();
        updateDO.setOptCode(3);
        int updcount = impdataMapper.update(updateDO, updateWrapper);
        return updcount > 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean updateImpInvoiceDetailPack(Integer status, Long invoiceId) {
        UpdateWrapper<ImpInvoiceDetailPackDO> updatepackWrapper = new UpdateWrapper<>();
        updatepackWrapper.eq("invoice_id", invoiceId)
                .eq("status", 1);
        ImpInvoiceDetailPackDO updateDO = new ImpInvoiceDetailPackDO();
        updateDO.setStatus(status);
        return impInvoiceDetailPackMapper.update(updateDO, updatepackWrapper) > 0;
    }

    @Override
    public ResultVo<BigDecimal> getExchangeRate(String currency, Date monthDate) {
        String currencyId = commonMapper.getCurrencyIdByName(currency);
        if (PublicUtil.isEmpty(currencyId)) {
            return ResultVo.failure("货币未登记" + currency);
        }
        if (PublicUtil.isEmpty(monthDate)) {
            monthDate = DateUtil.getCurrentDate();
        }
        return this.poInvoiceService.getComExchangeRate(currencyId, monthDate);
    }

    @Override
    @DS("cmdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<BigDecimal> getComExchangeRate(String currencyId, Date monthDate) {
        Date monthBeginDate = DateUtil.getMonthFirstDate(monthDate);
        Date monthEndDate = DateUtil.getMonthEndDate(monthDate);
        BigDecimal exchangeRate = vSalesManuorderMapper.getExchangeRate(currencyId, monthBeginDate, monthEndDate);
        if (exchangeRate == null || exchangeRate.compareTo(BigDecimal.ZERO) == 0) {
            return ResultVo.failure("无法查询汇率，请先在制造系统中登记本月汇率" + monthBeginDate);
        }
        return ResultVo.success(exchangeRate);
    }

    /**
     * 更新发票明细的人民币金额
     *
     * @param invoiceId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> updatePODetailRMBAmount(Long invoiceId) {
        LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsPoInvoiceDO::getInvoiceId, invoiceId);
        OpsPoInvoiceDO poInvoiceDO = poInvoiceMasterMapper.selectOne(queryWrapper);
        if (poInvoiceDO == null) {
            return ResultVo.failure("发票不存在" + invoiceId);
        }
        if (poInvoiceDO.getStatus() == POInvoiceState.CostBalanced.code()) {
            return ResultVo.failure("已结转不能再变更");
        }

        OpsPoInvoiceDO updPOInvoiceDO = new OpsPoInvoiceDO();
        if (StringUtils.isBlank(poInvoiceDO.getCurrencyCode())) {
            updPOInvoiceDO.setCurrencyCode(poInvoiceMasterMapper.getCurrencyCode(poInvoiceDO.getSupplierCode()));
            poInvoiceDO.setCurrencyCode(updPOInvoiceDO.getCurrencyCode());
        }


        updPOInvoiceDO.setId(poInvoiceDO.getId());
        if (poInvoiceDO.getExchangeRate() == null
                || poInvoiceDO.getExchangeRate().compareTo(BigDecimal.ZERO) == 0) {
//            if(StringUtils.isBlank(poInvoiceDO.getCurrencyCode())){
//                poInvoiceDO.setCurrencyCode(poInvoiceMasterMapper.getCurrencyCode(poInvoiceDO.getSupplierCode()));
//            }
            if (Constants.CURRENTCY_CNY.equalsIgnoreCase(poInvoiceDO.getCurrencyCode())) {
                updPOInvoiceDO.setExchangeRate(new BigDecimal("1"));
            } else {
                if (poInvoiceDO.getReceiveTime() == null) {
                    return ResultVo.failure("没有物流签收时间无法获得汇率");
                }

                ResultVo<BigDecimal> exchangeRateResult = poInvoiceService.getExchangeRate(poInvoiceDO.getCurrencyCode()
                        , poInvoiceDO.getReceiveTime());
                if (exchangeRateResult == null || exchangeRateResult.isSuccess() == false
                        || exchangeRateResult.getData().compareTo(BigDecimal.ZERO) == 0) {
                    return ResultVo.failure("无法查询汇率，请先在制造系统中登记本月汇率" + poInvoiceDO.getReceiveTime());
                }
                updPOInvoiceDO.setExchangeRate(exchangeRateResult.getData());
            }
        } else {
            updPOInvoiceDO.setExchangeRate(poInvoiceDO.getExchangeRate());
        }

        //1更新ops_po_invoice 汇率
        updPOInvoiceDO.setAmountRmb(poInvoiceDO.getAmount().multiply(updPOInvoiceDO.getExchangeRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
        poInvoiceMasterMapper.updateById(updPOInvoiceDO);

        //2更新明细人民币金额
        poInvoiceDetailMapper.updatePOInvoiceDetailRMBAmount(invoiceId, updPOInvoiceDO.getExchangeRate());


        return ResultVo.success("", "重新计算人民币金额成功");
    }

    /**
     * 查询明细总金额
     *
     * @param invoiceId
     * @return
     */
    @Override
    public ResultVo<OpsPoInvoiceDO> getPoInvoiceDetailAmount(Long invoiceId) {
        OpsPoInvoiceDO detailSumInfo = poInvoiceMasterMapper.getPoInvoiceDetailAmount(invoiceId);
        return ResultVo.success(detailSumInfo);
    }

    /**
     * 导出导出三国发票明细
     *
     * @param request
     * @return
     */
    @Override
    public void exportOtherInvoiceData(PoInvoiceMasterRequest request) {
        try {
            ResultVo<String> resultVo = this.checkQueryParam(request);
            if (!resultVo.isSuccess()) {
                throw new BusinessException(resultVo.getMessage());
            }

            String fromDate = null;
            String toDate = null;
            if (PublicUtil.isNotEmpty(request.getFromDate())) {
                fromDate = DateUtil.getFormatDate(request.getFromDate(), "yyyy-MM-dd");
            }
            if (PublicUtil.isNotEmpty(request.getToDate())) {
                toDate = DateUtil.getFormatDate(DateUtil.addDay(request.getToDate(), 1), "yyyy-MM-dd");
            }
            //    <!--add by WuWeiDong 20240126  bug 13443  去掉无价值项 -->
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            final String fromDateFinal = fromDate;
            final String toDateFinal = toDate;
            StringBuffer erorrMessage = new StringBuffer();
            Future<List<PoInvoiceDetailReportVO>> listFuture = executorService.submit(new Callable<List<PoInvoiceDetailReportVO>>() {
                @Override
                public List<PoInvoiceDetailReportVO> call() throws Exception {
                    try {
                        return poInvoiceMasterMapper.listPoInvoiceDetailReport(request, fromDateFinal, toDateFinal);
                    } catch (Exception ex) {
                        log.error("导出数据失败：==>listPoInvoiceDetailReport ==>" + ex.getMessage());
                        erorrMessage.append(ex.getMessage());
                        return Collections.emptyList();
                    }
                }
            });
            Future<List<PoInvoiceDetailReportVO>> usAndAplistFuture = executorService.submit(new Callable<List<PoInvoiceDetailReportVO>>() {
                @Override
                public List<PoInvoiceDetailReportVO> call() throws Exception {
                    try {
                        return poInvoiceMasterMapper.listusAndApPoInvoiceDetailReport(request, fromDateFinal, toDateFinal);
                    } catch (Exception ex) {
                        log.error("导出数据失败：==>listusAndApPoInvoiceDetailReport ==>" + ex.getMessage());
                        erorrMessage.append(ex.getMessage());
                        return Collections.emptyList();
                    }
                }
            });
            Future<List<PoInvoiceDetailReportVO>> otherlistFuture = executorService.submit(new Callable<List<PoInvoiceDetailReportVO>>() {
                @Override
                public List<PoInvoiceDetailReportVO> call() throws Exception {
                    try {
                        return poInvoiceMasterMapper.listOtherPoInvoiceDetailReport(request, fromDateFinal, toDateFinal);
                    } catch (Exception ex) {
                        log.error("导出数据失败：==>listOtherPoInvoiceDetailReport ==>" + ex.getMessage());
                        erorrMessage.append("导出数据失败：").append(ex.getMessage());
                        return Collections.emptyList();
                    }
                }
            });
            executorService.shutdown();
            while (true) {
                if (erorrMessage.length() > 0) {
                    executorService.shutdownNow();
                    throw new BusinessException(erorrMessage.toString());

                }
                if (executorService.isTerminated()) {
                    break;
                }
                Thread.sleep(500);
            }
//        List<PoInvoiceDetailReportVO> list = poInvoiceMasterMapper.listPoInvoiceDetailReport(request, fromDate, toDate);
//        List<PoInvoiceDetailReportVO> usAndAplist = poInvoiceMasterMapper.listusAndApPoInvoiceDetailReport(request, fromDate, toDate);
//        List<PoInvoiceDetailReportVO> otherlist = poInvoiceMasterMapper.listOtherPoInvoiceDetailReport(request, fromDate, toDate);

            List<PoInvoiceDetailReportVO> list = listFuture.get();
            if (CollectionUtil.isNotEmpty(usAndAplistFuture.get())) {
                list.addAll(usAndAplistFuture.get());
            }
            if (CollectionUtil.isNotEmpty(otherlistFuture.get())) {
                list.addAll(otherlistFuture.get());
            }

            if (CollectionUtil.isEmpty(list)) {
                throw new BusinessException("无数据导出。");
            }
            Map<String, SupplierVo> supplierMap = getSupplierMap();

            String path = "templates/OpsPoInvoiceNew.xlsx";
            InputStream inputStream = FileUtil.getTemplate(path);
            ExcelUtil excel = new ExcelUtil(inputStream);
            excel.openSheet(0);

            // 向模板中写入数据
            int row = 1;
            int cel;
            int numNo = 0;
            String invoiceno = "";
            for (PoInvoiceDetailReportVO data : list) {
                cel = 0;
                String supplierName = data.getSuppliercode();
                if (supplierMap.containsKey(data.getSuppliercode())) {
                    supplierName = supplierMap.get(data.getSuppliercode()).getName();
                }
                String supplierName1 = supplierName;
                String supplierName2 = supplierName;
                if (supplierName.contains("（") && supplierName.contains("）")) {
                    supplierName1 = supplierName.substring(supplierName.indexOf("（") + 1, supplierName.indexOf("）"));
                    supplierName2 = supplierName.replace("（", " ");
                    supplierName2 = supplierName2.replace("）", "");
                }
                if (!invoiceno.equalsIgnoreCase(data.getInvoiceno())) {
                    numNo++;
                }
                //去掉SMC
                if (supplierName1.startsWith("SMC")) {
                    supplierName1 = supplierName1.substring(3);
                }
                //    <!--add by WuWeiDong 20231109 bug 12560  导出发票主要数据 -->
                supplierName1 = StringUtils.trim(supplierName1);
                invoiceno = data.getInvoiceno();
                excel.setCellValue(row, cel++, numNo);
                excel.setCellValue(row, cel++, supplierName1);
                excel.setCellValue(row, cel++, data.getInvoiceno());
                excel.setCellValue(row, cel++, data.getOrderno());
                excel.setCellValue(row, cel++, data.getOverseainvoiceno());
                excel.setCellValue(row, cel++, data.getCosttime() == null ? "" : DateUtil.getFormatDate(data.getCosttime(), "yyyy-MM-dd"));
                excel.setCellValue(row, cel++, data.getAmount());
                excel.setCellValueNoRule(row, cel++, data.getExchangerate());
                excel.setCellValue(row, cel++, data.getCurrencycode());
                excel.setCellValue(row, cel++, data.getAmountrmb());
                excel.setCellValue(row, cel++, data.getCustomsfee());
                excel.setCellValue(row, cel++, data.getTransfee());
                //    <!--add by WuWeiDong 20240402  bug 13644 增加消费税 -->
                excel.setCellValue(row, cel++, data.getExcisetax());
                excel.setCellValue(row, cel++, data.getOtherfee());
                excel.setCellValue(row, cel++, data.getAmounttotal());
                excel.setCellValue(row, cel++, data.getInvoicedate() == null ? "" : DateUtil.getFormatDate(data.getInvoicedate(), "yyyy-MM-dd"));
                excel.setCellValue(row, cel++, data.getPayday());
                if (PublicUtil.isNotEmpty(data.getInvoicedate()) && PublicUtil.isNotEmpty(data.getPayday())) {
                    excel.setCellValue(row, cel++, DateUtil.addDay(data.getInvoicedate(), data.getPayday()));
                } else {
                    excel.setCellValue(row, cel++, data.getInvoicedate());
                }
                excel.setCellValue(row, cel++, 0);
                excel.setCellValue(row, cel++, 0);
                excel.setCellValue(row, cel++, supplierName2);
                excel.setCellValue(row, cel, "0106");
                row++;
            }
            excel.writeToResponse(response, "OpsPoInvoiceNew.xlsx");
        } catch (Exception ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    @DS("cmdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<InvoiceNoAndShipDateVO> listOPSTExportIvoiceNo(String optTime) {
        List<InvoiceNoAndShipDateVO> list = opstExportRequestToSalesMapper.listOPSTExportIvoiceNo(optTime);
        return list;
    }

    @Override
    @DS("cmdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<InvoiceNoAndShipDateVO> listOPSTExportIvoiceNoTest(String optTime) {
        List<InvoiceNoAndShipDateVO> list = opstExportRequestToSalesMapper.listOPSTExportIvoiceNo(optTime);
        return list;
    }

    @Override
    @DS("cmdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<OPSTExportRequestToSalesDO> listExportRequestToSales(InvoiceNoAndShipDateVO vo) {
//        List<OPSTExportRequestToSalesDO> list = opstExportRequestToSalesMapper.listOPSTExportRequestToSales(vo);
        List<OPSTExportRequestToSalesDO> list = new ArrayList<>();
        LambdaQueryWrapper<OPSTExportRequestToSalesDO> query = new LambdaQueryWrapper<>();
        query.eq(OPSTExportRequestToSalesDO::getInvoiceNo, vo.getInvoiceNo());
        query.isNotNull(OPSTExportRequestToSalesDO::getInvfrom);
        query.apply("convert(varchar(10),ExecuteTime,121)='" + vo.getShipdate() + "'");
        list = opstExportRequestToSalesMapper.selectList(query);
        return list;
    }

    @Override
    @DS("cmdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<OPSTExportRequestToSalesDO> listExportRequestToSalesTest(InvoiceNoAndShipDateVO vo) {
//        List<OPSTExportRequestToSalesDO> list = opstExportRequestToSalesMapper.listOPSTExportRequestToSales(vo);
        LambdaQueryWrapper<OPSTExportRequestToSalesDO> query = new LambdaQueryWrapper<>();
        query.eq(OPSTExportRequestToSalesDO::getInvoiceNo, vo.getInvoiceNo());
        query.isNotNull(OPSTExportRequestToSalesDO::getInvfrom);
        query.apply("convert(varchar(10),ExecuteTime,121)='" + vo.getShipdate() + "'");
        List<OPSTExportRequestToSalesDO> list = opstExportRequestToSalesMapper.selectList(query);
        return list;
    }

    @Override
    @DS("cmdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<OPSVExportInvoicePriceToSalesDO> listOPSVExportIvoice(String optTime) {
        LambdaQueryWrapper<OPSVExportInvoicePriceToSalesDO> query = new LambdaQueryWrapper<>();
        query.ge(OPSVExportInvoicePriceToSalesDO::getInvoiceDate, optTime);
        //                .isNotNull(OPSVExportInvoicePriceToSalesDO::getInvoiceDate);
        List<OPSVExportInvoicePriceToSalesDO> list = opSVExportInvoicePriceToSalesMapper.selectList(query);
        return list;
    }

    @Override
    @DS("cmdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<OPSVExportInvoicePriceToSalesDO> listOPSVExportIvoiceTest(String optTime) {
        LambdaQueryWrapper<OPSVExportInvoicePriceToSalesDO> query = new LambdaQueryWrapper<>();
        query.ge(OPSVExportInvoicePriceToSalesDO::getInvoiceDate, optTime);
        List<OPSVExportInvoicePriceToSalesDO> list = opSVExportInvoicePriceToSalesMapper.selectList(query);
        return list;
    }


    /**
     * 导出增值税发票月次统计
     *
     * @param request
     * @return
     */
    @Override
    public void exportValueImpinvoice(ImpInvoiceMasterRequest request) {
        LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = new LambdaQueryWrapper<>();
        if (request.getInvoiceDateEnd() != null) {
            request.setInvoiceDateEnd(DateUtil.addDay(request.getInvoiceDateEnd(), 1));
        } else {
            throw new BusinessException("输入发票日期。");
        }
        if (Objects.nonNull(request.getInvoiceDateStart()) && Objects.nonNull(request.getInvoiceDateEnd())) {
            int diffMonth = DateUtil.getDiffMonth(request.getInvoiceDateStart(), request.getInvoiceDateEnd());
            if (diffMonth > 12) {
                log.error("日期范围差不能超过12月。：{} -> {} :{}天", request.getInvoiceDateStart(), request.getInvoiceDateEnd(), diffMonth);
                throw new BusinessException("日期范围差不能超过12月。 当前差:" + String.valueOf(diffMonth));
            }
        }
        List<ImpInvoiceMasterDO> list = this.poInvoiceMasterMapper.listValueImpinvoice(request);
        if (CollectionUtil.isEmpty(list) && list.size() == 0) {
            throw new BusinessException("没有查询到数据。");
        }
        Map<String, SupplierVo> supplierMap = getSupplierMap();

        String path = "templates/OpsPoInvoiceNew.xlsx";
        InputStream inputStream = FileUtil.getTemplate(path);
        ExcelUtil excel = new ExcelUtil(inputStream);
        excel.openSheet(0);

        // 向模板中写入数据
        int row = 1;
        int cel;
        int numNo = 0;
        String invoiceno = "";
        for (ImpInvoiceMasterDO data : list) {
            cel = 0;
            String supplierName = data.getSupplierCode();
            if (supplierMap.containsKey(data.getSupplierCode())) {
                supplierName = supplierMap.get(data.getSupplierCode()).getName();
            }
            String supplierName1 = supplierName;
            String supplierName2 = supplierName;
            if (supplierName.contains("（") && supplierName.contains("）")) {
                supplierName1 = supplierName.substring(supplierName.indexOf("（") + 1, supplierName.indexOf("）"));
                supplierName2 = supplierName.replace("（", " ");
                supplierName2 = supplierName2.replace("）", "");
            }
//            if(!invoiceno.equalsIgnoreCase(data.getInvoiceNo())){
//                numNo++;
//            }
//            invoiceno=data.getInvoiceNo();
            excel.setCellValue(row, cel++, row);
            excel.setCellValue(row, cel++, supplierName1);
            excel.setCellValue(row, cel++, "");
            excel.setCellValue(row, cel++, "");
            excel.setCellValue(row, cel++, "");
            excel.setCellValue(row, cel++, "");
            excel.setCellValue(row, cel++, data.getAmount());
            excel.setCellValue(row, cel++, 1);
            excel.setCellValue(row, cel++, "CNY");
            excel.setCellValue(row, cel++, data.getAmount());
            excel.setCellValue(row, cel++, data.getCustomsFee());
            excel.setCellValue(row, cel++, data.getTransFee());
            excel.setCellValue(row, cel++, data.getExciseTax());
            excel.setCellValue(row, cel++, data.getOtherFee());
            excel.setCellValue(row, cel++, BigDecimalUtil.add(BigDecimalUtil.add(BigDecimalUtil.add(data.getAmountRmb(), data.getTransFee()), data.getOtherFee()), data.getCustomsFee()));
            excel.setCellValue(row, cel++, "");
            excel.setCellValue(row, cel++, "");
            excel.setCellValue(row, cel++, "");
            excel.setCellValue(row, cel++, 0);
            excel.setCellValue(row, cel++, 0);
            excel.setCellValue(row, cel++, supplierName2);
            excel.setCellValue(row, cel, "0106");
            row++;
        }
        excel.writeToResponse(response, "OpsPoInvoiceNew.xlsx");
    }

    //    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVo<String> updImpShipAmount(Long invoiceId) {
        ImpInvoiceMasterDO impmasterDO = impInvoiceMasterMapper.selectById(invoiceId);
        if ("GZ".equalsIgnoreCase(impmasterDO.getSupplierCode())) {
            return updGZImpShipAmount(invoiceId, "GZ");
        } else {
            return updCNImpShipAmount(invoiceId, impmasterDO.getSupplierCode());
        }
    }

    private ResultVo<String> updGZImpShipAmount(Long invoiceId, String supplierCode) {
        //更新发货明细单价
        LambdaQueryWrapper<ImpInvoiceDetailDO> query = new LambdaQueryWrapper<>();
        query.select(ImpInvoiceDetailDO::getFullOrderNo, ImpInvoiceDetailDO::getOverseaInvoiceNo,
                ImpInvoiceDetailDO::getPrice);
        query.eq(ImpInvoiceDetailDO::getInvoiceId, invoiceId);
        List<ImpInvoiceDetailDO> detaillist = impInvoiceDetailMapper.selectList(query);
        for (ImpInvoiceDetailDO detailDO : detaillist) {
            //更新发票明细单价
            LambdaUpdateWrapper<ImpInvoiceDetailDO> updatedetailWrapper = new LambdaUpdateWrapper<>();
            updatedetailWrapper.eq(ImpInvoiceDetailDO::getInvoiceNo, detailDO.getOverseaInvoiceNo())
                    .eq(ImpInvoiceDetailDO::getFullOrderNo, detailDO.getFullOrderNo());
            ImpInvoiceDetailDO updatedetailDO = new ImpInvoiceDetailDO();
            updatedetailDO.setPrice(detailDO.getPrice());
            impInvoiceDetailMapper.update(updatedetailDO, updatedetailWrapper);

            //更新成本明细单价
            LambdaUpdateWrapper<PoInvoiceDetailDO> updatepodetailWrapper = new LambdaUpdateWrapper<>();
            updatepodetailWrapper.eq(PoInvoiceDetailDO::getInvoiceNo, detailDO.getOverseaInvoiceNo())
                    .eq(PoInvoiceDetailDO::getOrderNo, detailDO.getFullOrderNo())
                    .ne(PoInvoiceDetailDO::getStatus, "9");
            PoInvoiceDetailDO updatepodetailDO = new PoInvoiceDetailDO();
            updatepodetailDO.setPrice(detailDO.getPrice());
            updatepodetailDO.setPriceRmb(detailDO.getPrice());
            poInvoiceDetailMapper.update(updatepodetailDO, updatepodetailWrapper);
        }
        //增值税发票原发票号（广州delivery）
        List<String> overseaNolist = poInvoiceDetailMapper.listInvoiceoverseaNo(invoiceId);

        if (overseaNolist == null || overseaNolist.size() == 0) {
            return ResultVo.failure("更新失败");
        }
        //增值税发票日期
        Date invoicedate = poInvoiceDetailMapper.getImpInvoiceDate(invoiceId);

        //增值税发票总金额
        BigDecimal sumAmount = poInvoiceDetailMapper.getImpInvoiceAmount(invoiceId);
        //发货数据所有原发票号合计金额（广州delivery）
        BigDecimal sumShipAmount = poInvoiceDetailMapper.getsumShiptAmount(overseaNolist);
        ImpInvoiceMasterDO updmasterDO = new ImpInvoiceMasterDO();
        updmasterDO.setId(invoiceId);
        //增值和发货金额比较，一样则把增值税发票改成成本结算
        if (sumAmount != null && sumShipAmount != null && sumAmount.compareTo(sumShipAmount) == 0) {
            updmasterDO.setStatus(6);
        }

        //更新发货发票的发票日期（所有原发票号作为主项发票号的）
        for (String invoiceNo : overseaNolist) {
            List<Long> shipinvoiceids = impInvoiceDetailMapper.getInvoiceIdByNo(invoiceNo, "GZ");
            Long shipinvoiceid = shipinvoiceids.get(0);
            //更新明细人民币金额
            poInvoiceDetailMapper.updateGZPOInvoiceDetailAmount(shipinvoiceid);
            //更新明细合计
            poInvoiceDetailMapper.updatePoInvoiceDetailAmountTotal(shipinvoiceid);
            //更新合计
            OpsPoInvoiceDO sumpoInvoice = poInvoiceMasterMapper.getPoInvoiceDetailAmount(shipinvoiceid);
            if (sumpoInvoice == null || sumpoInvoice.getAmount() == null) {
                continue;
            }
            UpdateWrapper<OpsPoInvoiceDO> updatepoWrapper = new UpdateWrapper<>();
            updatepoWrapper.eq("invoice_id", shipinvoiceid);
            OpsPoInvoiceDO updamount = new OpsPoInvoiceDO();
            updamount.setAmount(sumpoInvoice.getAmount());
            updamount.setAmountRmb(sumpoInvoice.getAmountRmb());
            updamount.setAmounttotal(sumpoInvoice.getAmounttotal());
            poInvoiceMasterMapper.update(updamount, updatepoWrapper);
//            poInvoiceMasterMapper.updatepoInvoiceMasterAmountTotal(shipinvoiceid);

            UpdateWrapper<ImpInvoiceMasterDO> updatemaserWrapper = new UpdateWrapper<>();
            updatemaserWrapper.eq("invoice_no", invoiceNo);
            updatemaserWrapper.eq("supplier_code", "GZ");
            ImpInvoiceMasterDO updmaster = new ImpInvoiceMasterDO();
            updmaster.setInvoiceDate(invoicedate);
            impInvoiceMasterMapper.update(updmaster, updatemaserWrapper);

            //增值税金额等于发货金额，直接成本入库
            BigDecimal invoiceamount = poInvoiceDetailMapper.getImpInvoiceAmountByoverseaInvoiceNo(invoiceNo, supplierCode);
            BigDecimal podetailamount = poInvoiceDetailMapper.getImpDetailAmountByInvoiceNo(invoiceNo, supplierCode);
            if (invoiceamount != null && podetailamount != null && invoiceamount.compareTo(podetailamount) == 0) {
                PoInvoiceToCostDTO costdto = null;
                costAccounting(shipinvoiceid, costdto);
                ResultVo<String> poresultVo = poInvoiceService.doDataToCost(shipinvoiceid, null);
                if (poresultVo != null && !poresultVo.isSuccess()) {
                    updmasterDO.setStatus(null);
                }
            } else {
                updmasterDO.setStatus(null);
            }
        }

        //所有匹配原发票号的发货金额
        updmasterDO.setShipAmount(sumShipAmount);
        impInvoiceMasterMapper.updateById(updmasterDO);
        return ResultVo.successMsg("更新成功，更新金额为" + sumShipAmount);
    }

    private ResultVo<String> updCNImpShipAmount(Long invoiceId, String supplierCode) {
        //增值税发票原发票号（广州delivery）
        List<String> overseaNolist = poInvoiceDetailMapper.listInvoiceoverseaNo(invoiceId);
        //增值税发票日期
        Date invoicedate = poInvoiceDetailMapper.getImpInvoiceDate(invoiceId);

        //增值税发票总金额
        BigDecimal sumAmount = poInvoiceDetailMapper.getImpInvoiceAmount(invoiceId);
        //发货数据所有原发票号合计金额（广州delivery）
        BigDecimal sumShipAmount = poInvoiceDetailMapper.getsumShiptAmount(overseaNolist);
        ImpInvoiceMasterDO updmasterDO = new ImpInvoiceMasterDO();
        updmasterDO.setId(invoiceId);
        //增值和发货金额比较，一样则把增值税发票改成成本结算
//        if(sumAmount!=null && sumShipAmount!=null && sumAmount.compareTo(sumShipAmount)==0){
//            updmasterDO.setStatus(6);
//        }

        //更新发货发票的发票日期（所有原发票号作为主项发票号的）
        for (String invoiceNo : overseaNolist) {
            UpdateWrapper<ImpInvoiceMasterDO> updatemaserWrapper = new UpdateWrapper<>();
            updatemaserWrapper.eq("invoice_no", invoiceNo);
            updatemaserWrapper.eq("supplier_code", supplierCode);
//            updatemaserWrapper.in("supplier_code", "CM", "CN", "TZ", "CT", "YZ");
            ImpInvoiceMasterDO updmaster = new ImpInvoiceMasterDO();
            updmaster.setInvoiceDate(invoicedate);
            impInvoiceMasterMapper.update(updmaster, updatemaserWrapper);

            //增值税金额等于发货金额，直接成本入库
//            List<Long> shipinvoiceids=impInvoiceDetailMapper.getInvoiceIdByNo(invoiceNo,supplierCode);
//            BigDecimal invoiceamount = poInvoiceDetailMapper.getImpInvoiceAmountByoverseaInvoiceNo(invoiceNo,supplierCode);
//            BigDecimal podetailamount = poInvoiceDetailMapper.getImpDetailAmountByInvoiceNo(invoiceNo,supplierCode);
//            if(invoiceamount!=null && podetailamount!=null && invoiceamount.compareTo(podetailamount)==0){
//                for(Long shipinvoiceid:shipinvoiceids){
//                    ResultVo<String> poresultVo = poInvoiceService.doDataToCost(shipinvoiceid, null);
//                    if(poresultVo!=null && !poresultVo.isSuccess()){
//                        updmasterDO.setStatus(null);
//                    }
//                }
//            }else{
//                updmasterDO.setStatus(null);
//            }
        }

        //所有匹配原发票号的发货金额
        updmasterDO.setShipAmount(sumShipAmount);
        impInvoiceMasterMapper.updateById(updmasterDO);
        return ResultVo.successMsg("更新成功，更新金额为" + sumShipAmount);
    }

    @Override
    public ResultVo<String> redoCostInvoice(String invoiceId) {
        LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsPoInvoiceDO::getInvoiceId, invoiceId);

        OpsPoInvoiceDO invoiceDO = poInvoiceMasterMapper.selectOne(queryWrapper);
        if (DateUtil.getDiffDay(invoiceDO.getCostTime(), new Date()) > 30) {
            return ResultVo.failure("已结转超过30天的不可重新结转");
        }
        invoiceDO.setStatus(2);
        int i = poInvoiceMasterMapper.updateById(invoiceDO);

        return i == 1 ? ResultVo.success("操作成功") : ResultVo.failure("操作失败");
    }

    //<!--add by WuWeiDong 20221104 task 2089 -->
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<List<POOrderNOVO>> listPOOrder(List<POOrderNODTO> orderNos) {
        if (PublicUtil.isEmpty(orderNos) || orderNos.size() == 0) {
            return ResultVo.failure("请输入查询采购单号");
        }
        List<POOrderNOVO> returnPOOrderNOVOs = new ArrayList<>(1);
        List<POOrderNOVO> listPOOrder = new ArrayList<>(1);
        for (POOrderNODTO item : orderNos) {
            listPOOrder.add(new POOrderNOVO(item.getOrderNo(), item.getOrderItem()));

        }
        //拆开查询
        List<POOrderNOVO> queryOrderNoVo = new ArrayList<>(1);
        List<POOrderNOVO> poOrderNOVOS = new ArrayList<>(1);
        int count = listPOOrder.size();
        int offset = 49;//一次查询项数
        for (int i = 0; i < count; i++) {
            if ((count) - (i + offset) < 0) {
                queryOrderNoVo = listPOOrder.subList(i, count);
            } else {
                queryOrderNoVo = listPOOrder.subList(i, i + offset + 1);
            }
            i = i + offset;
            long startTimer = System.currentTimeMillis();
            List<POOrderNOVO> returnOrderNOVO = opsPurchaseInvoiceMapper.listPOOrder(queryOrderNoVo);
            long endTimer = System.currentTimeMillis();
            log.info(i + " 查询结束耗时(s): " + (endTimer - startTimer) / 1000);
            if (PublicUtil.isNotEmpty(returnOrderNOVO)) {
                poOrderNOVOS.addAll(returnOrderNOVO);
            }
        }
///----一次性查询
//        long startTimer = System.currentTimeMillis();
//        List<POOrderNOVO> poOrderNOVOS = opsPurchaseInvoiceMapper.listPOOrder(listPOOrder);
//        long endTimer = System.currentTimeMillis();
//        log.info(" 查询结束耗时(s): " + (endTimer - startTimer) / 1000);


//查出结果合拼到关务订单
        Map<String, POOrderNOVO> collect = Stream.concat(listPOOrder.stream(), poOrderNOVOS.stream())
                .collect(Collectors.toMap(k -> k.getPoNo() + "-" + k.getLineItem(), v -> v, (d1, d2) -> {
                    d1.setModelNo(d2.getModelNo());
                    d1.setQuantity(d2.getQuantity());
                    d1.setLeftQuantity(d2.getLeftQuantity());
                    d1.setStateCode(d2.getStateCode());
                    d1.setSupplierId(d2.getSupplierId());
                    d1.setReceiveWarehouseId(d2.getReceiveWarehouseId());
                    d1.setPurchaseDate(d2.getPurchaseDate());
                    return d1;
                }));

        for (Object o : collect.values()) {
            returnPOOrderNOVOs.add(POOrderNOVO.class.cast(o));
        }
        return ResultVo.success(returnPOOrderNOVOs);
    }

    /**
     * add by WuWeiDong 20231110  bug 12560   导出数据条件验证。
     *
     * @param request
     * @return
     */
    private ResultVo<String> checkQueryParam(PoInvoiceMasterRequest request) {
        if (Objects.isNull(request.getId()) && Objects.isNull(request.getInvoiceId()) && StringUtils.isBlank(request.getInvoiceNo())
                && (Objects.isNull(request.getFromDate()) || Objects.isNull(request.getToDate()))) {
            return ResultVo.failure("请输入查询日期范围。");
        }
        if (Objects.nonNull(request.getFromDate()) && Objects.nonNull(request.getToDate())) {
            int diffMonth = DateUtil.getDiffMonth(request.getFromDate(), request.getToDate());
            if (diffMonth > 12) {
                log.error("日期范围差不能超过12月。：{} -> {} :{}天", request.getFromDate(), request.getToDate(), diffMonth);
                return ResultVo.failure("日期范围差不能超过12月。 当前差:" + String.valueOf(diffMonth));
            }
        }
        return ResultVo.success();
    }
}
