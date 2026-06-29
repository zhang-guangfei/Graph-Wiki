package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.enums.OpsSalesTaskReturnStatus;
import com.smc.smccloud.core.model.enums.SampleBalApplyHandStatusEnum;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.RcvdetailMapper;
import com.smc.smccloud.mapper.sampleorder.SampleBalApplyMapper;
import com.smc.smccloud.mapper.sampleorder.SampleBalPropertyAssignMapper;
import com.smc.smccloud.mapper.sampleorder.SampleOrderManageMapper;
import com.smc.smccloud.mapper.sampleorder.SamplebalMapper;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.DepartmentVO;
import com.smc.smccloud.model.HrOrganizationDto;
import com.smc.smccloud.model.RcvDetailDO;
import com.smc.smccloud.model.constants.Constants;
import com.smc.smccloud.model.enums.BalTypeEnum;
import com.smc.smccloud.model.enums.SampleBalOptCodeEnum;
import com.smc.smccloud.model.fileupload.FileUpload;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.order.SampleBalApplyCallBackVO;
import com.smc.smccloud.model.order.SmsSendOpsDetailTaskBean;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.returnorder.ReturnOrderApplyDTO;
import com.smc.smccloud.model.returnorder.ReturnOrderDO;
import com.smc.smccloud.model.salestask.OpsSalesCommonParamVO;
import com.smc.smccloud.model.salestask.UpTaskInfoVO;
import com.smc.smccloud.model.sampleorder.*;
import com.smc.smccloud.model.stock.DealReturnOpsParam;
import com.smc.smccloud.model.stock.DealReturnOpsParamVO;
import com.smc.smccloud.service.*;
import com.smc.smccloud.util.PriceCompute;
import com.smc.smccloud.utils.JasperHelper;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author lyc
 * @Date 2022/7/22 17:24
 * @Descripton TODO
 */
@Service
@Slf4j
public class SampleBalServiceImpl implements SampleBalService {
    @Resource
    private SamplebalMapper samplebalMapper;
    @Resource
    private SampleOrderApplyService sampleOrderApplyService;
    @Resource
    private HttpServletResponse response;
    @Resource
    private CommonService commonService;
    @Resource
    private JavaMailSenderImpl mailSender;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private OrderLogFeignApi orderLogFeignApi;
    @Resource
    private RedisManager redisManager;
    @Resource
    private SampleOrderManageMapper sampleOrderManageMapper;
    @Resource
    private PlatformTransactionManager transactionManager;

    @Resource
    private DictCommonService dictCommonService;

    @Resource
    private ReceiveOrderServiceFeignApi receiveOrderServiceFeignApi;

    @Resource
    private SampleBalApplyMapper sampleBalApplyMapper;

    @Resource
    private OpsSalesNoticeTaskFeignApi opsSalesNoticeTaskFeignApi;

    @Resource
    private SampleOrderDetailService sampleOrderDetailService;

    @Resource
    private ReturnOrderService returnOrderService;

    @Resource
    private SampleBalPropertyAssignMapper sampleBalPropertyAssignMapper;

    @Resource
    private RcvdetailMapper rcvdetailMapper;

    @Resource
    private SampleBalService sampleBalService;

    @Value("${file.base}")
    private String serverPath;

    @Value("${sales-file-upload-path.url}")
    private String salesFileUploadPath;

    @Override
    public int insertSampleBal(SamplebalDO samplebalDO) {
      return samplebalMapper.insert(samplebalDO);
    }

    /**
     *    如果有未结转的数量，抵扣掉未结转数量，否则写入负的待结转数量
     *    同时记录一条退货取消的记录
     * @param returnOrderDO
     * @return
     */
    @Override
    @DS("opsdb")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public int insertSampleBal(ReturnOrderDO returnOrderDO) {
        LambdaQueryWrapper<SamplebalDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(SamplebalDO::getRorderNo, returnOrderDO.getOrderNo())
                // .eq(SamplebalDO::getBalType, BalTypeEnum.LPFH.getCode())
                .eq(SamplebalDO::getOptCode,1)
                .gt(SamplebalDO::getQuantity, 0);
        List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(lambdaQueryWrapper);

        // 退货未被抵扣数量
        int leftQty = returnOrderDO.getRcvFineqty();
        Date now = new Date();

        if (leftQty <= 0) {
            return 0;
        }

        if (CollectionUtils.isEmpty(samplebalDOList)) {
            SamplebalDO item = convertSampleBal(returnOrderDO);
            item.setBalType(BalTypeEnum.LPFH.getCode()); // 良品返回
            item.setOptCode(SampleBalOptCodeEnum.DJZ.getCode()); // 待结转
            item.setRemark("退货");
            item.setOptTime(now);
            item.setQuantity(leftQty*-1);
            int i = insertSampleBal(item);
            sampleBalService.upSampleBalPropertyAssignResultWitnReturn(item.getId());
            return i;
        }

        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
        SamplebalDO samplebalDO = new SamplebalDO();
        for (SamplebalDO item : samplebalDOList) {
            if(leftQty<=0)
            {
                break;
            }
            //剩余数量大于当前数量，当前项可以被完全抵消，把当前项改成已完结
            if ( leftQty >= item.getQuantity()) {
                updateWrapper.clear();
                // 抵消退货的数据
                updateWrapper
                        .eq(SamplebalDO::getId, item.getId())
                        .set(SamplebalDO::getOptCode, SampleBalOptCodeEnum.YWJ.getCode())
                        .set(SamplebalDO::getOptTime,now)
                        .set(SamplebalDO::getOptDate,now)
                        .set(SamplebalDO::getBalType,BalTypeEnum.LPFH.getCode())
                        .set(StringUtils.isNotBlank(returnOrderDO.getUserNo()),SamplebalDO::getUserNo,returnOrderDO.getUserNo())
                        .set(StringUtils.isNotBlank(returnOrderDO.getCustomerNo()),SamplebalDO::getCustomerNo,returnOrderDO.getCustomerNo())
                        .set(SamplebalDO::getRemark, "退货已抵消"+ item.getQuantity());
                samplebalMapper.update(null, updateWrapper);
                leftQty -= item.getQuantity();
                samplebalDO = item;
            } else {
                List<Long> ids = new ArrayList<>();
                updateWrapper.clear();
                //当前项数量大于剩余数量，当前项的数量减去剩余数量,保持待结转状态
                updateWrapper
                        .eq(SamplebalDO::getId, item.getId())
                        .set(SamplebalDO::getOptTime,now)
                        .set(StringUtils.isNotBlank(returnOrderDO.getUserNo()),SamplebalDO::getUserNo,returnOrderDO.getUserNo())
                        .set(StringUtils.isNotBlank(returnOrderDO.getCustomerNo()),SamplebalDO::getCustomerNo,returnOrderDO.getCustomerNo())
                        .set(SamplebalDO::getQuantity,item.getQuantity() - leftQty)
                        .set(SamplebalDO::getRemark, "退货已抵消"+leftQty);
                samplebalMapper.update(null, updateWrapper);
                ids.add(item.getId());
                // 生成一条已抵扣记录
                samplebalDO = item;
                samplebalDO.setId(null);
                samplebalDO.setOptTime(now);
                samplebalDO.setQuantity(leftQty);
                samplebalDO.setOptDate(now);
                samplebalDO.setUserNo(returnOrderDO.getUserNo());
                samplebalDO.setCustomerNo(returnOrderDO.getCustomerNo());
                samplebalDO.setOptCode(SampleBalOptCodeEnum.YWJ.getCode());
                samplebalDO.setBalType(BalTypeEnum.LPFH.getCode());
                samplebalMapper.insert(samplebalDO);
                ids.add(samplebalDO.getId());
                leftQty=0;
                sampleBalService.upSampleBalPropertyAssignResult(ids);
            }
        }
        //剩余未被抵扣的数量写入负的待结转
        if (leftQty > 0 ) {
            samplebalDO.setId(null);
            samplebalDO.setQuantity(leftQty * -1);
            samplebalDO.setOptTime(now);
            samplebalDO.setUserNo(returnOrderDO.getUserNo());
            samplebalDO.setCustomerNo(returnOrderDO.getCustomerNo());
            samplebalDO.setBalType(BalTypeEnum.LPFH.getCode());
            samplebalDO.setOptCode(SampleBalOptCodeEnum.DJZ.getCode());
            samplebalDO.setRemark("退货");
            insertSampleBal(samplebalDO);
            sampleBalService.upSampleBalPropertyAssignResultWitnReturn(samplebalDO.getId());
        }
        return 1;
    }

