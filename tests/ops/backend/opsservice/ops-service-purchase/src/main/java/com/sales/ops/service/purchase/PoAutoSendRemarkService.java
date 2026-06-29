package com.sales.ops.service.purchase;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.HrOrganization;
import com.sales.ops.db.entity.OpsPoAutosendRemarkConfig;
import com.sales.ops.dto.query.PoAutoSendRemarkQO;
import com.sales.ops.dto.util.PageModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author B91717
 * @date 2023/08/24
 * @apiNote 采购自动发单，备注配置
 */
public interface PoAutoSendRemarkService {

    /**
     * 条件查询，自定义拦截清单查询
     */
    public PageInfo<OpsPoAutosendRemarkConfig> findAll(PageModel<PoAutoSendRemarkQO> pageModel);


    Integer  updateData(OpsPoAutosendRemarkConfig opsPoAutosendRemarkConfig) throws OpsException;

    Integer insertData(OpsPoAutosendRemarkConfig opsPoAutosendRemarkConfig) throws OpsException;

    void  deleteData(OpsPoAutosendRemarkConfig opsPoAutosendRemarkConfig) throws OpsException;

    void  restoreData(OpsPoAutosendRemarkConfig opsPoAutosendRemarkConfig) throws OpsException;
    /**
     * 批量编辑功能
     *  bug 10559 拦截配置 增加筛选，批量修改，删除，导出功能 B91717
     * @param list
     * @return
     * @throws OpsException
     */
//    Integer  updateDataBatch(List<OpsRequestpurchaseInterceptConfig> list) throws OpsException;
 // bug 10559 拦截配置 增加筛选，批量修改，删除，导出功能 B91717
    Integer deleteBatch(List<OpsPoAutosendRemarkConfig> list) throws OpsException;

    String importFile(MultipartFile file, String loginUser) throws Exception;

    List<HrOrganization> findDepartment();

}
