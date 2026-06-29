package com.smc.smccloud;

import cn.hutool.json.JSONUtil;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.InventoryMapper;
import com.smc.smccloud.model.inventory.InventoryRequestDTO;
import com.smc.smccloud.model.inventory.InventorySummaryVO;
import com.smc.smccloud.model.inventory.ModelWarehouseStockRequest;
import com.smc.smccloud.service.InventoryService;
import com.smc.smccloud.service.InventoryServiceFeignApi;
import com.smc.smccloud.service.InventorySupplierService;
import com.smc.smccloud.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Author: B90034
 * Date: 2021-12-02 15:54
 * Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryServiceTests {


    @Resource
    private InventoryMapper inventoryMapper;
//    @Resource
//    private RedisManager redisManager;
    @Resource
    private InventoryService inventoryService;

    @Resource
    private InventoryServiceFeignApi inventoryServiceFeignApi;
//    @Resource
//    private OpsPropertyFeignApi opsPropertyFeignApi;

    @Resource
    private InventorySupplierService inventorySupplierService;

    @Test
    public void testInv(){
        try {
            inventoryService.updateZeroInventoryVTest();
        } catch (Exception e) {
            log.error("测试",e);
            System.out.println(123);
        }
    }

    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        SMCApp.setForSysUser();
    }

    @Test
    public void listSpecInventoryTest() {
        ResultVo<String> resultVo = inventorySupplierService.impGPInventory();
        System.out.println("resultVo = " + JSONUtil.toJsonPrettyStr(resultVo));
    }

    @Test
    public void testRe() {
        File file = new File("E:\\Attachment\\JPSTOCK.DAT");
        MultipartFile multipartFile = null;
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        inventoryServiceFeignApi.parseJPSTockFile(multipartFile);
    }



    @Resource
    private ProductService productService;

    @Test
    public void testt() {
        log.info("result = {}", productService.getMinPackUnitByModelNo("MB-B03"));
    }


    @Test
    public void listModelWarehouseStockTest() {
        ModelWarehouseStockRequest dto = new ModelWarehouseStockRequest();
        // dto.setModelNos(new String[]{"NCDJPB15-050D", "10-AME250C-03B", "CDUK6-10D"});
        dto.setWarehouseCode("KGZ");
        log.info("result = {}", inventoryService.listModelWarehouseStock(dto));
    }

    @Test
    public void listCustomerSpecStockTest() {
        ModelWarehouseStockRequest dto = new ModelWarehouseStockRequest();
        //  dto.setModelNos(new String[]{"ZP3A-T3-A5", "ISE20-N-M-C6L-LB", "SY50M-2-1DA-C6"});
        //dto.setWarehouseCode("KGZ");
        log.info("result = {}", inventoryService.listCustomerSpecStock(dto));
    }

    @Test
    public void listWarehouseStockTest() {
        ResultVo<String> resultVo = inventorySupplierService.paseJPInventorySupplier();
        System.out.println("resultVo = " + resultVo.toString());
    }

    @Test
    public void getLogisWarehouseCodeTest() {
        //ResultVo<List<WarehouseStockVO>>  resulVo = inventoryService.getLogisWarehouseCanUseStock(new String[]{"ZP3A-T3-A5", "ISE20-N-M-C6L-LB", "SY50M-2-1DA-C6"});
        //System.out.println(resulVo);
    }

    @Test
    public void getModelWarehouseStockTest() {
        ModelWarehouseStockRequest dto = new ModelWarehouseStockRequest();
        dto.setModelNo("IZF21");
        dto.setInventoryTypeCode("GK-TY");
        dto.setWarehouseCode("KBJ");
        dto.setCustomerNo("A3K28");
        dto.setGroupCustomerNo(null);
        dto.setPpl(null);
        dto.setProjectCode(null);
        System.out.println("result = " + inventoryService.getModelWarehouseStock(dto));
    }

    @Test
    public void gettttt(){
        ResultVo<Integer> izf21 = inventoryService.getInvByModel("P36202028");
        System.out.println(123);
    }

    @Test
    public void listCustomerBinModelInventoryTest() {
        InventoryRequestDTO dto = new InventoryRequestDTO();
        dto.setModelNos(Arrays.asList("SY3A00-5UD1-NA", "SY30M-1-1A-C8-NA", "SY30M-3-1A-C8-NA", "EX260-SPN1", "EX260-SPR5", "EX260-SPR1", "SY30M-2-1DA-C6-NA"));
        dto.setPropertyId(552L);
        dto.setWarehouseCode("KSH");
      //  log.info("listCustomerBinModelInventoryTest result = {}", inventoryService.listCustomerBinModelInventory(dto));
        log.info("listCustomerBinModelInventoryTest result = {}", inventoryServiceFeignApi.listCustomerBinModelInventory(dto));
    }

    @Test
    public void listCanUseInventoryTest() {
        ModelWarehouseStockRequest dto = new ModelWarehouseStockRequest();
        dto.setCustomerNo("B65CM");
        dto.setWarehouseCodeList(Arrays.asList("KSH","SCZ","KBJ","KGZ"));
        dto.setModelNos(Arrays.asList("ZP2A-0305-CJXN0885"));
        log.info("listCanUseInventoryTest = {}", inventoryService.listCanUseInventory(dto));
    }

    @Test
    public void checkAndCreateInventoryPropertyTest() {
        OpsInventoryProperty vo = new OpsInventoryProperty();
        vo.setInventoryTypeCode("GK-TY");
        vo.setCustomerNo("98343");
        //log.info("checkAndCreateInventoryPropertyTest = {}", opsPropertyFeignApi.findProperty(vo));
    }

    @Test
    public void getWarehouseCanUseStockTest() {
        List<String> modelNoList = Arrays.asList("YA-03", "ZCDUKQ10-10D", "SY5120-5LZ-C6", "MSQB30R", "KQ2L12-03AS"); //
        List<String> warehouseConfig = Arrays.asList("KBJ", "SCZ", "KSH");
        log.info("getWarehouseCanUseStockTest = {}", inventoryMapper.getWarehouseCanUseStock(warehouseConfig, modelNoList));
    }

    @Test
    public void listInventorySummaryByPropertyIdTest() {
        InventoryRequestDTO dto = new InventoryRequestDTO();
        dto.setModelNos(Arrays.asList("SY3A00-5UD1-NA", "SY30M-1-1A-C8-NA", "SY30M-3-1A-C8-NA", "EX260-SPN1", "EX260-SPR5", "EX260-SPR1", "SY30M-2-1DA-C6-NA"));
        dto.setPropertyId(552L);
        dto.setWarehouseCode("KSH");
       // System.out.println ("result = "+inventoryService.listInventorySummaryByPropertyId(dto));
        ResultVo<List<InventorySummaryVO>> resultVo =inventoryServiceFeignApi.listInventorySummaryByPropertyId(dto);
        System.out.println ("result = "+resultVo.getData());

    }

}
