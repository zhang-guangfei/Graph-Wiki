package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.adapter.*;
import com.smc.smccloud.model.delivery.DeliveryInfo;
import com.smc.smccloud.model.delivery.WarehouseSendDateVO;

import java.util.List;
import java.util.Map;

/**
 * Author: B90034
 * Date: 2022-03-14 11:19
 * Description:
 */
public interface SMSAdapterService {

    /**
     * 根据营业所代码查询可补库分库信息
     *
     * @param deptNo 营业所代码
     * @return 营业所可补库分库
     */
    List<StockCode> findSubTreasury(String deptNo);

    /**
     * 营业所所属顾客在库补库获取货物所在地库房;获取货物所在地
     *
     * @param deptNo 营业所代码
     * @return 货物所在地库房
     */
    List<StockCode> findGoodsLocation(String deptNo);

    /**
     * 根据部门代码和型号查询营业所某型号的库存信息
     *
     * @param modelNo 型号
     * @param deptNo  营业所代码
     * @return list
     */
    List<InventoryInfo> getInventoryDetailByDeptNo(String modelNo, String deptNo);

    /**
     * 返回某型号的所有库存集合列表：拆分型号库存显示使用
     *
     * @param modelNoList 型号
     * @param deptNo      营业所代码
     * @return list
     */
    List<Inventory> findInventoryList(List<String> modelNoList, String deptNo);

    /**
     * 根据型号列表、部门代码、客户代码查询有效库存
     *
     * @param modelNos   型号列表
     * @param deptNo     部门代码
     * @param customerNo 客户代码
     * @return modelNo, avaQty
     */
    ResultVo<Map<String, Integer>> getQuantityCanUseBatch(List<String> modelNos, String deptNo, String customerNo);

    /**
     * 根据部门代码、客户代码、型号集合查询可用库存
     *
     * @param infoList customerNo、modelNo
     * @param deptNo   部门代码
     * @return 客户型号可用库存
     */
    ResultVo<List<MaterialModelInfo>> getMaterialModelCanUseQtyBatch(List<MaterialModelInfo> infoList, String deptNo);

    /**
     * 根据操作人获取可补库分库库房
     *
     * @param condition
     * @param deptNo
     * @return
     */
    ResultVo<List<StockCode>> findSubTreasuryByOperator(DataAuthoritySearchCondition condition, String deptNo);

    /**
     * 根据shikomiNo获取SHIKOMI
     *
     * @param answerNo
     * @return
     */
    ResultVo<List<ShikomiInfo>> getByNoBatch(List<String> answerNo);

    /**
     * 根据型号获取shikomi是否存在
     *
     * @param conditionList
     * @return
     */
    ResultVo<List<ShikomiInfo>> findByModelNoList(List<ShikomiCondition> conditionList);

    /**
     * 查库可预占库存（仅中心库）
     *
     * @param condition 查询条件
     * @return 可预占库存信息
     */
    Map<String, List<Inventory>> getCanPreInventory(InventoryCondition condition);


    /**
     * 查询某客户某型号可用库存
     *
     * @param condition 查询条件
     * @return 可用库存
     */
    CanTransInventory getCanUseByDeptCustomer(InventoryCondition condition);

    /**
     * 获取订单型号参考货期
     *
     * @param info 订单型号信息
     * @return 订单型号参考货期
     */
    ResultVo<DeliveryInfo> getDeliveryDay(DeliveryInfo info);

    /**
     * 获取物流交货期
     */
    ResultVo<DeliveryInfo> getWarehouseSendDate(DeliveryInfo info);

//    /**
//     * 通过订单号 获取物流交货期
//     */
//    ResultVo<List<WarehouseSendDateVO>> getWarehouseSendDateByOrderNo(List<WarehouseSendDateVO> info);

    /**
     * 模糊查询型号
     *
     * @param condition 模糊查询信息
     * @param page      分页信息
     * @return 分页数据
     */
    PageInfo<InventoryFuzzySearch> getFuzzyInventory(FuzzySearchCondition condition, Page page);

    /**
     * 查询型号的拆分型号库存信息
     *
     * @param modelNo 型号
     * @return 拆分型号库存信息
     */
    ResultVo<List<ProductBomInventory>> getProductBomByModelNo(String modelNo);


    ResultVo<String> testMybatisTimeOut();
}
