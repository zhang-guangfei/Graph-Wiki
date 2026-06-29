package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.smc.smccloud.mapper.csstock.CsImpdataMapper;
import com.smc.smccloud.model.csstock.CsImpdataDO;
import com.smc.smccloud.model.csstock.CsImpdataVO;
import com.smc.smccloud.service.CSImportDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-07-30 17:18
 * Description:
 */
@Slf4j
@Service
public class CSImportDataServiceImpl implements CSImportDataService {

    @Resource
    private CsImpdataMapper csImpdataMapper;

    @Override
    public boolean saveCSImportData(CsImpdataDO csImpdataDO) {
        int result;
        if (csImpdataDO.getId() == null || csImpdataDO.getId() == 0) {
            result = csImpdataMapper.insert(csImpdataDO);
        } else {
            result = csImpdataMapper.updateById(csImpdataDO);
        }
        return result > 0;
    }

    @Override
    public List<CsImpdataVO> listCsImpdata() {
        return csImpdataMapper.listImpData();
    }

    @Override
    public boolean checkCSInvoice(String warehouseCode, String invoiceNo) {
        LambdaQueryWrapper<CsImpdataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CsImpdataDO::getInvoiceNo, invoiceNo)
                .eq(CsImpdataDO::getWarehouseCode, warehouseCode);
        return csImpdataMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    @Transactional
    public void calcExpQtyAndLeftQty(List<CsImpdataDO> dataList, int stockQty) {
        LambdaUpdateWrapper<CsImpdataDO> updateWrapper = new LambdaUpdateWrapper<>();
        List<List<Integer>> idsList = new ArrayList<>();
        List<Integer> ids = null;
        Date updateTime = new Date();

        for (CsImpdataDO csImpData : dataList) {
            if (stockQty == 0) {
                //csImpData.setLeftQty(0);
                //csImpData.setExpQty(csImpData.getQuantity());
                if (ids == null || ids.size() == 2000) {
                    ids = new ArrayList<>(2000);
                    idsList.add(ids);
                }
                ids.add(csImpData.getId());
            } else {
                // 计算剩余数量
                if (stockQty > csImpData.getQuantity()) {
                    csImpData.setLeftQty(csImpData.getQuantity());
                } else {
                    csImpData.setLeftQty(stockQty);
                }
                // 计算出库数量
                csImpData.setExpQty(csImpData.getQuantity() - csImpData.getLeftQty());
                // 计算在库数量
                stockQty -= csImpData.getLeftQty();

                // 更新 出库数量&剩余数量
                updateWrapper.clear();
                updateWrapper.set(CsImpdataDO::getExpQty, csImpData.getExpQty())
                        .set(CsImpdataDO::getLeftQty, csImpData.getLeftQty())
                        .set(CsImpdataDO::getUpdateTime, updateTime);
                updateWrapper.eq(CsImpdataDO::getId, csImpData.getId());
                csImpdataMapper.update(null, updateWrapper);
            }
        }

        if (CollectionUtils.isNotEmpty(idsList)) {
            for (List<Integer> idList : idsList) {
                // 更新 出库数量&剩余数量
                updateWrapper.clear();
                updateWrapper.setSql("exp_qty = quantity")
                        .set(CsImpdataDO::getLeftQty, 0)
                        .set(CsImpdataDO::getUpdateTime, updateTime);
                updateWrapper.in(CsImpdataDO::getId, idList);
                csImpdataMapper.update(null, updateWrapper);
            }
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<CsImpdataDO> listCSImportDataByWrapper(Wrapper<CsImpdataDO> queryWrapper) {
        return csImpdataMapper.selectList(queryWrapper);
    }

    @Override
    public boolean updateCSImportDataByWrapper(CsImpdataDO entity, Wrapper<CsImpdataDO> updateWrapper) {
        return csImpdataMapper.update(entity, updateWrapper) > 0;
    }

}
