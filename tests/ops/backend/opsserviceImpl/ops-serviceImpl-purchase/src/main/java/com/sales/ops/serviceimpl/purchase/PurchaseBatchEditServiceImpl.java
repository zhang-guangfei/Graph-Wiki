package com.sales.ops.serviceimpl.purchase;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsPurchaseorderCancelLogMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inquiry.InquiryDlvDateEnum;
import com.sales.ops.dto.inquiry.InquiryQueryPurchaseDto;
import com.sales.ops.dto.inquiry.InquiryVerifyMsg;
import com.sales.ops.dto.purchase.PurchaseModifyApplyInfoDto;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.purchase.PurchaseBatchEditService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PurchaseBatchEditServiceImpl implements PurchaseBatchEditService {

    @Autowired
    private BasePoService basePoService;

    @Autowired
    private OpsPurchaseorderCancelLogMapper opsPurchaseorderCancelLogMapper;


    @Override
    public List<PurchaseModifyApplyInfoDto> selectRequestInfo(List<String> orderNos) throws OpsException {
        if (CollectionUtils.isEmpty(orderNos)) {
            throw Exceptions.OpsException("请求参数不能为空！");
        }
        List<PurchaseModifyApplyInfoDto> result = new ArrayList<>();
        PurchaseModifyApplyInfoDto purchaseModifyApplyInfoDto;
        for (String orderno : orderNos) {
            purchaseModifyApplyInfoDto = new PurchaseModifyApplyInfoDto();
            purchaseModifyApplyInfoDto.setOrderFno(orderno);
            if (orderno.contains("-")) {
                String[] split = orderno.split("-");
                purchaseModifyApplyInfoDto.setOrderno(split[0]);
                purchaseModifyApplyInfoDto.setItemno(Integer.parseInt(split[1]));
                if (split.length == 3) {
                    purchaseModifyApplyInfoDto.setSplititemno(Integer.parseInt(split[2]));
                }

            } else {
                if (orderno.length() != 10) {
                    throw Exceptions.OpsException("订单号错误，请修正单号！");
                }
                if (orderno.startsWith("99")) {
                    purchaseModifyApplyInfoDto.setOrderno(orderno.substring(0, 7));
                    purchaseModifyApplyInfoDto.setItemno(Integer.parseInt(orderno.substring(7)));
                } else {
                    if (StringUtils.equals("0", orderno.substring(orderno.length() - 1))) {
                        purchaseModifyApplyInfoDto.setOrderno(orderno.substring(0, 7));
                        purchaseModifyApplyInfoDto.setItemno(Integer.parseInt(orderno.substring(7)) / 10);
                    } else {
                        purchaseModifyApplyInfoDto.setOrderno(orderno.substring(0, 7));
                        purchaseModifyApplyInfoDto.setItemno(Integer.parseInt(orderno.substring(7, 9)));
                        purchaseModifyApplyInfoDto.setSplititemno(Integer.parseInt(orderno.substring(9)));
                    }
                }
            }
            OpsPurchaseorder order = basePoService.getPurchase(purchaseModifyApplyInfoDto.getOrderno(), purchaseModifyApplyInfoDto.getItemno(), purchaseModifyApplyInfoDto.getSplititemno());
            OpsRequestpurchase request = basePoService.getRequestPurchase(purchaseModifyApplyInfoDto.getOrderno(), purchaseModifyApplyInfoDto.getItemno(), purchaseModifyApplyInfoDto.getSplititemno());
            if (order == null && request == null) {
                throw Exceptions.OpsException("未找到采购单数据");
            }
            // bug 13815 采购单转订，申请是客户单号，实际采购单为合并采购单号，无法处理,根据请购单号查询采购单信息
            if (order == null && StringUtils.isNotBlank(request.getPoOrderNo())) { // PoOrderNo不等于空时，通过poorderno查询采购单信息
                order = basePoService.getPurchase(request.getPoOrderNo(), request.getPoItemNo(), request.getPoSplitNo());
            }
            if (order == null) {
                purchaseModifyApplyInfoDto.setStatecode("请购中");
                purchaseModifyApplyInfoDto.setSupplierid(request.getSupplierid());
                purchaseModifyApplyInfoDto.setModelno(request.getModelno());
                purchaseModifyApplyInfoDto.setQuantity(request.getQuantity());
                purchaseModifyApplyInfoDto.setNetweight(request.getNetweight() != null
                        ? request.getNetweight().multiply(new BigDecimal(request.getQuantity())) : null);
                purchaseModifyApplyInfoDto.setTranstype(request.getTranstype());
                purchaseModifyApplyInfoDto.setCustomerno(request.getCustomerno());
                purchaseModifyApplyInfoDto.setUserno(request.getUserno());
                purchaseModifyApplyInfoDto.setHopeexportdate(request.getHopeexportdate());
                purchaseModifyApplyInfoDto.setDeptno(request.getDeptno());
                purchaseModifyApplyInfoDto.setEndUser(request.getEndUser()); // bug19576 采购发单给老生管对于客户代码的传值
            } else {
                purchaseModifyApplyInfoDto.setStatecode(order.getStatecode());
                purchaseModifyApplyInfoDto.setDeptno(order.getDeptno());
                purchaseModifyApplyInfoDto.setSupplierid(order.getSupplierid());
                purchaseModifyApplyInfoDto.setModelno(order.getModelno());
                purchaseModifyApplyInfoDto.setQuantity(order.getQuantity());
                if (request != null) {
                    purchaseModifyApplyInfoDto.setNetweight(request.getNetweight() != null
                            ? request.getNetweight().multiply(new BigDecimal(request.getQuantity())) : null);
                }
                purchaseModifyApplyInfoDto.setTranstype(order.getTranstype());
                purchaseModifyApplyInfoDto.setCustomerno(order.getCustomerno());
                purchaseModifyApplyInfoDto.setUserno(order.getUserno());
                purchaseModifyApplyInfoDto.setHopeexportdate(order.getHopeexportdate());
                // bug 13815,当处于采购状态时，重新为单号赋值,预防出现合并采购情况
                purchaseModifyApplyInfoDto.setOrderno(order.getOrderno());
                purchaseModifyApplyInfoDto.setItemno(order.getItemno());
                purchaseModifyApplyInfoDto.setSplititemno(order.getSplititemno());
                //bug14745 订单修改和采购修改的增加手配号赋值
                purchaseModifyApplyInfoDto.setReplyorderno(order.getReplyorderno());
                purchaseModifyApplyInfoDto.setEndUser(request.getEndUser()); // bug19576 采购发单给老生管对于客户代码的传值
            }
            result.add(purchaseModifyApplyInfoDto);
        }
        return result;
    }

    @Override
    public List<InquiryQueryPurchaseDto> selectPurchaseOrder(List<String> orderNos) throws OpsException {
        if (CollectionUtils.isEmpty(orderNos)) {
            throw Exceptions.OpsException("请求参数不能为空！");
        }
        List<InquiryQueryPurchaseDto> result = new ArrayList<>();
        InquiryQueryPurchaseDto purchaseOrderReturn;
        for (String orderno : orderNos) {
            purchaseOrderReturn = new InquiryQueryPurchaseDto();
//			purchaseModifyApplyInfoDto.setOrderFno(orderno);
            if (orderno.contains("-")) {
                String[] split = orderno.split("-");
                // 校验 - 后面的子项号是否为空
                if (StringUtils.isBlank(split[1])) {
                    throw Exceptions.OpsException("订单错误，请修正单号！");
                }
                purchaseOrderReturn.setOrderno(split[0]);
                purchaseOrderReturn.setItemno(Integer.parseInt(split[1]));
                if (split.length == 3) {
                    purchaseOrderReturn.setSplititemno(Integer.parseInt(split[2]));
                }
            } else {
                if (orderno.length() != 10) {
                    throw Exceptions.OpsException("订单号输入错误，请修正单号！");
                }
                if (orderno.startsWith("99")) {
                    purchaseOrderReturn.setOrderno(orderno.substring(0, 7));
                    purchaseOrderReturn.setItemno(Integer.parseInt(orderno.substring(7)));
                } else {
                    if (StringUtils.equals("0", orderno.substring(orderno.length() - 1))) {
                        purchaseOrderReturn.setOrderno(orderno.substring(0, 7));
                        purchaseOrderReturn.setItemno(Integer.parseInt(orderno.substring(7)) / 10);
                    } else {
                        purchaseOrderReturn.setOrderno(orderno.substring(0, 7));
                        purchaseOrderReturn.setItemno(Integer.parseInt(orderno.substring(7, 9)));
                        purchaseOrderReturn.setSplititemno(Integer.parseInt(orderno.substring(9)));
                    }
                }
            }
            OpsPurchaseorder order = basePoService.getPurchase(purchaseOrderReturn.getOrderno(), purchaseOrderReturn.getItemno(), purchaseOrderReturn.getSplititemno());
//			if (order == null) {
//				// 校验是否合并采购
//				order = basePoService.getPurchaseByMergeRequestPurchase(purchaseOrderReturn.getOrderno(), purchaseOrderReturn.getItemno(), purchaseOrderReturn.getSplititemno());
//			}
            if (order == null) {
                // 查询是否被采购删单
                // 查询已删除数据
                OpsPurchaseorderCancelLogExample e = new OpsPurchaseorderCancelLogExample();
                if (purchaseOrderReturn.getSplititemno() == null) {
                    e.createCriteria().andOrdernoEqualTo(purchaseOrderReturn.getOrderno())
                            .andItemnoEqualTo(purchaseOrderReturn.getItemno()).andSplititemnoIsNull();
                } else {
                    e.createCriteria().andOrdernoEqualTo(purchaseOrderReturn.getOrderno())
                            .andItemnoEqualTo(purchaseOrderReturn.getItemno())
                            .andSplititemnoEqualTo(purchaseOrderReturn.getSplititemno());
                }
                List<OpsPurchaseorderCancelLog> tempC = opsPurchaseorderCancelLogMapper.selectByExample(e);
                if (tempC != null && tempC.size() > 0) {
//					throw Exceptions.OpsException(InquiryVerifyMsg.STATUSERROR.getDesc());
                    purchaseOrderReturn.setStatecode(tempC.get(0).getStatecode());
                } else {
                    throw Exceptions.OpsException(InquiryVerifyMsg.UNPURCHASE.getDesc());
                }
            } else {
                // 合并采购单号
//				if (order.getMergeflag()) {
//					// 查询请购单信息
//					OpsRequestpurchase requestpurchase = basePoService.getRequestPurchase(purchaseOrderReturn.getOrderno(), purchaseOrderReturn.getItemno(), purchaseOrderReturn.getSplititemno());
//					if (requestpurchase == null) {
//						throw Exceptions.OpsException("根据合并采购单，未找到对应的请购单数据");
//					}
//					purchaseOrderReturn.setCustomerno(requestpurchase.getCustomerno());
//					purchaseOrderReturn.setUserno(requestpurchase.getUserno());
//					purchaseOrderReturn.setQuantity(requestpurchase.getQuantity());
//				}
//				else {
                purchaseOrderReturn.setCustomerno(order.getCustomerno());
                purchaseOrderReturn.setUserno(order.getUserno());
                // 是否考虑返回一个 合并采购数量，以及请购数量
                purchaseOrderReturn.setQuantity(order.getQuantity());
//
                purchaseOrderReturn.setStatecode(order.getStatecode());
                purchaseOrderReturn.setOrdtype(order.getOrdtype());
                purchaseOrderReturn.setDeptno(order.getDeptno());
                purchaseOrderReturn.setSupplierid(order.getSupplierid());
                purchaseOrderReturn.setModelno(order.getModelno());
                purchaseOrderReturn.setTranstype(order.getTranstype());
                purchaseOrderReturn.setHopeexportdate(order.getHopeexportdate());
                purchaseOrderReturn.setDlvmoddate(order.getDlvmoddate());
                purchaseOrderReturn.setQtyreceive(order.getQtyreceive());
                purchaseOrderReturn.setReplyorderno(order.getReplyorderno());
                purchaseOrderReturn.setMergeflag(order.getMergeflag());
                purchaseOrderReturn.setPurchasetype(order.getPurchasetype()); // 新增返回采购单类别
                purchaseOrderReturn.setSmccode(order.getSmccode());
                // 查询请购表中的，出库区分进行校验
                List<OpsRequestpurchase> requestpurchases = basePoService.getRequestPurchaseByPurchase(order.getOrderno(), order.getItemno(), order.getSplititemno());
                if (!CollectionUtils.isEmpty(requestpurchases)) {
                    purchaseOrderReturn.setInventorytypecode(requestpurchases.get(0).getInventorytypecode());
                }
                // 查询ops_purchaseInvoice表中的qtytrans字段
                List<OpsPurchaseinvoice> purchaseinvoice = basePoService.getPurchaseInvoices(order);
                if (!CollectionUtils.isEmpty(purchaseinvoice)) {
                    purchaseOrderReturn.setQtytrans(purchaseinvoice.get(0).getQtytrans());
                    // bug14435,对应日本INQA修改,新增pono，；lineitem字段
                    purchaseOrderReturn.setPono(purchaseinvoice.get(0).getPono());
                    purchaseOrderReturn.setLineitem(purchaseinvoice.get(0).getLineitem());
                    // bug14490 INQA对接日本修改,需要新增传输字段
                    purchaseOrderReturn.setModelno(purchaseinvoice.get(0).getModelno());
                    purchaseOrderReturn.setSrcDeliveryTime(purchaseinvoice.get(0).getSrcDeliveryTime());
                    purchaseOrderReturn.setProducefactory(purchaseinvoice.get(0).getProducefactory());
                    purchaseOrderReturn.setReplyorderdate(purchaseinvoice.get(0).getReplyorderdate());
                    // 新增需要返回ops_purchaseInvoice表的replyOrderDate字段，以便前端进行校验
                }
                if (StringUtils.isNotBlank(purchaseOrderReturn.getSrcDeliveryTime()) && InquiryDlvDateEnum.dateValue(purchaseOrderReturn.getSrcDeliveryTime())) { // 日期格式的校验，校验SrcDeliveryTime是否为标准的日期格式
                    // 如果为日期时间格式，截取日期格式为yyyy-mm-dd返回
                    purchaseOrderReturn.setSrcDeliveryTime(purchaseOrderReturn.getSrcDeliveryTime().substring(0, 10));
                    purchaseOrderReturn.setDlvmoddate(DateUtil.stringToDate(purchaseOrderReturn.getSrcDeliveryTime()));
                } else if (StringUtils.isNotBlank(purchaseOrderReturn.getSrcDeliveryTime()) && !InquiryDlvDateEnum.dateValue(purchaseOrderReturn.getSrcDeliveryTime())) {
                    List<String> specialDateList = InquiryDlvDateEnum.specialDateList();
                    // 将日本返信的6位数字250327及241199，按照日期格式转换为2025-03-27/2024-11-99来显示，特殊的返信 如222222、444444不进行转换
                    if (purchaseOrderReturn.getSrcDeliveryTime().length() == 6 && !StringUtils.right(purchaseOrderReturn.getSrcDeliveryTime(),2).equals("99")  && !specialDateList.contains(purchaseOrderReturn.getSrcDeliveryTime())) {
                        purchaseOrderReturn.setSrcDeliveryTime(DateUtil.convertToYYYYMMDD(purchaseOrderReturn.getSrcDeliveryTime()));
                        purchaseOrderReturn.setDlvmoddate(DateUtil.stringToDate(purchaseOrderReturn.getSrcDeliveryTime()));
                    }
                } else if (StringUtils.isBlank(purchaseOrderReturn.getSrcDeliveryTime()) && purchaseOrderReturn.getDlvmoddate() != null) {
                    // 2024-07-04 返信纳期字段，新数据需要改用取purchaseinvoice的src_delivery_time字段，当src_delivery_time为空时，需要取order的Dlvmoddate字段
                    // 2.对于特殊交货期的校验，需要组合好再返回给调用方，将历史的9999-09-09都恢复成 999999字符串格式返回
                    purchaseOrderReturn.setSrcDeliveryTime(InquiryDlvDateEnum.convertDlvDate(order.getDlvmoddate()));
                }
            }
            purchaseOrderReturn.setEndUser(order.getEndUser()); // bug19576 采购表增加字段end_user
            result.add(purchaseOrderReturn);
        }
        return result;
    }

}
