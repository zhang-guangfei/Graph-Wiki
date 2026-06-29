package com.smc.smccloud.service.impl.bincalc.calculator;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.assembly.StockAssemblyDetailView;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.enums.OrderTypeEnum;
import com.sales.ops.enums.SourceTypeEnum;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.smc.smccloud.mapper.BinOrderDetailOrdingInfoMapper;
import com.smc.smccloud.model.bindata.BindataDO;
import com.smc.smccloud.model.binorder.*;
import com.smc.smccloud.model.trans.TransOrderDO;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 此类为补库策略的判断和补库数量的计算逻辑
 * 计算步骤概览：https://smc.sharepoint.cn/:x:/r/sites/ITDept/Shared%20Documents/17%E5%90%84%E7%BB%84%E6%96%87%E4%BB%B6%E5%A4%B9/1%E8%90%A5%E4%B8%9A%E7%BB%84/02%20OPS/02%20%E8%AF%A6%E7%BB%86%E8%AE%BE%E8%AE%A1/BIN%E8%AE%A1%E7%AE%97%E5%BC%80%E5%8F%91%E8%AF%A6%E7%BB%86%E8%AE%BE%E8%AE%A1.xlsx?d=wad992d214076403eb4cb85003ef3ff7d&csf=1&web=1&e=QcdFzG
 * 策略选择器：  https://smc.sharepoint.cn/:x:/r/sites/ITDept/Shared%20Documents/17%E5%90%84%E7%BB%84%E6%96%87%E4%BB%B6%E5%A4%B9/1%E8%90%A5%E4%B8%9A%E7%BB%84/02%20OPS/02%20%E8%AF%A6%E7%BB%86%E8%AE%BE%E8%AE%A1/BIN%E8%AE%A1%E7%AE%97%E5%BC%80%E5%8F%91%E8%AF%A6%E7%BB%86%E8%AE%BE%E8%AE%A1.xlsx?d=wad992d214076403eb4cb85003ef3ff7d&csf=1&web=1&e=DEzO8a
 * 补库方案A流程：https://smc.sharepoint.cn/:x:/r/sites/ITDept/Shared%20Documents/17%E5%90%84%E7%BB%84%E6%96%87%E4%BB%B6%E5%A4%B9/1%E8%90%A5%E4%B8%9A%E7%BB%84/02%20OPS/02%20%E8%AF%A6%E7%BB%86%E8%AE%BE%E8%AE%A1/BIN%E8%AE%A1%E7%AE%97%E5%BC%80%E5%8F%91%E8%AF%A6%E7%BB%86%E8%AE%BE%E8%AE%A1.xlsx?d=wad992d214076403eb4cb85003ef3ff7d&csf=1&web=1&e=6tSm1O
 * todo优化原则：
 * 几个分支的逻辑要条例清晰，每条纵向逻辑要上下通畅
 * 考虑流程的统一性，削减分散化的分支逻辑，期望尽量用统一的模板方法设计模式来处理不同分支，尽管有些分支短有些分支长
 * **考虑未来客户bin的修改空间
 * **考虑程序计算效率，考虑缓存的加载和读取，某些只读字段的批量查询，考虑库存的缓存加载和读取机制
 * **考虑异步写入任务的可能性
 * 考虑横向分层，分为查询层和决策层和持久化层
 * 考虑不同策略的封装和策略选择器的灵活变动
 * 考虑各个类的分层和解耦
 */
@Slf4j
@Service
public class ReplenishmentCalculator {

    @Autowired
    private BinOrderDetailOrdingInfoMapper binOrderDetailOrdingInfoMapper;

    /**
     * 补库策略选择器
     * 根据参数补库方式是否预设和预设值、bin品，选择补库策略ABCD
     */
    public BinOrderDetailDTO replenishmentStrategySelector(BinOrderDetailDO detail, CacheComponent cache) throws OpsException {
        // Task 7：按缓存索引执行单条 detail 的补库策略计算。
        //0.创建实体
        BinOrderDetailDTO dto = createBinOrderDetailDTO(detail, cache);
        //如果没有预设补库方式
        if (dto.getReplenishmentMethod() == null) {
            //如果仓库没有补库能力，或暂停补库
            if (dto.getPreStockFlag() != 1 || dto.getRecommMonths() == -1) {
                //方案C,不采购不调拨
                calculateBinStrategyC(dto);
            } else {
                //查询库存信息
                loadInventoryInfo(dto);
                //6.计算差异数量和需补库数量
                calculateBinDiffQty(dto);
                //7.补库方案A，以BIN为单位，计算采购和调拨数量
                calculateBinStrategyA(dto);
            }
        }
        // 如果预设了补库方式和数量
        else {
            //如果预设补库方式是采购,则全采不调
            if (dto.getReplenishmentMethod() == 0) {
                //方案D，按预设数量全采购，不调拨
                calculateBinStrategyD(dto);
            }
            //如果补库方式不是采购，则计算有效库存和过剩数量
            else {
                //查询库存信息
                loadInventoryInfo(dto);
                //6.计算差异数量和需补库数量
                calculateBinDiffQty(dto);
                //如果是BIN品
                if (dto.getQtybin() != null) {
                    //7.补库方案A，以BIN为单位，计算采购和调拨
                    calculateBinStrategyA(dto);
                } else {
                    //7.补库方案B,尽可能调拨所有的过剩数量，然后再去采购
                    calculateBinStrategyB(dto);
                }
            }
        }
        //8.最终补库数量和可用月数
        calculateConfirmQty(dto);
        return dto;
    }

    //加载数据 文档：计算步骤概览 https://smc.sharepoint.cn/:x:/r/sites/ITDept/Shared%20Documents/17%E5%90%84%E7%BB%84%E6%96%87%E4%BB%B6%E5%A4%B9/1%E8%90%A5%E4%B8%9A%E7%BB%84/02%20OPS/02%20%E8%AF%A6%E7%BB%86%E8%AE%BE%E8%AE%A1/BIN%E8%AE%A1%E7%AE%97%E5%BC%80%E5%8F%91%E8%AF%A6%E7%BB%86%E8%AE%BE%E8%AE%A1.xlsx?d=wad992d214076403eb4cb85003ef3ff7d&csf=1&web=1&e=QcdFzG
    private void loadInventoryInfo(BinOrderDetailDTO dto) throws OpsException {
        // Task 7：为当前 detail 汇总仓库范围、有效库存、过剩量、调拨顺序和可用月数。
        //1.计算仓库范围
        calculateWarehouseRange(dto);
        //2.计算有效库存数量
        calculateAvailableQty(dto);
        // 计算全公司库存
        calculateStockQtyAll(dto);
        //3.所有物流中心的过剩数量
        calculateAllExcessQty(dto);
        //4.调拨顺序
        calculateTransWarehouseSequence(dto);
        // 他仓过剩数量
        calculateOtherExcessQty(dto);
        //5.可用月数
        calculateMonths(dto);
    }

