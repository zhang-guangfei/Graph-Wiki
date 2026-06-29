package com.sales.ops.service.ba;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.common.BomSelectParam;
import com.sales.ops.dto.common.BomSelectResult;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.stereotype.Service;

/**
 * @author ：C14717
 * @version: $ bugid:17799
 * @description：
 * @date ：Created in 2025/6/13 9:19
 */
@Service
public interface BomSelectorService {
    //0.外部调用获取bom
    ResultVo<BomSelectResult> selectBom(BomSelectParam param) throws OpsException;
}
