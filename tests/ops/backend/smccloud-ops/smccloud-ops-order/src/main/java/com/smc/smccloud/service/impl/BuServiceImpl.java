package com.smc.smccloud.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.mapper.OPSVRequisitionStausOtherToSalesMapper;
import com.smc.smccloud.model.BuInterface.BuInvoiceResponse;
import com.smc.smccloud.model.VSalesManuorder.OPSVRequisitionStausOtherToSalesDO;
import com.smc.smccloud.model.cnfactory.OrderReplyResponse;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.orderstate.OrderStateEnum;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.service.BuService;
import com.smc.smccloud.service.OrderStateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author: B90034
 * Date: 2021-12-01 15:26
 * Description:
 */
@Slf4j
@Service
@RefreshScope
public class BuServiceImpl implements BuService {

    @Value("${buService.token-address}")
    private String tokenAddress;
    @Value("${buService.interface-address}")
    private String interfaceAddress;

    //Add by A78027 2023-5-8 for bug-10689
    @Value("${buService.password}")
    private String gwPassword;

    @Value("${buService.username}")
    private String gwUsername;

    @Resource
    private OPSVRequisitionStausOtherToSalesMapper opsvRequisitionStausOtherToSalesMapper;
    @Resource
    private OrderStateService orderStateService;
    @Resource
    private RedisManager redisManager;

//    /**
//     * Token 获取接口URL
//     */
//    private final static String TOKEN_URL = "http://10.116.1.234:10300/auth/buoath/token";

    /**
     * Token 获取接口传参
     * 账户：sales
     * 密码(需Base64加密)：bf2a10f917d7445a8448446b383b374e
     */
    //private final static String TOKEN_PARAM = "{\"password\":\"ZTZjZjY0NTM0ZmRhNGRiZmJlZDhhNjZjYjMyNmYzOWU=\",\"appId\":\"sales\"}";

    /**
     * Authorization
     */
    private static String Authorization = "";

    /**
     * 关务发票查询接口URL
     */
    private final static String IMPORT_INVOICE_INFO_URL_PROD = "/service/sales/queryImportInvoiceInfo?plantMark=%s&invNo=%s&startTime=%s&endTime=%s&pageNum=%s&pageSize=%s";

    /**
     * 营业订单返信查询接口
     */
    private final static String QUERY_SALESORDER_REPLY = "/service/sales/querySalesOrderReply";

    /**
     * 入库信息回填接口
     */
    private final static String UPDATE_WAREHOUSING_INFO = "/service/sales/updateReceiveTime?plantMark=%s&invNo=%s&receiveTime=%s&userName=%s";

    /**
     * 获取token
     */
    private void getToken() {
//        String url = "http://10.116.1.234:10300/auth/buoath/token";

        JSONObject param = JSONUtil.createObj();
        param.put("appId", gwUsername);
        param.put("password", gwPassword);
        //log.info("request body = {}", param.toString());

        HttpResponse httpResponse = HttpUtil.createPost(tokenAddress)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(param.toString())
                .execute();

        Authorization = httpResponse.header("Authorization");
        if (Authorization == null || "".equals(Authorization)) {
            throw new BusinessException("Authorization is null.");
        }
    }

    /**
     * 刷新token
     */
    private void refreshToken(HttpResponse response) {
        if (response != null) {
            // 如果 Token 有效期过半,该接口返回时会携带 refreshToken, 可使用 refreshToken 继续请求.
            String refreshToken = response.header("refreshToken");
            if (refreshToken != null && !"".equals(refreshToken)) {
                Authorization = refreshToken;
            }
        }
    }

