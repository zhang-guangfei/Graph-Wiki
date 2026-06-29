package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.SlowMovingModel.SlowMovingModelVO;
import com.smc.smccloud.model.SlowMovingModel.SlowMovingRequest;
import com.smc.smccloud.model.delivery.DeliveryInfo;
import com.smc.smccloud.model.product.*;
import com.smc.smccloud.model.shikomi.*;
import com.smc.smccloud.model.supplier.SupplierVo;
import com.smc.smccloud.service.hystrix.ProductServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 发布去掉 ： url = "http://10.116.194.236:8101"
 */
@FeignClient(name = "product-service",
        contextId = "ops-product",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = ProductServiceFeignHystrix.class)
public interface ProductServiceFeignApi {

    /**
     * 是否可以使用shikomi
     *
     * @param modelNo
     * @param shikomiNo
     * @param customerNo 客户代码 不可为空
     * @return status 0- 无效 1-有效 2-可借用,无效原因看answerText
     */
    @RequestMapping(value = "/product/shikomi/canUseShikomi", method = RequestMethod.POST)
    ResultVo<ShikomiVO> canUseShikomi(@RequestParam("modelNo") String modelNo, @RequestParam("shikomiNo") String shikomiNo, @RequestParam("customerNo") String customerNo);

    /**
     * 查询客户可用的shikomi
     *
     * @param modelNo
     * @param customerNo 客户代码 不可为空
     * @return status 0- 无效 1-有效 2-可借用,无效原因看answerText
     */
    @RequestMapping(value = "/product/shikomi/listCustomerShikomi", method = RequestMethod.POST)
    ResultVo<List<ShikomiVO>> listCustomerShikomi(@RequestParam("modelNo") String modelNo, @RequestParam("customerNo") String customerNo);


    /**
     * 预约shikomi
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/product/shikomi/prepareShikomi", method = RequestMethod.POST)
    ResultVo<String> prepareShikomi(@RequestBody ShikomiPrepareDTO dto);

    /**
     * 查询分页数据
     *
     * @return
     */
    @RequestMapping(value = "/product/shikomi/listShikomi", method = RequestMethod.POST)
    ResultVo<PageInfo<ShikomiVO>> listShikomi(@RequestBody ShikomiDataVO data);

    /**
     * 添加或修改shikomi
     *
     * @param shikomiVO
     * @return
     */
    @RequestMapping(value = "/product/shikomi/saveShikomidata", method = RequestMethod.POST)
    ResultVo<String> saveShikomidata(@RequestBody ShikomiVO shikomiVO);

    /**
     * 增加许可用户
     *
     * @param customerVO
     * @return
     */
    @RequestMapping(value = "/product/shikomi/saveCustomerdata", method = RequestMethod.POST)
    ResultVo<String> saveCustomerdata(@RequestBody ShikomiCustomerVO customerVO);

    /**
     * 导入日本回复文件
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/product/shikomi/importJPApplyFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.POST)
    ResultVo<String> importJPReplyFile(@RequestPart("file") MultipartFile file);

    /**
     * 导入中国回复文件
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/product/shikomi/importCNReplyFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.POST)
    ResultVo<String> importCNReplyFile(@RequestPart("file") MultipartFile file);

    /**
     * 自动导入shikomi文件
     *
     * @return
     */
    @RequestMapping(value = "/product/shikomi/importShikomiFile", method = RequestMethod.POST)
    ResultVo<String> autoImportShikomiFile();

    /**
     * 根据modelNo 查 price
     */
    @RequestMapping(value = "/product/findByModelNo", method = RequestMethod.GET)
    ResultVo<ProductPriceVO> findPriceByModelNo(@RequestParam("modelNo") String modelNo);

    /**
     * 添加关联主型号
     *
     * @param shikomiVO
     * @return
     */
    @RequestMapping(value = "/product/shikomi/addRelationModel", method = RequestMethod.POST)
    ResultVo<String> addRelationModel(@RequestBody ShikomiVO shikomiVO);

    /**
     * 推送点检
     *
     * @return
     */
    @RequestMapping(value = "/product/shikomi/sendInspection", method = RequestMethod.POST)
    ResultVo<String> sendInspectionReport();

