package com.sales.ops;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsRoBarcode;
import com.sales.ops.dto.inventory.WmRoConfirmDto;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.service.log.OpsRoBarcodeService;
import com.sales.ops.serviceimpl.dispatch.rodispatch.service.RoConfirmHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author C12961
 * @date 2023/5/23 9:27
 */
@Slf4j
public class RoConfirmTest extends BaseTest {


    @Autowired
    private RoConfirmHandler roConfirmHandler;
    @Autowired
    private OpsRoBarcodeService opsRoBarcodeService;


    @Test
    //@Transactional
    public void test1() {
        String roId = "ROBJ2305230110702041001002";
        String barcode = "1100000133781";
        OpsRoBarcode roBarCode = opsRoBarcodeService.getRoBarcodeByBarcode(roId, barcode);
        WmRoConfirmDto dto = createRoConfirmDto(roBarCode);
        try {
            roConfirmHandler.roConfirm(dto);
        } catch (OpsException e) {
            log.info("失败{}", e);
        }
        log.info("成功");
    }

    public WmRoConfirmDto createRoConfirmDto(OpsRoBarcode roBarCode) {
        WmRoConfirmDto dto = new WmRoConfirmDto();
        dto.setReceiveCode(roBarCode.getBarcode());//barcode
        dto.setInvoiceNo(roBarCode.getInvoiceno());//invoice
        dto.setReceiveOrderCode(roBarCode.getRoId());//roId
        dto.setWmsOrderCode("C12961Test" + roBarCode.getBarcode());//随便编 WMS单号(唯一码，用于判断富勒请求)
        dto.setWarehouseCode(roBarCode.getWarehouseCode());
        dto.setQty(roBarCode.getQty().toString());
        dto.setModelNo(roBarCode.getModelno());
        dto.setScanType("1");
        dto.setInventoryType("ZP");
        dto.setUsername("C12961Test");
        dto.setReceiveTime(new Date());
        dto.setUserDto(new UserDto("C12961Test"));
        return dto;
    }

}
