package com.sales.ops.serviceimpl.log;

import com.sales.ops.db.dao.OpsExceptionHandleMapper;
import com.sales.ops.db.entity.OpsExceptionHandle;
import com.sales.ops.service.log.OpsExceptionHandleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class OpsExceptionHandleServiceImpl implements OpsExceptionHandleService {
    @Autowired
    private OpsExceptionHandleMapper exceptionHandleMapper;

    @Override
    public void addRoConfirmExceptionHandle(String roId, String errTypeFlag, String msgId, String inputMsg, String
            outMsg, String modelNo, String qty, String warehouseCode, String userName) {
        OpsExceptionHandle exceptionHandle = new OpsExceptionHandle();
        exceptionHandle.setBusinessType("RO");
        exceptionHandle.setApiName("wmRoConfirm");
        exceptionHandle.setErrType(errTypeFlag);
        exceptionHandle.setMsgId(msgId);
        exceptionHandle.setStatus(0);
        exceptionHandle.setInputMsg(inputMsg);
        exceptionHandle.setOutputMsg(outMsg);
        exceptionHandle.setParameter1(roId);//ROID
        exceptionHandle.setParameter2(modelNo);//型号
        exceptionHandle.setParameter3(qty);//数量
        exceptionHandle.setParameter4(warehouseCode);//仓库号
        exceptionHandle.setCreateUser(userName);
        exceptionHandle.setCreateTime(new Date());
        exceptionHandle.setUpdateUser(userName);
        exceptionHandle.setUpdateTime(new Date());
        exceptionHandleMapper.insertSelective(exceptionHandle);
    }
}