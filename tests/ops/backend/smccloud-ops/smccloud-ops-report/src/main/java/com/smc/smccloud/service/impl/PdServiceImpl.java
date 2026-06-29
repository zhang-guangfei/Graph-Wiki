package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.TransOrder;
import com.sales.ops.dto.inventory.AdjustItemDTO;
import com.sales.ops.dto.inventory.AdjustParam;
import com.sales.ops.dto.inventory.AdjustType;
import com.sales.ops.dto.inventory.InventoryForAdjustDto;
import com.sales.ops.dto.order.InventoryForAdjustInputDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.enums.InventoryTypeEnum;
import com.smc.smccloud.core.model.enums.OtherDataExcResultEnum;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.CommonMapper;
import com.smc.smccloud.mapper.pd.*;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.enums.*;
import com.smc.smccloud.model.inventory.InventoryVO;
import com.smc.smccloud.model.pd.*;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import com.smc.smccloud.service.*;
import com.smc.smccloud.util.JasperHelper;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @Author lyc
 * @Date 2023/6/8 11:05
 * @Descripton TODO
 */
@Slf4j
@Service
public class PdServiceImpl implements PdService {

    @Resource
    private OpsAsPdBatchMapper opsAsPdBatchMapper;
    @Resource
    private DictCommonService dictCommonService;
    @Resource
    private OpsAsWmsInventoryDataMapper opsAsWmsInventoryDataMapper;
    @Resource
    private HttpServletResponse response;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private RedisManager redisManager;
    @Resource
    private OpsAsWmsInventoryArrivedNotInMapper opsAsWmsInventoryArrivedNotInMapper;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private OpsAsPdBillDataMapper opsAsPdBillDataMapper;
    @Resource
    private PdBatchService pdBatchService;
    @Resource
    private OpsAsPdBorrowDataMapper opsAsPdBorrowDataMapper;
    @Resource
    private OpsAsPdOtherdataMapper opsAsPdOtherdataMapper;
    @Resource
    private OpsAsPdCompensateDataMapper opsAsPdCompensateDataMapper;
    @Resource
    private OpsAsFinanceBlanceDataMapper opsAsFinanceBlanceDataMapper;
    @Resource
    private OpsAsFinanceBlanceDataMapperSharedb opsAsFinanceBlanceDataMapperSharedb;
    @Resource
    private OpsAsWmsTaskNoticeMapper opsAsWmsTaskNoticeMapper;
    @Resource
    private OpsAsOpsInventoryDataMapper opsAsOpsInventoryDataMapper;
    @Resource
    private PdBillService pdBillService;
    @Resource
    private OpsAsWmsInventoryService opsAsWmsInventoryService;
    @Resource
    private PlatformTransactionManager transactionManager;
    @Resource
    private OpsInventoryOpeningMapper opsInventoryOpeningMapper;

    @Resource
    private OpsPdAdjustMapper opsPdAdjustMapper;

    @Resource
    private JavaMailSenderImpl mailSender;

    @Resource
    private OpsPdWarehouseConfigMapper opsPdWarehouseConfigMapper;

    @Value("${file.base}")
    private String serverPath;

    @Resource
    private OpsAsPdThreeReportWareMapper opsAsPdThreeReportWareMapper;

    @Resource
    private OpsAsPdThreeReportMapper opsAsPdThreeReportMapper;

    @Resource
    private OpsAsPdCommonMapper opsAsPdCommonMapper;

    @Autowired
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;

    @Resource
    private RedissonUtil redissonUtil;

    @Resource
    private CommonMapper commonMapper;

    private static final String PD_REDISSON_LOCK_KEY = "ops:pd:redisson:";

    @Override
    public ResultVo<PageInfo<OpsAsPdBatchVO>> listPdBatchList(OpsAsPdBatchRequestVO requestVO,int pageNumber, int pageSize) {

        if (requestVO == null) {
            return ResultVo.failure("入参不可为空");
        }

        if (StringUtils.isNotBlank(requestVO.getPdBatchNo())) {
            requestVO.setPdBatchNo(requestVO.getPdBatchNo()+"%");
        }

        if (StringUtils.isNotBlank(requestVO.getPdDataEndTime())) {
            requestVO.setPdDataEndTime(requestVO.getPdDataEndTime()+" 23:59:59");
        }

        if (StringUtils.isNotBlank(requestVO.getCreateUser())) {
            requestVO.setCreateUser(requestVO.getCreateUser()+"%");
        }

        PageInfo<OpsAsPdBatchVO> pageInfo = PageHelper.startPage(pageNumber, pageSize).doSelectPageInfo(() -> opsAsPdBatchMapper.listOpsAsPdBatch(requestVO));
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            // 动态获取盘点状态
            ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(CommonConstants.PD_STATUS_DATAS);
            if (!dataCodes.isSuccess() || dataCodes.getData() == null) {
                return ResultVo.failure("动态获取盘点状态失败.");
            }
            Map<String, String> map = dataCodes.getData().stream().collect(Collectors.toMap(DataCodeVO::getCode, DataCodeVO::getCodeName));

            LambdaQueryWrapper<OpsAsWmsInventoryDataDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            for (OpsAsPdBatchVO item: pageInfo.getList()) {
                lambdaQueryWrapper.clear();
                lambdaQueryWrapper.eq(OpsAsWmsInventoryDataDO::getPdBatchNo,item.getPdBatchNo());
                Integer count = opsAsWmsInventoryDataMapper.selectCount(lambdaQueryWrapper);
                // 获取当前盘点批次号的wms总条数
                item.setPdWmsCount(count);
                if (StringUtils.isNotBlank(item.getPdState())) {
                    if (map.containsKey(item.getPdState())) {
                        item.setPdState(map.get(item.getPdState()));
                    }
                }
                if (StringUtils.isNotBlank(item.getCreateUser())) {
                    String name = "["+item.getCreateUser()+"]"+opsCommonService.getEmpSaleNameByNo(item.getCreateUser());
                    item.setCreateUser(name);
                }
                if (StringUtils.isNotBlank(item.getIsActive())) {
                    item.setIsActive(PdIsActiveEnum.getCodeName(item.getIsActive()));
                }

            }
        }

        return ResultVo.success(pageInfo);

    }

    @Override
    public ResultVo<List<OpsAsPdBatchVO>> findBatchNo(String batchNo) {

        if (StringUtils.isNotBlank(batchNo)) {
            batchNo = batchNo+"%";
        }

        List<OpsAsPdBatchVO> opsAsPdBatchVOS = opsAsPdBatchMapper.listOpsAsPdBatchNo(batchNo);
        if (CollectionUtils.isEmpty(opsAsPdBatchVOS)) {
            return ResultVo.failure();
        }
        return ResultVo.success(opsAsPdBatchVOS);
    }

    @Override
    public ResultVo<String> newPD() {
        LoginUserDTO loginAuthDto;
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            return ResultVo.failure("登录信息失效,请重新登录.");
        }
        String pdBatchNo = "PD"+ DateUtil.getYearMonthCode(new Date());
        LambdaQueryWrapper<OpsAsPdBatchDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsAsPdBatchDO::getIsActive,"1");
        OpsAsPdBatchDO opsAsPdBatchDO ;
        int count  = opsAsPdBatchMapper.selectCount(queryWrapper);
        Date nowDate = new Date();

        if (count > 0) {
            return ResultVo.failure("当前存在已经激活的盘点批次,不可再次新建盘点");
        } else {
            opsAsPdBatchDO = new OpsAsPdBatchDO();
            opsAsPdBatchDO.setPdBatchNo(pdBatchNo);
            opsAsPdBatchDO.setPdState(PdStateEnum.xjpd.getCode());
            opsAsPdBatchDO.setPdStartTime(nowDate);
            opsAsPdBatchDO.setIsActive("1");
            opsAsPdBatchDO.setCreateUser(loginAuthDto.getUserNo());
            opsAsPdBatchDO.setCreateTime(nowDate);
            opsAsPdBatchDO.setUpdateUser(loginAuthDto.getUserNo());
            opsAsPdBatchDO.setUpdateTime(nowDate);
            try {
                opsAsPdBatchMapper.insert(opsAsPdBatchDO);

                // 修改同步数据中间表数据状态为0
                LambdaUpdateWrapper<OpsAsWmsTaskNoticeDO> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(OpsAsWmsTaskNoticeDO::getPdBatchNo,pdBatchNo).set(OpsAsWmsTaskNoticeDO::getJobStatus,"0");
                opsAsWmsTaskNoticeMapper.update(null,updateWrapper);

                // 修改其他数据录入配置里面的盘点批次
                LambdaUpdateWrapper<OpsAsPdOtherdataDO> updatePdOtherData = new LambdaUpdateWrapper<>();
                updatePdOtherData.set(OpsAsPdOtherdataDO::getPdBatchNo,pdBatchNo);
                opsAsPdOtherdataMapper.update(null,updatePdOtherData);

            } catch (Exception e) {
                log.error("新建盘点发生异常:{}",e.getMessage(),e);
                return ResultVo.failure("新建盘点失败: "+e.getMessage());
            }
        }

        return ResultVo.success("新建盘点成功:"+pdBatchNo);
    }

    @Override
    public ResultVo<String> cleanPdData() {

        LambdaQueryWrapper<OpsAsPdBatchDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OpsAsPdBatchDO::getIsActive,"1");
        OpsAsPdBatchDO opsAsPdBatchDO = opsAsPdBatchMapper.selectOne(lambdaQueryWrapper);
        LambdaQueryWrapper<OpsAsWmsInventoryDataDO> queryWrapper = new LambdaQueryWrapper<>();

        Integer count = opsAsWmsInventoryDataMapper.selectCount(queryWrapper);

        if (opsAsPdBatchDO == null && count == 0) {
            return ResultVo.failure("该次盘点还未开始.没有准备数据");
        }

        if (opsAsPdBatchDO != null) {

            if (!PdStateEnum.sjcqOk.getCode().equals(opsAsPdBatchDO.getPdState())
                   && !PdStateEnum.sjcq.getCode().equals(opsAsPdBatchDO.getPdState())
                    && !PdStateEnum.xjpd.getCode().equals(opsAsPdBatchDO.getPdState())) {
                return ResultVo.failure("只有盘点处于数据抽取中/数据抽取完毕状态才可以进行准备数据清空");
            }

            opsAsPdBatchMapper.cleanPdData(opsAsPdBatchDO.getPdBatchNo());
        }

        if (count > 0) {
            opsAsWmsInventoryDataMapper.delOpsAsWmsInventoryData();
        }
        LambdaUpdateWrapper<OpsAsWmsTaskNoticeDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(OpsAsWmsTaskNoticeDO::getJobName,"ops_wms_inventoryData")
                .set(OpsAsWmsTaskNoticeDO::getJobStatus,"1");
        opsAsWmsTaskNoticeMapper.update(null,updateWrapper);

        return ResultVo.success("本次准备数据已清空.");
    }

    @Override
    public void exportArriverNotIn(OpsArriverNotInRequestVO dto) {
        MsgPromptVO msg;
        if(dto == null) {
            msg = new MsgPromptVO();
            msg.setReturnType("0");
            msg.setMsg("入参不可为空");
            redisManager.set(CommonConstants.PD_EXPORT_ARRIVENOTIN_MSGPROMPT, JSONUtil.toJsonStr(msg));
            return;
        }
        if (StringUtils.isBlank(dto.getExpType())) {
            msg = new MsgPromptVO();
            msg.setReturnType("0");
            msg.setMsg("请选择导出数据类型.");
            redisManager.set(CommonConstants.PD_EXPORT_ARRIVENOTIN_MSGPROMPT, JSONUtil.toJsonStr(msg));
            return;
        }
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return;
        }
        String path = "";
        List<OpsAsWmsInventoryArrivedNotInDO> list;
        if(dto.getExpType().equals("1")) {
            list = opsAsWmsInventoryArrivedNotInMapper.listArriveNotInDetail(dto,batchNoWithIsActive.getData().getPdBatchNo());
            if (CollectionUtils.isEmpty(list)) {
                msg = new MsgPromptVO();
                msg.setReturnType("0");
                msg.setMsg("没有数据,请先点击导出到货未入数据按钮进行数据导入");
                redisManager.set(CommonConstants.PD_EXPORT_ARRIVENOTIN_MSGPROMPT, JSONUtil.toJsonStr(msg));
                return;
            }
            path = "templates/excel/到货未入数据导出-明细数据.xlsx";
            for (OpsAsWmsInventoryArrivedNotInDO item : list) {
                if (StringUtils.isNotBlank(item.getLogisticsConfirm())) {
                    item.setLogisticsConfirm(item.getLogisticsConfirm().equals("1") ? "有" : "没有");
                }
            }
            exportInvoiceData(list,path);

        } else {
            list = opsAsWmsInventoryArrivedNotInMapper.listArriveNotInWithGroup(dto,batchNoWithIsActive.getData().getPdBatchNo());
            if (CollectionUtils.isEmpty(list)) {
                msg = new MsgPromptVO();
                msg.setReturnType("0");
                msg.setMsg("没有数据,请先点击导出到货未入数据按钮进行数据导入");
                redisManager.set(CommonConstants.PD_EXPORT_ARRIVENOTIN_MSGPROMPT, JSONUtil.toJsonStr(msg));
                return;
            }
            path = "templates/excel/到货未入数据导出-发票数据.xlsx";
            exportInvoiceDataGroupByInvoiceNo(commonArriveNotInDataHandWithGroup2(list),path);
        }
        msg = new MsgPromptVO();
        msg.setReturnType("1");
        msg.setMsg("数据导出完毕");
        redisManager.set(CommonConstants.PD_EXPORT_ARRIVENOTIN_MSGPROMPT, JSONUtil.toJsonStr(msg));
    }

    @Override
    public ResultVo<String> getExportMsgPrompt() {
        if (redisManager.hasKey(CommonConstants.PD_EXPORT_ARRIVENOTIN_MSGPROMPT)) {
            Object obj = redisManager.get(CommonConstants.PD_EXPORT_ARRIVENOTIN_MSGPROMPT);
            if (obj != null) {
                MsgPromptVO msgPromptVO = JSONObject.parseObject(obj.toString(), MsgPromptVO.class);
                if ("0".equals(msgPromptVO.getReturnType())) {
                    return ResultVo.failure(msgPromptVO.getMsg());
                }
                return ResultVo.success(msgPromptVO.getMsg());
            }
        }
        return ResultVo.failure("导出提示信息获取失败.");
    }

    @Override
    public void downImpExel() {
        String path = "templates/excel/到货未入数据导入.xlsx";
        ExcelUtil excel = new ExcelUtil(path);
        excel.writeToResponse(response, "到货未入数据导入.xlsx");
    }

    @Override
    // @Transactional(rollbackFor = Exception.class )
    public ResultVo<String> batchImpArriverNotIn(MultipartFile file, String loginUser) {
        if (file == null) {
            return ResultVo.failure("请选择导入文件");
        }
        if (StringUtils.isBlank(loginUser)) {
            return ResultVo.failure("登录信息失效,请重新登录");
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

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return ResultVo.failure("获取盘点批次号失败");
        }

        if (PdStateEnum.dhsjzbOK.getCode().equals(batchNoWithIsActive.getData().getPdState())) {
            return ResultVo.failure("到货未入数据确认完毕,不可再次导入.");
        }
        OpsAsPdBatchDO opsAsPdBatchDO = batchNoWithIsActive.getData();
        String batchNo = opsAsPdBatchDO.getPdBatchNo();
        // 重置上次操作
        opsAsWmsInventoryArrivedNotInMapper.delarrivedNotIn(batchNo,"2");
        opsAsWmsInventoryArrivedNotInMapper.updateLastOpt(batchNo,"3");
        StringBuilder msg = new StringBuilder();
        msg.append(" ");
        Date nowDate = new Date();
        LambdaUpdateWrapper<OpsAsWmsInventoryArrivedNotInDO> updateWrapper = new LambdaUpdateWrapper<>();
        LambdaQueryWrapper<OpsAsWmsInventoryArrivedNotInDO> queryWrapper = new LambdaQueryWrapper<>();
        OpsAsWmsInventoryArrivedNotInDO opsAsWmsInventoryArrivedNotInDO;
        int okCount = 0;
        int errorCount = 0;
        while (true) {
            row++;
            rows = sheet.getRow(row);
            if (rows == null) {
                break;
            }
            String linvoiceNo = excel.getCellString(rows.getCell(0));
            String lwarehouseCode = excel.getCellString(rows.getCell(1));
            String lisAll = excel.getCellString(rows.getCell(2));
            String wms_sys_invoice_no = excel.getCellString(rows.getCell(3));
            String wms_sys_warehouse_code = excel.getCellString(rows.getCell(4));
            String wms_sys_is_all = excel.getCellString(rows.getCell(5));
            String logistics_confirm = excel.getCellString(rows.getCell(6));
            if (StringUtils.isBlank(linvoiceNo) || StringUtils.isBlank(lwarehouseCode)) {
                continue;
            }
            if (StringUtils.isNotBlank(linvoiceNo) && StringUtils.isNotBlank(lwarehouseCode) &&
                StringUtils.isNotBlank(wms_sys_invoice_no) && StringUtils.isNotBlank(wms_sys_warehouse_code))
            {
                if(linvoiceNo.trim().equals(wms_sys_invoice_no.trim()) && lwarehouseCode.trim().equals(wms_sys_warehouse_code.trim()))
                {
                    updateWrapper.clear();
                    updateWrapper
                            .eq(OpsAsWmsInventoryArrivedNotInDO::getWmsSysInvoiceNo,linvoiceNo)
                            .eq(OpsAsWmsInventoryArrivedNotInDO::getWmsSysWarehouseCode,lwarehouseCode)
                            .set(OpsAsWmsInventoryArrivedNotInDO::getLInvoiceNo,linvoiceNo)
                            .set(OpsAsWmsInventoryArrivedNotInDO::getLWarehouseCode,lwarehouseCode)
                            .set(OpsAsWmsInventoryArrivedNotInDO::getLIsAll,lisAll)
                            .set(OpsAsWmsInventoryArrivedNotInDO::getDataResource,"3")
                            .set(OpsAsWmsInventoryArrivedNotInDO::getLogisticsConfirm,logistics_confirm);
                    int update = opsAsWmsInventoryArrivedNotInMapper.update(null, updateWrapper);
                    if(update != 0) {
                        okCount++;
                    }
                } else {
                    errorCount++;
                    msg.append("物流发票号:"+linvoiceNo+"仓库: "+lwarehouseCode+"和系统不一致,请确认;");
                }
            } else if (StringUtils.isNotBlank(linvoiceNo) && StringUtils.isNotBlank(lwarehouseCode) &&
                        StringUtils.isBlank(wms_sys_invoice_no) && StringUtils.isBlank(wms_sys_warehouse_code) )
            {
                opsAsWmsInventoryArrivedNotInDO = new OpsAsWmsInventoryArrivedNotInDO();
                opsAsWmsInventoryArrivedNotInDO.setLInvoiceNo(linvoiceNo);
                opsAsWmsInventoryArrivedNotInDO.setLWarehouseCode(lwarehouseCode);
                opsAsWmsInventoryArrivedNotInDO.setLIsAll(lisAll);
                opsAsWmsInventoryArrivedNotInDO.setPdBatchNo(batchNo);
                opsAsWmsInventoryArrivedNotInDO.setWmsSysWarehouseCode(wms_sys_warehouse_code);
                opsAsWmsInventoryArrivedNotInDO.setWmsSysInvoiceNo(wms_sys_invoice_no);
                opsAsWmsInventoryArrivedNotInDO.setWmsSysIsAll(wms_sys_is_all);
                opsAsWmsInventoryArrivedNotInDO.setLogisticsConfirm(logistics_confirm);
                opsAsWmsInventoryArrivedNotInDO.setPdDataType(PdDataTypeEnum.CG.getCode());
                opsAsWmsInventoryArrivedNotInDO.setCreateTime(nowDate);
                opsAsWmsInventoryArrivedNotInDO.setCreateUser(loginUser);
                opsAsWmsInventoryArrivedNotInDO.setUpdateTime(nowDate);
                opsAsWmsInventoryArrivedNotInDO.setUpdateUser(loginUser);
                opsAsWmsInventoryArrivedNotInDO.setDataResource("2");
                try {
                    opsAsWmsInventoryArrivedNotInMapper.insert(opsAsWmsInventoryArrivedNotInDO);
                    okCount++;
                } catch (Exception e) {
                    errorCount++;
                    log.error("新增物流确认结果发生异常: {}",e.getMessage(),e);
                    msg.append("物流发票号:"+linvoiceNo+"仓库: "+lwarehouseCode+"确认失败;");
                }
            }
        }
        pdBatchService.updatePdStateByPdbatchNo(batchNo,PdStateEnum.dhsjzb.getCode(),loginUser);
        return ResultVo.success("操作完毕.成功"+okCount+"条;失败:"+errorCount+"条; 失败提示:"+msg.toString());
    }

    @Override
    public ResultVo<OpsAsPdBatchDO> getBatchNoWithIsActive() {
        LambdaQueryWrapper<OpsAsPdBatchDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsAsPdBatchDO::getIsActive,"1");
        OpsAsPdBatchDO opsAsPdBatchDO;
        try {
            opsAsPdBatchDO = opsAsPdBatchMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            return ResultVo.failure("请确定是否仅存在一个有效的盘点批次号");
        }
        return ResultVo.success(opsAsPdBatchDO);
    }

    @Override
    public ResultVo<String> expotToArriveNotInByWmsData() {
        ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(CommonConstants.PD_DATA_TYPE);
        if (!dataCodes.isSuccess() || CollectionUtils.isEmpty(dataCodes.getData())) {
            return ResultVo.failure("动态获取数据类型配置失败");
        }
        List<String> dataTypeList = new ArrayList<>();
        for (DataCodeVO item : dataCodes.getData()) {
            if (StringUtils.isNotBlank(item.getExtNote3()) && "1".equals(item.getExtNote3())) {
                dataTypeList.add(item.getCode());
            }
        }
        if (CollectionUtils.isEmpty(dataTypeList)) {
            return ResultVo.failure("动态获取数据类型配置失败");
        }

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return ResultVo.failure("获取盘点批次号失败");
        }

        if (!PdStateEnum.sjcqOk.getCode().equals(batchNoWithIsActive.getData().getPdState())) {
            return ResultVo.failure("仅数据抽取完毕状态才可以进行到货未入数据准备中");
        }

        String pdBatchNo = batchNoWithIsActive.getData().getPdBatchNo();
        LambdaQueryWrapper<OpsAsWmsInventoryDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(OpsAsWmsInventoryDataDO::getPdDataType,dataTypeList).eq(OpsAsWmsInventoryDataDO::getPdBatchNo,pdBatchNo);
        List<OpsAsWmsInventoryDataDO> opsAsWmsInventoryDataDOS = opsAsWmsInventoryDataMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(opsAsWmsInventoryDataDOS)) {
            return ResultVo.failure("没有到货未入库数据,请排查数据准备是否完整");
        }
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("");
        }
        Date nowDate = new Date();
        OpsAsWmsInventoryArrivedNotInDO notInDO = new OpsAsWmsInventoryArrivedNotInDO();
        StringBuilder errMsg = new StringBuilder();
        errMsg.append("失败描述:");
        int okCount = 0;
        int errCount = 0;
        for (OpsAsWmsInventoryDataDO item : opsAsWmsInventoryDataDOS) {
            notInDO.setPdBatchNo(pdBatchNo);
            notInDO.setWmsSysInvoiceNo(item.getInvoiceNo());
            notInDO.setWmsSysWarehouseCode(item.getWarehouseCode());
            notInDO.setModelNo(item.getModelNo());
            notInDO.setCaseNo(item.getCaseNo());
            notInDO.setBarcode(item.getBarcode());
            notInDO.setBillQty(item.getBillQty());
            notInDO.setCreateUser(loginAuthDto.getUserNo());
            notInDO.setUpdateUser(loginAuthDto.getUserNo());
            notInDO.setUpdateTime(nowDate);
            notInDO.setCreateTime(nowDate);
            notInDO.setPdDataType(item.getPdDataType());
            notInDO.setOrderNo(item.getOrderNo());
            notInDO.setDataResource("1"); // 1wms 2物流确认文件
            notInDO.setIsAss(item.getIsAss());
            try {
                opsAsWmsInventoryArrivedNotInMapper.insert(notInDO);
                okCount++;
            } catch (Exception e) {
                errCount++;
                log.error("wms中间表数据写入到货未入发生异常: {}",e.getMessage(),e);
                errMsg.append(item.getInvoiceNo()+"-"+item.getWarehouseCode()+"wms中间表数据写入到货未入发生异常;");
            }
        }
        pdBatchService.updatePdStateByPdbatchNo(pdBatchNo,PdStateEnum.dhsjzb.getCode(),loginAuthDto.getUserNo());
        return ResultVo.success("导入完毕,成功共计"+okCount+"条;"+"失败:"+errCount+"条;"+errMsg.toString());
    }

    @Override
    public ResultVo<String>  arriveNotInInsertToBillData() {

        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return ResultVo.failure("获取盘点批次号失败");
        }

        if (!PdStateEnum.dhsjzb.getCode().equals(batchNoWithIsActive.getData().getPdState())) {
            return ResultVo.failure("无到货未入数据不允许点击确认数据按钮");
        }

        LambdaQueryWrapper<OpsAsWmsInventoryArrivedNotInDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsAsWmsInventoryArrivedNotInDO::getLogisticsConfirm,"1");
        queryWrapper.eq(OpsAsWmsInventoryArrivedNotInDO::getPdBatchNo,batchNoWithIsActive.getData().getPdBatchNo());

        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(12600);
        PageInfo<OpsAsWmsInventoryArrivedNotInDO> pageInfo;
        List<OpsAsPdBillDataDO> list = new CopyOnWriteArrayList<>();
        LoginUserDTO finalLoginAuthDto = loginAuthDto;
        while (true) {
            pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> {
                        opsAsWmsInventoryArrivedNotInMapper.selectList(queryWrapper);
                    });
            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                return ResultVo.failure("没有物流确认有的发票号,请确了解");
            }
//            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
            list.clear();
            try {
                for (OpsAsWmsInventoryArrivedNotInDO item : pageInfo.getList()) {
//                    fixedThreadPool.execute(() -> {
                        if (StringUtils.isNotBlank(item.getLogisticsConfirm()) && "1".equals(item.getLogisticsConfirm())) {
                            if (StringUtils.isNotBlank(item.getLInvoiceNo()) && StringUtils.isNotBlank(item.getWmsSysInvoiceNo())) {
                                if (item.getLInvoiceNo().equals(item.getWmsSysInvoiceNo())) {
                                    OpsAsPdBillDataDO  billDataDO = new OpsAsPdBillDataDO();
                                    billDataDO.setPdBatchNo(item.getPdBatchNo());
                                    billDataDO.setWarehouseCode(item.getLWarehouseCode());
                                    if (StringUtils.isNotBlank(item.getLInvoiceNo()) && StringUtils.isNotBlank(item.getBarcode())) {
                                        billDataDO.setShelvesNo(item.getLInvoiceNo()+item.getBarcode());
                                    }
                                    billDataDO.setCaseNo(item.getCaseNo());
                                    billDataDO.setBarcode(item.getBarcode());
                                    // 订单号
                                    billDataDO.setOrderNo(item.getOrderNo());
                                    billDataDO.setModelNo1(item.getModelNo());
//                                    billDataDO.setPdDataType(PdDataTypeEnum.CG.getCode());
                                    billDataDO.setPdDataType(item.getPdDataType()); // BUG19639 盘点票取数问题
                                    billDataDO.setBillQty(item.getBillQty());
                                    billDataDO.setPdQty1(item.getBillQty());
                                    billDataDO.setPdInputort1(finalLoginAuthDto.getUserNo());
                                    billDataDO.setWmsInvoiceNo(item.getWmsSysInvoiceNo());
                                    billDataDO.setInvoiceNo(item.getLInvoiceNo());
                                    billDataDO.setPdInputTime1(new Date());
                                    billDataDO.setPdBillType(PdBillTypeEnum.QDBILL.getCode()); // 清单票
                                    billDataDO.setCreateUser(finalLoginAuthDto.getUserNo());
                                    billDataDO.setCreateTime(new Date());
                                    billDataDO.setUpdateTime(new Date());
                                    billDataDO.setUpdateUser(finalLoginAuthDto.getUserNo());
                                    billDataDO.setDelFlag(false);
                                    billDataDO.setIsAss(StringUtils.isBlank(item.getIsAss()) ? "0":item.getIsAss());
                                    list.add(billDataDO);
                                }
                            } else  if (StringUtils.isNotBlank(item.getLInvoiceNo()) && StringUtils.isBlank(item.getWmsSysInvoiceNo())) {
                                OpsAsPdBillDataDO  billDataDO = new OpsAsPdBillDataDO();
                                billDataDO.setPdBatchNo(item.getPdBatchNo());
                                billDataDO.setWarehouseCode(item.getLWarehouseCode());
                                if (StringUtils.isNotBlank(item.getLInvoiceNo()) && StringUtils.isNotBlank(item.getBarcode())) {
                                    billDataDO.setShelvesNo(item.getLInvoiceNo()+item.getBarcode());
                                }
                                billDataDO.setCaseNo(item.getCaseNo());
                                billDataDO.setBarcode(item.getBarcode());
                                // 订单号
                                billDataDO.setOrderNo(item.getOrderNo());
                                billDataDO.setModelNo1(item.getModelNo());
                                billDataDO.setBillQty(item.getBillQty());
                                billDataDO.setPdInputort1(finalLoginAuthDto.getUserNo());
                                billDataDO.setWmsInvoiceNo(item.getWmsSysInvoiceNo());
                                billDataDO.setInvoiceNo(item.getLInvoiceNo());
                                billDataDO.setPdInputTime1(new Date());
                                billDataDO.setPdDataType(item.getPdDataType());
                                billDataDO.setPdBillType(PdBillTypeEnum.DHWRBLANKBILL.getCode()); // 空白票
                                billDataDO.setCreateUser(finalLoginAuthDto.getUserNo());
                                billDataDO.setCreateTime(new Date());
                                billDataDO.setUpdateTime(new Date());
                                billDataDO.setUpdateUser(finalLoginAuthDto.getUserNo());
                                billDataDO.setDelFlag(false);
                                billDataDO.setIsAss(StringUtils.isBlank(item.getIsAss()) ? "0":item.getIsAss());
                                list.add(billDataDO);
                            }
                        }
//                    });
                }
            } finally {
//                fixedThreadPool.shutdown();
//                while (true) {
//                    if (fixedThreadPool.isTerminated()) {
//                        break;
//                    }
//                }
            }
            List<OpsAsPdBillDataDO> tempList = list;
            // ExecutorService fixedThreadPoolInsert = Executors.newFixedThreadPool(10);
            List<List<OpsAsPdBillDataDO>> arrayListArrayList = ListUtils.partition(tempList,80);
            try {
                for (List<OpsAsPdBillDataDO> item : arrayListArrayList) {
                   // fixedThreadPoolInsert.execute(() -> {
                        opsAsPdBillDataMapper.batchInsertPdPillData(item);
                  //  });
                }
            } finally {
//                fixedThreadPoolInsert.shutdown();
//                while (true) {
//                    if (fixedThreadPoolInsert.isTerminated()) {
//                        break;
//                    }
//                }
            }
            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }
        redisManager.set(CommonConstants.OPS_PD_STATUS,"0");
        pdBatchService.updatePdStateByPdbatchNo(batchNoWithIsActive.getData().getPdBatchNo(),PdStateEnum.dhsjzbOK.getCode(),loginAuthDto.getUserNo());
        return ResultVo.success("确认完毕");
    }

    @Override
    public ResultVo<String> surePdType(String pdType, String pdDataType) {

        if (StringUtils.isBlank(pdType)) {
            return ResultVo.failure("请选择盘点形式");
        }
        if (StringUtils.isBlank(pdDataType)) {
            return ResultVo.failure("请选择盘点数据类型");
        }

        redisManager.set(CommonConstants.OPS_PD_DATA_TYPE,pdType+";"+pdDataType);

        redisManager.set(CommonConstants.OPS_PD_STATUS,PdRedisStatusEnum.alreadyHandToSureData.getCode());

        List<String> pdDataTypeList = new ArrayList<>();
        switch (pdDataType) {
            case "1":
                pdDataTypeList.add(PdDataTypeEnum.CG.getCode());
                pdDataTypeList.add(PdDataTypeEnum.DB.getCode());
                pdDataTypeList.add(PdDataTypeEnum.TH.getCode());
                break;
            case "2":
                pdDataTypeList.add(PdDataTypeEnum.GD.getCode());
                break;
            case "3":
                pdDataTypeList.add(PdDataTypeEnum.JY.getCode());
                break;
        }
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }
        if (pdDataTypeList.size() == 3) {
            LambdaUpdateWrapper<OpsAsPdBillDataDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .ne(OpsAsPdBillDataDO::getPdBillType,PdBillTypeEnum.DHWRBLANKBILL.getCode())
                    .in(OpsAsPdBillDataDO::getPdDataType,pdDataTypeList)
                    .set(OpsAsPdBillDataDO::getPdBillType,pdType)
                    .set(OpsAsPdBillDataDO::getUpdateTime,new Date())
                    .set(OpsAsPdBillDataDO::getUpdateUser,loginAuthDto.getUserNo());;
            int count = opsAsPdBillDataMapper.update(null, updateWrapper);
            if (count == 0) {
                return ResultVo.failure("没有对应数据，请了解");
            }
        } else {
            LambdaQueryWrapper<OpsAsWmsInventoryDataDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(OpsAsWmsInventoryDataDO::getPdDataType, pdDataTypeList);
            Integer count = opsAsWmsInventoryDataMapper.selectCount(queryWrapper);
            if (count == 0) {
                return ResultVo.failure("没有对应数据，请了解");
            }
            LambdaUpdateWrapper<OpsAsWmsInventoryDataDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .in(OpsAsWmsInventoryDataDO::getPdDataType,pdDataTypeList)
                    .set(OpsAsWmsInventoryDataDO::getPdBillType,pdType)
                    .set(OpsAsWmsInventoryDataDO::getUpdateTime,new Date())
                    .set(OpsAsWmsInventoryDataDO::getUpdateUser,loginAuthDto.getUserNo());
            opsAsWmsInventoryDataMapper.update(null,updateWrapper);
        }
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> surePdType2(String pdType, String pdDataType) {
        if (StringUtils.isBlank(pdType) || StringUtils.isBlank(pdDataType)) {
            return ResultVo.failure("盘点数据/盘点形式 不可为空");
        }
        OpsAsPdBatchDO opsAsPdBatchDO = new OpsAsPdBatchDO();

        List<String> pdDataTypeList = new ArrayList<>();

        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }

        redisManager.set(CommonConstants.OPS_PD_DATA_TYPE,pdType+";"+pdDataType);

        redisManager.set(CommonConstants.OPS_PD_STATUS,PdRedisStatusEnum.alreadyHandToSureData.getCode());
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        switch (pdDataType) {
            case "1":
                if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
                    return ResultVo.failure("当前不存在激活的盘点批次.");
                }
                if(StringUtils.isNotBlank(batchNoWithIsActive.getData().getPdDataTypeDhwr()) ||
                        StringUtils.isNotBlank(batchNoWithIsActive.getData().getPdBillTypeDhwr())) {
                    return ResultVo.success("您已经确认过数据,{到货未入}-{票据类型" + pdType+ "}");
                }
                opsAsPdBatchDO.setPdDataTypeDhwr(pdDataType);
                opsAsPdBatchDO.setPdBillTypeDhwr(pdType);
                updatePdBatchPdType(opsAsPdBatchDO,loginAuthDto);

                pdDataTypeList.add(PdDataTypeEnum.CG.getCode());
                pdDataTypeList.add(PdDataTypeEnum.DB.getCode());
                pdDataTypeList.add(PdDataTypeEnum.TH.getCode());

                break;
            case "2":
                opsAsPdBatchDO.setPdDataTypeGd(pdDataType);
                opsAsPdBatchDO.setPdBillTypeGd(pdType);
                updatePdBatchPdType(opsAsPdBatchDO,loginAuthDto);

                pdDataTypeList.add(PdDataTypeEnum.GD.getCode());

                break;
            case "3":
                opsAsPdBatchDO.setPdDataTypeJy(pdDataType);
                opsAsPdBatchDO.setPdBillTypeJy(pdType);
                updatePdBatchPdType(opsAsPdBatchDO,loginAuthDto);

                pdDataTypeList.add(PdDataTypeEnum.JY.getCode());

                break;
        }

        if (pdDataTypeList.size() == 3) {
            LambdaUpdateWrapper<OpsAsPdBillDataDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .eq(OpsAsPdBillDataDO::getPdBatchNo,batchNoWithIsActive.getData().getPdBatchNo())
                    .ne(OpsAsPdBillDataDO::getPdBillType,PdBillTypeEnum.DHWRBLANKBILL.getCode())
                    .in(OpsAsPdBillDataDO::getPdDataType,pdDataTypeList)
                    .set(OpsAsPdBillDataDO::getPdBillType,pdType)
                    .set(OpsAsPdBillDataDO::getUpdateTime,new Date())
                    .set(OpsAsPdBillDataDO::getUpdateUser,loginAuthDto.getUserNo());
            opsAsPdBillDataMapper.update(null, updateWrapper);
        } else {
            LambdaQueryWrapper<OpsAsWmsInventoryDataDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(OpsAsWmsInventoryDataDO::getPdDataType, pdDataTypeList);
            queryWrapper.eq(OpsAsWmsInventoryDataDO::getPdBatchNo,batchNoWithIsActive.getData().getPdBatchNo());
            Integer count = opsAsWmsInventoryDataMapper.selectCount(queryWrapper);
            if (count == 0) {
                return ResultVo.success("操作成功");
            }
            LambdaUpdateWrapper<OpsAsWmsInventoryDataDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .eq(OpsAsWmsInventoryDataDO::getPdBatchNo,batchNoWithIsActive.getData().getPdBatchNo())
                    .in(OpsAsWmsInventoryDataDO::getPdDataType,pdDataTypeList)
                    .set(OpsAsWmsInventoryDataDO::getPdBillType,pdType)
                    .set(OpsAsWmsInventoryDataDO::getUpdateTime,new Date())
                    .set(OpsAsWmsInventoryDataDO::getUpdateUser,loginAuthDto.getUserNo());
            opsAsWmsInventoryDataMapper.update(null,updateWrapper);
        }
        return ResultVo.success("操作成功");
    }

    public void updatePdBatchPdType(OpsAsPdBatchDO opsAsPdBatchDO, LoginUserDTO loginAuthDto) {

        log.info("{} 确认盘点形式 {} ",loginAuthDto.getUserNo(),JSONUtil.toJsonPrettyStr(opsAsPdBatchDO));
        LambdaUpdateWrapper<OpsAsPdBatchDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(OpsAsPdBatchDO::getIsActive,"1")
                .set(StringUtils.isNotBlank(opsAsPdBatchDO.getPdDataTypeGd()),OpsAsPdBatchDO::getPdDataTypeGd,opsAsPdBatchDO.getPdDataTypeGd())
                .set(StringUtils.isNotBlank(opsAsPdBatchDO.getPdBillTypeGd()),OpsAsPdBatchDO::getPdBillTypeGd,opsAsPdBatchDO.getPdBillTypeGd())
                .set(StringUtils.isNotBlank(opsAsPdBatchDO.getPdDataTypeDhwr()),OpsAsPdBatchDO::getPdDataTypeDhwr,opsAsPdBatchDO.getPdDataTypeDhwr())
                .set(StringUtils.isNotBlank(opsAsPdBatchDO.getPdBillTypeDhwr()),OpsAsPdBatchDO::getPdBillTypeDhwr,opsAsPdBatchDO.getPdBillTypeDhwr())
                .set(StringUtils.isNotBlank(opsAsPdBatchDO.getPdDataTypeJy()),OpsAsPdBatchDO::getPdDataTypeJy,opsAsPdBatchDO.getPdDataTypeJy())
                .set(StringUtils.isNotBlank(opsAsPdBatchDO.getPdBillTypeJy()),OpsAsPdBatchDO::getPdBillTypeJy,opsAsPdBatchDO.getPdBillTypeJy())
                .set(OpsAsPdBatchDO::getUpdateTime,new Date())
                .set(OpsAsPdBatchDO::getUpdateUser,loginAuthDto.getUserNo());
        opsAsPdBatchMapper.update(null,updateWrapper);
    }

    @Override
    // @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> createPdBill(CreateBlankRequest dto) {

        if (dto == null) {
            return ResultVo.failure("入参不可为空.");
        }
        log.info("打印盘点票输入票据数量 => {} ",JSONUtil.toJsonStr(dto));

