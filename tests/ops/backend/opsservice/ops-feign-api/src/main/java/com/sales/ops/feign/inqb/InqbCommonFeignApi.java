package com.sales.ops.feign.inqb;


import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 */
@FeignClient(name = "delivery-server",path = "do" , contextId = "inqbCommonFeignServer")
public interface InqbCommonFeignApi {


    @RequestMapping(value = "/inqb/queryCommon/findOrderArea", method = RequestMethod.GET)
    ResultVo<String> findOrderAreaInfo(@RequestParam(name = "endUser", required = true) String endUser,
                                                               @RequestParam(name = "deptNo", required = true) String deptNo);



}
