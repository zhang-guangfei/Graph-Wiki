package com.sales.ops.dto.ips;

import com.sales.ops.db.entity.IpsReceiveInvoiceMasterFromAll;
import com.sales.ops.db.entity.IpsReceiveSignImpInfoFromAll;

import java.io.Serializable;
import java.util.List;

/**
 * bug 19413.38 批量推送签收信息VO
 * 将发票主表和发票明细表一起推送，使用同一个接口和同一个事务
 */
public class IpsSignDataBatchPushVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发票主表数据
     */
    private IpsReceiveInvoiceMasterFromAll invoiceMaster;

    /**
     * 签收明细列表（批量）
     */
    private List<IpsReceiveSignImpInfoFromAll> signList;

    public IpsReceiveInvoiceMasterFromAll getInvoiceMaster() {
        return invoiceMaster;
    }

    public void setInvoiceMaster(IpsReceiveInvoiceMasterFromAll invoiceMaster) {
        this.invoiceMaster = invoiceMaster;
    }

    public List<IpsReceiveSignImpInfoFromAll> getSignList() {
        return signList;
    }

    public void setSignList(List<IpsReceiveSignImpInfoFromAll> signList) {
        this.signList = signList;
    }
}
