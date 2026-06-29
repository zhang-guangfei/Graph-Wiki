package com.sales.ops.dto.inqb;

import java.io.Serializable;
import java.util.List;

/**
 * @description:提供给门户的，INQB可用查询的接口参数信息,以及接口的返回参数信息
 * @author: B91717
 * @time: 2024/7/9 10:15
 */
public class InqbQueryRequestParam implements Serializable {
    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 客户
     */
    private String customerNo;

    /**
     * 用户
     */
    private String userNo;

    /**
     * 最终用户
     */
    private String endUser;

    /**
     * 返回的可用申请号集合
     */
    private List<String> inqbApplyNoList;

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    public List<String> getInqbApplyNoList() {
        return inqbApplyNoList;
    }

    public void setInqbApplyNoList(List<String> inqbApplyNoList) {
        this.inqbApplyNoList = inqbApplyNoList;
    }
}
