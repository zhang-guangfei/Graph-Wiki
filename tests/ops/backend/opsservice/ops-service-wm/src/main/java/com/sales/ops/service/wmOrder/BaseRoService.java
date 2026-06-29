package com.sales.ops.service.wmOrder;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsRo;
import com.sales.ops.db.entity.OpsRoItem;
import com.sales.ops.db.entity.OpsRoItemInventory;
import com.sales.ops.dto.inventory.OpsRoDto;
import com.sales.ops.dto.util.UserDto;

import java.util.List;

/**
 * @author C12961
 * @date 2022/3/31 17:57
 */
public interface BaseRoService {
    List<OpsRo> isExchangeOrder(String invoiceNo);


    boolean existsRoByRoId(String roId);

    Long countRoForNotSendToWmsByInvoiceNo(String invoiceNo, Long invoiceId);

    List<OpsRo> findRoByOrderNo(String orderId, Integer orderItem);

    List<OpsRo> findRoByOrderNo(String orderId, String orderItem);

    List<OpsRo> findRoByOrderNo(String orderId);

    List<OpsRo> findRoByOrderNoAndTHType(String orderId, String orderItem, Integer num);

    List<OpsRo> findRoByInvoiceNo(String invoiceNo);

    List<OpsRo> findRoByInvoiceNoAndInvoiceId(String invoiceNo, Long invoiceId);

    OpsRo findRoByRoId(String roId) throws OpsException;

    OpsRoItem findRoItemByRoId(String roId) throws OpsException;

    List<OpsRoItem> findRoItemsByRoId(String roId);

    List<OpsRoItemInventory> findRoItemInventoryByRoId(String roId);

    List<OpsRoItemInventory> findRoItemInventoryByInventoryId(long InventoryId);

    List<OpsRo> findRoByOrderItemNum(String orderId, Integer item, Integer num, Integer assNum);

    List<OpsRo> findRoByOrderItemNum(String orderId, Integer item, Integer num, Integer assNum, Integer extNum);

    List<OpsRo> findRoByOrderItemNum(String orderId, Integer item, Integer num, Integer assNum, String roType);

    List<OpsRo> findRoByOrderItemNum(String orderId, Integer item, Integer num, Integer assNum, Integer extNum, String roType);

    List<OpsRo> findRoByOrderItemNumExtNUM(String orderId, Integer item, Integer num, Integer assNum, Integer extNum, String roType);

    List<OpsRo> findDBRoByDBDo(OpsDo opsDo);

    OpsRo getRoByRoId(String roId) throws OpsException;

    int insertOpsRo(OpsRo ro);

    int insertOpsRoItem(OpsRoItem roItem);

    int deleteRoItemInventoryByRoId(String roId);

    void updateRoById(OpsRo opsRo) throws OpsException;

    int updateRoItemById(OpsRoItem opsRoItem) throws OpsException;

    int insertOpsRoItemInventory(OpsRoItemInventory roItemInventory);

    OpsRoDto insertRo(OpsRoDto opsRoDto);

    int deleteRoItem(Long id, UserDto userDto) throws OpsException;

    int updateOpsRoItemRecQty(String roId, int recQty, int version, String userName) throws OpsException;

    int updateOpsRoStatus(String roId, int status, int version, String userName);

    int updateOpsRoWarehouse(String roId, int version, String warehouseCode, String userDto);

    void updateOpsRoSignForInvoiceNo(String invoiceNo, Long invoiceId, String userName);

}
