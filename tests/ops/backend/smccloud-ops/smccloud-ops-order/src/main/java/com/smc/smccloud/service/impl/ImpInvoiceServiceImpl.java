package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.purchase.PurchaseReplyInfo;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.WmPurchaseFeignApi;
import com.smc.smccloud.common.SMCCodeUtil;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.mapper.impInvoice.PoInvoiceDetailMapper;
import com.smc.smccloud.mapper.impInvoice.PoInvoiceMasterMapper;
import com.smc.smccloud.model.BuInterface.BuInvoice;
import com.smc.smccloud.model.BuInterface.BuInvoiceDetail;
import com.smc.smccloud.model.BuInterface.BuInvoiceMaster;
import com.smc.smccloud.model.BuInterface.BuInvoiceResponse;
import com.smc.smccloud.model.Constants;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.MergeInvoiceDto;
import com.smc.smccloud.model.Purchase.OpsPurchaseInvoiceDO;
import com.smc.smccloud.model.cnfactory.OrderReplyResponse;
import com.smc.smccloud.model.enums.ImpInvoiceMasterStatusEnum;
import com.smc.smccloud.model.enums.InvoiceTypeEnum;
import com.smc.smccloud.model.invoice.*;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.orderstate.OrderSateDateType;
import com.smc.smccloud.model.orderstate.OrderStateEnum;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.model.product.ProductPhysicsVO;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Author: B90034 Date: 2021-12-03 08:54 Description:
 */
@Slf4j
@Service
public class ImpInvoiceServiceImpl implements ImpInvoiceService {

	@Resource
	private ImpInvoiceMasterMapper impInvoiceMasterMapper;
	@Resource
	private ImpInvoiceDetailMapper impInvoiceDetailMapper;
	@Resource
	private ImpInvoiceDetailPackMapper impInvoiceDetailPackMapper;
	@Resource
	private ImpInvoiceErrorMapper impInvoiceErrorMapper;
	@Resource
	private SalesInvoiceMapper salesInvoiceMapper;
	@Resource
	private ImpdataMapper impdataMapper;
	@Resource
	private PoInvoiceMasterMapper poInvoiceMasterMapper;
	@Resource
	private PoInvoiceDetailMapper poInvoiceDetailMapper;
	@Resource
	private OpsPurchaseInvoiceMapper opsPurchaseInvoiceMapper;
	@Resource
	private CommonMapper commonMapper;
	@Resource
	private BuService buService;
	@Resource
	private OrderStateService orderStateService;
	@Resource
	private OrderStateMapper orderStateMapper;
	@Resource
	private CommonServiceFeignApi commonServiceFeignApi;
	@Resource
	private PoInvoiceService poInvoiceService;
	@Resource
	private ImpDataBJMapper impDataBJMapper;
	@Resource
	private WmPurchaseFeignApi wmPurchaseFeignApi;
	@Resource
	private HttpServletResponse response;
	@Resource
	private RedissonUtil redissonUtil;
	@Resource
	private RedisManager redisManager;
	@Resource
	private DictDataServiceFeignApi dictDataServiceFeignApi;
	@Resource
	private OPSVImpInvoiceStatusFrmCMSMapper opsvImpInvoiceStatusFrmCMSMapper;
	@Resource
	private GZSalesinvoiceMapper gzSalesinvoiceMapper;
	@Resource
	private ImpInvoiceService impInvoiceService;
	@Resource
	private SendMessage sendMessage;
	@Resource
	private SalesImportService salesImportService;

	@Resource
	private ProductServiceFeignApi productServiceFeignApi;
	@Resource
	private DictCommonService dictCommonService;

	@Resource
	private PurchaseConvertService purchaseConvertService;

    @Resource
    private MergeSmcCodeDao mergeSmcCodeDao;


	private final static String NOT_COPY_DETAIL_TO_PACK = "JP;CN;CT;CM;";

	@Override
	public ResultVo<String> importGWInvoice(String plantMark, String invNo, Date startTime, Date endTime) {
		Date fromTime = new Date();

		ResultVo<String> impResult = this.importInvoiceDetailFromGW(plantMark, invNo, startTime, endTime);
		if (StringUtils.isBlank(invNo) && impResult.isSuccess()) {
			Date startUpdTime = new Date();
			ResultVo<String> resultVo = this.importInvoiceDetailFromGWByStatus(startUpdTime);
			impResult.setData(impResult.getData() + resultVo.getData());
		}

		log.info("导入关务发票 开始时间-{}, 总耗时: {}(s)", DateUtil.dateToDateTimeString(fromTime),
				DateUtil.getDiffSecond(fromTime, new Date()));
		return impResult;
	}

	@Override
	public ResultVo<String> importInvoiceDetailFromGW(String plantMark, String invNo, Date startTime, Date endTime) {
		int pageNum = 1;
		int pageSize = 10;
		String startTimeStr = startTime == null ? "" : DateUtil.dateToDateTimeString(startTime);
		String endTimeStr = endTime == null ? "" : DateUtil.dateToDateTimeString(endTime);
		Map<String, String> param = new HashMap<>();
		BuInvoiceResponse buInvoiceResponse;
		StringBuilder sbMsg = new StringBuilder();
		String keyPrefix = "ops:rediss:buInvoice:";
		String key;
		ResultVo<String> itemResult;

		while (true) {
			param.put("plantMark", Optional.ofNullable(plantMark).orElse("AM"));
			param.put("invNo", Optional.ofNullable(invNo).orElse(""));
			param.put("startTime", startTimeStr);
			param.put("endTime", endTimeStr);
			param.put("pageNum", String.valueOf(pageNum));
			param.put("pageSize", String.valueOf(pageSize));
			// log.info(param.toString());
			buInvoiceResponse = buService.queryImportInvoiceInfo(param);
			if (buInvoiceResponse.getCode() != 0) {
				return ResultVo.failure("调用关务接口失败-" + buInvoiceResponse.getMessage());
			}

			if (buInvoiceResponse.getData() != null
					&& CollectionUtils.isNotEmpty(buInvoiceResponse.getData().getContent())) {
				if (pageNum == 1) {
					log.info("buInvoice import totalSize = {}, totalPages = {}, params = {}",
							buInvoiceResponse.getData().getTotalSize(), buInvoiceResponse.getData().getTotalPages(),
							param);
				}
				log.info("buInvoice import pageNum {}", buInvoiceResponse.getData().getPageNum());
				for (BuInvoice buInvoice : buInvoiceResponse.getData().getContent()) {
					// log.info("入库发票" + buInvoice.getInvNo());
					long start = System.currentTimeMillis();
					key = keyPrefix + buInvoice.getInvNo();
					try {
						if (redissonUtil.tryLock(key, 1, 240)) {
							if (buInvoice.getInvoice() != null) {
								log.info("导入原始发票: {}, invNo={}, statusCode={}",
										buInvoice.getInvoice().getInvoiceOriginal(), buInvoice.getInvoice().getInvNo(),
										buInvoice.getInvoice().getStatusCode());
							}
							if (buInvoice.getInvoiceDetails() == null || buInvoice.getInvoiceDetails().length == 0) {
								log.info("{}发票明细为空,不导入", buInvoice.getInvoice().getInvNo());
								sbMsg.append("发票明细为空").append(buInvoice.getInvNo()).append("\r\n");
								continue;
							}
							log.info("导入关务发票 {} {}", buInvoice.getInvNo(), buInvoice.getInvoice());
							itemResult = this.saveBuInvoice(buInvoice);
							sbMsg.append(itemResult.getMessage());
						}
					} catch (Exception e) {
						log.error("导入关务发票异常: invNo-{}, errMsg: {}", buInvoice.getInvNo(), e.getMessage(), e);
					} finally {
						redissonUtil.unlock(key);
					}
					log.info("导入关务发票 {} 耗时: {}(s)", buInvoice.getInvNo(), (System.currentTimeMillis() - start) / 1000);
				}
				if (buInvoiceResponse.getData().getTotalPages().equals(buInvoiceResponse.getData().getPageNum())) {
					break;
				}
				pageNum++;
			} else {
				log.info("buInvoiceResponse.getData() is null or buInvoiceResponse.getData().getContent() isEmpty");
				if (buInvoiceResponse.getData() == null) {
					sbMsg.append("关务发票数据为空");
				}
				if (buInvoiceResponse.getData() != null && buInvoiceResponse.getData().getTotalSize() == 0) {
					sbMsg.append("暂无发票数据可导入");
				}
				break;
			}
		}
		return ResultVo.success(sbMsg.toString());
	}

	/**
	 * 重新导入没有入库的发票
	 */
	public ResultVo<String> importInvoiceDetailFromGWByStatus(Date notUpdTime) {
		StringBuilder sbMsg = new StringBuilder();
		LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.select(ImpInvoiceMasterDO::getCInvoiceNo);
		queryWrapper.in(ImpInvoiceMasterDO::getStatus, 1, 2).in(ImpInvoiceMasterDO::getGwStateCode, 0, 2)
				.lt(ImpInvoiceMasterDO::getUpdateTime, notUpdTime).isNotNull(ImpInvoiceMasterDO::getCInvoiceNo)
				.ne(ImpInvoiceMasterDO::getCInvoiceNo, "");
		List<ImpInvoiceMasterDO> list = impInvoiceMasterMapper.selectList(queryWrapper);

		log.info("准备重新导入未入库发票 {}", list.size());
		ResultVo<String> result;
		for (ImpInvoiceMasterDO masterDO : list) {
			result = importInvoiceDetailFromGW("AM", masterDO.getCInvoiceNo(), null, null);
			sbMsg.append(result.getData());
		}
		return ResultVo.success(sbMsg.toString());
	}

