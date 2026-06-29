package com.smc.smccloud.web;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.ExcelHelper;
import com.smc.smccloud.core.utils.ExcelUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.model.enums.PreStockDetailStatusEnum;
import com.smc.smccloud.model.prestock.*;
import com.smc.smccloud.service.PreStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Author: B90034
 * Date: 2021-10-25 10:22
 * Description:
 */
@Slf4j
@RequestMapping(value = "/shareapp/prestock")
@RestController
public class PreStockController {

    @Resource
    private PreStockService preStockService;

    /**
     * 查询申请
     *
     * @param request 查询条件
     * @return 申请列表
     */
    @RequestMapping(value = "/listPreStockApply", method = RequestMethod.POST)
    public ResultVo<PageInfo<PreStockApplyDTO>> listPreStockApply(@RequestBody PreStockApplyRequest request) {
        return preStockService.listPreStockApply(request);
    }

    /**
     * 保存申请
     *
     * @return applyId, passkey
     */
    @RequestMapping(value = "/saveApply", method = RequestMethod.POST)
    public ResultVo<Map<String, String>> savePreStockApply(@RequestBody PreStockApplyDetailDTO dto) {
        try {
            return preStockService.savePreStockApply(dto);
        } catch (Exception e) {
            log.error("savePreStockApply error , optUser = {}, params = {}", SMCApp.getLoginAuthDto(), dto);
            throw new BusinessException("保存错误："+e.getMessage());
        }
    }

    /**
     * 获取申请详情
     *
     * @param applyId 申请id
     * @param passkey passkey
     * @return 申请详情
     */
    @RequestMapping(value = "/getApply", method = RequestMethod.GET)
    public ResultVo<PreStockApplyDetailVO> getPreStockApply(@RequestParam("applyId") Long applyId,
                                                            @RequestParam("passkey") String passkey) {
        return preStockService.getPreStockApply(applyId, passkey);
    }

    /**
     * 分页查询申请项
     *
     * @param request 申请ID
     * @return 分页查询结果集
     */
    @RequestMapping(value = "/listApplyDetail", method = RequestMethod.POST)
    public ResultVo<PageInfo<PreStockDetailDTO>> listPreStockApplyDetail(@RequestBody PreStockApplyRequest request) {
        return preStockService.listPreStockApplyDetail(request);
    }

    /**
     * 根据申请项id查询处理结果
     *
     * @param detailIds 申请项id
     * @return result
     */
    @RequestMapping(value = "/getApplyDetailResult", method = RequestMethod.GET)
    public ResultVo<List<PreStockResultDTO>> getApplyDetailResult(@RequestParam("detailIds") List<Long> detailIds) {
        return preStockService.getApplyDetailResult(detailIds);
    }

    /**
     * 删除申请项
     *
     * @param detailIds 申请项id
     * @return string
     */
    @RequestMapping(value = "/removeDetail", method = RequestMethod.GET)
    public ResultVo<String> removeDetail(@RequestParam("detailIds") List<Long> detailIds) {
        preStockService.removeDetail(detailIds);
        return ResultVo.success("申请项删除成功");
    }

    /**
     * 提交申请
     *
     * @param dto 申请信息
     * @return string
     */
    @RequestMapping(value = "/submitApply", method = RequestMethod.POST)
    public ResultVo<String> submitApply(@RequestBody PreStockApplyDetailDTO dto) {
        ResultVo<Map<String, String>> saveResult = this.savePreStockApply(dto);
        if (!saveResult.isSuccess()) {
            return ResultVo.failure("提交失败," + saveResult.getMessage());
        }
        dto.setId(Long.parseLong(saveResult.getData().get("applyId")));
        return preStockService.submitPreStockApply(dto);
    }

    /**
     * 提交申请
     *
     * @param dto applyIds-申请id
     * @return 提交结果
     */
    @RequestMapping(value = "/approveApply", method = RequestMethod.POST)
    public ResultVo<String> approvePreStockApply(@RequestBody @Validated PreStockApplyHandleDTO dto) {
        return preStockService.approvePreStockApply(dto);
    }

    /**
     * 保存shikomi申请信息
     *
     * @param dto shikomi申请信息
     * @return 保存结果
     */
    @RequestMapping(value = "/saveShikomiInfo", method = RequestMethod.POST)
    public ResultVo<String> saveShikomiInfo(@RequestBody PreStockApplyDetailDTO dto) {
        return preStockService.saveShikomiInfo(dto);
    }

    /**
     * 导出shikomi文件
     *
     * @return string
     */
    @RequestMapping(value = "exportShikomiFile", method = RequestMethod.GET)
    public ResultVo<String> exportShikomiFile() {
        return preStockService.exportShikomiFile();
    }

