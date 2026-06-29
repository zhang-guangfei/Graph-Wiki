package com.smc.smccloud.model.sampleorder;

import lombok.Data;
import org.apache.commons.codec.language.bm.Lang;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/7/16 17:58
 * @Descripton TODO
 */
@Data
public class SplitSampleBalVO {

    private List<Long> ids;
    private String id;
    private int quantity;
    private String balType;
    private String deptNo;
    private String hlCode;
    private String userNo; // 操作人

}
