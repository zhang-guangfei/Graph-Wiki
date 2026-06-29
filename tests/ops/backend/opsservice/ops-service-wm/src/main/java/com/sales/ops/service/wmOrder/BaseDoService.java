package com.sales.ops.service.wmOrder;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsDoItem;
import com.sales.ops.db.entity.OpsDoItemInventory;
import com.sales.ops.dto.inventory.OpsDoDto;
import com.sales.ops.enums.DoSourceEnum;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.enums.InventoryTableTypeEnum;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Do查询接口
 * 1.指令ID查询
 * 2.十位单号批量查询
 * 3.十三位单号+指令类型通用查询
 * 4.十三位单号固定指令类型查询
 * 5.十四位单号+指令类型 通知发货查询
 * 6.其他查询
 * <p>
 * C12961 2025-05-15
 */
public interface BaseDoService {


    /***************** 1.指令ID 查询 *****************************/

    boolean existsDoByDoId(String doId);

    @Nullable
    OpsDo getDoByDoId(String doId) throws OpsException;

    @Nullable
    OpsDoItem getDoItemByDoId(String doId) throws OpsException;

    Long countDoItemInvByDoId(String doId);

    OpsDoItemInventory getDoItemInventoryById(long id);

    List<OpsDoItemInventory> getDoItemInventoryByDoId(String doId);

    List<OpsDoItemInventory> getDoItemInventoryByDoIdOrderByInventoryId(String doId);

    List<OpsDoItemInventory> getDoItemInventoryByDoId(String doId, InventoryTableTypeEnum inventoryTableTypeEnum);

    List<OpsDoItemInventory> getDoItemInventoryByDoId(String doId, String inventoryTableType, Integer greaterThanUseQty);

    List<OpsDoItemInventory> getDoItemInventoryByInventoryId(long InventoryId, InventoryTableTypeEnum inventoryTableTypeEnum);

    OpsDoDto getOpsDoDto(String doId) throws OpsException;

    /***************** 2.十位单号 查询*****************************/

    List<OpsDo> findDoListByOrderNo(String orderNo);

    List<OpsDo> findDoListByOrder(String orderNo, Integer item);

    List<OpsDo> findAllJYCKByOrder(String OrderId, String OrderItem);

    List<OpsDo> findAllDBCKByOrder(String OrderId, String OrderItem);

    OpsDo findByOrderOrderByNumMaxOne(String OrderId, String OrderItem, DoTypeEnum doTypeEnum) throws OpsException;

    /***************** 3.十三位单号+指令类型 通用查询*****************************/

    // 通用查询 通知发货+非通知发货
    List<OpsDo> findDoListByOrder(String OrderId, String OrderItem, Integer num, DoTypeEnum doTypeEnum) throws OpsException;

    // 通用查询 通知发货+非通知发货
    List<OpsDo> findDoListByOrder(String orderId, String orderItem, Integer num, Boolean assModelFlag, DoTypeEnum doTypeEnum) throws OpsException;


    /***************** 4.十三位单号 专门类型查询*****************************/

    // 返回单条指令，返回值允许为空
    @Nullable
    OpsDo findJYCKByOrder(String OrderId, String OrderItem, Integer num, DoSourceEnum sourceEnum) throws OpsException;

    // 返回值不允许为空
    OpsDo getJYCKByOrder(String OrderId, String OrderItem, Integer num, DoSourceEnum sourceEnum) throws OpsException;

    // 【非通知发货】 返回单条指令，返回值允许为空
    @Nullable
    OpsDo findDBCKByOrder(String OrderId, String OrderItem, Integer splitNo, DoSourceEnum sourceEnum) throws OpsException;

    List<OpsDo> findDBCKListByOrder(String OrderId, String OrderItem, Integer num, DoSourceEnum sourceEnum) throws OpsException;
    // 【非通知发货】 返回单条指令，返回值不允许为空
    OpsDo getDBCKByOrder(String OrderId, String OrderItem, Integer num, DoSourceEnum sourceEnum) throws OpsException;

    // 查询单条指令，返回值允许为空
    @Nullable
    OpsDo findCGDBCKByOrder(String OrderId, String OrderItem, Integer num) throws OpsException;

    /***************** 5.十四位单号+指令类型 通知发货查询*****************************/

    // 通知发货
    List<OpsDo> findDoListByOrder(String OrderId, String OrderItem, Integer num, Integer assNum, DoTypeEnum... doTypeEnum) throws OpsException;

    // 通知发货
    List<OpsDo> findDBCKByOrder(String OrderId, String OrderItem, Integer num, Integer assNum, Boolean assModelFlag);

    /***************** 6.其他查询*****************************/

    Boolean checkSpeModelNoSpeWarehouse(String modelNo) throws OpsException;

    List<OpsDo> findAllTypeDBCKListByOrder(String OrderId, String OrderItem, Integer num, DoSourceEnum sourceEnum) throws OpsException;
}
