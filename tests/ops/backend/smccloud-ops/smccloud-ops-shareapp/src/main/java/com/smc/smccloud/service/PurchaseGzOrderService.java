package com.smc.smccloud.service;

import cn.hutool.json.JSONArray;
import com.sales.ops.dto.util.CommonResult;

/**
 * @author B91717
 * @date 2022/5/9
 * @apiNote
 */
public interface PurchaseGzOrderService {

    /**
     * 广州制造订单写入
     *
     * @return
     */
    CommonResult<String> insertGZ(JSONArray jsonArray) throws Exception ;


}