//        if (dto.getMasterWarehouse() <= 0)  {
//            return ResultVo.failure("请输入中心仓空白票生成张数");
//        }
//        if (dto.getSubWarehouse() <= 0)  {
//            return ResultVo.failure("请输入分库空白票生成张数");
//        }
//        if (dto.getWtWarehouse() <= 0)  {
//            return ResultVo.failure("请输入寄售备库空白票生成张数");
//        }

        Object o = redisManager.get(CommonConstants.OPS_PD_STATUS);

        if (o != null) {
            if(PdRedisStatusEnum.handing.getCode().equals(o.toString())) {
                return ResultVo.success("正在执行中,请耐心等待..");
            } else if (PdRedisStatusEnum.alreadyHand.getCode().equals(o.toString())) {
                return ResultVo.success("已生成盘点票,不可重复生成.");
            } else if (PdRedisStatusEnum.notHandToArriveNotIn.getCode().equals(o.toString())) {
                return ResultVo.failure("请先进行到货未入数据确认.");
            } else if (!PdRedisStatusEnum.alreadyHandToSureData.getCode().equals(o.toString()) )  {
                return ResultVo.failure("请先进行盘点形式确认.");
            }
        }

        ResultVo<String> checkSurePdType = checkSurePdType();
        if (!checkSurePdType.isSuccess()) {
            return checkSurePdType;
        }
