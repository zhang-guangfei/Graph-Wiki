package com.smc.smccloud.service.handler;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.*;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Component
@Slf4j
public class OpsRcvOrderHandler {
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private ReceiveOrderServiceFeignApi receiveOrderServiceFeignApi;
    @Resource
    private StockAdjustServiceFeignApi stockAdjustServiceFeignApi;
    @Resource
    private ExpdetailServiceFeignApi expdetailServiceFeignApi;
    @Resource
    private OrderModifyServiceFeignApi orderModifyServiceFeignApi;
    @Resource
    private RedisManager redisManager;
    @Resource
    private RedissonUtil redissonUtil;

    public void init() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    public void destroy() {
        ThreadLocalMapUtil.clear();
    }

    /**
     * 自动接入订单
     */
    @XxlJob(value = "autoRcvOrder", init = "init", destroy = "destroy")
    public void autoRcvOrder() throws Exception {
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "2");
        if (PublicUtil.isEmpty(dataTypeCodesInfo.getData())) {
            XxlJobHelper.log("【autoRcvOrder】 自动接入订单失败(未获取到执行开关)");
            return;
        }
        if ("1".equals(dataTypeCodesInfo.getData().getExtNote1())) {
            ResultVo<String> stringResultVo = receiveOrderServiceFeignApi.autoRcvOrderFeign();
            if (stringResultVo.isSuccess()) {
                XxlJobHelper.handleSuccess("【autoRcvOrder】 自动接入订单执行成功 -> " + stringResultVo.getData());
            } else {
                XxlJobHelper.handleFail("【autoRcvOrder】 自动接入订单执行失败 -> " + stringResultVo.getMessage());
            }
        } else {
            XxlJobHelper.handleSuccess("自动接入订单功能已被停止");
        }
    }


    /**
     * 定期批量生成客户订单号 bug-8874 将Redis缓存预生成订单号的数据类型由Set改为List
     */
    @XxlJob(value = "generateCustomerOrderNo", init = "init", destroy = "destroy")
    public void generateCustomerOrderNo() throws Exception {
        XxlJobHelper.log("==> 进入定期批量生成客户订单号执行器");
        long size = redisManager.lSize(Constants.SALES_ORDER_NO);
        XxlJobHelper.log("==> 当前可用订单号数量 【{}】 ", size);
        // 获取客户订单号预警数量
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "7");
        if (!dataTypeCodesInfo.isSuccess()) {
            XxlJobHelper.handleFail("获取客户订单号预警数量失败.");
            return;
        }
        int custOrderQty = (int) (Integer.parseInt(dataTypeCodesInfo.getData().getExtNote1()) - size);
        if (custOrderQty > 0) {
            ResultVo<Set<String>> setResultVo = commonServiceFeignApi.batchGeneratorBillNo("13", custOrderQty);
            if (!setResultVo.isSuccess()) {
                XxlJobHelper.handleFail("生成客户订单号失败");
                return;
            }
            Object[] orderNos = setResultVo.getData().stream().sorted().toArray();
            boolean result = redisManager.lRightPush(Constants.SALES_ORDER_NO, orderNos);
            if (!result) {
                XxlJobHelper.handleFail("客户订单号存储失败");
                return;
            }
            XxlJobHelper.handleSuccess("批量生成客户订单号完毕, 共计 : " + orderNos.length + " 个.");
        } else if (custOrderQty == 0)  {
            XxlJobHelper.handleSuccess("客户订单号余量充足，暂时不用生成喔");
        }
    }



    /**
     * 从IPS接入工厂订单  => ips_send_order_to_ops
     * @throws Exception
     */
    @XxlJob(value = "receiveIPSOrder", init = "init", destroy = "destroy")
    public void receiveIPSOrder() throws Exception {

        XxlJobHelper.log("开始从ips接入工厂订单");
        ResultVo<String> stringResultVo = null;
        try {
            List<String> ids = new ArrayList<>();
            stringResultVo = receiveOrderServiceFeignApi.receiveIPSOrder(ids);
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobHelper.log("定时接入ips接入工厂订单异常 ： "+e.getMessage());
            XxlJobHelper.handleFail("定时接入ips接入工厂订单异常 ： "+e.getMessage());
            return;
        }
        if (!stringResultVo.isSuccess()) {
            XxlJobHelper.log("ips接入工厂订单有误 =>{}", stringResultVo.getMessage());
            XxlJobHelper.handleFail("ips接入工厂订单有误 =>" + stringResultVo.getMessage());
            return;
        }
        XxlJobHelper.log(stringResultVo.getData());
        XxlJobHelper.handleSuccess("ips接入工厂订单完毕:"+stringResultVo.getData());
    }


    /**
     * 根据接入订单状态 修改接入中国制造订单状态
     * 修改 ExpInvCode(订货区分) , salesStatus, SalesDeliveryTime
     */
    @XxlJob(value = "updateCNMadeOrderStatus", init = "init", destroy = "destroy")
    public void updateCNMadeOrderStatus() throws Exception {
        XxlJobHelper.log("==> 进入定修改接入中国制造订单状态执行器");
        long startTime = System.currentTimeMillis();
        XxlJobHelper.log("开始执行时间 => {} ms", startTime);
        ResultVo<String> stringResultVo = receiveOrderServiceFeignApi.updateCNMadeOrderStatus();
        if (!stringResultVo.isSuccess()) {
            XxlJobHelper.log("【updateCNMadeOrderStatus】 修改接入中国制造订单状态有误 =>{}", stringResultVo.getMessage());
            XxlJobHelper.handleFail("【updateCNMadeOrderStatus】 修改接入中国制造订单状态有误 => " + stringResultVo.getMessage());
            return;
        }
        long endTime = System.currentTimeMillis();
        XxlJobHelper.log("执行结束时间 => {} s, 共耗费时间(s)=> {}", endTime, (endTime - startTime) / 1000);
        XxlJobHelper.log("导入中国工厂货期：" + stringResultVo.getMessage());
        XxlJobHelper.handleSuccess("修改接入中国制造订单状态完毕:"+stringResultVo.getData());
    }

    /**
     * 定时获取集团内客户交易价格
     */
    @XxlJob(value = "getPriceFromRcvDetail", init = "init", destroy = "destroy")
    public void getPriceFromRcvDetail() throws Exception {
        XxlJobHelper.log("==> 定时获取集团内客户交易价格");
        RLock lock = redissonUtil.lock("ops:calcOrderPrice:ing");
        ResultVo<String> priceFromRcvDetail = null;
        try {
            priceFromRcvDetail = receiveOrderServiceFeignApi.calcOrderPriceSMCGroupCustomer();
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobHelper.log("定时获取集团内客户交易价格异常 ： "+e.getMessage());
            XxlJobHelper.handleFail("定时获取集团内客户交易价格异常 ： "+e.getMessage());
        } finally {
            redissonUtil.unlock(lock);
        }
        XxlJobHelper.log(priceFromRcvDetail.getMessage());

    }

    /**
     * 定时将3C产品进行财务调整
     */
    @XxlJob(value = "autoStockAdjustOf3C", init = "init", destroy = "destroy")
    public void autoStockAdjustOf3C() throws Exception {
        XxlJobHelper.log("==> 定时将3C产品进行财务调整");
        ResultVo<String> resultVo = null;
        try {
            resultVo = stockAdjustServiceFeignApi.threeCProductAdjust();
            if (!resultVo.isSuccess()) {
                XxlJobHelper.log("3C产品调整失败"+ resultVo.getMessage());
                XxlJobHelper.handleFail("3C产品调整失败");
                return;
            }
            XxlJobHelper.log(resultVo.getMessage());
            XxlJobHelper.handleSuccess("3C产品调整完毕:"+resultVo.getData());
        } catch (Exception e) {
            XxlJobHelper.log("3C产品调整失败"+ e.getMessage());
            XxlJobHelper.handleFail("3C产品调整失败");
        }
    }
    /**
     *  入库型号和成本入库型号不一致生成组换,每天执行
     */
    //    <!--add by WuWeiDong 20221027 bug 2084 -->
    @XxlJob(value = "createStockAssembleFromInvoiceError", init = "init", destroy = "destroy")
    public void  createStockAssembleFromInvoiceError() {
        XxlJobHelper.log("==> 执行【入库型号和成本入库型号不一致生成组换】");
        try {
            ResultVo<String> resultVo = stockAdjustServiceFeignApi.createStockAssembleFromInvoiceError();
            if (!resultVo.isSuccess()) {
                XxlJobHelper.log("型号不一致生成组换失败"+ resultVo.getMessage());
                XxlJobHelper.handleFail("型号不一致生成组换失败"); //    <!--add by WuWeiDong 20221121   -->
                return;
            }
            XxlJobHelper.log("型号不一致成生组换成功："+ resultVo.getData());
            XxlJobHelper.handleSuccess("型号不一致成生组换成功："+ resultVo.getData()); //    <!--add by WuWeiDong 20221121   -->
        }
        catch (Exception ex){
            XxlJobHelper.log("型号不一致生成组换失败："+ ex.getMessage());
            XxlJobHelper.handleFail("型号不一致生成组换失败");
            log.error(" XxlJob：createStockAssembleFromInvoiceError 执行失败：",ex); //    <!--add by WuWeiDong 20221121   -->
        }
    }


    @XxlJob(value = "callExpExpdetailForGZ", init = "init", destroy = "destroy")
    public void callExpExpdetailForGZ() throws Exception {
        XxlJobHelper.log("==> 同步发货数据给广州制造");
        ResultVo<String> resultVo = null;
        try {
            resultVo = expdetailServiceFeignApi.callExpExpdetailForGZ();
            if (!resultVo.isSuccess()) {
                XxlJobHelper.log("同步发货数据给广州制造失败"+ resultVo.getMessage());
                XxlJobHelper.handleFail("同步发货数据给广州制造失败");
                return;
            }
            XxlJobHelper.log(resultVo.getMessage());
            XxlJobHelper.handleSuccess("同步发货数据给广州制造完毕:"+resultVo.getData());
        } catch (Exception e) {
            XxlJobHelper.log("同步发货数据给广州制造失败"+ e.getMessage());
            XxlJobHelper.handleFail("同步发货数据给广州制造失败");
        }
    }


    @XxlJob(value = "calcImportLotEPrice", init = "init", destroy = "destroy")
    public void calcImportLotEPrice() throws Exception {
        XxlJobHelper.log("==> 修正E价");
        ResultVo<String> resultVo = null;
        try {
            resultVo = orderModifyServiceFeignApi.calcImportLotEPrice();
            if (!resultVo.isSuccess()) {
                XxlJobHelper.log("修正E价失败"+ resultVo.getMessage());
                XxlJobHelper.handleFail("修正E价失败");
                return;
            }
            XxlJobHelper.log(resultVo.getMessage());
        } catch (Exception e) {
            XxlJobHelper.log("修正E价成功"+ e.getMessage());
            XxlJobHelper.handleFail("修正E价失败");
        }
    }

    // 全量同步工作日信息，需要把汉字转化为编码
    @XxlJob(value = "syncWorkDay", init = "init", destroy = "destroy")
    public void syncWorkDay() throws Exception {
        XxlJobHelper.log("==> 全量同步工作日信息");
        commonServiceFeignApi.syncWorkDay();
        XxlJobHelper.log("==> 全量同步工作日信息完成");
    }

    @XxlJob(value = "calcExportLotEPrice", init = "init", destroy = "destroy")
    public void calcExportLotEPrice() throws Exception {
        XxlJobHelper.log("==> 修正E价");
        ResultVo<String> resultVo = null;
        try {
            resultVo = orderModifyServiceFeignApi.calcExportLotEPrice();
            if (!resultVo.isSuccess()) {
                XxlJobHelper.log("修正E价失败"+ resultVo.getMessage());
                XxlJobHelper.handleFail("修正E价失败");
                return;
            }
            XxlJobHelper.log(resultVo.getMessage());
            XxlJobHelper.handleSuccess("修正E价完毕:"+resultVo.getData());
        } catch (Exception e) {
            XxlJobHelper.log("修正E价成功"+ e.getMessage());
            XxlJobHelper.handleFail("修正E价失败");
        }
    }

}
