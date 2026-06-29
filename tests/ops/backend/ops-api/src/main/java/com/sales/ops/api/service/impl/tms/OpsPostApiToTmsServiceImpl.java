package com.sales.ops.api.service.impl.tms;

import com.sales.ops.api.dto.tms.InquiryFreightCalculationDto;
import com.sales.ops.api.service.tms.OpsPostApiToTmsService;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description： ops 提交数据到 tms
 * @date ：Created in 2021/10/27 16:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OpsPostApiToTmsServiceImpl implements OpsPostApiToTmsService {

    @Override
    public CommonResult inquiryFreightCalculation(InquiryFreightCalculationDto param) throws OpsException {
        return null;
    }
}