    private void calculateBinStrategyD(BinOrderDetailDTO dto) {
        dto.setPoQty(dto.getOrderQty());
        dto.setTransQty(0);
    }


    private void calculateConfirmQty(BinOrderDetailDTO dto) {
        dto.setConfirmQty(dto.getPoQty() + dto.getTransQty());
        if (dto.getAvailbleQty() != null) {
            //备库后可用月数 months 四舍五入，保留2位小数
            dto.setMonths(divide(dto.getConfirmQty() + dto.getAvailbleQty(), dto.getMean()));
        }
    }


    //不采购不调拨
    private void calculateBinStrategyC(BinOrderDetailDTO dto) {
        dto.setTransQty(0);
        dto.setPoQty(0);
    }

    //全调拨全采购模式
    private void calculateBinStrategyB(BinOrderDetailDTO dto) {
        Map<String, Integer> transMap = dto.getTransQtyMap();
        Integer needQty = dto.getNeedQty();
        Map<String, Integer> excessQtyMap = dto.getExcessQtyMap();
        //调拨仓顺序
        List<String> transWarehouseSequence = dto.getTransWarehouseSequence();
        //按顺序遍历调拨仓的过剩数量
        for (String warehouse : transWarehouseSequence) {
            if (needQty <= 0) {
                break;
            }
            Integer excessQty = excessQtyMap.get(warehouse);
            int transQty = 0;
            transQty = Math.min(excessQty, needQty);
            //该仓有可调拨的数量
            if (transQty > 0) {
                transMap.put(warehouse, transQty);
            }
            needQty -= transQty;
        }
        int transQtySum = transMap.values().stream().mapToInt(Integer::intValue).sum();
        dto.setTransQty(transQtySum);
        //采购等于调拨不够的数量
        int poQty = needQty;
        dto.setPoQty(poQty);
    }

    //计算：采购数量和调拨数量（按BIN取整）
    //思路图 https://smc.sharepoint.cn/:x:/r/sites/ITDept/Shared%20Documents/17%E5%90%84%E7%BB%84%E6%96%87%E4%BB%B6%E5%A4%B9/1%E8%90%A5%E4%B8%9A%E7%BB%84/02%20OPS/02%20%E8%AF%A6%E7%BB%86%E8%AE%BE%E8%AE%A1/BIN%E8%AE%A1%E7%AE%97%E5%BC%80%E5%8F%91%E8%AF%A6%E7%BB%86%E8%AE%BE%E8%AE%A1.xlsx?d=wad992d214076403eb4cb85003ef3ff7d&csf=1&web=1&e=6tSm1O
    private void calculateBinStrategyA(BinOrderDetailDTO dto) throws OpsException {
        //总可用月数<=最大可用月数
        if (dto.getStockMonthsAll().compareTo(dto.getMaxStockAllMonths()) <= 0) {
            purchaseOnly(dto);
            dto.setTransQty(0);
        }
        //总可用月数>最大控制月数
        else if (dto.getStockMonthsAll().compareTo(dto.getMaxControllMonths()) > 0) {
            // 只采购
            if (dto.getDirectpurchase() == 1) {
                dto.setPoQty(0);
                dto.setTransQty(0);
            }else{
                transOnly(dto);
                dto.setPoQty(0);
            }
        }
        //总可用月数<=最大控制月数且 总可用月数>最大可用月数
        else {
            // 只采购
            if (dto.getDirectpurchase() == 1) {
                purchaseOnly(dto);
                dto.setTransQty(0);
            }
            // 只调拨
            else if (dto.getDirectpurchase() == 2) {
                transOnly(dto);
                dto.setPoQty(0);
            }
            // 既采购又调拨
            else if (dto.getDirectpurchase() == 0) {
                transOnly(dto);
                purchaseOnly(dto);
            } else {
                throw Exceptions.OpsException("直采标识不正确");
            }
        }
    }

    //计算poqty=[(bin_diff_qty-sumExcessQty)/qtyBin]*qtyBin（[]中向下圆整，但[]中的值大于等于0.5且小于等于1且bincell=1 ,有效库存不够0.5*qtybin时，补1个bin）
    private void purchaseOnly(BinOrderDetailDTO dto) {
        //需补库数量-他仓过剩数量>0
        int needQty = Math.max(dto.getBinDiffQty() - dto.getOtherExcessQty(), 0);
        //要补的bin数,向下圆整，取int
        int poBinCell = needQty / dto.getQtybin();
        int finalPoQty = poBinCell * dto.getQtybin();
        //额定只要一个bin且在库不足0.5bin时，直接采购一整个bin
        if (dto.getBincell() == 1 && dto.getAvailbleQty() < 0.5 * dto.getQtybin() && needQty != 0 && (double) needQty / dto.getQtybin() >= 0.5 && (double) needQty / dto.getQtybin() < 1) {
            finalPoQty = dto.getQtybin();
        }
        dto.setPoQty(finalPoQty);
    }

    // 计算调拨数量
    private void transOnly(BinOrderDetailDTO dto) {
        //《Warehouse,TransQty》要计算的他仓调拨数量transMap
        Map<String, Integer> transMap = dto.getTransQtyMap();
        Integer needQty = dto.getNeedQty();
        Map<String, Integer> excessQtyMap = dto.getExcessQtyMap();
        //调拨仓顺序
        List<String> transWarehouseSequence = dto.getTransWarehouseSequence();
        //按顺序遍历调拨仓的过剩数量
        for (String warehouse : transWarehouseSequence) {
            if (needQty <= 0) {
                break;
            }
            Integer excessQty = excessQtyMap.get(warehouse);
            if (excessQty == 0) {
                continue;
            }
            int transQty = 0;
            if (needQty > excessQty) {
                int confirmTransQty = excessQty / dto.getQtybin() * dto.getQtybin();
                transQty += confirmTransQty;
                needQty -= excessQty;
            } else {
                int confirmTransQty = needQty / dto.getQtybin() * dto.getQtybin();
                transQty += confirmTransQty;
                //要一个bin时，且该仓的可用库存小于0.5BIN时直接调拨一个BIN
                if (dto.getBincell() == 1 && dto.getAvailbleQty() < 0.5 * dto.getQtybin() && excessQty > dto.getQtybin() && (double) needQty / dto.getQtybin() >= 0.5 && (double) needQty / dto.getQtybin() <= 1) {
                    transQty = dto.getQtybin();
                }
                needQty -= excessQty;
            }
            //该仓有可调拨的数量
            if (transQty > 0) {
                transMap.put(warehouse, transQty);
            }
        }
        int transQty = transMap.values().stream().mapToInt(Integer::intValue).sum();
        dto.setTransQty(transQty);
    }


