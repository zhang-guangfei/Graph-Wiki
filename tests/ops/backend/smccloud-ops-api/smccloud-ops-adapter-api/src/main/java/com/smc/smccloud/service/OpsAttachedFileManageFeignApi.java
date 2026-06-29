package com.smc.smccloud.service;

import com.sales.ops.dto.attachedfile.FileUploadInsertVO;
import com.sales.ops.dto.attachedfile.OpsAttachedFileManageVO;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.order.DelAttachedFileManageInfoVO;
import com.smc.smccloud.service.hystrix.SMSOrderServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/10/16 9:02
 * @Descripton TODO
 */
@FeignClient(name = "adapter-service",
        contextId = "adapter-file",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = SMSOrderServiceFeignHystrix.class)
public interface OpsAttachedFileManageFeignApi {

    @PostMapping("/adapter/fileManage/findAttacheFiledManageInfo")
    ResultVo<List<OpsAttachedFileManageVO>> findAttacheFiledManageInfo(@RequestBody OpsAttachedFileManageVO info);

    @PostMapping("/adapter/fileManage/insertFileInfo")
    ResultVo<String> insertFileInfo(@RequestBody List<OpsAttachedFileManageVO> info);

    // 软删除附件
    @PostMapping("/adapter/fileManage/delAttacheFileManageInfo")
    ResultVo<String> delAttacheFileManageInfo(@RequestBody DelAttachedFileManageInfoVO delAttachedFileManageInfoVO);

    // 附件下载
    @PostMapping("/adapter/fileManage/dowmLoadAttacheFileManageInfo")
    void dowmLoadAttacheFileManageInfo(@RequestBody OpsAttachedFileManageVO info);


}
