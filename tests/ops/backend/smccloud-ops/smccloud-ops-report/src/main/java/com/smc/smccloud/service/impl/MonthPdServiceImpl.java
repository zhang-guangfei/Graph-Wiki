package com.smc.smccloud.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.CommonMapper;
import com.smc.smccloud.mapper.pd.*;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.WarehouseVO;
import com.smc.smccloud.model.enums.PdBillTypeEnum;
import com.smc.smccloud.model.enums.PdDataTypeEnum;
import com.smc.smccloud.model.enums.PdOtherDataImpEnum;
import com.smc.smccloud.model.enums.YdPdExecStepCodeEnum;
import com.smc.smccloud.model.pd.*;
import com.smc.smccloud.service.DictCommonService;
import com.smc.smccloud.service.MonthPdService;
import com.smc.smccloud.service.OpsCommonService;
import com.smc.smccloud.service.PdService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author lyc
 * @Date 2024/11/1 9:32
 * @Descripton TODO
 */
@Service
@Slf4j
public class MonthPdServiceImpl implements MonthPdService {

    @Resource
    private OpsAsPdBatchYdMapper opsAsPdBatchYdMapper;

    @Resource
    private PdService pdService;

    @Resource
    private OpsPdStepManageMapper opsPdStepManageMapper;

    @Resource
    private OpsAsWmsInventoryDataMapper opsAsWmsInventoryDataMapper;
    @Resource
    private OpsAsWmsSubInventoryDataMapper opsAsWmsSubInventoryDataMapper;

    @Resource
    private OpsPdExecPlanManageMapper opsPdExecPlanManageMapper;

    @Resource
    private OpsAsPdCompensateDataMapper opsAsPdCompensateDataMapper;

    @Resource
    private DictCommonService dictCommonService;

    @Resource
    private OpsAsWmsInventoryArrivedNotInMapper opsAsWmsInventoryArrivedNotInMapper;

    @Resource
    private OpsAsOpsInventoryDataMapper opsAsOpsInventoryDataMapper;

    @Resource
    private OpsInventoryOpeningMapper opsInventoryOpeningMapper;

    @Resource
    private OpsAsFinanceBlanceDataMapper opsAsFinanceBlanceDataMapper;

    @Resource
    private OpsAsFinanceBlanceDataMapperSharedb opsAsFinanceBlanceDataMapperSharedb;

    @Resource
    private OpsAsPdCommonMapper opsAsPdCommonMapper;

    @Resource
    private OpsAsPdThreeReportMapper opsAsPdThreeReportMapper;

    @Resource
    private OpsCommonService opsCommonService;

    @Resource
    private OpsAsPdBatchMapper opsAsPdBatchMapper;

    @Resource
    private OpsAsPdThreeReportWareMapper opsAsPdThreeReportWareMapper;

    @Resource
    private JavaMailSenderImpl mailSender;

    @Resource
    private OpsAsPdBillDataMapper opsAsPdBillDataMapper;

    @Resource
    private OpsAsWmsInventoryDataMapperWithShareDB opsAsWmsInventoryDataMapperWithShareDB;

    @Resource
    private RedissonUtil redissonUtil;

    @Resource
    private CommonMapper commonMapper;

    private static final String PD_REDISSON_LOCK_KEY = "ops:pd:redisson:";

    @Override
    public ResultVo<PageInfo<OpsAsPdBatchYdDO>> listYdPdBatchList(YdPdSearchParams requestVO, int pageNumber, int pageSize) {
        if (requestVO == null) {
            return ResultVo.failure("入参不可为空");
        }

        LambdaQueryWrapper<OpsAsPdBatchYdDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .likeRight(StringUtils.isNotBlank(requestVO.getPdBatchNo()),OpsAsPdBatchYdDO::getPdBatchNo,requestVO.getPdBatchNo())
                .eq(StringUtils.isNotBlank(requestVO.getStatus()),OpsAsPdBatchYdDO::getStatus,requestVO.getStatus())
                .ge(StringUtils.isNotBlank(requestVO.getPdStartTime()),OpsAsPdBatchYdDO::getPdStartTime,requestVO.getPdStartTime())
                .le(StringUtils.isNotBlank(requestVO.getPdDataEndTime()),OpsAsPdBatchYdDO::getPdStartTime,requestVO.getPdDataEndTime());

        PageInfo<OpsAsPdBatchYdDO> pageInfo = PageHelper.startPage(pageNumber, pageSize).doSelectPageInfo(() -> opsAsPdBatchYdMapper.selectList(queryWrapper));
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            for (OpsAsPdBatchYdDO item : pageInfo.getList()) {
                if(item.getStatus() != null && item.getStatus() == 1) {
                    item.setStatusName("已执行");
                } else {
                    item.setStatusName("未执行");
                }
            }
        }
        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<PageInfo<OpsPdExecPlanManageDO>> listExecPlan(ExecPlanParamVO paramVO) {
        PageInfo<OpsPdExecPlanManageDO> pageInfo = PageHelper.startPage(paramVO.getPage().getPageNumber(), paramVO.getPage().getPageSize()).doSelectPageInfo(() -> opsPdExecPlanManageMapper.listExecPlan(paramVO));
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            for (OpsPdExecPlanManageDO item : pageInfo.getList()) {
                if(item.getExecFlag() != null && item.getExecFlag() == 1) {
                    item.setExecFlagName("已执行");
                } else {
                    item.setExecFlagName("未执行");
                }
            }
        }

        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<String> makeExecPlan() {
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("ops");
        }

        LocalDate startDate = LocalDate.now();

        // 存储生成的日期列表
        List<LocalDate> dateList = new ArrayList<>();

        // 生成12个月的日期
        for (int i = 0; i < 24; i++) {
            LocalDate nextMonth = startDate.plusMonths(i);
            LocalDate lastDayOfMonth = nextMonth.withDayOfMonth(nextMonth.lengthOfMonth());
            dateList.add(lastDayOfMonth);
        }

        // 格式化输出日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LambdaQueryWrapper<OpsPdExecPlanManageDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(OpsPdExecPlanManageDO::getExecDate, dateList);
        opsPdExecPlanManageMapper.delete(queryWrapper);

