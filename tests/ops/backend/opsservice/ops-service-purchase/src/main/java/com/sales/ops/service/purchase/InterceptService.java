package com.sales.ops.service.purchase;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsRequestpurchaseInterceptConfig;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.dto.purchase.OpsRequestPurchaseInterceptConfigVO;
import com.sales.ops.dto.util.PageModel;

import java.util.List;

/**
 * @author B91717
 * @date 2021/12/24
 * @apiNote
 */
public interface InterceptService {

    /**
     * 条件查询，自定义拦截清单查询
     */
    public PageInfo<OpsRequestpurchaseInterceptConfig> findAll(PageModel<OpsRequestPurchaseInterceptConfigVO> pageModel);


    Integer  updateData(OpsRequestpurchaseInterceptConfig opsRequestpurchaseInterceptConfig) throws OpsException;

    Integer insertData(OpsRequestpurchaseInterceptConfig opsRequestpurchaseInterceptConfig) throws OpsException;

    void  deleteData(OpsRequestpurchaseInterceptConfig opsRequestpurchaseInterceptConfig) throws OpsException;

    List<OpsWarehouse> findWarehouse();

    /**
     * 批量编辑功能
     *  bug 10559 拦截配置 增加筛选，批量修改，删除，导出功能 B91717
     * @param list
     * @return
     * @throws OpsException
     */
    Integer  updateDataBatch(List<OpsRequestpurchaseInterceptConfig> list) throws OpsException;
 // bug 10559 拦截配置 增加筛选，批量修改，删除，导出功能 B91717
    Integer deleteBatch(List<OpsRequestpurchaseInterceptConfig> list) throws OpsException;

}
