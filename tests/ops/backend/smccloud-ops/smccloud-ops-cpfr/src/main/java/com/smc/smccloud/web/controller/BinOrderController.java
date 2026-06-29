package com.smc.smccloud.web.controller;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.HROrganizationDO;
import com.smc.smccloud.model.bindata.BindataRequest;
import com.smc.smccloud.model.bindata.BindataVO;
import com.smc.smccloud.model.binorder.*;
import com.smc.smccloud.service.*;
import groovy.util.logging.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/cpfr")
@Slf4j
@CrossOrigin
public class BinOrderController {

    @Resource
    BinOrderCalcService binOrderCalcService;

    @Resource
    BinorderService binorderService;

    @Resource
    BindataService bindataService;

    @Resource
    BinTrialJobManageService binTrialJobManageService;
    @Resource
    BinTrialSalesBranchConfigService binTrialSalesBranchConfigService;

    @Resource
    ModelExpFreqByJobService modelExpFreqByJobService;

    @Resource
    OpsCommonService opsCommonService;

    @Resource
    DictCommonService dictCommonService;

    @RequestMapping(value = "/binorder/listCalcBinOrderDetail", method = RequestMethod.POST)
    public ResultVo<PageInfo<BinOrderDetailVO>> listCalcBinOrderDetail(@RequestBody BinOrderCalcQueryDTO dto) {
        return binOrderCalcService.listBinOrderDetail(dto);
    }

    @RequestMapping(value = "/binorder/listBinOrderApp", method = RequestMethod.POST)
    public ResultVo<PageInfo<BinOrderAppVO>> listBinOrderApp(@RequestBody BinOrderAppRequestDTO dto) {
        return binorderService.listBinOrderApp(dto);
    }

    @RequestMapping(value = "/binorder/approveBinOrder", method = RequestMethod.POST)
    public ResultVo<String> approveBinOrder(@RequestBody BinOrderApproveRequestDTO dto) {
        return binorderService.approveBinOrder(dto);
    }

    @RequestMapping(value = "/binorder/createOrder", method = RequestMethod.POST)
    public ResultVo<String> createOrder(@RequestBody BinOrderApproveRequestDTO dto) {
        return binorderService.createOrder(dto);
    }

    @RequestMapping(value = "/binorder/sendToInStock", method = RequestMethod.POST)
    public ResultVo<String> sendToInStock(@RequestBody BinOrderApproveRequestDTO dto) {
        return binorderService.sendToInStock(dto);
    }

    @RequestMapping(value = "/binorder/newBinOrderCalcId", method = RequestMethod.POST)
    public ResultVo<BinOrderCalcVO> newBinOrderCalcId(@RequestBody BinOrderCalcRequestDTO dto) {
        return binorderService.newBinOrderCalcId(dto);
    }

    @RequestMapping(value = "/binorder/calcBinOrder", method = RequestMethod.POST)
    public ResultVo<BinOrderCalcVO> calcBinOrder(@RequestBody BinOrderCalcRequestDTO dto) {
        return binorderService.calcBinOrder(dto);
    }

    @RequestMapping(value = "/binorder/applyBinOrder", method = RequestMethod.POST)
    public ResultVo<String> applyBinOrder(@RequestBody BinOrderApplyRequestDTO dto) {
        return binorderService.applyBinOrder(dto);
    }

    @RequestMapping(value = "/binorder/listBinOrderCalc", method = RequestMethod.POST)
    public ResultVo<List<BinOrderCalcVO>> listBinOrderCalc() {
        return binorderService.listBinOrderCalc();
    }

    @RequestMapping(value = "/binorder/updateBinOrderDlvDate", method = RequestMethod.POST)
    public ResultVo<String> updateBinOrderDlvDate(@RequestBody BinOrderUpdateDlvDateRequestDTO dto) {
        return binorderService.updateBinOrderDlvDate(dto);
    }

    @RequestMapping(value = "/binorder/batchUpdate", method = RequestMethod.POST)
    public ResultVo<String> batchUpdateBinOrder(@RequestBody BinOrderBatchUpdateDTO dto) {
        return binorderService.batchUpdateBinOrder(dto);
    }

