package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.ProductInterceptRuleMapper;
import com.smc.smccloud.model.product.ProductInterceptRuleDO;
import com.smc.smccloud.model.product.ProductInterceptRuleDTO;
import com.smc.smccloud.model.product.ProductInterceptRuleRequest;
import com.smc.smccloud.model.product.ProductInterceptRuleVO;
import com.smc.smccloud.service.DictCommonService;
import com.smc.smccloud.service.OpsCommonService;
import com.smc.smccloud.service.ProductInterceptRuleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Author: B90034
 * Date: 2022-05-23 09:43
 * Description: 特殊产品拦截规则服务
 */
@Slf4j
@Service
public class ProductInterceptRuleServiceImpl implements ProductInterceptRuleService {
    @Value("${file.base}")
    private String SERVERPATH;

    @Resource
    private DictCommonService dictCommonService;
    @Resource
    private ProductInterceptRuleMapper productInterceptRuleMapper;
    @Resource
    private HttpServletResponse response;

    @Override
    public ResultVo<List<ProductInterceptRuleVO>> listProductInterceptRule(ProductInterceptRuleRequest request) {
        // <!--add by WuWeiDong 20240112  bug 12635  增加型和类型查询 -->
        LambdaQueryWrapper<ProductInterceptRuleDO> queryWrapper = this.setQueryWrapper(request);
        List<ProductInterceptRuleDO> list = productInterceptRuleMapper.selectList(queryWrapper);
        List<ProductInterceptRuleVO> copyList = BeanCopyUtil.copyList(list, ProductInterceptRuleVO.class);
        return ResultVo.success(copyList);
    }
    @Override
    public ResultVo<PageInfo<ProductInterceptRuleVO>> listProductInterceptRuleByPage(ProductInterceptRuleRequest request){
        LambdaQueryWrapper<ProductInterceptRuleDO> queryWrapper = this.setQueryWrapper(request);
        PageHelper.startPage(request.getPageNum(),request.getPageSize());
        List<ProductInterceptRuleDO> list = productInterceptRuleMapper.selectList(queryWrapper);
        PageInfo<ProductInterceptRuleVO> pageInfo = BeanCopyUtil.pageDto2Vo(new PageInfo<>(list), ProductInterceptRuleVO.class);
        return ResultVo.success(pageInfo);
    }

