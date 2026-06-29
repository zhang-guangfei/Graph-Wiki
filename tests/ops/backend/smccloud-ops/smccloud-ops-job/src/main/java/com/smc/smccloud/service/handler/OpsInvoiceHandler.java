package com.smc.smccloud.service.handler;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.service.InvoiceServiceFeignApi;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Author: B90034
 * Date: 2021-12-03 16:12
 * Description:
 */
@Slf4j
@Component
public class OpsInvoiceHandler {

    @Resource
    private InvoiceServiceFeignApi invoiceServiceFeignApi;
    @Resource
    private RedisManager redisManager;

    public void init() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    public void destroy() {
        ThreadLocalMapUtil.clear();
    }

    /**
     * 导入进口发票数据
     */
    @XxlJob(value = "impImportInvoiceInfo", init = "init", destroy = "destroy")
    public ReturnT<String> impImportInvoiceInfo() {
        String param = XxlJobHelper.getJobParam();
        Date startTime = null;
        Date endTime;
        String redisLastTimeKey = "ops:job:impinvoice:GWLastTime";
        if (StringUtils.isEmpty(param)) {
            // bug13575 ,接口参数时间区间优化，开始时间当前时间-8小时，可在redis配置，默认8小时。
//            Object obj = redisManager.get(redisLastTimeKey);
//            if (obj != null) {
//                // 开始时间 = 上次成功的结束时间 - 30min
//                startTime = DateUtil.addMinute(DateUtil.stringToDateTime(obj.toString()), -30);
//            }
//            if (startTime == null) {
//                startTime = DateUtil.getToday();
//            }
            // 获取当前日期
            String redisHourKey = "ops:job:impinvoice:GWHour";
            int hour = -8;
            Object obj = redisManager.get(redisHourKey);
            if (obj != null) {
                hour = (int) obj;
            } else {
                redisManager.set(redisHourKey, hour);
            }
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
            // 设置为昨天
            cal.add(Calendar.HOUR_OF_DAY, hour);
            startTime = cal.getTime();
            endTime = DateUtil.getNow();
        } else {
            try {
                String[] params = param.split(",");
                startTime = DateUtil.stringToDateTime(params[0]);
                endTime = DateUtil.stringToDateTime(params[1]);
            } catch (Exception e) {
                XxlJobHelper.handleFail("执行参数异常,请输入正确的参数,如: 2021-10-19,2021-11-20");
                return ReturnT.FAIL;
            }
        }

        XxlJobHelper.log("params => plantMark = AM, invNo = , startTime = " + DateUtil.dateToDateTimeString(startTime)
                + ", endTime = " + DateUtil.dateToDateTimeString(endTime));
        ResultVo<String> impResult = invoiceServiceFeignApi.impImportInvoiceInfo("AM", "", startTime, endTime);
        if (impResult.isSuccess()) {
            log.info("进口发票数据导入成功");
            if (StringUtils.isEmpty(param)) {
                redisManager.set(redisLastTimeKey, DateUtil.dateToDateTimeString(endTime));
            }
            XxlJobHelper.handleSuccess("【" + DateUtil.dateToDateTimeString(startTime) + ","
                    + DateUtil.dateToDateTimeString(endTime) + "】" + impResult.getData());
            return ReturnT.SUCCESS;
        } else {
            log.error("进口发票数据导入失败: {}", impResult.getMessage());
            XxlJobHelper.handleFail("进口发票数据导入失败: " + "【" + DateUtil.dateToDateTimeString(startTime) + ","
                    + DateUtil.dateToDateTimeString(endTime) + "】" + impResult.getMessage());
            return ReturnT.FAIL;
        }
    }

    /**
     * 定时北京制造发货数据导入并发票入库(type=1是23测试服务器)
     */
    @XxlJob(value = "synImpCNInvoicePack", init = "init", destroy = "destroy")
    public ReturnT<String> synImpCNInvoicePack() throws Exception {
        String param = XxlJobHelper.getJobParam();
        String redisLastTimeKey = "ops:job:impinvoice:CNImpLastTime";
        String optTime=null;
        Integer type=1;
        if (StringUtils.isEmpty(param)) {
            Object obj = redisManager.get(redisLastTimeKey);
            if (obj != null) {
                // 开始时间
//                optTime = obj.toString();
                optTime = DateUtil.dateToDateTimeString(DateUtil.addHour(DateUtil.stringToDateTime(obj.toString()), -4));
            }
            if (optTime == null) {
                optTime =DateUtil.dateToDateString( DateUtil.addDay(DateUtil.getToday(),-1));
            }
        }else {
            type= Integer.valueOf(param.substring(0,1));
            optTime =param.substring(1);
        }

        String redisTime=DateUtil.dateToDateTimeString(DateUtil.getNow());
        XxlJobHelper.log("==> 进入定时北京制造发货数据导入并发票入库执行器:"+optTime);
        ResultVo<String> stringResultVo = invoiceServiceFeignApi.synImpCNInvoicePack(optTime,type);
        if (!stringResultVo.isSuccess()) {
            XxlJobHelper.log("北京制造发货数据导入有误 =>{}", stringResultVo.getMessage());
            return ReturnT.FAIL;
        }
        redisManager.set(redisLastTimeKey, redisTime);
        XxlJobHelper.log("接入北京制造发货数据完毕:"+stringResultVo.getData());
        return ReturnT.SUCCESS;
    }

