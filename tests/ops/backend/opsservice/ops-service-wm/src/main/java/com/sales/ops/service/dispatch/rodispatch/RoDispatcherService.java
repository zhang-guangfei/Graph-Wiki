package com.sales.ops.service.dispatch.rodispatch;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.flux.HandConfirm;
import com.sales.ops.dto.inventory.CreInvMoveForReturnOrderDto;
import com.sales.ops.dto.inventory.WmRoConfirmDto;

import java.util.List;

/**
 * @author C12961
 * @date 2023/5/19 10:25
 */
public interface RoDispatcherService {
    void handConfirm(HandConfirm handConfirm) throws OpsException;

    void roConfirm(WmRoConfirmDto wmRoConfirmDto) throws OpsException;

    void returnConfirm(List<CreInvMoveForReturnOrderDto> list) throws OpsException;
}
