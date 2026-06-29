package com.smc.smccloud.web;

import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.eta.FindETADataDto;
import com.sales.ops.dto.purchase.OpsRequestPurchaseInterceptConfigVO;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.adapter.*;
import com.smc.smccloud.model.delivery.CanUseQtyAndIsBinVO;
import com.smc.smccloud.model.delivery.DeliveryInfo;
import com.smc.smccloud.model.delivery.WarehouseSendDateVO;
import com.smc.smccloud.model.interceptConfig.OpsRequestpurchaseInterceptConfigDO;
import com.smc.smccloud.model.shikomi.ShikomiBudgetDO;
import com.smc.smccloud.model.shikomi.ShikomiBudgetRequest;
import com.smc.smccloud.model.shikomi.ShikomiBudgetVO;
import com.smc.smccloud.service.DeliveryService;
import com.smc.smccloud.service.IntercepterConfigService;
import com.smc.smccloud.service.SMSAdapterService;
import com.smc.smccloud.service.ShikomiService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Author: B90034
 * Date: 2022-03-14 14:12
 * Description:
 */
@RequestMapping("/product/SMSAdapter")
@RestController
public class SMSAdapterController {

    @Resource
    private SMSAdapterService smsAdapterService;

    @Resource
    private DeliveryService deliveryService;

    @Resource
    private ShikomiService shikomiService;

    @Resource
    private IntercepterConfigService intercepterConfigService;
    /**
     * 根据营业所代码查询可补库分库
     *
     * @param deptNo 营业所代码
     * @return 可补库分库信息
     */
    @RequestMapping(value = "/stockCode/findSubTreasury", method = RequestMethod.GET)
    public ResultVo<List<StockCode>> findSubTreasury(@RequestParam("deptNo") String deptNo) {
        return ResultVo.success(smsAdapterService.findSubTreasury(deptNo));
    }

    /**
     * 根据营业所代码查询货物所在地
     *
     * @param deptNo 营业所代码
     * @return 营业所货物所在仓库
     */
    @RequestMapping(value = "/stockCode/findGoodsLocation", method = RequestMethod.GET)
    public ResultVo<List<StockCode>> findGoodsLocation(@RequestParam("deptNo") String deptNo) {
        return ResultVo.success(smsAdapterService.findGoodsLocation(deptNo));
    }

    /**
     * (门户产品查询库存信息)
     * 根据型号和营业所代码查询型号的库存信息
     *
     * @param modelNo 型号
     * @param deptNo  营业所
     * @return 库存信息
     */
    @RequestMapping(value = "/inventory/detail", method = RequestMethod.GET)
    public ResultVo<List<InventoryInfo>> detail(@RequestParam("modelNo") String modelNo,
                                                @RequestParam(value = "deptNo", required = false) String deptNo) {
        List<InventoryInfo> result = smsAdapterService.getInventoryDetailByDeptNo(modelNo, deptNo);
        if (result == null) {
            return ResultVo.failure("型号不存在");
        } else {
            return ResultVo.success(result);
        }
    }

    /**
     * 返回某型号的所有库存集合列表：拆分型号库存显示使用
     */
    @RequestMapping(value = "/inventory/findInventoryList", method = RequestMethod.POST)
    public ResultVo<List<Inventory>> findInventoryList(@RequestBody List<String> modelNoList,
                                                       @RequestParam("deptNo") String deptNo) {
        List<Inventory> result = smsAdapterService.findInventoryList(modelNoList, deptNo);
        if (result == null) {
            return ResultVo.failure("OrderExceptionCode.NOT_EXIST_DEPTNO",
                    "SpringContextUtil.getMessage(OrderExceptionCode.NOT_EXIST_DEPTNO)");
        } else {
            return ResultVo.success(result);
        }
    }

