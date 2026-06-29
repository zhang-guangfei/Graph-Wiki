package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.FileCompressUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.binorder.ModelExpFreqByJobMapper;
import com.smc.smccloud.model.binorder.*;
import com.smc.smccloud.service.BinTrialJobManageService;
import com.smc.smccloud.service.ModelExpFreqByJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author wuweidong
 * @create 2023/6/8 14:06
 * @description @description --add by WuWeiDong 20230608  bug 10843  	自定义bin计算
 */
@Slf4j
@Service
@DS("opsreport")
public class ModelExpFreqByJobImpl implements ModelExpFreqByJobService {

    @Value("${file.base}")
    private String serverPath;
    @Resource
    private ModelExpFreqByJobMapper modelExpFreqByJobMapper;
    @Resource
    private BinTrialJobManageService binTrialJobManageService;

    @Override
    public ResultVo<PageInfo<ModelExpFreqByJobVO>> listBinTrialSalesBranchDetail(ModelExpFreqByJobRequestDTO request) {

        ResultVo<LambdaQueryWrapper<ModelExpFreqByJobDO>> result = this.setModelExpFreqByJobqueryWrapper(request);
        if (!result.isSuccess()) {
            return ResultVo.failure(result.getMessage());
        }
        LambdaQueryWrapper<ModelExpFreqByJobDO> queryWrapper = result.getData();
        queryWrapper.orderByAsc(ModelExpFreqByJobDO::getId);

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<ModelExpFreqByJobDO> list = modelExpFreqByJobMapper.selectList(queryWrapper);

        PageInfo<ModelExpFreqByJobDO> pageInfo = PageInfo.of(list);
        return ResultVo.success(BeanCopyUtil.pageDto2Vo(pageInfo, ModelExpFreqByJobVO.class));

    }

    @Override
    public void exporBinTrialSalesBranchDetail(ModelExpFreqByJobRequestDTO request, HttpServletResponse response) {

        if (PublicUtil.isEmpty(request.getJobId())
                && PublicUtil.isEmpty(request.getSalesBranchIds())
                && PublicUtil.isEmpty(request.getModelNos())) {
            throw new BusinessException("请输入查询条件。 ");
        }
        ResultVo<ModelExpFreqByJobRequestDTO> result = this.setModelExpFreqByJobQueryDto(request);
        if (!result.isSuccess()) {
            throw new BusinessException(result.getMessage());
        }

        long startTimer = System.currentTimeMillis();
        String fileName = "BinBaseData" + DateUtil.getFormatDate(DateUtil.getNow(), "yyyyMMddHH");
        String path = serverPath + "Bin" + File.separator + fileName + ".csv";
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        // 如果文件已存在，则删掉
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new BusinessException("文件创建失败: " + e.getMessage(), e);
        }
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GB2312"))) {
            // 写入表头
            this.writeHeader(bw);
            Map<String, Object> rangeInfo = modelExpFreqByJobMapper.getIdRange(request.getJobId(), request.getWarehouseCode(), request.getModelType(), Arrays.asList(request.getModelNos()));

            if (rangeInfo == null || rangeInfo.get("totalCount") == null) {
                log.warn("{} 无数据,跳过导出", fileName);
                return;
            }

            long minId = rangeInfo.get("minId") != null ? ((Number) rangeInfo.get("minId")).longValue() : 0L;
            long maxId = rangeInfo.get("maxId") != null ? ((Number) rangeInfo.get("maxId")).longValue() : 0L;
            long totalCount = ((Number) rangeInfo.get("totalCount")).longValue();

            if (totalCount == 0) {
                log.warn("{} 无数据,跳过导出", fileName);
                return;
            }

            log.info("{} 数据范围: ID {} ~ {},共 {} 条", fileName, minId, maxId, totalCount);


            long exportedCount = 0;
            long currentId = minId;
            int batchSize = 100000;
            // 针对300-500万条大数据量优化:每1000行批量写入一次,减少IO次数
            int batchWriteSize = 1000;
            // 单行约900字节,1000行约900KB,初始容量设为1MB避免扩容
            StringBuilder batchBuffer = new StringBuilder(1048576);

