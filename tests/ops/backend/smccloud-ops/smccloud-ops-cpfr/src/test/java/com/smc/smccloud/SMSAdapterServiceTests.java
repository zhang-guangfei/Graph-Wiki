package com.smc.smccloud;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.dto.order.TransOrderDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.model.adapter.*;
import com.smc.smccloud.model.shikomi.ShikomiVO;
import com.smc.smccloud.service.ProductServiceFeignApi;
import com.smc.smccloud.service.SMSAdapterService;
import com.smc.smccloud.service.impl.SMSAdapterServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Author: B90034
 * Date: 2022-03-22 13:18
 * Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SMSAdapterServiceTests {

    @Resource
    private SMSAdapterService SMSAdapterService;
    @Resource
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;

    @Before
    public void before() {

        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    @Test
    public void listBinOrGSSTest() {
        ProductCondition condition = new ProductCondition();
        condition.setDeptNo("223112");
        condition.setModelList(Arrays.asList("AN10-01", "AN15-02"));
        log.info("params = {}", JSON.toJSONString(condition));
        log.info("listBinOrGSSTest result = {}", SMSAdapterService.listBinOrGSS(condition));
    }

    @Test
    public void listModelBinOrGSSInfoTest() {
        ProductCondition condition = new ProductCondition();
        condition.setWarehouseCode("KBJ");
        condition.setModelList(Arrays.asList("AN10-01", "AN15-02"));
        log.info("params = {}", JSON.toJSONString(condition));
        log.info("listModelBinOrGSSInfoTest result = {}", SMSAdapterService.listModelBinOrGSSInfo(condition));
    }

    @Test
    public void productInfoTest() {
        ProductCondition condition = new ProductCondition();
        condition.setDeptNo("243383");
        condition.setCustomerNo("B65CM");
        ModelCalCondition calCondition = new ModelCalCondition();
        calCondition.setModelNo("ZP2A-0305-CJXN0885");
        calCondition.setCal(true);
        condition.setModelCalList(Collections.singletonList(calCondition));
        log.info("condition = {}", JSON.toJSONString(condition));
        log.info("productInfo result = {}", JSON.toJSONString(SMSAdapterService.productInfo(condition)));
    }

    @Test
    public void findAutoBinInfoByCustomerTest() {
        DataAuthoritySearchCondition condition = new DataAuthoritySearchCondition();
        condition.setCustomerCodeListByDataAuthority(Collections.singletonList("A4D94"));
        String customerNo = "A4D94";
        log.info("findAutoBinInfoByCustomerTest result = {}", JSON.toJSONString(SMSAdapterService.findAutoBinInfoByCustomer(condition, customerNo)));
    }

    @Test
    public void findBinModelInfoTest() {
        BinModelCondition condition = new BinModelCondition();
        condition.setCustomerNo("A4D94");
        String[] modelNos = new String[]{"AW20-N02H-2-A", "AW20-02CG-A"};
        condition.setModelNoList(Arrays.asList(modelNos));
        log.info("condition = {}", JSON.toJSONString(condition));
        log.info("findBinModelInfoTest result = {}", JSON.toJSONString(SMSAdapterService.findBinModelInfo(condition)));
    }

    @Test
    public void findAutoBinCustomerInfoTest() {
        DataAuthoritySearchCondition condition = new DataAuthoritySearchCondition();
        condition.setDeptCodeListByDataAuthority(Arrays.asList("224018", "224214", "224215", "261340", "261341"));
        log.info("condition = {}", JSON.toJSONString(condition));
        log.info("findAutoBinCustomerInfo result = {}", JSON.toJSONString(SMSAdapterService.findAutoBinCustomerInfo(condition)));
    }

    @Test
    public void transferApiTest() {
        TransOrderDto dto = new TransOrderDto();
        CommonResult<String> transResult = opsWmDispatchForOrderFeignApi.createTransOrder(dto);
        log.info("transResult = {}", JSON.toJSONString(transResult));
    }

    @Test
    public void listStockForReplTest() {
         List<String> list =  Arrays.asList("HRZ-S0006");
//         List<String> list = Arrays.asList("AC25-02DG-A", "AC25-02DG-SV-A", "AC25-02E", "AC25-02G-A", "AC25-02G-KV-12R", "AC25-02M-A", "AC25-02M-S-A", "AC25B-02CG-A", "AC25B-02G-A", "AC25B-ASS24-B"
//                    , "AC25C-N02G-V-Z-A", "AC30-02-A", "AC30-02CG-A", "AC30-02DE-2", "AC30-02DE-SV", "AC30-02DG-A", "AC30-02E-B", "AC30-02M-A", "AC30-02-X465A", "AC30-03-A"
//                    , "AC30-03CE-V-B", "AC30-03CG-B", "AC30-03CM-SV-A", "AC30-03C-V-A", "AC30-03D-A", "AC30-03DE-B", "AC30-03DG-1-A", "AC30-03DG-A", "AC30-03DG-B", "AC30-03DG-S-A"
//                    , "AC30-03DG-SV-A", "AC30-03-DNY2744", "AC30-03D-V-A", "AC30-03E-B", "AC30-03G-A", "AC30-03-V-A", "AC30A-02-A", "AC30A-02-B", "AC30A-02CE-38-B", "AC30A-02CE-B"
//                    , "AC30A-02CG-A", "AC30A-02D-A", "AC30A-02DE-B", "AC30A-02DG-A", "AC30A-02E-3-B", "AC30A-02E-B", "AC30A-02G-A", "AC30A-02G-B", "AC30A-03-B", "AC30A-03C-A"
//                    , "AC30A-03CE-B", "AC30A-03CG-A", "CDJ2D16-50Z-B", "CDJ2D16-60AZ-B", "CDJ2D16-60Z-B", "CDJ2D16-75Z-B", "CDJ2E10-125Z-B", "CDJ2E10-150Z-B", "CDJ2E10-15TZ-B", "CDJ2E10-40Z-B"
//                    , "CDJ2E10-60Z-B", "CDJ2E10-75AZ-B", "CDJ2E10-75Z-B", "CDJ2E16-100Z-B", "CDJ2E16-125Z-B", "CDJ2E16-150Z-A", "CDJ2E16-150Z-B", "CDJ2E16-175Z-B", "CDJ2E16-200Z-B", "CDJ2E16-250Z-B"
//                    , "CDJ2E16-30Z-B", "CDJ2E16-45Z-B", "CDJ2E16-60Z-B", "CDJ2E16-75Z-B", "CDJ2E6-30Z-B", "CDQ2B32-30DCZ", "CDQ2B32-30DMZ", "CDQ2B32-30DMZ-XC8", "CDQ2B32-30DZ", "CDQ2B32-30DZ-DNP0763"
//                    , "CDQ2B32-30DZ-XC8", "CDQ2B32-35DCMZ", "CDQ2B32-35DM", "CDQ2B32-35DMZ", "CDQ2B32-35DZ", "CDQ2B32-40DCMZ", "CDQ2B32-40DCMZ-XC89", "CDQ2B32-40DCZ", "CDQ2B32-40DMZ", "CDQ2B32-40DZ"
//                    , "CDQ2B32-45DMZ", "CDQ2B32-45DZ", "CDQ2B32-45DZ-XC8", "CDQ2B32-50DCMZ", "CDQ2B32-50DCZ", "CDQ2B32-50DMZ", "CDQ2B32-50DMZ-XC8", "CDQ2B32-50DZ", "ITV3050-043N", "ITV3050-044L"
//            );

        StockQueryForReplDTO replDTO = new StockQueryForReplDTO();
        replDTO.setInventoryTypeCode("ZL-CP");
        replDTO.setGroupCustomerNo("");

        // replDTO.setInventoryTypeCode("TY");
        replDTO.setIsQryBindata(true);
        replDTO.setIsQryShikomi(true);
        replDTO.setIsQrySubWarehouseStock(true);
        replDTO.setModelNos(list);


        log.info("listStockForReplTest ==>{}", JSONObject.toJSONString(SMSAdapterService.listStockForRepl(replDTO)));
    }
 @Test
 public  void getBinInfoByModelNoTest(){
     List<BinInfo> rtnData=SMSAdapterService.getBinInfoByModelNo("AS3000-03");
     log.info("getBinInfoByModelNoTest ==>{}", JSONObject.toJSONString(rtnData));
 }
    @Resource
    private SMSAdapterServiceImpl smsAdapterServiceImpl;

//    @Test
//    public void futureGetCanUseShikomiQuantityTest() {
//        try {
//
//
//            List<String> list = Arrays.asList("AC25-02DG-A", "AC25-02DG-SV-A", "AC25-02E", "AC25-02G-A", "AC25-02G-KV-12R", "AC25-02M-A", "AC25-02M-S-A", "AC25B-02CG-A", "AC25B-02G-A", "AC25B-ASS24-B"
//                    , "AC25C-N02G-V-Z-A", "AC30-02-A", "AC30-02CG-A", "AC30-02DE-2", "AC30-02DE-SV", "AC30-02DG-A", "AC30-02E-B", "AC30-02M-A", "AC30-02-X465A", "AC30-03-A"
//                    , "AC30-03CE-V-B", "AC30-03CG-B", "AC30-03CM-SV-A", "AC30-03C-V-A", "AC30-03D-A", "AC30-03DE-B", "AC30-03DG-1-A", "AC30-03DG-A", "AC30-03DG-B", "AC30-03DG-S-A"
//                    , "AC30-03DG-SV-A", "AC30-03-DNY2744", "AC30-03D-V-A", "AC30-03E-B", "AC30-03G-A", "AC30-03-V-A", "AC30A-02-A", "AC30A-02-B", "AC30A-02CE-38-B", "AC30A-02CE-B"
//                    , "AC30A-02CG-A", "AC30A-02D-A", "AC30A-02DE-B", "AC30A-02DG-A", "AC30A-02E-3-B", "AC30A-02E-B", "AC30A-02G-A", "AC30A-02G-B", "AC30A-03-B", "AC30A-03C-A"
//                    , "AC30A-03CE-B", "AC30A-03CG-A", "CDJ2D16-50Z-B", "CDJ2D16-60AZ-B", "CDJ2D16-60Z-B", "CDJ2D16-75Z-B", "CDJ2E10-125Z-B", "CDJ2E10-150Z-B", "CDJ2E10-15TZ-B", "CDJ2E10-40Z-B"
//                    , "CDJ2E10-60Z-B", "CDJ2E10-75AZ-B", "CDJ2E10-75Z-B", "CDJ2E16-100Z-B", "CDJ2E16-125Z-B", "CDJ2E16-150Z-A", "CDJ2E16-150Z-B", "CDJ2E16-175Z-B", "CDJ2E16-200Z-B", "CDJ2E16-250Z-B"
//                    , "CDJ2E16-30Z-B", "CDJ2E16-45Z-B", "CDJ2E16-60Z-B", "CDJ2E16-75Z-B", "CDJ2E6-30Z-B", "CDQ2B32-30DCZ", "CDQ2B32-30DMZ", "CDQ2B32-30DMZ-XC8", "CDQ2B32-30DZ", "CDQ2B32-30DZ-DNP0763"
//                    , "CDQ2B32-30DZ-XC8", "CDQ2B32-35DCMZ", "CDQ2B32-35DM", "CDQ2B32-35DMZ", "CDQ2B32-35DZ", "CDQ2B32-40DCMZ", "CDQ2B32-40DCMZ-XC89", "CDQ2B32-40DCZ", "CDQ2B32-40DMZ", "CDQ2B32-40DZ"
//                    , "CDQ2B32-45DMZ", "CDQ2B32-45DZ", "CDQ2B32-45DZ-XC8", "CDQ2B32-50DCMZ", "CDQ2B32-50DCZ", "CDQ2B32-50DMZ", "CDQ2B32-50DMZ-XC8", "CDQ2B32-50DZ", "ITV3050-043N", "ITV3050-044L"
//            );
//            long startTimer = System.currentTimeMillis();
//            Map<String, Integer> mapShikomiQuantity = new HashMap<>();
//            Future<Map<String, Integer>> futureSkikomi1 = smsAdapterServiceImpl.futureGetCanUseShikomiQuantity(list);
//            while (true) {
//                if (futureSkikomi1.isDone()) {
//                    mapShikomiQuantity = futureSkikomi1.get();
//                    break;
//                }
//            }
//            log.info("listStockForRepl ==>处理完成，耗时:{}s", (System.currentTimeMillis() - startTimer) / 1000.0);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
