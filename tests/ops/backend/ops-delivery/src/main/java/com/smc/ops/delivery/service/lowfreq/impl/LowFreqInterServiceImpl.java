package com.smc.ops.delivery.service.lowfreq.impl;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.db.entity.LowFrequencyInterceptionChecklist;
import com.sales.ops.db.entity.OpsRequestpurchaseInterceptConfig;
import com.smc.ops.delivery.mapper.LowFrequencyOpsdbDao;
import com.smc.ops.delivery.mapper.LowFrequencyReportdbDao;
import com.smc.ops.delivery.model.ModelExpFreqDto;
import com.smc.ops.delivery.service.lowfreq.LowFreqInterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/7/9 13:03
 */
@Service
@Slf4j
public class LowFreqInterServiceImpl implements LowFreqInterService {

    @Resource
    private LowFrequencyOpsdbDao lowFrequencyOpsdbDao;
    @Resource
    private LowFrequencyReportdbDao lowFrequencyReportdbDao;

    /**
     * 一、收集数据
     * 获取拦截型号
     * BUGID 14635
     * @return
     */
    @Override
    public List<String> getInterceptModelNos(){
        List<String> result = new ArrayList<>();
        //1. 查询 库存表 条件：1.有效在库大于0 ；2. 非委托在库 3. 'GK-TY','GK-PJ','GK-PPL'
        List<String> modelNos = invModelNos();
        if(CollectionUtils.isNotEmpty(modelNos)){
            //2.通过型号和StockCode='ALL' AND AvgQtyOf8 = 0 AND ModelNo ='' model_exp_freq
            result = colectMondelExpFreq(modelNos);
        }
        return result;
    }

    /**
     * 插入清单表
     */
    @Override
    public Integer insertInterceptData(List<String> modleNos) throws OpsException{
        Integer updateSize = 0;
        //1.插入清单表
        if(CollectionUtils.isNotEmpty(modleNos)){
            // 初始化数据
            List<LowFrequencyInterceptionChecklist> lowFrequencyInterceptionChecklists = initLowFreInterCheckLists(modleNos);
            // 持久化数据 写入低频拦截清单
            updateSize = saveLowFreInterCheckList(lowFrequencyInterceptionChecklists);
        }
        return updateSize;
    }

    /**
     * 更新或插入 采购拦截表
     * @param modelNos
     */
    @Override
    public Integer updateInterceptDatePo(List<String> modelNos) throws OpsException{
        Integer updateSize = 0;
        if(CollectionUtils.isNotEmpty(modelNos)) {
            // a.批量更新po拦截表为不可用
            updateAllPoInterConfigToEnable();
            // b.插入或更新
            List<String> addModelNos = new ArrayList<>();
            //1.收集批量插入数据 ，更新已有数据
            for(String modelNo : modelNos){
                // 查询采购拦截表
                OpsRequestpurchaseInterceptConfig poInterConfig = getPoInterConfig(modelNo);
                //筛选插入数据
                if(Objects.isNull(poInterConfig)){
                    addModelNos.add(modelNo);
                 //更新数据
                }else {
                    // 更新采购拦截表
                    updateSize = updateSize + updatePoIntefConfig(poInterConfig.getId(),true);
                }
            }
            //2.全量更新采购拦截表
            if(CollectionUtils.isNotEmpty(addModelNos)){
                // 初始化数据
                List<OpsRequestpurchaseInterceptConfig> opsPoIntercepts = initPoInterceptList(addModelNos);
                // 持久化数据  批量插入采购拦截表
                updateSize = updateSize + savePoInterceptConfig(opsPoIntercepts);
            }
        }
        return updateSize;
    }

