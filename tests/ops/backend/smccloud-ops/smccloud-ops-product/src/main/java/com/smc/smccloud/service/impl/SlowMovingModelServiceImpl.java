package com.smc.smccloud.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.SlowMovingModelMapper;
import com.smc.smccloud.model.SlowMovingModel.SlowMovingModelDO;
import com.smc.smccloud.model.SlowMovingModel.SlowMovingModelVO;
import com.smc.smccloud.model.SlowMovingModel.SlowMovingRequest;
import com.smc.smccloud.model.product.ProductInfoVO;
import com.smc.smccloud.service.ProductDeliveryService;
import com.smc.smccloud.service.ProductService;
import com.smc.smccloud.service.SlowMovingModelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
public class SlowMovingModelServiceImpl implements SlowMovingModelService {

    @Resource
    private SlowMovingModelMapper slowMovingModelMapper;
    @Resource
    private ProductService productService;
    @Resource
    private ProductDeliveryService productDeliveryService;

    @Override
    public ResultVo<PageInfo<SlowMovingModelVO>> listSlowMovingModelData(SlowMovingRequest request) {
        LambdaQueryWrapper<SlowMovingModelDO> query = new LambdaQueryWrapper<>();
        query.eq(PublicUtil.isNotEmpty(request.getModelNo()), SlowMovingModelDO::getModelNo, request.getModelNo())
                .eq(PublicUtil.isNotEmpty(request.getStatus()),SlowMovingModelDO::getStatus,request.getStatus());

        if (PublicUtil.isEmpty(request.getDateType())) {
            request.setDateType(0);
        }
        if (request.getDateType() == 1) {
            query.between(PublicUtil.isNotEmpty(request.getStratTime()), SlowMovingModelDO::getUpdateTime, request.getStratTime(), request.getEndTime());
        }
        if (request.getDateType() == 2) {
            query.between(PublicUtil.isNotEmpty(request.getStratTime()), SlowMovingModelDO::getLastInDate, request.getStratTime(), request.getEndTime());
        }
        if (request.getDateType() == 3) {
            query.between(PublicUtil.isNotEmpty(request.getStratTime()), SlowMovingModelDO::getLastOutDate, request.getStratTime(), request.getEndTime());
        }

        PageInfo<SlowMovingModelDO> pageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize())
                .doSelectPageInfo(() -> slowMovingModelMapper.selectList(query));
        PageInfo<SlowMovingModelVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, SlowMovingModelVO.class);

        return ResultVo.success(voPageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> importSlowModelData(MultipartFile file) {

        ResultVo<String> stringResultVo = checkImportSlowModelData(file);
        if (!stringResultVo.isSuccess()) {
            return stringResultVo;
        }

        ExcelHelper excel = null;
        try {
            excel = new ExcelHelper(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("滞留品导入失败" + e.getMessage());
        }
        Sheet sheet = excel.getSheet();
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();

        int colIndex;
        int lastRowNum = sheet.getLastRowNum();
        Row row;
        Date nowDate = DateUtil.getNow();
        LambdaQueryWrapper<SlowMovingModelDO> query = new LambdaQueryWrapper<>();
        SlowMovingModelDO modelDO = null;
        ResultVo<ProductInfoVO> resultVo;
        ResultVo<String> noByModelNo;
        try {
            for (int rowNum =1; rowNum<= lastRowNum; rowNum++) {
                row = sheet.getRow(rowNum);
                if (row == null) {
                    break;
                }
                colIndex = 0;
                modelDO = new SlowMovingModelDO();
                modelDO.setModelNo(excel.getCellString(row.getCell(colIndex++)).trim());
                resultVo = productService.getProductInfoByModelNo(modelDO.getModelNo());
                if (resultVo.isSuccess() && PublicUtil.isNotEmpty(resultVo.getData())) {
                    if (resultVo.getData().getEPrice() != null) {
                        modelDO.setEprice(new BigDecimal(resultVo.getData().getEPrice()));
                    }
                    modelDO.setProductName(resultVo.getData().getChineseName());
                    modelDO.setLotPrice(resultVo.getData().getLotPrice());
                    modelDO.setDesignTypeId(resultVo.getData().getDesignTypeId());
                }
                noByModelNo = productDeliveryService.getSupplierNoByModelNo(modelDO.getModelNo());
                if (noByModelNo.isSuccess()) {
                    modelDO.setSupplier(noByModelNo.getData());
                }
                modelDO.setQuantity(excel.getCellInteger(row.getCell(colIndex++)));
                modelDO.setWarehouseCode(excel.getCellString(row.getCell(colIndex++)));
                String lastInDate = excel.getCellString(row.getCell(colIndex++)).trim();
                if (PublicUtil.isNotEmpty(lastInDate)) {
                    modelDO.setLastInDate(DateUtil.stringToDate(lastInDate));
                }
                String lastOutDate = excel.getCellString(row.getCell(colIndex++)).trim();
                if (PublicUtil.isNotEmpty(lastOutDate)) {
                    modelDO.setLastOutDate(DateUtil.stringToDate(lastOutDate));
                }
                modelDO.setRemark(excel.getCellString(row.getCell(colIndex)).trim());
                modelDO.setCreateUser(userDTO.getUserNo());
                modelDO.setStatus(1);

                query.clear();
                query.eq(SlowMovingModelDO::getModelNo, modelDO.getModelNo());
                SlowMovingModelDO movingModelDO = slowMovingModelMapper.selectOne(query);
                if (movingModelDO != null) {
                    modelDO.setQuantity(movingModelDO.getQuantity());
                    slowMovingModelMapper.update(modelDO, query);
                } else {
                    slowMovingModelMapper.insert(modelDO);
                }
            }

            UpdateWrapper<SlowMovingModelDO> queryWrapper = new UpdateWrapper<>();
            queryWrapper.set("status", 9);
            queryWrapper.lt("updateTime", nowDate);
            slowMovingModelMapper.update(null, queryWrapper);

        } catch (Exception e) {
            log.error("数据写入异常 {}", JSONObject.toJSONString(modelDO),e);
            throw new RuntimeException("滞留品导入失败" + e.getMessage());
        }

        return ResultVo.success("导入成功");
    }

    public ResultVo<String> checkImportSlowModelData(MultipartFile file) {
        ExcelHelper excel = null;
        try {
            excel = new ExcelHelper(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("滞留品导入失败" + e.getMessage());
        }
        Sheet sheet = excel.getSheet();
        int colIndex;
        int lastRowNum = sheet.getLastRowNum();
        Row row;
        StringBuilder errorMsg = new StringBuilder();
        ResultVo<ProductInfoVO> resultVo;
        ResultVo<String> noByModelNo;
        for (int rowNum =1; rowNum<= lastRowNum; rowNum++) {
            row = sheet.getRow(rowNum);
            if (row == null) {
                break;
            }
            colIndex = 0;
            String modelNo = excel.getCellString(row.getCell(colIndex++)).trim();

            if (StringUtils.isBlank(modelNo)) {
                errorMsg.append("第").append(rowNum).append("行型号为空;");
                continue;
            }

            String qty = excel.getCellString(row.getCell(colIndex)).trim();
            if (StringUtils.isBlank(qty)) {
                errorMsg.append("第").append(rowNum).append("行数量为空;");
                continue;
            }

            resultVo = productService.getProductInfoByModelNo(modelNo);
            if (resultVo != null && resultVo.isSuccess() && PublicUtil.isNotEmpty(resultVo.getData())) {
                if (resultVo.getData().getEPrice() == null) {
                    errorMsg.append("第").append(rowNum).append("行").append(modelNo).append("E价为空");
                    continue;
                }

            } else {
                errorMsg.append("第").append(rowNum).append("行").append(modelNo).append("未能获取产品信息");
                continue;
            }
            noByModelNo = productDeliveryService.getSupplierNoByModelNo(modelNo);
            if (!noByModelNo.isSuccess()) {
                errorMsg.append("第").append(rowNum).append("行").append(modelNo).append("未能获取供应商");
            }
        }

        if(StringUtils.isNotBlank(errorMsg.toString())) {
            return ResultVo.failure(errorMsg.toString());
        }

        return ResultVo.success("校验通过");
    }

    @Override
    public ResultVo<String> updateSlowModelData(SlowMovingModelVO modelVO) {
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        SlowMovingModelDO modelDO = BeanCopyUtil.copy(modelVO, SlowMovingModelDO.class);
        modelDO.setUpdateUser(userDTO.getUserNo());
        int update = slowMovingModelMapper.updateById(modelDO);
        return update == 1 ? ResultVo.success("修改成功") : ResultVo.failure("修改失败");
    }

    @Override
    public ResultVo<String> delSlowModelData(Integer id) {
        if (PublicUtil.isEmpty(id)) {
            ResultVo.failure("请选择需要删除的数据");
        }
        SlowMovingModelDO modelDO = new SlowMovingModelDO();
        modelDO.setId(id);
        modelDO.setStatus(9);
        int i = slowMovingModelMapper.updateById(modelDO);

        return i == 1 ? ResultVo.success("删除成功") : ResultVo.failure("删除失败");
    }

    @Override
    public ResultVo<String> calcUpdateSlowModelOnHnad() {
        slowMovingModelMapper.calcSlowModel();
        return ResultVo.success("执行成功！");
    }

}
