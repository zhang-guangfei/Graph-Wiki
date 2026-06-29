package com.smc.smccloud.model.shikomi;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/4/14 16:38
 * @Descripton TODO
 */
@Data
public class ShikomiCallbackVO {

    /**
     * 处理状态: 0-处理失败, 1-处理成功.
     */
    private Integer status;

    /**
     * 处理失败原因
     */
    private String reason;

    private ShikomiCallbackMaster shikomi;


}
