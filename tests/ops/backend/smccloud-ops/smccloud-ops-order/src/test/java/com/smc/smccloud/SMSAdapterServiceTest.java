package com.smc.smccloud;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.EmailUtil;
import com.smc.smccloud.core.utils.FileUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.OrderModifyMapper;
import com.smc.smccloud.model.OrderModify.OrderModifyDO;
import com.smc.smccloud.model.order.OrderCancelResult;
import com.smc.smccloud.model.order.RcvMasterDO;
import com.smc.smccloud.service.SMSOrderServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-04-27 11:13
 * Description:
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SMSAdapterServiceTest {

    @Resource
    private OrderModifyMapper orderModifyMapper;
    @Resource
    private SMSOrderServiceFeignApi smsOrderServiceFeignApi;
    @Resource
    private JavaMailSenderImpl javaMailSender;
    @Resource
    private HttpServletResponse httpServletResponse;

    @Before
    public void setUp() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }


    @Test
    public void testDownLoadFile() {
        FileUtil.downloadFileToResponse("E:\\gooleDownload\\退货数据.xlsx",httpServletResponse);

    }


    @Test
    public void orderCancelTest() {
        LambdaQueryWrapper<OrderModifyDO> query = new LambdaQueryWrapper<>();
        query.eq(OrderModifyDO::getModifyType, "C")
                .eq(OrderModifyDO::getApplyNo, "HDD2204270001");
        List<OrderModifyDO> list = orderModifyMapper.selectList(query);

        List<OrderCancelResult> resultList = new ArrayList<>(list.size());
        OrderCancelResult result;
        for (OrderModifyDO info : list) {
            result = new OrderCancelResult();
            result.setNo(info.getApplyNo());
            result.setOrderNo(info.getOrderNo());
            if (info.getStatus() == 6) {
                result.setResult("2");
                result.setMessage("自动取消成功");
            } else {
                result.setResult("3");
                result.setMessage("取消异常：物流不允许取消");
            }
            log.info("OrderCancelResult = {}", result);
            resultList.add(result);
        }

        ResultVo<Boolean> sendMsgResult = smsOrderServiceFeignApi.sendOrderCancelReturnMessage(resultList);
        log.info("sendMsgResult = {}", sendMsgResult);
    }

    @Test
    public void testSendMainTemplate() {

        List<RcvMasterDO> list = new ArrayList<>();

        RcvMasterDO o = new RcvMasterDO();
        o.setOrOrderNo("123123123123123123123123123");
        o.setCustomerNo("AAAwwwwwwwwww");
        o.setDeptNo("12");
        o.setHlCode("123456");

        RcvMasterDO o2 = new RcvMasterDO();
        o2.setOrOrderNo("123456-11");
        o2.setCustomerNo("AAAwwwwwwwwww");
        o2.setDeptNo("12");
        o2.setHlCode("123456");


        RcvMasterDO o3 = new RcvMasterDO();
        o3.setOrOrderNo("123456-11");
        o3.setCustomerNo("AAAwwwwwwwwww");
        o3.setDeptNo("12");
        o3.setHlCode("123456");

        RcvMasterDO o4 = new RcvMasterDO();
        o4.setOrOrderNo("123456-11");
        o4.setCustomerNo("AAAwwwwwwwwww");
        o4.setDeptNo("12");
        o4.setHlCode("123456");

        list.add(o);
        list.add(o2);
        list.add(o3);
        list.add(o4);

        StringBuilder content = new StringBuilder();
        content.append("<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >")
                .append("<thead style=\"font-weight: bold;color: #000000;background-color: #B8CCE4;\" >")
                .append("<tr>" +
                        "<td width=\"15%\" >订单号</td>" +
                        "<td width=\"10%\" >客户代码</td>" +
                        "<td width=\"10%\" >部门</td>" +
                        "<td width=\"15%\" >HL部门</td>" +
                        "</tr>")
                .append("</thead><tbody>");

        for (RcvMasterDO item : list) {
            content.append("<tr>")
                    .append("<td>").append(item.getOrOrderNo()).append("</td>")
                    .append("<td>").append(item.getCustomerNo()).append("</td>")
                    .append("<td>").append(item.getDeptNo()).append("</td>")
                    .append("<td>").append(item.getHlCode()).append("</td>")
                    .append("</tr>");
        }
        content.append("</tbody></table></br>");
        boolean send = EmailUtil.send(javaMailSender, "2355766573@qq.com", "liyingchao@smc.com.cn","测试邮件",content.toString());
        System.out.println("send = " + send);
    }

}
