package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.sampleorder.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2022-08-01
 */
public interface SampleOrderManageService extends IService<SampleOrderManageDO> {

    int insertSampleOrderManage(SampleOrderManageDO sampleOrderManageDO);

    /**
     * 展览展示品管理列表
     */
    ResultVo<PageInfo<SampleOrderManageVO>> listSampleOrderManage(SampleOrderManageQuery request, Page page);

    /**
     * 查询数据导出
     */
    void exportSampleOrderManage(SampleOrderManageQuery request);


    /**
     * 盘点票导出
     */
    void exportZlzsManageData(SampleOrderManageQuery request);


    /**
     * 发布展示品盘点票
     */
    ResultVo<String> pushZlzsSampleOrderManageForPdf(SampleOrderManageQuery request);

    /**
     * 单次销账
     */
    ResultVo<String> zlzsOrderWriteOff(SampleOrderManageVO dto);

    /**
     * 批量销账
     */
    ResultVo<String> batchImportWriteOffData(MultipartFile file, String loginUser);
    /**
     * 单次变更实物所在部门
     */
    ResultVo<String> upSampleOrderManageDeptNo(SampleOrderManageVO sampleOrderManageVO);

    /**
     * 批量变更实物所在部门
     */
    ResultVo<String> batchUpSampleOrderManageDeptNo(MultipartFile file, String loginUser);

    /**
     * 批量导入历史盘点票
     * @param file excel文件
     * @param loginUser 操作人
     * @return
     */
    ResultVo<String> batchImportSampleOrderManageData(MultipartFile file, String loginUser);

    /**
     * 单次导入盘点票
     * @param sampleOrderManageDO 操作对象
     * @return
     */
    ResultVo<String> importSampleOrderManageData(SampleOrderManageDO sampleOrderManageDO);

    /**
     * 单次编辑盘点票
     * @param sampleOrderManageDO 操作对象
     * @return
     */
    ResultVo<String> editSampleOrderManage(SampleOrderManageDO sampleOrderManageDO);

    void downLoadHistorySampleOrderManageExcel();
}
