package com.smc.ops.delivery.controller;

import com.alibaba.excel.EasyExcel;
import com.sales.ops.common.easyexcel.EasyExcelUtil;
import com.sales.ops.common.easyexcel.handler.Custemhandler;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.entity.EmailBarcode;
import com.sales.ops.db.entity.ExpdetailBarcodeSend;
import com.sales.ops.db.entity.RuleCustomerBing;
import com.sales.ops.dto.easyexcel.delivery.BarSendExcelDto;
import com.sales.ops.dto.util.CommonResult;
import com.smc.ops.delivery.conf.FileUrlProp;
import com.smc.ops.delivery.service.barcode.SendBarcodeService;
import com.smc.smccloud.log.annotation.SysLog;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：发送箱码信息
 * @date ：Created in 2023/6/7 9:06
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/send/barcode")
public class SendBarcodeController {

    @Autowired
    private SendBarcodeService sendBarService;
    @Autowired
    private FileUrlProp fileProp;

    /**
     * 发送箱码明细给指定客户
     * @return
     * @throws OpsException
     */
    @SysLog("发送箱码明细给指定客户")
    @RequestMapping("/save/file/send/mail")
    public CommonResult<String> readOpsBarToSave() throws OpsException {
        // 1.查询发送箱码明细规则，以groupid分组
        Map<Integer, List<RuleCustomerBing>> integerListMap = sendBarService.groupByCurRule();
        for (Map.Entry<Integer, List<RuleCustomerBing>> entry : integerListMap.entrySet()) {
            // 2.1获取发送邮件时间
            int sendTime = entry.getValue().get(0).getSendTime();
            if(DateUtil.getHour(DateUtil.getNow()) == sendTime){
                //2.2. 获取时间范围 取昨天的数据
                Date startDate = DateUtil.getCurrentDate();
                Date endDate= DateUtil.addDay(startDate,1);
                //3 获取该group下顾客代码的发货箱码数据
                List<ExpdetailBarcodeSend> barByCustomerNOs = sendBarService.findBarByCustomerNO(entry.getValue(),DateUtil.dateToDateTimeString(startDate),DateUtil.dateToDateTimeString(endDate));
                if(CollectionUtils.isNotEmpty(barByCustomerNOs)){
                    String mailAddress = entry.getValue().get(0).getMail();
                    String nickName = entry.getValue().get(0).getConsignee();
                    String fileName = fileProp.getBase() + "delivery/barcode/SMC发货信息-"+nickName+"-"+ DateUtil.dateToDateString(DateUtil.getNow()) + ".xlsx";
                    //String fileName = "D:\\\\workSpaceC14717\\\\"+nickName+".xlsx"; //本地地址
                    EasyExcel.write(fileName, BarSendExcelDto.class)
                            .registerWriteHandler(new Custemhandler())
                            .registerWriteHandler(EasyExcelUtil.getStyleStrategy())
                            .sheet("箱码明细")
                            .doWrite(barByCustomerNOs);
                    sendBarService.saveMailToDb(fileName,mailAddress,"出库箱码明细");
                }
            }
        }
        return CommonResult.success();
    }

    /**
     * bugid:12391 c14717 2023/10/31
     * 发送箱码明细给指定客户
     * 1.抽取emailBarcode表 updateTime 字段取值范围是今日 mail分组的数据
     * 2.根据mail字段查询 emailBarcode表
     * 3.生成excel
     * 4.写入邮件表
     * @return
     * @throws OpsException
     */
    @SysLog("通过邮件字段发送箱码明细给指定客户")
    @RequestMapping("/saveFileByEmail/sendMail")
    public CommonResult<String> collectBarsaveFileToEmail() throws OpsException {
        // 1.查询发送箱码，获取分组邮件
        String startTime = DateUtil.dateToDateTimeString(DateUtil.getCurrentDate());
        String endTime = DateUtil.dateToDateTimeString(DateUtil.getNow());
        List<String> barcodeListGroup = sendBarService.findBarcodeListGroup(startTime, endTime);
        int fileFlag = 0;
        for(String mail : barcodeListGroup){
            fileFlag ++;
            List<EmailBarcode> listBarcodeDateByEmail = sendBarService.findListBarcodeDateByEmail(mail, startTime, endTime);
            if(CollectionUtils.isNotEmpty(listBarcodeDateByEmail)){
                String fileName = "SMC发货信息-"+fileFlag+".xlsx";
                String filePath = fileProp.getBase() + "delivery/emailBarcode/"+ DateUtil.dateToDateString(DateUtil.getNow())+"/";
                //String filePath = "D:\\\\workSpaceC14717\\\\emailBarcode\\\\"+ DateUtil.dateToDateString(DateUtil.getNow())+"\\\\"; //本地地址
                File directory = new File( filePath );
                // 如果存放目标文件路径不存在，则创建文件夹
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                EasyExcel.write(filePath+fileName, BarSendExcelDto.class)
                        .registerWriteHandler(new Custemhandler())
                        .registerWriteHandler(EasyExcelUtil.getStyleStrategy())
                        .sheet("箱码明细")
                        .doWrite(listBarcodeDateByEmail);
                sendBarService.saveMailToDb(filePath+fileName,mail,"箱码明细");
            }
        }
        return CommonResult.success();
    }
}
