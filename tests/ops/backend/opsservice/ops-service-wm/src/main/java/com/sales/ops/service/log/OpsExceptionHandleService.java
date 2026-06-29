package com.sales.ops.service.log;

/**
 * @author C12961
 * @date 2023/4/20 10:44
 */
public interface OpsExceptionHandleService {
    void addRoConfirmExceptionHandle(String roId, String errTypeFlag, String msgId, String inputMsg, String
            outMsg, String modelNo, String qty, String warehouseCode, String userName);
}
