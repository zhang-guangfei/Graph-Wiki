package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.hystrix.DictDataServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 数据字典服务  url = "http://10.116.194.236:8009"
 */
@FeignClient(name = "common-service",
        contextId = "dictdata-service",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = DictDataServiceFeignHystrix.class)
public interface DictDataServiceFeignApi {

    @RequestMapping(value = "/common/code/getDataTypes", method = RequestMethod.GET)
    ResultVo<List<DataCodeVO>> getDataCodes(@RequestParam("classCode") String classCode);

    @RequestMapping(value = "/common/code/getDataCodesTree", method = RequestMethod.POST)
    ResultVo<List<DataCodeVO>> getDataCodesTree(@RequestParam("classCode") String classCode);

    @RequestMapping(value = "/common/code/getDataTypeCodesInfo", method = RequestMethod.GET)
    ResultVo<DataTypeVO> getDataTypeCodesInfo(@RequestParam("classCode") String classCode, @RequestParam("code") String code);

    @RequestMapping(value = "/common/code/updateDataType", method = RequestMethod.POST)
    ResultVo<Integer> updateDataType(@RequestParam("classCode") String classCode, @RequestParam("code") String code, @RequestParam("extNote2") String extNote2);

    @RequestMapping(value = "/common/code/getRandomOrderNoGenerator", method = RequestMethod.GET)
    ResultVo<String> getRandomOrderNoGenerator(@RequestParam("classCode") String classCode, @RequestParam("code") String code);

    /**
     * 更新 ext_note1,ext_note2,ext_note3
     */
    @RequestMapping(value = "/common/code/updateDataParam", method = RequestMethod.POST)
    ResultVo<Integer> updateDataParam(@RequestBody DataTypeVO dataTypeVO);

    @RequestMapping(value = "/common/code/getDataTypeByParentCode", method = RequestMethod.GET)
    ResultVo<List<DataCodeVO>> getDataTypeByParentCode(@RequestParam("parentCode") String parentCode);

}
