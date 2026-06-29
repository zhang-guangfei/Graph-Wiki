package com.sales.ops.serviceimpl.log;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsInventoryLogMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.InventoryTableTypeEnum;
import com.sales.ops.service.log.OpsInventoryLogService;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OpsInventoryLogServiceImpl implements OpsInventoryLogService {

    @Resource
    private OpsInventoryLogMapper inventoryLogMapper;
    @Autowired
    private BaseInventoryService baseInventoryService;

    @Override
    public void insertOpsInventoryLogForRo(OpsRo opsRo, String modelNo, Long invnetoryId, int qty, UserDto userDto) throws OpsException {
        OpsInventory opsInventory = baseInventoryService.getInventoryById(invnetoryId);
        OpsInventoryLog log = new OpsInventoryLog();
        log.setInventoryId(invnetoryId);
        log.setPropertyId(opsInventory.getInventoryPropertyId());
        log.setQuantity(qty);
        log.setVoucherCode(opsRo.getRoId());
        log.setVoucherType(opsRo.getRoType());
        log.setOrderNo(opsRo.getOrderId());
        if (!StringUtils.isEmpty(opsRo.getOrderItem())) {
            log.setItemNo(Integer.parseInt(opsRo.getOrderItem()));
        }
        log.setModelno(modelNo);
        log.setInvoiceNo(opsRo.getInvoiceNo());
        log.setWarehouseCode(opsRo.getWarehouseCode());
        log.setVersion(0);
        log.setDelflag(0);
        log.setCreTime(new Date());
        log.setCreator(userDto.getUserName());
        log.setType(true);
        addLog(log);
    }

    @Override
    public void insertOpsInventoryLogForRo(OpsRo opsRo, OpsRoItemInventory roItemInventory, UserDto userDto) {
        OpsInventory opsInventory = baseInventoryService.getInventoryById(roItemInventory.getInventoryId());
        OpsInventoryLog log = new OpsInventoryLog();
        log.setInventoryId(roItemInventory.getInventoryId());
        log.setPropertyId(opsInventory.getInventoryPropertyId());
        log.setQuantity(roItemInventory.getQty());
        log.setVoucherCode(opsRo.getRoId());
        log.setVoucherType(opsRo.getRoType());
        log.setOrderNo(opsRo.getOrderId());
        if (!StringUtils.isEmpty(opsRo.getOrderItem())) {
            log.setItemNo(Integer.parseInt(opsRo.getOrderItem()));
        }
        log.setModelno(opsInventory.getModelno());
        log.setInvoiceNo(opsRo.getInvoiceNo());
        log.setWarehouseCode(opsInventory.getWarehouseCode());
        log.setVersion(0);
        log.setDelflag(0);
        log.setCreTime(new Date());
        log.setCreator(userDto.getUserName());
        log.setType(true);
        addLog(log);
    }

    @Override
    public void insertOpsInventoryLogForDo(OpsDo opsDo, long inventoryId, String invnetoryTableType, int qty, UserDto userDto) throws OpsException {
        OpsInventoryLog opsInventoryLog = new OpsInventoryLog();
        opsInventoryLog.setInventoryId(inventoryId);
        opsInventoryLog.setInventoryTableType(invnetoryTableType);
        opsInventoryLog.setQuantity(qty);
        opsInventoryLog.setVoucherCode(opsDo.getDoId());
        opsInventoryLog.setVoucherType(opsDo.getDoType());
        opsInventoryLog.setOrderNo(opsDo.getOrderId());
        opsInventoryLog.setItemNo(Integer.parseInt(opsDo.getOrderItem()));
        if (InventoryTableTypeEnum.NORMAL.getType().equals(invnetoryTableType)) {
            OpsInventory opsInventory = baseInventoryService.getInventoryById(inventoryId);
            opsInventoryLog.setModelno(opsInventory.getModelno());
            opsInventoryLog.setWarehouseCode(opsInventory.getWarehouseCode());
            opsInventoryLog.setPropertyId(opsInventory.getInventoryPropertyId());
        } else {
            OpsInventoryMove opsInventoryMove = baseInventoryService.getInventoryMoveById(inventoryId);
            opsInventoryLog.setModelno(opsInventoryMove.getModelno());
            opsInventoryLog.setWarehouseCode(opsInventoryMove.getWarehouseCode());
            opsInventoryLog.setPropertyId(opsInventoryMove.getInventoryPropertyId());
        }
        opsInventoryLog.setInvoiceNo(null);
        opsInventoryLog.setVersion(0);
        opsInventoryLog.setDelflag(0);
        opsInventoryLog.setCreTime(new Date());
        opsInventoryLog.setCreator(userDto.getUserName());
        opsInventoryLog.setType(false);
        addLog(opsInventoryLog);
    }

    @Override
    public Integer addLog(OpsInventoryLog log) {
        return inventoryLogMapper.insertSelective(log);
    }


    @Override
    public List<OpsInventoryLog> getOpsInventoryLogByVoucherCode(String voucherCode) {
        // add ro to adapter
        OpsInventoryLogExample example = new OpsInventoryLogExample();
        OpsInventoryLogExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0);
        criteria.andVoucherCodeEqualTo(voucherCode);
//        criteria.andVoucherTypeEqualTo(voucherType);
        return inventoryLogMapper.selectByExample(example);
    }
}
