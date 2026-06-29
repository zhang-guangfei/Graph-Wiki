package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.mapper.ProductPriceMapper;
import com.smc.smccloud.model.product.OrderModelInfoVO;
import com.smc.smccloud.model.product.ProductPriceDTO;
import com.smc.smccloud.model.product.ProductPriceVO;
import com.smc.smccloud.model.productPrice.ProductPriceDO;
import com.smc.smccloud.service.ProductPriceService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author smc
 * @since 2021-11-04
 */
@Service
public class ProductPriceServiceImpl extends ServiceImpl<ProductPriceMapper, ProductPriceDO> implements ProductPriceService {
    @Resource
    private ProductPriceMapper productPriceMapper;

    @Resource
    private RedisManager redisManager;

    @Override
    public ResultVo<String> getModelEPrice(String modelNo) {
        String eprice;

        //去redis里面获取价格
        Object object = redisManager.get("ops:modelEprice:" + modelNo);

        //如果没有则查数据库
        if (object == null) {
            QueryWrapper<ProductPriceDO> query = new QueryWrapper<>();
            query.select("TOP(1) ModelNo", "MinQuantity", "EPrice");
            query.lambda().eq(ProductPriceDO::getModelNo, modelNo)
                    .eq(ProductPriceDO::getIsDeleted, 0)
                    .orderByAsc(ProductPriceDO::getMinQuantity);
            ProductPriceDO priceDO = productPriceMapper.selectOne(query);
            if (priceDO == null) {
                return ResultVo.failure("未查询到E价");
            }
            //获取E价
            eprice = priceDO.getEprice().setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();

            redisManager.set("ops:modelEprice:" + modelNo, eprice);

        } else {
            eprice = object.toString();
        }

        return ResultVo.success(eprice);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<List<ProductPriceVO>> listProductPrice(List<String> modelNos) {
        if (CollectionUtils.isEmpty(modelNos)) {
            return ResultVo.failure("请输入查询数据。");
        }
        LambdaQueryWrapper<ProductPriceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ProductPriceDO::getModelNo, modelNos)
                .eq(ProductPriceDO::getIsDeleted, 0);

        List<ProductPriceDO> doList = productPriceMapper.selectList(queryWrapper);
        List<ProductPriceVO> dtoList = BeanCopyUtil.copyList(doList, ProductPriceVO.class);
        return ResultVo.success(dtoList);

    }

    @Override
    public ResultVo<String> getModelLotPrice(String modelNo, Integer quantity) {
        QueryWrapper<ProductPriceDO> priceQuery = new QueryWrapper<>();
        priceQuery.select("Top(1) ModelNo", "MinQuantity", "EPrice");
        priceQuery.lambda().eq(ProductPriceDO::getModelNo, modelNo)
                .le(ProductPriceDO::getMinQuantity, quantity)
                .eq(ProductPriceDO::getIsDeleted, 0)
                .orderByDesc(ProductPriceDO::getMinQuantity);
        ProductPriceDO productPriceDO = productPriceMapper.selectOne(priceQuery);
        if (productPriceDO == null) {
            return ResultVo.failure("未能查询到Lot价格");
        }
        return ResultVo.success(productPriceDO.getEprice().stripTrailingZeros().toPlainString());
    }


    @Override
    public ResultVo<OrderModelInfoVO> getModelInfoForOrder(String modelNo) {
        OrderModelInfoVO vo = productPriceMapper.getModelInfoForOrder(modelNo);
        return ResultVo.success(vo);
    }

    @Override
    public ResultVo<Boolean> isLotPrice(String modelNo) {

        if (StringUtils.isBlank(modelNo)) {
            return ResultVo.failure("参数不可为空");
        }
        QueryWrapper<ProductPriceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ModelNo", modelNo);
        List<ProductPriceDO> productPriceDOS = productPriceMapper.selectList(queryWrapper);
        if (productPriceDOS.isEmpty()) {
            return ResultVo.failure("暂无数据");
        }
        int collect = productPriceDOS.stream().mapToInt(ProductPriceDO::getMinQuantity).sum();
        if (collect > 1) {
            return ResultVo.success(true); // 是Lot价
        } else {
            return ResultVo.success(false);
        }
    }
}
