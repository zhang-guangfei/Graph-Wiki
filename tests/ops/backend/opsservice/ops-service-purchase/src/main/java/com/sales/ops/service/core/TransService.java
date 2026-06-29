package com.sales.ops.service.core;

import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.po.core.PoCalTransAndExportDateResultDto;
import com.sales.ops.dto.po.core.TransAndExportDateParamDto;
import com.sales.ops.dto.po.core.TransTypeDto;

import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/12/15 12:49
 */
public interface TransService {
    List<OpsRequestpurchase> calDlvInfo(List<OpsRequestpurchase> list, Date testDate);

    List<TransTypeDto> getTransIds(String supplier, String warehoue, String modelNo, Integer qty, String ordType,Boolean translation);

    // 制造指定出荷日计算
    PoCalTransAndExportDateResultDto getCalTransIdAndExportDate(TransAndExportDateParamDto param);
}
