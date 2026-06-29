package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.order.TransOrderDtoForMove;
import com.sales.ops.dto.purchase.RequestPurchaseInfoDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.ExcelUtil;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.csstock.WarehouseDO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.model.prestock.*;
import com.smc.smccloud.model.trans.TransOrderVO;

import java.util.List;
import java.util.Map;

/**
 * Author: B90034
 * Date: 2021-10-25 10:20
 * Description:
 */
public interface PreStockService {

    /**
     * 分页查询先行在库申请
     *
     * @param request 查询条件
     * @return result
     */
    ResultVo<PageInfo<PreStockApplyDTO>> listPreStockApply(PreStockApplyRequest request);

    /**
     * 保存先行在库申请
     *
     * @param dto 先行在库申请信息
     * @return applyId, passkey
     */
    ResultVo<Map<String, String>> savePreStockApply(PreStockApplyDetailDTO dto);

    /**
     * 创建先行在库申请单
     *
     * @param dto 申请信息
     * @return applyId
     */
    ResultVo<String> createPreStockApply(PreStockApplyDetailDTO dto);

    /**
     * 获取申请详情
     *
     * @param applyId 申请Id
     * @param passkey passskey
     * @return 申请详情
     */
    ResultVo<PreStockApplyDetailVO> getPreStockApply(Long applyId, String passkey);

    /**
     * 分页查询申请项
     *
     * @param request 申请ID
     * @return 分页查询结果集
     */
    ResultVo<PageInfo<PreStockDetailDTO>> listPreStockApplyDetail(PreStockApplyRequest request);

    /**
     * 根据申请项id查询处理结果
     *
     * @param detailIds 申请项id
     * @return result
     */
    ResultVo<List<PreStockResultDTO>> getApplyDetailResult(List<Long> detailIds);

    /**
     * 删除申请项
     *
     * @param detailIds detail.id
     */
    void removeDetail(List<Long> detailIds);

    /**
     * 提交申请: 先保存再提交
     *
     * @param dto 申请信息
     * @return result
     */
    ResultVo<String> submitPreStockApply(PreStockApplyDetailDTO dto);

    /**
     * 审核确认
     *
     * @param dto applyNos-申请号
     * @return result
     */
    ResultVo<String> approvePreStockApply(PreStockApplyHandleDTO dto);

    /**
     * 保存Shikomi申请信息
     *
     * @param dto Shikomi申请信息
     * @return result
     */
    ResultVo<String> saveShikomiInfo(PreStockApplyDetailDTO dto);

    /**
     * 生成SHIKOMI文件
     */
    ResultVo<String> exportShikomiFile();

    ResultVo<String> exportJPShikomiFile(List<PreStockDetailDO> detailList, DataCodeVO mailInfo);

    ResultVo<String> exportCNShikomiFile(List<PreStockDetailDO> detailList, DataCodeVO mailInfo);

    /**
     * 重置shikomi申请
     *
     * @param dto 重置shikomi处理dto
     * @return 结果
     */
    ResultVo<String> resetShikomiProcess(PreStockResetProcessDTO dto);

    /**
     * 重置申请处理状态
     */
    ResultVo<String> resetApplyProcessStatus(PreStockResetProcessDTO dto);

    /**
     * 查询处理结果
     *取消，改用传子项明细
     * @param applyId 申请号
     * @param modelNo 型号
     * @return 处理结果
     */
//    ResultVo<PreStockResultVO> getPreStockResult(Long applyId, String modelNo);

    /**
     * 查询处理结果
     *
     * @param applyId  申请号
     * @param detailId 申请项id
     * @return 处理结果
     */
    ResultVo<PreStockResultVO> getPreStockResult(Long applyId, Long detailId);

    /**
     * 查询处理结果
     *
     * @param applyId 申请id
     * @param modelNo 型号（选填）
     * @return result
     */
    List<PreStockResultDO> listPreStockResult(Long applyId, String modelNo);

    /**
     * 根据申请项id生成处理项
     *
     * @param detailId detailId
     * @return 处理项
     */
    ResultVo<List<PreStockResultDTO>> getProcessItemByDetailId(long detailId);

    /**
     * 手动执行处理结果项
     *
     * @param processItemList 处理结果项
     * @return 处理结果
     */
    ResultVo<String> handleProcessItem(List<PreStockResultDTO> processItemList);

    /**
     * 根据申请号查询处理结果
     *
     * @param applyNo 申请No
     * @return result
     */
    ResultVo<List<PreStockResultDTO>> listPreStockResultByApplyNo(String applyNo);

    /**
     * 根据申请ID查询处理结果
     *
     * @param applyId 申请ID
     * @return result
     */
    ResultVo<List<PreStockResultDTO>> listPreStockResultByApplyId(long applyId);

    /**
     * 先行在库处理-自动处理
     *
     * @param dto applyIds-申请ID, modelNo 处理型号(选填)
     * @return 处理结果
     */
    ResultVo<String> autoHandlePreStockApply(PreStockApplyHandleDTO dto);

    /**
     * 先行在库处理-自动处理
     *
     * @return 处理结果
     */
    ResultVo<String> autoHandlePreStockApply(PreStockApplyDO applyInfo, List<PreStockDetailDO> detailList, List<String> warehouseConfig, WarehouseDO warehouseDO,Boolean isIntercept);


    /**
     * @param applyInfo     申请信息
     * @param detail        申请项
     * @param canTransfer   是否可调库
     * @param stockInfoList 库存信息
     * @return 处理信息
     */
    List<PreStockResultDTO> autoCreateProcessItem(PreStockApplyDO applyInfo, PreStockDetailDO detail, boolean canTransfer, List<PreModelStockInfo> stockInfoList);

