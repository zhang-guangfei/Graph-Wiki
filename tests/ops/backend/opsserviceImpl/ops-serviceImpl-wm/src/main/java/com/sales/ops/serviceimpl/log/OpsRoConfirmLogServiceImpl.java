package com.sales.ops.serviceimpl.log;

import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.db.batchdao.OpsRoConfirmLogBatchDao;
import com.sales.ops.db.entity.OpsRoConfirmLog;
import com.sales.ops.dto.flux.RoConfirmItem;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.service.log.OpsRoConfirmLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OpsRoConfirmLogServiceImpl implements OpsRoConfirmLogService {


    @Autowired
    private OpsRoConfirmLogBatchDao opsRoConfirmLogBatchDao;


    /**
     * @description 发票确认结果日志
     * @author C12961
     * @date 2023/3/14 17:10
     */
    @Override
    public void insertInvoiceConfirmItemLog(List<RoConfirmItem> resultList) {
        if (CollectionUtils.isEmpty(resultList)) {
            return;
        }
        List<OpsRoConfirmLog> list = resultList.stream().map(item -> {
            OpsRoConfirmLog confirmLog = new OpsRoConfirmLog();
            confirmLog.setRoId(item.getRoId());
            confirmLog.setDoId(item.getDoid());
            confirmLog.setReceiveType(item.getReceiveType());
            confirmLog.setPcoId(item.getPcoid());
            confirmLog.setInvoiceNo(item.getInvoiceNo());
            confirmLog.setStatus(1);
            confirmLog.setCreator(UserDto.WMS.getUserName());
            confirmLog.setCreTime(new Date());
            return confirmLog;
        }).collect(Collectors.toList());
        // 手动批量分页插入
        Map<Integer, List<OpsRoConfirmLog>> mapBarcode = SplitBatchUtils.getInsertBatchBySqlserver(list, OpsRoConfirmLog.class);
        for (Map.Entry<Integer, List<OpsRoConfirmLog>> entry : mapBarcode.entrySet()) {
            log.info("insertBatchConfirmLog Msg: 总行数:{},页码:{}/{},写入行数:{}", list.size(), entry.getKey() + 1, mapBarcode.size(), entry.getValue().size());
            opsRoConfirmLogBatchDao.insertRoConfirmLogBatch(entry.getValue());
        }
    }

}
