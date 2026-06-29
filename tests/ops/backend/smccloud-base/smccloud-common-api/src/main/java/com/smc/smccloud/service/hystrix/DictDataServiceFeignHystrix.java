package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DictDataServiceFeignHystrix implements FallbackFactory<DictDataServiceFeignApi> {

    @Override
    public DictDataServiceFeignApi create(Throwable cause) {
        return new DictDataServiceFeignApi() {
            @Override
            public ResultVo<List<DataCodeVO>> getDataCodes(String classCode) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<DataCodeVO>> getDataCodesTree(String classCode) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<DataTypeVO> getDataTypeCodesInfo(String classCode, String code) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<Integer> updateDataType(String classCode, String code, String extNote2) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> getRandomOrderNoGenerator(String classCode, String code) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<Integer> updateDataParam(DataTypeVO dataTypeVO) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<DataCodeVO>> getDataTypeByParentCode(String parentCode) {
                return ResultVo.failure("服务降级");
            }

        };
    }
}
