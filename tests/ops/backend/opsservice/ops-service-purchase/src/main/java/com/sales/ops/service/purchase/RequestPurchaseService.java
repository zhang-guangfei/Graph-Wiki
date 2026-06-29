package com.sales.ops.service.purchase;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsPoTranstype;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.RequestpurchaseView;
import com.sales.ops.db.entity.Supplier;
import com.sales.ops.dto.po.PoMergeRuleConfigDto;
import com.sales.ops.dto.purchase.OpsPurchaseStatusToWMDto;
import com.sales.ops.dto.purchase.OpsRequestpurchaseDto;
import com.sales.ops.dto.purchase.RequestFilterCondition;
import com.sales.ops.dto.purchase.SplitCondition;
import com.sales.ops.dto.query.RequestPurchaseQO;
import com.sales.ops.dto.util.PageModel;

public interface RequestPurchaseService {

	// bug15266 自动发送合并订单，提出公共方法
	List<OpsPurchaseStatusToWMDto> mergeToPurchase(List<OpsRequestpurchaseDto> merge, String type);

	// bug11473 马腾 自动发单，提取出公用方法
	public OpsPurchaseorder generatePurchaseOrder(OpsRequestpurchase o, OpsPurchaseorder opsPurchaseorder);

	void updateMergeConfig(List<PoMergeRuleConfigDto> list) throws OpsException;

	List<PoMergeRuleConfigDto> getMergeConfig();

    HashMap<String,Integer> getMergeConfigMap() throws OpsException;

    // bug11473 马腾自动发单提出公共可用方法
	public JSONObject operateCalMerge(List<OpsRequestpurchase> requestPurchaseList) throws OpsException;

	/**
	 * 条件查询，请购单查询页面
	 */
	public PageInfo<OpsRequestpurchase> findAll(PageModel<RequestPurchaseQO> pageModel);

	/**
	 * 条件查询，请购单查询页面
	 */
	public PageInfo<RequestpurchaseView> findPreProccessList(PageModel<RequestPurchaseQO> pageModel);

	/**
	 * shikomi拦截，查询
	 */
	public PageInfo<OpsRequestpurchase> findShikomiList(PageModel<RequestPurchaseQO> pageModel);

	/**
	 * shikomi拦截，状态变更
	 */
	public Integer shikomiPass(OpsRequestpurchase opsRequestpurchase) throws OpsException;

	/**
	 * 拦截放行功能
	 * 
	 * @param
	 * @return
	 */
	public Integer requestPass(OpsRequestpurchase opsRequestpurchase, Boolean isBatch) throws OpsException;

	/**
	 * 拦截订单批量放行功能
	 *
	 * @param
	 * @return
	 */
	public Integer requestPassBatch(List<OpsRequestpurchase> list) throws OpsException;

	/**
	 * 请购单拦截，还原功能
	 */
	public Integer restore(OpsRequestpurchase opsRequestpurchase) throws OpsException;

	/**
	 * bug12272 批量订单还原
	 * 
	 * @return
	 */
	public Integer restoreBatch(List<OpsRequestpurchase> list) throws OpsException;

	/**
	 * 更新变更供应商，运输方式，交货期
	 * 
	 * @return
	 */
	public Integer updateSuppilyTrans(OpsRequestpurchase opsRequestpurchase) throws OpsException;

	/**
	 * 请购单批量编辑功能
	 */
	public Integer updateRequestPurchaseBatch(List<OpsRequestpurchase> list) throws OpsException;

	/**
	 * 根据订单号，查询请购单详情
	 */
	public List<OpsRequestpurchase> getByOrderNo(String OrderNo);

	/**
	 * 合并推荐采购查询方法
	 * 
	 * @return
	 */
	public JSONObject mergePurchase() throws Exception;

	/**
	 * 拆分采购
	 */
	public List<OpsRequestpurchaseDto> splitPurchase(SplitCondition condition);

	/**
	 * 手工合并采购
	 */
	public List<OpsRequestpurchaseDto> artificialMerge(List<OpsRequestpurchaseDto> list) throws OpsException;

	/**
	 * 后端筛选方法
	 */
	public RequestFilterCondition filterChange(RequestFilterCondition condition) throws OpsException;

	/**
	 * 执行采购单条采购
	 */
	public Integer executePurchaseOne(List<OpsRequestpurchaseDto> list) throws Exception;

	/**
	 * 执行采购方法合并采购
	 */
	public Integer executePurchaseMerge(List<OpsRequestpurchaseDto> list, String type) throws Exception;

	/**
	 * 查找供应商
	 */
	public List<Supplier> findSuppily();

	/**
	 * 查找供应商
	 */
	public List<OpsPoTranstype> findTrans();

	/**
	 * 变更待处理清单状态到待采购状态 批量接口
	 */
	public Integer requestConfirm(List<OpsRequestpurchase> list);

	/**
	 * 变更待处理清单状态到待采购状态 单条接口
	 */
	public Integer requestConfirmOne(OpsRequestpurchase opsRequestpurchase);

	/**
	 * 确认全部订单
	 */
	public Integer ConfirmAll(RequestPurchaseQO ops) throws OpsException;

	/**
	 * 请购直发采购
	 */
	public Integer toPurchase(RequestPurchaseQO ops) throws Exception;

	/**
	 * 获取可用Shikomi列表
	 */
	public List<String> getShikomiList();

	/**
	 * 拦截处理更新到备注信息中
	 */
	public Integer editInterceptRemark(OpsRequestpurchase opsRequestpurchase);

	/**
	 * 后端筛选方法
	 */
	public RequestFilterCondition sortChange(RequestFilterCondition condition) throws OpsException;

}
