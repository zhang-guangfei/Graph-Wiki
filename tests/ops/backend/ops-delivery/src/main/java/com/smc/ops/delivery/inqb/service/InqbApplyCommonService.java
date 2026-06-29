package com.smc.ops.delivery.inqb.service;

import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsCustomer;
import com.sales.ops.db.entity.OpsInqbCodeConfig;
import com.sales.ops.dto.inqb.InqbQueryRequest;
import com.sales.ops.dto.inqb.OpsInqbQueryInfo;
import com.sales.ops.dto.inqb.OrganizationDto;
import com.sales.ops.dto.inquiry.InquiryWorkdayCondition;
import com.smc.ops.delivery.model.DataTypeVO;
import com.smc.ops.delivery.model.inqb.OpsInqbCodeConfigDO;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * INQB处理的公共方法
 */
public interface InqbApplyCommonService {

    /**
     * 前端查询INQB申请单，条件查询
     *
     * @param inqbQueryRequest
     * @param page
     * @return
     */
    ResultVo<PageInfo<OpsInqbQueryInfo>> queryInqbApplyInfo(InqbQueryRequest inqbQueryRequest, Page page) throws Exception;


    void exportInqbApplyInfoExcel(InqbQueryRequest inqbQueryRequest, HttpServletResponse response);

    /**
     * 生成INQB申请单号
     *
     * @return
     */
    public ResultVo<String> generateInqbApplyNo();

    /**
     * 调用订单分配模块逻辑
     */


    /**
     * 根据提供的供应商回复日期，计算INQB子项的有效截止日
     * 回复当日开始可用，有效期为回复时间次日起的5个工作日均有效，第5个工作日的23:59:59失效；即截止日期是第5个工作日后一个自然日
     *
     * @param info
     * @return
     */
    public ResultVo<Date> calExpirationDate(InquiryWorkdayCondition info);

    /**
     * 获取客户信息
     *
     * @param customerNo
     * @return
     */
    public OpsCustomer getCustomerByCustomerNo(String customerNo);

    /**
     * 根据HL代码，向上提级到驻在所/营业所
     *
     * @param customerDept
     * @return
     */
    public ResultVo<String> findDeptNoByHRSalesDeptNo(String customerDept);

    /**
     * 根据部门代码，获取部门所在的组织信息
     *
     * @param code
     * @return
     */
    public ResultVo<OrganizationDto> getHrOrganMasterInfoByCode(String code);

    DataTypeVO getInqbDataCodesInfo(String classCode, String code);

    ResultVo<Boolean> updateInqbExtNote1(Long id, String extNote1, String curExtNote1);

    /**
     * 根据供应商及催促分类，获取供应商对应的催促原因
     *
     * @param codetype   发送/回复
     * @param supplyCode 供应商代码
     * @return
     */
    Map<String, OpsInqbCodeConfigDO> getCodecinfigBySuppily(String codetype, String supplyCode);

    ResultVo<List<OpsInqbCodeConfig>> getInqbCodeConfigFromRedis();

    ResultVo<String> refreshInqbCodeConfig();

    public ResultVo<String> getOrderAreaInfo(String endUser, String deptNo);

}
