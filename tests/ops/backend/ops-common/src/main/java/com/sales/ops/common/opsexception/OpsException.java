package com.sales.ops.common.opsexception;

/**
 * @author C02483
 * @version 1.0
 * @description: 异常信息
 * @date 2021/9/12 20:50
 */
public class OpsException extends BaseOpsException {

    private Boolean systemError = false;

    public OpsException(String className, String methodName, String errorMessage, Object... pArgs) {
        this.errorMessage = errorMessage;
        this.methodName = methodName;
        this.className = className;
        this.setArgs(pArgs);
    }

    public static OpsException CreateSystemError(String errorMessage, Object... args) {
        OpsException result = Exceptions.OpsException(errorMessage, args);
        result.setSystemError(true);
        return result;
    }

    public static OpsException CreateNoSystemError(String errorMessage, Object... args) {
        OpsException result = Exceptions.OpsException(errorMessage, args);
        result.setSystemError(false);
        return result;
    }

    public static OpsException CreateSystemError(String className, String methodName, String errorMessage, Object... args) {
        OpsException result = new OpsException(className, methodName, errorMessage, args);
        result.setSystemError(true);
        return result;
    }

    public static OpsException CreateNoSystemError(String className, String methodName, String errorMessage, Object... args) {
        OpsException result = new OpsException(className, methodName, errorMessage, args);
        result.setSystemError(false);
        return result;
    }

    @Override
    public String getMessage() {
        return this.errorMessage != null && !this.errorMessage.equals("") ? this.errorMessage : super.getMessage();
    }

    @Override
    public String getFullMessage() {
        return this.errorMessage != null && !this.errorMessage.equals("") ? String.format(this.errorMessage, this.args) : super.getMessage();
    }

    @Override
    public String toString() {
        return String.format("AtomException:className=%s,methodName=%s,errorMessage=%s", super.getClassName(), super.getMethodName(), this.getErrorMessage());
    }

    public Boolean getSystemError() {
        return this.systemError;
    }

    public void setSystemError(Boolean systemError) {
        this.systemError = systemError;
    }

}
