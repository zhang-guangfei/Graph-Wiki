package com.smc.ops.delivery.inquiry.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInquiryApply;
import com.sales.ops.db.entity.OpsInquiryCodeConfig;
import com.sales.ops.db.entity.OpsInquiryReasonParamConfig;
import com.sales.ops.dto.expdetail.DataCodeDto;
import com.sales.ops.dto.inquiry.*;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author smc
 * @since 2023-10-24
 */
public interface InquiryApplyService {

    /**
     * 列表查询主单
     *
     * @param inquiryApplyRequest
     * @param page
     * @return
     */
    ResultVo<PageInfo<OpsInquiryApply>> findAll(InquiryApplyRequest inquiryApplyRequest, Page page);

    void exportExcelData(InquiryApplyRequest inquiryApplyRequest, HttpServletResponse response);

    /**
     * 查询回复子项
     */
    ResultVo<List<InquiryOrderDetailReturnDto>> findDetail(OpsInquiryApply opsInquiryApply);

    /**
     * 根据orderno 查询催促详细信息
     *
     * @return
     */
    ResultVo<List<InquiryApplyVerifyReurn>> getOrderData(List<String> rorderFno);

    /**
     * 前端批量提交功能
     *
     * @param list
     * @return
     * @throws Exception
     */
    ResultVo<String> addInquiryData(List<InquiryApplyAddParam> list) throws Exception;

    /**
     * 根据orderno 查询催促详细信息
     *
     * @return
     */
    ResultVo<List<InquiryOrderVerifyReurn>> getOrderInquiryData(List<String> rorderFno);

    /**
     * 前端批量导入功能
     *
     * @param file
     * @return
     */
    ResultVo<List<InquiryApplyVerifyReurn>> importFile(MultipartFile file);

    // 订单批量导入的方法
    ResultVo<List<InquiryOrderVerifyReurn>> importOrderFile(MultipartFile file);

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @DS("opscmm")
    public List<DataCodeDto> getTbldata(String type);

    /**
     * 根据供应商代码获取可选，催促原因
     *
     * @param suppily
     * @return
     */
    ResultVo<List<OpsInquiryCodeConfig>> getReasonBySuppily(String suppily);

    /**
     * 获取催促原因所有的配置列表，带参数
     *
     * @return
     */
    ResultVo<List<OpsInquiryCodeConfig>> getAllReason();

    public ResultVo<List<OpsInquiryReasonParamConfig>> getAllParamConfig();

}