    /**
     * 重置SHIKOMI处理
     *
     * @param dto 重置shikomi处理DTO
     * @return 处理结果
     */
    @RequestMapping(value = "resetShikomiProcess", method = RequestMethod.POST)
    public ResultVo<String> resetShikomiProcess(@RequestBody PreStockResetProcessDTO dto) {
        return preStockService.resetShikomiProcess(dto);
    }

    /**
     * 重置申请处理状态
     */
    @RequestMapping(value = "resetApplyProcessStatus", method = RequestMethod.POST)
    public ResultVo<String> resetApplyProcessStatus(@RequestBody PreStockResetProcessDTO dto) {
        return preStockService.resetApplyProcessStatus(dto);
    }

//    /**
//     * 获取手动处理型号数据
//     *
//     * @param applyId 申请Id
//     * @param modelNo 型号
//     * @return 手动处理型号数据
//     */
//    @RequestMapping(value = "/getResult", method = RequestMethod.GET)
//    public ResultVo<PreStockResultVO> getPreStockResult(@RequestParam(value = "applyId") Long applyId,
//                                                        @RequestParam("modelNo") String modelNo) {
//        return preStockService.getPreStockResult(applyId, modelNo);
//    }

    /**
     * 获取手动处理申请项数据
     *
     * @param applyId  申请Id
     * @param detailId 型号
     * @return 手动处理申请项数据
     */
    @RequestMapping(value = "/getResult", method = RequestMethod.GET)
    public ResultVo<PreStockResultVO> getPreStockResult(@RequestParam(value = "applyId") Long applyId,
                                                        @RequestParam(value = "detailId") Long detailId) {
        return preStockService.getPreStockResult(applyId, detailId);
    }

    /**
     * 根据申请项id生成手动处理项
     *
     * @param detailId 申请项Id
     * @return 待处理项
     */
    @RequestMapping(value = "/getProcessItemByDetailId", method = RequestMethod.GET)
    public ResultVo<List<PreStockResultDTO>> getProcessItemByDetailId(@RequestParam("detailId") Long detailId) {
        return preStockService.getProcessItemByDetailId(detailId);
    }

    /**
     * 手动执行处理项
     *
     * @param processItemList 处理项列表
     * @return 处理结果
     */
    @RequestMapping(value = "/handleProcessItem", method = RequestMethod.POST)
    public ResultVo<String> handleProcessItem(@RequestBody List<PreStockResultDTO> processItemList) {
        return preStockService.handleProcessItem(processItemList);
    }

    /**
     * 自动处理
     *
     * @param dto applyIds-申请id, modelNo 处理型号(选填)
     * @return 处理结果
     */
    @RequestMapping(value = "/autoHandleApply", method = RequestMethod.POST)
    public ResultVo<String> autoHandleApply(@RequestBody @Validated PreStockApplyHandleDTO dto) {
        return preStockService.autoHandlePreStockApply(dto);
    }

    /**
     * 查询先行在库申请详情视图
     *
     * @param request 查询条件
     * @return 视图
     */
    @RequestMapping(value = "/listPreStockDetail", method = RequestMethod.POST)
    public ResultVo<PageInfo<PreStockDetailView>> listPreStockDetail(@RequestBody PreStockDetailRequest request) {
        return preStockService.listPreStockDetail(request);
    }

    /**
     * 导出先行在库申请项清单
     */
    @RequestMapping(value = "/exportPreStockDetail", method = RequestMethod.POST)
    public void exportPreStockDetail(HttpServletResponse response, @RequestBody PreStockDetailRequest request) {
        ExcelUtil excelUtil = preStockService.exportPreStockDetail(request);
        excelUtil.writeToResponse(response, "先行在库申请项清单.xlsx");
    }

    /**
     * 采购退单（3）  =》 处理中（2），或暂不备库（9）
     * @param detailIds
     * @param newStatus
     * @return
     */
    @RequestMapping(value = "/updatePreStockDetailStatus", method = RequestMethod.POST)
    public ResultVo<String> updatePreStockDetailStatus(@RequestParam("detailIds") List<Long> detailIds,
                                                       @RequestParam("newStatus") String newStatus) {
        return preStockService.updatePreStockDetailStatus(detailIds, PreStockDetailStatusEnum.purchaseCancel.getCode(),newStatus);
    }

    /**
     * 更改是否异仓调拨
     * @param id
     * @param transFlag
     * @return
     */
    @RequestMapping(value = "/updatePreStockDetailTranFlag", method = RequestMethod.POST)
    public ResultVo<String> updatePreStockDetailTranFlag(@RequestParam("id")  Long id,
                                                       @RequestParam("transFlag") boolean transFlag) {
        return preStockService.updatePreStockDetailTranFlag(  id ,  transFlag);
    }

}
