package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.binorder.BinTrialSalesBranchConfigMapper;
import com.smc.smccloud.model.*;
import com.smc.smccloud.model.binorder.*;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wuweidong
 * @create 2023/6/2 15:35
 * @description --add by WuWeiDong 20230602  bug 10843  	自定义bin计算
 */

@Slf4j
@Service
@DS("opsreport")
public class BinTrialSalesBranchConfigImpl implements BinTrialSalesBranchConfigService {
    @Value("${file.base}")
    private String serverPath;
    @Resource
    private BinTrialSalesBranchConfigMapper binTrialSalesBranchConfigMapper;

    @Resource
    private OpsCommonService opsCommonService;

    @Resource
    private DictCommonService dictCommonService;

    @Resource
    private PlatformTransactionManager transactionManager;

    @Resource
    private BinTrialJobManageService binTrialJobManageService;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Override
    public ResultVo<PageInfo<BinTrialSalesBranchConfigVO>> listBinTrialSalesBranchConfigData(BinTrialSalesBranchConfigRequestDTO request) {
        if (PublicUtil.isEmpty(request.getJobId())
                && PublicUtil.isEmpty(request.getSalesBranchIds())
                && request.getSalesBranchIds().length == 0
                && PublicUtil.isEmpty(request.getWarehouseCode())) {
            return ResultVo.failure("请输入查询条件。");
        }

        LambdaQueryWrapper<BinTrialSalesBranchConfigDO> queryWrapper = this.setqueryWrapper(request);
        queryWrapper.orderByDesc(BinTrialSalesBranchConfigDO::getId);
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<BinTrialSalesBranchConfigDO> list = binTrialSalesBranchConfigMapper.selectList(queryWrapper);

        PageInfo<BinTrialSalesBranchConfigDO> pageInfo = PageInfo.of(list);
        return ResultVo.success(BeanCopyUtil.pageDto2Vo(pageInfo, BinTrialSalesBranchConfigVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> saveBinTrialSalesBranchConfig(BinTrialSalesBranchConfigVO configVO) {
        try {
            if (Objects.isNull(configVO)) {
                return ResultVo.failure("请输入数据。");
            }
            BinTrialSalesBranchConfigDO binTrialSalesBranchConfig = BeanCopyUtil.copy(configVO, BinTrialSalesBranchConfigDO.class);
            String userNo = SMCApp.getLoginAuthDtoForSysUser().getUserNo();
            binTrialSalesBranchConfig.setUpdateTime(DateUtil.getNow());
            binTrialSalesBranchConfig.setUpdateUser(userNo);
            if (Objects.isNull(binTrialSalesBranchConfig.getId())) {
                binTrialSalesBranchConfig.setCreateTime(DateUtil.getNow());
                binTrialSalesBranchConfig.setCreateUser(userNo);
                binTrialSalesBranchConfigMapper.insert(binTrialSalesBranchConfig);
                return ResultVo.success("保存存成功。");

            } else {
                ResultVo<BinTrialJobManageDO> resultVo = binTrialJobManageService.checkBinTrialJobStatus(binTrialSalesBranchConfig.getJobId());
                if (!resultVo.isSuccess()) {
                    return ResultVo.failure(resultVo.getMessage());
                }
                binTrialSalesBranchConfigMapper.updateById(binTrialSalesBranchConfig);
                return ResultVo.success("更新成功。");
            }
        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            return ResultVo.failure("保存错误：" + ex);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> deleteBinTrialSalesBranchConfig(Long jobId, List<Long> ids) {
        if (PublicUtil.isEmpty(ids)) {
            return ResultVo.failure("输入删除的ID。");
        }
        try {
            ResultVo<BinTrialJobManageDO> resultVo = binTrialJobManageService.checkBinTrialJobStatus(jobId);
            if (!resultVo.isSuccess()) {
                return ResultVo.failure(resultVo.getMessage());
            }
            LambdaUpdateWrapper<BinTrialSalesBranchConfigDO> deleteWrap = new LambdaUpdateWrapper<>();
            deleteWrap.in(BinTrialSalesBranchConfigDO::getId, ids);
            binTrialSalesBranchConfigMapper.delete(deleteWrap);
            return ResultVo.success("删除成功。");
        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            return ResultVo.failure("删除错误：" + ex);
        }
    }

    @Override
    public ResultVo<String> createBinTrialSalesBranchConfig(Long jobId, String warehouseCode) {
        if (Objects.isNull(jobId) || Objects.isNull(warehouseCode)) {
            return ResultVo.failure("请输入删除的任务编号和仓库代码。");
        }
        try {
            List<String> warehouseCodes = dictCommonService.getWarehouseCodesByWarehouseCodeForMasterAndSub(warehouseCode).getData();
            if (PublicUtil.isEmpty(warehouseCodes)) {
                return ResultVo.failure("没有查到到该仓库对应信息，请确认。");
            }
            List<WarehouseSalesBranchDTO> warehouseSalesBranchDTOS = opsCommonService.getWarehouseSalesBranchList(warehouseCodes);
            if (PublicUtil.isEmpty(warehouseSalesBranchDTOS)) {
                return ResultVo.failure("没有查到到该仓库对应营业信息。");
            }
            ResultVo<String> rtnVo = ResultVo.failure("保存错误。");
            Date createTime = DateUtil.getNow();
            String userNo = SMCApp.getLoginAuthDtoForSysUser().getUserNo();

            LambdaQueryWrapper<BinTrialSalesBranchConfigDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BinTrialSalesBranchConfigDO::getJobId, jobId);
            int count = binTrialSalesBranchConfigMapper.selectCount(queryWrapper);
            if (count > 0) {
                ResultVo<BinTrialJobManageDO> resultVo = binTrialJobManageService.checkBinTrialJobStatus(jobId);
                if (!resultVo.isSuccess()) {
                    return ResultVo.failure(resultVo.getMessage());
                }
                binTrialSalesBranchConfigMapper.delete(queryWrapper);
            }

            List<BinTrialSalesBranchConfigDO> configDOList = new ArrayList<>();
            for (WarehouseSalesBranchDTO warehouseSalesBranch : warehouseSalesBranchDTOS) {
                BinTrialSalesBranchConfigDO configDO = new BinTrialSalesBranchConfigDO();
                configDO.setJobId(jobId);
                configDO.setWarehouseCode(warehouseSalesBranch.getWarehouseCode());
                configDO.setWarehouseCodeUpdate(warehouseSalesBranch.getWarehouseCode());
                configDO.setSalesBranchId(warehouseSalesBranch.getSalesBranchId());
                configDO.setCreateTime(createTime);
                configDO.setCreateUser(userNo);
                configDOList.add(configDO);
            }

            ResultVo<String> resultVo = this.InsertBranchConfigByBatch(configDOList);
            if (resultVo.isSuccess()) {
                return ResultVo.failure("追加完成。");
            } else {
                return resultVo;
            }
        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            return ResultVo.failure("保存错误：" + ex);
        }
    }

    @Override
    public ResultVo<String> addBinTrialSalesBranchConfig(Long jobId, String warehouseCode, List<String> branches) {
        if (PublicUtil.isEmpty(jobId) || PublicUtil.isEmpty(warehouseCode) || PublicUtil.isEmpty(branches)) {
            return ResultVo.failure("请输入有效数据。");
        }
        try {
            List<BinTrialSalesBranchConfigDO> configDOList = new ArrayList<>();
            int errCount = 0;
            int insertCount = 0;
            for (String branchId : branches) {
                LambdaQueryWrapper<BinTrialSalesBranchConfigDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(BinTrialSalesBranchConfigDO::getJobId, jobId)
                        .eq(BinTrialSalesBranchConfigDO::getSalesBranchId, branchId);
                int count = binTrialSalesBranchConfigMapper.selectCount(queryWrapper);
                if (count == 0) {
                    BinTrialSalesBranchConfigDO configDO = new BinTrialSalesBranchConfigDO();
                    configDO.setJobId(jobId);
                    configDO.setWarehouseCodeUpdate(warehouseCode);
                    configDO.setSalesBranchId(branchId);
                    configDO.setCreateTime(DateUtil.getNow());
                    configDO.setCreateUser(SMCApp.getLoginAuthDtoForSysUser().getUserNo());
                    OpsWarehouseSalesBranchConfigDO salesBranchDTO = opsCommonService.getSalesBranchListFirstWarehouse(branchId);
                    String warehouseCodeFirst = warehouseCode;
                    if (Objects.nonNull(salesBranchDTO) && Objects.nonNull(salesBranchDTO.getWarehouseCode())) {
                        warehouseCodeFirst = salesBranchDTO.getWarehouseCode();
                    }
                    configDO.setWarehouseCode(warehouseCodeFirst);
                    configDOList.add(configDO);
                } else {
                    errCount += 1;
                }

            }
            StringBuilder msg = new StringBuilder();
            ResultVo<String> resultVo = this.InsertBranchConfigByBatch(configDOList);
            if (resultVo.isSuccess()) {
                insertCount = configDOList.size();
                msg.append("追加完成。写入" + insertCount + "笔");
                if (errCount > 0) {
                    msg.append(errCount + "个营业所已存在。");
                }
                return ResultVo.success(msg.toString());
            } else {
                msg.append(resultVo.getMessage());
                if (errCount > 0) {
                    msg.append(errCount + "个营业所已存在。");
                }
                return ResultVo.failure(msg.toString());
            }

        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            return ResultVo.failure("保存错误：" + ex);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> restoreBinTrialSalesBranchConfig(Long jobId, List<Long> ids) {
        if (PublicUtil.isEmpty(jobId)) {
            return ResultVo.failure("请选择还原数据。");
        }
        try {
            ResultVo<BinTrialJobManageDO> resultVo = binTrialJobManageService.checkBinTrialJobStatus(jobId);
            if (!resultVo.isSuccess()) {
                return ResultVo.failure(resultVo.getMessage());
            }
            binTrialSalesBranchConfigMapper.restoreConfig(jobId, ids);
            return ResultVo.success("还原完成。");
        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
            return ResultVo.failure("保存错误：" + ex);
        }
    }

    /**
     * 用物流中心来查询，返回第一仓库数据
     *
     * @param jobId
     * @param masterCode
     * @return
     */
    @Override
    public ResultVo<List<String>> getWarehouseCodeByMasters(Long jobId, String masterCode) {
        if (PublicUtil.isEmpty(jobId) || PublicUtil.isEmpty(masterCode)) {
            return ResultVo.failure("请输入数据。");
        }
        LambdaQueryWrapper<BinTrialSalesBranchConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BinTrialSalesBranchConfigDO::getJobId, jobId)
                .select(BinTrialSalesBranchConfigDO::getWarehouseCodeUpdate);
        List<BinTrialSalesBranchConfigDO> list = binTrialSalesBranchConfigMapper.selectList(queryWrapper);
        if (list.size() == 0) {
            return ResultVo.success(Collections.emptyList());
        } else {
            List<String> warehouseCodes = new ArrayList<>();
            for (BinTrialSalesBranchConfigDO item : list) {
                String resultMaster = dictCommonService.getMasterWarehouseByCode(item.warehouseCodeUpdate);
                if (masterCode.equalsIgnoreCase(resultMaster)) {
                    if(!warehouseCodes.contains(item.warehouseCodeUpdate)) {
                        warehouseCodes.add(item.warehouseCodeUpdate);
                    }
                }
            }
            return ResultVo.success(warehouseCodes);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> importBinConfigureData(Long jobId, MultipartFile file) {
        try {
            ExcelHelper excel = new ExcelHelper(file.getInputStream());
            Sheet sheet = excel.getSheet();
            LoginUserDTO userDTO = SMCApp.getLoginAuthDto();

            ResultVo<List<OpsWarehouseSalesbranchConfigVO>>  resultFirstConfigVo = commonServiceFeignApi.getWarehouseSalesBranchConfigForPriority();
            if (!resultFirstConfigVo.isSuccess()){
                return ResultVo.failure("导入失败：" + resultFirstConfigVo.getMessage());
            }
           ResultVo<List<OpsWarehouseSalesbranchConfigVO>>  resultMasterConfigVo = commonServiceFeignApi.getWarehouseSalesBranchConfigForPriorityByMaster();
            if (!resultMasterConfigVo.isSuccess()){
                return ResultVo.failure("导入失败：" + resultMasterConfigVo.getMessage());
            }
            List<OpsWarehouseSalesbranchConfigVO> firstConfigVo=resultFirstConfigVo.getData();
            List<OpsWarehouseSalesbranchConfigVO> masterConfigVo=resultMasterConfigVo.getData();
            int cel;
            int lastRowNum = sheet.getLastRowNum();
            List<BinTrialSalesBranchConfigDO> configDOList = new ArrayList<>();
            Map<String, String> mapRepeatData = new HashMap();
            Map<String, Integer> beImport = new HashMap();
            final int batchCount = 900; //单次缓存的数据量
            Integer repeatCount = 0;
            Integer doCount = 0;
            Integer errCount = 0;
            for (int row = 1; row <= lastRowNum; row++) {
                Row rows = sheet.getRow(row);
                if (rows == null) {
                    break;
                }
                cel = 0;
                BinTrialSalesBranchConfigDO configDO = new BinTrialSalesBranchConfigDO();
                configDO.setJobId(jobId);
                String branchId = excel.getCellString(rows.getCell(cel++)).trim();
                String warehouseCode = excel.getCellString(rows.getCell(cel++)).trim();
                String warehouseMaster = excel.getCellString(rows.getCell(cel++)).trim();

                String warehouseCodeFirst = warehouseCode;
                String warehouseMasterFirst = warehouseMaster;

                List<OpsWarehouseSalesbranchConfigVO> configVOList=firstConfigVo.stream().filter(i->i.getSalesBranchId().equalsIgnoreCase(branchId)).collect(Collectors.toList());
                if(PublicUtil.isNotEmpty(configVOList)&& configVOList.size()>0)
                {
                    warehouseCodeFirst = configVOList.get(0).getWarehouseCode();
                }

                configVOList=masterConfigVo.stream().filter(i->i.getSalesBranchId().equalsIgnoreCase(branchId)).collect(Collectors.toList());
                if(PublicUtil.isNotEmpty(configVOList)&& configVOList.size()>0)
                {
                    warehouseMasterFirst = configVOList.get(0).getWarehouseCode();
                }
                configDO.setSalesBranchId(branchId);
                configDO.setWarehouseCode(warehouseCodeFirst);
                configDO.setWarehouseCodeUpdate(warehouseCode);
                configDO.setWarehouseMaster(warehouseMasterFirst);
                configDO.setWarehouseMasterUpdate(warehouseMaster);

                configDO.setCreateTime(DateUtil.getNow());
                configDO.setCreateUser(userDTO.getUserName());
                configDOList.add(configDO);
            }
            List<String> branchIds = configDOList.stream().map(BinTrialSalesBranchConfigDO::getSalesBranchId).distinct().collect(Collectors.toList());
            LambdaUpdateWrapper<BinTrialSalesBranchConfigDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(BinTrialSalesBranchConfigDO::getJobId,jobId)
                            .in(BinTrialSalesBranchConfigDO::getSalesBranchId, branchIds);
            binTrialSalesBranchConfigMapper.delete(updateWrapper);
            binTrialSalesBranchConfigMapper.InsertByBatch(configDOList);
            return ResultVo.success("导入完成，" + configDOList.size() + "笔。");

        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->导入失败：" + ex);
            return ResultVo.failure("导入失败：" + ex);
        }
    }

    @Override
    public void exportBinConfigureData(BinTrialSalesBranchConfigRequestDTO request, HttpServletResponse response) {
        if (PublicUtil.isEmpty(request.getJobId())
                && PublicUtil.isEmpty(request.getSalesBranchIds())
                && PublicUtil.isEmpty(request.getWarehouseCode())) {
            throw new BusinessException("请输入查询条件。 ");
        }

        String fileName = "BinConfigure" + DateUtil.getFormatDate(DateUtil.getNow(), "yyyyMMddHHmmss");
        String path = serverPath + fileName + ".csv";
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
        Map<Long, String> mapIdJobNo = new HashMap<>();
        BinTrialJobRequestDTO binTrialJobRequestDTO;
        ResultVo<List<BinTrialJobManageVO>> listResultVo = null;
        if (PublicUtil.isEmpty(request.jobId) && PublicUtil.isNotEmpty(request.jobNo)) {
            for (Map.Entry<Long, String> entry : mapIdJobNo.entrySet()) {
                if (request.jobNo.equalsIgnoreCase(entry.getValue())) {
                    request.jobId = entry.getKey();
                }
            }
            if (PublicUtil.isEmpty(request.jobId)) {
                binTrialJobRequestDTO = new BinTrialJobRequestDTO();
                binTrialJobRequestDTO.setJobNo(request.jobNo);
                listResultVo = binTrialJobManageService.getBinTrialJobManageData(binTrialJobRequestDTO);
                if (listResultVo.isSuccess()) {
                    if (PublicUtil.isNotEmpty(listResultVo.getData()) && listResultVo.getData().size() >= 1) {
                        request.jobId = listResultVo.getData().get(0).getId();
                        mapIdJobNo.put(request.jobId, request.jobNo);
                    } else {
                        throw new BusinessException(request.jobNo + "此任务编号不存在。");
                    }
                } else {
                    throw new BusinessException(listResultVo.getMessage());
                }

            }

        }


        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GB2312"))) {
            bw.write("任务编号,");
            bw.write("营业所代码,");
            bw.write("营业所名称,");
            bw.write("所属分公司,");
            bw.write("所属营业部,");
            bw.write("第一仓库代码,");
            bw.write("第一仓库,");
            bw.write("第一物流中心代码,");
            bw.write("第一物流中心");
            bw.newLine();

            LambdaQueryWrapper<BinTrialSalesBranchConfigDO> queryWrapper = this.setqueryWrapper(request);
            queryWrapper.orderByDesc(BinTrialSalesBranchConfigDO::getId);
            List<BinTrialSalesBranchConfigDO> list = binTrialSalesBranchConfigMapper.selectList(queryWrapper);

            if (PublicUtil.isEmpty(list)) {
                return;
            }
            List<BinTrialSalesBranchConfigVO> configVOS = BeanCopyUtil.copyList(list, BinTrialSalesBranchConfigVO.class);

            for (BinTrialSalesBranchConfigVO configVO : configVOS) {

                ResultVo<HROrganizationDO> hrOrganizationDOResultVo = opsCommonService.getHrOrganInfoByCode(configVO.getSalesBranchId());
                if (PublicUtil.isNotEmpty(hrOrganizationDOResultVo.getData())) {
                    String fullName = hrOrganizationDOResultVo.getData().getFullName();
                    String[] department;
                    if (Objects.nonNull(fullName)) {
                        // SMC投资管理有限公司_SMC自动化有限公司_北京分公司_北京营业部_京西所
                        department = fullName.split("_");
                        configVO.setBranchName(hrOrganizationDOResultVo.getData().getName());
                        if (department.length >= 4) {
                            configVO.setCompanyName(department[2]);
                            configVO.setAreaName(department[3]);
                        } else if (department.length == 3) {
                            configVO.setCompanyName(department[2]);
                            configVO.setAreaName(department[2]);
                        }
                    }
                }
                configVO.setWarehouseName(opsCommonService.getWarehouseNameByCode(configVO.getWarehouseCodeUpdate()));
                String masterCode = configVO.getWarehouseMasterUpdate();
                configVO.setMasterName(opsCommonService.getWarehouseNameByCode(masterCode));

                String jobNo = mapIdJobNo.get(configVO.getJobId());
                if (PublicUtil.isEmpty(jobNo)) {
                    binTrialJobRequestDTO = new BinTrialJobRequestDTO();
                    binTrialJobRequestDTO.setId(configVO.getJobId());
                    listResultVo = binTrialJobManageService.getBinTrialJobManageData(binTrialJobRequestDTO);
                    if (listResultVo.isSuccess()) {
                        if (PublicUtil.isNotEmpty(listResultVo.getData()) && listResultVo.getData().size() >= 1) {
                            jobNo = listResultVo.getData().get(0).getJobNo();
                            mapIdJobNo.put(configVO.getJobId(), jobNo);
                        }
                    }
                }
                if (PublicUtil.isEmpty(configVO.getSalesBranchId()))
                {
                    configVO.setSalesBranchId("");
                }
                if (PublicUtil.isEmpty(configVO.getBranchName()))
                {
                    configVO.setBranchName("");
                }
                if (PublicUtil.isEmpty(configVO.getCompanyName()))
                {
                    configVO.setCompanyName("");
                }
                if (PublicUtil.isEmpty(configVO.getAreaName()))
                {
                    configVO.setAreaName("");
                }
                bw.write(jobNo + ",");
                bw.write(configVO.getSalesBranchId() + ",");
                bw.write(configVO.getBranchName() + ",");
                bw.write(configVO.getCompanyName() + ",");
                bw.write(configVO.getAreaName() + ",");
                bw.write(configVO.getWarehouseCodeUpdate() + ",");
                bw.write(configVO.getWarehouseName() + ",");
                bw.write(configVO.getWarehouseMasterUpdate() + ",");
                bw.write(configVO.getMasterName() + ",");
                bw.newLine();
            }
            bw.flush();

            File zipFile = new File(path);
            InputStream is = new FileInputStream(zipFile);
            byte[] b = new byte[100];
            int len;
            try {
                while ((len = is.read(b)) > 0)
                    response.getOutputStream().write(b, 0, len);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            log.error("exportBinConfigureData failure, reason = {}", e.getMessage(), e);
        } finally {
            log.info("exportBinConfigureData 完成！");
        }
    }

    private ResultVo<String> InsertBranchConfigByBatch(List<BinTrialSalesBranchConfigDO> configDOList) {
        if (PublicUtil.isEmpty(configDOList)) {
            return ResultVo.failure("没有写入数据。");
        }
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);//纺
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        ResultVo<String> rtnVo = transactionTemplate.execute(transactionStatus -> {
            try {
                binTrialSalesBranchConfigMapper.InsertByBatch(configDOList);
                return ResultVo.success("建立完成", configDOList.get(0).getJobId().toString());
            } catch (Exception ex) {
                log.error(Thread.currentThread().getName() + "->错误TransactionTemplate：" + ex);
                return ResultVo.failure("保存错误：" + ex);
            }
        });
        return rtnVo;
    }

    private LambdaQueryWrapper<BinTrialSalesBranchConfigDO> setqueryWrapper(BinTrialSalesBranchConfigRequestDTO request) {
        LambdaQueryWrapper<BinTrialSalesBranchConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getJobId()), BinTrialSalesBranchConfigDO::getJobId, request.getJobId())
                .in(PublicUtil.isNotEmpty(request.getSalesBranchIds()) && request.getSalesBranchIds().length >= 1, BinTrialSalesBranchConfigDO::getSalesBranchId, request.getSalesBranchIds())
                .in(PublicUtil.isNotEmpty(request.getWarehouseCode()) && request.getWarehouseCode().length >= 1, BinTrialSalesBranchConfigDO::getWarehouseCodeUpdate, request.getWarehouseCode())
                .eq(PublicUtil.isNotEmpty(request.getWarehouseMaster()), BinTrialSalesBranchConfigDO::getWarehouseMasterUpdate, request.getWarehouseMaster());
        return queryWrapper;
    }
}
