package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.csstock.CsWarehouseRequest;
import com.smc.smccloud.model.csstock.CsWarehouseVO;
import com.smc.smccloud.model.csstock.WarehouseDO;
import com.smc.smccloud.model.csstock.WarehouseStockCodeVO;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-01-11 10:58
 * Description:
 */
public interface OpsWarehouseService {

    /**
     * 查询仓库信息
     *
     * @param warehouseCode 仓库代码
     * @return 仓库信息
     */
    WarehouseDO getWarehouseInfoByCode(String warehouseCode);

    /**
     * 查询营业所/代理的出库规则
     *
     * @param salesBranchId 营业所/代理代码
     * @return 出库规则
     */
    List<String> getOpsWarehouseSalesBranchConfig(String salesBranchId);

    /**
     * 查询仓库归属部门代码
     *
     * @param warehouseCode 仓库代码
     * @return 部门代码
     */
    String getWarehouseDeptNoByWarehouseCode(String warehouseCode);

    /**
     * 委托在库库房
     *
     * @param request
     * @return
     */
    ResultVo<PageInfo<CsWarehouseVO>> listCsWarehouse(CsWarehouseRequest request);

    List<WarehouseDO> listWarehouse(CsWarehouseRequest request);

    /**
     * 查询委托在库库房名称与库房代码
     *
     * @param warehouseCode
     * @param warehouseType
     * @return
     */
    ResultVo<List<WarehouseStockCodeVO>> listWarehouseStockCode(String warehouseCode, String warehouseType);

    /**
     * 库房信息
     *
     * @param agentNo
     * @param warehouseCode
     * @return
     */
     WarehouseDO getCsWarehouseDO(String agentNo, String warehouseCode);

    /**
     * 修改委托在库信息
     *
     * @param warehourseDO
     * @return
     */
    ResultVo<String> saveCsWarehouse(WarehouseDO warehourseDO);

    /**
     * 查询委托仓库代码列表
     */
    List<String> listAllWTWarehouseCode();


    WarehouseDO getWareHouseInfoByCode(String wareHouseCode);

    /**
     * 获取仓库类型
     * @param warehouseCode 仓库代码
     * @return 仓库类型
     */
    String getWarehouseType(String warehouseCode);

    /**
     * 判断是否物流中心
     * @param warehouseCode 仓库代码
     * @return boolean
     */
    boolean isMasterWarehouse(String warehouseCode);
}