    /**
     * 定时北京制造发票数据导入并发票入库
     */
    @XxlJob(value = "synVExportImpCNInvoicePack", init = "init", destroy = "destroy")
    public ReturnT<String> synVExportImpCNInvoicePack() throws Exception {
        String param = XxlJobHelper.getJobParam();
        String redisLastTimeKey = "ops:job:impinvoice:CNInvoiLastTime";
        String optTime=null;
        Integer type=1;
        if (StringUtils.isEmpty(param)) {
            Object obj = redisManager.get(redisLastTimeKey);
            if (obj != null) {
                // 开始时间
                optTime = obj.toString();
            }
            if (optTime == null) {
                optTime =DateUtil.dateToDateString(DateUtil.addDay(DateUtil.getToday(),-1));
            }
        }else {
            type= Integer.valueOf(param.substring(0,1));
            optTime =param.substring(1);
        }

        String redisTime=DateUtil.dateToDateTimeString(DateUtil.getNow());
        XxlJobHelper.log("==> 进入定时北京制造发票数据导入并发票入库执行器:"+optTime);
        ResultVo<String> stringResultVo = invoiceServiceFeignApi.synVExportImpCNInvoicePack(optTime,type);
        if (!stringResultVo.isSuccess()) {
            XxlJobHelper.log("北京制造发票数据导入有误 =>{}", stringResultVo.getMessage());
            return ReturnT.FAIL;
        }
        redisManager.set(redisLastTimeKey, redisTime);;
        XxlJobHelper.log("接入北京制造发票数据完毕："+stringResultVo.getData());
        return ReturnT.SUCCESS;
    }

    /**
     * 定时广州制造发货数据导入
     */
    @XxlJob(value = "synImpGZInvoicePack", init = "init", destroy = "destroy")
    public void synImpGZInvoicePack() throws Exception {
//        String redisLastTimeKey = "ops:job:impinvoice:GZImpLastTime";
        String param = XxlJobHelper.getJobParam();
        String optDate=null;
        if (StringUtils.isEmpty(param)) {
//            Object obj = redisManager.get(redisLastTimeKey);
//            if (obj != null) {
//                // 开始时间
//                optDate = obj.toString();
//            }
//            if (optDate == null) {
//                optDate =DateUtil.dateToDateString( DateUtil.addDay(DateUtil.getToday(),-1));
            optDate =DateUtil.dateToDateString(DateUtil.getMonthFirstDate(DateUtil.getToday()));
//            }
        }else {
            optDate =param;
        }
        XxlJobHelper.log("==> 进入定时广州制造发货数据导入并发票入库执行器:"+optDate);
        String redisTime=DateUtil.dateToDateTimeString(DateUtil.getNow());
        ResultVo<String> stringResultVo = invoiceServiceFeignApi.synImpGZInvoicePack(optDate);
        if (!stringResultVo.isSuccess()) {
            XxlJobHelper.log("广州制造发货数据导入有误 =>{}", stringResultVo.getMessage());
            XxlJobHelper.handleFail("广州制造发货数据导入有误"+stringResultVo.getMessage());
            return;
        }
//        redisManager.set(redisLastTimeKey, redisTime);
        XxlJobHelper.handleSuccess("接入广州制造发货数据完毕:"+stringResultVo.getData());
    }

    /**
     * 定时广州制造增值税发票导入
     */
    @XxlJob(value = "syncGZSalesinvoiceToIMP", init = "init", destroy = "destroy")
    public void syncGZSalesinvoiceToIMP() throws Exception {

        String redisLastTimeKey = "ops:job:impinvoice:GZInvoiLastTime";
        String param = XxlJobHelper.getJobParam();
        String optDate=null;
        if (StringUtils.isEmpty(param)) {
            Object obj = redisManager.get(redisLastTimeKey);
            if (obj != null) {
                // 开始时间
                optDate = obj.toString();
            }
            if (optDate == null) {
                optDate =DateUtil.dateToDateString( DateUtil.addDay(DateUtil.getToday(),-1));
            }
        }else {
            optDate =param;
        }
        XxlJobHelper.log("==> 进入广州制造增值税发票导入执行器:"+optDate);
        String redisTime=DateUtil.dateToDateTimeString(DateUtil.getNow());
        ResultVo<String> stringResultVo = invoiceServiceFeignApi.syncGZSalesinvoiceToIMP(optDate);
        if (!stringResultVo.isSuccess()) {
            XxlJobHelper.log("广州制造增值税发票导入有误 =>{}", stringResultVo.getMessage());
            XxlJobHelper.handleFail("广州制造增值税发票导入有误"+stringResultVo.getMessage());
            return;
        }
        redisManager.set(redisLastTimeKey, redisTime);
        XxlJobHelper.log("广州制造增值税发票导入完毕:"+stringResultVo.getData());
        XxlJobHelper.handleSuccess("接入广州制造增值税发票完毕:"+stringResultVo.getData());
    }