	public ResultVo<String> saveBuInvoice(BuInvoice buInvoice) {
		Date now = new Date();
		boolean isNewInvoice = true;

		ImpInvoiceMasterDO masterDO = new ImpInvoiceMasterDO();

		BuInvoiceMaster buInvoiceMaster = buInvoice.getInvoice();
		masterDO.setInvoiceNo(buInvoiceMaster.getInvoiceOriginal());

		masterDO.setCInvoiceNo(buInvoice.getInvNo());
		masterDO.setShipDate(buInvoiceMaster.getShipDate());
		masterDO.setPreArriveDate(buInvoiceMaster.getExpectReceiptDate());
		masterDO.setInvoiceDate(buInvoiceMaster.getInvDate());
		masterDO.setExchangeRate(buInvoiceMaster.getRate());
		masterDO.setAmountRmb(buInvoiceMaster.getMoney());

		if (masterDO.getShipDate() == null) {
			masterDO.setShipDate(buInvoiceMaster.getInvDate());
		}

		String supplierCode = "";
		// <!--add by WuWeiDong 20230822 bug 11830 来货供应商判断 -->
		if (PublicUtil.isEmpty(buInvoiceMaster.getImportCustomer())) {
			return ResultVo.failure("关务无供应商代码，请联系关务。");
		}
		ResultVo<DataTypeVO> dataTypeVOResultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("2078",
				buInvoiceMaster.getImportCustomer());
		if (!dataTypeVOResultVo.isSuccess()) {
			return ResultVo.failure("取来货供应商错误：" + dataTypeVOResultVo.getMessage() + "ImportCustomer："
					+ buInvoiceMaster.getImportCustomer());
		}
		if (PublicUtil.isNotEmpty(dataTypeVOResultVo.getData()) && dataTypeVOResultVo.getData().getStatus() == 1) {
			supplierCode = Optional.ofNullable(dataTypeVOResultVo.getData().getExtNote1()).orElse("");
		}
		if (StringUtils.isBlank(supplierCode)) {
			return ResultVo.failure(
					StringUtils.join("无判断供应商,请追加相关信息到[2078]字典。ImportCustomer：", buInvoiceMaster.getImportCustomer()));
		}
		// ** 第三方的，转用以下判断供应商
		if ("X".equalsIgnoreCase(supplierCode) && StringUtils.isNotBlank(buInvoiceMaster.getShipment())) {

			// buInvoiceMaster.setShipment(buInvoiceMaster.getShipment().replaceAll("有限公司",
			// ""));
			// supplierCode = commonMapper.getSupplierCodeByName("%" +
			// buInvoiceMaster.getShipment() + "%");
			// if ("SMC(北京)制造".equals(buInvoiceMaster.getShipment())) {
			// supplierCode = "CM";
			// } else if ("SMC(中国)".equals(buInvoiceMaster.getShipment())) {
			// supplierCode = "CN";
			// } else if ("SMC(天津)".equals(buInvoiceMaster.getShipment())) {
			// supplierCode = "CT";
			// } else {
			// supplierCode = "";
			// }
			String shipment = Optional.ofNullable(buInvoiceMaster.getShipment()).orElse("").toUpperCase();
			if (shipment.contains("SMC(北京)制造")) {
				supplierCode = "CM";
			} else if (shipment.contains("SMC(中国)")) {
				supplierCode = "CN";
			} else if (shipment.contains("SMC(天津)")) {
				supplierCode = "CT";
			} else {
				shipment = shipment.substring(0, shipment.indexOf("有限公司"));
				supplierCode = commonMapper.getSupplierCodeByName("%" + shipment + "%");
			}

		}
		// 新加坡 非S开头的是日本交易
		if ("SG".equalsIgnoreCase(supplierCode) && (masterDO.getInvoiceNo().startsWith("TCS")
				|| masterDO.getInvoiceNo().startsWith("TCN") || masterDO.getInvoiceNo().startsWith("TGZ"))) {
			supplierCode = "JP";
		}
		if ("VN".equalsIgnoreCase(supplierCode)) {
			supplierCode = "JP";
		}
		// 马来西亚直接交易的是MY*发票，和日本的是V*发票
		if ("MY".equalsIgnoreCase(supplierCode) && masterDO.getInvoiceNo().startsWith("V")) {
			supplierCode = "JP";
		}
		// 如果发票号以"AP"开头且Shipment="美国"
		if (buInvoiceMaster.getInvNo().startsWith("AP") && "美国".equals(buInvoiceMaster.getShipment())) {
			supplierCode = "AP";
		}

		masterDO.setSupplierCode(supplierCode);
		// 设置发票类型
		masterDO.setInvoiceType(this.getGWInvoiceType(masterDO.getSupplierCode()));
		// 非日本的用关务发票号
		if (!"JP".equalsIgnoreCase(masterDO.getSupplierCode())) {
			// 去掉后面4位
			masterDO.setInvoiceNo(buInvoice.getInvNo().substring(0, buInvoice.getInvNo().length() - 4));
		}
		// 临时加 解决三方的关务发票号没有加年月
		if (masterDO.getInvoiceType() != null && masterDO.getInvoiceNo().length() <= 5
				&& masterDO.getInvoiceType().compareTo(InvoiceTypeEnum.TRIPARTITE.getCode()) == 0
				&& masterDO.getShipDate().compareTo(DateUtil.stringToDate("2022-10-01")) < 0) {

			masterDO.setInvoiceNo(buInvoice.getInvNo());
		}

		// List<String> originalInvoiceNos =
		// Arrays.stream(buInvoice.getInvoiceDetails()).map(i ->
		// i.getInvoiceOriginal()).distinct().collect(Collectors.toList());
		// //如果是第3国的发票，多个发票合并在一起的，用合并的发票号
		// if (originalInvoiceNos.size() > 1) {
		// masterDO.setInvoiceNo(buInvoice.getInvNo());
		// }

		ImpInvoiceMasterDO exitMasterDO = getExistImpInvoiceMasterByGWInvoiceNo(masterDO.getCInvoiceNo());
		if (exitMasterDO == null) {
			exitMasterDO = getExistImpInvoiceMasterByInvoiceNo(masterDO.getSupplierCode(), masterDO.getInvoiceNo(),
					masterDO.getShipDate());
		}
		log.info("GWData:" + JSON.toJSONString(masterDO));
		boolean isSimpleUpd = false;
		if (exitMasterDO != null) {
			log.info("GWexist:" + JSON.toJSONString(exitMasterDO));
			if ("6".equals(exitMasterDO.getGwStateCode())) {
				return ResultVo.failure("发票已经成本入库不能重新导入" + masterDO.getInvoiceNo());
			}
			if (exitMasterDO.getStatus().equals(ImpInvoiceMasterStatusEnum.IMPED.getType())
					|| exitMasterDO.getStatus().equals(ImpInvoiceMasterStatusEnum.COSTED.getType())) {
				isSimpleUpd = true;
			}
			// 暂时取消
			/*
			 * if (buInvoiceMaster.getStatusCode().equals(exitMasterDO.
			 * getGwStateCode())) { return ResultVo.failure("发票状态未改变不能重新导入" +
			 * buInvoiceMaster.getStatusCode()); }
			 */

			masterDO.setId(exitMasterDO.getId());
			if (exitMasterDO.getDataType() == null) {
				masterDO.setDataType(1);
			} else {
				masterDO.setDataType(exitMasterDO.getDataType() | 1);
			}
			isNewInvoice = false;
		} else {
			// update by A78027 from bug 10566 in 20230406
			// 关务的供应商代码为空，则不新增导入
			if (StringUtils.isBlank(masterDO.getSupplierCode())) {
				return ResultVo.failure("供应商代码为空不导入" + masterDO.getInvoiceNo() + " ," + masterDO.getCInvoiceNo());
			}
			masterDO.setDataType(1);
		}

		masterDO.setUpdateTime(now);
		masterDO.setUpdateUser("GWIMP");

		masterDO.setTotalQty(buInvoiceMaster.getQuantity());
		masterDO.setStatus(this.getMasterStatus(buInvoiceMaster.getStatusCode()));
		masterDO.setAmount(buInvoiceMaster.getAmount());
		masterDO.setBoxQty(buInvoiceMaster.getBoxQty());
		masterDO.setGrossWeight(buInvoiceMaster.getGross());
		masterDO.setCustomsFee(buInvoiceMaster.getTariffTax());
		masterDO.setVatFee(buInvoiceMaster.getAddedTax());
		masterDO.setTransFee(buInvoiceMaster.getTrafficInsurance());
		masterDO.setExchangeRate(buInvoiceMaster.getRate());
		masterDO.setAmountRmb(BigDecimalUtil.mul(masterDO.getAmount(), masterDO.getExchangeRate()));
		masterDO.setTransType(orderStateService.convertTransType(buInvoiceMaster.getDlvyWay()));
		masterDO.setShipment(buInvoiceMaster.getShipment());
		masterDO.setShipment(masterDO.getShipment() + buInvoiceMaster.getImportCustomer());
		masterDO.setPlantMark(buInvoiceMaster.getPlantMark());
		masterDO.setWeight(buInvoiceMaster.getSuttle());
		masterDO.setCurrency(buInvoiceMaster.getCurrency());
		masterDO.setBargainType(buInvoiceMaster.getBargainType());
		masterDO.setExciseTax(buInvoiceMaster.getExciseTax());
		masterDO.setGwStateCode(buInvoiceMaster.getStatusCode());
		// <!--edit by WuWeiDong 20230901 bug 11976 备注信息拼接 -->StringBuffer
		StringBuffer remark = new StringBuffer();
		remark.append(Optional.ofNullable(buInvoiceMaster.getPaymentTerm()).orElse("")).append(" ")
				.append(Optional.ofNullable(buInvoiceMaster.getCustomerCode()).orElse("")).append(" ")
				.append(Optional.ofNullable(buInvoiceMaster.getShipment()).orElse("")).append(" ")
				.append(Optional.ofNullable(buInvoiceMaster.getImportCustomer()).orElse(""));

		masterDO.setRemark(remark.toString());
		masterDO.setReceiveDate(buInvoiceMaster.getReceiptGoodsDate());

		// 根据smccode转仓库代码
		String receiveWarehouseCode = "";
		// 三方的发票，北京仓入库
		if (masterDO.getInvoiceType() != null && masterDO.getInvoiceType().compareTo(4) == 0) {
			receiveWarehouseCode = this.getWarehouseCodeBySMCCode(buInvoiceMaster.getCustomerCode(), 2);
		} else {
			receiveWarehouseCode = this.getWarehouseCodeBySMCCode(buInvoiceMaster.getCustomerCode(), 1);
		}

		if (StringUtils.isBlank(receiveWarehouseCode)) {
			// OrderNoInfo masterorderNoInfo = new OrderNoInfo().convertJPOrder(
			// buInvoice.getInvoiceDetails()[0].getOrderNo(),
			// buInvoice.getInvoiceDetails()[0].getItemNo());
			// bug 13606 优化采购pono解析成orderno
			OrderNoInfo masterorderNoInfo = purchaseConvertService.convertPoNoFormPurchase(
					buInvoice.getInvoiceDetails()[0].getOrderNo(), buInvoice.getInvoiceDetails()[0].getItemNo());
			receiveWarehouseCode = impInvoiceMasterMapper.getReceiveWarehouseCode(masterorderNoInfo.getPoNo());
			if (StringUtils.isNotBlank(receiveWarehouseCode)) {
				masterDO.setReceiveWarehouseCode(receiveWarehouseCode);
			}
		} else {
			masterDO.setReceiveWarehouseCode(receiveWarehouseCode);
		}

		if (StringUtils.isNotBlank(buInvoiceMaster.getPaymentTerm())
				&& buInvoiceMaster.getPaymentTerm().contains("天")) {
			String paymentTerm = buInvoiceMaster.getPaymentTerm()
					.substring(0, buInvoiceMaster.getPaymentTerm().indexOf("天")).trim();
			masterDO.setPayDay(Integer.parseInt(paymentTerm));
		}
		if (ArrayUtils.isNotEmpty(buInvoice.getDeclareInfos())) {
			masterDO.setCustomsDate(buInvoice.getDeclareInfos()[0].getCustomsDate());
			masterDO.setDeclarationNo(buInvoice.getDeclareInfos()[0].getCustomsDeclarationNo());
		}
		if ("1".equalsIgnoreCase(masterDO.getGwStateCode()) || "2".equalsIgnoreCase(masterDO.getGwStateCode())) {
			masterDO.setStatus(2);// 已报关可以发票入库
		}
		// <!--add by WuWeiDong 20240304 bug 12279 更新无偿品发票-->
		// 判断SMCCode是否为无商业价值类型 或paymenterm是无偿 if(true) { invoiceType=8, status=7
		// }
		if (this.isNoCommercialValue(buInvoiceMaster.getCustomerCode())
				|| Optional.ofNullable(buInvoiceMaster.getPaymentTerm()).orElse("").contains("无偿")) {
			masterDO.setInvoiceType(8);
			masterDO.setStatus(7);
			if (!Optional.ofNullable(buInvoiceMaster.getPaymentTerm()).orElse("").contains("无偿")) {
				masterDO.setRemark(Optional.ofNullable(masterDO.getRemark()).orElse("") + "无商业价值");
			}
		}
		// bug 14223 关务发票导入时，需要增加对 到港时间赋值，同时传输给采购预到货
		if (buInvoiceMaster.getArrivalPortDate()!=null) {
			masterDO.setPortArrivedate(buInvoiceMaster.getArrivalPortDate());
		}
		if (masterDO.getId() == null) {
			masterDO.setCreateTime(masterDO.getUpdateTime());
			masterDO.setCreateUser("GWIMP");
			String billNo = commonServiceFeignApi.generatorBillNo("21").getData();
			if (PublicUtil.isEmpty(billNo)) {
				return ResultVo.failure("生成单号异常");
			}
			masterDO.setId(Long.parseLong(billNo));
			impInvoiceMasterMapper.insert(masterDO);
		} else {
			// 其他在途不能再更改
			if (exitMasterDO.getStatus() != 1 && exitMasterDO.getStatus() != 9) {
				masterDO.setStatus(exitMasterDO.getStatus());
			}
			// 已发票入库不更新仓库
			if (isSimpleUpd) {
				// 如果已经发票入库了，重新导入只更改：金额、费用和关务状态这几个字段 Bug-8785
				// masterDO.setReceiveWarehouseCode(exitMasterDO.getReceiveWarehouseCode());
				exitMasterDO.setAmount(masterDO.getAmount());
				exitMasterDO.setAmountRmb(masterDO.getAmountRmb());
				exitMasterDO.setCustomsFee(masterDO.getCustomsFee());
				exitMasterDO.setVatFee(masterDO.getVatFee());
				exitMasterDO.setTransFee(masterDO.getTransFee());
				exitMasterDO.setOtherFee(masterDO.getOtherFee());
				exitMasterDO.setExciseTax(masterDO.getExciseTax());
				exitMasterDO.setGwStateCode(masterDO.getGwStateCode());
				// bug14513 已发票入库需要追加BargainType的更新
				exitMasterDO.setBargainType(masterDO.getBargainType());
				impInvoiceMasterMapper.updateById(exitMasterDO);
			} else {
				impInvoiceMasterMapper.updateById(masterDO);
			}
		}

		// Map<String, ImpInvoiceDetailVO> impOrdersMap = new
		// HashMap<>(buInvoice.getInvoiceDetails().length);
		List<ImpInvoiceDetailVO> listItemVO = new ArrayList<>(buInvoice.getInvoiceDetails().length);
		ImpInvoiceDetailDO detailDO;
		ImpInvoiceDetailDO existDetailDO;
		OrderNoInfo orderNoInfo;
		// QueryWrapper<OpsPurchaseInvoiceDO> queryWrapper;
		// OpsPurchaseInvoiceDO poOrderDO;
		ImpInvoiceDetailVO impInvoiceDetailVO;
		boolean hasUpdateDetail = false;

		for (BuInvoiceDetail invoiceDetail : buInvoice.getInvoiceDetails()) {
			detailDO = new ImpInvoiceDetailDO();
			detailDO.setInvoiceId(masterDO.getId());
			detailDO.setFromId(invoiceDetail.getSerialId());
			if (!isNewInvoice) {
				existDetailDO = getImpInvoiceDetailByFromId(masterDO.getId(), detailDO.getFromId());
				if (existDetailDO != null) {
					detailDO.setId(existDetailDO.getId());
				}
				if (isSimpleUpd) {
					if (detailDO.getId() != null) {
						detailDO.setPrice(invoiceDetail.getUnitPrice());
						detailDO.setCustomsFee(invoiceDetail.getTariffTax());
						detailDO.setExciseTax(invoiceDetail.getExciseTax());
						detailDO.setOtherFee(invoiceDetail.getOtherTax());
						detailDO.setTransFee(invoiceDetail.getTrafficInsurance());
						detailDO.setVatFee(invoiceDetail.getAddedTax());
						detailDO.setUpdateUser("GWIMP");
						impInvoiceDetailMapper.updateById(detailDO);
						continue;
					}
				}
			}
			// detailDO.setOrderNo(invoiceDetail.getOrderNo());
			// detailDO.setItemNo(Integer.parseInt(invoiceDetail.getItemNo()));

			// bug14427 【交付系统PO海外三国】AP从关务接入发票信息无法正常接入的问题,需要调用新的解析方法
//			orderNoInfo = new OrderNoInfo().convertJPOrder(invoiceDetail.getOrderNo(), invoiceDetail.getItemNo());
			orderNoInfo = purchaseConvertService.convertPoNoFormPurchase(invoiceDetail.getOrderNo(), invoiceDetail.getItemNo());
			detailDO.setOrderNo(orderNoInfo.getOrderNo());
			detailDO.setItemNo(orderNoInfo.getItemNo());
			detailDO.setImpOrderNo(orderNoInfo.getOrFullOrderNo());
			detailDO.setFullOrderNo(orderNoInfo.getFullOrderNo());
			detailDO.setSplitItemNo(orderNoInfo.getSplitItem());
			detailDO.setPoNo(orderNoInfo.getPoNo());
			detailDO.setPoItemNo(orderNoInfo.getPoItemNo());
			detailDO.setOrderType(orderNoInfo.getOrderType() != null ? orderNoInfo.getOrderType().toString() : null);
			// if (detailDO.getOrderNo().startsWith("#")
			// || detailDO.getOrderNo().startsWith("EX.") ||
			// detailDO.getOrderNo().startsWith("MS-")) {
			// detailDO.setOrderType("99");
			// }
			detailDO.setStatus(1);
			detailDO.setInvoiceNo(masterDO.getInvoiceNo());
			detailDO.setModelNo(invoiceDetail.getModel());
			detailDO.setQuantity(invoiceDetail.getQuantity().intValue());
			detailDO.setPrice(invoiceDetail.getUnitPrice());
			detailDO.setShipDate(masterDO.getShipDate());
			detailDO.setShipMethod(masterDO.getTransType());
			detailDO.setEnName(invoiceDetail.getDescriptionCustoms());
			detailDO.setWeight(invoiceDetail.getWeight());
			detailDO.setOrigin(SMCCodeUtil.getSupplierCodeFromJPWhereCode(invoiceDetail.getCountryOrigin()));
			detailDO.setSupplierCode(masterDO.getSupplierCode());
			detailDO.setCurrency(masterDO.getCurrency());
			detailDO.setImpModelNo(invoiceDetail.getModel());
			detailDO.setOverseaInvoiceNo(invoiceDetail.getInvoiceOriginal());
			detailDO.setCustomsFee(invoiceDetail.getTariffTax());
			detailDO.setExciseTax(invoiceDetail.getExciseTax());
			detailDO.setOtherFee(invoiceDetail.getOtherTax());
			detailDO.setTransFee(invoiceDetail.getTrafficInsurance());
			detailDO.setVatFee(invoiceDetail.getAddedTax());
			detailDO.setNonCommercial(invoiceDetail.getNonCommercial());
			if (invoiceDetail.getNonCommercial() != null && "*".equalsIgnoreCase(invoiceDetail.getNonCommercial())) {
				detailDO.setNonCommercial("1");
			}
			// <!--add by WuWeiDong 20230505 bug 10636 Imp_invoice_detail发票表增加金额
			// -->
			detailDO.setAmount(invoiceDetail.getAmount());
			if (PublicUtil.isNotEmpty(detailDO.getOverseaInvoiceNo())
					&& (detailDO.getOverseaInvoiceNo().startsWith("DR000")
							|| "*".equals(detailDO.getOverseaInvoiceNo()))) {
				detailDO.setNonCommercial("1");
				detailDO.setStatus(7);
			}
			detailDO.setUpdateTime(now);

			// 香港入库型号去掉L-
			// if ("HK".equals(detailDO.getSupplierCode()) &&
			// detailDO.getModelNo().startsWith("L-")) {
			// detailDO.setModelNo(detailDO.getModelNo().substring(2));
			// }

			// 超过30的型号,取采购表里面的型号替换
			// if (detailDO.getModelNo().length() == 30) {
			// queryWrapper = Wrappers.query();
			// queryWrapper.eq("poNo", detailDO.getPoNo());
			// queryWrapper.eq("lineItem", detailDO.getPoItemNo());
			// poOrderDO = this.getPurchaseInvoiceByWrapper(queryWrapper);
			// if (null != poOrderDO && poOrderDO.getModelNo().length() > 30) {
			// detailDO.setModelNo(poOrderDO.getModelNo());
			// }
			// }
			if (detailDO.getOrderNo().length() > 20) {
				detailDO.setOrderNo(detailDO.getOrderNo().substring(0, 20));
			}

			if (detailDO.getId() == null) {
				detailDO.setCreateUser("GWIMP");
				detailDO.setCreateTime(now);
				impInvoiceDetailMapper.insert(detailDO);
			} else {
				hasUpdateDetail = true;
				detailDO.setUpdateUser("GWIMP");
				impInvoiceDetailMapper.updateById(detailDO);
			}
			impInvoiceDetailVO = BeanCopyUtil.copy(detailDO, ImpInvoiceDetailVO.class);
			listItemVO.add(impInvoiceDetailVO);
			// String key = detailDO.getPoNo() + "~" + detailDO.getPoItemNo();
			// if (impOrdersMap.containsKey(key)) {
			// ImpInvoiceDetailVO lastVO = impOrdersMap.get(key);
			// impInvoiceDetailVO.setQuantity(impInvoiceDetailVO.getQuantity() +
			// lastVO.getQuantity());
			// } else {
			// impOrdersMap.put(key, impInvoiceDetailVO);
			// }
		}

		// 重复导入关务发票状态为【预计到货|已到货待入库】时,明细可能重复录入,需删除之前录入的明细
		if (!hasUpdateDetail && (ImpInvoiceMasterStatusEnum.PREARRIVAL.getType().equals(masterDO.getStatus())
				|| ImpInvoiceMasterStatusEnum.BEIMP.getType().equals(masterDO.getStatus()))) {
			LambdaQueryWrapper<ImpInvoiceDetailDO> detailQuery = Wrappers.lambdaQuery();
			detailQuery.eq(ImpInvoiceDetailDO::getInvoiceId, masterDO.getId()).lt(ImpInvoiceDetailDO::getUpdateTime,
					now);
			impInvoiceDetailMapper.delete(detailQuery);
		}

		// 三国的默认复制发票数据到分包数据里面
		if (PublicUtil.isNotEmpty(masterDO.getSupplierCode())
				&& !NOT_COPY_DETAIL_TO_PACK.contains(masterDO.getSupplierCode())) {
			copyToInvoicedetailPack(masterDO.getId());
		}

		// 3.检查发票差异
		checkImpInvoiceError(masterDO.getId());
		// 把数据传递采购结果和更新订单状态
		// 4.更新采购在途数量
		if (masterDO.getInvoiceType() != null && masterDO.getInvoiceType().compareTo(3) != 0) {
			toUpdatePOTransStatus(listItemVO, masterDO);
		}

		// 5.更新订单状态
		if (masterDO.getStatus() == 1 || masterDO.getStatus() == 2) {
			toUpdateOrderState(masterDO, listItemVO);
		}

		// 如果三方发票，发送发票入库消息
		if (masterDO.getInvoiceType() != null && masterDO.getInvoiceType().compareTo(4) == 0) {
			// 发送发票入库消息
			ImpInvoiceProcessDTO processDTO = new ImpInvoiceProcessDTO();
			processDTO.setInvoiceId(masterDO.getId());
			processDTO.setProcessType(3);
			this.sendInvoiceProcessMsgToMQ(processDTO);
		}

		return ResultVo.success(masterDO.getInvoiceNo(),
				"导入" + masterDO.getInvoiceNo() + ",项数：" + buInvoice.getInvoiceDetails().length + "原金额："
						+ buInvoice.getInvoice().getAmount() + "：关务状态:" + buInvoice.getInvoice().getStatusCode());
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ImpInvoiceDetailDO getImpInvoiceDetailByFromId(Long invoiceId, Long fromid) {
		LambdaQueryWrapper<ImpInvoiceDetailDO> detailQuery = Wrappers.lambdaQuery();
		detailQuery.select(ImpInvoiceDetailDO::getId);
		detailQuery.eq(ImpInvoiceDetailDO::getInvoiceId, invoiceId).eq(ImpInvoiceDetailDO::getFromId, fromid);
		return impInvoiceDetailMapper.selectOne(detailQuery);
	}

	/**
	 * 获取状态
	 *
	 * @param statusCode
	 *            0-预到货; 1-到货
	 * @return 预计到货: 0 --> 1; 已到货: 1 --> 2;
	 */
	private Integer getMasterStatus(String statusCode) {
		if ("0".equals(statusCode)) {
			return 1; // "0" --> 1 预计到货
		} else if ("1".equals(statusCode)) {
			return 2; // "1" --> 2 已到货
		} else if ("2".equals(statusCode)) {
			return 2; // "2" --> 2 已到货
		} else {
			return null;
		}
	}

	/*
	 * private String convertShipMethodToTransType(String shipMethod) { if
	 * (shipMethod == null) { return "0"; } String transType; if
	 * (shipMethod.startsWith("A")) { transType = "1"; } else if
	 * (shipMethod.startsWith("S")) { transType = "0"; } else { transType = "0";
	 * } return transType; }
	 */

	/**
	 * 解析字符长度发票数据 共计字符259
	 *
	 * @param strLine
	 * @return
	 */
	private ImportOrderInfoVO convertJPShippingFileLine(String strLine) {
		ImportOrderInfoVO info = new ImportOrderInfoVO();
		// 当发票号为空(shipdate也为空) 或者 shipStatus不等于A时 不解析
		// if (strLine.length() < 30 || !"A".equals(strLine.substring(30, 31))
		// ||
		// PublicUtil.isEmpty(strLine.substring(114, 124).trim())) {
		// return null;
		// }

		if (strLine.length() < 30) {
			return null;
		}

		try {

			info.setSupplierCode("JP");
			// String companyCode = strLine.substring(0, 5);
			// if ("9501202".equals(companyCode))//只不导入北京的GSS
			// {
			// return null;
			// }
			// 订单号
			String orderNo = strLine.substring(7, 27).trim();
			// 订单项号子项
			String itemNo = strLine.substring(27, 30).trim();

			info.setShipStatus(strLine.substring(30, 31));
			if ("A".equalsIgnoreCase(info.getShipStatus())) {
				info.setDataType(1);// 发货数据
			} else if ("G".equalsIgnoreCase(info.getShipStatus())) {
				info.setDataType(2);
			} else if ("N".equalsIgnoreCase(info.getShipStatus())) {
				info.setDataType(3);// 生产中
				info.setRemark(info.getShipStatus());
			} else if ("W".equalsIgnoreCase(info.getShipStatus())) {
				info.setDataType(4);// 出厂
				info.setRemark(info.getShipStatus());
			} else {
				return null;
			}
			// GSS补货订单
			// if (orderNo.startsWith("GCNG") || orderNo.startsWith("GCNZ")) {
			// if ("G".equals(info.getShipStatus())) {
			// info.setOrderNo(orderNo);
			// info.setStatus(2);
			// } else {
			// info.setOrderNo(orderNo.substring(1, 11));
			// }
			// }
			// OrderNoInfo orderNoInfo = new
			// OrderNoInfo().convertJPOrder(orderNo, itemNo);
			// bug 13606 优化采购pono解析成orderno
			OrderNoInfo orderNoInfo = purchaseConvertService.convertPoNoFormPurchase(orderNo, itemNo);
			info.setOrderNo(orderNoInfo.getOrderNo());
			info.setItemNo(orderNoInfo.getItemNo());
			info.setSplitItemNo(orderNoInfo.getSplitItem());
			info.setPoItemNo(orderNoInfo.getPoItemNo().toString());
			info.setFullOrderNo(orderNoInfo.getFullOrderNo());
			info.setPoNo(orderNoInfo.getPoNo());
			info.setOrFullOrderNo(orderNoInfo.getOrFullOrderNo());
			// 订单类型
			info.setOrderType(orderNoInfo.getOrderType().toString());
			// 型号
			info.setModelNo(strLine.substring(31, 61).trim());
			// 数量
			info.setOrderTotalQty(Integer.parseInt(strLine.substring(61, 68)));
			// 此箱数量
			info.setQuantity(Integer.parseInt(strLine.substring(68, 75)));
			// 出口仓库数量
			info.setQtyInExport(Integer.parseInt(strLine.substring(75, 82)));
			// 日本承诺日期[日期格式=“YYMMDD”/6位]
			info.setJPPromiseDate(strLine.substring(84, 90));
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
			/*
			 * try {
			 * info.setPromiseDate(simpleDateFormat.parse(info.getJPPromiseDate(
			 * ))); } catch (Exception ex) { }
			 */
			if ("A".equals(info.getShipStatus()) && strLine.length() > 95) {
				// 发货日期[日期格式=“YYMMDD”/6位]
				String strShipDate = strLine.substring(90, 96);
				if (StringUtils.isNotBlank(strShipDate)) {
					info.setShippedDate(simpleDateFormat.parse(strShipDate));
				}
				info.setStatus(1);
			}
			if ("W".equals(info.getShipStatus()) && strLine.length() >= 155) {
				// Japan Processed Date 日本处理时间 [日期格式=“YYMMDD”/6位]
				String jpProcessDateStr = strLine.substring(149, 155);
				if (StringUtils.isNotBlank(jpProcessDateStr)) {
					info.setOptDate(simpleDateFormat.parse(jpProcessDateStr));
				}
				info.setStatus(1);
			}
			// 运输方式
			// SEA :Ship
			// AIR :Air Plane
			// FEDEX
			// COURIER
			if (strLine.length() > 105) {
				info.setShippingMethod(strLine.substring(96, 106).trim());
				info.setShippingMethod(orderStateService.convertTransType(info.getShippingMethod()));
			}
			// 发票号
			if (strLine.length() > 123) {
				info.setInvoiceNo(strLine.substring(114, 124).trim());
			}
			// 箱号Skid#
			if (strLine.length() > 126) {
				info.setCaseNo(strLine.substring(124, 127).trim());
			}
			// 59 Product Code
			if (strLine.length() > 148) {
				info.setECode(strLine.substring(145, 149).trim());
			}
			// FOB Price
			if (strLine.length() > 135) {
				String strPrice = strLine.substring(127, 136).trim();
				// CIF Price
				if (!PublicUtil.isEmpty(strPrice)) {
					BigDecimal price = new BigDecimal(strPrice);
					// 金额需要除以100
					info.setPrice(price.divide(new BigDecimal("100"), 3, BigDecimal.ROUND_HALF_UP));
				}
			}
			// 序号
			if (strLine.length() > 162) {
				String str = strLine.substring(155, 163);
				if (!PublicUtil.isEmpty(str)) {
					info.setSeqNo(Integer.parseInt(str));
				}
			}
			// 代码来自哪里
			if (strLine.length() > 183) {
				info.setWhereCode(SMCCodeUtil.getSupplierCodeFromJPWhereCode(strLine.substring(183, 184)));
			}
			if (strLine.length() >= 197) {
				// 标签条形码
				info.setShippingLabel(strLine.substring(184, 197));
			}
			// 重量
			if (strLine.length() >= 207) {
				String strWeight = strLine.substring(198, 207).trim();
				int weight = 0;
				if (!PublicUtil.isEmpty(strWeight)) {
					int weightInt = Integer.parseInt(strWeight);
					info.setWeight(weightInt / 100000.0000);
				}
			}
			// 货架号
			if (strLine.length() >= 207) {
				info.setShelfNo(strLine.substring(207, 217).trim());
			}
			if (strLine.length() > 217) {
				// 原产国
				info.setOrigin(strLine.substring(217, 218));
			}
			// RoHS代码
			if (strLine.length() > 218) {
				info.setRoHSCode(strLine.substring(218, 219));
			}
			// 原产国2
			if (strLine.length() > 220) {
				String counrty = strLine.substring(220, 222);
				// 供应商代码
				if (PublicUtil.isNotEmpty(counrty)) {
					info.setOrigin(counrty);
				}
			}
			// HS Code
			if (strLine.length() > 222) {
				info.setHSCode(strLine.substring(222, 237));
			}
			// if (companyCode.startsWith("06006")) {
			// //广州制造公司代码
			// info.setOwnerCode("CNG");
			// } else {
			// //广州分公司代码
			// info.setOwnerCode("CNZ");
			// }
			// 备注
			info.setRemark(orderNo + itemNo);
			// if (orderNo.contains("-DR")) {
			// info.setOrderType("R");
			// }
		} catch (Exception ex) {
			log.error("convertJPShippingFileLine = {}", strLine);
			log.error("convertJPShippingFileLine error: {}", ex.getMessage(), ex);
            return null;
		}
		return info;
	}

	/**
	 * 按供应商的发票号查询是否存在相同的发票
	 *
	 * @param supplierCode
	 * @param invoiceNo
	 * @param shipDate
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ImpInvoiceMasterDO getExistImpInvoiceMasterByInvoiceNo(String supplierCode, String invoiceNo,
			Date shipDate) {

		if (shipDate == null) {
			shipDate = DateUtil.getToday();
		}
		Date fromDate = DateUtil.addDay(shipDate, -60);
		LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(ImpInvoiceMasterDO::getInvoiceNo, invoiceNo);
		queryWrapper.ne(ImpInvoiceMasterDO::getStatus, 9);
		queryWrapper.and(wq -> wq.ge(ImpInvoiceMasterDO::getShipDate, fromDate).or()
				.ge(ImpInvoiceMasterDO::getCreateTime, fromDate));

		// queryWrapper.le(ImpInvoiceMasterDO::getShipDate,
		// DateUtil.addDay(shipDate, 30));
		queryWrapper.eq(StringUtils.isNotBlank(supplierCode), ImpInvoiceMasterDO::getSupplierCode, supplierCode);
		List<ImpInvoiceMasterDO> list = this.impInvoiceMasterMapper.selectList(queryWrapper);

		if (list.isEmpty()) {
			return null;
		}
		return list.get(list.size() - 1);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ImpInvoiceMasterDO getExistImpInvoiceMasterByGWInvoiceNo(String gwCinvoiceNo) {

		// Date fromDate = DateUtil.addDay(shipDate, -60);
		LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(ImpInvoiceMasterDO::getCInvoiceNo, gwCinvoiceNo);
		List<ImpInvoiceMasterDO> list = this.impInvoiceMasterMapper.selectList(queryWrapper);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(list.size() - 1);
	}

	/**
	 * 导入分包明细
	 *
	 * @param importOrderInfoVOs
	 * @return
	 */
	@Override
	// @Transactional(rollbackFor = Exception.class)
	public ResultVo<String> addImpInvoiceDataPack(List<ImportOrderInfoVO> importOrderInfoVOs) {

		StringBuilder sbMsg = new StringBuilder();
		// 1.解析按发票明细合并
		try {
			// 汇总发票号
			List<String> invoiceNos = importOrderInfoVOs.stream().map(ImportOrderInfoVO::getInvoiceNo).distinct()
					.collect(Collectors.toList());
			List<ImpInvoiceInfoData> listInvoiceMasters = new ArrayList<>(invoiceNos.size());
			ImpInvoiceInfoData impInvoiceInfoData;
			List<ImportOrderInfoVO> orderInfoVOList;
			ImpInvoiceMasterDO masterDO;
			List<ImpInvoiceDetailPackDO> impInvoiceDetailDOS;
			ImpInvoiceDetailPackDO detailDO;
			// OrderNoInfo orderNoInfo;

			for (String invoiceNo : invoiceNos) {
				if (PublicUtil.isEmpty(invoiceNo)) {
					continue;
				}
				impInvoiceInfoData = new ImpInvoiceInfoData();

				// 按发票号筛选发票明细
//				orderInfoVOList = importOrderInfoVOs.stream().filter(x -> x.getInvoiceNo().equals(invoiceNo))
//						.collect(Collectors.toList());
                orderInfoVOList = importOrderInfoVOs.stream().filter(x -> x.getInvoiceNo() != null && invoiceNo.equals(x.getInvoiceNo()))
                        .collect(Collectors.toList());

				// importOrderInfoVOs = importOrderInfoVOs.stream().filter(x ->
				// x.getInvoiceNo().equals(invoiceNo)).collect(Collectors.toList());
				// 设置master
				masterDO = new ImpInvoiceMasterDO();
				masterDO.setInvoiceNo(invoiceNo);
				masterDO.setCreateUser("JP");
				masterDO.setStatus(1);
				masterDO.setShipDate(orderInfoVOList.get(0).getShippedDate());
				masterDO.setSupplierCode("JP");
				masterDO.setTransType(orderInfoVOList.get(0).getShippingMethod());
				masterDO.setInvoiceType(getInvoiceType(masterDO.getSupplierCode()));
				// 汇总金额
				// Double price =
				// orderInfoVOList.stream().collect(Collectors.summingDouble(x
				// -> (Double) x.getPrice()))));
				/*
				 * BigDecimal price = orderInfoVOList.stream() //
				 * 将user对象的age取出来map为Bigdecimal
				 * .map(ImportOrderInfoVO::getAmount) // 使用reduce()聚合函数,实现累加器
				 * .reduce(BigDecimal.ZERO, BigDecimal::add);
				 */
				// 箱数
				int caseQty = orderInfoVOList.size();
				// 重量
				double weight = orderInfoVOList.stream().mapToDouble(ImportOrderInfoVO::getWeight).sum();
				// 数量汇总
				Integer qty = orderInfoVOList.stream().mapToInt(ImportOrderInfoVO::getQuantity).sum();
				// 订单项数
				Integer orderQty = (int) orderInfoVOList.stream().map(ImportOrderInfoVO::getFullOrderNo).distinct()
						.count();

				// masterDO.setAmount(price);
				masterDO.setBoxQty(caseQty);
				masterDO.setWeight(weight);
				masterDO.setOrderQty(orderQty);
				masterDO.setTotalQty(qty);
				// masterDO.setAmountRmb(price);
				impInvoiceInfoData.setImpInvoiceMaster(masterDO);

				impInvoiceDetailDOS = new ArrayList<>(orderInfoVOList.size());

				for (ImportOrderInfoVO impOrderVO : orderInfoVOList) {

					detailDO = new ImpInvoiceDetailPackDO();
					// orderNoInfo = new
					// OrderNoInfo().convertPOOrder(impOrderVO.getOrderNo(),
					// String.valueOf(impOrderVO.getItemNo()));

					detailDO.setOrderNo(impOrderVO.getOrderNo());
					detailDO.setItemNo(impOrderVO.getItemNo());
					detailDO.setSplitItemNo(impOrderVO.getSplitItemNo());
					detailDO.setImpOrderNo(impOrderVO.getOrFullOrderNo());
					detailDO.setFullOrderNo(impOrderVO.getFullOrderNo());
					detailDO.setPoNo(impOrderVO.getPoNo());
					detailDO.setPoItemNo(Integer.parseInt(impOrderVO.getPoItemNo()));
					// detailDO.setPurchaseType(orderNoInfo.getPurchaseType());

					detailDO.setCaseNo(impOrderVO.getCaseNo());
					detailDO.setBarcode(impOrderVO.getShippingLabel());
					detailDO.setCreateUser("");
					detailDO.setCurrency(impOrderVO.getCurrency());
					detailDO.setEnName(impOrderVO.getCNName());
					// 来源代码
					detailDO.setFromCode(impOrderVO.getWhereCode());
					detailDO.setInvoiceNo(impOrderVO.getInvoiceNo());
					detailDO.setModelNo(impOrderVO.getModelNo());
					detailDO.setOrderType(impOrderVO.getOrderType());
					detailDO.setOrigin(impOrderVO.getOrigin());
					detailDO.setPrice(impOrderVO.getPrice());
					detailDO.setProductCode(impOrderVO.getECode());
					detailDO.setQuantity(impOrderVO.getQuantity());
					detailDO.setRemark(impOrderVO.getRemark());
					detailDO.setShipDate(impOrderVO.getShippedDate());
					detailDO.setShipMethod(impOrderVO.getShippingMethod());
					detailDO.setStatus(1);
					detailDO.setSupplierCode(impOrderVO.getSupplierCode());
					detailDO.setWeight(impOrderVO.getWeight());
					detailDO.setShelfCode(impOrderVO.getShelfNo());
					detailDO.setRoHSCode(impOrderVO.getRoHSCode());
					detailDO.setCreateUser("SHPINF");
					detailDO.setUpdateUser("SHPINF");

					impInvoiceDetailDOS.add(detailDO);
				}
				impInvoiceInfoData.setImpInvoiceDetail(impInvoiceDetailDOS);
				listInvoiceMasters.add(impInvoiceInfoData);
			}

			ResultVo<String> saveResult;
			List<ImpInvoiceDetailVO> invoiceDetails;

			// 1.导入到到货分包数据
			for (ImpInvoiceInfoData impInoviceInfo : listInvoiceMasters) {

				log.info("IMP JP SHPINF : {}", impInoviceInfo.getImpInvoiceMaster().getInvoiceNo());
				// 1.1.保存发票明细
				try {
					saveResult = saveImpInvoicePack(impInoviceInfo);
					sbMsg.append(saveResult.getMessage()).append("\r\n");
					if (!saveResult.isSuccess()) {
						log.error(saveResult.getMessage());
						continue;
					}
				} catch (Exception e) {
					log.error("导入箱单失败：" + e.getMessage());
					log.error(impInoviceInfo.toString());
					sbMsg.append(impInoviceInfo.getImpInvoiceMaster().getInvoiceNo())
							.append("导入箱单失败：" + e.getMessage());
				}
			}
		} catch (Exception e) {
			log.error("导入分包明细 {}", e.getMessage(), e);
			return ResultVo.failure(e.getMessage());
		}
		return ResultVo.success("导入完毕.", sbMsg.toString());
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ImpInvoiceDetailPackDO getImpInvoiceDetailPack(Long invoiceId, String fullOrderNo, String barcode) {
		LambdaQueryWrapper<ImpInvoiceDetailPackDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ImpInvoiceDetailPackDO::getInvoiceId, invoiceId).eq(ImpInvoiceDetailPackDO::getBarcode, barcode)
				.eq(ImpInvoiceDetailPackDO::getFullOrderNo, fullOrderNo);
		List<ImpInvoiceDetailPackDO> list = impInvoiceDetailPackMapper.selectList(queryWrapper);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 保存发票分包数据
	 *
	 * @param impInvoiceInfoData
	 * @return
	 */
	@Transactional
	public ResultVo<String> saveImpInvoicePack(ImpInvoiceInfoData impInvoiceInfoData) {
		// 1.查询是否存在相同的发票
		ImpInvoiceMasterDO invoiceMasterDO = impInvoiceInfoData.getImpInvoiceMaster();

		// 1.检查是否存在
		ImpInvoiceMasterDO existMasterDO = getExistImpInvoiceMasterByInvoiceNo(invoiceMasterDO.getSupplierCode(),
				invoiceMasterDO.getInvoiceNo(), invoiceMasterDO.getShipDate());

		if (existMasterDO != null) {
			if (existMasterDO.getStatus() != 1 && existMasterDO.getStatus() != 2 && existMasterDO.getStatus() != 9) {
				return ResultVo.failure("已经存在和入库不可以再变更" + invoiceMasterDO.getInvoiceNo());
			}
			invoiceMasterDO.setId(existMasterDO.getId());
			invoiceMasterDO.setShipDate(existMasterDO.getShipDate());
			invoiceMasterDO.setShipment(existMasterDO.getShipment());
			if (existMasterDO.getPreArriveDate() != null) {
				invoiceMasterDO.setPreArriveDate(existMasterDO.getPreArriveDate());
			}
		}

		// 计算预计到货日期
		if ((existMasterDO == null || existMasterDO.getPreArriveDate() == null) && invoiceMasterDO.getShipDate() != null
				&& invoiceMasterDO.getPreArriveDate() == null) {
			/**
			 * 如果收货仓库为空时，用订单查查询
			 */
			if (PublicUtil.isEmpty(invoiceMasterDO.getReceiveWarehouseCode())) {
				ImpInvoiceDetailPackDO detailDO = impInvoiceInfoData.getImpInvoiceDetail().get(0);
				if (detailDO.getSplitItemNo() == null) {
					detailDO.setSplitItemNo(0);
				}
				invoiceMasterDO.setReceiveWarehouseCode(impInvoiceMasterMapper.getReceiveWarehouseCodeByOrderNo(
						detailDO.getOrderNo(), detailDO.getItemNo(), detailDO.getSplitItemNo()));
			}
			invoiceMasterDO.setPreArriveDate(orderStateService.calcEsArrivalDate(
					invoiceMasterDO.getReceiveWarehouseCode(), invoiceMasterDO.getSupplierCode(),
					invoiceMasterDO.getTransType(), invoiceMasterDO.getShipDate()));
		}

		// 2.保存到导入发票主表
		if (invoiceMasterDO.getId() == null) {
			invoiceMasterDO.setDataType(2);
			String billNo = commonServiceFeignApi.generatorBillNo("21").getData();
			if (PublicUtil.isEmpty(billNo)) {
				return ResultVo.failure("生成单号异常");
			}
			invoiceMasterDO.setId(Long.parseLong(billNo));
			invoiceMasterDO.setCreateUser("jpship");
			int insert = impInvoiceMasterMapper.insert(invoiceMasterDO);
			if (insert != 1) {
				log.error("[ add ImpInvoiceMasterDO ]改变行数小于 1 ,操作失败 ");
				return ResultVo.failure("[ add ImpInvoiceMasterDO ]改变行数小于 1 ,操作失败 ");
			}
		} else {
			invoiceMasterDO.setDataType(existMasterDO.getDataType() | 2);

			ImpInvoiceMasterDO updMasterDO = new ImpInvoiceMasterDO();
			updMasterDO.setId(existMasterDO.getId());
			updMasterDO.setDataType(invoiceMasterDO.getDataType());
			updMasterDO.setOrderQty(invoiceMasterDO.getOrderQty());
			updMasterDO.setTotalQty(invoiceMasterDO.getTotalQty());
			updMasterDO.setBoxQty(invoiceMasterDO.getBoxQty());
			updMasterDO.setUpdateTime(new Date());
			updMasterDO.setUpdateUser("jpship");
			impInvoiceMasterMapper.updateById(updMasterDO);
		}

		int successItem = 0;
		int faultItem = 0;
		ImpInvoiceDetailPackDO exitPackID;
		QueryWrapper<OpsPurchaseInvoiceDO> queryWrapper = new QueryWrapper<>();
		OpsPurchaseInvoiceDO poOrderDO;
		// 3.写入到分包明细表
		for (ImpInvoiceDetailPackDO impInvoiceDetailDO : impInvoiceInfoData.getImpInvoiceDetail()) {
			impInvoiceDetailDO.setInvoiceId(invoiceMasterDO.getId());

			exitPackID = getImpInvoiceDetailPack(invoiceMasterDO.getId(), impInvoiceDetailDO.getFullOrderNo(),
					impInvoiceDetailDO.getBarcode());
			if (exitPackID != null) {
				// log.info("已存在pack: {} {}", exitPackID.getOrderNo(),
				// exitPackID.getId());
				faultItem++;
				continue;
			}
			// 香港入库型号去掉L-
			// if ("HK".equals(impInvoiceDetailDO.getSupplierCode()) &&
			// impInvoiceDetailDO.getModelNo().startsWith("L-")) {
			// impInvoiceDetailDO.setModelNo(impInvoiceDetailDO.getModelNo().substring(2));
			// }

			// 超过30的型号,取采购表里面的型号替换
			// if (impInvoiceDetailDO.getModelNo().length() == 30
			// || impInvoiceDetailDO.getModelNo().length() == 29) {
			// queryWrapper.clear();
			// queryWrapper.eq("poNo", impInvoiceDetailDO.getPoNo());
			// queryWrapper.eq("lineItem", impInvoiceDetailDO.getPoItemNo());
			// poOrderDO = this.getPurchaseInvoiceByWrapper(queryWrapper);
			// if (null != poOrderDO && poOrderDO.getModelNo().length() > 30) {
			// impInvoiceDetailDO.setImpModelNo(impInvoiceDetailDO.getModelNo());
			// impInvoiceDetailDO.setModelNo(poOrderDO.getModelNo());
			// }
			// }

			addImpInvoiceDetailPack(impInvoiceDetailDO);
			successItem++;
		}
		List<ImpInvoiceDetailVO> invoiceDetails = BeanCopyUtil.copyList(impInvoiceInfoData.getImpInvoiceDetail(),
				ImpInvoiceDetailVO.class);

		// 2.生成在途预到货数据
		// update by A78027 from bug 10180 in 2023-04-06
		// 没有导关务发票数据，按箱单数据生成在途
		if ((invoiceMasterDO.getDataType() & 1) == 0) {
			// 是否启动箱单预到货,1启动
			ResultVo<DataTypeVO> dataTypeResultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "14");
			if (dataTypeResultVo != null && "1".equals(dataTypeResultVo.getData().getExtNote1())) {
				toUpdatePOTransStatus(invoiceDetails, invoiceMasterDO);
			}
		}

		// 3更新订单状态
		try {
			toUpdateOrderState(invoiceMasterDO, invoiceDetails);
		} catch (Exception e) {
			log.error("更新货期失败:{} {}", e.getMessage(), invoiceMasterDO.toString());
		}

		return ResultVo.success(invoiceMasterDO.getInvoiceNo(),
				invoiceMasterDO.getInvoiceNo() + "增加项数：" + successItem + ",存在项数：" + faultItem);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public OpsPurchaseInvoiceDO getPurchaseInvoiceByWrapper(Wrapper<OpsPurchaseInvoiceDO> queryWrapper) {
		return opsPurchaseInvoiceMapper.selectOne(queryWrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> addInvoiceMasterData(ImpInvoiceMasterDO invoiceMasterDO) {
		try {
			if (PublicUtil.isEmpty(invoiceMasterDO.getInvoiceNo())) {
				return ResultVo.failure("必须输入发票号");
			}
			// if (PublicUtil.isEmpty(invoiceMasterDO.getExchangeRate())) {
			// return ResultVo.failure("必须输入汇率");
			// }
			if (PublicUtil.isEmpty(invoiceMasterDO.getSupplierCode())) {
				return ResultVo.failure("必须选择供应商");
			}
			LoginUserDTO user = SMCApp.getLoginAuthDto();
			invoiceMasterDO.setUpdateTime(new Date());
			invoiceMasterDO.setUpdateUser(user.getUserNo());
			int invoiceType = getInvoiceType(invoiceMasterDO.getSupplierCode());
			invoiceMasterDO.setInvoiceType(invoiceType);
			// 判断id是否为空
			if (invoiceMasterDO.getId() == null || invoiceMasterDO.getId() == 0) {

				ImpInvoiceMasterDO exitMasterDO = getExistImpInvoiceMasterByInvoiceNo(invoiceMasterDO.getSupplierCode(),
						invoiceMasterDO.getInvoiceNo(), invoiceMasterDO.getShipDate());
				if (exitMasterDO != null) {
					return ResultVo.failure("已存在该发票，不能重复添加！");
				}
				invoiceMasterDO.setStatus(10);
				invoiceMasterDO.setDataType(1);

				String billNo = commonServiceFeignApi.generatorBillNo("21").getData();
				if (PublicUtil.isEmpty(billNo)) {
					return ResultVo.failure("生成单号异常");
				}
				invoiceMasterDO.setId(Long.parseLong(billNo));
				invoiceMasterDO.setCreateTime(new Date());
				invoiceMasterDO.setCreateUser(user.getUserNo());
				int insert = impInvoiceMasterMapper.insert(invoiceMasterDO);

				return insert == 1 ? ResultVo.success("新增成功") : ResultVo.failure("新增失败");
			} else {
				ImpInvoiceMasterDO existImpMasterDO = getImpInvoiceMasterById(invoiceMasterDO.getId());
				if (existImpMasterDO == null) {
					return ResultVo.failure("发票不存在" + existImpMasterDO.getId());
				}
				if (existImpMasterDO.getStatus().equals(3) || existImpMasterDO.getStatus().equals(6)) {
					return ResultVo.failure("状态已发生变更，不可以再修改" + existImpMasterDO.getStatus());
				}

				// 不为空则进行修改
                impInvoiceMasterMapper.updateById(invoiceMasterDO);

                // update by lyc   bug 13843 发票修改接口补充修改子表逻辑
                // 修改Imp_invoice_detail供应商
                LambdaUpdateWrapper<ImpInvoiceDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper
                        .eq(ImpInvoiceDetailDO::getInvoiceId, invoiceMasterDO.getId())
                        .set(ImpInvoiceDetailDO::getSupplierCode,invoiceMasterDO.getSupplierCode())
                        .set(ImpInvoiceDetailDO::getUpdateTime, new Date())
                        .set(ImpInvoiceDetailDO::getUpdateUser,user.getUserNo());
                impInvoiceDetailMapper.update(null,updateWrapper);
                // 修改 Imp_invoice_detail_pack 供应商
                LambdaUpdateWrapper<ImpInvoiceDetailPackDO> updateWrapper2 = new LambdaUpdateWrapper<>();
                updateWrapper2
                        .eq(ImpInvoiceDetailPackDO::getInvoiceId, invoiceMasterDO.getId())
                        .set(ImpInvoiceDetailPackDO::getSupplierCode,invoiceMasterDO.getSupplierCode())
                        .set(ImpInvoiceDetailPackDO::getUpdateTime, new Date())
                        .set(ImpInvoiceDetailPackDO::getUpdateUser,user.getUserNo());
                impInvoiceDetailPackMapper.update(null,updateWrapper2);

				return ResultVo.success("修改成功");
			}

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> addInvoiceDetailData(ImpInvoiceDetailDO invoiceDetailDO) {
		try {
			if (StringUtils.isNotBlank(invoiceDetailDO.getCaseNo()) && invoiceDetailDO.getCaseNo().length() < 3) {
				return ResultVo.failure("箱号不能少于3位数字，可以为空，建议取3位");
			}
			ImpInvoiceMasterDO masterDO = this.getImpInvoiceMasterById(invoiceDetailDO.getInvoiceId());
			if (!(masterDO.getStatus() == 1 || masterDO.getStatus() == 2 || masterDO.getStatus() == 10)) {
				return ResultVo.failure("当前状态不能新增明细，请检查发票状态");
			}
			if (PublicUtil.isEmpty(invoiceDetailDO.getPoNo()) || PublicUtil.isEmpty(invoiceDetailDO.getPoItemNo())) {
				invoiceDetailDO.setPoNo(invoiceDetailDO.getOrderNo());
				invoiceDetailDO.setPoItemNo(invoiceDetailDO.getItemNo());
			}
			invoiceDetailDO.setStatus(1);
			invoiceDetailDO.setSupplierCode(masterDO.getSupplierCode());
			invoiceDetailDO.setShipDate(masterDO.getShipDate());
			if (PublicUtil.isEmpty(invoiceDetailDO.getBarcode())) {
				String billNo = commonServiceFeignApi.generatorBillNo("20").getData();
				invoiceDetailDO.setBarcode(billNo);
			} else {
				invoiceDetailDO.setBarcode(invoiceDetailDO.getBarcode());
			}
			invoiceDetailDO.setCreateUser(SMCApp.getLoginAuthDto().getUserNo());

			int insert = impInvoiceDetailMapper.insert(invoiceDetailDO);
			return insert == 1 ? ResultVo.success("新增成功") : ResultVo.failure("新增失败");
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> addInvoiceDetailPackData(ImpInvoiceDetailPackDO invoiceDetailPackDO) {
		try {
			if (StringUtils.isNotBlank(invoiceDetailPackDO.getCaseNo())
					&& invoiceDetailPackDO.getCaseNo().length() < 3) {
				return ResultVo.failure("箱号不能少于3位数字，可以为空，建议取3位");
			}
			ImpInvoiceMasterDO masterDO = this.getImpInvoiceMasterById(invoiceDetailPackDO.getInvoiceId());
			if (!(masterDO.getStatus() == 1 || masterDO.getStatus() == 2 || masterDO.getStatus() == 10)) {
				return ResultVo.failure("当前状态不能新增明细，请检查发票状态");
			}
			if (PublicUtil.isEmpty(invoiceDetailPackDO.getPoNo())
					|| PublicUtil.isEmpty(invoiceDetailPackDO.getPoItemNo())) {
				invoiceDetailPackDO.setPoNo(invoiceDetailPackDO.getOrderNo());
				invoiceDetailPackDO.setPoItemNo(invoiceDetailPackDO.getItemNo());
			}
			invoiceDetailPackDO.setSupplierCode(masterDO.getSupplierCode());
			invoiceDetailPackDO.setShipDate(masterDO.getShipDate());
			invoiceDetailPackDO.setStatus(1);
			if (PublicUtil.isEmpty(invoiceDetailPackDO.getBarcode())) {
				String billNo = commonServiceFeignApi.generatorBillNo("20").getData();
				invoiceDetailPackDO.setBarcode(billNo);
			} else {
				invoiceDetailPackDO.setBarcode(invoiceDetailPackDO.getBarcode());
			}
			invoiceDetailPackDO.setCreateUser(SMCApp.getLoginAuthDto().getUserNo());
			int insert = impInvoiceDetailPackMapper.insert(invoiceDetailPackDO);
			return insert == 1 ? ResultVo.success("新增成功") : ResultVo.failure("新增失败");
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public ResultVo<PageInfo<ImpInvoiceMasterDO>> listImpInvoiceMaster(ImpInvoiceMasterRequest request) {

		// LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = new
		// LambdaQueryWrapper<>();
		// if (request.getSendTimeEnd() != null) {
		// request.setSendTimeEnd(DateUtil.addDay(request.getSendTimeEnd(), 1));
		// }
		// if (request.getPrearriveDateEnd() != null) {
		// request.setPrearriveDateEnd(DateUtil.addDay(request.getPrearriveDateEnd(),
		// 1));
		// }
		// if (request.getUpdateTimeEnd() != null) {
		// request.setUpdateTimeEnd(DateUtil.addDay(request.getUpdateTimeEnd(),
		// 1));
		// }
		// if (request.getInvoiceDateEnd() != null) {
		// request.setInvoiceDateEnd(DateUtil.addDay(request.getInvoiceDateEnd(),
		// 1));
		// }
		// if (PublicUtil.isNotEmpty(request.getInvoiceType()) &&
		// request.getInvoiceType() > 0) {
		// queryWrapper.eq(ImpInvoiceMasterDO::getInvoiceType,
		// request.getInvoiceType());
		// }
		// if (PublicUtil.isEmpty(request.getInvoiceNo())) {
		// queryWrapper.eq(PublicUtil.isNotEmpty(request.getSupplierCode()),
		// ImpInvoiceMasterDO::getSupplierCode, request.getSupplierCode());
		// queryWrapper.eq(PublicUtil.isNotEmpty(request.getStatus()),
		// ImpInvoiceMasterDO::getStatus, request.getStatus());
		// queryWrapper.eq(PublicUtil.isNotEmpty(request.getId()),
		// ImpInvoiceMasterDO::getId, request.getId());
		// queryWrapper.between(PublicUtil.isNotEmpty(request.getSendTimeStart()),
		// ImpInvoiceMasterDO::getShipDate, request.getSendTimeStart(),
		// request.getSendTimeEnd());
		// queryWrapper.between(PublicUtil.isNotEmpty(request.getPrearriveDateStart()),
		// ImpInvoiceMasterDO::getPreArriveDate,
		// request.getPrearriveDateStart(), request.getPrearriveDateEnd());
		// queryWrapper.between(PublicUtil.isNotEmpty(request.getUpdateTimeStart()),
		// ImpInvoiceMasterDO::getUpdateTime, request.getUpdateTimeStart(),
		// request.getUpdateTimeEnd());
		// queryWrapper.between(PublicUtil.isNotEmpty(request.getInvoiceDateStart()),
		// ImpInvoiceMasterDO::getInvoiceDate, request.getInvoiceDateStart(),
		// request.getInvoiceDateEnd());
		// } else {
		// if (request.getInvoiceNo().contains("%")) {
		// queryWrapper.apply(" invoice_no like '" + request.getInvoiceNo() +
		// "'");
		// } else {
		// queryWrapper.eq(PublicUtil.isNotEmpty(request.getInvoiceNo()),
		// ImpInvoiceMasterDO::getInvoiceNo, request.getInvoiceNo());
		// }
		// }
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = this.getImpInvoiceMasterWrapper(request);
		List<ImpInvoiceMasterDO> list = impInvoiceMasterMapper
				.selectList(queryWrapper.orderByDesc(ImpInvoiceMasterDO::getId));
		PageInfo<ImpInvoiceMasterDO> pageInfo = PageInfo.of(list);
		PageInfo<ImpInvoiceMasterDO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, ImpInvoiceMasterDO.class);
		return ResultVo.success(voPageInfo);
	}

	@Override
	public ResultVo<BigDecimal> getImpInvoiceAmountTotal(ImpInvoiceMasterRequest request) {
		// LambdaQueryWrapper<ImpInvoiceMasterDO>
		// queryWrapper=this.getImpInvoiceMasterWrapper(request);

		QueryWrapper<ImpInvoiceMasterDO> queryWrapper = new QueryWrapper<>();
		if (request.getSendTimeEnd() != null) {
			request.setSendTimeEnd(DateUtil.addDay(request.getSendTimeEnd(), 1));
		}
		if (request.getPrearriveDateEnd() != null) {
			request.setPrearriveDateEnd(DateUtil.addDay(request.getPrearriveDateEnd(), 1));
		}
		if (request.getUpdateTimeEnd() != null) {
			request.setUpdateTimeEnd(DateUtil.addDay(request.getUpdateTimeEnd(), 1));
		}
		if (request.getInvoiceDateEnd() != null) {
			request.setInvoiceDateEnd(DateUtil.addDay(request.getInvoiceDateEnd(), 1));
		}
		if (PublicUtil.isEmpty(request.getInvoiceNo())) {
			queryWrapper.eq(PublicUtil.isNotEmpty(request.getSupplierCode()), "supplier_code",
					request.getSupplierCode());
			queryWrapper.eq(PublicUtil.isNotEmpty(request.getStatus()), "status", request.getStatus());
			queryWrapper.eq(PublicUtil.isNotEmpty(request.getId()), "id", request.getId());
			queryWrapper.between(PublicUtil.isNotEmpty(request.getSendTimeStart()), "ship_date",
					request.getSendTimeStart(), request.getSendTimeEnd());
			queryWrapper.between(PublicUtil.isNotEmpty(request.getPrearriveDateStart()), "prearrive_Date",
					request.getPrearriveDateStart(), request.getPrearriveDateEnd());
			queryWrapper.between(PublicUtil.isNotEmpty(request.getUpdateTimeStart()), "update_time",
					request.getUpdateTimeStart(), request.getUpdateTimeEnd());
			queryWrapper.between(PublicUtil.isNotEmpty(request.getInvoiceDateStart()), "invoice_date",
					request.getInvoiceDateStart(), request.getInvoiceDateEnd());
		} else {
			if (request.getInvoiceNo().contains("%")) {
				queryWrapper.apply(" invoice_no like '" + request.getInvoiceNo() + "'");
			} else {
				queryWrapper.eq(PublicUtil.isNotEmpty(request.getInvoiceNo()), "invoice_no", request.getInvoiceNo());
			}
		}

		queryWrapper.select("sum(amount) as amount");
		ImpInvoiceMasterDO masterDO = impInvoiceMasterMapper.selectOne(queryWrapper);
		if (masterDO == null || masterDO.getAmount() == null) {
			return ResultVo.success(BigDecimal.ZERO);
		} else {
			return ResultVo.success(masterDO.getAmount());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> cancelImpInvoiceDataById(Integer invoiceId) {
		try {
			LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(ImpInvoiceMasterDO::getId, invoiceId);
			ImpInvoiceMasterDO invoiceMasterDO = this.getImpInvoiceMasterByWrapper(queryWrapper);
			if (null == invoiceMasterDO) {
				return ResultVo.failure("无有效数据");
			}
			if (invoiceMasterDO.getStatus() == ImpInvoiceMasterStatusEnum.IMPED.getType()
					|| invoiceMasterDO.getStatus() == ImpInvoiceMasterStatusEnum.COSTED.getType()) {
				return ResultVo.failure("已发票入库或已入成本，不可取消");
			}
			// LambdaQueryWrapper<ImpInvoiceDetailPackDO> queryDetailWrapper =
			// new LambdaQueryWrapper<>();
			// queryDetailWrapper.eq(ImpInvoiceDetailPackDO::getInvoiceId,
			// invoiceId);
			// List<ImpInvoiceDetailPackDO> impInvoiceDetailDOS =
			// this.impInvoiceDetailPackMapper.selectList(queryDetailWrapper);
			int result = 0;
			UpdateWrapper<ImpInvoiceMasterDO> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("id", invoiceId);
			ImpInvoiceMasterDO master = new ImpInvoiceMasterDO();
			master.setStatus(9);// 取消
			result = impInvoiceMasterMapper.update(master, updateWrapper);
			UpdateWrapper<ImpInvoiceDetailPackDO> updateDetailWrapper = new UpdateWrapper<>();
			updateDetailWrapper.eq("invoice_Id", invoiceId);
			ImpInvoiceDetailPackDO detail = new ImpInvoiceDetailPackDO();
			detail.setStatus(9);// 取消
			detail.setUpdateUser(SMCApp.getLoginAuthDto().getUserNo());
			result = impInvoiceDetailPackMapper.update(detail, updateDetailWrapper);
			return ResultVo.success("", "取消成功:" + master.getInvoiceNo());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> updateImpInvoicePreArriveDate(ImpInvoiceArriveDateRequest request) {
		try {
			if (PublicUtil.isEmpty(request.getPreArriveDate())) {
				return ResultVo.failure("预计到达日期必须填写！");
			}
			LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(ImpInvoiceMasterDO::getId, request.getId());
			ImpInvoiceMasterDO invoiceMasterDO = this.getImpInvoiceMasterByWrapper(queryWrapper);
			if (PublicUtil.isEmpty(invoiceMasterDO)) {
				return ResultVo.failure("无有效数据");
			}
			if (1 != invoiceMasterDO.getStatus()) {
				return ResultVo.failure("不可登记,请检查发票号状态");
			}
			ImpInvoiceMasterDO master = new ImpInvoiceMasterDO();
			master.setPreArriveDate(request.getPreArriveDate());// 预计达到日期
			master.setReceiveDate(request.getArriveDate()); // 关务发出日期
			master.setRemark(request.getRemark());// 备注
			// 如果实际到达时间不为空且状态为预计到货，改状态为已到货待入库
			if (PublicUtil.isNotEmpty(request.getArriveDate()) && request.getStatus() == 1) {
				invoiceMasterDO.setStatus(2);
				master.setStatus(2);
			}

			// 预计到达日期改变则更新orderstate和北京的updateInvoice
			if (invoiceMasterDO.getPreArriveDate() == null
					|| invoiceMasterDO.getPreArriveDate().compareTo(request.getPreArriveDate()) != 0
					|| (invoiceMasterDO.getReceiveDate() == null && PublicUtil.isNotEmpty(request.getArriveDate()))
					|| invoiceMasterDO.getReceiveDate().compareTo(request.getArriveDate()) != 0) {
				LambdaQueryWrapper<ImpInvoiceDetailDO> detailquery = new LambdaQueryWrapper<>();
				detailquery.eq(ImpInvoiceDetailDO::getInvoiceId, request.getId());
				List<ImpInvoiceDetailDO> detaillist = impInvoiceDetailMapper.selectList(detailquery);
				if (detaillist.isEmpty()) {
					return ResultVo.failure("没有明细数据" + invoiceMasterDO.getInvoiceNo());
				}
				invoiceMasterDO.setPreArriveDate(request.getPreArriveDate());

				// 1.更新orderstate和北京接口updateInvoice
				// boolean updSuccess = UpdOrderStateAndInvoice(invoiceMasterDO,
				// detaillist);
				// if (!updSuccess) {
				// return ResultVo.failure("更新订单状态或北京发票接口错误" +
				// invoiceMasterDO.getInvoiceNo());
				// }

				// 2.修改预计到货登记
				UpdateWrapper<ImpInvoiceMasterDO> updateWrapper = new UpdateWrapper<>();
				updateWrapper.eq("id", request.getId());
				int result = impInvoiceMasterMapper.update(master, updateWrapper);
				if (result != 1) {
					return ResultVo.failure("登记错误" + invoiceMasterDO.getInvoiceNo());
				}

			}
			return ResultVo.success("", "登记日期成功:" + invoiceMasterDO.getInvoiceNo());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * 1更新orderstate；2北京接口updateInvoice调用
	 *
	 * @return //
	 */
	// public boolean UpdOrderStateAndInvoice(ImpInvoiceMasterDO master,
	// List<ImpInvoiceDetailDO> detaillist) {
	// List<PurchaseReplyInfo> purchaseReplyInfoList = new ArrayList<>();
	// for (ImpInvoiceDetailDO info : detaillist) {
	//
	// //1更新预计到货状态
	// OrderStateVO orderStateVO = new OrderStateVO();
	// orderStateVO.setProvider(info.getSupplierCode());
	// orderStateVO.setSupplierCode(info.getSupplierCode());
	// orderStateVO.setStateDate(info.getShipDate());
	// orderStateVO.setFirstDate(info.getShipDate());
	// orderStateVO.setMaxDate(info.getShipDate());
	// orderStateVO.setMinDate(info.getShipDate());
	// orderStateVO.setOrderNo(info.getOrderNo());
	// orderStateVO.setEsArrivalDate(master.getPreArriveDate());
	//
	// if (info.getShipDate() != null) {
	// orderStateVO.setStateDes(info.getSupplierCode() +
	// DateUtil.dateToDateString(info.getShipDate()) + "已发出"
	// + info.getInvoiceNo() + ",预计达到日期" + master.getPreArriveDate() == null ?
	// "" : DateUtil.dateToDateString(master.getPreArriveDate()));
	// }
	// orderStateVO.setPoInvoiceNo(info.getInvoiceNo());
	// orderStateVO.setOrderType(OrderStateServiceImpl.getOrderType(info.getOrderNo().substring(0,
	// 1)));
	// orderStateVO.setStateCode(31);
	//
	// ResultVo<String> stringResultVo =
	// orderStateService.addOrderState(orderStateVO);
	// if (!stringResultVo.isSuccess()) {
	// log.error("发票数据推送OrderState失败");
	// return false;
	// }
	//
	// //修改北京updateInvoice接口数据
	// PurchaseReplyInfo purchaseReplyInfo = new PurchaseReplyInfo();
	// purchaseReplyInfo.setPono(info.getPoNo());
	// purchaseReplyInfo.setLineitem(info.getPoItemNo());
	// purchaseReplyInfo.setTranstype(info.getShipMethod());
	// purchaseReplyInfo.setImpdate(master.getPreArriveDate());
	// purchaseReplyInfo.setInvoiceno(master.getInvoiceNo()); // 原始发票号
	// purchaseReplyInfo.setModelno(info.getModelNo());
	// purchaseReplyInfo.setSupplierid(info.getSupplierCode());
	// //发运输中数量，发票入库时发收货数量
	//// if (master.getStatus() == 2) {//到货
	//// purchaseReplyInfo.setQtyreceive(info.getQuantity()); // 到货数量
	//// } else {
	// purchaseReplyInfo.setQtyTrans(info.getQuantity()); // 运输中数量
	// //}
	// purchaseReplyInfo.setInvoiceid(info.getInvoiceId());
	// purchaseReplyInfoList.add(purchaseReplyInfo);
	// log.info("发票运输在途：" + info.getPoNo() + "-" + info.getPoItemNo());
	// log.info(JSON.toJSONString(purchaseReplyInfo));
	// }
	//
	// //2.预计到货登记导入数据到北京接口updateInvoice
	// if (!purchaseReplyInfoList.isEmpty()) {
	// List<List<PurchaseReplyInfo>> list =
	// ListUtils.partition(purchaseReplyInfoList, 50); // 每50个数据一组
	// for (List<PurchaseReplyInfo> infoList : list) {
	// log.info("updateInvoice params = {}", JSON.toJSONString(infoList));
	// CommonResult<String> updateInvoice =
	// requestPurchaseFeignApi.updateInvoice(infoList);
	// if (updateInvoice.isSuccess()) {
	// continue;
	// }
	// //log.info("updateInvoice params = {}", JSON.toJSONString(infoList));
	// log.error("登记预计到货日期导入失败: {}", updateInvoice.getMessage());
	// throw new BusinessException("requestPurchaseFeignApi.updateInvoice fail,
	// " + updateInvoice.getMessage());
	// }
	// }
	// return true;
	// }

	/**
	 * 明细项完成录入
	 *
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> finishImpInvoiceDeatailAdd(Integer invoiceId) {
		// 先查询master
		try {
			LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(ImpInvoiceMasterDO::getId, invoiceId).eq(ImpInvoiceMasterDO::getStatus, 10);
			ImpInvoiceMasterDO invoiceMasterDO = this.getImpInvoiceMasterByWrapper(queryWrapper);
			if (invoiceMasterDO == null) {
				return ResultVo.failure("没有查到编辑中的发票，请检查状态");
			}
			// 再查询Detail
			LambdaQueryWrapper<ImpInvoiceDetailDO> detailquery = new LambdaQueryWrapper<>();
			detailquery.eq(ImpInvoiceDetailDO::getInvoiceId, invoiceId);
			List<ImpInvoiceDetailDO> detailList = this.listImpInvoiceDetailByWrapper(detailquery);
			if (detailList.isEmpty()) {
				return ResultVo.failure("没有明细数据");
			}
			// 1.合计总金额和数量
			boolean updaCount = updateImpInvoiceQty(invoiceMasterDO.getId());
			if (!updaCount) {
				return ResultVo.failure("合计总金额和数量错误");
			}

			// 检查发票差异
			this.checkImpInvoiceError(invoiceMasterDO.getId());

			// 3.修改状态
			UpdateWrapper<ImpInvoiceMasterDO> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("id", invoiceId);
			ImpInvoiceMasterDO master = new ImpInvoiceMasterDO();
			master.setStatus(2);// 默认改成已到货待入库
			int result = impInvoiceMasterMapper.update(master, updateWrapper);
			if (result != 1) {
				return ResultVo.failure("更新状态错误");
			}

			// 2.更新orderstate和北京接口updateInvoice
			// boolean updSuccess = UpdOrderStateAndInvoice(invoiceMasterDO,
			// detaillist);
			// if (!updSuccess) {
			// return ResultVo.failure("更新订单状态或北京发票接口错误");
			// }

			List<ImpInvoiceDetailVO> listItemVO = BeanCopyUtil.copyList(detailList, ImpInvoiceDetailVO.class);

			// 2.更新orderstate和北京接口updateInvoice

			// 2.更新采购预到货状态
			toUpdatePOTransStatus(listItemVO, invoiceMasterDO);

			// 3.更新订单状态 bug8628
			toUpdateOrderState(invoiceMasterDO, listItemVO);
			// end bug8628

			return ResultVo.success("已完成录入成功！");
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * 收货确认 //把发票从预计到货改成已收货待入库 //调用关务系统回调接口更新已收货
	 * <p>
	 * //触发自动入库调用入库方法--后期加
	 *
	 * @param dto
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> receiveGoods(ImpInvoiceReceiveDTO dto) {
		try {
			log.info("receiveGood:" + JSON.toJSONString(dto));
			long start = System.currentTimeMillis();
			if (PublicUtil.isEmpty(dto.getReceiveTime())) {
				return ResultVo.failure("签收时间不能为空" + dto.getInvoiceNo());
			}
			ImpInvoiceMasterDO invoiceMasterDO;
			if (dto.getInvoiceId() != null && dto.getInvoiceId() != 0) {
				invoiceMasterDO = this.getImpInvoiceMasterById(dto.getInvoiceId());
			} else {
				invoiceMasterDO = this.getLastImpInvoiceMaster(dto.getInvoiceNo(), dto.getSupplierNo());
			}
			if (invoiceMasterDO == null) {
				return ResultVo.failure(dto.getInvoiceNo() + "无发票数据无法签收");
			}
			if (invoiceMasterDO.getStatus() != 3 && invoiceMasterDO.getStatus() != 6) {
				// 20230406 发送异步消息重新再检查一次发票是否入库完成和补充数据
				sendImpInvocieConfirmProcessMsg(invoiceMasterDO);

				return ResultVo.failure(dto.getInvoiceNo() + "还没发票入库，不可以收货,重试一次");
			}

			ImpInvoiceMasterDO updImpInvoiceMasterDo = new ImpInvoiceMasterDO();
			updImpInvoiceMasterDo.setId(invoiceMasterDO.getId());
			updImpInvoiceMasterDo.setUpdateTime(new Date());
			updImpInvoiceMasterDo.setArrivedWarehouseCode(dto.getReceiveWarehouseCode());
			updImpInvoiceMasterDo.setArriveDate(dto.getReceiveTime());
			updImpInvoiceMasterDo.setUpdateUser("wms-rcv");
			impInvoiceMasterMapper.updateById(updImpInvoiceMasterDo);

			long end = System.currentTimeMillis();
			long time = (end - start) / 1000;
			log.info("receiveGoods的Master:" + Long.toString(time));

			if (StringUtils.isNotBlank(invoiceMasterDO.getCInvoiceNo())) {
				// 发送异步处理关务调用
				ImpInvoiceProcessDTO processDTO = new ImpInvoiceProcessDTO();
				processDTO.setInvoiceNo(invoiceMasterDO.getCInvoiceNo());
				processDTO.setReceiveTime(dto.getReceiveTime());
				processDTO.setProcessType(7);
				this.sendInvoiceProcessMsgToMQ(processDTO);

			}
			// 已发票入库待转成本还需要更新发票入库表单签收时间
			if (invoiceMasterDO.getStatus() == 3) {
				// selectOne底层代码逻辑是调用的selectList,语句加top 1，查一条数据即返回 begin bug8732
				// LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapperPOInvoice =
				// new LambdaQueryWrapper<>();
				// queryWrapperPOInvoice.select(OpsPoInvoiceDO::getId,
				// OpsPoInvoiceDO::getInvoiceNo, OpsPoInvoiceDO::getStatus);
				// queryWrapperPOInvoice.eq(OpsPoInvoiceDO::getInvoiceId,
				// invoiceMasterDO.getId());
				QueryWrapper<OpsPoInvoiceDO> queryWrapperPOInvoice = new QueryWrapper<>();
				queryWrapperPOInvoice.select(" top 1 id", "invoice_id", "status");
				queryWrapperPOInvoice.eq("invoice_id", invoiceMasterDO.getId());
				OpsPoInvoiceDO poInvoiceDO = poInvoiceMasterMapper.selectOne(queryWrapperPOInvoice);
				// begin bug8732
                if(Objects.isNull(poInvoiceDO)){
                    List<MergeInvoiceDto> invoiceSplitList = mergeSmcCodeDao.getInvoiceSplitListById(invoiceMasterDO.getId());
                    if(!org.springframework.util.CollectionUtils.isEmpty(invoiceSplitList)){
                        Long mergeInvoiceId = invoiceSplitList.get(0).getMergeInvoiceId();
                        QueryWrapper<OpsPoInvoiceDO> queryWrapperPOInvoice1 = new QueryWrapper<>();
                        queryWrapperPOInvoice1.select(" top 1 id", "invoice_id", "status");
                        queryWrapperPOInvoice1.eq("invoice_id", mergeInvoiceId);
                        poInvoiceDO = poInvoiceMasterMapper.selectOne(queryWrapperPOInvoice1);
                    }
                }
				if (poInvoiceDO != null && poInvoiceDO.getStatus() == 1) {
					OpsPoInvoiceDO updOpsPoInvoiceDo = new OpsPoInvoiceDO();
					updOpsPoInvoiceDo.setId(poInvoiceDO.getId());
					updOpsPoInvoiceDo.setReceiveTime(dto.getReceiveTime());

					updOpsPoInvoiceDo.setArrivedWarehouseCode(dto.getReceiveWarehouseCode());
					updOpsPoInvoiceDo.setUpdateTime(new Date());
					updOpsPoInvoiceDo.setUpdateUser("rcv");
					updOpsPoInvoiceDo.setStatus(2);
					poInvoiceMasterMapper.updateById(updOpsPoInvoiceDo);

					end = System.currentTimeMillis();
					time = (end - start) / 1000;
					log.info("receiveGoods的PO:" + Long.toString(time));

					// 发送发票处理消息
					ImpInvoiceProcessDTO processDTO = new ImpInvoiceProcessDTO();
					processDTO.setInvoiceId(invoiceMasterDO.getId());
					processDTO.setProcessType(2);
					this.sendInvoiceProcessMsgToMQ(processDTO);
					// confirmPOInvoiceDetail(invoiceMasterDO.getId(), 2);
				} else {
					log.error("更新成本签收时间失败,成本数据不存在或状态不对" + dto.getInvoiceNo());
					// return ResultVo.failure("更新成本签收失败,成本数据不存在或状态不对！" +
					// invoiceMasterDO.getInvoiceNo());
					if (poInvoiceDO == null) {
						// 20230406 发送异步消息重新再检查一次发票是否入库完成和补充数据
						sendImpInvocieConfirmProcessMsg(invoiceMasterDO);
					}
				}
			}
			end = System.currentTimeMillis();
			time = (end - start) / 1000;
			log.info("receiveGoods用时：" + invoiceMasterDO.getInvoiceNo() + ":" + Long.toString(time));
			return ResultVo.success("收货成功" + invoiceMasterDO.getInvoiceNo());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * 异步处理物流签收关务调用
	 *
	 * @return
	 */
	@Override
	public ResultVo<String> toupdateWarehousingByGW(ImpInvoiceProcessDTO dto) {
		Map<String, String> param = new HashMap<>(4);
		param.put("plantMark", "AM");
		param.put("invNo", dto.getInvoiceNo());
		param.put("receiveTime", DateUtil.dateToDateTimeString(dto.getReceiveTime()));
		param.put("userName", "wms-rcv");
		OrderReplyResponse response = buService.updateWarehousingInfo(param);

		if (response.getCode() != 0) {
			log.error("调用关务收货接口失败" + response.getMessage());
			return ResultVo.failure(response.getMessage());
		}
		return ResultVo.success();
	}

    /**
     * bugid 19186 合并smccode
     * 合并smccode 生成成本明细
     * @param mergeInvoiceDtos
     * @param invoiceId
     * @param type
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> megreInvoicePoInvDetail(List<MergeInvoiceDto> mergeInvoiceDtos,Long invoiceId, Integer type){
       for(MergeInvoiceDto obj :mergeInvoiceDtos ){
           ImpInvoiceMasterDO invoiceMasterDO = this.getImpInvoiceMasterById(obj.getSplitInvoiceId());
           if (invoiceMasterDO == null
                   || !((invoiceMasterDO.getGwStateCode() != null && "1".equals(invoiceMasterDO.getGwStateCode()))
                   || StringUtils.isBlank(invoiceMasterDO.getCInvoiceNo()))) {
               return ResultVo.failure("关务发票未确认R或者状态不对！");
           }
       }

        // 是否启用发票成本与业务入库差异检查
        ResultVo<DataTypeVO> checkSwitch = dictCommonService.getDataTypeCodesInfo("9002", "12");
        if (!checkSwitch.isSuccess()) {
            return ResultVo.failure("差异检查开关获取失败");
        }
        if ("1".equals(checkSwitch.getData().getExtNote1())) {
            // 发票明细型号数量的验证 begin bug8977
            for(MergeInvoiceDto obj :mergeInvoiceDtos ){
                List<String> diffModellist = impInvoiceDetailMapper.getPackAndDetailDiffModelNo(obj.getSplitInvoiceId());
                if (CollectionUtils.isNotEmpty(diffModellist)) {
                    return ResultVo.failure("发票发包明细和发票明细存在差异，请在发票数据管理界面查看型号及数量差异:" + String.join(",", diffModellist));
                }
            }
        }
        LambdaQueryWrapper<ImpInvoiceDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ImpInvoiceDetailDO::getInvoiceId, mergeInvoiceDtos.stream()
                .map(MergeInvoiceDto::getSplitInvoiceId)
                .collect(Collectors.toList()));
        List<ImpInvoiceDetailDO> details = this.listImpInvoiceDetailByWrapper(queryWrapper);

        // 发票明细写入到ops_po_invoice_detail
        Date impdate = DateUtil.getCurrentDate();
        String optUser = "rcv";
        if (type == 1) {
            optUser = SMCApp.getLoginAuthDto().getUserNo();
        }
        // 删除明细项
        if (CollectionUtils.isNotEmpty(details)) {
            // <!--add by WuWeiDong 20230829 bug 11912
            // ops_po_invoice_detail添加软删除标识 -->
            LambdaUpdateWrapper<PoInvoiceDetailDO> deletWrapper = new LambdaUpdateWrapper<>();
            deletWrapper.in(PoInvoiceDetailDO::getInvoiceId, mergeInvoiceDtos.stream()
                            .map(MergeInvoiceDto::getSplitInvoiceId)
                            .collect(Collectors.toList())).set(PoInvoiceDetailDO::getStatus, '9') // 取消删除
                    .set(PoInvoiceDetailDO::getUpdateUser, SMCApp.getLoginAuthDtoForSysUser().getUserNo())
                    .set(PoInvoiceDetailDO::getUpdateTime, DateUtil.getNow());
            poInvoiceDetailMapper.update(null, deletWrapper);
            // 写入原始发票
            // 录入ops_po_invoice_detail
            boolean istrue = addPoInvoiceDetail(impdate, details, optUser,mergeInvoiceDtos.get(0));
            if (istrue) {
                for(MergeInvoiceDto obj :mergeInvoiceDtos ) {
                    ImpInvoiceMasterDO invoiceMasterDO = this.getImpInvoiceMasterById(obj.getSplitInvoiceId());
                    if (invoiceMasterDO.getGwStateCode() != null
                            && "1".equals(invoiceMasterDO.getGwStateCode())) {
                        ImpInvoiceMasterDO updImpMasterDO = new ImpInvoiceMasterDO();
                        updImpMasterDO.setId(invoiceId);
                        updImpMasterDO.setGwStateCode("6");
                        impInvoiceMasterMapper.updateById(updImpMasterDO);
                    }
                }
            }
        }
        return ResultVo.success("导入明细成功" + invoiceId);
    }

	/*
	 * 生成成本明细
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> confirmPOInvoiceDetail(Long invoiceId, Integer type) {
		String lockKey = Constants.REDIS_KEY_CONFIRM_POINVOICE_LOCK + invoiceId;
		if (!redissonUtil.tryLock(lockKey, 1, 360, TimeUnit.SECONDS)) {
			log.info(invoiceId + "导入成本明细重复 " + lockKey);
			return ResultVo.failure("导入成本明细重复");
		}
		try {
            List<MergeInvoiceDto> mergeInvoiceDtos = mergeSmcCodeDao.getInvoiceSplitListByMegreId(invoiceId);
            if(CollectionUtils.isNotEmpty(mergeInvoiceDtos)){
                return megreInvoicePoInvDetail(mergeInvoiceDtos,invoiceId,type);
            }

            ImpInvoiceMasterDO invoiceMasterDO = this.getImpInvoiceMasterById(invoiceId);
			if (invoiceMasterDO == null
					|| !((invoiceMasterDO.getGwStateCode() != null && "1".equals(invoiceMasterDO.getGwStateCode()))
							|| StringUtils.isBlank(invoiceMasterDO.getCInvoiceNo()))) {
				return ResultVo.failure("关务发票未确认R或者状态不对！");
			}

			// 是否启用发票成本与业务入库差异检查
			ResultVo<DataTypeVO> checkSwitch = dictCommonService.getDataTypeCodesInfo("9002", "12");
			if (!checkSwitch.isSuccess()) {
				return ResultVo.failure("差异检查开关获取失败");
			}
			if ("1".equals(checkSwitch.getData().getExtNote1())) {
				// 发票明细型号数量的验证 begin bug8977
				List<String> diffModellist = impInvoiceDetailMapper.getPackAndDetailDiffModelNo(invoiceId);
				if (CollectionUtils.isNotEmpty(diffModellist)) {
					return ResultVo.failure("发票发包明细和发票明细存在差异，请在发票数据管理界面查看型号及数量差异:" + String.join(",", diffModellist));
				} // end bug8977
			}
			LambdaQueryWrapper<ImpInvoiceDetailDO> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(ImpInvoiceDetailDO::getInvoiceId, invoiceId);
			List<ImpInvoiceDetailDO> details = this.listImpInvoiceDetailByWrapper(queryWrapper);

			// 发票明细写入到ops_po_invoice_detail
			Date impdate = DateUtil.getCurrentDate();
			String optUser = "rcv";
			if (type == 1) {
				optUser = SMCApp.getLoginAuthDto().getUserNo();
			}

			// 删除明细项
			if (CollectionUtils.isNotEmpty(details)) {
				// <!--add by WuWeiDong 20230829 bug 11912
				// ops_po_invoice_detail添加软删除标识 -->
				LambdaUpdateWrapper<PoInvoiceDetailDO> deletWrapper = new LambdaUpdateWrapper<>();
				deletWrapper.eq(PoInvoiceDetailDO::getInvoiceId, invoiceId).set(PoInvoiceDetailDO::getStatus, '9') // 取消删除
						.set(PoInvoiceDetailDO::getUpdateUser, SMCApp.getLoginAuthDtoForSysUser().getUserNo())
						.set(PoInvoiceDetailDO::getUpdateTime, DateUtil.getNow());
				;
				poInvoiceDetailMapper.update(null, deletWrapper);

				// 录入ops_po_invoice_detail
				boolean istrue = addPoInvoiceDetail(impdate, details, optUser,null);
				if (istrue && invoiceMasterDO.getGwStateCode() != null
						&& "1".equals(invoiceMasterDO.getGwStateCode())) {
					ImpInvoiceMasterDO updImpMasterDO = new ImpInvoiceMasterDO();
					updImpMasterDO.setId(invoiceId);
					updImpMasterDO.setGwStateCode("6");
					impInvoiceMasterMapper.updateById(updImpMasterDO);
				}
			}
			// 异步触发计算金额
			// ImpInvoiceProcessDTO processDTO = new ImpInvoiceProcessDTO();
			// processDTO.setInvoiceId(invoiceId);
			// processDTO.setProcessType(5);
			// this.sendInvoiceProcessMsgToMQ(processDTO);
			// 异步生成组换
			// salesImportService.asyncToCreateStockAssembly(invoiceId,
			// invoiceMasterDO.getReceiveWarehouseCode());
			return ResultVo.success("导入明细成功" + invoiceId);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		} finally {
			redissonUtil.unlock(lockKey);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> clearPOInvoiceDetail(Long invoiceId) {
		try {
			LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(OpsPoInvoiceDO::getInvoiceId, invoiceId);
			OpsPoInvoiceDO poInvoiceDO = poInvoiceMasterMapper.selectOne(queryWrapper);
			if (poInvoiceDO == null) {
				return ResultVo.failure("发票不存在" + invoiceId);
			}
			if (poInvoiceDO.getStatus() == 3) {
				return ResultVo.failure("已成本结算，不能再清除" + invoiceId);
			}

			// 只更新关务6的变成1
			LambdaUpdateWrapper<ImpInvoiceMasterDO> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.eq(ImpInvoiceMasterDO::getId, invoiceId);
			updateWrapper.eq(ImpInvoiceMasterDO::getGwStateCode, "6");
			ImpInvoiceMasterDO impmasterDO = new ImpInvoiceMasterDO();
			impmasterDO.setGwStateCode("1");// 取消
			impInvoiceMasterMapper.update(impmasterDO, updateWrapper);

			LambdaUpdateWrapper<OpsPoInvoiceDO> updOpsInvoiceWrapper = new LambdaUpdateWrapper<>();
			updOpsInvoiceWrapper.eq(OpsPoInvoiceDO::getInvoiceId, invoiceId);
			OpsPoInvoiceDO updOpsInvoiceMasterDO = new OpsPoInvoiceDO();
			updOpsInvoiceMasterDO.setAmount(BigDecimal.ZERO);
			updOpsInvoiceMasterDO.setAmountRmb(BigDecimal.ZERO);
			poInvoiceMasterMapper.update(updOpsInvoiceMasterDO, updOpsInvoiceWrapper);

			// <!--add by WuWeiDong 20230829 bug 11912
			// ops_po_invoice_detail添加软删除标识 -->
			LambdaUpdateWrapper<PoInvoiceDetailDO> deletWrapper = new LambdaUpdateWrapper<>();
			deletWrapper.eq(PoInvoiceDetailDO::getInvoiceId, invoiceId).set(PoInvoiceDetailDO::getStatus, '9') // 取消删除
					.set(PoInvoiceDetailDO::getUpdateUser, SMCApp.getLoginAuthDtoForSysUser().getUserNo())
					.set(PoInvoiceDetailDO::getUpdateTime, DateUtil.getNow());
			poInvoiceDetailMapper.update(null, deletWrapper);

			return ResultVo.success("", "已清除成本明细" + invoiceId);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public ResultVo<List<InvoiceBalaceDTO>> getSalesInvoiceBalaceData(Date fromDate, Date toDate) {
		List<InvoiceBalaceDTO> dtoList = salesInvoiceMapper.getBalaceData(fromDate, toDate);
		return ResultVo.success(dtoList);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ResultVo<Integer> checkImpInvoiceError(Long invoiceId) {
		String lockKey = Constants.REDIS_KEY_CHECK_IMPERROR_LOCK + invoiceId;
		if (!redissonUtil.tryLock(lockKey, 1, 50, TimeUnit.SECONDS)) {
			log.info(invoiceId + "重复入库差异检测 " + lockKey);
			return ResultVo.failure("入库差异检测不能重复点击");
		}
		try {
			LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.select(ImpInvoiceMasterDO::getId, ImpInvoiceMasterDO::getStatus);
			queryWrapper.eq(ImpInvoiceMasterDO::getId, invoiceId);
			ImpInvoiceMasterDO masterDO = this.getImpInvoiceMasterByWrapper(queryWrapper);

			Map<String, Object> params;
			Object countObj;
			if (masterDO.getStatus() == 1 || masterDO.getStatus() == 2 || masterDO.getStatus() == 10) {
				Integer count = 0;
				params = new HashMap<>(4);
				params.put("invoiceId", invoiceId);
				params.put("Count", 0);
				impInvoiceErrorMapper.checkImpInvoiceError(params);
				countObj = params.get("Count");
				if (countObj != null) {
					count = Integer.valueOf(countObj.toString());
				}
				return ResultVo.success(count);
				// Integer integer = impInvoiceCheck(String.valueOf(invoiceId));
				// if (integer != null) {
				// return ResultVo.success(integer);
				// }
			}
			return ResultVo.failure("状态错误，请检查");
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		} finally {
			redissonUtil.unlock(lockKey);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> updateImpInvoiceIgnoreError(ImpInvoiceErrorDTO dto) {
		try {
			LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
			if (dto.getIds().length <= 0) {
				QueryWrapper<ImpInvoiceErrorDO> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("invoice_id", dto.getInvoiceId());
				ImpInvoiceErrorDO invoiceErrorDO = new ImpInvoiceErrorDO();
				invoiceErrorDO.setIgnoreReason(dto.getIgnoreReason());
				invoiceErrorDO.setIgnoreTime(DateUtil.getNow());
				invoiceErrorDO.setIgnoreError(true);
				invoiceErrorDO.setIgnorePsn(userDTO.getUserNo());
				int update = impInvoiceErrorMapper.update(invoiceErrorDO, queryWrapper);
				return ResultVo.success("成功忽略" + update + "条数据");

			} else {
				for (Long id : dto.getIds()) {
					ImpInvoiceErrorDO invoiceErrorDO = new ImpInvoiceErrorDO();
					invoiceErrorDO.setId(id);
					invoiceErrorDO.setIgnoreReason(dto.getIgnoreReason());
					invoiceErrorDO.setIgnoreTime(DateUtil.getNow());
					invoiceErrorDO.setIgnoreError(true);
					invoiceErrorDO.setIgnorePsn(userDTO.getUserNo());
					impInvoiceErrorMapper.updateById(invoiceErrorDO);
				}
				return ResultVo.success("成功忽略" + dto.getIds().length + "条数据");
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public ResultVo<PageInfo<ImpInvoiceDetailPackDO>> listImpInvoiceDetailPackByInvoiceId(
			ImpInvoiceDetailRequest request) {
		LambdaQueryWrapper<ImpInvoiceDetailPackDO> queryWrapper = getImpInvoiceDetailPackWrapper(request);

		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		List<ImpInvoiceDetailPackDO> list = this.impInvoiceDetailPackMapper.selectList(queryWrapper);
		PageInfo<ImpInvoiceDetailPackDO> pageInfo = BeanCopyUtil.pageDto2Vo(new PageInfo<>(list),
				ImpInvoiceDetailPackDO.class);
		return ResultVo.success(pageInfo);
	}

	private LambdaQueryWrapper<ImpInvoiceDetailPackDO> getImpInvoiceDetailPackWrapper(ImpInvoiceDetailRequest request) {
		LambdaQueryWrapper<ImpInvoiceDetailPackDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(PublicUtil.isNotEmpty(request.getInvoiceId()), ImpInvoiceDetailPackDO::getInvoiceId,
				request.getInvoiceId());
		queryWrapper.eq(PublicUtil.isNotEmpty(request.getInvoiceNo()), ImpInvoiceDetailPackDO::getInvoiceNo,
				request.getInvoiceNo());
		queryWrapper.eq(PublicUtil.isNotEmpty(request.getBarcode()), ImpInvoiceDetailPackDO::getBarcode,
				request.getBarcode());
		queryWrapper.eq(PublicUtil.isNotEmpty(request.getOverseaInvoiceNo()),
				ImpInvoiceDetailPackDO::getOverseaInvoiceNo, request.getOverseaInvoiceNo());

		if (PublicUtil.isNotEmpty(request.getModelNo())) {
			if (request.getModelNo().contains("%")) {
				queryWrapper.apply(" and model_no like '" + request.getModelNo() + "'");
			} else {
				queryWrapper.eq(ImpInvoiceDetailPackDO::getModelNo, request.getModelNo());
			}
		}
		if (PublicUtil.isNotEmpty(request.getOrderNo())) {
			if (request.getModelNo().contains("%")) {
				queryWrapper.apply(" and full_order_no '" + request.getOrderNo() + "'");
			} else {
				queryWrapper.eq(ImpInvoiceDetailPackDO::getFullOrderNo, request.getOrderNo());
			}
		}
		return queryWrapper;
	}

	// <!--add by WuWeiDong 20230202 bug 9450 导出发票主要数据 -->
	@Override
	public void exportImpInvoiceMaster(ImpInvoiceMasterRequest request) {
		try {
			LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = this.getImpInvoiceMasterWrapper(request);
			List<ImpInvoiceMasterDO> list = impInvoiceMasterMapper
					.selectList(queryWrapper.orderByDesc(ImpInvoiceMasterDO::getId));

			if (PublicUtil.isEmpty(list)) {
				return;
			}

			ExcelHelper excel = new ExcelHelper();
			excel.openSheet(0);
			List<String> colNames = Arrays.asList("发票ID", "发票号", "发票类型", "状态", "供应商", "发票日期", "发出日期", "预计到达", "到港时间",
					"报关日期", "到厂日期", "物流签收时间", "关务发票号", "收货仓库", "原发票金额", "汇率", "原发票金额rmb", "运保费", "关税", "消费税", "增值税",
					"币种", "其他费用", "发票订单数", "总数量", "总箱数", "总重量", "运输方式", "数据类型", "数据导入时间", "更新时间", "发票入库时间", "备注");
			Integer row = 0;
			Integer cel = 0;
			Workbook wb = excel.getWorkBook();
			org.apache.poi.ss.usermodel.Font font = wb.createFont();
			font.setFontName("微软雅黑");
			font.setBold(true);
			font.setFontHeightInPoints((short) 11);
			for (Integer i = 0; i < colNames.size(); i++) {
				excel.setCellValue(row, i, colNames.get(i));
				excel.setCellStyle(row, i, font, new Short[] { 1, 1, 1, 1 });
			}

			// 状态
			Map<String, String> statusMap = dictCommonService.getDataCodesMap("2040").getData();
			// 数据类型
			Map<String, String> dataTypeMap = dictCommonService.getDataCodesMap("2044").getData();
			// 运输方式
			Map<String, String> transTypeMap = dictCommonService.getDataCodesMap("'2041'").getData();
			// 发票类别
			Map<String, String> invoiceTypeMap = dictCommonService.getDataCodesMap("2064").getData();
			// MASTER 仓库
			Map<String, String> warehouseMap = dictCommonService.getWarehouseMap("MASTER").getData();

			row++;
			for (ImpInvoiceMasterDO invoiceMasterDO : list) {
				cel = 0;
				excel.setCellValue(row, cel++, invoiceMasterDO.getId());
				excel.setCellValue(row, cel++, invoiceMasterDO.getInvoiceNo());

				String tmp = PublicUtil.isEmpty(invoiceMasterDO.getInvoiceType()) ? ""
						: invoiceMasterDO.getInvoiceType().toString();
				excel.setCellValue(row, cel++, invoiceTypeMap.get(tmp));

				tmp = PublicUtil.isEmpty(invoiceMasterDO.getStatus()) ? "" : invoiceMasterDO.getStatus().toString();
				excel.setCellValue(row, cel++, statusMap.get(tmp));

				excel.setCellValue(row, cel++, invoiceMasterDO.getSupplierCode());
				excel.setCellValue(row, cel++, invoiceMasterDO.getInvoiceDate());
				excel.setCellValue(row, cel++, invoiceMasterDO.getShipDate());
				excel.setCellValue(row, cel++, invoiceMasterDO.getPreArriveDate());
				excel.setCellValue(row, cel++, invoiceMasterDO.getPortArrivedate());
				excel.setCellValue(row, cel++, invoiceMasterDO.getCustomsDate());
				excel.setCellValue(row, cel++, invoiceMasterDO.getReceiveDate());
				excel.setCellValue(row, cel++, invoiceMasterDO.getArriveDate());
				excel.setCellValue(row, cel++, invoiceMasterDO.getCInvoiceNo());

				tmp = PublicUtil.isEmpty(invoiceMasterDO.getReceiveWarehouseCode()) ? ""
						: invoiceMasterDO.getReceiveWarehouseCode().toString();
				excel.setCellValue(row, cel++, warehouseMap.get(tmp));

				excel.setCellValue(row, cel++, invoiceMasterDO.getAmount());
				excel.setCellValue(row, cel++, invoiceMasterDO.getExchangeRate());
				excel.setCellValue(row, cel++, invoiceMasterDO.getAmountRmb());
				excel.setCellValue(row, cel++, invoiceMasterDO.getTransFee());
				excel.setCellValue(row, cel++, invoiceMasterDO.getCustomsFee());
				excel.setCellValue(row, cel++, invoiceMasterDO.getExciseTax());
				excel.setCellValue(row, cel++, invoiceMasterDO.getVatFee());
				excel.setCellValue(row, cel++, invoiceMasterDO.getCurrency());
				excel.setCellValue(row, cel++, invoiceMasterDO.getOtherFee());
				excel.setCellValue(row, cel++, invoiceMasterDO.getOrderQty());
				excel.setCellValue(row, cel++, invoiceMasterDO.getTotalQty());
				excel.setCellValue(row, cel++, invoiceMasterDO.getBoxQty());
				excel.setCellValue(row, cel++, invoiceMasterDO.getWeight());

				tmp = PublicUtil.isEmpty(invoiceMasterDO.getTransType()) ? ""
						: invoiceMasterDO.getTransType().toString();
				excel.setCellValue(row, cel++, transTypeMap.get(tmp));

				tmp = PublicUtil.isEmpty(invoiceMasterDO.getDataType()) ? "" : invoiceMasterDO.getDataType().toString();
				excel.setCellValue(row, cel++, dataTypeMap.get(tmp));

				excel.setCellValue(row, cel++, invoiceMasterDO.getCreateTime());
				excel.setCellValue(row, cel++, invoiceMasterDO.getUpdateTime());
				excel.setCellValue(row, cel++, invoiceMasterDO.getConfirmDate());
				excel.setCellValue(row, cel, invoiceMasterDO.getRemark());
				row++;
			}
			excel.writeToResponse(response, "ImpInvoiceMaster.xlsx");
		} catch (Exception e) {
			log.error("[成本结算错误：]" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void exportImpInvoiceDetailPack(ImpInvoiceDetailRequest request) {
		LambdaQueryWrapper<ImpInvoiceDetailPackDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper = getImpInvoiceDetailPackWrapper(request);
		List<ImpInvoiceDetailPackDO> list = impInvoiceDetailPackMapper.selectList(queryWrapper);

		if (list == null) {
			return;
		}
		String path = "templates/ImpInvoiceDetail.xlsx";
		InputStream inputStream = FileUtil.getTemplate(path);
		ExcelHelper excel = new ExcelHelper(inputStream);
		excel.openSheet(0);

		// 向模板中写入数据
		int row = 1;
		int cel;
		for (ImpInvoiceDetailPackDO detailDO : list) {
			cel = 0;
			excel.setCellValue(row, cel++, detailDO.getId());
			excel.setCellValue(row, cel++, detailDO.getInvoiceId());
			excel.setCellValue(row, cel++, detailDO.getInvoiceNo());
			excel.setCellValue(row, cel++, detailDO.getFullOrderNo());
			excel.setCellValue(row, cel++, detailDO.getStatus());
			excel.setCellValue(row, cel++, detailDO.getModelNo());
			excel.setCellValue(row, cel++, detailDO.getQuantity());
			excel.setCellValue(row, cel++, detailDO.getPrice());
			excel.setCellValue(row, cel++, detailDO.getBarcode());
			excel.setCellValue(row, cel++, detailDO.getCaseNo());
			excel.setCellValue(row, cel++, detailDO.getOrigin());
			excel.setCellValue(row, cel++, detailDO.getWeight());
			excel.setCellValue(row, cel++, detailDO.getOverseaInvoiceNo());
			excel.setCellValue(row, cel++, detailDO.getRoHSCode());
			excel.setCellValue(row, cel, detailDO.getRemark());
			row++;
		}
		excel.writeToResponse(response, "ImpInvoiceDetail.xlsx");
	}

	@Override
	public ResultVo<PageInfo<ImpInvoiceDetailDO>> listImpInvoiceDetailByInvoiceId(ImpInvoiceDetailRequest request) {
		LambdaQueryWrapper<ImpInvoiceDetailDO> queryWrapper = getImpInvoiceDetailWrapper(request);
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		List<ImpInvoiceDetailDO> list = this.impInvoiceDetailMapper.selectList(queryWrapper);
		PageInfo<ImpInvoiceDetailDO> pageInfo = BeanCopyUtil.pageDto2Vo(new PageInfo<>(list), ImpInvoiceDetailDO.class);
		return ResultVo.success(pageInfo);
	}

	@Override
	public void exportImpInvoiceDetail(ImpInvoiceDetailRequest request) {
		LambdaQueryWrapper<ImpInvoiceDetailDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper = getImpInvoiceDetailWrapper(request);
		List<ImpInvoiceDetailDO> list = impInvoiceDetailMapper.selectList(queryWrapper);

		if (list == null) {
			return;
		}
		String path = "templates/ImpInvoiceDetail.xlsx";
		InputStream inputStream = FileUtil.getTemplate(path);
		ExcelHelper excel = new ExcelHelper(inputStream);
		excel.openSheet(0);

		// 向模板中写入数据
		int row = 1;
		int cel;
		for (ImpInvoiceDetailDO detailDO : list) {
			cel = 0;
			excel.setCellValue(row, cel++, detailDO.getId());
			excel.setCellValue(row, cel++, detailDO.getInvoiceId());
			excel.setCellValue(row, cel++, detailDO.getInvoiceNo());
			excel.setCellValue(row, cel++, detailDO.getFullOrderNo());
			excel.setCellValue(row, cel++, detailDO.getStatus());
			excel.setCellValue(row, cel++, detailDO.getModelNo());
			excel.setCellValue(row, cel++, detailDO.getQuantity());
			excel.setCellValue(row, cel++, detailDO.getPrice());
			excel.setCellValue(row, cel++, detailDO.getBarcode());
			excel.setCellValue(row, cel++, detailDO.getCaseNo());
			excel.setCellValue(row, cel++, detailDO.getOrigin());
			excel.setCellValue(row, cel++, detailDO.getWeight());
			excel.setCellValue(row, cel++, detailDO.getOverseaInvoiceNo());
			excel.setCellValue(row, cel++, detailDO.getRoHSCode());
			excel.setCellValue(row, cel, detailDO.getRemark());
			row++;
		}
		excel.writeToResponse(response, "ImpInvoiceDetail.xlsx");
	}

	// @Override
	// public ResultVo<PageInfo<ImpInvoiceDetailDiffVO>>
	// listImpInvoiceDetailDiffByInvoiceId
	// (ImpInvoiceDetailDiffRequest request) {
	// impInvoiceDetailMapper.updateImpInvoiceQty(request.getInvoiceId());
	// List<ImpInvoiceDetailDiffVO> list =
	// this.impInvoiceDetailMapper.listImpInvoiceDetailDiffByInvoiceId(request.getInvoiceId());
	// Page page = new Page(request.getPageNum(), request.getPageSize());
	// page.setTotal(list.size());
	// int startIndex = (request.getPageNum() - 1) * request.getPageSize();
	// int endIndex = Math.min((startIndex + request.getPageSize()),
	// list.size());
	// page.addAll(list.subList(startIndex, endIndex));
	// PageInfo<ImpInvoiceDetailDiffVO> pageInfo = new PageInfo<>(page);
	// return ResultVo.success(pageInfo);
	// }

	@Override
	public ResultVo<PageInfo<ImpInvoiceErrorDO>> listImpInvoiceErrorByInvoiceId(ImpInvoiceErrorRequest request) {
		PageInfo<ImpInvoiceErrorDO> pageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize())
				.doSelectPageInfo(() -> impInvoiceErrorMapper.getInvoiceError(request));
		return ResultVo.success(pageInfo);
	}

	@Override
	public void exportImpInvoiceError(ImpInvoiceErrorRequest request) {
		List<ImpInvoiceErrorDO> list = impInvoiceErrorMapper.getInvoiceError(request);
		if (list == null) {
			return;
		}
		String path = "templates/ImpInvoiceDetailDiff.xlsx";
		InputStream inputStream = FileUtil.getTemplate(path);
		ExcelHelper excel = new ExcelHelper(inputStream);

		excel.openSheet(0);

		// 向模板中写入数据
		int row = 1;
		int cel;
		for (ImpInvoiceErrorDO detailDO : list) {
			cel = 0;
			excel.setCellValue(row, cel++, request.getInvoiceId());
			excel.setCellValue(row, cel++, detailDO.getOrderNo());
			excel.setCellValue(row, cel++, detailDO.getModelNo());
			excel.setCellValue(row, cel++, detailDO.getPoModelNo());
			excel.setCellValue(row, cel++, detailDO.getQty());
			excel.setCellValue(row, cel++, detailDO.getPoQty());
			excel.setCellValue(row, cel++, detailDO.getPackQty());
			excel.setCellValue(row, cel++, detailDO.getErrorText());
			excel.setCellValue(row, cel, detailDO.getRemark());

			row++;
		}
		excel.writeToResponse(response, "ImpInvoiceDetailDiff.xlsx");
	}

	/**
	 * 导入日本SHPINF文件 20230203 A78027 增加解析出厂日 bug-9415
	 *
	 * @param file
	 * @return
	 */
	@Override
	public ResultVo<String> importJPShippingFile(MultipartFile file) {
		if (file == null) {
			return ResultVo.failure("空文件.");
		}
		String filename = file.getOriginalFilename();
		if (!filename.matches("^.+\\.(?i)(TXT)$")) {
			return ResultVo.failure("文件格式错误,只可上传txt");
		}

		String line;
		List<ImportOrderInfoVO> impInoviceList = new ArrayList<>();
		ImportOrderInfoVO importOrderInfoVO;
		// int row = 0;

		try (InputStreamReader isReader = new InputStreamReader(file.getInputStream(), StandardCharsets.US_ASCII);
				BufferedReader bf = new BufferedReader(isReader)) {
			log.info("开始导入JP SHPINF {}", file.getOriginalFilename());

            int notParse = 0;

			while ((line = bf.readLine()) != null) {
				line = line.trim();

				if (StringUtils.isBlank(line) || line.length() < 30) {
					continue;
				}
                if (notParse == 10) {
                    return ResultVo.success("", "非自动化公司发票无需导入");
                }
				// row++;
				if (!line.startsWith("95012")) {
                    log.error("非自动化公司发票无需导入 {}",line);
                    notParse++;
					continue;
				}
                notParse = 0;
				importOrderInfoVO = convertJPShippingFileLine(line);
				if (importOrderInfoVO == null) {
					continue;
				}
				// 为发货数据(状态1才追加) 且发票号非CN北京或含CS
				switch (importOrderInfoVO.getDataType()) {
				case 1:
					impInoviceList.add(importOrderInfoVO);
					break;
				case 2:// GSS
					break;
				case 3:// 预计出厂日
					break;
				case 4:// 出厂日
						// update by A78027 from bug 9415 in 2023-02-03
					if (importOrderInfoVO.getOptDate() == null) {
						break;
					}
					try {

						OrderStateVO orderStateVO = new OrderStateVO();
						orderStateVO.setOrderNo(importOrderInfoVO.getFullOrderNo());
						orderStateVO.setRorderNo(importOrderInfoVO.getOrderNo());
						orderStateVO.setItemNo(importOrderInfoVO.getItemNo());
						orderStateVO.setSplitNo(importOrderInfoVO.getSplitItemNo());
						orderStateVO.setStateDate(importOrderInfoVO.getOptDate());
						orderStateVO.setPoFacExpdate(importOrderInfoVO.getOptDate());
						orderStateVO.setStateDes(DateUtil.getFormatDate(importOrderInfoVO.getOptDate(), "yyyyMMdd")
								+ "日本出厂,数量" + importOrderInfoVO.getQtyInExport());
						Long dayDiff = Math
								.abs(DateUtil.getDiffDay(importOrderInfoVO.getOptDate(), DateUtil.getToday()));
						orderStateVO.setProvider("JP");
						orderStateVO.setStateCode(OrderStateEnum.SupplierFinishProd.code());

						// 10天的内数据更新Order_State ，否则补的历史数据只更新Oorder_statedetail
						if (dayDiff.compareTo(10L) < 0) {
							// 发送到队列中
							orderStateService.addOrderState(orderStateVO);
						} else {
							// orderStateVO.setRemark(Integer.toString(importOrderInfoVO.getQtyInExport()));
							log.info("更新前参数: {}", JSONUtil.toJsonPrettyStr(orderStateVO));
							orderStateService.updateOrderStateDetail(OrderSateDateType.PoFacExpDate, orderStateVO,
									importOrderInfoVO.getOptDate());
						}
					} catch (Exception ex) {// 忽略异常，优先保证其他发货数据能正常导入，后期重导
						log.error("处理出厂日错误", ex);
						log.error(JSONUtil.toJsonPrettyStr(importOrderInfoVO));
					}
					break;
				default:
					break;
				}
			}
			if (CollectionUtils.isEmpty(impInoviceList)) {
				return ResultVo.success("无数据");
			}
			log.info("解析分包明细记录数: {}", impInoviceList.size());
			SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
			// bug16765 imp发票导入部署准备,日本的箱单导入增加开关，新旧程序同时用一个开关， 0：旧系统 1：新系统
			ResultVo<DataTypeVO> dataTypeResultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("6004", "5");
			if (dataTypeResultVo != null && !"0".equals(dataTypeResultVo.getData().getExtNote1())) {
				log.info("旧系统日本shif文件导入已关闭,请用新程序进行导入");
				return ResultVo.success("旧系统日本shif文件导入已关闭,请用新程序进行导入");
			}
			return impInvoiceService.addImpInvoiceDataPack(impInoviceList);
		} catch (Exception e) {
			log.error("importJPShippingFile: {}", e.getMessage(), e);
			return ResultVo.failure(e.getMessage());
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> updateImpInvoiceDetail(ImpInvoiceDetailVO detailDO) {
		try {
			if (StringUtils.isNotBlank(detailDO.getCaseNo()) && detailDO.getCaseNo().length() < 3) {
				return ResultVo.failure("箱号不能少于3位数字，可以为空，建议取3位");
			}
			LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(ImpInvoiceMasterDO::getId, detailDO.getInvoiceId());
			ImpInvoiceMasterDO invoiceMasterDO = this.getImpInvoiceMasterByWrapper(queryWrapper);
			if (invoiceMasterDO == null) {
				return ResultVo.failure("无有效数据");
			}
			if (10 != invoiceMasterDO.getStatus() && 1 != invoiceMasterDO.getStatus()
					&& 2 != invoiceMasterDO.getStatus() && 4 != invoiceMasterDO.getStatus()) {
				return ResultVo.failure("不可编辑,请检查发票号状态");
			}
			// if (invoiceMasterDO.getId().equals(detailDO.getInvoiceId())) {
			// return ResultVo.failure("状态错误，请检查");
			// }

            LambdaUpdateWrapper<ImpInvoiceDetailDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper
                    .eq(ImpInvoiceDetailDO::getId, detailDO.getId())
                    .set(ImpInvoiceDetailDO::getOrderNo, detailDO.getOrderNo())
                    .set(ImpInvoiceDetailDO::getItemNo, detailDO.getItemNo())
                    .set(ImpInvoiceDetailDO::getSplitItemNo, detailDO.getSplitItemNo())
                    .set(ImpInvoiceDetailDO::getFullOrderNo, detailDO.getFullOrderNo())
                    .set(ImpInvoiceDetailDO::getPoNo, detailDO.getPoNo())
                    .set(ImpInvoiceDetailDO::getPoItemNo, detailDO.getPoItemNo())
                    .set(ImpInvoiceDetailDO::getModelNo, detailDO.getModelNo())
                    .set(ImpInvoiceDetailDO::getCaseNo, detailDO.getCaseNo())
                    .set(ImpInvoiceDetailDO::getRemark, detailDO.getRemark())
                    .set(ImpInvoiceDetailDO::getBarcode, detailDO.getBarcode())
                    .set(ImpInvoiceDetailDO::getPrice, detailDO.getPrice())
                    .set(ImpInvoiceDetailDO::getQuantity, detailDO.getQuantity())
                    .set(ImpInvoiceDetailDO::getOrigin, detailDO.getOrigin())
                    .set(ImpInvoiceDetailDO::getWeight, detailDO.getWeight())
                    .set(ImpInvoiceDetailDO::getImpOrderNo, detailDO.getImpOrderNo())
                    .set(ImpInvoiceDetailDO::getNonCommercial, detailDO.getNonCommercial())
                    .set(ImpInvoiceDetailDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo())
                    .set(ImpInvoiceDetailDO::getUpdateTime, new Date());
			/*UpdateWrapper<ImpInvoiceDetailDO> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("id", detailDO.getId());
			ImpInvoiceDetailDO detail = new ImpInvoiceDetailDO();
			detail.setOrderNo(detailDO.getOrderNo());
			detail.setItemNo(detailDO.getItemNo());
            detail.setSplitItemNo(detailDO.getSplitItemNo());
			detail.setFullOrderNo(detailDO.getFullOrderNo());
			detail.setPoNo(detailDO.getPoNo());
			detail.setPoItemNo(detailDO.getPoItemNo());
			detail.setModelNo(detailDO.getModelNo());
			detail.setCaseNo(detailDO.getCaseNo());
			detail.setRemark(detailDO.getRemark());
			detail.setBarcode(detailDO.getBarcode());
			detail.setPrice(detailDO.getPrice());
			detail.setQuantity(detailDO.getQuantity());
			detail.setOrigin(detailDO.getOrigin());
			detail.setWeight(detailDO.getWeight());
			detail.setImpOrderNo(detailDO.getImpOrderNo());
			detail.setNonCommercial(detailDO.getNonCommercial());
			detail.setUpdateUser(SMCApp.getLoginAuthDto().getUserNo());*/
			// detail.setSupplierCode(detailDO.getSupplierCode());
			// detail.setImpOrderNo(String.format("%s-%s", detail.getOrderNo(),
			// detail.getItemNo()));
			// detail.setSupplierCode(detailDO.getSupplierCode());
			int result = impInvoiceDetailMapper.update(null, lambdaUpdateWrapper);
			if (result != 1) {
				return ResultVo.failure("更新错误");
			}
			updateImpInvoiceQty(invoiceMasterDO.getId());
			// 检查发票差异
			// this.checkImpInvoiceError(invoiceMasterDO.getId());
			return ResultVo.success("保存成功。");
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> updateImpInvoiceDetailPack(ImpInvoiceDetailPackDTO detailDO) {
		try {
			if (StringUtils.isNotBlank(detailDO.getCaseNo()) && detailDO.getCaseNo().length() < 3) {
				return ResultVo.failure("箱号不能少于3位数字，可以为空，建议取3位");
			}
			LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(ImpInvoiceMasterDO::getId, detailDO.getInvoiceId());
			ImpInvoiceMasterDO invoiceMasterDO = this.getImpInvoiceMasterByWrapper(queryWrapper);
			if (invoiceMasterDO == null) {
				return ResultVo.failure("无有效数据");
			}
			if (10 != invoiceMasterDO.getStatus() && 1 != invoiceMasterDO.getStatus()
					&& 2 != invoiceMasterDO.getStatus() && 4 != invoiceMasterDO.getStatus()) {
				return ResultVo.failure("不可编辑,请检查发票号状态");
			}
			// if (invoiceMasterDO.getId().equals(detailDO.getInvoiceId())) {
			// return ResultVo.failure("状态错误，请检查");
			// }

            LambdaUpdateWrapper<ImpInvoiceDetailPackDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(ImpInvoiceDetailPackDO::getId, detailDO.getId())
                    .set(ImpInvoiceDetailPackDO::getOrderNo, detailDO.getOrderNo())
                    .set(ImpInvoiceDetailPackDO::getItemNo, detailDO.getItemNo())
                    .set(ImpInvoiceDetailPackDO::getSplitItemNo, detailDO.getSplitItemNo())
                    .set(ImpInvoiceDetailPackDO::getFullOrderNo, detailDO.getFullOrderNo())
                    .set(ImpInvoiceDetailPackDO::getPoNo, detailDO.getPoNo())
                    .set(ImpInvoiceDetailPackDO::getPoItemNo, detailDO.getPoItemNo())
                    .set(ImpInvoiceDetailPackDO::getModelNo, detailDO.getModelNo())
                    .set(ImpInvoiceDetailPackDO::getCaseNo, detailDO.getCaseNo())
                    .set(ImpInvoiceDetailPackDO::getRemark, detailDO.getRemark())
                    .set(ImpInvoiceDetailPackDO::getBarcode, detailDO.getBarcode())
                    .set(ImpInvoiceDetailPackDO::getPrice, detailDO.getPrice())
                    .set(ImpInvoiceDetailPackDO::getQuantity, detailDO.getQuantity())
                    .set(ImpInvoiceDetailPackDO::getOrigin, detailDO.getOrigin())
                    .set(ImpInvoiceDetailPackDO::getWeight, detailDO.getWeight())
                    .set(ImpInvoiceDetailPackDO::getImpOrderNo, detailDO.getImpOrderNo())
                    .set(ImpInvoiceDetailPackDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo())
                    .set(ImpInvoiceDetailPackDO::getUpdateTime, new Date());

			/*UpdateWrapper<ImpInvoiceDetailPackDO> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("id", detailDO.getId());
			ImpInvoiceDetailPackDO detail = new ImpInvoiceDetailPackDO();
			detail.setOrderNo(detailDO.getOrderNo());
			detail.setItemNo(detailDO.getItemNo());
			detail.setFullOrderNo(detailDO.getFullOrderNo());
			detail.setPoNo(detailDO.getPoNo());
			detail.setPoItemNo(detailDO.getPoItemNo());
			detail.setModelNo(detailDO.getModelNo());
			detail.setCaseNo(detailDO.getCaseNo());
			detail.setRemark(detailDO.getRemark());
			detail.setBarcode(detailDO.getBarcode());
			detail.setPrice(detailDO.getPrice());
			detail.setQuantity(detailDO.getQuantity());
			detail.setOrigin(detailDO.getOrigin());
			detail.setWeight(detailDO.getWeight());
			detail.setImpOrderNo(detailDO.getImpOrderNo());
			detail.setUpdateUser(SMCApp.getLoginAuthDto().getUserNo());*/
			// detail.setSupplierCode(detailDO.getSupplierCode());
			// detail.setImpOrderNo(String.format("%s-%s", detail.getOrderNo(),
			// detail.getItemNo()));
			// detail.setSupplierCode(detailDO.getSupplierCode());
			int result = impInvoiceDetailPackMapper.update(null, lambdaUpdateWrapper);
			if (result != 1) {
				return ResultVo.failure("更新错误");
			}
			// 检查发票差异
			// this.checkImpInvoiceError(invoiceMasterDO.getId());
			return ResultVo.success("更新成功。");
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> delImpInvoiceDetail(Integer invoiceId, Integer detailId) {
		try {
			LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(ImpInvoiceMasterDO::getId, invoiceId);
			ImpInvoiceMasterDO invoiceMasterDO = this.getImpInvoiceMasterByWrapper(queryWrapper);
			if (PublicUtil.isEmpty(invoiceMasterDO)) {
				return ResultVo.failure("无有效数据");
			}
			if (10 != invoiceMasterDO.getStatus() && 1 != invoiceMasterDO.getStatus()
					&& 2 != invoiceMasterDO.getStatus() && 4 != invoiceMasterDO.getStatus()) {
				return ResultVo.failure("不可删除,请检查发票号状态");
			}
			int result = impInvoiceDetailMapper.deleteById(detailId);
			if (result != 1) {
				return ResultVo.failure("删除错误");
			}
			updateImpInvoiceQty(invoiceId.longValue());
			// 检查发票差异
			// this.checkImpInvoiceError(invoiceMasterDO.getId());
			return ResultVo.success("删除成功。");
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> delImpInvoiceDetailPack(Integer invoiceId, Integer detailId) {
		try {
			LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(ImpInvoiceMasterDO::getId, invoiceId);
			ImpInvoiceMasterDO invoiceMasterDO = this.getImpInvoiceMasterByWrapper(queryWrapper);
			if (PublicUtil.isEmpty(invoiceMasterDO)) {
				return ResultVo.failure("无有效数据");
			}
			if (10 != invoiceMasterDO.getStatus() && 1 != invoiceMasterDO.getStatus()
					&& 2 != invoiceMasterDO.getStatus() && 4 != invoiceMasterDO.getStatus()) {
				return ResultVo.failure("不可删除,请检查发票号状态");
			}
			UpdateWrapper<ImpInvoiceDetailPackDO> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("id", detailId);
			updateWrapper.eq("invoice_id", invoiceId);
			int result = impInvoiceDetailPackMapper.delete(updateWrapper);
			if (result != 1) {
				return ResultVo.failure("删除错误");
			}
			// 检查发票差异
			// this.checkImpInvoiceError(invoiceMasterDO.getId());
			return ResultVo.success("删除成功。");
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public ResultVo<String> produceToconfirmPOInvoice(Long invoiceId) {
		try {
			// 检查差异
			ResultVo<Integer> chckCount = checkImpInvoiceError(invoiceId);
			if (chckCount != null && chckCount.getData() > 0) {
				log.error(invoiceId + "存在差异");
				return ResultVo.failure(invoiceId + "存在差异");
			}
			ResultVo<String> resultVo = impInvoiceService.confirmPOInvoice(invoiceId.intValue());
			if (!resultVo.isSuccess()) {
				log.error(invoiceId + "制造发票入库失败：" + resultVo.getMessage());
				return ResultVo.failure(invoiceId + "制造发票入库失败：" + resultVo.getMessage());
			}
			return ResultVo.success();
		} catch (Exception e) {
			log.error(invoiceId + "制造发票入库失败：" + e.getMessage());
			return ResultVo.failure(invoiceId + "制造发票入库失败：" + e.getMessage());
		}
	}

	@Override
	public ResultVo<String> autoGPconfirmPOInvoice(String optDate) {
		List<Long> invoiceIds = impInvoiceMasterMapper.listGPtoconfirmInvoiceId(optDate);

		ImpInvoiceErrorDTO dto;
		for (Long invoiceId : invoiceIds) {
			try {
				// 忽略差异
				ResultVo<Integer> chckCount = checkImpInvoiceError(invoiceId);
				if (chckCount != null && chckCount.getData() > 0) {
					log.error(invoiceId + "存在差异");
					continue;
				}
				// ImpInvoiceErrorDTO dto=new ImpInvoiceErrorDTO();
				// dto.setInvoiceId(invoiceId);
				// dto.setIgnoreReason("制造发货忽略");
				// updateImpInvoiceIgnoreError(dto);
				ResultVo<String> resultVo = impInvoiceService.confirmPOInvoice(invoiceId.intValue());
				if (!resultVo.isSuccess()) {
					log.error(invoiceId + "发票自动入库失败：" + resultVo.getMessage());
				}
			} catch (Exception e) {
				log.error(invoiceId + "发票自动入库失败：" + e.getMessage());
			}
		}
		return ResultVo.success();
	}

	/**
	 * 批量入库
	 *
	 * @param invoiceIds
	 * @return
	 */
	@Override
	// @Transactional(rollbackFor = Exception.class)
	public ResultVo<String> confirmPOInvoices(List<Integer> invoiceIds) {
		int count = 0;
		StringBuilder msg = new StringBuilder();
		ResultVo<String> resultVo;
		for (Integer invoiceId : invoiceIds) {
			resultVo = impInvoiceService.confirmPOInvoice(invoiceId);
			if (resultVo.isSuccess()) {
				count++;
			} else {
				msg.append(invoiceId).append("入库失败：").append(resultVo.getMessage());
			}
		}
		if (count > 0) {
			return ResultVo.successMsg(count + "条数据入库成功！" + msg);
		} else {
			return ResultVo.failure(msg.toString());
		}
	}

	/**
	 * 发票入库
	 *
	 * @param invoiceId
	 * @return
	 */
	@Override
	// @Transactional(rollbackFor = Exception.class)
	public ResultVo<String> confirmPOInvoice(Integer invoiceId) {
		String lockKey = Constants.REDIS_KEY_IMP_CONFIRM_LOCK + invoiceId;
		if (!redissonUtil.tryLock(lockKey, 1, 900, TimeUnit.SECONDS)) {
			log.info(invoiceId + "发票入库重复 " + lockKey);
			return ResultVo.failure("发票入库不能重复点击");
		}
		try {
			String optUser;
			try {
				optUser = SMCApp.getLoginAuthDto().getUserNo();
			} catch (Exception e) {
				optUser = "System";
			}

			// 1.检查数据
			LambdaQueryWrapper<ImpInvoiceMasterDO> queryMasterWrapper = new LambdaQueryWrapper<>();
			queryMasterWrapper.eq(ImpInvoiceMasterDO::getId, invoiceId);
			ImpInvoiceMasterDO invoiceMasterDO = this.getImpInvoiceMasterByWrapper(queryMasterWrapper);
			if (null == invoiceMasterDO) {
				return ResultVo.failure("无有效数据");
			}
			if (2 != invoiceMasterDO.getStatus()) {
				return ResultVo.failure("不可发票入库,请检查发票号状态" + invoiceMasterDO.getInvoiceNo());
			}
			if (StringUtils.isBlank(invoiceMasterDO.getSupplierCode())) {
				return ResultVo.failure("发票供应商不能为空，" + invoiceMasterDO.getInvoiceNo());
			}
			if (StringUtils.isBlank(invoiceMasterDO.getReceiveWarehouseCode())) {
				return ResultVo.failure("收货仓库为空不能入库" + invoiceMasterDO.getInvoiceNo());
			}

			LambdaQueryWrapper<ImpInvoiceErrorDO> invoiceErrorQueryWrapper = new LambdaQueryWrapper<>();
			invoiceErrorQueryWrapper.eq(ImpInvoiceErrorDO::getInvoiceId, invoiceId);
			invoiceErrorQueryWrapper.ne(ImpInvoiceErrorDO::getIgnoreError, 1); // 排除ignore_error
																				// =1
			Integer errCount = impInvoiceErrorMapper.selectCount(invoiceErrorQueryWrapper);
			if (errCount != null && errCount > 0) {
				return ResultVo.failure("存在发票差异，请修正后再入库" + invoiceMasterDO.getInvoiceNo());
			}

			invoiceMasterDO.setConfirmDate(new Date());
			invoiceMasterDO.setUpdateUser(optUser);

			// 2.未处理的改成待入库
			poInvoiceService.updateImpInvoiceDetailPack(2, invoiceMasterDO.getId());

			// 3.推送数据给采购模块
			ResultVo<String> resultVo = toImpdata(invoiceMasterDO.getInvoiceNo(), invoiceId);
			if (resultVo.isSuccess() == false) {
				log.error("发票入库失败：{}", resultVo.getMessage());
				sendImpInvocieConfirmProcessMsg(invoiceMasterDO);
				return resultVo;
			}

			// 4.更新状态到3和写入到ops_po_invoice
			resultVo = confirmPOInvoiceUpdate(invoiceMasterDO);

			// 5.发送异步消息
			sendImpInvocieConfirmProcessMsg(invoiceMasterDO);

			return resultVo;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		} finally {
			redissonUtil.unlock(lockKey);
		}
	}

	private void sendImpInvocieConfirmProcessMsg(ImpInvoiceMasterDO invoiceMasterDO) {
		ImpInvoiceProcessDTO processDTO = new ImpInvoiceProcessDTO();
		processDTO.setInvoiceNo(invoiceMasterDO.getInvoiceNo());
		processDTO.setReceiveTime(invoiceMasterDO.getConfirmDate());
		processDTO.setInvoiceId(invoiceMasterDO.getId());
		processDTO.setProcessType(8);
		processDTO.setRemark("完成发票入库");
		processDTO.setOptTime(invoiceMasterDO.getConfirmDate());
		processDTO.setOptUser(invoiceMasterDO.getUpdateUser());
		this.sendInvoiceProcessMsgToMQ(processDTO);
	}

    /**
     * bugid:19186 smccode合并方案
     * @param invoiceMasterDO
     */
    public void mergeInvoice(ImpInvoiceMasterDO invoiceMasterDO,List<MergeInvoiceDto> invoiceSplits){
        if(CollectionUtils.isNotEmpty(invoiceSplits)){
            // 更新为收货状态
            mergeSmcCodeDao.upTransferInfoStatus(invoiceMasterDO.getId(),invoiceMasterDO.getUpdateUser());
            invoiceMasterDO.setId(invoiceSplits.get(0).getMergeInvoiceId());
            invoiceMasterDO.setInvoiceNo(invoiceSplits.get(0).getOriginalInvoiceNo());
            Integer totalQty = mergeSmcCodeDao.getImpInvoiceMasterByIds(invoiceSplits);
            invoiceMasterDO.setTotalQty(totalQty);
        }
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResultVo<String> confirmPOInvoiceUpdate(ImpInvoiceMasterDO invoiceMasterDO) {
        String lockKey = Constants.REDIS_KEY_IMP_CONFIRM_UPD_LOCK + invoiceMasterDO.getId();
        // bugid:19186 smccode合并方案
        List<MergeInvoiceDto> invoiceSplitList = mergeSmcCodeDao.getInvoiceSplitListById(invoiceMasterDO.getId());
        if(CollectionUtils.isNotEmpty(invoiceSplitList)){
            lockKey = Constants.REDIS_KEY_IMP_CONFIRM_UPD_LOCK + invoiceSplitList.get(0).getMergeInvoiceId();
            invoiceSplitList = mergeSmcCodeDao.getInvoiceSplitListByMegreId(invoiceSplitList.get(0).getMergeInvoiceId());
        }
		if (!redissonUtil.tryLock(lockKey, 1, 480, TimeUnit.SECONDS)) {
			log.info("发票入库更新中" + lockKey);
			return ResultVo.failure("发票入库更新中");
		}
		try {
			// 1.更新成已发票入库状态

			Date confirmDate = new Date();
			UpdateWrapper<ImpInvoiceMasterDO> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("id", invoiceMasterDO.getId());
			updateWrapper.eq("status", 2);

			ImpInvoiceMasterDO updImpInvoiceMasterDo = new ImpInvoiceMasterDO();
			updImpInvoiceMasterDo.setId(invoiceMasterDO.getId());
			updImpInvoiceMasterDo.setUpdateTime(confirmDate);
			updImpInvoiceMasterDo.setStatus(3);
			updImpInvoiceMasterDo.setConfirmDate(confirmDate);
			updImpInvoiceMasterDo.setUpdateUser(invoiceMasterDO.getUpdateUser());
			int row = impInvoiceMasterMapper.update(updImpInvoiceMasterDo, updateWrapper);
			if (row != 1) {
				throw new BusinessException("更新发票失败" + invoiceMasterDO.getId());
			}

			// 2 写入ops_po_invoice
			invoiceMasterDO.setStatus(1);
			Date impdate = DateUtil.getCurrentDate();
			if (invoiceMasterDO.getArriveDate() != null) {
				invoiceMasterDO.setStatus(POInvoiceState.Arrived.code());
			}
			invoiceMasterDO.setAmount(BigDecimal.ZERO);
			invoiceMasterDO.setAmountRmb(BigDecimal.ZERO);
			invoiceMasterDO.setCreateUser(invoiceMasterDO.getUpdateUser());
			invoiceMasterDO.setConfirmDate(confirmDate);
            // bugid:19186 smccode合并方案
            mergeInvoice(invoiceMasterDO,invoiceSplitList);
			Boolean success = impInvoiceService.addPoInvoice(invoiceMasterDO, null);
			if (success == false) {
				throw new BusinessException("写入入库发票PO主体失败" + invoiceMasterDO.getId());
				// return ResultVo.failure("写入入库发票PO主体失败！");
			}

			// 3.回调关务状态为已到货
			if (StringUtils.isNotBlank(invoiceMasterDO.getCInvoiceNo())) {
				Map<String, String> param = new HashMap<>(8);
				param.put("plantMark", "AM");
				param.put("invNo", invoiceMasterDO.getCInvoiceNo());
				param.put("receiveTime", DateUtil.dateToDateTimeString(new Date()));
				param.put("userName", "ops");
				OrderReplyResponse response = buService.updateWarehousingInfo(param);

				if (response.getCode() != 0) {
					log.error("入库调用关务收货接口失败" + response.getMessage());
				}
			}
		} finally {
			redissonUtil.unlock(lockKey);
		}

		return ResultVo.successMsg("发票入库成功" + invoiceMasterDO.getInvoiceNo());
	}

	// @Async
	private ResultVo<String> toImpdata(String invoiceNo, Integer invoiceId) {
		CommonResult<String> comresult = wmPurchaseFeignApi.impdata(invoiceNo, invoiceId);
		if (comresult.isSuccess()) {
			return ResultVo.success(invoiceNo, "发票入库成功" + invoiceNo);
		}
		// poInvoiceService.updateImpInvoiceDetailPack(1, invoiceId);
		log.error(invoiceId + "发票入库: {}", comresult.getMessage());
		// throw new BusinessException("北京wmPurchaseFeignApi.impdata fail, " +
		// comresult.getMessage());
		return ResultVo.failure(invoiceNo + ",wmPurchaseFeignApi.impdata fail, " + comresult.getMessage());
	}

	/**
	 * 定时发票入库
	 *
	 * @return
	 */
	public ResultVo<String> syncToImpData() {
		List<ImpInvoiceDetailPackDO> list = impInvoiceDetailPackMapper
				.listToImpDataPack(DateUtil.addMonth(DateUtil.getCurrentDate(), -1));
		for (ImpInvoiceDetailPackDO info : list) {
			CommonResult<String> comresult = wmPurchaseFeignApi.impdata(info.getInvoiceNo(), info.getInvoiceId());
			if (!comresult.isSuccess()) {
				log.error(info.getInvoiceId() + "定时发票入库: {}", comresult.getMessage());
			}
		}
		return ResultVo.success("");
	}

	/*
	 * private List<ImpInvoiceMasterDO> listImpInvoiceMasterByInvoiceNo(String
	 * invoiceNo) { LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = new
	 * LambdaQueryWrapper<>(); queryWrapper.eq(ImpInvoiceMasterDO::getInvoiceNo,
	 * invoiceNo); return this.impInvoiceMasterMapper.selectList(queryWrapper);
	 * }
	 */

	@Override
	// @Transactional(rollbackFor = Exception.class)
	public ResultVo<String> impInvoiceToCost(ImpInvoiceToCostRequest request) {
		try {
			if (request == null || request.getIds().length == 0 || PublicUtil.isEmpty(request.getCostDate())) {
				return ResultVo.failure("没有选择发票ID或没有输入结算日期");
			}
			StringBuilder msg = new StringBuilder(";");
			int count = 0;
			String optUser = SMCApp.getLoginAuthDto().getUserName();
			// for (Long id : request.getIds()) {
			//
			// ResultVo<String> resultVo = doInvoiceToCost(id,
			// request.getCostDate(), optUser);
			// if (resultVo.isSuccess()) {
			// count++;
			// } else {
			// msg.append(id).append(resultVo.getMessage());
			// }
			// }
			if (count > 0) {
				return ResultVo.success("", "转成本" + count + "条数据成功" + msg);
			} else {
				return ResultVo.failure("转成本失败" + msg);
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * 增值税发票入库
	 *
	 * @param id
	 * @param costDate
	 * @param optUser
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> doInvoiceToCost(Long id, Date costDate, String optUser) {
		// 先更新与发货数据关联
		poInvoiceService.updImpShipAmount(id);
		// 1.查imp发票待转成本入库主体数据
		StringBuilder msg = new StringBuilder(";");
		ImpInvoiceMasterDO impInvoiceMasterDO = this.getImpInvoiceMasterById(id);
		if (impInvoiceMasterDO == null || impInvoiceMasterDO.getStatus() != 4) {
			return ResultVo.failure("无有效主体数据;");
		}

		// 2.查imp发票明细数据
		LambdaQueryWrapper<ImpInvoiceDetailDO> queryDetailWrapper = new LambdaQueryWrapper<>();
		queryDetailWrapper.eq(ImpInvoiceDetailDO::getInvoiceId, id);
		queryDetailWrapper.eq(ImpInvoiceDetailDO::getStatus, 5);
		List<ImpInvoiceDetailDO> details = listImpInvoiceDetailByWrapper(queryDetailWrapper);
		if (details == null || details.size() == 0) {
			return ResultVo.failure("无有效明细数据;");
		}

		if (StringUtils.isBlank(impInvoiceMasterDO.getReceiveWarehouseCode())) {
			String receiveWarehouseCode = impInvoiceMasterMapper.getReceiveWarehouseCode(details.get(0).getPoNo());
			if (StringUtils.isBlank(receiveWarehouseCode)) {
				return ResultVo.failure("无收货仓库，无法转成本");
			}
			impInvoiceMasterDO.setReceiveWarehouseCode(receiveWarehouseCode);
		}

		// 3.录入ops_po_invoice
		impInvoiceMasterDO.setStatus(POInvoiceState.CostBalanced.code()); // ops_po_invoice状态为已成本结算
		Date impDate = DateUtil.getCurrentDate();
		Boolean success = addPoInvoice(impInvoiceMasterDO, impDate, costDate);
		if (success == false) {
			return ResultVo.failure("主体数据录入失败或不能重复录入;");
		}

		// 4.录入ops_po_invoice_detail
		// addPoInvoiceDetail(impDate, details, optUser);

		// 更新明细合计
		poInvoiceDetailMapper.updatePoInvoiceDetailAmountTotal(id);
		// 更新主项合计
		OpsPoInvoiceDO pomasterDO = poInvoiceMasterMapper.getPoInvoiceDetailAmount(id);
		UpdateWrapper<OpsPoInvoiceDO> updatemaserWrapper = new UpdateWrapper<>();
		updatemaserWrapper.eq("invoice_id", id);
		OpsPoInvoiceDO updmaster = new OpsPoInvoiceDO();
		updmaster.setAmount(pomasterDO.getAmount());
		updmaster.setAmountRmb(pomasterDO.getAmountRmb());
		updmaster.setAmounttotal(pomasterDO.getAmounttotal());
		poInvoiceMasterMapper.update(updmaster, updatemaserWrapper);

		// 5.更新状态为已成本结算
		LambdaUpdateWrapper<ImpInvoiceDetailDO> updateDetailWrapper = new LambdaUpdateWrapper<>();
		updateDetailWrapper.eq(ImpInvoiceDetailDO::getInvoiceId, id);
		updateDetailWrapper.eq(ImpInvoiceDetailDO::getStatus, 5);
		ImpInvoiceDetailDO updDetailDO = new ImpInvoiceDetailDO();
		updDetailDO.setStatus(6);
		impInvoiceDetailMapper.update(updDetailDO, updateDetailWrapper);

		ImpInvoiceMasterDO masterDO = new ImpInvoiceMasterDO();
		masterDO.setId(id);
		masterDO.setReceiveWarehouseCode(impInvoiceMasterDO.getReceiveWarehouseCode());
		masterDO.setStatus(6);
		int result = impInvoiceMasterMapper.updateById(masterDO);
		if (1 == result) {
			LambdaQueryWrapper<PoInvoiceDetailDO> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(PoInvoiceDetailDO::getInvoiceId, id).ne(PoInvoiceDetailDO::getStatus, "9")
					.eq(PoInvoiceDetailDO::getImpType, 0);
			List<PoInvoiceDetailDO> detaillist = poInvoiceDetailMapper.selectList(queryWrapper);

			if (detaillist != null && detaillist.size() > 0) {
				OpsPoInvoiceDO poMasterDO = new OpsPoInvoiceDO();
				poMasterDO.setReceiveWarehouseCode(impInvoiceMasterDO.getReceiveWarehouseCode());
				poMasterDO.setExchangeRate(impInvoiceMasterDO.getExchangeRate());
				poMasterDO.setArrivedWarehouseCode(impInvoiceMasterDO.getArrivedWarehouseCode());
				salesImportService.addSalesImp(detaillist, costDate, poMasterDO);
			}
			return ResultVo.success();
		}
		return ResultVo.failure("更新成本明细出错;");
	}

	@Override
	// @Transactional(rollbackFor = Exception.class)
	public Integer impInvoiceCheck(String invoiceId, String invoiceNo) {

		if (StringUtils.isBlank(invoiceId)) {
			return null;
		}
		RLock lock = redissonUtil.lock("ops:checkInvoice:" + invoiceId);
		try {
			// 删除imp_invoice_error数据
			impInvoiceErrorMapper.deleteErrorInvoice(invoiceId);
		} catch (Exception e) {
			redissonUtil.unlock(lock);
			throw new RuntimeException();
		}

		// 根据发票id查发票明细
		List<ImpInvoiceErrorDO> impInvoiceDetailDOS = impInvoiceDetailMapper.selectInvoiceDetailByInvoiceId(invoiceId);

		// 分包中存在，发票(明细)中不存在
		List<ImpInvoiceErrorDO> invoiceDetailDOS = impInvoiceDetailPackMapper
				.selectTmpImpInvoiceErrorForNoExits(invoiceId);

		if (CollectionUtils.isEmpty(impInvoiceDetailDOS) && CollectionUtils.isEmpty(invoiceDetailDOS)) {
			return null;
		}

		Map<String, ImpInvoiceErrorDO> map = new HashMap<>();

		// 存入map
		for (ImpInvoiceErrorDO item : impInvoiceDetailDOS) {
			map.put(item.getPoNo() + "=" + item.getPoItemNo(), item);
		}
		for (ImpInvoiceErrorDO item : invoiceDetailDOS) {
			if (map.containsKey(item.getPoNo() + "=" + item.getPoItemNo())) {
				ImpInvoiceErrorDO impInvoiceErrorDO = map.get(item.getPoNo() + "=" + item.getPoItemNo());
				impInvoiceErrorDO.setPackQty(item.getPackQty());
				map.put(item.getPoNo() + "=" + item.getPoItemNo(), impInvoiceErrorDO);
			} else {
				map.put(item.getPoNo() + "=" + item.getPoItemNo(), item);
			}
		}
		List<String> orderNoList = new ArrayList<>(map.keySet());
		List<String> mainPoNoList = new ArrayList<>();
		List<String> itemNoList = new ArrayList<>();

		// 提取采购号和项号 查采购数据
		if (CollectionUtils.isNotEmpty(orderNoList)) {
			for (String item : orderNoList) {
				String[] split = item.split("=");
				mainPoNoList.add(split[0]);
				itemNoList.add(split[1]);
			}
		}
		LambdaQueryWrapper<OpsPurchaseInvoiceDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.ne(OpsPurchaseInvoiceDO::getStateCode, "5")
				.in(CollectionUtils.isNotEmpty(mainPoNoList), OpsPurchaseInvoiceDO::getPoNo, mainPoNoList)
				.in(CollectionUtils.isNotEmpty(itemNoList), OpsPurchaseInvoiceDO::getItemNo, itemNoList);
		List<OpsPurchaseInvoiceDO> opsPurchaseInvoiceDOS = opsPurchaseInvoiceMapper.selectList(queryWrapper);
		if (CollectionUtils.isEmpty(opsPurchaseInvoiceDOS)) {
			return null;
		}
		int insertCount = 0;
		// 比对差异
		for (String k : map.keySet()) {
			ImpInvoiceErrorDO impInvoiceErrorDO = map.get(k);
			for (OpsPurchaseInvoiceDO item : opsPurchaseInvoiceDOS) {
				if (StringUtils.isBlank(item.getPoNo()) || item.getItemNo() == null) {
					continue;
				}
				if (item.getPoNo().equals(impInvoiceErrorDO.getPoNo())
						&& item.getItemNo().equals(impInvoiceErrorDO.getPoItemNo())) {
					if (item.getQuantity() == null) {
						item.setQuantity(0);
					}
					if (item.getQtyImport() == null) {
						item.setQtyImport(0);
					}
					impInvoiceErrorDO.setInvoiceId(Long.valueOf(invoiceId));
					impInvoiceErrorDO.setPoQty(item.getQuantity() - item.getQtyImport());
					impInvoiceErrorDO.setPoModelNo(item.getModelNo());
					impInvoiceErrorDO.setPoWarehouseCode(item.getReceiveWarehouseId());
					impInvoiceErrorDO.setCreateTime(new Date());
					impInvoiceErrorDO.setInvoiceNo(invoiceNo);
					int count = 0;
					// 1 数量差异 2 型号差异 3 两者都存在
					if (impInvoiceErrorDO.getQty() == null
							|| !impInvoiceErrorDO.getQty().equals(impInvoiceErrorDO.getPackQty())
							|| impInvoiceErrorDO.getQty() > impInvoiceErrorDO.getPoQty()) {
						count = count + 1;
						impInvoiceErrorDO.setErrorType(1);
						impInvoiceErrorDO.setErrorText("数量差异");
					}
					if (!impInvoiceErrorDO.getModelNo().equals(impInvoiceErrorDO.getPoModelNo())) {
						count = count + 1;
						impInvoiceErrorDO.setErrorType(2);
						impInvoiceErrorDO.setErrorText("型号不同");
					}
					if (count == 2) {
						impInvoiceErrorDO.setErrorType(3);
						impInvoiceErrorDO.setErrorText("数量差异,型号差异");
					}

					impInvoiceErrorMapper.insert(impInvoiceErrorDO);
					insertCount = insertCount + 1;
				}
			}
		}

		redissonUtil.unlock(lock);

		return insertCount;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean addPoInvoice(ImpInvoiceMasterDO impDO, Date costDate) {
		OpsPoInvoiceDO poDO = new OpsPoInvoiceDO();
		poDO.setInvoiceId(impDO.getId());
		poDO.setInvoiceNo(impDO.getInvoiceNo());
		poDO.setAmount(
				impDO.getAmount() == null ? BigDecimal.ZERO : impDO.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
		poDO.setAmountRmb(impDO.getAmountRmb() == null ? BigDecimal.ZERO
				: impDO.getAmountRmb().setScale(2, BigDecimal.ROUND_HALF_UP));
		// poDO.setAmount(BigDecimal.ZERO);
		// poDO.setAmountRmb(BigDecimal.ZERO);
		poDO.setArrivedWarehouseCode(impDO.getArrivedWarehouseCode());
		poDO.setBoxQty(Optional.ofNullable(impDO.getBoxQty()).orElse(0));
		poDO.setPayDay(Optional.ofNullable(impDO.getPayDay()).orElse(0));
		poDO.setCreateUser(impDO.getCreateUser());
		poDO.setUpdateUser(impDO.getCreateUser());
		poDO.setCreateTime(new Date());
		poDO.setUpdateTime(new Date());
		poDO.setCurrencyCode(impDO.getCurrency()); // 人民币
		poDO.setCustomsDate(impDO.getCustomsDate());
		poDO.setDeclarationNo(impDO.getDeclarationNo());
		poDO.setExchangeRate(impDO.getExchangeRate());
		poDO.setWeight(impDO.getWeight());
		poDO.setGrossWeight(impDO.getGrossWeight());
		poDO.setInvoiceDate(impDO.getInvoiceDate());
		// if (poDO.getInvoiceDate() == null) {
		// poDO.setInvoiceDate(impDO.getShipDate());
		// }
		poDO.setOrderQty(impDO.getOrderQty());
		poDO.setReceiveWarehouseCode(impDO.getReceiveWarehouseCode());
		poDO.setShipDate(impDO.getShipDate());
		if (POInvoiceState.CostBalanced.code() == impDO.getStatus()) {
			poDO.setCostTime(costDate);
		}
		poDO.setStatus(impDO.getStatus());
		poDO.setSupplierCode(impDO.getSupplierCode());
		poDO.setCustomsFee(impDO.getCustomsFee());
		poDO.setVatFee(impDO.getVatFee());
		poDO.setTotalQty(impDO.getTotalQty());
		poDO.setTransFee(impDO.getTransFee());
		poDO.setOtherFee(impDO.getOtherFee());
		if (impDO.getConfirmDate() == null) {
			impDO.setConfirmDate(new Date());
		}
		poDO.setImpDate(DateUtil.getDate(impDO.getConfirmDate()));
		poDO.setBargainType(impDO.getBargainType());
		poDO.setReceiveTime(impDO.getArriveDate());
		poDO.setInvoiceType(impDO.getInvoiceType());

		// add by A78027 20230413 bug 10468
		String lockKey = Constants.REDIS_KEY_IMP_CONFIRM_ADDPO_LOCK + impDO.getId();
		if (!redissonUtil.tryLock(lockKey, 0, 360, TimeUnit.SECONDS)) {
			log.info("发票入库更新中2" + lockKey);
			return false;
		}

		try {
			// 检查是否存在
			LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(OpsPoInvoiceDO::getInvoiceId, impDO.getId());
			if (poInvoiceMasterMapper.selectCount(queryWrapper) > 0) {
				return true;
			}

			int row = poInvoiceMasterMapper.insert(poDO);
			return row == 1;
		} finally {
			redissonUtil.unlock(lockKey);
		}
	}

	/**
	 * //<!--add by WuWeiDong 20240305 bug 12279 更新【无需入库】--> 更新无需入库
	 *
	 * @param request
	 * @return
	 */
	@Override
	public ResultVo<String> updateMasterNoStorage(ImpInvoiceMasterRequest request) {

		if (CollectionUtils.isEmpty(request.getIds())) {
			return ResultVo.failure("请选择处理ID。");
		}
		LambdaQueryWrapper<ImpInvoiceMasterDO> query = new LambdaQueryWrapper<>();
		query.in(ImpInvoiceMasterDO::getId, request.getIds()).select(ImpInvoiceMasterDO::getId,
				ImpInvoiceMasterDO::getStatus);
		List<ImpInvoiceMasterDO> masterDOS = impInvoiceMasterMapper.selectList(query);
		if (CollectionUtils.isEmpty(masterDOS)) {
			return ResultVo.failure("数据不存在。");
		}
		/**
		 * 已到货待入库(2) 之前和已到货待入库(2) 才可以更新为无需入库
		 */

		List<ImpInvoiceMasterDO> updateList = masterDOS.stream().filter(i -> i.getStatus().compareTo(2) <= 0)
				.collect(Collectors.toList());
		if (CollectionUtils.isEmpty(updateList)) {
			return ResultVo.failure("【已到货待入库】之前状态才能更新【无需入库】。");
		}
		List<Long> ids = updateList.stream().map(ImpInvoiceMasterDO::getId).distinct().collect(Collectors.toList());
		LambdaUpdateWrapper<ImpInvoiceMasterDO> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.in(ImpInvoiceMasterDO::getId, ids).set(ImpInvoiceMasterDO::getInvoiceType, 8)
				.set(ImpInvoiceMasterDO::getStatus, 7).set(ImpInvoiceMasterDO::getUpdateTime, DateUtil.getNow())
				.set(ImpInvoiceMasterDO::getUpdateUser, SMCApp.getLoginAuthDtoForSysUser().getUserNo());
		impInvoiceMasterMapper.update(null, updateWrapper);
		return ResultVo.success("执行成功！");

	}

	/**
	 * 录入ops_invoice
	 */
	public Boolean addPoInvoice(ImpInvoiceMasterDO impDO, Date impdate, Date costDate) {
		return impInvoiceService.addPoInvoice(impDO, costDate);
	}

	/**
	 * 录入ops_invoice_detail
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addPoInvoiceDetail(Date impdate, List<ImpInvoiceDetailDO> details, String optUser, MergeInvoiceDto mergeInvoiceDto) {

		LambdaQueryWrapper<ImpInvoiceMasterDO> queryMasterWrapper = new LambdaQueryWrapper<>();
		queryMasterWrapper.eq(ImpInvoiceMasterDO::getId, details.get(0).getInvoiceId());
		ImpInvoiceMasterDO invoiceMasterDO = this.getImpInvoiceMasterByWrapper(queryMasterWrapper);
		if (null == invoiceMasterDO) {
			return false;
		}

		// 获取币种
		if (StringUtils.isBlank(invoiceMasterDO.getCurrency())) {
			String currency = poInvoiceMasterMapper.getCurrencyCode(invoiceMasterDO.getSupplierCode());
			invoiceMasterDO.setCurrency(currency);
		}
		// 获取最新汇率
		if (StringUtils.isNotBlank(invoiceMasterDO.getCurrency())
				&& !"CNY".equalsIgnoreCase(invoiceMasterDO.getCurrency())) {
			Date exchangeDate = invoiceMasterDO.getArriveDate();
			if (PublicUtil.isEmpty(exchangeDate)) {
				exchangeDate = invoiceMasterDO.getConfirmDate();
			}
			ResultVo<BigDecimal> exchangeRateResult = poInvoiceService.getExchangeRate(invoiceMasterDO.getCurrency(),
					exchangeDate);
			if (exchangeRateResult != null) {
				invoiceMasterDO.setExchangeRate(exchangeRateResult.getData());
			}
		}
		if ("CNY".equalsIgnoreCase(invoiceMasterDO.getCurrency())) {
			invoiceMasterDO.setExchangeRate(new BigDecimal("1"));
		}

		String key;
		Map<String, PoInvoiceDetailDO> poMaps = new HashMap<>(details.size());
		PoInvoiceDetailDO poDetailDO;
		BigDecimal amount = BigDecimal.ZERO;
		for (ImpInvoiceDetailDO detailDO : details) {
			// key = detailDO.getPoNo() + "-" + detailDO.getPoItemNo();
			if (detailDO.getSplitItemNo() == null) {
				detailDO.setSplitItemNo(0);
			}

			key = detailDO.getOrderNo() + "-" + detailDO.getItemNo() + "-" + detailDO.getSplitItemNo();

			// 三国发票不合并明细数据 begin bug8713
			if (PublicUtil.isNotEmpty(invoiceMasterDO.getInvoiceType())
					&& invoiceMasterDO.getInvoiceType() == InvoiceTypeEnum.THREE_COUNTRY.getCode()) {
				key = detailDO.getId().toString();
			} // end bug8713
			poDetailDO = new PoInvoiceDetailDO();

			if (poMaps.containsKey(key)) {
				poDetailDO = poMaps.get(key);
				if (!poDetailDO.getModelNo().equalsIgnoreCase(detailDO.getModelNo())) {
					throw new BusinessException("同个订单，型号不一样，不能导入成本明细！");
				}
				poDetailDO.setQuantity(poDetailDO.getQuantity() + detailDO.getQuantity());
				// poDetailDO.setAmount(BigDecimalUtil.mul(poDetailDO.getQuantity(),
				// poDetailDO.getPrice()).setScale(2,
				// BigDecimal.ROUND_HALF_UP));
				// <!--add by WuWeiDong 20230505 bug10636
				// 写入ops_po_invoice_detail时用Imp_invoice_detail表的Amount字段-->
				amount = detailDO.getAmount();
				if (Optional.ofNullable(amount).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) == 0) {
					amount = BigDecimalUtil.mul(detailDO.getQuantity(), detailDO.getPrice());
				}
				poDetailDO.setAmount(BigDecimalUtil.add(poDetailDO.getAmount(), amount));
				poDetailDO.setExciseTax(BigDecimalUtil.add(poDetailDO.getExciseTax(), detailDO.getExciseTax())
						.setScale(2, BigDecimal.ROUND_HALF_UP));
				poDetailDO.setCustomsFee(BigDecimalUtil.add(poDetailDO.getCustomsFee(), detailDO.getCustomsFee())
						.setScale(2, BigDecimal.ROUND_HALF_UP));
				poDetailDO.setTransFee(BigDecimalUtil.add(poDetailDO.getTransFee(), detailDO.getTransFee()).setScale(2,
						BigDecimal.ROUND_HALF_UP));
				poDetailDO.setOtherFee(BigDecimalUtil.add(poDetailDO.getOtherFee(), detailDO.getOtherFee()).setScale(2,
						BigDecimal.ROUND_HALF_UP));
				poDetailDO.setVatFee(BigDecimalUtil.add(poDetailDO.getVatFee(), detailDO.getVatFee()).setScale(2,
						BigDecimal.ROUND_HALF_UP));
			} else {
                poDetailDO.setInvoiceId(detailDO.getInvoiceId());
                if(Objects.nonNull(mergeInvoiceDto)){
                    poDetailDO.setInvoiceId(mergeInvoiceDto.getMergeInvoiceId());
                }

				poDetailDO.setImpType(0);
				// 取消此处处理，后面生成组换
				/*
				 * if (detailDO.getModelNo().startsWith("*")) {
				 * poDetailDO.setModelNo(detailDO.getModelNo().substring(1)); }
				 * else { poDetailDO.setModelNo(detailDO.getModelNo()); }
				 */
				// add by A78028 20230728 bug 11586
				poDetailDO.setOrderNo(detailDO.getFullOrderNo());
				poDetailDO.setRorderNo(detailDO.getOrderNo());
				poDetailDO.setItemNo(detailDO.getItemNo());
				poDetailDO.setSplitItemNo(detailDO.getSplitItemNo());

				poDetailDO.setModelNo(detailDO.getModelNo());
				poDetailDO.setQuantity(detailDO.getQuantity());
				poDetailDO.setRemark(detailDO.getRemark());
				poDetailDO.setPrice(Optional.ofNullable(detailDO.getPrice()).orElse(BigDecimal.ZERO).setScale(4,
						BigDecimal.ROUND_HALF_UP));
				poDetailDO.setSupplierCode(detailDO.getSupplierCode());
				poDetailDO.setInvoiceNo(detailDO.getInvoiceNo());
                if(Objects.nonNull(mergeInvoiceDto)){
                    poDetailDO.setInvoiceNo(mergeInvoiceDto.getOriginalInvoiceNo());

                }
				poDetailDO.setNonCommercial(detailDO.getNonCommercial() == null ? "" : detailDO.getNonCommercial());
				// poDetailDO.setPricermb(detailDO.getPrice());
				poDetailDO.setImpDate(impdate);
				poDetailDO.setStatus(1);
				// <!--add by WuWeiDong 20230505 bug10636
				// 写入ops_po_invoice_detail时用Imp_invoice_detail表的Amount字段-->
				amount = detailDO.getAmount();
				if (Optional.ofNullable(amount).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) == 0) {
					amount = BigDecimalUtil.mul(poDetailDO.getQuantity(), poDetailDO.getPrice());
				}
				poDetailDO.setAmount(amount);
				// poDetailDO.setAmount(BigDecimalUtil.mul(poDetailDO.getQuantity(),
				// poDetailDO.getPrice()));
				poDetailDO.setCustomsFee(detailDO.getCustomsFee() == null ? BigDecimal.ZERO
						: detailDO.getCustomsFee().setScale(2, BigDecimal.ROUND_HALF_UP));
				poDetailDO.setTransFee(detailDO.getTransFee() == null ? BigDecimal.ZERO
						: detailDO.getTransFee().setScale(2, BigDecimal.ROUND_HALF_UP));
				poDetailDO.setOtherFee(detailDO.getOtherFee() == null ? BigDecimal.ZERO
						: detailDO.getOtherFee().setScale(2, BigDecimal.ROUND_HALF_UP));
				poDetailDO.setExciseTax(detailDO.getExciseTax() == null ? BigDecimal.ZERO
						: detailDO.getExciseTax().setScale(2, BigDecimal.ROUND_HALF_UP));
				poDetailDO.setVatFee(detailDO.getVatFee() == null ? BigDecimal.ZERO
						: detailDO.getVatFee().setScale(2, BigDecimal.ROUND_HALF_UP));
				poDetailDO.setWeight(detailDO.getWeight() == null ? 0D : detailDO.getWeight());
				poDetailDO.setProdCountry(detailDO.getOrigin());
				poDetailDO.setProductName(detailDO.getEnName());
				poDetailDO.setOverseaInvoiceNo(detailDO.getOverseaInvoiceNo());
				if (detailDO.getOrderType() != null) {
					poDetailDO.setOrderType(Integer.parseInt(detailDO.getOrderType()));
				}
				poDetailDO.setCreateUser(optUser);

				if (StringUtils.isNotBlank(detailDO.getNonCommercial())
						&& ("1".equals(detailDO.getNonCommercial()) || "*".equals(detailDO.getNonCommercial()))) {
					poDetailDO.setImpType(1);
					poDetailDO.setNonCommercial("1");
				} else {
					poDetailDO.setImpType(0);
				}
				poMaps.put(key, poDetailDO);
			}
		}
		// 相同订单号合并成一条数据
		int count = 0;
		BigDecimal sumAmount = BigDecimal.ZERO;
		BigDecimal sumAmountRmb = BigDecimal.ZERO;
		BigDecimal exchangeRate = Optional.ofNullable(invoiceMasterDO.getExchangeRate()).orElse(BigDecimal.ZERO);
		for (PoInvoiceDetailDO poInvoiceDetailDO : poMaps.values()) {
			poInvoiceDetailDO.setImpDate(impdate);
			poInvoiceDetailDO
					.setPrice(BigDecimalUtil.div(poInvoiceDetailDO.getAmount(), poInvoiceDetailDO.getQuantity(), 4));
			poInvoiceDetailDO.setPriceRmb(
					exchangeRate.multiply(poInvoiceDetailDO.getPrice()).setScale(4, BigDecimal.ROUND_HALF_UP));
			poInvoiceDetailDO.setAmount(poInvoiceDetailDO.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
			poInvoiceDetailDO.setAmountRmb(
					exchangeRate.multiply(poInvoiceDetailDO.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
			poInvoiceDetailDO.setStatus(1);

			// <!--add by WuWeiDong 20230829 bug 11912 若有删除的恢复，并根据ID更新最新数据， -->
			LambdaQueryWrapper<PoInvoiceDetailDO> queryCancelWrapper = new LambdaQueryWrapper<>();
			queryCancelWrapper.eq(PoInvoiceDetailDO::getInvoiceId, poInvoiceDetailDO.getInvoiceId())
					.eq(PoInvoiceDetailDO::getOrderNo, poInvoiceDetailDO.getOrderNo())
					.eq(PoInvoiceDetailDO::getStatus, "9")
					.eq(PoInvoiceDetailDO::getQuantity, poInvoiceDetailDO.getQuantity())
					.select(PoInvoiceDetailDO::getId);

			PoInvoiceDetailDO CancelDO = poInvoiceDetailMapper.selectOne(queryCancelWrapper);
			if (PublicUtil.isNotEmpty(CancelDO)) {
				poInvoiceDetailDO.setId(CancelDO.getId());
				this.poInvoiceDetailMapper.updateById(poInvoiceDetailDO);
			} else {
				this.poInvoiceDetailMapper.insert(poInvoiceDetailDO);
			}

			count++;
			// 无商业价值的不加入
			if (poInvoiceDetailDO.getImpType() == 0) {
				sumAmount = BigDecimalUtil.add(sumAmount, poInvoiceDetailDO.getAmount());
				sumAmountRmb = BigDecimalUtil.add(sumAmountRmb, poInvoiceDetailDO.getAmountRmb());
			}
		}
        Long poInvoiceId = details.get(0).getInvoiceId();
        if(Objects.nonNull(mergeInvoiceDto)){
            poInvoiceId = mergeInvoiceDto.getMergeInvoiceId();
        }

		LambdaUpdateWrapper<OpsPoInvoiceDO> updateMasterWrapper = new LambdaUpdateWrapper<>();
		updateMasterWrapper.eq(OpsPoInvoiceDO::getInvoiceId, poInvoiceId);
		OpsPoInvoiceDO opsPoInvoiceDO = new OpsPoInvoiceDO();

		opsPoInvoiceDO.setCurrencyCode(invoiceMasterDO.getCurrency());
		opsPoInvoiceDO.setExchangeRate(exchangeRate);
		if ((PublicUtil.isNotEmpty(invoiceMasterDO.getInvoiceType()) && invoiceMasterDO.getInvoiceType() == 3)
				|| invoiceMasterDO.getAmount() == null) {
			opsPoInvoiceDO.setAmount(sumAmount);
			opsPoInvoiceDO.setAmountRmb(sumAmountRmb);
		} else {
			opsPoInvoiceDO.setAmount(invoiceMasterDO.getAmount());
			opsPoInvoiceDO.setAmountRmb(
					invoiceMasterDO.getAmount().multiply(exchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		// BigDecimal
		// amountRmb=Optional.ofNullable(invoiceMasterDO.getAmount()).orElse(BigDecimal.ZERO).multiply(opsPoInvoiceDO.getExchangeRate()).setScale(2,BigDecimal.ROUND_HALF_UP);

		opsPoInvoiceDO.setCustomsFee(Optional.ofNullable(invoiceMasterDO.getCustomsFee()).orElse(BigDecimal.ZERO)
				.setScale(2, BigDecimal.ROUND_HALF_UP));
		opsPoInvoiceDO.setTransFee(Optional.ofNullable(invoiceMasterDO.getTransFee()).orElse(BigDecimal.ZERO)
				.setScale(2, BigDecimal.ROUND_HALF_UP));
		opsPoInvoiceDO.setOtherFee(Optional.ofNullable(invoiceMasterDO.getOtherFee()).orElse(BigDecimal.ZERO)
				.setScale(2, BigDecimal.ROUND_HALF_UP));
		opsPoInvoiceDO.setVatFee(Optional.ofNullable(invoiceMasterDO.getVatFee()).orElse(BigDecimal.ZERO).setScale(2,
				BigDecimal.ROUND_HALF_UP));
		opsPoInvoiceDO.setExcisetax(Optional.ofNullable(invoiceMasterDO.getExciseTax()).orElse(BigDecimal.ZERO)
				.setScale(2, BigDecimal.ROUND_HALF_UP));
		poInvoiceMasterMapper.update(opsPoInvoiceDO, updateMasterWrapper);
		// poInvoiceMasterMapper.updateOpsPoInvoiceAmount(details.get(0).getInvoiceId());
		// 更新明细人民币金额
		// poInvoiceDetailMapper.updatePOInvoiceDetailRMBAmount(details.get(0).getInvoiceId(),
		// opsPoInvoiceDO.getExchangeRate());
		// 更新明细合计
		poInvoiceDetailMapper.updatePoInvoiceDetailAmountTotal(poInvoiceId);
		// 更新合计
		poInvoiceMasterMapper.updatepoInvoiceMasterAmountTotal(poInvoiceId);

		return true;
	}

	/**
	 * 发票转成本结算
	 *
	 * @param invoiceId
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> copyToInvoicedetailPack(Long invoiceId) {
		try {
			ImpInvoiceMasterDO inoviceMasterDO = impInvoiceMasterMapper.selectById(invoiceId);
			if (inoviceMasterDO == null) {
				return ResultVo.failure("该发票不存在" + invoiceId);
			}
			if (!(inoviceMasterDO.getStatus() == 1 || inoviceMasterDO.getStatus() == 2
					|| inoviceMasterDO.getStatus() == 10)) {
				return ResultVo.failure("当前状态不可以复制分包明细" + inoviceMasterDO.getInvoiceNo());
			}

			// 查找所有明细
			LambdaQueryWrapper<ImpInvoiceDetailDO> queryDetailWrapper = new LambdaQueryWrapper<>();
			queryDetailWrapper.eq(ImpInvoiceDetailDO::getInvoiceId, invoiceId)
					.orderByAsc(ImpInvoiceDetailDO::getFullOrderNo);
			List<ImpInvoiceDetailDO> details = this.impInvoiceDetailMapper.selectList(queryDetailWrapper);

			int count = 0;
			LambdaQueryWrapper<ImpInvoiceDetailPackDO> queryPackWrapper = new LambdaQueryWrapper<>();
			String fullOrderNo = "";
			ImpInvoiceDetailPackDO packDO;

			for (ImpInvoiceDetailDO info : details) {
				// 重复完整订单号第一次判断不存在的都加进去，第一次存在都不加
				queryPackWrapper.clear();
				if (StringUtils.isBlank(info.getBarcode())) {
					if (!fullOrderNo.equals(info.getFullOrderNo())) {
						queryPackWrapper.eq(ImpInvoiceDetailPackDO::getInvoiceId, info.getInvoiceId())
								.eq(ImpInvoiceDetailPackDO::getPoNo, info.getPoNo())
								.eq(ImpInvoiceDetailPackDO::getPoItemNo, info.getPoItemNo())
								.eq(ImpInvoiceDetailPackDO::getFullOrderNo, info.getFullOrderNo());
						// 判断分包数据中是否存在该订单数据，存在则跳过
						if (impInvoiceDetailPackMapper.selectCount(queryPackWrapper) > 0) {
							continue;
						}
						fullOrderNo = info.getFullOrderNo();
					}
				} else {
					queryPackWrapper.eq(ImpInvoiceDetailPackDO::getInvoiceId, info.getInvoiceId())
							.eq(ImpInvoiceDetailPackDO::getBarcode, info.getBarcode());
					if (impInvoiceDetailPackMapper.selectCount(queryPackWrapper) > 0) {
						continue;
					}
				}

				// 不存在则复制到分包数据中
				packDO = new ImpInvoiceDetailPackDO();
				packDO.setInvoiceId(info.getInvoiceId());
				packDO.setOrderNo(info.getOrderNo());
				packDO.setItemNo(info.getItemNo());
				packDO.setPoNo(info.getPoNo());
				packDO.setPoItemNo(info.getPoItemNo());
				packDO.setSplitItemNo(info.getSplitItemNo());
				packDO.setFullOrderNo(info.getFullOrderNo());
				packDO.setImpOrderNo(String.format("%s-%s", info.getOrderNo(), info.getItemNo()));
				packDO.setCaseNo(info.getCaseNo());
				packDO.setBarcode(info.getBarcode());
				packDO.setCurrency(info.getCurrency());
				packDO.setEnName(info.getEnName());
				// 来源代码
				packDO.setFromCode(info.getFromCode());
				packDO.setInvoiceNo(info.getInvoiceNo());
				packDO.setModelNo(info.getModelNo());
				packDO.setOrderType(info.getOrderType());
				packDO.setOrigin(info.getOrigin());
				packDO.setPrice(info.getPrice());
				packDO.setProductCode(info.getProductCode());
				packDO.setQuantity(info.getQuantity());
				packDO.setRemark("fromINV");
				packDO.setShipDate(info.getShipDate());
				packDO.setShipMethod(info.getShipMethod());
				packDO.setStatus(info.getStatus());
				packDO.setSupplierCode(info.getSupplierCode());
				packDO.setWeight(info.getWeight());
				packDO.setShelfCode(info.getShelfCode());
				packDO.setRoHSCode(info.getRoHSCode());
				packDO.setSnCode(info.getSnCode());
				// packDO.setCreateUser(SMCApp.getLoginAuthDto().getUserNo());
				packDO.setCreateUser("fromInv");
				Boolean aBoolean = addImpInvoiceDetailPack(packDO);
				if (!aBoolean) {
					return ResultVo.failure();
				}
				count++;
			}
			if (count > 0) {
				ImpInvoiceMasterDO updatemaster = new ImpInvoiceMasterDO();
				updatemaster.setId(invoiceId);
				updatemaster.setDataType(3);
				impInvoiceMasterMapper.updateById(updatemaster);
			}
			return ResultVo.success(String.valueOf(count), "复制到分包数据成功！" + count);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * 写入 Imp_invoice_detail_pack
	 */
	public Boolean addImpInvoiceDetailPack(ImpInvoiceDetailPackDO impInvoiceDetailPackDO) {
		if (impInvoiceDetailPackDO == null) {
			return false;
		}
		if (impInvoiceDetailPackDO.getBarcode() == null || "".equals(impInvoiceDetailPackDO.getBarcode())) {
			ResultVo<String> stringResultVo = commonServiceFeignApi.generatorBillNo("20");
			if (!stringResultVo.isSuccess() || stringResultVo.getData() == null) {
				return false;
			}
			impInvoiceDetailPackDO.setBarcode(stringResultVo.getData());
		}
		if (PublicUtil.isEmpty(impInvoiceDetailPackDO.getCaseNo())) {
			impInvoiceDetailPackDO.setCaseNo("000");
		}
		// 香港入库型号去掉L-
		// if ("HK".equals(impInvoiceDetailPackDO.getSupplierCode()) &&
		// impInvoiceDetailPackDO.getModelNo().startsWith("L-")) {
		// impInvoiceDetailPackDO.setModelNo(impInvoiceDetailPackDO.getModelNo().substring(2));
		// }

		// 超过30的型号,取采购表里面的型号替换
		// if (impInvoiceDetailPackDO.getModelNo().length() == 30 ||
		// impInvoiceDetailPackDO.getModelNo().length() == 29) {
		// QueryWrapper<OpsPurchaseInvoiceDO> queryWrapper = new
		// QueryWrapper<>();
		// queryWrapper.eq("poNo", impInvoiceDetailPackDO.getPoNo());
		// queryWrapper.eq("lineItem", impInvoiceDetailPackDO.getPoItemNo());
		// OpsPurchaseInvoiceDO poOrderDO =
		// this.getPurchaseInvoiceByWrapper(queryWrapper);
		// if (null != poOrderDO && poOrderDO.getModelNo().length() > 30) {
		// impInvoiceDetailPackDO.setModelNo(poOrderDO.getModelNo());
		// }
		// }

		// 重量为空或者0时，查product_physics表获取型号的重量
		if (impInvoiceDetailPackDO.getWeight() == null || impInvoiceDetailPackDO.getWeight() == 0) {
			ResultVo<ProductPhysicsVO> resultVo = productServiceFeignApi
					.getWeightByModelNo(impInvoiceDetailPackDO.getModelNo());
			if (resultVo.isSuccess()) {

				// <!--add by WuWeiDong 20230828 bug 11752 量重空指针错误 -->
				impInvoiceDetailPackDO.setWeight(
						Optional.ofNullable(resultVo.getData().getWeight()).orElse(BigDecimal.ZERO).doubleValue());
			}
		}

		try {
			impInvoiceDetailPackMapper.insert(impInvoiceDetailPackDO);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 查询入库数据
	 *
	 * @param request
	 * @return
	 */
	@Override
	public ResultVo<PageInfo<ImpDataVO>> listImpdata(ImpdataRequest request) {
		LambdaQueryWrapper<ImpDataDO> query = new LambdaQueryWrapper<>();
		if (request.getTodate() != null) {
			request.setTodate(DateUtil.addDay(request.getTodate(), 1));
		}

		query.eq(PublicUtil.isNotEmpty(request.getOrderNo()), ImpDataDO::getOrderNo, request.getOrderNo())
				.eq(PublicUtil.isNotEmpty(request.getInvoiceNo()), ImpDataDO::getInvoiceNo, request.getInvoiceNo())
				.eq(PublicUtil.isNotEmpty(request.getOptCode()), ImpDataDO::getOptCode, request.getOptCode())
				.like(PublicUtil.isNotEmpty(request.getModelNo()), ImpDataDO::getModelNo, request.getModelNo())
				.ge(request.getFromdate() != null, ImpDataDO::getImpdate, request.getFromdate())
				.lt(request.getTodate() != null, ImpDataDO::getImpdate, request.getTodate());
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		List<ImpDataDO> list = impdataMapper.selectList(query);
		PageInfo<ImpDataDO> pageInfo = PageInfo.of(list);
		PageInfo<ImpDataVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, ImpDataVO.class);
		return ResultVo.success(voPageInfo);
	}

	/**
	 * 传递数据给采购接口和更新订单状态
	 *
	 * @return
	 */
	public ResultVo<String> transImpInvoiceDetail1(ImpInvoiceMasterDO masterDO, List<ImpInvoiceDetailVO> invoiceDetails,
			boolean isFromPackData) {

		Date impDate = new Date();
		Date esArrivateDate = masterDO.getPreArriveDate();
		Date shipDate = masterDO.getShipDate();
		String stateDesc;
		OrderStateVO orderStateVO = new OrderStateVO();

		boolean receivedGoods = false;
		if (masterDO.getArriveDate() != null) {
			receivedGoods = true;
		}
		if (shipDate == null) {
			shipDate = DateUtil.getToday();// 暂时默认导入的当天
		}
		if (esArrivateDate == null) {
			esArrivateDate = orderStateService.calcEsArrivalDate("JP", masterDO.getTransType(), shipDate);
		}
		// String todayStr = DateUtil.getStringToday();
		String esArriveDateStr = "";
		String arriveDateStr = "";
		if (receivedGoods) {
			arriveDateStr = DateUtil.dateToDateString(masterDO.getArriveDate());
		}

		if (esArrivateDate != null) {
			esArriveDateStr = DateUtil.dateToDateString(esArrivateDate);
		}
		if (receivedGoods)// 已收货
		{
			stateDesc = masterDO.getShipment() + masterDO.getInvoiceNo() + "已收货" + arriveDateStr;
			orderStateVO.setActArrivalDate(masterDO.getArriveDate());
			orderStateVO.setStateCode(41);
		} else {
			stateDesc = masterDO.getShipment() + DateUtil.dateToDateString(shipDate) + "已发出" + masterDO.getInvoiceNo()
					+ ",预计达到日期" + esArriveDateStr;
			orderStateVO.setStateCode(31);
		}
		orderStateVO.setStateDes(stateDesc);
		orderStateVO.setProvider(masterDO.getSupplierCode());
		orderStateVO.setSupplierCode(masterDO.getSupplierCode());
		orderStateVO.setStateDate(shipDate);
		orderStateVO.setEsArrivalDate(esArrivateDate);
		orderStateVO.setPoInvoiceNo(masterDO.getInvoiceNo());

		// 分包数据不发送预到货
		if (isFromPackData == false) {
			Map<String, PurchaseReplyInfo> detailMaps = new HashMap<>(invoiceDetails.size());
			PurchaseReplyInfo purchaseReplyInfo;

			for (ImpInvoiceDetailVO orderVo : invoiceDetails) {
				if ("99".equals(orderVo.getOrderType())) {
					continue;
				}
				String key = orderVo.getPoNo() + "~" + orderVo.getPoItemNo();
				if (detailMaps.containsKey(key)) {
					purchaseReplyInfo = detailMaps.get(key);
					purchaseReplyInfo.setQtyTrans(purchaseReplyInfo.getQtyTrans() + orderVo.getQuantity());
				} else {
					purchaseReplyInfo = new PurchaseReplyInfo();
					purchaseReplyInfo.setPono(orderVo.getPoNo());
					purchaseReplyInfo.setLineitem(orderVo.getPoItemNo());
					purchaseReplyInfo.setInvoiceid(masterDO.getId());
					purchaseReplyInfo.setTranstype(masterDO.getTransType());
					purchaseReplyInfo.setImpdate(impDate);
					purchaseReplyInfo.setInvoiceno(orderVo.getInvoiceNo()); // 原始发票号
					purchaseReplyInfo.setModelno(orderVo.getModelNo());
					// bug13550 调用updateinvoice使用主表供应商信息
					purchaseReplyInfo.setSupplierid(masterDO.getSupplierCode());
					purchaseReplyInfo.setQtyTrans(orderVo.getQuantity()); // 运输中数量
					detailMaps.put(key, purchaseReplyInfo);
				}
			}
			log.info("{}", JSON.toJSONString(detailMaps.values()));

			try {
				CommonResult<String> updateInvoice = wmPurchaseFeignApi
						.updateInvoice(new ArrayList<>(detailMaps.values()));
				if (!updateInvoice.isSuccess()) {
					log.error("updateInvoice params = {}", JSON.toJSONString(detailMaps.values()));
					log.error("调用BJ发票接口失败导入失败: {}", updateInvoice.getMessage());
				}
			} catch (Exception e) {
				log.error("调用BJ发票接口失败updateInvoice：" + e.getMessage());
				return ResultVo.failure(e.getMessage());
			}
		}
		// 发送订单状态
		this.sendOrderState(invoiceDetails, orderStateVO);

		// 调用采购接口
		return ResultVo.success();
	}

	public ResultVo<String> toUpdateOrderState(ImpInvoiceMasterDO masterDO, List<ImpInvoiceDetailVO> invoiceDetails) {

		Date esArrivateDate = masterDO.getPreArriveDate();
		Date shipDate = masterDO.getShipDate();
		// String stateDesc;
		OrderStateVO orderStateVO = new OrderStateVO();

		if (shipDate == null) {
			shipDate = DateUtil.getToday();// 暂时默认导入的当天
		}

		StringBuilder sbStateDes = new StringBuilder();

		if (esArrivateDate == null) {
			esArrivateDate = orderStateService.calcEsArrivalDate(masterDO.getSupplierCode(), masterDO.getTransType(),
					shipDate);
		}

		if (masterDO.getStatus().equals(3))// 已发票入库
		{
			orderStateVO.setStateCode(OrderStateEnum.InvoiceImpStock.getCode());
			if (masterDO.getArriveDate() != null)// 已收货
			{
				sbStateDes.append(DateUtil.dateToDateString(masterDO.getArriveDate())).append("已收货")
						.append(masterDO.getInvoiceNo()).append(" ");
				orderStateVO.setActArrivalDate(masterDO.getArriveDate());
				orderStateVO.setStateCode(OrderStateEnum.ReceivedPOOrder.getCode());
				orderStateVO.setStateDate(masterDO.getArriveDate());
			} else {
				if (masterDO.getConfirmDate() != null) {
					sbStateDes.append(DateUtil.dateToDateString(masterDO.getConfirmDate()));
					orderStateVO.setStateDate(masterDO.getConfirmDate());
				}
				sbStateDes.append("已发票入库");
				if (esArrivateDate != null) {
					sbStateDes.append(",预计到货日期").append(DateUtil.dateToDateString(esArrivateDate));
				}
			}
			sbStateDes.append(" ").append(masterDO.getInvoiceNo());
		} else {
			if (PublicUtil.isNotEmpty(masterDO.getShipment())) {
				sbStateDes.append(masterDO.getShipment()).append(" ");
			} else {
				sbStateDes.append(masterDO.getSupplierCode()).append(" ");
			}

			sbStateDes.append(DateUtil.dateToDateString(shipDate)).append("已发出").append(masterDO.getInvoiceNo())
					.append(" ");
			if (esArrivateDate != null) {
				sbStateDes.append(",预计到货日期").append(DateUtil.dateToDateString(esArrivateDate));
			}
			orderStateVO.setStateCode(OrderStateEnum.SupplierShipped.getCode());
			orderStateVO.setStateDate(shipDate);

			// add by A78027 from bug11091 in 20230625
			if (masterDO.getCustomsDate() != null) {
				orderStateVO.setStateCode(OrderStateEnum.InvoiceDeclaredCustoms.getCode());
				sbStateDes.append(",").append(DateUtil.dateToDateString(masterDO.getCustomsDate())).append("已报关");
			}
		}
		orderStateVO.setStateDes(sbStateDes.toString());
		orderStateVO.setProvider(masterDO.getSupplierCode());
		orderStateVO.setSupplierCode(masterDO.getSupplierCode());
		orderStateVO.setEsArrivalDate(esArrivateDate);
		orderStateVO.setPoInvoiceNo(masterDO.getInvoiceNo());
		orderStateVO.setPoShipDate(shipDate);
		// add by A78027 20230411 bug 10270
		// 国内(三方)工厂发票，出厂日=发货日
		if (masterDO.getInvoiceType() != null && (masterDO.getInvoiceType() == 3 || masterDO.getInvoiceType() == 4)) {
			orderStateVO.setPoFacExpdate(shipDate);
		}

        // add by lyc 20240506 14402bug  增加传输报关日期、到港日期
        orderStateVO.setCustomsDate(masterDO.getCustomsDate());
        orderStateVO.setPortArriveDate(masterDO.getPortArrivedate());

		// 发送订单状态
		this.sendOrderState(invoiceDetails, orderStateVO);

		return ResultVo.success();
	}

	private String convertCNMSupplierCode(String code) {
		switch (code) {
		case "BJ":
			return "CM";
		case "CN":
			return "CN";
		case "SH":
			return "TZ";
		case "TJ":
			return "CT";
		default:
			return code;
		}
	}

	/**
	 * 北京制造发货录入发票分包数据
	 *
	 * @return
	 */
	// @Transactional(rollbackFor = Exception.class)
	public ResultVo<String> syncImpCNInvoicePack(String optTime, Integer type) {

//		if (!redissonUtil.tryLock(Constants.REDIS_KEY_IMP_CN_INVOICE, 1, 1200)) {
//			return ResultVo.failure("导入中");
//		}

        // 判断是否锁住
        if (redissonUtil.isLock(Constants.REDIS_KEY_IMP_CN_INVOICE)) {
            return ResultVo.failure("导入中");
        }
        // 加锁  过期:30分钟
        redissonUtil.lock(Constants.REDIS_KEY_IMP_CN_INVOICE, 1800);

		try {
			// 1.查找北京发货中间表
			List<InvoiceNoAndShipDateVO> nolist;
			String returnInvoiNos = "";
			// if (type == 1) {
			nolist = poInvoiceService.listOPSTExportIvoiceNo(optTime);
			if (nolist == null || nolist.isEmpty()) {
				return ResultVo.success(returnInvoiNos);
			}
			Map<String, String> supplierNameMap = new HashMap<>();
			List<OPSTExportRequestToSalesDO> list;
			List<ImpInvoiceDetailVO> invoiceDetails;
			ImpInvoiceMasterDO masterDO;

			for (InvoiceNoAndShipDateVO vo : nolist) {
				list = poInvoiceService.listExportRequestToSales(vo);
				masterDO = new ImpInvoiceMasterDO();
				masterDO.setSupplierCode(convertCNMSupplierCode(list.get(0).getInvfrom()));
				if (StringUtils.isNotBlank(masterDO.getSupplierCode())
						&& !supplierNameMap.containsKey(masterDO.getSupplierCode())) {
					supplierNameMap.put(masterDO.getSupplierCode(),
							commonMapper.getSupplierNameByid(masterDO.getSupplierCode()));
				}
				masterDO.setShipment(supplierNameMap.getOrDefault(masterDO.getSupplierCode(), ""));
				// 录入发票入库数据
				invoiceDetails = impInvoiceService.addBJImpInvoiceData(masterDO, list);
				if (invoiceDetails != null) {
					toProcessInvoice(masterDO, invoiceDetails);
					returnInvoiNos += masterDO.getInvoiceNo() + ";";
				}
			}
			return ResultVo.success(returnInvoiNos);
		} finally {
			redissonUtil.unlock(Constants.REDIS_KEY_IMP_CN_INVOICE);
		}
	}

	private ImpInvoiceDetailDO getImpInvoiceDetailId(Long invoiceId, String barcode) {
		QueryWrapper<ImpInvoiceDetailDO> detailQuery = new QueryWrapper<>();
		detailQuery.select("top 1 id");
		detailQuery.eq("invoice_id", invoiceId).eq("barcode", barcode);
		return impInvoiceDetailMapper.selectOne(detailQuery);
	}

	private Integer getImpInvoiceDetailByBarCode(String barcode) {
		QueryWrapper<ImpInvoiceDetailDO> detailQuery = new QueryWrapper<>();
		detailQuery.eq("barcode", barcode);
		return impInvoiceDetailMapper.selectCount(detailQuery);
	}

	private Integer getImpInvoiceDetailPackByBarCode(String barcode) {
		QueryWrapper<ImpInvoiceDetailPackDO> detailPackQuery = new QueryWrapper<>();
		detailPackQuery.eq("barcode", barcode);
		return impInvoiceDetailPackMapper.selectCount(detailPackQuery);
	}

	private boolean updateImpInvoiceQty(Long invoiceId) {
		ImpInvoiceMasterDO masterDO = impInvoiceDetailMapper.getTotalImpInvoiceDetail(invoiceId);
		ImpInvoiceMasterDO updMasterDo = new ImpInvoiceMasterDO();
		updMasterDo.setId(invoiceId);
		updMasterDo.setTotalQty(masterDO.getTotalQty());
		updMasterDo.setOrderQty(masterDO.getOrderQty());
		updMasterDo.setBoxQty(masterDO.getBoxQty());
		updMasterDo.setWeight(masterDO.getWeight());
		updMasterDo.setAmount(masterDO.getAmount());
		return impInvoiceMasterMapper.updateById(updMasterDo) == 1;
	}

	@Transactional(rollbackFor = Exception.class)
	public List<ImpInvoiceDetailVO> addBJImpInvoiceData(ImpInvoiceMasterDO masterDO,
			List<OPSTExportRequestToSalesDO> itemlist) {

		boolean isNewMaster = true;
		// BigDecimal sumAmount = BigDecimal.ZERO;
		// for (OPSTExportRequestToSalesDO info : itemlist) {
		// info.setAmount(info.getUnitPrice().multiply(new
		// BigDecimal(info.getQuantity().toString())));
		// sumAmount = sumAmount.add(info.getAmount());
		// }
		OPSTExportRequestToSalesDO oneItem = itemlist.get(0);
		String saleType = oneItem.getSaleType();
		masterDO.setInvoiceNo(oneItem.getInvoiceNo());
		masterDO.setShipDate(DateUtil.getDate(oneItem.getExecuteTime()));
		// add by A78027 20230411 bug 10398
		masterDO.setInvoiceDate(DateUtil.getDate(oneItem.getExecuteTime()));
		// add by A78027 20230411 bug 10180
		masterDO.setPreArriveDate(DateUtil.getToday());

		ImpInvoiceMasterDO exitMasterDO = getExistImpInvoiceMasterByInvoiceNo(masterDO.getSupplierCode(),
				masterDO.getInvoiceNo(), masterDO.getShipDate());

		if (exitMasterDO != null) {
			if (exitMasterDO.getStatus() == 3 || exitMasterDO.getStatus() == 6 || exitMasterDO.getStatus() == 9) {
				return null;
			}
			// //总价格一样无须处理
			// if (exitMasterDO.getAmount() != null &&
			// exitMasterDO.getAmount().compareTo(sumAmount) == 0) {
			// return null;
			// }
			masterDO.setId(exitMasterDO.getId());
			isNewMaster = false;
		}
		masterDO.setUpdateUser("CNIMP");
		masterDO.setCurrency("CNY");
		masterDO.setExchangeRate(new BigDecimal("1"));
		masterDO.setTransType("3");
		if ("三方".equalsIgnoreCase(saleType)) {
			if (masterDO.getId() == null) {
				masterDO.setStatus(1);
			}
			masterDO.setInvoiceType(4);
			masterDO.setDataType(2);
		} else {
			masterDO.setInvoiceType(3);
			masterDO.setDataType(1);
			masterDO.setStatus(2);
		}
		// masterDO.setAmountRmb(sumAmount);
		// masterDO.setAmount(sumAmount);
		// masterDO.setVatFee(taxamount);
		masterDO.setUpdateTime(new Date());

		// 获取采购单收货仓库
		String customerNo = itemlist.get(0).getCustomerNo();
		masterDO.setRemark("BJ制造发货数据" + customerNo);
		String receiveWarehouseCode;

		// if (!"TZ".equals(masterDO.getSupplierCode())) {
		if ("TZ".equalsIgnoreCase(masterDO.getSupplierCode())) {
			receiveWarehouseCode = this.getWarehouseCodeBySMCCode(customerNo, 3);
		} else {
			receiveWarehouseCode = this.getWarehouseCodeBySMCCode(customerNo, 2);
		}
		// }
		if (StringUtils.isBlank(receiveWarehouseCode)) {
			String orderNo = itemlist.get(0).getOrderNo().trim();
			OrderNoInfo masterorderNoInfo;
			if (orderNo.contains("-")) {
				String poNo = orderNo.substring(0, orderNo.length() - 1);
				String poItem = orderNo.substring(orderNo.length() - 1, orderNo.length());
				masterorderNoInfo = new OrderNoInfo().convertPOOrder(poNo, poItem);
			} else {
				masterorderNoInfo = new OrderNoInfo().convertFullOrderNo(orderNo);
			}
			receiveWarehouseCode = impInvoiceMasterMapper.getReceiveWarehouseCode(masterorderNoInfo.getPoNo());
		}
		masterDO.setReceiveWarehouseCode(receiveWarehouseCode);

		// if ("9501200".equals(customerNo) || "9501282".equals(customerNo) ||
		// "9501211".equals(customerNo)) {
		// masterDO.setReceiveWarehouseCode("KBJ");
		// } else {
		// receiveWarehouseCode = this.getWarehouseCodeBySMCCode(customerNo, 2);
		// if (StringUtils.isBlank(receiveWarehouseCode)) {
		// OrderNoInfo masterorderNoInfo = new
		// OrderNoInfo().convertCNOrder(itemlist.get(0).getOrderNo());
		// receiveWarehouseCode =
		// impInvoiceMasterMapper.getReceiveWarehouseCode(masterorderNoInfo.getPoNo());
		// if (StringUtils.isNotBlank(receiveWarehouseCode)) {
		// masterDO.setReceiveWarehouseCode(receiveWarehouseCode);
		// }
		// } else {
		// masterDO.setReceiveWarehouseCode(receiveWarehouseCode);
		// }
		// }

		// 3.保存到导入发票主表
		if (masterDO.getId() == null) {
			masterDO.setCreateUser("CNIMP");
			masterDO.setCreateTime(new Date());
			String billNo = commonServiceFeignApi.generatorBillNo("21").getData();
			if (PublicUtil.isEmpty(billNo)) {
				return null;
			}
			masterDO.setId(Long.parseLong(billNo));
			impInvoiceMasterMapper.insert(masterDO);
		} else {
			impInvoiceMasterMapper.updateById(masterDO);
		}

		List<ImpInvoiceDetailVO> invoiceDetails = new ArrayList<>(itemlist.size());
		ImpInvoiceDetailDO detailDO;
		OrderNoInfo orderNoInfo;
		for (OPSTExportRequestToSalesDO item : itemlist) {
			detailDO = new ImpInvoiceDetailDO();
			detailDO.setInvoiceNo(masterDO.getInvoiceNo());
			detailDO.setInvoiceId(masterDO.getId());
			if (PublicUtil.isEmpty(item.getBillNo())) {
				String billNo = commonServiceFeignApi.generatorBillNo("20").getData();
				detailDO.setBarcode(billNo);
			} else {
				detailDO.setBarcode(item.getBillNo());
			}
			// if (!isNewMaster) {
			// ImpInvoiceDetailDO existDetailDO =
			// getImpInvoiceDetailId(masterDO.getId(), detailDO.getBarcode());
			// if (existDetailDO != null) {
			// detailDO.setId(existDetailDO.getId());
			// }
			// }
			// barcode存在则不导入
			int barcodeCount;
			if (masterDO.getInvoiceType() == 4) {
				barcodeCount = getImpInvoiceDetailPackByBarCode(detailDO.getBarcode());
			} else {
				barcodeCount = getImpInvoiceDetailByBarCode(detailDO.getBarcode());
			}
			if (barcodeCount > 0) {
				continue;
			}
			detailDO.setModelNo(item.getModelNo());
			detailDO.setQuantity(item.getQuantity());
			detailDO.setPrice(item.getUnitPrice());
			BigDecimal amountDetail = item.getAmount();
			if (Optional.ofNullable(amountDetail).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) == 0) {
				amountDetail = BigDecimalUtil.mul(item.getQuantity(), item.getUnitPrice());
			}
			detailDO.setAmount(amountDetail);
			if (item.getOrderNo().length() < 10) {
				continue;
			}
			detailDO.setImpOrderNo(item.getOrderNo().trim());
			detailDO.setImpModelNo(item.getModelNo());
			orderNoInfo = new OrderNoInfo().convertCNOrder(item.getOrderNo().trim());
			detailDO.setOrderNo(orderNoInfo.getOrderNo());
			detailDO.setItemNo(orderNoInfo.getItemNo());
			detailDO.setSplitItemNo(orderNoInfo.getSplitItem());
			detailDO.setPoNo(orderNoInfo.getPoNo());
			detailDO.setPoItemNo(orderNoInfo.getPoItemNo());
			detailDO.setFullOrderNo(orderNoInfo.getFullOrderNo());
			detailDO.setCaseNo(item.getPaiNo());
			detailDO.setWeight(item.getUnitWeight());
			detailDO.setSupplierCode(convertCNMSupplierCode(item.getInvfrom()));
			detailDO.setShipDate(item.getExecuteTime());
			detailDO.setCurrency(masterDO.getCurrency());
			detailDO.setOverseaInvoiceNo(item.getFapiaoNo());
			detailDO.setSnCode(item.getSnCODE());
			detailDO.setStatus(1);
			detailDO.setRemark("BJ制造发货数据");
			detailDO.setUpdateTime(new Date());

			// 超过30的型号,取采购表里面的型号替换
			// if (detailDO.getModelNo().length() == 30 ||
			// detailDO.getModelNo().length() == 29) {
			// QueryWrapper<OpsPurchaseInvoiceDO> queryWrapper = new
			// QueryWrapper<>();
			// queryWrapper.eq("poNo", detailDO.getPoNo());
			// queryWrapper.eq("lineItem", detailDO.getPoItemNo());
			// OpsPurchaseInvoiceDO poOrderDO =
			// this.getPurchaseInvoiceByWrapper(queryWrapper);
			// if (null != poOrderDO && poOrderDO.getModelNo().length() > 30) {
			// detailDO.setModelNo(poOrderDO.getModelNo());
			// }
			// }

			if (detailDO.getId() == null) {
				detailDO.setCreateUser("CNIMP");
				detailDO.setCreateTime(new Date());
				if (masterDO.getInvoiceType() == 4) {
					ImpInvoiceDetailPackDO detailPackDO = BeanCopyUtil.copy(detailDO, ImpInvoiceDetailPackDO.class);
					impInvoiceDetailPackMapper.insert(detailPackDO);
				} else {
					impInvoiceDetailMapper.insert(detailDO);
				}

			} else {
				detailDO.setUpdateUser("CNIMP");
				if (masterDO.getInvoiceType() == 4) {
					ImpInvoiceDetailPackDO detailPackDO = BeanCopyUtil.copy(detailDO, ImpInvoiceDetailPackDO.class);
					impInvoiceDetailPackMapper.updateById(detailPackDO);
				} else {
					impInvoiceDetailMapper.updateById(detailDO);
				}
			}
			ImpInvoiceDetailVO detailVO = BeanCopyUtil.copy(detailDO, ImpInvoiceDetailVO.class);
			invoiceDetails.add(detailVO);
		}
		// 合计
		updateImpInvoiceQty(masterDO.getId());
		// 复制发票数据到分包数据里面
		if (masterDO.getInvoiceType() == 3) {
			copyToInvoicedetailPack(masterDO.getId());
		}
		return invoiceDetails;
	}

	/**
	 * 北京制造发票数据录入发票明细数据
	 *
	 * @return
	 */
	// @Transactional
	@Override
	public ResultVo<String> syncVExportImpCNInvoicePack(String optTime, Integer type) {
		// 1.查找北京发货中间表
		String returnInvoiNos = "";
		List<OPSVExportInvoicePriceToSalesDO> list;
		if (type == 1) {
			list = poInvoiceService.listOPSVExportIvoice(optTime);
		} else {
			list = poInvoiceService.listOPSVExportIvoiceTest(optTime);
		}

		// 2.按发票号分组，一个组一个发票主表
		Map<String, List<OPSVExportInvoicePriceToSalesDO>> listmap = list.stream()
				.collect(Collectors.groupingBy(OPSVExportInvoicePriceToSalesDO::getInvoiceNo));

		for (String key : listmap.keySet()) {
			List<OPSVExportInvoicePriceToSalesDO> itemlist = listmap.get(key);
			if (itemlist != null && !itemlist.isEmpty()) {
				// ImpInvoiceMasterDO masterDO = new ImpInvoiceMasterDO();
				// masterDO.setInvoiceNo(key);
				// masterDO.setSupplierCode(getSupperCode(itemlist.get(0).getInvfrom()));
				// masterDO.setShipDate(DateUtil.getDate(itemlist.get(0).getExpDate()));
				// masterDO.setInvoiceDate(itemlist.get(0).getInvoiceDate());
				// ImpInvoiceMasterDO exitMasterDO =
				// getExistImpInvoiceMasterByInvoiceNo(masterDO.getSupplierCode(),
				// masterDO.getInvoiceNo(), masterDO.getShipDate());
				// if (exitMasterDO != null) {
				// ImpInvoiceMasterDO updaMaster = new ImpInvoiceMasterDO();
				// updaMaster.setId(exitMasterDO.getId());
				// updaMaster.setInvoiceDate(masterDO.getInvoiceDate());
				// impInvoiceMasterMapper.updateById(updaMaster);
				// }
				ImpInvoiceMasterDO masterDO = new ImpInvoiceMasterDO();
				masterDO.setInvoiceNo(key);

				masterDO.setSupplierCode(convertCNMSupplierCode(itemlist.get(0).getInvfrom()));
				String supplierName = commonMapper.getSupplierNameByid(masterDO.getSupplierCode());
				masterDO.setShipment(supplierName);
				masterDO.setShipDate(DateUtil.getDate(itemlist.get(0).getExpDate()));
				masterDO.setInvoiceDate(itemlist.get(0).getInvoiceDate());
				// masterDO.setCInvoiceNo(itemlist.get(0).getBillNo());
				// 录入发票入库数据
				List<ImpInvoiceDetailVO> invoiceDetails = impInvoiceService.addBJVImpInvoice(masterDO, itemlist);
				if (invoiceDetails != null) {
					// 更新发货金额
					ImpInvoiceProcessDTO processDTO = new ImpInvoiceProcessDTO();
					processDTO.setInvoiceId(masterDO.getId());
					processDTO.setProcessType(6);
					this.sendInvoiceProcessMsgToMQ(processDTO);
					returnInvoiNos += masterDO.getInvoiceNo() + ";";
				}

				// if (invoiceDetails != null) {
				// //发送订单状态
				// OrderStateVO orderStateVO = new OrderStateVO();
				// String stateDesc;
				// stateDesc = masterDO.getShipment() + masterDO.getInvoiceNo()
				// + "已发票入库" + DateUtil.dateToDateString(new Date());
				// orderStateVO.setStateCode(36);
				// orderStateVO.setStateDes(stateDesc);
				// orderStateVO.setProvider(masterDO.getSupplierCode());
				// orderStateVO.setSupplierCode(masterDO.getSupplierCode());
				// orderStateVO.setStateDate(masterDO.getShipDate());
				// orderStateVO.setPoInvoiceNo(masterDO.getInvoiceNo());
				// sendOrderState(invoiceDetails, orderStateVO);
				// }
			}
		}
		return ResultVo.success(returnInvoiNos);
	}

	@Transactional(rollbackFor = Exception.class)
	public List<ImpInvoiceDetailVO> addBJVImpInvoice(ImpInvoiceMasterDO masterDO,
			List<OPSVExportInvoicePriceToSalesDO> itemlist) {
		// 合计发票总金额
		/*
		 * itemlist.stream().collect(Collectors.groupingBy(ImpInvoiceDetailDO::
		 * getPoNo,Collectors.groupingBy() ImpInvoiceDetailDO::getPoItemNo))..
		 */
		// BigDecimal amount =
		// itemlist.stream().map(OPSVExportInvoicePriceToSalesDO::getAmount).reduce(BigDecimal.ZERO,
		// BigDecimal::add);
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal taxamount = BigDecimal.ZERO;
		for (OPSVExportInvoicePriceToSalesDO info : itemlist) {
			amount = amount.add(info.getAmount());
			taxamount = taxamount.add(info.getAmount().multiply(info.getTaxRate()));
		}
		ImpInvoiceMasterDO exitMasterDO = getExistImpInvoiceMasterByInvoiceNo(masterDO.getSupplierCode(),
				masterDO.getInvoiceNo(), masterDO.getShipDate());

		if (exitMasterDO != null) {
			masterDO.setId(exitMasterDO.getId());
			if (!ImpInvoiceMasterStatusEnum.BECOST.getType().equals(exitMasterDO.getStatus())) {
				return null;
			}
			// 总价格一样无须处理
			if (exitMasterDO.getAmount().compareTo(amount) == 0) {
				return null;
			}
			// 删除明细项
			LambdaQueryWrapper<ImpInvoiceDetailDO> deletWrapper = new LambdaQueryWrapper<>();
			deletWrapper.eq(ImpInvoiceDetailDO::getInvoiceId, exitMasterDO.getId());
			impInvoiceDetailMapper.delete(deletWrapper);
		}
		masterDO.setUpdateUser("CNSales");
		masterDO.setStatus(4);
		masterDO.setCurrency("CNY");
		masterDO.setExchangeRate(new BigDecimal("1"));
		masterDO.setDataType(1);
		masterDO.setTransType("3");
		masterDO.setInvoiceType(5);
		masterDO.setAmountRmb(amount);
		masterDO.setAmount(amount);
		masterDO.setVatFee(taxamount);
		masterDO.setRemark("BJ制造发票数据");
		// 3.保存到导入发票主表
		if (masterDO.getId() == null) {
			masterDO.setCreateUser("CNSales");
			String billNo = commonServiceFeignApi.generatorBillNo("21").getData();
			if (PublicUtil.isEmpty(billNo)) {
				return null;
			}
			masterDO.setId(Long.parseLong(billNo));
			impInvoiceMasterMapper.insert(masterDO);
		} else {
			impInvoiceMasterMapper.updateById(masterDO);
		}

		List<ImpInvoiceDetailVO> invoiceDetails = new ArrayList<>(itemlist.size());
		ImpInvoiceDetailDO detailDO;
		OrderNoInfo orderNoInfo;

		for (OPSVExportInvoicePriceToSalesDO item : itemlist) {
			detailDO = new ImpInvoiceDetailDO();
			detailDO.setInvoiceNo(masterDO.getInvoiceNo());
			// String billNo =
			// commonServiceFeignApi.generatorBillNo("20").getData();
			// detailDO.setBarcode(billNo);
			detailDO.setModelNo(item.getModelNo());
			detailDO.setPrice(item.getPrice());
			detailDO.setQuantity(item.getQuantity());
			BigDecimal amountDetail = item.getAmount();
			if (Optional.ofNullable(amountDetail).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) == 0) {
				amountDetail = BigDecimalUtil.mul(item.getQuantity(), item.getPrice());
			}
			detailDO.setAmount(amountDetail);

			detailDO.setImpOrderNo(item.getOrderNo().trim());
			detailDO.setImpModelNo(item.getModelNo());
			if (item.getOrderNo().startsWith("YY")) {
				item.setOrderNo(item.getOrderNo().substring(2));
			}
			orderNoInfo = new OrderNoInfo().convertPOOrder(item.getOrderNo().trim(), item.getItemNo());
			detailDO.setOrderNo(orderNoInfo.getOrderNo());
			detailDO.setItemNo(orderNoInfo.getItemNo());
			detailDO.setSplitItemNo(orderNoInfo.getSplitItem());
			detailDO.setPoNo(orderNoInfo.getPoNo());
			detailDO.setPoItemNo(orderNoInfo.getPoItemNo());
			detailDO.setFullOrderNo(orderNoInfo.getFullOrderNo());

			detailDO.setInvoiceId(masterDO.getId());
			detailDO.setSupplierCode(convertCNMSupplierCode(item.getInvfrom()));
			detailDO.setShipDate(item.getExpDate());
			detailDO.setStatus(5);
			detailDO.setOverseaInvoiceNo(item.getBillNo());
			detailDO.setRemark("BJ制造发票数据");
			ImpInvoiceDetailVO detailVO = BeanCopyUtil.copy(detailDO, ImpInvoiceDetailVO.class);
			invoiceDetails.add(detailVO);
			impInvoiceDetailMapper.insert(detailDO);
		}
		return invoiceDetails;
	}

	/**
	 * 广州制造发货录入发票分包数据
	 *
	 * @return
	 */
	@Override
	public ResultVo<String> syncImpGZInvoicePack(String optDate) {

		if (!redissonUtil.tryLock(Constants.REDIS_KEY_IMP_GP_INVOICE, 1, 1200)) {
			return ResultVo.failure("导入中");
		}
		try {
			// 1.查找广州发货中间表
			String returnInvoiNos = "";
			LambdaQueryWrapper<ImpDataBJDO> query = new LambdaQueryWrapper<>();
			query.eq(ImpDataBJDO::getExpDesc, "GP").ge(ImpDataBJDO::getInsertTime, optDate)
					.isNotNull(ImpDataBJDO::getShipDate);
			List<ImpDataBJDO> list = impDataBJMapper.selectList(query);

			// 2.按发票号分组，一个组一个发票主表
			Map<String, List<ImpDataBJDO>> listmap = list.stream()
					.collect(Collectors.groupingBy(ImpDataBJDO::getInvoiceNo));

			List<ImpDataBJDO> itemlist;
			ImpInvoiceMasterDO masterDO;
			List<ImpInvoiceDetailVO> invoiceDetails;

			for (String key : listmap.keySet()) {

				itemlist = listmap.get(key);
				if (itemlist != null && !itemlist.isEmpty()) {
					masterDO = new ImpInvoiceMasterDO();
					masterDO.setInvoiceNo(key);
					masterDO.setSupplierCode("GZ");
					masterDO.setShipment("SMC（广州）");
					masterDO.setShipDate(DateUtil.getDate(itemlist.get(0).getShipDate()));
					masterDO.setPreArriveDate(masterDO.getShipDate());
					masterDO.setTransType("3");
					// masterDO.setCInvoiceNo(itemlist.get(0).getDeliveryNO());
					masterDO.setReceiveWarehouseCode(itemlist.get(0).getLogisticsCode());
					masterDO.setInvoiceDate(masterDO.getShipDate());

					// 录入发票入库数据
					invoiceDetails = impInvoiceService.addGZImpInvoiceData(masterDO, itemlist);

					if (invoiceDetails != null) {
						toProcessInvoice(masterDO, invoiceDetails);
						returnInvoiNos += masterDO.getInvoiceNo() + ";";
					}
				}
			}
			return ResultVo.success(returnInvoiNos);
		} finally {
			redissonUtil.unlock(Constants.REDIS_KEY_IMP_GP_INVOICE);
		}
	}

	private void toProcessInvoice(ImpInvoiceMasterDO masterDO, List<ImpInvoiceDetailVO> invoiceDetails) {
		// 更新采购发票在途状态
		// 发送订单状态
		/*
		 * OrderStateVO orderStateVO = new OrderStateVO(); Date shipDate =
		 * masterDO.getShipDate(); String stateDesc; Date esArrivateDate =
		 * masterDO.getPreArriveDate() == null ? DateUtil.addDay(shipDate, 2) :
		 * masterDO.getPreArriveDate(); if
		 * ("CN".equalsIgnoreCase(masterDO.getSupplierCode()) ||
		 * "CM".equalsIgnoreCase(masterDO.getSupplierCode())) { esArrivateDate =
		 * DateUtil.addDay(shipDate, 1); } stateDesc = masterDO.getShipment() +
		 * DateUtil.dateToDateString(shipDate) + "已发出" + masterDO.getInvoiceNo()
		 * + ",预计达到日期" + DateUtil.dateToDateString(esArrivateDate);
		 * orderStateVO.setStateCode(31);
		 * 
		 * orderStateVO.setStateDes(stateDesc);
		 * orderStateVO.setProvider(masterDO.getSupplierCode());
		 * orderStateVO.setSupplierCode(masterDO.getSupplierCode());
		 * orderStateVO.setStateDate(masterDO.getShipDate());
		 * orderStateVO.setEsArrivalDate(esArrivateDate);
		 * orderStateVO.setPoInvoiceNo(masterDO.getInvoiceNo());
		 * orderStateVO.setPoShipDate(shipDate);
		 * 
		 * // add by A78027 2023040430 bug 10169 //国内(三方)工厂发票，出厂日=发货日
		 * if(masterDO.getInvoiceType()==3||masterDO.getInvoiceType()==4) {
		 * orderStateVO.setPoFacExpdate(shipDate); }
		 * sendOrderState(invoiceDetails, orderStateVO);
		 */
		try {
			toUpdateOrderState(masterDO, invoiceDetails);
		} catch (Exception ex) {
			log.error("更新货期状态失败,{}", JSON.toJSONString(masterDO));
		}
		// 发送发票入库消息
		ImpInvoiceProcessDTO processDTO = new ImpInvoiceProcessDTO();
		processDTO.setInvoiceId(masterDO.getId());
		processDTO.setProcessType(3);
		this.sendInvoiceProcessMsgToMQ(processDTO);
	}

	@Transactional(rollbackFor = Exception.class)
	public List<ImpInvoiceDetailVO> addGZImpInvoiceData(ImpInvoiceMasterDO masterDO, List<ImpDataBJDO> itemlist) {

		boolean isNewMaster = true;
		// BigDecimal sumAmount = BigDecimal.ZERO;
		// for (ImpDataBJDO info : itemlist) {
		// sumAmount =
		// sumAmount.add(Optional.ofNullable(info.getAmount()).orElse(BigDecimal.ZERO));
		// }
		ImpInvoiceMasterDO exitMasterDO = getExistImpInvoiceMasterByInvoiceNo(masterDO.getSupplierCode(),
				masterDO.getInvoiceNo(), masterDO.getShipDate());

		if (exitMasterDO != null) {
			if (exitMasterDO.getStatus() == 3 || exitMasterDO.getStatus() == 6) {
				return null;
			}
			// 总价格一样无须处理
			// if (exitMasterDO.getAmount() != null &&
			// exitMasterDO.getAmount().compareTo(sumAmount) == 0) {
			// return null;
			// }
			masterDO.setId(exitMasterDO.getId());
			isNewMaster = false;
		}
		masterDO.setUpdateUser("GZIMP");
		masterDO.setStatus(2);
		masterDO.setCurrency("CNY");
		masterDO.setExchangeRate(BigDecimal.ONE);
		masterDO.setDataType(1);
		masterDO.setTransType("3");
		masterDO.setInvoiceType(3);
		// masterDO.setAmountRmb(sumAmount);
		// masterDO.setAmount(sumAmount);
		// masterDO.setVatFee(taxamount);
		masterDO.setRemark("GZ制造发货数据");
		masterDO.setUpdateTime(new Date());

		// 获取采购单收货仓库
		// OrderNoInfo masterorderNoInfo = new
		// OrderNoInfo().convertCNOrder(itemlist.get(0).getOrderNo());
		// String receiveWarehouseCode =
		// impInvoiceMasterMapper.getReceiveWarehouseCode(masterorderNoInfo.getPoNo());
		// if (StringUtils.isNotBlank(receiveWarehouseCode)) {
		// masterDO.setReceiveWarehouseCode(receiveWarehouseCode);
		// } else {
		// masterDO.setReceiveWarehouseCode("KGZ");
		// }
		// 3.保存到导入发票主表
		if (masterDO.getId() == null) {
			masterDO.setCreateTime(new Date());
			masterDO.setCreateUser("GZIMP");
			String billNo = commonServiceFeignApi.generatorBillNo("21").getData();
			if (PublicUtil.isEmpty(billNo)) {
				return null;
			}
			masterDO.setId(Long.parseLong(billNo));
			impInvoiceMasterMapper.insert(masterDO);
		} else {
			impInvoiceMasterMapper.updateById(masterDO);
		}

		List<ImpInvoiceDetailVO> invoiceDetails = new ArrayList<>(itemlist.size());
		ImpInvoiceDetailDO detailDO;
		OrderNoInfo orderNoInfo;

		for (ImpDataBJDO item : itemlist) {
			detailDO = new ImpInvoiceDetailDO();
			detailDO.setInvoiceNo(masterDO.getInvoiceNo());
			detailDO.setInvoiceId(masterDO.getId());
			detailDO.setFromId(item.getId());
			if (!isNewMaster) {
				ImpInvoiceDetailDO existDetailDO = getImpInvoiceDetailByFromId(masterDO.getId(), detailDO.getFromId());
				if (existDetailDO != null) {
					detailDO.setId(existDetailDO.getId());
				}
			}
			if (PublicUtil.isEmpty(item.getBarCode())) {
				String billNo = commonServiceFeignApi.generatorBillNo("20").getData();
				detailDO.setBarcode(billNo);
			} else {
				detailDO.setBarcode(item.getBarCode());
			}
			// barcode存在则不导入
			int barcodeCount = getImpInvoiceDetailByBarCode(detailDO.getBarcode());
			if (barcodeCount > 0) {
				continue;
			}
			detailDO.setModelNo(item.getModelNo());
			detailDO.setPrice(item.getPrice());
			detailDO.setQuantity(item.getQuantity());

			BigDecimal amountDetail = item.getAmount();
			if (Optional.ofNullable(amountDetail).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) == 0) {
				amountDetail = BigDecimalUtil.mul(item.getQuantity(), item.getPrice());
			}
			detailDO.setAmount(amountDetail);
			if (item.getOrderNo().trim().length() < 10) {
				continue;
			}
			detailDO.setImpOrderNo(item.getOrderNo().trim());
			detailDO.setImpModelNo(item.getModelNo());
			orderNoInfo = new OrderNoInfo().convertFullPONo(item.getOrderNo().trim());
			detailDO.setOrderNo(orderNoInfo.getOrderNo());
			detailDO.setItemNo(orderNoInfo.getItemNo());
			detailDO.setSplitItemNo(orderNoInfo.getSplitItem());
			detailDO.setPoNo(orderNoInfo.getPoNo());
			detailDO.setPoItemNo(orderNoInfo.getPoItemNo());
			detailDO.setFullOrderNo(orderNoInfo.getFullOrderNo());
			detailDO.setCaseNo(item.getCaseNo());
			detailDO.setOverseaInvoiceNo(item.getInvoiceNo());
			detailDO.setRoHSCode(item.getRoHS());
			detailDO.setWeight(item.getUnitWeight());
			// packDO.setWeight(item.getUnitWeight());
			detailDO.setShipDate(item.getImpDate());
			detailDO.setSupplierCode("GZ");
			detailDO.setStatus(1);
			detailDO.setRemark("GZ制造发货数据");
			detailDO.setCurrency(masterDO.getCurrency());
			detailDO.setUpdateTime(new Date());
			if (detailDO.getId() == null) {
				detailDO.setCreateUser("GZIMP");
				detailDO.setCreateTime(new Date());
				impInvoiceDetailMapper.insert(detailDO);
			} else {
				detailDO.setUpdateUser("GZIMP");
				impInvoiceDetailMapper.updateById(detailDO);
			}

			invoiceDetails.add(BeanCopyUtil.copy(detailDO, ImpInvoiceDetailVO.class));
		}
		// 合计
		updateImpInvoiceQty(masterDO.getId());
		// 复制发票数据到分包数据里面
		copyToInvoicedetailPack(masterDO.getId());

		return invoiceDetails;
	}

	/**
	 * 广州制造增值发票录入发票
	 *
	 * @return
	 */
	@Override
	public ResultVo<String> syncGZSalesinvoiceToIMP(String optDate) {
		// 1.查找广州发票中间表
		String returnInvoiNos = "";
		LambdaQueryWrapper<GZSalesinvoiceDO> query = new LambdaQueryWrapper<>();
		query.ge(GZSalesinvoiceDO::getInsertTime, optDate).isNotNull(GZSalesinvoiceDO::getOptDate);
		List<GZSalesinvoiceDO> list = gzSalesinvoiceMapper.selectList(query);

		// 2.按发票号分组，一个组一个发票主表
		Map<String, List<GZSalesinvoiceDO>> listmap = list.stream()
				.collect(Collectors.groupingBy(GZSalesinvoiceDO::getInvoiceNo));

		List<GZSalesinvoiceDO> itemlist;
		ImpInvoiceMasterDO masterDO;

		for (String key : listmap.keySet()) {

			itemlist = listmap.get(key);
			if (itemlist != null && !itemlist.isEmpty()) {
				masterDO = new ImpInvoiceMasterDO();
				masterDO.setInvoiceNo(key);
				masterDO.setSupplierCode("GZ");
				masterDO.setShipment("SMC（广州）");
				masterDO.setShipDate(DateUtil.getDate(itemlist.get(0).getImpdate()));
				masterDO.setInvoiceDate(itemlist.get(0).getOptDate());
				// masterDO.setCInvoiceNo(itemlist.get(0).getDeliveryNo());

				// 录入数据
				impInvoiceService.addGZImpInvoiceTmp(masterDO, itemlist);

				// 更新发货金额
				ImpInvoiceProcessDTO processDTO = new ImpInvoiceProcessDTO();
				processDTO.setInvoiceId(masterDO.getId());
				processDTO.setProcessType(6);
				this.sendInvoiceProcessMsgToMQ(processDTO);

				returnInvoiNos += masterDO.getInvoiceNo() + ";";
			}
		}
		// List<GZSalesinvoiceDO> itemList;
		// ImpInvoiceMasterDO masterDO;
		// ImpInvoiceMasterDO exitMasterDO;
		// ImpInvoiceMasterDO updaMaster;
		//
		// for (String key : listmap.keySet()) {
		// itemList = listmap.get(key);
		//
		// if (itemList != null && !itemList.isEmpty()) {
		// masterDO = new ImpInvoiceMasterDO();
		// masterDO.setInvoiceNo(key);
		// masterDO.setSupplierCode("GZ");
		// masterDO.setShipDate(DateUtil.getDate(itemList.get(0).getImpdate()));
		// masterDO.setInvoiceDate(itemList.get(0).getOptDate());
		//
		// exitMasterDO =
		// getExistImpInvoiceMasterByInvoiceNo(masterDO.getSupplierCode(),
		// masterDO.getInvoiceNo(), masterDO.getShipDate());
		// if (exitMasterDO != null) {
		// updaMaster = new ImpInvoiceMasterDO();
		// updaMaster.setId(exitMasterDO.getId());
		// updaMaster.setInvoiceDate(masterDO.getInvoiceDate());
		// impInvoiceMasterMapper.updateById(updaMaster);
		// }
		// }
		// }
		return ResultVo.success(returnInvoiNos);
	}

	@Transactional(rollbackFor = Exception.class)
	public List<ImpInvoiceDetailVO> addGZImpInvoiceTmp(ImpInvoiceMasterDO masterDO, List<GZSalesinvoiceDO> itemlist) {
		// 合计发票总金额
		BigDecimal amount = itemlist.stream().map(GZSalesinvoiceDO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal taxamount = itemlist.stream().map(GZSalesinvoiceDO::getTaxamount).reduce(BigDecimal.ZERO,
				BigDecimal::add);
		ImpInvoiceMasterDO exitMasterDO = getExistImpInvoiceMasterByInvoiceNo(masterDO.getSupplierCode(),
				masterDO.getInvoiceNo(), masterDO.getShipDate());

		if (exitMasterDO != null) {
			masterDO.setId(exitMasterDO.getId());
			// 不是待入库或分包数据的不做处理
			if (exitMasterDO.getStatus() != ImpInvoiceMasterStatusEnum.BECOST.getType()) {
				return null;
			}
			// 总价格一样无须处理
			if (exitMasterDO.getAmount().compareTo(amount) == 0) {
				return null;
			}
			// 删除明细项
			LambdaQueryWrapper<ImpInvoiceDetailDO> deletWrapper = new LambdaQueryWrapper<>();
			deletWrapper.eq(ImpInvoiceDetailDO::getInvoiceId, exitMasterDO.getId());
			impInvoiceDetailMapper.delete(deletWrapper);
		}
		masterDO.setUpdateUser("GZSales");
		masterDO.setStatus(4);
		masterDO.setCurrency("CNY");
		masterDO.setExchangeRate(BigDecimal.ONE);
		masterDO.setDataType(1);
		masterDO.setTransType("3");
		masterDO.setInvoiceType(5);
		masterDO.setAmountRmb(amount);
		masterDO.setAmount(amount);
		masterDO.setVatFee(taxamount);
		masterDO.setRemark("GZ制造发票数据");
		// 3.保存到导入发票主表
		if (masterDO.getId() == null) {
			masterDO.setCreateUser("GZSales");
			String billNo = commonServiceFeignApi.generatorBillNo("21").getData();
			if (PublicUtil.isEmpty(billNo)) {
				return null;
			}
			masterDO.setId(Long.parseLong(billNo));
			impInvoiceMasterMapper.insert(masterDO);
		} else {
			impInvoiceMasterMapper.updateById(masterDO);
		}

		List<ImpInvoiceDetailVO> invoiceDetails = new ArrayList<>(itemlist.size());
		ImpInvoiceDetailDO detailDO;
		OrderNoInfo orderNoInfo;

		for (GZSalesinvoiceDO item : itemlist) {
			detailDO = new ImpInvoiceDetailDO();
			detailDO.setInvoiceNo(masterDO.getInvoiceNo());
			// String billNo =
			// commonServiceFeignApi.generatorBillNo("20").getData();
			// detailDO.setBarcode(billNo);
			detailDO.setModelNo(item.getModelNo());
			detailDO.setQuantity(item.getQuantity());
			detailDO.setPrice(item.getPrice());
			BigDecimal amountDetail = item.getAmount();
			if (Optional.ofNullable(amountDetail).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) == 0) {
				amountDetail = BigDecimalUtil.mul(item.getQuantity(), item.getPrice());
			}
			detailDO.setAmount(amountDetail);
			if (item.getOrderNo().length() < 10) {
				continue;
			}
			detailDO.setImpOrderNo(item.getOrderNo().trim());
			detailDO.setImpModelNo(item.getModelNo());
			orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo().trim());
			detailDO.setOrderNo(orderNoInfo.getOrderNo());
			detailDO.setItemNo(orderNoInfo.getItemNo());
			detailDO.setPoNo(orderNoInfo.getPoNo());
			detailDO.setPoItemNo(orderNoInfo.getPoItemNo());
			detailDO.setFullOrderNo(orderNoInfo.getFullOrderNo());
			detailDO.setInvoiceId(masterDO.getId());
			detailDO.setShipDate(item.getImpdate());
			detailDO.setOverseaInvoiceNo(item.getDeliveryNo());
			detailDO.setSupplierCode("GZ");
			detailDO.setStatus(5);
			detailDO.setRemark("GZ制造发票数据");
			invoiceDetails.add(BeanCopyUtil.copy(detailDO, ImpInvoiceDetailVO.class));
			// 4.保存到导入发票明细
			impInvoiceDetailMapper.insert(detailDO);
		}
		return invoiceDetails;
	}

	/**
	 * 调用采购接口生成，预到货数据
	 *
	 * @param invoiceDetails
	 * @param master
	 */
	private void toUpdatePOTransStatus(List<ImpInvoiceDetailVO> invoiceDetails, ImpInvoiceMasterDO master) {

		if (master.getPreArriveDate() == null) {
			log.info("不生成发票预到货，预到货日期为空,{}", master.getInvoiceNo());
			return;
		}
		// add by A78027 20221130 bug 10303
		if (PublicUtil.isEmpty(master.getSupplierCode())) {
			log.info("不生成发票预到货，供应商为空,{}", master.getInvoiceNo());
			return;
		}
		// 是否启动发票预到货,1启动，其它不启动 bug8530
		ResultVo<DataTypeVO> dataTypeResultVo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "9");
		if (dataTypeResultVo == null || !"1".equals(dataTypeResultVo.getData().getExtNote1())) {
			log.info("启动发票预到货已关闭,{}", master.getInvoiceNo());
			return;
		}

		// end bug8530
		Map<String, PurchaseReplyInfo> poMaps = new HashMap<>(invoiceDetails.size());
		PurchaseReplyInfo info;
		String key;
		Date impdate = new Date();
		for (ImpInvoiceDetailVO orderVo : invoiceDetails) {
			key = orderVo.getPoNo() + "~" + orderVo.getPoItemNo();
			if (poMaps.containsKey(key)) {
				info = poMaps.get(key);
				info.setQtyTrans(orderVo.getQuantity() + info.getQtyTrans());
			} else {
				info = new PurchaseReplyInfo();
				// 改成3个订单号
				info.setOrderno(orderVo.getOrderNo());
				info.setItemno(orderVo.getItemNo());
				info.setSplitno(orderVo.getSplitItemNo());
				info.setPono(orderVo.getPoNo());
				info.setLineitem(orderVo.getPoItemNo());
				info.setTranstype(master.getTransType());
				info.setImpdate(master.getPreArriveDate());
				info.setInvoiceno(orderVo.getInvoiceNo()); // 原始发票号
				info.setInvoiceid(orderVo.getInvoiceId());
				info.setModelno(orderVo.getModelNo());
				// bug13550 使用主表供应商
				info.setSupplierid(master.getSupplierCode());
				info.setQtyTrans(orderVo.getQuantity()); // 运输中数量
				info.setCustomsdate(master.getCustomsDate());
				info.setPortarrivedate(master.getPortArrivedate());
				info.setDeclarationNo(master.getDeclarationNo()); // bug14223 交付系统关务发票导入时，报关单号字段的赋值
				poMaps.put(key, info);
			}
		}
		log.info(JSON.toJSONString(poMaps.values()));
		try {
			// 初始系统不执行
			CommonResult<String> updateInvoice = wmPurchaseFeignApi.updateInvoice(new ArrayList<>(poMaps.values()));
			if (!updateInvoice.isSuccess()) {
				log.error("调用BJ发票预到货接口失败: {} {}", master.getInvoiceNo(), updateInvoice.getMessage());
			}
		} catch (Exception e) {
			log.error("调用BJ发票预到货接口失败updateInvoice：" + e.getMessage());
		}
	}

	/**
	 * /** 更新采购到货状态 //* @param //packDO //* @return //
	 */
	/*
	 * public ResultVo<String> toUpdatePOReceiveState(ImpInvoiceDetailPackDO
	 * packDO) { PurchaseReplyInfo info = new PurchaseReplyInfo();
	 * info.setBarCode(packDO.getBarcode()); info.setCaseNo(packDO.getCaseNo());
	 * info.setImpdate(DateUtil.getCurrentDate());
	 * info.setInvoiceid(packDO.getInvoiceId());
	 * info.setInvoiceno(packDO.getInvoiceNo());
	 * info.setReplyorderno(packDO.getOrderNo());
	 * info.setModelno(packDO.getModelNo());
	 * info.setQtyreceive(packDO.getQuantity());
	 * info.setSupplierid(packDO.getSupplierCode());
	 * info.setLineitem(packDO.getPoItemNo()); info.setPono(packDO.getPoNo());
	 * if (PublicUtil.isEmpty(packDO.getBarcode())) { String billNo =
	 * commonServiceFeignApi.generatorBillNo("20").getData();
	 * info.setBarCode(billNo); } else { info.setBarCode(packDO.getBarcode()); }
	 * List<PurchaseReplyInfo> list = new ArrayList<>(1); list.add(info);
	 * boolean isupdate = toUpdateInvoice(list, packDO.getId());
	 * //调用北京结果并更新Pack状态 if (!isupdate) { //调用北京接口失败再重复调用一次 boolean seconupdate
	 * = toUpdateInvoice(list, packDO.getId()); if (seconupdate) { return
	 * ResultVo.success(); } } else { return ResultVo.success(); } return
	 * ResultVo.failure(); }
	 */
	// public boolean toUpdateInvoice(List<PurchaseReplyInfo> list, Long
	// packItemId) {
	// CommonResult<String> updateInvoice =
	// wmPurchaseFeignApi.updateInvoice(list);
	// if (!updateInvoice.isSuccess()) {
	// log.error("发票数据导入失败: {}", updateInvoice.getMessage());
	//// throw new BusinessException("updateInvoice fail, " +
	// updateInvoice.getMessage());
	// return false;
	// }
	// ImpInvoiceDetailPackDO updateDO = new ImpInvoiceDetailPackDO();
	// updateDO.setId(packItemId);
	// updateDO.setStatus(6);
	// int updcount = impInvoiceDetailPackMapper.updateById(updateDO);
	// return updcount > 0;
	// }

	/**
	 * 查询入库中数据
	 *
	 * @param request
	 * @return
	 */
	@Override
	public ResultVo<PageInfo<ImpInvoiceDetailPackVO>> listNoImpInvoiceDetailPack(ImpdataRequest request) {
		LambdaQueryWrapper<ImpInvoiceDetailPackDO> query = new LambdaQueryWrapper<>();
		if (request.getTodate() != null) {
			request.setTodate(DateUtil.addDay(request.getTodate(), 1));
		}
		query.eq(PublicUtil.isNotEmpty(request.getOrderNo()), ImpInvoiceDetailPackDO::getFullOrderNo,
				request.getOrderNo())
				.eq(PublicUtil.isNotEmpty(request.getInvoiceNo()), ImpInvoiceDetailPackDO::getInvoiceNo,
						request.getInvoiceNo())
				.eq(PublicUtil.isNotEmpty(request.getOptCode()), ImpInvoiceDetailPackDO::getStatus,
						request.getOptCode())
				.eq(PublicUtil.isNotEmpty(request.getSupplierCode()), ImpInvoiceDetailPackDO::getSupplierCode,
						request.getSupplierCode())
				.like(PublicUtil.isNotEmpty(request.getModelNo()), ImpInvoiceDetailPackDO::getModelNo,
						request.getModelNo())
				.ge(request.getFromdate() != null, ImpInvoiceDetailPackDO::getCreateTime, request.getFromdate())
				.lt(request.getTodate() != null, ImpInvoiceDetailPackDO::getCreateTime, request.getTodate());
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		List<ImpInvoiceDetailPackDO> list = impInvoiceDetailPackMapper.selectList(query);
		PageInfo<ImpInvoiceDetailPackDO> pageInfo = PageInfo.of(list);
		PageInfo<ImpInvoiceDetailPackVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, ImpInvoiceDetailPackVO.class);
		return ResultVo.success(voPageInfo);
	}

    /**
     * 查询入库中数据
     * 前端excel导出
     * @param request
     * @return
     */
    @Override
    public List<ImpInvoiceDetailPackVO> listNoImpInvoiceDetailPackExcel(ImpdataRequest request) {
        LambdaQueryWrapper<ImpInvoiceDetailPackDO> query = new LambdaQueryWrapper<>();
        if (request.getTodate() != null) {
            request.setTodate(DateUtil.addDay(request.getTodate(), 1));
        }
        query.eq(PublicUtil.isNotEmpty(request.getOrderNo()), ImpInvoiceDetailPackDO::getFullOrderNo,
                        request.getOrderNo())
                .eq(PublicUtil.isNotEmpty(request.getInvoiceNo()), ImpInvoiceDetailPackDO::getInvoiceNo,
                        request.getInvoiceNo())
                .eq(PublicUtil.isNotEmpty(request.getOptCode()), ImpInvoiceDetailPackDO::getStatus,
                        request.getOptCode())
                .eq(PublicUtil.isNotEmpty(request.getSupplierCode()), ImpInvoiceDetailPackDO::getSupplierCode,
                        request.getSupplierCode())
                .like(PublicUtil.isNotEmpty(request.getModelNo()), ImpInvoiceDetailPackDO::getModelNo,
                        request.getModelNo())
                .ge(request.getFromdate() != null, ImpInvoiceDetailPackDO::getCreateTime, request.getFromdate())
                .lt(request.getTodate() != null, ImpInvoiceDetailPackDO::getCreateTime, request.getTodate());
        List<ImpInvoiceDetailPackDO> impInvoiceDetailPackDOS = impInvoiceDetailPackMapper.selectList(query);
        return BeanCopyUtil.copyList(impInvoiceDetailPackDOS,ImpInvoiceDetailPackVO.class);
    }

	/**
	 * 手动入库入库失败的数据
	 *
	 * @param request
	 * @return
	 */
	/*
	 * @Override
	 * 
	 * @Transactional(rollbackFor = Exception.class) public ResultVo<String>
	 * handimpInvoiceDetailPack(ImpdataRequest request) { try { int count = 0;
	 * for (Integer id : request.getIds()) { ImpInvoiceDetailPackDO packDO =
	 * this.impInvoiceDetailPackMapper.selectById(id); if (packDO.getStatus() !=
	 * 2) { continue; } ResultVo<String> resultVo =
	 * toUpdatePOReceiveState(packDO); if (resultVo.isSuccess()) { count++; } }
	 * if (count > 0) { return ResultVo.success("成功入库" + count + "条数据！"); } else
	 * { return ResultVo.success("入库失败！"); } } catch (Exception e) { throw new
	 * BusinessException(e.getMessage(), e); } }
	 */

	/**
	 * 获取采购数据
	 *
	 * @param poNo
	 * @return
	 */
	@Override
	public ResultVo<OpsPurchaseInvoiceDO> getopspurchaseInvoice(String poNo, String poItemNo) {
		QueryWrapper<OpsPurchaseInvoiceDO> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isBlank(poItemNo)) {
			OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(poNo);
			String orderNo = orderNoInfo.getOrderNo();
			Integer itemNo = orderNoInfo.getItemNo();
			Integer splitNo = orderNoInfo.getSplitItem();

			queryWrapper.eq("orderNo", orderNo);
			queryWrapper.eq("itemNo", itemNo);
			if (splitNo == null) {
				queryWrapper.isNull("splitItemNo");
			} else {
				queryWrapper.eq("splitItemNo", splitNo);
			}
		} else {
			queryWrapper.eq("poNo", poNo);
			queryWrapper.eq("lineItem", poItemNo);
		}
		queryWrapper.select(" top 1 * ");
		OpsPurchaseInvoiceDO poOrderDO = opsPurchaseInvoiceMapper.selectOne(queryWrapper);
		return ResultVo.success(poOrderDO);
	}

	/***
	 * 导入关务发票状态
	 * 
	 * @return
	 */

	@Override
	public ResultVo<String> impInvoiceStatusFrmCMS() {
		return ResultVo.success("接口已停用");
//		// 从上次存储的时间接着读取
//		// Object o = redisManager.get("ops:cmsinvoicestate:lastDate");
//		Date lastDate = null;
//		lastDate = DateUtil.addDay(new Date(), -30);
//
//		/*
//		 * if (null == o) { lastDate =DateUtil.addDay(new Date(),-10); } else {
//		 * lastDate = DateUtil.stringToDateTime(o.toString()); }
//		 */
//		Date currentDate = DateUtil.getNow();
//		// 获取数据集
//		List<ImpInvoiceStatusFrmCMSDO> invoiceStatusFromCMSList = opsvImpInvoiceStatusFrmCMSMapper
//				.findInvoiceStatusFromCMS(lastDate, currentDate);
//		if (invoiceStatusFromCMSList.isEmpty()) {
//			return ResultVo.success("暂无数据可需导入");
//		}
//		// 根据发票号去重
//		/*
//		 * ArrayList<ImpInvoiceStatusFrmCMSDO> collect =
//		 * invoiceStatusFromCMSList.stream()
//		 * .collect(Collectors.collectingAndThen( Collectors.toCollection(() ->
//		 * new TreeSet<>(
//		 * Comparator.comparing(ImpInvoiceStatusFrmCMSDO::getOldInvoiceNo))),
//		 * ArrayList::new));
//		 */
//		int count = 0;
//		LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = new LambdaQueryWrapper<>();
//		List<ImpInvoiceMasterDO> impInvoiceMasterDOS;
//		ImpInvoiceMasterDO impInvoiceMasterDO;
//		ImpInvoiceMasterDO updImpInvoiceMasterDO;
//		OrderStateVO orderStateVO;
//		LambdaQueryWrapper<ImpInvoiceDetailDO> queryDetail = new LambdaQueryWrapper<>();
//		List<ImpInvoiceDetailVO> detailVOS;
//
//		for (ImpInvoiceStatusFrmCMSDO invoiceStatusFrmCMSDO : invoiceStatusFromCMSList) {
//			queryWrapper.clear();
//			queryWrapper.eq(ImpInvoiceMasterDO::getCInvoiceNo, invoiceStatusFrmCMSDO.getInvoiceNo())
//					.orderByDesc(ImpInvoiceMasterDO::getUpdateTime);
//			// 判断是否在 imp_invoice_master
//			impInvoiceMasterDOS = impInvoiceMasterMapper.selectList(queryWrapper);
//			if (impInvoiceMasterDOS.isEmpty()) {
//				continue;
//			}
//
//			// 根据发票号和 imp_invoice_master_id 查 Imp_invoice_detail
//			impInvoiceMasterDO = impInvoiceMasterDOS.get(0);
//
//			if (impInvoiceMasterDO.getStatus() != 1 || impInvoiceMasterDO.getStatus() != 2) {
//				continue;
//			}
//
//			updImpInvoiceMasterDO = new ImpInvoiceMasterDO();
//			updImpInvoiceMasterDO.setId(impInvoiceMasterDO.getId());
//
//			/*
//			 * // 报关日期 if (invoiceStatusFrmCMSDO.getCustomsDate() != null) {
//			 * impInvoiceMasterDO.setCustomsDate(invoiceStatusFrmCMSDO.
//			 * getCustomsDate()); if (impInvoiceMasterDO.getStatus() == 1) {
//			 * impInvoiceMasterDO.setStatus(2); } }
//			 */
//			// 预到货日期
//			/*
//			 * if (invoiceStatusFrmCMSDO.getEsArriveDate() != null) {
//			 * updImpInvoiceMasterDO.setPreArriveDate(invoiceStatusFrmCMSDO.
//			 * getEsArriveDate()); }
//			 */
//
//			boolean isUpd = false;
//			// 实际到港时间
//			if (invoiceStatusFrmCMSDO.getRealyArricalDate() != null) {
//				updImpInvoiceMasterDO.setPortArrivedate(invoiceStatusFrmCMSDO.getRealyArricalDate());
//				isUpd = true;
//			}
//
//			// 到厂日期
//			if (invoiceStatusFrmCMSDO.getArriveFactoryDate() != null) {
//				updImpInvoiceMasterDO.setReceiveDate(invoiceStatusFrmCMSDO.getArriveFactoryDate());
//				isUpd = true;
//			}
//			if (!isUpd) {
//				continue;
//			}
//			// orderStateVO = new OrderStateVO();
//
//			// 设置报关状态
//			if (invoiceStatusFrmCMSDO.getCustomsDate() != null) {
//				updImpInvoiceMasterDO
//						.setGwState(DateUtil.dateToDateString(invoiceStatusFrmCMSDO.getCustomsDate()) + "已报关");
//				if (invoiceStatusFrmCMSDO.getEsArriveDate() != null) {
//					updImpInvoiceMasterDO.setGwState(
//							"预计到货日期: " + DateUtil.dateToDateString(invoiceStatusFrmCMSDO.getEsArriveDate()));
//				}
//			} else if (invoiceStatusFrmCMSDO.getRealyArricalDate() != null) {
//				updImpInvoiceMasterDO
//						.setGwState(DateUtil.dateToDateString(invoiceStatusFrmCMSDO.getRealyArricalDate()) + "已到港");
//			}
//			impInvoiceMasterMapper.updateById(updImpInvoiceMasterDO);
//			count++;
//
//			// queryDetail.clear();
//			// queryDetail.eq(ImpInvoiceDetailDO::getInvoiceId,
//			// impInvoiceMasterDO.getId())
//			// // .eq(ImpInvoiceDetailDO::getInvoiceNo,
//			// impInvoiceMasterDO.getCInvoiceNo())
//			// .orderByDesc(ImpInvoiceDetailDO::getUpdateTime);
//			//
//			// List<ImpInvoiceDetailDO> impInvoiceDetailDOS =
//			// this.listImpInvoiceDetailByWrapper(queryDetail);
//			// if (impInvoiceDetailDOS.isEmpty()) {
//			// continue;
//			// }
//			//
//			//
//			// if (impInvoiceMasterDO.getStatus() != null
//			// && (impInvoiceMasterDO.getStatus() == 1 ||
//			// impInvoiceMasterDO.getStatus() == 2)) {
//			// // 遍历Imp_invoice_detail 推送到orderState
//			// detailVOS = BeanCopyUtil.copyList(impInvoiceDetailDOS,
//			// ImpInvoiceDetailVO.class);
//			// orderStateVO.setStateDes(impInvoiceMasterDO.getGwState());
//			// orderStateVO.setStateCode(31); // 完工发出
//			// sendOrderState(detailVOS, orderStateVO);
//			// }
//		}
//		// 存储本次最后读取的时间
//		// redisManager.set("ops:cmsinvoicestate:lastDate",
//		// DateUtil.dateToDateTimeString(invoiceStatusFromCMSList.get(0).getUpdateTime()));
//		return ResultVo.successMsg("操作成功,发票数据：" + count);
	}

	// /**
	// * <!--add by WuWeiDong 20231124 bug 12645 营业信息查询空指针错误 -->
	// * 只有YZ不更改orderstate的供应商，其他都根据发票的供应商更改到orderstate里
	// *
	// * @param orders
	// * @param orderStateVO
	// * @return
	// */
	// private ResultVo<String>
	// sendOrderStateCheckSupplier(List<ImpInvoiceDetailVO> orders,
	// OrderStateVO orderStateVO) {
	// Map<String, Boolean> map = new HashMap<>();
	// Set<String> orderNos = new HashSet<>(orders.size());
	// for (ImpInvoiceDetailVO order : orders) {
	//
	// if (StringUtils.isBlank(order.getFullOrderNo())) {
	// continue;
	// }
	//
	// if (orderNos.contains(order.getFullOrderNo())) {
	// continue;
	// }
	// if (Objects.isNull(order.getSplitItemNo())){
	// order.setSplitItemNo(0);
	// }
	// String orderNo=String.join("-", order.getFullOrderNo(),
	// order.getItemNo().toString(), order.getSplitItemNo().toString());
	// Boolean isYZ=map.getOrDefault(orderNo,false);
	// if (!map.containsKey(orderNo)){
	// LambdaQueryWrapper<OrderStateDO> queryWrapper = new
	// LambdaQueryWrapper<>();
	// queryWrapper.eq(OrderStateDO::getOrderNo, order.getFullOrderNo())
	// .eq(OrderStateDO::getItemNo, order.getItemNo())
	// .eq(OrderStateDO::getSplitNo, order.getSplitItemNo())
	// .select(OrderStateDO::getSupplierCode);
	// OrderStateDO orderStateDO = orderStateMapper.selectOne(queryWrapper);
	// isYZ="YZ".equalsIgnoreCase(orderStateDO.getSupplierCode());
	// map.put(orderNo,isYZ);
	// }
	// if (isYZ) {
	// orderStateVO.setSupplierCode("YZ");
	// }
	// orderStateVO.setOrderNo(order.getFullOrderNo());
	// orderStateVO.setRorderNo(order.getOrderNo());
	// orderStateVO.setSplitNo(order.getSplitItemNo());
	// orderStateVO.setItemNo(order.getItemNo());
	// orderStateVO.setModelNo(order.getModelNo());
	//
	// if (order.getOrderType() != null) {
	// orderStateVO.setOrderType(Integer.parseInt(order.getOrderType()));
	// }
	// orderStateService.addOrderState(orderStateVO);
	// orderNos.add(orderStateVO.getOrderNo());
	// }
	// return ResultVo.success();
	//
	// }

	/**
	 * 统一发送订单状态
	 *
	 * @param orders
	 *            订单号
	 * @param orderStateVO
	 *            统一订单状态,设置公共的发货状态信息
	 * @return
	 */
	public ResultVo<String> sendOrderState(List<ImpInvoiceDetailVO> orders, OrderStateVO orderStateVO) {
		// log.info("开始发送订单状态：" + orderStateVO.getStateDes() + " count=" +
		// orders.size());

		Set<String> orderNos = new HashSet<>(orders.size());
		for (ImpInvoiceDetailVO order : orders) {

			if (StringUtils.isBlank(order.getFullOrderNo())) {
				continue;
			}

			if (orderNos.contains(order.getFullOrderNo())) {
				continue;
			}
			orderStateVO.setOrderNo(order.getFullOrderNo());
			orderStateVO.setRorderNo(order.getOrderNo());
			orderStateVO.setSplitNo(order.getSplitItemNo());
			orderStateVO.setItemNo(order.getItemNo());
			orderStateVO.setModelNo(order.getModelNo());
			// if(orderStateVO.getStateCode()!=36){
			// orderStateVO.setQuantity(order.getQuantity());
			// }
			if (order.getOrderType() != null) {
				orderStateVO.setOrderType(Integer.parseInt(order.getOrderType()));
			}
			orderStateService.addOrderState(orderStateVO);
			orderNos.add(orderStateVO.getOrderNo());
		}
		return ResultVo.success();
	}

	private boolean sendInvoiceProcessMsgToMQ(ImpInvoiceProcessDTO dto) {
		RabbitMqMessage mqMsg = new RabbitMqMessage();
		mqMsg.setContent(JSON.toJSONString(dto));
		mqMsg.setFlag(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_INVOICE_PROSECC);
		mqMsg.setDataType(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS_INVOICE);
		mqMsg.setSystem(com.smc.smccloud.core.rabbitmq.constants.Constants.OPS);
		return sendMessage.sendInvoiceProcessMsg(mqMsg);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ImpInvoiceMasterDO getImpInvoiceMasterById(long invoiceId) {
		return impInvoiceMasterMapper.selectById(invoiceId);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ImpInvoiceMasterDO getLastImpInvoiceMaster(String invoiceNo, String supplierNo) {
		return impInvoiceMasterMapper.getLastImpInvoiceMaster(invoiceNo, supplierNo);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ImpInvoiceMasterDO getImpInvoiceMasterByWrapper(Wrapper<ImpInvoiceMasterDO> queryWrapper) {
		return impInvoiceMasterMapper.selectOne(queryWrapper);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<ImpInvoiceDetailDO> listImpInvoiceDetailByWrapper(Wrapper<ImpInvoiceDetailDO> queryWrapper) {
		return impInvoiceDetailMapper.selectList(queryWrapper);
	}

	/**
	 * @param smcCode
	 * @param extNote
	 *            需要额外转化的 2- 中国，北京，天津工厂 3- 上海特注工厂
	 * @return
	 */
	private String getWarehouseCodeBySMCCode(String smcCode, int extNote) {
		String key = "ops:datacode:2048";
		Object obj = redisManager.hGet(key, smcCode);
		DataCodeVO codeInfo;
		if (obj == null) {
			String classCode = "2048";
			ResultVo<List<DataCodeVO>> listResult = dictDataServiceFeignApi.getDataCodes(classCode);
			if (!listResult.isSuccess()) {
				log.error("getSMCCodeToWarehouseMap error: {}", listResult.getMessage());
				return null;
			}

			Map<String, Object> map = new HashMap<>(listResult.getData().size());
			for (DataCodeVO codeVO : listResult.getData()) {
				map.put(codeVO.getCode(), JSON.toJSONString(codeVO));
			}
			redisManager.hPutAll(key, map);
			redisManager.expire(key, 60 * 60 * 8);
			obj = map.get(smcCode);
		}

		if (obj != null) {
			codeInfo = JSON.parseObject(obj.toString(), DataCodeVO.class);
			if (extNote == 2) {
				if (StringUtils.isNotBlank(codeInfo.getExtNote2())) {
					return codeInfo.getExtNote2();
				}
			} else if (extNote == 3) {
				if (StringUtils.isNotBlank(codeInfo.getExtNote3())) {
					return codeInfo.getExtNote3();
				}
			}
			return codeInfo.getExtNote1();
		}

		return null;
	}

	@Override
	public ResultVo<String> autoConfirmPODetail() {
		List<Long> invoiceIdlist = poInvoiceMasterMapper.listNoPoDetailInvoiceId();
		for (Long invoiceId : invoiceIdlist) {
			try {
				impInvoiceService.confirmPOInvoiceDetail(invoiceId, 2);
			} catch (Exception e) {
			}
		}
		return ResultVo.success();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> changePoModelNo(String poNo, Integer poItemNo, String newModelNo) {

		String orderNo = poNo;
		Integer itemNo = poItemNo;
		Integer splitNo = null;
		if (poItemNo == null) {
			OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(poNo);
			orderNo = orderNoInfo.getOrderNo();
			itemNo = orderNoInfo.getItemNo();
			splitNo = orderNoInfo.getSplitItem();
		}

		return ResultVo.failure("暂时不处理");
		// Date updatedate=new Date();
		// //1）更新请采购型号
		// LambdaUpdateWrapper<OpsRequestpurchaseDO>
		// updateWrapper_requestpurchare = new LambdaUpdateWrapper<>();
		// updateWrapper_requestpurchare.eq(OpsRequestpurchaseDO::getOrderNo,orderNo)
		// .eq(OpsRequestpurchaseDO::getItemNo,itemNo);
		// if (splitNo!=null)
		// {
		// updateWrapper_requestpurchare.eq(OpsRequestpurchaseDO::getSplitItemNo,splitNo);
		// }
		// updateWrapper_requestpurchare.set(OpsRequestpurchaseDO::getModelNo,newModelNo)
		// .set(OpsRequestpurchaseDO::getUpdateTime,updatedate);
		// opsRequestpurchaseMapper.update( null,updateWrapper_requestpurchare);
		//
		// //2）更新采购型号
		// LambdaUpdateWrapper<OpsPurchaseOrderDO> updateWrapper_purchaseorder =
		// new LambdaUpdateWrapper<>();
		// updateWrapper_purchaseorder.eq(OpsPurchaseOrderDO::getOrderNo,orderNo)
		// .eq(OpsPurchaseOrderDO::getItemNo ,itemNo);
		// if (splitNo!=null)
		// {
		// updateWrapper_purchaseorder.eq(OpsPurchaseOrderDO::getSplitItemNo,splitNo);
		// }
		// updateWrapper_purchaseorder.set(OpsPurchaseOrderDO::getModelNo,newModelNo)
		// .set(OpsPurchaseOrderDO::getUpdateTime,updatedate);
		// opsPurchaseOrderMapper.update( null,updateWrapper_purchaseorder);
		//
		// //3）更新发票入库型号
		// LambdaUpdateWrapper<OpsPurchaseInvoiceDO>
		// updateWrapper_purchaseinvoice = new LambdaUpdateWrapper<>();
		// updateWrapper_purchaseinvoice.eq(OpsPurchaseInvoiceDO::getOrderNo,orderNo)
		// .eq(OpsPurchaseInvoiceDO::getItemNo ,itemNo);
		// if (splitNo!=null)
		// {
		// updateWrapper_purchaseinvoice.eq(OpsPurchaseInvoiceDO::getSplitItemNo,splitNo);
		// }
		// updateWrapper_purchaseinvoice.set(OpsPurchaseInvoiceDO::getModelNo,newModelNo)
		// .set(OpsPurchaseInvoiceDO::getUpdateTime,updatedate);
		// opsPurchaseInvoiceMapper.update( null
		// ,updateWrapper_purchaseinvoice);
		//
		//
		// //4)更新接单型号
		// LambdaUpdateWrapper<RcvDetailDO> updateWrapper_rcvdetail = new
		// LambdaUpdateWrapper<>();
		// updateWrapper_rcvdetail.eq(RcvDetailDO::getRorderNo,orderNo)
		// .eq(RcvDetailDO::getRorderItem ,itemNo)
		// .set(RcvDetailDO::getModelNo,newModelNo)
		// .set(RcvDetailDO::getUpdateTime,updatedate);
		//
		// rcvdetailMapper.update(null,updateWrapper_rcvdetail);
		// return ResultVo.success("更新成功！");
	}

	/**
	 * 根据供应商代码获取关务发票类型
	 *
	 * @param supplierCode
	 *            供应商代码
	 * @return 发票类型
	 */
	private int getGWInvoiceType(String supplierCode) {
		if (StringUtils.isBlank(supplierCode)) {
			return InvoiceTypeEnum.OTHER.getCode();
		}
		if ("JP".equals(supplierCode)) {
			return InvoiceTypeEnum.JP.getCode();
		}
		if ("CN,CM,CT,TZ".contains(supplierCode)) {
			// 三方: 中国工厂/北京工厂/天津工厂/上海
			return InvoiceTypeEnum.TRIPARTITE.getCode();
		} else {
			return InvoiceTypeEnum.THREE_COUNTRY.getCode();
		}
	}

	private int getInvoiceType(String supplierCode) {
		if (StringUtils.isBlank(supplierCode)) {
			return InvoiceTypeEnum.OTHER.getCode();
		}
		if ("JP".equals(supplierCode)) {
			return InvoiceTypeEnum.JP.getCode();
		}
		if ("CN,CM,CT,TZ,GZ".contains(supplierCode)) {
			// 国内工厂: 中国工厂/北京工厂/天津工厂/上海
			return InvoiceTypeEnum.CM.getCode();
		} else if ("GN".equalsIgnoreCase(supplierCode)) { // bug-9080
			return InvoiceTypeEnum.OTHER.getCode();
		} else {
			return InvoiceTypeEnum.THREE_COUNTRY.getCode();
		}
	}

	/**
	 * 判断是否无商业价值SMCCode
	 *
	 * @param smcCode
	 *            smccode
	 * @return boolean
	 */
	private boolean isNoCommercialValue(String smcCode) {
		// boolean rtnval= Arrays.binarySearch(Constants.NO_COMMERCIAL_VALUE,
		// smcCode) > 0; //查询的数组必须是有序的，如果不是有序的话，使用此方法是没有用的
		boolean rtnval = Arrays.asList(Constants.NO_COMMERCIAL_VALUE).contains(smcCode);
		return rtnval;
	}

	/**
	 * 获取原大发票号数据
	 *
	 * @param invoiceId
	 * @return
	 */
	@Override
	public ResultVo<List<PoInvoiceDetailDO>> listOverseaInvoiceData(Long invoiceId) {
		List<PoInvoiceDetailDO> list = poInvoiceDetailMapper.listInvoiceDataByOverseaNo(invoiceId);
		return ResultVo.success(list);
	}

	/**
	 * <!--add by WuWeiDong 20221130 bug 8614 --> 登记删除1,2->3,取消登记删除 3->2
	 *
	 * @param poInvoiceDTOList
	 * @param doType
	 *            1：登记删除1,2->3，2：取消登记删除 3->2
	 * @return
	 */
	@Override
	public ResultVo<String> updateDeleteDetailPack(List<PoInvoiceDTO> poInvoiceDTOList, Integer doType, String endUser) {
		if (PublicUtil.isEmpty(poInvoiceDTOList)) {
			return ResultVo.failure("数据缺失，请确认输入发票号和订单号。");
		}
		// List<PoInvoiceDTO> list=Arrays.asList(poInvoiceDTOList);
		String msg = "";
		Integer newStatus = -1;
		List<ImpInvoiceDetailPackDO> invoiceDetailPackDOS = new ArrayList<>();
		if (doType.equals(1)) {
			invoiceDetailPackDOS = impInvoiceDetailPackMapper
					.selectInvoiceDetailPacOnProcessByPoInvoiceDTO(poInvoiceDTOList);
			newStatus = 3;
			msg = "【登记删除订单】";
		} else if (doType.equals(2)) {
			invoiceDetailPackDOS = impInvoiceDetailPackMapper
					.selectInvoiceDetailPacOnErrorByPoInvoiceDTO(poInvoiceDTOList);
			newStatus = 2;
			msg = "【取消登记删除】";
		} else {
			return ResultVo.failure("请选择操作类型：(1)登记删除订单,(2)取消登记删除。");
		}

		if (PublicUtil.isEmpty(invoiceDetailPackDOS)) {
			return ResultVo.failure(msg + "失败，没有处理数据，可能已经登记。");
		}

		if (newStatus.equals(-1)) {
			return ResultVo.failure(msg + "失败，更新条件错误。");
		}
		// ResultVo<String> resultVo =
		// this.updateDeleteDetailPackStatusById(invoiceDetailPackDOS,
		// newStatus);
		ResultVo<String> resultVo = impInvoiceService.updateDeleteDetailPackStatusById(invoiceDetailPackDOS, newStatus,endUser);
		return resultVo;

	}

	/**
	 * 根据ID更新状态
	 *
	 * @param invoiceDetailPackDOS
	 * @param newStatus
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVo<String> updateDeleteDetailPackStatusById(List<ImpInvoiceDetailPackDO> invoiceDetailPackDOS,
			Integer newStatus, String endUser) {

		if (PublicUtil.isEmpty(invoiceDetailPackDOS)) {
			return ResultVo.failure("没有处理数据。");
		}
		String msg = "";
		ImpInvoiceErrorDO upErrorDO = new ImpInvoiceErrorDO();
		if (newStatus.equals(3)) {
			upErrorDO.setIgnoreError(true);
			upErrorDO.setIgnoreReason("已标记为差异订单，按正常入库");
			upErrorDO.setIgnoreTime(new Date());
			upErrorDO.setIgnorePsn(SMCApp.getLoginAuthDto().getUserNo());
			msg = "【登记删除订单】";
		} else if (newStatus.equals(2)) {
			upErrorDO.setIgnoreError(null);
			upErrorDO.setIgnoreReason(null);
			upErrorDO.setIgnoreTime(null);
			upErrorDO.setIgnorePsn(null);
			msg = "【取消登记删除】";
		} else {
			return ResultVo.failure("更新条件错误。");
		}

		List<ImpInvoiceErrorDO> impInvoiceErrorDOS = new ArrayList<>();
		ImpInvoiceErrorDO errorDO = new ImpInvoiceErrorDO();
		List<Long> listUpdateID = new ArrayList<>();
		for (ImpInvoiceDetailPackDO data : invoiceDetailPackDOS) {
			listUpdateID.add(data.getId());
			errorDO = new ImpInvoiceErrorDO();
			errorDO.setInvoiceId(data.getInvoiceId());
			errorDO.setInvoiceNo(data.getInvoiceNo());
			errorDO.setOrderNo(data.getFullOrderNo());
			impInvoiceErrorDOS.add(errorDO);
		}

		LambdaUpdateWrapper<ImpInvoiceDetailPackDO> updateWrapper = new LambdaUpdateWrapper<>();
        // bugid:20149 发票入库优化-前端部分 c14717 20260209
        if (newStatus.equals(3) && StringUtils.isNotBlank(endUser)){
            updateWrapper.in(ImpInvoiceDetailPackDO::getId, listUpdateID).set(ImpInvoiceDetailPackDO::getStatus, newStatus)
                    .set(ImpInvoiceDetailPackDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo())
                    .set(ImpInvoiceDetailPackDO::getEndUser,endUser.trim());
        }else {
            updateWrapper.in(ImpInvoiceDetailPackDO::getId, listUpdateID).set(ImpInvoiceDetailPackDO::getStatus, newStatus)
                    .set(ImpInvoiceDetailPackDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo())
                    .set(ImpInvoiceDetailPackDO::getEndUser,null);
        }
		Integer updcount = impInvoiceDetailPackMapper.update(new ImpInvoiceDetailPackDO(), updateWrapper);
		// System.out.println("更新了：" + updcount);
		impInvoiceErrorMapper.updateIgnoreInfo(impInvoiceErrorDOS, upErrorDO);

		return ResultVo.success(msg + "更新成功!" + updcount + "/" + listUpdateID.size());

	}

	/**
	 * 发票入库后，异步检查状态是否更新正确和更新货期状态
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public ResultVo<String> finishConfirmImpInvoice(ImpInvoiceProcessDTO dto) {

		ImpInvoiceMasterDO invoiceMasterDO = impInvoiceMasterMapper.selectById(dto.getInvoiceId());
		if (invoiceMasterDO == null) {
			return ResultVo.failure("发票id不存在" + dto.toString());
		}
		// 1.查询明细
		LambdaQueryWrapper<ImpInvoiceDetailPackDO> queryDetailWrapper = new LambdaQueryWrapper<>();
		queryDetailWrapper.eq(ImpInvoiceDetailPackDO::getInvoiceId, invoiceMasterDO.getId());
		queryDetailWrapper.select(ImpInvoiceDetailPackDO::getOrderNo, ImpInvoiceDetailPackDO::getItemNo,
				ImpInvoiceDetailPackDO::getSplitItemNo, ImpInvoiceDetailPackDO::getFullOrderNo,
				ImpInvoiceDetailPackDO::getPoNo, ImpInvoiceDetailPackDO::getPoItemNo,
				ImpInvoiceDetailPackDO::getModelNo, ImpInvoiceDetailPackDO::getQuantity,
				ImpInvoiceDetailPackDO::getStatus, ImpInvoiceDetailPackDO::getInvoiceNo);
		List<ImpInvoiceDetailPackDO> details = this.impInvoiceDetailPackMapper.selectList(queryDetailWrapper);
		if (details.isEmpty()) {
			return ResultVo.failure("发票明细不存在" + dto.toString());
		}

		// 2.如果发票状态还没有改成已入库则检查发票明细项有没有都已完成入库
		if (invoiceMasterDO.getStatus() == 2) {
			List<ImpInvoiceDetailPackDO> qryList = details.stream()
					.filter(x -> (x.getStatus() == 1) || (x.getStatus() == 2) || (x.getStatus() == 3))
					.collect(Collectors.toList());

			Long noProcessCount = details.stream()
					.filter(x -> (x.getStatus() == 1) || (x.getStatus() == 2) || (x.getStatus() == 3)).count();
			// 存在未处理项
			if (noProcessCount.compareTo(0L) > 0) {
				return ResultVo.failure("存在未处理项" + dto.toString());
			}
			Long processedCount = details.stream().filter(x -> x.getStatus() == 6).count();
			if (processedCount.compareTo(0L) == 0) {
				return ResultVo.failure("没有已处理项" + dto.toString());
			}
			invoiceMasterDO.setConfirmDate(dto.getOptTime());
			invoiceMasterDO.setUpdateUser(dto.getOptUser());
			ResultVo<String> resultVo = impInvoiceService.confirmPOInvoiceUpdate(invoiceMasterDO);
			if (resultVo.isSuccess() == false) {
				return resultVo;
			}
			invoiceMasterDO.setStatus(3);

		}

		// 3.三天内，并且是已入库状态，发送更新货期状态
		if (invoiceMasterDO.getStatus() == 3
				&& DateUtil.getDiffDay(invoiceMasterDO.getConfirmDate(), new Date()).intValue() < 3) {
			List<ImpInvoiceDetailVO> invoiceDetails = BeanCopyUtil.copyList(details, ImpInvoiceDetailVO.class);
			OrderStateVO orderStateVO = new OrderStateVO();
			orderStateVO.setProvider(invoiceMasterDO.getSupplierCode());
			orderStateVO.setSupplierCode(invoiceMasterDO.getSupplierCode());
			orderStateVO.setPoImpTime(new Date());
			orderStateVO.setStateDate(orderStateVO.getPoImpTime());
			orderStateVO.setPoInvoiceNo(invoiceMasterDO.getInvoiceNo());
			// add by A78027 20220327 bug 10169
			orderStateVO.setPoShipDate(invoiceMasterDO.getShipDate());
			orderStateVO.setStateCode(OrderStateEnum.InvoiceImpStock.getCode());
			orderStateVO.setStateDes(
					DateUtil.getFormatDate(new Date(), "yyyyMMdd") + "已发票入库" + invoiceMasterDO.getInvoiceNo());
			this.sendOrderState(invoiceDetails, orderStateVO);
		}

		return ResultVo.successMsg("更新发票入库成功" + dto.toString());
	}

	private LambdaQueryWrapper<ImpInvoiceMasterDO> getImpInvoiceMasterWrapper(ImpInvoiceMasterRequest request) {
		LambdaQueryWrapper<ImpInvoiceMasterDO> queryWrapper = new LambdaQueryWrapper<>();
		if (request.getSendTimeEnd() != null) {
			request.setSendTimeEnd(DateUtil.addDay(request.getSendTimeEnd(), 1));
		}
		if (request.getPrearriveDateEnd() != null) {
			request.setPrearriveDateEnd(DateUtil.addDay(request.getPrearriveDateEnd(), 1));
		}
		if (request.getUpdateTimeEnd() != null) {
			request.setUpdateTimeEnd(DateUtil.addDay(request.getUpdateTimeEnd(), 1));
		}
		if (request.getInvoiceDateEnd() != null) {
			request.setInvoiceDateEnd(DateUtil.addDay(request.getInvoiceDateEnd(), 1));
		}
		if (PublicUtil.isNotEmpty(request.getInvoiceType()) && request.getInvoiceType() > 0) {
			queryWrapper.eq(ImpInvoiceMasterDO::getInvoiceType, request.getInvoiceType());
		}
		if (PublicUtil.isEmpty(request.getInvoiceNo())) {
			queryWrapper.eq(PublicUtil.isNotEmpty(request.getSupplierCode()), ImpInvoiceMasterDO::getSupplierCode,
					request.getSupplierCode());
			queryWrapper.eq(PublicUtil.isNotEmpty(request.getStatus()), ImpInvoiceMasterDO::getStatus,
					request.getStatus());
			queryWrapper.eq(PublicUtil.isNotEmpty(request.getId()), ImpInvoiceMasterDO::getId, request.getId());
			queryWrapper.between(PublicUtil.isNotEmpty(request.getSendTimeStart()), ImpInvoiceMasterDO::getShipDate,
					request.getSendTimeStart(), request.getSendTimeEnd());
			queryWrapper.between(PublicUtil.isNotEmpty(request.getPrearriveDateStart()),
					ImpInvoiceMasterDO::getPreArriveDate, request.getPrearriveDateStart(),
					request.getPrearriveDateEnd());
			queryWrapper.between(PublicUtil.isNotEmpty(request.getUpdateTimeStart()), ImpInvoiceMasterDO::getUpdateTime,
					request.getUpdateTimeStart(), request.getUpdateTimeEnd());
			queryWrapper.between(PublicUtil.isNotEmpty(request.getInvoiceDateStart()),
					ImpInvoiceMasterDO::getInvoiceDate, request.getInvoiceDateStart(), request.getInvoiceDateEnd());
		} else {
			if (request.getInvoiceNo().contains("%")) {
				queryWrapper.apply(" invoice_no like '" + request.getInvoiceNo() + "'");
			} else {
				queryWrapper.eq(PublicUtil.isNotEmpty(request.getInvoiceNo()), ImpInvoiceMasterDO::getInvoiceNo,
						request.getInvoiceNo());
			}
		}
		return queryWrapper;

	}

	private LambdaQueryWrapper<ImpInvoiceDetailDO> getImpInvoiceDetailWrapper(ImpInvoiceDetailRequest request) {
		LambdaQueryWrapper<ImpInvoiceDetailDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(PublicUtil.isNotEmpty(request.getInvoiceId()), ImpInvoiceDetailDO::getInvoiceId,
				request.getInvoiceId());
		queryWrapper.eq(PublicUtil.isNotEmpty(request.getInvoiceNo()), ImpInvoiceDetailDO::getInvoiceNo,
				request.getInvoiceNo());
		queryWrapper.eq(PublicUtil.isNotEmpty(request.getBarcode()), ImpInvoiceDetailDO::getBarcode,
				request.getBarcode());
		queryWrapper.eq(PublicUtil.isNotEmpty(request.getOverseaInvoiceNo()), ImpInvoiceDetailDO::getOverseaInvoiceNo,
				request.getOverseaInvoiceNo());

		if (PublicUtil.isNotEmpty(request.getModelNo())) {
			if (request.getModelNo().contains("%")) {
				queryWrapper.apply(" and model_no like '" + request.getModelNo() + "'");
			} else {
				queryWrapper.eq(ImpInvoiceDetailDO::getModelNo, request.getModelNo());
			}
		}
		if (PublicUtil.isNotEmpty(request.getOrderNo())) {
			if (request.getOrderNo().contains("%")) {
				queryWrapper.apply(" and full_order_no '" + request.getOrderNo() + "'");
			} else {
				queryWrapper.eq(ImpInvoiceDetailDO::getFullOrderNo, request.getOrderNo());
			}
		}
		return queryWrapper;
	}

}
