package com.smc.ops.delivery.inqb.service.impl;

import com.sales.ops.dto.inqb.InqbQueryRequestParam;
import com.smc.ops.delivery.inqb.service.InqbQueryInfoService;
import com.smc.ops.delivery.mapper.inqb.InqbApplyMapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:INQB查询相关接口
 * @author: B91717
 * @time: 2024/7/9 10:59
 */
@Service
@Slf4j
public class InqbQueryInfoServiceImpl implements InqbQueryInfoService {

    @Resource
    private InqbApplyMapper inquiryMapper;

    /**
     * INQb查询接口
     * 给门户提供的查询接口
     * 参数:型号（整型号），客户、用户、最终用户，数量（小于等于问询数量） 即问询数量 大于等于请求数量
     * 返回可以使用的INQB申请信息，只返回申请号的集合即可
     *
     * @param inqbQueryRequestParams
     * @return
     * @throws Exception
     */
    @Override
    public ResultVo<List<InqbQueryRequestParam>> getUsageInqbNoInfo(List<InqbQueryRequestParam> inqbQueryRequestParams) throws Exception {
        if (CollectionUtils.isEmpty(inqbQueryRequestParams)) {
            return ResultVo.failure("请求参数为空，请补充后重试");
        }
        List<InqbQueryRequestParam> resultList = new ArrayList<>();
        // 循环参数集合，分开判断可用的清单，客户用户最终用户分别进行判断
        for (InqbQueryRequestParam param : inqbQueryRequestParams) {
            // 查询主表的可用清单
            List<String> applyNoList = inquiryMapper.getAvailableApplyNo(param);
            if (!CollectionUtils.isEmpty(applyNoList)) {
                // 根据回复时间进行倒序，方便门户取最新回复的
                param.setInqbApplyNoList(applyNoList);
            }
            resultList.add(param);
        }
        if (CollectionUtils.isEmpty(resultList)) {
            return ResultVo.failure("没有可用的INQB清单");
        }
        return ResultVo.success(resultList);
    }
}
