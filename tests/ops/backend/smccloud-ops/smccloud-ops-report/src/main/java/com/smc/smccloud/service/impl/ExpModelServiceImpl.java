package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.ExpModelDetailMapper;
import com.smc.smccloud.mapper.ModelExpFreqMapper;
import com.smc.smccloud.model.CSReportModelDTO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.ExpModelDetailDO;
import com.smc.smccloud.model.ModelExpFreqDO;
import com.smc.smccloud.model.csstock.CsInventoryRequestDTO;
import com.smc.smccloud.model.csstock.CsInventoryVO;
import com.smc.smccloud.model.csstock.CsStockSettingVO;
import com.smc.smccloud.model.product.ProductPriceDTO;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author edp04
 * @title: ExpModelServiceImpl
 * @date 2022/05/11 12:12
 */
@Service
@Slf4j
public class ExpModelServiceImpl implements ExpModelService {

    @Resource
    private ExpModelDetailMapper expModelDetailMapper;

    @Resource
    private ProductServiceFeignApi productServiceFeignApi;
    @Resource
    private ConsignmentStockFeignApi consignmentStockFeignApi;
    @Resource
    private BinServiceFeignApi binServiceFeignApi;
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Resource
    private ProductBomFeignApi productBomFeignApi;
    @Resource
    private JavaMailSenderImpl mailSender;
    @Resource
    private ModelExpFreqMapper modelExpFreqMapper;

