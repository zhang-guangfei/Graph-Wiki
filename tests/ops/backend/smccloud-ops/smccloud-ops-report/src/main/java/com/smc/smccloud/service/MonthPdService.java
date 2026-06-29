package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.pd.*;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author lyc
 * @Date 2024/11/1 9:31
 * @Descripton TODO
 */
public interface MonthPdService {

    ResultVo<PageInfo<OpsAsPdBatchYdDO>> listYdPdBatchList(YdPdSearchParams requestVO, int pageNumber, int pageSize);

    // 执行计划查询
    ResultVo<PageInfo<OpsPdExecPlanManageDO>> listExecPlan(ExecPlanParamVO paramVO);

    // 制作一年的执行计划
    ResultVo<String> makeExecPlan();

    // 更新/新增计划
    ResultVo<String> updateOrAddPdExecPlan(OpsPdExecPlanManageDO opsPdExecPlanManageDO);


    // --------------- 执行步骤 ------------------

    // 1. 新建月度盘点
    ResultVo<String> addMonthPd();

    // 2. wms数据抽取
    ResultVo<String> wmsDataExtract();

    // 3. wms到货未入数据抽取
    ResultVo<String> wmsDhwrExtract();

    // 确认数据 (抽取到货未入到盘点票结果表)
    ResultVo<String> arrivedNotInInsertToPdBill();

    // 4. 样品未结转
    ResultVo<String> ypNotBal();

    // 5. 已出库未推财务
    ResultVo<String> yckwtcw();

    // 6. 财务补偿数据
    ResultVo<String> cwbcData();

    // 7. OPS调拨在途
    ResultVo<String> opsdbzt();

    // 8. OPS制造在途
    ResultVo<String> opszzzt();

    // 9. OPS库存数据
    ResultVo<String> opsInventoryDataExtract();

    // 10. 财务结存数据
    ResultVo<String> cwjc();

    // 11. OPS采购到货未入
    ResultVo<String> opsCGdhwrData();

    // 12. OPS退货到货未入
    ResultVo<String> opsTHdhwrData();

    // 13. OPS调拨到货未入
    ResultVo<String> opsDbdhwrData();

    // 14. WMS调拨在途
    ResultVo<String> wmsdbztData();

    // 15. OPS退货在途数据
    ResultVo<String> opsTHzzData();

    // 16. WMS退货在途数据
    ResultVo<String> wmsThzzData();

    // 17. WMS制造在途
    ResultVo<String> wmszzztData();

    // 18. WMS组换在途
    ResultVo<String> wmszhztData();

    // 19. OPS组换到货未入
    ResultVo<String> opsZHDhwrData();

    // 20. OPS组换在途
    ResultVo<String> opszhztData();

    // 21. 输出盘点报表
    ResultVo<String> makePdReport();

    // 确认盘点形式:
    // 将wms中间表过渡库位及实体货架库存数据写入到盘点票结果表 以及 委托在库(寄售库存)数据抽取到盘点票结果表
    ResultVo<String> batchInsertPdBill();

    // 获取月度盘点未执行的批次信息
    OpsAsPdBatchYdDO getActivePdBatch();

    // 更新步骤管理表执行状态
    ResultVo<String> updatePdExecStep(String code,String remark,boolean isok);

    ResultVo<String> uiExecYdPd(String code);

    // 获取执行步骤清单
    ResultVo<List<OpsPdStepManageDO>> getExecStepList();

    // 月度盘点报表
    ResultVo<PageInfo<OpsAsPdThreeReportWareDO>> listPdThreeReportWare(OpsAsPdReportWareParams params);

    ResultVo<List<OpsAsPdThreeReportWareDO>> getPdBatchNoListFromReportWare(String pdBatchNo);

    // 执行月度盘点
    ResultVo<String> execYdPdStep();

    // 抽取OPS侧盘点数据
    ResultVo<String> execYdExtractOpsDataPdStep();

    // 抽取WMS侧盘点数据
    ResultVo<String> execYdExtractWmsDataPdStep();

    // 根据执行计划执行月度盘点
    ResultVo<String> execYdPdStepByPlan();

    ResultVo<PageInfo<OpsAsPdThreeReportWareDO>> listYdPdThreeReportWare(SearchReportWareParams params);

    void exportYdPdReport(HttpServletResponse response, SearchReportWareParams params);

    void exportYdPdStep(HttpServletResponse response,String methodCode );


    /**
     * 记录操作日志
     */
    void insertSysLog(String title, String optUser, String methodParams);

}
