package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.binorder.BinTrialJobManageMapper;
import com.smc.smccloud.mapper.binorder.BinTrialSalesBranchConfigMapper;
import com.smc.smccloud.mapper.binorder.ModelExpFreqByJobMapper;
import com.smc.smccloud.model.OpsWarehouseSalesbranchConfigVO;
import com.smc.smccloud.model.binorder.*;
import com.smc.smccloud.service.BinTrialJobManageService;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.ModelExpFreqByJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wuweidong
 * @create 2023/6/2 11:51
 * @description @description --add by WuWeiDong 20230602  bug 10843  	自定义bin计算
 */

@Slf4j
@Service
@DS("opsreport")
public class BinTrialJobManageImpl implements BinTrialJobManageService {


    @Resource
    private BinTrialJobManageMapper binTrialJobManageMapper;
    @Resource
    private ModelExpFreqByJobMapper modelExpFreqByJobMapper;
    @Resource
    private BinTrialSalesBranchConfigMapper binTrialSalesBranchConfigMapper;

    @Resource
    private ModelExpFreqByJobService modelExpFreqByJobService;

    @Resource
    private PlatformTransactionManager transactionManager;

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    /**
     * 分页显示
     *
     * @param request
     * @return
     */
    @Override
    public ResultVo<PageInfo<BinTrialJobManageVO>> listBinTrialJobManageData(BinTrialJobRequestDTO request) {
        LambdaQueryWrapper<BinTrialJobManageDO> queryWrapper = this.setQueryWrapper(request);
        queryWrapper.orderByDesc(BinTrialJobManageDO::getId);

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<BinTrialJobManageDO> list = binTrialJobManageMapper.selectList(queryWrapper);
        PageInfo<BinTrialJobManageDO> pageInfo = PageInfo.of(list);
        return ResultVo.success(BeanCopyUtil.pageDto2Vo(pageInfo, BinTrialJobManageVO.class));

    }

