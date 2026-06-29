package com.smc.smccloud;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.csstock.CsApplyDetailMapper;
import com.smc.smccloud.mapper.csstock.CsApplyMapper;
import com.smc.smccloud.mapper.csstock.CsInventoryMapper;
import com.smc.smccloud.mapper.csstock.CsStockSettingMapper;
import com.smc.smccloud.mapper.warehouse.WarehouseMapper;
import com.smc.smccloud.model.csstock.*;
import com.smc.smccloud.service.ConsignmentStockFeignApi;
import com.smc.smccloud.service.CsStockApplyService;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import com.smc.smccloud.service.ProductBomService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Random;

/**
 * Author: wangshunfan
 * Date: 2021-11-03 10:23
 * Description:
 */
@Slf4j
public class CsStockApplyServiceTests extends OpsShareAppApplicationTests {

    @Resource
    private CsApplyMapper csApplyMapper;
    @Resource
    private CsApplyDetailMapper csApplyDetailMapper;
    @Resource
    private CsStockSettingMapper csStockSettingMapper;
    @Resource
    private WarehouseMapper warehouseMapper;
    @Resource
    private CsInventoryMapper csInventoryMapper;

    @Resource
    private CsStockApplyService csStockApplyService;
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;


    @Resource
    private ConsignmentStockFeignApi consignmentStockFeignApi;

    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        SMCApp.setForSysUser();
    }

    @Test
    public void selectCsApplyByIdTest() {
        CsApplyDO csApplyDO = this.csApplyMapper.selectById(1);
        log.info("csApplyDO = " + csApplyDO);
    }


    @Test
    public void insertCsApplyTest() {
        for (int i = 0; i < 20; i++) {
            log.info("csApplyDO  test");
            CsApplyDO applyMaster = new CsApplyDO();
            applyMaster.setCustomerNo("SM002");
            applyMaster.setStatus(1);
            applyMaster.setCreateUser("smctest");
            applyMaster.setRemark("测试1");
            applyMaster.setUpdateUser("smctest");
            applyMaster.setStockCode("VSZ");
            applyMaster.setTotalQty(100);
            this.csApplyMapper.insert(applyMaster);
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("customer_no", "SM002");
            queryWrapper.eq("stock_code", "VSZ");
            List<CsStockSettingDO> settingDOs = csStockSettingMapper.selectList(queryWrapper);
            Integer random = new Random().nextInt(100);
            for (int j = 0; j < random; j++) {
                CsApplyDetailDO detailDO = new CsApplyDetailDO();
                detailDO.setStatus(1);
                detailDO.setQuantity(100);
                detailDO.setUpdateUser("smctest");
                detailDO.setCalcQty(100);
                detailDO.setModelNo(settingDOs.get(j).getModelNo());
                detailDO.setApplyId(applyMaster.getApplyId());
                this.csApplyDetailMapper.insert(detailDO);
            }
        }
    }

    @Test
    public void insertCsApplyDetailTest() {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_no", "SM002");
        queryWrapper.eq("stock_code", "VSZ");
        List<CsStockSettingDO> settingDOs = csStockSettingMapper.selectList(queryWrapper);
        Integer random = new Random().nextInt(100);
        for (int j = 0; j < random; j++) {
            CsApplyDetailDO detailDO = new CsApplyDetailDO();
            detailDO.setStatus(1);
            detailDO.setQuantity(100);
            detailDO.setUpdateUser("smctest");
            detailDO.setCalcQty(100);
            detailDO.setModelNo(settingDOs.get(j).getModelNo());
            detailDO.setApplyId((long) 70);
            this.csApplyDetailMapper.insert(detailDO);
        }

    }

    @Test
    public void updateCsApplyByIdTest() {
//        CsApplyDO applyDO = new CsApplyDO();
//        applyDO.setCustomerNo("SM001");
//        applyDO.setStatus(1);
//        applyDO.setCreateUser("8455");
//        applyDO.setRemark("测试1");
//        applyDO.setUpdateUser("8455");
//        applyDO.setStockCode("SM002-01");
//        applyDO.setTotalQty(10);
//        int result = this.csApplyMapper.updateById(applyDO);
//        log.info("返回行数：" + String.valueOf(result));

        CsStockImportOrderReceiveRequest item = new CsStockImportOrderReceiveRequest();
        Long[] ids = new Long[]{2796652L};
        item.setIds(ids);
        item.setWarehouseCode("WA2N06");
        item.setBarcodes(new String[]{""});
        ResultVo<String> resultVo = csStockApplyService.confirmReceiveCsStock(item);
        System.out.println("resultVo = " + resultVo);

    }

    @Test
    public void contextLoads() {
        log.info(" = ");
    }


    @Test
    public void insertCsSettingTest() {
        for (int i = 0; i < 50; i++) {
            log.info("csApplyDO  test");
            CsStockSettingDO applyDO = new CsStockSettingDO();
            applyDO.setCustomerNo(String.format("SX00%s", String.valueOf(i)));
            applyDO.setStatus(1);
            applyDO.setCreateUser("8544");
            applyDO.setLocationNo("A01B01");
            applyDO.setUpdateUser("8544");
            applyDO.setStockCode("SM001-01");
            applyDO.setInitStockMonth(12);
            applyDO.setModelNo("SS5Y3-20-02" + String.valueOf(new Random().nextInt(500)) + String.valueOf(new Random().nextInt(500)));
            applyDO.setInitStockQty(new Random().nextInt(500));
            applyDO.setReplType(1);
            applyDO.setInitUnitQty(new Random().nextInt(20));
            this.csStockSettingMapper.insert(applyDO);
        }
    }

    @Test
    public void calcAndGetCsStockCodeReplQtyTest() {
        ResultVo<List<CsStockReplenishmentVO>> resultVo = this.csStockApplyService.listReplDetail("WF001", "WWF001HZ");
        log.info(resultVo.toString());
    }


