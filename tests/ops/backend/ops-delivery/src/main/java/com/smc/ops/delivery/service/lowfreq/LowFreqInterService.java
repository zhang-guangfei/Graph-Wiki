package com.smc.ops.delivery.service.lowfreq;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.LowFrequencyInterceptionChecklist;
import com.sales.ops.db.entity.OpsRequestpurchaseInterceptConfig;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/7/9 13:02
 */
public interface LowFreqInterService {

    List<String> getInterceptModelNos();

    Integer insertInterceptData(List<String> modleNos) throws OpsException;

    Integer updateInterceptDatePo(List<String> modelNos) throws OpsException;

}