    @RequestMapping(value = "/binorder/clearTransQty/{calcId}", method = RequestMethod.POST)
    public ResultVo<String> clearTransQty(@PathVariable("calcId") Long calcId) {
        return binorderService.clearTransQty(calcId);
    }




    /**
     * Bin计算数据修改及拆分数据更改
     *
     * @param fromId
     * @return
     */
    @RequestMapping(value = "/binorder/listBinOrderDetailSplit", method = RequestMethod.POST)
    public ResultVo<List<BinOrderDetailVO>> listBinOrderDetailSplit(@RequestParam("fromId") Long fromId) {
        return binorderService.listBinOrderDetailSplit(fromId);
    }

    /**
     * Bin计算数据修改及拆分数据更改
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/binorder/updateBinOrderDetail", method = RequestMethod.POST)
    public ResultVo<String> updateBinOrderDetail(@RequestBody BinOrderDetailUpdateDTO dto) {
        return binorderService.updateBinOrderDetail(dto);
    }

    /**
     * 批量添加bin订货明细
     *
     * @param list
     * @return
     */
    @RequestMapping(value = "/binorder/saveBinOrderDetails", method = RequestMethod.POST)
    public ResultVo<String> saveBinOrderDetails(@RequestBody List<BinOrderDetailVO> list) {
        return binorderService.saveBinOrderDetails(list);
    }

    /**
     * 导出bin基础数据
     *
     * @return
     */
    @RequestMapping(value = "/binorder/exportModelExpFreq", method = RequestMethod.POST)
    public void exportModelExpFreq(@RequestBody ModelExpFreqRequest request, HttpServletResponse response) {
        binOrderCalcService.exportModelExpFreq(request, response);
    }

    /**
     * 查找客户bin
     */
    @RequestMapping(value = "/binorder/listCstmBindata", method = RequestMethod.POST)
    public ResultVo<PageInfo<BindataVO>> listCstmBindata(@RequestBody BindataRequest request) {
        return binorderService.listCstmBindata(request);
    }

    /**
     * 结束Bin计算申请
     */
    @RequestMapping(value = "/binorder/finishbinordercalc", method = RequestMethod.POST)
    public ResultVo<String> finishbinordercalc(@RequestParam("id") Long id) {
        return binorderService.finishbinordercalc(id);
    }

    @RequestMapping(value = "/binorder/checkImpBindataStatus", method = RequestMethod.GET)
    public ResultVo<String> checkImpBindataStatus(@RequestParam("key") String key) {
        return binorderService.checkImpBindataStatus(key);
    }

    @RequestMapping(value = "/binorder/listBinRedisMessage", method = RequestMethod.POST)
    public ResultVo<List<RedisMessageVO>> listBinRedisMessage() {
        return binorderService.listBinRedisMessage();
    }

    @RequestMapping(value = "/binorder/exportCalcBinOrderData", method = RequestMethod.POST)
    public void exportCalcBinOrderData(@RequestBody BinOrderCalcQueryDTO dto) {
        binorderService.exportCalcBinOrderData(dto);
    }

    @RequestMapping(value = "/binorder/listBinDetailSplit", method = RequestMethod.POST)
    public ResultVo<PageInfo<BinOrderDetailSplitVO>> listBinDetailSplit(@RequestBody BinOrderDetailSplitRequestDTO dto) {
        return binorderService.listBinDetailSplit(dto);
    }

    /**
     * 批量判断是否是bin型号
     */
    @RequestMapping(value = "/binorder/isBinModelBatch", method = RequestMethod.POST)
    public ResultVo<List<BinModelNoVO>> isBinModelBatch(@RequestBody List<String> modelNos) {
        return bindataService.isBinModelBatch(modelNos);
    }