    @Override
    public void exportZlzsOrderBalance(ZlzsExportRequest request) {

        if(request == null || StringUtils.isBlank(request.getStartExpDate()) || StringUtils.isBlank(request.getEndExpDate())) {
            return;
        }

        List<SamplebalDO> zlzsOrderBalanceByExpDate = samplebalMapper.findZlzsOrderBalanceByExpDate(request.getStartExpDate(), request.getEndExpDate());
        if (CollectionUtils.isEmpty(zlzsOrderBalanceByExpDate)) {
            return;
        }
        Map<String, SamplebalDO> mapByNodelNo = new HashMap<>();
        Map<String,List<SamplebalDO>> mapByDeptNo = new HashMap<>();
        // 根据型号合并数量
        for (SamplebalDO item : zlzsOrderBalanceByExpDate) {
            if (mapByNodelNo.containsKey(item.getModelNo())) {
                SamplebalDO samplebalDO = mapByNodelNo.get(item.getModelNo());
                samplebalDO.setQuantity(samplebalDO.getQuantity()+item.getQuantity());
                samplebalDO.setRorderNo(samplebalDO.getRorderNo()+";"+item.getRorderNo());
                mapByNodelNo.put(item.getModelNo(),samplebalDO);
            } else {
                mapByNodelNo.put(item.getModelNo(),item);
            }
        }
        List<SamplebalDO> list = new ArrayList<>(mapByNodelNo.values());
        // 按照营业所分组
        for (SamplebalDO item : list) {
            if (StringUtils.isBlank(item.getDeptNo())) {
                continue;
            }
            if (mapByDeptNo.containsKey(item.getDeptNo())) {
                List<SamplebalDO> samplebalDOList = mapByDeptNo.get(item.getDeptNo());
                samplebalDOList.add(item);
                mapByDeptNo.put(item.getDeptNo(),samplebalDOList);
            } else {
                List<SamplebalDO> samplebalDOList = new ArrayList<>();
                samplebalDOList.add(item);
                mapByDeptNo.put(item.getDeptNo(),samplebalDOList);
            }
        }
        // 存入所有的盘点结余数量
        mapByDeptNo.put("allData",list);
        String downloadName = "样品管理-展览展示管理.zip"; // 压缩包名称
        response.setContentType("application/zip; charset=UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + downloadName);
        // 按照营业所导出excel
        String deptName;
        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String deptNo : mapByDeptNo.keySet()) {
            List<SamplebalDO> samplebalDOList = mapByDeptNo.get(deptNo);
            if (CollectionUtils.isEmpty(samplebalDOList)) {
                continue;
            }
            if (deptNo.equals("allData")) {
                deptName = "";
            } else {
                deptName = commonService.getDeptNameByNo(deptNo);
            }
            // 部门为空导出所有部门到一个excel
            Workbook workbook = exportAllZlzsOrderBal(samplebalDOList, deptName, deptNo, request.getEndExpDate());
            if (workbook == null ) {
                continue;
            }
            try {
                ZipEntry entry = new ZipEntry(deptName+"展览展示品盘点票.xlsx");
                zipOutputStream.putNextEntry(entry);
                ByteOutputStream byteOutputStream = new ByteOutputStream();
                workbook.write(byteOutputStream);
                byteOutputStream.writeTo(zipOutputStream);
                byteOutputStream.close();
                zipOutputStream.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 存储导出时间
        redisManager.set(Constants.ZLZS_EXPORT_TIME,request.getStartExpDate()+"="+request.getEndExpDate());
    }

    @Override
    @DS("opsdb")
    public ResultVo<String> writeoffForZlzsOrder(WriteOffZlzsRequest request) {
        if (StringUtils.isBlank(request.getRorderNo())) {
            return ResultVo.failure("订单号不可为空.");
        }
        if (StringUtils.isBlank(request.getModelNo())) {
            return ResultVo.failure("型号不可为空");
        }
        if (request.getQty() <= 0) {
            return ResultVo.failure("销账数量不可为0且请输入正数");
        }
        // 销账数量小于等于剩余数量才可销账
        List<SamplebalDO> samplebalDOList = samplebalMapper.findZlzsOrderBalanceByRorderNoAndModelNo(request.getRorderNo(), request.getModelNo());
        if (CollectionUtils.isEmpty(samplebalDOList)) {
            return ResultVo.failure("没有可销账的数据");
        }
        SamplebalDO samplebalDO = samplebalDOList.get(0);
        if (request.getQty() > samplebalDO.getQuantity()) {
            return ResultVo.failure("销账数量小于等于剩余数量才可销账");
        }
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SamplebalDO::getRorderNo,samplebalDO.getRorderNo())
                .eq(SamplebalDO::getModelNo,samplebalDO.getModelNo())
                .eq(SamplebalDO::getBalType,BalTypeEnum.ZS.getCode())
                .eq(SamplebalDO::getOptCode,SampleBalOptCodeEnum.NHJZ.getCode());
        samplebalDO = samplebalMapper.selectList(queryWrapper).get(0);

        // 写入销账数据
        samplebalDO.setId(null);
        samplebalDO.setOptCode(SampleBalOptCodeEnum.YSZ.getCode());
        samplebalDO.setBalType(BalTypeEnum.ZS.getCode());
        samplebalDO.setOptTime(new Date());
        samplebalDO.setOptDate(new Date());
        samplebalDO.setInvoiceNo(request.getPcNo());
        samplebalDO.setRemark(request.getRemark());
        samplebalDO.setInDate(new Date());
        samplebalDO.setQuantity(request.getQty()*-1);
        int insert = samplebalMapper.insert(samplebalDO);
        // 记录销账操作日志
        insertSampleBalLog(samplebalDO);
        if (insert != 1) {
           return ResultVo.failure("订单号"+request.getRorderNo()+"型号"+request.getModelNo()+"销账失败.");
        }
        return ResultVo.success("订单号"+request.getRorderNo()+"型号"+request.getModelNo()+"销账成功.");
    }

    @Override
    @Transactional(rollbackFor = Exception.class )
    @DS("opsdb")
    public ResultVo<String> importWriteOffData(MultipartFile file, String loginUser) {

        if (file == null) {
            return ResultVo.failure("请上传文件");
        }

        if (StringUtils.isBlank(loginUser)) {
            return ResultVo.failure("操作人为空.");
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(filename) && !filename.matches("^.+\\.(?i)(xlsx)$")
                && !filename.matches("^.+\\.(?i)(xls)$")) {
            return ResultVo.failure("文件格式错误,请按照模板文件格式进行导入");
        }
        ExcelHelper excel = null;
        try {
            excel = new ExcelHelper(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (excel == null) {
            return ResultVo.failure("未读取到文件.");
        }
        Sheet sheet = excel.getSheet();
        int row = 0;
        Row rows;
        WriteOffZlzsRequest writeOffZlzsRequest;
        ResultVo<String> resultVo;
        StringBuilder errMsg = new StringBuilder();
        while (true) {
            row++;
            rows = sheet.getRow(row);
            if (rows == null) {
                break;
            }
            String rorderNo = excel.getCellString(rows.getCell(0));
            if (StringUtils.isBlank(rorderNo)) {
                throw new BusinessException("第"+row+"行的订单号为空.请仔细检查数据");
            }
            String modelNo = excel.getCellString(rows.getCell(1));
            if (StringUtils.isBlank(modelNo)) {
                throw new BusinessException("第"+row+"行的型号为空.请仔细检查数据");
            }
            String qtyStr = excel.getCellString(rows.getCell(2));
            if (StringUtils.isBlank(qtyStr)) {
                throw new BusinessException("第"+row+"行的数量为空.请仔细检查数据");
            }
            int qty = Integer.parseInt(qtyStr);
            if (qty <= 0) {
                throw new BusinessException("第"+row+"行的数量小于等于0.请仔细检查数据");
            }
            String remark = excel.getCellString(rows.getCell(3));
            String pcNo = excel.getCellString(rows.getCell(4));

            writeOffZlzsRequest = new WriteOffZlzsRequest();
            writeOffZlzsRequest.setRorderNo(rorderNo);
            writeOffZlzsRequest.setModelNo(modelNo);
            writeOffZlzsRequest.setQty(qty);
            writeOffZlzsRequest.setPcNo(pcNo);
            writeOffZlzsRequest.setRemark(remark);
            resultVo = writeoffForZlzsOrder(writeOffZlzsRequest);
            if (!resultVo.isSuccess()) {
                errMsg.append(resultVo.getMessage()+";");
            }
        }
        if (StringUtils.isBlank(errMsg.toString())) {
            return ResultVo.success("批量销账完毕.");
        }
        return ResultVo.failure(errMsg.toString());
    }

    @Override
    public ResultVo<String> pushZlzsOrderBalanceForExcel(ZlzsExportRequest request) {
        if(request == null || StringUtils.isBlank(request.getStartExpDate()) || StringUtils.isBlank(request.getEndExpDate())) {
            return ResultVo.failure("请先导出展示品盘点票 核对无误再进行发布");
        }

        List<SamplebalDO> zlzsOrderBalanceByExpDate = samplebalMapper.findZlzsOrderBalanceByExpDate(request.getStartExpDate(), request.getEndExpDate());
        if (CollectionUtils.isEmpty(zlzsOrderBalanceByExpDate)) {
            return ResultVo.failure("所选时间范围没有展示品盘点票数据,请选择扩大时间范围");
        }
        Map<String, SamplebalDO> mapByNodelNo = new HashMap<>();
        Map<String,List<SamplebalDO>> mapByDeptNo = new HashMap<>();
        // 根据型号合并数量
        for (SamplebalDO item : zlzsOrderBalanceByExpDate) {
            if (mapByNodelNo.containsKey(item.getModelNo())) {
                SamplebalDO samplebalDO = mapByNodelNo.get(item.getModelNo());
                samplebalDO.setQuantity(samplebalDO.getQuantity()+item.getQuantity());
                samplebalDO.setRorderNo(samplebalDO.getRorderNo()+";"+item.getRorderNo());
                mapByNodelNo.put(item.getModelNo(),samplebalDO);
            } else {
                mapByNodelNo.put(item.getModelNo(),item);
            }
        }
        List<SamplebalDO> list = new ArrayList<>(mapByNodelNo.values());
        // 按照营业所分组
        for (SamplebalDO item : list) {
            if (StringUtils.isBlank(item.getDeptNo())) {
                continue;
            }
            if (mapByDeptNo.containsKey(item.getDeptNo())) {
                List<SamplebalDO> samplebalDOList = mapByDeptNo.get(item.getDeptNo());
                samplebalDOList.add(item);
                mapByDeptNo.put(item.getDeptNo(),samplebalDOList);
            } else {
                List<SamplebalDO> samplebalDOList = new ArrayList<>();
                samplebalDOList.add(item);
                mapByDeptNo.put(item.getDeptNo(),samplebalDOList);
            }
        }
        // 存入所有的盘点结余数量
        mapByDeptNo.put("allData",list);
        String downloadName = "样品管理-展览展示管理.zip"; // 压缩包名称
        response.setContentType("application/zip; charset=UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + downloadName);
        // 按照营业所导出excel
        String deptName;
        StringBuilder errMsg = new StringBuilder();
        for (String deptNo : mapByDeptNo.keySet()) {
            if (redisManager.hasKey(Constants.ZLZS_SEND_DEPTNOS+deptNo)) {
                continue;
            }
            List<SamplebalDO> samplebalDOList = mapByDeptNo.get(deptNo);
            if (CollectionUtils.isEmpty(samplebalDOList)) {
                continue;
            }
            if (deptNo.equals("allData")) {
                deptName = "";
            } else {
                deptName = commonService.getDeptNameByNo(deptNo);
            }
            // 部门为空导出所有部门到一个excel
           // exportAllZlzsOrderBal(samplebalDOList, deptName, deptNo, request.getEndExpDate());
            String path = "template/样品管理-展览展示管理.xlsx";
            ExcelHelper excel = new ExcelHelper(path);
            excel.openSheet(0);
            // 向模板中写入数据
            int row = 4;
            int count = 0;
            int cel;
            String title = deptName + "展览展示品盘点票";
            String endTime = "数据截止日: "+ DateUtil.dateToDateStringWithYMD(request.getEndExpDate());
            String nowDate = "盘点时间: "+ DateUtil.dateToDateStringWithYMD(DateUtil.dateToDateString(new Date()));
            excel.setCellValue(0,0,title);
            excel.setCellValue(2,1,endTime);
            excel.setCellValue(2,6,nowDate);

            for (SamplebalDO item : samplebalDOList) {
                cel = 0;
                count++;
                excel.setCellValue(row,cel++,count); // 序号
                excel.setCellValue(row,cel++,item.getModelNo()); // 型号
                excel.setCellValue(row,cel++,item.getRorderNo()); // 订单号
                excel.setCellValue(row,cel++,item.getQuantity()); // 账本数量
                excel.setCellValue(row,cel++,""); // 营业所实际数量
                excel.setCellValue(row,cel++,""); // 差异说明
                excel.setCellValue(row,cel++,""); // 备注
                row++;
            }
            ResultVo<String> resultVo = sendEamilWithDept(deptNo, title, excel, true);
            if (!resultVo.isSuccess()) {
                errMsg.append(resultVo.getMessage()+";");
            } else {
                // 将成功的存入redis
                redisManager.set(Constants.ZLZS_SEND_DEPTNOS+deptNo,deptNo,3600*24*7);
            }
        }
        if (StringUtils.isBlank(errMsg.toString())) {
            return ResultVo.success("发送成功");
        }
        return ResultVo.failure(errMsg.toString());
    }

    @Override
    public ResultVo<String> pushZlzsOrderBalanceForPdf(ZlzsExportRequest request) {

        if(request == null || StringUtils.isBlank(request.getStartExpDate()) || StringUtils.isBlank(request.getEndExpDate())) {
            return ResultVo.failure("请先导出展示品盘点票 核对无误再进行发布");
        }

        List<SamplebalDO> zlzsOrderBalanceByExpDate = samplebalMapper.findZlzsOrderBalanceByExpDate(request.getStartExpDate(), request.getEndExpDate());
        if (CollectionUtils.isEmpty(zlzsOrderBalanceByExpDate)) {
            return ResultVo.failure("所选时间范围没有展示品盘点票数据,请选择扩大时间范围");
        }
        Map<String, SamplebalDO> mapByNodelNo = new HashMap<>();
        Map<String,List<SamplebalDO>> mapByDeptNo = new HashMap<>();
        // 根据型号合并数量
        for (SamplebalDO item : zlzsOrderBalanceByExpDate) {
            if (mapByNodelNo.containsKey(item.getModelNo())) {
                SamplebalDO samplebalDO = mapByNodelNo.get(item.getModelNo());
                samplebalDO.setQuantity(samplebalDO.getQuantity()+item.getQuantity());
                samplebalDO.setRorderNo(samplebalDO.getRorderNo()+";"+item.getRorderNo());
                mapByNodelNo.put(item.getModelNo(),samplebalDO);
            } else {
                mapByNodelNo.put(item.getModelNo(),item);
            }
        }
        List<SamplebalDO> list = new ArrayList<>(mapByNodelNo.values());
        // 按照营业所分组
        for (SamplebalDO item : list) {
            if (StringUtils.isBlank(item.getDeptNo())) {
                continue;
            }
            if (mapByDeptNo.containsKey(item.getDeptNo())) {
                List<SamplebalDO> samplebalDOList = mapByDeptNo.get(item.getDeptNo());
                samplebalDOList.add(item);
                mapByDeptNo.put(item.getDeptNo(),samplebalDOList);
            } else {
                List<SamplebalDO> samplebalDOList = new ArrayList<>();
                samplebalDOList.add(item);
                mapByDeptNo.put(item.getDeptNo(),samplebalDOList);
            }
        }
        // 存入所有的盘点结余数量
        mapByDeptNo.put("allData",list);
//        String downloadName = "样品管理-展览展示管理.zip"; // 压缩包名称
//        response.setContentType("application/zip; charset=UTF-8");
//        response.setHeader("Content-disposition", "attachment;filename=" + downloadName);
        // 按照营业所导出excel
        String deptName;
        StringBuilder errMsg = new StringBuilder();
        for (String deptNo : mapByDeptNo.keySet()) {
            if (redisManager.hasKey(Constants.ZLZS_SEND_DEPTNOS+deptNo)) {
                continue;
            }
            List<SamplebalDO> samplebalDOList = mapByDeptNo.get(deptNo);
            if (CollectionUtils.isEmpty(samplebalDOList)) {
                continue;
            }
            if (deptNo.equals("allData")) {
                deptName = "全部所";
            } else {
                deptName = opsCommonService.getDeptNameByNo(deptNo);
            }
            // 生成pdf
            ZlzsExportPdfVO zlzsExportPdfVO = new ZlzsExportPdfVO();
            List<ZlszPdfTableVo> zlzsExportPdfVOS = new ArrayList<>();
            for (SamplebalDO item : samplebalDOList) {
                ZlszPdfTableVo vo = new ZlszPdfTableVo();
                vo.setModelNo(item.getModelNo());
                vo.setOrderNo(item.getRorderNo());
                vo.setQuantity(item.getQuantity());
                zlzsExportPdfVOS.add(vo);
            }
            zlzsExportPdfVO.setTabledataWithZlzs(zlzsExportPdfVOS);
            Map<String, Object> map = new HashMap<>();
            map.put("deptName",deptName);
            map.put("endTime",request.getEndExpDate());
            map.put("nowDate",request.getStartExpDate());

            List<ZlzsExportPdfVO> zlzsExportPdfVOList = new ArrayList<>();
            zlzsExportPdfVOList.add(zlzsExportPdfVO);

            InputStream inputStream = FileUtil.getTemplate("template/jasper/zlzsBalance.jasper");
            String title = "展览展示品盘点票-"+deptName;
            // String fileName = title + ".pdf";
            InputStream streamPdf = null;
            try {
                streamPdf = JasperHelper.savePdfToInputStrem(inputStream, map, zlzsExportPdfVOList);
            } catch (JRException e) {
                log.error(deptName+"预览展览展示品盘点票发生异常,",e);
                errMsg.append(deptName+"生成pdf文件失败.");
                continue;
            }
            ResultVo<String> resultVo = sendPdfEamilWithDept(deptNo, title, streamPdf);
            if (!resultVo.isSuccess()) {
                errMsg.append(resultVo.getMessage()+";");
            } else {
                // 将成功的存入redis
                redisManager.set(Constants.ZLZS_SEND_DEPTNOS+deptNo,deptNo,3600*24*7);
            }
        }
        if (StringUtils.isBlank(errMsg.toString())) {
            return ResultVo.success("发送成功");
        }

        return ResultVo.failure(errMsg.toString());
    }

    @Override
    public ResultVo<String> getZLZSExportTime() {
        Object o = redisManager.get(Constants.ZLZS_EXPORT_TIME);
        if (o == null) {
            return ResultVo.failure("请先进行展示品盘点票导出操作.并核对数据是否正确");
        }
        return ResultVo.success(o.toString());
    }

    @Override
    public void downExcelForWriteOff() {
        String path = "template/"+Constants.ZLZS_WRITEOFF_FINENAME;
        ExcelUtil excel = new ExcelUtil(path);
        excel.writeToResponse(response, Constants.ZLZS_WRITEOFF_FINENAME);
    }

    @Override
    @DS("opsdb")
    public ResultVo<String> upZlzsRcvDeptNo(UpZlzsRcvDeptNoParams params) {
        if (params == null) {
            return ResultVo.failure("参数不可为空.");
        }

        if (StringUtils.isBlank(params.getRorderNo())) {
            return ResultVo.failure("订单号不可为空.");
        }
        if (StringUtils.isBlank(params.getRcvDeptNo())) {
            return ResultVo.failure("需要变更的展示品实物所在部门不可为空.");
        }
        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(SamplebalDO::getRorderNo,params.getRorderNo())
                .set(SamplebalDO::getRcvDeptNo,params.getRcvDeptNo())
                .set(SamplebalDO::getOptDate,new Date())
                .set(SamplebalDO::getOptTime,new Date());
        int update = samplebalMapper.update(null, updateWrapper);
        if (update != 1) {
            return ResultVo.failure(params.getRorderNo()+"变更展示品实物收货部门为"+params.getRcvDeptNo()+"失败.");
        }
        // 记录操作日志
        OrderLogVO orderLogVO = new OrderLogVO();
        orderLogVO.setOrderNo(params.getRorderNo());
        orderLogVO.setOptType(9);
        orderLogVO.setOptTime(new Date());
        orderLogVO.setCreateTime(new Date());
        orderLogVO.setDescription("变更展示品实物营业所为"+params.getRcvDeptNo());
        orderLogVO.setOptUserName(params.getOptUserNo());
        orderLogFeignApi.addOrderLog(orderLogVO);
        return ResultVo.success(params.getRorderNo()+"变更展示品实物收货部门为"+params.getRcvDeptNo()+"成功.");
    }

    @Override
    public void downExcelForUpRcvDeptNo() {
        String path = "template/"+Constants.ZLZS_UPRCVDEPTNO_FINENAME;
        ExcelUtil excel = new ExcelUtil(path);
        excel.writeToResponse(response, Constants.ZLZS_UPRCVDEPTNO_FINENAME);
    }

    @Override
    @Transactional(rollbackFor = Exception.class )
    @DS("opsdb")
    public ResultVo<String> batchUpRcvDeptNo(MultipartFile file, String loginUser) {

        if (file == null) {
            return ResultVo.failure("请上传文件");
        }

        if (StringUtils.isBlank(loginUser)) {
            return ResultVo.failure("操作人为空.");
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(filename) && !filename.matches("^.+\\.(?i)(xlsx)$")
                && !filename.matches("^.+\\.(?i)(xls)$")) {
            return ResultVo.failure("文件格式错误,请按照模板文件格式进行导入");
        }
        ExcelHelper excel = null;
        try {
            excel = new ExcelHelper(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (excel == null) {
            return ResultVo.failure("未读取到文件.");
        }
        Sheet sheet = excel.getSheet();
        int row = 0;
        Row rows;
        UpZlzsRcvDeptNoParams upZlzsRcvDeptNoVO;
        ResultVo<String> resultVo;
        StringBuilder errMsg = new StringBuilder();

        while (true) {
            row++;
            rows = sheet.getRow(row);
            if (rows == null) {
                break;
            }
            String rorderNo = excel.getCellString(rows.getCell(0));
            if (StringUtils.isBlank(rorderNo)) {
                throw new BusinessException("第"+row+"行的订单号为空.请仔细检查数据");
            }
            String rcvDeptNo = excel.getCellString(rows.getCell(1));
            if (StringUtils.isBlank(rcvDeptNo)) {
                throw new BusinessException("第"+row+"行的型号为空.请仔细检查数据");
            }
            upZlzsRcvDeptNoVO = new UpZlzsRcvDeptNoParams();
            upZlzsRcvDeptNoVO.setOptUserNo(loginUser);
            upZlzsRcvDeptNoVO.setRorderNo(rorderNo);
            upZlzsRcvDeptNoVO.setRcvDeptNo(rcvDeptNo);
            resultVo = upZlzsRcvDeptNo(upZlzsRcvDeptNoVO);
            if (!resultVo.isSuccess()) {
                errMsg.append(resultVo.getMessage()+";");
            }
        }
        if (StringUtils.isBlank(errMsg.toString())) {
            return ResultVo.success("批量变更完毕.");
        }
        return ResultVo.failure(errMsg.toString());
    }

    @Override
    public int updateSampleById(UpSamplelBalVO upSamplelBalVO) {

        if (upSamplelBalVO == null) {
            return 0;
        }

        if (StringUtils.isBlank(upSamplelBalVO.getId())) {
            return 0;
        }

        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(SamplebalDO::getId,upSamplelBalVO.getId())
                .set(StringUtils.isNotBlank(upSamplelBalVO.getInvoiceNo()),SamplebalDO::getInvoiceNo,upSamplelBalVO.getInvoiceNo())
                .set(StringUtils.isNotBlank(upSamplelBalVO.getInDate()),SamplebalDO::getInDate,upSamplelBalVO.getInDate())
                .set(StringUtils.isNotBlank(upSamplelBalVO.getBalType()),SamplebalDO::getBalType,upSamplelBalVO.getBalType())
                .set(StringUtils.isNotBlank(upSamplelBalVO.getAppType()),SamplebalDO::getAppType,upSamplelBalVO.getAppType())
                .set(StringUtils.isNotBlank(upSamplelBalVO.getOptCode()),SamplebalDO::getOptCode,upSamplelBalVO.getOptCode());
        return samplebalMapper.update(null,updateWrapper);
    }

    @Override
    public ResultVo<String> insertIntoSampleOrderManage(ZlzsExportRequest request) {

        if(request == null || StringUtils.isBlank(request.getStartExpDate()) || StringUtils.isBlank(request.getEndExpDate())) {
            return ResultVo.failure("请选择导出时间范围");
        }
        List<SamplebalDO> samplebalDOList = samplebalMapper.findZlzsOrderIntoSampleOrderManageByinDate(request.getStartExpDate(), request.getEndExpDate());
        if (CollectionUtils.isEmpty(samplebalDOList)) {
            return ResultVo.failure("所选时间范围未查到已结转的展览展示品数据");
        }
        SampleOrderManageDO sampleOrderManageDO;
        StringBuilder errMsg = new StringBuilder();

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        AtomicInteger totalCount = new AtomicInteger();
        AtomicInteger totalQty = new AtomicInteger();

        AtomicInteger errCount = new AtomicInteger();

        List<HrOrganizationDto> deptNoByHLSalesDeptNo = opsCommonService.getDeptNoByHLSalesDeptNo();
        if(CollectionUtils.isEmpty(deptNoByHLSalesDeptNo)) {
            return ResultVo.failure("未查询到部门组织信息.");
        }
        Map<String, String> deptNoMap = deptNoByHLSalesDeptNo.stream().collect(Collectors.toMap(HrOrganizationDto::getId, HrOrganizationDto::getEndDeptNo));


        for (SamplebalDO item : samplebalDOList) {
            sampleOrderManageDO = new SampleOrderManageDO();
            sampleOrderManageDO.setOrderNo(item.getRorderNo());
            sampleOrderManageDO.setModelNo(item.getModelNo());
            sampleOrderManageDO.setShipDate(item.getExpDate());
            sampleOrderManageDO.setImpQty(item.getQuantity());
            sampleOrderManageDO.setStatus(1);
            sampleOrderManageDO.setDeptNo(item.getRcvDeptNo());
            sampleOrderManageDO.setUpdateTime(new Date());
            sampleOrderManageDO.setCreateTime(new Date());
            sampleOrderManageDO.setOutTime(item.getInDate());
            sampleOrderManageDO.setParentDeptNo(deptNoMap.get(item.getRcvDeptNo()));
            SampleOrderManageDO finalSampleOrderManageDO = sampleOrderManageDO;
            transactionTemplate.execute(transactionStatus -> {
                try {
                    sampleOrderManageMapper.insert(finalSampleOrderManageDO);
                    // 通过id变更状态6->8
                    sampleOrderApplyService.updateSampleBalOptCodeById(item.getId());
                    // 计算导入条数
                    totalCount.getAndIncrement();
                    // 成功导入总数量
                    totalQty.set(totalQty.get() + item.getQuantity());
                } catch (Exception e) {
                    errCount.getAndIncrement();
                    errMsg.append(item.getRorderNo()+";");
                    transactionStatus.setRollbackOnly(); // 手动回滚
                    return false;
                }
                return true;
            });
        }
        String desc;
        if (StringUtils.isBlank(errMsg.toString())) {
            desc = "成功导入"+totalCount+"条, 数量共计"+totalQty+";导入失败:"+errMsg.toString();
        } else {
            desc = "成功导入"+totalCount+"条, 数量共计"+totalQty+";导入失败:"+errCount+"条,单号:"+errMsg.toString();
        }
        return ResultVo.success(desc);
    }

    @Override
    public ResultVo<PageInfo<SampleBalApplyVO>> findSampleBalApplyInfoList(QuerySampleBalApplyParam info) {

        LambdaQueryWrapper<SampleBalApplyDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .likeRight(StringUtils.isNotBlank(info.getSampleBalApplyNo()),SampleBalApplyDO::getSampleBalApplyNo,info.getSampleBalApplyNo())
                .eq(StringUtils.isNotBlank(info.getHandStatus()),SampleBalApplyDO::getHandStatus,info.getHandStatus())
                .eq(StringUtils.isNotBlank(info.getOrderNo()),SampleBalApplyDO::getOrderNo,info.getOrderNo())
                .in(CollectionUtils.isNotEmpty(info.getApplyDeptNo()),SampleBalApplyDO::getApplyDeptNo,info.getApplyDeptNo())
                .in(CollectionUtils.isNotEmpty(info.getApplyType()),SampleBalApplyDO::getApplyType,info.getApplyType())
                .in(CollectionUtils.isNotEmpty(info.getBalType()),SampleBalApplyDO::getApplyBalType,info.getBalType())
                .gt(StringUtils.isNotBlank(info.getApplyTimeStart()),SampleBalApplyDO::getApplyTime,info.getApplyTimeStart())
                .lt(StringUtils.isNotBlank(info.getApplyTimeEnd()),SampleBalApplyDO::getApplyTime,info.getApplyTimeEnd())
                .orderByAsc(SampleBalApplyDO::getSampleBalApplyNo);

        PageInfo<SampleBalApplyDO> pageInfo = PageHelper.startPage(info.getPage().getPageNumber(), info.getPage().getPageSize())
                .doSelectPageInfo(() -> sampleBalApplyMapper.selectList(lambdaQueryWrapper));

        PageInfo<SampleBalApplyVO> pageDto2Vo = BeanCopyUtil.pageDto2Vo(pageInfo, SampleBalApplyVO.class);
        if (CollectionUtils.isNotEmpty(pageDto2Vo.getList())) {
            for (SampleBalApplyVO item : pageDto2Vo.getList()) {
                if (item.getHandStatus() != null) {
                    item.setHandStatusName(SampleBalApplyHandStatusEnum.getCodeNameByCode(String.valueOf(item.getHandStatus())));
                }
                if (StringUtils.isNotBlank(item.getBackWarehource())) {
                    item.setBackWarehource(commonService.getWarehouseNameByCode(item.getBackWarehource())+"["+item.getBackWarehource()+"]");
                }
                if (StringUtils.isNotBlank(item.getApplyPsnNo())){
                    item.setApplyPsnNo(commonService.getEmpSaleNameByNo(item.getApplyPsnNo())+"["+item.getApplyPsnNo()+"]");
                }
                if (StringUtils.isNotBlank(item.getApplyDeptNo())){
                    item.setApplyDeptNo(commonService.getDeptNameByNo(item.getApplyDeptNo())+"["+item.getApplyDeptNo()+"]");
                }
                if (StringUtils.isNotBlank(item.getApplyBalType())){
                    item.setApplyBalType(BalTypeEnum.getCodeName(item.getApplyBalType()));
                }
                if (StringUtils.isNotBlank(item.getCustomerNo())) {
                    item.setCustomerNo("["+item.getCustomerNo()+"]"+commonService.getCustomerNameByNo(item.getCustomerNo()));
                }
                SampleBalApplySpecialVO specialVO = JSONObject.parseObject(item.getSpecial(), SampleBalApplySpecialVO.class);
                if (specialVO != null) {
                    item.setForceBalFlag(specialVO.getForceBalFlag() ? "是" : "否");
                }
            }
        }
        return ResultVo.success(pageDto2Vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVo<String> sureApplySampleBal(List<String> ids) {

        if (CollectionUtils.isEmpty(ids)) {
            return ResultVo.failure("请选择确认结转的数据");
        }
        LoginUserDTO loginAuthDto;
        try {
             loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            return ResultVo.failure("个人登录信息失效,请重新登录再操作");
        }

        LambdaQueryWrapper<SampleBalApplyDO> queryWrapper = new LambdaQueryWrapper<>();
        StringBuilder errorMsg = new StringBuilder();
        Boolean balFlag = false; // 是否结转成功
        int okCount = 0;
        String failMsg = "";

        for (String id : ids) {

            // 查询申请单信息 获取申请单
            queryWrapper.clear();
            queryWrapper.eq(SampleBalApplyDO::getId,id);
            SampleBalApplyDO sampleBalApplyDO = sampleBalApplyMapper.selectOne(queryWrapper);
            if (!SampleBalApplyHandStatusEnum.alreadyHand.getCode().equals(String.valueOf(sampleBalApplyDO.getHandStatus())) &&
                    !BalTypeEnum.XSKP.getCode().equals(sampleBalApplyDO.getApplyBalType())) {
                errorMsg.append(sampleBalApplyDO.getSampleBalApplyNo()).append("仅已受理状态的申请单才可申请确认结转.");
                continue;
            }
            //设置回滚点
            Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();

            try {
                if(BalTypeEnum.LPFH.getCode().equals(sampleBalApplyDO.getApplyBalType())) {
                    // 良品返回
                    if (sampleBalApplyDO.getSignQty() == 0  || sampleBalApplyDO.getSignTime() == null ) {
                        failMsg = sampleBalApplyDO.getApplyNo()+";"+sampleBalApplyDO.getOrderNo()+"还没确认收货,不能结转";
                        balFlag = false;
                    } else {
                        // 将收货登记写入的结转数据改成待转出
                        sampleOrderApplyService.updateLPFHData(sampleBalApplyDO,loginAuthDto.getUserNo());
                        balFlag = true;
                    }
                } else {
                    // 结转类型非转销售开票
                    if (!BalTypeEnum.XSKP.getCode().equals(sampleBalApplyDO.getApplyBalType())) {
                        if (sampleBalApplyDO.getIsAlreadyBal()) {
                            // 已经结转过 需要生成一条负数进行抵消 然后设置sourceId 再写入申请的单据
                            ResultVo<String> resultVo = sampleOrderApplyService.againBal(sampleBalApplyDO);
                            if (!resultVo.isSuccess()) {
                                balFlag = false;
                            } else {
                                balFlag = true;
                            }
                        } else {
                            SamplebalDO samplebalDO = new SamplebalDO();
                            samplebalDO.setBalType(sampleBalApplyDO.getApplyBalType());
                            samplebalDO.setAppType(sampleBalApplyDO.getApplyType());
                            // 生成发票号
                            String invoiceNo = sampleOrderDetailService.getInvoiceNoByCostType(samplebalDO);
                            if (StringUtils.isBlank(invoiceNo)) {
                                return ResultVo.failure("发票号生成失败.");
                            }
                            // 直接改sample_bal的数据为待转出
                            // balFlag = sampleOrderApplyService.updateSampleBalOptCodeById(sampleBalApplyDO, loginAuthDto.getUserNo(),invoiceNo);
                            balFlag = sampleOrderApplyService.updateSampleBySmapleApply(sampleBalApplyDO,loginAuthDto.getUserNo(),invoiceNo);
                        }
                    } else {
                        /**
                         * 如果没有结转过 则直接修改结转表申请单价为申请结转单价,状态为待转出
                         *   结转过:  需要生成对冲数据
                         */
                        if (sampleBalApplyDO.getIsAlreadyBal()) {
                            ResultVo<String> resultVo = sampleOrderApplyService.againBal(sampleBalApplyDO);
                            if (!resultVo.isSuccess()) {
                                balFlag = false;
                            } else {
                                balFlag = true;
                            }
                        } else {
                            // 修改结转表申请单价为申请结转单价,状态为待转出
                            balFlag = sampleOrderApplyService.updateSampleBySmapleApply(sampleBalApplyDO,loginAuthDto.getUserNo(),"");
                        }
                    }
                }
                
                // 回调门户
                if(!balFlag) {
                    // 回改结转申请表
                    upSampleBalApplyInfo(balFlag,sampleBalApplyDO,loginAuthDto.getUserNo());
                    errorMsg.append(sampleBalApplyDO.getOrderNo()).append("确认结转失败;").append(failMsg);
                    upOpsSalesNoticeTaskInfo(sampleBalApplyDO,loginAuthDto.getUserNo(),balFlag,errorMsg.toString());
                } else {
                    // 回改结转申请表
                    upSampleBalApplyInfo(balFlag,sampleBalApplyDO,loginAuthDto.getUserNo());
                    upOpsSalesNoticeTaskInfo(sampleBalApplyDO,loginAuthDto.getUserNo(),balFlag,"结转成功");
                    okCount++;
                }
            } catch (Exception e) {
                log.info("确认结转申请异常: {}",e.getMessage(),e);
                errorMsg.append(sampleBalApplyDO.getOrderNo()).append("确认结转失败:").append(e.getMessage()).append(";");
                //回滚当前事务
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            }
        }
        if (StringUtils.isBlank(errorMsg)) {
            return ResultVo.success(okCount+"条操作成功");
        }
        return ResultVo.success(okCount+"条操作成功,"+errorMsg.toString());
    }

    public Boolean updateLPFHData(SampleBalApplyDO sampleBalApplyDO,String optUser) {

        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(SamplebalDO::getRorderNo,sampleBalApplyDO.getOrderNo())
                .eq(SamplebalDO::getBalType,BalTypeEnum.LPFH.getCode())
                .eq(SamplebalDO::getOptCode,SampleBalOptCodeEnum.DJZ.getCode())
                .set(SamplebalDO::getOptCode,SampleBalOptCodeEnum.DZC.getCode())
                .set(SamplebalDO::getOptTime,new Date())
                .set(SamplebalDO::getInDate,new Date())
                .set(SamplebalDO::getOptDate,new Date());
        samplebalMapper.update(null,updateWrapper);
        return true;
    }

    public ResultVo<String> againBal(SampleBalApplyDO sampleBalApplyDO) {
        // 查询结转源数据
        SamplebalDO sampleBalDOById = getSampleBalDOById(sampleBalApplyDO.getSampleBalId());
        SamplebalDO cloneSampleBalObj = SerializationUtils.clone(sampleBalDOById);
        if (sampleBalDOById == null) {
            return ResultVo.failure("暂未找到源结转数据");
        }
        // 填充当前单sourceId
        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SamplebalDO::getId,sampleBalDOById.getId()).set(SamplebalDO::getSource,String.valueOf(sampleBalDOById.getId()));
        samplebalMapper.update(null,updateWrapper);

        Date nowDate = new Date();
        String invoiceNo = sampleOrderDetailService.getInvoiceNoByCostType(sampleBalDOById);
        if (StringUtils.isBlank(invoiceNo)) {
            return ResultVo.failure("生成冲负数据的发票号为空.重新结转失败.");
        }
        if (StringUtils.isBlank(sampleBalDOById.getSource())) {
            // 生成对冲的负数据
            sampleBalDOById.setQuantity(sampleBalDOById.getQuantity()*-1);
            sampleBalDOById.setOptCode(SampleBalOptCodeEnum.DZC.getCode());
            sampleBalDOById.setInvoiceNo(invoiceNo);
            sampleBalDOById.setOptDate(nowDate);
            sampleBalDOById.setOptTime(nowDate);
            sampleBalDOById.setInDate(nowDate);
            sampleBalDOById.setCreatetime(nowDate);
            sampleBalDOById.setId(null);
            samplebalMapper.insert(sampleBalDOById);

            // 设置负数数据的sourceId
            LambdaQueryWrapper<SamplebalDO> query = new LambdaQueryWrapper<>();
            query
                    .eq(SamplebalDO::getRorderNo,sampleBalDOById.getRorderNo())
                    .eq(SamplebalDO::getQuantity,sampleBalDOById.getQuantity())
                    .eq(SamplebalDO::getBalType,sampleBalDOById.getBalType())
                    .eq(SamplebalDO::getOptCode,SampleBalOptCodeEnum.DZC.getCode());
            List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(query);
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(samplebalDOList)){
                SamplebalDO obj = samplebalDOList.get(0);
                updateWrapper.clear();
                updateWrapper.eq(SamplebalDO::getId,obj.getId()).set(SamplebalDO::getSource,sampleBalApplyDO.getSampleBalId()+"-"+obj.getId());
                samplebalMapper.update(null,updateWrapper);
            }
        }

        if ( cloneSampleBalObj.getQuantity() > sampleBalApplyDO.getQuantity()) {
            int leftQty = cloneSampleBalObj.getQuantity() - sampleBalApplyDO.getQuantity();
            cloneSampleBalObj.setQuantity(leftQty);
            cloneSampleBalObj.setInvoiceNo(invoiceNo);
            cloneSampleBalObj.setOptCode(SampleBalOptCodeEnum.DZC.getCode());
            cloneSampleBalObj.setOptDate(nowDate);
            cloneSampleBalObj.setOptTime(nowDate);
            cloneSampleBalObj.setInDate(nowDate);
            cloneSampleBalObj.setId(null);
            samplebalMapper.insert(sampleBalDOById);
        }

        SamplebalDO balDo = new SamplebalDO();
        balDo.setCustomerNo(sampleBalApplyDO.getCustomerNo());
        balDo.setRorderNo(sampleBalApplyDO.getOrderNo());
        balDo.setModelNo(sampleBalApplyDO.getModelNo());
        balDo.setQuantity(sampleBalApplyDO.getQuantity());
        balDo.setDeptDesc(sampleBalApplyDO.getBalDeptNo());
        balDo.setPrice(sampleBalApplyDO.getPrice());
        if (sampleBalApplyDO.getApplyBalPrice() != null) {
            balDo.setAmount(sampleBalApplyDO.getApplyBalPrice().multiply(BigDecimal.valueOf(sampleBalApplyDO.getQuantity())).setScale(4, RoundingMode.HALF_UP));
        }
        // 获取税率
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo("9002", "1");
        BigDecimal taxRate;
        if (dataTypeCodesInfo.isSuccess() && dataTypeCodesInfo.getData() != null) {
            taxRate = new BigDecimal(dataTypeCodesInfo.getData().getExtNote1());
        } else {
            taxRate = new BigDecimal("0.13");
        }
        if(balDo.getAmount() != null) {
            BigDecimal ntaxAmount = PriceCompute.ntaxAmount(balDo.getAmount(), taxRate);
            balDo.setTaxAmount(PriceCompute.taxAmount(balDo.getAmount(),ntaxAmount));
        }

        balDo.setOptDate(nowDate);
        balDo.setAppType(sampleBalApplyDO.getApplyType());
        balDo.setBalType(sampleBalApplyDO.getApplyBalType());
        balDo.setProdFlag(sampleBalDOById.getProdFlag());
        balDo.setProdCode(sampleBalDOById.getProdCode());
        balDo.setOptCode(SampleBalOptCodeEnum.DZC.getCode());
        balDo.setECode(sampleBalDOById.getECode());
        balDo.setDeptNo(sampleBalApplyDO.getApplyDeptNo());
        balDo.setExpDate(sampleBalDOById.getExpDate());
        balDo.setModelinchn(sampleBalDOById.getModelinchn());
        balDo.setInDate(nowDate);
        balDo.setOrdType(sampleBalDOById.getOrdType());
        balDo.setUserName("");
        balDo.setOptTime(nowDate);
        balDo.setApplyCode(sampleBalApplyDO.getApplyNo());
        balDo.setPriceApply(sampleBalApplyDO.getApplyBalPrice());
        balDo.setCreatetime(nowDate);
        balDo.setStockCode(sampleBalDOById.getStockCode());
        balDo.setRcvDeptNo(sampleBalDOById.getRcvDeptNo());
        SamplebalDO samplebalDO = new SamplebalDO();
        samplebalDO.setAppType(balDo.getAppType());
        samplebalDO.setBalType(balDo.getBalType());
        // 生成发票号
        String invoiceNo2 = sampleOrderDetailService.getInvoiceNoByCostType(samplebalDO);
        if (StringUtils.isBlank(invoiceNo2)) {
            return ResultVo.failure("生成冲负数据的发票号为空.重新结转失败.");
        }
        balDo.setInvoiceNo(invoiceNo2);
        samplebalMapper.insert(balDo);
        return ResultVo.success("操作成功");
    }

    public SamplebalDO getSampleBalDOById(Long sourceId) {
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SamplebalDO::getId,sourceId)
                .ne(SamplebalDO::getOptCode,SampleBalOptCodeEnum.CANCEL.getCode());
        return samplebalMapper.selectOne(queryWrapper);
    }

    public Boolean upSampleBalApplyInfo(Boolean issucess,SampleBalApplyDO sampleBalApplyDO,String optUser ) {
        LambdaUpdateWrapper<SampleBalApplyDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SampleBalApplyDO::getId,sampleBalApplyDO.getId());
        if (issucess) {
            updateWrapper.set(SampleBalApplyDO::getHandStatus,SampleBalApplyHandStatusEnum.alreadyBal.getCode());
        } else {
            updateWrapper.set(SampleBalApplyDO::getHandStatus,SampleBalApplyHandStatusEnum.balerror.getCode());
        }
        updateWrapper.set(SampleBalApplyDO::getUpdateTime,new Date());
        updateWrapper.set(SampleBalApplyDO::getUpdateUser,optUser);
        try {
            sampleBalApplyMapper.update(null,updateWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean upOpsSalesNoticeTaskInfo(SampleBalApplyDO sampleBalApplyDO, String optUser, Boolean issucess, String handRemark) {

        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();

        OpsSalesCommonParamVO opsSalesCommonParamVO = new OpsSalesCommonParamVO();

        dealReturnOpsParamVO.setApplyType(6);

        SampleBalApplyCallBackVO callBackVO = new SampleBalApplyCallBackVO();

        callBackVO.setSampleBalApplyNo(sampleBalApplyDO.getSampleBalApplyNo());
        callBackVO.setOrderNo(sampleBalApplyDO.getOrderNo());
        callBackVO.setModelNo(sampleBalApplyDO.getModelNo());
        callBackVO.setApplyBalQty(sampleBalApplyDO.getApplyBalQty());

        if (issucess) {
            callBackVO.setHandStatus(SampleBalApplyHandStatusEnum.alreadyBal.getCode());
            callBackVO.setHandStatusName(SampleBalApplyHandStatusEnum.alreadyBal.getCodeName());
        } else {
            callBackVO.setHandStatus(SampleBalApplyHandStatusEnum.balerror.getCode());
            callBackVO.setHandStatusName(SampleBalApplyHandStatusEnum.balerror.getCodeName());
            callBackVO.setHandRemark(handRemark);
        }


        callBackVO.setBalType(sampleBalApplyDO.getApplyBalType());

        callBackVO.setSignQty(sampleBalApplyDO.getSignQty());
        callBackVO.setSignTime(sampleBalApplyDO.getSignTime());

        opsSalesCommonParamVO.setData(callBackVO);

        // 回调门户
        UpTaskInfoVO vo = new UpTaskInfoVO();
        vo.setBatchNo(sampleBalApplyDO.getBatchNo());
        vo.setOptUserNo(optUser);
        vo.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());

        DealReturnOpsParam param = new DealReturnOpsParam();
        param.setOpsSalesCommonParamVo(opsSalesCommonParamVO);

        dealReturnOpsParamVO.setDealReturnOpsParam(param);

        OpsSalesCommonParamVO callBackParam = new OpsSalesCommonParamVO();
        callBackParam.setData(dealReturnOpsParamVO);

        vo.setCallBackParameter(JSONUtil.toJsonStr(callBackParam));

        opsSalesNoticeTaskFeignApi.upOpsSalesNoticeTaskInfo(vo);

        return true;
    }

    public Boolean updateSampleBalOptCodeById(SampleBalApplyDO sampleBalApplyDO,String optUser,String invoiceNo) {
        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(SamplebalDO::getId,sampleBalApplyDO.getSampleBalId())
                .set(SamplebalDO::getOptCode,SampleBalOptCodeEnum.DZC.getCode())
                .set(SamplebalDO::getOptDate,new Date())
                .set(SamplebalDO::getOptTime, new Date())
                .set(SamplebalDO::getInDate, new Date())
                .set(SamplebalDO::getInvoiceNo, invoiceNo)
                .set(SamplebalDO::getUserName,optUser)
                .set(SamplebalDO::getQuantity,sampleBalApplyDO.getApplyBalQty())
                .set(SamplebalDO::getBalType,sampleBalApplyDO.getApplyBalType());
        int update = samplebalMapper.update(null, updateWrapper);
        if (update == 1) {
            return true;
        }
        return false;
    }



    // 校验数量是否满足
    public ResultVo<String> checkRcvQty(CheckRcvQtyVO checkRcvQtyVO) {
        // 查询结转表 已经结转的数量
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SamplebalDO::getRorderNo,checkRcvQtyVO.getOrderFno());
        queryWrapper.ne(SamplebalDO::getOptCode,SampleBalOptCodeEnum.CANCEL.getCode());
        List<SamplebalDO> sampleBalDOS = samplebalMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(sampleBalDOS)) {
            return ResultVo.failure("数量校验不通过,结转表可申请数量为0");
        }
        int sum = sampleBalDOS.stream()
                .mapToInt(SamplebalDO::getQuantity)
                .sum();

        if (checkRcvQtyVO.getApplyBalQty() > sum) {
            return ResultVo.failure("数量校验不通过,结转数量"+sum+"申请了"+checkRcvQtyVO.getApplyBalQty());
        }
        LambdaQueryWrapper<SamplebalDO> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(SamplebalDO::getId,checkRcvQtyVO.getSampleBalId());
        SamplebalDO samplebalDO = samplebalMapper.selectOne(queryWrapper1);
        if (samplebalDO == null) {
            return ResultVo.failure("数据校验不通过,结转表id不存在");
        }
        List<String> status = new ArrayList<>();
        status.add(SampleBalOptCodeEnum.YWJ.getCode());
        status.add(SampleBalOptCodeEnum.ZXS.getCode());
        status.add(SampleBalOptCodeEnum.NHJZ.getCode());
        status.add(SampleBalOptCodeEnum.YSZ.getCode());
        status.add(SampleBalOptCodeEnum.YZCPD.getCode());

        if (status.contains(samplebalDO.getOptCode())) {
            if(!checkRcvQtyVO.isBalFlag()) {
                return ResultVo.failure("数据校验不通过,"+checkRcvQtyVO.getSampleBalId()+"结转id是已结转状态");
            }
        } else {
           if(checkRcvQtyVO.isBalFlag()) {
               return ResultVo.failure("数据校验不通过,"+checkRcvQtyVO.getSampleBalId()+"结转id不是已结转状态");
           }
        }
        return ResultVo.success("校验通过");
    }

    @Override
    public ResultVo<String> insertReturnOrder(SmsSendOpsDetailTaskBean bean) {
        if (bean == null) {
            return ResultVo.failure("入参不可为空");
        }
        ReturnOrderApplyDTO dto = new ReturnOrderApplyDTO();
        dto.setApplyNo(bean.getApplyNo()); // 申请号
        String str = bean.getApplyNo();
        String str1 = str.substring(0, str.indexOf("-"));
        String str2 = str.substring(str1.length()+1, str.length());
        dto.setItemNo(Integer.valueOf(str2)); // 项号
        dto.setOrderNo(bean.getOrderNo()); // 订单号
        dto.setApplyQty(bean.getReturnQuantity()); // 申请数量
        dto.setModelNo(bean.getModelNo()); // 型号
        dto.setOrderQty(bean.getOrderQuantity()); // 订单数量
        dto.setApplicant(bean.getApplyPersonNo()); // 申请担当
        dto.setReason(bean.getReason()); // 寄回目的
        dto.setCustomerNo(bean.getCustomerNo());
        dto.setUserNo(bean.getUserNo());
        dto.setToUserStock(0);
        dto.setFeeRate(BigDecimal.ZERO);
        dto.setWarehouseCode(bean.getReturnLogisticsCenter());
        dto.setDeptNo(bean.getApplyDeptNo());
        // dto.setRemark();
        ResultVo<String> resultVo = returnOrderService.addReturnOrder(JSONUtil.toJsonStr(dto), null);
        // 保存附件
        if (CollectionUtils.isNotEmpty(bean.getAttachmentList())) {
            try {
                for (FileUpload item : bean.getAttachmentList()) {
                    // 门户文件路径
                    String url = salesFileUploadPath+PublicUtil.getStringByIndexOf(item.getFilePath(),File.separator,3)+item.getRandomFileName();
                    // 转换ops文件保存路径
                    String newFilePath = serverPath + File.separator + DateUtil.getYearMonthCode(new Date()) + File.separator + bean.getApplyNo() + item.getRandomFileName();
                    FileUtil.copyFile(url,newFilePath);
                }
            } catch (Exception e) {
                log.error("保存样品结转申请良品返回附件异常: {}",e.getMessage(),e);
                return ResultVo.failure("保存样品结转申请良品返回附件异常"+e.getMessage());
            }
        }
        return resultVo;
    }

    @Override
    public ResultVo<String> insertSampleBalApply(SampleBalApplyVO info) {
        if (info == null) {
            return ResultVo.failure("入参不可为空.");
        }
        try {
            sampleBalApplyMapper.insert(BeanCopyUtil.copy(info, SampleBalApplyDO.class));
        } catch (Exception e) {
            log.error("写入结转申请表异常:{}",e.getMessage(),e);
            throw new BusinessException("写入结转申请表异常:"+e.getMessage());
        }
        return ResultVo.success("写入成功");
    }

    @Override
    public ResultVo<String> findHandSampleBalApply(FindHandSampleBalHandVO vo) {

        LambdaQueryWrapper<SampleBalApplyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SampleBalApplyDO::getSampleBalApplyNo,vo.getApplyNo());
        List<SampleBalApplyDO> sampleBalApplyDOS1 = sampleBalApplyMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(sampleBalApplyDOS1)) {
            return ResultVo.failure(vo.getApplyNo()+"申请已存在");
        }
        LambdaQueryWrapper<SampleBalApplyDO> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2
                .eq(SampleBalApplyDO::getOrderNo,vo.getOrderFno())
                .ne(SampleBalApplyDO::getSampleBalId,vo.getSampleBalId())
                .ne(SampleBalApplyDO::getHandStatus, SampleBalApplyHandStatusEnum.alreadyBal.getCode());
        List<SampleBalApplyDO> sampleBalApplyDOS = sampleBalApplyMapper.selectList(queryWrapper2);
        if (CollectionUtils.isEmpty(sampleBalApplyDOS)){
            return ResultVo.success("可以申请");
        }
        return ResultVo.failure(vo.getOrderFno()+"存在受理中的申请"+sampleBalApplyDOS.get(0).getSampleBalApplyNo());
    }

    @Override
    public ResultVo<String> updateErrorSampleBalData() {
        // 查出所有负数清单
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .lt(SamplebalDO::getQuantity,0)
                .ne(SamplebalDO::getOptCode,9)
                .orderByAsc(SamplebalDO::getRorderNo);
        List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(samplebalDOList)) {
            return ResultVo.failure("暂无负数的结转清单数据");
        }

        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();

        for (SamplebalDO item : samplebalDOList) {
            if (StringUtils.isBlank(item.getRorderNo())){
                continue;
            }
            queryWrapper.clear();
            int balQty = Math.abs(item.getQuantity());
            queryWrapper
                    .eq(SamplebalDO::getQuantity,balQty)
                    .eq(SamplebalDO::getRorderNo,item.getRorderNo())
                    .eq(SamplebalDO::getBalType,item.getBalType());
            List<SamplebalDO> list = samplebalMapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(list)) {
                queryWrapper.clear();
                balQty = Math.abs(item.getQuantity());
                queryWrapper
                        .eq(SamplebalDO::getQuantity,balQty)
                        .eq(SamplebalDO::getRorderNo,item.getRorderNo());
                list = samplebalMapper.selectList(queryWrapper);
            }
            if (CollectionUtils.isEmpty(list)) {
                log.error("未找到对应的正数数据 {}",JSONUtil.toJsonPrettyStr(item));
                continue;
            }
            try {

                SamplebalDO samplebalDO = list.get(0);

                // 修正小于0的重新结转数据source = 正数id+"-"+当前id
                updateWrapper.clear();
                updateWrapper
                        .set(SamplebalDO::getSource,samplebalDO.getId()+"-"+item.getId())
                        .eq(SamplebalDO::getId,item.getId());
                samplebalMapper.update(null,updateWrapper);

                // 修正重新结转数据正数那条的 source = 负数的id
                updateWrapper.clear();
                updateWrapper
                        .set(SamplebalDO::getSource,String.valueOf(samplebalDO.getId()))
                        .eq(SamplebalDO::getId,samplebalDO.getId());
                samplebalMapper.update(null,updateWrapper);
            } catch (Exception e) {
                log.error("修正重新结转数据 source发生异常 {}",e.getMessage(),e);
            }
        }
        return ResultVo.success("数据修正完毕");
    }

//    @Override
//    public ResultVo<String> upSampleBalPropertyAssign(List<Long> sampleBalIds) {
//
//        // 获取优先分配的资产方
//        ResultVo<DataTypeVO> dataTypeCodesInfoWithDS = dictCommonService.getDataTypeCodesInfoWithDS("9002", "26");
//        if (!dataTypeCodesInfoWithDS.isSuccess()) {
//            return ResultVo.failure("未能获取优先分配的资产方");
//        }
//        List<String> listPropertyAssign = Arrays.asList(dataTypeCodesInfoWithDS.getData().getExtNote1().split(";"));
//
//        // 各资产分配情况
//        Map<String,SampleBalPropertyAssignDto> mapPropertyAssign = new HashMap<>();
//
//        // 迭代版本
//        int version = 1;
//        for (Long sampleBalId : sampleBalIds) {
//
//            LambdaQueryWrapper<SamplebalDO> sa = new LambdaQueryWrapper<>();
//            sa.eq(SamplebalDO::getId,sampleBalId);
//            SamplebalDO samplebalDO = samplebalMapper.selectOne(sa);
//            // 需要分配的结转数量
//            int remainQty = samplebalDO.getQuantity();
//
//            // 获取资产方分配清单
//            LambdaQueryWrapper<SampleBalPropertyAssignDO> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper
//                    .eq(SampleBalPropertyAssignDO::getSampleBalId,sampleBalId)
//                    .eq(SampleBalPropertyAssignDO::getDelFlag,0)
//                    .orderByAsc(SampleBalPropertyAssignDO::getCompanyId);
//            List<SampleBalPropertyAssignDO> sampleBalPropertyAssignDOS = sampleBalPropertyAssignMapper.selectList(queryWrapper);
//
//            /**
//             * 如果资产方分配清单为空 代表是sample_bal新增数据 需要看旧的资产是否分配完
//             */
//            if (CollectionUtils.isEmpty(sampleBalPropertyAssignDOS)) {
//                for (String c : listPropertyAssign) {
//                    SampleBalPropertyAssignDto sampleBalPropertyAssignDto = mapPropertyAssign.get(c);
//
//                    // 资产方有剩余
//                    if (sampleBalPropertyAssignDto.getRemailFlag()) {
//                        if(sampleBalPropertyAssignDto.getRemailQty() > 0) {
//                            SampleBalPropertyAssignDO item = new SampleBalPropertyAssignDO();
//                            item.setSampleBalId(sampleBalId);
//                            item.setVersion(version);
//                            item.setCompanyId(c);
//                            item.setQuantity(sampleBalPropertyAssignDto.getRemailQty());
//                            item.setModelNo(samplebalDO.getModelNo());
//                            item.setDelFlag(0);
//                            item.setCreateUser("ops");
//                            item.setCreateTime(new Date());
//                            sampleBalPropertyAssignMapper.insert(item);
//
//                            remainQty -= item.getQuantity();
//
//                            // 剩余数量为0 剩余标识改为false
//                            sampleBalPropertyAssignDto.setRemailFlag(false);
//                            sampleBalPropertyAssignDto.setRemailQty(0);
//                            mapPropertyAssign.put(item.getCompanyId(),sampleBalPropertyAssignDto);
//                            continue;
//                        }
//
//                        if (remainQty >= sampleBalPropertyAssignDto.getQty()) {
//                            SampleBalPropertyAssignDO item = new SampleBalPropertyAssignDO();
//                            item.setSampleBalId(sampleBalId);
//                            item.setVersion(version);
//                            item.setCompanyId(c);
//                            item.setQuantity(remainQty);
//                            item.setModelNo(samplebalDO.getModelNo());
//                            item.setDelFlag(0);
//                            item.setCreateUser("ops");
//                            item.setCreateTime(new Date());
//                            sampleBalPropertyAssignMapper.insert(item);
//                            return ResultVo.success("资产分配完成");
//                        }
//                    }
//                }
//                if (remainQty <= 0) {
//                    continue;
//                }
//            }
//
//            // 历史分配清单置为无效
//            LambdaUpdateWrapper<SampleBalPropertyAssignDO> updateWrapper = new LambdaUpdateWrapper<>();
//            updateWrapper.eq(SampleBalPropertyAssignDO::getSampleBalId,sampleBalId).eq(SampleBalPropertyAssignDO::getDelFlag,0)
//                    .set(SampleBalPropertyAssignDO::getDelFlag,1).set(SampleBalPropertyAssignDO::getUpdateTime,new Date())
//                    .set(SampleBalPropertyAssignDO::getUpdateUser,"ops");
//            sampleBalPropertyAssignMapper.update(null,updateWrapper);
//
//            // 汇总各资产方能分配的最大数量
//            for (SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
//                if (mapPropertyAssign.containsKey(item.getCompanyId())) {
//                    SampleBalPropertyAssignDto dto = new SampleBalPropertyAssignDto();
//                    dto.setQty(item.getQuantity()+mapPropertyAssign.get(item.getCompanyId()).getQty());
//                    dto.setCompanyId(item.getCompanyId());
//                    dto.setRemailFlag(true);
//                    dto.setRemailQty(dto.getQty());
//                    mapPropertyAssign.put(item.getCompanyId(),dto);
//                } else {
//                    SampleBalPropertyAssignDto dto = new SampleBalPropertyAssignDto();
//                    dto.setQty(item.getQuantity());
//                    dto.setCompanyId(item.getCompanyId());
//                    dto.setRemailFlag(true);
//                    dto.setRemailQty(dto.getQty());
//                    mapPropertyAssign.put(item.getCompanyId(),dto);
//                }
//            }
//
//            for (int i = 0; i < sampleBalPropertyAssignDOS.size(); i++) {
//                if (remainQty <= 0) {
//                    break;
//                }
//                SampleBalPropertyAssignDO item = sampleBalPropertyAssignDOS.get(i);
//                version = Optional.ofNullable(item.getVersion()).orElse(1)+1;
//                if (remainQty > item.getQuantity()) {
//                    item.setVersion(version);
//                    item.setId(null);
//                    item.setDelFlag(0);
//                    item.setCreateUser("ops");
//                    item.setCreateTime(new Date());
//                    sampleBalPropertyAssignMapper.insert(item);
//                    remainQty -= item.getQuantity();
//                    SampleBalPropertyAssignDto sampleBalPropertyAssignDto = mapPropertyAssign.get(item.getCompanyId());
//                    sampleBalPropertyAssignDto.setRemailFlag(false);
//                    sampleBalPropertyAssignDto.setRemailQty(0);
//                    mapPropertyAssign.put(item.getCompanyId(),sampleBalPropertyAssignDto);
//
//                    item.setId(null);
//                    item.setQuantity(remainQty);
//                    item.setCompanyId(sampleBalPropertyAssignDOS.get(i+1).getCompanyId());
//                    sampleBalPropertyAssignMapper.insert(item);
//                    remainQty -= item.getQuantity();
//
//                    SampleBalPropertyAssignDto dto2 = mapPropertyAssign.get(item.getCompanyId());
//                    dto2.setRemailFlag(true);
//                    dto2.setRemailQty(dto2.getQty() - item.getQuantity());
//                    mapPropertyAssign.put(item.getCompanyId(),dto2);
//                } else {
//                    item.setVersion(version);
//                    item.setId(null);
//                    item.setDelFlag(0);
//                    item.setQuantity(remainQty);
//                    item.setCreateUser("ops");
//                    item.setCreateTime(new Date());
//                    sampleBalPropertyAssignMapper.insert(item);
//
//                    SampleBalPropertyAssignDto sampleBalPropertyAssignDto = mapPropertyAssign.get(item.getCompanyId());
//                    sampleBalPropertyAssignDto.setRemailFlag(true);
//                    sampleBalPropertyAssignDto.setRemailQty(item.getQuantity() - remainQty);
//                    mapPropertyAssign.put(item.getCompanyId(),sampleBalPropertyAssignDto);
//                    break;
//                }
//            }
//        }
//        return ResultVo.success("资产分配完成");
//    }

