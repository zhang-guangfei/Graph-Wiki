package com.smc.ops.delivery.service.barcode;

import com.sales.ops.db.entity.EmailBarcode;
import com.sales.ops.db.entity.ExpdetailBarcodeSend;
import com.sales.ops.db.entity.RuleCustomerBing;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/6/7 12:14
 */
public interface SendBarcodeService {

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<ExpdetailBarcodeSend> findBarByCustomerNO(List<RuleCustomerBing> list, String startTime, String endTime);

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    Map<Integer,List<RuleCustomerBing>> groupByCurRule();

    @Transactional(rollbackFor = Exception.class)
    void saveMailToDb(String fileUrls, String mailAddress, String subject);

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<String> findBarcodeListGroup(String startTime, String endTime);

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<EmailBarcode> findListBarcodeDateByEmail(String mail, String startTime, String endTime);
}
