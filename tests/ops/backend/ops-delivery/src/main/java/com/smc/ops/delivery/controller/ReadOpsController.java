package com.smc.ops.delivery.controller;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.entity.EmailBarcode;
import com.sales.ops.db.entity.ExpdetailBarcodeSend;
import com.sales.ops.db.entity.OpsBarcodeRuleConfig;
import com.sales.ops.dto.util.CommonResult;
import com.smc.ops.delivery.service.barcode.ReadOpsService;
import com.smc.smccloud.log.annotation.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：读取ops数据库
 * @date ：Created in 2023/6/7 9:05
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/read/ops")
@Slf4j
public class ReadOpsController {

    @Autowired
    private ReadOpsService readOpsService;

    /**
     * 抽取ops发货箱码明细
     * @return
     * @throws OpsException
     */
    @SysLog("抽取ops发货箱码明细")
    @RequestMapping("/bar")
    public CommonResult<String> readOpsBarToSave() throws OpsException {
        //1.获取出库箱码明细规则
        List<OpsBarcodeRuleConfig> barRule = readOpsService.findBarRule();
        if(CollectionUtils.isNotEmpty(barRule)){
            for(OpsBarcodeRuleConfig barRul : barRule){
                //2.获取时间范围 开始时间比上次结束时间提前10分钟
                String beginTime = DateUtil.dateToDateTimeString(DateUtil.addMinute(barRul.getLastSyncTime(),-10)) ;
                Date endDate = DateUtil.addHour(barRul.getLastSyncTime(),barRul.getSyncTime());
                String endTime = DateUtil.dateToDateTimeString(endDate);
                //3.获取规则下的顾客代码
                List<String> cusRuleCusNos = readOpsService.findCusRuleCusNos(barRul.getRuleId());
                if(CollectionUtils.isNotEmpty(cusRuleCusNos)){
                    //4.获取出库箱码数据 时间排序 正序
                    List<ExpdetailBarcodeSend> opsBarList = readOpsService.findOpsBarList(cusRuleCusNos,beginTime, endTime);
                    if(CollectionUtils.isNotEmpty(opsBarList)){
                        //5.验证是否有重复数据
                        readOpsService.checkBarcodeRep(opsBarList);
                        //6.与关联查询去交集
                        List<ExpdetailBarcodeSend> saveBarList = new ArrayList<>();
                        List<ExpdetailBarcodeSend> saveBarNoUpTimeList = new ArrayList<>();
                        for(ExpdetailBarcodeSend param : opsBarList){
                            if(StringUtils.isNotBlank(param.getOrderFno())){
                                param.setUpdateTime(DateUtil.getNow());
                                param.setUpdateUser("sys");
                                saveBarList.add(param);
                            }else {
                                saveBarNoUpTimeList.add(param);
                            }
                        }
                        //7.存储记录，更新最后同步时间
                        if(CollectionUtils.isNotEmpty(saveBarList)){
                            readOpsService.saveBarcode(saveBarList,endTime,barRul.getRuleId());
                        }
                        if(CollectionUtils.isNotEmpty(saveBarNoUpTimeList)){
                            readOpsService.saveBarcodeNoUpTime(saveBarNoUpTimeList,endTime,barRul.getRuleId());
                        }
                        //8.同步updateTime为空的数据
                        List<ExpdetailBarcodeSend> upTimeNonList = readOpsService.findOpsExpSendByUpTimeList();
                        readOpsService.updateExpdetailBarcodeSend(upTimeNonList);
                    }
                    //9 更新最后同步时间 bugid:11396 20230717 c14717
                    readOpsService.updateBarcodeRule(endTime,barRul.getRuleId());
                }
            }
        }
        return CommonResult.success();
    }

    /**
     * bugid:12391 c14717 2023/10/31
     * 抽取ops发货箱码明细
     * 1.抽取昨日 ops_core expdetail表和expbarcode表 email字段不为空的字段
     * 2.验证emailBarcode表对比重复数据
     * 3.批量插入emailBarcode表
     * 4.同步emailBarcode表updateTime为空的数据
     * @return
     * @throws OpsException
     */
    @SysLog("通过邮件字段抽取ops发货箱码明细")
    @RequestMapping("/emailBar")
    public CommonResult<String> readOpsEmailBarToSave() throws OpsException {
        Date yesterday = DateUtil.addDay(DateUtil.getNow(), -1);
        String beginTime = DateUtil.dateToDateTimeString(DateUtil.getBeginTime(yesterday));
        String endTime = DateUtil.dateToDateTimeString(DateUtil.getEndTime(yesterday));
        //本地测试用
        /*beginTime = "2023-06-01";
        endTime = "2023-06-02";*/
        Integer emailBarcodeCount = readOpsService.countExpBarAndExpdetailHaveEmail(beginTime, endTime);
        log.info("每日保存箱码条数：{}",emailBarcodeCount);
        //1. 查询expBarcode表和 expdetail表 email有值的数据 同步数据
        List<EmailBarcode> opsBarList = readOpsService.findExpBarAndExpdetailHaveEmail(beginTime, endTime);
        if(CollectionUtils.isNotEmpty(opsBarList)){
            //2.根据箱码信息验重
            readOpsService.checkEmailBarcodeRep(opsBarList);
            //3.与关联查询去交集
            List<EmailBarcode> saveBarList = new ArrayList<>();
            List<EmailBarcode> saveBarNoUpTimeList = new ArrayList<>();
            for(EmailBarcode param : opsBarList){
                if(StringUtils.isNotBlank(param.getOrderFno())){
                    param.setUpdateTime(DateUtil.getNow());
                    param.setUpdateUser("sys");
                    saveBarList.add(param);
                }else {
                    saveBarNoUpTimeList.add(param);
                }
            }
            //4.存储记录，更新最后同步时间
            if(CollectionUtils.isNotEmpty(saveBarList)){
                //4.1 存储数据更新齐全
                readOpsService.saveEmailBarcode(saveBarList);
            }
            if(CollectionUtils.isNotEmpty(saveBarNoUpTimeList)){
                //4.2 存储数据不齐全，不更新update
                readOpsService.saveEmailBarcodeNoUpTime(saveBarNoUpTimeList);
            }
            //5.同步updateTime为空的数据
            List<EmailBarcode> upTimeNonList = readOpsService.findOpsEmailBarNotUptimeList();
            //6.更新updateTime为空的数据
            readOpsService.updateEmailBar(upTimeNonList);
        }
        opsBarList = null;
        return CommonResult.success();
    }
}