    @Override
    public ResultVo<String> upSampleBalPropertyAssign2(List<Long> sampleBalIds) {

        // id倒叙排序
        sampleBalIds = sampleBalIds.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        // 获取优先分配的资产方
        ResultVo<DataTypeVO> dataTypeCodesInfoWithDS = dictCommonService.getDataTypeCodesInfoWithDS("9002", "26");
        if (!dataTypeCodesInfoWithDS.isSuccess()) {
            return ResultVo.failure("未能获取优先分配的资产方");
        }
        List<String> listPropertyAssign = Arrays.asList(dataTypeCodesInfoWithDS.getData().getExtNote1().split(";"));

        // 各资产分配情况
        Map<String,SampleBalPropertyAssignDto> mapPropertyAssign = new HashMap<>();

        // 获取资产方分配清单
        LambdaQueryWrapper<SampleBalPropertyAssignDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(SampleBalPropertyAssignDO::getSampleBalId,sampleBalIds)
                .eq(SampleBalPropertyAssignDO::getDelFlag,0)
                .orderByAsc(SampleBalPropertyAssignDO::getCompanyId);
        List<SampleBalPropertyAssignDO> sampleBalPropertyAssignDOS = sampleBalPropertyAssignMapper.selectList(queryWrapper);

        // 历史分配清单置为无效
        LambdaUpdateWrapper<SampleBalPropertyAssignDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(SampleBalPropertyAssignDO::getSampleBalId,sampleBalIds).eq(SampleBalPropertyAssignDO::getDelFlag,0)
                .set(SampleBalPropertyAssignDO::getDelFlag,1).set(SampleBalPropertyAssignDO::getUpdateTime,new Date())
                .set(SampleBalPropertyAssignDO::getUpdateUser,"ops");
        sampleBalPropertyAssignMapper.update(null,updateWrapper);

        // 汇总各资产方能分配的最大数量
        for (SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
            if (mapPropertyAssign.containsKey(item.getCompanyId())) {
                SampleBalPropertyAssignDto dto = new SampleBalPropertyAssignDto();
                dto.setQty(item.getQuantity()+mapPropertyAssign.get(item.getCompanyId()).getQty());
                dto.setCompanyId(item.getCompanyId());
                dto.setRemailFlag(true);
                dto.setVersion(item.getVersion()==null?1:item.getVersion());
                dto.setRemailQty(dto.getQty());
                dto.setProportion(item.getProportion());
                mapPropertyAssign.put(item.getCompanyId(),dto);
            } else {
                SampleBalPropertyAssignDto dto = new SampleBalPropertyAssignDto();
                dto.setQty(item.getQuantity());
                dto.setCompanyId(item.getCompanyId());
                dto.setRemailFlag(true);
                dto.setVersion(item.getVersion()==null?1:item.getVersion());
                dto.setRemailQty(dto.getQty());
                dto.setProportion(item.getProportion());
                mapPropertyAssign.put(item.getCompanyId(),dto);
            }
        }
        // 构建资产分配实体信息
        for (Long sampleBalId : sampleBalIds) {

            LambdaQueryWrapper<SamplebalDO> sa = new LambdaQueryWrapper<>();
            sa.eq(SamplebalDO::getId,sampleBalId);
            SamplebalDO samplebalDO = samplebalMapper.selectOne(sa);
            int remainQry = samplebalDO.getQuantity();
            for (String s : listPropertyAssign) {
                if (remainQry <= 0) {
                    break;
                }
                SampleBalPropertyAssignDto sampleBalPropertyAssignDto = mapPropertyAssign.get(s);
                if (sampleBalPropertyAssignDto == null) {
                    continue;
                }
                if (sampleBalPropertyAssignDto.getRemailFlag() && sampleBalPropertyAssignDto.getRemailQty() > 0) {
                    if (remainQry >= sampleBalPropertyAssignDto.getRemailQty() ) {
                        SampleBalPropertyAssignDO item = new SampleBalPropertyAssignDO();
                        item.setSampleBalId(sampleBalId);
                        item.setModelNo(samplebalDO.getModelNo());
                        item.setQuantity(sampleBalPropertyAssignDto.getRemailQty());
                        item.setCompanyId(sampleBalPropertyAssignDto.getCompanyId());
                        item.setVersion(sampleBalPropertyAssignDto.getVersion()+1);
                        item.setDelFlag(0);
                        item.setCreateTime(new Date());
                        item.setCreateUser("ops");
                        item.setProportion(sampleBalPropertyAssignDto.getProportion());
                        sampleBalPropertyAssignMapper.insert(item);
                        remainQry -= sampleBalPropertyAssignDto.getRemailQty();
                        sampleBalPropertyAssignDto.setRemailFlag(false);
                        sampleBalPropertyAssignDto.setRemailQty(0);
                        mapPropertyAssign.put(sampleBalPropertyAssignDto.getCompanyId(),sampleBalPropertyAssignDto);
                    } else {
                        SampleBalPropertyAssignDO item = new SampleBalPropertyAssignDO();
                        item.setSampleBalId(sampleBalId);
                        item.setModelNo(samplebalDO.getModelNo());
                        item.setQuantity(remainQry);
                        item.setCompanyId(sampleBalPropertyAssignDto.getCompanyId());
                        item.setVersion(sampleBalPropertyAssignDto.getVersion()+1);
                        item.setDelFlag(0);
                        item.setCreateTime(new Date());
                        item.setCreateUser("ops");
                        item.setProportion(sampleBalPropertyAssignDto.getProportion());
                        sampleBalPropertyAssignMapper.insert(item);
                        sampleBalPropertyAssignDto.setRemailFlag(true);
                        sampleBalPropertyAssignDto.setRemailQty(sampleBalPropertyAssignDto.getRemailQty() - remainQry);
                        mapPropertyAssign.put(sampleBalPropertyAssignDto.getCompanyId(),sampleBalPropertyAssignDto);
                        remainQry = 0;
                    }
                }
            }
        }
        return ResultVo.success("资产分配完成");
    }

