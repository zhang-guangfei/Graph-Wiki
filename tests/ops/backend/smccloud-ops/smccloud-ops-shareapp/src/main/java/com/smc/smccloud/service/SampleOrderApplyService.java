package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.expdetail.ExpdetailParams;
import com.smc.smccloud.model.sampleorder.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2021-10-27
 */
public interface SampleOrderApplyService extends IService<SampleOrderApplyDO> {

    ResultVo<PageInfo<SampleOrderVO>> listSampleOrderData(SampleOrderRequest request, Page page);

    void exportSampleOrderApplyData(SampleOrderRequest request);

    ResultVo<PageInfo<SamplebalDO>> listSampleBal(SampleBalRequest request,Page page);


    /**
     * 样品订单数据 生成订单 (sampleOrder_apply、sampleOrder_detail -> rcvMaster、rcvDetail)
     */
    ResultVo<String> createOrderBySampleOrder(SampleOrderParams sampleOrderParams);


    ResultVo<String> autoCreateOrerBySampleOrder();


    /**
     * 样品订单 免费结转
     */
//    ResultVo<String> orderCarryTurn(OrderCarryParams orderCarryParams);

    /**
     * 新增样品数据 （1）
     */
    ResultVo<List<SampleOrderReturnDTO>>  addSampleOrder(SampleOrderDTO sampleOrderDTO);


    /**
     * 订单回退
     */
    ResultVo<String> rebackOrder(SampleOrderParams sampleOrderParams);

    /**
     * 自动生成结算数据
     * @return
     */
    ResultVo<String> autoGenerateSampleBalData();

    /**
     * 销售开票
     * @param params
     * @return
     */
    ResultVo<String> toSalesInvoice(InvoicingSalesParams params);

    /**
     * 样品订单 自动结转  定时作业 (状态由待结转-> 待转出)
     */
    ResultVo<String> autoOrderCarryTurn();

    /**
     * 样品待转出自动写入成本 [申请类型非试用品转销售]
     */
    ResultVo<String> autoInsertSales();

    /**
     * 拆分结转数量,类型
     */
    ResultVo<String> splitCarryType(SplitSampleBalVO splitSampleBalVO);

    /**
     * 样品结转导出
     */
    void exportSampleBalData(SampleBalRequest request);

    /**
     * 样品待转销售自动开票
     */
   ResultVo<String> autoToSalesInvoice();

    /**
     * 重新结转
     */
    ResultVo<String> againBal(SplitSampleBalVO splitSampleBalVO);

//    /**
//     * 展览展示品管理列表
//     */
//    ResultVo<PageInfo<SampleOrderManageVO>> listSampleOrderManage(SampleOrderManageQuery request, Page page);
//
//    /**
//     * 导出
//     */
//    void exportSampleOrderManage(SampleOrderManageQuery request);

    ResultVo<String> exportOverdueBalData();

    void downLoadNoBalFileByYearMonth(String yearMonth);

    ResultVo<String> writeExcel(List<SamplebalDO> list,String deptNo);


    SampleOrderApplyDO findSampleOrderApplyByOrderNo(String orderNo);

    SampleorderDetailDO findSampleOrderDetail(String fullOrderNo);

    /**
     * 取消转销售
     */
    ResultVo<String> cancelTurnSalesInvoice(SplitSampleBalVO sampleBalVO);

    int updateSampleBalOptCodeById(Long id);

    Boolean updateLPFHData(SampleBalApplyDO sampleBalApplyDO,String optUser);

    ResultVo<String> againBal(SampleBalApplyDO sampleBalApplyDO);

    void insertTask(Long id, String applyNo);

    Boolean updateSampleBalOptCodeById(SampleBalApplyDO sampleBalApplyDO,String optUser,String invoiceNo);

    // 修改结转表申请单价为申请结转单价,状态为待转出
    int updateSamplebalDO(SampleBalApplyDO sampleBalApplyDO);

    Boolean updateSampleBySmapleApply(SampleBalApplyDO sampleBalApplyDO,String optUser,String invoiceNo);
}
