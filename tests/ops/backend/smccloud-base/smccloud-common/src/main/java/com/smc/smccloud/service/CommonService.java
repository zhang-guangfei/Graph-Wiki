package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.Set;

public interface CommonService {

     /**
      * 生成一个单号
      */
     ResultVo<String> generatorBillNo(String billType);

     /**
      * 生成一个带时间前缀的单号
      * @param billType
      * @return
      */
     ResultVo<Set<String>> batchGeneratorBillNo(String billType, Integer number);
}
