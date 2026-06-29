package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.borrowstock.*;

import java.util.List;

public interface BorrowStockService {

    /**
     * 查询借库信息
     * @param brwMasterVO
     * @param page
     * @return
     */
    ResultVo<PageInfo<BrwMasterVO>> listBrwStockInfo(BrwMasterVO brwMasterVO, Page page);

    /**
     * 查询未归还信息
     * @param request
     * @param page
     * @return
     */
    ResultVo<PageInfo<BrwMasterDTO>> listNotReturn(BrwStockRequest request, Page page);

    /**
     * 查询明细
     * @param id
     * @return
     */
    ResultVo<List<BrwDetailVO>> listBrwDetail(Integer id);

    /**
     * 保存借货申请
     * @return
     */
    ResultVo<String> saveBrwStockApply(BrwApplyDTO dto);
}
