package com.sales.ops.dto.replacement;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/11/29 9:09
 */
public class NotifyShipmentPlanPageDto implements Serializable {


    private static final long serialVersionUID = -3516916300210759541L;
    private Long Id;
    private String planNo;
    private String orderFno;
    private Integer orderQty; //订单数量
    private String orderModelNo;//订单型号
    private String modelNo;//型号
    private Integer planQty; //通知发货数量
    private Integer matchQty = 0;//匹配数量
    private Integer outQty = 0;//发货数量
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date hopeDate;//客户货期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date wlDate;//物流指定货期
    private Integer status = 0;//状态 0待分配 1分配中 2分配完成

    private String statusMsg = "待分配";
    private String outStatus = "准备中";//发货状态
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    private String creator;

    private String remark;

    private List<NotifyShipmentPlanItemPageDto> detailList;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public String getOrderModelNo() {
        return orderModelNo;
    }

    public void setOrderModelNo(String orderModelNo) {
        this.orderModelNo = orderModelNo;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    public Integer getMatchQty() {
        return matchQty;
    }

    public void setMatchQty(Integer matchQty) {
        this.matchQty = matchQty;
    }

    public Integer getOutQty() {
        return outQty;
    }

    public void setOutQty(Integer outQty) {
        this.outQty = outQty;
    }

    public Date getHopeDate() {
        return hopeDate;
    }

    public void setHopeDate(Date hopeDate) {
        this.hopeDate = hopeDate;
    }

    public Date getWlDate() {
        return wlDate;
    }

    public void setWlDate(Date wlDate) {
        this.wlDate = wlDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOutStatus() {
        if(Objects.nonNull(this.outQty) && this.outQty>0){
            this.outStatus = "部分发货";
        }
        if(Objects.nonNull(this.outQty) && Objects.nonNull(this.planQty)){
            if(this.outQty.equals(this.planQty)){
                this.outStatus = "发货完成";
            }
        }
        return outStatus;
    }

    public String getStatusMsg() {
        if(StringUtils.isNotEmpty(this.remark)){
            if(this.remark.equals("删单")){
                this.statusMsg = "取消";
                return statusMsg;
            }
            if(Objects.nonNull(this.outQty) && Objects.nonNull(this.planQty) && this.outQty.equals(this.planQty)){
                this.statusMsg = "分配完成";
                return statusMsg;
            }else {
                this.statusMsg = "取消";
                return statusMsg;
            }
        }
        if(Objects.nonNull(status) && status == 2){
            this.statusMsg = "分配完成";
            return statusMsg;
        }
        if(Objects.nonNull(this.matchQty) && this.matchQty>0){
            this.statusMsg = "分配中";
        }
        if(Objects.nonNull(this.matchQty) && Objects.nonNull(this.planQty)){
            if(Objects.equals(this.planQty, this.matchQty)){
                this.statusMsg = "分配完成";
            }
        }
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<NotifyShipmentPlanItemPageDto> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<NotifyShipmentPlanItemPageDto> detailList) {
        this.detailList = detailList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
