package com.smc.smccloud.web.rpc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.SlowMovingModel.SlowMovingModelVO;
import com.smc.smccloud.model.SlowMovingModel.SlowMovingRequest;
import com.smc.smccloud.model.delivery.DeliveryInfo;
import com.smc.smccloud.model.product.*;
import com.smc.smccloud.model.productPrice.ProductPriceDO;
import com.smc.smccloud.model.shikomi.*;
import com.smc.smccloud.model.supplier.SupplierVo;
import com.smc.smccloud.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@RestController
public class ProductServiceFeignClient implements ProductServiceFeignApi {

    @Resource
    private ShikomiService shikomiService;
    @Resource
    private ProductPriceService productPriceService;
    @Resource
    private ProductDeliveryService productDeliveryService;
    @Resource
    private ProductService productService;
    @Resource
    private ProductErrorService productErrorService;
    @Resource
    private SlowMovingModelService slowMovingModelService;
    @Resource
    private ProductInterceptRuleService productInterceptRuleService;
    @Resource
    private SMSAdapterService smsAdapterService;

    @Override
    public ResultVo<ShikomiVO> canUseShikomi(String modelNo, String shikomiNo, String customerNo) {

        if ((modelNo == null || "".equals(modelNo)) || (shikomiNo == null || "".equals(shikomiNo)) || (customerNo == null || "".equals(customerNo))) {

            return ResultVo.failure("无效输入参数");
        }

        return shikomiService.canUseShikomi(modelNo, shikomiNo, customerNo);
    }

    @Override
    public ResultVo<List<ShikomiVO>> listCustomerShikomi(String modelNo, String customerNo) {

        if ((modelNo == null || "".equals(modelNo))) {

            return ResultVo.failure("无效参数");
        }

        List<ShikomiVO> list = shikomiService.listCustomerShikomi(modelNo, customerNo);

        return ResultVo.success(list);
    }

    @Override
    public ResultVo<String> prepareShikomi(ShikomiPrepareDTO dto) {
        if (PublicUtil.isEmpty(dto.getSupplierCode()) || PublicUtil.isEmpty(dto.getOrderNo()) || dto.getQuantity() == null) {
            return ResultVo.failure("无效请求");
        }
        return shikomiService.prepareShikomi(dto);
    }


    @Override
    public ResultVo<PageInfo<ShikomiVO>> listShikomi(ShikomiDataVO data) {

        PageInfo<ShikomiVO> pageInfo = shikomiService.listShikomi(data);

        return ResultVo.success(pageInfo);
    }

    @Override
    public ResultVo<String> saveShikomidata(ShikomiVO info) {

        ShikomiTotalDO shikomiTotalDO = new ShikomiTotalDO();
        BeanUtils.copyProperties(info, shikomiTotalDO);

        return shikomiService.saveShikomidata(shikomiTotalDO);
    }

    @Override
    public ResultVo<String> saveCustomerdata(ShikomiCustomerVO customerVO) {

        if ((customerVO.getShikomiNo() == null || "".equals(customerVO.getShikomiNo()))
                || (customerVO.getCustomerNo() == null || "".equals(customerVO.getCustomerNo()))) {

            return ResultVo.failure("无效输入参数");
        }

        return shikomiService.saveCustomerdata(customerVO);
    }

    @Override
    public ResultVo<String> importJPReplyFile(MultipartFile file) {

        return shikomiService.importJPReplyFile(file);
    }

    @Override
    public ResultVo<String> importCNReplyFile(MultipartFile file) {
        return shikomiService.importCNReplyFile(file);
    }

    @Override
    public ResultVo<String> autoImportShikomiFile() {
        return shikomiService.downloadFile();
    }

