package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.inventory.InventoryForAdjustDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.ExcelHelper;
import com.smc.smccloud.core.utils.ExcelUtil;
import com.smc.smccloud.model.stockassembly.*;

import java.io.InputStream;
import java.util.List;

/**
 * Author: B90034
 * Date: 2021-09-27 12:17
 * Description: 组换调库申请服务接口
 */
public interface StockAssemblyService {

    /**
     * 保存组换调库申请
     *
     * @param data 组换调库申请信息
     * @return applyId
     */
    ResultVo<String> saveStockAssembly(StockAssemblyApplyDTO data);

    /**
     * 创建组换调库申请
     *
     * @param data 申请信息
     * @return applyId
     */
    ResultVo<String> createStockAssemblyApply(StockAssemblyApplyDTO data);

    /**
     * 删除未处理的申请
     *
     * @param applyIds shenqingid
     */
    ResultVo<String> removeStockAssemblyApply(List<Long> applyIds);

    /**
     * 删除组换调库申请项
     *
     * @param detailIds detail.id
     */
    void removeStockAssemblyDetail(List<Long> detailIds);

    /**
     * 分页查询组换调库申请
     *
     * @param request 查询条件
     * @return 分页结果集
     */
    ResultVo<PageInfo<StockAssemblyApplyDTO>> listStockAssembly(StockAssemblyRequest request);

    /**
     * 根据申请号获取申请详情
     *
     * @param applyId 申请Id
     * @return 申请详情
     */
    StockAssemblyApplyDTO getStockAssemblyApply(Long applyId);

    /**
     * 分页查询申请项
     *
     * @param request 查询条件
     * @return 分页结果集
     */
    ResultVo<PageInfo<StockAssemblyItemDTO>> listStockAssemblyApplyDetail(StockAssemblyRequest request);

    /**
     * 提交申请: 先保存再提交
     * -编辑中提交为待确认状态
     *
     * @param data 申请Id
     * @return result
     */
    ResultVo<String> submitStockAssembly(StockAssemblyApplyDTO data);



    /**
     * 审核确认
     *
     * @param dto 处理信息
     * @return result
     */
    ResultVo<String> approveStockAssembly(StockAssemblyHandleDTO dto);
    /**
     * 根据申请类型处理申请
     *
     * @param dto 处理信息
     * @return 处理结果
     */
    ResultVo<String> handleApply(StockAssemblyHandleDTO dto);

    /**
     * 已确认的退回编辑
     *
     * @param dto 处理信息
     * @return result
     */
    ResultVo<String> returnApply(StockAssemblyHandleDTO dto);

    /**
     * 调库申请审批通过后的数据处理
     *
     * @param dto 处理信息
     * @return result
     */
    ResultVo<String> handleTransfer(StockAssemblyDO applyInfo, StockAssemblyHandleDTO dto);

    /**
     * 组换申请审批通过后的数据处理
     *
     * @param dto 处理信息
     * @return result
     */
    ResultVo<String> handleAssembly(StockAssemblyDO applyInfo, StockAssemblyHandleDTO dto);

    /**
     * 更新组换中的申请状态
     *
     * @param applyNo 组换申请号
     * @param result  组换结果 (true-组换成功; false-组换失败)
     * @return string
     */
    ResultVo<String> updateAssemblyStatus(String applyNo, Boolean result);

    /**
     * 多条件查询组换调库详情
     *
     * @param request 查询条件
     * @return List
     */
    ResultVo<PageInfo<StockAssemblyDetailView>> listStockAssemblyDetail(stockAssemblyDetailRequest request);

    /**
     * 导出组换调库详情
     *
     * @param request 导出条件
     * @return excel
     */
    ExcelUtil exportStockAssemblyDetail(stockAssemblyDetailRequest request);

    /**
     * 推送组换成功的申请数据至成本数据
     * @return 处理结果
     */
    ResultVo<String> sendAssemblyApplyToCost();

    /**
     * 查询已执行完成且未录入成本的组换申请信息
     */
    ResultVo<List<StockAssemblyDetailView>> listNoImportCostAssemblyData();

    /**
     * 导入组换成本数据
     *
     * @return string
     */
    ResultVo<String> updateImportCostAssemblyApplyStatus(List<StockAssemblyDetailView> detailList);

    /**
     * 批量导入申请项
     *
     * @param inputStream 导入文件流
     * @param applyId     申请ID
     * @return 导入处理结果
     */
    ResultVo<String> importApplyDetail(InputStream inputStream, Long applyId);

    /**
     * 处理门户组换调库申请
     * @param applyNo 申请号
     * @return 处理结果
     */
    ResultVo<List<TransferResult>> handleSMSInStockApply(String applyNo);

    /**
     *  返回调入的型号，做生成下发物流指令
     * @param applyNo
     * @return
     */
    public  ResultVo<List<InventoryForAdjustDto>> getAssemblyDataForWMS(String applyNo,Boolean isTransOut );
}