    /**
     * @param dto
     * @return
     */
    @RequestMapping(value = "/product/shikomi/answerInspection", method = RequestMethod.POST)
    ResultVo<String> answerInspection(@RequestBody ShikomiInspectionAnsewrDTO dto);

    /**
     * 根据shikomiNo查inspection表
     *
     * @param shikomiNo
     * @return
     */
    @RequestMapping(value = "/product/shikomi/findInspectionInfo", method = RequestMethod.POST)
    ResultVo<List<ShikomiInspectionVO>> findInspectionInfoById(@RequestParam(value = "shikomiNo") String shikomiNo);

    @RequestMapping(value = "/product/shikomi/getShikomiDataByNo", method = RequestMethod.GET)
    ResultVo<ShikomiVO> getShikomiDataByNo(@RequestParam("shikomiNo") String shikomiNo, @RequestParam("modelNo") String modelNo, @RequestParam("supplierNo") String supplierNo);

    /**
     * 分页查询shikomi中止和继续信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/product/shikomi/listPageShikomiInspection", method = RequestMethod.POST)
    ResultVo<PageInfo<ShikomiInspectionVO>> listPageShikomiInspection(@RequestBody ShikomiInspectionRequest request);

    @RequestMapping(value = "/product/shikomi/exportShikomiInspectionData", method = RequestMethod.POST)
    void exportShikomiInspectionData(@RequestBody ShikomiInspectionRequest request);

    /**
     * 根据shikomiNo查询客户信息
     *
     * @param shikomiNo
     * @return
     */
    @RequestMapping(value = "/product/shikomi/findCustomerDataByNo", method = RequestMethod.GET)
    ResultVo<List<ShikomiCustomerVO>> findCustomerDataByNo(@RequestParam(value = "shikomiNo") String shikomiNo);

    /**
     * 根据型号查E价
     *
     * @param modelNo 型号
     * @return E价
     */
    @RequestMapping(value = "/product/shikomi/getModelEPrice", method = RequestMethod.GET)
    ResultVo<String> getModelEPrice(@RequestParam(value = "modelNo") String modelNo);

    /**
     * 查询产品批量E价
     *
     * @param modelNo  型号
     * @param quantity 数量
     * @return 批量E价
     */
    @RequestMapping(value = "/product/shikomi/getModelLotPrice", method = RequestMethod.GET)
    ResultVo<String> getModelLotPrice(@RequestParam("modelNo") String modelNo, @RequestParam("quantity") Integer quantity);

    /**
     * 批量型号查询型号价格信息
     * @param modelNos
     * @return
     */
    @RequestMapping(value = "/product/shikomi/listProductPrice", method = RequestMethod.GET)
    ResultVo<List<ProductPriceVO>> listProductPrice(@RequestParam("modelNos") List<String> modelNos);
    /**
     * 根据型号查询供应商
     *
     * @param modelNo 型号
     * @return 供应商
     */
    @RequestMapping(value = "/product/supplier/getSupplierNoByModelNo", method = RequestMethod.GET)
    ResultVo<String> getSupplierNoByModelNo(@RequestParam(value = "modelNo") String modelNo);

    /**
     * 查询产品信息for下单
     *
     * @param modelNo
     * @return
     */
    @RequestMapping(value = "/product/model/getModelInfoForOrder", method = RequestMethod.GET)
    ResultVo<OrderModelInfoVO> getModelInfoForOrder(@RequestParam(value = "modelNo") String modelNo);

    /**
     * 根据型号查询该型号的最小包装数
     *
     * @param modelNo 型号
     * @return 最小包装数
     */
    @RequestMapping(value = "/product/product/getMinPackUnitByModelNo", method = RequestMethod.GET)
    ResultVo<Integer> getMinPackUnitByModelNo(@RequestParam(value = "modelNo") String modelNo);

    /**
     * 根据型号查询该产品信息
     *
     * @param modelNo 型号
     * @return 产品信息
     */
    @RequestMapping(value = "/product/product/getProductInfoByModelNo", method = RequestMethod.GET)
    ResultVo<ProductInfoVO> getProductInfoByModelNo(@RequestParam(value = "modelNo") String modelNo);

