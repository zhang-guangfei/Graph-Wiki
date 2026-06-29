package com.sales.ops.service.wmOrder;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.CreRoByInventoryDto;
import com.sales.ops.dto.inventory.InventoryForAdjustDto;
import com.sales.ops.dto.inventory.OpsDoDto;
import com.sales.ops.dto.inventory.OpsRoDto;
import com.sales.ops.dto.order.InventoryForTransInputDto;
import com.sales.ops.dto.order.OpsRoAddItemDto;
import com.sales.ops.dto.order.OpsWarehouseRoDto;
import com.sales.ops.dto.order.TransOrderDtoForMove;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.RoTypeEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：收货指令
 * @date ：Created in 2021/9/26 10:20
 */
public interface OpsRoService {

    PageInfo<OpsRo> findRoByPage(PageModel<OpsWarehouseRoDto> pageModel);
    OpsRo insertRo(OpsRo opsRo, OpsRoItem opsRoItem, List<OpsRoItemInventory> roItemInventoryList, UserDto userDto) throws OpsException;

    OpsRo createRoAndFinishForSalesInfo(InventoryForAdjustDto dto, OpsDoDto doDto) throws OpsException;

    void updateQtyForRo(OpsRo opsRo, OpsRoItemInventory roItemInventory, UserDto userDto) throws OpsException;

    List<OpsRo> CreateRoForDispatch(List<CreRoByInventoryDto> roList, UserDto userDto) throws OpsException;

    void updatePreQtyForRo(OpsRo opsRo, OpsRoItemInventory roItemInventory, UserDto userDto)
            throws OpsException;

    void updateRoItemQtyByDoItemId(Long opsRoItemId, Integer qty, Integer version) throws OpsException;

    List<OpsRo> createRoForAdjust(List<InventoryForAdjustDto> dtoList, UserDto userDto) throws OpsException;

    List<OpsRo> createRoForWMSAdjust(List<InventoryForAdjustDto> dtoList, UserDto userDto) throws OpsException;

    List<OpsRoDto> createRoForTrans(InventoryForTransInputDto inputDto) throws OpsException;

    void createRoForTransMove(TransOrderDtoForMove dto, OpsInventoryMove move) throws OpsException;

    void createROForPorduceV2(String orderId, String warehouseCode,
                              UserDto userDto, Boolean onlyWms)throws OpsException;

    List<OpsRo> createRoForProduceChange(List<InventoryForAdjustDto> rkList, String warehouseCode, UserDto userDto, Boolean onlyWms) throws OpsException;

    void delOpsRoOrder(Long cancelId, OpsRo opsRo, String msg, UserDto user) throws OpsException;

    OpsRoAddItemDto findRoToWms(String roId);



    String splitDBRo(OpsDo opsDBDo, Integer qty, int num) throws OpsException;

    OpsRo initRoForInvoice(String roId, String orderId, String orderItem, int num, RoTypeEnum roTypeEnum, String warehouseCode, String supplierId, String invoiceNo, Long invoiceId);

    OpsRoItem initRoItemForInvoice(String roId, int qty, String modelno, String greenCode, BigDecimal netWeight);

    OpsRoItemInventory initRoItemInventoryForInvoice(String roId, Integer roItem, Long inventoryId, Integer qty);

    OpsRo initOpsRo(String roId, OpsRequestpurchase opsRequestpurchase);

    OpsRoItem initOpsRoItem(String roid, OpsRequestpurchase opsRequestpurchase);

    /**
     * 已废弃
     * 收货扫描处理新方法
     * 按每次收货数量增已收货数
     * 2022-11-15
     *
     * @param param
     * @return
     * @throws OpsException
     */
    // String wmRoConfirmHandle(WmRoConfirmDto param) throws OpsException;

    OpsRoDto createRoForCancelSalesInfo(InventoryForAdjustDto adjustDto, OpsDoDto opsDoDto) throws OpsException;


}
