package com.sales.ops.serviceimpl.purchase;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.dto.ips.PsiDataType;
import com.sales.ops.enums.ipsPurchase.IpsPurchaseCommonEnum;
import com.sales.ops.enums.ipsPurchase.IpsPurchaseSendSwitchEnum;
import com.sales.ops.service.ips.IpsPurchaseSendCommonService;
import com.sales.ops.service.ips.PsiPurchaseSendOverSeaService;
import com.sales.ops.service.purchase.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.sales.ops.db.batchdao.PoBatchDao;
import com.sales.ops.db.dao.OpsJpPModelnoMapper;
import com.sales.ops.db.dao.OpsMaxinvoiceMapper;
import com.sales.ops.db.dao.OpsPoAutosendRemarkConfigMapper;
import com.sales.ops.db.dao.OpsPoDestinationConfigMapper;
import com.sales.ops.db.dao.OpsPoModelSendConfigMapper;
import com.sales.ops.db.dao.OpsPurchaseinvoiceMapper;
import com.sales.ops.db.dao.OpsPurchaseorderMapper;
import com.sales.ops.db.dao.OpsRequestpurchaseMapper;
import com.sales.ops.db.dao.OpsWarehouseLocationnoMapper;
import com.sales.ops.db.dao.ProductBomDetailVtMapper;
import com.sales.ops.db.dao.SupplierMapper;
import com.sales.ops.db.entity.OpsCustomer;
import com.sales.ops.db.entity.OpsCustomerProperty;
import com.sales.ops.db.entity.OpsJpPModelno;
import com.sales.ops.db.entity.OpsJpPModelnoExample;
import com.sales.ops.db.entity.OpsMaxinvoice;
import com.sales.ops.db.entity.OpsMaxinvoiceExample;
import com.sales.ops.db.entity.OpsPoAutosendRemarkConfig;
import com.sales.ops.db.entity.OpsPoAutosendRemarkConfigExample;
import com.sales.ops.db.entity.OpsPoDestinationConfig;
import com.sales.ops.db.entity.OpsPoModelSendConfig;
import com.sales.ops.db.entity.OpsPoModelSendConfigExample;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsPurchaseinvoiceExample;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsPurchaseorderExample;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.OpsRequestpurchaseExample;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.db.entity.OpsWarehouseLocationno;
import com.sales.ops.db.entity.OpsWarehouseLocationnoExample;
import com.sales.ops.db.entity.ProductBomDetailVt;
import com.sales.ops.db.entity.ProductBomDetailVtExample;
import com.sales.ops.db.entity.Supplier;
import com.sales.ops.db.extdao.PurchaseOrderDao;
import com.sales.ops.enums.OrderTypeEnum;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.enums.PurchaseTypeEnum;
import com.sales.ops.service.wm.WmCommonService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;

@Service
@Transactional
public class PurchaseExportTxtServiceImpl implements PurchaseExportTxtService {

	private final static Logger logger = LoggerFactory.getLogger(PurchaseExportTxtServiceImpl.class);

	@Autowired
	private OpsMaxinvoiceMapper opsMaxinvoiceMapper;

	@Autowired
	private OpsPurchaseorderMapper opsPurchaseorderMapper;

	@Autowired
	private OpsPurchaseinvoiceMapper opsPurchaseinvoiceMapper;

	@Autowired
	private PurchaseOrderDao purchaseOrderDao;

	@Autowired
	private ProductBomDetailVtMapper productBomDetailVtMapper;

	@Autowired
	private SupplierMapper supplierMapper;

	@Autowired
	private OpsWarehouseLocationnoMapper opsWarehouseLocationnoMapper;

	@Autowired
	private PurchaseSendEmail purchaseSendEmail;

	@Autowired
	private RequestPurchasePreService requestPurchasePreService;

	@Autowired
	private OpsRequestpurchaseMapper opsRequestpurchaseMapper;

	@Autowired
	private OpsJpPModelnoMapper opsJpPModelnoMapper;

	@Autowired
	private OpsPoDestinationConfigMapper opsPoDestinationConfigMapper;

	@Autowired
	private WmCommonService wmCommonService;

	@Autowired
	private PoBatchDao poBatchDao;

	@Autowired
	private OpsPoAutosendRemarkConfigMapper opsPoAutosendRemarkConfigMapper;

	@Autowired
	private OpsPoModelSendConfigMapper opsPoModelSendConfigMapper;

	@Autowired
	private DictDataServiceFeignApi dictDataServiceFeignApi;

	@Autowired
	private SendFileService sendPdfService;
    @Autowired
    private SplitSmcCodeService splitSmcCodeService;

	@Value("${pofiles.basepath}")
	private String poBasePath; // 采购发单文件生成目录

	@Autowired
	private IpsPurchaseSendCommonService ipsPurchaseSendCommonService;

	@Autowired
	private PsiPurchaseSendOverSeaService psiPurchaseSendOverSeaService;

	@Override
	public String sendOrder() throws Exception {
		// 重新计算非当天采购单的出荷日
		reCalExportday(3);
		// 预处理
		sendPre(3);
		// 日本文件生成
		String textInfo = textInfoJP(null);
		if (StringUtils.isNotBlank(textInfo)) {
			String pathJP = writeToTxtJP(textInfo);
			OpsPurchaseinvoiceExample exam = new OpsPurchaseinvoiceExample();
			exam.createCriteria().andStatecodeEqualTo("0").andSupplieridEqualTo("JP");
			OpsPurchaseinvoice record = new OpsPurchaseinvoice();
			record.setStatecode("1");
			record.setUpdatetime(new Date());
			record.setSendtime(new Date());
			opsPurchaseinvoiceMapper.updateByExampleSelective(record, exam);
			// 调用日本邮件发送接口，发送TXT邮件给日本
			purchaseSendEmail.sendMailJP(pathJP);

		}

		// 海外文件生成
		List<String> pathOversea = overSeaFile();
		if (pathOversea.size() > 0) {
			purchaseSendEmail.sendMailOverSea(pathOversea);
		}
		// 中国制造订单发送
		// purchaseSendOrderManuService.sendOrderToManu();
		// 改用接口发送
		// purchaseSendOrderManuService.sendOrderToManuInterface();
		// // 广州制造发送
		// purchaseSendOrderGZService.sendOrderGZ();
		return "成功";

	}

