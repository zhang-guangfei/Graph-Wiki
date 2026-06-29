package com.sales.ops;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.inventory.WmDoConfirmDto;
import com.sales.ops.dto.inventory.WmDoItemConfirmDto;
import com.sales.ops.service.wmOrder.OpsDoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author C12961
 * @date 2023/5/19 17:05
 */
@Slf4j
public class DoConfirmTest extends BaseTest {

    @Autowired
    private OpsDoService opsDoService;

    @Test
    public void test1() {
        String doId = "DO10701833001001000";
        int qty = 1;
        WmDoConfirmDto dto = new WmDoConfirmDto();
        dto.setDeliveryOrderCode(doId);
        WmDoItemConfirmDto item = new WmDoItemConfirmDto();
        item.setQty(Double.valueOf(qty));
        dto.setShipTime(new Date());
        dto.setItems(item);
        try {
            opsDoService.wmDoConfirm(dto);
        } catch (OpsException e) {
            log.error("发货异常：{}", e);
        }
    }
}
