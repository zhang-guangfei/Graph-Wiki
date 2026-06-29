package com.smc.smccloud.service.impl;

import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.dto.ips.IpsReceiveOrderAllOriginalInfoDto;
import com.sales.ops.dto.util.CommonResult;
import com.smc.smccloud.mapper.purchase.PurchaseIpsOrderMapper;
import com.smc.smccloud.service.PurchaseIpsOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class PurchaseIpsOrderServiceImpl implements PurchaseIpsOrderService {

    @Resource
    private PurchaseIpsOrderMapper purchaseIpsOrderMapper;

    /**
     *
     * @param ipsReceiveOrderAllOriginalInfoDtos
     * @return
     */
    @Override
    public CommonResult<String> insertIpsOrder(List<IpsReceiveOrderAllOriginalInfoDto> ipsReceiveOrderAllOriginalInfoDtos) throws Exception {
        StringBuilder errMsg = new StringBuilder();
        // 调用公共 拆分参数方法
        Map<Integer, List<IpsReceiveOrderAllOriginalInfoDto>> mapBarcode = SplitBatchUtils.getInsertBatchBySqlserver(ipsReceiveOrderAllOriginalInfoDtos, IpsReceiveOrderAllOriginalInfoDto.class);
        for (Map.Entry<Integer, List<IpsReceiveOrderAllOriginalInfoDto>> entry : mapBarcode.entrySet()) {
            try {
                purchaseIpsOrderMapper.insertIpsReceiveOrder(entry.getValue());
            } catch (Exception ex) {
                errMsg.append("采购发单写入IPS中间表失败").append(ex.getMessage());
                throw new Exception("采购发单写入IPS中间表失败" + "->错误：" + ex);
            }
        }
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return CommonResult.failure(errMsg.toString());
        }
        return CommonResult.success("IPS发单成功，共计"+ ipsReceiveOrderAllOriginalInfoDtos.size());
    }
}
