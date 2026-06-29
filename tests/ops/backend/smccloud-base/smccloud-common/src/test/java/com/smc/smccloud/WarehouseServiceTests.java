package com.smc.smccloud;

import cn.hutool.json.JSONUtil;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import com.smc.smccloud.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: B90034
 * Date: 2023-01-06 13:36
 * Description:
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class WarehouseServiceTests {

    @Resource
    private WarehouseService warehouseService;

    @Test
    public void getSubWarehouseCodeTest() {
        ResultVo<List<WarehouseVO>> resultVo = warehouseService.getSubWarehouse("KSH");
        log.info(JSONUtil.toJsonStr(resultVo));
    }

    @Test
    public void isMasterWarehouseTest() {
        log.info("isMasterWarehouseTest = {}", warehouseService.isMasterWarehouse("KBJ"));
        log.info("isMasterWarehouseTest = {}", warehouseService.isMasterWarehouse("SCZ"));
        log.info("isMasterWarehouseTest = {}", warehouseService.isMasterWarehouse("SCD"));
    }

    @Test
    public void getWarehouseCodeInfoTest() {
        log.info("getWarehouseCodeInfoTest = {}", warehouseService.getWarehouseInfoByCode("KBJ"));
    }
    @Test
    public void getWarehouseSalesBranchConfigForPriorityTest() {
        log.info("getWarehouseCodeInfoTest = {}", warehouseService.getWarehouseSalesBranchConfigForPriority());
    }

//    @Test
//    public void listWarehouseTest() {
//        WarehouseQueryDTO dto=new WarehouseQueryDTO();
//        dto.setIsPrestock(true);
//        dto.setIsTransfer(true);
//        log.info("listWarehouseTest = {}", warehouseService.listWarehouse(dto));
//    }
}