    @Override
    public ResultVo<String> upSampleBalPropertyAssignWithSplitModelNo(List<Long> sampleBalIds) {
        // id倒叙排序
        sampleBalIds = sampleBalIds.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        // 获取优先分配的资产方
        ResultVo<DataTypeVO> dataTypeCodesInfoWithDS = dictCommonService.getDataTypeCodesInfoWithDS("9002", "26");
        if (!dataTypeCodesInfoWithDS.isSuccess()) {
            return ResultVo.failure("未能获取优先分配的资产方");
        }
        List<String> listPropertyAssign = Arrays.asList(dataTypeCodesInfoWithDS.getData().getExtNote1().split(";"));

        // 各资产分配情况
        Map<String,SampleBalPropertyAssignDto> mapPropertyAssign = new HashMap<>();

        // 获取资产方分配清单
        LambdaQueryWrapper<SampleBalPropertyAssignDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(SampleBalPropertyAssignDO::getSampleBalId,sampleBalIds)
                .eq(SampleBalPropertyAssignDO::getDelFlag,0)
                .orderByAsc(SampleBalPropertyAssignDO::getModelNo,SampleBalPropertyAssignDO::getCompanyId);
        List<SampleBalPropertyAssignDO> sampleBalPropertyAssignDOS = sampleBalPropertyAssignMapper.selectList(queryWrapper);

        // 历史分配清单置为无效
        LambdaUpdateWrapper<SampleBalPropertyAssignDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(SampleBalPropertyAssignDO::getSampleBalId,sampleBalIds).eq(SampleBalPropertyAssignDO::getDelFlag,0)
                .set(SampleBalPropertyAssignDO::getDelFlag,1).set(SampleBalPropertyAssignDO::getUpdateTime,new Date())
                .set(SampleBalPropertyAssignDO::getUpdateUser,"ops");
        sampleBalPropertyAssignMapper.update(null,updateWrapper);

        LambdaQueryWrapper<SamplebalDO> sa = new LambdaQueryWrapper<>();
        sa.in(SamplebalDO::getId,sampleBalIds).orderByDesc(SamplebalDO::getId);
        List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(sa);


        // 计算拆分型号可以分配数量
//        Map<String,Integer> calbiliMap = new HashMap<>();
//        for(SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
//            if (calbiliMap.containsKey(item.getModelNo())) {
//                calbiliMap.put(item.getModelNo(), item.getQuantity()+calbiliMap.getOrDefault(item.getModelNo(),0));
//            } else {
//                calbiliMap.put(item.getModelNo(), item.getQuantity());
//            }
//        }

        // 汇总各资产方能分配的最大数量
        for (SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
            if (mapPropertyAssign.containsKey(item.getCompanyId()+item.getModelNo())) {
                SampleBalPropertyAssignDto dto = new SampleBalPropertyAssignDto();
                dto.setQty(item.getQuantity()+mapPropertyAssign.get(item.getCompanyId()).getQty());
                dto.setCompanyId(item.getCompanyId());
                dto.setRemailFlag(true);
                dto.setVersion(item.getVersion()==null?1:item.getVersion());
                dto.setRemailQty(dto.getQty());
                dto.setProportion(item.getProportion());
                mapPropertyAssign.put(item.getCompanyId()+item.getModelNo(),dto);
            } else {
                SampleBalPropertyAssignDto dto = new SampleBalPropertyAssignDto();
                dto.setQty(item.getQuantity());
                dto.setCompanyId(item.getCompanyId());
                dto.setRemailFlag(true);
                dto.setVersion(item.getVersion()==null?1:item.getVersion());
                dto.setRemailQty(dto.getQty());
                dto.setProportion(item.getProportion());
                mapPropertyAssign.put(item.getCompanyId()+item.getModelNo(),dto);
            }
        }

        // 对拆分型号去重
        List<SampleBalPropertyAssignDO> uniqueassignDO = new ArrayList<>(sampleBalPropertyAssignDOS.stream()
                .collect(Collectors.toMap(
                        SampleBalPropertyAssignDO::getModelNo,
                        Function.identity(),
                        (existing, replacement) -> existing
                ))
                .values());

        for (SamplebalDO item : samplebalDOList) {
             for (SampleBalPropertyAssignDO assignDO: uniqueassignDO) {
                 int remainQry = item.getQuantity();
                 for (String s : listPropertyAssign) {
                     if (remainQry <= 0) {
                         break;
                     }
                     SampleBalPropertyAssignDto sampleBalPropertyAssignDto = mapPropertyAssign.get(s + assignDO.getModelNo());
                     if (sampleBalPropertyAssignDto == null) {
                         continue;
                     }
                     if (sampleBalPropertyAssignDto.getRemailFlag() && sampleBalPropertyAssignDto.getRemailQty() > 0) {
                         remainQry = remainQry*sampleBalPropertyAssignDto.getProportion();
                         if (remainQry >= sampleBalPropertyAssignDto.getRemailQty()) {
                             assignDO.setId(null);
                             assignDO.setSampleBalId(item.getId());
                             assignDO.setVersion(sampleBalPropertyAssignDto.getVersion()+1);
                             assignDO.setCreateTime(new Date());
                             assignDO.setCreateUser("ops");
                             assignDO.setQuantity(sampleBalPropertyAssignDto.getRemailQty());
                             assignDO.setProportion(sampleBalPropertyAssignDto.getProportion());
                             sampleBalPropertyAssignMapper.insert(assignDO);
                             remainQry -= sampleBalPropertyAssignDto.getRemailQty();
                             sampleBalPropertyAssignDto.setRemailQty(0);
                             sampleBalPropertyAssignDto.setRemailFlag(false);
                             mapPropertyAssign.put(sampleBalPropertyAssignDto.getCompanyId(),sampleBalPropertyAssignDto);
                         } else {
                             assignDO.setId(null);
                             assignDO.setSampleBalId(item.getId());
                             assignDO.setVersion(sampleBalPropertyAssignDto.getVersion()+1);
                             assignDO.setCreateTime(new Date());
                             assignDO.setCreateUser("ops");
                             assignDO.setQuantity(remainQry);
                             assignDO.setProportion(sampleBalPropertyAssignDto.getProportion());
                             sampleBalPropertyAssignMapper.insert(assignDO);
                             sampleBalPropertyAssignDto.setRemailQty(sampleBalPropertyAssignDto.getRemailQty() - remainQry);
                             sampleBalPropertyAssignDto.setRemailFlag(true);
                             mapPropertyAssign.put(sampleBalPropertyAssignDto.getCompanyId(),sampleBalPropertyAssignDto);
                             remainQry = 0;
                         }
                     }
                 }
             }
        }

        return ResultVo.success("资产分配完成");
    }

