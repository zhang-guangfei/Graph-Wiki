package com.sales.ops.serviceimpl.inventory.adjustment;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsInventoryAdjMapper;
import com.sales.ops.db.dao.OpsInventoryDiffMapper;
import com.sales.ops.db.entity.OpsInventoryAdj;
import com.sales.ops.db.entity.OpsInventoryDiff;
import com.sales.ops.db.extdao.AdjustInventoryQtyDao;
import com.sales.ops.service.inventory.adjustment.AdjustInventoryQtyService;
import com.sales.ops.service.inventory.adjustment.CalInventoryQtyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2022/11/4 15:39
 */
@Slf4j
@Service
public class AdjustInventoryQtyServiceImpl implements AdjustInventoryQtyService {

    @Autowired
    private OpsInventoryDiffMapper opsInventoryDiffMapper;
    @Autowired
    private AdjustInventoryQtyDao adjustInventoryQtyDao;
    @Autowired
    private OpsInventoryAdjMapper opsInventoryAdjMapper;
    @Autowired
    private CalInventoryQtyService calInventoryQtyService;

    @Override
    public void createOpsInventoryDiff() {
        adjustInventoryQtyDao.createInventoryDiff();
    }


    @Override
    public void handleOpsInventoryDiff(Integer num) {
        if (num == null) {
            num = 1;
        }
        List<OpsInventoryDiff> diffs = adjustInventoryQtyDao.OpsInventoryDiff(num);
        for (OpsInventoryDiff diff : diffs) {
            OpsInventoryDiff update = new OpsInventoryDiff();
            update.setId(diff.getId());
            try {
                calInventoryQtyService.calInventoryQty(diff.getId(), diff.getWarehouseCode(), diff.getModelno(), -1 * diff.getQuantityDiff());
                update.setStatus(1);
            } catch (OpsException e) {
                update.setStatus(2);
                update.setMsg(e.getMessage());
            }
            update.setModifyTime(new Date());
            opsInventoryDiffMapper.updateByPrimaryKeySelective(update);
        }
    }

    @Override
    public void handleOpsInventoryAdj(Integer num) {
        List<OpsInventoryAdj> adjList = adjustInventoryQtyDao.OpsInventoryAdj(num);
        for (OpsInventoryAdj adj : adjList) {
            OpsInventoryAdj update = new OpsInventoryAdj();
            update.setId(adj.getId());
            try {
                Integer result = adjustInventoryQtyDao.adjustInventoryQty(adj.getInventoryId(), adj.getQuantityAdj());
                if (result == 1) {
                    update.setStatus(1);
                } else {
                    update.setStatus(2);
                }
            } catch (Exception e) {
                update.setStatus(2);
                update.setRemark(e.getMessage());
            }
            update.setAdjDate(new Date());
            opsInventoryAdjMapper.updateByPrimaryKeySelective(update);
        }
    }


}
