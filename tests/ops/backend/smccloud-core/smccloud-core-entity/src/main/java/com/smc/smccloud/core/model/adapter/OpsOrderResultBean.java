package com.smc.smccloud.core.model.adapter;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/8/4 13:56
 * @Descripton TODO  共通接口回调门户固定返回实体
 */
@Data
public class OpsOrderResultBean {
    /**
     * 申请号--（申请号-项号）
     */
    private String applyNo;

    /**
     * 结果（成功、失败）
     */
    private String result;

    /**
     * 结果描述
     */
    private String resultDescription;

    // 变更采购单(运输方式/指定工厂出荷日) 生成新的单号
    private String newOrderNo;
}
