package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sales.ops.dto.attachedfile.OpsAttachedFileManageVO;
import com.sales.ops.enums.FileUploadTypeEnum;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.OpsAttachedFileManageMapper;
import com.smc.smccloud.model.OpsAttachedFileManageDO;
import com.smc.smccloud.model.UploadFileByUIVO;
import com.smc.smccloud.model.fileupload.FileUpload;
import com.smc.smccloud.service.OpsAttachedFileManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smc
 * @since 2023-10-10
 */
@Service
@Slf4j
public class OpsAttachedFileManageServiceImpl implements OpsAttachedFileManageService {

    @Value("${sales-file-upload-path.url}")
    private String salesFileUploadPath;

    @Value("${ops-file-upload-path.url}")
    private String opsFileUploadPath;

    @Resource
    private HttpServletResponse response;

    @Resource
    private OpsAttachedFileManageMapper opsAttachedFileManageMapper;

    /**
     * /ops/files/order
     * 最低售价: /minPrice/202310(yyyyMM格式年月)/xxx.txt
     * 样品: /sampleOrder/202310(yyyyMM格式年月)/xxx.txt
     * @return
     */
    @Override
    @DS("opsdb")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW )
    public ResultVo<String> insertFileInfo(String businessKeyValue, List<FileUpload> fileList,String createUser ,String fileType,String businessType ) {
        if(CollectionUtils.isEmpty(fileList)) {
            return ResultVo.failure("附件列表不可为空.");
        }
        if (StringUtils.isBlank(businessType)) {
            return ResultVo.failure("业务类型不可为空.");
        }
        OpsAttachedFileManageDO opsAttachedFileManageDO;
        try {
            for (FileUpload item : fileList) {
                if(item == null|| StringUtils.isBlank(item.getFilePath())) {
                    return ResultVo.failure("附件列表里不允许有空文件或者文件路径为空");
                }
                // 门户文件路径
                String url = salesFileUploadPath+PublicUtil.getStringByIndexOf(item.getFilePath(),File.separator,3)+item.getRandomFileName();
                log.info("{} 门户文件路径=> {}",businessKeyValue,url);
                // 转换ops文件保存路径
                String newFilePath = opsFileUploadPath + File.separator+ businessType +File.separator +fileType+File.separator+ DateUtil.getYearMonthCode(new Date()) + File.separator;
                String newUrl = newFilePath + item.getRandomFileName();
                log.info("{} ops保存门户文件路径=> {}",businessKeyValue,newUrl);
                try {
                    // 复制文件
                    FileUtil.copyFile(url,newUrl);
                } catch (Exception e) {
                    throw new BusinessException(businessKeyValue+"保存附件异常"+e.getMessage());
                }
                opsAttachedFileManageDO = new OpsAttachedFileManageDO();
                opsAttachedFileManageDO.setBusinessKeyValue(businessKeyValue);
                opsAttachedFileManageDO.setBusinessType(businessType);
                opsAttachedFileManageDO.setFileType(fileType);
                opsAttachedFileManageDO.setFilePath(newFilePath);
                opsAttachedFileManageDO.setRandomFileName(item.getRandomFileName());
                opsAttachedFileManageDO.setRealFileName(item.getRealFileName());
                opsAttachedFileManageDO.setDelFlag(false);
                opsAttachedFileManageDO.setCreateUser(createUser);
                opsAttachedFileManageDO.setCreateTime(new Date());
                // opsAttachedFileManageDO.setBatchNo(PublicUtil.getRandomBatchNo("S"));
                opsAttachedFileManageMapper.insert(opsAttachedFileManageDO);
            }
        } catch (Exception e) {
            log.error("{}保存{}附件异常 {}",businessKeyValue,fileType,e.getMessage(),e);
            throw new BusinessException(businessKeyValue+"保存附件异常"+e.getMessage());
        }
        return ResultVo.success("保存附件信息完毕.");
    }

    @Override
    @DS("opsdb")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW )
    public ResultVo<String> insertFileInfo2(List<OpsAttachedFileManageVO>  info) {
        if (CollectionUtils.isEmpty(info)){
            throw new BusinessException("新增附件入参不可为空");
        }

        try {
            for (OpsAttachedFileManageVO item : info) {

                if (StringUtils.isBlank(item.getCreateUser())) {
                    throw new BusinessException("创建人不可为空");
                }

                if(StringUtils.isBlank(item.getBusinessType())) {
                    throw new BusinessException("业务类型不可为空");
                }

                if(StringUtils.isBlank(item.getBusinessKeyValue())) {
                    throw new BusinessException("业务单号不可为空");
                }

                if(StringUtils.isBlank(item.getFileType()) || StringUtils.isBlank(item.getFilePath())
                        || StringUtils.isBlank(item.getRandomFileName()) || StringUtils.isBlank(item.getRealFileName())) {
                    throw new BusinessException("文件类型/文件路径/随机文件名/真实文件名,不可为空");
                }

                // item.setBatchNo(PublicUtil.getRandomBatchNo("S"));
                item.setDelFlag(false);
                item.setCreateTime(new Date());
                opsAttachedFileManageMapper.insert(BeanCopyUtil.copy(item,OpsAttachedFileManageDO.class));

            }
        } catch (Exception e) {
            log.error("保存附件异常 {}",e.getMessage(),e);
            throw new BusinessException("保存附件异常"+e.getMessage());
        }
        return ResultVo.success("保存附件信息完毕.");
    }

    @Override
    public ResultVo<List<OpsAttachedFileManageDO>> findAttacheFiledManageInfo(OpsAttachedFileManageDO info) {

        if (info == null) {
            return ResultVo.failure("入参不可为空");
        }

        if (StringUtils.isBlank(info.getBusinessKeyValue()) && StringUtils.isBlank(info.getRandomFileName())
                && StringUtils.isBlank(info.getRealFileName()) && StringUtils.isBlank(info.getFilePath())) {
            return ResultVo.failure("业务单号/随机文件名/真实文件名/文件路径,不可都为空");
        }

        LambdaQueryWrapper<OpsAttachedFileManageDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StringUtils.isNotBlank(info.getBusinessKeyValue()),OpsAttachedFileManageDO::getBusinessKeyValue,info.getBusinessKeyValue())
                .eq(StringUtils.isNotBlank(info.getRandomFileName()),OpsAttachedFileManageDO::getRandomFileName,info.getRandomFileName())
                .eq(StringUtils.isNotBlank(info.getRealFileName()),OpsAttachedFileManageDO::getRealFileName,info.getRealFileName())
                .eq(OpsAttachedFileManageDO::getDelFlag,false)
                .eq(StringUtils.isNotBlank(info.getFilePath()),OpsAttachedFileManageDO::getFilePath,info.getFilePath())
                .eq(StringUtils.isNotBlank(info.getFileType()),OpsAttachedFileManageDO::getFileType,info.getFileType())
                .eq(StringUtils.isNotBlank(info.getBusinessType()),OpsAttachedFileManageDO::getBusinessType,info.getBusinessType())
                .eq(StringUtils.isNotBlank(info.getCreateUser()),OpsAttachedFileManageDO::getCreateUser,info.getCreateUser())
                .eq(StringUtils.isNotBlank(info.getUpdateUser()),OpsAttachedFileManageDO::getUpdateUser,info.getUpdateUser());
        List<OpsAttachedFileManageDO> opsAttachedFileManageDOS = opsAttachedFileManageMapper.selectList(queryWrapper);
        return ResultVo.success(opsAttachedFileManageDOS);

    }

    @Override
    public ResultVo<String> delAttacheFileManageInfo(List<String> ids,String optUser) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultVo.failure("入参不可为空");
        }

        LambdaUpdateWrapper<OpsAttachedFileManageDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .in(OpsAttachedFileManageDO::getId,ids)
                .set(OpsAttachedFileManageDO::getDelFlag,true)
                .set(OpsAttachedFileManageDO::getUpdateUser,optUser)
                .set(OpsAttachedFileManageDO::getUpdateTime,new Date());
        try {
            opsAttachedFileManageMapper.update(null, updateWrapper);
        } catch (Exception e) {
            return ResultVo.failure("删除失败"+e.getMessage());
        }
        return ResultVo.success("删除成功");
    }

    @Override
    public void dowmLoadAttacheFileManageInfo(String filePath,String randomFileName) {
        if (StringUtils.isBlank(filePath)) {
            return;
        }
        log.info("结转可申请清单附件下载路径{}",filePath+randomFileName);
        FileUtil.downloadFileToResponse(filePath+randomFileName,response);
    }

    @Override
    public ResultVo<String> uploadFileAttacheFileManageInfoToServer( MultipartFile[] fileList,
                                                                    String keyValue, String createUser) {
        if (fileList == null || fileList.length == 0) {
            return ResultVo.failure("请选择需要上传的附件");
        }

        for (MultipartFile file : fileList) {
            String[] split = file.getOriginalFilename().split("\\.");
            String randowStr = PublicUtil.getRandomStr()+"."+split[1];
            String filePath = opsFileUploadPath + File.separator + FileUploadTypeEnum.SAMPLEORDERBAL.getBusinessType() + File.separator +
                    FileUploadTypeEnum.SAMPLEORDERBAL.getFileType()+File.separator + DateUtil.getYearMonthCode(new Date()) + File.separator;
            // 1.1 上传文件到服务器
            log.info("样品可结转清单上传附件url {} 随机文件名 {}",filePath,randowStr);
            uploadReturnFile(file,randowStr,filePath);

            // 1.2 保存文件信息到ops_attached_file_manage
            OpsAttachedFileManageDO opsAttachedFileManageDO = new OpsAttachedFileManageDO();
            opsAttachedFileManageDO.setBusinessType(FileUploadTypeEnum.SAMPLEORDERBAL.getBusinessType());
            opsAttachedFileManageDO.setBusinessKeyValue(keyValue);
            opsAttachedFileManageDO.setFileType(FileUploadTypeEnum.SAMPLEORDERBAL.getFileType());
            opsAttachedFileManageDO.setFilePath(filePath);
            opsAttachedFileManageDO.setRandomFileName(randowStr);
            opsAttachedFileManageDO.setRealFileName(file.getOriginalFilename());
            opsAttachedFileManageDO.setDelFlag(false);
            opsAttachedFileManageDO.setCreateUser(createUser);
            opsAttachedFileManageDO.setCreateTime(new Date());
            opsAttachedFileManageMapper.insert(opsAttachedFileManageDO);
        }
        return ResultVo.success("上传"+fileList.length+"个文件成功.");
    }
    public Boolean uploadReturnFile(MultipartFile multipartFile, String fileRandomName,String filePath) {
        OutputStream os = null;
        try {
            InputStream stream = multipartFile.getInputStream();
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(filePath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileRandomName);
            while ((len = stream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            log.error("文件上传时发生异常", e);
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
            } catch (IOException e) {
                log.error("文件上传时发生异常", e);
            }
        }
        return true;
    }
}
