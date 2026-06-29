package com.smc.ops.delivery.inqb.controller;

import com.sales.ops.db.entity.OpsInqbCodeConfig;
import com.sales.ops.dto.inqb.InqbApplyVerifyReurn;
import com.sales.ops.dto.inqb.InqbSalesApplyAddParam;
import com.sales.ops.dto.inqb.InqbSalesApplyAddReturn;
import com.sales.ops.dto.inqb.InqbSalesReturnStatusEnum;
import com.smc.ops.delivery.inqb.service.InqbApplyHandleService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dxf
 * INQB申请接口
 */

@RestController
@CrossOrigin
@RequestMapping("/inqb/handle")
public class InqbHandleCotroller {

    @Resource
    private InqbApplyHandleService inqbApplyHandleService;


    /**
     * 门户催促新增调用接口
     * @param inqbSalesApplyAddParam
     * @return
     */
    @RequestMapping(value = "/salesAddApply",method = RequestMethod.POST)
    public ResultVo<InqbSalesApplyAddReturn> salesAddApply(@RequestBody InqbSalesApplyAddParam inqbSalesApplyAddParam){
        try {
            return inqbApplyHandleService.salesInqbAdd(inqbSalesApplyAddParam);
        } catch (Exception e) {
            InqbSalesApplyAddReturn salesApplyReturn = new InqbSalesApplyAddReturn();
            salesApplyReturn.setStatus(InqbSalesReturnStatusEnum.ADDERROR.getType());
            salesApplyReturn.setStatusDescription(e.getMessage());
            return ResultVo.success(salesApplyReturn);
        }
    }

    /**
     *
     * 获取配置的催促原因，带参数
     * @return
     */
    @RequestMapping(value = "/getAllReason", method = RequestMethod.POST)
    public ResultVo<List<OpsInqbCodeConfig>> getReasonWithParams() {
        List<OpsInqbCodeConfig> opsInqbCodeConfigs = null;
        try {
            opsInqbCodeConfigs = inqbApplyHandleService.getInqbCodeConfig();
            if (CollectionUtils.isEmpty(opsInqbCodeConfigs)) {
                return ResultVo.failure("暂无INQB配置分类码信息，请联系IT补充");
            }
            return ResultVo.success(opsInqbCodeConfigs);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }


    /**
     * 门户催促新增校验接口，判断传输参数的是否可催促，初始校验
     * bug14968 inqB提交校验接口
     * @param inqbApplyVerify
     * @return
     */
    @RequestMapping(value = "/salesInqbAddValid",method = RequestMethod.POST)
    public ResultVo<InqbApplyVerifyReurn> salesInqbAddValid(@RequestBody InqbApplyVerifyReurn inqbApplyVerify){
        try {
            return inqbApplyHandleService.salesInqbValid(inqbApplyVerify);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }


}
