package com.sales.ops.serviceimpl.inventory.adjustment;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsInventoryAdjMapper;
import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryAdj;
import com.sales.ops.db.extdao.AdjustInventoryQtyDao;
import com.sales.ops.service.inventory.adjustment.CalInventoryQtyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2022/11/9 14:33
 */

@Slf4j
@Service
public class CalInventoryQtyServiceImpl implements CalInventoryQtyService {


    @Autowired
    private AdjustInventoryQtyDao adjustInventoryQtyDao;
    @Autowired
    private OpsInventoryAdjMapper opsInventoryAdjMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void calInventoryQty(Long diffId, String warehouse, String modelno, Integer quantity) throws OpsException {
        List<OpsInventoryAdj> adjList = new ArrayList<>();
        List<OpsInventory> TY = adjustInventoryQtyDao.findInventoryTY(modelno, warehouse);
        List<OpsInventory> GK = adjustInventoryQtyDao.findInventoryGK(modelno, warehouse);
        List<OpsInventory> QB = adjustInventoryQtyDao.findInventoryQB(modelno, warehouse);

        if (quantity > 0) {
            for (OpsInventory inventory : TY) {
                Integer qty = inventory.getQuantity() > quantity ? quantity : (inventory.getQuantity() < 0 ? 0 : inventory.getQuantity());
                //baseInventoryService.UpdateQtyInventory(inventory.getInventoryId(), -1 * qty, InventoryTableTypeEnum.NORMAL.getType(), UserDto.ADMIN);
                quantity = quantity - qty;
                OpsInventoryAdj adj = new OpsInventoryAdj();
                adj.setDiffId(diffId);
                adj.setInventoryId(inventory.getInventoryId());
                adj.setQuantityBefore(inventory.getQuantity());
                adj.setQuantityAdj(qty);
                adj.setCreateTime(new Date());
                adj.setCreator("C12961_code");
                adjList.add(adj);
            }
        }
        if (quantity > 0) {
            for (OpsInventory inventory : GK) {
                Integer qty = inventory.getQuantity() > quantity ? quantity : (inventory.getQuantity() < 0 ? 0 : inventory.getQuantity());
                //baseInventoryService.UpdateQtyInventory(inventory.getInventoryId(), -1 * qty, InventoryTableTypeEnum.NORMAL.getType(), UserDto.ADMIN);
                quantity = quantity - qty;
                OpsInventoryAdj adj = new OpsInventoryAdj();
                adj.setDiffId(diffId);
                adj.setInventoryId(inventory.getInventoryId());
                adj.setQuantityBefore(inventory.getQuantity());
                adj.setQuantityAdj(qty);
                adj.setCreateTime(new Date());
                adj.setCreator("C12961_code");
                adjList.add(adj);
            }
        }
        /*if (quantity > 0) {
            for (OpsInventory inventory : QB) {
                Integer qty = inventory.getQuantity() > quantity ? quantity : (inventory.getQuantity() < 0 ? 0 : inventory.getQuantity());
                //baseInventoryService.UpdateQtyInventory(inventory.getInventoryId(), -1 * qty, InventoryTableTypeEnum.NORMAL.getType(), UserDto.ADMIN);
                quantity = quantity - qty;
                OpsInventoryAdj adj = new OpsInventoryAdj();
                adj.setDiffId(diffId);
                adj.setInventoryId(inventory.getInventoryId());
                adj.setQuantityBefore(inventory.getQuantity());
                adj.setQuantityAdj(qty);
                adj.setCreateTime(new Date());
                adj.setCreator("C12961_code");
                opsInventoryAdjMapper.insertSelective(adj);
            }
        }*/
        if (quantity > 0) {
            if (!TY.isEmpty()) {
                OpsInventoryAdj ty = adjList.get(0);
                OpsInventoryAdj adj = new OpsInventoryAdj();
                adj.setDiffId(diffId);
                adj.setInventoryId(ty.getInventoryId());
                adj.setQuantityBefore(ty.getQuantityBefore() - ty.getQuantityAdj());
                adj.setQuantityAdj(quantity);
                adj.setCreateTime(new Date());
                adj.setCreator("C12961_code");
                adjList.add(adj);
            } else {
                throw Exceptions.OpsException("库存数量不足，缺少库存数：" + quantity);
            }
        }
        for (OpsInventoryAdj adj : adjList) {
            opsInventoryAdjMapper.insertSelective(adj);
        }

    }

}
