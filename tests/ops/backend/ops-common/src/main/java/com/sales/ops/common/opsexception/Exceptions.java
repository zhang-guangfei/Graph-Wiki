package com.sales.ops.common.opsexception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author C02483
 * @version 1.0
 * @description: 异常信息
 * @date 2021/9/12 20:51
 */
public class Exceptions {

    public static String c_ArgsNoEnough = "参数不全";
    public static String c_ConcurrencyOperate = "并发操作异常";
    public static String c_SystemError = "系统执行异常！";
    public static String c_PermissionDenied = "无权限";

    public Exceptions() {
    }

    public static RuntimeException unchecked(Exception e) {
        return e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }

    public static String getStackTraceAsString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
        for (Object cause = ex; cause != null; cause = ((Throwable) cause).getCause()) {
            Class[] var3 = causeExceptionClasses;
            int var4 = causeExceptionClasses.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Class<? extends Exception> causeClass = var3[var5];
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static BaseOpsException atomException(String errMessage, Object... args) {
        StackTraceElement traceElement = Thread.currentThread().getStackTrace()[2];
        String className = traceElement.getClassName();
        String methodName = traceElement.getMethodName();
        int lineNumber = traceElement.getLineNumber();
        return new BaseOpsException(className, methodName, errMessage, args);
    }

    public static OpsException OpsException(String errMessage, Object... args) {
        StackTraceElement traceElement = Thread.currentThread().getStackTrace()[2];
        String className = traceElement.getClassName();
        String methodName = traceElement.getMethodName();
        int lineNumber = traceElement.getLineNumber();
        return new OpsException(className, methodName, errMessage, args);
    }

    public static TransOrderException TransOrderException(String errMessage, Object... args) {
        StackTraceElement traceElement = Thread.currentThread().getStackTrace()[2];
        String className = traceElement.getClassName();
        String methodName = traceElement.getMethodName();
        int lineNumber = traceElement.getLineNumber();
        return new TransOrderException(className, methodName, errMessage, args);
    }

    public static SalesInfoException SalesInfoException(ErrorCode errorCode, Object... args) {
        StackTraceElement traceElement = Thread.currentThread().getStackTrace()[2];
        String className = traceElement.getClassName();
        String methodName = traceElement.getMethodName();
        return new SalesInfoException(className, methodName, errorCode.getDesc(), args);
    }

    public static AdjustOrderException AdjustOrderException(String errMessage, Object... args) {
        StackTraceElement traceElement = Thread.currentThread().getStackTrace()[2];
        String className = traceElement.getClassName();
        String methodName = traceElement.getMethodName();
        return new AdjustOrderException(className, methodName, errMessage, args);
    }

}