    /**
     * 终止和继续申请
     *
     * @param info
     * @return
     */
    @RequestMapping(value = "/product/product/inspectApply", method = RequestMethod.POST)
    ResultVo<String> inspectApply(@RequestBody ShikomiInspectionDTO info);

    /**
     * 点检处理
     *
     * @param info
     * @return
     */
    @RequestMapping(value = "/product/shikomi/inspectProcess", method = RequestMethod.POST)
    ResultVo<String> inspectProcess(@RequestBody ShikomiInspectionDTO info);

    @RequestMapping(value = "/product/shikomi/exportShikomi", method = RequestMethod.POST)
    void exportShikomi(@RequestBody ShikomiDataVO data);

    /**
     * 根据型号获取可用shikomi
     *
     * @param modelNo
     * @return
     */
    @RequestMapping(value = "/product/shikomi/getCanUseShikomi", method = RequestMethod.GET)
    ResultVo<List<ShikomiVO>> getCanUseShikomi(@RequestParam("modelNo") String modelNo);

    /**
     * 判断bin是否lot价
     */
    @RequestMapping(value = "/product/price/isLotPrice", method = RequestMethod.GET)
    ResultVo<Boolean> isLotPrice(@RequestParam("modelNo") String modelNo);

    /**
     * 更新在库在途数量
     */
    @RequestMapping(value = "/product/shikomi/calShikomiUpdateInfo", method = RequestMethod.GET)
    void calShikomiUpdateInfo();

    /**
     * 检测shikomi库存不足项并发送警告邮箱
     *
     * @return
     */
    @RequestMapping(value = "/product/shikomi/checkShikomiStcokInsufficient", method = RequestMethod.GET)
    Boolean checkShikomiStcokInsufficient();

    /**
     * 根据型号获取英文名称
     *
     * @param modelNo 型号
     * @return englishName
     */
    @RequestMapping(value = "/product/info/getProductEnglishName", method = RequestMethod.GET)
    ResultVo<String> getProductEnglishName(@RequestParam("modelNo") String modelNo);


    /**
     * 查询是否收纳品
     */
    @RequestMapping(value = "/product/info/listProductEos", method = RequestMethod.POST)
    ResultVo<List<ProductCashierVO>> listProductEos(@RequestBody List<String> modelNos);

    /**
     * 查询滞销品信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/product/slow/listSlowMovingModelData", method = RequestMethod.POST)
    ResultVo<PageInfo<SlowMovingModelVO>> listSlowMovingModelData(@RequestBody SlowMovingRequest request);

    /**
     * 导入滞销品信息
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/product/slow/importSlowModelData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.POST)
    ResultVo<String> importSlowModelData(@RequestPart("file") MultipartFile file);

    @RequestMapping(value = "/product/slow/updateSlowModelData", method = RequestMethod.POST)
    ResultVo<String> updateSlowModelData(@RequestBody SlowMovingModelVO modelVO);

    @RequestMapping(value = "/product/slow/delSlowModelData", method = RequestMethod.GET)
    ResultVo<String> delSlowModelData(@RequestParam("id") Integer id);

    @RequestMapping(value = "/product/slow/calcUpdateSlowModelOnHnad", method = RequestMethod.GET)
    ResultVo<String> calcUpdateSlowModelOnHnad();

    @RequestMapping(value = "/product/info/listProductInfoForShikomi", method = RequestMethod.POST)
    ResultVo<List<ProductForShikomiVO>> listProductInfoForShikomi(@RequestBody List<String> modelNos);

    @RequestMapping(value = "/product/info/importCNMadeOrder", method = RequestMethod.POST)
    ResultVo<String> importCNMProductModel();

    @RequestMapping(value = "/product/product/getPriceMast", method = RequestMethod.GET)
    ResultVo<ProductPriceDTO> getPriceMast(@RequestParam(value = "modelNo") String modelNo);

    /**
     * 根据型号查询产品备注信息
     *
     * @param modelNo 型号
     * @return 产品备注信息
     */
    @RequestMapping(value = "/product/product/getRemark", method = RequestMethod.GET)
    ResultVo<ProductRemark> getProductRemarkByModelNo(@RequestParam(value = "modelNo") String modelNo);



