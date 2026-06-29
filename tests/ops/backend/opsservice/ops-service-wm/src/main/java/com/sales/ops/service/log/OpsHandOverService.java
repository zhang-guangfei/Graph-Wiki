package com.sales.ops.service.log;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsHandover;

import java.util.List;

/**
 * @author ：c02483
 * @date ：Created in 2022/1/26 16:00
 * @description：交接确认
 */
public interface OpsHandOverService {

    void addHandOver(List<OpsHandover> opsHandovers) throws OpsException;

    List<OpsHandover> findHandOvers(String invoiceNo);

    List<OpsHandover> findHandOvers(String doId, String invoiceNo);
}
