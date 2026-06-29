package com.sales.ops.serviceimpl.purchase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.batchdao.PoBatchDao;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.PurchaseOrderDao;
import com.sales.ops.dto.purchase.OpsPurchaseStatusToWMDto;
import com.sales.ops.dto.purchase.OpsRequestpurchaseDto;
import com.sales.ops.dto.purchase.RequestPurchaseInfo;
import com.sales.ops.enums.OrderTypeEnum;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.enums.PurchaseTypeEnum;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.service.ips.PsiPurchaseSendJpService;
import com.sales.ops.service.mail.PurchaseMailService;
import com.sales.ops.service.purchase.*;
import com.sales.ops.service.wm.WmCommonService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/***
 * bug11473 WTSR2023000504-po发单自动化
 * 
 * @author SMC892N
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseAutoServiceImpl implements PurchaseAutoService {

	private final static Logger logger = LoggerFactory.getLogger(PurchaseAutoServiceImpl.class);

	@Autowired
	private OpsPurchaseorderMapper opsPurchaseorderMapper;

	@Autowired
	private OpsPurchaseinvoiceMapper opsPurchaseinvoiceMapper;

	@Autowired
	private PurchaseOrderDao purchaseOrderDao;

	@Autowired
	private ProductBomDetailVtMapper productBomDetailVtMapper;

	@Autowired
	private RequestPurchasePreService requestPurchasePreService;

	@Autowired
	private OpsWarehouseLocationnoMapper opsWarehouseLocationnoMapper;

	@Autowired
	private OpsRequestpurchaseMapper opsRequestpurchaseMapper;

	@Autowired
	private OpsPoDestinationConfigMapper opsPoDestinationConfigMapper;

	@Autowired
	private OpsJpPModelnoMapper opsJpPModelnoMapper;

	@Autowired
	private PurchaseExportTxtService purchaseExportTxtService;

	@Autowired
	private PurchaseSendOrderGZService purchaseSendOrderGZService;

	@Autowired
	private PurchaseSendOrderManuService purchaseSendOrderManuService;

	@Autowired
	private RequestPurchaseService requestPurchaseService;

	@Autowired
	private SupplierMapper supplierMapper;

	@Autowired
	private WmCommonService wmCommonService;

	@Autowired
	private PurchaseMailService purchaseMailService;

	@Autowired
	private PoBatchDao poBatchDao;

	@Autowired
	private OpsPoModelSendConfigMapper opsPoModelSendConfigMapper;

	@Autowired
	private DictDataServiceFeignApi dictDataServiceFeignApi;

	@Autowired
	private OpsPoAutosendRemarkConfigMapper opsPoAutosendRemarkConfigMapper;

	@Autowired
	private MinPriceService minPriceService;

	@Autowired
	private SpecialOrderNoService specialOrderNoService;

    @Autowired
    private SplitSmcCodeService splitSmcCodeService;

	@Resource
	private PsiPurchaseSendJpService psiPurchaseSendJpService;

	public List<OpsPurchaseStatusToWMDto> operateToReady(List<OpsRequestpurchase> requestPurchaseList, String type)  throws OpsException {
		List<OpsPurchaseStatusToWMDto> wmdto = new ArrayList<OpsPurchaseStatusToWMDto>();
		JSONObject ret = requestPurchaseService.operateCalMerge(requestPurchaseList);
		if (ret.containsKey("mergedata") && ret.get("mergedata") != null) {
			List<OpsRequestpurchaseDto> merge = JSON.parseArray(JSON.toJSONString(ret.get("mergedata")),
					OpsRequestpurchaseDto.class);
			if (!CollectionUtils.isEmpty(merge)) {
				// 将合并的数据更新状态为2，待合并采购
				List<Long> ids = merge.stream().map(OpsRequestpurchaseDto::getId).collect(Collectors.toList());
				for (int i = 0; i < ids.size(); i++) {
					if (i % 2000 == 0) {
						List<Long> a = new ArrayList<Long>();
						if (i + 2000 < ids.size()) {
							a = ids.subList(i, i + 2000);
						} else {
							a = ids.subList(i, ids.size());
						}
						poBatchDao.updateRequestById(a, RequestPurchaseStatusEnum.DAICAIGOU, null);
					}
				}
				wmdto = requestPurchaseService.mergeToPurchase(merge, "A");
			}
		}
		if (ret.containsKey("onedata") && ret.get("onedata") != null) {
			List<OpsRequestpurchaseDto> ready = JSON.parseArray(JSON.toJSONString(ret.get("onedata")),
					OpsRequestpurchaseDto.class);
			if (!CollectionUtils.isEmpty(ready)) {
				// 生成批次号
				Calendar calendar = Calendar.getInstance();
				Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
				Integer minute = calendar.get(Calendar.MINUTE);
				String version = type + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + hour.toString()
						+ minute.toString() + "A";
				// 准备自动发单状态
				List<Long> ids = ready.stream().map(OpsRequestpurchaseDto::getId).collect(Collectors.toList());
				for (int i = 0; i < ids.size(); i++) {
					if (i % 2000 == 0) {
						List<Long> a = new ArrayList<Long>();
						if (i + 2000 < ids.size()) {
							a = ids.subList(i, i + 2000);
						} else {
							a = ids.subList(i, ids.size());
						}
						poBatchDao.updateRequestById(a, RequestPurchaseStatusEnum.AUTOREADY, version);
					}
				}
			}
		}
		return wmdto;
	}

	@Override
	public List<OpsPurchaseStatusToWMDto> requestToReadyJP()  throws OpsException{
		SupplierExample ex = new SupplierExample();
		ex.createCriteria().andIdEqualTo("JP").andIsautosendEqualTo(true);
		List<Supplier> supplierList = supplierMapper.selectByExample(ex);
		if (CollectionUtils.isEmpty(supplierList)) {
			return null;
		}
		List<String> status = new ArrayList<String>();
		status.add(RequestPurchaseStatusEnum.CHULIZHONG);
		status.add(RequestPurchaseStatusEnum.DAICAIGOU);
		OpsRequestpurchaseExample examReq = new OpsRequestpurchaseExample();
		examReq.createCriteria().andStatecodeIn(status).andSupplieridEqualTo("JP");
		List<OpsRequestpurchase> requestPurchaseList = opsRequestpurchaseMapper.selectByExample(examReq);
		if (CollectionUtils.isEmpty(requestPurchaseList)) {
			return null;
		}
		return operateToReady(requestPurchaseList, "JP");
	}

	@Override
	public List<OpsPurchaseStatusToWMDto> requestToReadyGZ()  throws OpsException{
		SupplierExample ex = new SupplierExample();
		ex.createCriteria().andIdEqualTo("GZ").andIsautosendEqualTo(true);
		List<Supplier> supplierList = supplierMapper.selectByExample(ex);
		if (CollectionUtils.isEmpty(supplierList)) {
			return null;
		}
		List<String> status = new ArrayList<String>();
		status.add(RequestPurchaseStatusEnum.CHULIZHONG);
		status.add(RequestPurchaseStatusEnum.DAICAIGOU);
		OpsRequestpurchaseExample examReq = new OpsRequestpurchaseExample();
		examReq.createCriteria().andStatecodeIn(status).andSupplieridEqualTo("GZ");
		List<OpsRequestpurchase> requestPurchaseList = opsRequestpurchaseMapper.selectByExample(examReq);
		if (CollectionUtils.isEmpty(requestPurchaseList)) {
			return null;
		}
		return operateToReady(requestPurchaseList, "GZ");
	}

	@Override
	public List<OpsPurchaseStatusToWMDto> requestToReadyManufacture()  throws OpsException{
		SupplierExample ex = new SupplierExample();
		ex.createCriteria().andIdIn(manuSupplyList()).andIsautosendEqualTo(true);
		List<Supplier> supplierList = supplierMapper.selectByExample(ex);
		if (CollectionUtils.isEmpty(supplierList)) {
			return null;
		}
		List<String> manuAuto = supplierList.stream().map(Supplier::getId).collect(Collectors.toList());
		List<String> status = new ArrayList<String>();
		status.add(RequestPurchaseStatusEnum.CHULIZHONG);
		status.add(RequestPurchaseStatusEnum.DAICAIGOU);
		OpsRequestpurchaseExample examReq = new OpsRequestpurchaseExample();
		examReq.createCriteria().andStatecodeIn(status).andSupplieridIn(manuAuto);
		List<OpsRequestpurchase> requestPurchaseList = opsRequestpurchaseMapper.selectByExample(examReq);
		if (CollectionUtils.isEmpty(requestPurchaseList)) {
			return null;
		}
		return operateToReady(requestPurchaseList, "MANU");
	}

	@Override
	public List<OpsPurchaseStatusToWMDto> requestToReadyOverSea()  throws OpsException{
		SupplierExample ex = new SupplierExample();
		ex.createCriteria().andIdNotIn(overSeaNotInList()).andIsautosendEqualTo(true);
		List<Supplier> supplierList = supplierMapper.selectByExample(ex);
		if (CollectionUtils.isEmpty(supplierList)) {
			return null;
		}
		List<String> auto = supplierList.stream().map(Supplier::getId).collect(Collectors.toList());
		List<String> status = new ArrayList<String>();
		status.add(RequestPurchaseStatusEnum.CHULIZHONG);
		status.add(RequestPurchaseStatusEnum.DAICAIGOU);
		OpsRequestpurchaseExample examReq = new OpsRequestpurchaseExample();
		examReq.createCriteria().andStatecodeIn(status).andSupplieridIn(auto);
		List<OpsRequestpurchase> requestPurchaseList = opsRequestpurchaseMapper.selectByExample(examReq);
		if (CollectionUtils.isEmpty(requestPurchaseList)) {
			return null;
		}
		return operateToReady(requestPurchaseList, "OVERSEA");
	}

	/***
	 * bug13940 使用字段参数控制发单供应商集合
	 */
	@Override
	public List<OpsPurchaseStatusToWMDto> requestToReadyOverSeaIn(String dic)  throws OpsException{
		List<String> supplierin = overSeaInList(dic);
		if (CollectionUtils.isEmpty(supplierin)) {
			return null;
		}
		SupplierExample ex = new SupplierExample();
		ex.createCriteria().andIdIn(supplierin).andIsautosendEqualTo(true);
		List<Supplier> supplierList = supplierMapper.selectByExample(ex);
		if (CollectionUtils.isEmpty(supplierList)) {
			return null;
		}
		List<String> manuAuto = supplierList.stream().map(Supplier::getId).collect(Collectors.toList());
		List<String> status = new ArrayList<String>();
		status.add(RequestPurchaseStatusEnum.CHULIZHONG);
		status.add(RequestPurchaseStatusEnum.DAICAIGOU);
		OpsRequestpurchaseExample examReq = new OpsRequestpurchaseExample();
		examReq.createCriteria().andStatecodeIn(status).andSupplieridIn(manuAuto);
		List<OpsRequestpurchase> requestPurchaseList = opsRequestpurchaseMapper.selectByExample(examReq);
		if (CollectionUtils.isEmpty(requestPurchaseList)) {
			return null;
		}
		return operateToReady(requestPurchaseList, "OVERSEA");

	}

	/***
	 * bug13940 使用字段参数控制发单供应商集合
	 */
	@Override
	public List<OpsPurchaseStatusToWMDto> requestToReadyOverSeaNotIn(String dic)  throws OpsException{
		List<String> supplierin = overSeaInList(dic);
		if (CollectionUtils.isEmpty(supplierin)) {
			return null;
		}
		SupplierExample ex = new SupplierExample();
		ex.createCriteria().andIdNotIn(overSeaNotInList()).andIdNotIn(supplierin).andIsautosendEqualTo(true);
		List<Supplier> supplierList = supplierMapper.selectByExample(ex);
		if (CollectionUtils.isEmpty(supplierList)) {
			return null;
		}
		List<String> manuAuto = supplierList.stream().map(Supplier::getId).collect(Collectors.toList());
		List<String> status = new ArrayList<String>();
		status.add(RequestPurchaseStatusEnum.CHULIZHONG);
		status.add(RequestPurchaseStatusEnum.DAICAIGOU);
		OpsRequestpurchaseExample examReq = new OpsRequestpurchaseExample();
		examReq.createCriteria().andStatecodeIn(status).andSupplieridIn(manuAuto);
		List<OpsRequestpurchase> requestPurchaseList = opsRequestpurchaseMapper.selectByExample(examReq);
		if (CollectionUtils.isEmpty(requestPurchaseList)) {
			return null;
		}
		return operateToReady(requestPurchaseList, "OVERSEA");

	}

	@Override
	public List<OpsPurchaseStatusToWMDto> readyToPurchaseJP() {
		// 请购写入采购
		OpsRequestpurchaseExample examReq = new OpsRequestpurchaseExample();
		examReq.createCriteria().andStatecodeEqualTo(RequestPurchaseStatusEnum.AUTOREADY).andSupplieridEqualTo("JP");
		List<OpsRequestpurchase> reList = opsRequestpurchaseMapper.selectByExample(examReq);
		if (CollectionUtils.isEmpty(reList)) {
			return null;
		}
		return requestToPurchase(reList);
	}

	@Override
	public List<OpsPurchaseStatusToWMDto> readyToPurchaseGZ() {
		// 请购写入采购
		OpsRequestpurchaseExample exam = new OpsRequestpurchaseExample();
		exam.createCriteria().andStatecodeEqualTo(RequestPurchaseStatusEnum.AUTOREADY).andSupplieridEqualTo("GZ");
		List<OpsRequestpurchase> reList = opsRequestpurchaseMapper.selectByExample(exam);
		if (CollectionUtils.isEmpty(reList)) {
			return null;
		}
		return requestToPurchase(reList);
	}

	@Override
	public List<OpsPurchaseStatusToWMDto> readyToPurchaseManufacture() {
		// 请购写入采购
		OpsRequestpurchaseExample exam = new OpsRequestpurchaseExample();
		exam.createCriteria().andStatecodeEqualTo(RequestPurchaseStatusEnum.AUTOREADY)
				.andSupplieridIn(manuSupplyList());
		List<OpsRequestpurchase> reList = opsRequestpurchaseMapper.selectByExample(exam);
		if (CollectionUtils.isEmpty(reList)) {
			return null;
		}
		return requestToPurchase(reList);
	}

	@Override
	public List<OpsPurchaseStatusToWMDto> readyToPurchaseOverSea() {
		// 请购写入采购
		OpsRequestpurchaseExample exam = new OpsRequestpurchaseExample();
		exam.createCriteria().andStatecodeEqualTo(RequestPurchaseStatusEnum.AUTOREADY)
				.andSupplieridNotIn(overSeaNotInList());
		List<OpsRequestpurchase> reList = opsRequestpurchaseMapper.selectByExample(exam);
		if (CollectionUtils.isEmpty(reList)) {
			return null;
		}
		return requestToPurchase(reList);
	}

	@Override
	public List<OpsPurchaseStatusToWMDto> readyToPurchaseOverSeaIn(String dic) {
		List<String> supplierin = overSeaInList(dic);
		if (CollectionUtils.isEmpty(supplierin)) {
			return null;
		}
		// 请购写入采购
		OpsRequestpurchaseExample exam = new OpsRequestpurchaseExample();
		exam.createCriteria().andStatecodeEqualTo(RequestPurchaseStatusEnum.AUTOREADY).andSupplieridIn(supplierin);
		List<OpsRequestpurchase> reList = opsRequestpurchaseMapper.selectByExample(exam);
		if (CollectionUtils.isEmpty(reList)) {
			return null;
		}
		return requestToPurchase(reList);

	}

	@Override
	public List<OpsPurchaseStatusToWMDto> readyToPurchaseOverSeaNotIn(String dic) {
		List<String> supplierin = overSeaInList(dic);
		if (CollectionUtils.isEmpty(supplierin)) {
			return null;
		}
		// 请购写入采购
		OpsRequestpurchaseExample exam = new OpsRequestpurchaseExample();
		exam.createCriteria().andStatecodeEqualTo(RequestPurchaseStatusEnum.AUTOREADY)
				.andSupplieridNotIn(overSeaNotInList()).andSupplieridNotIn(supplierin);
		List<OpsRequestpurchase> reList = opsRequestpurchaseMapper.selectByExample(exam);
		if (CollectionUtils.isEmpty(reList)) {
			return null;
		}
		return requestToPurchase(reList);
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

	public List<String> overSeaNotInList() {
		// 获取海外待采购数据
		List<String> suppilyList = new ArrayList<>();
		suppilyList.add("CN");
		suppilyList.add("CM");
		suppilyList.add("YZ");
		suppilyList.add("CT");
		suppilyList.add("TZ");
		suppilyList.add("GZ");
		suppilyList.add("JP");
		return suppilyList;
	}

	public List<OpsPurchaseStatusToWMDto> requestToPurchase(List<OpsRequestpurchase> items) {
		List<OpsPurchaseStatusToWMDto> opsSendWmList = new ArrayList<OpsPurchaseStatusToWMDto>();
		// bug 13662 AP单号重新生成
		// List<OpsRequestpurchase> apsplit = items.stream()
		// .filter(a -> a.getSplititemno() != null && StringUtils.equals("AP",
		// a.getSupplierid()))
		// .collect(Collectors.toList());
		// bug14219 AP所有订单均重新编号，为了子项号从1开始
		List<OpsRequestpurchase> apsplit = items.stream().filter(a -> StringUtils.equals("AP", a.getSupplierid()))
				.collect(Collectors.toList());
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
		// 重算倒挂的货期
		org.joda.time.LocalDate today = new org.joda.time.LocalDate(new Date());
		List<OpsRequestpurchase> before = items.stream().filter(s -> s.getRequesttime().compareTo(today.toDate()) < 0)
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(before)) {
			items.removeAll(before);
			before = requestPurchasePreService.calDlvInfo(before);
			for (OpsRequestpurchase i : before) {
				OpsPurchaseorder j = new OpsPurchaseorder();
				requestPurchaseService.generatePurchaseOrder(i, j);
				j.setOperator("autoSendIT");
				j.setInformation(j.getInformation() + ";autoSend;");
				j.setSendversion(i.getSendversion());
				// bug11841 特殊订单，发单时型号需要前面加*进行订货
				if (StringUtils.isNotBlank(i.getProducttag()) && StringUtils.equals("1", i.getProducttag())
						&& !j.getInformation().contains("add*")) {
					j.setInformation(j.getInformation() + ";add*;");
				}
				opsPurchaseorderMapper.insertSelective(j);
				// 回写给wm状态更新
				OpsPurchaseStatusToWMDto opsSendWm = new OpsPurchaseStatusToWMDto();
				opsSendWm.setOpsPurchaseorder(j);
				opsSendWm.setOpsRequestpurchase(i);
				opsSendWmList.add(opsSendWm);
				// 调用更新请购单状态
				OpsRequestpurchase requestUpdate = new OpsRequestpurchase();
				requestUpdate.setId(i.getId());
				requestUpdate.setStatecode(RequestPurchaseStatusEnum.YIFASONG);
				requestUpdate.setUpdatetime(new Date());
				requestUpdate.setOperator("autoSendIT");
				requestUpdate.setHopeexportdate(i.getHopeexportdate());
				requestUpdate.setTranstype(i.getTranstype());
				// bug13662 请购表新增采购单号字段
				requestUpdate.setPoOrderNo(j.getOrderno());
				requestUpdate.setPoItemNo(j.getItemno());
				if (j.getSplititemno() != null) {
					requestUpdate.setPoSplitNo(j.getSplititemno());
				} else {
					// bug14690无拆分单号时赋值为null
					requestUpdate.setPoSplitNo(null);
				}
				opsRequestpurchaseMapper.updateByPrimaryKeySelective(requestUpdate);
			}
		}
		if (!CollectionUtils.isEmpty(items)) {
			for (OpsRequestpurchase i : items) {
				OpsPurchaseorder j = new OpsPurchaseorder();
				requestPurchaseService.generatePurchaseOrder(i, j);
				j.setOperator("autoSendIT");
				j.setInformation(j.getInformation() + ";autoSend;");
				j.setSendversion(i.getSendversion());
				// bug11841 特殊订单，发单时型号需要前面加*进行订货
				if (StringUtils.isNotBlank(i.getProducttag()) && StringUtils.equals("1", i.getProducttag())
						&& !j.getInformation().contains("add*")) {
					j.setInformation(j.getInformation() + ";add*;");
				}
				opsPurchaseorderMapper.insertSelective(j);
				// 回写给wm状态更新
				OpsPurchaseStatusToWMDto opsSendWm = new OpsPurchaseStatusToWMDto();
				opsSendWm.setOpsPurchaseorder(j);
				opsSendWm.setOpsRequestpurchase(i);
				opsSendWmList.add(opsSendWm);
				// 调用更新请购单状态
				OpsRequestpurchase requestUpdate = new OpsRequestpurchase();
				requestUpdate.setId(i.getId());
				requestUpdate.setStatecode(RequestPurchaseStatusEnum.YIFASONG);
				requestUpdate.setUpdatetime(new Date());
				requestUpdate.setOperator("autoSendIT");
				// bug13662 请购表新增采购单号字段
				requestUpdate.setPoOrderNo(j.getOrderno());
				requestUpdate.setPoItemNo(j.getItemno());
				if (j.getSplititemno() != null) {
					requestUpdate.setPoSplitNo(j.getSplititemno());
				} else {
					// bug14690无拆分单号时赋值为null
					requestUpdate.setPoSplitNo(null);
				}
				opsRequestpurchaseMapper.updateByPrimaryKeySelective(requestUpdate);
			}
		}

		// // 写入wm状态
		// if (!CollectionUtils.isEmpty(opsSendWmList)) {
		// opsWmDispatchForOrderFeignApi.sendForPo(opsSendWmList);
		// }
		return opsSendWmList;
	}

	@Override
	public Integer purchaseAutoJP(boolean isAuto) throws Exception {
		SupplierExample example = new SupplierExample();
		example.createCriteria().andIdEqualTo("JP").andIsautosendEqualTo(true).andEmailIsNotNull();
		List<Supplier> supplierList = supplierMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(supplierList)) {
			return 0;
		}
		String likeString = "%A";
		if (!isAuto) {
			likeString = "%M";
		}
		// 采购文件生成并发送邮件
		OpsPurchaseorderExample ex = new OpsPurchaseorderExample();
		ex.createCriteria().andStatecodeEqualTo(PurchaseOrderStatusEnum.DAICHULI).andSupplieridEqualTo("JP")
				.andSendversionLike(likeString);
		ex.setOrderByClause("orderNo,itemNo");
		List<OpsPurchaseorder> items = opsPurchaseorderMapper.selectByExample(ex);
		if (!CollectionUtils.isEmpty(items)) {
			reCalExportday(items);
			sendPre(items);
			// bug19186 日本发单切换为从PSI发单,最低售价的附件也从PSI推送 B91717
			// bug12457 最低售价附件发送邮件
//			minPriceService.minPriceOperate(items);
		}

		// 日本文件生成
