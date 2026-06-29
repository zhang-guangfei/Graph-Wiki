package com.sales.ops.serviceimpl.event.v3.deliveryDate;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.dao.OpsMailMapper;
import com.sales.ops.db.dao.OpsPurchaseorderMapper;
import com.sales.ops.db.entity.OpsMail;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsPurchaseorderExample;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.extdao.CommonMqpper;
import com.sales.ops.dto.order.CustomerOrderCancelForDeliveryDateDTO;
import com.sales.ops.dto.order.OpsSalesNoticeTaskDto;
import com.sales.ops.dto.order.OrderStateHandDto;
import com.sales.ops.enums.RequestPurchaseStatusEnum;
import com.sales.ops.enums.SendStatusEnum;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.*;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.UIDGenerator;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.Department.DepartmentVO;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.order.orderdel.SalesErpOrderDeleteResultVO;
import com.smc.smccloud.model.ordermodify.OrderModifyVO;
import com.smc.smccloud.model.ordermodify.SpecialVO;
import com.smc.smccloud.model.orderstate.OrderStateEnum;
import com.smc.smccloud.model.salestask.OpsSalesCommonParamVO;
import com.smc.smccloud.model.stock.DealReturnOpsParam;
import com.smc.smccloud.model.stock.DealReturnOpsParamVO;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeliveryDateEventServiceImpl implements DeliveryDateEventService {

    @Resource
    private CommonMqpper commonMqpper;

    @Resource
    private OpsPurchaseorderMapper opsPurchaseorderMapper;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Resource
    private OpsMailMapper opsMailMapper;

    @Resource
    private OPSRedisUtils redisUtils;

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    //采购删单
    /**
     *  请购单 cancelSource = -2
     *  采购单 cancelSource != -2
     * */
    @Override
    public void cancelPurchaseOrder(String cancelSource, OpsPurchaseorder purchaseOrder) {

        ResultVo<DataTypeVO> dataTypeCodesInfoWithDS = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "34");
        if (!dataTypeCodesInfoWithDS.isSuccess()) {
            return;
        }

        if ("1".equals(dataTypeCodesInfoWithDS.getData().getExtNote1())) {
            return;
        }

        OrderStateHandDto orderStateHandDto = new OrderStateHandDto();
        if (purchaseOrder.getSplititemno() == null) {
            orderStateHandDto.setOrderNo(purchaseOrder.getOrderno()+"-" + purchaseOrder.getItemno());
        } else {
            orderStateHandDto.setOrderNo(purchaseOrder.getOrderno()+"-" + purchaseOrder.getItemno()+"-" + purchaseOrder.getSplititemno());
            orderStateHandDto.setSplititemno(purchaseOrder.getSplititemno());
        }

        orderStateHandDto.setOrderId(purchaseOrder.getOrderno());
        orderStateHandDto.setOrderItem(purchaseOrder.getItemno());
        orderStateHandDto.setModelNo(purchaseOrder.getModelno());
        orderStateHandDto.setQuantity(purchaseOrder.getQuantity());
        orderStateHandDto.setDeptNo(purchaseOrder.getDeptno());
        orderStateHandDto.setOrderPsnNo(purchaseOrder.getOperator());
        orderStateHandDto.setOptUser(purchaseOrder.getOperator());
        String desc = "";
        if ("-2".equals(cancelSource)) {
            desc = DateUtil.dateToDateTimeString(new Date())+"【请购单】已取消";
        } else {
            desc = DateUtil.dateToDateTimeString(new Date())+"【采购单】已取消";
        }
        orderStateHandDto.setStateDes(desc);
        orderStateHandDto.setSupplierCode(purchaseOrder.getSupplierid());
        orderStateHandDto.setShikomiNo(purchaseOrder.getShikomianswerno());
        orderStateHandDto.setOrderType(purchaseOrder.getOrdtype());
        orderStateHandDto.setCustomerNo(purchaseOrder.getCustomerno());
        orderStateHandDto.setWarehouseCode(purchaseOrder.getReceivewarehouseid());

        if ("21".equals(orderStateHandDto.getOrderType()) || "20".equals(orderStateHandDto.getOrderType()) || "3".equals(orderStateHandDto.getOrderType())) {
            // 取消先行在库及客户订单发送邮件
            sendCancelPrestockAndCustomerOrderEmail(orderStateHandDto);
        }


    }

    @Override
    public void requestIntercept(OpsRequestpurchase purchaseOrder) {
        ResultVo<DataTypeVO> dataTypeCodesInfoWithDS = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "34");
        if (!dataTypeCodesInfoWithDS.isSuccess()) {
            return;
        }

        if ("1".equals(dataTypeCodesInfoWithDS.getData().getExtNote1())) {
            return;
        }

        OrderStateHandDto orderStateHandDto = new OrderStateHandDto();

        if (purchaseOrder.getSplititemno() == null) {
            orderStateHandDto.setOrderNo(purchaseOrder.getOrderno()+"-" + purchaseOrder.getItemno());
        } else {
            orderStateHandDto.setOrderNo(purchaseOrder.getOrderno()+"-" + purchaseOrder.getItemno()+"-" + purchaseOrder.getSplititemno());
            orderStateHandDto.setSplititemno(purchaseOrder.getSplititemno());
        }
        orderStateHandDto.setOrderId(purchaseOrder.getOrderno());
        orderStateHandDto.setOrderItem(purchaseOrder.getItemno());
        orderStateHandDto.setModelNo(purchaseOrder.getModelno());
        orderStateHandDto.setQuantity(purchaseOrder.getQuantity());
        orderStateHandDto.setDeptNo(purchaseOrder.getDeptno());
        orderStateHandDto.setOrderPsnNo(purchaseOrder.getOperator());
        orderStateHandDto.setOptUser(purchaseOrder.getOperator());
        // 状态描述
        StringBuilder stateDes = new StringBuilder();
        stateDes.append(DateUtil.dateToDateTimeString(new Date())).append(OrderStateEnum.PurchaseFault.getStateName()).append("，拦截原因：");
        if (RequestPurchaseStatusEnum.SHIKOMILANJIE.equals(purchaseOrder.getStatecode())) {
            stateDes.append("SHIKOMI");
        }
        stateDes.append(purchaseOrder.getInterceptmsg());

        orderStateHandDto.setStateDes(stateDes.toString());
        orderStateHandDto.setSupplierCode(purchaseOrder.getSupplierid());
        orderStateHandDto.setShikomiNo(purchaseOrder.getShikomianswerno());
        orderStateHandDto.setOrderType(purchaseOrder.getOrdtype());
        orderStateHandDto.setCustomerNo(purchaseOrder.getCustomerno());

        // 发送采购拦截邮件通知
        sendShikomiInterceptMsgEmail(orderStateHandDto);
    }

    /**
     * 客单删单
     * @param cancelForOrderDto
     */
    @Override
    public void customerCancel(CustomerOrderCancelForDeliveryDateDTO cancelForOrderDto) {

        ResultVo<DataTypeVO> dataTypeCodesInfoWithDS = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "34");
        if (!dataTypeCodesInfoWithDS.isSuccess()) {
            return;
        }

        if ("1".equals(dataTypeCodesInfoWithDS.getData().getExtNote1())) {
            return;
        }

        OrderStateHandDto orderStateHandDto = new OrderStateHandDto();
        orderStateHandDto.setOrderNo(cancelForOrderDto.getOrderFno());
        orderStateHandDto.setModelNo(cancelForOrderDto.getModelno());
        orderStateHandDto.setQuantity(cancelForOrderDto.getQuantity());
        orderStateHandDto.setDeptNo(cancelForOrderDto.getDeptNo());
        orderStateHandDto.setOrderPsnNo(cancelForOrderDto.getEmployeeno());
        orderStateHandDto.setOptUser(cancelForOrderDto.getUserDto().getUserName());
        StringBuilder desc = new StringBuilder();
        if (ObjectUtil.isNotNull(cancelForOrderDto.getUserDto())) {
            desc.append(cancelForOrderDto.getUserDto().getUserName());
        }
        if (StringUtils.isNotBlank(cancelForOrderDto.getOrderType()) && OrderTypeEnum.getCodeName(cancelForOrderDto.getOrderType()) != null) {
            desc.append("做" + OrderTypeEnum.getCodeName(cancelForOrderDto.getOrderType()));
        }
        desc.append("取消订单");
        if (StringUtils.isNotBlank(cancelForOrderDto.getDuty())) {
            desc.append(" 责任人：" + cancelForOrderDto.getDuty());
        }
        if (StringUtils.isNotBlank(cancelForOrderDto.getReason())) {
            desc.append(" 原因：" + cancelForOrderDto.getReason());
        }
        orderStateHandDto.setStateDes(desc.toString());
        orderStateHandDto.setOrderType(cancelForOrderDto.getOrderType());
        orderStateHandDto.setCustomerNo(cancelForOrderDto.getCustomerNo());

        List<OrderModifyVO> orderModifyVOS = commonMqpper.queryOrderModifyList(cancelForOrderDto.getOrderFno());

        if (CollectionUtils.isEmpty(orderModifyVOS)) {
            //  ops删单写入OrderModify
            insertOrderModify(orderStateHandDto);
            // 写入task
            insertTask(orderStateHandDto);

        } else {
            for (OrderModifyVO item : orderModifyVOS) {
                if (OrderModifyEnum.handing.getCode().equals(String.valueOf(item.getStatus()))
                        || OrderModifyEnum.waitHand.getCode().equals(String.valueOf(item.getStatus()))
                        || OrderModifyEnum.notHand.getCode().equals(String.valueOf(item.getStatus())))
                {
                    commonMqpper.updateOrderModify(cancelForOrderDto.getReason(),cancelForOrderDto.getUserDto().getUserName(),item.getId());

                    // 根据批次号修改task的回调参数
                    updateTaskCallBackParameter(item, cancelForOrderDto.getReason());
                }
            }
        }

        // 取消集团订单发送邮件
        if ("11".equals(cancelForOrderDto.getOrderType())) {
            sendMainForCancelCNOrder(orderStateHandDto);
        }
        // 取消广州制造订单发送邮件
        if ("C1D72".equals(cancelForOrderDto.getCustomerNo())) {
            sendMailForGZ(orderStateHandDto);
        }
        // 取消先行在库及客户订单发送邮件
        sendCancelPrestockAndCustomerOrderEmail(orderStateHandDto);

    }

    // 写入order_modify
    private void insertOrderModify(OrderStateHandDto orderStateHandDto) {
        OrderModifyVO orderModifyVO = new OrderModifyVO();
        orderModifyVO.setOrderNo(orderStateHandDto.getOrderNo());
        orderModifyVO.setBatchNo("");
        orderModifyVO.setModifyType("C");
        orderModifyVO.setStatus(6);
        orderModifyVO.setDeptNo(orderStateHandDto.getDeptNo());
        orderModifyVO.setCustomerNo(orderStateHandDto.getCustomerNo());
        orderModifyVO.setChangeType("C");
        orderModifyVO.setAnswerText(orderStateHandDto.getStateDes());
        orderModifyVO.setCreateUser("ops");
        orderModifyVO.setCreateTime(new Date());

        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderStateHandDto.getOrderNo());
        OpsPurchaseorderExample example = new OpsPurchaseorderExample();
        example.createCriteria().andOrdernoEqualTo(orderNoInfo.getOrderNo()).andItemnoEqualTo(orderNoInfo.getItemNo());
        if (orderNoInfo.getSplitItem() != null) {
            example.createCriteria().andSplititemnoEqualTo(orderNoInfo.getSplitItem());
        }
        List<OpsPurchaseorder> purchaseOrders = opsPurchaseorderMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(purchaseOrders)) {
            orderModifyVO.setSupplierOrderNo(purchaseOrders.get(0).getReplyorderno());
        }
        commonMqpper.insertOrderModify(orderModifyVO);
    }

    // 写入task
    private void insertTask(OrderStateHandDto orderStateHandDto) {
        String batchNo = UIDGenerator.generateUID();
        OpsSalesNoticeTaskDto taskDO = new OpsSalesNoticeTaskDto();
        taskDO.setBatchNo(batchNo);
        taskDO.setBusinessCode(OrderModifyTypeEnum.cancel_order.getCode());
        taskDO.setOrderFno(orderStateHandDto.getOrderNo());

        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        dealReturnOpsParamVO.setApplyType(8);

        DealReturnOpsParam param = new DealReturnOpsParam();

        SalesErpOrderDeleteResultVO vo = new SalesErpOrderDeleteResultVO();

        vo.setApplyItemNo("OPS_DEL");
        vo.setOrderNo(orderStateHandDto.getOrderNo());
        vo.setSecondProcess(false);
        vo.setStatus(CancelOrderToSalesStatus.del_success.getCode());
        vo.setStatusDescription(orderStateHandDto.getStateDes());

        param.setSalesErpOrderDeleteResultVo(vo);

        dealReturnOpsParamVO.setDealReturnOpsParam(param);

        OpsSalesCommonParamVO bean = new OpsSalesCommonParamVO();
        bean.setData(dealReturnOpsParamVO);

        taskDO.setCallBackParameter(JSONUtil.toJsonStr(bean));

        Date nowDate = new Date();
        taskDO.setHandleStartTime(nowDate);
        taskDO.setHandleStatus("1");
        taskDO.setCreateTime(nowDate);
        taskDO.setReturnStatus(OpsSalesTaskReturnStatus.notReturn.getCode());
        taskDO.setTryCount(0);
        taskDO.setErrorHandCount(0);
        taskDO.setSource("ops");
        commonMqpper.insertSalesNoticeTask(taskDO);
    }

    private void updateTaskCallBackParameter(OrderModifyVO orderModifyInfo,String remark) {
        if (StringUtils.isBlank(orderModifyInfo.getBatchNo())) {
            return;
        }
        SpecialVO specialVO = null;
        if (StringUtils.isNotBlank(orderModifyInfo.getSpecial())) {
            specialVO = JSONObject.parseObject(orderModifyInfo.getSpecial(), SpecialVO.class);
        }

        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        dealReturnOpsParamVO.setApplyType(8);

        DealReturnOpsParam param = new DealReturnOpsParam();

        SalesErpOrderDeleteResultVO vo = new SalesErpOrderDeleteResultVO();

        if (StringUtils.isBlank(orderModifyInfo.getApplyNo())) {
            vo.setApplyItemNo("OPS_DEL");
        } else {
            vo.setApplyItemNo(orderModifyInfo.getApplyNo());
        }

        vo.setOrderNo(orderModifyInfo.getOrderNo());
        if (specialVO != null) {
            vo.setSecondProcess(specialVO.getSecondApproval());
        }
        vo.setStatus(CancelOrderToSalesStatus.del_success.getCode());
        vo.setStatusDescription(remark);

        param.setSalesErpOrderDeleteResultVo(vo);

        dealReturnOpsParamVO.setDealReturnOpsParam(param);

        OpsSalesCommonParamVO bean = new OpsSalesCommonParamVO();
        bean.setData(dealReturnOpsParamVO);

        commonMqpper.updateSalesNoticeTaskCallBackParam(JSONUtil.toJsonStr(bean), orderModifyInfo.getBatchNo(),0);


    }

    private void sendMainForCancelCNOrder(OrderStateHandDto orderStateHandDto) {

       StringBuilder content = new StringBuilder();
       content.append("<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >")
               .append("<thead style=\"font-weight: bold;color: #000000;background-color: #B8CCE4;\" >")
               .append("<tr>" + "<td width=\"15%\" >订单号</td>" + "<td width=\"10%\" >型号</td>"
                       + "<td width=\"5%\" >数量</td>" + "<td width=\"10%\" >订货部门</td>" + "<td width=\"10%\" >下单人</td>"
                       + "<td width=\"25%\" >删除原因</td>" + "<td width=\"15%\" >删除时间</td>" + "</tr>")
               .append("</thead><tbody>");
       content.append("<tr>").append("<td>").append(orderStateHandDto.getOrderNo()).append("</td>").append("<td>")
               .append(orderStateHandDto.getModelNo()).append("</td>").append("<td>")
               .append(orderStateHandDto.getQuantity() == null ? "" : orderStateHandDto.getQuantity()).append("</td>")
               .append("<td>").append(orderStateHandDto.getOptUser() == null ? "" : orderStateHandDto.getOptUser())
               .append("</td>").append("<td>")
               .append(StringUtils.isBlank(orderStateHandDto.getDeptNo()) ? "" : orderStateHandDto.getDeptNo())
               .append("</td>").append("<td>").append(orderStateHandDto.getStateDes()).append("</td>").append("<td>")
               .append(DateUtil.dateToDateTimeString(new Date()))
               .append("</td>").append("</tr>");
       content.append("</tbody></table></br>");
       // 获取邮箱
       ResultVo<DataTypeVO> cnc = dictDataServiceFeignApi.getDataTypeCodesInfo("9004", "CNC");
       String strEamil = "zhizao6dd3@smc.com.cn;zhangchunmei@smc.com.cn;liyanchun@smc.com.cn;qianzhenzhen@smc.com.cn";
       String strContent = "中国制造订单删除通知邮件.";
       OpsMail mail = new OpsMail();
       if (!cnc.isSuccess()) {
           mail.setMailTo(strEamil.replaceAll(";", ","));
       } else {
           strEamil = cnc.getData().getExtNote1();
           mail.setMailTo(strEamil.replaceAll(";", ","));
           mail.setCc(cnc.getData().getExtNote2().replaceAll(";", ","));
       }
       mail.setSubject(strContent);
       mail.setContext(content.toString());
       mail.setSendDate(new Date());
       mail.setStatus(SendStatusEnum.INIT.getType());
       mail.setInsertTime(new Date());
       opsMailMapper.insertSelective(mail);
    }

    private void sendMailForGZ(OrderStateHandDto orderStateHandDto) {

        StringBuilder content = new StringBuilder();
        content.append("<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >")
                .append("<thead style=\"font-weight: bold;color: #000000;background-color: #B8CCE4;\" >")
                .append("<tr>" + "<td width=\"15%\" >订单号</td>" + "<td width=\"10%\" >型号</td>"
                        + "<td width=\"5%\" >数量</td>" + "<td width=\"10%\" >订货部门</td>" + "<td width=\"10%\" >下单人</td>"
                        + "<td width=\"25%\" >删除原因</td>" + "<td width=\"15%\" >删除时间</td>" + "</tr>")
                .append("</thead><tbody>");
        content.append("<tr>").append("<td>").append(orderStateHandDto.getOrderNo()).append("</td>").append("<td>")
                .append(orderStateHandDto.getModelNo()).append("</td>").append("<td>")
                .append(orderStateHandDto.getQuantity() == null ? "" : orderStateHandDto.getQuantity()).append("</td>")
                .append("<td>").append(orderStateHandDto.getDeptNo() == null ? "" : orderStateHandDto.getDeptNo())
                .append("</td>").append("<td>")
                .append(StringUtils.isBlank(orderStateHandDto.getOrderPsnNo()) ? "" : orderStateHandDto.getOrderPsnNo())
                .append("</td>").append("<td>").append(orderStateHandDto.getStateDes()).append("</td>").append("<td>")
                .append(DateUtil.dateToDateTimeString(new Date()))
                .append("</td>").append("</tr>");
        content.append("</tbody></table></br>");
        // 获取邮箱
        ResultVo<DataTypeVO> cnc = dictDataServiceFeignApi.getDataTypeCodesInfo("9004", "GP");
        String strEamil = "jiangjingjing@smc.com.cn;mairuixia@smc.com.cn";
        String strContent = "广州制造订单删除通知邮件.";
        OpsMail mail = new OpsMail();
        if (!cnc.isSuccess()) {
            mail.setMailTo(strEamil.replaceAll(";", ","));
        } else {
            strEamil = cnc.getData().getExtNote1();
            mail.setMailTo(strEamil.replaceAll(";", ","));
            mail.setCc(cnc.getData().getExtNote2().replaceAll(";", ","));
        }
        mail.setBcc("webservice@smcgz.com.cn,isod02@smc.com.cn");
        mail.setSubject(strContent);
        mail.setContext(content.toString());
        mail.setSendDate(new Date());
        mail.setStatus(SendStatusEnum.INIT.getType());
        mail.setInsertTime(new Date());
        opsMailMapper.insertSelective(mail);
    }

    private void sendCancelPrestockAndCustomerOrderEmail(OrderStateHandDto orderStateHandDto) {

        Object o = redisUtils.hGet("ops:emailOrder:cancelOrder", orderStateHandDto.getOrderNo());
        if (o != null) { // 防止重复发邮件
            return;
        }

        if (StringUtils.isBlank(orderStateHandDto.getOrderNo())) {
            return;
        }

        String deptNo;
        String customerNo;
        String orderType;
        String email = "";
        String deptEmail = "";
        String bc = "";

        deptNo = orderStateHandDto.getDeptNo();
        customerNo = orderStateHandDto.getCustomerNo();
        orderType = String.valueOf(orderStateHandDto.getOrderType());

        if (StringUtils.isBlank(orderStateHandDto.getOrderNo())) {
            return;
        }

        if (orderStateHandDto.getOrderNo().startsWith("99") && OrderTypeEnum.xxbkOrder.getCode().equals(orderType)) {
            orderType = OrderTypeEnum.binbkOrder.getCode();
        }
        if (orderStateHandDto.getOrderNo().startsWith("V")) {
            orderType = "3";
        }

        List<OrderModifyVO> doList = commonMqpper.getOrderCancelData(orderStateHandDto.getOrderNo());
        if (CollectionUtil.isNotEmpty(doList)) {
            OrderModifyVO modifyData = doList.get(0);
            if (StringUtils.isNotBlank(modifyData.getDeptNo())) {
                deptNo = modifyData.getDeptNo();
                ResultVo<DepartmentVO> resultVo = commonServiceFeignApi.getDepartmentInfo(deptNo);
                if (resultVo.isSuccess()) {
                    if (PublicUtil.isNotEmpty(resultVo.getData())) {
                        email = resultVo.getData().getEmailOrder(); // 客户订单
                    }
                    customerNo = modifyData.getCustomerNo();
                    orderType = String.valueOf(modifyData.getOrderType());
                }
            }
        }

        if ("1".equals(orderType)) {
            ResultVo<DepartmentVO> resultVo = commonServiceFeignApi.getDepartmentInfo(deptNo);
            if (resultVo.isSuccess()) {
                if (PublicUtil.isNotEmpty(resultVo.getData())) {
                    email = resultVo.getData().getEmailOrder(); // 客户订单
                }
            }
        }

        if (!orderType.equals("1")) {
            if (StringUtils.isBlank(orderStateHandDto.getOrderNo())) {
                return;
            }
            OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderStateHandDto.getOrderNo());
            OrderModifyVO stateDO = commonMqpper.getPurchaseOrderByNo(orderNoInfo.getOrderNo(),
                    String.valueOf(orderNoInfo.getItemNo()));
            if (stateDO == null) {
                return;
            }
            customerNo = stateDO.getCustomerNo();
            deptNo = stateDO.getDeptNo();
        }

        if (orderType.equals("21") && StringUtils.isNotBlank(orderStateHandDto.getWarehouseCode())) {
            ResultVo<DepartmentVO> resultVo = commonServiceFeignApi.getDepartmentInfo(deptNo);
            ResultVo<String> resultVo1 = commonServiceFeignApi.getWarehouseType(orderStateHandDto.getWarehouseCode());
            if (resultVo1.isSuccess()) {
                String warehouseType = resultVo1.getData();
                if ("SUB".equalsIgnoreCase(warehouseType)) {
                    orderType = orderType + "-2"; // 分库补库
                    email = resultVo.getData() == null ? "" : resultVo.getData().getEmailSubStock();
                } else {
                    orderType = orderType + "-1"; // 在库补库
                    email = resultVo.getData() == null ? "" : resultVo.getData().getEmailUserStock();
                }
            }
        }
        if (orderType.equals("3")) {
            ResultVo<DepartmentVO> resultVo = commonServiceFeignApi.getDepartmentInfo(deptNo);
            email = resultVo.getData() == null ? "" : resultVo.getData().getEmailCSStock();
        }

        if (PublicUtil.isNotEmpty(deptNo)) {
            ResultVo<DepartmentVO> resultVo = commonServiceFeignApi.getDepartmentInfo(deptNo);
            deptEmail = resultVo.getData() == null ? "" : resultVo.getData().getEmailAddr();
        }

        if (email == null) {
            email = "";
        }

        ResultVo<List<DataCodeVO>> dataCodes = dictDataServiceFeignApi.getDataCodes("9006");

        if (dataCodes.isSuccess() && CollectionUtils.isNotEmpty(dataCodes.getData())) {
            List<DataCodeVO> data = dataCodes.getData();
            Map<String, DataCodeVO> dataCodeVOMap = data.stream()
                    .collect(Collectors.toMap(DataCodeVO::getCode, Function.identity()));
            if (dataCodeVOMap.containsKey(orderType)) {
                DataCodeVO dataCodeVO = dataCodeVOMap.get(orderType);
                email += dataCodeVO.getExtNote1();
                if (StringUtils.isNotBlank(dataCodeVO.getExtNote2())) {
                    bc = dataCodeVO.getExtNote2();
                }
            }
        }
        if (PublicUtil.isNotEmpty(deptEmail)) {
            email += ";" + deptEmail;
        }
        if (email.startsWith(";")) {
            email = email.substring(email.indexOf(";") + 1);
        }
        if (StringUtils.isBlank(email)) {
            return;
        }
        ResultVo<String> deptNameByNo = commonServiceFeignApi.getDeptNameByNo(deptNo);
        String deptName = "";
        if (deptNameByNo.isSuccess()) {
            deptName = deptNameByNo.getData();
        }
        String customerName = "";
        ResultVo<String> customerNameByNo = commonServiceFeignApi.getCustomerNameByNo(customerNo);
        if (customerNameByNo.isSuccess()) {
            customerName = customerNameByNo.getData();
        }
        // 获取邮箱
        String strContent = " 先行在库及客户订单删除通知邮件.";
        String content = "<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >"
                + "<thead style=\"font-weight: bold;color: #000000;background-color: #B8CCE4;\" >" + "<tr>"
                + "<td width=\"10%\" >营业所</td>" + "<td width=\"15%\" >订单号</td>" + "<td width=\"10%\" >型号</td>"
                + "<td width=\"5%\" >数量</td>" + "<td width=\"10%\" >客户代码</td>" + "<td width=\"10%\" >客户名称</td>"
                + "<td width=\"25%\" >删除原因</td>" + "<td width=\"15%\" >删除时间</td>" + "</tr>" + "</thead><tbody>" + "<tr>"
                + "<td>" + deptName + "</td>" + "<td>" + orderStateHandDto.getOrderNo()
                + "</td>" + "<td>" + orderStateHandDto.getModelNo() + "</td>" + "<td>"
                + (orderStateHandDto.getQuantity() == null ? "" : orderStateHandDto.getQuantity()) + "</td>" + "<td>"
                + (customerNo == null ? "" : customerNo) + "</td>" + "<td>"
                + (customerNo == null ? "" : customerName) + "</td>" + "<td>"
                + orderStateHandDto.getStateDes() + "</td>" + "<td>" + DateUtil.dateToDateTimeString(new Date())
                + "</td>" +
                "</tr>" + "</tbody></table></br>";
        log.info("先行在库及客户订单删除'" + orderStateHandDto.getOrderNo() + "'发送至邮件:" + email);

        if (StringUtils.isNotBlank(email)) {
            OpsMail opsMailDO = new OpsMail();
            opsMailDO.setMailTo(email.replaceAll(";", ","));
            opsMailDO.setSubject(strContent);
            opsMailDO.setContext(content);
            opsMailDO.setSendDate(new Date());
            opsMailDO.setBcc("webservice@smcgz.com.cn");
            opsMailDO.setStatus(SendStatusEnum.INIT.getType());
            opsMailDO.setInsertTime(new Date());
            opsMailMapper.insertSelective(opsMailDO);
        } else {
            log.error("先行在库及客户订单删除" + orderStateHandDto.getOrderNo() + "发送至邮件失败, == > 邮箱为空.");
        }

    }

    private void sendShikomiInterceptMsgEmail(OrderStateHandDto orderStateHandDto) {
        if (!orderStateHandDto.getStateDes().toUpperCase().contains("SHIKOMI")) {
            return;
        }

        log.info("进入SHIKOMI删单拦截 ==>> 参数: {}", JSONObject.toJSONString(orderStateHandDto));

        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(orderStateHandDto.getOrderNo());

        List<OpsPurchaseorder> opsPurchaseorders = commonMqpper.queryPurchaseOrder(orderNoInfo.getOrderNo(), String.valueOf(orderNoInfo.getItemNo()));
        if (CollectionUtils.isEmpty(opsPurchaseorders)) {
            return;
        }

        OpsPurchaseorder opsRequestpurchaseDO = opsPurchaseorders.get(0);

        if (StringUtils.isBlank(opsRequestpurchaseDO.getDeptno())) {
            return;
        }
        log.info("进入SHIKOMI删单拦截 ==>> 请购单: {}", JSONObject.toJSONString(opsRequestpurchaseDO));
        // 获取营业所邮箱
        ResultVo<DepartmentVO> departmentInfo = commonServiceFeignApi
                .getDepartmentInfo(opsRequestpurchaseDO.getApplyDeptNo());
        if (!departmentInfo.isSuccess() || StringUtils.isBlank(departmentInfo.getData().getEmailAddr())) {
            return;
        }
        DepartmentVO departmentVO = departmentInfo.getData();
        log.info("进入SHIKOMI删单拦截 ==>> 部门信息: {}", JSONObject.toJSONString(departmentVO));
        // 收件人邮箱
        StringBuilder email = new StringBuilder();
        // 抄送人邮箱
        StringBuilder ccEmail = new StringBuilder();

        // 先行在库补库单88*以及客户订单
        if (OrderTypeEnum.saleOrder.getCode().equals(opsRequestpurchaseDO.getOrdtype())
                || (OrderTypeEnum.xxbkOrder.getCode().equals(opsRequestpurchaseDO.getOrdtype())
                && opsRequestpurchaseDO.getOrderno().startsWith("88"))) {
            email.append(departmentVO.getEmailAddr()).append(";");
        }
        // 先行在库补库单88*
        if (OrderTypeEnum.xxbkOrder.getCode().equals(opsRequestpurchaseDO.getOrdtype())
                && opsRequestpurchaseDO.getOrderno().startsWith("88")) {
            email.append(departmentVO.getEmailUserStock()).append(";");
            ccEmail.append("linlijia@smc.com.cn;wengweijie@smc.com.cn;jichunfei@smc.com.cn;sub-inventory@smc.com.cn;");
        }
        // 分库补库单88*
        if (StringUtils.isNotBlank(opsRequestpurchaseDO.getReceivewarehouseid())) {
            Boolean aBoolean = commonServiceFeignApi.judgeIsSubWareHouse(opsRequestpurchaseDO.getReceivewarehouseid());
            if (aBoolean) {
                if (OrderTypeEnum.xxbkOrder.getCode().equals(opsRequestpurchaseDO.getOrdtype())
                        && opsRequestpurchaseDO.getOrderno().startsWith("88")) {
                    email.append(departmentVO.getEmailSubStock()).append(";");
                    ccEmail.append("sub-inventory@smc.com.cn");
                }
            }
        }
        // 委托在库补库单 V*
        if (OrderTypeEnum.wtzkOrder.getCode().equals(opsRequestpurchaseDO.getOrdtype())
                && opsRequestpurchaseDO.getOrdtype().startsWith("V")) {
            email.append(departmentVO.getEmailCSStock()).append(";");
            ccEmail.append("consignmentsh@smc.com.cn;isod13@smc.com.cn;");
        }
        // Bin补库订单
        if (OrderTypeEnum.binbkOrder.getCode().equals(opsRequestpurchaseDO.getOrdtype())
                && opsRequestpurchaseDO.getOrdtype().startsWith("99")) {
            ccEmail.append("xuxiaoying@smc.com.cn;");
        }
        // 客户订单
        if (OrderTypeEnum.saleOrder.getCode().equals(opsRequestpurchaseDO.getOrdtype())) {
            email.append(departmentVO.getEmailOrder()).append(";");
        }

        String customerName = "";
        ResultVo<String> customerNameByNo = commonServiceFeignApi.getCustomerNameByNo(orderStateHandDto.getCustomerNo());
        if (customerNameByNo.isSuccess()) {
            customerName = customerNameByNo.getData();
        }

        StringBuilder content = new StringBuilder();
        content.append("<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >")
                .append("<thead style=\"font-weight: bold;color: #000000;background-color: #B8CCE4;\" >")
                .append("<tr>" + "<td width=\"15%\" >订单号</td>" + "<td width=\"20%\" >客户名称</td>"
                        + "<td width=\"15%\" >型号</td>" + "<td width=\"5%\" >数量</td>" + "<td width=\"40%\" >拦截原因</td>"
                        + "</tr>")
                .append("</thead><tbody>");
        content.append("<tr>").append("<td>").append(orderStateHandDto.getOrderNo()).append("</td>").append("<td>")
                .append(orderStateHandDto.getCustomerNo() + "["
                        + customerName + "]")
                .append("</td>").append("<td>").append(orderStateHandDto.getModelNo()).append("</td>").append("<td>")
                .append(orderStateHandDto.getQuantity() == null ? "" : orderStateHandDto.getQuantity()).append("</td>")
                .append("<td>").append(orderStateHandDto.getStateDes()).append("</td>").append("</tr>");
        content.append("</tbody></table></br>");
        String strContent = "SHIKOMI拦截订单通知邮件,本邮件由系统发出,请勿直接回复本邮件.";

        ResultVo<DataTypeVO> cnc = dictDataServiceFeignApi.getDataTypeCodesInfo("9004", "SHIKOMI");
        OpsMail mail = new OpsMail();
        if (!cnc.isSuccess()) {
            email.append("deliverysh@smc.com.cn");
            mail.setMailTo(email.toString().replaceAll(";", ","));
        } else {
            email.append(cnc.getData().getExtNote1());
            mail.setMailTo(cnc.getData().getExtNote1().replaceAll(";", ","));
        }
        if (StringUtils.isNotBlank(ccEmail.toString())) {
            mail.setCc(ccEmail.toString().replaceAll(";", ","));
        }
        mail.setBcc("webservice@smcgz.com.cn");
        mail.setSubject(strContent);
        mail.setContext(content.toString());
        mail.setSendDate(new Date());
        mail.setStatus(SendStatusEnum.INIT.getType());
        mail.setInsertTime(new Date());
        opsMailMapper.insertSelective(mail);

    }

}
