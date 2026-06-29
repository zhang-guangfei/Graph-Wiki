package com.smc.smccloud.web;


import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.FileUtil;
import com.smc.smccloud.model.csstock.*;
import com.smc.smccloud.service.CsStockApplyService;
import com.smc.smccloud.service.OpsWarehouseService;
import com.smc.smccloud.utils.JasperHelper;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 寄售管理
 *
 * @author smc
 * @since 2021-11-03
 */
@RestController
@RequestMapping(value = "/shareapp/csstock")
public class CsStockApplyController {

    @Resource
    private CsStockApplyService csStockApplyService;
    @Resource
    private OpsWarehouseService warehouseService;

    /**
     * 加载寄售申请
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listCsStockApply", method = RequestMethod.POST)
    public ResultVo<PageInfo<CsApplyDO>> listCsStockApply(@RequestBody CsStockApplyRequest request) {
        return csStockApplyService.listCsStockApply(request);
    }

    /**
     * 加载寄售型号清单设置管理
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listCsStockSetting", method = RequestMethod.POST)
    public ResultVo<PageInfo<CsStockSettingDO>> listCsStockSetting(@RequestBody CsStockSettingRequest request) {
        return csStockApplyService.listCsStockSetting(request);
    }

    /**
     * 计算并生成需补库型号以及数量
     *
     * @param agentNo
     * @param warehouseCode
     * @return
     */
    @RequestMapping(value = "/listReplDetail", method = RequestMethod.GET)
    public ResultVo<List<CsStockReplenishmentVO>> listReplDetail(@RequestParam String agentNo, @RequestParam String warehouseCode) {
        return csStockApplyService.listReplDetail(agentNo, warehouseCode);
    }

    /**
     * 更新备库型号启停使用
     *
     * @param agentNo
     * @param warehouseCode
     * @param modelNo
     * @param status
     * @return
     */
    @RequestMapping(value = "/updateCsSettingModelStatus", method = RequestMethod.GET)
    public ResultVo<String> updateCsSettingModelStatus(@RequestParam String agentNo, @RequestParam String warehouseCode, @RequestParam String modelNo, @RequestParam Integer status) {
        return csStockApplyService.updateCsSettingModelStatus(agentNo, warehouseCode, modelNo, status);
    }

    /**
     * 生成寄售申请申请清单号
     *
     * @param agentNo
     * @param warehouseCode
     * @return
     */
    @RequestMapping(value = "/createReplApply", method = RequestMethod.GET)
    public ResultVo<String> createReplApply(@RequestParam String agentNo, @RequestParam String warehouseCode) {
        return csStockApplyService.createReplApply(agentNo, warehouseCode);
    }

    /**
     * 加载寄售申请子项清单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listCsStockApplyDetail", method = RequestMethod.POST)
    public ResultVo<PageInfo<CsApplyDetailDO>> listCsStockApplyDetail(@RequestBody CsApplyDetailRequest request) {
        return csStockApplyService.listCsStockApplyDetail(request);
    }

    /**
     * 删除申请号子项行
     *
     * @param applyId
     * @param modelNo
     * @return
     */
    @RequestMapping(value = "/removeDetail", method = RequestMethod.GET)
    public ResultVo<String> removeDetail(@RequestParam Long applyId, @RequestParam String modelNo) {
        return csStockApplyService.removeDetail(applyId, modelNo);
    }

    /**
     * 新增申请子项
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addDetail", method = RequestMethod.POST)
    public ResultVo<String> removeDetail(@RequestBody CsApplyDetailDTO request) {
        return csStockApplyService.addDetail(request);
    }

    /**
     * 修改申请子项
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateDetail", method = RequestMethod.POST)
    public ResultVo<String> updateDetail(@RequestBody CsApplyDetailDTO request) {
        return csStockApplyService.updateDetail(request);
    }

    /**
     * 删除申请号（更改状态=9）
     *
     * @param applyId
     * @return
     */
    @RequestMapping(value = "/removeApply", method = RequestMethod.GET)
    public ResultVo<String> removeApply(@RequestParam Long applyId) {
        return csStockApplyService.removeApply(applyId);
    }