    @Override
    public ResultVo<ProductPriceVO> findPriceByModelNo(String modelNo) {
        QueryWrapper<ProductPriceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ModelNo", modelNo);
        queryWrapper.eq("MinQuantity", 1);
        ProductPriceDO one = productPriceService.getOne(queryWrapper);

        ProductPriceVO o = BeanCopyUtil.copy(one, ProductPriceVO.class);
        if (PublicUtil.isEmpty(o)) {
            return ResultVo.failure("暂无数据");
        }
        return ResultVo.success(o);
    }

    @Override
    public ResultVo<String> addRelationModel(ShikomiVO shikomiVO) {

        if (PublicUtil.isEmpty(shikomiVO.getShikomiNo()) || PublicUtil.isEmpty(shikomiVO.getModelNo()) || PublicUtil.isEmpty(shikomiVO.getSerialModel())) {
            return ResultVo.failure("无效输入参数");
        }

        return shikomiService.addRelationModel(shikomiVO);
    }

    @Override
    public ResultVo<String> sendInspectionReport() {
        return shikomiService.sendInspectionReport();
    }

    @Override
    public ResultVo<String> answerInspection(ShikomiInspectionAnsewrDTO dto) {
        return shikomiService.answerInspection(dto);
    }

    @Override
    public ResultVo<List<ShikomiInspectionVO>> findInspectionInfoById(String shikomiNo) {
        return shikomiService.findInspectionInfoById(shikomiNo);
    }

    @Override
    public ResultVo<ShikomiVO> getShikomiDataByNo(String shikomiNo, String modelNo, String supplierNo) {
        return shikomiService.getShikomiDataByNo(shikomiNo, modelNo, supplierNo);
    }

    @Override
    public ResultVo<PageInfo<ShikomiInspectionVO>> listPageShikomiInspection(ShikomiInspectionRequest request) {
        return shikomiService.listPageShikomiInspection(request);
    }

    @Override
    public void exportShikomiInspectionData(ShikomiInspectionRequest request) {
        shikomiService.exportShikomiInspectionData(request);
    }

    @Override
    public ResultVo<List<ShikomiCustomerVO>> findCustomerDataByNo(String shikomiNo) {

        return shikomiService.findCustomerDataByNo(shikomiNo);
    }

    @Override
    public ResultVo<String> getModelEPrice(String modelNo) {
        return productPriceService.getModelEPrice(modelNo);
    }

    @Override
    public ResultVo<String> getModelLotPrice(String modelNo, Integer quantity) {
        return productPriceService.getModelLotPrice(modelNo, quantity);
    }
    @Override
    public ResultVo<List<ProductPriceVO>> listProductPrice(List<String> modelNos){
        return productPriceService.listProductPrice(modelNos);
    }

    @Override
    public ResultVo<String> getSupplierNoByModelNo(String modelNo) {
        return productDeliveryService.getSupplierNoByModelNo(modelNo);
    }

    @Override
    public ResultVo<OrderModelInfoVO> getModelInfoForOrder(String modelNo) {
        return productPriceService.getModelInfoForOrder(modelNo);
    }

    @Override
    public ResultVo<ProductInfoVO> getProductInfoByModelNo(String modelNo) {
        return productService.getProductInfoByModelNo(modelNo);
    }

    @Override
    public ResultVo<Integer> getMinPackUnitByModelNo(String modelNo) {
        return productService.getMinPackUnitByModelNo(modelNo);
    }

    @Override
    public ResultVo<String> getProductEnglishName(String modelNo) {
        return productService.getProductEnglishName(modelNo);
    }

    @Override
    public ResultVo<List<ProductCashierVO>> listProductEos(List<String> modelNos) {
        return productService.listProductEos(modelNos);
    }

    @Override
    public ResultVo<PageInfo<SlowMovingModelVO>> listSlowMovingModelData(SlowMovingRequest request) {
        return slowMovingModelService.listSlowMovingModelData(request);
    }

    @Override
    public ResultVo<String> importSlowModelData(MultipartFile file) {
        return slowMovingModelService.importSlowModelData(file);
    }

