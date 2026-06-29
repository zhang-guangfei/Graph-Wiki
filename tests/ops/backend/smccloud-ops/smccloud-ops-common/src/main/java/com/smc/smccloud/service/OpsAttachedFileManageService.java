package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sales.ops.dto.attachedfile.OpsAttachedFileManageVO;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.OpsAttachedFileManageDO;
import com.smc.smccloud.model.UploadFileByUIVO;
import com.smc.smccloud.model.fileupload.FileUpload;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2023-10-10
 */
public interface OpsAttachedFileManageService {

    /**
     * 保存附件至附件表
     * @param businessKeyValue 业务单号
     * @param fileList 传递过来的附件信息
     * @return
     */
    ResultVo<String> insertFileInfo(String businessKeyValue, List<FileUpload> fileList, String createUser, String fileType,String businessType);


    ResultVo<String> insertFileInfo2(List<OpsAttachedFileManageVO> info);


    /**
     * 可选参数: 业务单号/随机文件名/真实文件名/文件路径/文件类型/业务类型/创建人/更新人/
     * 业务单号/随机文件名/真实文件名/文件路径, 不可都为空 需保证必须输入一个
     * @param info
     * @return
     */
    ResultVo<List<OpsAttachedFileManageDO>> findAttacheFiledManageInfo(OpsAttachedFileManageDO info);

    // 软删除附件
    ResultVo<String> delAttacheFileManageInfo(List<String> ids,String optUser);

    // 附件下载
    void dowmLoadAttacheFileManageInfo(String filePath,String randomFileName);

    // 前端上传附件信息
    ResultVo<String> uploadFileAttacheFileManageInfoToServer( MultipartFile[] fileList,
                                                              String keyValue,String createUser);

}
