package com.sales.ops.serviceimpl.inventory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.conf.MailAddress;
import com.sales.ops.common.errorEnum.SalesInfoErrorCodeEnum;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.MapContainer;
import com.sales.ops.db.batchdao.ProductInspectionsGroupDao;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.*;
import com.sales.ops.dto.attachedfile.OpsAttachedFileManageVO;
import com.sales.ops.dto.flux.CancelDocOrderDto;
import com.sales.ops.dto.flux.CancelDocOrderV2Dto;
import com.sales.ops.dto.flux.RoConfirmItem;
import com.sales.ops.dto.flux.RoSignConfirmDto;
import com.sales.ops.dto.inqb.OpsInqbInfo;
import com.sales.ops.dto.inqb.OpsInqbUsageInfo;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.order.*;
import com.sales.ops.dto.product.ProductInspectionsGroupInfo;
import com.sales.ops.dto.purchase.PurchaseUpdateInfo;
import com.sales.ops.dto.purchase.RequestPurchaseInfo;
import com.sales.ops.dto.replacement.NotifyShipmentCondition;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.feign.OpsCallWmsFeignApi;
import com.sales.ops.feign.OpsCreditInterceptFlagFeighApi;
import com.sales.ops.service.ba.OpsWarehouseSalesbranchService;
import com.sales.ops.service.ba.OpsWarehouseService;
import com.sales.ops.service.inqb.InqbCommonService;
import com.sales.ops.service.inventory.*;
import com.sales.ops.service.log.*;
import com.sales.ops.service.order.OpsCustomerOrderService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.po.PoModelService;
import com.sales.ops.service.wm.WmCommonService;
import com.sales.ops.service.wmOrder.*;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.sales.ops.serviceimpl.event.v3.PurchaseEventPublisher;
import com.sales.ops.serviceimpl.event.v3.TransferEventPublisher;
import com.sales.ops.serviceimpl.event.v3.stockadjust.OpsTransferOrderEventService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.invoice.ImpInvoiceReceiveDTO;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import com.smc.smccloud.service.*;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.sales.ops.event.publisher.enums.EventSourceEnum.WAREHOUSE_GOODS_CONFIRM;
import static com.sales.ops.event.publisher.enums.EventSourceEnum.WAREHOUSE_SIGNIN_CONFIRM;

/**
 * @author C02483
 * @version 1.0
 * @description: 物流调度相关的方法
 * @date 2021/10/3 9:50
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class WmDispatchServiceImpl implements WmDispatchService {

	@Autowired
	private AllotInvenToryService allotInvenToryService;
	@Autowired
	private WmRouterOrderService wmRouterOrderService;
	@Autowired
	private OpsRoService opsRoService;
	@Autowired
	private OpsDoService opsDoService;
	@Autowired
	private BaseDoService baseDoService;
	@Autowired
	private OpsPcoService opsPcoService;
	@Autowired
	private BaseInventoryService baseInventoryService;
	@Autowired
	private OpsInventoryPropertyService opsInventoryPropertyService;
	@Autowired
	private OpsWarehouseService opsWarehouseService;
	@Autowired
	private OpsWarehouseSalesbranchService opsWarehouseSalesbranchService;
	@Autowired
	private WmOrderTaskService wmOrderTaskService;
	@Autowired
	private OpsDoItemInventoryMapper opsDoItemInventoryMapper;
	@Autowired
	private OpsInventoryMoveMapper opsInventoryMoveMapper;
	@Autowired
	private OpsInvenToryLogExtDao opsInvenToryLogExtDao;
	@Autowired
	private OpsOrderInventoryLogService orderInventoryLogService;
	@Autowired
	private OpsHandOverService opsHandOverService;
	@Autowired
	private OrderdlvdataMapper orderdlvdataMapper;
	@Autowired
	private OpsHandoverMapper handoverMapper;
	@Autowired
	private OpsRoQtyAdjustMapper opsRoQtyAdjustMapper;
	@Autowired
	private OpsCallWmsFeignApi opsCallWmsFeignApi;
	@Autowired
	private InvoiceServiceFeignApi invoiceServiceFeignApi;
	@Autowired
	private OpsPcoItemInventoryMapper opsPcoItemInventoryMapper;
	@Autowired
	private OpsRoMapper opsRoMapper;
	@Autowired
	private OpsRoItemMapper opsRoItemMapper;
	@Autowired
	private OpsRoBarcodeMapper opsRoBarcodeMapper;
	@Autowired
	private OpsRoItemInventoryMapper opsRoItemInventoryMapper;
	@Autowired
	private OpsWmOrderTaskMapper opsWmOrderTaskMapper;
	@Autowired
	private OpsCreditInterceptFlagFeighApi OpsCreditInterceptFlagFeighApi;
	@Autowired
	private OpsTransferOrderEventService opsTransferOrderEventService;
	@Autowired
	private StockAssemblyFeignApi stockAssemblyFeignApi;
	@Autowired
	private ConsignmentStockFeignApi consignmentStockFeignApi;
	@Autowired
	private OpsImpdataMapper opsImpdataMapper;
	@Autowired
	private CommonServiceFeignApi commonServiceFeignApi;
	@Autowired
	private ImpInvoiceEventLogDao impInvoiceEventLogDao;
	@Autowired
	private OpsRequestpurchaseMapper opsRequestpurchaseMapper;
	@Autowired
	private WmDoService wmDoService;
	@Autowired
	private OPSWarehouseDao opsWarehouseDao;
	@Autowired
	private OpsDlvCustomerDao opsDlvCustomerDao;
	@Autowired
	private OpsCustomerWldateMapper opsCustomerWldateMapper;
	@Autowired
	private BindataMapper bindataMapper;
	@Autowired
	private OpsInventoryMapper opsInventoryMapper;
	@Autowired
	private OpsInventoryLogService opsInventoryLogService;
	@Autowired
	private OpsPurchaseorderMapper opsPurchaseorderMapper;
	@Autowired
	private OpsReqPoMappingMapper opsReqPoMappingMapper;
	@Autowired
	private ProductInspectionsGroupDao productInspectionsGroupDao;
	@Autowired
	private OpsMailMapper opsMailMapper;
	@Autowired
	private MailAddress mailAddress;
	@Autowired
	private WmCommonService wmCommonService;
	@Autowired
	private RoConfirmLogService roConfirmLogService;
	@Autowired
	private OpsRoBarcodeService opsRoBarcodeService;
	@Autowired
	private OpsRoPostService opsRoPostService;
	@Autowired
	private BaseRoService baseRoService;
	@Autowired
	private OpsAttachedFileManageFeignApi opsAttachedFileManageFeignApi;

	@Autowired
	private BasePoService basePoService;
	@Autowired
	private BaseWMOrderService baseWMOrderService;
	@Autowired
	private DictDataServiceFeignApi dictDataServiceFeignApi;

	@Autowired
	private PoModelService poModelService;

	@Autowired
	private InqbCommonService inqbCommonService;

	@Autowired
	private OpsCustomerOrderService opsCustomerOrderService;
	@Autowired
	private DoPcoLogicCenterService doPcoLogicCenterService;
	@Autowired
	private CustomerEventPublisher customerEventPublisher;

	@Autowired
	private ReceiveGoodsService receiveGoodsService;
	@Autowired
	private TransferEventPublisher transferEventPublisher;

    @Autowired
    private InventoryRiskDao inventoryRiskDao;
	@Autowired
	private PurchaseEventPublisher purchaseEventPublisher;

	/**
	 * @author ：C02483
	 * @date ：Created in 2021/12/7 13:39 集约规则 1。整单货齐和随发分批默认选出集约仓 后者用于采购集约
	 *       2。多仓货齐和随到随发 入存在多仓出库存或无库存出库需要默认集约仓，如果是item出单仓满足 集约仓为出货仓
	 * @description：获取集约仓
	 */
	private String GetGatherHoues(InventoryCkByOrderInputDto inputDto, InventoryCkByOrderOutDto outDto)
			throws OpsException {
		List<String> houseList = new ArrayList<String>(outDto.getWarehouseCodeSets());
		String gatherhouse = null;
		//  货齐单仓 随发单仓 通知发货 集约仓为属地集约 中心仓
		if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.equals(inputDto.getCKType())
				|| CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.equals(inputDto.getCKType())
				|| CKTYPEEnum.NOTIFY_UNLIMIT.equals(inputDto.getCKType())
		) {
			gatherhouse = opsWarehouseSalesbranchService.getGatherWarehousesWithBranchId(true,
					inputDto.getDeptno(), outDto.getDoSourceEnum());
		}
		// 随到随发 货齐多仓 随发分批 同仓库存满足(size=1)，集约仓为库存仓库 同仓库存不满足，集约仓为属地集约 (出分库同仓必货齐)
		if(CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.equals(inputDto.getCKType())
				|| CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.equals(inputDto.getCKType())
				|| CKTYPEEnum.ITEM_UNLIMIT.equals(inputDto.getCKType())){
			int houseListSize = houseList.size();
			if (inputDto.isAllotStatus() && houseListSize == 1) {// 库存数量满足 (出分库同仓必货齐)
				gatherhouse = houseList.get(0);
			} else {// 1.库存数量不满足属地集约;2.多仓集约仓为属地集约;（随发分配生成指令时不看集约仓） 中心仓 如果是拆分型号则需要有加工能力的集约仓
				gatherhouse = opsWarehouseSalesbranchService.getGatherWarehousesWithBranchId(false,
						inputDto.getDeptno(), outDto.getDoSourceEnum());
			}
		}
		// 随发分配非拆分型号 集约仓可为空
		if (StringUtils.isEmpty(gatherhouse)) {
			// 随发分配 拆分型号需要集约仓
			if(CKTYPEEnum.ITEM_UNLIMIT.equals(inputDto.getCKType())){
				if (DoSourceEnum.ASSModelNo.equals(outDto.getDoSourceEnum())){
					throw Exceptions.OpsException("当前部门代码不存在可集约出库仓!" + inputDto.getDeptno(), inputDto.getDeptno());// 如果是拆分型号，需要看是否有集约能力和拆分能力
				}
			}else {
				throw Exceptions.OpsException("当前部门代码不存在可集约出库仓!" + inputDto.getDeptno(), inputDto.getDeptno());// 如果是拆分型号，需要看是否有集约能力和拆分能力
			}
		}
		return gatherhouse;
	}

	// 存入快照 包括在库和在途
	private void insertInventorySnapshot(String modelNo, String orderId, String orderItem) {
		opsInvenToryLogExtDao.insertopsInventoryLogBak(orderId, orderItem, modelNo);
		opsInvenToryLogExtDao.insertopsInventoryMoveLogBak(orderId, orderItem, modelNo);
	}


	/**
	 * @description：客户订单完成物流调度功能
	 * @author ：c02483
	 * @date ：Created in 2021/10/3 9:49
	 */
	private InventoryCkByOrderOutDto Orderdispatch(InventoryCkByOrderInputDto inputDto) throws OpsException {

		// 初始化物流出库单据
		WmOrderByInventoryDto wmOrderByInventoryDto = new WmOrderByInventoryDto();
		// 处理订单，算出库房
		InventoryCkByOrderOutDto outDto = allotInvenToryService.getOpsInventoryListByCk(inputDto);
		// 特殊订单不此采购判断
		if (!inputDto.isAllotStatus()) {// 不满足出库判断优先整型号采购
			if (DoSourceEnum.ASSModelNo.equals(outDto.getDoSourceEnum())) {// 拆分型号不拆数量
				if (outDto.getAssBom().getProductBom().getPriorityComplete()) {// 优先能型号采购
					if (OrderSpecExpType.include(inputDto.getExpDlvType(), OrderSpecExpType.MustOrderToCSStock)) {//
						throw Exceptions.OpsException("特殊标识为必须出委托不采购");
					}
					outDto.setAssBom(null);
					outDto.setDoSourceEnum(DoSourceEnum.CG);
					inputDto.setAllotQuantity(0);
				} else {
					for (AssBomDetail assBomDetail : outDto.getAssBom().getDetailList()) {// 子型号不拆数量
						if (!assBomDetail.IsEnough()) {
							if (OrderSpecExpType.include(inputDto.getExpDlvType(),
									OrderSpecExpType.MustOrderToCSStock)) {
								throw Exceptions.OpsException("特殊标识为必须出委托不采购");
							}
							assBomDetail.setAssAllotQty(0);
							assBomDetail.setMapMoveqty(null);
							assBomDetail.setMapqty(null);
						}
					}
				}
			}
		}

		// 最小包装单位判断
		if (!DoSourceEnum.ASSModelNo.equals(outDto.getDoSourceEnum())) {// 拆分型号不判断最小包装单位
			if (!inputDto.isAllotStatus()) {
                allotInvenToryService.minPack(inputDto.getModelno(), inputDto, outDto);
			}
		}
		// 计算集约仓 bugid:16290 c14717 20250214
		String gatherhouse = "";
		if( OrderSpecExpType.include(inputDto.getExpDlvType(),OrderSpecExpType.SPECIAL_WAREHOUSE) ){
			//集约仓为指定集约仓 查表
			String fixedWarehouseCode = "";//productSpecialModelDao.getFixedWarehouseCode(OrderSpecExpType.SPECIAL_WAREHOUSE.code());
			if(StringUtils.isBlank(fixedWarehouseCode)){
				throw Exceptions.OpsException("指定集约仓配置不存在");
			}
			gatherhouse = StringUtils.trim(fixedWarehouseCode);
		}else {
			gatherhouse = GetGatherHoues(inputDto, outDto);
		}

		inputDto.setWarehouseCode(gatherhouse);
		outDto.setWarehouseCode(gatherhouse);
		InventoryDispatchDto dispatchDto = new InventoryDispatchDto(outDto);

		List<OpsRequestpurchase> requestpurchaseList = new ArrayList<>();
		// 根据发货方式生成物流出库单据
		if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.equals(inputDto.getCKType())
				|| CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.equals(inputDto.getCKType())
				|| CKTYPEEnum.NOTIFY_UNLIMIT.equals(inputDto.getCKType())
				|| CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.equals(inputDto.getCKType())
				|| CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.equals(inputDto.getCKType())) {
			wmRouterOrderService.GatherOrder(inputDto, dispatchDto, wmOrderByInventoryDto, inputDto.getUserDto());
		}
		if (CKTYPEEnum.ITEM_UNLIMIT.equals(inputDto.getCKType())) {
			wmRouterOrderService.UngatherOrder(inputDto, dispatchDto, wmOrderByInventoryDto, inputDto.getUserDto());
		}

		// 生成请购单
		boolean splitNoSend = true;// 拆分号是否下发
		if (DoSourceEnum.ASSModelNo.equals(dispatchDto.getDoSourceEnum())) {
			for (AssBomDetail assBomDetail : dispatchDto.getAssBom().getDetailList()) {
				if (!assBomDetail.IsEnough()) {
					Integer qty = assBomDetail.getAssQty() - assBomDetail.getAssAllotQty();
					// BUGID 8618 C14717 20221115 returnSplit3CModelNo
					String subModelno = assBomDetail.getProductBomDetail().getModelno();
					// 1.判断3c型号 2.发邮件
					subModelno = returnSplit3CModelNo(subModelno, inputDto.getModelno(),
							inputDto.getOrderId() + inputDto.getOrderItem());
					if (org.apache.commons.collections.CollectionUtils.isNotEmpty(wmOrderByInventoryDto.getPcoList())) {
						for (CrePcoByInventoryDto pcoDto : wmOrderByInventoryDto.getPcoList()) {
							if (org.apache.commons.collections.CollectionUtils.isNotEmpty(pcoDto.getOpsPcoItems())) {
								for (OpsPcoItem pcoItem : pcoDto.getOpsPcoItems()) {
									if (pcoItem.getSubModelno()
											.equals(assBomDetail.getProductBomDetail().getModelno())) {
										// 设置3c型号
										pcoItem.setSubModelno(subModelno);
									}
								}
							}
						}
					}
					Long bomId = assBomDetail.getProductBomDetail().getBomid();
					// 组装请购实体
					OpsRequestpurchase opsRequestpurchase = initOpsRequestpurchase(splitNoSend, inputDto,
							WMPurchaseTagEnum.ASS, assBomDetail.getAssitem(), subModelno, qty, gatherhouse, bomId);
					opsRequestpurchase.setShikomianswerno("");// 2022-09-29拆分型号不传shikomian
					requestpurchaseList.add(opsRequestpurchase);
				}
			}
		} else if (inputDto.getQuantity() > inputDto.getAllotQuantity()) {
			if (inputDto.getAllotQuantity() == 0) {
				outDto.setDoSourceEnum(DoSourceEnum.CG);
				splitNoSend = false;
			}
			if (OrderSpecExpType.include(inputDto.getExpDlvType(), OrderSpecExpType.MustOrderToCSStock)) {
				throw Exceptions.OpsException("特殊标识为必须出委托不采购");
			}
			inputDto.addQtyItem();
			Integer qty = inputDto.getQuantity() - inputDto.getAllotQuantity();
			wmRouterOrderService.CreateCGDo(inputDto, wmOrderByInventoryDto);
			OpsRequestpurchase opsRequestpurchase = initOpsRequestpurchase(splitNoSend, inputDto,
					WMPurchaseTagEnum.WHOLE, inputDto.getQtyItem(), inputDto.getModelno(), qty, gatherhouse, null);
			if (OrderTypeEnum.DR.equals(inputDto.getOrderType()) || OrderTypeEnum.CR.equals(inputDto.getOrderType())) {// DR单直接写供应商JP
				opsRequestpurchase.setSupplierid("JP");
			}
			requestpurchaseList.add(opsRequestpurchase);
		}
		// 持久化物流单据
		if (!wmOrderByInventoryDto.getRoList().isEmpty()) {
			opsRoService.CreateRoForDispatch(wmOrderByInventoryDto.getRoList(), inputDto.getUserDto());
		}
		if (!wmOrderByInventoryDto.getDolist().isEmpty()) {
			opsDoService.CreateDoForDispatch(wmOrderByInventoryDto.getDolist(), inputDto.getUserDto(),outDto.getRiskInvMap());
		}
		if (!wmOrderByInventoryDto.getPcoList().isEmpty()) {
			opsPcoService.CreatePcoForDispatch(wmOrderByInventoryDto.getPcoList(), inputDto.getUserDto(),inputDto.getCKType().getCode(),outDto.getRiskInvMap());
		}
		// 持久化请购表
		if (!CollectionUtils.isEmpty(requestpurchaseList)) {
			outDto.setOpsRequestpurchaseList(requestpurchaseList);
			// bugid:12668 c14717 2023-11-22 验证采购单是否存在
			for (OpsRequestpurchase item : requestpurchaseList) {
				OpsRequestpurchase r = basePoService.getRequestPurchase(item.getOrderno(), item.getItemno(),
						item.getSplititemno());
				if (Objects.nonNull(r)) {
					throw Exceptions.OpsException("请购单已存在，请先处理请购单");
				}
				// 写入表
				opsRequestpurchaseMapper.insertSelective(item);
			}
		}
		return outDto;
	}

	/**
	 * BUGID 8618 C14717 20221115
	 *
	 * @param subModelNo
	 *            子型号
	 * @param modelNo
	 *            整型号
	 */
	private String returnSplit3CModelNo(String subModelNo, String modelNo, String orderFno) {
		// 型号带3c返回
		if (subModelNo.startsWith("3C-")) {
			return subModelNo;
		} else {
			// bugid:13496 3c表判断是否正确 c14717 20240206
			// boolean flag = handle3CModel(subModelNo);
			boolean flag = poModelService.judge3CModel(subModelNo);
			if (flag) {
				// 存储邮件
				saveMailToDb(subModelNo, modelNo, "3C-" + subModelNo, orderFno);// 发送邮件
				return "3C-" + subModelNo;
			} else {
				return subModelNo;
			}
		}
	}

	/**
	 * 发送拆分型号3c变更 BUGID 8618 C14717 20221115
	 *
	 * @param subModelNo
	 *            子型号
	 * @param modelNo
	 *            整型号
	 */
	private void saveMailToDb(String subModelNo, String modelNo, String cSubmodelNo, String orderFno) {
		// 保存邮件到数据库
		OpsMail opsMail = new OpsMail();
		// opsMail.setMailFrom(mail.getFrom());
		// 初始状态是0
		opsMail.setStatus(SendStatusEnum.INIT.getType());
		opsMail.setCc(mailAddress.getThreeCcc());
		opsMail.setMailTo(mailAddress.getThreecMailTo());
		// 主题
		opsMail.setSubject("3C产品维护");
		StringBuffer con = new StringBuffer();
		con.append("<h4> ");
		con.append("订单号：");
		con.append(orderFno);
		con.append("，整型号：");
		con.append(modelNo);
		con.append("，拆分子型号：");
		con.append(subModelNo);
		con.append("是3C产品：");
		con.append(cSubmodelNo);
		con.append("请确认维护。");
		con.append(" </h4>");
		// 邮件内容
		opsMail.setContext(con.toString());
		// 发件人名称
		opsMail.setNickName("ops邮件");
		opsMailMapper.insertSelective(opsMail);
	}

	/**
	 * 判断是否是3C型号
	 *
	 * @return BUGID 8618 C14717 20221115
	 */
	private boolean handle3CModel(String modelNo) {
		List<String> modelList = new ArrayList<String>();
		modelList.add(modelNo);
		if (!modelNo.startsWith("3C-")) {
			modelList.add("3C-" + modelNo);
		}
		List<ProductInspectionsGroupInfo> arr = productInspectionsGroupDao.selectInfos(modelList);
		for (ProductInspectionsGroupInfo i : arr) {
			if (StringUtils.equals("3C-A", i.getInspections())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @description：请购单初始化
	 * @author ：c02483
	 * @date ：Created in 2021/11/10 11:34
	 */
	@Override
	public OpsRequestpurchase initOpsRequestpurchase(boolean splitNoSend, InventoryCkByOrderInputDto inputDto,
													 WMPurchaseTagEnum wmPurchaseTagEnum, int splititemno, String modelno, int qty, String requestwarehouseid, Long bomId) throws OpsException{
		OpsRequestpurchase opsRequestpurchase = new OpsRequestpurchase();
		opsRequestpurchase.setWmtag(wmPurchaseTagEnum.getType());
		opsRequestpurchase.setOrderno(inputDto.getOrderId());
		opsRequestpurchase.setItemno(Integer.parseInt(inputDto.getOrderItem()));
		if (splitNoSend) {// 整型号采购不传
			opsRequestpurchase.setSplititemno(splititemno);
		}
		// stdPrice,shikomiAnswerNo,remark,salesinfono,requestTime,productTag,producttagre
		opsRequestpurchase.setCustomerno(inputDto.getCustomer());
		opsRequestpurchase.setUserno(inputDto.getUserNo());
		opsRequestpurchase.setDeptno(inputDto.getDeptno());
		opsRequestpurchase.setApplyDeptNo(inputDto.getApplyDeptNo());
		opsRequestpurchase.setOrdtype(inputDto.getOrderType());
		opsRequestpurchase.setPurchasetype(PurchaseTypeEnum.SALE.getTypeCode());
		opsRequestpurchase.setModelno(modelno);
		opsRequestpurchase.setQuantity(qty);
		opsRequestpurchase.setSpecmark(inputDto.getSpecmark());
		opsRequestpurchase.setHopedeliverydate(inputDto.getHopeDate());
		// ru
		opsRequestpurchase.setRequestwarehouseid(requestwarehouseid);
		opsRequestpurchase.setStdprice(inputDto.getEprice());
		opsRequestpurchase.setRemark(inputDto.getRemark1());
		opsRequestpurchase.setRequesttime(com.sales.ops.dto.util.DateUtil.getNow());
		opsRequestpurchase.setSalesinfono(inputDto.getPreSaleNo());
		opsRequestpurchase.setShikomianswerno(inputDto.getShikomiNo());
		opsRequestpurchase.setProducttag(inputDto.getProductTag());
		opsRequestpurchase.setProducttagremark(inputDto.getProductTagRemark());
		opsRequestpurchase.setOrderdate(inputDto.getROrdDate());
		opsRequestpurchase.setInventorytypecode("TY");

		opsRequestpurchase.setStatecode(RequestPurchaseStatusEnum.DAICHULI);
		opsRequestpurchase.setUpdatetime(com.sales.ops.dto.util.DateUtil.getNow());
		if (Objects.nonNull(inputDto.getOrderInitFlag()) && inputDto.getOrderInitFlag()) {
			// bugid:10579 20230424 c14717 订单还原拦截
			opsRequestpurchase.setInformation("revertOrder;");
		}
		opsRequestpurchase.setEndUser(inputDto.getEndUserNo()); // bug19576 增加endUser字段赋值，取值为rcvMasteR.endUserNo
		// bugid:12266 c14717 20231016 最低售价
		// bugid:13517 c14717 20240222 提供客户型号和客户订单号
		RequestPurchaseInfo requestPurchaseInfo = new RequestPurchaseInfo();
		requestPurchaseInfo.setCorderno(inputDto.getCorderNo());
		requestPurchaseInfo.setCproductno(inputDto.getCproductNo());
		// bugid:14940 c14717 20240826
		if (OrderSpecExpType.include(inputDto.getExpDlvType(), OrderSpecExpType.AIRTRANS)) {
			requestPurchaseInfo.setAirCustomer(true);
		}
		if (OrderSpecExpType.include(inputDto.getExpDlvType(), OrderSpecExpType.MINPRICE)) {// 最低售价
			// 查询附件
			OpsAttachedFileManageVO opsAttachedFileManageVO = new OpsAttachedFileManageVO();
			opsAttachedFileManageVO.setBusinessKeyValue(inputDto.getOrderFno());
			opsAttachedFileManageVO.setBusinessType(FileUploadTypeEnum.MINPRICE.getBusinessType());
			opsAttachedFileManageVO.setFileType(FileUploadTypeEnum.MINPRICE.getFileType());
			ResultVo<List<OpsAttachedFileManageVO>> attacheFiledManageInfo = opsAttachedFileManageFeignApi
					.findAttacheFiledManageInfo(opsAttachedFileManageVO);
			if (!attacheFiledManageInfo.isSuccess()) {
				throw Exceptions.OpsException("最低售价-附件获取失败");
			}
			if (CollectionUtil.isEmpty(attacheFiledManageInfo.getData())) {
				throw Exceptions.OpsException("最低售价-无附件");
			}
			// 写入请购表
			requestPurchaseInfo.setMinPrice(true);
			requestPurchaseInfo.setFileList(attacheFiledManageInfo.getData());
		}
		//bugid:14624 c14717 20240708
		orderHandleInqBSetCG(requestPurchaseInfo, opsRequestpurchase, inputDto, bomId);
		opsRequestpurchase.setInfojson(JSON.toJSONString(requestPurchaseInfo));
		return opsRequestpurchase;
	}


	/**
	 * bugid:14624 20240704 c14717
	 * 1.rcvdetail 读取inqB号
	 * 2.调用inqB查询接口
	 * 3.对比型号 数量 最终用户
	 * 4.条件匹配 调用inqB预占接口，inqB号写入请购表
	 * @param po
	 * @param inputDto
	 * @param bomId
	 */
	public void orderHandleInqBSetCG(RequestPurchaseInfo requestPurchaseInfo, OpsRequestpurchase po,InventoryCkByOrderInputDto inputDto, Long bomId){
		// 1.rcvdetail 读取inqB号
		OpsInqbDetail inqbDetail = null;
		if(StringUtils.isNotBlank(inputDto.getInqBNo())){
			// 2.调用inqB查询接口
			ResultVo<OpsInqbInfo> inqbApplyInfo = inqbCommonService.getInqbApplyInfo(inputDto.getInqBNo());
			if(!inqbApplyInfo.isSuccess()){
				log.info(inqbApplyInfo.getMessage());
			}
			// 2.1 返回成功，数据不为空
			if(inqbApplyInfo.isSuccess() && Objects.nonNull(inqbApplyInfo.getData()) && CollectionUtils.isNotEmpty(inqbApplyInfo.getData().getOpsInqbDetailList())){
				log.info("订单分配查询inqB,申请号{}" ,inqbApplyInfo.getData().getOpsInqbApply().getInqbApplyNo());
				// 回更对象
				OpsInqbUsageInfo opsInqbUsageInfo = null;
				for(OpsInqbDetail param : inqbApplyInfo.getData().getOpsInqbDetailList()){
					// 3.对比型号 数量 最终用户 bomId
					if(Objects.nonNull(param.getQuantity())
							&& Objects.nonNull(param.getModelNo())
							&& Objects.nonNull(param.getEndUser())){
						if(Objects.isNull(bomId)){
							// inqB申请数量大于等于 请购数量可用 15065 2024-08-28
							if(param.getQuantity() >= po.getQuantity()
									&& param.getModelNo().equals(po.getModelno())){
								if(param.getEndUser().equals(inputDto.getCustomer())
										|| param.getEndUser().equals(inputDto.getUserNo())
										|| param.getEndUser().equals(inputDto.getEndUserNo())){
									inqbDetail = param;
									log.info("订单分配已匹配inqB,申请号{}" ,inqbApplyInfo.getData().getOpsInqbApply().getInqbApplyNo());
									break;
								}
							}
						}
						if(Objects.nonNull(bomId)){
							// inqB申请数量大于等于 请购数量可用 15065 2024-08-28
							if(param.getQuantity() >= po.getQuantity()
									&& param.getModelNo().equals(po.getModelno())
									&& param.getBomId().equals(bomId.toString())){
								if(param.getEndUser().equals(inputDto.getCustomer())
										|| param.getEndUser().equals(inputDto.getUserNo())
										|| param.getEndUser().equals(inputDto.getEndUserNo())){
									inqbDetail = param;
									log.info("订单分配已匹配inqB,申请号{}" ,inqbApplyInfo.getData().getOpsInqbApply().getInqbApplyNo());
									break;
								}
							}
						}
					}
				}
				if(Objects.nonNull(inqbDetail)){
					// 4.条件匹配 调用inqB预占接口，inqB号写入请购表
					opsInqbUsageInfo = new OpsInqbUsageInfo(inqbDetail.getInqbApplyNo(), inqbDetail.getItemNo(), po.getQuantity(),
							inputDto.getOrderFno(), com.sales.ops.dto.util.DateUtil.getNow());
					ResultVo<String> stringResultVo = inqbCommonService.updateInqbUsageInfo(opsInqbUsageInfo);
					log.info("订单分配已更新inqB,申请号{}" ,inqbApplyInfo.getData().getOpsInqbApply().getInqbApplyNo());
					if(stringResultVo.isSuccess()){
						requestPurchaseInfo.setInqbDetail(inqbDetail);
					}else {
						log.info(stringResultVo.getMessage());
					}
				}
			}
		}
	}

	/**
	 * @description：订单分配：整单号调度
	 * @author ：c02483
	 * @date ：Created in 2021/10/26 18:31
	 */
	@Override
	public void OrderdispatchForOrder(Rcvmaster rcvmaster, List<Rcvdetail> rcvdetails) throws OpsException {
		for (Rcvdetail rcvdetail : rcvdetails) {
			OrderdispatchForOrderItem(rcvmaster, rcvdetail, false);
		}
	}

	/**
	 * @description：订单分配：单项调度
	 * @author ：c0248356
	 * @date ：Created in 2021/10/26 18:
	 */
	@Override
	public List<OpsOrderAssignResult> OrderdispatchForOrderItem(Rcvmaster rcvmaster, Rcvdetail rcvdetail,
																Boolean orderInitFlag) throws OpsException {
		//校验rcvdetail单价 如果为空或为0抛出异常 bugid:14501 c14717 20240620
		if(Objects.isNull(rcvdetail.getPrice()) || (BigDecimal.ZERO.compareTo(rcvdetail.getPrice()) == 0)){
			throw Exceptions.OpsException("订单单价异常");
		}
		//校验rcvdetail校验客户货期 如果为空异常 bugid:16278 c14717 20241224
		if(Objects.isNull(rcvdetail.getDlvDate())){
			throw Exceptions.OpsException("客户货期不可为空");
		}
		// 查重复
		List<OpsDo> doList = baseDoService.findDoListByOrder(rcvdetail.getRorderNo(),
				rcvdetail.getRorderItem().toString(), null, null);
		if (!CollectionUtils.isEmpty(doList)) {
			return null;
		}
		// 查地址
		OrderdlvdataKey orderdlvdataKey = new OrderdlvdataKey();
		orderdlvdataKey.setOrderno(rcvmaster.getRorderNo());
		orderdlvdataKey.setItemno(rcvdetail.getRorderItem());
		Orderdlvdata orderdlvdata = orderdlvdataMapper.selectByPrimaryKey(orderdlvdataKey);
		if (ObjectUtil.isNull(orderdlvdata)) {
			OrderdlvdataExample example = new OrderdlvdataExample();
			example.createCriteria().andOrdernoEqualTo(rcvmaster.getRorderNo()).andItemnoEqualTo(0);
			List<Orderdlvdata> orderdlvdataList = orderdlvdataMapper.selectByExample(example);
			if (orderdlvdataList.size() > 0) {
				orderdlvdata = orderdlvdataList.get(0);
			}
		}

		// 发货地址技术数据判断
		if (Objects.isNull(orderdlvdata)) {
			throw Exceptions.OpsException("单号：" + rcvmaster.getRorderNo() + " ; orderdlvdata表无数据");
		} else {
			if ("3".equals(orderdlvdata.getDlvflag())) {// 自提 拦截订单
				if (StringUtils.isEmpty(orderdlvdata.getContactpsn()) || StringUtils.isEmpty(orderdlvdata.getTelno())) {
					throw Exceptions.OpsException("此单无发货基础数据");// 自提也需要维护发货基础数据
				}
			} else {
				if (StringUtils.isEmpty(orderdlvdata.getProvince()) || StringUtils.isEmpty(orderdlvdata.getCity())
						|| StringUtils.isEmpty(orderdlvdata.getDistrict())
						|| StringUtils.isEmpty(orderdlvdata.getDlvaddress())
						|| StringUtils.isEmpty(orderdlvdata.getContactpsn())
						|| StringUtils.isEmpty(orderdlvdata.getTelno())) {
					throw Exceptions.OpsException("此单无发货基础数据");// 自提也需要维护发货基础数据
				}
			}
		}

		// 组装订单数据
		InventoryCkByOrderInputDto inputDto = new InventoryCkByOrderInputDto(rcvmaster, rcvdetail, orderdlvdata);

		if (Objects.isNull(inputDto.getPhone()) && Objects.isNull(inputDto.getMobile())) {
			throw Exceptions.OpsException("此单无有效的电话号");// 自提也需要维护发货基础数据
		}

        //bugid: 19277 c14717 20251028
        if ( CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(rcvmaster.getDlvEntire())
                &&  OrderSpecExpType.include(inputDto.getExpDlvType(), OrderSpecExpType.MustOrderToCSStock)) {
            throw Exceptions.OpsException("服务备库订单不能使用货齐单仓发货方式");
        }

		inputDto.setUserDto(UserDto.AUTO);

		// bugid:10579 20230424 c14717
		// 订单还原标识--用于 分配订单如果需要采购，修改采购状态为拦截
		inputDto.setOrderInitFlag(orderInitFlag);

		// *****处理订单*****
		InventoryCkByOrderOutDto outDto = Orderdispatch(inputDto);

		// bugid: 10345 20230410 c14717 计算物流交货期
		Map<String, String> modifyDoResult = opsDoService.updateDoPcoWlday(rcvdetail.getRorderNo(),
				rcvdetail.getRorderItem().toString(), null, false, UserDto.ADMIN);

		// 处理订单返回结果助理状态
		List<OpsOrderAssignResult> list = new ArrayList<>();
		getOpsOrderAssgnResultByOutDto(outDto, list, inputDto);

		if (StringUtils.isNotEmpty(modifyDoResult.get("maxWmDlvDate"))) {
			// 修改rcvdetail状态
			wmRouterOrderService.updateRcvDetailToHandled(rcvdetail.getId(), rcvdetail.getRorderNo(),
					rcvdetail.getRorderItem(), inputDto.getCustOrderStatusEnum(),
					com.sales.ops.common.until.DateUtil.stringToDate(modifyDoResult.get("maxWmDlvDate")));
		} else {
			throw Exceptions.OpsException("更新物流交货期失败");
		}

		return list;
	}

	private void getOpsOrderAssgnResultByOutDto(InventoryCkByOrderOutDto outDto, List<OpsOrderAssignResult> list,
												InventoryCkByOrderInputDto inputDto) {
		int seqNo = 0;
		if (!outDto.getInventoryMapSorted().isEmpty()) {
			if (DoSourceEnum.ASSModelNo.equals(outDto.getDoSourceEnum())) {
				if (Objects.nonNull(outDto.getAssBom())
						&& !CollectionUtils.isEmpty(outDto.getAssBom().getDetailList())) {
					for (AssBomDetail assBomDetail : outDto.getAssBom().getDetailList()) {
						if (!Objects.isNull(assBomDetail.getMapMoveqty())) {
							for (Map.Entry<OpsInventoryMove, Integer> integerEntry : assBomDetail.getMapMoveqty()
									.entrySet()) {
								OpsInventoryMove opsInventoryMove = integerEntry.getKey();
								int alreadyDistributionNum = integerEntry.getValue();// 当前inventroyId已分配数量
								OpsOrderAssignResult opsOrderAssignResult = new OpsOrderAssignResult();
								opsOrderAssignResult.setOrderNo(inputDto.getOrderId());
								opsOrderAssignResult.setOrderItem(Integer.parseInt(inputDto.getOrderItem()));
								seqNo = seqNo + 1;
								opsOrderAssignResult.setSeqno(seqNo);
								if (Objects.nonNull(opsInventoryMove.getAssociateNo())) {
									opsOrderAssignResult.setAssociateNo(opsInventoryMove.getAssociateNo());
								}
								if (Objects.nonNull(opsInventoryMove.getAssociateNoItem())) {
									opsOrderAssignResult.setAssociateNoItem(opsInventoryMove.getAssociateNoItem());
								}
								if (Objects.nonNull(opsInventoryMove.getAssociateNoSplitno())) {
									opsOrderAssignResult
											.setAssociateNoSplitNo(opsInventoryMove.getAssociateNoSplitno());
								} else {
									opsOrderAssignResult.setAssociateNoSplitNo(0);
								}
								if (Objects.nonNull(opsInventoryMove.getSupplierid())) {
									opsOrderAssignResult.setSupplierid(opsInventoryMove.getSupplierid());
								}
								opsOrderAssignResult.setStockType(opsInventoryMove.getInventoryStatus());
								opsOrderAssignResult.setStockCode(opsInventoryMove.getWarehouseCode());
                                // bugid 20641 c14717 20260424
                                List<OpsInventoryProperty> opsInventoryProperties = inputDto.getInvPropertyMap().get(opsInventoryMove.getModelno());
                                for (OpsInventoryProperty property : opsInventoryProperties) {
                                    if(property.getInventoryPropertyId().equals(opsInventoryMove.getInventoryPropertyId())){
                                        opsOrderAssignResult.setInventoryTypeCode(property.getInventoryTypeCode());
                                        break;
                                    }
                                }
								opsOrderAssignResult.setModelno(opsInventoryMove.getModelno());
								opsOrderAssignResult.setQuantity(alreadyDistributionNum);
								list.add(opsOrderAssignResult);
							}
						}
						if (!Objects.isNull(assBomDetail.getMapqty())) {
							for (Map.Entry<OpsInventory, Integer> integerEntry : assBomDetail.getMapqty().entrySet()) {
								OpsInventory opsInventory = integerEntry.getKey();
                                int alreadyDistributionNum = integerEntry.getValue();// 当前inventroyId已分配数量
								OpsOrderAssignResult opsOrderAssignResult = new OpsOrderAssignResult();
                                // bugid: 20641 c14717 20260424
                                Boolean risk = outDto.getRiskInvMap().get(opsInventory.getInventoryId());
                                if(risk != null && risk){
                                    opsOrderAssignResult.setInventoryRisk(true);
                                }
								opsOrderAssignResult.setOrderNo(inputDto.getOrderId());
								opsOrderAssignResult.setOrderItem(Integer.parseInt(inputDto.getOrderItem()));
								seqNo = seqNo + 1;
								if (Objects.nonNull(opsInventory.getWarehouseCode())) {
									opsOrderAssignResult.setSupplierid(opsInventory.getWarehouseCode());
								}
								opsOrderAssignResult.setSeqno(seqNo);
								opsOrderAssignResult.setStockType(opsInventory.getInventoryStatus());
								opsOrderAssignResult.setStockCode(opsInventory.getWarehouseCode());
                                // bugid 20641 c14717 20260424
                                List<OpsInventoryProperty> opsInventoryProperties = inputDto.getInvPropertyMap().get(opsInventory.getModelno());
                                for (OpsInventoryProperty property : opsInventoryProperties) {
                                    if(property.getInventoryPropertyId().equals(opsInventory.getInventoryPropertyId())){
                                        opsOrderAssignResult.setInventoryTypeCode(property.getInventoryTypeCode());
                                        break;
                                    }
                                }
								opsOrderAssignResult.setModelno(opsInventory.getModelno());
								opsOrderAssignResult.setQuantity(alreadyDistributionNum);
								list.add(opsOrderAssignResult);
							}
						}
					}
				}
			}
			if (!Objects.isNull(outDto.getInventoryMoveMap())) {
				for (Map.Entry<OpsInventoryMove, Integer> integerEntry : outDto.getInventoryMoveMap().entrySet()) {
					OpsInventoryMove opsInventoryMove = integerEntry.getKey();
					int alreadyDistributionNum = integerEntry.getValue();// 当前inventroyId已分配数量
					OpsOrderAssignResult opsOrderAssignResult = new OpsOrderAssignResult();
					opsOrderAssignResult.setOrderNo(inputDto.getOrderId());
					opsOrderAssignResult.setOrderItem(Integer.parseInt(inputDto.getOrderItem()));
					seqNo = seqNo + 1;
					opsOrderAssignResult.setSeqno(seqNo);
					opsOrderAssignResult.setStockType(opsInventoryMove.getInventoryStatus());
					if (Objects.nonNull(opsInventoryMove.getAssociateNo())) {
						opsOrderAssignResult.setAssociateNo(opsInventoryMove.getAssociateNo());
					}
					if (Objects.nonNull(opsInventoryMove.getAssociateNoItem())) {
						opsOrderAssignResult.setAssociateNoItem(opsInventoryMove.getAssociateNoItem());
					}
					if (Objects.nonNull(opsInventoryMove.getAssociateNoSplitno())) {
						opsOrderAssignResult.setAssociateNoSplitNo(opsInventoryMove.getAssociateNoSplitno());
					} else {
						opsOrderAssignResult.setAssociateNoSplitNo(0);
					}
					if (Objects.nonNull(opsInventoryMove.getSupplierid())) {
						opsOrderAssignResult.setSupplierid(opsInventoryMove.getSupplierid());
					}
					opsOrderAssignResult.setStockCode(opsInventoryMove.getWarehouseCode());
                    // bugid 20641 c14717 20260424
                    List<OpsInventoryProperty> opsInventoryProperties = inputDto.getInvPropertyMap().get(opsInventoryMove.getModelno());
                    for (OpsInventoryProperty property : opsInventoryProperties) {
                        if(property.getInventoryPropertyId().equals(opsInventoryMove.getInventoryPropertyId())){
                            opsOrderAssignResult.setInventoryTypeCode(property.getInventoryTypeCode());
                            break;
                        }
                    }
					opsOrderAssignResult.setModelno(opsInventoryMove.getModelno());
					opsOrderAssignResult.setQuantity(alreadyDistributionNum);
					list.add(opsOrderAssignResult);
				}
			}
			if (!Objects.isNull(outDto.getInventoryMap())) {
				for (Map.Entry<OpsInventory, Integer> integerEntry : outDto.getInventoryMap().entrySet()) {
					OpsInventory opsInventory = integerEntry.getKey();
					int alreadyDistributionNum = integerEntry.getValue();// 当前inventroyId已分配数量
					OpsOrderAssignResult opsOrderAssignResult = new OpsOrderAssignResult();
                    // bugid: 20641 c14717 20260424
                    Boolean risk = outDto.getRiskInvMap().get(opsInventory.getInventoryId());
                    if(risk != null && risk){
                        opsOrderAssignResult.setInventoryRisk(true);
                    }
                    opsOrderAssignResult.setOrderNo(inputDto.getOrderId());
					opsOrderAssignResult.setOrderItem(Integer.parseInt(inputDto.getOrderItem()));
					seqNo = seqNo + 1;
					if (Objects.nonNull(opsInventory.getWarehouseCode())) {
						opsOrderAssignResult.setSupplierid(opsInventory.getWarehouseCode());
					}
					opsOrderAssignResult.setSeqno(seqNo);
					opsOrderAssignResult.setStockType(opsInventory.getInventoryStatus());
					opsOrderAssignResult.setStockCode(opsInventory.getWarehouseCode());
                    // bugid 20641 c14717 20260424
                    List<OpsInventoryProperty> opsInventoryProperties = inputDto.getInvPropertyMap().get(opsInventory.getModelno());
                    for (OpsInventoryProperty property : opsInventoryProperties) {
                        if(property.getInventoryPropertyId().equals(opsInventory.getInventoryPropertyId())){
                            opsOrderAssignResult.setInventoryTypeCode(property.getInventoryTypeCode());
                            break;
                        }
                    }
					opsOrderAssignResult.setModelno(opsInventory.getModelno());
					opsOrderAssignResult.setQuantity(alreadyDistributionNum);
					list.add(opsOrderAssignResult);
				}
			}
		}
		if (!CollectionUtils.isEmpty(outDto.getOpsRequestpurchaseList())) {
			for (OpsRequestpurchase opsRequestpurchase : outDto.getOpsRequestpurchaseList()) {
				OpsOrderAssignResult opsOrderAssignResult = new OpsOrderAssignResult();
				opsOrderAssignResult.setOrderNo(inputDto.getOrderId());
				opsOrderAssignResult.setOrderItem(Integer.parseInt(inputDto.getOrderItem()));
				seqNo = seqNo + 1;
				opsOrderAssignResult.setSeqno(seqNo);
				opsOrderAssignResult.setStockType("CG");
				opsOrderAssignResult.setStockCode("");
				opsOrderAssignResult.setInventoryTypeCode("");
				opsOrderAssignResult.setModelno(opsRequestpurchase.getModelno());
				opsOrderAssignResult.setQuantity(opsRequestpurchase.getQuantity());
				list.add(opsOrderAssignResult);
			}
		}
	}

	/*
	 * public void sendForPo(List<OpsPurchaseStatusToWMDto> list) throws
	 * OpsException {// List<OpsWmOrderTask> taskList = new
	 * ArrayList<OpsWmOrderTask>(); for (OpsPurchaseStatusToWMDto dto : list) {
	 * OpsPurchaseorder opsPurchaseorder = dto.getOpsPurchaseorder();
	 * OpsRequestpurchase opsRequestpurchase = dto.getOpsRequestpurchase(); //
	 * b和k // 订单类型为 20 BIN补库订单 21 先行补库订单 3 委托在库补货订单 分库和位图补库生成调拨单 if
	 * (OrderTypeEnum.BIN.equals(opsRequestpurchase.getOrdtype()) ||
	 * OrderTypeEnum.PRE.equals(opsRequestpurchase.getOrdtype()) ||
	 * OrderTypeEnum.WT.equals(opsRequestpurchase.getOrdtype())) { if
	 * (!Objects.equals(opsRequestpurchase.getPurchasewarehouseid(),
	 * opsRequestpurchase.getRequestwarehouseid())) { OpsWarehouse opsWarehouse
	 * = opsWarehouseService
	 * .findById(opsRequestpurchase.getRequestwarehouseid());// 请购仓 if
	 * (WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsWarehouse.
	 * getWarehouseType()) ||
	 * WarehouseTypeEnum.FDC.getHouseTypeCode().equals(opsWarehouse.
	 * getWarehouseType())) { String dbckid =
	 * String.format(OrderIDFormatEnum.DBC_FORMAT.getFormat(),
	 * opsRequestpurchase.getOrderno(), String.format("%03d",
	 * (opsRequestpurchase.getItemno())), String.format("%03d", 0),
	 * String.format("%03d", 0)); String dbrkid =
	 * String.format(OrderIDFormatEnum.DBR_FORMAT.getFormat(),
	 * opsRequestpurchase.getOrderno(), String.format("%03d",
	 * (opsRequestpurchase.getItemno())), String.format("%03d", 0),
	 * String.format("%03d", 0)); OpsDo opsDo = initOpsDo(dbckid,
	 * opsRequestpurchase, opsWarehouse,
	 * opsRequestpurchase.getPurchasewarehouseid()); OpsDoItem opsDoItem =
	 * opsDoService.initOpsDoItem(dbckid, opsRequestpurchase);
	 * opsDoItem.setQty(opsRequestpurchase.getQuantity()); OpsRo opsRo =
	 * opsRoService.initOpsRo(dbrkid, opsRequestpurchase); List<OpsRoItem>
	 * roItemList = opsRoService.initOpsRoItem(dbrkid, opsRequestpurchase);
	 * opsDoService.insertDo(opsDo, opsDoItem, new
	 * ArrayList<OpsDoItemInventory>(), new UserDto("分库采购调拨", "0.0.0.0"));
	 * opsRoService.insertRo(opsRo, roItemList, new
	 * ArrayList<OpsRoItemInventory>(), new UserDto("分库采购调拨", "0.0.0.0"));
	 * OpsWmOrderTask opsWmOrderTaskDO = new OpsWmOrderTask();
	 * opsWmOrderTaskDO.setWmOrderId(dbckid);
	 * opsWmOrderTaskDO.setWmOrderType(WmOrderTaskEnum.DO.getType());
	 * opsWmOrderTaskDO.setTaskType(WmOrderTaskEnum.ORDER.getType());
	 * opsWmOrderTaskDO.setFlag(Integer.valueOf(SendStatusEnum.READ.getType()));
	 * opsWmOrderTaskDO.setCreator("分库采购调拨"); opsWmOrderTaskDO.setCreTime(new
	 * Date()); // wmOrderTaskService.addOpsWmOrderTask(opsWmOrderTaskDO);
	 * taskList.add(opsWmOrderTaskDO); OpsWmOrderTask opsWmOrderTaskRO = new
	 * OpsWmOrderTask(); opsWmOrderTaskRO.setWmOrderId(dbrkid);
	 * opsWmOrderTaskRO.setWmOrderType(WmOrderTaskEnum.RO.getType());
	 * opsWmOrderTaskRO.setTaskType(WmOrderTaskEnum.ORDER.getType());
	 * opsWmOrderTaskRO.setFlag(Integer.valueOf(SendStatusEnum.READ.getType()));
	 * opsWmOrderTaskRO.setCreator("分库采购调拨"); opsWmOrderTaskRO.setCreTime(new
	 * Date()); taskList.add(opsWmOrderTaskRO); //
	 * wmOrderTaskService.addOpsWmOrderTask(opsWmOrderTaskRO); } } } else { //
	 * 写入状态变更事件表
	 * orderEventService.customerOrderEventGenerator(CustomerOrderEventEnum.
	 * PURCHASE_SENT_PO, opsRequestpurchase.getOrderno(),
	 * opsRequestpurchase.getItemno().toString(),
	 * opsRequestpurchase.getSplititemno() == null ? 0 :
	 * opsRequestpurchase.getSplititemno(),
	 * JSONUtil.toJsonStr(opsPurchaseorder)); }
	 * orderStateService.sendOrderStateForSendPurchase(opsPurchaseorder); } //
	 * 批量更新task wmOrderTaskService.addBatchOpsWmOrderTask(taskList); }
	 */

	/**
	 * @description： 采购单转订取消
	 *
	 * @author C12961
	 * @date 2022/3/9 12:19
	 */
	/*
	 * @Override public int resetForPo(OpsPurchaseorder opsPurchaseorder) throws
	 * OpsException { String poNo = PoNoUtil.getFullPoNo(opsPurchaseorder);
	 * log.info("采购转订取消：" + poNo); OpsInventoryMoveExample moveEx = new
	 * OpsInventoryMoveExample(); moveEx.createCriteria().andDelflagEqualTo(0)
	 * .andAssociateNoEqualTo(opsPurchaseorder.getOrderno())
	 * .andAssociateNoItemEqualTo(opsPurchaseorder.getItemno())
	 * .andAssociateNoSplitnoEqualTo(PoNoUtil.getSplitNo(opsPurchaseorder));
	 * //根据采购单号查询在途库存 List<OpsInventoryMove> opsInventoryMoves =
	 * opsInventoryMoveMapper.selectByExample(moveEx); if
	 * (!CollectionUtils.isEmpty(opsInventoryMoves)) { for (OpsInventoryMove
	 * move : opsInventoryMoves) { log.info(JSONUtil.toJsonStr(move)); boolean
	 * exist = baseCustomerOrderService.isRcvDetailExist(move.getOrderno(),
	 * move.getItemno()); if (exist) {
	 * orderEventService.customerOrderEventGenerator(CustomerOrderEventEnum.
	 * PURCHASE_RESET, move.getOrderno(), move.getItemno().toString(),
	 * move.getSplititemno(), move); } } } return 1; }
	 */

	/**
	 * @description：采购单已接单回执
	 * @author ：c02483 一次性传PO采购，请购
	 * @date ：Created in 2021/10/3 15:22
	 */
	/*
	 * public void acceptForPo(PoForAcceptDto dto) throws OpsException { if
	 * (null == dto) { throw Exceptions.OpsException("无接单数据"); } // 数据判断
	 * OpsPurchaseorder purchase = dto.getPurchaseorder(); if
	 * (!StringUtils.isNotEmpty(purchase.getReceivewarehouseid())) { throw
	 * Exceptions.OpsException("导入数据.库房不可空。" + purchase.getOrderno()); } if
	 * (purchase.getQuantity() < 1) { throw
	 * Exceptions.OpsException("导入订单采购数量需大于0。" + purchase.getOrderno()); } int
	 * qty = 0; for (OpsRequestpurchase requestPurchase :
	 * dto.getRequestpurchaseList()) { qty += requestPurchase.getQuantity(); }
	 * // 判断请购数量大于采购数量 if (qty > purchase.getQuantity()) { throw
	 * Exceptions.OpsException("导入请购总数不可大于采购数量。" + purchase.getOrderno()); } //
	 * 请购的判断 for (OpsRequestpurchase requestPurchase :
	 * dto.getRequestpurchaseList()) { // 请购库房不能空 if
	 * (StringUtils.isEmpty(requestPurchase.getRequestwarehouseid())) { throw
	 * Exceptions.OpsException("导入请购库房有误。" + purchase.getOrderno()); } //
	 * 请购库房不能空 if (StringUtils.isEmpty(requestPurchase.getInventorytypecode()))
	 * { throw Exceptions.OpsException("导入库存类别有误，不可为空。" +
	 * purchase.getOrderno()); } } List<OpsInventoryMove> poInventoryMoves =
	 * baseInventoryService .findInventoryMoveByPo(purchase.getOrderno(),
	 * purchase.getItemno(), purchase.getSplititemno()); //进入采购转订逻辑 if (null !=
	 * poInventoryMoves && poInventoryMoves.size() > 0) { // 除去P以外,存在P,T1,T3,不更变
	 * for (OpsInventoryMove inventoryMove : poInventoryMoves) { if
	 * (!InventoryStatusEnum.PRODUCE.getCode().equalsIgnoreCase(inventoryMove.
	 * getInventoryStatus())) { log.info("订单已接单，且同时存在非P生产中数据，不可更变供应商ID：" +
	 * purchase.getOrderno() + "-" + purchase.getItemno().toString()); throw
	 * Exceptions.OpsException("订单已接单，且同时存在非P生产中数据，不可更变供应商ID：" +
	 * PoNoUtil.getFullPoNo(purchase)); } } // bug：10039 2023-03-17 C12961 去除校验
	 * 【转订前后采购单供应商和签收仓是否一致】 // 更新在途库存，供应商与签收仓 for (OpsInventoryMove
	 * inventoryMove : poInventoryMoves) {
	 * baseInventoryService.updateInventoryMoveConversionByInventoryId(
	 * inventoryMove.getInventoryId(), purchase.getSupplierid(),
	 * purchase.getReceivewarehouseid(), inventoryMove.getVersion()); } //发布事件
	 * for (OpsInventoryMove inventoryMove : poInventoryMoves) { if
	 * (!StringUtils.isEmpty(inventoryMove.getOrderno())) {
	 * orderEventService.customerOrderEventGenerator(CustomerOrderEventEnum.
	 * PURCHASE_RECEIVE, inventoryMove.getOrderno(),
	 * inventoryMove.getItemno().toString(), inventoryMove.getSplititemno(),
	 * JSONUtil.toJsonStr(purchase)); } } // 跳出语句 return; } qty = 0; // 预约数 int
	 * prepareQuantity = 0; long inventoryId = 0L; for (OpsRequestpurchase
	 * requestPurchase : dto.getRequestpurchaseList()) { //
	 * 类型不是BIN(BIN补库订单),PRE(先行补库订单),DR(DR补库订单),CR(CR补库订单)，WT(委托在库补货订单)才会产生占用库存（
	 * 样品，销售订单，一般贸易） if (!OrderTypeEnum.BIN.equals(requestPurchase.getOrdtype())
	 * && !OrderTypeEnum.PRE.equals(requestPurchase.getOrdtype()) &&
	 * !OrderTypeEnum.DR.equals(requestPurchase.getOrdtype()) &&
	 * !OrderTypeEnum.CR.equals(requestPurchase.getOrdtype()) &&
	 * !OrderTypeEnum.WT.equals(requestPurchase.getOrdtype())) { prepareQuantity
	 * = requestPurchase.getQuantity(); } else { // 类型BIN、PRE DR/CR
	 * 需判断采购仓与请购仓库不同加预约数（关联DO采购类型还是用交易出库JYCK，采购调拨出库CGDBCK） if
	 * (!requestPurchase.getRequestwarehouseid()
	 * .equalsIgnoreCase(dto.getPurchaseorder().getReceivewarehouseid())) {
	 * prepareQuantity = requestPurchase.getQuantity(); } } OpsInventoryProperty
	 * property = new OpsInventoryProperty();
	 * property.setInventoryTypeCode(requestPurchase.getInventorytypecode());
	 * property.setGroupCustomerNo(requestPurchase.getGroupcustomerno());
	 * property.setCustomerNo(requestPurchase.getCustomerno());
	 * //如存最终用户，使用最终用户库存属性 2022-10-10 if
	 * (!StringUtils.isEmpty(requestPurchase.getUserno())) {
	 * property.setCustomerNo(requestPurchase.getUserno()); }
	 * property.setPpl(requestPurchase.getPpl());
	 * property.setProjectCode(requestPurchase.getProjectcode()); long
	 * inventoryPropertyId =
	 * opsInventoryPropertyService.findPropertyWithInsertByExample(property, new
	 * UserDto("acceptForPo", null)); OpsInventoryMove inventory = new
	 * OpsInventoryMove();
	 * inventory.setSignWarehouseCode(purchase.getReceivewarehouseid());// 采购仓
	 * inventory.setWarehouseCode(requestPurchase.getRequestwarehouseid());//
	 * 请购仓 inventory.setInventoryStatus(InventoryStatusEnum.PRODUCE.getCode());
	 * inventory.setQaStatus(QAStatusEnum.NORMAL.getType()); // 增加在途库存数
	 * inventory.setQuantity(requestPurchase.getQuantity());
	 * inventory.setPoQty(requestPurchase.getQuantity());
	 * inventory.setPrepareQuantity(prepareQuantity);
	 * inventory.setModelno(requestPurchase.getModelno());
	 * inventory.setInventoryPropertyId(inventoryPropertyId);
	 * inventory.setOrderno(requestPurchase.getOrderno());
	 * inventory.setItemno(requestPurchase.getItemno());
	 * inventory.setSplititemno(requestPurchase.getSplititemno());
	 * inventory.setSupplierid(purchase.getSupplierid());//使用采购采购 //
	 * 该字段为请购原始值不转1（用于后期分析数据使用）
	 * inventory.setPrice(requestPurchase.getStdprice());
	 * inventory.setAssociateNo(purchase.getOrderno());// 采购票号
	 * inventory.setAssociateNoItem(purchase.getItemno());// 采购票号Item
	 * inventory.setAssociateNoSplitno(purchase.getSplititemno());//
	 * 采购票号Splititemno
	 * inventory.setSourceType(SourceTypeEnum.PURCHASE.getType());// 来源
	 * inventory.setGreencode(purchase.getGreencode());// 绿色标记值是：H
	 * inventory.setOptStatus(InventoryMoveOpsStatusEnum.INVONICE_ACCEPT.getCode
	 * ());// 1采购接单 baseInventoryService.createInvMoveReturnId(inventory,
	 * UserDto.WMS); inventoryId = inventory.getInventoryId(); int rows = 0; qty
	 * = qty + requestPurchase.getQuantity(); // 预约数 prepareQuantity = 0; //
	 * 整型号采购 if
	 * (WMPurchaseTagEnum.WHOLE.getType().equals(requestPurchase.getWmtag())) {
	 * // 查询DO(交易出库单) List<OpsDo> doList =
	 * baseDoService.findDoListByOrder(requestPurchase.getOrderno(),
	 * requestPurchase.getItemno().toString(), requestPurchase.getSplititemno(),
	 * DoTypeEnum.JYCK); for (OpsDo opsDo : doList) { // DOItem OpsDoItem doItem
	 * = baseDoService.getDoItemByDoId(opsDo.getDoId()); OpsDoItemInventory
	 * doItemInventory = new OpsDoItemInventory();
	 * doItemInventory.setDoId(opsDo.getDoId());
	 * doItemInventory.setDoItem(doItem.getDoItem());
	 * doItemInventory.setInventoryId(inventoryId);
	 * doItemInventory.setInventoryTableType(InventoryTableTypeEnum.TRANS.
	 * getType()); doItemInventory.setUseQty(requestPurchase.getQuantity());//
	 * 当前PO在途ID，对应的数量 doItemInventory.setOutQty(0);
	 * doItemInventory.setSortnum(++rows); List<OpsDoItemInventory>
	 * doItemInventoryList = new ArrayList<>();
	 * doItemInventoryList.add(doItemInventory);
	 * opsDoService.insertDoItemInventoryList(doItemInventoryList);
	 * prepareQuantity += requestPurchase.getQuantity(); } // 查询DO(采购调拨出库)
	 * doList = baseDoService.findDoListByOrder(requestPurchase.getOrderno(),
	 * requestPurchase.getItemno().toString(), requestPurchase.getSplititemno(),
	 * DoTypeEnum.CGDBCK); for (OpsDo opsDo : doList) { // DOItem OpsDoItem
	 * doItem = baseDoService.getDoItemByDoId(opsDo.getDoId());
	 * OpsDoItemInventory doItemInventory = new OpsDoItemInventory();
	 * doItemInventory.setDoId(opsDo.getDoId());
	 * doItemInventory.setDoItem(doItem.getDoItem());
	 * doItemInventory.setInventoryId(inventoryId);
	 * doItemInventory.setInventoryTableType(InventoryTableTypeEnum.TRANS.
	 * getType()); doItemInventory.setUseQty(requestPurchase.getQuantity());//
	 * 当前PO在途ID，对应的数量 doItemInventory.setOutQty(0);
	 * doItemInventory.setSortnum(++rows); List<OpsDoItemInventory>
	 * doItemInventoryList = new ArrayList<>();
	 * doItemInventoryList.add(doItemInventory);
	 * opsDoService.insertDoItemInventoryList(doItemInventoryList);
	 * prepareQuantity += requestPurchase.getQuantity(); } } // 拆分采购 else if
	 * (WMPurchaseTagEnum.ASS.getType().equals(requestPurchase.getWmtag())) {
	 * List<OpsPco> pcoList =
	 * opsPcoService.GetPcolistByOrder(requestPurchase.getOrderno(),
	 * requestPurchase.getItemno().toString()); rows = 0; for (OpsPco opsPco :
	 * pcoList) { List<OpsPcoItem> pcoItemList =
	 * opsPcoService.GetPcoItemByOrder(opsPco.getPcoId(),
	 * inventory.getModelno()); for (OpsPcoItem pcoItem : pcoItemList) {
	 * OpsPcoItemInventory pcoItemInventory = new OpsPcoItemInventory();
	 * pcoItemInventory.setPcoId(opsPco.getPcoId());
	 * pcoItemInventory.setPcoItem(pcoItem.getPcoItem());
	 * pcoItemInventory.setUseQty(requestPurchase.getQuantity());
	 * pcoItemInventory.setInventoryId(inventoryId);
	 * pcoItemInventory.setVersion(0L); pcoItemInventory.setDelflag(0);
	 * pcoItemInventory.setCreator(UserDto.ADMIN.getUserName());
	 * pcoItemInventory.setCreTime(new Date());
	 * pcoItemInventory.setPcoType(opsPco.getPcoType());// 1拆分2组装
	 * pcoItemInventory.setInventoryTableType(InventoryTableTypeEnum.TRANS.
	 * getType()); pcoItemInventory.setSortnum(++rows);
	 * opsPcoService.insertInventoryItem(pcoItemInventory); prepareQuantity +=
	 * requestPurchase.getQuantity(); } } } else { throw
	 * Exceptions.OpsException("采购组装标识错误。WMTag类型只有W,A" +
	 * requestPurchase.getOrderno()); } // 订单类型DR、CR 如有DOID关联，增加预约数 会存在DR,CR有客单
	 * if (OrderTypeEnum.DR.equals(requestPurchase.getOrdtype()) ||
	 * OrderTypeEnum.CR.equals(requestPurchase.getOrdtype())) { if
	 * (prepareQuantity > 0) {
	 * baseInventoryService.addPreQtyInventory(inventoryId, prepareQuantity,
	 * InventoryTableTypeEnum.TRANS.getType(), UserDto.WMS); } } // 写入状态变更事件表 if
	 * ("A".equals(requestPurchase.getPurchasetype())) {
	 * orderEventService.customerOrderEventGenerator(CustomerOrderEventEnum.
	 * PURCHASE_RECEIVE, requestPurchase.getOrderno(),
	 * requestPurchase.getItemno().toString(), requestPurchase.getSplititemno(),
	 * JSONUtil.toJsonStr(purchase)); } } // 合并采购多出部分，需新增行数据 if
	 * (purchase.getQuantity() > qty) { int leftQty = purchase.getQuantity() -
	 * qty; OpsInventoryMove inventory = new OpsInventoryMove();
	 * inventory.setSignWarehouseCode(purchase.getReceivewarehouseid());
	 * inventory.setWarehouseCode(purchase.getReceivewarehouseid());
	 * inventory.setInventoryStatus(InventoryStatusEnum.PRODUCE.getCode());
	 * inventory.setQaStatus(QAStatusEnum.NORMAL.getType()); // 增加在途库存数
	 * inventory.setQuantity(leftQty);
	 * inventory.setPoQty(purchase.getQuantity());
	 * inventory.setPrepareQuantity(0);
	 * inventory.setModelno(purchase.getModelno());
	 * inventory.setInventoryPropertyId(1L); inventory.setOrderno(null);
	 * inventory.setItemno(null); inventory.setSplititemno(null);
	 * inventory.setPrice(null);
	 * inventory.setAssociateNo(purchase.getOrderno());// 采购票号
	 * inventory.setAssociateNoItem(purchase.getItemno());// 采购票号Item
	 * inventory.setAssociateNoSplitno(purchase.getSplititemno());//
	 * 采购票号Splititemno
	 * inventory.setSourceType(SourceTypeEnum.PURCHASE.getType());// 来源采购
	 * inventory.setGreencode(purchase.getGreencode());// 绿色标记值是：H
	 * inventory.setOptStatus(InventoryMoveOpsStatusEnum.INVONICE_ACCEPT.getCode
	 * ());// 1采购接单 inventory.setRemark("因合并采购,生成的行数据");
	 * baseInventoryService.createInvMoveReturnId(inventory, UserDto.WMS); } }
	 */

	/**
	 * @description：预到货发票导入(按发票一采购单，一次性导入) @author ：c02483 多到货取比PO数多，取最多数
	 * @date ：Created in 2021/11/24 14:23
	 */
	public void invoiceForPoInit(List<OpsPurchaseinvoice> list) throws OpsException {
		//【校验
		if (CollectionUtils.isEmpty(list)) {
			throw Exceptions.OpsException("无预到货发票导入数据");
		}
		// 数据校验
		for (OpsPurchaseinvoice invoicedto : list) {
			if (StringUtils.isEmpty(invoicedto.getInvoiceno())) {
				throw Exceptions.OpsException("发票号不可为空。" + JSON.toJSONString(invoicedto));
			}
			if (invoicedto.getQtytrans() < 1) {
				throw Exceptions.OpsException("无预到货发票导入数据.数量不可0。" + invoicedto.getPono());
			}
		}
		//校验】
		for (OpsPurchaseinvoice invoicedto : list) {
			// 在途型号P生产中（按PO）
			List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListByPo(
					InventoryStatusEnum.PRODUCE, invoicedto.getModelno(), invoicedto.getOrderno(),
					invoicedto.getItemno(), invoicedto.getSplititemno(), null);
			//【校验
			if (CollectionUtils.isEmpty(inventoryMoveList)) {
				int splitNo = 0;
				if (null != invoicedto.getSplititemno()) {
					splitNo = invoicedto.getSplititemno();
				}
				throw Exceptions.OpsException("订单号：" + invoicedto.getOrderno() + "-" + invoicedto.getItemno().toString()
						+ "-" + String.valueOf(splitNo) + ".未接单");
			}
			// 一个PO一个发票号只接受一次，重复不继续执行(T1)
			List<OpsInventoryMove> exitsInventoryMoveList = baseInventoryService.getOpsInventoryMoveListByPo(
					InventoryStatusEnum.CGTRANS, invoicedto.getModelno(), invoicedto.getOrderno(),
					invoicedto.getItemno(), invoicedto.getSplititemno(), invoicedto.getInvoiceno());
			if (!CollectionUtils.isEmpty(exitsInventoryMoveList)) {
				int splitNo = 0;
				if (null != invoicedto.getSplititemno()) {
					splitNo = invoicedto.getSplititemno();
				}
				throw Exceptions
						.OpsException("发票预导入，订单号：" + invoicedto.getOrderno() + "-" + invoicedto.getItemno().toString()
								+ "-" + String.valueOf(splitNo) + ".已存在该发票号" + invoicedto.getInvoiceno() + "。不可重复导入");
			}
			//校验】
			// 【在途运输数】
			int qty = invoicedto.getQtytrans();
			//【供应商接单数】
			Integer moveTotalQty = 0;
			for (OpsInventoryMove inventoryMove : inventoryMoveList) {
				moveTotalQty += inventoryMove.getQuantity();
			}
			// 预到货多到，改为在途=在途数一样（不接受多到的情况） 2022-08-08
			if (qty > moveTotalQty) {
				qty = moveTotalQty;
			}
			//查询所有的关联关系的十位单号
			List<NotifyShipmentCondition> notifyOrderList = createNotifyDeliveryDto(inventoryMoveList,"invoiceForPoInit");
			// 供应商接单=在途数，一次性到货P改T1
			if (qty == moveTotalQty) {
				for (OpsInventoryMove inventoryMove : inventoryMoveList) {
					// 更新在途发票号 imDateTheory 理论到达仓库时间
					inventoryMove.setPrereceivedate(invoicedto.getImdatetheory());
					baseInventoryService.UpdateStatusInventoryMoveForCG(invoicedto, inventoryMove);
				}
			}
			// 来多货，需要把多出部分改在途中数量
			else if (qty > moveTotalQty) {
				// 预到货多到，改为在途=在途数一样（不接受多到的情况）,以下代码为备用暂不注销 2022-08-08
				int leftQty = qty;
				for (OpsInventoryMove inventoryMove : inventoryMoveList) {
					// 大于或者等于，修改P=>T1
					if (leftQty >= inventoryMove.getQuantity()) {
						// 更新在途发票号 where=invoicedto update=inventoryMove
						inventoryMove.setPrereceivedate(invoicedto.getImdatetheory());
						baseInventoryService.UpdateStatusInventoryMoveForCG(invoicedto, inventoryMove);
						leftQty = leftQty - inventoryMove.getQuantity();
						// 把原来P改T1的在途ID
					}
				}
				// 多到部分，判断是不是合并采购（orderno=null）意思没有DO关联就不用再新增行数，直接使用原来行改数量即可
				boolean isMergePo = false;
				Long inventoryId = 0L;
				Long version = 0L;
				for (OpsInventoryMove inventoryMove : inventoryMoveList) {
					if (inventoryMove.getInventoryPropertyId() == 1L) {
						isMergePo = true;
						inventoryId = inventoryMove.getInventoryId();
						version = inventoryMove.getVersion();
					}
				}
				// 多出来部分追加到库存
				if (isMergePo) {
					baseInventoryService.updateQtyInventoryMoveWithPre(inventoryId, leftQty, 0, version + 1,
							UserDto.WMS);
				}
				// 如果都是请购，多生成一行库存生成在途数据T1(不被其他DO占用，仅补库使用)
				else {
					OpsInventoryMove tinventoryMove = new OpsInventoryMove();
					tinventoryMove.setWarehouseCode(inventoryMoveList.get(0).getWarehouseCode());
					tinventoryMove.setSignWarehouseCode(inventoryMoveList.get(0).getSignWarehouseCode());
					// 采购在途
					tinventoryMove.setInventoryStatus(InventoryStatusEnum.CGTRANS.getCode());
					tinventoryMove.setPoQty(inventoryMoveList.get(0).getPoQty());
					tinventoryMove.setQuantity(leftQty);
					tinventoryMove.setUnit(inventoryMoveList.get(0).getUnit());
					tinventoryMove.setQaStatus(inventoryMoveList.get(0).getQaStatus());
					tinventoryMove.setPrepareQuantity(0);// 多出来作为库存
					tinventoryMove.setModelno(inventoryMoveList.get(0).getModelno());
					tinventoryMove.setInventoryPropertyId(1L);
					tinventoryMove.setAssociateNo(inventoryMoveList.get(0).getAssociateNo());
					tinventoryMove.setAssociateNoItem(inventoryMoveList.get(0).getAssociateNoItem());
					tinventoryMove.setAssociateNoSplitno(inventoryMoveList.get(0).getAssociateNoSplitno());
					tinventoryMove.setSupplierid(invoicedto.getSupplierid());
					// imDateTheory 理论到达仓库时间
					tinventoryMove.setPrereceivedate(invoicedto.getImdatetheory());
					tinventoryMove.setSourceType(SourceTypeEnum.PURCHASE.getType());
					tinventoryMove.setGreencode(invoicedto.getGreencode());// 绿色标记值是：H
					tinventoryMove.setOptStatus(OptStatusEnum.PURCHASE_ACCEPT.getCode());// 操作状态
					tinventoryMove.setRemark("预导入发票,数量到多生成行数据");
					// 都出部分插入采购在途（P）
					baseInventoryService.createInvMoveReturnId(tinventoryMove, UserDto.ADMIN);
					leftQty = 0;
				}
			}
			// 来少货，需要把原来一条拆成2条:T1跟P
			else if (qty < moveTotalQty) {
				// 此次PO来货数量
				int leftQty = qty;
				for (OpsInventoryMove inventoryMove : inventoryMoveList) {
					if (leftQty <= 0) {
						break;
					}
					//修改P=>T1
					//剩余数大于等于PO行数
					if (leftQty >= inventoryMove.getQuantity()) {
						// 更新在途发票号 where=invoicedto update=inventoryMove
						inventoryMove.setPrereceivedate(invoicedto.getImdatetheory());
						baseInventoryService.UpdateStatusInventoryMoveForCG(invoicedto, inventoryMove);
						List<OpsDoItemInventory> doItemInventoryList = baseDoService
								.getDoItemInventoryByInventoryId(inventoryMove.getInventoryId(),
										InventoryTableTypeEnum.TRANS);
						leftQty = leftQty - inventoryMove.getQuantity();
					}
					// 剩余数小于PO行数 进行拆分P=>P+T1（仅对当前行拆分: 1.维持P改拆分数量，2.新增T1）15 25
					else if (leftQty < inventoryMove.getQuantity()) {
						int splitTQty = leftQty;// 拆分t1（新增行）
						//【拆分P=>P+T1】
						Long TinventoryMoveid = splitInventoryProduct(invoicedto, inventoryMove, leftQty);
						//【Do拆分关联关系】
						// do分配到原来分配到的库存ID，重新更新到DO。InventoryId 存在多个DOID行对应
						List<OpsDoItemInventory> doItemInventoryList = baseDoService
								.getDoItemInventoryByInventoryId(inventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
						// PO数不够分配时
						for (OpsDoItemInventory doItemInventory : doItemInventoryList) {
							if (splitTQty <= 0) {
								break;
							}
							OpsDo opsDo = baseDoService.getDoByDoId(doItemInventory.getDoId());
							if (Objects.isNull(opsDo)) {
								throw Exceptions.OpsException("存在DOID关联库存数据，DO表无数据。DOID:" + doItemInventory.getDoId());
							}
							// 在途的数>=当前DOID分配数，直接改PinventoryId为T1inventoryId
							if (splitTQty >= doItemInventory.getUseQty()) {
								// 更变doItemInventory.TinventoryMoveid 为在途T行
								int i = opsDoService.updateOpsDoItemInventory(doItemInventory.getId(),
										doItemInventory.getVersion(), doItemInventory.getUseQty(), TinventoryMoveid,
										InventoryTableTypeEnum.TRANS, UserDto.WMS);
								if (i < 1) {
									throw Exceptions.OpsException("系统忙，请稍后再试");
								}
								splitTQty = splitTQty - doItemInventory.getUseQty();
							}
							// 在途的数<当前DOID分配数，插入新的T1关联关系
							else if (splitTQty < doItemInventory.getUseQty()) {
								int oldLeft = doItemInventory.getUseQty() - splitTQty;
								//修改P的doItemInventory的数量
								int i = opsDoService.updateOpsDoItemInventory(doItemInventory.getId(),
										doItemInventory.getVersion(), oldLeft, doItemInventory.getInventoryId(),
										InventoryTableTypeEnum.TRANS, UserDto.WMS);
								if (i < 1) {
									throw Exceptions.OpsException("系统忙，请稍后再试");
								}
								//创建新的T1关联关系
								OpsDoItemInventory tdoItemInventory = new OpsDoItemInventory();
								tdoItemInventory.setDoId(doItemInventory.getDoId());
								tdoItemInventory.setDoItem(doItemInventory.getDoItem());
								tdoItemInventory.setInventoryId(TinventoryMoveid);
								tdoItemInventory.setInventoryTableType(doItemInventory.getInventoryTableType());
								tdoItemInventory.setUseQty(splitTQty);// 多出来部分是新生成在途ID
								tdoItemInventory.setOutQty(0);
								tdoItemInventory.setSortnum(0);
								opsDoService.insertDoItemInventory(tdoItemInventory);
								List<OpsDoItemInventory> doItemInventorys = baseDoService.getDoItemInventoryByInventoryId(TinventoryMoveid, InventoryTableTypeEnum.TRANS);
								splitTQty = 0;
							}
						}
						//【Pco拆分关联关系】
						List<OpsPcoItemInventory> pcoItemInventoryList = opsPcoService
								.getOpsPcoItemInventoryByInventoryId(inventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
						for (OpsPcoItemInventory pcoItemInventory : pcoItemInventoryList) {
							OpsPco opsPco = opsPcoService.selectPcoBypcoId(pcoItemInventory.getPcoId());
							if (Objects.isNull(opsPco)) {
								throw Exceptions.OpsException("存在DOID关联库存数据，DO表无数据。DOID:" + pcoItemInventory.getPcoId());
							}
							if (splitTQty <= 0) {
								break;
							}
							//关联关系PInventoryId改T1inventoryId
							if (splitTQty >= pcoItemInventory.getUseQty()) {
								int i = opsPcoService.updatetOpsPcoItemInventory(pcoItemInventory.getId(),
										pcoItemInventory.getVersion(), pcoItemInventory.getUseQty(), TinventoryMoveid,
										InventoryTableTypeEnum.TRANS, UserDto.WMS);
								if (i < 1) {
									throw Exceptions.OpsException("系统忙，请稍后再试");
								}
								splitTQty = splitTQty - pcoItemInventory.getUseQty();
							}
							// 在途的数<当前PCOID分配数，插入新的T1关联关系
							else if (splitTQty < pcoItemInventory.getUseQty()) {
								int oldLeft = pcoItemInventory.getUseQty() - splitTQty;
								opsPcoService.updatetOpsPcoItemInventory(pcoItemInventory.getId(),
										pcoItemInventory.getVersion(), oldLeft, pcoItemInventory.getInventoryId(),
										InventoryTableTypeEnum.TRANS, UserDto.WMS);
								//创建新的T1PcoItemInventory
								OpsPcoItemInventory tpcoItemInventory = new OpsPcoItemInventory();
								tpcoItemInventory.setPcoId(pcoItemInventory.getPcoId());// 如果有PCOOID，新增
								tpcoItemInventory.setPcoItem(pcoItemInventory.getPcoItem());
								tpcoItemInventory.setPcoType(pcoItemInventory.getPcoType());
								tpcoItemInventory.setInventoryId(TinventoryMoveid);
								tpcoItemInventory.setInventoryTableType(InventoryTableTypeEnum.TRANS.getType());// 在途中
								tpcoItemInventory.setUseQty(splitTQty);// 多出来部分是新生成在途ID
								tpcoItemInventory.setSortnum(0);
								opsPcoService.insertInventoryItem(tpcoItemInventory);
								splitTQty = 0;
							}
						}
						// 把剩余数改0
						leftQty = 0;
					}
				}
			}
			//通知发货的处理逻辑
			for (NotifyShipmentCondition condition : notifyOrderList) {
				doPcoLogicCenterService.splitOrderByDlvEntry(condition);
			}
		}
	}

	private Long splitInventoryProduct(OpsPurchaseinvoice invoicedto, OpsInventoryMove inventoryMove, int leftQty) throws OpsException {
		// 写入在途T1占预约数 更新在途T1占预约数 要扣减的数量 为同一个值
		int preQty = 0;
		// 出来的部分大于等于预约数默认多出来的（多出来默认新写入行T1）
		if (inventoryMove.getPrepareQuantity() > 0) {
			preQty = Math.min(inventoryMove.getPrepareQuantity(), leftQty);
		}
		// P
		baseInventoryService.updateQtyInventoryMoveWithPre(inventoryMove.getInventoryId(), -leftQty,
				-preQty, inventoryMove.getVersion(), UserDto.WMS);
		// 生成在途数据T1
		OpsInventoryMove tinventoryMove = new OpsInventoryMove();
		tinventoryMove.setWarehouseCode(inventoryMove.getWarehouseCode());
		tinventoryMove.setSignWarehouseCode(inventoryMove.getSignWarehouseCode());
		// 采购在途
		tinventoryMove.setInventoryStatus(InventoryStatusEnum.CGTRANS.getCode());
		tinventoryMove.setPoQty(inventoryMove.getPoQty());
		tinventoryMove.setQuantity(leftQty);// 多出货=T1
		tinventoryMove.setPrepareQuantity(preQty);
		tinventoryMove.setUnit(inventoryMove.getUnit());
		tinventoryMove.setQaStatus(inventoryMove.getQaStatus());
		tinventoryMove.setModelno(inventoryMove.getModelno());
		tinventoryMove.setInventoryPropertyId(inventoryMove.getInventoryPropertyId());
		tinventoryMove.setOrderno(inventoryMove.getOrderno());
		tinventoryMove.setItemno(inventoryMove.getItemno());
		tinventoryMove.setSplititemno(inventoryMove.getSplititemno());
		tinventoryMove.setAssociateNo(inventoryMove.getAssociateNo());
		tinventoryMove.setAssociateNoItem(inventoryMove.getAssociateNoItem());
		tinventoryMove.setAssociateNoSplitno(inventoryMove.getAssociateNoSplitno());
		tinventoryMove.setSupplierid(invoicedto.getSupplierid());
		tinventoryMove.setInvoiceid(invoicedto.getInvoiceid());
		tinventoryMove.setInvoiceno(invoicedto.getInvoiceno());
		// imDateTheory 理论到达仓库时间
		tinventoryMove.setPrereceivedate(invoicedto.getImdatetheory());
		tinventoryMove.setSourceType(SourceTypeEnum.PURCHASE.getType());
		tinventoryMove.setGreencode(invoicedto.getGreencode());// 绿色标记值是：H
		tinventoryMove.setOptStatus(OptStatusEnum.INVOICE_INIT.getCode());// 操作状态
		// 插入采购在途（T1）
		Long TinventoryMoveid = baseInventoryService.createInvMoveReturnId(tinventoryMove,
				UserDto.WMS);
		return TinventoryMoveid;
	}

	@Override
	public void invoiceForPoUpdate(List<OpsPurchaseinvoice> OpsPurchaseinvoices) throws OpsException {
		MapContainer<Long, OpsPurchaseinvoice> container = new MapContainer<>();
		for (OpsPurchaseinvoice purchase : OpsPurchaseinvoices) {
			container.put(purchase.getInvoiceid(), purchase);
		}
		container.forEach((invoice, list) -> {
			OpsPurchaseinvoice purchase = list.get(0);
			Date imDateTheory = purchase.getImdatetheory();
			int i = baseInventoryService.updatePreReceiveDateByInvoiceId(invoice, imDateTheory);
		});
	}

	/**
	 * @description：发票导入（按发票号导入） @description：分纳到货的时候+出库方式（整单集约单仓出库、单项集约整仓出库、随到随发）
	 *                           当做特发的时候，影响销售单、加工单出库
	 * @author ：c02483
	 * @date ：Created in 2021/10/3 15:21//必须一票一票导入
	 * 校验：一次请求只能有一个发票号，一个签收仓
	 * 查询本发票的所有moveT1，对比来货po号和moves的po号是否一致，如果move没来货，则将moveT1改回moveP
	 *
	 * ImpPoNoList.forEach(poNo -> {
	 *     无move的po来货数据算多到,创建多到货moveT1，无预占
	 *     有optStatus=3的算重复导入，抛异常
	 *     本票本po来箱总数不能小于本po本票的预到货T1数，否则抛出
	 *     本票本po来箱总数大于预到货T1数，不修改原T1,
	 *     		直接将本po的P转换为T1
	 *     		或者将P拆分为P和T1，将PdoItemInventory删除，重新生成PdoItemInventory和T1doItemInventory,无pcoItemInventory
	 *     	本票本po来箱总数大于T1+P，创建多到货moveT1，无预占
	 * })
	 *
	 * 修改do集约仓、创建调拨单
	 * 查询该发票号的所有move，变更签收仓
	 *
	 *创建Task:ro和roBarcode，
	 * optStatus该3
	 */
	public void invoiceForPoConfirm(List<OpsImpdata> list) throws OpsException {
		if (Objects.isNull(list)) {
			throw Exceptions.OpsException("参数解析失败--List<OpsImpdata>");
		}
		if (CollectionUtils.isEmpty(list)) {
			throw Exceptions.OpsException("无发票导入数据");
		}
		// 校验判断
		for (OpsImpdata invoicedto : list) {
			if (StringUtils.isEmpty(invoicedto.getInvoiceno())) {
				throw Exceptions.OpsException("传入参数发票号空值");
			}
			if (null == invoicedto.getInvoiceid()) {
				throw Exceptions.OpsException("传入参数发票Id空值");
			}
			if (invoicedto.getQuantity() < 1) {
				throw Exceptions.OpsException("数量不可为空。" + invoicedto.getOrderno() + "-" + invoicedto.getItemno());
			}
		}
		// 判断一个发票是不是多个
		List<String> invoiceNos = list.stream().map(OpsImpdata::getInvoiceno).collect(Collectors.toList());
		if (invoiceNos.stream().distinct().count() > 1) {
			throw Exceptions.OpsException("请单票导入，不可多票同时导入");
		}
		List<Long> invoiceIds = list.stream().map(OpsImpdata::getInvoiceid).collect(Collectors.toList());
		if (invoiceIds.stream().distinct().count() > 1) {
			throw Exceptions.OpsException("请单票导入，不可多票ID同时导入");
		}
		// 签收仓库 不可多个
		List<String> signWarehouseCodes = list.stream().map(OpsImpdata::getReceivewarehouseid)
				.collect(Collectors.toList());
		if (signWarehouseCodes.stream().distinct().count() > 1) {
			throw Exceptions.OpsException("错误，传入签收仓只允许单个，不可多个签收仓库");
		}
		// 只有1个签收仓库（此时传过来是签收仓）如传过来签收仓与在途表签收仓不一致，按发票号修改签收仓
		String signWarehouseCode = list.get(0).getReceivewarehouseid();
		// 供应商ID
		String supplierid = list.get(0).getSupplierid();
		// 发票号
		String invoiceNo = list.get(0).getInvoiceno();
		Long invoiceId = list.get(0).getInvoiceid();

		// 按PO生成 (PO-List<OpsImpdata>)
		Map<OpsInventoryMove, List<OpsImpdata>> mapPoImpData = new HashMap<>();
		for (OpsImpdata invoicedto : list) {
			OpsInventoryMove inventoryMove = new OpsInventoryMove();
			inventoryMove.setAssociateNo(invoicedto.getOrderno());
			inventoryMove.setAssociateNoItem(invoicedto.getItemno());
			inventoryMove.setAssociateNoSplitno(invoicedto.getSplititemno());
			inventoryMove.setModelno(invoicedto.getModelno());
			inventoryMove.setInvoiceno(invoicedto.getInvoiceno());
			inventoryMove.setInvoiceid(invoicedto.getInvoiceid());
			inventoryMove.setGreencode(invoicedto.getGreencode());
			if (!mapPoImpData.containsKey(inventoryMove)) {
				List<OpsImpdata> implist = new ArrayList();
				implist.add(invoicedto);
				mapPoImpData.put(inventoryMove, implist);
			} else {
				mapPoImpData.get(inventoryMove).add(invoicedto);
			}
		}
		// 用于校验多出来po号基础数据
		Map<OpsInventoryMove, List<OpsImpdata>> mapDiffImpData = new HashMap<>();
		for (OpsImpdata invoicedto : list) {
			OpsInventoryMove diffInventoryMove = new OpsInventoryMove();
			diffInventoryMove.setAssociateNo(invoicedto.getOrderno());
			diffInventoryMove.setAssociateNoItem(invoicedto.getItemno());
			diffInventoryMove.setAssociateNoSplitno(
					Objects.isNull(invoicedto.getSplititemno()) ? 0 : invoicedto.getSplititemno());
			diffInventoryMove.setModelno(invoicedto.getModelno());
			diffInventoryMove.setInvoiceno(invoicedto.getInvoiceno());
			diffInventoryMove.setInvoiceid(invoicedto.getInvoiceid());
			if (!mapDiffImpData.containsKey(diffInventoryMove)) {
				List<OpsImpdata> diffImplist = new ArrayList();
				diffImplist.add(invoicedto);
				mapDiffImpData.put(diffInventoryMove, diffImplist);
			} else {
				mapDiffImpData.get(diffInventoryMove).add(invoicedto);
			}
		}
		//第一次查询move
		List<OpsInventoryMove> tInventoryMoveList = baseInventoryService.getOpsInventoryMoveListByInvoice(invoiceNo,
				signWarehouseCode);
		// 对比发票导入的po数据和预到货数据的po是否相对应
		List<OpsInventoryMove> mapDiffTMoveData = new ArrayList<>();
		// 处理预到货有PO号，发票导入无PO号。
		for (OpsInventoryMove tPoMove : tInventoryMoveList) {
			OpsInventoryMove diffInventoryMove = new OpsInventoryMove();
			diffInventoryMove.setAssociateNo(tPoMove.getAssociateNo());
			diffInventoryMove.setAssociateNoItem(tPoMove.getAssociateNoItem());
			diffInventoryMove.setAssociateNoSplitno(tPoMove.getAssociateNoSplitno());// NULL有默认值0，无需再次转换
			diffInventoryMove.setModelno(tPoMove.getModelno());
			diffInventoryMove.setInvoiceno(tPoMove.getInvoiceno());
			diffInventoryMove.setInvoiceid(tPoMove.getInvoiceid());
			if (!mapDiffTMoveData.contains(diffInventoryMove)) {
				mapDiffTMoveData.add(diffInventoryMove);
			}
		}
		// 发票导入中没有的po记录，需要将moveT1改回P
		// 遍历在途预到货存在T1数据但发票导入不存在PO，将T1改成P（还回去）
		for (OpsInventoryMove opsInventoryMove : mapDiffTMoveData) {
			if (!mapDiffImpData.containsKey(opsInventoryMove)) {
				log.info("预到货存在PO号发票数据，发票导入无PO号。PO:" + opsInventoryMove.getAssociateNo() + "-"
						+ opsInventoryMove.getAssociateNoItem() + "-" + opsInventoryMove.getAssociateNoSplitno());
				// T改P
				baseInventoryService.updateInventoryMoveTToP(opsInventoryMove.getInvoiceid(),
						opsInventoryMove.getInvoiceno(), opsInventoryMove.getAssociateNo(),
						opsInventoryMove.getAssociateNoItem(), opsInventoryMove.getAssociateNoSplitno());
			}
		}

		// 13550 优化异仓判断逻辑，将判断从po循环中挪到循环外，查询条件只保留inventory_status,invoice_no
		// 和invoice_id即可 2024-03-07
		//重新查询move,第二次查询
		List<OpsInventoryMove> tinventoryMoves = baseInventoryService
				.getOpsInventoryMoveListByInvoice(InventoryStatusEnum.CGTRANS, invoiceNo, invoiceId);
		// 传入仓库与PO签收仓库不一致,更变签收仓
		Boolean hasDiffSignWarehouse = tinventoryMoves.stream()
				.anyMatch(move -> !signWarehouseCode.equalsIgnoreCase(move.getSignWarehouseCode()));

		// 发票导入多到数量增加的行，不调拨出去，留在本仓当库存 2022-12-08
		List<Long> morePoInventoryMoveId = new ArrayList<>();
		// 无PO来货判断为多来货（多出来部分）
		Map<OpsInventoryMove, List<OpsImpdata>> mapMorePoInvbarcode = new HashMap<>();
		// 按PO遍历发票导入的数据，1.生成采购多发票导入的move和修改关联关系，
		for (Map.Entry<OpsInventoryMove, List<OpsImpdata>> entry : mapPoImpData.entrySet()) {
			// PO号
			OpsInventoryMove inventoryMove = entry.getKey();
			List<OpsImpdata> implist = entry.getValue();
			String orderNo = inventoryMove.getAssociateNo();
			Integer itemNo = inventoryMove.getAssociateNoItem();
			Integer splitNo = inventoryMove.getAssociateNoSplitno() == null ? 0 : inventoryMove.getAssociateNoSplitno();
			// po对应barocde总数量
			int bQty = 0;
			for (OpsImpdata impdata : implist) {
				bQty += impdata.getQuantity();
			}
			// 按PO号查清单(含T跟P以及W)
			List<OpsInventoryMove> inventoryMoveList = baseInventoryService.findInventoryMoveByPo(
					inventoryMove.getAssociateNo(), inventoryMove.getAssociateNoItem(),
					inventoryMove.getAssociateNoSplitno());
			// 无PO数据算多到
			if (CollectionUtils.isEmpty(inventoryMoveList)) {
				mapMorePoInvbarcode.put(entry.getKey(), implist);
				continue;
			}
			int pQty = 0;// 生成中
			int tQty = 0;// 在途中
			// int otherQty = 0;//其他
			// 生成中
			List<OpsInventoryMove> pinventoryMoveList = new ArrayList<>();
			// 在途中
			List<OpsInventoryMove> transInventoryMoveList = new ArrayList<>();
			// List<OpsInventoryMove> wInventoryMoveList = new ArrayList<>();
			//计算tQty总数和pQty总数
			for (OpsInventoryMove opsInventoryMove : inventoryMoveList) {
				// 状态发票已入库，且发票号一样表示已经导入过
				if (3 == opsInventoryMove.getOptStatus()
						&& inventoryMove.getInvoiceno().equalsIgnoreCase(opsInventoryMove.getInvoiceno())) {
					throw Exceptions.OpsException("发票已导入，请勿重新导入.订单号:" + orderNo + "-" + itemNo + "-" + splitNo);
				}
				if (opsInventoryMove.getInventoryStatus().equalsIgnoreCase(InventoryStatusEnum.PRODUCE.getCode())) {
					pinventoryMoveList.add(opsInventoryMove);
					pQty += opsInventoryMove.getQuantity();
				} else if (opsInventoryMove.getInventoryStatus().equalsIgnoreCase(InventoryStatusEnum.CGTRANS.getCode())) {
					// 只判断在途预到货
					if (inventoryMove.getInvoiceno().equalsIgnoreCase(opsInventoryMove.getInvoiceno())) {
						transInventoryMoveList.add(opsInventoryMove);
						tQty += opsInventoryMove.getQuantity();
					}
				}
			}
			// 箱码数量小于T1（不按发票号）在途中数量
			if (bQty < tQty) {
				throw Exceptions.OpsException("发票导入数:" + bQty + "不可小于预到货数:" + tQty + ",请联系电算处理.订单号:" + orderNo + "-"
						+ itemNo + "-" + splitNo);
			}
			// 多来货barcode数大于在途数直接把生产中改为T1
			// 场景1：到货10，在途5个，生成1
			// 场景2：到货10，在途10个，生成10
			// 场景3：到货10，在途0个，生成10
			if (bQty == tQty) {// 入库数量=在途数量 无须处理
				continue;
			}
			// 处理发票导入数大于预到货数逻辑 可分配数量=发票导入数-预到货数量
			int leftAllcQty = bQty - tQty;
			// 更新发票号，更新状态 P改T1 或拆分P
			for (OpsInventoryMove pInventoryMove : pinventoryMoveList) {
				// 分配到0跳出循环
				if (leftAllcQty == 0) {
					break;
				}
				// 剩余数够分配P，P改T1
				if (leftAllcQty >= pInventoryMove.getQuantity()) {
					// 更新P改T1，且更新发票号，改成预到货模式,后面代码在批量改成，发票导入3
					baseInventoryService.updateInventoryMovePToT(pInventoryMove.getInventoryId(), invoiceNo, invoiceId,
							"T1", pInventoryMove.getSupplierid(), 2, pInventoryMove.getVersion());
					leftAllcQty -= pInventoryMove.getQuantity();
				} else {
					// 剩余未分配数小于在途P数， 拆分P，一拆二 P-T1
					// 剩余8 生成中10 ，改成8在途，2生成中
					int p_Qty = pInventoryMove.getQuantity() - leftAllcQty;
					int t_Qty = leftAllcQty;
					// 剩余8 生成中预约数10 ，改成6个预约在途T1，0个P
					// 剩余8 生成中预约数6 ，改成6个预约在途T1，0个P
					int p_PreQty = 0;
					int t_PreQty = 0;
					if (leftAllcQty >= pInventoryMove.getPrepareQuantity()) {
						// 不能超量分配
						t_PreQty = pInventoryMove.getPrepareQuantity();
					} else {// 剩余数小于在途生成P预约数(不够分配)，一拆二
						// 剩余8，生成中预约数10 ，改成8个预约在途T1，2个P
						p_PreQty = pInventoryMove.getPrepareQuantity() - leftAllcQty;
						t_PreQty = leftAllcQty;
					}
					List<OpsDoItemInventory> opsDoItemInventories = baseDoService.getDoItemInventoryByInventoryId(
							pInventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
					// 经确认PCO不存在合并采购或多到情况 2022-10-18
					// 原来的P行扣数 100-60=40(P)
					baseInventoryService.updateQtyInventoryMoveWithPre(pInventoryMove.getInventoryId(),
							-(pInventoryMove.getQuantity() - p_Qty), -(pInventoryMove.getPrepareQuantity() - p_PreQty),
							pInventoryMove.getVersion(), UserDto.WMS);
					// 新写入T1行
					OpsInventoryMove tinventoryMove = new OpsInventoryMove();
					tinventoryMove.setWarehouseCode(pInventoryMove.getWarehouseCode());
					tinventoryMove.setSignWarehouseCode(pInventoryMove.getSignWarehouseCode());
					tinventoryMove.setInventoryStatus(InventoryStatusEnum.CGTRANS.getCode());
					tinventoryMove.setPoQty(pInventoryMove.getPoQty());
					tinventoryMove.setQuantity(t_Qty);
					tinventoryMove.setPrepareQuantity(t_PreQty);
					tinventoryMove.setUnit(pInventoryMove.getUnit());
					tinventoryMove.setQaStatus(pInventoryMove.getQaStatus());
					tinventoryMove.setModelno(pInventoryMove.getModelno());
					tinventoryMove.setInventoryPropertyId(pInventoryMove.getInventoryPropertyId());
					tinventoryMove.setOrderno(pInventoryMove.getOrderno());
					tinventoryMove.setItemno(pInventoryMove.getItemno());
					tinventoryMove.setSplititemno(pInventoryMove.getSplititemno());
					tinventoryMove.setAssociateNo(pInventoryMove.getAssociateNo());
					tinventoryMove.setAssociateNoItem(pInventoryMove.getAssociateNoItem());
					tinventoryMove.setAssociateNoSplitno(pInventoryMove.getAssociateNoSplitno());
					tinventoryMove.setSupplierid(pInventoryMove.getSupplierid());
					tinventoryMove.setInvoiceid(invoiceId);
					tinventoryMove.setInvoiceno(invoiceNo);
					// imDateTheory 理论到达仓库时间
					tinventoryMove.setPrereceivedate(implist.get(0).getReceivedate());
					tinventoryMove.setSourceType(SourceTypeEnum.PURCHASE.getType());
					tinventoryMove.setGreencode(pInventoryMove.getGreencode());// 绿色标记值是：H
					tinventoryMove.setOptStatus(OptStatusEnum.INVOICE_CONFIRM.getCode());// 操作状态
					// 插入采购在途（T1）
					Long tinventoryMoveid = baseInventoryService.createInvMoveReturnId(tinventoryMove, UserDto.WMS);
					if (!CollectionUtils.isEmpty(opsDoItemInventories)) {
						// 更新P行DOID与库存关联关系表
						opsDoService.deleteDoItemInventoryByInventoryId(pInventoryMove.getInventoryId(),
								InventoryTableTypeEnum.TRANS.getType());
						// 写入T行DOID与库存关联关系表
						OpsDoItemInventory doItemInventory = new OpsDoItemInventory();
						doItemInventory.setInventoryId(pInventoryMove.getInventoryId());
						doItemInventory.setDoId(opsDoItemInventories.get(0).getDoId());
						doItemInventory.setDoItem(opsDoItemInventories.get(0).getDoItem());
						doItemInventory.setInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
						doItemInventory.setUseQty(p_Qty);
						doItemInventory.setOutQty(0);
						opsDoService.insertDoItemInventory(doItemInventory);
						// 写入T1行DOID与库存关联关系表
						doItemInventory = new OpsDoItemInventory();
						doItemInventory.setInventoryId(tinventoryMoveid);
						doItemInventory.setDoId(opsDoItemInventories.get(0).getDoId());
						doItemInventory.setDoItem(opsDoItemInventories.get(0).getDoItem());
						doItemInventory.setInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
						doItemInventory.setUseQty(t_Qty);
						doItemInventory.setOutQty(0);
						opsDoService.insertDoItemInventory(doItemInventory);
					}

					leftAllcQty -= t_PreQty;
				}
			}
			// 箱码数-在途数T1-生成中P=新写入在途库存行
			int moreQty = bQty - tQty - pQty;
			// 多到部分写入其中一行
			if (moreQty > 0) {
				// 多到部分写入到大库）新写入行
				OpsInventoryMove tinventoryMove = new OpsInventoryMove();
				tinventoryMove.setWarehouseCode(signWarehouseCode);
				tinventoryMove.setSignWarehouseCode(signWarehouseCode);
				// 采购在途
				tinventoryMove.setInventoryStatus(InventoryStatusEnum.CGTRANS.getCode());
				tinventoryMove.setPoQty(moreQty);
				tinventoryMove.setQuantity(moreQty);
				tinventoryMove.setUnit(null);
				tinventoryMove.setQaStatus(QAStatusEnum.NORMAL.getType());
				tinventoryMove.setPrepareQuantity(0);// 多出来作为库存
				tinventoryMove.setModelno(inventoryMove.getModelno());
				tinventoryMove.setInventoryPropertyId(1L);
				tinventoryMove.setAssociateNo(inventoryMove.getAssociateNo());
				tinventoryMove.setAssociateNoItem(inventoryMove.getAssociateNoItem());
				tinventoryMove.setAssociateNoSplitno(inventoryMove.getAssociateNoSplitno());
				tinventoryMove.setSupplierid(supplierid);
				// imDateTheory 理论到达仓库时间
				tinventoryMove.setPrereceivedate(new Date());
				tinventoryMove.setSourceType(SourceTypeEnum.PURCHASE.getType());
				tinventoryMove.setGreencode(null);// 绿色标记值是：H
				tinventoryMove.setInvoiceid(invoiceId);
				tinventoryMove.setInvoiceno(invoiceNo);
				tinventoryMove.setOptStatus(OptStatusEnum.INVOICE_CONFIRM.getCode());
				tinventoryMove.setOriginalQuantity(moreQty);
				tinventoryMove.setPreOriginalQuantity(0);
				tinventoryMove.setRemark("发票导入:多到货发票数据");
				// 多出部分插入采购在途（P）
				Long inventoryMoveId = baseInventoryService.createInvMoveReturnId(tinventoryMove, UserDto.ADMIN);
				morePoInventoryMoveId.add(inventoryMoveId);
			}
		}
		//2.生成采购多到的moveT1,无请购单号，无关联关系
		// 多来货PO写入在途表 ，不做关联当库存
		for (Map.Entry<OpsInventoryMove, List<OpsImpdata>> entry : mapMorePoInvbarcode.entrySet()) {
			int moreQty = 0;
			OpsInventoryMove opsInventoryMove = entry.getKey();
			List<OpsImpdata> impdataList = entry.getValue();
			for (OpsImpdata impdata : impdataList) {
				moreQty += impdata.getQuantity();
			}
			OpsInventoryMove tinventoryMove = new OpsInventoryMove();
			tinventoryMove.setWarehouseCode(signWarehouseCode);
			tinventoryMove.setSignWarehouseCode(signWarehouseCode);
			// 采购在途
			tinventoryMove.setInventoryStatus(InventoryStatusEnum.CGTRANS.getCode());
			tinventoryMove.setPoQty(opsInventoryMove.getPoQty());
			tinventoryMove.setQuantity(moreQty);
			tinventoryMove.setUnit(null);
			tinventoryMove.setQaStatus(QAStatusEnum.NORMAL.getType());
			tinventoryMove.setPrepareQuantity(0);// 多出来作为库存
			tinventoryMove.setModelno(opsInventoryMove.getModelno());
			tinventoryMove.setInventoryPropertyId(1L);
			tinventoryMove.setAssociateNo(opsInventoryMove.getAssociateNo());
			tinventoryMove.setAssociateNoItem(opsInventoryMove.getAssociateNoItem());
			tinventoryMove.setAssociateNoSplitno(opsInventoryMove.getAssociateNoSplitno());
			tinventoryMove.setSupplierid(supplierid);
			// imDateTheory 理论到达仓库时间
			tinventoryMove.setPrereceivedate(new Date());
			tinventoryMove.setSourceType(SourceTypeEnum.PURCHASE.getType());
			tinventoryMove.setGreencode(null);// 绿色标记值是：H
			tinventoryMove.setInvoiceid(opsInventoryMove.getInvoiceid());
			tinventoryMove.setInvoiceno(opsInventoryMove.getInvoiceno());
			tinventoryMove.setOptStatus(OptStatusEnum.INVOICE_CONFIRM.getCode());
			tinventoryMove.setOriginalQuantity(moreQty);
			tinventoryMove.setPreOriginalQuantity(0);
			tinventoryMove.setRemark("发票导入:多到货发票数据");
			// 多出部分插入采购在途（P）
			Long inventoryMoveId = baseInventoryService.createInvMoveReturnId(tinventoryMove, UserDto.ADMIN);
            morePoInventoryMoveId.add(inventoryMoveId);
		}

		// 更变签收仓后，需要把有关联的DOID类型CGDBCK归属仓库也需更变
		List<OpsInventoryMove> inventoryMoves = baseInventoryService.findOpsInventoryMoveByInvoiceNo(invoiceNo);
		// 按po号位单位处理
		Map<OpsInventoryMove, List<OpsInventoryMove>> mapInventoryMoveData = new HashMap<>();
		for (OpsInventoryMove invoiceMovedto : inventoryMoves) {
			OpsInventoryMove inventoryMove = new OpsInventoryMove();
			inventoryMove.setAssociateNo(invoiceMovedto.getAssociateNo());
			inventoryMove.setAssociateNoItem(invoiceMovedto.getAssociateNoItem());
			inventoryMove.setAssociateNoSplitno(invoiceMovedto.getAssociateNoSplitno());
			inventoryMove.setInvoiceno(invoiceMovedto.getInvoiceno());
			inventoryMove.setInvoiceid(invoiceMovedto.getInvoiceid());
			// bug:8987 多到数量，不调拨产生的在途的id剔除，不继续产生调拨 2022-12-08
			// 2022-12-25 因测试反馈有问题，不给与上线先注释
			if (morePoInventoryMoveId.contains(invoiceMovedto.getInventoryId())) {
				continue;
			}
			if (!mapInventoryMoveData.containsKey(inventoryMove)) {
				List<OpsInventoryMove> implist = new ArrayList();
				implist.add(invoiceMovedto);
				mapInventoryMoveData.put(inventoryMove, implist);
			} else {
				mapInventoryMoveData.get(inventoryMove).add(inventoryMove);
				// 20220826MT增加数量赋值
				mapInventoryMoveData.get(inventoryMove).forEach(i -> {
					if (i.getModelno() == null) {
						i.setModelno(invoiceMovedto.getModelno());
					}
					if (i.getQuantity() == null) {
						i.setQuantity(invoiceMovedto.getQuantity());
					}
					if (i.getWarehouseCode() == null) {
						i.setWarehouseCode(invoiceMovedto.getWarehouseCode());
					}
					if (i.getSignWarehouseCode() == null) {
						i.setSignWarehouseCode(invoiceMovedto.getSignWarehouseCode());
					}
					if (i.getInventoryId() == null) {
						i.setInventoryId(invoiceMovedto.getInventoryId());
					}
					if (i.getPrepareQuantity() == null) {
						i.setPrepareQuantity(invoiceMovedto.getPrepareQuantity());
					}
					if (i.getGreencode() == null) {
						i.setGreencode(invoiceMovedto.getGreencode());
					}
				});
			}
		}

		// 处理已跟变签收仓
		for (Map.Entry<OpsInventoryMove, List<OpsInventoryMove>> entry : mapInventoryMoveData.entrySet()) {
			List<OpsInventoryMove> inventoryMoveList = entry.getValue();
			// 预占数汇总
			for (OpsInventoryMove inventoryMove : inventoryMoveList) {
				// inventoryMove被DO,PCO预分配占用数量
				int useqty = 0;
				// 在途PO号对应的剩余数BIN补库（非DO PCO预占数）
				// 请购仓与签收仓（采购仓）不一致，才调用(signWarehouseCode=发票导入传过来值)
				List<OpsDoItemInventory> opsDoItemInventorys = baseDoService
						.getDoItemInventoryByInventoryId(inventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
				// 处理DO有在途预占
				if (!CollectionUtils.isEmpty(opsDoItemInventorys)) {
					for (OpsDoItemInventory opsDoItemInventory : opsDoItemInventorys) {
						useqty = useqty + opsDoItemInventory.getUseQty();
						String doId = opsDoItemInventory.getDoId();
						OpsDo opsDo = baseDoService.getDoByDoId(doId);
						if (null == opsDo) {
							throw Exceptions.OpsException("发票入库更变签收仓，无对应DOID：" + doId);
						}
						// 不判断DO类型 签收仓与出库仓不一致
						if (!signWarehouseCode.equals(opsDo.getWarehouseCode())) {
							// 判断加工单非加工单
							if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
								List<OpsPco> pcos = baseWMOrderService.getPcos(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum());
								if(CollectionUtils.isEmpty(pcos)){
									throw Exceptions.OpsException("发票入库更变签收仓，无对应opsPcoID：" + opsDo.getOrderId() + "-"
											+ String.valueOf(opsDo.getOrderItem()));
								}
								OpsPco opsPco = pcos.get(0);
								if (null == opsPco) {
									throw Exceptions.OpsException("发票入库更变签收仓，无对应opsPcoID：" + opsDo.getOrderId() + "-"
											+ String.valueOf(opsDo.getOrderItem()));
								}
								OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoId(opsPco.getPcoId(),
										opsDo.getAssNum());
								if (Objects.isNull(opsPcoItem)) {
									throw Exceptions.OpsException(
											"采购仓变更加工单没有查询到加工单" + opsPco.getOrderId() + "-" + opsPco.getOrderItem());
								}
								// 修改更变签收仓库加工单DOID关联的仓库
								opsDo.setWarehouseCode(signWarehouseCode);
								opsDoService.handROChangeDoWarehouseCodeByCGDo(opsDo, opsPcoItem.getPcoId(),
										opsPcoItem.getPcoItem(), inventoryMove.getModelno());
							} else {
								// 修改更变签收仓库DOID关联的仓库（非加工单，销售客单）
								opsDo.setWarehouseCode(signWarehouseCode);
								opsDoService.handROChangeDoWarehouseCodeByCGDo(opsDo, null, null, inventoryMove.getModelno());
							}

						} else {
							// bugid：12563 不异仓收货，也要判断是否修改集约仓
							if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())) {
								opsDo.setWarehouseCode(signWarehouseCode);
								opsDoService.handROChangeDoWarehouseCodeByCGDo(opsDo, null, null, inventoryMove.getModelno());
							}
						}
					}
				}
				// 处理PCO有在途预占
				List<OpsPcoItemInventory> opsPcoItemInventorys = opsPcoService.getOpsPcoItemInventoryByInventoryId(
						inventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
				if (!CollectionUtils.isEmpty(opsPcoItemInventorys)) {
					for (OpsPcoItemInventory opsPcoItemInventory : opsPcoItemInventorys) {
						useqty = useqty + opsPcoItemInventory.getUseQty();
						String pcoId = opsPcoItemInventory.getPcoId();
						Integer pcoItem = opsPcoItemInventory.getPcoItem();
						OpsPco opsPco = opsPcoService.getPcoByPcoId(pcoId);
						OpsDo opsDo = null;
						if (null != opsPco) {
							// 匹配交易出库(不用匹配拆num)
							List<OpsDo> dos = baseWMOrderService.getDos(opsPco.getOrderId(),
									opsPco.getOrderItem(), opsPco.getNum());
							List<OpsDo> opsDos  = baseWMOrderService.getDos(dos, DoTypeEnum.JYCK);
							if (null != opsDos) {
								opsDo = opsDos.get(0);
							} else {
								throw Exceptions.OpsException(
										"采购仓变更加工单没有查询到交易出库" + opsPco.getOrderId() + "-" + opsPco.getOrderItem());
							}
							// 不判断DO类型 签收仓与出库仓不一致
							if (!signWarehouseCode.equals(opsDo.getWarehouseCode())) {
								opsDo.setWarehouseCode(signWarehouseCode);
								opsDoService.handROChangeDoWarehouseCodeByCGDo(opsDo, pcoId, pcoItem,inventoryMove.getModelno());
							}
						}
					}
				}

			}

		}
		// 只要该发票有一条库存为异仓收货，则签收仓更变（传入签收仓与在途表签收仓不一致）
		if (hasDiffSignWarehouse) {
			baseInventoryService.updateSignWarehouseByInvoiceNo(invoiceNo, invoiceId, signWarehouseCode);
		}
		// 一个RO
		// 一个RO-ITEM
		// 多个RO-ITEM_Inventory
		// Nbarcode
		// 逐个一个PO
		List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
		for (Map.Entry<OpsInventoryMove, List<OpsImpdata>> entry : mapPoImpData.entrySet()) {
			OpsInventoryMove inventoryMove = entry.getKey();
			List<OpsImpdata> impdataList = entry.getValue();
			List<OpsInventoryMove> tinventoryMoveList = baseInventoryService.getOpsInventoryMoveListByPo(
					InventoryStatusEnum.CGTRANS, inventoryMove.getModelno(), inventoryMove.getAssociateNo(),
					inventoryMove.getAssociateNoItem(), inventoryMove.getAssociateNoSplitno(),
					inventoryMove.getInvoiceno());
			int onhandQty = 0;
			for (OpsInventoryMove opsInventoryMove : tinventoryMoveList) {
				onhandQty += opsInventoryMove.getQuantity();
			}
			int barcodeQty = 0;
			for (OpsImpdata impdata : impdataList) {
				barcodeQty += impdata.getQuantity();
			}
			if (onhandQty != barcodeQty) {
				log.error("在途表数与箱码数不一致！发票号: invoiceNo = {}, PO号: {}-{}-{}，po数量：{}，箱码数量：{}", invoiceNo,
						inventoryMove.getAssociateNo(), inventoryMove.getAssociateNoItem(),
						inventoryMove.getAssociateNoSplitno(), onhandQty, barcodeQty);
			}
			String orderNo = inventoryMove.getAssociateNo();
			int itemNo = inventoryMove.getAssociateNoItem();
			int splitno = null == inventoryMove.getAssociateNoSplitno() ? 0 : inventoryMove.getAssociateNoSplitno();
			String roid = String.format(OrderIDFormatEnum.RO_FORMAT.getFormat(), list.get(0).getInvoiceno(), orderNo,
					String.format("%03d", itemNo), String.format("%03d", splitno));
			OpsRo ro = opsRoService.initRoForInvoice(roid, inventoryMove.getAssociateNo(),
					String.valueOf(inventoryMove.getAssociateNoItem()), splitno, RoTypeEnum.CGRKBK,
					list.get(0).getReceivewarehouseid(), list.get(0).getSupplierid(), list.get(0).getInvoiceno(),
					list.get(0).getInvoiceid());
			List<OpsRoItemInventory> roItemInventoryList = new ArrayList<>();
			List<OpsRoBarcode> barcodeList = new ArrayList<>();
			// roItem数据结构
			BigDecimal netWeight = BigDecimal.ZERO;

			if (Objects.nonNull(impdataList.get(0).getWeight())) {
				netWeight = impdataList.get(0).getWeight();
			}
			OpsRoItem roItem = opsRoService.initRoItemForInvoice(roid, onhandQty, inventoryMove.getModelno(),
					inventoryMove.getGreencode(), netWeight);
			// RoItemInventory数据结构
			for (OpsInventoryMove opsInventoryMove : tinventoryMoveList) {
				OpsRoItemInventory roItemInventory = opsRoService.initRoItemInventoryForInvoice(roid,
						roItem.getRoItem(), opsInventoryMove.getInventoryId(), opsInventoryMove.getQuantity());
				roItemInventoryList.add(roItemInventory);
			}
			for (OpsImpdata opsImpdata : impdataList) {
				OpsRoBarcode barcode = opsRoBarcodeService.initOpsRoBarcodeForInvoice(roItem.getRoId(),
						ro.getWarehouseCode(), opsImpdata, opsImpdata.getQuantity(), inventoryMove.getAssociateNo(),
						inventoryMove.getAssociateNoItem(), inventoryMove.getAssociateNoSplitno());
				barcodeList.add(barcode);
			}
			opsRoService.insertRo(ro, roItem, roItemInventoryList, UserDto.WMS);
			// 批量插入barcode
			opsRoBarcodeService.insertBatchBarcode(barcodeList);
			OpsWmOrderTask opsWmOrderTask = new OpsWmOrderTask();
			opsWmOrderTask.setFlag(Integer.valueOf(SendStatusEnum.INIT.getType()));
			opsWmOrderTask.setCreator(UserDto.WMS.getUserName());
			opsWmOrderTask.setCreTime(new Date());
			opsWmOrderTask.setWmOrderId(roid);// todo roid
			opsWmOrderTask.setWmOrderType(WmOrderTaskEnum.RO.getType());
			opsWmOrderTask.setTaskType(WmOrderTaskEnum.ORDER.getType());
			taskList.add(opsWmOrderTask);
			// wmOrderTaskService.addOpsWmOrderTask(opsWmOrderTask);
			OpsWmOrderTask opsWmOrderTaskBarcode = new OpsWmOrderTask();
			opsWmOrderTaskBarcode.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.getType()));
			opsWmOrderTaskBarcode.setCreator(UserDto.WMS.getUserName());
			opsWmOrderTaskBarcode.setCreTime(new Date());
			opsWmOrderTaskBarcode.setWmOrderId(roid);// todo roid
			opsWmOrderTaskBarcode.setWmOrderType(WmOrderTaskEnum.RO.getType());
			opsWmOrderTaskBarcode.setTaskType(WmOrderTaskEnum.BARCODE.getType());
			taskList.add(opsWmOrderTaskBarcode);
			// wmOrderTaskService.addOpsWmOrderTask(opsWmOrderTaskBarcode);
		}
		// 批量更新task
		wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
		// 操作状态改关务导入且初始数量
		baseInventoryService.updateInventoryMoveOptStatusByInvoiceNo(invoiceNo, invoiceId,
				OptStatusEnum.INVOICE_CONFIRM);
		//处理订单通知发货逻辑
		List<NotifyShipmentCondition> notifyOrderList = createNotifyDeliveryDto(inventoryMoves,"invoiceForPoConfirm");
		//通知发货的处理逻辑
		for (NotifyShipmentCondition condition : notifyOrderList) {
			doPcoLogicCenterService.splitOrderByDlvEntry(condition);
		}
	}

	private List<NotifyShipmentCondition> createNotifyDeliveryDto(List<OpsInventoryMove> inventoryMoves,String creator) throws OpsException {
		List<NotifyShipmentCondition> notifyOrderList = new ArrayList<>();
		for (OpsInventoryMove move : inventoryMoves) {
			List<OpsDoItemInventory> doItemInventoryList = baseDoService.getDoItemInventoryByInventoryId(move.getInventoryId(), InventoryTableTypeEnum.TRANS);
			List<OpsPcoItemInventory> pcoItemInventoryList = opsPcoService.getOpsPcoItemInventoryByInventoryId(move.getInventoryId(), InventoryTableTypeEnum.TRANS);
			for (OpsDoItemInventory doItemInventory : doItemInventoryList) {
				OpsDo opsDo = baseDoService.getDoByDoId(doItemInventory.getDoId());
				if (opsDo != null && (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType()) ||  DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) ) {
					notifyOrderList.add(new NotifyShipmentCondition(opsDo, creator));
				}
			}
			for (OpsPcoItemInventory pcoItemInventory : pcoItemInventoryList) {
				OpsPco opsPco = opsPcoService.selectPcoBypcoId(pcoItemInventory.getPcoId());
				if (opsPco != null) {
					OpsDo opsDo = baseDoService.getJYCKByOrder(opsPco.getOrderId(), opsPco.getOrderItem(), opsPco.getNum(), DoSourceEnum.ASSModelNo);
					if (opsDo != null && DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
						notifyOrderList.add(new NotifyShipmentCondition(opsDo, creator));
					}
				}
			}
		}
		return notifyOrderList.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * @description：发票签收
	 * @description：采购如果不允许异仓合并，一张发票只能属于一个仓
	 * @author ：c02483
	 * @date ：Created in 2022/1/14 13:54
	 */
	public void invoiceForSignWms(RoSignConfirmDto paramDto) throws OpsException {
		String invoice = paramDto.getInvoice();
		String warehouse = paramDto.getWarehouse();
		Long invoiceId = paramDto.getInvoiceId();
		if (StringUtils.isEmpty(invoice)) {
			throw Exceptions.OpsException("发票号不可为空");
		}
		// 通过发票号，查询是否为组换单，如果为组换单，则跳过
		List<OpsRo> roIds = baseRoService.isExchangeOrder(invoice);
		if (CollectionUtils.isNotEmpty(roIds)) {
			// 更新RO 签收状态`
			baseRoService.updateOpsRoSignForInvoiceNo(invoice, invoiceId, UserDto.WMS.getUserName());
			return;
		}
		if (StringUtils.isEmpty(warehouse)) {
			throw Exceptions.OpsException("签收仓不可为空");
		}
		// List<OpsInventoryMove> list =
		// baseInventoryService.getOpsInventoryMoveListByInvoice(invoice, null);
		// begin bug:9385 增加发票ID判断 b28029 2023-02-07
		List<OpsInventoryMove> list = baseInventoryService.getOpsInventoryMoveListByInvoice(invoice, invoiceId, null);
		// end bug:9385
		if (CollectionUtils.isEmpty(list)) {
			throw Exceptions.OpsException("发票号:" + invoice + ".不存在待签收的库存信息", invoice);
		}
		OpsWarehouse opsWarehouse = opsWarehouseService.findById(warehouse);
		if (null == opsWarehouse) {
			throw Exceptions.OpsException("发票号:" + invoice + ".库房不存在。" + warehouse, invoice);
		}
		// 委托在库库房不需要判断是否下发物流task
		if (!WarehouseTypeEnum.WT.getHouseTypeCode().equalsIgnoreCase(opsWarehouse.getWarehouseType())) {
			// 存在未下发的WMS
			Long countIswms = baseRoService.countRoForNotSendToWmsByInvoiceNo(invoice, invoiceId);
			if (countIswms > 0) {
				throw Exceptions.OpsException(
						"发票号:" + invoice + ".有" + String.valueOf(countIswms) + "项未下发WMS(不可签收)，请稍等", invoice);
			}
		}
		// 传入发票号null 使用传入发票ID
		if (Objects.isNull(invoiceId)) {
			invoiceId = list.get(0).getInvoiceid();
		}

		// 物流已经签收发票（不可继续操作）
		if (InventoryStatusEnum.W.getCode().equals(list.get(0).getInventoryStatus())) {
			throw Exceptions.OpsException("发票号:" + invoice + ".物流已经签收发票，请勿重复提交。", invoice);
		}
		// 签收仓（1票只能单仓）
		List<String> signWarehouseCodes = list.stream().map(OpsInventoryMove::getSignWarehouseCode)
				.collect(Collectors.toList());
		if (signWarehouseCodes.stream().distinct().count() > 1) {
			throw Exceptions.OpsException("单票仅允许单仓签收代码，发票号：" + invoice + ",请联系IT部门");
		}
		if (!warehouse.equalsIgnoreCase(signWarehouseCodes.get(0))) {
			throw Exceptions.OpsException(
					"发票号：" + invoice + ",签收仓(" + signWarehouseCodes.get(0) + ")与实际签收仓库(" + warehouse + ")不一致,请切换仓库代码");
		}

		// 写日志
		for (OpsInventoryMove inventoryMove : list) {
			// 在途 W到货未入库
			baseInventoryService.UpdateStatusInventoryMoveForSign(inventoryMove);
		}
		// 写事件
		for (OpsInventoryMove move : list) {
			List<OpsDoItemInventory> doItemInventoryList = baseDoService
					.getDoItemInventoryByInventoryId(move.getInventoryId(), InventoryTableTypeEnum.TRANS);
			// do表
			for (OpsDoItemInventory itemInventory : doItemInventoryList) {
				OpsDo opsDo = baseDoService.getDoByDoId(itemInventory.getDoId());
				if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())) {
					transferEventPublisher.transferOrderEvent(EventSourceEnum.INVENTORY_TRANS_ORDER_SIGNIN, opsDo);
				}
				// 交易出库或调拨出库
				else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())||DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
					StatusParamDto param = new StatusParamDto();
					if (SourceTypeEnum.PURCHASE.getType().equals(move.getSourceType())) {
						param.setPoNo(move.getAssociateNo());
						param.setPoItem(move.getAssociateNoItem());
						param.setPoSplitNo(move.getAssociateNoSplitno());
					}
					param.setInvoiceNo(invoice);
					param.setInvoiceId(invoiceId);
					param.setWarehouseCode(warehouse);
					param.setQuantity(itemInventory.getUseQty());
					param.setDate(DateUtil.formatDateTime(new Date()));
					//签收采购单和调拨单
					customerEventPublisher.customerOrderEvent(WAREHOUSE_SIGNIN_CONFIRM,
							opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()), OrderNoInfo.getFromDo(opsDo).getSplitNoNotNull(), param);
				}
			}
			List<OpsPcoItemInventory> pcoItemInventories = opsPcoService
					.getOpsPcoItemInventoryByInventoryId(move.getInventoryId(), InventoryTableTypeEnum.TRANS);
			// pco表
			for (OpsPcoItemInventory itemInventory : pcoItemInventories) {
				OpsPco pco = opsPcoService.getPcoByPcoId(itemInventory.getPcoId());
				StatusParamDto param = new StatusParamDto();
				if (SourceTypeEnum.PURCHASE.getType().equals(move.getSourceType())) {
					param.setPoNo(move.getAssociateNo());
					param.setPoItem(move.getAssociateNoItem());
					param.setPoSplitNo(move.getAssociateNoSplitno());
				}
				param.setInvoiceNo(invoice);
				param.setInvoiceId(invoiceId);
				param.setWarehouseCode(warehouse);
				param.setQuantity(itemInventory.getUseQty());
				param.setDate(DateUtil.formatDateTime(new Date()));
				//签收采购单和调拨单
				customerEventPublisher.customerOrderEvent(WAREHOUSE_SIGNIN_CONFIRM,
						pco.getOrderId(), Integer.valueOf(pco.getOrderItem()), itemInventory.getPcoItem(), param);
			}
		}
		// 调用成本收货接口
		OpsInventoryMove opsInventoryMove = list.get(0);
		if (InventoryStatusEnum.CGTRANS.getCode().equals(opsInventoryMove.getInventoryStatus())) {
			ImpInvoiceReceiveDTO impInvoiceReceiveDTO = new ImpInvoiceReceiveDTO();
			impInvoiceReceiveDTO.setInvoiceNo(invoice);
			impInvoiceReceiveDTO.setInvoiceId(invoiceId);
			impInvoiceReceiveDTO.setReceiveTime(new Date());
			// 18857 C12961 2025-09-17 改为取签收仓库字段，不取请购仓
			impInvoiceReceiveDTO.setReceiveWarehouseCode(opsInventoryMove.getSignWarehouseCode());
			impInvoiceReceiveDTO.setSupplierNo(opsInventoryMove.getSupplierid());
			//bugid:17829 c14717 20250708 发票签收order解耦
			ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "28");
			if (!dataTypeCodesInfo.isSuccess()
					|| Objects.isNull(dataTypeCodesInfo.getData())
					|| StringUtils.isEmpty(dataTypeCodesInfo.getData().getExtNote1())) {
				throw Exceptions.OpsException("发票签收开关错误");
			}
			if (!dataTypeCodesInfo.getData().getExtNote1().equals("0")
					&& !dataTypeCodesInfo.getData().getExtNote1().equals("1")) {
				throw Exceptions.OpsException("发票签收开关错误Code=:" + dataTypeCodesInfo.getData().getExtNote1());
			}
			ResultVo<String> resultVo = null;
			//旧接口
			if (dataTypeCodesInfo.getData().getExtNote1().equals("0")) {
				resultVo = invoiceServiceFeignApi.receiveGoods(impInvoiceReceiveDTO);
			}
			//新方法
			if (dataTypeCodesInfo.getData().getExtNote1().equals("1")) {
				resultVo = receiveGoodsService.receiveGoods(impInvoiceReceiveDTO);
			}
			if (!resultVo.isSuccess()) {
				throw Exceptions.OpsException("调用InvoiceServiceFeignApi.receiveGoods接口错误:" + resultVo.getMessage(),
						invoice);
			}
			// 更新OPSImpdata.Receivedate for invoiceNo
			OpsImpdataExample example = new OpsImpdataExample();
			OpsImpdataExample.Criteria criteria = example.createCriteria();
			// 13561 增加更新条件invoiceId
			if (StringUtils.isNotEmpty(invoice)) {
				criteria.andInvoicenoEqualTo(invoice);
			}
			if (invoiceId != null) {
				criteria.andInvoiceidEqualTo(invoiceId);
			}
			OpsImpdata opsImpdata = new OpsImpdata();
			opsImpdata.setReceivedate(new Date());
			opsImpdata.setStatecode("1");// 0待收货 1已签收 2已入库 9异常
			opsImpdataMapper.updateByExampleSelective(opsImpdata, example);
		}
		// 更新RO 签收状态
		baseRoService.updateOpsRoSignForInvoiceNo(invoice, invoiceId, UserDto.WMS.getUserName());
		// 操作状态改发票签收
		baseInventoryService.updateInventoryMoveOptStatusByInvoiceNo(invoice, invoiceId,
				OptStatusEnum.INVOICE_SIGN);
		List<OpsRo> opsRoList = baseRoService.findRoByInvoiceNoAndInvoiceId(invoice,invoiceId);
		//给每一个采购单写入签收事件，发票号为当前发票号
		for (OpsRo ro : opsRoList) {
			if(RoTypeEnum.CGRKBK.getType().equals(ro.getRoType())){
				List<OpsPurchaseinvoice> invoices = basePoService.getPurchaseInvoicesByNo(ro.getOrderId(), Integer.valueOf(ro.getOrderItem()), ro.getNum());
				if(CollectionUtils.isNotEmpty(invoices)){
					OpsPurchaseinvoice purchaseinvoice = invoices.get(0);
					purchaseinvoice.setInvoiceno(invoice);
					purchaseinvoice.setInvoiceid(invoiceId);
					purchaseEventPublisher.purchaseOrderEvent(EventSourceEnum.PURCHASE_INVOICE_SIGNED, purchaseinvoice);
				}
			}
		}
	}

	/**
	 * @description：到货确认
	 * @author ：c02483
	 * @date ：Created in 2021/10/3 15:17
	 */
	public GoodsConfirmDto goodsConfirm(RoSignConfirmDto paramDto) throws OpsException {
		// bug:11242 C12961 延迟下发do、pco的指令信息
		Map<String, String> doSendMap = new HashMap<>();
		Map<String, OpsSendPcoAndDoMidIDDto> pcoSendMap = new HashMap<>();
		String invoice = paramDto.getInvoice();
		Long invoiceId = paramDto.getInvoiceId();
		String warehouse = paramDto.getWarehouse();
		if (StringUtils.isEmpty(invoice)) {
			throw Exceptions.OpsException("发票号不可为空");
		}
		// 12737 查询roConfirmLog,并返回 2024-01-15
		List<OpsRoConfirmLog> roConfirmLogList = roConfirmLogService.getRoConfirmLogsByInvoiceId(invoiceId, invoice);
		if (CollectionUtil.isNotEmpty(roConfirmLogList)) {
			List<RoConfirmItem> roConfirmItemList = roConfirmLogList.stream()
					.map(log -> new RoConfirmItem(log.getReceiveType(), log.getRoId(), log.getDoId(), log.getPcoId(),
							log.getInvoiceNo(), log.getInvoiceId()))
					.collect(Collectors.toList());
			log.info("此发票号已到货确认过，处理结果如下：{}", JSONUtil.toJsonStr(roConfirmItemList));
			return new GoodsConfirmDto(roConfirmItemList, null, null);
		}
		// 通过发票号，查询是否为组换单，如果为组换单，则跳过
		List<OpsRo> roIdsForZH = baseRoService.isExchangeOrder(invoice);
		if (CollectionUtils.isNotEmpty(roIdsForZH)) {
			List<RoConfirmItem> results = roIdsForZH.stream()
					.map(ro -> new RoConfirmItem(RoConfirmRecTypeEnum.INZK.getType(), ro.getRoId(), null, null, invoice,
							invoiceId))
					.collect(Collectors.toList());
			return new GoodsConfirmDto(results, null, null);
		}
		if (StringUtils.isEmpty(warehouse)) {
			throw Exceptions.OpsException("签收仓不可为空");
		}
		Date fromDate = new Date();
		// 在途中
		// begin bug:9385 增加发票ID判断 b28029 2023-02-10
		List<OpsInventoryMove> list = baseInventoryService.getOpsInventoryMoveListByInvoice(invoice, invoiceId, null);
		// end bug:9385
		// List<OpsInventoryMove> list =
		// baseInventoryService.getOpsInventoryMoveListByInvoice(invoice, null);
		if (CollectionUtils.isEmpty(list)) {
			throw Exceptions.OpsException("无到货确认,发票号：" + invoice);
		}
		if (!InventoryStatusEnum.W.getCode().equals(list.get(0).getInventoryStatus())) {
			throw Exceptions.OpsException("未签收发票，请先签收。" + invoice, invoice);
		}
		// 操作状态（1已接单 2初次导入 3关务导入 4已发票签收 5已到货确认）
		if (OptStatusEnum.GOODS_CONFIRM.getCode() == list.get(0).getOptStatus()) {
			throw Exceptions.OpsException("已到货确认，请勿重复处理。发票号：" + invoice);
		}
		List<String> signWarehouseCodes = list.stream().map(OpsInventoryMove::getSignWarehouseCode)
				.collect(Collectors.toList());
		if (signWarehouseCodes.stream().distinct().count() > 1) {
			throw Exceptions.OpsException("数据异常。单票仅允许单仓签收代码，发票号：" + invoice + ",请联系IT部门");
		}
		if (!warehouse.equalsIgnoreCase(signWarehouseCodes.get(0))) {
			throw Exceptions.OpsException(
					"发票号：" + invoice + ",签收仓(" + signWarehouseCodes.get(0) + ")与实际签收仓库(" + warehouse + ")不一致,请切换仓库代码");
		}
		// do越库更新 key:Doid, value<1预约2越库,roid>
		Map<String, Map<Integer, String>> doYueKu = new HashMap<>();
		// pco越库更新
		Map<String, String> pcoYueKu = new HashMap<>();

		// PO 对应 OpsInventoryMove行(PO，orderno,item,split)
		Map<OpsInventoryMove, List<OpsInventoryMove>> mapPoInvMove = new HashMap<>();
		for (OpsInventoryMove inventoryMove : list) {
			OpsInventoryMove inventoryMovePO = new OpsInventoryMove();
			inventoryMovePO.setAssociateNo(inventoryMove.getAssociateNo());
			inventoryMovePO.setAssociateNoItem(inventoryMove.getAssociateNoItem());
			inventoryMovePO.setAssociateNoSplitno(inventoryMove.getAssociateNoSplitno());
			inventoryMovePO.setModelno(inventoryMove.getModelno());
			inventoryMovePO.setInvoiceno(inventoryMove.getInvoiceno());
			inventoryMovePO.setInvoiceid(inventoryMove.getInvoiceid());
			if (!mapPoInvMove.containsKey(inventoryMove)) {
				List<OpsInventoryMove> implist = new ArrayList();
				implist.add(inventoryMovePO);
				mapPoInvMove.put(inventoryMove, implist);
			} else {
				mapPoInvMove.get(inventoryMove).add(inventoryMovePO);
			}
		}
		// 返回给富勒的值
		List<RoConfirmItem> roConfirmItemList = new ArrayList<>();
		// 判断合并采购时，上货架且按箱重复RO 不继续发
		List<String> roIds = new ArrayList<>();
		// 已发送的DOID，会存在一个发票2在途分别2 DOID交叉预约，剔除已下发
		List<String> doIds = new ArrayList<>();
		// begin:bug9278 判断物流中心库房 true为物流中心 b28029 2023-1-10
		ResultVo<String> opsWarehouseType = wmCommonService.getWarehouseTypeByCode(warehouse);
		if (!opsWarehouseType.isSuccess()) {
			throw Exceptions.OpsException(opsWarehouseType.getMessage());
		}
		// end bug:9278 非物流中心，不存在越库，预约。只可上库存
		if (!WarehouseTypeEnum.RDC.getHouseTypeCode().equalsIgnoreCase(opsWarehouseType.getData())) {
			for (Map.Entry<OpsInventoryMove, List<OpsInventoryMove>> entry : mapPoInvMove.entrySet()) {
				OpsInventoryMove inventoryMove = entry.getKey();
				List<OpsRoItemInventory> roItemInventoryList = baseRoService
						.findRoItemInventoryByInventoryId(inventoryMove.getInventoryId());
				if (CollectionUtils.isEmpty(roItemInventoryList)) {
					throw Exceptions.OpsException(
							"无对应的OpsRoItemInventory:InventoryId=>" + String.valueOf(inventoryMove.getInventoryId()));
				}
				OpsRoItemInventory roItemInventory = roItemInventoryList.get(0);
				String roId = roItemInventory.getRoId();
				// 返回上货架库存
				RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INZK;
				RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, null, null,
						invoice, invoiceId);
				roConfirmItemList.add(roConfirmItem);
			}
			// 操作状态改发票确认
			baseInventoryService.updateInventoryMoveOptStatusByInvoiceNo(invoice, invoiceId,
					OptStatusEnum.GOODS_CONFIRM);
			ImpInvoiceEventLog eventLog = new ImpInvoiceEventLog();
			eventLog.setOpType("/order/confirmgoods");
			eventLog.setRequestParam(invoice);
			eventLog.setOpStartTime(fromDate);
			eventLog.setReturnData(JSON.toJSONString(roConfirmItemList));
			this.addImpInvoiceEventLog(eventLog);
			// 写入到货确认返回结果
			List<OpsRoConfirmLog> confirmLogs = new ArrayList<>();
			for (RoConfirmItem roConfirmItem : roConfirmItemList) {
				OpsRoConfirmLog confirmLog = new OpsRoConfirmLog();
				confirmLog.setRoId(roConfirmItem.getRoId());
				confirmLog.setDoId(roConfirmItem.getDoid());
				confirmLog.setReceiveType(roConfirmItem.getReceiveType());
				confirmLog.setPcoId(roConfirmItem.getPcoid());
				confirmLog.setInvoiceNo(roConfirmItem.getInvoiceNo());
				// bug12737 ro_confirm_log，增加invoiceid的字段，到货确认后记录此字段
				confirmLog.setInvoiceId(roConfirmItem.getInvoiceId());
				confirmLog.setStatus(1);
				confirmLog.setCreator(UserDto.WMS.getUserName());
				confirmLog.setCreTime(new Date());
				confirmLogs.add(confirmLog);
			}
			roConfirmLogService.insertBatchConfirmLog(confirmLogs);
			return new GoodsConfirmDto(roConfirmItemList, null, null);
		}
		// 逐个一个PO
		for (Map.Entry<OpsInventoryMove, List<OpsInventoryMove>> entry : mapPoInvMove.entrySet()) {
			OpsInventoryMove inventoryMove = entry.getKey();
			List<OpsInventoryMove> inventoryMoves = entry.getValue();
			// DOID，DoItemInventory
			Map<String, List<OpsDoItemInventory>> mapdo = new HashMap<>();
			// PCOID，PcoItemInventory
			Map<String, List<OpsPcoItemInventory>> mappco = new HashMap<>();
			// RoItemInventory
			List<OpsRoItemInventory> roItemInventoryList = baseRoService
					.findRoItemInventoryByInventoryId(inventoryMove.getInventoryId());
			if (CollectionUtils.isEmpty(roItemInventoryList)) {
				throw Exceptions.OpsException(
						"无对应的OpsRoItemInventory:InventoryId=>" + String.valueOf(inventoryMove.getInventoryId()));
			}
			OpsRoItemInventory roItemInventory = roItemInventoryList.get(0);
			String roId = roItemInventory.getRoId();
			List<OpsRoBarcode> roBarcodes = opsRoBarcodeService.findRoBarcodeByRoId(roId);
			if (CollectionUtils.isEmpty(roBarcodes)) {
				throw Exceptions.OpsException("无对应的箱码：ROID:" + roId);
			}
			// 箱码对应数量汇总（用于判断能否预约）
			int barCodeTotalQty = 0;
			for (OpsRoBarcode opsRoBarcode : roBarcodes) {
				barCodeTotalQty += opsRoBarcode.getQty();
			}
			// DO在途库存关联 key=doid,value=opsDoItemInventory
			List<OpsDoItemInventory> doItemInventoryList = baseDoService
					.getDoItemInventoryByInventoryId(inventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
			for (OpsDoItemInventory doItemInventory : doItemInventoryList) {
				if (mapdo.containsKey(doItemInventory.getDoId())) {
					mapdo.get(doItemInventory.getDoId()).add(doItemInventory);
				} else {
					List<OpsDoItemInventory> ItemInventoryList = new ArrayList<>();
					ItemInventoryList.add(doItemInventory);
					mapdo.put(doItemInventory.getDoId(), ItemInventoryList);
				}
			}
			// 组装在途库存关联 key=pcoid,value=opsPcoItemInventorys
			List<OpsPcoItemInventory> pcoItemInventoryList = opsPcoService
					.getOpsPcoItemInventoryByInventoryId(inventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
			for (OpsPcoItemInventory pcoItemInventory : pcoItemInventoryList) {
				if (mappco.containsKey(pcoItemInventory.getPcoId())) {
					mappco.get(pcoItemInventory.getPcoId()).add(pcoItemInventory);
				} else {
					List<OpsPcoItemInventory> ItemInventoryList = new ArrayList<>();
					ItemInventoryList.add(pcoItemInventory);
					mappco.put(pcoItemInventory.getPcoId(), ItemInventoryList);
				}
			}
			// 在途PO有两种判断 =1行与>1行
			// 在途行数只有1行(
			if (inventoryMoves.size() == 1) {
				// 箱码对应数量大于收货数说明合并采购，上在库 或者箱码数大于被预约数
				// 一在途被多个DO或PCO分配，则合并采购
				if (barCodeTotalQty > inventoryMove.getQuantity()
						|| (inventoryMove.getPrepareQuantity() > 0
						&& barCodeTotalQty > inventoryMove.getPrepareQuantity())
						|| (mapdo.size() > 0 && mappco.size() > 0)) {
					// 返回上货架在库
					if (!roIds.contains(roId)) {
						// 返回上货架库存
						RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INZK;
						RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, null,
								null, invoice, invoiceId);
						roConfirmItemList.add(roConfirmItem);
						roIds.add(roId);
					}
					// 合并采购下发DO
					for (Map.Entry<String, List<OpsDoItemInventory>> doEntry : mapdo.entrySet()) {
						String doId = doEntry.getKey();
						OpsDo opsDo = baseDoService.getDoByDoId(doId);
						if (null == opsDo) {
							throw Exceptions.OpsException("无opsDo:doId=>" + doId);
						}
						OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(doId);
						if (null == opsDoItem) {
							throw Exceptions.OpsException("无对应的opsDoItem:doId=>" + doId);
						}
						// 判断是否货齐
						boolean isEnough = this.isEnoughToCrossForDo(opsDo, opsDoItem, invoice);
						// 收齐才下发DO
						if (isEnough) {
							// 判断是否货齐下发条件
							if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
								if (!doIds.contains(doId)) {
									doIds.add(doId);
									// this.sendDoToWms (doId, 0, "");
								}
							}
							// 客户交易出库单
							else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
								// 2单项分批随到发货(默认就越库)只判断信用拦截
								if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())) {
									// 默认下发DO
									if (!doIds.contains(doId)) {
										doIds.add(doId);
										// this.sendDoToWms (doId, 0, "");
									}
								}
								else if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(opsDo.getDlvEntire())){
									if (!doIds.contains(doId)) {
										doIds.add(doId);
										// 通知发货不越库，上预约
									}
								}
								// 0单项单仓货齐发货(按10位数单号齐否判断)
								else if (CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
										|| CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.getCode()
										.equals(opsDo.getDlvEntire())) {
									List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(),
											opsDo.getOrderItem(), null, DoTypeEnum.JYCK);
									// 子项满足条件默认获齐全
									boolean itemEnough = true;
									isEnough = false;
									for (OpsDo subDo : doList) {
										OpsDoItem subDoItem = baseDoService.getDoItemByDoId(subDo.getDoId());
										if (null == subDoItem) {
											throw Exceptions.OpsException("无对应的opsDoItem:doId=>" + subDo.getDoId());
										}
										// 是否货齐
										isEnough = isEnoughToCrossForDo(subDo, subDoItem, invoice);
										// 其中有一DO不货齐，则不满足越库
										if (!isEnough) {
											itemEnough = false;
										}
									}
									// 满足下发DO条件
									if (itemEnough) {
										for (OpsDo subdo : doList) {
											// do 按订单10位一起下发得使用10位订单包含的DOid号
											if (!doIds.contains(subdo.getDoId())) {
												doIds.add(subdo.getDoId());
												// this.sendDoToWms
												// (subdo.getDoId(), 0, "");
											}
										}
									}
								}
								// 1-整单单仓货齐发货,3-整单多仓货齐发货
								// 7位订单齐了才可以越库（如果越库需要七位数下发WMS）
								else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
										|| CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
									// 初始
									isEnough = true;
									List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(), null, null,
											DoTypeEnum.JYCK);
									// 越库，判断订单其他项是否已经越库
									isEnough = isEnoughTocrossForFullOrder(doList, opsDo);
									// 符合货齐条件
									if (isEnough) {
										// 下发DO（按7位数）下发到WMS
										for (OpsDo subDo : doList) {
											if (!doIds.contains(subDo.getDoId())) {
												doIds.add(subDo.getDoId());
												// this.sendDoToWms
												// (subDo.getDoId(), 0, "");
											}
										}
									}

								}


							}
							else if (DoTypeEnum.CGDBCK.getType().equals(opsDo.getDoType())) {// 采购调拨出库
								// 直接下发DO（预到货时已拆分DOID）
								// 下发DO
								if (!doIds.contains(doId)) {
									doIds.add(doId);
									// this.sendDoToWms (doId, 0, "");
								}
							}
						}
					}
					// 以上合并采购执行下发执行DO完成
					// 开始执行合并采购执行下发PCO
					for (Map.Entry<String, List<OpsPcoItemInventory>> pcoEntry : mappco.entrySet()) {
						String pcoid = pcoEntry.getKey();
						Integer pcoItem = 0;
						List<OpsPcoItemInventory> pcoItemInventories = pcoEntry.getValue();
						OpsPco opsPco = opsPcoService.getPcoByPcoId(pcoid);
						if (null == opsPco) {
							throw Exceptions.OpsException("无pcoid：" + pcoid);
						}
						if (CollectionUtils.isEmpty(pcoItemInventories)) {
							throw Exceptions.OpsException("无OpsPcoItemInventory：" + pcoid);
						}
						pcoItem = pcoItemInventories.get(0).getPcoItem();
						List<OpsDo> doList = baseDoService.findDoListByOrder(opsPco.getOrderId(), opsPco.getOrderItem(),
								null, DoTypeEnum.JYCK);
						// pco orderId orderItem 对应只有1个DO
						if (CollectionUtils.isEmpty(doList)) {
							throw Exceptions.OpsException(
									"无对应的交易出库单：" + opsPco.getOrderId() + "-" + String.valueOf(opsPco.getOrderItem()));
						}
						OpsDo opsDo = doList.get(0);
						if (null == opsDo) {
							throw Exceptions.OpsException("无对应opsDo：对应订单" + opsPco.getOrderId() + "-"
									+ String.valueOf(opsPco.getOrderItem()));
						}
						String doId = opsDo.getDoId();
						List<OpsPcoItem> pcoItemList = opsPcoService.selectItemBypcoId(pcoid);
						if (CollectionUtils.isEmpty(pcoItemList)) {
							throw Exceptions.OpsException("OpsPcoItem无数据：" + pcoid);
						}
						// 加工单可否可越库(单项DO的PCOITEM齐了)
						// boolean isEnough = isEnoughTocrossForPco(opsPco,
						// pcoItemList, invoice);
						OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoid, pcoItem);
						if (null == opsPcoItem) {
							throw Exceptions.OpsException("OpsPcoItem无数据：" + pcoid + "-" + String.valueOf(pcoItem));
						}
						// 判断当行PCO-ITEM是否货齐
						boolean isEnough = isEnoughTocrossForPcoItem(opsPcoItem, invoice);
						if (isEnough) {
							// 2-单项分批随到发货，需要10位订单都齐了才可以下发DO,PCO
							// 0-单项单仓货齐发货(订单号10位数判断)
							if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())
									|| CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
									|| CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.getCode()
									.equals(opsDo.getDlvEntire())) {
								// todo 不越库不下发
							}
							// 1-整单单仓货齐发货，3-整单多仓货齐发货，需要7位订单都齐才同时下发DO ,PCO 7位全部
							// 1和3,
							// 暂定上库存(2022-9-7)，后续改善后再判断。需要7位数订单内所有ITEM全齐，才一次下发
							else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
									|| CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
								// 上库存INZK（且不下发DO,pco）
							}
						}
					}
					// 以上执行PCO完成
				}
				// 以上执行箱码大于在途总数（上库存）
				// 1个在途行1个do占用
				else if (mapdo.size() == 1) {
					RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INYY;
					int crossFlag = 0; // 1上预约 2越库
					String doId = "";
					// 只有一个DO
					for (Map.Entry<String, List<OpsDoItemInventory>> doEntry : mapdo.entrySet()) {
						doId = doEntry.getKey();
					}
					OpsDo opsDo = baseDoService.getDoByDoId(doId);
					if (null == opsDo) {
						throw Exceptions.OpsException("无opsDo:doId=>" + doId);
					}
					OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(doId);
					if (null == opsDoItem) {
						throw Exceptions.OpsException("无opsDoItem:doId=>" + doId);
					}

					// 一共判断DO类型 调拨出库、交易出库、采购调拨出库
					// 调拨出库(判断交货期，不判断信用拦截)
					// bugid：12563 调库单也需要越库 2023-12-07 C12961
					if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())
							&& StringUtils.equals(opsDo.getWarehouseCode(), opsDo.getGatherWarehouseCode())) {
						// 入在库
						roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INZK;
						RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, null,
								null, invoice, invoiceId);
						roConfirmItemList.add(roConfirmItem);
						roIds.add(roId);
					} else if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())
							|| (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())
							&& !StringUtils.equals(opsDo.getWarehouseCode(), opsDo.getGatherWarehouseCode()))) {
						boolean isEnough = false;
						// 是否货齐准备好，可以下发
						boolean isReady = this.isEnoughToCrossForDo(opsDo, opsDoItem, invoice);
						// 默认上预约货架
						roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INYY;
						crossFlag = 1;
						// 货齐代表可下发,继续判断上预约或越库指令
						if (isReady) {
							isEnough = this.isEnoughTocrossForDoFlag(opsDo, opsDoItem, invoice);
						}
						// 符合越库条件
						if (isEnough) {
							crossFlag = 2;
							// 越库指令
							roConfirmRecTypeEnum = RoConfirmRecTypeEnum.YK;
						}
						if (isReady) {// 货齐符合发货
							if (!doIds.contains(doId)) {
								doIds.add(doId);
								// this.sendDoToWms(doId, crossFlag, roId);
								// 符合越库条件（不信用拦截）
								if (roConfirmRecTypeEnum == RoConfirmRecTypeEnum.YK) {
									Map<Integer, String> roMap = new HashMap<>();
									roMap.put(2, roId);
									doYueKu.put(doId, roMap);
									// bug:11242 C12961 暂时存储要下发的do
									doSendMap.put(doId, roId);
								}
							}
						}
					}
					// 客户交易出库单
					else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
						// 按交货期客户(true
						// 的需按do.wldate日期判断大于等于当天，判断信用拦截（判断越库），格式yyyy-MM-dd)
						boolean isUseWlDate = this.exitsCustmerWldateByCustomerNo(opsDo.getCustomerNo());
						// 2单项分批随到发货(默认就越库)只判断信用拦截
						if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())) {
							boolean isReady = false;// 是否货齐准备好，可以下发
							// 信用拦截是否越库
							boolean isEnough = false;
							// 默认上预约
							roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INYY;
							crossFlag = 1;
							isReady = this.isEnoughToCrossForDo(opsDo, opsDoItem, invoice);
							// 货齐（可以下发DO）
							if (isReady) {
								if (isUseWlDate) {
									// 当天>=物流发货日期，可越库。 小于发货日不越库
									if (this.isArriveWlDate(opsDo.getWlDate())) {
										isEnough = this.checkCreditCross(opsDo.getOrderId(), opsDo.getOrderItem());
									} else {
										isEnough = false;
									}
								} else {// 不考虑纳期
									isEnough = this.checkCreditCross(opsDo.getOrderId(), opsDo.getOrderItem());
								}
								if (isEnough) {
									crossFlag = 2;
									roConfirmRecTypeEnum = RoConfirmRecTypeEnum.YK;
								}
							}
							if (isReady) {
								// this.sendDoToWms(doId, crossFlag, roId);
								// 下发越库
								if (RoConfirmRecTypeEnum.YK == roConfirmRecTypeEnum) {
									Map<Integer, String> roMap = new HashMap<>();
									roMap.put(2, roId);
									doYueKu.put(doId, roMap);
									// bug:11242 C12961 暂时存储要下发的do
									doSendMap.put(doId, roId);
								}
							}
						}
						// 0单项单仓货齐发货(按10位数单号齐否判断)
						else if (CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
								|| CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.getCode()
								.equals(opsDo.getDlvEntire())) {
							List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(),
									opsDo.getOrderItem(), null, DoTypeEnum.JYCK);
							// 是否货齐(默认货齐，有其中一个不齐，就不下发)
							boolean isReady = true;
							for (OpsDo subDo : doList) {
								OpsDoItem subDoItem = baseDoService.getDoItemByDoId(subDo.getDoId());
								if (null == subDoItem) {
									throw Exceptions.OpsException("无对应的subDo.opsDoItem:doId=>" + subDo.getDoId());
								}
								// 是否货齐
								boolean isItemEnough = this.isEnoughToCrossForDo(subDo, subDoItem, invoice);
								// 其中有一DO不货齐，则不满足越库
								if (!isItemEnough) {
									isReady = false;
								}
							}
							boolean isEnough = false;
							// 判断信用拦截，是否越库与上预约
							if (isReady) {
								isEnough = true;
								// 信用拦截
								boolean isItemEnough = true;
								for (OpsDo subDo : doList) {
									// 纳期客户
									if (isUseWlDate) {
										// 到交货期
										if (this.isArriveWlDate(subDo.getWlDate())) {
											isItemEnough = this.checkCreditCross(opsDo.getOrderId(),
													opsDo.getOrderItem());
										} else {
											// 没到交货期（默认上预约）
											isItemEnough = false;
										}
									} else {
										isItemEnough = this.checkCreditCross(opsDo.getOrderId(), opsDo.getOrderItem());
									}
									// 信用拦截有一项拦截则不越库
									if (!isItemEnough) {
										isEnough = false;
									}
								}
							}
							crossFlag = 1;
							roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INYY;
							// 上面已经判断货齐并下发 1通过 0 不通过
							if (isEnough) {
								// 判断越库指令还是预约指令
								isEnough = this.isEnoughTocrossForDoFlag(opsDo, opsDoItem, invoice);
							}
							// 是否越库 true=越库
							if (isEnough) {
								crossFlag = 2;
								roConfirmRecTypeEnum = RoConfirmRecTypeEnum.YK;
							}
							// 符合下发条件
							if (isReady) {
								// 2023-04-07 新增 遍历按10位订单DO数组 b28029
								// 如存在一个发票两个或以上DOID（如调拨），在第一个DOID就下发1一个DOID同时，把剩余DOID一起下发。A
								// B C doid
								// 遍历到下一个DOID，因为已在第一次下发过，就不再下发了（只有同一票来货且）
								for (OpsDo subdo : doList) {
									if (!doIds.contains(subdo.getDoId())) {
										// 当前行越库或预约指令
										if (subdo.getDoId().equals(doId)) {
											doIds.add(subdo.getDoId());
											// this.sendDoToWms(subdo.getDoId(),
											// crossFlag, roId);
											// 不信用拦截(符合越库条件)
											if (roConfirmRecTypeEnum == RoConfirmRecTypeEnum.YK) {
												Map<Integer, String> roMap = new HashMap<>();
												roMap.put(2, roId);
												doYueKu.put(doId, roMap);
												// bug:11242 C12961 暂时存储要下发的do
												doSendMap.put(doId, roId);
											}
										} else {
											// 其他DOID直接下发指令（非越库）
											// 检测当前DOID 存不存在同发票，存在
											// 1、修改doyueku,2、下发越库标记
											// （不需要发票号判断，因上面代码以进行判断）
											List<OpsDoItemInventory> subDoItemInventoryList = baseDoService
													.getDoItemInventoryByDoId(subdo.getDoId(),
															InventoryTableTypeEnum.TRANS);
											doIds.add(subdo.getDoId());
											// 表示无在途，直接下发在库
											if (CollectionUtils.isEmpty(subDoItemInventoryList)) {
												// this.sendDoToWms
												// (subdo.getDoId(), 0, "");
											} else {
												// 需越库或预约
												// this.sendDoToWms(subdo.getDoId(),
												// crossFlag, roId);
												// 如果同一个发票，2个调拨单订单里，第一个下发的时候，会用ROID-1，那第二个也会一起下发，用ROID-1,这就不对了，应该第二个订单用ROID-2
												if (roConfirmRecTypeEnum == RoConfirmRecTypeEnum.YK
														|| roConfirmRecTypeEnum == RoConfirmRecTypeEnum.INYY) {
													// 找到对应ROid
													String subRoId = roId;
													Long subDoInventoryId = subDoItemInventoryList.get(0)
															.getInventoryId();
													List<OpsRoItemInventory> opsRoItemInventories = baseRoService
															.findRoItemInventoryByInventoryId(subDoInventoryId);
													if (!CollectionUtils.isEmpty(opsRoItemInventories)) {
														subRoId = opsRoItemInventories.get(0).getRoId();
													}
													Map<Integer, String> roMap = new HashMap<>();
													roMap.put(roConfirmRecTypeEnum == RoConfirmRecTypeEnum.YK ? 2 : 1,
															subRoId);
													doYueKu.put(subdo.getDoId(), roMap);
													if (roConfirmRecTypeEnum == RoConfirmRecTypeEnum.YK) {
														// bug:11242 C12961
														// 暂时存储要下发的do
														doSendMap.put(subdo.getDoId(), subRoId);
													}
												}
											}
										}
									}
								}
							}
						}
						// 1-整单单仓货齐发货,3-整单多仓货齐发货 7位订单齐了才可以越库（如果越库需要七位数下发WMS）
						else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
								|| CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
							// 判断当前DO是否越库
							boolean isEnough = this.isEnoughToCrossForDo(opsDo, opsDoItem, invoice);
							// 是否货齐(默认货齐，有其中一个不齐，就不下发)
							boolean isReady = true;
							if (isEnough) {
								// 越库，判断订单其他项是否已经越库（DOType=交易出库）
								List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(), null, null,
										DoTypeEnum.JYCK);
								// 是否货齐（7位数）
								isReady = isEnoughTocrossForFullOrder(doList, opsDo);
								// 符合货齐条件
								if (isReady) {
									// 考虑纳期
									for (OpsDo subDo : doList) {
										boolean itemEnough = false;// 子项信用拦截
										if (this.isArriveWlDate(subDo.getWlDate())) {
											itemEnough = this.checkCreditCross(opsDo.getOrderId(),
													opsDo.getOrderItem());
										} else {
											// 没到交货期（默认上预约）
											itemEnough = false;
										}
										// 信用拦截有一项拦截则不越库　
										if (!itemEnough) {
											isEnough = false;
										}
									}
									// 默认预约　
									roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INYY;
									crossFlag = 1;
									// 通过信用拦截
									if (isEnough) {
										// 判断预约指令还是越库指令　
										isEnough = this.isEnoughTocrossForDoFlag(opsDo, opsDoItem, invoice);
										if (isEnough) {
											crossFlag = 2;
											roConfirmRecTypeEnum = RoConfirmRecTypeEnum.YK;
										}
									}
									// 是要下发DOID　
									if (isReady) {
										// 下发DO（按7位数）先下发到WMS　
										for (OpsDo subDo : doList) {
											if (subDo.getDoId().equals(doId)) {
												// 带上批次属性标记
												if (!doIds.contains(subDo.getDoId())) {
													doIds.add(subDo.getDoId());
													// this.sendDoToWms(subDo.getDoId(),
													// crossFlag, roId);
													// 符合越库条件（不拦截）
													if (roConfirmRecTypeEnum == RoConfirmRecTypeEnum.YK) {
														Map<Integer, String> roMap = new HashMap<>();
														roMap.put(2, roId);
														doYueKu.put(doId, roMap);
														// bug:11242 C12961
														// 暂时存储要下发的do
														doSendMap.put(doId, roId);
													}
												}
											} else {
												// 直接下发DOID
												if (!doIds.contains(subDo.getDoId())) {
													doIds.add(subDo.getDoId());
													// this.sendDoToWms
													// (subDo.getDoId(), 0,
													// roId);
												}
											}
										}
									}
								}
							}
						}
						else if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(opsDo.getDlvEntire())){
							if (!doIds.contains(opsDo.getDoId())) {
								doIds.add(opsDo.getDoId());
							}
						}

					}
					// 采购调拨出库 直接下发DO（预到货时已拆分DOID）
					else if (DoTypeEnum.CGDBCK.getType().equals(opsDo.getDoType())) {
						boolean isEnough = false;
						// 是否货齐
						isEnough = this.isEnoughToCrossForDo(opsDo, opsDoItem, invoice);
						roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INYY;
						crossFlag = 1;
						// 采购调拨出库越库（如分批来，前端已拆分DOID）
						if (isEnough) {
							// 判断上预约还是越库
							isEnough = this.isEnoughTocrossForDoFlag(opsDo, opsDoItem, invoice);
							// 下发越库指令还是预约指令
							if (isEnough) {
								crossFlag = 2;
								roConfirmRecTypeEnum = RoConfirmRecTypeEnum.YK;
							}
							if (isEnough) {
								if (!doIds.contains(doId)) {
									// 带上批次属性
									// this.sendDoToWms(doId, crossFlag, roId);
									doIds.add(doId);
									Map<Integer, String> roMap = new HashMap<>();
									roMap.put(2, roId);
									doYueKu.put(doId, roMap);
									// bug:11242 C12961 暂时存储要下发的do
									doSendMap.put(doId, roId);
								}
							}
						}
					}
					// 单行结束
					if (!roIds.contains(roId)) {
						// 返回富勒
						RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, doId,
								null, invoice, invoiceId);
						roConfirmItemList.add(roConfirmItem);
						roIds.add(roId);
						if (RoConfirmRecTypeEnum.INYY == roConfirmRecTypeEnum) {
							Map<Integer, String> roMap = new HashMap<>();
							roMap.put(1, roId);
							doYueKu.put(doId, roMap);
						}
					}
				} // 以上处理1个在途行1个do占用 完毕
				// 1个在途行多个do占用（不等于1）,返回上库存
				else if (mapdo.size() > 1) {
					if (!roIds.contains(roId)) {
						// 上货架指令
						RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INZK;
						RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, null,
								null, invoice, invoiceId);
						roConfirmItemList.add(roConfirmItem);
						roIds.add(roId);
					}
					// 判断越库
					for (Map.Entry<String, List<OpsDoItemInventory>> doEntry : mapdo.entrySet()) {
						String doId = doEntry.getKey();
						OpsDo opsDo = baseDoService.getDoByDoId(doId);
						if (null == opsDo) {
							throw Exceptions.OpsException("无对应的opsDo:doId=>" + doId);
						}
						OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(doId);
						if (null == opsDoItem) {
							throw Exceptions.OpsException("无对应的opsDoItem:doId=>" + doId);
						}

						boolean isReady = this.isEnoughToCrossForDo(opsDo, opsDoItem, invoice);
						// 单DOID货齐，继续执行下面逻辑判断
						if (isReady) {
							// 按交货期客户(true
							// 的需按do.wldate日期判断大于等于当天，判断信用拦截（判断越库），格式yyyy-MM-dd)
							// 调拨出库(判断交货期，不判断信用拦截)
							if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
								if (!doIds.contains(doId)) {
									doIds.add(doId);
									// this.sendDoToWms (doId, 0, "");
								}
							}
							// 客户交易出库单
							else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
								// 2单项分批随到发货(默认就越库)只判断信用拦截
								if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())) {
									// 下发DO
									if (!doIds.contains(doId)) {
										doIds.add(doId);
										// this.sendDoToWms (doId, 0, "");
									}
								}
								// 0单项单仓货齐发货(按10位数单号齐否判断)
								else if (CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
										|| CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.getCode()
										.equals(opsDo.getDlvEntire())) {
									List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(),
											opsDo.getOrderItem(), null, DoTypeEnum.JYCK);
									// 默认货齐（其中一条不齐就当不齐）
									isReady = true;
									for (OpsDo subdo : doList) {
										OpsDoItem subOpsDoItem = baseDoService.getDoItemByDoId(subdo.getDoId());
										// 判断货齐
										boolean isEnough = this.isEnoughToCrossForDo(subdo, subOpsDoItem, invoice);
										// 其中有一DO不货齐，则不满足越库
										if (!isEnough) {
											isReady = false;
										}
									}
									// 以上货齐判断
									if (isReady) {
										for (OpsDo subdo : doList) {
											if (!doIds.contains(subdo.getDoId())) {
												doIds.add(subdo.getDoId());
												// this.sendDoToWms
												// (subdo.getDoId(), 0, "");
											}
										}
									}
								}
								// 1-整单单仓货齐发货,3-整单多仓货齐发货
								// 10位订单齐了才可以越库（如果越库需要七位数下发WMS）
								else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
										|| CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
									boolean isEnough = true;
									// 越库，判断订单其他项是否已经越库
									List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(), null, null,
											DoTypeEnum.JYCK);
									// 货齐（按7位数）
									isEnough = this.isEnoughTocrossForFullOrder(doList, opsDo);
									if (isEnough) {
										// 下发DO （不考虑纳期和信用拦截）
										for (OpsDo opsSubDo : doList) {
											if (!doIds.contains(opsSubDo.getDoId())) {
												doIds.add(opsSubDo.getDoId());
												// this.sendDoToWms
												// (opsSubDo.getDoId(), 0, "");
											}
										}
									}
								}
							} else if (DoTypeEnum.CGDBCK.getType().equals(opsDo.getDoType())) {// 采购调拨出库
								// 直接下发DO（预到货时已拆分DOID）
								// 且越库（如分批来，前端已拆分DOID）
								// 判断是否货齐
								if (!doIds.contains(doId)) {
									doIds.add(doId);
									// this.sendDoToWms (doId, 0, "");
								}
							}
						}
					}
				}
				// 以上处理DO 1个在途行1个do 1个在途行N个do占用,返回上库存结束
				// PCO是否越库条件 1个在途1个PCO
				else if (mappco.size() == 1) {
					String pcoid = "";
					Integer pcoItem = 0;
					// 有且仅有1行PcoItemInventory
					List<OpsPcoItemInventory> pcoItemInventories = new ArrayList<>();
					// 有且仅有1行PCO
					for (Map.Entry<String, List<OpsPcoItemInventory>> pcoEntry : mappco.entrySet()) {
						pcoid = pcoEntry.getKey();
						pcoItemInventories = pcoEntry.getValue();
					}
					if (CollectionUtils.isEmpty(pcoItemInventories)) {
						throw Exceptions.OpsException("pcoItemInventories无数据：" + pcoid + "-" + String.valueOf(pcoItem));
					}
					OpsPco opsPco = opsPcoService.selectPcoBypcoId(pcoid);
					if (null == opsPco) {
						throw Exceptions.OpsException("opsPco无数据：" + pcoid);
					}
					List<OpsPcoItem> pcoItemList = opsPcoService.selectItemBypcoId(pcoid);
					if (CollectionUtils.isEmpty(pcoItemList)) {
						throw Exceptions.OpsException("OpsPcoItem无数据：" + pcoid);
					}
					// 预到货改标识符（子项到货）
					pcoItem = pcoItemInventories.get(0).getPcoItem();
					// PCO-pcoItemInventory 一行
					OpsPcoItemInventory pcoItemInventory = pcoItemInventories.get(0);
					opsPcoService.updatePcoItemIsCrossByPcoItem(pcoid, pcoItemInventory.getPcoItem(), true);
					// 当前行PCOITEM
					OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoid, pcoItem);
					if (null == opsPcoItem) {
						throw Exceptions.OpsException("OpsPcoItem无数据：" + pcoid + ".Item:" + String.valueOf(pcoItem));
					}
					// PCO越库与否取决于10位数订单是否全部到齐，齐了下发DO，PCO
					List<OpsDo> doList = baseDoService.findDoListByOrder(opsPco.getOrderId(), opsPco.getOrderItem(),
							null, DoTypeEnum.JYCK);
					// pco orderId orderItem 对应只有1个DO
					if (CollectionUtils.isEmpty(doList)) {
						throw Exceptions.OpsException(
								"无对应的交易出库单：" + opsPco.getOrderId() + "-" + String.valueOf(opsPco.getOrderItem()));
					}
					OpsDo opsDo = doList.get(0);
					if (null == opsDo) {
						throw Exceptions.OpsException(
								"无对应opsDo：对应订单" + opsPco.getOrderId() + "-" + String.valueOf(opsPco.getOrderItem()));
					}
					String doId = opsDo.getDoId();
					boolean isEnough = false;
					// 是否货齐发货（当前行Item）
					boolean ready = this.isEnoughTocrossForPcoItem(opsPcoItem, invoice);
					// 当前行满足发货条件（不拦截，纳期符合）
					if (ready) {
						// 0-单项单仓货齐发货、2-单项分批随到发货 (订单号10位数判断)
						if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())
								|| CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
								|| CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.getCode()
								.equals(opsDo.getDlvEntire())) {
							// 判断PCO其他子项是否都货齐（十位数）
							isEnough = this.isEnoughTocrossForPcoId(opsDo, pcoItemList, opsPcoItem);
							if (isEnough) {
								// 纳期客户
								boolean isUseWlDate = this.exitsCustmerWldateByCustomerNo(opsDo.getCustomerNo());
								// 纳期客户
								if (isUseWlDate) {
									// 符合纳期在判断信用拦截
									if (this.isArriveWlDate(opsDo.getWlDate())) {
										isEnough = this.checkCreditCross(opsDo.getOrderId(), opsDo.getOrderItem());
									} else {
										isEnough = false;
									}
								} else {
									isEnough = this.checkCreditCross(opsDo.getOrderId(), opsDo.getOrderItem());
								}
								// 默认预约
								RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.PCOYY;
								// 货齐且不信用拦截，越库
								if (isEnough) {
									roConfirmRecTypeEnum = RoConfirmRecTypeEnum.PCOYK;
								}
								Map<String, String> yukuMap = new HashMap<>();
								for (OpsPcoItem subPcoItem : pcoItemList) {
									// 查外采购
									List<OpsPcoItemInventory> subPcoItemInventories = this.opsPcoService
											.findPcoItemInventoryByPcoIdAndItem(subPcoItem.getPcoId(),
													subPcoItem.getPcoItem());
									// PCO 是外部采购的才传lot13
									// 外部到货的PCO项号需要传PCOID，库存的不需要传
									Boolean isLot13 = false;
									for (OpsPcoItemInventory opsPcoItemInventory : subPcoItemInventories) {
										isLot13 = false;
										if (InventoryTableTypeEnum.TRANS.getType()
												.equals(opsPcoItemInventory.getInventoryTableType())) {
											isLot13 = true;
										}
										if (isLot13) {
											yukuMap.put(subPcoItem.getSubModelno(), subPcoItem.getPcoId());
										}
									}
								}
								if (!roIds.contains(roId)) {
									// 单项单仓货齐发货越库（下发PCO do） 按DO order(按10位数)
									// 批量DOID下发
									RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(),
											roId, opsDo.getDoId(), pcoid, invoice, invoiceId);
									roConfirmItemList.add(roConfirmItem);
								}
								doIds.add(doId);
								// 预约的、越库的PCO带上YuKuMap，越库的YuKuRoId （不需要YuKuRoId）
								OpsSendPcoAndDoMidIDDto pcoAndDoMidIDDto = new OpsSendPcoAndDoMidIDDto();
								pcoAndDoMidIDDto.setDoId(opsDo.getDoId());
								pcoAndDoMidIDDto.setPcoId(opsPco.getPcoId());
								pcoAndDoMidIDDto.setYuKuMap(yukuMap);
								// 以上最后一项货齐判断是否符合下发是否越库或预约
								// 越库需带上此标记
								if (isEnough) {
									pcoAndDoMidIDDto.setYuKuRoId(roId);
								}
								// 越库的时候带上自己这箱以及之前的外部先来货的
								if (roConfirmRecTypeEnum == RoConfirmRecTypeEnum.PCOYK) {
									// wmDoService.updateWMSPcoAddDoTwo(pcoAndDoMidIDDto);
									// bug:10269 C12961 2023-03-31
									// 延迟下发问题：pco延迟下发时，不在pco表中写ro_id
									// 最后一箱（也就是越库需更新）
									pcoYueKu.put(pcoid, roId);
									Map<Integer, String> roMap = new HashMap<>();
									roMap.put(2, roId);
									doYueKu.put(doId, roMap);
									// bug:11242 C12961 暂时存储要下发的do、pco
									doSendMap.put(doId, roId);
									pcoSendMap.put(opsPco.getPcoId(), pcoAndDoMidIDDto);
								}
							} else {
								// PCOITEM没货齐不下发
								if (!roIds.contains(roId)) {
									RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.PCOYY;
									// add BUG 8501 B28029
									RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(),
											roId, doId, pcoid, invoice, invoiceId);
									// end
									roConfirmItemList.add(roConfirmItem);
								}
							}
						}
						// 1-整单单仓货齐发货，3-整单多仓货齐发货，需要7位订单都齐才同时下发DO ,PCO 7位全部
						// 1和3, 暂定上库存(2022-9-7)，后续改善后再判断。需要7位数订单内所有ITEM全齐，才一次下发
						else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
								|| CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())
								|| CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(opsDo.getDlvEntire())
						) {
							// 上库存INZK（且不下发DO,pco）
							if (!roIds.contains(roId)) {
								RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.PCOYY;
								RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId,
										opsDo.getDoId(), pcoid, invoice, invoiceId);
								roConfirmItemList.add(roConfirmItem);
							}
						}
					} else {// PCO （当前行Item） 货不齐，上预约
						if (!roIds.contains(roId)) {
							RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.PCOYY;
							RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId,
									opsDo.getDoId(), pcoid, invoice, invoiceId);
							roConfirmItemList.add(roConfirmItem);
						}
					}
				}
				// 以上处理1个PCO
				// 1个在途多个多个PCO
				else if (mappco.size() > 1) {
					// 提示返回上库存
					RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INZK;
					RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, null, null,
							invoice, invoiceId);
					roConfirmItemList.add(roConfirmItem);
					// 执行判断能否下发DO,PCO指令
					for (Map.Entry<String, List<OpsPcoItemInventory>> pcoEntry : mappco.entrySet()) {
						String pcoid = pcoEntry.getKey();
						Integer pcoItem = 0;
						List<OpsPcoItemInventory> pcoItemInventories = pcoEntry.getValue();
						if (CollectionUtils.isEmpty(pcoItemInventories)) {
							throw Exceptions.OpsException("pcoItemInventories无数据：" + pcoid);
						}
						OpsPco opsPco = opsPcoService.selectPcoBypcoId(pcoid);
						if (null == opsPco) {
							throw Exceptions.OpsException("opsPco无数据：" + pcoid);
						}
						List<OpsPcoItem> pcoItemList = opsPcoService.selectItemBypcoId(pcoid);
						if (CollectionUtils.isEmpty(pcoItemList)) {
							throw Exceptions.OpsException("OpsPcoItem无数据：" + pcoid);
						}
						// 预到货改标识符（子项到货）
						OpsPcoItemInventory pcoItemInventory = pcoItemInventories.get(0);
						opsPcoService.updatePcoItemIsCrossByPcoItem(pcoid, pcoItemInventory.getPcoItem(), true);
						pcoItem = pcoItemInventories.get(0).getPcoItem();
						OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoid, pcoItem);
						if (null == opsPcoItem) {
							throw Exceptions.OpsException("opsPcoItem无数据：" + pcoid);
						}
						// 加工单可否可越库(单项DO的PCOITEM齐了)
						boolean isEnough = this.isEnoughTocrossForPcoItem(opsPcoItem, invoice);
						if (isEnough) {
							// PCO越库与否取决于10位数订单是否全部到齐，齐了下发DO，PCO
							List<OpsDo> doList = baseDoService.findDoListByOrder(opsPco.getOrderId(),
									opsPco.getOrderItem(), null, DoTypeEnum.JYCK);
							// pco orderId orderItem 对应只有1个DO
							if (CollectionUtils.isEmpty(doList)) {
								throw Exceptions.OpsException("无对应的交易出库单：" + opsPco.getOrderId() + "-"
										+ String.valueOf(opsPco.getOrderItem()));
							}
							OpsDo opsDo = doList.get(0);
							if (null == opsDo) {
								throw Exceptions.OpsException("无对应opsDo：对应订单" + opsPco.getOrderId() + "-"
										+ String.valueOf(opsPco.getOrderItem()));
							}
							// 0-单项单仓货齐发货 or 2-单项分批随到发货(订单号10位数判断)
							if (CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
									|| CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.getCode()
									.equals(opsDo.getDlvEntire())
									|| CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())) {
								// todo 不越库不下发
							}
							// 1-整单单仓货齐发货，3-整单多仓货齐发货，需要7位订单都齐才同时下发DO ,PCO 7位全部
							// 1和3,
							// 暂定上库存(2022-9-7)，后续改善后再判断。需要7位数订单内所有ITEM全齐，才一次下发
							else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
									|| CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
								// 上库存INZK（且不下发DO,pco）
							}
						}
					}
				}
				// 以上执行完1单po在途行数DO-PCO判断下发
				if (mapdo.size() == 0 && mappco.size() == 0) {
					// 无DO/PCO数据只能上库存INZK（且不下发DO）
					// 补库采购入库收货
					RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INZK;
					RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, null, null,
							invoice, invoiceId);
					roConfirmItemList.add(roConfirmItem);
					continue;
				}
			}
			// 以上单行po在途行数 inventoryMoves.sise=1 执行结束
			else if (inventoryMoves.size() > 1) {
				// 大于1 都必走上货架INZK
				// PCOID，PcoItemInventory(合并采购)
				Map<String, List<OpsPcoItemInventory>> mappcoMerge = new HashMap<>();
				// 关联DO(上库存)
				for (Map.Entry<String, List<OpsDoItemInventory>> doEntry : mapdo.entrySet()) {
					// 下发DO（越库）
					String doId = doEntry.getKey();
					OpsDo opsDo = baseDoService.getDoByDoId(doId);
					if (null == opsDo) {
						throw Exceptions.OpsException("无DOID：" + doId);
					}
					// 一个DO对应一个ITEM
					OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(doId);
					if (null == opsDoItem) {
						throw Exceptions.OpsException("无opsDoItem：" + doId);
					}
					// 是否货齐
					boolean isEnough = isEnoughToCrossForDo(opsDo, opsDoItem, invoice);
					if (isEnough) {
						// 调拨出库(判断交货期，不判断信用拦截)
						if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
							if (!doIds.contains(doId)) {
								doIds.add(doId);
								// this.sendDoToWms (doId, 0, "");
							}
						}
						// 客户交易出库单
						else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
							// 2单项分批随到发货,默认下发
							if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())) {
								// 下发WMS-DO
								if (!doIds.contains(doId)) {
									doIds.add(doId);
									// this.sendDoToWms (doId, 0, "");
								}
							}
							// 0单项单仓货齐发货(按10位数单号齐否判断)
							else if (CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
									|| CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.getCode()
									.equals(opsDo.getDlvEntire())) {
								List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(),
										opsDo.getOrderItem(), null, DoTypeEnum.JYCK);
								// 子项满足条件默认获齐全
								boolean itemEnough = true;
								for (OpsDo subdo : doList) {
									OpsDoItem subOpsDoItem = baseDoService.getDoItemByDoId(doId);
									if (null == opsDoItem) {
										throw Exceptions.OpsException("无对应的opsDoItem:doId=>" + doId);
									}
									// 是否齐
									isEnough = this.isEnoughToCrossForDo(subdo, subOpsDoItem, invoice);
									// 其中有一DO不货齐，则不满足下发
									if (!isEnough) {
										itemEnough = false;
									}
								}
								// 满足下发DO条件
								if (itemEnough) {
									for (OpsDo subdo : doList) {
										// do 按订单10位一起下发
										if (!doIds.contains(subdo.getDoId())) {
											doIds.add(subdo.getDoId());
											// this.sendDoToWms
											// (subdo.getDoId(), 0, roId);
										}
									}
								}
							}
							// 1-整单单仓货齐发货,3-整单多仓货齐发货 7位订单齐了才可以越库（如果越库需要七位数下发WMS）
							else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
									|| CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
								// 默认货齐（判断有其中一项不齐就退出）
								isEnough = true;
								// 七位订单号判断
								List<OpsDo> doList = baseDoService.findDoListByOrder(opsDo.getOrderId(), null, null,
										DoTypeEnum.JYCK);
								// 判断货齐按七位数
								isEnough = this.isEnoughTocrossForFullOrder(doList, opsDo);
								if (isEnough) {
									// 按7位数订单下发
									for (OpsDo opsDoSub : doList) {
										// 下发WMS-DO
										if (!doIds.contains(opsDoSub.getDoId())) {
											doIds.add(opsDoSub.getDoId());
											// this.sendDoToWms
											// (opsDoSub.getDoId(), 0, roId);
										}
									}
								}
							}
						} else if (DoTypeEnum.CGDBCK.getType().equals(opsDo.getDoType())) {// 采购调拨出库
							// 直接下发DO（预到货时已拆分DOID）
							// 如分批来，前端已拆分DOID
							// 收齐才下发DO
							if (!doIds.contains(doId)) {
								doIds.add(doId);
								// this.sendDoToWms (doId, 0, roId);
							}
						}
					}
				}
				// 关联PCO
				for (Map.Entry<String, List<OpsPcoItemInventory>> pcoEntry : mappcoMerge.entrySet()) {
					String pcoId = pcoEntry.getKey();
					Integer pcoItem = 0;
					OpsPco opsPco = opsPcoService.selectPcoBypcoId(pcoId);
					if (null == opsPco) {
						throw Exceptions.OpsException("opsPco无数据：" + pcoId);
					}
					List<OpsPcoItem> pcoItemList = opsPcoService.selectItemBypcoId(pcoId);
					if (CollectionUtils.isEmpty(pcoItemList)) {
						throw Exceptions.OpsException("OpsPcoItem无数据：" + pcoId);
					}
					List<OpsPcoItemInventory> pcoItemInventories = pcoEntry.getValue();
					if (CollectionUtils.isEmpty(pcoItemInventories)) {
						throw Exceptions.OpsException("OpsItemInventories无数据：" + pcoId);
					}
					pcoItem = pcoItemInventories.get(0).getPcoItem();
					OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoId, pcoItem);
					if (null == opsPco) {
						throw Exceptions.OpsException("opsPcoItem无数据：" + pcoId + "-" + String.valueOf(pcoItem));
					}
					// 货齐（PCOItem单行货齐）
					boolean isEnough = this.isEnoughTocrossForPcoItem(opsPcoItem, invoice);
					// PCO找DO可越库
					if (isEnough) {
						List<OpsDo> doList = baseDoService.findDoListByOrder(opsPco.getOrderId(), opsPco.getOrderItem(),
								null, DoTypeEnum.JYCK);
						if (CollectionUtils.isEmpty(doList)) {
							throw Exceptions.OpsException(
									"无对应的交易出库单：" + opsPco.getOrderId() + "-" + String.valueOf(opsPco.getOrderItem()));
						}
						// pco orderId orderItem 对应只有1个DO
						OpsDo opsDo = doList.get(0);
						if (null == opsDo) {
							throw Exceptions.OpsException("无对应opsDo：对应订单" + opsPco.getOrderId() + "-"
									+ String.valueOf(opsPco.getOrderItem()));
						}
						// 0-单项单仓货齐发货 2-单项分批随到发货 (订单号10位数判断)
						if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(opsDo.getDlvEntire())
								|| CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
								|| CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.getCode()
								.equals(opsDo.getDlvEntire())) {
							// todo 不越库不下发
						}
						// 1-整单单仓货齐发货，3-整单多仓货齐发货，需要7位订单都齐才同时下发DO ,PCO 7位全部
						// 1和3, 暂定上库存(2022-9-7)，后续改善后再判断。需要7位数订单内所有ITEM全齐，才一次下发
						else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(opsDo.getDlvEntire())
								|| CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(opsDo.getDlvEntire())) {
							// 上库存INZK（且不下发DO,pco）
						}
					}
				}
				// 返回上货架库存
				RoConfirmRecTypeEnum roConfirmRecTypeEnum = RoConfirmRecTypeEnum.INZK;
				RoConfirmItem roConfirmItem = new RoConfirmItem(roConfirmRecTypeEnum.getType(), roId, null, null,
						invoice, invoiceId);
				roConfirmItemList.add(roConfirmItem);
				roIds.add(roId);
				continue;
			}
		}
		// 以上PO遍历结束
		if (CollectionUtils.isEmpty(roConfirmItemList)) {
			throw Exceptions.OpsException("无到货确认" + invoice, invoice, null, null);
		}
		// 写入状态变更事件表(必须在下发之前)
		for (OpsInventoryMove inventoryMove : list) {
			// DO发货在途中
			List<OpsDoItemInventory> doItemInventoryList = baseDoService
					.getDoItemInventoryByInventoryId(inventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
			for (OpsDoItemInventory itemInventory : doItemInventoryList) {
				OpsDo opsDo = baseDoService.getDoByDoId(itemInventory.getDoId());
				if (null == opsDo) {
					throw Exceptions.OpsException("无对应的opsDo:DoId=>" + itemInventory.getDoId());
				}
				// 采购调拨出不记录
				if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())||DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
					// 插入事件前增加校验，如果不是客户订单，则不插入事件
					StatusParamDto param = new StatusParamDto();
					param.setInvoiceNo(invoice);
					param.setInvoiceId(invoiceId);
					param.setQuantity(itemInventory.getUseQty());
					param.setDate(DateUtil.formatDateTime(new Date()));
					if (SourceTypeEnum.PURCHASE.getType().equals(inventoryMove.getSourceType())) {
						param.setPoNo(inventoryMove.getAssociateNo());
						param.setPoItem(inventoryMove.getAssociateNoItem());
						param.setPoSplitNo(inventoryMove.getAssociateNoSplitno());
					}
					// 到货确认事件
					customerEventPublisher.customerOrderEvent(WAREHOUSE_GOODS_CONFIRM,
							opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()), OrderNoInfo.getFromDo(opsDo).getSplitNoNotNull(), param);

				}
			}
			// PCO发货在途中
			List<OpsPcoItemInventory> pcoItemInventoryList = opsPcoService
					.getOpsPcoItemInventoryByInventoryId(inventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
			for (OpsPcoItemInventory pcoItemInventory : pcoItemInventoryList) {
				OpsPco opsPco = opsPcoService.selectPcoBypcoId(pcoItemInventory.getPcoId());
				if (null == opsPco) {
					throw Exceptions.OpsException("无对应的opsPco:PcoId=>" + pcoItemInventory.getPcoId());
				}
				StatusParamDto param = new StatusParamDto();
				param.setInvoiceNo(invoice);
				param.setInvoiceId(invoiceId);
				param.setQuantity(pcoItemInventory.getUseQty());
				param.setDate(DateUtil.formatDateTime(new Date()));
				if (SourceTypeEnum.PURCHASE.getType().equals(inventoryMove.getSourceType())) {
					param.setPoNo(inventoryMove.getAssociateNo());
					param.setPoItem(inventoryMove.getAssociateNoItem());
					param.setPoSplitNo(inventoryMove.getAssociateNoSplitno());
				}
				// 到货确认事件
				customerEventPublisher.customerOrderEvent(WAREHOUSE_GOODS_CONFIRM,
						opsPco.getOrderId(), Integer.valueOf(opsPco.getOrderItem()), pcoItemInventory.getPcoItem(), param);
			}
		}
		List<OpsRo> roList = baseRoService.findRoByInvoiceNoAndInvoiceId(invoice,invoiceId);
		for (OpsRo opsRo : roList) {
			if (RoTypeEnum.TKRK.getType().equals(opsRo.getRoType())) {
				transferEventPublisher.transferOrderEvent(EventSourceEnum.INVENTORY_TRANS_ORDER_GOODS_CONFIRM, opsRo);
			}
		}
		// do越库更新 key:Doid, value<1预约2越库,roid>
		for (Map.Entry<String, Map<Integer, String>> doEntry : doYueKu.entrySet()) {
			String doId = doEntry.getKey();
			for (Map.Entry<Integer, String> doTypeEntry : doEntry.getValue().entrySet()) {
				Integer crossType = doTypeEntry.getKey();
				String roId = doTypeEntry.getValue();
				opsDoService.updateOpsDoForCrossRoId(doId, crossType, roId);
			}
		}
		// 更新PCO，RO字段
		for (Map.Entry<String, String> pcoEntry : pcoYueKu.entrySet()) {
			String pcoId = pcoEntry.getKey();
			String roId = pcoEntry.getValue();
			opsPcoService.updateOpsPcoForCrossRoId(pcoId, roId);
		}
		// 操作状态改发票确认
		baseInventoryService.updateInventoryMoveOptStatusByInvoiceNo(invoice, invoiceId,
				OptStatusEnum.GOODS_CONFIRM);
		ImpInvoiceEventLog eventLog = new ImpInvoiceEventLog();
		eventLog.setOpType("/order/confirmgoods");
		eventLog.setRequestParam(invoice);
		eventLog.setOpStartTime(fromDate);
		eventLog.setReturnData(JSON.toJSONString(roConfirmItemList));
		this.addImpInvoiceEventLog(eventLog);
		// 写入到货确认返回结果
		List<OpsRoConfirmLog> confirmLogs = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
		String batchNo = String.format("%s%s", warehouse, sdf.format(System.currentTimeMillis()));
		for (RoConfirmItem roConfirmItem : roConfirmItemList) {
			OpsRoConfirmLog confirmLog = new OpsRoConfirmLog();
			confirmLog.setBatchNo(batchNo);
			confirmLog.setRoId(roConfirmItem.getRoId());
			confirmLog.setDoId(roConfirmItem.getDoid());
			confirmLog.setReceiveType(roConfirmItem.getReceiveType());
			confirmLog.setPcoId(roConfirmItem.getPcoid());
			confirmLog.setInvoiceNo(roConfirmItem.getInvoiceNo());
			confirmLog.setInvoiceId(roConfirmItem.getInvoiceId());
			confirmLog.setStatus(1);
			confirmLog.setCreator(UserDto.WMS.getUserName());
			confirmLog.setCreTime(new Date());
			confirmLogs.add(confirmLog);
		}
		roConfirmLogService.insertBatchConfirmLog(confirmLogs);
		return new GoodsConfirmDto(roConfirmItemList, doSendMap, pcoSendMap);
	}

	/**
	 * @description：5.9 调拨出库发票数据回传（先调用handconfirm再doconfirm没有前后关联）
	 *
	 * @author ：c02483
	 * @date ：Created in 2022/1/26 15:44
	 */
    /*
	@Deprecated
	@Override
	public void handconfirm(HandConfirm handConfirm) throws OpsException {
		if (Objects.isNull(handConfirm)) {
			throw Exceptions.OpsException("参数解析失败--handconfirm");
		}
		if (StringUtils.isEmpty(handConfirm.getInvoice())) {
			throw Exceptions.OpsException("发票号不可空。");
		}
		if (CollectionUtils.isEmpty(handConfirm.getHandlist())) {
			throw Exceptions.OpsException("发票清单不可空。");
		}
		String invoiceNo = handConfirm.getInvoice();
		Long invoiceId = System.currentTimeMillis();
		// 相同Do为一个集合 1个DO应为一个PO
		Map<String, List<HandItem>> map = new HashMap<>();
		for (HandItem item : handConfirm.getHandlist()) {
			if (item.getQty() < 1) {
				throw Exceptions.OpsException(item.getDoid() + "。传值数量不可为空 。");
			}
			if (map.containsKey(item.getDoid())) {
				map.get(item.getDoid()).add(item);
			} else {
				List<HandItem> handItemList = new ArrayList<>();
				handItemList.add(item);
				map.put(item.getDoid(), handItemList);
			}
		}
		// 已导入过不 重新导入
		OpsHandoverExample example = new OpsHandoverExample();
		example.createCriteria().andInvoicenoEqualTo(handConfirm.getInvoice());
		example.createCriteria().andDelflagEqualTo(0);
		List<OpsHandover> opsHandoversList = handoverMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(opsHandoversList)) {
			throw Exceptions.OpsException(handConfirm.getInvoice() + "，已导入发票号,不可重复导入。");
		}
		if (CollectionUtils.isEmpty(handConfirm.getHandlist())) {
			throw Exceptions.OpsException(handConfirm.getInvoice() + "，请求调拨出库发票数据参数异常");
		}
		List<CsImportDataDTO> csImportDataDTOS = new ArrayList<>();
		OpsWarehouse opsCsWarehouse = new OpsWarehouse();
		List<String> roIds = new ArrayList<>();
		// todo 生成在途库存 barcode wmordertask 下发富勒 单个DO
		for (Map.Entry<String, List<HandItem>> entry : map.entrySet()) {
			String doid = entry.getKey();
			List<HandItem> handItemList = entry.getValue();
			int qty = 0;
			for (HandItem handItem : handItemList) {
				if (handItem.getQty() < 1) {
					throw Exceptions.OpsException(handConfirm.getInvoice() + "，请求调拨出库发票数据项数量不可小于1,发票号：" + invoiceNo);
				}
				qty += handItem.getQty();
			}
			int moreQty = 0;// 多到部分
			OpsDo opsDo = baseDoService.findDoByDoId(doid);
			if (null == opsDo) {
				throw Exceptions.OpsException(doid + "，无DO数据.发票号：" + invoiceNo);
			}
			OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(doid);
			if (null == opsDoItem) {
				throw Exceptions.OpsException(doid + "，无DOItem数据，发票号：" + invoiceNo);
			}
			// RO只有一行
			List<OpsRo> opsRos = baseRoService.findRoByOrderItemNum(opsDo.getOrderId(),
					Integer.valueOf(opsDo.getOrderItem()), Integer.valueOf(opsDo.getNum()),
					Integer.valueOf(opsDo.getAssNum()));
			if (CollectionUtils.isEmpty(opsRos)) {
				throw Exceptions.OpsException(doid + "，无RO数据。" + opsDo.getOrderId() + "-" + opsDo.getOrderItem() + "-"
						+ String.valueOf(opsDo.getNum()));
			}
			OpsRo opsRo = new OpsRo();
			// 调拨单会提前写入RO，但无发票号且PO号一样，为了区分调拨增加类型DBRK
			if (opsRos.size() > 1) {
				for (OpsRo ro : opsRos) {
					// 调拨，采购入库，调库入库 类型是提前生成RO,ROITEM
					if (RoTypeEnum.DBRK.getType().equals(ro.getRoType())
							|| RoTypeEnum.CGRKGK.getType().equals(ro.getRoType())
							|| RoTypeEnum.TKRK.getType().equals(ro.getRoType())
							|| RoTypeEnum.CGRKBK.getType().equals(ro.getRoType())) {
						if (StringUtils.isEmpty(ro.getInvoiceNo())) {
							// 调拨
							opsRo = ro;
						}
					}
				}
				// 如无对应RO，则生成RO给目的仓收货
				if (null == opsRo || StringUtils.isEmpty(opsRo.getRoId())) {
					String orderNo = opsDo.getOrderId();
					String orderItem = opsDo.getOrderItem();
					Integer num = opsDo.getNum();
					Integer assNum = opsDo.getAssNum();
					// RO规则DBR+订单+子项+拆分+随机（为防止重复）
					String roNewId = String.format("DBR%s%s%s%s%s", orderNo, orderItem, null == num ? 0 : num,
							null == assNum ? 0 : assNum, UUID.randomUUID().toString().split("-")[0]);
					// RO调拨只有一条写入RO-Item
					OpsRo opsNewRo = new OpsRo();
					opsNewRo.setRoId(roNewId);
					// opsNewRo.setInvoiceNo("");//默认无发票号
					opsNewRo.setOrderId(orderNo);
					opsNewRo.setOrderItem(orderItem);
					opsNewRo.setNum(num);
					opsNewRo.setAssNum(assNum);
					opsNewRo.setRoSource(RoSourceEnum.EMPTY.getType());
					opsNewRo.setRoType("DBRK");
					opsNewRo.setWarehouseCode(opsDo.getGatherWarehouseCode());
					opsNewRo.setRoStatus(RoStatusEnum.WAIT.getStatus());
					opsNewRo.setCustomerNo(opsRo.getCustomerNo());
					opsNewRo.setCreTime(new Date());
					opsNewRo.setRemark("调拨回传无RO,新写入");
					opsNewRo.setVersion(0);
					opsRoMapper.insertSelective(opsNewRo);
					// RO调拨只有一条写入RO-Item
					OpsRoItem opsNewRoItem = new OpsRoItem();
					opsNewRoItem.setRoId(roNewId);
					opsNewRoItem.setRoItem(1);
					opsNewRoItem.setModelno(opsDoItem.getModelno());
					opsNewRoItem.setQty(qty);
					opsNewRoItem.setRecQty(0);
					opsNewRoItem.setRemark("调拨回传无RO,新写入");
					opsNewRoItem.setCreator(UserDto.WMS.getUserName());
					opsNewRoItem.setCreTime(new Date());
					opsRoItemMapper.insertSelective(opsNewRoItem);
					List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
					// 新增RO 写入下发表
					// 下发目的仓RO barcode 指令（当前有RO order）
					OpsWmOrderTask opsWmOrderTaskOrder = new OpsWmOrderTask();
					opsWmOrderTaskOrder.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.getType()));
					opsWmOrderTaskOrder.setCreator(UserDto.WMS.getUserName());
					opsWmOrderTaskOrder.setCreTime(new Date());
					opsWmOrderTaskOrder.setWmOrderId(roNewId);
					opsWmOrderTaskOrder.setWmOrderType(WmOrderTaskEnum.RO.getType());
					opsWmOrderTaskOrder.setTaskType(WmOrderTaskEnum.ORDER.getType());
					taskList.add(opsWmOrderTaskOrder);
					// 批量更新task
					wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
					// 如无RO数据，生成RO
					opsRo = opsNewRo;
				}
			} else {
				opsRo = opsRos.get(0);
			}
			if (null == opsRo || StringUtils.isEmpty(opsRo.getRoId())) {
				// 如无RO数据，生成RO
				throw Exceptions.OpsException(doid + "，RO无数据。发票号：" + invoiceNo);
			}
			OpsRoItem opsRoItem = baseRoService.findRoItemByRoId(opsRo.getRoId());
			// 判断调拨数量与ROItem初始的数量比较，如小于初始数量，将拆分ROID
			if (null == opsRoItem) {
				throw Exceptions.OpsException(doid + "，opsRoItem无数据：" + opsRo.getRoId() + ".发票号：" + invoiceNo);
			}
			// 拆分规则：订单三个字段固定，只更变ROID,初始给的QTY与实际调拨的不符时
			// 原来的RO一拆2，原RO更新新发票号使用当前发票。新生成ROID，下次调拨使用（数据5,来4）
			if (opsRoItem.getQty() > qty) {
				// 写入RO+orderno+item+num+uuid(8字符)
				String roNewId = String.format("DBR%s%s%s%s%s", opsRo.getOrderId(), opsRo.getOrderItem(),
						null == opsRo.getNum() ? 0 : opsRo.getNum(), null == opsRo.getAssNum() ? 0 : opsRo.getAssNum(),
						UUID.randomUUID().toString().split("-")[0]);
				// RO调拨只有一条写入RO-Item
				OpsRo opsNewRo = new OpsRo();
				opsNewRo.setRoId(roNewId);
				// opsNewRo.setInvoiceNo("");//默认不给发票号
				opsNewRo.setOrderId(opsRo.getOrderId());
				opsNewRo.setOrderItem(String.valueOf(opsRo.getOrderItem()));
				opsNewRo.setNum(opsRo.getNum());
				opsNewRo.setAssNum(opsRo.getAssNum());
				opsNewRo.setRoSource(RoSourceEnum.EMPTY.getType());
				opsNewRo.setRoType(opsRo.getRoType());
				opsNewRo.setWarehouseCode(opsRo.getWarehouseCode());
				opsNewRo.setRoStatus(RoStatusEnum.WAIT.getStatus());
				opsNewRo.setCustomerNo(opsRo.getCustomerNo());
				opsNewRo.setCreTime(new Date());
				opsNewRo.setRemark("调拨回传数量拆分");
				opsRoMapper.insertSelective(opsNewRo);
				// RO调拨只有一条写入RO-Item
				OpsRoItem opsNewRoItem = new OpsRoItem();
				opsNewRoItem.setRoId(roNewId);
				opsNewRoItem.setModelno(opsRoItem.getModelno());
				opsNewRoItem.setQty(opsRoItem.getQty() - qty);
				opsNewRoItem.setRecQty(0);
				opsNewRoItem.setRoItem(1);
				opsNewRoItem.setRemark("调拨回传数量拆分。" + String.valueOf(opsRoItem.getQty()) + "改："
						+ String.valueOf(opsRoItem.getQty() - qty));
				opsNewRoItem.setCreator(UserDto.WMS.getUserName());
				opsNewRoItem.setCreTime(new Date());
				opsRoItemMapper.insertSelective(opsNewRoItem);
				// 更新当前ROID的QTY
				OpsRoItemExample roItemExample = new OpsRoItemExample();
				roItemExample.createCriteria().andIdEqualTo(opsRoItem.getId()).andVersionEqualTo(opsRoItem.getVersion())
						.andDelflagEqualTo(0);
				OpsRoItem updateOpsRoItem = new OpsRoItem();
				updateOpsRoItem.setQty(qty);// 当前发出的数量
				updateOpsRoItem.setVersion(opsRoItem.getVersion() + 1);
				updateOpsRoItem.setModifyTime(new Date());
				updateOpsRoItem.setModifier(UserDto.WMS.getUserName());
				opsRoItemMapper.updateByExampleSelective(updateOpsRoItem, roItemExample);

				List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
				// 新增RO 写入下发表
				// 下发目的仓RO barcode 指令（当前有RO order）
				OpsWmOrderTask opsWmOrderTaskOrder = new OpsWmOrderTask();
				opsWmOrderTaskOrder.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.getType()));
				opsWmOrderTaskOrder.setCreator(UserDto.WMS.getUserName());
				opsWmOrderTaskOrder.setCreTime(new Date());
				opsWmOrderTaskOrder.setWmOrderId(roNewId);
				opsWmOrderTaskOrder.setWmOrderType(WmOrderTaskEnum.RO.getType());
				opsWmOrderTaskOrder.setTaskType(WmOrderTaskEnum.ORDER.getType());
				taskList.add(opsWmOrderTaskOrder);
				// 批量更新task
				wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
			} else if (opsRoItem.getQty() < qty) {// 调拨5，实际调拨6，把原来那行调拨数量更新6
				OpsRoItemExample roItemExample = new OpsRoItemExample();
				roItemExample.createCriteria().andIdEqualTo(opsRoItem.getId()).andVersionEqualTo(opsRoItem.getVersion())
						.andDelflagEqualTo(0);
				OpsRoItem updateOpsRoItem = new OpsRoItem();
				updateOpsRoItem.setQty(qty);// 当前发出的数量
				updateOpsRoItem.setVersion(opsRoItem.getVersion() + 1);
				updateOpsRoItem.setModifyTime(new Date());
				updateOpsRoItem.setModifier(UserDto.WMS.getUserName());
				updateOpsRoItem.setRemark("实际调拨数大于原调拨数");
				opsRoItemMapper.updateByExampleSelective(updateOpsRoItem, roItemExample);
				// 多到数量(判断预约数区分)
				moreQty = qty - opsRoItem.getQty();
			}

			// 通用库存生成在途库存
			long inventoryId = 0L;
			long inventoryPropertyId = 1L;
			int prepareQuantity = 0;
			boolean isWT = false;
			if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())) {// 主动调库需要查找制定库存属性
				TransOrder transOrder = opsTransferOrderEventService.findTransOrder(opsDo.getOrderId(),
						Integer.valueOf(opsDo.getOrderItem()));
				OpsInventoryProperty property = new OpsInventoryProperty();
				property.setInventoryTypeCode(transOrder.getToInventoryTypeCode());
				property.setCustomerNo(transOrder.getToCustomerNo());
				property.setPpl(transOrder.getToPpl());
				property.setProjectCode(transOrder.getToProjectCode());
				property.setGroupCustomerNo(transOrder.getToGroupCustomerNo());
				property.setSalesInfoNo(transOrder.getToSalesInfoNo());
				inventoryPropertyId = opsInventoryPropertyService.findPropertyWithInsertByExample(property,
						new UserDto("handconfirm", null));
				// 调拨库房不一致查库房表
				if (transOrder.getFromWarehouseCode() != transOrder.getToWarehouseCode()) {
					// 目的仓看是不是委托在库
					opsCsWarehouse = opsWarehouseService.findById(transOrder.getToWarehouseCode());
					// 委托在库标记
					if (WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsCsWarehouse.getWarehouseType())) {
						isWT = true;
					}
				}
				// 主动调库更新发票号（运单号）,发运日期，发运数量
				opsTransferOrderEventService.updateTransOrderShipById(transOrder.getId(), invoiceNo, invoiceId,
						transOrder.getShipQty() + qty, new Date());
			} else if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())
					|| DoTypeEnum.CGDBCK.getType().equals(opsDo.getDoType())) {
				// begin bug:9685
				// 库存批次属性ID获取，DO关联库存如有N采用N行批次ID，如因时间差导致入库后调拨出库前，取在途ID批次库存ID
				// b28029 2023-2-16
				// 取库存批次属性ID，关联关系表在库ID
				List<OpsDoItemInventory> opsDoItemInventories = baseDoService.getDoItemInventoryByDoId(opsDo.getDoId(),
						InventoryTableTypeEnum.NORMAL);
				if (CollectionUtils.isEmpty(opsDoItemInventories)) {
					// 不存在关联关系在库行(N行)，取T行 如T行也没，报错不处理
					opsDoItemInventories = baseDoService.getDoItemInventoryByDoId(opsDo.getDoId(),
							InventoryTableTypeEnum.TRANS);
					// 无opsDoItemInventories数据
					if (CollectionUtils.isEmpty(opsDoItemInventories)) {
						throw Exceptions.OpsException(doid + "，无opsDoItemInventories数据");
					}
					// 在途ID
					OpsInventoryMove opsInventoryMove = baseInventoryService
							.getInventoryMoveById(opsDoItemInventories.get(0).getInventoryId());
					if (Objects.isNull(opsInventoryMove)) {
						throw Exceptions.OpsException(doid + "，opsDoItemInventories,无在途表ID数据："
								+ opsDoItemInventories.get(0).getInventoryId());
					}
					// 取在途的批属性ID
					inventoryPropertyId = opsInventoryMove.getInventoryPropertyId();
				} else {
					// 存在关联关系在库行(N行)
					OpsDoItemInventory opsDoItemInventory = opsDoItemInventories.get(0);
					// 在库ID
					OpsInventory opsInventory = baseInventoryService
							.getInventoryById(opsDoItemInventory.getInventoryId());
					if (Objects.isNull(opsInventory)) {
						throw Exceptions.OpsException(doid + "，opsDoItemInventories,无在库表ID数据："
								+ opsDoItemInventories.get(0).getInventoryId());
					}
					// 取原始出库的批属性ID
					inventoryPropertyId = opsInventory.getInventoryPropertyId();
				}
				// end bug:9685
				// 调拨库房不一致查库房表
				if (opsDo.getWarehouseCode() != opsDo.getGatherWarehouseCode()) {
					// 目的仓看是不是委托在库
					opsCsWarehouse = opsWarehouseService.findById(opsDo.getGatherWarehouseCode());
					// 委托在库标记
					if (WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsCsWarehouse.getWarehouseType())) {
						isWT = true;
					}
				}
			} else if (DoTypeEnum.ZDQX.getType().equals(opsDo.getDoType())) {// 转定出库（临时使用）
				// 转定业务（转定又取消）：DBCK改ZDCK方便区分不建立关联关系，可以给目的仓入库存
				// 此类问题是手工取消，目的仓转定发出仓继续发出
				List<OpsDoItemInventory> opsDoItemInventories = baseDoService.getDoItemInventoryByDoId(opsDo.getDoId());
				if (!CollectionUtils.isEmpty(opsDoItemInventories)) {
					// 如有关联关系DO打上删除标记
					for (OpsDoItemInventory opsDoItemInventory : opsDoItemInventories) {
						// 删除在库库存关系与在途关系
						opsDoService.deleteDoItemInventoryByInventoryId(opsDoItemInventory.getInventoryId(),
								opsDoItemInventory.getInventoryTableType());
					}
				}
				// wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId();
			} else {
				throw Exceptions.OpsException(doid + "，类型错误.发票号：" + invoiceNo);
			}

			// 调拨出库的要预占，其他不预占数（20220720）
			if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
				prepareQuantity = qty;
				// if (moreQty > 0) {
				// //有多到的还原回去(如原来5个，现在多到1个=6个 5个预占，1个多到)
				// prepareQuantity = qty - moreQty;
				// }
			}
			// do调拨出库类型组装选择,do第四个字段，不是组装选第三字段
			int num = DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) ? opsDo.getAssNum()
					: opsDo.getNum();
			OpsInventoryMove inventory = new OpsInventoryMove();
			inventory.setInvoiceno(invoiceNo);
			inventory.setInventoryPropertyId(inventoryPropertyId);
			inventory.setWarehouseCode(opsDo.getGatherWarehouseCode());
			inventory.setSignWarehouseCode(opsDo.getGatherWarehouseCode());
			inventory.setInventoryStatus(InventoryStatusEnum.DBTRANS.getCode());// 调拨在途
			inventory.setQuantity(qty);// 存在分纳出库
			inventory.setQaStatus(QAStatusEnum.NORMAL.getType());
			// 才会产生占用库存
			inventory.setPrepareQuantity(prepareQuantity);
			inventory.setModelno(opsDoItem.getModelno());
			inventory.setOrderno(opsDo.getOrderId());
			inventory.setItemno(Integer.valueOf(opsDo.getOrderItem()));
			inventory.setSplititemno(num);
			inventory.setAssociateNo(opsDo.getOrderId());
			inventory.setAssociateNoItem(Integer.valueOf(opsDo.getOrderItem()));
			inventory.setAssociateNoSplitno(num);
			inventory.setPrice(opsDoItem.getPrice());
			inventory.setSourceType(SourceTypeEnum.DB.getType());
			inventory.setPoQty(opsDoItem.getOutQty());
			inventory.setOptStatus(OptStatusEnum.PURCHASE_ACCEPT.getCode());
			inventory.setGreencode(opsDoItem.getGreenCode());// 环保标志 2023-02-08
			inventory.setPoQty(qty);
			// imDateTheory
			inventoryId = baseInventoryService.createInvMoveReturnId(inventory, UserDto.WMS);

			// 更新RO发票号
			// OpsRoExample roExample = new OpsRoExample();
			// roExample.createCriteria().andIdEqualTo(opsRo.getId()).andVersionEqualTo(opsRo.getVersion())
			// .andDelflagEqualTo(0);
			OpsRoExample roExample = new OpsRoExample();
			roExample.createCriteria().andRoIdEqualTo(opsRo.getRoId()).andVersionEqualTo(opsRo.getVersion())
					.andDelflagEqualTo(0);
			OpsRo updateOpsRo = new OpsRo();
			updateOpsRo.setInvoiceNo(handConfirm.getInvoice());
			updateOpsRo.setVersion(opsRo.getVersion() + 1);
			updateOpsRo.setModifyTime(new Date());
			updateOpsRo.setModifier(UserDto.WMS.getUserName());
			if (1 != opsRoMapper.updateByExampleSelective(updateOpsRo, roExample)) {
				throw Exceptions.OpsException(opsRo.getRoId() + "，更新RO行发票更新错误");
			}
			// 写入ops_ro_item_inventory
			OpsRoItemInventory opsRoItemInventory = new OpsRoItemInventory();
			opsRoItemInventory.setRecQty(0);
			opsRoItemInventory.setInventoryId(inventoryId);
			opsRoItemInventory.setQty(qty);
			opsRoItemInventory.setRoId(opsRo.getRoId());
			opsRoItemInventory.setRoItem(opsRoItem.getRoItem());
			opsRoItemInventory.setDelflag(0);
			opsRoItemInventory.setVersion(0L);
			opsRoItemInventory.setCreator(UserDto.WMS.getUserName());
			opsRoItemInventory.setCreTime(new Date());
			opsRoItemInventoryMapper.insertSelective(opsRoItemInventory);
			if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {

				// do是调拨出库单按13位订单号去找交易出库单
				List<OpsDo> jyckList = baseDoService.findDoListByOrder(opsDo.getOrderId(), opsDo.getOrderItem(),
						opsDo.getNum(), DoTypeEnum.JYCK);
				if (!CollectionUtils.isEmpty(jyckList)) {
					// 调拨拆分型号且对应的交易出库单是拆分数量，取调拨拆分型号序号
					if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
						// 随机1行，判断是否为数量拆分，是的话按10位订单号+拆分型号序号找到对应DOID
						OpsDo opsDoJyck = jyckList.get(0);
						if (DoSourceEnum.ASSQTY.getType().equals(opsDoJyck.getDoSource())) {
							// 交易出库单
							jyckList = baseDoService.findDoListByOrder(opsDo.getOrderId(), opsDo.getOrderItem(),
									opsDo.getAssNum(), DoTypeEnum.JYCK);
						}
					}
					// 有且仅有1行
					OpsDo opsDoJyck = jyckList.get(0);
					if (null == opsDoJyck) {
						throw Exceptions.OpsException("对应DOID无数据。doid" + opsDo.getDoId());
					}
					// 调拨出库单可能会被取消，取消(不追加关联教育出库单)
					// 型号拆分
					if (DoSourceEnum.ASSModelNo.getType().equals(opsDoJyck.getDoSource())) {
						OpsPco opsPco = opsPcoService.findPcoByOrder(opsDoJyck.getOrderId(), opsDoJyck.getOrderItem());
						if (null == opsPco) {
							throw Exceptions.OpsException("对应PCOID无数据。order" + opsDoJyck.getOrderId());
						}
						// 调拨Doid的AssNum对应pcoitem的pcoid
						OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(opsPco.getPcoId(),
								opsDo.getAssNum());
						if (null == opsPcoItem) {
							throw Exceptions.OpsException("对应PCOItem无数据。pcoId" + opsPco.getPcoId());
						}
						OpsPcoItemInventory pcoItemInventory = new OpsPcoItemInventory();
						pcoItemInventory.setInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
						pcoItemInventory.setSortnum(1);
						pcoItemInventory.setInventoryId(inventoryId);
						pcoItemInventory.setPcoId(opsPco.getPcoId());
						pcoItemInventory.setPcoItem(opsDo.getAssNum());// 调拨单对应的assnum
						pcoItemInventory.setUseQty(qty);
						pcoItemInventory.setDelflag(0);
						pcoItemInventory.setVersion(0L);
						pcoItemInventory.setCreator(UserDto.WMS.getUserName());
						pcoItemInventory.setCreTime(new Date());
						pcoItemInventory.setPcoType(opsPco.getPcoType());
						opsPcoItemInventoryMapper.insertSelective(pcoItemInventory);
					} else {
						// 写入ops_do_item_inventory
						OpsDoItemInventory doItemInventory = new OpsDoItemInventory();
						doItemInventory.setInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
						doItemInventory.setSortnum(1);
						doItemInventory.setInventoryId(inventoryId);
						doItemInventory.setDoId(opsDoJyck.getDoId());
						doItemInventory.setDoItem(Integer.valueOf(opsDoItem.getDoItem()));
						doItemInventory.setUseQty(qty);
						doItemInventory.setDelflag(0);
						doItemInventory.setVersion(0L);
						doItemInventory.setCreator(UserDto.WMS.getUserName());
						doItemInventory.setCreTime(new Date());
						opsDoItemInventoryMapper.insertSelective(doItemInventory);
					}
				}
			}
			// 写入ROID 为下发ROID
			if (!roIds.contains(opsRo.getRoId())) {
				roIds.add(opsRo.getRoId());
			}
			for (HandItem handItem : handItemList) {
				// 委托在库房
				if (isWT) {
					CsImportDataDTO csImportDataDTO = new CsImportDataDTO();
					csImportDataDTO.setAgentNo(opsCsWarehouse.getCustomerNo());
					csImportDataDTO.setBarcode(handItem.getBarcode());
					csImportDataDTO.setCaseNo("");
					csImportDataDTO.setCreateUser(UserDto.WMS.getUserName());
					csImportDataDTO.setImpType(1);
					csImportDataDTO.setOrderNo(opsDo.getOrderId() + "-" + String.valueOf(opsDo.getOrderItem()));
					csImportDataDTO.setStatus(1);
					csImportDataDTO.setInvoiceNo(invoiceNo);
					csImportDataDTO.setModelNo(opsDoItem.getModelno());
					csImportDataDTO.setWarehouseCode(opsCsWarehouse.getWarehouseCode());
					csImportDataDTO.setQuantity(handItem.getQty());
					csImportDataDTO.setImpDate(new Date());
					csImportDataDTO.setDeliveryNo(invoiceNo);
					csImportDataDTO.setShipDate(new Date());
					csImportDataDTO.setRoId(opsRo.getRoId());
					csImportDataDTOS.add(csImportDataDTO);
				}
				// 写入ops_ro_barcode
				OpsRoBarcode opsRoBarcode = new OpsRoBarcode();
				opsRoBarcode.setInvoiceno(handConfirm.getInvoice());
				opsRoBarcode.setRoId(opsRo.getRoId());
				opsRoBarcode.setWarehouseCode(opsDo.getGatherWarehouseCode());// 目的仓
				opsRoBarcode.setBarcode(handItem.getBarcode());
				opsRoBarcode.setPackageCode(handItem.getCaseno());
				opsRoBarcode.setModelno(handItem.getModelno());
				opsRoBarcode.setQty(handItem.getQty());
				opsRoBarcode.setDelflag(0);
				opsRoBarcode.setCreTime(new Date());
				opsRoBarcode.setCreator(UserDto.WMS.getUserName());
				opsRoBarcode.setOrderno(opsRo.getOrderId());
				opsRoBarcode.setItemno(Integer.valueOf(opsRo.getOrderItem()));
				// opsRoBarcode.setSplititemno(null == opsRo.getNum() ? 0 :
				// Integer.valueOf(opsRo.getNum()));
				opsRoBarcode.setNum(opsRo.getNum());
				opsRoBarcode.setAssNum(opsRo.getAssNum());
				opsRoBarcode.setRoItem(opsRoItem.getRoItem());
				opsRoBarcode.setScan(1);
				opsRoBarcodeMapper.insertSelective(opsRoBarcode);
			}
		}
		// 写入委托在库收货表ImpData
		if (csImportDataDTOS.size() > 0) {
			ResultVo<String> resultVo = consignmentStockFeignApi.addImpData(csImportDataDTOS);
			if (!resultVo.isSuccess()) {
				throw Exceptions
						.OpsException("调用写入委托在库表错误consignmentStockFeignApi.addImpData：" + resultVo.getMessage());
			}
		}
		// 调拨没有task barcode
		// update wmtask ro-order=0 (默认=3)
		// insert wmtask ro-barcode=3
		List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
		for (String roId : roIds) {
			// 更新order_task order状态
			OpsWmOrderTaskExample exa = new OpsWmOrderTaskExample();
			OpsWmOrderTaskExample.Criteria cri = exa.createCriteria();
			cri.andFlagBetween(3, 4);// 将挂起状态设置为 0初始状态
			cri.andWmOrderTypeEqualTo(WmOrderTaskEnum.RO.getType());
			cri.andTaskTypeEqualTo(WmOrderTaskEnum.ORDER.getType());
			cri.andWmOrderIdEqualTo(roId);
			cri.andDelflagEqualTo(0);
			OpsWmOrderTask record = new OpsWmOrderTask();
			record.setFlag(0);
			record.setModifyTime(new Date());
			record.setModifier("调拨");
			opsWmOrderTaskMapper.updateByExampleSelective(record, exa);
			// 下发目的仓RO barcode 指令（当前有RO order）
			OpsWmOrderTask opsWmOrderTaskBarcode = new OpsWmOrderTask();
			opsWmOrderTaskBarcode.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.getType()));
			opsWmOrderTaskBarcode.setCreator(UserDto.WMS.getUserName());
			opsWmOrderTaskBarcode.setCreTime(new Date());
			opsWmOrderTaskBarcode.setWmOrderId(roId);// todo roid
			opsWmOrderTaskBarcode.setWmOrderType(WmOrderTaskEnum.RO.getType());
			opsWmOrderTaskBarcode.setTaskType(WmOrderTaskEnum.BARCODE.getType());
			taskList.add(opsWmOrderTaskBarcode);
			// wmOrderTaskService.addOpsWmOrderTask(opsWmOrderTaskBarcode);
		}
		// 批量更新task
		wmOrderTaskService.addBatchOpsWmOrderTask(taskList);

		List<OpsHandover> opsHandovers = new ArrayList<>();
		for (HandItem item : handConfirm.getHandlist()) {
			if (item.getQty() < 1) {
				throw Exceptions.OpsException("数量不可空。");
			}
			OpsHandover opsHandover = new OpsHandover();
			opsHandover.setInvoiceno(handConfirm.getInvoice());
			opsHandover.setHandDate(handConfirm.getHangdate());
			opsHandover.setDoId(item.getDoid());
			opsHandover.setModelno(item.getModelno());
			opsHandover.setQty(item.getQty());
			opsHandover.setCaseno(item.getCaseno());
			opsHandover.setBarcode(item.getBarcode());
			opsHandover.setCreTime(new Date());
			opsHandover.setCreator(UserDto.WMS.getUserName());
			opsHandover.setModifyTime(new Date());
			opsHandover.setModifier(UserDto.WMS.getUserName());
			opsHandover.setDelflag(0);
			opsHandover.setFromwarehousecode(handConfirm.getFromWarehouseCode());
			opsHandover.setTowarehousecode(handConfirm.getToWarehouseCode());
			opsHandover.setLogisticscode(handConfirm.getLogisticsCode());
			opsHandover.setExpresscode(handConfirm.getExpressCode());
			opsHandovers.add(opsHandover);
		}
		if (CollectionUtils.isEmpty(opsHandovers)) {
			throw Exceptions.OpsException("发票清单不可空。");
		}
		// 写入物流发票交接信息
		opsHandOverService.addHandOver(opsHandovers);
	}
	*/

	/**
	 * @description 采购单更新信息
	 * @author C12961
	 * @date 2022/6/27 12:38
	 */
	@Override
	public void updatePurchaseOrder(PurchaseUpdateInfo info) throws OpsException {
		if (info.getSplitno() == null) {
			info.setSplitno(0);
		}
		List<OpsInventoryMove> opsInventoryMoves = baseInventoryService.findInventoryMoveByPo(info.getOrderno(),
				info.getItemno(), info.getSplitno());
		for (OpsInventoryMove move : opsInventoryMoves) {
			if (StringUtils.isNotBlank(info.getSupplierid())) {
				OpsInventoryMove update = new OpsInventoryMove();
				update.setSupplierid(info.getSupplierid());
				update.setInventoryId(move.getInventoryId());
				update.setModifyTime(new Date());
				update.setModifier("采购单更新供应商");
				opsInventoryMoveMapper.updateByPrimaryKeySelective(update);
			}
		}
	}

	/* ====================================== */

	/**
	 * @description：调账单 optType:"+"调整+库存,"-'调账-库存
	 * @author ：c02483
	 * @date ：Created in 2021/10/15 13:33
	 */
	@Override
	public void InventoryForAdjust(InventoryForAdjustInputDto dto) throws OpsException {
		if (dto.getOptType().equals("-")) {
			log.info("====开始调账减操作=====");
			opsDoService.createDoForAdjust(dto.getDtoList(), dto.getUserDto());
		} else if (dto.getOptType().equals("+")) {
			log.info("====开始调账增操作=====");
			opsRoService.createRoForAdjust(dto.getDtoList(), dto.getUserDto());

		}
	}

	/**
	 * @description：WMS调账单 optType:"+"调整+库存,"-'调账-库存
	 * @author ：c02483
	 * @date ：Created in 2021/10/15 13:33
	 */
	@Override
	public void InventoryForWMSAdjust(InventoryForAdjustInputDto dto) throws OpsException {
		if (dto.getOptType().equals("-")) {
			log.info("====开始调账减操作=====");
			opsDoService.createDoForWMSAdjust(dto.getDtoList(), dto.getUserDto());
		} else if (dto.getOptType().equals("+")) {
			log.info("====开始调账增操作=====");
			opsRoService.createRoForWMSAdjust(dto.getDtoList(), dto.getUserDto());

		}
	}

	/**
	 * @description：调库单
	 * @author ：c02483
	 * @date ：Created in 2021/10/15 13:32
	 */
	@Override
	public void inventoryForTrans(InventoryForTransInputDto inputDto) throws OpsException {
		opsDoService.createDoForTrans(inputDto);
		opsRoService.createRoForTrans(inputDto);
	}

	/**
	 * @description：调库单-预约在途
	 * @author ：c12961
	 * @date ：Created in 2021/10/15 13:32
	 */
	@Override
	public void inventoryMoveForTransOrder(TransOrderDtoForMove dto, OpsInventoryMove fromMove) throws OpsException {
		opsDoService.createDoForTransMove(dto, fromMove);
		opsRoService.createRoForTransMove(dto, fromMove);
	}

	/**
	 * 创建组换单生成do指令
	 *
	 * @param dto
	 * @param onlyWms
	 *            仅调wms标识
	 * @throws OpsException
	 */
	@Override
	public void InventoryForProducChange(InventoryForProducChangeDto dto, Boolean onlyWms) throws OpsException {
		opsDoService.createDoForProduceChange(dto.getCkList(), dto.getWarehouseCode(), dto.getUserDto(), onlyWms);
	}

	/**
	 * bugid:12889 c14717 20240115 1.wms发运单页面点击"组换无法取消按钮" 2.ops验证状态是否可以取消
	 * 3.取消调用wms取消do指令 4.wms取消成功后，ops取消相应指令 5.调用变更状态接口
	 */
	@Override
	public void wmsProducChangeStatus(String orderId, String msg, long cancelId) throws OpsException {
		// 1.查询指令
		List<OpsDo> opsDoList = baseDoService.findDoListByOrder(orderId, null, null, null);
		if (CollectionUtils.isEmpty(opsDoList)) {
			throw Exceptions.OpsException("组换单不存在");
		}
		// 2.验证状态
		for (OpsDo opsDo : opsDoList) {
			if (DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())
					|| DoStatusEnum.PART.getStatus().equals(opsDo.getDoStatus())) {
				throw Exceptions.OpsException("货物已发运，不可取消");
			}
		}
		if (DoTypeEnum.ZHCK.getType().equals(opsDoList.get(0).getDoType())
				|| DoTypeEnum.ZHCKOW.getType().equals(opsDoList.get(0).getDoType())) {
			// 组装报文
			List<CancelDocOrderDto> dbList = new ArrayList<>();
			if (StringUtils.isBlank(msg)) {
				msg = "组换无法操作";
			}
			for (OpsDo opsDo : opsDoList) {
				CancelDocOrderDto param = new CancelDocOrderDto(opsDo.getId(), opsDo.getWarehouseCode(),
						opsDo.getDoId(), DoTypeEnum.getType(opsDo.getDoType()).getWltype(), msg);
				dbList.add(param);
			}
			if (CollectionUtil.isNotEmpty(dbList)) {
				// 删除wms指令
				CommonResult<List<JSONObject>> wmsResult = opsCallWmsFeignApi.cancelDocOrder(dbList);// 查看是否可删除
				if (wmsResult.isSuccess()) {
					for (OpsDo opsDo : opsDoList) {
						// 删除do指令
						opsDoService.insertOrderCancelItem(cancelId, opsDo.getDoId(), opsDo.getDoType(), msg,
								UserDto.WMS);
						opsDoService.delDoByDoId(opsDo.getId(), opsDo.getDoId(), opsDo.getOrderId(),
								opsDo.getOrderItem(), UserDto.WMS);
						wmOrderTaskService.delWmOrderTask(opsDo.getDoId(), msg);
					}
					// 如果ro存在删除ro指令
					List<OpsRo> opsRoList = baseRoService.findRoByOrderNo(orderId);
					if (CollectionUtils.isNotEmpty(opsRoList)) {
						for (OpsRo opsRo : opsRoList) {
							// wms操作先下架，在上架
							opsRoService.delOpsRoOrder(cancelId, opsRo, "取消组换", UserDto.WMS);
						}
					}
					// 调用广州返回状态
					stockAssemblyFeignApi.updateAssemblyStatus(orderId, false);
				} else {
					throw Exceptions.OpsException("调用wms取消失败");
				}
			}
		} else {
			throw Exceptions.OpsException("组换单不存在");
		}
	}

	/**
	 * @description：情报预约
	 * @author ：c02483
	 * @date ：Created in 2021/12/30 10:16
	 */
	public void InventoryForSalesInfoNo(InventoryForAdjustDto dto) throws OpsException {
		OpsDoDto doDto = opsDoService.CreateDoAndFinishForSalesInfo(dto, OrderTypeEnum.PRE, DoSourceEnum.SALESINFO,
				DoTypeEnum.QBCK);
		opsRoService.createRoAndFinishForSalesInfo(dto, doDto);
	}

	/**
	 * @description：情报预约自动取消
	 * @author ：c02483
	 * @date ：Created in 2021/12/30 10:16
	 */
	@Override
	public Integer undoInventoryForSalesInfo(InventoryForAdjustDto adjustDto) throws OpsException {
		// 情报号
		String salesInfoNo = adjustDto.getSalesInfoNo();
		// 查询出批属性
		Long propertyId = opsInventoryPropertyService.findPropertyIdBySalesInfoNo(salesInfoNo);
		if (propertyId == null) {
			return SalesInfoErrorCodeEnum.NOT_FOUND_PROPERTY.getCode();
		}
		log.info("查询出批属性id:" + propertyId);
		// 根据批属性查询库存
		OpsInventoryExample inventoryExample = new OpsInventoryExample();
		inventoryExample.createCriteria().andDelflagEqualTo(0).andInventoryPropertyIdEqualTo(propertyId);
		List<OpsInventory> inventoryForSalesInfoList = opsInventoryMapper.selectByExample(inventoryExample);
		if (inventoryForSalesInfoList.isEmpty()) {
			log.info("无库存占用");
			log.info("删除批属性：" + propertyId);
			opsInventoryPropertyService.deletePropertyById(propertyId);
			return SalesInfoErrorCodeEnum.NOT_FOUND_INVENTORY.getCode();
		}
		// 有客单预占情报库存
		for (OpsInventory inv : inventoryForSalesInfoList) {
			log.info("分析库存数量：" + JSONUtil.toJsonStr(inv));
			int available = inv.getQuantity() - inv.getPrepareQuantity();
			log.info("可释放的数量为:" + available);
			if (available > 0) {
				handleUndo(adjustDto, inv, available);
				// 重新查询库存数量，如果库存数量为0，则删掉库存
				inv = baseInventoryService.getInventoryById(inv.getInventoryId());
			}
			if (inv.getQuantity() == 0) {
				log.info("库存数量为0，直接删除库存");
				baseInventoryService.deleteInventory(inv.getInventoryId(), inv.getVersion(), UserDto.ADMIN);
			}
		}
		inventoryForSalesInfoList = opsInventoryMapper.selectByExample(inventoryExample);
		if (inventoryForSalesInfoList.isEmpty()) {
			log.info("删除批属性：" + propertyId);
			opsInventoryPropertyService.deletePropertyById(propertyId);
		}
		return SalesInfoErrorCodeEnum.SUCCESS_CANCEL.getCode();
	}

	// 处理释放情报库存，返回情报库存处理后的数量
	private void handleUndo(InventoryForAdjustDto adjustDto, OpsInventory inv, int available) throws OpsException {
		log.info("开始释放库存:" + JSONUtil.toJsonStr(inv));
		log.info("开始组装对象");
		InventoryForAdjustDto newAdjustDto = BeanUtil.copyProperties(adjustDto, InventoryForAdjustDto.class);
		newAdjustDto.setInventoryId(inv.getInventoryId());
		newAdjustDto.setModelno(inv.getModelno());
		newAdjustDto.setQty(available);
		newAdjustDto.setWarehouseCode(inv.getWarehouseCode());
		Integer orderItem = 1;
		List<OpsDo> opsDos = baseDoService.findDoListByOrder(newAdjustDto.getOrderId(), null, null, DoTypeEnum.QBQX);
		// 获取最大的orderItem+1
		if (!opsDos.isEmpty()) {
			orderItem = opsDos.stream().map(item -> Integer.valueOf(item.getOrderItem()))
					.max(Comparator.comparing(Integer::intValue)).get() + 1;
		}
		log.info("新的项号为：" + orderItem);
		newAdjustDto.setOrderItem(orderItem);
		log.info("开始生成物流指令");
		OpsDoDto opsDoDto = opsDoService.createDoForCancelSalesInfo(newAdjustDto);
		OpsRoDto opsRoDto = opsRoService.createRoForCancelSalesInfo(newAdjustDto, opsDoDto);
		log.info("出库指令：" + JSONUtil.toJsonPrettyStr(opsDoDto));
		log.info("入库指令：" + JSONUtil.toJsonPrettyStr(opsRoDto));
		// 调整库存数量
		log.info("开始调整库存数量");
		Long fromId = opsDoDto.getDoItemInventoryList().get(0).getInventoryId();
		Long toId = opsRoDto.getRoItemInventoryList().get(0).getInventoryId();
		OpsInventory fromInv = baseInventoryService.getInventoryById(fromId);
		OpsInventory toInv = baseInventoryService.getInventoryById(toId);
		// log.info("调整前：fromInv:\n" + JSONUtil.toJsonStr(fromInv) + "\n" +
		// JSONUtil.toJsonStr(toInv));
		opsDoService.updateQtyForDo(opsDoDto.getOpsDo(), opsDoDto.getDoItemInventoryList(), UserDto.ADMIN);
		opsRoService.updateQtyForRo(opsRoDto.getOpsRo(), opsRoDto.getRoItemInventoryList().get(0), UserDto.ADMIN);
		log.info("调整库存数量完成");
		fromInv = baseInventoryService.getInventoryById(fromId);
		toInv = baseInventoryService.getInventoryById(toId);
		// log.info("调整后：fromInv:\n" + JSONUtil.toJsonStr(fromInv) + "\n" +
		// JSONUtil.toJsonStr(toInv));
	}

	/**
	 * 订单还原 订单取消 交易出库和调拨出库都需要取消
	 */
	@Override
	public Boolean orderChangeToInitDelOrder(CancelForOrderDto inputDto) throws OpsException {
		if (Objects.isNull(inputDto.getUserDto())) {
			throw Exceptions.OpsException("请传删单用户");
		}
		List<CancelDocOrderDto> cancelWmsJYCKOrder = new ArrayList<>();// 交易出库wms报文
		// 包括do和pco
		List<CancelDocOrderDto> cancelWmsDBCKOrder = new ArrayList<>();// 调拨出库报文
		List<CancelDocOrderDto> cancelWmsOrder = new ArrayList<>();// 交易出库wms报文
		// 包括do交易出库单和调拨单和pco
		// 插入 取消操作记录到order_cancel表
		Long cancelId = null;
		// 查询 出库指令 构造删除数据
		List<OpsDo> doList = baseDoService.findDoListByOrder(inputDto.getOrderId(), inputDto.getOrderItem(), null,
				null);
		if (!CollectionUtils.isEmpty(doList)) {
			for (OpsDo opsDo : doList) {
				// bugid: 12058 c14717 20230908 1订单还原 物流已经部分发运 包含调拨单
				if (DoStatusEnum.PART.getStatus().equals(opsDo.getDoStatus())) {
					return false;
				}
				// 物流已经发运完成
				if (DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())) {
					return false;
				}
				// bugid 9267 c14717 2023/01/06 ps:每次取消都需要问wms，wms如果无记录返回成功
				getCancelOrder(opsDo, inputDto, cancelWmsJYCKOrder, cancelWmsDBCKOrder, null, null);
			}
			if (CollectionUtil.isNotEmpty(cancelWmsJYCKOrder)) {
				cancelWmsOrder.addAll(cancelWmsJYCKOrder);
			}
			if (CollectionUtil.isNotEmpty(cancelWmsDBCKOrder)) {
				cancelWmsOrder.addAll(cancelWmsDBCKOrder);
			}
			if (CollectionUtil.isNotEmpty(cancelWmsOrder)) {
				CommonResult wmsResult = opsCallWmsFeignApi.cancelDocOrder(cancelWmsOrder);// 查看是否可删除
				if (wmsResult.isSuccess()) {
					cancelId = opsDoService.insertOrderCancel(inputDto);
					// 取消调拨单
					delOpsOrder(cancelId, inputDto, null, cancelWmsDBCKOrder);
					// 取消交易出库单
					delOpsOrder(cancelId, inputDto, cancelWmsJYCKOrder, null);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 调拨单删单
	 */
	@Override
	public Boolean cancelTKCKByOrder(CancelForOrderDto inputDto) throws OpsException {
		if (Objects.isNull(inputDto.getUserDto())) {
			throw Exceptions.OpsException("请传删单用户");
		}
		// bugid:12714 c14717 2023-11-27
		ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "17");
		if (!dataTypeCodesInfo.isSuccess() || Objects.isNull(dataTypeCodesInfo.getData())) {
			throw Exceptions.OpsException("调用删单开关失败");
		}
		if (!dataTypeCodesInfo.getData().getExtNote1().equals("0")
				&& !dataTypeCodesInfo.getData().getExtNote1().equals("1")) {
			throw Exceptions.OpsException("调用删单开关失败Code=:" + dataTypeCodesInfo.getData().getExtNote1());
		}
		// 查询 出库指令 构造删除数据
		List<OpsDo> doList = baseDoService.findDoListByOrder(inputDto.getOrderId(), inputDto.getOrderItem(), null,
				null);
		if (CollectionUtils.isEmpty(doList)) {
			throw Exceptions.OpsException("无发货指令");
		}
		// bugid 9267 c14717 2023/01/06 ps:每次取消都需要问wms，wms如果无记录返回成功
		OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(doList.get(0).getWarehouseCode());
		if (Objects.nonNull(opsWarehouse)
				&& WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsWarehouse.getWarehouseType())) {
			throw Exceptions.OpsException("服务备库调库取消，无此功能");
		}
		for (OpsDo opsDo : doList) {
			if (!DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())) {
				throw Exceptions.OpsException("调库单取消接口，无法取消其他单据:" + opsDo.getDoType());
			}
			// bugid: 12058 c14717 20230908 2主动调拨单取消 物流已经部分发运 包含调拨单
			if (DoStatusEnum.PART.getStatus().equals(opsDo.getDoStatus())
					|| DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())) {
				throw Exceptions.OpsException("物流已作业");
			}
		}
		// bugid:12714 c14717 2023-11-27
		// 调用物流老的接口
		if (dataTypeCodesInfo.getData().getExtNote1().equals("0")) {
			return cancelDBOrderV1(inputDto, doList);
		}
		// 调用新删单接口
		if (dataTypeCodesInfo.getData().getExtNote1().equals("1")) {
			return cancelDBOrderV2(inputDto, doList);
		}
		return false;
	}

	/**
	 * 订单取消 交易出库单是否能删除 调拨单 可删可不删
	 */
	@Override
	public Boolean cancellationOfOrder(CancelForOrderDto inputDto) throws OpsException {
		if (Objects.isNull(inputDto.getUserDto())) {
			throw Exceptions.OpsException("请传删单用户");
		}
		// bugid:12714 c14717 2023-11-27
		ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "17");
		if (!dataTypeCodesInfo.isSuccess() || Objects.isNull(dataTypeCodesInfo.getData())) {
			throw Exceptions.OpsException("调用删单开关失败");
		}
		if (!dataTypeCodesInfo.getData().getExtNote1().equals("0")
				&& !dataTypeCodesInfo.getData().getExtNote1().equals("1")) {
			throw Exceptions.OpsException("调用删单开关失败Code=:" + dataTypeCodesInfo.getData().getExtNote1());
		}
		List<OpsDo> doList = baseDoService.findDoListByOrder(inputDto.getOrderId(), inputDto.getOrderItem(), null,
				null);
		if (CollectionUtils.isEmpty(doList)) {
			throw Exceptions.OpsException("无发货指令");
		}
		// 订单状态校验
		for (OpsDo opsDo : doList) {
			if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {// 交易客单
				// 物流已经部分发运
				if (DoStatusEnum.PART.getStatus().equals(opsDo.getDoStatus())
						|| DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())) {
					return false;
				}
			}
		}
		// bugid:12714 c14717 2023-11-27
		// 调用物流老的接口
		if (dataTypeCodesInfo.getData().getExtNote1().equals("0")) {
			return cancelOrderV1(inputDto, doList);
		}
		// 调用新删单接口
		if (dataTypeCodesInfo.getData().getExtNote1().equals("1")) {
			return cancelOrderV2(inputDto, doList);
		}
		return false;
	}

	/**
	 * 调用删单接口 v1 bugid:12714 c14717 2023-11-27
	 *
	 * @param inputDto
	 * @param doList
	 * @return
	 * @throws OpsException
	 */
	public Boolean cancelDBOrderV1(CancelForOrderDto inputDto, List<OpsDo> doList) throws OpsException {
		List<CancelDocOrderDto> cancelWmsDBCKOrder = new ArrayList<>();// 调拨出库报文
		for (OpsDo opsDo : doList) {
			// bugid 9267 c14717 2023/01/06 ps:每次取消都需要问wms，wms如果无记录返回成功
			getCancelOrder(opsDo, inputDto, null, cancelWmsDBCKOrder, null, null);
		}
		// 插入 取消操作记录到order_cancel表
		Long cancelId = opsDoService.insertOrderCancel(inputDto);
		if (!CollectionUtils.isEmpty(cancelWmsDBCKOrder)) {
			CommonResult<List<JSONObject>> wmDBCKResult = opsCallWmsFeignApi.cancelDocOrder(cancelWmsDBCKOrder);// 查看调拨是否可删除
			if (wmDBCKResult.isSuccess()) {// 如果调拨删除成功
				// bugid:10525 c14717 20230419
				delOpsTKCKOrder(cancelId, inputDto, cancelWmsDBCKOrder);
			} else {
				throw Exceptions.OpsException("物流已经作业，无法取消");
			}
		}
		return true;
	}

	/**
	 * 调用删单接口 v2 bugid:12714 c14717 2023-11-27
	 *
	 * @param inputDto
	 * @param doList
	 * @return
	 * @throws OpsException
	 */
	public Boolean cancelDBOrderV2(CancelForOrderDto inputDto, List<OpsDo> doList) throws OpsException {
		List<CancelDocOrderDto> cancelWmsDBCKOrder = new ArrayList<>();// 调拨出库报文 用于删除ops指令
		List<CancelDocOrderV2Dto> cancelBDOrderV2 = new ArrayList<>();// wms报文V2  用于删除wms指令
		for (OpsDo opsDo : doList) {
			// bugid 9267 c14717 2023/01/06 ps:每次取消都需要问wms，wms如果无记录返回成功
			getCancelOrder(opsDo, inputDto, null, cancelWmsDBCKOrder, null, cancelBDOrderV2);
		}
		// 插入 取消操作记录到order_cancel表
		Long cancelId = opsDoService.insertOrderCancel(inputDto);
		if (!CollectionUtils.isEmpty(cancelWmsDBCKOrder)) {
			CommonResult<List<JSONObject>> wmDBCKResult = opsCallWmsFeignApi.cancelDocOrderV2(cancelBDOrderV2);// 查看调拨是否可删除
			if (wmDBCKResult.isSuccess()) {// 如果调拨删除成功
				// bugid:10525 c14717 20230419
				delOpsTKCKOrder(cancelId, inputDto, cancelWmsDBCKOrder);
			} else {
				throw Exceptions.OpsException("物流已经作业，无法取消");
			}
		}
		return true;
	}

	/**
	 * 调用删单接口 v1 bugid:12714 c14717 2023-11-27
	 *
	 * @param inputDto
	 * @param doList
	 * @return
	 * @throws OpsException
	 */
	public Boolean cancelOrderV1(CancelForOrderDto inputDto, List<OpsDo> doList) throws OpsException {
		List<CancelDocOrderDto> cancelWmsJYCKOrder = new ArrayList<>();// 交易出库wms报文
		// 包括do和pco
		List<CancelDocOrderDto> cancelWmsDBCKOrder = new ArrayList<>();// 调拨出库报文
		for (OpsDo opsDo : doList) {
			// bugid 9267 c14717 2023/01/06 ps:每次取消都需要问wms，wms如果无记录返回成功
			getCancelOrder(opsDo, inputDto, cancelWmsJYCKOrder, cancelWmsDBCKOrder, null, null);
		}
		CommonResult<List<JSONObject>> wmJYCKResult = opsCallWmsFeignApi.cancelDocOrder(cancelWmsJYCKOrder);// 查看是否可删除
		// 物流已作业
		if (!wmJYCKResult.isSuccess()) {
			return false;
		}
		// 插入 取消操作记录到order_cancel表
		Long cancelId = opsDoService.insertOrderCancel(inputDto);
		if (CollectionUtils.isNotEmpty(cancelWmsDBCKOrder)) {
			// 调拨单不为空
			for (CancelDocOrderDto cancelDocOrderDto : cancelWmsDBCKOrder) {
				List<CancelDocOrderDto> cancelWmsDBCKOrderSub = new ArrayList<>();// 调拨出库报文
				cancelWmsDBCKOrderSub.add(cancelDocOrderDto);
				CommonResult<List<JSONObject>> wmDBCKResult = opsCallWmsFeignApi.cancelDocOrder(cancelWmsDBCKOrderSub);// 查看调拨是否可删除
				if (wmDBCKResult.isSuccess()) {// 如果调拨删除成功
					delOpsOrder(cancelId, inputDto, null, cancelWmsDBCKOrderSub);
				}
			}
		}
		delOpsOrder(cancelId, inputDto, cancelWmsJYCKOrder, null);
		return true;
	}

	/**
	 * 调用删单接口 v2 bugid:12714 c14717 2023-11-27
	 *
	 * @param inputDto
	 * @param doList
	 * @return
	 * @throws OpsException
	 */
	public Boolean cancelOrderV2(CancelForOrderDto inputDto, List<OpsDo> doList) throws OpsException {
		List<CancelDocOrderDto> cancelWmsJYCKOrder = new ArrayList<>();// 适配旧方法 删除ops指令报文
		List<CancelDocOrderDto> cancelWmsDBCKOrder = new ArrayList<>();// 适配旧方法 删除ops指令报文
		List<CancelDocOrderV2Dto> cancelOrderV2 = new ArrayList<>();// wms报文V2   删除wms指令报文
		List<CancelDocOrderV2Dto> cancelBDOrderV2 = new ArrayList<>();// wms报文V2  删除wms指令报文
		// 收集指令报文
		for (OpsDo opsDo : doList) {
			getCancelOrder(opsDo, inputDto, cancelWmsJYCKOrder, cancelWmsDBCKOrder, cancelOrderV2, cancelBDOrderV2);
		}
		// 批量取消交易指令
		CommonResult<List<JSONObject>> wmJYCKResult = opsCallWmsFeignApi.cancelDocOrderV2(cancelOrderV2);// 查看是否可删除
		// 物流已作业
		if (!wmJYCKResult.isSuccess()) {
			return false;
		}
		// 插入 取消操作记录到order_cancel表
		Long cancelId = opsDoService.insertOrderCancel(inputDto);
		if (CollectionUtils.isNotEmpty(cancelBDOrderV2)) {
			// 收集删除ops指令报文
			Map<String,CancelDocOrderDto > DBMap = new HashMap<>();
			for (CancelDocOrderDto cancelDocOrderDto : cancelWmsDBCKOrder) {
				DBMap.put(cancelDocOrderDto.getDocNo(),cancelDocOrderDto);
			}
			for (CancelDocOrderV2Dto V2Dto : cancelBDOrderV2) {
				// 逐条取消wms指令 bugid 15498 c14717 20241021
				List<CancelDocOrderV2Dto> V2DtoSubList = new ArrayList<>();// 调拨出库报文
				V2DtoSubList.add(V2Dto);
				CommonResult<List<JSONObject>> wmDBCKResult = opsCallWmsFeignApi.cancelDocOrderV2(V2DtoSubList);// 查看调拨是否可删除
				if (wmDBCKResult.isSuccess()) {// 如果调拨删除成功
					// 删除wms指令
					List<CancelDocOrderDto> canSubDO = new ArrayList<>();
					canSubDO.add(DBMap.get(V2Dto.getDocNo()));
					delOpsOrder(cancelId, inputDto, null, canSubDO);
				}
			}
		}
		delOpsOrder(cancelId, inputDto, cancelWmsJYCKOrder, null);
		return true;
	}

	/**
	 * 委托在库订单取消 C14717 20221017
	 */
	@Override
	public Boolean cancellationOfOrderWT(CancelForOrderDto inputDto) throws OpsException {
		if (Objects.isNull(inputDto.getUserDto())) {
			throw Exceptions.OpsException("请传删单用户");
		}
		boolean successCancelFlag = true;// 是否能取消订单
		// 插入 取消操作记录到order_cancel表
		Long cancelId = null;
		// 查询 出库指令 构造删除数据
		List<OpsDo> doList = baseDoService.findDoListByOrder(inputDto.getOrderId(), inputDto.getOrderItem(), null,
				null);
		if (!CollectionUtils.isEmpty(doList)) {
			if (doList.size() == 1) {
				OpsDo opsDo = doList.get(0);
				if (!DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {// 交易客单
					throw Exceptions.OpsException("数据异常，请联系it人员处理", opsDo.getDoId());
				}
				if (!DoSourceEnum.ALL.getType().equals(opsDo.getDoSource())) {
					throw Exceptions.OpsException("拆分订单，请联系it人员处理", opsDo.getDoId());
				}
				cancelId = opsDoService.insertOrderCancel(inputDto);
				List<String> orderNos = new ArrayList<>();
				orderNos.add(inputDto.getOrderFno());
				ResultVo<String> resultVo = consignmentStockFeignApi.cancelCsExpDetailByOrderNo(orderNos);
				if (Objects.nonNull(resultVo) && resultVo.isSuccess()) {
					updateWmOrderTask(opsDo.getDoId(), inputDto.getReason());
					opsDoService.CancelDo(opsDo.getId(), opsDo.getDoId(), inputDto.getOrderId(),
							inputDto.getOrderItem(), inputDto.getUserDto());
					opsDoService.insertOrderCancelItem(cancelId, opsDo.getDoId(), DoTypeEnum.JYCK.getType(), "取消成功",
							inputDto.getUserDto());
				} else {
					if (Objects.nonNull(resultVo) && StringUtils.isNotEmpty(resultVo.getMessage())) {
						throw Exceptions.OpsException(resultVo.getMessage());
					} else {
						throw Exceptions.OpsException("数据异常，请联系it人员处理", inputDto.getOrderFno());
					}
				}
			} else {
				throw Exceptions.OpsException("数据异常，请联系it人员处理", inputDto.getOrderFno());
			}
		} else {
			throw Exceptions.OpsException("无数据", inputDto.getOrderFno());
		}
		return successCancelFlag;
	}

	// 整备数据
	private void getCancelOrder(OpsDo opsDo, CancelForOrderDto inputDto, List<CancelDocOrderDto> doAndPcoList,
								List<CancelDocOrderDto> dbList, List<CancelDocOrderV2Dto> cancelOrderV2List,
								List<CancelDocOrderV2Dto> cancelDBOrderV2List) throws OpsException {
		if (org.springframework.util.StringUtils.isEmpty(inputDto.getReason())) {
			// bugid : 8811 c14717 20221130
			inputDto.setReason("OPS取消指令");
		}
		if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
			if (opsDo.getDoStatus() > DoStatusEnum.WAIT.getStatus()) {
				throw Exceptions.OpsException("发货中和发货完成不可取消", opsDo.getDoId());
			}
			// 如果是pco 固定kt; 如果是do CM参加DoTypeEnum枚举对应关系的wltype
			if(Objects.nonNull(doAndPcoList)){
				CancelDocOrderDto doParam = new CancelDocOrderDto(opsDo.getId(), opsDo.getGatherWarehouseCode(),
						opsDo.getDoId(), "CM", inputDto.getReason());
				doAndPcoList.add(doParam);
			}
			CancelDocOrderV2Dto orderV2Dto = new CancelDocOrderV2Dto(opsDo.getGatherWarehouseCode(), opsDo.getDoId(),
					inputDto.getReason());
			if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())
					&& !DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
				List<OpsPco> pcoList = opsPcoService.GetPcolistByOrder(inputDto.getOrderId(), inputDto.getOrderItem());
				if (!CollectionUtils.isEmpty(pcoList)) {
					for (OpsPco opsPco : pcoList) {
						if(Objects.nonNull(doAndPcoList)){
							CancelDocOrderDto param = new CancelDocOrderDto(opsPco.getId(), opsPco.getWarehouseCode(),
									opsPco.getPcoId(), "KT", inputDto.getReason());
							doAndPcoList.add(param);
						}
						orderV2Dto.setRelatedPcoId(opsPco.getPcoId());
					}
				}
			}
			// bugid:12714 c14717 2023-11-27
			if (Objects.nonNull(cancelOrderV2List)) {
				cancelOrderV2List.add(orderV2Dto);
			}
		} else {// 其他全部是调拨
			if (StringUtils.isBlank(opsDo.getDoType()) || Objects.isNull(DoTypeEnum.getType(opsDo.getDoType()))) {
				throw Exceptions.OpsException("doType 为空");
			}
			//bugid:14056 c14717 2024-04-25 删除调拨单判断状态
			if (DoStatusEnum.INIT.getStatus().equals(opsDo.getDoStatus())
					|| DoStatusEnum.WAIT.getStatus().equals(opsDo.getDoStatus())) {
				if(Objects.nonNull(dbList)){
					CancelDocOrderDto param = new CancelDocOrderDto(opsDo.getId(), opsDo.getWarehouseCode(), opsDo.getDoId(),
							DoTypeEnum.getType(opsDo.getDoType()).getWltype(), inputDto.getReason());
					dbList.add(param);
				}
				// bugid:12714 c14717 2023-11-27
				if (Objects.nonNull(cancelDBOrderV2List)) {
					CancelDocOrderV2Dto orderV2Dto = new CancelDocOrderV2Dto(opsDo.getWarehouseCode(), opsDo.getDoId(),
							inputDto.getReason());
					cancelDBOrderV2List.add(orderV2Dto);
				}
			}
		}
	}

	// 订单取消
	private void updateWmOrderTask(String wmOrderId, String msg) throws OpsException {
		OpsWmOrderTaskCondition condition = new OpsWmOrderTaskCondition();
		condition.setWmOrderId(wmOrderId);
		// condition.setFlag(2);
		condition.setDelFlag(1);
		condition.setMsg(msg);
		wmOrderTaskService.updateOpsWmOrderTaskFlagByCondition(condition);
	}

	// 删除opsdelOpsTKCKOrder相关表软删除
	private void delOpsTKCKOrder(Long cancelId, CancelForOrderDto inputDto, List<CancelDocOrderDto> cancelWmsDBCKOrder)
			throws OpsException {

		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(cancelWmsDBCKOrder)) {
			for (CancelDocOrderDto cancelDocOrderDto : cancelWmsDBCKOrder) {
				OpsDo opsDo = baseDoService.getDoByDoId(cancelDocOrderDto.getDocNo());

				updateWmOrderTask(cancelDocOrderDto.getDocNo(), inputDto.getReason());
				opsDoService.CancelDo(cancelDocOrderDto.getWmId(), cancelDocOrderDto.getDocNo(), inputDto.getOrderId(),
						inputDto.getOrderItem(), inputDto.getUserDto());
				opsDoService.insertOrderCancelItem(cancelId, cancelDocOrderDto.getDocNo(), opsDo.getDoType(), "取消成功",
						inputDto.getUserDto());
				List<OpsRo> opsRos = baseRoService.findDBRoByDBDo(opsDo);
				if (CollectionUtils.isEmpty(opsRos)) {
					throw Exceptions.OpsException(opsDo.getDoId() + "，无RO数据");
				}
				if (opsRos.size() > 1) {
					throw Exceptions.OpsException(opsDo.getDoId() + "，RO数据重复" + opsRos.get(0).getRoId());
				}
				opsRoService.delOpsRoOrder(cancelId, opsRos.get(0), inputDto.getReason(), inputDto.getUserDto());
			}
		}
	}

	// 删除ops相关表软删除
	private void delOpsOrder(Long cancelId, CancelForOrderDto inputDto, List<CancelDocOrderDto> cancelWmsJYCKOrder,
							 List<CancelDocOrderDto> cancelWmsDBCKOrder ) throws OpsException {
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(cancelWmsJYCKOrder)) {
			for (CancelDocOrderDto cancelDocOrderDto : cancelWmsJYCKOrder) {
				updateWmOrderTask(cancelDocOrderDto.getDocNo(), inputDto.getReason());
				if ("CM".equals(cancelDocOrderDto.getOrderType())) {// 交易出库
					opsDoService.CancelDo(cancelDocOrderDto.getWmId(), cancelDocOrderDto.getDocNo(),
							inputDto.getOrderId(), inputDto.getOrderItem(), inputDto.getUserDto());
					opsDoService.insertOrderCancelItem(cancelId, cancelDocOrderDto.getDocNo(),
							DoTypeEnum.JYCK.getType(), "取消成功", inputDto.getUserDto());
				} else if ("KT".equals(cancelDocOrderDto.getOrderType())) {// 加工
					opsPcoService.CancelPco(cancelDocOrderDto.getWmId(), cancelDocOrderDto.getDocNo(), inputDto);
					opsDoService.insertOrderCancelItem(cancelId, cancelDocOrderDto.getDocNo(),
							PcoTypeEnum.PTJG.getDesc(), "取消成功", inputDto.getUserDto());
				}
			}
		}
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(cancelWmsDBCKOrder)) {
			for (CancelDocOrderDto cancelDocOrderDto : cancelWmsDBCKOrder) {
				OpsDo opsDo = baseDoService.getDoByDoId(cancelDocOrderDto.getDocNo());
				updateWmOrderTask(cancelDocOrderDto.getDocNo(), inputDto.getReason());
				opsDoService.CancelDo(cancelDocOrderDto.getWmId(), cancelDocOrderDto.getDocNo(), inputDto.getOrderId(),
						inputDto.getOrderItem(), inputDto.getUserDto());
				opsDoService.insertOrderCancelItem(cancelId, cancelDocOrderDto.getDocNo(), opsDo.getDoType(), "取消成功",
						inputDto.getUserDto());
				List<OpsRo> opsRos = baseRoService.findDBRoByDBDo(opsDo);
				if (CollectionUtils.isEmpty(opsRos)) {
					throw Exceptions.OpsException(opsDo.getDoId() + "，无RO数据");
				}
				if (opsRos.size() > 1) {
					throw Exceptions.OpsException(opsDo.getDoId() + "，RO数据重复" + opsRos.get(0).getRoId());
				}
				opsRoService.delOpsRoOrder(cancelId, opsRos.get(0), inputDto.getReason(), inputDto.getUserDto());
			}
		}
	}

	/**
	 * 仅判断当前够不够越库条件 不判断上预约还是越库的条件
	 *
	 * @description：判断是否可以越库 true 越库 false 不越库
	 * @author ：c02483
	 * @date ：Created in 2022/1/17 13:32
	 */
	private boolean isEnoughToCrossForDo(OpsDo opsDo, OpsDoItem doItem, String invoice) throws OpsException {
		// DOItemInventory
		List<OpsDoItemInventory> subdoItemInventoryList = baseDoService.getDoItemInventoryByDoId(opsDo.getDoId(), null);
		int itemQty = 0;
		for (OpsDoItemInventory subdoItemInventory : subdoItemInventoryList) {
			// 在途表
			if (InventoryTableTypeEnum.TRANS.getType().equals(subdoItemInventory.getInventoryTableType())) {
				// 在途
				OpsInventoryMove opsInventoryMove = baseInventoryService
						.getInventoryMoveById(subdoItemInventory.getInventoryId());
				if (Objects.nonNull(opsInventoryMove)) {
					// 在途表只判断当前发票内的数量
					if (Objects.equals(opsInventoryMove.getInvoiceno(), invoice)) {
						itemQty = itemQty + subdoItemInventory.getUseQty();
					}
				}
			}
			// 物流在库表
			if (InventoryTableTypeEnum.NORMAL.getType().equals(subdoItemInventory.getInventoryTableType())) {
				itemQty = itemQty + subdoItemInventory.getUseQty();
			}
		}
		if (doItem.getQty() != itemQty) {
			return false;
		}
		return true;
	}

	/**
	 * 判断越库还是上预约指令
	 *
	 * @description：判断是否货齐 true 越库 false 不越库 OpsDo-OpsDoItem 是1对1关系
	 * @author ：c02483
	 * @date ：Created in 2022/1/17 13:32
	 */
	private boolean isEnoughTocrossForDoFlag(OpsDo opsDo, OpsDoItem doItem, String invoice) throws OpsException {
		// DOItemInventory
		List<OpsDoItemInventory> subdoItemInventoryList = baseDoService.getDoItemInventoryByDoId(opsDo.getDoId(), null);
		// 在途数
		int tranQty = 0;
		// 在库数
		int invQty = 0;
		int moveCount = 0;
		for (OpsDoItemInventory subdoItemInventory : subdoItemInventoryList) {
			// 在途表
			if (InventoryTableTypeEnum.TRANS.getType().equals(subdoItemInventory.getInventoryTableType())) {
				// 在途
				OpsInventoryMove opsInventoryMove = baseInventoryService
						.getInventoryMoveById(subdoItemInventory.getInventoryId());
				if (Objects.nonNull(opsInventoryMove)) {
					if (Objects.equals(opsInventoryMove.getInvoiceno(), invoice)) {
						tranQty += opsInventoryMove.getQuantity();
						moveCount++;
					}
				}
			}
			// 物流在库表（如果DO占用库存数，也不当货不齐）
			if (InventoryTableTypeEnum.NORMAL.getType().equals(subdoItemInventory.getInventoryTableType())) {
				invQty += subdoItemInventory.getUseQty();
			}
		}
		// 在库数有预分配 当货不齐（不越库）
		if (invQty > 0) {
			return false;
		}
		if(moveCount>1){
			return false;
		}
		// 当前发票在途数与DO出库数量不一致不可越库
		if (doItem.getQty() != tranQty) {
			return false;
		}

		return true;
	}

	/**
	 * @description：判断加工单是否可以越库
	 * @author ：c02483
	 * @date ：Created in 2022/1/17 15:02
	 */
	// private boolean isEnoughTocrossForPco(OpsPco opsPco, List<OpsPcoItem>
	// pcoItems, String invoice)
	// throws OpsException {
	// for (OpsPcoItem pcoItem : pcoItems) {
	// List<OpsPcoItemInventory> subPcoItemsInventoryList = opsPcoService
	// .selectItemInventoryBypcoId(pcoItem.getPcoId(), pcoItem.getPcoItem(),
	// null);
	// int itemQty = 0;
	// for (OpsPcoItemInventory subPcoItemInventory : subPcoItemsInventoryList)
	// {
	// if
	// (InventoryTableTypeEnum.TRANS.getType().equals(subPcoItemInventory.getInventoryTableType()))
	// {
	// OpsInventoryMove opsInventoryMove = baseInventoryService
	// .getInventoryMoveById(subPcoItemInventory.getInventoryId());
	// if (Objects.nonNull(opsInventoryMove)) {
	// if (Objects.equals(opsInventoryMove.getInvoiceno(), invoice)) {
	// itemQty = itemQty + subPcoItemInventory.getUseQty();
	// }
	// }
	// }
	// if
	// (InventoryTableTypeEnum.NORMAL.getType().equals(subPcoItemInventory.getInventoryTableType()))
	// {
	// itemQty = itemQty + subPcoItemInventory.getUseQty();
	// }
	// }
	// if (pcoItem.getSubQty() != itemQty) {
	// return false;
	// }
	// }
	// return true;
	// }

	/**
	 * 判断当前pco+item是否货齐 (当前行)
	 */
	private boolean isEnoughTocrossForPcoItem(OpsPcoItem pcoItem, String invoice) throws OpsException {
		List<OpsPcoItemInventory> subPcoItemsInventoryList = opsPcoService
				.selectItemInventoryBypcoId(pcoItem.getPcoId(), pcoItem.getPcoItem(), null);
		int itemQty = 0;
		for (OpsPcoItemInventory subPcoItemInventory : subPcoItemsInventoryList) {
			if (InventoryTableTypeEnum.TRANS.getType().equals(subPcoItemInventory.getInventoryTableType())) {
				OpsInventoryMove opsInventoryMove = baseInventoryService
						.getInventoryMoveById(subPcoItemInventory.getInventoryId());
				if (Objects.nonNull(opsInventoryMove)) {
					if (Objects.equals(opsInventoryMove.getInvoiceno(), invoice)) {
						itemQty = itemQty + subPcoItemInventory.getUseQty();
					}
				}
			}
			if (InventoryTableTypeEnum.NORMAL.getType().equals(subPcoItemInventory.getInventoryTableType())) {
				itemQty = itemQty + subPcoItemInventory.getUseQty();
			}
		}
		if (pcoItem.getSubQty() != itemQty) {
			return false;
		}
		return true;
	}

	/**
	 * 到货确认用
	 * 信用拦截 1通过（不拦截） 0不通过(拦截)
	 */
	private boolean checkCreditCross(String orderNo, String item) throws OpsException {
		return !opsCustomerOrderService.checkCredit(orderNo, item);
	}

	/**
	 * 退货生成在途数据
	 */
	@Deprecated
	@Override
	public void handReturnOrderConfirm(List<CreInvMoveForReturnOrderDto> list) throws OpsException {

		// 非组装按退货单+退货项，逐项调用传值,始终list.size=1，组装就按组装子项一次性传如组装3个子项，list.size=3
		// 2022-12-1 旧
		// 按批次一次性传，非组装+组装=1+N（子项拆分），如2个非组装+1个组装（3个子项）， 传非组装2+组装3个子项，list.size=5
		// 2022-12-8 新
		if (Objects.isNull(list)) {
			throw Exceptions.OpsException("参数解析失败--List<OpsImpdata>");
		}
		if (CollectionUtils.isEmpty(list)) {
			throw Exceptions.OpsException("无退货数据导入");
		}
		// 数据校验判断
		for (CreInvMoveForReturnOrderDto inputDto : list) {
			if (StringUtils.isEmpty(inputDto.getApplyNo())) {
				throw Exceptions.OpsException("退货单号不可为空");
			}
			if (inputDto.getItemNo() < 1) {
				throw Exceptions.OpsException("退货单项号不可为空");
			}
			if (StringUtils.isEmpty(inputDto.getWarehouseCode())) {
				throw Exceptions.OpsException("仓库代码不可为空");
			}
			if (StringUtils.isEmpty(inputDto.getCustomerNo())) {
				throw Exceptions.OpsException("客户代码不可为空");
			}
			if (StringUtils.isEmpty(inputDto.getModelNo())) {
				throw Exceptions.OpsException("型号不可为空");
			}
			if (inputDto.getQty() < 0) {
				throw Exceptions.OpsException("良品不可为负数");
			}
			// //传的是组装子项size>1 判断是否存在组装拆分项
			// if (list.size() > 1) {
			// if (null == inputDto.getSplitItemNo() || 0 ==
			// inputDto.getSplitItemNo()) {
			// throw Exceptions.OpsException("传入数组大于1为组装数据，子项拆分不可为空或等于0");
			// }
			// }
		}
		// //一次推送一个退货单(多个退货单就是传值错误)
		// List<String> applyNoList =
		// list.stream().map(CreInvMoveForReturnOrderDto::getApplyNo).distinct().collect(Collectors.toList());
		// if (applyNoList.size() > 1) {
		// throw Exceptions.OpsException("单次推送数据需为同一个退货单号");
		// }
		// 发票号规则(不可大于22)：单行退货TH+申请号+项号 or 整单退货TH+申请号
		String invoiceNo;
		// 同1退货单号分批传值，用小时区分（上游1小时传一次）
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHH");
		String mmddhh = simpleDateFormat.format(new Date());
		List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
		for (CreInvMoveForReturnOrderDto inputDto : list) {
			String applyNo = inputDto.getApplyNo();
			Integer itemNo = inputDto.getItemNo();
			String modelNo = inputDto.getModelNo();
			Integer qty = inputDto.getQty();
			String psnNo = inputDto.getPsnNo();
			String warehouseCode = inputDto.getWarehouseCode();
			String orderNo = inputDto.getOrderNo();
			String customerNo = inputDto.getCustomerNo();
			int orderItem = inputDto.getOrderItem();
			int splitItemNo = 0;
			if (null != inputDto.getSplitItemNo()) {
				splitItemNo = inputDto.getSplitItemNo();// 订单拆分项
			}
			// 单行退货TH+申请号+项号
			// if (list.size() == 1) {
			// invoiceNo = "TH-" + applyNo + "-" + String.valueOf(itemNo);
			// } else {
			// // 整单退货TH+申请号
			// invoiceNo = "TH-" + applyNo;
			// }
			// invoiceNo = "TH-" + applyNo + "-" + String.valueOf(itemNo);
			// TH-TH2211150009-120116 (月日小时)
			if (applyNo.toUpperCase().startsWith("TH")) {
				// 退货号TH开头，不加TH-防止发票号超长
				invoiceNo = applyNo + "-" + mmddhh;
			} else {
				// 退货号非TH开头，加TH-目识别是退货的单据
				invoiceNo = "TH-" + applyNo + "-" + mmddhh;
			}
			if (qty == 0) {
				// "当良品是0时，不继续执行"
				continue;
			}
			// 存在PO不可继续写入(po=退货申请号+项号)
			// List<OpsInventoryMove> opsInventoryMoves =
			// baseInventoryService.getInventoryMoveByPo(orderNo, orderItem,
			// splitItemNo, null);
			// begin bug:9384 退货处理存在一个订单多次退货 B28029 2023-02-01
			List<OpsInventoryMove> opsInventoryMoves = baseInventoryService.getOpsInventoryMoveListByAssociateNo(
					InventoryStatusEnum.THTRANS, modelNo, orderNo, orderItem, splitItemNo, invoiceNo);
			// end bug:9384
			if (!CollectionUtils.isEmpty(opsInventoryMoves)) {
				throw Exceptions.OpsException(
						"已产生在途库存数据，请勿重复生成." + orderNo + "-" + orderItem + "-" + splitItemNo + "型号：" + modelNo);
			}
			Long inventoryPropertyId = 1L;
			// 客户通用库存
			if (inputDto.getToUserStock()) {
				OpsInventoryProperty property = new OpsInventoryProperty();
				property.setInventoryTypeCode(InventoryTypeEnum.GKTY.getType());
				property.setCustomerNo(customerNo);
				inventoryPropertyId = opsInventoryPropertyService.findPropertyWithInsertByExample(property,
						new UserDto("handReturn", null));
			}
			OpsInventoryMove tinventoryMove = new OpsInventoryMove();
			tinventoryMove.setWarehouseCode(warehouseCode);
			tinventoryMove.setSignWarehouseCode(warehouseCode);
			tinventoryMove.setInventoryStatus(InventoryStatusEnum.THTRANS.getCode());
			tinventoryMove.setQuantity(qty);
			tinventoryMove.setQaStatus(QAStatusEnum.NORMAL.getType());
			tinventoryMove.setPrepareQuantity(0);
			tinventoryMove.setModelno(modelNo);
			tinventoryMove.setInventoryPropertyId(inventoryPropertyId);
			// begin bug:8857 B28029 2022-12-01 退货申请号改成订单号
			tinventoryMove.setAssociateNo(orderNo);
			tinventoryMove.setAssociateNoItem(orderItem);
			tinventoryMove.setAssociateNoSplitno(splitItemNo);
			// end
			// tinventoryMove.setAssociateNo(applyNo);
			// tinventoryMove.setAssociateNoItem(itemNo);
			// tinventoryMove.setAssociateNoSplitno(splitItemNo);
			tinventoryMove.setOrderno(orderNo);
			tinventoryMove.setItemno(orderItem);
			tinventoryMove.setSplititemno(splitItemNo);// 订单拆分项
			tinventoryMove.setSupplierid(customerNo);// 退货供应商代码填写客户代码
			tinventoryMove.setInvoiceno(invoiceNo);
			tinventoryMove.setPrereceivedate(DateUtil.date()); // 预计到达时间默认当天
			tinventoryMove.setSourceType(SourceTypeEnum.RETURN.getType());
			tinventoryMove.setOptStatus(OptStatusEnum.PURCHASE_ACCEPT.getCode());
			tinventoryMove.setPoQty(qty);
			// 插入采购在途（T4退货在途）
			Long tinventoryMoveId = baseInventoryService.createInvMoveReturnId(tinventoryMove, UserDto.WMS);
			// 退货ROID=RO+TH+applyNo+itemno(3位数)+数量（4位）
			String roId = "ROTH" + applyNo + String.format("%03d", itemNo) + String.format("%03d", splitItemNo)
					+ String.format("%04d", qty);
			OpsRo opsRo = new OpsRo();
			opsRo.setInvoiceNo(invoiceNo);
			opsRo.setRoId(roId);
			// begin bug:8857 B28029 2022-12-09 退货申请号改成订单号
			opsRo.setOrderId(orderNo);
			opsRo.setOrderItem(String.valueOf(orderItem));
			opsRo.setNum(splitItemNo);
			// end
			opsRo.setRoSource(RoSourceEnum.RETURN.getType());
			opsRo.setRoType(RoTypeEnum.THRK.getType());
			opsRo.setWarehouseCode(warehouseCode);
			opsRo.setRoStatus(RoStatusEnum.WAIT.getStatus());
			opsRo.setCreator(psnNo);
			opsRo.setCreTime(new Date());
			opsRoMapper.insertSelective(opsRo);
			// RO调拨只有一条写入RO-Item
			OpsRoItem opsRoItem = new OpsRoItem();
			opsRoItem.setRoId(roId);
			opsRoItem.setRoItem(1);
			opsRoItem.setModelno(modelNo);
			opsRoItem.setQty(qty);
			opsRoItem.setRecQty(0);
			opsRoItem.setQaStatus(QAStatusEnum.NORMAL.getType());
			opsRoItem.setFromInventoryId(tinventoryMoveId);
			opsRoItem.setFromInventoryTableType(InventoryTableTypeEnum.TRANS.getType());
			opsRoItem.setRemark("退货处理");
			opsRoItem.setCreator(psnNo);
			opsRoItem.setCreTime(new Date());
			opsRoItemMapper.insertSelective(opsRoItem);
			// 写入ops_ro_item_inventory
			OpsRoItemInventory opsRoItemInventory = new OpsRoItemInventory();
			opsRoItemInventory.setRoId(opsRo.getRoId());
			opsRoItemInventory.setRoItem(opsRoItem.getRoItem());
			opsRoItemInventory.setRecQty(0);
			opsRoItemInventory.setInventoryId(tinventoryMoveId);
			opsRoItemInventory.setQty(qty);
			opsRoItemInventory.setDelflag(0);
			opsRoItemInventory.setVersion(0L);
			opsRoItemInventory.setCreator(psnNo);
			opsRoItemInventory.setCreTime(new Date());
			opsRoItemInventoryMapper.insertSelective(opsRoItemInventory);
			// 写入RO-Barcode
			OpsRoBarcode opsRoBarcode = new OpsRoBarcode();
			opsRoBarcode.setInvoiceno(invoiceNo);
			opsRoBarcode.setRoId(roId);
			opsRoBarcode.setRoItem(1);
			opsRoBarcode.setOrderno(orderNo);// 申请号
			opsRoBarcode.setItemno(orderItem);// 申请子项
			opsRoBarcode.setNum(splitItemNo);// 子项拆分项
			opsRoBarcode.setAssNum(0);
			opsRoBarcode.setQty(qty);
			opsRoBarcode.setDelflag(0);
			opsRoBarcode.setModelno(modelNo);
			opsRoBarcode.setWarehouseCode(warehouseCode);
			opsRoBarcode.setCreator(psnNo);
			opsRoBarcode.setCreTime(new Date());
			// 生成箱码
			ResultVo<String> stringResultVo = commonServiceFeignApi.generatorBillNo("20");
			if (!stringResultVo.isSuccess() || stringResultVo.getData() == null) {
				throw Exceptions.OpsException("生成barcode异常:" + orderNo + "-" + itemNo.toString());
			}
			opsRoBarcode.setBarcode(stringResultVo.getData());
			opsRoBarcode.setScan(1);
			opsRoBarcodeMapper.insertSelective(opsRoBarcode);
			OpsWmOrderTask opsWmOrderTask = new OpsWmOrderTask();
			opsWmOrderTask.setFlag(Integer.valueOf(SendStatusEnum.INIT.getType()));
			opsWmOrderTask.setCreator(UserDto.WMS.getUserName());
			opsWmOrderTask.setCreTime(new Date());
			opsWmOrderTask.setWmOrderId(roId);
			opsWmOrderTask.setWmOrderType(WmOrderTaskEnum.RO.getType());
			opsWmOrderTask.setTaskType(WmOrderTaskEnum.ORDER.getType());
			taskList.add(opsWmOrderTask);
			OpsWmOrderTask opsWmOrderTaskBarcode = new OpsWmOrderTask();
			opsWmOrderTaskBarcode.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.getType()));
			opsWmOrderTaskBarcode.setCreator(UserDto.WMS.getUserName());
			opsWmOrderTaskBarcode.setCreTime(new Date());
			opsWmOrderTaskBarcode.setWmOrderId(roId);
			opsWmOrderTaskBarcode.setWmOrderType(WmOrderTaskEnum.RO.getType());
			opsWmOrderTaskBarcode.setTaskType(WmOrderTaskEnum.BARCODE.getType());
			taskList.add(opsWmOrderTaskBarcode);
		}
		// 批量更新task
		wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
	}

	/**
	 * 历史数据（旧数据）迁移写入在途表
	 *
	 * @param inventoryStatusEnum
	 *            P(生成中) T1（在途中） T3(调拨中)
	 */
	/*
	 * @Override public void handOldOrderConfirm(String inventoryStatusEnum)
	 * throws OpsException {
	 * 
	 * if (InventoryStatusEnum.PRODUCE.getCode().equalsIgnoreCase(
	 * inventoryStatusEnum)) { // 1按PO
	 * OpsInventoryMoveDatamigrationMasterExample masterExample = new
	 * OpsInventoryMoveDatamigrationMasterExample();
	 * masterExample.createCriteria().andStatusEqualTo(1)
	 * .andInventoryStatusEqualTo(InventoryStatusEnum.PRODUCE.getCode()); // P
	 * 生成 在途 List<OpsInventoryMoveDatamigrationMaster> masters =
	 * opsInventoryMoveDatamigrationMasterMapper
	 * .selectByExample(masterExample); if (CollectionUtils.isEmpty(masters)) {
	 * throw Exceptions.OpsException("无数据生成."); } Map<OpsInventoryMove,
	 * List<OpsInventoryMoveDatamigrationMaster>> mapPoInv = new HashMap<>(); //
	 * 按发票号 for (OpsInventoryMoveDatamigrationMaster master : masters) {
	 * OpsInventoryMove inventoryMove = new OpsInventoryMove();
	 * inventoryMove.setAssociateNo(master.getAssociateNo());
	 * inventoryMove.setAssociateNoItem(master.getAssociateNoItem());
	 * inventoryMove.setAssociateNoSplitno(master.getAssociateNoSplitno()); if
	 * (!mapPoInv.containsKey(inventoryMove)) {
	 * List<OpsInventoryMoveDatamigrationMaster> implist = new ArrayList();
	 * implist.add(master); mapPoInv.put(inventoryMove, implist); } else {
	 * mapPoInv.get(inventoryMove).add(master); } } ExecutorService
	 * fixedThreadPool = Executors.newFixedThreadPool(6); try { for
	 * (Map.Entry<OpsInventoryMove, List<OpsInventoryMoveDatamigrationMaster>>
	 * entryMaster : mapPoInv .entrySet()) {
	 * List<OpsInventoryMoveDatamigrationMaster> masterPos =
	 * entryMaster.getValue(); // 创建一个可重用固定个数的线程池(8个线程)
	 * fixedThreadPool.execute(() -> { try {
	 * this.handOldOrderConfirmForMove(InventoryStatusEnum.PRODUCE, "",
	 * masterPos); } catch (Exception ex) {
	 * System.out.println(Thread.currentThread().getName() + "==>" +
	 * ex.toString()); } }); } } catch (Exception ex) {
	 * 
	 * } finally { fixedThreadPool.shutdown(); System.out.println("exits"); }
	 * 
	 * } else if (InventoryStatusEnum.CGTRANS.getCode().equalsIgnoreCase(
	 * inventoryStatusEnum) ||
	 * InventoryStatusEnum.DBTRANS.getCode().equalsIgnoreCase(
	 * inventoryStatusEnum)) { OpsInventoryMoveDatamigrationMasterExample
	 * masterExample = new OpsInventoryMoveDatamigrationMasterExample();
	 * masterExample.createCriteria().andStatusEqualTo(1).
	 * andInventoryStatusEqualTo(inventoryStatusEnum);
	 * List<OpsInventoryMoveDatamigrationMaster> masters =
	 * opsInventoryMoveDatamigrationMasterMapper
	 * .selectByExample(masterExample); List<String> invoiceNos = new
	 * ArrayList<>(); for (OpsInventoryMoveDatamigrationMaster master : masters)
	 * { if (!StringUtils.isEmpty(master.getInvoiceNo())) { if
	 * (!invoiceNos.contains(master.getInvoiceNo())) {
	 * invoiceNos.add(master.getInvoiceNo()); } } } // 创建一个可重用固定个数的线程池(8个线程)
	 * ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4); try {
	 * for (int i = 0; i < invoiceNos.size(); i++) { String invoceNo =
	 * invoiceNos.get(i); Integer invoceIndex = i; fixedThreadPool.execute(() ->
	 * { try { System.out.println(Thread.currentThread().getName() + "-->" +
	 * String.valueOf(invoceIndex) + "==>" + invoceNo); if
	 * (InventoryStatusEnum.DBTRANS.getCode().equalsIgnoreCase(
	 * inventoryStatusEnum)) {
	 * this.handOldOrderConfirmForMove(InventoryStatusEnum.DBTRANS, invoceNo,
	 * null); } else if (InventoryStatusEnum.CGTRANS.getCode().equalsIgnoreCase(
	 * inventoryStatusEnum)) {
	 * this.handOldOrderConfirmForMove(InventoryStatusEnum.CGTRANS, invoceNo,
	 * null); } } catch (Exception ex) {
	 * System.out.println(Thread.currentThread().getName() + "==>" +
	 * ex.toString()); } }); } } catch (Exception ex) {
	 * 
	 * } finally { fixedThreadPool.shutdown(); System.out.println("exits"); } }
	 * else { throw Exceptions.OpsException("无效类型，仅支持P、T1与T3"); }
	 * 
	 * }
	 */
	/*
	 * 
	 * @Override public void handOldOrderConfirmForMove(InventoryStatusEnum
	 * inventoryStatusEnum, String invoice,
	 * List<OpsInventoryMoveDatamigrationMaster> masterPos) throws OpsException
	 * { Date fromDate = new Date(); Integer masterCount = 0; Integer
	 * detailCount = 0; if (InventoryStatusEnum.PRODUCE == inventoryStatusEnum)
	 * { for (OpsInventoryMoveDatamigrationMaster opsImpdata : masterPos) {
	 * StringBuilder str = new StringBuilder("PO号：" +
	 * opsImpdata.getAssociateNo() + "-"); OpsInventoryProperty property = new
	 * OpsInventoryProperty(); long inventoryPropertyId = 1; //
	 * 默认TY不用查批次属性（迁移数据只有客户代码） if
	 * (!opsImpdata.getInventoryTypeCode().equalsIgnoreCase("TY")) {
	 * property.setInventoryTypeCode(opsImpdata.getInventoryTypeCode());
	 * property.setCustomerNo(opsImpdata.getCustomerNo()); // 历史迁移没以下属性
	 * inventoryPropertyId =
	 * opsInventoryPropertyService.findPropertyWithInsertByExample(property, new
	 * UserDto("handOldOrderConfirm", null)); } // 操作人 String psnNo =
	 * UserDto.WMS.getUserName(); OpsInventoryMove tinventoryMove = new
	 * OpsInventoryMove();
	 * tinventoryMove.setWarehouseCode(opsImpdata.getWarehouseCode());
	 * tinventoryMove.setSignWarehouseCode(opsImpdata.getSignWarehouseCode());
	 * // 采购在途
	 * tinventoryMove.setInventoryStatus(opsImpdata.getInventoryStatus());
	 * tinventoryMove.setQuantity(opsImpdata.getQuantity());
	 * tinventoryMove.setQaStatus(QAStatusEnum.NORMAL.getType());
	 * tinventoryMove.setPrepareQuantity(0);
	 * tinventoryMove.setModelno(opsImpdata.getModelno());
	 * tinventoryMove.setInventoryPropertyId(inventoryPropertyId);
	 * tinventoryMove.setOrderno(opsImpdata.getOrderno());
	 * tinventoryMove.setItemno(opsImpdata.getItemno());
	 * tinventoryMove.setSplititemno(opsImpdata.getSplititemno());
	 * tinventoryMove.setAssociateNo(opsImpdata.getAssociateNo());
	 * tinventoryMove.setAssociateNoItem(opsImpdata.getAssociateNoItem());
	 * tinventoryMove.setAssociateNoSplitno(opsImpdata.getAssociateNoSplitno());
	 * tinventoryMove.setSupplierid(opsImpdata.getSupplierid());
	 * tinventoryMove.setInvoiceno(opsImpdata.getInvoiceNo()); // imDateTheory
	 * 理论到达仓库时间
	 * tinventoryMove.setPrereceivedate(opsImpdata.getPrereceivedate());
	 * tinventoryMove.setSourceType(String.valueOf(opsImpdata.getSourceType()));
	 * tinventoryMove.setOptStatus(InventoryMoveOpsStatusEnum.INVONICE_ACCEPT.
	 * getCode()); tinventoryMove.setRemark("历史数据迁移导入");
	 * tinventoryMove.setPoQty(opsImpdata.getQuantity()); if
	 * (StringUtil.isEmpty(opsImpdata.getSignWarehouseCode())) {
	 * tinventoryMove.setSignWarehouseCode(opsImpdata.getWarehouseCode()); }
	 * else {
	 * tinventoryMove.setSignWarehouseCode(opsImpdata.getSignWarehouseCode()); }
	 * // 插入采购在途（T4退货在途）
	 * baseInventoryService.createInvMoveReturnId(tinventoryMove,
	 * UserDto.ADMIN); // 更新中间表状态 OpsInventoryMoveDatamigrationMaster master =
	 * new OpsInventoryMoveDatamigrationMaster(); master.setStatus(2);// 已经生成在途
	 * master.setId(opsImpdata.getId());
	 * opsInventoryMoveDatamigrationMasterMapper.updateByPrimaryKeySelective(
	 * master);
	 * 
	 * } } else if (InventoryStatusEnum.CGTRANS == inventoryStatusEnum ||
	 * InventoryStatusEnum.DBTRANS == inventoryStatusEnum) {
	 * 
	 * try { StringBuilder str = new StringBuilder("发票号：" + invoice); String
	 * inventoryStatus = InventoryStatusEnum.CGTRANS.getCode(); if
	 * (InventoryStatusEnum.DBTRANS == inventoryStatusEnum) { inventoryStatus =
	 * InventoryStatusEnum.DBTRANS.getCode(); }
	 * 
	 * // 1按发票号 OpsInventoryMoveDatamigrationMasterExample masterExample = new
	 * OpsInventoryMoveDatamigrationMasterExample();
	 * masterExample.createCriteria().andStatusEqualTo(1).
	 * andInventoryStatusEqualTo(inventoryStatus) .andInvoiceNoEqualTo(invoice);
	 * // T1 生成 RO入库数据 List<OpsInventoryMoveDatamigrationMaster> masters =
	 * opsInventoryMoveDatamigrationMasterMapper
	 * .selectByExample(masterExample); if (CollectionUtils.isEmpty(masters)) {
	 * throw Exceptions.OpsException("无数据生成."); } //
	 * 供应商代码，同一个发票号应该是相同供应商代码（上游数据已处理区分） String supplierId = ""; if
	 * (StringUtil.isNotEmpty(masters.get(0).getSupplierid())) { supplierId =
	 * masters.get(0).getSupplierid(); } // 按发票号汇总（按票逐一遍历） Map<String,
	 * List<OpsInventoryMoveDatamigrationMaster>> mapPoInvbarcode = new
	 * HashMap<>(); for (OpsInventoryMoveDatamigrationMaster invoicedto :
	 * masters) { if (!mapPoInvbarcode.containsKey(invoicedto.getInvoiceNo())) {
	 * List<OpsInventoryMoveDatamigrationMaster> implist = new ArrayList();
	 * implist.add(invoicedto); mapPoInvbarcode.put(invoicedto.getInvoiceNo(),
	 * implist); } else {
	 * mapPoInvbarcode.get(invoicedto.getInvoiceNo()).add(invoicedto); } }
	 * Map<OpsInventoryMove, List<OpsInventoryMoveDatamigrationMaster>> mapPoInv
	 * = new HashMap<>(); // 按发票号 for (Map.Entry<String,
	 * List<OpsInventoryMoveDatamigrationMaster>> entry :
	 * mapPoInvbarcode.entrySet()) { String invoiceNo = entry.getKey();
	 * List<OpsInventoryMoveDatamigrationMaster> masterList = entry.getValue();
	 * for (OpsInventoryMoveDatamigrationMaster opsImpdata : masterList) {
	 * OpsInventoryMove inventoryMove = new OpsInventoryMove();
	 * inventoryMove.setAssociateNo(opsImpdata.getAssociateNo());
	 * inventoryMove.setAssociateNoItem(opsImpdata.getAssociateNoItem());
	 * inventoryMove.setAssociateNoSplitno(opsImpdata.getAssociateNoSplitno());
	 * if (!mapPoInv.containsKey(inventoryMove)) {
	 * List<OpsInventoryMoveDatamigrationMaster> implist = new ArrayList();
	 * implist.add(opsImpdata); mapPoInv.put(inventoryMove, implist); } else {
	 * mapPoInv.get(inventoryMove).add(opsImpdata); } } } int i = 0; // 按PO遍历
	 * for (Map.Entry<OpsInventoryMove,
	 * List<OpsInventoryMoveDatamigrationMaster>> entryMaster : mapPoInv
	 * .entrySet()) { i += 1; List<OpsInventoryMoveDatamigrationMaster> masterPo
	 * = entryMaster.getValue(); Date poFromDate = new Date();
	 * 
	 * List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>(); for
	 * (OpsInventoryMoveDatamigrationMaster opsImpdata : masterPo) { str = new
	 * StringBuilder("发票号：" + invoice); OpsInventoryProperty property = new
	 * OpsInventoryProperty(); Date propertyFromDate = new Date(); //
	 * 默认TY(批属性=1) long inventoryPropertyId = 1; // 默认TY不用查批次属性 if
	 * (!opsImpdata.getInventoryTypeCode().equalsIgnoreCase("TY")) {
	 * property.setInventoryTypeCode(opsImpdata.getInventoryTypeCode());
	 * property.setCustomerNo(opsImpdata.getCustomerNo()); inventoryPropertyId =
	 * opsInventoryPropertyService.findPropertyWithInsertByExample(property, new
	 * UserDto("handOldOrderConfirmForMove", null)); } str.append(",1.批属性用时:" +
	 * String.valueOf(new Date().getTime() - propertyFromDate.getTime()) + "毫秒,"
	 * + String.valueOf(
	 * com.sales.ops.common.until.DateUtil.getDiffSecond(propertyFromDate, new
	 * Date())) + "秒"); propertyFromDate = new Date(); // 操作人 String psnNo =
	 * UserDto.WMS.getUserName(); OpsInventoryMove tinventoryMove = new
	 * OpsInventoryMove();
	 * tinventoryMove.setWarehouseCode(opsImpdata.getWarehouseCode());
	 * tinventoryMove.setSignWarehouseCode(opsImpdata.getSignWarehouseCode());
	 * // 采购在途
	 * tinventoryMove.setInventoryStatus(opsImpdata.getInventoryStatus());
	 * tinventoryMove.setQuantity(opsImpdata.getQuantity());
	 * tinventoryMove.setQaStatus(QAStatusEnum.NORMAL.getType());
	 * tinventoryMove.setPrepareQuantity(0);
	 * tinventoryMove.setModelno(opsImpdata.getModelno());
	 * tinventoryMove.setInventoryPropertyId(inventoryPropertyId);
	 * tinventoryMove.setOrderno(opsImpdata.getOrderno());
	 * tinventoryMove.setItemno(opsImpdata.getItemno());
	 * tinventoryMove.setSplititemno(opsImpdata.getSplititemno());
	 * tinventoryMove.setAssociateNo(opsImpdata.getAssociateNo());
	 * tinventoryMove.setAssociateNoItem(opsImpdata.getAssociateNoItem());
	 * tinventoryMove.setAssociateNoSplitno(opsImpdata.getAssociateNoSplitno());
	 * tinventoryMove.setSupplierid(opsImpdata.getSupplierid());
	 * tinventoryMove.setInvoiceno(opsImpdata.getInvoiceNo()); // imDateTheory
	 * 理论到达仓库时间
	 * tinventoryMove.setPrereceivedate(opsImpdata.getPrereceivedate());
	 * tinventoryMove.setSourceType(String.valueOf(opsImpdata.getSourceType()));
	 * tinventoryMove.setOptStatus(InventoryMoveOpsStatusEnum.INVONICE_ACCEPT.
	 * getCode()); tinventoryMove.setPoQty(opsImpdata.getQuantity()); if
	 * (StringUtil.isEmpty(opsImpdata.getSignWarehouseCode())) {
	 * tinventoryMove.setSignWarehouseCode(opsImpdata.getWarehouseCode()); }
	 * else {
	 * tinventoryMove.setSignWarehouseCode(opsImpdata.getSignWarehouseCode()); }
	 * tinventoryMove.setRemark("历史数据迁移导入"); // 插入采购在途（T4退货在途） Long
	 * tinventoryMoveId =
	 * baseInventoryService.createInvMoveReturnId(tinventoryMove,
	 * UserDto.ADMIN); str.append(",2.写入在途用时:" + String.valueOf(new
	 * Date().getTime() - propertyFromDate.getTime()) + "毫秒," + String.valueOf(
	 * com.sales.ops.common.until.DateUtil.getDiffSecond(propertyFromDate, new
	 * Date())) + "秒"); // 退货ROID=RO+TH+applyNo+itemno(3位数)+数量（4位） String roId =
	 * String.format("RO%s%s%s%s%s", opsImpdata.getInvoiceNo(),
	 * opsImpdata.getSignWarehouseCode(), opsImpdata.getAssociateNo(),
	 * String.format("%03d", opsImpdata.getAssociateNoItem()),
	 * String.format("%03d", null == opsImpdata.getAssociateNoSplitno() ? 0 :
	 * opsImpdata.getAssociateNoSplitno())); propertyFromDate = new Date(); //
	 * RO调拨只有一条写入RO-Item OpsRo opsRo = new OpsRo();
	 * opsRo.setInvoiceNo(opsImpdata.getInvoiceNo()); opsRo.setRoId(roId); //
	 * 采购历史数据，PO号保存 opsRo.setOrderId(opsImpdata.getOrderno());
	 * opsRo.setOrderItem(String.valueOf(opsImpdata.getItemno()));
	 * opsRo.setNum(opsImpdata.getSplititemno());
	 * opsRo.setRoSource(RoSourceEnum.CG.getType());
	 * opsRo.setRoType(RoTypeEnum.CGRKGK.getType());
	 * opsRo.setWarehouseCode(opsImpdata.getWarehouseCode());
	 * opsRo.setRoStatus(RoStatusEnum.WAIT.getStatus()); opsRo.setCreTime(new
	 * Date()); opsRo.setRemark("历史数据迁移导入"); opsRoMapper.insertSelective(opsRo);
	 * // RO调拨只有一条写入RO-Item OpsRoItem opsRoItem = new OpsRoItem();
	 * opsRoItem.setModelno(opsImpdata.getModelno());
	 * opsRoItem.setQty(opsImpdata.getQuantity()); opsRoItem.setRecQty(0);
	 * opsRoItem.setRoId(roId); opsRoItem.setRoItem(1);
	 * opsRoItem.setFromInventoryId(tinventoryMoveId);
	 * opsRoItem.setFromInventoryTableType(InventoryTableTypeEnum.TRANS.getType(
	 * )); opsRoItem.setRemark("历史数据迁移"); opsRoItem.setCreator(psnNo);
	 * opsRoItem.setCreTime(new Date());
	 * opsRoItemMapper.insertSelective(opsRoItem); // 写入ops_ro_item_inventory
	 * OpsRoItemInventory opsRoItemInventory = new OpsRoItemInventory();
	 * opsRoItemInventory.setRecQty(0);
	 * opsRoItemInventory.setInventoryId(tinventoryMoveId);
	 * opsRoItemInventory.setQty(opsImpdata.getQuantity());
	 * opsRoItemInventory.setRoId(opsRo.getRoId());
	 * opsRoItemInventory.setRoItem(opsRoItem.getRoItem());
	 * opsRoItemInventory.setDelflag(0); opsRoItemInventory.setVersion(0L);
	 * opsRoItemInventory.setCreator(psnNo); opsRoItemInventory.setCreTime(new
	 * Date()); opsRoItemInventoryMapper.insertSelective(opsRoItemInventory);
	 * str.append(",3.RO用时:" + String.valueOf(new Date().getTime() -
	 * propertyFromDate.getTime()) + "毫秒," + String.valueOf(
	 * com.sales.ops.common.until.DateUtil.getDiffSecond(propertyFromDate, new
	 * Date())) + "秒"); // 按InvoiceNo写入BAROCDE
	 * OpsInventoryMoveDatamigrationDetailExample detailExample = new
	 * OpsInventoryMoveDatamigrationDetailExample();
	 * OpsInventoryMoveDatamigrationDetailExample.Criteria criteria =
	 * detailExample.createCriteria();
	 * criteria.andStatusEqualTo(1).andInvoiceNoEqualTo(opsImpdata.getInvoiceNo(
	 * ))
	 * .andOrdernoEqualTo(opsImpdata.getOrderno()).andItemnoEqualTo(opsImpdata.
	 * getItemno()); if (opsImpdata.getSplititemno() != null &&
	 * opsImpdata.getSplititemno() > 0) {
	 * criteria.andSplititemnoEqualTo(opsImpdata.getSplititemno()); }
	 * propertyFromDate = new Date(); // T1 生成 RO入库数据
	 * List<OpsInventoryMoveDatamigrationDetail> details =
	 * opsInventoryMoveDatamigrationDetailMapper
	 * .selectByExample(detailExample); if (CollectionUtils.isEmpty(masters)) {
	 * throw Exceptions.OpsException("无数据生成."); } str.append(",4.查中间子表用时:" +
	 * String.valueOf(new Date().getTime() - propertyFromDate.getTime()) + "毫秒,"
	 * + String.valueOf(
	 * com.sales.ops.common.until.DateUtil.getDiffSecond(propertyFromDate, new
	 * Date())) + "秒"); detailCount += details.size(); propertyFromDate = new
	 * Date(); List<OpsRoBarcode> opsRoBarcodes = new ArrayList(); for
	 * (OpsInventoryMoveDatamigrationDetail detail : details) { // 写入RO-Barcode
	 * OpsRoBarcode opsRoBarcode = new OpsRoBarcode();
	 * opsRoBarcode.setInvoiceno(detail.getInvoiceNo());
	 * opsRoBarcode.setWarehouseCode(detail.getWarehouseCode());
	 * opsRoBarcode.setRoId(roId); opsRoBarcode.setRoItem(1);
	 * opsRoBarcode.setOrderno(opsImpdata.getOrderno());
	 * opsRoBarcode.setItemno(opsImpdata.getItemno());
	 * opsRoBarcode.setSplititemno(opsImpdata.getSplititemno());
	 * opsRoBarcode.setQty(detail.getQty()); opsRoBarcode.setDelflag(0);
	 * opsRoBarcode.setModelno(detail.getModelno());
	 * opsRoBarcode.setBarcode(detail.getBarcode());
	 * opsRoBarcode.setPackageCode(detail.getPackageCode());
	 * opsRoBarcode.setCreator(psnNo); opsRoBarcode.setCreTime(new Date());
	 * opsRoBarcode.setScan(1); //
	 * opsRoBarcodeMapper.insertSelective(opsRoBarcode);
	 * opsRoBarcodes.add(opsRoBarcode); } // 批量写入barcode if
	 * (opsRoBarcodes.size() > 0) { log.info("批量写入barcode Msg: {}",
	 * opsRoBarcodes.size());
	 * opsRoBarcodeService.insertBatchBarcode(opsRoBarcodes); }
	 * str.append(",5.写入barcode:" + String.valueOf(details.size()) + ",用时:" +
	 * String.valueOf(new Date().getTime() - propertyFromDate.getTime()) + "毫秒,"
	 * + String.valueOf(
	 * com.sales.ops.common.until.DateUtil.getDiffSecond(propertyFromDate, new
	 * Date())) + "秒"); propertyFromDate = new Date(); OpsWmOrderTask
	 * opsWmOrderTask = new OpsWmOrderTask();
	 * opsWmOrderTask.setFlag(Integer.valueOf(SendStatusEnum.INIT.getType()));
	 * opsWmOrderTask.setCreator(UserDto.ADMIN.getUserName());
	 * opsWmOrderTask.setCreTime(new Date()); opsWmOrderTask.setWmOrderId(roId);
	 * opsWmOrderTask.setWmOrderType(WmOrderTaskEnum.RO.getType());
	 * opsWmOrderTask.setTaskType(WmOrderTaskEnum.ORDER.getType());
	 * taskList.add(opsWmOrderTask); //
	 * wmOrderTaskService.addOpsWmOrderTask(opsWmOrderTask);
	 * 
	 * OpsWmOrderTask opsWmOrderTaskBarcode = new OpsWmOrderTask();
	 * opsWmOrderTaskBarcode.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.
	 * getType()));
	 * opsWmOrderTaskBarcode.setCreator(UserDto.ADMIN.getUserName());
	 * opsWmOrderTaskBarcode.setCreTime(new Date());
	 * opsWmOrderTaskBarcode.setWmOrderId(roId);
	 * opsWmOrderTaskBarcode.setWmOrderType(WmOrderTaskEnum.RO.getType());
	 * opsWmOrderTaskBarcode.setTaskType(WmOrderTaskEnum.BARCODE.getType());
	 * taskList.add(opsWmOrderTaskBarcode); //
	 * wmOrderTaskService.addOpsWmOrderTask(opsWmOrderTaskBarcode);
	 * str.append(",6.写入wmOrder用时:" + String.valueOf(new Date().getTime() -
	 * propertyFromDate.getTime()) + "毫秒," + String.valueOf(
	 * com.sales.ops.common.until.DateUtil.getDiffSecond(propertyFromDate, new
	 * Date())) + "秒"); str.append(",PO:" +
	 * entryMaster.getKey().getAssociateNo() + "-" +
	 * String.valueOf(entryMaster.getKey().getAssociateNoItem()) + ",总用时:" +
	 * String.valueOf(
	 * com.sales.ops.common.until.DateUtil.getDiffSecond(poFromDate, new
	 * Date())) + "秒"); } // 批量更新task
	 * wmOrderTaskService.addBatchOpsWmOrderTask(taskList); } // T1采购在途 if
	 * (InventoryStatusEnum.CGTRANS == inventoryStatusEnum) {
	 * log.info("回写 ImpInvoiceMaster Msg: {}", invoice);
	 * 
	 * // 更新历史迁移数据 更变发票表状态，结果 Boolean result = false; String msg = "";
	 * ImpInvoiceMasterExample impMasterExample = new ImpInvoiceMasterExample();
	 * impMasterExample.createCriteria().andInvoiceNoEqualTo(invoice).
	 * andSupplierCodeEqualTo(supplierId) .andStatusBetween(1, 3);
	 * List<ImpInvoiceMaster> impInvoiceMasters =
	 * impInvoiceMasterMapper.selectByExample(impMasterExample); if
	 * (CollectionUtils.isEmpty(impInvoiceMasters)) { result = false; msg =
	 * "无发票数据"; } else { ImpInvoiceMaster impInvoiceMaster = new
	 * ImpInvoiceMaster(); impInvoiceMaster.setStatus(3);
	 * impInvoiceMaster.setUpdateTime(new Date()); int updateCount =
	 * impInvoiceMasterMapper.updateByExampleSelective(impInvoiceMaster,
	 * impMasterExample); if (updateCount > 0) { result = true; msg =
	 * "成功更新发票状态3.总行数：" + updateCount + "行"; } else { result = false; msg =
	 * "有发票数据但更新错误"; } } ImpInvoiceMasterMigrationResult impMigrationResult =
	 * new ImpInvoiceMasterMigrationResult();
	 * impMigrationResult.setInvoiceno(invoice);
	 * impMigrationResult.setSupplierid(supplierId);
	 * impMigrationResult.setResult(result); impMigrationResult.setMsg(msg);
	 * impMigrationResult.setUpdatetime(new Date());
	 * impInvoiceMasterMigrationResultMapper.insertSelective(impMigrationResult)
	 * ; log.info("回写 ImpInvoiceMaster Msg: {} 成功", invoice); }
	 * log.info("标记历史数据中间表状态 Msg: {} 成功", invoice); Date migrationFromDate = new
	 * Date(); // 按InvoiceNo写入BAROCDE OpsInventoryMoveDatamigrationDetailExample
	 * detailExample = new OpsInventoryMoveDatamigrationDetailExample();
	 * detailExample.createCriteria().andInvoiceNoEqualTo(invoice); // 标记在途状态
	 * OpsInventoryMoveDatamigrationDetail updateDetail = new
	 * OpsInventoryMoveDatamigrationDetail(); updateDetail.setStatus(2);//
	 * 已经生成在途
	 * opsInventoryMoveDatamigrationDetailMapper.updateByExampleSelective(
	 * updateDetail, detailExample); OpsInventoryMoveDatamigrationMasterExample
	 * updateMasterExample = new OpsInventoryMoveDatamigrationMasterExample();
	 * updateMasterExample.createCriteria().andInvoiceNoEqualTo(invoice);
	 * OpsInventoryMoveDatamigrationMaster master = new
	 * OpsInventoryMoveDatamigrationMaster(); master.setStatus(2);// 已经生成在途
	 * master.setUpdatetime(new Date());
	 * opsInventoryMoveDatamigrationMasterMapper.updateByExampleSelective(
	 * master, updateMasterExample); str = new StringBuilder("发票号：" + invoice +
	 * "master项数:" + String.valueOf(masters.size()) + "detailCount项数:" +
	 * String.valueOf(detailCount)); str.append(",更新中间表用时:" + String.valueOf(new
	 * Date().getTime() - migrationFromDate.getTime()) + "毫秒," + String.valueOf(
	 * com.sales.ops.common.until.DateUtil.getDiffSecond(migrationFromDate, new
	 * Date())) + "秒"); str.append(",发票总用时:" + String.valueOf(new
	 * Date().getTime() - fromDate.getTime()) + "毫秒," +
	 * String.valueOf(com.sales.ops.common.until.DateUtil.getDiffSecond(
	 * fromDate, new Date())) + "秒"); ImpInvoiceEventLog log = new
	 * ImpInvoiceEventLog(); log.setOpType("handOldOrderConfirmForMove");
	 * log.setRequestParam(str.toString()); log.setOpStartTime(fromDate);
	 * addImpInvoiceEventLog(log); } catch (Exception ex) {
	 * log.error("handOldOrderConfirmForMove error:{} ex:{}", invoice,
	 * ex.getMessage()); } } else { log.info("无效类型", invoice); throw
	 * Exceptions.OpsException("无效类型."); } }
	 */

	/**
	 * 生成委托在库历史数据导入（转生成在途数据） 委托在库历史迁移代码
	 */
	/*
	 * @Override public void handCsOrderConfirm() throws OpsException { //
	 * 1.查询出csimpdata where status=1 ResultVo<List<CsImpdataVO>> resultVo =
	 * consignmentStockFeignApi.listCsImpdata(); if (!resultVo.isSuccess()) {
	 * throw Exceptions.OpsException("调用查询接口错误.订单号。" + resultVo.getMessage()); }
	 * List<CsImpdataVO> impdataVOS = resultVo.getData(); Map<OpsInventoryMove,
	 * List<CsImpdataVO>> mapPoInv = new HashMap<>(); // 构造PO for (CsImpdataVO
	 * opsImpdata : impdataVOS) { OrderNoInfo orderNoInfo = new
	 * OrderNoInfo().convertFullOrderNo(opsImpdata.getOrderNo()); String orderNo
	 * = orderNoInfo.getOrderNo(); int itemNo = orderNoInfo.getItemNo(); int
	 * splitItem = null == orderNoInfo.getSplitItem() ? 0 :
	 * orderNoInfo.getSplitItem(); OpsInventoryMove inventoryMove = new
	 * OpsInventoryMove(); inventoryMove.setAssociateNo(orderNo);
	 * inventoryMove.setAssociateNoItem(itemNo);
	 * inventoryMove.setAssociateNoSplitno(splitItem);
	 * inventoryMove.setWarehouseCode(opsImpdata.getWarehouseCode());
	 * inventoryMove.setModelno(opsImpdata.getModelNo()); if
	 * (!mapPoInv.containsKey(inventoryMove)) { List<CsImpdataVO> implist = new
	 * ArrayList(); implist.add(opsImpdata); mapPoInv.put(inventoryMove,
	 * implist); } else { mapPoInv.get(inventoryMove).add(opsImpdata); } } //
	 * 日期流水yyyyMMdd(发票号会过长) String yyyyMMdd =
	 * com.smc.smccloud.core.utils.DateUtil.getYearMonthDay(new Date()); //
	 * 更新ROID， 只填 id status roId List<CsImpdataVO> updateRoIds = new
	 * ArrayList<>(); for (Map.Entry<OpsInventoryMove, List<CsImpdataVO>>
	 * entryMaster : mapPoInv.entrySet()) { OpsInventoryMove opsInventoryMove =
	 * entryMaster.getKey(); List<CsImpdataVO> csImportDataDTOS =
	 * entryMaster.getValue(); // 订单待收货总数 int qty = 0; for (CsImpdataVO
	 * csImportDataDTO : csImportDataDTOS) { // 发票号不可空 if
	 * (StringUtils.isEmpty(csImportDataDTO.getInvoiceNo())) { throw
	 * Exceptions.OpsException("存在发票号空值.订单号。" + csImportDataDTO.getOrderNo()); }
	 * if (csImportDataDTO.getQuantity() < 1) { throw
	 * Exceptions.OpsException("在途数不可为0。" + csImportDataDTO.getOrderNo()); } qty
	 * += csImportDataDTO.getQuantity(); } String invoiceNo =
	 * csImportDataDTOS.get(0).getInvoiceNo(); String warehouseCode =
	 * opsInventoryMove.getWarehouseCode(); String orderNo =
	 * opsInventoryMove.getAssociateNo(); int orderItem =
	 * opsInventoryMove.getAssociateNoItem(); int splitItemNo =
	 * opsInventoryMove.getAssociateNoSplitno(); String modelNo =
	 * opsInventoryMove.getModelno(); OpsInventoryMove tinventoryMove = new
	 * OpsInventoryMove(); tinventoryMove.setWarehouseCode(warehouseCode);
	 * tinventoryMove.setSignWarehouseCode(warehouseCode); // 调拨在途
	 * tinventoryMove.setInventoryStatus(InventoryStatusEnum.DBTRANS.getCode());
	 * tinventoryMove.setQuantity(qty);
	 * tinventoryMove.setQaStatus(QAStatusEnum.NORMAL.getType());
	 * tinventoryMove.setPrepareQuantity(0); tinventoryMove.setModelno(modelNo);
	 * tinventoryMove.setInventoryPropertyId(1L);
	 * tinventoryMove.setAssociateNo(orderNo);
	 * tinventoryMove.setAssociateNoItem(orderItem);
	 * tinventoryMove.setAssociateNoSplitno(splitItemNo);
	 * tinventoryMove.setOrderno(orderNo); tinventoryMove.setItemno(orderItem);
	 * tinventoryMove.setSplititemno(splitItemNo);// 订单拆分项
	 * tinventoryMove.setSupplierid(csImportDataDTOS.get(0).getWarehouseCode());
	 * // 调拨为原仓库代码 tinventoryMove.setInvoiceno(invoiceNo);
	 * tinventoryMove.setPrereceivedate(DateUtil.date()); // 预计到达时间默认当天
	 * tinventoryMove.setSourceType(SourceTypeEnum.DB.getType());
	 * tinventoryMove.setOptStatus(InventoryMoveOpsStatusEnum.INVONICE_ACCEPT.
	 * getCode()); tinventoryMove.setPoQty(qty); // 插入采购在途（T4退货在途） Long
	 * tinventoryMoveId =
	 * baseInventoryService.createInvMoveReturnId(tinventoryMove,
	 * UserDto.ADMIN); // 发票号过长改用日期流水yyyyMMdd(发票号会过长) // ROID最大32，ROCS+15发票号+7+3
	 * String roId = "ROCS" + yyyyMMdd + opsInventoryMove.getAssociateNo() +
	 * String.format("%03d", opsInventoryMove.getAssociateNoItem()) +
	 * String.format("%03d", opsInventoryMove.getAssociateNoItem()); OpsRo opsRo
	 * = new OpsRo(); opsRo.setRoId(roId); opsRo.setInvoiceNo(invoiceNo);
	 * opsRo.setOrderId(opsInventoryMove.getAssociateNo());
	 * opsRo.setOrderItem(String.valueOf(opsInventoryMove.getAssociateNoItem()))
	 * ; opsRo.setNum(opsInventoryMove.getSplititemno());
	 * opsRo.setRoSource(RoSourceEnum.CG.getType());
	 * opsRo.setRoType(RoTypeEnum.CGRKGK.getType());
	 * opsRo.setWarehouseCode(opsInventoryMove.getWarehouseCode());
	 * opsRo.setRoStatus(RoStatusEnum.WAIT.getStatus());
	 * opsRo.setCreator("History"); opsRo.setCreTime(new Date());
	 * opsRo.setRemark("委托在库历史数据迁移导入"); opsRoMapper.insertSelective(opsRo);
	 * 
	 * // RO调拨只有一条写入RO-Item OpsRoItem opsRoItem = new OpsRoItem();
	 * opsRoItem.setModelno(opsInventoryMove.getModelno());
	 * opsRoItem.setQty(qty); opsRoItem.setRecQty(0); opsRoItem.setRoId(roId);
	 * opsRoItem.setRoItem(1); opsRoItem.setFromInventoryId(tinventoryMoveId);
	 * opsRoItem.setFromInventoryTableType(InventoryTableTypeEnum.TRANS.getType(
	 * )); opsRoItem.setRemark("委托在库历史数据迁移"); opsRoItem.setCreator("WMS");
	 * opsRoItem.setCreTime(new Date());
	 * opsRoItemMapper.insertSelective(opsRoItem);
	 * 
	 * // 写入ops_ro_item_inventory OpsRoItemInventory opsRoItemInventory = new
	 * OpsRoItemInventory(); opsRoItemInventory.setRecQty(0);
	 * opsRoItemInventory.setInventoryId(tinventoryMoveId);
	 * opsRoItemInventory.setQty(qty); opsRoItemInventory.setRoId(roId);
	 * opsRoItemInventory.setRoItem(1); opsRoItemInventory.setDelflag(0);
	 * opsRoItemInventory.setVersion(0L); opsRoItemInventory.setCreator("WMS");
	 * opsRoItemInventory.setCreTime(new Date());
	 * opsRoItemInventoryMapper.insertSelective(opsRoItemInventory); for
	 * (CsImpdataVO detail : csImportDataDTOS) { // 写入RO-Barcode OpsRoBarcode
	 * opsRoBarcode = new OpsRoBarcode();
	 * opsRoBarcode.setInvoiceno(detail.getInvoiceNo());
	 * opsRoBarcode.setWarehouseCode(detail.getWarehouseCode());
	 * opsRoBarcode.setRoId(roId); opsRoBarcode.setRoItem(1);
	 * opsRoBarcode.setOrderno(orderNo); opsRoBarcode.setItemno(orderItem);
	 * opsRoBarcode.setSplititemno(splitItemNo);
	 * opsRoBarcode.setQty(detail.getQuantity()); opsRoBarcode.setDelflag(0);
	 * opsRoBarcode.setModelno(detail.getModelNo());
	 * opsRoBarcode.setBarcode(detail.getBarcode());
	 * opsRoBarcode.setPackageCode(detail.getCaseNo());
	 * opsRoBarcode.setCreator("WMS"); opsRoBarcode.setCreTime(new Date());
	 * opsRoBarcode.setScan(1);
	 * opsRoBarcodeMapper.insertSelective(opsRoBarcode); CsImpdataVO impdataRo =
	 * new CsImpdataVO(); impdataRo.setRoId(roId);
	 * impdataRo.setId(detail.getId()); impdataRo.setUpdateUser("History");
	 * impdataRo.setUpdateTime(new Date()); updateRoIds.add(impdataRo); } } //
	 * 回更csimpdata roid where status=1 （委托在库收货，不需要追加下发表到富勒系统opsWmOrderTask）
	 * ResultVo<String> updateResult =
	 * consignmentStockFeignApi.updateImpDataRoId(updateRoIds); if
	 * (!resultVo.isSuccess()) { throw Exceptions.OpsException("调用委托在库更新接口错误。" +
	 * resultVo.getMessage()); }
	 * 
	 * }
	 */
	@Override
	public Long addImpInvoiceEventLog(ImpInvoiceEventLog log) {
		if (StringUtil.isEmpty(log.getRequestParam())) {
			return 0L;
		}
		Long useMilliSecond = 0L;// 毫秒
		Date endTime = new Date();
		if (ObjectUtils.isEmpty(log.getOpStartTime())) {
			log.setOpStartTime(endTime);
		}
		useMilliSecond = endTime.getTime() - log.getOpStartTime().getTime();
		log.setOpStatus(0);
		log.setOpEndTime(endTime);
		log.setDuration(useMilliSecond);
		log.setReturnData(log.getReturnData());
		impInvoiceEventLogDao.insertImpInvoiceEventLog(log);
		return log.getId();
	}

	/**
	 * 按交货期发货客户 按客户代码查询（仅需严格按交货期 true ）
	 */
	private boolean exitsCustmerWldateByCustomerNo(String customerNo) {
		if (null == customerNo) {
			return false;
		}
		OpsCustomerWldateExample example = new OpsCustomerWldateExample();
		example.createCriteria().andCustomerNoEqualTo(customerNo).andIsWldateEqualTo(1).andDelFlagEqualTo(0);
		Long count = opsCustomerWldateMapper.countByExample(example);
		return count > 0 ? true : false;
	}

	/**
	 * 是否达到物流时间（可越库的时间判断） 当天>=物流发货日期，可越库。 小于发货日不越库
	 *
	 * @param wlDate
	 *            物流时间
	 * @return 1到达物流日期 0未到达物流日期
	 */
	private boolean isArriveWlDate(Date wlDate) {
		if (null == wlDate) {
			return false;
		}
		int wlDateDay = com.smc.smccloud.core.utils.DateUtil.getDate(new Date()).compareTo(wlDate);
		if (wlDateDay >= 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> listDlvCustomers() throws OpsException {
		return opsDlvCustomerDao.listDlvCustomers();
	}

	/**
	 * 调拨回传取消 按发票号
	 */
	@Override
	public void cancelHandconfirmByInvoiceNo(String invoiceNo) throws OpsException {
		// 1.首先查询调拨回传表
		if (StringUtils.isEmpty(invoiceNo)) {
			throw Exceptions.OpsException("发票号不可空");
		}
		OpsHandoverExample example = new OpsHandoverExample();
		example.createCriteria().andInvoicenoEqualTo(invoiceNo);
		List<OpsHandover> opsHandoversList = handoverMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(opsHandoversList)) {
			throw Exceptions.OpsException(invoiceNo + "，无数据，不需取消。");
		}

		// 在途表
		List<OpsInventoryMove> opsInventoryMoves = baseInventoryService.findOpsInventoryMoveByInvoiceNo(invoiceNo);
		if (CollectionUtils.isEmpty(opsInventoryMoves)) {
			throw Exceptions.OpsException(invoiceNo + "，在途表无数据。");
		}
		List<OpsRo> opsRos = baseRoService.findRoByInvoiceNo(invoiceNo);
		if (CollectionUtils.isEmpty(opsRos)) {
			throw Exceptions.OpsException(invoiceNo + "，RO无数据。");
		}
		// 判断ROITEMN是否完整
		for (OpsRo opsRo : opsRos) {
			OpsRoItem opsRoItem = baseRoService.findRoItemByRoId(opsRo.getRoId());
			if (null == opsRoItem) {
				throw Exceptions.OpsException(invoiceNo + "，opsRoItem无数据。ROID：" + opsRo.getRoId());
			}
		}
		// 找PCO,DO 处理 delflag=1
		for (OpsInventoryMove opsInventoryMove : opsInventoryMoves) {
			List<OpsDoItemInventory> doItemInventorys = baseDoService
					.getDoItemInventoryByInventoryId(opsInventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
			// 如果有关联，把doItemInventory 也删除改为无效
			for (OpsDoItemInventory doItemInventory : doItemInventorys) {
				OpsDo opsDo = baseDoService.getDoByDoId(doItemInventory.getDoId());
				if (opsDo == null) {
					throw Exceptions.OpsException(invoiceNo + "，doid已被删除，请先恢复。" + doItemInventory.getDoId());
				}
				OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(opsDo.getDoId());
				if (opsDoItem == null) {
					throw Exceptions.OpsException(invoiceNo + "，OpsDoItem无数据。ROID：" + opsDo.getDoId());
				}
				// 删除DoItemInventory
				opsDoService.deleteDoItemInventoryByInventoryId(doItemInventory.getInventoryId(),
						InventoryTableTypeEnum.TRANS.getType());
			}
			List<OpsPcoItemInventory> pcoItemInventorys = opsPcoService.getOpsPcoItemInventoryByInventoryId(
					opsInventoryMove.getInventoryId(), InventoryTableTypeEnum.TRANS);
			for (OpsPcoItemInventory pcoItemInventory : pcoItemInventorys) {
				OpsPco opsPco = opsPcoService.getPcoByPcoId(pcoItemInventory.getPcoId());
				if (opsPco == null) {
					throw Exceptions.OpsException(invoiceNo + "，doid已被删除，请先恢复。" + opsPco.getPcoId());
				}
				List<OpsPcoItem> opsPcoItems = opsPcoService.findPcoItemByPcoId(opsPco.getPcoId());
				if (CollectionUtils.isEmpty(opsPcoItems)) {
					throw Exceptions.OpsException(invoiceNo + "，OpsPcoItem无数据。PCOID：" + opsPco.getPcoId());
				}
				opsPcoService.deletePcoItemInventoryByInventoryId(pcoItemInventory.getInventoryId(),
						InventoryTableTypeEnum.TRANS.getType());
			}
		}

		for (OpsRo opsRo : opsRos) {
			baseRoService.deleteRoItemInventoryByRoId(opsRo.getRoId());
			opsRoBarcodeService.delOpsRoBarcodeByRoId(opsRo.getRoId());
			// 下发状态重新改0
			// wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(opsRo.getRoId(),"RO","BARCODE");

			// 物理删除task-ro-barcode
			// wmOrderTaskService.delWmsOrderTaskByWmOrderId(opsRo.getRoId(),"RO",WmOrderTaskEnum.ORDER.getType());
		}
	}

	/**
	 * 整单货齐发货判断（按7位数订单清单） 剔除传入的DO，只判断其他DO项
	 */
	public boolean isEnoughTocrossForFullOrder(List<OpsDo> doList, OpsDo opsDo) throws OpsException {
		String doId = opsDo.getDoId();
		for (OpsDo subOpsDo : doList) {
			if (subOpsDo.getDoId().equals(doId)) {
				continue;
			}
			// 仅判断交易出库的（非交易出库当不越库）
			if (!DoTypeEnum.JYCK.getType().equalsIgnoreCase(subOpsDo.getDoType())) {
				return false;
			}
			// 为响应效率，型号拆分不继续二次判断
			if (DoSourceEnum.ASSModelNo.getType().equalsIgnoreCase(subOpsDo.getDoSource())) {
				return false;
			}
			OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(subOpsDo.getDoId());
			// 总数
			int qty = opsDoItem.getQty();
			int itemQty = 0;
			List<OpsDoItemInventory> opsDoItemInventories = baseDoService.getDoItemInventoryByDoId(subOpsDo.getDoId());
			for (OpsDoItemInventory subDoItemInventory : opsDoItemInventories) {
				if (InventoryTableTypeEnum.NORMAL.getType().equals(subDoItemInventory.getInventoryTableType())) {
					itemQty = itemQty + subDoItemInventory.getUseQty();
				}
				if (InventoryTableTypeEnum.TRANS.getType().equals(subDoItemInventory.getInventoryTableType())) {
					return false;
				}
			}
			// 其中一行DO不货齐
			if (qty != itemQty) {
				return false;
			}
		}
		return true;
	}

	/**
	 * PCO判断是否货齐（剔除掉传入的当行PCO）
	 */
	public boolean isEnoughTocrossForPcoId(OpsDo opsDo, List<OpsPcoItem> pcoItemList, OpsPcoItem opsPcoItem)
			throws OpsException {
		String pcoId = opsPcoItem.getPcoId();
		Integer pcoItem = opsPcoItem.getPcoItem();
		// 仅判断PCO一样，只判断ITEM是否都货齐
		for (OpsPcoItem subOpsPcoItem : pcoItemList) {
			// 传入的PCOID与ITEM不一致，不齐（数据异常）
			if (!pcoId.equalsIgnoreCase(subOpsPcoItem.getPcoId())) {
				return false;
			}
			// 子项号如一样，不继续判断
			if (pcoItem == subOpsPcoItem.getPcoItem()) {
				continue;
			}
			// 仅判断交易出库的（非交易出库当不越库）
			if (!DoTypeEnum.JYCK.getType().equalsIgnoreCase(opsDo.getDoType())) {
				return false;
			}
			// do 不是销售类型 上预约
			if (!DoSourceEnum.ASSModelNo.getType().equalsIgnoreCase(opsDo.getDoSource())) {
				return false;
			}
			// 总数
			int qty = subOpsPcoItem.getSubQty();
			int itemQty = 0;
			List<OpsPcoItemInventory> opsPcoItemInventories = opsPcoService
					.getOpsPcoItemInventoryByPcoId(subOpsPcoItem.getPcoId(), subOpsPcoItem.getPcoItem());
			for (OpsPcoItemInventory subPcoItemInventory : opsPcoItemInventories) {
				if (InventoryTableTypeEnum.NORMAL.getType().equals(subPcoItemInventory.getInventoryTableType())) {
					itemQty = itemQty + subPcoItemInventory.getUseQty();
				}
				// 其他子项，有在途采购且未入库扫描，不子项越库
				if (InventoryTableTypeEnum.TRANS.getType().equals(subPcoItemInventory.getInventoryTableType())) {
					return false;
				}
			}
			// 其中一行DO不货齐
			if (qty != itemQty) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void handOpsRoQtyAdjust(Integer flag) throws OpsException {
		// 查RO调整数据中间表
		OpsRoQtyAdjustExample example = new OpsRoQtyAdjustExample();
		example.createCriteria().andStatusEqualTo(1).andFlagEqualTo(flag);
		List<OpsRoQtyAdjust> opsRoQtyAdjusts = opsRoQtyAdjustMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(opsRoQtyAdjusts)) {
			throw Exceptions.OpsException("无待处理数据");
		}
		// 数据合法性校验
		List<String> roIdList = new ArrayList<>();
		for (OpsRoQtyAdjust roQtyAdjust : opsRoQtyAdjusts) {
			String roId = roQtyAdjust.getRoId();
			if (StringUtils.isEmpty(roQtyAdjust.getModelno())) {
				throw Exceptions.OpsException("型号不可为空.roID:" + roId);
			}
			if (roQtyAdjust.getQty() < 1) {
				throw Exceptions.OpsException("数量不为0或负数.roID:" + roId);
			}
			if (1 != roQtyAdjust.getFlag() && 2 != roQtyAdjust.getFlag()) {
				throw Exceptions.OpsException("处理方式类型.只能是1（多传方式处理）或2（少传方式处理）:" + roId);
			}
			roIdList.add(roId);
		}
		List<String> collect1 = opsRoQtyAdjusts.stream().map(OpsRoQtyAdjust::getRoId).distinct()
				.collect(Collectors.toList());
		// 判断ROID是否重复值
		if (roIdList.size() != collect1.size()) {
			throw Exceptions.OpsException("入库单据ROID存在重复值");
		}
		// 业务逻辑处理 按ROID逐一处理
		for (OpsRoQtyAdjust opsRoQtyAdjust : opsRoQtyAdjusts) {

			String roId = opsRoQtyAdjust.getRoId();
			OpsRo opsRo = baseRoService.findRoByRoId(roId);
			if (Objects.isNull(opsRo)) {
				throw Exceptions.OpsException("opsRo无数据。RoId:" + roId);
			}
			OpsRoItem opsRoItem = baseRoService.findRoItemByRoId(opsRo.getRoId());
			if (Objects.isNull(opsRoItem)) {
				throw Exceptions.OpsException("opsRoItem无数据。RoId:" + roId);
			}
			if (!opsRoItem.getModelno().equals(opsRoQtyAdjust.getModelno())) {
				// 防止ROID号与要处理的型号不一致，增加多层判断
				throw Exceptions.OpsException("型号与ROITEM不一样。RoId:" + roId);
			}
			List<OpsRoPost> opsRoPosts = opsRoPostService.findOpsRoPostByRoId(roId);
			if (CollectionUtils.isEmpty(opsRoPosts)) {
				throw Exceptions.OpsException("无回传数据。RoId:" + roId);
			}
			Integer roPostQty = 0;
			for (OpsRoPost opsRoPost : opsRoPosts) {
				roPostQty += opsRoPost.getQty();
			}
			if (opsRoItem.getQty() == roPostQty) {
				throw Exceptions.OpsException("应收数与已收数量一致，无效处理。RoId:" + roId);
			}
			if (opsRoItem.getRecQty() <= opsRoItem.getQty()) {
				throw Exceptions.OpsException("非多收货情况，不可处理。。RoId:" + roId);
			}
			List<OpsRoItemInventory> opsRoItemInventories = baseRoService.findRoItemInventoryByRoId(roId);
			if (CollectionUtils.isEmpty(opsRoItemInventories)) {
				throw Exceptions.OpsException("opsRoItemInventories无数据。RoId:" + roId);
			}
			String userName = "冲负数量";
			// 多收数量
			int moreQty = roPostQty - opsRoItem.getQty();
			int adjustQty = opsRoQtyAdjust.getQty().intValue();
			if (moreQty != adjustQty) {
				throw Exceptions.OpsException("调整数需要与差异数一致。RoId:" + roId);
			}
			// 修正多出来数据
			// 查库存如果小于等于多出来数，则不能调整负数
			List<OpsInventoryLog> opsInventoryLogs = opsInventoryLogService.getOpsInventoryLogByVoucherCode(roId);
			if (CollectionUtils.isEmpty(opsInventoryLogs)) {
				throw Exceptions.OpsException("opsInventoryLogs无数据。RoId:" + roId);
			}
			// 修改库存数据
			Long inventoryId = opsInventoryLogs.get(0).getInventoryId();
			Long propertyId = opsInventoryLogs.get(0).getPropertyId();

			OpsInventory opsInventory = baseInventoryService.getInventoryById(inventoryId);
			if (Objects.isNull(opsInventory)) {
				throw Exceptions.OpsException("库存opsInventory无数据,inventoryId:" + inventoryId + "。RoId:" + roId);
			}
			// 库存数小于需扣减库存数,标记状态9，且不继续处理逻辑
			// if (opsInventory.getQuantity() < adjustQty) {
			// OpsRoQtyAdjustExample roQtyAdjustExample = new
			// OpsRoQtyAdjustExample();
			// roQtyAdjustExample.createCriteria().andStatusEqualTo(1).andRoIdEqualTo(roId);
			// OpsRoQtyAdjust updateRoQtyAdjust = new OpsRoQtyAdjust();
			// updateRoQtyAdjust.setRemark("调整数：" + adjustQty + "不可大于库存数：" +
			// opsInventory.getQuantity() + ",库存inventoryId:" + inventoryId);
			// updateRoQtyAdjust.setStatus(9);//错误
			// updateRoQtyAdjust.setModifyTime(new Date());
			// int count =
			// opsRoQtyAdjustMapper.updateByExampleSelective(updateRoQtyAdjust,
			// roQtyAdjustExample);
			// if (count == 0) {
			// throw Exceptions.OpsException("更新请购表错误。RoId:" + roId);
			// }
			// //不够库存数，不继续执行
			// continue;
			// }
			// 调整RO-ITEM已收到数量改成应收数量
			baseRoService.updateOpsRoItemRecQty(roId, opsRoItem.getQty(), opsRoItem.getVersion(), userName);
			// 存入对账表
			OpsRoPost opsRoPost = new OpsRoPost();
			opsRoPost.setMsgId("");
			opsRoPost.setRoId(roId);
			opsRoPost.setWarehouseCode(opsRo.getWarehouseCode());
			opsRoPost.setReceiveCode("");
			opsRoPost.setQty(-moreQty);
			opsRoPost.setInventoryType("CC");
			opsRoPost.setScanType("2");
			opsRoPost.setDelflag(0);
			opsRoPost.setCreator(userName);
			opsRoPost.setCreTime(new Date());
			opsRoPost.setModelno(opsRoItem.getModelno());
			opsRoPost.setInvoiceno(opsRo.getInvoiceNo());
			opsRoPost.setReceivetime(new Date());
			opsRoPost.setUsername(userName);
			opsRoPostService.addOpsRoPost(opsRoPost);
			// 增加在库加数
			baseInventoryService.subQtyInventory(inventoryId, moreQty, InventoryTableTypeEnum.NORMAL.getType(),
					UserDto.WMS);
			// 写入OpsInventoryLog
			OpsInventoryLog opsInventoryLog = new OpsInventoryLog();
			opsInventoryLog.setInventoryId(inventoryId);
			opsInventoryLog.setPropertyId(propertyId);
			opsInventoryLog.setQuantity(moreQty);
			opsInventoryLog.setVoucherCode(roId);
			opsInventoryLog.setVoucherType(opsRo.getRoType());
			opsInventoryLog.setOrderNo(opsRo.getOrderId());
			if (!org.springframework.util.StringUtils.isEmpty(opsRo.getOrderItem())) {
				opsInventoryLog.setItemNo(Integer.parseInt(opsRo.getOrderItem()));
			}
			opsInventoryLog.setModelno(opsRoItem.getModelno());
			opsInventoryLog.setInvoiceNo(opsRo.getInvoiceNo());
			opsInventoryLog.setWarehouseCode(opsRo.getWarehouseCode());
			opsInventoryLog.setVersion(0);
			opsInventoryLog.setDelflag(0);
			opsInventoryLog.setCreTime(new Date());
			opsInventoryLog.setCreator(userName);
			opsInventoryLog.setType(false);// 减 false 加true
			opsInventoryLogService.addLog(opsInventoryLog);

			// 采购表处理
			OpsPurchaseorderExample purchaseorderExample = new OpsPurchaseorderExample();
			OpsPurchaseorderExample.Criteria purCriteria = purchaseorderExample.createCriteria();
			purCriteria.andOrdernoEqualTo(opsRo.getOrderId());
			purCriteria.andItemnoEqualTo(Integer.valueOf(opsRo.getOrderItem()));
			purCriteria.andModelnoEqualTo(opsRoItem.getModelno());
			// 如有订单第三个不等于0判断多
			if (opsRo.getNum() > 0) {
				purCriteria.andSplititemnoEqualTo(opsRo.getNum());
			}
			List<OpsPurchaseorder> purchaseorders = opsPurchaseorderMapper.selectByExample(purchaseorderExample);
			// 请购表 有些只有一行
			if (!CollectionUtils.isEmpty(purchaseorders)) {
				// 修正采购收货数量
				OpsPurchaseorder purchaseorder = purchaseorders.get(0);
				// 收货数量大于应收数量，入库数改为应该收数量
				if (purchaseorder.getQtyreceive() > purchaseorder.getQuantity()) {
					OpsPurchaseorder updatePurchaseorder = new OpsPurchaseorder();
					updatePurchaseorder.setId(purchaseorder.getId());
					updatePurchaseorder.setQtyreceive(purchaseorder.getQuantity());
					updatePurchaseorder.setRemark(purchaseorder.getRemark() + ",[调整收货数]："
							+ purchaseorder.getQtyreceive() + "调为：" + purchaseorder.getQuantity());
					int count = opsPurchaseorderMapper.updateByPrimaryKeySelective(updatePurchaseorder);
					if (count == 0) {
						throw Exceptions.OpsException("更新请购表错误。RoId:" + roId);
					}
				}
				// bug13662 废除合并表
				OpsRequestpurchaseExample requestpurchaseExample = new OpsRequestpurchaseExample();
				OpsRequestpurchaseExample.Criteria criteria = requestpurchaseExample.createCriteria();
				criteria.andPoOrderNoEqualTo(purchaseorder.getOrderno());
				criteria.andPoItemNoEqualTo(purchaseorder.getItemno());
				criteria.andModelnoEqualTo(purchaseorder.getModelno());
				if (purchaseorder.getSplititemno() != null) {
					criteria.andPoSplitNoEqualTo(purchaseorder.getSplititemno());
				}
				List<OpsRequestpurchase> requestpurchaseList = opsRequestpurchaseMapper
						.selectByExample(requestpurchaseExample);
				if (CollectionUtils.isNotEmpty(requestpurchaseList)) {
					for (OpsRequestpurchase a : requestpurchaseList) {
						if (a.getQtyimport() > a.getQuantity()) {
							OpsRequestpurchase updateRequestpurchase = new OpsRequestpurchase();
							updateRequestpurchase.setId(a.getId());
							updateRequestpurchase.setQtyimport(a.getQuantity());
							updateRequestpurchase.setUpdatetime(new Date());
							updateRequestpurchase.setRemark(
									a.getRemark() + ",[调整收货数]：" + a.getQtyimport() + "调为：" + a.getQuantity());
							int count = opsRequestpurchaseMapper.updateByPrimaryKeySelective(updateRequestpurchase);
							if (count == 0) {
								throw Exceptions.OpsException("更新请购表错误。RoId:" + roId);
							}
						}
					}
				}

				// 合并采购的处理方式
				// if (purchaseorder.getMergeflag()) {
				// OpsReqPoMappingExample opsReqPoMappingExample = new
				// OpsReqPoMappingExample();
				// OpsReqPoMappingExample.Criteria criteria =
				// opsReqPoMappingExample.createCriteria();
				// criteria.andPurchaseordernoEqualTo(purchaseorder.getOrderno());
				// List<OpsReqPoMapping> opsReqPoMappings =
				// opsReqPoMappingMapper.selectByExample(opsReqPoMappingExample);
				// for (OpsReqPoMapping opsReqPoMapping : opsReqPoMappings) {
				// OpsRequestpurchase requestpurchase =
				// opsRequestpurchaseMapper.selectByPrimaryKey(opsReqPoMapping.getRequestpurchaseid());
				// //收货数量大于应收数量，入库数改为应该收数量
				// if (requestpurchase.getQtyimport() >
				// requestpurchase.getQuantity()) {
				// OpsRequestpurchase updateRequestpurchase = new
				// OpsRequestpurchase();
				// updateRequestpurchase.setId(requestpurchase.getId());
				// updateRequestpurchase.setQtyimport(requestpurchase.getQuantity());
				// updateRequestpurchase.setUpdatetime(new Date());
				// updateRequestpurchase.setRemark(requestpurchase.getRemark() +
				// ",[调整收货数]：" + requestpurchase.getQtyimport() + "调为：" +
				// requestpurchase.getQuantity());
				// int count =
				// opsRequestpurchaseMapper.updateByPrimaryKeySelective(updateRequestpurchase);
				// if (count == 0) {
				// throw Exceptions.OpsException("更新请购表错误。RoId:" + roId);
				// }
				// }
				// }
				// } else {
				// //非合并采购处理
				// //请购表处理
				// OpsRequestpurchaseExample requestpurchaseExample = new
				// OpsRequestpurchaseExample();
				// OpsRequestpurchaseExample.Criteria criteria =
				// requestpurchaseExample.createCriteria();
				// criteria.andOrdernoEqualTo(opsRo.getOrderId());
				// criteria.andItemnoEqualTo(Integer.valueOf(opsRo.getOrderItem()));
				// criteria.andModelnoEqualTo(opsRoItem.getModelno());
				// //如有订单第三个不等于0判断多
				// if (opsRo.getNum() > 0) {
				// criteria.andSplititemnoEqualTo(opsRo.getNum());
				// }
				// List<OpsRequestpurchase> requestpurchaseList =
				// opsRequestpurchaseMapper.selectByExample(requestpurchaseExample);
				// //请购表 有些只有一行
				// if (!CollectionUtils.isEmpty(requestpurchaseList)) {
				// OpsRequestpurchase requestpurchase =
				// requestpurchaseList.get(0);
				// //收货数量大于应收数量，入库数改为应该收数量
				// if (requestpurchase.getQtyimport() >
				// requestpurchase.getQuantity()) {
				// OpsRequestpurchase updateRequestpurchase = new
				// OpsRequestpurchase();
				// updateRequestpurchase.setId(requestpurchase.getId());
				// updateRequestpurchase.setQtyimport(requestpurchase.getQuantity());
				// updateRequestpurchase.setUpdatetime(new Date());
				// updateRequestpurchase.setRemark(requestpurchase.getRemark() +
				// ",[调整收货数]：" + requestpurchase.getQtyimport() + "调为：" +
				// requestpurchase.getQuantity());
				// int count =
				// opsRequestpurchaseMapper.updateByPrimaryKeySelective(updateRequestpurchase);
				// if (count == 0) {
				// throw Exceptions.OpsException("更新请购表错误。RoId:" + roId);
				// }
				// }
				// }
				// }
			}
			// 查RO调整数据中间表
			OpsRoQtyAdjustExample roQtyAdjustExample = new OpsRoQtyAdjustExample();
			roQtyAdjustExample.createCriteria().andStatusEqualTo(1).andRoIdEqualTo(roId);
			OpsRoQtyAdjust updateRoQtyAdjust = new OpsRoQtyAdjust();
			updateRoQtyAdjust.setStatus(2);
			updateRoQtyAdjust.setModifyTime(new Date());
			int count = opsRoQtyAdjustMapper.updateByExampleSelective(updateRoQtyAdjust, roQtyAdjustExample);
			if (count == 0) {
				throw Exceptions.OpsException("更新请购表错误。RoId:" + roId);
			}
		}
		return;
	}
}
