package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sales.ops.db.entity.ProductError;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.mapper.ProductErrorMapper;
import com.smc.smccloud.model.product.ProductErrorDO;
import com.smc.smccloud.service.ProductErrorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author smc
 * @since 2022-04-10
 */
@Service
public class ProductErrorServiceImpl implements ProductErrorService {


    @Resource
    private ProductErrorMapper productErrorMapper;

    @Override
    public ResultVo<Boolean> isErrorModelNo(String modelNo) {
        if (StringUtils.isBlank(modelNo)) {
            return ResultVo.success(Boolean.TRUE);
        }
        LambdaQueryWrapper<ProductErrorDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ProductErrorDO::getModelNo, modelNo)
                .eq(ProductErrorDO::getIsDeleted,0);
        Integer count = productErrorMapper.selectCount(queryWrapper);
        if (Optional.ofNullable(count).orElse(0) > 0) {
            return ResultVo.success(Boolean.TRUE);
        } else {
            return ResultVo.success(Boolean.FALSE);
        }
    }
}
