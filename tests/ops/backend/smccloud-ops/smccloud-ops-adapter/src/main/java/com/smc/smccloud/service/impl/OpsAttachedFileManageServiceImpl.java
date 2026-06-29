//package com.smc.smccloud.service.impl;
//
//import com.baomidou.dynamic.datasource.annotation.DS;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.smc.smccloud.core.exception.BusinessException;
//import com.smc.smccloud.core.model.ResultVo.ResultVo;
//import com.smc.smccloud.core.model.enums.FileUploadTypeEnum;
//import com.smc.smccloud.core.utils.DateUtil;
//import com.smc.smccloud.core.utils.FileUtil;
//import com.smc.smccloud.core.utils.PublicUtil;
//import com.smc.smccloud.mapper.OpsAttachedFileManageMapper;
//import com.smc.smccloud.model.OpsAttachedFileManageDO;
//import com.smc.smccloud.model.fileupload.FileUpload;
//import com.smc.smccloud.service.OpsAttachedFileManageService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.util.Collection;
//import java.util.Date;
//import java.util.List;
//
///**
// * <p>
// *  服务实现类
// * </p>
// *
// * @author smc
// * @since 2023-10-10
// */
//@Service
//@Slf4j
//public class OpsAttachedFileManageServiceImpl extends ServiceImpl<OpsAttachedFileManageMapper, OpsAttachedFileManageDO> implements OpsAttachedFileManageService {
//
//    @Value("${sales-file-upload-path.url}")
//    private String salesFileUploadPath;
//
//    @Value("${ops-file-upload-path.url}")
//    private String opsFileUploadPath;
//
//    @Resource
//    private OpsAttachedFileManageMapper opsAttachedFileManageMapper;
//
//    /**
//     * /ops/files/order
//     * 最低售价: /minPrice/202310(yyyyMM格式年月)/xxx.txt
//     * 样品: /sampleOrder/202310(yyyyMM格式年月)/xxx.txt
//     * @return
//     */
//    @Override
//    @DS("opsdb")
//    @Transactional(rollbackFor = Exception.class)
//    public ResultVo<String> insertFileInfo(String businessKeyValue, List<FileUpload> fileList,String createUser) {
//        if(CollectionUtils.isEmpty(fileList)) {
//            return ResultVo.failure("附件列表不可为空.");
//        }
//        OpsAttachedFileManageDO opsAttachedFileManageDO;
//        String fileType = "";
//        try {
//            for (FileUpload item : fileList) {
//                if(item == null|| StringUtils.isBlank(item.getFilePath())) {
//                    return ResultVo.failure("附件列表里不允许有空文件或者文件路径为空");
//                }
//                fileType = item.getFileType();
//                // 门户文件路径
//                String url = salesFileUploadPath+PublicUtil.getStringByIndexOf(item.getFilePath(),"/",3)+item.getRandomFileName();
//                log.info("{} 门户文件路径=> {}",businessKeyValue,url);
//                // 转换ops文件保存路径
//                String newFilePath = opsFileUploadPath+"/order/"+fileType+"/"+ DateUtil.getYearMonthCode(new Date()) + "/";
//                String newUrl = newFilePath + item.getRandomFileName();
//                log.info("{} ops保存门户文件路径=> {}",businessKeyValue,newUrl);
//                try {
//                    // 复制文件
//                    FileUtil.copyFile(url,newUrl);
//                } catch (Exception e) {
//                    throw new BusinessException(businessKeyValue+"保存附件异常"+e.getMessage());
//                }
//                opsAttachedFileManageDO = new OpsAttachedFileManageDO();
//                opsAttachedFileManageDO.setBusinessKeyValue(businessKeyValue);
//                opsAttachedFileManageDO.setBusinessType(FileUploadTypeEnum.getBusinessTypeByFileType(fileType));
//                opsAttachedFileManageDO.setFileType(item.getFileType());
//                opsAttachedFileManageDO.setFilePath(newFilePath);
//                opsAttachedFileManageDO.setRandomFileName(item.getRandomFileName());
//                opsAttachedFileManageDO.setRealFileName(item.getRealFileName());
//                opsAttachedFileManageDO.setDelFlag(false);
//                opsAttachedFileManageDO.setCreateUser(createUser);
//                opsAttachedFileManageDO.setCreateTime(new Date());
//                opsAttachedFileManageMapper.insert(opsAttachedFileManageDO);
//            }
//        } catch (Exception e) {
//            log.error("{}保存{}附件异常 {}",businessKeyValue,fileType,e.getMessage(),e);
//            throw new BusinessException(businessKeyValue+"保存附件异常"+e.getMessage());
//        }
//        return ResultVo.success("保存附件信息完毕.");
//    }
//}
