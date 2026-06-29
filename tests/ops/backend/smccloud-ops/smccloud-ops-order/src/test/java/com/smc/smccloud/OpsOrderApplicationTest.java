package com.smc.smccloud;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.enums.OrderStateStatusEnum;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.OrderSales.OrderSalesDO;
import com.smc.smccloud.model.VSalesManuorder.OPSVRequisitionStausToSalesDO;
import com.smc.smccloud.model.order.OpsOrderModidataDO;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.orderstate.OrderStateDO;
import com.smc.smccloud.model.orderstate.OrderStateEnum;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.model.orderstate.OrderSupplierReplyState;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OpsOrderApplicationTest {

    @Resource
    private SendMessage sendMessage;
    @Resource
    private OrderStateService orderStateService;
    @Resource
    private RedisManager redisManager;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Resource
    private OrderSalesMapper orderSalesMapper;
    @Resource
    private OpsOrderModidataMapper opsOrderModidataMapper;
    @Resource
    private OrderStateMapper orderStateMapper;
    @Resource
    private OrderSalesService orderSalesService;
    @Resource
    private CommonService commonService;

    @Resource
    private OPSVRequisitionStausToSalesMapper opsvRequisitionStausToSalesMapper;



    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    @Test
    public void feignTest() {

        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "6");
        DataTypeVO data = dataTypeCodesInfo.getData();
        String extNote1 = data.getExtNote1();
        System.out.println("extNote1 = " + extNote1);
        String[] s = extNote1.split(" ");
        System.out.println("s = " + s[0]);
        System.out.println("s11 = " + s[1]);
    }

    @Test
    public void sendMQMsg() {
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setFlag("orderlog");
        rabbitMqMessage.setSystem("opsTest");
        rabbitMqMessage.setDataType("1");
        OrderLogVO orderLogVO = new OrderLogVO();
        for (int i = 0; i < 10; i++) {
            orderLogVO.setOrderNo("xx" + (i + 1));
            orderLogVO.setDescription("测试mq==>" + (i + 1));
            String s = JSONObject.toJSONString(orderLogVO);
            rabbitMqMessage.setContent(s);
            sendMessage.sendOrderLogMsg(rabbitMqMessage);
        }
    }

    @Test
    public void testFindLogs() {
//        Object customer = redisManager.hGet(Constants.REDIS_ALL_CUSTOMER_INFO, "C08BF");
////        System.out.println("customer = " + customer.toString());
////        CustomerVO customerVO = JSONObject.parseObject(JSONObject.toJSONString(customer), CustomerVO.class);
////        System.out.println("customerVO = " + customerVO.toString());
//        Object k1 = redisManager.get("k1");
//        System.out.println("k1 = " + k1.toString());
        OrderSupplierReplyState info =new  OrderSupplierReplyState();
        info.setOrderNo("10437700-1");
        info.setCorderNo("CN11041010");
        info.setStateCode(2);
        info.setDlvDate(DateUtil.stringToDate("2022-08-05"));
        info.setStateDes("工单状态:已集约,预计完工日期2022-08-05,原因:材料到货");
        OrderStateVO orderState = new OrderStateVO();

    }

    @Test
    public void testRe() {
//        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
//        ResultVo<DepartmentVO> departmentInfo = commonServiceFeignApi.getDepartmentInfo(String.valueOf(261420));
//        System.out.println("departmentInfo = " + departmentInfo.toString());
        OrderSupplierReplyState info =new  OrderSupplierReplyState();
        info.setOrderNo("10437700-1");
        info.setCorderNo("CN11041010");
        info.setStateCode(2);
        info.setDlvDate(DateUtil.stringToDate("2022-08-05"));
        info.setStateDes("工单状态:已集约,预计完工日期2022-08-05,原因:材料到货");

        ResultVo<String> resultVo = orderStateService.supplierReplyOrderState(info);
        System.out.println("resultVo = " + resultVo.toString());

    }

    @Test
    public void sendOrderStateMsgTest() {
        List<OpsOrderModidataDO> opsOrderModidataDOS = opsOrderModidataMapper.listData();
        for (OpsOrderModidataDO item : opsOrderModidataDOS) {
            OrderStateVO o = new OrderStateVO();
            o.setStateCode(Integer.parseInt(OrderStateStatusEnum.YSDDQR.getCode()));
            o.setOrderNo(item.getRorderFno());
            o.setItemNo(item.getOrderItem());
            o.setRorderNo(item.getOrderId());
            if (item.getDutyName() != null) {
                o.setStateDes(item.getDutyName()+"删单:"+item.getRemark());
            } else {
                o.setStateDes("删单:"+item.getRemark());
            }
            o.setStateDate(new Date());

            LambdaQueryWrapper<OrderStateDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OrderStateDO::getOrderNo,o.getOrderNo());
            OrderStateDO orderStateDO = orderStateMapper.selectOne(queryWrapper);
            System.out.println("orderStateDO = " + orderStateDO.toString());
            if (orderStateDO.getStateCode() != 90) {
                ResultVo<String> resultVo = orderStateService.addOrderState(o);
                System.out.println("resultVo = " + resultVo.toString());
            }
        }
    }

    @Test
    public void HLcodeTest() {
        QueryWrapper<OrderSalesDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(" distinct DeptNo ");
        List<OrderSalesDO> list = orderSalesMapper.selectList(queryWrapper);

        LambdaUpdateWrapper<OrderSalesDO> updateWrapper;
        ResultVo<String> resultVo;
        String deptNo;

        for (OrderSalesDO data : list) {
            if (data == null || StringUtils.isBlank(data.getDeptNo())) {
                continue;
            }
            resultVo = commonServiceFeignApi.getDeptNoByHRSalesDeptNo(data.getDeptNo());
            if (!resultVo.isSuccess()) {
                log.error("HLcodeTest error : {}", resultVo);
                continue;
            }
            deptNo = resultVo.getData();
            String HLcode = null;
            if (!deptNo.equals(data.getDeptNo())) {
                HLcode = data.getDeptNo();
            }
            updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(OrderSalesDO::getDeptNo, deptNo)
                    .set(OrderSalesDO::getHLCode, HLcode);
            updateWrapper.eq(OrderSalesDO::getDeptNo, data.getDeptNo());
            orderSalesMapper.update(null, updateWrapper);
        }
    }

    @Test
    public void HLcodeTest2() {
        String deptNameByNo = commonService.getDeptNameByNo("233220");
        System.out.println("deptNameByNo = " + deptNameByNo);

    }


    @Test
    public void importCNMOPSVRequisitionStatusToSales() {

        List<OPSVRequisitionStausToSalesDO> stausToSalesDOList = opsvRequisitionStausToSalesMapper.findReqStausToSalesListByOrderNo("10010214-30");
        if (stausToSalesDOList.isEmpty()) {
            return;
        }
        ResultVo<String> resultVo;
        for (OPSVRequisitionStausToSalesDO item : stausToSalesDOList) {
            OrderStateVO orderStateVO = convertToOrderState(item);
            orderStateVO.setStateCode(OrderStateEnum.SupplierRcvOrder.getCode());
            if (item.getEsDlvDate() != null) {
                orderStateVO.setPoReplyDate(item.getEsDlvDate());
                orderStateVO.setStateDes("供应商已接单,工厂纳期: "+DateUtil.dateToDateString(item.getEsDlvDate()));
            }

            /**
             * 发票号 != null       ==>  已发货
             * 装箱日期 != null     ==>  货齐
             * 开始生产日期 != null  ==> 生产中
             * 接单日期 != null     ==> 接入订单
             */
            String factoryName = "";
            if (PublicUtil.isNotEmpty(item.getOrderType()) && item.getOrderType().length() > 4) {
                factoryName = item.getOrderType().substring(0, 4);
            }
            orderStateVO.setStateDes(factoryName + item.getStaus() + DateUtil.dateToDateString(item.getOrderDate()));
            if (org.apache.commons.lang3.StringUtils.isNotBlank(item.getInvoiceNo())) {
                orderStateVO.setStateCode(OrderStateEnum.SupplierShipped.code());
                orderStateVO.setStateDes(factoryName + " 已发货" + item.getInvoiceNo());
            } else if (item.getPackDate() != null) {
                orderStateVO.setStateCode(OrderStateEnum.GoodsReady.code());
                orderStateVO.setStateDes(factoryName + DateUtil.dateToDateString(item.getPackDate()) + "已装箱");
            } else if (item.getStartProductionDate() != null) {
                orderStateVO.setStateCode(OrderStateEnum.SupplierInProd.code());
                orderStateVO.setStateDes(factoryName + DateUtil.dateToDateString(item.getStartProductionDate()) + "生产中");
            }

            resultVo = addOrderState(orderStateVO);
        }

        System.out.println("导入中国制造订单状态完毕,共计读取 : " + stausToSalesDOList.size());
    }


    public OrderStateVO convertToOrderState(OPSVRequisitionStausToSalesDO item) {

        if (org.apache.commons.lang3.StringUtils.isBlank(item.getOrderNo())) {
            return null;
        }

        OrderStateVO orderStateVO = new OrderStateVO();
        OrderNoInfo orderNoInfo;
        if (item.getOrderNo().contains("-")) {
            orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo());
        } else {
            orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo()+item.getItemNo());
        }

        orderStateVO.setOrderNo(orderNoInfo.getFullOrderNo());
        orderStateVO.setPoHolon(item.getPoHolon());
        orderStateVO.setRorderNo(orderNoInfo.getOrderNo());
        orderStateVO.setItemNo(orderNoInfo.getItemNo());
        orderStateVO.setSplitNo(orderNoInfo.getSplitItem());
        orderStateVO.setPoReplyDate(item.getEsDlvDate());
        orderStateVO.setRemark(item.getRemark());
        orderStateVO.setSupplierRcvTime(item.getOrderDate());
        orderStateVO.setPoInvoiceNo(item.getInvoiceNo());
        orderStateVO.setUpdateTime(new Date());
        orderStateVO.setProvider("cnm");
        orderStateVO.setBeginProduceDate(item.getStartProductionDate());
        orderStateVO.setSupplierCode(convenSupplierCodeByOrderTypeName(item.getOrderType()));
        return orderStateVO;
    }

    public ResultVo<String> addOrderState(OrderStateVO orderStateVO) {
        if (null == orderStateVO) {
            return ResultVo.failure("保存订单状态失败");
        }
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setContent(JSON.toJSONString(orderStateVO));
        rabbitMqMessage.setFlag(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_ORDER_STATE);
        rabbitMqMessage.setDataType(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_ORDER);
        rabbitMqMessage.setSystem(Constants.OPS);
        boolean sendResult = sendMessage.sendOrderStateMsg(rabbitMqMessage);

        if (sendResult) {
            return ResultVo.success("发送成功");
        }
        return ResultVo.failure("发送失败");
    }

    public static String convenSupplierCodeByOrderTypeName(String orderTypeName) {
        if (org.apache.commons.lang3.StringUtils.isBlank(orderTypeName)) {
            return "";
        }
        if (orderTypeName.contains("天津")) {
            return "CT";
        } else if (orderTypeName.contains("北京")) {
            return "CM";
        } else if (orderTypeName.contains("中国") || orderTypeName.contains("制造")) {
            return "CN";
        } else if (orderTypeName.contains("上海")) {
            return "TZ";
        } else if (orderTypeName.contains("特注品订单")) {
            return "YZ";
        } else {
            return "";
        }
    }



}
