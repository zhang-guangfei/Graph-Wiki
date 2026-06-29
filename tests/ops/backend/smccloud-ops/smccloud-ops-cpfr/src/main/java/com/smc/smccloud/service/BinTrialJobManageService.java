package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.binorder.BinTrialJobManageDO;
import com.smc.smccloud.model.binorder.BinTrialJobRequestDTO;
import com.smc.smccloud.model.binorder.BinTrialJobManageVO;

import java.util.List;

/**
 * @author wuweidong
 * @create 2023/6/2 10:19
 * @description


 */
public interface BinTrialJobManageService {
    /**
     * 分页显示
     * @param request
     * @return
     */
    ResultVo<PageInfo<BinTrialJobManageVO>>listBinTrialJobManageData (BinTrialJobRequestDTO request);

    /**
     * 检查任务状态
     * @param jobId
     * @return
     */
    ResultVo<BinTrialJobManageDO> checkBinTrialJobStatus(Long jobId);
    /**
     * 保存
     * @param jobManageVO
     * @return
     */
    ResultVo<String> saveBinTrialJobManager(BinTrialJobManageVO jobManageVO);

    /**
     * 根据ID集合删除
     * @param ids
     * @return
     */
    ResultVo<String> deleteBinTrialJobManager(List<Long> ids);

    /**
     * 列出数据
     * @param request
     * @return
     */
    ResultVo<List<BinTrialJobManageVO>> getBinTrialJobManageData(BinTrialJobRequestDTO request);

    /**
     * 复制新的job
     * @param request
     * @return
     */
    ResultVo<String> copyBinTrialJobManager(Long jobId);

    /**
     * 复制新的job
     * @param request
     * @return
     */
    ResultVo<String> sumitBinTrialJob(List<Long> ids);

    /**
     * 执行Bin计算
     * @param ids
     * @return
     */
    ResultVo<String> runBinTrialJob();

}