    @Override
    public ResultVo<String> upSampleBalPropertyAssignWithReturn(Long sampleId) {


        LambdaQueryWrapper<SamplebalDO> qe = new LambdaQueryWrapper<>();
        qe.eq(SamplebalDO::getId,sampleId);
        SamplebalDO samplebalDO = samplebalMapper.selectOne(qe);

        if (DateUtil.getYear(samplebalDO.getCreatetime()) < 2025) {
            OrderLogVO orderLogVO = new OrderLogVO();
            orderLogVO.setDescription(sampleId + "创建时间为" + DateUtil.dateToDateString(samplebalDO.getCreatetime()) + ";2025年之前的不进行资产分配");
            orderLogVO.setOrderNo(sampleId.toString());
            orderLogFeignApi.addOrderLog(orderLogVO);
            return ResultVo.failure("2025年之前的不进行资产分配");
        }

        // 获取优先分配的资产方
        ResultVo<DataTypeVO> dataTypeCodesInfoWithDS = dictCommonService.getDataTypeCodesInfoWithDS("9002", "26");
        if (!dataTypeCodesInfoWithDS.isSuccess()) {
            return ResultVo.failure("未能获取优先分配的资产方");
        }
        List<String> listPropertyAssign = Arrays.asList(dataTypeCodesInfoWithDS.getData().getExtNote1().split(";"));


        // 根据单号获取所有结转数据
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();

        List<String> statusList = new ArrayList<>();
        statusList.add(SampleBalOptCodeEnum.CANCEL.getCode());
        statusList.add(SampleBalOptCodeEnum.YSZ.getCode());

        queryWrapper.eq(SamplebalDO::getRorderNo,samplebalDO.getRorderNo()).notIn(SamplebalDO::getOptCode,statusList);
        List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(queryWrapper);

        List<Long> sampleBalIds = samplebalDOList.stream().map(SamplebalDO::getId).collect(Collectors.toList());

        // 获取资产方分配清单
        LambdaQueryWrapper<SampleBalPropertyAssignDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .in(SampleBalPropertyAssignDO::getSampleBalId,sampleBalIds)
                .eq(SampleBalPropertyAssignDO::getDelFlag,0)
                .orderByAsc(SampleBalPropertyAssignDO::getCompanyId);
        List<SampleBalPropertyAssignDO> sampleBalPropertyAssignDOS = sampleBalPropertyAssignMapper.selectList(lambdaQueryWrapper);

        // 各资产分配情况
        Map<String,SampleBalPropertyAssignDto> mapPropertyAssign = new HashMap<>();

        // 汇总各资产方能分配的最大数量
        for (SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
            if (mapPropertyAssign.containsKey(item.getCompanyId())) {
                SampleBalPropertyAssignDto dto = new SampleBalPropertyAssignDto();
                dto.setQty(item.getQuantity()+mapPropertyAssign.get(item.getCompanyId()).getQty());
                dto.setCompanyId(item.getCompanyId());
                dto.setRemailFlag(true);
                dto.setIdstr(dto.getIdstr()+";"+item.getId());
                dto.setVersion(item.getVersion()==null?1:item.getVersion());
                dto.setRemailQty(dto.getQty());
                dto.setProportion(item.getProportion());
                mapPropertyAssign.put(item.getCompanyId(),dto);
            } else {
                SampleBalPropertyAssignDto dto = new SampleBalPropertyAssignDto();
                dto.setQty(item.getQuantity());
                dto.setCompanyId(item.getCompanyId());
                dto.setIdstr(String.valueOf(item.getId()));
                dto.setRemailFlag(true);
                dto.setVersion(item.getVersion()==null?1:item.getVersion());
                dto.setRemailQty(dto.getQty());
                dto.setProportion(item.getProportion());
                mapPropertyAssign.put(item.getCompanyId(),dto);
            }
        }

        int remainQry = samplebalDO.getQuantity();

        for (String s : listPropertyAssign) {
            if (remainQry == 0) {
                break;
            }
            SampleBalPropertyAssignDto sampleBalPropertyAssignDto = mapPropertyAssign.get(s);
            if (sampleBalPropertyAssignDto == null) {
                continue;
            }
            if (sampleBalPropertyAssignDto.getRemailFlag() && sampleBalPropertyAssignDto.getRemailQty() > 0) {
                if (Math.abs(remainQry) >= sampleBalPropertyAssignDto.getRemailQty() ) {
                    SampleBalPropertyAssignDO item = new SampleBalPropertyAssignDO();
                    item.setSampleBalId(sampleId);
                    item.setModelNo(samplebalDO.getModelNo());
                    item.setQuantity(sampleBalPropertyAssignDto.getRemailQty()*-1);
                    item.setCompanyId(sampleBalPropertyAssignDto.getCompanyId());
                    item.setVersion(sampleBalPropertyAssignDto.getVersion()+1);
                    item.setDelFlag(0);
                    item.setCreateTime(new Date());
                    item.setCreateUser("ops");
                    item.setProportion(sampleBalPropertyAssignDto.getProportion());
                    sampleBalPropertyAssignMapper.insert(item);
                    remainQry += sampleBalPropertyAssignDto.getRemailQty();
                    sampleBalPropertyAssignDto.setRemailFlag(false);
                    sampleBalPropertyAssignDto.setRemailQty(0);
                    mapPropertyAssign.put(sampleBalPropertyAssignDto.getCompanyId(),sampleBalPropertyAssignDto);
                } else {
                    SampleBalPropertyAssignDO item = new SampleBalPropertyAssignDO();
                    item.setSampleBalId(sampleId);
                    item.setModelNo(samplebalDO.getModelNo());
                    item.setQuantity(remainQry);
                    item.setCompanyId(sampleBalPropertyAssignDto.getCompanyId());
                    item.setVersion(sampleBalPropertyAssignDto.getVersion()+1);
                    item.setDelFlag(0);
                    item.setCreateTime(new Date());
                    item.setCreateUser("ops");
                    item.setProportion(sampleBalPropertyAssignDto.getProportion());
                    sampleBalPropertyAssignMapper.insert(item);
                    sampleBalPropertyAssignDto.setRemailFlag(true);
                    sampleBalPropertyAssignDto.setRemailQty(sampleBalPropertyAssignDto.getRemailQty() - remainQry);
                    mapPropertyAssign.put(sampleBalPropertyAssignDto.getCompanyId(),sampleBalPropertyAssignDto);
                    remainQry = 0;
                }
            }
        }

        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> upSampleBalPropertyAssignWithReturnSplitModelNo(Long sampleId) {

        LambdaQueryWrapper<SamplebalDO> qe = new LambdaQueryWrapper<>();
        qe.eq(SamplebalDO::getId,sampleId);
        SamplebalDO samplebalDO = samplebalMapper.selectOne(qe);

        // 获取优先分配的资产方
        ResultVo<DataTypeVO> dataTypeCodesInfoWithDS = dictCommonService.getDataTypeCodesInfoWithDS("9002", "26");
        if (!dataTypeCodesInfoWithDS.isSuccess()) {
            return ResultVo.failure("未能获取优先分配的资产方");
        }
        List<String> listPropertyAssign = Arrays.asList(dataTypeCodesInfoWithDS.getData().getExtNote1().split(";"));

        // 根据单号获取所有结转数据
        List<String> statusList = new ArrayList<>();
        statusList.add(SampleBalOptCodeEnum.CANCEL.getCode());
        statusList.add(SampleBalOptCodeEnum.YSZ.getCode());
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SamplebalDO::getRorderNo,samplebalDO.getRorderNo()).notIn(SamplebalDO::getOptCode,statusList);
        List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(queryWrapper);

        List<Long> sampleBalIds = samplebalDOList.stream().map(SamplebalDO::getId).collect(Collectors.toList());

        // 获取资产方分配清单
        LambdaQueryWrapper<SampleBalPropertyAssignDO> sa = new LambdaQueryWrapper<>();
        sa
                .in(SampleBalPropertyAssignDO::getSampleBalId,sampleBalIds)
                .eq(SampleBalPropertyAssignDO::getDelFlag,0)
                .orderByAsc(SampleBalPropertyAssignDO::getModelNo,SampleBalPropertyAssignDO::getCompanyId);
        List<SampleBalPropertyAssignDO> sampleBalPropertyAssignDOS = sampleBalPropertyAssignMapper.selectList(sa);

        // 各资产分配情况
        Map<String,SampleBalPropertyAssignDto> mapPropertyAssign = new HashMap<>();

        // 汇总各资产方能分配的最大数量
        for (SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
            if (mapPropertyAssign.containsKey(item.getCompanyId()+item.getModelNo())) {
                SampleBalPropertyAssignDto dto = new SampleBalPropertyAssignDto();
                dto.setQty(item.getQuantity()+mapPropertyAssign.get(item.getCompanyId()).getQty());
                dto.setCompanyId(item.getCompanyId());
                dto.setRemailFlag(true);
                dto.setVersion(item.getVersion()==null?1:item.getVersion());
                dto.setRemailQty(dto.getQty());
                dto.setProportion(item.getProportion());
                mapPropertyAssign.put(item.getCompanyId()+item.getModelNo(),dto);
            } else {
                SampleBalPropertyAssignDto dto = new SampleBalPropertyAssignDto();
                dto.setQty(item.getQuantity());
                dto.setCompanyId(item.getCompanyId());
                dto.setRemailFlag(true);
                dto.setVersion(item.getVersion()==null?1:item.getVersion());
                dto.setRemailQty(dto.getQty());
                dto.setProportion(item.getProportion());
                mapPropertyAssign.put(item.getCompanyId()+item.getModelNo(),dto);
            }
        }
        // 对拆分型号去重
        List<SampleBalPropertyAssignDO> uniqueassignDO = new ArrayList<>(sampleBalPropertyAssignDOS.stream()
                .collect(Collectors.toMap(
                        SampleBalPropertyAssignDO::getModelNo,
                        Function.identity(),
                        (existing, replacement) -> existing
                ))
                .values());


        for (SampleBalPropertyAssignDO assignDO: uniqueassignDO) {
            int remainQry = samplebalDO.getQuantity();
            for (String s : listPropertyAssign) {
                if (remainQry == 0) {
                    break;
                }
                SampleBalPropertyAssignDto sampleBalPropertyAssignDto = mapPropertyAssign.get(s + assignDO.getModelNo());
                if (sampleBalPropertyAssignDto == null) {
                    continue;
                }
                if (sampleBalPropertyAssignDto.getRemailFlag() && sampleBalPropertyAssignDto.getRemailQty() > 0) {
                    remainQry = remainQry*sampleBalPropertyAssignDto.getProportion();
                    if (Math.abs(remainQry) >= sampleBalPropertyAssignDto.getRemailQty()) {
                        assignDO.setId(null);
                        assignDO.setSampleBalId(samplebalDO.getId());
                        assignDO.setVersion(sampleBalPropertyAssignDto.getVersion()+1);
                        assignDO.setCreateTime(new Date());
                        assignDO.setCreateUser("ops");
                        assignDO.setQuantity(sampleBalPropertyAssignDto.getRemailQty()*-1);
                        assignDO.setProportion(sampleBalPropertyAssignDto.getProportion());
                        sampleBalPropertyAssignMapper.insert(assignDO);
                        remainQry += sampleBalPropertyAssignDto.getRemailQty();
                        sampleBalPropertyAssignDto.setRemailQty(0);
                        sampleBalPropertyAssignDto.setRemailFlag(false);
                        mapPropertyAssign.put(sampleBalPropertyAssignDto.getCompanyId(),sampleBalPropertyAssignDto);
                    } else {
                        assignDO.setId(null);
                        assignDO.setSampleBalId(samplebalDO.getId());
                        assignDO.setVersion(sampleBalPropertyAssignDto.getVersion()+1);
                        assignDO.setCreateTime(new Date());
                        assignDO.setCreateUser("ops");
                        assignDO.setQuantity(remainQry);
                        assignDO.setProportion(sampleBalPropertyAssignDto.getProportion());
                        sampleBalPropertyAssignMapper.insert(assignDO);
                        sampleBalPropertyAssignDto.setRemailQty(sampleBalPropertyAssignDto.getRemailQty() - remainQry);
                        sampleBalPropertyAssignDto.setRemailFlag(true);
                        mapPropertyAssign.put(sampleBalPropertyAssignDto.getCompanyId(),sampleBalPropertyAssignDto);
                        remainQry = 0;
                    }
                }
            }
        }
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> againBalUpPropertyAssign(List<Long> sampleBalIds) {
        LambdaQueryWrapper<SampleBalPropertyAssignDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SampleBalPropertyAssignDO::getSampleBalId,sampleBalIds).eq(SampleBalPropertyAssignDO::getDelFlag,0);
        List<SampleBalPropertyAssignDO> sampleBalPropertyAssignDOS = sampleBalPropertyAssignMapper.selectList(queryWrapper);
        int version = 1;
        for(SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
            LambdaUpdateWrapper<SampleBalPropertyAssignDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .eq(SampleBalPropertyAssignDO::getId,item.getId())
                    .set(SampleBalPropertyAssignDO::getDelFlag,1)
                    .set(SampleBalPropertyAssignDO::getUpdateTime,new Date());
            sampleBalPropertyAssignMapper.update(null,updateWrapper);

            item.setId(null);
            item.setCreateTime(new Date());
            item.setDelFlag(0);
            item.setCreateUser("ops");
            version = Optional.ofNullable(item.getVersion()).orElse(1)+1;
            item.setVersion(version);
            sampleBalPropertyAssignMapper.insert(item);
        }

        for (SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
            item.setId(null);
            item.setCreateTime(new Date());
            item.setDelFlag(0);
            item.setCreateUser("ops");
            item.setSampleBalId(sampleBalIds.get(1));
            item.setVersion(version);
            sampleBalPropertyAssignMapper.insert(item);
        }

        for (SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
            item.setId(null);
            item.setCreateTime(new Date());
            item.setDelFlag(0);
            item.setCreateUser("ops");
            item.setSampleBalId(sampleBalIds.get(2));
            item.setQuantity(item.getQuantity()*-1);
            item.setVersion(version);
            sampleBalPropertyAssignMapper.insert(item);
        }

        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> newAgainBalUpPropertyAssign(List<Long> sampleBalIds) {
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SamplebalDO::getId,sampleBalIds);
        List<SamplebalDO> samplebalDOs = samplebalMapper.selectList(queryWrapper);
        SamplebalDO samplebalDO = samplebalDOs.get(0);
        RcvDetailDO rcvDetailInfo = rcvdetailMapper.findRcvDetailInfo(samplebalDO.getRorderNo());
        if (rcvDetailInfo == null) {
            return ResultVo.failure(samplebalDO.getRorderNo()+"未查到订单信息");
        }

        // 获取优先分配的资产方
        ResultVo<DataTypeVO> dataTypeCodesInfoWithDS = dictCommonService.getDataTypeCodesInfoWithDS("9002", "26");
        if (!dataTypeCodesInfoWithDS.isSuccess()) {
            return ResultVo.failure("未能获取优先分配的资产方");
        }

        List<String> listPropertyAssign = Arrays.asList(dataTypeCodesInfoWithDS.getData().getExtNote1().split(";"));

        // 型号拆分
        if ("1".equals(rcvDetailInfo.getProdFlag())) {
            // 获取资产方分配清单
            LambdaQueryWrapper<SampleBalPropertyAssignDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2
                    .in(SampleBalPropertyAssignDO::getSampleBalId,sampleBalIds)
                    .eq(SampleBalPropertyAssignDO::getDelFlag,0)
                    .orderByAsc(SampleBalPropertyAssignDO::getModelNo,SampleBalPropertyAssignDO::getCompanyId);
            List<SampleBalPropertyAssignDO> sampleBalPropertyAssignDOS = sampleBalPropertyAssignMapper.selectList(queryWrapper2);

            // 计算拆分型号可以分配数量
            Map<String,Integer> calbiliMap = new HashMap<>();
            for(SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
                if (calbiliMap.containsKey(item.getModelNo())) {
                    calbiliMap.put(item.getModelNo(), item.getQuantity()+calbiliMap.getOrDefault(item.getModelNo(),0));
                } else {
                    calbiliMap.put(item.getModelNo(), item.getQuantity());
                }
            }

            // 各资产分配情况
            Map<String,SampleBalPropertyAssignDto> mapPropertyAssign = new HashMap<>();

            // 汇总各资产方能分配的最大数量
            for (SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
                if (mapPropertyAssign.containsKey(item.getCompanyId()+item.getModelNo())) {
                    SampleBalPropertyAssignDto dto = new SampleBalPropertyAssignDto();
                    dto.setQty(item.getQuantity()+mapPropertyAssign.get(item.getCompanyId()).getQty());
                    dto.setCompanyId(item.getCompanyId());
                    dto.setRemailFlag(true);
                    dto.setVersion(item.getVersion()==null?1:item.getVersion());
                    dto.setRemailQty(dto.getQty());
                    dto.setProportion(item.getProportion());
                    mapPropertyAssign.put(item.getCompanyId()+item.getModelNo(),dto);
                } else {
                    SampleBalPropertyAssignDto dto = new SampleBalPropertyAssignDto();
                    dto.setQty(item.getQuantity());
                    dto.setCompanyId(item.getCompanyId());
                    dto.setRemailFlag(true);
                    dto.setVersion(item.getVersion()==null?1:item.getVersion());
                    dto.setRemailQty(dto.getQty());
                    dto.setProportion(item.getProportion());
                    mapPropertyAssign.put(item.getCompanyId()+item.getModelNo(),dto);
                }
            }

            // 对拆分型号去重
            List<SampleBalPropertyAssignDO> uniqueassignDO = new ArrayList<>(sampleBalPropertyAssignDOS.stream()
                    .collect(Collectors.toMap(
                            SampleBalPropertyAssignDO::getModelNo,
                            Function.identity(),
                            (existing, replacement) -> existing
                    ))
                    .values());

            for(int i = 0; i < samplebalDOs.size(); i++  ) {
                int version = 1;
                SamplebalDO samplebalDO1 = samplebalDOs.get(i);
                if (i == 2 || i == 3) {
                    for (SampleBalPropertyAssignDO assignDO: uniqueassignDO) {
                        int remainQry = samplebalDO1.getQuantity();
                        for (String s : listPropertyAssign) {
                            if (remainQry <= 0) {
                                break;
                            }
                            SampleBalPropertyAssignDto sampleBalPropertyAssignDto = mapPropertyAssign.get(s + assignDO.getModelNo());
                            if (sampleBalPropertyAssignDto == null) {
                                continue;
                            }
                            if (sampleBalPropertyAssignDto.getRemailFlag() && sampleBalPropertyAssignDto.getRemailQty() > 0) {
                                remainQry = remainQry*sampleBalPropertyAssignDto.getProportion();
                                if (remainQry >= sampleBalPropertyAssignDto.getRemailQty()) {
                                    assignDO.setId(null);
                                    assignDO.setSampleBalId(samplebalDO1.getId());
                                    assignDO.setVersion(sampleBalPropertyAssignDto.getVersion()+1);
                                    assignDO.setCreateTime(new Date());
                                    assignDO.setCreateUser("ops");
                                    assignDO.setQuantity(sampleBalPropertyAssignDto.getRemailQty());
                                    assignDO.setProportion(sampleBalPropertyAssignDto.getProportion());
                                    sampleBalPropertyAssignMapper.insert(assignDO);
                                    remainQry -= sampleBalPropertyAssignDto.getRemailQty();
                                    sampleBalPropertyAssignDto.setRemailQty(0);
                                    sampleBalPropertyAssignDto.setRemailFlag(false);
                                    mapPropertyAssign.put(sampleBalPropertyAssignDto.getCompanyId(),sampleBalPropertyAssignDto);
                                } else {
                                    assignDO.setId(null);
                                    assignDO.setSampleBalId(samplebalDO1.getId());
                                    assignDO.setVersion(sampleBalPropertyAssignDto.getVersion()+1);
                                    assignDO.setCreateTime(new Date());
                                    assignDO.setCreateUser("ops");
                                    assignDO.setQuantity(remainQry);
                                    assignDO.setProportion(sampleBalPropertyAssignDto.getProportion());
                                    sampleBalPropertyAssignMapper.insert(assignDO);
                                    sampleBalPropertyAssignDto.setRemailQty(sampleBalPropertyAssignDto.getRemailQty() - remainQry);
                                    sampleBalPropertyAssignDto.setRemailFlag(true);
                                    mapPropertyAssign.put(sampleBalPropertyAssignDto.getCompanyId(),sampleBalPropertyAssignDto);
                                    remainQry = 0;
                                }
                            }
                        }
                    }
                }


                if (i == 0) {
                    for(SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
                        LambdaUpdateWrapper<SampleBalPropertyAssignDO> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper
                                .eq(SampleBalPropertyAssignDO::getId,item.getId())
                                .set(SampleBalPropertyAssignDO::getDelFlag,1)
                                .set(SampleBalPropertyAssignDO::getUpdateTime,new Date());
                        sampleBalPropertyAssignMapper.update(null,updateWrapper);

                        item.setId(null);
                        item.setCreateTime(new Date());
                        item.setDelFlag(0);
                        item.setCreateUser("ops");
                        version = Optional.ofNullable(item.getVersion()).orElse(1)+1;
                        item.setVersion(version);
                        sampleBalPropertyAssignMapper.insert(item);
                    }
                }

                if (i == 3) {
                    for (SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
                        item.setId(null);
                        item.setCreateTime(new Date());
                        item.setDelFlag(0);
                        item.setCreateUser("ops");
                        item.setSampleBalId(samplebalDO1.getId());
                        item.setQuantity(item.getQuantity()*-1);
                        item.setVersion(version);
                        sampleBalPropertyAssignMapper.insert(item);
                    }
                }

            }


        } else {  // 型号不拆分
            LambdaQueryWrapper<SampleBalPropertyAssignDO> qe = new LambdaQueryWrapper<>();
            qe.in(SampleBalPropertyAssignDO::getSampleBalId,sampleBalIds)
                    .eq(SampleBalPropertyAssignDO::getDelFlag,0)
                    .orderByAsc(SampleBalPropertyAssignDO::getCompanyId);;
            List<SampleBalPropertyAssignDO> sampleBalPropertyAssignDOS = sampleBalPropertyAssignMapper.selectList(qe);

            // 各资产分配情况
            Map<String,SampleBalPropertyAssignDto> mapPropertyAssign = new HashMap<>();
            // 汇总各资产方能分配的最大数量
            for (SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
                if (mapPropertyAssign.containsKey(item.getCompanyId())) {
                    SampleBalPropertyAssignDto dto = new SampleBalPropertyAssignDto();
                    dto.setQty(item.getQuantity()+mapPropertyAssign.get(item.getCompanyId()).getQty());
                    dto.setCompanyId(item.getCompanyId());
                    dto.setRemailFlag(true);
                    dto.setVersion(item.getVersion()==null?1:item.getVersion());
                    dto.setRemailQty(dto.getQty());
                    dto.setProportion(item.getProportion());
                    mapPropertyAssign.put(item.getCompanyId(),dto);
                } else {
                    SampleBalPropertyAssignDto dto = new SampleBalPropertyAssignDto();
                    dto.setQty(item.getQuantity());
                    dto.setCompanyId(item.getCompanyId());
                    dto.setRemailFlag(true);
                    dto.setVersion(item.getVersion()==null?1:item.getVersion());
                    dto.setRemailQty(dto.getQty());
                    dto.setProportion(item.getProportion());
                    mapPropertyAssign.put(item.getCompanyId(),dto);
                }
            }
           for(int i = 0; i < samplebalDOs.size(); i++  ) {
               int version = 1;
               SamplebalDO samplebalDO1 = samplebalDOs.get(i);
               if (i == 0) {
                   for(SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
                       LambdaUpdateWrapper<SampleBalPropertyAssignDO> updateWrapper = new LambdaUpdateWrapper<>();
                       updateWrapper
                               .eq(SampleBalPropertyAssignDO::getId,item.getId())
                               .set(SampleBalPropertyAssignDO::getDelFlag,1)
                               .set(SampleBalPropertyAssignDO::getUpdateTime,new Date());
                       sampleBalPropertyAssignMapper.update(null,updateWrapper);

                       item.setId(null);
                       item.setCreateTime(new Date());
                       item.setDelFlag(0);
                       item.setCreateUser("ops");
                       version = Optional.ofNullable(item.getVersion()).orElse(1)+1;
                       item.setVersion(version);
                       sampleBalPropertyAssignMapper.insert(item);
                   }
               }
               int remainQry = samplebalDO1.getQuantity();
               if (i == 1 || i == 2) {
                   for (String s : listPropertyAssign) {
                       if (remainQry <= 0) {
                           break;
                       }
                       SampleBalPropertyAssignDto sampleBalPropertyAssignDto = mapPropertyAssign.get(s);
                       if (sampleBalPropertyAssignDto == null) {
                           continue;
                       }
                       if (sampleBalPropertyAssignDto.getRemailFlag() && sampleBalPropertyAssignDto.getRemailQty() > 0) {
                           if (remainQry >= sampleBalPropertyAssignDto.getRemailQty() ) {
                               SampleBalPropertyAssignDO item = new SampleBalPropertyAssignDO();
                               item.setSampleBalId(samplebalDO1.getId());
                               item.setModelNo(samplebalDO.getModelNo());
                               item.setQuantity(sampleBalPropertyAssignDto.getRemailQty());
                               item.setCompanyId(sampleBalPropertyAssignDto.getCompanyId());
                               item.setVersion(sampleBalPropertyAssignDto.getVersion()+1);
                               item.setDelFlag(0);
                               item.setCreateTime(new Date());
                               item.setCreateUser("ops");
                               item.setProportion(sampleBalPropertyAssignDto.getProportion());
                               sampleBalPropertyAssignMapper.insert(item);
                               remainQry -= sampleBalPropertyAssignDto.getRemailQty();
                               sampleBalPropertyAssignDto.setRemailFlag(false);
                               sampleBalPropertyAssignDto.setRemailQty(0);
                               mapPropertyAssign.put(sampleBalPropertyAssignDto.getCompanyId(),sampleBalPropertyAssignDto);
                           } else {
                               SampleBalPropertyAssignDO item = new SampleBalPropertyAssignDO();
                               item.setSampleBalId(samplebalDO1.getId());
                               item.setModelNo(samplebalDO.getModelNo());
                               item.setQuantity(remainQry);
                               item.setCompanyId(sampleBalPropertyAssignDto.getCompanyId());
                               item.setVersion(sampleBalPropertyAssignDto.getVersion()+1);
                               item.setDelFlag(0);
                               item.setCreateTime(new Date());
                               item.setCreateUser("ops");
                               item.setProportion(sampleBalPropertyAssignDto.getProportion());
                               sampleBalPropertyAssignMapper.insert(item);
                               sampleBalPropertyAssignDto.setRemailFlag(true);
                               sampleBalPropertyAssignDto.setRemailQty(sampleBalPropertyAssignDto.getRemailQty() - remainQry);
                               mapPropertyAssign.put(sampleBalPropertyAssignDto.getCompanyId(),sampleBalPropertyAssignDto);
                               remainQry = 0;
                           }
                       }
                   }
               }
               if (i == 3) {
                   for (SampleBalPropertyAssignDO item : sampleBalPropertyAssignDOS) {
                       item.setId(null);
                       item.setCreateTime(new Date());
                       item.setDelFlag(0);
                       item.setCreateUser("ops");
                       item.setSampleBalId(samplebalDO1.getId());
                       item.setQuantity(item.getQuantity()*-1);
                       item.setVersion(version);
                       sampleBalPropertyAssignMapper.insert(item);
                   }
               }
           }
        }
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> upSampleBalPropertyAssignResult(List<Long> sampleBalIds) {
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SamplebalDO::getId,sampleBalIds.get(0));
        SamplebalDO samplebalDO = samplebalMapper.selectOne(queryWrapper);

        RcvDetailDO rcvDetailInfo = rcvdetailMapper.findRcvDetailInfo(samplebalDO.getRorderNo());
        if (rcvDetailInfo == null) {
            return ResultVo.failure(samplebalDO.getRorderNo()+"未查到订单信息");
        }

        if (DateUtil.getYear(samplebalDO.getCreatetime()) < 2025) {
            OrderLogVO orderLogVO = new OrderLogVO();
            orderLogVO.setDescription(sampleBalIds.toString() + "创建时间为" + DateUtil.dateToDateString(rcvDetailInfo.getCreateTime()) + ";2025年之前的不进行资产分配");
            orderLogVO.setOrderNo(rcvDetailInfo.getRorderFno());
            orderLogFeignApi.addOrderLog(orderLogVO);
            return ResultVo.failure("2025年之前的不进行资产分配");
        }

        ResultVo<String> stringResultVo = null;
        // 型号拆分
        if ("2".equals(rcvDetailInfo.getProdFlag())) {
            stringResultVo = upSampleBalPropertyAssignWithSplitModelNo(sampleBalIds);
        } else { // 非型号拆分
            stringResultVo = upSampleBalPropertyAssign2(sampleBalIds);
        }
        return stringResultVo;
    }

