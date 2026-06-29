package com.sales.ops.dto.order;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops 提交wms要上传的数据查询条件
 * @date ：Created in 2021/10/27 17:09
 */
public class OpsWmOrderTaskCondition {

    private String wmOrderId; //指令号

    private String wmOrderType;//指令类型:RO DO PCO

    private String taskType; //任务类型:order  barcode

    private Integer flag; //初始0,1成功，2失败 3挂起 4 已读

    private String limit;//查询行数；

    private String msg;//

    private Integer delFlag;

    private List<String> wmOrderIdList;

    public String getWmOrderId() {
        return wmOrderId;
    }

    public void setWmOrderId(String wmOrderId) {
        this.wmOrderId = wmOrderId;
    }

    public String getWmOrderType() {
        return wmOrderType;
    }

    public void setWmOrderType(String wmOrderType) {
        this.wmOrderType = wmOrderType;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getWmOrderIdList() {
        return wmOrderIdList;
    }

    public void setWmOrderIdList(List<String> wmOrderIdList) {
        this.wmOrderIdList = wmOrderIdList;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
