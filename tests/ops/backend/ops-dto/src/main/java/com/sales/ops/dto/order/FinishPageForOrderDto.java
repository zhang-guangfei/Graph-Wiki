package com.sales.ops.dto.order;

import java.io.Serializable;
import java.util.List;

/**
 * @author C14717
 * @version 1.0
 * @description: 完纳订单页面
 * @date 2023/8/17 14:10
 */
public class FinishPageForOrderDto implements Serializable {

    private static final long serialVersionUID = -6446517458413919353L;
    /* 客户单号 */
    private String orderNo;

    private String orderItem;

    private String orderFoNo;
    /* 型号 */
    private String modelNO;
    /* 订单数量 */
    private Integer orderQty;
    /* 已发数量 */
    private Integer outQty;
    /* 完纳数量 */
    private Integer finishQty;

    private String userName;

    private String msg;

    private List<FinishPoListForDto> poList;

    public FinishPageForOrderDto(String orderNo, String orderItem,String orderFoNo,String modelNO){
        this.orderNo = orderNo;
        this.orderItem = orderItem;
        this.orderFoNo = orderFoNo;
        this.modelNO = modelNO;
    }



    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public String getOrderFoNo() {
        return orderFoNo;
    }

    public void setOrderFoNo(String orderFoNo) {
        this.orderFoNo = orderFoNo;
    }

    public String getModelNO() {
        return modelNO;
    }

    public void setModelNO(String modelNO) {
        this.modelNO = modelNO;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Integer getOutQty() {
        return outQty;
    }

    public void setOutQty(Integer outQty) {
        this.outQty = outQty;
    }

    public Integer getFinishQty() {
        return finishQty;
    }

    public void setFinishQty(Integer finishQty) {
        this.finishQty = finishQty;
    }

    public List<FinishPoListForDto> getPoList() {
        return poList;
    }

    public void setPoList(List<FinishPoListForDto> poList) {
        this.poList = poList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "{" +
                "orderNo='" + orderNo + '\'' +
                ", orderItem='" + orderItem + '\'' +
                ", orderFoNo='" + orderFoNo + '\'' +
                ", modelNO='" + modelNO + '\'' +
                ", orderQty=" + orderQty +
                ", outQty=" + outQty +
                ", finishQty=" + finishQty +
                ", userName='" + userName + '\'' +
                ", msg='" + msg + '\'' +
                ", poList=" + poList +
                '}';
    }
}
