package com.sales.ops.web.controller.mainten;

import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsBsQuerypriceNewmodel;
import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.db.entity.ProductDelivery;
import com.sales.ops.dto.mainten.ModelData;
import com.sales.ops.service.mainten.BasicsMaintenanceService;
import com.sales.ops.webutil.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/basics")
public class BasicsMaintenanceController extends BaseController {

    @Autowired
    private BasicsMaintenanceService maintenanceService;

    @GetMapping("/findProductModel")
    public PageInfo<ModelData> findProductModelByNo(@RequestParam("modelNo") String modelNo,@RequestParam("pageNumber") Integer pageNumber
            , @RequestParam("fuzzy") Boolean fuzzy, @RequestParam("pageSize") Integer pageSize
            , @RequestParam(required = false, value = "startTime") String startTime
            , @RequestParam(required = false, value = "endTime") String endTime) {
        return maintenanceService.findProductModelByNo(modelNo,fuzzy, pageNumber, pageSize, startTime, endTime);
    }

    @GetMapping("/findProductModelList")
    public List<ModelData> findProductModelListByNo(@RequestParam("modelNo") String modelNo) {
        return maintenanceService.findProductModelListByNo(modelNo);
    }

    @GetMapping("/findSplitInfoByWholeNo")
    public List<ModelData> findSplitInfoByWholeNo(@RequestParam("modelNo") String modelNo) {
        return maintenanceService.findSplitInfoByWholeNo(modelNo);
    }

    @GetMapping("/findWholeModel")
    public List<ProductBom> findWholeModelInfoByModelNo(@RequestParam("modelNo") String modelNo) {
        return maintenanceService.findWholeModelInfoByModelNo(modelNo);
    }

    @PostMapping("/updateWholeModel")
    public Integer updateWholeModelInfoByModelNo(@RequestBody List<ProductBom> bom) {
        return maintenanceService.updateWholeModelInfoByModelNo(bom);
    }

    @PostMapping("/insert")
    public Integer insertProductModel(@RequestBody List<ModelData> data) {
        return maintenanceService.insertProductModel(data);
    }

    @GetMapping("/findProductDelivery")
    public PageInfo<ProductDelivery> findProductDeliveryByNo(@RequestParam("modelNo") String modelNo
            , @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize
            , @RequestParam(required = false, value = "startTime") String startTime
            , @RequestParam(required = false, value = "endTime") String endTime) {
        return maintenanceService.findProductDeliveryByNo(modelNo, pageNumber, pageSize, startTime, endTime);
    }

    @GetMapping("/findCountryBySupplyId")
    public Map<String, String> findCountryBySupplierId(String supplyId) {
        return maintenanceService.findCountryBySupplierId(supplyId);
    }

    @GetMapping("/findAllCountry")
    public List<Map<String, String>> findAllCountry() {
        return maintenanceService.findAllCountry();
    }

    @GetMapping("/findAllSupplier")
    public List<Map<String, String>> findAllSupplier() {
        return maintenanceService.findAllSupplier();
    }

    @PostMapping("/insertDelivery")
    public int insertProductDelivery(@RequestBody List<ProductDelivery> lists) {
        return maintenanceService.insertProductDelivery(lists);
    }

    @PostMapping("/updateDelivery")
    public void updateProductDelivery(@RequestBody ProductDelivery delivery) {
        maintenanceService.updateProductDelivery(delivery);
    }

    @GetMapping("/delete")
    public void deleteProductDelivery(@RequestParam("modelNo") String modelNo, @RequestParam("supplyId") String supplyId) {
        maintenanceService.deleteProductDelivery(modelNo, supplyId);
    }

    @GetMapping("/findDeliveryByNo")
    public ProductDelivery selectDeliveryByModelNo(@RequestParam("modelNo") String modelNo
            , @RequestParam("supplierId") String supplierId) {
        return maintenanceService.selectDeliveryByModelNo(modelNo, supplierId);
    }

    @GetMapping("/findDeliveryList")
    public List<ProductDelivery> selectDeliveryListByNo(@RequestParam("modelNo") String modelNo) {
        return maintenanceService.selectDeliveryListByNo(modelNo);
    }

    @GetMapping("/findBeingSuppliers")
    public List<ProductDelivery> findBeingSuppliersByModelNo(@RequestParam("modelNo") String modelNo) {
        return maintenanceService.findBeingSuppliersByModelNo(modelNo);
    }

    @GetMapping("/findQueryPriceByModelNo")
    public List<OpsBsQuerypriceNewmodel> findQueryPriceNewModelByModelNo(
            @RequestParam(required = false, value = "modelNo") String modelNo) {
        return maintenanceService.findQueryPriceNewModelByModelNo(modelNo);
    }

    @PostMapping("/insertQueryPrice")
    public int insertQueryPrice(@RequestBody List<ProductDelivery> lists) {
        return maintenanceService.insertQueryPrice(lists);
    }
}
