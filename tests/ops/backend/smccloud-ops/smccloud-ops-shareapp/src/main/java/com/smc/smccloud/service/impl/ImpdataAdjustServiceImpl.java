package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.mapper.ImpdataAdjustMapper;
import com.smc.smccloud.model.cost.ImpdataAdjustDO;
import com.smc.smccloud.model.product.ProductInfoVO;
import com.smc.smccloud.service.ImpdataAdjustService;
import com.smc.smccloud.service.ProductServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: B90034
 * Date: 2022-03-03 15:40
 * Description:
 */
@Slf4j
@Service
public class ImpdataAdjustServiceImpl implements ImpdataAdjustService {

    @Resource
    private ImpdataAdjustMapper impdataAdjustMapper;
//    @Resource
//    private StockAssemblyService stockAssemblyService;

    @Resource
    private ProductServiceFeignApi productServiceFeignApi;

    // Add by Dengdenghui 2022-11-29 bug-8822
    @Override
    public String getInvoiceNoByOrderNo(String orderNo) {
        QueryWrapper<ImpdataAdjustDO> queryWrapper = Wrappers.query();
        queryWrapper.select("Top(1) Id ", "InvoiceNo", "OrderNo");
        queryWrapper.lambda().eq(ImpdataAdjustDO::getOrderNo, orderNo);
        ImpdataAdjustDO impdataAdjustDO = impdataAdjustMapper.selectOne(queryWrapper);
        return impdataAdjustDO == null ? null : impdataAdjustDO.getInvoiceNo();
    }

    // Add by Dengdenghui 2022-11-29 bug-8822
    @DS("costdb")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> importAssemblyCostData(List<ImpdataAdjustDO> costDataList) {
        Map<String, ProductInfoVO> productInfoMap = new HashMap<>();
        ResultVo<ProductInfoVO> productInfoResultVo;
        ProductInfoVO productInfo;
        Date today = DateUtil.getToday();
        String optCode = "0";

        for (ImpdataAdjustDO costData : costDataList) {
            if (productInfoMap.containsKey(costData.getModelNo())) {
                productInfo = productInfoMap.get(costData.getModelNo());
            } else {
                // 查询型号的ECode信息
                productInfoResultVo = productServiceFeignApi.getProductInfoByModelNo(costData.getModelNo());
                if (productInfoResultVo.isSuccess() && productInfoResultVo.getData() != null) {
                    productInfo = productInfoResultVo.getData();
                    productInfoMap.put(costData.getModelNo(), productInfo);
                } else {
                    productInfo = null;
                    log.error("获取产品信息失败, modelNo = {}, productInfoResultVo = {}", costData.getModelNo(), productInfoResultVo);
                }
            }
            costData.setECode(productInfo == null ? "" : productInfo.getECode());
            costData.setOptCode(optCode);
            costData.setImpDate(today);
        }

        // 如果组换是一对一的，调出项ECode为空，可以用调入型号的ECode代替
        if (costDataList.size() == 2) {
            ImpdataAdjustDO outCostData = null;
            ImpdataAdjustDO inCostData = null;
            for (ImpdataAdjustDO costData : costDataList) {
                if (costData.getQuantity() > 0) {
                    inCostData = costData;
                } else {
                    outCostData = costData;
                }
            }
            if (inCostData != null && outCostData != null) {
                if (StringUtils.isNotBlank(inCostData.getECode()) && StringUtils.isBlank(outCostData.getECode())) {
                    outCostData.setECode(inCostData.getECode());
                }
            }
        }

        // 批量插入
        this.insertBatch(costDataList);

        return ResultVo.success("已成功写入成本数据");
    }

