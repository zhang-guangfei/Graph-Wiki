package com.sales.ops.serviceimpl.purchase;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.PurchaseDeleteDao;
import com.sales.ops.db.extdao.RequestPurchaseDao;
import com.sales.ops.db.extdao.SplitSmcCodeDao;
import com.sales.ops.dto.po.PoOrderNoDto;
import com.sales.ops.dto.purchase.*;
import com.sales.ops.dto.query.PurchaseQO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.ResultCode;
import com.sales.ops.enums.OrderTypeEnum;
import com.sales.ops.enums.PoReplyDateStrEnum;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.sales.ops.feign.WmPurchaseFeignApi;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.purchase.*;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author B91717
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private OpsPurchaseorderMapper opsPurchaseorderMapper;

	@Autowired
	private OpsRequestpurchaseMapper opsRequestpurchaseMapper;

	@Autowired
	private OpsImpdataMapper opsImpdataMapper;

	@Autowired
	private RequestPurchaseDao requestPurchaseDao;

	@Autowired
	private OpsPurchaseinvoiceMapper opsPurchaseinvoiceMapper;

	@Autowired
	private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;

	@Autowired
	private PurchaseDeleteDao purchaseDeleteDao;

	@Autowired
	private PurchaseorderViewMapper purchaseorderViewMapper;

	@Autowired
	private RequestPurchasePreService requestPurchasePreService;

	@Autowired
	private WmPurchaseFeignApi wmPurchaseFeignApi;

	@Autowired
	private BasePoService basePoService;

	@Resource
	private PurchaseModifyService purchaseModifyService;

	@Resource
	private OpsPoTranstypeMapper opsPoTranstypeMapper;

	@Autowired
	private PurchaseAutoService purchaseAutoService;

	@Autowired
	private RequestpurchaseViewMapper requestpurchaseViewMapper;

    @Autowired
    private SplitSmcCodeService splitSmcCodeService;

    @Autowired
    private SplitSmcCodeDao splitSmcCodeDao;

	String operator = "";

	/**
	 * 采购单查询初始方法 切换为查 视图purchaseOrder_view 需要查询到删除的信息
	 */
	@Override
	public PageInfo<PurchaseorderView> findAll(PageModel<PurchaseQO> pageModel) {
		PageInfo<PurchaseorderView> pageInfo = new PageInfo<PurchaseorderView>();

		PurchaseorderViewExample example = new PurchaseorderViewExample();
		PurchaseorderViewExample.Criteria criteria = example.createCriteria();
		// 筛选状态为待处理状态的
		if (pageModel.getCondition() == null) {
			pageInfo = PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize())
					.doSelectPageInfo(() -> purchaseorderViewMapper.selectByExample(null));
			return pageInfo;
		}
		PurchaseQO condition = pageModel.getCondition();
		if (StringUtils.isNotBlank(condition.getOrderno())) {
			String pono = condition.getOrderno();
			String orderno;
			Integer itemno = null;
			Integer splititemno = null;
			List<String> ordernoList = new ArrayList<String>();
			// bugid:17671 c14717 20250516  改为视图查询请购信息，包含了删单的信息
			RequestpurchaseViewExample reqex = new RequestpurchaseViewExample();
			RequestpurchaseViewExample.Criteria reqCri = reqex.createCriteria();
			if (condition.getOrderno().contains("-")) {
				String[] split = pono.split("-");
				orderno = split[0];
				// bug15205 增加是否为空判断
				if (split.length > 1 && StringUtils.isNotBlank(split[1]))
					itemno = Integer.parseInt(split[1]);
				if (split.length == 3) {
					splititemno = Integer.parseInt(split[2]);
					reqCri.andOrdernoEqualTo(orderno);
					reqCri.andItemnoEqualTo(itemno);
					reqCri.andSplititemnoEqualTo(splititemno);
				} else if (itemno != null) {
					reqCri.andOrdernoEqualTo(orderno);
					reqCri.andItemnoEqualTo(itemno);
				} else {
					reqCri.andOrdernoEqualTo(orderno);
				}
			} else {
				orderno = condition.getOrderno();
				reqCri.andOrdernoEqualTo(orderno);
			}
			// 采购单号不为空
			reqCri.andPoOrderNoIsNotNull();
			List<RequestpurchaseView> relist = requestpurchaseViewMapper.selectByExample(reqex);
			if (!CollectionUtils.isEmpty(relist)) {
				// bug 14963,采购查询采购单号去重处理
				if (splititemno != null){
					ordernoList.addAll(relist.stream().filter(Objects::nonNull) // 防止空指针异常
							.map(RequestpurchaseView::getOrderItemSplit).distinct().collect(Collectors.toList()));
					if(ordernoList.size()==1){
						String[] split = ordernoList.get(0).split("-");
						if(split.length==3 && StringUtils.isNotBlank(split[2])){
							criteria.andSplititemnoEqualTo(Integer.parseInt(split[2]));
						}
						criteria.andItemnoEqualTo(Integer.parseInt(split[1]));
						criteria.andOrdernoEqualTo(split[0]);
					}else {
						criteria.andOrderItemSplitIn(ordernoList);
					}
				} else if (itemno != null){
					ordernoList.addAll(relist.stream().filter(Objects::nonNull) // 防止空指针异常
							.map(RequestpurchaseView::getOrderItem).distinct().collect(Collectors.toList()));
					if(ordernoList.size()==1){
						String[] split = ordernoList.get(0).split("-");
						criteria.andItemnoEqualTo(Integer.parseInt(split[1]));
						criteria.andOrdernoEqualTo(split[0]);
					}else {
						criteria.andOrderItemIn(ordernoList);
					}
				}else {
					ordernoList.addAll(relist.stream().filter(Objects::nonNull) // 防止空指针异常
							.map(RequestpurchaseView::getPoOrderNo).distinct().collect(Collectors.toList()));
					criteria.andOrdernoIn(ordernoList);
				}
			} else {
				if (itemno != null) {
					criteria.andItemnoEqualTo(itemno);
				}
				if (splititemno != null){
					criteria.andSplititemnoEqualTo(splititemno);
				}
				criteria.andOrdernoEqualTo(orderno);
			}
		}
		if (StringUtils.isNotBlank(condition.getCorderno())) {
			criteria.andCordernoEqualTo(condition.getCorderno());
		}

		if (StringUtils.isNotBlank(condition.getModelno())) {
			criteria.andModelnoLike(condition.getModelno() + "%");
		}
		if (StringUtils.isNotBlank(condition.getPurchasetype())) {
			criteria.andPurchasetypeEqualTo(condition.getPurchasetype());
		}
		if (StringUtils.isNotBlank(condition.getStatecode())) {
			criteria.andStatecodeEqualTo(condition.getStatecode());
		}
		if (StringUtils.isNotBlank(condition.getCustomerno())) {
			criteria.andCustomernoEqualTo(condition.getCustomerno());
		}
		if (StringUtils.isNotBlank(condition.getSupplierid())) {
			criteria.andSupplieridEqualTo(condition.getSupplierid());
		}
		if (StringUtils.isNotBlank(condition.getOrdtype())) {
			criteria.andOrdtypeEqualTo(condition.getOrdtype());
		}
		if (StringUtils.isNotBlank(condition.getReceivewarehouseid())) {
			criteria.andReceivewarehouseidEqualTo(condition.getReceivewarehouseid());
		}
		if (StringUtils.isNotBlank(condition.getOrdtype())) {
			criteria.andOrdtypeEqualTo(condition.getOrdtype());
		}
		// bug14609 WTSR2024000608-添加海外订单导出数据的新需求,增加批次号的筛选条件
		if (StringUtils.isNotBlank(condition.getSendversion())) {
			criteria.andSendversionEqualTo(condition.getSendversion());
		}
		/**
		 * 营业所改位用列表查询
		 */
		if (!CollectionUtils.isEmpty(condition.getDeptNos())) {
			criteria.andDeptnoIn(condition.getDeptNos());
		}
		// 判断开始日期和结束日期
		if (condition.getPurchaseDateStart() != null || condition.getPurchaseDateEnd() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(condition.getPurchaseDateEnd());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
			cal.add(Calendar.DATE, 1);
			condition.setPurchaseDateEnd(cal.getTime());
			criteria.andPurchasedateBetween(condition.getPurchaseDateStart(), condition.getPurchaseDateEnd());
		}
		example.setOrderByClause("purchasedate desc");

		pageInfo = PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize())
				.doSelectPageInfo(() -> purchaseorderViewMapper.selectByExample(example));
		// bug13111,前端返回返信纳期字段进行翻译
		if (!CollectionUtils.isEmpty(pageInfo.getList())) {
			pageInfo.getList().stream().map(purchase -> {
				if (purchase.getDlvmoddate() != null) {
					purchase.setDlvmoddateStr(PoReplyDateStrEnum.getDescByCode(purchase.getDlvmoddate()).getCode());
				}
				return purchase;
			}).collect(Collectors.toList());
		}
		return BeanCopyUtil.pageDto2Vo(pageInfo, PurchaseorderView.class);
	}

	/**
	 * 采购订单转定操作
	 *
	 * @throws OpsException
	 *             先将原订单设为已经取消状态，再新增一条请购单
	 */
	// @Override
	public Integer updateTrans_old(OpsPurchaseorder opsPurchaseorder, String operator) throws OpsException {
		// 转定先设置之前订单为取消状态
		// bug13662 请购表增加采购单号字段，取消合并表
		List<OpsRequestpurchase> relist = basePoService.getRequestPurchaseByPurchase(opsPurchaseorder.getOrderno(),
				opsPurchaseorder.getItemno(), opsPurchaseorder.getSplititemno());
		// 根据sql写入记录表
		purchaseDeleteDao.insertPurchase(opsPurchaseorder.getId(), PurchaseOrderStatusEnum.ZHAUNDINGSHANCHU, operator);
		// 取得备注信息，从该信息后追加转定原因
		// String infomation =
		// opsPurchaseorderMapper.selectByPrimaryKey(opsPurchaseorder.getId()).getInformation();
		// 修改为直接删除表，同时写入记录表
		int result = opsPurchaseorderMapper.deleteByPrimaryKey(opsPurchaseorder.getId());
		PoToWmDto temp = null;
		if (result == 1) {
			temp = new PoToWmDto();
			temp.setPurchase(opsPurchaseorder);
			temp.setRequests(relist);
		}
		if (!opsPurchaseorder.getStatecode().equals(PurchaseOrderStatusEnum.DAICHULI)) {
			OpsPurchaseinvoiceExample example = new OpsPurchaseinvoiceExample();
			OpsPurchaseinvoiceExample.Criteria criteria = example.createCriteria();
			criteria.andOrdernoEqualTo(opsPurchaseorder.getOrderno()).andItemnoEqualTo(opsPurchaseorder.getItemno());
			// bug 12852,拆分单号为空时，增加 isnull条件
			if (opsPurchaseorder.getSplititemno() != null) {
				criteria.andSplititemnoEqualTo(opsPurchaseorder.getSplititemno());
			} else {
				criteria.andSplititemnoIsNull();
			}
			OpsPurchaseinvoice invoice = opsPurchaseinvoiceMapper.selectByExample(example).get(0);
			if (invoice != null) {
				opsPurchaseinvoiceMapper.deleteByPrimaryKey(invoice.getId());
			}
		}
		// if (!OrderTypeEnum.BIN.equals(opsPurchaseorder.getOrdtype())) {
		// 重算收货仓、smccode
		OpsRequestpurchase re = new OpsRequestpurchase();
		re.setModelno(opsPurchaseorder.getModelno());
		re.setPurchasetype(opsPurchaseorder.getPurchasetype());
		re.setDeptno(opsPurchaseorder.getDeptno());
		re.setSupplierid(opsPurchaseorder.getSupplierid());
		re.setCustomerno(opsPurchaseorder.getCustomerno());
		re.setOrdtype(opsPurchaseorder.getOrdtype());
		re.setProducttype(opsPurchaseorder.getProducttype());
		re.setQuantity(opsPurchaseorder.getQuantity());
		re.setHopeexportdate(opsPurchaseorder.getHopeexportdate());
		re.setPurchasewarehouseid(opsPurchaseorder.getReceivewarehouseid());
		re.setTranstype(opsPurchaseorder.getTranstype());
		re = requestPurchasePreService.calWarehouseAndSmccode(re, true);
		// 赋值
		opsPurchaseorder.setSmccode(re.getSmccode());
		// }

		opsPurchaseorder.setId(null);
		opsPurchaseorder.setStatecode(PurchaseOrderStatusEnum.DAICHULI);
		opsPurchaseorder.setPurchasedate(new Date());
		// 20221028设置接单时间等数据为null
		opsPurchaseorder.setReplyorderno(null);
		opsPurchaseorder.setQtyreceive(0);
		// 增加转订操作者
		opsPurchaseorder.setOperator(operator);
		// bug11841 特殊订单，发单时型号需要前面加*进行订货
		if (StringUtils.isNotBlank(opsPurchaseorder.getGreencode())
				&& StringUtils.equals("1", opsPurchaseorder.getGreencode())) {
			opsPurchaseorder.setInformation(opsPurchaseorder.getInformation() + ";add*;");
			opsPurchaseorder.setGreencode(null);
		}
		// 新增一条
        //bugid:19186 c14717 20251127 合并smccode
        OpsPoDestinationConfig subCode = splitSmcCodeService.getSubCode(opsPurchaseorder.getSupplierid(), opsPurchaseorder.getModelno()
                , opsPurchaseorder.getReceivewarehouseid(), opsPurchaseorder.getPurchasetype(), opsPurchaseorder.getTranstype());

        if(Objects.nonNull(subCode)){
            opsPurchaseorder.setSmccode(subCode.getSmccode());
            opsPurchaseorder.setSubCode(subCode.getSubCode());
        }
		int results = opsPurchaseorderMapper.insertSelective(opsPurchaseorder);
		if (results < 1) {
			throw Exceptions.OpsException("转订失败！");
		}
		// bug10516马腾 合并采购订单转订后直接写入order表，因此需要调用订单回写事件
		List<OpsPurchaseStatusToWMDto> opsSendWmList = new ArrayList<OpsPurchaseStatusToWMDto>();
		relist.forEach(i -> {
			OpsPurchaseStatusToWMDto opsSendWm = new OpsPurchaseStatusToWMDto();
			opsSendWm.setOpsPurchaseorder(opsPurchaseorder);
			opsSendWm.setOpsRequestpurchase(i);
			opsSendWmList.add(opsSendWm);
		});
		// bug12704增加自动发单
		try {
			if (StringUtils.equals("JP", opsPurchaseorder.getSupplierid()))
				purchaseAutoService.purchaseAutoJP(false);
			else if (StringUtils.equals("GZ", opsPurchaseorder.getSupplierid()))
				purchaseAutoService.purchaseAutoGZ(false);
			else if (manuSupplyList().contains(opsPurchaseorder.getSupplierid()))
				purchaseAutoService.purchaseAutoManufacture(false);
			else
				purchaseAutoService.purchaseAutoOverSea(false);
		} catch (Exception e) {
			throw Exceptions.OpsException("转订失败！自动发单失败，请重试或联系IT！");
		}
		// 增加自动发单后，将发给订单的修改信息的feign操作向后延迟，发单成功后才调用订单接口
		if (opsSendWmList.size() > 0) {
			if (temp != null) {
				opsWmDispatchForOrderFeignApi.resetForPo(temp);
			}
			opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmList);
		}

		return results;
	}

	public List<String> manuSupplyList() {
		// 定义制造发单的供应商列表
		List<String> suppilyList = new ArrayList<>();
		suppilyList.add("CN");
		suppilyList.add("CM");
		suppilyList.add("YZ");
		suppilyList.add("CT");
		suppilyList.add("TZ");
		return suppilyList;
	}

	// 20221209
	// bug7519 马腾 采购转订 将新采购写入请购表中，从请购阶段开始
	@Override
	public Integer updateTrans(OpsPurchaseorder opsPurchaseorder) throws OpsException {
		// bug10287 马腾 采购日本的不能当天转订日本
		if (StringUtils.equals("JP", opsPurchaseorder.getSupplierid())) {
			OpsPurchaseorder temp = opsPurchaseorderMapper.selectByPrimaryKey(opsPurchaseorder.getId());
			if (temp != null && StringUtils.equals("JP", temp.getSupplierid())
					&& LocalDate.now().isEqual(new LocalDate(temp.getPurchasedate()))) {
				throw Exceptions.OpsException("转订失败！当天不可重复采购到日本！");
			}
		}

		// operator = requestPurchaseService.getOperator(dto);
		// bug 10483 2023-04-20 B91717 请购模块相关操作，处理人变更为从前端获取
		operator = opsPurchaseorder.getOperator();
		// 从数据库中查询此采购单
		OpsPurchaseorderExample orderexample = new OpsPurchaseorderExample();
		if (opsPurchaseorder.getSplititemno() != null) {
			orderexample.createCriteria().andOrdernoEqualTo(opsPurchaseorder.getOrderno())
					.andItemnoEqualTo(opsPurchaseorder.getItemno())
					.andSplititemnoEqualTo(opsPurchaseorder.getSplititemno());
		} else {
			orderexample.createCriteria().andOrdernoEqualTo(opsPurchaseorder.getOrderno())
					.andItemnoEqualTo(opsPurchaseorder.getItemno()).andSplititemnoIsNull();
		}
		List<OpsPurchaseorder> order = opsPurchaseorderMapper.selectByExample(orderexample);
		if (CollectionUtils.isEmpty(order)) {
			throw Exceptions.OpsException("未查到此采购单,请刷新后重试！");
		}
		OpsPurchaseorder orderNow = order.get(0);
		orderNow.setSupplierid(opsPurchaseorder.getSupplierid());
		orderNow.setTranstype(opsPurchaseorder.getTranstype());
		orderNow.setHopeexportdate(opsPurchaseorder.getHopeexportdate());
		orderNow.setGreencode(opsPurchaseorder.getGreencode());
		orderNow.setInformation(opsPurchaseorder.getInformation());
		orderNow.setOperator(opsPurchaseorder.getOperator());

		// 若为合并采购则维持原来方法，直接写入采购流程中
		if (orderNow.getMergeflag()) {
			// 若为合并采购则维持原来方法，直接写入采购流程中
			return updateTrans_old(orderNow, operator);
		}
		// 获取请购表
		OpsRequestpurchaseExample e = new OpsRequestpurchaseExample();
		if (opsPurchaseorder.getSplititemno() != null) {
			e.createCriteria().andOrdernoEqualTo(opsPurchaseorder.getOrderno())
					.andItemnoEqualTo(opsPurchaseorder.getItemno())
					.andSplititemnoEqualTo(opsPurchaseorder.getSplititemno());
		} else {
			// bug 12852,拆分单号为空时，增加 isnull条件
			e.createCriteria().andOrdernoEqualTo(opsPurchaseorder.getOrderno())
					.andItemnoEqualTo(opsPurchaseorder.getItemno()).andSplititemnoIsNull();
		}
		List<OpsRequestpurchase> relist = opsRequestpurchaseMapper.selectByExample(e);
		if (relist == null || relist.size() == 0) {
			throw Exceptions.OpsException("转订失败！合并采购请删除后重订！");
		}
		OpsRequestpurchase re = relist.get(0);
		// bug 9555 增加操作者字段
		// OpsRequestpurchase updateRequest = new OpsRequestpurchase();
		// updateRequest.setId(re.getId());
		// updateRequest.setOperator(operator);
		// opsRequestpurchaseMapper.updateByPrimaryKeySelective(updateRequest);
		// 更新采购表，操作者
		// OpsPurchaseorder updatePurchase = new OpsPurchaseorder();
		// updatePurchase.setId(opsPurchaseorder.getId());
		// updatePurchase.setOperator(operator);
		// opsPurchaseorderMapper.updateByPrimaryKeySelective(updatePurchase);

		purchaseDeleteDao.insertRequest(re.getId(), RequestPurchaseStatusEnum.ZHAUNDINGSHANCHU, operator);
		opsRequestpurchaseMapper.deleteByPrimaryKey(re.getId());
		// 根据sql写入采购删除表
		purchaseDeleteDao.insertPurchase(orderNow.getId(), PurchaseOrderStatusEnum.ZHAUNDINGSHANCHU, operator);
		int result = opsPurchaseorderMapper.deleteByPrimaryKey(orderNow.getId());
		if (result == 1) {
			PoToWmDto temp = new PoToWmDto();
			temp.setPurchase(orderNow);
			temp.setRequests(relist);
			opsWmDispatchForOrderFeignApi.resetForPo(temp);
		}
		if (!orderNow.getStatecode().equals(PurchaseOrderStatusEnum.DAICHULI)) {
			OpsPurchaseinvoiceExample example = new OpsPurchaseinvoiceExample();
			OpsPurchaseinvoiceExample.Criteria criteria = example.createCriteria();
			criteria.andOrdernoEqualTo(opsPurchaseorder.getOrderno()).andItemnoEqualTo(opsPurchaseorder.getItemno());
			if (opsPurchaseorder.getSplititemno() != null) {
				criteria.andSplititemnoEqualTo(opsPurchaseorder.getSplititemno());
			} else {
				// bug 12852,拆分单号为空时，增加 isnull条件
				criteria.andSplititemnoIsNull();
			}
			// 报错修改
			List<OpsPurchaseinvoice> invoice = opsPurchaseinvoiceMapper.selectByExample(example);
			if (invoice != null && invoice.size() > 0) {
				opsPurchaseinvoiceMapper.deleteByPrimaryKey(invoice.get(0).getId());
			}

		}
		// if (!OrderTypeEnum.BIN.equals(opsPurchaseorder.getOrdtype())) {
		// 重算收货仓、smccode
		re.setSupplierid(orderNow.getSupplierid());
		re.setHopeexportdate(orderNow.getHopeexportdate());
		re.setPurchasewarehouseid(orderNow.getReceivewarehouseid());
		re.setTranstype(orderNow.getTranstype());
		// bug9582 马腾
		if (!OrderTypeEnum.BIN.equals(orderNow.getOrdtype())) {
			re.setPurchasewarehouseid(null);
		}
		// bug9379马腾 清空smccode重算
		re.setSmccode(null);
		re = requestPurchasePreService.calWarehouseAndSmccode(re, true);
		// }
		// 新增一条请购
		re.setId(null);
		re.setStatecode(RequestPurchaseStatusEnum.CHULIZHONG);
		re.setRequesttime(new Date());
		// bug11841型号需要加*订货
		if (StringUtils.isNotBlank(orderNow.getGreencode())) {
			if (StringUtils.equals("1", orderNow.getGreencode())) {
				re.setProducttag(orderNow.getGreencode());
			} else if (StringUtils.equals("H", orderNow.getGreencode())) {
				re.setProducttag("0");
			}
		}
		// bug9582 马腾 如重算得到采购仓或国别代码为空，则转订失败
		if (StringUtils.isBlank(re.getPurchasewarehouseid())) {
			// throw Exceptions.OpsException("计算采购仓失败,不可转订此供应商！");
			// bug9844 如果没计算出来采购仓，则使用之前的采购仓
			re.setPurchasewarehouseid(orderNow.getReceivewarehouseid());
			re.setSmccode(orderNow.getSmccode());
		}
		// 设置默认smccode
		if (StringUtils.isBlank(re.getSmccode())) {
			if (StringUtils.equals("KGZ", re.getPurchasewarehouseid())) {
				re.setSmccode("9501211");
			} else if (StringUtils.equals("KSH", re.getPurchasewarehouseid())) {
				re.setSmccode("9501202");
			} else {
				re.setSmccode("9501200");
			}
		}
		// bug11002 转订 新增采购转订的原因 B91717
		if (StringUtils.isNotBlank(orderNow.getInformation())) {
			String information = "转定原因：".concat(orderNow.getInformation());
			re.setInformation(
					StringUtils.isEmpty(re.getInformation()) ? information : re.getInformation().concat(information));
		}
		// bug13662 清除请购表中采购单号字段值
		re.setPoOrderNo(null);
		re.setPoItemNo(null);
		re.setPoSplitNo(null);
		int results = opsRequestpurchaseMapper.insertSelective(re);
		if (results < 1) {
			throw Exceptions.OpsException("转订失败！");
		}
		return results;
	}

	/**
	 * 更新交货期，只限供应商为日本的
	 */
	@Override
	public Integer updateDeliveryDate(OpsPurchaseorder opsPurchaseorder) throws OpsException {
		// bug 9555 增加操作者字段
		// operator = requestPurchaseService.getOperator(dto);
		// bug 10483 2023-04-20 B91717 请购模块相关操作，处理人变更为从前端获取
		operator = opsPurchaseorder.getOperator();
		// 更新交货期
		OpsPurchaseorder opsPurchaseorderNew = new OpsPurchaseorder();
		opsPurchaseorderNew.setId(opsPurchaseorder.getId());
		opsPurchaseorderNew.setHopedeliverydate(opsPurchaseorder.getHopedeliverydate());
		opsPurchaseorderNew.setOperator(operator);
		int result = opsPurchaseorderMapper.updateByPrimaryKeySelective(opsPurchaseorderNew);
		if (result < 1) {
			throw Exceptions.OpsException("更新交货期失败！");
		}
		return result;
	}

	@Override
	public OpsPurchaseorder getByid(Long id) {
		OpsPurchaseorder result = opsPurchaseorderMapper.selectByPrimaryKey(id);
		return result;
	}

	@Override
	public List<OpsRequestpurchase> getRequest(Long id) throws OpsException {
		// 根据ID查找采购信息
		OpsPurchaseorder result = opsPurchaseorderMapper.selectByPrimaryKey(id);
		if (result == null) {
			return null;
		}
		// 定义请购单查询
		OpsRequestpurchaseExample opsRequestpurchaseExample = new OpsRequestpurchaseExample();
		// 定义返回的请购单集合
		List<OpsRequestpurchase> requestPurchaseList = new ArrayList<>();
		// bug13662 请购表新增采购单号 废除合并表
		OpsRequestpurchaseExample.Criteria criteria = opsRequestpurchaseExample.createCriteria();
		criteria.andPoOrderNoEqualTo(result.getOrderno());
		if (result.getItemno() != null) {
			criteria.andPoItemNoEqualTo(result.getItemno());
		}
		if (result.getSplititemno() != null) {
			criteria.andPoSplitNoEqualTo(result.getSplititemno());
		} else {
			criteria.andPoSplitNoIsNull();
		}
		requestPurchaseList = opsRequestpurchaseMapper.selectByExample(opsRequestpurchaseExample);
		if (CollectionUtils.isEmpty(requestPurchaseList)) {
			throw Exceptions.OpsException("无对应请购单数据，请重试");
		}
		// bug 11058 采购详情显示根据数量进行排序
		requestPurchaseList = requestPurchaseList.stream().sorted(Comparator.comparing(OpsRequestpurchase::getQuantity))
				.collect(Collectors.toList());
		return requestPurchaseList;
	}

	/**
	 * 强制完纳功能
	 */
	@Override
	public void compel(OpsPurchaseorder opsPurchaseorder) throws OpsException {
		// 更新交货期
		OpsPurchaseorder opsPurchaseorderNew = new OpsPurchaseorder();
		opsPurchaseorderNew.setId(opsPurchaseorder.getId());
		opsPurchaseorderNew.setStatecode(PurchaseOrderStatusEnum.QIANGZHIWANNA);
		opsPurchaseorderNew.setUpdatetime(new Date());
		opsPurchaseorderMapper.updateByPrimaryKeySelective(opsPurchaseorderNew);
	}

    /**
     * 查询到货信息
     */
    @Override
    public List<ImpDataDto> getGoodsDetailByOrder(PoOrderNoDto param) throws OpsException {
        // 根据采购信息查询到货信息
        OpsImpdataExample opsImpdataExample = new OpsImpdataExample();
        OpsImpdataExample.Criteria criteria = opsImpdataExample.createCriteria();
        criteria.andOrdernoEqualTo(param.getOrderno()).andItemnoEqualTo(param.getItemno());
        // bug 12852 采购详情界面的到货信息查询有误,拆分单号条件的判断
        if (param.getSplititemno() == null) {
            criteria.andSplititemnoIsNull();
        } else {
            criteria.andSplititemnoEqualTo(param.getSplititemno());
        }
        List<OpsImpdata> opsImpdataList = opsImpdataMapper.selectByExample(opsImpdataExample);
        List<ImpDataDto> impDataDtos = new ArrayList<>();

        if(!CollectionUtils.isEmpty(opsImpdataList)){
            for(OpsImpdata impdata : opsImpdataList){
                ImpDataDto impDataDto = new ImpDataDto();
                TransferInfo transfer = splitSmcCodeDao.getTransferInfoInvoiceId(impdata.getInvoiceid());
                BeanCopyUtil.copy(impdata,impDataDto);
                if(Objects.nonNull(transfer)){
                    impDataDto.setTransferCarried(transfer.getCarried());
                    impDataDto.setTransferExpressCode(transfer.getExpressCode());
                    if(transfer.getLogisticsStatus() == 1){
                        impDataDto.setTransferStatus("转运仓已签收");
                    }
                    if(transfer.getLogisticsStatus() == 2){
                        impDataDto.setTransferStatus("转运仓已发运");
                    }
                    if(transfer.getLogisticsStatus() == 3){
                        impDataDto.setTransferStatus("目的仓已收货");
                    }
                }
                impDataDtos.add(impDataDto);
            }

        }
        return impDataDtos;
    }

	/**
	 * 查询到货信息
	 */
	@Override
	public List<ImpDataDto> getGoodsDetail(Long id) throws OpsException {
		// 根据ID查找采购信息
		OpsPurchaseorder opsPurchaseorder = opsPurchaseorderMapper.selectByPrimaryKey(id);
		if (opsPurchaseorder == null) {
			return null;
		}
		// 根据采购信息查询到货信息
		OpsImpdataExample opsImpdataExample = new OpsImpdataExample();
		OpsImpdataExample.Criteria criteria = opsImpdataExample.createCriteria();
		criteria.andOrdernoEqualTo(opsPurchaseorder.getOrderno()).andItemnoEqualTo(opsPurchaseorder.getItemno());
		// bug 12852 采购详情界面的到货信息查询有误,拆分单号条件的判断
		if (opsPurchaseorder.getSplititemno() == null) {
			criteria.andSplititemnoIsNull();
		} else {
			criteria.andSplititemnoEqualTo(opsPurchaseorder.getSplititemno());
		}
		List<OpsImpdata> opsImpdataList = opsImpdataMapper.selectByExample(opsImpdataExample);
        List<ImpDataDto> impDataDtos = new ArrayList<>();

        if(!CollectionUtils.isEmpty(opsImpdataList)){
            for(OpsImpdata impdata : opsImpdataList){
                ImpDataDto impDataDto = new ImpDataDto();
                TransferInfo transfer = splitSmcCodeDao.getTransferInfoInvoiceId(impdata.getInvoiceid());
                BeanCopyUtil.copy(impdata,impDataDto);
                if(Objects.nonNull(transfer)){
                    impDataDto.setTransferCarried(transfer.getCarried());
                    impDataDto.setTransferExpressCode(transfer.getExpressCode());
                    if(transfer.getLogisticsStatus() == 1){
                        impDataDto.setTransferStatus("转运仓已签收");
                    }
                    if(transfer.getLogisticsStatus() == 2){
                        impDataDto.setTransferStatus("转运仓已发运");
                    }
                    if(transfer.getLogisticsStatus() == 3){
                        impDataDto.setTransferStatus("目的仓已收货");
                    }
                }
                impDataDtos.add(impDataDto);
            }

        }
		return impDataDtos;
	}

	/**
	 * 手工模拟调用api接口
	 */
	@Override
	public CommonResult<String> apiRepo(Map<String, Object> list) throws OpsException {
		Map<String, Object> maps = list;
		List<PurchaseReplyInfo> apiList = new ArrayList<>();
		PurchaseReplyInfo purchaseReplyInfo = new PurchaseReplyInfo();

		// purchaseReplyInfo.setPono(maps.get("pono").toString());
		// purchaseReplyInfo.setLineitem(Integer.parseInt(maps.get("lineitem").toString()));
		// bug 10280,采购手工接单时，接口传输改用 orderno+itemno+splititemno
		purchaseReplyInfo.setOrderno(maps.get("orderno").toString());
		purchaseReplyInfo.setItemno(Integer.parseInt(maps.get("itemno").toString()));
		// splitNo判空
		if (maps.get("splititemno") != null) {
			purchaseReplyInfo.setSplitno(Integer.parseInt(maps.get("splititemno").toString()));
		}
		if (maps.containsKey("supplierid")) {
			purchaseReplyInfo.setSupplierid(maps.get("supplierid").toString());
		}
		if (maps.containsKey("receivewarehouseid")) {
			purchaseReplyInfo.setWarehouseid(maps.get("receivewarehouseid").toString());
		}
		if (maps.containsKey("modelno")) {
			purchaseReplyInfo.setModelno(maps.get("modelno").toString());
		}
		purchaseReplyInfo.setReplyorderno(maps.get("replyorderno").toString());
		purchaseReplyInfo.setReplyorderdate(new Date());
		apiList.add(purchaseReplyInfo);
		// purchaseApiService.updateInvoice(apiList);
		// 改为用wm的服务
		// bug 8938,从wm服务调用接口返回成功失败
		CommonResult<String> result= wmPurchaseFeignApi.updateInvoice(apiList);

		// bug10483，校验是否接单成功，成功的话：更新采购表的操作人字段
		if (result.getCode().equals(ResultCode.SUCCESS.code())) {
			PurchaseReplyInfo replyInfo = purchaseReplyInfo;
			OpsPurchaseorder purchase = basePoService.getPurchase(replyInfo.getOrderno(), replyInfo.getItemno(), replyInfo.getSplitno());
			//如果采购单状态不是已结单，则返回失败并提示错误信息
			if (!PurchaseOrderStatusEnum.YIJIEDAN.equals(purchase.getStatecode())) {
				String data = result.getData();
				if (JSONUtil.isJson(data)) {
					HashMap<String, String> map = JSONUtil.toBean(data, HashMap.class);
					String msg = "";
					for (Map.Entry<String, String> entry : map.entrySet()) {
						msg += entry.getKey() + ":" + entry.getValue() + " ";
					}
					return CommonResult.failure(msg);
				}
				return CommonResult.failure(data);
			}
			// 更新operator
			String operator = "";
			if (maps.containsKey("operator")) {
				operator = maps.get("operator").toString();
			}
			// 1.更新Purchaseinvoice表的操作人
			OpsPurchaseinvoice purchaseinvoice = new OpsPurchaseinvoice();
			purchaseinvoice.setUpdatetime(new Date());
			if (StringUtils.isNotBlank(operator)) {
				purchaseinvoice.setOperatorid(operator);
			}
			OpsPurchaseinvoiceExample example = new OpsPurchaseinvoiceExample();
			example.createCriteria().andPonoEqualTo(maps.get("pono").toString())
					.andLineitemEqualTo(Integer.valueOf(maps.get("lineitem").toString()));
			int results = opsPurchaseinvoiceMapper.updateByExampleSelective(purchaseinvoice, example);
			if (results > 0) {
				// 2.查询接单号、子项号、拆分单号
				List<OpsPurchaseinvoice> opsPurchaseinvoiceList = opsPurchaseinvoiceMapper.selectByExample(example);
				if (!CollectionUtils.isEmpty(opsPurchaseinvoiceList)) {
					// 3.更新purchaseorder表的操作人
					OpsPurchaseorderExample opsPurchaseorderExample = new OpsPurchaseorderExample();
					OpsPurchaseorderExample.Criteria mapCriteria = opsPurchaseorderExample.createCriteria();
					mapCriteria.andOrdernoEqualTo(opsPurchaseinvoiceList.get(0).getOrderno())
							.andItemnoEqualTo(opsPurchaseinvoiceList.get(0).getItemno());
					if (opsPurchaseinvoiceList.get(0).getSplititemno() != null) {
						mapCriteria.andSplititemnoEqualTo(opsPurchaseinvoiceList.get(0).getSplititemno());
					} else {
						// bug 12852,拆分单号为空时，增加 isnull条件
						mapCriteria.andSplititemnoIsNull();
					}
					OpsPurchaseorder purchaseorder = new OpsPurchaseorder();
					purchaseorder.setUpdatetime(new Date());
					if (StringUtils.isNotBlank(operator)) {
						purchaseorder.setOperator(operator);
					}
					opsPurchaseorderMapper.updateByExampleSelective(purchaseorder, opsPurchaseorderExample);
				}
			}
		}
		return result;
	}

	@Override
	public List<DlvModDateDto> getDlvModDate(String orderNo, Integer itemNo, Integer splitItemNo) {
		OpsPurchaseinvoiceExample example = new OpsPurchaseinvoiceExample();
		OpsPurchaseinvoiceExample.Criteria criteria = example.createCriteria();
		criteria.andOrdernoEqualTo(orderNo).andItemnoEqualTo(itemNo);
		if (splitItemNo != null) {
			criteria.andSplititemnoEqualTo(splitItemNo);
		} else {
			// bug 12852,拆分单号为空时，增加 isnull条件
			criteria.andSplititemnoIsNull();
		}
		List<OpsPurchaseinvoice> opsPurchaseinvoiceList = opsPurchaseinvoiceMapper.selectByExample(example);
		if (opsPurchaseinvoiceList.size() == 1) {
			List<DlvModDateDto> list = new ArrayList<>();
			OpsPurchaseinvoice purchase = opsPurchaseinvoiceList.get(0);
			if (purchase.getDlvmoddate1() != null || purchase.getDlvmoddate1time() != null) {
				DlvModDateDto item = new DlvModDateDto();
				// bug13111 特殊日期翻译
				item.setDlvModDateStr(PoReplyDateStrEnum.getDescByCode(purchase.getDlvmoddate1()).getCode());
				item.setDlvModDate(purchase.getDlvmoddate1());
				item.setUpdateTime(purchase.getDlvmoddate1time());
				item.setReasonremark(purchase.getReasonremark());
				list.add(item);
			}
			if (purchase.getDlvmoddate2() != null || purchase.getDlvmoddate2time() != null) {
				DlvModDateDto item = new DlvModDateDto();
				// bug13111 特殊日期翻译
				item.setDlvModDateStr(PoReplyDateStrEnum.getDescByCode(purchase.getDlvmoddate2()).getCode());
				item.setDlvModDate(purchase.getDlvmoddate2());
				item.setUpdateTime(purchase.getDlvmoddate2time());
				item.setReasonremark(purchase.getReasonremark());
				list.add(item);
			}
			if (purchase.getDlvmoddate3() != null || purchase.getDlvmoddate3time() != null) {
				DlvModDateDto item = new DlvModDateDto();
				// bug13111 特殊日期翻译
				item.setDlvModDateStr(PoReplyDateStrEnum.getDescByCode(purchase.getDlvmoddate3()).getCode());
				item.setDlvModDate(purchase.getDlvmoddate3());
				item.setUpdateTime(purchase.getDlvmoddate3time());
				item.setReasonremark(purchase.getReasonremark());
				list.add(item);
			}
			return list;
		}
		return null;
	}

	/**
	 * @param request
	 * @param id
	 * @return 2022-11-03 B91717 请购编辑页面，更新供应商，smccode等信息
	 * @author B91717
	 */
	@Override
	public Integer updateRequestEdit(OpsRequestpurchase request, Long id) throws OpsException {
		// 构造更新example
		OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
		example.createCriteria().andIdEqualTo(id);
		int result = opsRequestpurchaseMapper.updateByExampleSelective(request, example);
		if (result < 1) {
			throw Exceptions.OpsException("请购编辑,更新失败！");
		}
		return result;
	}
}
