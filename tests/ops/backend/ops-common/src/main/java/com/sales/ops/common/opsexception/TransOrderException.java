package com.sales.ops.common.opsexception;

/**
 * @author C12961
 * @date 2022/8/8 15:04
 */
public class TransOrderException extends OpsException{


    public TransOrderException(String className, String methodName, String errorMessage, Object... pArgs) {
        super(className, methodName, errorMessage, pArgs);
    }


}
