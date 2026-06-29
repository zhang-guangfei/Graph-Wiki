package com.sales.ops.serviceimpl.deliver;

import java.util.*;

import com.sales.ops.common.until.OPSRedisUtils;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.sales.ops.db.dao.AdapterOpsTranslationConfigMapper;
import com.sales.ops.db.dao.OpsPurchaseinvoiceMapper;
import com.sales.ops.db.entity.AdapterOpsTranslationConfig;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsPurchaseinvoiceExample;
import com.sales.ops.dto.poDeliver.ChangeFromSupplier;
import com.sales.ops.dto.purchase.PurchaseReplyInfo;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.PoReplyDateStrEnum;
import com.sales.ops.feign.DeliveryPoFeignApi;
import com.sales.ops.feign.WmPurchaseFeignApi;
import com.sales.ops.service.deliver.ChangeLogService;
import com.sales.ops.service.deliver.PoFactService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;

import javax.annotation.Resource;

/**
 * 交付计划--采购处理
 * 
 * @author SMC892N
 *
 */
@Service
public class ChangeLogServiceImpl implements ChangeLogService {

	private final static Logger logger = LoggerFactory.getLogger(ChangeLogService.class);

	@Autowired
	private DictDataServiceFeignApi dictDataServiceFeignApi;

	@Autowired
	private OpsPurchaseinvoiceMapper opsPurchaseinvoiceMapper;

	@Autowired
	private AdapterOpsTranslationConfigMapper adapterOpsTranslationConfigMapper;

	@Autowired
	private WmPurchaseFeignApi wmPurchaseFeignApi;

	@Autowired
	private DeliveryPoFeignApi deliveryPoFeignApi;

	@Autowired
	private PoFactService poFactService;

	@Resource
	private OPSRedisUtils opsRedisUtils;

	@Override
	public List<OpsPurchaseinvoice> getPurchaseInvoice(String orderno, Integer itemno, Integer splitno) {
		if (StringUtils.isBlank(orderno) || itemno == null) {
			return null;
		}
		OpsPurchaseinvoiceExample opsPurchaseinvoiceExample = new OpsPurchaseinvoiceExample();
		if (splitno == null || splitno == 0) {
			// bug13665 增加拆分单号为空的查询限制
			opsPurchaseinvoiceExample.createCriteria().andOrdernoEqualTo(orderno).andItemnoEqualTo(itemno)
					.andSplititemnoIsNull();
		} else {
			opsPurchaseinvoiceExample.createCriteria().andOrdernoEqualTo(orderno).andItemnoEqualTo(itemno)
					.andSplititemnoEqualTo(splitno);
		}
		List<OpsPurchaseinvoice> l = opsPurchaseinvoiceMapper.selectByExample(opsPurchaseinvoiceExample);
		return l;
	}

	@Override
	public List<OpsPurchaseinvoice> getPurchaseInvoiceByPono(String pono, Integer lineitem) {
		if (StringUtils.isBlank(pono) || lineitem == null) {
			return null;
		}
		OpsPurchaseinvoiceExample opsPurchaseinvoiceExample = new OpsPurchaseinvoiceExample();
		opsPurchaseinvoiceExample.createCriteria().andPonoEqualTo(pono).andLineitemEqualTo(lineitem);
		List<OpsPurchaseinvoice> l = opsPurchaseinvoiceMapper.selectByExample(opsPurchaseinvoiceExample);
		return l;
	}

