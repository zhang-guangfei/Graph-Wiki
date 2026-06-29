package com.smc.ops.delivery.inquiry.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInquiryApply;
import com.sales.ops.db.entity.OpsInquiryCodeConfig;
import com.sales.ops.db.entity.OpsInquiryReasonParamConfig;
import com.sales.ops.dto.expdetail.DataCodeDto;
import com.sales.ops.dto.inquiry.*;
import com.sales.ops.enums.OrderStatusEnum;
import com.sales.ops.feign.purchase.PurchaseBatchEditFeignApi;
import com.smc.ops.delivery.inquiry.mapper.InquiryApplyMapper;
import com.smc.ops.delivery.inquiry.mapper.InquiryCodeConfigMapper;
import com.smc.ops.delivery.inquiry.mapper.InquiryReasonParamConfigMapper;
import com.smc.ops.delivery.inquiry.service.InquiryApplyHandleService;
import com.smc.ops.delivery.inquiry.service.InquiryApplyService;
import com.smc.ops.delivery.mapper.DictdataDao;
import com.smc.ops.delivery.model.inqb.OpsInquiryApplyExcelVO;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.OrderTypeEnum;
import com.smc.smccloud.core.model.enums.PurchaseStatusEnum;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.ExcelHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
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
public class InquiryApplyServiceImpl implements InquiryApplyService {

    @Resource
    private InquiryApplyMapper inquiryApplyMapper;

    @Resource
    private PurchaseBatchEditFeignApi purchaseBatchEditFeignApi;

    @Resource
    private InquiryApplyHandleService inquiryApplyHandleService;

    @Resource
    private InquiryCodeConfigMapper inquiryCodeConfigMapper;

    @Resource
    private DictdataDao dictdataDao;

