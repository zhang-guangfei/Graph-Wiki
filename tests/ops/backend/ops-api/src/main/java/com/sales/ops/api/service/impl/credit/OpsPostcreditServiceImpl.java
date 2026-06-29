package com.sales.ops.api.service.impl.credit;

import com.sales.ops.api.service.credit.OpsPostcreditService;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OpsCreditInterceptFlagFeighApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author C02483
 * @version 1.0
 * @description: 调用信用拦截接口
 * @date 2022/1/25 10:21
 */
@Service
public class OpsPostcreditServiceImpl implements OpsPostcreditService {

    @Autowired
    private OpsCreditInterceptFlagFeighApi opsCreditInterceptFlagFeighApi;

    @Override
    public Map<String,String> checkCredit(@RequestBody List<String> orderFnoList) throws OpsException {
        CommonResult<Map<String,String>> result = opsCreditInterceptFlagFeighApi.getCreditInterceptFlagByOrderFno(orderFnoList);
        if (result != null && result.isSuccess()) {
            return result.getData();
        } else {
            throw Exceptions.OpsException("信用拦截请求失败{}", result.getMessage());
        }
    }




}
