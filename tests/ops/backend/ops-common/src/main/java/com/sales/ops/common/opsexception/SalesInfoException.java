package com.sales.ops.common.opsexception;

/**
 * @author C12961
 * @date 2022/10/21 9:09
 */
public class SalesInfoException extends OpsException{


    public SalesInfoException(String className, String methodName, String errorMessage, Object... pArgs) {
        super(className, methodName, errorMessage, pArgs);
    }





}
