package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/8/20 14:25
 */
public interface DataTypeService {
    ResultVo<List<DataCodeVO>> getDataCodes(String classCode);
}
