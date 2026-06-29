package com.sales.ops.dto.ips;

/**
 * @description:
 * "cancelMark": "",//取消标识
 * "cancelReasonCode": ""//取消原因
 * @author: B91717
 * @time: 2024/12/17 16:42
 */
public class IpsCancelInfoDto {

    private String cancelMark; // 取消标识
    private String cancelReasonCode; // 取消原因

    public String getCancelMark() {
        return cancelMark;
    }

    public void setCancelMark(String cancelMark) {
        this.cancelMark = cancelMark;
    }

    public String getCancelReasonCode() {
        return cancelReasonCode;
    }

    public void setCancelReasonCode(String cancelReasonCode) {
        this.cancelReasonCode = cancelReasonCode;
    }
}
