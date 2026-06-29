package com.sales.ops.web.controller.ba;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.common.BomSelectParam;
import com.sales.ops.dto.common.BomSelectResult;
import com.sales.ops.service.ba.BomSelectorService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/6/17 10:16
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/wm/api/bom")
public class SpecialBomController {

    @Autowired
    private BomSelectorService bomSelectorService;

    @GetMapping(value = "/bomSelector")
    public ResultVo<BomSelectResult> bomSelector(@RequestBody BomSelectParam param) throws OpsException {
        return bomSelectorService.selectBom(param);
    }
}
