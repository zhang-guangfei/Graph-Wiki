package com.sales.ops.utils;

import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.OpsRo;

import java.util.Optional;

/**
 * @author C12961
 * @date 2023/2/16 17:29
 */
public class PoNoUtil {

    public static final String delimiter = "-";

    public static String create(String poNo, Integer itemNo, Integer splitNo) {
        String splitItemNo = Optional.ofNullable(splitNo).orElse(0).toString();
        if("0".equals(splitItemNo)){
            return String.join(delimiter, poNo, itemNo.toString());
        }
        return String.join(delimiter, poNo, itemNo.toString(), splitItemNo);
    }

    public static String getFullPoNo(OpsPurchaseorder po) {
        String orderNo = po.getOrderno();
        String itemNo = Optional.ofNullable(po.getItemno()).orElse(0).toString();
        String splitItemNo = Optional.ofNullable(po.getSplititemno()).orElse(0).toString();
        if("0".equals(splitItemNo)){
            return String.join(delimiter, orderNo, itemNo);
        }
        return String.join(delimiter, orderNo, itemNo, splitItemNo);
    }

    public static String getFullPoNo(OpsRo ro) {
        String orderNo = ro.getOrderId();
        String itemNo = ro.getOrderItem();
        String splitItemNo = Optional.ofNullable(ro.getNum()).orElse(0).toString();
        if("0".equals(splitItemNo)){
            return String.join(delimiter, orderNo, itemNo);
        }
        return String.join(delimiter, orderNo, itemNo, splitItemNo);
    }

    public static String getFullRePoNo(OpsInventoryMove move) {
        String orderNo = move.getOrderno();
        String itemNo = Optional.ofNullable(move.getItemno()).orElse(0).toString();
        String splitItemNo = Optional.ofNullable(move.getSplititemno()).orElse(0).toString();
        if("0".equals(splitItemNo)){
            return String.join(delimiter, orderNo, itemNo);
        }
        return String.join(delimiter, orderNo, itemNo, splitItemNo);
    }

    public static String getFullPoNo(OpsInventoryMove move) {
        String orderNo = move.getAssociateNo();
        String itemNo = Optional.ofNullable(move.getAssociateNoItem()).orElse(0).toString();
        String splitItemNo = Optional.ofNullable(move.getAssociateNoSplitno()).orElse(0).toString();
        if("0".equals(splitItemNo)){
            return String.join(delimiter, orderNo, itemNo);
        }
        return String.join(delimiter, orderNo, itemNo, splitItemNo);
    }

    public static String getFullRePoNo(OpsRequestpurchase po) {
        String orderNo = po.getOrderno();
        String itemNo = Optional.ofNullable(po.getItemno()).orElse(0).toString();
        String splitItemNo = Optional.ofNullable(po.getSplititemno()).orElse(0).toString();
        if("0".equals(splitItemNo)){
            return String.join(delimiter, orderNo, itemNo);
        }
        return String.join(delimiter, orderNo, itemNo, splitItemNo);
    }

    public static Integer getSplitNo(OpsPurchaseorder po) {
        return Optional.ofNullable(po.getSplititemno()).orElse(0);
    }

    public static Integer getSplitNo(OpsRequestpurchase po) {
        return Optional.ofNullable(po.getSplititemno()).orElse(0);
    }


}