    @Override
    public ResultVo<String> updateSlowModelData(SlowMovingModelVO modelVO) {
        return slowMovingModelService.updateSlowModelData(modelVO);
    }

    @Override
    public ResultVo<String> delSlowModelData(Integer id) {
        return slowMovingModelService.delSlowModelData(id);
    }

    @Override
    public ResultVo<String> calcUpdateSlowModelOnHnad() {
        return slowMovingModelService.calcUpdateSlowModelOnHnad();
    }

    @Override
    public ResultVo<List<ProductForShikomiVO>> listProductInfoForShikomi(List<String> modelNos) {
        return productService.listProductInfoForShikomi(modelNos);
    }

    @Override
    public ResultVo<String> importCNMProductModel() {
        return productService.importCNMProductModel();
    }

    @Override
    public ResultVo<ProductPriceDTO> getPriceMast(String modelNo) {
        return productService.getPriceMast(modelNo);
    }

    @Override
    public ResultVo<ProductRemark> getProductRemarkByModelNo(String modelNo) {
        ProductRemark productRemark = productService.getProductRemarkByModelNo(modelNo);
        return ResultVo.success(productRemark);
    }

    @Override
    public ResultVo<List<ProductInterceptRuleVO>> listProductInterceptRule(ProductInterceptRuleRequest request) {
        return productInterceptRuleService.listProductInterceptRule(request);
    }
    @Override
    public ResultVo<PageInfo <ProductInterceptRuleVO>> listProductInterceptRuleByPage(ProductInterceptRuleRequest request) {
        return productInterceptRuleService.listProductInterceptRuleByPage(request);
    }
    @Override
    public void exportProductInterceptRule( ProductInterceptRuleRequest request ){
          productInterceptRuleService.exportProductInterceptRule(request);
    }
    @Override
    public ResultVo<String> addOrUpdateInterceptRule(ProductInterceptRuleVO ruleVO) {
        return productInterceptRuleService.addOrUpdateInterceptRule(ruleVO);
    }

    @Override
    public ResultVo<List<ProductInterceptRuleDTO>> checkProductInterceptRule(ProductInterceptRuleDTO dto) {
        return ResultVo.success(productInterceptRuleService.checkProductInterceptRule(dto));
    }

    @Override
    public ResultVo<String> deleteInterceptRule(Integer id) {
        return productInterceptRuleService.deleteInterceptRule(id);
    }
    @Override
    public ResultVo<String> deleteInterceptRuleByIds(  List<Integer> ids){
        return productInterceptRuleService.deleteInterceptRuleByIds(ids);
    }
    @Override
   public ResultVo<String> deleteInterceptRuleByCondition(  ProductInterceptRuleRequest request){
        return  productInterceptRuleService.deleteInterceptRuleByCondition(request);
    }
    @Override
    public ResultVo<Boolean> getIsProductRestrict(String modelNo, String customerNo, String endUser) {
        return productService.getIsProductRestrict(modelNo, customerNo, endUser);
    }

    @Override
    public ResultVo<DeliveryInfo> findWarehouseSendDate(DeliveryInfo info) {
        return smsAdapterService.getWarehouseSendDate(info);
    }

    @Override
    public void importGPProductModel() {
        productService.importGPProductModel();
    }

    @Override
    public ResultVo<Boolean> isErrorModelNo(String modelNo) {
        return productErrorService.isErrorModelNo(modelNo);
    }

    @Override
    public ResultVo<Boolean> isEosModelNo(String modelNo) {
        return productService.isEosModelNo(modelNo);
    }

    @Override
    public ResultVo<Boolean> isModelOfCN(String modelNo) {
        return productService.isModelOfCN(modelNo);
    }

    @Override
    public ResultVo<List<String>> checkAndReturnErrorModel(List<String> modelNoList) {
        return productService.checkAndReturnErrorModel(modelNoList);
    }