        for (LocalDate date : dateList) {
            OpsPdExecPlanManageDO opsPdExecPlanManageDO = new OpsPdExecPlanManageDO();
            opsPdExecPlanManageDO.setExecDate(date.format(formatter));
            opsPdExecPlanManageDO.setExecFlag(0);
            opsPdExecPlanManageDO.setCreateTime(new Date());
            opsPdExecPlanManageDO.setUpdateTime(new Date());
            opsPdExecPlanManageDO.setCreateUser(loginAuthDto.getUserNo());
            opsPdExecPlanManageDO.setUpdateUser(loginAuthDto.getUserNo());
            opsPdExecPlanManageMapper.insert(opsPdExecPlanManageDO);
        }
        return ResultVo.success("执行完毕");
    }

    @Override
    public ResultVo<String> updateOrAddPdExecPlan(OpsPdExecPlanManageDO opsPdExecPlanManageDO) {
        if(opsPdExecPlanManageDO == null) {
            return ResultVo.failure("入参不可为空");
        }

        if (opsPdExecPlanManageDO.getId() != null) {
            LambdaUpdateWrapper<OpsPdExecPlanManageDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(OpsPdExecPlanManageDO::getId,opsPdExecPlanManageDO.getId())
                    .set(OpsPdExecPlanManageDO::getExecFlag,opsPdExecPlanManageDO.getExecFlag())
                    .set(OpsPdExecPlanManageDO::getExecDate,opsPdExecPlanManageDO.getExecDate())
                    .set(OpsPdExecPlanManageDO::getUpdateTime,new Date())
                    .set(OpsPdExecPlanManageDO::getUpdateUser,opsPdExecPlanManageDO.getUpdateUser());
            opsPdExecPlanManageMapper.update(null,updateWrapper);
        } else {
            // 新增
            opsPdExecPlanManageDO.setCreateTime(new Date());
            opsPdExecPlanManageDO.setCreateUser(opsPdExecPlanManageDO.getUpdateUser());
            opsPdExecPlanManageDO.setUpdateTime(new Date());
            opsPdExecPlanManageDO.setUpdateUser(opsPdExecPlanManageDO.getUpdateUser());
            opsPdExecPlanManageMapper.insert(opsPdExecPlanManageDO);
        }
        return ResultVo.success("操作成功");
    }

    @Override
    public ResultVo<String> addMonthPd() {
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("ops");
        }
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 分别获取年、月、日
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue(); // 注意：月份从1开始，即1表示一月，12表示十二月
        String monthStr = "";
        String dayStr = "";
        if (month < 10) {
            monthStr = "0" + month;
        } else {
            monthStr = String.valueOf(month);
        }
        int day = currentDate.getDayOfMonth();
        if (day < 10) {
            dayStr = "0"+day;
        } else {
            dayStr = String.valueOf(day);
        }
        String pdBatchNo = "YPD"+year+monthStr+dayStr;
        LambdaQueryWrapper<OpsAsPdBatchYdDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsAsPdBatchYdDO::getPdBatchNo,pdBatchNo);
        if(opsAsPdBatchYdMapper.selectOne(queryWrapper) != null) {
            return ResultVo.failure("该月已生成");
        }

        LambdaUpdateWrapper<OpsAsPdBatchYdDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(OpsAsPdBatchYdDO::getStatus,0)
                .set(OpsAsPdBatchYdDO::getStatus,1);
        opsAsPdBatchYdMapper.update(null,updateWrapper);

        OpsAsPdBatchYdDO opsAsPdBatchYdDO = new OpsAsPdBatchYdDO();
        opsAsPdBatchYdDO.setPdBatchNo(pdBatchNo);
        opsAsPdBatchYdDO.setPdStartTime(new Date());
        opsAsPdBatchYdDO.setCreateTime(new Date());
        opsAsPdBatchYdDO.setCreateUser(loginAuthDto.getUserNo());
        opsAsPdBatchYdDO.setUpdateTime(new Date());
        opsAsPdBatchYdDO.setUpdateUser(loginAuthDto.getUserNo());
        opsAsPdBatchYdDO.setStatus(0);
        opsAsPdBatchYdMapper.insert(opsAsPdBatchYdDO);

        // 重置步骤状态
        opsAsPdBatchYdMapper.resetPdExecStep();

        updatePdExecStep(YdPdExecStepCodeEnum.newYdPd.getCode(),null,true);
        return ResultVo.success("新建成功");
    }

    @Override
    public ResultVo<String> wmsDataExtract() {
        String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.yck.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取已出库未推财务数据源失败.");
        }

        try {
            // 获取当前盘点批次号
            OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
            if (activePdBatch == null) {
                updatePdExecStep(YdPdExecStepCodeEnum.wmsDataExtract.getCode(),"没有获取到当次月度激活的盘点批次号",false);
                return ResultVo.failure("没有获取到当次月度激活的盘点批次号");
            }
            String batchNo = activePdBatch.getPdBatchNo();

            // 抽取之前清理掉同批次的数据
            opsAsPdBatchYdMapper.deleteOpsAsWmsInventoryData(batchNo);

            // 抽取wms库存数据
            opsAsPdBatchYdMapper.batchInsertOpsAsWmsInventory(batchNo);

            opsAsPdBatchYdMapper.deleteOpsAsWmsSubInventoryData(batchNo);

            opsAsPdBatchYdMapper.batchInsertOpsAsWmsSubInventoryData(batchNo);

            // 更新执行状态
            updatePdExecStep(YdPdExecStepCodeEnum.wmsDataExtract.getCode(),null,true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("wms数据抽取异常: {}",e.getMessage());
            updatePdExecStep(YdPdExecStepCodeEnum.wmsDataExtract.getCode(),e.getMessage(),false);
            throw new RuntimeException("wms数据抽取异常");
        }
        return ResultVo.success("wms数据抽取完毕");
    }

    @Override
    public ResultVo<String> wmsDhwrExtract() {
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

        OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
        if (activePdBatch == null ) {
            return ResultVo.failure("获取月度盘点批次号失败");
        }

        String pdBatchNo = activePdBatch.getPdBatchNo();
        LambdaQueryWrapper<OpsAsWmsInventoryDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(OpsAsWmsInventoryDataDO::getPdDataType,dataTypeList).eq(OpsAsWmsInventoryDataDO::getPdBatchNo,pdBatchNo);
        List<OpsAsWmsInventoryDataDO> opsAsWmsInventoryDataDOS = opsAsWmsInventoryDataMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(opsAsWmsInventoryDataDOS)) {
            return ResultVo.failure("没有到货未入库数据,请排查数据准备是否完整");
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
            notInDO.setCreateUser("yd_sys");
            notInDO.setUpdateUser("yd_sys");
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
        updatePdExecStep(YdPdExecStepCodeEnum.wmsDhwrExtract.getCode(),null,true);
        return ResultVo.success("导入完毕,成功共计"+okCount+"条;"+"失败:"+errCount+"条;"+errMsg.toString());
    }

    @Override
    public ResultVo<String> ypNotBal() {
        String batchNo = "";
        try {
            String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.yp.getCode());
            if(StringUtils.isBlank(dataSourceByCode)) {
                return ResultVo.failure("获取月度样品未结转数据源失败.");
            }

            // 获取当前盘点批次号
            OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
            if (activePdBatch == null) {
                updatePdExecStep(YdPdExecStepCodeEnum.yp.getCode(),"获取当次盘点批次号失败",false);
                return ResultVo.failure("获取当次盘点批次号失败");
            }
            batchNo = activePdBatch.getPdBatchNo();

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.yp.getCode();

            if(redissonUtil.isLock(key)) {
                return ResultVo.success("月度样品结转数据抽取中,请勿重复处理.");
            }

            redissonUtil.lock(key, 30*60);

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"1","2");

            String execDate = DateUtil.dateToDateTimeString(new Date());

            // 获取样品数据写入到补偿表
            List<OpsAsPdCompensateDataDO> opsAsPdCompensateDataDOS = opsAsPdCompensateDataMapper.sampleOrderImpCompensateData(execDate,dataSourceByCode);
            if (CollectionUtils.isEmpty(opsAsPdCompensateDataDOS)) {
                updatePdExecStep(YdPdExecStepCodeEnum.yp.getCode(),"暂无月度样品结转数据",true);
                return ResultVo.success("暂无月度样品结转数据.");
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
                opsAsPdCompensateDataDO.setCreateUser("yd_sys");
                opsAsPdCompensateDataDO.setUpdateUser("yd_sys");
                opsAsPdCompensateDataMapper.insert(opsAsPdCompensateDataDO);
            }
            updatePdExecStep(YdPdExecStepCodeEnum.yp.getCode(),null,true);

            LoginUserDTO loginAuthDto = null;
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto = new LoginUserDTO();
                loginAuthDto.setUserNo("yd_sys");
            }

            insertSysLog("月度样品结转数据抽取",loginAuthDto.getUserNo(),"execDate: "+execDate+" dataSource: "+dataSourceByCode);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("月度样品结转数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.yp.getCode(),e.getMessage(),false);
            throw new BusinessException("月度样品结转数据抽取失败 :"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.yp.getCode());
        }
        return ResultVo.success("月度样品结转数据抽取完毕");
    }

    @Override
    public ResultVo<String> yckwtcw() {

        String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.yck.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取已出库未推财务数据源失败.");
        }

        // 获取当前盘点批次号
        OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
        if (activePdBatch == null) {
            return ResultVo.failure("没有获取到当次月度激活的盘点批次号");
        }
        String batchNo = activePdBatch.getPdBatchNo();

        try {

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.yck.getCode();

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("月度已出库未推财务数据抽取中,请勿重复处理.");
            }

            redissonUtil.lock(key, 30*60);

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"2","2");

            List<OpsAsPdCompensateDataDO> opsAsPdCompensateDataDOS = opsAsPdCompensateDataMapper.yckNotPushCw(dataSourceByCode);
            if (CollectionUtils.isEmpty(opsAsPdCompensateDataDOS)) {
                updatePdExecStep(YdPdExecStepCodeEnum.yck.getCode(),"暂无已出库未推财务的数据",true);
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
                opsAsPdCompensateDataDO.setCreateUser("yd_sys");
                opsAsPdCompensateDataDO.setUpdateUser("yd_sys");
                opsAsPdCompensateDataMapper.insert(opsAsPdCompensateDataDO);
            }
            updatePdExecStep(YdPdExecStepCodeEnum.yck.getCode(),null,true);

            LoginUserDTO loginAuthDto = null;
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto = new LoginUserDTO();
                loginAuthDto.setUserNo("yd_sys");
            }
            insertSysLog("月度已出库未推财务的数据抽取",loginAuthDto.getUserNo(),"dataSource: "+dataSourceByCode);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("已出库未推财务的数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.yck.getCode(),e.getMessage(),false);
            throw new RuntimeException("已出库未推财务的数据抽取失败");
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.yck.getCode());
        }
        return ResultVo.success("已出库未推财务的数据抽取完毕");
    }

    @Override
    public ResultVo<String> cwbcData() {

        String batchNo = "";

        try {
            String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.cwbc.getCode());
            if(StringUtils.isBlank(dataSourceByCode)) {
                return ResultVo.failure("获取月度财务补偿数据源失败.");
            }
            // 获取当前盘点批次号
            OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
            if (activePdBatch == null) {
                return ResultVo.failure("获取月度当次盘点批次号失败");
            }
            batchNo = activePdBatch.getPdBatchNo();

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.cwbc.getCode();

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("月度财务补偿数据抽取中,请勿重复处理.");
            }

            redissonUtil.lock(key, 30*60);

            String year = batchNo.substring(3, 7);
            String month = batchNo.substring(7, 9);
            String pdDate = year+"-"+month+"-"+"01";
            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"3","3");

            List<OpsAsPdCompensateDataDO> listCwDataNotSureSales = opsAsPdCompensateDataMapper.cwData(PublicUtil.getNextMonthDate(pdDate),
                    year,month,dataSourceByCode);

            if (CollectionUtils.isEmpty(listCwDataNotSureSales)) {
                updatePdExecStep(YdPdExecStepCodeEnum.cwbc.getCode(),"暂无月度财务补偿的数据",true);
                return ResultVo.success("暂无月度财务补偿的数据.");
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
                opsAsPdCompensateDataDO.setCreateUser("yd_sys");
                opsAsPdCompensateDataDO.setUpdateUser("yd_sys");
                opsAsPdCompensateDataMapper.insert(opsAsPdCompensateDataDO);
            }
            updatePdExecStep(YdPdExecStepCodeEnum.cwbc.getCode(),null,true);

            LoginUserDTO loginAuthDto = null;
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto = new LoginUserDTO();
                loginAuthDto.setUserNo("yd_sys");
            }
            insertSysLog("月度财务补偿数据抽取",loginAuthDto.getUserNo(),PublicUtil.getNextMonthDate(pdDate)+"&"+year+"&"+month+"&"+dataSourceByCode);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("月度财务补偿数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.cwbc.getCode(),e.getMessage(),false);
            throw new RuntimeException("月度财务补偿数据抽取失败");
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.cwbc.getCode());
        }
        return ResultVo.success("月度财务补偿数据抽取完毕");
    }

    @Override
    public ResultVo<String> opsdbzt() {

        String batchNo = "";

        try {
            String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.db.getCode());
            if(StringUtils.isBlank(dataSourceByCode)) {
                return ResultVo.failure("获取OPS调拨在途数据源失败.");
            }
            // 获取当前盘点批次号
            OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
            if (activePdBatch == null) {
                return ResultVo.failure("获取当次盘点批次号失败");
            }
            batchNo =activePdBatch.getPdBatchNo();

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.db.getCode();

            if (redissonUtil.isLock(key)) {
                return ResultVo.success("OPS调拨在途数据抽取中,请勿重复处理.");
            }

            redissonUtil.lock(key, 30*60);

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"4","2");

            List<OpsAsPdCompensateDataDO> list = opsAsPdCompensateDataMapper.dbztWithOps(dataSourceByCode);

            if (CollectionUtils.isEmpty(list)) {
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
                opsAsPdCompensateDataDO.setCreateUser("yd_sys");
                opsAsPdCompensateDataDO.setUpdateUser("yd_sys");
                opsAsPdCompensateDataMapper.insert(opsAsPdCompensateDataDO);
            }
            updatePdExecStep(YdPdExecStepCodeEnum.db.getCode(),null, true);

            LoginUserDTO loginAuthDto = null;
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto = new LoginUserDTO();
                loginAuthDto.setUserNo("yd_sys");
            }
            insertSysLog("月度OPS调拨在途数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("OPS调拨在途数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.db.getCode(),e.getMessage(), false);
           throw new BusinessException("OPS调拨在途数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.db.getCode());
        }
        return ResultVo.success("OPS调拨在途数据抽取完毕");
    }

    @Override
    public ResultVo<String> opszzzt() {
        String batchNo = "";
        try {
            String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.zz.getCode());
            if(StringUtils.isBlank(dataSourceByCode)) {
                return ResultVo.failure("获取制造在途数据源失败.");
            }

            // 获取当前盘点批次号
            OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
            if (activePdBatch == null) {
                return ResultVo.failure("获取当次盘点批次号失败");
            }
            batchNo = activePdBatch.getPdBatchNo();

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.zz.getCode();

            if(redissonUtil.isLock(key)) {
                return ResultVo.success("制造在途数据正在抽取中");
            }

            redissonUtil.lock(key, 30*60);

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"5","2");

            List<OpsAsPdCompensateDataDO> list = opsAsPdCompensateDataMapper.zzztWithOps(dataSourceByCode);

            if (CollectionUtils.isEmpty(list)) {
                updatePdExecStep(YdPdExecStepCodeEnum.zz.getCode(),"暂无制造的数据", true);
                return ResultVo.success("暂无制造的数据.");
            }
            Date nowDate = new Date();
            OpsAsPdCompensateDataDO opsAsPdCompensateDataDO;
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
                opsAsPdCompensateDataDO.setCreateUser("yd_sys");
                opsAsPdCompensateDataDO.setUpdateUser("yd_sys");
                opsAsPdCompensateDataMapper.insert(opsAsPdCompensateDataDO);
            }
            updatePdExecStep(YdPdExecStepCodeEnum.zz.getCode(),null, true);

            LoginUserDTO loginAuthDto = null;
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto = new LoginUserDTO();
                loginAuthDto.setUserNo("yd_sys");
            }
            insertSysLog("月度制造在途数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("制造在途数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.zz.getCode(),e.getMessage(), false);
            throw new BusinessException("制造在途数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.zz.getCode());
        }
        return ResultVo.success("制造在途数据抽取完毕");
    }

    @Override
    public ResultVo<String> opsInventoryDataExtract() {

        String batchNo = "";
        try {
            String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.opsData.getCode());
            if(StringUtils.isBlank(dataSourceByCode)) {
                return ResultVo.failure("获取OPS库存数据源失败.");
            }
            // 获取当前盘点批次号
            OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
            if (activePdBatch == null) {
                return ResultVo.failure("获取当次盘点批次号失败");
            }
            batchNo = activePdBatch.getPdBatchNo();

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.opsData.getCode();

            if(redissonUtil.isLock(key)) {
                return ResultVo.success("OPS库存数据正在抽取中,请勿重复处理.");
            }

            redissonUtil.lock(key, 30*60);

            opsAsOpsInventoryDataMapper.delOpsAsOpsInventoryData("1",batchNo);

            List<OpsAsOpsInventoryDataDO> opsAsOpsInventoryDataDOS = opsAsOpsInventoryDataMapper.opsInventory(dataSourceByCode);
            if (CollectionUtils.isEmpty(opsAsOpsInventoryDataDOS)) {
                updatePdExecStep(YdPdExecStepCodeEnum.opsData.getCode(),"暂无ops库存数据抽取的数据", true);
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
                opsAsOpsInventoryDataDO.setCreateUser("yd_sys");
                opsAsOpsInventoryDataDO.setUpdateUser("yd_sys");
                opsAsOpsInventoryDataMapper.insert(opsAsOpsInventoryDataDO);
            }
            updatePdExecStep(YdPdExecStepCodeEnum.opsData.getCode(),null, true);

            LoginUserDTO loginAuthDto = null;
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto = new LoginUserDTO();
                loginAuthDto.setUserNo("yd_sys");
            }
            insertSysLog("月度ops库存数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("ops库存数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.opsData.getCode(),e.getMessage(), false);
            throw new BusinessException("ops库存数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.opsData.getCode());
        }
        return ResultVo.success("ops库存数据抽取完毕");
    }

    @Override
    public ResultVo<String> cwjc() {

        String batchNo = "";

        try {
            // 获取当前盘点批次号
            OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
            if (activePdBatch == null) {
                return ResultVo.failure("获取当次盘点批次号失败");
            }
            batchNo = activePdBatch.getPdBatchNo();

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.cwjc.getCode();

            if(redissonUtil.isLock(key)) {
                return ResultVo.success("财务结存数据正在抽取中,请勿重复处理.");
            }

            redissonUtil.lock(key, 30*60);

            /**
             * 删除该批次的sharedb库的财务结存数据
             */
            opsAsFinanceBlanceDataMapper.delCwjcDataSharedb(batchNo);

            /**
             * 删除该批次report财务结存数据
             */
            opsAsFinanceBlanceDataMapper.delCwjcData(batchNo);

            String substring = batchNo.trim().substring(3, 9);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

            YearMonth yearMonth = YearMonth.parse(substring, formatter);
            YearMonth previousMonth = yearMonth.minusMonths(1); // 往前推1个月

            String result = previousMonth.format(formatter);

            /**
             * 自动化财务结存
             */
            List<OpsAsFinanceBlanceDataDO> cwjcData = opsAsFinanceBlanceDataMapper.getCwjcData(result);
            if (CollectionUtils.isEmpty(cwjcData)) {
                updatePdExecStep(YdPdExecStepCodeEnum.cwjc.getCode(),"暂无自动化财务结存数据抽取的数据", true);
                return ResultVo.success("暂无自动化财务结存数据抽取的数据.");
            }

            List<OpsAsFinanceBlanceDataDO> gzCwjcData = opsAsFinanceBlanceDataMapper.getGzCwjcData(result);

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
                opsAsFinanceBlanceDataDO.setCreateUser("yd_sys");
                opsAsFinanceBlanceDataDO.setUpdateUser("yd_sys");
                // 1 自动化 2 广州
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

            updatePdExecStep(YdPdExecStepCodeEnum.cwjc.getCode(),null, true);

            LoginUserDTO loginAuthDto = null;
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto = new LoginUserDTO();
                loginAuthDto.setUserNo("yd_sys");
            }
            insertSysLog("月度财务结存数据抽取",loginAuthDto.getUserNo(),result);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("财务结存数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.cwjc.getCode(),e.getMessage(), false);
            throw new BusinessException("财务结存数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.cwjc.getCode());
        }
        return ResultVo.success("财务结存数据抽取完毕");
    }

    @Override
    public ResultVo<String> opsCGdhwrData() {

        String batchNo = "";
        try {
            String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.cgdhwr.getCode());
            if(StringUtils.isBlank(dataSourceByCode)) {
                return ResultVo.failure("获取ops采购到货未入数据源失败.");
            }
            // 获取当前盘点批次号
            OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
            if (activePdBatch == null) {
                return ResultVo.failure("获取当次盘点批次号失败");
            }
            batchNo = activePdBatch.getPdBatchNo();

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.cgdhwr.getCode();

            if(redissonUtil.isLock(key)) {
                return ResultVo.success("OPS采购到货未入数据正在抽取中,请勿重复处理.");
            }

            redissonUtil.lock(key, 30*60);

            opsAsOpsInventoryDataMapper.delOpsAsOpsInventoryData("2",batchNo);

            List<OpsAsOpsInventoryDataDO> opsAsOpsInventoryDataDOS = opsAsOpsInventoryDataMapper.opsArriveNotInWithCG(dataSourceByCode);
            if (CollectionUtils.isEmpty(opsAsOpsInventoryDataDOS)) {
                updatePdExecStep(YdPdExecStepCodeEnum.cgdhwr.getCode(),"暂无ops采购到货未入数据",true);
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
                opsAsOpsInventoryDataDO.setCreateUser("yd_sys");
                opsAsOpsInventoryDataDO.setUpdateUser("yd_sys");
                opsAsOpsInventoryDataMapper.insert(opsAsOpsInventoryDataDO);
            }
            updatePdExecStep(YdPdExecStepCodeEnum.cgdhwr.getCode(),null,true);

            LoginUserDTO loginAuthDto = null;
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto = new LoginUserDTO();
                loginAuthDto.setUserNo("yd_sys");
            }
            insertSysLog("月度ops采购到货未入数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("ops采购到货未入数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.cgdhwr.getCode(),e.getMessage(),false);
            throw new BusinessException("ops采购到货未入数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.cgdhwr.getCode());
        }
        return ResultVo.success("ops采购到货未入数据抽取完毕");
    }

    @Override
    public ResultVo<String> opsTHdhwrData() {
        String batchNo = "";
        try {
            String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.thdhwr.getCode());
            if(StringUtils.isBlank(dataSourceByCode)) {
                return ResultVo.failure("获取ops退货到货未入数据源失败.");
            }
            // 获取当前盘点批次号
            OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
            if (activePdBatch == null) {
                return ResultVo.failure("获取当次盘点批次号失败");
            }
            batchNo = activePdBatch.getPdBatchNo();

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.thdhwr.getCode();
            if(redissonUtil.isLock(key)) {
                return ResultVo.success("OPS退货到货未入数据正在抽取中,请勿重复处理.");
            }
            redissonUtil.lock(key, 30*60);

            opsAsOpsInventoryDataMapper.delOpsAsOpsInventoryData("4",batchNo);

            List<OpsAsOpsInventoryDataDO> opsAsOpsInventoryDataDOS = opsAsOpsInventoryDataMapper.opsArriveNotInWithTH(dataSourceByCode);
            if (CollectionUtils.isEmpty(opsAsOpsInventoryDataDOS)) {
                updatePdExecStep(YdPdExecStepCodeEnum.thdhwr.getCode(),"暂无ops采购到货未入数据", true);
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
                opsAsOpsInventoryDataDO.setCreateUser("yd_sys");
                opsAsOpsInventoryDataDO.setUpdateUser("yd_sys");
                opsAsOpsInventoryDataMapper.insert(opsAsOpsInventoryDataDO);
            }
            updatePdExecStep(YdPdExecStepCodeEnum.thdhwr.getCode(),null, true);

            LoginUserDTO loginAuthDto = null;
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto = new LoginUserDTO();
                loginAuthDto.setUserNo("yd_sys");
            }
            insertSysLog("月度OPS退货到货未入数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("ops退货到货未入数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.thdhwr.getCode(),e.getMessage(), false);
            throw new BusinessException("ops退货到货未入数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.thdhwr.getCode());
        }
        return ResultVo.success("ops退货到货未入数据抽取完毕");
    }

    @Override
    public ResultVo<String> opsDbdhwrData() {

        String batchNo = "";

        try {
            String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.dbdhwr.getCode());
            if(StringUtils.isBlank(dataSourceByCode)) {
                return ResultVo.failure("获取ops调拨到货未入数据源失败.");
            }
            // 获取当前盘点批次号
            OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
            if (activePdBatch == null) {
                return ResultVo.failure("获取当次盘点批次号失败");
            }
            batchNo = activePdBatch.getPdBatchNo();

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.dbdhwr.getCode();
            if(redissonUtil.isLock(key)) {
                return ResultVo.success("OPS调拨到货未入数据正在抽取中,请勿重复处理.");
            }
            redissonUtil.lock(key, 30*60);

            opsAsOpsInventoryDataMapper.delOpsAsOpsInventoryData("3",batchNo);

            List<OpsAsOpsInventoryDataDO> opsAsOpsInventoryDataDOS = opsAsOpsInventoryDataMapper.opsArriveNotInWithDB(dataSourceByCode);
            if (CollectionUtils.isEmpty(opsAsOpsInventoryDataDOS)) {
                updatePdExecStep(YdPdExecStepCodeEnum.dbdhwr.getCode(),"暂无ops调拨到货未入数据",true);
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
                opsAsOpsInventoryDataDO.setCreateUser("yd_sys");
                opsAsOpsInventoryDataDO.setUpdateUser("yd_sys");
                opsAsOpsInventoryDataMapper.insert(opsAsOpsInventoryDataDO);
            }
            updatePdExecStep(YdPdExecStepCodeEnum.dbdhwr.getCode(),null,true);

            LoginUserDTO loginAuthDto = null;
            try {
                loginAuthDto = SMCApp.getLoginAuthDto();
            } catch (Exception e) {
                loginAuthDto = new LoginUserDTO();
                loginAuthDto.setUserNo("yd_sys");
            }
            insertSysLog("月度ops调拨到货未入数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("ops调拨到货未入数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.dbdhwr.getCode(),e.getMessage(),false);
            throw new BusinessException("ops调拨到货未入数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.dbdhwr.getCode());
        }
        return ResultVo.success("ops调拨到货未入数据抽取完毕");
    }

    @Override
    public ResultVo<String> wmsdbztData() {
        String batchNo = "";
        try {
            String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.wmsbc.getCode());
            if(StringUtils.isBlank(dataSourceByCode)) {
                return ResultVo.failure("获取WMS补偿数据失败.");
            }
            // 获取当前盘点批次号
            OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
            if (activePdBatch == null) {
                return ResultVo.failure("获取当次盘点批次号失败");
            }
            batchNo = activePdBatch.getPdBatchNo();

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.wmsbc.getCode();
            if(redissonUtil.isLock(key)) {
                return ResultVo.success("WMS补偿数据正在抽取中,请勿重复处理.");
            }
            redissonUtil.lock(key, 30*60);

            opsAsPdCompensateDataMapper.clearOpsCompensateDataWithWMS(batchNo,"1");

            Page page = new Page();
            page.setPageNumber(1);
            page.setPageSize(6000);
            PageInfo<OpsAsPdCompensateDataDO> pageInfo;
            while (true) {
                String finalBatchNo = batchNo;
                pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                        .doSelectPageInfo(() -> {
                            opsAsPdCompensateDataMapper.selWmsBcData(finalBatchNo);
                        });
                if (CollectionUtils.isEmpty(pageInfo.getList())) {
                    updatePdExecStep(YdPdExecStepCodeEnum.wmsbc.getCode(),"暂无wms补偿数据",true);
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
                if (pageInfo.isIsLastPage()) {
                    break;
                }
                page.setPageNumber(page.getPageNumber() + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("WMS补偿数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.wmsbc.getCode(),e.getMessage(),false);
            throw new BusinessException("WMS补偿数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.wmsbc.getCode());
        }
        updatePdExecStep(YdPdExecStepCodeEnum.wmsbc.getCode(),null,true);

        LoginUserDTO loginAuthDto = null;
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto = new LoginUserDTO();
            loginAuthDto.setUserNo("yd_sys");
        }
        insertSysLog("月度WMS补偿数据抽取",loginAuthDto.getUserNo(),"");

        return ResultVo.success("WMS补偿数据抽取完毕");
    }

    @Override
    public ResultVo<String> opsTHzzData() {
        String batchNo = "";
        String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.opsReturnData.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取ops退货在途数据源失败.");
        }
        try {
            // 获取当前盘点批次号
            OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
            if (activePdBatch == null) {
                return ResultVo.failure("获取当次盘点批次号失败");
            }
            batchNo = activePdBatch.getPdBatchNo();

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.opsReturnData.getCode();
            if(redissonUtil.isLock(key)) {
                return ResultVo.success("OPS退货在途数据正在抽取中,请勿重复处理.");
            }
            redissonUtil.lock(key, 30*60);

            opsAsPdCompensateDataMapper.clearOpsReturnCompensateData(batchNo,"2","6");

            List<OpsAsPdCompensateDataDO> list = opsAsPdCompensateDataMapper.selOpsReturnData(dataSourceByCode);
            if (CollectionUtils.isEmpty(list)) {
                updatePdExecStep(YdPdExecStepCodeEnum.opsReturnData.getCode(),"暂无ops退货在途数据",true);
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
        } catch (Exception e) {
            e.printStackTrace();
            log.info("ops退货在途数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.opsReturnData.getCode(),e.getMessage(),false);
            throw new BusinessException("ops退货在途数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.opsReturnData.getCode());
        }
        updatePdExecStep(YdPdExecStepCodeEnum.opsReturnData.getCode(),null,true);

        LoginUserDTO loginAuthDto = null;
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto = new LoginUserDTO();
            loginAuthDto.setUserNo("yd_sys");
        }
        insertSysLog("月度ops退货在途数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        return ResultVo.success("ops退货在途数据抽取完毕");

    }

    @Override
    public ResultVo<String> wmsThzzData() {


        String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.wmsReturnData.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取wms退货在途数据源失败.");
        }
        // 获取当前盘点批次号
        OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
        if (activePdBatch == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = activePdBatch.getPdBatchNo();
        try {

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.wmsReturnData.getCode();

            if(redissonUtil.isLock(key)) {
                return ResultVo.success("WMS退货在途数据正在抽取中,请勿重复处理.");
            }
            redissonUtil.lock(key, 30*60);

            opsAsPdCompensateDataMapper.clearOpsReturnCompensateData(batchNo,"1","6");

            List<OpsAsPdCompensateDataDO> list = opsAsPdCompensateDataMapper.selWmsReturnData(batchNo);

            if (CollectionUtils.isEmpty(list)) {
                updatePdExecStep(YdPdExecStepCodeEnum.wmsReturnData.getCode(),"暂无wms退货在途数据",true);
                return ResultVo.success("暂无wms退货在途数据");
            }
            for (OpsAsPdCompensateDataDO item : list) {
                opsAsPdCompensateDataMapper.insert(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info("wms退货在途数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.wmsReturnData.getCode(),e.getMessage(),false);
            throw new BusinessException("wms退货在途数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.wmsReturnData.getCode());
        }
        updatePdExecStep(YdPdExecStepCodeEnum.wmsReturnData.getCode(),null,true);

        LoginUserDTO loginAuthDto = null;
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto = new LoginUserDTO();
            loginAuthDto.setUserNo("yd_sys");
        }
        insertSysLog("月度wms退货在途数据抽取",loginAuthDto.getUserNo(),"");

        return ResultVo.success("wms退货在途数据抽取完毕");
    }

    @Override
    public ResultVo<String> wmszzztData() {
        String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.wmszz.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取wms制造在途数据源失败.");
        }
        // 获取当前盘点批次号
        OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
        if (activePdBatch == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = activePdBatch.getPdBatchNo();
        try {
            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.wmszz.getCode();

            if(redissonUtil.isLock(key)) {
                return ResultVo.success("WMS制造在途数据正在抽取中,请勿重复处理.");
            }
            redissonUtil.lock(key, 30*60);

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"5","1");

            List<OpsAsPdCompensateDataDO> list = opsAsPdCompensateDataMapper.zzztWithWms(dataSourceByCode,batchNo);

            if (CollectionUtils.isEmpty(list)) {
                updatePdExecStep(YdPdExecStepCodeEnum.wmszz.getCode(),"暂无制造的数据",true);
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
        } catch (Exception e) {
            e.printStackTrace();
            log.info("wms制造在途数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.wmszz.getCode(),e.getMessage(),false);
            throw new BusinessException("wms制造在途数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.wmszz.getCode());
        }
        updatePdExecStep(YdPdExecStepCodeEnum.wmszz.getCode(),null,true);

        LoginUserDTO loginAuthDto = null;
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto = new LoginUserDTO();
            loginAuthDto.setUserNo("yd_sys");
        }
        insertSysLog("月度wms制造在途数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        return ResultVo.success("wms制造在途数据抽取完毕");
    }

    @Override
    public ResultVo<String> wmszhztData() {
        String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.wmszhzt.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取wms组换在途数据源失败.");
        }
        // 获取当前盘点批次号
        OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
        if (activePdBatch == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = activePdBatch.getPdBatchNo();
        try {
            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.wmszhzt.getCode();

            if(redissonUtil.isLock(key)) {
                return ResultVo.success("WMS组换在途数据正在抽取中,请勿重复处理.");
            }

            redissonUtil.lock(key, 30*60);

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"7","1");

            List<OpsAsPdCompensateDataDO> list = opsAsPdCompensateDataMapper.wmszhztData(dataSourceByCode,batchNo);

            if (CollectionUtils.isEmpty(list)) {
                updatePdExecStep(YdPdExecStepCodeEnum.wmszhzt.getCode(),"暂无wms组换在途的数据",true);
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
        } catch (Exception e) {
            e.printStackTrace();
            log.info("wms组换在途数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.wmszhzt.getCode(),e.getMessage(),false);
            throw new BusinessException("wms组换在途数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.wmszhzt.getCode());
        }
        updatePdExecStep(YdPdExecStepCodeEnum.wmszhzt.getCode(),null,true);

        LoginUserDTO loginAuthDto = null;
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto = new LoginUserDTO();
            loginAuthDto.setUserNo("yd_sys");
        }
        insertSysLog("月度wms组换在途数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        return ResultVo.success("wms组换在途数据抽取完毕");
    }

    @Override
    public ResultVo<String> opsZHDhwrData() {
        String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.opszhdhwr.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取ops组换到货未入数据源失败.");
        }
        // 获取当前盘点批次号
        OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
        if (activePdBatch == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = activePdBatch.getPdBatchNo();
        try {

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.opszhdhwr.getCode();

            if(redissonUtil.isLock(key)) {
                return ResultVo.success("OPS组换到货未入数据正在抽取中,请勿重复处理.");
            }

            redissonUtil.lock(key, 30*60);

            opsAsOpsInventoryDataMapper.delOpsAsOpsInventoryData("5",batchNo);

            List<OpsAsOpsInventoryDataDO> opsAsOpsInventoryDataDOS = opsAsOpsInventoryDataMapper.opszhdhwr(dataSourceByCode);
            if (CollectionUtils.isEmpty(opsAsOpsInventoryDataDOS)) {
                updatePdExecStep(YdPdExecStepCodeEnum.opszhdhwr.getCode(),"暂无ops组换到货未入数据",true);
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
        } catch (Exception e) {
            e.printStackTrace();
            log.info("ops组换到货未入数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.opszhdhwr.getCode(),e.getMessage(),false);
            throw new BusinessException("ops组换到货未入数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.opszhdhwr.getCode());
        }
        updatePdExecStep(YdPdExecStepCodeEnum.opszhdhwr.getCode(),null,true);

        LoginUserDTO loginAuthDto = null;
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto = new LoginUserDTO();
            loginAuthDto.setUserNo("yd_sys");
        }
        insertSysLog("月度ops组换到货未入数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        return ResultVo.success("ops组换到货未入数据抽取完毕");
    }

    @Override
    public ResultVo<String> opszhztData() {
        String dataSourceByCode = pdService.getDataSourceByCode(PdOtherDataImpEnum.opszhzt.getCode());
        if(StringUtils.isBlank(dataSourceByCode)) {
            return ResultVo.failure("获取OPS组换在途数据源失败.");
        }
        // 获取当前盘点批次号
        OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
        if (activePdBatch == null) {
            return ResultVo.failure("获取当次盘点批次号失败");
        }
        String batchNo = activePdBatch.getPdBatchNo();
        try {

            String key = PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.opszhzt.getCode();

            if(redissonUtil.isLock(key)) {
                return ResultVo.success("OPS组换在途数据正在抽取中,请勿重复处理.");
            }

            redissonUtil.lock(key, 30*60);

            opsAsPdCompensateDataMapper.clearOpsCompensateData(batchNo,"7","2");

            List<OpsAsPdCompensateDataDO> list = opsAsPdCompensateDataMapper.opszhzt(dataSourceByCode);

            if (CollectionUtils.isEmpty(list)) {
                updatePdExecStep(YdPdExecStepCodeEnum.opszhzt.getCode(),"暂无OPS组换在途的数据",true);
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
        } catch (Exception e) {
            e.printStackTrace();
            log.info("OPS组换在途数据抽取失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.opszhzt.getCode(),e.getMessage(),false);
            throw new BusinessException("OPS组换在途数据抽取失败:"+e.getMessage());
        } finally {
            redissonUtil.unlock(PD_REDISSON_LOCK_KEY+batchNo+"+"+PdOtherDataImpEnum.opszhzt.getCode());
        }
        updatePdExecStep(YdPdExecStepCodeEnum.opszhzt.getCode(),null,true);

        LoginUserDTO loginAuthDto = null;
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto = new LoginUserDTO();
            loginAuthDto.setUserNo("yd_sys");
        }
        insertSysLog("月度OPS调拨在途数据抽取",loginAuthDto.getUserNo(),dataSourceByCode);

        return ResultVo.success("OPS组换在途数据抽取完毕");
    }

    @Override
    public ResultVo<String> makePdReport() {
        long lstart = System.currentTimeMillis();
        log.info("开始生成盘点报表==>");

        OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
        if (activePdBatch == null) {
            return ResultVo.failure("获取当前盘点批次号失败");
        }

        String pd_batch_no = activePdBatch.getPdBatchNo();

        /**
         * 将ops_as_pd_bill_data结果表里的第一次录入型号,数量更新到第二次里
         */
        opsAsPdCommonMapper.updateOpsAsPdBillDataWithPdBatchNo(pd_batch_no);

        /**
         * 盘点数据生成时同时向库存期初管理表中写入盘点时点对应的库存期初数据。
         *1. 查询库存期初管理表是否存在历史年度盘点批次号
         *2. 如果不存在历史年度盘点批次号 则将实在库更进入库存期初管理表，
         *   如果存在历史年度盘点批次号，则将上期盘点实在库更新到当前年度库存期初数。
         */
        List<String> historyInventoryBatchNo = opsAsPdCommonMapper.getYdHistoryInventoryBatchNo();
        if (CollectionUtils.isEmpty(historyInventoryBatchNo)) {
            return ResultVo.failure("未同步上期盘点库存数据,无法写入盘点时点对应的库存期初数据。");
        }
        if(historyInventoryBatchNo.size()>1) {
            opsAsPdCommonMapper.updateIventoryOpenIngLastInitQty(historyInventoryBatchNo.get(1),historyInventoryBatchNo.get(0));
        } else {
            opsAsPdCommonMapper.ydUpdateInventoryinitialQuantity();
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
        List<String> lastPdBatchNo = opsAsPdBatchMapper.getLastYdPdBatchNo();

        if(CollectionUtils.isNotEmpty(lastPdBatchNo)) {
            String lastBatchNo = "";
            if (lastPdBatchNo.size() > 1) {
                opsAsPdCommonMapper.updateOpsAsPdThreeReportWare(lastPdBatchNo.get(0), lastPdBatchNo.get(1));
            } else {
                lastBatchNo = lastPdBatchNo.get(0);
                log.error("{},初次盘点,没有上期盘点期初数",lastBatchNo);
            }
        }

        // 更新盘点步骤为已执行
        updatePdExecStep(YdPdExecStepCodeEnum.outputPdReport.getCode(),null,true);

        // 更新盘点状态
        LambdaUpdateWrapper<OpsAsPdBatchYdDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(OpsAsPdBatchYdDO::getPdBatchNo,pd_batch_no)
                .set(OpsAsPdBatchYdDO::getStatus,1)
                .set(OpsAsPdBatchYdDO::getUpdateTime,new Date());
        opsAsPdBatchYdMapper.update(null,updateWrapper);
        return ResultVo.success("生成盘点报表完毕");
    }

    @Override
    public ResultVo<String> batchInsertPdBill() {
        try {
            OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
            if (activePdBatch == null) {
                return ResultVo.failure("获取当前盘点批次号失败");
            }
            String pd_batch_no = activePdBatch.getPdBatchNo();
            // 确认盘点形式:
            List<String> pdDataTypeList = new ArrayList<>();
            pdDataTypeList.add(PdDataTypeEnum.GD.getCode());
            pdDataTypeList.add(PdDataTypeEnum.JY.getCode());
            LambdaUpdateWrapper<OpsAsWmsInventoryDataDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .eq(OpsAsWmsInventoryDataDO::getPdBatchNo,pd_batch_no)
                    .in(OpsAsWmsInventoryDataDO::getPdDataType,pdDataTypeList)
                    .set(OpsAsWmsInventoryDataDO::getPdBillType, PdBillTypeEnum.SJBILL.getCode())
                    .set(OpsAsWmsInventoryDataDO::getUpdateTime,new Date())
                    .set(OpsAsWmsInventoryDataDO::getUpdateUser,"sys");
            opsAsWmsInventoryDataMapper.update(null,updateWrapper);

            // 将wms中间表过渡库位及实体货架库存数据写入到盘点票结果表 以及 委托在库(寄售库存)数据抽取到盘点票结果表
            List<String> dataTypeList = new ArrayList<>();
            dataTypeList.add(PdDataTypeEnum.GD.getCode());
            dataTypeList.add(PdDataTypeEnum.ST.getCode());
            dataTypeList.add(PdDataTypeEnum.WT.getCode());
            dataTypeList.add(PdDataTypeEnum.JY.getCode());
            LambdaQueryWrapper<OpsAsWmsInventoryDataDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            Page page = new Page();
            page.setPageNumber(1);
            page.setPageSize(12600);
            PageInfo<OpsAsWmsInventoryDataDO> pageInfo;
            while (true) {
                lambdaQueryWrapper.clear();
                lambdaQueryWrapper
                                .eq(OpsAsWmsInventoryDataDO::getPdBatchNo,pd_batch_no)
                                .in(OpsAsWmsInventoryDataDO::getPdDataType,dataTypeList).orderByAsc(OpsAsWmsInventoryDataDO::getId);
                pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                            .doSelectPageInfo(() -> {
                                opsAsWmsInventoryDataMapper.selectList(lambdaQueryWrapper);
                            });
                if (CollectionUtils.isEmpty(pageInfo.getList())) {
                    updatePdExecStep(YdPdExecStepCodeEnum.insertPdBill.getCode(),pd_batch_no+"无有效的wms盘点数据",true);
                    return ResultVo.success("无有效的wms盘点数据,请确认");
                }
                ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
                List<OpsAsPdBillDataDO> opsAsPdBillDataDOList = new CopyOnWriteArrayList<>();
                try {
                    for (OpsAsWmsInventoryDataDO item : pageInfo.getList()) {
                        fixedThreadPool.execute(() -> {
                            OpsAsPdBillDataDO billDataDO = conventOpsAsPdBillDataDO(item, pd_batch_no, new Date());
                            opsAsPdBillDataDOList.add(billDataDO);
                        });
                    }
                } finally {
                    fixedThreadPool.shutdown();
                    while (true) {
                        if (fixedThreadPool.isTerminated()) {
                            break;
                        }
                    }
                }
                List<OpsAsPdBillDataDO> tempList = opsAsPdBillDataDOList;
                ExecutorService fixedThreadPoolInsert = Executors.newFixedThreadPool(10);
                List<List<OpsAsPdBillDataDO>> arrayListArrayList = ListUtils.partition(tempList,80);
                try {
                    for (List<OpsAsPdBillDataDO> item : arrayListArrayList) {
                        fixedThreadPoolInsert.execute(() -> {
                            opsAsPdBillDataMapper.batchInsertPdPillData(item);
                        });
                    }
                } finally {
                    fixedThreadPoolInsert.shutdown();
                    while (true) {
                        if (fixedThreadPoolInsert.isTerminated()) {
                            break;
                        }
                    }
                }
                if (pageInfo.isIsLastPage()) {
                    break;
                }
                page.setPageNumber(page.getPageNumber() + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("写入月度盘点单据失败 {}",e.getMessage(),e);
            updatePdExecStep(YdPdExecStepCodeEnum.insertPdBill.getCode(),e.getMessage(),false);
            throw new BusinessException("写入月度盘点单据:"+e.getMessage());
        }

        // 更新盘点步骤为已执行
        updatePdExecStep(YdPdExecStepCodeEnum.insertPdBill.getCode(),null,true);

        return ResultVo.success("操作完毕");
    }

    public OpsAsPdBillDataDO conventOpsAsPdBillDataDO(OpsAsWmsInventoryDataDO item,String pdBatchNo, Date nowDate) {
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
        billDataDO.setPdInputort1("yd_sys");
        billDataDO.setPdInputTime1(nowDate);
        billDataDO.setPdDataType(item.getPdDataType());
//        if (StringUtils.isNotBlank(item.getPdDataType()) && item.getPdDataType().equals(PdDataTypeEnum.WT.getCode()))
//        {
//            billDataDO.setPdBillType(PdBillTypeEnum.XPBILL.getCode());
//        } else {
//            billDataDO.setPdBillType(item.getPdBillType());
//        }
        billDataDO.setPdBillType(PdBillTypeEnum.SJBILL.getCode());
        billDataDO.setOrderNo(item.getOrderNo());
        billDataDO.setPdWarehouseType(item.getWarehouseType());
        billDataDO.setDelFlag(false);
        billDataDO.setPdSort(item.getPdSort());
        billDataDO.setCreateUser("yd_sys");
        billDataDO.setCreateTime(nowDate);
        billDataDO.setUpdateTime(nowDate);
        billDataDO.setUpdateUser("yd_sys");
//        if (StringUtils.isNotBlank(billDataDO.getPdBillType()) &&
//                (billDataDO.getPdBillType().equals(PdBillTypeEnum.SJBILL.getCode()) || billDataDO.getPdBillType().equals(PdBillTypeEnum.QDBILL.getCode()))) {
//            billDataDO.setPdQty1(item.getBillQty());
//        }
        billDataDO.setPdQty1(item.getBillQty());
        billDataDO.setIsAss(StringUtils.isBlank(item.getIsAss()) ? "0":item.getIsAss());
        return billDataDO;
    }

    @Override
    public OpsAsPdBatchYdDO getActivePdBatch() {
        LambdaQueryWrapper<OpsAsPdBatchYdDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(OpsAsPdBatchYdDO::getCreateTime);
        return opsAsPdBatchYdMapper.selectList(queryWrapper).get(0);
    }

    @Override
    public ResultVo<String> updatePdExecStep(String code,String remark,boolean isok) {

        LambdaUpdateWrapper<OpsPdStepManageDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(OpsPdStepManageDO::getStepCode,code)
                .set(isok,OpsPdStepManageDO::getStatus,1)
                .set(OpsPdStepManageDO::getRemark,remark)
                // .set(isok,OpsPdStepManageDO::getExecTime,new Date())
                .set(OpsPdStepManageDO::getUpdateTime,new Date());
        opsPdStepManageMapper.update(null, updateWrapper);
        return ResultVo.success("更新成功");
    }

    @Override
    public ResultVo<String> uiExecYdPd(String code) {
        if (StringUtils.isBlank(code)) {
            return ResultVo.failure("入参不可为空");
        }
        YdPdExecStepCodeEnum belongEnum = YdPdExecStepCodeEnum.getBelongEnum(code);
        if (belongEnum == null) {
            return ResultVo.failure(code+"入参编码不在配置表里");
        }
        ResultVo<String> resultVo = null;
        try {
            switch (belongEnum) {
                case newYdPd:
                    resultVo = addMonthPd();
                    break;
                case wmsDataExtract:
                    resultVo = wmsDataExtract();
                    break;
                case wmsDhwrExtract:
                    resultVo = wmsDhwrExtract();
                    break;
                case yp:
                    resultVo = ypNotBal();
                    break;
                case yck:
                    resultVo = yckwtcw();
                    break;
                case cwbc:
                    resultVo = cwbcData();
                    break;
                case db:
                    resultVo = opsdbzt();
                    break;
                case zz:
                    resultVo = opszzzt();
                    break;
                case opsData:
                    resultVo = opsInventoryDataExtract();
                    break;
                case cwjc:
                    resultVo = cwjc();
                    break;
                case cgdhwr:
                    resultVo = opsCGdhwrData();
                    break;
                case thdhwr:
                    resultVo = opsTHdhwrData();
                    break;
                case dbdhwr:
                    resultVo = opsDbdhwrData();
                    break;
                case wmsbc:
                    resultVo = wmsdbztData();
                    break;
                case opsReturnData:
                    resultVo = opsTHzzData();
                    break;
                case wmsReturnData:
                    resultVo = wmsThzzData();
                    break;
                case wmszz:
                    resultVo = wmszzztData();
                    break;
                case wmszhzt:
                    resultVo = wmszhztData();
                    break;
                case opszhdhwr:
                    resultVo = opsZHDhwrData();
                    break;
                case opszhzt:
                    resultVo = opszhztData();
                    break;
                case outputPdReport:
                    resultVo = makePdReport();
                    break;
                case arrivedNotInInsertToPdBill:
                    resultVo = arrivedNotInInsertToPdBill();
                    break;
                case insertPdBill:
                    resultVo = batchInsertPdBill();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            return ResultVo.failure("处理异常:"+e.getMessage());
        }
        return resultVo;
    }

    @Override
    public ResultVo<List<OpsPdStepManageDO>> getExecStepList() {

        List<OpsPdStepManageDO> execStepList = opsAsPdBatchYdMapper.getExecStepList();
        if (CollectionUtils.isNotEmpty(execStepList)) {
            for (OpsPdStepManageDO item : execStepList) {
                if (item.getStatus() != null) {
                    item.setStatusName(item.getStatus() == 0 ? "未执行":"已执行");
                }
            }
        }
        return ResultVo.success(execStepList);
    }

    @Override
    public ResultVo<PageInfo<OpsAsPdThreeReportWareDO>> listPdThreeReportWare(OpsAsPdReportWareParams params) {
        if (params == null) {
            return ResultVo.failure("入参不可为空");
        }

        LambdaQueryWrapper<OpsAsPdThreeReportWareDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(CollectionUtils.isNotEmpty(params.getWarehouseList()),OpsAsPdThreeReportWareDO::getWarehouseCode,params.getWarehouseList())
                .likeRight(StringUtils.isNotBlank(params.getModelNo()),OpsAsPdThreeReportWareDO::getModelNo,params.getModelNo().trim())
                .eq(StringUtils.isNotBlank(params.getPdBatchNo()),OpsAsPdThreeReportWareDO::getPdBatchNo,params.getPdBatchNo());


        PageInfo<OpsAsPdThreeReportWareDO> pageInfo = PageHelper.startPage(params.getPage().getPageNumber(), params.getPage().getPageSize()).doSelectPageInfo(
                () ->  opsAsPdThreeReportWareMapper.selectList(queryWrapper)
        );

        if(CollectionUtils.isNotEmpty(pageInfo.getList())) {
            for (OpsAsPdThreeReportWareDO item : pageInfo.getList()) {
                if (StringUtils.isNotBlank(item.getWarehouseCode())) {
                    item.setWarehosueCodeName(opsCommonService.getWarehouseNameByCode(item.getWarehouseCode()));
                }
            }
        }
        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<List<OpsAsPdThreeReportWareDO>> getPdBatchNoListFromReportWare(String pdBatchNo) {

        QueryWrapper<OpsAsPdThreeReportWareDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(" top(20) pd_batch_no ");
        queryWrapper.lambda()
                .likeRight(StringUtils.isNotBlank(pdBatchNo),OpsAsPdThreeReportWareDO::getPdBatchNo,pdBatchNo)
                .likeRight(OpsAsPdThreeReportWareDO::getPdBatchNo,"YPD")
                .groupBy(OpsAsPdThreeReportWareDO::getPdBatchNo)
                .orderByDesc(OpsAsPdThreeReportWareDO::getPdBatchNo);
        List<OpsAsPdThreeReportWareDO> threeReportWareDOS = opsAsPdThreeReportWareMapper.selectList(queryWrapper);
        return ResultVo.success(threeReportWareDOS);
    }

    @Override
    public ResultVo<String> execYdPdStep() {

        return ResultVo.success("该作业已停用");

        // 是否到执行时间
//        List<OpsPdExecPlanManageDO> opsPdExecPlanManageDOS = opsPdExecPlanManageMapper.selectCountPlan();
//
//        if (CollectionUtils.isEmpty(opsPdExecPlanManageDOS)) {
//            return ResultVo.success("暂未到执行时间");
//        }
//        try {
//            // 新建月度盘点
//            addMonthPd();
//            // wms数据抽取
//            wmsDataExtract();
//            // wms到货未入数据抽取
//            wmsDhwrExtract();
//
//            // 确认数据(到货未入数据写入盘点票)
//            arrivedNotInInsertToPdBill();
//
//            // 确认盘点形式:
//            // 将wms中间表过渡库位及实体货架库存数据写入到盘点票结果表 以及 委托在库(寄售库存)数据抽取到盘点票结果表
//            batchInsertPdBill();
//            // 样品未结转
//            ypNotBal();
//            // 已出库未推财务
//            yckwtcw();
//            // 财务补偿数据
//            cwbcData();
//            // OPS调拨在途
//            opsdbzt();
//            // OPS制造在途
//            opszzzt();
//            // OPS库存数据
//            opsInventoryDataExtract();
//            // 财务结存数据
//            cwjc();
//            // OPS采购到货未入
//            opsCGdhwrData();
//            // OPS退货到货未入
//            opsTHdhwrData();
//            // OPS调拨到货未入
//            opsDbdhwrData();
//            // WMS调拨在途
//            wmsdbztData();
//            // OPS退货在途数据
//            opsTHzzData();
//            // WMS退货在途数据
//            wmsThzzData();
//            // WMS制造在途
//            wmszzztData();
//            // WMS组换在途
//            wmszhztData();
//
//            // wms组换到货未入
//
//            // OPS组换到货未入
//            opsZHDhwrData();
//            // OPS组换在途
//            opszhztData();
//            // 输出盘点报表
//            makePdReport();
//        } catch (Exception e) {
//            log.error("生成月度盘点报告失败", e);
//            throw new RuntimeException("生成月度盘点报告失败", e);
//        }


//        // 新建月度盘点
//        addMonthPd();
//        List<CompletableFuture<Void>> futures = new ArrayList<>();
//        futures.add(runAsyncWithExceptionHandling("wms数据抽取", this::wmsDataExtract));
//        futures.add(runAsyncWithExceptionHandling("wms到货未入数据抽取", this::wmsDhwrExtract));
//        // 确认盘点形式:
//        // 将wms中间表过渡库位及实体货架库存数据写入到盘点票结果表 以及 委托在库(寄售库存)数据抽取到盘点票结果表
//        futures.add(runAsyncWithExceptionHandling("写入月度盘点单据", this::batchInsertPdBill));
//        futures.add(runAsyncWithExceptionHandling("样品未结转", this::ypNotBal));
//        futures.add(runAsyncWithExceptionHandling("已出库未推财务", this::yckwtcw));
//        futures.add(runAsyncWithExceptionHandling("财务补偿数据", this::cwbcData));
//        futures.add(runAsyncWithExceptionHandling("OPS调拨在途", this::opsdbzt));
//        futures.add(runAsyncWithExceptionHandling("OPS制造在途", this::opszzzt));
//        futures.add(runAsyncWithExceptionHandling("OPS库存数据", this::opsInventoryDataExtract));
//        futures.add(runAsyncWithExceptionHandling("财务结存数据", this::cwjc));
//        futures.add(runAsyncWithExceptionHandling("OPS采购到货未入", this::opsCGdhwrData));
//        futures.add(runAsyncWithExceptionHandling("OPS退货到货未入", this::opsTHdhwrData));
//        futures.add(runAsyncWithExceptionHandling("OPS调拨到货未入", this::opsDbdhwrData));
//        futures.add(runAsyncWithExceptionHandling("WMS调拨在途", this::wmsdbztData));
//        futures.add(runAsyncWithExceptionHandling("OPS退货在途数据", this::opsTHzzData));
//        futures.add(runAsyncWithExceptionHandling("WMS退货在途数据", this::wmsThzzData));
//        futures.add(runAsyncWithExceptionHandling("WMS制造在途", this::wmszzztData));
//        futures.add(runAsyncWithExceptionHandling("WMS组换在途", this::wmszhztData));
//        futures.add(runAsyncWithExceptionHandling("OPS组换到货未入", this::opsZHDhwrData));
//        futures.add(runAsyncWithExceptionHandling("OPS组换在途", this::opszhztData));
//
//        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
//
//        combinedFuture.thenRun(() -> {
//            try {
//                // 输出盘点报表
//                makePdReport();
//            } catch (Exception e) {
//                throw new RuntimeException("生成月度盘点报告失败", e);
//            }
//        }).exceptionally(ex -> {
//            log.error("发生错误: " + ex.getMessage(), ex);
//            return null;
//        });
        // return ResultVo.success("执行完毕");
    }

    @Override
    public ResultVo<String> execYdExtractOpsDataPdStep() {
        // 是否到执行时间
        List<OpsPdExecPlanManageDO> opsPdExecPlanManageDOS = opsPdExecPlanManageMapper.selectCountPlan();

        if (CollectionUtils.isEmpty(opsPdExecPlanManageDOS)) {
            return ResultVo.success("暂未到执行时间");
        }
        try {
            // 新建月度盘点
            addMonthPd();
            // wms数据抽取
            // wmsDataExtract();
            // wms到货未入数据抽取
            // wmsDhwrExtract();

            // 确认数据(到货未入数据写入盘点票)
            // arrivedNotInInsertToPdBill();

            // 确认盘点形式:
            // 将wms中间表过渡库位及实体货架库存数据写入到盘点票结果表 以及 委托在库(寄售库存)数据抽取到盘点票结果表
            // batchInsertPdBill();
            // 样品未结转
            ypNotBal();
            // 已出库未推财务
            yckwtcw();
            // 财务补偿数据
            cwbcData();
            // OPS调拨在途
            opsdbzt();
            // OPS制造在途
            opszzzt();
            // OPS库存数据
            opsInventoryDataExtract();
            // 财务结存数据
            cwjc();
            // OPS采购到货未入
            opsCGdhwrData();
            // OPS退货到货未入
            opsTHdhwrData();
            // OPS调拨到货未入
            opsDbdhwrData();
            // WMS调拨在途
            //wmsdbztData();
            // OPS退货在途数据
            opsTHzzData();
            // WMS退货在途数据
            // wmsThzzData();
            // WMS制造在途
            // wmszzztData();
            // WMS组换在途
            // wmszhztData();

            // wms组换到货未入

            // OPS组换到货未入
            opsZHDhwrData();
            // OPS组换在途
            opszhztData();
            // 输出盘点报表
            // makePdReport();
        } catch (Exception e) {
            log.error("OPS侧盘点数据抽取失败", e);
            throw new RuntimeException("OPS侧盘点数据抽取失败", e);
        }

        return ResultVo.success("OPS侧盘点数据抽取执行完毕");
    }

    @Override
    public ResultVo<String> execYdExtractWmsDataPdStep() {
        // 是否到执行时间
        List<OpsPdExecPlanManageDO> opsPdExecPlanManageDOS = opsPdExecPlanManageMapper.selectCountPlan();

        if (CollectionUtils.isEmpty(opsPdExecPlanManageDOS)) {
            return ResultVo.success("暂未到执行时间");
        }

        // 获取当前盘点批次号
        OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
        if (activePdBatch == null) {
            return ResultVo.failure("没有获取到当次月度激活的盘点批次号");
        }
        String batchNo = activePdBatch.getPdBatchNo();

        /**
         * wms侧月度数据是否抽取完毕
         */
        int ops_wms_ypd_job = commonMapper.selCountExecYdOk("ops_wms_ypd_job", batchNo);
        if (ops_wms_ypd_job == 0) {
            return ResultVo.failure("wms数据未抽取完毕");
        }

        try {
            // 新建月度盘点
            // addMonthPd();
            // wms数据抽取
            wmsDataExtract();
            // wms到货未入数据抽取
            wmsDhwrExtract();

            // 确认数据(到货未入数据写入盘点票)
            arrivedNotInInsertToPdBill();

            // 确认盘点形式:
            // 将wms中间表过渡库位及实体货架库存数据写入到盘点票结果表 以及 委托在库(寄售库存)数据抽取到盘点票结果表
            batchInsertPdBill();
//            // 样品未结转
//            ypNotBal();
//            // 已出库未推财务
//            yckwtcw();
//            // 财务补偿数据
//            cwbcData();
//            // OPS调拨在途
//            opsdbzt();
//            // OPS制造在途
//            opszzzt();
//            // OPS库存数据
//            opsInventoryDataExtract();
//            // 财务结存数据
//            cwjc();
//            // OPS采购到货未入
//            opsCGdhwrData();
//            // OPS退货到货未入
//            opsTHdhwrData();
//            // OPS调拨到货未入
//            opsDbdhwrData();
            // WMS调拨在途
            wmsdbztData();
//            // OPS退货在途数据
//            opsTHzzData();
            // WMS退货在途数据
            wmsThzzData();
            // WMS制造在途
            wmszzztData();
            // WMS组换在途
            wmszhztData();

            // wms组换到货未入

            // OPS组换到货未入
//            opsZHDhwrData();
            // OPS组换在途
//            opszhztData();
            // 输出盘点报表
            makePdReport();

            /**
             * 更新执行计划为已执行
             */
            Integer id = opsPdExecPlanManageDOS.get(0).getId();
            LambdaUpdateWrapper<OpsPdExecPlanManageDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .eq(OpsPdExecPlanManageDO::getId,id)
                    .set(OpsPdExecPlanManageDO::getExecFlag,1)
                    .set(OpsPdExecPlanManageDO::getUpdateTime,new Date());
            opsPdExecPlanManageMapper.update(null,updateWrapper);


        } catch (Exception e) {
            log.error("生成月度盘点报告失败", e);
            throw new RuntimeException("生成月度盘点报告失败", e);
        }

        return ResultVo.success("执行完毕");
    }

    public ResultVo<String> arrivedNotInInsertToPdBill() {

        OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
        if (activePdBatch == null) {
            return ResultVo.failure("获取当前盘点批次号失败");
        }
        LambdaQueryWrapper<OpsAsWmsInventoryArrivedNotInDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsAsWmsInventoryArrivedNotInDO::getPdBatchNo,activePdBatch.getPdBatchNo());
        List<OpsAsWmsInventoryArrivedNotInDO> opsAsWmsInventoryArrivedNotInDOS = opsAsWmsInventoryArrivedNotInMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(opsAsWmsInventoryArrivedNotInDOS)) {
            return ResultVo.success("wms无到货未入数据数据");
        }

        List<OpsAsPdBillDataDO> billDataDOList = new ArrayList<>();

        for(OpsAsWmsInventoryArrivedNotInDO item : opsAsWmsInventoryArrivedNotInDOS) {
            OpsAsPdBillDataDO  billDataDO = new OpsAsPdBillDataDO();
            billDataDO.setPdBatchNo(activePdBatch.getPdBatchNo());
            billDataDO.setWarehouseCode(StringUtils.isBlank(item.getLWarehouseCode()) ? item.getWmsSysWarehouseCode() : item.getLWarehouseCode());
            if (StringUtils.isNotBlank(item.getLInvoiceNo()) && StringUtils.isNotBlank(item.getBarcode())) {
                billDataDO.setShelvesNo(item.getLInvoiceNo()+item.getBarcode());
            }
            billDataDO.setCaseNo(item.getCaseNo());
            billDataDO.setBarcode(item.getBarcode());
            // 订单号
            billDataDO.setOrderNo(item.getOrderNo());
            billDataDO.setModelNo1(item.getModelNo());
            billDataDO.setPdDataType(item.getPdDataType());
            billDataDO.setBillQty(item.getBillQty());
            billDataDO.setPdQty1(item.getBillQty());
            billDataDO.setPdInputort1("yd_sys");
            billDataDO.setWmsInvoiceNo(item.getWmsSysInvoiceNo());
            billDataDO.setInvoiceNo(item.getLInvoiceNo());
            billDataDO.setPdInputTime1(new Date());
            billDataDO.setPdBillType(PdBillTypeEnum.SJBILL.getCode()); // 数据票
            billDataDO.setCreateUser("yd_sys");
            billDataDO.setCreateTime(new Date());
            billDataDO.setUpdateTime(new Date());
            billDataDO.setUpdateUser("yd_sys");
            billDataDO.setDelFlag(false);
            billDataDO.setIsAss(StringUtils.isBlank(item.getIsAss()) ? "0":item.getIsAss());
            billDataDOList.add(billDataDO);
        }

        List<List<OpsAsPdBillDataDO>> partition = ListUtils.partition(billDataDOList, 80);
        for(List<OpsAsPdBillDataDO> list : partition) {
            opsAsPdBillDataMapper.batchInsertPdPillData(list);
        }

        return ResultVo.success("到货未入数据写入盘点票完毕");
    }

    @Override
    public ResultVo<String> execYdPdStepByPlan() {
        /**
         * 一天执行一次
         * 获取当前日期yyyy-MM-dd 去计划表里查看 是否存在未执行的
         * 存在的话 执行
         * 不存在 跳过忽略该天
         */
        String curdateStr = DateUtil.dateToString(DateUtil.getCurrentDate());

        LambdaQueryWrapper<OpsPdExecPlanManageDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsPdExecPlanManageDO::getExecDate,curdateStr);
        OpsPdExecPlanManageDO opsPdExecPlanManageDO = opsPdExecPlanManageMapper.selectOne(queryWrapper);
        if (opsPdExecPlanManageDO == null) {
            return ResultVo.success("未到执行时间点");
        }
        ResultVo<String> resultVo = execYdPdStep();

        if (!resultVo.isSuccess()) {
            return resultVo;
        }
        LambdaUpdateWrapper<OpsPdExecPlanManageDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(OpsPdExecPlanManageDO::getExecDate,curdateStr)
                .set(OpsPdExecPlanManageDO::getExecFlag,1)
                .set(OpsPdExecPlanManageDO::getUpdateTime,new Date());
        opsPdExecPlanManageMapper.update(null,updateWrapper);

        return ResultVo.success(curdateStr+"执行完毕");
    }

    @Override
    public ResultVo<PageInfo<OpsAsPdThreeReportWareDO>> listYdPdThreeReportWare(SearchReportWareParams params) {

        if (params == null) {
            return ResultVo.failure("入参不可为空");
        }

        PageInfo<OpsAsPdThreeReportWareDO> pageInfo = PageHelper.startPage(params.getPage().getPageNumber(), params.getPage().getPageSize()).doSelectPageInfo(
                                                                            () -> opsAsPdThreeReportWareMapper.listYDPdThreeReportWare(params));
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            for (OpsAsPdThreeReportWareDO item : pageInfo.getList()) {
                if (StringUtils.isNotBlank(item.getWarehouseCode())) {
                    item.setWarehosueCodeName(opsCommonService.getWarehouseNameByCode(item.getWarehouseCode()));
                }
            }
        }
        return ResultVo.success(pageInfo);

    }

    @Override
    public void exportYdPdReport(HttpServletResponse response, SearchReportWareParams params) {
        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(6000);
        PageInfo<OpsAsPdThreeReportWareDO> pageInfo;
        List<OpsAsPdThreeReportWareExportVO> list = new ArrayList<>();
        while (true) {
            pageInfo =  PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                    .doSelectPageInfo(() -> opsAsPdThreeReportWareMapper.listYDPdThreeReportWare(params));

            if (CollectionUtils.isEmpty(pageInfo.getList())) {
                return;
            }
            for (OpsAsPdThreeReportWareDO item : pageInfo.getList()) {
                if (StringUtils.isNotBlank(item.getWarehouseCode())) {
                    item.setWarehosueCodeName(opsCommonService.getWarehouseNameByCode(item.getWarehouseCode()));
                }
            }
            list.addAll(BeanCopyUtil.copyList(pageInfo.getList(),OpsAsPdThreeReportWareExportVO.class));
            if (pageInfo.isIsLastPage()) {
                break;
            }
            page.setPageNumber(page.getPageNumber() + 1);
        }
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        try {
            String fileName = URLEncoder.encode("月度报表", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            EasyExcel.write(outputStream)
                    .head(OpsAsPdThreeReportWareExportVO.class)
                    .sheet("Sheet1")
                    .doWrite(list);
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            Map<String,InputStream> map = new HashMap<>();
            map.put("月度报表.xlsx",inputStream);
            ResultVo<DataTypeVO> pd = dictCommonService.getDataTypeCodesInfo("9004", "PDREPORT");
            if (!pd.isSuccess()) {
                throw new BusinessException("导出月度报表时,获取邮箱失败");
            }
            EmailUtil.send(mailSender,pd.getData().getExtNote1(),pd.getData().getExtNote2(),pd.getData().getExtNote3(),"月度报表","月度报表导出完毕,请查看附件内容.",map);
            log.info("月度报表导出完毕,数据已发送至邮件..");
        } catch (IOException e) {
            response.reset();
            log.info("月度报表导出异常: 异常信息 {}",e.getMessage(),e);
            throw new RuntimeException("导出月度报表数据发生异常: "+e.getMessage());
        }
    }

    @Override
    public void exportYdPdStep(HttpServletResponse response, String methodCode) {
        if (StringUtils.isBlank(methodCode)) {
            return;
        }
        OpsAsPdBatchYdDO activePdBatch = getActivePdBatch();
        if (activePdBatch == null ) {
            return;
        }
        String pdBatchNo = activePdBatch.getPdBatchNo();

        if (methodCode.equals(YdPdExecStepCodeEnum.newYdPd.getCode())) {
            return;
        } else if (methodCode.equals(YdPdExecStepCodeEnum.wmsDataExtract.getCode())) {
            return;
        } else if (methodCode.equals(YdPdExecStepCodeEnum.wmsDhwrExtract.getCode())) {
            return;
        } else if (methodCode.equals(YdPdExecStepCodeEnum.insertPdBill.getCode())) {
            return;
        } else if (methodCode.equals(YdPdExecStepCodeEnum.yp.getCode())) {
            exportCompensateData(pdBatchNo,"1","2",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.yck.getCode())) {
            exportCompensateData(pdBatchNo,"2","2",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.cwbc.getCode())) {
            exportCompensateData(pdBatchNo,"3","3",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.db.getCode())) {
            exportCompensateData(pdBatchNo,"4","2",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.zz.getCode())) {
            exportCompensateData(pdBatchNo,"5","2",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.opsData.getCode())) {
            exportOpsInventoryData(pdBatchNo,"1",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.cwjc.getCode())) {
            exportOpsFinanceBlance(pdBatchNo,response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.cgdhwr.getCode())) {
            exportOpsInventoryData(pdBatchNo,"2",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.thdhwr.getCode())) {
            exportOpsInventoryData(pdBatchNo,"4",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.dbdhwr.getCode())) {
            exportOpsInventoryData(pdBatchNo,"3",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.wmsbc.getCode())) {
            exportCompensateData(pdBatchNo,"4","1",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.opsReturnData.getCode())) {
            exportCompensateData(pdBatchNo,"6","2",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.wmsReturnData.getCode())) {
            exportCompensateData(pdBatchNo,"6","1",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.wmszz.getCode())) {
            exportCompensateData(pdBatchNo,"5","1",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.wmszhzt.getCode())) {
            exportCompensateData(pdBatchNo,"7","1",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.opszhdhwr.getCode())) {
            exportOpsInventoryData(pdBatchNo,"5",response);
        } else if (methodCode.equals(YdPdExecStepCodeEnum.opszhzt.getCode())) {
            exportCompensateData(pdBatchNo,"7","2",response);
        }
    }

    @Override
    public void insertSysLog(String title, String optUser, String methodParams) {
        commonMapper.insertSysLog(title,optUser,methodParams);
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

    // 获取结存数据
    public List<OpsAsFinanceBlanceDataDO> getFinanceBlanceData(String pdBatchNo) {

        LambdaQueryWrapper<OpsAsFinanceBlanceDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OpsAsFinanceBlanceDataDO::getPdBatchNo,pdBatchNo);
        return opsAsFinanceBlanceDataMapper.selectList(queryWrapper);
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

    // 获取ops库存数据
    public List<OpsAsOpsInventoryDataDO> getOpsInventoryData(String pdBatchNo,String dataType) {

        LambdaQueryWrapper<OpsAsOpsInventoryDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OpsAsOpsInventoryDataDO::getPdBatchNo,pdBatchNo)
                .eq(StringUtils.isNotBlank(dataType), OpsAsOpsInventoryDataDO::getDataType,dataType);
        return opsAsOpsInventoryDataMapper.selectList(queryWrapper);
    }

    private CompletableFuture<Void> runAsyncWithExceptionHandling(String taskName, Runnable task) {
        return CompletableFuture.runAsync(() -> {
            try {
                task.run();
            } catch (Exception e) {
                throw new RuntimeException(taskName + " failed", e);
            }
        });
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

    // 获取补偿数据
    public List<OpsAsPdCompensateDataDO> getCompensateData(String pdBatchNo,String dataType,String dataSource) {

        LambdaQueryWrapper<OpsAsPdCompensateDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(OpsAsPdCompensateDataDO::getPdBatchNo,pdBatchNo)
                .eq(StringUtils.isNotBlank(dataType), OpsAsPdCompensateDataDO::getPdDataType,dataType)
                .eq(OpsAsPdCompensateDataDO::getPdDataSource,dataSource);
        return opsAsPdCompensateDataMapper.selectList(queryWrapper);
    }

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
}
