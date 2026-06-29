package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.binorder.ModelExpFreqByJobRequestDTO;
import com.smc.smccloud.model.binorder.ModelExpFreqByJobVO;

import javax.servlet.http.HttpServletResponse;

import java.util.*;


/**
 * @author wuweidong
 * @create 2023/6/8 14:01
 * @description
 */
public interface ModelExpFreqByJobService {
    ResultVo<PageInfo<ModelExpFreqByJobVO>> listBinTrialSalesBranchDetail(ModelExpFreqByJobRequestDTO request);
    void exporBinTrialSalesBranchDetail(ModelExpFreqByJobRequestDTO request, HttpServletResponse response) ;

    ResultVo<String> updateModelExpFreqByJob(Long jobId,Integer master);

    ResultVo<String>  calcModelExpFreqUpdModelInfoByJob(Long jobId, Date endDate);
    Map<String, Date> getLastMonthRange(Integer months );

//    ResultVo<String> calcModelExpFreqByJob(Long jobId);
}
