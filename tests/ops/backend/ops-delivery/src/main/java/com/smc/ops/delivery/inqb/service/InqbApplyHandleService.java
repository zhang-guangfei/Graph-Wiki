package com.smc.ops.delivery.inqb.service;

import com.sales.ops.db.entity.OpsInqbCodeConfig;
import com.sales.ops.dto.inqb.InqbApplyVerifyReurn;
import com.sales.ops.dto.inqb.InqbSalesApplyAddParam;
import com.sales.ops.dto.inqb.InqbSalesApplyAddReturn;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 催促处理申请方法
 */
public interface InqbApplyHandleService {

    /**
     * INQB申请校验
     * ①型号必须是供应商可生产的型号，查询product delivery表
     * ②最终用户，型号为查询条件，如果存在3日内的申请单则不允许问询。
     * @param modelno,enduser
     * @return
     */
    ResultVo<String> inqbVerify(String modelno,String enduser,Integer quantity);


    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> inqApplyAdd(List<InqbSalesApplyAddParam> list) throws Exception ;
    /**
     * 门户接入 inqB申请接口
     * @param inqbSalesApplyAddParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    ResultVo<InqbSalesApplyAddReturn> salesInqbAdd(InqbSalesApplyAddParam inqbSalesApplyAddParam) throws Exception;


    List<OpsInqbCodeConfig> getInqbCodeConfig() throws Exception;

    /**
     * 门户提交校验的初始接口
     * @param inqbSalesApplyVerifyReurn
     * @return
     * @throws Exception
     */
    ResultVo<InqbApplyVerifyReurn> salesInqbValid(InqbApplyVerifyReurn inqbSalesApplyVerifyReurn) throws Exception;


}
