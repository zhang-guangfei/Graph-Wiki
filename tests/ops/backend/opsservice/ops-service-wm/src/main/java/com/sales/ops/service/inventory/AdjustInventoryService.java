package com.sales.ops.service.inventory;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.StockTransferPlan;
import com.sales.ops.db.entity.StockTransferPlanItem;
import com.sales.ops.dto.inventory.AdjustItemDTO;
import com.sales.ops.dto.inventory.AdjustParam;
import com.sales.ops.dto.query.OpsStockTransferPlanQO;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author C12961
 * @date 2023/4/7 18:20
 */
public interface AdjustInventoryService {

    @Transactional(rollbackFor = Exception.class)
    void finishPlanJob();

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    void handlePOFinishTransferPlan(OpsRequestpurchase requestPurchase, OpsPurchaseorder purchaseorder, String endUser, String userName) throws OpsException;

    PageInfo<StockTransferPlan> searchStockTransferPlanByPage(PageModel<OpsStockTransferPlanQO> pageModel) throws OpsException;

    List<StockTransferPlanItem> getStockTransferPlanItemList(String planNo);

    Integer delPlan(List<String> planNoList, String userName) throws OpsException;

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    void createTransferPlanForADDOrder(String orderNo, Integer itemNo, Integer splitItemNo, String endUser, String modelNo, Integer qty, String creator) throws OpsException;

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    void createTransferPlanForDelOrderPage(OpsRequestpurchase requestOrder, OpsPurchaseorder purchaseOrder,String endUser, String creator, String doId) throws OpsException;

    @Transactional(rollbackFor = Exception.class)
    void initInvTypeValue(OpsRequestpurchase requestOrder, StockTransferPlan obj) throws OpsException;

    @Transactional(rollbackFor = Exception.class)
    void delPoAfterDelTPlan(String poNo, Integer itemNo, Integer splitNo);

    // 创建调库计划
    @Transactional(rollbackFor = Exception.class)
    void createStockTransferPlan(StockTransferPlan obj) throws OpsException;

    // 执行调库计划
    @Transactional(rollbackFor = Exception.class)
    void exeStockTransferPlan(String poNo, Integer itemNo, Integer splitNo, Integer roFinQty, Boolean poFinish, String warehouseCode) throws OpsException;

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    void exeOneStockTransferPlan(StockTransferPlan obj, Integer finishQty, int status, Boolean poFinish, ResultVo<String> resultVo, Integer orderItem, String warehouseCode) throws OpsException;

    @Transactional(rollbackFor = Exception.class)
    void exeStockTransferPlanByDoId(String doId, Integer roFinQty, Boolean dbFinish, String warehouseCode) throws OpsException;

    AdjustParam adjustInventory(@RequestBody AdjustParam param);

    void adjustItemForSub(AdjustItemDTO adjustItem, boolean isAvailable, UserDto userDto) throws OpsException;

    void adjustItemForAdd(AdjustItemDTO adjustItem, UserDto userDto) throws OpsException;
}
