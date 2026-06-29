package com.sales.ops.serviceimpl.dispatch.podispatch;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.purchase.OpsPurchaseStatusToWMDto;
import com.sales.ops.dto.purchase.PoToWmDto;
import com.sales.ops.service.dispatch.PoDispatcherService;
import com.sales.ops.serviceimpl.dispatch.podispatch.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author C12961
 * @desc 物流模块关于采购单的调度器
 * @date 2023/2/16 17:39
 */
@Slf4j
@Service
@AllArgsConstructor
public class PoDispatcherServiceImpl implements PoDispatcherService {

    // 构造器方法注入
    private final PurchaseResetHandler purchaseResetHandler;
    private final PurchasePreprocessHandler purchasePreProcessHandler;
    private final PurchaseInterceptHandler purchaseInterceptHandler;
    private final PurchaseSendHandler purchaseSendHandler;
    private final PurchaseAcceptHandler purchaseAcceptHandler;

    /**
     * @description： 采购单转订取消回调
     * @author C12961
     * @date 2022/3/9 12:19
     * @update 2023/03/02
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resetForPo(PoToWmDto poDto) throws OpsException {
        purchaseResetHandler.resetForPo(poDto);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void preprocessForRequestPo(List<OpsRequestpurchase> list) throws OpsException {
        purchasePreProcessHandler.preprocessForRequestPo(list);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void interceptForRequestPo(List<OpsRequestpurchase> list) throws OpsException {
        purchaseInterceptHandler.interceptForRequestPo(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void releaseForRequestPo(List<OpsRequestpurchase> list) throws OpsException {
        purchaseInterceptHandler.releaseForRequestPo(list);
    }

    /**
     * @description 采购单已发单回调 生成采购调拨出入库指令
     * @author C12961
     * @date 2023/2/16 19:49
     * @update 2023/03/02
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendForPo(List<OpsPurchaseStatusToWMDto> list) throws OpsException {
        purchaseSendHandler.sendForPo(list);
    }

    /**
     * @description 采购已接单回调 主要的作用： 1. 采购转订接单时 1.1 修改供应商和签收仓 1.2 创建订单状态事件 2. 接单时
     * 2.1 创建生产中库存 2.2 给物流指令绑定库存 2.3 设置库存预占数量 2.4 计算多采购的数量,创建库存 2.5
     * 创建订单状态事件
     * @author C12961
     * @date 2023/3/2 14:32
     */
    // bug16252 采购单处理move，开启新事务，事务提交后下次可以查询出move，防止创建多条move
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public void acceptForPo(PoToWmDto dto) throws OpsException {
        purchaseAcceptHandler.acceptForPo(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invoiceImport(List<OpsPurchaseinvoice> list) throws OpsException {
        throw Exceptions.OpsException("此接口暂不支持");
    }

}
