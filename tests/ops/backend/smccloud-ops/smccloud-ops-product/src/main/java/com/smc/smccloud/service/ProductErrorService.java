package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author smc
 * @since 2022-04-10
 */
public interface ProductErrorService {

    /**
     * 是否错误型号
     *
     * @param modelNo 型号
     * @return boolean
     */
    ResultVo<Boolean> isErrorModelNo(String modelNo);
}