    /**
     * 根据型号列表、部门代码、客户代码查询有效库存
     *
     * @param modelNo    型号列表
     * @param deptNo     部门代码
     * @param customerNo 客户代码
     * @return modelNo, avaQty
     */
    @RequestMapping(value = "/inventory/quantityCanUseBatch", method = RequestMethod.POST)
    public ResultVo<Map<String, Integer>> getQuantityCanUseBatch(@RequestBody List<String> modelNo,
                                                                 @RequestParam("deptNo") String deptNo,
                                                                 @RequestParam(value = "customerNo", required = false) String customerNo) {
        return smsAdapterService.getQuantityCanUseBatch(modelNo, deptNo, customerNo);
    }

    /**
     * 批量查询型号的可用数量
     *
     * @param modelNo 型号列表
     * @param deptNo  部门代码
     * @return 型号的可用数量
     */
    @RequestMapping(value = "/inventory/quantityCanUseAllBatch", method = RequestMethod.POST)
    public ResultVo<Map<String, Integer>> quantityCanUseAllBatch(@RequestBody List<String> modelNo,
                                                                 @RequestParam(value = "deptNo", required = false) String deptNo) {
        return smsAdapterService.getQuantityCanUseBatch(modelNo, deptNo, null);
    }

    /**
     *  门户物料号维护批量导入
     * -根据部门代码、客户代码、型号集合查询可用库存
     *
     * @param infoList customerNo、modelNo
     * @param deptNo   部门代码
     * @return 客户型号可用库存
     */
    @RequestMapping(value = "/inventory/getMaterialModelCanUseQtyBatch", method = RequestMethod.POST)
    public ResultVo<List<MaterialModelInfo>> getMaterialModelCanUseQtyBatch(@RequestBody List<MaterialModelInfo> infoList,
                                                                            @RequestParam("deptNo") String deptNo) {
        return smsAdapterService.getMaterialModelCanUseQtyBatch(infoList, deptNo);
    }

    /**
     * 根据操作人获取可补库分库库房
     *
     * @param dataAuthoritySearchCondition
     * @param deptNo
     * @return
     */
    @RequestMapping(value = "/inventory/findSubTreasuryByOperator", method = RequestMethod.POST)
    public ResultVo<List<StockCode>> findSubTreasuryByOperator(@RequestBody DataAuthoritySearchCondition dataAuthoritySearchCondition,
                                                               @RequestParam("deptNo") String deptNo) {
        return smsAdapterService.findSubTreasuryByOperator(dataAuthoritySearchCondition, deptNo);
    }

    /**
     * 根据shikomiNo获取SHIKOMI
     *
     * @param answerNo
     * @return
     */
    @RequestMapping(value = "/shikomi/findByNoBatch", method = RequestMethod.POST)
    public ResultVo<List<ShikomiInfo>> getByNoBatch(@RequestBody List<String> answerNo) {

        return smsAdapterService.getByNoBatch(answerNo);
    }

    /**
     * 根据型号获取shikomi是否存在
     *
     * @param conditionList
     * @return
     */
    @RequestMapping(value = "/shikomi/findByModelNoList", method = RequestMethod.POST)
    public ResultVo<List<ShikomiInfo>> findByModelNoList(@RequestBody List<ShikomiCondition> conditionList) {
        return smsAdapterService.findByModelNoList(conditionList);
    }

    /**
     * 查询可预占库存：仅大库库房
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/inventory/findCanPreInventory", method = RequestMethod.POST)
    public ResultVo<Map<String, List<Inventory>>> canPreInventory(@RequestBody InventoryCondition condition) {
        Map<String, List<Inventory>> result = smsAdapterService.getCanPreInventory(condition);
        if (result == null) {
            return ResultVo.failure();
        }
        return ResultVo.success(result);
    }


    /**
     * 客户调库：型号有效库存。返回的库存满足所有型号需求
     */
    @RequestMapping(value = "/inventory/findCanUseByDeptCustomer", method = RequestMethod.POST)
    public ResultVo<CanTransInventory> canUse(@RequestBody InventoryCondition condition) {
        return ResultVo.success(smsAdapterService.getCanUseByDeptCustomer(condition));
    }

    /**
     * 获取订单型号参考货期
     *
     * @param info 订单型号信息
     * @return 订单型号参考货期
     */
    @RequestMapping(value = "/delivery/findDeliveryDate", method = RequestMethod.POST)
    public ResultVo<DeliveryInfo> findDeliveryDate(@RequestBody DeliveryInfo info) {
        // 计算参考货期
        return smsAdapterService.getDeliveryDay(info);
    }

