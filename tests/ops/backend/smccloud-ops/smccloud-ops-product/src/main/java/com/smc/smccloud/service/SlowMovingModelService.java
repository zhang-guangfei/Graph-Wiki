package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.SlowMovingModel.SlowMovingModelVO;
import com.smc.smccloud.model.SlowMovingModel.SlowMovingRequest;
import org.springframework.web.multipart.MultipartFile;

public interface SlowMovingModelService {

    /**
     * 查询滞销品数据
     *
     * @param request
     * @return
     */
    ResultVo<PageInfo<SlowMovingModelVO>> listSlowMovingModelData(SlowMovingRequest request);

    /**
     * 导入数据 .xlsx
     * @param file
     * @return
     */
    ResultVo<String> importSlowModelData(MultipartFile file);


    ResultVo<String> updateSlowModelData(SlowMovingModelVO modelVO);

    ResultVo<String> delSlowModelData(Integer id);

    ResultVo<String> calcUpdateSlowModelOnHnad();
}