package com.sales.ops.serviceimpl.deliver;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.sales.ops.db.dao.OpsPoDeliveryFactMapper;
import com.sales.ops.db.entity.OpsPoDeliveryFact;
import com.sales.ops.db.entity.OpsPoDeliveryFactExample;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.dto.purchase.PurchaseReplyInfo;
import com.sales.ops.service.deliver.ChangeLogService;
import com.sales.ops.service.deliver.PoFactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PoFactServiceImpl implements PoFactService {

	@Autowired
	private OpsPoDeliveryFactMapper opsPoDeliveryFactMapper;

	@Autowired
	private ChangeLogService changeLogService;

	@Override
	public void insertFactReply(List<PurchaseReplyInfo> info) {
		// TODO Auto-generated method stub
		for (PurchaseReplyInfo o : info) {
			if (o.getDeliverytimeHFact() == null && o.getDeliverytimeWFact() == null
					&& StringUtils.isBlank(o.getTranstypeFact())) {
				continue;
			}

			List<OpsPurchaseinvoice> l = changeLogService.getPurchaseInvoiceByPono(o.getPono(), o.getLineitem());
			if (!CollectionUtils.isEmpty(l)) {
				int insert = 0;
				OpsPurchaseinvoice temp = l.get(0);
				// 判断fact表中是否已存在
				OpsPoDeliveryFactExample ex = new OpsPoDeliveryFactExample();
				if (temp.getSplititemno() == null) {
					ex.createCriteria().andOrderNoEqualTo(temp.getOrderno()).andItemNoEqualTo(temp.getItemno())
							.andSplitNoIsNull().andModelNoEqualTo(temp.getModelno()).andQuantityEqualTo(o.getFactqty());
				} else {
					ex.createCriteria().andOrderNoEqualTo(temp.getOrderno()).andItemNoEqualTo(temp.getItemno())
							.andSplitNoEqualTo(temp.getSplititemno()).andModelNoEqualTo(temp.getModelno())
							.andQuantityEqualTo(o.getFactqty());
				}
				List<OpsPoDeliveryFact> factlist = opsPoDeliveryFactMapper.selectByExample(ex);
				if (CollectionUtils.isEmpty(factlist)) {
					insert = 1;
				} else {
					// 是否有H/W为空或与当前值不同的fact表数据
					if (o.getDeliverytimeHFact() != null && o.getDeliverytimeWFact() != null) {
						List<OpsPoDeliveryFact> temphw = factlist.stream()
								.filter(s -> (s.getDeliveryTimeH() == null && s.getDeliveryTimeW() == null))
								.collect(Collectors.toList());
						if (CollectionUtils.isEmpty(temphw)) {
							temphw = factlist.stream()
									.filter(s -> s.getDeliveryTimeH() == o.getDeliverytimeHFact()
											&& s.getDeliveryTimeW() == o.getDeliverytimeWFact())
									.collect(Collectors.toList());
							if (CollectionUtils.isEmpty(temphw))
								insert = 1;
						} else {
							OpsPoDeliveryFact fact = new OpsPoDeliveryFact();
							fact.setDeliveryTimeH(o.getDeliverytimeHFact());
                            //bugid:19011
                            if(StringUtils.isNotBlank(o.getSupplierid()) && "JP".equals(o.getSupplierid())){
                                fact.setDeliveryTimeA(o.getDeliverytimeWFact());
                            }
							fact.setDeliveryTimeW(o.getDeliverytimeWFact());
							fact.setInvoiceNo(o.getInvoiceno());
							fact.setUpdateTime(new Date());
							if (StringUtils.isNotBlank(o.getTranstypeFact()))
								fact.setTranstypeCode(o.getTranstypeFact());
							opsPoDeliveryFactMapper.updateByExampleSelective(fact, ex);
						}
					} else if (o.getDeliverytimeHFact() != null) {
						// W为空
						List<OpsPoDeliveryFact> temph = factlist.stream().filter(s -> s.getDeliveryTimeH() == null)
								.collect(Collectors.toList());
						if (CollectionUtils.isEmpty(temph)) {
							temph = factlist.stream().filter(s -> s.getDeliveryTimeH() == o.getDeliverytimeHFact())
									.collect(Collectors.toList());
							if (CollectionUtils.isEmpty(temph))
								insert = 1;
						} else {
							OpsPoDeliveryFact fact = new OpsPoDeliveryFact();
							fact.setDeliveryTimeH(o.getDeliverytimeHFact());
							fact.setUpdateTime(new Date());
							if (StringUtils.isNotBlank(o.getTranstypeFact()))
								fact.setTranstypeCode(o.getTranstypeFact());
							opsPoDeliveryFactMapper.updateByExampleSelective(fact, ex);
						}
					} else {
						// H为空
						List<OpsPoDeliveryFact> tempw = factlist.stream().filter(s -> s.getDeliveryTimeW() == null)
								.collect(Collectors.toList());
						if (CollectionUtils.isEmpty(tempw)) {
							tempw = factlist.stream().filter(s -> s.getDeliveryTimeW() == o.getDeliverytimeWFact())
									.collect(Collectors.toList());
							if (CollectionUtils.isEmpty(tempw))
								insert = 1;
						} else {
							OpsPoDeliveryFact fact = new OpsPoDeliveryFact();
							fact.setDeliveryTimeW(o.getDeliverytimeWFact());
                            //bugid:19011
                            if(StringUtils.isNotBlank(o.getSupplierid()) && "JP".equals(o.getSupplierid())){
                                fact.setDeliveryTimeA(o.getDeliverytimeWFact());
                            }
							fact.setInvoiceNo(o.getInvoiceno());
							fact.setUpdateTime(new Date());
							if (StringUtils.isNotBlank(o.getTranstypeFact()))
								fact.setTranstypeCode(o.getTranstypeFact());
							opsPoDeliveryFactMapper.updateByExampleSelective(fact, ex);
						}
					}

				}
				if (insert == 1) {
					OpsPoDeliveryFact fact = new OpsPoDeliveryFact();
					fact.setSourceId(o.getFactid());
					fact.setOrderNo(temp.getOrderno());
					fact.setItemNo(temp.getItemno());
					fact.setSplitNo(temp.getSplititemno());
					fact.setModelNo(temp.getModelno());
					fact.setQuantity(o.getFactqty());
					fact.setTranstypeCode(o.getTranstypeFact());
					fact.setDeliveryTimeH(o.getDeliverytimeHFact());
					fact.setDeliveryTimeW(o.getDeliverytimeWFact());
                    //bugid:19011
                    if(StringUtils.isNotBlank(o.getSupplierid()) && "JP".equals(o.getSupplierid())){
                        fact.setDeliveryTimeA(o.getDeliverytimeWFact());
                    }
					fact.setInvoiceNo(o.getInvoiceno());
					opsPoDeliveryFactMapper.insertSelective(fact);
				}
			}
		}
	}

	@Override
	public void insertFactPre(PurchaseReplyInfo o) {
        OpsPoDeliveryFactExample exfact = new OpsPoDeliveryFactExample();
		OpsPoDeliveryFactExample.Criteria criteria = exfact.createCriteria().andOrderNoEqualTo(o.getOrderno()).andItemNoEqualTo(o.getItemno())
				.andModelNoEqualTo(o.getModelno()).andQuantityEqualTo(o.getQtyTrans()).andInvoiceIdIsNull();
		if (o.getSplitno() == null) {
			criteria.andSplitNoIsNull();
		} else {
			criteria.andSplitNoEqualTo(o.getSplitno());
		}
		List<OpsPoDeliveryFact> factlist = opsPoDeliveryFactMapper.selectByExample(exfact);
		//2.不存在，插入新记录，带invoiceId,invoiceNo
		if (CollectionUtils.isEmpty(factlist)) {
			OpsPoDeliveryFact fact = new OpsPoDeliveryFact();
			fact.setOrderNo(o.getOrderno());
			fact.setItemNo(o.getItemno());
			fact.setSplitNo(o.getSplitno());
			fact.setModelNo(o.getModelno());
			fact.setQuantity(o.getQtyTrans());
			fact.setTranstypeCode(o.getTranstypeFact());
			fact.setInvoiceId(o.getInvoiceid());
			fact.setInvoiceNo(o.getInvoiceno());
			fact.setDeliveryTimeA(o.getDeliverytimeAFact());
			fact.setDeliveryTimeW(o.getDeliverytimeWFact());
            // bugid:19011 20251014
            if(StringUtils.isNotBlank(o.getSupplierid()) && "JP".equals(o.getSupplierid())){
                fact.setDeliveryTimeA(o.getDeliverytimeWFact());
            }else {
                fact.setDeliveryTimeA(o.getDeliverytimeAFact());
            }
			//bugid 20746
			if (StringUtils.isNotBlank(o.getSupplierid()) && "JP".equals(o.getSupplierid()) && (fact.getDeliveryTimeW() == null)) {
				//查询历史数据
				OpsPoDeliveryFact historyFact = getHistoryFact(o);
				if (historyFact != null) {
					if (fact.getDeliveryTimeW() == null) {
						fact.setDeliveryTimeA(historyFact.getDeliveryTimeA());
						fact.setDeliveryTimeW(historyFact.getDeliveryTimeW());
						fact.setRemark("parentId：" + historyFact.getId().toString());
					}
				}
			}
			opsPoDeliveryFactMapper.insertSelective(fact);
		} else {
			factlist = factlist.stream().filter(p -> StringUtils.isBlank(p.getInvoiceNo())
					|| StringUtils.equals(p.getInvoiceNo(), o.getInvoiceno())).collect(Collectors.toList());
			//3.1无匹配发票号的记录，插入新发票记录
			if (CollectionUtils.isEmpty(factlist)) {
				OpsPoDeliveryFact fact = new OpsPoDeliveryFact();
				fact.setOrderNo(o.getOrderno());
				fact.setItemNo(o.getItemno());
				fact.setSplitNo(o.getSplitno());
				fact.setModelNo(o.getModelno());
				fact.setQuantity(o.getQtyTrans());
				fact.setTranstypeCode(o.getTranstypeFact());
				fact.setInvoiceId(o.getInvoiceid());
				fact.setInvoiceNo(o.getInvoiceno());
				fact.setDeliveryTimeA(o.getDeliverytimeAFact());
				fact.setDeliveryTimeW(o.getDeliverytimeWFact());
                // bugid:19011 20251014
                if(StringUtils.isNotBlank(o.getSupplierid()) && "JP".equals(o.getSupplierid())){
                    fact.setDeliveryTimeA(o.getDeliverytimeWFact());
                }else {
                    fact.setDeliveryTimeA(o.getDeliverytimeAFact());
                }
				//bugid 20746
				if (StringUtils.isNotBlank(o.getSupplierid()) && "JP".equals(o.getSupplierid()) && (fact.getDeliveryTimeW() == null)) {
					//查询历史数据
					OpsPoDeliveryFact historyFact = getHistoryFact(o);
					if (historyFact != null) {
						if (fact.getDeliveryTimeW() == null) {
							fact.setDeliveryTimeA(historyFact.getDeliveryTimeA());
							fact.setDeliveryTimeW(historyFact.getDeliveryTimeW());
							fact.setRemark("parentId：" + historyFact.getId().toString());
						}
					}
				}
                opsPoDeliveryFactMapper.insertSelective(fact);
			}
			//3.2只有一条匹配记录，update更新invoiceId,invoiceNo,deliveryTimeA
			else if (factlist.size() == 1) {
				OpsPoDeliveryFact record = new OpsPoDeliveryFact();
				record.setId(factlist.get(0).getId());
				record.setInvoiceId(o.getInvoiceid());
				record.setInvoiceNo(o.getInvoiceno());
                // bugid:19011 20251014
                if(StringUtils.isNotBlank(o.getSupplierid()) && "JP".equals(o.getSupplierid())){
                    record.setDeliveryTimeA(o.getDeliverytimeWFact());
                }else {
                    record.setDeliveryTimeA(o.getDeliverytimeAFact());
                }
                record.setUpdateTime(new Date());
				opsPoDeliveryFactMapper.updateByPrimaryKeySelective(record);
			}
			//3.3多条匹配记录，相同发票号，update；无相同发票号，update第一条
			else {
				List<OpsPoDeliveryFact> ftemp = factlist.stream()
						.filter(p -> StringUtils.equals(p.getInvoiceNo(), o.getInvoiceno()))
						.collect(Collectors.toList());
				if (CollectionUtils.isEmpty(ftemp)) {
					OpsPoDeliveryFact record = new OpsPoDeliveryFact();
					record.setId(factlist.get(0).getId());
					record.setInvoiceId(o.getInvoiceid());
					record.setInvoiceNo(o.getInvoiceno());
                    // bugid:19011 20251014
                    if(StringUtils.isNotBlank(o.getSupplierid()) && "JP".equals(o.getSupplierid())){
                        record.setDeliveryTimeA(o.getDeliverytimeWFact());
                    }else {
                        record.setDeliveryTimeA(o.getDeliverytimeAFact());
                    }
					record.setUpdateTime(new Date());
					opsPoDeliveryFactMapper.updateByPrimaryKeySelective(record);
				} else {
					OpsPoDeliveryFact record = new OpsPoDeliveryFact();
					record.setId(ftemp.get(0).getId());
					record.setInvoiceId(o.getInvoiceid());
					record.setInvoiceNo(o.getInvoiceno());
                    // bugid:19011 20251014
                    if(StringUtils.isNotBlank(o.getSupplierid()) && "JP".equals(o.getSupplierid())){
                        record.setDeliveryTimeA(o.getDeliverytimeWFact());
                    }else {
                        record.setDeliveryTimeA(o.getDeliverytimeAFact());
                    }
                    record.setUpdateTime(new Date());
					opsPoDeliveryFactMapper.updateByPrimaryKeySelective(record);
				}
			}
		}
	}

	private OpsPoDeliveryFact getHistoryFact(PurchaseReplyInfo o) {
		OpsPoDeliveryFact historyFact = null;
		OpsPoDeliveryFactExample example = new OpsPoDeliveryFactExample();
		//20838 查询条件中将不带型号，因为历史数据中有型号为空的数据
		OpsPoDeliveryFactExample.Criteria criteria = example.createCriteria().andOrderNoEqualTo(o.getOrderno()).andItemNoEqualTo(o.getItemno())
				.andInvoiceIdIsNull();
		example.setOrderByClause("isnull(update_time, create_time) desc");
		if (o.getSplitno() == null) {
			criteria.andSplitNoIsNull();
		} else {
			criteria.andSplitNoEqualTo(o.getSplitno());
		}
		List<OpsPoDeliveryFact> historyFactList = opsPoDeliveryFactMapper.selectByExample(example);
		if (!historyFactList.isEmpty()) {
			historyFact = historyFactList.get(0);
		}
		return historyFact;
	}

}
