package com.sales.ops.dto.po.core;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/12/15 14:40
 */
public class TransTypeDto {

    public TransTypeDto(){}

    public TransTypeDto(String transId,String transName,Integer usageType){
        this.transId = transId;
        this.transName = transName;
        this.usageType = usageType;
    }


    private String transId;

    private String transName;

    private Integer usageType; // 0 自动计算 1 // 特殊计算

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public Integer getUsageType() {
        return usageType;
    }

    public void setUsageType(Integer usageType) {
        this.usageType = usageType;
    }
}
