package com.sales.ops.utils;

import cn.hutool.core.util.RandomUtil;
import com.sales.ops.enums.OrderIDFormatEnum;

import java.util.Optional;

/**
 * @author C12961
 * @date 2023/3/2 9:45
 */
public class WmOrderNoFactory {

    // 交易出
    public static String JYCK_ID(String orderId, Integer orderItem, Integer splitNo) {
        return createWmNo(OrderIDFormatEnum.DO_FORMAT, orderId, orderItem, splitNo, 0);
    }

    // 分纳交易出
    public static String JYCK_FN_ID(String orderId, Integer orderItem, Integer splitNo) {
        return createWmNo(OrderIDFormatEnum.DO_FN_FORMAT, orderId, orderItem, splitNo, 0);
    }

    // 采购入
    public static String RO_ID(String orderId, Integer orderItem, Integer splitNo) {
        return createWmNo(OrderIDFormatEnum.RO_FORMAT, orderId, orderItem, splitNo, 0);
    }

    // 调拨出
    public static String DBCK_ID(String orderId, Integer orderItem, Integer splitNo) {
        return createWmNo(OrderIDFormatEnum.DBC_FORMAT, orderId, orderItem, splitNo, 0);
    }

    // 型号拆分的调拨出
    public static String DBCK_ID_FOR_PCO(String orderId, Integer orderItem, Integer pcoItem) {
        return createWmNo(OrderIDFormatEnum.DBC_FORMAT, orderId, orderItem, 0, pcoItem);
    }

    // 分纳调拨出
    public static String DBCK_FN_ID(String orderId, Integer orderItem, Integer splitNo) {
        return createWmNo(OrderIDFormatEnum.DBC_FN_FORMAT, orderId, orderItem, splitNo, 0);
    }

    public static String DBRK_ID_Random(String orderId, String orderItem, Integer num, Integer assnum) {
        return String.format(OrderIDFormatEnum.DBR_RANDOM_FORMAT.getFormat(),
                orderId, fillToThree(Integer.valueOf(orderItem)), fillToThree(Optional.ofNullable(num).orElse(0)), fillToThree(Optional.ofNullable(assnum).orElse(0)),
                RandomUtil.randomString(8));
    }

    // 调拨入
    public static String DBRK_ID(String orderId, Integer orderItem, Integer splitNo) {
        return createWmNo(OrderIDFormatEnum.DBR_FORMAT, orderId, orderItem, splitNo, 0);
    }

    // 分纳调拨入
    public static String DBRK_FN_ID(String orderId, Integer orderItem, Integer splitNo) {
        return createWmNo(OrderIDFormatEnum.DBR_FN_FORMAT, orderId, orderItem, splitNo, 0);
    }

    // 型号拆分的调拨入
    public static String DBRK_ID_FOR_PCO(String orderId, Integer orderItem, Integer pcoItem) {
        return createWmNo(OrderIDFormatEnum.DBR_FORMAT, orderId, orderItem, 0, pcoItem);
    }

    // 型号拆分转数量拆分
    public static String DO_PCO_CHANGE_DO_ID(String orderId, Integer orderItem, Integer pcoItem, Integer splitNo) {
        return createWmNo(OrderIDFormatEnum.DO_PCO_CHANGE_DO_FORMAT, orderId, orderItem, pcoItem, splitNo);
    }


    // 退货入库
    public static String THRK_ID(String invoiceNo, Integer itemNo, Integer SplitNo, Integer qty) {
        return String.format(OrderIDFormatEnum.RO_TH_FORMAT.getFormat(), invoiceNo, fillToThree(itemNo), fillToThree(SplitNo), fillToFour(qty));
    }

    // 创建物流单号：do_id,ro_id
    private static String createWmNo(OrderIDFormatEnum formatter, String orderNo, Integer itemNo, Integer splitNo, Integer fillNo) {
        return String.format(formatter.getFormat(), orderNo, fillToThree(itemNo), fillToThree(splitNo), fillToThree(fillNo));
    }

    /*****************调拨指令***********************/

    public static String DBRK_FN(String orderId, Integer orderItem, Integer num, Integer assNum) {
        return String.format(OrderIDFormatEnum.DBR_FN_FORMAT.getFormat(), orderId, fillToThree(orderItem), fillToThree(num), fillToThree(assNum));
    }


    /*****************调库指令***********************/
    public static String TKCK(String orderId, Integer orderItem, Integer num, Integer assNum) {
        return String.format(OrderIDFormatEnum.DBC_FORMAT.getFormat(), orderId, fillToFour(orderItem), fillToThree(num), fillToThree(assNum));
    }
    public static String TKRK(String orderId, Integer orderItem, Integer num, Integer assNum) {
        return String.format(OrderIDFormatEnum.DBR_FORMAT.getFormat(), orderId, fillToFour(orderItem), fillToThree(num), fillToThree(assNum));
    }
    public static String TKCK_FN(String orderId, Integer orderItem, Integer num, Integer assNum) {
        return String.format(OrderIDFormatEnum.DBC_FN_FORMAT.getFormat(), orderId, fillToFour(orderItem), fillToThree(num), fillToThree(assNum));
    }
    public static String TKRK_FN(String orderId, Integer orderItem, Integer num, Integer assNum) {
        return String.format(OrderIDFormatEnum.DBR_FN_FORMAT.getFormat(), orderId, fillToFour(orderItem), fillToThree(num), fillToThree(assNum));
    }
    /*****************调账指令***********************/
    // TZRK
    public static String RO_ADJUST(String invoiceNo, String orderId, Integer orderItem) {
        return String.format(OrderIDFormatEnum.RO_FORMAT.getFormat(), invoiceNo, orderId, fillToFour(orderItem), "ADJ");
    }

    //TZCK
    public static String DO_ADJUST(String orderId, Integer orderItem) {
        int i = RandomUtil.randomInt(100, 999);
        return String.format(OrderIDFormatEnum.DO_FORMAT.getFormat(), orderId, fillToFour(orderItem), i, "ADJ");
    }
    public static String fillToFour(String code) {
        return String.format("%04d", Integer.valueOf(code));
    }
    public static String fillToFour(Integer code) {
        return String.format("%04d", code);
    }



    public static String fillToThree(Integer code) {
        return String.format("%03d", code);
    }

}
