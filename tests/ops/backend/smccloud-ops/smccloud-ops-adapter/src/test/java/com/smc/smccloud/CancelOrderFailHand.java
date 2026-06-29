package com.smc.smccloud;

import com.alibaba.fastjson.JSONObject;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.ExcelHelper;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.model.order.OrderCancelResult;
import com.smc.smccloud.service.OrderService;
import com.smc.smccloud.service.SMSOrderServiceFeignApi;
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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/9/2 15:59
 * @Descripton TODO
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CancelOrderFailHand {

    @Resource
    private SMSOrderServiceFeignApi smsOrderServiceFeignApi;
    @Resource
    private OrderService orderService;

    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }


    @Test
    public void testRe() {

        List<String> list = getOrderNoList();
        System.out.println("list = " + list.size());
//         String[] arr = new String[]{"A9674KY100"};
//         List<String> list = Arrays.asList(arr);
        int count = 0;
        int num = 1;
        for (String no :list) {
            List<OrderCancelResult> cancelResultList = new ArrayList<>(1);
            OrderCancelResult cancelResult = new OrderCancelResult();
            cancelResult.setOrderNo(no);
            cancelResult.setResult("2");
            cancelResult.setMessage("系统补推删单");
            cancelResult.setHandlePsnNo("SYSTEM");
            cancelResult.setHandlePsnName("系统处理");
            cancelResult.setHandleRemark("手动处理删单问题");
            cancelResultList.add(cancelResult);
            log.info("删单[客户单] 回调给门户队列的接口 => {} ",JSONObject.toJSONString(cancelResult));
            ResultVo<Boolean> resultVo = orderService.sendOrderCancelReturnMessage(cancelResultList);
            if (resultVo.isSuccess()) {
                count++;
                num++;
            }
            System.out.println("resultVo = " + JSONObject.toJSONString(resultVo));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("count = " + count);
    }



    public List<String> getOrderNoList() {

        List<String> list = new ArrayList<>();
        File file = new File("E:\\xxx\\test.xlsx");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ExcelHelper excelHelper = null;
        excelHelper = new ExcelHelper(fileInputStream);

        Sheet sheet = excelHelper.getSheet();
        int lastRowNum = sheet.getLastRowNum();
        Row rows;
        for (int row = 0; row <= lastRowNum; row++) {
            rows = sheet.getRow(row);
            if (rows == null) {
                break;
            }
            String cell0 = excelHelper.getCellString(rows.getCell(0));
            list.add(cell0);
        }
        return list;
    }





}
