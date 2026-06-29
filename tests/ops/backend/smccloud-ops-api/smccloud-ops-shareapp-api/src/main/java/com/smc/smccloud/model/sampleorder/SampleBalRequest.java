package com.smc.smccloud.model.sampleorder;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/1/20 15:30
 * @Descripton TODO
 */

@Data
public class SampleBalRequest {

    // 客户代码
    private String customerNo;
    // 接单号
    private String rorderNo;
    // 型号
    private String modelNo;
    // 操作日期
    private String optDateStart;
    private String optDateEnd;
    // 结转日期
    private String inDateStart;
    private String inDateEnd;
    // 出库日期
    private String expDateStart;
    private String expDateEnd;
    // 创建日期
    private String creDateStart;
    private String creDateEnd;

    // 处理状态
    private List<String> optCode;
    // 申请号
    private String applyCode;
    // 申请方式
    private List<String> applyType;
    // 结转方式
    private List<String> balType;

    private List<String> deptNos;

}
