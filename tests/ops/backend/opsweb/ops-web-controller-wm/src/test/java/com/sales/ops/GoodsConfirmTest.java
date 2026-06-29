package com.sales.ops;

import com.sales.ops.service.inventory.WmDispatchService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class GoodsConfirmTest extends BaseTest {


    @Autowired
    private WmDispatchService wmDispatchService;

    @Test
    @Transactional
    public void test1() {
        /*RoSignConfirmDto paramDto = new RoSignConfirmDto();
        paramDto.setInvoice("LCN365");
        paramDto.setInvoiceId(23050336L);
        paramDto.setWarehouse("KBJ");
        paramDto.setUserName("testZhang");
        paramDto.setUpdateTime(new Date());
        try {
            List<RoConfirmItem> roConfirmItems = wmDispatchService.goodsConfirm(paramDto);
            log.info("{}", JSONUtil.toJsonPrettyStr(roConfirmItems));
        } catch (OpsException e) {
            throw new RuntimeException(e);
        }*/
    }

}