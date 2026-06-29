package com.smc.smccloud.service.impl.bincalc.creator;

import cn.hutool.json.JSONUtil;
import com.smc.smccloud.mapper.BindataBatchRepository;
import com.smc.smccloud.mapper.BindataClientWarehouseMapper;
import com.smc.smccloud.mapper.BindataRepository;
import com.smc.smccloud.mapper.binorder.BinorderDetailRepository;
import com.smc.smccloud.model.bindata.BindataDO;
import com.smc.smccloud.model.binorder.BinOrderDetailDO;
import com.smc.smccloud.service.impl.NewBinOrderCalcServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WarehouseBinProcessor {

    // insertBatchDetail 写入 25 个字段，50 条约 1250 个参数，低于 SQL Server 2100 参数上限。
    private static final int DETAIL_INSERT_BATCH_SIZE = 50;

    @Resource
    private BinorderDetailRepository binorderDetailRepository;
    @Resource
    private BindataRepository bindataRepository;
    @Resource
    private BindataBatchRepository bindataBatchRepository;
    @Resource
    private BindataClientWarehouseMapper bindataClientWarehouseMapper;


    /**
     * 如果BinOrderDetail表中没有预设数据，则插入数据，数据条数按照bindata数据来定
     * 如果有预设数据，则不插入，直接更新补充字段，一般不超过百条，可以for循环更新
     * 此方法为原存储过程的部分逻辑
     * @param calcId 计算号
     * @param calcType 仓库bin为1
     * @param warehouseCode 计算仓
     */
    public void processWarehouseBin(Long calcId, Integer calcType, String warehouseCode) {
        // Task 2-4：仓库 BIN 的 bindetail 基础数据生成入口。
        Map<String, BindataDO> bindataMap = new HashMap<>();
        //查询表中的所有仓库bin
        List<BinOrderDetailDO> warehouseBinOrderDetailList = binorderDetailRepository.findBinOrderDetailByCalcType(calcId, calcType);
        //如果表中没有预设数据，则插入数据
        if (CollectionUtils.isEmpty(warehouseBinOrderDetailList)) {
            long timer = System.currentTimeMillis();
            //查询计算仓的所有bindata型号，并创建binOrderDetail
            List<BindataDO> warehouseBindata = bindataBatchRepository.findWarehouseBindata(warehouseCode);
            log.info("bindetail生成-查询Bindata完成，calcId:{}，仓库:{}，数量:{}，用时:{}s",
                    calcId, warehouseCode, warehouseBindata.size(), elapsedSeconds(timer));
            timer = System.currentTimeMillis();
            //bindata信息写入缓存
            for (BindataDO bindataDO : warehouseBindata) {
                bindataMap.put(bindataDO.getModelNo(), bindataDO);
            }
            // Task 2：clients 只在后续持久化到 detail.client，这里提前批量查出，避免 N 次 getClientsById。
            Map<Long, List<String>> bindataIdToClients =
                    BindataClientBatchLoader.load(bindataClientWarehouseMapper, warehouseBindata);
            log.info("bindetail生成-查询clients完成，calcId:{}，bindata数量:{}，用时:{}s",
                    calcId, warehouseBindata.size(), elapsedSeconds(timer));
            timer = System.currentTimeMillis();
            //bindata信息转为DO
            List<BinOrderDetailDO> binOrderDetailDOList = NewBinOrderCalcServiceImpl.convertToBinOrderDetailList(calcId, warehouseBindata);
            List<BinOrderDetailDO> detailsToInsert = new ArrayList<>(binOrderDetailDOList.size());
            for (BinOrderDetailDO binDetail : binOrderDetailDOList) {
                //查询bindata从缓存
                BindataDO bindata = bindataMap.get(binDetail.getModelNo());
                if (bindata != null) {
                    binDetail.setFreq(bindata.getFreq());
                    binDetail.setMean(calculateMean(bindata));
                    binDetail.setSupplierCode(bindata.getSupplierCode());
                    binDetail.setModelClass(bindata.getSetLevel());
                    binDetail.setMainSupplierCode(bindata.getSupplierCode());
                    binDetail.setBinClass(bindata.getStockType().toString());
                    binDetail.setOrderType(bindata.getOrderType());
                    List<String> clients = BindataClientBatchLoader.getClients(bindataIdToClients, bindata.getId());
                    binDetail.setClient(JSONUtil.toJsonStr(clients));
                    detailsToInsert.add(binDetail);
                }
            }
            log.info("bindetail生成-转换填充detail完成，calcId:{}，detail数量:{}，用时:{}s",
                    calcId, detailsToInsert.size(), elapsedSeconds(timer));
            timer = System.currentTimeMillis();
            // Task 3：新生成的 detail 按批次写入，降低 bindetail 生成阶段的数据库往返次数。
            insertDetailsInBatch(detailsToInsert);
            log.info("bindetail生成-批量插入detail完成，calcId:{}，detail数量:{}，用时:{}s",
                    calcId, detailsToInsert.size(), elapsedSeconds(timer));
        } else {
            // Task 4：如果有预设 detail，则复用 IncludeNotBIN 批量索引补齐频率、月用量和供应商等字段。
            // IncludeNotBIN 允许取到软删除 bindata，用于非 BIN 场景补齐 freq/mean 等字段。
            // 外层 key=warehouseCode，内层 key=modelNo，value=IncludeNotBIN 语义下选中的 BindataDO。
            Map<String, Map<String, BindataDO>> bindataIncludeNotBinIndex =
                    BindataBatchLoader.load(bindataRepository, calcType, warehouseBinOrderDetailList);
            for (BinOrderDetailDO binDetail : warehouseBinOrderDetailList) {
                BindataDO bindata = BindataBatchLoader.get(bindataIncludeNotBinIndex, binDetail);
                BinOrderDetailDO update = new BinOrderDetailDO();
                if (bindata != null) {
                    update.setId(binDetail.getId());
                    //如果是BIN品，则写入bincell和qtybin
                    if(bindata.getDelFlag()==0){
                        update.setBincell(bindata.getBinCell());
                        update.setQtybin(bindata.getQtyBin());
                        update.setBinClass(bindata.getStockType().toString());
                    }
                    update.setFreq(bindata.getFreq());
                    update.setMean(calculateMean(bindata));
                    if (StringUtils.isBlank(binDetail.getSupplierCode())) {
                        update.setSupplierCode(bindata.getSupplierCode());
                    }
                    update.setModelClass(bindata.getSetLevel());
                    update.setMainSupplierCode(bindata.getSupplierCode());
                    if (StringUtils.isBlank(binDetail.getOrderType())) {
                        update.setOrderType(bindata.getOrderType());
                    }
                    //获取被集约方
                    List<String> clients = bindataRepository.getClientsById(new Long(bindata.getId()));
                    binDetail.setClient(JSONUtil.toJsonStr(clients));
                    binorderDetailRepository.updateById(update);
                }
            }
        }
        // bindetail 基础数据生成完成后，统一回写频率汇总、中央仓、调拨方式和产品信息等派生字段。
        long updateTimer = System.currentTimeMillis();
        bindataBatchRepository.updateFreq(calcId);
        bindataBatchRepository.updateMeanAll(calcId);
        bindataBatchRepository.updateBinCellAll(calcId);
        if (StringUtils.startsWith(warehouseCode, "K")) {
            bindataBatchRepository.updateLastDlvdate(calcId, warehouseCode);
        }
        bindataBatchRepository.updateCenterWarehouse(calcId);
        bindataBatchRepository.updateTransType(calcId);
        bindataBatchRepository.updateProductInfo(calcId);
        log.info("bindetail生成-后续批量update完成，calcId:{}，用时:{}s", calcId, elapsedSeconds(updateTimer));
    }




    private Double calculateMean(BindataDO bindata) {
        // 替代: case isnull(b.setFreq,1) when 0 then 1 else b.setFreq end
        return (bindata.getSetFreq() == null || bindata.getSetFreq() == 0) ? 1.0 : bindata.getSetFreq().doubleValue();
    }

    private void insertDetailsInBatch(List<BinOrderDetailDO> details) {
        if (CollectionUtils.isEmpty(details)) {
            return;
        }
        // Task 3：按 SQL Server 参数上限分片插入 detail，单批只负责持久化，不再额外补字段。
        for (int from = 0; from < details.size(); from += DETAIL_INSERT_BATCH_SIZE) {
            int to = Math.min(from + DETAIL_INSERT_BATCH_SIZE, details.size());
            binorderDetailRepository.insertBatchDetail(details.subList(from, to));
        }
    }

    private double elapsedSeconds(long startMillis) {
        return (System.currentTimeMillis() - startMillis) / 1000.0;
    }

}
