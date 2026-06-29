package com.smc.ops.delivery.service.order.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.easyexcel.EasyExcelUtil;
import com.sales.ops.common.easyexcel.handler.Custemhandler;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.entity.OpsMail;
import com.sales.ops.db.entity.OpsOrderUpdateLog;
import com.sales.ops.dto.easyexcel.delivery.OrderModifyRecordExcelDto;
import com.sales.ops.enums.SendStatusEnum;
import com.smc.ops.delivery.conf.FileUrlProp;
import com.smc.ops.delivery.conf.OpsMailAdressConf;
import com.smc.ops.delivery.mapper.OpsExpdetailDao;
import com.smc.ops.delivery.mapper.OpsOrderUpdateLogMapper;
import com.smc.ops.delivery.service.order.OrderModifyRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/7/4 9:45
 */
@Service
@Slf4j
public class OrderModifyRecordServiceImpl implements OrderModifyRecordService {

    @Autowired
    private OpsOrderUpdateLogMapper opsOrderUpdateLogMapper;
    @Autowired
    private OpsExpdetailDao opsExpdetailDao;

    @Autowired
    private FileUrlProp fileProp;

    @Autowired
    private OpsMailAdressConf opsMailAdressConf;

    /**
     * bugid: 14562 订单还原、转定数据定时发送
     */
    @Override
    public void getOrderModifyReportToSaveExcelAndMailDb(){
        String lastMonthFirstDate = DateUtil.dateToDateString(DateUtil.getLastMonthFirstDate(DateUtil.getNow()));
        String lastMonthEndDate = DateUtil.dateToDateString(DateUtil.getLastMonthEndDate(DateUtil.getNow()));
        List<OrderModifyRecordExcelDto> reNewOrdersRecordList = getReNewOrdersRecordList(lastMonthFirstDate, lastMonthEndDate);
        List<OrderModifyRecordExcelDto> zdOrdersRecordList = getZDOrdersRecordList(lastMonthFirstDate, lastMonthEndDate);
        reNewOrdersRecordList.addAll(zdOrdersRecordList);
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(reNewOrdersRecordList)){
            String fileName = fileProp.getBase() + "delivery/orderModifyReport/订单还原及转定报表-"+ DateUtil.dateToDateString(DateUtil.getNow()) + ".xlsx";
            //String fileName = "D:\\\\workSpaceC14717\\\\订单还原及转定报表-"+ DateUtil.dateToDateString(DateUtil.getNow()) + ".xlsx";//本地地址
            EasyExcel.write(fileName, OrderModifyRecordExcelDto.class)
                    .registerWriteHandler(new Custemhandler())
                    .registerWriteHandler(EasyExcelUtil.getStyleStrategy())
                    .sheet("订单还原及转定报表")
                    .doWrite(reNewOrdersRecordList);
             saveMailToDb(fileName,opsMailAdressConf.getModifyOrderTO(),opsMailAdressConf.getModifyOrderCC());
        }
    }


    public List<OrderModifyRecordExcelDto> getReNewOrdersRecordList(String beginTime, String endTime){
        String handle = "订单还原";
        List<OpsOrderUpdateLog> selectList = opsOrderUpdateLogMapper.selectOpsOrderUpdateLogList(handle, beginTime, endTime);
        return initOrderModifyRecordExcelDto(selectList,handle);
    }

    public List<OrderModifyRecordExcelDto> getZDOrdersRecordList(String beginTime, String endTime){
        String handle = "转定";
        List<OpsOrderUpdateLog> selectList = opsOrderUpdateLogMapper.selectOpsOrderUpdateLogList(handle, beginTime, endTime);
        return initOrderModifyRecordExcelDto(selectList,handle);
    }

    public List<OrderModifyRecordExcelDto> initOrderModifyRecordExcelDto (List<OpsOrderUpdateLog> selectList,String handle){
        List<OrderModifyRecordExcelDto> resultList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(selectList)){
            for (OpsOrderUpdateLog param: selectList){
                OrderModifyRecordExcelDto obj = new OrderModifyRecordExcelDto();
                obj.setHandle(handle);
                obj.setOrderid(param.getOrderid());
                obj.setOrderItem(param.getOrderItem());
                obj.setCreateTime(param.getCreateTime());
                obj.setHandleResult(param.getResult());
                try {
                    JSONObject jsonObject = JSONObject.parseObject(param.getParams());
                    obj.setUserName(jsonObject.get("userName").toString());
                } catch (Exception e) {
                    log.error("解析操作人异常");
                }
                resultList.add(obj);
            }
        }
        return resultList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMailToDb(String fileUrls, String mailAddress, String cc){
        //保存邮件到数据库
        OpsMail opsMail = new OpsMail();
        //附件地址
        opsMail.setFileUrls(fileUrls);
        //初始状态是0
        opsMail.setStatus(SendStatusEnum.INIT.getType());
        //抄送
        opsMail.setCc(cc);
        //如果包含分号，替换逗号
        opsMail.setMailTo(mailAddress.replaceAll(";",","));
        //主题
        String beginTime = DateUtil.getYearMonth(DateUtil.addMonth(DateUtil.getNow(),-1));
        opsMail.setSubject("客单转定和还原报表 " +beginTime);
        StringBuffer con = new StringBuffer();
        con.append("<h4> ");
        con.append("请查看附件");
        con.append(" </h4>");
        //邮件内容
        opsMail.setContext(con.toString());
        //发件人名称 bugid:12173 c14717 20230920
        //opsMail.setNickName(nickName);
        opsExpdetailDao.insertMailData(opsMail);
    }



}