//		String textInfo = purchaseExportTxtService.textInfoJP(likeString);
//		if (StringUtils.isNotBlank(textInfo) && StringUtils.isNotBlank(supplierList.get(0).getEmail())) {
//			String pathJP = purchaseExportTxtService.writeToTxtJP(textInfo);
//			OpsPurchaseinvoiceExample exam = new OpsPurchaseinvoiceExample();
//			exam.createCriteria().andStatecodeEqualTo("0").andSupplieridEqualTo("JP").andSendversionLike(likeString);
//			OpsPurchaseinvoice record = new OpsPurchaseinvoice();
//			record.setStatecode("1");
//			record.setUpdatetime(new Date());
//			record.setSendtime(new Date());
//			record.setFilepath(pathJP);
//			opsPurchaseinvoiceMapper.updateByExampleSelective(record, exam);
//			// 调用日本邮件发送接口，发送TXT邮件给日本
//			// purchaseSendEmail.sendMailJP(pathJP);
//			// 直接调用发送邮件
//			if (StringUtils.isNotBlank(supplierList.get(0).getEmail())) {
//				purchaseMailService.sendMailJP(pathJP, supplierList.get(0).getEmail());
//			}
//		}
		// bug19186 日本发单切换为从PSI发单 B91717
		psiPurchaseSendJpService.psiJpOrderSend(items, likeString, supplierList.get(0).getEmail());
		return items.size();
	}

	@Override
	public Integer purchaseAutoGZ(boolean isAuto) throws Exception {
		SupplierExample exam = new SupplierExample();
		exam.createCriteria().andIdEqualTo("GZ").andIsautosendEqualTo(true);
		List<Supplier> supplierList = supplierMapper.selectByExample(exam);
		if (CollectionUtils.isEmpty(supplierList)) {
			return 0;
		}
		String likeString = "%A";
		if (!isAuto) {
			likeString = "%M";
		}
		OpsPurchaseorderExample ex = new OpsPurchaseorderExample();
		ex.createCriteria().andStatecodeEqualTo(PurchaseOrderStatusEnum.DAICHULI).andSupplieridEqualTo("GZ")
				.andSendversionLike(likeString);
		ex.setOrderByClause("orderNo,itemNo");
		List<OpsPurchaseorder> items = opsPurchaseorderMapper.selectByExample(ex);
		if (!CollectionUtils.isEmpty(items)) {
			reCalExportday(items);
			sendPre(items);
		}
		Integer num = purchaseSendOrderGZService.operateGZ(likeString);
		return num;
	}

	@Override
	public Integer purchaseAutoManufacture(boolean isAuto) throws Exception {
		SupplierExample exam = new SupplierExample();
		exam.createCriteria().andIdIn(manuSupplyList()).andIsautosendEqualTo(true);
		List<Supplier> supplierList = supplierMapper.selectByExample(exam);
		if (CollectionUtils.isEmpty(supplierList)) {
			return 0;
		}
		String likeString = "%A";
		if (!isAuto) {
			likeString = "%M";
		}
		List<String> manuAuto = supplierList.stream().map(Supplier::getId).collect(Collectors.toList());
		OpsPurchaseorderExample ex = new OpsPurchaseorderExample();
		ex.createCriteria().andStatecodeEqualTo(PurchaseOrderStatusEnum.DAICHULI).andSupplieridIn(manuAuto)
				.andSendversionLike(likeString);
		ex.setOrderByClause("orderNo,itemNo");
		List<OpsPurchaseorder> items = opsPurchaseorderMapper.selectByExample(ex);
		if (!CollectionUtils.isEmpty(items)) {
			reCalExportday(items);
			sendPre(items);
		}

		return purchaseSendOrderManuService.operateManufacture(likeString);
	}

	@Override
	public Integer purchaseAutoOverSea(boolean isAuto) throws Exception {
		SupplierExample exam = new SupplierExample();
		exam.createCriteria().andIdNotIn(overSeaNotInList()).andIsautosendEqualTo(true).andEmailIsNotNull();
		List<Supplier> supplierList = supplierMapper.selectByExample(exam);
		if (CollectionUtils.isEmpty(supplierList)) {
			return 0;
		}
		String likeString = "%A";
		if (!isAuto) {
			likeString = "%M";
		}
		List<String> auto = supplierList.stream().map(Supplier::getId).collect(Collectors.toList());
		OpsPurchaseorderExample ex = new OpsPurchaseorderExample();
		ex.createCriteria().andStatecodeEqualTo(PurchaseOrderStatusEnum.DAICHULI).andSupplieridIn(auto)
				.andSendversionLike(likeString);
		ex.setOrderByClause("orderNo,itemNo");
		List<OpsPurchaseorder> items = opsPurchaseorderMapper.selectByExample(ex);
		if (!CollectionUtils.isEmpty(items)) {
			reCalExportday(items);
			sendPre(items);
		}

		// 获取仓库信息
		ResultVo<List<OpsWarehouse>> warelist = wmCommonService.getWarehouseCanPurchase();
		Map<String, OpsWarehouse> opswarehousemap = new HashMap<String, OpsWarehouse>();
		if (warelist.isSuccess()) {
			warelist.getData().forEach(i -> {
				opswarehousemap.put(i.getWarehouseCode(), i);
			});
		}
		// 海外文件生成
		for (Supplier a : supplierList) {
			if (StringUtils.isNotBlank(a.getEmail())) {
				// bug19186 海外发单切换为从PSI发单 B91717
				List<String> files = purchaseExportTxtService.writeToExcel(a.getId().trim(), a, opswarehousemap,
						likeString);
				if (!CollectionUtils.isEmpty(files)) {
					// purchaseSendEmail.sendMailOverSea(files);
					if (StringUtils.isNotBlank(a.getEmail())) {
						purchaseMailService.sendMailOverSea(files, a.getEmail());
					}
				}
			}
		}

		return items.size();
	}

	@Override
	public Integer purchaseAutoOverSeaIn(boolean isAuto, String dic) throws Exception {
		SupplierExample exam = new SupplierExample();
		exam.createCriteria().andIdIn(overSeaInList(dic)).andIsautosendEqualTo(true).andEmailIsNotNull();
		List<Supplier> supplierList = supplierMapper.selectByExample(exam);
		if (CollectionUtils.isEmpty(supplierList)) {
			return 0;
		}
		String likeString = "%A";
		if (!isAuto) {
			likeString = "%M";
		}
		List<String> auto = supplierList.stream().map(Supplier::getId).collect(Collectors.toList());
		OpsPurchaseorderExample ex = new OpsPurchaseorderExample();
		ex.createCriteria().andStatecodeEqualTo(PurchaseOrderStatusEnum.DAICHULI).andSupplieridIn(auto)
				.andSendversionLike(likeString);
		ex.setOrderByClause("orderNo,itemNo");
		List<OpsPurchaseorder> items = opsPurchaseorderMapper.selectByExample(ex);
		if (!CollectionUtils.isEmpty(items)) {
			reCalExportday(items);
			sendPre(items);
		}

		// 获取仓库信息
		ResultVo<List<OpsWarehouse>> warelist = wmCommonService.getWarehouseCanPurchase();
		Map<String, OpsWarehouse> opswarehousemap = new HashMap<String, OpsWarehouse>();
		if (warelist.isSuccess()) {
			warelist.getData().forEach(i -> {
				opswarehousemap.put(i.getWarehouseCode(), i);
			});
		}
		// 海外文件生成
		for (Supplier a : supplierList) {
			if (StringUtils.isNotBlank(a.getEmail())) {
				// bug19186 海外发单切换为从PSI发单 B91717
				List<String> files = purchaseExportTxtService.writeToExcel(a.getId().trim(), a, opswarehousemap,
						likeString);
				if (!CollectionUtils.isEmpty(files)) {
					// purchaseSendEmail.sendMailOverSea(files);
					if (StringUtils.isNotBlank(a.getEmail())) {
						purchaseMailService.sendMailOverSea(files, a.getEmail());
					}
				}
			}
		}

		return items.size();
	}

	@Override
	public Integer purchaseAutoOverSeaNotIn(boolean isAuto, String dic) throws Exception {
		SupplierExample exam = new SupplierExample();
		exam.createCriteria().andIdNotIn(overSeaNotInList()).andIdNotIn(overSeaInList(dic)).andIsautosendEqualTo(true)
				.andEmailIsNotNull();
		List<Supplier> supplierList = supplierMapper.selectByExample(exam);
		if (CollectionUtils.isEmpty(supplierList)) {
			return 0;
		}
		String likeString = "%A";
		if (!isAuto) {
			likeString = "%M";
		}
		List<String> auto = supplierList.stream().map(Supplier::getId).collect(Collectors.toList());
		OpsPurchaseorderExample ex = new OpsPurchaseorderExample();
		ex.createCriteria().andStatecodeEqualTo(PurchaseOrderStatusEnum.DAICHULI).andSupplieridIn(auto)
				.andSendversionLike(likeString);
		ex.setOrderByClause("orderNo,itemNo");
		List<OpsPurchaseorder> items = opsPurchaseorderMapper.selectByExample(ex);
		if (!CollectionUtils.isEmpty(items)) {
			reCalExportday(items);
			sendPre(items);
		}

		// 获取仓库信息
		ResultVo<List<OpsWarehouse>> warelist = wmCommonService.getWarehouseCanPurchase();
		Map<String, OpsWarehouse> opswarehousemap = new HashMap<String, OpsWarehouse>();
		if (warelist.isSuccess()) {
			warelist.getData().forEach(i -> {
				opswarehousemap.put(i.getWarehouseCode(), i);
			});
		}
		// 海外文件生成
		for (Supplier a : supplierList) {
			if (StringUtils.isNotBlank(a.getEmail())) {
				// bug19186 海外发单切换为从PSI发单 B91717
				List<String> files = purchaseExportTxtService.writeToExcel(a.getId().trim(), a, opswarehousemap,
						likeString);
				if (!CollectionUtils.isEmpty(files)) {
					// purchaseSendEmail.sendMailOverSea(files);
					if (StringUtils.isNotBlank(a.getEmail())) {
						purchaseMailService.sendMailOverSea(files, a.getEmail());
					}
				}
			}
		}

		return items.size();
	}

	public void sendPre(List<OpsPurchaseorder> items) throws Exception {
		// bug10347 马腾 获取对应供应商采购仓的期望到货仓
		List<OpsPoDestinationConfig> hopelist = opsPoDestinationConfigMapper.selectByExample(null);
		Map<String, String> hopemap = new HashMap<String, String>();
		hopelist.forEach(i -> {
			// bug11329马腾 增加采购类型限制
			if (!hopemap.containsKey(i.getSupplierid() + "," + i.getWarehouseid() + "," + i.getOrdertype())) {
				hopemap.put(i.getSupplierid() + "," + i.getWarehouseid() + "," + i.getOrdertype(),
						i.getHopereceivewarehouse());
			}
		});
		// bug12040发单特殊型号前面增加*号
		OpsPoModelSendConfigExample configex = new OpsPoModelSendConfigExample();
		configex.createCriteria().andIsDeletedEqualTo(false);
		List<OpsPoModelSendConfig> modelConfig = opsPoModelSendConfigMapper.selectByExample(configex);
		// bug12255增加业务备注
		OpsPoAutosendRemarkConfigExample remarkex = new OpsPoAutosendRemarkConfigExample();
		remarkex.createCriteria().andIsDeletedEqualTo(false).andSupplierClassEqualTo("JP");
		List<OpsPoAutosendRemarkConfig> configList = opsPoAutosendRemarkConfigMapper.selectByExample(remarkex);

		items.forEach(item -> {
			// bug10347 马腾 获取采购单对应的期望到货仓并赋值更新
			// bug11329马腾 增加采购类型限制
			if (hopemap.containsKey(
					item.getSupplierid() + "," + item.getReceivewarehouseid() + "," + item.getPurchasetype())) {
				item.setHopereceivewarehouse(hopemap
						.get(item.getSupplierid() + "," + item.getReceivewarehouseid() + "," + item.getPurchasetype()));
			} else {
				item.setHopereceivewarehouse("KBJ");
			}

			int i = 0;
			OpsPurchaseinvoice invoice = new OpsPurchaseinvoice();
			invoice = convert(item, invoice);
			if (StringUtils.equals("0", item.getSpecmark())) {
				if (StringUtils.equals("AP", item.getSupplierid())) {
					invoice.setPono(item.getOrderno());
					invoice.setLineitem(item.getItemno());
				} else {
					if (item.getSplititemno() == null) {
						invoice.setPono(item.getOrderno() + "-" + item.getItemno().toString());
					} else {
						invoice.setPono(item.getOrderno() + "-" + item.getItemno().toString() + "-"
								+ item.getSplititemno().toString());
					}
					invoice.setLineitem(1);
				}

			} else {
				invoice.setPono(item.getOrderno());
				invoice.setLineitem(item.getItemno());
			}
			// bug12040发单特殊型号前面增加*号
			for (OpsPoModelSendConfig m : modelConfig) {
				if (StringUtils.isBlank(m.getSupplier()) || StringUtils.equals(m.getSupplier(), item.getSupplierid())) {
					// 模糊匹配
					if (m.getFuzzy()) {
						if (Pattern.matches(m.getModel(), item.getModelno())) {
							invoice.setModelno(m.getModi() + invoice.getModelno());
						}
					} else {
						if (StringUtils.equals(item.getModelno(), m.getModel())) {
							invoice.setModelno(m.getModi() + invoice.getModelno());
						}
					}
				}
			}

			// 日本订单
			// 判断日本订单,如何判断是日本订单
			if ("JP".equals(item.getSupplierid())) {
				// 3c-
				if (item.getInspectiontype() != null) {
					if (StringUtils.equals(item.getInspectiontype(), "3C-A")) {
						if (!item.getModelno().toUpperCase().startsWith("3C-")
								&& StringUtils.equals("0", item.getSpecmark())) {
							if (invoice.getModelno().startsWith("*")) {
								invoice.setModelno("*3C-" + item.getModelno());
							} else {
								invoice.setModelno("3C-" + item.getModelno());
							}
							invoice.setAdd3c("1");
						}
					}
				}
				// 暂无需对应 -sp
				// deliveryflag
				if (OrderTypeEnum.KUCUNDINGDAN.containsKey(item.getOrdtype())) {
					invoice.setDeliveryflag("2");
				} else {
					invoice.setDeliveryflag("1");
				}
				// locationNo
				invoice.setLocationno(getLoactionNo(item.getModelno(), item.getReceivewarehouseid()));

				// 阀汇流板,组装原件型号前加*
				if (StringUtils.equals("2", item.getSpecmark())) {
					invoice.setModelno("*" + item.getModelno());
				}
				// bug12349取消二次电池备注
				// specmark
				// if (StringUtils.isNotBlank(item.getRemark()) &&
				// item.getRemark().contains("二次电池")) {
				// invoice.setSpecmark("A");
				// } else {
				invoice.setSpecmark(" ");
				// }
				// 20220705新需求，B、K类型订单且型号在提供的清单列表中的需要增加P标识
				if (StringUtils.equals(PurchaseTypeEnum.BIN.getTypeCode(), item.getPurchasetype())
						|| StringUtils.equals(PurchaseTypeEnum.PRE.getTypeCode(), item.getPurchasetype())) {
					// 判断型号
					OpsJpPModelnoExample jpex = new OpsJpPModelnoExample();
					jpex.createCriteria().andModelnoEqualTo(item.getModelno()).andIsdeleteEqualTo("0");
					List<OpsJpPModelno> jptemp = opsJpPModelnoMapper.selectByExample(jpex);
					if (jptemp != null && jptemp.size() > 0) {
						invoice.setSpecmark("P");
					}
				}
				// vipcode
				// bug10705 马腾 增加供应商查询
				// bug12349变更下述数据库查询的返回值
				// bug12284先行在库订单，在重点项目标识为true时才显示vipcode
				// bug 20054,用end_user替换userno
				if (StringUtils.equals(PurchaseTypeEnum.PRE.getTypeCode(), item.getPurchasetype())) {
					if (StringUtils.isNotBlank(item.getInfojson())) {
						RequestPurchaseInfo info = JSONObject.parseObject(item.getInfojson(),
								RequestPurchaseInfo.class);
						if (info.isVip()) {
							List<OpsCustomerProperty> vipList = purchaseOrderDao.getVIPCode(
									StringUtils.isBlank(item.getEndUser()) ? item.getCustomerno() : item.getEndUser(),
									item.getSupplierid());
							if (vipList != null && vipList.size() > 0) {
								invoice.setVipcode(vipList.get(0).getVipcode());
							}
						}
					}
				} else {
					List<OpsCustomerProperty> vipList = purchaseOrderDao.getVIPCode(
							StringUtils.isBlank(item.getEndUser()) ? item.getCustomerno() : item.getEndUser(),
							item.getSupplierid());
					if (vipList != null && vipList.size() > 0) {
						invoice.setVipcode(vipList.get(0).getVipcode());
					}
				}
				// bug12255增加特殊备注
				List<OpsPoAutosendRemarkConfig> tempConfig = configList.stream()
						.filter(c -> StringUtils.equals(
								StringUtils.isNotBlank(item.getEndUser()) ? item.getEndUser() : item.getCustomerno(),
								c.getCustomerNo()))
						.collect(Collectors.toList());
				if (org.apache.commons.collections.CollectionUtils.isNotEmpty(tempConfig)) {
					for (OpsPoAutosendRemarkConfig c : tempConfig) {
						if (StringUtils.isBlank(c.getModelno())) {
							if (StringUtils.isNotBlank(item.getServerremark())) {
								if (!StringUtils.equals(item.getServerremark(), c.getRemark()))
									invoice.setServerremark(item.getServerremark() + ";" + c.getRemark());
							} else {
								invoice.setServerremark(c.getRemark());
							}
						} else {
							if (c.getFuzzy()) {
								// 模糊匹配
								if (Pattern.matches(c.getModelno(), item.getModelno())) {
									if (StringUtils.isNotBlank(item.getServerremark())) {
										if (!StringUtils.equals(item.getServerremark(), c.getRemark()))
											invoice.setServerremark(item.getServerremark() + ";" + c.getRemark());
									} else {
										invoice.setServerremark(c.getRemark());
									}
								}
							} else {
								if (StringUtils.equals(c.getModelno(), item.getModelno())) {
									if (StringUtils.isNotBlank(item.getServerremark())) {
										if (!StringUtils.equals(item.getServerremark(), c.getRemark()))
											invoice.setServerremark(item.getServerremark() + ";" + c.getRemark());
									} else {
										invoice.setServerremark(c.getRemark());
									}
								}
							}
						}
					}
				}

				// 判断是否无法接收整型号，需要拆分成阀板型号
				ProductBomDetailVtExample example = new ProductBomDetailVtExample();
				example.createCriteria().andModelnoEqualTo(item.getModelno());
				example.setOrderByClause("isBaseBoard desc");
				List<ProductBomDetailVt> vtlist = productBomDetailVtMapper.selectByExample(example);
				if (vtlist == null || vtlist.size() == 0) {
					// 写入发票表
					i += opsPurchaseinvoiceMapper.insertSelective(invoice);
				} else {
					// 判断是否数据异常
					long board = vtlist.stream().filter(a -> a.getIsbaseboard()).count();
					if (board > 1) {
						logger.error("采购单写入发票表失败，productBomDetailVt数据异常：" + invoice.getPono() + ";modelno:"
								+ item.getModelno());
					} else {
						for (int j = 0; j < vtlist.size(); j++) {
							// 拆分成阀汇流板写入发票表
							if (j == 0) {
								if (vtlist.get(j).getIsbaseboard()) {
									invoice.setModelno(vtlist.get(j).getSubmodelno());
								} else {
									// 阀汇流板型号数据维护错误，提示
									logger.error("采购单写入发票表失败，productBomDetailVt数据异常：" + invoice.getPono() + ";modelno:"
											+ item.getModelno());
									break;
								}
							} else {
								invoice.setModelno("*" + vtlist.get(j).getSubmodelno());
							}
							invoice.setLineitem(j + 1);
							invoice.setQuantity(vtlist.get(j).getQuantity() * item.getQuantity());
							// 拆分标识,为后续合并使用
							invoice.setJpsplitvt(vtlist.size());
							invoice.setUpdatetime(new Date());
							i += opsPurchaseinvoiceMapper.insertSelective(invoice);
						}
					}
				}
			} else {
				// 写入发票表
				invoice.setUpdatetime(new Date());
				i += opsPurchaseinvoiceMapper.insertSelective(invoice);
			}

			// 更新采购单状态
			// bug10347 马腾 增加采购表期望供应商到货仓字段的更新
			if (i > 0) {
				OpsPurchaseorderExample ex = new OpsPurchaseorderExample();
				ex.createCriteria().andIdEqualTo(item.getId()).andStatecodeEqualTo(PurchaseOrderStatusEnum.DAICHULI);
				OpsPurchaseorder exam = new OpsPurchaseorder();
				exam.setStatecode(PurchaseOrderStatusEnum.YIFASONG);
				exam.setUpdatetime(new Date());
				exam.setHopereceivewarehouse(item.getHopereceivewarehouse());
				opsPurchaseorderMapper.updateByExampleSelective(exam, ex);
			} else {
				// 异常
				OpsPurchaseorderExample ex = new OpsPurchaseorderExample();
				ex.createCriteria().andIdEqualTo(item.getId()).andStatecodeEqualTo(PurchaseOrderStatusEnum.DAICHULI);
				OpsPurchaseorder exam = new OpsPurchaseorder();
				exam.setStatecode(PurchaseOrderStatusEnum.SHUJUYICHANG);
				exam.setUpdatetime(new Date());
				exam.setHopereceivewarehouse(item.getHopereceivewarehouse());
				opsPurchaseorderMapper.updateByExampleSelective(exam, ex);
			}
		});
	}

	public String getLoactionNo(String modelNo, String warehouse) {
		OpsWarehouseLocationnoExample example = new OpsWarehouseLocationnoExample();
		example.createCriteria().andModelnoEqualTo(modelNo).andWarehouseidEqualTo(warehouse);
		List<OpsWarehouseLocationno> list = opsWarehouseLocationnoMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0).getLocationno();
		} else {
			return "";
		}
	}

	// 转换
	public OpsPurchaseinvoice convert(OpsPurchaseorder item, OpsPurchaseinvoice invoice) {
		invoice.setOrderno(item.getOrderno());
		invoice.setItemno(item.getItemno());
		if (item.getSplititemno() != null)
			invoice.setSplititemno(item.getSplititemno());
		invoice.setModelno(item.getModelno());
		// bug11841 特殊订单型号前加*订货
		if (StringUtils.isNotBlank(item.getInformation()) && item.getInformation().contains("add*")) {
			invoice.setModelno("*" + invoice.getModelno());
		}
		invoice.setQuantity(item.getQuantity());
		invoice.setTranstype(item.getTranstype());
		invoice.setSpecmark(item.getSpecmark());
		invoice.setShikomianswerno(item.getShikomianswerno());
		invoice.setSupplierid(item.getSupplierid());
		invoice.setReceivewarehouseid(item.getReceivewarehouseid());
		invoice.setInqno(item.getInqno());
		invoice.setSmccode(item.getSmccode());
        invoice.setSubCode(item.getSubCode());
		invoice.setRemark(item.getRemark());
		invoice.setStatecode("0");
		invoice.setCustomerno(item.getCustomerno());
		invoice.setUserno(item.getUserno());
		invoice.setSalesinfono(item.getSalesinfono());
		invoice.setHopedeliverydate(item.getHopedeliverydate());
		invoice.setHopeexportdate(item.getHopeexportdate());
		invoice.setHscode(item.getHscode());
		invoice.setPurchasetype(item.getPurchasetype());
		invoice.setPurchasedate(item.getPurchasedate());
		invoice.setImportfobpriceoriginal(item.getImportfobpriceoriginal());
		invoice.setImportcurrencyid(item.getImportcurrencyid());
		invoice.setSupplierpartno(item.getSupplierpartno());
		invoice.setDeptno(item.getDeptno());
		invoice.setServerremark(item.getServerremark());
		invoice.setInspectiontype(item.getInspectiontype());
		// 2022-09-06,增加greencode
		if (StringUtils.equals(item.getGreencode(), "H")) {
			invoice.setGreencode(item.getGreencode());
		}
		// bug10440 马腾 增加订单类型字段
		invoice.setOrdtype(item.getOrdtype());
		// bug10347 马腾 增加期望供应商到货仓字段
		invoice.setHopereceivewarehouse(item.getHopereceivewarehouse());
		invoice.setSendversion(item.getSendversion());
		// bug12284增加外部信息字段传输
		invoice.setInfojson(item.getInfojson());
		invoice.setSupplierAssignType(item.getSupplierAssignType()); // bug19540 WTSR2025001597-配合PMS分单系统OPS系统发单功能新增
		invoice.setPrepareorderno(item.getPrepareorderno()); // bug20023 采购自动发单给psi时,传递准备订单号
		invoice.setEndUser(item.getEndUser());
		return invoice;
	}

	public void reCalExportday(List<OpsPurchaseorder> items) throws Exception {
		org.joda.time.LocalDate today = new org.joda.time.LocalDate(new Date());
		// bug12373 增加出荷日为当天的订单的出荷日重算
		// bug14859 增加筛选出荷日为空的数据，若BIN订单期望出荷日为空则设置为客户期望货期
		items = items
				.stream().filter(s -> (s.getPurchasedate().compareTo(today.toDate()) < 0
						|| s.getHopeexportdate() == null || s.getHopeexportdate().compareTo(today.toDate()) <= 0))
				.collect(Collectors.toList());
		Date hope = null;
		for (OpsPurchaseorder i : items) {
			if (OrderTypeEnum.BIN.equals(i.getOrdtype())) {
				if (i.getHopeexportdate() == null) {
					i.setHopeexportdate(i.getHopedeliverydate());
				}
				if (i.getHopeexportdate().compareTo(today.toDate()) <= 0) {
					i.setHopeexportdate(today.plusDays(5).toDate());
				}
				continue;
			}
			// bug13662 请购表新增采购单号字段 废除合并表
			OpsRequestpurchaseExample e = new OpsRequestpurchaseExample();
			if (i.getSplititemno() != null) {
				e.createCriteria().andPoOrderNoEqualTo(i.getOrderno()).andPoItemNoEqualTo(i.getItemno())
						.andPoSplitNoEqualTo(i.getSplititemno());
			} else {
				e.createCriteria().andPoOrderNoEqualTo(i.getOrderno()).andPoItemNoEqualTo(i.getItemno())
						.andPoSplitNoIsNull();
			}
			List<OpsRequestpurchase> r = opsRequestpurchaseMapper.selectByExample(e);
			r.forEach(item -> {
				if (item.getOrdtype() != OrderTypeEnum.BIN) {
                    //bugid:20455  如果订单类型 既不是 DR 也不是 CR，则清空希望出荷日
                    if (!OrderTypeEnum.DR.equals(item.getOrdtype())
                            && !OrderTypeEnum.CR.equals(item.getOrdtype())){
                        item.setHopeexportdate(null);; // 将出荷日置空，参与重新计算
                    }
                    // 先行在库不清空运输方式
                    if (!PurchaseTypeEnum.PRE.getTypeCode().equals(item.getPurchasetype())){
                        item.setTranstype(null);
                    }
					item.setSupplierid(i.getSupplierid());
					item.setHopedeliverydate(i.getHopedeliverydate());
				} else {
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(new Date());
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					item.setHopeexportdate(calendar.getTime());
				}
			});
			r = requestPurchasePreService.calDlvInfo(r);
			if (!CollectionUtils.isEmpty(r)) {
				OpsRequestpurchase j = r.stream().sorted(Comparator.comparing(OpsRequestpurchase::getHopeexportdate,
						Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList()).get(0);
				// 阀汇流板specmark！=0，则统一smccode及出荷日
				if (StringUtils.isBlank(j.getSpecmark()) || "0".equals(j.getSpecmark())) {
					hope = null;
				} else if ("1".equals(j.getSpecmark())) {
					hope = j.getHopeexportdate();
				} else if ("2".equals(j.getSpecmark())) {
					if (hope != null) {
						j.setHopeexportdate(hope);
					}
				}
				i.setHopeexportdate(j.getHopeexportdate());
				i.setTranstype(j.getTranstype());
				OpsPurchaseorder p = new OpsPurchaseorder();
				p.setHopeexportdate(j.getHopeexportdate());
				p.setTranstype(j.getTranstype());
				p.setUpdatetime(new Date());
				p.setId(i.getId());
				p.setPurchasedate(new Date());
                //bugid:19186 c14717 20251127 合并smccode
                OpsPoDestinationConfig subCode = splitSmcCodeService.getSubCode(i.getSupplierid(), i.getModelno()
                        , i.getReceivewarehouseid(), i.getPurchasetype(), j.getTranstype());
                if(Objects.nonNull(subCode)){
                    p.setSmccode(subCode.getSmccode());
                    p.setSubCode(subCode.getSubCode());
                }
				opsPurchaseorderMapper.updateByPrimaryKeySelective(p);
			}
		}
	}

	// bug12330增加海外早九点发单的单独作业，亚洲供应商才有早九点发单
	public List<String> overSeaInList(String dic) {
		ResultVo<List<DataCodeVO>> result = dictDataServiceFeignApi.getDataCodes(dic);
		if (result.isSuccess() && !CollectionUtils.isEmpty(result.getData())) {
			List<String> suppilyList = result.getData().stream().map(DataCodeVO::getCode).collect(Collectors.toList());
			return suppilyList;
		}
		return null;
	}

}
