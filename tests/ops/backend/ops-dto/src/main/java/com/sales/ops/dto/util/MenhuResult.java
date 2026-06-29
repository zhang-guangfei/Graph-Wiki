package com.sales.ops.dto.util;

import java.io.Serializable;

/**
 * @author C12961
 * @date 2023/1/6 9:22
 */
public class MenhuResult<T>  implements Serializable {


    private String code;
    private String message;
    private T content;
    private boolean success;

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

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
