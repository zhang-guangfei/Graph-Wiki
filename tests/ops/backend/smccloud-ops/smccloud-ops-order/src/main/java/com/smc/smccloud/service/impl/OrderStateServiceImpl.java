package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.purchase.PurchaseReplyInfo;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.PoReplyDateStrEnum;
import com.sales.ops.enums.SendStatusEnum;
import com.sales.ops.feign.WmPurchaseFeignApi;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.*;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.*;
import com.smc.smccloud.model.*;
import com.smc.smccloud.model.Department.DepartmentVO;
import com.smc.smccloud.model.OrderLog.OrderStateLogDO;
import com.smc.smccloud.model.OrderModify.OrderModifyDO;
import com.smc.smccloud.model.Purchase.OpsPurchaseOrderDO;
import com.smc.smccloud.model.VSalesManuorder.OPSVRequisitionStausToSalesDO;
import com.smc.smccloud.model.VSalesManuorder.OpsVDeliveryAnswerToSalesDO;
import com.smc.smccloud.model.VSalesManuorder.OpsVManuorderToSales;
import com.smc.smccloud.model.enums.CallBackSMSApplyTypeEnum;
import com.smc.smccloud.model.enums.OpsSalesTaskHandStatus;
import com.smc.smccloud.model.enums.OpsSalesTaskReturnStatus;
import com.smc.smccloud.model.notice.OpsSalesNoticeTaskDO;
import com.smc.smccloud.model.order.OrderCancelResult;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.order.RcvDetailDO;
import com.smc.smccloud.model.order.orderdel.SalesErpOrderDeleteResultVO;
import com.smc.smccloud.model.ordermodify.SpecialVO;
import com.smc.smccloud.model.orderstate.*;
import com.smc.smccloud.model.requestpurchase.OpsRequestpurchaseDO;
import com.smc.smccloud.model.salestask.OpsSalesCommonParamVO;
import com.smc.smccloud.model.shikomi.ShikomiPrepareDTO;
import com.smc.smccloud.model.stock.DealReturnOpsParam;
import com.smc.smccloud.model.stock.DealReturnOpsParamVO;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@Service
public class OrderStateServiceImpl implements OrderStateService {

	@Resource
	private OrderStateMapper orderStateMapper;
	@Resource
	private OrderStateDetailMapper orderStateDetailMapper;
	@Resource
	private OpsVDeliveryAnswerToSalesMapper deliveryAnswerToSalesMapper;
	@Resource
	private OpsVManuorderToSalesMapper opsVManuorderToSalesMapper;
	@Resource
	private SendMessage sendMessage;
	@Resource
	private RedisManager redisManager;
	@Resource
	private WmPurchaseFeignApi wmPurchaseFeignApi;
	@Resource
	private SMSOrderServiceFeignApi smsOrderServiceFeignApi;
	@Resource
	private OrderModifyMapper orderModifyMapper;
	@Resource
	private OPSVRequisitionStausToSalesMapper opsvRequisitionStausToSalesMapper;
	@Resource
	private PreStockFeignApi preStockFeignApi;
	@Resource
	private DictDataServiceFeignApi dictDataServiceFeignApi;
	@Resource
	private JavaMailSenderImpl javaMailSender;
	@Resource
	private ProductServiceFeignApi productServiceFeignApi;
	@Resource
	private CommonServiceFeignApi commonServiceFeignApi;
	@Resource
	private CommonService commonService;
	@Resource
	private OpsRequestpurchaseMapper opsRequestpurchaseMapper;
	@Resource
	private HttpServletResponse response;
	@Resource
	private HandDelErrorOrderMapper handDelErrorOrderMapper;
	@Resource
	private OrderStateLogService orderStateLogService;
	@Resource
	private RcvdetailMapper rcvdetailMapper;
	@Resource
	private OrderStateLogMapper orderStateLogMapper;
	@Resource
	private OpsCommonService opsCommonService;
	@Resource
	private DictCommonService dictCommonService;

	@Resource
	private OpsSalesNoticeTaskMapper opsSalesNoticeTaskMapper;

	@Resource
	private PurchaseConvertService purchaseConvertService;



	@Resource
	private SalesNotickTaskService salesNotickTaskService;

	@Value("${sendmail.flag}")
	private String sendMailFlag;

	@Value("${ftp.server}")
	private String server;
	@Value("${ftp.user}")
	private String user;
	@Value("${ftp.password}")
	private String password;

	@Value("${ops-file-upload-path.url}")
	private String filePath;

	@Override
	public ResultVo<PageInfo<OrderStateDTO>> listOrderState(OrderStateRequest request, Page page) {

		log.info("货期状态查询接口参数: " + JSONObject.toJSONString(request));

		List<String> orderNos = new ArrayList<>();
		List<String> supplierOrderNos = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(request.getOrderNos())) {
			orderNos = request.getOrderNos().stream().filter(Objects::nonNull).filter(i -> !i.equals(""))
					.collect(Collectors.toList());
		}
		// add by LiYingChao from bug8855 in 20221201
		if (CollectionUtils.isNotEmpty(request.getSupplierOrderNos())) {
			supplierOrderNos = request.getSupplierOrderNos().stream().filter(Objects::nonNull)
					.filter(i -> !i.equals("")).collect(Collectors.toList());
		}
		LambdaQueryWrapper<OrderStateDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper
				.likeRight(PublicUtil.isNotEmpty(request.getOrderNo()), OrderStateDO::getOrderNo, request.getOrderNo())
				.likeRight(judgmentConditions(request.getModelNo()), OrderStateDO::getModelNo,
						strSplit(request.getModelNo()))
				.in(CollectionUtils.isNotEmpty(orderNos), OrderStateDO::getOrderNo, orderNos)
				.in(CollectionUtils.isNotEmpty(supplierOrderNos), OrderStateDO::getSupplierOrderNo, supplierOrderNos)
				.eq(judgmentConditions2(request.getModelNo()), OrderStateDO::getModelNo, request.getModelNo())
				.in(CollectionUtils.isNotEmpty(request.getStateCode()), OrderStateDO::getStateCode,
						request.getStateCode())
				.in(CollectionUtils.isNotEmpty(request.getStatus()), OrderStateDO::getStateCode, request.getStatus())
				.in(CollectionUtils.isNotEmpty(request.getDeptCode()), OrderStateDO::getDeptNo, request.getDeptCode())
				.in(CollectionUtils.isNotEmpty(request.getPurchaseTypes()), OrderStateDO::getPurchaseType,
						request.getPurchaseTypes())
				.in(CollectionUtils.isNotEmpty(request.getWarehouseCodes()), OrderStateDO::getWarehouseCode,
						request.getWarehouseCodes())
				.in(CollectionUtils.isNotEmpty(request.getSupplierCodes()), OrderStateDO::getSupplierCode,
						request.getSupplierCodes())
				.eq(PublicUtil.isNotEmpty(request.getDeptNo()), OrderStateDO::getDeptNo, request.getDeptNo())
				.like(PublicUtil.isNotEmpty(request.getCustomerNo()), OrderStateDO::getCustomerNo,
						request.getCustomerNo())
				.eq(PublicUtil.isNotEmpty(request.getCorderNo()), OrderStateDO::getCorderNo, request.getCorderNo())
				.eq(PublicUtil.isNotEmpty(request.getCmodelNo()), OrderStateDO::getCmodelNo, request.getCmodelNo())
				.like(PublicUtil.isNotEmpty(request.getUserNo()), OrderStateDO::getUserNo, request.getUserNo())
				.in(CollectionUtils.isNotEmpty(request.getOrderTypes()), OrderStateDO::getOrderType,
						request.getOrderTypes())
				// 客户交货期
				.ge(PublicUtil.isNotEmpty(request.getCustDlvDateStart()), OrderStateDO::getCustDlvDate,
						request.getCustDlvDateStart())
				.le(PublicUtil.isNotEmpty(request.getCustDlvDateEnd()), OrderStateDO::getCustDlvDate,
						request.getCustDlvDateEnd())
				// 指定物流发送日
				.ge(PublicUtil.isNotEmpty(request.getShipDateStart()), OrderStateDO::getShipDate,
						request.getShipDateStart())
				.le(PublicUtil.isNotEmpty(request.getShipDateEnd()), OrderStateDO::getShipDate,
						request.getShipDateEnd())
				// 工厂纳期
				.ge(PublicUtil.isNotEmpty(request.getPoReplyDateStart()), OrderStateDO::getPoReplyDate,
						request.getPoReplyDateStart())
				.le(PublicUtil.isNotEmpty(request.getPoReplyDateEnd()), OrderStateDO::getPoReplyDate,
						request.getPoReplyDateEnd())
				// 指定工厂出荷日
				.ge(PublicUtil.isNotEmpty(request.getPoDlvDateStart()), OrderStateDO::getPoDlvDate,
						request.getPoDlvDateStart())
				.le(PublicUtil.isNotEmpty(request.getPoDlvDateEnd()), OrderStateDO::getPoDlvDate,
						request.getPoDlvDateEnd())
				// 供应商发出日
				.ge(PublicUtil.isNotEmpty(request.getPoShipDateStart()), OrderStateDO::getPoShipDate,
						request.getPoShipDateStart())
				.le(PublicUtil.isNotEmpty(request.getPoShipDateEnd()), OrderStateDO::getPoShipDate,
						request.getPoShipDateEnd())
				// 实际出厂日
				.ge(PublicUtil.isNotEmpty(request.getPoFacExpdateStart()), OrderStateDO::getPoFacExpdate,
						request.getPoFacExpdateStart())
				.le(PublicUtil.isNotEmpty(request.getPoFacExpdateEnd()), OrderStateDO::getPoFacExpdate,
						request.getPoFacExpdateEnd())
				// 预计到达日期
				.ge(PublicUtil.isNotEmpty(request.getEsArrivalDateStart()), OrderStateDO::getEsArrivalDate,
						request.getEsArrivalDateStart())
				.le(PublicUtil.isNotEmpty(request.getEsArrivalDateEnd()), OrderStateDO::getEsArrivalDate,
						request.getEsArrivalDateEnd())
				// 下单日期
				.ge(PublicUtil.isNotEmpty(request.getStartDate()), OrderStateDO::getOrderDate, request.getStartDate())
				.le(PublicUtil.isNotEmpty(request.getEndDate()), OrderStateDO::getOrderDate, request.getEndDate())
				// 更新时间
				.ge(PublicUtil.isNotEmpty(request.getUpdateTimeStart()), OrderStateDO::getUpdateTime,
						request.getUpdateTimeStart())
				.le(PublicUtil.isNotEmpty(request.getUpdateTimeEnd()), OrderStateDO::getUpdateTime,
						request.getUpdateTimeEnd())
				// 供应商接单时间
				.ge(PublicUtil.isNotEmpty(request.getSupplierRcvTimeStart()), OrderStateDO::getSupplierRcvTime,
						request.getSupplierRcvTimeStart())
				.le(PublicUtil.isNotEmpty(request.getSupplierRcvTimeEnd()), OrderStateDO::getSupplierRcvTime,
						request.getSupplierRcvTimeEnd())
				// 接单日期
				.ge(PublicUtil.isNotEmpty(request.getOrderDateStart()), OrderStateDO::getOrderDate,
						request.getOrderDateStart())
				.le(PublicUtil.isNotEmpty(request.getOrderDateEnd()), OrderStateDO::getOrderDate,
						request.getOrderDateEnd())
				.ge(PublicUtil.isNotEmpty(request.getDlvUpdTimeStart()), OrderStateDO::getDlvUpdtime,
						request.getDlvUpdTimeStart())
				.le(PublicUtil.isNotEmpty(request.getDlvUpdTimeEnd()), OrderStateDO::getDlvUpdtime,
						request.getDlvUpdTimeEnd());

		// add by A78027 from bug11091 in 20230625
		if (request.getSpecQryType() != null) {
			switch (request.getSpecQryType()) {
			case 1:// 返信延期
				queryWrapper.and(wrapper -> wrapper.lt(OrderStateDO::getPoReplyDate, DateUtil.getToday())
						.in(OrderStateDO::getStateCode, 22, 25));
				break;
			}
		}
		queryWrapper.orderByDesc(OrderStateDO::getOrderDate);

