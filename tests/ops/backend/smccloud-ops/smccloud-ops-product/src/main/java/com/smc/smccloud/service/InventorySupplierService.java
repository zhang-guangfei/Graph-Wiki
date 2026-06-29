package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.inventory.InventorySupplierDO;
import com.smc.smccloud.model.inventory.InventorySupplierVO;
import com.smc.smccloud.model.inventory.InventoryVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author smc
 * @since 2022-01-25
 */
public interface InventorySupplierService {

    //int insertOrUpdate(InventorySupplierVO inventorySupplierVO);

    ResultVo<String> importJPStockData(List<InventorySupplierVO> list);

    ResultVo<String> parseJPSTockFile(MultipartFile file);


    /**
     * 查询某型号的供应商库存
     *
     * @param modelNos 型号
     * @return 库存信息
     */
    List<InventorySupplierDO> listModelSupplierInventory(List<String> modelNos);

    /**
     * 查询型号的可订货（可用）供应商库存（bug-11913）
     *
     * @param modelNos 型号列表
     * @return 型号的可订货（可用）供应商库存
     */
    List<InventoryVO> listModelSupplierCanOrderQty(List<String> modelNos);

    /**
     * 解析http://192.168.168.4:9999/JP-CN/ 日本供应商库存
     */
    ResultVo<String> paseJPInventorySupplier();

    /**
     * 导入更新广州制造库存
     */
    ResultVo<String> impGPInventory();
}
