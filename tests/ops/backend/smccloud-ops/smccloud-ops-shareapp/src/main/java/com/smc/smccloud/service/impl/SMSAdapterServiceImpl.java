package com.smc.smccloud.service.impl;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.adapter.BinDataApply;
import com.smc.smccloud.model.adapter.BinDataApplyItem;
import com.smc.smccloud.model.adapter.ChinaRegionWarehouseSupplyApply;
import com.smc.smccloud.model.adapter.ChinaRegionWarehouseSupplyApplyItem;
import com.smc.smccloud.model.prestock.PreStockResultDTO;
import com.smc.smccloud.service.PreStockService;
import com.smc.smccloud.service.SMSAdapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-03-09 17:04
 * Description:
 */
@Slf4j
@Service
public class SMSAdapterServiceImpl implements SMSAdapterService {

    @Resource
    private PreStockService preStockService;

    @Override
    public ResultVo<BinDataApply> findBinApply(String no) {
        ResultVo<List<PreStockResultDTO>> listResult = preStockService.listPreStockResultByApplyNo(no);
        if (!listResult.isSuccess()) {
            return ResultVo.failure(listResult.getMessage());
        }
        BinDataApply binDataApply = new BinDataApply();
        binDataApply.setId(no);
        List<BinDataApplyItem> itemList = new ArrayList<>(listResult.getData().size());
        BinDataApplyItem item;
        for (PreStockResultDTO dto : listResult.getData()) {
            item = new BinDataApplyItem();
            item.setItemId(dto.getItemNo());
            item.setModelNo(dto.getApplyModelNo());
            item.setOrderNo(dto.getOrderNo());
            itemList.add(item);
        }
        binDataApply.setItems(itemList);
        return ResultVo.success(binDataApply);
    }

    @Override
    public ResultVo<ChinaRegionWarehouseSupplyApply> findChinaRegionWarehouse(String no) {
        ResultVo<List<PreStockResultDTO>> listResult = preStockService.listPreStockResultByApplyNo(no);
        if (!listResult.isSuccess()) {
            return ResultVo.failure(listResult.getMessage());
        }
        ChinaRegionWarehouseSupplyApply info = new ChinaRegionWarehouseSupplyApply();
        info.setId(no);
        List<ChinaRegionWarehouseSupplyApplyItem> itemList = new ArrayList<>(listResult.getData().size());
        ChinaRegionWarehouseSupplyApplyItem item;
        for (PreStockResultDTO dto : listResult.getData()) {
            item = new ChinaRegionWarehouseSupplyApplyItem();
            item.setId(no);
            item.setItemId(dto.getItemNo());
            item.setModelNo(dto.getApplyModelNo());
            item.setOrderNo(dto.getOrderNo());
            itemList.add(item);
        }
        info.setItems(itemList);
        return ResultVo.success(info);
    }
}
