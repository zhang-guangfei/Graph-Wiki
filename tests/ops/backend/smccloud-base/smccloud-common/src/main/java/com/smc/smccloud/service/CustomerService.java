package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.Customer.CustomerDO;
import com.smc.smccloud.model.Customer.OpsCustomerWldateDO;
import com.smc.smccloud.model.customer.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author smc
 * @since 2022-01-12
 */
public interface CustomerService extends IService<CustomerDO> {

    CustomerVO getCustomerByCustomerNo(String customerNo);

    List<CustomerVO> getCustomerListInfoByCustomerNos(CustomerParam customerParam);

    ResultVo<List<CustomerVO>> getAllCustomerInfo(String customerNo);

    /**
     * 根据客户代码获取客户名称
     *
     * @param customerNo 客户代码
     * @return customerName
     */
    ResultVo<String> getCustomerNameByNo(String customerNo);

    /**
     * 获取客户英文名称
     *
     * @param customerNo 客户代码
     * @return CustomerEName
     */
    ResultVo<String> getCustomerENameByNo(String customerNo);

    /**
     * 模糊查询客户信息
     *
     * @param customerNo
     * @return
     */
    List<CustomerVO> findCustomerInfoByNoOrName(String customerNo);

    // 缓存所有的客户信息
    void cacheAllCustomerInfo();

    ResultVo<List<String>> getIndustryMediamCodeToCstmNo(List<String> industryCode);


    // 客户备案查询
    ResultVo<PageInfo<OpsCustomerWldateVO>> getOpsCustomerWldateList(CustomerWldateRequest request);

    // 根据客户编码删除客户备案信息
    ResultVo<String> delCustomerWldByCustomerNos(CustomerWldDelVO customerWldDelVO);

    ResultVo<OpsCustomerWldateVO> getCustomerInfoByCustomerNo(String customerNo);

    // 客户备案导出
    void exportCustomerWldList(CustomerWldateRequest request);

    // 下载备案导入模板
    void downLoadExcel();

    // 新增客户备案(单条)
    ResultVo<String> addCustomerWldInfo(OpsCustomerWldateVO opsCustomerWldateVO);

    // 导入客户备案
    ResultVo<String> importCustomerWld(MultipartFile file,String loginUser);

    ResultVo<String> batchAddCustomerWld(BatchAddCustomerWldVO batchAddCustomerWldVO);


    /**
     * 批量翻译ename为空的  客户名称(中文->英文)
     */
    ResultVo<String> BatchTranslateCustomerNameToEnglish();

    ResultVo<String> translateCustomerNameToEnglish();

}
