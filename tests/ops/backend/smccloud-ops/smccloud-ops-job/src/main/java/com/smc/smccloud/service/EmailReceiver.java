package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.ConnectEmailCondition;
import com.smc.smccloud.model.ParseEmailDTO;

import java.util.List;

/**
 * 定时作业(解析邮箱附件 导入数据)
 */
public interface EmailReceiver {

    /**
     * Downloads new messages and saves attachments to disk if any.
     */
//    ResultVo<String> receiveEmails(ConnectEmailCondition connectEmailCondition);
}