//        int i = opsAsPdBillDataMapper.selectNullBillType();
//        if (i != 0) {
//            return ResultVo.failure("请先确定盘点形式.");
//        }

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("无有效的盘点数据,请确认");
        }

        if (Integer.parseInt(PdStateEnum.pdBillscz.getCode()) < Integer.parseInt(batchNoWithIsActive.getData().getPdState())) {
            return ResultVo.failure("当前节点是"+PdStateEnum.getCodeNameByCode(batchNoWithIsActive.getData().getPdState())+",不可生成盘点票");
        }

        AtomicReference<String> errMsg = new AtomicReference<>("");

        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }

        String pdBatchNo = batchNoWithIsActive.getData().getPdBatchNo();
        List<String> dataTypeList = new ArrayList<>();
        dataTypeList.add(PdDataTypeEnum.GD.getCode());
        dataTypeList.add(PdDataTypeEnum.ST.getCode());
        dataTypeList.add(PdDataTypeEnum.WT.getCode());
        dataTypeList.add(PdDataTypeEnum.JY.getCode());
        long startAdd = System.currentTimeMillis();
        try {
            log.info("开始执行=> 将wms中间表过渡库位及实体货架库存数据写入到盘点票结果表 以及 委托在库(寄售库存)数据抽取到盘点票结果表\n");
            redisManager.set(CommonConstants.OPS_PD_STATUS,PdRedisStatusEnum.handing.getCode());
            ResultVo<String> resultVo = batchInsertToPdBill(pdBatchNo, loginAuthDto, dataTypeList);
            if (!resultVo.isSuccess()) {
                redisManager.set(CommonConstants.OPS_PD_STATUS,PdRedisStatusEnum.canhand.getCode());
                return resultVo;
            }
            log.info("结束 => 将wms中间表过渡库位,实体货架库存,委托在库数据写入到盘点票结果表耗时: {} s " , (System.currentTimeMillis() - startAdd) /1000);
            Thread.sleep(2000*60);
            // 生成空白票数据
            log.info("开始=> 生成空白票");
            long startCreateBlank = System.currentTimeMillis();
            createBlankBillData(dto,pdBatchNo);
            log.info("结束=> 生成空白票结束耗时: {} s " , (System.currentTimeMillis() - startCreateBlank) /1000);

            log.info("开始=> 生成盘点票号回更盘点票表 -- 现品票.现品空白票,数据票");
            long startCre = System.currentTimeMillis();
            // billCount = createXPPdBillNo(billCount,pdBatchNo);
            createPdBillNoByWarehouseCodeWithXP(pdBatchNo);
            log.info("结束 => 生成盘点票号回更盘点票表 -- 现品票.数据票: {} s " , (System.currentTimeMillis() - startCre) /1000);

            // 生成盘点票号回更盘点票表 -- 到货未入清单票
            log.info("开始=> 生成盘点票号回更盘点票表 -- 到货未入清单票");
            long startUp = System.currentTimeMillis();
            // createArriveNotInBillNo(billCount,pdBatchNo);
            createPdBillNoByWarehouseCodeWittArriveNotIn(pdBatchNo);
            log.info("结束 => 生成盘点票号回更盘点票表 -- 到货未入清单票: {} s " , (System.currentTimeMillis() - startUp) /1000);
            // 更新盘点批次表状态- 盘点票已生成
            pdBatchService.updatePdStateByPdbatchNo(pdBatchNo,PdStateEnum.pdBillysc.getCode(),loginAuthDto.getUserNo());
            redisManager.set(CommonConstants.OPS_PD_STATUS,PdRedisStatusEnum.canhand.getCode());
        } catch (Exception e) {
            redisManager.set(CommonConstants.OPS_PD_STATUS,PdRedisStatusEnum.notHandToArriveNotIn.getCode());
            rockCommitData(pdBatchNo,PdStateEnum.dhsjzb.getCode(),loginAuthDto.getUserNo(),pdBatchNo);
            log.error("票据生成及打印->生成盘点票发生异常{}",e.getMessage(),e);
            errMsg.set("生成盘点票失败,请重新从确认到货未入数据步骤开始 : " + e.getMessage());
        }
        if (StringUtils.isNotBlank(errMsg.get())) {
            return ResultVo.failure(errMsg.get());
        }
        log.info("结束 => 生成盘点票共计耗时: {} s " , (System.currentTimeMillis() - startAdd) /1000);
        return ResultVo.success("盘点票已全部生成,请打印");
    }

    public ResultVo<String> checkSurePdType() {
        LambdaQueryWrapper<OpsAsPdBatchDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsAsPdBatchDO::getIsActive,"1");
        OpsAsPdBatchDO opsAsPdBatchDO = opsAsPdBatchMapper.selectOne(queryWrapper);
        if (opsAsPdBatchDO == null) {
            return ResultVo.failure("当前不存在已激活的盘点批次");
        }
        if (StringUtils.isBlank(opsAsPdBatchDO.getPdDataTypeDhwr()) || StringUtils.isBlank(opsAsPdBatchDO.getPdBillTypeDhwr())) {
            return ResultVo.failure("到货未入未确认盘点形式,请进行确认");
        }
        if (StringUtils.isBlank(opsAsPdBatchDO.getPdBillTypeGd()) || StringUtils.isBlank(opsAsPdBatchDO.getPdDataTypeGd())) {
            return ResultVo.failure("正式过渡库位库存未确认盘点形式,请进行确认");
        }
        if (StringUtils.isBlank(opsAsPdBatchDO.getPdDataTypeJy()) || StringUtils.isBlank(opsAsPdBatchDO.getPdBillTypeJy())) {
            return ResultVo.failure("集约待交接区未确认盘点形式,请进行确认");
        }
        return ResultVo.success("数据确认检查通过");
    }

    @Override
    public ResultVo<String> isCreDhwrBlankBill() {
        int arriveNotInCount = opsAsPdBillDataMapper.findArriveNotInCount();
        if (arriveNotInCount > 0) {
            return ResultVo.success("可生成");
        }
        return ResultVo.failure("暂无到货未入空白票,不可生成");
    }

    @Override
    public ResultVo<String> getPdSureDateType() {
        Object object = redisManager.get(CommonConstants.OPS_PD_DATA_TYPE);
        if (object != null) {
            return ResultVo.success(object.toString());
        }
        return ResultVo.failure("暂无选择盘点形式.");
    }

    public void rockCommitData(String pdBathNo,String code, String optUser,String pdBatchNo) {
        opsAsPdBillDataMapper.clearPdBillData(pdBatchNo);
        pdBatchService.updatePdStateByPdbatchNo(pdBathNo,code,optUser);
    }

    public ResultVo<String> batchInsertToPdBill(String pdBatchNo,LoginUserDTO loginAuthDto,List<String> dataTypeList) {
        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(20000);
        PageInfo<OpsAsWmsInventoryDataDO> pageInfo;
        boolean pdStateFlag = true;
        Date nowDate = new Date();
        while (true) {
            pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> {
                        opsAsWmsInventoryService.selectByDataType(pdBatchNo, dataTypeList);
                    });
            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                log.info("结束 => 无有效的盘点数据,请确认");
                return ResultVo.failure("无有效的盘点数据,请确认");
            }
            if (pdStateFlag) {
                // 更新盘点批次表状态- 盘点票生成中
                pdBatchService.updatePdStateByPdbatchNo(pdBatchNo,PdStateEnum.pdBillscz.getCode(),loginAuthDto.getUserNo());
                pdStateFlag = false;
            }
            List<OpsAsPdBillDataDO> opsAsPdBillDataDOList = new ArrayList<>();
            for (OpsAsWmsInventoryDataDO item : pageInfo.getList()) {
                OpsAsPdBillDataDO billDataDO = conventOpsAsPdBillDataDO(item, loginAuthDto, pdBatchNo, nowDate);
                opsAsPdBillDataDOList.add(billDataDO);
            }

            List<List<OpsAsPdBillDataDO>> arrayListArrayList = ListUtils.partition(opsAsPdBillDataDOList,80);
            for (List<OpsAsPdBillDataDO> item : arrayListArrayList) {
                opsAsPdBillDataMapper.batchInsertPdPillData(item);
            }
            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }
        return ResultVo.success("操作成功");
    }

    // 生成盘点票号回更盘点票表 -- 到货未入清单票
    public int createArriveNotInBillNo(int billCount,String pdBatchNo) {

        /**
         *  现品票.数据票按照仓库代码,盘点顺序.货架号.型号排序
         *  到货未入清单票 按照仓库代码 发票号 拖号 型号 BarCode排序
         *  每个货架最多每20行生成一个盘点票号 更新盘点票号字段
         *  到货未入清单票和到货未入的空白票的票号格式为 仓库代码+发票号+5位连编
         *  实盘票 数据票 实盘空白票的票号格式为仓库代码+盘点批次号+5位连编
         *  各仓到货未入的空白票号必须排在到货未入清单票的后面
         *  实盘空白票的票号必须排在实盘票号的后面
         */
        AtomicInteger billCountNum = new AtomicInteger(billCount);
        // 清单票.到货未入空白票
        List<String> PDbillTypeList = new ArrayList<>();
        PDbillTypeList.add(PdBillTypeEnum.QDBILL.getCode());
        PDbillTypeList.add(PdBillTypeEnum.DHWRBLANKBILL.getCode());
        List<OpsAsPdBillDataDO> xpAndSpBillList =  pdBillService.findPdBillData(PDbillTypeList,pdBatchNo);
        if (CollectionUtils.isEmpty(xpAndSpBillList)) {
            return 1;
        }
        Map<String, List<OpsAsPdBillDataDO>> mapByBillType = new HashMap<>();
        ExecutorService fixedThreadPoolwithHandSelData = Executors.newFixedThreadPool(10);
        try {
            for (OpsAsPdBillDataDO item: xpAndSpBillList) {
                fixedThreadPoolwithHandSelData.execute(() -> {
                    if (StringUtils.isNotBlank(item.getPdBillType())) {
                        if (mapByBillType.containsKey(item.getPdBillType())) {
                            List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = mapByBillType.get(item.getPdBillType());
                            opsAsPdBillDataDOS.add(item);
                            mapByBillType.put(item.getPdBillType(),opsAsPdBillDataDOS);
                        } else {
                            List<OpsAsPdBillDataDO> list = new ArrayList<>();
                            list.add(item);
                            mapByBillType.put(item.getPdBillType(),list);
                        }
                    }
                });
            }
        } finally {
            fixedThreadPoolwithHandSelData.shutdown();
            while (true) {
                if (fixedThreadPoolwithHandSelData.isTerminated()) {
                    break;
                }
            }
        }
        LambdaUpdateWrapper<OpsAsPdBillDataDO> updateWrapper = new LambdaUpdateWrapper<>();
        // ExecutorService fixedThreadPoolwithHandUpData = Executors.newFixedThreadPool(10);
        try {
            for (String key : mapByBillType.keySet()) {
                // fixedThreadPoolwithHandUpData.execute(() -> {
                    if (!key.equals(PdBillTypeEnum.DHWRBLANKBILL.getCode())) {
                        List<OpsAsPdBillDataDO> list = mapByBillType.get(key);
                        if (CollectionUtils.isNotEmpty(list)) {
                            List<List<OpsAsPdBillDataDO>> arrayListArrayList = ListUtils.partition(list,20);
                            for (List<OpsAsPdBillDataDO> item : arrayListArrayList) {
                                for (OpsAsPdBillDataDO i : item ) {
                                    String pdBillNo = creBillNo(i, billCountNum.get());
                                    if (StringUtils.isBlank(pdBillNo)) {
                                        // 到货未入清单票
                                        throw new BusinessException("到货未入清单票生成失败:"+i.getId());
                                    }
                                    updateWrapper = new LambdaUpdateWrapper<>();
                                    updateWrapper
                                            .eq(OpsAsPdBillDataDO::getId,i.getId())
                                            .set(OpsAsPdBillDataDO::getPdBillNo,pdBillNo)
                                            .set(OpsAsPdBillDataDO::getUpdateTime,new Date());
                                    opsAsPdBillDataMapper.update(null,updateWrapper); // 更新生成的盘点票号
                                }
                                billCountNum.getAndIncrement();
                            }
                        }
                    }
                // });
            }
        } finally {
//            fixedThreadPoolwithHandUpData.shutdown();
//            while (true) {
//                if (fixedThreadPoolwithHandUpData.isTerminated()) {
//                    break;
//                }
//            }
        }
        // 紧跟生成到货未入空白票
        List<OpsAsPdBillDataDO> list = mapByBillType.get(PdBillTypeEnum.DHWRBLANKBILL.getCode());
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("未成功生成到货未入空白票");
        }

        // ExecutorService fixedThreadPoolUp = Executors.newFixedThreadPool(10);
        try {
            List<List<OpsAsPdBillDataDO>> arrayListArrayList = ListUtils.partition(list, 20);
            for (List<OpsAsPdBillDataDO> i : arrayListArrayList) {
                // fixedThreadPoolUp.execute(() -> {
                if (CollectionUtils.isNotEmpty(i)) {
                    for (OpsAsPdBillDataDO item : i ) {
                        String pdBillNo = creBillNo(item, billCountNum.get());
                        if (StringUtils.isBlank(pdBillNo)) {
                            // 到货未入清单票
                            throw new BusinessException("到货未入空白票清单票生成失败:"+item.getId());
                        }
                        updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper
                                .eq(OpsAsPdBillDataDO::getId,item.getId())
                                .set(OpsAsPdBillDataDO::getPdBillNo,pdBillNo)
                                .set(OpsAsPdBillDataDO::getUpdateTime,new Date());
                        opsAsPdBillDataMapper.update(null,updateWrapper); // 更新生成的盘点票号
                    }
                }
                    billCountNum.getAndIncrement();
               // });
            }
        } finally {
//            fixedThreadPoolUp.shutdown();
//            while (true) {
//                if (fixedThreadPoolUp.isTerminated()) {
//                    break;
//                }
//            }
        }

        return billCount;
    }


    public void printPdXpBillData(String title,String billType) {

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return;
        }

        // 根据当前当前操作人获取其能打印的仓库
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            throw new BusinessException("当前登录信息过期,请重新登录.");
        }

        if (StringUtils.isBlank(loginAuthDto.getUserNo())) {
            throw new BusinessException("当前登录信息过期,请重新登录.");
        }
        String warehouseCode = getWarehouseConfigInfo(loginAuthDto.getUserNo());
        if (StringUtils.isBlank(warehouseCode)) {
            return;
        }
        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(6000);
        PageInfo<OpsAsPdBillDataDO> pageInfo;

        Map<String,List<OpsAsPdBillDataDO>> map = new HashMap<>();

        while (true) {
            pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> {
                        opsAsPdBillDataMapper.findPdBillDataByBillType(billType,warehouseCode,batchNoWithIsActive.getData().getPdBatchNo());
                    });

            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                return;
            }
            for (OpsAsPdBillDataDO item : pageInfo.getList()) {
                if (StringUtils.isBlank(item.getPdBillNo())) {
                    continue;
                }
                if (map.containsKey(item.getPdBillNo())) {
                    List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = map.get(item.getPdBillNo());
                    opsAsPdBillDataDOS.add(item);
                    map.put(item.getPdBillNo(),opsAsPdBillDataDOS);
                } else {
                    List<OpsAsPdBillDataDO> opsAsPdBillDataDOList = new ArrayList<>();
                    opsAsPdBillDataDOList.add(item);
                    map.put(item.getPdBillNo(),opsAsPdBillDataDOList);
                }
            }

            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        List<String> keyList = new ArrayList<>(map.keySet());
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        keyList = keyList.stream().sorted(String::compareTo).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        FileOutputStream  out = null;
        List<JasperPrint> jasperPrintList = new ArrayList<>();
        String path = System.getProperty("user.dir")+"/"+title+".pdf";
        log.info("现品盘点票输出位置: "+path);
        try {
            out = new FileOutputStream(path);
            int pageNumber = 1;
            for (String key : keyList) {
                if (StringUtils.isBlank(key)) {
                    return;
                }
                List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = map.get(key);
                if (CollectionUtils.isEmpty(opsAsPdBillDataDOS)) {
                    continue;
                }
                List<PrintPdXpBillVO> printPdXpBillVOS = new ArrayList<>();
                PrintPdXpBiilDTO datasurce = new PrintPdXpBiilDTO();
                PrintPdXpBillVO pdXpBillVO;
                for (OpsAsPdBillDataDO  item : opsAsPdBillDataDOS ) {
                    pdXpBillVO = new PrintPdXpBillVO();
                    pdXpBillVO.setShelvesNo(item.getShelvesNo() == null ? "" : item.getShelvesNo());
                    pdXpBillVO.setLocationNo(item.getLocationNo() == null ? "" : item.getLocationNo());
                    pdXpBillVO.setOrderNo(item.getOrderNo() == null ? "" : item.getOrderNo());
                    pdXpBillVO.setFactQty(item.getBillQty() == null ? "0" : String.valueOf(item.getBillQty()));
                    pdXpBillVO.setModelNo(item.getModelNo1() == null ? "" : item.getModelNo1());
                    if (billType.equals(PdBillTypeEnum.SJBILL.getCode())) {
                        if (item.getBillQty() == null) {
                            item.setBillQty(0);
                        }
                        pdXpBillVO.setRemark(String.valueOf(item.getBillQty()));
                        pdXpBillVO.setPdQty(String.valueOf(item.getBillQty()));
                    } else {
                        pdXpBillVO.setRemark("");
                        pdXpBillVO.setPdQty("");
                    }

                    pdXpBillVO.setPdSurePerson("");
                    printPdXpBillVOS.add(pdXpBillVO);
                }
                datasurce.setDetailDataSet(printPdXpBillVOS);
                Map<String, Object> jasperMap = new HashMap<>();
                jasperMap.put("img", CommonConstants.IMG);
                jasperMap.put("title",title);
                jasperMap.put("pageNumber",pageNumber);
                jasperMap.put("pageSize",keyList.size());
                jasperMap.put("pdDate",DateUtil.dateToDateString(new Date()).replace("-","/"));
                jasperMap.put("pdBillNo",opsAsPdBillDataDOS.get(0).getPdBillNo() == null ? "xxxxx" : "NO."+opsAsPdBillDataDOS.get(0).getPdBillNo());
                List<PrintPdXpBiilDTO> printPdXpBiilDTOS = new ArrayList<>(1);
                printPdXpBiilDTOS.add(datasurce);
                InputStream inputStream;
                if (PdBillTypeEnum.SJBILL.getCode().equals(billType)) {
                    inputStream = FileUtil.getTemplate("templates/jasper/pdDataBillPrint.jasper");
                } else {
                    inputStream = FileUtil.getTemplate("templates/jasper/pdXpBillPrint.jasper");
                }

                JasperPrint jasperPrint = JasperHelper.crePdfWithJasperPrint(inputStream, jasperMap, printPdXpBiilDTOS);
                jasperPrintList.add(jasperPrint);
                pageNumber++;
            }
            exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            baos.close();
            out.write(bytes);
            FileUtil.downloadFileToResponse(path,response);
            log.info("打印 end ====>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        } catch (JRException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                File file = new File(path);
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void uploadAllFileForPD() {
        List<String> warehouseCode = new ArrayList<>();
        // master
        List<String> masterWarehouse = opsAsPdBillDataMapper.findWarehouseCodeAndType(WarehouseTypeEnum.RDC.getHouseTypeCode());

        // wt
        List<String> wtWarehouse = opsAsPdBillDataMapper.findWarehouseCodeAndType(WarehouseTypeEnum.WT.getHouseTypeCode());

        // sub
        List<String> subWarehouse = opsAsPdBillDataMapper.findWarehouseCodeAndType(WarehouseTypeEnum.FDC.getHouseTypeCode());

        if (CollectionUtils.isNotEmpty(masterWarehouse)) {
            warehouseCode.addAll(masterWarehouse);
        }
        if (CollectionUtils.isNotEmpty(wtWarehouse)) {
            warehouseCode.addAll(wtWarehouse);
        }
        if (CollectionUtils.isNotEmpty(subWarehouse)) {
            warehouseCode.addAll(subWarehouse);
        }
        if (CollectionUtils.isEmpty(warehouseCode)) {
            return;
        }

        String fileSavePtch = "";
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return;
        }
        String pdBatchNo = batchNoWithIsActive.getData().getPdBatchNo()+"/";

        for (String item: warehouseCode) {
            // 打印现品票
            fileSavePtch = serverPath+CommonConstants.SAVEFILEPATH_PD+pdBatchNo+PdUploadFilePathEnum.xp.getCode();
            printPdXpBillDataToServer("营业盘点现品票--在库",PdBillTypeEnum.XPBILL.getCode(),item+".pdf",fileSavePtch,item);
            // 打印货架实盘空白票
            fileSavePtch = serverPath+CommonConstants.SAVEFILEPATH_PD+pdBatchNo+PdUploadFilePathEnum.hjpdkbp.getCode();
            printPdXpBillDataToServer("营业盘点现品票--手写票",PdBillTypeEnum.XPBLANKBILL.getCode(),item+".pdf",fileSavePtch,item);
            // 到货未入空白票
            fileSavePtch = serverPath+CommonConstants.SAVEFILEPATH_PD+pdBatchNo+PdUploadFilePathEnum.dhwrkbp.getCode();
            printPdXpBillDataToServer("营业盘点现品票--手写票",PdBillTypeEnum.DHWRBLANKBILL.getCode(),item+".pdf",fileSavePtch,item);
            // 立会票
            fileSavePtch = serverPath+CommonConstants.SAVEFILEPATH_PD+pdBatchNo+PdUploadFilePathEnum.lh.getCode();
            printLhBillDataWithPdfToServer("营业盘点立会票--在库",PdBillTypeEnum.XPBILL.getCode(),item+".pdf",fileSavePtch,item);
            // 数据票
            fileSavePtch = serverPath+CommonConstants.SAVEFILEPATH_PD+pdBatchNo+PdUploadFilePathEnum.sj.getCode();
            printPdXpBillDataToServer("营业数据盘点票",PdBillTypeEnum.SJBILL.getCode(),item+".pdf",fileSavePtch,item);

            // 到货未入清单票
            fileSavePtch = serverPath+CommonConstants.SAVEFILEPATH_PD+pdBatchNo+PdUploadFilePathEnum.dhwrqdp.getCode();
            printPdXpBillDataToServer("清单盘点票",PdBillTypeEnum.XPBLANKBILL.getCode(),item+".pdf",fileSavePtch,item);
            // 寄售库存盘点票
            fileSavePtch = serverPath+CommonConstants.SAVEFILEPATH_PD+pdBatchNo+PdUploadFilePathEnum.wt.getCode();
            printPdXpBillDataToServer(item,PdBillTypeEnum.XPBLANKBILL.getCode(),item+".pdf",fileSavePtch,item);
        }
    }

    @Override
    public void printPdXpBillDataToServer(String title, String billType,String fileName,String fileSavePath,String warehouse) {

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return;
        }

        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(6000);
        PageInfo<OpsAsPdBillDataDO> pageInfo;
        String warehouseCode = warehouse;

        Map<String,List<OpsAsPdBillDataDO>> map = new HashMap<>();

        while (true) {
            pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> {
                        opsAsPdBillDataMapper.findPdBillDataByBillType(billType,warehouseCode,batchNoWithIsActive.getData().getPdBatchNo());
                    });

            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                return;
            }
            for (OpsAsPdBillDataDO item : pageInfo.getList()) {
                if (StringUtils.isBlank(item.getPdBillNo())) {
                    continue;
                }
                if (map.containsKey(item.getPdBillNo())) {
                    List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = map.get(item.getPdBillNo());
                    opsAsPdBillDataDOS.add(item);
                    map.put(item.getPdBillNo(),opsAsPdBillDataDOS);
                } else {
                    List<OpsAsPdBillDataDO> opsAsPdBillDataDOList = new ArrayList<>();
                    opsAsPdBillDataDOList.add(item);
                    map.put(item.getPdBillNo(),opsAsPdBillDataDOList);
                }
            }

            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        List<String> keyList = new ArrayList<>(map.keySet());
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        keyList = keyList.stream().sorted(String::compareTo).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        FileOutputStream  out = null;
        List<JasperPrint> jasperPrintList = new ArrayList<>();
        String path = System.getProperty("user.dir")+"/"+title+".pdf";
        log.info("现品盘点票输出位置: "+fileSavePath);
        try {
            out = new FileOutputStream(path);
            int pageNumber = 1;
            for (String key : keyList) {
                if (StringUtils.isBlank(key)) {
                    return;
                }
                List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = map.get(key);
                if (CollectionUtils.isEmpty(opsAsPdBillDataDOS)) {
                    continue;
                }
                List<PrintPdXpBillVO> printPdXpBillVOS = new ArrayList<>();
                PrintPdXpBiilDTO datasurce = new PrintPdXpBiilDTO();
                PrintPdXpBillVO pdXpBillVO;
                for (OpsAsPdBillDataDO  item : opsAsPdBillDataDOS ) {
                    pdXpBillVO = new PrintPdXpBillVO();
                    pdXpBillVO.setShelvesNo(item.getShelvesNo() == null ? "" : item.getShelvesNo());
                    pdXpBillVO.setLocationNo(item.getLocationNo() == null ? "" : item.getLocationNo());
                    pdXpBillVO.setModelNo(item.getModelNo1() == null ? "" : item.getModelNo1());
                    if (billType.equals(PdBillTypeEnum.SJBILL.getCode())) {
                        if (item.getBillQty() == null) {
                            item.setBillQty(0);
                        }
                        pdXpBillVO.setRemark(String.valueOf(item.getBillQty()));
                        pdXpBillVO.setPdQty(String.valueOf(item.getBillQty()));
                    } else {
                        pdXpBillVO.setRemark("");
                        pdXpBillVO.setPdQty("");
                    }

                    pdXpBillVO.setPdSurePerson("");
                    printPdXpBillVOS.add(pdXpBillVO);
                }
                datasurce.setDetailDataSet(printPdXpBillVOS);
                Map<String, Object> jasperMap = new HashMap<>();
                jasperMap.put("img", CommonConstants.IMG);
                jasperMap.put("title",title);
                jasperMap.put("pageNumber",pageNumber);
                jasperMap.put("pageSize",keyList.size());
                jasperMap.put("pdDate",DateUtil.dateToDateString(new Date()).replace("-","/"));
                jasperMap.put("pdBillNo",opsAsPdBillDataDOS.get(0).getPdBillNo() == null ? "xxxxx" : "NO."+opsAsPdBillDataDOS.get(0).getPdBillNo());
                List<PrintPdXpBiilDTO> printPdXpBiilDTOS = new ArrayList<>(1);
                printPdXpBiilDTOS.add(datasurce);
                InputStream inputStream = FileUtil.getTemplate("templates/jasper/pdXpBillPrint.jasper");
                JasperPrint jasperPrint = JasperHelper.crePdfWithJasperPrint(inputStream, jasperMap, printPdXpBiilDTOS);
                jasperPrintList.add(jasperPrint);
                pageNumber++;
            }
            exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            baos.close();
            out.write(bytes);
//            FileUtil.downloadFileToResponse(path,response);
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            FileUtil.uploadFileWithStream(fileInputStream,fileSavePath,fileName);
            log.info("打印 {} {} end ====>>>>>>>>>>>>>>>>>>>>>>>>>> ",fileSavePath,fileName);
        } catch (JRException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                File file = new File(path);
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void printLhBillDataWithPdfToServer(String title, String billType, String fileName, String fileSavePath, String warehouse) {
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return;
        }

        String warehouseCode = warehouse;
        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(6000);
        PageInfo<OpsAsPdBillDataDO> pageInfo;

        Map<String,List<OpsAsPdBillDataDO>> map = new HashMap<>();

        while (true) {
            pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> {
                        opsAsPdBillDataMapper.findPdBillDataByBillType(billType,warehouseCode,batchNoWithIsActive.getData().getPdBatchNo());
                    });

            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                return;
            }
            for (OpsAsPdBillDataDO item : pageInfo.getList()) {
                if (StringUtils.isBlank(item.getPdBillNo())) {
                    continue;
                }
                if (map.containsKey(item.getPdBillNo())) {
                    List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = map.get(item.getPdBillNo());
                    opsAsPdBillDataDOS.add(item);
                    map.put(item.getPdBillNo(),opsAsPdBillDataDOS);
                } else {
                    List<OpsAsPdBillDataDO> opsAsPdBillDataDOList = new ArrayList<>();
                    opsAsPdBillDataDOList.add(item);
                    map.put(item.getPdBillNo(),opsAsPdBillDataDOList);
                }
            }

            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        List<String> keyList = new ArrayList<>(map.keySet());
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        keyList = keyList.stream().sorted(String::compareTo).collect(Collectors.toList());
        FileOutputStream  out = null;
        List<JasperPrint> jasperPrintList = new ArrayList<>();
        String path = System.getProperty("user.dir")+"/"+title+".pdf";
        log.info("例会盘点票输出位置: "+fileSavePath);
        try {
            out = new FileOutputStream(path);
            int pageNumber = 1;
            for (String key : keyList) {
                List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = map.get(key);
                if (CollectionUtils.isEmpty(opsAsPdBillDataDOS)) {
                    continue;
                }
                List<PrintLhBillVO> printPdXpBillVOS = new ArrayList<>();
                PrintLhBillDTO datasurce = new PrintLhBillDTO();
                PrintLhBillVO printLhBillVO;
                for (OpsAsPdBillDataDO  item : opsAsPdBillDataDOS ) {
                    printLhBillVO = new PrintLhBillVO();
                    printLhBillVO.setShelvesNo(item.getShelvesNo() == null ? "" : item.getShelvesNo());
                    printLhBillVO.setLocationNo(item.getLocationNo() == null ? "" : item.getLocationNo());
                    printLhBillVO.setModelNo(item.getModelNo1() == null ? "" : item.getModelNo1());
                    printLhBillVO.setAgainSureQty("");
                    printLhBillVO.setBillQty((item.getBillQty() == null || item.getBillQty() == 0) ? "" : String.valueOf(item.getBillQty()));
                    printPdXpBillVOS.add(printLhBillVO);
                }
                datasurce.setDetailDataSet(printPdXpBillVOS);
                Map<String, Object> jasperMap = new HashMap<>();
                jasperMap.put("img", CommonConstants.IMG);
                jasperMap.put("title",title);
                jasperMap.put("pageNumber",pageNumber);
                jasperMap.put("pageSize",keyList.size());
                jasperMap.put("pdDate",DateUtil.dateToDateString(new Date()).replace("-","/"));
                jasperMap.put("pdBillNo",opsAsPdBillDataDOS.get(0).getPdBillNo() == null ? "xxxxx" : "NO."+opsAsPdBillDataDOS.get(0).getPdBillNo());
                List<PrintLhBillDTO> printPdXpBiilDTOS = new ArrayList<>(1);
                printPdXpBiilDTOS.add(datasurce);
                InputStream inputStream = FileUtil.getTemplate("templates/jasper/pdXpLhBillPrint.jasper");
                JasperPrint jasperPrint = JasperHelper.crePdfWithJasperPrint(inputStream, jasperMap, printPdXpBiilDTOS);
                jasperPrintList.add(jasperPrint);
                pageNumber++;
            }
            exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            baos.close();
            out.write(bytes);
//            FileUtil.downloadFileToResponse(path,response);
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            FileUtil.uploadFileWithStream(fileInputStream,fileSavePath,fileName);
            log.info("打印 {} {} end ====>>>>>>>>>>>>>>>>>>>>>>>>>> ",fileSavePath,fileName);
        } catch (JRException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                File file = new File(path);
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void printPdArriveNotInWithPdfToServer(String title, String billType, String fileName, String fileSavePath, String warehouse) {

    }

    @Override
    public ResultVo<List<BillPrintShowVO>>  findModelNoCount(String dataType) {

        List<BillPrintShowVO> list = new ArrayList<>();

        ResultVo<List<BillPrintShowVO>> modelNoCountByMaster = findModelNoCountByMaster(dataType);
        if (modelNoCountByMaster.isSuccess() && CollectionUtils.isNotEmpty(modelNoCountByMaster.getData())) {
            list.addAll(modelNoCountByMaster.getData());
        }
        ResultVo<List<BillPrintShowVO>> modelNoCountBySUB = findModelNoCountBySUB(dataType);

        if (modelNoCountBySUB.isSuccess() && CollectionUtils.isNotEmpty(modelNoCountBySUB.getData())) {
            list.addAll(modelNoCountBySUB.getData());
        }

        ResultVo<List<BillPrintShowVO>> modelNoCountByWT = findModelNoCountByWT(dataType);

        if (modelNoCountByWT.isSuccess() && CollectionUtils.isNotEmpty(modelNoCountByWT.getData())) {
            list.addAll(modelNoCountByWT.getData());
        }

        return ResultVo.success(list);
    }

    @Override
    public ResultVo<List<BillPrintShowVO>>  findModelNoCountByMaster(String dataType) {
        List<String> pdDataTypeList = new ArrayList<>();
        if(StringUtils.isNotBlank(dataType)) {
            switch (dataType) {
                case "1" :
                    pdDataTypeList.add(PdDataTypeEnum.CG.getCode());
                    pdDataTypeList.add(PdDataTypeEnum.DB.getCode());
                    pdDataTypeList.add(PdDataTypeEnum.TH.getCode());
                    pdDataTypeList.add(PdDataTypeEnum.ZHDHWR.getCode());
                    break;
                case "2" :
                    pdDataTypeList.add(PdDataTypeEnum.GD.getCode());
                    break;
                case "3" :
                    pdDataTypeList.add(PdDataTypeEnum.JY.getCode());
                    break;
            }
        }

        List<BillPrintShowVO> list = new ArrayList<>();
        List<BillPrintShowVO> tempList = new ArrayList<>();

        List<String> warehouseCodes = opsAsPdBillDataMapper.findWarehouseCodeAndType(WarehouseTypeEnum.RDC.getHouseTypeCode());

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当前盘点批次号失败");
        }

        String pdBatchNo = batchNoWithIsActive.getData().getPdBatchNo();

        warehouseCodes.parallelStream().forEach(item -> {
            BillPrintShowVO  billPrintShowVO = new BillPrintShowVO();
            billPrintShowVO.setWarehouseCode(item);
            //库房名称
            billPrintShowVO.setWarehouseCodeName("["+item+"]"+opsCommonService.getWarehouseNameByCode(item));
            int modelTypeCount = opsAsPdBillDataMapper.findModelTypeCount(item,pdBatchNo,pdDataTypeList);
            if(modelTypeCount > 0) {
                // 预计盘点型号种类数
                billPrintShowVO.setModelTypeCount(modelTypeCount);
                // 预计盘点型号总数量
                billPrintShowVO.setModelNoQtyCount(opsAsPdBillDataMapper.findModelQtyCount(item,pdBatchNo,pdDataTypeList));
                // 预计现品盘型号总数量
                billPrintShowVO.setXpModelNoCount(opsAsPdBillDataMapper.findXpPdCount(item,pdBatchNo,pdDataTypeList));
                // 正式过渡库位型号总数量
                billPrintShowVO.setGdModelNoCount(opsAsPdBillDataMapper.findGdModelNoCount(item,pdBatchNo,pdDataTypeList));
                // 集约待交接区型号总数量
                billPrintShowVO.setJyModelNoCount(opsAsPdBillDataMapper.findJyModelNoCount(item,pdBatchNo,pdDataTypeList));
                // 预计生成现品票
                billPrintShowVO.setCreXPBillCount(opsAsPdBillDataMapper.creXpBillCount(item,pdBatchNo,pdDataTypeList));
                // 预计生成立会票
                billPrintShowVO.setCreLhBillCount(billPrintShowVO.getCreXPBillCount());
                // 预计生成数据票
                billPrintShowVO.setCreDataBillCount(opsAsPdBillDataMapper.creDataBillCount(item,pdBatchNo,pdDataTypeList));
                // 预计生成到货未入清单票
                billPrintShowVO.setCreArrveNotInCount(opsAsPdBillDataMapper.creArriveNotInBillCount(item,pdBatchNo,pdDataTypeList));
                list.add(billPrintShowVO);
            }
        });
        tempList = list;
        tempList = tempList.stream().sorted(Comparator.comparing(BillPrintShowVO::getWarehouseCode)).collect(Collectors.toList());
        return ResultVo.success(tempList);
    }
    @Override
    public ResultVo<List<BillPrintShowVO>>  findModelNoCountBySUB(String dataType) {

        List<String> pdDataTypeList = new ArrayList<>();
        if(StringUtils.isNotBlank(dataType)) {
            switch (dataType) {
                case "1" :
                    pdDataTypeList.add(PdDataTypeEnum.CG.getCode());
                    pdDataTypeList.add(PdDataTypeEnum.DB.getCode());
                    pdDataTypeList.add(PdDataTypeEnum.TH.getCode());
                    pdDataTypeList.add(PdDataTypeEnum.ZHDHWR.getCode());
                    break;
                case "2" :
                    pdDataTypeList.add(PdDataTypeEnum.GD.getCode());
                    break;
                case "3" :
                    pdDataTypeList.add(PdDataTypeEnum.JY.getCode());
                    break;
            }
        }

        List<BillPrintShowVO> list = new ArrayList<>();
        List<BillPrintShowVO> tempList = new ArrayList<>();
        // List<String> warehouseCodes = opsAsPdBillDataMapper.findWarehouseCode();

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当前盘点批次号失败");
        }
        String pdBatchNo = batchNoWithIsActive.getData().getPdBatchNo();
        List<String> warehouseCodes = opsAsPdBillDataMapper.findWarehouseCodeAndType(WarehouseTypeEnum.FDC.getHouseTypeCode());
        warehouseCodes.parallelStream().forEach(item -> {
            BillPrintShowVO  billPrintShowVO = new BillPrintShowVO();
            billPrintShowVO.setWarehouseCode(item);
            //库房名称
            billPrintShowVO.setWarehouseCodeName("["+item+"]"+opsCommonService.getWarehouseNameByCode(item));
            int modelTypeCount = opsAsPdBillDataMapper.findModelTypeCount(item,pdBatchNo,pdDataTypeList);
            if(modelTypeCount > 0) {
                // 预计盘点型号种类数
                billPrintShowVO.setModelTypeCount(modelTypeCount);
                // 预计盘点型号总数量
                billPrintShowVO.setModelNoQtyCount(opsAsPdBillDataMapper.findModelQtyCount(item,pdBatchNo,pdDataTypeList));
                // 预计现品盘型号总数量
                billPrintShowVO.setXpModelNoCount(opsAsPdBillDataMapper.findXpPdCount(item,pdBatchNo,pdDataTypeList));
                // 正式过渡库位型号总数量
                billPrintShowVO.setGdModelNoCount(opsAsPdBillDataMapper.findGdModelNoCount(item,pdBatchNo,pdDataTypeList));
                // 集约待交接区型号总数量
                billPrintShowVO.setJyModelNoCount(opsAsPdBillDataMapper.findJyModelNoCount(item,pdBatchNo,pdDataTypeList));
                // 预计生成现品票
                billPrintShowVO.setCreXPBillCount(opsAsPdBillDataMapper.creXpBillCount(item,pdBatchNo,pdDataTypeList));
                // 预计生成立会票
                billPrintShowVO.setCreLhBillCount(billPrintShowVO.getCreXPBillCount());
                // 预计生成数据票
                billPrintShowVO.setCreDataBillCount(opsAsPdBillDataMapper.creDataBillCount(item,pdBatchNo,pdDataTypeList));
                // 预计生成到货未入清单票
                billPrintShowVO.setCreArrveNotInCount(opsAsPdBillDataMapper.creArriveNotInBillCount(item,pdBatchNo,pdDataTypeList));
                list.add(billPrintShowVO);
            }
        });
        tempList = list;
        tempList = tempList.stream().sorted(Comparator.comparing(BillPrintShowVO::getWarehouseCode)).collect(Collectors.toList());
        return ResultVo.success(tempList);
    }
    @Override
    public ResultVo<List<BillPrintShowVO>>  findModelNoCountByWT(String dataType) {

        List<String> pdDataTypeList = new ArrayList<>();
        if(StringUtils.isNotBlank(dataType)) {
            switch (dataType) {
                case "1" :
                    pdDataTypeList.add(PdDataTypeEnum.CG.getCode());
                    pdDataTypeList.add(PdDataTypeEnum.DB.getCode());
                    pdDataTypeList.add(PdDataTypeEnum.TH.getCode());
                    pdDataTypeList.add(PdDataTypeEnum.ZHDHWR.getCode());
                    break;
                case "2" :
                    pdDataTypeList.add(PdDataTypeEnum.GD.getCode());
                    break;
                case "3" :
                    pdDataTypeList.add(PdDataTypeEnum.JY.getCode());
                    break;
            }
        }

        List<BillPrintShowVO> list = new ArrayList<>();
        List<BillPrintShowVO> tempList = new ArrayList<>();
        // List<String> warehouseCodes = opsAsPdBillDataMapper.findWarehouseCode();

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当前盘点批次号失败");
        }
        String pdBatchNo = batchNoWithIsActive.getData().getPdBatchNo();

        List<String> warehouseCodes = opsAsPdBillDataMapper.findWarehouseCodeAndType(WarehouseTypeEnum.WT.getHouseTypeCode());
        warehouseCodes.parallelStream().forEach(item -> {
            BillPrintShowVO  billPrintShowVO = new BillPrintShowVO();
            billPrintShowVO.setWarehouseCode(item);
            //库房名称
            billPrintShowVO.setWarehouseCodeName("["+item+"]"+opsCommonService.getWarehouseNameByCode(item));
            int modelTypeCount = opsAsPdBillDataMapper.findModelTypeCount(item,pdBatchNo,pdDataTypeList);
            if(modelTypeCount > 0) {
                // 预计盘点型号种类数
                billPrintShowVO.setModelTypeCount(modelTypeCount);
                // 预计盘点型号总数量
                billPrintShowVO.setModelNoQtyCount(opsAsPdBillDataMapper.findModelQtyCount(item,pdBatchNo,pdDataTypeList));
                // 预计现品盘型号总数量
                billPrintShowVO.setXpModelNoCount(opsAsPdBillDataMapper.findXpPdCount(item,pdBatchNo,pdDataTypeList));
                // 正式过渡库位型号总数量
                billPrintShowVO.setGdModelNoCount(opsAsPdBillDataMapper.findGdModelNoCount(item,pdBatchNo,pdDataTypeList));
                // 集约待交接区型号总数量
                billPrintShowVO.setJyModelNoCount(opsAsPdBillDataMapper.findJyModelNoCount(item,pdBatchNo,pdDataTypeList));
                // 预计生成现品票
                billPrintShowVO.setCreXPBillCount(opsAsPdBillDataMapper.creXpBillCount(item,pdBatchNo,pdDataTypeList));
                // 预计生成立会票
                billPrintShowVO.setCreLhBillCount(billPrintShowVO.getCreXPBillCount());
                // 预计生成数据票
                billPrintShowVO.setCreDataBillCount(opsAsPdBillDataMapper.creDataBillCount(item,pdBatchNo,pdDataTypeList));
                // 预计生成到货未入清单票
                billPrintShowVO.setCreArrveNotInCount(opsAsPdBillDataMapper.creArriveNotInBillCount(item,pdBatchNo,pdDataTypeList));
                list.add(billPrintShowVO);
            }
        });
        tempList = list;
        tempList = tempList.stream().sorted(Comparator.comparing(BillPrintShowVO::getWarehouseCode)).collect(Collectors.toList());
        return ResultVo.success(tempList);
    }

    @Override
    public void printLhBillDataWithPdf(String title, String billType) {

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return;
        }

        // 根据当前当前操作人获取其能打印的仓库
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            throw new BusinessException("当前登录信息过期,请重新登录.");
        }

        if (StringUtils.isBlank(loginAuthDto.getUserNo())) {
            throw new BusinessException("当前登录信息过期,请重新登录.");
        }
        String warehouseCode = getWarehouseConfigInfo(loginAuthDto.getUserNo());
        if (StringUtils.isBlank(warehouseCode)) {
            return;
        }

        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(6000);
        PageInfo<OpsAsPdBillDataDO> pageInfo;

        Map<String,List<OpsAsPdBillDataDO>> map = new HashMap<>();

        while (true) {
            pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> {
                        opsAsPdBillDataMapper.findPdBillDataByBillType(billType,warehouseCode,batchNoWithIsActive.getData().getPdBatchNo());
                    });

            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                return;
            }
            for (OpsAsPdBillDataDO item : pageInfo.getList()) {
                if (StringUtils.isBlank(item.getPdBillNo())) {
                    continue;
                }
                if (map.containsKey(item.getPdBillNo())) {
                    List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = map.get(item.getPdBillNo());
                    opsAsPdBillDataDOS.add(item);
                    map.put(item.getPdBillNo(),opsAsPdBillDataDOS);
                } else {
                    List<OpsAsPdBillDataDO> opsAsPdBillDataDOList = new ArrayList<>();
                    opsAsPdBillDataDOList.add(item);
                    map.put(item.getPdBillNo(),opsAsPdBillDataDOList);
                }
            }

            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        List<String> keyList = new ArrayList<>(map.keySet());
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        keyList = keyList.stream().sorted(String::compareTo).collect(Collectors.toList());
        FileOutputStream  out = null;
        List<JasperPrint> jasperPrintList = new ArrayList<>();
        String path = System.getProperty("user.dir")+"/"+title+".pdf";
        log.info("例会盘点票输出位置: "+path);
        try {
            out = new FileOutputStream(path);
            int pageNumber = 1;
            for (String key : keyList) {
                List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = map.get(key);
                if (CollectionUtils.isEmpty(opsAsPdBillDataDOS)) {
                    continue;
                }
                List<PrintLhBillVO> printPdXpBillVOS = new ArrayList<>();
                PrintLhBillDTO datasurce = new PrintLhBillDTO();
                PrintLhBillVO printLhBillVO;
                for (OpsAsPdBillDataDO  item : opsAsPdBillDataDOS ) {
                    printLhBillVO = new PrintLhBillVO();
                    printLhBillVO.setShelvesNo(item.getShelvesNo() == null ? "" : item.getShelvesNo());
                    printLhBillVO.setLocationNo(item.getLocationNo() == null ? "" : item.getLocationNo());
                    printLhBillVO.setModelNo(item.getModelNo1() == null ? "" : item.getModelNo1());
                    printLhBillVO.setAgainSureQty("");
                    printLhBillVO.setBillQty((item.getBillQty() == null || item.getBillQty() == 0) ? "" : String.valueOf(item.getBillQty()));
                    printPdXpBillVOS.add(printLhBillVO);
                }
                datasurce.setDetailDataSet(printPdXpBillVOS);
                Map<String, Object> jasperMap = new HashMap<>();
                jasperMap.put("img", CommonConstants.IMG);
                jasperMap.put("title",title);
                jasperMap.put("pageNumber",pageNumber);
                jasperMap.put("pageSize",keyList.size());
                jasperMap.put("pdDate",DateUtil.dateToDateString(new Date()).replace("-","/"));
                jasperMap.put("pdBillNo",opsAsPdBillDataDOS.get(0).getPdBillNo() == null ? "xxxxx" : "NO."+opsAsPdBillDataDOS.get(0).getPdBillNo());
                List<PrintLhBillDTO> printPdXpBiilDTOS = new ArrayList<>(1);
                printPdXpBiilDTOS.add(datasurce);
                InputStream inputStream = FileUtil.getTemplate("templates/jasper/pdXpLhBillPrint.jasper");
                JasperPrint jasperPrint = JasperHelper.crePdfWithJasperPrint(inputStream, jasperMap, printPdXpBiilDTOS);
                jasperPrintList.add(jasperPrint);
                pageNumber++;
            }
            exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            baos.close();
            out.write(bytes);
            FileUtil.downloadFileToResponse(path,response);
            log.info("打印 end ====>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        } catch (JRException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                File file = new File(path);
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void printPdArriveNotInWithPdf(String title, String billType) {
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return;
        }

        // 根据当前当前操作人获取其能打印的仓库
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            throw new BusinessException("当前登录信息过期,请重新登录.");
        }

        if (StringUtils.isBlank(loginAuthDto.getUserNo())) {
            throw new BusinessException("当前登录信息过期,请重新登录.");
        }
        String warehouseCode = getWarehouseConfigInfo(loginAuthDto.getUserNo());
        if (StringUtils.isBlank(warehouseCode)) {
            return;
        }

        LambdaQueryWrapper<OpsAsPdBillDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsAsPdBillDataDO::getPdBillType,billType);
        queryWrapper.eq(OpsAsPdBillDataDO::getPdBatchNo,batchNoWithIsActive.getData().getPdBatchNo());
        queryWrapper.eq(OpsAsPdBillDataDO::getWarehouseCode,warehouseCode);
        queryWrapper.orderByAsc(OpsAsPdBillDataDO::getPdBillNo)
                    .orderByAsc(OpsAsPdBillDataDO::getInvoiceNo)
                    .orderByAsc(OpsAsPdBillDataDO::getCaseNo)
                    .orderByAsc(OpsAsPdBillDataDO::getModelNo1)
                    .orderByAsc(OpsAsPdBillDataDO::getBarcode);
        List<OpsAsPdBillDataDO> list = opsAsPdBillDataMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Map<String,List<OpsAsPdBillDataDO>> map = new HashMap<>();
        for(OpsAsPdBillDataDO item : list) {
            if (StringUtils.isBlank(item.getPdBillNo())) {
                continue;
            }
            if (map.containsKey(item.getPdBillNo())) {
                List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = map.get(item.getPdBillNo());
                opsAsPdBillDataDOS.add(item);
                map.put(item.getPdBillNo(),opsAsPdBillDataDOS);
            } else {
                List<OpsAsPdBillDataDO> opsAsPdBillDataDOList = new ArrayList<>();
                opsAsPdBillDataDOList.add(item);
                map.put(item.getPdBillNo(),opsAsPdBillDataDOList);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        List<String> keyList = new ArrayList<>(map.keySet());
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        keyList = keyList.stream().sorted(String::compareTo).collect(Collectors.toList());
        FileOutputStream  out = null;
        List<JasperPrint> jasperPrintList = new ArrayList<>();
        String path = System.getProperty("user.dir")+"/"+title+".pdf";
        log.info("到货未入盘点票输出位置: "+path);
        try {
            out = new FileOutputStream(path);
            int pageNumber = 1;
            for (String key : keyList) {
                List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = map.get(key);
                if (CollectionUtils.isEmpty(opsAsPdBillDataDOS)) {
                    continue;
                }
                List<PrintArriveNotInBillVO> printPdXpBillVOS = new ArrayList<>();
                PrintArriveNotInBillDTO datasurce = new PrintArriveNotInBillDTO();
                PrintArriveNotInBillVO arriveNotInBillVO;
                for (OpsAsPdBillDataDO  item : opsAsPdBillDataDOS ) {
                    arriveNotInBillVO = new PrintArriveNotInBillVO();
                    arriveNotInBillVO.setInvoiceNo(item.getInvoiceNo() == null ? "" : item.getInvoiceNo());
                    arriveNotInBillVO.setCaseNo(item.getCaseNo() == null ? "" : item.getCaseNo());
                    arriveNotInBillVO.setModelNo(item.getModelNo1() == null ? "" : item.getModelNo1());
                    arriveNotInBillVO.setQty((item.getBillQty() == null || item.getBillQty() == 0) ? "" : String.valueOf(item.getBillQty()));
                    arriveNotInBillVO.setBarcode(item.getBarcode() == null ? "" : item.getBarcode());
                    arriveNotInBillVO.setCreateTime(item.getCreateTime() == null ? "" : DateUtil.dateToDateString(item.getCreateTime()));
                    printPdXpBillVOS.add(arriveNotInBillVO);
                }
                datasurce.setDetailDataSet(printPdXpBillVOS);
                Map<String, Object> jasperMap = new HashMap<>();
                jasperMap.put("img", CommonConstants.IMG);
                jasperMap.put("title",title);
                jasperMap.put("pageNumber",pageNumber);
                jasperMap.put("pageSize",keyList.size());
                jasperMap.put("pdDate",DateUtil.dateToDateString(new Date()).replace("-","/"));
                jasperMap.put("pdBillNo",opsAsPdBillDataDOS.get(0).getPdBillNo() == null ? "xxxxx" : "NO."+opsAsPdBillDataDOS.get(0).getPdBillNo());
                List<PrintArriveNotInBillDTO> printPdXpBiilDTOS = new ArrayList<>(1);
                printPdXpBiilDTOS.add(datasurce);
                InputStream inputStream = FileUtil.getTemplate("templates/jasper/pdArriveNotInBillPrint.jasper");
                JasperPrint jasperPrint = JasperHelper.crePdfWithJasperPrint(inputStream, jasperMap, printPdXpBiilDTOS);
                jasperPrintList.add(jasperPrint);
                pageNumber++;
            }
            exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            baos.close();
            out.write(bytes);
            FileUtil.downloadFileToResponse(path,response);
            log.info("打印 end ====>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        } catch (JRException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                File file = new File(path);
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void printWtBillDataWithPdf(String warehouseCode) {
        if (StringUtils.isBlank(warehouseCode)) {
            return;
        }
        String warehouseNameByCode = opsCommonService.getWarehouseNameByCode(warehouseCode);
        LambdaQueryWrapper<OpsAsPdBillDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsAsPdBillDataDO::getPdDataType,PdDataTypeEnum.WT.getCode());
        queryWrapper.eq(OpsAsPdBillDataDO::getWarehouseCode,warehouseCode);
        queryWrapper.orderByAsc(OpsAsPdBillDataDO::getPdBillNo);
        List<OpsAsPdBillDataDO> list = opsAsPdBillDataMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Map<String,List<OpsAsPdBillDataDO>> map = new HashMap<>();
        for(OpsAsPdBillDataDO item : list) {
            if (StringUtils.isBlank(item.getPdBillNo())) {
                continue;
            }
            if (map.containsKey(item.getPdBillNo())) {
                List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = map.get(item.getPdBillNo());
                opsAsPdBillDataDOS.add(item);
                map.put(item.getPdBillNo(),opsAsPdBillDataDOS);
            } else {
                List<OpsAsPdBillDataDO> opsAsPdBillDataDOList = new ArrayList<>();
                opsAsPdBillDataDOList.add(item);
                map.put(item.getPdBillNo(),opsAsPdBillDataDOList);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        List<String> keyList = new ArrayList<>(map.keySet());
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        keyList = keyList.stream().sorted(String::compareTo).collect(Collectors.toList());
        FileOutputStream  out = null;
        List<JasperPrint> jasperPrintList = new ArrayList<>();
        String path = System.getProperty("user.dir")+"/"+warehouseCode+".pdf";
        log.info("委托在库盘点票输出位置: "+path);
        try {
            out = new FileOutputStream(path);
            int pageNumber = 1;
            for (String key : keyList) {
                List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = map.get(key);
                if (CollectionUtils.isEmpty(opsAsPdBillDataDOS)) {
                    continue;
                }
                List<PrintWtBillDataVO> printPdXpBillVOS = new ArrayList<>();
                PrintWtBillDataDTO datasurce = new PrintWtBillDataDTO();
                PrintWtBillDataVO printWtBillDataVO;
                for (OpsAsPdBillDataDO  item : opsAsPdBillDataDOS ) {
                    printWtBillDataVO = new PrintWtBillDataVO();
                    printWtBillDataVO.setShelvesNo(item.getShelvesNo() == null ? "" : item.getShelvesNo());
                    printWtBillDataVO.setBillQty(( item.getBillQty() == null || item.getBillQty() == 0 ) ? "" : String.valueOf(item.getBillQty()));
                    printWtBillDataVO.setModelNo(item.getModelNo1() == null ? "" : item.getModelNo1());
                    printWtBillDataVO.setQty("");
                    printWtBillDataVO.setSureQty("");
                    printWtBillDataVO.setRemark("");
                    printPdXpBillVOS.add(printWtBillDataVO);
                }
                datasurce.setDetailDataSet(printPdXpBillVOS);
                Map<String, Object> jasperMap = new HashMap<>();
                jasperMap.put("pdDate", DateUtil.dateToDateString(new Date()));
                jasperMap.put("pdBillNo",opsAsPdBillDataDOS.get(0).getPdBillNo() == null ? "xxxxx" : "NO."+opsAsPdBillDataDOS.get(0).getPdBillNo());
                jasperMap.put("warehouseName","客户:"+warehouseNameByCode);
                jasperMap.put("pageNumber",pageNumber);
                jasperMap.put("pageSize",keyList.size());
                List<PrintWtBillDataDTO> printPdXpBiilDTOS = new ArrayList<>(1);
                printPdXpBiilDTOS.add(datasurce);
                InputStream inputStream = FileUtil.getTemplate("templates/jasper/PDWtBillDataPrint.jasper");
                JasperPrint jasperPrint = JasperHelper.crePdfWithJasperPrint(inputStream, jasperMap, printPdXpBiilDTOS);
                jasperPrintList.add(jasperPrint);
                pageNumber++;
            }
            exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            baos.close();
            out.write(bytes);
            FileUtil.downloadFileToResponse(path,response);
            log.info("打印 end ====>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        } catch (JRException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                File file = new File(path);
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void printWtBillDataWithPdf2(String warehouseCode,HttpServletResponse response) {
        if (StringUtils.isBlank(warehouseCode)) {
            return;
        }
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return;
        }
        String warehouseNameByCode = opsCommonService.getWarehouseNameByCode(warehouseCode);
        LambdaQueryWrapper<OpsAsPdBillDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsAsPdBillDataDO::getPdDataType,PdDataTypeEnum.WT.getCode());
        queryWrapper.eq(OpsAsPdBillDataDO::getWarehouseCode,warehouseCode);
        queryWrapper.eq(OpsAsPdBillDataDO::getPdBatchNo,batchNoWithIsActive.getData().getPdBatchNo());
        queryWrapper.eq(OpsAsPdBillDataDO::getDelFlag,false);
        queryWrapper
                .orderByAsc(OpsAsPdBillDataDO::getPdBillNo)
                .orderByAsc(OpsAsPdBillDataDO::getShelvesNo)
                .orderByAsc(OpsAsPdBillDataDO::getModelNo1);

        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(6000);
        PageInfo<OpsAsPdBillDataDO> pageInfo;
        Map<String,List<OpsAsPdBillDataDO>> map = new HashMap<>();

        while (true) {
            pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> {
                        opsAsPdBillDataMapper.selectList(queryWrapper);
                    });

            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                return;
            }
            for (OpsAsPdBillDataDO item : pageInfo.getList()) {
                if (StringUtils.isBlank(item.getPdBillNo())) {
                    continue;
                }
                if (map.containsKey(item.getPdBillNo())) {
                    List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = map.get(item.getPdBillNo());
                    opsAsPdBillDataDOS.add(item);
                    map.put(item.getPdBillNo(),opsAsPdBillDataDOS);
                } else {
                    List<OpsAsPdBillDataDO> opsAsPdBillDataDOList = new ArrayList<>();
                    opsAsPdBillDataDOList.add(item);
                    map.put(item.getPdBillNo(),opsAsPdBillDataDOList);
                }
            }

            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        List<String> keyList = new ArrayList<>(map.keySet());
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        keyList = keyList.stream().sorted(String::compareTo).collect(Collectors.toList());
        FileOutputStream  out = null;
        List<JasperPrint> jasperPrintList = new ArrayList<>();
        String path = System.getProperty("user.dir")+"/"+warehouseCode+".pdf";
        log.info("委托在库盘点票输出位置: "+path);
        try {
            out = new FileOutputStream(path);
            int pageNumber = 1;
            for (String key : keyList) {
                List<OpsAsPdBillDataDO> opsAsPdBillDataDOS = map.get(key);
                if (CollectionUtils.isEmpty(opsAsPdBillDataDOS)) {
                    continue;
                }
                List<PrintWtBillDataVO> printPdXpBillVOS = new ArrayList<>();
                PrintWtBillDataDTO datasurce = new PrintWtBillDataDTO();
                PrintWtBillDataVO printWtBillDataVO;
                for (OpsAsPdBillDataDO  item : opsAsPdBillDataDOS ) {
                    printWtBillDataVO = new PrintWtBillDataVO();
                    printWtBillDataVO.setShelvesNo(item.getShelvesNo() == null ? "" : item.getShelvesNo());
                    printWtBillDataVO.setBillQty(( item.getBillQty() == null || item.getBillQty() == 0 ) ? "" : String.valueOf(item.getBillQty()));
                    printWtBillDataVO.setModelNo(item.getModelNo1() == null ? "" : item.getModelNo1());
                    printWtBillDataVO.setQty("");
                    printWtBillDataVO.setSureQty("");
                    printWtBillDataVO.setRemark("");
                    printPdXpBillVOS.add(printWtBillDataVO);
                }
                datasurce.setDetailDataSet(printPdXpBillVOS);
                Map<String, Object> jasperMap = new HashMap<>();
                jasperMap.put("pdDate", DateUtil.dateToDateString(new Date()));
                jasperMap.put("pdBillNo",opsAsPdBillDataDOS.get(0).getPdBillNo() == null ? "xxxxx" : "NO."+opsAsPdBillDataDOS.get(0).getPdBillNo());
                jasperMap.put("warehouseName","客户:"+warehouseNameByCode);
                jasperMap.put("pageNumber",pageNumber);
                jasperMap.put("pageSize",keyList.size());
                List<PrintWtBillDataDTO> printPdXpBiilDTOS = new ArrayList<>(1);
                printPdXpBiilDTOS.add(datasurce);
                InputStream inputStream = FileUtil.getTemplate("templates/jasper/PDWtBillDataPrint.jasper");
                JasperPrint jasperPrint = JasperHelper.crePdfWithJasperPrint(inputStream, jasperMap, printPdXpBiilDTOS);
                jasperPrintList.add(jasperPrint);
                pageNumber++;
            }
            exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            baos.close();
            out.write(bytes);
            FileUtil.downloadFileToResponse(path,response);
            log.info("打印 end ====>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        } catch (JRException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                File file = new File(path);
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ResultVo<PageInfo<OpsAsPdBillDataDO>> listPdBillData(PdInputRequestVO dto) {
        if(dto == null) {
            return ResultVo.failure("入参不可为空.");
        }
        if (StringUtils.isNotBlank(dto.getPdBillNo())) {
            dto.setPdBillNo(dto.getPdBillNo()+"%");
        }
        if (StringUtils.isNotBlank(dto.getModelNo())) {
            dto.setModelNo(dto.getModelNo()+"%");
        }
        if (StringUtils.isNotBlank(dto.getPdInputort())) {
            dto.setPdInputort(dto.getPdInputort()+"%");
        }
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("未找到本次激活的盘点批次号");
        }
        if (StringUtils.isNotBlank(dto.getWarehouseType()) && CollectionUtils.isEmpty(dto.getWarehouseCodes())) {
            ResultVo<List<WarehouseVO>> warehouseByType = commonServiceFeignApi.getWarehouseByType(dto.getWarehouseType());
            if (!warehouseByType.isSuccess() || CollectionUtils.isEmpty(warehouseByType.getData())) {
                return ResultVo.failure("根据仓库类型获取仓库编码失败");
            }
            List<WarehouseVO> data = warehouseByType.getData();
            List<String> list = new ArrayList<>();
            for (WarehouseVO item :data) {
                list.add(item.getWarehouseCode());
            }
            dto.setWarehouseCodes(list);
        }
        PageInfo<OpsAsPdBillDataDO> pageInfo = PageHelper.startPage(dto.getPage().getPageNumber(), dto.getPage().getPageSize()).doSelectPageInfo(
                () -> opsAsPdBillDataMapper.listPdBillDataByXpInput(dto,batchNoWithIsActive.getData().getPdBatchNo()));
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            for (OpsAsPdBillDataDO item : pageInfo.getList()) {
                if (StringUtils.isNotBlank(item.getUpdateUser())) {
                    item.setUpdateUser("["+item.getUpdateUser()+"]"+opsCommonService.getEmpSaleNameByNo(item.getUpdateUser()));
                }
                item.setPdInputTime1UI(item.getPdInputTime1());
                item.setUpdateTimeUI(item.getUpdateTime());
            }
        }
        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<String> updatePdBillDataById(PdInputDto dto) {
        if (dto == null|| CollectionUtils.isEmpty(dto.getDto())) {
            return ResultVo.failure("请重新选中数据进行修正.");
        }
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("无有效的盘点数据,请确认");
        }

        if (!PdStateEnum.pdBillysc.getCode().equals(batchNoWithIsActive.getData().getPdState()) &&
                !PdStateEnum.pdlrz.getCode().equals(batchNoWithIsActive.getData().getPdState()) ) {
            return ResultVo.failure("当前节点"+PdStateEnum.getCodeNameByCode(batchNoWithIsActive.getData().getPdState())+"不允许编辑");
        }

        if (!PdStateEnum.pdlrz.getCode().equals(batchNoWithIsActive.getData().getPdState())) {
            pdBatchService.updatePdStateByPdbatchNo(batchNoWithIsActive.getData().getPdBatchNo(),
                    PdStateEnum.pdlrz.getCode(),dto.getDto().get(0).getUpdateUser());
        }
        LambdaUpdateWrapper<OpsAsPdBillDataDO> updateWrapper = new LambdaUpdateWrapper<>();
        for (OpsAsPdBillDataDO item : dto.getDto()) {
            if (item.getPdQty1() == null || item.getPdQty1() < 0) {
                return ResultVo.failure("数量需大于0");
            }
            Object o = redisManager.hGet(CommonConstants.PD_WAREHOUSECODE_FIRST_INPUT + item.getPdBatchNo(), item.getWarehouseCode());
            String lastOptUser = "";
            if (o != null) {
                if (o.toString().contains(":")) {
                    String[] split = o.toString().split(":");
                    lastOptUser = split[0];
                }
                return ResultVo.failure(lastOptUser+"已经确认过录入,不可再次修改");
            }
            updateWrapper.clear();
            updateWrapper
                    .eq(OpsAsPdBillDataDO::getId,item.getId())
                    .set(OpsAsPdBillDataDO::getPdQty1,item.getPdQty1())
                    .set(OpsAsPdBillDataDO::getUpdateUser,item.getUpdateUser())
                    .set(OpsAsPdBillDataDO::getUpdateTime,new Date());
            opsAsPdBillDataMapper.update(null,updateWrapper);
        }
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> clearPdXpBillDataQtyById(PdInputDto dto) {
        if (dto == null|| CollectionUtils.isEmpty(dto.getDto())) {
            return ResultVo.failure("请重新选中数据进行修正.");
        }
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }
        List<OpsAsPdBillDataDO> dto1 = dto.getDto();
        List<Long> ids = dto1.stream().map(OpsAsPdBillDataDO::getId).collect(Collectors.toList());
        LambdaUpdateWrapper<OpsAsPdBillDataDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .in(OpsAsPdBillDataDO::getId,ids)
                .set(OpsAsPdBillDataDO::getPdQty1,0)
                .set(OpsAsPdBillDataDO::getUpdateUser,loginAuthDto.getUserNo())
                .set(OpsAsPdBillDataDO::getUpdateTime,new Date());
        opsAsPdBillDataMapper.update(null,updateWrapper);
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> upXpBlankBillInputInfo(PdInputDto dto) {
        if (dto == null|| CollectionUtils.isEmpty(dto.getDto())) {
            return ResultVo.failure("请重新选中数据进行修正.");
        }
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("无有效的盘点数据,请确认");
        }

        if (!PdStateEnum.pdBillysc.getCode().equals(batchNoWithIsActive.getData().getPdState()) &&
                !PdStateEnum.pdlrz.getCode().equals(batchNoWithIsActive.getData().getPdState()) ) {
            return ResultVo.failure("当前节点"+PdStateEnum.getCodeNameByCode(batchNoWithIsActive.getData().getPdState())+"不允许编辑");
        }

        if (!PdStateEnum.pdlrz.getCode().equals(batchNoWithIsActive.getData().getPdState())) {
            pdBatchService.updatePdStateByPdbatchNo(batchNoWithIsActive.getData().getPdBatchNo(),
                    PdStateEnum.pdlrz.getCode(),dto.getDto().get(0).getUpdateUser());
        }
        LambdaUpdateWrapper<OpsAsPdBillDataDO> updateWrapper = new LambdaUpdateWrapper<>();
        for (OpsAsPdBillDataDO item : dto.getDto()) {

            Object o = redisManager.hGet(CommonConstants.PD_WAREHOUSECODE_FIRST_INPUT + item.getPdBatchNo(), item.getWarehouseCode());
            String lastOptUser = "";
            if (o != null) {
                if (o.toString().contains(":")) {
                    String[] split = o.toString().split(":");
                    lastOptUser = split[0];
                }
                return ResultVo.failure(lastOptUser+"已经确认过录入,不可再次修改");
            }

            if (item.getPdQty1() == null || item.getPdQty1() < 0) {
                item.setPdQty1(null);
            }
            if (StringUtils.isBlank(item.getModelNo1())) {
                item.setModelNo1(null);
            } else {
                item.setModelNo1(item.getModelNo1().trim().toUpperCase());
            }
            if (StringUtils.isBlank(item.getShelvesNo())) {
                item.setShelvesNo(null);
            } else {
                item.setShelvesNo(item.getShelvesNo().trim().toUpperCase());
            }
            updateWrapper.clear();
            updateWrapper
                    .eq(OpsAsPdBillDataDO::getId,item.getId())
                    .set(OpsAsPdBillDataDO::getPdQty1,item.getPdQty1())
                    .set(OpsAsPdBillDataDO::getModelNo1,item.getModelNo1())
                    .set(OpsAsPdBillDataDO::getShelvesNo,item.getShelvesNo())
                    .set(OpsAsPdBillDataDO::getUpdateUser,item.getUpdateUser())
                    .set(OpsAsPdBillDataDO::getUpdateTime,new Date());
            opsAsPdBillDataMapper.update(null,updateWrapper);
        }
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> clearPdXpBlankBillDataQtyById(PdInputDto dto) {
        if (dto == null|| CollectionUtils.isEmpty(dto.getDto())) {
            return ResultVo.failure("请重新选中数据进行修正.");
        }
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }
        List<OpsAsPdBillDataDO> dto1 = dto.getDto();
        List<Long> ids = dto1.stream().map(OpsAsPdBillDataDO::getId).collect(Collectors.toList());
        LambdaUpdateWrapper<OpsAsPdBillDataDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .in(OpsAsPdBillDataDO::getId,ids)
                .set(OpsAsPdBillDataDO::getPdQty1,0)
                .set(OpsAsPdBillDataDO::getModelNo1,null)
                .set(OpsAsPdBillDataDO::getShelvesNo,null)
                .set(OpsAsPdBillDataDO::getUpdateUser,loginAuthDto.getUserNo())
                .set(OpsAsPdBillDataDO::getUpdateTime,new Date());
        opsAsPdBillDataMapper.update(null,updateWrapper);
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> upArriveNotINBlankBillInputInfo(PdInputDto dto) {
        if (dto == null|| CollectionUtils.isEmpty(dto.getDto())) {
            return ResultVo.failure("请重新选中数据进行修正.");
        }
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("无有效的盘点数据,请确认");
        }

        if (!PdStateEnum.pdBillysc.getCode().equals(batchNoWithIsActive.getData().getPdState()) &&
                !PdStateEnum.pdlrz.getCode().equals(batchNoWithIsActive.getData().getPdState()) ) {
            return ResultVo.failure("当前节点"+PdStateEnum.getCodeNameByCode(batchNoWithIsActive.getData().getPdState())+"不允许编辑");
        }

        if (!PdStateEnum.pdlrz.getCode().equals(batchNoWithIsActive.getData().getPdState())) {
            pdBatchService.updatePdStateByPdbatchNo(batchNoWithIsActive.getData().getPdBatchNo(),
                    PdStateEnum.pdlrz.getCode(),dto.getDto().get(0).getUpdateUser());
        }
        LambdaUpdateWrapper<OpsAsPdBillDataDO> updateWrapper = new LambdaUpdateWrapper<>();
        for (OpsAsPdBillDataDO item : dto.getDto()) {

            Object o = redisManager.hGet(CommonConstants.PD_WAREHOUSECODE_FIRST_INPUT + item.getPdBatchNo(), item.getWarehouseCode());
            String lastOptUser = "";
            if (o != null) {
                if (o.toString().contains(":")) {
                    String[] split = o.toString().split(":");
                    lastOptUser = split[0];
                }
                return ResultVo.failure(lastOptUser+"已经确认过录入,不可再次修改");
            }

            if (item.getPdQty1() == null || item.getPdQty1() <= 0) {
                item.setPdQty1(null);
            }
            if (StringUtils.isBlank(item.getModelNo1())) {
                item.setModelNo1(null);
            }
            if (StringUtils.isBlank(item.getInvoiceNo())) {
                item.setInvoiceNo(null);
            }
            if (StringUtils.isBlank(item.getCaseNo())) {
                item.setCaseNo(null);
            }
            if (StringUtils.isBlank(item.getBarcode())) {
                item.setBarcode(null);
            }
            updateWrapper.clear();
            updateWrapper
                    .eq(OpsAsPdBillDataDO::getId,item.getId())
                    .set(OpsAsPdBillDataDO::getPdQty1,item.getPdQty1())
                    .set(OpsAsPdBillDataDO::getModelNo1,item.getModelNo1())
                    .set(OpsAsPdBillDataDO::getCaseNo,item.getCaseNo())
                    .set(OpsAsPdBillDataDO::getBarcode,item.getBarcode())
                    .set(OpsAsPdBillDataDO::getInvoiceNo,item.getInvoiceNo())
                    .set(OpsAsPdBillDataDO::getUpdateUser,item.getUpdateUser())
                    .set(OpsAsPdBillDataDO::getUpdateTime,new Date());
            opsAsPdBillDataMapper.update(null,updateWrapper);
        }
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> clearArriverNotInBlankBillDataQtyById(PdInputDto dto) {
        if (dto == null|| CollectionUtils.isEmpty(dto.getDto())) {
            return ResultVo.failure("请重新选中数据进行修正.");
        }
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }
        List<OpsAsPdBillDataDO> dto1 = dto.getDto();
        List<Long> ids = dto1.stream().map(OpsAsPdBillDataDO::getId).collect(Collectors.toList());
        LambdaUpdateWrapper<OpsAsPdBillDataDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .in(OpsAsPdBillDataDO::getId,ids)
                .set(OpsAsPdBillDataDO::getPdQty1,0)
                .set(OpsAsPdBillDataDO::getModelNo1,null)
                .set(OpsAsPdBillDataDO::getInvoiceNo,null)
                .set(OpsAsPdBillDataDO::getCaseNo,null)
                .set(OpsAsPdBillDataDO::getBarcode,null)
                .set(OpsAsPdBillDataDO::getUpdateUser,loginAuthDto.getUserNo())
                .set(OpsAsPdBillDataDO::getUpdateTime,new Date());
        opsAsPdBillDataMapper.update(null,updateWrapper);
        return ResultVo.success("操作成功");
    }

    @Override
    public void downBorrowExel() {
        String path = "templates/excel/借库数据.xlsx";
        ExcelUtil excel = new ExcelUtil(path);
        excel.writeToResponse(response, "借库数据.xlsx");
    }

    @Override
    public ResultVo<String> batchImpBorrowData(MultipartFile file, String loginUser) {

        if (file == null) {
            return ResultVo.failure("请选择导入文件");
        }
        if (StringUtils.isBlank(loginUser)) {
            return ResultVo.failure("登录信息失效,请重新登录");
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
        int count = 0;
        int upCount;
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return ResultVo.failure("获取盘点批次号失败");
        }
        OpsAsPdBatchDO opsAsPdBatchDO = batchNoWithIsActive.getData();
        String batchNo = opsAsPdBatchDO.getPdBatchNo();

        LambdaUpdateWrapper<OpsAsPdBorrowDataDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(OpsAsPdBorrowDataDO::getPdBatchNo,batchNo).set(OpsAsPdBorrowDataDO::getDelFlag,true);
        opsAsPdBorrowDataMapper.update(null, updateWrapper);

        Date nowDate = new Date();
        OpsAsPdBorrowDataDO borrowDataDO;
        while (true) {
            row++;
            rows = sheet.getRow(row);
            if (rows == null) {
                break;
            }
            borrowDataDO = new OpsAsPdBorrowDataDO();
            String orderNo = excel.getCellString(rows.getCell(0));
            String modelNo = excel.getCellString(rows.getCell(1));
            String quantity = excel.getCellString(rows.getCell(2));
            String warehouse_code = excel.getCellString(rows.getCell(3));
            String expDate = excel.getCellString(rows.getCell(4));
            String person = excel.getCellString(rows.getCell(5));
            String deptNo = excel.getCellString(rows.getCell(6));

            borrowDataDO.setPdBatchNo(batchNo);
            borrowDataDO.setWarehouseCode(warehouse_code);
            borrowDataDO.setPdDataType("1");
            borrowDataDO.setOrderNo(orderNo);
            borrowDataDO.setModelNo(modelNo);
            if (StringUtils.isNotBlank(quantity) && PublicUtil.isNum(quantity)) {
                borrowDataDO.setQty(Integer.parseInt(quantity));
            }
            if (StringUtils.isNotBlank(expDate)) {
                expDate =  expDate.replace("/","-");
                borrowDataDO.setBorrowDate(DateUtil.stringToDate(expDate));
            }
            borrowDataDO.setBorrowPerson(person);
            borrowDataDO.setBorrowDept(deptNo);
            borrowDataDO.setDelFlag(false);
            borrowDataDO.setRemark("");
            borrowDataDO.setCreateUser(loginUser);
            borrowDataDO.setUpdateUser(loginUser);
            borrowDataDO.setUpdateTime(nowDate);
            borrowDataDO.setCreateTime(nowDate);
            try {
                upCount = opsAsPdBorrowDataMapper.insert(borrowDataDO);
            } catch (Exception e) {
                log.error("借库数据导入发生异常: {}",e.getMessage(),e);
                throw new BusinessException(e.getMessage());
            }
            if (upCount != 0) {
                count++;
            }
        }
        return ResultVo.success("操作完毕.共计"+count+"条");
    }

    @Override
    public ResultVo<String> delBorrowDataById(PdBorrowDto dto) {
        if (dto == null || CollectionUtils.isEmpty(dto.getDto())) {
            return ResultVo.failure("请选择需要操作的数据");
        }

        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }

        List<Long> collect = dto.getDto().stream().map(OpsAsPdBorrowDataDO::getId).collect(Collectors.toList());
        LambdaUpdateWrapper<OpsAsPdBorrowDataDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .in(OpsAsPdBorrowDataDO::getId,collect)
                .set(OpsAsPdBorrowDataDO::getDelFlag,true)
                .set(OpsAsPdBorrowDataDO::getUpdateTime,new Date())
                .set(OpsAsPdBorrowDataDO::getUpdateUser,loginAuthDto.getUserNo());
        opsAsPdBorrowDataMapper.update(null,updateWrapper);
        return ResultVo.success("成功删除"+collect.size()+"条数据");
    }

    @Override
    public ResultVo<String> upBorrowInfo(PdBorrowDto dto) {
        if (dto == null|| CollectionUtils.isEmpty(dto.getDto())) {
            return ResultVo.failure("请重新选中数据进行修正.");
        }
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("无有效的盘点数据,请确认");
        }

        if (!PdStateEnum.pdBillysc.getCode().equals(batchNoWithIsActive.getData().getPdState()) &&
                !PdStateEnum.pdlrz.getCode().equals(batchNoWithIsActive.getData().getPdState()) ) {
            return ResultVo.failure("当前节点"+PdStateEnum.getCodeNameByCode(batchNoWithIsActive.getData().getPdState())+"不允许编辑");
        }

        if (!PdStateEnum.pdlrz.getCode().equals(batchNoWithIsActive.getData().getPdState())) {
           pdBatchService.updatePdStateByPdbatchNo(batchNoWithIsActive.getData().getPdBatchNo(),
                   PdStateEnum.pdlrz.getCode(),dto.getDto().get(0).getUpdateUser());
        }

        LambdaUpdateWrapper<OpsAsPdBorrowDataDO> updateWrapper = new LambdaUpdateWrapper<>();
        for (OpsAsPdBorrowDataDO item : dto.getDto()) {
            Object o = redisManager.hGet(CommonConstants.PD_WAREHOUSECODE_FIRST_INPUT + item.getPdBatchNo(), item.getWarehouseCode());
            String lastOptUser = "";
            if (o != null) {
                if (o.toString().contains(":")) {
                    String[] split = o.toString().split(":");
                    lastOptUser = split[0];
                }
                return ResultVo.failure(lastOptUser+"已经确认过录入,不可再次修改");
            }
            if (StringUtils.isBlank(item.getOrderNo())) {
                item.setOrderNo(null);
            }
            if (StringUtils.isBlank(item.getModelNo())) {
                item.setModelNo(null);
            }
            if (item.getQty() == null || item.getQty() < 0) {
                item.setQty(null);
            }
            if (StringUtils.isBlank(item.getWarehouseCode())) {
                item.setWarehouseCode(null);
            }
            if (item.getBorrowDateUI() == null) {
                item.setBorrowDate(null);
            }
            if (StringUtils.isBlank(item.getBorrowPerson())) {
                item.setBorrowPerson(null);
            }
            if (StringUtils.isBlank(item.getBorrowDept())) {
                item.setBorrowDept(null);
            }
            updateWrapper.clear();
            updateWrapper
                    .eq(OpsAsPdBorrowDataDO::getId,item.getId())
                    .set(OpsAsPdBorrowDataDO::getOrderNo,item.getOrderNo())
                    .set(OpsAsPdBorrowDataDO::getModelNo,item.getModelNo())
                    .set(OpsAsPdBorrowDataDO::getQty,item.getQty())
                    .set(OpsAsPdBorrowDataDO::getWarehouseCode,item.getWarehouseCode())
                    .set(OpsAsPdBorrowDataDO::getBorrowDate,item.getBorrowDateUI())
                    .set(OpsAsPdBorrowDataDO::getBorrowPerson,item.getBorrowPerson())
                    .set(OpsAsPdBorrowDataDO::getBorrowDept,item.getBorrowDept())
                    .set(OpsAsPdBorrowDataDO::getUpdateUser,item.getUpdateUser())
                    .set(OpsAsPdBorrowDataDO::getUpdateTime,item.getUpdateTime());
            opsAsPdBorrowDataMapper.update(null, updateWrapper);
        }
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<PageInfo<OpsAsPdBorrowDataDO>> listBorrowDataList(PdInputRequestVO dto) {
        if(dto == null) {
            return ResultVo.failure("入参不可为空.");
        }

        if (StringUtils.isNotBlank(dto.getWarehouseType()) && CollectionUtils.isEmpty(dto.getWarehouseCodes())) {
            ResultVo<List<WarehouseVO>> warehouseByType = commonServiceFeignApi.getWarehouseByType(dto.getWarehouseType());
            if (!warehouseByType.isSuccess() || CollectionUtils.isEmpty(warehouseByType.getData())) {
                return ResultVo.failure("根据仓库类型获取仓库编码失败");
            }
            List<WarehouseVO> data = warehouseByType.getData();
            List<String> list = new ArrayList<>();
            for (WarehouseVO item :data) {
                list.add(item.getWarehouseCode());
            }
            dto.setWarehouseCodes(list);
        }
        LambdaQueryWrapper<OpsAsPdBorrowDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(CollectionUtils.isNotEmpty(dto.getWarehouseCodes()),OpsAsPdBorrowDataDO::getWarehouseCode,dto.getWarehouseCodes())
                .eq(OpsAsPdBorrowDataDO::getDelFlag,0)
                .likeRight(StringUtils.isNotBlank(dto.getModelNo()),OpsAsPdBorrowDataDO::getModelNo,dto.getModelNo())
                .likeRight(StringUtils.isNotBlank(dto.getPdInputort()),OpsAsPdBorrowDataDO::getUpdateUser,dto.getPdInputort())
                .orderByAsc(OpsAsPdBorrowDataDO::getOrderNo).orderByAsc(OpsAsPdBorrowDataDO::getId);

        PageInfo<OpsAsPdBorrowDataDO> pageInfo = PageHelper.startPage(dto.getPage().getPageNumber(), dto.getPage().getPageSize()).doSelectPageInfo(
                () -> opsAsPdBorrowDataMapper.selectList(queryWrapper));
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            for (OpsAsPdBorrowDataDO item : pageInfo.getList()) {
                if (StringUtils.isNotBlank(item.getUpdateUser())) {
                    item.setUpdateUser("["+item.getUpdateUser()+"]"+opsCommonService.getEmpSaleNameByNo(item.getUpdateUser()));
                }
                item.setUpdateTimeUI(item.getUpdateTime());
                item.setBorrowDateUI(item.getBorrowDate());
                if (item.getQty() == null || item.getQty() == 0) {
                    item.setQty(null);
                }
            }
        }
        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<PageInfo<OpsAsPdBillDataDO>> findPdBillDataListWithDiff(PdDiffDataRequestVO dto) {
        if (dto == null) {
            return ResultVo.failure("入参不可为空.");
        }

        if (StringUtils.isNotBlank(dto.getWarehouseType()) && CollectionUtils.isEmpty(dto.getWarehouseCodes())) {
            ResultVo<List<WarehouseVO>> warehouseByType = commonServiceFeignApi.getWarehouseByType(dto.getWarehouseType());
            if (!warehouseByType.isSuccess() || CollectionUtils.isEmpty(warehouseByType.getData())) {
                return ResultVo.failure("根据仓库类型获取仓库编码失败");
            }
            List<WarehouseVO> data = warehouseByType.getData();
            List<String> list = new ArrayList<>();
            for (WarehouseVO item :data) {
                list.add(item.getWarehouseCode());
            }
            dto.setWarehouseCodes(list);
        }
        if (StringUtils.isNotBlank(dto.getPdBillNo())) {
            dto.setPdBillNo("%"+dto.getPdBillNo()+"%");
        }
        if (StringUtils.isNotBlank(dto.getModelNo())) {
            dto.setModelNo(dto.getModelNo()+"%");
        }
        if (CollectionUtil.isNotEmpty(dto.getDiffTypes()) && dto.getDiffTypes().size() == 1) {
            dto.setDiffType(dto.getDiffTypes().get(0));
        }

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return ResultVo.failure("获取盘点批次号失败");
        }
        PageInfo<OpsAsPdBillDataDO> pageInfo = PageHelper.startPage(dto.getPage().getPageNumber(), dto.getPage().getPageSize()).doSelectPageInfo(
                () -> opsAsPdBillDataMapper.findPdBillDataListWithDiff(dto,batchNoWithIsActive.getData().getPdBatchNo()));
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            for (OpsAsPdBillDataDO item :pageInfo.getList()) {
                if (StringUtils.isNotBlank(item.getPdBillType())) {
                    item.setPdBillType(PdBillTypeEnum.getCodeName(item.getPdBillType()));
                }
                if (item.getPdInputTime1() != null) {
                    item.setPdInputTime1UI(item.getPdInputTime1());
                }
                if (item.getPdInputTime2() != null) {
                    item.setPdInputTime2UI(item.getPdInputTime2());
                }
                if (StringUtils.isNotBlank(item.getPdInputort1())) {
                    item.setPdInputort1("["+item.getPdInputort1()+"]"+opsCommonService.getEmpSaleNameByNo(item.getPdInputort1()));
                }
                if (StringUtils.isNotBlank(item.getPdInputort2())) {
                    item.setPdInputort2("["+item.getPdInputort2()+"]"+opsCommonService.getEmpSaleNameByNo(item.getPdInputort2()));
                }
            }
        }
        return ResultVo.success(pageInfo);
    }

    @Override
    public void exportPdBillDiffFindDataList(PdDiffDataRequestVO dto) {
        if (dto == null) {
            return;
        }
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }

        if (StringUtils.isNotBlank(dto.getWarehouseType()) && CollectionUtils.isEmpty(dto.getWarehouseCodes())) {
            ResultVo<List<WarehouseVO>> warehouseByType = commonServiceFeignApi.getWarehouseByType(dto.getWarehouseType());
            if (!warehouseByType.isSuccess() || CollectionUtils.isEmpty(warehouseByType.getData())) {
                return;
            }
            List<WarehouseVO> data = warehouseByType.getData();
            List<String> list = new ArrayList<>();
            for (WarehouseVO item :data) {
                list.add(item.getWarehouseCode());
            }
            dto.setWarehouseCodes(list);
        }
        if (StringUtils.isNotBlank(dto.getPdBillNo())) {
            dto.setPdBillNo("%"+dto.getPdBillNo()+"%");
        }
        if (StringUtils.isNotBlank(dto.getModelNo())) {
            dto.setModelNo(dto.getModelNo()+"%");
        }
        if (CollectionUtils.isNotEmpty(dto.getDiffTypes()) && dto.getDiffTypes().size()  == 1) {
            dto.setDiffType(dto.getDiffTypes().get(0));
        }
        // 获取当前批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return;
        }

        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(6000);
        // 向模板中写入数据
        int row = 1;
        String path = "templates/excel/盘点差异调整导出.xlsx";
        ExcelUtil excel = new ExcelUtil(path);
        excel.openSheet(0);
        PageInfo<OpsAsPdBillDataDO> pageInfo;
        while (true) {
            pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> {
                        opsAsPdBillDataMapper.findPdBillDataListWithDiff(dto,batchNoWithIsActive.getData().getPdBatchNo());
                    });
            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                return;
            }

            row =  writeExcel(pageInfo.getList(),excel,row);

            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }
        excel.writeToResponse(response, "盘点差异调整导出.xlsx");
    }



    public void exportPdBillDiffFindDataListByEasyExcel(PdDiffDataRequestVO dto,HttpServletResponse response) {

        long lstart = System.currentTimeMillis();
        log.info("开始导盘点差异数据");
        if (dto == null) {
            return;
        }
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }

        if (StringUtils.isNotBlank(dto.getWarehouseType()) && CollectionUtils.isEmpty(dto.getWarehouseCodes())) {
            ResultVo<List<WarehouseVO>> warehouseByType = commonServiceFeignApi.getWarehouseByType(dto.getWarehouseType());
            if (!warehouseByType.isSuccess() || CollectionUtils.isEmpty(warehouseByType.getData())) {
                return;
            }
            List<WarehouseVO> data = warehouseByType.getData();
            List<String> list = new ArrayList<>();
            for (WarehouseVO item :data) {
                list.add(item.getWarehouseCode());
            }
            dto.setWarehouseCodes(list);
        }
        if (StringUtils.isNotBlank(dto.getPdBillNo())) {
            dto.setPdBillNo("%"+dto.getPdBillNo()+"%");
        }
        if (StringUtils.isNotBlank(dto.getModelNo())) {
            dto.setModelNo(dto.getModelNo()+"%");
        }
        if (CollectionUtils.isNotEmpty(dto.getDiffTypes()) && dto.getDiffTypes().size()  == 1) {
            dto.setDiffType(dto.getDiffTypes().get(0));
        }
        // 获取当前批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return;
        }

        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(20000);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetmL.sheet");
        response.setCharacterEncoding("utf-8");
        try {
            String fileName = URLEncoder.encode("盘点差异数据导出", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");
            PageInfo<PdDiffBillDataExportVO> pageInfo;
            InputStream inputStream = new ClassPathResource("templates/excel/盘点差异调整导出.xlsx").getInputStream();

            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), PdDiffBillDataExportVO.class)
                    .withTemplate(inputStream).autoCloseStream(Boolean.FALSE).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
            while (true) {
                long l = System.currentTimeMillis();
                log.info("第"+page.getPageNumber()+"页 查询开始 =>");
                pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                        .doSelectPageInfo(() -> {
                            opsAsPdBillDataMapper.findPdBillDataListWithDiffByExport(dto,batchNoWithIsActive.getData().getPdBatchNo());
                        });
                if (CollectionUtils.isEmpty(pageInfo.getList())) {
                    return;
                }
                log.info("第"+page.getPageNumber()+"页 查询结束 => " + (System.currentTimeMillis() - l) / 1000);
                log.info("开始写入Excel");
                long l1 = System.currentTimeMillis();
                for (PdDiffBillDataExportVO item: pageInfo.getList()) {
                    if (item.getPdInputTime1() != null) {
                        item.setPdInputTime1UI(item.getPdInputTime1());
                    }
                    if (item.getPdInputTime2() != null) {
                        item.setPdInputTime2UI(item.getPdInputTime2());
                    }
                    if (StringUtils.isNotBlank(item.getPdInputort1())) {
                        item.setPdInputort1("["+item.getPdInputort1()+"]"+opsCommonService.getEmpSaleNameByNo(item.getPdInputort1()));
                    }
                    if (StringUtils.isNotBlank(item.getPdInputort2())) {
                        item.setPdInputort2("["+item.getPdInputort2()+"]"+opsCommonService.getEmpSaleNameByNo(item.getPdInputort2()));
                    }

                    if(StringUtils.isNotBlank(item.getPdBillType())) {
                        item.setPdBillType(PdBillTypeEnum.getCodeName(item.getPdBillType()));
                    }
                }
                // List<PdDiffBillDataExportVO> pdDiffBillDataExportVOS = BeanCopyUtil.copyList(pageInfo.getList(), PdDiffBillDataExportVO.class);
                excelWriter.write(pageInfo.getList(),writeSheet);
                log.info("写入Excel结束 => "+ (System.currentTimeMillis() - l1) / 1000 );
                if (pageInfo.isIsLastPage()) {
                    break;
                }
                page.setPageNumber(page.getPageNumber() + 1);
            }
            excelWriter.finish();
            log.info("导盘点差异数据结束 耗时 {} (s)",(System.currentTimeMillis() - lstart) / 1000);
        } catch (IOException e) {
            response.reset();
            e.printStackTrace();
        }
    }

    public int writeExcel(List<OpsAsPdBillDataDO> pdBillDataListWithDiff,ExcelUtil excel, int row) {

        int cel;
        for (OpsAsPdBillDataDO item :pdBillDataListWithDiff) {
            cel = 0;
            if (item.getPdInputTime1() != null) {
                item.setPdInputTime1UI(item.getPdInputTime1());
            }
            if (item.getPdInputTime2() != null) {
                item.setPdInputTime2UI(item.getPdInputTime2());
            }
            if (StringUtils.isNotBlank(item.getPdInputort1())) {
                item.setPdInputort1("["+item.getPdInputort1()+"]"+opsCommonService.getEmpSaleNameByNo(item.getPdInputort1()));
            }
            if (StringUtils.isNotBlank(item.getPdInputort2())) {
                item.setPdInputort2("["+item.getPdInputort2()+"]"+opsCommonService.getEmpSaleNameByNo(item.getPdInputort2()));
            }

            if(StringUtils.isNotBlank(item.getPdBillType())) {
                item.setPdBillType(PdBillTypeEnum.getCodeName(item.getPdBillType()));
            }

            excel.setCellValue(row, cel++, item.getId());
            excel.setCellValue(row, cel++, item.getPdBatchNo());
            excel.setCellValue(row, cel++, item.getPdBillNo());
            excel.setCellValue(row, cel++, item.getPdBillType());
            excel.setCellValue(row, cel++, item.getShelvesNo());
            excel.setCellValue(row, cel++, item.getLocationNo());
            excel.setCellValue(row, cel++, item.getInvoiceNo());
            excel.setCellValue(row, cel++, item.getCaseNo());
            excel.setCellValue(row, cel++, item.getBarcode());
            excel.setCellValue(row, cel++, item.getBillQty());
            excel.setCellValue(row, cel++, item.getModelNo1());
            excel.setCellValue(row, cel++, item.getModelNo2());
            excel.setCellValue(row, cel++, item.getPdQty1());
            excel.setCellValue(row, cel++, item.getPdQty2());
            excel.setCellValue(row, cel++, item.getPdInputTime1());
            excel.setCellValue(row, cel++, item.getPdInputTime2());
            excel.setCellValue(row, cel++, item.getPdInputort1());
            excel.setCellValue(row, cel, item.getPdInputort2());
            row++;
        }
        return row;
    }

    @Override
    public ResultVo<String> suerAgainInput(String warehouseCode) {

        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }

        // 获取当前批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return ResultVo.failure("获取盘点批次号失败");
        }
        OpsAsPdBatchDO opsAsPdBatchDO = batchNoWithIsActive.getData();
        String batchNo = opsAsPdBatchDO.getPdBatchNo();

        int count = 0;
        if(StringUtils.isNotBlank(warehouseCode)) {
            count =  opsAsPdBillDataMapper.countIsNullPdQty1WithWarehouseCode(warehouseCode,batchNo);
        } else {
            count = opsAsPdBillDataMapper.countIsNullPdQty1(batchNo);
        }

        if (count > 0) {
            return ResultVo.failure("检测到第一型号/数量未录入完毕,请检查.");
        }

        if(StringUtils.isNotBlank(warehouseCode)) {
            Object o = redisManager.hGet(CommonConstants.PD_WAREHOUSECODE_FIRST_INPUT + batchNo, warehouseCode);
            String lastOptUser = "";
            if (o != null) {
                if (o.toString().contains(":")) {
                    String[] split = o.toString().split(":");
                    lastOptUser = split[0];
                }
                return ResultVo.failure(lastOptUser+"已经确认过录入,不可重复确认");
            }
            String pdState  = batchNoWithIsActive.getData().getPdState();
            if (!PdStateEnum.pdlrz.getCode().equals(pdState)) {
                return ResultVo.failure("只有盘点录入完之后才可确认二次录入");
            }
            // 第一次盘点型号.数量调至第二次
            opsAsPdBillDataMapper.updateWithExport(batchNo,loginAuthDto.getUserNo(),warehouseCode);

            redisManager.hPut(CommonConstants.PD_WAREHOUSECODE_FIRST_INPUT+batchNo,warehouseCode,loginAuthDto.getUserNo()+":1");
        } else {
            // 第一次盘点型号.数量调至第二次
            opsAsPdBillDataMapper.updateWithExport2(batchNo,loginAuthDto.getUserNo());
            // 获取各仓代码
            ResultVo<List<com.smc.smccloud.model.WarehouseVO>> master = opsCommonService.getWarehouseByType("MASTER");
            List<com.smc.smccloud.model.WarehouseVO> warehouseVOS = master.getData();
            ResultVo<List<com.smc.smccloud.model.WarehouseVO>> sub = opsCommonService.getWarehouseByType("sub");
            warehouseVOS.addAll(sub.getData());
            for (com.smc.smccloud.model.WarehouseVO w : warehouseVOS) {
                redisManager.hPut(CommonConstants.PD_WAREHOUSECODE_FIRST_INPUT+batchNo,w.getWarehouseCode(),loginAuthDto.getUserNo()+":1");
            }
        }
        return ResultVo.success("确认二次录入操作成功");
    }

    @Override
    public ResultVo<String> updateDiffPdBillDataById(PdInputDto dto) {
        if (dto == null|| CollectionUtils.isEmpty(dto.getDto())) {
            return ResultVo.failure("请重新选中数据进行修正.");
        }
        // 获取当前批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return ResultVo.failure("获取盘点批次号失败");
        }
        LambdaQueryWrapper<OpsAsPdBillDataDO> queryWrapper = new LambdaQueryWrapper<>();
        LambdaUpdateWrapper<OpsAsPdBillDataDO> updateWrapper = new LambdaUpdateWrapper<>();
        for (OpsAsPdBillDataDO item : dto.getDto()) {

            queryWrapper.clear();
            queryWrapper.eq(OpsAsPdBillDataDO::getId,item.getId());
            OpsAsPdBillDataDO opsAsPdBillDataDO = opsAsPdBillDataMapper.selectOne(queryWrapper);

//            Object o = redisManager.hGet(CommonConstants.PD_WAREHOUSECODE_FIRST_INPUT + item.getPdBatchNo(), item.getWarehouseCode());
//            if(o == null) {
//                return ResultVo.failure(item.getWarehouseCode()+"还没进行第一次录入完毕确认,请先进行确认");
//            }

            if (item.getPdQty2() == null || item.getPdQty2() <= 0) {
                item.setPdQty2(null);
            }
            if (StringUtils.isBlank(item.getModelNo2())) {
                item.setModelNo2(null);
            }
            updateWrapper.clear();
            updateWrapper.eq(OpsAsPdBillDataDO::getId,item.getId());
            if (PdBillTypeEnum.XPBLANKBILL.getCode().equals(opsAsPdBillDataDO.getPdBillType()) ||
                    PdBillTypeEnum.DHWRBLANKBILL.getCode().equals(opsAsPdBillDataDO.getPdBillType())) {
                updateWrapper.set(OpsAsPdBillDataDO::getModelNo2,item.getModelNo2());
            }
            if (PdBillTypeEnum.XPBLANKBILL.getCode().equals(opsAsPdBillDataDO.getPdBillType()) ||
                    PdBillTypeEnum.DHWRBLANKBILL.getCode().equals(opsAsPdBillDataDO.getPdBillType()) ||
                   PdBillTypeEnum.XPBILL.getCode().equals(opsAsPdBillDataDO.getPdBillType())) {
                updateWrapper.set(OpsAsPdBillDataDO::getPdQty2,item.getPdQty2());
            }
            updateWrapper.set(OpsAsPdBillDataDO::getShelvesNo,item.getShelvesNo());
            updateWrapper.set(OpsAsPdBillDataDO::getUpdateUser,item.getUpdateUser());
            updateWrapper.set(OpsAsPdBillDataDO::getUpdateTime,new Date());
            opsAsPdBillDataMapper.update(null,updateWrapper);
        }
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> delDiffPdBillDataById(PdInputDto dto) {
        if (dto == null|| CollectionUtils.isEmpty(dto.getDto())) {
            return ResultVo.failure("请重新选中数据进行修正.");
        }
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }
        List<OpsAsPdBillDataDO> dto1 = dto.getDto();
        List<Long> ids = dto1.stream().map(OpsAsPdBillDataDO::getId).collect(Collectors.toList());
        LambdaUpdateWrapper<OpsAsPdBillDataDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .in(OpsAsPdBillDataDO::getId,ids)
                .set(OpsAsPdBillDataDO::getDelFlag,true)
                .set(OpsAsPdBillDataDO::getUpdateUser,loginAuthDto.getUserNo())
                .set(OpsAsPdBillDataDO::getUpdateTime,new Date());
        opsAsPdBillDataMapper.update(null,updateWrapper);
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<List<OpsAsPdOtherdataDO>> listPdOtherDataImp() {
        List<OpsAsPdOtherdataDO> list = new ArrayList<>();
        LambdaQueryWrapper<OpsAsPdOtherdataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsAsPdOtherdataDO::getDelFlag,false);
        list = opsAsPdOtherdataMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.success(list);
        }
        for (OpsAsPdOtherdataDO item : list) {
            item.setUpdateTimeUI(item.getUpdateTime());
            item.setJobExecTimeUI(item.getJobExecTime());
            if (StringUtils.isNotBlank(item.getUpdateUser())) {
                item.setUpdateUser("["+item.getUpdateUser()+"]"+opsCommonService.getEmpSaleNameByNo(item.getUpdateUser()));
            }
            if (StringUtils.isNotBlank(item.getExecFlag())) {
                item.setExecFlagName(OtherDataExcResultEnum.getNameBycode(item.getExecFlag().trim()));
            }
        }
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<String> editOtherData(PdOtherDataDto dto) {
        if (dto == null|| CollectionUtils.isEmpty(dto.getDto())) {
            return ResultVo.failure("请重新选中数据进行修正.");
        }
        LambdaUpdateWrapper<OpsAsPdOtherdataDO> updateWrapper = new LambdaUpdateWrapper<>();
        for (OpsAsPdOtherdataDO item : dto.getDto()) {
            updateWrapper.clear();
            updateWrapper
                    .eq(OpsAsPdOtherdataDO::getId,item.getId())
                    .set(OpsAsPdOtherdataDO::getJobExecTime,item.getJobExecTimeUI())
                    .set(OpsAsPdOtherdataDO::getUpdateTime,new Date())
                    .set(OpsAsPdOtherdataDO::getUpdateUser,item.getUpdateUser());
            opsAsPdOtherdataMapper.update(null,updateWrapper);
        }
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> delOtherDataById(PdOtherDataDto dto) {
        if (dto == null|| CollectionUtils.isEmpty(dto.getDto())) {
            return ResultVo.failure("请选择需要操作的数据.");
        }
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }
        List<OpsAsPdOtherdataDO> dto1 = dto.getDto();
        List<Integer> ids = dto1.stream().map(OpsAsPdOtherdataDO::getId).collect(Collectors.toList());
        LambdaUpdateWrapper<OpsAsPdOtherdataDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .in(OpsAsPdOtherdataDO::getId,ids)
                .set(OpsAsPdOtherdataDO::getDelFlag,true)
                .set(OpsAsPdOtherdataDO::getUpdateUser,loginAuthDto.getUserNo())
                .set(OpsAsPdOtherdataDO::getUpdateTime,new Date());
        opsAsPdOtherdataMapper.update(null,updateWrapper);
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> wtDataImpWmsInventoryData() {
        List<OpsAsWmsInventoryDataDO> opsAsWmsInventoryDataDOS = opsAsWmsInventoryDataMapper.wtDataImpWmsInventoryData();
        if (CollectionUtils.isEmpty(opsAsWmsInventoryDataDOS)) {
            return ResultVo.failure("暂无数据");
        }
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();
        OpsAsWmsInventoryDataDO opsAsWmsInventoryDataDO;
        Date nowDate = new Date();
        for (OpsAsWmsInventoryDataDO item : opsAsWmsInventoryDataDOS ) {
            opsAsWmsInventoryDataDO = new OpsAsWmsInventoryDataDO();
            opsAsWmsInventoryDataDO.setPdBatchNo(batchNo);
            opsAsWmsInventoryDataDO.setWarehouseCode(item.getWarehouseCode());
            opsAsWmsInventoryDataDO.setShelvesNo(item.getShelvesNo());
            opsAsWmsInventoryDataDO.setModelNo(item.getModelNo());
            opsAsWmsInventoryDataDO.setBillQty(item.getBillQty());
            opsAsWmsInventoryDataDO.setWarehouseType(item.getWarehouseType());
            opsAsWmsInventoryDataDO.setCreateTime(nowDate);
            opsAsWmsInventoryDataDO.setUpdateTime(nowDate);
            opsAsWmsInventoryDataDO.setCreateUser("sys");
            opsAsWmsInventoryDataDO.setUpdateUser("sys");
            opsAsWmsInventoryDataDO.setPdDataType(PdDataTypeEnum.WT.getCode());
            opsAsWmsInventoryDataDO.setPdBillType(PdBillTypeEnum.XPBILL.getCode());
            opsAsWmsInventoryDataMapper.insert(opsAsWmsInventoryDataDO);
        }
        return ResultVo.success("委托在库数据导入完毕");
    }

    @Override
    public ResultVo<String> impSampleorderDataToOpsCompensateData(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.yp.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取样品未结转数据源失败.");
        }

        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.yp.getCode();

        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;
        try {

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.yp.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到样品未结转程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
                jobExecTime = new Date();
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("样品未结转正在执行中,请勿重复执行.");
            }

            redissonUtil.lock(key,30*60);

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"1","2");
            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            // String execDate = DateUtil.dateToDateString(jobExecTime);
            String execDate = DateUtil.dateToDateTimeString(new Date());

            // 获取样品数据写入到补偿表
            List<OpsAsPdCompensateDataDO> opsAsPdCompensateDataDOS = opsAsPdCompensateDataMapper.sampleOrderImpCompensateData(execDate,dataSourceByCode);
            if (CollectionUtils.isEmpty(opsAsPdCompensateDataDOS)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无样品结转数据.");
            }
            Date nowDate = new Date();
            OpsAsPdCompensateDataDO opsAsPdCompensateDataDO ;
            for (OpsAsPdCompensateDataDO item : opsAsPdCompensateDataDOS) {
                opsAsPdCompensateDataDO = new OpsAsPdCompensateDataDO();
                opsAsPdCompensateDataDO.setPdBatchNo(batchNo);
                opsAsPdCompensateDataDO.setOrderNo(item.getOrderNo());
                opsAsPdCompensateDataDO.setRorderNo(item.getRorderNo());
                opsAsPdCompensateDataDO.setItemNo(item.getItemNo());
                opsAsPdCompensateDataDO.setWarehouseCode(item.getWarehouseCode());
                opsAsPdCompensateDataDO.setModelNo(item.getModelNo());
                opsAsPdCompensateDataDO.setQty(item.getQty());
                opsAsPdCompensateDataDO.setPdDataType(item.getPdDataType());
                opsAsPdCompensateDataDO.setPdDataSource(item.getPdDataSource());
                opsAsPdCompensateDataDO.setCreateTime(nowDate);
                opsAsPdCompensateDataDO.setUpdateTime(nowDate);
                opsAsPdCompensateDataDO.setCreateUser("sys");
                opsAsPdCompensateDataDO.setUpdateUser("sys");
                opsAsPdCompensateDataMapper.insert(opsAsPdCompensateDataDO);
            }
        } catch (Exception e) {
            log.info("样品结转数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.failure("样品结转数据抽取失败 :"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }

        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());

        commonMapper.insertSysLog("年度样品结转数据抽取",loginAuthDto.getUserNo(),"execDate:"+DateUtil.dateToDateTimeString(new Date())+dataSourceByCode);

        return ResultVo.success("样品结转数据抽取完毕");
    }

    @Override
    public ResultVo<String> yckNotpushCwData(String flag) {

        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.yck.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取已出库未推财务数据源失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.yck.getCode();


        // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.yck.getCode(), batchNo);
        if (opsAsPdOtherdataDO == null) {
            return ResultVo.failure("暂未找到已出库未推财务程序的配置");
        }
        Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
        if (jobExecTime == null) {
            jobExecTime = new Date();
            // return ResultVo.failure("请检查执行时间格式配置的是否正确");
        }
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("已出库未推财务数据正在执行中,请勿重复执行.");
            }

            redissonUtil.lock(key,30*60);

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"2","2");

            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            List<OpsAsPdCompensateDataDO> opsAsPdCompensateDataDOS = opsAsPdCompensateDataMapper.yckNotPushCw(dataSourceByCode);
            if (CollectionUtils.isEmpty(opsAsPdCompensateDataDOS)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无已出库未推财务的数据.");
            }
            Date nowDate = new Date();
            OpsAsPdCompensateDataDO opsAsPdCompensateDataDO ;
            for (OpsAsPdCompensateDataDO item : opsAsPdCompensateDataDOS) {
                opsAsPdCompensateDataDO = new OpsAsPdCompensateDataDO();
                opsAsPdCompensateDataDO.setPdBatchNo(batchNo);
                opsAsPdCompensateDataDO.setOrderNo(item.getOrderNo());
                opsAsPdCompensateDataDO.setRorderNo(item.getRorderNo());
                opsAsPdCompensateDataDO.setItemNo(item.getItemNo());
                opsAsPdCompensateDataDO.setWarehouseCode(item.getWarehouseCode());
                opsAsPdCompensateDataDO.setModelNo(item.getModelNo());
                opsAsPdCompensateDataDO.setQty(item.getQty());
                opsAsPdCompensateDataDO.setPdDataType(StringUtils.isNotBlank(item.getPdDataType()) ? item.getPdDataType().trim(): "");
                opsAsPdCompensateDataDO.setPdDataSource(StringUtils.isNotBlank(item.getPdDataSource()) ? item.getPdDataSource().trim(): "");
                opsAsPdCompensateDataDO.setCreateTime(nowDate);
                opsAsPdCompensateDataDO.setUpdateTime(nowDate);
                opsAsPdCompensateDataDO.setCreateUser("sys");
                opsAsPdCompensateDataDO.setUpdateUser("sys");
                opsAsPdCompensateDataMapper.insert(opsAsPdCompensateDataDO);
            }
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());

            commonMapper.insertSysLog("年度已出库未推财务数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        } catch (Exception e) {
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.failure("已出库未推财务的数据抽取失败");
        } finally {
            redissonUtil.unlock(key);
        }
        return ResultVo.success("已出库未推财务的数据抽取完毕");
    }

    @Override
    public ResultVo<String> cwbcData(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.cwbc.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取财务补偿数据源失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.cwbc.getCode();

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("财务补偿数据正在执行中,请勿重复执行.");
            }
            redissonUtil.lock(key,30*60);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.cwbc.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到财务补偿程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
                jobExecTime = new Date();
            }
            LoginUserDTO loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"3","3");
            // 1 自动执行  0手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            String year = batchNo.substring(2, 6);
            String month = batchNo.substring(6, 8);

            String pdDate = year+"-"+month+"-"+"01";

            List<OpsAsPdCompensateDataDO> listCwDataNotSureSales = opsAsPdCompensateDataMapper.cwData(PublicUtil.getNextMonthDate(pdDate),
                                                                                                year,month,dataSourceByCode);

            if (CollectionUtils.isEmpty(listCwDataNotSureSales)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无财务补偿的数据.");
            }
            Date nowDate = new Date();
            OpsAsPdCompensateDataDO opsAsPdCompensateDataDO ;
            for (OpsAsPdCompensateDataDO item : listCwDataNotSureSales) {
                opsAsPdCompensateDataDO = new OpsAsPdCompensateDataDO();
                opsAsPdCompensateDataDO.setPdBatchNo(batchNo);
                opsAsPdCompensateDataDO.setOrderNo(item.getOrderNo());
                opsAsPdCompensateDataDO.setRorderNo(item.getRorderNo());
                opsAsPdCompensateDataDO.setItemNo(item.getItemNo());
                opsAsPdCompensateDataDO.setWarehouseCode(item.getWarehouseCode());
                opsAsPdCompensateDataDO.setModelNo(item.getModelNo());
                opsAsPdCompensateDataDO.setQty(item.getQty());
                opsAsPdCompensateDataDO.setPdDataType("3");
                opsAsPdCompensateDataDO.setPdDataSource("3");
                opsAsPdCompensateDataDO.setCreateTime(nowDate);
                opsAsPdCompensateDataDO.setUpdateTime(nowDate);
                opsAsPdCompensateDataDO.setCreateUser("sys");
                opsAsPdCompensateDataDO.setUpdateUser("sys");
                opsAsPdCompensateDataMapper.insert(opsAsPdCompensateDataDO);
            }

            commonMapper.insertSysLog("年度财务补偿数据抽取",loginAuthDto.getUserNo(),PublicUtil.getNextMonthDate(pdDate)+"&"+year+"&"+month+"&"+dataSourceByCode);

        } catch (Exception e) {
            log.info("财务补偿数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.failure("财务补偿数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }
        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());

        return ResultVo.success("财务补偿数据抽取完毕");
    }

    @Override
    public ResultVo<String> dbztData(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.db.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取OPS调拨在途数据源失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.db.getCode();

        try {

           if (redissonUtil.isLock(key)) {
                return ResultVo.success("OPS调拨在途数据正在执行中,请勿重复执行.");
            }
           redissonUtil.lock(key,30*60);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.db.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到OPS调拨在途程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                jobExecTime = new Date();
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"4","2");

            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            List<OpsAsPdCompensateDataDO> list = opsAsPdCompensateDataMapper.dbztWithOps(dataSourceByCode);

            if (CollectionUtils.isEmpty(list)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无OPS调拨的数据.");
            }
            Date nowDate = new Date();
            OpsAsPdCompensateDataDO opsAsPdCompensateDataDO ;
            for (OpsAsPdCompensateDataDO item : list) {
                opsAsPdCompensateDataDO = new OpsAsPdCompensateDataDO();
                opsAsPdCompensateDataDO.setPdBatchNo(batchNo);
                opsAsPdCompensateDataDO.setWarehouseCode(item.getWarehouseCode());
                opsAsPdCompensateDataDO.setModelNo(item.getModelNo());
                opsAsPdCompensateDataDO.setQty(item.getQty());
                opsAsPdCompensateDataDO.setPdDataType(item.getPdDataType());
                opsAsPdCompensateDataDO.setPdDataSource(item.getPdDataSource());
                opsAsPdCompensateDataDO.setRorderNo(item.getRorderNo());
                opsAsPdCompensateDataDO.setItemNo(item.getItemNo());
                opsAsPdCompensateDataDO.setSplitItemNo(item.getSplitItemNo());
                opsAsPdCompensateDataDO.setCreateTime(nowDate);
                opsAsPdCompensateDataDO.setUpdateTime(nowDate);
                opsAsPdCompensateDataDO.setCreateUser("sys");
                opsAsPdCompensateDataDO.setUpdateUser("sys");
                opsAsPdCompensateDataMapper.insert(opsAsPdCompensateDataDO);
            }
            commonMapper.insertSysLog("年度OPS调拨在途数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        } catch (Exception e) {
            log.info("OPS调拨在途数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.success("OPS调拨在途数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }
        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
        return ResultVo.success("OPS调拨在途数据抽取完毕");
    }

    @Override
    public ResultVo<String> zzztData(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.zz.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取制造在途数据源失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.zz.getCode();

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("制造在途数据正在执行中,请勿重复执行.");
            }

            redissonUtil.lock(key,30*60);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.zz.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到制造在途程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                jobExecTime = new Date();
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"5","2");

            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            List<OpsAsPdCompensateDataDO> list = opsAsPdCompensateDataMapper.zzztWithOps(dataSourceByCode);

            if (CollectionUtils.isEmpty(list)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无制造的数据.");
            }
            Date nowDate = new Date();
            OpsAsPdCompensateDataDO opsAsPdCompensateDataDO ;
            for (OpsAsPdCompensateDataDO item : list) {
                opsAsPdCompensateDataDO = new OpsAsPdCompensateDataDO();
                opsAsPdCompensateDataDO.setPdBatchNo(batchNo);
                opsAsPdCompensateDataDO.setWarehouseCode(item.getWarehouseCode());
                opsAsPdCompensateDataDO.setModelNo(item.getModelNo());
                opsAsPdCompensateDataDO.setQty(item.getQty());
                opsAsPdCompensateDataDO.setPdDataType(item.getPdDataType());
                opsAsPdCompensateDataDO.setPdDataSource(item.getPdDataSource());
                opsAsPdCompensateDataDO.setRorderNo(item.getRorderNo());
                opsAsPdCompensateDataDO.setItemNo(item.getItemNo());
                opsAsPdCompensateDataDO.setSplitItemNo(item.getSplitItemNo());
                opsAsPdCompensateDataDO.setCreateTime(nowDate);
                opsAsPdCompensateDataDO.setUpdateTime(nowDate);
                opsAsPdCompensateDataDO.setCreateUser("sys");
                opsAsPdCompensateDataDO.setUpdateUser("sys");
                opsAsPdCompensateDataMapper.insert(opsAsPdCompensateDataDO);
            }

            commonMapper.insertSysLog("年度制造在途数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        } catch (Exception e) {
            log.info("制造在途数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.success("制造在途数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }
        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
        return ResultVo.success("制造在途数据抽取完毕");
    }

    @Override
    public ResultVo<String> wmszzztData(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.wmszz.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取wms制造在途数据源失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.wmszz.getCode();

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("WMS制造在途数据正在执行中,请勿重复执行.");
            }
            redissonUtil.lock(key,30*60);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.wmszz.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到制造在途程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                jobExecTime = new Date();
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"5","1");

            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            List<OpsAsPdCompensateDataDO> list = opsAsPdCompensateDataMapper.zzztWithWms(dataSourceByCode,batchNo);

            if (CollectionUtils.isEmpty(list)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无制造的数据.");
            }
            Date nowDate = new Date();
            OpsAsPdCompensateDataDO opsAsPdCompensateDataDO ;
            for (OpsAsPdCompensateDataDO item : list) {
                opsAsPdCompensateDataDO = item;
                opsAsPdCompensateDataDO.setPdBatchNo(batchNo);
                opsAsPdCompensateDataDO.setId(null);
                opsAsPdCompensateDataDO.setCreateTime(nowDate);
                opsAsPdCompensateDataDO.setUpdateTime(nowDate);
                opsAsPdCompensateDataDO.setCreateUser("sys");
                opsAsPdCompensateDataDO.setUpdateUser("sys");
                opsAsPdCompensateDataMapper.insert(opsAsPdCompensateDataDO);
            }
            commonMapper.insertSysLog("年度wms制造在途数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);
        } catch (Exception e) {
            log.info("wms制造在途数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.success("wms制造在途数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }
        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
        return ResultVo.success("wms制造在途数据抽取完毕");
    }

    @Override
    public ResultVo<String> wmsZHztData(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.wmszhzt.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取wms组换在途数据源失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.wmszhzt.getCode();

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("WMS组换在途数据正在执行中,请勿重复执行.");
            }
            redissonUtil.lock(key,30*60);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.wmszhzt.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到wms组换在途程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                jobExecTime = new Date();
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"7","1");

            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            List<OpsAsPdCompensateDataDO> list = opsAsPdCompensateDataMapper.wmszhztData(dataSourceByCode, batchNo);

            if (CollectionUtils.isEmpty(list)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无wms组换在途的数据.");
            }
            Date nowDate = new Date();
            OpsAsPdCompensateDataDO opsAsPdCompensateDataDO ;
            for (OpsAsPdCompensateDataDO item : list) {
                opsAsPdCompensateDataDO = item;
                opsAsPdCompensateDataDO.setPdBatchNo(batchNo);
                opsAsPdCompensateDataDO.setId(null);
                opsAsPdCompensateDataDO.setCreateTime(nowDate);
                opsAsPdCompensateDataDO.setUpdateTime(nowDate);
                opsAsPdCompensateDataDO.setCreateUser("sys");
                opsAsPdCompensateDataDO.setUpdateUser("sys");
                opsAsPdCompensateDataMapper.insert(opsAsPdCompensateDataDO);
            }
            commonMapper.insertSysLog("年度wms组换在途数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        } catch (Exception e) {
            log.info("wms组换在途数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.success("wms组换在途数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }
        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
        return ResultVo.success("wms组换在途数据抽取完毕");
    }

    @Override
    public ResultVo<String> opsZHztData(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.opszhzt.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取OPS组换在途数据源失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.opszhzt.getCode();

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("OPS组换在途数据正在执行中,请勿重复执行.");
            }

            redissonUtil.lock(key,30*60);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.opszhzt.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到OPS组换在途程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                jobExecTime = new Date();
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"7","2");

            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            List<OpsAsPdCompensateDataDO> list = opsAsPdCompensateDataMapper.opszhzt(dataSourceByCode);

            if (CollectionUtils.isEmpty(list)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无OPS组换在途的数据.");
            }
            Date nowDate = new Date();
            OpsAsPdCompensateDataDO opsAsPdCompensateDataDO ;
            for (OpsAsPdCompensateDataDO item : list) {
                opsAsPdCompensateDataDO = new OpsAsPdCompensateDataDO();
                opsAsPdCompensateDataDO.setPdBatchNo(batchNo);
                opsAsPdCompensateDataDO.setWarehouseCode(item.getWarehouseCode());
                opsAsPdCompensateDataDO.setModelNo(item.getModelNo());
                opsAsPdCompensateDataDO.setQty(item.getQty());
                opsAsPdCompensateDataDO.setPdDataType(item.getPdDataType());
                opsAsPdCompensateDataDO.setPdDataSource(item.getPdDataSource());
                opsAsPdCompensateDataDO.setRorderNo(item.getRorderNo());
                opsAsPdCompensateDataDO.setItemNo(item.getItemNo());
                opsAsPdCompensateDataDO.setSplitItemNo(item.getSplitItemNo());
                opsAsPdCompensateDataDO.setCreateTime(nowDate);
                opsAsPdCompensateDataDO.setUpdateTime(nowDate);
                opsAsPdCompensateDataDO.setCreateUser("sys");
                opsAsPdCompensateDataDO.setUpdateUser("sys");
                opsAsPdCompensateDataMapper.insert(opsAsPdCompensateDataDO);
            }

            commonMapper.insertSysLog("OPS组换在途数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        } catch (Exception e) {
            log.info("OPS组换在途数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.success("OPS组换在途数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }
        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
        return ResultVo.success("OPS组换在途数据抽取完毕");
    }

    @Override
    public ResultVo<String> opsZHdhwr(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.opszhdhwr.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取ops组换到货未入数据源失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.opszhdhwr.getCode();

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("OPS组换到货未入数据正在执行中,请勿重复执行.");
            }

            redissonUtil.lock(key,30*60);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.opszhdhwr.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到ops组换到货未入数据程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                jobExecTime = new Date();
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            opsAsOpsInventoryDataMapper.delOpsAsOpsInventoryData("5",batchNo);

            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            List<OpsAsOpsInventoryDataDO> opsAsOpsInventoryDataDOS = opsAsOpsInventoryDataMapper.opszhdhwr(dataSourceByCode);
            if (CollectionUtils.isEmpty(opsAsOpsInventoryDataDOS)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无ops组换到货未入数据.");
            }
            Date nowDate = new Date();
            OpsAsOpsInventoryDataDO opsAsOpsInventoryDataDO ;
            for (OpsAsOpsInventoryDataDO item : opsAsOpsInventoryDataDOS) {
                opsAsOpsInventoryDataDO = new OpsAsOpsInventoryDataDO();
                opsAsOpsInventoryDataDO.setPdBatchNo(batchNo);
                opsAsOpsInventoryDataDO.setWarehouseCode(item.getWarehouseCode());
                opsAsOpsInventoryDataDO.setModelNo(item.getModelNo());
                opsAsOpsInventoryDataDO.setQty(item.getQty());
                opsAsOpsInventoryDataDO.setDataType(item.getDataType());
                opsAsOpsInventoryDataDO.setCreateTime(nowDate);
                opsAsOpsInventoryDataDO.setUpdateTime(nowDate);
                opsAsOpsInventoryDataDO.setCreateUser("sys");
                opsAsOpsInventoryDataDO.setUpdateUser("sys");
                opsAsOpsInventoryDataMapper.insert(opsAsOpsInventoryDataDO);
            }
            commonMapper.insertSysLog("年度OPS组换到货未入数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);
        } catch (Exception e) {
            log.info("ops组换到货未入数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.success("ops组换到货未入数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }
        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
        return ResultVo.success("ops组换到货未入数据抽取完毕");
    }

    @Override
    public ResultVo<String> opsArriveNotInWithCG(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.cgdhwr.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取ops采购到货未入数据源失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.cgdhwr.getCode();

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("OPS采购到货未入数据正在执行中,请勿重复执行.");
            }

            redissonUtil.lock(key,30*60);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.cgdhwr.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到ops采购到货未入数据程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                jobExecTime = new Date();
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            opsAsOpsInventoryDataMapper.delOpsAsOpsInventoryData("2",batchNo);

            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            List<OpsAsOpsInventoryDataDO> opsAsOpsInventoryDataDOS = opsAsOpsInventoryDataMapper.opsArriveNotInWithCG(dataSourceByCode);
            if (CollectionUtils.isEmpty(opsAsOpsInventoryDataDOS)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无ops采购到货未入数据.");
            }
            Date nowDate = new Date();
            OpsAsOpsInventoryDataDO opsAsOpsInventoryDataDO ;
            for (OpsAsOpsInventoryDataDO item : opsAsOpsInventoryDataDOS) {
                opsAsOpsInventoryDataDO = new OpsAsOpsInventoryDataDO();
                opsAsOpsInventoryDataDO.setPdBatchNo(batchNo);
                opsAsOpsInventoryDataDO.setWarehouseCode(item.getWarehouseCode());
                opsAsOpsInventoryDataDO.setModelNo(item.getModelNo());
                opsAsOpsInventoryDataDO.setQty(item.getQty());
                opsAsOpsInventoryDataDO.setDataType(item.getDataType());
                opsAsOpsInventoryDataDO.setCreateTime(nowDate);
                opsAsOpsInventoryDataDO.setUpdateTime(nowDate);
                opsAsOpsInventoryDataDO.setCreateUser("sys");
                opsAsOpsInventoryDataDO.setUpdateUser("sys");
                opsAsOpsInventoryDataMapper.insert(opsAsOpsInventoryDataDO);
            }
            commonMapper.insertSysLog("年度OPS采购到货未入数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);
        } catch (Exception e) {
            log.info("ops采购到货未入数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.success("ops采购到货未入数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }
        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
        return ResultVo.success("ops采购到货未入数据抽取完毕");
    }

    @Override
    public ResultVo<String> opsArriveNotInWithTH(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.thdhwr.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取ops退货到货未入数据源失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.thdhwr.getCode();

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("OPS退货到货未入数据正在执行中,请勿重复执行.");
            }

            redissonUtil.lock(key,30*60);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.thdhwr.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到ops退货到货未入数据程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                jobExecTime = new Date();
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            opsAsOpsInventoryDataMapper.delOpsAsOpsInventoryData("4",batchNo);

            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            List<OpsAsOpsInventoryDataDO> opsAsOpsInventoryDataDOS = opsAsOpsInventoryDataMapper.opsArriveNotInWithTH(dataSourceByCode);
            if (CollectionUtils.isEmpty(opsAsOpsInventoryDataDOS)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无ops采购到货未入数据.");
            }
            Date nowDate = new Date();
            OpsAsOpsInventoryDataDO opsAsOpsInventoryDataDO ;
            for (OpsAsOpsInventoryDataDO item : opsAsOpsInventoryDataDOS) {
                opsAsOpsInventoryDataDO = new OpsAsOpsInventoryDataDO();
                opsAsOpsInventoryDataDO.setPdBatchNo(batchNo);
                opsAsOpsInventoryDataDO.setWarehouseCode(item.getWarehouseCode());
                opsAsOpsInventoryDataDO.setModelNo(item.getModelNo());
                opsAsOpsInventoryDataDO.setQty(item.getQty());
                opsAsOpsInventoryDataDO.setDataType(item.getDataType());
                opsAsOpsInventoryDataDO.setCreateTime(nowDate);
                opsAsOpsInventoryDataDO.setUpdateTime(nowDate);
                opsAsOpsInventoryDataDO.setCreateUser("sys");
                opsAsOpsInventoryDataDO.setUpdateUser("sys");
                opsAsOpsInventoryDataMapper.insert(opsAsOpsInventoryDataDO);
            }
            commonMapper.insertSysLog("年度ops退货到货未入数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);
        } catch (Exception e) {
            log.info("ops退货到货未入数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.success("ops退货到货未入数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }
        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
        return ResultVo.success("ops退货到货未入数据抽取完毕");
    }

    @Override
    public ResultVo<String> opsArriveNotInWithDB(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.dbdhwr.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取ops调拨到货未入数据源失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.dbdhwr.getCode();

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("OPS调拨到货未入数据正在执行中,请勿重复执行.");
            }

            redissonUtil.lock(key,30*60);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.dbdhwr.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到ops调拨到货未入数据程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                jobExecTime = new Date();
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            opsAsOpsInventoryDataMapper.delOpsAsOpsInventoryData("3",batchNo);

            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            List<OpsAsOpsInventoryDataDO> opsAsOpsInventoryDataDOS = opsAsOpsInventoryDataMapper.opsArriveNotInWithDB(dataSourceByCode);
            if (CollectionUtils.isEmpty(opsAsOpsInventoryDataDOS)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无ops调拨到货未入数据.");
            }
            Date nowDate = new Date();
            OpsAsOpsInventoryDataDO opsAsOpsInventoryDataDO ;
            for (OpsAsOpsInventoryDataDO item : opsAsOpsInventoryDataDOS) {
                opsAsOpsInventoryDataDO = new OpsAsOpsInventoryDataDO();
                opsAsOpsInventoryDataDO.setPdBatchNo(batchNo);
                opsAsOpsInventoryDataDO.setWarehouseCode(item.getWarehouseCode());
                opsAsOpsInventoryDataDO.setModelNo(item.getModelNo());
                opsAsOpsInventoryDataDO.setQty(item.getQty());
                opsAsOpsInventoryDataDO.setDataType(item.getDataType());
                opsAsOpsInventoryDataDO.setCreateTime(nowDate);
                opsAsOpsInventoryDataDO.setUpdateTime(nowDate);
                opsAsOpsInventoryDataDO.setCreateUser("sys");
                opsAsOpsInventoryDataDO.setUpdateUser("sys");
                opsAsOpsInventoryDataMapper.insert(opsAsOpsInventoryDataDO);
            }
            commonMapper.insertSysLog("年度ops调拨到货未入数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);
        } catch (Exception e) {
            log.info("ops调拨到货未入数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.success("ops调拨到货未入数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }
        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
        return ResultVo.success("ops调拨到货未入数据抽取完毕");
    }

    @Override
    public ResultVo<String> opsInventory(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.opsData.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取OPS库存数据源失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.opsData.getCode();

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("OPS库存数据正在执行中,请勿重复执行.");
            }

            redissonUtil.lock(key,30*60);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.opsData.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到OPS库存数据程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                jobExecTime = new Date();
               // return ResultVo.failure("请检查执行时间格式配置的是否正确");
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            opsAsOpsInventoryDataMapper.delOpsAsOpsInventoryData("1",batchNo);

            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            List<OpsAsOpsInventoryDataDO> opsAsOpsInventoryDataDOS = opsAsOpsInventoryDataMapper.opsInventory(dataSourceByCode);
            if (CollectionUtils.isEmpty(opsAsOpsInventoryDataDOS)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无ops库存数据抽取的数据.");
            }
            Date nowDate = new Date();
            OpsAsOpsInventoryDataDO opsAsOpsInventoryDataDO ;
            for (OpsAsOpsInventoryDataDO item : opsAsOpsInventoryDataDOS) {
                opsAsOpsInventoryDataDO = new OpsAsOpsInventoryDataDO();
                opsAsOpsInventoryDataDO.setPdBatchNo(batchNo);
                opsAsOpsInventoryDataDO.setWarehouseCode(item.getWarehouseCode());
                opsAsOpsInventoryDataDO.setModelNo(item.getModelNo());
                opsAsOpsInventoryDataDO.setQty(item.getQty());
                opsAsOpsInventoryDataDO.setDataType("1");
                opsAsOpsInventoryDataDO.setCreateTime(nowDate);
                opsAsOpsInventoryDataDO.setUpdateTime(nowDate);
                opsAsOpsInventoryDataDO.setCreateUser("sys");
                opsAsOpsInventoryDataDO.setUpdateUser("sys");
                opsAsOpsInventoryDataMapper.insert(opsAsOpsInventoryDataDO);
            }

            commonMapper.insertSysLog("年度OPS库存数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        } catch (Exception e) {
            log.info("ops库存数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.success("ops库存数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }
        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
        return ResultVo.success("ops库存数据抽取完毕");
    }

    @Override
    public ResultVo<String> wmsbcData(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.wmsbc.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取WMS补偿数据失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.wmsbc.getCode();

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("WMS补偿数据正在执行中,请勿重复执行.");
            }

            redissonUtil.lock(key,30*60);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.wmsbc.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到WMS补偿程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                jobExecTime = new Date();
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            opsAsPdCompensateDataMapper.clearOpsCompensateDataWithWMS(batchNo,"1");

            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            Page page = new Page();
            page.setPageNumber(1);
            page.setPageSize(6000);
            PageInfo<OpsAsPdCompensateDataDO> pageInfo;
            while (true) {
                pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                        .doSelectPageInfo(() -> {
                            opsAsPdCompensateDataMapper.selWmsBcData(batchNo);
                        });
                if (CollectionUtils.isEmpty(pageInfo.getList())) {
                    upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                    return ResultVo.success("暂无wms补偿数据");
                }
                for (OpsAsPdCompensateDataDO item : pageInfo.getList()) {
                        item.setPdBatchNo(batchNo);
                        item.setCreateUser("wms");
                        item.setUpdateUser("wms");
                        item.setCreateTime(new Date());
                        item.setUpdateTime(new Date());
                        item.setPdDataSource("1");
                    opsAsPdCompensateDataMapper.insert(item);
                }
    //            Map<String,List<OpsAsPdCompensateDataDO>> map = groupList(pageInfo.getList(), 100);
    //            for(String key : map.keySet()) {
    //                for (OpsAsPdCompensateDataDO item : map.get(key)) {
    //                    item.setPdBatchNo(batchNo);
    //                    item.setCreateUser("wms");
    //                    item.setUpdateUser("wms");
    //                    item.setCreateTime(new Date());
    //                    item.setUpdateTime(new Date());
    //                    item.setPdDataSource("1");
    //                }
    //                opsAsPdCompensateDataMapper.batchInsertCompensateData(map.get(key),batchNo);
    //            }
                if (pageInfo.isIsLastPage()) {
                    break;
                }
                page.setPageNumber(page.getPageNumber() + 1);
            }
            commonMapper.insertSysLog("年度WMS补偿数据抽取",loginAuthDto.getUserNo(),"");
        } catch (Exception e) {
            log.info("WMS补偿数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.success("WMS补偿数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }
        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
        return ResultVo.success("WMS补偿数据抽取完毕");
    }

    @Override
    public ResultVo<String> opsReturnData(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.opsReturnData.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取ops退货在途数据源失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.opsReturnData.getCode();

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("OPS退货在途数据正在执行中,请勿重复执行.");
            }
            redissonUtil.lock(key);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.opsReturnData.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到ops退货在途数据程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                jobExecTime = new Date();
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            opsAsPdCompensateDataMapper.clearOpsReturnCompensateData(batchNo,"2","6");

            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            List<OpsAsPdCompensateDataDO> list = opsAsPdCompensateDataMapper.selOpsReturnData(dataSourceByCode);
            if (CollectionUtils.isEmpty(list)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无ops退货在途数据");
            }
            for (OpsAsPdCompensateDataDO item : list) {
                item.setPdBatchNo(batchNo);
                item.setCreateUser(CommonConstants.COMMON_USER_OPS);
                item.setUpdateUser(CommonConstants.COMMON_USER_OPS);
                item.setCreateTime(new Date());
                item.setUpdateTime(new Date());
                opsAsPdCompensateDataMapper.insert(item);
            }
            commonMapper.insertSysLog("年度OPS退货在途数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        } catch (Exception e) {
            log.info("ops退货在途数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.success("ops退货在途数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }

        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
        return ResultVo.success("ops退货在途数据抽取完毕");
    }

    @Override
    public ResultVo<String> wmsReturnData(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;

        String dataSourceByCode = getDataSourceByCode(PdOtherDataImpEnum.wmsReturnData.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取wms退货在途数据源失败.");
        }
        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.wmsReturnData.getCode();

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("WMS退货在途数据正在执行中,请勿重复执行.");
            }

            redissonUtil.lock(key);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.wmsReturnData.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到wms退货在途数据程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                jobExecTime = new Date();
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }

            opsAsPdCompensateDataMapper.clearOpsReturnCompensateData(batchNo,"1","6");

            // 1 自动执行  0 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }

            List<OpsAsPdCompensateDataDO> list = opsAsPdCompensateDataMapper.selWmsReturnData(batchNo);

            if (CollectionUtils.isEmpty(list)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无wms退货在途数据");
            }
            for (OpsAsPdCompensateDataDO item : list) {
                opsAsPdCompensateDataMapper.insert(item);
            }

            commonMapper.insertSysLog("年度WMS退货在途数据抽取",loginAuthDto.getUserNo(),"");

        } catch (Exception e) {
            log.info("wms退货在途数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.success("wms退货在途数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }

        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
        return ResultVo.success("wms退货在途数据抽取完毕");
    }

    @Override
    public ResultVo<String> cwBalance(String flag) {
        if (StringUtils.isBlank(flag)) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAsPdOtherdataDO opsAsPdOtherdataDO = null;
        LoginUserDTO loginAuthDto = null;

        // 获取当前盘点批次号
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = batchNoWithIsActive.getData().getPdBatchNo();

        String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.cwjc.getCode();

        try {

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("财务结存数据正在执行中,请勿重复执行.");
            }

            redissonUtil.lock(key);

            // 判断当前作业的执行状态 若已经执行过 则需要清空当前批次的数据
            opsAsPdOtherdataDO = getPdOtherDataImpStatus(PdOtherDataImpEnum.cwjc.getCode(), batchNo);
            if (opsAsPdOtherdataDO == null) {
                return ResultVo.failure("暂未找到财务结存数据程序的配置");
            }
            Date jobExecTime = opsAsPdOtherdataDO.getJobExecTime();
            if (jobExecTime == null) {
                jobExecTime = new Date();
                // return ResultVo.failure("请检查执行时间格式配置的是否正确");
            }
            loginAuthDto = new LoginUserDTO();
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto.setUserNo("sys");
            }


            /**
             * 删除该批次的sharedb库的财务结存数据
             */
            opsAsFinanceBlanceDataMapper.delCwjcDataSharedb(batchNo);

            /**
             * 删除该批次report财务结存数据
             */
            opsAsFinanceBlanceDataMapper.delCwjcData(batchNo);

            // 1 自动执行  2 手动执行
            if ("1".equals(flag)) {
                if (jobExecTime.after(new Date())) {
                    return ResultVo.failure(opsAsPdOtherdataDO.getImpDataType()+"未到执行时间,暂不执行.");
                }
                loginAuthDto.setUserNo("sys");
            }
            /**
             * 自动化结存
             */
            List<OpsAsFinanceBlanceDataDO> cwjcData = opsAsFinanceBlanceDataMapper.getCwjcData(batchNo.trim().substring(2,8));
            if (CollectionUtils.isEmpty(cwjcData)) {
                upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
                return ResultVo.success("暂无自动化财务结存数据抽取的数据.");
            }
            /**
             * 广州制造结存
             */
            List<OpsAsFinanceBlanceDataDO> gzCwjcData = opsAsFinanceBlanceDataMapper.getGzCwjcData(batchNo.trim().substring(2,8));

            if(CollectionUtils.isNotEmpty(gzCwjcData)) {
                cwjcData.addAll(gzCwjcData);
            }


            Date nowDate = new Date();
            OpsAsFinanceBlanceDataDO opsAsFinanceBlanceDataDO ;
            for (OpsAsFinanceBlanceDataDO item : cwjcData) {
                opsAsFinanceBlanceDataDO = new OpsAsFinanceBlanceDataDO();
                opsAsFinanceBlanceDataDO.setPdBatchNo(batchNo);
                opsAsFinanceBlanceDataDO.setModelNo(item.getModelNo());
                opsAsFinanceBlanceDataDO.setBalanceQty(item.getBalanceQty());
                opsAsFinanceBlanceDataDO.setCreateTime(nowDate);
                opsAsFinanceBlanceDataDO.setUpdateTime(nowDate);
                opsAsFinanceBlanceDataDO.setCreateUser("sys");
                opsAsFinanceBlanceDataDO.setUpdateUser("sys");
                opsAsFinanceBlanceDataDO.setDataSource(item.getDataSource());
                /**
                 * 写入sharedb 库
                 */
                opsAsFinanceBlanceDataMapperSharedb.insert(opsAsFinanceBlanceDataDO);
                /**
                 * 写入report库
                 */
                opsAsFinanceBlanceDataMapper.insert(opsAsFinanceBlanceDataDO);
            }
            commonMapper.insertSysLog("年度财务结存数据抽取",loginAuthDto.getUserNo(),batchNo.trim().substring(2,8));
        } catch (NumberFormatException e) {
            log.info("财务结存数据抽取失败 {}",e.getMessage(),e);
            upOtherDataImpStatus(opsAsPdOtherdataDO.getId(), OtherDataExcResultEnum.exc_fail.getCode());
            return ResultVo.success("财务结存数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }
        upOtherDataImpStatus(opsAsPdOtherdataDO.getId(),OtherDataExcResultEnum.exc_success.getCode());
        return ResultVo.success("财务结存数据抽取完毕");
    }

    public OpsAsPdOtherdataDO getPdOtherDataImpStatus(String methodCode,String pdBatchNo) {
        LambdaQueryWrapper<OpsAsPdOtherdataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OpsAsPdOtherdataDO::getPdBatchNo,pdBatchNo)
                .eq(OpsAsPdOtherdataDO::getDelFlag,false)
                .eq(OpsAsPdOtherdataDO::getMethodCode,methodCode);
        return opsAsPdOtherdataMapper.selectOne(queryWrapper);
    }

    public void upOtherDataImpStatus(int id,String execFlag) {
        LambdaUpdateWrapper<OpsAsPdOtherdataDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper
                .eq(OpsAsPdOtherdataDO::getId,id)
                .set(OpsAsPdOtherdataDO::getUpdateTime,new Date())
                .set(OpsAsPdOtherdataDO::getExecFlag,execFlag);
                // .set(OpsAsPdOtherdataDO::getUpdateUser,optUserNo);
        opsAsPdOtherdataMapper.update(null,lambdaUpdateWrapper);
    }

    @Override
    public ResultVo<String> execPdTaskImpData() {
        LambdaQueryWrapper<OpsAsPdOtherdataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsAsPdOtherdataDO::getDelFlag,false).eq(OpsAsPdOtherdataDO::getExecFlag,"0");
        List<OpsAsPdOtherdataDO> opsAsPdOtherdataDOS = opsAsPdOtherdataMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(opsAsPdOtherdataDOS)) {
            return ResultVo.failure("暂无所需执行的定时作业.");
        }
        StringBuilder msg = new StringBuilder();
        for (OpsAsPdOtherdataDO item : opsAsPdOtherdataDOS) {
            ResultVo<String> resultVo = null;
            if (PdOtherDataImpEnum.yp.getCode().equals(item.getMethodCode())) {
                resultVo = impSampleorderDataToOpsCompensateData("1");
            } else if (PdOtherDataImpEnum.yck.getCode().equals(item.getMethodCode())) {
                resultVo = yckNotpushCwData("1");
            } else if (PdOtherDataImpEnum.cwbc.getCode().equals(item.getMethodCode())) {
                resultVo = cwbcData("1");
            } else if (PdOtherDataImpEnum.opsData.getCode().equals(item.getMethodCode())) {
                resultVo = opsInventory("1");
            } else if (PdOtherDataImpEnum.cwjc.getCode().equals(item.getMethodCode())) {
                resultVo = cwBalance("1");
            } else if (PdOtherDataImpEnum.cgdhwr.getCode().equals(item.getMethodCode())) {
                resultVo = opsArriveNotInWithCG("1");
            } else if (PdOtherDataImpEnum.thdhwr.getCode().equals(item.getMethodCode())) {
                resultVo = opsArriveNotInWithTH("1");
            } else if (PdOtherDataImpEnum.dbdhwr.getCode().equals(item.getMethodCode())) {
                resultVo = opsArriveNotInWithDB("1");
            } else if (PdOtherDataImpEnum.db.getCode().equals(item.getMethodCode())) {
                resultVo = dbztData("1");
            } else if (PdOtherDataImpEnum.zz.getCode().equals(item.getMethodCode())) {
                resultVo = zzztData("1");
            } else if (PdOtherDataImpEnum.wmsbc.getCode().equals(item.getMethodCode())) {
                resultVo = wmsbcData("1");
            } else if (PdOtherDataImpEnum.opsReturnData.getCode().equals(item.getMethodCode())) {
                resultVo = opsReturnData("1");
            } else if (PdOtherDataImpEnum.wmsReturnData.getCode().equals(item.getMethodCode())) {
                resultVo = wmsReturnData("1");
            } else if (PdOtherDataImpEnum.wmszz.getCode().equals(item.getMethodCode())) {
                resultVo = wmszzztData("1");
            }

            if (resultVo != null) {
                if(resultVo.isSuccess()) {
                    msg.append(resultVo.getData()+" ");
                } else {
                    msg.append(resultVo.getMessage()+" ");
                }
            }
        }
        return ResultVo.success(msg.toString());
    }

    @Override
    public ResultVo<String> exportOtherData(String methodCode) {
        if (StringUtils.isBlank(methodCode)) {
            return ResultVo.failure("入参不可为空.");
        }
        ResultVo<String> resultVo = null;
        if (methodCode.equals(PdOtherDataImpEnum.yp.getCode())) {
            resultVo = impSampleorderDataToOpsCompensateData("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.yck.getCode())) {
            resultVo = yckNotpushCwData("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.cwbc.getCode())) {
            resultVo = cwbcData("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.opsData.getCode())) {
            resultVo = opsInventory("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.cwjc.getCode())) {
            resultVo = cwBalance("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.cgdhwr.getCode())) {
            resultVo = opsArriveNotInWithCG("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.thdhwr.getCode())) {
            resultVo = opsArriveNotInWithTH("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.dbdhwr.getCode())) {
            resultVo = opsArriveNotInWithDB("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.db.getCode())) {
            resultVo = dbztData("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.zz.getCode())) {
            resultVo = zzztData("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.wmsbc.getCode())) {
            resultVo = wmsbcData("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.opsReturnData.getCode())) {
            resultVo = opsReturnData("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.wmsReturnData.getCode())) {
            resultVo = wmsReturnData("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.wmszz.getCode())) {
            resultVo = wmszzztData("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.wmszhzt.getCode())) {
            resultVo = wmsZHztData("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.opszhzt.getCode())) {
            resultVo = opsZHztData("0");
        } else if (methodCode.equals(PdOtherDataImpEnum.opszhdhwr.getCode())) {
            resultVo = opsZHdhwr("0");
        }
        return resultVo;
    }

    @Override
    public void exportOtherDataWithExcel(String methodCode,HttpServletResponse response) {
        if (StringUtils.isBlank(methodCode)) {
            return;
        }
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return;
        }
        if (methodCode.equals(PdOtherDataImpEnum.yp.getCode())) {
            exportCompensateData(batchNoWithIsActive.getData().getPdBatchNo(),"1","2",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.yck.getCode())) {
            exportCompensateData(batchNoWithIsActive.getData().getPdBatchNo(),"2","2",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.cwbc.getCode())) {
            exportCompensateData(batchNoWithIsActive.getData().getPdBatchNo(),"3","3",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.opsData.getCode())) {
            exportOpsInventoryData(batchNoWithIsActive.getData().getPdBatchNo(),"1",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.cwjc.getCode())) {
            exportOpsFinanceBlance(batchNoWithIsActive.getData().getPdBatchNo(),response);
        } else if (methodCode.equals(PdOtherDataImpEnum.cgdhwr.getCode())) {
            exportOpsInventoryData(batchNoWithIsActive.getData().getPdBatchNo(),"2",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.thdhwr.getCode())) {
            exportOpsInventoryData(batchNoWithIsActive.getData().getPdBatchNo(),"4",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.dbdhwr.getCode())) {
            exportOpsInventoryData(batchNoWithIsActive.getData().getPdBatchNo(),"3",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.db.getCode())) {
            exportCompensateData(batchNoWithIsActive.getData().getPdBatchNo(),"4","2",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.zz.getCode())) {
            exportCompensateData(batchNoWithIsActive.getData().getPdBatchNo(),"5","2",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.wmsbc.getCode())) {
            exportCompensateData(batchNoWithIsActive.getData().getPdBatchNo(),"4","1",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.opsReturnData.getCode())) {
            exportCompensateData(batchNoWithIsActive.getData().getPdBatchNo(),"6","2",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.wmsReturnData.getCode())) {
            exportCompensateData(batchNoWithIsActive.getData().getPdBatchNo(),"6","1",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.wmszz.getCode())) {
            exportCompensateData(batchNoWithIsActive.getData().getPdBatchNo(),"5","1",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.wmszhzt.getCode())) {
            exportCompensateData(batchNoWithIsActive.getData().getPdBatchNo(),"7","1",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.opszhzt.getCode())) {
            exportCompensateData(batchNoWithIsActive.getData().getPdBatchNo(),"7","2",response);
        } else if (methodCode.equals(PdOtherDataImpEnum.opszhdhwr.getCode())) {
            exportOpsInventoryData(batchNoWithIsActive.getData().getPdBatchNo(),"5",response);
        }
    }

    public void exportCompensateData(String pdBatchNo,String dataType,String dataSource,HttpServletResponse response) {
        List<OpsAsPdCompensateDataDO> compensateData = getCompensateData(pdBatchNo, dataType, dataSource);
        if (CollectionUtils.isEmpty(compensateData)) {
            return;
        }
        for (OpsAsPdCompensateDataDO item : compensateData) {
            if (StringUtils.isNotBlank(item.getWarehouseCode())) {
                item.setWarehouseCode("["+item.getWarehouseCode()+"]"+opsCommonService.getWarehouseNameByCode(item.getWarehouseCode()));
            }
            if (StringUtils.isNotBlank(item.getPdDataType())) {
                item.setPdDataType(conventCompensateDataType(item.getPdDataType().trim()));
            }
            if (StringUtils.isNotBlank(item.getPdDataSource())) {
                item.setPdDataSource(conventCompensateDataResource(item.getPdDataSource().trim()));
            }
        }
        List<OpsAsPdCompensateDataExportVO> opsAsPdCompensateDataExportVOS = BeanCopyUtil.copyList(compensateData, OpsAsPdCompensateDataExportVO.class);
        try {
            String fileName = URLEncoder.encode("compensateData", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");
            InputStream inputStream = new ClassPathResource(CommonConstants.other_data_export_excel).getInputStream();

            EasyExcel.write(response.getOutputStream(), OpsAsPdCompensateDataExportVO.class)
                     .withTemplate(inputStream)
                    .sheet("Sheet1").doWrite(opsAsPdCompensateDataExportVOS);
        } catch (IOException e) {
            response.reset();
            log.info("导出补偿数据发生异常 批次号:{} dataResource: {}, dataType: {}, 异常信息 {}",pdBatchNo,dataSource,dataType,e.getMessage(),e);
            throw new RuntimeException("导出数据发生异常: "+e.getMessage());
        }

    }

    public void exportOpsInventoryData(String pdBatchNo,String dataType,HttpServletResponse response) {
        List<OpsAsOpsInventoryDataDO> opsInventoryData = getOpsInventoryData(pdBatchNo, dataType);
        if (CollectionUtils.isEmpty(opsInventoryData)) {
            return;
        }
        for (OpsAsOpsInventoryDataDO item : opsInventoryData) {
            if (StringUtils.isNotBlank(item.getWarehouseCode())) {
                item.setWarehouseCode("["+item.getWarehouseCode()+"]"+opsCommonService.getWarehouseNameByCode(item.getWarehouseCode()));
            }
            if (StringUtils.isNotBlank(item.getDataType())) {
                item.setDataType(conventOpsInventoryDataType(item.getDataType().trim()));
            }
        }
        List<OpsInventoryExportVO> opsInventoryExportVOS = BeanCopyUtil.copyList(opsInventoryData, OpsInventoryExportVO.class);
        try {
            String fileName = URLEncoder.encode("opsInventoryData", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");
            InputStream inputStream = new ClassPathResource(CommonConstants.other_data_export_excel).getInputStream();

            EasyExcel.write(response.getOutputStream(), OpsInventoryExportVO.class)
                     .withTemplate(inputStream)
                     .sheet("Sheet1").doWrite(opsInventoryExportVOS);
        } catch (IOException e) {
            response.reset();
            log.info("导出补偿数据发生异常 批次号:{} dataResource: {}, 异常信息 {}",pdBatchNo,dataType,e.getMessage(),e);
            throw new RuntimeException("导出数据发生异常: "+e.getMessage());
        }

    }

    public void exportOpsFinanceBlance(String pdBatchNo,HttpServletResponse response) {
        List<OpsAsFinanceBlanceDataDO> financeBlanceData = getFinanceBlanceData(pdBatchNo);
        if (CollectionUtils.isEmpty(financeBlanceData)) {
            return;
        }
        for (OpsAsFinanceBlanceDataDO item : financeBlanceData) {
            if (StringUtils.isNotBlank(item.getWarehouseCode())) {
                item.setWarehouseCode("["+item.getWarehouseCode()+"]"+opsCommonService.getWarehouseNameByCode(item.getWarehouseCode()));
            }
        }
        List<OpsFinanceBlanceExportVO> opsFinanceBlanceExportVOS = BeanCopyUtil.copyList(financeBlanceData, OpsFinanceBlanceExportVO.class);
        try {
            String fileName = URLEncoder.encode("opsFinanceBlanceData", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");
            InputStream inputStream = new ClassPathResource(CommonConstants.other_data_export_excel).getInputStream();

            EasyExcel.write(response.getOutputStream(), OpsFinanceBlanceExportVO.class)
                    .withTemplate(inputStream)
                    .sheet("Sheet1").doWrite(opsFinanceBlanceExportVOS);
        } catch (IOException e) {
            response.reset();
            log.info("导出补偿数据发生异常 批次号:{} 异常信息 {}",pdBatchNo,e.getMessage(),e);
            throw new RuntimeException("导出数据发生异常: "+e.getMessage());
        }

    }

    // 获取补偿数据
    public List<OpsAsPdCompensateDataDO> getCompensateData(String pdBatchNo,String dataType,String dataSource) {

        LambdaQueryWrapper<OpsAsPdCompensateDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OpsAsPdCompensateDataDO::getPdBatchNo,pdBatchNo)
                .eq(StringUtils.isNotBlank(dataType), OpsAsPdCompensateDataDO::getPdDataType,dataType)
                .eq(OpsAsPdCompensateDataDO::getPdDataSource,dataSource);
       return opsAsPdCompensateDataMapper.selectList(queryWrapper);
    }

    // 获取ops库存数据
    public List<OpsAsOpsInventoryDataDO> getOpsInventoryData(String pdBatchNo,String dataType) {

        LambdaQueryWrapper<OpsAsOpsInventoryDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OpsAsOpsInventoryDataDO::getPdBatchNo,pdBatchNo)
                .eq(StringUtils.isNotBlank(dataType), OpsAsOpsInventoryDataDO::getDataType,dataType);
        return opsAsOpsInventoryDataMapper.selectList(queryWrapper);
    }

    // 获取结存数据
    public List<OpsAsFinanceBlanceDataDO> getFinanceBlanceData(String pdBatchNo) {

        LambdaQueryWrapper<OpsAsFinanceBlanceDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OpsAsFinanceBlanceDataDO::getPdBatchNo,pdBatchNo);
        return opsAsFinanceBlanceDataMapper.selectList(queryWrapper);
    }

    @Override
    public ResultVo<String> pdDataImp() {
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return ResultVo.failure("获取盘点批次号失败");
        }
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }
        pdBatchService.updatePdStateByPdbatchNo(batchNoWithIsActive.getData().getPdBatchNo(),PdStateEnum.sjcqOk.getCode(),loginAuthDto.getUserNo());
        return ResultVo.success("数据抽取完毕.");
    }

    @Override
    public ResultVo<String> startExtractData() {
        LambdaQueryWrapper<OpsAsWmsTaskNoticeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsAsWmsTaskNoticeDO::getJobName,"ops_wms_inventoryData");
        OpsAsWmsTaskNoticeDO opsAsWmsTaskNoticeDO = opsAsWmsTaskNoticeMapper.selectOne(queryWrapper);
        if (opsAsWmsTaskNoticeDO == null) {
            return ResultVo.failure("请检查ops和wms库存中间表的配置是否正确");
        }
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return ResultVo.failure("获取盘点批次号失败");
        }
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }
        pdBatchService.updatePdStateByPdbatchNo(batchNoWithIsActive.getData().getPdBatchNo(),PdStateEnum.sjcq.getCode(),loginAuthDto.getUserNo());

        // 0未执行 1需要执行 2wms执行完毕 3ops数据抽取中  4 ops抽取完毕
        if ("1".equals(opsAsWmsTaskNoticeDO.getJobStatus())) {
            return ResultVo.success("数据正在抽取中,大概所需5分钟,请耐心等候...");
        }
        if ("3".equals(opsAsWmsTaskNoticeDO.getJobStatus())) {
            return ResultVo.success("ops抽取数据中,请耐心等候");
        }
        if ("2".equals(batchNoWithIsActive.getData().getPdState())) {
            return ResultVo.success("ops抽取数据已经抽取完毕.");
        }
//        if(opsAsWmsTaskNoticeDO.getJobStatus().equals("0")) {
//
//        }
        LambdaUpdateWrapper<OpsAsWmsTaskNoticeDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(OpsAsWmsTaskNoticeDO::getJobName,"ops_wms_inventoryData")
                .set(OpsAsWmsTaskNoticeDO::getJobStatus,"1")
                .set(OpsAsWmsTaskNoticeDO::getPdBatchNo,batchNoWithIsActive.getData().getPdBatchNo())
                .set(OpsAsWmsTaskNoticeDO::getUpdateTime,new Date());
        opsAsWmsTaskNoticeMapper.update(null,updateWrapper);
        return ResultVo.success("数据正在抽取中,请耐心等候");
    }

    @Override
    public ResultVo<String> getOpsAsWmsTaskNoticeStatus() {
        LambdaQueryWrapper<OpsAsWmsTaskNoticeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsAsWmsTaskNoticeDO::getJobName,"ops_wms_inventoryData");
        OpsAsWmsTaskNoticeDO opsAsWmsTaskNoticeDO = opsAsWmsTaskNoticeMapper.selectOne(queryWrapper);
        if (opsAsWmsTaskNoticeDO == null) {
            return ResultVo.failure("请检查ops和wms库存中间表的配置是否正确");
        }
        return ResultVo.success(opsAsWmsTaskNoticeDO.getJobStatus());
    }

    @Override
    public ResultVo<String> getPdStatusFromRedis() {
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("无有效的盘点数据,请确认");
        }
        if (Integer.parseInt(PdStateEnum.pdBillscz.getCode()) < Integer.parseInt(batchNoWithIsActive.getData().getPdState())) {
            return ResultVo.failure("当前节点是"+PdStateEnum.getCodeNameByCode(batchNoWithIsActive.getData().getPdState())+",不可再次确认盘点形式");
        }
        Object o = redisManager.get(CommonConstants.OPS_PD_STATUS);
        if (o != null && o.toString().equals(PdRedisStatusEnum.alreadyHand.getCode())) {
            return ResultVo.failure("已生成盘点票,不可再次确认盘点形式");
        } else if (o != null && o.toString().equals(PdRedisStatusEnum.handing.getCode())) {
            return ResultVo.failure("生成盘点票中,不可再次确认盘点形式");
        }
        return ResultVo.success();
    }

    @Override
    public ResultVo<List<OpsAsWmsInventoryArrivedNotInDO>> remoteInvoiceWithArriveNotIn(String invoice) {
        if (StringUtils.isNotBlank(invoice)) {
            invoice = invoice+"%";
        }
        List<OpsAsWmsInventoryArrivedNotInDO> topTenInvoiceLike = opsAsWmsInventoryArrivedNotInMapper.getTopTenInvoiceLike(invoice);
        if (CollectionUtils.isEmpty(topTenInvoiceLike)) {
            return ResultVo.failure("暂无数据");
        }
        return  ResultVo.success(topTenInvoiceLike);
    }

    @Override
    public ResultVo<List<OpsAsPdBillDataDO>> remotePdBillNo(RemotePdBillNoVo remotePdBillNoVo) {

        if (Objects.isNull(remotePdBillNoVo) || StringUtils.isBlank(remotePdBillNoVo.getPdBillNo())) {
            return ResultVo.failure();
        }

        if (StringUtils.isNotBlank(remotePdBillNoVo.getPdBillNo())) {
            remotePdBillNoVo.setPdBillNo("%"+remotePdBillNoVo.getPdBillNo()+"%");
        }

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("未找到本次激活的盘点批次号.");
        }

        List<OpsAsPdBillDataDO> list = opsAsPdBillDataMapper.getTopTenPdBillNoLike(remotePdBillNoVo,batchNoWithIsActive.getData().getPdBatchNo());
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.failure();
        }
        return  ResultVo.success(list);
    }

    @Override
    public String getWarehouseConfigInfo(String person) {
        if (StringUtils.isBlank(person)) {
            return null;
        }
        LambdaQueryWrapper<OpsPdWarehouseConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsPdWarehouseConfigDO::getWarehousePersonNo,person);
        List<OpsPdWarehouseConfigDO> opsPdWarehouseConfigDOS = opsPdWarehouseConfigMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(opsPdWarehouseConfigDOS)) {
            return null;
        }
        return opsPdWarehouseConfigDOS.get(0).getWarehouseCode();
    }

    @Override
    public void exportShelvesData(HttpServletResponse response) {
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return;
        }
        long l = System.currentTimeMillis();
        log.info("实盘货架号数据开始导出..");
        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(6000);
        PageInfo<ExportShelvesVO> pageInfo;
        List<ExportShelvesVO> list = new ArrayList<>();
        while (true) {
            pageInfo =  PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> opsAsPdBillDataMapper.exportShelves(batchNoWithIsActive.getData().getPdBatchNo()));

            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                return;
            }
            list.addAll(pageInfo.getList());
            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        log.info("实盘货架号数据查询完毕 共计{}条.开始写入excel ==> {} s ",list.size(),(System.currentTimeMillis() - l) / 1000);
        // List<ExportShelvesVO> exportShelvesVOS = opsAsPdBillDataMapper.exportShelves(batchNoWithIsActive.getData().getPdBatchNo());

        try {
            String fileName = URLEncoder.encode("货架号", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            EasyExcel.write(outputStream)
                    .head(ExportShelvesVO.class)
                    .sheet("Sheet1")
                    .doWrite(list);
            InputStream inputStream = new  ByteArrayInputStream(outputStream.toByteArray());
            Map<String,InputStream> map = new HashMap<>();
            map.put("货架号.xlsx",inputStream);
//            InputStream inputStream = new ClassPathResource(CommonConstants.shelves_excel).getInputStream();
//
//            EasyExcel.write(response.getOutputStream(), ExportShelvesVO.class)
//                    .withTemplate(inputStream)
//                    .sheet("Sheet1").doWrite(exportShelvesVOS);
            ResultVo<DataTypeVO> pd = dictCommonService.getDataTypeCodesInfo("9004", "PD");
            if (!pd.isSuccess()) {
                return;
            }
            EmailUtil.send(mailSender,pd.getData().getExtNote1(),pd.getData().getExtNote2(),pd.getData().getExtNote3(),"实盘货架号数据","实盘货架号数据导出完毕,请查看附件内容.",map);
            log.info("实盘货架号数据导出完毕,数据已发送至邮件.. 共计耗时{}", (System.currentTimeMillis() - l) / 1000 );
        } catch (IOException e) {
            response.reset();
            log.info("导出货架号异常: 批次号:{} , 异常信息 {}",batchNoWithIsActive.getData().getPdBatchNo(),e.getMessage(),e);
            throw new RuntimeException("导出数据发生异常: "+e.getMessage());
        }
    }

    @Override
    public void exportBillCountData(HttpServletResponse response,String dataType) {


        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return;
        }

        List<String> pdDataTypeList = new ArrayList<>();
        if(StringUtils.isNotBlank(dataType)) {
            switch (dataType) {
                case "1" :
                    pdDataTypeList.add(PdDataTypeEnum.CG.getCode());
                    pdDataTypeList.add(PdDataTypeEnum.DB.getCode());
                    pdDataTypeList.add(PdDataTypeEnum.TH.getCode());
                    pdDataTypeList.add(PdDataTypeEnum.ZHDHWR.getCode());
                    break;
                case "2" :
                    pdDataTypeList.add(PdDataTypeEnum.GD.getCode());
                    break;
                case "3" :
                    pdDataTypeList.add(PdDataTypeEnum.JY.getCode());
                    break;
            }
        }

        LambdaQueryWrapper<OpsAsPdBillDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(OpsAsPdBillDataDO::getPdBatchNo,
                            OpsAsPdBillDataDO::getWarehouseCode,
                            OpsAsPdBillDataDO::getPdBillNo,
                            OpsAsPdBillDataDO::getShelvesNo,
                            OpsAsPdBillDataDO::getLocationNo,
                            OpsAsPdBillDataDO::getOrderNo,
                            OpsAsPdBillDataDO::getModelNo1,
                            OpsAsPdBillDataDO::getBillQty);
        if (CollectionUtils.isNotEmpty(pdDataTypeList)) {
            queryWrapper.in(OpsAsPdBillDataDO::getPdDataType,pdDataTypeList);
        }

        queryWrapper.eq(OpsAsPdBillDataDO::getDelFlag,false)
                .eq(OpsAsPdBillDataDO::getPdBatchNo,batchNoWithIsActive.getData().getPdBatchNo());
        List<OpsAsPdBillDataDO> list = opsAsPdBillDataMapper.selectList(queryWrapper);


        List<BillPrintShowExportVO> billPrintShowExportVOS = BeanCopyUtil.copyList(list, BillPrintShowExportVO.class);

        try {
            String fileName = URLEncoder.encode("compensateData", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");
            InputStream inputStream = new ClassPathResource(CommonConstants.other_data_export_excel).getInputStream();

            EasyExcel.write(response.getOutputStream(), BillPrintShowExportVO.class)
                    .withTemplate(inputStream)
                    .sheet("Sheet1").doWrite(billPrintShowExportVOS);
        } catch (IOException e) {
            response.reset();
            throw new RuntimeException("导出数据发生异常: "+e.getMessage());
        }

    }

    @Override
    public ResultVo<String> makeCurPBatchCancel() {
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            return ResultVo.failure("当前登录用户信息失效,请重新登录");
        }
        LambdaUpdateWrapper<OpsAsPdBatchDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(OpsAsPdBatchDO::getIsActive,"1")
                .set(OpsAsPdBatchDO::getIsActive,"2")
                .set(OpsAsPdBatchDO::getUpdateUser,loginAuthDto.getUserNo())
                .set(OpsAsPdBatchDO::getUpdateTime,new Date());
        opsAsPdBatchMapper.update(null,updateWrapper);
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<PageInfo<OpsPdAdjustDO>> findPdAdjustList(PdAdjustParamVO paramVO) {
        if (paramVO == null) {
            return ResultVo.failure("入参为空");
        }
        LambdaQueryWrapper<OpsPdAdjustDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(CollectionUtils.isNotEmpty(paramVO.getWarehouseCodes()),OpsPdAdjustDO::getWarehouseCode,paramVO.getWarehouseCodes())
                .eq(StringUtils.isNotBlank(paramVO.getModelNo()),OpsPdAdjustDO::getModelNo,paramVO.getModelNo())
                .eq(StringUtils.isNotBlank(paramVO.getHandStatus()),OpsPdAdjustDO::getHandStatus,paramVO.getHandStatus())
                .eq(StringUtils.isNotBlank(paramVO.getPdBatchNo()),OpsPdAdjustDO::getPdBatchNo,paramVO.getPdBatchNo());

        PageInfo<OpsPdAdjustDO> pageInfo = PageHelper.startPage(paramVO.getPage().getPageNumber(), paramVO.getPage().getPageSize()).doSelectPageInfo(
                () -> opsPdAdjustMapper.selectList(queryWrapper)
        );
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            for (OpsPdAdjustDO item : pageInfo.getList()) {
                item.setInventoryType(InventoryTypeEnum.getName(item.getInventoryType()));
                if(StringUtils.isNotBlank(item.getCreateUser())) {
                    item.setCreateUser(item.getCreateUser()+"["+opsCommonService.getEmpSaleNameByNo(item.getCreateUser())+"]");
                }
                if(StringUtils.isNotBlank(item.getConfirmUser())) {
                    item.setConfirmUser(item.getConfirmUser()+"["+opsCommonService.getEmpSaleNameByNo(item.getConfirmUser())+"]");
                }
                if (item.getCreateTime() != null) {
                    item.setCreateTimeStr(DateUtil.dateToDateTimeString(item.getCreateTime()));
                }
                if (item.getConfirmTime() != null) {
                    item.setConfirmTimeStr(DateUtil.dateToDateTimeString(item.getConfirmTime()));
                }
                if (item.getHandStatus() == 0) {
                    item.setHandStatusName("未调帐");
                } else if (item.getHandStatus() == 1) {
                    item.setHandStatusName("已调账");
                } else if (item.getHandStatus() == 2) {
                    item.setHandStatusName("调账失败");
                }
            }
        }
        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<String> createPdAdjust(String optUser) {

        // 获取当前盘点批次
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("当前不存在有效盘点批次");
        }
        int curcode = Integer.parseInt(batchNoWithIsActive.getData().getPdState());
        int code = Integer.parseInt(PdStateEnum.sctzd.getCode());
        if(curcode > code) {
            return ResultVo.failure("当前盘点处于"+PdStateEnum.getCodeNameByCode(String.valueOf(curcode))+"不可以生成调账单");
        }
        String redisKey = "ops:pd:sctzd:"+batchNoWithIsActive.getData().getPdBatchNo()+":";
        Object o = redisManager.get(redisKey);
        if (o != null && "1".equals(o.toString())) {
            return ResultVo.failure("调账单正在生成中,请留意邮件查看是否生成完毕.");
        }

        String pdBatchNo = batchNoWithIsActive.getData().getPdBatchNo();

        // 每次生成调账单之前 先清空当前盘点批次的日期
        opsPdAdjustMapper.delete(new LambdaQueryWrapper<OpsPdAdjustDO>().eq(OpsPdAdjustDO::getPdBatchNo,pdBatchNo));

        /**
         * 对每一个仓中的每一个OPS盘点数和WMS盘点数存在差异的型号进行差异调账，
         * 当OPS盘点数>WMS盘点数时进行减账处理，
         * 当OPS盘点数<WMS盘点数时进行加帐处理，
         * 相等时不处理
         */
        // 查询ops_as_pd_three_report_ware ops_sum_qty <> wms_sum_qty
        List<OpsAsPdThreeReportWareDO> opsAsPdThreeReportWareDOList = opsAsPdThreeReportWareMapper.listSumOpsQtyNeSumWmsQty(pdBatchNo);
        if (CollectionUtils.isEmpty(opsAsPdThreeReportWareDOList)) {
            return ResultVo.failure("暂无报表数据");
        }

        Map<String, List<OpsAsPdThreeReportWareDO>> mapData = opsAsPdThreeReportWareDOList.stream()
                .collect(Collectors.groupingBy(OpsAsPdThreeReportWareDO::getWarehouseCode));


        redisManager.set(redisKey,"1");
        long l = System.currentTimeMillis();
        log.info("调账单开始生成...");

        List<ErrorMsgVO> list = new ArrayList<>();
        ErrorMsgVO vo = new ErrorMsgVO();
        vo.setErrorMsg1("仓库");
        vo.setErrorMsg2("型号");
        vo.setErrorMsg3("ops合计数");
        vo.setErrorMsg4("wms合计数");
        vo.setErrorMsg5("异常原因");
        list.add(vo);


        for (String key : mapData.keySet()) {
            List<OpsAsPdThreeReportWareDO> threeReportWareDOS = mapData.get(key);
            String adjustNo = "";
            int count = 0;
            for(OpsAsPdThreeReportWareDO item : threeReportWareDOS) {
                count++;
                adjustNo = "PD"+DateUtil.getYearMonthCode(new Date()) +key + String.format("%06d", count);
                try {
                    if(item.getOpsSumQty()>item.getWmsSumQty()) {
                        // 减账处理
                        subtractionAccount(item,optUser,adjustNo);
                    } else if (item.getOpsSumQty()<item.getWmsSumQty()) {
                        // 加账处理
                        addAccount(item,optUser,adjustNo);
                    }
                } catch (Exception e) {
                    ErrorMsgVO errorMsgVO = new ErrorMsgVO();
                    errorMsgVO.setErrorMsg1(item.getWarehouseCode());
                    errorMsgVO.setErrorMsg2(item.getModelNo());
                    errorMsgVO.setErrorMsg3(String.valueOf(item.getOpsSumQty()));
                    errorMsgVO.setErrorMsg4(String.valueOf(item.getWmsSumQty()));
                    errorMsgVO.setErrorMsg5(e.getMessage());
                    list.add(errorMsgVO);
                }
            }
        }

        log.info("调账单生成完毕,共耗时{}", (System.currentTimeMillis() - l) / 1000 );
        redisManager.set(redisKey,"2");

        if(list.size() < 2) {
            pdBatchService.updatePdStateByPdbatchNo(batchNoWithIsActive.getData().getPdBatchNo(),PdStateEnum.sctzd.getCode(),optUser);
        }

        return pushMsg(list);
    }

    public ResultVo<String> pushMsg(List<ErrorMsgVO> list) {
        String excelPath = "templates/excel/生成调账单异常数据清单.xlsx";
        ResultVo<DataTypeVO> pd = dictCommonService.getDataTypeCodesInfo("9004", "PD");
        if (!pd.isSuccess()) {
            return ResultVo.failure("获取发送生成调账单邮件配置失败.");
        }
        if (list.size() > 1) {
            try {
                // 异常数据发送邮件提醒告知结果
                // 创建一个新的Excel工作簿
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Sheet1");

                // 将txt文件的内容写入Excel工作表
                for (int i = 0; i < list.size(); i++) {
                    Row row = sheet.createRow(i);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(list.get(i).getErrorMsg1());
                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(list.get(i).getErrorMsg2());
                    Cell cell2 = row.createCell(2);
                    cell2.setCellValue(list.get(i).getErrorMsg3());
                    Cell cell3 = row.createCell(3);
                    cell3.setCellValue(list.get(i).getErrorMsg4());
                    Cell cell4 = row.createCell(4);
                    cell4.setCellValue(list.get(i).getErrorMsg5());
                }

                // 将工作簿写入文件
                try (FileOutputStream fileOut = new FileOutputStream(excelPath)) {
                    workbook.write(fileOut);
                }
                // 关闭工作簿
                workbook.close();

                InputStream inputStream = new FileInputStream(new File(excelPath));

                Map<String,InputStream> map = new HashMap<>();
                map.put("货架号.xlsx",inputStream);

                EmailUtil.send(mailSender,pd.getData().getExtNote1(),pd.getData().getExtNote2(),pd.getData().getExtNote3(),"生成调账单结果",
                                                                            "调账单生成完毕,附件信息为生成调账单异常的数据,请查看附件内容.",map);
                return ResultVo.success("处理成功");
            } catch (Exception e) {
                log.error("生成调账单异常文件异常,{}",e.getMessage(),e);
                throw new RuntimeException(e.getMessage());
            }
        }
        EmailUtil.send(mailSender,pd.getData().getExtNote1(),pd.getData().getExtNote2(),"生成调账单结果","生成调账单完毕.");
        return ResultVo.success("处理成功");
    }

    @Override
    public void exportPdAdjustData(PdAdjustParamVO paramVO) {

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null) {
            return;
        }

        long l = System.currentTimeMillis();
        log.info("调账单开始导出..");

        LambdaQueryWrapper<OpsPdAdjustDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OpsPdAdjustDO::getPdBatchNo,batchNoWithIsActive.getData().getPdBatchNo())
                .in(CollectionUtils.isNotEmpty(paramVO.getWarehouseCodes()),OpsPdAdjustDO::getWarehouseCode,paramVO.getWarehouseCodes())
                .eq(StringUtils.isNotBlank(paramVO.getHandStatus()),OpsPdAdjustDO::getHandStatus,paramVO.getHandStatus())
                .eq(StringUtils.isNotBlank(paramVO.getModelNo()),OpsPdAdjustDO::getModelNo,paramVO.getModelNo());

        paramVO.getPage().setPageSize(6000);
        paramVO.getPage().setPageNumber(1);
        List<ExportPdAdjustVO> list = new ArrayList<>();
        while (true) {
            PageInfo<OpsPdAdjustDO> pageInfo = PageHelper.startPage(paramVO.getPage().getPageNumber(), paramVO.getPage().getPageSize()).doSelectPageInfo(
                    () -> opsPdAdjustMapper.selectList(queryWrapper)
            );
            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                return;
            }
            list.addAll(BeanCopyUtil.copyList(pageInfo.getList(),ExportPdAdjustVO.class));
            if (pageInfo.isIsLastPage()) {
                break;
            }
            paramVO.getPage().setPageNumber(paramVO.getPage().getPageNumber() + 1);
        }
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        log.info("调账单查询完毕 共计{}条.开始写入excel ==> {} s ",list.size(),(System.currentTimeMillis() - l) / 1000);

        for (ExportPdAdjustVO item : list) {
            item.setInventoryType(InventoryTypeEnum.getName(item.getInventoryType()));
            if(StringUtils.isNotBlank(item.getCreateUser())) {
                item.setCreateUser(item.getCreateUser()+"["+opsCommonService.getEmpSaleNameByNo(item.getCreateUser())+"]");
            }
            if(StringUtils.isNotBlank(item.getConfirmUser())) {
                item.setConfirmUser(item.getConfirmUser()+"["+opsCommonService.getEmpSaleNameByNo(item.getConfirmUser())+"]");
            }
            if (item.getHandStatus() == 0) {
                item.setHandStatusName("未调帐");
            } else if (item.getHandStatus() == 1) {
                item.setHandStatusName("已调账");
            } else if (item.getHandStatus() == 2) {
                item.setHandStatusName("调账失败");
            }
        }

        try {
            String fileName = URLEncoder.encode("调账单", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            EasyExcel.write(outputStream)
                    .head(ExportPdAdjustVO.class)
                    .sheet("Sheet1")
                    .doWrite(list);
            InputStream inputStream = new  ByteArrayInputStream(outputStream.toByteArray());
            Map<String,InputStream> map = new HashMap<>();
            map.put("调账单.xlsx",inputStream);
            ResultVo<DataTypeVO> pd = dictCommonService.getDataTypeCodesInfo("9004", "PD");
            if (!pd.isSuccess()) {
                return;
            }
            EmailUtil.send(mailSender,pd.getData().getExtNote1(),pd.getData().getExtNote2(),pd.getData().getExtNote3(),"调账单","调账单导出完毕,请查看附件内容.",map);
            log.info("调账单导出完毕,数据已发送至邮件.. 共计耗时{}", (System.currentTimeMillis() - l) / 1000 );
        } catch (IOException e) {
            response.reset();
            log.info("导出调账单异常: 批次号:{} , 异常信息 {}",batchNoWithIsActive.getData().getPdBatchNo(),e.getMessage(),e);
            throw new RuntimeException("导出数据发生异常: "+e.getMessage());
        }
    }

    @Override
    public ResultVo<String> confirmPdAccount(CommonHnadVO commonHnadVO) {
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess()) {
            return ResultVo.failure("当前没有激活的盘点批次");
        }

        if (batchNoWithIsActive.getData().getPdState().equals(PdStateEnum.qrtzd.getCode())) {
            return ResultVo.success("已确认调账单,不可重复确认.");
        }

        LambdaUpdateWrapper<OpsPdAdjustDO> updateWrapper = new LambdaUpdateWrapper<>();
        List<String> errData = new ArrayList<>();

        while (true) {
            List<OpsPdAdjustDO> needAdjustHandData = opsPdAdjustMapper.getNeedAdjustHandData(batchNoWithIsActive.getData().getPdBatchNo(), commonHnadVO.getIds());
            if (CollectionUtils.isEmpty(needAdjustHandData)) {
                break;
            }
            List<AdjustItemDTO> handlist = new ArrayList<>();
            // +库存集合
            List<OpsPdAdjustDO> addInvList = needAdjustHandData.stream().filter(i -> i.getAdjustQty() > 0).collect(Collectors.toList());
            // -库存集合
            List<OpsPdAdjustDO> subInvList = needAdjustHandData.stream().filter(i -> i.getAdjustQty() < 0).collect(Collectors.toList());

            AdjustParam adjustParam = new AdjustParam();
            UserDto userDto = new UserDto();
            userDto.setUserName(commonHnadVO.getOptUser());
            adjustParam.setUserDto(userDto);

            if (CollectionUtils.isNotEmpty(addInvList)) {
                List<AdjustItemDTO> dtoList = new ArrayList<>();
                /**
                 * 调帐 加库存
                 */
                for(OpsPdAdjustDO item : addInvList) {
                    AdjustItemDTO adjustItemDTO = conventAdjustItemDTO(item, "+");
                    dtoList.add(adjustItemDTO);
                }
                adjustParam.setAdjustItems(dtoList);

                log.info("调账单加账处理开始:{},{}",JSONUtil.toJsonStr(adjustParam),dtoList.size());
                CommonResult<AdjustParam> adjustParamCommonResult = opsWmDispatchForOrderFeignApi.adjustInventory(adjustParam);
                log.info("调账单加账处理结果:{}",JSONUtil.toJsonStr(adjustParamCommonResult));
                handlist.addAll(adjustParamCommonResult.getData().getAdjustItems());
            }
            if (CollectionUtils.isNotEmpty(subInvList)) {
                /**
                 * 调帐 减库存
                 */
                List<AdjustItemDTO> dtoList = new ArrayList<>();
                for(OpsPdAdjustDO item : subInvList) {
                    AdjustItemDTO adjustItemDTO = conventAdjustItemDTO(item, "-");
                    dtoList.add(adjustItemDTO);
                }
                adjustParam.setAdjustItems(dtoList);
                log.info("调账单减账处理开始:{}",dtoList.size());
                CommonResult<AdjustParam> adjustParamCommonResult = opsWmDispatchForOrderFeignApi.adjustInventory(adjustParam);
                log.info("调账单减账处理结果:{}",JSONUtil.toJsonStr(adjustParamCommonResult));
                handlist.addAll(adjustParamCommonResult.getData().getAdjustItems());
            }

            for(AdjustItemDTO a : handlist) {
                updateWrapper.clear();
                updateWrapper
                        .eq(OpsPdAdjustDO::getAdjustNo,a.getOrderId())
                        .eq(OpsPdAdjustDO::getPdBatchNo,batchNoWithIsActive.getData().getPdBatchNo())
                        .eq(OpsPdAdjustDO::getAdjustItemNo,a.getOrderItem())
                        .eq(OpsPdAdjustDO::getModelNo,a.getModelNo())
                        .set(OpsPdAdjustDO::getConfirmTime, new Date())
                        .set(OpsPdAdjustDO::getConfirmUser,commonHnadVO.getOptUser());
                if (a.getResult() == 1) {
                    updateWrapper.set(OpsPdAdjustDO::getHandStatus,1);
                } else {
                    errData.add(a.getOrderId());
                    updateWrapper.set(OpsPdAdjustDO::getHandStatus,2);
                    updateWrapper.set(OpsPdAdjustDO::getRemark,a.getMessage());
                }
                opsPdAdjustMapper.update(null,updateWrapper);
            }
        }

        if (CollectionUtils.isEmpty(commonHnadVO.getIds())) {
            pdBatchService.updatePdStateByPdbatchNo(batchNoWithIsActive.getData().getPdBatchNo(),PdStateEnum.qrtzd.getCode(),commonHnadVO.getOptUser());
        }
        if (CollectionUtils.isNotEmpty(errData)) {
            return ResultVo.failure("处理失败,请查看失败调帐单号:" + errData.toString());
        }
        return ResultVo.success("处理成功");
    }

    public AdjustItemDTO conventAdjustItemDTO(OpsPdAdjustDO item,String optType) {

        AdjustItemDTO adjustItemDTO = new AdjustItemDTO();
        if ("+".equals(optType)) {
            adjustItemDTO.setAdjustType(AdjustType.Addition);
        } else if ("-".equals(optType)) {
            adjustItemDTO.setAdjustAvailableInventory(false);
            adjustItemDTO.setAdjustType(AdjustType.Subtraction);
        }
        adjustItemDTO.setOrderId(item.getAdjustNo());
        adjustItemDTO.setOrderItem(item.getAdjustItemNo());
        adjustItemDTO.setModelNo(item.getModelNo());
        adjustItemDTO.setWarehouseCode(item.getWarehouseCode());
        adjustItemDTO.setQty(Math.abs(item.getAdjustQty()));
        if (StringUtils.isNotBlank(item.getInventoryType())) {
            adjustItemDTO.setPropertyType(com.sales.ops.enums.InventoryTypeEnum.parse(item.getInventoryType()));
        }
        adjustItemDTO.setCustomerNo(item.getCustomerNo());
        adjustItemDTO.setGroupCustomerNo(item.getGroupCustomerNo());
        adjustItemDTO.setPpl(item.getPpl());
        adjustItemDTO.setProjectCode(item.getProjectNo());
        return adjustItemDTO;

    }

    @Override
    public ResultVo<String> createAdjustform(OpsPdAdjustDO opsPdAdjustDO) {
        if(opsPdAdjustDO == null) {
            return ResultVo.failure("入参为空");
        }
        opsPdAdjustDO.setAdjustItemNo(1);
        opsPdAdjustDO.setCreateTime(new Date());
        opsPdAdjustDO.setDataSource(1);
        opsPdAdjustDO.setHandStatus(1);
        opsPdAdjustDO.setConfirmUser(opsPdAdjustDO.getCreateUser());
        opsPdAdjustDO.setConfirmTime(new Date());

        AdjustParam adjustParam = new AdjustParam();
        UserDto userDto = new UserDto();
        userDto.setUserName(opsPdAdjustDO.getCreateUser());
        adjustParam.setUserDto(userDto);

        AdjustItemDTO adjustItemDTO;
        if(opsPdAdjustDO.getAdjustQty() <= 0) {
            adjustItemDTO  = conventAdjustItemDTO(opsPdAdjustDO, "-");
        } else {
            adjustItemDTO  = conventAdjustItemDTO(opsPdAdjustDO, "+");
        }

        List<AdjustItemDTO> list = new ArrayList<>();
        list.add(adjustItemDTO);
        adjustParam.setAdjustItems(list);

        CommonResult<AdjustParam> adjust1 = null;
        try {
            adjust1 = opsWmDispatchForOrderFeignApi.adjustInventory(adjustParam);
            if (adjust1 != null && adjust1.getData().getAdjustItems().get(0).getResult() == 1) {
                opsPdAdjustMapper.insert(opsPdAdjustDO);
                return ResultVo.success(opsPdAdjustDO.getAdjustNo()+"调账成功");
            }
            log.error("调账失败: data:{} {}",JSONUtil.toJsonStr(adjustItemDTO), JSONUtil.toJsonStr(adjust1));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("单条调账失败 data:{}, {}",JSONUtil.toJsonStr(adjustItemDTO),e.getMessage(),e);
            return ResultVo.failure("调账失败");
        }
        return ResultVo.failure(opsPdAdjustDO.getAdjustNo()+"调账失败: {}"+adjust1.getData().getAdjustItems().get(0).getMessage());
    }

    @Override
    public ResultVo<OpsPdAdjustDO> createAdjustNo(String warehouseCode) {

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess()) {
            return ResultVo.failure("获取当前有效盘点批次异常");
        }
        String invoiceNo = batchNoWithIsActive.getData().getPdBatchNo()+warehouseCode;
        List<OpsPdAdjustDO> adjustDOList = opsPdAdjustMapper.getOpsAdJustByWarehouseCode(invoiceNo);
        OpsPdAdjustDO opsPdAdjustDO = new OpsPdAdjustDO();
        opsPdAdjustDO.setAdjustInvoiceNo(invoiceNo);
        opsPdAdjustDO.setPdBatchNo(batchNoWithIsActive.getData().getPdBatchNo());
        if (CollectionUtils.isEmpty(adjustDOList)) {
            opsPdAdjustDO.setAdjustNo(invoiceNo+"000001");
        } else {
            OpsPdAdjustDO adjustDO = adjustDOList.get(0);
            // 截取后六位
            String str = adjustDO.getAdjustNo().substring(adjustDO.getAdjustNo().length() - 6);
            int num = Integer.parseInt(str)+1;
            String numStr = String.format("%06d", num);
            opsPdAdjustDO.setAdjustNo(invoiceNo+numStr);
        }
        return ResultVo.success(opsPdAdjustDO);
    }

    @Override
    public void quantityCanUseBatch() {
        String path = "templates/excel/批量调账模板.xlsx";
        ExcelUtil excel = new ExcelUtil(path);
        excel.writeToResponse(response, "批量调账模板.xlsx");
    }

    @Override
    public ResultVo<String> batchAdjustData(MultipartFile file, String loginUser) {

        if (file == null) {
            return ResultVo.failure("请上传文件");
        }

        if (StringUtils.isBlank(loginUser)) {
            return ResultVo.failure("操作人为空.请退出重新登录");
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(filename) && !filename.matches("^.+\\.(?i)(xlsx)$")
                && !filename.matches("^.+\\.(?i)(xls)$")) {
            return ResultVo.failure("文件格式错误,请按照模板文件格式进行导入");
        }

        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess()) {
            return ResultVo.failure("未获取到有效的盘点批次");
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
        OpsPdAdjustDO opsPdAdjustDO;
        List<OpsPdAdjustDO> pdAdjustDOS = new ArrayList<>();
        List<String> allType = com.sales.ops.enums.InventoryTypeEnum.getAllType();
        while (true) {
            row++;
            rows = sheet.getRow(row);
            opsPdAdjustDO = new OpsPdAdjustDO();
            if (rows == null) {
                break;
            }
            String warehouseCode = excel.getCellString(rows.getCell(0));
            if (StringUtils.isBlank(warehouseCode)) {
                throw new BusinessException("第"+row+"行的仓库代码为空.请仔细检查数据");
            }
            String modelNo = excel.getCellString(rows.getCell(1));
            if (StringUtils.isBlank(modelNo)) {
                throw new BusinessException("第"+row+"行的型号为空.请仔细检查数据");
            }
            String customerNo = excel.getCellString(rows.getCell(2));
            String inventoryType = excel.getCellString(rows.getCell(3));
            if (StringUtils.isBlank(inventoryType)) {
                throw new BusinessException("第"+row+"行的库存类型为空.请仔细检查数据");
            }
            if (!allType.contains(inventoryType)) {
                throw new BusinessException("第"+row+"行的库存类型编码非有效.请仔细检查数据");
            }
            String adjustQty = excel.getCellString(rows.getCell(4));
            if (StringUtils.isBlank(adjustQty)) {
                throw new BusinessException("第"+row+"行的调账数量为空.请仔细检查数据");
            }
            if(!isInteger(adjustQty)) {
                throw new BusinessException("第"+row+"行的调账数量必须为整数.请仔细检查数据");
            }
            String ppl = excel.getCellString(rows.getCell(5));
            String projectNo = excel.getCellString(rows.getCell(6));
            String salesInfoNo = excel.getCellString(rows.getCell(7));
            String groupCustomerNo = excel.getCellString(rows.getCell(8));
            String batchNo = batchNoWithIsActive.getData().getPdBatchNo();
            opsPdAdjustDO.setPdBatchNo(batchNo);
            opsPdAdjustDO.setWarehouseCode(warehouseCode);
            opsPdAdjustDO.setAdjustInvoiceNo(batchNo+warehouseCode);
            opsPdAdjustDO.setModelNo(modelNo);
            // 调账单号
            // opsPdAdjustDO.setAdjustNo(batchNo+warehouseCode+String.format("%06d", count+1));
            // 调账单项号
            opsPdAdjustDO.setAdjustQty(Integer.parseInt(adjustQty));
            opsPdAdjustDO.setInventoryType(inventoryType);
            opsPdAdjustDO.setCustomerNo(customerNo);
            opsPdAdjustDO.setPpl(ppl);
            opsPdAdjustDO.setProjectNo(projectNo);
            opsPdAdjustDO.setSalesInfoNo(salesInfoNo);
            opsPdAdjustDO.setGroupCustomerNo(groupCustomerNo);
            opsPdAdjustDO.setCreateUser(loginUser);
            opsPdAdjustDO.setCreateTime(new Date());
            opsPdAdjustDO.setConfirmTime(new Date());
            opsPdAdjustDO.setConfirmUser(loginUser);
            opsPdAdjustDO.setHandStatus(1);
            opsPdAdjustDO.setDataSource(1);
            pdAdjustDOS.add(opsPdAdjustDO);
        }
        Map<String, List<OpsPdAdjustDO>> mapData = pdAdjustDOS.stream()
                .collect(Collectors.groupingBy(OpsPdAdjustDO::getWarehouseCode));

        for (String key : mapData.keySet()) {
            List<OpsPdAdjustDO> dataList = mapData.get(key);
            ResultVo<OpsPdAdjustDO> adjustDOResultVo = createAdjustNo(key);
            String adjustNo1 = adjustDOResultVo.getData().getAdjustNo();
            int num = Integer.parseInt(adjustNo1.substring(adjustNo1.length()-6));
            for(OpsPdAdjustDO item : dataList) {
                item.setAdjustNo(item.getAdjustInvoiceNo()+String.format("%06d", num ));
                item.setAdjustItemNo(1);
                num++;
            }
        }

        // mapData 转 list
        List<OpsPdAdjustDO> adjustDOList = mapData.values().stream().flatMap(Collection::stream).collect(Collectors.toList());

        // +库存集合
        List<OpsPdAdjustDO> addInvList = adjustDOList.stream().filter(i -> i.getAdjustQty() > 0).collect(Collectors.toList());
        // -库存集合
        List<OpsPdAdjustDO> subInvList = adjustDOList.stream().filter(i -> i.getAdjustQty() < 0).collect(Collectors.toList());

        AdjustParam adjustParam = new AdjustParam();
        UserDto userDto = new UserDto();
        userDto.setUserName(loginUser);
        adjustParam.setUserDto(userDto);
        List<AdjustItemDTO> dtoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(addInvList)) {
            /**
             * 调帐 加库存
             */
            for(OpsPdAdjustDO item : addInvList) {
                AdjustItemDTO adjustItemDTO = conventAdjustItemDTO(item, "+");
                dtoList.add(adjustItemDTO);
            }
        }
        if (CollectionUtils.isNotEmpty(subInvList)) {
            /**
             * 调帐 减库存
             */
            for(OpsPdAdjustDO item : subInvList) {
                AdjustItemDTO adjustItemDTO = conventAdjustItemDTO(item, "-");
                dtoList.add(adjustItemDTO);
            }
        }
        adjustParam.setAdjustItems(dtoList);
        CommonResult<AdjustParam> adjustParamCommonResult = opsWmDispatchForOrderFeignApi.adjustInventory(adjustParam);
        log.info("调账单加账处理结果:{}",JSONUtil.toJsonStr(adjustParamCommonResult));
        List<String> errorData = new ArrayList<>();
        for (OpsPdAdjustDO item : addInvList) {
            if(adjustParamCommonResult != null && CollectionUtil.isNotEmpty(adjustParamCommonResult.getData().getAdjustItems())) {
                for (AdjustItemDTO dto : adjustParamCommonResult.getData().getAdjustItems()) {
                    if (item.getAdjustNo().equals(dto.getOrderId()) && item.getAdjustItemNo() == dto.getOrderItem()) {
                        if (dto.getResult() == 1) {
                            opsPdAdjustMapper.insert(item);
                        } else {
                            errorData.add(item.getAdjustNo());
                            log.error("调账单加账处理失败结果:{}",JSONUtil.toJsonStr(dto));
                        }
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(errorData)) {
            return ResultVo.failure("调账处理失败,失败单号为:"+errorData.toString());
        }
        return ResultVo.success("处理成功");
    }

    @Override
    public ResultVo<String> surePdDataDiff(String optUser) {
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return ResultVo.failure("获取盘点批次号失败");
        }
        pdBatchService.updatePdStateByPdbatchNo(batchNoWithIsActive.getData().getPdBatchNo(), PdStateEnum.pdcyqrz.getCode(),optUser);
        return ResultVo.success("操作成功");
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public InventoryForAdjustDto convertToInventoryForAdjustInputDto(OpsPdAdjustDO item) {
        InventoryForAdjustDto dto = new InventoryForAdjustDto();
        dto.setOrderId(item.getAdjustNo());
        dto.setOrderItem(item.getAdjustItemNo());
        dto.setModelno(item.getModelNo());
        dto.setQty(Math.abs(item.getAdjustQty()));
        dto.setInventoryId(item.getInventoryId());
        dto.setWarehouseCode(item.getWarehouseCode());
        dto.setCustomerNo(item.getCustomerNo());
        dto.setPpl(item.getPpl());
        dto.setProjectCode(item.getProjectNo());
        dto.setGroupCustomerNo(item.getGroupCustomerNo());
        dto.setSalesInfoNo(item.getSalesInfoNo());
        return dto;
    }

    public void addAccount(OpsAsPdThreeReportWareDO item,String optUser,String adjustNo) {
        // 获取该仓库的型号差异数量
        int diffQty = item.getWmsSumQty() - item.getOpsSumQty();
        int adjustItemNo = 1;
        // 获取当前inventory表中该仓库该型号的prepare_quantity - quantity > 0 且不在非调账清单中的数据 升序排序
        List<InventoryVO> inventoryList = opsAsPdCommonMapper.getInventoryList(item.getModelNo(), item.getWarehouseCode());
        if (CollectionUtils.isNotEmpty(inventoryList)) {
            for (int i= 1; i<=inventoryList.size();i++)  {
                InventoryVO inventoryVO = inventoryList.get(i-1);
                if (diffQty <= 0) {
                    break;
                }
                // 调整数量
                int adjustQty = inventoryVO.getPrepareQuantity() - inventoryVO.getQuantity();
                int absQty = Math.abs(adjustQty);
                int needQty = diffQty - absQty;
                if(diffQty - absQty <= 0) {
                    needQty = diffQty;
                } else {
                    needQty = absQty;
                }
                // 创建调账单
                OpsPdAdjustDO adjust = createAdjust(item, inventoryVO, adjustNo, adjustItemNo, needQty, optUser);
                opsPdAdjustMapper.insert(adjust);
                diffQty -= adjustQty;
                adjustItemNo++;
            }
        }
        // 负账处理完毕,仍有wms_sum_qty > ops_sum_qty, 剩余数量全部冲入该仓库的通用在库
        if (diffQty > 0) {
            // 根据型号和仓库获取该型号在通用大库中的数量
            List<InventoryVO> inventoryListByModelNo = opsAsPdCommonMapper.getInventoryListByModelNoAndWarehouseWithTyAddQty(item.getModelNo(),item.getWarehouseCode());
            if (CollectionUtils.isEmpty(inventoryListByModelNo)) {
                return;
            }
            // 创建调账单
            OpsPdAdjustDO adjust = createAdjust(item, inventoryListByModelNo.get(0),adjustNo, adjustItemNo,
                                                    diffQty, optUser);
            opsPdAdjustMapper.insert(adjust);
        }
    }

    public void subtractionAccount(OpsAsPdThreeReportWareDO item,String optUser,String adjustNo) {
        // 获取该仓库的型号差异数量
        int diffQty = item.getOpsSumQty() - item.getWmsSumQty();

        List<OpsPdAdjustDO> adjustDOList = new ArrayList<>();
        int adjustItemNo = 1;
        /**
         * 先调客户代码为95002的有效GK-TY库存
         */
        List<InventoryVO> inventoryListByModelNo = opsAsPdCommonMapper.getInventoryListByModelNo(item.getModelNo(),item.getWarehouseCode());
        if (CollectionUtils.isNotEmpty(inventoryListByModelNo)) {
            InventoryVO  inventoryVO = inventoryListByModelNo.get(0);
            if(diffQty - inventoryVO.getAvaQty() <= 0) {
                // 生成调账单
                OpsPdAdjustDO adjust = createAdjust(item, inventoryVO,adjustNo, adjustItemNo, diffQty*-1, optUser);
                opsPdAdjustMapper.insert(adjust);
                return;
            }
            OpsPdAdjustDO adjust = createAdjust(item, inventoryVO, adjustNo, adjustItemNo, inventoryVO.getAvaQty()*-1, optUser);
            adjustItemNo++;
            adjustDOList.add(adjust);
            diffQty = diffQty - inventoryVO.getAvaQty();
        }

        List<InventoryVO> inventoryVOS = opsAsPdCommonMapper.getInventoryListByModelNoAndWarehouseWithTy(item.getModelNo(), item.getWarehouseCode());
        /**
         *  有剩余调大库通用库存
         */
        if (CollectionUtil.isNotEmpty(inventoryVOS)) {
            InventoryVO inventoryVO = inventoryVOS.get(0);
            int canAdjustQty = diffQty;
            diffQty = diffQty - inventoryVO.getAvaQty();
            if (diffQty <=0) {
                // 生成调账单
                OpsPdAdjustDO adjust = createAdjust(item, inventoryVO, adjustNo, adjustItemNo, canAdjustQty*-1, optUser);
                opsPdAdjustMapper.insert(adjust);
                return;
            }
            OpsPdAdjustDO adjust = createAdjust(item, inventoryVO,adjustNo, adjustItemNo, inventoryVO.getAvaQty()*-1, optUser);
            adjustDOList.add(adjust);
            adjustItemNo++;
        }
        /**
         * 调其他
         * 排除客户代码95002的 TY库存且不再非调库清单里的数据
         */
        List<InventoryVO> inventoryWithXXZK = opsAsPdCommonMapper.getInventoryWithXXZK(item.getWarehouseCode(), item.getModelNo());
        if (CollectionUtil.isNotEmpty(inventoryWithXXZK)) {
            for (int i= 1; i<=inventoryWithXXZK.size();i++)  {
                InventoryVO inventoryVO = inventoryWithXXZK.get(i-1);
                // 调整数量
                int adjustQty = 0;
                if(diffQty - inventoryVO.getAvaQty() <= 0) {
                    adjustQty = diffQty;
                } else {
                    adjustQty = inventoryVO.getAvaQty();
                }
                OpsPdAdjustDO adjust = createAdjust(item, inventoryVO,adjustNo, adjustItemNo, adjustQty*-1, optUser);
                adjustDOList.add(adjust);
                adjustItemNo++;
                diffQty = diffQty - inventoryVO.getAvaQty();
                if (diffQty <= 0) {
                    adjustDOList.forEach(opsPdAdjustDO -> opsPdAdjustMapper.insert(opsPdAdjustDO));
                    return;
                }
            }

        }
        /**
         * 还有剩余
         * 继续调大库通用库存 实在库
         */
        List<InventoryVO> inventoryVOList = opsAsPdCommonMapper.getInventoryListByModelNoAndWarehouseWithTyQuantity(item.getModelNo(),item.getWarehouseCode());
        if (CollectionUtils.isNotEmpty(inventoryVOList)) {
            InventoryVO inventoryVO = inventoryVOList.get(0);

            if (diffQty - inventoryVO.getQuantity() < 0) {
                OpsPdAdjustDO adjust = createAdjust(item, inventoryVO,adjustNo, adjustItemNo, diffQty*-1, optUser);
                boolean exit = false;
                for (OpsPdAdjustDO adjustDO : adjustDOList) {
                    if (Objects.equals(adjustDO.getInventoryId(), adjust.getInventoryId())) {
                        adjustDO.setAdjustQty(adjust.getAdjustQty()+adjustDO.getAdjustQty());
                        exit = true;
                    }
                }
                if (!exit) {
                    adjustDOList.add(adjust);
                }
                adjustDOList.forEach(opsPdAdjustDO -> opsPdAdjustMapper.insert(opsPdAdjustDO));
                return;

            } else {
                diffQty = diffQty - inventoryVO.getQuantity();
                OpsPdAdjustDO adjust = createAdjust(item, inventoryVO,adjustNo, adjustItemNo, inventoryVO.getQuantity()*-1, optUser);
                boolean exit = false;
                for (OpsPdAdjustDO adjustDO : adjustDOList) {
                    if (Objects.equals(adjustDO.getInventoryId(), adjust.getInventoryId())) {
                        adjustDO.setAdjustQty(adjust.getAdjustQty()+adjustDO.getAdjustQty());
                        exit = true;
                    }
                }
                if (!exit) {
                    adjustDOList.add(adjust);
                }
            }

        }
        /**
         * 有剩余调其他实在库 (实在库倒叙)
         */
        List<InventoryVO> inventoryWithXXZK2 = opsAsPdCommonMapper.getInventoryWithXXZK2(item.getWarehouseCode(), item.getModelNo());
        if(CollectionUtils.isNotEmpty(inventoryWithXXZK2)) {
            for (int i= 1; i<=inventoryWithXXZK2.size();i++)  {
                InventoryVO invDo = inventoryWithXXZK2.get(i-1);
                if (diffQty - invDo.getQuantity() < 0) {
                    OpsPdAdjustDO adjust = createAdjust(item, invDo,adjustNo, adjustItemNo, diffQty*-1, optUser);
                    boolean exit = false;
                    for (OpsPdAdjustDO adjustDO : adjustDOList) {
                        if (Objects.equals(adjustDO.getInventoryId(), adjust.getInventoryId())) {
                            adjustDO.setAdjustQty(adjust.getAdjustQty()+adjustDO.getAdjustQty());
                            exit = true;
                        }
                    }
                    if (!exit) {
                        adjustDOList.add(adjust);
                    }
                    adjustDOList.forEach(opsPdAdjustDO -> opsPdAdjustMapper.insert(opsPdAdjustDO));
                    return;
                } else {
                    diffQty = diffQty - invDo.getQuantity();
                    OpsPdAdjustDO adjust = createAdjust(item, invDo,adjustNo, adjustItemNo, invDo.getQuantity()*-1, optUser);
                    boolean exit = false;
                    for (OpsPdAdjustDO adjustDO : adjustDOList) {
                        if (Objects.equals(adjustDO.getInventoryId(), adjust.getInventoryId())) {
                            adjustDO.setAdjustQty(adjust.getAdjustQty()+adjustDO.getAdjustQty());
                            exit = true;
                        }
                    }
                    if (!exit) {
                        adjustDOList.add(adjust);
                    }
                }
                if (diffQty <= 0) {
                    adjustDOList.forEach(opsPdAdjustDO -> opsPdAdjustMapper.insert(opsPdAdjustDO));
                    return;
                }
            }
        }
        /**
         * 还有差异余量直冲入该型号的大库通用库存实在库
         * 减完还有差异余量则直接冲入将该型号大库通用库存实在库，将其减为对应的负值
         */
        if (diffQty > 0) {
            if (CollectionUtils.isNotEmpty(inventoryVOList)) {
                OpsPdAdjustDO adjust = createAdjust(item, inventoryVOList.get(0),adjustNo, adjustItemNo, diffQty*-1, optUser);
                boolean exit = false;
                for (OpsPdAdjustDO adjustDO : adjustDOList) {
                    if (Objects.equals(adjustDO.getInventoryId(), adjust.getInventoryId())) {
                        adjustDO.setAdjustQty(adjust.getAdjustQty()+adjustDO.getAdjustQty());
                        exit = true;
                    }
                }
                if (!exit) {
                    adjustDOList.add(adjust);
                }
                adjustDOList.forEach(opsPdAdjustDO -> opsPdAdjustMapper.insert(opsPdAdjustDO));
                return;
            }
        }
    }

    public OpsPdAdjustDO createAdjust(OpsAsPdThreeReportWareDO item, InventoryVO inventoryVO,String adjustNo,int itemNo,int adjustQty,String optUser) {
        // 创建调账单
        OpsPdAdjustDO opsPdAdjustDO = new OpsPdAdjustDO();
        opsPdAdjustDO.setPdBatchNo(item.getPdBatchNo());
        opsPdAdjustDO.setAdjustInvoiceNo(item.getPdBatchNo()+inventoryVO.getWarehouseCode());
        opsPdAdjustDO.setAdjustNo(adjustNo);
        opsPdAdjustDO.setAdjustItemNo(itemNo);
        opsPdAdjustDO.setModelNo(inventoryVO.getModelNo());
        opsPdAdjustDO.setAdjustQty(adjustQty);
        opsPdAdjustDO.setInventoryType(inventoryVO.getInventoryTypeCode());
        opsPdAdjustDO.setCustomerNo(inventoryVO.getCustomerNo());
        opsPdAdjustDO.setPpl(inventoryVO.getPpl());
        opsPdAdjustDO.setProjectNo(inventoryVO.getProjectCode());
        opsPdAdjustDO.setGroupCustomerNo(inventoryVO.getGroupCustomerNo());
        opsPdAdjustDO.setSalesInfoNo(inventoryVO.getSalesInfoNo());
        opsPdAdjustDO.setCreateUser(optUser);
        opsPdAdjustDO.setCreateTime(new Date());
        opsPdAdjustDO.setWarehouseCode(inventoryVO.getWarehouseCode());
        opsPdAdjustDO.setInventoryId(inventoryVO.getId());
        return opsPdAdjustDO;
    }

    public void createBlankBillData(CreateBlankRequest dto,String pdBatchNo) {
        log.info("打印盘点票输入票据数量 (createBlankBillData) => {} ",JSONUtil.toJsonStr(dto));
        int wtWarehouse = dto.getWtWarehouse();
        int subWarehouse = dto.getSubWarehouse();
        int masterWarehouse = dto.getMasterWarehouse();
        int arriveNotINWarehouse = dto.getArriveNotINWarehouse();

        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("sys");
        }
        Date nowDate = new Date();
        OpsAsPdBillDataDO item;
        if(masterWarehouse > 0) {
            ResultVo<List<com.smc.smccloud.model.WarehouseVO>> master = opsCommonService.getWarehouseByType("MASTER");
            if (!master.isSuccess() || master.getData()  == null) {
                return;
            }
            log.info("中心仓 => {}, {}",master.getData().size(), JSONUtil.toJsonStr(master.getData()));
            for (com.smc.smccloud.model.WarehouseVO warehouseVO : master.getData()) {
                createXpBlankBill(masterWarehouse,warehouseVO.getWarehouseCode(),
                        warehouseVO.getWarehouseType(),pdBatchNo,loginAuthDto.getUserNo());
            }
        }

        if(subWarehouse > 0) {
            ResultVo<List<com.smc.smccloud.model.WarehouseVO>> sub = opsCommonService.getWarehouseByType("SUB");
            if (!sub.isSuccess() || sub.getData()  == null) {
                return;
            }
            log.info("分库 => {},{}",sub.getData().size(), JSONUtil.toJsonStr(sub.getData()));
            for (com.smc.smccloud.model.WarehouseVO warehouseVO : sub.getData()) {
                createXpBlankBill(subWarehouse,warehouseVO.getWarehouseCode(),
                        warehouseVO.getWarehouseType(),pdBatchNo,loginAuthDto.getUserNo());
            }
        }

        if(wtWarehouse > 0) {
            ResultVo<List<com.smc.smccloud.model.WarehouseVO>> wt = opsCommonService.getWarehouseByType("WT");
            if (!wt.isSuccess() || wt.getData()  == null) {
                return;
            }
            log.info("委托在库 => {},{}",wt.getData().size(), JSONUtil.toJsonStr(wt.getData()));
            for (com.smc.smccloud.model.WarehouseVO warehouseVO : wt.getData()) {
                createXpBlankBill(wtWarehouse,warehouseVO.getWarehouseCode(),
                        warehouseVO.getWarehouseType(),pdBatchNo,loginAuthDto.getUserNo());
            }
        }
        // 生成到货未入空白票
        if (arriveNotINWarehouse > 0) {
            List<com.smc.smccloud.model.WarehouseVO> listWare = new ArrayList<>();
            // 中心仓
            ResultVo<List<com.smc.smccloud.model.WarehouseVO>> master = opsCommonService.getWarehouseByType("MASTER");
            if (master.isSuccess() && CollectionUtils.isNotEmpty(master.getData())) {
                listWare.addAll(master.getData());
            }
            // 分库
            ResultVo<List<com.smc.smccloud.model.WarehouseVO>> sub = opsCommonService.getWarehouseByType("SUB");
            if (sub.isSuccess() && CollectionUtils.isNotEmpty(sub.getData())) {
                listWare.addAll(sub.getData());
            }
            // wt
            ResultVo<List<com.smc.smccloud.model.WarehouseVO>> wt = opsCommonService.getWarehouseByType("WT");
            if (wt.isSuccess() && CollectionUtils.isNotEmpty(wt.getData())) {
                listWare.addAll(wt.getData());
            }

           log.info("需要打印到货未入空白票的仓库数量 {}, 需要打印票数量{} ",listWare.size(),listWare.size()*20);

            List<OpsAsPdBillDataDO> list = new ArrayList<>();
            for (com.smc.smccloud.model.WarehouseVO warehouseVO : listWare) {
                for (int i = 0; i < arriveNotINWarehouse*20; i++  ) {
                    item = new OpsAsPdBillDataDO();
                    item.setPdBatchNo(pdBatchNo);
                    item.setPdWarehouseType(warehouseVO.getWarehouseType());
                    item.setPdBillType(PdBillTypeEnum.DHWRBLANKBILL.getCode());
                    item.setPdDataType(PdDataTypeEnum.CG.getCode());
                    item.setDelFlag(false);
                    item.setWarehouseCode(warehouseVO.getWarehouseCode());
                    // item.setInvoiceNo(billDataDO.getInvoiceNo());
                    item.setCreateTime(nowDate);
                    item.setUpdateTime(nowDate);
                    item.setCreateUser(loginAuthDto.getUserNo());
                    item.setUpdateUser(loginAuthDto.getUserNo());
                    item.setRemark("到货未入空白票");
                    list.add(item);
                }
            }
            List<List<OpsAsPdBillDataDO>> arrayListArrayList = ListUtils.partition(list,100);
            for (List<OpsAsPdBillDataDO> i : arrayListArrayList) {
                opsAsPdBillDataMapper.batchInsertBlackPdBill(i);
            }
        }

    }

    public void createXpBlankBill(int size,String warehousCode,String warehouseType, String pdBatchNo,String optUserNo) {
        Date nowDate = new Date();
        OpsAsPdBillDataDO item;
        List<OpsAsPdBillDataDO> list = new ArrayList<>();
        for (int i = 0; i < size*20; i++  ) {
            item = new OpsAsPdBillDataDO();
            item.setPdBatchNo(pdBatchNo);
            if( ( StringUtils.isNotBlank(warehouseType) && warehouseType.equals("WT") ) || warehousCode.startsWith("W")) {
                item.setPdDataType(PdDataTypeEnum.WT.getCode());
            } else {
                item.setPdDataType(PdDataTypeEnum.ST.getCode());
            }
            item.setPdBillType(PdBillTypeEnum.XPBLANKBILL.getCode());
            item.setDelFlag(false);
            item.setWarehouseCode(warehousCode);
            item.setPdWarehouseType(warehouseType);
            item.setCreateTime(nowDate);
            item.setUpdateTime(nowDate);
            item.setCreateUser(optUserNo);
            item.setUpdateUser(optUserNo);
            item.setRemark("现品空白票");
            list.add(item);
        }
        List<List<OpsAsPdBillDataDO>> partition = ListUtils.partition(list, 100);
        for (List<OpsAsPdBillDataDO> i: partition) {
            opsAsPdBillDataMapper.batchInsertBlackPdBill(i);
        }

//        int toIndex = 180;
//        // 截取list 每180一组
//        for (int j = 0; j < list.size(); j+=180) {
//            if (j+180 > list.size()) {
//                toIndex = list.size() - j;
//            }
//            List<OpsAsPdBillDataDO> tempList = list.subList(j, j + toIndex);
//            opsAsPdBillDataMapper.batchInsertBlackPdBill(tempList);
//        }
    }

    // 生成盘点票号回更盘点票表 -- 现品票.数据票 现品空白票
    public int createXPPdBillNo(int billCount,String pdBatchNo) {

        /**
         *  现品票.数据票按照仓库代码,盘点顺序.货架号.型号排序
         *  到货未入清单票 按照仓库代码 发票号 拖号 型号 BarCode排序
         *  每个货架最多每20行生成一个盘点票号 更新盘点票号字段
         *  到货未入清单票和到货未入的空白票的票号格式为 仓库代码+发票号+5位连编
         *  实盘票 数据票 实盘空白票的票号格式为仓库代码+盘点批次号+5位连编
         *  各仓到货未入的空白票号必须排在到货未入清单票的后面
         *  实盘空白票的票号必须排在实盘票号的后面
         */

        AtomicInteger billCountNum = new AtomicInteger(billCount);

        // 现品票.数据票
        List<String> PDbillTypeList = new ArrayList<>();
        PDbillTypeList.add(PdBillTypeEnum.XPBILL.getCode());
        PDbillTypeList.add(PdBillTypeEnum.SJBILL.getCode());
        PDbillTypeList.add(PdBillTypeEnum.XPBLANKBILL.getCode());

        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(6000);
        PageInfo<OpsAsPdBillDataDO> pageInfo;
        Map<String, List<OpsAsPdBillDataDO>> mapByShelves = new HashMap<>();
        List<OpsAsPdBillDataDO> xpBlankBillArr = new ArrayList<>();
        List<OpsAsPdBillDataDO> shelvesList = new ArrayList<>();

        // 先按照仓库进行分组 每个仓库存的都是从00001开始，先现品票再空白票再数据票；到货未入的每个仓也是00001开始
        while (true) {
            pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> {
                        pdBillService.findPdBillData(PDbillTypeList,pdBatchNo);
                    });
            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                return 1;
            }
            // ExecutorService fixedThreadPoolwithHandSelData = Executors.newFixedThreadPool(5);
            // try {
                for (OpsAsPdBillDataDO item: pageInfo.getList()) {
                    // fixedThreadPoolwithHandSelData.execute(() -> {
                        if (StringUtils.isBlank(item.getShelvesNo())) {
                            item.setShelvesNo("shelves");
                        }
                        if (item.getPdBillType().equals(PdBillTypeEnum.XPBLANKBILL.getCode())) {
                            item.setShelvesNo("xpBlankBill");
                        }
                        if (item.getShelvesNo().equals("xpBlankBill")) {
                            xpBlankBillArr.add(item);
                        } else if (item.getShelvesNo().equals("shelves")) {
                            shelvesList.add(item);
                        } else {
                            if (mapByShelves.containsKey(item.getShelvesNo())) {
                                List<OpsAsPdBillDataDO> opsAsPdBillDataDOList = mapByShelves.get(item.getShelvesNo());
                                opsAsPdBillDataDOList.add(item);
                                mapByShelves.put(item.getShelvesNo(),opsAsPdBillDataDOList);
                            } else {
                                List<OpsAsPdBillDataDO> tempList = new ArrayList<>();
                                tempList.add(item);
                                mapByShelves.put(item.getShelvesNo(),tempList);
                            }
                        }
                   // });
                }
            // }
            //finally {
//                fixedThreadPoolwithHandSelData.shutdown();
//                while (true) {
//                    if (fixedThreadPoolwithHandSelData.isTerminated()) {
//                        break;
//                    }
//                }
           // }

            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }
        log.info("map: size() "+mapByShelves.keySet().size());
        LambdaUpdateWrapper<OpsAsPdBillDataDO> updateWrapper;
        // ExecutorService fixedThreadPoolwithHandUpData = Executors.newFixedThreadPool(10);
        try {
            Date nowDate = new Date();
            for (String key : mapByShelves.keySet()) {
                // fixedThreadPoolwithHandUpData.execute(() -> {
                if (StringUtils.isBlank(key)) {
                    continue;
                }
                if (key.equals("xpBlankBill") || key.equals("shelves") ) {
                    continue;
                }
                List<OpsAsPdBillDataDO> list = mapByShelves.get(key);
                if (CollectionUtils.isEmpty(list)) {
                    continue;
                }
                Map<String,List<OpsAsPdBillDataDO>> map = groupList(list, 20);
                for (String keyStr : map.keySet()) {
                    if (StringUtils.isBlank(keyStr)) {
                        continue;
                    }
                    if (CollectionUtils.isEmpty(map.get(keyStr))) {
                        continue;
                    }
                    List<Long> ids = map.get(keyStr).stream().map(OpsAsPdBillDataDO::getId).collect(Collectors.toList());
                    String pdBillNo = creBillNo(map.get(keyStr).get(0), billCountNum.get());
                    updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper
                            .in(OpsAsPdBillDataDO::getId,ids)
                            .set(OpsAsPdBillDataDO::getPdBillNo,pdBillNo)
                            .set(OpsAsPdBillDataDO::getUpdateTime,nowDate);
                    opsAsPdBillDataMapper.update(null, updateWrapper);// 更新生成的盘点票号
//                    for (OpsAsPdBillDataDO i : map.get(keyStr)) {
//                        String pdBillNo = creBillNo(i, billCountNum.get());
//                        if (StringUtils.isBlank(pdBatchNo)) {
//                            throw new BusinessException("票号为空. "+i.getId());
//                        }
//                        updateWrapper = new LambdaUpdateWrapper<>();
//                        updateWrapper
//                                .eq(OpsAsPdBillDataDO::getId,i.getId())
//                                .set(OpsAsPdBillDataDO::getPdBillNo,pdBillNo)
//                                .set(OpsAsPdBillDataDO::getUpdateTime,nowDate);
//                        int update = opsAsPdBillDataMapper.update(null, updateWrapper);// 更新生成的盘点票号
//                        if (update == 0) {
//                            throw new BusinessException("更新0条数据. "+i.getId() +"票号"+ pdBillNo);
//                        }
//                    }
                    billCountNum.getAndIncrement();
                }
               //  });
            }
        } finally {
//            fixedThreadPoolwithHandUpData.shutdown();
//            while (true) {
//                if (fixedThreadPoolwithHandUpData.isTerminated()) {
//                    break;
//                }
//            }
        }
        // 货架为空的
        List<OpsAsPdBillDataDO> tempShelesBlankArr = JSON.parseArray(JSON.toJSONString(shelvesList), OpsAsPdBillDataDO.class);
        if (CollectionUtils.isEmpty(tempShelesBlankArr)) {
            throw new BusinessException("未成功回写现品票票号");
        }
        int countUp = 1;
        log.info("货架号为空的list大小 {}",tempShelesBlankArr.size());
        Map<String,List<OpsAsPdBillDataDO>> map = groupList(tempShelesBlankArr, 20);
        for (String keyStr : map.keySet()) {
            if (StringUtils.isBlank(keyStr)) {
                continue;
            }
            if (CollectionUtils.isEmpty(map.get(keyStr))) {
                continue;
            }
            for (OpsAsPdBillDataDO i : map.get(keyStr)) {
                String pdBillNo = creBillNo(i, billCountNum.get());
                if (StringUtils.isBlank(pdBatchNo)) {
                    throw new BusinessException("票号为空. "+i.getId());
                }
                updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper
                        .eq(OpsAsPdBillDataDO::getId,i.getId())
                        .set(OpsAsPdBillDataDO::getPdBillNo,pdBillNo)
                        .set(OpsAsPdBillDataDO::getUpdateTime,new Date());
                int update = opsAsPdBillDataMapper.update(null, updateWrapper);// 更新生成的盘点票号
                if (update == 0) {
                    throw new BusinessException("更新0条数据. "+i.getId() +"票号"+ pdBillNo);
                }
                countUp++;
            }
            billCountNum.getAndIncrement();
        }
        log.info("更新货架号为空的list数量"+countUp);

        // 紧跟生成现品票空白票
        if (CollectionUtils.isEmpty(xpBlankBillArr)) {
            throw new BusinessException("没有现品空白票,生成现品空白票失败.");
        }
        List<List<OpsAsPdBillDataDO>> arrayListArrayList = ListUtils.partition(xpBlankBillArr,20);
            for (List<OpsAsPdBillDataDO> i : arrayListArrayList) {
                if (CollectionUtils.isNotEmpty(i)) {
                    for (OpsAsPdBillDataDO billDataDO : i) {
                        String pdBillNo = creBillNo(billDataDO, billCountNum.get());
                        updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper
                                .eq(OpsAsPdBillDataDO::getId,billDataDO.getId())
                                .set(OpsAsPdBillDataDO::getPdBillNo,pdBillNo)
                                .set(OpsAsPdBillDataDO::getUpdateTime,new Date());
                        opsAsPdBillDataMapper.update(null,updateWrapper); // 更新生成的盘点票号
                    }
                    billCountNum.getAndIncrement();
                }

            }
        return billCountNum.get();
    }

    int num = 0;

    public boolean createPdBillNoByWarehouseCodeWithXP(String pdBatchNo) {

        /**
         *  现品票.数据票按照仓库代码,盘点顺序.货架号,po_sort,型号排序
         *  到货未入清单票 按照仓库代码 发票号 拖号 型号 BarCode排序
         *  每个货架最多每20行生成一个盘点票号 更新盘点票号字段
         *  到货未入清单票和到货未入的空白票的票号格式为 仓库代码+发票号+5位连编
         *  实盘票 数据票 实盘空白票的票号格式为仓库代码+盘点批次号+5位连编
         *  各仓到货未入的空白票号必须排在到货未入清单票的后面
         *  实盘空白票的票号必须排在实盘票号的后面
         */
        int count = opsAsPdBillDataMapper.selectWarehouseCodeIsNull(pdBatchNo);
        if (count > 0) {
            throw new BusinessException("存在仓库代码为空的数据，无法生成盘点票，请确认数据");
        }
        List<String> warehouseCodes = opsAsPdBillDataMapper.findWarehouseCode();
        for (String item : warehouseCodes) {
            // 实体货架现品票
            int billNoNum = createPdBillNo(PdBillTypeEnum.XPBILL.getCode(),pdBatchNo,item,PdDataTypeEnum.ST.getCode(),1,1);
            // 委托在库现品票
            billNoNum = createPdBillNo(PdBillTypeEnum.XPBILL.getCode(),pdBatchNo,item,PdDataTypeEnum.WT.getCode(),billNoNum,1);
            // 过渡库位现品票
            billNoNum = createPdBillNo(PdBillTypeEnum.XPBILL.getCode(),pdBatchNo,item,PdDataTypeEnum.GD.getCode(),billNoNum,1);
            // 集约区现品票
            billNoNum = createPdBillNo(PdBillTypeEnum.XPBILL.getCode(),pdBatchNo,item,PdDataTypeEnum.JY.getCode(),billNoNum,1);
            // 现品空白票
            billNoNum = createPdBillNo(PdBillTypeEnum.XPBLANKBILL.getCode(), pdBatchNo, item,"", billNoNum,1);
            // 数据票
            billNoNum = createPdBillNo(PdBillTypeEnum.SJBILL.getCode(), pdBatchNo, item,"", billNoNum,1);
        }
        log.info("更新数量: "+ num);
        return true;
    }

    public boolean createPdBillNoByWarehouseCodeWittArriveNotIn(String pdBatchNo) {

        /**
         *  现品票.数据票按照仓库代码,盘点顺序.货架号.型号排序
         *  到货未入清单票 按照仓库代码 发票号 拖号 型号 BarCode排序
         *  每个货架最多每20行生成一个盘点票号 更新盘点票号字段
         *  到货未入清单票和到货未入的空白票的票号格式为 仓库代码+发票号+5位连编
         *  实盘票 数据票 实盘空白票的票号格式为仓库代码+盘点批次号+5位连编
         *  各仓到货未入的空白票号必须排在到货未入清单票的后面
         *  实盘空白票的票号必须排在实盘票号的后面
         */
        int count = opsAsPdBillDataMapper.selectWarehouseCodeIsNull(pdBatchNo);
        if (count > 0) {
            throw new BusinessException("存在仓库代码为空的数据，无法生成盘点票，请确认数据");
        }
        List<String> warehouseCodes = opsAsPdBillDataMapper.findWarehouseCode();
        for (String item : warehouseCodes) {
            // 清单票
            int billNoNum = createPdBillNo(PdBillTypeEnum.QDBILL.getCode(), pdBatchNo, item,"", 1,2);
            // 到货未入空白票
            billNoNum = createPdBillNo(PdBillTypeEnum.DHWRBLANKBILL.getCode(), pdBatchNo, item,"", billNoNum,2);
        }
        return true;
    }

    /**
     *
     * @param billType
     * @param pdBatchNo
     * @param warehouseCode
     * @param billNoCount
     * @param flag   1-> 实盘 2->到货未入
     * @return
     */
    public int createPdBillNo(String billType,String pdBatchNo,String warehouseCode,String dataType, int billNoCount,int flag) {
        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(10000);
        PageInfo<OpsAsPdBillDataDO> pageInfo;
        Map<String,List<OpsAsPdBillDataDO>> mapByShelves = new HashMap<>();
        while (true) {
            pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> {
                        opsAsPdBillDataMapper.findDataByBillType(billType,pdBatchNo,warehouseCode,dataType);
                    });
            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                return billNoCount;
            }
            for (OpsAsPdBillDataDO item : pageInfo.getList()) {
                // 实体票按照货架号排 到货未入按照发票号排
                if (flag == 1) {
                    if (StringUtils.isBlank(item.getShelvesNo())) {
                        item.setShelvesNo("shelves");
                    }
                    if (mapByShelves.containsKey(item.getShelvesNo())) {
                        List<OpsAsPdBillDataDO> opsAsPdBillDataDOList = mapByShelves.get(item.getShelvesNo());
                        opsAsPdBillDataDOList.add(item);
                        mapByShelves.put(item.getShelvesNo(),opsAsPdBillDataDOList);
                    } else {
                        List<OpsAsPdBillDataDO> tempList = new ArrayList<>();
                        tempList.add(item);
                        mapByShelves.put(item.getShelvesNo(),tempList);
                    }
                } else {
                    if (billType.equals(PdBillTypeEnum.DHWRBLANKBILL.getCode())) {
                        if (StringUtils.isBlank(item.getInvoiceNo())) {
                            item.setInvoiceNo("DHWRKBP");
                        }
                    } else {
                        if (StringUtils.isBlank(item.getInvoiceNo())) {
                            item.setInvoiceNo("invoiceNo");
                        }
                    }
                    if (mapByShelves.containsKey(item.getInvoiceNo())) {
                        List<OpsAsPdBillDataDO> opsAsPdBillDataDOList = mapByShelves.get(item.getInvoiceNo());
                        opsAsPdBillDataDOList.add(item);
                        mapByShelves.put(item.getInvoiceNo(),opsAsPdBillDataDOList);
                    } else {
                        List<OpsAsPdBillDataDO> tempList = new ArrayList<>();
                        tempList.add(item);
                        mapByShelves.put(item.getInvoiceNo(),tempList);
                    }
                }
            }
            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }
        LambdaUpdateWrapper<OpsAsPdBillDataDO> updateWrapper;
        Date nowDate = new Date();
        // 1.2 按照仓库,货架号来 每个仓都是从00001开始  每个货架最多每20行生成一个盘点票号 更新盘点票号字段
        AtomicInteger billCountNum = new AtomicInteger(billNoCount);
        List<String> keyList = mapByShelves.keySet().stream().sorted(String::compareTo).collect(Collectors.toList());
        for (String mapByShelvesKey : keyList) {
            List<OpsAsPdBillDataDO> collect;
            if (flag == 1) {
                // 现品票.数据票按照仓库代码,盘点顺序.货架号.型号排序
                collect = mapByShelves.get(mapByShelvesKey).stream().sorted(
                        Comparator.comparing(OpsAsPdBillDataDO::getPdNo,  new Comparator<String>() {
                            @Override
                            public int compare(String o1, String o2) {
                                if (StringUtils.isBlank(o1)) {
                                    o1 = "0";
                                }
                                if (StringUtils.isBlank(o2)) {
                                    o2 = "0";
                                }
                                try {
                                    return Integer.parseInt(o1.trim()) - Integer.parseInt(o2.trim());
                                } catch (NumberFormatException e) {
                                   return -1;
                                }
                            }
                        })
                        .thenComparing(OpsAsPdBillDataDO::getShelvesNo, Comparator.nullsFirst(String::compareTo))
                        .thenComparing(OpsAsPdBillDataDO::getPdSort, new Comparator<String>() {
                            @Override
                            public int compare(String o1, String o2) {
                                if (StringUtils.isBlank(o1)) {
                                    o1 = "0";
                                }
                                if (StringUtils.isBlank(o2)) {
                                    o2 = "0";
                                }
                                try {
                                    return Integer.parseInt(o1.trim()) - Integer.parseInt(o2.trim());
                                } catch (NumberFormatException e) {
                                    return -1;
                                }
                            }
                        })
                        .thenComparing(OpsAsPdBillDataDO::getModelNo1, Comparator.nullsFirst(String::compareTo))
                ).collect(Collectors.toList());
            } else {
                // 到货未入清单票 按照仓库代码 发票号 拖号 型号 BarCode排序
                collect = mapByShelves.get(mapByShelvesKey).stream().sorted(
                        Comparator.comparing(OpsAsPdBillDataDO::getInvoiceNo, Comparator.nullsFirst(String::compareTo))
                                .thenComparing(OpsAsPdBillDataDO::getCaseNo, Comparator.nullsFirst(String::compareTo))
                                .thenComparing(OpsAsPdBillDataDO::getModelNo1, Comparator.nullsFirst(String::compareTo))
                                .thenComparing(OpsAsPdBillDataDO::getBarcode, Comparator.nullsFirst(String::compareTo))
                ).collect(Collectors.toList());
            }

            Map<String,List<OpsAsPdBillDataDO>> map = groupList(collect, 20);
            List<String> collect1 = map.keySet().stream().sorted(String::compareTo).collect(Collectors.toList());
            for (String keyStr : collect1) {
                List<Long> ids = map.get(keyStr).stream().map(OpsAsPdBillDataDO::getId).collect(Collectors.toList());
                String pdBillNo = creBillNo(map.get(keyStr).get(0), billCountNum.get());
                if (StringUtils.isBlank(pdBillNo)) {
                    throw new BusinessException(map.get(keyStr).get(0).getId()+"存在仓库代码为空的数据，无法生成盘点票，请确认数据");
                }
                updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper
                        .in(OpsAsPdBillDataDO::getId,ids)
                        .set(OpsAsPdBillDataDO::getPdBillNo,pdBillNo)
                        .set(OpsAsPdBillDataDO::getUpdateTime,nowDate);
                int update = opsAsPdBillDataMapper.update(null, updateWrapper);// 更新生成的盘点票号
                num += update;
                billCountNum.getAndIncrement();
            }
        }
        return billCountNum.get();
    }

    public String creBillNo(OpsAsPdBillDataDO item,int billCount) {
        if (item == null || StringUtils.isBlank(item.getPdBillType()) || billCount == 0) {
            log.error("JSON=>"+billCount+"数据=>"+JSONUtil.toJsonPrettyStr(item));
            return null;
        }
        if (StringUtils.isBlank(item.getWarehouseCode())) {
            return null;
        }
        /**
         * *  到货未入清单票和到货未入的空白票的票号格式为 仓库代码+发票号+5位连编
         * *  实盘票 数据票 实盘空白票的票号格式为仓库代码+盘点批次号+5位连编
         */
        String format = String.format("%05d", billCount);
        String billNo = "";
        if (PdBillTypeEnum.QDBILL.getCode().equals(item.getPdBillType())
                || PdBillTypeEnum.DHWRBLANKBILL.getCode().equals(item.getPdBillType()) ) {
            billNo = item.getWarehouseCode()+item.getInvoiceNo()+format;
        } else {
            billNo = item.getWarehouseCode()+item.getPdBatchNo()+format;
        }
        return billNo;
    }


    public OpsAsPdBillDataDO conventOpsAsPdBillDataDO(OpsAsWmsInventoryDataDO item, LoginUserDTO loginAuthDto,String pdBatchNo, Date nowDate) {
        OpsAsPdBillDataDO billDataDO = new OpsAsPdBillDataDO();
        billDataDO.setPdBatchNo(pdBatchNo);
        billDataDO.setWarehouseCode(item.getWarehouseCode());
        // billDataDO.setPdBillNo();
        billDataDO.setShelvesNo(item.getShelvesNo());
        billDataDO.setLocationNo(item.getLocationNo());
        billDataDO.setInvoiceNo(item.getInvoiceNo());
        billDataDO.setCaseNo(item.getCaseNo());
        billDataDO.setBarcode(item.getBarcode());
        billDataDO.setPdNo(item.getPdNo());
        billDataDO.setModelNo1(item.getModelNo());
        billDataDO.setBillQty(item.getBillQty());
        billDataDO.setPdInputort1(loginAuthDto.getUserNo());
        billDataDO.setPdInputTime1(nowDate);
        billDataDO.setPdDataType(item.getPdDataType());
        if (StringUtils.isNotBlank(item.getPdDataType()) && item.getPdDataType().equals(PdDataTypeEnum.WT.getCode()))
        {
            billDataDO.setPdBillType(PdBillTypeEnum.XPBILL.getCode());
        } else {
            billDataDO.setPdBillType(item.getPdBillType());
        }
        billDataDO.setOrderNo(item.getOrderNo());
        billDataDO.setPdWarehouseType(item.getWarehouseType());
        billDataDO.setDelFlag(false);
        billDataDO.setPdSort(item.getPdSort());
        billDataDO.setCreateUser(loginAuthDto.getUserNo());
        billDataDO.setCreateTime(nowDate);
        billDataDO.setUpdateTime(nowDate);
        billDataDO.setUpdateUser(loginAuthDto.getUserNo());
        if (StringUtils.isNotBlank(billDataDO.getPdBillType()) &&
                (billDataDO.getPdBillType().equals(PdBillTypeEnum.SJBILL.getCode()) || billDataDO.getPdBillType().equals(PdBillTypeEnum.QDBILL.getCode()))) {
            billDataDO.setPdQty1(item.getBillQty());
        }
        billDataDO.setIsAss(StringUtils.isBlank(item.getIsAss()) ? "0":item.getIsAss());
        return billDataDO;
    }


    public static Map groupList(List list,int size){

        int listSize=list.size();
        int toIndex=size;
        Map map = new HashMap();
        int keyToken = 0;
        for(int i = 0;i<list.size();i+=size){
            if(i+size>listSize){
                toIndex=listSize-i;
            }
            List newList = new ArrayList<>(list.subList(i,i+toIndex));
            map.put("keyName"+keyToken, newList);
            keyToken++;
        }
        return map;
    }

    @Override
    public  ResultVo<PageInfo<OpsAsWmsInventoryArrivedNotInDO>> listArriveNotInWithGroup(OpsArriverNotInRequestVO dto) {

        if(dto == null) {
            return ResultVo.failure("入参不可为空");
        }
        if (StringUtils.isNotBlank(dto.getInvoiceNo())) {
            dto.setInvoiceNo(dto.getInvoiceNo()+"%");
        }
        if (StringUtils.isNotBlank(dto.getWarehouseType()) && CollectionUtils.isEmpty(dto.getWarehouseCodes())) {
            ResultVo<List<WarehouseVO>> warehouseByType = commonServiceFeignApi.getWarehouseByType(dto.getWarehouseType());
            if (!warehouseByType.isSuccess() || CollectionUtils.isEmpty(warehouseByType.getData())) {
                return ResultVo.failure("根据仓库类型获取仓库编码失败");
            }
            List<WarehouseVO> data = warehouseByType.getData();
            List<String> list = new ArrayList<>();
            for (WarehouseVO item :data) {
                list.add(item.getWarehouseCode());
            }
            dto.setWarehouseCodes(list);
        }
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess() || batchNoWithIsActive.getData() == null ) {
            return ResultVo.failure("获取盘点批次号失败");
        }
        if(StringUtils.isBlank(dto.getPdBatchNo())) {
            dto.setPdBatchNo(batchNoWithIsActive.getData().getPdBatchNo());
        }
        PageInfo<OpsAsWmsInventoryArrivedNotInDO> pageInfo = PageHelper.startPage(dto.getPage().getPageNumber(), dto.getPage().getPageSize()).doSelectPageInfo(
                                                                                    () -> opsAsWmsInventoryArrivedNotInMapper.listArriveNotInWithGroup(dto,dto.getPdBatchNo()));
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            pageInfo.setList(commonArriveNotInDataHandWithGroup(pageInfo.getList()));
        }
        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<PageInfo<OpsAsWmsInventoryArrivedNotInDO>> listArriveNotInDetail(OpsArriverNotInRequestVO dto) {
        if(dto == null) {
            return ResultVo.failure("入参不可为空");
        }
        if (StringUtils.isNotBlank(dto.getInvoiceNo())) {
            dto.setInvoiceNo(dto.getInvoiceNo()+"%");
        }
        ResultVo<OpsAsPdBatchDO> batchNoWithIsActive = getBatchNoWithIsActive();
        if (!batchNoWithIsActive.isSuccess()) {
            return ResultVo.failure("获取当前激活盘点批次失败");
        }
        if (batchNoWithIsActive.getData() == null) {
            return ResultVo.failure("当前不存在已激活的盘点批次,请确认");
        }

        if (StringUtils.isBlank(dto.getPdBatchNo())) {
            dto.setPdBatchNo(batchNoWithIsActive.getData().getPdBatchNo());
        }

        if (StringUtils.isNotBlank(dto.getWarehouseType()) && CollectionUtils.isEmpty(dto.getWarehouseCodes())) {
            ResultVo<List<WarehouseVO>> warehouseByType = commonServiceFeignApi.getWarehouseByType(dto.getWarehouseType());
            if (!warehouseByType.isSuccess() || CollectionUtils.isEmpty(warehouseByType.getData())) {
                return ResultVo.failure("根据仓库类型获取仓库编码失败");
            }
            List<WarehouseVO> data = warehouseByType.getData();
            List<String> list = new ArrayList<>();
            for (WarehouseVO item :data) {
                list.add(item.getWarehouseCode());
            }
            dto.setWarehouseCodes(list);
        }
        PageInfo<OpsAsWmsInventoryArrivedNotInDO> pageInfo = PageHelper.startPage(dto.getPage().getPageNumber(), dto.getPage().getPageSize()).doSelectPageInfo(
                () -> opsAsWmsInventoryArrivedNotInMapper.listArriveNotInDetail(dto,dto.getPdBatchNo()));
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
           pageInfo.setList(commonArriveNotInDataHandDetail(pageInfo.getList()));
        }
        return ResultVo.success(pageInfo);
    }

    public void exportInvoiceData(List<OpsAsWmsInventoryArrivedNotInDO> list,String filePath) {
        if (CollectionUtils.isEmpty(list)){
            return;
        }
        if(StringUtils.isBlank(filePath)) {
            return;
        }
        ExcelUtil excel = new ExcelUtil(filePath);
        excel.openSheet(0);
        // 向模板中写入数据
        int row = 1;
        int cel;
        for (OpsAsWmsInventoryArrivedNotInDO item : list) {
            cel = 0;
            excel.setCellValue(row, cel++, item.getPdBatchNo());
            excel.setCellValue(row, cel++, item.getWmsSysWarehouseCode());
            excel.setCellValue(row, cel++, item.getWmsSysInvoiceNo());
            excel.setCellValue(row, cel++, item.getLWarehouseCode());
            excel.setCellValue(row, cel++, item.getLInvoiceNo());
            excel.setCellValue(row, cel++, item.getCaseNo());
            excel.setCellValue(row, cel++, item.getOrderNo());
            excel.setCellValue(row, cel++, item.getModelNo());
            String codeNameByCode = PdDataTypeEnum.getCodeNameByCode(item.getPdDataType());
            excel.setCellValue(row, cel++, codeNameByCode);
            excel.setCellValue(row, cel++, item.getBillQty());
            excel.setCellValue(row, cel++, item.getBarcode());
            excel.setCellValue(row, cel++, item.getLogisticsConfirm());
            excel.setCellValue(row, cel, item.getCreateTime2());
            row++;
        }
        excel.writeToResponse(response, "到货未入数据导出-明细数据.xlsx");
    }

    public void exportInvoiceDataGroupByInvoiceNo(List<OpsAsWmsInventoryArrivedNotInDO> list,String filePath) {
        if (CollectionUtils.isEmpty(list)){
            return;
        }
        if(StringUtils.isBlank(filePath)) {
            return;
        }
        ExcelUtil excel = new ExcelUtil(filePath);
        excel.openSheet(0);
        // 向模板中写入数据
        int row = 1;
        int cel;
        for (OpsAsWmsInventoryArrivedNotInDO item : list) {
            cel = 0;
            excel.setCellValue(row, cel++, item.getPdBatchNo());
            excel.setCellValue(row, cel++, item.getWmsSysWarehouseCode());
            excel.setCellValue(row, cel++, item.getWmsSysInvoiceNo());
            excel.setCellValue(row, cel++, item.getWmsSysIsAll());
            excel.setCellValue(row, cel++, item.getLWarehouseCode());
            excel.setCellValue(row, cel++, item.getLInvoiceNo());
            excel.setCellValue(row, cel++, item.getLIsAll());
            excel.setCellValue(row, cel, item.getLogisticsConfirm());
            row++;
        }
        excel.writeToResponse(response, "到货未入数据导出-发票数据.xlsx");
    }

    public List<OpsAsWmsInventoryArrivedNotInDO> commonArriveNotInDataHandWithGroup(List<OpsAsWmsInventoryArrivedNotInDO> list) {

        for (OpsAsWmsInventoryArrivedNotInDO item : list) {
            if (StringUtils.isNotBlank(item.getLIsAll())) {
                item.setLIsAll(item.getLIsAll().equals("1") ? "是":"否");
            }
            if (StringUtils.isNotBlank(item.getWmsSysIsAll())) {
                item.setWmsSysIsAll(item.getWmsSysIsAll().equals("1") ? "是":"否");
            }
            if (StringUtils.isNotBlank(item.getLWarehouseCode())) {
                String warehouseNameByCode = opsCommonService.getWarehouseNameByCode(item.getLWarehouseCode());
                item.setLWarehouseCode("["+item.getLWarehouseCode()+"]"+warehouseNameByCode);
            }
            if (StringUtils.isNotBlank(item.getWmsSysWarehouseCode())) {
                String warehouseNameByCode = opsCommonService.getWarehouseNameByCode(item.getWmsSysWarehouseCode());
                item.setWmsSysWarehouseCode("["+item.getWmsSysWarehouseCode()+"]"+warehouseNameByCode);
            }
            if (StringUtils.isNotBlank(item.getLogisticsConfirm())) {
                item.setLogisticsConfirm(item.getLogisticsConfirm().equals("1") ? "有" : "没有");
            }
            if (item.getCreateTime() != null) {
                item.setCreateTime2(item.getCreateTime());
            }
        }
        return list;
    }

    public List<OpsAsWmsInventoryArrivedNotInDO> commonArriveNotInDataHandWithGroup2(List<OpsAsWmsInventoryArrivedNotInDO> list) {

        for (OpsAsWmsInventoryArrivedNotInDO item : list) {
            if (StringUtils.isNotBlank(item.getLIsAll())) {
                item.setLIsAll(item.getLIsAll().equals("1") ? "是":"否");
            }
            if (StringUtils.isNotBlank(item.getWmsSysIsAll())) {
                item.setWmsSysIsAll(item.getWmsSysIsAll().equals("1") ? "是":"否");
            }
//            if (StringUtils.isNotBlank(item.getLWarehouseCode())) {
//                String warehouseNameByCode = opsCommonService.getWarehouseNameByCode(item.getLWarehouseCode());
//                item.setLWarehouseCode("["+item.getLWarehouseCode()+"]"+warehouseNameByCode);
//            }
//            if (StringUtils.isNotBlank(item.getWmsSysWarehouseCode())) {
//                String warehouseNameByCode = opsCommonService.getWarehouseNameByCode(item.getWmsSysWarehouseCode());
//                item.setWmsSysWarehouseCode("["+item.getWmsSysWarehouseCode()+"]"+warehouseNameByCode);
//            }
            if (StringUtils.isNotBlank(item.getLogisticsConfirm())) {
                item.setLogisticsConfirm(item.getLogisticsConfirm().equals("1") ? "有" : "没有");
            }
            if (item.getCreateTime() != null) {
                item.setCreateTime2(item.getCreateTime());
            }
        }
        return list;
    }

    public List<OpsAsWmsInventoryArrivedNotInDO> commonArriveNotInDataHandDetail(List<OpsAsWmsInventoryArrivedNotInDO> list) {

        for (OpsAsWmsInventoryArrivedNotInDO item : list) {

            if (StringUtils.isNotBlank(item.getLWarehouseCode())) {
                String warehouseNameByCode = opsCommonService.getWarehouseNameByCode(item.getLWarehouseCode());
                item.setLWarehouseCode("["+item.getLWarehouseCode()+"]"+warehouseNameByCode);
            }
            if (StringUtils.isNotBlank(item.getWmsSysWarehouseCode())) {
                String warehouseNameByCode = opsCommonService.getWarehouseNameByCode(item.getWmsSysWarehouseCode());
                item.setWmsSysWarehouseCode("["+item.getWmsSysWarehouseCode()+"]"+warehouseNameByCode);
            }
            if (StringUtils.isNotBlank(item.getLogisticsConfirm())) {
                item.setLogisticsConfirm(item.getLogisticsConfirm().equals("1") ? "有" : "没有");
            }
        }
        return list;
    }

    public String getDataSourceByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodesNotCache("5004");
        if (!dataCodes.isSuccess() || CollectionUtils.isEmpty(dataCodes.getData())) {
            return null;
        }
        for (DataCodeVO item : dataCodes.getData()) {
            if (code.equals(item.getCode())) {
                return item.getExtNote1();
            }
        }
        return null;
    }

    @Override
    public ResultVo<List<OpsPdAdjustDO>>  findAdjustDoList(String pdBatchNo) {

        if (StringUtils.isNotBlank(pdBatchNo)) {
            pdBatchNo = pdBatchNo+"%";
        }

        List<OpsPdAdjustDO> pdAdjustDOS = opsAsPdBatchMapper.listPdAdjustBatchNo(pdBatchNo);

        if (CollectionUtils.isEmpty(pdAdjustDOS)) {
            return ResultVo.failure();
        }

        return ResultVo.success(pdAdjustDOS);
    }

    // 数据类型1 样品未结转，2已发货未推财务，3财务补偿数据，4调拨在途，5制造在途
    public String conventCompensateDataType(String code) {
        String codeName = code;
        if (StringUtils.isBlank(code)) {
            return codeName;
        }
        if (code.equals("1")) {
            codeName = "样品未结转";
        } else if (code.equals("2")) {
            codeName = "已发货未推财务";
        } else if (code.equals("3")) {
            codeName = "财务补偿数据";
        } else if (code.equals("4")) {
            codeName = "调拨在途";
        } else if (code.equals("5")) {
            codeName = "制造在途";
        }
        return codeName;
    }
    // 数据来源1WMS，2OPS ，3财务系统，调拨在途和制造在途WMS和OPS均有自己的数据
    public String conventCompensateDataResource(String code) {
        String codeName = code;
        if (StringUtils.isBlank(code)) {
            return codeName;
        }
        if (code.equals("1")) {
            codeName = "WMS";
        } else if (code.equals("2")) {
            codeName = "OPS";
        } else if (code.equals("3")) {
            codeName = "财务系统";
        }
        return codeName;
    }

    // 数据类型1 库存数据，2 OPS采购到货未入，3 OPS调拨到货未入，4OPS退货到货未入
    public String conventOpsInventoryDataType(String code) {
        String codeName = code;
        if (StringUtils.isBlank(code)) {
            return codeName;
        }
        if (code.equals("1")) {
            codeName = "库存数据";
        } else if (code.equals("2")) {
            codeName = "OPS采购到货未入";
        } else if (code.equals("3")) {
            codeName = "OPS调拨到货未入";
        } else if (code.equals("4")) {
            codeName = "OPS退货到货未入";
        }
        return codeName;
    }

}
