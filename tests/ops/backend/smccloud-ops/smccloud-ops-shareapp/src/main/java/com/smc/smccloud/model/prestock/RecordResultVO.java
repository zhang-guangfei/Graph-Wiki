package com.smc.smccloud.model.prestock;

import com.smc.smccloud.model.inventory.OpsInventoryLogVO;
import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2024/12/6 13:20
 * @Descripton TODO
 */
@Data
public class RecordResultVO {

    private List<OpsInventoryLogVO> logVOList;

    private List<FindAvgQtyRecordsVO> findAvgQtyRecordsVOS;
}