    /**
     * 查询型号订货中
     */
    @RequestMapping(value = "/binorder/listOrdingOrder", method = RequestMethod.GET)
    public ResultVo<List<OrdingOrderVO>> listOrdingOrder(@RequestParam("modelNo") String modelNo,
                                                         @RequestParam("warehouseCode") String warehouseCode,
                                                         @RequestParam("detailId") Long id) {
        return binorderService.listOrdingOrder(modelNo, warehouseCode, id);
    }

    /**
     * 查询型号订货中
     */
    @RequestMapping(value = "/binorder/getModelExpFreqMain", method = RequestMethod.GET)
    public ResultVo<ModelExpFreqMainVO> getModelExpFreqMain() {
        return binorderService.getModelExpFreqMain();
    }

    /**
     * 导出bin补货查询
     *
     * @return
     */
    @RequestMapping(value = "/binorder/exportBinDetailSplit", method = RequestMethod.POST)
    public void exportBinDetailSplit(@RequestBody BinOrderDetailSplitRequestDTO dto) {
        binorderService.exportBinDetailSplit(dto);
    }

    /**
     * 查询被预约数据
     */
    @RequestMapping(value = "/binorder/listPrepareOrderView", method = RequestMethod.GET)
    public ResultVo<List<PrepareOrderVO>> listPrepareOrderView(@RequestParam("modelNo") String modelNo,
                                                               @RequestParam("warehouseCode") String warehouseCode,
                                                               @RequestParam("detailId") Long id) {
        return binorderService.listPrepareOrderView(modelNo, warehouseCode,id);
    }
    /**
     * binTrial job 分页显示
     *
     * @param request
     * @return
     */

