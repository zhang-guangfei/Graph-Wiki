package com.smc.smccloud.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.until.DateUtil;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.InventoryTypeEnum;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.prestock.PreorderAccountConfigMapper;
import com.smc.smccloud.model.prestock.*;
import com.smc.smccloud.service.OpsCommonService;
import com.smc.smccloud.service.PreOrderAccountConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author wuweidong
 * @create 2024/1/3 13:53
 * @description   bug 12759   WTSR2023000683-顾客在库&战略在库决算功能
 */
@Slf4j
@Service
public class PreOrderAccountConfigServiceImpl implements PreOrderAccountConfigService {

    private final String REDIS_KEY_OPS_PRESTOCK_DELAYCONFIG = CommonConstants.OPS_PRESTOCK + "delayconfig";
    @Value("${file.base}")
    private String serverPath;
    @Resource
    private RedisManager redisManager;

    @Resource
    private PreorderAccountConfigMapper preorderAccountConfigMapper;

    @Resource
    private PreOrderAccountConfigService preorderAccountConfigService;
    @Resource
    private OpsCommonService opsCommonService;

    @Override
    public ResultVo<PageInfo<PreOrderAccountConfigDO>> listPreOrderAccountConfig(PreOrderAccountRequest request) {

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<PreOrderAccountConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(request.getInventoryTypeCode()), PreOrderAccountConfigDO::getInventoryTypeCode, request.getInventoryTypeCode())
                .eq(StringUtils.isNotBlank(request.getCustomerNo()), PreOrderAccountConfigDO::getCustomerNo, request.getCustomerNo())
                .eq(StringUtils.isNotBlank(request.getGroupCustomerNo()), PreOrderAccountConfigDO::getGroupCustomerNo, request.getGroupCustomerNo())
                .eq(StringUtils.isNotBlank(request.getPpl()), PreOrderAccountConfigDO::getPpl, request.getPpl())
                .eq(StringUtils.isNotBlank(request.getProjectCode()), PreOrderAccountConfigDO::getProjectCode, request.getProjectCode())
                .eq(PreOrderAccountConfigDO::getDelFlag, false);

