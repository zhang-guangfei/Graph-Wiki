package com.smc.ops.delivery.service.commonservice;

import com.smc.ops.delivery.model.DataCodeVO;
import com.smc.ops.delivery.model.DataTypeVO;
import com.smc.ops.delivery.util.ResultVo;

import java.util.List;

/**
 * @Author lyc
 * @Date 2024/2/27 11:51
 * @Descripton TODO
 */
public interface DictCommonService {



    DataTypeVO getDataCodesInfo(String classCode, String code);

    ResultVo<Boolean> updateExtNote2(Long id ,String extNote2,String curExtNote2);




}