    /**
     * <!--add by WuWeiDong 20240112  bug 12635  导出功能 -->
     *
     * @param request
     */
    @Override
    public void exportProductInterceptRule(ProductInterceptRuleRequest request) {

        List<String> colNameList = Arrays.asList("序号", "拦截名称", "拦截类型", "状态", "型号", "型号类型", "供应商", "原产地", "许可客户", "最小数量"
                , "最大数量", "处理方式", "变更仓库", "变更供应商", "是否定时重新检查", "备注");
        int row = 0;
        int colIndex = 0;
        try {

            ExcelUtil excel = new ExcelUtil(FileUtil.getTemplate("templates/ProductInterceptRule.xlsx"));

            // 写入表头
//              colIndex = 0;
//            for (String name : colNameList) {
//                excel.setCellValue(row, colIndex++, name);
//            }
            row++;
            Map<String, String> applyTypeMap = dictCommonService.getDataCodesMap("2046").getData();
            Map<String, String> actionTypeMap = dictCommonService.getDataCodesMap("2047").getData();


            //写入内容
            ResultVo<List<ProductInterceptRuleVO>> ruleVOList = this.listProductInterceptRule(request);

            if (CollectionUtils.isEmpty(ruleVOList.getData())) {
                throw new BaseException("特殊产品拦截规则导出错误:".concat("没有数据。"));
            }

            Integer id = 1;
            for (ProductInterceptRuleVO vo : ruleVOList.getData()) {
                colIndex = 0;
                excel.setCellValue(row, colIndex++, id.toString());
                excel.setCellValue(row, colIndex++, ObjectUtils.isEmpty(vo.getName()) ? "" : vo.getName());


                String context = "";
                if (ObjectUtils.isNotEmpty(vo.getApplyType())) {
                    context = vo.getApplyType().toString();
                    context = applyTypeMap.getOrDefault(context, context);
                }
                excel.setCellValue(row, colIndex++, context);

                context = Optional.ofNullable(vo.getStatus()).orElse(9) == 1 ? "启用" : "取消";
                excel.setCellValue(row, colIndex++, context);
                excel.setCellValue(row, colIndex++, StringUtils.isEmpty(vo.getModelNo()) ? "" : vo.getModelNo());

                context = Optional.ofNullable(vo.getStatus()).orElse(2) == 1 ? "完整型号" : "系列";
                excel.setCellValue(row, colIndex++, context);

                excel.setCellValue(row, colIndex++, StringUtils.isEmpty(vo.getSupplierId()) ? "" : vo.getSupplierId());
                excel.setCellValue(row, colIndex++, StringUtils.isEmpty(vo.getOrigin()) ? "" : vo.getOrigin());
                excel.setCellValue(row, colIndex++, StringUtils.isEmpty(vo.getWarehouseCode()) ? "" : vo.getWarehouseCode());
                excel.setCellValue(row, colIndex++, StringUtils.isEmpty(vo.getAllowCustomerNos()) ? "" : vo.getAllowCustomerNos());
                excel.setCellValue(row, colIndex++, ObjectUtils.isEmpty(vo.getMinQty()) ? "" : vo.getMinQty().toString());
                excel.setCellValue(row, colIndex++, ObjectUtils.isEmpty(vo.getMaxQty()) ? "" : vo.getMaxQty().toString());


                context = "";
                if (ObjectUtils.isNotEmpty(vo.getActionType())) {
                    context = vo.getActionType().toString();
                    context = actionTypeMap.getOrDefault(context, context);
                }
                excel.setCellValue(row, colIndex++, context);
                excel.setCellValue(row, colIndex++, StringUtils.isEmpty(vo.getToWarehouseCode()) ? "" : vo.getToWarehouseCode());
                excel.setCellValue(row, colIndex++, StringUtils.isEmpty(vo.getRestartFlag()) ? "" : vo.getRestartFlag());
                excel.setCellValue(row, colIndex++, StringUtils.isEmpty(vo.getRemark()) ? "" : vo.getRemark());

                row++;
                id++;
            }
            excel.getSxssfSheet().flushRows();
            excel.writeToResponse(response, "先行在库申请项清单.xlsx");

        } catch (Exception e) {
            log.error("特殊产品拦截规则导出错误 failure, reason = {}", e.getMessage(), e);
            throw new BaseException("特殊产品拦截规则导出错误:".concat(e.getMessage()));
        }

    }

    @Override
    public ResultVo<String> addOrUpdateInterceptRule(ProductInterceptRuleVO ruleVO) {
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        ProductInterceptRuleDO ruleDO = BeanCopyUtil.copy(ruleVO, ProductInterceptRuleDO.class);

        if (ObjectUtils.isEmpty(ruleDO.getModelType())) {
            ruleDO.setModelType(0);
        }
        if (ObjectUtils.isEmpty(ruleDO.getApplyType())) {
            ruleDO.setApplyType(0);
        }
        // model 转换 matchModleNo
        String modelno = ruleDO.getModelNo().replaceAll("\n", "\\|");
        if (ruleDO.getApplyType().compareTo(2) == 0) {
            //    <!--add by WuWeiDong 20240401  bug 12635  拦截类型=基础数据计算(2)，拆开保存 -->
            String[] modelnos = ruleDO.getModelNo().split("\n");
            ProductInterceptRuleDO newRuleDO = ruleDO;

            if (modelnos.length == 1) {
                newRuleDO.setMatchModelNo(this.modelNoConvertToMatchModelNo(modelnos[0]));
                newRuleDO.setModelNo(modelnos[0]);
                this.addOrUpdateInterceptRulForBinModel(newRuleDO);
            } else {
                Boolean first = true;
                for (String str : modelnos) {
                    if (first && (ObjectUtils.isNotEmpty(ruleVO.getId()))) {
                        newRuleDO.setId(ruleDO.getId());
                    }
                    first = false;
                    newRuleDO.setModelNo(str);
                    newRuleDO.setMatchModelNo(this.modelNoConvertToMatchModelNo(str));
                    this.addOrUpdateInterceptRulForBinModel(newRuleDO);
                }
            }
            return ResultVo.success("添加成功");
        }
        if (ruleDO.getModelType().compareTo(1) == 0) {
            ruleDO.setMatchModelNo(modelno);
        } else {
            // model 转换 matchModleNo
            ruleDO.setMatchModelNo(this.modelNoConvertToMatchModelNo(modelno));
        }
        if (PublicUtil.isEmpty(ruleVO.getId())) {
            ruleDO.setStatus(1);
            ruleDO.setCreateUser(userDTO.getUserNo());
            ruleDO.setCreateTime(new Date());
            int insert = productInterceptRuleMapper.insert(ruleDO);
            return insert == 1 ? ResultVo.success("添加成功") : ResultVo.failure("添加失败");
        } else {
            if (ruleDO.getStatus().compareTo(9) == 0) {
                ruleDO.setStatus(1);
            }
            ruleDO.setUpdateUser(userDTO.getUserNo());
            ruleDO.setUpdateTime(new Date());
            int update = productInterceptRuleMapper.updateById(ruleDO);
            return update == 1 ? ResultVo.success("修改成功") : ResultVo.failure("修改失败");
        }
    }

