package com.sales.ops;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.OpsDoService;
import com.sales.ops.serviceimpl.dispatch.rodispatch.service.RoConfirmHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class TKCKTest extends BaseTest {

    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private BaseDoService baseDoService;
    @Autowired
    RoConfirmHandler roConfirmHandler;


  /*  @Test
    public void splitTKCK() throws OpsException {
        OpsDo tkck = baseDoService.findDoByDoIdNoExce("DBCT202312070001000000");
        String doId = opsDoService.createAssDoForRo(tkck, 2, UserDto.WMS, DoTypeEnum.TKCK);
        log.info(doId);
    }

    @Test
    public void updateWarehouse() throws OpsException {
        OpsDo tkck = baseDoService.findDoByDoIdNoExce("DBCT202312070001000000");
        tkck.setWarehouseCode("KBJ");
//        opsDoService.handROChangeDoWarehouseCodeByCGDo(tkck, null, null);
    }

    @Test
    public void updateWarehouse2() throws OpsException {
        OpsDo tkck = baseDoService.findDoByDoIdNoExce("DBCT202312070001001000FN");
        tkck.setWarehouseCode("KSH");
//        opsDoService.handROChangeDoWarehouseCodeByCGDo(tkck, null, null);
    }

    @Test
    public void handTransInventory() throws OpsException {
        OpsDo tkck = baseDoService.findDoByDoIdNoExce("DBCT202312070002000000");
        roConfirmHandler.handleInventoryForTransOrder(tkck);
    }*/





}