    @RequestMapping(value = "/delivery/findDeliveryCanUseInvQtyAndIsBin", method = RequestMethod.POST)
    public ResultVo<List<CanUseQtyAndIsBinVO>> findDeliveryCanUseInvQtyAndIsBin(@RequestBody List<FindETADataDto> dataList) {
        // 计算参考货期
        return deliveryService.getDeliveryIsBInAndCanUseQty(dataList);
    }


    /**
     * 计算物流发货期
     */
    @RequestMapping(value = "/delivery/findWarehouseSendDate", method = RequestMethod.POST)
    public ResultVo<DeliveryInfo> findWarehouseSendDate(@RequestBody DeliveryInfo info) {
        return smsAdapterService.getWarehouseSendDate(info);
    }

//    /**
//     * 反算物流发货日  接口转移到adapter服务
//     */
//    @RequestMapping(value = "/delivery/findWarehouseSendDateByOrderNo", method = RequestMethod.POST)
//    public ResultVo<List<WarehouseSendDateVO>> findWarehouseSendDateByOrderNo(@RequestBody List<WarehouseSendDateVO> info) {
//        return smsAdapterService.getWarehouseSendDateByOrderNo(info);
//    }

    /**
     * 型号模糊查询
     */
    @RequestMapping(value = "/inventory/findFuzzyInventory", method = RequestMethod.POST)
    public ResultVo<PageInfo<InventoryFuzzySearch>> findFuzzyInventory(@RequestBody FuzzySearchCondition condition,
                                                                       @RequestParam("pageNumber") Integer pageNumber,
                                                                       @RequestParam("pageSize") Integer pageSize) {
        Page page = new Page();
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        return ResultVo.success(smsAdapterService.getFuzzyInventory(condition, page));
    }

    /**
     * 查询型号的拆分型号信息
     *
     * @param modelNo 型号
     * @return 拆分型号信息
     */
    @RequestMapping(value = "/inventory/getProductBomByModelNo", method = RequestMethod.GET)
    public ResultVo<List<ProductBomInventory>> getProductBomByModelNo(@RequestParam("modelNo") String modelNo) {
        return smsAdapterService.getProductBomByModelNo(modelNo);
    }

    // 测试mybatis全局超时时间是否配置ok
    @GetMapping("/testMybatisTimeOut")
    public ResultVo<String> testMybatisTimeOut() {
        return smsAdapterService.testMybatisTimeOut();
    }

    @PostMapping("/findShikomiBudget")
    public ResultVo<PageInfo<ShikomiBudgetVO>> findShikomiBudget(@RequestBody ShikomiBudgetRequest request) {
        return shikomiService.findShikomiBudget(request);
    }

    @PostMapping("/exportShikomiBudget")
    public void exportShikomiBudget(@RequestBody ShikomiBudgetRequest request, HttpServletResponse response) {
        shikomiService.exportShikomiBudget(request,response);
    }

    @GetMapping("/shikomiInspectionCalc")
    public ResultVo<String> shikomiInspectionCalc() {
       return shikomiService.shikomiInspectionCalcNew();
    }

    @GetMapping("/findAdjustBatchNo")
    public ResultVo<List<ShikomiBudgetDO>> findAdjustBatchNo(@RequestParam("batchNo") String batchNo) {
        return shikomiService.findAdjustBatchNo(batchNo);
    }

    @GetMapping("/downloadTemplate")
    public void  downloadTemplate() {
        intercepterConfigService.downloadTemplate();
    }

    /**
     * 拦截配置数据导入
     */
    @PostMapping("/importIntercepterData")
    public ResultVo<String> importData(@RequestParam("file") MultipartFile file, @RequestParam("optUser") String optUser) {
        return intercepterConfigService.importData(file,optUser);
    }

    @PostMapping("/addData")
    public ResultVo<String> addData(@RequestBody OpsRequestPurchaseInterceptConfigVO item) {
        return intercepterConfigService.addData(item);
    }


}
