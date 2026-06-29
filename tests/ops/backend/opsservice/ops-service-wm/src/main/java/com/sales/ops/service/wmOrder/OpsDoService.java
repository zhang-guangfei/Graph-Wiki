package com.sales.ops.service.wmOrder;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.order.*;
import com.sales.ops.dto.query.OpsDeliveryOrderQO;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.DoSourceEnum;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.enums.InventoryTableTypeEnum;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OpsDoService {


    void calculateDoReadyDate(String doId) throws OpsException;

    void updateDOisWMSbyID(Long id);

    //do查询分页
    PageInfo<OpsDo> searchDoByPage(PageModel<OpsDeliveryOrderQO> pageModel) throws OpsException;

    //收集do指令
    List<OpsDo> CreateDoForDispatch(List<CreDoByInventoryDto> doList, UserDto userDto,Map<Long, Boolean> riskMap) throws OpsException;

    //预约情报
    OpsDoDto createDoForCancelSalesInfo(InventoryForAdjustDto adjustDto) throws OpsException;

    //调库
    List<OpsDo> createDoForAdjust(List<InventoryForAdjustDto> dtoList, UserDto userDto) throws OpsException;

    //预约情报
    OpsDoDto CreateDoAndFinishForSalesInfo(InventoryForAdjustDto adjustDto, String orderType, DoSourceEnum doSourceEnum, DoTypeEnum doTypeEnum) throws OpsException;

    //物流调账
    List<OpsDo> createDoForWMSAdjust(List<InventoryForAdjustDto> dtoList, UserDto userDto) throws OpsException;

    //调库
    List<OpsDoDto> createDoForTrans(InventoryForTransInputDto inputDto) throws OpsException;


    void createDoForTransMove(TransOrderDtoForMove dto, OpsInventoryMove move) throws OpsException;

    //组换
    List<OpsDo> createDoForProduceChange(List<InventoryForAdjustDto> ckList, String warehouseCode, UserDto userDto, Boolean onlyWms) throws OpsException;

    //订单变更 订单修改
    Map<String, String> UpdateDoForOrder(UpdateForOrderDto dto) throws OpsException;

    void getFinishPo(List<FinishOrderWmsReqDto> doList, List<FinishPoListForDto> poPageList,FinishPageForOrderDto pageObj) throws OpsException;

    Integer getTKCKFinishDo(List<OpsDo> opsDoList, List<FinishOrderWmsReqDto> reqList, FinishPageForOrderDto pageObj) throws OpsException;

    Integer finishTKCKDo(List<OpsDo> finishDoList, String OrderId, String OrderItem, StringBuffer logBuf, String userName) throws OpsException;

    // 完纳 获取指令可完纳数量 bugid:11758 20230814 c14717
    Integer getFinishDo(List<OpsDo> opsDoList, List<FinishOrderWmsReqDto> reqList,FinishPageForOrderDto finishPageForOrderDto) throws OpsException;

    //完纳 验证完纳数量 bugid:11758 20230814 c14717
    HashMap<String,Integer> checkFinishDo(List<FinishOrderWmsReqDto> checkReqList, List<FinishOrderWmsResDto> askRepList) throws OpsException;

    //完纳 变更指令 bugid:11758 20230814 c14717
    Integer finishDo(List<OpsDo> opsDo,String OrderId, String OrderItem,StringBuffer logBuf, String userName) throws OpsException;

    //删单
    void CancelDo(Long id, String doid,  String orderId,String orderItem,UserDto userDto) throws OpsException;

    void CancelOnlyDo(String doId, String reason, UserDto userDto) throws OpsException;

    Map<String, String> updateDoEmail(UpdateForOrderDto dto) throws OpsException;

    //订单修改 修改地址，特发，自提：十位订单号批量修改 --针对拆分数量
    Map<String, String> updateDoAddressNew(UpdateForOrderDto dto,Boolean specialFlag) throws OpsException;


    //子项特发删除旧的物流单据
    void delOpsWmOrder(Long cancelId, String pcoId, String doId, UpdateForOrderDto dto) throws OpsException;

    OpsPco fillInUpdatePcoInfo(OpsPco updatePco, UpdateForOrderDto dto);

    //填充要变更的信息下发wms
    OpsDo fillInUpdateInfo(OpsDo upOpsDo, UpdateForOrderDto dto);

    void updatePcoDeliveryDay(OpsPco opsPco, UpdateForOrderDto dto) throws OpsException;

    void updatePco(OpsPco opsPco, UpdateForOrderDto dto) throws OpsException;

    void updateDoDeliveryDay(OpsDo opsDo, UpdateForOrderDto dto) throws OpsException;

    void updateDo(OpsDo opsDo, UpdateForOrderDto dto) throws OpsException;

    //do变更
    void updateDoByDo(OpsDo opsDo);

    void updateDoByCGDBCKDo(OpsDo opsCGDBCKDo);

    void delDoByDoId(Long id, String doId, String orderId, String orderItem, UserDto userDto) throws OpsException;

    /**
     * ops提交wmsDO和DOite
     */
    @Deprecated
    OpsDoAndItemDto findDoToWms(String limit) throws OpsException;

    //删单
    Long insertOrderCancel(CancelForOrderDto orderDto);

    Integer insertOrderCancelItem(Long cancelId, String wlOrderId, String wlType, String result, UserDto userDto);

    //委托在库出库确认
    String wmWTDoConfirm(WmWTDoConfirmDto param) throws OpsException;

    //出库发货确认接口
    String wmDoConfirm(WmDoConfirmDto param) throws OpsException;

    //出库发货状态接口
    String wmDoStatus(WmDoStatusDto param) throws OpsException;

    // 出库确认回传异常表
    void saveDOopsExceptionHandle(String apiName, String errType, int status, String expChildNo, String inputMsg, String outputMsg,
                                  String doId, String modelNo, int qty, String warehouseCode, String inventoryId, String inventoryType, String pcoId, String pcoItem);

    // 状态
    int prepareOperation(String doId) throws OpsException;

    // 物流开始出库作业
    void startOperation(String doId, String waveNo, String waveDeliveryDate, String expectedDeliveryDate) throws OpsException;

    void releaseOperation(String doId, String optTime, String waveNo) throws OpsException;

    void doComfirmCommon(boolean isWT, Integer wmOutQty, OpsDo opsDo, OpsDoItem opsDoItem, String barcode,
                         String packageCode, String volume, String weight, String sender, String expressCode, String logisticsCode,
                         String boxType, Date shipDate, String expressChildNo) throws OpsException;

    void updateDoItemQtyByDoItemId(Long opsDoItemId, Integer qty, Integer version) throws OpsException;

    void updateDoItemInvQtyById(Long opsDoItemInvId, Integer qty, Long version) throws OpsException;

    /**
     * 更新Do 等待状态
     * OK=物流入库对应RO收货完成
     *
     * @throws OpsException
     */
    void updateDoWaitTypeForDoId(String doId, String waitType, Integer version, String modifier) throws OpsException;



    void updateDoWaitTypeAndWareHouseCodeForId(Long id,String doSource, String gatherWarehouseCode, String warehouseCode,
                                               String waitType, Integer version, String modifier,Integer isWms,Integer delflag) throws OpsException;

    int deleteDoItemInventoryByPrimaryKeySelective(Long id, UserDto userDto) throws OpsException;

    void updateDoItemInventoryByPrimaryKeySelective(Long id, Long inventoryId, String inventoryTableType, UserDto userDto) throws OpsException;

    void updateDoToException(Long id);

    void updateDoToRoId(Long id, String roId, Integer roCrossType,String relocation);

    void updateGatherWarehouse(OpsDo oldOpsDo) throws OpsException;

    //订单修改随发分批拆分do
    String createAssDo(OpsDo oldOpsDo, int qty, UserDto userDto, DoTypeEnum doTypeEnum,String dlvEntire) throws OpsException;

    //oldDOItemInvSumQty > 0 && opsDoItemQty > oldDOItemInvSumQty 拆分指令
    Integer assDoByQtySumItemInv(int maxNum, String oldJyDoId, int doItemQty, OpsDoItem opsDoItem, UserDto userDto, DoTypeEnum doTypeEnum, WmOrderByInventoryDto wmOrderByInventoryDto) throws OpsException;

    OpsDo updateDoDlvEntryDelDB(OpsDo oldOpsDo) throws OpsException;

    //采购分那到货，随发分批需要拆分do
    String createAssDoForRo(OpsDo oldOpsDo, int qty, UserDto userDto,DoTypeEnum doTypeEnum) throws OpsException;

    //补库实际到货仓与请购仓不符 不包括提前生成的调拨单
    Boolean handRoChangeWarehouseCreateDB(Integer actualQty, String actualWarehouse, String orderno, Integer itemno, Integer splititemno,List<Long> inventoryMoveIds) throws OpsException;

    //采购入库 仓库和请购不一致
    void handROChangeDoWarehouseCodeByCGDo(OpsDo cgDo, String pcoId,Integer pcoItem, String modelNo) throws OpsException;

    OpsDo initOpsDo(String doId, OpsRequestpurchase opsRequestpurchase, OpsWarehouse opsWarehouse,
                    String receivewarehouseid);

    OpsDoItem initOpsDoItem(String doId, OpsRequestpurchase opsRequestpurchase);

    OpsDo insertDo(OpsDo opsDo, OpsDoItem opsDoItem, List<OpsDoItemInventory> doItemInventoryList, UserDto userDto) throws OpsException;

    OpsDo createDo(OpsDo opsDo, OpsDoItem opsDoItem, List<OpsDoItemInventory> doItemInventoryList, UserDto userDto) throws OpsException;

    void insertDoItemInventory(OpsDoItemInventory doItemInventory) throws OpsException;

    void insertDoItemInv(OpsDoItemInventory doItemInventory, UserDto userDto) throws OpsException;

    void insertDoItemInventoryList(List<OpsDoItemInventory> doItemInventoryList) throws OpsException;

    List<OpsDoItemInventory> deleteDoItemInventoryByInventoryId(Long inventoryId, String inventoryTable) throws OpsException;

    int updateOpsDoItemInventory(Long id, Long version, Integer useQty, Long inventoryId, InventoryTableTypeEnum inventoryTableTypeEnum, UserDto userDto);

    void updateOpsDoItemInventoryRcv(Long id, Long version , Long inventoryId, InventoryTableTypeEnum inventoryTableTypeEnum,Long fromInventoryId,InventoryTableTypeEnum fromInventoryTableType, String userName);


    void updateOpsDoItemInventory(OpsDoItemInventory doItemInventory, UserDto userDto) throws OpsException;

    void updateOpsDo(OpsDo opsDo, UserDto userDto) throws OpsException;

    void updateOpsDoItemInventoryDoId(Long id, Long version, String doId, UserDto userDto);

    void updatePreQtyForDo(OpsDo opsDo, List<OpsDoItemInventory> opsDoItemInventoryList, UserDto userDto,Map<Long, Boolean> riskMap) throws OpsException;

    void updateQtyForDo(OpsDo opsDo, List<OpsDoItemInventory> opsDoItemInventoryList, UserDto userDto) throws OpsException;

    void createDBOrder(OpsDo cgDo, OpsPcoItem opsPcoItem,UserDto userDto) throws OpsException;

    void initOpsDBDoAndRo(String dbckid, String dbrkid, OpsDo jyckDo, OpsDoItem jyckDoItem, OpsPcoItem opsPcoItem, WmOrderByInventoryDto wmOrderByInventoryDto) throws OpsException;

    void delDoChangeItemInventory(OpsDo opsDo, Long cancelId, DoTypeEnum doTypeEnum, OpsPcoItem opsPcoItem, OpsDo opsJYCKDo) throws OpsException;

    void delDBRo(OpsDo opsDo, Long cancelId, String roType) throws OpsException;

    CreDoByInventoryDto createAssQtyDo(String formatDoId, Integer splitNum, OpsDo oldOpsDo, OpsDoItem oldOpsDoItem, int qty, UserDto userDto, DoTypeEnum doTypeEnum, String waitType) throws OpsException;

    void updateOpsDoForCrossRoId(String doId, int roCrossType, String roId);

    //处理异常数据 生成调拨单
    String handleAbnormalData(String doId, String pcoId, Integer pcoItem, String signWarehouseCode,String roId) throws OpsException;


    void updateOpsDoItemInventoryOutQty(OpsDoItemInventory opsDoItemInventory,  Integer outQty, String userName) throws OpsException;

    void updateOpsDoItemOutQty(String doId, Integer outQty, Integer version, String userName) throws OpsException;

    int updateOpsDoStatus(String doId, int status, int version, String userName);

    /**
     * 获取DoItemInventory来源DO关联关系
     * @param doId
     * @param srcInventoryId
     * @param inventoryTableTypeEnum 当前行类型（不是src_InventoryTableType)
     * @return
     * @throws OpsException
     */
    OpsDoItemInventory getOpsDoItemInventoryBySrcInventoryId(String doId,  Long srcInventoryId, InventoryTableTypeEnum inventoryTableTypeEnum) throws OpsException;

    Date tkckWlDay(Date dlvDateParam, String fWareHoseCode, String tWareHouseCode, String orderNo) throws OpsException;

    //计算更新物流交货期
    Map<String, String> updateDoPcoWlday(String orderId, String orderItem, Date dlvDate,Boolean updateRcvFlag, UserDto userDto) throws OpsException;

    Map<String, String> updateDoPcoWldayV1(String orderId, String orderItem, Date dlvDateParam, Boolean updateRcvFlag, UserDto userDto) throws OpsException;

    // 计算订单物流交货期
    String getOrderMaxWlday(String orderId, String orderItem, Date dlvDateParam) throws OpsException;

    List<Rcvdetail> findRcvDetailsByMove(Long moveId) throws OpsException;

    List<OrderNoInfo> findOrderNoInfoListByMove(Long moveId) throws OpsException;
}
