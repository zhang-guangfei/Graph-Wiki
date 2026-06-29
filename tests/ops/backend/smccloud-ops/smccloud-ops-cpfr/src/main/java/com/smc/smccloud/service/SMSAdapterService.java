package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.model.adapter.*;

import java.util.List;
import java.util.Map;

/**
 * Author: B90034
 * Date: 2022-03-17 13:55
 * Description:
 */
public interface SMSAdapterService {

    /**
     * 根据客户代码和型号获取型号的BIN数、通用在库和专备
     * （北京bin数量、上海bin数量、广州bin数、通用在库、顾客在库数量）
     * @param condition 客户代码和型号列表
     * @return 型号的BIN数、通用在库和专备
     */
    Map<String, BinModelInfo> findBinModelInfo(BinModelCondition condition);

    /**
     * 查询需要BIN补库的客户
     *
     * @param condition
     * @return
     */
    List<AutoBinInfo> findAutoBinCustomerInfo(DataAuthoritySearchCondition condition);

    /**
     * 查询某客户的自动补库信息 （建议补库数量、Bom号、项目号）
     *
     * @param condition
     * @param customerNo 客户代码
     * @return 自动补库信息
     */
    List<BinModelInfo> findAutoBinInfoByCustomer(DataAuthoritySearchCondition condition, String customerNo);

    /**
     * 取消自动补库
     *
     * @param condition 取消范围
     * @return 取消结果
     */
    boolean cancelCustomerBinAutoReple(UpdateAutoStatusCondition condition);

    /**
     *
     * @param condition
     * @return
     */
    boolean updateCustomerBinAutoRepleStatus(UpdateAutoStatusCondition condition);

    /**
     * 根据型号获取Bin信息
     *
     * @param modelNo 型号
     * @return 型号的Bin信息
     */
    List<BinInfo> getBinInfoByModelNo(String modelNo);

    /**
     * 查询型号的BIN品和GSS品
     *
     * @param condition 查询条件
     * @return Map
     */
    ResultVo<Map<String, List<Product>>> listModelBinOrGSSInfo(ProductCondition condition);

    /**
     * 查询型号的BIN品和GSS品
     *
     * @param condition 查询条件
     * @return List
     */
    ResultVo<List<Product>> listBinOrGSS(ProductCondition condition);

    /**
     * 型号bin或gss品信息、bin安全库存数及有效库存数量
     * @param condition 查询条件
     * @return List
     */
    ResultVo<List<Product>> productInfo(ProductCondition condition);


    /**
     *  专用补库时查询在库信息
     * @param dto
     * @return
     */
    ResultVo<List<StockInfoForReplVO>> listStockForRepl(StockQueryForReplDTO dto);
}