    //他仓过剩数量
    private void calculateOtherExcessQty(BinOrderDetailDTO dto) {
        int otherExcessQty = 0;
        for (Map.Entry<String, Integer> entry : dto.getExcessQtyMap().entrySet()) {
            String warehouse = entry.getKey();
            Integer excessQty = entry.getValue();
            if (dto.getWarehouseCode().equals(warehouse)) {
                continue;
            }
            otherExcessQty += excessQty;
        }
        dto.setOtherExcessQty(otherExcessQty);
    }

    //计算调拨顺序，请重点测
    private void calculateTransWarehouseSequence(BinOrderDetailDTO dto) throws OpsException {
        List<String> transWarehouseSequence = new ArrayList<>();
        String calcWarehouse = dto.getWarehouseCode();
        String masterWarehouse = null;
        String centerWarehouse = null;
        //计算仓为物流中心
        if (dto.getIsMasterWarehouse()) {
            masterWarehouse = calcWarehouse;
            //先查询原调拨仓库顺序
            List<String> tempTransWarehouseSequence = dto.getCache().getTransSequenceDict().get(masterWarehouse);
            transWarehouseSequence = new ArrayList<>(tempTransWarehouseSequence);
            //获取计算仓
            if (dto.getCenterFlag() == 1) {
                // 使用原调拨仓库顺序
                centerWarehouse = calcWarehouse;
            } else {
                centerWarehouse = dto.getCenterWarehouse();
                //找中央仓，不一定每个型号都有中央仓，所以可以缺省
                List<BindataDO> bindataDOList = dto.getCache().getBindataDOMap().get(dto.getModelNo());
                if (CollectionUtils.isNotEmpty(bindataDOList)) {
                    for (BindataDO bindataDO : bindataDOList) {
                        if (bindataDO.getCentreFlag() == 1) {
                            centerWarehouse = bindataDO.getWarehouseCode();
                        }
                    }
                }
            }
            if (StringUtils.equals(calcWarehouse, centerWarehouse)) {
                //调拨仓顺序:其他，不要计算仓
            } else {
                addTop(centerWarehouse, transWarehouseSequence);
            }
        }
        //分库
        else {
            // 分库所属物流中心
            //masterWarehouse = bindataRepository.getParentCode(calcWarehouse);
            List<WarehouseVO> subWarehouseVOList = dto.getCache().getWarehouseInfoMap().get(WarehouseTypeEnum.FDC.getHouseTypeCode());
            // TODO Task 7E：Task 6 已建立仓库索引后，这里可改为按仓库编码 Map 查询，避免 stream 查找分库所属主仓。
            WarehouseVO subWarehouse = subWarehouseVOList.stream().filter(warehouseVO -> warehouseVO.getWarehouseCode().equals(calcWarehouse)).findFirst().orElse(null);
            if (subWarehouse != null) {
                masterWarehouse = subWarehouse.getParentCode();
            }
            if (StringUtils.isBlank(masterWarehouse)) {
                throw Exceptions.OpsException("没有找到分库所属物流中心:" + calcWarehouse);
            }
            List<String> tempTransWarehouseSequence = dto.getCache().getTransSequenceDict().get(masterWarehouse);
            transWarehouseSequence = new ArrayList<>(tempTransWarehouseSequence);
            //查询该型号中央仓
            centerWarehouse = dto.getCenterWarehouse();
            List<String> client = dto.getClientWarehouse();
            //if (StringUtils.isBlank(centerWarehouse)) {
            //    throw Exceptions.OpsException("没有找到物流中心:" + dto.getModelNo());
            //}
            //计算仓所属上级物流中心是中央仓或被集约方
            if (masterWarehouse.equals(centerWarehouse) || client.contains(calcWarehouse)) {
                // 调拨顺序：中央仓+上级物流中心+其他仓，不要计算仓
                addTop(masterWarehouse, transWarehouseSequence);
                //将中心仓放最前边
                addTop(centerWarehouse, transWarehouseSequence);
            } else {
                // 调拨顺序：上级物流中心+中央仓+其他仓，不要计算仓
                //将中央仓放最前边
                addTop(centerWarehouse, transWarehouseSequence);
                //将上级物流中心放最前边
                addTop(masterWarehouse, transWarehouseSequence);
            }
        }
        dto.setTransWarehouseSequence(transWarehouseSequence);
    }


    private static void addTop(String masterWarehouse, List<String> warehouseSequence) {
        //将上级物流中心放最前边
        if (StringUtils.isNotBlank(masterWarehouse)) {
            warehouseSequence.remove(masterWarehouse);
            warehouseSequence.add(0, masterWarehouse);
        }
    }


    //计算所有物流中心的过剩数量
    private void calculateAllExcessQty(BinOrderDetailDTO dto) {
        //查询所有物流中心
        List<String> masterWarehouses = dto.getCache().getMasterWarehouses();
        for (String masterWarehouseCode : masterWarehouses) {
            // 计算物流中心的过剩数量
            calculateMasterExcessQty(dto, masterWarehouseCode);
        }
        dto.setExcessQty(JSONUtil.toJsonStr(dto.getExcessQtyMap()));
    }

    //物流中心的过剩数量
    private void calculateMasterExcessQty(BinOrderDetailDTO dto, String masterWarehouseCode) {
        // Task 5、Task 7A：优先复用缓存中的主仓有效库存和 AvgQtyOf8，避免逐条数据库查询。
        // 查询物流中心的通用有效在库
        //Integer invQty = binCalcInventoryMapper.selectNormalAvailableInventory(dto.getModelNo(), Collections.singletonList(masterWarehouseCode));
        Integer invQty = dto.getCache().getInventoryQuantity(dto.getModelNo(), masterWarehouseCode);
        //BindataDO masterWarehouseBindata = bindataRepository.findBindata2(dto.getCalcType(), dto.getModelNo(), masterWarehouseCode);
        BindataDO masterWarehouseBindata = null;
        List<BindataDO> bindataDOList = dto.getCache().getBindataDOMap().get(dto.getModelNo());
        if (CollectionUtils.isNotEmpty(bindataDOList)) {
            for (BindataDO bindataDO : bindataDOList) {
                if (bindataDO.getWarehouseCode().equals(masterWarehouseCode)) {
                    masterWarehouseBindata = bindataDO;
                }
            }
        }
        int masterExcessQty = 0;
        //有值且未软删则为bin品,和bin数量无关
        if (masterWarehouseBindata != null) {
            //计算仓为分库
            if (!dto.getIsMasterWarehouse()) {
                // bin品中央仓：有效在库-设定平均
                masterExcessQty = invQty - masterWarehouseBindata.getSetFreq();
            }
            //计算仓为物流中心
            else {
                // bin品中央仓：有效在库-安全在库量
                if (masterWarehouseBindata.getCentreFlag() == 1) {
                    masterExcessQty = invQty - masterWarehouseBindata.getSafeQty();
                }
                // bin品非中央仓：有效在库-(BIN数+1)*BIN数量
                else {
                    masterExcessQty = invQty - (masterWarehouseBindata.getBinCell() + 1) * masterWarehouseBindata.getQtyBin();
                }
            }
        }
        //非bin品，包含分库和物流中心
        else {
            int avgQty8 = dto.getCache().getModelExpFreqAvgQty8(dto.getModelNo(), masterWarehouseCode);
            // 中央仓或非中央仓
            // 计算仓是分库：有效在库-月均
            if (!dto.getIsMasterWarehouse()) {
                masterExcessQty = invQty - avgQty8;
            }
            // 计算仓是物流中心：有效在库- 2*月均
            else {
                masterExcessQty = invQty - 2 * avgQty8;
            }
        }
        //最小为0
        masterExcessQty = (Math.max(masterExcessQty, 0));
        dto.getExcessQtyMap().put(masterWarehouseCode, masterExcessQty);
    }

