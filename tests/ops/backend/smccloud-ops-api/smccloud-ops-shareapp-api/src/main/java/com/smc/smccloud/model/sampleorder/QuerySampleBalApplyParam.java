package com.smc.smccloud.model.sampleorder;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/10/26 15:14
 * @Descripton TODO
 */
@Data
public class QuerySampleBalApplyParam {

    // 订单号
    private String orderNo;
    // 申请号
    private String sampleBalApplyNo;

    // 处理状态
    private String handStatus;

    // 申请部门
    private List<String> applyDeptNo;

    // 申请类型
    private List<String> applyType;

    // 结转类型
    private List<String> balType;

    // 申请开始时间
    private String applyTimeStart;

    // 申请结束时间
    private String applyTimeEnd;

    private Page page;

}
