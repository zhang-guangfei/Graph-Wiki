package com.sales.ops.feign;

import com.alibaba.fastjson.JSONArray;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author B91717
 * @date 2022/5/9
 * @apiNote
 */
@FeignClient(name = "shareapp-service", contextId = "purchasegzorder")
public interface OpsGZOrderFeignApi {

    /**
     * 写入CTCsmcOrder
     */
    @RequestMapping(value = "/shareapp/purchasegzorder/insertGZ", method = RequestMethod.POST)
    CommonResult<String> insertOrderGz(@RequestBody JSONArray jsonArray);

}