    @Override
    public ResultVo<String> upSampleBalPropertyAssignResultWitnReturn(Long sampleId) {
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SamplebalDO::getId,sampleId);
        SamplebalDO samplebalDO = samplebalMapper.selectOne(queryWrapper);
        RcvDetailDO rcvDetailInfo = rcvdetailMapper.findRcvDetailInfo(samplebalDO.getRorderNo());
        if (rcvDetailInfo == null) {
            return ResultVo.failure(samplebalDO.getRorderNo()+"未查到订单信息");
        }

        if (DateUtil.getYear(samplebalDO.getCreatetime()) < 2025) {
            OrderLogVO orderLogVO = new OrderLogVO();
            orderLogVO.setDescription(sampleId + "创建时间为" + DateUtil.dateToDateString(rcvDetailInfo.getCreateTime()) + ";2025年之前的不进行资产分配");
            orderLogVO.setOrderNo(rcvDetailInfo.getRorderFno());
            orderLogFeignApi.addOrderLog(orderLogVO);
            return ResultVo.failure("2025年之前的不进行资产分配");
        }

        ResultVo<String> stringResultVo = null;
        // 型号拆分
        if ("2".equals(rcvDetailInfo.getProdFlag())) {
            stringResultVo = upSampleBalPropertyAssignWithReturnSplitModelNo(sampleId);
        } else { // 非型号拆分
            stringResultVo = upSampleBalPropertyAssignWithReturn(sampleId);
        }
        return stringResultVo;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<SampleBalApplyDO> getSampleBalApplyDOListByBalId(Long samplebalDOId) {
        // 20530bug 读取sample_bal_apply表的交易主体
        LambdaQueryWrapper<SampleBalApplyDO> sampleBalApplyDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sampleBalApplyDOLambdaQueryWrapper.eq(SampleBalApplyDO::getSampleBalId,samplebalDOId).orderByDesc(SampleBalApplyDO::getId);
        return sampleBalApplyMapper.selectList(sampleBalApplyDOLambdaQueryWrapper);
    }

