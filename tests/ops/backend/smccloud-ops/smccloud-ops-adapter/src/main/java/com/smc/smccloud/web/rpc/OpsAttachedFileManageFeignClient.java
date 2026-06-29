package com.smc.smccloud.web.rpc;

import com.sales.ops.dto.attachedfile.FileUploadInsertVO;
import com.sales.ops.dto.attachedfile.OpsAttachedFileManageVO;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.model.OpsAttachedFileManageDO;
import com.smc.smccloud.model.order.DelAttachedFileManageInfoVO;
import com.smc.smccloud.service.OpsAttachedFileManageFeignApi;
import com.smc.smccloud.service.OpsAttachedFileManageService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lyc
 * @Date 2023/10/16 9:07
 * @Descripton TODO
 */
@RestController
public class OpsAttachedFileManageFeignClient implements OpsAttachedFileManageFeignApi {


    @Resource
    private OpsAttachedFileManageService opsAttachedFileManageService;

    @Override
    public ResultVo<List<OpsAttachedFileManageVO>> findAttacheFiledManageInfo(OpsAttachedFileManageVO info) {
        if (info == null) {
            return ResultVo.failure("入参不可为空");
        }
        OpsAttachedFileManageDO copyInfo = BeanCopyUtil.copy(info, OpsAttachedFileManageDO.class);
        ResultVo<List<OpsAttachedFileManageDO>> attacheFiledManageInfo = opsAttachedFileManageService.findAttacheFiledManageInfo(copyInfo);
        if (attacheFiledManageInfo.isSuccess() && CollectionUtils.isNotEmpty(attacheFiledManageInfo.getData())) {
            return ResultVo.success(BeanCopyUtil.copyList(attacheFiledManageInfo.getData(),OpsAttachedFileManageVO.class));
        } else {
            if (!attacheFiledManageInfo.isSuccess()) {
                return ResultVo.failure(attacheFiledManageInfo.getMessage());
            } else {
                return ResultVo.success(null);
            }
        }
    }

    @Override
    public ResultVo<String> insertFileInfo(List<OpsAttachedFileManageVO> info) {
           return opsAttachedFileManageService.insertFileInfo2(info);
    }

    @Override
    public ResultVo<String> delAttacheFileManageInfo(DelAttachedFileManageInfoVO delAttachedFileManageInfoVO) {
        return opsAttachedFileManageService.delAttacheFileManageInfo(delAttachedFileManageInfoVO.getIds(), delAttachedFileManageInfoVO.getOptUser());
    }

    @Override
    public void dowmLoadAttacheFileManageInfo(OpsAttachedFileManageVO info) {
        opsAttachedFileManageService.dowmLoadAttacheFileManageInfo(info.getFilePath(),info.getRandomFileName());
    }

}