    /**
     * //1. 查询 库存表 条件：1.有效在库大于0 ；2. 非委托在库 3. 'GK-TY','GK-PJ','GK-PPL'
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<String> invModelNos(){
        return lowFrequencyOpsdbDao.getInventoryModelNos();
    }

    /**
     * 2.通过型号和StockCode='ALL' AND AvgQtyOf8 = 0 AND ModelNo ='' model_exp_freq
     * 全公司近8月月均为0（判断条件：ALL订货数据和基础数据的最近8个月月平均数量同时为0）——model_exp_freq.StockCode=ALL，
     * 根据型号找到ModelType=1和ModelType=2的AvgQtyOf8均为0，找不到的情况默认按0计算
     * @param inventoryModelNos
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<String> colectMondelExpFreq(List<String> inventoryModelNos){
        List<String> interceptModelNos = new ArrayList<>();
        for(String modelNo : inventoryModelNos){
            List<ModelExpFreqDto> modelExpFreqDtos = lowFrequencyReportdbDao.getModelExpFreqDtos(modelNo);
            if(CollectionUtils.isNotEmpty(modelExpFreqDtos)){
                boolean flag = true;
                // 是否满足Avgqtyof8 全部都为0
                for(ModelExpFreqDto obj : modelExpFreqDtos){
                    if(Objects.nonNull(obj.getAvgqtyof8()) && !obj.getAvgqtyof8().equals(0)){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    // 型号带* 转换为 (.*)
                    String interModelNo = modelNo;
                    if (modelNo.contains("*")
                            && !modelNo.contains("(.*)")) {
                        interModelNo = modelNo.replace("*", "(.*)");
                    }
                    interceptModelNos.add(interModelNo);
                }
            }
        }
        return interceptModelNos;
    }


    //3.写入低频拦截清单
    @Transactional(rollbackFor = Exception.class)
    public Integer saveLowFreInterCheckList(List<LowFrequencyInterceptionChecklist> paramList) throws OpsException {
        Integer count = 0;
        Map<Integer, List<LowFrequencyInterceptionChecklist>> params = SplitBatchUtils.getInsertBatchBySqlserver(paramList, LowFrequencyInterceptionChecklist.class);
        for (Map.Entry<Integer, List<LowFrequencyInterceptionChecklist>> entry : params.entrySet()) {
            count += lowFrequencyOpsdbDao.BatchInsertLowFreqInterChecklist(entry.getValue());
        }
        return count;
    }

    /**
     * 4.0
     * 批量更细 采购拦截表
     * 条件：operator ='死库拦截'
     * 更新：失效
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateAllPoInterConfigToEnable(){
        lowFrequencyOpsdbDao.updateAllPoInterConfigToEnable();
    }

    /**
     * 4.1.1 根据型号获取 采购拦截表
     * @param modelNo
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public OpsRequestpurchaseInterceptConfig getPoInterConfig(String modelNo){
        return lowFrequencyOpsdbDao.getPoInterceptConfigList(modelNo);
    }

    /**
     * 4.1.2 更新 采购拦截表
     * @param id
     * @param enable
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer updatePoIntefConfig(Integer id, Boolean enable){
        return lowFrequencyOpsdbDao.updatePoInterceptConfig(id,enable);
    }

    /**
     * 批量插入采购拦截表
     * @param paramList
     * @return
     * @throws OpsException
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer savePoInterceptConfig(List<OpsRequestpurchaseInterceptConfig> paramList) throws OpsException {
        Integer count = 0;
        Map<Integer, List<OpsRequestpurchaseInterceptConfig>> params = SplitBatchUtils.getInsertBatchBySqlserver(paramList, OpsRequestpurchaseInterceptConfig.class);
        for (Map.Entry<Integer, List<OpsRequestpurchaseInterceptConfig>> entry : params.entrySet()) {
            count += lowFrequencyOpsdbDao.BatchInsertPoInterceptConfig(entry.getValue());
        }
        return count;
    }

    /**
     * 初始化批量插入数据
     * @param modelNos
     * @return
     */
    public List<LowFrequencyInterceptionChecklist> initLowFreInterCheckLists(List<String> modelNos){
        List<LowFrequencyInterceptionChecklist> results= new ArrayList<>();
        if(CollectionUtils.isNotEmpty(modelNos)){
            String batch = DateUtil.dateToDateString(DateUtil.getNow());
            for(String modelno : modelNos){
                LowFrequencyInterceptionChecklist obj = new LowFrequencyInterceptionChecklist();
                obj.setTypes("死库拦截");
                obj.setBatch(batch);
                obj.setModelno(modelno);
                obj.setCreator("job");
                obj.setUpdatetime(DateUtil.getNow());
                results.add(obj);
            }
        }
        return results;
    }

    /**
     * 初始化批量插入数据
     * @param modelNos
     * @return
     */
    public List<OpsRequestpurchaseInterceptConfig> initPoInterceptList(List<String> modelNos){
        List<OpsRequestpurchaseInterceptConfig> results= new ArrayList<>();
        if(CollectionUtils.isNotEmpty(modelNos)){
            for(String modelno : modelNos){
                OpsRequestpurchaseInterceptConfig obj = new OpsRequestpurchaseInterceptConfig();
                obj.setTypeid("0");
                obj.setRulekey(modelno);
                obj.setReason("近8频为0，订单拦截，在库企画调库后，订单还原");
                obj.setRemark("近8频为0，订单拦截，在库企画调库后，订单还原");
                obj.setEnable(true);
                obj.setDefaultaction("4");
                obj.setOperator("死库拦截");
                obj.setUpdatetime(DateUtil.getNow());
                results.add(obj);
            }
        }
        return results;
    }
}
