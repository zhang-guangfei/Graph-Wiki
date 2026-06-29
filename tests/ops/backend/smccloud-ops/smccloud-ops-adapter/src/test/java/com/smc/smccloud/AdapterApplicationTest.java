package com.smc.smccloud;

import com.alibaba.fastjson.JSON;
import com.smc.smccloud.config.AdapterRabbitMqRedis;
import com.smc.smccloud.config.AdapterRedisManager;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.ExcelHelper;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.model.adapter.AdapterResult;
import com.smc.smccloud.model.adapter.order.*;
import com.smc.smccloud.model.adapter.stock.*;
import com.smc.smccloud.model.order.OrderCancelResult;
import com.smc.smccloud.model.ordermodify.OrderModifyVO;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.OrderService;
import com.smc.smccloud.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * @Author lyc
 * @Date 2022/3/22 18:29
 * @Descripton TODO
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AdapterApplicationTest {

    @Resource(name = "adapterRedisManager")
    private AdapterRedisManager redisManager;

    @Resource
    private OrderService orderService;
    @Resource
    private StockService stockService;
    @Resource
    private SendMessage sendMessage;
    @Resource
    public AdapterRabbitMqRedis adapterRabbitMqRedis;

    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    @Test
    public void redisTest() {
//        boolean hasKey = redisManager.hasKey("sale:rabbitmq:customerInStock:41587639");
////        System.out.println("hasKey = " + hasKey);
////
////        Object value = redisManager.get("sale:rabbitmq:customerInStock:41587639");
////        System.out.println("value = " + value.toString());

        // redisManager.set("sale:rabbitmq:message:order:" + "S000394", "test",60);
        Object o = redisManager.get("sale:rabbitmq:message:order:" + "S000393-7");
        System.out.println("o = " + o.toString());

    }

    @Test
    public void orderCreateTest() {
//        String content = "";
//        List<AdapterOrder> adapterOrders = JSON.parseArray(content, AdapterOrder.class);
//        System.out.println("==> orderCreateTest result = " + orderService.handleOrderCreateMQ(adapterOrders));
    }

    @Test
    public void sendMesssageTest() {
        String msg = "[{\"itemId\":1,\"message\":\"处理成功\",\"modelNo\":\"KQ2H06-08A\",\"no\":\"GTD2204070001\",\"result\":true}]";
        RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(msg,
                Constants.ERP_CUSTOMERINSTOCKTRANSFER_CREATE_RETURN,
                Constants.CUSTOMERINSTOCKTRANSFER,
                Constants.SMS);
        sendMessage.opsToSmsYyqb(rabbitMqMessageOrder);
    }

    @Test
    public void handleOrderCreateMsgTest() {
        String content = "[{\"addressInfo\":{\"carrier\":\"顺丰特快\",\"contactPerson\":\"苏艳坤\",\"contactPhone\":\"13693529909\",\"customerName\":\"北京厚智技术发展有限责任公司\",\"customerNo\":\"95026\",\"deptNo\":\"223115\",\"dlvAddress\":\"河北省保定市涿州市北二环桥西金源钢业西边\",\"dlvFlag\":\"1\",\"employeeNo\":\"ACC000009\",\"intensiveNo\":\"0\"}," +
                "\"contractno\":\"BJD2204250002\",\"cproductname\":\"接头\",\"customerno\":\"95026\",\"deptno\":\"223115\",\"discount\":0.125,\"dlvdate\":\"2022-05-21\",\"dlventire\":\"0\",\"dlvsite\":\"1\",\"dlvtype\":\"0\",\"empoffice\":\"ACC000009\",\"empsale\":\"C01573\",\"eprice\":10.00,\"hddno\":\"HDD2205110002\",\"modelno\":\"KQ2H04-02AS\",\"no\":\"FZD2205110211\",\"price\":12.71,\"quantity\":5,\"quotationno\":\"BJD2204250002\",\"rcvclassify\":\"1\",\"remark\":\"\",\"specmark\":\"0\",\"tradecompanyid\":\"CN0-B\",\"transfee\":\"0\",\"typecode\":\"0\",\"useWarehouseType\":2,\"userno\":\"C1KCS\",\"warehousesenddate\":\"2022-05-16\",\"workday\":1652237130670}]";

        List<AdapterOrder> adapterOrders = JSON.parseArray(content, AdapterOrder.class);
        String hddnoPrefix = "THDD220518";
        String noPrefix = "TFZD220518";
        String hddno; // 合同订单号
        String no; // 发注单号
        int hddnoNum = 0;
        int noNum;
        Date now = new Date();
        String remark = "测试接入门户订单";
        RabbitMqMessage rabbitMqMessageOrder;

        // 模拟门户接单消息推送到接单消息队列
        int itemSize = 1005;
        List<AdapterOrder> orderList = new ArrayList<>(itemSize);
        AdapterOrder orderInfo;
        hddno = "THDD2205180002";
        for (int i = 1; i < 1006; i++) {
            orderInfo = BeanCopyUtil.copy(adapterOrders.get(0), AdapterOrder.class);
            orderInfo.setHddno(hddno);
            orderInfo.setNo(handleNo(noPrefix, i));
            orderList.add(orderInfo);
        }
        rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(orderList),
                Constants.ERP_ORDER_CREATE,
                Constants.ORDER,
                Constants.SMS);
        sendMessage.smsToOpsOrderCreate(rabbitMqMessageOrder);
        /*for (int i = 0; i < 1; i++) {
            hddnoNum++;
            noNum = 0;
            hddno = handleNo(hddnoPrefix, hddnoNum); // 生成合同订单号

            for (AdapterOrder info : adapterOrders) {
                noNum++;
                no = handleNo(noPrefix, noNum); // 生成发注单号

                info.setWorkday(now);
                info.setHddno(hddno);
                info.setNo(no);
                info.setRemark(remark);
            }

            rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(adapterOrders),
                    Constants.ERP_ORDER_CREATE,
                    Constants.ORDER,
                    Constants.SMS);
           sendMessage.smsToOpsOrderCreate(rabbitMqMessageOrder);
        }*/
    }

    private String handleNo(String prefix, int no) {
        int numLength = 4;
        StringBuilder num = new StringBuilder(String.valueOf(no));
        String zero = "0";
        while (num.length() < numLength) {
            num.insert(0, zero);
        }
        //String dateFomat = "yyMMdd";
        //return prefix + DateUtil.getFormatDate(new Date(), dateFomat) + num;
        return prefix + num;
    }

    @Test
    public void handleOrderCreateMQTest() {
//        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
//        rabbitMqMessage.setRandomNumber("64591208");
//        rabbitMqMessage.setDataType("sale:rabbitmq:order:");
//        String content = "";
//        List<AdapterOrder> adapterOrders = JSON.parseArray(content, AdapterOrder.class);
//
//        /*
//         * 调用接单处理接口
//         */
//        List<AdapterOrderResult> orderReturns = orderService.handleOrderCreateMQ(adapterOrders);
//
//        adapterRabbitMqRedis.consumer(rabbitMqMessage);
//
//        RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(orderReturns),
//                Constants.ERP_ORDER_CREATE_RETURN,
//                Constants.ORDER,
//                Constants.SMS);
//        sendMessage.opsToSmsOrderCreate(rabbitMqMessageOrder);
//        log.info("订单接单-消息消费成功: {} --result: {}", rabbitMqMessage.getRandomNumber(), orderReturns);
    }

    @Test
    public void handleOrderCancelMQTest() {
        List<OrderModifyVO> list=orderService.listOrderModify();
        for(OrderModifyVO vo:list){
            String content = "";
            OrderCancelDTO info = new OrderCancelDTO();
            info.setApplyPersonNo(vo.getApplyPersonNo());
            info.setApplyReason(vo.getReason());
            info.setApplyTime(vo.getApplyTime());
            List<OrderCancelItem> items = new ArrayList<>();
            OrderCancelItem item = new OrderCancelItem();
            if(vo.getOrderNo().contains("-")){
                int lastIndex = vo.getOrderNo().lastIndexOf("-");
                String itemNo = vo.getOrderNo().substring(lastIndex + 1, vo.getOrderNo().length());
                item.setItemNo(itemNo);
            }else if(vo.getOrderNo().length() == 10){
                item.setItemNo(String.valueOf(Integer.parseInt(vo.getOrderNo().substring(7, 10))));
            }else {
                item.setItemNo("1");
            }

            item.setNo(vo.getApplyNo());
            item.setOrderNo(vo.getOrderNo());
            items.add(item);

            info.setItems(items);
            System.out.println("jieotoe");
            System.out.println(info);
            /*
             * 调用取消订单接口
             */
            List<OrderCancelResult> result = orderService.handleOrderCancelMQ(info);
            log.info("handleOrderCancelMQ result = {}", result);

//        adapterRabbitMqRedis.consumer(rabbitMqMessage);

            RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(result),
                    Constants.ERP_ORDER_CANCEL_RETURN,
                    Constants.ORDER,
                    Constants.SMS);
            sendMessage.opsToSmsOrderCancel(rabbitMqMessageOrder);
        }

        //log.info("订单取消-消息消费成功: " + rabbitMqMessage.getContent() + " --result: " + result);
    }


    @Test
    public void handlePreOccupiedInventoryCreateMQTest() {
        String content = "{\"applyDate\":\"2022-10-27 11:23:43\",\"customerName\":\"大连连硕泵阀有限公司\",\"customerNo\":\"C25C7\",\"deptNo\":\"223220\",\"hopeOrderDate\":\"2022-10-28 00:00:00\",\"id\":\"YZ2210270072\",\"items\":[{\"applyQuantity\":24,\"auditQuantity\":24,\"deliveryDate\":\"2022-10-31 00:00:00\",\"expiryDate\":1668096000000,\"id\":\"YZ2210270072\",\"itemId\":1,\"itemState\":\"3\",\"modelNo\":\"VFA5220-03-P\",\"stockCode\":\"KBJ\"}],\"makeEmployeeNo\":\"990006\",\"saleEmployeeNo\":\"B80925\",\"state\":\"1\",\"userNo\":\"990006\"}";
        PreSaleInventory info = JSON.parseObject(content, PreSaleInventory.class);
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setRandomNumber("45679238");
        rabbitMqMessage.setContent(content);
        rabbitMqMessage.setDataType("sale:rabbitmq:preOccupiedInventory:");
        /*
         * 调用取消订单接口
         */
        //List<AdapterResult> resultList = stockService.handlePreOccupiedInventoryCreateMQ(info);
        //log.info("handlePreOccupiedInventoryCreateMQ result = {}", result);
        //adapterRabbitMqRedis.consumer(rabbitMqMessage);

        List<AdapterResult> resultList = new ArrayList<>(info.getItems().size());
        AdapterResult result;
        for (PreSaleInventoryItem item : info.getItems()) {
            result = new AdapterResult();
            result.setNo(item.getId());
            result.setItemNo(item.getItemId());
            result.setModelNo(item.getModelNo());
            result.setResult(true);
            result.setMessage("情报预约成功");
            resultList.add(result);
        }

        /*
         * 返回结果
         */
        RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(resultList),
                Constants.ERP_PREOCCUPIEDINVENTORY_CREATE_RETURN,
                Constants.PREOCCUPIEDINVENTORY,
                Constants.SMS);
        sendMessage.opsToSmsYyqb(rabbitMqMessageOrder);
        log.info("预占库存消费成功：" + rabbitMqMessage.getContent() + "--result:" + resultList);
    }

    @Test
    public void handlePreOccupiedInventoryCancelMQTest() {
       // 74582690
        String content = "{\"id\":\"YZ2210080030\",\"items\":[{\"applyQuantity\":120,\"auditQuantity\":120,\"deliveryDate\":\"2022-10-23 00:00:00\",\"id\":\"YZ2210080030\",\"itemId\":1,\"itemState\":\"3\",\"modelNo\":\"KQB2H12-04S-X1114\",\"stockCode\":\"KBJ\"}],\"makeEmployeeNo\":\"ACC000835\",\"operateType\":\"删除\",\"saleEmployeeNo\":\"C15635\",\"state\":\"1\",\"userNo\":\"ACC000835\"}";

        PreSaleInventory info = JSON.parseObject(content, PreSaleInventory.class);
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setRandomNumber("74582690");
        rabbitMqMessage.setContent(content);
        rabbitMqMessage.setDataType("sale:rabbitmq:preOccupiedInventory:");

        AdapterResult result = new AdapterResult();
        result.setResult(true);
        result.setNo(info.getId());
        result.setOperateType(info.getOperateType());
        Map<Integer, String> modelItem = new HashMap<>(info.getItems().size());
        Map<Integer, String> statusMap = new HashMap<>(info.getItems().size());
        result.setItemModelList(modelItem);
        result.setItemListStatus(statusMap);

        for (PreSaleInventoryItem item : info.getItems()) {
            statusMap.put(item.getItemId(), Boolean.TRUE.toString());
            modelItem.put(item.getItemId(), item.getModelNo());
            result.setResult(true);
            result.setMessage("取消成功");
        }

        /*
         * 返回结果
         */
        RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(result),
                Constants.ERP_PREOCCUPIEDINVENTORY_DELETE_RETURN,
                Constants.PREOCCUPIEDINVENTORY,
                Constants.SMS);
        sendMessage.opsToSmsYyqb(rabbitMqMessageOrder);
        log.info("预占库存删除-消息消费成功: {} {} --result: {}", rabbitMqMessage.getRandomNumber(), info.getId(), result);
    }

    @Test
    public void handleSubTreasuryCreateMQTest() {
        String content = "";
        ChinaRegionWarehouseSupplyApply apply = JSON.parseObject(content, ChinaRegionWarehouseSupplyApply.class);
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setRandomNumber("94582716");
        rabbitMqMessage.setDataType("sale:rabbitmq:subTreasury:");

        AdapterResult result = stockService.handleSubTreasuryCreateMQ(apply);
        adapterRabbitMqRedis.consumer(rabbitMqMessage);

        RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(result),
                Constants.ERP_SUBTREASURY_CREATE_RETURN,
                Constants.SUBTREASURY,
                Constants.SMS);
        sendMessage.opsToSmsYyqb(rabbitMqMessageOrder);
    }

    @Test
    public void handleCustomersInStockCreateMQTest() {
        String content = "{\"applyDate\":\"2022-09-13 10:51:54\",\"applyType\":0,\"autoSupply\":false,\"customerNo\":\"A3497\",\"deptNo\":\"243652\",\"id\":\"ZK2209130004\"," +
                "\"items\":[{\"applyQuantity\":50,\"assemble\":\"正常\",\"auditQuantity\":50,\"automaticInventoryDetail\":false,\"binDataInK30\":0,\"binDataInK90\":0,\"customerInOrderingQuantity\":0,\"customerStockQuantity\":0,\"ePrice\":4367.90,\"hisSaleFrequency\":0,\"hisSaleQuantity\":0,\"hopeDeliveryDate\":1664899200000,\"id\":\"ZK2209130004\",\"itemId\":1,\"modelNo\":\"XLD-40-M9//-XF1A\",\"rohs10\":false,\"stockQuantity\":0,\"transType\":\"空运\"}," +
                "{\"applyQuantity\":50,\"assemble\":\"正常\",\"auditQuantity\":50,\"automaticInventoryDetail\":false,\"binDataInK30\":0,\"binDataInK90\":0,\"customerInOrderingQuantity\":0,\"customerStockQuantity\":0,\"ePrice\":4367.90,\"hisSaleFrequency\":0,\"hisSaleQuantity\":0,\"hopeDeliveryDate\":1667145600000,\"id\":\"ZK2209130004\",\"itemId\":2,\"modelNo\":\"XLD-40-M9//-XF1A\",\"rohs10\":false,\"stockQuantity\":0,\"transType\":\"空运\"}," +
                "{\"applyQuantity\":200,\"assemble\":\"正常\",\"auditQuantity\":200,\"automaticInventoryDetail\":false,\"binDataInK30\":0,\"binDataInK90\":0,\"customerInOrderingQuantity\":0,\"customerStockQuantity\":0,\"ePrice\":4367.90,\"hisSaleFrequency\":0,\"hisSaleQuantity\":0,\"hopeDeliveryDate\":1671724800000,\"id\":\"ZK2209130004\",\"itemId\":3,\"modelNo\":\"XLD-40-M9//-XF1A\",\"rohs10\":false,\"stockQuantity\":0,\"transType\":\"空运\"}]," +
                "\"makeEmployeeName\":\"杨大利\",\"makeEmployeeNo\":\"A80226\",\"remark\":\"原先行在库订单99652M3025，XLD-40-M9//，300个，因货期问题，日本制造建议更换成型号XLD-40-M9//-XF1A，并按50个9月21日/50个10月17日/200个12月9日设置日本入库日。\",\"saleEmployeeNo\":\"A80225\",\"status\":\"已通过\"}";
        BinDataApply apply = JSON.parseObject(content, BinDataApply.class);
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setRandomNumber("20614753");
        rabbitMqMessage.setDataType("sale:rabbitmq:customerInStock:");

        AdapterResult result = stockService.handleCustomersInStockCreateMQ(apply);
        adapterRabbitMqRedis.consumer(rabbitMqMessage);

        RabbitMqMessage rabbitMqMessageBinData = new RabbitMqMessage(JSON.toJSONString(result),
                Constants.ERP_CUSTOMERINSTOCK_CREATE_RETURN,
                Constants.CUSTOMERINSTOCK,
                Constants.SMS);
        sendMessage.opsToSmsYyqb(rabbitMqMessageBinData);
    }

    @Test
    public void OrderEditReceiveTest() {
        String content = "";
        OrderDeliveryModel orderDeliveryModel = JSON.parseObject(content, OrderDeliveryModel.class);
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setRandomNumber("72143850");
        rabbitMqMessage.setDataType("sale:rabbitmq:order:");

        AdapterReturn result = orderService.handleOrderEditMQ(orderDeliveryModel);
        adapterRabbitMqRedis.consumer(rabbitMqMessage);

        RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(result),
                Constants.ERP_ORDER_EDIT_RERURN,
                Constants.ORDER,
                Constants.SMS);
        sendMessage.opsToSmsOrderEdit(rabbitMqMessageOrder);
    }

    @Test
    public void handleCustomerInStockTransferCreateMQTest() {
        String content = "{\"customerNo\":\"TR018\",\"deptNo\":\"261246\",\"employeeNo\":\"ACC000176\",\"items\":[{\"customerNoTrans\":\"A4960\",\"itemId\":1,\"modelNo\":\"ZP3-T16BN-B5\",\"quantity\":3,\"stockTrans\":\"243320-KSH\"}],\"no\":\"GTD2210280273\"}";
        BinTransferInfo info = JSON.parseObject(content, BinTransferInfo.class);
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setRandomNumber("65918073");
        rabbitMqMessage.setDataType("sale:rabbitmq:customerInStockTransfer:");
        /*
         * 返回结果
         */
        //List<BinTransferReturn> resultList = stockService.handleCustomerInStockTransferCreateMQ(info);
        //adapterRabbitMqRedis.consumer(rabbitMqMessage);

        List<BinTransferReturn> resultList = new ArrayList<>();
        BinTransferReturn result;
        for (BinTransferInfoItem item : info.getItems()) {
            result = new BinTransferReturn();
            resultList.add(result);
            result.setNo(info.getNo());
            result.setItemId(item.getItemId());
            result.setModelNo(item.getModelNo());
            result.setResult(true);
            result.setMessage("处理成功");
        }

        /*
         * 顾客在库调库处理结果：将处理结果发送至顾客在库调库接收结果队列 返回信息如下：
         * 顾客在库调库信息：顾客在库调库号，处理状态：success(true、false)) 过滤标记：x-flag =
         * 'erp-customerInStockTransfer-create-return'
         */
        RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(resultList),
                Constants.ERP_CUSTOMERINSTOCKTRANSFER_CREATE_RETURN,
                Constants.CUSTOMERINSTOCKTRANSFER,
                Constants.SMS);
        sendMessage.opsToSmsYyqb(rabbitMqMessageOrder);
        log.info("顾客在库调库-消息消费成功: {} --result: {}", rabbitMqMessage.getRandomNumber(), resultList);
    }

    @Test
    public void handleCustomerInStockTransferCreateMQTest2() {
        File file = new File("D:\\test\\调库消费失败单号.xlsx");

        try (InputStream inputStream = new FileInputStream(file)){
            ExcelHelper excel = new ExcelHelper(inputStream);
            Sheet sheet = excel.getSheet();
            int lastRowNum = sheet.getLastRowNum();
            Row row;
            String applyNo;
            String randomNumber;
            String content;

            for (int i = 1; i <= lastRowNum; i++) {
                row  = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                applyNo = excel.getCellString(row.getCell(0)).trim();
                randomNumber = excel.getCellString(row.getCell(3)).trim();
                content = excel.getCellString(row.getCell(4)).trim();
                log.info("====> applyNo = {}, randomNumber = {}, content = {}", applyNo, randomNumber, content);

                BinTransferInfo info = JSON.parseObject(content, BinTransferInfo.class);
                RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
                rabbitMqMessage.setRandomNumber(randomNumber);
                rabbitMqMessage.setDataType("sale:rabbitmq:customerInStockTransfer:");
                /*
                 * 返回结果
                 */
                //List<BinTransferReturn> resultList = stockService.handleCustomerInStockTransferCreateMQ(info);
                //adapterRabbitMqRedis.consumer(rabbitMqMessage);

                List<BinTransferReturn> resultList = new ArrayList<>();
                BinTransferReturn result;
                for (BinTransferInfoItem item : info.getItems()) {
                    result = new BinTransferReturn();
                    resultList.add(result);
                    result.setNo(info.getNo());
                    result.setItemId(item.getItemId());
                    result.setModelNo(item.getModelNo());
                    result.setResult(true);
                    result.setMessage("调库成功");
                }

                /*
                 * 顾客在库调库处理结果：将处理结果发送至顾客在库调库接收结果队列 返回信息如下：
                 * 顾客在库调库信息：顾客在库调库号，处理状态：success(true、false)) 过滤标记：x-flag =
                 * 'erp-customerInStockTransfer-create-return'
                 */
                RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(resultList),
                        Constants.ERP_CUSTOMERINSTOCKTRANSFER_CREATE_RETURN,
                        Constants.CUSTOMERINSTOCKTRANSFER,
                        Constants.SMS);
                sendMessage.opsToSmsYyqb(rabbitMqMessageOrder);
                log.info("顾客在库调库-消息消费成功: {} --result: {}", rabbitMqMessage.getRandomNumber(), resultList);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void handleOrderCancelMQToOPSTest() {
        List<OrderCancelResult> result = new ArrayList<>();
        OrderCancelResult vo = new OrderCancelResult();
//        vo.setNo("HDD2208312995");
//        vo.setOrderNo("10051155-9");
        vo.setNo("HDD2208261769");
        vo.setOrderNo("10046182-10");
        vo.setResult("1");
        vo.setMessage("申请失败，稍后再申请！");
        result.add(vo);
        RabbitMqMessage rabbitMqMessageOrder = new RabbitMqMessage(JSON.toJSONString(result),
                Constants.ERP_ORDER_CANCEL_RETURN,
                Constants.ORDER,
                Constants.SMS);
        sendMessage.opsToSmsOrderCancel(rabbitMqMessageOrder);
        //log.info("订单取消-消息消费成功: " + rabbitMqMessage.getContent() + " --result: " + result);
    }
}
