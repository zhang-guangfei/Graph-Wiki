package com.sales.ops.service.purchase;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsRequestpurchase;

import java.util.List;

/**
 * @author B91717
 * 请购处理界面相关操作 service
 */
public interface RequestHandleService {
    /**
     * bug 11997
     * 采购自动发单，暂不处理功能
     */
    public Integer requestIntercept(List<OpsRequestpurchase> list) throws OpsException;
}