    /**
     * 根据名称模糊查询特殊产品拦截规则
     * @param request
     * @return
     */
    @RequestMapping(value = "/product/intercept/listProductInterceptRule", method = RequestMethod.POST)
    ResultVo<List<ProductInterceptRuleVO>> listProductInterceptRule(@RequestBody ProductInterceptRuleRequest request);


    /**
     * 根据名称模糊查询特殊产品拦截规则
     * @param request
     * @return
     */
    @RequestMapping(value = "/product/intercept/listProductInterceptRuleByPage", method = RequestMethod.POST)
    ResultVo<PageInfo<ProductInterceptRuleVO>> listProductInterceptRuleByPage(@RequestBody ProductInterceptRuleRequest request);

    /**
     * 导出数据
     * @param request
     */
    @RequestMapping(value = "/product/intercept/exportProductInterceptRule", method = RequestMethod.POST)
    void exportProductInterceptRule(@RequestBody ProductInterceptRuleRequest request );


    /**
     * 添加或更新特殊产品拦截规则
     */
    @RequestMapping(value = "/product/intercept/addOrUpdateInterceptRule", method = RequestMethod.POST)
    ResultVo<String> addOrUpdateInterceptRule(@RequestBody ProductInterceptRuleVO ruleVO);

    /**
     * 特殊产品拦截验证
     *
     * @param dto 验证信息
     * @return 验证结果
     */
    @RequestMapping(value = "/product/intercept/checkProductInterceptRule", method = RequestMethod.POST)
    ResultVo<List<ProductInterceptRuleDTO>> checkProductInterceptRule(@RequestBody ProductInterceptRuleDTO dto);

    @RequestMapping(value = "/product/intercept/deleteInterceptRule", method = RequestMethod.GET)
    ResultVo<String> deleteInterceptRule(@RequestParam(value = "id") Integer id);

    @RequestMapping(value = "/product/intercept/deleteInterceptRuleByIds", method = RequestMethod.POST)
    ResultVo<String> deleteInterceptRuleByIds(@RequestParam(value = "ids") List<Integer> ids);

    @RequestMapping(value = "/product/intercept/deleteInterceptRuleByCondition", method = RequestMethod.POST)
    ResultVo<String> deleteInterceptRuleByCondition(@RequestBody  ProductInterceptRuleRequest request);

    /**
     * 查询型号是否
     *
     * @param modelNo
     * @param customerNo
     * @param endUser
     * @return
     */
    @RequestMapping(value = "/product/intercept/getIsProductRestrict", method = RequestMethod.GET)
    ResultVo<Boolean> getIsProductRestrict(@RequestParam(value = "modelNo") String modelNo, @RequestParam(value = "customerNo") String customerNo,
                                           @RequestParam(value = "endUser") String endUser);


    /**
     * 获取物流交货期
     */
    @RequestMapping(value = "/delivery/getWarehouseSendDate", method = RequestMethod.POST)
    ResultVo<DeliveryInfo> findWarehouseSendDate(@RequestBody DeliveryInfo info);

    /**
     * 定时任务同步 importGPProductModel  调用sync_prodmast_gp存储过程
     */
    @RequestMapping(value = "/product/importGPProductModel", method = RequestMethod.GET)
    void importGPProductModel();

    /**
     * 是否错误型号
     *
     * @param modelNo 型号
     * @return true 是  false 不是
     */
    @RequestMapping(value = "/product/isErrorModelNo", method = RequestMethod.GET)
    ResultVo<Boolean> isErrorModelNo(@RequestParam("modelNo") String modelNo);

    /**
     * 是否收敛品
     *
     * @param modelNo 型号
     * @return true 是  false 不是
     */
    @RequestMapping(value = "/product/isEosModelNo", method = RequestMethod.GET)
    ResultVo<Boolean> isEosModelNo(@RequestParam("modelNo") String modelNo);

    /**
     * 是否中国产型号
     * true 是 false 不是
     */
    @RequestMapping(value = "/product/isModelOfCN", method = RequestMethod.GET)
    ResultVo<Boolean> isModelOfCN(@RequestParam("modelNo") String modelNo);

