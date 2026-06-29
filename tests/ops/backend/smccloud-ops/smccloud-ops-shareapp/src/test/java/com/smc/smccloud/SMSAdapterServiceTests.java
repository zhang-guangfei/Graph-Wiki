package com.smc.smccloud;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.EmailUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.sampleorder.SamplebalMapper;
import com.smc.smccloud.model.enums.SampleBalOptCodeEnum;
import com.smc.smccloud.model.opsreturn.ReturnOrderBackCallVO;
import com.smc.smccloud.model.sampleorder.SamplebalDO;
import com.smc.smccloud.service.ReturnOrderService;
import com.smc.smccloud.service.SMSAdapterService;
import com.smc.smccloud.service.SampleOrderApplyFeignApi;
import com.smc.smccloud.service.SampleOrderApplyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-03-16 16:10
 * Description:
 */
@Slf4j
public class SMSAdapterServiceTests extends OpsShareAppApplicationTests {

    @Resource
    private SMSAdapterService smsAdapterService;
    @Resource
    private ReturnOrderService returnOrderService;
    @Resource
    private SampleOrderApplyFeignApi sampleOrderApplyFeignApi;
    @Resource
    private SampleOrderApplyService sampleOrderApplyService;
    @Resource
    private SamplebalMapper samplebalMapper;
    @Resource
    private JavaMailSenderImpl mailSender;

    @Test
    public void deptNoTest() {
//        String deptNo = "223112";
//        System.out.println("====> deptNo = " + deptNo);
//        System.out.println("====> HR deptNo = " + converTo(deptNo));
        /**
         * [
         *     {
         *         "applyNo": "TH20220001",
         *         "itemNo": 1,
         *         "allowReturnQuantity": 1,
         *         "notAllowReturnQuantity": 2,
         *         "shelves": "101"
         *     }
         * ]
         */
        List<ReturnOrderBackCallVO> list = new ArrayList<>();
        ReturnOrderBackCallVO item = new ReturnOrderBackCallVO();
        item.setApplyNo("TH20220001");
        item.setItemNo(1);
        item.setAllowReturnQuantity(1);
        item.setNotAllowReturnQuantity(2);
        item.setShelves("101");
        list.add(item);
        ResultVo<String> resultVo = returnOrderService.dealReturnOrder(list);


    }

    @Test
    public void testCreateOrder() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);

        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .isNotNull(SamplebalDO::getExpDate)
                .eq(SamplebalDO::getOptCode, SampleBalOptCodeEnum.DJZ.getCode());
        List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(queryWrapper);
        samplebalDOList.subList(0, 2);
        sampleOrderApplyService.writeExcel(samplebalDOList,"223411");
    }


    @Test
    public void findBinApplyTest() {
        String no = "ZK2205190012";
        log.info("findBinApplyTest {} >>> {}", no, smsAdapterService.findBinApply(no));
    }


    @Test
    public void testAutoSampleBal() {
        ResultVo<String> resultVo = sampleOrderApplyService.autoInsertSales();
        System.out.println("resultVo = " + resultVo.toString());
    }

    @Test
    public void testAutoSampleBalTurn() {
        ResultVo<String> resultVo = sampleOrderApplyService.autoOrderCarryTurn();
        System.out.println("resultVo = " + resultVo.toString());
    }

    @Test
    public void findChinaRegionWarehouseTest() {
        String no = "FK2205240001";
        log.info("findBinApplyTest {} >>> {}", no, smsAdapterService.findChinaRegionWarehouse(no));
    }

    @Test
    public void mailTest() {
        EmailUtil.send(mailSender, "edp06@smcgz.com.cn", null, "OPS系统测试邮件发送", "OPS系统测试邮件发送");
    }
}