    //0.创建实体
    private BinOrderDetailDTO createBinOrderDetailDTO(BinOrderDetailDO detail, CacheComponent cache) throws OpsException {
        BinOrderDetailDTO dto = new BinOrderDetailDTO();
        BeanUtil.copyProperties(detail, dto);
        dto.initQty();
        dto.setCache(cache);
        WarehouseVO warehouse = cache.getWarehouseByCode(detail.getWarehouseCode());
        if (warehouse != null) {
            //有补库能力
            Integer preStockFlag = Optional.ofNullable(warehouse.getPrestockFlag()).orElse(0);
            dto.setPreStockFlag(preStockFlag);
        } else {
            throw Exceptions.OpsException("获取仓库信息失败:" + warehouse);
        }
        //计算仓类型是否为物流中心
        setIsMasterWarehouse(dto, cache);
        //被集约方
        String client = detail.getClient();
        if (StringUtils.isNotBlank(client) || JSONUtil.isJson(client)) {
            List<String> clients = JSONUtil.toList(client, String.class);
            dto.getClientWarehouse().addAll(clients);
        }
        if (dto.getMean() == null || dto.getMean() == 0) {
            dto.setMean(1.0);
        }
        if (dto.getMeanAll() == null || dto.getMeanAll() == 0) {
            dto.setMeanAll(1.0);
        }
        //只有型号为非bin品会用到，而非bin品的binOrderdetail只来自于导入，其freq要么是导入，要么是来自于model_exp_freq.AvgQtyOf8
        dto.setAvgQty8(dto.getFreq());
        dto.setSupplierCode(detail.getSupplierCode());
        if (StringUtils.isBlank(dto.getSupplierCode())) {
            dto.setSupplierCode(detail.getMainSupplierCode());
        }
        return dto;
    }

    //计算额定数量
    private void calculateRatedQty(BinOrderDetailDTO dto) {
        dto.setRatedQty(dto.getQtybin() * dto.getBincell());
    }

    //计算仓库范围
    private void calculateWarehouseRange(BinOrderDetailDTO dto) {
        // 如果计算仓是物流中心
        List<String> warehouseRange = new ArrayList<>();
        if (dto.getIsMasterWarehouse()) {
            //如果是中央仓
            if (dto.getCenterFlag() == 1) {
                // 计算仓+计算仓包含分库
                warehouseRange.add(dto.getWarehouseCode());
                List<String> warehouse = dto.getCache().getSubWarehouse(dto.getWarehouseCode());
                warehouseRange.addAll(warehouse);
                // 被集约方+被集约方包含分库
                for (String warehouseCode : dto.getClientWarehouse()) {
                    warehouseRange.add(warehouseCode);
                    List<String> clientWarehouse = dto.getCache().getSubWarehouse(warehouseCode);
                    warehouseRange.addAll(clientWarehouse);
                }
            }
            // 如果不是中央仓
            else {
                warehouseRange.add(dto.getWarehouseCode());
                // 计算仓+计算仓包含分库
                List<String> warehouse = dto.getCache().getSubWarehouse(dto.getWarehouseCode());
                warehouseRange.addAll(warehouse);
            }
        }
        // 如果计算仓不是物流中心
        else {
            // 计算仓
            warehouseRange.add(dto.getWarehouseCode());
        }
        // 去重并保留原仓库顺序；Set 专用于后续 contains 判断。
        Set<String> warehouseRangeSet = new LinkedHashSet<>(warehouseRange);
        dto.setWarehouseRange(new ArrayList<>(warehouseRangeSet));
        dto.setWarehouseRangeSet(warehouseRangeSet);
    }

    private boolean setIsMasterWarehouse(BinOrderDetailDTO dto, CacheComponent cache) {
        dto.setIsMasterWarehouse(cache.isMasterWarehouse(dto.getWarehouseCode()));
        return dto.getIsMasterWarehouse();
    }

    //查询仓库补库能力

    //计算有效库存数量
    private void calculateAvailableQty(BinOrderDetailDTO dto) {
        CacheComponent cache = dto.getCache();
        // 有效在库:仓库范围内的当前仓库的通用 (库存数量-预约数量）
        //Integer normalAvailableInventory = binCalcInventoryMapper.selectNormalAvailableInventory(dto.getModelNo(), dto.getWarehouseRange());
        Integer normalAvailableInventory = cache.getInventoryQuantity(dto.getWarehouseRange(), dto.getModelNo());
        dto.setStockQty(normalAvailableInventory);
        //订货中数量,和订货中清单
        calcOrdingQty(dto, cache);
        //被预约数量，和被预约清单
        calcPreQty(dto, cache);
        //有效在途数量：订货中数量-被预约数量
        Integer moveAvailableQty = dto.getOrdingQty() - dto.getPreQty();
        dto.setMoveQty(moveAvailableQty);
        //有效库存数量:有效在库+有效在途
        dto.setAvailbleQty(normalAvailableInventory + moveAvailableQty);
    }

