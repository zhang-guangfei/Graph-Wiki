package com.smc.smccloud.core.rabbitmq;

public class RabbitMqConstants {
    /**
     * 随机数，8位
     */
    public static final int RABBITMQ_RANDOM = 8;

    /**
     * 有效期15日=3600 * 24 * 15
     */
    public static final long RABBITMQ_REDIS_EXPIRE = 1296000;

    /**
     * RabbitMq在redis中的前缀
     */
    public static final String RABBITMQ_REDIS_PREFIX = "ops:rabbitmq:";

    /**
     * 待生产
     */
    public static final String RABBITMQ_REDIS_PRODUCER = "producer:";

    /**
     * 生产失败
     */
    public static final String RABBITMQ_REDIS_PRODUCER_FAILURE = RABBITMQ_REDIS_PRODUCER + "failure:";

    /**
     * 待消费
     */
    public static final String RABBITMQ_REDIS_CONSUMER = "consumer:";

    /**
     * 消费成功
     */
    public static final String RABBITMQ_REDIS_CONSUMER_SUCCESS = RABBITMQ_REDIS_CONSUMER + "success:";

    /**
     * 消费失败
     */
    public static final String RABBITMQ_REDIS_CONSUMER_FAILURE = RABBITMQ_REDIS_CONSUMER + "failure:";

    /**
     * 订单
     */
    public static final String ORDER = RABBITMQ_REDIS_PREFIX + "order:";

    /**
     * 订单修改
     */
    public static final String ORDER_MODIFY = ORDER + "modify:";

    /**
     * 出库单修改
     */
    public static final String DELIVERY_ORDER_MODIFY = ORDER + "delivery:modify:";

    /**
     * 回款
     */
    public static final String PAYMENT = RABBITMQ_REDIS_PREFIX + "payment:";

    /**
     * 预占库存
     */
    public static final String PREOCCUPIEDINVENTORY = RABBITMQ_REDIS_PREFIX + "preOccupiedInventory:";

    /**
     * 在库调库
     */
    public static final String CUSTOMERINSTOCKTRANSFER = RABBITMQ_REDIS_PREFIX + "customerInStockTransfer:";

    /**
     * 分库补库
     */
    public static final String SUBTREASURY = RABBITMQ_REDIS_PREFIX + "subTreasury:";

    /**
     * 在库补库
     */
    public static final String CUSTOMERINSTOCK = RABBITMQ_REDIS_PREFIX + "customerInStock:";

    /**
     * E价格
     */
    public static final String PRODUCTINQUIRYEPRICE = RABBITMQ_REDIS_PREFIX + "productInquiryEprice:";

    /**
     * InqA
     */
    public static final String ORDERENQUIRY = RABBITMQ_REDIS_PREFIX + "orderEnquiry:";
    /**
     * Inqb
     */
    public static final String PRODUCTINQUIRYINQB = RABBITMQ_REDIS_PREFIX + "productInquiryInqb:";

    /**
     * InqA
     */
    public static final String PRODUCTINQUIRYINQA = RABBITMQ_REDIS_PREFIX + "productInquiryInqA:";

    /**
     * 预占库存
     */
    public static final String WEBSOCKET = RABBITMQ_REDIS_PREFIX + "websocket:";

    /**
     * 特价废除
     */
    public static final String SPECIALPRICEABOLISH = RABBITMQ_REDIS_PREFIX + "specialPriceAbolish:";

    /**
     * OTHERS
     */
    public static final String FLOW_RECORD_LOG = RABBITMQ_REDIS_PREFIX + "flowRecordLog:";
    /**
     * SMS
     */
    public static final String SMS = "SMS";

    /**
     * ERP
     */
    public static final String ERP = "ERP";

    /**
     * CSS
     */
    public static final String CSS = "CSS";

    /**
     * 新建订单
     */
    public static final String ERP_ORDER_CREATE = "erp-order-create";

    /**
     * 新建订单-返回结果
     */
    public static final String ERP_ORDER_CREATE_RETURN = "erp-order-create-return";

    /**
     * 编辑整单订单
     */
    public static final String ERP_ORDER_EDIT = "erp-order-edit";

    /**
     * 编辑整单订单-返回结果
     */
    public static final String ERP_ORDER_EDIT_RETURN = "erp-order-edit-return";

