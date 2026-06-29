package com.smc.ops.delivery.service.commonservice;

import com.smc.ops.delivery.model.DataCodeVO;
import com.smc.ops.delivery.model.DataTypeVO;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.List;

/**
 * @Author lyc
 * @Date 2024/2/27 11:51
 * @Descripton TODO
 */
public interface DictCommonService {
    ResultVo<List<DataCodeVO>> getDataCodes(String classCode);

    String getWarehouseCodeBySMCCode(String smcCode, int extNote);

    ResultVo<String> generatorBillNo(String billType);

    DataTypeVO getDataCodesInfo(String classCode, String code);

    ResultVo<Boolean> updateExtNote2(Long id ,String extNote2,String curExtNote2);


    ResultVo<Boolean> updateExtNote1(Long id ,String extNote2,String curExtNote2);
}
