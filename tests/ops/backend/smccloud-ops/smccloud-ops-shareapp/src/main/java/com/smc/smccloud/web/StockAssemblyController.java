package com.smc.smccloud.web;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.ExcelUtil;
import com.smc.smccloud.core.utils.FileUtil;
import com.smc.smccloud.model.OpsKdInfoDO;
import com.smc.smccloud.model.kd.KdInfoParams;
import com.smc.smccloud.model.stockassembly.*;
import com.smc.smccloud.service.OpsKdInfoService;
import com.smc.smccloud.service.StockAssemblyService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

/**
 * Author: B90034
 * Date: 2021-10-09 16:03
 * Description:
 */
@RequestMapping(value = "/shareapp/stockassembly")
@RestController
public class StockAssemblyController {

    @Resource
    private OpsKdInfoService opsKdInfoService;

    @Resource
    private StockAssemblyService stockAssemblyService;

    /**
     * 保存申请
     *
     * @param data 申请信息
     * @return applyId
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultVo<String> saveStockAssembly(@RequestBody @Validated StockAssemblyApplyDTO data) {
        return stockAssemblyService.saveStockAssembly(data);
    }

    /**
     * 删除申请
     *
     * @param applyIds 申请ID
     * @return 结果
     */
    @RequestMapping(value = "/removeApply", method = RequestMethod.GET)
    public ResultVo<String> removeStockAssemblyApply(@RequestParam("applyIds") List<Long> applyIds) {
        return stockAssemblyService.removeStockAssemblyApply(applyIds);
    }

    /**
     * 删除申请项
     *
     * @param detailIds 申请项id
     * @return string
     */
    @RequestMapping(value = "/removeDetail", method = RequestMethod.GET)
    public ResultVo<String> removeStockAssemblyDetail(@RequestParam("detailIds") List<Long> detailIds) {
        stockAssemblyService.removeStockAssemblyDetail(detailIds);
        return ResultVo.success("删除成功");
    }

    /**
     * 查询申请列表
     *
     * @param request 查询条件
     * @return 申请列表
     */
    @RequestMapping(value = "/listStockAssembly", method = RequestMethod.POST)
    public ResultVo<PageInfo<StockAssemblyApplyDTO>> listStockAssembly(@RequestBody StockAssemblyRequest request) {
        return stockAssemblyService.listStockAssembly(request);
    }

    /**
     * 获取申请详情
     *
     * @param applyId 申请id
     * @return 申请详情信息
     */
    @RequestMapping(value = "/getApplyByNo", method = RequestMethod.GET)
    public ResultVo<StockAssemblyApplyDTO> getStockAssemblyApply(@RequestParam("applyId") Long applyId) {
        StockAssemblyApplyDTO dto = stockAssemblyService.getStockAssemblyApply(applyId);
        return ResultVo.success(dto);
    }

    /**
     * 分页查询申请项
     *
     * @param request 查询条件 (申请id、出入库类型)
     * @return 分页结果集
     */
    @RequestMapping(value = "/listApplyDetail", method = RequestMethod.POST)
    public ResultVo<PageInfo<StockAssemblyItemDTO>> listStockAssemblyApplyDetail(@RequestBody StockAssemblyRequest request) {
        return stockAssemblyService.listStockAssemblyApplyDetail(request);
    }

    /**
     * 提交申请
     *
     * @param data 申请信息
     * @return string
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResultVo<String> submitStockAssembly(@RequestBody @Validated StockAssemblyApplyDTO data) {
        ResultVo<String> saveResult = stockAssemblyService.saveStockAssembly(data);
        if (!saveResult.isSuccess()) {
            return ResultVo.failure("保存失败-" + saveResult.getMessage());
        }
        data.setId(Long.parseLong(saveResult.getData()));

        return stockAssemblyService.submitStockAssembly(data);
    }

    /**
     * 审核确认申请
     *
     * @param dto 确认信息
     * @return string
     */
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public ResultVo<String> approveStockAssembly(@RequestBody StockAssemblyHandleDTO dto) {
        return stockAssemblyService.approveStockAssembly(dto);
    }

    /**
     * 处理调库申请
     *
     * @param dto 处理信息
     * @return 处理结果
     */
    @RequestMapping(value = "/handle", method = RequestMethod.POST)
    public ResultVo<String> handleApply(@RequestBody StockAssemblyHandleDTO dto) {
        return stockAssemblyService.handleApply(dto);
    }