    /**
     * 提交申请出库
     *
     * @param request
     * @return
     */
//    @RequestMapping(value = "/confirmCsApply", method = RequestMethod.POST)
//    public ResultVo<String> confirmCsApply(@RequestBody CsApplyConfirmDTO request) {
//        return csStockApplyService.confirmCsApply(request);
//    }

    /**
     * 寄售库房清单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listCsWarehouse", method = RequestMethod.POST)
    public ResultVo<PageInfo<CsWarehouseVO>> listCsWarehouse(@RequestBody CsWarehouseRequest request) {
        return warehouseService.listCsWarehouse(request);
    }

    /**
     * 保存寄售库房
     *
     * @param csWarehourseDO
     * @return
     */
    @RequestMapping(value = "/saveCsWarehourse", method = RequestMethod.POST)
    public ResultVo<String> saveCsWarehouse(@RequestBody WarehouseDO csWarehourseDO) {
        return warehouseService.saveCsWarehouse(csWarehourseDO);
    }

    /**
     * 启用停用寄售库房
     *
     * @param warehouseCode
     * @param status
     * @return
     */
    @RequestMapping(value = "/updateCsWarehourseStatus", method = RequestMethod.GET)
    public ResultVo<String> updateCsWarehourseStatus(@RequestParam String warehouseCode, Integer status) {
        return csStockApplyService.updateCsWarehouseStatus(warehouseCode, status);
    }

    /**
     * 导入备库型号清单设置
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/importCsStockSettingData", method = RequestMethod.POST)
    public ResultVo<String> importCsStockSettingData(@RequestBody MultipartFile file, @RequestParam Integer type) {
        return csStockApplyService.importCsStockSettingData(file, type);
    }

    /**
     * 删除设置型号清单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteCsStockSettingById", method = RequestMethod.GET)
    public ResultVo<String> deleteCsStockSettingById(@RequestParam Long id) {
        return csStockApplyService.deleteCsStockSettingById(id);
    }

    /**
     * 修改备库清单
     *
     * @param csStockSettingDTO
     * @return
     */
    @RequestMapping(value = "/updateCsStockSetting", method = RequestMethod.POST)
    public ResultVo<String> updateCsStockSetting(@RequestBody CsStockSettingDTO csStockSettingDTO) {
        return csStockApplyService.updateCsStockSetting(csStockSettingDTO);
    }

    /**
     * 仓库清单
     * 库房代码-库房名称
     *
     * @param warehouseCode
     * @return
     */
    @RequestMapping(value = "/listWarehourseStockCode", method = RequestMethod.GET)
    public ResultVo<List<WarehouseStockCodeVO>> listWarehourseStockCode(@RequestParam String warehouseCode, String warehouseType) {
        return warehouseService.listWarehouseStockCode(warehouseCode, warehouseType);
    }

    /**
     * 需退货数据
     *
     * @param agentNo
     * @param warehouseCode
     * @return
     */
    @RequestMapping(value = "/listCalcReturn", method = RequestMethod.GET)
    public ResultVo<List<CsReturnCalcVo>> listCalcReturn(@RequestParam String agentNo, @RequestParam String warehouseCode, @RequestParam Integer calcType) {
        return csStockApplyService.listCalcReturn(agentNo, warehouseCode, calcType);
    }

    @RequestMapping(value = "/listCalcReturnMaster", method = RequestMethod.POST)
    public ResultVo<PageInfo<CsTmpReturnVO>> listCalcReturnMaster(@RequestBody CsTmpReturnDTO dto) {
        return csStockApplyService.listCalcReturnMaster(dto);
    }

    /**
     * 查询入库数据
     *
     * @param requestDTO
     * @return
     */
    @RequestMapping(value = "/listCsImportDetail", method = RequestMethod.POST)
    public ResultVo<PageInfo<CsImportDetailVO>> listCsImportDetail(@RequestBody CsImportDetailRequestDTO requestDTO) {
        return csStockApplyService.listCsImportDetail(requestDTO);
    }

