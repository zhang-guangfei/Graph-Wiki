package com.sales.ops.service.inventory;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.InventoryReport;
import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.dto.inventory.InventoryDTO;
import com.sales.ops.dto.inventory.InventoryForTransDto;
import com.sales.ops.dto.inventory.InventoryReportExcel;
import com.sales.ops.dto.order.OpsWTInventoryDTO;
import com.sales.ops.dto.order.TransOrderQueryMoveParam;
import com.sales.ops.dto.order.TransOrderQueryMoveResult;
import com.sales.ops.dto.query.InventoryQO;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.InventoryTableTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @author C02483
 * @version 1.0
 * @description: 库存服务：出库算法、库存操作
 * @date 2021/9/10 15:51
 */
public interface OpsInventoryService {

    Long getInventoryIdForTransOrder(String warehouse, String modelno, String inventoryTypeCode, String customerNo, String ppl, String projectCode, String groupCustomer, String salesInfo, UserDto userDto) throws OpsException;

    Map<OpsInventory, Integer> findInventoryListByTransOrderInfo(InventoryForTransDto transDto) throws OpsException;

    List<TransOrderQueryMoveResult> findAvailableMoveForTransOrder(TransOrderQueryMoveParam dto) throws OpsException;

    List<OpsWTInventoryDTO> findInventoryIdAndInventoryTypeByDoId(String doId) throws OpsException;

    List<OpsWTInventoryDTO> findInventoryIdAndInventoryTypeByPcoId(String pcoId, Integer PcoItem) throws OpsException;

    InventoryDTO getInventoryDto(InventoryTableTypeEnum tableType, Long inventoryId) throws OpsException;

    PageInfo<InventoryReport> searchByPage(PageModel<InventoryQO> pageModel) throws OpsException;


    List<InventoryReportExcel> download(InventoryQO condition) throws OpsException;
}
