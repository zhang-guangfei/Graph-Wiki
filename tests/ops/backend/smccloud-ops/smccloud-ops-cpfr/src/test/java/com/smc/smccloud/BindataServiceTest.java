package com.smc.smccloud;

import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.BindataMapper;
import com.smc.smccloud.model.bindata.BindataDO;
import com.smc.smccloud.service.BinOrderCalcService;
import com.smc.smccloud.service.BinTrialJobManageService;
import com.smc.smccloud.service.BindataService;
import com.smc.smccloud.service.CommonServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author edp02 @Date 2021/10/6 15:27
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BindataServiceTest {

    @Resource
    private BindataService bindataService;

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private BinOrderCalcService binOrderCalcService;

    @Resource
    BindataMapper bindataMapper;


    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    @Test
    public void listAgentReportForCNTest() {
//        BindataRequest request=new BindataRequest();
//        request.setStockType(1);
        String billNo="030";
        log.info("list="+Integer.parseInt(billNo));
//        List<BindataDTO> list = bindataService.listBindata(request);
//        log.info("list="+list);
    }

    @Test
    public void saveBindataTest() {
        BindataDO info=new BindataDO();
        info.setStockType(1);
        info.setModelNo("10-AR50-06-C");
        info.setCustomerNo("GZ000");
        info.setProdType("0");
        info.setQtyBin(56);
        info.setBinCell(38);
        info.setWarehouseCode("KGP");
        info.setCreateTime(new Date());
        info.setUpdateTime(new Date());
//        ResultVo<String> result = bindataService.saveBindata(info);
//        log.info("result="+result);
    }
    @Test
    public void exportModelFreqTest()
    {
        binOrderCalcService.getInventoryInfomap("CP96SDB32-160C");
    }

    @Test
    public void getBindataByModelNoTest()
    {

    }

    @Test
    public void listBinInfoByModelNoTest() {
        log.info("result = {}", bindataService.listBinInfoByModelNo("AN10-01"));
    }


//    @Test
//    public void createTransNoTest() {
//        System.out.println("billNO = 17, no = " + commonServiceFeignApi.generatorBillNo("17"));
//    }

    @Resource
    private BinTrialJobManageService binTrialJobManager;
    @Test
    public void runBinTrialJobTest(){
        binTrialJobManager.runBinTrialJob();
    }

    @Test
    public  void deleteBindata()
    {
       // bindataMapper.deleteBindata();
    }
}
