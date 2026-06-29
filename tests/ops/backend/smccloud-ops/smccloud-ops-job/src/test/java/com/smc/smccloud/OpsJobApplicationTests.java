package com.smc.smccloud;


import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.EmailUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.model.ConnectEmailCondition;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.OrderSales.OrderSalesVO;
import com.smc.smccloud.service.*;
import com.smc.smccloud.service.handler.EmailHanlder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpsJobApplication.class)
public class OpsJobApplicationTests {

    @Autowired
    private RedisManager redisManager;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Resource
    private ReceiveOrderServiceFeignApi receiveOrderServiceFeignApi;

    @Resource
    private EmailReceiver emailAttachmentReceiver;



    @Resource
    private ExpModelServiceFeignApi expModelServiceFeignApi;

//    @Resource
//    private ClientAuthFeginConfiguration clientAuthFeginConfiguration;


    @Resource
    private  EmailHanlder emailHanlder;


    @Test
    public void contextLoads() {
//        EmailUtil.send(mailSender,"invoice@smcgz.com.cn" , "","", "tt",
//                "test");
        String redisKey = "ops:job:rcvmail:" + "20251104";
        if (redisManager.hHasKey(redisKey, "105532")) {
            System.out.println("redisKey = " + redisKey);
        } else {
            System.out.println("redisKey =  不存在" );
        }
    }


    @Test
    public void testRcvFile() {

        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        ConnectEmailCondition connectEmailCondition = new ConnectEmailCondition();
        connectEmailCondition.setHost("mail.smcgz.com.cn");
        connectEmailCondition.setPort("110");
        connectEmailCondition.setUserName("invoice");
        connectEmailCondition.setPassWord("IT#190117");
        connectEmailCondition.setSaveDirectory("E:/Attachment/");

        emailAttachmentReceiver.receiveEmails(connectEmailCondition);
    }

    @Test
    public void testC() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        ResultVo<String> resultVo = expModelServiceFeignApi.sendSalesEDiscountReport("2022-11");
        System.out.println("resultVo = " + resultVo);

    }

    @Test
    public void dateTest() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        ResultVo<String> priceFromRcvDetail = receiveOrderServiceFeignApi.calcOrderPriceSMCGroupCustomer();
        System.out.println("priceFromRcvDetail = " + priceFromRcvDetail.toString());
    }

    @Test
    public void dateCacheTest() {

        // cpfr:binimps
        Object hashKeyValue = redisManager.getHashKeyValue("ops:cpfr:binimps");
        System.out.println("hashKeyValue = " + hashKeyValue.toString());
//        Date startTime = DateUtil.stringToDate("2021-12-01");
//        log.info("init startTime = {}", startTime);
//        Object obj = redisManager.get("opsjob:param:invoice:impImportInvoiceInfo");
//        if (obj != null) {
//            startTime = DateUtil.stringToDate((String) obj);
//        }
//        log.info("after redis.get startTime = {}", startTime);

//        Date endTime = DateUtil.stringToDate("2021-12-03");
//        redisManager.set("opsjob:param:invoice:impImportInvoiceInfo", DateUtil.dateToDateString(endTime));
//
//        boolean hasKey = redisManager.hasKey("opsjob:param:invoice:impImportInvoiceInfo");
//        log.info("haskey = {}", hasKey);
//
//        obj = redisManager.get("opsjob:param:invoice:impImportInvoiceInfo");
//        log.info("after redis.set startTime = {}", obj);

        // redisManager.delete("opsjob:param:invoice:impImportInvoiceInfo");
    }


    @Test
    public void testaaa() {
//        OrderStateVO orderStateVO = new OrderStateVO();
//        SampleOrderVO sampleOrderVO = new SampleOrderVO();
//        orderStateVO.setOrderNo("123456789");
//        orderStateVO.setId((long) 123);
//        sampleOrderVO.setItemNo(123);
//        sampleOrderVO.setOrderNo("123123");
//        sampleOrderVO.setQuantity(10);
//
//        String s = autoEmailAttachmentReceive.testType(orderStateVO);
//        System.out.println("s = " + s);


    }

    @Test
    public void createCustomerOrderNo() {
        OrderSalesVO orderStateVO = new OrderSalesVO();
        orderStateVO.setId((long) 111);
        orderStateVO.setDeptNo("123");
        orderStateVO.setStatus("2");
        orderStateVO.setQuantity(10);
        OrderSalesVO orderStateVO2 = new OrderSalesVO();
        orderStateVO2.setRorderItem(10);
        orderStateVO2.setDeptNo("123");
        orderStateVO2.setStatus("2");
        orderStateVO2.setQuantity(10);


        mergeObject(orderStateVO, orderStateVO2);


        System.out.println("orderStateVO2 = " + orderStateVO2.toString());

        System.out.println("orderStateVO = " + orderStateVO.toString());

    }

    public <T> void mergeObject(T origin, T destination) {
        if (origin == null || destination == null)
            return;
        if (!origin.getClass().equals(destination.getClass()))
            return;

        Field[] fields = destination.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                Object valueD = fields[i].get(origin);
                Object valueO = fields[i].get(destination);
                if (null == valueO) {
                    fields[i].set(destination, valueD);
                }
                fields[i].setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testRe() {
        redisManager.hPut(Constants.ORDER_ESARRIVADATE, "100001", "WWWWW");
        Map<String, Object> map = new HashMap<>();
        map.put("1002", "wewewewe");
        map.put("1003", "aeeeee");
        redisManager.hPutAll(Constants.ORDER_ESARRIVADATE, map);

        boolean b = redisManager.hHasKey(Constants.ORDER_ESARRIVADATE, "1002");
        System.out.println("b = " + b);

        Object o = redisManager.hGet(Constants.ORDER_ESARRIVADATE, "1002");
        System.out.println("o = " + o.toString());
        List<Object> list = new ArrayList<>();
        list.add("1002");
        list.add("1003");
        List<Object> objects = redisManager.hMultiGet(Constants.ORDER_ESARRIVADATE, list);
        System.out.println("objects = " + objects.toString());

    }

    @Test
    public void testAuth() {
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "2");
        if (PublicUtil.isEmpty(dataTypeCodesInfo.getData())) {
            log.error("【autoRcvOrder】 自动接入订单失败(未获取到执行开关)");
            return;
        }
        if (dataTypeCodesInfo.getData().getExtNote1().equals("1")) {
            ResultVo<String> stringResultVo = receiveOrderServiceFeignApi.autoRcvOrderFeign();
            if (!stringResultVo.isSuccess() || PublicUtil.isEmpty(stringResultVo.getData())) {
                log.error("【【autoRcvOrder】 自动接入订单执行失败 ->{}", stringResultVo.getMessage());
            }
        }
    }

    @Test
    public void parseEmailTest() throws Exception {
        emailHanlder.parseEmailFile();
    }

    @Test
    public void redisOrderNoTransferFromSetToListTest() {
        // bug-8874 将Redis中Set数据类型缓存的预生成订单号改为List数据类型缓存
        // 获取已生成单号
        Set<Object> orderNoSet = redisManager.members(Constants.ORDERSALES_CUSTOMER_ORDERNO);
        // 排序 + 转换数组类型
        Object[] orderNos = orderNoSet.stream().sorted().toArray();
        System.out.println("orderNoSet size = " + orderNoSet.size());
        System.out.println("orderNos length = " + orderNos.length);
        System.out.println("orderNos = " + Arrays.deepToString(orderNos));
        // 迁移至List类型数据结构
        redisManager.lRightPush(Constants.SALES_ORDER_NO, orderNos);
        System.out.println("迁移订单号完成");
    }

}