            while (currentId <= maxId) {
                long endId = Math.min(currentId + batchSize - 1, maxId);

                // TOP分页查询
                List<ModelExpFreqByJobDO> batch = modelExpFreqByJobMapper.selectByIdRange(
                        request.getJobId(), request.getWarehouseCode(), request.getModelType(), Arrays.asList(request.getModelNos()),
                        currentId,
                        endId,
                        batchSize
                );

                if (batch.isEmpty()) {
                    currentId = endId + 1;
                    continue;
                }

                // 批量构建CSV行
                for (ModelExpFreqByJobDO info : batch) {
                    convertToCsvRow(batchBuffer, info);
                    exportedCount++;

                    // 达到批量写入阈值时,一次性写入
                    if (exportedCount % batchWriteSize == 0) {
                        bw.write(batchBuffer.toString());
                        batchBuffer.setLength(0); // 清空缓冲区
                    }
                }

                // 写入剩余数据
                if (batchBuffer.length() > 0) {
                    bw.write(batchBuffer.toString());
                    batchBuffer.setLength(0);
                }

                currentId = batch.get(batch.size() - 1).getId() + 1;

                // 进度日志(每10万条打印一次)
                if (exportedCount % 100000 == 0) {
                    long percent = 100 * exportedCount / totalCount;
                    log.info("{} 进度: {}/{} 条 ({}%)", fileName, exportedCount, totalCount, percent);
                }
            }

            bw.flush();
            bw.close();

            String zipPath = serverPath + "Bin" + File.separator + fileName + ".zip";
            File zipFile = new File(zipPath);
            File tofile = new File(path);
            if (zipFile.exists()) {
                zipFile.delete();
            }
            FileCompressUtil.zipFiles(new File[]{tofile}, zipFile);  //压缩

