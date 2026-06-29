package com.smc.smccloud.web;

import com.alibaba.excel.EasyExcel;
import com.sales.ops.dto.assembly.InventoryRelationOrderExcel;
import com.sales.ops.dto.query.InventoryQO;
import com.smc.smccloud.service.ExportInventoryRelationOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/report")
public class InventoryController {

    @Autowired
    ExportInventoryRelationOrderService exportInventoryRelationOrderService;

    @GetMapping("/inventory/relationOrder/download")
    public void exportInventoryRelationOrder(@RequestParam(required = false) String warehouseType, @RequestParam(required = false) String warehouseCode,
                                             @RequestParam(required = false) String modelno, @RequestParam(required = false) String customerNO,
                                             @RequestParam(required = false) String groupCustomerNo, @RequestParam(required = false) String ppl,
                                             @RequestParam(required = false) String projectCode, @RequestParam(required = false) String inventoryTypeCode,
                                             @RequestParam(required = false) String salesInfoNo, HttpServletResponse response) throws IOException {
        InventoryQO inventoryQO = new InventoryQO();
        inventoryQO.setWarehouseType(warehouseType);
        inventoryQO.setWarehouseCode(warehouseCode);
        inventoryQO.setModelno(modelno);
        inventoryQO.setCustomerNo(customerNO);
        inventoryQO.setGroupCustomerNo(groupCustomerNo);
        inventoryQO.setPpl(ppl);
        inventoryQO.setProjectCode(projectCode);
        inventoryQO.setInventoryTypeCode(inventoryTypeCode);
        inventoryQO.setSalesInfoNo(salesInfoNo);
        List<InventoryRelationOrderExcel> excelList = exportInventoryRelationOrderService.AsyncGetInventoryRelationOrder(inventoryQO);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("库存关联订单", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), InventoryRelationOrderExcel.class)
                .sheet("库存关联订单")
                .doWrite(excelList);
    }
}
