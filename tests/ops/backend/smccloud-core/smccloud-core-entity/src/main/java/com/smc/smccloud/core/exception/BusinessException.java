package com.smc.smccloud.core.exception;

import com.smc.smccloud.core.model.ErrorCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * BusinessException
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -3911456266524956017L;
    /**
     * 异常码
     */
    protected int code;
    protected String message;

    public BusinessException() {
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public BusinessException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
    }

    public BusinessException(ErrorCodeEnum codeEnum, Object... args) {
        super(String.format(codeEnum.msg(), args));
        this.code = codeEnum.code();
        this.message = codeEnum.msg();
    }
}