    /**
     * 校验型号并返回错误型号
     */
    @RequestMapping(value = "/product/checkAndReturnErrorModel", method = RequestMethod.POST)
    ResultVo<List<String>> checkAndReturnErrorModel(@RequestBody List<String> modelNoList);

    @RequestMapping(value = "/product/shikomi/listShikomiRegistData", method = RequestMethod.POST)
    ResultVo<List<ShikomiRegistDateCallbackDTO>> listShikomiRegistData(@RequestBody List<String> applyNos);

    /**
     * shikomi点检 发送邮件
     *
     * @return
     */
    @RequestMapping(value = "/product/shikomi/checkShikomiInspectionByDept", method = RequestMethod.POST)
    ResultVo<String> checkShikomiInspectionByDept();

    /**
     * shikomi点检计算
     * @param file
     * @return
     */
    @RequestMapping(value = "/product/shikomi/calcShikomiInspection", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.POST)
    ResultVo<String> calcShikomiInspection(@RequestPart("file") MultipartFile file);

    @RequestMapping(value = "/product/shikomi/checkStatus", method = RequestMethod.GET)
    ResultVo<String> checkStatus();

    /**
     * 取消预约shikomi
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/product/shikomi/CancelPrepareShikomi", method = RequestMethod.POST)
    ResultVo<String> cancelPrepareShikomi(@RequestBody ShikomiPrepareDTO dto);

    @RequestMapping(value = "/product/shikomi/delShikomiCustomerById", method = RequestMethod.GET)
    ResultVo<String> delShikomiCustomerById(@RequestParam("id") Integer id);

    @RequestMapping(value = "/product/shikomi/delShikomiByNo", method = RequestMethod.GET)
    ResultVo<String> delShikomiByNo(@RequestParam("shikomiNo") String shikomiNo, @RequestParam("modelNo") String modelNo, @RequestParam("supplierCode") String supplierCode);

    @RequestMapping(value = "/product/shikomi/importCNShikomiData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.POST)
    ResultVo<String> importCNShikomiData(@RequestPart("file") MultipartFile file);

    @RequestMapping(value = "/product/shikomi/getShikomiSupplier", method = RequestMethod.GET)
    ResultVo<List<SupplierVo>> getShikomiSupplier();

    @RequestMapping(value = "/product/shikomi/saveShikomiCache", method = RequestMethod.GET)
    ResultVo<String> saveShikomiCache();

    @RequestMapping(value = "/product/shikomi/importShikomiNoord", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.POST)
    ResultVo<String> importShikomiNoord(@RequestPart("file") MultipartFile file);

    @RequestMapping(value = "/product/shikomi/syncCNShikomiNoordQty", method = RequestMethod.GET)
    ResultVo<String> syncCNShikomiNoordQty();

    @RequestMapping(value = "/product/shikomi/calcShikomiQtyWarning", method = RequestMethod.GET)
    ResultVo<String> calcShikomiQtyWarning();

    @RequestMapping(value = "/product/shikomi/updateShikomiWarnQty", method = RequestMethod.POST)
    ResultVo<String> updateShikomiWarnQty(@RequestBody ShikomiWarnCallBackDTO dto);

    @RequestMapping(value = "/product/shikomi/shikomiWarningSendMH", method = RequestMethod.GET)
    ResultVo<String> shikomiWarningSendMH();

    /**
     * 根据型号获取重量信息
     *
     * @param modelNo
     * @return
     */
    @RequestMapping(value = "/product/product/getWeightByModelNo", method = RequestMethod.GET)
    ResultVo<ProductPhysicsVO> getWeightByModelNo(@RequestParam("modelNo") String modelNo);

    @RequestMapping(value = "/product/product/shikomiInspectionCalcNew", method = RequestMethod.GET)
    ResultVo<String> shikomiInspectionCalcNew();

    @RequestMapping(value = "/product/shikomi/upShikomiBudjet", method = RequestMethod.POST)
    ResultVo<String> upShikomiBudjet(@RequestBody ShikomiBudgetVO shikomiBudgetVO);

    @RequestMapping(value = "/product/shikomi/getSupplierByShikomi", method = RequestMethod.GET)
    ResultVo<ShikomiVO> getSupplierByShikomi(@RequestParam("shikomiNo") String shikomiNo);
}
