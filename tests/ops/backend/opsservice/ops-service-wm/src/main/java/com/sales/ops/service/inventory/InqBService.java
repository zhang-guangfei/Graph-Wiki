package com.sales.ops.service.inventory;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.inqb.OpsInqbOrderAllotRequest;
import com.sales.ops.dto.inqb.OpsInqbOrderAllotResult;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/6/13 13:15
 */
public interface InqBService {
    List<OpsInqbOrderAllotResult> inqBGetCKRule(OpsInqbOrderAllotRequest param) throws OpsException;
}
