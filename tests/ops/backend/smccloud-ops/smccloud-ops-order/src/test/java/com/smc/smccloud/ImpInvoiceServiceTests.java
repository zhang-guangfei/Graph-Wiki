package com.smc.smccloud;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.inventory.InventoryForAdjustDto;
import com.sales.ops.feign.RequestPurchaseFeignApi;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.FileUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.model.invoice.*;
import com.smc.smccloud.model.stockassembly.StockAssemblyRequest;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.ImpInvoiceService;
import com.smc.smccloud.service.PoInvoiceService;
import com.smc.smccloud.service.StockAdjustService;
import com.smc.smccloud.service.StockAssemblyFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Author: B90034
 * Date: 2021-12-03 09:31
 * Description:
 */
@Slf4j
public class ImpInvoiceServiceTests extends OpsOrderApplicationTest {

    @Resource
    private ImpInvoiceService impInvoiceService;

    @Resource
    private StockAdjustService stockAdjustService;

    @Resource
    private RequestPurchaseFeignApi requestPurchaseFeignApi;

    @Resource
    private StockAssemblyFeignApi stockAssemblyFeignApi;
    @Resource
    private SendMessage sendMessage;

    @Before
    public void setUp() {

        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS
        );
    }

    @Test
    public void importInvoiceInfoTest() {
        String plantMark = "AM";
        String invNo = "23081192304";
        Date startTime = null; // DateUtil.stringToDate("2022-08-30 05:00:00");
        Date endTime = null; // DateUtil.stringToDate("2022-08-31 05:30:00");
        log.info("result = {}", impInvoiceService.importInvoiceDetailFromGW(plantMark, invNo, startTime, endTime));
    }

    @Test
    public void confirmPOInvoiceTest() {
        ResultVo<String> result = impInvoiceService.confirmPOInvoice(230400387);
        System.out.println(result.toString());
    }

    @Test
    public void updateMasterNoStorageTest(){
        ImpInvoiceMasterRequest request =new ImpInvoiceMasterRequest();
        request.setIds(   Arrays.asList(24011208l));
       log.info(" updateMasterNoStorageTest -->返回：{}",impInvoiceService.updateMasterNoStorage(request));
    }

    @Test
    public void finishConfirmTest() {
        ImpInvoiceProcessDTO dto = new ImpInvoiceProcessDTO();
        dto.setOptUser("test");
        dto.setInvoiceId(23110021L);
        dto.setOptTime(new Date());
        dto.setRemark("test");
        dto.setProcessType(8);
        ResultVo<String> result = impInvoiceService.finishConfirmImpInvoice(dto);
        System.out.println(result.toString());
    }

    @Test
    public void ImpInvoiceDetailToImpDataTest() {
//        List<PurchaseReplyInfo> list = new ArrayList<>(1);
//        PurchaseReplyInfo info = new PurchaseReplyInfo();
//        info.setPono("10143738-42");
//        info.setLineitem(1);
//        info.setSupplierid("");
//        info.setReplyorderdate(new Date());
//        info.setReplyexportdate(new Date());
//        info.setModelno("RS2H50A-30TM");
//        info.setReasonremark("测试");
//        info.setReplyorderno("10143738-42");
//        info.setErrdescription("测试");
//        list.add(info);
//        CommonResult<String> stringCommonResult = requestPurchaseFeignApi.updateInvoice(list);
//        System.out.println("stringCommonResult = " + JSONObject.toJSONString(stringCommonResult));
    }

    @Test
    public void testupdateOpsInvoicede() {
//        List<PurchaseReplyInfo> list=new ArrayList<>();
//            PurchaseReplyInfo info=new PurchaseReplyInfo();
//            info.setPono("11ABJVE-1-1");
//            info.setLineitem(1);
//            info.setTranstype("1");
//            info.setSupplierid("O");
//            info.setQtyreceive(2);
//            list.add(info);
//        CommonResult<String> result =requestPurchaseFeignApi.updateInvoice(list);
//        log.info("kais");
//        System.out.println(result);
//        System.out.println(result.getMessage());
//        System.out.println(result.getData());
//        System.out.println(result.getCode());
    }

    @Test
    public void checkImpInvoiceErrorTest() {
        //ResultVo<Integer> resultVo = impInvoiceService.checkImpInvoiceError(3641L);
        //System.out.println(resultVo);
    }

    @Test
    public void receiveGoodsTest() {
        ImpInvoiceReceiveDTO dto = new ImpInvoiceReceiveDTO();
        dto.setInvoiceNo("CT001");
        dto.setReceiveTime(new Date());
        dto.setReceiveWarehouseCode("KGZ");
        dto.setSupplierNo("JP");
        ResultVo<String> resultVo = impInvoiceService.receiveGoods(dto);
        System.out.println(resultVo);
    }

    @Test
    public void importJPShippingFileTest() {
        MultipartFile file = null;
        try {
            file = FileUtil.getMultipartFile("SHPINF.txt",
                    new FileInputStream(new File("D:\\SHPINF.TXT")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ResultVo<String> result = impInvoiceService.importJPShippingFile(file);
        log.info("importJPShippingFileTest {}", result);

    }


    @Test
    public void sendInvoiceProcessMsgToMQTest() {
        ImpInvoiceProcessDTO dto = new ImpInvoiceProcessDTO();
        dto.setInvoiceId(1111L);
        dto.setProcessType(2);
        RabbitMqMessage mqMsg = new RabbitMqMessage();
        mqMsg.setContent(JSON.toJSONString(dto));
        mqMsg.setFlag(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_INVOICE_PROSECC);
        mqMsg.setDataType(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_INVOICE);
        mqMsg.setSystem(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS);
        log.info("sendInvoiceProcessMsgToMQTest = {} ", sendMessage.sendInvoiceProcessMsg(mqMsg));
    }


    @Test
    public void UpdateDeleteDetailPack() {
        List<PoInvoiceDTO> poInvoiceDTOS = new ArrayList<>();
        PoInvoiceDTO poInvoiceDTO = new PoInvoiceDTO();
        poInvoiceDTO.setInvoiceId(Long.valueOf(22111373));
        poInvoiceDTO.setInvoiceNo("SKT4578");
        poInvoiceDTO.setFullOrderNo("10199752-15");
        poInvoiceDTOS.add(poInvoiceDTO);
        System.out.println(JSON.toJSON(poInvoiceDTOS));
//        ResultVo<String> resultVo=  impInvoiceService.UpdateDeleteDetailPack(poInvoiceDTOS, 2);
//        System.out.println(resultVo);
    }

    @Resource
    PoInvoiceService poInvoiceService;

    @Test
    public void updatePoInvoiceDetailCostTest() {
        PoInvoiceDetailVO poInvoiceDetailVO = new PoInvoiceDetailVO();
        poInvoiceDetailVO.setInvoiceId(23010194L);
        poInvoiceDetailVO.setInvoiceNo("GP2301060001");
        poInvoiceDetailVO.setOrderNo("10362101-1");
        poInvoiceDetailVO.setPrice(BigDecimal.valueOf(433.45));
        poInvoiceDetailVO.setAmount(BigDecimal.valueOf(9102.45));
        poInvoiceDetailVO.setPriceRmb(BigDecimal.valueOf(433.45));
        poInvoiceDetailVO.setAmountRmb(BigDecimal.valueOf(9102.45));
        poInvoiceDetailVO.setCustomsFee(BigDecimal.valueOf(0));
        poInvoiceDetailVO.setTransFee(BigDecimal.valueOf(20));
        poInvoiceDetailVO.setExciseTax(BigDecimal.valueOf(23));
        poInvoiceDetailVO.setOtherFee(BigDecimal.valueOf(0));
        poInvoiceDetailVO.setPriceTotal(BigDecimal.valueOf(433.45));
        poInvoiceDetailVO.setAmountTotal(BigDecimal.valueOf(9102.45));

        ResultVo<String> resultVo = poInvoiceService.updatePoInvoiceDetailCost(poInvoiceDetailVO);
        System.out.println(resultVo);
    }

    @Test
    public void getExchangeRateByinvoiceIdTest() {
        ResultVo<BigDecimal> resultVo = poInvoiceService.getExchangeRateByinvoiceId(23010194L);
        System.out.println(resultVo);
    }

    @Test
    public void listPoinvoice() {
        PoInvoiceMasterRequest request = new PoInvoiceMasterRequest();
        request.setInvoiceId(23010001L);
        ResultVo<PageInfo<OpsPoInvoiceVO>> resultVo = poInvoiceService.listPoInvoice(request);
        System.out.println(resultVo);
    }


    @Test
    public void doDataToCostTest() {

//    String userNo=SMCApp.getLoginAuthDtoForSysUser().getUserNo();


        ResultVo<String> resultVo = poInvoiceService.doDataToCost(23010003L, DateUtil.stringToDate("2023-02-02 05:00:00"));
        System.out.println(resultVo);
    }

    @Test
    public void exportImpInvoiceMasterTest() {
        try {
            ImpInvoiceMasterRequest request = new ImpInvoiceMasterRequest();
            request.setStatus(1);
            request.setInvoiceNo("22002326");
            impInvoiceService.exportImpInvoiceMaster(request);
        } catch (Exception ex) {
            System.out.println("错误");
            System.out.println(ex.getMessage());
        }
        System.out.println("测试完成");
    }

    @Test
    public void syncGZSalesinvoiceToIMPTest()
    {
       ResultVo<String> result=  impInvoiceService.syncGZSalesinvoiceToIMP("2023-7-13");
       System.out.println(result);
    }

    @Test
    public  void syncImpGZInvoicePackTest(){
        log.info(" result={}",impInvoiceService.syncImpGZInvoicePack("2023-08-28"));
    }

    @Test
    public void listStockAssemblyApplyDetailTest(){
        StockAssemblyRequest request=new StockAssemblyRequest();
        request.setApplyNo("ZH0000001192");
        request.setIsTransOut(false);//true 出，false 入
        request.setPageNum(1);
        request.setPageSize(1000);
        log.info("listStockAssemblyApplyDetail ==> {}", stockAssemblyFeignApi.listStockAssemblyApplyDetail(request).getData().getList());
    }

    @Test
    public void getAssemblyInDataForWMSTest(){
        List<InventoryForAdjustDto> dto=stockAssemblyFeignApi.getAssemblyInDataForWMS("ZH0000005815").getData();
        log.info("getAssemblyInDataForWMS ==> {}", JSONObject.toJSONString(dto));
    }
}
