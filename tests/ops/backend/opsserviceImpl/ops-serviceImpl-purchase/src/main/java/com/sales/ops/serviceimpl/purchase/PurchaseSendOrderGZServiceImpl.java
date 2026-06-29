package com.sales.ops.serviceimpl.purchase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.db.dao.OpsPurchaseinvoiceMapper;
import com.sales.ops.db.dao.OpsTomanuConfigMapper;
import com.sales.ops.db.dao.RcvdetailMapper;
import com.sales.ops.db.dao.RcvmasterMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OpsRequisitionCNDao;
import com.sales.ops.db.extdao.PurchaseOrderDao;
import com.sales.ops.dto.ips.IpsReceiveOrderAllOriginalInfoDto;
import com.sales.ops.dto.purchase.OrderSalesGPDto;
import com.sales.ops.dto.purchase.RequestPurchaseInfo;
import com.sales.ops.dto.purchase.SMCOrderDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.enums.TradeCompanyIdEnum;
import com.sales.ops.enums.invoice.ImpInvoiceCommonEnum;
import com.sales.ops.enums.ipsPurchase.IpsPurchaseCommonEnum;
import com.sales.ops.enums.ipsPurchase.IpsPurchaseSendSwitchEnum;
import com.sales.ops.feign.CtcSmcOrderToFeignApi;
import com.sales.ops.feign.OpsGZOrderFeignApi;
import com.sales.ops.feign.ips.IpsPurchaseSendFeignApi;
import com.sales.ops.service.ips.IpsPurchaseSendCommonService;
import com.sales.ops.service.purchase.PurchaseExportTxtService;
import com.sales.ops.service.purchase.PurchaseSendEmail;
import com.sales.ops.service.purchase.PurchaseSendOrderGZService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseSendOrderGZServiceImpl implements PurchaseSendOrderGZService {

	private final static Logger logger = LoggerFactory.getLogger(PurchaseSendOrderGZServiceImpl.class);

	@Autowired
	private OpsPurchaseinvoiceMapper opsPurchaseinvoiceMapper;

	@Autowired
	private RcvmasterMapper rcvmasterMapper;

	@Autowired
	private RcvdetailMapper rcvdetailMapper;

	@Autowired
	private OpsGZOrderFeignApi opsGZOrderFeignApi;

	@Autowired
	private PurchaseOrderDao purchaseOrderDao;

	@Autowired
	private PurchaseExportTxtService purchaseExportTxtService;

	@Autowired
	private OpsRequisitionCNDao opsRequisitionCNDao;

	@Autowired
	private PurchaseSendEmail purchaseSendEmail;

	@Autowired
	private OpsTomanuConfigMapper opsTomanuConfigMapper;

	@Autowired
	private CtcSmcOrderToFeignApi ctcSmcOrderToFeignApi;

	@Resource
	private DictDataServiceFeignApi dictDataServiceFeignApi;

	@Resource
	private IpsPurchaseSendFeignApi ipsPurchaseSendFeignApi;
	@Resource
	private IpsPurchaseSendCommonService ipsPurchaseSendCommonService;

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 工厂订单发送，到广州
	 */
	@Override
	public Integer sendOrderGZ() throws Exception {

		// 重新计算非当天采购单的出荷日
		purchaseExportTxtService.reCalExportday(2);
		// 采购预处理
		purchaseExportTxtService.sendPre(2);

		return operateGZ(null);
	}


	// bug16807 【IPS对接改造】新增OPS采购中国制造体系（含广州制造、中国制造、制六等）订单推送IPS相关需求
	// 增加字典开关的配置, 字典值：0= 只发老系统，1 = 只发送新系统，2=新老同时发
	@Override
	public Integer operateGZ(String versionLike) throws Exception {
		// bug16807 【IPS对接改造】新增OPS采购中国制造体系（含广州制造、中国制造、制六等）订单推送IPS相关需求 2025-02-26
		// 增加字典开关的配置, 字典值：0= 只发老系统，1 = 只发送新系统，2=新老同时发
		// 1.首先查询字典中的开关配置信息
		ResultVo<DataTypeVO> dictResult = dictDataServiceFeignApi.getDataTypeCodesInfo(IpsPurchaseCommonEnum.IPS_PURCHASE_SEND_DICT.getCode(), IpsPurchaseCommonEnum.GZ_SWITCH.getCode());
		if (dictResult.isSuccess() && StringUtils.isNotBlank(dictResult.getData().getExtNote1())) {
			// 获取字典配置的值,后续使用
			String swithchConfigValue = dictResult.getData().getExtNote1();
			// 判断dcit 配置的值，是否在字典中
			if (!IpsPurchaseSendSwitchEnum.isValidCode(swithchConfigValue)) {
				swithchConfigValue = IpsPurchaseSendSwitchEnum.OLD.getCode();
			}
			// bug18697 OPS向制六和上海特注品采购的订单发送批次号优化,需要重新生成的批次，重新写入map中
			String batchGzNo = "GZ";
			if (StringUtils.isNotBlank(dictResult.getData().getExtNote2())) {
				batchGzNo = dictResult.getData().getExtNote2();
			}
			OpsPurchaseinvoiceExample example = new OpsPurchaseinvoiceExample();
			if (StringUtils.isNotBlank(versionLike)) {
				example.createCriteria().andStatecodeEqualTo("0").andSmccodeIsNotNull().andSupplieridEqualTo("GZ")
						.andSendversionLike(versionLike);
			} else {
				example.createCriteria().andStatecodeEqualTo("0").andSmccodeIsNotNull().andSupplieridEqualTo("GZ");
			}
			List<OpsPurchaseinvoice> invoiceList = opsPurchaseinvoiceMapper.selectByExample(example);
			if (CollectionUtils.isNotEmpty(invoiceList)) {
				List<OrderSalesGPDto> orderSalesGPDtos = new ArrayList<>();
				// bug15422 写入CTC
				List<SMCOrderDto> smcOrderDtoList = new ArrayList<SMCOrderDto>();
				List<OpsTomanuConfig> opsTomanuConfigsList = opsTomanuConfigMapper.selectByExample(null);
				Map<String, String> ctcSupplierConfig = new HashMap<String, String>();
				if (CollectionUtils.isNotEmpty(opsTomanuConfigsList)) {
					opsTomanuConfigsList.forEach(a -> {
						ctcSupplierConfig.put(a.getSupplierid(), a.getRequesttype());
					});
				}
				invoiceList.forEach(opsInvoiceTest -> {
					// VGOrdersalesCn vgOrdersalesCn = new VGOrdersalesCn();
					OrderSalesGPDto orderSalesGPDto = new OrderSalesGPDto();
					orderSalesGPDto.setRorderno(opsInvoiceTest.getPono());
					orderSalesGPDto.setRorderitem(opsInvoiceTest.getLineitem().toString());
					orderSalesGPDto.setWorkday(simpleDateFormat.format(new Date()));
					orderSalesGPDto.setStatus("0");
					// orderSalesGPDto.setCustomerno(opsInvoiceTest.getCustomerno());
					orderSalesGPDto.setModelno(opsInvoiceTest.getModelno());
					orderSalesGPDto.setQuantity(opsInvoiceTest.getQuantity());
					orderSalesGPDto.setPrice(new BigDecimal(0));
					orderSalesGPDto.setAccount(new BigDecimal(0));
					// orderSalesGPDto.setRcvclassify("1");
					orderSalesGPDto.setStockcode("KGZ");
					orderSalesGPDto.setExpinvcode("3");
					orderSalesGPDto.setOrdtranstype("2");
					orderSalesGPDto.setDlventire("0");
					orderSalesGPDto.setTransfee("2");
					orderSalesGPDto.setTranschannel("1");
					orderSalesGPDto.setUserno(StringUtils.isNotBlank(opsInvoiceTest.getUserno())
							? opsInvoiceTest.getUserno() : opsInvoiceTest.getCustomerno());
					orderSalesGPDto.setRcvclassify(opsInvoiceTest.getPurchasetype());
					orderSalesGPDto.setOptdate(simpleDateFormat.format(new Date()));
					// bug 11329,供应商为广州制造的销售订单 发票收货仓异常问题,修改为取Hopereceivewarehouse
					orderSalesGPDto.setAddressid(opsInvoiceTest.getHopereceivewarehouse());
					// 取得期望出货日
					String dlvdate = simpleDateFormat2.format(opsInvoiceTest.getHopeexportdate());
					LocalTime localTime = LocalTime.now();
					LocalTime dealTime = LocalTime.of(12, 00, 0);
					if (localTime.isBefore(dealTime)) {
						dlvdate = dlvdate + " 10:00:00";
					} else {
						dlvdate = dlvdate + " 15:00:00";
					}

					orderSalesGPDto.setDlvdate(dlvdate);
					// 查询交易主体
					RcvmasterExample rcvmasterExample = new RcvmasterExample();
					rcvmasterExample.createCriteria().andRorderNoEqualTo(opsInvoiceTest.getOrderno());
					List<Rcvmaster> rcvmasters = rcvmasterMapper.selectByExample(rcvmasterExample);
					orderSalesGPDto.setCustomerno("C1D7V");
					if (CollectionUtils.isNotEmpty(rcvmasters)) {
						orderSalesGPDto.setOrdersourceentity(rcvmasters.get(0).getTradeCompanyid());
						// 根据交易主体翻译出客户代码
						if (StringUtils
								.isNotBlank(TradeCompanyIdEnum.getCustomerno(rcvmasters.get(0).getTradeCompanyid()))) {
							orderSalesGPDto
									.setCustomerno(TradeCompanyIdEnum.getCustomerno(rcvmasters.get(0).getTradeCompanyid()));
						}
					}

					// 2022-06-13 新增广州制造也发送跟中国一样的备注
					// 追加 JPRemarks--21 备注 富士康/vIp/客户类别/组装子项
					orderSalesGPDto.setRemark("");

					// bug 10705 修改vipCode的查询方法 B91717
					// bug12349备注增加客户所属集团
					List<OpsCustomerProperty> customerVipCode = purchaseOrderDao
							.getVIPCode(StringUtils.isNotBlank(opsInvoiceTest.getUserno()) ? opsInvoiceTest.getUserno()
									: opsInvoiceTest.getCustomerno(), opsInvoiceTest.getSupplierid());
					if (CollectionUtils.isNotEmpty(customerVipCode)) {
						if (StringUtils.isBlank(orderSalesGPDto.getRemark())) {
							orderSalesGPDto.setRemark("重点客户:" + customerVipCode.get(0).getVipcode());
						} else {
							orderSalesGPDto.setRemark(
									orderSalesGPDto.getRemark() + "/" + "重点客户:" + customerVipCode.get(0).getVipcode());
						}
						// bug12485 vipcode传输
						orderSalesGPDto.setVipcode(customerVipCode.get(0).getVipcode());
					}
					// new 获取营业所名称
					// 2022-03-09 修改营业所名称以及组装标识，暂时不写入备注字段
					// bug12349备注增加客户所属集团
					String deptName = opsRequisitionCNDao.getDepartName(opsInvoiceTest.getDeptno());
					if (StringUtils.isNotBlank(deptName)) {
						if (CollectionUtils.isNotEmpty(customerVipCode)) {
							orderSalesGPDto.setRemark(orderSalesGPDto.getRemark() + "[" + deptName + ","
									+ customerVipCode.get(0).getCustomerGroup() + "]");
						} else
							orderSalesGPDto.setRemark(orderSalesGPDto.getRemark() + "[" + deptName + "]");
					} else {
						if (CollectionUtils.isNotEmpty(customerVipCode))
							orderSalesGPDto.setRemark(
									orderSalesGPDto.getRemark() + "[" + customerVipCode.get(0).getCustomerGroup() + "]");
					}

					// bug 12485 2023-11-2新增 vipPriority 字段
					orderSalesGPDto.setVippriority(0);
					// bug 13517 增加客户订单号及客户型号
					if (StringUtils.isNotBlank(opsInvoiceTest.getInfojson())) {
						RequestPurchaseInfo info = JSONObject.parseObject(opsInvoiceTest.getInfojson(),
								RequestPurchaseInfo.class);
						if (StringUtils.isNotBlank(info.getCorderno())) {
							orderSalesGPDto.setCorderno(info.getCorderno());
						}
						if (StringUtils.isNotBlank(info.getCproductno())) {
							orderSalesGPDto.setCproductno(info.getCproductno());
						}
					}
					// 增加inqb单号传输
					orderSalesGPDto.setCqueryno(opsInvoiceTest.getInqno());

					// 改入写
					orderSalesGPDtos.add(orderSalesGPDto);

					// bug15422 广州订单也写入CTC表
					SMCOrderDto smcOrderDto = new SMCOrderDto();
					smcOrderDto.setOrderNo(opsInvoiceTest.getPono());
					smcOrderDto.setPrjno(opsInvoiceTest.getLineitem().toString());
					smcOrderDto.setModelNo(opsInvoiceTest.getModelno());
					smcOrderDto.setQty(opsInvoiceTest.getQuantity());
					smcOrderDto.setRemarks(orderSalesGPDto.getRemark());
					smcOrderDto.setDeliverydate(simpleDateFormat.format(opsInvoiceTest.getHopeexportdate()));
					smcOrderDto.setOrderdate(simpleDateFormat.format(new Date()));
					smcOrderDto.setDbname("SMCPDM");
					smcOrderDto.setStatus("0");
					// 若配置表中没有，则不传，现在广州未配置因为不赋值，后续如需配置则直接修改数据库数据即可
					if (ctcSupplierConfig.containsKey(opsInvoiceTest.getSupplierid())) {
						smcOrderDto.setProducePlace(ctcSupplierConfig.get(opsInvoiceTest.getSupplierid()));
					}
					smcOrderDtoList.add(smcOrderDto);

				});
				JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(orderSalesGPDtos));
				// bug14866先改采购状态再调用接口
				// 更新invoice 状态
				OpsPurchaseinvoice updatestateCode = new OpsPurchaseinvoice();
				updatestateCode.setStatecode(PurchaseOrderStatusEnum.YIFASONG);
				updatestateCode.setSendtime(new Date());
				// opsPurchaseinvoiceMapper.updateByExampleSelective(updatestateCode,
				// example);
				List<Long> idList = invoiceList.stream().map(a -> a.getId()).collect(Collectors.toList());
				OpsPurchaseinvoiceExample example2 = new OpsPurchaseinvoiceExample();
				List<Long> temp = new ArrayList<>();
				// 超过2000条时，分批操作
				for (int i = 0; i < idList.size(); i++) {
					if (i % 2000 == 0) {
						temp = null;
						if (i + 2000 < idList.size()) {
							temp = idList.subList(i, i + 2000);
						} else {
							temp = idList.subList(i, idList.size());
						}
						example2.clear();
						example2.createCriteria().andIdIn(temp);
						// opsPurchaseinvoiceMapper.updateByExampleSelective(updatestateCode,
						// example2);
						// bug14866 更新purchaseInvoice增加更新三次重试，避免被牺牲
						int trynum = 3;
						boolean updateresult = false;
						while (!updateresult && trynum > 0) {
							try {
								opsPurchaseinvoiceMapper.updateByExampleSelective(updatestateCode, example2);
								updateresult = true;
							} catch (Exception e) {
								trynum--;
								if (trynum == 0 && !updateresult) {
									logger.error("采购发单GZ更新purchaseinvoice三次尝试均失败。", e);
									throw e;
								}
							}
						}
					}
				}
				CommonResult<String> resultVo = new CommonResult<String>();
				String batchCnno = "";
				// 根据swithchConfigValue值，来判断发送新旧系统
				if (IpsPurchaseSendSwitchEnum.OLD.getCode().equals(swithchConfigValue)) { //只发旧系统
					// 掉用广州订单写入的api接口
					resultVo = opsGZOrderFeignApi.insertOrderGz(jsonArray);
				} else if (IpsPurchaseSendSwitchEnum.NEW.getCode().equals(swithchConfigValue)) { //只发新系统
					// 调用转换方法
					List<IpsReceiveOrderAllOriginalInfoDto> ipsSendOrderConvert = ipsPurchaseSendCommonService
							.ipsSendOrderConvert(invoiceList, ctcSupplierConfig, batchGzNo, null, null,"hopeReceiveWarehouse");
					batchCnno = ipsSendOrderConvert.get(0).getBatchNo();
					// 打印发送json信息
					logger.info("发送到IPS的json信息：" + JSON.toJSONString(ipsSendOrderConvert));
					// 调用feign接口，发送到IPS表中
					resultVo = ipsPurchaseSendFeignApi.insertPurchaseOrderToIps(ipsSendOrderConvert);
				} else { // 新旧同时发
					// 先掉用关务接口
					resultVo = opsGZOrderFeignApi.insertOrderGz(jsonArray);
					// 调用IPS转换方法
					List<IpsReceiveOrderAllOriginalInfoDto> ipsSendOrderConvert = ipsPurchaseSendCommonService
							.ipsSendOrderConvert(invoiceList, ctcSupplierConfig, batchGzNo, null, null,"hopeReceiveWarehouse");
					batchCnno = ipsSendOrderConvert.get(0).getBatchNo();
					// 打印发送json信息
					logger.info("发送到IPS的json信息：" + JSON.toJSONString(ipsSendOrderConvert));
					// 调用feign接口，发送到IPS表中
					CommonResult<String> ipsResult = ipsPurchaseSendFeignApi.insertPurchaseOrderToIps(ipsSendOrderConvert);
				}

				if (resultVo.isSuccess()) {
					// bug15422 广州订单也写入CTC表
					JSONArray ctcinfo = JSONArray.parseArray(JSON.toJSONString(smcOrderDtoList));
					ctcSmcOrderToFeignApi.insertSmcOrderCtc(ctcinfo);
					String message = "本次广州制造订单共计发送：" + invoiceList.size() + "项。 ";
					purchaseSendEmail.mailMessage(2, message);
					// 当发送PSI时，更新cnno到purchaseinvoice表中
					if (StringUtils.isNotBlank(swithchConfigValue) && !IpsPurchaseSendSwitchEnum.OLD.getCode().equals(swithchConfigValue)) {
						ipsPurchaseSendCommonService.updatePurchaseInvoiceStatus(invoiceList, batchCnno, null);
					}
				} else {
					String message = "广州制造订单发送出错，错误信息：" + resultVo.getMessage();
					purchaseSendEmail.mailMessage(3, message);
					// purchaseSendEmail.mailMessage(2,message);
					throw Exceptions.OpsException(resultVo.getMessage());
				}
				return invoiceList.size();
			}
			return 0;
		} else {
			logger.error("获取广州制造-采购发单字典配置字典 id {} 失败！", ImpInvoiceCommonEnum.IMPSYSN_DICTID.getCode());
			throw Exceptions.OpsException("获取获取广州制造--采购发单字典配置字典 id {} 失败！", ImpInvoiceCommonEnum.IMPSYSN_DICTID.getCode());
		}
	}

}
