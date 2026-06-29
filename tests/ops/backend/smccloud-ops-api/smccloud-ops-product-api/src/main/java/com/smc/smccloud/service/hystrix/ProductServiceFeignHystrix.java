package com.smc.smccloud.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.SlowMovingModel.SlowMovingModelVO;
import com.smc.smccloud.model.SlowMovingModel.SlowMovingRequest;
import com.smc.smccloud.model.delivery.DeliveryInfo;
import com.smc.smccloud.model.product.*;
import com.smc.smccloud.model.shikomi.*;
import com.smc.smccloud.model.supplier.SupplierVo;
import com.smc.smccloud.service.ProductServiceFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Author: Denghui
 * Date: 2021-09-26 13:34
 * Description:
 */
@Component
public class ProductServiceFeignHystrix implements FallbackFactory<ProductServiceFeignApi> {

    @Override
    public ProductServiceFeignApi create(Throwable throwable) {
        return new ProductServiceFeignApi() {
            @Override
            public ResultVo<ShikomiVO> canUseShikomi(String modelNo, String shikomiNo, String customerNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<ShikomiVO>> listCustomerShikomi(String modelNo, String customerNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<PageInfo<ShikomiVO>> listShikomi(ShikomiDataVO data) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> saveShikomidata(ShikomiVO shikomiVO) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> saveCustomerdata(ShikomiCustomerVO customerVO) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> importJPReplyFile(MultipartFile file) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> importCNReplyFile(MultipartFile file) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> autoImportShikomiFile() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<ProductPriceVO> findPriceByModelNo(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> addRelationModel(ShikomiVO shikomiVO) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> sendInspectionReport() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> answerInspection(ShikomiInspectionAnsewrDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<ShikomiInspectionVO>> findInspectionInfoById(String shikomiNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<ShikomiVO> getShikomiDataByNo(String shikomiNo, String modelNo, String supplierNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<PageInfo<ShikomiInspectionVO>> listPageShikomiInspection(ShikomiInspectionRequest request) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public void exportShikomiInspectionData(ShikomiInspectionRequest request) {
                return;
            }

            @Override
            public ResultVo<List<ShikomiCustomerVO>> findCustomerDataByNo(String shikomiNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> getModelEPrice(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> getModelLotPrice(String modelNo, Integer quantity) {
                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
            public ResultVo<List<ProductPriceVO>> listProductPrice(List<String> modelNos){
                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
            public ResultVo<String> getSupplierNoByModelNo(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<OrderModelInfoVO> getModelInfoForOrder(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<Integer> getMinPackUnitByModelNo(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<ProductInfoVO> getProductInfoByModelNo(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> inspectApply(ShikomiInspectionDTO info) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> inspectProcess(ShikomiInspectionDTO info) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public void exportShikomi(ShikomiDataVO data) {
                return;
            }

            @Override
            public ResultVo<List<ShikomiVO>> getCanUseShikomi(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<Boolean> isLotPrice(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public void calShikomiUpdateInfo() {
                return;
            }

            @Override
            public Boolean checkShikomiStcokInsufficient() {
                return null;
            }

            @Override
            public ResultVo<String> getProductEnglishName(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<ProductCashierVO>> listProductEos(List<String> modelNos) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<PageInfo<SlowMovingModelVO>> listSlowMovingModelData(SlowMovingRequest request) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> importSlowModelData(MultipartFile file) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> updateSlowModelData(SlowMovingModelVO modelVO) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> delSlowModelData(Integer id) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> calcUpdateSlowModelOnHnad() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<ProductForShikomiVO>> listProductInfoForShikomi(List<String> modelNos) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> importCNMProductModel() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<ProductPriceDTO> getPriceMast(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<ProductRemark> getProductRemarkByModelNo(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<ProductInterceptRuleVO>> listProductInterceptRule(ProductInterceptRuleRequest request) {
                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
            public  ResultVo<PageInfo<ProductInterceptRuleVO>>  listProductInterceptRuleByPage(ProductInterceptRuleRequest request) {
                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
            public void  exportProductInterceptRule(ProductInterceptRuleRequest request) {
                  ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> addOrUpdateInterceptRule(ProductInterceptRuleVO ruleVO) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<ProductInterceptRuleDTO>> checkProductInterceptRule(ProductInterceptRuleDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
            public ResultVo<String> deleteInterceptRuleByIds( List<Integer> ids){
                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
          public   ResultVo<String> deleteInterceptRuleByCondition( ProductInterceptRuleRequest request){
                return ResultVo.failure("请求失败,服务降级");
            }
            @Override
            public ResultVo<String> deleteInterceptRule(Integer id) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<Boolean> getIsProductRestrict(String modelNo, String customerNo, String endUser) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<DeliveryInfo> findWarehouseSendDate(DeliveryInfo info) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public void importGPProductModel() {

            }

            @Override
            public ResultVo<Boolean> isErrorModelNo(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<Boolean> isEosModelNo(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<Boolean> isModelOfCN(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<String>> checkAndReturnErrorModel(List<String> modelNoList) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<ShikomiRegistDateCallbackDTO>> listShikomiRegistData(List<String> applyNos) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> checkShikomiInspectionByDept() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> calcShikomiInspection(MultipartFile file) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> checkStatus() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> cancelPrepareShikomi(ShikomiPrepareDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> delShikomiCustomerById(Integer id) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> delShikomiByNo(String shikomiNo,String modelNo, String supplierCode) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> importCNShikomiData(MultipartFile file) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<List<SupplierVo>> getShikomiSupplier() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> saveShikomiCache() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> importShikomiNoord(MultipartFile file) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> syncCNShikomiNoordQty() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> calcShikomiQtyWarning() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> updateShikomiWarnQty(ShikomiWarnCallBackDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> shikomiWarningSendMH() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<ProductPhysicsVO> getWeightByModelNo(String modelNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> shikomiInspectionCalcNew() {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> upShikomiBudjet(ShikomiBudgetVO shikomiBudgetVO) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<ShikomiVO> getSupplierByShikomi(String shikomiNo) {
                return ResultVo.failure("请求失败,服务降级");
            }

            @Override
            public ResultVo<String> prepareShikomi(ShikomiPrepareDTO dto) {
                return ResultVo.failure("请求失败,服务降级");
            }
        };
    }
}