    @Override
    public ResultVo<String> onSendAgentOrderFreqReport(String agentNo) {
        if (PublicUtil.isEmpty(agentNo)) {
            return ResultVo.failure("代理商代码为空");
        }

        Date endDate = DateUtil.getLastMonthEndDate(DateUtil.getCurrentDate());
        Date fromDate = DateUtil.getMonthFirstDate(DateUtil.addMonth(endDate, -5));

        List<ExpModelDetailDO> list = expModelDetailMapper.listExpDetail(agentNo, fromDate, endDate);
        if (PublicUtil.isEmpty(list)) {
            return ResultVo.failure("未查询到型号订货信息");
        }

        Map<String, List<ExpModelDetailDO>> modelMap = list.stream().collect(Collectors.groupingBy(ExpModelDetailDO::getModelNo));

        List<CSReportModelDTO> models = new ArrayList<>(modelMap.size());
        HashMap<String, CSReportModelDTO> modelUsers;
        CSReportModelDTO dto;
        CSReportModelDTO user;

        for (Map.Entry<String, List<ExpModelDetailDO>> entry : modelMap.entrySet()) {
            dto = new CSReportModelDTO();
            dto.setModelNo(entry.getValue().get(0).getModelNo());
            dto.setOldModelNo(entry.getValue().get(0).getOldModelNo());
            dto.setTotalQty(0);
            dto.setQtys(new int[6]);
            dto.setTotalQty(0);
            dto.setMaxUserNo("");
            dto.setMaxUserQty(0);
            dto.setAvgOrdQty(0);

            modelUsers = new HashMap<>();

            for (ExpModelDetailDO detailDO : entry.getValue()) {
                int index = DateUtil.getDiffMonth(fromDate, detailDO.getMonthDate());
                dto.getQtys()[index] = dto.getQtys()[index] + detailDO.getQty();
                dto.setTotalQty(dto.getTotalQty() + detailDO.getQty());
                dto.setOrders(dto.getOrders() + detailDO.getOrderNumber());

                // 按客户统计
                if (modelUsers.containsKey(detailDO.getCustomerNo())) {
                    user = modelUsers.get(detailDO.getCustomerNo());
                } else {
                    user = new CSReportModelDTO();
                    user.setMaxUserNo(detailDO.getCustomerNo());
                    user.setModelNo(detailDO.getModelNo());
                    modelUsers.put(detailDO.getCustomerNo(), user);
                }
                user.setMaxUserQty(user.getMaxUserQty() + detailDO.getQty());
            }

            setTotalParam(dto, modelUsers);
            models.add(dto);
        }

        InputStream stream = FileUtil.getTemplate("templates/excel/CSAgentExpReport.xlsx");
        ExcelHelper excel = new ExcelHelper(stream);

        int i = 0;

        while (i <= 5) {
            excel.setCellValue(0, 11 + i, DateUtil.getYearMonth(DateUtil.addMonth(fromDate, i)));
            i++;
        }

        List<String> modelNoList = new ArrayList<>(models.size());

        int row = 2;
        for (CSReportModelDTO modelDTO : models) {
            // 总订货数量小于10,并且月数小于等1,不导出
            if (modelDTO.getTotalQty() < 20 || modelDTO.getOrdMonths() <= 1) {
                continue;
            }
            LambdaQueryWrapper<ModelExpFreqDO> query = new LambdaQueryWrapper<>();
            query.eq(ModelExpFreqDO::getModelNo, modelDTO.getModelNo());
            query.eq(ModelExpFreqDO::getModelType, 2);
            query.eq(ModelExpFreqDO::getStockType, 4);
            query.eq(ModelExpFreqDO::getStockCode, agentNo);

            ModelExpFreqDO expFreqDO = modelExpFreqMapper.selectOne(query);
            // 过滤掉基础型号
            if (expFreqDO == null) {
                continue;
            }

            modelNoList.add(modelDTO.getModelNo());

            ResultVo<ProductPriceDTO> priceMast = productServiceFeignApi.getPriceMast(modelDTO.getModelNo());
            if (PublicUtil.isNotEmpty(priceMast.getData())) {
                ProductPriceDTO priceDTO = priceMast.getData();

                modelDTO.setEprice(priceDTO.getEprice());
                modelDTO.setMinOrdQty(priceDTO.getMinPackUnit() == null ? 1 : priceDTO.getMinPackUnit());
//                modelDTO.setClassCode(priceDTO.getClassCode());
                modelDTO.setWeight(priceDTO.getNetWeight());
            }

            excel.setCellValue(row, 0, agentNo);
            excel.setCellValue(row, 1, modelDTO.getModelNo());
            excel.setCellValue(row, 2, modelDTO.getOldModelNo());
//            ResultVo<BindataVO> kshdata = binServiceFeignApi.getBindataByModelNo(modelDTO.getModelNo(), "KSH");
//            excel.setCellValue(row, 3, kshdata.getData() == null ? "0" : "1"); //bin上海
//            ResultVo<BindataVO> kbjdata = binServiceFeignApi.getBindataByModelNo(modelDTO.getModelNo(), "KBJ");
//            excel.setCellValue(row, 4, kbjdata.getData() == null ? "0" : "1"); //bin北京
//            ResultVo<BindataVO> kgzdata = binServiceFeignApi.getBindataByModelNo(modelDTO.getModelNo(), "KGZ");
//            excel.setCellValue(row, 5, kgzdata.getData() == null ? "0" : "1"); //bin广州
            excel.setCellValue(row, 6, modelDTO.getEprice());
            excel.setCellValue(row, 7, modelDTO.getWeight());
            excel.setCellValue(row, 8, modelDTO.getClassCode());
            excel.setCellValue(row, 9, modelDTO.getUserCount());
            excel.setCellValue(row, 10, modelDTO.getMinOrdQty());
            i = 0;
            for (int qty : modelDTO.getQtys()) {
                excel.setCellValue(row, 11 + i, qty);
                i++;
            }
            excel.setCellValue(row, 17, modelDTO.getTotalQty());
            excel.setCellValue(row, 18, modelDTO.getOrdMonths());
            excel.setCellValue(row, 19, modelDTO.getLastOrdMonths());
            excel.setCellValue(row, 20, modelDTO.getMean());
            excel.setCellValue(row, 21, modelDTO.isRepl() ? modelDTO.getMean() * 2 : 0);
            excel.setCellValue(row, 22, modelDTO.getAvgOrdQty());
            excel.setCellValue(row, 23, modelDTO.getMaxUserQty());
            excel.setCellValue(row, 24, modelDTO.getMaxUserRate());
            CsInventoryRequestDTO requestDTO = new CsInventoryRequestDTO();
            requestDTO.setModelNo(modelDTO.getModelNo());
            requestDTO.setCustomerNo(agentNo);
            Page page = new Page();
            page.setPageNumber(1);
            page.setPageSize(50);
            requestDTO.setPage(page);
            ResultVo<PageInfo<CsInventoryVO>> resultVo = consignmentStockFeignApi.listCsStockInventory(requestDTO);
            List<CsInventoryVO> vos = resultVo.getData().getList();
            if (PublicUtil.isNotEmpty(vos)) {
                int onHouseNum = vos.stream().mapToInt(CsInventoryVO::getOnHouseNum).sum();
                int appointmentNum = vos.stream().mapToInt(CsInventoryVO::getAppointmentNum).sum();
                excel.setCellValue(row, 25, onHouseNum); //委托在库数量
                excel.setCellValue(row, 26, appointmentNum); //委托预约数量
            }
            excel.setCellValue(row, 27, modelDTO.isRepl() ? "是" : "否");
            ResultVo<CsStockSettingVO> stockSetting = consignmentStockFeignApi.getCsStockSetting(agentNo, list.get(0).getWarehouseCode(), modelDTO.getModelNo());
            excel.setCellValue(row, 28, PublicUtil.isEmpty(stockSetting.getData()) ? "" : stockSetting.getData().getSponsor()); //提案方
            BigDecimal mul = BigDecimalUtil.mul(modelDTO.getMean(), 1.5);
            long l = Math.round(BigDecimalUtil.div(mul, 10, 2).doubleValue()) * 10;
            excel.setCellValue(row, 30, l);
            excel.setCellFormula(row, 31, String.format("Y%d*D%d", row + 1, row + 1));
            ResultVo<Boolean> canSplit = productBomFeignApi.isCanSplit(modelDTO.getModelNo());
            excel.setCellValue(row, 32, canSplit.getData() ? "是" : "否"); //是否拆分型号

            row++;
        }

        ResultVo<List<Integer>> count1 = binServiceFeignApi.getBinCountByModelNo(modelNoList, "KSH");
        ResultVo<List<Integer>> count2 = binServiceFeignApi.getBinCountByModelNo(modelNoList, "KBJ");
        ResultVo<List<Integer>> count3 = binServiceFeignApi.getBinCountByModelNo(modelNoList, "KGZ");
        List<Integer> list1 = count1.getData();
        row = 2;
        for (Integer integer : list1) {
            excel.setCellValue(row++, 3, integer > 0 ? "是" : "否");
        }
        List<Integer> list2 = count2.getData();
        row = 2;
        for (Integer integer : list2) {
            excel.setCellValue(row++, 4, integer > 0 ? "是" : "否");
        }
        List<Integer> list3 = count3.getData();
        row = 2;
        for (Integer integer : list3) {
            excel.setCellValue(row++, 5, integer > 0 ? "是" : "否");
        }

        try {
            String fileName = "CS-Report-" + agentNo + ".xlsx";
            String subject = "服务备库客户出库统计" + agentNo + " " + DateUtil.getYearMonth(endDate);
            StringBuilder sbContent = new StringBuilder();
            sbContent.append(subject);
            sbContent.append("<br>");
            sbContent.append(DateUtil.getFormatDate(DateUtil.getNow(), "yyyy-MM-dd HH:mm"));
            Map<String, InputStream> attachment = new LinkedHashMap<>(1);
            attachment.put(fileName, excel.convertTo());
            ResultVo<DataTypeVO> info = dictDataServiceFeignApi.getDataTypeCodesInfo("9004", "CS1");
            String to = info.getData().getExtNote1();
            String cc = info.getData().getExtNote2();
            String bcc = info.getData().getExtNote3();
            EmailUtil.send(mailSender, to, cc, bcc, subject, sbContent.toString(), attachment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVo.success("已发送至邮箱");
    }

    private void setTotalParam(CSReportModelDTO dto, HashMap<String, CSReportModelDTO> modelUsers) {

        dto.setUserCount(modelUsers.size());
        int i = 0;
        int months = 0;
        int lastOrdMonths = 0;
        for (int qty : dto.getQtys()) {
            if (qty > 0) {
                months++;
                if (i >= 3) {
                    lastOrdMonths++;
                }
            }

            i++;
        }
        dto.setOrdMonths(months);
        dto.setLastOrdMonths(lastOrdMonths);
        dto.setMean(BigDecimalUtil.div(dto.getTotalQty(), 6, 1).intValue());
        dto.setUserCount(modelUsers.size());

        for (CSReportModelDTO userDTO : modelUsers.values()) {

            if (dto.getMaxUserQty() <= userDTO.getMaxUserQty()) {
                dto.setMaxUserQty(userDTO.getMaxUserQty());
                dto.setMaxUserNo(userDTO.getMaxUserNo());
            }
        }
        dto.setMaxUserRate(BigDecimalUtil.div(dto.getMaxUserQty(), dto.getTotalQty(), 2).floatValue());

        // 平均下单数量
        dto.setAvgOrdQty(BigDecimalUtil.div(dto.getTotalQty(), dto.getOrders(), 2).intValue());

        // 备货条件
        if (dto.getUserCount() >= 2 && dto.getOrdMonths() >= 3 && dto.getLastOrdMonths() >= 2
                && dto.getMaxUserRate() < 0.9) {
            dto.setRepl(true);
        }
    }



    @Override
    public ResultVo<String> sendCustomerData() {
        List<String> list = expModelDetailMapper.listCustomerData();

        InputStream stream = FileUtil.getTemplate("templates/excel/CSAgentExpReport.xlsx");
        ExcelHelper excel = new ExcelHelper(stream);

        int row = 1;
        for (String no : list) {
            excel.setCellValue(row, 0, no);
            row++;
        }
        try {
            String fileName = "1.xlsx";
            String subject = "代理商代码" + " " + DateUtil.getYearMonth(new Date());
            StringBuilder sbContent = new StringBuilder();
            sbContent.append(subject);
            sbContent.append("<br>");
            sbContent.append(DateUtil.getFormatDate(DateUtil.getNow(), "yyyy-MM-dd HH:mm"));
            Map<String, InputStream> attachment = new LinkedHashMap<>(1);
            attachment.put(fileName, excel.convertTo());

//            String to = info.getData().getExtNote1();
//            String cc = info.getData().getExtNote2();
//            String bcc = info.getData().getExtNote3();
//            EmailUtil.send(mailSender, to, cc, bcc, subject, sbContent.toString(), attachment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVo.success();
    }
}
