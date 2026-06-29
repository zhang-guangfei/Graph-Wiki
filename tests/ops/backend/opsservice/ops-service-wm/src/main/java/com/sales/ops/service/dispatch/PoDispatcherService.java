package com.sales.ops.service.dispatch;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.purchase.OpsPurchaseStatusToWMDto;
import com.sales.ops.dto.purchase.PoToWmDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author C12961
 * @date 2023/2/17 10:55
 */
public interface PoDispatcherService {
    void resetForPo(PoToWmDto poDto) throws OpsException;

    void preprocessForRequestPo(List<OpsRequestpurchase> list) throws OpsException;

    void interceptForRequestPo(List<OpsRequestpurchase> list) throws OpsException;

    void releaseForRequestPo(List<OpsRequestpurchase> list) throws OpsException;

    void sendForPo(List<OpsPurchaseStatusToWMDto> list) throws OpsException;

    void acceptForPo(PoToWmDto dto) throws OpsException;

    @Transactional(rollbackFor = Exception.class)
    void invoiceImport(List<OpsPurchaseinvoice> list) throws OpsException;
}
