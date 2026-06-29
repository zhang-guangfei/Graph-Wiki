package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.OrderLog.OrderStateLogDO;
import com.smc.smccloud.model.orderstate.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface OrderStateService {

    ResultVo<PageInfo<OrderStateDTO>> listOrderState(OrderStateRequest orderSalesRequest, Page page);

    // 导出.
    void exportOrderState(OrderStateRequest orderSalesRequest);

    /**
     * 发送订单状态至MQ
     *
     * @param orderStateVO
     * @return
     */
    ResultVo<String> addOrderState(OrderStateVO orderStateVO);


    /**
     * 消费发送到MQ的OrderState消息
     * 保存数据至数据库
     *
     * @param orderStateVO
     * @return
     */
    ResultVo<String> rcvOrderStateMQ(OrderStateVO orderStateVO);


    /**
     * 导入日本delivery.data
     *
     * @param jpDeliveryDataVOList
     * @return
     */
    ResultVo<String> importJPDeliveryData(List<JPDeliveryDataVO> jpDeliveryDataVOList);

    /**
     * 导入日本ORDER-95012.TXT文件
     */
    ResultVo<String> importJPReceiveOrderFile(MultipartFile file);


    /**
     * 计算预计到达日期
     *
     * @param supplierCode 供应商
     * @param transType    运输方式
     * @param dlvDate      出厂纳期
     * @return
     */
    Date calcEsArrivalDate(String supplierCode, String transType, Date dlvDate);

    /**
     * 转换3位的运输方式
     *
     * @param threeCode
     * @return
     */
    String convertTransType(String threeCode);


    /**
     * 导入中国制造订单返信
     *
     * @return
     */
    ResultVo<String> importCNMOrderReply();


    ResultVo<String> importOverseaOrderReply(List<OrderStateVO> list);


    /**
     * 供应商回复交货期
     *
     * @param replyState
     * @return
     */
    ResultVo<String> supplierReplyOrderState(OrderSupplierReplyState replyState);

    ResultVo<String> handleDeleleOrder(HandleDeleteOrderDTO dto);


    /**
     * 导入中国工厂未完成订单状态
     *
     * @return
     */
    //ResultVo<String> importCNMOPSVRequisitionStatusToSales();

    /**
     * 导入中国工厂未完成订单状态
     * @return
     */
    ResultVo<String> importCNMNotFinishOrderState();


    ResultVo<String> checkOrderState();

    /**
     * 导入中国工厂接单日期
     * @return
     */
    ResultVo<String> importCNMRecieveOrderTime();

    // ResultVo<String> importCNMRecieveOrderTimeByUpDate();

    void syncOrderStateInfo();

    // 导入供应商返信
    ResultVo<String> importSupplierReplyDate(MultipartFile file);

    String getStateSupplierOrderNo(String orderNo);

    void sendOrderStateToGZProd(OrderStateDO info);


    /**
     * 处理删单错误的订单
     * 将所有已经删单的数据推给门户
     */
    ResultVo<String> handDelErrorOrder();


    /**
     * 获取拆分的货期订单数据
     */
    ResultVo<List<OrderStateDTO>> getSplitOrderState(String rorderNo,String itemNo);

    ResultVo<String> downloadJPDeliveryFile();

    ResultVo<PageInfo<OrderStateLogDO>> getOrderStateLogList(OrderStateLogRequest request, int pageNumber, int pageSize);


    ResultVo<DataTypeVO> testCommonService(String classCode,String code);

    void updateOrderStateDetail(OrderSateDateType dateType,
                           OrderStateVO orderStateVO, Date updDate);

    ResultVo<List<String>> canDelOrderStatus();


    boolean isAllowChangeStatus(Integer toStatus,Integer prevStatus);


    /**
     * 计算预计到货日期
     * @param warehouseCode
     * @param supplierCode
     * @param transType
     * @param dlvDate
     * @return
     */
     Date calcEsArrivalDate(String warehouseCode, String supplierCode, String transType, Date dlvDate);

}