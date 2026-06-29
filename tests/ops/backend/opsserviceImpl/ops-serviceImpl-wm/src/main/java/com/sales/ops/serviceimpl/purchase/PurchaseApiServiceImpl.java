package com.sales.ops.serviceimpl.purchase;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.sales.ops.common.constants.Constants;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.OrderNoUtil;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.PurchaseInvoiceDao;
import com.sales.ops.db.extdao.PurchaseOrderDao;
import com.sales.ops.dto.purchase.PoReplyInfoDto;
import com.sales.ops.dto.purchase.PoToWmDto;
import com.sales.ops.dto.purchase.PurchaseReplyInfo;
import com.sales.ops.dto.purchase.PurchaseUpdateInfo;
import com.sales.ops.dto.util.LogUtil;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.enums.*;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.feign.PurchaseDeliverFeignApi;
import com.sales.ops.redisson.OpsRedissonLock;
import com.sales.ops.service.dispatch.PoDispatcherService;
import com.sales.ops.service.inventory.AdjustInventoryService;
import com.sales.ops.service.inventory.WmDispatchService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.purchase.PurchaseApiService;
import com.sales.ops.serviceimpl.event.v3.PurchaseEventPublisher;
import com.sales.ops.utils.PoNoUtil;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PurchaseApiServiceImpl implements PurchaseApiService {

	private final static Logger logger = LoggerFactory.getLogger(PurchaseApiServiceImpl.class);

	@Autowired
	private OpsRequestpurchaseMapper opsRequestpurchaseMapper;

	@Autowired
	private OpsPurchaseinvoiceMapper opsPurchaseinvoiceMapper;

	@Autowired
	private OpsImpdataMapper opsImpdataMapper;

	@Autowired
	private OpsPurchaseorderMapper opsPurchaseorderMapper;

	@Autowired
	private WmDispatchService wmDispatchService;

	@Autowired
	private ImpInvoiceDetailPackMapper impInvoiceDetailPackMapper;

	@Autowired
	private OpsPurchaseorderCancelLogMapper opsPurchaseorderCancelLogMapper;

	@Autowired
	private ImpInvoiceMasterMapper impInvoiceMasterMapper;

	@Autowired
	private OpsPurchaseinvoicetranslogMapper opsPurchaseinvoicetranslogMapper;

	@Autowired
	private OpsRequestpurchaseCancelLogMapper opsRequestpurchaseCancelLogMapper;

	@Autowired
	private PurchaseOrderDao purchaseOrderDao;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private PoDispatcherService poDispatcherService;

	@Autowired
	private OpsPoDeliveryPlanMapper opsPoDeliveryPlanMapper;

	@Autowired
	private PurchaseEventPublisher purchaseEventPublisher;

	@Autowired
	private PurchaseDeliverFeignApi purchaseDeliverFeignApi;

	@Autowired
	private OpsPoDeliveryFactMapper opsPoDeliveryFactMapper;

	@Autowired
	private PurchaseInvoiceDao purchaseInvoiceDao;

	@Autowired
	private BasePoService basePoService;
	@Autowired
	private OpsRedissonLock opsRedissonLock;
    @Autowired
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Autowired
    private AdjustInventoryService adjustInventoryService;

	private static final String keyPrefix="ops:rediss:poAccept:";

    /**
     * bugid:19405
     * "CN","CM","CT","TZ","YZ","GZ"
     * @return
     * @throws OpsException
     */
    @Override
    public List<String> manufactureSupplier() throws OpsException {
		List<String> list = new ArrayList<String>();
        // 集团标准编码客户对应
        ResultVo<List<DataCodeVO>> resultVo = dictDataServiceFeignApi.getDataCodes("3016");
        List<DataCodeVO> dictData = resultVo.getData();
        if (dictData == null || dictData.isEmpty()) {
            throw Exceptions.OpsException("获取中国制造供应商校验列表失败:dict 3016");
        }
        for (DataCodeVO dict : dictData) {
            list.add(dict.getCode());
        }
		return list;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> updateInvoice(List<PurchaseReplyInfo> list) throws Exception {
		if (CollectionUtils.isEmpty(list)) {
			throw Exceptions.OpsException("更新的列表为空，请重试");
		}
		// log
		updateLog(list);

		// 在途库存
		List<OpsPurchaseinvoice> transList = new ArrayList<OpsPurchaseinvoice>();
		// 修改预到货日期
		List<OpsPurchaseinvoice> preImpDateList = new ArrayList<OpsPurchaseinvoice>();
		Map<String, String> errorInfoMap = new HashMap<>();
		for (PurchaseReplyInfo o : list) {
			// 需增加生产在途库存的列表
			List<OpsPurchaseorder> produceList = new ArrayList<OpsPurchaseorder>();
			List<OpsPurchaseorder> temp = null;
			// 20221027增加对接单日期及接单号的判断,若有返信纳期也等于接单 for bug8497 by 马腾
			// 20260304 bug18341 删除存在返信日期时，接单日为空的情况下对接单号，接单日期的补偿。
			//			if (o.getReplyexportdate() != null) {
			//				if (o.getReplyorderno() == null) {
			//					o.setReplyorderno("system");
			//				}
			//				if (o.getReplyorderdate() == null) {
			//					o.setReplyorderdate(new Date());
			//				}
			//			}
			OpsPurchaseinvoice purchaseinvoice = new OpsPurchaseinvoice();
			// 避免死锁，更换为使用feign查询
			// CommonResult<List<OpsPurchaseinvoice>> l;
			OpsPurchaseinvoiceExample opsPurchaseinvoiceExample = new OpsPurchaseinvoiceExample();
			if (StringUtils.isBlank(o.getOrderno()) && StringUtils.isNotBlank(o.getPono())) {
				// l =
				// purchaseDeliverFeignApi.getPurchaseInvoiceByPono(o.getPono(),
				// o.getLineitem());
				opsPurchaseinvoiceExample.createCriteria().andPonoEqualTo(o.getPono())
						.andLineitemEqualTo(o.getLineitem());
			} else if (StringUtils.isNotBlank(o.getOrderno())) {
				// 增加拆分单号为0的判断，pack表第一项会将为空的拆分单号赋值为0
				// bug15736 增加拆分单号为0的判断
				if (o.getSplitno() == null || o.getSplitno() == 0) {
					// l =
					// purchaseDeliverFeignApi.getPurchaseInvoice(o.getOrderno(),
					// o.getItemno(), 0);
					opsPurchaseinvoiceExample.createCriteria().andOrdernoEqualTo(o.getOrderno())
							.andItemnoEqualTo(o.getItemno()).andSplititemnoIsNull();
				} else {
					// l =
					// purchaseDeliverFeignApi.getPurchaseInvoice(o.getOrderno(),
					// o.getItemno(), o.getSplitno());
					opsPurchaseinvoiceExample.createCriteria().andOrdernoEqualTo(o.getOrderno())
							.andItemnoEqualTo(o.getItemno()).andSplititemnoEqualTo(o.getSplitno());
				}
			} else {
				continue;
			}
			List<OpsPurchaseinvoice> l = opsPurchaseinvoiceMapper.selectByExample(opsPurchaseinvoiceExample);
			if (CollectionUtils.isEmpty(l)) {
				// if (l.isSuccess() && CollectionUtils.isEmpty(l.getData())) {
				// 若来货数据为不存在订单，则主动增加对应的采购单
				OpsPurchaseinvoice n = new OpsPurchaseinvoice();
				// bug 11687 马腾 判断参数中使用orderno还是pono
				if (StringUtils.isNotBlank(o.getOrderno())) {
					n.setOrderno(o.getOrderno());
					n.setItemno(o.getItemno());
					n.setPono(o.getOrderno() + "-" + o.getItemno().toString());
					if (o.getSplitno() != null) {
						n.setSplititemno(o.getSplitno());
						n.setPono(n.getPono() + "-" + o.getSplitno().toString());
					}
					n.setLineitem(1);
				} else {
					continue;
				}

				if (o.getSupplierid() == null || o.getWarehouseid() == null || o.getModelno() == null) {
					// logger.error("错误发票导入,po:" + o.getPono());
					continue;
				}
				n.setModelno(o.getModelno());
				n.setJpsplitvt(0);
				n.setStatecode(PurchaseInvoiceStatusEnum.YIFASONG);
				n.setUpdatetime(new Date());
				n.setSupplierid(o.getSupplierid());
				n.setReceivewarehouseid(o.getWarehouseid());
				//opsPurchaseinvoiceMapper.insertSelective(n);
				purchaseInvoiceDao.insertOpsPurchaseinvoice(n);
				purchaseinvoice = n;
			} else {
				purchaseinvoice = l.get(0);
			}
			// invoice表更新的标识
			int invoiceflag = 0;
			// 回更订单标志
			int orderflag = 0;
			// bugid：17385 传事件标识
			boolean eventFlag = false;
			PurchaseUpdateInfo info = new PurchaseUpdateInfo();
			info.setOrderno(purchaseinvoice.getOrderno());
			info.setItemno(purchaseinvoice.getItemno());
			if (purchaseinvoice.getSplititemno() != null)
				info.setSplitno(purchaseinvoice.getSplititemno());
			// 更新采购表
			OpsPurchaseorder purchaseorder = new OpsPurchaseorder();
			int purchaseflag = 0;
			// 变更供应商// bug10303 供应商判断空字符串 马腾
			// bug11945马腾 增加供应商转订判断，只有中国制造内部转订的接单遇到货数据才接入，其余的不接入
			if (StringUtils.isNotBlank(o.getSupplierid())
					&& !StringUtils.equals(o.getSupplierid(), purchaseinvoice.getSupplierid())) {
				if (manufactureSupplier().contains(o.getSupplierid())
						&& manufactureSupplier().contains(purchaseinvoice.getSupplierid())) {
					// bug12645 YZ的采购单，发票信息是CN的不修改供应商
					if (StringUtils.equals("YZ", purchaseinvoice.getSupplierid())
							&& StringUtils.equals("CN", o.getSupplierid())) {
						if (StringUtils.isBlank(o.getInvoiceno())) {
							purchaseinvoice.setSupplierid(o.getSupplierid());
							purchaseorder.setSupplierid(o.getSupplierid());
							info.setSupplierid(o.getSupplierid());
							invoiceflag = 1;
							purchaseflag = 1;
							orderflag = 1;
						}
					} else {
						purchaseinvoice.setSupplierid(o.getSupplierid());
						purchaseorder.setSupplierid(o.getSupplierid());
						info.setSupplierid(o.getSupplierid());
						purchaseflag = 1;
						orderflag = 1;
						invoiceflag = 1;
					}
				} else {
					continue;
				}
			}

			// 增加生产中库存
			OpsPurchaseorderExample ex = new OpsPurchaseorderExample();
			if (purchaseinvoice.getSplititemno() != null) {
				ex.createCriteria().andOrdernoEqualTo(purchaseinvoice.getOrderno())
						.andItemnoEqualTo(purchaseinvoice.getItemno())
						.andSplititemnoEqualTo(purchaseinvoice.getSplititemno());
			} else {
				ex.createCriteria().andOrdernoEqualTo(purchaseinvoice.getOrderno())
						.andItemnoEqualTo(purchaseinvoice.getItemno()).andSplititemnoIsNull();
			}

			// 是否接单：接单日期，有可能不返回接单日期而是延期原因
			// bug13927 调用此接口时若发现未接单均进行接单处理
			if (purchaseinvoice.getReplyorderdate() == null && StringUtils.isBlank(purchaseinvoice.getReplyorderno()) ) {
				invoiceflag = 1;
				purchaseinvoice.setStatecode(PurchaseInvoiceStatusEnum.YIJIEDAN);
				if (o.getReplyorderdate() != null) {
					purchaseinvoice.setReplyorderdate(o.getReplyorderdate());
				}
				if (o.getReplyorderno() != null) {
					purchaseinvoice.setReplyorderno(o.getReplyorderno());
				} else {
					purchaseinvoice.setReplyorderno("system");
				}
				// 判断是否为采购时因为供应商不接受整型号拆分情况
				if (purchaseinvoice.getJpsplitvt() == 0
						|| (purchaseinvoice.getJpsplitvt() > 0 && purchaseinvoice.getLineitem() == 1)) {
					temp = opsPurchaseorderMapper.selectByExample(ex);
					if (temp != null && temp.size() > 0) {
						// bug10185 MT 更新供应商
						if (StringUtils.isNotBlank(o.getSupplierid())
								&& !StringUtils.equals(o.getSupplierid(), temp.get(0).getSupplierid())) {
							temp.get(0).setSupplierid(o.getSupplierid());
						}
						// bug10440 马腾 DR订单不调用后续接口发送至物流
						if (!StringUtils.equals(purchaseinvoice.getOrdtype(), OrderTypeEnum.DR)
								&& !StringUtils.equals(purchaseinvoice.getOrdtype(), OrderTypeEnum.CR)) {
							produceList.addAll(temp);
						}

						purchaseorder.setStatecode(PurchaseOrderStatusEnum.YIJIEDAN);
						if (o.getReplyorderno() != null) {
							purchaseorder.setReplyorderno(o.getReplyorderno());
						}
						purchaseflag = 1;
					} else {
						// 查询已删除数据
						OpsPurchaseorderCancelLogExample e = new OpsPurchaseorderCancelLogExample();
						if (purchaseinvoice.getSplititemno() == null) {
							e.createCriteria().andOrdernoEqualTo(purchaseinvoice.getOrderno())
									.andItemnoEqualTo(purchaseinvoice.getItemno()).andSplititemnoIsNull();
						} else {
							e.createCriteria().andOrdernoEqualTo(purchaseinvoice.getOrderno())
									.andItemnoEqualTo(purchaseinvoice.getItemno())
									.andSplititemnoEqualTo(purchaseinvoice.getSplititemno());
						}
						List<OpsPurchaseorderCancelLog> tempC = opsPurchaseorderCancelLogMapper.selectByExample(e);
						if (tempC != null && tempC.size() > 0) {
							temp = new ArrayList<OpsPurchaseorder>();
							// for (OpsPurchaseorderCancelLog h : tempC) {
							OpsPurchaseorderCancelLog h = tempC.get(0);
							OpsPurchaseorder u = new OpsPurchaseorder();
							u.setOrderno(h.getOrderno());
							u.setItemno(h.getItemno());
							if (h.getSplititemno() != null) {
								u.setSplititemno(h.getSplititemno());
							}
							u.setModelno(h.getModelno());
							u.setQuantity(h.getQuantity());
							// u.setSupplierid(h.getSupplierid());
							// bug10185 MT 更新供应商
							if (StringUtils.isNotBlank(o.getSupplierid())
									&& !StringUtils.equals(o.getSupplierid(), h.getSupplierid())) {
								u.setSupplierid(o.getSupplierid());
							}
							u.setReceivewarehouseid(h.getReceivewarehouseid());// 采购仓
							u.setGreencode(h.getGreencode());
							u.setMergeflag(false);
							temp.add(u);
							// }
							// bug10440 马腾 DR订单不调用后续接口发送至物流
							if (!StringUtils.equals(purchaseinvoice.getOrdtype(), OrderTypeEnum.DR)
									&& !StringUtils.equals(purchaseinvoice.getOrdtype(), OrderTypeEnum.CR)) {
								produceList.addAll(temp);
							}
						}
					}
				}
			}
			// 是否需要接单
			if (!CollectionUtils.isEmpty(produceList)) {
				boolean acceptsuccess = true;
				for (OpsPurchaseorder i : produceList) {
					boolean result = acceptPurchaseOperate(i, errorInfoMap);
					if (!result) {
						acceptsuccess = false;
					}
				}
				// 如果接单失败则执行下一订单
				if (!acceptsuccess) {
					continue;
				}
			}

			// 在途
			if (o.getInvoiceno() != null && o.getQtyTrans() != null) {
				// 查询之前此发票是否已预到货
				OpsPurchaseinvoicetranslogExample logex = new OpsPurchaseinvoicetranslogExample();
				logex.createCriteria().andPonoEqualTo(purchaseinvoice.getPono())
						.andLineitemEqualTo(purchaseinvoice.getLineitem()).andInvoiceidEqualTo(o.getInvoiceid())
						.andInvoicenoEqualTo(o.getInvoiceno());
				List<OpsPurchaseinvoicetranslog> loglist = opsPurchaseinvoicetranslogMapper.selectByExample(logex);
				if (loglist == null || loglist.size() == 0) {
					// bug10440 马腾 DR订单不调用后续接口发送至物流
					if (!StringUtils.equals(purchaseinvoice.getOrdtype(), OrderTypeEnum.DR)
							&& !StringUtils.equals(purchaseinvoice.getOrdtype(), OrderTypeEnum.CR)) {
						// bug15340 增加数量判断，若为多到货则不进行预到货操作，避免影响其他采购单无法预到货
						if (purchaseinvoice.getQuantity() == null || purchaseinvoice.getQtyreceive() == null
								|| purchaseinvoice.getQtytrans() == null
								|| (purchaseinvoice.getQuantity().compareTo(purchaseinvoice.getQtyreceive()) > 0
										&& purchaseinvoice.getQuantity()
												.compareTo(purchaseinvoice.getQtytrans()) > 0)) {
							transList.add(purchaseinvoice);
							// 写入预到货记录表
							OpsPurchaseinvoicetranslog log = new OpsPurchaseinvoicetranslog();
							log.setPono(purchaseinvoice.getPono());
							log.setLineitem(purchaseinvoice.getLineitem());
							log.setInvoiceid(o.getInvoiceid());
							log.setInvoiceno(o.getInvoiceno());
							log.setQuantity(o.getQtyTrans());
							opsPurchaseinvoicetranslogMapper.insertSelective(log);
							// 更新purchaseinvoice表
							invoiceflag = 1;
							purchaseinvoice.setInvoiceno(o.getInvoiceno());
							purchaseinvoice.setInvoiceid(o.getInvoiceid());
							purchaseinvoice.setQtytrans(o.getQtyTrans());
							purchaseinvoice.setImdatetheory(o.getImpdate());
                            if (o.getDeliverytimeHFact() != null) {
                                purchaseinvoice.setFacexpdate(o.getDeliverytimeHFact());
                            } else if (o.getDeliverytimeWFact() != null) {
                                purchaseinvoice.setFacexpdate(o.getDeliverytimeWFact());
                            } else if (o.getDeliverytimeAFact() != null) {
                                purchaseinvoice.setFacexpdate(o.getDeliverytimeAFact());
                            }
							// 更新purchaseorder表
							purchaseflag = 1;
							purchaseorder.setStatecode(PurchaseOrderStatusEnum.YUNSHUZHONG);

							// 同时更新 purchaseinvoice 表的 stateCode 为"运输中"
							// bug20565
							purchaseinvoice.setStatecode(PurchaseOrderStatusEnum.YUNSHUZHONG);
							invoiceflag = 1;  // 标记需要更新发票表

							// bug14526 fact数据写入
							if (StringUtils.isBlank(o.getModelno()))
								o.setModelno(purchaseinvoice.getModelno());
							purchaseDeliverFeignApi.insertFactPre(o);
						} else {
							// TODO 后续增加异常记录及邮件提醒
							logger.error("多到货跳过预到货步骤:" + purchaseinvoice.getOrderno() + "-"
									+ purchaseinvoice.getItemno().toString() + (purchaseinvoice.getSplititemno() == null
											? "" : ("-" + purchaseinvoice.getSplititemno().toString())));
						}
					}
				} else if (o.getImpdate() != null && (purchaseinvoice.getImdatetheory() == null
						|| o.getImpdate().compareTo(purchaseinvoice.getImdatetheory()) != 0)) {
					// bug14323 if判断中的或运算，将判断是否为空放在前面
					// bug10886 修改预到货日期时 同时修改move中的数据
					preImpDateList.add(purchaseinvoice);
					invoiceflag = 1;
					purchaseinvoice.setInvoiceno(o.getInvoiceno());
					purchaseinvoice.setInvoiceid(o.getInvoiceid());
					purchaseinvoice.setQtytrans(o.getQtyTrans());
					purchaseinvoice.setImdatetheory(o.getImpdate());
				}
			}

			// 延期原因
			// bug11687 判断reasonremark字段的长度不要过长，不重复写入
			if (o.getReasonremark() != null) {
				if (purchaseinvoice.getReasonremark() == null) {
					purchaseinvoice.setReasonremark(o.getReasonremark());
					invoiceflag = 1;
				} else if (!purchaseinvoice.getReasonremark().contains(o.getReasonremark())) {
					purchaseinvoice.setReasonremark(purchaseinvoice.getReasonremark() + ";" + o.getReasonremark());
					invoiceflag = 1;
				}
			}
			// 供应商接单号更新
			if (StringUtils.isNotBlank(o.getReplyorderno())) {
				purchaseinvoice.setReplyorderno(o.getReplyorderno());
				purchaseorder.setReplyorderno(o.getReplyorderno());
				purchaseflag = 1;
				invoiceflag = 1;
			}
			// 接单日期更新 bugid:20901 20260518
			if (o.getReplyorderdate() != null){
                if(purchaseinvoice.getReplyorderdate() == null || o.getReplyorderdate().compareTo(purchaseinvoice.getReplyorderdate()) != 0){
                    purchaseinvoice.setReplyorderdate(o.getReplyorderdate());
                    invoiceflag = 1;
                }
			}
			// bug12769 采购返信提醒
			int reply = 0;
			Date replyBefore = null;
			int insertplan = 0;
			// 回复货期/变更货期
			// bug10179 马腾 判断是否相同使用compareTo
			// bug13893 【交付计划】如果为非日期返信，则清空返信字段值
			if (StringUtils.isNotBlank(o.getSrcdeliverytime())) {
				if (o.getReplyexportdate() == null) {
					o.setReplyexportdate(null);
					replyBefore = purchaseinvoice.getReplyexportdate();
					// bug20798 srcdeliverytime 不清空返信字段，而是直接使用新计算出来的值replyexportdate
					//purchaseinvoice.setReplyexportdate(null);
					purchaseorder.setDlvmoddate(null);
					purchaseflag = 1;
					reply = 1;
					info.setSrcdeliverytime(o.getSrcdeliverytime());
					info.setSrcdeliverytimedesc(o.getSrcdeliverytimedesc());
					orderflag = 1;
					insertplan = 1;
				}
				purchaseinvoice.setSrcDeliveryTime(o.getSrcdeliverytime());
				invoiceflag = 1;
			}
			// 回复货期/变更货期
			// bug10179 马腾 判断是否相同使用compareTo
			if (o.getReplyexportdate() != null || (o.getReplyexportdate() == null && StringUtils.isNotBlank(o.getSrcdeliverytime()))) {
				Date replyexportdate;
				if (o.getReplyexportdate() != null) {
					// bug11783马腾 设置货期格式，去除时分秒
					replyexportdate = new org.joda.time.LocalDate(DateUtils.truncate(o.getReplyexportdate(), Calendar.DATE)).toDate();
				} else {
					// bug 20798 C12961
					replyexportdate = convertSrcDeliveryTimeToDate(o.getSrcdeliverytime());
					o.setReplyexportdate(replyexportdate);
				}
				// 没回复货期，则直接更新回复货期字段
				if (purchaseinvoice.getReplyexportdate() == null) {
					purchaseinvoice.setReplyexportdate(replyexportdate);
					purchaseorder.setDlvmoddate(replyexportdate);
					info.setReplyexportdate(replyexportdate);
					// bug12769标志货期已变更
					reply = 1;
					insertplan = 1;
					invoiceflag = 1;
					purchaseflag = 1;
					orderflag = 1;
				} else if (purchaseinvoice.getReplyexportdate().compareTo(replyexportdate) != 0) {
					// 之前回复过货期，但是3次变更数据未满，则依次填充满
					if (purchaseinvoice.getDlvmoddate1() == null) {
						// bug12769记录返信变更前返信
						replyBefore = purchaseinvoice.getReplyexportdate();
						purchaseinvoice.setDlvmoddate1(purchaseinvoice.getReplyexportdate());
						purchaseinvoice.setReplyexportdate(replyexportdate);
						purchaseinvoice.setReasonremark(o.getReasonremark());
						purchaseinvoice.setDlvmoddate1time(new Date());
						purchaseorder.setDlvmoddate(replyexportdate);
						info.setReplyexportdate(replyexportdate);
					} else if (purchaseinvoice.getDlvmoddate2() == null) {
						// bug12769记录返信变更前返信
						replyBefore = purchaseinvoice.getReplyexportdate();
						purchaseinvoice.setDlvmoddate2(purchaseinvoice.getReplyexportdate());
						purchaseinvoice.setReplyexportdate(replyexportdate);
						purchaseinvoice.setReasonremark(purchaseinvoice.getReasonremark() + ";" + o.getReasonremark());
						purchaseinvoice.setDlvmoddate2time(new Date());
						purchaseorder.setDlvmoddate(replyexportdate);
						info.setReplyexportdate(replyexportdate);
					} else if (purchaseinvoice.getDlvmoddate3() == null) {
						// bug12769记录返信变更前返信
						replyBefore = purchaseinvoice.getReplyexportdate();
						purchaseinvoice.setDlvmoddate3(purchaseinvoice.getReplyexportdate());
						purchaseinvoice.setReplyexportdate(replyexportdate);
						purchaseinvoice.setReasonremark(purchaseinvoice.getReasonremark() + ";" + o.getReasonremark());
						purchaseinvoice.setDlvmoddate3time(new Date());
						purchaseorder.setDlvmoddate(replyexportdate);
						info.setReplyexportdate(replyexportdate);
					} else {
						// bug12769记录返信变更前返信
						replyBefore = purchaseinvoice.getReplyexportdate();
						// 若三次变更已满，则1保存2里的货期，2保存3里的货期，3保存上次的货期，reply里保存此次返回的货期
						purchaseinvoice.setDlvmoddate1(purchaseinvoice.getDlvmoddate2());
						purchaseinvoice.setDlvmoddate2(purchaseinvoice.getDlvmoddate3());
						purchaseinvoice.setDlvmoddate3(purchaseinvoice.getReplyexportdate());
						purchaseinvoice.setReplyexportdate(replyexportdate);
						purchaseinvoice.setReasonremark(purchaseinvoice.getReasonremark() + ";" + o.getReasonremark());
						purchaseinvoice.setDlvmoddate3time(new Date());
						purchaseorder.setDlvmoddate(replyexportdate);
						info.setReplyexportdate(replyexportdate);
					}
					purchaseflag = 1;
					orderflag = 1;
					// bug12769标志货期已变更
					reply = 1;
					insertplan = 1;
					invoiceflag = 1;
				}
			}
			// bug12769返信提醒事件
			if (reply == 1) {
				poReplyEvent(replyBefore, purchaseinvoice);
			}
			if (insertplan == 1) {
				OpsPoDeliveryPlan plan = new OpsPoDeliveryPlan();
				plan.setSourceId(o.getPlanid());
				plan.setOrderNo(purchaseinvoice.getOrderno());
				plan.setItemNo(purchaseinvoice.getItemno());
				plan.setSplitNo(purchaseinvoice.getSplititemno());
				plan.setLatestDeliveryTime(o.getReplyexportdate());
				plan.setSrcDeliveryTime(o.getSrcdeliverytime());
				plan.setTranstypeCode(o.getTranstype());
				plan.setDeliveryH(o.getDeliverytimeHPlan());
				plan.setDeliveryW(o.getDeliverytimeWPlan());
				if (o.getPlanqty() != null) {
					plan.setQuantity(o.getPlanqty());
					if (o.getPlanqty().compareTo(purchaseinvoice.getQuantity()) <= 0) {
						// 将之前的返信数量不满足的数据行最新返信字段设为否
						OpsPoDeliveryPlan plantemp = new OpsPoDeliveryPlan();
						plantemp.setNewest(false);
						plantemp.setUpdateTime(new Date());
						OpsPoDeliveryPlanExample planex = new OpsPoDeliveryPlanExample();
						if (purchaseinvoice.getSplititemno() == null)
							planex.createCriteria().andOrderNoEqualTo(purchaseinvoice.getOrderno())
									.andItemNoEqualTo(purchaseinvoice.getItemno()).andSplitNoIsNull()
									.andQuantityGreaterThan(purchaseinvoice.getQuantity() - o.getPlanqty());
						else
							planex.createCriteria().andOrderNoEqualTo(purchaseinvoice.getOrderno())
									.andItemNoEqualTo(purchaseinvoice.getItemno())
									.andSplitNoEqualTo(purchaseinvoice.getSplititemno())
									.andQuantityGreaterThan(purchaseinvoice.getQuantity() - o.getPlanqty());
						opsPoDeliveryPlanMapper.updateByExampleSelective(plantemp, planex);
					}
				} else {
					// bug14526 plan表若没传数量则为全部数量
					plan.setQuantity(purchaseinvoice.getQuantity());
				}
				opsPoDeliveryPlanMapper.insertSelective(plan);
			}

            // 没传Facexpdate，用A的值代替这个
            if (o.getFacexpdate() == null) {
                o.setFacexpdate(o.getDeliverytimeAFact());
            }

			// bug9415 马腾 增加供应商实际出库时间
			if (o.getFacexpdate() != null && (purchaseinvoice.getFacexpdate() == null
					|| purchaseinvoice.getFacexpdate().compareTo(o.getFacexpdate()) != 0)) {
				purchaseinvoice.setFacexpdate(o.getFacexpdate());
				invoiceflag = 1;
			}

			// 变更运输方式
			// bug14293增加实际运输方式字段
			if (StringUtils.isNotBlank(o.getTranstypeFact())) {
				if (!StringUtils.equals(purchaseinvoice.getTranstype(), o.getTranstypeFact())
						&& !StringUtils.equals(purchaseinvoice.getTranstypemod(), o.getTranstypeFact())) {
					purchaseinvoice.setTranstypemod(o.getTranstypeFact());
					purchaseinvoice.setTranstypeFact(o.getTranstypeFact());
					invoiceflag = 1;
					// bugid：17385
					eventFlag = true;
					info.setTranstype(o.getTranstypeFact());
				}
			} else {
				if (StringUtils.isNotBlank(o.getTranstype())){
					if (!StringUtils.equals(purchaseinvoice.getTranstype(), o.getTranstype())
							&& !StringUtils.equals(purchaseinvoice.getTranstypemod(), o.getTranstype())) {
						purchaseinvoice.setTranstypemod(o.getTranstype());
						purchaseinvoice.setTranstypeFact(o.getTranstype());
						invoiceflag = 1;
						// bugid：17385
						eventFlag = true;
						info.setTranstype(o.getTranstype());
					}
				}
			}

			// 如果purchaseinvoice表中实际运输方式字段为空，则赋值为transtype字段值
			if (StringUtils.isBlank(purchaseinvoice.getTranstypeFact())) {
				purchaseinvoice.setTranstypeFact(purchaseinvoice.getTranstype());
				invoiceflag = 1;
			}

			// 货期回复号
			if (o.getDlvanswerno() != null) {
				if (purchaseinvoice.getDlvanswerno1() == null) {
					purchaseinvoice.setDlvanswerno1(o.getDlvanswerno());
					invoiceflag = 1;
				} else if (purchaseinvoice.getDlvanswerno2() == null) {
					purchaseinvoice.setDlvanswerno2(o.getDlvanswerno());
					invoiceflag = 1;
				} else {
					// logger.error(o.getPono() + o.getLineitem() +
					// "日本货期回复号已超，此次回复号为：" +
					// o.getDlvanswerno());
				}
			}
			// 生产工厂
			if (o.getProducefactory() != null) {
				purchaseinvoice.setProducefactory(o.getProducefactory());
				invoiceflag = 1;
				eventFlag = true;
			}
			// 生产HOLON
			if (o.getProduceholon() != null) {
				purchaseinvoice.setProduceholon(o.getProduceholon());
				invoiceflag = 1;
			}
			// 到港时间
			if (o.getPortarrivedate() != null) {
				if (purchaseinvoice.getPortArrivedate() == null) {
					purchaseinvoice.setPortArrivedate(o.getPortarrivedate());
					purchaseorder.setPortArrivedate(o.getPortarrivedate());
					info.setPortarrivedate(o.getPortarrivedate());
					purchaseflag = 1;
					orderflag = 1;
					invoiceflag = 1;
				} else if (purchaseinvoice.getPortArrivedate() != null
						&& purchaseinvoice.getPortArrivedate() != o.getPortarrivedate()) {
					purchaseinvoice.setPortArrivedate(o.getPortarrivedate());
					purchaseorder.setPortArrivedate(o.getPortarrivedate());
					info.setPortarrivedate(o.getPortarrivedate());
					purchaseflag = 1;
					orderflag = 1;
					invoiceflag = 1;
				}
			}

			// 报关时间
			if (o.getCustomsdate() != null) {
				if (purchaseinvoice.getCustomsDate() == null) {
					purchaseinvoice.setCustomsDate(o.getCustomsdate());
					purchaseorder.setCustomsDate(o.getCustomsdate());
					info.setCustomsdate(o.getCustomsdate());
					purchaseflag = 1;
					orderflag = 1;
					invoiceflag = 1;
				} else if (purchaseinvoice.getCustomsDate() != null
						&& purchaseinvoice.getCustomsDate() != o.getCustomsdate()) {
					purchaseinvoice.setCustomsDate(o.getCustomsdate());
					purchaseorder.setCustomsDate(o.getCustomsdate());
					info.setCustomsdate(o.getCustomsdate());
					purchaseflag = 1;
					orderflag = 1;
					invoiceflag = 1;
				}
			}

			// 开始生产日
			if (o.getBeginproducedate() != null) {
				if (purchaseinvoice.getBeginproducedate() == null) {
					purchaseinvoice.setBeginproducedate(o.getBeginproducedate());
					purchaseorder.setBeginProduceDate(o.getBeginproducedate());
					info.setBeginproducedate(o.getBeginproducedate());
					purchaseflag = 1;
					orderflag = 1;
					invoiceflag = 1;
				} else if (purchaseinvoice.getBeginproducedate() != null
						&& purchaseinvoice.getBeginproducedate() != o.getBeginproducedate()) {
					purchaseinvoice.setBeginproducedate(o.getBeginproducedate());
					purchaseorder.setBeginProduceDate(o.getBeginproducedate());
					info.setBeginproducedate(o.getBeginproducedate());
					purchaseflag = 1;
					orderflag = 1;
					invoiceflag = 1;
				}
			}

			// 采购价格
			if (o.getPoprice() != null) {
				if (purchaseinvoice.getPoPrice() == null) {
					purchaseinvoice.setPoPrice(o.getPoprice());
					purchaseorder.setPoPrice(o.getPoprice());
					purchaseflag = 1;
					invoiceflag = 1;
				} else if (purchaseinvoice.getPoPrice() != null && purchaseinvoice.getPoPrice() != o.getPoprice()) {
					purchaseinvoice.setPoPrice(o.getPoprice());
					purchaseorder.setPoPrice(o.getPoprice());
					purchaseflag = 1;
					invoiceflag = 1;
				}
			}
			//bugid:15666 WTSR2024000564 更新
			if (Objects.nonNull(o.getReceiveDate()) && (Objects.isNull(purchaseinvoice.getImdatetheoryafter())
					|| o.getReceiveDate().compareTo(purchaseinvoice.getImdatetheoryafter()) != 0)){
				purchaseinvoice.setImdatetheoryafter(o.getReceiveDate());
				invoiceflag = 1;
			}
            // bugid:19565 c14717 20251222
            if(StringUtils.isNotBlank(o.getSendFilePath())){
                purchaseinvoice.setFilepath(o.getSendFilePath());
                invoiceflag = 1;
            }

			// 更新
			if (invoiceflag == 1) {
				purchaseinvoice.setUpdatetime(new Date());
				// 增加更新三次重试，避免被牺牲
				int trynum = 3;
				boolean updateresult = false;
				while (!updateresult && trynum > 0) {
					try {
						//bugid:16474 c14717 20250311 更新factExpdate问题
						//opsPurchaseinvoiceMapper.updateByPrimaryKey(purchaseinvoice);
						opsPurchaseinvoiceMapper.updateByPrimaryKeySelective(purchaseinvoice);
						updateresult = true;
					} catch (Exception e) {
						trynum--;
						if (trynum == 0 && !updateresult) {
							logger.error("更新purchaseinvoice三次尝试均失败。", e);
							throw e;
						}
					}
				}
			}

			if (purchaseflag == 1) {
				purchaseorder.setUpdatetime(new Date());
				opsPurchaseorderMapper.updateByExampleSelective(purchaseorder, ex);
			}
			// 回更订单信息
			if (orderflag == 1) {
				// bug 11945马腾 将变更订单操作放到最后进行，避免数据不回滚
				// updateOrderList.add(info);
				// 将调用feign修改为调用service方法
				updateForPo(info);
				wmDispatchService.updatePurchaseOrder(info);
			}
			if (o.getDeliverytimeHFact() != null || o.getDeliverytimeAFact() != null || o.getDeliverytimeWFact() != null) {
				eventFlag = true;
			}
			//事件变更 bugid：17385 C14717 20250410 增加变更运输方式时调用事件，运输方式写入info
			if(orderflag == 1 || eventFlag){
				// 变更运输方式后，发送采购变更事件
				purchaseEventPublisher.purchaseOrderEventByNo(EventSourceEnum.PURCHASE_ORDER_UPDATE, info.getOrderno(),
						info.getItemno(), info.getSplitno(), info);
			}
		}

		// 在途
		Map<String, Boolean> log = new HashMap<String, Boolean>();
		List<OpsPurchaseinvoice> trans = new ArrayList<OpsPurchaseinvoice>();
		if (transList.size() > 0) {
			for (OpsPurchaseinvoice i : transList) {
				if (i.getJpsplitvt() == 0) {
					if (i.getModelno().contains("*") || i.getModelno().startsWith("3C")) {
						OpsPurchaseorderExample example = new OpsPurchaseorderExample();
						if (i.getSplititemno() != null)
							example.createCriteria().andOrdernoEqualTo(i.getOrderno()).andItemnoEqualTo(i.getItemno())
									.andSplititemnoEqualTo(i.getSplititemno());
						else
							example.createCriteria().andOrdernoEqualTo(i.getOrderno()).andItemnoEqualTo(i.getItemno())
									.andSplititemnoIsNull();
						List<OpsPurchaseorder> opo = opsPurchaseorderMapper.selectByExample(example);
						if (opo != null && opo.size() > 0) {
							i.setModelno(opo.get(0).getModelno());
						}
					}
					trans.add(i);
				} else {
					// 日本不接收整型号需合并型号
					if (i.getLineitem() == 1) {
						if (!log.containsKey(i.getPono() + i.getLineitem())) {
							OpsPurchaseorderExample example = new OpsPurchaseorderExample();
							if (i.getSplititemno() != null)
								example.createCriteria().andOrdernoEqualTo(i.getOrderno())
										.andItemnoEqualTo(i.getItemno()).andSplititemnoEqualTo(i.getSplititemno());
							else
								example.createCriteria().andOrdernoEqualTo(i.getOrderno())
										.andItemnoEqualTo(i.getItemno()).andSplititemnoIsNull();
							List<OpsPurchaseorder> opo = opsPurchaseorderMapper.selectByExample(example);
							if (opo != null && opo.size() > 0) {
								i.setModelno(opo.get(0).getModelno());
								i.setQtytrans(i.getQuantity() / opo.get(0).getQuantity() * i.getQtytrans());
								trans.add(i);
								log.put(i.getPono() + i.getLineitem(), true);
							}
						}
					}
				}
			}
			// 调用接口在途库存
			// ((Logger) log).info("InvoniceForPoInit params = {}",
			// JSON.toJSONString(list));
			invoniceForPoInit(trans);
			wmDispatchService.invoiceForPoInit(trans);
			for (OpsPurchaseinvoice i : transList) {
				// 预到货后，发送采购更新事件
				purchaseEventPublisher.purchaseOrderEvent(EventSourceEnum.PURCHASE_INVOICE_IMPORT, i);
			}
		}

		// bug10886 更新move数据
		Map<String, Boolean> logpre = new HashMap<String, Boolean>();
		List<OpsPurchaseinvoice> pre = new ArrayList<OpsPurchaseinvoice>();
		if (preImpDateList.size() > 0) {
			for (OpsPurchaseinvoice i : preImpDateList) {
				if (i.getJpsplitvt() == 0) {
					if (i.getModelno().contains("*") || i.getModelno().startsWith("3C")) {
						OpsPurchaseorderExample example = new OpsPurchaseorderExample();
						if (i.getSplititemno() != null)
							example.createCriteria().andOrdernoEqualTo(i.getOrderno()).andItemnoEqualTo(i.getItemno())
									.andSplititemnoEqualTo(i.getSplititemno());
						else
							example.createCriteria().andOrdernoEqualTo(i.getOrderno()).andItemnoEqualTo(i.getItemno())
									.andSplititemnoIsNull();
						List<OpsPurchaseorder> opo = opsPurchaseorderMapper.selectByExample(example);
						if (opo != null && opo.size() > 0) {
							i.setModelno(opo.get(0).getModelno());
						}
					}
					pre.add(i);
				} else {
					// 日本不接收整型号需合并型号
					if (i.getLineitem() == 1) {
						if (!logpre.containsKey(i.getPono() + i.getLineitem())) {
							OpsPurchaseorderExample example = new OpsPurchaseorderExample();
							if (i.getSplititemno() != null)
								example.createCriteria().andOrdernoEqualTo(i.getOrderno())
										.andItemnoEqualTo(i.getItemno()).andSplititemnoEqualTo(i.getSplititemno());
							else
								example.createCriteria().andOrdernoEqualTo(i.getOrderno())
										.andItemnoEqualTo(i.getItemno()).andSplititemnoIsNull();
							List<OpsPurchaseorder> opo = opsPurchaseorderMapper.selectByExample(example);
							if (opo != null && opo.size() > 0) {
								i.setModelno(opo.get(0).getModelno());
								i.setQtytrans(i.getQuantity() / opo.get(0).getQuantity() * i.getQtytrans());
								pre.add(i);
								logpre.put(i.getPono() + i.getLineitem(), true);
							}
						}
					}
				}
			}
			invoiceForPoUpdateLog(pre);
			wmDispatchService.invoiceForPoUpdate(pre);
			for (OpsPurchaseinvoice i : preImpDateList) {
				// 修改预到货日期后，发送采购更新事件
				purchaseEventPublisher.purchaseOrderEventByNo(EventSourceEnum.PURCHASE_ORDER_UPDATE, i.getOrderno(),
						i.getItemno(), i.getSplititemno(), null);
			}
		}
		// bug 11945马腾 将变更订单操作放到最后进行，避免数据不回滚
		// if (!CollectionUtils.isEmpty(updateOrderList)) {
		// opsWmDispatchForOrderFeignApi.updateForPo(updateOrderList);
		// }
		return errorInfoMap;
	}

	public boolean acceptPurchaseOperate(OpsPurchaseorder i, Map<String, String> errorInfoMap) {
		PoToWmDto t = new PoToWmDto();
		t.setPurchase(i);
		// bug13662 请购表新增三个采购单号字段
		OpsRequestpurchaseExample ex = new OpsRequestpurchaseExample();
		if (i.getSplititemno() != null)
			ex.createCriteria().andPoOrderNoEqualTo(i.getOrderno()).andPoItemNoEqualTo(i.getItemno())
					.andPoSplitNoEqualTo(i.getSplititemno());
		else
			ex.createCriteria().andPoOrderNoEqualTo(i.getOrderno()).andPoItemNoEqualTo(i.getItemno())
					.andPoSplitNoIsNull();
		List<OpsRequestpurchase> te = opsRequestpurchaseMapper.selectByExample(ex);
		if (te != null && te.size() > 0) {
			t.setRequests(te);
		} else {
			OpsRequestpurchaseCancelLogExample exa = new OpsRequestpurchaseCancelLogExample();
			if (i.getSplititemno() == null) {
				exa.createCriteria().andPoOrderNoEqualTo(i.getOrderno()).andPoItemNoEqualTo(i.getItemno())
						.andPoSplitNoIsNull();
			} else {
				exa.createCriteria().andPoOrderNoEqualTo(i.getOrderno()).andPoItemNoEqualTo(i.getItemno())
						.andPoSplitNoEqualTo(i.getSplititemno());
			}
			List<OpsRequestpurchaseCancelLog> tempC = opsRequestpurchaseCancelLogMapper.selectByExample(exa);
			if (tempC != null && tempC.size() > 0) {
				te = new ArrayList<OpsRequestpurchase>();
				for (OpsRequestpurchaseCancelLog tc : tempC) {
					// OpsRequestpurchaseCancelLog tc = tempC.get(0);
					OpsRequestpurchase re = new OpsRequestpurchase();
					re.setOrderno(tc.getOrderno());
					re.setItemno(tc.getItemno());
					if (tc.getSplititemno() != null) {
						re.setSplititemno(tc.getSplititemno());
					}
					re.setModelno(tc.getModelno());
					re.setOrdtype(tc.getOrdtype());
					re.setQuantity(tc.getQuantity());
					re.setInventorytypecode(tc.getInventorytypecode());
					re.setGroupcustomerno(tc.getGroupcustomerno());
					re.setCustomerno(tc.getCustomerno());
					re.setPpl(tc.getPpl());
					re.setProjectcode(tc.getProjectcode());
					re.setRequestwarehouseid(tc.getRequestwarehouseid());
					re.setWmtag(tc.getWmtag());
					te.add(re);
				}
				t.setRequests(te);
			}
		}
		// bug15340 若没有对应的请购单则不进行接单操作
		if (!CollectionUtils.isEmpty(t.getRequests())) {
			// bug15340若单个接单报错，则不抛出异常，直接执行下一单的接单操作
			OpsPurchaseorder purchase = t.getPurchase();
			OrderNoInfo orderNoInfo = OrderNoInfo.getFromPurchase(purchase);
			// bug16252 采购单加锁
			opsRedissonLock.addLock(keyPrefix + orderNoInfo.getFullNo());
			try {
				//查询采购状态
				purchase = basePoService.getPurchase(purchase.getOrderno(), purchase.getItemno(), purchase.getSplititemno());
				if (purchase != null) {
					String statecode = purchase.getStatecode();
					if (StringUtils.isNotBlank(statecode)) {
						//采购状态必须为待处理或已发送
						List<String> list = Arrays.asList(PurchaseOrderStatusEnum.DAICHULI, PurchaseOrderStatusEnum.YIFASONG);
						if (!list.contains(statecode)) {
							logger.error("采购接单状态校验失败：{}", PoNoUtil.getFullPoNo(purchase));
							return false;
						}
					}
				}
				acceptForPo(t);
				// bug16252 采购单处理move，开启新事务
				poDispatcherService.acceptForPo(t);
				// 增加订单状态总线事件
				purchaseEventPublisher.purchaseOrderEvent(EventSourceEnum.PURCHASE_ORDER_RECEIVE, i, i);
				return true;
			} catch (Exception e) {
				// TODO 后续补充异常记录及发邮件
				String errorInfo = "采购接单异常:" + OrderNoInfo.getFromPurchase(i).getFullNo() + e.getMessage() + Arrays.toString(e.getStackTrace());
				errorInfoMap.put(OrderNoInfo.getFromPurchase(i).getFullNo(), errorInfo);
				logger.error("wm-updateinvoice-接单异常:" + t.getPurchase() != null ? t.getPurchase().getOrderno() : "", e);
			} finally {
				// bug16252 采购单解锁
				opsRedissonLock.releaseLock(keyPrefix + orderNoInfo.getFullNo());
			}
		} else {
			// TODO 后续补充异常记录及发邮件
			String errorInfo = "采购单无对应请购单，不进行接单操作:" + OrderNoInfo.getFromPurchase(i).getFullNo();
			errorInfoMap.put(OrderNoInfo.getFromPurchase(i).getFullNo(), errorInfo);
			logger.error("采购单无对应请购单，不进行接单操作:" + i.getOrderno() + "-" + i.getItemno().toString()
					+ (i.getSplititemno() == null ? "" : ("-" + i.getSplititemno().toString())));
		}
		return false;
	}

	// bug12769 采购单返信提醒事件
	public void poReplyEvent(Date replyDateBefore, OpsPurchaseinvoice item) {
		PoReplyInfoDto dto = new PoReplyInfoDto();
		dto.setPoOrderNo(item.getOrderno());
		dto.setPoItemNo(item.getItemno());
		if (item.getSplititemno() != null)
			dto.setPoSplitItemNo(item.getSplititemno());
		OrderNoInfo orderNoInfo = new OrderNoInfo(item.getOrderno(), item.getItemno(), item.getSplititemno());
		String fullNo = orderNoInfo.getFullNo();
		dto.setPoOrderFNo(fullNo);

		dto.setModelNo(item.getModelno());
		dto.setQuantity(item.getQuantity());
		dto.setSupplierOrderNo(item.getReplyorderno());
		dto.setSupplierId(item.getSupplierid());
		dto.setHopeExportDate(item.getHopeexportdate());

		// 兼容66/66/66等数据，修改为string类型，调用解析方法
		if (item.getReplyexportdate() == null) {
			dto.setReplyDateMod(item.getSrcDeliveryTime());
		} else {
			dto.setReplyDateMod(PoReplyDateStrEnum.getDescByCode(item.getReplyexportdate()).getCode());
		}
		if (replyDateBefore != null) {
			dto.setReplyDateModBefore(PoReplyDateStrEnum.getDescByCode(replyDateBefore).getCode());
		}

		dto.setCustomerNo(item.getCustomerno());
		dto.setUserNo(item.getUserno());
		//dto.setEndUser();
		//dto.setDeptNoCustomer();
		//dto.setLeader();
		if (StringUtils.isNotBlank(item.getOrdtype())) {
			dto.setOrderType(item.getOrdtype());
			String orderTypeName = com.smc.smccloud.core.model.enums.OrderTypeEnum.getCodeName(dto.getOrderType().toString());
			dto.setOrderTypeName(orderTypeName);
		}
		dto.setModifyTime(new Date());
		dto.setEndUser(item.getEndUser()); // bug19576 采购表追加end_user字段
		poReply(dto);
		if(!Objects.equals(replyDateBefore, item.getReplyexportdate())) {
			purchaseEventPublisher.purchaseOrderEvent(EventSourceEnum.PURCHASE_ORDER_REPLY_DATE, dto);
		}
	}

	// bug12769 采购返信提醒事件日志
	public void poReply(PoReplyInfoDto poInfo) {
		logger.info("purchaseReplyDate params = {}", JSON.toJSONString(poInfo));
		ImpInvoiceEventLog log = new ImpInvoiceEventLog();
		log.setOpType("poReplyEnvent");
		log.setRequestParam(JSON.toJSONString(poInfo));
		wmDispatchService.addImpInvoiceEventLog(log);
	}

	public void updateForPo(PurchaseUpdateInfo poInfo) {
		logger.info("updateForPo params = {}", JSON.toJSONString(poInfo));
		ImpInvoiceEventLog log = new ImpInvoiceEventLog();
		log.setOpType("/order/po/update");
		log.setRequestParam(JSON.toJSONString(poInfo));
		wmDispatchService.addImpInvoiceEventLog(log);
	}

	public boolean updateLog(List<PurchaseReplyInfo> list) throws Exception {
		logger.info("updateInvoice params = {}", JSON.toJSONString(list));
		ImpInvoiceEventLog log = new ImpInvoiceEventLog();
		log.setOpType("/purchase/updateInvoice");
		log.setRequestParam(JSON.toJSONString(list));
		wmDispatchService.addImpInvoiceEventLog(log);
		return true;
	}

	public boolean acceptForPo(PoToWmDto dto) throws Exception {
		logger.info("/order/po/accept params:{}", JSONUtil.toJsonPrettyStr(dto));
		ImpInvoiceEventLog invoiceLog = LogUtil.createInvoiceLog("/order/po/accept", dto);
		wmDispatchService.addImpInvoiceEventLog(invoiceLog);
		return true;
	}

	public boolean invoniceForPoInit(List<OpsPurchaseinvoice> list) throws Exception {
		logger.info("InvoniceForPoInit params = {}", JSON.toJSONString(list));
		ImpInvoiceEventLog log = new ImpInvoiceEventLog();
		log.setOpType("/order/import/init/Invoice");
		log.setRequestParam(JSON.toJSONString(list));
		wmDispatchService.addImpInvoiceEventLog(log);
		return true;
	}

	public void invoiceForPoUpdateLog(List<OpsPurchaseinvoice> list) {
		logger.info("InvoniceForPoUpdate params = {}", JSON.toJSONString(list));
		ImpInvoiceEventLog log = new ImpInvoiceEventLog();
		log.setOpType("/order/import/update/Invoice");
		log.setRequestParam(JSON.toJSONString(list));
		wmDispatchService.addImpInvoiceEventLog(log);
	}

	// bug8614 马腾 状态为3的发票子项新增采购单作为对应
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer impdata(String invoiceNo, long invoiceId) throws Exception {
		logger.info("impdata params :invoiceno=" + invoiceNo + ";invoiceid=" + invoiceId);
		Integer result = null;
		// 补充前序步骤
		List<PurchaseReplyInfo> prelist = new ArrayList<PurchaseReplyInfo>();
		// PurchaseReplyInfo pre = null;
		// 到货库存
		List<OpsPurchaseinvoice> receiveList = new ArrayList<OpsPurchaseinvoice>();
		// 入库
		List<OpsImpdata> impInsertList = new ArrayList<OpsImpdata>();
		// 获取发票数据
		List<Short> statuslist = new ArrayList<Short>();
		statuslist.add((short) 2);
		statuslist.add((short) 3);
		ImpInvoiceDetailPackExample ex = new ImpInvoiceDetailPackExample();
		ex.createCriteria().andInvoiceIdEqualTo(invoiceId).andInvoiceNoEqualTo(invoiceNo).andStatusIn(statuslist);
		List<ImpInvoiceDetailPack> list = impInvoiceDetailPackMapper.selectByExample(ex);
		if (list == null || list.size() == 0) {
			return result;
		}
		ImpInvoiceMaster master = impInvoiceMasterMapper.selectByPrimaryKey(invoiceId);
		if (master == null) {
			return result;
		} else {
			if (StringUtils.isBlank(master.getReceiveWarehouseCode())) {
				return result;
			}
		}
		result = 0;
		// bug14563 补充fact表
		Map<String, Integer> sumqtymap = new HashMap<String, Integer>();
		Map<String, String> modelNoMap = new HashMap<String, String>();
		// 若补充前序步骤，汇总到货数量map
		Map<String, Integer> preqtymap = new HashMap<String, Integer>();
		List<OpsPurchaseinvoice> invoiceUpdate = new ArrayList<OpsPurchaseinvoice>();
		for (ImpInvoiceDetailPack o : list) {
			OpsPurchaseinvoice purchaseinvoice = new OpsPurchaseinvoice();
			OpsPurchaseinvoiceExample opsPurchaseinvoiceExample = new OpsPurchaseinvoiceExample();
			// opsPurchaseinvoiceExample.createCriteria().andPonoEqualTo(o.getPono()).andLineitemEqualTo(o.getPoitemno());
			// 统一修改为使用订单号、子项号、拆分单号进行查询
			// bug15736 增加拆分单号为0的判断
			if (o.getSplitItemNo() == null || o.getSplitItemNo() == 0) {
				opsPurchaseinvoiceExample.createCriteria().andOrdernoEqualTo(o.getOrderNo())
						.andItemnoEqualTo(o.getItemNo()).andSplititemnoIsNull();
			} else {
				opsPurchaseinvoiceExample.createCriteria().andOrdernoEqualTo(o.getOrderNo())
						.andItemnoEqualTo(o.getItemNo()).andSplititemnoEqualTo(o.getSplitItemNo());
			}
			// bug8614判断状态，补充为新采购单
			if (o.getStatus() == 3) {
				purchaseinvoice = add(o, master.getReceiveWarehouseCode());
				o.setPono(purchaseinvoice.getPono());
				o.setPoitemno(purchaseinvoice.getLineitem());
				opsPurchaseinvoiceExample = new OpsPurchaseinvoiceExample();
				opsPurchaseinvoiceExample.createCriteria().andPonoEqualTo(o.getPono())
						.andLineitemEqualTo(o.getPoitemno());
			} else {
				List<OpsPurchaseinvoice> l = opsPurchaseinvoiceMapper.selectByExample(opsPurchaseinvoiceExample);
				if (l != null && l.size() > 0) {
					purchaseinvoice = l.get(0);
				} else {
					purchaseinvoice = new OpsPurchaseinvoice();
					purchaseinvoice.setPono(o.getPono());
					purchaseinvoice.setLineitem(o.getPoitemno());
					purchaseinvoice.setOrderno(o.getOrderNo());
					purchaseinvoice.setItemno(o.getItemNo());
					// bug15736 增加拆分单号为0的判断
					if (o.getSplitItemNo() != null && o.getSplitItemNo() != 0) {
						purchaseinvoice.setSplititemno(o.getSplitItemNo());
					}
					purchaseinvoice.setModelno(o.getModelNo());
					purchaseinvoice.setSupplierid(o.getSupplierCode());
					purchaseinvoice.setGreencode(o.getRohsCode());
					// 读取删单数据log
					OpsPurchaseorderCancelLogExample e = new OpsPurchaseorderCancelLogExample();
					if (purchaseinvoice.getSplititemno() == null) {
						e.createCriteria().andOrdernoEqualTo(purchaseinvoice.getOrderno())
								.andItemnoEqualTo(purchaseinvoice.getItemno()).andSplititemnoIsNull();
					} else {
						e.createCriteria().andOrdernoEqualTo(purchaseinvoice.getOrderno())
								.andItemnoEqualTo(purchaseinvoice.getItemno())
								.andSplititemnoEqualTo(purchaseinvoice.getSplititemno());
					}

					List<OpsPurchaseorderCancelLog> temp = opsPurchaseorderCancelLogMapper.selectByExample(e);
					if (temp == null || temp.size() == 0) {
						// 更新状态
						// ImpInvoiceDetailPack pack = new
						// ImpInvoiceDetailPack();
						// pack.setId(o.getId());
						// pack.setStatus((short) 4);
						// pack.setExpMsg("无采购数据");
						// impInvoiceDetailPackMapper.updateByPrimaryKeySelective(pack);
						// continue;
						throw new Exception("发票号：" + invoiceNo + "中，采购单：" + o.getPono() + "无采购数据");
					} else {
						// purchaseinvoice.setSupplierid(temp.get(0).getSupplierid());
						// purchaseinvoice.setGreencode(temp.get(0).getGreencode());
						if (temp.get(0).getModelno().equals(o.getModelNo())) {
							purchaseinvoice.setJpsplitvt(0);
						} else {
							purchaseinvoice.setModelno(temp.get(0).getModelno());
							purchaseinvoice.setJpsplitvt(2);
						}
					}
				}
			}
			// 汇总所有子项
			if (!sumqtymap.containsKey(purchaseinvoice.getOrderno() + "-" + purchaseinvoice.getItemno().toString()
					+ (purchaseinvoice.getSplititemno() == null ? ""
							: ("-" + purchaseinvoice.getSplititemno().toString())))) {
				Integer sum = 0;
				for (ImpInvoiceDetailPack s : list) {
					if (StringUtils.equals(s.getOrderNo(), purchaseinvoice.getOrderno()) && s.getItemNo() != null
							&& s.getItemNo().compareTo(purchaseinvoice.getItemno()) == 0)
						if ((s.getSplitItemNo() == null && purchaseinvoice.getSplititemno() == null)
								|| (s.getSplitItemNo() != null && purchaseinvoice.getSplititemno() != null
										&& s.getSplitItemNo().compareTo(purchaseinvoice.getSplititemno()) == 0))
							sum = s.getQuantity() + sum;
				}
				//收集型号
				sumqtymap.put(purchaseinvoice.getOrderno() + "-" + purchaseinvoice.getItemno().toString()
						+ (purchaseinvoice.getSplititemno() == null ? ""
								: ("-" + purchaseinvoice.getSplititemno().toString())),
						sum);
				modelNoMap.put(purchaseinvoice.getOrderno() + "-" + purchaseinvoice.getItemno().toString()
								+ (purchaseinvoice.getSplititemno() == null ? ""
								: ("-" + purchaseinvoice.getSplititemno().toString())),
						purchaseinvoice.getModelno());
			}

			// 补充前序状态
			// 查询之前此发票是否已预到货
			OpsPurchaseinvoicetranslogExample logex = new OpsPurchaseinvoicetranslogExample();
			logex.createCriteria().andPonoEqualTo(purchaseinvoice.getPono())
					.andLineitemEqualTo(purchaseinvoice.getLineitem()).andInvoiceidEqualTo(o.getInvoiceId())
					.andInvoicenoEqualTo(o.getInvoiceNo());
			List<OpsPurchaseinvoicetranslog> loglist = opsPurchaseinvoicetranslogMapper.selectByExample(logex);
			if (loglist == null || loglist.size() == 0) {
				PurchaseReplyInfo pre = new PurchaseReplyInfo();
				pre.setPono(purchaseinvoice.getPono());
				pre.setLineitem(purchaseinvoice.getLineitem());
				pre.setOrderno(purchaseinvoice.getOrderno());
				pre.setItemno(purchaseinvoice.getItemno());
				if (purchaseinvoice.getSplititemno() != null) {
					pre.setSplitno(purchaseinvoice.getSplititemno());
				}
				if (purchaseinvoice.getReplyorderdate() == null) {
					pre.setReplyorderdate(new Date());
				}
				if (purchaseinvoice.getReplyorderno() == null) {
					pre.setReplyorderno("system");
				}
				pre.setInvoiceno(o.getInvoiceNo());
				pre.setInvoiceid(o.getInvoiceId());
				// bug15494 补充接单预到货事件时不重复操作
				if (!preqtymap.containsKey(purchaseinvoice.getPono() + "-" + purchaseinvoice.getLineitem())) {
					Integer sum = 0;
					for (ImpInvoiceDetailPack s : list) {
						if (StringUtils.equals(s.getPono(), pre.getPono()))
							if (s.getPoitemno().equals(pre.getLineitem()))
								sum = s.getQuantity() + sum;
					}
					preqtymap.put(pre.getPono() + "-" + pre.getLineitem(), sum);
					pre.setQtyTrans(sum);

					// bug13550 2024-3-28 业务可以修改发票主表供应商，按主表供应商为准
					pre.setSupplierid(master.getSupplierCode());
					pre.setModelno(o.getModelNo());
					pre.setWarehouseid(master.getReceiveWarehouseCode());
					// bug10426 马腾 补充预到货日期字段
					if (master.getPrearriveDate() != null) {
						pre.setImpdate(master.getPrearriveDate());
					}
					// 供应商实际发运日期
					pre.setFacexpdate(master.getShipDate());
					pre.setDeliverytimeAFact(master.getShipDate());
					prelist.add(pre);
				}

			}

			// 到货
			if (o.getQuantity() != null && o.getBarcode() != null) {
				OpsImpdataExample exam = new OpsImpdataExample();
				if (o.getCaseNo() != null) {
					exam.createCriteria().andInvoiceidEqualTo(o.getInvoiceId()).andInvoicenoEqualTo(o.getInvoiceNo())
							.andPonoEqualTo(o.getPono()).andLineitemEqualTo(o.getPoitemno())
							.andBarcodeEqualTo(o.getBarcode()).andCasenoEqualTo(o.getCaseNo());
				} else {
					exam.createCriteria().andInvoiceidEqualTo(o.getInvoiceId()).andInvoicenoEqualTo(o.getInvoiceNo())
							.andPonoEqualTo(o.getPono()).andLineitemEqualTo(o.getPoitemno())
							.andBarcodeEqualTo(o.getBarcode());
				}
				List<OpsImpdata> im = opsImpdataMapper.selectByExample(exam);
				if (im == null || im.size() == 0) {
					if (purchaseinvoice.getQtyreceive() == null) {
						purchaseinvoice.setQtyreceive(o.getQuantity());
					} else {
						purchaseinvoice.setQtyreceive(purchaseinvoice.getQtyreceive() + o.getQuantity());
					}
					OpsImpdata imp = new OpsImpdata();
					imp.setInvoiceid(o.getInvoiceId());
					imp.setInvoiceno(o.getInvoiceNo());
					imp.setPono(purchaseinvoice.getPono());
					imp.setLineitem(purchaseinvoice.getLineitem());
					imp.setOrderno(purchaseinvoice.getOrderno());
					imp.setItemno(purchaseinvoice.getItemno());
					if (purchaseinvoice.getSplititemno() != null)
						imp.setSplititemno(purchaseinvoice.getSplititemno());
					imp.setModelno(purchaseinvoice.getModelno());
					imp.setSupplierid(purchaseinvoice.getSupplierid());
					imp.setGreencode(purchaseinvoice.getGreencode());
					imp.setReceivewarehouseid(master.getReceiveWarehouseCode());
					imp.setQuantity(o.getQuantity());
					imp.setSupplierexpdate(o.getShipDate());
					imp.setStatecode("0");
					imp.setInserttime(new Date());
					imp.setUpdatetime(new Date());
					imp.setBarcode(o.getBarcode());
					imp.setOperate(purchaseinvoice.getAdd3c());
					if (purchaseinvoice.getJpsplitvt() > 0) {
						imp.setOperate("1");
					}
					// 型号不一样
					if (!StringUtils.equals(o.getModelNo(), purchaseinvoice.getModelno())) {
						imp.setOperate("1");
					}

					purchaseinvoice.setBarcode(o.getBarcode());
					if (o.getCaseNo() != null) {
						imp.setCaseno(o.getCaseNo());
						purchaseinvoice.setCaseno(o.getCaseNo());
					}
					imp.setWeight(o.getWeight());
					// bug10440 马腾 DR订单不调用后续接口发送至物流
					if (!StringUtils.equals(purchaseinvoice.getOrdtype(), OrderTypeEnum.DR)
							&& !StringUtils.equals(purchaseinvoice.getOrdtype(), OrderTypeEnum.CR)) {
						impInsertList.add(imp);
						receiveList.add(purchaseinvoice);
					}

					// bug10440 马腾 DR订单判断是否到货数量已等于采购数量，自行更改完成状态
					if ((StringUtils.equals(purchaseinvoice.getOrdtype(), OrderTypeEnum.DR)
							|| StringUtils.equals(purchaseinvoice.getOrdtype(), OrderTypeEnum.CR))
							&& purchaseinvoice.getQuantity().equals(purchaseinvoice.getQtyreceive())) {
						// 更新采购表
						OpsPurchaseorderExample temp = new OpsPurchaseorderExample();
						if (purchaseinvoice.getSplititemno() != null) {
							temp.createCriteria().andOrdernoEqualTo(purchaseinvoice.getOrderno())
									.andItemnoEqualTo(purchaseinvoice.getItemno())
									.andSplititemnoEqualTo(purchaseinvoice.getSplititemno());
						} else {
							temp.createCriteria().andOrdernoEqualTo(purchaseinvoice.getOrderno())
									.andItemnoEqualTo(purchaseinvoice.getItemno()).andSplititemnoIsNull();
						}
						OpsPurchaseorder record = new OpsPurchaseorder();
						record.setUpdatetime(new Date());
						record.setQtyreceive(purchaseinvoice.getQtyreceive());
						record.setFinishdate(new Date());
						record.setStatecode(PurchaseOrderStatusEnum.YIWANCHENG);
						opsPurchaseorderMapper.updateByExampleSelective(record, temp);
						// 更新请购表
						// bug13662使用请购中的采购单号字段进行查询
						OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
						if (purchaseinvoice.getSplititemno() != null) {
							example.createCriteria().andPoOrderNoEqualTo(purchaseinvoice.getOrderno())
									.andPoItemNoEqualTo(purchaseinvoice.getItemno())
									.andPoSplitNoEqualTo(purchaseinvoice.getSplititemno());
						} else {
							example.createCriteria().andPoOrderNoEqualTo(purchaseinvoice.getOrderno())
									.andPoItemNoEqualTo(purchaseinvoice.getItemno()).andPoSplitNoIsNull();
						}
						OpsRequestpurchase updateRequestpurchase = new OpsRequestpurchase();
						updateRequestpurchase.setQtyimport(purchaseinvoice.getQuantity());
						updateRequestpurchase.setUpdatetime(new Date());
						updateRequestpurchase.setStatecode(RequestPurchaseStatusEnum.YIWANCHENG);
						opsRequestpurchaseMapper.updateByExampleSelective(updateRequestpurchase, example);

						// 入库数量及状态赋值
						purchaseinvoice.setQtyimport(purchaseinvoice.getQuantity());
						purchaseinvoice.setStatecode(PurchaseInvoiceStatusEnum.YIWANCHENG);
					}

					// 更新
					purchaseinvoice.setImdateinfact(master.getArriveDate());
					purchaseinvoice.setUpdatetime(new Date());
					// opsPurchaseinvoiceMapper.updateByPrimaryKey(purchaseinvoice);
					invoiceUpdate.add(purchaseinvoice);
				}
				// 更新状态
				ImpInvoiceDetailPack pack = new ImpInvoiceDetailPack();
				pack.setId(o.getId());
				pack.setStatus((short) 6);
				impInvoiceDetailPackMapper.updateByPrimaryKeySelective(pack);

			}
		}
		// 前序状态补充
		if (prelist.size() > 0) {
			updateInvoice(prelist);
		}
		// bug15984,2024-12-4由于后续代码中先更新了qtyrecive，补充预到货时又根据qtyrecive判断是否需要预到货，导致跳过了预到货步骤，因此将更新采购表操作延后
		// 更新invoice表
		if (!CollectionUtils.isEmpty(invoiceUpdate)) {
			invoiceUpdate.forEach(a -> {
				opsPurchaseinvoiceMapper.updateByPrimaryKey(a);
			});
		}

		// 到货
		if (receiveList.size() > 0) {
			Map<String, Boolean> log = new HashMap<String, Boolean>();
			Map<String, OpsPurchaseinvoice> inmap = new HashMap<String, OpsPurchaseinvoice>();
			receiveList.forEach(i -> {
				if (!inmap.containsKey(i.getPono() + i.getLineitem()))
					inmap.put(i.getPono() + i.getLineitem(), i);
			});
			List<OpsImpdata> imp = new ArrayList<OpsImpdata>();
			for (OpsImpdata i : impInsertList) {
				OpsPurchaseinvoice in = inmap.get(i.getPono() + i.getLineitem());
				// ops-impdata判断是否为日本整型号拆分数据，需要生成组装指令
				if (in.getJpsplitvt() == 0) {
					if (i.getModelno().contains("*") || i.getModelno().startsWith("3C-")) {
						OpsPurchaseorderExample example = new OpsPurchaseorderExample();
						if (i.getSplititemno() != null)
							example.createCriteria().andOrdernoEqualTo(i.getOrderno()).andItemnoEqualTo(i.getItemno())
									.andSplititemnoEqualTo(i.getSplititemno());
						else
							example.createCriteria().andOrdernoEqualTo(i.getOrderno()).andItemnoEqualTo(i.getItemno())
									.andSplititemnoIsNull();
						List<OpsPurchaseorder> opo = opsPurchaseorderMapper.selectByExample(example);
						if (opo != null && opo.size() > 0) {
							if (!StringUtils.equals(i.getModelno(), opo.get(0).getModelno())) {
								i.setOperate("1");
							}
							i.setModelno(opo.get(0).getModelno());
						}
					}
					imp.add(i);
					opsImpdataMapper.insertSelective(i);
				} else {
					if (in.getLineitem() == 1) {
						// 日本不接收整型号需合并型号
						if (!log.containsKey(i.getPono() + i.getLineitem())) {
							OpsPurchaseorderExample example = new OpsPurchaseorderExample();
							if (i.getSplititemno() != null)
								example.createCriteria().andOrdernoEqualTo(i.getOrderno())
										.andItemnoEqualTo(i.getItemno()).andSplititemnoEqualTo(i.getSplititemno());
							else
								example.createCriteria().andOrdernoEqualTo(i.getOrderno())
										.andItemnoEqualTo(i.getItemno()).andSplititemnoIsNull();
							List<OpsPurchaseorder> opo = opsPurchaseorderMapper.selectByExample(example);
							if (opo != null && opo.size() > 0) {
								i.setModelno(opo.get(0).getModelno());
								i.setQuantity(in.getQuantity() / opo.get(0).getQuantity() * i.getQuantity());
								imp.add(i);
								log.put(i.getPono() + i.getLineitem(), true);
								opsImpdataMapper.insertSelective(i);
							}
						}
					}
				}
				result = result + 1;
			}
			// 调用接口到货库存
			invoiceForPoConfirm(imp);
			// wmDispatchService.invoniceForPoConfirm(imp);
			wmDispatchService.invoiceForPoConfirm(imp);
			for (OpsPurchaseinvoice purchaseinvoice : inmap.values()) {
				purchaseEventPublisher.purchaseOrderEvent(EventSourceEnum.PURCHASE_INVOICE_CONFIRM, purchaseinvoice);
			}
		}
		// bug14563写入返信fact表
		if (!sumqtymap.isEmpty()) {
			sumqtymap.entrySet().forEach(i -> {
				String orderno = i.getKey().split("-")[0];
				Integer itemno = Integer.parseInt(i.getKey().split("-")[1]);
				Integer splitno = null;
				if (i.getKey().split("-").length == 3) {
					splitno = Integer.parseInt(i.getKey().split("-")[2]);
				}
				OpsPoDeliveryFactExample exfact = new OpsPoDeliveryFactExample();
				if (splitno == null)
					exfact.createCriteria().andOrderNoEqualTo(orderno).andItemNoEqualTo(itemno).andSplitNoIsNull()
							.andInvoiceIdEqualTo(master.getId()).andInvoiceNoEqualTo(master.getInvoiceNo());
				else
					exfact.createCriteria().andOrderNoEqualTo(orderno).andItemNoEqualTo(itemno)
							.andSplitNoEqualTo(splitno).andInvoiceIdEqualTo(master.getId())
							.andInvoiceNoEqualTo(master.getInvoiceNo());
				List<OpsPoDeliveryFact> factlist = opsPoDeliveryFactMapper.selectByExample(exfact);
				if (CollectionUtils.isEmpty(factlist)) {
					OpsPoDeliveryFact fact = new OpsPoDeliveryFact();
					fact.setOrderNo(orderno);
					fact.setItemNo(itemno);
					fact.setSplitNo(splitno);
					fact.setQuantity(i.getValue());
					fact.setInvoiceId(master.getId());
					fact.setInvoiceNo(master.getInvoiceNo());
					// 20838 获取型号
					fact.setModelNo(modelNoMap.get(i.getKey()));
                    //bugid:19011 jp 取消补偿
                    if(StringUtils.isNotBlank(master.getSupplierCode()) && !"JP".equals(master.getSupplierCode())){
                        fact.setDeliveryTimeA(master.getShipDate());
                    }
					opsPoDeliveryFactMapper.insertSelective(fact);
				} else {
					// 查询是否有返信A相同的，有则无需处理；无则更新
					List<OpsPoDeliveryFact> t = factlist.stream()
							.filter(p -> p.getDeliveryTimeA() != null && p.getDeliveryTimeA() == master.getShipDate())
							.collect(Collectors.toList());
					if (CollectionUtils.isEmpty(t)) {
						OpsPoDeliveryFact record = new OpsPoDeliveryFact();
						record.setId(factlist.get(0).getId());
                        //bugid:19011 jp 取消补偿
                        if(StringUtils.isNotBlank(master.getSupplierCode()) && !"JP".equals(master.getSupplierCode())){
                            record.setDeliveryTimeA(master.getShipDate());
                        }
						record.setQuantity(i.getValue());
						record.setUpdateTime(new Date());
						opsPoDeliveryFactMapper.updateByPrimaryKeySelective(record);
					}

				}
			});
		}

		return result;
	}

	public boolean invoiceForPoConfirm(List<OpsImpdata> list) throws Exception {
		logger.info("InvoniceForPoConfirm params = {}", JSON.toJSONString(list));
		ImpInvoiceEventLog log = new ImpInvoiceEventLog();
		log.setOpType("/order/import/confirm/Invoice");
		log.setRequestParam(JSON.toJSONString(list));
		wmDispatchService.addImpInvoiceEventLog(log);
		return true;
	}

	// bug8614 马腾 新增采购单函数，根据发票子项新生成一条采购单写入采购删除表中记录
	public OpsPurchaseinvoice add(ImpInvoiceDetailPack item, String warehouseCode) {
		// 生成新的订单号
		String orderNo = newOrderNo();
		item.setOrderNo("ADD" + orderNo);
		item.setItemNo(1);
		// 请购
		OpsRequestpurchaseCancelLog requestCancel = new OpsRequestpurchaseCancelLog();
		requestCancel.setId((long) 1);
		requestCancel.setOrderno(item.getOrderNo());
		requestCancel.setItemno(item.getItemNo());
        requestCancel.setSplititemno(item.getSplitItemNo());
		requestCancel.setModelno(item.getModelNo());
		requestCancel.setQuantity(item.getQuantity());
		requestCancel.setSupplierid(item.getSupplierCode());
		if (StringUtils.isNotBlank(item.getRohsCode())) {
			requestCancel.setProducttag("0");
		}
		requestCancel.setPurchasewarehouseid(warehouseCode);
		requestCancel.setOrdtype("20");
		requestCancel.setInventorytypecode("TY");
		requestCancel.setRequestwarehouseid(warehouseCode);
		requestCancel.setStatecode(PurchaseOrderStatusEnum.ADDNEW);
		requestCancel.setInserttime(new Date());
		requestCancel.setCustomerno("");
		requestCancel.setSpecmark("0");
		requestCancel.setIslot(false);
		requestCancel.setWmtag("W");
		requestCancel.setRemark(item.getInvoiceId() + ";" + item.getInvoiceNo() + ";" + item.getPono() + ";"
				+ item.getPoitemno().toString());
		requestCancel.setPoOrderNo(item.getOrderNo());
		requestCancel.setPoItemNo(item.getItemNo());
        requestCancel.setEndUser(item.getEndUser());
		opsRequestpurchaseCancelLogMapper.insertSelective(requestCancel);
		// 采购
		OpsPurchaseorderCancelLog purchaseCancel = new OpsPurchaseorderCancelLog();
		purchaseCancel.setId((long) 1);
		purchaseCancel.setOrderno(item.getOrderNo());
		purchaseCancel.setItemno(item.getItemNo());
        purchaseCancel.setSplititemno(item.getSplitItemNo());
		purchaseCancel.setModelno(item.getModelNo());
		purchaseCancel.setQuantity(item.getQuantity());
		purchaseCancel.setSupplierid(item.getSupplierCode());
		if (StringUtils.isNotBlank(item.getRohsCode())) {
			purchaseCancel.setGreencode(item.getRohsCode());
		}
		purchaseCancel.setReceivewarehouseid(warehouseCode);
		purchaseCancel.setRemark(item.getInvoiceId() + ";" + item.getInvoiceNo() + ";" + item.getPono() + ";"
				+ item.getPoitemno().toString());
		purchaseCancel.setStatecode(PurchaseOrderStatusEnum.ADDNEW);
		purchaseCancel.setInserttime(new Date());
		purchaseCancel.setUpdatetime(new Date());
        purchaseCancel.setEndUser(item.getEndUser());
		opsPurchaseorderCancelLogMapper.insertSelective(purchaseCancel);

		OpsPurchaseinvoice purchaseInvoice = new OpsPurchaseinvoice();
		purchaseInvoice.setPono(item.getOrderNo() + "-" + item.getItemNo().toString());
		purchaseInvoice.setLineitem(1);
		purchaseInvoice.setOrderno(item.getOrderNo());
		purchaseInvoice.setItemno(item.getItemNo());
        purchaseInvoice.setSplititemno(item.getSplitItemNo());
		purchaseInvoice.setRemark(item.getInvoiceId() + ";" + item.getInvoiceNo() + ";" + item.getPono() + ";"
				+ item.getPoitemno().toString());
		purchaseInvoice.setJpsplitvt(0);
		purchaseInvoice.setModelno(item.getModelNo());
		purchaseInvoice.setQuantity(item.getQuantity());
		purchaseInvoice.setSupplierid(item.getSupplierCode());
		purchaseInvoice.setStatecode(PurchaseOrderStatusEnum.ADDNEW);
		purchaseInvoice.setUpdatetime(new Date());
        purchaseInvoice.setEndUser(item.getEndUser());
		opsPurchaseinvoiceMapper.insertSelective(purchaseInvoice);
        // bugid:20149 发票入库优化-前端部分 c14717 20260209
        if(StringUtils.isNotBlank(item.getEndUser())){
            // 创建调库计划
            try {
                adjustInventoryService.createTransferPlanForADDOrder(item.getOrderNo(),item.getItemNo(),item.getSplitItemNo()
                        ,item.getEndUser(),item.getModelNo(),item.getQuantity(),"impdata");
            } catch (OpsException e) {
                logger.error("调库计划创建失败:"+item.getOrderNo());
            }

        }

		return purchaseInvoice;
	}

	// bug8614新单号
	public String newOrderNo() {
		String maxOrderNo = purchaseOrderDao.maxAddPurchaseNo();
		if (StringUtils.isBlank(maxOrderNo)) {
			maxOrderNo = "0000000000001";
			return maxOrderNo;
		} else {
			maxOrderNo = maxOrderNo.substring(3);
		}
		String orderNo = OrderNoUtil.hexAdd(maxOrderNo, 1);
		return orderNo;
	}
	// BUG9306马腾 新单号
	// public String newOrderNo() {
	// SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
	// String before = sdf.format(new Date());
	// String orderNo = before + String.format("%07d", getSerialNumber());
	// return orderNo;
	// }

	// BUG9306马腾 从REDIS中获取当前累计数 每天更新
	public Integer getSerialNumber() {
		long time = new Date().getTime();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		long lastsecond = (calendar.getTime().getTime() - time) / 1000;

		RedisAtomicLong numRedis = new RedisAtomicLong(Constants.COUNTER_NEWPURCHASENO,
				redisTemplate.getConnectionFactory());
		Long num = numRedis.getAndIncrement();
		if (num == null || num.longValue() == 0) {
			numRedis.expire(lastsecond, TimeUnit.SECONDS);
			num = numRedis.getAndIncrement();
		}
		return num.intValue();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean addDeletePurchase(String invoiceNo, long invoiceId) throws Exception {
		List<Short> statuslist = new ArrayList<Short>();
		statuslist.add((short) 1);
		statuslist.add((short) 2);
		ImpInvoiceDetailPackExample ex = new ImpInvoiceDetailPackExample();
		ex.createCriteria().andInvoiceIdEqualTo(invoiceId).andInvoiceNoEqualTo(invoiceNo).andStatusIn(statuslist);
		List<ImpInvoiceDetailPack> list = impInvoiceDetailPackMapper.selectByExample(ex);
		if (list == null || list.size() == 0) {
			return false;
		}
		ImpInvoiceMaster master = impInvoiceMasterMapper.selectByPrimaryKey(invoiceId);
		if (master == null) {
			return false;
		} else {
			if (StringUtils.isBlank(master.getReceiveWarehouseCode())) {
				return false;
			}
		}
		list = purchaseOrderDao.getNoPurchase(invoiceId, invoiceNo);
		if (list == null || list.size() == 0) {
			return false;
		}
		// 补充请购及采购表信息
		for (ImpInvoiceDetailPack item : list) {
			// 请购
			OpsRequestpurchaseCancelLog requestCancel = new OpsRequestpurchaseCancelLog();
			requestCancel.setId((long) 1);
			requestCancel.setOrderno(item.getOrderNo());
			requestCancel.setItemno(item.getItemNo());
			if (item.getSplitItemNo() != null) {
				requestCancel.setSplititemno(item.getSplitItemNo());
			}
			requestCancel.setModelno(item.getModelNo());
			requestCancel.setQuantity(item.getQuantity());
			requestCancel.setSupplierid(item.getSupplierCode());
			if (StringUtils.isNotBlank(item.getRohsCode())) {
				requestCancel.setProducttag("0");
			}
			requestCancel.setPurchasewarehouseid(master.getReceiveWarehouseCode());
			requestCancel.setOrdtype("20");
			requestCancel.setInventorytypecode("TY");
			requestCancel.setRequestwarehouseid(master.getReceiveWarehouseCode());
			requestCancel.setStatecode(PurchaseOrderStatusEnum.BUCHONG);
			requestCancel.setInserttime(new Date());
			requestCancel.setCustomerno("");
			requestCancel.setSpecmark("0");
			requestCancel.setIslot(false);
			requestCancel.setWmtag("W");
			opsRequestpurchaseCancelLogMapper.insertSelective(requestCancel);
			// 采购
			OpsPurchaseorderCancelLog purchaseCancel = new OpsPurchaseorderCancelLog();
			purchaseCancel.setId((long) 1);
			purchaseCancel.setOrderno(item.getOrderNo());
			purchaseCancel.setItemno(item.getItemNo());
			if (item.getSplitItemNo() != null) {
				purchaseCancel.setSplititemno(item.getSplitItemNo());
			}
			purchaseCancel.setModelno(item.getModelNo());
			purchaseCancel.setQuantity(item.getQuantity());
			purchaseCancel.setSupplierid(item.getSupplierCode());
			if (StringUtils.isNotBlank(item.getRohsCode())) {
				purchaseCancel.setGreencode(item.getRohsCode());
			}
			purchaseCancel.setReceivewarehouseid(master.getReceiveWarehouseCode());
			purchaseCancel.setStatecode(PurchaseOrderStatusEnum.BUCHONG);
			purchaseCancel.setInserttime(new Date());
			purchaseCancel.setUpdatetime(new Date());
			opsPurchaseorderCancelLogMapper.insertSelective(purchaseCancel);
		}

		return true;
	}

	/**
	 * 参考 OrderStateServiceImpl#resolveJPDlvDate 的 setJpDlvDateValue 逻辑
	 * 将供应商返回的原始返信（6位字符串）转换成日期格式
	 */
	private Date convertSrcDeliveryTimeToDate(String srcDeliveryTime) {
		if (StringUtils.isBlank(srcDeliveryTime)) {
			return null;
		}
		if (srcDeliveryTime.length() == 6) {
			// 大致返信：前4位为年月，后2位为99
			if (!srcDeliveryTime.startsWith("99") && srcDeliveryTime.endsWith("99")) {
				String yearStr = srcDeliveryTime.substring(0, 2);
				String monthStr = srcDeliveryTime.substring(2, 4);
				int year = Integer.parseInt(yearStr) + 2000;
				int month = Integer.parseInt(monthStr);
				Calendar cal = Calendar.getInstance();
				cal.set(year, month - 1, 1);
				int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				String conventDateStr = "99" + yearStr + "-" + monthStr + "-" + daysInMonth;
				return parseDate(conventDateStr);
			}
			// 不确定返信特殊编码
			switch (srcDeliveryTime) {
				case "111111":
					return parseDate("1111-01-01");
				case "222222":
					return parseDate("2222-02-02");
				case "333333":
					return parseDate("3333-03-03");
				case "444444":
					return parseDate("4444-04-04");
				case "555555":
					return parseDate("5555-05-05");
				case "666666":
					return parseDate("6666-06-06");
				case "777777":
					return parseDate("7777-07-07");
				case "888888":
					return parseDate("8888-08-08");
				case "999999":
					return parseDate("9999-09-09");
				case "990000":
					return parseDate("9900-09-09");
				default:
					// 正常日期yyMMdd转换为20yy-MM-dd
					String strDate = "20" + srcDeliveryTime.substring(0, 2) + "-" + srcDeliveryTime.substring(2, 4) + "-" + srcDeliveryTime.substring(4, 6);
					Date result = parseDate(strDate);
					//如果解析失败，则使用默认值
					if (result == null) {
						return parseDate("9999-12-31");
					}
					return result;
			}
		} else {
			return parseDate(srcDeliveryTime);
		}
	}

	private Date parseDate(String dateStr) {
		try {
			return DateUtil.parseDate(dateStr);
		} catch (Exception e) {
			logger.warn("转换src_delivery_time日期失败: {}", dateStr);
			return null;
		}
	}

}
