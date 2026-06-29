package com.sales.ops.api.dto.tms;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description： 7.3 tms 推送物流路由信息
 * @date ：Created in 2021/12/27 16:18
 */
public class LogisticsRoutingInfoDto implements Serializable {

    private static final long serialVersionUID = -3611559736908753197L;

    private String orderNo;    //订单号
    private String waybillNo;    //运单号
    private String reference1;    //客户单号
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date send_time;    //推送时间
    private String message;    //路由信息
    private String send_flag;    //标记
    private String send_who;    //推送人（默认TMS）
    private String deliverStatus;    //签收状态
    private String udf01;    //自定义01
    private String udf02;    //自定义02
    private String udf03;    //自定义03
    private String udf04;    //自定义04
    private String udf05;    //自定义05
    private String sysId;    //Datahub流水号

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getReference1() {
        return reference1;
    }

    public void setReference1(String reference1) {
        this.reference1 = reference1;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSend_flag() {
        return send_flag;
    }

    public void setSend_flag(String send_flag) {
        this.send_flag = send_flag;
    }

    public String getSend_who() {
        return send_who;
    }

    public void setSend_who(String send_who) {
        this.send_who = send_who;
    }

    public String getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(String deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public String getUdf01() {
        return udf01;
    }

    public void setUdf01(String udf01) {
        this.udf01 = udf01;
    }

    public String getUdf02() {
        return udf02;
    }

    public void setUdf02(String udf02) {
        this.udf02 = udf02;
    }

    public String getUdf03() {
        return udf03;
    }

    public void setUdf03(String udf03) {
        this.udf03 = udf03;
    }

    public String getUdf04() {
        return udf04;
    }

    public void setUdf04(String udf04) {
        this.udf04 = udf04;
    }

    public String getUdf05() {
        return udf05;
    }

    public void setUdf05(String udf05) {
        this.udf05 = udf05;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }
}