    /**
     * 导出全部所的展览展示品结余数据
     */
    public Workbook exportAllZlzsOrderBal(List<SamplebalDO> list, String deptName,String deptNo,String endExpDate) {

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        String path = "template/样品管理-展览展示管理.xlsx";
        ExcelHelper excel = new ExcelHelper(path);
        excel.openSheet(0);
        // 向模板中写入数据
        int row = 4;
        int count = 0;
        int cel;
        String title = deptName + "展览展示品盘点票";
        String endTime = "数据截止日: "+ DateUtil.dateToDateStringWithYMD(endExpDate);
        String nowDate = "盘点时间: "+ DateUtil.dateToDateStringWithYMD(DateUtil.dateToDateString(new Date()));
        excel.setCellValue(0,0,title);
        excel.setCellValue(2,1,endTime);
        excel.setCellValue(2,6,nowDate);

        for (SamplebalDO item : list) {
            cel = 0;
            count++;
            excel.setCellValue(row,cel++,count); // 序号
            excel.setCellValue(row,cel++,item.getModelNo()); // 型号
            excel.setCellValue(row,cel++,item.getRorderNo()); // 订单号
            excel.setCellValue(row,cel++,item.getQuantity()); // 账本数量
            excel.setCellValue(row,cel++,""); // 营业所实际数量
            excel.setCellValue(row,cel++,""); // 差异说明
            excel.setCellValue(row,cel++,""); // 备注
            row++;
        }
        // sendEamilWithDept(deptNo, title, excel, true);
//        String downloadName = "样品管理-展览展示管理.zip";
//        response.setContentType("application/zip; charset=UTF-8");
//        response.setHeader("Content-disposition", "attachment;filename=" + downloadName);
//        try {
//            Workbook workbook = excel.getWorkBook();
//            // ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
//            //多个文件从这里就可遍历了
//            // --start
//            ZipEntry entry = new ZipEntry(deptNo+"展览展示品盘点票.xlsx");
//            zipOutputStream.putNextEntry(entry);
//            ByteOutputStream byteOutputStream = new ByteOutputStream();
//            workbook.write(byteOutputStream);
//            byteOutputStream.writeTo(zipOutputStream);
//            byteOutputStream.close();
//            zipOutputStream.closeEntry();
//            // --end
//            // zipOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return excel.getWorkBook();
    }

