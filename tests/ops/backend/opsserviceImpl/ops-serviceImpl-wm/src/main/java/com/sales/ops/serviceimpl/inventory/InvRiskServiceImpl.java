package com.sales.ops.serviceimpl.inventory;

import com.sales.ops.common.until.DateUtil;
import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.db.extdao.InventoryRiskDao;
import com.sales.ops.dto.inventory.InventoryRiskDTO;
import com.sales.ops.service.inventory.InvRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/11/4 10:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class InvRiskServiceImpl implements InvRiskService {

    @Autowired
    private InventoryRiskDao inventoryRiskDao;


    /**
     * bugid:19127 风险在库 计算
     * @throws Exception
     */
    @Override
    public void handleExe() throws Exception{
        String batchNo = DateUtil.getDateHourMinute1(DateUtil.getNow());
        // 1.查数据 数据源1
        List<InventoryRiskDTO> data1 = findData1(batchNo);
        // 2.查数据 数据源2
        List<InventoryRiskDTO> data2 = findData2(batchNo);
        // 3.去重 基于主键去重，优先保留data1中的数据
        List<InventoryRiskDTO> merged = new ArrayList<>(Stream.concat(data1.stream(), data2.stream())
                .collect(Collectors.toMap(InventoryRiskDTO::getInventoryId, // key：用 inventoryId 作为唯一键
                        ir -> ir   // value：元素本身
                        , (existing, replacement) -> existing)).values());// 冲突时保留 existing（即 data1 中的
        // 4.迁移日志
        upLogData();
        // 5.持久化数据
        insertData(merged);
    }

    // 1. update
    public void updateRistData(){
        inventoryRiskDao.updateInvRiskExpired();
    }

    /**
     * 2.查数据 数据源2
     * 待决算、待审批、待清算、延期待决算
     * @return
     */
    public List<InventoryRiskDTO> findData1(String batchNo){
        return inventoryRiskDao.findPreorderAccountDataNew(batchNo);
    }

    // 3.查数据 数据源2
    public List<InventoryRiskDTO> findData2(String batchNo){
        return inventoryRiskDao.findModelExpFreqDataNew(batchNo);
    }


    // 4.写入数据
    public void insertData(List<InventoryRiskDTO> list) throws Exception{
        // 调用公共 拆分参数方法
        Map<Integer, List<InventoryRiskDTO>> mapBarcode = SplitBatchUtils.getInsertBatchBySqlserver(list, InventoryRiskDTO.class);
        for (Map.Entry<Integer, List<InventoryRiskDTO>> entry : mapBarcode.entrySet()) {
            try {
                inventoryRiskDao.insertInvRiskBatch(entry.getValue());
            } catch (Exception ex) {
                throw new Exception("写入数据失败" + "->错误：" + ex);
            }
        }
    }


    /**
     * 数据迁移到日志
     *
     * @throws Exception
     */
    public void upLogData() throws Exception{
        // 1.查询数据
        List<InventoryRiskDTO> allRisk = inventoryRiskDao.findAllRisk();
        // 2.写入日志数据
        Map<Integer, List<InventoryRiskDTO>> mapBarcode = SplitBatchUtils.getInsertBatchBySqlserver(allRisk, InventoryRiskDTO.class);
        for (Map.Entry<Integer, List<InventoryRiskDTO>> entry : mapBarcode.entrySet()) {
            try {
                inventoryRiskDao.insertInvRiskLogBatch(entry.getValue());
            } catch (Exception ex) {
                throw new Exception("写入数据失败" + "->错误：" + ex);
            }
        }
        // 3.删除数据
        inventoryRiskDao.delAllRisk();
    }
}
