package com.sales.ops.service.log;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsInventoryLog;
import com.sales.ops.db.entity.OpsRo;
import com.sales.ops.db.entity.OpsRoItemInventory;
import com.sales.ops.dto.util.UserDto;

import java.util.List;

public interface OpsInventoryLogService {

    void insertOpsInventoryLogForRo(OpsRo opsRo, String modelNo, Long invnetoryId, int qty, UserDto userDto) throws OpsException;

    void insertOpsInventoryLogForRo(OpsRo opsRo, OpsRoItemInventory roItemInventory, UserDto userDto);

    void insertOpsInventoryLogForDo(OpsDo opsDo, long inventoryId, String invnetoryTableType, int qty, UserDto userDto) throws OpsException;

    Integer addLog(OpsInventoryLog log);

    /**
     * 查询库存日志表
     */
    List<OpsInventoryLog> getOpsInventoryLogByVoucherCode(String voucherCode);

}
