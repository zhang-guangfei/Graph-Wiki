package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.mapper.ProductDeliveryMapper;
import com.smc.smccloud.model.product.ProductDeliveryDO;
import com.smc.smccloud.service.ProductDeliveryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-01-21 11:36
 * Description:
 */
@Service
public class ProductDeliveryServiceImpl implements ProductDeliveryService {

    @Resource
    private ProductDeliveryMapper productDeliveryMapper;

    @Override
    public ResultVo<String> getSupplierNoByModelNo(String modelNo) {
        LambdaQueryWrapper<ProductDeliveryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProductDeliveryDO::getModelNo, ProductDeliveryDO::getSupplyId);
        queryWrapper.eq(ProductDeliveryDO::getModelNo, modelNo)
                .eq(ProductDeliveryDO::getIsPrimary, true);
        List<ProductDeliveryDO> deliveryList = productDeliveryMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(deliveryList)) {
            return ResultVo.failure("没有设置供应商");
        } else {
            return ResultVo.success(deliveryList.get(0).getSupplyId());
        }
    }

    @Override
    public String getOrgCountryByModelNo(String modelNo) {
        LambdaQueryWrapper<ProductDeliveryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProductDeliveryDO::getModelNo, ProductDeliveryDO::getOrgCountryId);
        queryWrapper.eq(ProductDeliveryDO::getModelNo, modelNo)
                .eq(ProductDeliveryDO::getIsPrimary, true)
                .orderByDesc(ProductDeliveryDO::getUpdateTime);
        List<ProductDeliveryDO> deliveryList = productDeliveryMapper.selectList(queryWrapper);
        return CollectionUtils.isEmpty(deliveryList) ? null : deliveryList.get(0).getOrgCountryId();
    }

    @Override
    public List<ProductDeliveryDO> listOrgCountryByModelNo(List<String> modelNo) {
        LambdaQueryWrapper<ProductDeliveryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProductDeliveryDO::getModelNo, ProductDeliveryDO::getOrgCountryId);
        queryWrapper.in(ProductDeliveryDO::getModelNo, modelNo)
                .eq(ProductDeliveryDO::getIsPrimary, true);
        return productDeliveryMapper.selectList(queryWrapper);
    }
}
