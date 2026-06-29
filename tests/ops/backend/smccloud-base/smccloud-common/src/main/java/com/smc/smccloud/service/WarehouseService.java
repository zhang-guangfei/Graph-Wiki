package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.OpsWarehouseSalesbranchConfigVO;
import com.smc.smccloud.model.WarehouseDO;
import com.smc.smccloud.model.warehouse.UpWarehouseDelflagVO;
import com.smc.smccloud.model.warehouse.WarehouseQueryDTO;
import com.smc.smccloud.model.warehouse.WarehouseVO;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-01-22 12:01
 * Description:
 */
public interface WarehouseService {

    //查询物体仓库信息
    ResultVo<List<WarehouseVO>> listWarehouse(WarehouseQueryDTO dto);

    ResultVo<WarehouseVO> getWarehouseInfoByCode(String warehouseCode);


    /**
     * 根据仓库代码查询仓库名称
     *
     * @param warehouseCode 仓库代码
     * @return 仓库名称
     */
    ResultVo<String> getWarehouseName(String warehouseCode);

    //查询委托在库外的仓库代码和名称
    ResultVo<List<WarehouseVO>> listWarehouseNoWT();

    ResultVo<String> getWarehouseParentCode(String warehouseCode);

    ResultVo<List<WarehouseVO>> getSubWarehouse(String warehouseCode);
    ResultVo<List<WarehouseVO>> getWarehouseByType(String wareHouseType);
    ResultVo<String> getWarehouseType(String warehouseCode);

    /**
     * 根据部门代码获取运输天数
     */
    ResultVo<Integer> getTransDayByDeptNo(String deptNo);

    // 判断是否是分库
    Boolean judgeIsSubWareHouse(String wareHouse);

    /**
     * 根据仓库类型获取仓库列表
     */
    ResultVo<List<WarehouseDO>> findWareHouseByType(String wareHouseType);

    /**
     * 获取仓库类型获取仓库代码集合
     */
    ResultVo<List<String>> getWarehouseCodeByWarehouseType(String warehouseType);

    /**
     * 检查是否物流中心
     *
     * @param warehouseCode 仓库代码
     * @return boolean
     */
    ResultVo<Boolean> isMasterWarehouse(String warehouseCode);

    /**
     * 根据仓库代码/名称 模糊查询仓库 (适用于前端ui下拉选择组件)
     */
    ResultVo<List<WarehouseDO>> findWareHouseInfoWithLike(String code,String type);

    /**
     * 营业所优先出的仓库
     * @return
     */
    ResultVo<List<OpsWarehouseSalesbranchConfigVO>> getWarehouseSalesBranchConfigForPriority();

    /**
     * 营业所优先出的物流中心
     * @return
     */
    ResultVo<List<OpsWarehouseSalesbranchConfigVO>>  getWarehouseSalesBranchConfigForPriorityByMaster();

    // 修改仓库是否删除标识
    ResultVo<String> upWarehouseDelFlag (UpWarehouseDelflagVO upinfo);
}