    @Override
    public ResultVo<List<ShikomiRegistDateCallbackDTO>> listShikomiRegistData(List<String> applyNos) {
        return shikomiService.listShikomiRegistData(applyNos);
    }

    @Override
    public ResultVo<String> checkShikomiInspectionByDept() {
        return shikomiService.checkShikomiInspectionByDept();
    }

    @Override
    public ResultVo<String> calcShikomiInspection(MultipartFile file) {
        return shikomiService.calcShikomiInspection(file);
    }

    @Override
    public ResultVo<String> cancelPrepareShikomi(ShikomiPrepareDTO dto) {
        return shikomiService.cancelPrepareShikomi(dto);
    }

    @Override
    public ResultVo<String> checkStatus() {
        return shikomiService.checkStatus();
    }

    @Override
    public ResultVo<String> delShikomiCustomerById(Integer id) {
        return shikomiService.delShikomiCustomerById(id);
    }

    @Override
    public ResultVo<String> delShikomiByNo(String shikomiNo,String modelNo, String supplierCode) {
        return shikomiService.delShikomiByNo(shikomiNo, modelNo, supplierCode);
    }

    @Override
    public ResultVo<String> importCNShikomiData(MultipartFile file) {
        return shikomiService.importCNShikomiData(file);
    }

    @Override
    public ResultVo<List<SupplierVo>> getShikomiSupplier() {
        return shikomiService.getShikomiSupplier();
    }

    @Override
    public ResultVo<String> saveShikomiCache() {
        return shikomiService.saveShikomiCache();
    }

    @Override
    public ResultVo<String> importShikomiNoord(MultipartFile file) {
        return shikomiService.importShikomiNoord(file);
    }

    @Override
    public ResultVo<String> syncCNShikomiNoordQty() {
        return shikomiService.syncCNShikomiNoordQty();
    }

    @Override
    public ResultVo<String> calcShikomiQtyWarning() {
        return shikomiService.calcShikomiQtyWarning();
    }

    @Override
    public ResultVo<String> updateShikomiWarnQty(ShikomiWarnCallBackDTO dto) {
        return shikomiService.updateShikomiWarnQty(dto);
    }

    @Override
    public ResultVo<String> shikomiWarningSendMH() {
        return shikomiService.shikomiWarningSendMH();
    }

    @Override
    public ResultVo<ProductPhysicsVO> getWeightByModelNo(String modelNo) {
        return productService.getWeightByModelNo(modelNo);
    }

    @Override
    public ResultVo<String> shikomiInspectionCalcNew() {
        return shikomiService.shikomiInspectionCalcNew();
    }

    @Override
    public ResultVo<String> upShikomiBudjet(ShikomiBudgetVO shikomiBudgetVO) {
        return shikomiService.upShikomiBudjet(shikomiBudgetVO);
    }

    @Override
    public ResultVo<ShikomiVO> getSupplierByShikomi(String shikomiNo) {
        return shikomiService.getSupplierByShikomi(shikomiNo);
    }

    @Override
    public ResultVo<String> inspectApply(ShikomiInspectionDTO info) {
        return shikomiService.inspectApply(info);
    }

    @Override
    public ResultVo<String> inspectProcess(ShikomiInspectionDTO info) {
        return shikomiService.inspectProcess(info);
    }

    @Override
    public void exportShikomi(ShikomiDataVO data) {
        shikomiService.exportShikomi(data);
    }

    @Override
    public ResultVo<List<ShikomiVO>> getCanUseShikomi(String modelNo) {
        return shikomiService.getCanUseShikomi(modelNo);
    }

    @Override
    public ResultVo<Boolean> isLotPrice(String modelNo) {
        return productPriceService.isLotPrice(modelNo);
    }


    @Override
    public void calShikomiUpdateInfo() {
        shikomiService.calShikomiUpdateInfo();
    }

    @Override
    public Boolean checkShikomiStcokInsufficient() {
        return shikomiService.checkShikomiStcokInsufficient();
    }
}
