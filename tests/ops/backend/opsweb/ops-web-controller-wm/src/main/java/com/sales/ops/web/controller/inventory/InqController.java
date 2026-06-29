package com.sales.ops.web.controller.inventory;

import com.sales.ops.dto.delivery.ExpPoDto;
import com.sales.ops.dto.delivery.InqAExpPoRequestDto;
import com.sales.ops.dto.inqb.OpsInqbOrderAllotRequest;
import com.sales.ops.dto.inqb.OpsInqbOrderAllotResult;
import com.sales.ops.service.delivery.exp_po.ExpPoService;
import com.sales.ops.service.inventory.InqBService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/6/13 15:01
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/wm")
public class InqController {

    @Autowired
    private InqBService inqBService;

    @Autowired
    private ExpPoService expPoService;

    /**
     * bugid:14454 inq-b 订单分配接口
     * @param param
     * @return
     */
    @PostMapping(value = "/inq/b")
    public ResultVo<List<OpsInqbOrderAllotResult>> inqBGetCKRule(@RequestBody OpsInqbOrderAllotRequest param) {
        try {
            List<OpsInqbOrderAllotResult> opsInqbOrderAllotResults = inqBService.inqBGetCKRule(param);
            return ResultVo.success(opsInqbOrderAllotResults);
        } catch (Exception ex) {
            return ResultVo.failure(ex.getMessage());
        }
    }

    /**
     * bugid:14912 20240827 c14717
     * inq-a 根据输入的客户期望货期倒算相应的采购单的期望发货日
     * @param param
     * @return
     */
    @PostMapping(value = "/inq/a/exp")
    public ResultVo<List<ExpPoDto>> inqAGetPoExpDate(@RequestBody InqAExpPoRequestDto param) {
        try {
            List<ExpPoDto> expPoDtos = expPoService.calculatePoExpDate(param.getExpDate(), param.getOrderNo(), param.getOrderItem());
            return ResultVo.success(expPoDtos);
        } catch (Exception ex) {
            return ResultVo.failure(ex.getMessage());
        }
    }
}
