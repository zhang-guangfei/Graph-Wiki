package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.CarrierVO;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author lyc
 * @Date 2022/4/7 9:06
 * @Descripton TODO
 */
public interface CarrierService {

    ResultVo<CarrierVO> findCarrierInfoById(String carrierId);
}
