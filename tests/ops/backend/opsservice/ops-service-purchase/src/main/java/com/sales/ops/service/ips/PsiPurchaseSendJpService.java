package com.sales.ops.service.ips;

import com.sales.ops.db.entity.OpsPurchaseorder;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.List;

/**
 * @author B91717
 * 向IPS发采购单时，日本订单的发送
 */
public interface PsiPurchaseSendJpService {



    /**
     * 日本订单向psi发单
     */
    public ResultVo<String> psiJpOrderSend(List<OpsPurchaseorder> purchaseorders, String likeString, String email) throws Exception;


}
