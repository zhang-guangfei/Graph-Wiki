package com.sales.ops;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsDoItem;
import com.sales.ops.dto.flux.HandConfirm;
import com.sales.ops.dto.flux.HandItem;
import com.sales.ops.dto.inventory.WmDoConfirmDto;
import com.sales.ops.dto.inventory.WmDoItemConfirmDto;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.OpsDoService;
import com.sales.ops.serviceimpl.dispatch.rodispatch.service.HandConfirmHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author C12961
 * @date 2023/5/19 11:23
 */
@Slf4j
public class HandConfirmTest extends BaseTest {

    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private HandConfirmHandler handConfirmHandler;

    @Autowired
    private BaseDoService baseDoService;

    @Test
    public void test1() {
        String doId = "DO10701833001001000";
        int qty = 1;
        if (true) {
            try {
                doConfirm(doId, qty);
            } catch (OpsException e) {
                log.error("doConfirm异常：{}", e);
            }
        }


    }
    @Test
    public void test2() {

        try {
            doConfirm("DBC10701329001001001", 8);
            doConfirm("DBC10701346005002001", 10);
            doConfirm("DBC10701447004002001", 10);
            doConfirm("DBC10701598001002001", 6);
        } catch (OpsException e) {
            log.error("doConfirm异常：{}", e);
        }
    }
    @Test
    public void test3() {
        Map<String, Integer> map = new HashMap<>();
        map.put("DBC10701329001001001", 8);
        map.put("DBC10701346005002001", 10);
        map.put("DBC10701447004002001", 10);
        map.put("DBC10701598001002001", 6);
        String randomStr = RandomUtil.randomString(6);
        HandConfirm handConfirm = new HandConfirm();
        handConfirm.setInvoice(randomStr);//发票号必须是新的
        handConfirm.setWarehouseCode("KSH"); //from
        handConfirm.setFromWarehouseCode("KSH"); //from
        handConfirm.setToWarehouseCode("KBJ"); //target
        handConfirm.setLogisticsCode("TEST_SF");
        handConfirm.setExpressCode("TestExpressCode");
        handConfirm.setHangdate(DateUtil.date());
        handConfirm.setHandlist(new ArrayList<>());
        final Integer[] i = {0};
        map.forEach((doId, qty) -> {
            i[0] = i[0] + 1;
            OpsDo opsDo = null;
            OpsDoItem doItem = null;
            try {
                opsDo = baseDoService.getDoByDoId(doId);
                doItem = baseDoService.getDoItemByDoId(doId);
            } catch (OpsException e) {
                throw new RuntimeException(e);
            }
            HandItem handItem = new HandItem();
            handItem.setDoid(doId);
            handItem.setModelno(doItem.getModelno());
            handItem.setQty(qty);
            handItem.setCaseno("TestCaseno"); //wms号码
            handItem.setBarcode("Test230526-" + i[0] + "-" + randomStr);//箱码 创建barcode,也是新的
            handConfirm.getHandlist().add(handItem);
        });
        try {
            handConfirmHandler.handConfirm(handConfirm);
        } catch (OpsException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        log.info("handConfirm成功");
    }

    @Transactional
    public void doConfirm(String doId, int qty) throws OpsException {
        WmDoItemConfirmDto item = new WmDoItemConfirmDto();
        item.setQty(Double.valueOf(qty));
        item.setBarCode("Test23053001");
        WmDoConfirmDto dto = new WmDoConfirmDto();
        dto.setExpressCodeChild(RandomUtil.randomString(10));
        dto.setDeliveryOrderCode(doId);
        dto.setShipTime(DateUtil.date());
        dto.setItems(item);
        opsDoService.wmDoConfirm(dto);
        log.info("doConfirm成功");
    }

    @Transactional
    public void handConfirm(String doId, int qty) throws OpsException {
        String randomStr = RandomUtil.randomString(6);
        OpsDo opsDo = baseDoService.getDoByDoId(doId);
        OpsDoItem doItem = baseDoService.getDoItemByDoId(doId);
        HandItem handItem = new HandItem();
        handItem.setDoid(doId);
        handItem.setModelno(doItem.getModelno());
        handItem.setQty(qty);
        handItem.setCaseno("TestCaseno1"); //wms号码
        handItem.setBarcode("Test23052601-" + randomStr);//箱码 创建barcode,也是新的
//        HandItem handItem2 = new HandItem();
//        handItem2.setDoid(doId);
//        handItem2.setModelno(doItem.getModelno());
//        handItem2.setQty(qty);
//        handItem2.setCaseno("TestCaseno2");//wms号码
//        handItem2.setBarcode("Test23052602-"+randomStr);//箱码 创建barcode,也是新的


        HandConfirm handConfirm = new HandConfirm();
        handConfirm.setInvoice(randomStr);//发票号必须是新的
        handConfirm.setWarehouseCode(opsDo.getWarehouseCode()); //from
        handConfirm.setFromWarehouseCode(opsDo.getWarehouseCode()); //from
        handConfirm.setToWarehouseCode(opsDo.getGatherWarehouseCode()); //target
        handConfirm.setLogisticsCode("TEST_SF");
        handConfirm.setExpressCode("TestExpressCode");
        handConfirm.setHangdate(DateUtil.date());
        handConfirm.setHandlist(Arrays.asList(handItem));
        handConfirmHandler.handConfirm(handConfirm);
        log.info("handConfirm成功");
    }

}
