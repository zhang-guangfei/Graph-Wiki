package com.sales.ops.web.controller;

import com.sales.ops.db.dao.OpsRequestpurchaseMapper;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.test.CalDlvInfoDto;
import com.sales.ops.dto.test.CalDlvInfoResultDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.core.TransService;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/9/4 12:37
 */
@CrossOrigin
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TransService transService;
    @Autowired
    private OpsRequestpurchaseMapper opsRequestpurchaseMapper;
    @RequestMapping(value = "/calDlvInfo")
    @ResponseBody
    public CommonResult<List<CalDlvInfoResultDto>> calDlvInfoNew(@RequestBody CalDlvInfoDto infoDto) {
        try {
            List<OpsRequestpurchase> list = new ArrayList<>();
            for (Long id: infoDto.getIds()) {
                OpsRequestpurchase requestPurchase = opsRequestpurchaseMapper.selectByPrimaryKey(id);
                list.add(requestPurchase);
            }
            List<OpsRequestpurchase> opsRequestpurchaseList = transService.calDlvInfo(list,infoDto.getDate());
            List<CalDlvInfoResultDto> calDlvInfoResultDtos = BeanCopyUtil.copyList(opsRequestpurchaseList, CalDlvInfoResultDto.class);
            return CommonResult.success(calDlvInfoResultDtos);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }


    }


}