    /**
     * 查询出库数据
     *
     * @param requestDTO
     * @return
     */
    @RequestMapping(value = "/listCsExportDetail", method = RequestMethod.POST)
    public ResultVo<PageInfo<CsExportDetailVO>> listCsExportDetail(@RequestBody CsExportDetailRequestDTO requestDTO) {
        return csStockApplyService.listCsExportDetail(requestDTO);
    }


    /**
     * 查询退货数据
     *
     * @param requestDTO
     * @return
     */
    @RequestMapping(value = "/listReturnApply", method = RequestMethod.POST)
    public ResultVo<PageInfo<CsReturnApplyVO>> listReturnApply(@RequestBody CsReturnApplyRequestDTO requestDTO) {
        return csStockApplyService.listReturnApply(requestDTO);
    }


    /**
     * 查询退货明细数据
     *
     * @param requestDTO
     * @return
     */
    @RequestMapping(value = "/listReturnDetail", method = RequestMethod.POST)
    public ResultVo<PageInfo<CsReturnDetailVO>> listReturnDetail(@RequestBody CsReturnDetailRequestDTO requestDTO) {
        return csStockApplyService.listCsReturnApplyDetail(requestDTO);
    }

    /**
     * 生成寄售退货申请清单号
     *
     * @param agentNo
     * @param warehouseCode
     * @return
     */
    @RequestMapping(value = "/createReturnApply", method = RequestMethod.GET)
    public ResultVo<String> createReturnApply(@RequestParam String agentNo, @RequestParam String warehouseCode) {
        return csStockApplyService.createReturnApply(agentNo, warehouseCode);
    }

