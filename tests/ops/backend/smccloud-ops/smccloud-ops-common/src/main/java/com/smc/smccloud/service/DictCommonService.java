package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeDO;
import com.smc.smccloud.model.DataTypeVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lyc
 * @Date 2023/1/10 16:31
 * @Descripton TODO
 */
public interface DictCommonService {
    /**
     * 根据classCode  获取字典表对象信息
     * @param classCode
     * @return
     */
    ResultVo<Map<String,String>>  getDataCodesMap(String classCode);
    /**
     * 根据classCode code 获取字典表对象信息
     * @param classCode
     * @param code
     * @return
     */
    ResultVo<DataTypeVO> getDataTypeCodesInfo(String classCode, String code);

    ResultVo<DataTypeVO> getDataTypeCodesInfoWithCatch(String classCode, String code);

    ResultVo<DataTypeVO> getDataTypeCodesInfoWithDS(String classCode, String code);

    /**
     * 获取仓库类型获取仓库代码集合
     * @param warehouseType 仓库类型 例如 master,sub,wt
     * @return
     */
    ResultVo<Map<String,String>>  getWarehouseMap(String warehouseType);
    /**
     * 获取仓库类型获取仓库代码集合
     * @param warehouseType 仓库类型 例如 master,sub,wt
     * @return
     */
    ResultVo<List<String>> getWarehouseCodeByWarehouseType(String warehouseType);

    /**
     * 获取传入仓库和包括它的分仓的仓库代码集合
     * @param warehouseCode 仓库代码如：KBJ，KSH，KGZ
     * @return
     */
    ResultVo<List<String>> getWarehouseCodesByWarehouseCodeForMasterAndSub(String warehouseCode);

    /**
     * 根据分类编码查询有效代码
     * 根据classCode 获取该编码下的所有  [有加缓存]
     */
    ResultVo<List<DataCodeVO>> getDataCodes(String classCode);


    ResultVo<List<DataCodeVO>> getDataCodesNotCache(String classCode);
    /**
     * 查询物流中心库
     * @return
     */
    List<String> getMasterWarehouseCodes();

    /**
     * 查出对应物流中心库
     * @param warehouseCode 物流中心库，分库，委托在库
     * @return
     */
    String getMasterWarehouseByCode(String warehouseCode);
}
