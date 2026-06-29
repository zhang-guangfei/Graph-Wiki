package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.SpecOrder.SpecOrderDO;
import com.smc.smccloud.model.specorder.SpecOrderDTO;
import com.smc.smccloud.model.specorder.SpecOrderVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface SpecOrderService {

    /**
     * 保存特殊订单
     * @param dto
     * @return
     */
    ResultVo<SpecOrderDTO> saveSpecOrder(SpecOrderDTO dto);

    /**
     * 查询特殊订单
     * @param specOrderVO
     * @param page
     * @return
     */
    PageInfo<SpecOrderVO> listSpecOrder(SpecOrderVO specOrderVO, Page page);

    void exportListSpecOrder(SpecOrderVO specOrderVO, HttpServletResponse response);


    /**
     * 生成批次号
     * @param groupNo
     * @return
     */
    ResultVo<String> addSpecOrder(String groupNo);

    /**
     * 生成特殊订单
     * @param specOrderVO
     * @return
     */
    ResultVo<String> createSpecOrder(SpecOrderVO specOrderVO);

    /**
     * 根据批次号生成特殊订单
     * @param specOrderVOS
     * @return
     */
    ResultVo<String> generateSpecOrder(List<SpecOrderVO> specOrderVOS);


    ResultVo<List<SpecOrderDO>> findSpecOrder(String groupNo);

    ResultVo<String> deleteSpecOrder(Integer id);

    /**
     * 导出一般贸易订单信息和出货信息至Excel
     * @return
     */
    ResultVo<String> exportSpecOrderExpDetailToExcel();

}
