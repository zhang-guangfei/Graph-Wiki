package com.sales.ops.serviceimpl.purchase;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.nacos.common.utils.Objects;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.dao.OpsPurchaseErrorLogMapper;
import com.sales.ops.db.dao.OpsPurchaseinvoiceMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.po.RequestAndPurchaseDto;
import com.sales.ops.dto.purchase.PurchaseCancelEventParam;
import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.dto.purchase.RequestCancelDto;
import com.sales.ops.dto.purchase.ToDtoUtil;
import com.sales.ops.enums.*;
import com.sales.ops.event.publisher.entity.OrderNoInfo;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.service.order.state.OrderStateService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.purchase.PurchaseCancelService;
import com.sales.ops.service.purchase.PurchaseCtcDelService;
import com.sales.ops.service.purchase.PurchaseSendEmail;
import com.sales.ops.serviceimpl.event.v3.PurchaseEventPublisher;
import com.sales.ops.utils.PoNoUtil;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.binorder.ModelExpFreqVO;
import com.smc.smccloud.model.shikomi.ShikomiPrepareDTO;
import com.smc.smccloud.service.BinServiceFeignApi;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import com.smc.smccloud.service.PreStockFeignApi;
import com.smc.smccloud.service.ProductServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseCancelServiceImpl implements PurchaseCancelService {

	@Autowired
	private OpsPurchaseinvoiceMapper opsPurchaseinvoiceMapper;
	@Resource
	private PurchaseCtcDelService purchaseCtcDelService;
	@Autowired
	private OrderStateService orderStateService;
	@Autowired
	private BasePoService basePoService;

	@Autowired
	private OpsPurchaseErrorLogMapper opsPurchaseErrorLogMapper;
	@Autowired
	private PurchaseSendEmail purchaseSendEmail;
	@Autowired
	private BinServiceFeignApi binServiceFeignApi;
	@Autowired
	private PurchaseEventPublisher purchaseEventPublisher;
	@Autowired
	private ProductServiceFeignApi productServiceFeignApi;
	@Autowired
	private PreStockFeignApi preStockFeignApi;
	@Autowired
	private DictDataServiceFeignApi dictDataServiceFeignApi;
	public static final String DICT_CLASS_CODE = "9002";
	public static String DICT_CODE = "34";
	/**
	 * @description 查询采购单信息给客户删单界面展示 输入客户单号，返回客户订单关联的请购单和采购单，不包括客户单预约的补库单
	 * @author C12961
	 * @date 2023/3/22 16:31
	 */
	@Override
	public List<PurchaseInfoForCancel> getPurchase(String orderNo, int itemNo) throws OpsException {
		List<PurchaseInfoForCancel> result = new ArrayList<>();
		List<OpsRequestpurchase> requestPurchases = basePoService.getRequestPurchasesByRcvNo(orderNo, itemNo);
		for (OpsRequestpurchase request : requestPurchases) {
			OpsPurchaseorder purchase = basePoService.getPurchaseByRequestPurchase(request.getOrderno(),
					request.getItemno(), request.getSplititemno());
			PurchaseInfoForCancel deleteInfo = createPurchaseInfoForCancel(request, purchase);
			result.add(deleteInfo);
		}
		return result;
	}

	// bug10700 查询采购子项方法
	@Override
	public List<PurchaseInfoForCancel> getPurchaseItem(String orderNo, int itemNo, Integer splitNo)
			throws OpsException {
		List<PurchaseInfoForCancel> result = new ArrayList<>();
		OpsRequestpurchase requestPurchases = basePoService.getRequestPurchase(orderNo, itemNo, splitNo);
		if (Objects.nonNull(requestPurchases)) {
			OpsPurchaseorder purchase = null;
			if (StringUtils.isNotBlank(requestPurchases.getPoOrderNo())) {
				purchase = basePoService.getPurchase(requestPurchases.getPoOrderNo(), requestPurchases.getPoItemNo(),
						requestPurchases.getPoSplitNo());
			}
			PurchaseInfoForCancel deleteInfo = createPurchaseInfoForCancel(requestPurchases, purchase);
			result.add(deleteInfo);
		}
		return result;
	}

	/**
	 * @description 创建返回实体 <采购单信息>
	 * @author C12961
	 * @date 2023/3/22 16:45
	 */
	private PurchaseInfoForCancel createPurchaseInfoForCancel(OpsRequestpurchase request, OpsPurchaseorder purchase) {
		PurchaseInfoForCancel info = new PurchaseInfoForCancel();
		info.setRequestno(request.getOrderno());
		info.setRequestItemno(request.getItemno());
		info.setRequestSplitno(request.getSplititemno());
		info.setModelno(request.getModelno());
		info.setRequestQuantity(request.getQuantity());
		info.setSupplierid(request.getSupplierid());
		info.setStatus(request.getStatecode());
		ResultVo<Boolean> isBin = binServiceFeignApi.isBinModel(request.getModelno());
		if (isBin.isSuccess() && isBin.getData()) {
			info.setBin(true);
		}
		if (purchase != null) {
			info.setPurchaseno(purchase.getOrderno());
			info.setPurchaseItemno(purchase.getItemno());
			info.setPurchaseSplitno(purchase.getSplititemno());
			info.setMerge(purchase.getMergeflag());
			info.setModelno(purchase.getModelno());
			info.setQuantity(purchase.getQuantity());
			info.setSupplierid(purchase.getSupplierid());
			info.setStatus(purchase.getStatecode());
			// bug9927 马腾 是否可删:1可删，2需人工处理，3不可删
			// bug14473 变更iscandelete为boolean类型对应数值，0不可删，1可删
			// if (StringUtils.equals(PurchaseOrderStatusEnum.YIJIEDAN,
			// purchase.getStatecode())) {
			// info.setIscandelete(2);
			// }
			// BUG 11986,采购删单 新增风险等级判断，返回给订单删单接口
			Integer risk = getPurchaseRisk(purchase, info.isBin());
			if (risk != null) {
				info.setRisk(risk);
			}
		} else if (!StringUtils.equals(RequestPurchaseStatusEnum.AUTOREADY, request.getStatecode())) {
			// bug11473自动发单 请购表增加状态标识进入了自动发单流程了，此状态时不可删
			// bug9927 马腾 是否可删:1可删，2需人工处理，3不可删
			info.setIscandelete(1);
		}
		return info;
	}

	/**
	 * bug11986,自动删单的规则补充，增加风险字段的返回，流程如下： 1.校验采购单状态，如果是已接单、运输中、已完成状态 继续下面校验规则
	 * 2.校验是否为bin品（调用广州接口来校验），如果为 bin品 继续下面校验 3.如果 dlvModDate iS not null &&
	 * dlvModDate-getdate()<30 && quantity <= 0.5*自动化整体8月均（查询页面值），返回低风险（risk=1）
	 * 其余均为高风险(risk = 2) 运输中和已完成状态的 订单，不需要校验 返信纳期
	 *
	 * @param purchase
	 * @return
	 */
	private Integer getPurchaseRisk(OpsPurchaseorder purchase, boolean isBin) {
		// 1.首先判断采购单状态是否为 已接单、运输中，已完成
		if (PurchaseDeleteEnum.statusSet().contains(purchase.getStatecode())) {
			if (isBin) {
				// 调用接口 获取自动化8月 所有大仓的 AvgQtyOf8字段
				ResultVo<List<ModelExpFreqVO>> resultVo = binServiceFeignApi.getMasterFreq(purchase.getModelno());
				if (resultVo.isSuccess() && resultVo.getData() != null) {
					List<ModelExpFreqVO> list = resultVo.getData();
					// 对大仓AvgQtyOf8字段数据进行求和
					int avgQtyOf8 = list.stream().map(ModelExpFreqVO::getAvgQtyOf8)
							.reduce(BigDecimal.ZERO, BigDecimal::add).intValue();
					// 3.校验 制造最新返信在一个月内，且 订单数量小于等于 0.5*自动化整体8月均，返回低风险
					if (purchase.getQuantity() <= 0.5 * avgQtyOf8) {
						// bug 11986,只有已接单的状态 才校验返信纳期，别的状态不校验
						if (StringUtils.equals(PurchaseOrderStatusEnum.YIJIEDAN, purchase.getStatecode())) {
							if (purchase.getDlvmoddate() != null) {
								// 获取当前日期 与 采购返信纳期的 相差天数
								Long dateDiff = DateUtil.getDiffDay(DateUtil.getCurrentDate(),
										DateUtil.getDate(purchase.getDlvmoddate()));
								if (dateDiff < PurchaseDeleteEnum.MAXDATE.getCode()) {
									return PurchaseDeleteEnum.LOWRISK.getCode();
								}
							}
							return PurchaseDeleteEnum.HIGHRISK.getCode();
						}
						return PurchaseDeleteEnum.LOWRISK.getCode();
					}
				}
			}
		}
		return PurchaseDeleteEnum.HIGHRISK.getCode();
	}

	/**
	 * @description 客户订单删除采购单和请购单的服务，通过参数（取消类型）删除采购单和请购单
	 *              建议此方法专门给《客户订单画面删除采购单》调用，和《采购画面删除采购单》功能区分出来 1.采购模块删除请购单和采购单
	 *              2.发送事件和消息队列，通知订单状态管理和交货期管理 修改取消接口，直接删除请购及采购表单号，另存入日志记录表
	 */
	@Override
	public boolean cancelPurchase(RequestCancelDto requestCancelDto) throws Exception {
		// bug9927 马腾 取消类型更改为从何界面过来的请求：1采购删除界面过来的请求，为空则判断是否可删
		String cancelType = requestCancelDto.getCanceltype();// 取消类型（0：请购 ，1：采购）
		String sourceType = requestCancelDto.getSourceType();// 取消来源（0：自动删单，1：手动删单，2：订单还原）
		String orderNo = requestCancelDto.getOrderno();
		Integer itemNo = requestCancelDto.getItemno();
		Integer splitItemNo = requestCancelDto.getSplititemno();

		// bug10483 修改更新人直接从前端获取，B91717
		String operator = StringUtils.isBlank(requestCancelDto.getOperator()) ? "" : requestCancelDto.getOperator();
		// bug12344 新增删单原因，B91717
		String reason = StringUtils.isBlank(requestCancelDto.getReason()) ? "" : requestCancelDto.getReason();
		// 删除的请购单和采购单
		RequestAndPurchaseDto purchaseDto = null;

		// bug 9927 先校验采购是否可以删除,再操作删单 B91717
		purchaseValidation(orderNo, itemNo, splitItemNo, cancelType);

		// 1.下面是删单逻辑
		// bug9927 是否为采购删单界面过来的请求，删除采购单和请购单
		if (StringUtils.equals(cancelType, "1")) {
			purchaseDto = cancelPurchaseWithRequestPurchase(orderNo, itemNo, splitItemNo, operator, reason);
		} else {
			OpsPurchaseorder purchase = basePoService.getPurchaseByRequestPurchase(orderNo, itemNo, splitItemNo);
			// 非采购删除界面过来,判断请购单及采购单是否可删
			if (purchase == null) {
				// 删除请购单(移表)
				OpsRequestpurchase requestPurchase = cancelRequestPurchase(orderNo, itemNo, splitItemNo, operator,
						reason);
				purchaseDto = new RequestAndPurchaseDto(requestPurchase);
			} else {
				// bug14937 此处应该传采购单号
				purchaseDto = cancelPurchaseWithRequestPurchase(purchase.getOrderno(), purchase.getItemno(),
						purchase.getSplititemno(), operator, reason);
			}
		}
		// 取消预约shikomi
		// 2.下面是发送消息和事件
		if (purchaseDto != null) {
			//获取开关 9002-34 是否更新先行在库状态 取消预约shikomi
			ResultVo<DataTypeVO> dict = dictDataServiceFeignApi.getDataTypeCodesInfo(DICT_CLASS_CODE, DICT_CODE);
			boolean success = dict.isSuccess();
			if (!success || dict.getData() == null) {
				throw Exceptions.OpsException("获取数据字典失败:" + dict.getMessage());
			}
			String extNote1 = dict.getData().getExtNote1();
			if (purchaseDto.existPurchase()) {
				// 2.1 发送事件给事件管理器:采购单和请购单删单
				// 空则为采购页面删单
				String cancelSource = Optional.ofNullable(sourceType).orElse(PurchaseCancelSourceEnum.CANCEL_PURCHASE.getType());
				PurchaseCancelEventParam param = new PurchaseCancelEventParam(cancelSource,purchaseDto.getPurchaseOrder(),purchaseDto.getRequestPurchaseList());
				OpsPurchaseorder purchase = purchaseDto.getPurchaseOrder();
				OrderNoInfo orderNoInfo = new OrderNoInfo(purchase.getOrderno(), purchase.getItemno(), purchase.getSplititemno());
				purchaseEventPublisher.purchaseOrderEvent(EventSourceEnum.PURCHASE_ORDER_CANCEL, orderNoInfo, param);
				// 2.2 发送MQ消息给交货期队列：采购单删除
				orderStateService.sendOrderStateForCancelPurchase(purchaseDto.getPurchaseOrder());
				String fullPoNo = PoNoUtil.getFullPoNo(purchase);
				//更新先行在库状态 取消预约shikomi
				//1 走旧队列 2 走新事件和同步更新
				if (extNote1.equals("2")) {
					try {
						ShikomiPrepareDTO dto = new ShikomiPrepareDTO();
						dto.setOrderNo(fullPoNo);
						dto.setShikomiNo(purchase.getShikomianswerno());
						dto.setSupplierCode(purchase.getSupplierid());
						dto.setOrderNo(purchase.getOrderno());
						dto.setQuantity(purchase.getQuantity());
						ResultVo<String> result =productServiceFeignApi.cancelPrepareShikomi(dto);
						if (!result.isSuccess()) {
							log.error("取消预约shikomi处理失败 {},{}", fullPoNo, result.getMessage());
						}
						result = preStockFeignApi.purchaseOrderCancelHandle(fullPoNo);
						if (!result.isSuccess()) {
							log.error("先行在库订单状态变更处理异常 {},{}", fullPoNo, result.getMessage());
						}
					} catch (Exception e) {
						log.error("先行在库订单状态变更处理异常 {},{}", fullPoNo, e.getMessage(), e);
					}
				}
			}
			// 2.1 如果只有请购没有采购，且没删单来源，发送事件给事件管理器:请购单删单
			else {
				if (purchaseDto.existRequestPurchase()) {
					for (OpsRequestpurchase opsRequestpurchase : purchaseDto.getRequestPurchaseList()) {
						String cancelSource = Optional.ofNullable(sourceType).orElse(PurchaseCancelSourceEnum.CANCEL_REQUEST_PURCHASE.getType());
						PurchaseCancelEventParam param = new PurchaseCancelEventParam(cancelSource,purchaseDto.getPurchaseOrder(),Collections.singletonList(opsRequestpurchase));
						OrderNoInfo orderNoInfo = new OrderNoInfo(opsRequestpurchase.getOrderno(), opsRequestpurchase.getItemno(), opsRequestpurchase.getSplititemno());
						purchaseEventPublisher.purchaseOrderEvent(EventSourceEnum.PURCHASE_ORDER_CANCEL, orderNoInfo, param);
					}
				}
			}
			if (purchaseDto.existRequestPurchase()) {
				// 2.2 发送MQ消息给交货期查询：请购单删除
				purchaseDto.getRequestPurchaseList().forEach(requestPurchaseMQ -> orderStateService
						.sendOrderStateForCancelRequestPurchase(requestPurchaseMQ));
				//更新先行在库状态 取消预约shikomi
				// 1 走旧队列 2 走新事件和同步更新
				if (extNote1.equals("2")) {
					for (OpsRequestpurchase requestpurchase : purchaseDto.getRequestPurchaseList()) {
						String fullPoNo = PoNoUtil.getFullRePoNo(requestpurchase);
						try {
							ShikomiPrepareDTO dto = new ShikomiPrepareDTO();
							dto.setOrderNo(fullPoNo);
							dto.setShikomiNo(requestpurchase.getShikomianswerno());
							dto.setSupplierCode(requestpurchase.getSupplierid());
							dto.setQuantity(requestpurchase.getQuantity());
							ResultVo<String> result =productServiceFeignApi.cancelPrepareShikomi(dto);
							if (!result.isSuccess()) {
								log.error("取消预约shikomi处理失败 {},{}", fullPoNo, result.getMessage());
							}
							result = preStockFeignApi.purchaseOrderCancelHandle(fullPoNo);
							if (!result.isSuccess()) {
								log.error("先行在库订单状态变更处理异常 {},{}", fullPoNo, result.getMessage());
							}
						} catch (Exception e) {
							log.error("先行在库订单状态变更处理异常 {},{}", fullPoNo, e.getMessage(), e);
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * @description 删除采购单和关联的请购单，返回删除内容 建议对外暴露成服务
	 * @author C12961
	 * @date 2023/3/22 16:44
	 */
	private RequestAndPurchaseDto cancelPurchaseWithRequestPurchase(String orderNo, Integer itemNo, Integer splitItemNo,
			String operator, String reason) throws Exception {
		// 查询采购单
		OpsPurchaseorder purchase = basePoService.getPurchase(orderNo, itemNo, splitItemNo);
		if (purchase == null) {
			throw Exceptions.OpsException("根据采购单号查询不到采购单信息:" + PoNoUtil.create(orderNo, itemNo, splitItemNo));
		}
		// 查询请购单，根据采购单号（自动识别合并采购单）
		List<OpsRequestpurchase> requestPurchaseList = basePoService.getRequestPurchaseByPurchase(orderNo, itemNo,
				splitItemNo);
		if (CollectionUtils.isEmpty(requestPurchaseList)) {
			throw Exceptions.OpsException("根据采购单号查询不到请购单信息:" + PoNoUtil.create(orderNo, itemNo, splitItemNo));
		}
		// bug 12344 opspurchaseOrder转为采购删单实体
		OpsPurchaseorderCancelLog opsPurchaseorderCancelLog = new OpsPurchaseorderCancelLog();
		opsPurchaseorderCancelLog = (OpsPurchaseorderCancelLog) ToDtoUtil.PoToDto(purchase, opsPurchaseorderCancelLog);
		// 重新为采购删单实体，赋值
		opsPurchaseorderCancelLog.setInserttime(new Date());
		opsPurchaseorderCancelLog.setStatecode(PurchaseOrderStatusEnum.SHANCHU);
		opsPurchaseorderCancelLog.setOperator(operator);
		opsPurchaseorderCancelLog.setDeletereason(reason);
		// 判断采购单状态能否删单
		// 删除采购单（移表）
		// basePoService.deletePurchase(purchase, operator);
		// 更换新的采购删单方法
		basePoService.deletePurchaseByCancelLog(opsPurchaseorderCancelLog);
		// 删除请购单
		for (OpsRequestpurchase requestPurchase : requestPurchaseList) {
			// bug 12344 opsrequestpurchase转为请购删单实体
			OpsRequestpurchaseCancelLog opsRequestpurchaseCancelLog = new OpsRequestpurchaseCancelLog();
			opsRequestpurchaseCancelLog = (OpsRequestpurchaseCancelLog) ToDtoUtil.PoToDto(requestPurchase,
					opsRequestpurchaseCancelLog);
			// 重新为采购删单实体，赋值
			opsRequestpurchaseCancelLog.setInserttime(new Date());
			opsRequestpurchaseCancelLog.setStatecode(RequestPurchaseStatusEnum.QUXIAO);
			opsRequestpurchaseCancelLog.setOperator(operator);
			opsRequestpurchaseCancelLog.setDeletereason(reason);
			// 删除请购表（移表）
			// basePoService.deleteRequestPurchase(requestPurchase, operator);
			// 更换新的请购删单方法
			basePoService.deleteRequestPurchaseByCancelLog(opsRequestpurchaseCancelLog);
			// 删除关联表
			// bug13662 废除合并表
			// if (purchase.getMergeflag() != null && purchase.getMergeflag()) {
			// basePoService.deleteReqPoMapping(purchase, requestPurchase);
			// }
		}
		// 更新ops_purchaseInvoice状态,判断是否已经发送采购
		if (!StringUtils.equals(PurchaseOrderStatusEnum.DAICHULI, purchase.getStatecode())) {
			List<OpsPurchaseinvoice> opsPurchaseInvoices = basePoService.getPurchaseInvoices(purchase);
			if (CollectionUtils.isNotEmpty(opsPurchaseInvoices)) {
				OpsPurchaseinvoice opsPurchaseInvoice = opsPurchaseInvoices.get(0);
				// 直接置为删除状态
				int delResult = opsPurchaseinvoiceMapper.deleteByPrimaryKey(opsPurchaseInvoice.getId());
				// 调用CTC删除接口
				if (delResult == 1) {
					// BUG15422 增加广州订单发送至CTC
					List<String> Suppliers = Arrays.asList("CN", "CM", "YZ", "CT", "GZ");
					if (Suppliers.contains(opsPurchaseInvoice.getSupplierid())) {
						purchaseCtcDelService.insertMid(opsPurchaseInvoices);
					}
				}
			}
		}
		return new RequestAndPurchaseDto(purchase, requestPurchaseList);
	}

	/**
	 * 3212@description 只删除请购单，返回删除内容 建议对外暴露成服务
	 *
	 * @author C12961
	 * @date 2023/3/22 16:43
	 */
	private OpsRequestpurchase cancelRequestPurchase(String orderNo, Integer itemNo, Integer splitItemNo,
			String operator, String reason) throws Exception {
		// 查询请购单
		OpsRequestpurchase requestPurchase = basePoService.getRequestPurchase(orderNo, itemNo, splitItemNo);
		// 删除请购单(移表)
		// basePoService.deleteRequestPurchase(requestPurchase, operator);
		// bug 12344 opsrequestpurchase转为请购删单实体
		OpsRequestpurchaseCancelLog opsRequestpurchaseCancelLog = new OpsRequestpurchaseCancelLog();
		opsRequestpurchaseCancelLog = (OpsRequestpurchaseCancelLog) ToDtoUtil.PoToDto(requestPurchase,
				opsRequestpurchaseCancelLog);
		// 重新为采购删单实体，赋值
		opsRequestpurchaseCancelLog.setInserttime(new Date());
		opsRequestpurchaseCancelLog.setStatecode(RequestPurchaseStatusEnum.QUXIAO);
		opsRequestpurchaseCancelLog.setOperator(operator);
		opsRequestpurchaseCancelLog.setDeletereason(reason);
		basePoService.deleteRequestPurchaseByCancelLog(opsRequestpurchaseCancelLog);
		return requestPurchase;
	}

	/**
	 * bug 9927 20230426,删单异常 写入采购异常记录表 同时写入邮件发送表中，通过邮件发送给业务
	 */
	@Override
	public void purchaseErrorLog(RequestCancelDto requestCancelDto, String errormsg) {
		OpsPurchaseErrorLog opsPurchaseErrorLog = new OpsPurchaseErrorLog();
		opsPurchaseErrorLog.setOrderno(requestCancelDto.getOrderno());
		opsPurchaseErrorLog.setItemno(requestCancelDto.getItemno());
		opsPurchaseErrorLog.setSplititemno(requestCancelDto.getSplititemno());
		opsPurchaseErrorLog.setMergeflag(requestCancelDto.getMergeflag());
		opsPurchaseErrorLog.setSourcetype(requestCancelDto.getSourceType());
		opsPurchaseErrorLog.setOperator(requestCancelDto.getOperator());
		opsPurchaseErrorLog.setErrorcode(PurchaseErrorEnum.DELETE.getType());
		opsPurchaseErrorLog.setErrormsg(errormsg);
		// 写入采购错误日志表
		int result = opsPurchaseErrorLogMapper.insertSelective(opsPurchaseErrorLog);
		// 将错误订单写入邮件记录表
		if (result > 0) {
			errormsg = "订单号：" + requestCancelDto.getOrderno() + "-" + requestCancelDto.getItemno() + "删单异常,异常信息："
					+ errormsg;
			purchaseSendEmail.purchaseDelMessage(errormsg);
		}
	}

	/**
	 * bug 9927 先校验采购是否可以删除,再操作删单 B91717
	 *
	 * @param orderNo
	 * @param itemNo
	 * @param splitItemNo
	 * @return
	 * @throws OpsException
	 */
	public void purchaseValidation(String orderNo, Integer itemNo, Integer splitItemNo, String cancelType)
			throws OpsException {
		List<OpsRequestpurchase> requestPurchases = new ArrayList<OpsRequestpurchase>();
		OpsPurchaseorder purchase = null;
		if (StringUtils.equals(cancelType, "1")) {
			requestPurchases = basePoService.getRequestPurchaseByPurchase(orderNo, itemNo, splitItemNo);
			// 判断请购的集合是否为空
			if (CollectionUtils.isEmpty(requestPurchases)) {
				// 如果没查询到请购单，则抛异常
				throw Exceptions.OpsException("根据根据采购单号查询不到请购单信息:" + PoNoUtil.create(orderNo, itemNo, splitItemNo));
			}
			// bug11473马腾 删单时增加状态判断
			if (StringUtils.equals(RequestPurchaseStatusEnum.AUTOREADY, requestPurchases.get(0).getStatecode())) {
				throw Exceptions.OpsException("请购单状态不可直接删除！");
			}
			// 判断是否为合并采购单据
			if (requestPurchases.size() > 1) {
				purchase = basePoService.getPurchase(orderNo, itemNo, splitItemNo);
			} else {
				OpsRequestpurchase requestPurchase = requestPurchases.get(0);
				purchase = basePoService.getPurchaseByRequestPurchase(requestPurchase.getOrderno(),
						requestPurchase.getItemno(), requestPurchase.getSplititemno());
			}
		} else {
			OpsRequestpurchase requestPurchase = basePoService.getRequestPurchase(orderNo, itemNo, splitItemNo);
			// 判断请购的集合是否为空
			if (requestPurchase == null) {
				// 如果没查询到请购单，则抛异常
				throw Exceptions.OpsException("根据根据采购单号查询不到请购单信息:" + PoNoUtil.create(orderNo, itemNo, splitItemNo));
			} else if (StringUtils.equals(RequestPurchaseStatusEnum.AUTOREADY, requestPurchase.getStatecode())) {
				// bug11473马腾 删单时增加状态判断
				throw Exceptions.OpsException("请购单状态不可直接删除！");
			}
			purchase = basePoService.getPurchaseByRequestPurchase(requestPurchase.getOrderno(),
					requestPurchase.getItemno(), requestPurchase.getSplititemno());
		}

		if (purchase != null && !StringUtils.equals(PurchaseOrderStatusEnum.YIJIEDAN, purchase.getStatecode())) {
			throw Exceptions.OpsException("采购单状态不可直接删除！" + PoNoUtil.getFullPoNo(purchase));
		}
	}

}
