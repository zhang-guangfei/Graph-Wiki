package com.sales.ops.dto.easyexcel.delivery;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/7/4 9:52
 */
public class OrderModifyRecordExcelDto implements Serializable {
    private static final long serialVersionUID = -1613626799106811482L;

    @ExcelProperty("操作")
    private String handle;

    @ExcelProperty("单号")
    private String orderid;
    @ExcelProperty("项号")
    private Integer orderItem;
    @ExcelProperty("工号")
    private String userName;
    @ExcelProperty("时间")
    private Date createTime;

    @ExcelProperty("处理结果")
    private String handleResult;

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }
}
