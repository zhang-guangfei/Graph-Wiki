package com.sales.ops.serviceimpl.purchase;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.sales.ops.db.entity.*;
import com.sales.ops.dto.po.core.TransTypeDto;
import com.sales.ops.service.core.TransService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.sales.ops.db.dao.OpsPoTranstypeConfigMapper;
import com.sales.ops.dto.purchase.BaseDataDto;
import com.sales.ops.dto.purchase.ModifyPurchaseDto;
import com.sales.ops.enums.PurchaseOrderStatusEnum;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.purchase.PurchaseModifyService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;

@Service
@Transactional
public class PurchaseModifyServiceImpl implements PurchaseModifyService {

	@Autowired
	private BasePoService basePoService;

	@Autowired
	private OpsPoTranstypeConfigMapper opsPoTranstypeConfigMapper;

	@Autowired
	private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Autowired
    private TransService transService;


    // 针对日本采购单修改申请返回的数据，因此运输方式不包含陆运
    @Override
    public List<ModifyPurchaseDto> getPurchase(List<String> orderNos, String businessCode) {
        if (CollectionUtils.isEmpty(orderNos) || StringUtils.isBlank(businessCode)) {
            return null;
        }
        List<ModifyPurchaseDto> list = new ArrayList<ModifyPurchaseDto>();
        for (String i : orderNos) {
            ModifyPurchaseDto dto = new ModifyPurchaseDto();
            dto.setOrderFno(i);
            if (i.contains("-")) {
                String no = i.substring(0, i.lastIndexOf("-"));
                if (no.contains("-")) {
                    dto.setOrderNo(no.substring(0, no.lastIndexOf("-")));
                    dto.setItemNo(Integer.parseInt(no.substring(no.lastIndexOf("-") + 1)));
                    dto.setSplitNo(Integer.parseInt(i.substring(i.lastIndexOf("-") + 1)));
                } else {
                    dto.setOrderNo(no);
                    dto.setItemNo(Integer.parseInt(i.substring(i.lastIndexOf("-") + 1)));
                }
            } else {
                if (i.length() != 10) {
                    dto.setReset(false);
                    dto.setRemark("订单号输入错误，不可变更!");
                    list.add(dto);
                    continue;
                }
                if (i.startsWith("99")) {
                    dto.setOrderNo(i.substring(0, 7));
                    dto.setItemNo(Integer.parseInt(i.substring(7)));
                } else {
                    if (StringUtils.equals("0", i.substring(i.length() - 1))) {
                        dto.setOrderNo(i.substring(0, 7));
                        dto.setItemNo(Integer.parseInt(i.substring(7)) / 10);
                    } else {
                        dto.setOrderNo(i.substring(0, 7));
                        dto.setItemNo(Integer.parseInt(i.substring(7, 9)));
                        dto.setSplitNo(Integer.parseInt(i.substring(9)));
                    }
                }
            }
            OpsPurchaseorder order = basePoService.getPurchase(dto.getOrderNo(), dto.getItemNo(), dto.getSplitNo());
            OpsRequestpurchase request = basePoService.getRequestPurchase(dto.getOrderNo(), dto.getItemNo(),
                    dto.getSplitNo());
            if (order == null) {
                if (request == null) {
                    dto.setReset(false);
                    dto.setRemark("无采购数据，不可变更!");
                    list.add(dto);
                    continue;
                } else {
                    dto.setSupplierId(request.getSupplierid());
                    dto.setModelNo(request.getModelno());
                    dto.setQty(request.getQuantity());
                    dto.setPurchaseDate(request.getRequesttime());
                    dto.setNetWeight(request.getNetweight() != null
                            ? request.getNetweight().multiply(new BigDecimal(request.getQuantity())) : null);
                    dto.setTransType(request.getTranstype());
                    dto.setCustomerNo(request.getCustomerno());
                    dto.setUserNo(request.getUserno());
                    dto.setHopeExportDate(request.getHopeexportdate());
                    dto.setHopeDeliveryDate(request.getHopedeliverydate());
                    if (StringUtils.equals("JP", request.getSupplierid())) {
                        dto.setReset(true);
                    } else {
                        dto.setReset(false);
                        dto.setRemark("非采购日本，不可变更!");
                    }
                }
            } else {
                dto.setSupplierId(order.getSupplierid());
                dto.setModelNo(order.getModelno());
                dto.setQty(order.getQuantity());
                dto.setPurchaseDate(order.getPurchasedate());
                if(Objects.nonNull(request)){
                    dto.setNetWeight(request.getNetweight() != null
                            ? request.getNetweight().multiply(new BigDecimal(request.getQuantity())) : null);
                }
                dto.setTransType(order.getTranstype());
                dto.setCustomerNo(order.getCustomerno());
                dto.setUserNo(order.getUserno());
                dto.setHopeExportDate(order.getHopeexportdate());
                dto.setDlvModDate(order.getDlvmoddate());
                dto.setHopeDeliveryDate(order.getHopedeliverydate());
                if (StringUtils.equals("T", businessCode)) {
                    // 变更运输方式：请购处理的状态或采购单存在P库存的状态
                    List<OpsPurchaseinvoice> invoice = basePoService.getPurchaseInvoices(order);
                    if (invoice.get(0).getQtytrans() != null && invoice.get(0).getQtytrans() >= order.getQuantity()) {
                        dto.setReset(false);
                        dto.setRemark("供应商已全部发货，不可变更!");
                    } else {
                        dto.setReset(true);
                    }
                } else {
                    // 变更指定出荷日：请购处理的状态或者采购单运输中之前的状态
                    if (StringUtils.equals(order.getStatecode(), PurchaseOrderStatusEnum.YIFASONG)
                            || StringUtils.equals(order.getStatecode(), PurchaseOrderStatusEnum.YIJIEDAN)) {
                        dto.setReset(true);
                    } else {
                        dto.setReset(false);
                        dto.setRemark("采购单已在运输中，不可变更!");
                    }
                }
                if (!StringUtils.equals("JP", order.getSupplierid())) {
                    dto.setReset(false);
                    dto.setRemark("非采购日本，不可变更!");
                }
            }
            // bugid:18836 c14717 20251218
            String warehouseCode = "";
            String modelNo = dto.getModelNo();
            Integer qty = dto.getQty();
            String ordType = "";
            String supplierId = dto.getSupplierId();
            if(Objects.nonNull(order)){
                warehouseCode = order.getHopereceivewarehouse();
                ordType = order.getOrdtype();
            } else {
                warehouseCode = request.getRequestwarehouseid();
                if(StringUtils.isNotBlank(request.getPurchasewarehouseid())){
                    warehouseCode = request.getPurchasewarehouseid();
                }
            }
            List<TransTypeDto> transIds = transService.getTransIds(supplierId, warehouseCode, modelNo, qty, ordType,false);
            // bug11808、bug12472增加实体显示code、codename，并返回
            List<BaseDataDto> tDto = new ArrayList<BaseDataDto>();
            if(!CollectionUtils.isEmpty(transIds)){
                List<TransTypeDto> uniqueList = new ArrayList<>(transIds.stream()
                        .collect(Collectors.toMap(
                                TransTypeDto::getTransId,      // key：transId
                                tdto -> tdto,                    // value：整个对象
                                (existing, replacement) -> existing // 冲突时保留第一个
                        ))
                        .values());
                for(TransTypeDto obj : uniqueList){
                    BaseDataDto pt = new BaseDataDto();
                    pt.setCode(obj.getTransId());
                    pt.setCodeName(obj.getTransName());
                    tDto.add(pt);
                }
            }
            dto.setAvailableTransType(tDto);
            list.add(dto);
        }
        return list;
    }

}
