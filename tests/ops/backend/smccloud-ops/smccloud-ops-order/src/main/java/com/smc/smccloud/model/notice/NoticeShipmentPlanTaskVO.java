package com.smc.smccloud.model.notice;

import com.sales.ops.dto.replacement.NotifyShipmentPlanSaveDto;
import lombok.Data;

import java.util.List;

@Data
public class NoticeShipmentPlanTaskVO {
    private String userName;
    private List<NotifyShipmentPlanSaveDto> dataList;
}
