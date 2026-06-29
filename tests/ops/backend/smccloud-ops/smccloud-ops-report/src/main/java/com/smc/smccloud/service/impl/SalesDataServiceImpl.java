package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.SalesDataMapper;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.SalesDataDO;
import com.smc.smccloud.model.salesData.EDisvStatisticsVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import com.smc.smccloud.service.SalesDataService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smc
 * @since 2022-10-28
 */
@Service
public class SalesDataServiceImpl extends ServiceImpl<SalesDataMapper, SalesDataDO> implements SalesDataService {

    @Resource
    private SalesDataMapper salesDataMapper;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Resource
    private JavaMailSenderImpl mailSender;

    // add by LiYingChao from bug 8517 in 20221031
    // update by LiYingChao from bug8856 in 20221201
    @Override
    public ResultVo<String> sendSalesEDiscountReport(String date) {

        if (StringUtils.isEmpty(date)) {
            return ResultVo.failure("请输入统计日期.");
        }

        // 判断分隔符"-"出现的次数
        if (PublicUtil.showCountByStr("-") != 1) {
            return ResultVo.failure("请检查日期参数格式,需满足yyyy-MM.");
        }

        String[] split = date.split("-");
        String year = split[0];
        String m = "";
        int mon = Integer.parseInt(split[1]);
        if (mon < 10) {
            m = "0"+mon;
        } else {
            m = String.valueOf(mon);
        }
        String dateParam = date;

        // 当前月
        List<EDisvStatisticsVO> curMonthDisReport = salesDataMapper.getCurMonthDisReport(dateParam);
        if (CollectionUtils.isEmpty(curMonthDisReport)) {
            return ResultVo.failure(dateParam+"暂无数据");
        }
        // 当年
        dateParam = dateParam+"-01";
        Date d = DateUtil.stringToDate(dateParam);
        d = DateUtil.addMonth(d, 1);
        String endDate = DateUtil.dateToString(d);
        String startDate = "";
        int month = DateUtil.getMonth(d);
        if (month - 4 > 0) {
            startDate = DateUtil.getYear(d) + "-04-01";
        } else {
            startDate = DateUtil.getYear(d)-1 + "-04-01";
        }
        List<EDisvStatisticsVO> curYearDisReport = salesDataMapper.getCurYearDisReport(startDate, endDate);

        if (CollectionUtils.isEmpty(curYearDisReport)) {
            return ResultVo.failure("暂无所需发送数据");
        }

        // 当月-修改前E销售额
        List<EDisvStatisticsVO> curMonthBeforeUpDisReport = salesDataMapper.getCurMonthBeforeUpDisReport(date);

        // 当年- 修改前E销售额
        List<EDisvStatisticsVO> curYearBeforeUpDisReport = salesDataMapper.getCurYearBeforeUpDisReport(startDate, endDate);

        String path = "templates/excel/E差益统计表.xlsx";
        ExcelHelper excel = new ExcelHelper(path);
        Workbook wb = excel.getWorkBook();
        Font font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setBold(true);
        font.setColor((short)10);
        font.setFontHeightInPoints((short) 11);

        Font font2 = wb.createFont();
        font2.setFontName("微软雅黑");
        font2.setBold(true);
        font2.setFontHeightInPoints((short) 11);
        excel.openSheet(0);

        Sheet sheet = wb.getSheet("E差益统计表");

        // 向模板中写入数据
        int row = 4;
        int cel;

        excel.setCellStyle(1,1,font2,new Short[]{null, null, null, null});
        excel.setCellValue(1, 1, year+m);
        excel.setCellStyle(1,1,font2,new Short[]{null, null, null, null});

        // update by LiYingChao from bug8856 in 20221201
        // excel.setCellValue(1, 7, "金额单位:人民币元");

//        sheet.addMergedRegion(new CellRangeAddress(1,1,1,6));
//        sheet.addMergedRegion(new CellRangeAddress(1,1,7,10));

        // 获取交易主体名称
        ResultVo<List<DataCodeVO>> dataCodes = dictDataServiceFeignApi.getDataCodes("2066");
        if (!dataCodes.isSuccess() || dataCodes.getData() == null) {
            return ResultVo.failure("获取交易主体名称失败.");
        }
        Map<String,String> map = new HashMap<>();
        Map<String, BigDecimal> curMonthBeforeUpDisReportMap = new HashMap<>();
        Map<String, BigDecimal> curYearBeforeUpDisReportMap = new HashMap<>();

        List<DataCodeVO> data = dataCodes.getData();
        for (DataCodeVO item :data) {
            if (!map.containsKey(item.getCode())) {
                map.put(item.getCode().trim(),item.getCodeName());
            }
        }

        for (EDisvStatisticsVO item : curMonthBeforeUpDisReport) {
            if (!curMonthBeforeUpDisReportMap.containsKey(item.getTradeCompanyId())) {
                curMonthBeforeUpDisReportMap.put(item.getTradeCompanyId().trim(),item.getEamount());
            }
        }

        for (EDisvStatisticsVO item : curYearBeforeUpDisReport) {
            if (!curYearBeforeUpDisReportMap.containsKey(item.getTradeCompanyId())) {
                curYearBeforeUpDisReportMap.put(item.getTradeCompanyId().trim(),item.getEamount());
            }
        }
        String format = "0.0%";

        for (EDisvStatisticsVO curYear : curYearDisReport) {
            cel = 10;
            excel.setCellDataFormat(row,cel,format);
            excel.setCellValue(row, cel--, CommonFormulaUtil.calcDiscountForcustomCount(curYear.getNtaxAmount(),curYearBeforeUpDisReportMap.get(curYear.getTradeCompanyId().trim()),3));  // 修改前E差益率
            excel.setCellValue(row, cel--, curYear.getBnsAmount());  // 当年 BNS金额
            excel.setCellDataFormat(row,cel,format);
            excel.setCellValue(row, cel--, CommonFormulaUtil.calcDiscountForcustomCount(curYear.getNtaxAmount(),curYear.getEamount(),3));  // 当年 E差益率
            excel.setCellValue(row, cel--, curYear.getEamount());  // 当年 E销售额
            excel.setCellValue(row, cel--, curYear.getNtaxAmount());  // 当年 不含税额
            if (CollectionUtils.isNotEmpty(curMonthDisReport)) {
                for (EDisvStatisticsVO curMonth : curMonthDisReport) {
                    if (curYear.getTradeCompanyId().trim().equals(curMonth.getTradeCompanyId().trim())) {
                        excel.setCellDataFormat(row,cel,format);
                        excel.setCellValue(row, cel--, CommonFormulaUtil.calcDiscountForcustomCount(curMonth.getNtaxAmount(),curMonthBeforeUpDisReportMap.get(curMonth.getTradeCompanyId().trim()),3));  // 修改前E差益率
                        excel.setCellValue(row, cel--, curMonth.getBnsAmount());  // 当月 BNS金额
                        excel.setCellDataFormat(row,cel,format);
                        excel.setCellValue(row, cel--, CommonFormulaUtil.calcDiscountForcustomCount(curMonth.getNtaxAmount(),curMonth.getEamount(),3));  // 当月 E差益率
                        excel.setCellValue(row, cel--, curMonth.getEamount());  // 当月 E销售额
                        excel.setCellValue(row, cel--, curMonth.getNtaxAmount());  // 当月 不含税额
                    }
                }
            }
            excel.setCellStyle(row,0,font2,new Short[]{null, null, null, null});
            excel.setCellValue(row, 0, map.get(curYear.getTradeCompanyId().trim()));  // 交易主体
            row++;
        }
        // 共计各列的值 (当月)
        // 不含税额
         BigDecimal curMonthNtaxPrice = curMonthDisReport.stream().filter(item -> item.getNtaxAmount() != null).map(EDisvStatisticsVO::getNtaxAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        // E销售额
        BigDecimal curMonthEAmountPrice = curMonthDisReport.stream().filter(item -> item.getEamount() != null).map(EDisvStatisticsVO::getEamount).reduce(BigDecimal.ZERO, BigDecimal::add);
        // E差益率
        BigDecimal curMonthEDisvRatePrice = CommonFormulaUtil.calcDiscountForcustomCount(curMonthNtaxPrice,curMonthEAmountPrice,3);
        // BNS金额
        BigDecimal curMonthBusRatePrice = curMonthDisReport.stream().filter(item -> item.getBnsAmount() != null).map(EDisvStatisticsVO::getBnsAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 修改前E差益率
        BigDecimal curMonthSumBeforeUpEamont= curMonthBeforeUpDisReportMap.values().stream().filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal curMonthBeforeUpEDisvRatePrice = CommonFormulaUtil.calcDiscountForcustomCount(curMonthNtaxPrice,curMonthSumBeforeUpEamont,3);
        // 共计各列的值 (当年)
        // 不含税额
        BigDecimal curYearNtaxPrice = curYearDisReport.stream().filter(item -> item.getNtaxAmount() != null).map(EDisvStatisticsVO::getNtaxAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        // E销售额
        BigDecimal curYearEAmountPrice = curYearDisReport.stream().filter(item -> item.getEamount() != null).map(EDisvStatisticsVO::getEamount).reduce(BigDecimal.ZERO, BigDecimal::add);
        // E差益率
        BigDecimal curYearEDisvRatePrice = CommonFormulaUtil.calcDiscountForcustomCount(curYearNtaxPrice, curYearEAmountPrice,3);
        // BNS金额
        BigDecimal curYearBusRatePrice = curYearDisReport.stream().filter(item -> item.getBnsAmount() != null).map(EDisvStatisticsVO::getBnsAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 修改前E差益率
        BigDecimal curYearSumBeforeUpEamont= curYearBeforeUpDisReportMap.values().stream().filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal curYearBeforeUpEDisvRatePrice = CommonFormulaUtil.calcDiscountForcustomCount(curYearNtaxPrice,curYearSumBeforeUpEamont,3);

        excel.setCellDataFormat(row,10,format);
        excel.setCellValue(row, 10, curYearBeforeUpEDisvRatePrice);
        excel.setCellValue(row, 9, curYearBusRatePrice);
        excel.setCellDataFormat(row,8,format);
        excel.setCellValue(row, 8, curYearEDisvRatePrice);
        excel.setCellValue(row, 7, curYearEAmountPrice);
        excel.setCellValue(row, 6, curYearNtaxPrice);
        excel.setCellDataFormat(row,5,format);
        excel.setCellValue(row, 5, curMonthBeforeUpEDisvRatePrice);
        excel.setCellValue(row, 4, curMonthBusRatePrice);
        excel.setCellDataFormat(row,3,format);
        excel.setCellValue(row, 3, curMonthEDisvRatePrice);
        excel.setCellValue(row, 2, curMonthEAmountPrice);
        excel.setCellValue(row, 1, curMonthNtaxPrice);
        excel.setCellStyle(row,0,font,new Short[]{null, null, null, null});
        excel.setCellValue(row, 0, "全部");

        // 发送邮件
        String fileName = date+"E差益统计表.xlsx";
        String subject = date+"E差益统计表";
        Map<String, InputStream> attachment = new LinkedHashMap<>(2);
        attachment.put(fileName, excel.convertTo()); // 附件
        ResultVo<DataTypeVO> esrResult = dictDataServiceFeignApi.getDataTypeCodesInfo("9004", "ESR");
        StringBuilder sendEmail = new StringBuilder(); // 发送人邮箱
        StringBuilder csEamil = new StringBuilder(); // 抄送人邮箱
        StringBuilder msEmain = new StringBuilder(); // 密送人邮箱
        msEmain.append("webservice@smcgz.com.cn;");
        if (esrResult.isSuccess() && esrResult.getData() != null) {
            DataTypeVO dataTypeVO = esrResult.getData();
            if (StringUtils.isNotBlank(dataTypeVO.getExtNote1())) {
                sendEmail.append(dataTypeVO.getExtNote1());
            }
            if (StringUtils.isNotBlank(dataTypeVO.getExtNote2())) {
                csEamil.append(dataTypeVO.getExtNote2());
            }
            if (StringUtils.isNotBlank(dataTypeVO.getExtNote3())) {
                msEmain.append(dataTypeVO.getExtNote3());
            }
        } else {
           return ResultVo.failure("请检查邮箱配置是否有误");
        }
        String content = "<b>E率修正已完成，E差益统计数据请参考附件。</b><br>" +
                "<b>如有问题，请联系价格管理课朱思敏，谢谢。</b> <br><br>" +
                "<b>此邮件为系统发送，请勿直接回复。</b> <br><br>";
        EmailUtil.send(mailSender,sendEmail.toString() , csEamil.toString(),msEmain.toString(), subject,
                content, attachment);

        return ResultVo.success("成功推送"+date+"E差益统计表");
    }
}
