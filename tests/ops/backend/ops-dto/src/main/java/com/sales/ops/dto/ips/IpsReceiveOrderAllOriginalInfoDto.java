package com.sales.ops.dto.ips;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 记录接收的业务系统的原始订单数据
 * @author: B91717
 */
public class IpsReceiveOrderAllOriginalInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sourceType; // 数据来源方式及接口形式

    private String sourceSystem; // 业务系统源id，O OPS系统，M 制造PMS系统，O_P新采购系统，I_P集团内采购系统

    private String content; // 原始数据的json串

    private String srcOrderNo; // 原始单号

    private String srcOrderItemNo; // 原始项号

    private String batchNo; // 批次号概念

    private Integer batchNum; // 批次数量，用于抽取时的校验

    private Date createTime; // 写入时间

    private String createUser; // 写入人

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSrcOrderNo() {
        return srcOrderNo;
    }

    public void setSrcOrderNo(String srcOrderNo) {
        this.srcOrderNo = srcOrderNo;
    }

    public String getSrcOrderItemNo() {
        return srcOrderItemNo;
    }

    public void setSrcOrderItemNo(String srcOrderItemNo) {
        this.srcOrderItemNo = srcOrderItemNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(Integer batchNum) {
        this.batchNum = batchNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
