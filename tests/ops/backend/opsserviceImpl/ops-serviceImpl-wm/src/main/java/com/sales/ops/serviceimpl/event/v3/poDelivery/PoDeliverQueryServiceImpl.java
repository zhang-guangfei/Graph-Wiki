package com.sales.ops.serviceimpl.event.v3.poDelivery;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.sales.ops.db.entity.ImpInvoiceMaster;
import com.sales.ops.db.entity.OpsPoDeliveryFact;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.extdao.PoDeliverQueryDao;
import com.sales.ops.dto.poDeliver.PoDeliverQueryDto;
import com.sales.ops.service.po.BasePoService;
import com.smc.smccloud.core.model.enums.OPSTransTypeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 采购发货查询服务实现类 (严格按照 DTO 注释实现)
 *
 * @author SMC892N
 */
@Service
public class PoDeliverQueryServiceImpl implements PoDeliverQueryService {

    @Autowired
    private PoDeliverQueryDao poDeliverQueryDao;
    @Autowired
    private BasePoService basePoService;

    @Override
    public PoDeliverQueryDto getDeliverInfoByOrderNo(String orderno, Integer itemno, Integer splitno) {
        if (StringUtils.isBlank(orderno)) {
            return null;
        }
        PoDeliverQueryDto result = new PoDeliverQueryDto();

        // 步骤 1: 查询主表数据 (ops_purchaseInvoice)
        fillPurchaseInvoice(result, orderno, itemno, splitno);

        // 步骤 2: 查询发票主表信息 (imp_invoice_master)
        fillInvoiceMaster(result, result.getInvoiceNo(), result.getInvoiceId());

        // 步骤 3: 查询发票明细总数量 (imp_invoice_detail)
        fillInvoiceDetailQuantity(result, orderno, itemno, splitno, result.getInvoiceNo(), result.getInvoiceId());

        // 步骤 4: 查询发货实绩信息 (ops_po_delivery_fact)
        fillDeliveryFact(result, orderno, itemno, splitno);
        return result;
    }

    private void fillPurchaseInvoice(PoDeliverQueryDto dto, String orderno, Integer itemno, Integer splitno) {
        //stateCode, supplierId, transType, hopeExportDate, replyOrderNo, replyOrderDate, purchaseDate,
        // produceFactory, replyExportDate, beginProduceDate, invoiceNo, customsDate
        List<OpsPurchaseinvoice> opsPurchaseinvoices = poDeliverQueryDao.selectDeliverInfoByOrderNo(orderno, itemno, splitno);
        if (CollectionUtils.isEmpty(opsPurchaseinvoices)) {
            return;
        }
        OpsPurchaseinvoice opsPurchaseinvoice = opsPurchaseinvoices.get(0);
        dto.setOrderNo(opsPurchaseinvoice.getOrderno());
        dto.setItemNo(opsPurchaseinvoice.getItemno());
        dto.setSplitNo(opsPurchaseinvoice.getSplititemno());
        dto.setStateCode(opsPurchaseinvoice.getStatecode());
        dto.setSupplierId(opsPurchaseinvoice.getSupplierid());
        if (StringUtils.isNotBlank(opsPurchaseinvoice.getTranstype())) {
            String name = OPSTransTypeEnum.getNameByCode(opsPurchaseinvoice.getTranstype());
            dto.setTransType(name);
        }
        dto.setHopeExportDate(opsPurchaseinvoice.getHopeexportdate());
        dto.setReplyOrderNo(opsPurchaseinvoice.getReplyorderno());
        dto.setReplyOrderDate(opsPurchaseinvoice.getReplyorderdate());
        dto.setPurchaseDate(opsPurchaseinvoice.getPurchasedate());
        dto.setProduceFactory(opsPurchaseinvoice.getProducefactory());
        dto.setReplyExportDate(opsPurchaseinvoice.getReplyexportdate());
        dto.setBeginProduceDate(opsPurchaseinvoice.getBeginproducedate());
        dto.setInvoiceNo(opsPurchaseinvoice.getInvoiceno());
        dto.setInvoiceId(opsPurchaseinvoice.getInvoiceid());
        dto.setCustomsDate(opsPurchaseinvoice.getCustomsDate());
        //查询采购单状态
        OpsPurchaseorder purchase = basePoService.getPurchase(orderno, itemno, splitno);
        if (purchase != null) {
            dto.setStateCode(purchase.getStatecode());
        }
    }


    private void fillInvoiceMaster(PoDeliverQueryDto dto, String invoiceNo, Long invoiceId) {
        if (StringUtils.isBlank(invoiceNo) || invoiceId == null) {
            return;
        }
        List<ImpInvoiceMaster> invoiceMasters = poDeliverQueryDao.selectImpInvoiceMasterByInvoiceNo(invoiceNo, invoiceId);
        if (CollectionUtils.isEmpty(invoiceMasters)) {
            return;
        }
        ImpInvoiceMaster invoiceMaster = invoiceMasters.get(0);
        // 来源：imp_invoice_master.prearrive_date (预计到货日)
        dto.setPreArriveDate(invoiceMaster.getPrearriveDate());
        // 来源：imp_invoice_master.confirm_date
        dto.setConfirmDate(invoiceMaster.getConfirmDate());
        // 来源：imp_invoice_master.status
        dto.setInvoiceStatus(invoiceMaster.getStatus());
        // 来源：imp_invoice_master.arrive_date
        dto.setArriveDate(invoiceMaster.getArriveDate());
        // 来源：imp_invoice_master.arrived_warehouse_code
        dto.setArrivedWarehouseCode(invoiceMaster.getArrivedWarehouseCode());
        // 来源：imp_invoice_master.ship_date
        dto.setShipDate(invoiceMaster.getShipDate());
    }


    private void fillInvoiceDetailQuantity(PoDeliverQueryDto dto, String orderno, Integer itemno, Integer splitno,
                                           String invoiceNo, Long invoiceId) {
        if (dto == null || StringUtils.isBlank(invoiceNo) || invoiceId == null) {
            return;
        }
        Integer totalQuantity = poDeliverQueryDao.selectImpDetailTotalQuantity(orderno, itemno, splitno, invoiceNo, invoiceId);
        if (totalQuantity != null && totalQuantity > 0) {
            dto.setQuantity(totalQuantity);
        }
    }


    private void fillDeliveryFact(PoDeliverQueryDto dto, String orderno, Integer itemno, Integer splitno) {
        if (dto == null) {
            return;
        }

        // 查询最新发货实绩记录
        OpsPoDeliveryFact latestFact = poDeliverQueryDao.selectLatestFactByOrderNo(orderno, itemno, splitno);
        if (latestFact == null) {
            return;
        }

        // 根据 DTO 注释赋值实际完工日、入库日、出库日
        // 来源：ops_po_delivery_fact.delivery_time_H/W/A
        dto.setDeliveryTimeH(latestFact.getDeliveryTimeH());
        dto.setDeliveryTimeW(latestFact.getDeliveryTimeW());
        dto.setDeliveryTimeA(latestFact.getDeliveryTimeA());
    }
}
