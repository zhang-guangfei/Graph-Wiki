package com.sales.ops.serviceimpl.inventory;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.BindataMapper;
import com.sales.ops.db.dao.SupplierMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.InventoryRiskDao;
import com.sales.ops.db.extdao.OPSProductDao;
import com.sales.ops.db.extdao.OpsInventoryDao;
import com.sales.ops.dto.common.BomSelectParam;
import com.sales.ops.dto.common.BomSelectResult;
import com.sales.ops.dto.common.BomVersions;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.zd.ZDInventoryCkByOrderOutDto;
import com.sales.ops.enums.*;
import com.sales.ops.enums.common.BomSelectEnum;
import com.sales.ops.feign.OPSSupplierFeignApi;
import com.sales.ops.service.ba.BomSelectorService;
import com.sales.ops.service.ba.OpsWarehouseService;
import com.sales.ops.service.inventory.AllotInvenToryService;
import com.sales.ops.serviceimpl.utils.HashCodeUtil;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.receiveorder.OrderSpecExpType;
import com.smc.smccloud.service.BinServiceFeignApi;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author C02483
 * @version 1.0
 * @description: 库存分配算法
 * @date 2021/10/3 9:34
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AllotInvenToryServiceImpl implements AllotInvenToryService {

    @Autowired
    private BaseInventoryService baseInventoryService;
    @Autowired
    private BinServiceFeignApi binServiceFeignApi;
    @Autowired
    private OpsWarehouseService opsWarehouseService;

    @Autowired
    private OPSProductDao productDao;

    private static Long UNIT_Day = 24 * 60 * 60 * 1000L;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private OPSSupplierFeignApi opsSupplierFeignApi;

    @Autowired
    private BindataMapper bindataMapper;

    @Autowired
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Autowired
    private OpsInventoryDao opsInventoryDao;

    @Autowired
    private BomSelectorService bomSelectorService;

    @Autowired
    private InventoryRiskDao inventoryRiskDao;

    /**
     * @author ：c02483
     * @date ：Created in 2021/12/9 14:03
     * @description：大口订单判断:true 不允许出库，flase ；允许出库存
     */
    private boolean BigOrderForWareHose(InventoryStatusEnum inventoryStatusEnum, OpsInventoryMatchConfig matchConfig,
                                        String modelNo, String warehouseCode, int orderqty, int aqty) throws OpsException {
        if (InventoryTypeEnum.TY.getType().equals(matchConfig.getInventoryTypeCode())) {//
            if (inventoryStatusEnum.getCode().equals(InventoryStatusEnum.PRODUCE.getCode())) {//生产在途不判断大口
                return false;
            }

            //ResultVo<BindataVO> resultVo = binServiceFeignApi.getBindataByModelNo(modelNo,warehouseCode);
            BindataExample example = new BindataExample();
            example.createCriteria().andDelflagEqualTo((short)0).andModelnoEqualTo(modelNo).andWarehouseCodeEqualTo(warehouseCode).andStocktypeEqualTo(1);
            List<Bindata>  list = bindataMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(list)) {
                Bindata bindataVO = list.get(0);
                //bindataVO.getSafeqty();
                if (Objects.nonNull(bindataVO)) {//大口订单
                    if (bindataVO.getSafeqty() == 0) {
                        return false;
                    }
                    int monthqty = (int) Math.ceil((bindataVO.getMean() == null ? 0 : bindataVO.getMean()) * 1.5);
                    if (orderqty > monthqty) {//订单数量 > 月使用量 800> 124
                        if (aqty - orderqty <= bindataVO.getSafeqty()) {//库存可用数-订单数 <= 安全库存 186
                            return true;//不允许出库存
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {//该型号没有bin信息
                return false;
                //throw Exceptions.OpsException("获取bin库存配置信息失败");
            }
        } else {
            return false;
        }
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/12/31 10:56
     * @description：安全库存
     */
    private int SafeQtyForWareHose(String modelNo, String warehouseCode) throws OpsException {
        /*BinDataQueryRequest request = new BinDataQueryRequest();
        request.setWarehouseCode(warehouseCode);
        String[] modelNos = new String[]{modelNo};
        request.setModelNos(modelNos);*/
        //ResultVo<BindataVO> resultVo = binServiceFeignApi.getBindataByModelNo(modelNo,warehouseCode);
        BindataExample example = new BindataExample();
        example.createCriteria().andDelflagEqualTo((short)0).andModelnoEqualTo(modelNo).andWarehouseCodeEqualTo(warehouseCode).andStocktypeEqualTo(1);
        List<Bindata>  list = bindataMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            Bindata bindataVO = list.get(0);
            if (Objects.nonNull(bindataVO)) {
                return bindataVO.getSafeqty();
            } else {
                return -1;
            }
        } else {
            return -1;
            //throw Exceptions.OpsException("获取bin库存配置信息失败");
        }

    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/12/23 10:55
     * @description：0 contine; 1:return--end 结束;
     */
    private int UnAssForMove(InventoryStatusEnum statusEnum, InventoryCkByOrderInputDto inputDto, OpsInventoryMatchConfig matchConfig, OpsInventoryMove inventoryMove, InventoryCkByOrderOutDto outDto) throws OpsException {
        int unAllotQuantity = inputDto.getQuantity() - inputDto.getAllotQuantity();//未分数量
        boolean bigOrderjudger = BigOrderForWareHose(statusEnum, matchConfig, inputDto.getModelno(), inventoryMove.getWarehouseCode(), unAllotQuantity, inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity());
        if (bigOrderjudger) {
            return 0;
        }
        if ((inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity()) >= unAllotQuantity) {
            outDto.getInventoryMoveMap().put(inventoryMove, unAllotQuantity);
            outDto.addMapSorted(inventoryMove);
            outDto.getWarehouseCodeSets().add(inventoryMove.getWarehouseCode());
            inputDto.addAllotQuantity(unAllotQuantity);
            return 1;
        }
        return 0;
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/10/3 9:32
     * @description：按照库存属性---》库房遍历出库,整出规则
     */
    @Override
    public void PHUnAssInvenTory(InventoryCkByOrderInputDto inputDto, List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList,
                                 InventoryCkByOrderOutDto outDto) throws OpsException {
        for (OpsInventoryRuleConfigItem ruleConfigItem : opsInventoryRuleConfigItemList) {
            //获取更详细出库规则
            OpsInventoryMatchConfig matchConfig = baseInventoryService.getOpsInventoryMatchConfig(ruleConfigItem.getInventoryMatchCode());
            //获取库存属性
            // bugid:20641 C14717 20260423
            List<OpsInventoryProperty>  inventoryPropertyList = baseInventoryService.getInvProperty(matchConfig, inputDto, inputDto.getModelno());
            if(CollectionUtils.isEmpty(inventoryPropertyList)){
                continue;
            }
            //获取营业所和库房关联代码
            List<OpsWarehouseSalesbranchConfig> salesbranchConfigList = baseInventoryService.getBranchListBysalesBranchId(inputDto.getOrderType(),inputDto.getCustomer(),inputDto.getDeptno(), matchConfig.getMatchWarehouseTypeCode(),inputDto.getExpDlvType(),inputDto.getExpLinkNo());
            if (CollectionUtils.isEmpty(salesbranchConfigList)) {
                continue;
            }
            //获取满足订单数量的仓库
            for (OpsWarehouseSalesbranchConfig salesbranchConfig : salesbranchConfigList) {
                if (InventoryStatusEnum.NORMAL.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventory> inventoryList = baseInventoryService.getOpsInventoryListV3(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getInvMap());
                    for (OpsInventory inventory : inventoryList) {
                        if (inputDto.isAllotStatus()) {
                            return;
                        }
                        int unAllotQuantity = inputDto.getQuantity() - inputDto.getAllotQuantity();//未分数量
                        //风险在库 库存数量大于风险在库数量
                        boolean invQtyBig = true;
                        if(matchConfig.getMatchInventoryRisk()){
                            // 判断风险和库存的数量比较
                            List<RiskDto> riskDtos = inputDto.getRiskMap().get(inputDto.getModelno());
                            for (RiskDto riskDto:riskDtos){
                                if(riskDto.getInventoryId().equals(inventory.getInventoryId())){
                                   int invQty = inventory.getQuantity() - inventory.getPrepareQuantity() ;
                                   int riskQty = riskDto.getQuantity() - riskDto.getPrepareQuantity();
                                   // 当风险在库数量小于库存数量时
                                   if(invQty > riskQty){
                                       invQtyBig = false;
                                       if ( riskQty >= unAllotQuantity) {
                                           outDto.getInventoryMap().put(inventory, unAllotQuantity);
                                           outDto.addMapSorted(inventory);
                                           outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                           inputDto.addAllotQuantity(unAllotQuantity);
                                           // 风险在库标识
                                           outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                                           return;
                                       }
                                   }
                                }
                            }
                        }
                        if (!invQtyBig){
                            continue;
                        }
                        //判断大口订单
                        boolean bigOrderjudger = BigOrderForWareHose(InventoryStatusEnum.NORMAL, matchConfig, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), unAllotQuantity, inventory.getQuantity() - inventory.getPrepareQuantity());
                        if (bigOrderjudger) {
                            continue;
                        }
                        if ((inventory.getQuantity() - inventory.getPrepareQuantity()) >= unAllotQuantity) {
                            outDto.getInventoryMap().put(inventory, unAllotQuantity);
                            outDto.addMapSorted(inventory);
                            outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                            inputDto.addAllotQuantity(unAllotQuantity);
                            if(matchConfig.getMatchInventoryRisk()){
                                // 风险在库标识
                                outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                            }
                            return;
                        }
                    }
                }
                //生成在途
                if (InventoryStatusEnum.PRODUCE.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (inputDto.isAllotStatus()) {
                            return;
                        }
                        int flag = UnAssForMove(InventoryStatusEnum.PRODUCE, inputDto, matchConfig, inventoryMove, outDto);
                        if (flag == 1) {
                            return;
                        }
                    }
                }
                //到货未入库
                if (InventoryStatusEnum.W.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (inputDto.isAllotStatus()) {
                            return;
                        }
                        int flag = UnAssForMove(InventoryStatusEnum.W, inputDto, matchConfig, inventoryMove, outDto);
                        if (flag == 1) {
                            return;
                        }
                    }
                }
                //采购在途
                if (InventoryStatusEnum.CGTRANS.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (inputDto.isAllotStatus()) {
                            return;
                        }
                        if (matchConfig.getMathchPreRecDate()) {
                            if (Objects.nonNull(inventoryMove.getPrereceivedate())) {
                                //判断交货期和到货期 bugid 8926 20221207 c14717 客户订单交货期 <= 在途库存的预到货日期，可使用在途库存
                                if (inputDto.getHopeDate().getTime() - inventoryMove.getPrereceivedate().getTime() <= 0) {
                                    int flag = UnAssForMove(InventoryStatusEnum.CGTRANS, inputDto, matchConfig, inventoryMove, outDto);
                                    if (flag == 1) {
                                        return;
                                    }
                                } else {
                                    Supplier supplier = supplierMapper.selectByPrimaryKey(inventoryMove.getSupplierid());
                                    if (Objects.nonNull(supplier)) {
                                        //Supplier supplier = commonResult.getData();
                                        if (inventoryMove.getPrereceivedate().getTime() - (new Date()).getTime()
                                                <= supplier.getFstdeliveryday() * UNIT_Day) {
                                            int flag = UnAssForMove(InventoryStatusEnum.CGTRANS, inputDto, matchConfig, inventoryMove, outDto);
                                            if (flag == 1) {
                                                return;
                                            }
                                        }
                                    } else {
                                        throw Exceptions.OpsException("获取供应商失败。", inventoryMove.getSupplierid());
                                    }
                                }
                            }
                        }else{//ppl 直接出不判断日期
                            int flag = UnAssForMove(InventoryStatusEnum.CGTRANS, inputDto, matchConfig, inventoryMove, outDto);
                            if (flag == 1) {
                                return;
                            }
                        }
                    }
                }

                //调拨在途
                if (InventoryStatusEnum.DBTRANS.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (inputDto.isAllotStatus()) {
                            return;
                        }
                        if (matchConfig.getMathchPreRecDate()) {
                            if (Objects.nonNull(inventoryMove.getPrereceivedate())) {
                                if (inventoryMove.getPrereceivedate().getTime() - inputDto.getHopeDate().getTime() <= 0) {
                                    int flag = UnAssForMove(InventoryStatusEnum.DBTRANS, inputDto, matchConfig, inventoryMove, outDto);
                                    if (flag == 1) {
                                        return;
                                    }
                                }
                            }
                        }else{//ppl 直接出
                            int flag = UnAssForMove(InventoryStatusEnum.DBTRANS, inputDto, matchConfig, inventoryMove, outDto);
                            if (flag == 1) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/12/23 10:55
     * @description：0 contine; 1:return--end 结束;
     */
    private int ASSQtyForMove(InventoryStatusEnum inventoryStatusEnum, OpsInventoryMove inventoryMove, OpsInventoryMatchConfig matchConfig, InventoryCkByOrderInputDto inputDto, InventoryCkByOrderOutDto outDto) throws OpsException {

        int unAllotQuantity = inputDto.getQuantity() - inputDto.getAllotQuantity();
        boolean bigOrderjudger = BigOrderForWareHose(inventoryStatusEnum, matchConfig, inputDto.getModelno(), inventoryMove.getWarehouseCode(), unAllotQuantity, inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity());
        if (bigOrderjudger) {
            return 0;
        }
        if ((inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity()) >= unAllotQuantity) {
            outDto.getInventoryMoveMap().put(inventoryMove, unAllotQuantity);
            outDto.addMapSorted(inventoryMove);
            outDto.getWarehouseCodeSets().add(inventoryMove.getWarehouseCode());
            inputDto.addAllotQuantity(unAllotQuantity);
        } else if (inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity() < unAllotQuantity) {
            outDto.getInventoryMoveMap().put(inventoryMove, inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity());
            outDto.addMapSorted(inventoryMove);
            outDto.getWarehouseCodeSets().add(inventoryMove.getWarehouseCode());
            inputDto.addAllotQuantity(inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity());
        }
        return 0;
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/10/3 9:32
     * @description：按照库存属性---》库房遍历出库,拆分数量规则
     */
    @Override
    public void PHAssQtyInvenTory(InventoryCkByOrderInputDto inputDto, List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList,
                                  InventoryCkByOrderOutDto outDto) throws OpsException {
        for (OpsInventoryRuleConfigItem ruleConfigItem : opsInventoryRuleConfigItemList) {
            OpsInventoryMatchConfig matchConfig = baseInventoryService.getOpsInventoryMatchConfig(ruleConfigItem.getInventoryMatchCode());
            // 获取库存属性 bugid:20641
            List<OpsInventoryProperty>  inventoryPropertyList = baseInventoryService.getInvProperty(matchConfig, inputDto, inputDto.getModelno());
            if(CollectionUtils.isEmpty(inventoryPropertyList)){
                continue;
            }
            List<OpsWarehouseSalesbranchConfig> salesbranchConfigList = baseInventoryService.getBranchListBysalesBranchId(inputDto.getOrderType(),inputDto.getCustomer(),inputDto.getDeptno(), matchConfig.getMatchWarehouseTypeCode(),inputDto.getExpDlvType(),inputDto.getExpLinkNo());
            if (CollectionUtils.isEmpty(salesbranchConfigList)) {
                continue;
            }
            for (OpsWarehouseSalesbranchConfig salesbranchConfig : salesbranchConfigList) {
                if (inputDto.isAllotStatus()) {
                    return;
                }
                if (InventoryStatusEnum.NORMAL.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventory> inventoryList = baseInventoryService.getOpsInventoryListV3(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getInvMap());
                    //bugid:19127 跳过已经出的库存 GK-TY先于风险在库，解决既包含GK-TY同时也是风险的问题
                    outerLoop:
                    for (OpsInventory inventory : inventoryList) {
                        if (Objects.nonNull(outDto.getInventoryMap())){
                            for (Map.Entry<OpsInventory, Integer> entry : outDto.getInventoryMap().entrySet()){
                                if(inventory.getInventoryId().equals(entry.getKey().getInventoryId())){
                                    continue outerLoop;
                                }
                            }
                        }
                        if (inputDto.isAllotStatus()) {
                            return;
                        }
                        int unAllotQuantity = inputDto.getQuantity() - inputDto.getAllotQuantity();
                        //风险在库 库存数量大于风险在库数量
                        boolean invQtyBig = true;
                        if(matchConfig.getMatchInventoryRisk()){
                            // 判断风险和库存的数量比较
                            List<RiskDto> riskDtos = inputDto.getRiskMap().get(inputDto.getModelno());
                            for (RiskDto riskDto:riskDtos){
                                if(riskDto.getInventoryId().equals(inventory.getInventoryId())){
                                    int invQty = inventory.getQuantity() - inventory.getPrepareQuantity() ;
                                    int riskQty = riskDto.getQuantity() - riskDto.getPrepareQuantity();
                                    // 当风险在库数量小于库存数量时
                                    if(invQty > riskQty){
                                        invQtyBig = false;
                                        if ( riskQty >= unAllotQuantity) {
                                            outDto.getInventoryMap().put(inventory, unAllotQuantity);
                                            outDto.addMapSorted(inventory);
                                            outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                            inputDto.addAllotQuantity(unAllotQuantity);
                                            // 风险在库标识
                                            outDto.getRiskInvMap().put(inventory.getInventoryId(), true);

                                            break;
                                        } else {
                                            outDto.getInventoryMap().put(inventory, riskQty);
                                            outDto.addMapSorted(inventory);
                                            outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                            inputDto.addAllotQuantity(riskQty);
                                            // 风险在库标识
                                            outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                                        }
                                    }
                                }
                            }
                        }
                        if (!invQtyBig){
                            continue;
                        }
                        if (OrderTypeEnum.PRE.equals(inputDto.getOrderType())
                                && InventoryMatchConfigEnum.MASTER_GM.getCode().equals(ruleConfigItem.getInventoryMatchCode()))
                        {
                            int safeqty = SafeQtyForWareHose(inputDto.getModelno(), salesbranchConfig.getWarehouseCode());
                            if (inventory.getQuantity() - inventory.getPrepareQuantity() - unAllotQuantity - safeqty >= 0) {
                                outDto.getInventoryMap().put(inventory, unAllotQuantity);
                                outDto.addMapSorted(inventory);
                                outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                inputDto.addAllotQuantity(unAllotQuantity);
                                if(matchConfig.getMatchInventoryRisk()){
                                    // 风险在库标识
                                    outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                                }
                                break;
                            } else if (inventory.getQuantity() - inventory.getPrepareQuantity() - unAllotQuantity - safeqty < 0) {
                                if (inventory.getQuantity() - inventory.getPrepareQuantity() - safeqty > 0) {
                                    outDto.getInventoryMap().put(inventory, inventory.getQuantity() - inventory.getPrepareQuantity() - safeqty);
                                    outDto.addMapSorted(inventory);
                                    outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                    inputDto.addAllotQuantity(inventory.getQuantity() - inventory.getPrepareQuantity() - safeqty);
                                    if(matchConfig.getMatchInventoryRisk()){
                                        // 风险在库标识
                                        outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                                    }
                                }
                            }
                        }
                        else
                        {
                            boolean bigOrderjudger = BigOrderForWareHose(InventoryStatusEnum.NORMAL, matchConfig,
                                    inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), unAllotQuantity,
                                    inventory.getQuantity() - inventory.getPrepareQuantity());
                            if (bigOrderjudger) {
                                continue;
                            }
                            if ((inventory.getQuantity() - inventory.getPrepareQuantity()) >= unAllotQuantity) {
                                outDto.getInventoryMap().put(inventory, unAllotQuantity);
                                outDto.addMapSorted(inventory);
                                outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                inputDto.addAllotQuantity(unAllotQuantity);
                                if(matchConfig.getMatchInventoryRisk()){
                                    // 风险在库标识
                                    outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                                }
                                break;
                            } else if (inventory.getQuantity() - inventory.getPrepareQuantity() < unAllotQuantity) {
                                outDto.getInventoryMap().put(inventory, inventory.getQuantity() - inventory.getPrepareQuantity());
                                outDto.addMapSorted(inventory);
                                outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                inputDto.addAllotQuantity(inventory.getQuantity() - inventory.getPrepareQuantity());
                                if(matchConfig.getMatchInventoryRisk()){
                                    // 风险在库标识
                                    outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                                }
                            }
                        }
                    }
                }
                //生产在途
                if (InventoryStatusEnum.PRODUCE.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (inputDto.isAllotStatus()) {
                            return;
                        }
                        ASSQtyForMove(InventoryStatusEnum.PRODUCE, inventoryMove, matchConfig, inputDto, outDto);
                    }
                }
                //到货未入库
                if (InventoryStatusEnum.W.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (inputDto.isAllotStatus()) {
                            return;
                        }
                        ASSQtyForMove(InventoryStatusEnum.W, inventoryMove, matchConfig, inputDto, outDto);
                    }
                }
                //采购在途
                if (InventoryStatusEnum.CGTRANS.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (inputDto.isAllotStatus()) {
                            return;
                        }
                        if (matchConfig.getMathchPreRecDate()) {
                            if (Objects.nonNull(inventoryMove.getPrereceivedate())) {
                                //bugid 8926 20221207 c14717 客户订单交货期 <= 在途库存的预到货日期，可使用在途库存
                                if (inputDto.getHopeDate().getTime() - inventoryMove.getPrereceivedate().getTime() <= 0) {
                                    ASSQtyForMove(InventoryStatusEnum.CGTRANS, inventoryMove, matchConfig, inputDto, outDto);
                                } else {
                                    //CommonResult<Supplier> commonResult = opsSupplierFeignApi.getSupplierInfo(inventoryMove.getSupplierid());
                                    Supplier supplier = supplierMapper.selectByPrimaryKey(inventoryMove.getSupplierid());
                                    if (Objects.nonNull(supplier)) {
                                        //Supplier supplier =  commonResult.getData();
                                        if (inventoryMove.getPrereceivedate().getTime() - (new Date()).getTime()
                                                <= supplier.getFstdeliveryday() * UNIT_Day) {
                                            ASSQtyForMove(InventoryStatusEnum.CGTRANS, inventoryMove, matchConfig, inputDto, outDto);
                                        }
                                    } else {
                                        throw Exceptions.OpsException("获取供应商失败。", inventoryMove.getSupplierid());
                                    }
                                }
                            }
                        }else{//ppl 不判断日期
                            ASSQtyForMove(InventoryStatusEnum.CGTRANS, inventoryMove, matchConfig, inputDto, outDto);
                        }
                    }
                }
                //调拨在途
                if (InventoryStatusEnum.DBTRANS.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (inputDto.isAllotStatus()) {
                            return;
                        }
                        if (matchConfig.getMathchPreRecDate()) {
                            if (Objects.nonNull(inventoryMove.getPrereceivedate())) {
                                if (inventoryMove.getPrereceivedate().getTime() - inputDto.getHopeDate().getTime() <= 0) {
                                    ASSQtyForMove(InventoryStatusEnum.DBTRANS, inventoryMove, matchConfig, inputDto, outDto);
                                }
                            }
                        }else {
                            ASSQtyForMove(InventoryStatusEnum.DBTRANS, inventoryMove, matchConfig, inputDto, outDto);
                        }
                    }
                }

            }
        }
    }

    private void AssBomQtyForMove(InventoryStatusEnum inventoryStatusEnum,OpsInventoryMatchConfig matchConfig, AssBomDetail assBomDetail, OpsInventoryMove inventoryMove,InventoryCkByOrderOutDto outDto) throws OpsException{

        int unAllotQuantity = assBomDetail.getAssQty() - assBomDetail.getAssAllotQty();

        boolean bigOrderjudger = BigOrderForWareHose(inventoryStatusEnum, matchConfig, inventoryMove.getModelno(), inventoryMove.getWarehouseCode(), unAllotQuantity, inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity());
        if (bigOrderjudger) {
            return;
        }

        if ((inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity()) >= unAllotQuantity) {
            assBomDetail.setAssAllotQty(assBomDetail.getAssAllotQty() + unAllotQuantity);
            assBomDetail.getMapMoveqty().put(inventoryMove, unAllotQuantity);
            outDto.addMapSorted(inventoryMove);//排序字段
            //bugid:14956 c14717 20240815 打开注释 拆分型号在途参与集约仓计算
            outDto.getWarehouseCodeSets().add(inventoryMove.getWarehouseCode());
            unAllotQuantity = unAllotQuantity - unAllotQuantity;
        }
        /*else if (inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity() < unAllotQuantity) {
            assBomDetail.setAssAllotQty(assBomDetail.getAssAllotQty() + inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity());
            assBomDetail.getMapMoveqty().put(inventoryMove, inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity());
            //   outDto.getWarehouseCodeSets().add(inventoryMove.getWarehouseCode());
            unAllotQuantity = unAllotQuantity - (inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity());
        }*/
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/11/29 20:37
     * @description：同一属性，可以出多仓
     */
    private List<AssBomDetail> PHASSModelNoForBom(InventoryCkByOrderInputDto inputDto, List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList, BomVersions bomVersion,InventoryCkByOrderOutDto outDto) throws OpsException {
        List<ProductBomDetail> productBomDetailList = bomVersion.getBomDetails();
        List<AssBomDetail> assBomDetailList = new ArrayList<>();

        for (ProductBomDetail productBomDetail : productBomDetailList) {
            Integer assQty = productBomDetail.getQuantity() * (inputDto.getQuantity() - inputDto.getAllotQuantity());
            AssBomDetail assBomDetail = new AssBomDetail();
            assBomDetail.setProductBomDetail(productBomDetail);
            assBomDetail.setAssQty(assQty);
            for (OpsInventoryRuleConfigItem ruleConfigItem : opsInventoryRuleConfigItemList) {
                if (assBomDetail.IsEnough()) {
                    break;
                }
                OpsInventoryMatchConfig matchConfig = baseInventoryService.getOpsInventoryMatchConfig(ruleConfigItem.getInventoryMatchCode());
                // 获取库存属性 bugid:20641
                List<OpsInventoryProperty>  inventoryPropertyList = baseInventoryService.getInvProperty(matchConfig, inputDto, productBomDetail.getModelno());
                if(CollectionUtils.isEmpty(inventoryPropertyList)){
                    continue;
                }
                List<OpsWarehouseSalesbranchConfig> salesbranchConfigList = baseInventoryService.getBranchListBysalesBranchId(inputDto.getOrderType(),inputDto.getCustomer(),inputDto.getDeptno(), matchConfig.getMatchWarehouseTypeCode(),inputDto.getExpDlvType(),inputDto.getExpLinkNo());
                for (OpsWarehouseSalesbranchConfig salesbranchConfig : salesbranchConfigList) {
                    if (assBomDetail.IsEnough()) {
                        break;
                    }
                    if (InventoryStatusEnum.NORMAL.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                        List<OpsInventory> inventoryList = baseInventoryService.getOpsInventoryListV3(inventoryPropertyList, productBomDetail.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getInvMap());
                        for (OpsInventory inventory : inventoryList) {
                            if (assBomDetail.IsEnough()) {
                                break;
                            }
                            int unAllotQuantity = assBomDetail.getAssQty() - assBomDetail.getAssAllotQty();

                            //风险在库 库存数量大于风险在库数量
                            boolean invQtyBig = true;
                            if(matchConfig.getMatchInventoryRisk()){
                                // 判断风险和库存的数量比较
                                List<RiskDto> riskDtos = inputDto.getRiskMap().get(productBomDetail.getModelno());
                                for (RiskDto riskDto:riskDtos){
                                    if(riskDto.getInventoryId().equals(inventory.getInventoryId())){
                                        int invQty = inventory.getQuantity() - inventory.getPrepareQuantity() ;
                                        int riskQty = riskDto.getQuantity() - riskDto.getPrepareQuantity();
                                        // 当风险在库数量小于库存数量时
                                        if(invQty > riskQty){
                                            invQtyBig = false;
                                            if (riskQty >= unAllotQuantity) {
                                                assBomDetail.setAssAllotQty(assBomDetail.getAssAllotQty() + unAllotQuantity);
                                                assBomDetail.getMapqty().put(inventory, unAllotQuantity);
                                                outDto.addMapSorted(inventory);//排序字段
                                                outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                                unAllotQuantity = unAllotQuantity - unAllotQuantity;
                                                // 风险在库标识
                                                outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                                            }
                                        }
                                    }
                                }
                            }
                            if (!invQtyBig){
                                continue;
                            }
                            //判断大口
                            boolean bigOrderjudger = BigOrderForWareHose(InventoryStatusEnum.NORMAL, matchConfig, productBomDetail.getModelno(), salesbranchConfig.getWarehouseCode(), unAllotQuantity, inventory.getQuantity() - inventory.getPrepareQuantity());
                            if (bigOrderjudger) {
                                continue;
                            }
                            if ((inventory.getQuantity() - inventory.getPrepareQuantity()) >= unAllotQuantity) {
                                assBomDetail.setAssAllotQty(assBomDetail.getAssAllotQty() + unAllotQuantity);
                                assBomDetail.getMapqty().put(inventory, unAllotQuantity);
                                outDto.addMapSorted(inventory);//排序字段
                                outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                unAllotQuantity = unAllotQuantity - unAllotQuantity;
                                if(matchConfig.getMatchInventoryRisk()){
                                    // 风险在库标识
                                    outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                                }
                            }
                         /*   else if (inventory.getQuantity() - inventory.getPrepareQuantity() < unAllotQuantity) {
                                assBomDetail.setAssAllotQty(assBomDetail.getAssAllotQty() + inventory.getQuantity() - inventory.getPrepareQuantity());
                                assBomDetail.getMapqty().put(inventory, inventory.getQuantity() - inventory.getPrepareQuantity());
                                //outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                unAllotQuantity = unAllotQuantity - (inventory.getQuantity() - inventory.getPrepareQuantity());
                            }*/
                        }
                    }
                    //生产在途
                    if (InventoryStatusEnum.PRODUCE.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                        List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, productBomDetail.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                        for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                            if (assBomDetail.IsEnough()) {
                                break;
                            }
                            AssBomQtyForMove(InventoryStatusEnum.PRODUCE,matchConfig,assBomDetail, inventoryMove, outDto);
                        }
                    }
                    //到货未入库
                    if (InventoryStatusEnum.W.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                        List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, productBomDetail.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                        for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                            if (assBomDetail.IsEnough()) {
                                break;
                            }
                            AssBomQtyForMove(InventoryStatusEnum.W,matchConfig,assBomDetail, inventoryMove, outDto);
                        }
                    }
                    //采购在途
                    if (InventoryStatusEnum.CGTRANS.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                        List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, productBomDetail.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                        for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                            if (assBomDetail.IsEnough()) {
                                break;
                            }
                            if (matchConfig.getMathchPreRecDate()) {
                                if (Objects.nonNull(inventoryMove.getPrereceivedate())) {
                                    //bugid 8926 20221207 c14717 客户订单交货期 <= 在途库存的预到货日期，可使用在途库存
                                    if (inputDto.getHopeDate().getTime() - inventoryMove.getPrereceivedate().getTime() <= 0) {
                                        AssBomQtyForMove(InventoryStatusEnum.CGTRANS, matchConfig, assBomDetail, inventoryMove, outDto);
                                    } else {
                                        Supplier supplier = supplierMapper.selectByPrimaryKey(inventoryMove.getSupplierid());
                                        if (Objects.nonNull(supplier)) {
                                            //Supplier supplier = commonResult.getData();
                                            if (inventoryMove.getPrereceivedate().getTime() - (new Date()).getTime()
                                                    <= supplier.getFstdeliveryday() * UNIT_Day) {
                                                AssBomQtyForMove(InventoryStatusEnum.CGTRANS, matchConfig, assBomDetail, inventoryMove, outDto);
                                            }
                                        } else {
                                            throw Exceptions.OpsException("获取供应商失败。", inventoryMove.getSupplierid());
                                        }
                                    }
                                }
                            }else{//ppl 不判断日期
                                AssBomQtyForMove(InventoryStatusEnum.CGTRANS, matchConfig, assBomDetail, inventoryMove, outDto);
                            }
                        }
                    }
                    //调拨在途
                    if (InventoryStatusEnum.DBTRANS.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                        List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, productBomDetail.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                        for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                            if (assBomDetail.IsEnough()) {
                                break;
                            }
                            if (matchConfig.getMathchPreRecDate()) {
                                if (Objects.nonNull(inventoryMove.getPrereceivedate())) {
                                    if (inventoryMove.getPrereceivedate().getTime() - inputDto.getHopeDate().getTime() <= 0) {
                                        AssBomQtyForMove(InventoryStatusEnum.DBTRANS, matchConfig, assBomDetail, inventoryMove, outDto);
                                    }
                                }
                            }else {
                                AssBomQtyForMove(InventoryStatusEnum.DBTRANS, matchConfig, assBomDetail, inventoryMove, outDto);
                            }
                        }
                    }
                }
            }
            assBomDetailList.add(assBomDetail);
        }
        return assBomDetailList;


    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/11/29 20:37
     * @description：单一仓库满足才能出库
     */
    private List<AssBomDetail> HPASSModelNoForBom(InventoryCkByOrderInputDto inputDto, List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList,
                                                  List<OpsWarehouseSalesbranchConfig> salesbranchConfigList, BomVersions bomVersion ,InventoryCkByOrderOutDto outDto) throws OpsException {
        List<ProductBomDetail> productBomDetailList = bomVersion.getBomDetails();
        for (OpsWarehouseSalesbranchConfig salesbranchConfig : salesbranchConfigList) {
            //判断拆分能力
            OpsWarehouse opsWarehouse = opsWarehouseService.findById(salesbranchConfig.getWarehouseCode());
            //单项组装 且 分库 且无组装能力
            if (OrderSpecExpType.include(inputDto.getExpDlvType(),OrderSpecExpType.OneItemToAssemble) && WarehouseTypeEnum.FDC.getHouseTypeCode().equals(opsWarehouse.getWarehouseType()) && !opsWarehouse.getAssemblyFlag() ) { //无拆分能力
                continue;
            }
            //单项组装 且 委托 且无组装能力
            if (OrderSpecExpType.include(inputDto.getExpDlvType(),OrderSpecExpType.OneItemToAssemble) && WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsWarehouse.getWarehouseType()) && !opsWarehouse.getAssemblyFlag() ) { //无拆分能力
                continue;
            }
            List<AssBomDetail> assBomDetailList = new ArrayList<>();
            boolean enough = true;
            for (ProductBomDetail productBomDetail : productBomDetailList) {
                Integer assQty = productBomDetail.getQuantity() * (inputDto.getQuantity() - inputDto.getAllotQuantity());
                AssBomDetail assBomDetail = new AssBomDetail();
                assBomDetail.setProductBomDetail(productBomDetail);
                assBomDetail.setAssQty(assQty);
                for (OpsInventoryRuleConfigItem ruleConfigItem : opsInventoryRuleConfigItemList) {
                    if (assBomDetail.getAssAllotQty().equals(assQty)) {
                        break;
                    }
                    OpsInventoryMatchConfig matchConfig = baseInventoryService.getOpsInventoryMatchConfig(ruleConfigItem.getInventoryMatchCode());
                    // 获取库存属性 bugid:20641
                    List<OpsInventoryProperty>  inventoryPropertyList = baseInventoryService.getInvProperty(matchConfig, inputDto, productBomDetail.getModelno());
                    if(CollectionUtils.isEmpty(inventoryPropertyList)){
                        continue;
                    }
                    if (assBomDetail.IsEnough()) {
                        break;
                    }
                    if (InventoryStatusEnum.NORMAL.getCode().equals(matchConfig.getMatchInventoryStatus())){
                        List<OpsInventory> inventoryList = baseInventoryService.getOpsInventoryListV3(inventoryPropertyList, productBomDetail.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getInvMap());
                        for (OpsInventory inventory : inventoryList) {
                            if (assBomDetail.IsEnough()) {
                                break;
                            }
                            int unAllotQuantity = assQty - assBomDetail.getAssAllotQty();
                            //风险在库 库存数量大于风险在库数量
                            boolean invQtyBig = true;
                            if(matchConfig.getMatchInventoryRisk()){
                                // 判断风险和库存的数量比较
                                List<RiskDto> riskDtos = inputDto.getRiskMap().get(productBomDetail.getModelno());
                                for (RiskDto riskDto:riskDtos){
                                    if(riskDto.getInventoryId().equals(inventory.getInventoryId())){
                                        int invQty = inventory.getQuantity() - inventory.getPrepareQuantity() ;
                                        int riskQty = riskDto.getQuantity() - riskDto.getPrepareQuantity();
                                        // 当风险在库数量小于库存数量时
                                        if(invQty > riskQty){
                                            invQtyBig = false;
                                            if (riskQty >= unAllotQuantity) {
                                                assBomDetail.setAssAllotQty(assBomDetail.getAssAllotQty() + unAllotQuantity);
                                                assBomDetail.getMapqty().put(inventory, unAllotQuantity);
                                                outDto.addMapSorted(inventory);//排序字段
                                                outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                                unAllotQuantity = unAllotQuantity - unAllotQuantity;
                                                // 风险在库标识
                                                outDto.getRiskInvMap().put(inventory.getInventoryId(), true);

                                            }
                                        }
                                    }
                                }
                            }
                            if (!invQtyBig){
                                continue;
                            }
                            boolean bigOrderjudger = BigOrderForWareHose(InventoryStatusEnum.NORMAL, matchConfig, productBomDetail.getModelno(), salesbranchConfig.getWarehouseCode(), unAllotQuantity, inventory.getQuantity() - inventory.getPrepareQuantity());
                            if (bigOrderjudger) {
                                continue;
                            }
                            if ((inventory.getQuantity() - inventory.getPrepareQuantity()) >= unAllotQuantity) {
                                assBomDetail.setAssAllotQty(assBomDetail.getAssAllotQty() + unAllotQuantity);
                                assBomDetail.getMapqty().put(inventory, unAllotQuantity);
                                outDto.addMapSorted(inventory);//排序字段
                                outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                unAllotQuantity = unAllotQuantity - unAllotQuantity;
                                if(matchConfig.getMatchInventoryRisk()){
                                    // 风险在库标识
                                    outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                                }
                            }
                        /*else if (inventory.getQuantity() - inventory.getPrepareQuantity() < unAllotQuantity) {
                            assBomDetail.setAssAllotQty(assBomDetail.getAssAllotQty() + inventory.getQuantity() - inventory.getPrepareQuantity());
                            assBomDetail.getMapqty().put(inventory, inventory.getQuantity() - inventory.getPrepareQuantity());
                            //outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                            unAllotQuantity = unAllotQuantity - (inventory.getQuantity() - inventory.getPrepareQuantity());
                        }*/
                        }
                    }
                    //生产在途
                    if (InventoryStatusEnum.PRODUCE.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                        List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, productBomDetail.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                        for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                            if (assBomDetail.IsEnough()) {
                                break;
                            }
                            AssBomQtyForMove(InventoryStatusEnum.PRODUCE,matchConfig,assBomDetail, inventoryMove,outDto);
                        }
                    }
                    //到货未入库
                    if (InventoryStatusEnum.W.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                        List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, productBomDetail.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                        for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                            if (assBomDetail.IsEnough()) {
                                break;
                            }
                            AssBomQtyForMove(InventoryStatusEnum.W,matchConfig,assBomDetail, inventoryMove,outDto);
                        }
                    }
                    //采购在途
                    if (InventoryStatusEnum.CGTRANS.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                        List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, productBomDetail.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                        for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                            if (assBomDetail.IsEnough()) {
                                break;
                            }
                            if (matchConfig.getMathchPreRecDate()) {
                                if (Objects.nonNull(inventoryMove.getPrereceivedate())) {
                                    //bugid 8926 20221207 c14717 客户订单交货期 <= 在途库存的预到货日期，可使用在途库存
                                    if (inputDto.getHopeDate().getTime() - inventoryMove.getPrereceivedate().getTime()  <= 0) {
                                        AssBomQtyForMove(InventoryStatusEnum.CGTRANS, matchConfig, assBomDetail, inventoryMove, outDto);
                                    } else {
                                        //CommonResult<Supplier> commonResult = opsSupplierFeignApi.getSupplierInfo(inventoryMove.getSupplierid());
                                        Supplier supplier = supplierMapper.selectByPrimaryKey(inventoryMove.getSupplierid());
                                        if (Objects.nonNull(supplier)) {
                                            //Supplier supplier =  commonResult.getData();
                                            if (inventoryMove.getPrereceivedate().getTime() - (new Date()).getTime()
                                                    <= supplier.getFstdeliveryday() * UNIT_Day) {
                                                AssBomQtyForMove(InventoryStatusEnum.CGTRANS, matchConfig, assBomDetail, inventoryMove, outDto);
                                            }
                                        } else {
                                            throw Exceptions.OpsException("获取供应商失败。", inventoryMove.getSupplierid());
                                        }
                                    }
                                }
                            }else {//ppl 不判日期
                                AssBomQtyForMove(InventoryStatusEnum.CGTRANS, matchConfig, assBomDetail, inventoryMove, outDto);
                            }
                        }
                    }
                    //调拨在途
                    if (InventoryStatusEnum.DBTRANS.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                        List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, productBomDetail.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                        for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                            if (assBomDetail.IsEnough()) {
                                break;
                            }
                            if (matchConfig.getMathchPreRecDate()) {
                                if (Objects.nonNull(inventoryMove.getPrereceivedate())) {
                                    if (inventoryMove.getPrereceivedate().getTime() - inputDto.getHopeDate().getTime() <= 0) {
                                        AssBomQtyForMove(InventoryStatusEnum.DBTRANS, matchConfig, assBomDetail, inventoryMove, outDto);
                                    }
                                }
                            }else {
                                AssBomQtyForMove(InventoryStatusEnum.DBTRANS, matchConfig, assBomDetail, inventoryMove, outDto);
                            }
                        }
                    }
                }
                if (!assBomDetail.IsEnough()) {
                    enough = false;
                    break;
                }
                assBomDetailList.add(assBomDetail);
            }
            if (enough) {
                return assBomDetailList;
            }else {
                //因为拆分型号分库优先级高于大库
                outDto.getInventoryMapSorted().clear();
                outDto.getWarehouseCodeSets().clear();
                outDto.getRiskInvMap().clear();
            }
        }
        return null;
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/10/3 9:32
     * @description：按照库存属性---》库房遍历出库,拆分型号规则
     */
    @Override
    public void PHAssModelNoInvenTory(InventoryCkByOrderInputDto inputDto,
                                      List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList,
                                      InventoryCkByOrderOutDto outDto,BomSelectResult bom) throws OpsException {


        List<AssBom> assBomList = new ArrayList<>();
        boolean bomEnough = false;
        for (BomVersions bomVersion : bom.getBomVersionsList()) {
            List<AssBomDetail> assBomDetailList = PHASSModelNoForBom(inputDto, opsInventoryRuleConfigItemList, bomVersion,outDto);
            AssBom assBom = new AssBom();
            assBom.setProductBom(bomVersion.getProductBom());
            assBom.setDetailList(assBomDetailList);
            assBom.setQty(inputDto.getQuantity() - inputDto.getAllotQuantity());
            assBomList.add(assBom);
            if (assBom.getEnough()) {
                bomEnough = true;
                outDto.setAssBom(assBom);
                //是否为特殊bom bugid:17799
                outDto.setSpecialBom(bom.getSpecialBom());
                inputDto.addAllotQuantity(inputDto.getQuantity() - inputDto.getAllotQuantity());
                break;
            }
        }
        if (!bomEnough) {
            if (!assBomList.get(0).getProductBom().getPriorityComplete()) {
                outDto.setAssBom(assBomList.get(0));
                //是否为特殊bom bugid:17799
                outDto.setSpecialBom(bom.getSpecialBom());
            } else {
                outDto.setDoSourceEnum(DoSourceEnum.CG);
            }
        }

    }

    /**
     * @param inputDto
     * @param opsInventoryRuleConfigItemList
     * @param outDto
     * @author ：c02483
     * @date ：Created in 2021/10/3 9:32
     * @description：按照库房---》库存属性遍历出库,整出规则
     */
    @Override
    public void HPUnAssInvenTory(InventoryCkByOrderInputDto inputDto, List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList,
                                 List<OpsWarehouseSalesbranchConfig> salesbranchConfigList, InventoryCkByOrderOutDto outDto) throws OpsException {
        for (OpsWarehouseSalesbranchConfig salesbranchConfig : salesbranchConfigList) {
            for (OpsInventoryRuleConfigItem ruleConfigItem : opsInventoryRuleConfigItemList) {
                OpsInventoryMatchConfig matchConfig = baseInventoryService.getOpsInventoryMatchConfig(ruleConfigItem.getInventoryMatchCode());
                //bugid:19127
                // 获取库存属性 bugid:20641
                List<OpsInventoryProperty>  inventoryPropertyList = baseInventoryService.getInvProperty(matchConfig, inputDto, inputDto.getModelno());
                if(CollectionUtils.isEmpty(inventoryPropertyList)){
                    continue;
                }
                if (InventoryStatusEnum.NORMAL.getCode().equals(matchConfig.getMatchInventoryStatus())){
                    List<OpsInventory> inventoryList = baseInventoryService.getOpsInventoryListV3(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getInvMap());
                    for (OpsInventory inventory : inventoryList) {
                        int unAllotQuantity = inputDto.getQuantity() - inputDto.getAllotQuantity();//未分数量
                        //风险在库 库存数量大于风险在库数量
                        boolean invQtyBig = true;
                        if(matchConfig.getMatchInventoryRisk()){
                            // 判断风险和库存的数量比较
                            List<RiskDto> riskDtos = inputDto.getRiskMap().get(inputDto.getModelno());
                            for (RiskDto riskDto:riskDtos){
                                if(riskDto.getInventoryId().equals(inventory.getInventoryId())){
                                    int invQty = inventory.getQuantity() - inventory.getPrepareQuantity() ;
                                    int riskQty = riskDto.getQuantity() - riskDto.getPrepareQuantity();
                                    // 当风险在库数量小于库存数量时
                                    if(invQty > riskQty){
                                        invQtyBig = false;
                                        if (riskQty >= unAllotQuantity) {
                                            outDto.getInventoryMap().put(inventory, unAllotQuantity);
                                            outDto.addMapSorted(inventory);
                                            outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                            inputDto.addAllotQuantity(unAllotQuantity);
                                            // 风险在库标识
                                            outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                        if (!invQtyBig){
                            continue;
                        }

                        boolean bigOrderjudger = BigOrderForWareHose(InventoryStatusEnum.NORMAL, matchConfig, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), unAllotQuantity, inventory.getQuantity() - inventory.getPrepareQuantity());
                        if (bigOrderjudger) {
                            continue;
                        }
                        if ((inventory.getQuantity() - inventory.getPrepareQuantity()) >= unAllotQuantity) {
                            outDto.getInventoryMap().put(inventory, unAllotQuantity);
                            outDto.addMapSorted(inventory);
                            outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                            inputDto.addAllotQuantity(unAllotQuantity);
                            if(matchConfig.getMatchInventoryRisk()){
                                // 风险在库标识
                                outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                            }
                            return;
                        }
                    }
                }
                //生产在途
                if (InventoryStatusEnum.PRODUCE.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (inputDto.isAllotStatus()) {
                            return;
                        }
                        UnAssForMove(InventoryStatusEnum.PRODUCE, inputDto, matchConfig, inventoryMove, outDto);
                    }
                }
                //到货未入库
                if (InventoryStatusEnum.W.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (inputDto.isAllotStatus()) {
                            return;
                        }
                        UnAssForMove(InventoryStatusEnum.W, inputDto, matchConfig, inventoryMove, outDto);
                    }
                }
                //采购在途
                if (InventoryStatusEnum.CGTRANS.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (inputDto.isAllotStatus()) {
                            return;
                        }
                        if (matchConfig.getMathchPreRecDate()) {//
                            if (Objects.nonNull(inventoryMove.getPrereceivedate())) {
                                //bugid 8926 20221207 c14717 客户订单交货期 <= 在途库存的预到货日期，可使用在途库存
                                if (inputDto.getHopeDate().getTime() - inventoryMove.getPrereceivedate().getTime() <= 0) {
                                    UnAssForMove(InventoryStatusEnum.CGTRANS, inputDto, matchConfig, inventoryMove, outDto);
                                } else {
                                    //CommonResult<Supplier> commonResult = opsSupplierFeignApi.getSupplierInfo(inventoryMove.getSupplierid());
                                    Supplier supplier = supplierMapper.selectByPrimaryKey(inventoryMove.getSupplierid());
                                    if (Objects.nonNull(supplier)) {
                                        //Supplier supplier =  commonResult.getData();
                                        if (inventoryMove.getPrereceivedate().getTime() - (new Date()).getTime()
                                                <= supplier.getFstdeliveryday() * UNIT_Day) {
                                            UnAssForMove(InventoryStatusEnum.CGTRANS, inputDto, matchConfig, inventoryMove, outDto);
                                        }
                                    } else {
                                        throw Exceptions.OpsException("获取供应商失败。", inventoryMove.getSupplierid());
                                    }
                                }
                            }
                        } else{//ppl 不判断日期
                            UnAssForMove(InventoryStatusEnum.CGTRANS, inputDto, matchConfig, inventoryMove, outDto);
                        }
                    }
                }
                //调拨在途
                if (InventoryStatusEnum.DBTRANS.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (inputDto.isAllotStatus()) {
                            return;
                        }
                        if (matchConfig.getMathchPreRecDate()) {
                            if (Objects.nonNull(inventoryMove.getPrereceivedate())) {
                                if (inventoryMove.getPrereceivedate().getTime() - inputDto.getHopeDate().getTime() <= 0) {
                                    UnAssForMove(InventoryStatusEnum.DBTRANS, inputDto, matchConfig, inventoryMove, outDto);
                                }
                            }
                        }else{//ppl 不看日期
                            UnAssForMove(InventoryStatusEnum.DBTRANS, inputDto, matchConfig, inventoryMove, outDto);
                        }
                    }
                }
            }
        }

    }

    /**
     * @param inputDto
     * @param opsInventoryRuleConfigItemList
     * @param outDto
     * @author ：c02483
     * @date ：Created in 2021/10/3 9:32
     * @description：按照库房---》库存属性遍历出库,拆分数量规则
     */
    @Override
    public void HPAssQtyInvenTory(InventoryCkByOrderInputDto inputDto, List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList, List<OpsWarehouseSalesbranchConfig> salesbranchConfigList, InventoryCkByOrderOutDto outDto) throws OpsException {
        for (OpsWarehouseSalesbranchConfig salesbranchConfig : salesbranchConfigList) {
            for (OpsInventoryRuleConfigItem ruleConfigItem : opsInventoryRuleConfigItemList) {
                OpsInventoryMatchConfig matchConfig = baseInventoryService.getOpsInventoryMatchConfig(ruleConfigItem.getInventoryMatchCode());
                //bugid:19127
                // 获取库存属性 bugid:20641
                List<OpsInventoryProperty>  inventoryPropertyList = baseInventoryService.getInvProperty(matchConfig, inputDto, inputDto.getModelno());
                if(CollectionUtils.isEmpty(inventoryPropertyList)){
                    continue;
                }
                if (InventoryStatusEnum.NORMAL.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventory> inventoryList = baseInventoryService.getOpsInventoryListV3(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getInvMap());
                    outerLoop: // 跳过已经出的库存 GK-TY先于风险在库，解决既包含GK-TY同时也是风险的问题
                    for (OpsInventory inventory : inventoryList) {
                        if (Objects.nonNull(outDto.getInventoryMap())){
                            for (Map.Entry<OpsInventory, Integer> entry : outDto.getInventoryMap().entrySet()){
                                if(inventory.getInventoryId().equals(entry.getKey().getInventoryId())){
                                    continue outerLoop;
                                }
                            }
                        }
                        int unAllotQuantity = inputDto.getQuantity() - inputDto.getAllotQuantity();

                        //风险在库 库存数量大于风险在库数量
                        boolean invQtyBig = true;
                        if(matchConfig.getMatchInventoryRisk()){
                            // 判断风险和库存的数量比较
                            List<RiskDto> riskDtos = inputDto.getRiskMap().get(inputDto.getModelno());
                            for (RiskDto riskDto:riskDtos){
                                if(riskDto.getInventoryId().equals(inventory.getInventoryId())){
                                    int invQty = inventory.getQuantity() - inventory.getPrepareQuantity() ;
                                    int riskQty = riskDto.getQuantity() - riskDto.getPrepareQuantity();
                                    // 当风险在库数量小于库存数量时
                                    if(invQty > riskQty){
                                        invQtyBig = false;
                                        if (riskQty >= unAllotQuantity) {
                                            outDto.getInventoryMap().put(inventory, unAllotQuantity);
                                            outDto.addMapSorted(inventory);
                                            outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                            inputDto.addAllotQuantity(unAllotQuantity);
                                            // 风险在库标识
                                            outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                                            return;
                                        } else {
                                            outDto.getInventoryMap().put(inventory, riskQty);
                                            outDto.addMapSorted(inventory);
                                            outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                                            inputDto.addAllotQuantity(riskQty);
                                            // 风险在库标识
                                            outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                                        }
                                    }
                                }
                            }
                        }
                        if (!invQtyBig){
                            continue;
                        }
                        boolean bigOrderjudger = BigOrderForWareHose(InventoryStatusEnum.NORMAL, matchConfig, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), unAllotQuantity, inventory.getQuantity() - inventory.getPrepareQuantity());
                        if (bigOrderjudger) {
                            continue;
                        }
                        if ((inventory.getQuantity() - inventory.getPrepareQuantity()) >= unAllotQuantity) {
                            outDto.getInventoryMap().put(inventory, unAllotQuantity);
                            outDto.addMapSorted(inventory);
                            outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                            inputDto.addAllotQuantity(unAllotQuantity);
                            if(matchConfig.getMatchInventoryRisk()){
                                // 风险在库标识
                                outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                            }
                            return;
                        } else if (inventory.getQuantity() - inventory.getPrepareQuantity() < unAllotQuantity) {
                            outDto.getInventoryMap().put(inventory, inventory.getQuantity() - inventory.getPrepareQuantity());
                            outDto.addMapSorted(inventory);
                            outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());
                            inputDto.addAllotQuantity(inventory.getQuantity() - inventory.getPrepareQuantity());
                            if(matchConfig.getMatchInventoryRisk()){
                                // 风险在库标识
                                outDto.getRiskInvMap().put(inventory.getInventoryId(), true);
                            }
                        }
                    }
                }
                //生产在途
                if (InventoryStatusEnum.PRODUCE.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        ASSQtyForMove(InventoryStatusEnum.PRODUCE, inventoryMove, matchConfig, inputDto, outDto);
                    }
                }
                //到货未入库
                if (InventoryStatusEnum.W.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        ASSQtyForMove(InventoryStatusEnum.W, inventoryMove, matchConfig, inputDto, outDto);
                    }
                }
                //采购在途
                if (InventoryStatusEnum.CGTRANS.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (matchConfig.getMathchPreRecDate()) {
                            if (Objects.nonNull(inventoryMove.getPrereceivedate())) {
                                //bugid 8926 20221207 c14717 客户订单交货期 <= 在途库存的预到货日期，可使用在途库存
                                if (inputDto.getHopeDate().getTime() - inventoryMove.getPrereceivedate().getTime()  <= 0) {
                                    ASSQtyForMove(InventoryStatusEnum.CGTRANS, inventoryMove, matchConfig, inputDto, outDto);
                                } else {
                                    //CommonResult<Supplier> commonResult = opsSupplierFeignApi.getSupplierInfo(inventoryMove.getSupplierid());
                                    Supplier supplier = supplierMapper.selectByPrimaryKey(inventoryMove.getSupplierid());
                                    if (Objects.nonNull(supplier)) {
                                        //Supplier supplier =  commonResult.getData();
                                        if (inventoryMove.getPrereceivedate().getTime() - (new Date()).getTime()
                                                <= supplier.getFstdeliveryday() * UNIT_Day) {
                                            ASSQtyForMove(InventoryStatusEnum.CGTRANS, inventoryMove, matchConfig, inputDto, outDto);
                                        }
                                    } else {
                                        throw Exceptions.OpsException("获取供应商失败。", inventoryMove.getSupplierid());
                                    }
                                }
                            }
                        }else{
                            ASSQtyForMove(InventoryStatusEnum.CGTRANS, inventoryMove, matchConfig, inputDto, outDto);
                        }
                    }
                }
                //调拨在途
                if (InventoryStatusEnum.DBTRANS.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (matchConfig.getMathchPreRecDate()) {
                            if (Objects.nonNull(inventoryMove.getPrereceivedate())) {
                                if (inventoryMove.getPrereceivedate().getTime() - inputDto.getHopeDate().getTime() <= 0) {
                                    ASSQtyForMove(InventoryStatusEnum.DBTRANS, inventoryMove, matchConfig, inputDto, outDto);
                                }
                            }
                        }else {// ppl 不看日期
                            ASSQtyForMove(InventoryStatusEnum.DBTRANS, inventoryMove, matchConfig, inputDto, outDto);
                        }
                    }
                }
            }
            if (!inputDto.isAllotStatus()) {
                inputDto.setAllotQuantity(0);
                outDto.getInventoryMoveMap().clear();
                outDto.getInventoryMap().clear();
                outDto.getInventoryMapSorted().clear();
                outDto.getWarehouseCodeSets().clear();//bug号 8510 20221028 C14717
                outDto.getRiskInvMap().clear();
            }
        }
    }

    /**
     * @param inputDto
     * @param opsInventoryRuleConfigItemList
     * @param outDto
     * @author ：c02483
     * @date ：Created in 2021/10/3 9:32
     * @description：按照库房---》库存属性遍历出库,拆分型号规则
     */
    @Override
    public void HPAssModelNoInvenTory(InventoryCkByOrderInputDto inputDto, List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList,
                                      List<OpsWarehouseSalesbranchConfig> salesbranchConfigList, InventoryCkByOrderOutDto outDto,BomSelectResult bom) throws OpsException {
        for (BomVersions bomVersion : bom.getBomVersionsList()) {
            List<AssBomDetail> assBomDetailList = HPASSModelNoForBom(inputDto, opsInventoryRuleConfigItemList, salesbranchConfigList, bomVersion,outDto);
            if (Objects.isNull(assBomDetailList)) {
                continue;
            }
            AssBom assBom = new AssBom();
            assBom.setProductBom(bomVersion.getProductBom());
            assBom.setDetailList(assBomDetailList);
            assBom.setQty(inputDto.getQuantity() - inputDto.getAllotQuantity());
            if (assBom.getEnough()) {
                outDto.setAssBom(assBom);
                //是否为特殊bom bugid:17799
                outDto.setSpecialBom(bom.getSpecialBom());
                inputDto.addAllotQuantity(inputDto.getQuantity() - inputDto.getAllotQuantity());
                break;
            }
        }
    }

    /**
     * bugid:17319 c14717 20250401
     * 根据型号提前加载库存
     * @param inputDto
     * @param modelNo
     */
    @Override
    public void getInvByModelNO(InventoryCkByOrderInputDto inputDto, String modelNo){
        List<OpsInventory> allInvList = opsInventoryDao.getAllModelNoInvListDao(modelNo);
        for(OpsInventory inv : allInvList) {
            Long hashCodeKey = HashCodeUtil.hashCodeInventoryKey(inv.getInventoryPropertyId(), inv.getModelno(), inv.getWarehouseCode(), inv.getInventoryStatus());
            if(CollectionUtils.isEmpty(inputDto.getInvMap().get(hashCodeKey))){
                List<OpsInventory> list = new ArrayList<>();
                list.add(inv);
                inputDto.getInvMap().put(hashCodeKey,list);
            }else {
                inputDto.getInvMap().get(hashCodeKey).add(inv);
            }
        }
        if(CollectionUtils.isNotEmpty(allInvList)){
            //  提取 InvPropertyIds 列表
            List<Long> invPids = allInvList.stream()
                    .map(OpsInventory::getInventoryPropertyId)
                    .distinct()
                    .collect(Collectors.toList());
            List<OpsInventoryProperty> invPropertyList = inventoryRiskDao.getInvProperty(invPids);
            inputDto.getInvPropertyMap().put(modelNo,invPropertyList);
            Map<Long, String> invProperTyMap = invPropertyList.stream()
                    .collect(Collectors.toMap(
                            OpsInventoryProperty::getInventoryPropertyId,       // Key: InventoryPropertyId
                            OpsInventoryProperty::getInventoryTypeCode // Value: InventoryTypeCode
                            , (oldValue, newValue) -> oldValue
                    ));

            // 风险在库数据  提取 InvIds 列表
            List<Long> invIds = allInvList.stream()
                    .map(OpsInventory::getInventoryId)
                    .collect(Collectors.toList());
            List<RiskDto> invRiskList = inventoryRiskDao.getInvRisk(invIds);
            if (CollectionUtils.isNotEmpty(invRiskList)){
                Map<Long, Long> invMap = allInvList.stream()
                        .collect(Collectors.toMap(
                                OpsInventory::getInventoryId,       // Key: InventoryId
                                OpsInventory::getInventoryPropertyId, // Value: InventoryPropertyId
                                (oldValue, newValue) -> oldValue
                        ));
                for(RiskDto riskDto : invRiskList){
                    Long invPropertyId = invMap.get(riskDto.getInventoryId());
                    String InventoryTypeCode = invProperTyMap.get(invPropertyId);
                    riskDto.setInventoryTypeCode(InventoryTypeCode);
                    riskDto.setInventoryPropertyId(invPropertyId);
                }
                inputDto.getRiskMap().put(modelNo,invRiskList);
            }
        }
    }

    @Override
    public void getMoveInvByModelNO(InventoryCkByOrderInputDto inputDto, String modelNo){
        // 签收仓作为订单分配出库存
        List<OpsInventoryMove> allInvList = opsInventoryDao.getAllModelNoMoveInvListDao(modelNo);
        for(OpsInventoryMove inv : allInvList) {
            Long hashCodeKey = HashCodeUtil.hashCodeInventoryKey(inv.getInventoryPropertyId(), inv.getModelno(), inv.getWarehouseCode(), inv.getInventoryStatus());
            if(CollectionUtils.isEmpty(inputDto.getMoveInvMap().get(hashCodeKey))){
                List<OpsInventoryMove> list = new ArrayList<>();
                list.add(inv);
                inputDto.getMoveInvMap().put(hashCodeKey,list);
            }else {
                inputDto.getMoveInvMap().get(hashCodeKey).add(inv);
            }
        }
        if(CollectionUtils.isNotEmpty(allInvList)){
            //  提取 InvPropertyIds 列表
            List<Long> invPids = allInvList.stream()
                    .map(OpsInventoryMove::getInventoryPropertyId)
                    .distinct()
                    .collect(Collectors.toList());
            List<OpsInventoryProperty> invPropertyList = inventoryRiskDao.getInvProperty(invPids);
            if(CollectionUtils.isNotEmpty(invPropertyList)){
                if(CollectionUtils.isEmpty(inputDto.getInvPropertyMap().get(modelNo))){
                    inputDto.getInvPropertyMap().put(modelNo,invPropertyList);
                }else {
                    inputDto.getInvPropertyMap().get(modelNo).addAll(invPropertyList);
                    // 去重
                    inputDto.getInvPropertyMap().get(modelNo).stream()
                            .distinct() // 依赖对象的 equals/hashCode 进行去重
                            .collect(Collectors.toList());
                }
            }
        }
    }

    /**
     * bugid:17319 c14717 20250401
     * 订单分配出库规则出拆分型号时，根据加载bomdetail型号的库存
     * @param inputDto
     * @throws OpsException
     */
    public void getAssModelNoInvByModelNO(InventoryCkByOrderInputDto inputDto,BomSelectResult bom) throws OpsException{
        if(!inputDto.getAssModelRule()){
            inputDto.setAssModelRule(true);
            for(BomVersions bv : bom.getBomVersionsList()){
                for(ProductBomDetail productBomDetail : bv.getBomDetails()){
                    getInvByModelNO(inputDto,productBomDetail.getModelno());
                    getMoveInvByModelNO(inputDto,productBomDetail.getModelno());
                }
            }
        }
    }

    /**
     * 判断最小包装单位 重新分配库存数量和采购数量
     *
     * @param modelNo
     *            型号
     * @return 返回采购数量
     */
    @Override
    public void minPack(String modelNo, InventoryCkByOrderInputDto inputDto, InventoryCkByOrderOutDto outDto)
            throws OpsException {
        Integer orderNum = inputDto.getQuantity();// 订单数量
        Integer ckNum = inputDto.getAllotQuantity();// 拆分数量
        // CommonResult<Product> result =
        // opsProductFeignApi.searchProduct(modelNo);//
        // 直接查表
        Product product = productDao.queryProductByModelNo(modelNo);
        if (Objects.isNull(product)) {
            throw Exceptions.OpsException("产品表无此型号");
        }
        if (product.getMinPackUnit() == null || product.getMinPackUnit() == 0) {
            // 不用操作
            return;
        }
        BindataExample example = new BindataExample();
        example.createCriteria().andDelflagEqualTo((short) 0).andModelnoEqualTo(modelNo).andStocktypeEqualTo(1);
        // List<Bindata> list = bindataMapper.selectByExample(example);
        long isBin = bindataMapper.countByExample(example);

        if (isBin > 0) {// bin 向上取整
            // （(订单数量 - 在库数量) /最小包装数量）* 最小包装数量
            int cgNum = (int) Math.ceil((orderNum - ckNum) / (product.getMinPackUnit() * 1.00))
                    * product.getMinPackUnit();
            ckInventoryChange(outDto, inputDto, cgNum);
        } else {// 非bin
            // 返回采购数量 （在库数量/最小包装）* 最小包装
            int cgNum = orderNum - (int) ((ckNum / product.getMinPackUnit()) * product.getMinPackUnit());
            ckInventoryChange(outDto, inputDto, cgNum);
        }
    }

    /**
     * 根据最小包装单位变更拆分数量
     *
     * @param cgNum
     *            应采购数量
     */
    private void ckInventoryChange(InventoryCkByOrderOutDto outDto, InventoryCkByOrderInputDto inputDto,
                                   Integer cgNum) {

        if (!DoSourceEnum.ASSModelNo.equals(outDto.getDoSourceEnum())
                && inputDto.getQuantity() > inputDto.getAllotQuantity()) {
            // 应拆分数量，订单数量减采购数量
            int ckNum = inputDto.getQuantity() - cgNum;
            int kjNum = 0;// 扣减出库数量
            if (ckNum > 0) {
                kjNum = inputDto.getAllotQuantity() - ckNum;
            } else {
                kjNum = inputDto.getAllotQuantity();
            }
            // 减消当前拆分接口的库存

            if (!outDto.getInventoryMapSorted().isEmpty()) {
                int inventoryMapSortedSize = outDto.getInventoryMapSorted().size();
                for (int i = 0; i < inventoryMapSortedSize; i++) {
                    Optional<Map.Entry<Object, Integer>> max0 = outDto.getInventoryMapSorted().entrySet().stream()
                            .max(Map.Entry.comparingByValue());// 取出最大值
                    if (!Objects.isNull(outDto.getInventoryMoveMap().get(max0.get().getKey()))) {// 在途
                        int alreadyDistributionNum = outDto.getInventoryMoveMap()
                                .get((OpsInventoryMove) max0.get().getKey());// 当前inventroyId已分配数量
                        if (alreadyDistributionNum - kjNum > 0) {// 消减数量小于等于当前库存直接扣减
                            outDto.getInventoryMoveMap().put((OpsInventoryMove) max0.get().getKey(),
                                    alreadyDistributionNum - kjNum);
                            inputDto.setAllotQuantity(inputDto.getAllotQuantity() - kjNum);
                            break;
                        } else if (alreadyDistributionNum - kjNum == 0) {
                            outDto.getInventoryMoveMap().remove((OpsInventoryMove) max0.get().getKey());
                            outDto.getInventoryMapSorted().remove((OpsInventoryMove) max0.get().getKey());
                            // outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());//重复的
                            // 不扣减
                            inputDto.setAllotQuantity(inputDto.getAllotQuantity() - kjNum);
                            break;
                        } else {
                            kjNum = kjNum - alreadyDistributionNum;
                            outDto.getInventoryMoveMap().remove((OpsInventoryMove) max0.get().getKey());
                            outDto.getInventoryMapSorted().remove((OpsInventoryMove) max0.get().getKey());
                            // outDto.getWarehouseCodeSets().add(inventory.getWarehouseCode());//重复的
                            // 不扣减
                            inputDto.setAllotQuantity(inputDto.getAllotQuantity() - alreadyDistributionNum);
                        }
                    }
                    if (!Objects.isNull(outDto.getInventoryMap().get(max0.get().getKey()))) {// 在库
                        int alreadyDistributionNum = outDto.getInventoryMap().get((OpsInventory) max0.get().getKey());// 当前inventroyId已分配数量
                        if (alreadyDistributionNum - kjNum > 0) {// 消减数量小于等于当前库存直接扣减
                            outDto.getInventoryMap().put((OpsInventory) max0.get().getKey(),
                                    alreadyDistributionNum - kjNum);
                            inputDto.setAllotQuantity(inputDto.getAllotQuantity() - kjNum);
                            break;
                        } else if (alreadyDistributionNum - kjNum == 0) {
                            outDto.getInventoryMap().remove((OpsInventory) max0.get().getKey());
                            outDto.getInventoryMapSorted().remove(max0.get().getKey());
                            inputDto.setAllotQuantity(inputDto.getAllotQuantity() - kjNum);
                            break;
                        } else {
                            kjNum = kjNum - alreadyDistributionNum;

                            outDto.getInventoryMap().remove((OpsInventory) max0.get().getKey());
                            outDto.getInventoryMapSorted().remove(max0.get().getKey());
                            inputDto.setAllotQuantity(inputDto.getAllotQuantity() - alreadyDistributionNum);

                        }
                    }
                }
            }
        }
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/10/3 9:54
     * @description：返回库存分配结果 规则需要放缓存
     */
    public InventoryCkByOrderOutDto getOpsInventoryListByCk(InventoryCkByOrderInputDto inputDto) throws OpsException {
        InventoryCkByOrderOutDto outDto = new InventoryCkByOrderOutDto();
        //判断订单类型 直接采购
        if(StringUtils.isNotBlank(inputDto.getSpecmark()) && (!inputDto.getSpecmark().equals("0"))){//阀汇流板标识 不等于0直接采购
            return outDto;
        }
        if(OrderTypeEnum.DR.equals(inputDto.getOrderType()) || OrderTypeEnum.CR.equals(inputDto.getOrderType())){//DR CR直接采购
            return outDto;
        }
        //更加订单类型获取出库规则
        List<OpsOrderInventoryRuleConfig> orderInventoryRuleConfigList =
                baseInventoryService.getOrderRuleList(inputDto.getOrderType(), inputDto.getTag(), inputDto.getPropertyType(),inputDto.getCKType());
        if (CollectionUtils.isEmpty(orderInventoryRuleConfigList)) {
            throw Exceptions.OpsException("缺少库存规则调度配置orderType:" + inputDto.getOrderType(), inputDto.getOrderId(), inputDto.getOrderItem());
        }
        getInvByModelNO(inputDto,inputDto.getModelno());
        getMoveInvByModelNO(inputDto,inputDto.getModelno());
        List<InvCkTargetDto> targetList = new ArrayList<>();
        for (OpsOrderInventoryRuleConfig orderInventoryRuleConfig : orderInventoryRuleConfigList) {
            if (inputDto.isAllotStatus()){
                break;
            }
            //获取详细出库规则
            OpsInventoryRuleConfig ruleConfig = baseInventoryService.getOpsInventoryRuleConfigList(orderInventoryRuleConfig.getRuleCode());
            List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList = baseInventoryService.getInventoryRuleConfigItemList(orderInventoryRuleConfig.getRuleCode());
            //bugid:19146  规则一战到底，最后选择拆分数量优先去采购
            InventoryCkByOrderOutDto targetOutput = outDto.deepCopy();
            if(Objects.nonNull(targetOutput)){
                targetList.add(new InvCkTargetDto(inputDto.getAllotQuantity(),targetOutput));
            }
            clearCKRuleInvDate(outDto, inputDto);
            if ("PH".equals(ruleConfig.getRuleType())) {
                switch (ruleConfig.getRuleAssFlag()) {
                    case 0://不拆
                        PHUnAssInvenTory(inputDto, opsInventoryRuleConfigItemList, outDto);
                        if (inputDto.isAllotStatus()) {
                            outDto.setDoSourceEnum(DoSourceEnum.ALL);
                        }
                        break;
                    case 1://拆数量 不出在途 流程无
                        PHAssQtyInvenTory(inputDto, opsInventoryRuleConfigItemList, outDto);
                        if (inputDto.getAllotQuantity() > 0) {
                            outDto.setDoSourceEnum(DoSourceEnum.ASSQTY);
                        }
                        break;
                    case 2://拆型号(残余数量拆型号)
                        if (inputDto.getAllotQuantity() == 0) {
                            if (OrderSpecExpType.include(inputDto.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)){//整单组装不拆分
                                break;
                            }
                            // bugid:17799 c14717 20250617
                            getBom(inputDto);
                            BomSelectResult bom = inputDto.getBomReuslt();
                            //无bom 或 特殊bom不拆分
                            if(Objects.isNull(bom) || (bom.getSpecialBom() && !bom.getBomVersionsList().get(0).getBomType())){
                                break;
                            }
                            getAssModelNoInvByModelNO(inputDto,bom);
                            PHAssModelNoInvenTory(inputDto, opsInventoryRuleConfigItemList, outDto,bom);
                            if (Objects.nonNull(outDto.getAssBom())) {
                                outDto.setDoSourceEnum(DoSourceEnum.ASSModelNo);
                            }
                        }
                        break;
                    default://todo throw opsexception
                        break;
                }
            } else
                if ("HP".equals(ruleConfig.getRuleType())) {
                List<OpsWarehouseSalesbranchConfig> salesbranchConfigList = baseInventoryService.getBranchListBysalesBranchId(inputDto.getOrderType(),inputDto.getCustomer(),inputDto.getDeptno(), ruleConfig.getWarehouseTypeCode(),inputDto.getExpDlvType(),inputDto.getExpLinkNo());
                if (!CollectionUtils.isEmpty(salesbranchConfigList)) {
                    switch (ruleConfig.getRuleAssFlag()) {
                        case 0://不拆
                            HPUnAssInvenTory(inputDto, opsInventoryRuleConfigItemList, salesbranchConfigList, outDto);
                            if (inputDto.isAllotStatus()) {
                                outDto.setDoSourceEnum(DoSourceEnum.ALL);
                            }
                            break;
                        case 1://拆数量 不出在途 流程无
                            HPAssQtyInvenTory(inputDto, opsInventoryRuleConfigItemList, salesbranchConfigList, outDto);
                            if (inputDto.getAllotQuantity() > 0) {
                                outDto.setDoSourceEnum(DoSourceEnum.ASSQTY);
                            }
                            break;
                        case 2://拆型号(残余数量拆型号)，同库存性质的拆分型号 不出在途 流程无
                            if (inputDto.getAllotQuantity() == 0) {
                                if (OrderSpecExpType.include(inputDto.getExpDlvType(),OrderSpecExpType.WholeOrderToAssemble)){//整单组装不拆分
                                    break;
                                }
                                // bugid:17799 c14717 20250617
                                getBom(inputDto);
                                BomSelectResult bom = inputDto.getBomReuslt();
                                //无bom 或 特殊bom不拆分
                                if(Objects.isNull(bom) || (bom.getSpecialBom() && !bom.getBomVersionsList().get(0).getBomType())){
                                    break;
                                }
                                getAssModelNoInvByModelNO(inputDto,bom);
                                HPAssModelNoInvenTory(inputDto, opsInventoryRuleConfigItemList, salesbranchConfigList, outDto, bom);
                                if (Objects.nonNull(outDto.getAssBom())) {
                                    outDto.setDoSourceEnum(DoSourceEnum.ASSModelNo);
                                }
                            }
                            break;
                        default:// todo opsexception
                            break;
                    }
                }
            } else {
                throw Exceptions.OpsException("ruleConfig配置异常", ruleConfig.toString());
            }
            if (inputDto.getQuantity().equals(inputDto.getAllotQuantity())) {
                break;
            }
        }
        //bugid 19146  结果优选 不满足库存 ，则对比这一次和上一次做个优选；
        return optimalCKRuleInvDate(outDto,inputDto, targetList);
        // return outDto;
    }

    /**
     * 出库存优选
     * @param outDto
     * @param inputDto
     * @param list
     * @return
     */
    public InventoryCkByOrderOutDto optimalCKRuleInvDate(InventoryCkByOrderOutDto outDto
            , InventoryCkByOrderInputDto inputDto, List<InvCkTargetDto> list){
        // 库存满足
        if(inputDto.isAllotStatus()){
            return outDto;
        }
        // 选择整型号最大出库规则，出库存
        if(CollectionUtils.isNotEmpty(list)){
            list.sort(new Comparator<InvCkTargetDto>() {// 由大到小排序
                @Override
                public int compare(InvCkTargetDto o1, InvCkTargetDto o2) {
                    Integer target1 = o1.getAllotQuantity();
                    Integer target2 = o2.getAllotQuantity();
                    if(Objects.isNull(target1)){
                        target1 = 0;
                    }
                    if(Objects.isNull(target2)){
                        target2 = 0;
                    }
                    return target2.compareTo(target1);
                }
            });
            for(InvCkTargetDto obj : list){
                if(Objects.nonNull(obj.getAllotQuantity()) && obj.getAllotQuantity() > 0){
                    inputDto.setAllotQuantity(obj.getAllotQuantity());
                    return obj.getOutDto();
                }
            }
        }
        //todo 暂无规则 选择子型号采购或整型号采购
        /*if(CollectionUtils.isNotEmpty(list)){
            list.sort(new Comparator<InvCkTargetDto>() {// 由大到小排序
                @Override
                public int compare(InvCkTargetDto o1, InvCkTargetDto o2) {
                    Integer target1 = 0;
                    Integer target2 = 0;
                    if(Objects.nonNull(o1.getOutDto().getAssBom())){ //List<AssBomDetail> detailList
                        if(CollectionUtils.isNotEmpty(o1.getOutDto().getAssBom().getDetailList())){
                            for(AssBomDetail detail : o1.getOutDto().getAssBom().getDetailList()){
                                Integer assAllotQty = detail.getAssAllotQty();
                                if(Objects.isNull(assAllotQty)){
                                    assAllotQty = 0;
                                }
                                target1 = target1 + assAllotQty;
                            }
                        }
                    }
                    if(Objects.nonNull(o2.getOutDto().getAssBom())){ //List<AssBomDetail> detailList
                        if(CollectionUtils.isNotEmpty(o2.getOutDto().getAssBom().getDetailList())){
                            for(AssBomDetail detail : o2.getOutDto().getAssBom().getDetailList()){
                                Integer assAllotQty = detail.getAssAllotQty();
                                if(Objects.isNull(assAllotQty)){
                                    assAllotQty = 0;
                                }
                                target2 = target2 + assAllotQty;
                            }
                        }
                    }
                    return target2.compareTo(target1);
                }
            });
            for(InvCkTargetDto obj : list){
                if(Objects.nonNull(obj.getOutDto().getAssBom()) && obj.getInputDto().getAllotQuantity() > 0){
                    inputDto.setAllotQuantity(obj.getInputDto().getAllotQuantity());
                    return BeanCopyUtil.copy(obj.getOutDto(), InventoryCkByOrderOutDto.class);
                }
            }
        }*/

        return outDto; // 无变化
    }



    /**
     * bugid:17799
     * bugid: 20641 订单分配一次仅获取一次BOM
     * @return
     * @throws OpsException
     */
    public void getBom(InventoryCkByOrderInputDto inputDto) throws OpsException{
        if (!inputDto.getBomFlag()){
            inputDto.setBomFlag(true);
            BomSelectParam param = new BomSelectParam();
            param.setCustomerCode(inputDto.getEndUserNo());
            param.setModelNo(inputDto.getModelno());
            ResultVo<BomSelectResult> resultVo = bomSelectorService.selectBom(param);
            if(resultVo.isSuccess()){
                inputDto.setBomReuslt(resultVo.getData());
            }else if (BomSelectEnum.NONE.getCode().equals(resultVo.getCode())){

            }else {
                throw Exceptions.OpsException(resultVo.getMessage());
            }
        }
    }

    /**
     * 不满足出库存是，每次都要清空累计量，最后优选采购
     * @param outDto
     * @return
     */
    public void clearCKRuleInvDate(InventoryCkByOrderOutDto outDto, InventoryCkByOrderInputDto inputDto){

        // 出型号拆分规则
        if (Objects.nonNull(outDto.getAssBom())){
            if(!outDto.getAssBom().getEnough()){
                //清空拆分型号的信息
                inputDto.setAllotQuantity(0);
                outDto.getInventoryMapSorted().clear();
                outDto.getWarehouseCodeSets().clear();
                outDto.setAssBom(null);
                outDto.getRiskInvMap().clear();
            }
        }else {
            if (!inputDto.isAllotStatus()) {
                inputDto.setAllotQuantity(0);
                outDto.getInventoryMoveMap().clear();
                outDto.getInventoryMap().clear();
                outDto.getInventoryMapSorted().clear();
                outDto.getWarehouseCodeSets().clear();
                outDto.getRiskInvMap().clear();
            }
        }
        outDto.setDoSourceEnum(null);
    }
    /**
     * 获取可转定库存列表
     * @param inputDto
     * @return
     */
    @Override
    public void getOpsInventoryListByCkToZD(InventoryCkByOrderInputDto inputDto,ZDInventoryCkByOrderOutDto outDto) throws OpsException{
        //判断订单类型 直接采购
        if(StringUtils.isNotBlank(inputDto.getSpecmark()) && (!inputDto.getSpecmark().equals("0"))){//阀汇流板标识 不等于0直接采购
            throw Exceptions.OpsException("阀汇流板标识不能转定");
        }
        if(OrderTypeEnum.DR.equals(inputDto.getOrderType()) || OrderTypeEnum.CR.equals(inputDto.getOrderType())){//DR CR直接采购
            throw Exceptions.OpsException("DR和CR订单不能转定");
        }
        //更加订单类型获取出库规则
        List<OpsOrderInventoryRuleConfig> orderInventoryRuleConfigList =
                baseInventoryService.getOrderRuleList(inputDto.getOrderType(), inputDto.getTag(), inputDto.getPropertyType(),inputDto.getCKType());
        if (CollectionUtils.isEmpty(orderInventoryRuleConfigList)) {
            throw Exceptions.OpsException("缺少库存规则调度配置orderType:" + inputDto.getOrderType(), inputDto.getOrderId(), inputDto.getOrderItem());
        }
        for (OpsOrderInventoryRuleConfig orderInventoryRuleConfig : orderInventoryRuleConfigList) {
            //获取详细出库规则
            OpsInventoryRuleConfig ruleConfig = baseInventoryService.getOpsInventoryRuleConfigList(orderInventoryRuleConfig.getRuleCode());
            List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList = baseInventoryService.getInventoryRuleConfigItemList(orderInventoryRuleConfig.getRuleCode());
            if ("PH".equals(ruleConfig.getRuleType())) {
                switch (ruleConfig.getRuleAssFlag()) {
                    case 0://不拆
                        PHUnAssInvenToryToZD(inputDto, opsInventoryRuleConfigItemList, outDto);
                        break;
                    case 1://拆数量 不出在途 流程无
                        //不做处理
                        break;
                    case 2://拆型号(残余数量拆型号)
                        //不处理
                        break;
                    default:
                        break;
                }
            } else if ("HP".equals(ruleConfig.getRuleType())) {
                //不做处理
            } else {
                throw Exceptions.OpsException("ruleConfig配置异常", ruleConfig.toString());
            }
            if (inputDto.getQuantity().equals(inputDto.getAllotQuantity())) {
                break;
            }
        }
    }

    /**
     * 库房遍历出库,整出规则 转定用
     * @param inputDto
     * @param opsInventoryRuleConfigItemList
     * @param outDto
     * @throws OpsException
     */
    public void PHUnAssInvenToryToZD(InventoryCkByOrderInputDto inputDto, List<OpsInventoryRuleConfigItem> opsInventoryRuleConfigItemList,
                                     ZDInventoryCkByOrderOutDto outDto) throws OpsException{
        //bugid:16268 c14717 20250115
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "27");
        if (!dataTypeCodesInfo.isSuccess() || Objects.isNull(dataTypeCodesInfo.getData())) {
            throw Exceptions.OpsException("调用转定出分库开关失败");
        }
        if (!dataTypeCodesInfo.getData().getExtNote1().equals("0")
                && !dataTypeCodesInfo.getData().getExtNote1().equals("1")) {
            throw Exceptions.OpsException("调用转定出分库开关失败Code=:" + dataTypeCodesInfo.getData().getExtNote1());
        }
        //解决不包含分库流程 如货期单仓
        if (dataTypeCodesInfo.getData().getExtNote1().equals("1")){
            boolean subFlag = false;//包含分库
            for (OpsInventoryRuleConfigItem ruleConfigItem : opsInventoryRuleConfigItemList){
                if(ruleConfigItem.getInventoryMatchCode().contains("SUB")){
                    subFlag = true;
                }
            }
            if(!subFlag){
                String[] rules = new String[]{"SUB_PPL","SUB_PRO","SUB_EU","SUB_GROUP_EU","SUB_GM"
                        ,"SUB_PPL_FX","SUB_PRO_FX","SUB_EU_FX"}; //20671 添加分库规则 风险部分
                for(String rule : rules){
                    OpsInventoryRuleConfigItem item = new OpsInventoryRuleConfigItem();
                    item.setInventoryMatchCode(rule);
                    opsInventoryRuleConfigItemList.add(item);
                }
            }
        }
        for (OpsInventoryRuleConfigItem ruleConfigItem : opsInventoryRuleConfigItemList) {
            //获取更详细出库规则
            OpsInventoryMatchConfig matchConfig = baseInventoryService.getOpsInventoryMatchConfig(ruleConfigItem.getInventoryMatchCode());
            //获取库存属性
            // 获取库存属性 bugid:20641
            List<OpsInventoryProperty>  inventoryPropertyList = baseInventoryService.getInvProperty(matchConfig, inputDto, inputDto.getModelno());

            if(CollectionUtils.isEmpty(inventoryPropertyList)){
                continue;
            }
            List<OpsWarehouseSalesbranchConfig> salesbranchConfigList = new ArrayList<>();
            if (dataTypeCodesInfo.getData().getExtNote1().equals("0")) {
                salesbranchConfigList = baseInventoryService.getBranchListBysalesBranchId(inputDto.getOrderType(),inputDto.getCustomer(),inputDto.getDeptno(), matchConfig.getMatchWarehouseTypeCode(),inputDto.getExpDlvType(),inputDto.getExpLinkNo());
            }
            // 调用新删单接口
            if (dataTypeCodesInfo.getData().getExtNote1().equals("1")) {
                //bugid:16268 c14717 20241224 转定时 订单规则有分库时，出全部分库，不遵从营业所所属表。
                if(WarehouseTypeEnum.FDC.getHouseTypeCode().equals( matchConfig.getMatchWarehouseTypeCode())){
                    List<String> houseList = opsWarehouseService.findCodesByTypeCache(false,"", WarehouseTypeEnum.FDC.getHouseTypeCode(), false, "");//已经排除委托在库贩卖机
                    for(String w : houseList){
                        OpsWarehouseSalesbranchConfig o = new OpsWarehouseSalesbranchConfig();
                        o.setWarehouseCode(w);
                        salesbranchConfigList.add(o);
                    }
                }else {
                    //获取营业所和库房关联代码
                    salesbranchConfigList = baseInventoryService.getBranchListBysalesBranchId(inputDto.getOrderType(),inputDto.getCustomer(),inputDto.getDeptno(), matchConfig.getMatchWarehouseTypeCode(),inputDto.getExpDlvType(),inputDto.getExpLinkNo());
                }
            }

            if (CollectionUtils.isEmpty(salesbranchConfigList)) {
                continue;
            }
            //获取满足订单数量的仓库
            for (OpsWarehouseSalesbranchConfig salesbranchConfig : salesbranchConfigList) {
                if (InventoryStatusEnum.NORMAL.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventory> inventoryList = baseInventoryService.getOpsInventoryListV3(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getInvMap());
                    for (OpsInventory inventory : inventoryList) {

                        int unAllotQuantity = inputDto.getQuantity() - inputDto.getAllotQuantity();//未分数量
                        //风险在库 库存数量大于风险在库数量
                        boolean invQtyBig = true;
                        if(matchConfig.getMatchInventoryRisk()){
                            // 判断风险和库存的数量比较
                            List<RiskDto> riskDtos = inputDto.getRiskMap().get(inputDto.getModelno());
                            for (RiskDto riskDto:riskDtos){
                                if(riskDto.getInventoryId().equals(inventory.getInventoryId())){
                                    int invQty = inventory.getQuantity() - inventory.getPrepareQuantity() ;
                                    int riskQty = riskDto.getQuantity() - riskDto.getPrepareQuantity();
                                    // 当风险在库数量小于库存数量时
                                    if(invQty > riskQty){
                                        invQtyBig = false;
                                        if (riskQty >= unAllotQuantity) {
                                            inventory.setQuantity(riskDto.getQuantity());
                                            inventory.setPrepareQuantity(riskDto.getPrepareQuantity());
                                            outDto.setInvList(inventory,false, true);
                                        }
                                    }
                                }
                            }
                        }
                        if (!invQtyBig){
                            continue;
                        }
                        //判断大口订单
                        boolean bigOrderjudger = BigOrderForWareHose(InventoryStatusEnum.NORMAL, matchConfig, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), unAllotQuantity, inventory.getQuantity() - inventory.getPrepareQuantity());
                        if ((inventory.getQuantity() - inventory.getPrepareQuantity()) >= unAllotQuantity) {
                            outDto.setInvList(inventory,bigOrderjudger, matchConfig.getMatchInventoryRisk());
                        }
                    }
                }
                //生成在途
                if (InventoryStatusEnum.PRODUCE.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        UnAssForMoveToZD(InventoryStatusEnum.PRODUCE, inputDto, matchConfig, inventoryMove, outDto);
                    }
                }
                //到货未入库
                if (InventoryStatusEnum.W.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    //不出到货未入库 ops_code = 5的库存
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        //W 5 不出
                        if(Objects.nonNull(inventoryMove.getOptStatus()) && inventoryMove.getOptStatus().equals(5)){
                            continue;
                        }
                        UnAssForMoveToZD(InventoryStatusEnum.W, inputDto, matchConfig, inventoryMove, outDto);
                    }
                }
                //采购在途
                if (InventoryStatusEnum.CGTRANS.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    List<OpsInventoryMove> inventoryMoveList = baseInventoryService.getOpsInventoryMoveListV1(inventoryPropertyList, inputDto.getModelno(), salesbranchConfig.getWarehouseCode(), matchConfig.getMatchInventoryStatus(),inputDto.getMoveInvMap());
                    for (OpsInventoryMove inventoryMove : inventoryMoveList) {
                        if (matchConfig.getMathchPreRecDate()) {
                            if (Objects.nonNull(inventoryMove.getPrereceivedate())) {
                                //判断交货期和到货期 bugid 8926 20221207 c14717 客户订单交货期 <= 在途库存的预到货日期，可使用在途库存
                                if (inputDto.getHopeDate().getTime() - inventoryMove.getPrereceivedate().getTime() <= 0) {
                                    UnAssForMoveToZD(InventoryStatusEnum.CGTRANS, inputDto, matchConfig, inventoryMove, outDto);
                                } else {
                                    //CommonResult<Supplier> commonResult = opsSupplierFeignApi.getSupplierInfo(inventoryMove.getSupplierid());
                                    Supplier supplier = supplierMapper.selectByPrimaryKey(inventoryMove.getSupplierid());
                                    if (Objects.nonNull(supplier)) {
                                        //Supplier supplier = commonResult.getData();
                                        if (inventoryMove.getPrereceivedate().getTime() - (new Date()).getTime()
                                                <= supplier.getFstdeliveryday() * UNIT_Day) {
                                            UnAssForMoveToZD(InventoryStatusEnum.CGTRANS, inputDto, matchConfig, inventoryMove, outDto);
                                        }
                                    } else {
                                        throw Exceptions.OpsException("获取供应商失败。", inventoryMove.getSupplierid());
                                    }
                                }
                            }
                        }else{//ppl 直接出不判断日期
                            UnAssForMoveToZD(InventoryStatusEnum.CGTRANS, inputDto, matchConfig, inventoryMove, outDto);
                        }
                    }
                }
                //调拨在途
                if (InventoryStatusEnum.DBTRANS.getCode().equals(matchConfig.getMatchInventoryStatus())) {
                    //不出调拨在途
                }
            }
        }
    }
    //转定在途判断
    private void UnAssForMoveToZD(InventoryStatusEnum statusEnum, InventoryCkByOrderInputDto inputDto,
                                 OpsInventoryMatchConfig matchConfig, OpsInventoryMove inventoryMove, ZDInventoryCkByOrderOutDto outDto) throws OpsException {
        int unAllotQuantity = inputDto.getQuantity() - inputDto.getAllotQuantity();//未分数量
        boolean bigOrderjudger = BigOrderForWareHose(statusEnum, matchConfig, inputDto.getModelno(), inventoryMove.getWarehouseCode(), unAllotQuantity, inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity());
        if ((inventoryMove.getQuantity() - inventoryMove.getPrepareQuantity()) >= unAllotQuantity) {
            outDto.setInvMoveList(inventoryMove,bigOrderjudger);
        }

    }
}
