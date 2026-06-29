package com.sales.ops.serviceimpl.order.result;

import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.enums.SourceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
public class Result {

    private String orderId;
    private Integer orderItem;
    private String modelno;//转订的型号
    private boolean isCG;//自发采购还是预约采购在途
    private OrderNoInfo po;//关联单号
    private boolean isDB;//是否为调拨
    private Integer qty;//转订的数量

    //十位单号，modelno必填，move可以为空，当请购处理时为空(move表无数据，或二次处理状态)，qty为转订数量
    public static Result create(String orderId, String orderItem, String modelno, OpsInventoryMove move, int qty) {
        OrderNoInfo associate = OrderNoInfo.getAssociateNoFromMove(move);
        OrderNoInfo orderNo = OrderNoInfo.getFromMove(move);
        // 通过对比关联单号和十位单号是否相同来判断是自发采购还是预约在途
        boolean isCG = move.getInventoryId() == null ||
                (StringUtils.equals(move.getSourceType(), SourceTypeEnum.PURCHASE.getType()) &&
                        StringUtils.equals(orderNo.getOrderNo(), orderId) &&
                        StringUtils.equals(orderNo.getItemNo().toString(), orderItem));
        // 通过sourceType判断是否为调拨
        boolean isDB = move.getInventoryId() != null &&
                StringUtils.equals(move.getSourceType(), SourceTypeEnum.DB.getType());
        return new Result(orderId, Integer.valueOf(orderItem), modelno, isCG, associate, isDB, qty);
    }


}
