package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.mapper.product.ProductBomDetailMapper;
import com.smc.smccloud.mapper.product.ProductBomMapper;
import com.smc.smccloud.model.prestock.ProductBomDO;
import com.smc.smccloud.model.prestock.ProductBomDetailDO;
import com.smc.smccloud.service.ProductBomService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-01-11 11:09
 * Description:
 */
@Service
public class ProductBomServiceImpl implements ProductBomService {

    @Resource
    private ProductBomDetailMapper productBomDetailMapper;
    @Resource
    private ProductBomMapper productBomMapper;

    @Override
    public List<ProductBomDetailDO> findSplittableDetailByModelNo(String modelNo) {
        return productBomDetailMapper.listSplittableDetailByModelNo(modelNo);
    }

    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<ProductBomDetailDO> listPreStockSplitDetailByModelNo(String modelNo) {
        return productBomDetailMapper.listPreStockSplitDetailByModelNo(modelNo);
    }

    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Boolean isCanSplit(String modelNo) {
        if (StringUtils.isBlank(modelNo)) {
            return Boolean.FALSE;
        }
        QueryWrapper<ProductBomDO> queryWrapper = Wrappers.query();
        queryWrapper.select("Top(1) bomId");
        queryWrapper.lambda().eq(ProductBomDO::getModelNo, modelNo)
                .eq(ProductBomDO::getIsValid, Boolean.TRUE);
        ProductBomDO bomInfo = productBomMapper.selectOne(queryWrapper);
        return bomInfo == null ? Boolean.FALSE : Boolean.TRUE;
    }

    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Boolean isPreStockCanSplit(String modelNo) {
        if (StringUtils.isBlank(modelNo)) {
            return Boolean.FALSE;
        }
        QueryWrapper<ProductBomDO> queryWrapper = Wrappers.query();
        queryWrapper.select("Top(1) bomId");
        queryWrapper.lambda().eq(ProductBomDO::getModelNo, modelNo)
                .eq(ProductBomDO::getIsValid, Boolean.TRUE)
                .eq(ProductBomDO::getPriorityComplete, Boolean.FALSE);
        ProductBomDO bomInfo = productBomMapper.selectOne(queryWrapper);
        return bomInfo == null ? Boolean.FALSE : Boolean.TRUE;
    }

}
