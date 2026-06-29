package com.smc.ops.delivery.service.barcode;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.EmailBarcode;
import com.sales.ops.db.entity.ExpdetailBarcodeSend;
import com.sales.ops.db.entity.OpsBarcodeRuleConfig;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/6/7 11:06
 */
public interface ReadOpsService {

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    void checkBarcodeRep(List<ExpdetailBarcodeSend> opsBarList);

    @Transactional(rollbackFor = Exception.class)
    Integer saveBarcodeNoUpTime(List<ExpdetailBarcodeSend> opsBarList, String lastSyncTime, Integer ruID) throws OpsException;

    @Transactional(rollbackFor = Exception.class)
    Integer saveBarcode(List<ExpdetailBarcodeSend> opsBarList, String lastSyncTime, Integer ruID)  throws OpsException;

    @Transactional(rollbackFor = Exception.class)
    Integer updateBarcodeRule(String lastSyncTime, Integer ruID) throws OpsException;

    @Transactional(rollbackFor = Exception.class)
    void updateExpdetailBarcodeSend(List<ExpdetailBarcodeSend> upTimeNonList)  throws OpsException;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<ExpdetailBarcodeSend> findOpsExpList(String doId, String barW);

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<ExpdetailBarcodeSend> findOpsExpSendByUpTimeList();

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<ExpdetailBarcodeSend> findOpsExpBarList(List<String> list, String beginTime, String endTime);

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<ExpdetailBarcodeSend> findOpsBarList(List<String> list, String beginTime, String endTime);

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<OpsBarcodeRuleConfig> findBarRule();


    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<String> findCusRuleCusNos(Integer ruID);

    //bugid:12391 c14717 2023/10/31 查询expBarcode表和 expdetail表 email有值的数据 同步数据
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<EmailBarcode> findExpBarAndExpdetailHaveEmail(String beginTime, String endTime);

    //
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    Integer countExpBarAndExpdetailHaveEmail(String beginTime, String endTime);

    //bugid:12391 c14717 2023/10/31 根据箱码信息验重
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    void checkEmailBarcodeRep(List<EmailBarcode> opsBarList);

    //bugid:12391 c14717 2023/10/31
    @Transactional(rollbackFor = Exception.class)
    Integer saveEmailBarcodeNoUpTime(List<EmailBarcode> opsBarList) throws OpsException;

    //bugid:12391 c14717 2023/10/31
    @Transactional(rollbackFor = Exception.class)
    Integer saveEmailBarcode(List<EmailBarcode> opsBarList) throws OpsException;

    //bugid:12391 c14717 2023/10/31 查询update为空的数据
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<EmailBarcode> findOpsEmailBarNotUptimeList();

    //bugid:12391 c14717 2023/10/31
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<EmailBarcode> findEmailBarExpList(String doId, String barW);

    //bugid:12391 c14717 2023/10/31 更新updateTime为空的数据
    @Transactional(rollbackFor = Exception.class)
    void updateEmailBar(List<EmailBarcode> upTimeNonList)  throws OpsException;
}
