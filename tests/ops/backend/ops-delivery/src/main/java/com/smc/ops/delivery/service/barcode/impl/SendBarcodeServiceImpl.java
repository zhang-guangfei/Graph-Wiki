package com.smc.ops.delivery.service.barcode.impl;

import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.entity.EmailBarcode;
import com.sales.ops.db.entity.ExpdetailBarcodeSend;
import com.sales.ops.db.entity.OpsMail;
import com.sales.ops.db.entity.RuleCustomerBing;
import com.sales.ops.enums.SendStatusEnum;
import com.smc.ops.delivery.mapper.EmailBarcodeDao;
import com.smc.ops.delivery.mapper.ExpdetailBarcodeSendDao;
import com.smc.ops.delivery.mapper.OpsExpdetailDao;
import com.smc.ops.delivery.service.barcode.SendBarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/6/7 12:15
 */
@Service
public class SendBarcodeServiceImpl implements SendBarcodeService {

    @Autowired
    private ExpdetailBarcodeSendDao expBarSendDao;

    @Autowired
    private OpsExpdetailDao opsExpdetailDao;

    @Autowired
    private EmailBarcodeDao emailBarcodeDao;


    /**
     * 分组查询数据
     * @param list
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<ExpdetailBarcodeSend> findBarByCustomerNO(List<RuleCustomerBing> list,String startTime,String endTime){
        return expBarSendDao.findListBarcodeDate(list,startTime,endTime);
    }

    /**
     * 查询发送规则 groupby 分组
     * @param
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Map<Integer,List<RuleCustomerBing>> groupByCurRule(){
        Map<Integer,List<RuleCustomerBing>> map = new HashMap<Integer,List<RuleCustomerBing>>();
        List<RuleCustomerBing> cusRules = expBarSendDao.findCusRule();
        for(RuleCustomerBing obj : cusRules){
            if(map.containsKey(obj.getGroupId())){
                map.get(obj.getGroupId()).add(obj);
            }else {
                List<RuleCustomerBing> listOne =  new ArrayList<RuleCustomerBing>();
                listOne.add(obj);
                map.put(obj.getGroupId(),listOne);
            }
        }
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMailToDb(String fileUrls, String mailAddress, String subject){
        //保存邮件到数据库
        OpsMail opsMail = new OpsMail();
        //附件地址
        opsMail.setFileUrls(fileUrls);
        //opsMail.setMailFrom(mail.getFrom());
        //初始状态是0
        opsMail.setStatus(SendStatusEnum.INIT.getType());
        //抄送
        //opsMail.setCc(mailAddress.getExceptionHandleCC());
        //如果包含分号，替换逗号
        opsMail.setMailTo(mailAddress.replaceAll(";",","));
        //主题
        Date yesterday = DateUtil.addDay(DateUtil.getNow(), -1);
        String beginTime = DateUtil.dateToDateString(DateUtil.getBeginTime(yesterday));
        opsMail.setSubject(subject+" " +beginTime);
        StringBuffer con = new StringBuffer();
        con.append("<h4> ");
        con.append("请查看附件");
        con.append(",附件为贵司");
        con.append("出库箱码明细");
        con.append(" </h4>");
        //邮件内容
        opsMail.setContext(con.toString());
        //发件人名称 bugid:12173 c14717 20230920
        //opsMail.setNickName(nickName);
        opsExpdetailDao.insertMailData(opsMail);
    }

    /**
     * 获取分组邮件-去重
     * //bugid:12391 c14717 2023/10/31
     * @param startTime
     * @param endTime
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<String> findBarcodeListGroup(String startTime, String endTime){
        List<String> barcodeListGroupList = emailBarcodeDao.findBarcodeListGroup(startTime, endTime);
        return barcodeListGroupList.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 通过邮件 查找详细信息
     * bugid:12391 c14717 2023/10/31
     * @param mail
     * @param startTime
     * @param endTime
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<EmailBarcode> findListBarcodeDateByEmail(String mail, String startTime, String endTime){
       return emailBarcodeDao.findListBarcodeDateByEmail(startTime,endTime,mail);
    }
}