        PageInfo<PreOrderAccountConfigDO> pageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize()).doSelectPageInfo(
                                                          () -> preorderAccountConfigMapper.selectList(queryWrapper));

        if(CollectionUtils.isNotEmpty(pageInfo.getList())) {
            for (PreOrderAccountConfigDO item : pageInfo.getList()) {
                if(StringUtils.isNotBlank(item.getInventoryTypeCode())) {
                    item.setInventoryTypeCodeName(InventoryTypeEnum.getName(item.getInventoryTypeCode()));
                }
                if (StringUtils.isNotBlank(item.getCustomerNo())) {
                    item.setCustomerName(opsCommonService.getCustomerNameByNo(item.getCustomerNo()));
                }
                if (item.getIsDelay() != null) {
                    item.setIsDelayStr(item.getIsDelay() ? "是" : "否");
                }
            }
        }
        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<List<PreOrderAccountConfigDTO>> getPreOrderAccountConfig(PreOrderAccountRequest request) {
        if (Objects.isNull(request.getInventoryTypeCode())) {
            return ResultVo.failure("请输入仓库类型。");

        }
        if (StringUtils.isEmpty(request.getCustomerNo())) {
            request.setCustomerNo("");
        }
        if (StringUtils.isEmpty(request.getPpl())) {
            request.setPpl("");
        }
        if (StringUtils.isEmpty(request.getProjectCode())) {
            request.setProjectCode("");
        }
        if (StringUtils.isEmpty(request.getGroupCustomerNo())) {
            request.setGroupCustomerNo("");
        }

        String key = String.join("~", request.getInventoryTypeCode()
                , request.getCustomerNo(), request.getPpl()
                , PublicUtil.objToString(request.getProjectCode()), PublicUtil.objToString(request.getGroupCustomerNo()));

        List<PreOrderAccountConfigDTO> configDTOList = new ArrayList<>();

        Object obj = redisManager.hGet(REDIS_KEY_OPS_PRESTOCK_DELAYCONFIG, key);

        if (ObjectUtils.isEmpty(obj)) {
            LambdaQueryWrapper<PreOrderAccountConfigDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PreOrderAccountConfigDO::getInventoryTypeCode, request.getInventoryTypeCode())
                    .eq(PreOrderAccountConfigDO::getCustomerNo, request.getCustomerNo())
                    .eq(PreOrderAccountConfigDO::getPpl, request.getPpl())
                    .eq(PreOrderAccountConfigDO::getProjectCode, request.getProjectCode())
                    .eq(PreOrderAccountConfigDO::getGroupCustomerNo, request.getGroupCustomerNo());

            List<PreOrderAccountConfigDO> configDOList = preorderAccountConfigMapper.selectList(queryWrapper);
            configDTOList = BeanCopyUtil.copyList(configDOList, PreOrderAccountConfigDTO.class);
            redisManager.hPut(REDIS_KEY_OPS_PRESTOCK_DELAYCONFIG, key, JSONObject.toJSONString(configDTOList));

        } else {
            configDTOList = JSONObject.parseArray(obj.toString(), PreOrderAccountConfigDTO.class);
        }
        return ResultVo.success(configDTOList);
    }

    @Override
    public ResultVo<String> writeAndUpdatePreOrderAccountConfig(PreOrderAccountConfigDO configDO) {
        try {
            if (ObjectUtils.isNotEmpty(configDO.getId())) {
                this.UpdateAccountConfigById(configDO);
            } else {
                Integer id = this.getAccountConfigId(configDO);
                if (ObjectUtils.isEmpty(id)) {
                    List<PreOrderAccountConfigDO> configDOList = new ArrayList<>();
                    String userNo = SMCApp.getLoginAuthDtoForSysUser().getUserNo();
                    Date createTime = DateUtil.getNow();
                    configDO.setCreateTime(createTime);
                    configDO.setCreator(userNo);
                    configDO.setModifyTime(createTime);
                    configDO.setModifier(userNo);
                    configDO.setDelFlag(false);
                    configDOList.add(configDO);
                    preorderAccountConfigService.writeAccountConfigByBath(configDOList);

                } else {
                    configDO.setId(id);
                    configDO.setDelFlag(false);
                    this.UpdateAccountConfigById(configDO);
                }
            }
            return ResultVo.success("执行成功！");
        } catch (Exception e) {
            log.error("执行失败：{}", e);
            return ResultVo.failure("执行失败：" + e.getMessage());
        }
    }

    @Override
    public ResultVo<String> importPreOrderAccountConfig(MultipartFile file) {
        try {
            ExcelHelper excel = new ExcelHelper(file.getInputStream());
            Sheet sheet = excel.getSheet();
            String userNo = SMCApp.getLoginAuthDtoForSysUser().getUserNo();
            Date createTime = DateUtil.getNow();
            int cel;
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum > 10000) {
                return ResultVo.failure("导入数据超过10000笔，请确认导入文档是否正确。");
            }
            for (int row = 1; row <= lastRowNum; row++) {
                Row rows = sheet.getRow(row);
                if (rows == null) {
                    break;
                }
                cel = 0;
                PreOrderAccountConfigDO configDO = new PreOrderAccountConfigDO();
                String priority = excel.getCellString(rows.getCell(cel++)).trim();
                if(StringUtils.isBlank(priority)) {
                    continue;
                }
                Integer preorityId = 1;
                if (StringUtils.isNotBlank(priority)) {
                    preorityId = Integer.parseInt(priority);
                }
                configDO.setPriority(preorityId);
                configDO.setInventoryTypeCode(excel.getCellString(rows.getCell(cel++)).trim());
                configDO.setCustomerNo(excel.getCellString(rows.getCell(cel++)).trim());
                configDO.setGroupCustomerNo(excel.getCellString(rows.getCell(cel++)).trim());
                configDO.setPpl(excel.getCellString(rows.getCell(cel++)).trim());
                configDO.setProjectCode(excel.getCellString(rows.getCell(cel++)).trim());
                String delayFlag = excel.getCellString(rows.getCell(cel++)).trim();
                configDO.setIsDelay(delayFlag.equalsIgnoreCase("是"));
                String maxDay = excel.getCellString(rows.getCell(cel++)).trim();
                configDO.setDelayMaxDay(Integer.parseInt(maxDay));
                configDO.setCreateTime(createTime);
                configDO.setCreator(userNo);
                configDO.setModifyTime(createTime);
                configDO.setModifier(userNo);
                configDO.setDelFlag(false);
                Integer id = this.getAccountConfigId(configDO);
                if (ObjectUtils.isEmpty(id)) {
                    preorderAccountConfigMapper.insert(configDO);
                } else {
                    configDO.setId(id);
                    configDO.setDelFlag(false);
                    this.UpdateAccountConfigById(configDO);
                }
            }
            return ResultVo.success("操作成功");
        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->导入失败：" + ex);
            return ResultVo.failure("导入失败：" + ex);
        }
    }

    @Override
    public void exportPreOrderAccountConfig(PreOrderAccountRequest request, HttpServletResponse response) {
        if (request == null) {
            return;
        }
        request.setPageSize(999999);
        ResultVo<PageInfo<PreOrderAccountConfigDO>> pageInfoResultVo = this.listPreOrderAccountConfig(request);
        if (pageInfoResultVo == null) {
            return;
        }
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(pageInfoResultVo.getData().getList())) {
            return;
        }
        List<PreOrderAccountConfigExportVO> preOrderAccountExportVOS = BeanCopyUtil.copyList(pageInfoResultVo.getData().getList(), PreOrderAccountConfigExportVO.class);
        try {
            String fileName = URLEncoder.encode("opsInventoryData", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");
            InputStream inputStream = new ClassPathResource(CommonConstants.others_data_export_excel).getInputStream();

            EasyExcel.write(response.getOutputStream(), PreOrderAccountConfigExportVO.class)
                    .withTemplate(inputStream)
                    .sheet("Sheet1").doWrite(preOrderAccountExportVOS);
        } catch (IOException e) {
            response.reset();
            throw new RuntimeException("导出数据发生异常: "+e.getMessage());
        }
    }

    @Override
    public ResultVo<String> deletePreOrderAccountConfigById(Long id) {
        if (id == null) {
            return ResultVo.failure("入参不可为空");
        }
        LambdaUpdateWrapper<PreOrderAccountConfigDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PreOrderAccountConfigDO::getId, id).set(PreOrderAccountConfigDO::getDelFlag, true);
        preorderAccountConfigMapper.update(null, updateWrapper);
        return ResultVo.success("操作成功");
    }

    /**
     * 返回Id,
     *
     * @param configDO
     * @return
     */
    private Integer getAccountConfigId(PreOrderAccountConfigDO configDO) {
        LambdaQueryWrapper<PreOrderAccountConfigDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreOrderAccountConfigDO::getPriority, configDO.getPriority())
                .eq(PreOrderAccountConfigDO::getInventoryTypeCode, configDO.getInventoryTypeCode())
                .eq(PreOrderAccountConfigDO::getCustomerNo, configDO.getCustomerNo())
                .eq(PreOrderAccountConfigDO::getGroupCustomerNo, configDO.getGroupCustomerNo())
                .eq(PreOrderAccountConfigDO::getPpl, configDO.getPpl())
                .eq(PreOrderAccountConfigDO::getProjectCode, configDO.getProjectCode());
        PreOrderAccountConfigDO accountConfigDO = preorderAccountConfigMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(accountConfigDO)) {
            return null;
        }
        return accountConfigDO.getId();
    }

    @Override
    public ResultVo<String> UpdateAccountConfigById(PreOrderAccountConfigDO configDO) {
        LambdaUpdateWrapper<PreOrderAccountConfigDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PreOrderAccountConfigDO::getId, configDO.getId())
                .set(ObjectUtils.isNotEmpty(configDO.getPriority()), PreOrderAccountConfigDO::getPriority, configDO.getPriority())
                .set(StringUtils.isNotBlank(configDO.getInventoryTypeCode()), PreOrderAccountConfigDO::getInventoryTypeCode, configDO.getInventoryTypeCode())
                .set(StringUtils.isNotBlank(configDO.getCustomerNo()), PreOrderAccountConfigDO::getCustomerNo, configDO.getCustomerNo())
                .set(StringUtils.isNotBlank(configDO.getPpl()), PreOrderAccountConfigDO::getPpl, configDO.getPpl())
                .set(StringUtils.isNotBlank(configDO.getProjectCode()), PreOrderAccountConfigDO::getProjectCode, configDO.getProjectCode())
                .set(StringUtils.isNotBlank(configDO.getGroupCustomerNo()), PreOrderAccountConfigDO::getGroupCustomerNo, configDO.getGroupCustomerNo())
                .set(ObjectUtils.isNotEmpty(configDO.getIsDelay()), PreOrderAccountConfigDO::getIsDelay, configDO.getIsDelay())
                .set(ObjectUtils.isNotEmpty(configDO.getDelayMaxDay()), PreOrderAccountConfigDO::getDelayMaxDay, configDO.getDelayMaxDay())
                .set(PreOrderAccountConfigDO::getDelFlag, configDO.getDelFlag())
                .set(PreOrderAccountConfigDO::getModifyTime, DateUtil.getNow())
                .set(PreOrderAccountConfigDO::getModifier, SMCApp.getLoginAuthDtoForSysUser().getUserNo());

        preorderAccountConfigMapper.update(null, updateWrapper);
        return ResultVo.success();
    }

    private Boolean UpdateAccountConfig(PreOrderAccountConfigDO configDO) {
        LambdaUpdateWrapper<PreOrderAccountConfigDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PreOrderAccountConfigDO::getPriority, configDO.getPriority())
                .eq(PreOrderAccountConfigDO::getInventoryTypeCode, configDO.getInventoryTypeCode())
                .eq(PreOrderAccountConfigDO::getCustomerNo, configDO.getCustomerNo())
                .eq(PreOrderAccountConfigDO::getGroupCustomerNo, configDO.getGroupCustomerNo())
                .eq(PreOrderAccountConfigDO::getPpl, configDO.getPpl())
                .eq(PreOrderAccountConfigDO::getProjectCode, configDO.getProjectCode());
        if (ObjectUtils.isNotEmpty(configDO.getIsDelay())) {
            updateWrapper.set(PreOrderAccountConfigDO::getIsDelay, configDO.getIsDelay());
        }
        if (ObjectUtils.isNotEmpty(configDO.getDelayMaxDay())) {
            updateWrapper.set(PreOrderAccountConfigDO::getDelayMaxDay, configDO.getDelayMaxDay());
        }
        updateWrapper.set(PreOrderAccountConfigDO::getModifyTime, DateUtil.getNow())
                .set(PreOrderAccountConfigDO::getModifier, SMCApp.getLoginAuthDtoForSysUser().getUserNo());

        preorderAccountConfigMapper.update(null, updateWrapper);
        return true;
    }

    @Override
    public ResultVo<String> writeAccountConfigByBath(List<PreOrderAccountConfigDO> list) {
        try {
            // 写入主表
            Integer size = list.size();
            Integer offset = 175;
            int runSize = (size / offset) + 1;
            if (runSize > 200) {
                runSize = 200;
            }
            ExecutorService executor = Executors.newFixedThreadPool(runSize);
            for (int idx = 0; idx < size; idx++) {
                final List<PreOrderAccountConfigDO> listSub = (idx + offset) >= size ? list.subList(idx, size) : list.subList(idx, idx + offset + 1);
                Future<Integer> future = executor.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {

                        try {
                            preorderAccountConfigMapper.insertByBatch(listSub);

                        } catch (NullPointerException ex) {
                            log.error(Thread.currentThread().getName() + ":->错误1：" + ex.getMessage());
                        } catch (Exception ex) {
                            log.error(Thread.currentThread().getName() + ":->错误2：" + ex.getMessage());
                        }
                        return 1;
                    }
                });
                idx = idx + offset;
            }
            executor.shutdown();
            while (true) {
                if (executor.isTerminated()) {
                    break;
                }
            }
            return ResultVo.success();

        } catch (Exception e) {
            log.error("writeAccountConfigByBath ->错误:", e);
            throw new BusinessException("writeAccountConfigByBath ->错误:" + e.getMessage(), e);
        }
    }
}
