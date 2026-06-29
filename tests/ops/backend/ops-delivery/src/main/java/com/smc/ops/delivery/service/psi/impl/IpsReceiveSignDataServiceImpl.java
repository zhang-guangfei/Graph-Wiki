package com.smc.ops.delivery.service.psi.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.IpsReceiveSignImpInfoFromAll;
import com.sales.ops.dto.ips.IpsSignDataBatchPushVO;
import com.smc.ops.delivery.mapper.IpsReceiveSignImpInfoFromAllMapper;
import com.smc.ops.delivery.mapper.psi.IpsReceiveInvoiceMasterFromAllMapper;
import com.smc.ops.delivery.model.ips.IpsReceiveInvoiceMasterFromAllDO;
import com.smc.ops.delivery.model.ips.IpsReceiveSignImpInfoFromAllDO;
import com.smc.ops.delivery.service.psi.IpsReceiveSignDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * bug 19413.38 签收数据批量推送服务实现
 */
@Service
@AllArgsConstructor
public class IpsReceiveSignDataServiceImpl implements IpsReceiveSignDataService {

    private final IpsReceiveInvoiceMasterFromAllMapper ipsReceiveInvoiceMasterFromAllMapper;
    private final IpsReceiveSignImpInfoFromAllMapper ipsReceiveSignImpInfoFromAllMapper;

    @DS("ips_sharedb")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void batchInsertSignData(IpsSignDataBatchPushVO data) {
        // 写入发票主表
        IpsReceiveInvoiceMasterFromAllDO masterDO = BeanUtil.copyProperties(data.getInvoiceMaster(), IpsReceiveInvoiceMasterFromAllDO.class);
        ipsReceiveInvoiceMasterFromAllMapper.insert(masterDO);
        // 批量写入签收明细
        for (IpsReceiveSignImpInfoFromAll sign : data.getSignList()) {
            IpsReceiveSignImpInfoFromAllDO signDO = BeanUtil.copyProperties(sign, IpsReceiveSignImpInfoFromAllDO.class);
            ipsReceiveSignImpInfoFromAllMapper.insert(signDO);
        }
    }
}
