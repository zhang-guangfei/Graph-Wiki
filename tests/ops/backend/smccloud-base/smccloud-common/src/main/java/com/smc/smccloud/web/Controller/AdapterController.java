package com.smc.smccloud.web.Controller;


import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.model.Adapter.Department;
import com.smc.smccloud.service.AdapterService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/common/SMSAdapter")
@RestController
public class AdapterController {

    @Resource
    private AdapterService adapterService;

    @RequestMapping("/department/findSaleDeptCommon")
    public ResultVo<List<Department>> findSaleDeptCommon(
            @RequestBody DataAuthoritySearchCondition dataAuthoritySearchCondition,
            @RequestParam("deptNo") String deptNo) {

        return adapterService.findSaleDeptCommon(dataAuthoritySearchCondition, deptNo);
    }
}