    /**
     * 更新filePath
     * @param o
     */
    public void updatePinvFilePath( PurchaseReplyInfo o){
        if ( StringUtils.isBlank(o.getPono())) {
            return;
        }
        OpsPurchaseinvoiceExample opsPurchaseinvoiceExample = new OpsPurchaseinvoiceExample();
        opsPurchaseinvoiceExample.createCriteria().andPonoEqualTo(o.getPono())
                .andLineitemEqualTo(o.getLineitem());
        List<OpsPurchaseinvoice> l = opsPurchaseinvoiceMapper.selectByExample(opsPurchaseinvoiceExample);
        if (CollectionUtils.isEmpty(l)) {
            return;
        }
        if(StringUtils.isNotBlank(o.getSendFilePath())){
            OpsPurchaseinvoice purchaseinvoice = new OpsPurchaseinvoice();
            purchaseinvoice.setId(l.get(0).getId());
            purchaseinvoice.setFilepath(o.getSendFilePath());
            purchaseinvoice.setUpdatetime(new Date());
            // 增加更新三次重试，避免被牺牲
            int trynum = 3;
            boolean updateresult = false;
            while (!updateresult && trynum > 0) {
                try {
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
    }

	@Override
	public CommonResult<Integer> operateMainLog() {
		// 运输方式
		Map<String, String> transtypelist = new HashMap<String, String>();
		Map<String, String> supplierlist = new HashMap<String, String>();
		List<AdapterOpsTranslationConfig> translist = adapterOpsTranslationConfigMapper.selectByExample(null);
		if (!CollectionUtils.isEmpty(translist)) {
			translist.forEach(a -> {
				if (StringUtils.equals("transtype", a.getCodeType()))
					transtypelist.put(a.getAdapterCode(), a.getOpsCode());
				if (StringUtils.equals("supplierid", a.getCodeType()))
					supplierlist.put(a.getAdapterCode(), a.getOpsCode());
			});
		} else {
			logger.error("adapter_ops_translation_config无数据");
			return CommonResult.failure("adapter_ops_translation_config无数据");
		}
		if (CollectionUtils.isEmpty(supplierlist)) {
			logger.error("adapter_ops_translation_config无supplierlist数据");
			return CommonResult.failure("adapter_ops_translation_config无supplier数据");
		}
		if (CollectionUtils.isEmpty(transtypelist)) {
			logger.error("adapter_ops_translation_config无transtypelist数据");
			return CommonResult.failure("adapter_ops_translation_config无transtype数据");
		}
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
		ResultVo<List<DataCodeVO>> result = dictDataServiceFeignApi.getDataCodes("3008");
		if (result.isSuccess() && !CollectionUtils.isEmpty(result.getData())
				&& StringUtils.isNotBlank(result.getData().get(0).getExtNote1())) {
			long id = Long.parseLong(result.getData().get(0).getExtNote1());
			long sum = Long.parseLong(result.getData().get(0).getExtNote2());
			CommonResult<List<ChangeFromSupplier>> listresult = deliveryPoFeignApi.getWaitOperateChange(id,
					Long.sum(id, sum));
			if (!listresult.isSuccess()) {
				return CommonResult.failure(listresult.getMessage());
			}
			List<ChangeFromSupplier> list = listresult.getData();
			if (CollectionUtils.isEmpty(list)) {
				return CommonResult.success(0);
			}
			List<PurchaseReplyInfo> info = new ArrayList<PurchaseReplyInfo>();
			for (ChangeFromSupplier item : list) {
                // bugid:19871 不接D码 C14717 20260113
                if(StringUtils.isNotBlank(item.getMaincode()) && "D".equals(item.getMaincode())){
                    // 不接D码
                    continue;
                }
				// 接单、写入采购货期计划及实际俩表
				PurchaseReplyInfo temp = new PurchaseReplyInfo();
                //bugid:19565 c14717 20251222
                if(StringUtils.isNotBlank(item.getSendFilePath())){
                    temp.setSendFilePath(item.getSendFilePath());
                }
				temp.setPono(item.getPono().trim());
				if (item.getLineitem() == null) {
					temp.setLineitem(1);
				} else {
					temp.setLineitem(item.getLineitem());
				}

                if(StringUtils.isNotBlank(item.getMaincode()) && "I_S".equals(item.getMaincode())){
                    // 更新至ops_purchaseInvoice.filepath
                    updatePinvFilePath(temp);
                    continue;
                }
				if (StringUtils.isNotBlank(item.getSuppliercode())
						&& supplierlist.containsKey(item.getSuppliercode())) {
					temp.setSupplierid(supplierlist.get(item.getSuppliercode()));
				} else {
					logger.error("operateMainLog无供应商数据，pono：" + item.getPono() + ";lineitem:" + item.getLineitem()
							+ ";id:" + item.getId());
					continue;
				}
				// 接单号
				if (StringUtils.isNotBlank(item.getSupplierordermainno())) {
					if (StringUtils.isNotBlank(item.getSupplierorderlineno())) {
						temp.setReplyorderno(item.getSupplierordermainno() + "-" + item.getSupplierorderlineno());
					} else {
						temp.setReplyorderno(item.getSupplierordermainno());
					}
				}
				// 接单日期
				if (item.getReceiveorderdate() != null) {
					temp.setReplyorderdate(item.getReceiveorderdate());
				}
				if (StringUtils.isNotBlank(item.getModelno())) {
					temp.setModelno(item.getModelno());
				}
				// 出库区分：0在库，1生产
				if (StringUtils.isNotBlank(item.getGoodssourcecode())) {
					if (StringUtils.equals("0", item.getGoodssourcecode())) {
						temp.setProducefactory("在库");
					} else {
						temp.setProducefactory("生产");
					}
				}
				// 出库区分
				if (StringUtils.isNotBlank(item.getGoodsourcecoderemark())) {
					temp.setProducefactory(item.getGoodsourcecoderemark());
				}
				if (StringUtils.isNotBlank(item.getProduceunit())) {
					temp.setProduceholon(item.getProduceunit());
				}
				if (item.getProducestarttime() != null) {
					temp.setBeginproducedate(item.getProducestarttime());
				}
				if (StringUtils.isNotBlank(item.getSrcdeliverytime())) {
					temp.setSrcdeliverytime(item.getSrcdeliverytime());
					temp.setPlanid(item.getPlanid());
					// 返信
					if (item.getLatestdeliverytime() != null) {
						temp.setReplyexportdate(item.getLatestdeliverytime());
					}
					for (PoReplyDateStrEnum i : PoReplyDateStrEnum.values()) {
						if (StringUtils.equals(i.getCode(), item.getSrcdeliverytime())) {
							temp.setReplyexportdate(null);
							temp.setSrcdeliverytimedesc(i.getCodeName());
						}
					}
				}
				if (item.getPlanqty() != null) {
					temp.setPlanqty(item.getPlanqty());
				}

				// TODO 异常运输方式是否需要存储
				if (StringUtils.isNotBlank(item.getReplytranstype())) {
					if (transtypelist.containsKey(item.getReplytranstype()))
						temp.setTranstype(transtypelist.get(item.getReplytranstype()));
				}
				if (StringUtils.isNotBlank(item.getPlantranstype())) {
					if (transtypelist.containsKey(item.getPlantranstype()))
						temp.setTranstype(transtypelist.get(item.getPlantranstype()));
				}
				// 实际运输方式
				if (StringUtils.isNotBlank(item.getFacttranstype())) {
					if (transtypelist.containsKey(item.getFacttranstype()))
						temp.setTranstypeFact(transtypelist.get(item.getFacttranstype()));
				}
				if (StringUtils.isNotBlank(item.getPlanreamrk())) {
					temp.setReasonremark(item.getPlanreamrk());
				}

                // H->W->A  H有值取H H没值取W W没值取A
				// 供应商出库时间
				if (item.getDeliverytimeWFact() != null) {
					temp.setFacexpdate(item.getDeliverytimeWFact());
					temp.setDeliverytimeWFact(item.getDeliverytimeWFact());
				}
				// 工厂实际完工日
				if (item.getDeliverytimeHFact() != null) {
                    temp.setFacexpdate(item.getDeliverytimeHFact());
					temp.setDeliverytimeHFact(item.getDeliverytimeHFact());
				}
				// ops_po_exporting_log_from_supplier表id，对应采购fact表sourceid
				if (item.getFactid() != null) {
					temp.setFactid(item.getFactid());
				}
				// 实际返信对应的到货数量
				if (item.getFactqty() != null) {
					temp.setFactqty(item.getFactqty());
				}
				// 预计H W
				if (item.getDeliverytimeHPlan() != null) {
					temp.setDeliverytimeHPlan(item.getDeliverytimeHPlan());
				}
				if (item.getDeliverytimeWPlan() != null) {
					temp.setDeliverytimeWPlan(item.getDeliverytimeWPlan());
				}
				// bug14563取消发票号的读取使用
				// if (StringUtils.isNotBlank(item.getInvoiceNo())) {
				// temp.setInvoiceno(item.getInvoiceNo());
				// }
				// bug14957增加数据来源，判断updateinvoice调用从哪个数据源过来
				temp.setSource("deliver_adapter");
				info.add(temp);
			}
			//
			if (!CollectionUtils.isEmpty(info)) {
				CommonResult<String> updateinvoice = null;
				try {
					updateinvoice = wmPurchaseFeignApi.updateInvoice(info);
				} catch (Exception e) {
					logger.error("交付系统适配器数据处理id：" + Long.toString(id) + "之后的增量为：" + Long.toString(sum) + "数据处理失败！错误原因：",
							e);
					//重试3次 bugid:16753 c14717 20250212
					Boolean retryFlag = handFail(id);
					if(retryFlag){
						return CommonResult.success(0);
					}
					return CommonResult.failure("交付系统适配器数据处理id：" + Long.toString(id) + "之后的增量为："
							+ Long.toString(sum) + "数据处理失败！updateinvoice错误原因：" + e.getMessage());
				}
				if (!updateinvoice.isSuccess()) {
					//重试3次 bugid:16753 c14717 20250212
					Boolean retryFlag = handFail(id);
					if(retryFlag){
						return CommonResult.success(0);
					}

					// 更新字典中存储处理到的id值
					DataTypeVO dataTypeVO = new DataTypeVO();
					dataTypeVO.setClassCode("3008");
					dataTypeVO.setCode("id");
					// bug14315 在处理数据不足sum值时，按实际数量保存id值
					if (Long.compare(sum, list.size()) > 0) {
						dataTypeVO.setExtNote1(Long.toString(Long.sum(id, list.size())));
					} else {
						dataTypeVO.setExtNote1(Long.toString(Long.sum(id, sum)));
					}

					ResultVo<Integer> i = dictDataServiceFeignApi.updateDataParam(dataTypeVO);
					if (i.isSuccess()) {
						return CommonResult.failure("交付系统适配器数据处理id：" + Long.toString(id) + "之后的增量为："
								+ Long.toString(sum) + "数据处理失败！updateinvoice错误原因：" + updateinvoice.getMessage());
					}
					return CommonResult.failure("交付系统适配器数据处理id：" + Long.toString(id) + "之后的增量为："
							+ Long.toString(sum) + "数据处理失败！updateinvoice错误原因：" + updateinvoice.getMessage()
							+ ";字典更新错误原因：" + i.getMessage());

				}
				// bug14526写入返信fact表
				poFactService.insertFactReply(info);
			}

			// 更新字典中存储处理到的id值
			DataTypeVO dataTypeVO = new DataTypeVO();
			dataTypeVO.setClassCode("3008");
			dataTypeVO.setCode("id");
			// bug14315 在处理数据不足sum值时，按实际数量保存id值
			if (Long.compare(sum, list.size()) > 0) {
				dataTypeVO.setExtNote1(Long.toString(Long.sum(id, list.size())));
			} else {
				dataTypeVO.setExtNote1(Long.toString(Long.sum(id, sum)));
			}
			ResultVo<Integer> i = dictDataServiceFeignApi.updateDataParam(dataTypeVO);
			if (i.isSuccess()) {
				return CommonResult.success(list.size());
			}
			return CommonResult.failure(i.getMessage());
		} else {
			logger.error("字典3008无数据");
			return CommonResult.failure("字典3008无数据");
		}
	}

	/**
	 * 返回布尔值，是否需要重试 败重试三次
	 * @param id
	 * @return
	 * times    redis    result
	 *   1      null      true
	 *   2       2        true
	 *   3       3        true
	 *   4       4        false
	 */
	public Boolean handFail(Long id){
		try {
			String key = "ops:po:upinvoice:fails";
			Object obj = opsRedisUtils.hGet(key, id.toString());
			if(Objects.isNull(obj)){
				opsRedisUtils.hPut(key, id.toString(),2);
				opsRedisUtils.expire(key,  60 * 60 * 24 * 10);
				return true;
			}
			int failSize = (int) obj;
			if(3 - failSize < 0){
				return false;
			}
			opsRedisUtils.hPut(key, id.toString(),failSize + 1);
			opsRedisUtils.expire(key,  60 * 60 * 24 * 10);
			return 3 - failSize >= 0;
		} catch (Exception e) {
			logger.error("redis连接失败");
			return false;
		}
	}

}