    /**
     * <!--add by WuWeiDong 20240401  bug 12635  拦截类型=基础数据计算 拆开保存 -->
     * 针对 拦截类型=基础数据计算(2)， 写入或更新
     *
     * @param interceptRuleDO
     * @return
     */
    public ResultVo<String> addOrUpdateInterceptRulForBinModel(ProductInterceptRuleDO interceptRuleDO) {
        if (PublicUtil.isEmpty(interceptRuleDO.getModelNo())) {
            return ResultVo.failure("修改失败，请输入型号");
        }
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        LambdaQueryWrapper<ProductInterceptRuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductInterceptRuleDO::getModelNo, interceptRuleDO.getModelNo())
                .eq(ProductInterceptRuleDO::getApplyType, 2);
        List<ProductInterceptRuleDO> ruleDOS = productInterceptRuleMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(ruleDOS)) {
            interceptRuleDO.setStatus(1);
            interceptRuleDO.setCreateUser(userDTO.getUserNo());
            interceptRuleDO.setCreateTime(new Date());
            interceptRuleDO.setUpdateUser(userDTO.getUserNo());
            interceptRuleDO.setUpdateTime(new Date());
            int insert = productInterceptRuleMapper.insert(interceptRuleDO);

        } else {

            ProductInterceptRuleDO ruleDO = ruleDOS.get(0);
            if (ruleDO.getStatus().compareTo(9) == 0) {
                interceptRuleDO.setStatus(1);
            }
            interceptRuleDO.setId(ruleDO.getId());
            interceptRuleDO.setUpdateUser(userDTO.getUserNo());
            interceptRuleDO.setUpdateTime(new Date());
            int update = productInterceptRuleMapper.updateById(interceptRuleDO);
        }
        return ResultVo.success("添加成功");

    }

    @Override
    public List<ProductInterceptRuleDTO> checkProductInterceptRule(ProductInterceptRuleDTO dto) {
        LambdaQueryWrapper<ProductInterceptRuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq((dto.getApplyType() != null), ProductInterceptRuleDO::getApplyType, dto.getApplyType())
                .eq(ProductInterceptRuleDO::getStatus, 1);
        List<ProductInterceptRuleDO> ruleList = productInterceptRuleMapper.selectList(queryWrapper);
        // 根据处理类型分组
        Map<Integer, List<ProductInterceptRuleDO>> actionMap = ruleList.stream().collect(Collectors.groupingBy(ProductInterceptRuleDO::getActionType));
        // 3-自动退回 > 1-拦截转人工 > 4-转等待处理 > 2-自动变更
        List<Integer> priorityList = Arrays.asList(3, 1, 4, 2);

        List<ProductInterceptRuleDTO> matchRuleDTOList = new ArrayList<>();
        String splitRegex = "\\|";
        boolean isMatch;
        ProductInterceptRuleDTO matchRule; // 匹配规则结果

        for (Integer actionType : priorityList) {
            for (ProductInterceptRuleDO rule : actionMap.getOrDefault(actionType, Collections.emptyList())) {
                isMatch = false;
                if (rule.getModelType() == 1) {
                    // 匹配整型号拦截规则
                    if (Arrays.asList(rule.getMatchModelNo().split(splitRegex)).contains(dto.getModelNo())) {
                        isMatch = this.matchInterceptModel(rule, dto);
                    }
                } else {
                    // 匹配系列型号拦截规则
                    if (Pattern.matches(rule.getMatchModelNo(), dto.getModelNo())) {
                        isMatch = this.matchInterceptModel(rule, dto);
                    }
                }
                if (isMatch) {
                    matchRule = BeanCopyUtil.copy(dto, ProductInterceptRuleDTO.class);
                    matchRule.setIntercept(true);
                    matchRule.setId(rule.getId());
                    matchRule.setActionType(rule.getActionType());
                    matchRule.setToSupplierId(rule.getToSupplierId());
                    matchRule.setToWarehouseCode(rule.getToWarehouseCode());
                    matchRule.setMinQty(rule.getMinQty());
                    matchRule.setMaxQty(rule.getMaxQty());
                    matchRule.setRemark(rule.getRemark());
                    matchRule.setRestartFlag(rule.getRestartFlag());
                    matchRuleDTOList.add(matchRule);
                }
            }
        }

        /*if (dto.getId() != null) {
            int index = 0;
            for (int i = 0; i < matchRuleDTOList.size(); i++) {
                if (matchRuleDTOList.get(i).getId().compareTo(dto.getId()) == 0 && matchRuleDTOList.get(i).getActionType() == 1) {
                    index = ++i;
                    break;
                }
            }
            if (index > 0 && index <= matchRuleDTOList.size()) {
                matchRuleDTOList = matchRuleDTOList.subList(index, matchRuleDTOList.size());
            }
        }*/
        return matchRuleDTOList;
    }

    @Override
    public ResultVo<String> deleteInterceptRule(Integer id) {
        if (PublicUtil.isEmpty(id)) {
            return ResultVo.failure("请输入id");
        }
        ProductInterceptRuleDO ruleDO = new ProductInterceptRuleDO();
        ruleDO.setId(id);
        ruleDO.setStatus(9);
        ruleDO.setUpdateTime(new Date());
        ruleDO.setUpdateUser(SMCApp.getLoginAuthDtoForSysUser().getUserNo());
         productInterceptRuleMapper.updateById(ruleDO);
        return  ResultVo.success("取消成功");
    }

    @Override
    public ResultVo<String> deleteInterceptRuleByIds(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultVo.failure("请输入id");
        }
        LambdaUpdateWrapper<ProductInterceptRuleDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(ProductInterceptRuleDO::getId, ids)
                .set(ProductInterceptRuleDO::getStatus, 9)
                .set(ProductInterceptRuleDO::getUpdateTime, new Date())
                .set(ProductInterceptRuleDO::getUpdateUser, SMCApp.getLoginAuthDtoForSysUser().getUserNo());
        productInterceptRuleMapper.update(null, updateWrapper);
        return ResultVo.success("取消成功");
    }

    /**
     * 根据条件删除拦截规则 状态改为9 取消
     *
     * @param request
     * @return
     */
    public ResultVo<String> deleteInterceptRuleByCondition(ProductInterceptRuleRequest request) {
        if (StringUtils.isBlank(request.getModelNo()) &&
                CollectionUtils.isEmpty(request.getModelNos())
                && StringUtils.isBlank(request.getName()) && ObjectUtils.isEmpty(request.getApplyType())) {
            return ResultVo.failure("请输入条件");
        }
        LambdaUpdateWrapper<ProductInterceptRuleDO> updateWrapper = new LambdaUpdateWrapper<>();
        if (StringUtils.isNotBlank(request.getModelNo())) {
            updateWrapper.like(ProductInterceptRuleDO::getModelNo, request.getModelNo());
        }
        if (CollectionUtils.isNotEmpty(request.getModelNos())) {
            if (request.getModelNos().size() == 1) {
                updateWrapper.in(ProductInterceptRuleDO::getModelNo, request.getModelNos().get(0));
            } else {
                updateWrapper.in(ProductInterceptRuleDO::getModelNo, request.getModelNos());
            }
        }
        updateWrapper.like(PublicUtil.isNotEmpty(request.getName()), ProductInterceptRuleDO::getName, request.getName())
                .eq(PublicUtil.isNotEmpty(request.getApplyType()), ProductInterceptRuleDO::getApplyType, request.getApplyType())
                .ne(ProductInterceptRuleDO::getStatus, 9)
                .set(ProductInterceptRuleDO::getStatus, 9)
                .set(ProductInterceptRuleDO::getUpdateTime, new Date())
                .set(ProductInterceptRuleDO::getUpdateUser, SMCApp.getLoginAuthDtoForSysUser().getUserNo());
        productInterceptRuleMapper.update(null, updateWrapper);
        return ResultVo.success("取消成功");
    }

    /**
     * 拦截规则匹配
     *
     * @param rule 拦截规则
     * @param dto  验证信息
     * @return 拦截结果
     */
    private boolean matchInterceptModel(ProductInterceptRuleDO rule, ProductInterceptRuleDTO dto) {
        if (StringUtils.isNotBlank(rule.getSupplierId()) && !rule.getSupplierId().equals(dto.getSupplierId())) {
            return false; // 供应商放行
        }
        if (StringUtils.isNotBlank(rule.getWarehouseCode()) && !rule.getWarehouseCode().equals(dto.getWarehouseCode())) {
            return false; // 仓库放行
        }
        if (StringUtils.isNotBlank(rule.getAllowCustomerNos()) && rule.getAllowCustomerNos().contains(dto.getCustomerNo())) {
            return false; // 客户放行
        }
        if (rule.getMinQty() != null && rule.getMinQty() < dto.getQuantity()) {
            return false; // 最小数量放行
        }
        if (rule.getMaxQty() != null && rule.getMaxQty() > dto.getQuantity()) {
            return false; // 最大数量放行
        }
        return true;
    }

    /**
     * '#'表示一个数字[0-9], '?'表示一个字母[A-Z], '*'表示任意个字符, 多个匹配型号用'|'分隔
     *
     * @param modelNo 匹配型号
     * @return MatchModelNo
     */
    private String modelNoConvertToMatchModelNo(String modelNo) {
        String startMark = "^";
        String endMark = "$";
        String[] modelNos = modelNo.split("\\|");

        for (int i = 0; i < modelNos.length; i++) {
            char c = modelNos[i].charAt(0);
            if ((c > 47 && c < 58) || (c > 64 && c < 91)) {
                modelNos[i] = startMark + modelNos[i];
            }
            c = modelNos[i].charAt(modelNos[i].length() - 1);
            if ((c > 47 && c < 58) || (c > 64 && c < 91)) {
                modelNos[i] = modelNos[i] + endMark;
            }
            modelNos[i] = modelNos[i].replaceAll("\\*", "\\\\S+")
                    .replaceAll("#", "[0-9]")
                    .replaceAll("\\?", "[A-Z]");
        }
        return StringUtils.join(modelNos, "|");
    }

    private LambdaQueryWrapper<ProductInterceptRuleDO> setQueryWrapper(ProductInterceptRuleRequest request) {
        LambdaQueryWrapper<ProductInterceptRuleDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(request.getModelNo())) {
            queryWrapper.like(ProductInterceptRuleDO::getModelNo, request.getModelNo());
        }
        if (CollectionUtils.isNotEmpty(request.getModelNos())) {
            if (request.getModelNos().size() == 1) {
                queryWrapper.in(ProductInterceptRuleDO::getModelNo, request.getModelNos().get(0));
            } else {
                queryWrapper.in(ProductInterceptRuleDO::getModelNo, request.getModelNos());
            }
        }
        queryWrapper.like(PublicUtil.isNotEmpty(request.getName()), ProductInterceptRuleDO::getName, request.getName())
                .eq(PublicUtil.isNotEmpty(request.getStatus()), ProductInterceptRuleDO::getStatus, request.getStatus())
                .eq(PublicUtil.isNotEmpty(request.getApplyType()), ProductInterceptRuleDO::getApplyType, request.getApplyType());
        return queryWrapper;
    }
}