    /**
     * 更新关务发票到货状态
     * 导入OPS_V_ImpInvoiceStatusFrmCMS数据
     * @throws Exception
     */
    @XxlJob(value = "impInvoiceStatusFrmCMS", init = "init", destroy = "destroy")
    public void impInvoiceStatusFrmCMS() throws Exception{
        XxlJobHelper.log("==> 更新关务发票到货状态  功能已停用 ");
//        ResultVo<String> resultVo = invoiceServiceFeignApi.impInvoiceStatusFrmCMS();
//        if (resultVo.isSuccess()) {
//            XxlJobHelper.handleSuccess(resultVo.getData());
//        } else {
//            XxlJobHelper.handleFail(resultVo.getMessage());
//        }
    }

    /**
     * 定时发票入库
     */
    @XxlJob(value = "synToImpData", init = "init", destroy = "destroy")
    public void synToImpData() throws Exception {
        XxlJobHelper.log("==> 进入定时发票入库执行器");
        ResultVo<String> stringResultVo = invoiceServiceFeignApi.synToImpData();
        if (!stringResultVo.isSuccess()) {
            XxlJobHelper.log("定时发票入库有误 =>{}", stringResultVo.getMessage());
            XxlJobHelper.handleFail("定时发票入库有误"+stringResultVo.getMessage());
            return;
        }
        XxlJobHelper.log("定时发票入库完毕!!");
        XxlJobHelper.handleSuccess("定时发票入库完毕!!");
    }

    /**
     * 定时成本结算
     */
    @XxlJob(value = "autoDataToCost", init = "init", destroy = "destroy")
    public void autoDataToCost() throws Exception {
        XxlJobHelper.log("==> 进入定时成本结算执行器");
        ResultVo<String> stringResultVo = invoiceServiceFeignApi.autoDataToCost();
        if (!stringResultVo.isSuccess()) {
            XxlJobHelper.log("定时成本结算有误 =>{}", stringResultVo.getMessage());
            XxlJobHelper.handleFail("定时成本结算有误"+stringResultVo.getMessage());
            return;
        }
        XxlJobHelper.log("定时成本结算完毕!!");
        XxlJobHelper.handleSuccess("定时成本结算完毕!!");
    }

    /**
     * 定时制造发货数据发票入库
     */
    @XxlJob(value = "autoGPconfirmPOInvoice", init = "init", destroy = "destroy")
    public void autoGPconfirmPOInvoice() throws Exception {
        String param = XxlJobHelper.getJobParam();
        String optDate=DateUtil.getFormatDate(new Date(),"yyyy-MM-dd");
        if (StringUtils.isNotBlank(param)) {
            optDate=param;
        }
        XxlJobHelper.log("==> 进入定时制造发货数据发票入库执行器");
        ResultVo<String> stringResultVo = invoiceServiceFeignApi.autoGPconfirmPOInvoice(optDate);
        if (!stringResultVo.isSuccess()) {
            XxlJobHelper.log("制造发货数据发票入库有误 =>{}", stringResultVo.getMessage());
            XxlJobHelper.handleFail("制造发货数据发票入库有误"+stringResultVo.getMessage());
            return;
        }
        XxlJobHelper.log("制造发货数据发票入库完毕!!");
        XxlJobHelper.handleSuccess("制造发货数据发票入库完毕!!");
    }

    /**
     * 定时导入PO明细
     */
    @XxlJob(value = "autoConfirmPODetail", init = "init", destroy = "destroy")
    public void autoConfirmPODetail() throws Exception {
        XxlJobHelper.log("==> 进入定时导入PO明细执行器");
        ResultVo<String> stringResultVo = invoiceServiceFeignApi.autoConfirmPODetail();
        if (!stringResultVo.isSuccess()) {
            XxlJobHelper.log("导入PO明细有误 =>{}", stringResultVo.getMessage());
            XxlJobHelper.handleFail("导入PO明细有误"+stringResultVo.getMessage());
            return;
        }
        XxlJobHelper.log("导入PO明细完毕!!");
        XxlJobHelper.handleSuccess("导入PO明细完毕!!");
    }

    /**
     * branch_inventory_transaction表在月末最后一天进行决算处理，生成决算结果表
     * @throws Exception
     */
    @XxlJob(value = "monthlyInventorySummary", init = "init", destroy = "destroy")
    public void monthlyInventorySummary() throws Exception {
        XxlJobHelper.log("==> 开始进行决算处理");
        invoiceServiceFeignApi.monthlyInventorySummary();
        XxlJobHelper.log("已经生成决算结果表,请查看表monthly_inventory_summary");
    }

}