    @Override
    public ResultVo<BinTrialJobManageDO> checkBinTrialJobStatus(Long jobId) {
        //验证状态
        if (Objects.isNull(jobId)) {
            return ResultVo.failure("输入任务编号。");
        }
        LambdaQueryWrapper<BinTrialJobManageDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BinTrialJobManageDO::getId, jobId);
        BinTrialJobManageDO jobManageDO = binTrialJobManageMapper.selectOne(queryWrapper);
        if (Objects.isNull(jobManageDO) || Objects.isNull(jobManageDO.getId())) {
            return ResultVo.failure("任务不存在。");
        } else if (jobManageDO.getStatus() >= 1 || jobManageDO.getIsDeleted() != 0) {
            return ResultVo.failure("已提交或取消，不可编辑。");
        }
        return ResultVo.success(jobManageDO, "可编辑。");

    }

    /**
     * 保存
     *
     * @param jobManageVO
     * @return
     */
    @Override
    public ResultVo<String> saveBinTrialJobManager(BinTrialJobManageVO jobManageVO) {
        try {
            if (PublicUtil.isEmpty(jobManageVO.jobName) ||
                    (PublicUtil.isEmpty(jobManageVO.getWarehouseCode()) && PublicUtil.isEmpty(jobManageVO.getWarehouseMaster()))) {
                return ResultVo.failure("任务名称，或计算仓库不能为空。");
            }
            //验证仓库
//            if (Objects.nonNull(jobManageVO.getWarehouseCode()) && jobManageVO.getWarehouseCode().length() > 0) {
//                String[] warehouseCodeArr = jobManageVO.getWarehouseCode().split(",");
//                for (String warehouseCode : warehouseCodeArr) {
//                    String warehouseName = OpsCommonServiceImpl.getWarehouseNameByCode(warehouseCode);
//                    if (Objects.isNull(warehouseName) || warehouseName.length() == 0) {
//                        return ResultVo.failure("仓库代码：" + warehouseCode + "不存在，请确认。");
//                    }
//                }
//            }
            //验证仓库是否重复
            if (Objects.nonNull(jobManageVO.getWarehouseCode()) && jobManageVO.getWarehouseCode().length() > 0
                    && Objects.nonNull(jobManageVO.getWarehouseMaster()) && jobManageVO.getWarehouseMaster().length() > 0) {
                List<String> warehouseCodes = Arrays.asList(jobManageVO.getWarehouseCode().split(","));
                List<String> warehouseMasters = Arrays.asList(jobManageVO.getWarehouseMaster().split(","));
                for (String code : warehouseCodes) {
                    if (warehouseMasters.contains(code)) {
                        return ResultVo.failure("第一仓库与第一物流中心有重复，请确认。");
                    }
                }
            }

            BinTrialJobManageDO jobManageDO = BeanCopyUtil.copy(jobManageVO, BinTrialJobManageDO.class);
            String userNo = SMCApp.getLoginAuthDtoForSysUser().getUserNo();
            jobManageDO.setUpdateTime(DateUtil.getNow());
            jobManageDO.setUpdateUser(userNo);
            if (Objects.isNull(jobManageDO.getId())) {
                ResultVo<String> stringResultVo = commonServiceFeignApi.generatorBillNo("33");
                if (!stringResultVo.isSuccess() || stringResultVo.getData() == null) {
                    throw Exceptions.OpsException("生成任务号失败。");
                }
                jobManageDO.setJobNo(stringResultVo.getData());
                jobManageDO.setCreateTime(DateUtil.getNow());
                jobManageDO.setCreateUser(userNo);

                TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
                transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                ResultVo<String> rtnVo = transactionTemplate.execute(transactionStatus -> {
                    try {
                        binTrialJobManageMapper.insert(jobManageDO);
                        this.insertBaseSalesbranchConfig(jobManageDO.getId());
                        //     binTrialJobManageMapper.insertBaseSalesbranchConfig(jobManageDO.getId(), userNo);
                        return ResultVo.success("保存存成功，任务编号：" + jobManageDO.getJobNo());
                    } catch (Exception ex) {
                        transactionStatus.setRollbackOnly(); // 手动回滚
                        log.error(Thread.currentThread().getName() + "->错误TransactionTemplate：" + ex);
                        return ResultVo.failure("保存错误：" + ex);
                    }
                });
                return rtnVo;

            } else {
                //验证状态
                ResultVo<BinTrialJobManageDO> resultVo = this.checkBinTrialJobStatus(jobManageVO.getId());
                if (!resultVo.isSuccess()) {
                    return ResultVo.failure(resultVo.getMessage());
                }
                binTrialJobManageMapper.updateById(jobManageDO);
                return ResultVo.success("更新成功。");
            }
        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            return ResultVo.failure("保存错误：" + ex);
        }

    }

    /**
     * 根据ID集合删除
     *
     * @param ids
     * @return
     */
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> deleteBinTrialJobManager(List<Long> ids) {
        if (PublicUtil.isEmpty(ids)) {
            return ResultVo.failure("输入删除的ID。");
        }
        try {
            //验证状态
//            for (Long id : ids) {
//                ResultVo<BinTrialJobManageDO> resultVo = this.checkBinTrialJobStatus(id);
//                if (!resultVo.isSuccess()) {
//                    return ResultVo.failure(resultVo.getMessage());
//                }
//            }

            LambdaQueryWrapper<BinTrialJobManageDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(BinTrialJobManageDO::getId, ids)
                    .eq(BinTrialJobManageDO::getStatus, 2)
                    .eq(BinTrialJobManageDO::getIsDeleted, 0);
            int count = binTrialJobManageMapper.selectCount(queryWrapper);
            if (count >= 1) {
                return ResultVo.failure("已计算完成，不可取消。");
            }

            LambdaUpdateWrapper<BinTrialJobManageDO> updateWrap = new LambdaUpdateWrapper<>();
            updateWrap.in(BinTrialJobManageDO::getId, ids);
            updateWrap.set(BinTrialJobManageDO::getIsDeleted, "1");
            updateWrap.set(BinTrialJobManageDO::getUpdateTime, DateUtil.getNow());
            updateWrap.set(BinTrialJobManageDO::getUpdateUser, SMCApp.getLoginAuthDtoForSysUser().getUserNo());
            binTrialJobManageMapper.update(null, updateWrap);
            return ResultVo.success("删除成功。");
        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            return ResultVo.failure("删除错误：" + ex);
        }
    }

    /**
     * 列出数据
     *
     * @param request
     * @return
     */
    @Override
    public ResultVo<List<BinTrialJobManageVO>> getBinTrialJobManageData(BinTrialJobRequestDTO request) {
        try {
            LambdaQueryWrapper<BinTrialJobManageDO> queryWrapper = this.setQueryWrapper(request);
            List<BinTrialJobManageDO> list = binTrialJobManageMapper.selectList(queryWrapper);
            return ResultVo.success(BeanCopyUtil.copyList(list, BinTrialJobManageVO.class));
        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            return ResultVo.failure("错误：" + ex);
        }
    }

    /**
     * 复制新的job
     *
     * @param jobId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> copyBinTrialJobManager(Long jobId) {
        try {
            LambdaQueryWrapper<BinTrialJobManageDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BinTrialJobManageDO::getId, jobId);
            BinTrialJobManageDO jobInfo = binTrialJobManageMapper.selectOne(queryWrapper);
            if (Objects.isNull(jobInfo)) {
                return ResultVo.failure("没有有查询到Id[" + jobId + "]的数据");
            } else {
                ResultVo<String> stringResultVo = commonServiceFeignApi.generatorBillNo("33");
                if (!stringResultVo.isSuccess() || stringResultVo.getData() == null) {
                    throw Exceptions.OpsException("生成任务号失败。");
                }
                String createUser = SMCApp.getLoginAuthDtoForSysUser().getUserNo();
                jobInfo.setId(null);
                jobInfo.setJobNo(stringResultVo.getData());
                jobInfo.setStatus(0);
                jobInfo.setExecuteEndTime(null);
                jobInfo.setExecuteStartTime(null);
                jobInfo.setIsDeleted(0);
                jobInfo.setResultTableName(null);
                jobInfo.setCreateTime(DateUtil.getNow());
                jobInfo.setCreateUser(createUser);
                jobInfo.setUpdateTime(null);
                jobInfo.setUpdateUser(null);
                TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
                transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                ResultVo<String> rtnVo = transactionTemplate.execute(transactionStatus -> {
                    try {
                        binTrialJobManageMapper.insert(jobInfo);
                        binTrialJobManageMapper.copybinTrialSalesbranchConfig(jobId, jobInfo.getId(), createUser);
                        return ResultVo.success("复制成功！新任务编号：" + stringResultVo.getData());
                    } catch (Exception ex) {
                        transactionStatus.setRollbackOnly(); // 手动回滚
                        log.error(Thread.currentThread().getName() + "->错误TransactionTemplate：" + ex);
                        return ResultVo.failure("保存错误：" + ex);
                    }
                });
                return rtnVo;
            }
        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            return ResultVo.failure("操作失败：" + ex);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> sumitBinTrialJob(List<Long> ids) {
        if (PublicUtil.isEmpty(ids)) {
            return ResultVo.failure("请选择ID。");
        }
        try {
            //验证状态
            LambdaQueryWrapper<BinTrialSalesBranchConfigDO> queryWrapper = new LambdaQueryWrapper<>();
            for (Long id : ids) {
                ResultVo<BinTrialJobManageDO> resultVo = this.checkBinTrialJobStatus(id);
                if (!resultVo.isSuccess()) {
                    return ResultVo.failure(resultVo.getMessage());
                }
                queryWrapper.clear();
                queryWrapper.eq(BinTrialSalesBranchConfigDO::getJobId, id);
                int count = binTrialSalesBranchConfigMapper.selectCount(queryWrapper);
                if (count == 0) {
                    return ResultVo.failure("还没有建立基本配置，不能提交。");
                }

            }
            String userNO = SMCApp.getLoginAuthDtoForSysUser().getUserNo();
            LambdaUpdateWrapper<BinTrialJobManageDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.in(BinTrialJobManageDO::getId, ids)
                    .eq(BinTrialJobManageDO::getStatus, 0)
                    .eq(BinTrialJobManageDO::getIsDeleted, 0)
                    .set(BinTrialJobManageDO::getStatus, 1)
                    ///   .set(BinTrialJobManageDO::getExecuteStartTime, DateUtil.getNow())
                    .set(BinTrialJobManageDO::getUpdateTime, DateUtil.getNow())
                    .set(BinTrialJobManageDO::getUpdateUser, userNO);
            binTrialJobManageMapper.update(null, updateWrapper);
            return ResultVo.success("提交完成！");
        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            return ResultVo.failure("操作失败：" + ex);
        }

    }

    /**
     * 执行Bin计算
     *
     * @param
     * @return
     */
    @Override
    public ResultVo<String> runBinTrialJob() {
        try {
            long startTimer = System.currentTimeMillis();
            Date currentDate = DateUtil.getCurrentDate();
            log.info("runBinTrialJob 开始计算 ");
            LambdaQueryWrapper<BinTrialJobManageDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.le(BinTrialJobManageDO::getPlanExecuteDate, DateUtil.getFormatDate(currentDate, "yyyy-MM-dd"))
                    .eq(BinTrialJobManageDO::getIsDeleted, 0)
                    .in(BinTrialJobManageDO::getStatus, Arrays.asList(1, 9));
            List<BinTrialJobManageDO> list = binTrialJobManageMapper.selectList(queryWrapper);
            if (PublicUtil.isEmpty(list)) {
                log.info("runBinTrialJob 计算完成(s),无数据。 ");
                return ResultVo.success("OK");
            }
            for (BinTrialJobManageDO jobManageDO : list) {
                this.runBinTrialJobByOne(jobManageDO);
            }
            log.info("runBinTrialJob 计算完成(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);
            return ResultVo.success("计算完成");
        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            return ResultVo.failure("操作失败：" + ex);
        }
    }


    private ResultVo<String> runBinTrialJobByOne(BinTrialJobManageDO jobManageDO) {
        LambdaUpdateWrapper<BinTrialJobManageDO> updateWrapper = new LambdaUpdateWrapper<>();
        String userNo = SMCApp.getLoginAuthDtoForSysUser().getUserNo();
        try {
            long startTimer = System.currentTimeMillis();
            log.info("runBinTrialJob 开始计算JobId:" + jobManageDO.getId());

            updateWrapper.eq(BinTrialJobManageDO::getId, jobManageDO.getId())
                    .set(BinTrialJobManageDO::getExecuteStartTime, DateUtil.getNow())
                    .set(BinTrialJobManageDO::getUpdateTime, DateUtil.getNow())
                    .set(BinTrialJobManageDO::getUpdateUser, userNo);
            binTrialJobManageMapper.update(null, updateWrapper);

            // 更新计算信息，并计算
            this.updateModelExpFreqByJob(jobManageDO);


            updateWrapper.clear();
            updateWrapper.eq(BinTrialJobManageDO::getId, jobManageDO.getId())
                    .in(BinTrialJobManageDO::getStatus, Arrays.asList(1, 9))
                    .set(BinTrialJobManageDO::getStatus, 2)
                    .set(BinTrialJobManageDO::getExecuteEndTime, DateUtil.getNow())
                    .set(BinTrialJobManageDO::getUpdateTime, DateUtil.getNow())
                    .set(BinTrialJobManageDO::getUpdateUser, userNo);
            binTrialJobManageMapper.update(null, updateWrapper);
            log.info("runBinTrialJob JobId:" + jobManageDO.getId() + "计算完成(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);
            return ResultVo.success("操作完成");
        } catch (Exception e) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + e);

            updateWrapper.clear();
            updateWrapper.eq(BinTrialJobManageDO::getId, jobManageDO.getId())
                    .in(BinTrialJobManageDO::getStatus, Arrays.asList(1, 9))
                    .set(BinTrialJobManageDO::getStatus, 9)
                    .set(BinTrialJobManageDO::getUpdateTime, DateUtil.getNow())
                    .set(BinTrialJobManageDO::getUpdateUser, userNo);
            binTrialJobManageMapper.update(null, updateWrapper);

            return ResultVo.failure("操作失败：" + e);
        }
    }

    private LambdaQueryWrapper<BinTrialJobManageDO> setQueryWrapper(BinTrialJobRequestDTO request) {
        LambdaQueryWrapper<BinTrialJobManageDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getId()), BinTrialJobManageDO::getId, request.getId())
                .eq(PublicUtil.isNotEmpty(request.getJobNo()), BinTrialJobManageDO::getJobNo, request.getJobNo())
                .like(PublicUtil.isNotEmpty(request.getJobName()), BinTrialJobManageDO::getJobName, request.getJobName());
        if (PublicUtil.isNotEmpty(request.getStatus())) {
            queryWrapper.eq(BinTrialJobManageDO::getStatus, request.getStatus())
                    .eq(BinTrialJobManageDO::getIsDeleted, 0);
        }
        if (PublicUtil.isNotEmpty(request.getWarehouseCode()) && request.getWarehouseCode().size() > 0) {
            int count = request.getWarehouseCode().size();
            if (count == 1) {
                queryWrapper.and(wrapper -> {
                    wrapper.like(BinTrialJobManageDO::getWarehouseCode, request.getWarehouseCode().get(0))
                            .or().like(BinTrialJobManageDO::getWarehouseMaster, request.getWarehouseCode().get(0));
                });
            } else {
                queryWrapper.and(wrapper -> {
                    for (int i = 0; i < count; i++) {
                        wrapper.like(BinTrialJobManageDO::getWarehouseCode, request.getWarehouseCode().get(i))
                                .or().like(BinTrialJobManageDO::getWarehouseMaster, request.getWarehouseCode().get(0));
                        if (i != count - 1) {
                            wrapper.or();
                        }
                    }
                });
            }
        }
        return queryWrapper;
    }

    private boolean insertBaseSalesbranchConfig(Long jobId) {
        try {

            ResultVo<List<OpsWarehouseSalesbranchConfigVO>> resultVoWarehouse = commonServiceFeignApi.getWarehouseSalesBranchConfigForPriority();
            ResultVo<List<OpsWarehouseSalesbranchConfigVO>> resultVoWarehouseMaster = commonServiceFeignApi.getWarehouseSalesBranchConfigForPriorityByMaster();
            if (!resultVoWarehouse.isSuccess() || !resultVoWarehouseMaster.isSuccess()) {
                log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + " 读取营业所仓库配置信息错误。");
                throw new BusinessException(" 读取营业所仓库配置信息错误。");
            }
            List<OpsWarehouseSalesbranchConfigVO> firstWarehouse = resultVoWarehouse.getData();
            List<OpsWarehouseSalesbranchConfigVO> firstWarehouseMaster = resultVoWarehouseMaster.getData();
            if (PublicUtil.isEmpty(firstWarehouse)) {
                log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + " 没有营业所仓库配置信息。");
                throw new BusinessException(" 没有营业所仓库配置信息。");
            }
            List<BinTrialSalesBranchConfigDO> configDOList = new ArrayList<>();
            for (OpsWarehouseSalesbranchConfigVO vo : firstWarehouse) {
                BinTrialSalesBranchConfigDO configDO = new BinTrialSalesBranchConfigDO();
                configDO.setJobId(jobId);
                configDO.setSalesBranchId(vo.getSalesBranchId());
                configDO.setWarehouseCode(vo.getWarehouseCode());
                configDO.setWarehouseCodeUpdate(vo.getWarehouseCode());
                List<OpsWarehouseSalesbranchConfigVO> masterConfigVO = firstWarehouseMaster.stream()
                        .filter(i -> i.getSalesBranchId().equalsIgnoreCase(vo.getSalesBranchId())).collect(Collectors.toList());
                String masterWarehouse;
                if (PublicUtil.isNotEmpty(masterConfigVO) && masterConfigVO.size() > 0) {
                    masterWarehouse = masterConfigVO.get(0).getWarehouseCode();
                } else {
                    masterWarehouse = commonServiceFeignApi.getWarehouseParentCode(vo.getWarehouseCode()).getData();
                }
                configDO.setWarehouseMaster(masterWarehouse);
                configDO.setWarehouseMasterUpdate(masterWarehouse);
                configDO.setCreateTime(DateUtil.getNow());
                configDO.setCreateUser(SMCApp.getLoginAuthDtoForSysUser().getUserNo());

                configDOList.add(configDO);
            }

            // 1. 追加默认配置
            binTrialSalesBranchConfigMapper.InsertByBatch(configDOList);

            // 2. 追加 默认配置没有的部门代码数据
            List<BinTrialSalesBranchConfigDO> addOtherBranchConfig = binTrialSalesBranchConfigMapper.getAddOtherBranchConfig(jobId);
            if (PublicUtil.isNotEmpty(addOtherBranchConfig) && addOtherBranchConfig.size() > 0) {
                configDOList.clear();
                for (BinTrialSalesBranchConfigDO configDO : addOtherBranchConfig) {
                    configDO.setJobId(jobId);
                    configDO.setWarehouseCodeUpdate(configDO.getWarehouseCode());
                    String masterWarehouse = commonServiceFeignApi.getWarehouseParentCode(configDO.getWarehouseCode()).getData();
                     if (PublicUtil.isEmpty(masterWarehouse)){
                         masterWarehouse=configDO.getWarehouseCode();
                     }
                    configDO.setWarehouseMaster(masterWarehouse);
                    configDO.setWarehouseMasterUpdate(masterWarehouse);
                    configDO.setCreateTime(DateUtil.getNow());
                    configDO.setCreateUser(SMCApp.getLoginAuthDtoForSysUser().getUserNo());
                    configDOList.add(configDO);
                }
                binTrialSalesBranchConfigMapper.InsertByBatch(configDOList);
            }
            return true;
        } catch (Exception e) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + e);
            throw new BusinessException("错误：" + e);
        }
    }

    private boolean updateModelExpFreqByJob(BinTrialJobManageDO jobManageDO) {
        try {
            ResultVo<String> resultVo = ResultVo.failure();

            LambdaQueryWrapper<ModelExpFreqByJobDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ModelExpFreqByJobDO::getJobId, jobManageDO.getId());
            modelExpFreqByJobMapper.delete(queryWrapper);
            // 1.计算第一仓库
            if (PublicUtil.isNotEmpty(jobManageDO.getWarehouseCode())) {
                resultVo = modelExpFreqByJobService.updateModelExpFreqByJob(jobManageDO.getId(), 0);
                if (!resultVo.isSuccess()) {
                    throw new BusinessException("计算第一仓库错误：" + resultVo.getMessage());
                }
            }
            //2. 计算物流中心
            if (PublicUtil.isNotEmpty(jobManageDO.getWarehouseMaster())) {
                resultVo = modelExpFreqByJobService.updateModelExpFreqByJob(jobManageDO.getId(), 1);
                if (!resultVo.isSuccess()) {
                    throw new BusinessException("计算第一物流中心错误：" + resultVo.getMessage());
                }
            }
            //3.更新型号级别等属性
           Map<String,Date> map= modelExpFreqByJobService.getLastMonthRange(36);
            modelExpFreqByJobService.calcModelExpFreqUpdModelInfoByJob(jobManageDO.getId(), map.get("endDate"));

            return true;
        } catch (Exception e) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + e);
            throw new BusinessException("错误：" + e);
        }
    }

}
