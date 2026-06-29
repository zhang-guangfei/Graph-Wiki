package com.sales.ops.dto.replacement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: $ 计划匹配指令实体
 * @description：
 * @date ：Created in 2024/12/3 13:52
 */
public class NotifyShipmentPlanMatchOrderDto implements Serializable {
    private static final long serialVersionUID = 1486388249053078408L;

    private String planNo;

    private Date hopeDate;

    private Date wlDate;

    private List<NotifyShipmentAllowSplitDto> list;

    public NotifyShipmentPlanMatchOrderDto(){}

    public NotifyShipmentPlanMatchOrderDto(String planNo,Date hopeDate, Date wlDate){
        this.planNo =  planNo;
        this.hopeDate = hopeDate;
        this.wlDate = wlDate;
        this.list = new ArrayList<>();
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public List<NotifyShipmentAllowSplitDto> getList() {
        return list;
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

    public void setList(List<NotifyShipmentAllowSplitDto> list) {
        this.list = list;
    }

    public void addList(NotifyShipmentAllowSplitDto obj) {
        this.list.add(obj);
    }
}
