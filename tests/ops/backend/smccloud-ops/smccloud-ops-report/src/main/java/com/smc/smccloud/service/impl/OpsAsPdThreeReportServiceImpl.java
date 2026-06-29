package com.smc.smccloud.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.EmailUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.pd.*;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.WarehouseVO;
import com.smc.smccloud.model.enums.PdStateEnum;
import com.smc.smccloud.model.pd.*;
import com.smc.smccloud.service.*;
import com.smc.smccloud.util.EasyExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author lyc
 * @Date 2023/12/19 10:20
 * @Descripton TODO
 */
@Service
@Slf4j
public class OpsAsPdThreeReportServiceImpl implements OpsAsPdThreeReportService {

    @Resource
    private OpsAsPdThreeReportMapper opsAsPdThreeReportMapper;

    @Resource
    private OpsCommonService opsCommonService;

    @Resource
    private OpsAsPdThreeReportWareMapper opsAsPdThreeReportWareMapper;

    @Resource
    private OpsAsPdBatchMapper opsAsPdBatchMapper;
    @Resource
    private DictCommonService dictCommonService;

    @Resource
    private JavaMailSenderImpl mailSender;

    @Resource
    private OpsAsPdCommonMapper opsAsPdCommonMapper;

    @Resource
    private PdService pdService;

    @Resource
    private OpsInventoryOpeningMapper opsInventoryOpeningMapper;

    @Override
    public ResultVo<PageInfo<OpsAsPdThreeReportDO>> pdReportList(PdReportParamVO paramVO) {
        if (paramVO == null) {
            return ResultVo.failure("参数为空");
        }
        LambdaQueryWrapper<OpsAsPdThreeReportDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(paramVO.getPdBatchNo()),OpsAsPdThreeReportDO::getPdBatchNo,paramVO.getPdBatchNo());
        if (StringUtils.isNotBlank(paramVO.getModelNo())) {
            if (paramVO.getModelNo().contains("%")) {
                lambdaQueryWrapper.likeRight(StringUtils.isNotBlank(paramVO.getModelNo()),OpsAsPdThreeReportDO::getModelNo,paramVO.getModelNo());
            }else {
                lambdaQueryWrapper.eq(StringUtils.isNotBlank(paramVO.getModelNo()),OpsAsPdThreeReportDO::getModelNo,paramVO.getModelNo());
            }
        }

        PageInfo<OpsAsPdThreeReportDO> pageInfo = PageHelper.startPage(paramVO.getPage().getPageNumber(), paramVO.getPage().getPageSize())
                .doSelectPageInfo(() -> opsAsPdThreeReportMapper.selectList(lambdaQueryWrapper));

