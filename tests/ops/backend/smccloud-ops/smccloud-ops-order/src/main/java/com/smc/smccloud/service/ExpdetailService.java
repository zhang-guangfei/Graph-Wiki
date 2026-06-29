package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.expdetail.ExpdetailRequest;
import com.smc.smccloud.model.expdetail.ExpdetailVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author smc
 * @since 2022-01-20
 */
public interface ExpdetailService {

//    ResultVo<PageInfo<ExpdetailVO>> listExpdetail(ExpdetailRequest request);

    ResultVo<PageInfo<ExpdetailVO>> listExpdetailWithCustomer(ExpdetailRequest request);

    ResultVo<PageInfo<ExpdetailVO>> listExpdetailWithCustomerForAgent (ExpdetailRequest request);

    ResultVo<ExpdetailVO> findOne(Long id);

    ResultVo<String> updateExpDetail(ExpdetailVO expdetailVO);

    ResultVo<List<ExpdetailVO>> findExpDetailByOrderType(ExpdetailRequest request);

    ResultVo<ExpdetailVO> listExpdetailByOrderNo(String orderNo);

    ResultVo<List<ExpdetailVO>> exportExpdetail(ExpdetailRequest request);

    ResultVo<List<ExpdetailVO>> exportExpdetailForAgent(ExpdetailRequest request);

    ResultVo<String> callExpExpdetailForGZ();

    ResultVo<String> updateExpdetailOptCodeById(Long id, String optCode);
}
