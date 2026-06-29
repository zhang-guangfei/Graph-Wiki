package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.product.ProductInterceptRuleDTO;
import com.smc.smccloud.model.product.ProductInterceptRuleRequest;
import com.smc.smccloud.model.product.ProductInterceptRuleVO;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-05-23 09:42
 * Description: 特殊产品拦截规则服务
 */
public interface ProductInterceptRuleService {

    /**
     * 查询拦截规则
     *
     * @param request 查询参考
     * @return 拦截规则列表
     */
    ResultVo<List<ProductInterceptRuleVO>> listProductInterceptRule(ProductInterceptRuleRequest request);
    ResultVo<PageInfo<ProductInterceptRuleVO>> listProductInterceptRuleByPage(ProductInterceptRuleRequest request);
    /**
     * 导出拦截规则
     * @param request
     */
    void exportProductInterceptRule(ProductInterceptRuleRequest request);

    /**
     * 添加或更新拦截规则
     *
     * @param ruleVO 拦截规则信息
     * @return 结果
     */
    ResultVo<String> addOrUpdateInterceptRule(ProductInterceptRuleVO ruleVO);

    /**
     * 拦截验证
     *
     * @param dto 验证信息
     * @return 验证结果
     */
    List<ProductInterceptRuleDTO> checkProductInterceptRule(ProductInterceptRuleDTO dto);

    /**
     * 删除拦截规则 状态改为9 取消
     * @param id
     * @return
     */
    ResultVo<String> deleteInterceptRule(Integer id);
    /**
     * 选择ids 删除拦截规则 状态改为9 取消
     * @param ids
     * @return
     */
    ResultVo<String> deleteInterceptRuleByIds(List<Integer> ids);

    /**
     * 根据条件删除拦截规则 状态改为9 取消
     * @param request
     * @return
     */
    ResultVo<String> deleteInterceptRuleByCondition(ProductInterceptRuleRequest request);

}
