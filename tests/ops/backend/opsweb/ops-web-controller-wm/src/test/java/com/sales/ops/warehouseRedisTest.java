package com.sales.ops;

import com.sales.ops.service.wm.WmCommonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class warehouseRedisTest extends BaseTest {
    @Autowired
    private WmCommonService wmCommonService;

    @Test
    public void test() {
        wmCommonService.refreshWarehouseCache("KBJ");
    }
}
