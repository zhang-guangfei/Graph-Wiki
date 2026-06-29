package com.sales.ops.serviceimpl.inventory.factory;

import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.TransOrder;
import org.apache.commons.lang.StringUtils;

/**
 * @author C12961
 * @date 2023/3/3 11:20
 */
public class PropertyFactory {

    /**
     * @description 采购接单调用，用户创建生产中库存时查询订单号
     * @author C12961
     * @date 2023/3/3 14:29
     */
    public static OpsInventoryProperty fromRequestPurchase(OpsRequestpurchase request) {
        String customerNo = StringUtils.isNotBlank(request.getUserno()) ? request.getUserno() : request.getGroupcustomerno();
        OpsInventoryProperty property = new OpsInventoryProperty();
        property.setInventoryTypeCode(request.getInventorytypecode());
        property.setCustomerNo(request.getCustomerno());
        property.setGroupCustomerNo(customerNo);
        property.setPpl(request.getPpl());
        property.setProjectCode(request.getProjectcode());
        return property;
    }

    public static OpsInventoryProperty fromTransOrder(TransOrder transOrder) {
        OpsInventoryProperty property = new OpsInventoryProperty();
        property.setInventoryTypeCode(transOrder.getToInventoryTypeCode());
        property.setCustomerNo(transOrder.getToCustomerNo());
        property.setPpl(transOrder.getToPpl());
        property.setProjectCode(transOrder.getToProjectCode());
        property.setGroupCustomerNo(transOrder.getToGroupCustomerNo());
        property.setSalesInfoNo(transOrder.getToSalesInfoNo());
        return property;
    }

    public static OpsInventoryProperty createCondition(String typeCode, String customerNo, String ppl, String pj, String groupCustomer, String salesInfo) {
        OpsInventoryProperty property = new OpsInventoryProperty();
        property.setInventoryTypeCode(typeCode);
        property.setCustomerNo(customerNo);
        property.setPpl(ppl);
        property.setProjectCode(pj);
        property.setGroupCustomerNo(groupCustomer);
        property.setSalesInfoNo(salesInfo);
        return property;
    }

}
