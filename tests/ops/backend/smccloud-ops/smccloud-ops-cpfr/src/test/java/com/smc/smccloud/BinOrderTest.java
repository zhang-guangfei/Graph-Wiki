package com.smc.smccloud;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.sales.ops.feign.RequestPurchaseFeignApi;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.BinOrderCalcMapper;
import com.smc.smccloud.mapper.binorder.BinTrialJobManageMapper;
import com.smc.smccloud.mapper.binorder.BinorderDetailRepository;
import com.smc.smccloud.mapper.binorder.ModelExpFreqMapper;
import com.smc.smccloud.model.Constants;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.binorder.*;
import com.smc.smccloud.service.*;
import com.smc.smccloud.service.impl.BinOrderCalcServiceImpl;
import com.smc.smccloud.service.impl.BinorderCreateServiceImpl;
import com.smc.smccloud.service.impl.BinorderServiceImpl;
import com.smc.smccloud.service.impl.bincalc.calculator.ReplenishmentCalcManager;
import com.smc.smccloud.service.impl.bincalc.calculator.ReplenishmentCalculator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BinOrderTest {

    @Resource
    BinorderServiceImpl binOrderServiceImpl;
    @Resource
    BinOrderCalcServiceImpl binOrderCalcServiceImpl;

    @Resource
    private BinOrderCalcService binOrderCalcService;

    @Resource
    private RequestPurchaseFeignApi requestPurchaseFeignApi;

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    @Resource
     private RedisManager redisUtil;

    @Resource
    private BinorderCreateServiceImpl binorderCreateService;
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Resource
    private CommonService commonService;

    @Resource
    private  BinorderService binorderService;

    @Resource
    private BinOrderCalcMapper binOrderCalcMapper;
    @Resource
    private BinorderDetailRepository binorderDetailRepository;
    @Resource
    private ReplenishmentCalculator replenishmentCalculator;
    @Resource
    private ReplenishmentCalcManager replenishmentCalcManager;


    @Test
    public void calcOrder2() {
        //List<BinOrderDetailDO> details = binorderDetailRepository.findBinOrderDetailByCalcId(7056L);
        //for (BinOrderDetailDO detail : details) {
        //    log.info(JSONUtil.toJsonStr( detail));
        //}
    }


    @Before
    public void before() {

        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        SMCApp.setForSysUser();
    }

    @Test
    public void calcOrder() {
        BinOrderCalcRequestDTO dto = new BinOrderCalcRequestDTO();
        dto.setWarehouseCode("KGZ");
        dto.setCalcType(1);
        binOrderServiceImpl.calcBinOrder(dto);
    }

    @Test
    @Transactional
    public void calcDetail() {
        //28110853 25A-MXQ12-40
        //28110844 G46-10-01-C2
        List<BinOrderDetailDO> details = Collections.singletonList(binorderDetailRepository.selectById(29830673));

        //创建缓存对象
        CacheComponent cache = replenishmentCalcManager.getCacheComponent(details);
        for (BinOrderDetailDO detail : details) {
            try {
                BinOrderDetailDTO dto = replenishmentCalculator.replenishmentStrategySelector(detail, cache);
                replenishmentCalcManager.insertTable(detail.getCalcId(), Collections.singletonList(dto));
                log.info(JSONUtil.toJsonStr(dto));
            } catch (NullPointerException e) {
                log.error("空指针异常：", e);
            } catch (Exception e) {
                // 不中断for循环的处理
                log.error("补库方案计算异常", e);
            }

        }

    }




    @Test
    public void listBinOrderDetail() {
        String modelNo = "25A-CDM2B20-100Z";
        Map<String, BinOrderInventoryInfoVO> result = binOrderCalcServiceImpl.getInventoryInfomap(modelNo);
        System.out.println("jigeio");
        System.out.println(result);
    }

    @Test
    public void listModelExpFreqTest() {
        ModelExpFreqRequest request = new ModelExpFreqRequest();
        Page page = new Page();
        request.setStockcode("KGZ");
        page.setPageNumber(1);
        page.setPageSize(10);
        PageInfo<ModelExpFreqVO> result = binOrderServiceImpl.listModelExpFreq(request);

        log.info(result.toString());
    }

    @Test
    public void updateBInOrderDetailTest() {
        BinOrderDetailUpdateDTO dto = new BinOrderDetailUpdateDTO();
        dto.setId(1L);
        dto.setModelNo("10-AR50-06-B");
        ArrayList<BinOrderDetailVO> items = new ArrayList<>();

        BinOrderDetailVO item1 = new BinOrderDetailVO();
        item1.setConfirmQty(10);
        item1.setOrderType("B");
        item1.setSupplierCode("JP");
        item1.setTransType("1");
        items.add(item1);

        BinOrderDetailVO item2 = new BinOrderDetailVO();
        item2.setConfirmQty(15);
        item2.setOrderType("9");
        item2.setSupplierCode("KBJ");
        item2.setTransType("0");
        items.add(item2);

        dto.setItems(items);

        binOrderServiceImpl.updateBinOrderDetail(dto);
    }

//    @Test
//    public void  createOrderTest()
//    {
//        binOrderServiceImpl.createOrderByAppId(272L);
//    }



    @Test
    public void listBinWarehouseStockTest() {
        // log.info("result 1 = {}", binOrderCalcServiceImpl.listBinWarehouseStock("VEX1500-04"));

        List<String> modelNoList = Arrays.asList("AF60-F10-A","AF60P-060S","AF800-12","AF800-14","AF800-14-2","AF810-14","AF811-12","AF811-14","AF900-20","AF910-20"
                ,"AF911-20","AFD20-01-A","AFD20-02-A","AFD20-02C-A","AFD20P-060AS","AFD30-02-A","AFD30-02C-A","AFD30-02D-A","AFD30-03-A","AFD30-03C-A"
                ,"AFD30-03D-A","AFD30-F03-A","AFD30P-060AS","AFD40-03-A","AFD40-03C-A","AFD40-03D-A","AFD40-04-A","AFD40-04C-A","AFD40-04D-A","AFD40-06-A"
                ,"AFD40P-060AS","AFF11C-04D","AFF11C-06","AFF11C-06D","AFF11C-06D-T","AFF22C-10","AFF22C-10D-T","AFF37B-10D","AFF37B-10D-T","AFF37B-14"
                ,"AFF37B-14D","AFF37B-14D-T","AFF4C-03","AFF4C-03D","AFF75B-20","AFF75B-20D","AFF75B-20D-T","AFF8C-04D","AFF8C-04D-T","AFF90D-20"
                ,"AFF-EL11B","AFF-EL22B","AFF-EL37B","AFF-EL4B","AFF-EL75B","AFF-EL8B","AFJ40-04-40-T","AFM20-01-A","AFM20-01C-A","AFM20-02-A"
                ,"AFM20-02C-A","AFM20-F02C-A","AFM20P-060AS","AFM30-02-A","AFM30-02C-A","AFM30-02D-6-A","AFM30-02D-A","AFM30-03-A","AFM30-03C-A","AFM30-03D-A"
                ,"AFM30-03D-R-A","AFM30P-060AS","AFM40-02-A","AFM40-03-A","AFM40-03C-A","AFM40-03D-A","AFM40-04-A","AFM40-04C-A","AFM40-04D-A","AFM40-04D-R-A"
                ,"AFM40-04-R-A","AFM40-06-A","AFM40-06D-A","AFM40-F04D-A","AFM40P-060AS","AK2000-02","AK2000-N02","AK4000-02","AK4000-03","AK4000-04"
                ,"AK4000-F04","AK4000-N04","AK6000-06","AK6000-10","AK6000-F10","AKB01A-01S","AKB01B-01S","AKB02A-02S","AKB02B-02S","AKB03A-03S"
                ,"AKB03B-03S","AKB04A-04S","AKB04B-04S","AKH04-00","AKH04A-M5","AKH04B-01S","AKH04B-M5","AKH06-00","AKH06A-01S","AKH06A-M5"
                ,"AKH06B-01S","AKH06B-M5","AKH07-00","AKH08-00","AKH08A-02S","AKH08B-01S","AKH08B-02S","AKH08B-03S","AKH10-00","AKH10A-03S"
                ,"AKH10B-02S","AKH10B-03S","AKH10B-04S","AKH12-00","AKH12A-03S","AKH12A-04S","AKH12B-03S","AKH12B-04S","AKM3000-02-A","AKM4000-03-A"
                ,"AL10-M5-A","AL-18S","AL20-01-A","AL20-02-2-A","AL20-02-3-A","AL20-02-A","AL30-02-3-A","AL30-02-A","AL30-03-2-A","AL30-03-3-A"
                ,"AL30-03-A","AL30-03-R-A","AL40-02-A","AL40-03-A","AL40-04-2-A","AL40-04-6-A","CDRB2BWU15-90SZ","CDRB2BWU20-180SZ","CDRB2BWU30-180SZ","CDRB2BWU30-90SZ"
                ,"CDRB2BWU40-90DZ","CDRBS20-90","CDRBU2WU20-180SZ","CDRBU2WU30-180SZ","CDRQ2BS10-180","CDRQ2BS10-90","CDRQ2BS15-180","CDRQ2BS15-90","CDRQ2BS20-180","CDRQ2BS20-90"
                ,"CDRQ2BS20-90C","CDRQ2BS30-180C","CDRQ2BS30-90C","CDRQ2BS40-180C","CDRQ2BS40-90C","CD-S03","CDS1BN180-180","CDS1FN200-250","CDS2B160-150","CDS2F160-200"
                ,"CDS2F160-250","CDU10-10D","CDU10-15D","CDU10-20D","CDU10-25D","CDU10-30D","CDU10-40D","CDU10-50D","CDU10-5D","CDU10-60D"
                ,"CDU16-10D","CDU16-15D","CDU16-20D","CDU16-25D","CDU16-30D","CDU16-40D","CDU16-5D","CDU16-60D","CDU20-100D","CDU20-10D"
                ,"CDU20-15D","CDU20-20D","CDU20-25D","CDU20-30D","CDU20-40D","CDU20-50D","CDU20-5D","CDU20-70D","CDU20-80D","CDU20-90D"
                ,"CDU25-100D","CDU25-10D","CDU25-15D","CDU25-20D","CDU25-25D","CDU25-30D","CDU25-40D","CDU25-50D","CDU25-5D","CDU25-60D"
                ,"CDU25-70D","CDU25-80D","CDU32-100D","CDU32-10D","CDU32-15D","CDU32-20D","CDU32-25D","CDU32-30D","KQ2L10-02GS","KQ2L10-02NS"
                ,"KQ2L10-03A","KQ2L10-03AS","KQ2L10-03NS","KQ2L10-04A","KQ2L10-04AS","KQ2L10-04NS","KQ2L10-12A","KQ2L10-99A","KQ2L10-G02A","KQ2L10-G03A"
                ,"KQ2L10-G04A","KQ2L10-U01A","KQ2L10-U02A","KQ2L10-U03A","KQ2L11-00A","KQ2L11-02NS","KQ2L11-03AS","KQ2L11-34AS","KQ2L11-35AS","KQ2L11-36AS"
                ,"KQ2L12-00A","KQ2L12-02A","KQ2L12-02AP","KQ2L12-02AS","KQ2L12-02GS","KQ2L12-02NS","KQ2L12-03A","KQ2L12-03AS","KQ2L12-03GS","KQ2L12-03NS"
                ,"KQ2L12-04A","KQ2L12-04AS","KQ2L12-04GS","KQ2L12-04NS","KQ2L12-16A","KQ2L12-99A","KQ2L12-G02A","KQ2L12-G03A","KQ2L12-G04A","KQ2L12-U02A"
                ,"KQ2L12-U04A","KQ2L13-00A","KQ2L13-02NS","KQ2L13-03AS","KQ2L13-04AS","KQ2L13-36AS","KQ2L13-37AS","KQ2L13-99A","KQ2L16-00A","KQ2L16-02AS"
                ,"KQ2L16-03AS","KQ2L16-03NS","KQ2L16-04A","KQ2L16-04AS","KQ2L16-04GS","KQ2L16-04NS","KQ2L16-99A","KQ2L16-G04A","KQ2L16-U04A","KQ2L23-01NS"
                ,"KQ2L23-M3G","KQ2L23-M5A","KQ2LE04-00A","KQ2LE04-00N","KQ2LE06-00A","KQ2LE06-00N","KQ2LE08-00A","KQ2LE08-00N","KQ2LE10-00A","KQ2LE12-00A"
                ,"KQ2LF04-01A","KAU06-00","KBE3-10","KBE3-12","KBP2","KBP3","KBX22","KBX6","KBZ1-06","KBZ2-08"
                ,"KBZ3-10","KCE06-00","KCE08-00","KCE10-00","KCH04-01S","KCH04-M5","KCH06-00","KCH06-02S","KCH08-00","KCH08-01S"
                ,"KCH08-02S","KCH10-02S","KCL06-01S","KCL06-M5","KCL08-02S","KDM10-04","KDM10-06","KDM10-07","KDM10-08","KDM10P-04"
                ,"KDM10P-06","KDM10P-08","KDM10S-04","KDM10S-06","KDM10S-08","KDM20-04","KDM20-06","KDM20-08","KDM20P-04","KDM20P-06"
                ,"KDM20P-08","KDM20S-04","KDM20S-06","KDM20S-08","KDMP-06","KDMS-06","KEB06","KEB08","KEB10","KEB12"
                ,"KFE06-00","KFE06-02","KFE06B-02","KFE08N-00","KFE08U-00","KFE12B-03","KFE12U-03","KFF06-02","KFF08B-02","KFF08N-02"
                ,"KFF08U-02","KFF10U-02","KFG2E0806-00","KFG2E1075-00","KFG2E1209-00","KFG2F0604-02","KFG2F1613-04","KFG2H0425-01","KFG2H0604-00","KFG2H0604-01"
                ,"KFG2H0604-01S","KFG2H0604-02","KFG2H0806-00","KFG2H0806-01","KFG2H0806-01S","KFG2H0806-02","KFG2H0806-02S","KFG2H0806-03","KFG2H0806-03S","KFG2H1008-02"
                ,"KFG2H1008-03","KFG2H1008-04","KFG2H1075-03","KFG2H1209-02","KFG2H1209-02S","KFG2H1209-04","KFG2H1209-04S","KFG2H1210-02","KFG2H1210-04","KFG2H1613-04"
                ,"KFG2H1613-04S","KFG2L0604-01","KFG2L0604-02","KFG2L0604-02S","KFG2L0806-00","KFG2L0806-01","KFG2L0806-01S","KFG2L0806-02","KFG2L0806-03","KFG2L1008-02"
                ,"KFG2L1075-02S","KFG2L1209-02","KFG2L1209-03","KFG2L1209-03S","KFG2L1209-04","KFG2L1613-03S","KFG2T0425-00","KFG2T0604-00","KFG2T0604-01","KFG2T0806-00"
                ,"KFG2T1008-00","KFG2T1613-00","KFG2V0604-01","KFG2V0604-01S","KFG2V0806-01","KFG2V0806-02","KFH04-01","KFH04-01S","KFH04-02","KFH04-02S"
                ,"KFH04B-01S","KFH06-00","KFH06-01","KFH06-01S","KFH06-02","KFH06-02S","KFH06-03S","KFH06B-01S","KFH06B-02S","KFH08B-01S"
                ,"KFH08B-02S","KFH08B-03S","KFH08N-01","KFH08N-01S","KFH08N-02","KFH08N-02S","KFH08N-03S","KFH08U-01","KFH08U-01S","KFH08U-02"
                ,"KFH08U-02S","KFH08U-03S","KFH10B-02S","KFH10N-02","KFH10N-02S");
        List<String> warehouseList = Arrays.asList("KBJ", "KGZ","KSH");
//
//        List<WarehouseInventoryVO> inventoryVOS=new ArrayList<>();
//        List<BindataDO> bindataDOS=new ArrayList<>();
//        try {
//            Future<List<WarehouseInventoryVO>> inventoryfuture = commonService.asyncGetWarehouseInventoryByModels(modelNoList, warehouseList);
//            Future<List<BindataDO>> binDatafuture = commonService.asyncGetBinDataByModels(modelNoList, warehouseList);
//            while (true) {
//                if (inventoryfuture.isDone() && binDatafuture.isDone()) {
//                    log.info("result 2 = {}", inventoryfuture.get());
//                    inventoryVOS.addAll(inventoryfuture.get());
//                    bindataDOS.addAll(binDatafuture.get());
//                    System.out.println("完成");
//                    break;
//                }
//            }
//        } catch (NullPointerException ex) {
//            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误1：" + ex);
//
//        } catch (Exception ex) {
//            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误2：" + ex);
//
//        }
//


      log.info("result 2 = {}", binOrderCalcServiceImpl.listBinWarehouseStock(modelNoList, warehouseList));
    }

    @Test
    public void addModelExpFreqCsvTest() {
        binOrderCalcService.addModelExpFreqCsv();
    }

    @Test
    public void calcUpdate() {

    }

    @Test
    public void listBinWarehouseStockTest11(){
        ResultVo<List<BinOrderInventoryInfoVO>> resultVo= binOrderCalcServiceImpl.listBinWarehouseStock("PSE531-M5-L");
        System.out.println(resultVo.getData());
        BinOrderDetailDO detailDO =new BinOrderDetailDO();
    }

    //@Test
    //public void calcBinOrderTransQtyTest() {
    //    BinOrderCalcRequestDTO dto = new BinOrderCalcRequestDTO();
    //    dto.setCalcId(2358l);
    //    dto.setWarehouseCode("KSH");
    //    dto.setCalcType(1);
    //    RedisMessageVO vo = new RedisMessageVO();
    //    vo.setStatus(1);
    //    vo.setNo("Test985");
    //    binOrderCalcServiceImpl.calcBinOrderTransQty(dto, vo);
    //    System.out.println("完成");
    //
    //}

    //@Test
    //public void calcasycalcBinOrderTest() {
    //    try {
    //        BinOrderCalcRequestDTO dto = new BinOrderCalcRequestDTO();
    //        dto.setCalcId(1860l);
    //        dto.setWarehouseCode("KSH");
    //        dto.setCalcType(1);
    //
    //        binOrderCalcServiceImpl.asycalcBinOrder(dto);
    //        System.out.println("完成");
    //    }catch (Exception ex) {
    //        log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" + ex);
    //    }
    //
    //}


    @Test
    public void createOrderTest() {
        binorderCreateService.createOrderByAppId(677L);

    }
    @Resource
    private BinorderCreateServiceImpl  binorderCreateServiceImpl;
    @Test
    public void onCreateOrderByAppIdTest(){
        log.info("onCreateOrderByAppIdTest  KCZ = {}", binorderCreateServiceImpl.onCreateOrderByAppId(684L));
    }


    @Test
    public void listOrdingorderTest() {
        ResultVo<List<OrdingOrderVO>> resultVo=  binOrderServiceImpl.listOrdingOrder("SY5120-5DZD-C6",  "KBJ", null);
        System.out.println("dns");
    }

    @Test
    public void getBinOrderTransWarehouseTest() {
        ResultVo<DataTypeVO> resultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("4012", "KBJ");
        log.info("getBinOrderTransWarehouseTest KBJ = {}", resultVo);

        resultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("4012", "KGZ");
        log.info("getBinOrderTransWarehouseTest KGZ = {}", resultVo);

        resultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("4012", "KSH");
        log.info("getBinOrderTransWarehouseTest KSH = {}", resultVo);

        resultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("4012", "KCZ");
        log.info("getBinOrderTransWarehouseTest  KCZ = {}", resultVo);

    }
    @Resource
    private BinTrialJobManageMapper binTrialJobManageMapper;

    @Resource
    private BinTrialJobManageService binTrialJobManageService;
    @Test
    public  void getBinTrialJobManageDOTest(){
//        LambdaQueryWrapper<BinTrialJobManageDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq( BinTrialJobManageDO::getId,3l);
//        BinTrialJobManageDO jobInfo = binTrialJobManageMapper.selectOne(queryWrapper);

        binTrialJobManageService.copyBinTrialJobManager(3l);
    }
//    @Resource
//    private  CommonServiceFeignApi commonServiceFeignApi;

    @Test
    public  void  cancelTransQtyTest()
    {
        binOrderCalcMapper.cancelTransQty( 1149L);
        binOrderCalcMapper.updateOrderTypeB2K(1149L);

        binOrderCalcMapper.cancelTransQty( 1150L);
        binOrderCalcMapper.updateOrderTypeB2K(1150L);

        binOrderCalcMapper.cancelTransQty( 1151L);
        binOrderCalcMapper.updateOrderTypeB2K(1151L);

        binOrderCalcMapper.cancelTransQty( 1152L);
        binOrderCalcMapper.updateOrderTypeB2K(1152L);


        //binOrderCalcMapper.cancelTransQty( 1048L);
        //binOrderCalcMapper.cancelTransQty( 1049L);
        //binOrderCalcMapper.cancelTransQty( 1050L);
    }
    @Test
    public void getWarehouseSalesBranchConfigForPriorityTest() {
        log.info("getWarehouseCodeInfoTest = {}", commonServiceFeignApi.getWarehouseSalesBranchConfigForPriority());
    }

    @Test
    public void batchUpdateBinOrderTest()
    {
        BinOrderBatchUpdateDTO dto =new BinOrderBatchUpdateDTO();
        dto.setCalcId(1120L);
        dto.setTransQty(0);
        dto.setIds(new ArrayList<>());
        dto.getIds().add(8586867L);
        //dto.getIds().add(8586863L);
        //dto.setTransType("1");
        dto.setOrderType("K");
        binOrderServiceImpl.batchUpdateBinOrder(dto);
    }
    @Test
    public void calcmodelExpFreqTest(){
        binorderService.calcmodelExpFreq(1);
    }
    @Resource
    private ModelExpFreqMapper modelExpFreqMapper;
    @Test
    public void  calcModelExpFreqByWholeTest(){
      //  modelExpFreqMapper.calcModelExpFreqByWhole(1);
        modelExpFreqMapper.calcModelExpFreqByWareHouse(1);
      //  modelExpFreqMapper.calcModelExpFreqByCustomer(1);
      //  modelExpFreqMapper.calcModelExpFreqBySubWarehouse(1);
     //   modelExpFreqMapper.calcModelExpFreqByCustomGroupCode(1);
    }
    @Autowired
    private RedissonUtil redissonUtil;
    @Test
    public void testLock() {
        String key=Constants.REDIS_KEY_BIN_CALC_LOCK+"90";
        Boolean isLock= redissonUtil.tryLock(key,0,12*60*60,TimeUnit.SECONDS);//12个小时
        if (!isLock){
            log.info("已上锁");
            return;
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            redissonUtil.unlock(key);
            e.printStackTrace();
        } finally {
            //redissonUtil.unlock(lock_test);
            log.info("完成");
        }
    }

}
