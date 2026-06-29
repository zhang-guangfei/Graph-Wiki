package com.sales.ops.service.purchase;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.po.PoOrderNoDto;
import com.sales.ops.dto.purchase.DlvModDateDto;
import com.sales.ops.dto.purchase.ImpDataDto;
import com.sales.ops.dto.query.PurchaseQO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;

import java.util.List;
import java.util.Map;

public interface PurchaseService {

    /**
     * 条件查询，采购单查询页面
     */
    public PageInfo<PurchaseorderView> findAll(PageModel<PurchaseQO> pageModel);

    /**
     * 采购单转定，更换供应商
     * @param opsPurchaseorder
     * @return
     * @throws OpsException
     */
    public Integer updateTrans(OpsPurchaseorder opsPurchaseorder) throws OpsException;

    /**
     * 更新交货期，只限供应商为日本的
     * @param opsPurchaseorder
     * @return
     * @throws OpsException
     */
    public Integer updateDeliveryDate(OpsPurchaseorder opsPurchaseorder) throws OpsException;

    /**
     *采购单查询-采购单详情
     * @param id
     * @return
     */
    public  OpsPurchaseorder getByid(Long id);

    /**
     *采购单详情-获取请购单信息
     */
    public List<OpsRequestpurchase> getRequest(Long id) throws OpsException;

    /**
     * 强制完纳功能
     */
    public void compel(OpsPurchaseorder opsPurchaseorder) throws OpsException;

    List<ImpDataDto> getGoodsDetailByOrder(PoOrderNoDto param) throws OpsException;

    /**
     * 查询到货信息
     * @param id
     * @return
     * @throws OpsException
     */
    public List<ImpDataDto> getGoodsDetail(Long id) throws  OpsException;


    public CommonResult<String> apiRepo(Map<String, Object> list) throws OpsException;

    List<DlvModDateDto> getDlvModDate(String orderNo, Integer itemNo, Integer splitItemNo) throws OpsException;

    /**
     * @author B91717
     * @param request
     * @param id
     * @return
     * 2022-11-03 B91717 请购编辑页面，更新供应商，smccode等信息
     */
    public Integer updateRequestEdit(OpsRequestpurchase request,Long id)  throws OpsException;

}
