package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.utils.ExcelHelper;
import com.smc.smccloud.model.adapter.Product;
import com.smc.smccloud.model.bindata.*;
import com.smc.smccloud.model.binorder.*;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;

/**
 * @Author edp02 @Date 2021/10/6 14:25
 */
public interface BindataService {

    //查询客户BIN
    List<BindataDO> findCustomerBindataExcludeModelnos(List<String> modelnoList);

    List<BindataDO> findBindata(String modelno, Long propertyId, String warehouseCode, Integer calcType);

    List<ModelExpFreqDO> findModeExpFreq(String modelno, Integer stockType, String stockCode);

    List<BindataVO> listModelBinData(BindataRequest request);

    PageInfo<BindataVO> listBindata(BindataRequest request);

    @Async
    void asyupdAndAddBinDataV2(List<BindataExcelVO> voList, String rediskey);

    ResultVo<String> saveBindata(BindataVO info);

    void asyupdAndAddBinData(ExcelHelper excel, String rediskey);

    ResultVo<String> deleteBindata(Integer[] ids);

    ResultVo<BindataVO> getBindataByModelNo(String modelNo, String warehouseCode);

    ResultVo<List<Integer>> getBinCountByModelNo(List<String> modelNos, String warehouseCode);

    ResultVo<Map<String, BindataVO>> getBindataByModelNoAndWarehouse(String modelNo, List<String> warehouseList);

    /**
     * 判断型号是否BIN
     * @param modelNos
     * @param warehouseList
     * @return
     */
    ResultVo<Map<String, Boolean>> checkModelIsBin(List<String>modelNos,List<String> warehouseList );

    /**
     *  查询运营区分
     * @param modelNo
     * @param binType  运营区分
     * @return
     */
    ResultVo<List<BindataVO>> getBindataByModelNoAnBinType(String modelNo, String binType);

    ResultVo<List<BindataVO>> getBindataByModelNos(BinDataQueryRequest request);

    ResultVo<Boolean> isBinModel(String modelNo);

    ResultVo<List<BinModelNoVO>> isBinModelBatch(List<String> modelNos);

    void exportBinData(BindataRequest dto);

    ResultVo<String> updateProductInfo();

    ResultVo<List<BindataVO>> listBinDataForReplQty();

    /**
     * 查询客户先行在库自动补库清单
     *
     * @param request 补库客户信息
     * @return 客户自动补库清单
     */
    ResultVo<List<BindataVO>> getBinDataForAutoPreStock(BindataRequest request);

    /**
     * 查询客户BIN型号
     *
     * @param customerNo  客户代码
     * @param modelNoList 型号列表（选填）
     * @return 某客户BIN型号清单
     */
    List<BindataVO> listCustomerBinModel(String customerNo, List<String> modelNoList);

//    /**
//     * 查询客户需要补库清单
//     *
//     * @param customerNo 客户代码
//     * @return 客户补库清单
//     */
//    List<BindataVO> listCustomerBinRepleModel(String customerNo);
//
//    /**
//     * 查询中心库型号的BIN数
//     *
//     * @param modelNoList 型号列表
//     * @return 中心库型号BIN数
//     */
//    List<BindataVO> listCentralStockBinInfo(List<String> modelNoList);

    /**
     * 根据型号查询BIN信息
     *
     * @param modelNo 型号
     * @return BinInfo
     */
    List<BindataVO> listBinInfoByModelNo(String modelNo);

    /**
     * 查询型号的Bin数
     *
     * @param modelNoList 型号
     * @return BinCell
     */
    List<BindataVO> listBinCellByModelNo(List<String> modelNoList);

    /**
     * 查询BIN补库客户信息
     *
     * @param condition DataAuthoritySearchCondition
     * @return customerNo, customerName
     */
    List<Map<String, Object>> listBinCustomerInfo(DataAuthoritySearchCondition condition);

    /**
     * 取消客户BIN自动补库
     */
    boolean cancelCustomerBinAutoReple(String warehouseCode, String customerNo, List<String> modelNoList);

    /**
     * 更新客户BIN的自动补库状态
     *
     * @param customerNo  客户代码
     * @param modelNoList 型号
     * @param status      状态
     * @return boolean
     */
    boolean updateCustomerBinAutoRepleStatus(String warehouseCode, String customerNo, List<String> modelNoList, int status);

    /**
     * 根据部门和型号查询BIN品和GSS品
     *
     * @param warehouseCodeList 仓库代码
     * @param modelNoList   型号
     * @return list
     */
    List<Product> listBinAndGSS(List<String> warehouseCodeList, List<String> modelNoList);

//    /**
//     * 型号bin或gss品信息、bin安全库存数及有效库存数量
//     *
//     * @param deptNo      部门代码
//     * @param modelNoList 型号列表
//     * @param customerNo  客户代码（可选）
//     */
//    List<Product> listProductInfo(List<String> modelNoList, String deptNo, String customerNo);

    // ResultVo<List<BindataVO>> getBindataInfo(CsModelQryRequest csModelQryRequest);

    /**
     * 获取Bin频率信息
     */
    ResultVo<List<ModelExpFreqVO>> getModelExpFreq(ModelExpFreqRequest request);

    ResultVo<List<ModelExpFreqVO>> getModelExpFreqWithRiskLevel(ModelExpFreqRequest request);
    /**
     * bug11986,订单删除需要增加风险验证，新增获取所有大仓的 AvgQtyOf8字段
     * @param modelNo
     * @return
     */
    ResultVo<List<ModelExpFreqVO>> getMasterFreq(String modelNo);

    ResultVo<List<ModelExpFreqVO>> getCustomerModelFreq(CustomerExpFreqRequest request);

    String getDeptPriorityStock(String deptNo);

    List<String> listDeptPriorityStock(String deptNo);

    List<String> getWTWarehouseByCustomerNo(String customerNo);

    void dowmBinDataImport();
}
