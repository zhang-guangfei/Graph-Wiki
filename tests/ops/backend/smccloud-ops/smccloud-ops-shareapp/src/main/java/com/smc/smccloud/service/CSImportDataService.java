package com.smc.smccloud.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.smc.smccloud.model.csstock.CsImpdataDO;
import com.smc.smccloud.model.csstock.CsImpdataVO;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-07-30 17:18
 * Description:
 */
public interface CSImportDataService {

    boolean saveCSImportData(CsImpdataDO csImpdataDO);

    List<CsImpdataVO> listCsImpdata();

    /**
     * 检查委托在库发票号是否已经存在
     */
    boolean checkCSInvoice(String warehouseCode, String invoiceNo);

    /**
     * 更新出库数量和剩余数量
     *
     * @param dataList 委托在库入库数据
     * @param stockQty 库存在库数
     */
    void calcExpQtyAndLeftQty(List<CsImpdataDO> dataList, int stockQty);

    List<CsImpdataDO> listCSImportDataByWrapper(Wrapper<CsImpdataDO> queryWrapper);

    boolean updateCSImportDataByWrapper(CsImpdataDO entity, Wrapper<CsImpdataDO> updateWrapper);
}
