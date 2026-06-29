package com.smc.smccloud.core.rabbitmq.constants;

/**
 * Author: B90034
 * Date: 2022-01-06 09:42
 * Description:
 */
public class Constants {

    /**
     * system
     */
    public static final String OPS = "OPS";
    public static final String SMS = "SMS";

    /**
     * dataType
     */
    public static final String OPS_ORDER = "ops:rabbitmq:order:";
    public static final String OPS_INVOICE = "ops:rabbitmq:invoice:";
    public static final String ORDER = "sale:rabbitmq:order:";
    public static final String CUSTOMERINSTOCKTRANSFER = "sale:rabbitmq:customerInStockTransfer:";
    public static final String SUBTREASURY = "sale:rabbitmq:subTreasury:";
    public static final String PREOCCUPIEDINVENTORY = "sale:rabbitmq:preOccupiedInventory:";
    public static final String CUSTOMERINSTOCK = "sale:rabbitmq:customerInStock:";

    /**
     * x-flag
     */
    public static final String OPS_ORDER_LOG = "ops-order-log";
    public static final String OPS_ORDER_STATE = "ops-order-state";
    public static final String OPS_ORDER_RECEIVE = "ops-order-receive";
    public static final String OPS_INVOICE_PROSECC = "ops-invoice-process";

    public static final String ERP_ORDER_CANCEL = "erp-order-cancel";
    public static final String ERP_ORDER_CANCEL_RETURN = "erp-order-cancel-return";

    public static final String ERP_ORDER_CREATE = "erp-order-create"; // 订单创建(发单)
    public static final String ERP_ORDER_CREATE_RETURN = "erp-order-create-return";

    public static final String ERP_ORDER_EDIT = "erp-order-edit"; // 修改交易条款 (修改订单)
    public static final String ERP_ORDER_EDIT_RERURN = "erp-order-edit-return"; // 修改订单结果返回

    public static final String ERP_ORDER_DELIVERY_MODIFY = "erp-order-delivery-modify"; // 修改订单货期
    public static final String ERP_ORDER_DELIVERY_MODIFY_RERURN = "erp-order-delivery-modify-return";


    public static final String ERP_CUSTOMERINSTOCKTRANSFER_CREATE = "erp-customerInStockTransfer-create"; // 调库
    public static final String ERP_CUSTOMERINSTOCKTRANSFER_CREATE_RETURN = "erp-customerInStockTransfer-create-return"; // 调库结果返回

    public static final String ERP_SUBTREASURY_CREATE = "erp-subTreasury-create"; // 分库补库创建
    public static final String ERP_SUBTREASURY_CREATE_RETURN = "erp-subTreasury-create-return"; // 分库补库创建结果返回

    public static final String ERP_PREOCCUPIEDINVENTORY_CREATE = "erp-preOccupiedInventory-create"; // 预占库存
    public static final String ERP_PREOCCUPIEDINVENTORY_CREATE_RETURN = "erp-preOccupiedInventory-create-return"; // 预占库存结果返回

    public static final String ERP_PREOCCUPIEDINVENTORY_DELETE = "erp-preOccupiedInventory-delete"; // 取消预占库存
    public static final String ERP_PREOCCUPIEDINVENTORY_DELETE_RETURN = "erp-preOccupiedInventory-delete-return"; // 取消预占库存结果返回

    public static final String ERP_CUSTOMERINSTOCK_CREATE = "erp-customerInStock-create"; // 客户在库创建
    public static final String ERP_CUSTOMERINSTOCK_CREATE_RETURN = "erp-customerInStock-create-return"; // 客户在库创建结果返回

}