    private void calcPreQty(BinOrderDetailDTO dto, CacheComponent cache) {
        //预约数量：被客户订单预约的Bin补库采购单、被先行在库订单预约的Bin补库采购单
        List<BinOrderDetailPreInfoDO> list = new ArrayList<>();
        // 补库采购单,包括未接单和已接单未入库
        List<OpsPurchaseorder> binPurchaseOrders = new ArrayList<>();
        for (OpsPurchaseorder po : cache.getPurchaseOrderList(dto.getWarehouseRange(), dto.getModelNo())) {
            if (OrderTypeEnum.BIN.equals(po.getOrdtype())) {
                binPurchaseOrders.add(po);
            }
        }
        Set<String> poNoSet = new HashSet<>();
        for (OpsPurchaseorder po : binPurchaseOrders) {
            poNoSet.add(OrderNoInfo.getFromPurchase(po).getFullNo());
        }
        //被客户订单预约的采购单（用不用仓库都可以）
        List<OrderStatusView> customerOrderPrepare = new ArrayList<>();
        Set<String> customerAssociateNoSet = cache.getCustomerAssociateNoSet(dto.getModelNo());
        boolean hasCustomerPrepare = false;
        for (String poNo : poNoSet) {
            if (customerAssociateNoSet.contains(poNo)) {
                hasCustomerPrepare = true;
                break;
            }
        }
        if (hasCustomerPrepare) {
            List<OrderStatusView> customerOrders = cache.getCustomerOrderAssociateInfoMap().get(dto.getModelNo());
            for (OrderStatusView order : customerOrders) {
                if (poNoSet.contains(order.getAssociateNo())) {
                    customerOrderPrepare.add(order);
                }
            }
        }
        //被先行在库订单预约的采购单,1-待处理; 2-已处理; 4-取消处理;
        List<PreStockResultDO> preStockOrderPrepare = new ArrayList<>();
        for (PreStockResultDO order : cache.getPreStockOrderInfoMap().get(dto.getModelNo())) {
            if (dto.getWarehouseRangeSet().contains(order.getOrderStock())
                    && poNoSet.contains(order.getPrepareOrder())
                    && !"4".equals(order.getOptStatus())) {
                preStockOrderPrepare.add(order);
            }
        }

        for (OrderStatusView orderStatusView : customerOrderPrepare) {
            BinOrderDetailPreInfoDO binPurchasePreInfo = createBinPurchasePreInfo(dto, orderStatusView);
            list.add(binPurchasePreInfo);
        }
        for (PreStockResultDO preStockResultDO : preStockOrderPrepare) {
            BinOrderDetailPreInfoDO binPurchasePreInfo = createBinPurchasePreInfo(dto, preStockResultDO, cache);
            list.add(binPurchasePreInfo);
        }
        dto.setPreInfoDOList(list);
        int preQty = 0;
        for (BinOrderDetailPreInfoDO preInfo : list) {
            preQty += preInfo.getQuantity();
        }
        dto.setPreQty(preQty);
    }

    private BinOrderDetailPreInfoDO createBinPurchasePreInfo(BinOrderDetailDTO dto, OrderStatusView order) {
        BinOrderDetailPreInfoDO pre = new BinOrderDetailPreInfoDO();
        pre.setCalcId(dto.getCalcId());
        pre.setBinDetailId(dto.getId());
        pre.setOrderType(SourceTypeEnum.PURCHASE.getType());
        pre.setPoOrderNo(order.getAssociateNo());
        pre.setOrderNo(order.getOrderId() + "-" + order.getOrderItem());
        pre.setModelno(order.getModelno());
        pre.setQuantity(order.getQty());
        return pre;
    }


    private BinOrderDetailPreInfoDO createBinPurchasePreInfo(BinOrderDetailDTO dto, PreStockResultDO order, CacheComponent cache) {
        // Task 9：优先使用批量预查或 lazy cache 的 TKCK 未出库数量，避免同一 orderFno 重复查库。
        String orderNo = order.getOrderNo();
        Integer quantity = StringUtils.isBlank(orderNo) ? null : cache.getTkckNotOutQty(orderNo);
        if (quantity == null) {
            quantity = selectTkckNotOutQty(orderNo);
            cache.putTkckNotOutQty(orderNo, quantity);
        }
        BinOrderDetailPreInfoDO pre = new BinOrderDetailPreInfoDO();
        pre.setCalcId(dto.getCalcId());
        pre.setBinDetailId(dto.getId());
        pre.setOrderType(SourceTypeEnum.PURCHASE.getType());
        pre.setPoOrderNo(order.getPrepareOrder());
        pre.setOrderNo(order.getOrderNo());
        pre.setModelno(order.getModelNo());
        pre.setQuantity(quantity);
        return pre;
    }

    private Integer selectTkckNotOutQty(String orderFno) {
        String[] orderKey = parseTkckOrderFno(orderFno);
        if (orderKey == null) {
            return binOrderDetailOrdingInfoMapper.selectTKCKNotOutQty(orderFno);
        }
        return binOrderDetailOrdingInfoMapper.selectTKCKNotOutQtyByOrderKey(orderKey[0], orderKey[1]);
    }

