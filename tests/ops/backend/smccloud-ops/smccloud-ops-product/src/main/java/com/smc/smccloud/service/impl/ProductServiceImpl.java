package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.ChinaSuppplierEnum;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.Manufacture.OpsShareTModelAvailableToSalesDO;
import com.smc.smccloud.model.Manufacture.OpsTModelAvailableToSales;
import com.smc.smccloud.model.product.*;
import com.smc.smccloud.service.BinServiceFeignApi;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: B90034
 * Date: 2022-01-26 15:58
 * Description:
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductEosMapper productEosMapper;
    @Resource
    private ProductDeliveryMapper productDeliveryMapper;
    @Resource
    private BinServiceFeignApi binServiceFeignApi;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private RedisManager redisManager;
    @Resource
    private OpsTModelAvailableToSalesMapper salesMapper;
    @Resource
    private OpsShareTModelAvailableToSales shareSalesMapper;
    @Resource
    private ProductRestrictCustomerWhitelistMapper productRestrictCustomerWhitelistMapper;
    @Resource
    private ProductRestrictMapper productRestrictMapper;
    @Resource
    private ProductPhysicsMapper productPhysicsMapper;

    @Override
    public ResultVo<Integer> getMinPackUnitByModelNo(String modelNo) {
        if (StringUtils.isEmpty(modelNo)) {
            return ResultVo.failure("型号不能为空");
        }
        LambdaQueryWrapper<ProductDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProductDO::getModelNo, ProductDO::getMinPackUnit);
        queryWrapper.eq(ProductDO::getModelNo, modelNo)
                .ne(ProductDO::getIsDeleted, true);
        ProductDO productInfo = productMapper.selectOne(queryWrapper);
        if (productInfo == null) {
            return ResultVo.failure("该型号不存在");
        }
        int minPackUnit = Optional.ofNullable(productInfo.getMinPackUnit()).orElse(1);
        return ResultVo.success(minPackUnit);
    }

    @Override
    public ResultVo<ProductInfoVO> getProductInfoByModelNo(String modelNo) {
        ProductInfoVO info = productMapper.getProductInfoByModelNo(modelNo);
        return ResultVo.success(info);
    }

    @Override
    public ResultVo<String> getProductEnglishName(String modelNo) {
        LambdaQueryWrapper<ProductDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProductDO::getModelNo, ProductDO::getECode, ProductDO::getEnglishName);
        queryWrapper.eq(ProductDO::getModelNo, modelNo)
                .ne(ProductDO::getIsDeleted, true);
        ProductDO productInfo = productMapper.selectOne(queryWrapper);
        if (productInfo == null) {
            return ResultVo.failure("该型号不存在");
        }
        if (StringUtils.isNotBlank(productInfo.getEnglishName())) {
            return ResultVo.success(productInfo.getEnglishName());
        }
        String invEngName = productMapper.getProductInvEngName(productInfo.getECode());
        return ResultVo.success(invEngName);
    }

    @Override
    public ResultVo<List<ProductCashierVO>> listProductEos(List<String> modelNos) {
        if (modelNos.isEmpty()) {
            return ResultVo.failure("型号不可为空.");
        }

        List<ProductCashierVO> list = new ArrayList<>();

        for (String modelNo : modelNos) {

            ProductCashierVO productCashierVO = new ProductCashierVO();

            QueryWrapper<ProductEosDO> eosDOQueryWrapper = new QueryWrapper<>();
            eosDOQueryWrapper.eq("ModelNo", modelNo);
            ProductEosDO productEosDO = productEosMapper.selectOne(eosDOQueryWrapper);

            if (productEosDO == null) {
                return ResultVo.failure("暂无数据.");
            }

            productCashierVO.setModelNo(modelNo);
            productCashierVO.setEosType(StringUtils.isBlank(productCashierVO.getEosType()) ? "" : productCashierVO.getEosType());

            if (productCashierVO.getEosType() != null) {
                if (productCashierVO.getEosType().equals("1")) {
                    productCashierVO.setEosTypeName("切换对象");
                } else if (productCashierVO.getEosType().equals("2")) {
                    productCashierVO.setEosTypeName("停止销售");
                }
            }
            productCashierVO.setModelNoRecommend(StringUtils.isBlank(productCashierVO.getModelNoRecommend()) ? "" : productCashierVO.getModelNoRecommend());

            list.add(productCashierVO);
        }
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<List<ProductForShikomiVO>> listProductInfoForShikomi(List<String> modelNos) {

        if (PublicUtil.isEmpty(modelNos)) {
            return ResultVo.failure("请输入型号");
        }

        List<ProductForShikomiVO> voList = new ArrayList<>();
        ProductForShikomiVO shikomiVO = null;
        Boolean eosModel = true;

        for (String modelNo : modelNos) {
            QueryWrapper<ProductEosDO> query = new QueryWrapper<>();
            query.eq("ModelNo", modelNo);
            query.eq("is_deleted", 0);
            // 查是否为eos
            ProductEosDO productEosDO = productEosMapper.selectOne(query);
            if (productEosDO == null) {
                eosModel = false;
            }
            // 查是否为bin
            ResultVo<Boolean> model = binServiceFeignApi.isBinModel(modelNo);

            QueryWrapper<ProductDeliveryDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("modelNo", modelNo);
            queryWrapper.eq("is_deleted", 0);
            queryWrapper.eq("isPrimary", 1);

            // 查供应商和原产地
//            List<ProductDeliveryDO> list = productDeliveryMapper.selectList(queryWrapper);
            ProductDeliveryDO deliveryDO = productDeliveryMapper.selectOne(queryWrapper);
            // bug 10602  WuJiaWen 2023/04/26
            if (deliveryDO == null) {
                deliveryDO = new ProductDeliveryDO();
            }
            shikomiVO = new ProductForShikomiVO();
            shikomiVO.setSupplier(deliveryDO.getSupplyId());
            ResultVo<String> supplierName = commonServiceFeignApi.getSupplierName(shikomiVO.getSupplier());
            shikomiVO.setSupplierName(supplierName.getData());
            shikomiVO.setOrigin(deliveryDO.getOrgCountryId());
            ResultVo<String> originName = commonServiceFeignApi.getSupplierName(shikomiVO.getOrigin());
            shikomiVO.setOriginName(originName.getData());
            shikomiVO.setEosModel(eosModel);
            shikomiVO.setBinModel(model.getData());
            shikomiVO.setModelNo(modelNo);
            voList.add(shikomiVO);
        }
        return ResultVo.success(voList);
    }

    /**
     * 导入中国工厂可生产清单
     *
     * @return
     */
    @Override
//    @Transactional
    public ResultVo<String> importCNMProductModel() {
//        Object o = redisManager.get("ops:CNMModel:updateTime");
//        Date lastUpdateTime = null;
//        if (o != null) {
//            lastUpdateTime = DateUtil.stringToDateTime(o.toString());
//        }
        Date updateTime = DateUtil.getNow();

        QueryWrapper<OpsTModelAvailableToSales> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("UpdateTime");
//        queryWrapper.ge(PublicUtil.isNotEmpty(lastUpdateTime),"UpdateTime", lastUpdateTime);
        List<OpsTModelAvailableToSales> list = salesMapper.selectList(queryWrapper);

        try {
            for (OpsTModelAvailableToSales sales : list) {
                QueryWrapper<OpsShareTModelAvailableToSalesDO> query = new QueryWrapper<>();
                query.eq("SerialID", sales.getSerialID());
                OpsShareTModelAvailableToSalesDO salesDO = shareSalesMapper.selectOne(query);
                if (salesDO == null) {
                    OpsShareTModelAvailableToSalesDO toSalesDO = BeanCopyUtil.copy(sales, OpsShareTModelAvailableToSalesDO.class);
                    toSalesDO.setUpdateTime(new Date());
                    toSalesDO.setStatusCode(0);
                    toSalesDO.setDelflag(0);
                    shareSalesMapper.insert(toSalesDO);
                } else {
                    OpsShareTModelAvailableToSalesDO toSalesDO = BeanCopyUtil.copy(sales, OpsShareTModelAvailableToSalesDO.class);
                    toSalesDO.setUpdateTime(toSalesDO.getUpdateTime());
                    toSalesDO.setDelflag(0);
                    shareSalesMapper.update(toSalesDO, query);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.failure(e.getMessage());
        }

//        LambdaQueryWrapper<OpsShareTModelAvailableToSalesDO> query = new LambdaQueryWrapper<>();
//        query.le(OpsShareTModelAvailableToSalesDO::getUpdateTime, updateTime);
//        OpsShareTModelAvailableToSalesDO salesDO = new OpsShareTModelAvailableToSalesDO();
//        salesDO.setDelflag(1);
//        shareSalesMapper.update(salesDO, query);

        Date now = DateUtil.getNow();
        String date = DateUtil.dateToDateTimeString(now);
        redisManager.set("ops:CNMModel:updateTime", date);
        return ResultVo.success("", "导入");
    }

    @Override
    public ResultVo<ProductPriceDTO> getPriceMast(String modelNo) {
        ProductPriceDTO modelData = productMapper.getModelData(modelNo);
        return ResultVo.success(modelData);
    }

    @Override
    public ProductRemark getProductRemarkByModelNo(String modelNo) {
        final String key = "ops:product:minPackageQty";
        Object obj = redisManager.hGet(key, modelNo);
        ProductRemark rtnVal = new ProductRemark();
        if (Objects.nonNull(obj)) {
            if ("".equals(obj.toString())) {
                rtnVal = null;
            } else {
                rtnVal = JSONObject.parseObject(obj.toString(), ProductRemark.class);
            }
        } else {
            rtnVal = productMapper.getProductRemarkByModelNo(modelNo);
            if (Objects.nonNull(rtnVal)) {
                redisManager.hPut(key, modelNo, JSONObject.toJSONString(rtnVal));
            } else {
                redisManager.hPut(key, modelNo, ""); //以防止没有数据，继访问数据库。
            }
            redisManager.expire(key, 30 * 60);//保留30分钟。
        }
        return rtnVal;
    }

    /**
     * true 拦截  false 不拦截
     *
     * @param modelNo
     * @param customerNo
     * @param endUser
     * @return
     */
    @Override
    public ResultVo<Boolean> getIsProductRestrict(String modelNo, String customerNo, String endUser) {
        if (StringUtils.isBlank(modelNo)) {
            return ResultVo.success(false, "型号不可为空");
        }

        if (StringUtils.isBlank(customerNo) && StringUtils.isBlank(endUser)) {
            return ResultVo.success(false, "客户/最终用户,两者不可都为空.");
        }

        String custNo = StringUtils.isBlank(customerNo) ? "" : customerNo;
        String userNo = StringUtils.isBlank(endUser) ? "" : endUser;

        // 查看是否限制品
        LambdaQueryWrapper<ProductRestrictDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ProductRestrictDO::getIsDeleted, 0)
                .eq(ProductRestrictDO::getModelNo, modelNo);
        Integer num = productRestrictMapper.selectCount(queryWrapper);
        if (num > 0) {
            int count = productRestrictCustomerWhitelistMapper.selectCustomerModelCount(modelNo, custNo, userNo);
            if (count == 0) {
                return ResultVo.success(true);
            }
        }
        return ResultVo.success(false);
    }

    @Override
    public void importGPProductModel() {
        productMapper.importGPProductModel();
    }

    @Override
    public ResultVo<Boolean> isEosModelNo(String modelNo) {
        if (StringUtils.isBlank(modelNo)) {
            return ResultVo.success(Boolean.FALSE);
        }
//        LambdaQueryWrapper<ProductEosDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(ProductEosDO::getModelNo, modelNo);
//        queryWrapper.eq(ProductEosDO::getIsDeleted, 0);
//        Integer count = productEosMapper.selectCount(queryWrapper);
//        if (Optional.ofNullable(count).orElse(0) > 0) {
//            return ResultVo.success(Boolean.TRUE);
//        } else {
//            return ResultVo.success(Boolean.FALSE);
//        }
        LambdaQueryWrapper<ProductDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ProductDO::getModelNo, modelNo).eq(ProductDO::getIsDeleted, 0);
        ProductDO productDO = productMapper.selectOne(lambdaQueryWrapper);
        if (Objects.isNull(productDO) || Objects.isNull(productDO.getIsEos())) {
            return ResultVo.success(Boolean.FALSE);
        }
        return ResultVo.success(productDO.getIsEos());
    }

    @Override
    public ResultVo<Boolean> isModelOfCN(String modelNo) {

        if (StringUtils.isBlank(modelNo)) {
            return ResultVo.success(Boolean.FALSE);
        }
        LambdaQueryWrapper<ProductDeliveryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ProductDeliveryDO::getIsDeleted, 0)
                .eq(ProductDeliveryDO::getModelNo, modelNo)
                .in(ProductDeliveryDO::getSupplyId, ChinaSuppplierEnum.getAllCNSupplier());
        Integer count = productDeliveryMapper.selectCount(queryWrapper);
        if (Optional.ofNullable(count).orElse(0) > 0) {
            return ResultVo.success(Boolean.TRUE);
        } else {
            return ResultVo.success(Boolean.FALSE);
        }

    }

    @Override
    public ResultVo<ProductPhysicsVO> getWeightByModelNo(String modelNo) {
        LambdaQueryWrapper<ProductPhysicsDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductPhysicsDO::getModelNo, modelNo);

        ProductPhysicsDO physicsDO = productPhysicsMapper.selectOne(queryWrapper);
        if (physicsDO == null) {
            return ResultVo.failure("未查询到改型号信息");
        }
        ProductPhysicsVO physicsVO = BeanCopyUtil.copy(physicsDO, ProductPhysicsVO.class);
        return ResultVo.success(physicsVO);
    }

    @Override
    public ResultVo<List<String>> checkAndReturnErrorModel(List<String> modelNoList) {
        if (CollectionUtils.isEmpty(modelNoList)) {
            return ResultVo.success(Collections.emptyList(), "校验型号为空");
        }
        LambdaQueryWrapper<ProductDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProductDO::getModelNo);
        queryWrapper.in(ProductDO::getModelNo, modelNoList);
        List<Object> productNoList = productMapper.selectObjs(queryWrapper);
        List<String> errorModelNoList = modelNoList.stream().filter(a -> !productNoList.contains(a)).collect(Collectors.toList());
        return ResultVo.success(errorModelNoList);
    }
}
