package com.sales.ops.dto.po;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/7/30 13:38
 */
public class MdmResponseDto implements Serializable {

    private Boolean success;

    private String code;

    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
