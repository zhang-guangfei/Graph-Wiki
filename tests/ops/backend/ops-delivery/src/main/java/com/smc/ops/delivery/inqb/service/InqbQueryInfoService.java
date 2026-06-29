package com.smc.ops.delivery.inqb.service;

import com.sales.ops.dto.inqb.InqbQueryRequestParam;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.List;

/**
 * INQB查询信息接口
 */
public interface InqbQueryInfoService {


    /**
     * INQb查询接口
     * 给门户提供的查询接口
     * 参数:型号（整型号），客户、用户、最终用户，数量（小于等于问询数量）
     * 返回可以使用的INQB申请信息，只返回申请号的集合即可
     * @param inqbQueryRequestParams
     * @return
     * @throws Exception
     */
    ResultVo<List<InqbQueryRequestParam>> getUsageInqbNoInfo(List<InqbQueryRequestParam> inqbQueryRequestParams) throws Exception;

}