    @RequestMapping(value = "/binTrial/listBinTrialJobManageData", method = RequestMethod.POST)
    @ResponseBody
    ResultVo<PageInfo<BinTrialJobManageVO>> listBinTrialJobManageData(@RequestBody BinTrialJobRequestDTO request) {
//        System.out.println(JSONObject.toJSON(request));
        return binTrialJobManageService.listBinTrialJobManageData(request);
    }
    /**
     * binTrial job 保存
     *
     * @param jobManageVO
     * @return
     */
    @RequestMapping(value = "/binTrial/saveBinTrialJobManager", method = RequestMethod.POST)
    ResultVo<String> saveBinTrialJobManager(@RequestBody BinTrialJobManageVO jobManageVO) {
        return binTrialJobManageService.saveBinTrialJobManager(jobManageVO);
    }
    /**
     * binTrial job 根据ID集合删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/binTrial/deleteBinTrialJobManager", method = RequestMethod.POST)
    ResultVo<String> deleteBinTrialJobManager(@RequestParam("ids") List<Long> ids) {
        return binTrialJobManageService.deleteBinTrialJobManager(ids);
    }

    @RequestMapping(value = "/binTrial/sumitBinTrialJob", method = RequestMethod.POST)
    ResultVo<String> sumitBinTrialJob(@RequestParam("ids") List<Long> ids) {
        return binTrialJobManageService.sumitBinTrialJob(ids);
    }
    @RequestMapping(value = "/binTrial/getBinTrialJobManageData", method = RequestMethod.POST)
    ResultVo<List<BinTrialJobManageVO>> getBinTrialJobManageData(@RequestBody BinTrialJobRequestDTO request) {
        return binTrialJobManageService.getBinTrialJobManageData(request);
    }

    @RequestMapping(value = "/binTrial/copyBinTrialJobManager", method = RequestMethod.POST)
    ResultVo<String> copyBinTrialJobManager(@RequestParam("jobId") Long jobId) {
        return binTrialJobManageService.copyBinTrialJobManager(jobId);
    }

    ;

    @RequestMapping(value = "/binTrial/listBinTrialSalesBranchConfigData", method = RequestMethod.POST)
    ResultVo<PageInfo<BinTrialSalesBranchConfigVO>> listBinTrialSalesBranchConfigData(@RequestBody BinTrialSalesBranchConfigRequestDTO request) {

        Map<Long,String> mapIdJobNo=new HashMap<>();
        BinTrialJobRequestDTO binTrialJobRequestDTO ;
        ResultVo<List<BinTrialJobManageVO>> listResultVo =null;

//         if (PublicUtil.isNotEmpty(request.getWarehouseMaster()) && (PublicUtil.isEmpty(request.warehouseCode) || request.warehouseCode.length==0) ) {
//           ResultVo<List<String>> result=  binTrialSalesBranchConfigService.getWarehouseCodeByMasters(request.jobId,request.getWarehouseMaster());
//           if (result.isSuccess()) {
//               List<String> warehouseCodes = new ArrayList<>();
//               warehouseCodes.addAll(result.getData());
//               request.setWarehouseCode(warehouseCodes.toArray(new String[warehouseCodes.size()]));
//           }
//         }

         if (PublicUtil.isEmpty(request.jobId) && PublicUtil.isNotEmpty(request.jobNo)){
             for (Map.Entry<Long, String> entry : mapIdJobNo.entrySet()) {
                   if(request.jobNo.equalsIgnoreCase(entry.getValue())){
                     request.jobId =entry.getKey();
                 }
             }
             if(PublicUtil.isEmpty(request.jobId)) {
                  binTrialJobRequestDTO= new BinTrialJobRequestDTO();
                 binTrialJobRequestDTO.setJobNo(request.jobNo);
                  listResultVo= binTrialJobManageService.getBinTrialJobManageData(binTrialJobRequestDTO);
                 if (listResultVo.isSuccess())
                 {
                     if (PublicUtil.isNotEmpty(listResultVo.getData()) && listResultVo.getData().size() >=1) {
                         request.jobId = listResultVo.getData().get(0).getId();
                         mapIdJobNo.put(request.jobId,request.jobNo);
                     }
                     else{
                         return ResultVo.failure(request.jobNo+"此任务编号不存在。");
                     }
                 } else{
                     return ResultVo.failure(listResultVo.getMessage());
                 }

             }

         }

        ResultVo<PageInfo<BinTrialSalesBranchConfigVO>> resultVo = binTrialSalesBranchConfigService.listBinTrialSalesBranchConfigData(request);


        for (int i = 0; i < resultVo.getData().getList().size(); i++) {
            ResultVo<HROrganizationDO> hrOrganizationDOResultVo = opsCommonService.getHrOrganInfoByCode(resultVo.getData().getList().get(i).getSalesBranchId());
            if (PublicUtil.isNotEmpty(hrOrganizationDOResultVo.getData())) {
                String fullName = hrOrganizationDOResultVo.getData().getFullName();
                String[] department;
                if (Objects.nonNull(fullName)) {
                    // SMC投资管理有限公司_SMC自动化有限公司_北京分公司_北京营业部_京西所
                    department = fullName.split("_");
                    resultVo.getData().getList().get(i).setBranchName(hrOrganizationDOResultVo.getData().getName());
                    if (department.length >= 4) {
                        resultVo.getData().getList().get(i).setCompanyName(department[2]);
                        resultVo.getData().getList().get(i).setAreaName(department[3]);
                    } else if (department.length == 3) {
                        resultVo.getData().getList().get(i).setCompanyName(department[2]);
                        resultVo.getData().getList().get(i).setAreaName(department[2]);
                    }
                }
            }
//            String masterCode = dictCommonService.getMasterWarehouseByCode(resultVo.getData().getList().get(i).getWarehouseCodeUpdate());
//            if (Objects.isNull(masterCode)) {
//                masterCode = resultVo.getData().getList().get(i).getWarehouseCodeUpdate();
//            }
//            String masterName = opsCommonService.getWarehouseNameByCode(masterCode);
//            resultVo.getData().getList().get(i).setWarehouseMaster(masterCode);
//            resultVo.getData().getList().get(i).setMasterName(masterName);
            Long jobId=resultVo.getData().getList().get(i).getJobId();
            String jobNo= mapIdJobNo.get(jobId);
            if(PublicUtil.isEmpty(jobNo)) {
                binTrialJobRequestDTO= new BinTrialJobRequestDTO();
                binTrialJobRequestDTO.setId(jobId) ;
                listResultVo= binTrialJobManageService.getBinTrialJobManageData(binTrialJobRequestDTO);
                if (listResultVo.isSuccess()) {
                    if (PublicUtil.isNotEmpty(listResultVo.getData()) && listResultVo.getData().size() >= 1) {
                        jobNo = listResultVo.getData().get(0).getJobNo();
                        mapIdJobNo.put(jobId, jobNo);
                    }
                }
            }
            resultVo.getData().getList().get(i).setJobNo(jobNo);
        }
        return resultVo;
    }

    @RequestMapping(value = "/binTrial/saveBinTrialSalesBranchConfig", method = RequestMethod.POST)
    ResultVo<String> saveBinTrialSalesBranchConfig(@RequestBody BinTrialSalesBranchConfigVO configVO) {
        return binTrialSalesBranchConfigService.saveBinTrialSalesBranchConfig(configVO);
    }

    @RequestMapping(value = "/binTrial/deleteBinTrialSalesBranchConfig", method = RequestMethod.POST)
    ResultVo<String> deleteBinTrialSalesBranchConfig(@RequestParam("jobId") Long jobId, @RequestParam("ids") List<Long> ids) {
        return binTrialSalesBranchConfigService.deleteBinTrialSalesBranchConfig(jobId, ids);
    }


    @RequestMapping(value = "/binTrial/createBinTrialSalesBranchConfig", method = RequestMethod.POST)
    ResultVo<String> createBinTrialSalesBranchConfig(@RequestParam("jobId") Long jobId, @RequestParam("warehouseCode") String warehouseCode) {
        return binTrialSalesBranchConfigService.createBinTrialSalesBranchConfig(jobId, warehouseCode);
    }

    @RequestMapping(value = "/binTrial/addBinTrialSalesBranchConfig", method = RequestMethod.POST)
    ResultVo<String> addBinTrialSalesBranchConfig(@RequestParam("jobId") Long jobId, @RequestParam("warehouseCode") String warehouseCode, @RequestParam("branches") List<String> branches) {
        return binTrialSalesBranchConfigService.addBinTrialSalesBranchConfig(jobId, warehouseCode, branches);
    }

    @RequestMapping(value = "/binTrial/restoreBinTrialSalesBranchConfig", method = RequestMethod.POST)
    ResultVo<String> restoreBinTrialSalesBranchConfig(@RequestParam("jobId") Long jobId, @RequestParam("ids") List<Long> ids) {
        return binTrialSalesBranchConfigService.restoreBinTrialSalesBranchConfig(jobId, ids);
    }

    @RequestMapping(value = "/binTrial/importBinConfigureData", method = RequestMethod.POST)
    ResultVo<String> importBinConfigureData(@RequestParam("jobId") Long jobId, @RequestParam("file") MultipartFile file) {
        return binTrialSalesBranchConfigService.importBinConfigureData(jobId, file);
    }

    @RequestMapping(value = "/binTrial/exportBinConfigureData", method = RequestMethod.POST)
    void exportBinConfigureData(@RequestBody BinTrialSalesBranchConfigRequestDTO request, HttpServletResponse response) {
        binTrialSalesBranchConfigService.exportBinConfigureData(request, response);
    }

    @RequestMapping(value = "/binTrial/listBinTrialSalesBranchDetail", method = RequestMethod.POST)
    ResultVo<PageInfo<ModelExpFreqByJobVO>> listBinTrialSalesBranchDetail(@RequestBody ModelExpFreqByJobRequestDTO request) {
      return modelExpFreqByJobService.listBinTrialSalesBranchDetail(request);
    }

    @RequestMapping(value = "/binTrial/exporBinTrialSalesBranchDetail", method = RequestMethod.POST)
    void exporBinTrialSalesBranchDetail(@RequestBody ModelExpFreqByJobRequestDTO request, HttpServletResponse response) {
        modelExpFreqByJobService.exporBinTrialSalesBranchDetail(request, response);
    }

    @GetMapping("/bindata/dowmBinDataImport")
    public void dowmBinDataImport() {
        bindataService.dowmBinDataImport();
    }
}
