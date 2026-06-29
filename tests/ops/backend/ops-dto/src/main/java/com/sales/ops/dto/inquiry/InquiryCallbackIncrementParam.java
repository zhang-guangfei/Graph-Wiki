package com.sales.ops.dto.inquiry;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dxf 2025/05/29
 * 回调参数,用于增量获取供应商的回复信息
 */
public class InquiryCallbackIncrementParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long callbackIncrementLastid; // 用于存储增量的id
    private Date callbackIncrementLasttime; // 用于存储增量的时间

    public Long getCallbackIncrementLastid() {
        return callbackIncrementLastid;
    }

    public void setCallbackIncrementLastid(Long callbackIncrementLastid) {
        this.callbackIncrementLastid = callbackIncrementLastid;
    }

    public Date getCallbackIncrementLasttime() {
        return callbackIncrementLasttime;
    }

    public void setCallbackIncrementLasttime(Date callbackIncrementLasttime) {
        this.callbackIncrementLasttime = callbackIncrementLasttime;
    }
}
