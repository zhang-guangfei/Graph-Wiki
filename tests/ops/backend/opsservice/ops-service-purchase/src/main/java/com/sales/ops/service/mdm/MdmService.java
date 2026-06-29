package com.sales.ops.service.mdm;

import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.po.MdmRequest;
import com.sales.ops.dto.po.MdmResponseDto;

import java.util.List;
import java.util.Map;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/7/30 13:48
 */
public interface MdmService {
    String getToken();

    void sendMdmData(Map<String, List<OpsRequestpurchase>> modelMap);

    MdmResponseDto callBusinessApi(List<MdmRequest> request);
}
