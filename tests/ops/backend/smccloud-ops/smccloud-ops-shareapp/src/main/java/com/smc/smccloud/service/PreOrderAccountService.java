package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.inventory.InventoryVO;
import com.smc.smccloud.model.prestock.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wuweidong
 * @create 2023/12/7 10:15
 * @description
 */
public interface PreOrderAccountService {

    /**
     * 查询主要表
     * @param request
     * @return
     */
    ResultVo<PageInfo<PreOrderAccountDTO>> listPreOrderAccount(PreOrderAccountRequest request);

    /**
     * 导出主要表
     * @param request
     */
    void exportPreOrderAccount(PreOrderAccountRequest request, HttpServletResponse response);

    /**
     * 查询详细
     * @param request
     * @return
     */
    ResultVo<PageInfo<PreOrderAccountDetailDTO>> listPreOrderDetail(PreOrderAccountRequest request);

    /**
     * 导出详细表
     * @param request
     */
    void exportPreOrderDetail(PreOrderAccountRequest request, HttpServletResponse response);



    /**
     * 处理，调拨到大库。
     * @param ids
     * @return
     */
    ResultVo<String> handleTransferByIds( List<Long>  ids);

    /**
     * 自动处理清算和延迟到期
     * @return
     */
    ResultVo<String> handleTransferByAuto() ;

    /**
     * 门户处理接口
     * @param detailDTO
     * @return
     */
    ResultVo<String> updatePreOrderAccountFromSales(PreOrderAccountDetailDTO detailDTO );

}