    /**
     * 修改订单物流货期
     */
    public static final String ERP_ORDER_DELIVERY_MODIFY = "erp-order-delivery-modify";

    /**
     * 修改订单物流货期-返回结果
     */
    public static final String ERP_ORDER_DELIVERY_MODIFY_RETURN = "erp-order-delivery-modify-return";

    /**
     * 取消订单
     */
    public static final String ERP_ORDER_CANCEL = "erp-order-cancel";

    /**
     * 取消订单-返回结果
     */
    public static final String ERP_ORDER_CANCEL_RETURN = "erp-order-cancel-return";

    /**
     * 新建付款计划
     */
    public static final String ERP_PAYMENT_CREATE = "erp-payment-create";

    /**
     * 新建付款计划-返回结果
     */
    public static final String ERP_PAYMENT_CREATE_RETURN = "erp-payment-create-return";

    /**
     * 新建预占库存
     */
    public static final String ERP_PREOCCUPIEDINVENTORY_CREATE = "erp-preOccupiedInventory-create";

    /**
     * 新建预占库存-返回结果
     */
    public static final String ERP_PREOCCUPIEDINVENTORY_CREATE_RETURN = "erp-preOccupiedInventory-create-return";

    /**
     * 删除预占库存
     */
    public static final String ERP_PREOCCUPIEDINVENTORY_DELETE = "erp-preOccupiedInventory-delete";

    /**
     * 删除预占库存-返回结果
     */
    public static final String ERP_PREOCCUPIEDINVENTORY_DELETE_RETURN = "erp-preOccupiedInventory-delete-return";

    /**
     * 新建在库调库
     */
    public static final String ERP_CUSTOMERINSTOCKTRANSFER_CREATE = "erp-customerInStockTransfer-create";

    /**
     * 新建在库调库-返回结果
     */
    public static final String ERP_CUSTOMERINSTOCKTRANSFER_CREATE_RETURN = "erp-customerInStockTransfer-create-return";

    /**
     * 新建分库补库
     */
    public static final String ERP_SUBTREASURY_CREATE = "erp-subTreasury-create";

    /**
     * 新建分库补库-返回结果
     */
    public static final String ERP_SUBTREASURY_CREATE_RETURN = "erp-subTreasury-create-return";

    /**
     * 新建顾客在库补库
     */
    public static final String ERP_CUSTOMERINSTOCK_CREATE = "erp-customerInStock-create";

    /**
     * 新建顾客在库补库-返回结果
     */
    public static final String ERP_CUSTOMERINSTOCK_CREATE_RETURN = "erp-customerInStock-create-return";

    /**
     * erp-productInquiryEprice-create
     */
    public static final String ERP_PRODUCTINQUIRYEPRICE_CREATE = "erp-productInquiryEprice-create";

    /**
     * erp-productInquiryEprice-return
     */
    public static final String ERP_PRODUCTINQUIRYEPRICE_RETURN = "erp-productInquiryEprice-create-return";

    /**
     * erp-productInquiryInqb-create
     */
    public static final String ERP_PRODUCTINQUIRYINQB_CREATE = "erp-productInquiryInqb-create";

    /**
     * erp-productInquiryInqb-return
     */
    public static final String ERP_PRODUCTINQUIRYINQB_RETURN = "erp-productInquiryInqb-create-return";

    /**
     * erp-productInquiryInqA-create
     */
    public static final String ERP_PRODUCTINQUIRYINQA_CREATE = "erp-productInquiryInqA-create";

    /**
     * erp-productInquiryInqA-return
     */
    public static final String ERP_PRODUCTINQUIRYINQA_RETURN = "erp-productInquiryInqA-create-return";

    /**
     * websocket-create
     */
    public static final String WEBSOCKET_CREATE = "websocket-create";

    /**
     * 重要流程履历
     */
    public static final String OTHERS_FLOWLOG_CREATE = "others-flowLog-create";

    /**
     * 特价废除
     */
    public static final String CSS_TO_SMS_SPECIALPRICE = "css-to-sms-nullify-specialprice";

    /**
     * 特价废除接收
     */
    public static final String CSS_TO_SMS_SPECIALPRICE_RETURN = "css-to-sms-nullify-specialprice-return";
}