            InputStream is = new FileInputStream(zipFile);
            byte[] b = new byte[10240];
            int len;
            try {
                while ((len = is.read(b)) > 0) {
                    response.getOutputStream().write(b, 0, len);
                }
            } catch (IOException e) {
                log.error("exportBinConfigureData failure, reason = {}", e.getMessage(), e);
            } finally {
                try {
                    is.close();
                    log.info("exportBinConfigureData 完成(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);
                } catch (IOException e) {
                    log.error("exportBinConfigureData ->输出流关闭失败: {}", e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            log.error("exportBinConfigureData failure, reason = {}", e.getMessage(), e);
            throw new BusinessException("错误：" + e.getMessage());
        }
    }

    /**
     * Bin自定义计算
     *
     * @param jobId  任务编号id
     * @param master 0第一仓库，1第一物流中心
     * @return
     */
    @Override
    public ResultVo<String> updateModelExpFreqByJob(Long jobId, Integer master) {
        try {
            long startTimer = System.currentTimeMillis();

            Map<String, Object> map = new HashMap<>();
            map.put("jobid", jobId);
            map.put("master", master);
            map.put("result", 0);
            modelExpFreqByJobMapper.updateModelExpFreqByJob(map);
            log.info("updateModelExpFreqByJob 完成(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);
            return ResultVo.success("完成");

        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            return ResultVo.failure("完成错误：" + ex);
        }
    }

    /**
     * 更新型号信息
     * @param jobId
     * @param endDate
     * @return
     */
    @Override
    public ResultVo<String> calcModelExpFreqUpdModelInfoByJob(Long jobId, Date endDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("jobId", jobId);
        map.put("endDate", endDate);
        map.put("result", 0);
        modelExpFreqByJobMapper.calcModelExpFreqUpdModelInfoByJobII(map);
        return ResultVo.success("完成");
    }

    /**
     * 取起始时间
     * @param months
     * @return
     */
    @Override
    public Map<String, Date> getLastMonthRange(Integer months) {
        Map<String, Object> map = new HashMap<>();
        map.put("months", months);
        map.put("beginDate", null);
        map.put("endDate", null);
        map.put("result", 0);

        modelExpFreqByJobMapper.getLastMonthRange(map);

        Map<String, Date> rtnMap = new HashMap<>();
        rtnMap.put("beginDate", (Date) map.get("beginDate"));
        rtnMap.put("endDate", (Date) map.get("endDate"));
        return rtnMap;
    }


    private void writeHeader(BufferedWriter bw) {
        try {
            bw.write("型号,");
            bw.write("仓库,");
//        bw.write("统计类型,");
            bw.write("型号类别,");
            bw.write("等级,");
            bw.write("型号分类,");
//        bw.write("产品类别(1-标准品 2-简易特注品 3-特注品 4-集成型号 5-维修品 6-阀岛型号),");
            bw.write("产品区分(1-收敛品2-贩卖限制品4-Shikomi8-可拆分,");
            bw.write("最后下单月份,");
            bw.write("产品系列,");
            bw.write("供应商,");
            bw.write("原产地,");
            bw.write("E价,");
            bw.write("ECode,");
            bw.write("设定平均,");
            bw.write("设定级别,");
            bw.write("变动系数,");
            bw.write("移动平均变量1,");
            bw.write("移动平均变量2,");
            bw.write("移动平均变量3,");
            bw.write("最新月均,");
            // bw.write("部门,");
            bw.write("下单月数8,");
            bw.write("客户数8,");
            bw.write("下单数量8,");
            bw.write("月平均数量8,");
            bw.write("最多下单客户8,");
            bw.write("营业所代码8,");
            bw.write("最多客户的比例8,");
            bw.write("最多下单客户数量8,");
            bw.write("订单项数8,");
            bw.write("下单月数12,");
            bw.write("客户数12,");
            bw.write("下单数量12,");
            bw.write("月平均数量12,");
            bw.write("最多下单客户12,");
            bw.write("营业所代码12,");
            bw.write("最多客户的比例12,");
            bw.write("最多下单客户数量12,");
            bw.write("订单项数12,");
            bw.write("下单月数24,");
            bw.write("客户数24,");
            bw.write("下单数量24,");
            bw.write("月平均数量24,");
            bw.write("最多下单客户24,");
            bw.write("营业所代码24,");
            bw.write("最多客户的比例24,");
            bw.write("最多下单客户数量24,");
            bw.write("订单项数24,");
            bw.write("下单月数36,");
            bw.write("客户数36,");
            bw.write("下单数量36,");
            bw.write("月平均数量36,");
            bw.write("最多下单客户36,");
            bw.write("营业所代码36,");
            bw.write("最多客户的比例36,");
            bw.write("最多下单客户数量36,");
            bw.write("订单项数36,");
            bw.write("M1,");
            bw.write("M2,");
            bw.write("M3,");
            bw.write("M4,");
            bw.write("M5,");
            bw.write("M6,");
            bw.write("M7,");
            bw.write("M8,");
            bw.write("M9,");
            bw.write("M10,");
            bw.write("M11,");
            bw.write("M12,");
            bw.write("M13,");
            bw.write("M14,");
            bw.write("M15,");
            bw.write("M16,");
            bw.write("M17,");
            bw.write("M18,");
            bw.write("M19,");
            bw.write("M20,");
            bw.write("M21,");
            bw.write("M22,");
            bw.write("M23,");
            bw.write("M24,");
            bw.write("M25,");
            bw.write("M26,");
            bw.write("M27,");
            bw.write("M28,");
            bw.write("M29,");
            bw.write("M30,");
            bw.write("M31,");
            bw.write("M32,");
            bw.write("M33,");
            bw.write("M34,");
            bw.write("M35,");
            bw.write("M36");
            bw.newLine();
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 将单条数据转换为CSV行(使用StringBuilder批量构建)
     * @param buffer 缓冲区,用于累积多行数据
     * @param info 数据对象
     */
    private void convertToCsvRow(StringBuilder buffer, ModelExpFreqByJobDO info) {
        String delimiter = ",";
        
        buffer.append('"').append(info.getModelNo() == null ? "" : info.getModelNo()).append('"');
        buffer.append(delimiter);
        buffer.append(info.getWarehouseCode() == null ? "" : info.getWarehouseCode());
        buffer.append(delimiter);
        buffer.append("1".equals(info.getModelType()) ? "基础型号" : "订货型号");
        buffer.append(delimiter);
        buffer.append(info.getClassCode() == null ? "" : info.getClassCode());
        buffer.append(delimiter);
        buffer.append(info.getModelClass() == null ? "" : info.getModelClass());
        buffer.append(delimiter);
        buffer.append(Objects.isNull(info.getProductType()) ? "" : String.valueOf(info.getProductType()));
        buffer.append(delimiter);
        buffer.append(info.getLastOrdMonth() == null ? "" : DateUtil.getFormatDate(info.getLastOrdMonth(), "yyyy-MM-dd"));
        buffer.append(delimiter);
        buffer.append(info.getProductSeries() == null ? "" : info.getProductSeries());
        buffer.append(delimiter);
        buffer.append(info.getMainOrigin() == null ? "" : info.getMainOrigin());
        buffer.append(delimiter);
        buffer.append(info.getSecondOrigin() == null ? "" : info.getSecondOrigin());
        buffer.append(delimiter);
        buffer.append(info.getEPrice() == null ? "" : info.getEPrice().toString());
        buffer.append(delimiter);
        buffer.append(info.getECode() == null ? "" : info.getECode());
        buffer.append(delimiter);
        buffer.append(info.getSetMean() == null ? "" : info.getSetMean().toString());
        buffer.append(delimiter);
        buffer.append(info.getSetClassCode() == null ? "" : info.getSetClassCode());
        buffer.append(delimiter);
        buffer.append(info.getVariation() == null ? "" : info.getVariation().toString());
        buffer.append(delimiter);
        buffer.append(info.getMoveRate1() == null ? "" : info.getMoveRate1().toString());
        buffer.append(delimiter);
        buffer.append(info.getMoveRate2() == null ? "" : info.getMoveRate2().toString());
        buffer.append(delimiter);
        buffer.append(info.getMoveRate3() == null ? "" : info.getMoveRate3().toString());
        buffer.append(delimiter);
        buffer.append(info.getSetAvgQty() == null ? "" : info.getSetAvgQty().toString());
        buffer.append(delimiter);
        
        // 8/12/24/36月统计数据
        appendPeriodData(buffer, delimiter, info, 8);
        appendPeriodData(buffer, delimiter, info, 12);
        appendPeriodData(buffer, delimiter, info, 24);
        appendPeriodData(buffer, delimiter, info, 36);
        
        // M1-M36
        Integer[] monthData = {
            info.getM1(), info.getM2(), info.getM3(), info.getM4(), info.getM5(), info.getM6(),
            info.getM7(), info.getM8(), info.getM9(), info.getM10(), info.getM11(), info.getM12(),
            info.getM13(), info.getM14(), info.getM15(), info.getM16(), info.getM17(), info.getM18(),
            info.getM19(), info.getM20(), info.getM21(), info.getM22(), info.getM23(), info.getM24(),
            info.getM25(), info.getM26(), info.getM27(), info.getM28(), info.getM29(), info.getM30(),
            info.getM31(), info.getM32(), info.getM33(), info.getM34(), info.getM35(), info.getM36()
        };

        for (int i = 0; i < monthData.length; i++) {
            Integer m = monthData[i];
            buffer.append(m == null ? "" : m.toString());
            if (i < monthData.length - 1) {
                buffer.append(delimiter);
            }
        }
        
        buffer.append("\n");
    }

    /**
     * 追加周期统计数据到StringBuilder
     */
    private void appendPeriodData(StringBuilder buffer, String delimiter, ModelExpFreqByJobDO info, int period) {
        switch (period) {
            case 8:
                buffer.append(info.getMonthsOf8() == null ? "" : info.getMonthsOf8().toString());
                buffer.append(delimiter);
                buffer.append(info.getCustomersOf8() == null ? "" : info.getCustomersOf8().toString());
                buffer.append(delimiter);
                buffer.append(info.getQtyOf8() == null ? "" : info.getQtyOf8().toString());
                buffer.append(delimiter);
                buffer.append(info.getAvgQtyOf8() == null ? "" : info.getAvgQtyOf8().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerOf8() == null ? "" : info.getMaxCustomerOf8());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerDeptOf8() == null ? "" : info.getMaxCustomerDeptOf8());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerRateOf8() == null ? "" : info.getMaxCustomerRateOf8().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerQtyOf8() == null ? "" : info.getMaxCustomerQtyOf8().toString());
                buffer.append(delimiter);
                buffer.append(info.getOrdersOf8() == null ? "" : info.getOrdersOf8().toString());
                break;
            case 12:
                buffer.append(info.getMonthsOf12() == null ? "" : info.getMonthsOf12().toString());
                buffer.append(delimiter);
                buffer.append(info.getCustomersOf12() == null ? "" : info.getCustomersOf12().toString());
                buffer.append(delimiter);
                buffer.append(info.getQtyOf12() == null ? "" : info.getQtyOf12().toString());
                buffer.append(delimiter);
                buffer.append(info.getAvgQtyOf12() == null ? "" : info.getAvgQtyOf12().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerOf12() == null ? "" : info.getMaxCustomerOf12());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerDeptOf12() == null ? "" : info.getMaxCustomerDeptOf12());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerRateOf12() == null ? "" : info.getMaxCustomerRateOf12().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerQtyOf12() == null ? "" : info.getMaxCustomerQtyOf12().toString());
                buffer.append(delimiter);
                buffer.append(info.getOrdersOf12() == null ? "" : info.getOrdersOf12().toString());
                break;
            case 24:
                buffer.append(info.getMonthsOf24() == null ? "" : info.getMonthsOf24().toString());
                buffer.append(delimiter);
                buffer.append(info.getCustomersOf24() == null ? "" : info.getCustomersOf24().toString());
                buffer.append(delimiter);
                buffer.append(info.getQtyOf24() == null ? "" : info.getQtyOf24().toString());
                buffer.append(delimiter);
                buffer.append(info.getAvgQtyOf24() == null ? "" : info.getAvgQtyOf24().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerOf24() == null ? "" : info.getMaxCustomerOf24());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerDeptOf24() == null ? "" : info.getMaxCustomerDeptOf24());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerRateOf24() == null ? "" : info.getMaxCustomerRateOf24().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerQtyOf24() == null ? "" : info.getMaxCustomerQtyOf24().toString());
                buffer.append(delimiter);
                buffer.append(info.getOrdersOf24() == null ? "" : info.getOrdersOf24().toString());
                break;
            case 36:
                buffer.append(info.getMonthsOf36() == null ? "" : info.getMonthsOf36().toString());
                buffer.append(delimiter);
                buffer.append(info.getCustomersOf36() == null ? "" : info.getCustomersOf36().toString());
                buffer.append(delimiter);
                buffer.append(info.getQtyOf36() == null ? "" : info.getQtyOf36().toString());
                buffer.append(delimiter);
                buffer.append(info.getAvgQtyOf36() == null ? "" : info.getAvgQtyOf36().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerOf36() == null ? "" : info.getMaxCustomerOf36());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerDeptOf36() == null ? "" : info.getMaxCustomerDeptOf36());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerRateOf36() == null ? "" : info.getMaxCustomerRateOf36().toString());
                buffer.append(delimiter);
                buffer.append(info.getMaxCustomerQtyOf36() == null ? "" : info.getMaxCustomerQtyOf36().toString());
                buffer.append(delimiter);
                buffer.append(info.getOrdersOf36() == null ? "" : info.getOrdersOf36().toString());
                break;
        }
        buffer.append(delimiter);
    }

    /**
     * 写入CSV数据行(直接写入,兼容旧代码)
     * @deprecated 推荐使用 convertToCsvRow + 批量写入方式
     */
    @Deprecated
    private void writeLine(BufferedWriter bw, ModelExpFreqByJobDO info) {
        try {
            String delimiter = ",";
            bw.write('\"' + info.getModelNo() + '\"');
            bw.write(delimiter);
            bw.write(info.getWarehouseCode() == null ? "" : info.getWarehouseCode());
            bw.write(delimiter);
//        bw.write(info.getStockType() == null ? "0" : info.getStockType().toString());
//        bw.write(delimiter);
            bw.write(info.getModelType().equals("1") ? "基础型号" : "订货型号");
            bw.write(delimiter);
            bw.write(info.getClassCode() == null ? "" : info.getClassCode());
            bw.write(delimiter);
            bw.write(info.getModelClass() == null ? "" : info.getModelClass());
            bw.write(delimiter);
//        bw.write(info.getDesignType() == null ? "" : info.getDesignType());
//        bw.write(delimiter);
            bw.write(Objects.isNull(info.getProductType()) ? "" : String.valueOf(info.getProductType()));
            bw.write(delimiter);
            bw.write(info.getLastOrdMonth() == null ? "" : DateUtil.getFormatDate(info.getLastOrdMonth(), "yyyy-MM-dd"));
            bw.write(delimiter);
            bw.write(info.getProductSeries() == null ? "" : info.getProductSeries());
            bw.write(delimiter);
            bw.write(info.getMainOrigin() == null ? "" : info.getMainOrigin());
            bw.write(delimiter);
            bw.write(info.getSecondOrigin() == null ? "" : info.getSecondOrigin());
            bw.write(delimiter);
            bw.write(info.getEPrice() == null ? "" : info.getEPrice().toString());
            bw.write(delimiter);
            bw.write(info.getECode() == null ? "" : info.getECode());
            bw.write(delimiter);
            bw.write(info.getSetMean() == null ? "" : info.getSetMean().toString());
            bw.write(delimiter);
            bw.write(info.getSetClassCode() == null ? "" : info.getSetClassCode());
            bw.write(delimiter);
            bw.write(info.getVariation() == null ? "" : info.getVariation().toString());
            bw.write(delimiter);
            bw.write(info.getMoveRate1() == null ? "" : info.getMoveRate1().toString());
            bw.write(delimiter);
            bw.write(info.getMoveRate2() == null ? "" : info.getMoveRate2().toString());
            bw.write(delimiter);
            bw.write(info.getMoveRate3() == null ? "" : info.getMoveRate3().toString());
            bw.write(delimiter);
            bw.write(info.getSetAvgQty() == null ? "" : info.getSetAvgQty().toString());
            bw.write(delimiter);
            // bw.write(info.getDeptNo() == null ? "" : info.getDeptNo().toString());
            // bw.write(delimiter);
            bw.write(info.getMonthsOf8() == null ? "" : info.getMonthsOf8().toString());
            bw.write(delimiter);
            bw.write(info.getCustomersOf8() == null ? "" : info.getCustomersOf8().toString());
            bw.write(delimiter);
            bw.write(info.getQtyOf8() == null ? "" : info.getQtyOf8().toString());
            bw.write(delimiter);
            bw.write(info.getAvgQtyOf8() == null ? "" : info.getAvgQtyOf8().toString());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerOf8() == null ? "" : info.getMaxCustomerOf8());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerDeptOf8() == null ? "" : info.getMaxCustomerDeptOf8());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerRateOf8() == null ? "" : info.getMaxCustomerRateOf8().toString());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerQtyOf8() == null ? "" : info.getMaxCustomerQtyOf8().toString());
            bw.write(delimiter);
            bw.write(info.getOrdersOf8() == null ? "" : info.getOrdersOf8().toString());
            bw.write(delimiter);
            bw.write(info.getMonthsOf12() == null ? "" : info.getMonthsOf12().toString());
            bw.write(delimiter);
            bw.write(info.getCustomersOf12() == null ? "" : info.getCustomersOf12().toString());
            bw.write(delimiter);
            bw.write(info.getQtyOf12() == null ? "" : info.getQtyOf12().toString());
            bw.write(delimiter);
            bw.write(info.getAvgQtyOf12() == null ? "" : info.getAvgQtyOf12().toString());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerOf12() == null ? "" : info.getMaxCustomerOf12());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerDeptOf12() == null ? "" : info.getMaxCustomerDeptOf12());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerRateOf12() == null ? "" : info.getMaxCustomerRateOf12().toString());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerQtyOf12() == null ? "" : info.getMaxCustomerQtyOf12().toString());
            bw.write(delimiter);
            bw.write(info.getOrdersOf12() == null ? "" : info.getOrdersOf12().toString());
            bw.write(delimiter);
            bw.write(info.getMonthsOf24() == null ? "" : info.getMonthsOf24().toString());
            bw.write(delimiter);
            bw.write(info.getCustomersOf24() == null ? "" : info.getCustomersOf24().toString());
            bw.write(delimiter);
            bw.write(info.getQtyOf24() == null ? "" : info.getQtyOf24().toString());
            bw.write(delimiter);
            bw.write(info.getAvgQtyOf24() == null ? "" : info.getAvgQtyOf24().toString());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerOf24() == null ? "" : info.getMaxCustomerOf24());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerDeptOf24() == null ? "" : info.getMaxCustomerDeptOf24());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerRateOf24() == null ? "" : info.getMaxCustomerRateOf24().toString());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerQtyOf24() == null ? "" : info.getMaxCustomerQtyOf24().toString());
            bw.write(delimiter);
            bw.write(info.getOrdersOf24() == null ? "" : info.getOrdersOf24().toString());
            bw.write(delimiter);
            bw.write(info.getMonthsOf36() == null ? "" : info.getMonthsOf36().toString());
            bw.write(delimiter);
            bw.write(info.getCustomersOf36() == null ? "" : info.getCustomersOf36().toString());
            bw.write(delimiter);
            bw.write(info.getQtyOf36() == null ? "" : info.getQtyOf36().toString());
            bw.write(delimiter);
            bw.write(info.getAvgQtyOf36() == null ? "" : info.getAvgQtyOf36().toString());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerOf36() == null ? "" : info.getMaxCustomerOf36());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerDeptOf36() == null ? "" : info.getMaxCustomerDeptOf36());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerRateOf36() == null ? "" : info.getMaxCustomerRateOf36().toString());
            bw.write(delimiter);
            bw.write(info.getMaxCustomerQtyOf36() == null ? "" : info.getMaxCustomerQtyOf36().toString());
            bw.write(delimiter);
            bw.write(info.getOrdersOf36() == null ? "" : info.getOrdersOf36().toString());
            bw.write(delimiter);
            bw.write(info.getM1() == null ? "" : info.getM1().toString());
            bw.write(delimiter);
            bw.write(info.getM2() == null ? "" : info.getM2().toString());
            bw.write(delimiter);
            bw.write(info.getM3() == null ? "" : info.getM3().toString());
            bw.write(delimiter);
            bw.write(info.getM4() == null ? "" : info.getM4().toString());
            bw.write(delimiter);
            bw.write(info.getM5() == null ? "" : info.getM5().toString());
            bw.write(delimiter);
            bw.write(info.getM6() == null ? "" : info.getM6().toString());
            bw.write(delimiter);
            bw.write(info.getM7() == null ? "" : info.getM7().toString());
            bw.write(delimiter);
            bw.write(info.getM8() == null ? "" : info.getM8().toString());
            bw.write(delimiter);
            bw.write(info.getM9() == null ? "" : info.getM9().toString());
            bw.write(delimiter);
            bw.write(info.getM10() == null ? "" : info.getM10().toString());
            bw.write(delimiter);
            bw.write(info.getM11() == null ? "" : info.getM11().toString());
            bw.write(delimiter);
            bw.write(info.getM12() == null ? "" : info.getM12().toString());
            bw.write(delimiter);
            bw.write(info.getM13() == null ? "" : info.getM13().toString());
            bw.write(delimiter);
            bw.write(info.getM14() == null ? "" : info.getM14().toString());
            bw.write(delimiter);
            bw.write(info.getM15() == null ? "" : info.getM15().toString());
            bw.write(delimiter);
            bw.write(info.getM16() == null ? "" : info.getM16().toString());
            bw.write(delimiter);
            bw.write(info.getM17() == null ? "" : info.getM17().toString());
            bw.write(delimiter);
            bw.write(info.getM18() == null ? "" : info.getM18().toString());
            bw.write(delimiter);
            bw.write(info.getM19() == null ? "" : info.getM19().toString());
            bw.write(delimiter);
            bw.write(info.getM20() == null ? "" : info.getM20().toString());
            bw.write(delimiter);
            bw.write(info.getM21() == null ? "" : info.getM21().toString());
            bw.write(delimiter);
            bw.write(info.getM22() == null ? "" : info.getM22().toString());
            bw.write(delimiter);
            bw.write(info.getM23() == null ? "" : info.getM23().toString());
            bw.write(delimiter);
            bw.write(info.getM24() == null ? "" : info.getM24().toString());
            bw.write(delimiter);
            bw.write(info.getM25() == null ? "" : info.getM25().toString());
            bw.write(delimiter);
            bw.write(info.getM26() == null ? "" : info.getM26().toString());
            bw.write(delimiter);
            bw.write(info.getM27() == null ? "" : info.getM27().toString());
            bw.write(delimiter);
            bw.write(info.getM28() == null ? "" : info.getM28().toString());
            bw.write(delimiter);
            bw.write(info.getM29() == null ? "" : info.getM29().toString());
            bw.write(delimiter);
            bw.write(info.getM30() == null ? "" : info.getM30().toString());
            bw.write(delimiter);
            bw.write(info.getM31() == null ? "" : info.getM31().toString());
            bw.write(delimiter);
            bw.write(info.getM32() == null ? "" : info.getM32().toString());
            bw.write(delimiter);
            bw.write(info.getM33() == null ? "" : info.getM33().toString());
            bw.write(delimiter);
            bw.write(info.getM34() == null ? "" : info.getM34().toString());
            bw.write(delimiter);
            bw.write(info.getM35() == null ? "" : info.getM35().toString());
            bw.write(delimiter);
            bw.write(info.getM36() == null ? "" : info.getM36().toString());
            bw.newLine();

        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    private ResultVo<LambdaQueryWrapper<ModelExpFreqByJobDO>> setModelExpFreqByJobqueryWrapper(ModelExpFreqByJobRequestDTO request) {
        if (PublicUtil.isEmpty(request.getJobNo())
                && PublicUtil.isEmpty(request.getJobId())
                && PublicUtil.isEmpty(request.getWarehouseCode())
                && (PublicUtil.isEmpty(request.getModelNos()) || request.getModelNos().length == 0)
        ) {
            return ResultVo.failure("请输入查询条件。");
        }
        if (PublicUtil.isNotEmpty(request.getJobNo()) && PublicUtil.isEmpty(request.getJobId())) {
            BinTrialJobRequestDTO binTrialJobRequestDTO = new BinTrialJobRequestDTO();
            binTrialJobRequestDTO.setJobNo(request.getJobNo());
            ResultVo<List<BinTrialJobManageVO>> listResultVo = binTrialJobManageService.getBinTrialJobManageData(binTrialJobRequestDTO);
            if (listResultVo.isSuccess()) {
                if (PublicUtil.isNotEmpty(listResultVo.getData()) && listResultVo.getData().size() >= 1) {
                    request.setJobId(listResultVo.getData().get(0).getId());
                }
            }
        }
        LambdaQueryWrapper<ModelExpFreqByJobDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getJobId()), ModelExpFreqByJobDO::getJobId, request.getJobId())
                .eq(PublicUtil.isNotEmpty(request.getWarehouseCode()), ModelExpFreqByJobDO::getWarehouseCode, request.getWarehouseCode())
                .eq(PublicUtil.isNotEmpty(request.getModelType()), ModelExpFreqByJobDO::getModelType, request.getModelType())
                .in(PublicUtil.isNotEmpty(request.getModelNos()) && request.getModelNos().length > 0, ModelExpFreqByJobDO::getModelNo, request.getModelNos());
        return ResultVo.success(queryWrapper);
    }
    private ResultVo<ModelExpFreqByJobRequestDTO> setModelExpFreqByJobQueryDto(ModelExpFreqByJobRequestDTO request) {
        if (PublicUtil.isEmpty(request.getJobNo())
                && PublicUtil.isEmpty(request.getJobId())
                && PublicUtil.isEmpty(request.getWarehouseCode())
                && (PublicUtil.isEmpty(request.getModelNos()) || request.getModelNos().length == 0)
        ) {
            return ResultVo.failure("请输入查询条件。");
        }
        if (PublicUtil.isNotEmpty(request.getJobNo()) && PublicUtil.isEmpty(request.getJobId())) {
            BinTrialJobRequestDTO binTrialJobRequestDTO = new BinTrialJobRequestDTO();
            binTrialJobRequestDTO.setJobNo(request.getJobNo());
            ResultVo<List<BinTrialJobManageVO>> listResultVo = binTrialJobManageService.getBinTrialJobManageData(binTrialJobRequestDTO);
            if (listResultVo.isSuccess()) {
                if (PublicUtil.isNotEmpty(listResultVo.getData()) && listResultVo.getData().size() >= 1) {
                    request.setJobId(listResultVo.getData().get(0).getId());
                }
            }
        }
        return ResultVo.success(request);
    }
}
