package com.smc.smccloud.common;


import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.order.RcvMasterDO;
import com.smc.smccloud.model.receiveorder.RcvOrderVO;

import java.util.ArrayList;
import java.util.List;

public class SMCCodeUtil {

    /**
     * 日本ship data出厂地转供应商代码
     * @param whereCode
     * @return
     */
    public static String getSupplierCodeFromJPWhereCode(String whereCode)
    {
        if(PublicUtil.isNotEmpty(whereCode))
        {
            return "";
        }
        switch (whereCode)
        {
            case "J":
                return "JP";
            case "S":
                return "SG";
            case "K":
                    return "KR";
            case "V":
                return "VN";
            case "I":
                return "IN";
            case "U":
                return "US";
            case "C":
                return "CN";
            case "T":
                    return "TJ";
            default:
                return whereCode;
        }
    }
}
