package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.returnorder.RcvOrderReGisterDTO;
import com.smc.smccloud.model.returnorder.ReturnOrderDO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2022-02-05
 */
public interface SalesInvoiceService {

    ResultVo<String> insertSalesInvoiceMidInfo(ReturnOrderDO returnOrderDO, RcvOrderReGisterDTO reGisterDTO);
}
