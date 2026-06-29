package com.sales.ops.service.wmOrder;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.order.OpsPcoAndItemAndItemInventoryDto;
import com.sales.ops.enums.DoTypeEnum;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/11/6 8:30
 */
public interface BaseWMOrderService {
    List<OpsDo> getDos(@Nonnull List<OpsDo> list, @Nonnull DoTypeEnum doTypeEnum);

    List<OpsDo> getDos(@Nonnull List<OpsDo> list, @Nonnull DoTypeEnum doTypeEnum, @Nonnull Integer num);

    List<OpsDo> getDos(@Nonnull List<OpsDo> list, @Nonnull DoTypeEnum doTypeEnum, @Nonnull Integer num, @Nonnull Integer assNum);

    List<OpsDo> getShippedDos(@Nonnull List<OpsDo> list);

    List<OpsDo> getDos(@Nonnull String orderId, String orderItem);

    List<OpsDo> getDos(@Nonnull String orderId, @Nonnull String orderItem, @Nonnull Integer num);

    List<OpsDo> getDos(@Nonnull String orderId, @Nonnull String orderItem, @Nonnull Integer num, @Nonnull Integer assNum);

    OpsDo getDo(@Nonnull String doId) throws OpsException;

    OpsDoItem getDoItem(@Nonnull String doId) throws OpsException;

    List<OpsDoItemInventory> getDoItemInv(@Nonnull String doId);

    List<OpsPco> getPcos(@Nonnull String orderId, String orderItem);

    List<OpsPco> getPcos(@Nonnull String orderId, @Nonnull String orderItem, @Nonnull Integer num);

    OpsPco getPco(@Nonnull String pcoId) throws OpsException;

    List<OpsPcoItem> getPcoItem(@Nonnull String pcoId, Integer pcoItem);

    List<OpsPcoItemInventory> getPcoItemInv(@Nonnull String pcoId, Integer pcoItem);

    OpsPcoAndItemAndItemInventoryDto getPcoDto(@Nonnull String orderId, @Nonnull String orderItem, @Nonnull Integer num);

    Rcvdetail findRcvDetail(String orderId, Integer orderItem) throws OpsException;

    List<Rcvdetail> findRcvDetail(String orderId) throws OpsException;

    Rcvdetail findRcvDetailByFno(String orderFno) throws OpsException;
}
