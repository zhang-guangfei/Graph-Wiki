package com.sales.ops.service.ba;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.enums.RcvOrderStatusEnum;

/**
 * @author C12961
 * @date 2022/11/8 10:10
 */
public interface RcvStatusConfigService {


    Boolean findConfigByKey(RcvOrderStatusEnum fromStatus, RcvOrderStatusEnum toStatus) throws OpsException;
}
