package com.sales.ops.service.inventory;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsExceptionHandle;
import com.sales.ops.dto.ba.ChangeYYDto;
import com.sales.ops.dto.easyexcel.DoConfirmCompensateTenTimeDto;
import com.sales.ops.dto.util.UserDto;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author
 * @version 1.0
 * @description: 出入库异常记录表
 * @date 2022/10/25 10:55
 */
public interface OpsExceptionHandleService {

    /**
     * bugid:10804
     *  WMS 下预约调用ops接口
     * @param list
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<ChangeYYDto> changeYY(List<ChangeYYDto> list);

    /**
     * 处理异常表
     *
     * @throws OpsException
     */
    int handleExceptionHandleToErrDoConfirm(OpsExceptionHandle obj) throws OpsException;

    @Transactional(rollbackFor = Exception.class)
    void saveMailToDb(String fileUrls);

    @Transactional(rollbackFor = Exception.class)
    void handStatus(OpsExceptionHandle excObj);

    @Transactional(rollbackFor = Exception.class)
    void handSucess(OpsExceptionHandle excObj);

    @Transactional(rollbackFor = Exception.class)
    void handFail(OpsExceptionHandle excObj, List<DoConfirmCompensateTenTimeDto> excelDtoList);

}
