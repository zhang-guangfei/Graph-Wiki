package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.product.*;

import java.util.List;

public interface ProductService {

    /**
     * 根据型号查询该型号的最小包装数
     *
     * @param modelNo 型号
     * @return 最小包装数 (if null, return 1)
     */
    ResultVo<Integer> getMinPackUnitByModelNo(String modelNo);

    /**
     * 根据型号查询产品信息
     *
     * @param modelNo 型号
     * @return 产品信息
     */
    ResultVo<ProductInfoVO> getProductInfoByModelNo(String modelNo);

    /**
     * 查询型号的英文名称
     * @param modelNo 型号
     * @return 型号的英文名称
     */
    ResultVo<String> getProductEnglishName(String modelNo);

    ResultVo<List<ProductCashierVO>> listProductEos(List<String> modelNos);

    ResultVo<List<ProductForShikomiVO>> listProductInfoForShikomi(List<String> modelNos);

    ResultVo<String> importCNMProductModel();

    ResultVo<ProductPriceDTO> getPriceMast(String modelNo);

    /**
     * 根据型号查询产品备注信息
     *
     * @param modelNo 型号
     * @return 产品备注信息
     */
    ProductRemark getProductRemarkByModelNo(String modelNo);


    /**
     * 查询型号是否
     * @param modelNo
     * @param customerNo
     * @param endUser
     * @return
     */
    ResultVo<Boolean> getIsProductRestrict(String modelNo,String customerNo,String endUser);


    /**
     * 定时任务同步 importGPProductModel  调用sync_prodmast_gp存储过程
     */
    void importGPProductModel();


    // 是否收敛品
    ResultVo<Boolean> isEosModelNo(String modelNo);

    ResultVo<Boolean> isModelOfCN(String modelNo);

    ResultVo<ProductPhysicsVO> getWeightByModelNo(String modelNo);

    /**
     * 校验型号并返回错误的型号
     */
    ResultVo<List<String>> checkAndReturnErrorModel(List<String> modelNoList);
}