    @Override
    public BuInvoiceResponse queryImportInvoiceInfo(Map<String, String> param) {
        if (StringUtils.isBlank(Authorization)) {
            this.getToken();
        }

        BuInvoiceResponse buInvoiceResponse;
        HttpResponse response = null;
        try {
            String url = String.format(interfaceAddress + IMPORT_INVOICE_INFO_URL_PROD, param.get("plantMark"), param.get("invNo"),
                    URLEncoder.encode(param.get("startTime"), "UTF-8"),
                    URLEncoder.encode(param.get("endTime"), "UTF-8"),
                    param.get("pageNum"), param.get("pageSize"));
            response = HttpUtil.createPost(url)
                    .header("Authorization", Authorization)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .charset(StandardCharsets.UTF_8)
                    .execute();

            if (response.body() == null || "".equals(response.body())) {
                buInvoiceResponse = new BuInvoiceResponse();
                buInvoiceResponse.setCode(1);
                buInvoiceResponse.setMessage("进口发票查询查询无响应");
                return buInvoiceResponse;
            }
            buInvoiceResponse = JSON.parseObject(response.body(), BuInvoiceResponse.class);
            if (buInvoiceResponse.getCode() != 0) {
                Authorization = "";
            }
            return buInvoiceResponse;
        } catch (Exception e) {
            Authorization = "";
            log.error("从关务系统查询发票信息失败: params = {}, response = {}", param, response);
            throw new BusinessException("进口发票查询失败, " + e.getMessage() + ", param = " + param, e);
        } finally {
            this.refreshToken(response);
        }
    }

    @Override
    public OrderReplyResponse querySalesOrderReply(List<String> orderNos) {

        OrderReplyResponse replyResponse = new OrderReplyResponse();

        if (Authorization == null || "".equals(Authorization)) {
            this.getToken();
        }

        HttpResponse response;

        String json = null;
        if (orderNos != null && orderNos.size() > 0) {
            json = JSONUtil.toJsonStr(orderNos);
        }


        try {
            response = HttpUtil.createPost(interfaceAddress + QUERY_SALESORDER_REPLY)
                    .header("Authorization", Authorization)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .charset(StandardCharsets.UTF_8)
                    .body(json)
                    .execute();
            String strResponse = response.body();
            if (strResponse == null || "".equals(strResponse)) {
                replyResponse.setCode(1);
                replyResponse.setMessage("无数据返回");
                return replyResponse;
            }

            replyResponse = JSONUtil.toBean(strResponse, OrderReplyResponse.class);

            return replyResponse;
        } catch (Exception e) {
            throw new BusinessException("中国制造订单返信查询接口响应失败 error=> {} ", e);
        } finally {
            //this.refreshToken(response);
        }
    }

    @Override
    public OrderReplyResponse updateWarehousingInfo(Map<String, String> param) {

        OrderReplyResponse replyResponse = new OrderReplyResponse();

        if (Authorization == null || "".equals(Authorization)) {
            this.getToken();
        }

        HttpResponse response = null;
        try {
            String url = String.format(interfaceAddress + UPDATE_WAREHOUSING_INFO, param.get("plantMark"), param.get("invNo"),
                    URLEncoder.encode(param.get("receiveTime"), "UTF-8"),
                    param.get("userName"));

            response = HttpUtil.createPost(url)
                    .header("Authorization", Authorization)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .charset(StandardCharsets.UTF_8)
                    .execute();

            String strResponse = response.body();
            if (strResponse == null || "".equals(strResponse)) {
                replyResponse.setCode(1);
                replyResponse.setMessage("无数据返回");
                return replyResponse;
            }

            replyResponse = JSONUtil.toBean(strResponse, OrderReplyResponse.class);

            return replyResponse;

        } catch (Exception e) {
            Authorization = "";
            throw new BusinessException("调用关务系统回调接口失败, param = " + param, e);
        } finally {
            this.refreshToken(response);
        }

    }