	// 预处理
	// 2022-06-06 预处理拆分为 1.制造，2.广州三部分，else 日本+海外 单独进行预处理
	@Override
	public void sendPre(int dealsuppilyid) throws Exception {
		OpsPurchaseorderExample ex = new OpsPurchaseorderExample();
		OpsPurchaseorderExample.Criteria criteria = ex.createCriteria()
				.andStatecodeEqualTo(PurchaseOrderStatusEnum.DAICHULI);
		// 定义制造发单的供应商列表
		List<String> suppilyList = new ArrayList<>();
		suppilyList.add("CN");
		suppilyList.add("CM");
		suppilyList.add("YZ");
		suppilyList.add("CT");
		// suppilyList.add("TZ");
		switch (dealsuppilyid) {
		case 1:
			criteria.andSupplieridIn(suppilyList);
			break;
		case 2:
			criteria.andSupplieridEqualTo("GZ");
			break;
		default:
			suppilyList.add("GZ");
			criteria.andSupplieridNotIn(suppilyList);
		}
		ex.setOrderByClause("orderNo,itemNo");
		List<OpsPurchaseorder> items = opsPurchaseorderMapper.selectByExample(ex);
		if (items == null || items.size() == 0) {
			return;
		}
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
				if (item.getSplititemno() == null) {
					invoice.setPono(item.getOrderno() + "-" + item.getItemno().toString());
				} else {
					invoice.setPono(item.getOrderno() + "-" + item.getItemno().toString() + "-"
							+ item.getSplititemno().toString());
				}
				invoice.setLineitem(1);
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
				// zerotax smccode 9501282/9501284 ??
				// greencode

				// bug10440 马腾 后缀不写入pono
				// 日本故障品-DR CR
				// if (StringUtils.equals(item.getOrdtype(), OrderTypeEnum.DR))
				// {
				// invoice.setPono(invoice.getPono() + "-DR");
				// } else if (StringUtils.equals(item.getOrdtype(),
				// OrderTypeEnum.CR)) {
				// invoice.setPono(invoice.getPono() + "-CR");
				// }

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
				// bug20054 OPS向日本及海外发单时，会用到userno字段，需要确认是否替换为新加的enduser
				List<OpsCustomerProperty> vipList = purchaseOrderDao.getVIPCode(
						StringUtils.isBlank(item.getEndUser()) ? item.getCustomerno() : item.getEndUser(),
						item.getSupplierid());
				if (vipList != null && vipList.size() > 0) {
					invoice.setVipcode(vipList.get(0).getVipcode());
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
				ex.clear();
				ex.createCriteria().andIdEqualTo(item.getId()).andStatecodeEqualTo(PurchaseOrderStatusEnum.DAICHULI);
				OpsPurchaseorder exam = new OpsPurchaseorder();
				exam.setStatecode(PurchaseOrderStatusEnum.YIFASONG);
				exam.setUpdatetime(new Date());
				exam.setHopereceivewarehouse(item.getHopereceivewarehouse());
				opsPurchaseorderMapper.updateByExampleSelective(exam, ex);
			} else {
				// 异常
				ex.clear();
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
		invoice.setSupplierAssignType(item.getSupplierAssignType()); // bug19540 WTSR2025001597-配合PMS分单系统OPS系统发单功能新增
		invoice.setEndUser(item.getEndUser());
		invoice.setPrepareorderno(item.getPrepareorderno()); // bug20023 采购自动发单给psi时,传递准备订单号
		return invoice;
	}

	// 生成订向日本订单导出文件文本
	// 2025-10-09 bug19111 发单日本HScode问题,用字节数占位来替换String.format方法 （StringBytePadUtil.padRightByBytes(str1, 24)）
	@Override
	public String textInfoJP(String versionLike) throws Exception {
		OpsPurchaseinvoiceExample exam = new OpsPurchaseinvoiceExample();
		if (StringUtils.isNotBlank(versionLike)) {
			exam.createCriteria().andStatecodeEqualTo("0").andSupplieridEqualTo("JP").andSendversionLike(versionLike);
		} else {
			exam.createCriteria().andStatecodeEqualTo("0").andSupplieridEqualTo("JP");
		}
		exam.setOrderByClause("poNo");
		List<OpsPurchaseinvoice> list = opsPurchaseinvoiceMapper.selectByExample(exam);
		if (list == null || list.size() == 0) {
			return null;
		}
		StringBuffer textInfo = new StringBuffer();
		for (OpsPurchaseinvoice item : list) {
			// bug10440 马腾 DR后缀放在发单文件时处理
			if (StringUtils.equals(item.getOrdtype(), OrderTypeEnum.DR)) {
				item.setPono(item.getPono() + "-DR");
			} else if (StringUtils.equals(item.getOrdtype(), OrderTypeEnum.CR)) {
				item.setPono(item.getPono() + "-CR");
			}
			// header
			textInfo.append(item.getSmccode()); //7位 1-7
			textInfo.append("H"); // 1位 8
			textInfo.append(String.format("%-20s",
					StringUtils.isBlank(item.getPurchasetype()) ? " " : item.getPurchasetype() + item.getPono()));
			SimpleDateFormat ft = new SimpleDateFormat("yyMMdd");//20位 9-28
			textInfo.append(ft.format(item.getHopeexportdate()));// 6位 29-34
			textInfo.append(ft.format(item.getPurchasedate())); // 6位 35-40
			//bugid:15251 c14717 20250303  1位 41
			if(StringUtils.equals("G", item.getTranstype())){ //空运 且 日通
				textInfo.append("G");
			}else {
				textInfo.append(StringUtils.equals("1", item.getTranstype()) ? "A"
						: (StringUtils.equals("4", item.getTranstype()) ? "C" : "B"));
			}
			textInfo.append("D180C");// 4位 42-45
			textInfo = addSpace(6, textInfo); // 6位 46-51
			textInfo.append(item.getDeliveryflag());// 1位 52
			// bugid:19186 c14717 20251127 合并smccode subCode 3位 53-55
			if(StringUtils.isNotBlank(item.getSubCode())){
				textInfo.append(item.getSubCode());
			}
			textInfo.append("\r\n"); // 换行代表下一个文件
			// item
			textInfo.append(item.getSmccode()); // 7位 1-7
			textInfo.append("D"); // 1位 8
			// 向前补空格
			textInfo.append(String.format("%3s", item.getLineitem())); // 9-11
			if (item.getModelno().length() < 31) { //12-41
				textInfo.append(String.format("%-30s", item.getModelno()));
			} else {
				textInfo.append(item.getModelno().substring(0, 30));
			}
			textInfo.append(String.format("%7s", item.getQuantity())); //42-48
			textInfo = addSpace(13, textInfo); // 49-61
			// 20220602：DR订单使用订单备注，其余订单使用业务备注
			if (item.getPono().endsWith("-DR")) { // 62-85
				if (StringUtils.isBlank(item.getRemark())) {
					textInfo = addSpace(24, textInfo);
				} else {
					textInfo.append(String.format("%-24s", item.getRemark()));
				}
			} else {
				if (StringUtils.isBlank(item.getServerremark())) {
					textInfo = addSpace(24, textInfo);
				} else {
					textInfo.append(String.format("%-24s", item.getServerremark()));
				}
			}

			// 货位号
			if (StringUtils.equals(PurchaseTypeEnum.SALE.getTypeCode(), item.getPurchasetype())) { //86-95
				textInfo.append(String.format("%-10s", item.getDeptno()));
			} else {
				if (StringUtils.isBlank(item.getLocationno())) {
					textInfo = addSpace(10, textInfo);
				} else {
					textInfo.append(String.format("%-10s",
							item.getReceivewarehouseid() + "-" + item.getLocationno().substring(0, 6).toUpperCase()));
				}
			}

			if (StringUtils.isBlank(item.getSpecmark())) { //96位
				textInfo = addSpace(1, textInfo);
			} else
				textInfo.append(item.getSpecmark());
			textInfo = addSpace(1, textInfo);
			if (StringUtils.isBlank(item.getHscode())) {
				textInfo = addSpace(15, textInfo);
			} else {
				textInfo.append(String.format("%-15s", item.getHscode()));
			}
			textInfo = addSpace(1, textInfo);
			if (StringUtils.isBlank(item.getVipcode())) {
//				if (StringUtils.isBlank(item.getUserno())) {
//					textInfo.append(String.format("%-10s", item.getCustomerno()));
//				} else {
//					textInfo.append(String.format("%-10s", item.getUserno()));
//				}
				// bug 20054 日本发单时，转换为最终用户
				if (StringUtils.isBlank(item.getEndUser())) {
					textInfo.append(String.format("%-10s", item.getCustomerno()));
				} else {
					textInfo.append(String.format("%-10s", item.getEndUser()));
				}
			} else {
				textInfo.append(String.format("%-10s", item.getVipcode()));
			}
			textInfo = addSpace(24, textInfo);
			if (StringUtils.isBlank(item.getGreencode())) {
				textInfo = addSpace(1, textInfo);
			} else {
				textInfo.append(item.getGreencode());
			}
			if (StringUtils.isBlank(item.getInqno())) {
				textInfo = addSpace(10, textInfo);
			} else {
				textInfo.append(String.format("%-10s", item.getInqno()));
			}
			if (StringUtils.isBlank(item.getShikomianswerno())) {
				textInfo = addSpace(15, textInfo);
			} else {
				textInfo.append(String.format("%-15s", item.getShikomianswerno()));
			}
			// 新加坡供应商写成S
			if (StringUtils.equals("SG", item.getSupplierid())) {
				textInfo.append("S");
			} else {
				textInfo = addSpace(1, textInfo);
			}
			// 货位号
			// 20221123马腾 bug修复 此处占位应为5位
			if (StringUtils.equals(PurchaseTypeEnum.SALE.getTypeCode(), item.getPurchasetype())) {
				if (item.getSplititemno() == null) {
					textInfo.append(String.format("%-5s", "AAA"));
				} else {
					textInfo.append(String.format("%-5s", "999"));
				}
			} else {
				if (StringUtils.isBlank(item.getLocationno())) {
					textInfo = addSpace(5, textInfo);
				} else {
					textInfo.append(String.format("%-5s", item.getLocationno().substring(0, 2).toUpperCase()));
				}
			}
			textInfo = addSpace(13, textInfo);
			if (item.getModelno().length() > 30) {
				textInfo.append(String.format("%-20s", item.getModelno().substring(30)));
			} else {
				textInfo = addSpace(20, textInfo);
			}
			textInfo = addSpace(59, textInfo);
			// 营业情报号
			if (StringUtils.isBlank(item.getSalesinfono())) {
				textInfo = addSpace(26, textInfo);
			} else {
				textInfo.append("CN0");
				textInfo.append(item.getSalesinfono().substring(0, 7));
				textInfo = addSpace(10, textInfo);
				textInfo.append(item.getSalesinfono().substring(7));
				textInfo = addSpace(3, textInfo);
			}
			textInfo = addSpace(37, textInfo);
			textInfo.append("\r\n");

			// end
			textInfo.append(item.getSmccode()); // 7位 1-7
			textInfo.append("T"); // 8位
			textInfo = addSpace(72, textInfo);
			textInfo.append("\r\n");
		}
		return textInfo.toString();
	}

	// 添加空格
	public StringBuffer addSpace(int spaceNum, StringBuffer str) {
		for (int i = 0; i < spaceNum; i++) {
			str.append(" ");
		}
		return str;
	}

	// 生成TXT文件
	@Override
	public String writeToTxtJP(String text) throws Exception {
		// 获取当前导出文件名invNo
		String invNo = "";
		String fileEmail = "";
		OpsMaxinvoiceExample example = new OpsMaxinvoiceExample();
		example.createCriteria().andDescriptionEqualTo("日本").andPidEqualTo("D");
		List<OpsMaxinvoice> inv = opsMaxinvoiceMapper.selectByExample(example);
		if (inv != null && inv.size() > 0) {
			invNo = inv.get(0).getRemark();
		} else {
			throw new Exception("获取最大invNo失败！");
		}
		if (StringUtils.isBlank(invNo)) {
			invNo = "000";
		}
		// 更新invNo最大号
		OpsMaxinvoice temp = new OpsMaxinvoice();
		temp.setRemark(String.format("%03d", Integer.parseInt(invNo) + 1));
		example.clear();
		example.createCriteria().andPidEqualTo("D");
		opsMaxinvoiceMapper.updateByExampleSelective(temp, example);

		// 生成的文件路径
		// String path = "/home"
		// String path = "D:\\" + new DateTime().getYear() + "\\EXP95012." +
		// invNo + ".txt";
		String template = "/ops/Sys_Report/";
		// bug 18642 OPS向日本及海外发单时，将备份文件夹的位置，配置到nacos中，方便进行环境的切换
		if (StringUtils.isNotBlank(poBasePath)) {
			template = poBasePath;
		}
		String path = template + new DateTime().getYear() + "/EXP95012." + invNo;
		File file = new File(path);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		file.createNewFile();
		fileEmail = path;
		// write 解决中文乱码问题
		// FileWriter fw = new FileWriter(file, true);
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(text);
		bw.flush();
		bw.close();
		fw.close();
		return fileEmail;
	}

	// 海外文件，分供应商生成对应的文件
	public List<String> overSeaFile() throws Exception {
		// 供应商信息，为获取fullName
		List<Supplier> supplierList = supplierMapper.selectByExample(null);
		Map<String, Supplier> supplierMap = new HashMap<String, Supplier>();
		if (supplierList == null || supplierList.size() == 0) {
			return null;
		} else {
			supplierList.forEach(j -> {
				if (StringUtils.isBlank(j.getFullname())) {
					j.setFullname("");
				}
				if (j.getPaymentday() == null) {
					j.setPaymentday(60);
				}
				supplierMap.put(j.getId().trim(), j);
			});
		}
		// bug10347 马腾 获取仓库信息
		ResultVo<List<OpsWarehouse>> warelist = wmCommonService.getWarehouseCanPurchase();
		Map<String, OpsWarehouse> opswarehousemap = new HashMap<String, OpsWarehouse>();
		if (warelist.isSuccess()) {
			warelist.getData().forEach(i -> {
				opswarehousemap.put(i.getWarehouseCode(), i);
			});
		}

		// 文件生成
		List<String> invoiceSupplierList = purchaseOrderDao.getInvoiceSupplierOverSea();
		List<String> filePathOverSea = new ArrayList<String>();
		if (invoiceSupplierList != null && invoiceSupplierList.size() > 0) {
			for (String item : invoiceSupplierList) {
				filePathOverSea.addAll(writeToExcel(item.trim(), supplierMap.get(item.trim()), opswarehousemap, null));
			}
		}
		return filePathOverSea;
	}

	// 海外订货生成excel文件
	@Override
	public List<String> writeToExcel(String supplierNo, Supplier su, Map<String, OpsWarehouse> opswarehousemap,
			String versionLike) throws Exception {
		// 获取数据
		OpsPurchaseinvoiceExample exam = new OpsPurchaseinvoiceExample();
		if (StringUtils.isNotBlank(versionLike)) {
			exam.createCriteria().andStatecodeEqualTo("0").andSupplieridEqualTo(supplierNo)
					.andSendversionLike(versionLike);
		} else {
			exam.createCriteria().andStatecodeEqualTo("0").andSupplieridEqualTo(supplierNo);
		}

		List<OpsPurchaseinvoice> list = opsPurchaseinvoiceMapper.selectByExample(exam);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		// bug11473马腾 获取贩卖限制品
		List<String> modelNoList = list.stream().distinct().map(OpsPurchaseinvoice::getModelno)
				.collect(Collectors.toList());
		List<String> restrict = new ArrayList<String>();
		for (int i = 0; i < modelNoList.size(); i++) {
			if (i % 2000 == 0) {
				List<String> temp = new ArrayList<String>();
				if (i + 2000 < modelNoList.size()) {
					temp = modelNoList.subList(i, i + 2000);
				} else {
					temp = modelNoList.subList(i, modelNoList.size());
				}
				List<String> l = poBatchDao.restrictModel(temp);
				if (l != null && l.size() > 0) {
					restrict.addAll(l);
				}
			}
		}
		Map<String, Boolean> resMap = new HashMap<String, Boolean>();
		Map<String, OpsCustomer> customerMap = new HashMap<String, OpsCustomer>();
		if (!CollectionUtils.isEmpty(restrict)) {
			restrict.forEach(a -> {
				resMap.put(a, true);
			});
		}
		// 若为贩卖限制品，则 查询客户英文名
		// bug13940 部门供应商要求备注增加客户英文名信息，因此不论是不是贩卖限制品查询用户信息
		// bug 20054,用end_user替换userno
		List<String> customer = list.stream().distinct()
				.filter(s -> StringUtils.isBlank(s.getEndUser()) && StringUtils.isNotBlank(s.getCustomerno()))
				.map(OpsPurchaseinvoice::getCustomerno).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(customer)) {
			customer = list.stream().distinct().filter(s -> StringUtils.isNotBlank(s.getEndUser()))
					.map(OpsPurchaseinvoice::getEndUser).collect(Collectors.toList());
		} else {
			customer.addAll(list.stream().distinct().filter(s -> StringUtils.isNotBlank(s.getEndUser()))
					.map(OpsPurchaseinvoice::getEndUser).collect(Collectors.toList()));
		}
		List<OpsCustomer> customerList = new ArrayList<OpsCustomer>();
		for (int i = 0; i < customer.size(); i++) {
			if (i % 2000 == 0) {
				List<String> temp = new ArrayList<String>();
				if (i + 2000 < customer.size()) {
					temp = customer.subList(i, i + 2000);
				} else {
					temp = customer.subList(i, customer.size());
				}
				List<OpsCustomer> l = poBatchDao.getCustomerInfo(temp);
				if (l != null && l.size() > 0) {
					customerList.addAll(l);
				}
			}
		}
		customerList.forEach(a -> {
			customerMap.put(a.getCustomerNo(), a);
		});

		// bug11473自动发单 型号物料号
		// bug12255增加供应商分类字段，海外只获取OVERSEA的配置信息
		OpsPoAutosendRemarkConfigExample ex = new OpsPoAutosendRemarkConfigExample();
		ex.createCriteria().andIsDeletedEqualTo(false).andSupplierClassEqualTo("OVERSEA");
		List<OpsPoAutosendRemarkConfig> configList = opsPoAutosendRemarkConfigMapper.selectByExample(ex);

		List<String> fileEmail = new ArrayList<String>();
		// 邮件发送路径
		// bug10347 马腾 根据期望供应商到货仓字段划分生成文件
		Map<String, List<OpsPurchaseinvoice>> warehouseMap = list.stream()
				.collect(Collectors.groupingBy(OpsPurchaseinvoice::getHopereceivewarehouse));
		// 2022-03-28,供应商模板都统一了，只是按照发货仓库的代码来进行区分
		// 2021-4-19 bug10347 修改为按照期望到货仓划分
		for (Map.Entry<String, List<OpsPurchaseinvoice>> entry : warehouseMap.entrySet()) {
			String key = entry.getKey();
			List<OpsPurchaseinvoice> sList = entry.getValue();
			if (!opswarehousemap.containsKey(key)) { // 校验仓库清单中是否存在，不存在时默认为KBJ
				key = "KBJ";
			}
//			if (opswarehousemap.containsKey(key)) {
//				fileEmail.add(overseaExport(key, supplierNo, sList, su.getFullname(), su.getPaymentday(),
//						opswarehousemap.get(key), resMap, customerMap, configList));
//				// bug13409发单增加PDF文件
//				ResultVo<List<DataCodeVO>> result = dictDataServiceFeignApi.getDataCodes("3007");
//				if (result.isSuccess() && !CollectionUtils.isEmpty(result.getData())) {
//					List<String> t = result.getData().stream().map(DataCodeVO::getCode).collect(Collectors.toList());
//					if (!CollectionUtils.isEmpty(t) && t.contains(supplierNo)) {
//						fileEmail.add(sendPdfService.exportPdfFile(key, supplierNo, sList, su.getFullname(),
//								su.getPaymentday(), opswarehousemap.get(key), resMap, customerMap, configList));
//					}
//				}
//			} else {
//				fileEmail.add(overseaExport("KBJ", supplierNo, sList, su.getFullname(), su.getPaymentday(),
//						opswarehousemap.get("KBJ"), resMap, customerMap, configList));
//				// bug13409发单增加PDF文件
//				ResultVo<List<DataCodeVO>> result = dictDataServiceFeignApi.getDataCodes("3007");
//				if (result.isSuccess() && !CollectionUtils.isEmpty(result.getData())) {
//					List<String> t = result.getData().stream().map(DataCodeVO::getCode).collect(Collectors.toList());
//					if (!CollectionUtils.isEmpty(t) && t.contains(supplierNo)) {
//						fileEmail.add(sendPdfService.exportPdfFile("KBJ", supplierNo, sList, su.getFullname(),
//								su.getPaymentday(), opswarehousemap.get("KBJ"), resMap, customerMap, configList));
//					}
//				}
//			}
			// ----------------以下为PSI切换后的代码修改-------------------------------------
			// 1.增加字典开关的配置, 字典值：0= 只发老系统，1 = 只发送新系统，2=新老同时发
			ResultVo<PsiDataType> dictResult = ipsPurchaseSendCommonService.getSwitchflag(IpsPurchaseCommonEnum.OVERSEA_SWITCH.getCode());
			PsiDataType dictDataValues = dictResult.getData();
			String swithchConfigValue = dictDataValues.getExtNote1();
			String opsFilePath = "";
			String pdfPath = "";
			// 2. 如果为只发旧系统 或者 新旧同时发，调用老的生成方法
			if (IpsPurchaseSendSwitchEnum.OLD.getCode().equals(swithchConfigValue) || IpsPurchaseSendSwitchEnum.ALL.getCode().equals(swithchConfigValue)) {
				// 2.1旧的文件生成
				opsFilePath = overseaExport(key, supplierNo, sList, su.getFullname(), su.getPaymentday(),
						opswarehousemap.get(key), resMap, customerMap, configList);
				fileEmail.add(opsFilePath);
				// bug13409发单增加PDF文件
				ResultVo<List<DataCodeVO>> result = dictDataServiceFeignApi.getDataCodes("3007");
				if (result.isSuccess() && !CollectionUtils.isEmpty(result.getData())) {
					List<String> t = result.getData().stream().map(DataCodeVO::getCode).collect(Collectors.toList());
					if (!CollectionUtils.isEmpty(t) && t.contains(supplierNo)) {
						fileEmail.add(sendPdfService.exportPdfFile(key, supplierNo, sList, su.getFullname(),
								su.getPaymentday(), opswarehousemap.get(key), resMap, customerMap, configList));
					}
				}
			}
			// 3.1 生成发送PSI的批次号
			String batchNo = "";
			ResultVo<String> batchNoResultVo = ipsPurchaseSendCommonService.generateIpsOrderBatchNo(supplierNo);
			if (batchNoResultVo.isSuccess()) {
				batchNo = batchNoResultVo.getData();
			}
			// 3.2 先更新采购的状态
			ipsPurchaseSendCommonService.updatePurchaseInvoiceStatus(sList, batchNo, opsFilePath);
			// 4. 如果为新系统 或者 新旧同时发，调用新系统的生成方法
			if (IpsPurchaseSendSwitchEnum.NEW.getCode().equals(swithchConfigValue) || IpsPurchaseSendSwitchEnum.ALL.getCode().equals(swithchConfigValue)) {
				// 调用新的PSI发送方法
				// 注：psi推送PDF的文件时，由PSI自己生成，无需OPS传输，由于是一批订单一个PDF文件
				ResultVo<String> psiSendResult = psiPurchaseSendOverSeaService.writeToExcel(key, supplierNo, sList, su.getFullname(), su.getPaymentday(),
						opswarehousemap.get(key), resMap, customerMap, configList,dictDataValues, batchNo, pdfPath);
				// 发单完成
				if (!psiSendResult.isSuccess()) {
					logger.error("海外订单发送生成excel操作失败：{}", psiSendResult.getMessage());
					throw Exceptions.OpsException("海外订单发送PSI发单失败失败" + psiSendResult.getMessage());
				}
			}
		}
		return fileEmail;
	}