    private String[] parseTkckOrderFno(String orderFno) {
        if (StringUtils.isBlank(orderFno)) {
            return null;
        }
        // 用最后一个横线拆分，和缓存批量预查保持一致；无法拆分时保留旧 SQL 兜底。
        int separatorIndex = orderFno.lastIndexOf("-");
        if (separatorIndex <= 0 || separatorIndex >= orderFno.length() - 1) {
            return null;
        }
        String orderId = orderFno.substring(0, separatorIndex);
        String orderItem = orderFno.substring(separatorIndex + 1);
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(orderItem)) {
            return null;
        }
        return new String[]{orderId, orderItem};
    }


    private void calcOrdingQty(BinOrderDetailDTO dto, CacheComponent cache) {
        //poNoList 来自orderStatusItem.associateNo
        Set<String> poNoSet = cache.getCustomerAssociateNoSet(dto.getModelNo());
        // 订货中数量：采购单、调拨单、退货单、组换单
        // 客单请购未采购,且没有被客单预约
        List<OpsRequestpurchase> customerRequestpurchases = new ArrayList<>();
        List<OpsRequestpurchase> binRequestpurchases = new ArrayList<>();
        for (OpsRequestpurchase po : cache.getRequestPurchaseList(dto.getWarehouseRange(), dto.getModelNo())) {
            String fullNo = new OrderNoInfo(po.getOrderno(), po.getItemno(), po.getSplititemno()).getFullNo();
            if (OrderTypeEnum.KEHU.equals(po.getOrdtype()) && !poNoSet.contains(fullNo)) {
                customerRequestpurchases.add(po);
            } else if (OrderTypeEnum.BIN.equals(po.getOrdtype())) {
                binRequestpurchases.add(po);
            }
        }

        // 客单采购,且没有被客单预约(接单前和接单后)，状态不严谨
        List<OpsPurchaseorder> customerPurchaseOrders = new ArrayList<>();
        List<OpsPurchaseorder> binPurchaseOrders = new ArrayList<>();
        for (OpsPurchaseorder po : cache.getPurchaseOrderList(dto.getWarehouseRange(), dto.getModelNo())) {
            String fullNo = new OrderNoInfo(po.getOrderno(), po.getItemno(), po.getSplititemno()).getFullNo();
            if (OrderTypeEnum.KEHU.equals(po.getOrdtype()) && !poNoSet.contains(fullNo)) {
                customerPurchaseOrders.add(po);
            } else if (OrderTypeEnum.BIN.equals(po.getOrdtype())) {
                binPurchaseOrders.add(po);
            }
        }

        // 主动调拨，分库补库调拨、客户调拨(不含被客单预约的)
        List<OpsInventoryMove> transMove = new ArrayList<>();
        for (OpsInventoryMove move : cache.getInventoryMoveList(dto.getWarehouseRange(), dto.getModelNo())) {
            if (SourceTypeEnum.DB.getType().equals(move.getSourceType())
                    || SourceTypeEnum.RETURN.getType().equals(move.getSourceType())) {
                transMove.add(move);
            }
        }
        //将transMove改为transMove的深拷贝
        List<OpsInventoryMove> transMoveCopy = new ArrayList<>(transMove.size());
        for (OpsInventoryMove move : transMove) {
            OpsInventoryMove moveCopy = new OpsInventoryMove();
            BeanUtil.copyProperties(move, moveCopy);
            transMoveCopy.add(moveCopy);
        }
        transMove = transMoveCopy;
        //分为1.主动调拨，2.分库补库调拨，3.客户调拨（需剔除被客单预约的部分数量）
        //剔除被客单预约的部分数量
        if (CollectionUtils.isNotEmpty(transMove)) {
            applyPreparedMoveQuantity(cache, transMove);
        }
        // 主动调拨，未出库012
        List<TransOrderDO> transOrderList = new ArrayList<>();
        for (TransOrderDO order : cache.getTransOrderInfoMap().get(dto.getModelNo())) {
            if (dto.getWarehouseRangeSet().contains(order.getToWarehouseCode())) {
                transOrderList.add(order);
            }
        }
        //组换
        List<StockAssemblyDetailView> stockAssemblyDetailViews = new ArrayList<>();
        for (StockAssemblyDetailView order : cache.getAssemblyOrderInfoMap().get(dto.getModelNo())) {
            if (dto.getWarehouseRangeSet().contains(order.getDetailWarehouseCode())) {
                stockAssemblyDetailViews.add(order);
            }
        }

        //<modelno,orderFno,DO>
        Map<String, Map<String, BinOrderDetailOrdingInfoDO>> ordingInfoMap = new HashMap<>();
        //1.采购
        inputMapRepo(dto, customerRequestpurchases, ordingInfoMap);
        inputMapRepo(dto, binRequestpurchases, ordingInfoMap);
        inputMapPo(dto, customerPurchaseOrders, ordingInfoMap);
        inputMapPo(dto, binPurchaseOrders, ordingInfoMap);
        //2.调库，退货
        inputMapMove(dto, transMove, ordingInfoMap);
        inputMapTransOrder(dto, transOrderList, ordingInfoMap);
        //3.组换
        inputMapStockAssembly(dto, stockAssemblyDetailViews, ordingInfoMap);
        //记录订货中明细实体
        Map<String, BinOrderDetailOrdingInfoDO> map = ordingInfoMap.get(dto.getModelNo());
        List<BinOrderDetailOrdingInfoDO> ordingInfoList = new ArrayList<>();
        if (map != null) {
            ordingInfoList.addAll(map.values());
        }
        dto.setOrdingInfoDOList(ordingInfoList);
        //计算订货中数量
        int ordingQtySUm = 0;
        for (BinOrderDetailOrdingInfoDO ordingInfo : ordingInfoList) {
            ordingQtySUm += ordingInfo.getQuantity();
        }
        dto.setOrdingQty(ordingQtySUm);
    }

    private void applyPreparedMoveQuantity(CacheComponent cache, List<OpsInventoryMove> transMove) {
        // Task 8：批量预查命中时只做 Set 判断；小候选量场景保留原有查询路径。
        if (cache.hasPreparedMoveIdCache()) {
            for (OpsInventoryMove move : transMove) {
                if (cache.isJyckPreparedMove(move.getInventoryId())) {
                    move.setQuantity(move.getQuantity() - move.getPrepareQuantity());
                }
                if (cache.isPcoPreparedMove(move.getInventoryId())) {
                    move.setQuantity(move.getQuantity() - move.getPrepareQuantity());
                }
            }
            return;
        }

        List<Long> moveIds = new ArrayList<>(transMove.size());
        for (OpsInventoryMove move : transMove) {
            moveIds.add(move.getInventoryId());
        }
        //查询被预约的DO的类型，如果是JYCK，则数量算未被预约的move数量
        List<Long> moveIdByJYCK = binOrderDetailOrdingInfoMapper.selectDBCKMoveNotPrepareByJYCK(moveIds);
        Set<Long> moveIdByJYCKSet = new HashSet<>(moveIdByJYCK);
        for (OpsInventoryMove move : transMove) {
            if (moveIdByJYCKSet.contains(move.getInventoryId())) {
                move.setQuantity(move.getQuantity() - move.getPrepareQuantity());
            }
        }
        List<Long> moveIdByPCO = binOrderDetailOrdingInfoMapper.selectDBCKMoveNotPrepareByPCO(moveIds);
        Set<Long> moveIdByPCOSet = new HashSet<>(moveIdByPCO);
        for (OpsInventoryMove move : transMove) {
            if (moveIdByPCOSet.contains(move.getInventoryId())) {
                move.setQuantity(move.getQuantity() - move.getPrepareQuantity());
            }
        }
    }


    private void inputMapStockAssembly(BinOrderDetailDTO dto, List<StockAssemblyDetailView> stockAssemblyDetailViews, Map<String, Map<String, BinOrderDetailOrdingInfoDO>> mapModel) {
        for (StockAssemblyDetailView assembly : stockAssemblyDetailViews) {
            String orderNo = SourceTypeEnum.DB.getType() + "#" + assembly.getApplyNo() + "#" + assembly.getDetailId() + "#" + "0" + "#";
            if (mapModel.containsKey(assembly.getModelNo())) {
                Map<String, BinOrderDetailOrdingInfoDO> orderNoMap = mapModel.get(assembly.getModelNo());
                if (orderNoMap.containsKey(orderNo)) {
                    BinOrderDetailOrdingInfoDO ording = orderNoMap.get(orderNo);
                    ording.setQuantity(ording.getQuantity() + assembly.getQuantity().intValue());
                } else {
                    BinOrderDetailOrdingInfoDO ording = createOrdingOrder(dto, assembly);
                    orderNoMap.put(orderNo, ording);
                }
            } else {
                Map<String, BinOrderDetailOrdingInfoDO> orderNoMap = new HashMap<>();
                mapModel.put(assembly.getModelNo(), orderNoMap);
                BinOrderDetailOrdingInfoDO ording = createOrdingOrder(dto, assembly);
                orderNoMap.put(orderNo, ording);
            }
        }
    }

    private static void inputMapTransOrder(BinOrderDetailDTO dto, List<TransOrderDO> transOrderList, Map<String, Map<String, BinOrderDetailOrdingInfoDO>> mapModel) {
        for (TransOrderDO transOrder : transOrderList) {
            String orderNo = SourceTypeEnum.DB.getType() + "#" + transOrder.getTransNo() + "#" + transOrder.getItemNo() + "#" + "0" + "#";
            if (mapModel.containsKey(transOrder.getModelNo())) {
                Map<String, BinOrderDetailOrdingInfoDO> orderNoMap = mapModel.get(transOrder.getModelNo());
                if (orderNoMap.containsKey(orderNo)) {
                    BinOrderDetailOrdingInfoDO ording = orderNoMap.get(orderNo);
                    ording.setQuantity(ording.getQuantity() + transOrder.getQuantity());
                } else {
                    BinOrderDetailOrdingInfoDO ording = createOrdingOrder(dto, transOrder);
                    orderNoMap.put(orderNo, ording);
                }
            } else {
                Map<String, BinOrderDetailOrdingInfoDO> orderNoMap = new HashMap<>();
                mapModel.put(transOrder.getModelNo(), orderNoMap);
                BinOrderDetailOrdingInfoDO ording = createOrdingOrder(dto, transOrder);
                orderNoMap.put(orderNo, ording);
            }
        }
    }

    private static void inputMapMove(BinOrderDetailDTO dto, List<OpsInventoryMove> moveList, Map<String, Map<String, BinOrderDetailOrdingInfoDO>> mapModel) {
        for (OpsInventoryMove o : moveList) {
            if (o.getQuantity() == 0) {
                continue;
            }
            String orderNo = o.getSourceType() + "#" + o.getAssociateNo() + "#" + o.getAssociateNoItem() + "#" + o.getAssociateNoSplitno() + "#";
            if (mapModel.containsKey(o.getModelno())) {
                Map<String, BinOrderDetailOrdingInfoDO> orderNoMap = mapModel.get(o.getModelno());
                if (orderNoMap.containsKey(orderNo)) {
                    BinOrderDetailOrdingInfoDO ording = orderNoMap.get(orderNo);
                    ording.setQuantity(ording.getQuantity() + o.getQuantity());
                } else {
                    BinOrderDetailOrdingInfoDO ording = createOrdingOrder(dto, o);
                    orderNoMap.put(orderNo, ording);
                }
            } else {
                Map<String, BinOrderDetailOrdingInfoDO> orderNoMap = new HashMap<>();
                mapModel.put(o.getModelno(), orderNoMap);
                BinOrderDetailOrdingInfoDO ording = createOrdingOrder(dto, o);
                orderNoMap.put(orderNo, ording);
            }
        }
    }

    private static void inputMapPo(BinOrderDetailDTO dto, List<OpsPurchaseorder> purchaseOrders, Map<String, Map<String, BinOrderDetailOrdingInfoDO>> mapModel) {
        for (OpsPurchaseorder o : purchaseOrders) {
            String orderNo = "0" + "#" + o.getOrderno() + "#" + o.getItemno() + "#" + Optional.ofNullable(o.getSplititemno()).orElse(0) + "#";
            if (mapModel.containsKey(o.getModelno())) {
                Map<String, BinOrderDetailOrdingInfoDO> orderNoMap = mapModel.get(o.getModelno());
                if (orderNoMap.containsKey(orderNo)) {
                    BinOrderDetailOrdingInfoDO ording = orderNoMap.get(orderNo);
                    ording.setQuantity(ording.getQuantity() + o.getQuantity() - Optional.ofNullable(o.getQtyreceive()).orElse(0));
                } else {
                    BinOrderDetailOrdingInfoDO ording = createOrdingOrder(dto, o);
                    orderNoMap.put(orderNo, ording);
                }
            } else {
                Map<String, BinOrderDetailOrdingInfoDO> orderNoMap = new HashMap<>();
                mapModel.put(o.getModelno(), orderNoMap);
                BinOrderDetailOrdingInfoDO ording = createOrdingOrder(dto, o);
                orderNoMap.put(orderNo, ording);
            }
        }
    }

    private static void inputMapRepo(BinOrderDetailDTO dto, List<OpsRequestpurchase> requestpurchases, Map<String, Map<String, BinOrderDetailOrdingInfoDO>> mapModel) {
        for (OpsRequestpurchase o : requestpurchases) {
            String orderNo = "0" + "#" + o.getOrderno() + "#" + o.getItemno() + "#" + Optional.ofNullable(o.getSplititemno()).orElse(0) + "#";
            if (mapModel.containsKey(o.getModelno())) {
                Map<String, BinOrderDetailOrdingInfoDO> orderNoMap = mapModel.get(o.getModelno());
                if (orderNoMap.containsKey(orderNo)) {
                    BinOrderDetailOrdingInfoDO ording = orderNoMap.get(orderNo);
                    ording.setQuantity(ording.getQuantity() + o.getQuantity());
                } else {
                    BinOrderDetailOrdingInfoDO ording = createOrdingOrder(dto, o);
                    orderNoMap.put(orderNo, ording);
                }
            } else {
                Map<String, BinOrderDetailOrdingInfoDO> orderNoMap = new HashMap<>();
                mapModel.put(o.getModelno(), orderNoMap);
                BinOrderDetailOrdingInfoDO ording = createOrdingOrder(dto, o);
                orderNoMap.put(orderNo, ording);
            }
        }
    }

    //已请购&& 未生成采购单的
    private static BinOrderDetailOrdingInfoDO createOrdingOrder(BinOrderDetailDTO dto, OpsRequestpurchase po) {
        BinOrderDetailOrdingInfoDO ording = new BinOrderDetailOrdingInfoDO();
        ording.setCalcId(dto.getCalcId());
        ording.setBinDetailId(dto.getId());
        ording.setOrderType("0");
        ording.setOrderNo(po.getOrderno());
        ording.setItemNo(po.getItemno());
        ording.setSplitNo(po.getSplititemno());
        ording.setWarehouseCode(po.getRequestwarehouseid());
        ording.setModelno(po.getModelno());
        ording.setQuantity(po.getQuantity());
        ording.setInQty(0);
        ording.setExpDate(po.getHopedeliverydate());
        ording.setOrderDate(po.getRequesttime());
        ording.setTransType(po.getTranstype());
        ording.setSupplier(po.getSupplierid());
        return ording;
    }

    //已生成采购单的采购
    private static BinOrderDetailOrdingInfoDO createOrdingOrder(BinOrderDetailDTO dto, OpsPurchaseorder po) {
        BinOrderDetailOrdingInfoDO ording = new BinOrderDetailOrdingInfoDO();
        ording.setCalcId(dto.getCalcId());
        ording.setBinDetailId(dto.getId());
        ording.setOrderType("0");
        ording.setOrderNo(po.getOrderno());
        ording.setItemNo(po.getItemno());
        ording.setSplitNo(po.getSplititemno());
        ording.setWarehouseCode(po.getReceivewarehouseid());
        ording.setModelno(po.getModelno());
        ording.setQuantity(po.getQuantity() - Optional.ofNullable(po.getQtyreceive()).orElse(0));
        ording.setInQty(po.getQtyreceive());
        ording.setExpDate(po.getHopedeliverydate());
        ording.setOrderDate(po.getPurchasedate());
        ording.setTransType(po.getTranstype());
        ording.setSupplier(po.getSupplierid());
        return ording;
    }

    //已生成move的调拨
    private static BinOrderDetailOrdingInfoDO createOrdingOrder(BinOrderDetailDTO dto, OpsInventoryMove o) {
        BinOrderDetailOrdingInfoDO ording = new BinOrderDetailOrdingInfoDO();
        ording.setCalcId(dto.getCalcId());
        ording.setBinDetailId(dto.getId());
        ording.setOrderType(o.getSourceType());
        ording.setOrderNo(o.getAssociateNo());
        ording.setItemNo(o.getAssociateNoItem());
        ording.setSplitNo(o.getAssociateNoSplitno());
        ording.setWarehouseCode(o.getSignWarehouseCode());
        ording.setModelno(o.getModelno());
        ording.setQuantity(o.getQuantity());
        if (StringUtils.equals(o.getSourceType(), SourceTypeEnum.PURCHASE.getType())) {
            OpsPurchaseorder po = dto.getCache().getPurchaseOrderLine(
                    o.getModelno(), o.getOrderno(), o.getItemno(), o.getSplititemno());
            if (po != null) {
                ording.setInQty(po.getQtyreceive());
                ording.setExpDate(po.getHopedeliverydate());
                ording.setOrderDate(po.getPurchasedate());
                ording.setTransType(po.getTranstype());
                ording.setSupplier(po.getSupplierid());
            }
        } else {
            ording.setInQty(0);
            ording.setExpDate(o.getCreTime());
            ording.setOrderDate(o.getCreTime());
            ording.setTransType(null);
            ording.setSupplier(o.getSupplierid());
        }
        return ording;
    }

    //未move的调拨
    private static BinOrderDetailOrdingInfoDO createOrdingOrder(BinOrderDetailDTO dto, TransOrderDO o) {
        BinOrderDetailOrdingInfoDO ording = new BinOrderDetailOrdingInfoDO();
        ording.setCalcId(dto.getCalcId());
        ording.setBinDetailId(dto.getId());
        ording.setOrderType(SourceTypeEnum.DB.getType());
        ording.setOrderNo(o.getTransNo());
        ording.setItemNo(o.getItemNo());
        ording.setSplitNo(0);
        ording.setWarehouseCode(o.getToWarehouseCode());
        ording.setModelno(o.getModelNo());
        ording.setQuantity(o.getQuantity());
        ording.setInQty(0);
        ording.setExpDate(o.getShipDate());
        ording.setOrderDate(o.getWmsDlvDate());
        if (o.getTransType() != null) {
            ording.setTransType(o.getTransType().toString());
        }
        ording.setSupplier(o.getFromWarehouseCode());
        return ording;
    }    //未move的调拨

    private static BinOrderDetailOrdingInfoDO createOrdingOrder(BinOrderDetailDTO dto, StockAssemblyDetailView o) {
        BinOrderDetailOrdingInfoDO ording = new BinOrderDetailOrdingInfoDO();
        ording.setCalcId(dto.getCalcId());
        ording.setBinDetailId(dto.getId());
        ording.setOrderType(SourceTypeEnum.DB.getType());
        ording.setOrderNo(o.getApplyNo());
        ording.setItemNo(o.getDetailId().intValue());
        ording.setSplitNo(0);
        ording.setWarehouseCode(o.getDetailWarehouseCode());
        ording.setModelno(o.getModelNo());
        ording.setQuantity(o.getQuantity().intValue());
        ording.setInQty(0);
        ording.setExpDate(o.getDlvDate());
        ording.setOrderDate(o.getApplyDate());
        ording.setSupplier(o.getApplyWarehouseCode());
        return ording;
    }


    // 公司总库存：stock_qty_all
    private void calculateStockQtyAll(BinOrderDetailDTO dto) {
        CacheComponent cache = dto.getCache();
        //Integer normalAvailableInventory = binCalcInventoryMapper.selectNormalAvailableInventoryAllWarehouse(dto.getModelNo());
        List<OpsInventory> list = cache.getInventoryInfoMap().get(dto.getModelNo());
        // TODO Task 7E：可复用 Task 7A 库存数量索引，或新增 modelNo -> totalAvailableQty，避免按型号库存列表求和。
        int normalAvailableInventory = list.stream().map(i -> i.getQuantity() - i.getPrepareQuantity()).reduce(Integer::sum).orElse(0);
        dto.setStockQtyAll(normalAvailableInventory);
    }

    //差异数量和需补库数量，有预设就用预设，没有预设就用计算值
    private void calculateBinDiffQty(BinOrderDetailDTO dto) {
        //如果预设指定补库方式，且非bin品
        if (dto.getReplenishmentMethod() != null && dto.getQtybin() == null) {
            //则差异数量=需补库数量=预设数量
            dto.setNeedQty(dto.getOrderQty());
            dto.setBinDiffQty(dto.getOrderQty());
        }
        //计算需补库数量NeedQty
        else {
            //计算额定数量
            calculateRatedQty(dto);
            //差异数量=需补库数量=额定数量-可用数量,最小为0
            int binDiffQty = Math.max(dto.getRatedQty() - dto.getAvailbleQty(), 0);
            dto.setBinDiffQty(binDiffQty);
            dto.setNeedQty(binDiffQty);
            //差异bin数，向下取整
            int binDiffNum = dto.getBinDiffQty() / dto.getQtybin();
            dto.setBinDiffNum((double) binDiffNum);
        }
    }

    private void calculateMonths(BinOrderDetailDTO dto) {
        // 全公司可用月数 stock_months_all 四舍五入，保留1位小数
        dto.setStockMonthsAll(divide(dto.getStockQtyAll(), dto.getMeanAll()));
        // 在库可用月数 stock_months
        dto.setStockMonths(divide(dto.getAvailbleQty(), dto.getMean()));
        // 最大控制月数
        dto.setMaxControllMonths(dto.getCache().getMaxControlMonths());
        // 最大总可用月数
        dto.setMaxStockAllMonths(dto.getCache().getMaxStockAllMonths());
    }

    //四舍五入，保留两位小数
    public double divide(Integer _a, Double _b) {
        if (_b == 0) {
            return 0;
        }
        BigDecimal a = BigDecimal.valueOf(_a);
        BigDecimal b = BigDecimal.valueOf(_b);
        //HALF_UP 代表四舍五入，2代表保留两位小数
        return a.divide(b, 2, RoundingMode.HALF_UP).doubleValue();
    }

}
