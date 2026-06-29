package com.sales.ops.serviceimpl.dispatch.dodispatch;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.flux.RoConfirmItem;
import com.sales.ops.dto.flux.RoSignConfirmDto;
import com.sales.ops.service.dispatch.dodispatch.DoDispatcherService;
import com.sales.ops.serviceimpl.dispatch.dodispatch.service.GoodsConfirmHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 和入库有关的物流操作
 * 【应用服务层】
 *
 * @author C12961
 * @date 2023/3/14 10:29
 */
@Service
@AllArgsConstructor
public class DoDispatcherServiceImpl implements DoDispatcherService {

    private final GoodsConfirmHandler goodsConfirmHandler;

    /**
     * @description 一个发票一次到货确认
     * 一个发票号下面有多条库存，多条库存可以按关联单号分组
     * @author C12961
     * @date 2023/3/14 11:53
     */
    public List<RoConfirmItem> goodsConfirm(RoSignConfirmDto confirm) throws OpsException {
        return goodsConfirmHandler.goodsConfirm(confirm);
    }



}