//    @Test
//    public void confirmCsApplyTest() {
//        CsApplyConfirmDTO request = new CsApplyConfirmDTO();
//        request.setApplyId(107);
//        request.setApprovePass(true);
//        request.setApproveText("OK....");
//        ResultVo<String> result = this.csStockApplyService.confirmCsApply(request);
//        System.out.println(result);
//    }

    @Test
    public void getCsStockApplyIdTest() {
        ResultVo<String> randomOrderNoGenerator = dictDataServiceFeignApi.getRandomOrderNoGenerator("9001", "11");// 寄售订单
        String rorderNo = randomOrderNoGenerator.getData();
        System.out.println("订单号：");
        System.out.println(rorderNo);
    }

    @Test
    public void listCsInventoryTest() {
        CsInventoryRequestDTO csStockInventoryRequestDTO = new CsInventoryRequestDTO();
        List<CsInventoryVO> csStockInventoryVOResultVo = this.csInventoryMapper.listCsInventory(csStockInventoryRequestDTO);
        System.out.println(csStockInventoryVOResultVo);
    }

    @Test
    public void listCsWarehouseTest() {
//        CsImportDataDTO item = new CsImportDataDTO();
//        item.setRoId("aaaaa");
//        item.setInvoiceNo("testInvoice12112");
//        item.setWarehouseCode("Test");
//        List<CsImportDataDTO> list = new ArrayList<>();
//        list.add(item);
//        ResultVo<String> resultVo = consignmentStockFeignApi.addImpData(list);
//        System.out.println("resultVo = " + resultVo);
//        CsWarehouseRequest request = new CsWarehouseRequest();
//        ResultVo<PageInfo<CsWarehouseVO>> pageInfoResultVo = consignmentStockFeignApi.listCsWarehouse(request);
//        System.out.println("pageInfoResultVo = " + pageInfoResultVo.toString());

    }

    @Test
    public void updateCsStockModelLocationNoTest() {
        CsStockUpdateLocationDTO csStockUpdateLocationDTO = new CsStockUpdateLocationDTO();
        csStockUpdateLocationDTO.setAgentNo("WF001");
        csStockUpdateLocationDTO.setWarehouseCode("WWF001HZ");
        csStockUpdateLocationDTO.setModelNo("CDRB2BWU30-90SZ");
        csStockUpdateLocationDTO.setLocationNo("V11A10");

        ResultVo<String> csWarehouse = this.csStockApplyService.updateCsStockModelLocationNo(csStockUpdateLocationDTO);
        log.info("" + csWarehouse);
    }

    @Test
    public void listReplDetailTest() {
        ResultVo<List<CsStockReplenishmentVO>> listResultVo = this.csStockApplyService.listReplDetail("WF001", "WWF001HZ");
        log.info("返回：" + listResultVo);
    }

    @Test
    public void listCsImportDetailTest() {
        CsImportDetailRequestDTO requestDTO = new CsImportDetailRequestDTO();
        ResultVo<PageInfo<CsImportDetailVO>> csImpdata = this.csStockApplyService.listCsImportDetail(requestDTO);
        log.info("返回：" + csImpdata);
    }

    @Test
    public void listCsSExportDetailTest() {
        CsExportDetailRequestDTO requestDTO = new CsExportDetailRequestDTO();
        ResultVo<PageInfo<CsExportDetailVO>> csExportDetail = this.csStockApplyService.listCsExportDetail(requestDTO);
        log.info("返回：" + csExportDetail);
    }

    @Test
    public void listCsReturnApplyDetailTest() {

        CsReturnDetailRequestDTO requestDTO = new CsReturnDetailRequestDTO();
        requestDTO.setApplyId(2);
        ResultVo<PageInfo<CsReturnDetailVO>> csReturnApplyDetail = this.csStockApplyService.listCsReturnApplyDetail(requestDTO);
        log.info("返回：" + csReturnApplyDetail);
    }

    @Test
    public void listMonthBalanceTest() throws ParseException {
//        CsBalcenQryRequest request = new CsBalcenQryRequest();
//        request.setAgentNo("WF001");
//        request.setWarehouseCode("WWF001HZ");
////        request.setModelNo("AM-BM104");
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//        request.setMonthDate(ft.parse("2022-01-01"));
//        ResultVo<PageInfo<CsModelBalanceVO>> csModelBalanceVO = this.csStockApplyService.listMonthBalance(request);
//        log.info("返回：" + csModelBalanceVO);
    }

    @Test
    public void createReplApplyTest() {

        ResultVo<String> result = this.csStockApplyService.createReplApply("WF001", "WWF001HZ");
        log.info("返回：" + result);
    }

    @Test
    public void listCalcReturnTest() {

    }

    @Test
    public void createCalcReturnApplyTest() {
        ResultVo<String> result = this.csStockApplyService.createReturnApply("AD009", "WAD009DG");
        log.info("返回：" + result);
    }

    @Test
    public void listReturnApplyTest() {
        CsReturnApplyRequestDTO requestDTO = new CsReturnApplyRequestDTO();
        ResultVo<PageInfo<CsReturnApplyVO>> result = this.csStockApplyService.listReturnApply(requestDTO);
        log.info("返回：" + result);
    }

    @Test
    public void confirmReceiveCsStockTest() {
        //
        CsStockImportOrderReceiveRequest requestDTO=new CsStockImportOrderReceiveRequest();
        requestDTO.setWarehouseCode("WA2N06");
        requestDTO.setAgentNo("A2N06");
        String[] barcodes={"1100001690372"};
        requestDTO.setBarcodes(barcodes);
        ResultVo<String> result = this.csStockApplyService.confirmReceiveCsStock(requestDTO);
        log.info("返回：" + result);
    }

    @Resource
    private ProductBomService productBomService;

    @Test
    public void isCanSplitTest() {
        System.out.println("isCanSplitTest result = " + productBomService.isCanSplit("ITV1050-312L"));
    }

    @Test
    public void calCsImpExpQtyTest() {
        log.info("calCsImpExpQtyTest  {}", csStockApplyService.calCsImpExpQty());
    }

    @Test
    public  void getCanUseEAmountTest(){

        log.info("getCanUseEAmountTest  {}", csStockApplyService.getCanUseEAmount("B0560"));
    }

    @Test
    public  void listCsStockApplyTest(){
        CsStockApplyRequest request=new CsStockApplyRequest();
        csStockApplyService.listCsStockApply(request);
    }

}


