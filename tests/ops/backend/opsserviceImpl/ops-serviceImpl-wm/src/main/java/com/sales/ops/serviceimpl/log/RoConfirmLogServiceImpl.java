package com.sales.ops.serviceimpl.log;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.db.batchdao.OpsRoConfirmLogBatchDao;
import com.sales.ops.db.dao.OpsRoConfirmLogMapper;
import com.sales.ops.db.entity.OpsRoConfirmLog;
import com.sales.ops.db.entity.OpsRoConfirmLogExample;
import com.sales.ops.service.log.RoConfirmLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author C12961
 * @date 2023/4/20 10:25
 */
@Slf4j
@Service
public class RoConfirmLogServiceImpl implements RoConfirmLogService {
    @Autowired
    private OpsRoConfirmLogBatchDao opsRoConfirmLogBatchDao;

    @Autowired
    private OpsRoConfirmLogMapper opsRoConfirmLogMapper;

    @Override
    public Integer insertBatchConfirmLog(List<OpsRoConfirmLog> list) throws OpsException {
        Integer count = 0;
        Map<Integer, List<OpsRoConfirmLog>> mapBarcode = SplitBatchUtils.getInsertBatchBySqlserver(list, OpsRoConfirmLog.class);
        for (Map.Entry<Integer, List<OpsRoConfirmLog>> entry : mapBarcode.entrySet()) {
            log.info("insertBatchConfirmLog Msg: 总行数:{},页码:{}/{},写入行数:{}", list.size(), entry.getKey() + 1, mapBarcode.size(), entry.getValue().size());
            count += opsRoConfirmLogBatchDao.insertRoConfirmLogBatch(entry.getValue());
        }
        return count;
    }
    @Override
    public List<OpsRoConfirmLog> getRoConfirmLogsByInvoiceId(Long invoiceId,String invoiceNo){
        OpsRoConfirmLogExample ex = new OpsRoConfirmLogExample();
        OpsRoConfirmLogExample.Criteria criteria = ex.createCriteria();
        criteria.andInvoiceNoEqualTo(invoiceNo);
        if (invoiceId != null) {
            criteria.andInvoiceIdEqualTo(invoiceId);
        }
        List<OpsRoConfirmLog> opsRoConfirmLogs = opsRoConfirmLogMapper.selectByExample(ex);
        return opsRoConfirmLogs;
    }

}
