package com.sales.ops.service.wmOrder;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsPco;
import com.sales.ops.db.entity.OpsPcoItem;
import com.sales.ops.db.entity.OpsPcoItemInventory;
import com.sales.ops.dto.inventory.CrePcoByInventoryDto;
import com.sales.ops.dto.inventory.WmPCOConfirmDto;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.order.OpsPcoAddItemDto;
import com.sales.ops.dto.order.OpsPcoAndItemAndItemInventoryDto;
import com.sales.ops.dto.order.OpsWarehousePcoDto;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.InventoryTableTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：加工指令
 * @date ：Created in 2021/9/28 11:42
 */
public interface OpsPcoService {

    PageInfo<OpsPco> findByExample(PageModel<OpsWarehousePcoDto> pageModel);

    //查询加工子型号
    List<OpsPcoItem> getPcoItem(String orderId, String orderItem, Integer num);//todo chai

    List<OpsPcoItemInventory> deletePcoItemInventoryByInventoryId(Long inventoryId, String inventoryTable) throws OpsException;

    List<OpsPcoItem> selectItemBypcoId(String pcoId);//查询明细

    OpsPco selectPcoBypcoId(String pcoId);//查询明细

    OpsPco findPcoByOrder(String OrderId, String OrderItem, Integer num) throws OpsException;
    @Deprecated
    OpsPco findPcoByOrder(String OrderId, String OrderItem) throws OpsException;//todo chai

    List<OpsPcoItemInventory> selectItemInventoryBypcoId(String pcoId, Integer pcoItem, InventoryTableTypeEnum inventoryTableTypeEnum);

    OpsPcoItem getPcoItemByPcoIdAndItem(String pcoId, Integer pcoItem) throws OpsException;

    List<OpsPcoItemInventory> findPcoItemInventoryByPcoIdAndItem(String pcoId, Integer pcoItem) throws OpsException;

    List<OpsPcoItemInventory> findPcoItemInventoryByPcoIdAndItemAndTableType(String pcoId, Integer pcoItem, InventoryTableTypeEnum inventoryTableTypeEnum) throws OpsException;

    OpsPcoItemInventory findPcoItemInventory(String pcoId, Integer pcoItem, Integer sortnum) throws OpsException;

    OpsPcoItemInventory findPcoItemInventoryById(Long id) throws OpsException;

    void releasePreQtyInventoryAndDelPcoItemInventoryId(Long id, UserDto userDto) throws OpsException;

    OpsPco crePco(OpsPco opsPco, List<OpsPcoItem> opsPcoItemList, List<OpsPcoItemInventory> pcoInventoryList, UserDto userDto) throws OpsException;

    OpsPco CreatePco(OpsPco opsPco, List<OpsPcoItem> opsPcoItemList, List<OpsPcoItemInventory> pcoInventoryList, UserDto userDto,Map<Long, Boolean> riskMap) throws OpsException;

    List<OpsPco> CreatePcoForDispatch(List<CrePcoByInventoryDto> pcoList, UserDto userDto, String dlvEntire, Map<Long, Boolean> riskMap) throws OpsException;

    List<OpsPco> GetPcolistByOrder(String OrderId, String OrderItem) throws OpsException;//todo chai

    void insertInventoryItem(OpsPcoItemInventory itemInventory) throws OpsException;

    void CancelPco(Long id, String pcoId, CancelForOrderDto CancelForOrderDto) throws OpsException;

    void updatePreQtyForPco(OpsPco opsPco, List<OpsPcoItemInventory> pcoInventoryList, UserDto userDto) throws OpsException;

    void delPcoByPrimaryKey(Long id, String pcoId, String orderId, String orderItem, UserDto userDto) throws OpsException;

    void Confirm(OpsPco opsPco, List<OpsPcoItemInventory> pcoInventoryList, UserDto userDto,Map<Long, Boolean> riskMap) throws OpsException;

    OpsPcoAddItemDto findPcoToWms(String pcoId);

    OpsPcoAndItemAndItemInventoryDto findPcoAndPcoItemByOrderIdAndOrderItem(String orderId, String orderItem);//todo chai

    OpsPcoAndItemAndItemInventoryDto findPcoAndPcoItemByPcoId(String pcoId);

    List<OpsPcoItemInventory> getOpsPcoItemInventoryByInventoryId(long InventoryId, InventoryTableTypeEnum inventoryTableTypeEnum);

    List<OpsPcoItemInventory> getOpsPcoItemInventoryByPcoId(String pcoId,Integer pcoItem);

    int updatetOpsPcoItemInventory(Long id, Long version, Integer useQty, Long inventoryId, InventoryTableTypeEnum inventoryTableTypeEnum, UserDto userDto);

    void updateOpsPcoItemInv(Long id, Long version, Integer useQty, Long inventoryId, String tableType, UserDto userDto) throws OpsException;

    void updatetOpsPcoItemInventoryRcv(Long id, Long version, Long inventoryId, InventoryTableTypeEnum inventoryTableTypeEnum, Long fromInventoryId, InventoryTableTypeEnum fromInventoryTableType, String userName);

    void updatetOpsPcoItemInventoryByKey(OpsPcoItemInventory opsPcoItemInventory);

    void updatetOpsPcoItem(Long id, String waitType, UserDto userDto);

    void updatePcoItemQty(Long id, Integer subQty, UserDto userDto)  throws OpsException;

    void updatePcoItemInvQty(Long id, Integer subQty, UserDto userDto) throws OpsException;

    void updatetOpsPco(Long id, Integer isWms, UserDto userDto) throws OpsException;

    //5.6 组装确认回传
    String wmPCOConfirm(WmPCOConfirmDto param) throws OpsException;

    void updatePcoItemWaitTypeForItem(String pcoId, int item, String waitType, String modifier) throws OpsException;

    int deletePcoItemInventoryByPrimaryKeySelective(Long id, UserDto userDto) throws OpsException;

    void updatePcoItemToExcepiton(Long id);

    List<OpsPcoItem> findPcoItemByPcoId(String pcoId) throws OpsException;

    OpsPcoItem getPcoItemByPcoId(String pcoId,Integer item) throws OpsException;


    OpsPco getPcoByPcoId(String pcoId) throws OpsException;

    List<OpsPcoItemInventory> getPcoItemInventoryByPcoId(String pcoId) throws OpsException;

    Long countPcoItemInvByPcoIdItem(String pcoId, Integer pcoItem) throws OpsException;

    void updatePcoItemIsCrossByPcoItem(String pcoId, Integer item, boolean cross);

    void updateOpsPcoForCrossRoId(String pcoId, String roId);

    void insertPcoItemInventory(OpsPcoItemInventory pcoItemInventory)  throws OpsException;

    /**
     * 获取DoItemInventory来源DO关联关系
     * @param pcoId
     * @param pcoItem
     * @param srcInventoryId
     * @param inventoryTableTypeEnum 当前行类型（不是src_InventoryTableType)
     * @return
     * @throws OpsException
     */
    OpsPcoItemInventory getOpsPcoItemInventoryBySrcInventoryId(String pcoId, Integer pcoItem, Long srcInventoryId, InventoryTableTypeEnum inventoryTableTypeEnum) throws OpsException;


}