    /**
     * 发送邮件展览展示盘点票给营业所
     * @param deptNo  部门编码
     * @param title   文件名称(同邮件主题)
     * @param excel    excel
     * @param isDeptNo  是否是营业所
     * @return
     */
    public ResultVo<String> sendEamilWithDept(String deptNo,String title,ExcelHelper excel, Boolean isDeptNo) {

        String backupEmail = "renyan@smc.com.cn";
        // 抄送邮箱
        String CsEmailAddr = "smccnorder@smc.com.cn;"+backupEmail;
        String toEamilAddr = "";
        String fileName = title+".xlsx";
        if (StringUtils.isBlank(deptNo)) {
            toEamilAddr = backupEmail;
            CsEmailAddr = null;
        } else {
            if ("allData".equals(deptNo)) {
                Map<String, InputStream> attachment = new LinkedHashMap<>(2);
                attachment.put(fileName, excel.convertTo()); // 附件
                EmailUtil.send(mailSender,backupEmail , null,null,title,
                        "所有营业所和非营业所的盘点票,详情请查看附件,本邮件由系统自动发送，请勿直接回复本邮件",attachment);
                return ResultVo.success("发送成功");
            }

            // 根据部门编码获取部门信息
            ResultVo<DepartmentVO> departmentInfo = opsCommonService.getDepartmentInfo(deptNo);
            if (departmentInfo.isSuccess()) {
                DepartmentVO data = departmentInfo.getData();
                if (data == null || StringUtils.isBlank(data.getEmailAddr())) {
                    EmailUtil.send(mailSender,backupEmail , null,null,"未能根据"+deptNo+"获取营业所邮箱地址,发送展览展示盘点票邮件失败",
                            "未能获取"+deptNo+"营业所邮箱地址,请进行邮箱地址维护,发送展览展示盘点票邮件失败,本邮件由系统自动发送，请勿直接回复本邮件");
                    return ResultVo.success("发送成功");
                }
                toEamilAddr = data.getEmailAddr();
            } else {
                EmailUtil.send(mailSender,backupEmail , null,null,"未能根据"+deptNo+"获取营业所邮箱地址,发送展览展示盘点票邮件失败",
                        "未能获取"+deptNo+"营业所邮箱地址,请进行邮箱地址维护,发送展览展示盘点票邮件失败,本邮件由系统自动发送，请勿直接回复本邮件");
                return ResultVo.success("发送成功");
            }
        }
        Map<String, InputStream> attachment = new LinkedHashMap<>(2);
        attachment.put(fileName, excel.convertTo()); // 附件

        if (StringUtils.isBlank(toEamilAddr)) {
            toEamilAddr = backupEmail;
        }

        EmailUtil.send(mailSender,toEamilAddr , CsEmailAddr,null, title,
                "详情请查看附件[" + title + "],本邮件由系统自动发送，请勿直接回复本邮件", attachment);
        return ResultVo.success(deptNo+"邮件发送完毕.");
    }

    /**
     * 发送邮件展览展示盘点票给营业所
     * pdf格式
     */
    public ResultVo<String> sendPdfEamilWithDept(String deptNo,String title,InputStream inputStream){
        String backupEmail = "renyan@smc.com.cn";
        // 抄送邮箱
        String CsEmailAddr = "smccnorder@smc.com.cn;"+backupEmail;
        String toEamilAddr = "";
        String fileName = title+".pdf";
        if (StringUtils.isBlank(deptNo)) {
            toEamilAddr = backupEmail;
            CsEmailAddr = null;
        } else {
            if ("allData".equals(deptNo)) {
                Map<String, InputStream> attachment = new LinkedHashMap<>(2);
                attachment.put(fileName, inputStream); // 附件
                EmailUtil.send(mailSender,backupEmail , null,null,title,
                        "所有营业所和非营业所的盘点票,详情请查看附件,本邮件由系统自动发送，请勿直接回复本邮件",attachment);
                return ResultVo.success("发送成功");
            }

            // 根据部门编码获取部门信息
            ResultVo<DepartmentVO> departmentInfo = opsCommonService.getDepartmentInfo(deptNo);
            String subject = "";
            if (departmentInfo.isSuccess()) {
                DepartmentVO data = departmentInfo.getData();
                if (data == null || StringUtils.isBlank(data.getEmailAddr())) {
                    subject = "未能根据"+deptNo+"获取营业所邮箱地址,发送展览展示盘点票邮件失败";
                    EmailUtil.send(mailSender,backupEmail , null,null,subject,
                            "未能获取"+deptNo+"营业所邮箱地址,请进行邮箱地址维护,发送展览展示盘点票邮件失败,本邮件由系统自动发送，请勿直接回复本邮件");
                    return ResultVo.failure(subject);
                }
                toEamilAddr = data.getEmailAddr();
            } else {
                subject = "未能根据"+deptNo+"获取营业所邮箱地址,发送展览展示盘点票邮件失败";
                EmailUtil.send(mailSender,backupEmail , null,null,subject,
                        "未能获取"+deptNo+"营业所邮箱地址,请进行邮箱地址维护,发送展览展示盘点票邮件失败,本邮件由系统自动发送，请勿直接回复本邮件");
                return ResultVo.failure(subject);
            }
        }
        Map<String, InputStream> attachment = new LinkedHashMap<>(2);
        attachment.put(fileName, inputStream); // 附件

        if (StringUtils.isBlank(toEamilAddr)) {
            toEamilAddr = backupEmail;
        }

        EmailUtil.send(mailSender,toEamilAddr , CsEmailAddr,null, title,
                "详情请查看附件[" + title + "],本邮件由系统自动发送，请勿直接回复本邮件", attachment);
        return ResultVo.success(deptNo+"邮件发送完毕.");
    }

    private SamplebalDO convertSampleBal(ReturnOrderDO returnOrderDO) {
        SamplebalDO samplebalDO = new SamplebalDO();
        samplebalDO.setCustomerNo(returnOrderDO.getCustomerNo());
        samplebalDO.setRorderNo(returnOrderDO.getOrderNo());
        samplebalDO.setModelNo(returnOrderDO.getModelNo());
        samplebalDO.setQuantity(returnOrderDO.getRcvFineqty() * -1);
        samplebalDO.setSubModelNo("");
        samplebalDO.setSubQty(0);
        samplebalDO.setUserNo(returnOrderDO.getUserNo());
        samplebalDO.setOptDate(new Date());
        samplebalDO.setApplyCode(returnOrderDO.getApplyNo());
        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(returnOrderDO.getOrderNo());
        String appType = "";
        SampleOrderApplyDO sampleOrderApplyDO = sampleOrderApplyService.findSampleOrderApplyByOrderNo(orderNoInfo.getOrderNo());
        if (sampleOrderApplyDO != null && StringUtils.isNotBlank(sampleOrderApplyDO.getApplyType()) ) {
            appType = sampleOrderApplyDO.getApplyType();
        } else {
            String ypAppTypeByOrderNo = getYPAppTypeByOrderNo(orderNoInfo.getOrderNo());
            if(StringUtils.isNotBlank(ypAppTypeByOrderNo)) {
                appType = ypAppTypeByOrderNo;
            }
        }
//        if (StringUtils.isBlank(appType)) {
//            appType = SampleBalAppTypeEnum.SYPMFSY.getCode();
//        }
        samplebalDO.setAppType(appType);
        // samplebalDO.setDeptNo(returnOrderDO.getDeptNo());
        samplebalDO.setRcvDeptNo(returnOrderDO.getDeptNo());
        ResultVo<String> deptNoByHRSalesDeptNo = opsCommonService.getDeptNoByHRSalesDeptNo(returnOrderDO.getDeptNo());
        if (deptNoByHRSalesDeptNo.isSuccess() && !deptNoByHRSalesDeptNo.getData().equals(returnOrderDO.getDeptNo())) {
            samplebalDO.setDeptDesc(returnOrderDO.getDeptNo());
            samplebalDO.setDeptNo(deptNoByHRSalesDeptNo.getData());
        } else {
            samplebalDO.setDeptDesc(returnOrderDO.getDeptNo());
            samplebalDO.setDeptNo(returnOrderDO.getDeptNo());
        }
        samplebalDO.setOrdType("9");
        samplebalDO.setOptTime(new Date());
        samplebalDO.setCreatetime(new Date());
        samplebalDO.setPriceApply(returnOrderDO.getSalesPrice());
        samplebalDO.setPrice(returnOrderDO.getSalesPrice());
        return samplebalDO;
    }


    // 根据订单号获取样品单申请类型
    public String getYPAppTypeByOrderNo(String orderNo) {
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(orderNo),SamplebalDO::getRorderNo,orderNo);
        List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(samplebalDOList)) {
            return null;
        } else {
            for (SamplebalDO item : samplebalDOList) {
                if (StringUtils.isNotBlank(item.getAppType())) {
                    return item.getAppType();
                }
            }
        }
        return null;
    }


    public void insertSampleBalLog(SamplebalDO samplebalDO) {
        LoginUserDTO loginAuthDto = null;
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto = new LoginUserDTO();
            loginAuthDto.setUserName("未知");
        }
        OrderLogVO orderLogVO = new OrderLogVO();
        orderLogVO.setOrderNo(samplebalDO.getRorderNo());
        orderLogVO.setOptType(9);
        orderLogVO.setOptTime(new Date());
        orderLogVO.setCreateTime(new Date());
        orderLogVO.setDescription("展览展示品销账"+samplebalDO.getQuantity());
        orderLogVO.setOptUserName(loginAuthDto.getUserName());
        orderLogFeignApi.addOrderLog(orderLogVO);
    }

}
