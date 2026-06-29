package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sales.ops.dto.purchase.OpsRequestPurchaseInterceptConfigVO;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.ExcelHelper;
import com.smc.smccloud.core.utils.ExcelUtil;
import com.smc.smccloud.mapper.OpsRequestpurchaseInterceptConfigMapper;
import com.smc.smccloud.model.interceptConfig.OpsRequestpurchaseInterceptConfigDO;
import com.smc.smccloud.service.IntercepterConfigService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author lyc
 * @Date 2024/9/13 13:22
 * @Descripton TODO
 */
@Service
public class IntercepterConfigServiceImpl implements IntercepterConfigService {
    @Resource
    private HttpServletResponse httpServletResponse;
    @Resource
    private OpsRequestpurchaseInterceptConfigMapper opsRequestpurchaseInterceptConfigMapper;
    @Override
    public void downloadTemplate() {
        String path = "templates/拦截配置导入模板.xlsx";
        ExcelUtil excel = new ExcelUtil(path);
        excel.writeToResponse(httpServletResponse, "拦截配置导入模板.xlsx");
    }

    @Override
    public ResultVo<String> importData(MultipartFile file, String optUser) {
        if (file == null) {
            return ResultVo.failure("请上传文件");
        }

        if (StringUtils.isBlank(optUser)) {
            return ResultVo.failure("操作人为空.请退出重新登录");
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
        OpsRequestpurchaseInterceptConfigDO opsRequestpurchaseInterceptConfigDO;
        List<OpsRequestpurchaseInterceptConfigDO> list = new ArrayList<>();
        while (true) {
            row++;
            rows = sheet.getRow(row);
            opsRequestpurchaseInterceptConfigDO = new OpsRequestpurchaseInterceptConfigDO();
            if (rows == null) {
                break;
            }
            String typeId = excel.getCellString(rows.getCell(0));
            String ruleKey = excel.getCellString(rows.getCell(1));
            String reason = excel.getCellString(rows.getCell(2));
            String valid = excel.getCellString(rows.getCell(3));
            //bugid:17646 c14717 20250522
            String outStock = excel.getCellString(rows.getCell(4));
            String ruleKey1 = excel.getCellString(rows.getCell(5));//自定义客户
            String ruleKey2 = excel.getCellString(rows.getCell(6));//自定义数量

            if(StringUtils.isBlank(typeId) && StringUtils.isBlank(ruleKey) && StringUtils.isBlank(reason)
                    && StringUtils.isBlank(valid)&& StringUtils.isBlank(outStock)) {
                continue;
            }
            if (StringUtils.isBlank(typeId)) {
                throw new BusinessException("第"+row+"行的匹配类型为空.请仔细检查数据");
            }
            if(typeId.length() != 1) {
                throw new BusinessException("第"+row+"行的匹配类型导入失败,请按照规则导入.");
            }

            if (StringUtils.isBlank(ruleKey)) {
                throw new BusinessException("第"+row+"行的关键字为空.请仔细检查数据");
            }
            if (ruleKey.contains("*")) {
                ruleKey = ruleKey.replaceAll("\\*","(.*)");
            }
            if (StringUtils.isBlank(reason)) {
                throw new BusinessException("第"+row+"行的拦截原因为空.请仔细检查数据");
            }
            if (StringUtils.isBlank(valid)) {
                throw new BusinessException("第"+row+"行的是否有效为空.请仔细检查数据");
            }
            if(valid.length() != 1) {
                throw new BusinessException("第"+row+"行的是否有效导入失败,请按照规则导入.");
            }
            //bugid:17646 c14717 20250522
            if(outStock.length() != 1) {
                throw new BusinessException("第"+row+"行的是否自动出库导入失败,请按照规则导入.");
            }
            //自定义拦截配置
            if(Integer.parseInt(typeId) == 2 && StringUtils.isNotBlank(ruleKey2)){
                try {
                    Integer.parseInt(ruleKey2);
                } catch (NumberFormatException e) {
                    throw new BusinessException("第"+row+"行的是自定义数量导入失败,请按照规则导入.");
                }
            }
            opsRequestpurchaseInterceptConfigDO.setTypeId(typeId);
            opsRequestpurchaseInterceptConfigDO.setRuleKey(ruleKey);
            //bugid:17646 c14717 20250522
            if(Integer.parseInt(typeId) == 2){
                if(StringUtils.isNotBlank(ruleKey1)){
                    opsRequestpurchaseInterceptConfigDO.setRuleKey1(ruleKey1);
                }
                if(StringUtils.isNotBlank(ruleKey2)){
                    opsRequestpurchaseInterceptConfigDO.setRuleKey2(Integer.parseInt(ruleKey2));
                }
            }
            opsRequestpurchaseInterceptConfigDO.setReason(reason);
            opsRequestpurchaseInterceptConfigDO.setAutoStockOut("1".equals(outStock));
            opsRequestpurchaseInterceptConfigDO.setEnable("1".equals(valid));
            opsRequestpurchaseInterceptConfigDO.setDefaultAction("4");
            opsRequestpurchaseInterceptConfigDO.setOperator(optUser);
            opsRequestpurchaseInterceptConfigDO.setUpdateTime(new Date());
            list.add(opsRequestpurchaseInterceptConfigDO);
        }
        LambdaQueryWrapper<OpsRequestpurchaseInterceptConfigDO> queryWrapper= new LambdaQueryWrapper<>();
        LambdaUpdateWrapper<OpsRequestpurchaseInterceptConfigDO> updateWrapper = new LambdaUpdateWrapper<>();
        for (OpsRequestpurchaseInterceptConfigDO item : list) {
            queryWrapper.clear();
            queryWrapper.eq(OpsRequestpurchaseInterceptConfigDO::getTypeId,item.getTypeId())
                    .eq(OpsRequestpurchaseInterceptConfigDO::getRuleKey,item.getRuleKey())
                    .eq(OpsRequestpurchaseInterceptConfigDO::getReason,item.getReason().trim());
            List<OpsRequestpurchaseInterceptConfigDO> opsRequestpurchaseInterceptConfigDOList = opsRequestpurchaseInterceptConfigMapper.selectList(queryWrapper);
            if(CollectionUtils.isEmpty(opsRequestpurchaseInterceptConfigDOList) && !item.getEnable()) {
                continue;
            }
            //bugid:17646 c14717 20250522
            if(!item.getTypeId().equals("2")){
                item.setRuleKey2(null);
                item.setRuleKey1(null);
            }
            if(CollectionUtils.isEmpty(opsRequestpurchaseInterceptConfigDOList)) {
                opsRequestpurchaseInterceptConfigMapper.insert(item);
            } else {
                updateWrapper.clear();
                updateWrapper
                        .eq(OpsRequestpurchaseInterceptConfigDO::getTypeId,item.getTypeId())
                        .eq(OpsRequestpurchaseInterceptConfigDO::getRuleKey,item.getRuleKey())
                        .eq(OpsRequestpurchaseInterceptConfigDO::getReason,item.getReason())
                        .set(OpsRequestpurchaseInterceptConfigDO::getReason,item.getReason())
                        .set(OpsRequestpurchaseInterceptConfigDO::getAutoStockOut,item.getAutoStockOut())
                        .set(OpsRequestpurchaseInterceptConfigDO::getEnable,item.getEnable())
                        .set(OpsRequestpurchaseInterceptConfigDO::getDefaultAction,item.getDefaultAction())
                        .set(OpsRequestpurchaseInterceptConfigDO::getOperator,item.getOperator())
                        .set(OpsRequestpurchaseInterceptConfigDO::getUpdateTime,item.getUpdateTime());
                //bugid:17646 c14717 20250522
                if(!item.getTypeId().equals("2")){
                    updateWrapper.set(OpsRequestpurchaseInterceptConfigDO::getRuleKey1,null);
                    updateWrapper.set(OpsRequestpurchaseInterceptConfigDO::getRuleKey2,null);
                }else {
                    if(StringUtils.isNotBlank(item.getRuleKey1())){
                        updateWrapper.set(OpsRequestpurchaseInterceptConfigDO::getRuleKey1,item.getRuleKey1());
                    }
                    if(Objects.nonNull(item.getRuleKey2())){
                        updateWrapper.set(OpsRequestpurchaseInterceptConfigDO::getRuleKey2,item.getRuleKey2());
                    }
                }
                opsRequestpurchaseInterceptConfigMapper.update(null,updateWrapper);
            }
        }
        return ResultVo.success("导入完毕");
    }

    @Override
    public ResultVo<String> addData(OpsRequestPurchaseInterceptConfigVO item) {
        if (item == null) {
            return ResultVo.failure("入参不可为空");
        }
        LambdaQueryWrapper<OpsRequestpurchaseInterceptConfigDO> queryWrapper= new LambdaQueryWrapper<>();

        LambdaUpdateWrapper<OpsRequestpurchaseInterceptConfigDO> updateWrapper = new LambdaUpdateWrapper<>();

        queryWrapper.eq(OpsRequestpurchaseInterceptConfigDO::getTypeId,item.getTypeid())
                .eq(OpsRequestpurchaseInterceptConfigDO::getRuleKey,item.getRulekey())
                .eq(OpsRequestpurchaseInterceptConfigDO::getReason,item.getReason().trim());
        List<OpsRequestpurchaseInterceptConfigDO> opsRequestpurchaseInterceptConfigDOList = opsRequestpurchaseInterceptConfigMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(opsRequestpurchaseInterceptConfigDOList) && !item.getEnable()) {
            return ResultVo.success("操作成功");
        }
        // bug14634 用户输入星号表示任意字符匹配时，替换为正确的正则格式
        if (StringUtils.isNotBlank(item.getRulekey())
                && item.getRulekey().contains("*")
                && !item.getRulekey().contains("(.*)")) {
            item.setRulekey(item.getRulekey().replace("*", "(.*)"));
        }
        if(CollectionUtils.isEmpty(opsRequestpurchaseInterceptConfigDOList)) {
            OpsRequestpurchaseInterceptConfigDO configDO = new OpsRequestpurchaseInterceptConfigDO();
            configDO.setTypeId(item.getTypeid());
            configDO.setRuleKey(item.getRulekey());
            //bugid:17646 c14717 20250522
            configDO.setAutoStockOut(item.getAutoStockOut());
            configDO.setRuleKey1(item.getRuleKey1());
            configDO.setRuleKey2(item.getRuleKey2());
            if(!configDO.getTypeId().equals("2")){
                configDO.setRuleKey2(null);
                configDO.setRuleKey1(null);
            }
            configDO.setReason(item.getReason());
            configDO.setEnable(item.getEnable());
            configDO.setDefaultAction(item.getDefaultaction());
            configDO.setOperator(item.getOperator());
            configDO.setUpdateTime(new Date());
            opsRequestpurchaseInterceptConfigMapper.insert(configDO);
        } else {
            updateWrapper
                    .eq(OpsRequestpurchaseInterceptConfigDO::getTypeId,item.getTypeid())
                    .eq(OpsRequestpurchaseInterceptConfigDO::getRuleKey,item.getRulekey())
                    .eq(OpsRequestpurchaseInterceptConfigDO::getReason,item.getReason())
                    .set(OpsRequestpurchaseInterceptConfigDO::getAutoStockOut,item.getAutoStockOut())
                    .set(OpsRequestpurchaseInterceptConfigDO::getReason,item.getReason())
                    .set(OpsRequestpurchaseInterceptConfigDO::getEnable,item.getEnable())
                    .set(OpsRequestpurchaseInterceptConfigDO::getDefaultAction,item.getDefaultaction())
                    .set(OpsRequestpurchaseInterceptConfigDO::getOperator,item.getOperator())
                    .set(OpsRequestpurchaseInterceptConfigDO::getUpdateTime,new Date());
            //bugid:17646 c14717 20250522
            if(!item.getTypeid().equals("2")){
                updateWrapper.set(OpsRequestpurchaseInterceptConfigDO::getRuleKey1,null);
                updateWrapper.set(OpsRequestpurchaseInterceptConfigDO::getRuleKey2,null);
            }else {
                if(StringUtils.isNotBlank(item.getRuleKey1())){
                    updateWrapper.set(OpsRequestpurchaseInterceptConfigDO::getRuleKey1,item.getRuleKey1());
                }
                if(Objects.nonNull(item.getRuleKey2())){
                    updateWrapper.set(OpsRequestpurchaseInterceptConfigDO::getRuleKey2,item.getRuleKey2());
                }
            }
            opsRequestpurchaseInterceptConfigMapper.update(null,updateWrapper);
        }
        return ResultVo.success("操作成功");
    }
}
