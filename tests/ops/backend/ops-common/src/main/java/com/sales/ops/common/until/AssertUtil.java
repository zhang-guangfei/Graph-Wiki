package com.sales.ops.common.until;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import org.apache.commons.lang.StringUtils;


/**
 * @author C12961
 * @date 2022/10/18 14:52
 */
public class AssertUtil {

    public static void isTrue(Boolean expression, String msg) throws OpsException {
        if (!expression) {
            throw Exceptions.OpsException(msg);
        }
    }

    public static void isFalse(Boolean expression, String msg) throws OpsException {
        if (expression) {
            throw Exceptions.OpsException(msg);
        }
    }


    public static void isBlank(String str, String msg) throws OpsException {
        if (StringUtils.isNotBlank(str)) {
            throw Exceptions.OpsException(msg);
        }
    }

    public static void notBlank(String str, String msg) throws OpsException {
        if (StringUtils.isBlank(str)) {
            throw Exceptions.OpsException(msg);
        }
    }

    public static void isNull(Object obj, String msg) throws OpsException {
        if (obj != null) {
            throw Exceptions.OpsException(msg);
        }
    }

    public static void notNull(Object obj, String msg) throws OpsException {
        if (obj == null) {
            throw Exceptions.OpsException(msg);
        }
    }


}
