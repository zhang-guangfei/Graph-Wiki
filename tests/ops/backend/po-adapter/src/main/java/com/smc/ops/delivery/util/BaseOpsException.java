package com.smc.ops.delivery.util;

/**
 * @author C02483
 * @version 1.0
 * @description: 异常信息
 * @date 2021/9/12 20:49
 */
public class BaseOpsException extends Exception{

    protected String className;
    protected String errorMessage;
    protected String methodName;
    protected Object[] args;
    protected Boolean canContinue = false;

    public BaseOpsException() {
    }

    public BaseOpsException(String className, String methodName, String errorMessage, Object... pArgs) {
        this.errorMessage = errorMessage;
        this.methodName = methodName;
        this.className = className;
        this.setArgs(pArgs);
    }

    @Override
    public String getMessage() {
        return this.errorMessage != null && !this.errorMessage.equals("") ? this.errorMessage : super.getMessage();
    }

    public String getFullMessage() {
        return this.errorMessage != null && !this.errorMessage.equals("") ? String.format(this.errorMessage, this.args) : super.getMessage();
    }

    @Override
    public String toString() {
        return String.format("BaseOpsException:className=%s,methodName=%s,errorMessage=%s", this.className, this.methodName, this.errorMessage);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Boolean getCanContinue() {
        return canContinue;
    }

    public void setCanContinue(Boolean canContinue) {
        this.canContinue = canContinue;
    }
}
