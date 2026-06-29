package com.smc.smccloud;

import com.alibaba.fastjson.JSON;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.WarehouseMapper;
import com.smc.smccloud.model.adapter.*;
import com.smc.smccloud.service.SMSAdapterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: B90034
 * Date: 2022-03-16 16:10
 * Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SMSAdapterServiceTests {

    @Resource
    private SMSAdapterService SMSAdapterService;

    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    @Test
    public void findSubTreasuryTest() {
        log.info("findSubTreasury result = {}", SMSAdapterService.findSubTreasury("243712"));
    }

    @Test
    public void findGoodsLocationTest() {
        log.info("findGoodsLocationTest result = {}", SMSAdapterService.findGoodsLocation("244207"));
    }

    @Test
    public void getCanUseByDeptCustomerTest() {
        InventoryCondition condition = new InventoryCondition();
        condition.setDeptNo("261331");
        condition.setCustomerNo("C2YT7");
        List<ModelNoQuantity> list = new ArrayList<>();
        ModelNoQuantity modelNoQuantity = new ModelNoQuantity();
        modelNoQuantity.setModelno("M-5AU-4-X83");
        modelNoQuantity.setQuantity(1);
        list.add(modelNoQuantity);
        /*ModelNoQuantity modelNoQuantity2 = new ModelNoQuantity();
        modelNoQuantity2.setModelno("10-AME250C-03B");
        modelNoQuantity2.setQuantity(1);
        list.add(modelNoQuantity2);*/
        condition.setList(list);
        log.info("getCanUseByDeptCustomer result = {}", JSON.toJSONString(SMSAdapterService.getCanUseByDeptCustomer(condition)));
        // List<String> strs = objs.stream() .map(object -> Objects.toString(object, null)) .collect(Collectors.toList());
        // List<String> strList = objs.stream() .map(Object::toString ) .collect( Collectors.toList() );
    }

    @Test
    public void getQuantityCanUseBatchTest() {
        List<String> modelNoList = Arrays.asList("ECDQ2B80-30DM");
        log.info("getQuantityCanUseBatchTest result = {}",
                SMSAdapterService.getQuantityCanUseBatch(modelNoList, "244220", "B5234"));
    }

    @Test
    public void quantityCanUseAllBatchTest() {
        List<String> modelNoList = Arrays.asList("SY5200-5U1");
        log.info("quantityCanUseAllBatchTest result = {}",
                SMSAdapterService.getQuantityCanUseBatch(modelNoList, "", null));
    }

    @Test
    public void getMaterialModelCanUseQtyBatchTest() {
        String deptNo = "223121";
        List<String> modelNoList = Arrays.asList("VQ21A1-5YZB-C6-F", "ZP3-M0806S-CJXU0723");
        List<MaterialModelInfo> infoList = new ArrayList<>();
        for (String modelNo : modelNoList) {
            MaterialModelInfo info = new MaterialModelInfo();
            info.setCustomerNo("B953N");
            info.setModelNo(modelNo);
            infoList.add(info);
        }
        log.info("getMaterialModelCanUseQtyBatchTest = {}", JSON.toJSONString(SMSAdapterService.getMaterialModelCanUseQtyBatch(infoList, deptNo)));
    }

    @Test
    public void getInventoryDetailByDeptNoTest() {
        String modelNo = "AN10-01";
        String deptNo = "188900"; // 223110
        // deptNo = null;
        List<InventoryInfo> infoList = SMSAdapterService.getInventoryDetailByDeptNo(modelNo, deptNo);
        for (InventoryInfo info : infoList) {
            System.out.println("=====> " + info.getInventoryName() + ", 总数量 = " + info.getInventoryQuantity() + ", List.size = " + info.getList().size());
            if (info.getList().size() > 0) {
                for (Inventory inventory : info.getList()) {
                    System.out.println("===> " + inventory);
                }
            }
            System.out.println("=========================================================");
        }
    }

    @Resource
    private WarehouseMapper warehouseMapper;

    @Test
    public void findGoodsLocationTest2() {
        List<String> warehouseList = new ArrayList<>();
        warehouseList.add("KBJ");
        warehouseList.add("KSH");
        warehouseList.add("KGZ");
        log.info("result ==== {}", warehouseMapper.findGoodsLocation(warehouseList));
    }

    @Test
    public void getCanPreInventoryTest() {
        InventoryCondition condition = new InventoryCondition();
        condition.setCustomerNo("B12W7");
        condition.setDeptNo("223113");
        List<String> list = new ArrayList<>();
        list.add("AN15-N02");
        condition.setModelNo(list);
        Map<String, List<Inventory>> map = SMSAdapterService.getCanPreInventory(condition);
        log.info("getCanPreInventoryTest result = {}", map);
    }

    @Test
    public void getFuzzyInventoryTest() {
        FuzzySearchCondition condition = new FuzzySearchCondition();
        condition.setModelNo("PFMB7202-06");
        condition.setProperty(null); // 排序
        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(10);
        log.info("getFuzzyInventoryTest result = {}", SMSAdapterService.getFuzzyInventory(condition, page));
    }

    @Test
    public void getProductBomByModelNoTest() {
        log.info("getProductBomByModelNoTest result = {}", JSON.toJSONString(SMSAdapterService.getProductBomByModelNo("PFM750S-01-A-M-S-X731")));
    }

    @Test
    public void findInventoryListTest() {
        List<String> modelNo = Arrays.asList("BM5-020","CDM2E20-30AZ", "D-M9BL");
        String deptNo = "223171";
        List<Inventory> list = SMSAdapterService.findInventoryList(modelNo, deptNo);
        for (Inventory info : list) {
            System.out.println("===> " + info);
        }
    }
}
