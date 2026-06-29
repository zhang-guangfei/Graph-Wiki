package com.sales.ops.dto.inventory;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/11/10 10:28
 */
public class InvCkTargetDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private InventoryCkByOrderOutDto outDto;

    private Integer AllotQuantity;

    public InvCkTargetDto(){}

    public InvCkTargetDto(Integer AllotQuantity, InventoryCkByOrderOutDto outDto){
        this.AllotQuantity = AllotQuantity;
        this.outDto = outDto;
    }

    public Integer getAllotQuantity() {
        return AllotQuantity;
    }

    public void setAllotQuantity(Integer allotQuantity) {
        AllotQuantity = allotQuantity;
    }

    public InventoryCkByOrderOutDto getOutDto() {
        return outDto;
    }

    public void setOutDto(InventoryCkByOrderOutDto outDto) {
        this.outDto = outDto;
    }
}