    /*
    从中国工厂导入所有未发货订单状态
     */
    @Override
    public ResultVo<String> importCNFactoryNotSendOrderState() {

        // 从上次存储的时间接着读取  OPS_V_RequisitionStausOtherToSales
        Object o = redisManager.get("ops:VRequisitionStausOtherToSales:lastDate");
        String lastDate = "";
        // 当前日期 yyyy-MM-dd
        String dateString = DateUtil.dateToDateString(new Date());
        if (null == o) {
            lastDate = dateString + " 00:00:00";
        } else {
            lastDate = o.toString();
        }

        String currentTime = dateString + " 23:59:59";

        List<OPSVRequisitionStausOtherToSalesDO> listData = opsvRequisitionStausOtherToSalesMapper.findReqStausOtherToSalesListByDate(lastDate, currentTime);
        if (listData.isEmpty()) {
            return ResultVo.failure("暂无数据");
        }
        for (OPSVRequisitionStausOtherToSalesDO item : listData) {

            OrderStateVO orderStateVO = new OrderStateVO();
            OrderNoInfo orderNoInfo;
            if (item.getOrderNo().contains("-")) {
                orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo());
            } else {
                if (StringUtils.isNotBlank(item.getItemNo())) {
                    if (item.getItemNo().length() == 1) {
                        item.setItemNo("00"+item.getItemNo());
                    } else if (item.getItemNo().length() == 2) {
                        item.setItemNo("0"+item.getItemNo());
                    }
                }
                orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo()+item.getItemNo());
            }
            orderStateVO.setRorderNo(orderNoInfo.getOrderNo());
            orderStateVO.setOrderNo(orderNoInfo.getFullOrderNo());
            orderStateVO.setItemNo(orderNoInfo.getItemNo());
            orderStateVO.setSplitNo(orderNoInfo.getSplitItem());
            orderStateVO.setModelNo(StringUtils.isBlank(item.getModelNo()) ? "" : item.getModelNo());
//            if (item.getQuantity() != null) {
//                orderStateVO.setQuantity(item.getQuantity());
//            }
            if (item.getPoShipDate() != null) {
                // orderStateVO.setPoShipDate(item.getPoShipDate());
                orderStateVO.setStateDate(item.getPoShipDate());
            }
            orderStateVO.setRemark(StringUtils.isBlank(item.getCalReason()) ? "" : item.getCalReason());
            orderStateVO.setProvider("CNM");
            orderStateVO.setOptUserNo("cnm");
            orderStateVO.setOptUserName("cnm");
            String strDes = "";
            if (StringUtils.isNotBlank(item.getOrderType())) {
                strDes = strDes + item.getOrderType() + ",";
            }
            if (StringUtils.isNotBlank(item.getStatus())) {
                strDes = strDes + item.getStatus() + ",";
            }
            if (StringUtils.isNotBlank(item.getCalReason())) {
                strDes = strDes + item.getCalReason();
            }
            if (null != item.getUpdateDate()) {
                strDes = strDes + DateUtil.dateToDateString(item.getUpdateDate());
            }

            if (item.getStatus().equals("待接单")) {
                orderStateVO.setStateCode(OrderStateEnum.Purchareing.getCode());
            } else if (item.getStatus().equals("未接单删单")) {
                orderStateVO.setStateCode(OrderStateEnum.SupplierCanceled.getCode());
            } else if (item.getStatus().equals("已出库")) {
                orderStateVO.setStateCode(OrderStateEnum.SupplierShipped.getCode());
            } else if (item.getStatus().equals("已接单删单")) {
                orderStateVO.setStateCode(OrderStateEnum.SupplierCanceled.getCode());
            }

            orderStateVO.setStateDes(strDes);
            orderStateVO.setSupplierCode(convenSupplierCodeByOrderTypeName(item.getOrderType()));
            ResultVo<String> stringResultVo = orderStateService.addOrderState(orderStateVO);
            if (!stringResultVo.isSuccess()) {
                return ResultVo.failure("推送数据至队列失败.");
            }
        }

        Date updateDate = listData.get(0).getUpdateDate();
        String updateTime = DateUtil.dateToDateTimeString(updateDate);
        // 存储本次最后读取的时间
        redisManager.set("ops:VRequisitionStausOtherToSales:lastDate", updateTime);
        return ResultVo.success("获取营业订单状态完毕.");

    }

    public static String convenSupplierCodeByOrderTypeName(String orderTypeName) {
        if (StringUtils.isBlank(orderTypeName)) {
            return "";
        }
        if (orderTypeName.contains("天津")) {
            return "CT";
        } else if (orderTypeName.contains("北京")) {
            return "CM";
        } else if (orderTypeName.contains("中国") || orderTypeName.contains("制造")) {
            return "CN";
        } else if (orderTypeName.contains("上海")) {
            return "TZ";
        } else if (orderTypeName.contains("特注品订单")) {
            return "YZ";
        } else {
            return "";
        }
    }


}
