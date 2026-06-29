package com.smc.ops.delivery.service.barcode.impl;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.db.entity.EmailBarcode;
import com.sales.ops.db.entity.ExpdetailBarcodeSend;
import com.sales.ops.db.entity.OpsBarcodeRuleConfig;
import com.smc.ops.delivery.mapper.EmailBarcodeDao;
import com.smc.ops.delivery.mapper.ExpdetailBarcodeSendDao;
import com.smc.ops.delivery.mapper.OpsExpdetailDao;
import com.smc.ops.delivery.service.barcode.ReadOpsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/6/7 11:10
 */
@Service
public class ReadOpsServiceImpl implements ReadOpsService {

    @Autowired
    private OpsExpdetailDao opsExpdetailDao;

    @Autowired
    private ExpdetailBarcodeSendDao expdetailBarcodeSendDao;

    @Autowired
    private EmailBarcodeDao emailBarcodeDao;

    /**
     * 检查数据是否重复，重复从列表中删除
     *
     * @param opsBarList
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void checkBarcodeRep(List<ExpdetailBarcodeSend> opsBarList){
        boolean checkFlag = true;
        List<ExpdetailBarcodeSend> removeList = new ArrayList<ExpdetailBarcodeSend>();
        for(ExpdetailBarcodeSend param : opsBarList){
            if(checkFlag){
                Integer count = expdetailBarcodeSendDao.checkBarcodeRep(param.getBarcodeW(),param.getBarcodeN());
                if(Objects.nonNull(count)){
                    if(count > 0){
                        removeList.add(param);
                    }else if(count == 0){//正序排序，如果存在不重复的数据，后边的数据都不会重复;
                        checkFlag = false;
                    }
                }
            }
            param.setCreateTime(DateUtil.getNow());
            param.setCreateUser("sys");
        }
        //批量删除重复的数据
        if(CollectionUtils.isNotEmpty(removeList)){
            opsBarList.removeAll(removeList);
        }
    }

    /**
     * 批量写入表，不包含更新时间
     * @param opsBarList
     * @param lastSyncTime
     * @param ruID
     * @return
     * @throws OpsException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer saveBarcodeNoUpTime(List<ExpdetailBarcodeSend> opsBarList,String lastSyncTime,Integer ruID) throws OpsException{
        Integer count = 0;
        Map<Integer, List<ExpdetailBarcodeSend>> mapBarcode = SplitBatchUtils.getInsertBatchBySqlserver(opsBarList, ExpdetailBarcodeSend.class);
        for (Map.Entry<Integer, List<ExpdetailBarcodeSend>> entry : mapBarcode.entrySet()) {
            count += expdetailBarcodeSendDao.batchInsertBarcode(entry.getValue());
        }
        return count;
    }

    /**
     * 批量写入表，包含更新时间
     * @param opsBarList
     * @param lastSyncTime
     * @param ruID
     * @return
     * @throws OpsException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer saveBarcode(List<ExpdetailBarcodeSend> opsBarList,String lastSyncTime,Integer ruID) throws OpsException{
        Integer count = 0;
        Map<Integer, List<ExpdetailBarcodeSend>> mapBarcode = SplitBatchUtils.getInsertBatchBySqlserver(opsBarList, ExpdetailBarcodeSend.class);
        for (Map.Entry<Integer, List<ExpdetailBarcodeSend>> entry : mapBarcode.entrySet()) {
            count += expdetailBarcodeSendDao.batchInsert(entry.getValue());
        }
        return count;
    }

    /**
     * 更新规则同步时间
     * @param lastSyncTime
     * @param ruID
     * @return
     * @throws OpsException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateBarcodeRule(String lastSyncTime, Integer ruID)  throws OpsException{
       return expdetailBarcodeSendDao.updateBarcodeRule(lastSyncTime,ruID);
    }


    /**
     * 更新expdetail数据
     * @return
     * @throws OpsException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateExpdetailBarcodeSend(List<ExpdetailBarcodeSend> upTimeNonList)  throws OpsException{
        //同步数据
        if(CollectionUtils.isNotEmpty(upTimeNonList)){
            for(ExpdetailBarcodeSend objSend : upTimeNonList){
                List<ExpdetailBarcodeSend> opsExpList = findOpsExpList(objSend.getDoId(), objSend.getBarcodeW());
                if(CollectionUtils.isNotEmpty(opsExpList)){
                    opsExpList.get(0).setBarcodeN(objSend.getBarcodeN());
                    opsExpList.get(0).setUpdateTime(DateUtil.getNow());
                    opsExpList.get(0).setUpdateUser("sys");
                    expdetailBarcodeSendDao.updateExpdetailBarcodeSend(opsExpList.get(0));
                }
            }
        }
    }


    /**
     * 查询expdetail
     * @param doId
     * @param barW
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<ExpdetailBarcodeSend> findOpsExpList(String doId, String barW){
        return opsExpdetailDao.findListExpdetail(doId,barW);
    }

    /**
     * 查询ExpdetailBarcodeSend updateTime is null
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<ExpdetailBarcodeSend> findOpsExpSendByUpTimeList(){
        return expdetailBarcodeSendDao.findListBarcodeByBar();
    }


    /**
     * 查询barcode数据
     * @param list
     * @param beginTime
     * @param endTime
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<ExpdetailBarcodeSend> findOpsExpBarList(List<String> list, String beginTime, String endTime){
        return opsExpdetailDao.findListExpDetailBarcodeDate(list,beginTime,endTime);
    }

    /**
     *
     * 查询barcode关联expdetail数据
     * @param list
     * @param beginTime
     * @param endTime
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<ExpdetailBarcodeSend> findOpsBarList(List<String> list, String beginTime, String endTime){
        return opsExpdetailDao.findListBarcodeDate(list,beginTime,endTime);
    }

    /**
     * 查询barcode规则
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<OpsBarcodeRuleConfig> findBarRule(){
        return expdetailBarcodeSendDao.findBarRule();
    }


    /**
     * 查询箱单数据发送规则
     * @param ruID
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<String> findCusRuleCusNos(Integer ruID){
        return expdetailBarcodeSendDao.findCusRuleCusNos(ruID);
    }


    /**
     * bugid:12391 c14717 2023/10/31
     * 查询expBarcode表和 expdetail表 email有值的数据 同步数据
     *
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<EmailBarcode> findExpBarAndExpdetailHaveEmail(String beginTime, String endTime){
        return opsExpdetailDao.findExpBarAndExpdetailHaveEmail(beginTime,endTime);
    }

    //
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Integer countExpBarAndExpdetailHaveEmail(String beginTime, String endTime){
        return opsExpdetailDao.countExpBarAndExpdetailHaveEmail(beginTime,endTime);
    }

    /**
     * bugid:12391 c14717 2023/10/31
     * 检查数据是否重复，重复从列表中删除
     * @param opsBarList
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void checkEmailBarcodeRep(List<EmailBarcode> opsBarList){
        boolean checkFlag = true;
        List<EmailBarcode> removeList = new ArrayList<EmailBarcode>();
        for(EmailBarcode param : opsBarList){
            if(checkFlag){
                //根据箱码信息验重
                Integer count = emailBarcodeDao.checkBarcodeRep(param.getBarcodeW(),param.getBarcodeN());
                if(Objects.nonNull(count)){
                    if(count > 0){
                        removeList.add(param);
                    }else if(count == 0){//正序排序，如果存在不重复的数据，后边的数据都不会重复;
                        checkFlag = false;
                    }
                }
            }
            param.setCreateTime(DateUtil.getNow());
            param.setCreateUser("sys");
        }
        //批量删除重复的数据
        if(CollectionUtils.isNotEmpty(removeList)){
            opsBarList.removeAll(removeList);
        }
    }

    /**
     * bugid:12391 c14717 2023/10/31
     * 批量写入表，不包含更新时间
     * @param opsBarList

     * @return
     * @throws OpsException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer saveEmailBarcodeNoUpTime(List<EmailBarcode> opsBarList) throws OpsException{
        Integer count = 0;
        Map<Integer, List<EmailBarcode>> mapBarcode = SplitBatchUtils.getInsertBatchBySqlserver(opsBarList, EmailBarcode.class);
        for (Map.Entry<Integer, List<EmailBarcode>> entry : mapBarcode.entrySet()) {
            count += emailBarcodeDao.batchInsertNoUpTime(entry.getValue());
        }
        return count;
    }

    /**
     * bugid:12391 c14717 2023/10/31
     * 批量写入表，包含更新时间
     * @param opsBarList

     * @return
     * @throws OpsException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer saveEmailBarcode(List<EmailBarcode> opsBarList) throws OpsException{
        Integer count = 0;
        Map<Integer, List<EmailBarcode>> mapBarcode = SplitBatchUtils.getInsertBatchBySqlserver(opsBarList, EmailBarcode.class);
        for (Map.Entry<Integer, List<EmailBarcode>> entry : mapBarcode.entrySet()) {
            count += emailBarcodeDao.batchInsert(entry.getValue());
        }
        return count;
    }

    /**
     * bugid:12391 c14717 2023/10/31
     * 查询ExpdetailBarcodeSend updateTime is null
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<EmailBarcode> findOpsEmailBarNotUptimeList(){
        return emailBarcodeDao.findEmailBarcodeByUpdateTimeIsNull();
    }

    /**
     * 查询expdetail
     * @param doId
     * @param barW
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<EmailBarcode> findEmailBarExpList(String doId, String barW){
        return opsExpdetailDao.findListEmailExpdetail(doId,barW);
    }

    /**
     * bugid:12391 c14717 2023/10/31
     * 更新expdetail数据
     * @return
     * @throws OpsException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateEmailBar(List<EmailBarcode> upTimeNonList)  throws OpsException{
        //同步数据
        if(CollectionUtils.isNotEmpty(upTimeNonList)){
            for(EmailBarcode objSend : upTimeNonList){
                List<EmailBarcode> opsExpList = findEmailBarExpList(objSend.getDoId(), objSend.getBarcodeW());
                if(CollectionUtils.isNotEmpty(opsExpList)){
                    opsExpList.get(0).setBarcodeN(objSend.getBarcodeN());
                    opsExpList.get(0).setUpdateTime(DateUtil.getNow());
                    opsExpList.get(0).setUpdateUser("sys");
                    emailBarcodeDao.updateExpdetailBarcodeSend(opsExpList.get(0));
                }
            }
        }
    }
}
