package com.smc.smccloud.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.order.SmsSendOpsDetailTaskBean;
import com.smc.smccloud.model.returnorder.ReturnOrderDO;
import com.smc.smccloud.model.sampleorder.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/7/22 17:23
 * @Descripton TODO
 */
public interface SampleBalService {

    @DS("opsdb")
    int insertSampleBal(SamplebalDO samplebalDO);

    int insertSampleBal(ReturnOrderDO returnOrderDO);

    // 展示品盘点票导出
    void exportZlzsOrderBalance(ZlzsExportRequest request);

    // 展览展示品销账
    ResultVo<String> writeoffForZlzsOrder(WriteOffZlzsRequest request);

    // 通过导入excel批量销账
    ResultVo<String> importWriteOffData(MultipartFile file, String loginUser);

    // 发布展览展示品盘点票给营业所(Excel)
    ResultVo<String> pushZlzsOrderBalanceForExcel(ZlzsExportRequest request);

    // 发布展览展示品盘点票给营业所(pdf)
    ResultVo<String> pushZlzsOrderBalanceForPdf(ZlzsExportRequest request);

    // 获取盘点票导出时间
    ResultVo<String> getZLZSExportTime();

    // 下载展示品销账模板
    void downExcelForWriteOff();

    // 变更展示品实物所在部门(单条)
    ResultVo<String> upZlzsRcvDeptNo(UpZlzsRcvDeptNoParams params);

    // 下载变更展示品实物所在部门模板
    void downExcelForUpRcvDeptNo();

    // 通过导入变更展示品实物所在部门模板批量变更
    ResultVo<String> batchUpRcvDeptNo(MultipartFile file, String loginUser);

    int updateSampleById(UpSamplelBalVO upSamplelBalVO);

    /**
     * 展示品盘点导出(写入sampleOrderManage) -> 展览展示品管理页面
     */
    ResultVo<String> insertIntoSampleOrderManage(ZlzsExportRequest request);


    /**
     * 样品结转申请确认查询
     * @param info
     * @return
     */
    ResultVo<PageInfo<SampleBalApplyVO>> findSampleBalApplyInfoList(QuerySampleBalApplyParam info);

    /**
     * 申请确认结转
     */
    ResultVo<String> sureApplySampleBal(List<String> ids);


    ResultVo<String> insertSampleBalApply(SampleBalApplyVO info);

    // 查询是否存在处理中的单据
    ResultVo<String> findHandSampleBalApply(FindHandSampleBalHandVO vo);

    ResultVo<String> checkRcvQty(CheckRcvQtyVO checkRcvQtyVO);


    // 申请确认结转良返自动写入退货表
    ResultVo<String> insertReturnOrder(SmsSendOpsDetailTaskBean bean);


    /*
    *  修正结转表异常数据
    * */
    ResultVo<String> updateErrorSampleBalData();


    /**
     * 修正样品资产分配表 非拆分型号
     */
    // ResultVo<String> upSampleBalPropertyAssign(List<Long> sampleBalIds);


    /**
     * 修正样品资产分配表 非拆分型号
     */
    ResultVo<String> upSampleBalPropertyAssign2(List<Long> sampleBalIds);

    /**
     * 修正样品资产分配表 拆分型号
     */
    ResultVo<String> upSampleBalPropertyAssignWithSplitModelNo(List<Long> sampleBalIds);

    /**
     * 样品结转退货负数写入资产分配 非拆分型号
     */
    ResultVo<String> upSampleBalPropertyAssignWithReturn(Long sampleId);

    /**
     * 样品结转退货负数写入资产分配 拆分型号
     */
    ResultVo<String> upSampleBalPropertyAssignWithReturnSplitModelNo(Long sampleId);

    /**
     * 样品重新结转写入资产分配表  拆分&&不拆分
     */
    ResultVo<String> againBalUpPropertyAssign(List<Long> sampleBalIds);

    /**
     * 新样品订单申请 重新结转  拆分&&不拆分
     */
    ResultVo<String> newAgainBalUpPropertyAssign(List<Long> sampleBalIds);

    /**
     * 样品结转写入分配表 (非退货)
     * 由这里统一判断是否型号拆分 然后分别调用对应的接口进行处理
     */
    ResultVo<String> upSampleBalPropertyAssignResult(List<Long> sampleBalIds);


    /**
     * 样品结转退货负数写入资产分配
     * 由这里统一判断是否型号拆分 然后分别调用对应的接口进行处理
     */
    ResultVo<String> upSampleBalPropertyAssignResultWitnReturn(Long sampleId);

    /**
     * 读取sample_bal_apply表的交易主体
     */
    List<SampleBalApplyDO> getSampleBalApplyDOListByBalId(Long samplebalDOId);
}