    /**
     * 快速处理（保存+提交+审核+执行处理）
     *
     * @param data 申请
     * @return 处理结果
     */
    @RequestMapping(value = "/fastHandle", method = RequestMethod.POST)
    public ResultVo<String> fastHandleApply(@RequestBody @Validated StockAssemblyApplyDTO data) {
        try {
            // 1.保存
            ResultVo<String> saveResult = stockAssemblyService.saveStockAssembly(data);
            if (!saveResult.isSuccess()) {
                return ResultVo.failure("保存失败-" + saveResult.getMessage());
            }
            // 2.提交
            data.setId(Long.parseLong(saveResult.getData()));
            ResultVo<String> submitResult = stockAssemblyService.submitStockAssembly(data);
            if (!submitResult.isSuccess()) {
                //    <!--add by WuWeiDong 20230630  bug 11089  返回具体信息 -->
                return ResultVo.failure(submitResult.getData(),"", submitResult.getMessage());
            }
            // 3.审核确认
            StockAssemblyHandleDTO dto = new StockAssemblyHandleDTO();
            dto.setApplyIds(Collections.singletonList(data.getId()));
            dto.setHandleType("1");
            ResultVo<String> approveResult = stockAssemblyService.approveStockAssembly(dto);
            if (!approveResult.isSuccess()) {
                return ResultVo.failure(approveResult.getMessage());
            }
            // 4.执行处理
            dto.setHandleType("3");
            return stockAssemblyService.handleApply(dto);
        }catch (Exception ex){
            return ResultVo.failure("执行失败: " + ex.getMessage());
        }
    }

    /**
     * 查询申请视图
     *
     * @param request 查询条件
     * @return 视图
     */
    @RequestMapping(value = "/listDetail", method = RequestMethod.POST)
    public ResultVo<PageInfo<StockAssemblyDetailView>> listStockAssemblyDetail(@RequestBody stockAssemblyDetailRequest request) {
        return stockAssemblyService.listStockAssemblyDetail(request);
    }

    /**
     * 导出申请项清单
     */
    @RequestMapping(value = "/exportStockAssemblyDetail", method = RequestMethod.POST)
    public void exportStockAssemblyDetail(HttpServletResponse response, @RequestBody stockAssemblyDetailRequest request) {
        ExcelUtil excelUtil = stockAssemblyService.exportStockAssemblyDetail(request);
        excelUtil.writeToResponse(response, "组换调库申请项清单.xlsx");
    }

    /**
     * 导出组换调库申请项导入模板
     */
    @RequestMapping(value = "/exportApplyDetailTemplate", method = RequestMethod.GET)
    public void exportStockAssemblyDetailTemplate(HttpServletResponse response) throws UnsupportedEncodingException {
        String path = "template/组换调库申请项导入模板.xlsx";
        String fileName = "组换调库申请项导入模板.xlsx";
        response.resetBuffer();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        try (InputStream is = FileUtil.getTemplate(path);
             BufferedInputStream bis = new BufferedInputStream(is);
             OutputStream os = response.getOutputStream();
             BufferedOutputStream bos = new BufferedOutputStream(os)) {

            int len;
            for (byte[] buffer = new byte[4096]; (len = bis.read(buffer)) != -1; ) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw new BusinessException("模板导出失败");
        }
    }

    // Add by DengDengHui, 2022-10-20 for bug-8402

    /**
     * 批量导入申请项
     *
     * @param applyId 申请ID
     * @param file    文件
     * @return 导入结果
     */
    @RequestMapping(value = "/importStockAssemblyApplyDetail", method = RequestMethod.POST)
    public ResultVo<String> importStockAssemblyApplyDetail(@RequestParam(value = "applyId") Long applyId,
                                                           @RequestParam(value = "file") MultipartFile file) {

        try (InputStream inputStream = file.getInputStream()) { // bug-9203
            return stockAssemblyService.importApplyDetail(inputStream, applyId);
        } catch (Exception e) {
            return ResultVo.failure("导入文件失败: " + e.getMessage());
        }
    }
    // end


    /**
     * KD查询
     */
    @PostMapping("/listKdInfos")
    public ResultVo<PageInfo<OpsKdInfoDO>> listKdInfos(@RequestBody KdInfoParams params) {
        return opsKdInfoService.listKdInfos(params);
    }

    /**
     * KD查询导出
     */
    @PostMapping("/exportKdInfoData")
    public void exportKdInfoData(@RequestBody KdInfoParams params) {
        opsKdInfoService.exportKdInfoData(params);
    }

}
