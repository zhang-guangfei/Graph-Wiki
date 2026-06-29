package com.sales.ops.serviceimpl.purchase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.PoMergeRuleConfigDao;
import com.sales.ops.db.extdao.RequestPurchaseDao;
import com.sales.ops.dto.po.PoMergeRuleConfigDto;
import com.sales.ops.dto.purchase.*;
import com.sales.ops.dto.query.RequestPurchaseQO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.enums.*;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.feign.OPSProductFeignApi;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.sales.ops.service.purchase.*;
import com.sales.ops.serviceimpl.event.v3.PurchaseEventPublisher;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.bindata.BindataRequest;
import com.smc.smccloud.model.bindata.BindataVO;
import com.smc.smccloud.service.BinServiceFeignApi;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class RequestPurchaseServiceImpl implements RequestPurchaseService {

	private final static Logger logger = LoggerFactory.getLogger(RequestPurchaseServiceImpl.class);

	@Autowired
	private OpsRequestpurchaseMapper opsRequestpurchaseMapper;

	@Autowired
	private RequestPurchaseDao requestPurchaseDao;

	@Autowired
	private SupplierMapper supplierMapper;

	@Autowired
	private OpsPoTranstypeMapper opsPoTranstypeMapper;

	@Autowired
	private OpsPurchaseorderMapper opsPurchaseorderMapper;

	@Autowired
	private BinServiceFeignApi binServiceFeignApi;

	@Autowired
	private OPSProductFeignApi opsProductFeignApi;

	@Autowired
	private RequestPurchasePreService requestPurchasePreService;

	@Autowired
	private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;

	@Autowired
	private RequestpurchaseViewMapper requestpurchaseViewMapper;

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private OpsPoEventLogMapper opsPoEventLogMapper;

	@Autowired
	private PurchaseAutoService purchaseAutoService;

	@Autowired
	private OpsCustomerPropertyMapper opsCustomerPropertyMapper;

	@Autowired
	private PurchaseEventPublisher purchaseEventPublisher;

	@Autowired
	private DictDataServiceFeignApi dictDataServiceFeignApi;

	@Autowired
	private SpecialOrderNoService specialOrderNoService;

	@Autowired
	private PoMergeRuleConfigDao poMergeRuleConfigDao;

    @Autowired
    private SplitSmcCodeService splitSmcCodeService;

	// bug11473 自动发单
	public void autoSend() throws Exception {
		purchaseAutoService.purchaseAutoJP(false);
		purchaseAutoService.purchaseAutoGZ(false);
		purchaseAutoService.purchaseAutoManufacture(false);
		purchaseAutoService.purchaseAutoOverSea(false);
	}

	@Override
	// 返回全部的请购单数据,支持条件查询
	public PageInfo<OpsRequestpurchase> findAll(PageModel<RequestPurchaseQO> pageModel) {
		PageInfo<OpsRequestpurchase> pageInfo = new PageInfo<OpsRequestpurchase>();

		OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
		OpsRequestpurchaseExample.Criteria criteria = example.createCriteria();
		// 筛选状态为待处理状态的
		criteria.andStatecodeEqualTo(RequestPurchaseStatusEnum.CHULIZHONG);
		if (StringUtils.isNotBlank(pageModel.getOrderBy())) {
			// 是否区分按7位单号进行排序
			// if (pageModel.getOrderBy().contains("orderno")){
			// String orderBySend =
			// pageModel.getOrderBy().substring(pageModel.getOrderBy().length()-4);
			// example.setOrderByClause(pageModel.getOrderBy()+",itemNo
			// "+orderBySend+",splitItemNo "+orderBySend );
			// }
			// else {
			example.setOrderByClause(pageModel.getOrderBy());
			// }
		}
		if (pageModel.getCondition() == null) {
			pageInfo = PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize())
					.doSelectPageInfo(() -> opsRequestpurchaseMapper.selectByExample(example));
			return pageInfo;
		}
		RequestPurchaseQO condition = pageModel.getCondition();
		if (StringUtils.isNotBlank(condition.getOrderno())) {
			String pono = condition.getOrderno();
			if (condition.getOrderno().contains("-")) {
				String[] split = pono.split("-");
				criteria.andOrdernoEqualTo(split[0]);
				criteria.andItemnoEqualTo(Integer.parseInt(split[1]));
				if (split.length == 3) {
					criteria.andSplititemnoEqualTo(Integer.parseInt(split[2]));
				}
			} else {
				criteria.andOrdernoEqualTo(condition.getOrderno());
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
		// if (StringUtils.isNotBlank(condition.getSupplierid())) {
		// criteria.andSupplieridEqualTo(condition.getSupplierid());
		// }
		// bug7538,调整请购处理，供应商可以多列选择
		if (!CollectionUtils.isEmpty(condition.getSupplierids())) {
			criteria.andSupplieridIn(condition.getSupplierids());
		}
		if (StringUtils.isNotBlank(condition.getTranstype())) {
			criteria.andTranstypeEqualTo(condition.getTranstype());
		}
		if (!CollectionUtils.isEmpty(condition.getDeptNos())) {
			criteria.andDeptnoIn(condition.getDeptNos());
		}

		if (StringUtils.isNotBlank(condition.getOrdtype())) {
			criteria.andOrdtypeEqualTo(condition.getOrdtype());
		}
		if (StringUtils.isNotBlank(condition.getPurchasewarehouseid())) {
			criteria.andPurchasewarehouseidEqualTo(condition.getPurchasewarehouseid());
		}
		pageInfo = PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize())
				.doSelectPageInfo(() -> opsRequestpurchaseMapper.selectByExample(example));
		return pageInfo;

	}

	@Override
	// 返回全部的请购单数据,支持条件查询
	public PageInfo<RequestpurchaseView> findPreProccessList(PageModel<RequestPurchaseQO> pageModel) {
		PageInfo<RequestpurchaseView> pageInfo = new PageInfo<RequestpurchaseView>();
		RequestpurchaseViewExample example = new RequestpurchaseViewExample();
		RequestpurchaseViewExample.Criteria criteria = example.createCriteria();
		// 筛选状态为待处理状态的
		// criteria.andStatecodeEqualTo(RequestPurchaseStatusEnum.DAICHULI);
		if (StringUtils.isNotBlank(pageModel.getOrderBy())) {
			// 是否区分按7位单号进行排序
			example.setOrderByClause(pageModel.getOrderBy());
		}
		if (pageModel.getCondition() == null) {
			pageInfo = PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize())
					.doSelectPageInfo(() -> requestpurchaseViewMapper.selectByExample(example));
			return pageInfo;
		}
		RequestPurchaseQO condition = pageModel.getCondition();
		if (StringUtils.isNotBlank(condition.getOrderno())) {
			String pono = condition.getOrderno();
			if (condition.getOrderno().contains("-")) {
				String[] split = pono.split("-");
				criteria.andOrdernoEqualTo(split[0]);
				criteria.andItemnoEqualTo(Integer.parseInt(split[1]));
				if (split.length == 3) {
					criteria.andSplititemnoEqualTo(Integer.parseInt(split[2]));
				}
			} else {
				criteria.andOrdernoEqualTo(condition.getOrderno());
			}
		}
		if (StringUtils.isNotBlank(condition.getStatecode())) {
			criteria.andStatecodeEqualTo(condition.getStatecode());
		}
		if (StringUtils.isNotBlank(condition.getModelno())) {
			criteria.andModelnoLike(condition.getModelno() + "%");
		}
		if (StringUtils.isNotBlank(condition.getPurchasetype())) {
			criteria.andPurchasetypeEqualTo(condition.getPurchasetype());
		}
		if (StringUtils.isNotBlank(condition.getSupplierid())) {
			criteria.andSupplieridEqualTo(condition.getSupplierid());
		}
		if (StringUtils.isNotBlank(condition.getTranstype())) {
			criteria.andTranstypeEqualTo(condition.getTranstype());
		}
		if (!CollectionUtils.isEmpty(condition.getDeptNos())) {
			criteria.andDeptnoIn(condition.getDeptNos());
		}
		if (StringUtils.isNotBlank(condition.getOrdtype())) {
			criteria.andOrdtypeEqualTo(condition.getOrdtype());
		}
		if (StringUtils.isNotBlank(condition.getPurchasewarehouseid())) {
			criteria.andPurchasewarehouseidEqualTo(condition.getPurchasewarehouseid());
		}
		// bug14609 WTSR2024000608-添加海外订单导出数据的新需求,增加批次号\采购单号的筛选条件
		if (StringUtils.isNotBlank(condition.getSendversion())) {
			criteria.andSendversionEqualTo(condition.getSendversion());
		}
		if (StringUtils.isNotBlank(condition.getPoOrderNo())) {
			String purchaseNo = condition.getPoOrderNo();
			if (condition.getPoOrderNo().contains("-")) {
				String[] split = purchaseNo.split("-");
				criteria.andPoOrderNoEqualTo(split[0]);
				criteria.andPoItemNoEqualTo(Integer.parseInt(split[1]));
				if (split.length == 3) {
					criteria.andPoSplitNoEqualTo(Integer.parseInt(split[2]));
				}
			} else {
				criteria.andPoOrderNoEqualTo(condition.getPoOrderNo());
			}
		}
		// 判断开始日期和结束日期
		if (condition.getRequesTimeStart() != null || condition.getRequesTimeEnd() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(condition.getRequesTimeEnd());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
			cal.add(Calendar.DATE, 1);
			condition.setRequesTimeEnd(cal.getTime());
			criteria.andRequesttimeBetween(condition.getRequesTimeStart(), condition.getRequesTimeEnd());
		}
		// example.setOrderByClause("requesttime desc");
		pageInfo = PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize())
				.doSelectPageInfo(() -> requestpurchaseViewMapper.selectByExample(example));
		return pageInfo;
	}

	/**
	 * 初始合并采购方法 合并采购的方法
	 */
	@Override
	public JSONObject mergePurchase() throws Exception {
		JSONObject ret = new JSONObject();
		List<OpsRequestpurchaseDto> completionList = new ArrayList<OpsRequestpurchaseDto>();
		// 查询待采购的请购单数据，为下一步合并做准备
		// 查询所有请购单数据
		OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
		OpsRequestpurchaseExample.Criteria opsRequestCriteria = example.createCriteria();
		opsRequestCriteria.andStatecodeEqualTo(RequestPurchaseStatusEnum.DAICAIGOU);
		// opsRequestCriteria.andPurchasetypeIn(RequestMergeEnum.purchasetypeList());
		// opsRequestCriteria.andOrdtypeNotIn(RequestMergeEnum.orderTypeList());
		// opsRequestCriteria.andSpecmarkNotIn(RequestMergeEnum.specmarkList());
		// 查询符合条件的请购单
		List<OpsRequestpurchase> requestPurchaseList = opsRequestpurchaseMapper.selectByExample(example);
		// 空集合判断
		if (CollectionUtils.isEmpty(requestPurchaseList)) {
			// 预留空返回值异常
			ret.put("onedata", completionList);
			ret.put("mergedata", completionList);
			return ret;
			// throw Exceptions.OpsException("暂无符合条件的请购数据，请稍后重试");
		}
		// maxIndex = requestPurchaseList.size() >= maxIndex ? maxIndex :
		// requestPurchaseList.size();
		// requestPurchaseList = requestPurchaseList.subList(0, maxIndex);

		// bug11473自动发单 提出公共可用方法
		ret = operateCalMerge(requestPurchaseList);
		// 2022-06-03,只截取前2000条数据，防止页面数据太多。
		int maxIndex = Integer.parseInt(RequestMergeEnum.MAXVIEWSIZE.getCode());
		if (ret.get("onedata") != null) {
			List<OpsRequestpurchaseDto> temp = JSON.parseArray(JSON.toJSONString(ret.get("onedata")),
					OpsRequestpurchaseDto.class);
			if (!CollectionUtils.isEmpty(temp)) {
				ret.put("onedata", temp.subList(0, temp.size() >= maxIndex ? maxIndex : temp.size()));
			}
		}

		return ret;
	}

	/**
	 * bugid：17619
	 * 更新合并采购配置表
	 * @param list
	 * @throws OpsException
	 */
	@Override
	public void updateMergeConfig(List<PoMergeRuleConfigDto> list) throws OpsException{
		for(PoMergeRuleConfigDto obj : list){
			// 验证非空
			if(StringUtils.isBlank(obj.getRuleParam())){
				throw Exceptions.OpsException("配置错误，参数不可为空");
			}
			// 去空格
			obj.setRuleParam(obj.getRuleParam().trim());
			//验证数据
			try {
				Integer.parseInt(obj.getRuleParam());
			} catch (NumberFormatException e) {
				throw Exceptions.OpsException("配置错误，需要填写数字");
			}
		}
		//更新数据
		for(PoMergeRuleConfigDto obj : list){
			poMergeRuleConfigDao.updateMergeConfig(obj.getRuleParam(),obj.getModifier(),obj.getRuleCode());
		}
	}

	/**
	 * bugid:17619
	 * 获取合并采购配置信息
	 * @return
	 */
	@Override
	public List<PoMergeRuleConfigDto> getMergeConfig(){
		return poMergeRuleConfigDao.getPoMergeRuleConfigListByMIDQTYAndMINCOUNT();
	}

	/**
	 * bugid:17619
	 * @return
	 * @throws OpsException
	 */
	@Override
	public HashMap<String,Integer> getMergeConfigMap() throws OpsException{
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		List<PoMergeRuleConfigDto> mergeConfigList = getMergeConfig();
		if(CollectionUtils.isEmpty(mergeConfigList)){
			throw Exceptions.OpsException("合并采购配置表错误-ops_po_merge_rule_config缺失");
		}
		for(PoMergeRuleConfigDto obj : mergeConfigList){
			if(StringUtils.isBlank(obj.getRuleParam())){
				throw Exceptions.OpsException("合并采购配置表错误-ops_po_merge_rule_config--MIDQTY--MINCOUNT");
			}
			int param = 0;
			try {
				param = Integer.parseInt( obj.getRuleParam());
			} catch (NumberFormatException e) {
				throw Exceptions.OpsException("合并采购配置表错误-ops_po_merge_rule_config--MIDQTY--MINCOUNT");
			}
			map.put(obj.getRuleCode(),param);
		}
		return map;
	}




	@Override
	public JSONObject operateCalMerge(List<OpsRequestpurchase> requestPurchaseList) throws OpsException{
		//bugid:17619 获取配置文件
		HashMap<String, Integer> mergeConfigMap = getMergeConfigMap();
		JSONObject ret = new JSONObject();
		// 定义第二次维度筛选的数据集合
		List<OpsRequestpurchaseDto> quantityList = new ArrayList<>();
		// 定义完整的返回数据集
		List<OpsRequestpurchaseDto> completionList = new ArrayList<OpsRequestpurchaseDto>();
		// 定义多段价格可以合并的，供应商清单
		Set<String> lotSuppilyList = new HashSet<String>(RequestMergeEnum.lotSuppilyList());
		// 转换为前端需要的DTO
		List<OpsRequestpurchaseDto> requestList = new ArrayList<OpsRequestpurchaseDto>();
		requestPurchaseList.stream().forEach(o -> {
			try {
				OpsRequestpurchaseDto opsRequestpurchaseDto = new OpsRequestpurchaseDto();
				opsRequestpurchaseDto = (OpsRequestpurchaseDto) PoToDto(o, opsRequestpurchaseDto);
				requestList.add(opsRequestpurchaseDto);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		// 从非BIN中筛选purchaseType为A和B的数据，客户订单和先行在库，进行下一步的合并采购推荐,其余订单不参与合并
		Set<String> purchasetypeList = new HashSet<String>(RequestMergeEnum.purchasetypeList());
		// 定义单条不合并的数组，取出剩余不合并数据
		List<OpsRequestpurchaseDto> oneDataList = requestList.stream()
				.filter(e -> !(purchasetypeList.contains(e.getPurchasetype()))).collect(Collectors.toList());
		// 定义后续参与合并的数组,筛选数据A和B的数据
		List<OpsRequestpurchaseDto> requestListFilterType = requestList.stream()
				.filter(e -> purchasetypeList.contains(e.getPurchasetype())).collect(Collectors.toList());

		// 2022-03-20 新增，需要在AB订单中 筛选订单类型不为bin补库的订单20
		// 2023-06-06 对应委托WTSR2023000492，新增修改 委托在库订单（ordtype=3） 不参与合并 bug11058
		Set<String> orderTypeList = new HashSet<String>(RequestMergeEnum.orderTypeList());
		List<OpsRequestpurchaseDto> requestListMerge = requestListFilterType.stream()
				.filter(e -> !(orderTypeList.contains(e.getOrdtype()))).collect(Collectors.toList());
		// 取出不需要合并的
		// 2023-06-06 对应委托WTSR2023000492，新增修改 委托在库订单（ordtype=3） 不参与合并 bug11058
		List<OpsRequestpurchaseDto> binList = requestListFilterType.stream()
				.filter(e -> orderTypeList.contains(e.getOrdtype())).collect(Collectors.toList());
		// 追加非BIN的订单类型到请购表
		if (!CollectionUtils.isEmpty(binList)) {
			oneDataList.addAll(binList);
		}
		// 新规则，不需要再做bin判断，注释掉
		// 将非bin数据合并到单条不合并集合中
		// 首先判断是否有符合合并采购条件的数据,没有直接返回单条列表
		if (CollectionUtils.isEmpty(requestListMerge)) {
			ret.put("onedata", getOneData(oneDataList));
			return ret;
		}
		// 2021-10-22 有SHIKOMI号的不参与合并
		List<OpsRequestpurchaseDto> shikomiList = requestListMerge.stream()
				.filter(e -> StringUtils.isNotBlank(e.getShikomianswerno())).collect(Collectors.toList());
		// 按 ordType,ModelNo,Supplierid,Shikomi 进行分组,先进行最严格条件的分组
		if (!CollectionUtils.isEmpty(shikomiList)) {
			oneDataList.addAll(shikomiList);
		}
		// 筛选没有SHIKOMI号的进行后续合并操作
		quantityList = requestListMerge.stream().filter(e -> StringUtils.isBlank(e.getShikomianswerno()))
				.collect(Collectors.toList());

		// 筛选阀汇留板标识的,不进行合并
		List<String> specmark = RequestMergeEnum.specmarkList();
		List<OpsRequestpurchaseDto> specmarkList = quantityList.stream().filter(e -> specmark.contains(e.getSpecmark()))
				.collect(Collectors.toList());

		if (!CollectionUtils.isEmpty(specmarkList)) {
			oneDataList.addAll(specmarkList);
			// 筛选出 不为阀汇留板标识的 参与下面合并
			quantityList = quantityList.stream().filter(e -> !specmark.contains(e.getSpecmark()))
					.collect(Collectors.toList());
		}
		// 2023-06-06 对应委托WTSR2023000492，新增合并校验，对型号需要有式样书的 ，不判断合并 bug11058
		ProductExample productExample = new ProductExample();
		ProductExample.Criteria productExampleCriteria = productExample.createCriteria();
		productExampleCriteria.andManifoldsignEqualTo(RequestMergeEnum.MAINFOLDSIGN.getCode());
		// 取出样式书型号
		List<Product> productList = productMapper.selectByExample(productExample);
		if (!CollectionUtils.isEmpty(productList)) {
			// throw Exceptions.OpsException("型号式样书清单为空，请维护基础数据！");
			List<String> mainfoldsignList = productList.stream().map(a -> a.getManifoldsign())
					.collect(Collectors.toList());
			Set<String> mainfoldsignSet = new HashSet<String>(mainfoldsignList);
			// 筛选出列表中样式书型号 bug11058
			List<OpsRequestpurchaseDto> mainfoldsignDto = quantityList.stream()
					.filter(e -> mainfoldsignSet.contains(e.getModelno())).collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(specmarkList)) {
				oneDataList.addAll(mainfoldsignDto);
				// 筛选出 不是式样书的型号，参与下面合并
				quantityList = quantityList.stream().filter(e -> !mainfoldsignSet.contains(e.getModelno()))
						.collect(Collectors.toList());
			}
		}
		// 首先判断是否有符合合并采购条件的数据,没有直接返回单条列表
		if (CollectionUtils.isEmpty(quantityList)) {
			ret.put("onedata", getOneData(oneDataList));
			return ret;
		}
		// bug14330 AP订单合并后数量也不能超过固定值
		Integer maxnum = 200;
		ResultVo<List<DataCodeVO>> result = dictDataServiceFeignApi.getDataCodes("3011");
		if (result.isSuccess() && !CollectionUtils.isEmpty(result.getData())
				&& StringUtils.isNotBlank(result.getData().get(0).getExtNote1())) {
			maxnum = Integer.parseInt(result.getData().get(0).getExtNote1());
		}
		final int maxnumtemp = maxnum;
		// 对第二种情况进行分组，只取同类型订单、同供应商、同型号、同采购仓库
		// new 新增加同采购仓库合并
		Map<String, List<OpsRequestpurchaseDto>> suppilyMap = quantityList.stream()
				.collect(Collectors.groupingBy(item -> item.getPurchasetype() + "@" + item.getModelno() + "@"
						+ item.getSupplierid() + "@" + item.getPurchasewarehouseid()));

		suppilyMap.forEach((key, sList) -> {
			// 统计同型号的项数
			// int quantityMerge =
			// sList.stream().mapToInt(OpsRequestpurchaseDto::getQuantity).sum();
			// int itemSum = sList.size();
			String[] keys = key.split("@");
			// 取得供应商字段，用于判断是否为海外,型号判断是否为多段
			String suppilyId = keys[2];
			boolean isLot = sList.get(0).getIslot();

			if (sList.size() > 1) {
				// 2022-05-29 如果合并后的rosh不一致，不合并
				Long count = sList.stream().map(OpsRequestpurchaseDto::getProducttag).distinct().count();
				if (count != 1) {
					oneDataList.addAll(sList);
				} else {
					// bug13662
					// AP合并规则修改:采购订单供应商为AP、同批次、同客户、同型号、订单项数>=2项，订单数量<200
					if (StringUtils.equals("AP", suppilyId)) {
						// 同客户
						Map<String, List<OpsRequestpurchaseDto>> cMap = sList.stream()
								.collect(Collectors.groupingBy(OpsRequestpurchase::getCustomerno));
						cMap.forEach((ckey, cList) -> {
							List<OpsRequestpurchaseDto> qtyList = cList.stream().filter(e -> e.getQuantity() < 200)
									.sorted(Comparator.comparing(OpsRequestpurchaseDto::getQuantity))
									.collect(Collectors.toList());
							if (CollectionUtils.isEmpty(qtyList)) {
								oneDataList.addAll(cList);
							} else {
								if (qtyList.size() > 1) {
									// bug14330 AP订单合并后数量也不能超过固定值
									int num = 0;
									List<OpsRequestpurchaseDto> mergetemp = new ArrayList<OpsRequestpurchaseDto>();
									for (OpsRequestpurchaseDto d : qtyList) {
										num = num + d.getQuantity();
										if (num <= maxnumtemp) {
											mergetemp.add(d);
										} else {
											if (mergetemp.size() > 1) {
												completionList.addAll(getMergeData(mergetemp));
												mergetemp.clear();
												mergetemp.add(d);
												num = d.getQuantity();
											} else {
												if (!CollectionUtils.isEmpty(mergetemp)) {
													oneDataList.addAll(mergetemp);
													mergetemp.clear();
												}
												oneDataList.add(d);
											}
										}
									}
									if (mergetemp.size() > 1) {
										completionList.addAll(getMergeData(mergetemp));
									} else if (mergetemp.size() == 1) {
										oneDataList.add(mergetemp.get(0));
									}
									// 其余的数据加入单条的清单
									List<OpsRequestpurchaseDto> otherList = cList.stream()
											.filter(e -> e.getQuantity() >= 200).collect(Collectors.toList());
									if (CollectionUtils.isEmpty(qtyList)) {
										oneDataList.addAll(otherList);
									}

								} else {
									oneDataList.addAll(cList);
								}
							}
						});
					} else {
						if (isLot) {
							// 2023-06-06 对应委托WTSR2023000492，多段价格 只有供应商为CN CM
							// CT的不合并,其余正常合并 bug11058
							if (lotSuppilyList.contains(suppilyId)) {
								oneDataList.addAll(sList);
							} else {
								List<OpsRequestpurchaseDto> opsRequestpurchaseDtos = getMergeData(sList);
								// 2022-05-07 合并后的多段价格如果相同，则不合并
								if (opsRequestpurchaseDtos.get(0).getStdpriceMerge()
										.compareTo(opsRequestpurchaseDtos.get(0).getStdprice()) == 0) {
									oneDataList.addAll(sList);
								} else {
									completionList.addAll(opsRequestpurchaseDtos);
								}
							}
						} else {
							// 新规则同指定制造出库日的项数>10,则合并
							Map<Date, List<OpsRequestpurchaseDto>> dateMap = sList.stream()
									.collect(Collectors.groupingBy(OpsRequestpurchase::getHopeexportdate));
							dateMap.forEach((dateKey, dateList) -> {
								// 判断同出库日的项数 >10 AND QUANTITY <8
								// 2023-06-06
								// 对应委托WTSR2023000492，订单项数超过十项，且订单数量小于8个的参与合并,其余不参与合并
								// bug11058
								// 筛选订单数量小于8的集合，参与后续合并
								List<OpsRequestpurchaseDto> qtyList = dateList.stream().filter(
												e -> e.getQuantity() <= mergeConfigMap.get("MIDQTY"))//bugid: 17619 RequestMergeEnum.MIDQTY.getCode()
										.collect(Collectors.toList());
								if (CollectionUtils.isEmpty(qtyList)) {
									oneDataList.addAll(dateList);
								} else {
									if (qtyList.size() >= mergeConfigMap.get("MINCOUNT")) {//bugid: 17619 Integer.parseInt(RequestMergeEnum.MINCOUNT.getCode())
										// 项数超过10项 符合条件的数据加入合并清单 bug11058
										completionList.addAll(getMergeData(qtyList));
										// 其余的数据加入单条的清单
										List<OpsRequestpurchaseDto> otherList = dateList.stream()
												.filter(e -> e.getQuantity() > mergeConfigMap.get("MIDQTY"))//bugid: 17619 RequestMergeEnum.MIDQTY.getCode()
												.collect(Collectors.toList());
										if (CollectionUtils.isEmpty(qtyList)) {
											oneDataList.addAll(otherList);
										}
									} else {
										// 如果项数未超过10条，直接不合并 bug11058
										oneDataList.addAll(dateList);
									}
								}
							});
						}
					}
				}

			} else {
				oneDataList.addAll(sList);
			}
		});
		// 请求合并方法
		List<OpsRequestpurchaseDto> oneList = getOneData(oneDataList);
		if (!CollectionUtils.isEmpty(oneList)) {
			Map<String, Object> suppilyCount = listTotal(oneDataList);
			ret.put("totalCount", suppilyCount);
		}
		ret.put("onedata", oneList);
		ret.put("mergedata", completionList);
		return ret;
	}

	/**
	 * 按供应商分组，统计各个供应商的总项数
	 */
	public Map<String, Object> listTotal(List<OpsRequestpurchaseDto> list) {
		// 按照供应商进行分组
		Map<String, List<OpsRequestpurchaseDto>> requestMap = list.stream()
				.collect(Collectors.groupingBy(OpsRequestpurchaseDto::getSupplierid));
		Map<String, Object> suppiltTotal = new HashMap<String, Object>();
		suppiltTotal.put("CN", 0);
		suppiltTotal.put("JP", 0);
		suppiltTotal.put("Oversea", 0);
		// 获取中国的供应商列表
		List<String> cnList = requestPurchaseDao.getSuppilyByCountry("CN");
		// Map<String,Object> CNMap = new HashMap<String, Object>();
		// CNMap.put("CN",0);
		// Map<String,Object> JPMap = new HashMap<String, Object>();
		// JPMap.put("JP",0);
		// Map<String,Object> OverseaMap = new HashMap<String, Object>();
		// OverseaMap.put("Oversea",0);
		requestMap.forEach((key, sList) -> {
			int size = 0;
			if (cnList.contains(key)) {
				if (suppiltTotal.containsKey("CN")) {
					size = Integer.parseInt(suppiltTotal.get("CN").toString());
				}
				suppiltTotal.put("CN", sList.size() + size);
			} else if (key.equals("JP")) {
				if (suppiltTotal.containsKey("JP")) {
					size = Integer.parseInt(suppiltTotal.get("JP").toString());
				}
				suppiltTotal.put("JP", sList.size() + size);
			} else {
				if (suppiltTotal.containsKey("Oversea")) {
					size = Integer.parseInt(suppiltTotal.get("Oversea").toString());
				}
				suppiltTotal.put("Oversea", sList.size() + size);
			}
		});
		// suppiltTotal.add(CNMap);
		// suppiltTotal.add(JPMap);
		// suppiltTotal.add(OverseaMap);
		return suppiltTotal;
	}

	/**
	 * 不同制造交货期的请购单，合并后的以最近的为准，同样运输方式也参考该请购单的运输方式；
	 */
	public List<OpsRequestpurchaseDto> getMergeData(List<OpsRequestpurchaseDto> opsRequestpurchaseList) {
		if (CollectionUtils.isEmpty(opsRequestpurchaseList)) {
			return opsRequestpurchaseList;
			// throw Exceptions.OpsException("合并数据为空，请重试！");
		}
		// 按照出货期进行排序，合并后的以最近的为准，同样运输方式也参考该请购单的运输方式；
		opsRequestpurchaseList.sort(Comparator.comparing(OpsRequestpurchaseDto::getHopeexportdate));
		// 定义合并后的制造交货期以及运输方式
		Date hopeExportDateMerge = opsRequestpurchaseList.stream().findFirst().orElse(null).getHopeexportdate();
		String transTypeMerge = opsRequestpurchaseList.stream().findFirst().orElse(null).getTranstype();
		String inqnoMerge = opsRequestpurchaseList.stream().findFirst().orElse(null).getInqno();
		Date hopedeliverydateMerge = opsRequestpurchaseList.stream().findFirst().orElse(null).getHopedeliverydate();
		// 不同入库仓库，根据数量排序，项数最多的入库仓作为合并后的入库仓
		Map<String, List<OpsRequestpurchaseDto>> requestMap = opsRequestpurchaseList.stream()
				.collect(Collectors.groupingBy(OpsRequestpurchaseDto::getPurchasewarehouseid));
		// 定义合并后的入库仓库
		String purchaseWarehouseIdMerge = "";
		String smccodeMerge = "";
		if (requestMap.size() > 1) {
			// 定义统计合并数量
			int countSum = 0;
			for (Entry<String, List<OpsRequestpurchaseDto>> requestwarehouseid : requestMap.entrySet()) {
				int count = requestwarehouseid.getValue().stream().mapToInt(OpsRequestpurchaseDto::getQuantity).sum();
				if (count > countSum) {
					countSum = count;
					// requestWarehouseIdMerge =
					// requestwarehouseid.getValue().stream().findFirst().orElse(null).getRequestwarehouseid();
					purchaseWarehouseIdMerge = requestwarehouseid.getValue().stream().findFirst().orElse(null)
							.getPurchasewarehouseid();
					smccodeMerge = requestwarehouseid.getValue().stream().findFirst().orElse(null).getSmccode();
				}
			}
		} else {
			purchaseWarehouseIdMerge = opsRequestpurchaseList.stream().findFirst().orElse(null)
					.getPurchasewarehouseid();
			smccodeMerge = opsRequestpurchaseList.stream().findFirst().orElse(null).getSmccode();
		}
		// 取得合并后的总数量
		int quantityMerge = opsRequestpurchaseList.stream().mapToInt(OpsRequestpurchaseDto::getQuantity).sum();
		// 取得合并后的总重量
		BigDecimal weightMerge = new BigDecimal(0);
		if (opsRequestpurchaseList.get(0).getNetweight() != null) {
			weightMerge = opsRequestpurchaseList.get(0).getNetweight().multiply(new BigDecimal(quantityMerge));
		}

		// 计算合并后的价格
		BigDecimal priceMerge = new BigDecimal(0);
		BigDecimal fobMerge = new BigDecimal(0);
		if (opsRequestpurchaseList.get(0).getIslot()) {
			// 调用价格接口，获取多段价格，为了确认合并后的价格
			CommonResult<List<ProductPrice>> getPrice = opsProductFeignApi
					.findProductPriceByModelNo(opsRequestpurchaseList.get(0).getModelno());
			if (getPrice.isSuccess()) {
				List<ProductPrice> priceList = getPrice.getData();
				// 对多段价格进行倒叙排序，匹配合并后的数量区间。
				priceList.sort(Comparator.comparing(ProductPrice::getMinquantity, Comparator.reverseOrder()));
				for (ProductPrice productPrice : priceList) {
					if (priceMerge.equals(BigDecimal.ZERO)) {
						if (quantityMerge >= productPrice.getMinquantity()) {
							priceMerge = productPrice.getEprice();
							// 获取合并后的fob价
							fobMerge = productPrice.getImportfobpriceoriginal();
						}
					}
				}
			}
		} else {
			priceMerge = opsRequestpurchaseList.get(0).getStdprice();
			fobMerge = opsRequestpurchaseList.get(0).getImportfobpriceoriginal();
		}
		// String purchaseWarehouseIdMerges = purchaseWarehouseIdMerge;
		// BigDecimal stdPriceMerge = priceMerge;
		// BigDecimal fobPriceMerge = fobMerge;
		// String smccodeMergeFinal = smccodeMerge;

		// bug12284合并时考虑先行在库vip标识
		String json = null;
		for (OpsRequestpurchaseDto i : opsRequestpurchaseList) {
			if (StringUtils.isNotBlank(i.getInfojson())) {
				RequestPurchaseInfo info = JSONObject.parseObject(i.getInfojson(), RequestPurchaseInfo.class);
				if (info.isVip()) {
					json = i.getInfojson();
					break;
				}
			}
		}
		// 合并后客户使用vip客户的代码
		String customerNo = null;
		String userNo = null;
		String deptNo = null;
		String endUser = null;
		List<String> customerList = opsRequestpurchaseList.stream()
				.filter(s -> StringUtils.isNotBlank(s.getCustomerno())).map(OpsRequestpurchaseDto::getCustomerno)
				.distinct().collect(Collectors.toList());
		customerList.addAll(opsRequestpurchaseList.stream().filter(s -> StringUtils.isNotBlank(s.getUserno()))
				.map(OpsRequestpurchaseDto::getUserno).distinct().collect(Collectors.toList()));
		// bug12485 合并时的合并规则，使用vip客户作为合并后客户代码，该客户所属部门为合并后部门
		// bug12700 对客户代码进行判空处理，非空时不进行查询
		if (!CollectionUtils.isEmpty(customerList)) {
			OpsCustomerPropertyExample vipExam = new OpsCustomerPropertyExample();
			vipExam.createCriteria().andCustomernoIn(customerList);
			List<OpsCustomerProperty> vipList = opsCustomerPropertyMapper.selectByExample(vipExam);
			// 若没有vip客户则使用货期最近的客户
			if (CollectionUtils.isEmpty(vipList)) {
				List<OpsRequestpurchaseDto> temp = opsRequestpurchaseList.stream()
						.filter(s -> s.getHopeexportdate().compareTo(hopeExportDateMerge) == 0)
						.collect(Collectors.toList());
				customerNo = temp.get(0).getCustomerno();
				userNo = temp.get(0).getUserno();
				deptNo = temp.get(0).getDeptno();
				endUser = temp.get(0).getEndUser();
			} else {
				List<OpsRequestpurchaseDto> temp = opsRequestpurchaseList.stream()
						.filter(s -> vipList.get(0).getCustomerno().equals(s.getCustomerno())
								|| vipList.get(0).getCustomerno().equals(s.getUserno()))
						.collect(Collectors.toList());
				customerNo = temp.get(0).getCustomerno();
				userNo = temp.get(0).getUserno();
				deptNo = temp.get(0).getDeptno();
				endUser = temp.get(0).getEndUser();
			}
		}

		// 定义唯一行UUID
		String uuid = UUID.randomUUID().toString();
		// BigDecimal finalWeightMerge = weightMerge;
		for (OpsRequestpurchaseDto o : opsRequestpurchaseList) {
			o.setHopeExportDateMerge(hopeExportDateMerge);
			o.setTransTypeMerge(transTypeMerge);
			o.setPurchaseWarehouseIdMerge(purchaseWarehouseIdMerge);
			o.setQuantitySum(quantityMerge);
			o.setNetweightMerge(weightMerge);
			o.setUuid(uuid);
			// 2021-10-22 合并后的如果为多段，重新取得对应段的价格，后续接口完善
			o.setStdpriceMerge(priceMerge);
			o.setImportFobPriceOriginalMerge(fobMerge);
			o.setInqnoMerge(inqnoMerge);
			o.setHopedeliverydateMerge(hopedeliverydateMerge);
			o.setSmccodeMerge(smccodeMerge);
			if (StringUtils.isNotBlank(json)) {
				o.setInfojson(json);
			}
			if (StringUtils.isNotBlank(customerNo)) {
				o.setCustomerno(customerNo);
			}
			if (StringUtils.isNotBlank(userNo)) {
				o.setUserno(userNo);
			}
			if (StringUtils.isNotBlank(deptNo)) {
				o.setDeptno(deptNo);
			}
			if (StringUtils.isNotBlank(endUser)) {
				o.setEndUser(endUser);
			}
		}
		return opsRequestpurchaseList;
	}

	/**
	 * 还需要判断是否为bin品，从广州得到该型号所在仓库的所有的bin信息，跟当前型号和仓库进行匹配，然后添加bin属性，并且显示在页面上，
	 * 是否为bin不作为手工合并的条件 只有同类型订单、同供应商、同型号 作为手工合并的基础条件，bin品可以手工合并，但是需符合1bin数量要求
	 */
	/**
	 * 单条数据返回组合
	 */
	public List<OpsRequestpurchaseDto> getOneData(List<OpsRequestpurchaseDto> oneDataList) {
		if (CollectionUtils.isEmpty(oneDataList)) {
			return null;
		}
		oneDataList.stream().forEach(o -> {
			String uuid = UUID.randomUUID().toString();
			o.setHopeExportDateMerge(o.getHopeexportdate());
			o.setTransTypeMerge(o.getTranstype());
			o.setPurchaseWarehouseIdMerge(o.getPurchasewarehouseid());
			o.setQuantitySum(o.getQuantity());
			o.setUuid(uuid);
			o.setStdpriceMerge(o.getStdprice());
		});
		return oneDataList;
	}

	/**
	 * 用于前端点击拆分操作的处理需求
	 */
	@Override
	public List<OpsRequestpurchaseDto> splitPurchase(SplitCondition condition) {
		// 得到拆分操作的uuid
		String uuid = condition.getUuid();
		// 得到完整数据
		List<OpsRequestpurchaseDto> tableData = condition.getTabledatas();
		// 从完整数据中筛选出，修改的数据
		List<OpsRequestpurchaseDto> splitData = tableData.stream().filter(e -> e.getUuid().equals(uuid))
				.collect(Collectors.toList());
		List<OpsRequestpurchaseDto> newData = tableData.stream().filter(e -> !e.getUuid().equals(uuid))
				.collect(Collectors.toList());
		// 调用合并采购方法获取新数据
		List<OpsRequestpurchaseDto> splitNewData = getMergeData(splitData);
		// 拼接新集合，返回前端
		newData.addAll(splitNewData);

		return newData;
	}

	/**
	 * 手工合并采购
	 *
	 * @param list
	 * @return
	 */
	@Override
	public List<OpsRequestpurchaseDto> artificialMerge(List<OpsRequestpurchaseDto> list) throws OpsException {
		if (list.size() == 0) {
			throw Exceptions.OpsException("暂无勾选合并记录，请重试！！！");
		}
		List<String> purchasetypeList = new ArrayList<String>();
		purchasetypeList.add(PurchaseTypeEnum.SALE.getTypeCode());
		purchasetypeList.add(PurchaseTypeEnum.PRE.getTypeCode());
		// 剩余不合并数据，定义单条不合并的数组
		List<OpsRequestpurchaseDto> filterList = list.stream()
				.filter(e -> !(purchasetypeList.contains(e.getPurchasetype()))).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(filterList)) {
			// 存在非客户订单和先行在库，不能合并
			throw Exceptions.OpsException("存在非客户订单和先行在库订单，不能合并，请更正后重试！");
		}
		// 判断阀标识
		List<String> specmark = new ArrayList<String>();
		specmark.add("1");
		specmark.add("2");
		List<OpsRequestpurchaseDto> specmarkList = list.stream().filter(e -> specmark.contains(e.getSpecmark()))
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(specmarkList)) {
			// 存在非客户订单和先行在库，不能合并
			throw Exceptions.OpsException("存在阀huban，不能合并，请更正后重试！");
		}
		if (!list.get(0).getIslot()) {
			Map<Date, List<OpsRequestpurchaseDto>> dateMap = list.stream()
					.collect(Collectors.groupingBy(OpsRequestpurchaseDto::getHopeexportdate));
			dateMap.forEach((dateKey, dateList) -> {
				// 判断同出库日的项数
				if (dateList.size() < 5) {
					try {
						throw Exceptions.OpsException("合并的订单型号非多段型号，且出库日的项数小于5，不能合并！");
					} catch (OpsException e) {
						e.printStackTrace();
					}
				}
			});
		}
		List<OpsRequestpurchaseDto> mergeList = getMergeData(list);
		// 对合并的各项bin数量进行校验，如果大于该仓库的1bin数量，则不进行合并
		List<OpsRequestpurchaseDto> binDataList = mergeList.stream().filter(e -> e.isBin())
				.collect(Collectors.toList());
		if (binDataList.size() > 0) {
			// 取得合并后的入库仓库,调广州接口得到1bin数量
			OpsRequestpurchaseDto biDto = binDataList.get(0);
			BindataRequest bindataRequest = new BindataRequest();
			bindataRequest.setWarehouseCode(biDto.getPurchaseWarehouseIdMerge());
			bindataRequest.setModelNo(biDto.getModelno());
			List<String> modelNos = new ArrayList<>();
			modelNos.add(biDto.getModelno());
			// String[] modelNos = { biDto.getModelno() };
			// bindataRequest.setModelNos(Arrays.asList(modelNos));
			bindataRequest.setModelNos(modelNos);
			ResultVo<BindataVO[]> bindata = binServiceFeignApi.listModelBinData(bindataRequest);
			if (bindata.isSuccess()) {
				if (bindata.getData() != null && bindata.getData().length > 0) {
					BindataVO bindataVO = bindata.getData()[0];
					int quantitySum = binDataList.get(0).getQuantitySum();
					// 判断合并采购数量是否大于1bin数量
					if (quantitySum > bindataVO.getQtyBin()) {
						// 大于该型号的1bin数量不允许合并，自定义异常处理
						throw Exceptions.OpsException("合并中的bin品超过该型号的1bin数量，请更正后重试！");
					}
				}
			}
		}
		return mergeList;
	}

	/**
	 * 后端筛选方法
	 */
	@Override
	public RequestFilterCondition filterChange(RequestFilterCondition condition) throws OpsException {
		// 根据选择的供应商，得到筛选的数据
		List<OpsRequestpurchaseDto> filterList = new ArrayList<>();
		if (CollectionUtils.isEmpty(condition.getFiltersuppilyid())) {
			filterList = condition.getOnedata();
		} else {
			filterList = condition.getOnedata().stream()
					.filter(e -> condition.getFiltersuppilyid().contains(e.getSupplierid()))
					.collect(Collectors.toList());
		}

		// 取得完整的供应商列表返回前端，用于下次筛选使用
		List<String> suppilyList = condition.getOnedata().stream().map(a -> a.getSupplierid())
				.collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
		condition.setFilterdata(filterList);
		condition.setSuppilylist(suppilyList);

		return condition;
	}

	/**
	 * bug12272 WTSR2023001014-PO拦截处理优化
	 *
	 * @param list
	 * @return
	 * @throws OpsException
	 */
	@Override
	public Integer requestPassBatch(List<OpsRequestpurchase> list) throws OpsException {
		Integer result = 0;
		for (OpsRequestpurchase opsRequestpurchase : list) {
			if (opsRequestpurchase.getSplititemno() != null) {
				// TODO bug15266 校验同订单号拆分子项有没有在此次一起被放行
				OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
				example.createCriteria().andOrdernoEqualTo(opsRequestpurchase.getOrderno())
						.andItemnoEqualTo(opsRequestpurchase.getItemno());
				List<OpsRequestpurchase> temp = opsRequestpurchaseMapper.selectByExample(example);
				if (CollectionUtils.isEmpty(temp)) {
					throw Exceptions.OpsException("未查到此请购单" + opsRequestpurchase.getOrderno() + "-"
							+ opsRequestpurchase.getItemno() + "-" + opsRequestpurchase.getSplititemno());
				}
				for (OpsRequestpurchase item : temp) {
					if (StringUtils.equals(RequestPurchaseStatusEnum.LANJIE, item.getStatecode())
							|| StringUtils.equals(RequestPurchaseStatusEnum.SHIKOMILANJIE, item.getStatecode())) {
						if (item.getSplititemno() != null
								&& opsRequestpurchase.getSplititemno() != item.getSplititemno()) {
							int size = list.stream()
									.filter(p -> StringUtils.equals(p.getOrderno(), item.getOrderno())
											&& p.getItemno().compareTo(item.getItemno()) == 0
											&& p.getSplititemno() == item.getSplititemno())
									.collect(Collectors.toList()).size();
							if (size == 0) {
								throw Exceptions.OpsException(
										"此请购单" + opsRequestpurchase.getOrderno() + "-" + opsRequestpurchase.getItemno()
												+ "-" + opsRequestpurchase.getSplititemno() + "有其他被拦截的拆分子项，需要一起放行");
							}
						}
					}
				}
			}

			// 调用单条订单放行功能
			result = requestPass(opsRequestpurchase, true);
		}
		return result;
	}

	/**
	 * 变更拦截状态
	 */
	@Override
	public Integer requestPass(OpsRequestpurchase opsRequestpurchase, Boolean isBatch) throws OpsException {
		// TODO bug15266 校验同订单号子项有没有被拦截的
		if (!isBatch && opsRequestpurchase.getSplititemno() != null) {
			OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
			example.createCriteria().andOrdernoEqualTo(opsRequestpurchase.getOrderno())
					.andItemnoEqualTo(opsRequestpurchase.getItemno());
			List<OpsRequestpurchase> list = opsRequestpurchaseMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(list)) {
				throw Exceptions.OpsException(
						"未查到此请购单" + opsRequestpurchase.getOrderno() + "-" + opsRequestpurchase.getItemno());
			}
			for (OpsRequestpurchase item : list) {
				if (StringUtils.equals(RequestPurchaseStatusEnum.LANJIE, item.getStatecode())
						|| StringUtils.equals(RequestPurchaseStatusEnum.SHIKOMILANJIE, item.getStatecode())) {
					if (item.getSplititemno() != null && opsRequestpurchase.getSplititemno() != item.getSplititemno()) {
						throw Exceptions.OpsException("此请购单有其他拦截中子项，不可单独放行" + opsRequestpurchase.getOrderno() + "-"
								+ opsRequestpurchase.getItemno().toString() + "-"
								+ opsRequestpurchase.getSplititemno().toString());
					}
				}
			}
		}

		if (interceptVerify(opsRequestpurchase)) {
			// bug9555 采购处理增加操作者字段
			// bug 10483 2023-04-20 B91717 请购模块相关操作，处理人变更为从前端获取
			// bug 12373 拦截放行还原时，出荷日为当天的订单则进行出荷日重算：B91717
			opsRequestpurchase = requestRelease(opsRequestpurchase);
			// int result;
			OpsRequestpurchase updateRequest = new OpsRequestpurchase();
			if (StringUtils.isBlank(opsRequestpurchase.getSupplierid())) {
				updateRequest.setStatecode(RequestPurchaseStatusEnum.DAICHULI);
				updateRequest.setReleasereason(opsRequestpurchase.getReleasereason());
				updateRequest.setSmccode(opsRequestpurchase.getSmccode());
				updateRequest.setPurchasewarehouseid(opsRequestpurchase.getPurchasewarehouseid());
				updateRequest.setOperator(opsRequestpurchase.getOperator());
				updateRequest.setUpdatetime(new Date());
				updateRequest.setSupplierAssignType(opsRequestpurchase.getSupplierAssignType());
			} else {
				// bug 11030 请购拦截，放行时，需要重新计算供应商和SMCcode
				OpsRequestpurchase orginPurchase = opsRequestpurchaseMapper
						.selectByPrimaryKey(opsRequestpurchase.getId());
				// 判断编辑数据的供应商是否发生变更
				if (!opsRequestpurchase.getSupplierid().equals(orginPurchase.getSupplierid())) {
					opsRequestpurchase.setSmccode(null);
					// bug 9358请购处理更改供应商后，bin补库订单不需要重新计算采购仓
					if (!opsRequestpurchase.getOrdtype().equals(OrderTypeEnum.BIN)) {
						opsRequestpurchase.setPurchasewarehouseid(null);
					}
					// bug 11030 请购拦截，放行时，需要重新计算供应商和SMCcode
					// 如果供应商发生变更了，调用重新计算smccode
					// 和 采购仓库的方法
					opsRequestpurchase = requestPurchasePreService.calWarehouseAndSmccode(opsRequestpurchase, true);
				}
				updateRequest.setStatecode(RequestPurchaseStatusEnum.CHULIZHONG);
				updateRequest.setSupplierid(opsRequestpurchase.getSupplierid());
				updateRequest.setTranstype(opsRequestpurchase.getTranstype());
				updateRequest.setHopeexportdate(opsRequestpurchase.getHopeexportdate());
				updateRequest.setSmccode(opsRequestpurchase.getSmccode());
				updateRequest.setPurchasewarehouseid(opsRequestpurchase.getPurchasewarehouseid());
				updateRequest.setOperator(opsRequestpurchase.getOperator());
				updateRequest.setUpdatetime(new Date());
				updateRequest.setSupplierAssignType(opsRequestpurchase.getSupplierAssignType());
			}
			// 更新条件 id,orderno,satecode
			OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
			example.createCriteria().andIdEqualTo(opsRequestpurchase.getId())
					.andStatecodeEqualTo(RequestPurchaseStatusEnum.LANJIE);
			// 更新选中字段
			Integer i = opsRequestpurchaseMapper.updateByExampleSelective(updateRequest, example);
			// bug14200 拦截放行增加事件总线
			purchaseEventPublisher.requestPurchaseOrderEvent(EventSourceEnum.REQUEST_RELEASE, opsRequestpurchase);
			return i;
		} else {
			throw Exceptions.OpsException("订单号：" + opsRequestpurchase.getOrderno() + "状态不是拦截状态，请刷新页面后 再操作放行！");
		}
	}

	/**
	 * 拦截订单还原功能
     * 1.BIN 不清空运输方式 不清空指定制造出荷日
     * 2.DR CR  不清空指定制造出荷日 清空运输方式
     * 3.先行在库不清空运输方式 清空制造指定出荷日
     * 4.其他 清空指定制造出荷日 清空运输方式
	 */
	@Override
	public Integer restore(OpsRequestpurchase opsRequestpurchase) throws OpsException {
		if (interceptVerify(opsRequestpurchase)) {
            int result = 0;
			// bug9555 采购处理增加操作者字段
			// bug 10483 2023-04-20 B91717 请购模块相关操作，处理人变更为从前端获取
            if (opsRequestpurchase.getOrdtype() == OrderTypeEnum.BIN) {
                // 不清空运输方式 不清空指定制造出荷日
                result = requestPurchaseDao.interceptRestore3(opsRequestpurchase.getId(),
                        opsRequestpurchase.getOrderno(), opsRequestpurchase.getOperator());

            }else {
                //bugid:20455  如果订单类型 既不是 DR 也不是 CR，则清空希望出荷日
                if (OrderTypeEnum.DR.equals(opsRequestpurchase.getOrdtype())
                        || OrderTypeEnum.CR.equals(opsRequestpurchase.getOrdtype())){
                    // 不清空指定制造出荷日 清空运输方式
                    result = requestPurchaseDao.interceptRestore1(opsRequestpurchase.getId(),
                            opsRequestpurchase.getOrderno(), opsRequestpurchase.getOperator());
                } else if (PurchaseTypeEnum.PRE.getTypeCode().equals(opsRequestpurchase.getPurchasetype())){
                    // 先行在库不清空运输方式 清空制造指定出荷日
                    result = requestPurchaseDao.interceptRestore2(opsRequestpurchase.getId(),
                            opsRequestpurchase.getOrderno(), opsRequestpurchase.getOperator());
                }else {
                    result = requestPurchaseDao.interceptRestore(opsRequestpurchase.getId(),
                            opsRequestpurchase.getOrderno(), opsRequestpurchase.getOperator());
                }
            }

			return result;
		} else {
			throw Exceptions.OpsException("订单号：" + opsRequestpurchase.getOrderno() + "状态不是拦截状态，请刷新页面后 再操作还原！");
		}
	}

	/**
	 * bug12272 WTSR2023001014-PO拦截处理优化
	 *
	 * @param list
	 * @return
	 * @throws OpsException
	 */
	@Override
	public Integer restoreBatch(List<OpsRequestpurchase> list) throws OpsException {
		Integer result = 0;
		for (OpsRequestpurchase opsRequestpurchase : list) {
			// 调用单条接口，进行订单还原
			result = result + restore(opsRequestpurchase);
		}
		return result;
	}

	/**
	 * 更新供应商等信息
	 */
	@Override
	public Integer updateSuppilyTrans(OpsRequestpurchase opsRequestpurchase) throws OpsException {
		// bug9555 采购处理增加操作者字段
		// operator = getOperator(dto);
		// bug 10483 2023-04-20 B91717 请购模块相关操作，处理人变更为从前端获取
		// 判空
		if (StringUtils.isBlank(opsRequestpurchase.getOrderno())
				|| StringUtils.isBlank(opsRequestpurchase.getSupplierid())
				|| StringUtils.isBlank(opsRequestpurchase.getTranstype())) {
			throw Exceptions.OpsException("修正字段存在空值，请更正后重试！");
		}

        // bugid:19007 c14717 20251009 判断是否是24字节
        if(StringUtils.equals("JP", opsRequestpurchase.getSupplierid())
                && StringUtils.isNotBlank(opsRequestpurchase.getServerremark())){
            try {
                byte[] bytes = opsRequestpurchase.getServerremark().getBytes("UTF-8");
                if(bytes.length > 24){
                    throw Exceptions.OpsException("日本订单备注不可多余24字节");
                }
            } catch (UnsupportedEncodingException e) {
                throw Exceptions.OpsException("不支持的编码");
            }
        }

		// 创建新的更新实体
		OpsRequestpurchase request = new OpsRequestpurchase();

        request.setServerremark(opsRequestpurchase.getServerremark());
		if (StringUtils.isNotBlank(opsRequestpurchase.getProducttag())) {
			request.setProducttag(opsRequestpurchase.getProducttag());
		}
		if (StringUtils.isNotBlank(opsRequestpurchase.getSupplierid())) {
			request.setSupplierid(opsRequestpurchase.getSupplierid());
		}
		if (StringUtils.isNotBlank(opsRequestpurchase.getTranstype())) {
			request.setTranstype(opsRequestpurchase.getTranstype());
		}
		if (opsRequestpurchase.getHopeexportdate() != null) {
			request.setHopeexportdate(opsRequestpurchase.getHopeexportdate());
		}
		if (StringUtils.isNotBlank(opsRequestpurchase.getSupplierAssignType())) {
			request.setSupplierAssignType(opsRequestpurchase.getSupplierAssignType());
		}
		request.setUpdatetime(new Date());
		request.setIsedited(true);
		// 增加采购更新人
		request.setOperator(opsRequestpurchase.getOperator());
		// 2022-11-01 B91717 请购单数据编辑，切换供应商时需要重新计算其 smccode 和 采购仓库 bug8527
		OpsRequestpurchase updatePurchase = opsRequestpurchaseMapper.selectByPrimaryKey(opsRequestpurchase.getId());
		// bug 11203 请购单处理页面，修改请购信息时，加入请购状态的校验
		if (!updatePurchase.getStatecode().equals(RequestPurchaseStatusEnum.CHULIZHONG)) {
			throw Exceptions.OpsException(
					"采购单号：" + updatePurchase.getOrderno() + "-" + updatePurchase.getItemno() + " 已经确认完成，不能进行修改，请刷新页面！");
		}
		// 判断编辑数据的供应商是否发生变更
		if (!opsRequestpurchase.getSupplierid().equals(updatePurchase.getSupplierid())) {
			opsRequestpurchase.setSmccode(null);
			// bug 9358请购处理更改供应商后，bin补库订单不需要重新计算采购仓
			if (!opsRequestpurchase.getOrdtype().equals(OrderTypeEnum.BIN)) {
				opsRequestpurchase.setPurchasewarehouseid(null);
			}
			// 如果供应商发生变更了，调用重新计算smccode 和 采购仓库的方法
			opsRequestpurchase = requestPurchasePreService.calWarehouseAndSmccode(opsRequestpurchase, true);
			// 重新计算后，给smcode赋新值
			request.setSmccode(opsRequestpurchase.getSmccode());
			request.setPurchasewarehouseid(opsRequestpurchase.getPurchasewarehouseid());
		}

		// 2022-11-03 将更新的方法单独提取到别的service,可以用到事务
		// 构造更新example
		Integer result = purchaseService.updateRequestEdit(request, opsRequestpurchase.getId());
		return result;
	}

	/**
	 * 请购单批量编辑功能
	 *
	 * @param list
	 */
	@Override
	public Integer updateRequestPurchaseBatch(List<OpsRequestpurchase> list) throws OpsException {
		int result = 0;
		// 2022-11-01 B91717 请购单数据编辑，切换供应商时需要重新计算其 smccode 和 采购仓库 bug8527
		for (OpsRequestpurchase opsRequestpurchase : list) {
			// 改为循环调用单条方法
			result = updateSuppilyTrans(opsRequestpurchase);
		}
		return result;
	}

	/**
	 * 根据订单号，查询请购单详情
	 */
	@Override
	public List<OpsRequestpurchase> getByOrderNo(String orderNo) {
		if (StringUtils.isBlank(orderNo)) {
			return null;
		}
		OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
		example.createCriteria().andOrdernoEqualTo(orderNo);
		List<OpsRequestpurchase> opsRequestpurchase = opsRequestpurchaseMapper.selectByExample(example);
		return opsRequestpurchase;
	}

	/**
	 * 变更待处理清单状态到待采购状态 单条接口
	 */
	@Override
	public Integer requestConfirmOne(OpsRequestpurchase opsRequestpurchase) {
		// bug9555 采购处理增加操作者字段
		// operator = getOperator(dto);
		// bug 10483 2023-04-20 B91717 请购模块相关操作，处理人变更为从前端获取
		OpsRequestpurchase request = new OpsRequestpurchase();
		request.setStatecode(RequestPurchaseStatusEnum.DAICAIGOU);
		request.setUpdatetime(new Date());
		request.setOperator(opsRequestpurchase.getOperator());
		OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
		example.createCriteria().andStatecodeEqualTo(RequestPurchaseStatusEnum.CHULIZHONG)
				.andIdEqualTo(opsRequestpurchase.getId());
		int result = opsRequestpurchaseMapper.updateByExampleSelective(request, example);
		return result;
	}

	/**
	 * 变更待处理清单状态到待采购状态 批量接口
	 */
	@Override
	public Integer requestConfirm(List<OpsRequestpurchase> list) {
		// bug9555 采购处理增加操作者字段
		// operator = getOperator(dto);
		// bug 10483 2023-04-20 B91717 请购模块相关操作，处理人变更为从前端获取
		List<Long> idList = list.stream().map(a -> a.getId()).collect(Collectors.toList());
		OpsRequestpurchase request = new OpsRequestpurchase();
		request.setStatecode(RequestPurchaseStatusEnum.DAICAIGOU);
		request.setUpdatetime(new Date());
		request.setOperator(list.get(0).getOperator());
		OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
		// example.createCriteria().andStatecodeEqualTo(RequestPurchaseStatusEnum.CHULIZHONG).andIdIn(idList);
		int result = 0;
		List<Long> temp = new ArrayList<>();
		// result = opsRequestpurchaseMapper.updateByExampleSelective(request,
		// example);
		for (int i = 0; i < idList.size(); i++) {
			if (i % 2000 == 0) {
				temp = null;
				if (i + 2000 < idList.size()) {
					temp = idList.subList(i, i + 2000);
				} else {
					temp = idList.subList(i, idList.size());
				}
				example.clear();
				example.createCriteria().andStatecodeEqualTo(RequestPurchaseStatusEnum.CHULIZHONG).andIdIn(temp);
				result = result + opsRequestpurchaseMapper.updateByExampleSelective(request, example);
			}
		}
		return result;
	}

	/**
	 * 单条执行采购 单条采购，不写入关系表中
	 */
	// 加入校验 请购日期<>发行时日期
	@Override
	public Integer executePurchaseOne(List<OpsRequestpurchaseDto> list) throws Exception {
		if (list.size() == 0) {
			// 定义空返回模式
			return null;
		}
		// bug 9927 校验是否已经发送采购。
		// bug 10668 采购发单前的校验
		if (purchaseVerify(list)) {
			throw Exceptions.OpsException("列表中有订单已被他人采购或者已被删单，请刷新页面重新发送采购");
		}
		// 单条采购 DTO 转 do
		List<OpsRequestpurchase> opsRequestpurchaseList = new ArrayList<>();
		for (OpsRequestpurchaseDto o : list) {
			OpsRequestpurchase opsRequestpurchase = new OpsRequestpurchase();
			opsRequestpurchase = (OpsRequestpurchase) PoToDto(o, opsRequestpurchase);
			opsRequestpurchaseList.add(opsRequestpurchase);
		}
		// bug 10483 2023-04-20 B91717 请购模块相关操作，处理人变更为从前端获取
		OpsRequestpurchase requestUpdate = new OpsRequestpurchase();
		// String d = simpleDateFormat2.format(new Date()) + " 00:00:00";
		// 获取当天日期
		// Date thisDay = sdf.parse(d);
		org.joda.time.LocalDate today = new org.joda.time.LocalDate(DateUtils.truncate(new Date(), Calendar.DATE));
		// wm发单需要用到
		List<OpsPurchaseStatusToWMDto> opsSendWmList = new ArrayList<>();

		// bug10668，优化采购发单，先筛选出需要重新计算的数据，批量计算
		// 先筛选出需要计算的数据
		List<OpsRequestpurchase> filterList = opsRequestpurchaseList.stream()
				.filter(e -> e.getOrdtype() != OrderTypeEnum.BIN
						&& (e.getRequesttime().before(today.toDate()) || e.getHopeexportdate().before(today.toDate())))
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(filterList)) {
			// bug 10668 重新计算交货期等数据,改为批量
			reCalRequest(filterList);
			// 清空list重新查询最新数据
			opsRequestpurchaseList.clear();
			// 重新取得更新后的完整数据
			List<Long> idLists = list.stream().map(a -> a.getId()).collect(Collectors.toList());
			OpsRequestpurchaseExample exampletemp = new OpsRequestpurchaseExample();
			// OpsRequestpurchaseExample.Criteria criteria =
			// exampletemp.createCriteria();
			// criteria.andIdIn(idLists);
			List<Long> temp = new ArrayList<>();
			// 超过2000条，分组查询
			for (int i = 0; i < idLists.size(); i++) {
				if (i % 2000 == 0) {
					temp = null;
					if (i + 2000 < idLists.size()) {
						temp = idLists.subList(i, i + 2000);
					} else {
						temp = idLists.subList(i, idLists.size());
					}
					exampletemp.clear();
					exampletemp.createCriteria().andIdIn(temp);
					// 重新获取集合列表
					opsRequestpurchaseList.addAll(opsRequestpurchaseMapper.selectByExample(exampletemp));
				}
			}
		}
		// bug 13662 AP单号重新生成
		// List<OpsRequestpurchase> apsplit = opsRequestpurchaseList.stream()
		// .filter(a -> a.getSplititemno() != null && StringUtils.equals("AP",
		// a.getSupplierid()))
		// .collect(Collectors.toList());
		// bug14219 AP所有订单均重新编号，为了子项号从1开始
		List<OpsRequestpurchase> apsplit = opsRequestpurchaseList.stream()
				.filter(a -> StringUtils.equals("AP", a.getSupplierid())).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(apsplit)) {
			// 查询是否有同一订单号
			Map<String, List<OpsRequestpurchase>> orderMap = apsplit.stream()
					.collect(Collectors.groupingBy(OpsRequestpurchase::getOrderno));
			orderMap.forEach((key, sList) -> {
				if (sList.size() > 0) {
					// String ap = "AP" + (new
					// SimpleDateFormat("yyMMddHHmmssSSS")).format(new Date());
					// bug14330 变更AP单号生成规则
					String ap = "AP" + specialOrderNoService.generateOrderNo("AP");
					if (StringUtils.isBlank(ap)) {
						logger.error("AP单号生成失败，请检查字典等参数是否齐全！");
						ap = "AP" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new Date());
						ap = ap.substring(0, ap.length() - 2);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							logger.error("sleep100error");
						}
					}
					int poItemNo = 1;
					for (OpsRequestpurchase i : sList.stream().filter(a -> StringUtils.equals(key, a.getOrderno()))
							.collect(Collectors.toList())) {
						i.setPoOrderNo(ap);
						i.setPoItemNo(poItemNo++);
						i.setPoSplitNo(null);
					}
					// try {
					// Thread.sleep(100);
					// } catch (InterruptedException e) {
					// logger.error("sleep100error");
					// }
				}
			});
		}

		int result = 0;
		// 校验完毕，开始发送采购单
		for (OpsRequestpurchase o : opsRequestpurchaseList) {
			o.setOperator(list.get(0).getOperator());
			// // bug 9487,采购发单时校验是否存在合并采购订单 B91717
			// OpsReqPoMappingExample opsReqPoMappingExample = new
			// OpsReqPoMappingExample();
			// OpsReqPoMappingExample.Criteria mapCriteria =
			// opsReqPoMappingExample.createCriteria();
			// mapCriteria.andRequestpurchaseidEqualTo(o.getId()).andRequestordernoEqualTo(o.getOrderno())
			// .andRequestitemnoEqualTo(o.getItemno());
			// if (o.getSplititemno() != null) {
			// mapCriteria.andRequestsplititemnoEqualTo(o.getSplititemno());
			// } else {
			// // bug 12852,拆分单号为空时，增加 isnull条件
			// mapCriteria.andRequestsplititemnoIsNull();
			// }
			// List<OpsReqPoMapping> opsReqPoMappingList =
			// opsReqPoMappingMapper.selectByExample(opsReqPoMappingExample);
			// if (!CollectionUtils.isEmpty(opsReqPoMappingList)) {
			// throw Exceptions.OpsException(o.getOrderno() + "-" +
			// o.getItemno() + "该订单已经由合并采购发单，请刷新页面重试");
			// }
			// bug13662废除合并表
			if (!StringUtils.equals(RequestPurchaseStatusEnum.CHULIZHONG, o.getStatecode())
					&& !StringUtils.equals(RequestPurchaseStatusEnum.DAICAIGOU, o.getStatecode())) {
				throw Exceptions.OpsException(o.getOrderno() + "-" + o.getItemno() + "该订单状态已不可采购，请刷新页面重试");
			}
			OpsPurchaseorder opsPurchaseorder = new OpsPurchaseorder();

			if (StringUtils.isBlank(o.getSupplierid())) {
				throw Exceptions.OpsException(o.getOrderno() + "供应商信息为空，请补充后重试");
			}
			// bug11473马腾 自动发单，提取出公用函数
			opsPurchaseorder = generatePurchaseOrder(o, opsPurchaseorder);
			// bug11473自动发单 生成批次号
			Calendar calendar = Calendar.getInstance();
			Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
			Integer minute = calendar.get(Calendar.MINUTE);
			String version = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + hour.toString()
					+ minute.toString() + "M";
			opsPurchaseorder.setSendversion(version);
			result = opsPurchaseorderMapper.insertSelective(opsPurchaseorder);

			// 调用更新请购单状态
			requestUpdate.setId(o.getId());
			requestUpdate.setStatecode(RequestPurchaseStatusEnum.YIFASONG);
			requestUpdate.setUpdatetime(new Date());
			requestUpdate.setOperator(list.get(0).getOperator());
			requestUpdate.setSendversion(version);
			// bug13662请购表新增采购单号存储
			requestUpdate.setPoOrderNo(opsPurchaseorder.getOrderno());
			requestUpdate.setPoItemNo(opsPurchaseorder.getItemno());
			if (opsPurchaseorder.getSplititemno() != null) {
				requestUpdate.setPoSplitNo(opsPurchaseorder.getSplititemno());
			} else {
				// bug14690无拆分单号时赋值为null
				requestUpdate.setPoSplitNo(null);
			}
			int results = opsRequestpurchaseMapper.updateByPrimaryKeySelective(requestUpdate);

			if (results == 1) {
				// 回写给wm状态更新
				OpsPurchaseStatusToWMDto opsSendWm = new OpsPurchaseStatusToWMDto();
				opsSendWm.setOpsPurchaseorder(opsPurchaseorder);
				opsSendWm.setOpsRequestpurchase(o);
				opsSendWmList.add(opsSendWm);
			}
		}
		// bug11473 马腾 自动发单
		autoSend();
		// 写入wm状态
		if (!CollectionUtils.isEmpty(opsSendWmList)) {
			// bug 11534,采购发单时，新加 写入采购发单操作记录表
			sendEventLog(opsSendWmList, false);
			opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmList);

		}
		return result;
	}

	@Override
	public OpsPurchaseorder generatePurchaseOrder(OpsRequestpurchase o, OpsPurchaseorder opsPurchaseorder) {
		if (StringUtils.isNotBlank(o.getSmccode())) {
			// throw
			// Exceptions.OpsException(o.getOrderno()+"smccode为空，请补充后重试");
			opsPurchaseorder.setSmccode(o.getSmccode());
		}
		// bug13662 AP发单有拆分单号的生成新单号
		if (StringUtils.isNotBlank(o.getPoOrderNo())) {
			opsPurchaseorder.setOrderno(o.getPoOrderNo());
			opsPurchaseorder.setItemno(o.getPoItemNo());
			if (o.getPoSplitNo() != null) {
				opsPurchaseorder.setSplititemno(o.getPoSplitNo());
			}
		} else {
			opsPurchaseorder.setOrderno(o.getOrderno());
			opsPurchaseorder.setItemno(o.getItemno());
			if (o.getSplititemno() != null) {
				opsPurchaseorder.setSplititemno(o.getSplititemno());
			}
		}

		opsPurchaseorder.setDeptno(o.getDeptno());
		opsPurchaseorder.setApplyDeptNo(o.getApplyDeptNo());
		opsPurchaseorder.setCustomerno(o.getCustomerno());
		opsPurchaseorder.setUserno(o.getUserno());
		opsPurchaseorder.setInqno(o.getInqno());
		opsPurchaseorder.setPurchasetype(o.getPurchasetype());
		opsPurchaseorder.setModelno(o.getModelno());
		opsPurchaseorder.setQuantity(o.getQuantity());
		// bug12284增加外部信息字段传输
		opsPurchaseorder.setInfojson(o.getInfojson());

		// 判断是否需要偶数订货
		if (o.getIseven() != null) {
			if (o.getIseven()) {
				if (o.getQuantity() % 2 != 0) {
					// bug 10605当需要偶数订货时不修改 请购实体的数量，直接修改采购实体数量 B91717
					// o.setQuantity(o.getQuantity() + 1);
					opsPurchaseorder.setQuantity(o.getQuantity() + 1);
				}
			}
		}
		// 判断是否符合最小包装单位
		if (o.getMinpackunit() != null) {
			// 计算得到 按照最小包装单位的倍数来进行采购
			if (o.getQuantity() % o.getMinpackunit() != 0) {
				int minCount = o.getQuantity() / o.getMinpackunit();
				// o.setQuantity((minCount + 1) * o.getMinpackunit());
				// bug 10605当最小包装单位订货时不修改 请购实体的数量，直接修改采购实体数量 B91717
				opsPurchaseorder.setQuantity((minCount + 1) * o.getMinpackunit());
			}
		}
		opsPurchaseorder.setStdprice(o.getStdprice());
		if (StringUtils.isBlank(o.getSpecmark())) {
			opsPurchaseorder.setSpecmark("0");
		} else {
			opsPurchaseorder.setSpecmark(o.getSpecmark());
		}
		opsPurchaseorder.setShikomianswerno(o.getShikomianswerno());
		opsPurchaseorder.setHopedeliverydate(o.getHopedeliverydate());
		opsPurchaseorder.setStatecode(PurchaseOrderStatusEnum.DAICHULI);
		opsPurchaseorder.setRemark(o.getRemark());
		// bug 11002,采购表新增转订原因
		if (StringUtils.isNotBlank(o.getInformation()))
			opsPurchaseorder.setInformation(o.getInformation());
		// bug11841 特殊订单，发单时型号需要前面加*进行订货
		if (StringUtils.isNotBlank(o.getProducttag()) && StringUtils.equals("1", o.getProducttag())) {
			if (StringUtils.isNotBlank(opsPurchaseorder.getInformation())) {
				opsPurchaseorder.setInformation(opsPurchaseorder.getInformation() + ";add*;");
			} else {
				opsPurchaseorder.setInformation("add*;");
			}
		}
		if (StringUtils.equals(o.getProducttag(), "0")) {
			opsPurchaseorder.setGreencode("H");
		}
		opsPurchaseorder.setProducttype(o.getProducttype());
		opsPurchaseorder.setReceivewarehouseid(o.getPurchasewarehouseid());
		opsPurchaseorder.setTranstype(o.getTranstype());
		opsPurchaseorder.setHopeexportdate(o.getHopeexportdate());
		opsPurchaseorder.setSupplierid(o.getSupplierid());

		opsPurchaseorder.setHscode(o.getHscode());
		opsPurchaseorder.setPurchasedate(new Date());
		opsPurchaseorder.setSupplierpartno(o.getSupplierpartno());
		opsPurchaseorder.setImportcurrencyid(o.getImportcurrencyid());
		opsPurchaseorder.setImportfobpriceoriginal(o.getImportfobpriceoriginal());
		opsPurchaseorder.setOrdtype(o.getOrdtype());
		opsPurchaseorder.setServerremark(o.getServerremark());
		if (o.getInventorypropertyid() != null) {
			opsPurchaseorder.setInventorypropertyid(o.getInventorypropertyid());
		}
		if (o.getInspectiontype() != null) {
			opsPurchaseorder.setInspectiontype(o.getInspectiontype());
		}
		// 9555 采购处理增加操作者字段
		opsPurchaseorder.setOperator(o.getOperator());
        //bugid:19186 c14717 20251127 合并smccode
        OpsPoDestinationConfig subCode = splitSmcCodeService.getSubCode(opsPurchaseorder.getSupplierid(), opsPurchaseorder.getModelno()
                , opsPurchaseorder.getReceivewarehouseid(), opsPurchaseorder.getPurchasetype(), opsPurchaseorder.getTranstype());

        if(Objects.nonNull(subCode)){
            opsPurchaseorder.setSmccode(subCode.getSmccode());
            opsPurchaseorder.setSubCode(subCode.getSubCode());
        }
		opsPurchaseorder.setSupplierAssignType(o.getSupplierAssignType()); // bug19540 WTSR2025001597-配合PMS分单系统OPS系统发单功能新增
		opsPurchaseorder.setPrepareorderno(o.getPrepareorderno()); // bug20023 采购自动发单给psi时,传递准备订单号
		opsPurchaseorder.setEndUser(o.getEndUser());
		return opsPurchaseorder;
	}

	/**
	 * 多条合并采购
	 */
	// 加入校验 请购日期<>发行时日期
	@Override
	public Integer executePurchaseMerge(List<OpsRequestpurchaseDto> list, String type) throws Exception {
		if (list.size() == 0) {
			// 定义空返回模式
			return null;
		}
		// bug 9927 校验是否已经发送采购。
		// bug 10668 采购发单前的校验
		if (purchaseVerify(list)) {
			throw Exceptions.OpsException("列表中有订单已被他人采购或者已被删单，请刷新页面重新发送采购");
		}

		// bug 9555新增操作者字段
		// bug 10483 2023-04-20 B91717 请购模块相关操作，处理人变更为从前端获取

		// 获取当天日期
		org.joda.time.LocalDate today = new org.joda.time.LocalDate(DateUtils.truncate(new Date(), Calendar.DATE));

		// 筛选请购日期小于当天得
		List<OpsRequestpurchaseDto> filterList = list.stream()
				.filter(s -> s.getHopeexportdate().before(today.toDate()) && s.getOrdtype() != OrderTypeEnum.BIN)
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(filterList)) {
			// 筛选出来的需要重新计算的请购单，dto转 do
			List<OpsRequestpurchase> opsRequestpurchaseList = new ArrayList<>();
			for (OpsRequestpurchaseDto o : filterList) {
				OpsRequestpurchase opsRequestpurchase = new OpsRequestpurchase();
				opsRequestpurchase = (OpsRequestpurchase) PoToDto(o, opsRequestpurchase);
				opsRequestpurchaseList.add(opsRequestpurchase);
			}
			// 重新计算交货期 提出为公共方法，两个模块都能调用
			reCalRequest(opsRequestpurchaseList);
		}

		// 同步给wm时用到
		List<OpsPurchaseStatusToWMDto> opsSendWmList = mergeToPurchase(list, type);

		// TODO
		// bug11473 马腾 自动发单
		autoSend();

		// 更新wm状态
		if (!CollectionUtils.isEmpty(opsSendWmList)) {
			// bug 11534,采购发单时，新加 写入采购发单记录表
			sendEventLog(opsSendWmList, true);
			opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmList);
		}

		/**
		 * 2022-05-07 废弃之前的采购发送任务，改为点击采购摁扭后调用采购发送方法
		 */
		return list.size();
	}

	/**
	 *
	 * bug15266 合并自动发单，提出公共方法
	 *
	 */
	@Override
	public List<OpsPurchaseStatusToWMDto> mergeToPurchase(List<OpsRequestpurchaseDto> list, String type) {
		List<OpsPurchaseStatusToWMDto> opsSendWmList = new ArrayList<OpsPurchaseStatusToWMDto>();
		Map<String, List<OpsRequestpurchaseDto>> mergeMap = list.stream()
				.collect(Collectors.groupingBy(OpsRequestpurchaseDto::getUuid));
		mergeMap.forEach((key, sList) -> {

			OpsPurchaseorder opsPurchaseorder = new OpsPurchaseorder();
			// 获取随机ID
			OpsRequestpurchaseDto o = sList.get(0);

			// bug14330 变更合并单号生成规则
			String PurchaseOrderNo = "MR" + specialOrderNoService.generateOrderNo("MR");
			if (StringUtils.isBlank(PurchaseOrderNo)) {
				logger.error("合并单号生成失败，请检查字典等参数是否齐全！");
				PurchaseOrderNo = "MR" + (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new Date());
				PurchaseOrderNo = PurchaseOrderNo.substring(0, PurchaseOrderNo.length() - 2);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					logger.error("sleep10error");
				}
			}

			if (StringUtils.isBlank(o.getSupplierid())) {
				try {
					throw Exceptions.OpsException(o.getOrderno() + "供应商信息为空，请补充后重试");
				} catch (OpsException e) {
					e.printStackTrace();
				}
			}
			if (StringUtils.isBlank(o.getSmccode())) {
				try {
					throw Exceptions.OpsException(o.getOrderno() + "smccode为空，请补充后重试");
				} catch (OpsException e) {
					e.printStackTrace();
				}

			}
			// bug 11002,采购表新增转订原因
			if (StringUtils.isNotBlank(o.getInformation()))
				opsPurchaseorder.setInformation(o.getInformation());
			// bug11841 特殊订单，发单时型号需要前面加*进行订货
			if (StringUtils.isNotBlank(o.getProducttag()) && StringUtils.equals("1", o.getProducttag())) {
				if (StringUtils.isNotBlank(opsPurchaseorder.getInformation())) {
					opsPurchaseorder.setInformation(opsPurchaseorder.getInformation() + ";add*;");
				} else {
					opsPurchaseorder.setInformation("add*;");
				}
			}
			opsPurchaseorder.setOrderno(PurchaseOrderNo);
			opsPurchaseorder.setItemno(1);
			opsPurchaseorder.setDeptno(o.getDeptno());
			opsPurchaseorder.setApplyDeptNo(o.getApplyDeptNo());
			opsPurchaseorder.setCustomerno(o.getCustomerno());
			opsPurchaseorder.setUserno(o.getUserno());
			opsPurchaseorder.setInqno(o.getInqnoMerge());
			opsPurchaseorder.setPurchasetype(o.getPurchasetype());
			opsPurchaseorder.setModelno(o.getModelno());
			opsPurchaseorder.setQuantity(o.getQuantitySum());
			// bug12284增加外部信息字段传输
			opsPurchaseorder.setInfojson(o.getInfojson());
			// 判断是否需要偶数订货
			if (o.getIseven() != null) {
				if (o.getIseven()) {
					if (o.getQuantitySum() % 2 != 0) {
						// bug 10605当最小包装单位订货时不修改 请购实体的数量，直接修改采购实体数量 B91717
						// o.setQuantitySum(o.getQuantitySum() + 1);
						opsPurchaseorder.setQuantity(o.getQuantitySum() + 1);
					}
				}
			}
			// 判断是否符合最小包装单位
			if (o.getMinpackunit() != null) {
				// 计算得到 按照最小包装单位的倍数来进行采购
				if (o.getQuantitySum() % o.getMinpackunit() != 0) {
					int minCount = o.getQuantitySum() / o.getMinpackunit();
					// bug 10605当最小包装单位订货时不修改 请购实体的数量，直接修改采购实体数量 B91717
					// o.setQuantitySum((minCount + 1) * o.getMinpackunit());
					opsPurchaseorder.setQuantity((minCount + 1) * o.getMinpackunit());
				}
			}

			opsPurchaseorder.setStdprice(o.getStdpriceMerge());
			// 阀留板,默认为0
			if (StringUtils.isBlank(o.getSpecmark())) {
				opsPurchaseorder.setSpecmark("0");
			} else {
				opsPurchaseorder.setSpecmark(o.getSpecmark());
			}
			// opsPurchaseorder.setShikomianswerno(o.getShikomianswerno());
			opsPurchaseorder.setHopedeliverydate(o.getHopedeliverydateMerge());
			opsPurchaseorder.setStatecode(PurchaseOrderStatusEnum.DAICHULI);
			opsPurchaseorder.setRemark(o.getRemark());
			if (StringUtils.equals(o.getProducttag(), "0")) {
				opsPurchaseorder.setGreencode("H");
			}
			opsPurchaseorder.setProducttype(o.getProducttype());
			opsPurchaseorder.setReceivewarehouseid(o.getPurchasewarehouseid());
			opsPurchaseorder.setTranstype(o.getTransTypeMerge());
			opsPurchaseorder.setHopeexportdate(o.getHopeExportDateMerge());
			opsPurchaseorder.setSupplierid(o.getSupplierid());
			opsPurchaseorder.setSmccode(o.getSmccodeMerge());
			opsPurchaseorder.setHscode(o.getHscode());
			opsPurchaseorder.setPurchasedate(new Date());
			opsPurchaseorder.setSupplierpartno(o.getSupplierpartno());
			opsPurchaseorder.setImportcurrencyid(o.getImportcurrencyid());
			opsPurchaseorder.setImportfobpriceoriginal(o.getImportFobPriceOriginalMerge());
			opsPurchaseorder.setMergeflag(true);
			opsPurchaseorder.setOrdtype(o.getOrdtype());
			opsPurchaseorder.setServerremark(o.getServerremark());
			opsPurchaseorder.setCorderno(o.getCorderno());
			if (o.getInventorypropertyid() != null) {
				opsPurchaseorder.setInventorypropertyid(o.getInventorypropertyid());
			}
			if (o.getInspectiontype() != null) {
				opsPurchaseorder.setInspectiontype(o.getInspectiontype());
			}
			// bug 9555 operator
			opsPurchaseorder.setOperator(list.get(0).getOperator());

			// bug11473自动发单 生成批次号
			Calendar calendar = Calendar.getInstance();
			Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
			Integer minute = calendar.get(Calendar.MINUTE);
			// bug15266自动发送合并采购单
			String version = "MR" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + hour.toString()
					+ minute.toString() + type;

			opsPurchaseorder.setSendversion(version);

            // bugid:19186 c14717 20251127 合并smccode
            OpsPoDestinationConfig subCode = splitSmcCodeService.getSubCode(opsPurchaseorder.getSupplierid(), opsPurchaseorder.getModelno()
                    , opsPurchaseorder.getReceivewarehouseid(), opsPurchaseorder.getPurchasetype(), opsPurchaseorder.getTranstype());

            if(Objects.nonNull(subCode)){
                opsPurchaseorder.setSmccode(subCode.getSmccode());
                opsPurchaseorder.setSubCode(subCode.getSubCode());
            }
			opsPurchaseorder.setSupplierAssignType(o.getSupplierAssignType()); // bug19540 WTSR2025001597-配合PMS分单系统OPS系统发单功能新增
			opsPurchaseorder.setEndUser(o.getEndUser());
			opsPurchaseorder.setPrepareorderno(o.getPrepareorderno()); // bug20023 采购自动发单给psi时,传递准备订单号
			opsPurchaseorderMapper.insertSelective(opsPurchaseorder);

			// 写入映射关系表
			sList.forEach(s -> {
				// 调用更新请购单状态
				OpsRequestpurchase opsRequestpurchase = new OpsRequestpurchase();
				opsRequestpurchase.setId(s.getId());
				opsRequestpurchase.setStatecode(RequestPurchaseStatusEnum.YIFASONG);
				opsRequestpurchase.setUpdatetime(new Date());
				opsRequestpurchase.setMergeflag(true);
				// bug 9555 增加操作者字段
				opsRequestpurchase.setOperator(list.get(0).getOperator());
				// bug11473自动发单 生成批次号
				opsRequestpurchase.setSendversion(version);
				// bug13662 增加请购表中的采购单号
				opsRequestpurchase.setPoOrderNo(opsPurchaseorder.getOrderno());
				opsRequestpurchase.setPoItemNo(opsPurchaseorder.getItemno());
				// bug14690无拆分单号时赋值为null
				opsRequestpurchase.setPoSplitNo(null);
				int results = opsRequestpurchaseMapper.updateByPrimaryKeySelective(opsRequestpurchase);
				// 写入wm状态更新
				if (results == 1) {
					OpsPurchaseStatusToWMDto opsSendWm = new OpsPurchaseStatusToWMDto();
					opsSendWm.setOpsPurchaseorder(opsPurchaseorder);
					opsSendWm.setOpsRequestpurchase(s);
					opsSendWmList.add(opsSendWm);

				}
			});
		});
		return opsSendWmList;
	}

	/**
	 * 获取供应商列表
	 */
	@Override
	public List<Supplier> findSuppily() {
		SupplierExample supplierExample = new SupplierExample();
		// SupplierExample.Criteria criteria = supplierExample.createCriteria();
		supplierExample.setOrderByClause("sort");
		return supplierMapper.selectByExample(supplierExample);
	}

	/**
	 * 获取运输方式
	 */
	@Override
	public List<OpsPoTranstype> findTrans() {
		return opsPoTranstypeMapper.selectByExample(null);
	}

	/**
	 * 获取shikomi拦截清单
	 */
	@Override
	public PageInfo<OpsRequestpurchase> findShikomiList(PageModel<RequestPurchaseQO> pageModel) {
		RequestPurchaseQO condition = pageModel.getCondition();
		PageInfo<OpsRequestpurchase> pageInfo = new PageInfo<OpsRequestpurchase>();
		OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
		OpsRequestpurchaseExample.Criteria criteria = example.createCriteria();
		criteria.andStatecodeEqualTo(condition.getStatecode());
		if (StringUtils.isNotBlank(pageModel.getOrderBy())) {
			example.setOrderByClause(pageModel.getOrderBy());
		} else {
			example.setOrderByClause("orderDate desc");
		}

		if (StringUtils.isNotBlank(condition.getOrderno())) {
			String pono = condition.getOrderno();
			if (condition.getOrderno().contains("-")) {
				String[] split = pono.split("-");
				criteria.andOrdernoEqualTo(split[0]);
				criteria.andItemnoEqualTo(Integer.parseInt(split[1]));
				if (split.length == 3) {
					criteria.andSplititemnoEqualTo(Integer.parseInt(split[2]));
				}
			} else {
				criteria.andOrdernoEqualTo(condition.getOrderno());
			}
		}
		if (StringUtils.isNotBlank(condition.getModelno())) {
			criteria.andModelnoLike("%" + condition.getModelno() + "%");
		}
		if (StringUtils.isNotBlank(condition.getPurchasetype())) {
			criteria.andPurchasetypeEqualTo(condition.getPurchasetype());
		}
		if (StringUtils.isNotBlank(condition.getCustomerno())) {
			criteria.andCustomernoEqualTo(condition.getCustomerno());
		}
		if (StringUtils.isNotBlank(condition.getInterceptmsg())) {
			criteria.andInterceptmsgLike("%" + condition.getInterceptmsg() + "%");
		}
		if (StringUtils.isNotBlank(condition.getSupplierid())) {
			criteria.andSupplieridEqualTo(condition.getSupplierid());
		}
		if (StringUtils.isNotBlank(condition.getTranstype())) {
			criteria.andTranstypeEqualTo(condition.getTranstype());
		}
		// bug 8633 B91717 采购拦截加入订单类别筛选
		if (StringUtils.isNotBlank(condition.getOrdtype())) {
			criteria.andOrdtypeEqualTo(condition.getOrdtype());
		}
		if (!CollectionUtils.isEmpty(condition.getDeptNos())) {
			// criteria.andApplyDeptNoIn(condition.getDeptNos());
			// bug 10560,修改采购相关页面 展示为deptno字段
			criteria.andDeptnoIn(condition.getDeptNos());
		}
		pageInfo = PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize())
				.doSelectPageInfo(() -> opsRequestpurchaseMapper.selectByExample(example));
		return pageInfo;
	}

	/**
	 * 确认全部订单
	 *
	 * @param ops
	 */
	@Override
	public Integer ConfirmAll(RequestPurchaseQO ops) throws OpsException {
		List<OpsRequestpurchase> selectList = new ArrayList<>();
		OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
		OpsRequestpurchaseExample.Criteria criteria = example.createCriteria();
		criteria.andStatecodeEqualTo(RequestPurchaseStatusEnum.CHULIZHONG);
		if (StringUtils.isBlank(ops.getOrderno()) && StringUtils.isBlank(ops.getSupplierid())
				&& CollectionUtils.isEmpty(ops.getSupplierids()) && StringUtils.isBlank(ops.getTranstype())
				&& StringUtils.isBlank(ops.getPurchasetype()) && StringUtils.isBlank(ops.getOrdtype())
				&& StringUtils.isBlank(ops.getModelno()) && CollectionUtils.isEmpty(ops.getDeptNos())
				&& StringUtils.isBlank(ops.getCorderno())) {
			selectList = opsRequestpurchaseMapper.selectByExample(example);
		} else {
			if (StringUtils.isNotBlank(ops.getOrderno())) {
				String pono = ops.getOrderno();
				if (ops.getOrderno().contains("-")) {
					String[] split = pono.split("-");
					criteria.andOrdernoEqualTo(split[0]);
					criteria.andItemnoEqualTo(Integer.parseInt(split[1]));
					if (split.length == 3) {
						criteria.andSplititemnoEqualTo(Integer.parseInt(split[2]));
					}
				} else {
					criteria.andOrdernoEqualTo(ops.getOrderno());
				}
			}
			if (StringUtils.isNotBlank(ops.getModelno())) {
				criteria.andModelnoLike(ops.getModelno() + "%");
			}
			if (StringUtils.isNotBlank(ops.getPurchasetype())) {
				criteria.andPurchasetypeEqualTo(ops.getPurchasetype());
			}
			if (StringUtils.isNotBlank(ops.getCustomerno())) {
				criteria.andCustomernoEqualTo(ops.getCustomerno());
			}
			if (StringUtils.isNotBlank(ops.getInterceptmsg())) {
				criteria.andInterceptmsgLike("%" + ops.getInterceptmsg() + "%");
			}
			// if (StringUtils.isNotBlank(ops.getSupplierid())) {
			// criteria.andSupplieridEqualTo(ops.getSupplierid());
			// }
			// bug7538,调整请购处理，供应商可以多列选择
			if (!CollectionUtils.isEmpty(ops.getSupplierids())) {
				criteria.andSupplieridIn(ops.getSupplierids());
			}
			if (StringUtils.isNotBlank(ops.getTranstype())) {
				criteria.andTranstypeEqualTo(ops.getTranstype());
			}
			if (!CollectionUtils.isEmpty(ops.getDeptNos())) {
				criteria.andDeptnoIn(ops.getDeptNos());
			}
			selectList = opsRequestpurchaseMapper.selectByExample(example);
		}
		Integer result = 0;
		if (!CollectionUtils.isEmpty(selectList)) {
			selectList.forEach(o -> {
				o.setOperator(ops.getOperator());
			});
			// 调用批量处理方法
			result = requestConfirm(selectList);
		}
		return result;

	}

	@Override
	public Integer toPurchase(RequestPurchaseQO ops) throws Exception {
		Integer result = 0;
		List<OpsRequestpurchaseDto> lists = new ArrayList<>();
		List<OpsRequestpurchase> selectList = new ArrayList<>();
		OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
		OpsRequestpurchaseExample.Criteria criteria = example.createCriteria();
		criteria.andStatecodeEqualTo(RequestPurchaseStatusEnum.CHULIZHONG);
		// bug14442 直发采购时，供应商集合判空优化，用CollectionUtils.isEmpty
		if (StringUtils.isBlank(ops.getOrderno()) && StringUtils.isBlank(ops.getSupplierid())
				&& CollectionUtils.isEmpty(ops.getSupplierids()) && StringUtils.isBlank(ops.getTranstype())
				&& StringUtils.isBlank(ops.getPurchasetype()) && StringUtils.isBlank(ops.getOrdtype())
				&& StringUtils.isBlank(ops.getModelno()) && CollectionUtils.isEmpty(ops.getDeptNos())
				&& StringUtils.isBlank(ops.getCorderno())) {
			selectList = opsRequestpurchaseMapper.selectByExample(example);
		} else {
			if (StringUtils.isNotBlank(ops.getOrderno())) {
				String pono = ops.getOrderno();
				if (ops.getOrderno().contains("-")) {
					String[] split = pono.split("-");
					criteria.andOrdernoEqualTo(split[0]);
					criteria.andItemnoEqualTo(Integer.parseInt(split[1]));
					if (split.length == 3) {
						criteria.andSplititemnoEqualTo(Integer.parseInt(split[2]));
					}
				} else {
					criteria.andOrdernoEqualTo(ops.getOrderno());
				}
			}
			if (StringUtils.isNotBlank(ops.getModelno())) {
				criteria.andModelnoLike(ops.getModelno() + "%");
			}
			if (StringUtils.isNotBlank(ops.getPurchasetype())) {
				criteria.andPurchasetypeEqualTo(ops.getPurchasetype());
			}
			if (StringUtils.isNotBlank(ops.getCustomerno())) {
				criteria.andCustomernoEqualTo(ops.getCustomerno());
			}
			if (StringUtils.isNotBlank(ops.getInterceptmsg())) {
				criteria.andInterceptmsgLike("%" + ops.getInterceptmsg() + "%");
			}
			// if (StringUtils.isNotBlank(ops.getSupplierid())) {
			// criteria.andSupplieridEqualTo(ops.getSupplierid());
			// }
			// bug7538,调整请购处理，供应商可以多列选择
			if (!CollectionUtils.isEmpty(ops.getSupplierids())) {
				criteria.andSupplieridIn(ops.getSupplierids());
			}
			if (StringUtils.isNotBlank(ops.getTranstype())) {
				criteria.andTranstypeEqualTo(ops.getTranstype());
			}
			if (!CollectionUtils.isEmpty(ops.getDeptNos())) {
				criteria.andDeptnoIn(ops.getDeptNos());
			}
			selectList = opsRequestpurchaseMapper.selectByExample(example);
		}
		// bug14442 直发采购时，加入非空的判断
		if (!CollectionUtils.isEmpty(selectList)) {
			// po转DTO
			selectList.stream().forEach(o -> {
				OpsRequestpurchaseDto opsRequestpurchaseDto = new OpsRequestpurchaseDto();
				try {
					opsRequestpurchaseDto = (OpsRequestpurchaseDto) PoToDto(o, opsRequestpurchaseDto);
					opsRequestpurchaseDto.setOperator(ops.getOperator());
					lists.add(opsRequestpurchaseDto);
				} catch (Exception e1) {
					logger.error("OpsRequestpurchase转换为OpsRequestpurchaseDto时报错: ", e1);
				}
			});
			result = executePurchaseOne(lists);
		}
		return result;
	}

	/**
	 * shikomi放行 bug13379 取消放行时预占shikomi，放行时只操作shikomi放行状态，非补库单清空期望出荷日及运输方式
	 */
	@Override
	public Integer shikomiPass(OpsRequestpurchase opsRequestpurchase) throws OpsException {
		// bug 12373 拦截放行还原时，出荷日为当天的订单则进行出荷日重算：B91717
		// opsRequestpurchase = requestRelease(opsRequestpurchase);
		// bug 13379 shikomi拦截放行时，增加状态校验，后续操作与请购还原一致
		if (interceptVerify(opsRequestpurchase)) {
			if (StringUtils.isNotBlank(opsRequestpurchase.getShikomianswerno())) {
				opsRequestpurchase.setShikomirelease("2");
			} else {
				// 2024-01-24 不使用shikomi号时，操作请购单还原
				opsRequestpurchase.setShikomirelease("1");
			}
			// 2024-01-26 bin补库时，不清空
			if (!opsRequestpurchase.getOrdtype().equals(OrderTypeEnum.BIN)) {
                //bugid:20455  如果订单类型 既不是 DR 也不是 CR，则清空希望出荷日
                if (!OrderTypeEnum.DR.equals(opsRequestpurchase.getOrdtype())
                        && !OrderTypeEnum.CR.equals(opsRequestpurchase.getOrdtype())){
                    opsRequestpurchase.setHopeexportdate(null); // 将出荷日置空，参与重新计算
                }
                // 先行在库不清空运输方式
                if (!PurchaseTypeEnum.PRE.getTypeCode().equals(opsRequestpurchase.getPurchasetype())){
                    opsRequestpurchase.setTranstype(null);
                }
			}
			opsRequestpurchase.setStatecode(RequestPurchaseStatusEnum.DAICHULI);
			opsRequestpurchase.setUpdatetime(new Date());
			return opsRequestpurchaseMapper.updateByPrimaryKey(opsRequestpurchase); // 更新采购状态
		} else {
			throw Exceptions.OpsException("此请购单状态不是拦截状态，请刷新页面后 再操作还原！");
		}
	}

	/**
	 * 获取可用Shikomi列表
	 */
	@Override
	public List<String> getShikomiList() {
		return null;
	}

	/**
	 * 拦截处理更新到备注信息中
	 *
	 * @param opsRequestpurchase
	 */
	@Override
	public Integer editInterceptRemark(OpsRequestpurchase opsRequestpurchase) {
		OpsRequestpurchase opsPurchaseNew = new OpsRequestpurchase();
		opsPurchaseNew.setId(opsRequestpurchase.getId());
		opsPurchaseNew.setReleasereason(opsRequestpurchase.getReleasereason());
		opsPurchaseNew.setUpdatetime(new Date());
		int result = opsRequestpurchaseMapper.updateByPrimaryKeySelective(opsPurchaseNew);
		return result;
	}

	/**
	 * 后端筛选方法
	 *
	 * @param condition
	 */
	@Override
	public RequestFilterCondition sortChange(RequestFilterCondition condition) throws OpsException {
		List<OpsRequestpurchaseDto> oneDataOrderBy = new ArrayList<>();
		condition.getColumn();
		if (condition.getOrderby().equals("ascending")) {
			oneDataOrderBy = condition.getOnedata().stream().sorted(Comparator
							.comparing(OpsRequestpurchaseDto::getOrderno).thenComparing(OpsRequestpurchaseDto::getItemno))
					.collect(Collectors.toList());
		} else {
			oneDataOrderBy = condition.getOnedata().stream()
					.sorted(Comparator.comparing(OpsRequestpurchaseDto::getOrderno).reversed()
							.thenComparing(OpsRequestpurchaseDto::getItemno).reversed())
					.collect(Collectors.toList());
		}
		condition.setOnedata(oneDataOrderBy);
		return condition;
	}

	/**
	 * @param po
	 * @param dto
	 * @return
	 * @throws Exception
	 *             PO转DTO工具类
	 */
	public Object PoToDto(Object po, Object dto) throws Exception {

		// 取得po对象的所有属性
		Field[] poFields = po.getClass().getDeclaredFields();
		// 取父类的所有属性
		Field[] superPoFields = po.getClass().getSuperclass().getDeclaredFields();
		// 合并数组
		poFields = (Field[]) mergeArray(poFields, superPoFields);

		// 遍历拼接dto的set方法字段表示
		for (Field f : poFields) {
			String fieldName = f.getName();
			// 取得当前get，set字符串表达形式
			String dtoSetMethodName = "set" + firstToBig(fieldName);
			String poGetMethodName = "get" + firstToBig(fieldName);

			// 取得DTO对象的set方法
			Method dtoSetMethod = null;
			try {
				dtoSetMethod = dto.getClass().getMethod(dtoSetMethodName, f.getType());
				// 取得Po对象的get方法
				Method poGetMethod = po.getClass().getMethod(poGetMethodName);
				// 将po对象的属性值set到dto对象中去
				dtoSetMethod.invoke(dto, poGetMethod.invoke(po));
			} catch (NoSuchMethodException e) {// 如果不存在此方法跳过，
				continue;
			}

		}
		return dto;

	}

	/**
	 * 合并数组
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public Object[] mergeArray(Object[] a, Object[] b) {
		Object[] c = Arrays.copyOf(a, a.length + b.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	/**
	 * 首字母大写
	 *
	 * @param fieldName
	 * @return
	 */
	public String firstToBig(String fieldName) {
		if (fieldName != null && fieldName != "") {
			fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		}
		return fieldName;
	}

	/**
	 * bug 9927 B91717 bug 10668 采购发单优化 校验请购是否发送采购 以及请购单是否还存在 通用方法
	 *
	 * @return
	 */
	public boolean purchaseVerify(List<OpsRequestpurchaseDto> opsPurchaseorderList) throws OpsException {
		for (OpsRequestpurchaseDto opsPurchaseorder : opsPurchaseorderList) {
			// bug 9927,采购发单时校验是否存在 已经单条采购完成的
			OpsPurchaseorderExample opsPurchaseorderExample = new OpsPurchaseorderExample();
			OpsPurchaseorderExample.Criteria mapCriteria = opsPurchaseorderExample.createCriteria();
			mapCriteria.andOrdernoEqualTo(opsPurchaseorder.getOrderno()).andItemnoEqualTo(opsPurchaseorder.getItemno());
			if (opsPurchaseorder.getSplititemno() != null) {
				mapCriteria.andSplititemnoEqualTo(opsPurchaseorder.getSplititemno());
			} else {
				// bug 12852,拆分单号为空时，增加 isnull条件
				mapCriteria.andSplititemnoIsNull();
			}
			List<OpsPurchaseorder> opsPurchaseorders = opsPurchaseorderMapper.selectByExample(opsPurchaseorderExample);
			// 如果有值，说明已经发送采购了
			if (!CollectionUtils.isEmpty(opsPurchaseorders)) {
				return true;
			}
			// 检查请购的状态，是否存在/或者已发送采购
			OpsRequestpurchaseExample opsRequestpurchaseExample = new OpsRequestpurchaseExample();
			OpsRequestpurchaseExample.Criteria rerquestCriteria = opsRequestpurchaseExample.createCriteria();
			rerquestCriteria.andOrdernoEqualTo(opsPurchaseorder.getOrderno())
					.andItemnoEqualTo(opsPurchaseorder.getItemno());
			if (opsPurchaseorder.getSplititemno() != null) {
				rerquestCriteria.andSplititemnoEqualTo(opsPurchaseorder.getSplititemno());
			} else {
				// bug 12852,拆分单号为空时，增加 isnull条件
				rerquestCriteria.andSplititemnoIsNull();
			}
			List<OpsRequestpurchase> requestpurchases = opsRequestpurchaseMapper
					.selectByExample(opsRequestpurchaseExample);
			// 校验请购表如果不存在
			if (CollectionUtils.isEmpty(requestpurchases)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * bug 10668,采购发单重新计算交货期，改为批量调用
	 */
	public void reCalRequest(List<OpsRequestpurchase> filterList) throws OpsException {
		List<OpsRequestpurchase> recalList = new ArrayList<OpsRequestpurchase>();
		filterList.forEach(f -> {
			f.setHopeexportdate(null);
			recalList.add(f);
		});
		// 批量500条来调用
		List<OpsRequestpurchase> temp = new ArrayList<OpsRequestpurchase>();
		List<OpsRequestpurchase> newRecalList = new ArrayList<OpsRequestpurchase>();
		// 超过2000条时，分批操作
		for (int i = 0; i < recalList.size(); i++) {
			if (i % 500 == 0) {
				temp = null;
				if (i + 500 < recalList.size()) {
					temp = recalList.subList(i, i + 500);
				} else {
					temp = recalList.subList(i, recalList.size());
				}
				// 取得计算后的数据
				newRecalList.addAll(requestPurchasePreService.calDlvInfo(temp));
			}
		}
		// 更新计算后的数据到请购表中
		OpsRequestpurchase updateRequest = new OpsRequestpurchase();
		newRecalList.forEach(item -> {
			updateRequest.setId(item.getId());
			updateRequest.setHopeexportdate(item.getHopeexportdate());
			updateRequest.setTranstype(item.getTranstype());
			updateRequest.setUpdatetime(new Date());
			opsRequestpurchaseMapper.updateByPrimaryKeySelective(updateRequest);
		});

	}

	// bug 11534,采购发单时，新加 写入采购发单记录表 操作
	public void sendEventLog(List<OpsPurchaseStatusToWMDto> opsSendWmList, Boolean isMerge) {
		OpsPoEventLog opsPoEventLog = new OpsPoEventLog();
		if (isMerge) {
			opsPoEventLog.setPoType("合并采购发单");
		} else {
			opsPoEventLog.setPoType("单条采购发单");
		}
		// 设置参数
		opsPoEventLog.setParams(JSON.toJSONString(opsSendWmList));
		opsPoEventLogMapper.insertSelective(opsPoEventLog);
	}

	// bug 12373 拦截放行时，出荷日为当天的订单则进行出荷日重算：B91717
	public OpsRequestpurchase requestRelease(OpsRequestpurchase opsRequestpurchase) {
		// 2024-01-30 bin补库订单，不参与重新计算，B91717
		if (!opsRequestpurchase.getOrdtype().equals(OrderTypeEnum.BIN)) {
			org.joda.time.LocalDate today = new org.joda.time.LocalDate(new Date());
			// 校验出荷日是否 小于等于当天日期
			// bug 13379 拦截放行时，校验出荷日时，增加判空
			if (opsRequestpurchase.getHopeexportdate() == null
					|| opsRequestpurchase.getHopeexportdate().compareTo(today.toDate()) <= 0) {
				List<OpsRequestpurchase> opsRequestpurchases = new ArrayList<>();

                //bugid:20455  如果订单类型 既不是 DR 也不是 CR，则清空希望出荷日
                if (!OrderTypeEnum.DR.equals(opsRequestpurchase.getOrdtype())
                        && !OrderTypeEnum.CR.equals(opsRequestpurchase.getOrdtype())){
                    opsRequestpurchase.setHopeexportdate(null); // 将出荷日置空，参与重新计算
                }
                // 先行在库不清空运输方式
                if (!PurchaseTypeEnum.PRE.getTypeCode().equals(opsRequestpurchase.getPurchasetype())){
                    opsRequestpurchase.setTranstype(null);
                }
				opsRequestpurchases.add(opsRequestpurchase);
				// 重新计算出荷日
				OpsRequestpurchase reCalRequest = requestPurchasePreService.calDlvInfo(opsRequestpurchases).get(0);
				// 重新赋值
				opsRequestpurchase.setHopeexportdate(reCalRequest.getHopeexportdate());
				opsRequestpurchase.setTranstype(reCalRequest.getTranstype());
			}
		}
		return opsRequestpurchase;
	}

	/**
	 * bug 12272,PO拦截优化，批量放行 或者还原时，对采购单状态进行校验，是否为拦截状态
	 *
	 * @return
	 */
	public Boolean interceptVerify(OpsRequestpurchase opsRequestpurchase) {
		OpsRequestpurchase requestValify = opsRequestpurchaseMapper.selectByPrimaryKey(opsRequestpurchase.getId());
		if (requestValify.getStatecode().equals(RequestPurchaseStatusEnum.LANJIE)
				|| requestValify.getStatecode().equals(RequestPurchaseStatusEnum.SHIKOMILANJIE)) {
			return true;
		}
		return false;
	}

}