    @Resource
    private InquiryReasonParamConfigMapper inquiryReasonParamConfigMapper;

    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public ResultVo<PageInfo<OpsInquiryApply>> findAll(InquiryApplyRequest inquiryApplyRequest, Page page) {
        // 判断开始日期和结束日期
        if (inquiryApplyRequest.getStartDate() != null || inquiryApplyRequest.getEndDate() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(inquiryApplyRequest.getEndDate());
            cal.add(Calendar.DATE, 1);
            inquiryApplyRequest.setEndDate(cal.getTime());
        }
        PageInfo<OpsInquiryApply> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> inquiryApplyMapper.selectInquiryApply(inquiryApplyRequest));
        return ResultVo.success(pageInfo);
    }

    @Override
    public void exportExcelData(InquiryApplyRequest dto, HttpServletResponse response) {
        // 判断开始日期和结束日期,不能超过两年，以结束日期为准
        Date startDate = dto.getStartDate();
        Date endDate = dto.getEndDate();
        if (endDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.YEAR, -2);
            Date startDateLimit = cal.getTime();
            if (dto.getStartDate() == null || dto.getStartDate().before(startDateLimit)) {
                dto.setStartDate(startDateLimit);
            }
        }else {
            if(startDate != null){
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                cal.add(Calendar.YEAR, 2);
                Date endDateLimit = cal.getTime();
                dto.setEndDate(endDateLimit);
            }else{
                Date today = DateUtil.getToday();
                Calendar cal = Calendar.getInstance();
                cal.setTime(today);
                cal.add(Calendar.YEAR, -2);
                Date startDateLimit = cal.getTime();
                dto.setStartDate(startDateLimit);
                dto.setEndDate(today);
            }
        }
        //end晚一天，以查询当天全天的数据
        Calendar cal = Calendar.getInstance();
        cal.setTime(dto.getEndDate());
        cal.add(Calendar.DATE, 1);
        dto.setEndDate(cal.getTime());
        List<OpsInquiryApply> list = inquiryApplyMapper.selectInquiryApply(dto);
        List<OpsInquiryApplyExcelVO> excelList = new ArrayList<>();

        for (OpsInquiryApply apply : list) {
            OpsInquiryApplyExcelVO vo = new OpsInquiryApplyExcelVO();
            BeanUtil.copyProperties(apply, vo);
            if (apply.getOrderStatus() != null) {
                String orderStatus = PurchaseStatusEnum.getName(apply.getOrderStatus().toString());
                vo.setOrderStatus(orderStatus);
            }
            if (StringUtils.isNotBlank(apply.getOrderType())) {
                vo.setOrderType(OrderTypeEnum.getCodeName(apply.getOrderType()));
            }
            if (apply.getInquiryStatus() != null) {
                vo.setInquiryStatus(InquiryStatusEnum.getDescByCode(apply.getInquiryStatus()));
            }
//            if (apply.getReplyDeliveryDate() != null) {
//                if (DateUtil.isAfterDate(apply.getReplyDeliveryDate(), apply.getHopeDeliveryDate())) {
//                    vo.setReplyResultDesc("超预期");
//                } else {
//                    vo.setReplyResultDesc("预期内");
//                }
//            }
            excelList.add(vo);
        }
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("数据导出", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), OpsInquiryApplyExcelVO.class)
                    .sheet("数据导出")
                    .doWrite(excelList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultVo<List<InquiryOrderDetailReturnDto>> findDetail(OpsInquiryApply opsInquiryApply) {
        List<InquiryOrderDetailReturnDto> returnList = new ArrayList<>();
        if (opsInquiryApply.getInquiryType().equals(InquiryTypeEnum.PURCHASE.getType())) {
            returnList = inquiryApplyMapper.selectInquiryDetail(opsInquiryApply.getInquiryApplyNo());
        } else {
            returnList = inquiryApplyMapper.selectInquiryDetailOrder(opsInquiryApply.getInquiryApplyNo());
            returnList.forEach(item -> {
                item.setOrderStatusDesc(OrderStatusEnum.getDescByState(item.getOrderStatus()));
            });
        }
        // 2024-09-26 订单催促时，需要联查订单分配表 获取分配信息
        return ResultVo.success(returnList);
    }

    @Override
    public ResultVo<List<InquiryApplyVerifyReurn>> getOrderData(List<String> rorderFno) {
        if (CollectionUtils.isEmpty(rorderFno)) {
            return ResultVo.failure("订单号为空，请输入完整单号");
        }
        try {
            return inquiryApplyHandleService.purchaseInquiryVerify(rorderFno, "0");
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    @Override
    public ResultVo<List<InquiryOrderVerifyReurn>> getOrderInquiryData(List<String> rorderFno) {
        if (CollectionUtils.isEmpty(rorderFno)) {
            return ResultVo.failure("订单号为空，请输入完整单号");
        }
        try {
            return inquiryApplyHandleService.orderInquiryVerify(rorderFno, "1");
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    @Override
    public ResultVo<String> addInquiryData(List<InquiryApplyAddParam> list) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.failure("请求数据为空，请补充数据后重试！！！");
        }
        return inquiryApplyHandleService.purchaseInquiryAdd(list);
    }

    /**
     * 前端批量导入功能
     *
     * @param file
     * @return
     */
    @Override
    public ResultVo<List<InquiryApplyVerifyReurn>> importFile(MultipartFile file) {
        List<InquiryApplyVerifyReurn> resultList = new ArrayList<>();
        ResultVo<List<InquiryApplyVerifyReurn>> resultVo = ResultVo.success();
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        StringBuilder returnMsg = new StringBuilder();
        try {
            ExcelHelper excel = new ExcelHelper(file.getInputStream());
            Sheet sheet = excel.getSheet();
            int colIndex;
            int lastRowNum = sheet.getLastRowNum();
            Row row;
            List<String> ordernos;
            InquiryApplyVerifyReurn reurnDo;
            // 构建催促原因map
            Map<String, String> codeConfigMap = getInquiryCodecinfig();
            HashMap<String, String> ordernoMap = new HashMap<String, String>(); // 构建orderno的map,校验重复订单
            // applyno,获取缓存中的申请号
//            int applyno = Integer.parseInt(inquiryApplyHandleService.generateApplyNo("0"));
            // 导入时，增加重复单号的校验，第二次出现的重复单号，不导入，并前端提示异常原因
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                try {
                    row = sheet.getRow(rowNum);
                    if (row == null) {
                        break;
                    }
                    colIndex = 0;
                    // 获取接单号
                    String orderno = excel.getCellString(row.getCell(colIndex++)).trim();
                    if (StringUtils.isBlank(orderno)) {
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum + "、第 " + (rowNum)).append(" 行，订单号为空，导入失败！");
                        continue;
                    }
                    if (ordernoMap.containsKey(orderno)) {
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum + "、第 " + (rowNum)).append(" 行，订单号：").append(orderno).append("重复，导入失败！");
                        continue;
                    }
                    // 校验查询接口
                    ordernos = new ArrayList<>(Collections.singletonList(orderno));
                    ResultVo<List<InquiryApplyVerifyReurn>> verifyResult = inquiryApplyHandleService.purchaseInquiryVerify(ordernos, "0");
                    if (!verifyResult.isSuccess()) {
                        reurnDo = new InquiryApplyVerifyReurn();
                        reurnDo.setOrderNo(orderno);
                        reurnDo.setCanPress(false);
                        reurnDo.setCheckFailureMsg(verifyResult.getMessage());
                        resultList.add(reurnDo);
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum + "、第 " + (rowNum) + "行导入失败：").append(verifyResult.getMessage());
                        continue;
                    }
                    List<InquiryApplyVerifyReurn> reurns = verifyResult.getData();
                    if (CollectionUtils.isEmpty(reurns)) {
                        reurnDo = new InquiryApplyVerifyReurn();
                        reurnDo.setOrderNo(orderno);
                        reurnDo.setCanPress(false);
                        reurnDo.setCheckFailureMsg(InquiryVerifyMsg.UNPURCHASE.getDesc());
                        resultList.add(reurnDo);
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum + "、第 " + (rowNum) + "行导入失败：").append(InquiryVerifyMsg.UNPURCHASE.getDesc());
                        continue;
                    }
                    reurnDo = reurns.get(0);
                    // 校验失败的，返回失败
                    if (!reurnDo.getCanPress()) {
                        resultList.add(reurnDo);
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum + "、第 " + (rowNum) + "行导入失败：").append(reurnDo.getCheckFailureMsg());
                        continue;
                    }
                    String lastInDate = excel.getCellString(row.getCell(colIndex++)).trim();
                    if (StringUtils.isNotBlank(lastInDate)) {
                        reurnDo.setHopeDeliveryDate(DateUtil.stringToDate(lastInDate));
                    }
                    String reasonDesc = excel.getCellString(row.getCell(colIndex++)).trim();
                    if (StringUtils.isNotBlank(reasonDesc)) {
                        reurnDo.setInquiryReason(reasonDesc);
                        String reasoner = codeConfigMap.get(reasonDesc);
                        if (StringUtils.isNotBlank(reasoner)) {
                            reurnDo.setInquiryReasonType(reasoner);
                        }
                    }
//                reurnDo.setInquiryDescription(excel.getCellString(row.getCell(colIndex++)).trim());
                    reurnDo.setInquiryRemark(excel.getCellString(row.getCell(colIndex)).trim());
                    // 生成申请单号，每次从redis中读取后再push
                    reurnDo.setInquiryApplyNo(inquiryApplyHandleService.generateApplyNo("0"));
                    resultList.add(reurnDo);
                    ordernoMap.put(orderno, orderno);
                    successNum++;
                } catch (Exception e) {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、第 " + rowNum + 1 + " 行导入失败：";
                    failureMsg.append(msg + e.getMessage());
                    log.error(msg, e);
                }
            }
        } catch (Exception e) {
            log.error("催促批量导入失败" + e.toString());
            throw new RuntimeException("催促批量导入失败" + e.getMessage());
        }
        if (CollectionUtils.isEmpty(resultList)) {
            return ResultVo.failure("催促批量导入失败:未找到对应单据信息");
        }
        resultVo.setData(resultList);
        if (failureNum > 0) {
            failureMsg.insert(0, "导入成功 " + successNum + " 条，但部分导入失败！共 " + failureNum + " 条，错误信息如下：");
            resultVo.setMessage(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条!");
            resultVo.setMessage(successMsg.toString());
        }
        return resultVo;
    }

    @Override
    public ResultVo<List<InquiryOrderVerifyReurn>> importOrderFile(MultipartFile file) {
        List<InquiryOrderVerifyReurn> resultList = new ArrayList<>();
        ResultVo<List<InquiryOrderVerifyReurn>> resultVo = ResultVo.success();
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        StringBuilder returnMsg = new StringBuilder();
        try {
            ExcelHelper excel = new ExcelHelper(file.getInputStream());
            Sheet sheet = excel.getSheet();
            int colIndex;
            int lastRowNum = sheet.getLastRowNum();
            Row row;
            List<String> ordernos;
            InquiryOrderVerifyReurn reurnDo;
            InquiryOrderMasterDto inquiryOrderMasterDto;

            // 构建催促原因map
            Map<String, String> codeConfigMap = getInquiryCodecinfig();
            HashMap<String, String> ordernoMap = new HashMap<String, String>(); // 构建orderno的map,校验重复订单
            // applyno,获取缓存中的申请号
//            int applyno = Integer.parseInt(inquiryApplyHandleService.generateApplyNo("0"));
            // 导入时，增加重复单号的校验，第二次出现的重复单号，不导入，并前端提示异常原因
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                try {
                    row = sheet.getRow(rowNum);
                    if (row == null) {
                        break;
                    }
                    colIndex = 0;
                    // 获取接单号
                    String orderno = excel.getCellString(row.getCell(colIndex++)).trim();
                    if (StringUtils.isBlank(orderno)) {
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum + "、第 " + (rowNum)).append(" 行，订单号为空，导入失败！");
                        continue;
                    }
                    if (ordernoMap.containsKey(orderno)) {
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum + "、第 " + (rowNum)).append(" 行，订单号：").append(orderno).append("重复，导入失败！");
                        continue;
                    }
                    // 校验查询接口
                    ordernos = new ArrayList<>(Collections.singletonList(orderno));
                    ResultVo<List<InquiryOrderVerifyReurn>> verifyResult = inquiryApplyHandleService.orderInquiryVerify(ordernos, "1");
                    reurnDo = new InquiryOrderVerifyReurn();
                    inquiryOrderMasterDto = new InquiryOrderMasterDto();
                    inquiryOrderMasterDto.setOrderNo(orderno);
                    if (!verifyResult.isSuccess()) {
                        inquiryOrderMasterDto.setCanPress(false);
                        inquiryOrderMasterDto.setCheckFailureMsg(verifyResult.getMessage());
                        reurnDo.setInquiryOrderMaster(inquiryOrderMasterDto);
                        resultList.add(reurnDo);
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum + "、第 " + (rowNum) + "行导入失败：").append(verifyResult.getMessage());
                        continue;
                    }
                    List<InquiryOrderVerifyReurn> reurns = verifyResult.getData();
                    if (CollectionUtils.isEmpty(reurns)) {
                        inquiryOrderMasterDto.setCanPress(false);
                        inquiryOrderMasterDto.setCheckFailureMsg(InquiryVerifyMsg.UNPURCHASE.getDesc());
                        reurnDo.setInquiryOrderMaster(inquiryOrderMasterDto);
                        resultList.add(reurnDo);
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum + "、第 " + (rowNum) + "行导入失败：").append(InquiryVerifyMsg.UNPURCHASE.getDesc());
                        continue;
                    }
                    reurnDo = reurns.get(0);
                    inquiryOrderMasterDto = reurnDo.getInquiryOrderMaster();
                    // 校验失败的，返回失败
                    if (!inquiryOrderMasterDto.getCanPress()) {
                        resultList.add(reurnDo);
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum + "、第 " + (rowNum) + "行导入失败：").append(inquiryOrderMasterDto.getCheckFailureMsg());
                        continue;
                    }
                    String lastInDate = excel.getCellString(row.getCell(colIndex++)).trim();
                    if (StringUtils.isNotBlank(lastInDate)) {
                        inquiryOrderMasterDto.setHopeDeliveryDate(DateUtil.stringToDate(lastInDate));
                    }
                    InquiryApplyAddParam inquiryApplyAddParam = new InquiryApplyAddParam();
                    inquiryApplyAddParam.setOrderNo(orderno);
                    inquiryApplyAddParam.setHopeDeliveryDate(inquiryOrderMasterDto.getHopeDeliveryDate());
                    // todo 订单催促增加货期的校验，调用校验接口
                    ResultVo<List<InquiryOrderMasterDto>> deliveryDateVerifyResult = inquiryApplyHandleService.deliveryDateInquiryVerify(Arrays.asList(inquiryApplyAddParam));
                    if (!deliveryDateVerifyResult.isSuccess() || CollectionUtils.isEmpty(deliveryDateVerifyResult.getData())) {
                        inquiryOrderMasterDto.setCanPress(false);
                        inquiryOrderMasterDto.setCheckFailureMsg(deliveryDateVerifyResult.getMessage());
                        reurnDo.setInquiryOrderMaster(inquiryOrderMasterDto);
                        resultList.add(reurnDo);
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum + "、第 " + (rowNum) + "行导入失败：").append(deliveryDateVerifyResult.getMessage());
                        continue;
                    }
                    if (!deliveryDateVerifyResult.getData().get(0).getCanPress()) {
                        inquiryOrderMasterDto.setCanPress(false);
                        inquiryOrderMasterDto.setCheckFailureMsg(deliveryDateVerifyResult.getData().get(0).getCheckFailureMsg());
                        reurnDo.setInquiryOrderMaster(inquiryOrderMasterDto);
                        resultList.add(reurnDo);
                        failureNum++;
                        failureMsg.append("<br/>").append(failureNum + "、第 " + (rowNum) + "行导入失败：").append(deliveryDateVerifyResult.getData().get(0).getCheckFailureMsg());
                        continue;
                    }
                    String reasonDesc = excel.getCellString(row.getCell(colIndex++)).trim();
                    if (StringUtils.isNotBlank(reasonDesc)) {
                        inquiryOrderMasterDto.setInquiryReason(reasonDesc);
                        String reasoner = codeConfigMap.get(reasonDesc);
                        if (StringUtils.isNotBlank(reasoner)) {
                            inquiryOrderMasterDto.setInquiryReasonType(reasoner);
                        }
                    }
                    inquiryOrderMasterDto.setInquiryRemark(excel.getCellString(row.getCell(colIndex)).trim());
                    // 生成申请单号，每次从redis中读取后再push
                    inquiryOrderMasterDto.setInquiryApplyNo(inquiryApplyHandleService.generateApplyNo("1"));
                    reurnDo.setInquiryOrderMaster(inquiryOrderMasterDto);
                    resultList.add(reurnDo);
                    ordernoMap.put(orderno, orderno);
                    successNum++;
                } catch (Exception e) {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、第 " + rowNum + " 行导入失败：";
                    failureMsg.append(msg + e.getMessage());
                    log.error(msg, e);
                }
            }
        } catch (Exception e) {
            log.error("催促批量导入失败" + e.toString());
            throw new RuntimeException("催促批量导入失败" + e.getMessage());
        }
        if (CollectionUtils.isEmpty(resultList)) {
            return ResultVo.failure("催促批量导入失败:未找到对应单据信息");
        }
        resultVo.setData(resultList);
        if (failureNum > 0) {
            failureMsg.insert(0, "导入成功 " + successNum + " 条，但部分导入失败！共 " + failureNum + " 条，错误信息如下：");
            resultVo.setMessage(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条!");
            resultVo.setMessage(successMsg.toString());
        }
        return resultVo;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @DS("opscmm")
    public List<DataCodeDto> getTbldata(String type) {
        return dictdataDao.getTbldatatype(type);
    }

    /**
     * 根据供应商代码获取可选，催促原因
     *
     * @param suppily
     * @return
     */
    @Override
    public ResultVo<List<OpsInquiryCodeConfig>> getReasonBySuppily(String suppily) {
        if (StringUtils.isBlank(suppily)) {
            return ResultVo.failure("供应商代码为空，不能获取催促原因配置，请检查");
        }
        List<OpsInquiryCodeConfig> resultList = new ArrayList<>();
        Set<String> suppilyList = new HashSet<String>(InquiryVerifyMsg.manuSuppilyList());// 校验是否为中国制造供应商
        if (suppilyList.contains(suppily)) {
            resultList = inquiryCodeConfigMapper.getManuCodeConfig("0");
        }
        if ("JP".equalsIgnoreCase(suppily)) {
            resultList = inquiryCodeConfigMapper.getAs400CodeConfig("0");
        }
        if ("GZ".equalsIgnoreCase(suppily)) {
            resultList = inquiryCodeConfigMapper.getGzCodeConfig("0");
        }
        if (CollectionUtils.isEmpty(resultList)) {
            return ResultVo.failure("未找到该供应商的相关催促原因配置，请联系IT补充");
        }
        return ResultVo.success(resultList);
    }

    /**
     * 获取催促原因所有的配置列表，带参数
     *
     * @return
     */
    @Override
    public ResultVo<List<OpsInquiryCodeConfig>> getAllReason() {
        List<OpsInquiryCodeConfig> resultList = inquiryCodeConfigMapper.selectSendConfig("0");
        if (CollectionUtils.isEmpty(resultList)) {
            return ResultVo.failure("未找到催促原因配置，请联系IT补充");
        }
        return ResultVo.success(resultList);
    }

    @Override
    public ResultVo<List<OpsInquiryReasonParamConfig>> getAllParamConfig() {
        List<OpsInquiryReasonParamConfig> resultList = inquiryReasonParamConfigMapper.getReasonParamConfig();
        if (CollectionUtils.isEmpty(resultList)) {
            return ResultVo.failure("未找到催促原因配置，请联系IT补充");
        }
        return ResultVo.success(resultList);
    }

    // 获取催促原因配置
    private Map<String, String> getInquiryCodecinfig() {
        List<OpsInquiryCodeConfig> list = inquiryCodeConfigMapper.selectSendConfig("0");
        return list.stream().collect(Collectors.toMap(
                OpsInquiryCodeConfig::getOpsReasonDesc, OpsInquiryCodeConfig::getOpsReasonCode,
                (val1, val2) -> val2
        ));
    }


}
