package com.smc.smccloud.service;

import com.sales.ops.dto.purchase.OpsRequestPurchaseInterceptConfigVO;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.interceptConfig.OpsRequestpurchaseInterceptConfigDO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author lyc
 * @Date 2024/9/13 13:21
 * @Descripton TODO
 */
public interface IntercepterConfigService {

    /**
     * 导出模板
     */
    void downloadTemplate();

    ResultVo<String> importData(MultipartFile file,String optUser);

    ResultVo<String> addData(OpsRequestPurchaseInterceptConfigVO item);

}