    /**
     * 批量插入
     */
    private void insertBatch(List<ImpdataAdjustDO> dataList) {
        List<List<ImpdataAdjustDO>> lists = ListUtils.partition(dataList, 50);
        for (List<ImpdataAdjustDO> list : lists) {
            impdataAdjustMapper.insertBatch(list);
        }
    }


//    @DS("costdb")
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public ResultVo<String> importAssemblyCostData() {
//        // 1.查询未录入成本数据的组换申请
//        List<StockAssemblyDetailView> assemblyDataList = stockAssemblyService.listNoImportCostAssemblyData().getData();
//        if (assemblyDataList.isEmpty()) {
//            return ResultVo.success("没有需要录入成本数据的组换申请");
//        }
//
//        // 2.录入组换申请成本数据
//        LambdaQueryWrapper<ImpdataAdjustDO> impdataAdjustQueryWrapper = Wrappers.lambdaQuery();
//        ImpdataAdjustDO impdataAdjustDO;
//        Map<String, ProductInfoVO> productInfoMap = new HashMap<>();
//        ResultVo<ProductInfoVO> productInfoResultVo;
//        ProductInfoVO productInfo;
//        Date now = DateUtil.getToday();
//        String optCode = "0";
//
//        for (StockAssemblyDetailView data : assemblyDataList) {
//            // 如果已录入成本则不再重复录入
//            impdataAdjustQueryWrapper.clear();
//            impdataAdjustQueryWrapper.eq(ImpdataAdjustDO::getInvoiceNo, data.getInvoiceNo())
//                    .eq(ImpdataAdjustDO::getOrderNo, data.getApplyNo())
//                    .eq(ImpdataAdjustDO::getModelNo, data.getModelNo())
//                    .eq(ImpdataAdjustDO::getStockCode, data.getDetailWarehouseCode())
//                    .eq(ImpdataAdjustDO::getModelNo, data.getModelNo())
//                    .eq(ImpdataAdjustDO::getQuantity, data.getQuantity().intValue());
//            if (impdataAdjustMapper.selectCount(impdataAdjustQueryWrapper) > 0) {
//                continue;
//            }
//            if (productInfoMap.containsKey(data.getModelNo())) {
//                productInfo = productInfoMap.get(data.getModelNo());
//            } else {
//                // 查询型号的单价
//                productInfoResultVo = productServiceFeignApi.getProductInfoByModelNo(data.getModelNo());
//                if (productInfoResultVo.isSuccess() && productInfoResultVo.getData() != null) {
//                    productInfo = productInfoResultVo.getData();
//                    productInfoMap.put(data.getModelNo(), productInfo);
//                } else {
//                    productInfo = null;
//                    log.error("获取产品信息失败, modelNo = {}, productInfoResultVo = {}", data.getModelNo(), productInfoResultVo);
//                }
//            }
//
//            impdataAdjustDO = new ImpdataAdjustDO();
//            impdataAdjustDO.setImpDate(now);
//            impdataAdjustDO.setInvoiceNo(data.getInvoiceNo());
//            impdataAdjustDO.setOrderNo(data.getApplyNo());
//            impdataAdjustDO.setStockCode(data.getDetailWarehouseCode());
//            impdataAdjustDO.setInvDesc("组换");
//            impdataAdjustDO.setECode(productInfo == null ? "" : productInfo.getECode());
//            impdataAdjustDO.setCustomerNo(data.getCustomerNo());
//            impdataAdjustDO.setModelNo(data.getModelNo());
//            impdataAdjustDO.setQuantity(data.getQuantity().intValue());
//            //impdataAdjustDO.setPrice(new BigDecimal(productInfo.getEPrice()));
//            //impdataAdjustDO.setAmount(impdataAdjustDO.getPrice().multiply(new BigDecimal(impdataAdjustDO.getQuantity().toString())));
//            impdataAdjustDO.setOptCode(optCode);
//            impdataAdjustDO.setDataSource("SA");
//            impdataAdjustMapper.insert(impdataAdjustDO);
//        }
//
//        // 3.更新组换申请状态
//        stockAssemblyService.updateImportCostAssemblyApplyStatus(assemblyDataList);
//        return ResultVo.success("已成功录入组换申请的成本数据");
//    }
}