        if(CollectionUtils.isNotEmpty(pageInfo.getList())) {
            LambdaQueryWrapper<OpsAsPdThreeReportWareDO> queryWrapper = new LambdaQueryWrapper<>();
            for (OpsAsPdThreeReportDO item: pageInfo.getList()) {
                queryWrapper.clear();
                queryWrapper
                        .eq(OpsAsPdThreeReportWareDO::getPdBatchNo,item.getPdBatchNo())
                        .eq(OpsAsPdThreeReportWareDO::getModelNo,item.getModelNo());
                List<OpsAsPdThreeReportWareDO> threeReportWareDOS = opsAsPdThreeReportWareMapper.selectList(queryWrapper);
                int sum = threeReportWareDOS.stream()
                        .mapToInt(OpsAsPdThreeReportWareDO::getLastInitialQuantity).sum();
                item.setLastInitialQuantity(sum);
            }
        }

        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<String> markPdReport() {

        long lstart = System.currentTimeMillis();
        log.info("开始生成盘点报表==>");
        LoginUserDTO loginAuthDto;
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            return ResultVo.failure("当前登录信息失效,请重新登录");
        }

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = pdService.getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess()) {
            return ResultVo.failure("获取当前盘点批次号失败");
        }
        if (batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("当前不存在已激活的盘点批次,请确认");
        }
        String pd_batch_no = batchNoWithIsActive.getData().getPdBatchNo();

        /**
         * 盘点数据生成时同时向库存期初管理表中写入盘点时点对应的库存期初数据。
         *1. 查询库存期初管理表是否存在历史年度盘点批次号
         *2. 如果不存在历史年度盘点批次号 则将实在库更进入库存期初管理表，
         *   如果存在历史年度盘点批次号，则将上期盘点实在库更新到当前年度库存期初数。
         */
        List<String> historyInventoryBatchNo = opsAsPdCommonMapper.getHistoryInventoryBatchNo();
        if (CollectionUtils.isEmpty(historyInventoryBatchNo)) {
            return ResultVo.failure("未同步上期盘点库存数据,无法写入盘点时点对应的库存期初数据。");
        }
        if(historyInventoryBatchNo.size()>1) {
            opsAsPdCommonMapper.updateIventoryOpenIngLastInitQty(historyInventoryBatchNo.get(1),historyInventoryBatchNo.get(0));
//            LambdaQueryWrapper<OpsInventoryOpeningDO> lambdaQueryWrapper =new LambdaQueryWrapper<>();
//            lambdaQueryWrapper.eq(OpsInventoryOpeningDO::getBatchNo, historyInventoryBatchNo.get(0));
//            List<OpsInventoryOpeningDO> opsInventoryOpeningDOS = opsInventoryOpeningMapper.selectList(lambdaQueryWrapper);
//            if (CollectionUtils.isNotEmpty(opsInventoryOpeningDOS)) {
//                ExecutorService fixedThreadPoolInsert = Executors.newFixedThreadPool(10);
//                try {
//                    for (OpsInventoryOpeningDO item : opsInventoryOpeningDOS) {
//
//                        fixedThreadPoolInsert.execute(() -> {
//
//                            LambdaQueryWrapper<OpsInventoryOpeningDO> queryWrapper = new LambdaQueryWrapper<>();
//                            queryWrapper
//                                    .eq(OpsInventoryOpeningDO::getBatchNo, historyInventoryBatchNo.get(1))
//                                    .eq(OpsInventoryOpeningDO::getInventoryId, item.getInventoryId());
//                            OpsInventoryOpeningDO opsInventoryOpeningDO = opsInventoryOpeningMapper.selectOne(queryWrapper);
//
//                            LambdaUpdateWrapper<OpsInventoryOpeningDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//                            lambdaUpdateWrapper
//                                    .eq(OpsInventoryOpeningDO::getBatchNo, historyInventoryBatchNo.get(0))
//                                    .eq(OpsInventoryOpeningDO::getInventoryId, item.getInventoryId())
//                                    .set(OpsInventoryOpeningDO::getInitialQuantity, opsInventoryOpeningDO.getQuantity())
//                                    .set(OpsInventoryOpeningDO::getInitialTime,new Date());
//                            opsInventoryOpeningMapper.update(null, lambdaUpdateWrapper);
//
//                        });
//                    }
//                } finally {
//                    fixedThreadPoolInsert.shutdown();
//                    while (true) {
//                        if (fixedThreadPoolInsert.isTerminated()) {
//                            break;
//                        }
//                    }
//                    try {
//                        Thread.sleep(3000*60);
//                    } catch (Exception e) {
//                        log.error("线程池等待异常",e);
//                        e.printStackTrace();
//                        throw new RuntimeException("线程池等待异常"+e.getMessage());
//                    }
//                }
//            }
        } else {
            opsAsPdCommonMapper.updateInventoryinitialQuantity();
        }

        try {
            opsAsPdThreeReportMapper.callAsPdThreeTeportWare(pd_batch_no);
            opsAsPdThreeReportMapper.callAsPdThreeTeport(pd_batch_no);
        } catch (Exception e) {
            log.error("生成盘点报表异常: {}",e.getMessage(),e);
            throw new BusinessException("生成盘点报表异常:"+e.getMessage());
        }
        log.info("存储过程执行完毕 =>  共计耗时 {} 秒",(System.currentTimeMillis() - lstart) / 1000);

        // 获取各有效仓库
        ResultVo<List<WarehouseVO>> master = opsCommonService.getWarehouseByType("MASTER");
        ResultVo<List<WarehouseVO>> sub = opsCommonService.getWarehouseByType("SUB");
        if (!master.isSuccess()) {
            return ResultVo.failure("获取有效中心仓仓库失败");
        }
        if(!sub.isSuccess()) {
            return ResultVo.failure("获取有效分库失败");
        }
        List<WarehouseVO> masterWareHouseList = master.getData();
        List<WarehouseVO> subWareHouseList = sub.getData();
        masterWareHouseList.addAll(subWareHouseList);

        for (WarehouseVO item : masterWareHouseList) {
            String wareHouseCode = item.getWarehouseCode().toLowerCase();
            // 更新各仓的业务数
            opsAsPdThreeReportMapper.updateYwQtyForWareHouse(wareHouseCode,pd_batch_no);
            // 更新各仓的盘点数
            opsAsPdThreeReportMapper.updateWlQtyForWareHouse(wareHouseCode,pd_batch_no);
            // 更新各仓的物流盘点数-业务账簿数
            opsAsPdThreeReportMapper.updateWlQtySubtractYwQty(wareHouseCode,pd_batch_no);
        }
        long lend = System.currentTimeMillis();
        log.info("生成报表完毕=> 共计耗时 {} 秒",(lend-lstart)/1000);


        /**
         * 盘点生成的三方报表中增加指标【OPS上期盘点期初数】，该指标取数数值为上期盘点的OPS侧的合计数即ops_sum_qty。
         * 1. 获取上期盘点批次号
         * 2. 拿到上次盘点的所有仓库库存数据
         * 3. 遍历数据 根据型号 仓库 盘点批次号 拿到当期数据 设置上次盘点库存期初数
         */
        // 1. 获取上次盘点批次号
        List<String> lastPdBatchNo = opsAsPdBatchMapper.getLastPdBatchNo();

        if(CollectionUtils.isNotEmpty(lastPdBatchNo)) {
            String lastBatchNo = "";
            if (lastPdBatchNo.size() > 1) {
                opsAsPdCommonMapper.updateOpsAsPdThreeReportWare(lastPdBatchNo.get(0), lastPdBatchNo.get(1));
//                lastBatchNo = lastPdBatchNo.get(1);
//
//                // 2.拿到上次盘点的所有仓库库存数据
//                LambdaQueryWrapper<OpsAsPdThreeReportWareDO> queryWrapper = new LambdaQueryWrapper<>();
//                queryWrapper.eq(OpsAsPdThreeReportWareDO::getPdBatchNo,lastBatchNo)
//                        .isNotNull(OpsAsPdThreeReportWareDO::getWarehouseCode);
//                List<OpsAsPdThreeReportWareDO> threeReportWareDOS = opsAsPdThreeReportWareMapper.selectList(queryWrapper);
//
//                LambdaUpdateWrapper<OpsAsPdThreeReportWareDO> updateWrapper = new LambdaUpdateWrapper<>();
//                LambdaQueryWrapper<OpsAsPdThreeReportWareDO> query = new LambdaQueryWrapper<>();
//                for (OpsAsPdThreeReportWareDO item : threeReportWareDOS) {
//                    // 3. 遍历数据 根据型号 仓库 盘点批次号 拿到当期数据 设置上次盘点库存期初数
//                    query.clear();
//                    query.eq(OpsAsPdThreeReportWareDO::getModelNo,item.getModelNo())
//                            .eq(OpsAsPdThreeReportWareDO::getWarehouseCode,item.getWarehouseCode())
//                            .eq(OpsAsPdThreeReportWareDO::getPdBatchNo,pd_batch_no);
//                    try {
//                        // 获取本次盘点的数据,设置上次盘点库存期初数
//                        OpsAsPdThreeReportWareDO threeReportWareDO = opsAsPdThreeReportWareMapper.selectOne(query);
//                        if (threeReportWareDO == null) {
//                            continue;
//                        }
//                        updateWrapper.clear();
//                        updateWrapper.eq(OpsAsPdThreeReportWareDO::getPdBatchNo,pd_batch_no)
//                                .eq(OpsAsPdThreeReportWareDO::getWarehouseCode,item.getWarehouseCode())
//                                .eq(OpsAsPdThreeReportWareDO::getModelNo,item.getModelNo())
//                                .set(OpsAsPdThreeReportWareDO::getLastInitialQuantity,threeReportWareDO.getOpsSumQty());
//                        opsAsPdThreeReportWareMapper.update(null,updateWrapper);
//                    } catch (Exception e) {
//                        log.error("{},{},{},获取as_pd_three_report_ware查出多条",item.getModelNo(),item.getWarehouseCode(),pd_batch_no,e);
//                    }
//                }
            } else {
                lastBatchNo = lastPdBatchNo.get(0);
                log.error("{},初次盘点,没有上期盘点期初数",lastBatchNo);
            }
        }

        // 本次盘点的状态变更为【已生成盘点报表】，本次盘点批次号自动失效
        LambdaUpdateWrapper<OpsAsPdBatchDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(OpsAsPdBatchDO::getPdBatchNo,pd_batch_no)
                // .set(OpsAsPdBatchDO::getIsActive,"2")
                .set(OpsAsPdBatchDO::getPdState,PdStateEnum.generateReport.getCode())
                .set(OpsAsPdBatchDO::getUpdateTime,new Date())
                .set(OpsAsPdBatchDO::getUpdateUser,loginAuthDto.getUserNo());
        opsAsPdBatchMapper.update(null,updateWrapper);
        return ResultVo.success("生成盘点报表完毕");
    }

    @Override
    public void exportThreePdReport(HttpServletResponse response, PdReportParamVO paramVO) {
        LambdaQueryWrapper<OpsAsPdThreeReportDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsAsPdThreeReportDO::getPdBatchNo,paramVO.getPdBatchNo());
        queryWrapper.eq(StringUtils.isNotBlank(paramVO.getModelNo()),OpsAsPdThreeReportDO::getModelNo,paramVO.getModelNo());
        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(6000);
        PageInfo<OpsAsPdThreeReportDO> pageInfo;
        List<OpsAsPdThreeReportVO> list = new ArrayList<>();
        while (true) {
            pageInfo =  PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> opsAsPdThreeReportMapper.selectList(queryWrapper));

            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                return;
            }
            list.addAll(BeanCopyUtil.copyList(pageInfo.getList(),OpsAsPdThreeReportVO.class));
            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        try {
            String fileName = URLEncoder.encode("三方报表", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            EasyExcel.write(outputStream)
                    .head(OpsAsPdThreeReportVO.class)
                    .sheet("Sheet1")
                    .doWrite(list);
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            Map<String,InputStream> map = new HashMap<>();
            map.put("三方报表.xlsx",inputStream);
            ResultVo<DataTypeVO> pd = dictCommonService.getDataTypeCodesInfo("9004", "PDREPORT");
            if (!pd.isSuccess()) {
                throw new BusinessException("导出三方报表时,获取邮箱失败");
            }
            EmailUtil.send(mailSender,pd.getData().getExtNote1(),pd.getData().getExtNote2(),pd.getData().getExtNote3(),"三方报表","三方报表导出完毕,请查看附件内容.",map);
            log.info("三方报表导出完毕,数据已发送至邮件..");
        } catch (IOException e) {
            response.reset();
            log.info("导出三方报表异常: 异常信息 {}",e.getMessage(),e);
            throw new RuntimeException("导出三方报表数据发生异常: "+e.getMessage());
        }
    }

    @Override
    public void exportTwoPdReport(HttpServletResponse response) {
        LambdaQueryWrapper<OpsAsPdThreeReportDO> queryWrapper = new LambdaQueryWrapper<>();
        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(6000);
        PageInfo<OpsAsPdThreeReportDO> pageInfo;
        List<OpsAsPdThreeReportVOWithTwo> list = new ArrayList<>();
        while (true) {
            pageInfo =  PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> opsAsPdThreeReportMapper.selectList(queryWrapper));

            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                return;
            }
            list.addAll(BeanCopyUtil.copyList(pageInfo.getList(),OpsAsPdThreeReportVOWithTwo.class));
            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        try {
            String fileName = URLEncoder.encode("两方报表", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            EasyExcel.write(outputStream)
                    .head(OpsAsPdThreeReportVOWithTwo.class)
                    .sheet("Sheet1")
                    .doWrite(list);
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            Map<String,InputStream> map = new HashMap<>();
            map.put("两方报表.xlsx",inputStream);
            ResultVo<DataTypeVO> pd = dictCommonService.getDataTypeCodesInfo("9004", "PDREPORT");
            if (!pd.isSuccess()) {
                throw new BusinessException("导出两方报表时,获取邮箱失败");
            }
            EmailUtil.send(mailSender,pd.getData().getExtNote1(),pd.getData().getExtNote2(),pd.getData().getExtNote3(),"两方报表","两方报表导出完毕,请查看附件内容.",map);
            log.info("两方报表导出完毕,数据已发送至邮件..");
        } catch (IOException e) {
            response.reset();
            log.info("导出两方报表异常: 异常信息 {}",e.getMessage(),e);
            throw new RuntimeException("导出两方报表数据发生异常: "+e.getMessage());
        }
    }
}
