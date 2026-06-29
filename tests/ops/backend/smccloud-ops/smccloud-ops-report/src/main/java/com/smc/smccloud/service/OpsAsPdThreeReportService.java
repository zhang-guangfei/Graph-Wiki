package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.pd.OpsAsPdThreeReportDO;
import com.smc.smccloud.model.pd.PdReportParamVO;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2023-12-19
 */
public interface OpsAsPdThreeReportService {

    // 查询
    ResultVo<PageInfo<OpsAsPdThreeReportDO>> pdReportList(PdReportParamVO paramVO);

    // 生成盘点报表
    ResultVo<String> markPdReport();

    // 导出三方报表
    void exportThreePdReport(HttpServletResponse response, PdReportParamVO paramVO);

    // 导出两方报表
    void exportTwoPdReport(HttpServletResponse response);


}