	/**
	 * 海外订单发送生成excel操作
	 */
	public String overseaExport(String warehouseid, String supplierNo, List<OpsPurchaseinvoice> list, String fullname,
			Integer payment, OpsWarehouse opswarehouse, Map<String, Boolean> resMap,
			Map<String, OpsCustomer> customerMap, List<OpsPoAutosendRemarkConfig> configList) throws Exception {
		String fileEmail = "";
		// 2022-03-28,供应商模板都统一了，只是按照发货仓库的代码来进行区分
		// 模板路径
		// String template = "D:\\";
		String template = "/ops/Sys_Report/";
		// bug 18642 OPS向日本及海外发单时，将备份文件夹的位置，配置到nacos中，方便进行环境的切换
		if (StringUtils.isNotBlank(poBasePath)) {
			template = poBasePath;
		}
		// 文件名称
		String fileName = null;
		// 模板上明细起始行
		Integer itemline = null;
		// bug13940 模板文件名从字典中获取
		String carrier = null;
		ResultVo<List<DataCodeVO>> result = dictDataServiceFeignApi.getDataCodes("3009");
		if (result.isSuccess() && !CollectionUtils.isEmpty(result.getData())) {
			List<DataCodeVO> other = result.getData().stream().filter(i -> StringUtils.equals("other", i.getCode()))
					.collect(Collectors.toList());
			for (DataCodeVO dic : result.getData()) {
				if (StringUtils.equals(dic.getCode(), supplierNo)) {
					if (StringUtils.isNotBlank(dic.getExtNote1()))
						fileName = dic.getExtNote1();
					if (StringUtils.isNotBlank(dic.getExtNote2()))
						itemline = Integer.parseInt(dic.getExtNote2());
					if (StringUtils.isNotBlank(dic.getExtNote3()))
						carrier = dic.getExtNote3();
					break;
				}
			}
			if (StringUtils.isBlank(fileName)) {
				if (!CollectionUtils.isEmpty(other) && other.size() > 0) {
					if (StringUtils.isNotBlank(other.get(0).getExtNote1()))
						fileName = other.get(0).getExtNote1();
					if (StringUtils.isNotBlank(other.get(0).getExtNote2()))
						itemline = Integer.parseInt(other.get(0).getExtNote2());
					if (StringUtils.isNotBlank(other.get(0).getExtNote3()))
						carrier = other.get(0).getExtNote3();
				}
			}
		}
		// 字典中未获取到则赋予默认值
		if (StringUtils.isBlank(fileName)) {
			fileName = "OrverSea_OrdList";
			// AP变更模板，增加订单项号列显示
			if (StringUtils.equals("AP", supplierNo)) {
				fileName = "OrverSea_OrdList_AP";
			}
		}
		if (itemline == null) {
			itemline = 18;
		}
		if (StringUtils.isBlank(carrier)) {
			carrier = "774046836";
		}
		// 备注加客户英文名的国家
		int enameflag = 0;
		ResultVo<List<DataCodeVO>> ename = dictDataServiceFeignApi.getDataCodes("3010");
		if (ename.isSuccess() && !CollectionUtils.isEmpty(ename.getData())) {
			for (DataCodeVO temp : ename.getData()) {
				if (StringUtils.equals(temp.getCode(), supplierNo)) {
					enameflag = 1;
					break;
				}
			}
		}

		// 需要修改为 服务器生成文件路径
		String filePath = template + new DateTime().getYear() + "/" + supplierNo + "/" + warehouseid;
		File file = createNewFile(template, fileName, filePath, supplierNo, warehouseid);
		String newPath = file.getPath();
		fileEmail = file.getPath();
		FileInputStream fis = new FileInputStream(newPath);
		// xslx
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet("OrderList From China");
		// 判断是否是AP，若不是则隐藏最后一列APNO
		// if (!StringUtils.equals("AP", supplierNo)) {
		// sheet.setColumnHidden(9, true);
		// }
		// 20220905意大利隐藏TNT列
		// if (StringUtils.equals("IT", supplierNo)) {
		// sheet.setColumnHidden(6, true);
		// }
		// 填充数据
		cellView(sheet, 6, 0, "1. Origin:" + fullname);
		// bug10347 马腾 增加根据不同期望供应商到货仓，获取对应的英文地址及收货人信息，填充发单文件
		cellView(sheet, 10, 0,
				"   Name: " + opswarehouse.getEnglishLinkman() + "    " + "Phone:" + opswarehouse.getLinkPhone());
		cellView(sheet, 11, 0, "   Address: " + opswarehouse.getEnglishAddress());
		cellView(sheet, 12, 0, "5. Payment condition: Within " + payment + " days affer receiverd");
		cellView(sheet, itemline - 1, 8, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

		for (OpsPurchaseinvoice item : list) {
			itemline = itemline + 1;
			int j = 0;
			// bug13662 AP发单子项将订单号及项号分别传输
			if (StringUtils.equals("AP", supplierNo)) {
				cellView1(sheet, itemline, j++, item.getPono(), wb);
				cellView1(sheet, itemline, j++, item.getLineitem().toString(), wb);
			} else {
				cellView1(sheet, itemline, j++, item.getPono(), wb);
			}
			cellView1(sheet, itemline, j++, item.getModelno(), wb);
			cellView1(sheet, itemline, j++, item.getQuantity().toString(), wb);
			cellView1(sheet, itemline, j++, new SimpleDateFormat("yyyy-MM-dd").format(item.getHopeexportdate()), wb);
			// cellView1(sheet, i, 4, StringUtils.equals("0",
			// item.getTranstype()) ? "Sea"
			// : (StringUtils.equals("1", item.getTranstype()) ? "Air" : "EMS"),
			// wb);
			cellView1(sheet, itemline, j++, "Air", wb);

			if (StringUtils.equals("US", supplierNo)) {
				// 供应商为美国remark字段写入最近一次登入的fob价，OverSea_Model_price:Price_overSea
				if (item.getImportfobpriceoriginal() != null) {
					cellView1(sheet, itemline, j++, item.getImportfobpriceoriginal().toString(), wb);
				} else {
					j++;
				}
			} else {
				j++;
			}
			// 2024-1-18业务通知，TNT停用，后续使用fedex
			cellView1(sheet, itemline, j++, carrier, wb);
			cellView1(sheet, itemline, j++, item.getShikomianswerno(), wb);
			// bug11473自动发单贩卖限制品备注增加客户英文名
			String remark = StringUtils.isNotBlank(item.getServerremark()) ? item.getServerremark() : "";
			if (resMap.containsKey(item.getModelno()) || enameflag == 1) {
				if (StringUtils.isBlank(item.getEndUser()) && StringUtils.isNotBlank(item.getCustomerno())
						&& customerMap.containsKey(item.getCustomerno())) {
					if (StringUtils.isNotBlank(customerMap.get(item.getCustomerno()).getEname()))
						remark = remark + " " + customerMap.get(item.getCustomerno()).getEname();
					else if (StringUtils.isNotBlank(customerMap.get(item.getCustomerno()).getAliasEname())) {
						remark = remark + " " + customerMap.get(item.getCustomerno()).getAliasEname();
					} else {
						if (enameflag == 1) {
							remark = remark + " " + customerMap.get(item.getCustomerno()).getName();
						}
					}
				} else if (StringUtils.isNotBlank(item.getEndUser()) && customerMap.containsKey(item.getEndUser())) {
					if (StringUtils.isNotBlank(customerMap.get(item.getEndUser()).getEname()))
						remark = remark + " " + customerMap.get(item.getEndUser()).getEname();
					else if (StringUtils.isNotBlank(customerMap.get(item.getEndUser()).getAliasEname())) {
						remark = remark + " " + customerMap.get(item.getEndUser()).getAliasEname();
					} else {
						if (enameflag == 1) {
							remark = remark + " " + customerMap.get(item.getEndUser()).getName();
						}
					}
				}
			}
			// bug11473物料号
			List<OpsPoAutosendRemarkConfig> temp = configList.stream().filter(s -> StringUtils.equals(item.getModelno(),
					s.getModelno())
					&& (StringUtils.equals(item.getDeptno(), s.getDeptno()) || StringUtils.isBlank(s.getDeptno())))
					.collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(temp)) {
				for (OpsPoAutosendRemarkConfig o : temp) {
					remark = remark + " " + o.getRemark();
				}
			}
			cellView1(sheet, itemline, j++, remark, wb);

			if (StringUtils.equals("AP", supplierNo)) {
				cellView1(sheet, itemline, j++, item.getSupplierpartno(), wb);
			}
		}
		// 填充完毕,写文件
		FileOutputStream ops = new FileOutputStream(newPath);
		wb.write(ops);
		wb.close();
		ops.close();
		fis.close();
		// bug11473 马腾 自动发单 保存文件名
//		OpsPurchaseinvoice record = new OpsPurchaseinvoice();
//		record.setStatecode("1");
//		record.setUpdatetime(new Date());
//		record.setSendtime(new Date());
//		record.setFilepath(fileEmail);
//		List<Long> idList = list.stream().map(a -> a.getId()).collect(Collectors.toList());
//		OpsPurchaseinvoiceExample exam = new OpsPurchaseinvoiceExample();
//		if (idList.size() > 2000) {
//			for (int j = 0; j < idList.size(); j++) {
//				if (j % 2000 == 0) {
//					List<Long> temp = null;
//					if (j + 2000 < idList.size()) {
//						temp = idList.subList(j, j + 2000);
//					} else {
//						temp = idList.subList(j, idList.size());
//					}
//					exam.clear();
//					exam.createCriteria().andIdIn(temp);
//					opsPurchaseinvoiceMapper.updateByExampleSelective(record, exam);
//				}
//			}
//		} else {
//			exam.createCriteria().andIdIn(idList);
//			opsPurchaseinvoiceMapper.updateByExampleSelective(record, exam);
//		}

		return fileEmail;
	}

