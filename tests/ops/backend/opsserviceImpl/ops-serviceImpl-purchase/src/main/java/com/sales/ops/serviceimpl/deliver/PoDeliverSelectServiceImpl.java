package com.sales.ops.serviceimpl.deliver;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.PoDeliverDao;
import com.sales.ops.dto.poDeliver.PoDeliverSelectDto;
import com.sales.ops.dto.poDeliver.SelectFilter;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.enums.PoReplyDateStrEnum;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.service.deliver.PoDeliverSelectService;
import com.sales.ops.service.po.BasePoService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PoDeliverSelectServiceImpl implements PoDeliverSelectService {

    @Autowired
    private PoDeliverDao poDeliverDao;
    @Autowired
    private BasePoService basePoService;

    @Override
    public PageInfo<PoDeliverSelectDto> selectDeliverInfo(PageModel<SelectFilter> info) throws OpsException {
        if (info.getCondition() == null) {
            return null;
        }
        SelectFilter condition = info.getCondition();
        if (StringUtils.isNotBlank(condition.getPono())) {
            //按照逗号拆分
            String[] poNoList = condition.getPono().split("\n");
            List<OrderNoInfo> orderNoInfoList = new ArrayList<>();
            condition.setOrderNoInfoList(orderNoInfoList);
            for (int i = 0; i < poNoList.length; i++) {
                // 去空格
                String orderFno = poNoList[i].trim();
                if (orderFno.contains("-")) {
                    String[] split = orderFno.split("-");
                    boolean continueFlag = false;
                    for (int j = 0; j < split.length; j++) {
                        if (StringUtils.isBlank(split[j].trim())) {
                            continueFlag = true;
                            break;
                        }
                        split[j] = split[j].trim();
                    }
                    if (continueFlag) {
                        continue;
                    }
                    //去完空格后，
                    if (split.length == 2) {
                        orderNoInfoList.add(new OrderNoInfo(split[0].trim(), split[1].trim(), null));
                    } else if (split.length == 3) {
                        orderNoInfoList.add(new OrderNoInfo(split[0].trim(), Integer.valueOf(split[1].trim()), Integer.valueOf(split[2].trim())));
                    }
                } else {
                    // 去空格
                    if (StringUtils.isBlank(orderFno.trim())) {
                        continue;
                    }
                    orderNoInfoList.add(new OrderNoInfo().setOrderNo(orderFno.trim()));
                }
            }
        }
        //poNoList大于500条就报错
        if (CollectionUtils.isNotEmpty(condition.getOrderNoInfoList()) && condition.getOrderNoInfoList().size() > 500) {
            throw Exceptions.OpsException("最多查询500条采购单号数据");
        }
        if (StringUtils.isNotBlank(condition.getReplyOrderNo())) {
            //按照逗号拆分
            String[] replyOrderNoList = condition.getReplyOrderNo().split("\n");
            //去空格
            for (int i = 0; i < replyOrderNoList.length; i++) {
                replyOrderNoList[i] = replyOrderNoList[i].trim();
            }
            condition.setReplyOrderNoList(Arrays.asList(replyOrderNoList));
        }

        if (StringUtils.equals("", condition.getStateCode())) {
            condition.setStateCode(null);
        }
        PageInfo<PoDeliverSelectDto> pageInfo = PageHelper.startPage(info.getPageNumber(), info.getPageSize())
                .doSelectPageInfo(() -> poDeliverDao.selectDeliverInfo(condition));
        return pageInfo;
    }

    @Override
    public List<PoDeliverSelectDto> selectDetailInfo(List<PoDeliverSelectDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<OpsPoDeliveryPlan> planlist = poDeliverDao.selectPlanByOrderNo(list);
        List<OpsPoDeliveryFact> factlist = poDeliverDao.selectFactByOrderNo(list);
        List<OpsImpdata> impdatalist = poDeliverDao.selectImpdataByOrderNo(list);
        List<ImpInvoiceDetail> impdlist = poDeliverDao.selectImpDetailByOrderNo(list);

        list.forEach(a -> {
            if (a.getDlvModDate3() != null) {
                a.setDlvModDate(a.getDlvModDate3());
                a.setDlvModDateTime(a.getDlvModDate3Time());
            } else if (a.getDlvModDate2() != null) {
                a.setDlvModDate(a.getDlvModDate2());
                a.setDlvModDateTime(a.getDlvModDate2Time());
            } else if (a.getDlvModDate1() != null) {
                a.setDlvModDate(a.getDlvModDate1());
                a.setDlvModDateTime(a.getDlvModDate1Time());
            }
            List<OpsRequestpurchase> requestList = basePoService.getRequestPurchaseByPurchase(a.getOrderNo(), a.getItemNo(), a.getSplitItemNo());
            if (CollectionUtils.isNotEmpty(requestList)) {
                OpsRequestpurchase requestpurchase = requestList.get(0);
                a.setRequestWarehouseId(requestpurchase.getRequestwarehouseid());
                a.setPurchaseWarehouseId(requestpurchase.getPurchasewarehouseid());
            }
            Integer produceQty = a.getQuantity();
            String produceInfo = "";
            List<OpsPoDeliveryPlan> plantemp = planlist.stream()
                    .filter(p -> StringUtils.equals(p.getOrderNo(), a.getOrderNo())
                            && p.getItemNo().compareTo(a.getItemNo()) == 0
                            && ((p.getSplitNo() == null && a.getSplitItemNo() == null)
                            || (p.getSplitNo() == a.getSplitItemNo())))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(plantemp)) {
                OpsPoDeliveryPlan plan = plantemp.stream()
                        .sorted(Comparator.comparing(OpsPoDeliveryPlan::getCreateTime, Comparator.reverseOrder()))
                        .collect(Collectors.toList()).get(0);
                a.setDeliveryPlanH(plan.getDeliveryH());
                a.setDeliveryPlanW(plan.getDeliveryW());
                // bug14939返信描述
                String desc = "";
                for (PoReplyDateStrEnum i : PoReplyDateStrEnum.values()) {
                    if (StringUtils.equals(i.getCode(), plan.getSrcDeliveryTime())) {
                        desc = i.getCodeName();
                    }
                }
                // bug14947 增加src字段不为空的判断
                if (StringUtils.isBlank(desc) && plan.getSrcDeliveryTime() != null
                        && plan.getSrcDeliveryTime().endsWith("99") && plan.getLatestDeliveryTime() != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    desc = "最晚" + formatter.format(plan.getLatestDeliveryTime());
                }
                if (StringUtils.isNotBlank(desc)) {
                    a.setDeliveryPlanA(a.getDeliveryPlanA() + "(" + desc + ")");
                }
                for (OpsPoDeliveryPlan p : plantemp) {
                    produceInfo += p
                            .getQuantity() == null
                            ? a.getQuantity().toString()
                            : p.getQuantity().toString()
                            + "个的返信为:" + (p.getSrcDeliveryTime() == null
                            ? p.getLatestDeliveryTime().toString() : p.getSrcDeliveryTime())
                            + ";";
                }
            }
            List<OpsPoDeliveryFact> facttemp = factlist.stream()
                    .filter(p -> StringUtils.equals(p.getOrderNo(), a.getOrderNo())
                            && p.getItemNo().compareTo(a.getItemNo()) == 0
                            && ((p.getSplitNo() == null && a.getSplitItemNo() == null)
                            || (p.getSplitNo() == a.getSplitItemNo())))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(facttemp)) {
                // bug 20720 优先判断updateTime的值，如果updateTime为空，则用createTime
                OpsPoDeliveryFact fact = facttemp.stream()
                        .max(Comparator.comparing(
                                (OpsPoDeliveryFact p) -> p.getUpdateTime() != null ? p.getUpdateTime() : p.getCreateTime(),
                                Comparator.nullsFirst(Date::compareTo)
                        ))
                        .orElse(null);
                a.setDeliveryFactH(fact.getDeliveryTimeH());
                a.setDeliveryFactW(fact.getDeliveryTimeW());
                a.setDeliveryFactA(fact.getDeliveryTimeA());
                a.setInvoiceno(fact.getInvoiceNo());
                List<ImpInvoiceMaster> impInvoiceMasters = poDeliverDao.selectImpInvoiceMasterByInvoiceNo(fact.getInvoiceNo(),fact.getInvoiceId());
                if(CollectionUtils.isNotEmpty(impInvoiceMasters)){
                    ImpInvoiceMaster impInvoiceMaster = impInvoiceMasters.get(0);
                    //供应商发票日期
                    a.setInvoiceDate(impInvoiceMaster.getInvoiceDate());
                }
                List<OpsPoInvoice> opsPoInvoices = poDeliverDao.selectOpsPoInvoiceByInvoiceNo(fact.getInvoiceNo(), fact.getInvoiceId());
                if (CollectionUtils.isNotEmpty(opsPoInvoices)) {
                    OpsPoInvoice opsPoInvoice = opsPoInvoices.get(0);
                    //实物实际入库仓库
                    a.setReceiveWarehouseId(opsPoInvoice.getReceiveWarehouseCode());
                }
                // TODO 供应商仓库处理中，有W无A
                Integer qtyW = facttemp.stream()
                        .filter(p -> p.getDeliveryTimeA() == null && p.getDeliveryTimeW() != null)
                        .mapToInt(OpsPoDeliveryFact::getQuantity).sum();
                a.setwQty(qtyW);
                if (qtyW != null) {
                    produceQty = produceQty - qtyW;
                }
            } else {
                a.setwQty(0);
            }

            // 判断是否为已完成
            if (StringUtils.equals(PurchaseOrderStatusEnum.YIWANCHENG, a.getStateCode())) {
                a.setFinishQty(a.getQuantity());
                a.setwQty(null);
            } else {
                // 运输中imp_detail
                Integer trans = impdlist.stream()
                        .filter(p -> StringUtils.equals(p.getOrderNo(), a.getOrderNo())
                                && p.getItemNo().compareTo(a.getItemNo()) == 0
                                && ((p.getSplitItemNo() == null && a.getSplitItemNo() == null)
                                || (p.getSplitItemNo() == a.getSplitItemNo())))
                        .mapToInt(ImpInvoiceDetail::getQuantity).sum();
                a.setTransQty(trans);
                if (trans != null) {
                    produceQty = produceQty - trans;
                }
                // 收货中、已完成数量ops_impdata
                List<OpsImpdata> itemp = impdatalist
                        .stream().filter(
                                p -> StringUtils.equals(p.getOrderno(), a.getOrderNo())
                                        && p.getItemno().compareTo(a.getItemNo()) == 0
                                        && ((p.getSplititemno() == null && a.getSplitItemNo() == null)
                                        || (p.getSplititemno() == a.getSplitItemNo())))
                        .collect(Collectors.toList());
                Integer finishqty = itemp.stream().filter(p -> StringUtils.equals("2", p.getStatecode()))
                        .mapToInt(OpsImpdata::getQuantity).sum();
                a.setFinishQty(finishqty);
                if (finishqty != null) {
                    produceQty = produceQty - finishqty;
                }
                Integer reciving = itemp.stream().filter(p -> !StringUtils.equals("2", p.getStatecode()))
                        .mapToInt(OpsImpdata::getQuantity).sum();
                a.setReceivingQty(reciving);
                if (reciving != null) {
                    produceQty = produceQty - reciving;
                }
                // 生产中数量
                if (produceQty > 0) {
                    a.setProduceQty(produceQty);
                } else {
                    a.setProduceQty(0);
                }
                if (StringUtils.isNotBlank(produceInfo)) {
                    a.setProduceQtyInfo(produceInfo);
                }
            }

        });
        return list;
    }

}
