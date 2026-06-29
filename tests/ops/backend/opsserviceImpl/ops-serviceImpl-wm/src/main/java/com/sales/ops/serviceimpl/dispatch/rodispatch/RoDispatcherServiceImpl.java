package com.sales.ops.serviceimpl.dispatch.rodispatch;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.flux.HandConfirm;
import com.sales.ops.dto.inventory.CreInvMoveForReturnOrderDto;
import com.sales.ops.dto.inventory.WmRoConfirmDto;
import com.sales.ops.service.dispatch.rodispatch.RoDispatcherService;
import com.sales.ops.serviceimpl.dispatch.rodispatch.service.HandConfirmHandler;
import com.sales.ops.serviceimpl.dispatch.rodispatch.service.ReturnConfirmHandler;
import com.sales.ops.serviceimpl.dispatch.rodispatch.service.RoConfirmHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author C12961
 * @date 2023/4/26 14:21
 */
@Service
@AllArgsConstructor
public class RoDispatcherServiceImpl implements RoDispatcherService {

    private final HandConfirmHandler handConfirmHandler;
    private final RoConfirmHandler roConfirmHandler;
    private final ReturnConfirmHandler returnConfirmHandler;

    @Transactional(rollbackFor = OpsException.class)
    @Override
    public void handConfirm(HandConfirm handConfirm) throws OpsException {
        handConfirmHandler.handConfirm(handConfirm);
    }

    @Transactional(rollbackFor = OpsException.class)
    @Override
    public void roConfirm(WmRoConfirmDto wmRoConfirmDto) throws OpsException {
        roConfirmHandler.roConfirm(wmRoConfirmDto);
    }

    @Transactional(rollbackFor = OpsException.class)
    @Override
    public void returnConfirm(List<CreInvMoveForReturnOrderDto> list) throws OpsException {
        returnConfirmHandler.returnConfirm(list);
    }


}
