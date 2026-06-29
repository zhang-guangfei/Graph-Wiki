package com.smc.smccloud.service.hystrix;

import com.sales.ops.dto.attachedfile.FileUploadInsertVO;
import com.sales.ops.dto.attachedfile.OpsAttachedFileManageVO;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.order.DelAttachedFileManageInfoVO;
import com.smc.smccloud.service.OpsAttachedFileManageFeignApi;
import com.smc.smccloud.service.SMSOrderServiceFeignApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/10/16 9:04
 * @Descripton TODO
 */
@Component
public class OpsAttachedFileManageFeignHystrix implements FallbackFactory<OpsAttachedFileManageFeignApi> {
    @Override
    public OpsAttachedFileManageFeignApi create(Throwable throwable) {
        return new OpsAttachedFileManageFeignApi() {
            @Override
            public ResultVo<List<OpsAttachedFileManageVO>> findAttacheFiledManageInfo(OpsAttachedFileManageVO info) {
                return ResultVo.failure("接口异常,服务降级.");
            }

            @Override
            public ResultVo<String> insertFileInfo(List<OpsAttachedFileManageVO> info) {
                return ResultVo.failure("接口异常,服务降级.");
            }

            @Override
            public ResultVo<String> delAttacheFileManageInfo(DelAttachedFileManageInfoVO delAttachedFileManageInfoVO) {
                return ResultVo.failure("接口异常,服务降级.");
            }

            @Override
            public void dowmLoadAttacheFileManageInfo(OpsAttachedFileManageVO info) {

            }
        };
    }
}
