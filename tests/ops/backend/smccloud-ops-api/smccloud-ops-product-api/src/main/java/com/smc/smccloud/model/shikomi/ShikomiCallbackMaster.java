package com.smc.smccloud.model.shikomi;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/4/14 16:39
 * @Descripton TODO
 */
@Data
public class ShikomiCallbackMaster {

    /**
     * 主单申请号
      */
    private String applyNo;

    /**
     * shikomi号
     */
    private String shikomiNo;

    /**
     *  申请类型 1:新建 2:中止 3:继续
     */
    private Integer applyType;

    private List<ShikomiCallbackDetail> detailItem;



}