		PageInfo<OrderStateDO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
				.doSelectPageInfo(() -> {
					orderStateMapper.selectList(queryWrapper);
				});
		PageInfo<OrderStateDTO> orderStateDTOPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, OrderStateDTO.class);
		if (CollectionUtils.isNotEmpty(orderStateDTOPageInfo.getList())) {
			Map<String, String> map = new HashMap<>();
			for (OrderStateDTO item : orderStateDTOPageInfo.getList()) {
				item.setTransType(OPSTransTypeEnum.getNameByCode(item.getTransType()));
				// add by LiYingChao from bug 9001 in 2022-12-09
				item.setPoTransType(OPSTransTypeEnum.getNameByCode(item.getPoTransType()));
				if (StringUtils.isNotBlank(item.getSupplierCode())) {
					if (!map.containsKey(item.getSupplierCode())) {
						String supplierCodeName = opsCommonService.getSupplierNameByCode(item.getSupplierCode()) + "["
								+ item.getSupplierCode() + "]";
						map.put(item.getSupplierCode(), supplierCodeName);
					}
					item.setSupplierCode(map.get(item.getSupplierCode()));
				}
				if (StringUtils.isNotBlank(item.getPurchaseType())) {
					item.setPurchaseType(PurchaseTypeEnum.getName(item.getPurchaseType()));
				}
				if (StringUtils.isNotBlank(item.getWarehouseCode())) {
					if (!map.containsKey(item.getWarehouseCode())) {
						String warehouseName = opsCommonService.getWarehouseNameByCode(item.getWarehouseCode());
						map.put(item.getWarehouseCode(), warehouseName);
					}
					item.setWarehouseCode(map.get(item.getWarehouseCode()));
				}
				if (StringUtils.isNotBlank(item.getDeptNo())) {
					if (!map.containsKey(item.getDeptNo())) {
						String deptName = opsCommonService.getDeptNameByNo(item.getDeptNo()) + "[" + item.getDeptNo()
								+ "]";
						map.put(item.getDeptNo(), deptName);
					}
					item.setDeptNo(map.get(item.getDeptNo()));
				}
				// 特殊日期翻译
				if (item.getPoReplyDate() != null) {
					item.setPoReplyDateStr(PoReplyDateStrEnum.getDescByCode(item.getPoReplyDate()).getCode());
				}

				if (item.getPoReplyDateA() != null) {
					item.setPoReplyDateAStr(PoReplyDateStrEnum.getDescByCode(item.getPoReplyDateA()).getCode());
				}

				if (item.getPoReplyDateB() != null) {
					item.setPoReplyDateBStr(PoReplyDateStrEnum.getDescByCode(item.getPoReplyDateB()).getCode());
				}

				if (item.getPoReplyDateC() != null) {
					item.setPoReplyDateCStr(PoReplyDateStrEnum.getDescByCode(item.getPoReplyDateC()).getCode());
				}
			}
		}

		return ResultVo.success(orderStateDTOPageInfo);
	}

	@Override
	public String getStateSupplierOrderNo(String orderNo) {
		LambdaQueryWrapper<OrderStateDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.select(OrderStateDO::getSupplierOrderNo);
		queryWrapper.eq(OrderStateDO::getOrderNo, orderNo);
		List<OrderStateDO> orderStateDOS = orderStateMapper.selectList(queryWrapper);
		OrderStateDO info = null;
		if (CollectionUtil.isNotEmpty(orderStateDOS)) {
			info = orderStateDOS.get(0);
		}
		if (info != null) {
			return info.getSupplierOrderNo();
		}
		return null;
	}

	@Override
	public void exportOrderState(OrderStateRequest request) {
		long startTimer = System.currentTimeMillis();
		log.info("货期状态查询导出接口参数: " + JSONObject.toJSONString(request));

		List<String> orderNos = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(request.getOrderNos())) {
			orderNos = request.getOrderNos().stream().filter(Objects::nonNull).filter(i -> !i.equals(""))
					.collect(Collectors.toList());
		}
		// add by LiYingChao from bug8855 in 20221201
		List<String> supplierOrderNos = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(request.getSupplierOrderNos())) {
			supplierOrderNos = request.getSupplierOrderNos().stream().filter(Objects::nonNull)
					.filter(i -> !i.equals("")).collect(Collectors.toList());
		}

		Page page = new Page();
		page.setPageNumber(1);
		page.setPageSize(2000);

		LambdaQueryWrapper<OrderStateDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper
				.likeRight(PublicUtil.isNotEmpty(request.getOrderNo()), OrderStateDO::getOrderNo, request.getOrderNo())
				.likeRight(judgmentConditions(request.getModelNo()), OrderStateDO::getModelNo,
						strSplit(request.getModelNo()))
				.in(CollectionUtils.isNotEmpty(orderNos), OrderStateDO::getOrderNo, orderNos)
				.in(CollectionUtils.isNotEmpty(supplierOrderNos), OrderStateDO::getSupplierOrderNo, supplierOrderNos)
				.eq(judgmentConditions2(request.getModelNo()), OrderStateDO::getModelNo, request.getModelNo())
				.in(CollectionUtils.isNotEmpty(request.getStateCode()), OrderStateDO::getStateCode,
						request.getStateCode())
				.in(CollectionUtils.isNotEmpty(request.getStatus()), OrderStateDO::getStateCode, request.getStatus())
				.in(CollectionUtils.isNotEmpty(request.getDeptCode()), OrderStateDO::getDeptNo, request.getDeptCode())
				.in(CollectionUtils.isNotEmpty(request.getWarehouseCodes()), OrderStateDO::getWarehouseCode,
						request.getWarehouseCodes())
				.in(CollectionUtils.isNotEmpty(request.getSupplierCodes()), OrderStateDO::getSupplierCode,
						request.getSupplierCodes())
				.in(CollectionUtils.isNotEmpty(request.getSupplierCodes()), OrderStateDO::getSupplierCode,
						request.getSupplierCodes())
				.eq(PublicUtil.isNotEmpty(request.getDeptNo()), OrderStateDO::getDeptNo, request.getDeptNo())
				.like(PublicUtil.isNotEmpty(request.getCustomerNo()), OrderStateDO::getCustomerNo,
						request.getCustomerNo())
				.eq(PublicUtil.isNotEmpty(request.getCorderNo()), OrderStateDO::getCorderNo, request.getCorderNo())
				.eq(PublicUtil.isNotEmpty(request.getCmodelNo()), OrderStateDO::getCmodelNo, request.getCmodelNo())
				.like(PublicUtil.isNotEmpty(request.getUserNo()), OrderStateDO::getUserNo, request.getUserNo())
				.in(CollectionUtils.isNotEmpty(request.getOrderTypes()), OrderStateDO::getOrderType,
						request.getOrderTypes())
				// 客户交货期
				.ge(PublicUtil.isNotEmpty(request.getCustDlvDateStart()), OrderStateDO::getCustDlvDate,
						request.getCustDlvDateStart())
				.le(PublicUtil.isNotEmpty(request.getCustDlvDateEnd()), OrderStateDO::getCustDlvDate,
						request.getCustDlvDateEnd())
				// 指定物流发送日
				.ge(PublicUtil.isNotEmpty(request.getShipDateStart()), OrderStateDO::getShipDate,
						request.getShipDateStart())
				.le(PublicUtil.isNotEmpty(request.getShipDateEnd()), OrderStateDO::getShipDate,
						request.getShipDateEnd())
				// 工厂纳期
				.ge(PublicUtil.isNotEmpty(request.getPoReplyDateStart()), OrderStateDO::getPoReplyDate,
						request.getPoReplyDateStart())
				.le(PublicUtil.isNotEmpty(request.getPoReplyDateEnd()), OrderStateDO::getPoReplyDate,
						request.getPoReplyDateEnd())
				// 指定工厂出荷日
				.ge(PublicUtil.isNotEmpty(request.getPoDlvDateStart()), OrderStateDO::getPoDlvDate,
						request.getPoDlvDateStart())
				.le(PublicUtil.isNotEmpty(request.getPoDlvDateEnd()), OrderStateDO::getPoDlvDate,
						request.getPoDlvDateEnd())
				// 供应商发出日
				.ge(PublicUtil.isNotEmpty(request.getPoShipDateStart()), OrderStateDO::getPoShipDate,
						request.getPoShipDateStart())
				.le(PublicUtil.isNotEmpty(request.getPoShipDateEnd()), OrderStateDO::getPoShipDate,
						request.getPoShipDateEnd())
				// 实际出厂日
				.ge(PublicUtil.isNotEmpty(request.getPoFacExpdateStart()), OrderStateDO::getPoFacExpdate,
						request.getPoFacExpdateStart())
				.le(PublicUtil.isNotEmpty(request.getPoFacExpdateEnd()), OrderStateDO::getPoFacExpdate,
						request.getPoFacExpdateEnd())
				// 预计到达日期
				.ge(PublicUtil.isNotEmpty(request.getEsArrivalDateStart()), OrderStateDO::getEsArrivalDate,
						request.getEsArrivalDateStart())
				.le(PublicUtil.isNotEmpty(request.getEsArrivalDateEnd()), OrderStateDO::getEsArrivalDate,
						request.getEsArrivalDateEnd())
				// 下单日期
				.ge(PublicUtil.isNotEmpty(request.getStartDate()), OrderStateDO::getOrderDate, request.getStartDate())
				.le(PublicUtil.isNotEmpty(request.getEndDate()), OrderStateDO::getOrderDate, request.getEndDate())
				// 更新时间
				.ge(PublicUtil.isNotEmpty(request.getUpdateTimeStart()), OrderStateDO::getUpdateTime,
						request.getUpdateTimeStart())
				.le(PublicUtil.isNotEmpty(request.getUpdateTimeEnd()), OrderStateDO::getUpdateTime,
						request.getUpdateTimeEnd())
				// 供应商接单时间
				.ge(PublicUtil.isNotEmpty(request.getSupplierRcvTimeStart()), OrderStateDO::getSupplierRcvTime,
						request.getSupplierRcvTimeStart())
				.le(PublicUtil.isNotEmpty(request.getSupplierRcvTimeEnd()), OrderStateDO::getSupplierRcvTime,
						request.getSupplierRcvTimeEnd())
				// 接单日期
				.ge(PublicUtil.isNotEmpty(request.getOrderDateStart()), OrderStateDO::getOrderDate,
						request.getOrderDateStart())
				.le(PublicUtil.isNotEmpty(request.getOrderDateEnd()), OrderStateDO::getOrderDate,
						request.getOrderDateEnd());
		queryWrapper.orderByDesc(OrderStateDO::getOrderDate);
		// List<OrderStateDO> orderStateDOS =
		// orderStateMapper.selectList(queryWrapper);
		// List<OrderStateDO> orderStateDOS = new ArrayList<>();
		PageInfo<OrderStateDO> pageInfo;
		String path = "templates/exportOrderState.xlsx";
		ExcelUtil excel = new ExcelUtil(path);
		excel.openSheet(0);

		// 从字典获取交货期状态配置
		ResultVo<List<DataCodeVO>> dataCodes = dictCommonService
				.getDataCodes(com.smc.smccloud.model.Constants.DICT_CLASSCODE_ORDERSTATE);
		if (!dataCodes.isSuccess() || CollectionUtils.isEmpty(dataCodes.getData())) {
			return;
		}
		Map<String, String> map = new HashMap<>();
		for (DataCodeVO item : dataCodes.getData()) {
			map.put(item.getCode(), item.getCodeName());
		}
		// 向模板中写入数据
		int row = 1;
		while (true) {
			pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize()).doSelectPageInfo(() -> {
				orderStateMapper.selectList(queryWrapper);
			});
			if (CollectionUtils.isEmpty(pageInfo.getList())) {
				return;
			}
			row = writeExcelForOrderState(pageInfo.getList(), excel, row, map);
			// orderStateDOS.addAll(pageInfo.getList());
			if (pageInfo.isIsLastPage()) {
				break;
			}
			if (page.getPageNumber() == 40 && pageInfo.getPages() > 40) {
				excel.setCellValue(row, 0,
						"共计还有" + (pageInfo.getPages() - page.getPageNumber()) * page.getPageSize() + "条数据未导出");
				break;
			}
			page.setPageNumber(page.getPageNumber() + 1);
		}
		Workbook workBook = excel.getWorkBook();
		Sheet sheet = workBook.getSheet("Sheet1");
		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 6));

		// if (CollectionUtils.isEmpty(orderStateDOS)) {
		// return;
		// }
		// int cel;
		// String stateCodeStr;
		// String orderTypeName;
		// Map<String,String> map = new HashMap<>();

		// for (OrderStateDO item : orderStateDOS) {
		//
		// stateCodeStr =
		// OrderStateEnum.getStateNameByCode(item.getStateCode());
		//
		// // 运输方式名称
		// if (StringUtils.isNotBlank(item.getTransType())) {
		// item.setTransType(OPSTransTypeEnum.getNameByCode(item.getTransType()));
		// }
		//
		// // 收货仓库名称
		// if (StringUtils.isNotBlank(item.getWarehouseCode())) {
		// if (!map.containsKey(item.getWarehouseCode())) {
		// String wareHouseName =
		// commonService.getWarehouseNameByCode(item.getWarehouseCode()) + "[" +
		// item.getWarehouseCode() + "]";
		// map.put(item.getWarehouseCode(),wareHouseName);
		// }
		// item.setWarehouseCode(map.get(item.getWarehouseCode()));
		// }
		//
		// if (StringUtils.isNotBlank(item.getCustomerNo())) {
		// if (!map.containsKey(item.getCustomerNo())) {
		// String customerName =
		// commonService.getCustomerNameByNo(item.getCustomerNo()) + "[" +
		// item.getCustomerNo() + "]";
		// map.put(item.getCustomerNo(),customerName);
		// }
		// item.setCustomerNo(map.get(item.getCustomerNo()));
		// }
		//
		// if (StringUtils.isNotBlank(item.getUserNo())) {
		// if (item.getUserNo().equals(item.getCustomerNo())) {
		// item.setUserNo(item.getCustomerNo());
		// } else {
		// if(!map.containsKey(item.getUserNo())) {
		// String userName = commonService.getCustomerNameByNo(item.getUserNo())
		// + "[" + item.getUserNo() + "]";
		// map.put(item.getUserNo(),userName);
		// }
		// item.setUserNo(map.get(item.getUserNo()));
		// }
		// }
		//
		// if (StringUtils.isNotBlank(item.getDeptNo())) {
		// if (!map.containsKey(item.getDeptNo())) {
		// String deptName = commonService.getDeptNameByNo(item.getDeptNo()) +
		// "[" + item.getDeptNo() + "]";
		// map.put(item.getDeptNo(),deptName);
		// }
		// item.setDeptNo(map.get(item.getDeptNo()));
		// }
		//
		// if (StringUtils.isNotBlank(item.getSupplierCode())) {
		// if (!map.containsKey(item.getSupplierCode())) {
		// String supplierCodeName =
		// commonService.getSupplierNameByCode(item.getSupplierCode()) + "[" +
		// item.getSupplierCode() + "]";
		// map.put(item.getSupplierCode(),supplierCodeName);
		// }
		// item.setSupplierCode(map.get(item.getSupplierCode()));
		// }
		// orderTypeName =
		// OrderTypeEnum.getCodeName(String.valueOf(item.getOrderType()));
		//
		// cel = 0;
		// excel.setCellValue(row, cel++, item.getOrderNo());
		// excel.setCellValue(row, cel++, stateCodeStr);
		// excel.setCellValue(row, cel++, item.getStateDes());
		// excel.setCellValue(row, cel++, item.getModelNo());
		// excel.setCellValue(row, cel++, item.getQuantity());
		// excel.setCellValue(row, cel++, item.getSupplierCode());
		//
		// excel.setCellValue(row, cel++, item.getCustShipDate()); //指定物流发货日
		// excel.setCellValue(row, cel++, item.getCustDlvDate()); // 客户交货期
		// excel.setCellValue(row, cel++, item.getPoDlvDate()); // 指定工厂出荷日
		// excel.setCellValue(row, cel++, item.getPoReplyDate()); // 工厂纳期
		// excel.setCellValue(row, cel++, item.getPoReplyDateA()); // 工厂纳期变更1
		// excel.setCellValue(row, cel++, item.getPoReplyDateB()); // 工厂纳期变更2
		// excel.setCellValue(row, cel++, item.getPoReplyDateC()); // 工厂纳期变更3
		// excel.setCellValue(row, cel++, item.getSupplierRcvTime()); // 供应商接单日
		// excel.setCellValue(row, cel++, item.getOrderDate()); // 订单接单日
		// excel.setCellValue(row, cel++, item.getBeginProduceDate()); // 开始生产日
		// excel.setCellValue(row, cel++, item.getPoShipDate()); // 实际出厂日
		// excel.setCellValue(row, cel++, item.getSupplierOrderNo()); // 手配号
		// excel.setCellValue(row, cel++, item.getTransType());
		// excel.setCellValue(row, cel++, item.getWarehouseCode());
		// excel.setCellValue(row, cel++, item.getPoInvoiceNo());
		// excel.setCellValue(row, cel++, item.getCustomerNo());
		// excel.setCellValue(row, cel++, item.getUserNo());
		// excel.setCellValue(row, cel++, item.getCmodelNo());
		// excel.setCellValue(row, cel++, item.getCorderNo());
		// excel.setCellValue(row, cel++, item.getDeptNo());
		// excel.setCellValue(row, cel++, item.getShikomiNo());
		// excel.setCellValue(row, cel++, item.getPoDelayDay());
		// excel.setCellValue(row, cel++, item.getArrivalDelayDay());
		// excel.setCellValue(row, cel++, item.getEsArrivalDate()); // 预计到达日期
		// excel.setCellValue(row, cel++, orderTypeName);
		// excel.setCellValue(row, cel++, item.getPurchaseType());
		// excel.setCellValue(row, cel++, item.getProvider());
		// excel.setCellValue(row, cel++, item.getCreateTime());
		// excel.setCellValue(row, cel, item.getUpdateTime());
		// row++;
		// }
		excel.writeToResponse(response, "exportOrderState.xlsx");
		long endTimer = System.currentTimeMillis();
		log.info("交货期数据导出耗时: " + (endTimer - startTimer) / 1000 + " 秒");

	}

	/**
	 * 交货期导出写入excel
	 */
	public int writeExcelForOrderState(List<OrderStateDO> orderStateDOS, ExcelUtil excel, int row,
			Map<String, String> dictMap) {

		String stateCodeStr;
		String orderTypeName;
		Map<String, String> map = new HashMap<>();
		int cel;
		for (OrderStateDO item : orderStateDOS) {

			if (item.getStateCode() != null && dictMap.containsKey(String.valueOf(item.getStateCode()))) {
				stateCodeStr = dictMap.get(String.valueOf(item.getStateCode()));
			} else {
				stateCodeStr = String.valueOf(item.getStateCode());
			}
			// 运输方式名称
			if (StringUtils.isNotBlank(item.getTransType())) {
				item.setTransType(OPSTransTypeEnum.getNameByCode(item.getTransType()));
			}
			// add by LiYingChao from bug 9001 in 2022-12-09
			if (StringUtils.isNotBlank(item.getPoTransType())) {
				item.setPoTransType(OPSTransTypeEnum.getNameByCode(item.getPoTransType()));
			}
			// 收货仓库名称
			if (StringUtils.isNotBlank(item.getWarehouseCode())) {
				if (!map.containsKey(item.getWarehouseCode())) {
					String wareHouseName = item.getWarehouseCode() + "["
							+ opsCommonService.getWarehouseNameByCode(item.getWarehouseCode()) + "]";
					map.put(item.getWarehouseCode(), wareHouseName);
				}
				item.setWarehouseCode(map.get(item.getWarehouseCode()));
			}

			if (StringUtils.isNotBlank(item.getCustomerNo())) {
				if (!map.containsKey(item.getCustomerNo())) {
					String customerName = item.getCustomerNo() + "["
							+ opsCommonService.getCustomerNameByNo(item.getCustomerNo()) + "]";
					map.put(item.getCustomerNo(), customerName);
				}
				item.setCustomerNo(map.get(item.getCustomerNo()));
			}

			if (StringUtils.isNotBlank(item.getUserNo())) {
				if (item.getUserNo().equals(item.getCustomerNo())) {
					item.setUserNo(item.getCustomerNo());
				} else {
					if (!map.containsKey(item.getUserNo())) {
						String userName = item.getUserNo() + "["
								+ opsCommonService.getCustomerNameByNo(item.getUserNo()) + "]";
						map.put(item.getUserNo(), userName);
					}
					item.setUserNo(map.get(item.getUserNo()));
				}
			}

			if (StringUtils.isNotBlank(item.getDeptNo())) {
				if (!map.containsKey(item.getDeptNo())) {
					String deptName = item.getDeptNo() + "[" + opsCommonService.getDeptNameByNo(item.getDeptNo()) + "]";
					map.put(item.getDeptNo(), deptName);
				}
				item.setDeptNo(map.get(item.getDeptNo()));
			}

			if (StringUtils.isNotBlank(item.getSupplierCode())) {
				if (!map.containsKey(item.getSupplierCode())) {
					String supplierCodeName = item.getSupplierCode() + "["
							+ opsCommonService.getSupplierNameByCode(item.getSupplierCode()) + "]";
					map.put(item.getSupplierCode(), supplierCodeName);
				}
				item.setSupplierCode(map.get(item.getSupplierCode()));
			}

			String poReplyDateStr = PoReplyDateStrEnum.getDescByCode(item.getPoReplyDate()).getCode();

			orderTypeName = OrderTypeEnum.getCodeName(String.valueOf(item.getOrderType()));

			cel = 0;
			excel.setCellValue(row, cel++, item.getOrderNo());
			excel.setCellValue(row, cel++, stateCodeStr);
			excel.setCellValue(row, cel++, item.getStateDes());
			excel.setCellValue(row, cel++, item.getModelNo());
			excel.setCellValue(row, cel++, item.getQuantity());
			excel.setCellValue(row, cel++, item.getSupplierCode());
			excel.setCellValue(row, cel++, item.getWarehouseCode()); // 收货仓库
			excel.setCellValue(row, cel++, item.getCustShipDate()); // 指定物流发货日
			excel.setCellValue(row, cel++, item.getCustDlvDate()); // 客户交货期
			excel.setCellValue(row, cel++, item.getPoDlvDate()); // 指定工厂出荷日
			excel.setCellValue(row, cel++, DateUtil.dateToString(item.getExpectedProductionCompletionDateH())); // 工厂预计完工日
			excel.setCellValue(row, cel++, DateUtil.dateToString(item.getExpectedLogisticsArrivalDateW())); // 工厂预计入库日
			excel.setCellValue(row, cel++, poReplyDateStr); // 工厂纳期
			excel.setCellValue(row, cel++, item.getPoReplyDateA()); // 工厂纳期变更1
			excel.setCellValue(row, cel++, item.getPoReplyDateB()); // 工厂纳期变更2
			excel.setCellValue(row, cel++, item.getPoReplyDateC()); // 工厂纳期变更3
			excel.setCellValue(row, cel++, item.getSupplierRcvTime()); // 供应商接单日
			excel.setCellValue(row, cel++, item.getOrderDate()); // 订单接单日
			excel.setCellValue(row, cel++, item.getBeginProduceDate()); // 开始生产日
			excel.setCellValue(row, cel++, item.getPoFacExpdate()); // 实际出厂日
			excel.setCellValue(row, cel++, item.getPoShipDate()); // 供应商发出日
			excel.setCellValue(row, cel++, item.getPoExpType()); // 捆包
			excel.setCellValue(row, cel++, item.getSupplierOrderNo()); // 手配号
			excel.setCellValue(row, cel++, item.getTransType());
			excel.setCellValue(row, cel++, item.getPoTransType());
			excel.setCellValue(row, cel++, item.getPoInvoiceNo());
			excel.setCellValue(row, cel++, item.getCustomerNo());
			excel.setCellValue(row, cel++, item.getUserNo());
			excel.setCellValue(row, cel++, item.getCmodelNo());
			excel.setCellValue(row, cel++, item.getCorderNo());
			excel.setCellValue(row, cel++, item.getDeptNo());
			excel.setCellValue(row, cel++, item.getShikomiNo());
			excel.setCellValue(row, cel++, item.getPoDelayDay());
			excel.setCellValue(row, cel++, item.getArrivalDelayDay());
			excel.setCellValue(row, cel++, item.getEsArrivalDate()); // 预计到达日期
			excel.setCellValue(row, cel++, orderTypeName);
			excel.setCellValue(row, cel++, item.getPurchaseType());
			excel.setCellValue(row, cel++, item.getProvider());
			excel.setCellValue(row, cel++, item.getCreateTime());
			excel.setCellValue(row, cel++, item.getUpdateTime());
			row++;
		}
		return row;
	}

	@Override
	public ResultVo<String> addOrderState(OrderStateVO orderStateVO) {
		if (null == orderStateVO) {
			return ResultVo.failure("保存订单状态失败");
		}
		RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
		rabbitMqMessage.setContent(JSON.toJSONString(orderStateVO));
		rabbitMqMessage.setFlag(Constants.OPS_ORDER_STATE);
		rabbitMqMessage.setDataType(Constants.OPS_ORDER);
		rabbitMqMessage.setSystem(Constants.OPS);
		boolean sendResult = sendMessage.sendOrderStateMsg(rabbitMqMessage);

		if (sendResult) {
			return ResultVo.success("发送成功");
		}
		return ResultVo.failure("发送失败");
	}

	// @Transactional
	@Override
	public ResultVo<String> rcvOrderStateMQ(OrderStateVO orderStateVO) {
		if (orderStateVO == null) {
			return ResultVo.failure("参数为空,接入状态管理失败");
		}

		if (StringUtils.isNotBlank(orderStateVO.getOrderNo()) && orderStateVO.getOrderNo().endsWith("-null")) {
			orderStateVO.setOrderNo(orderStateVO.getOrderNo().substring(0, orderStateVO.getOrderNo().indexOf("-null")));
		}
		if (PublicUtil.isEmpty(orderStateVO.getStateCode())) {
			return ResultVo.failure("状态为空");
		}

		OrderStateDO updOrderStateDO = BeanCopyUtil.copy(orderStateVO, OrderStateDO.class);
		if (updOrderStateDO.getSplitNo() == null) {
			updOrderStateDO.setSplitNo(0);
		}
		if (updOrderStateDO.getItemNo() == null || updOrderStateDO.getItemNo() == 0) {
			return ResultVo.failure("项号为空或零,异常数据不接入");
		}

		if (StringUtils.isNotBlank(updOrderStateDO.getOptUserName())
				&& updOrderStateDO.getOptUserName().length() > 10) {
			updOrderStateDO.setOptUserName(updOrderStateDO.getOptUserName().substring(0, 10));
		}

		LambdaQueryWrapper<OrderStateDO> queryWrapper = new LambdaQueryWrapper<>();

		if (StringUtils.isNotBlank(updOrderStateDO.getRorderNo()) && updOrderStateDO.getItemNo() != null
				&& updOrderStateDO.getSplitNo() != null) {
			queryWrapper.eq(OrderStateDO::getRorderNo, updOrderStateDO.getRorderNo());
			queryWrapper.eq(OrderStateDO::getItemNo, updOrderStateDO.getItemNo());
			queryWrapper.eq(OrderStateDO::getSplitNo, updOrderStateDO.getSplitNo());
		} else {
			if (StringUtils.isNotBlank(updOrderStateDO.getOrderNo())) {
				queryWrapper.eq(OrderStateDO::getOrderNo, updOrderStateDO.getOrderNo());
			} else {
				return ResultVo.failure("更新失败,单号为空");
			}
		}
		queryWrapper.orderByDesc(OrderStateDO::getUpdateTime);
		List<OrderStateDO> orderStateDOS = orderStateMapper.selectList(queryWrapper);
		OrderStateDO curOrderStateDO;
		if (CollectionUtils.isEmpty(orderStateDOS)) {
			curOrderStateDO = null;
		} else {
			curOrderStateDO = orderStateDOS.get(0);
		}

		// OrderStateDO curOrderStateDO =
		// orderStateMapper.selectList(queryWrapper);

		if (updOrderStateDO.getUpdateTime() == null) {
			updOrderStateDO.setUpdateTime(new Date());
		}

		// add by LiYingChao from bug8466 in 20221026
		if (updOrderStateDO.getStateCode() == OrderStateEnum.CanceledNotConfirm.code()
				&& orderStateVO.getPurchaseFlag() != 1) {
			Boolean aBoolean = getRcvDetailStatusByOrderNo(orderStateVO, curOrderStateDO);
			if (!aBoolean) {
				return ResultVo.failure("接单表未删单,不更新货期状态和回调门户");
			}
		}
		try {
			if (null == curOrderStateDO) {

				if (updOrderStateDO.getStateCode() == null
						|| updOrderStateDO.getStateCode() >= OrderStateEnum.SupplierInProd.getCode()) {
					return ResultVo.failure("第一次写入状态不应该是生产之后");
				}
				if (StringUtils.isBlank(updOrderStateDO.getModelNo())) {
					return ResultVo.failure("型号为空不写入.");
				}

				if (updOrderStateDO.getIntercept() == null) {
					updOrderStateDO.setIntercept(false);
				}

				if (updOrderStateDO.getPurchaseFlag() == 1) {
					if (updOrderStateDO.getSplitNo() != null && updOrderStateDO.getSplitNo() != 0) {
						updOrderStateDO.setOrderNo(updOrderStateDO.getRorderNo() + "-" + updOrderStateDO.getItemNo()
								+ "-" + updOrderStateDO.getSplitNo());
					} else {
						updOrderStateDO.setOrderNo(updOrderStateDO.getRorderNo() + "-" + updOrderStateDO.getItemNo());
					}
				}

				if (updOrderStateDO.getItemNo() == null
						&& (updOrderStateDO.getSplitNo() == null || updOrderStateDO.getSplitNo() == 0)) {
					OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(updOrderStateDO.getOrderNo());
					updOrderStateDO.setItemNo(orderNoInfo.getItemNo());
					updOrderStateDO.setSplitNo(orderNoInfo.getSplitItem());
				}

				if (StringUtils.isBlank(updOrderStateDO.getEndUser())) {
					if (StringUtils.isNotBlank(updOrderStateDO.getUserNo())) {
						updOrderStateDO.setEndUser(updOrderStateDO.getUserNo());
					} else if (StringUtils.isNotBlank(updOrderStateDO.getCustomerNo())) {
						updOrderStateDO.setEndUser(updOrderStateDO.getCustomerNo());
					}
				}
				orderStateMapper.insert(updOrderStateDO);
			} else
			{

				/**
				 * 13663bug update 2024-03-06 解决 当delivery解析状态为31完工发出时
				 * 再次接入shpinf文件 状态为30 数据无法接入
				 */
				if (updOrderStateDO.getStateCode().equals(OrderStateEnum.SupplierFinishProd.getCode())
						&& curOrderStateDO.getStateCode().equals(OrderStateEnum.SupplierShipped.getCode())) {
					updOrderStateDO.setStateCode(OrderStateEnum.SupplierShipped.getCode());
				} else if (!isAllowChangeStatus(updOrderStateDO.getStateCode(), curOrderStateDO.getStateCode())) {
					// 对比前状态，根据配置判断是否可以更改当前状态
					log.info("{},不允许变更为 {}, {}", curOrderStateDO.getStateCode(), updOrderStateDO.getStateCode(),
							JSONUtil.toJsonStr(updOrderStateDO));
					return ResultVo.success(curOrderStateDO.getStateCode() + "不允许变更为" + updOrderStateDO.getStateCode());
				}

				if (updOrderStateDO.getStateCode() == OrderStateEnum.CanceledNotConfirm.getCode()
						&& StringUtils.isNotBlank(updOrderStateDO.getOptUserName())
						&& updOrderStateDO.getOptUserName().contains("变更订单状态")) {
					if (curOrderStateDO.getStateCode() == OrderStateEnum.CanceledNotConfirm.getCode()
							&& (StringUtils.isNotBlank(curOrderStateDO.getOptUserName())
									|| StringUtils.isNotBlank(curOrderStateDO.getOptUserNo()))) {
						updOrderStateDO.setOptUserName(curOrderStateDO.getOptUserName());
						updOrderStateDO.setOptUserNo(curOrderStateDO.getOptUserNo());
					}
				}
				// <!--add by WuWeiDong 20231124 bug 12645 营业信息查询空指针错误 -->
				// orderstate的消息的状态36发票入库,或者31发出，41已收货
				// 只有YZ并当前导入供应商是CN不更改orderstate的供应商，其他都根据发票的供应商更改到orderstate里
				List<Integer> limitStateCode = Arrays.asList(31, 36, 41);
				if ("YZ".equalsIgnoreCase(curOrderStateDO.getSupplierCode())
						&& "CN".equalsIgnoreCase(updOrderStateDO.getSupplierCode())
						&& (limitStateCode.contains(updOrderStateDO.getStateCode()))) {
					updOrderStateDO.setSupplierCode("YZ");
				}
				/**
				 * 校验供应商 若供应商与采购供应商不同，且非中国制造内部转订，则不修改供应商
				 */
				if (!isUpSupplier2(updOrderStateDO)) {
					return ResultVo.success(updOrderStateDO.getOrderNo() + "->" + "不允许变更供应商");
				}

				// 设置上次默认值
				updOrderStateDO.setDlvUpdtime(curOrderStateDO.getDlvUpdtime());
				updOrderStateDO.setId(curOrderStateDO.getId());

				if (curOrderStateDO.getItemNo() == null && curOrderStateDO.getSplitNo() == null) {
					OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(curOrderStateDO.getOrderNo());
					updOrderStateDO.setItemNo(orderNoInfo.getItemNo());
					updOrderStateDO.setSplitNo(orderNoInfo.getSplitItem());
				}

				// if (curOrderStateDO.getOrderType() != null) {
				// updOrderStateDO.setOrderType(curOrderStateDO.getOrderType());
				// }

				if ((updOrderStateDO.getOrderType() == null && curOrderStateDO.getOrderType() != null)
						|| (updOrderStateDO.getOrderType() != null && updOrderStateDO.getOrderType() == 99)) {
					updOrderStateDO.setOrderType(curOrderStateDO.getOrderType());
				}

				if (StringUtils.isBlank(curOrderStateDO.getEndUser())) {
					if (StringUtils.isNotBlank(curOrderStateDO.getUserNo())) {
						updOrderStateDO.setEndUser(curOrderStateDO.getUserNo());
					} else if (StringUtils.isNotBlank(curOrderStateDO.getCustomerNo())) {
						updOrderStateDO.setEndUser(curOrderStateDO.getCustomerNo());
					}
				}

				if (curOrderStateDO.getIntercept() == null) {
					curOrderStateDO.setIntercept(false);
				}

				updOrderStateDO.setOrderNo(curOrderStateDO.getOrderNo());

				// 较上次延迟天数
				Integer changeLateDay = getDelayPOReplyDate(updOrderStateDO, curOrderStateDO);
				if (changeLateDay > 0) {
					updOrderStateDO.setStateDes(updOrderStateDO.getStateDes() + " 较上次延迟" + changeLateDay + "天");
				}

				// 发出采购单会传原运输方式, 后续不再变更此字段.
				if (StringUtils.isNotBlank(curOrderStateDO.getPoTransType())) {
					updOrderStateDO.setPoTransType(curOrderStateDO.getPoTransType());
				}
				orderStateMapper.updateById(updOrderStateDO);

			}

			if (StringUtils.isBlank(orderStateVO.getOrderNo())) {
				orderStateVO.setOrderNo(updOrderStateDO.getOrderNo());
			}

			if (curOrderStateDO == null) {
				updateOrderStateDetail(orderStateVO, orderStateVO);
			} else {
				updateOrderStateDetail(orderStateVO, BeanCopyUtil.copy(curOrderStateDO, OrderStateVO.class));
			}

		} catch (Exception e) {
			log.error("更新交货期数据处理异常", e);
			log.error("[写入订单状态异常] 错误的订单号: {}, 错误数据: {}", orderStateVO.getOrderNo(),
					JSONObject.toJSONString(orderStateVO));
			throw new BusinessException(e.getMessage(), e);
		}

		if (orderStateVO.getStateCode() == null) {
			return ResultVo.success("接入数据成功");
		}

		if (updOrderStateDO.getOrderType() == null && curOrderStateDO != null) {
			updOrderStateDO.setOrderType(curOrderStateDO.getOrderType());
		}
		if (updOrderStateDO.getOrderType() == null) {
			updOrderStateDO.setOrderType(0);
		}

		try {

			ResultVo<DataTypeVO> dataTypeCodesInfoWithDS = dictCommonService.getDataTypeCodesInfoWithDS("9002", "34");
			if (!dataTypeCodesInfoWithDS.isSuccess()) {
				return ResultVo.failure(dataTypeCodesInfoWithDS.getMessage());
			}

			if ("1".equals(dataTypeCodesInfoWithDS.getData().getExtNote1())) {
				if (updOrderStateDO.getStateCode() == OrderStateEnum.CanceledNotConfirm.code() && (String
						.valueOf(updOrderStateDO.getOrderType()).equals(OrderTypeEnum.saleOrder.getCode())
						|| String.valueOf(updOrderStateDO.getOrderType()).equals(OrderTypeEnum.ypOrder.getCode()))) {
					Thread.sleep(2000); // 删单队列可能执行 让order_modify等会 防止相互交叉更新
					log.info(sendMailFlag + "环境,取消订单");
					log.info("删单[orderState]是否采购单 : " + orderStateVO.getPurchaseFlag() + "  : "
							+ JSONObject.toJSONString(orderStateVO));
					if (orderStateVO.getPurchaseFlag() != 1) {
						LambdaQueryWrapper<OrderModifyDO> orderModifyQuery = new LambdaQueryWrapper<>();
						orderModifyQuery.eq(OrderModifyDO::getOrderNo, updOrderStateDO.getOrderNo())
								.eq(OrderModifyDO::getModifyType, OrderModifyTypeEnum.cancel_order.getCode());
						List<OrderModifyDO> orderModifylist = orderModifyMapper.selectList(orderModifyQuery);
						/**
						 * 1. 门户存在删单申请, task里肯定有 根据订单修改里的批次号 查询task 将成功删单的回调参数修改  再次进行回调
						 *  2. 门户不存在,写入订单修改
						 */
						if (CollectionUtils.isEmpty(orderModifylist)) {
							insertModifyWithOpsDelOrder(updOrderStateDO, curOrderStateDO, "");
							// 写入task 统一回调门户
							// String batchNo = PublicUtil.getRandomBatchNo("T", new
							// Date());
							String batchNo = UIDGenerator.generateUID();
							insertNotickTaskWithOpsDelOrder(updOrderStateDO, batchNo);
						} else {
							for (OrderModifyDO item : orderModifylist) {
								if (OrderModifyEnum.handing.getCode().equals(String.valueOf(item.getStatus()))
										|| OrderModifyEnum.waitHand.getCode().equals(String.valueOf(item.getStatus()))
										|| OrderModifyEnum.notHand.getCode().equals(String.valueOf(item.getStatus()))) {
									LambdaUpdateWrapper<OrderModifyDO> updateWrapper = new LambdaUpdateWrapper<>();
									updateWrapper.set(OrderModifyDO::getAnswerTime, DateUtil.getNow())
											.set(OrderModifyDO::getStatus,
													Integer.parseInt(OrderModifyEnum.finish.getCode()))
											.set(OrderModifyDO::getAnswerText,
													Optional.ofNullable(updOrderStateDO.getStateDes()).orElse(""))
											.set(OrderModifyDO::getAnswerPns,
													Optional.ofNullable(updOrderStateDO.getOptUserNo()).orElse(""));
									updateWrapper.eq(OrderModifyDO::getId, item.getId());
									orderModifyMapper.update(null, updateWrapper);

									// 根据批次号修改task的回调参数
									upTaskCallBackParam(item,
											Optional.ofNullable(updOrderStateDO.getStateDes()).orElse(""));
								}
							}
						}
					}
				}
				if (updOrderStateDO.getStateCode() == OrderStateEnum.CanceledNotConfirm.code()) {
					if (StringUtils.isNotBlank(sendMailFlag) && sendMailFlag.equals("prod")) {

						/**
						 * add by LiYingChao from bug8724 in 20221121
						 * 接单处删除的消息，所有都发 purchaseFlag = 0
						 *  采购处删除的，不发客户订单 purchaseFlag = 1
						 */
						if (orderStateVO.getPurchaseFlag() == 0) {
							// 取消集团订单发送邮件
							if (updOrderStateDO.getOrderType().equals(Integer.valueOf(OrderTypeEnum.gnjtOrder.getCode()))) {
								sendMainForCancelCNOrder(updOrderStateDO);
							}
							// 取消广州制造订单发送邮件
							if (StringUtils.isNotBlank(updOrderStateDO.getCustomerNo())
									&& "C1D72".equalsIgnoreCase(updOrderStateDO.getCustomerNo())) {
								sendMailForGZ(updOrderStateDO);
							} else {
								if (curOrderStateDO != null && StringUtils.isNotBlank(curOrderStateDO.getCustomerNo())
										&& "C1D72".equalsIgnoreCase(curOrderStateDO.getCustomerNo())) {
									sendMailForGZ(updOrderStateDO);
								}
							}
							this.sendCancelPrestockAndCustomerOrderEmail(updOrderStateDO);
						}

						if (orderStateVO.getPurchaseFlag() == 1) {
							// 取消先行在库及客户订单发送邮件
							if (updOrderStateDO.getOrderType().equals(Integer.valueOf(OrderTypeEnum.xxbkOrder.getCode()))
									|| updOrderStateDO.getOrderType()
									.equals(Integer.valueOf(OrderTypeEnum.binbkOrder.getCode()))
									|| updOrderStateDO.getOrderType()
									.equals(Integer.valueOf(OrderTypeEnum.wtzkOrder.getCode()))) {
								this.sendCancelPrestockAndCustomerOrderEmail(updOrderStateDO);
							}
						}
					}
					// 取消预约shikomi
					ShikomiPrepareDTO dto = new ShikomiPrepareDTO();
					dto.setShikomiNo(updOrderStateDO.getShikomiNo());
					dto.setSupplierCode(updOrderStateDO.getSupplierCode());
					dto.setOrderNo(updOrderStateDO.getOrderNo());
					dto.setQuantity(updOrderStateDO.getQuantity());
					productServiceFeignApi.cancelPrepareShikomi(dto);
				}

				// 状态修改共享给广州制造
				if ("C1D72".equalsIgnoreCase(updOrderStateDO.getCustomerNo())) {
					if (StringUtils.isBlank(updOrderStateDO.getCorderNo()) && curOrderStateDO != null
							&& StringUtils.isNotBlank(curOrderStateDO.getCorderNo())) {
						updOrderStateDO.setCorderNo(curOrderStateDO.getCorderNo());
					}
					sendOrderStateToGZProd(updOrderStateDO);
				}


				if (StringUtils.isNotBlank(sendMailFlag) && sendMailFlag.equals("prod")) {
					// 发送采购拦截邮件通知
					if (updOrderStateDO.getStateCode().equals(OrderStateEnum.PurchaseFault.code())) {
						sendShikomiInterceptMsgEmail(updOrderStateDO);
					}
				}

				// 20240510 增加开关 bug 14112 交货期调用updateinvoice方法增加开关
				ResultVo<DataTypeVO> dataTypeCodesInfoWithCatch = dictCommonService
						.getDataTypeCodesInfoWithCatch(CommonConstants.classCode_9002, CommonConstants.code_22);
				if (dataTypeCodesInfoWithCatch.isSuccess() && dataTypeCodesInfoWithCatch.getData() != null) {
					String extNote1 = dataTypeCodesInfoWithCatch.getData().getExtNote1();
					if (StringUtils.isNotBlank(extNote1) && "1".equals(extNote1)) {
						// 更新采购订单状态
						toUpdatePOOrderState(updOrderStateDO);
					}
				}

				if (orderStateVO.getPurchaseFlag() != 3) {
					// 先行在库订单状态变更处理
					if (updOrderStateDO.getOrderType().equals(Integer.valueOf(OrderTypeEnum.xxbkOrder.getCode()))
							|| updOrderStateDO.getOrderType().equals(Integer.valueOf(OrderTypeEnum.wtzkOrder.getCode()))) {
						this.preStockPurchaseOrderStateHandle(updOrderStateDO);
					}
				}
			}


			// 回复中国制造订单状态
//			if (updOrderStateDO.getOrderType().equals(Integer.valueOf(OrderTypeEnum.gnjtOrder.getCode()))) {
//				if (curOrderStateDO != null && StringUtils.isBlank(updOrderStateDO.getCustomerNo())) {
//					updOrderStateDO.setCustomerNo(curOrderStateDO.getCustomerNo());
//				}
//				replyCNMOrderState(updOrderStateDO);
//			}

		} catch (Exception e) {
			log.error("[订单状态变更处理异常] 错误的订单号: {}, 错误数据: {}", orderStateVO.getOrderNo(),
					JSONObject.toJSONString(orderStateVO));
			log.error("订单状态变更处理异常" + e);
			throw new BusinessException(e.getMessage(), e);
		}

		// 转发合并订单消息
		resendMergeOrderState(orderStateVO);

		return ResultVo.success("接入数据成功");
	}

	public void upTaskCallBackParam(OrderModifyDO orderModifyInfo, String remark) {
		if (StringUtils.isBlank(orderModifyInfo.getBatchNo())) {
			return;
		}
		SpecialVO specialVO = null;
		if (StringUtils.isNotBlank(orderModifyInfo.getSpecial())) {
			specialVO = JSONObject.parseObject(orderModifyInfo.getSpecial(), SpecialVO.class);
		}

		DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
		dealReturnOpsParamVO.setApplyType(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode());

		DealReturnOpsParam param = new DealReturnOpsParam();

		SalesErpOrderDeleteResultVO vo = new SalesErpOrderDeleteResultVO();

		if (StringUtils.isBlank(orderModifyInfo.getApplyNo())) {
			vo.setApplyItemNo("OPS_DEL");
		} else {
			vo.setApplyItemNo(orderModifyInfo.getApplyNo());
		}

		vo.setOrderNo(orderModifyInfo.getOrderNo());
		if (specialVO != null) {
			vo.setSecondProcess(specialVO.getSecondApproval());
		}
		vo.setStatus(CancelOrderToSalesStatus.del_success.getCode());
		vo.setStatusDescription(remark);

		param.setSalesErpOrderDeleteResultVo(vo);

		dealReturnOpsParamVO.setDealReturnOpsParam(param);

		OpsSalesCommonParamVO bean = new OpsSalesCommonParamVO();
		bean.setData(dealReturnOpsParamVO);

		LambdaUpdateWrapper<OpsSalesNoticeTaskDO> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(OpsSalesNoticeTaskDO::getBatchNo, orderModifyInfo.getBatchNo())
				.set(OpsSalesNoticeTaskDO::getCallBackParameter, JSONUtil.toJsonStr(bean))
				.set(OpsSalesNoticeTaskDO::getReturnStatus, OpsSalesTaskHandStatus.notHand.getCode())
				.set(OpsSalesNoticeTaskDO::getUpdateTime, new Date());
		opsSalesNoticeTaskMapper.update(null, updateWrapper);
	}

	// ops删单写入task表
	public ResultVo<String> insertNotickTaskWithOpsDelOrder(OrderStateDO orderStateDO, String batchNo) {
		OpsSalesNoticeTaskDO taskDO = new OpsSalesNoticeTaskDO();
		taskDO.setBatchNo(batchNo);
		taskDO.setBusinessCode(OrderModifyTypeEnum.cancel_order.getCode());
		taskDO.setOrderFno(orderStateDO.getOrderNo());

		DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
		dealReturnOpsParamVO.setApplyType(CallBackSMSApplyTypeEnum.ORDER_DELETE.getCode());

		DealReturnOpsParam param = new DealReturnOpsParam();

		SalesErpOrderDeleteResultVO vo = new SalesErpOrderDeleteResultVO();

		vo.setApplyItemNo("OPS_DEL");
		vo.setOrderNo(orderStateDO.getOrderNo());
		vo.setSecondProcess(false);
		vo.setStatus(CancelOrderToSalesStatus.del_success.getCode());
		vo.setStatusDescription(orderStateDO.getStateDes());

		param.setSalesErpOrderDeleteResultVo(vo);

		dealReturnOpsParamVO.setDealReturnOpsParam(param);

		OpsSalesCommonParamVO bean = new OpsSalesCommonParamVO();
		bean.setData(dealReturnOpsParamVO);

		taskDO.setCallBackParameter(JSONUtil.toJsonStr(bean));

		Date nowDate = new Date();
		taskDO.setHandleStartTime(nowDate);
		taskDO.setHandleStatus(OpsSalesTaskHandStatus.hand_success.getCode());
		taskDO.setCreateTime(nowDate);
		taskDO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
		taskDO.setTryCount(0);
		taskDO.setErrorHandCount(0);
		taskDO.setSource(CommonConstants.COMMON_USER_OPS);
		opsSalesNoticeTaskMapper.insert(taskDO);

		return ResultVo.success("写入成功");
	}

	// ops删单写入OrderModify
	public ResultVo<String> insertModifyWithOpsDelOrder(OrderStateDO orderStateDO, OrderStateDO curOrderStateDO,
			String batchNo) {
		OrderModifyDO orderModifyDO = new OrderModifyDO();
		orderModifyDO.setBatchNo(batchNo);
		orderModifyDO.setModifyType(OrderModifyTypeEnum.cancel_order.getCode());
		orderModifyDO.setStatus(Integer.valueOf(OrderModifyEnum.finish.getCode()));
		orderModifyDO.setOrderNo(orderStateDO.getOrderNo());
		orderModifyDO.setDeptNo(curOrderStateDO.getDeptNo());
		orderModifyDO.setCustomerNo(curOrderStateDO.getCustomerNo());
		orderModifyDO.setChangeType(OrderModifyTypeEnum.cancel_order.getCode());
		orderModifyDO.setAnswerText(orderStateDO.getStateDes());
		orderModifyDO.setCreateUser(CommonConstants.COMMON_USER_OPS);
		orderModifyDO.setCreateTime(new Date());
		OpsPurchaseOrderDO purchaseOrderInfo = salesNotickTaskService.getPurchaseOrderInfo(orderStateDO.getOrderNo());
		if (purchaseOrderInfo != null) {
			orderModifyDO.setSupplierOrderNo(purchaseOrderInfo.getReplyOrderNo());
		}
		// orderModifyDO.setRemark(orderStateDO.getStateDes());
		orderModifyMapper.insert(orderModifyDO);
		return ResultVo.success("写入成功");
	}

	/**
	 * true 可以更 false 不可以
	 * 
	 * @param orderStateDO
	 * @return
	 */
	public boolean isUpSupplier2(OrderStateDO orderStateDO) {
		if (StringUtils.isBlank(orderStateDO.getSupplierCode())) {
			return true;
		}
		OpsPurchaseOrderDOCommon purOrderInfo = opsCommonService.getPurOrderInfo(orderStateDO.getRorderNo(),
				orderStateDO.getItemNo(), orderStateDO.getSplitNo());
		if (purOrderInfo == null) {
			return true;
		}
		ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(CommonConstants.ZGGCSUPPLIER);
		if (!dataCodes.isSuccess() || CollectionUtil.isEmpty(dataCodes.getData())) {
			return false;
		}
		List<String> codes = dataCodes.getData().stream().map(DataCodeVO::getCode).collect(Collectors.toList());
		// 与采购一样 可以变更
		if (orderStateDO.getSupplierCode().equals(purOrderInfo.getSupplierId())) {
			return true;
		}
		// 都是国内 可以变更
		if (codes.contains(orderStateDO.getSupplierCode()) && codes.contains(purOrderInfo.getSupplierId())) {
			return true;
		} else {
			return false;
		}
	}

	public void isUpSupplier(OrderStateDO orderStateDO) {
		if (StringUtils.isNotBlank(orderStateDO.getSupplierCode())) {
			OpsPurchaseOrderDOCommon purOrderInfo = opsCommonService.getPurOrderInfo(orderStateDO.getRorderNo(),
					orderStateDO.getItemNo(), orderStateDO.getSplitNo());
			if (purOrderInfo != null) {
				ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(CommonConstants.ZGGCSUPPLIER);
				if (!dataCodes.isSuccess() || CollectionUtil.isEmpty(dataCodes.getData())) {
					return;
				}
				List<String> codes = dataCodes.getData().stream().map(DataCodeVO::getCode).collect(Collectors.toList());
				if (!orderStateDO.getSupplierCode().equals(purOrderInfo.getSupplierId())
						&& !codes.contains(orderStateDO.getSupplierCode())) {
					orderStateDO.setSupplierCode(purOrderInfo.getSupplierId());
				}
			}
		}
	}

	// 根据采购合并单号获取其原始单号 (查询合并采购单的请购单)
	public List<String> getPoOrderNoByMrNoSendMq(String mrNo) {
		List<String> list = new ArrayList<>();
		// List<String> requestPurchaseIdByMrNo =
		// opsRequestpurchaseMapper.getRequestPurchaseIdByMrNo(mrNo);
		// if (CollectionUtils.isEmpty(requestPurchaseIdByMrNo)) {
		// return list;
		// }
		// LambdaQueryWrapper<OpsRequestpurchaseDO> queryWrapper = new
		// LambdaQueryWrapper<>();
		// queryWrapper.in(OpsRequestpurchaseDO::getId,
		// requestPurchaseIdByMrNo);
		// List<OpsRequestpurchaseDO> opsRequestpurchaseDOS =
		// opsRequestpurchaseMapper.selectList(queryWrapper);
		// bug13662 废除采购合并表，请购表中增加采购单号
		List<OpsRequestpurchaseDO> opsRequestpurchaseDOS = opsRequestpurchaseMapper.getRequestPurchaseByMrNo(mrNo);

		for (OpsRequestpurchaseDO item : opsRequestpurchaseDOS) {
			String orderNo = "";
			if (item.getSplitItemNo() != null && item.getSplitItemNo() != 0) {
				orderNo = item.getOrderNo() + "-" + item.getItemNo() + "-" + item.getSplitItemNo();
			} else {
				orderNo = item.getOrderNo() + "-" + item.getItemNo();
			}
			list.add(orderNo);
		}
		return list;

	}

	// 记录货期明细
	@Override
	public void updateOrderStateDetail(OrderSateDateType dateType, OrderStateVO orderStateVO, Date updDate) {
		LambdaQueryWrapper<OrderStateDetailDO> query = new LambdaQueryWrapper<>();
		query.select(OrderStateDetailDO::getId, OrderStateDetailDO::getOrderNo, OrderStateDetailDO::getDateType,
				OrderStateDetailDO::getStateDate, OrderStateDetailDO::getChangeTimes);
		query.eq(OrderStateDetailDO::getDateType, dateType.code())
				.eq(OrderStateDetailDO::getOrderNo, orderStateVO.getOrderNo())
				.orderByDesc(OrderStateDetailDO::getUpdateTime);
		List<OrderStateDetailDO> orderStateDetailDOS = orderStateDetailMapper.selectList(query);
		OrderStateDetailDO orderStateDetailDO;
		if (CollectionUtils.isEmpty(orderStateDetailDOS)) {
			orderStateDetailDO = new OrderStateDetailDO();
			orderStateDetailDO.setDateType(dateType.code());
			orderStateDetailDO.setDateName(dateType.dateName());
			orderStateDetailDO.setOrderNo(orderStateVO.getOrderNo());
			orderStateDetailDO.setFirstDate(updDate);
			orderStateDetailDO.setMaxDate(updDate);
			orderStateDetailDO.setMinDate(updDate);
			orderStateDetailDO.setStateDate(updDate);
			orderStateDetailDO.setStateDes(orderStateVO.getStateDes());
			orderStateDetailDO.setUpdateTime(new Date());
			orderStateDetailDO.setCreateTime(new Date());
			orderStateDetailDO.setProvider(orderStateVO.getProvider());
			orderStateDetailDO.setChangeTimes(0);
			orderStateDetailDO.setDelayDay(0);
			orderStateDetailMapper.insert(orderStateDetailDO);
		} else {
			orderStateDetailDO = orderStateDetailDOS.get(0);
			orderStateDetailDO.setProvider(orderStateVO.getProvider());
			orderStateDetailDO.setDateName(dateType.dateName());
			Date curDate = orderStateDetailDO.getStateDate();
			// 没有变化
			if (0 == updDate.compareTo(curDate)) {
				return;
			}
			if (updDate.after(curDate)) {
				orderStateDetailDO.setMaxDate(updDate);
				if (orderStateDetailDO.getStateDate() != null && updDate != null) {
					orderStateDetailDO
							.setDelayDay(DateUtil.getDiffDay(orderStateDetailDO.getStateDate(), updDate).intValue());
				}
				if (orderStateDetailDO.getChangeTimes() != null) {
					orderStateDetailDO.setChangeTimes(orderStateDetailDO.getChangeTimes() + 1);
				}
			} else {
				orderStateDetailDO.setMinDate(updDate);
				orderStateDetailDO.setDelayDay(0);
			}
			orderStateDetailDO.setUpdateTime(new Date());
			orderStateDetailDO.setProvider(orderStateVO.getProvider());
			orderStateDetailMapper.updateById(orderStateDetailDO);
		}

	}

	@Override
	public ResultVo<List<String>> canDelOrderStatus() {
		List<String> list = opsCommonService.canDelOrderStatus();
		return ResultVo.success(list);
	}

	/**
	 * 增加接单表是否删单的逻辑判断,并写入日志[仅状态为90[删单]]
	 */
	// add by LiYingChao from bug8466 in 20221026
	public Boolean getRcvDetailStatusByOrderNo(OrderStateVO orderStateVO, OrderStateDO orderStateDO) {
		// 1.判断接单表是否已删单
		if (orderStateDO == null) {
			return false;
		}
		LambdaQueryWrapper<RcvDetailDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.select(RcvDetailDO::getStatus).eq(RcvDetailDO::getRorderFno, orderStateDO.getOrderNo());
		RcvDetailDO rcvDetailDO = rcvdetailMapper.selectOne(lambdaQueryWrapper);
		if (rcvDetailDO == null) {
			return false;
		}
		if (rcvDetailDO.getStatus() != 9) {
			return false;
		}
		try {
			// 2.写入删单日志
			orderStateLogService.insertOrderStateLog(orderStateVO, "mq队列统一入口删单消费");
		} catch (Exception e) {
			log.error("mq消费增加删单日志:", e);
			return true;
		}
		return true;
	}

	/**
	 * 工厂纳期是否发生改变
	 *
	 * @param updOrderStateDO
	 * @param curOrderStateDO
	 * @return 0 -未改变 大于1 - 延迟
	 */
	public Integer getDelayPOReplyDate(OrderStateDO updOrderStateDO, OrderStateDO curOrderStateDO) {
		if (updOrderStateDO.getPoReplyDate() == null) {
			return 0;
		}

		if (curOrderStateDO.getPoReplyDate() == null) {
			return 0;
		}

		// 一样
		Long diffDay = 0L;

		if (isSpecJPRepleDlvDate(updOrderStateDO.getPoReplyDate())
				|| isSpecJPRepleDlvDate(curOrderStateDO.getPoReplyDate())) {
			diffDay = 0L;

			// 两个都是一样的特色日期,直接返回
			if (updOrderStateDO.getPoReplyDate().equals(curOrderStateDO.getPoReplyDate())) {
				return 0;
			}
		} else {
			diffDay = DateUtil.getDiffDay(curOrderStateDO.getPoReplyDate(), updOrderStateDO.getPoReplyDate());

			// updOrderStateDO.setPoReplyDate(DateUtil.getDate(updOrderStateDO.getPoReplyDate()));

			// 相同日期不更新
			if (diffDay == 0) {
				return 0;
			}
		}

		if (curOrderStateDO.getPoReplyDateA() == null) {
			updOrderStateDO.setPoReplyDateA(curOrderStateDO.getPoReplyDate());
			updOrderStateDO.setDlvUpdtime(updOrderStateDO.getUpdateTime());
		} else if (curOrderStateDO.getPoReplyDateB() == null) {
			updOrderStateDO.setPoReplyDateB(curOrderStateDO.getPoReplyDate());
			updOrderStateDO.setDlvUpdtime(updOrderStateDO.getUpdateTime());
		} else if (curOrderStateDO.getPoReplyDateC() == null) {
			updOrderStateDO.setPoReplyDateC(curOrderStateDO.getPoReplyDate());
			updOrderStateDO.setDlvUpdtime(updOrderStateDO.getUpdateTime());
		} else {
			updOrderStateDO.setPoReplyDateA(curOrderStateDO.getPoReplyDateB());
			updOrderStateDO.setPoReplyDateB(curOrderStateDO.getPoReplyDateC());
			updOrderStateDO.setPoReplyDateC(curOrderStateDO.getPoReplyDate());
			updOrderStateDO.setDlvUpdtime(updOrderStateDO.getUpdateTime());
		}
		return diffDay.intValue();
	}

	/**
	 * 取消先行在库及客户订单发送邮件
	 */
	private void sendCancelPrestockAndCustomerOrderEmail(OrderStateDO orderStateDO) {
		log.info("取消先行在库及客户订单发送邮件: {}", JSONObject.toJSONString(orderStateDO));
		Object o = redisManager.hGet(OrderConstants.EMAILORDER_CANCEL, orderStateDO.getOrderNo());
		if (o != null) { // 防止重复发邮件
			return;
		}
		String deptNo;
		String customerNo;
		String orderType;
		String email = "";
		String deptEmail = "";
		String bc = "";
		LambdaQueryWrapper<OrderStateDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(OrderStateDO::getOrderNo, orderStateDO.getOrderNo());
		OrderStateDO orderState = orderStateMapper.selectOne(queryWrapper);
		deptNo = orderState.getDeptNo();
		customerNo = orderState.getCustomerNo();
		orderType = String.valueOf(orderState.getOrderType());

		if (StringUtils.isBlank(orderStateDO.getOrderNo())) {
			return;
		}

		if (orderStateDO.getOrderNo().startsWith("99") && OrderTypeEnum.xxbkOrder.getCode().equals(orderType)) {
			orderType = OrderTypeEnum.binbkOrder.getCode();
		}
		if (orderStateDO.getOrderNo().startsWith("V")) {
			orderType = "3";
		}

		List<OrderStateDO> doList = orderStateMapper.getOrderCancelData(orderStateDO.getOrderNo());
		if (CollectionUtil.isNotEmpty(doList)) {
			OrderStateDO modifyData = doList.get(0);
			if (StringUtils.isNotBlank(modifyData.getDeptNo())) {
				deptNo = modifyData.getDeptNo();
				ResultVo<DepartmentVO> resultVo = commonServiceFeignApi.getDepartmentInfo(deptNo);
				if (resultVo.isSuccess()) {
					if (PublicUtil.isNotEmpty(resultVo.getData())) {
						email = resultVo.getData().getEmailOrder(); // 客户订单
					}
					customerNo = modifyData.getCustomerNo();
					orderType = String.valueOf(modifyData.getOrderType());
				}
			}
		}

		if ("1".equals(orderType)) {
			ResultVo<DepartmentVO> resultVo = commonServiceFeignApi.getDepartmentInfo(deptNo);
			if (resultVo.isSuccess()) {
				if (PublicUtil.isNotEmpty(resultVo.getData())) {
					email = resultVo.getData().getEmailOrder(); // 客户订单
				}
			}
		}

		if (!orderType.equals("1")) {
			if (StringUtils.isBlank(orderStateDO.getOrderNo())) {
				return;
			}
			OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderStateDO.getOrderNo());
			OrderStateDO stateDO = orderStateMapper.getPurchaseOrderByNo(orderNoInfo.getOrderNo(),
					String.valueOf(orderNoInfo.getItemNo()));
			if (stateDO == null) {
				return;
			}
			customerNo = stateDO.getCustomerNo();
			deptNo = stateDO.getDeptNo();
		}

		if (orderType.equals("21")) {
			ResultVo<DepartmentVO> resultVo = commonServiceFeignApi.getDepartmentInfo(deptNo);
			String warehouseType = commonService.getWarehouseType(orderState.getWarehouseCode());
			if ("SUB".equalsIgnoreCase(warehouseType)) {
				orderType = orderType + "-2"; // 分库补库
				email = resultVo.getData() == null ? "" : resultVo.getData().getEmailSubStock();
			} else {
				orderType = orderType + "-1"; // 在库补库
				email = resultVo.getData() == null ? "" : resultVo.getData().getEmailUserStock();
			}
		}
		if (orderType.equals("3")) {
			ResultVo<DepartmentVO> resultVo = commonServiceFeignApi.getDepartmentInfo(deptNo);
			email = resultVo.getData() == null ? "" : resultVo.getData().getEmailCSStock();
		}

		if (PublicUtil.isNotEmpty(deptNo)) {
			ResultVo<DepartmentVO> resultVo = commonServiceFeignApi.getDepartmentInfo(deptNo);
			deptEmail = resultVo.getData() == null ? "" : resultVo.getData().getEmailAddr();
		}

		if (email == null) {
			email = "";
		}

		ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(CommonConstants.XXZK_MAIL_SEND);

		if (dataCodes.isSuccess() && CollectionUtils.isNotEmpty(dataCodes.getData())) {
			List<DataCodeVO> data = dataCodes.getData();
			Map<String, DataCodeVO> dataCodeVOMap = data.stream()
					.collect(Collectors.toMap(DataCodeVO::getCode, Function.identity()));
			if (dataCodeVOMap.containsKey(orderType)) {
				DataCodeVO dataCodeVO = dataCodeVOMap.get(orderType);
				email += dataCodeVO.getExtNote1();
				if (StringUtils.isNotBlank(dataCodeVO.getExtNote2())) {
					bc = dataCodeVO.getExtNote2();
				}
			}
		}
		// if (orderType.equals("21-1")) {
		// email +=
		// ";linlijia@smc.com.cn;wengweijie@smc.com.cn;jichunfei@smc.com.cn;sub-inventory@smc.com.cn";
		// }
		// if (orderType.equals("21-2")) {
		// email += ";sub-inventory@smc.com.cn";
		// }
		// if (orderType.equals("3")) {
		// email += ";consignmentsh@smc.com.cn;isod13@smc.com.cn";
		// bc = ";isod02@smc.com.cn";
		// }
		// if (orderType.equals("20")) {
		// email = "xuxiaoying@smc.com.cn";
		// }
		// Object o1 = redisManager.hGet("ops:sendmsemail", orderType);
		// if (o1 != null) {
		// bc = o1.toString();
		// }
		// Object object = redisManager.hGet("ops:sendemail", orderType);
		// if (object != null) {
		// email += object.toString();
		// }
		if (PublicUtil.isNotEmpty(deptEmail)) {
			email += ";" + deptEmail;
		}
		if (email.startsWith(";")) {
			email = email.substring(email.indexOf(";") + 1);
		}
		if (StringUtils.isBlank(email)) {
			return;
		}

		// 获取邮箱
		String strContent = " 先行在库及客户订单删除通知邮件.";
		String content = "<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >"
				+ "<thead style=\"font-weight: bold;color: #000000;background-color: #B8CCE4;\" >" + "<tr>"
				+ "<td width=\"10%\" >营业所</td>" + "<td width=\"15%\" >订单号</td>" + "<td width=\"10%\" >型号</td>"
				+ "<td width=\"5%\" >数量</td>" + "<td width=\"10%\" >客户代码</td>" + "<td width=\"10%\" >客户名称</td>"
				+ "<td width=\"25%\" >删除原因</td>" + "<td width=\"15%\" >删除时间</td>" + "</tr>" + "</thead><tbody>" + "<tr>"
				+ "<td>" + commonService.getDeptNameByNo(deptNo) + "</td>" + "<td>" + orderStateDO.getOrderNo()
				+ "</td>" + "<td>" + orderStateDO.getModelNo() + "</td>" + "<td>"
				+ (orderStateDO.getQuantity() == null ? "" : orderStateDO.getQuantity()) + "</td>" + "<td>"
				+ (customerNo == null ? "" : customerNo) + "</td>" + "<td>"
				+ (customerNo == null ? "" : commonService.getCustomerNameByNo(customerNo)) + "</td>" + "<td>"
				+ orderStateDO.getStateDes() + "</td>" + "<td>" + DateUtil.dateToDateTimeString(
						orderStateDO.getUpdateTime() == null ? new Date() : orderStateDO.getUpdateTime())
				+ "</td>" +
				// "<td>" + deptEmail + "</td>" +
				"</tr>" + "</tbody></table></br>";
		log.info("先行在库及客户订单删除'" + orderStateDO.getOrderNo() + "'发送至邮件:" + email);
		if (StringUtils.isNotBlank(email)) {
            OpsMailDO opsMailDO = new OpsMailDO();
            opsMailDO.setMailTo(email.replaceAll(";", ","));
            opsMailDO.setSubject(strContent);
            opsMailDO.setContext(content);
            opsMailDO.setSendDate(new Date());
            // opsMailDO.setCc(dataTypeVO.getExtNote2().replaceAll(";",","));
            opsMailDO.setBcc("webservice@smcgz.com.cn");
            opsMailDO.setStatus(SendStatusEnum.INIT.getType());
            // opsMailDO.setFileUrls(pathFile+File.separator+fileName);
            opsMailDO.setInsertTime(new Date());
            opsCommonService.insertOpsMail(opsMailDO);
			// EmailUtil.send(javaMailSender, email, null, "webservice@smcgz.com.cn" + bc, strContent, content);
			redisManager.hPut(OrderConstants.EMAILORDER_CANCEL, orderStateDO.getOrderNo(), email);
		} else {
			log.error("先行在库及客户订单删除" + orderStateDO.getOrderNo() + "发送至邮件失败, == > 邮箱为空.");
		}
	}

	/**
	 * 取消集团订单发送邮件
	 */
	public void sendMainForCancelCNOrder(OrderStateDO orderStateDO) {
		log.info("进入取消国内集团发送邮件: " + JSONObject.toJSONString(orderStateDO));

		/*
		 * Object o = redisManager.hGet(OrderConstants.EMAILORDER_CANCEL,
		 * orderStateDO.getOrderNo()); if (o !=null) { //防止重复发邮件 return; }
		 */

		LambdaQueryWrapper<OrderStateDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(OrderStateDO::getOrderNo, orderStateDO.getOrderNo());
		OrderStateDO orderState = orderStateMapper.selectOne(queryWrapper);

		StringBuilder content = new StringBuilder();
		content.append("<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >")
				.append("<thead style=\"font-weight: bold;color: #000000;background-color: #B8CCE4;\" >")
				.append("<tr>" + "<td width=\"15%\" >订单号</td>" + "<td width=\"10%\" >型号</td>"
						+ "<td width=\"5%\" >数量</td>" + "<td width=\"10%\" >订货部门</td>" + "<td width=\"10%\" >下单人</td>"
						+ "<td width=\"25%\" >删除原因</td>" + "<td width=\"15%\" >删除时间</td>" + "</tr>")
				.append("</thead><tbody>");
		content.append("<tr>").append("<td>").append(orderStateDO.getOrderNo()).append("</td>").append("<td>")
				.append(orderStateDO.getModelNo()).append("</td>").append("<td>")
				.append(orderStateDO.getQuantity() == null ? "" : orderStateDO.getQuantity()).append("</td>")
				.append("<td>").append(orderState.getOrderPsnDept() == null ? "" : orderState.getOrderPsnDept())
				.append("</td>").append("<td>")
				.append(StringUtils.isBlank(orderState.getOrderPsnNo()) ? "" : orderState.getOrderPsnNo())
				.append("</td>").append("<td>").append(orderStateDO.getStateDes()).append("</td>").append("<td>")
				.append(DateUtil.dateToDateTimeString(orderStateDO.getUpdateTime() == null ? new Date()
						: orderStateDO.getUpdateTime()))
				.append("</td>").append("</tr>");
		content.append("</tbody></table></br>");
		// 获取邮箱
		ResultVo<DataTypeVO> cnc = dictDataServiceFeignApi.getDataTypeCodesInfo("9004", "CNC");
		String strEamil = "zhizao6dd3@smc.com.cn;zhangchunmei@smc.com.cn;liyanchun@smc.com.cn;qianzhenzhen@smc.com.cn";
		String strContent = "中国制造订单删除通知邮件.";
		if (!cnc.isSuccess()) {
			EmailUtil.send(javaMailSender, strEamil, "", "2355766573@qq.com;webservice@smcgz.com.cn", strContent,
					content.toString());
		} else {
			strEamil = cnc.getData().getExtNote1();
			EmailUtil.send(javaMailSender, strEamil, cnc.getData().getExtNote2(), strContent, content.toString());
		}
		// redisManager.hPut(OrderConstants.EMAILORDER_CANCEL,
		// orderStateDO.getOrderNo(), strEamil);
	}

	/**
	 * 取消广州制造订单发送邮件
	 */
	public void sendMailForGZ(OrderStateDO orderStateDO) {

		// 防止重复发邮件
		/*
		 * Object o = redisManager.hGet(OrderConstants.EMAILORDER_CANCEL,
		 * orderStateDO.getOrderNo()); if (o !=null) { return; }
		 */

		LambdaQueryWrapper<OrderStateDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(OrderStateDO::getOrderNo, orderStateDO.getOrderNo());
		OrderStateDO orderState = orderStateMapper.selectOne(queryWrapper);

		StringBuilder content = new StringBuilder();
		content.append("<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >")
				.append("<thead style=\"font-weight: bold;color: #000000;background-color: #B8CCE4;\" >")
				.append("<tr>" + "<td width=\"15%\" >订单号</td>" + "<td width=\"10%\" >型号</td>"
						+ "<td width=\"5%\" >数量</td>" + "<td width=\"10%\" >订货部门</td>" + "<td width=\"10%\" >下单人</td>"
						+ "<td width=\"25%\" >删除原因</td>" + "<td width=\"15%\" >删除时间</td>" + "</tr>")
				.append("</thead><tbody>");
		content.append("<tr>").append("<td>").append(orderStateDO.getOrderNo()).append("</td>").append("<td>")
				.append(orderStateDO.getModelNo()).append("</td>").append("<td>")
				.append(orderStateDO.getQuantity() == null ? "" : orderStateDO.getQuantity()).append("</td>")
				.append("<td>").append(orderState.getOrderPsnDept() == null ? "" : orderState.getOrderPsnDept())
				.append("</td>").append("<td>")
				.append(StringUtils.isBlank(orderState.getOrderPsnNo()) ? "" : orderState.getOrderPsnNo())
				.append("</td>").append("<td>").append(orderStateDO.getStateDes()).append("</td>").append("<td>")
				.append(DateUtil.dateToDateTimeString(orderStateDO.getUpdateTime() == null ? new Date()
						: orderStateDO.getUpdateTime()))
				.append("</td>").append("</tr>");
		content.append("</tbody></table></br>");
		// 获取邮箱
		ResultVo<DataTypeVO> cnc = dictDataServiceFeignApi.getDataTypeCodesInfo("9004", "GP");
		String strEamil = "jiangjingjing@smc.com.cn;mairuixia@smc.com.cn";
		String strContent = "广州制造订单删除通知邮件.";
		if (!cnc.isSuccess()) {
			EmailUtil.send(javaMailSender, strEamil, "", "webservice@smcgz.com.cn;isod02@smc.com.cn", strContent,
					content.toString());
		} else {
			strEamil = cnc.getData().getExtNote1();
			EmailUtil.send(javaMailSender, strEamil, cnc.getData().getExtNote2(),
					"webservice@smcgz.com.cn;isod02@smc.com.cn", strContent, content.toString());
		}
		// redisManager.hPut(OrderConstants.EMAILORDER_CANCEL,
		// orderStateDO.getOrderNo(), strEamil);
	}

	/**
	 * 发送采购拦截邮件通知
	 */
	public void sendShikomiInterceptMsgEmail(OrderStateDO orderStateDO) {

		if (!orderStateDO.getStateDes().toUpperCase().contains("SHIKOMI")) {
			return;
		}

		log.info("进入SHIKOMI删单拦截 ==>> 参数: {}", JSONObject.toJSONString(orderStateDO));

		OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderStateDO.getOrderNo());
		LambdaQueryWrapper<OpsRequestpurchaseDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(OpsRequestpurchaseDO::getOrderNo, orderNoInfo.getOrderNo()).eq(OpsRequestpurchaseDO::getItemNo,
				orderNoInfo.getItemNo());
		List<OpsRequestpurchaseDO> opsRequestpurchaseDOS = opsRequestpurchaseMapper.selectList(queryWrapper);
		if (CollectionUtils.isEmpty(opsRequestpurchaseDOS)) {
			return;
		}

		OpsRequestpurchaseDO opsRequestpurchaseDO = opsRequestpurchaseDOS.get(0);

		if (StringUtils.isBlank(opsRequestpurchaseDO.getDeptNo())) {
			return;
		}
		log.info("进入SHIKOMI删单拦截 ==>> 请购单: {}", JSONObject.toJSONString(opsRequestpurchaseDO));
		// 获取营业所邮箱
		ResultVo<DepartmentVO> departmentInfo = commonServiceFeignApi
				.getDepartmentInfo(opsRequestpurchaseDO.getApplyDeptNo());
		if (!departmentInfo.isSuccess() || StringUtils.isBlank(departmentInfo.getData().getEmailAddr())) {
			return;
		}
		DepartmentVO departmentVO = departmentInfo.getData();
		log.info("进入SHIKOMI删单拦截 ==>> 部门信息: {}", JSONObject.toJSONString(departmentVO));
		// 收件人邮箱
		StringBuilder email = new StringBuilder();
		// 抄送人邮箱
		StringBuilder ccEmail = new StringBuilder();
		// 密送人邮箱
		// String mmEmail = "2355766573@qq.com";
		// 先行在库补库单88*以及客户订单
		if (OrderTypeEnum.saleOrder.getCode().equals(opsRequestpurchaseDO.getOrdType())
				|| (OrderTypeEnum.xxbkOrder.getCode().equals(opsRequestpurchaseDO.getOrdType())
						&& opsRequestpurchaseDO.getOrderNo().startsWith("88"))) {
			email.append(departmentVO.getEmailAddr()).append(";");
		}
		// 先行在库补库单88*
		if (OrderTypeEnum.xxbkOrder.getCode().equals(opsRequestpurchaseDO.getOrdType())
				&& opsRequestpurchaseDO.getOrderNo().startsWith("88")) {
			email.append(departmentVO.getEmailUserStock()).append(";");
			ccEmail.append("linlijia@smc.com.cn;wengweijie@smc.com.cn;jichunfei@smc.com.cn;sub-inventory@smc.com.cn;");
		}
		// 分库补库单88*
		if (StringUtils.isNotBlank(opsRequestpurchaseDO.getRequestWarehouseId())) {
			Boolean aBoolean = commonServiceFeignApi.judgeIsSubWareHouse(opsRequestpurchaseDO.getRequestWarehouseId());
			if (aBoolean) {
				if (OrderTypeEnum.xxbkOrder.getCode().equals(opsRequestpurchaseDO.getOrdType())
						&& opsRequestpurchaseDO.getOrderNo().startsWith("88")) {
					email.append(departmentVO.getEmailSubStock()).append(";");
					ccEmail.append("sub-inventory@smc.com.cn");
				}
			}
		}
		// 委托在库补库单 V*
		if (OrderTypeEnum.wtzkOrder.getCode().equals(opsRequestpurchaseDO.getOrdType())
				&& opsRequestpurchaseDO.getOrderNo().startsWith("V")) {
			email.append(departmentVO.getEmailCSStock()).append(";");
			ccEmail.append("consignmentsh@smc.com.cn;isod13@smc.com.cn;");
		}
		// Bin补库订单
		if (OrderTypeEnum.binbkOrder.getCode().equals(opsRequestpurchaseDO.getOrdType())
				&& opsRequestpurchaseDO.getOrderNo().startsWith("99")) {
			ccEmail.append("xuxiaoying@smc.com.cn;");
		}
		// 客户订单
		if (OrderTypeEnum.saleOrder.getCode().equals(opsRequestpurchaseDO.getOrdType())) {
			email.append(departmentVO.getEmailOrder()).append(";");
		}

		StringBuilder content = new StringBuilder();
		content.append("<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >")
				.append("<thead style=\"font-weight: bold;color: #000000;background-color: #B8CCE4;\" >")
				.append("<tr>" + "<td width=\"15%\" >订单号</td>" + "<td width=\"20%\" >客户名称</td>"
						+ "<td width=\"15%\" >型号</td>" + "<td width=\"5%\" >数量</td>" + "<td width=\"40%\" >拦截原因</td>"
						+ "</tr>")
				.append("</thead><tbody>");
		content.append("<tr>").append("<td>").append(orderStateDO.getOrderNo()).append("</td>").append("<td>")
				.append(orderStateDO.getCustomerNo() + "["
						+ commonService.getCustomerNameByNo(orderStateDO.getCustomerNo()) + "]")
				.append("</td>").append("<td>").append(orderStateDO.getModelNo()).append("</td>").append("<td>")
				.append(orderStateDO.getQuantity() == null ? "" : orderStateDO.getQuantity()).append("</td>")
				.append("<td>").append(orderStateDO.getStateDes()).append("</td>").append("</tr>");
		content.append("</tbody></table></br>");
		String strContent = "SHIKOMI拦截订单通知邮件,本邮件由系统发出,请勿直接回复本邮件.";

		ResultVo<DataTypeVO> cnc = dictDataServiceFeignApi.getDataTypeCodesInfo("9004", "SHIKOMI");
		if (!cnc.isSuccess()) {
			email.append("deliverysh@smc.com.cn");
			log.info("SHIKOMI拦截邮箱发送: {} : 抄送人: {}", email.toString(), ccEmail.toString());
			EmailUtil.send(javaMailSender, email.toString(), ccEmail.toString(), "webservice@smcgz.com.cn", strContent,
					content.toString());
		} else {
			email.append(cnc.getData().getExtNote1());
			log.info("SHIKOMI拦截邮箱发送: {} : 抄送人: {}", email.toString(), ccEmail.toString());
			EmailUtil.send(javaMailSender, email.toString(), ccEmail.toString(), "webservice@smcgz.com.cn", strContent,
					content.toString());
		}

	}

	/**
	 * 处理先行在库采购订单状态
	 *
	 * @param orderStateInfo
	 *            订单状态信息
	 */
	private void preStockPurchaseOrderStateHandle(OrderStateDO orderStateInfo) {
		log.info("先行在库订单状态变更处理 {} ", JSONUtil.toJsonStr(orderStateInfo));
		// 删除/取消p
		if (orderStateInfo.getStateCode() == OrderStateEnum.CanceledNotConfirm.code()) {
			log.info(" 进入 先行在库采购单取消 => {}", JSONUtil.toJsonStr(orderStateInfo));
			ResultVo<String> result = null;
			try {
				result = preStockFeignApi.purchaseOrderCancelHandle(orderStateInfo.getOrderNo());
			} catch (Exception e) {
				log.error("先行在库订单状态变更处理异常 {},{}", orderStateInfo.getOrderNo(), e.getMessage(), e);
			}
			log.info("先行在库采购单取消: {} {}", orderStateInfo.getOrderNo(), result);
		}
	}

	/*
	 * 更新采购表状态信息 20230203 A78027 增加传递工厂出荷日
	 */
	public void toUpdatePOOrderState(OrderStateDO orderStateInfo) {
		// != 供应商已接单 (22).供应商接单异常(21).生产中(25) 完工（30） 这4个状态在另外的接口中调用
		// add by A78027 From bugId 10270
		// 20230412 增加发票入库状态
		if (orderStateInfo.getStateCode() != OrderStateEnum.SupplierRcvOrder.code()
				&& orderStateInfo.getStateCode() != OrderStateEnum.SupplierReplyProblemOrder.code()
				&& orderStateInfo.getStateCode() != OrderStateEnum.SupplierInProd.code()
				&& orderStateInfo.getStateCode() != OrderStateEnum.SupplierFinishProd.code()
				&& orderStateInfo.getStateCode() != OrderStateEnum.SupplierShipped.code()
				&& orderStateInfo.getStateCode() != OrderStateEnum.InvoiceImpStock.code()) {
			return;
		}

		if (orderStateInfo.getRorderNo() == null || orderStateInfo.getItemNo() == null) {
			log.error("order state 订单号为null:" + orderStateInfo.toString());
			return;
		}

		List<PurchaseReplyInfo> list = new ArrayList<>(1);
		PurchaseReplyInfo info = new PurchaseReplyInfo();

		// bug 10280 改成按3个订单号
		info.setOrderno(orderStateInfo.getRorderNo());
		info.setItemno(orderStateInfo.getItemNo());
		info.setSplitno(orderStateInfo.getSplitNo());

		// if (orderStateInfo.getOrderNo().contains("-")) {
		// info.setPono(orderStateInfo.getOrderNo());
		// info.setLineitem(1); // 项号
		// } else if (orderStateInfo.getOrderNo().length() == 10) {
		// if (StringUtils.isNumeric(orderStateInfo.getOrderNo().substring(7,
		// 10))) {
		// info.setPono(orderStateInfo.getOrderNo().substring(0, 7));
		// info.setLineitem(Integer.parseInt(orderStateInfo.getOrderNo().substring(7,
		// 10)));
		// } else {
		// return;
		// }
		// }
		// OrderNoInfo orderNoInfo = new
		// OrderNoInfo().convertPOOrder(orderStateVO.getOrderNo(),"1");
		// info.setPono(orderNoInfo.getPoNo());
		// info.setLineitem(1);
		info.setReplyorderno(orderStateInfo.getSupplierOrderNo());
		info.setSupplierid(orderStateInfo.getSupplierCode());
		// info.setModelno(orderStateInfo.getModelNo());
		info.setReplyexportdate(orderStateInfo.getPoReplyDate());
		info.setReplyorderdate(orderStateInfo.getSupplierRcvTime());
		info.setProduceholon(orderStateInfo.getPoHolon());
		info.setSupplierid(orderStateInfo.getSupplierCode());
		// update by lyc from bug 12124 in 2023-09-14
		// info.setTranstype(orderStateInfo.getTransType());
		info.setTranstype(orderStateInfo.getPoTransType());
		// update by A78027 from bug 9415 in 2023-02-03
		info.setFacexpdate(orderStateInfo.getPoFacExpdate());// 工厂出荷日

		// add by lyc 20240606 14404bug 传输报关日期、到港日期
		info.setCustomsdate(orderStateInfo.getCustomsDate());
		info.setPortarrivedate(orderStateInfo.getPortArriveDate());

		if (orderStateInfo.getStateCode() == OrderStateEnum.SupplierRcvOrder.code()) { // 供应商已接单
			info.setReasonremark(orderStateInfo.getStateDes());
		} else if (orderStateInfo.getStateCode() == OrderStateEnum.SupplierReplyProblemOrder.code()) {
			// 21供应商接单异常
			info.setErrdescription(orderStateInfo.getStateDes());
		} else if (orderStateInfo.getStateCode() == OrderStateEnum.SupplierInProd.code()
				|| orderStateInfo.getStateCode() == OrderStateEnum.SupplierShipped.code()
				|| orderStateInfo.getStateCode() == OrderStateEnum.SupplierFinishProd.code()) {
			// 25生产中
			info.setReasonremark(orderStateInfo.getStateDes());
		}
        
		list.add(info);

		// CommonResult<String> updateInvoice =
		// purchaseFeignApi.updatePurchaseInfo(list);
		log.info("更新采购表状态信息:{}", JSONObject.toJSONString(list));
		CommonResult<String> updateInvoice = wmPurchaseFeignApi.updateInvoice(list);
		if (!updateInvoice.isSuccess()) {
			log.error("北京接口[updateInvoice] 响应失败 异常信息: {}, 参数: {} ", updateInvoice.getMessage(),
					JSONObject.toJSONString(list));
		}
	}

	/**
	 * 回复中国制造订单状态
	 *
	 * @param orderStateInfo
	 */
	public void replyCNMOrderState(OrderStateDO orderStateInfo) {

		if (StringUtils.isNotBlank(orderStateInfo.getCustomerNo())
				&& !GroupCustomerEnum.exitGroupCustomerEnum(orderStateInfo.getCustomerNo())) {
			return;
		}
		if (StringUtils.isNotBlank(orderStateInfo.getProvider()) && orderStateInfo.getProvider().equals("sys_po")) {
			orderStateInfo.setStateDes(orderStateInfo.getStateDes() + ";来自合并订单" + orderStateInfo.getRemark());
		}
		try {

			ResultVo<DataTypeVO> dataTypeCodesInfoWithDS = dictCommonService.getDataTypeCodesInfoWithDS("9002", "30");
			if (dataTypeCodesInfoWithDS.isSuccess() && dataTypeCodesInfoWithDS.getData() != null) {
				String extNote1 = dataTypeCodesInfoWithDS.getData().getExtNote1();
				if(extNote1.equals("2") || extNote1.equals("3")) {
					if (orderStateInfo.getStateCode() == OrderStateEnum.CanceledNotConfirm.code()) {
						log.info("删单反馈制造参数: {}", JSONObject.toJSONString(orderStateInfo));
						LambdaUpdateWrapper<OpsVManuorderToSales> updateWrapper = new LambdaUpdateWrapper<>();
						updateWrapper.eq(OpsVManuorderToSales::getSalesOrderNo, orderStateInfo.getOrderNo())
								.set(OpsVManuorderToSales::getSalesCancelStatus, 1).set(OpsVManuorderToSales::getSalesStatus, 3)
								.set(OpsVManuorderToSales::getSalesCancelTime, new Date())
								.set(OpsVManuorderToSales::getSalesUpdateTime, new Date())
								.set(OpsVManuorderToSales::getSalesCancelReason, orderStateInfo.getStateDes());
						opsVManuorderToSalesMapper.update(null, updateWrapper);
					} else {
						// 如果制造删单 重新处理后 恢复制造状态
						LambdaQueryWrapper<OpsVManuorderToSales> queryWrapper = new LambdaQueryWrapper<>();
						queryWrapper.eq(OpsVManuorderToSales::getSalesOrderNo, orderStateInfo.getOrderNo())
								.eq(OpsVManuorderToSales::getSalesCancelStatus, 1);
						List<OpsVManuorderToSales> opsVManuorderToSales = opsVManuorderToSalesMapper.selectList(queryWrapper);
						if (CollectionUtils.isNotEmpty(opsVManuorderToSales)) {
							LambdaUpdateWrapper<OpsVManuorderToSales> updateWrapper = new LambdaUpdateWrapper<>();
							updateWrapper.eq(OpsVManuorderToSales::getSalesOrderNo, orderStateInfo.getOrderNo())
									.set(OpsVManuorderToSales::getSalesCancelStatus, 0)
									.set(OpsVManuorderToSales::getSalesStatus, 2)
									.set(OpsVManuorderToSales::getSalesCancelTime, null)
									.set(OpsVManuorderToSales::getSalesUpdateTime, new Date())
									.set(OpsVManuorderToSales::getSalesCancelReason, null);
							opsVManuorderToSalesMapper.update(null, updateWrapper);
						}

					}

					// 回改制造订单的订货区分.
					if (orderStateInfo.getStateCode() != OrderStateEnum.CanceledNotConfirm.code()) {
						LambdaUpdateWrapper<OpsVManuorderToSales> updateWrapper = new LambdaUpdateWrapper<>();
						updateWrapper.eq(OpsVManuorderToSales::getSalesOrderNo, orderStateInfo.getOrderNo())
								.set(OpsVManuorderToSales::getSalesRemark, orderStateInfo.getStateDes())
								.set(StringUtils.isNotBlank(orderStateInfo.getSupplierOrderNo()),
										OpsVManuorderToSales::getSalesordernoJp, orderStateInfo.getSupplierOrderNo())
								.set(orderStateInfo.getEsArrivalDate() != null, OpsVManuorderToSales::getExpectedArrivalDate,
										DateUtil.getDateHourMinute(orderStateInfo.getEsArrivalDate()))
								.set(StringUtils.isNotBlank(orderStateInfo.getPoInvoiceNo()),
										OpsVManuorderToSales::getExpectedArrivalinvNo, orderStateInfo.getPoInvoiceNo())
								.set(OpsVManuorderToSales::getSalesUpdateTime, new Date());
						// 订货返信
						if (orderStateInfo.getStateCode() == OrderStateEnum.Purchareing.getCode()) {
							updateWrapper.set(orderStateInfo.getPoReplyDate() != null,
									OpsVManuorderToSales::getSalesDeliveryTime, orderStateInfo.getPoReplyDate());
						} else if (orderStateInfo.getStateCode() == OrderStateEnum.GoodsReady.getCode()) { // 制定工厂出荷日
							updateWrapper.set(orderStateInfo.getPoDlvDate() != null, OpsVManuorderToSales::getSalesDeliveryTime,
									orderStateInfo.getPoDlvDate());
						}
						// 订货区分
						if (StringUtils.isNotBlank(orderStateInfo.getSupplierNo())) {
							updateWrapper.set(OpsVManuorderToSales::getExpInvCode, orderStateInfo.getSupplierNo());
						} else if (StringUtils.isNotBlank(orderStateInfo.getSupplierCode())) {
							updateWrapper.set(OpsVManuorderToSales::getExpInvCode, orderStateInfo.getSupplierCode());
						}

						// 运输方式 1 空运 0 海运
						if (StringUtils.isNotBlank(orderStateInfo.getTransType())) {
							if ("1".equals(orderStateInfo.getTransType())) {
								updateWrapper.set(OpsVManuorderToSales::getTransType, orderStateInfo.getTransType());
							} else {
								updateWrapper.set(OpsVManuorderToSales::getTransType, "0");
							}
						}
						opsVManuorderToSalesMapper.update(null, updateWrapper);
					}

				}
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
			log.error("更新制造视图异常: " + JSON.toJSONString(orderStateInfo));
			String json = JSON.toJSONString(orderStateInfo);
			redisManager.hPut("ops:cnmo:errstate", orderStateInfo.getOrderNo(), json);
		}
	}

	@Override
	public void sendOrderStateToGZProd(OrderStateDO info) {

        ResultVo<DataTypeVO> dataTypeCodesInfo1 = dictCommonService.getDataTypeCodesInfo("6012", "3");
        if (dataTypeCodesInfo1.isSuccess() && dataTypeCodesInfo1.getData() != null) {
            DataTypeVO data = dataTypeCodesInfo1.getData();
            if ("0".equals(data.getExtNote1())) {
                return;
            }
        }

        log.info("OPS状态更新给广州制造: {}", JSONObject.toJSONString(info));
		OrderSupplierReplyState state = BeanCopyUtil.copy(info, OrderSupplierReplyState.class);
		if (StringUtils.isBlank(state.getCorderNo())) {
			return;
		}
		try {
			String url = "http://10.116.192.19:8100/omc/orderState/supplierReplyOrderState";
			HttpResponse response = HttpUtil.createPost(url)
					.header("token",
							"eyJuYW1lIjoiYXBwLXNhbGVzd2ViIiwiZXhwIjoxNTk0MzQ3MzA5fQ.iDP7_GB4rKMqVLxyLDm0iK4NOGHUCOaNM6StKKvbPkk")
					.header("Content-Type", "application/json;charset=UTF-8").charset(StandardCharsets.UTF_8)
					.body(JSONUtil.toJsonStr(state)).executeAsync();
			// .execute();
			log.info("OPS状态更新给广州制造返回: {}", JSONObject.toJSONString(response));
		} catch (Exception e) {
			log.error("状态更新给广州制造失败: {}", e.getMessage(), e.getMessage());
		}
	}

	public void updateOrderStateDetail(OrderStateVO orderStateVO, OrderStateVO curOrderStateVO) {
		Class cls = orderStateVO.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			if (field.getType() == Date.class) {
				try {
					field.setAccessible(true);
					Object value = field.get(orderStateVO);
					Object curValue = field.get(curOrderStateVO);
					if (value != null && curValue != null && !value.equals(curValue)) {
						String fileName = field.getName();
						OrderSateDateType dateType = OrderSateDateType.getByName(fileName);
						if (dateType != null) {
							updateOrderStateDetail(dateType, orderStateVO, (Date) value, (Date) curValue);
						}
					}
				} catch (Exception ex) {
					log.error("[更新状态明细]异常: 异常数据 {}, 异常信息: {} ", JSONObject.toJSONString(orderStateVO), ex.getMessage(),
							ex);
				}
			}
		}
	}

	public void updateOrderStateDetail(OrderSateDateType dateType, OrderStateVO orderStateVO, Date updDate,
			Date curDate) {
		LambdaQueryWrapper<OrderStateDetailDO> query = new LambdaQueryWrapper<>();
		query.select(OrderStateDetailDO::getId, OrderStateDetailDO::getOrderNo, OrderStateDetailDO::getDateType,
				OrderStateDetailDO::getStateDate, OrderStateDetailDO::getChangeTimes);
		query.eq(OrderStateDetailDO::getDateType, dateType.code())
				.eq(OrderStateDetailDO::getOrderNo, orderStateVO.getOrderNo())
				.orderByDesc(OrderStateDetailDO::getUpdateTime);
		List<OrderStateDetailDO> orderStateDetailDOS = orderStateDetailMapper.selectList(query);
		OrderStateDetailDO orderStateDetailDO;
		if (CollectionUtils.isEmpty(orderStateDetailDOS)) {
			orderStateDetailDO = new OrderStateDetailDO();
			orderStateDetailDO.setDateType(dateType.code());
			orderStateDetailDO.setDateName(dateType.dateName());
			orderStateDetailDO.setOrderNo(orderStateVO.getOrderNo());
			orderStateDetailDO.setFirstDate(updDate);
			orderStateDetailDO.setMaxDate(updDate);
			orderStateDetailDO.setMinDate(updDate);
			orderStateDetailDO.setStateDate(updDate);
			orderStateDetailDO.setStateDes(orderStateVO.getStateDes());
			orderStateDetailDO.setUpdateTime(new Date());
			orderStateDetailDO.setCreateTime(new Date());
			orderStateDetailDO.setProvider(orderStateVO.getProvider());
			orderStateDetailDO.setChangeTimes(0);
			orderStateDetailDO.setDelayDay(0);
			orderStateDetailMapper.insert(orderStateDetailDO);
		} else {
			orderStateDetailDO = orderStateDetailDOS.get(0);
			orderStateDetailDO.setProvider(orderStateVO.getProvider());
			orderStateDetailDO.setDateName(dateType.dateName());
			// 没有变化
			if (0 == updDate.compareTo(curDate)) {
				return;
			}
			if (updDate.after(curDate)) {
				orderStateDetailDO.setMaxDate(updDate);
				if (orderStateDetailDO.getStateDate() != null && updDate != null) {
					orderStateDetailDO
							.setDelayDay(DateUtil.getDiffDay(orderStateDetailDO.getStateDate(), updDate).intValue());
				}
				if (orderStateDetailDO.getChangeTimes() != null) {
					orderStateDetailDO.setChangeTimes(orderStateDetailDO.getChangeTimes() + 1);
				}
			} else {
				orderStateDetailDO.setMinDate(updDate);
				orderStateDetailDO.setDelayDay(0);
			}
			orderStateDetailDO.setUpdateTime(new Date());
			orderStateDetailDO.setProvider(orderStateVO.getProvider());
			orderStateDetailMapper.updateById(orderStateDetailDO);
		}
	}

	@Override
	public ResultVo<String> importJPDeliveryData(List<JPDeliveryDataVO> jpDeliveryDataVOList) {
		if (jpDeliveryDataVOList.isEmpty()) {
			return ResultVo.failure("导入数据失败!");
		}

		Date now = DateUtil.getNow();
		for (JPDeliveryDataVO jpDeliveryDataVO : jpDeliveryDataVOList) {

			OrderStateVO orderStateVO = jpDeliveryDataToOrderState(jpDeliveryDataVO);
			orderStateVO.setUpdateTime(now);
			ResultVo<String> stringResultVo = addOrderState(orderStateVO);
			if (!stringResultVo.isSuccess()) {
				log.error("导入日本delivery.dat失败 " + stringResultVo.getMessage());
				// return ResultVo.failure("导入数据失败!");
			}
		}
		return ResultVo.success("", "成功导入数据!,记录数：" + jpDeliveryDataVOList.size());
	}

	@Override
	public ResultVo<String> importJPReceiveOrderFile(MultipartFile file) {
		if (file == null) {
			return ResultVo.failure("空文件.");
		}

		String line;
		List<OrderStateVO> list = new ArrayList<>();
		List<OrderStateVO> tempList;
		Date stateDate = new Date();
		Date receiveOrderDate; // 接单处理时间
		String rcvOrderDate;

		try (InputStreamReader isr = new InputStreamReader(file.getInputStream(), StandardCharsets.US_ASCII);
				BufferedReader bf = new BufferedReader(isr)) {

			tempList = new ArrayList<>();
			OrderStateVO orderStateVO;
			while ((line = bf.readLine()) != null) {
				line = line.trim();
				if (StringUtils.isBlank(line)) {
					continue;
				}
				// 获取接单时间
				if (line.indexOf("FROM JAPAN") > 0) {
					String substring = line.substring(7, 17).trim() + " " + line.substring(19, 27).trim();
					receiveOrderDate = null;
					rcvOrderDate = "";
					if (StringUtils.isNotEmpty(substring)) {
                        try {
							if (StringUtils.isNotBlank(line.substring(19, 27).trim()) && line.substring(19, 27).trim().length() > 5 ) {
								receiveOrderDate = DateUtil.getFormatDate(substring, "yyyy/MM/dd HH:mm:ss");
							} else {
								substring = line.substring(7, 17).trim() + " 00:00:00";
								receiveOrderDate = DateUtil.getFormatDate(substring, "yyyy/MM/dd HH:mm:ss");
							}
                        } catch (Exception e) {
                           continue;
                        }
                        if (receiveOrderDate != null ) {
                            rcvOrderDate = DateUtil.getYearMonthDay(receiveOrderDate);
                        }
					}
					for (OrderStateVO temp : tempList) {
						temp.setSupplierRcvTime(receiveOrderDate);
						temp.setStateDate(stateDate);
						temp.setStateDes(String.format("%s日本已接单,指定出荷日%s.%s", rcvOrderDate,
								DateUtil.getYearMonthDay(temp.getPoDlvDate()),
								Optional.ofNullable(temp.getShikomiNo()).orElse("")));
					}
					list.addAll(tempList);
					continue;
				}
				// 解析文件数据行
				orderStateVO = convertJPReceiveOrderTXT(line);
				if (orderStateVO == null) {
					continue;
				}
				tempList.add(orderStateVO);
			}
		} catch (IOException e) {
			log.error("{}文件解析失败: {}", file.getName(), e);
			return ResultVo.failure(e.toString());
		}
		// 数据导入处理
		if (list.isEmpty()) {
			return ResultVo.failure("JP rcv order 无数据");
		}

		ResultVo<String> stringResultVo;
		for (OrderStateVO orderStateVO : list) {
			stringResultVo = addOrderState(orderStateVO);
			if (!stringResultVo.isSuccess()) {
				log.error("推送" + orderStateVO.getOrderNo() + "时,发送错误." + stringResultVo.getMessage());
			}
		}
		return ResultVo.success("", "导入订单数：" + list.size());
	}

	/**
	 * 解析数据行
	 *
	 * @param line
	 *            数据行
	 */
	public OrderStateVO convertJPReceiveOrderTXT(String line) {

		OrderStateVO orderStateVO = new OrderStateVO();

		try {
			String orderNo = line.substring(6, 26).trim();
			orderStateVO.setModelNo(line.substring(39, 69).trim());
			String strDlvDate = "20" + line.substring(26, 34).replace("/", "-");
			Date dlvDate = DateUtil.stringToDate(strDlvDate);
			orderStateVO.setPoDlvDate(dlvDate);
			String itemNo = line.substring(34, 38).trim();
			String strqty = line.substring(71, 76).trim();
			String strPrice = line.substring(76, 88).trim();

			String shikomiNo = null;
			if (line.length() > 95) {
				shikomiNo = line.substring(95).trim();
			}

			// OrderNoInfo orderNoInfo = new
			// OrderNoInfo().convertJPOrder(orderNo, itemNo);
			// bug 13606 优化采购pono解析成orderno
			OrderNoInfo orderNoInfo = purchaseConvertService.convertPoNoFormPurchase(orderNo, itemNo);
			orderStateVO.setOrderNo(orderNoInfo.getFullOrderNo());
			orderStateVO.setRorderNo(orderNoInfo.getOrderNo());
			orderStateVO.setItemNo(orderNoInfo.getItemNo());
			orderStateVO.setSplitNo(orderNoInfo.getSplitItem());

			if (StringUtils.isNotBlank(strqty)) {
				orderStateVO.setQuantity(Integer.parseInt(strqty));
			}
			// orderStateVO.setPoReplyDate(poReplyDate);
			orderStateVO.setShikomiNo(shikomiNo);
			orderStateVO.setPurchaseType(orderNoInfo.getPurchaseType());
			if (orderNoInfo.getOrderType() != 99) {
				orderStateVO.setOrderType(orderNoInfo.getOrderType());
			}
			orderStateVO.setStateCode(OrderStateEnum.SupplierRcvOrder.code()); // 供应商已接单
			orderStateVO.setStateType(2); // 接单处理
			orderStateVO.setSupplierCode("JP");
			orderStateVO.setProvider("JP");
			if (PublicUtil.isNotEmpty(strPrice)) {
				orderStateVO.setPoPrice(new BigDecimal(strPrice));
			}
			// log.info(orderStateVO.toString());
		} catch (Exception e) {
			log.error("convertJPReceiveOrderTXT:" + line);
			log.error(e.toString());
			return null;
		}
		return orderStateVO;
	}

	/**
	 * 解析日本交货期 0-空白日期 1-正确日期 2-特殊字符 9-异常状态 除了55 66 77的都算异常订单
	 *
	 * @param info
	 */
	private void resolveJPDlvDate(JPDeliveryDataVO info) {
		if (PublicUtil.isEmpty(info.getJpDlvDate())) {
			info.setJpDlvDateType(0);
			return;
		}

		if (!info.getJpDlvDate().startsWith("99") && info.getJpDlvDate().endsWith("99")) {
			String yearStr = info.getJpDlvDate().substring(0, 2);
			String monthStr = info.getJpDlvDate().substring(3, 5);
			int daysInMonth = PublicUtil.getDaysInMonth(Integer.parseInt(yearStr), Integer.parseInt(monthStr));
			String conventDateStr = "99" + yearStr + "-" + monthStr + "-" + daysInMonth;
			info.setJpDlvDateType(1);
			info.setJpDlvDateValue(DateUtil.stringToDate(conventDateStr));
			return;
		}

		switch (info.getJpDlvDate()) {
		case "11/11/11":
			info.setJpDlvDateDes("要图纸11/11/11");
			info.setJpDlvDateType(9);
			info.setJpDlvDateValue(DateUtil.stringToDate("1111-01-01"));
			return;
		case "22/22/22":
			info.setJpDlvDateDes("型号确认中订单正转交正确部门22/22/22");
			info.setJpDlvDateType(9);
			info.setJpDlvDateValue(DateUtil.stringToDate("2222-02-02"));
			return;
		case "33/33/33":
			info.setJpDlvDateDes("正在日本研发中心组装33/33/33");
			info.setJpDlvDateType(9);
			info.setJpDlvDateValue(DateUtil.stringToDate("3333-03-03"));
			return;
		case "44/44/44":
			info.setJpDlvDateDes("紧急生产停止44/44/44");
			info.setJpDlvDateType(9);
			info.setJpDlvDateValue(DateUtil.stringToDate("4444-04-04"));
			return;
		case "55/55/55":
			info.setJpDlvDateDes("部品订向其他工厂，无确切交货期55/55/55");
			info.setJpDlvDateType(2);
			info.setJpDlvDateValue(DateUtil.stringToDate("5555-05-05"));
			return;
		case "66/66/66":
			info.setJpDlvDateDes("部品订向外协，无确切交货期66/66/66");
			info.setJpDlvDateType(2);
			info.setJpDlvDateValue(DateUtil.stringToDate("6666-06-06"));
			return;
		case "77/77/77":
			info.setJpDlvDateDes("加工完毕日期不确认，正在确认中77/77/77");
			info.setJpDlvDateType(2);
			info.setJpDlvDateValue(DateUtil.stringToDate("7777-07-07"));
			return;
		case "88/88/88":
			info.setJpDlvDateDes("日本制造部向研发询问图纸中88/88/88");
			info.setJpDlvDateType(9);
			info.setJpDlvDateValue(DateUtil.stringToDate("8888-08-08"));
			return;
		case "99/99/99":
			info.setJpDlvDateDes("订单异常，请登录AS400查看异常原因并及时进行处理");
			info.setJpDlvDateType(9);
			info.setJpDlvDateValue(DateUtil.stringToDate("9999-09-09"));
			return;
		case "99/00/00":
			info.setJpDlvDateDes("要提供试样书99/00/00");
			info.setJpDlvDateType(9);
			info.setJpDlvDateValue(DateUtil.stringToDate("9900-09-09"));
			return;
		default:
			info.setJpDlvDateType(1);
			String strDate = "20" + info.getJpDlvDate();
			if (!PublicUtil.isValidDate(strDate.replace('/', '-'))) {
				info.setJpDlvDateValue(DateUtil.stringToDate("9999-12-31"));
			} else {
				info.setJpDlvDateValue(DateUtil.stringToDate(strDate.replace('/', '-')));
			}
			return;
		}
	}

	/**
	 * 1 异常 0 采购中 除去55 66 77 其他都算异常订单
	 *
	 * @param date
	 * @return
	 */
	// public static int isErrorOrder(String date) {
	// if (PublicUtil.isEmpty(date)) {
	// return 1;
	// }
	// switch (date) {
	// case "11/11/11":
	// return 1;
	// case "22/22/22":
	// return 1;
	// case "33/33/33":
	// return 1;
	// case "44/44/44":
	// return 1;
	// case "55/55/55":
	// return 0;
	// case "66/66/66":
	// return 0;
	// case "77/77/77":
	// return 0;
	// case "88/88/88":
	// return 1;
	// case "99/99/99":
	// return 1;
	// case "99/00/00":
	// return 1;
	// default:
	// return 1;
	// }
	// }

	private static String getPoReplayDate(String date) {
		if (PublicUtil.isEmpty(date)) {
			return "";
		}
		switch (date) {
		case "11/11/11":
			return "1111-1-1";
		case "22/22/22":
			return "2222-2-2";
		case "33/33/33":
			return "3333-3-3";
		case "44/44/44":
			return "4444-4-4";
		case "55/55/55":
			return "5555-5-5";
		case "66/66/66":
			return "6666-6-6";
		case "77/77/77":
			return "7777-7-7";
		case "88/88/88":
			return "8888-8-8";
		case "99/99/99":
			return "9999-9-9";
		case "99/00/00":
			return "9900-9-9";
		default:
			return "";
		}
	}

	public boolean isSpecJPRepleDlvDate(Date date) {
		if (date == null) {
			return true;
		}
		String dateString = DateUtil.dateToDateString(date);
		switch (dateString) {
		case "1111-01-01":
			return true;
		case "2222-02-02":
			return true;
		case "3333-03-03":
			return true;
		case "4444-04-04":
			return true;
		case "5555-05-05":
			return true;
		case "6666-06-06":
			return true;
		case "7777-07-07":
			return true;
		case "8888-08-08":
			return true;
		case "9999-09-09":
			return true;
		case "9900-09-09":
			return true;
		default:
			return false;
		}
	}

	public static int getOrderType(String jpPOType) {
		int orderType = 0;
		switch (jpPOType) {
		case "A":
			orderType = 1;
			break;
		case "B":
			orderType = 20;
			break;
		case "K":
			orderType = 20;
			break;
		case "G":
			orderType = 20;
			break;
		default:
			orderType = 21;
		}
		return orderType;
	}

	// 判断 '-' 分隔符出现的次数
	/*
	 * public int showCountByStr(String str) { int oldLength = str.length();
	 * String newStr = str.replaceAll("-", ""); int newLength = newStr.length();
	 * return oldLength - newLength; }
	 */

	public static Boolean judgmentConditions(String str) { // 是否包含 %
		if (PublicUtil.isEmpty(str)) {
			return false;
		}
		return str.contains("%");
	}

	public static Boolean judgmentConditions2(String str) {
		if (judgmentConditions(str)) {
			return false;
		}
		return !PublicUtil.isEmpty(str);
	}

	public static String strSplit(String str) {
		if (PublicUtil.isEmpty(str)) {
			return "";
		}
		if (str.contains("%")) {
			return str.substring(0, str.indexOf("%"));
		} else {
			return str;
		}

	}

	/**
	 * 计算预计到达日期
	 *
	 * @param supplierCode
	 * @param transType
	 * @param dlvDate
	 * @return
	 */
	@Override
	public Date calcEsArrivalDate(String supplierCode, String transType, Date dlvDate) {
		if (PublicUtil.isEmpty(supplierCode) || PublicUtil.isEmpty(dlvDate)) {
			return null;
		}
		if (PublicUtil.isEmpty(transType)) {
			transType = "0";
		}
		int dayNum = 0;
		// 海(0) 18天 快船(4) 12 空(1) 7
		switch (supplierCode) {
		case "JP": // 日本
			switch (transType) {
			case "1": // 空运
				// 从redis中获取所需运输天数
				Object o = redisManager.get("ops:EsArrivalDateByTranType:" + "JP" + 1);
				if (o == null) {
					ResultVo<DataTypeVO> jp = dictDataServiceFeignApi.getDataTypeCodesInfo("2002", "JP");
					if (jp.isSuccess() && jp.getData() != null) {
						DataTypeVO data = jp.getData();
						redisManager.set("ops:EsArrivalDateByTranType:" + "JP" + 1, data.getExtNote1(), 3600 * 24 * 7);
						dayNum = Integer.parseInt(data.getExtNote1());
					}
				} else {
					dayNum = Integer.parseInt(o.toString());
				}
				return DateUtil.addDay(dlvDate, dayNum);
			case "4": // 快船
				// 从redis中获取所需运输天数
				Object obj = redisManager.get("ops:EsArrivalDateByTranType:" + "JP" + 4);
				if (obj == null) {
					ResultVo<DataTypeVO> jp = dictDataServiceFeignApi.getDataTypeCodesInfo("2002", "JP");
					if (jp.isSuccess() && jp.getData() != null) {
						DataTypeVO data = jp.getData();
						redisManager.set("ops:EsArrivalDateByTranType:" + "JP" + 4, data.getExtNote2(), 3600 * 24 * 7);
						dayNum = Integer.parseInt(data.getExtNote2());
					}
				} else {
					dayNum = Integer.parseInt(obj.toString());
				}
				return DateUtil.addDay(dlvDate, dayNum);
			case "0": // 海运
				// 从redis中获取所需运输天数
				Object obj2 = redisManager.get("ops:EsArrivalDateByTranType:" + "JP" + 0);
				if (obj2 == null) {
					ResultVo<DataTypeVO> jp = dictDataServiceFeignApi.getDataTypeCodesInfo("2002", "JP");
					if (jp.isSuccess() && jp.getData() != null) {
						DataTypeVO data = jp.getData();
						redisManager.set("ops:EsArrivalDateByTranType:" + "JP" + 0, data.getExtNote3(), 3600 * 24 * 7);
						dayNum = Integer.parseInt(data.getExtNote3());
					}
				} else {
					dayNum = Integer.parseInt(obj2.toString());
				}
				return DateUtil.addDay(dlvDate, dayNum);
			default:
				return DateUtil.addDay(dlvDate, 18);
			}
		case "HK": // 香港
			return DateUtil.addDay(dlvDate, 4);
		case "CM": // 北京
			return dlvDate;
		case "GZ": // 广州
			return dlvDate;
		case "CT": // 广州
			return dlvDate;
		default:
			return dlvDate;
		}
	}

	@Override
	public String convertTransType(String threeCode) {
		if (StringUtils.isEmpty(threeCode)) {
			return null;
		}
		String transType = null;
		switch (threeCode) {
		case "SEA":
			transType = "0";
			break;
		case "AIR":
			transType = "1";
			break;
		case "LAND":
			transType = "3";
			break;
		case "COURIER":
			transType = "1";
			break;
		// case "CLIPPER":
		// transType = "4";
		// break;
		case "RAIL":
			transType = "5";
			break;
		case "SEA&AIR":
			// bug15038 增加快船的选项
			transType = "4";
			break;
		default:
			break;
		}
		return transType;
	}

	public static String convertTransTypeFromOneChar(String oneChar) {
		if (StringUtils.isEmpty(oneChar)) {
			return "0";
		}
		String transType = null;
		switch (oneChar.substring(0, 1)) {
		case "S":
			transType = "0";
			break;
		case "A":
			transType = "1";
			break;
		case "L":
			transType = "3";
			break;
		case "C":
			transType = "4";
			break;
		case "R":
			transType = "5";
			break;
		default:
			break;
		}
		return transType;
	}

	private static String getTransTypeDesc(String transType) {
		if (StringUtils.isBlank(transType)) {
			return "";
		}
		String des;
		switch (transType) {
		case "0":
			des = "海运";
			break;
		case "1":
			des = "空运";
			break;
		case "3":
			des = "陆运";
			break;
		case "4":
			des = "快船";
			break;
		case "5":
			des = "铁路";
			break;
		default:
			des = "";
		}
		return des;
	}

	/**
	 * 同步中国制造订单返信
	 *
	 * @return
	 */
	@Override
	public ResultVo<String> importCNMOrderReply() {
		List<OpsVDeliveryAnswerToSalesDO> list = deliveryAnswerToSalesMapper.selectData();
		OrderStateVO orderStateVO;

		try {
			for (OpsVDeliveryAnswerToSalesDO salesDO : list) {
				orderStateVO = new OrderStateVO();

				OrderNoInfo orderNoInfo;
				if (salesDO.getOrderNo().contains("-")) {
					orderNoInfo = new OrderNoInfo().convertFullOrderNo(salesDO.getOrderNo());
				} else {
					orderNoInfo = new OrderNoInfo().convertFullOrderNo(salesDO.getOrderNo() + salesDO.getItemNo());
				}

				orderStateVO.setOrderNo(orderNoInfo.getFullOrderNo());
				orderStateVO.setItemNo(orderNoInfo.getItemNo());
				orderStateVO.setSplitNo(orderNoInfo.getSplitItem());

				orderStateVO.setRorderNo(orderNoInfo.getOrderNo());
				orderStateVO.setProvider("CNM_replay");
				// orderStateVO.setModelNo(salesDO.getModelNo());
				// orderStateVO.setQuantity(salesDO.getQuantity());
				// 营业纳期
				orderStateVO.setPoDlvDate(salesDO.getDlvDate());
				// 变更纳期
				orderStateVO.setPoReplyDate(salesDO.getChangeDlvDate());
				// orderStateVO.setPoShipDate(salesDO.getExportDate());
				orderStateVO.setPoHolon(salesDO.getHolon());
				// orderStateVO.setPoNo(salesDO.getDependencyCode());
				orderStateVO.setSupplierCode(convenSupplierCodeByOrderTypeName(salesDO.getOrderType()));
				orderStateVO.setPoHolon(salesDO.getHolon());

				String factoryName = getCNFactoryByCNOrderTypeName(salesDO.getOrderType());

				/**
				 * 有发票号的 制造量产 已发出Y32151 +2022-06-02（装箱时间） 有出库日期 已出库 2022-05-31
				 * 有装箱时间 已装箱2022-06-02 其他 加工中纳期2022-06-17
				 */
				StringBuilder ss = new StringBuilder();
				ss.append(factoryName);
				if (StringUtils.isNotBlank(salesDO.getOrderType())) {
					String str = salesDO.getOrderType().substring(0, salesDO.getOrderType().length() - 2);
					ss.append(str).append(",");
				}

				if (StringUtils.isNotEmpty(salesDO.getRemark())) {
					ss.append(salesDO.getRemark()).append(" ");
				}

				if (StringUtils.isNotBlank(salesDO.getInvoiceNo())) {
					if (salesDO.getPackDate() != null) {
						ss.append("已发出" + salesDO.getInvoiceNo() + " "
								+ DateUtil.dateToDateTimeString(salesDO.getPackDate()));
					} else {
						ss.append("已发出" + salesDO.getInvoiceNo());
					}
					// orderStateVO.setPoShipDate(salesDO.getPackDate());
					orderStateVO.setStateCode(OrderStateEnum.SupplierShipped.code());
					orderStateVO.setStateDes(ss.toString());
				} else if (salesDO.getExpDate() != null) {
					ss.append("已出库 " + DateUtil.dateToDateTimeString(salesDO.getExpDate()));
					orderStateVO.setStateCode(OrderStateEnum.SupplierShipped.code());
					orderStateVO.setStateDes(ss.toString());
				} else if (salesDO.getPackDate() != null) {
					ss.append("已装箱 " + DateUtil.dateToDateTimeString(salesDO.getPackDate()));
					orderStateVO.setStateCode(OrderStateEnum.SupplierShipped.code());
					orderStateVO.setStateDes(ss.toString());
				} else {
					if (salesDO.getChangeDlvDate() != null) {
						ss.append("加工中 纳期" + DateUtil.dateToDateTimeString(salesDO.getChangeDlvDate()));
					} else {
						ss.append("加工中");
					}
					orderStateVO.setStateCode(OrderStateEnum.SupplierInProd.code());
					orderStateVO.setStateDes(ss.toString());
				}
				addOrderState(orderStateVO);
			}
		} catch (Exception e) {
			log.error("异常提示: ", e);
		}
		return ResultVo.success("成功更新订单反信" + list.size() + "条");
	}

	private String getCNFactoryByCNOrderTypeName(String orderType) {
		String factoryName = "";
		if (PublicUtil.isNotEmpty(orderType)) {
			return "";
		}
		if (orderType.length() >= 4) {
			factoryName = orderType.substring(0, 4);
			if (factoryName.startsWith("特注品")) {
				factoryName = "中国制造特注品";
			}
		}
		return factoryName;
	}

	public static String convenSupplierCodeByOrderTypeName(String orderTypeName) {
		if (StringUtils.isBlank(orderTypeName)) {
			return "";
		}
		if (orderTypeName.contains("天津")) {
			return "CT";
		} else if (orderTypeName.contains("北京")) {
			return "CM";
		} else if (orderTypeName.contains("中国") || orderTypeName.contains("制造")) {
			return "CN";
		} else if (orderTypeName.contains("上海")) {
			return "TZ";
		} else if (orderTypeName.contains("特注品订单")) {
			return "YZ";
		} else {
			return "";
		}
	}

	@Override
	public ResultVo<String> importOverseaOrderReply(List<OrderStateVO> list) {

		for (OrderStateVO stateVO : list) {
			OrderStateDO stateDO = BeanCopyUtil.copy(stateVO, OrderStateDO.class);
			orderStateMapper.insert(stateDO);
		}
		return ResultVo.success("导入成功");
	}

	@Override
	public ResultVo<String> supplierReplyOrderState(OrderSupplierReplyState replyState) {

        ResultVo<DataTypeVO> dataTypeCodesInfo1 = dictCommonService.getDataTypeCodesInfo("6012", "1");
        if (dataTypeCodesInfo1.isSuccess() && dataTypeCodesInfo1.getData() != null) {
            DataTypeVO data = dataTypeCodesInfo1.getData();
            if ("0".equals(data.getExtNote1())) {
                return ResultVo.failure("此接口现在停用");
            }
        }

		if (replyState.getOrderNo() == null) {
			return ResultVo.failure("订单号不能为空");
		}
		// bugId:13624 C14717 20240306 poAdapter
		//ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "18");
		//if (dataTypeCodesInfo.isSuccess() && dataTypeCodesInfo.getData() != null) {
		//	if (dataTypeCodesInfo.getData().getExtNote1().equals("1")) {
		//		ResultVo<String> stringResultVo = poAdapterFeign
		//				.GZGetReceivingOrdersAndReturnLetter(JSONObject.toJSONString(replyState));
		//	}
		//}

		OrderStateVO orderStateVO = new OrderStateVO();
		orderStateVO.setStateDes(replyState.getStateDes());

		String orderNo = replyState.getOrderNo();
		orderStateVO.setOrderNo(orderNo);

		// 把订单号拆分开 bug 11782 by A78027 20230811
		OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderNo);
		orderStateVO.setRorderNo(orderNoInfo.getOrderNo());
		orderStateVO.setItemNo(orderNoInfo.getItemNo());
		orderStateVO.setSplitNo(orderNoInfo.getSplitItem());

		orderStateVO.setSupplierOrderNo(replyState.getSupplierOrderNo());
		orderStateVO.setOptUserNo(replyState.getOptUser());
		orderStateVO.setOptUserName(replyState.getOptUserName());
		orderStateVO.setPoHolon(replyState.getHolon());
		orderStateVO.setStateDate(replyState.getOptTime());
		/**
		 * 状态代码 1-已接入订单 2-生产中 3-已完工货齐 4-已发货 9-取消订单
		 */
		switch (replyState.getStateCode()) {
		case 1:// 接入订单
			orderStateVO.setStateCode(22);
			orderStateVO
					.setSupplierRcvTime(replyState.getRecieveDate() == null ? new Date() : replyState.getRecieveDate());
			orderStateVO.setPoReplyDate(replyState.getDlvDate());
			break;
		case 2:// 生产中
			orderStateVO.setStateCode(25);
			orderStateVO.setPoReplyDate(replyState.getDlvDate());
			break;
		case 3:// 已完工货齐
			orderStateVO.setStateCode(31);
			break;
		case 4:// 已发货
			orderStateVO.setStateCode(31);
			orderStateVO.setPoShipDate(replyState.getShipDate());
			break;
		case 9:
			orderStateVO.setStateCode(29);// 供应商取消
			break;
		default:
			return ResultVo.failure("状态代码错误" + replyState.getStateCode());
		}

		ResultVo<String> resultVo = addOrderState(orderStateVO);
		if (!resultVo.isSuccess()) {
			return resultVo;
		}

		return ResultVo.success(replyState.getOrderNo(), "更新状态成功" + replyState.getOrderNo());
	}

	@Override
	public ResultVo<String> handleDeleleOrder(HandleDeleteOrderDTO dto) {
		LambdaUpdateWrapper<OrderStateDO> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(OrderStateDO::getOrderNo, dto.getOrderNo()).eq(OrderStateDO::getStateCode, 90);
		OrderStateDO updOrderState = new OrderStateDO();
		updOrderState.setStateCode(91);
		updOrderState.setUpdateTime(new Date());
		updOrderState.setStateDes("删除订单：" + dto.getRemark());
		updOrderState.setOptUserNo(dto.getOptUser());
		int updCount = orderStateMapper.update(updOrderState, updateWrapper);
		return updCount > 0 ? ResultVo.successMsg("更新成功！") : ResultVo.failure("更新失败！");
	}

	/**
	 * 导入中国馆工厂接单时间
	 *
	 * @return
	 */
	public ResultVo<String> importCNMRecieveOrderTime() {
		Object o = redisManager.get(OrderConstants.REDIS_KEY_CNM_RCVTIME);
		String lastDate = "";
		if (null == o) {
			lastDate = DateUtil.dateToDateTimeString(DateUtil.addDay(new Date(), -2));
		} else {
			lastDate = DateUtil.dateToDateTimeString(DateUtil.addHour(DateUtil.stringToDateTime(o.toString()), -1));
		}
		String currentDate = DateUtil.dateToDateTimeString(new Date());
		List<OPSVRequisitionStausToSalesDO> stausToSalesDOList = opsvRequisitionStausToSalesMapper
				.listCNOrderReplyByReceiveTime(lastDate);
		if (stausToSalesDOList.isEmpty()) {
			return ResultVo.success("暂无所需导入数据");
		}

		for (OPSVRequisitionStausToSalesDO item : stausToSalesDOList) {
			OrderStateVO orderStateVO = convertToOrderState(item);
			String factoryName = "";
			if (PublicUtil.isNotEmpty(item.getOrderType()) && item.getOrderType().length() > 4) {
				factoryName = item.getOrderType().substring(0, 4);
			}
			orderStateVO.setStateCode(OrderStateEnum.SupplierRcvOrder.code());
			orderStateVO.setStateDes(factoryName + " " + DateUtil.dateToDateString(orderStateVO.getSupplierRcvTime())
					+ "供应商已接单 " + item.getRemark());
			// orderStateVO.setSupplierOrderNo(item.getRemark());//改成在库出荷or现场
			orderStateVO.setPoHolon(item.getPoHolon());
			addOrderState(orderStateVO);
		}

		// 存储本次最后读取的时间
		redisManager.set(OrderConstants.REDIS_KEY_CNM_RCVTIME,
				DateUtil.dateToDateTimeString(stausToSalesDOList.get(0).getOrderDate()));
		return ResultVo.success("", "导入中国制造接单: " + stausToSalesDOList.size());
	}

	@Override
	public void syncOrderStateInfo() {
		orderStateMapper.syncOrderStateInfo();
	}

	@Override
	public ResultVo<String> importSupplierReplyDate(MultipartFile file) {
		log.info("进入 = > 供应商返信[解析邮件]:");
		if (file == null) {
			return ResultVo.failure("文件为空");
		}
		ExcelHelper excelHelper = null;
		try {
			excelHelper = new ExcelHelper(file.getInputStream());
		} catch (IOException e) {
			log.error("供应商返信[解析邮件]发生异常: ", e);
		}

		Sheet sheet = excelHelper.getSheet();
		int lastRowNum = sheet.getLastRowNum();
		Row rows;
		StringBuilder msg;
		List<OrderStateVO> list = new ArrayList<>();
		List<PurchaseReplyInfo> uplist = new ArrayList<PurchaseReplyInfo>();

        int count = 0;
		for (int row = 1; row <= lastRowNum; row++) {

            if (count == 20) {
                break;
            }

			msg = new StringBuilder();
			rows = sheet.getRow(row);
			if (rows == null) {
                count++;
				continue;
			}
			String cell0 = excelHelper.getCellString(rows.getCell(0)); // 供应商名称
			String cell1 = excelHelper.getCellString(rows.getCell(1)); // 订单号
			String cell2 = excelHelper.getCellString(rows.getCell(2)); // 返信日期
			String cell3 = excelHelper.getCellString(rows.getCell(3)); // 备注
			String cell4 = excelHelper.getCellString(rows.getCell(4)); // 新增-AP项号

			if (StringUtils.isBlank(cell1)) {
                count++;
				continue;
			}
			if (StringUtils.isNotBlank(cell0)) {
				msg.append(cell0 + ":");
			}
			String strDate = "";
			if (StringUtils.isNotBlank(cell2)) {
                try {
                    strDate = conventDateStr(cell2);
                } catch (Exception e) {
                    log.error("供应商返信异常：{} ", e.getMessage());
                    continue;
                }
                if (StringUtils.isNotBlank(strDate)) {
					msg.append("供应商返信纳期:" + strDate);
				}
			}
			if (StringUtils.isNotBlank(cell3)) {
				msg.append(":" + cell3);
			}
			// 推送货期状态
			OrderStateVO orderState = new OrderStateVO();
			orderState.setOrderNo(cell1.trim());
			// add by liYingChao From bugId 9262
			// update by lyc from bugid 13662 in 2024-04-08
			// OrderNoInfo orderNoInfo = new
			// OrderNoInfo().convertFullOrderNo(orderState.getOrderNo());
			OrderNoInfo orderNoInfo = purchaseConvertService.convertPoNoFormPurchase(cell1.trim(), cell4.trim());
			orderState.setRorderNo(orderNoInfo.getOrderNo());
			orderState.setItemNo(orderNoInfo.getItemNo());
			orderState.setSplitNo(orderNoInfo.getSplitItem());
            orderState.setOrderNo(orderNoInfo.getFullOrderNo());
			orderState.setStateDes(msg.toString());
			if (StringUtils.isNotBlank(strDate)) {
				orderState.setPoReplyDate(DateUtil.stringToDate(strDate));
			} else {
				// bug14467海外订单没有返信的则进行接单
				PurchaseReplyInfo temp = new PurchaseReplyInfo();
				temp.setOrderno(orderNoInfo.getOrderNo());
				temp.setItemno(orderNoInfo.getItemNo());
				temp.setSplitno(orderNoInfo.getSplitItem());
				temp.setReplyorderdate(new Date());
				uplist.add(temp);
			}
			orderState.setProvider("POSUP");
			orderState.setStateCode(OrderStateEnum.SupplierInProd.getCode());
			addOrderState(orderState);
			list.add(orderState);
            count = 0;
		}
		// bug14467没有返信的则进行接单
		if (CollectionUtils.isNotEmpty(uplist)) {
			try {
				CommonResult<String> updateInvoice = wmPurchaseFeignApi.updateInvoice(uplist);
				if (!updateInvoice.isSuccess()) {
					log.error("updateInvoice params = {}", JSON.toJSONString(uplist));
					log.error("调用BJ发票接口失败导入失败: {}", updateInvoice.getMessage());
				}
			} catch (Exception e) {
				log.error("调用BJ发票接口失败updateInvoice：", e);
				return ResultVo.failure(e.getMessage());
			}
		}
		log.info("供应商返信解析完毕,共计: " + list.size() + " 数据: " + JSONObject.toJSONString(list));
		return ResultVo.success("供应商返信解析完毕,共计:" + list.size());
	}

	public static String conventDateStr(String strDate) {
		if (StringUtils.isNotBlank(strDate)) {
			strDate = strDate.trim();
			if (strDate.contains("/")) {
				if (strDate.length() > 8) {
					String[] split = strDate.split("/");
					String year = split[0];
					String month = split[1];
					String day = split[2];
					if (Integer.parseInt(month) < 10) {
						month = "0" + month;
					}
					if (Integer.parseInt(day) < 10) {
						day = "0" + day;
					}
					return year + "-" + month + "-" + day;
				} else {
					if (PublicUtil.isNum(strDate.substring(0, 4))) {
						String[] split = strDate.split("/");
						String year = split[0];
						String month = split[1];
						String day = split[2];
						if (Integer.parseInt(month) < 10) {
							month = "0" + month;
						}
						if (Integer.parseInt(day) < 10) {
							day = "0" + day;
						}
						return year + "-" + month + "-" + day;
					} else {
						String[] split = strDate.split("/");
						String day = split[0];
						String month = split[1];
						String year = "20" + split[2];
						return year + "-" + month + "-" + day;
					}
				}
			} else if (strDate.contains(".")) {
				String[] split = strDate.split("\\.");
				String day = split[0];
				String month = split[1];
				String year = "20" + split[2];
				return year + "-" + month + "-" + day;
			} else if (strDate.contains("-")) {
				return strDate;
			} else if (PublicUtil.isNum(strDate)) {
				String year = strDate.substring(0, 4);
				String month = strDate.substring(4, 6);
				String day = strDate.substring(6, 8);
				return year + "-" + month + "-" + day;
			}
		}
		return null;
	}

	/**
	 * 导入中国工厂未完成订单状态
	 *
	 * @return
	 */
	@Override
	public ResultVo<String> importCNMNotFinishOrderState() {

		StringBuilder sbMsg = new StringBuilder();
		boolean hasFault = false;
		// 1.导入接单时间
		try {
			ResultVo<String> resultVo1 = importCNMRecieveOrderTime();
			sbMsg.append(resultVo1.getMessage());
		} catch (Exception e) {
			hasFault = true;
			sbMsg.append("导入中国工厂接单时间失败：" + e.getMessage());
			log.error(e.toString());
		}
		// 2.按更新时间导入返信
		try {
			// 按更新时间导入
			ResultVo<String> resultVo2 = importCNMNotFinishOrderStateByUpdateTime();
			sbMsg.append(resultVo2.getMessage());
		} catch (Exception e) {
			hasFault = true;
			sbMsg.append("导入中国工厂返信失败：" + e.getMessage());
			log.error(e.toString());
		}

		// 3.导入其他没有更新时间的，如果制造解决更新时间为空的可以去掉
		try {
			ResultVo<String> resultVo3 = importCNMNotFinishOrderStateByNoUpdateTime();
		} catch (Exception e) {
			hasFault = true;
			sbMsg.append("导入中国工厂返信失败：" + e.getMessage());
			log.error(e.toString());
		}
		log.info(sbMsg.toString());
		if (hasFault) {
			return ResultVo.failure(sbMsg.toString());
		} else {
			return ResultVo.successMsg(sbMsg.toString());
		}
	}

	public ResultVo<String> importCNMNotFinishOrderStateByUpdateTime() {
		// 从上次存储的时间接着读取
		Object o = redisManager.get(OrderConstants.REDIS_KEY_CNM_NOF_FINISH_ORDER);
		String lastDate = "";
		if (null == o) {
			lastDate = DateUtil.dateToDateTimeString(DateUtil.addDay(new Date(), -2));
		} else {
			lastDate = DateUtil.dateToDateTimeString(DateUtil.addHour(DateUtil.stringToDateTime(o.toString()), -1));
		}

		String currentTime = DateUtil.dateToDateTimeString(new Date());
		List<OPSVRequisitionStausToSalesDO> stausToSalesDOList = opsvRequisitionStausToSalesMapper
				.findReqStausToSalesListByUpDate(lastDate);
		if (stausToSalesDOList.isEmpty()) {
			return ResultVo.success("暂无所需导入数据");
		}
		ResultVo<String> resultVo;
		for (OPSVRequisitionStausToSalesDO item : stausToSalesDOList) {
			OrderStateVO orderStateVO = convertToOrderState(item);
			resultVo = addOrderState(orderStateVO);
		}
		// 存储本次最后读取的时间
		redisManager.set(OrderConstants.REDIS_KEY_CNM_NOF_FINISH_ORDER, currentTime);

		return ResultVo.successMsg("导入中国制造订单状态(按更新时间),共计读取 : " + stausToSalesDOList.size());
	}

	private ResultVo<String> importCNMNotFinishOrderStateByNoUpdateTime() {
		List<OPSVRequisitionStausToSalesDO> cnmOrderStates = opsvRequisitionStausToSalesMapper
				.listCNOrderReplyByNoUpdateTime();
		if (cnmOrderStates.isEmpty()) {
			return ResultVo.success("暂无所需导入数据");
		}
		ResultVo<String> resultVo;
		for (OPSVRequisitionStausToSalesDO item : cnmOrderStates) {
			OrderStateVO orderStateVO = convertToOrderState(item);
			resultVo = addOrderState(orderStateVO);
		}

		return ResultVo.successMsg("导入中国制造订单状态(无更新时间),共计读取 : " + cnmOrderStates.size());
	}

	public OrderStateVO convertToOrderState(OPSVRequisitionStausToSalesDO item) {

		if (StringUtils.isBlank(item.getOrderNo())) {
			return null;
		}

		OrderStateVO orderStateVO = new OrderStateVO();
		OrderNoInfo orderNoInfo;
		if (item.getOrderNo().contains("-")) {
			orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo());
		} else {
			orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo() + item.getItemNo());
		}
		orderStateVO.setOrderNo(orderNoInfo.getFullOrderNo());
		orderStateVO.setPoHolon(item.getPoHolon());
		orderStateVO.setRorderNo(orderNoInfo.getOrderNo());
		orderStateVO.setItemNo(orderNoInfo.getItemNo());
		orderStateVO.setSplitNo(orderNoInfo.getSplitItem());
		// orderStateVO.setPoReplyDate(item.getEsDlvDate());
		orderStateVO.setRemark(item.getRemark());
		orderStateVO.setSupplierRcvTime(item.getOrderDate());
		orderStateVO.setPoInvoiceNo(item.getInvoiceNo());
		orderStateVO.setUpdateTime(new Date());
		orderStateVO.setProvider("cnm");
		orderStateVO.setBeginProduceDate(item.getStartProductionDate());
		orderStateVO.setSupplierCode(convenSupplierCodeByOrderTypeName(item.getOrderType()));

		if (PublicUtil.isNotEmpty(item.getStartProductionDate())) {
			orderStateVO.setStateCode(OrderStateEnum.SupplierInProd.getCode());
		} else {
			orderStateVO.setStateCode(OrderStateEnum.SupplierRcvOrder.getCode());
		}

		// 2023-06-16 bug 11091 改成工厂的订单号
		orderStateVO.setSupplierOrderNo("YY" + item.getRefOrderNo());

		StringBuilder sbStatus = new StringBuilder();
		String factoryName = "";
		if (PublicUtil.isNotEmpty(item.getOrderType()) && item.getOrderType().length() > 4) {
			factoryName = item.getOrderType().substring(0, 4);
			sbStatus.append(factoryName);
		}
		if (PublicUtil.isNotEmpty(item.getStartProductionDate())) {
			sbStatus.append("开始生产日" + DateUtil.dateToDateString(item.getStartProductionDate()));
		}
		// orderStateVO.setStateDes(factoryName + item.getStaus() +
		// DateUtil.dateToDateString(item.getOrderDate()));

		if ("已返信".equals(item.getReturnMsgStatus())) {
			if (StringUtils.isNotBlank(item.getNotSureReturnDate())) {
				orderStateVO = conventSpecialPoDlvDate(item.getNotSureReturnDate(), orderStateVO);
			} else {
				orderStateVO.setPoReplyDate(item.getEsDlvDate());
				orderStateVO.setExpectedLogisticsArrivalDateW(item.getExpectedLogisticsArrivalDateW());
				orderStateVO.setExpectedProductionCompletionDateH(item.getExpectedProductionCompletionDateH());
			}
		}

		// if (item.getEsDlvDate() != null) {
		// sbStatus.append("工厂纳期" +
		// DateUtil.dateToDateString(item.getEsDlvDate()));
		// }

		if (orderStateVO != null && orderStateVO.getPoReplyDate() != null) {
			sbStatus.append("工厂纳期" + DateUtil.dateToDateString(orderStateVO.getPoReplyDate()));
		}

		if ("在库出荷".equalsIgnoreCase(item.getRemark())) {
			orderStateVO.setStateCode(OrderStateEnum.SupplierShipped.code());
			orderStateVO.setPoExpType("*");
			sbStatus.append(" 出工厂在库");
		} else if ("现场".equalsIgnoreCase(item.getRemark())) {
			orderStateVO.setPoExpType("P");
			sbStatus.append(item.getRemark());
		} else {
			orderStateVO.setPoExpType("");
			if (PublicUtil.isNotEmpty(item.getRemark())) {
				sbStatus.append(" ").append(item.getRemark());
			}
		}

		if (StringUtils.isNotBlank(item.getInvoiceNo())) {
			orderStateVO.setStateCode(OrderStateEnum.SupplierShipped.code());
			sbStatus.append(" 已发货" + item.getInvoiceNo());
		} else if (item.getPackDate() != null) {
			orderStateVO.setStateCode(OrderStateEnum.SupplierShipped.code());
			sbStatus.append(DateUtil.dateToDateString(item.getPackDate()) + "已装箱");
		}
		orderStateVO.setStateDes(sbStatus.toString());

		return orderStateVO;
	}

	@Override
	public ResultVo<String> checkOrderState() {
		// 获取需要校对的起始时间
		/*
		 * Object o = redisTemplate.opsForValue().get("ops:checkOrderState");
		 * String curDate = DateUtil.dateToDateString(new Date());
		 * List<CheckOrderStateVO> orderStateVOList; if (o == null) {
		 * orderStateVOList = orderStateMapper.selOrderStateDataList(curDate); }
		 * else { orderStateVOList =
		 * orderStateMapper.selOrderStateDataList(o.toString()); } if
		 * (orderStateVOList.isEmpty()) { return ResultVo.failure("暂无数据需要校对"); }
		 * for (CheckOrderStateVO item : orderStateVOList) { if
		 * (item.getStatus() == null && item.getStateCode() == null) { continue;
		 * } OrderStateEnum fromRcvState =
		 * OrderStateEnum.getFromRcvState(item.getStatus()); if
		 * (item.getStateCode() == fromRcvState.code()) { continue; }
		 * OrderStateVO orderStateVO = BeanCopyUtil.copy(item,
		 * OrderStateVO.class); orderStateVO.setOrderNo(item.getRorderNo() + "-"
		 * + item.getRorderItem());
		 * orderStateVO.setStateCode(fromRcvState.code());
		 * addOrderState(orderStateVO); }
		 */
		return ResultVo.successMsg("数据校对完毕.");
	}

	@Override
	public ResultVo<String> handDelErrorOrder() {

		List<String> list = handDelErrorOrderMapper.handDelErrorOrderDOS();

		if (org.apache.commons.collections.CollectionUtils.isEmpty(list)) {
			return ResultVo.success("数据已经操作完毕");
		}
		int num = 0;
		for (String no : list) {
			List<OrderCancelResult> cancelResultList = new ArrayList<>(1);
			OrderCancelResult cancelResult = new OrderCancelResult();
			cancelResult.setOrderNo(no);
			cancelResult.setResult("2");
			cancelResult.setMessage("系统补推删单");
			cancelResult.setHandlePsnNo("SYSTEM");
			cancelResult.setHandlePsnName("系统处理");
			cancelResult.setHandleRemark("手动处理删单问题");
			cancelResultList.add(cancelResult);
			ResultVo<Boolean> resultVo = smsOrderServiceFeignApi.sendOrderCancelReturnMessage(cancelResultList);
			if (resultVo.isSuccess()) {
				LambdaUpdateWrapper<HandDelErrorOrderDO> updateWrapper = new LambdaUpdateWrapper<>();
				updateWrapper.set(HandDelErrorOrderDO::getHandStatus, 1).eq(HandDelErrorOrderDO::getOrderNo, no);
				handDelErrorOrderMapper.update(null, updateWrapper);
				num++;
			}
			System.out.println("resultVo = " + JSONObject.toJSONString(resultVo));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				log.error("异常提示: ", e);
			}
		}
		return ResultVo.success("推送数据完毕,共成功推送数据: " + num + "未成功 : " + (list.size() - num));
	}

	@Override
	public ResultVo<List<OrderStateDTO>> getSplitOrderState(String rorderNo, String itemNo) {

		if (StringUtils.isBlank(rorderNo) || StringUtils.isBlank(itemNo)) {
			return ResultVo.success(new ArrayList<>());
		}
		LambdaQueryWrapper<OrderStateDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(OrderStateDO::getRorderNo, rorderNo).eq(OrderStateDO::getItemNo, itemNo)
				.gt(OrderStateDO::getSplitNo, "0");
		List<OrderStateDO> orderStateDOS = orderStateMapper.selectList(queryWrapper);

		List<OrderStateDTO> orderStateDTOList = BeanCopyUtil.copyList(orderStateDOS, OrderStateDTO.class);

		if (CollectionUtils.isNotEmpty(orderStateDTOList)) {
			for (OrderStateDTO item : orderStateDTOList) {
				item.setTransType(OPSTransTypeEnum.getNameByCode(item.getTransType()));
				if (StringUtils.isNotBlank(item.getSupplierCode())) {
					item.setSupplierCode(commonService.getSupplierNameByCode(item.getSupplierCode()) + "["
							+ item.getSupplierCode() + "]");
				}
				if (StringUtils.isNotBlank(item.getPurchaseType())) {
					item.setPurchaseType(PurchaseTypeEnum.getName(item.getPurchaseType()));
				}
				if (StringUtils.isNotBlank(item.getWarehouseCode())) {
					item.setWarehouseCode(commonService.getWarehouseNameByCode(item.getWarehouseCode()));
				}
			}
		}
		return ResultVo.success(orderStateDTOList);
	}

	@Override
	public ResultVo<String> downloadJPDeliveryFile() {
		InputStream inputStream = null;
		BufferedInputStream bis = null;
		try {
			// String author = "Basic " +
			// Base64.getEncoder().encodeToString("JP-CN:WZBbkgwY".getBytes());
			//
			// URL url = new URL("http://192.168.168.4:9999/JP-CN/BACKODR.ZIP");
			//
			// URLConnection connection = url.openConnection();
			//
			// connection.setRequestProperty("Authorization", author);
			// connection.connect();
			// log.info("访问 http://192.168.168.4:9999/JP-CN 成功");
			//
			// inputStream = connection.getInputStream();
			String s = FtpFileUtil.dowmloadFile("BACKODR.ZIP", server, user, password, filePath);
			inputStream = new FileInputStream(s);
			bis = new BufferedInputStream(inputStream);
			// 进行文件解压
			return unzip(bis);
		} catch (Exception e) {
			log.error("下载失败: {}", e.getMessage(), e);
			return ResultVo.failure(e.getMessage());
		} finally {
			try {
				if (bis != null) {
					bis.close();
					bis = null;
				}
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
			} catch (IOException e) {
				log.error("释放流异常: ", e);
			}
		}
	}

	@Override
	public ResultVo<PageInfo<OrderStateLogDO>> getOrderStateLogList(OrderStateLogRequest request, int pageNumber,
			int pageSize) {

		if (request == null) {
			return ResultVo.success();
		}

		LambdaQueryWrapper<OrderStateLogDO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(StringUtils.isNotBlank(request.getOrderId()), OrderStateLogDO::getOrderId, request.getOrderId())
				.eq(StringUtils.isNotBlank(request.getOrderNo()), OrderStateLogDO::getOrderNo, request.getOrderNo())
				.ge(StringUtils.isNotBlank(request.getCreateTimeStart()), OrderStateLogDO::getCreateTime,
						request.getCreateTimeStart())
				.le(StringUtils.isNotBlank(request.getCreateTimeEnd()), OrderStateLogDO::getCreateTime,
						request.getCreateTimeEnd());

		PageHelper.startPage(pageNumber, pageSize);
		List<OrderStateLogDO> orderStateLogDOS = orderStateLogMapper.selectList(queryWrapper);
		return ResultVo.success(PageInfo.of(orderStateLogDOS));
	}

	@Override
	public ResultVo<DataTypeVO> testCommonService(String classCode, String code) {
		return dictCommonService.getDataTypeCodesInfo(classCode, code);
	}

	public ResultVo<String> unzip(BufferedInputStream bis) {

		try {
			// 读入流
			ZipInputStream zipInputStream = new ZipInputStream(bis);
			// 遍历每一个文件
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			String unzipFilePath = null;
			ResultVo<String> resultVo;
			while (zipEntry != null) {
				if (!zipEntry.isDirectory()) { // 是否为文件
					// 文件
					unzipFilePath = zipEntry.getName();
					Date lastUpDate = new Date(zipEntry.getLastModifiedTime().to(TimeUnit.MILLISECONDS));
					File file = new File(String.valueOf(zipEntry));
					// 如果是csv文件，则上传数据
					if (unzipFilePath.endsWith(".CSV") || unzipFilePath.endsWith(".csv")) {
						MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
								ContentType.APPLICATION_OCTET_STREAM.toString(), zipInputStream);
						resultVo = saveJPDeliverFile(multipartFile, lastUpDate);
						zipInputStream.close();
						return resultVo;
					}
				}
				zipEntry = zipInputStream.getNextEntry();
			}
		} catch (Exception e) {
			log.error("解压文件发生异常:", e);
			return ResultVo.failure(e.getMessage());
		}
		return ResultVo.failure(" 解析 http://192.168.168.4:9999/JP-CN/BACKODR.ZIP 失败");
	}

	public ResultVo<String> saveJPDeliverFile(MultipartFile file, Date lastUpDate) {
		try {

			DataInputStream dataIn = new DataInputStream(file.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(dataIn, "utf-8"));

			String oneLine = null;

			int count = 1;
			int paseCount = 0;
			while ((oneLine = reader.readLine()) != null) {

				if (count++ == 1) {
					continue;
				}
				JPDeliveryDataVO info = readJPDeliveryLine(oneLine);
				info.setUpdDate(lastUpDate);
				OrderStateVO orderStateVO = jpDeliveryDataToOrderState(info);
				addOrderState(orderStateVO);
				paseCount++;
			}
			reader.close();
			return ResultVo.success("读取完毕,共推送" + paseCount + "条");

		} catch (Exception e) {
			log.error("保存日本在库库存发生异常: {}", e.getMessage(), e);
			return ResultVo.failure(e.getMessage());
		}
	}

	private JPDeliveryDataVO readJPDeliveryLine(String line) {
		JPDeliveryDataVO info = new JPDeliveryDataVO();
		String[] arrs = line.split("\t");
		// List<String> list = Arrays.asList(arrs);
		info.setOrderNo(arrs[7]);
		info.setItem(arrs[8]);
		info.setSupplierOrderNo(arrs[9] + "-" + arrs[10]);
		info.setTransType(arrs[11]);
		info.setModelNo(arrs[13]);
		info.setQty(Integer.parseInt(arrs[15]));
		int qty = Integer.parseInt(arrs[14]);
		int remainQty = Integer.parseInt(arrs[15]);
		info.setFinishQty(qty - remainQty);
		info.setDlvDate(arrs[17]);
		info.setJpDlvDate(arrs[18]);
		info.setRohs(arrs[25]);
		info.setRcvDate(arrs[16]);
		info.setShikomiNo(arrs[20]);
		info.setFactory(arrs[19]); // 出在库
		info.setBaling(arrs[21]); // 已捆包
		info.setJpDlvDate(conventJPDlvDate(info.getJpDlvDate()));
		if (StringUtils.isNotBlank(info.getFactory()) && info.getFactory().length() > 2) {
			info.setFactory(info.getFactory().substring(0, 2));
		}
		return info;
	}
	/*
	 * 使用jpDeliveryDataToOrderState private OrderStateVO
	 * convertOrderState(JPDeliveryDataVO info) { OrderStateVO orderState = new
	 * OrderStateVO();
	 * 
	 * orderState.setModelNo(info.getModelNo());
	 * orderState.setSupplierOrderNo(info.getSupplierOrderNo());
	 * orderState.setSupplierCode("JP"); OrderNoInfo orderNoInfo = new
	 * OrderNoInfo().convertJPOrder(info.getOrderNo(), info.getItem());
	 * 
	 * orderState.setOrderNo(orderNoInfo.getFullOrderNo());
	 * orderState.setRorderNo(orderNoInfo.getOrderNo());
	 * orderState.setItemNo(orderNoInfo.getItemNo());
	 * orderState.setSplitNo(orderNoInfo.getSplitItem());
	 * orderState.setPoNo(orderNoInfo.getPoNo());
	 * orderState.setPurchaseType(orderNoInfo.getPurchaseType()); if
	 * (StringUtils.isNotBlank(info.getTransType())) {
	 * orderState.setTransType(convertTransTypeFromOneChar(info.getTransType().
	 * substring(0, 1))); } orderState.setPoDlvDate(DateUtil.stringToDate("20" +
	 * info.getDlvDate(), "yyyyMMdd")); String dateDes =
	 * getJPDlvDateDes(info.getJpDlvDate()); int errorOrder =
	 * isErrorOrder(info.getJpDlvDate()); // 是否是异常 1 异常 0 采购中 StringBuilder
	 * sbStateDes = new StringBuilder();
	 * sbStateDes.append(DateUtil.getFormatDate(info.getUpdDate(), "yyyyMMdd"));
	 * if (PublicUtil.isNotEmpty(dateDes)) { if (errorOrder == 1) {
	 * orderState.setStateCode(21);//供应商接单异常 // orderState.setStateDes("日本接单异常:"
	 * + dateDes); sbStateDes.append("日本接单异常:").append(dateDes); } else {
	 * orderState.setStateCode(22); // 采购中 if
	 * (StringUtils.isNotBlank(info.getJpDlvDate()) &&
	 * info.getJpDlvDate().equals("66/66/66")) { orderState.setStateCode(25); }
	 * // orderState.setStateDes("日本接单:" + dateDes);
	 * sbStateDes.append("日本接单:").append(dateDes); } // 异常订单设置工厂纳期 if
	 * (StringUtils.isNotBlank(info.getJpDlvDate())) { String poReplayDate =
	 * getPoReplayDate(info.getJpDlvDate()); if
	 * (StringUtils.isNotBlank(poReplayDate)) {
	 * orderState.setPoReplyDate(DateUtil.stringToDate(poReplayDate)); } }
	 * 
	 * } else { // 日本交货期为空且 不是 11/11/11/这种代码 if
	 * (StringUtils.isNotBlank(info.getJpDlvDate())) { String strDate = "20" +
	 * info.getJpDlvDate();
	 * orderState.setPoReplyDate(DateUtil.stringToDate(strDate.replace('/',
	 * '-'))); orderState.setEsArrivalDate(calcEsArrivalDate("JP",
	 * orderState.getTransType(), DateUtil.stringToDate(strDate.replace("/",
	 * "-"))));
	 * 
	 * 
	 * if (info.getFinishQty() != null && info.getFinishQty() >= info.getQty())
	 * { orderState.setStateCode(31);//完工
	 * orderState.setStateDate(info.getUpdDate()); sbStateDes.append("日本货齐待发货");
	 * } else { orderState.setStateCode(25);//生产中
	 * orderState.setStateDate(orderState.getPoReplyDate());
	 * sbStateDes.append("日本生产中，预计出厂日期" +
	 * DateUtil.getFormatDate(orderState.getPoReplyDate(), "yyyyMMdd")); if
	 * (info.getFinishQty() != null && info.getFinishQty() > 0) {
	 * sbStateDes.append(" 已货齐数量").append(info.getFinishQty()); } //
	 * sbStateDes.append(" 剩余分纳数量"+ (info.getQty())); }
	 * sbStateDes.append(",预计达到日期").append(DateUtil.getFormatDate(orderState.
	 * getEsArrivalDate(), "yyyyMMdd")); } else { // 日本交货期为空
	 * orderState.setStateCode(25); // orderState.setStateDes("供应商无返信纳期:" +
	 * dateDes); sbStateDes.append("供应商无返信纳期:").append(dateDes); } }
	 * sbStateDes.append(" ").append(getTransTypeDesc(orderState.getTransType())
	 * ); orderState.setPoExpType(info.getFactory());
	 * orderState.setProvider("JP");
	 * 
	 * if (StringUtils.isNotBlank(orderState.getPoExpType())) { if
	 * (orderState.getPoExpType().startsWith("SG")) {
	 * sbStateDes.append(" 转订新加坡"); } else if
	 * (orderState.getPoExpType().startsWith("VN")) {
	 * sbStateDes.append(" 转订越南"); } else if
	 * ("*".equalsIgnoreCase(orderState.getPoExpType())) {
	 * sbStateDes.append(" 已捆包"); } }
	 * orderState.setStateDes(sbStateDes.toString()); return orderState; }
	 */

	/**
	 * 99是供应商接单异常，66和没有出荷日是生产中，其他没有代码是供应商已接单
	 *
	 * @param info
	 * @return
	 */
	public OrderStateVO jpDeliveryDataToOrderState(JPDeliveryDataVO info) {

		OrderStateVO orderStateVO = new OrderStateVO();
		orderStateVO.setModelNo(info.getModelNo());
		orderStateVO.setSupplierOrderNo(info.getSupplierOrderNo());
		orderStateVO.setSupplierCode("JP");
		orderStateVO.setDateFrom(1);

		// OrderNoInfo orderNoInfo = new
		// OrderNoInfo().convertJPOrder(info.getOrderNo(), info.getItem());
		// bug 13606 优化采购pono解析成orderno
		OrderNoInfo orderNoInfo = purchaseConvertService.convertPoNoFormPurchase(info.getOrderNo(), info.getItem());
		orderStateVO.setOrderNo(orderNoInfo.getFullOrderNo());
		orderStateVO.setRorderNo(orderNoInfo.getOrderNo());
		orderStateVO.setItemNo(orderNoInfo.getItemNo());
		orderStateVO.setSplitNo(orderNoInfo.getSplitItem());
		orderStateVO.setPoNo(orderNoInfo.getPoNo());
		orderStateVO.setPurchaseType(orderNoInfo.getPurchaseType());
		orderStateVO.setTransType(convertTransTypeFromOneChar(info.getTransType()));
		orderStateVO.setPoDlvDate(DateUtil.stringToDate("20" + info.getDlvDate().replace("/", "-")));
		resolveJPDlvDate(info);
		StringBuilder sbStateDes = new StringBuilder();
		sbStateDes.append(DateUtil.getFormatDate(info.getUpdDate(), "yyyyMMdd"));
		orderStateVO.setPoExpType(info.getFactory());
		orderStateVO.setProvider("JP");
		switch (info.getJpDlvDateType()) {
		case 0:// 没有设置交货期
			orderStateVO.setStateCode(OrderStateEnum.SupplierInProd.getCode());
			sbStateDes.append("日本未返回纳期");
			break;
		case 1:// 正确交货期
			orderStateVO.setStateCode(OrderStateEnum.SupplierInProd.getCode());
			orderStateVO.setPoReplyDate(info.getJpDlvDateValue());
			orderStateVO.setEsArrivalDate(
					calcEsArrivalDate("JP", orderStateVO.getTransType(), orderStateVO.getPoReplyDate()));
			if (info.getFinishQty() != null && info.getFinishQty() >= info.getQty()) {
				orderStateVO.setStateCode(OrderStateEnum.SupplierShipped.getCode());// 完工
				orderStateVO.setStateDate(info.getUpdDate());
				sbStateDes.append("日本货齐待发货");
			} else {
				orderStateVO.setStateDate(orderStateVO.getPoReplyDate());
				sbStateDes.append("日本生产中，预计出厂日期" + DateUtil.getFormatDate(orderStateVO.getPoReplyDate(), "yyyyMMdd"));
				if (info.getFinishQty() != null && info.getFinishQty() > 0) {
					sbStateDes.append(" 已货齐数量").append(info.getFinishQty());
				}
				// sbStateDes.append(" 剩余分纳数量"+
				// (info.getQty()-info.getFinishQty()));
			}
			sbStateDes.append(",预计达到日期").append(DateUtil.getFormatDate(orderStateVO.getEsArrivalDate(), "yyyyMMdd"));
			break;
		case 2:// 特殊日期字符
			orderStateVO.setStateCode(OrderStateEnum.SupplierRcvOrder.getCode()); // 22供应商已接单
			sbStateDes.append("日本接单:").append(info.getJpDlvDateDes());
			if (info.getJpDlvDate().equals("66/66/66")) {
				orderStateVO.setStateCode(OrderStateEnum.SupplierInProd.getCode()); // 25生产中
			}
			orderStateVO.setPoReplyDate(info.getJpDlvDateValue());
			break;
		case 9:// 异常状态
			orderStateVO.setStateCode(OrderStateEnum.SupplierReplyProblemOrder.getCode());// 21供应商接单异常
			sbStateDes.append("日本接单异常:").append(info.getJpDlvDateDes());
			orderStateVO.setPoReplyDate(info.getJpDlvDateValue());
			break;
		}
		if (StringUtils.isNotBlank(orderStateVO.getPoExpType())) {
			if (orderStateVO.getPoExpType().startsWith("SG")) {
				sbStateDes.append(" 转订新加坡");
			} else if (orderStateVO.getPoExpType().startsWith("VN")) {
				sbStateDes.append(" 转订越南");
			} else if ("*".equalsIgnoreCase(orderStateVO.getPoExpType())) {
				sbStateDes.append(" 出在库");
			}
		}
		if ("*".equals(info.getBaling())) {
			sbStateDes.append(" 已捆包");
		}
		sbStateDes.append(" ").append(getTransTypeDesc(orderStateVO.getTransType()));
		orderStateVO.setStateDes(sbStateDes.toString());
		return orderStateVO;
	}

	public static String conventJPDlvDate(String dateStr) {
		if (StringUtils.isBlank(dateStr) || dateStr.length() != 8) {
			return "";
		}
		return dateStr.substring(0, 2) + "/" + dateStr.substring(2, 4) + "/" + dateStr.substring(4);
	}

	/**
	 * 根据发出日期计算预计到达日期
	 *
	 * @return
	 */
	/*
	 * public static Date calcArrivalDateByDlvDate1(String supplier, String
	 * transType, Date dlvDate) { if (dlvDate == null || transType == null) {
	 * return null; } switch (supplier) { case "D":// 日本，海运30天，空运7 if
	 * ("1".equals(transType) || "5".equals(transType)) { return
	 * DateUtil.addDay(dlvDate, 7); } return DateUtil.addDay(dlvDate, 30); case
	 * "O": return DateUtil.addDay(dlvDate, 4);// 20170804从7改成4 case "A":// 香港
	 * return DateUtil.addDay(dlvDate, 9); case "Q":// 广州工厂 return dlvDate;
	 * default: return dlvDate; } }
	 */
	private static String getJPStateDesc(String date) {
		String str = null;
		if (PublicUtil.isEmpty(date)) {
			return "未确定交货期";
		}
		switch (date) {
		case "11/11/11":
			str = "要图纸";
			break;
		case "22/22/22":
			str = "型号确认中";
			break;
		case "33/33/33":
			str = "开发部正在组装";
			break;
		case "44/44/44":
			str = "紧急生产停止";
			break;
		case "55/55/55":
			str = "部品不足";
			break;
		case "66/66/66":
			str = "部品不足";
			break;
		case "77/77/77":
			str = "正在确认中";
			break;
		case "88/88/88":
			str = "询问图纸中";
			break;
		case "99/99/99":
			str = "问题订单,等待修正";
			break;
		}
		return str;
	}

	private static String getTransTypeFromJPCode(String code) {
		if (PublicUtil.isEmpty(code)) {
			return "0";
		}
		if (code.startsWith("AIR")) {
			return "1";
		}
		return "0";
	}

	/**
	 * 对比当前状态和要更新的状态和配置判断是否允许变更的状态
	 *
	 * @param toStatus
	 * @param prevStatus
	 * @return
	 */
	public boolean isAllowChangeStatus(Integer toStatus, Integer prevStatus) {
		if (prevStatus == null) {
			return true;
		}
		String redisKey = com.smc.smccloud.model.Constants.REDIS_KEY_ALLOW_UPDATE_ORDER_STATE;
		// 获取状态可变更配置
		Object o = redisManager.hGet(redisKey, "all");

		if (redisManager.hGet(redisKey, "all") == null) {
			ResultVo<List<DataCodeVO>> dataCodes = dictCommonService
					.getDataCodes(com.smc.smccloud.model.Constants.DICT_CLASSCODE_ORDERSTATE);
			if (!dataCodes.isSuccess() || CollectionUtils.isEmpty(dataCodes.getData())) {
				return true;
			}

			int count = 0;
			// Map<String, DataCodeVO> map = new HashMap<>();
			List<DataCodeVO> dataCodeVOList = dataCodes.getData();
			for (DataCodeVO item : dataCodeVOList) {
				if (StringUtils.isNotBlank(item.getExtNote3())) {
					redisManager.hPut(redisKey, item.getCode(), item.getExtNote3());
					count++;
				}
			}
			redisManager.hPut(redisKey, "all", count);
			redisManager.expire(redisKey, 86400);
		}

		Object allowStatus = redisManager.hGet(redisKey, Integer.toString(toStatus));
		if (PublicUtil.isEmpty(allowStatus)) {
			return true;
		}
		if (!allowStatus.toString().contains(prevStatus + ",")) {
			return false;
		}
		return true;

	}

	@Override
	public Date calcEsArrivalDate(String warehouseCode, String supplierCode, String transType, Date dlvDate) {
		if (PublicUtil.isEmpty(warehouseCode)) {
			return calcEsArrivalDate(supplierCode, transType, dlvDate);
		}

		String key = com.smc.smccloud.model.Constants.REDIS_KEY_TRANS_DAYS;
		if (!redisManager.hasKey(key)) {
			ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes("2073");
			if (!dataCodes.isSuccess() || CollectionUtils.isEmpty(dataCodes.getData())) {
				throw new BusinessException("获取数据字典失败 2073");
			}
			for (DataCodeVO dataCodeVO : dataCodes.getData()) {
				redisManager.hPut(key, dataCodeVO.getCode(), dataCodeVO.getExtNote1());
			}
			redisManager.expire(key, 86400);
		}

		Object objTransDays = redisManager.hGet(key, supplierCode + "-" + warehouseCode);
		if (objTransDays != null) {
			String strDays = objTransDays.toString();
			String[] arrsDays = strDays.split("-");
			if (arrsDays.length == 4) {
				switch (Optional.ofNullable(transType).orElse("")) {
				case "1": // 空运
					return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[0]));
				case "4": // 快船
					return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[1]));
				case "0": // 海运
					return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[2]));
				default:
					return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[3]));
				}
			}
		} else {

			if ("GZ,CN,CM,CT,YZ".contains(supplierCode)) {
				objTransDays = redisManager.hGet(key, "cnother");
				String strDays = objTransDays.toString();
				String[] arrsDays = strDays.split("-");

				if (arrsDays.length == 4) {
					switch (transType) {
					case "1": // 空运
						return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[0]));
					case "3": // 陆运
						return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[1]));
					case "5": // 铁路
						return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[2]));
					default:
						return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[3]));
					}
				}
			} else {
				objTransDays = redisManager.hGet(key, "other");
				String strDays = objTransDays.toString();
				String[] arrsDays = strDays.split("-");
				if (arrsDays.length == 4) {
					switch (Optional.ofNullable(transType).orElse("")) {
					case "1": // 空运
						return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[0]));
					case "4": // 快船
						return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[1]));
					case "0": // 海运
						return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[2]));
					default:
						return DateUtil.addDay(dlvDate, Integer.valueOf(arrsDays[3]));
					}
				}
			}

		}

		return null;

	}

	/**
	 * 转发合并订单消息
	 */
	private void resendMergeOrderState(OrderStateVO orderStateVO) {
		// 10814bug 如果是采购合并单 则根据合并单号查出其原单号,将当前对象接收到的对象值按照原单号推送到交货期队列
		if (orderStateVO.getOrderNo().startsWith("MR")) {
			log.info("转发合并订单消息 {} ", JSONUtil.toJsonStr(orderStateVO));
			String mrOrderNo = orderStateVO.getOrderNo();
			List<String> poOrderNoByMrNoSendMq = getPoOrderNoByMrNoSendMq(orderStateVO.getRorderNo());
			for (String item : poOrderNoByMrNoSendMq) {
				OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(item);
				OrderStateVO stateVO = new OrderStateVO();
				stateVO = orderStateVO;
				stateVO.setOrderNo(item);
				stateVO.setRorderNo(orderNoInfo.getOrderNo());
				stateVO.setItemNo(orderNoInfo.getItemNo());
				stateVO.setStateDate(new Date());
				stateVO.setRemark(mrOrderNo);
				if (orderNoInfo.getSplitItem() != null) {
					stateVO.setSplitNo(orderNoInfo.getSplitItem());
				}
				stateVO.setProvider("sys_po");
				addOrderState(stateVO);
			}
		}
	}

	/**
	 * 计算延误天数
	 * 
	 * @param orderState
	 * @return
	 */
	public Integer calcOrderStateDelayDay(OrderStateVO orderState) {
		// 采购中状态,预设定的预计到货日期对比，计算预计延误天数
		if ((orderState.getStateCode().compareTo(20) >= 1 && orderState.getStateCode().compareTo(27) == 0)
				|| (orderState.getStateCode().compareTo(30) >= 1 && orderState.getStateCode().compareTo(48) == 0)) {
			orderState.setPoDelayDay(calcDelayDay(orderState.getEsArrivalDate(), orderState.getEsArriveDateReq()));
			//
		}

		return 0;
	}

	private Integer calcDelayDay(Date estDate, Date date) {
		if (estDate == null || date == null) {
			return 0;
		}
		Long day = DateUtil.getDiffDay(date, estDate);
		if (day.compareTo(0L) < 0) {
			day = 0L;
		}
		return day.intValue();
	}

	/**
	 * 查询国内从仓库发货到营业所的设置天数
	 * 
	 * @param warehouseCode
	 * @param deptNo
	 * @return
	 */
	private Integer getConfigDomesticShipDay(String warehouseCode, String deptNo) {
		return 3;
	}

	private OrderStateVO conventSpecialPoDlvDate(String dateStr, OrderStateVO orderStateVO) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}

		if (!dateStr.startsWith("99") && dateStr.endsWith("99")) {
			String yearStr = dateStr.substring(0, 2);
			String monthStr = dateStr.substring(2, 4);
			int daysInMonth = PublicUtil.getDaysInMonth(Integer.parseInt(yearStr), Integer.parseInt(monthStr));
			String conventDateStr = "99" + yearStr + "-" + monthStr + "-" + daysInMonth;
			orderStateVO.setPoReplyDate(DateUtil.stringToDate(conventDateStr));
			return orderStateVO;
		}

		Date date = null;
		switch (dateStr.trim()) {
		case "111111":
			date = DateUtil.stringToDate("1111-01-01");
			orderStateVO.setStateDes("日本接单异常:要图纸11/11/11");
			orderStateVO.setStateCode(OrderStateEnum.SupplierReplyProblemOrder.getCode());// 21供应商接单异常
			// orderStateVO.setPoDlvDate(date);
			orderStateVO.setPoReplyDate(date);
			break;
		case "222222":
			date = DateUtil.stringToDate("2222-02-02");
			orderStateVO.setStateDes("日本接单异常:型号确认中订单正转交正确部门22/22/22");
			orderStateVO.setStateCode(OrderStateEnum.SupplierReplyProblemOrder.getCode());// 21供应商接单异常
			// orderStateVO.setPoDlvDate(date);
			orderStateVO.setPoReplyDate(date);
			break;
		case "333333":
			date = DateUtil.stringToDate("3333-03-03");
			orderStateVO.setStateDes("日本接单异常:正在日本研发中心组装33/33/33");
			orderStateVO.setStateCode(OrderStateEnum.SupplierReplyProblemOrder.getCode());// 21供应商接单异常
			// orderStateVO.setPoDlvDate(date);
			orderStateVO.setPoReplyDate(date);
			break;
		case "444444":
			date = DateUtil.stringToDate("4444-04-04");
			orderStateVO.setStateDes("日本接单异常:紧急生产停止44/44/44");
			orderStateVO.setStateCode(OrderStateEnum.SupplierReplyProblemOrder.getCode());// 21供应商接单异常
			// orderStateVO.setPoDlvDate(date);
			orderStateVO.setPoReplyDate(date);
			break;
		case "555555":
			date = DateUtil.stringToDate("5555-05-05");
			orderStateVO.setStateDes("日本接单:部品订向其他工厂，无确切交货期55/55/55");
			orderStateVO.setStateCode(OrderStateEnum.SupplierRcvOrder.getCode()); // 22供应商已接单
			// orderStateVO.setPoDlvDate(date);
			orderStateVO.setPoReplyDate(date);
			break;
		case "666666":
			date = DateUtil.stringToDate("6666-06-06");
			orderStateVO.setStateDes("日本接单:部品订向外协，无确切交货期66/66/66");
			orderStateVO.setStateCode(OrderStateEnum.SupplierInProd.getCode()); // 25生产中
			// orderStateVO.setPoDlvDate(date);
			orderStateVO.setPoReplyDate(date);
			break;
		case "777777":
			date = DateUtil.stringToDate("7777-07-07");
			orderStateVO.setStateDes("日本接单:加工完毕日期不确认，正在确认中77/77/77");
			orderStateVO.setStateCode(OrderStateEnum.SupplierRcvOrder.getCode()); // 22供应商已接单
			// orderStateVO.setPoDlvDate(date);
			orderStateVO.setPoReplyDate(date);
			break;
		case "888888":
			date = DateUtil.stringToDate("8888-08-08");
			orderStateVO.setStateDes("日本接单异常:日本制造部向研发询问图纸中88/88/88");
			orderStateVO.setStateCode(OrderStateEnum.SupplierReplyProblemOrder.getCode());// 21供应商接单异常
			// orderStateVO.setPoDlvDate(date);
			orderStateVO.setPoReplyDate(date);
			break;
		case "999999":
			date = DateUtil.stringToDate("9999-09-09");
			orderStateVO.setStateDes("日本接单异常:订单异常，请登录AS400查看异常原因并及时进行处理");
			orderStateVO.setStateCode(OrderStateEnum.SupplierReplyProblemOrder.getCode());// 21供应商接单异常
			// orderStateVO.setPoDlvDate(date);
			orderStateVO.setPoReplyDate(date);
			break;
		case "999900":
			date = DateUtil.stringToDate("9999-09-09");
			orderStateVO.setStateDes("日本接单异常:要提供试样书99/00/00");
			orderStateVO.setStateCode(OrderStateEnum.SupplierReplyProblemOrder.getCode());// 21供应商接单异常
			// orderStateVO.setPoDlvDate(date);
			orderStateVO.setPoReplyDate(date);
			break;
		default:
			String conventDateStr = "20" + dateStr;
			String year = conventDateStr.substring(0, 4);
			String month = conventDateStr.substring(4, 6);
			String day = conventDateStr.substring(6, 8);
			String ymd = year + "-" + month + "-" + day;
			if (!PublicUtil.isValidDate(ymd)) {
				orderStateVO.setPoReplyDate(DateUtil.stringToDate("9999-12-31"));
			} else {
				date = DateUtil.stringToDate(ymd);
				orderStateVO.setPoReplyDate(date);
			}
			break;
		}
		return orderStateVO;
	}

}
