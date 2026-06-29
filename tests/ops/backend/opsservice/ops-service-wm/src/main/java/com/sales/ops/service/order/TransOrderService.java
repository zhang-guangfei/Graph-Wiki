package com.sales.ops.service.order;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.TransOrder;
import com.sales.ops.dto.inventory.InventoryForTransDto;
import com.sales.ops.dto.order.TransOrderDto;
import com.sales.ops.dto.order.TransOrderDtoForMove;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.OrderTransStatusEnum;

import java.util.Date;

public interface TransOrderService {


    int createTransOrderForPreorder(TransOrder transOrder) throws OpsException;

    TransOrder getTransOrderByTransNo(String transNo, Integer itemNo);

    void createTransOrder(TransOrder transOrder, UserDto userDto) throws OpsException;

    void createTransOrderBatch(TransOrderDto dto) throws OpsException;

    void createTransOrderOld(TransOrder transOrder, UserDto userDto) throws OpsException;

    void insertTransOrderForNormal(TransOrder transOrder) throws OpsException;

    void createTransOrderForMove(TransOrderDtoForMove dto) throws OpsException;

    Long findInventoryIdForTransOrderInput(InventoryForTransDto transDto, UserDto userDto) throws OpsException;

    int updateWarehouse(Long id, String fromWarehouse, String toWarehouse);

    int updateTransOrderById(TransOrder transOrder);

    int updateStatus(Long id, OrderTransStatusEnum status) throws OpsException;

    int updateStatus(String transNo, Integer itemNo, OrderTransStatusEnum status, Integer qty) throws OpsException;

    int updateInQuantity(Long id, Integer qty) throws OpsException;

    int updateTransOrderShipById(Long id, String invoiceNo, Long invoiceId, int qty, Date shipDate) throws OpsException;

    TransOrder findTransOrder(String transNo, int transItem) throws OpsException;
}