    /**
     * 生成调库单/调拨单
     *
     * @param applyInfo    先行在库申请
     * @param detailInfo   申请项信息
     * @param transferList 调库/调拨处理项
     */
    ResultVo<String> createTransferOrder(PreStockApplyDO applyInfo, PreStockDetailDO detailInfo, List<PreStockResultDTO> transferList);
     ResultVo<String> saveTransferOrder(List<TransOrderVO> transDtoList, PreStockDetailDO detailInfo, List<PreStockResultDTO> transferList);
    /**
     *  生成预占在途
     * @param applyInfo 申请信息
     * @param detailInfo 申请项信息
     * @param moveList  在途处理项
     * @return
     */
     ResultVo<String> createMoverOrder(PreStockApplyDO applyInfo, PreStockDetailDO detailInfo, List<PreStockResultDTO> moveList) ;
    ResultVo<String> saveMoveOrder(List<TransOrderDtoForMove> moveOrderList, PreStockDetailDO detailInfo, List<PreStockResultDTO> moveList);
    /**
     * 生成采购订单
     *
     * @param applyInfo    申请信息
     * @param detailInfo   申请项信息
     * @param purchaseList 采购处理项
     */
    ResultVo<String> createPurchaseOrder(PreStockApplyDO applyInfo, PreStockDetailDO detailInfo, List<PreStockResultDTO> purchaseList);
    ResultVo<String> savePurchaseOrder(List<RequestPurchaseInfoDto> PurchaseOrderList, List<OrderStateVO> orderStateList, PreStockDetailDO detailInfo, List<PreStockResultDTO> purchaseList);
    /**
     * 自动尝试处理被拦截的申请项
     */
    void autoProcessInterceptedApplyDetail();

    /**
     * 采购取消回调接口
     *
     * @param orderNo 采购单号
     * @return 回调处理结果
     */
    ResultVo<String> purchaseOrderCancelHandle(String orderNo);

    /**
     * 获取先行备库出在库仓库
     *
     * @param applyInfo 申请信息
     * @return 出在库仓库列表
     */
    ResultVo<List<String>> getWarehouseConfig(PreStockApplyDO applyInfo);

    ResultVo<PreStockApplyDetailDTO> findPreStockApplyByNo(String applyNo);

    ResultVo<List<PreStockDetailDTO>> findPreStockDetailByNo(Long applyId, String modelNo);

    /**
     * 查询先行在库申请详情视图
     *
     * @param request 查询条件
     * @return 视图
     */
    ResultVo<PageInfo<PreStockDetailView>> listPreStockDetail(PreStockDetailRequest request);

    /**
     * 导出先行在库申请项详情清单
     *
     * @param request 导出条件
     * @return excel
     */
    ExcelUtil exportPreStockDetail(PreStockDetailRequest request);

    /**
     * 处理门户先行在库申请
     *
     * @param createDto 申请信息
     * @return 处理结果
     */
    ResultVo<String> handleSMSPreStockApply(PreStockApplyDetailDTO createDto);

    /**
     * 获取符合申请出库规则的在库信息
     *
     * @param modelNo         型号
     * @param warehouseConfig 出库规则
     * @return 符合出库优先级规则的在库信息
     */
    List<PreModelStockInfo> getPreModelInventoryInfo(String modelNo, List<String> warehouseConfig);

    /**
     * 判断申请项是否可调库
     *
     * @param applyInfo       申请信息
     * @param detail          申请项
     * @param warehouseConfig 可出库仓库
     * @return true-可调库, false-不可调库
     */
    boolean checkTransfer(PreStockApplyDO applyInfo, PreStockDetailDO detail, List<String> warehouseConfig);


    ResultVo<List<ShikomiCallbackDTO>> getShikomiStockData(List<String> applyNos);

    /**
     * 先行在库申请处理时，将自动周转类型申请的qtybin更新到BinData
     *
     * @param applyId 申请id
     */
    void updateQtyBinToBinData(long applyId);

    /**
     * 推送申请项处理状态给门户消息队列
     *
     * @param applyNo 申请号
     */
    void callbackProcessStatusToPortal(String applyNo, List<PreStockDetailDO> detailList);

    /**
     * 回调处理后生成的（采购单号&调拨单号）给门户接口
     *
     * @param applyNo    申请号
     * @param detailList 申请项
     */
    void callbackPurchaseNoToPortal(String applyNo, List<PreStockDetailDO> detailList);

    /**
     * 自动回调门户
     */
    ResultVo<String> autoCallbackPortal();

    /**
     * 根据申请批次号回调门户处理结果
     *
     * @param batchNo 批次号
     * @return 回调门户结果
     */
    ResultVo<String> callBackResultToPortalByBatchNo(String batchNo);

    /**
     * 采购退单（3）  =》 处理中（2），或暂不备库（9）
     * @param ids
     * @param status
     * @param newStatus
     * @return
     */
    ResultVo<String> updatePreStockDetailStatus(List<Long> ids,String status,String newStatus);

    /**
     * 更新是否异仓调拨
     * @param id
     * @param transFlag true:异仓调拨;false:同仓调拨
     * @return
     */
    ResultVo<String> updatePreStockDetailTranFlag(Long id ,Boolean transFlag);

    PreStockResultDO getPreStockResultByOrderNo(String orderNo);
    PreStockDetailDO getPreStockDetailById(Long id);
}
