package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.order.TransOrderDtoForMove;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.trans.TransOrderCancelDTO;
import com.smc.smccloud.model.trans.TransOrderRequest;
import com.smc.smccloud.model.trans.TransOrderVO;

import java.util.List;

public interface TransStockService {
    /**
     * 调拨处理
     * @param voList
     * @return
     */
    ResultVo<String> transStock(List<TransOrderVO> voList);

    ResultVo<String> transStockAll(List<TransOrderVO> voList);

    /**
     * 预约在途
     * @param voList
     * @return
     */
    ResultVo<String> createTransOrderForMove(List<TransOrderDtoForMove> voList) ;
    /**
     * 调库查询
     * @return
     */
    ResultVo<PageInfo<TransOrderVO>> findTransOrder (TransOrderRequest request);

    /**
     * 调库取消
     * @param dto
     * @return
     */
    ResultVo<String> cancelTransOrder(TransOrderCancelDTO dto);

    /**
     * 导出调库数据
     */
    void exportTransData(TransOrderRequest request);
}
