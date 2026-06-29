package com.smc.ops.delivery.inqb.controller;

import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.inqb.InqbQueryRequest;
import com.sales.ops.dto.inqb.OpsInqbQueryInfo;
import com.smc.ops.delivery.inqb.service.InqbApplyCommonService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: B91717
 * @time: 2024/11/27 13:49
 */
@RestController
@CrossOrigin
@RequestMapping("/inqb/apply")
public class InqbApplyController {

    @Resource
    private InqbApplyCommonService inqbApplyCommonService;


    /**
     * INQB 前端，条件查询
     */
    @RequestMapping(value = "/queryInqbList", method = RequestMethod.POST)
    public ResultVo<PageInfo<OpsInqbQueryInfo>> findAll(@RequestBody InqbQueryRequest inqbQueryRequest, Page page) {
        try {
            return inqbApplyCommonService.queryInqbApplyInfo(inqbQueryRequest, page);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public void exportInqbApplyInfoExcel(@RequestBody InqbQueryRequest inqbQueryRequest, HttpServletResponse response) {
         inqbApplyCommonService.exportInqbApplyInfoExcel(inqbQueryRequest, response);
    }


}