    @RequestMapping(value = "/printReturnApply", method = RequestMethod.GET)
    public void printReturnApply(HttpServletResponse response, @RequestParam("applyId") Integer applyId) {
        ResultVo<PrintCsReturnDTO> pageData = csStockApplyService.printCsReturnByApplyId(applyId);
        if (pageData == null) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("img", Constants.IMG);
        map.put("applyId", pageData.getData().getApplyId());
        map.put("passTime", DateUtil.dateToDateTimeString(pageData.getData().getPrintTime()));
        map.put("returnCompany", pageData.getData().getReturnCompany());
        map.put("printTime", DateUtil.dateToDateTimeString(pageData.getData().getPrintTime()));
        map.put("contactPsn", pageData.getData().getContactPsn());
        map.put("contactTelno", pageData.getData().getContactTelno());
        map.put("warehouseName", pageData.getData().getWarehouseName());
        map.put("adress", pageData.getData().getAdress());
        map.put("linkman", pageData.getData().getLinkman());
        map.put("smcPhone", pageData.getData().getSmcPhone());
        PrintCsReturnDTO printCsReturnDTO = new PrintCsReturnDTO();
        printCsReturnDTO.setCsReturnData(pageData.getData().getCsReturnData());
        List<PrintCsReturnDTO> printCsReturnDTOS = new ArrayList<>(1);
        printCsReturnDTOS.add(printCsReturnDTO);
        InputStream inputStream = FileUtil.getTemplate("template/jasper/csReturnApply.jasper");
//        Map<String, Object> params = new HashMap<>();
        try {
            String fileName = "服务备库退货申请.pdf";
            JasperHelper.showPdf(fileName, inputStream, response, map, printCsReturnDTOS);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/listMonthBalance", method = RequestMethod.POST)
    public ResultVo<PageInfo<CsModelBalanceVO>> createReturnApply(@RequestBody CsBalcenQryRequest request) {
        return csStockApplyService.listMonthBalance(request);
    }


    @RequestMapping(value = "/printBalance", produces = MediaType.APPLICATION_PDF_VALUE, method = RequestMethod.POST)
    public void printBalance(@RequestBody CsBalcenQryRequest request, HttpServletResponse response) {
        csStockApplyService.printBalance(request, response);
//        ResultVo<PageInfo<CsModelBalanceVO>> resultVo = this.csStockApplyService.listMonthBalance(request);
//
//        if (resultVo == null || !resultVo.isSuccess()) {
//            return;
//        }
//        CsWarehouseVO csWarehouse = csStockApplyService.getCsWarehouse(request.getAgentNo(), request.getWarehouseCode());
//        if (csWarehouse == null) {
//            return;
//        }
//        List<PrintCsBalanceVO> csBalanceData = BeanCopyUtil.copyList(resultVo.getData().getList(), PrintCsBalanceVO.class);
//
//        PrintCsBalanceDTO csBalanceDTO = new PrintCsBalanceDTO();
//        csBalanceDTO.setCsBalanceData(csBalanceData);
//        Map<String, Object> params = new HashMap<>();
//        params.put("warehouseName", csWarehouse.getWarehouseName());
//        params.put("monthDate", request.getMonthDate());
//        List<PrintCsBalanceDTO> list = new ArrayList<>(1);
//        list.add(csBalanceDTO);
//        InputStream inputStream = FileUtil.getTemplate("template/jasper/csBalance.jasper");
//        try {
//            String fileName = "委托在库月次报告.pdf";
//            JasperHelper.showPdf(fileName, inputStream, response, params, list);
//        } catch (JRException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @RequestMapping(value = "/updateCsTmpReturnCalcDataById", method = RequestMethod.POST)
    public ResultVo<String> updateCsTmpReturnCalcDataById(@RequestBody CsReturnCalcVo calcVo) {
        return csStockApplyService.updateCsTmpReturnCalcDataById(calcVo);
    }

    @RequestMapping(value = "/deleteCsReturnApplyById", method = RequestMethod.GET)
    public ResultVo<String> deleteCsReturnApplyById(@RequestParam("id") Integer id) {
        return csStockApplyService.deleteCsReturnApplyById(id);
    }

    @RequestMapping(value = "/calcBalance", method = RequestMethod.GET)
    public ResultVo<String> calcBalance(@RequestParam("monthDate") Date monthDate) {
        return csStockApplyService.calcBalance(monthDate);
    }

    @RequestMapping(value = "/exportCalcRetrunData", method = RequestMethod.POST)
    public void exportCalcRetrunData() {
        csStockApplyService.exportCalcRetrunData();
    }

    @RequestMapping(value = "/exportCsBalanceData", method = RequestMethod.POST)
    public void exportCsBalanceData(@RequestBody CsBalcenQryRequest request) {
        csStockApplyService.exportCsBalanceData(request);
    }

    @RequestMapping(value = "/getCalcbanceDate", method = RequestMethod.GET)
    public ResultVo<List<CsBalanceCalcMasterDO>> getCalcbanceDate() {
        return csStockApplyService.getCalcbanceDate();
    }

    @RequestMapping(value = "/updateCsBalanceDateById", method = RequestMethod.GET)
    public ResultVo<String> updateCsBalanceDateById(@RequestParam("id") Integer id) {
        return csStockApplyService.updateCsBalanceDateById(id);
    }

    @RequestMapping(value = "/updateCsBalaceMothDate", method = RequestMethod.POST)
    public ResultVo<String> updateCsBalaceMothDate(@RequestBody CsBalanceCalcMasterDO masterDO) {
        return csStockApplyService.updateCsBalaceMothDate(masterDO);
    }

    @RequestMapping(value = "/exportCsImpData", method = RequestMethod.POST)
    public void exportCsImpData(@RequestBody CsImportDetailRequestDTO requestDTO) {
        csStockApplyService.exportCsImpData(requestDTO);
    }

    @RequestMapping(value = "/exportCsExpData", method = RequestMethod.POST)
    public void exportCsExpData(@RequestBody CsExportDetailRequestDTO requestDTO) {
        csStockApplyService.exportCsExpData(requestDTO);
    }

    @RequestMapping(value = "/sendCsReturnApplyToEmail", method = RequestMethod.GET)
    public ResultVo<String> sendCsReturnApplyToEmail(@RequestParam("applyId") Integer applyId) {
        return csStockApplyService.sendCsReturnApplyToEmail(applyId);
    }

}