	public static File createNewFile(String template, String fileName, String filePath, String supplier,
			String warehouse) throws Exception {
		// 读取模板，并赋值到新文件
		// 文件模板路径
		String path = (template + fileName + ".xlsx");
		File file = new File(path);

		// 新的文件名
		// bug 9575 海外发单文件名称精确到时分秒毫秒 B91717
		String newFileName = URLEncoder.encode(supplier, "utf-8") + "-" + warehouse + "-"
				+ (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new Date()) + ".xlsx";
		// String newFileName = URLEncoder.encode(fileName, "utf-8");
		// 判断路径是否存在
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 写入到新的excel
		File newFile = new File(filePath, newFileName);

		newFile.createNewFile();
		// 复制模板到新文件
		fileChannelCopy(file, newFile);

		return newFile;
	}

	/**
	 * 完成赋值处理
	 *
	 * @param s
	 *            源文件
	 * @param t
	 *            新文件
	 */
	public static void fileChannelCopy(File s, File t) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(s), 1024);
				out = new BufferedOutputStream(new FileOutputStream(t), 1024);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 为 cell 赋值
	 *
	 * @param sheet
	 *            页面
	 * @param rn
	 *            行号
	 * @param cn
	 *            列号
	 * @param val
	 *            值
	 */
	private void cellView(XSSFSheet sheet, int rn, int cn, String val) {

		if (StringUtils.isBlank(val))
			return;

		XSSFRow row = sheet.getRow(rn);
		if (row == null) {
			row = sheet.createRow(rn); // 该行无数据，创建行对象
		}
		XSSFCell cell = row.getCell(cn);
		if (cell == null) {
			cell = row.createCell(cn);
		}
		cell.setCellValue(val);

	}

	private void cellView1(XSSFSheet sheet, int rn, int cn, String val, XSSFWorkbook wb) {

		if (StringUtils.isBlank(val))
			return;

		XSSFRow row = sheet.getRow(rn);
		if (row == null) {
			row = sheet.createRow(rn); // 该行无数据，创建行对象
		}
		XSSFCell cell = row.getCell(cn);
		if (cell == null) {
			cell = row.createCell(cn);
		}
		cell.setCellValue(val);

		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);
		// 设置字体及大小
		// XSSFFont font2 = wb.createFont();
		// font2.setFontName("宋体");
		// font2.setFontHeightInPoints((short) 9); // 字体大小
		// cellStyle.setFont(font2);
		//
		// 设置边框
		cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
		cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
	}

	@Override
	public void reCalExportday(int dealsuppilyid) throws Exception {
		org.joda.time.LocalDate today = new org.joda.time.LocalDate(new Date());
		OpsPurchaseorderExample ex = new OpsPurchaseorderExample();
		OpsPurchaseorderExample.Criteria criterias = ex.createCriteria()
				.andStatecodeEqualTo(PurchaseOrderStatusEnum.DAICHULI).andPurchasedateLessThan(today.toDate());
		// .andHopeexportdateLessThanOrEqualTo(sdf.parse(d))
		// 定义制造发单的供应商列表
		List<String> suppilyList = new ArrayList<>();
		suppilyList.add("CN");
		suppilyList.add("CM");
		suppilyList.add("YZ");
		suppilyList.add("CT");
		// suppilyList.add("TZ");
		switch (dealsuppilyid) {
		case 1:
			criterias.andSupplieridIn(suppilyList);
			break;
		case 2:
			criterias.andSupplieridEqualTo("GZ");
			break;
		default:
			suppilyList.add("GZ");
			criterias.andSupplieridNotIn(suppilyList);
		}
		ex.setOrderByClause("orderNo,itemNo");
		List<OpsPurchaseorder> items = opsPurchaseorderMapper.selectByExample(ex);
		if (items == null || items.size() == 0) {
			return;
		}
		Date hope = null;
		for (OpsPurchaseorder i : items) {
			// bug13662 请购表新增采购单号字段
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
