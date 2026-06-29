package com.smc.smccloud.service.impl;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.product.ProductCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author lyc
 * @Date 2023/4/28 16:29
 * @Descripton TODO
 */
@Service
@Slf4j
public class ProductCommonServiceImpl implements ProductCommonService {
    @Override
    public ResultVo<String> interfaceTest() {
        log.info("接口调用成功.");
        return ResultVo.success("接口调用成功");
    }
}
