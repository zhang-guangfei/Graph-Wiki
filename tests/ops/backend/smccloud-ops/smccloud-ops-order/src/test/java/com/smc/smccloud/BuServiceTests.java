package com.smc.smccloud;

import com.alibaba.fastjson.JSONObject;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.model.BuInterface.BuInvoiceResponse;
import com.smc.smccloud.model.receiveorder.CalSMCGuidingPriceEntity;
import com.smc.smccloud.model.salestask.OpsSalesCommonParamVO;
import com.smc.smccloud.service.BuService;
import com.smc.smccloud.service.ReceiveOrderService;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: B90034
 * Date: 2021-12-01 15:26
 * Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BuServiceTests extends OpsOrderApplicationTest {

    @Resource
    private BuService buService;
    @Resource
    private ReceiveOrderService receiveOrderService;
    @Resource
    private RedisManager redisManager;

//    @Resource
//    private BaseWarehouseService baseWarehouseService;

    @Test
    public void queryImportInvoiceInfoTest() {
        int pageNum = 1;
        int pageSize = 10;
        Map<String, String> param = new HashMap<>();
        param.put("plantMark", "AM");
        param.put("invNo", "YCN2542305");
        param.put("startTime", "");
        param.put("endTime", "");
        param.put("pageNum", String.valueOf(pageNum));
        param.put("pageSize", String.valueOf(pageSize));
        BuInvoiceResponse buInvoiceResponse = buService.queryImportInvoiceInfo(param);
        log.info("====> response.body = {}", buInvoiceResponse);
    }


//    @Test
//    public void querySalesOrderReply() {
//        //boolean master = baseWarehouseService.isMaster("MASTER");
//        //System.out.println("master = " + master);
//
//    }

    @Test
    public void tesComputerPrice() {
        ResultVo<CalSMCGuidingPriceEntity> getPriceEntityResultVo = receiveOrderService.calSMCGuidingPrice("CN0", "CNM",
                "ZP-B0020C29Z9-DBI00291",
                50, "S13006037");
        System.out.println("referencePriceResultVo = " + getPriceEntityResultVo.toString());
    }

    @Test
    public void updateWarehousingInfoTest() {
//        ResultVo<String> stringResultVo = productCommonService.interfaceTest();
//        System.out.println("stringResultVo = " + stringResultVo);
    }

    public static void main(String[] args) {
        /*String url = "http://10.116.1.201/api/auth/login";
        String tokenParams = "username=testsystem&password=123456";
        String s = HttpRequest.sendPost(url, tokenParams);
        System.out.println("s = " + s);*/

    }
    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public   void execCallInterfaceTest() {
        String methodName = "createPreStockApply";
        String className = "com.smc.smccloud.service.impl.SalesNotickTaskServiceImpl";
        String params = "{\"data\":{\"applyType\":\"1\",\"reason\":\"其他\",\"stockType\":\"ZL-HY\",\"applyTimeTemp\":\"2024-03-04 08:35:44\",\"applyPsn\":\"刘凯\",\"remark\":\"\\t随着IVD市场的高速增长，LVMK-KD也将迎来了国产化进程，重点客户都提出了样品测试的述求依赖，为了更高效业务处理，现统一由行业牵头进行样品订单专备申请，统一依赖业务中心进行处理，订向北京工厂。订货成功后，再统一分配，进行样品订单订购。同时行业也需要进行一些中国产地专备先行在库，保证日本产地与中国产地并行一段时间、\",\"customerBaseNo\":\"FPP02\",\"applyDeptNo\":\"235602\",\"deptNo\":\"235602\",\"warehouseCode\":\"KBJ\",\"groupCustomerNo\":\"FPP02\",\"applyNo\":\"test2402280001\",\"details\":{\"dlvDateTemp\":\"2024-03-29 00:00:00\",\"quantity\":6000,\"itemNo\":5,\"modelNo\":\"LVMK27-6J\",\"status\":\"2\"},\"customerBaseName\":\"行业群\",\"applyPsnNo\":\"B78025\"}}";

        String businessCode = "1";
        String batchNo = "Test1234555556";
        OpsSalesCommonParamVO object = JSONObject.parseObject(params, OpsSalesCommonParamVO.class);
        object.setBatchNo(batchNo);
        object.setBusinessCode(businessCode);
        Object result = null;
        try {
            Class<?> classObj = Class.forName(className);
            Object o = applicationContext.getBean(classObj);
            // 获取接口中的方法
            Method[] methods = classObj.getMethods();
            // 指定方法
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    // 调用方法
                    result = method.invoke(o, object);
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            log.error(className + "->" + methodName + "调用失败" + e.getMessage(), e);
        }

        log.info("result  =>{}",result);

    }
}
