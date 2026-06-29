package com.sales.ops.serviceimpl.inventory;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.common.conf.OpsCommonConfig;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.InventoryRiskDao;
import com.sales.ops.db.extdao.OPSProductDao;
import com.sales.ops.db.extdao.OpsInventoryBinDao;
import com.sales.ops.db.extdao.OpsInventoryDao;
import com.sales.ops.dto.inventory.InventoryCkByOrderInputDto;
import com.sales.ops.dto.inventory.InventoryRiskDTO;
import com.sales.ops.dto.inventory.RiskDto;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.feign.OPSProductFeignApi;
import com.sales.ops.feign.inventory.OpsInventoryBaseFeignApi;
import com.sales.ops.service.ba.OpsWarehouseSalesbranchService;
import com.sales.ops.service.cache.CacheService;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.inventory.OpsInventoryTypeService;
import com.sales.ops.serviceimpl.annotation.InvLog;
import com.sales.ops.serviceimpl.annotation.Retryable;
import com.sales.ops.serviceimpl.utils.HashCodeUtil;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：c02483
 * @date ：Created in 2021/9/10 16:18
 * @description：库存基础服务
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseInventoryService {

    @Resource
    private OpsInventoryMapper opsInventoryMapper;
    @Resource
    private OpsInventoryMoveMapper opsInventoryMoveMapper;
    @Resource
    private OpsInventoryPropertyMapper opsInventoryPropertyMapper;
    @Resource
    private OpsInventoryPropertyService opsInventoryPropertyService;
    @Resource
    private OpsInventoryDao opsInventoryDao;
    @Resource
    private OpsWarehouseSalesbranchService warehouseSalesbranchService;
    @Resource
    private OPSProductFeignApi opsProductFeignApi;
    @Resource
    private OpsInventoryBaseFeignApi opsInventoryBaseFeignApi;
    @Resource
    private OpsInventoryTypeService opsInventoryTypeService;

    @Autowired
    private OPSProductDao productMapper;

    @Autowired
    private OpsInventoryRuleConfigMapper opsInventoryRuleConfigMapper;

    @Autowired
    private OpsOrderInventoryRuleConfigMapper opsOrderInventoryRuleConfigMapper;

    @Autowired
    private OpsInventoryRuleConfigItemMapper opsInventoryRuleConfigItemMapper;

    @Autowired
    private OpsInventoryMatchConfigMapper opsInventoryMatchConfigMapper;

    @Autowired
    private OpsCommonConfig opsCommonConfig;
    @Autowired
    private InventorySupplierMapper inventorySupplierMapper;
    @Autowired
    private OpsInventoryBinDao opsInventoryBinDao;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private InventoryRiskDao inventoryRiskDao;


    /**************** 库存的查询 **************************/
    /**
     * @description：根据在库库存ID查询库存
     * @author ：c02483
     * @date ：Created in 2021/11/8 12:03
     */
    public OpsInventory getInventoryById(Long id) {
        return opsInventoryMapper.selectByPrimaryKey(id);
    }

    /**
     * @description：根据在途库存ID查询库存
     * @author ：c02483
     * @date ：Created in 2021/11/8 12:03
     */
    public OpsInventoryMove getInventoryMoveById(Long id) {
        return opsInventoryMoveMapper.selectByPrimaryKey(id);
    }


    /**
     * @description 库存查询在库表 仓库、型号、属性ID、库存状态（默认在库）
     * @author C12961
     * @date 2022/3/10 16:12
     */
    public List<OpsInventory> findOpsInventory(OpsInventory inv) throws OpsException {
        OpsInventoryExample ex = new OpsInventoryExample();
        OpsInventoryExample.Criteria criteria = ex.createCriteria();
        criteria.andDelflagEqualTo(0);
        if (StrUtil.isNotBlank(inv.getWarehouseCode())) {
            criteria.andWarehouseCodeEqualTo(inv.getWarehouseCode());
        }
        if (StrUtil.isNotBlank(inv.getModelno())) {
            criteria.andModelnoEqualTo(inv.getModelno());
        }
        if (inv.getInventoryPropertyId() != null) {
            criteria.andInventoryPropertyIdEqualTo(inv.getInventoryPropertyId());
        }
        if (StrUtil.isNotBlank(inv.getInventoryStatus())) {
            criteria.andInventoryStatusEqualTo(inv.getInventoryStatus());
        } else {
            criteria.andInventoryStatusEqualTo(InventoryStatusEnum.NORMAL.getCode());
        }
        return opsInventoryMapper.selectByExample(ex);
    }

    /**
     * @description 库存查询在库表，指定批属性内容 仓库、型号、属性ID、库存状态（默认在库）
     * @author C12961
     * @date 2022/3/10 16:12
     */
    public List<OpsInventory> findOpsInventory(OpsInventory inv, OpsInventoryProperty property) throws OpsException {
        List<OpsInventory> invList = new ArrayList<>();
        List<OpsInventoryProperty> propertyList = opsInventoryPropertyService.matchByExample(property);
        if (propertyList.size() > 0) {
            for (OpsInventoryProperty pro : propertyList) {
                inv.setInventoryPropertyId(pro.getInventoryPropertyId());
                List<OpsInventory> opsInventoryList = findOpsInventory(inv);
                invList.addAll(opsInventoryList);
            }
        }
        if (!CollectionUtils.isEmpty(invList)) {
            invList.sort(new Comparator<OpsInventory>() {// 由大到小排序
                @Override
                public int compare(OpsInventory o1, OpsInventory o2) {
                    Integer target1 = o1.getQuantity() - o1.getPrepareQuantity();
                    Integer target2 = o2.getQuantity() - o2.getPrepareQuantity();
                    return target2.compareTo(target1);
                }
            });
        }
        return invList;
    }

    /**
     * @description 库存查询在途表 仓库、型号、属性ID、库存状态（默认在库）
     * @author C12961
     * @date 2022/3/10 16:12
     */
    public List<OpsInventoryMove> findOpsInventoryMove(OpsInventoryMove inv) throws OpsException {
        OpsInventoryMoveExample ex = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = ex.createCriteria();
        criteria.andDelflagEqualTo(0);
        if (StrUtil.isNotBlank(inv.getWarehouseCode())) {
            criteria.andWarehouseCodeEqualTo(inv.getWarehouseCode());
        }
        if (StrUtil.isNotBlank(inv.getModelno())) {
            criteria.andModelnoEqualTo(inv.getModelno());
        }
        if (inv.getInventoryPropertyId() != null) {
            criteria.andInventoryPropertyIdEqualTo(inv.getInventoryPropertyId());
        }
        if (StrUtil.isNotBlank(inv.getInventoryStatus())) {
            criteria.andInventoryStatusEqualTo(inv.getInventoryStatus());
        }
        return opsInventoryMoveMapper.selectByExample(ex);
    }

    /**
     * @description 库存查询在途表，指定批属性内容 仓库、型号、属性ID、库存状态（默认在库）
     * @author C12961
     * @date 2022/3/10 16:12
     */
    public List<OpsInventoryMove> findOpsInventoryMove(OpsInventoryMove inv, OpsInventoryProperty property)
            throws OpsException {
        List<OpsInventoryMove> invList = new ArrayList<>();
        List<OpsInventoryProperty> propertyList = opsInventoryPropertyService.matchByExample(property);
        if (propertyList.size() > 0) {
            for (OpsInventoryProperty pro : propertyList) {
                inv.setInventoryPropertyId(pro.getInventoryPropertyId());
                List<OpsInventoryMove> opsInventoryMoveList = findOpsInventoryMove(inv);
                invList.addAll(opsInventoryMoveList);
            }
        }
        return invList;
    }

    //bugid:17173 c14717 库存查询优化
    public List<OpsInventory> getOpsInventoryListZDV2(List<OpsInventoryProperty> inventoryPropertyList, String modelno,
                                                    String warehouseCode, String inventoryStatus,HashMap<Long, List<OpsInventory>> invMap) {
        List<OpsInventory> list = new ArrayList<>();
        for (OpsInventoryProperty opsInventoryProperty : inventoryPropertyList) {
            Long hashCodeKey = HashCodeUtil.hashCodeInventoryKey(opsInventoryProperty.getInventoryPropertyId(), modelno, warehouseCode, inventoryStatus);
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(invMap.get(hashCodeKey))){
                list.addAll(invMap.get(hashCodeKey));
            }
        }
        if (!CollectionUtils.isEmpty(list)) {
            list.sort(new Comparator<OpsInventory>() {// 由大到小排序
                @Override
                public int compare(OpsInventory o1, OpsInventory o2) {
                    Integer target1 = o1.getQuantity() - o1.getPrepareQuantity();
                    Integer target2 = o2.getQuantity() - o2.getPrepareQuantity();
                    return target2.compareTo(target1);
                }
            });
        }
        return list;
    }

    //bugid:17319 c14717 20240401 订单分配库存查询优化-每型号只查一次库存
    public List<OpsInventory> getOpsInventoryListV3(List<OpsInventoryProperty> inventoryPropertyList, String modelno,
                                                      String warehouseCode, String inventoryStatus,HashMap<Long, List<OpsInventory>> invMap) {
        List<OpsInventory> list = new ArrayList<>();
        for (OpsInventoryProperty opsInventoryProperty : inventoryPropertyList) {
            Long hashCodeKey = HashCodeUtil.hashCodeInventoryKey(opsInventoryProperty.getInventoryPropertyId(), modelno, warehouseCode, inventoryStatus);
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(invMap.get(hashCodeKey))){
                list.addAll(invMap.get(hashCodeKey));
            }
        }
        if (!CollectionUtils.isEmpty(list)) {
            list.sort(new Comparator<OpsInventory>() {// 由大到小排序
                @Override
                public int compare(OpsInventory o1, OpsInventory o2) {
                    Integer target1 = o1.getQuantity() - o1.getPrepareQuantity();
                    Integer target2 = o2.getQuantity() - o2.getPrepareQuantity();
                    return target2.compareTo(target1);
                }
            });
        }
        return list;
    }


    /**
     * @description：查询可用库存列表
     * @author ：c02483
     * @date ：Created in 2021/9/13 8:03
     */
    public List<OpsInventory> getOpsInventoryList(List<OpsInventoryProperty> inventoryPropertyList, String modelno,
                                                  String warehouseCode, String inventoryStatus) {
        List<OpsInventory> list = new ArrayList<>();
        for (OpsInventoryProperty opsInventoryProperty : inventoryPropertyList) {
            List<OpsInventory> targetList = opsInventoryDao.getOpsInventoryListDao(
                    opsInventoryProperty.getInventoryPropertyId(), modelno, warehouseCode, inventoryStatus);
            list.addAll(targetList);
        }
        if (!CollectionUtils.isEmpty(list)) {
            list.sort(new Comparator<OpsInventory>() {// 由大到小排序
                @Override
                public int compare(OpsInventory o1, OpsInventory o2) {
                    Integer target1 = o1.getQuantity() - o1.getPrepareQuantity();
                    Integer target2 = o2.getQuantity() - o2.getPrepareQuantity();
                    return target2.compareTo(target1);
                }
            });
        }
        return list;
    }

    public List<OpsInventoryMove> getOpsInventoryMoveListV1(List<OpsInventoryProperty> inventoryPropertyList, String modelno,
                                                    String warehouseCode, String inventoryStatus,HashMap<Long, List<OpsInventoryMove>> moveInvMap) {
        List<OpsInventoryMove> list = new ArrayList<>();
        for (OpsInventoryProperty opsInventoryProperty : inventoryPropertyList) {
            Long hashCodeKey = HashCodeUtil.hashCodeInventoryKey(opsInventoryProperty.getInventoryPropertyId(), modelno, warehouseCode, inventoryStatus);
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(moveInvMap.get(hashCodeKey))){
                list.addAll(moveInvMap.get(hashCodeKey));
            }
        }
        if (!CollectionUtils.isEmpty(list)) {
            list.sort(new Comparator<OpsInventoryMove>() {// 由大到小排序
                @Override
                public int compare(OpsInventoryMove o1, OpsInventoryMove o2) {
                    Integer target1 = o1.getQuantity() - o1.getPrepareQuantity();
                    Integer target2 = o2.getQuantity() - o2.getPrepareQuantity();
                    return target2.compareTo(target1);
                }
            });
        }
        return list;
    }

    /**
     * @description：查询可用在途库存列表
     * @author ：c02483
     * @date ：Created in 2021/9/13 8:03
     */
    public List<OpsInventoryMove> getOpsInventoryMoveList(List<OpsInventoryProperty> inventoryPropertyList,
                                                          String modelno, String warehouseCode, String inventoryStatus) {
        List<OpsInventoryMove> list = new ArrayList<>();
        for (OpsInventoryProperty opsInventoryProperty : inventoryPropertyList) {
            List<OpsInventoryMove> targetList = opsInventoryDao.getOpsInventoryMoveListDao(
                    opsInventoryProperty.getInventoryPropertyId(), modelno, warehouseCode, inventoryStatus);
            list.addAll(targetList);
        }
        if (!CollectionUtils.isEmpty(list)) {
            list.sort(new Comparator<OpsInventoryMove>() {// 由大到小排序
                @Override
                public int compare(OpsInventoryMove o1, OpsInventoryMove o2) {
                    Integer target1 = o1.getQuantity() - o1.getPrepareQuantity();
                    Integer target2 = o2.getQuantity() - o2.getPrepareQuantity();
                    return target2.compareTo(target1);
                }
            });
        }

        return list;
    }

    /**
     * 按照查询条件获取可用在途库存
     *
     * @param modelno
     * @param warehouseCodes      空查全部
     * @param inventoryStatusList 至少天一项
     * @param associateNoType     至少填一项，关联单号类型:[0:88开头的先行在库补库单, 1:99开头的bin补库单, 2:其他订单]
     * @param sourceType          至少填一项，在途来源:[0:采购, 1:调拨, 2:退货]
     * @return
     * @throws OpsException
     */
    public List<OpsInventoryMove> findOpsInventoryMoveForTransOrder(String modelno, List<String> warehouseCodes, List<String> inventoryStatusList,
                                                                    List<Integer> associateNoType, List<String> sourceType) throws OpsException {
        OpsInventoryMoveExample ex = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = ex.createCriteria();
        criteria.andDelflagEqualTo(0);
        if (StrUtil.isNotBlank(modelno)) {
            criteria.andModelnoEqualTo(modelno);
        }
        if (!CollectionUtils.isEmpty(warehouseCodes)) {
            criteria.andWarehouseCodeIn(warehouseCodes);
        }
        if (!CollectionUtils.isEmpty(inventoryStatusList)) {
            criteria.andInventoryStatusIn(inventoryStatusList);
        } else {
            throw Exceptions.OpsException("请填写至少一项库存状态");
        }
        if (!CollectionUtils.isEmpty(sourceType)) {
            criteria.andSourceTypeIn(sourceType);
        } else {
            throw Exceptions.OpsException("请填写至少一项在途来源:[0:采购, 1:调拨, 2:退货]");
        }
        List<OpsInventoryMove> moves = opsInventoryMoveMapper.selectByExample(ex);
        // 过滤有效库存大于0的
        moves = moves.stream().filter(m -> m.getQuantity() > m.getPrepareQuantity()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(associateNoType)) {
            if (associateNoType.size() == 1) {
                if (Objects.equals(associateNoType.get(0), 0)) {// 只要"88"开头的
                    moves = moves.stream().filter(m -> m.getAssociateNo().startsWith("88")).collect(Collectors.toList());
                } else if (Objects.equals(associateNoType.get(0), 1)) {// 只要"99"开头的且通用
                    moves = moves.stream()
                            .filter(m -> m.getAssociateNo().startsWith("99"))
                            .filter(m -> m.getInventoryPropertyId().intValue() == 1)
                            .collect(Collectors.toList());
                } else if (Objects.equals(associateNoType.get(0), 2)) {// 不是"88"也不是"99"开头
                    moves = moves.stream().filter(m -> !m.getAssociateNo().startsWith("88")).filter(m -> !m.getAssociateNo().startsWith("99")).collect(Collectors.toList());
                } else if (Objects.equals(associateNoType.get(0), 3)) {// 只要"99"开头的且不是通用
                    moves = moves.stream()
                            .filter(m -> m.getAssociateNo().startsWith("99"))
                            .filter(m -> m.getInventoryPropertyId().intValue() != 1)
                            .collect(Collectors.toList());
                }
            } else {
                List<OpsInventoryMove> move88 = moves.stream().filter(m -> m.getAssociateNo().startsWith("88")).collect(Collectors.toList());
                List<OpsInventoryMove> move99AndTY = moves.stream().filter(m -> m.getAssociateNo().startsWith("99")).filter(m -> m.getInventoryPropertyId().intValue() == 1).collect(Collectors.toList());
                List<OpsInventoryMove> move99NotTY = moves.stream().filter(m -> m.getAssociateNo().startsWith("99")).filter(m -> m.getInventoryPropertyId().intValue() != 1).collect(Collectors.toList());
                List<OpsInventoryMove> moveNot8899 = moves.stream().filter(m -> !m.getAssociateNo().startsWith("88")).filter(m -> !m.getAssociateNo().startsWith("99")).collect(Collectors.toList());
                List<OpsInventoryMove> available = new ArrayList<>();
                if (associateNoType.contains(0)) {
                    available.addAll(move88);
                }
                if (associateNoType.contains(1)) {
                    available.addAll(move99AndTY);
                }
                if (associateNoType.contains(2)) {
                    available.addAll(moveNot8899);
                }
                if (associateNoType.contains(3)) {
                    available.addAll(move99NotTY);
                }
                moves = available;
            }
        } else {
            throw Exceptions.OpsException("请填写至少一项关联单号类型:[0:88开头的先行在库补库单, 1:99开头的bin补库单, 2:其他订单, 3:99开头的先行在库补库单]");
        }
        return moves;
    }


    /**
     * @description： 获取拆分bom列表
     * @author ：c02483
     * @date ：Created in 2021/9/21 0:08
     */
    protected List<ProductBom> getProductBomList(String modelno) throws OpsException {
        /*
         * CommonResult result = opsProductFeignApi.searchProductBom(modelno); if
         * (ResultCode.SUCCESS.code().equals(result.getCode())) { return
         * (List<ProductBom>) result.getData(); } else { throw
         * Exceptions.OpsException("查询拆分Bom异常", result.getMessage()); }
         */
        return productMapper.queryProductBomByModelNo(modelno);
    }

    /**
     * @description：获取Bom明细
     * @author ：c02483
     * @date ：Created in 2021/9/21 0:09
     */
    protected List<ProductBomDetail> getProductBomDetailList(Long bomid) throws OpsException {
        /*
         * CommonResult<List<ProductBomDetail>> result =
         * opsProductFeignApi.searchProductBomDetail(bomid.toString()); if
         * (ResultCode.SUCCESS.code().equals(result.getCode())) { return
         * result.getData(); } else { throw
         * Exceptions.OpsException("查询拆分Bom异常，无BomDetail", result.getMessage()); }
         */
        List<ProductBomDetail> productBomDetailList = productMapper.queryProductBomDetailByModelNo(bomid);
        if (CollectionUtils.isEmpty(productBomDetailList)) {
            throw Exceptions.OpsException("数据异常，无BomDetail。bomID:" + bomid);
        }
        return productBomDetailList;

    }

    /**
     * @description：按照客户代码，查询对应可出库的仓库号
     * @author ：c02483
     * @date ：Created in 2021/10/3 14:43
     */
    protected List<OpsWarehouseSalesbranchConfig> getBranchListBysalesBranchId(
            String orderType, String customerNo, String salesBranchId, String warehouseTypeCode, Integer expDlvType, String expLinkNo) throws OpsException {
        return warehouseSalesbranchService.getBranchListBysalesBranchId(orderType, customerNo, salesBranchId,
                warehouseTypeCode, expDlvType, expLinkNo);
    }

    /**
     * @description 根据po单号、发票号、采购单状态查询
     * @author C12961
     * @date 2023/3/2 15:32
     */
    public List<OpsInventoryMove> findInventoryMoveByRequestPurchase(String orderNo, Integer itemNo, Integer splitNo) {
        OpsInventoryMoveExample ex = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = ex.createCriteria();
        // 15226 查询move类型为采购 2024-10-17
        criteria.andDelflagEqualTo(0).andSourceTypeEqualTo(SourceTypeEnum.PURCHASE.getType());
        if (StringUtils.isNotEmpty(orderNo)) {
            criteria.andOrdernoEqualTo(orderNo);
        }
        if (Objects.nonNull(itemNo)) {
            criteria.andItemnoEqualTo(itemNo);
        }
        if (Objects.nonNull(splitNo)) {
            criteria.andSplititemnoEqualTo(splitNo);
        } else {
            criteria.andSplititemnoEqualTo(0);
        }
        return opsInventoryMoveMapper.selectByExample(ex);
    }

    /**
     * @description 根据po单号查询采购多订库存
     * @author C12961
     * @date 2023/8/21 14:32
     */
    public List<OpsInventoryMove> findInventoryMoveWithEmptyRequestNoByPurchase(String orderNo, Integer itemNo, Integer splitNo) {
        OpsInventoryMoveExample ex = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = ex.createCriteria();
        // 15226 查询move类型为采购 2024-10-17
        criteria.andSourceTypeEqualTo(SourceTypeEnum.PURCHASE.getType());
        if (StringUtils.isNotEmpty(orderNo)) {
            criteria.andAssociateNoEqualTo(orderNo);
        }
        if (Objects.nonNull(itemNo)) {
            criteria.andAssociateNoItemEqualTo(itemNo);
        }
        if (Objects.nonNull(splitNo)) {
            criteria.andAssociateNoSplitnoEqualTo(splitNo);
        } else {
            criteria.andAssociateNoSplitnoEqualTo(0);
        }
        criteria.andOrdernoIsNull().andItemnoIsNull().andSplititemnoEqualTo(0);
        criteria.andDelflagEqualTo(0);
        return opsInventoryMoveMapper.selectByExample(ex);
    }


    public List<OpsInventoryMove> findInventoryMoveByPo(InventoryStatusEnum inventoryStatus,
                                                        String associateNo, Integer associateItem, Integer associateSplit, String invoiceNo) {
        OpsInventoryMoveExample ex = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = ex.createCriteria();
        // 15226 查询move类型为采购 2024-10-17
        criteria.andSourceTypeEqualTo(SourceTypeEnum.PURCHASE.getType());
        if (StringUtils.isNotEmpty(associateNo)) {
            criteria.andAssociateNoEqualTo(associateNo);
        }
        if (Objects.nonNull(associateItem)) {
            criteria.andAssociateNoItemEqualTo(associateItem);
        }
        if (Objects.nonNull(associateSplit)) {
            criteria.andAssociateNoSplitnoEqualTo(associateSplit);
        } else {
            criteria.andAssociateNoSplitnoEqualTo(0);
        }
        if (Objects.nonNull(inventoryStatus)) {
            criteria.andInventoryStatusEqualTo(inventoryStatus.getCode());
        }
        if (StringUtils.isNotEmpty(invoiceNo)) {
            criteria.andInvoicenoEqualTo(invoiceNo);
        }
        criteria.andDelflagEqualTo(0);
        return opsInventoryMoveMapper.selectByExample(ex);
    }

    /**
     * @description 根据po单号和采购库存状态查询
     * @author C12961
     * @date 2023/3/2 15:32
     */
    public List<OpsInventoryMove> findInventoryMoveByPo(String poNo, Integer poItemNo, Integer poSplitNo,
                                                        InventoryStatusEnum inventoryStatus) {
        return findInventoryMoveByPo(inventoryStatus, poNo, poItemNo, poSplitNo, null);
    }

    /**
     * @description 根据po单号查询
     * @author C12961
     * @date 2023/3/2 15:32
     */
    public List<OpsInventoryMove> findInventoryMoveByPo(String poOrderNo, Integer poItemNo, Integer poSplitNo) {
        return findInventoryMoveByPo(null, poOrderNo, poItemNo, poSplitNo, null);
    }


    /**
     * @description：通过关联单号查询在途库存
     * @author ：c02483
     * @date ：Created in 2021/11/12 15:38
     */
    public List<OpsInventoryMove> getOpsInventoryMoveListByPo(InventoryStatusEnum inventoryStatusEnum, String modelno,
                                                              String associateNo, Integer associateNoItem, Integer splititemno, String invoiceNo) {
        OpsInventoryMoveExample example = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = example.createCriteria();
        criteria.andAssociateNoEqualTo(associateNo).andAssociateNoItemEqualTo(associateNoItem)
                .andInTimeIsNull().andModelnoEqualTo(modelno)
                .andDelflagEqualTo(0);
        // 15226 查询move类型为采购 2024-10-17
        criteria.andSourceTypeEqualTo(SourceTypeEnum.PURCHASE.getType());
        if (Objects.nonNull(inventoryStatusEnum)) {
            criteria.andInventoryStatusEqualTo(inventoryStatusEnum.getCode());
        }
        if (!StringUtils.isEmpty(invoiceNo)) {
            criteria.andInvoicenoEqualTo(invoiceNo);
        }
        if (Objects.nonNull(splititemno)) {
            criteria.andAssociateNoSplitnoEqualTo(splititemno);
        } else {
            criteria.andAssociateNoSplitnoEqualTo(0);
        }
        example.setOrderByClause("  cre_time asc ");
        return opsInventoryMoveMapper.selectByExample(example);
    }

    /**
     * @description：通过关联单号查询在途库存
     * @author ：c02483
     * @date ：Created in 2021/11/12 15:38
     */
    public List<OpsInventoryMove> getOpsInventoryMoveListByAssociateNo(InventoryStatusEnum inventoryStatusEnum, String modelno,
                                                              String associateNo, Integer associateNoItem, Integer splititemno, String invoiceNo) {
        OpsInventoryMoveExample example = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = example.createCriteria();
        criteria.andAssociateNoEqualTo(associateNo).andAssociateNoItemEqualTo(associateNoItem)
                .andInTimeIsNull().andModelnoEqualTo(modelno)
                .andDelflagEqualTo(0);
        if (Objects.nonNull(inventoryStatusEnum)) {
            criteria.andInventoryStatusEqualTo(inventoryStatusEnum.getCode());
        }
        if (!StringUtils.isEmpty(invoiceNo)) {
            criteria.andInvoicenoEqualTo(invoiceNo);
        }
        if (Objects.nonNull(splititemno)) {
            criteria.andAssociateNoSplitnoEqualTo(splititemno);
        } else {
            criteria.andAssociateNoSplitnoEqualTo(0);
        }
        example.setOrderByClause("  cre_time asc ");
        return opsInventoryMoveMapper.selectByExample(example);
    }

    public List<OpsInventoryMove> selectOpsInventoryMoveListByPo(String modelno, String associateNo,
                                                                 String associateNoItem, Integer associateNoSplitNo, String invoiceNo) {
        OpsInventoryMoveExample example = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = example.createCriteria();
        criteria.andAssociateNoEqualTo(associateNo).andAssociateNoItemEqualTo(Integer.parseInt(associateNoItem))// .andSplititemnoEqualTo(splititemno)
                .andInTimeIsNull().andModelnoEqualTo(modelno).andDelflagEqualTo(0);
        if (!StringUtils.isEmpty(invoiceNo)) {
            criteria.andInvoicenoEqualTo(invoiceNo);
        }
        if (Objects.nonNull(associateNoSplitNo)) {
            criteria.andAssociateNoSplitnoEqualTo(associateNoSplitNo);
        }
        example.setOrderByClause("  cre_time asc ");
        return opsInventoryMoveMapper.selectByExample(example);
    }


    /**
     * @description：根据发票获取在途库存
     * @author ：c02483
     * @date ：Created in 2022/1/15 10:00
     */
    public List<OpsInventoryMove> getOpsInventoryMoveListByInvoice(String invoiceNo, String warehouse) {
        OpsInventoryMoveExample example = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = example.createCriteria();
        criteria.andInvoicenoEqualTo(invoiceNo).andDelflagEqualTo(0);
        if (Objects.nonNull(warehouse)) {
//          criteria.andWarehouseCodeEqualTo(warehouse);
            criteria.andSignWarehouseCodeEqualTo(warehouse);
        }
        return opsInventoryMoveMapper.selectByExample(example);
    }


    public List<OpsInventoryMove> getOpsInventoryMoveListByInvoice(InventoryStatusEnum inventoryStatusEnum, String invoiceNo, Long invoiceId) {
        OpsInventoryMoveExample example = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = example.createCriteria();
        criteria.andInvoicenoEqualTo(invoiceNo).andDelflagEqualTo(0);
        if (invoiceId != null) {
            criteria.andInvoiceidEqualTo(invoiceId);
        }
        if (inventoryStatusEnum != null) {
            criteria.andInventoryStatusEqualTo(inventoryStatusEnum.getCode());
        }
        return opsInventoryMoveMapper.selectByExample(example);
    }

    /**
     * 新增发票号ID判断
     */
    public List<OpsInventoryMove> getOpsInventoryMoveListByInvoice(String invoiceNo, Long invoiceId, String warehouse) {
        OpsInventoryMoveExample example = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = example.createCriteria();
        criteria.andInvoicenoEqualTo(invoiceNo).andDelflagEqualTo(0);
        if (Objects.nonNull(warehouse)) {
            criteria.andSignWarehouseCodeEqualTo(warehouse);
        }
        if (Objects.nonNull(invoiceId)) {
            criteria.andInvoiceidEqualTo(invoiceId);
        }
        return opsInventoryMoveMapper.selectByExample(example);
    }

    public List<InventorySupplier> getInventorySupplier(String supplier, String modelno) {
        InventorySupplierExample e = new InventorySupplierExample();
        InventorySupplierExample.Criteria criteria = e.createCriteria();
        if (StringUtils.isNotBlank(supplier)) {
            criteria.andSupplieridEqualTo(supplier);
        }
        if (StringUtils.isNotBlank(modelno)) {
            criteria.andModelnoEqualTo(modelno);
        }
        return inventorySupplierMapper.selectByExample(e);
    }


    public boolean isBinModelNo(String modelNo, String customerNo) {
        Integer i = opsInventoryBinDao.isBinModelno(modelNo, customerNo);
        return i > 0;
    }

    /********************** 库存的增删改 ********************************/

    /**
     * @description：创建在库库存，返回库存ID
     * @author ：c02483
     * @date ：Created in 2021/12/30 13:57
     */
    public synchronized Long createInvReturnId(OpsInventory inventory, UserDto userDto) throws OpsException {
        //去掉空格和制表符
        if (!StringUtils.isEmpty(inventory.getModelno())) {
            inventory.setModelno(inventory.getModelno().trim());
            inventory.setModelno(inventory.getModelno().replace("\r", ""));
        }
        inventory.setDelflag(0);
        inventory.setVersion(0L);
        inventory.setCreTime(new Date());
        inventory.setModifyTime(new Date());
        inventory.setCreator(userDto.getUserName());
        inventory.setModifier(userDto.getUserName());
        inventory.setInTime(new Date());
        if (inventory.getQuantity() == null) {
            inventory.setQuantity(0);
        }
        if (inventory.getPrepareQuantity() == null) {
            inventory.setPrepareQuantity(0);
        }
        if (StringUtils.isEmpty(inventory.getQaStatus())) {
            inventory.setQaStatus(QAStatusEnum.NORMAL.getType());
        }
        if (!StringUtils.isEmpty(inventory.getModelno()) && !StringUtils.isEmpty(inventory.getWarehouseCode())
                && inventory.getInventoryPropertyId() != null) {
            OpsInventoryExample example = new OpsInventoryExample();
            example.createCriteria().andWarehouseCodeEqualTo(inventory.getWarehouseCode())
                    .andModelnoEqualTo(inventory.getModelno())
                    .andInventoryPropertyIdEqualTo(inventory.getInventoryPropertyId()).andDelflagEqualTo(0);
            long count = opsInventoryMapper.countByExample(example);
            if (count == 0) {
                opsInventoryDao.insertInventory(inventory);
            } else {
                throw Exceptions.OpsException("库存插入失败，库存已存在");
            }
        } else {
            String EmptyColumn = null;
            if (StringUtils.isEmpty(inventory.getModelno())) {
                EmptyColumn += "型号";
            }
            if (StringUtils.isEmpty(inventory.getWarehouseCode())) {
                EmptyColumn += "仓库代码";
            }
            if (inventory.getInventoryPropertyId() == null) {
                EmptyColumn += "批属性";
            }
            throw Exceptions.OpsException("库存插入失败，缺少字段：" + EmptyColumn);
        }
        return inventory.getInventoryId();
    }

    /**
     * @description：创建在途库存，返回库存ID
     * @author ：c02483
     * @date ：Created in 2021/12/30 13:57
     */
    public Long createInvMoveReturnId(OpsInventoryMove inventory, UserDto userDto) {
        inventory.setDelflag(0);
        inventory.setVersion(0L);
        inventory.setCreTime(new Date());
        inventory.setModifyTime(new Date());
        inventory.setOptTime(new Date());
        inventory.setCreator(userDto.getUserName());
        inventory.setModifier(userDto.getUserName());
        if (StringUtils.isEmpty(inventory.getQaStatus())) {
            inventory.setQaStatus(QAStatusEnum.NORMAL.getType());
        }
        opsInventoryDao.insertInventoryMove(inventory);
        return inventory.getInventoryId();
    }

    /**
     * @description：逻辑删除在途库存
     * @author ：c02483
     * @date ：Created in 2021/12/28 11:15
     */
    //bugid:10716 c14717 20230610
    @InvLog(apiName = "库存数量变动日志")
    public void deleteInventoryMove(Long id, Long version, UserDto userDto) throws OpsException {
        OpsInventoryMoveExample example = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andVersionEqualTo(version).andInventoryIdEqualTo(id);
        OpsInventoryMove record = new OpsInventoryMove();
        record.setQuantity(0);
        record.setPrepareQuantity(0);
        record.setVersion(version + 1);
        record.setModifyTime(new Date());
        record.setModifier(userDto.getUserName());
        record.setDelflag(1);
        record.setDelTime(new Date());
        int rows = opsInventoryMoveMapper.updateByExampleSelective(record, example);
        if (rows != 1) {
            throw Exceptions.OpsException("delete库存更新异常ID:" + id, id);
        }
    }

    //bugid:10716 c14717 20230610
    @InvLog(apiName = "库存数量变动日志")
    public void deleteInventoryMoveNotPreQty(Long id, Long version, UserDto userDto) throws OpsException {
        OpsInventoryMoveExample example = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andVersionEqualTo(version).andInventoryIdEqualTo(id);
        OpsInventoryMove record = new OpsInventoryMove();
        record.setQuantity(0);
        record.setVersion(version + 1);
        record.setModifyTime(new Date());
        record.setModifier(userDto.getUserName());
        record.setDelflag(1);
        record.setDelTime(new Date());
        int rows = opsInventoryMoveMapper.updateByExampleSelective(record, example);
        if (rows != 1) {
            throw Exceptions.OpsException("delete库存更新异常ID:" + id, id);
        }
    }

    //bugid:10716 c14717 20230610
    @InvLog(apiName = "库存数量变动日志")
    public void deleteInventoryMoveNew(Long id, Long version, Integer qty, Integer preQty, String userName) throws OpsException {
        OpsInventoryMoveExample example = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andVersionEqualTo(version).andInventoryIdEqualTo(id);
        OpsInventoryMove record = new OpsInventoryMove();
        record.setQuantity(0);
        record.setPrepareQuantity(0);
        record.setOriginalQuantity(qty);//原始收货数量
        record.setPreOriginalQuantity(preQty);//原始收货预占数量
        record.setVersion(version + 1);
        record.setModifyTime(new Date());
        record.setModifier(userName);
        record.setDelflag(1);
        record.setDelTime(new Date());
        int rows = opsInventoryMoveMapper.updateByExampleSelective(record, example);
        if (rows != 1) {
            throw Exceptions.OpsException("库存更新异常ID:" + id, id);
        }
    }

    /**
     * @description：逻辑删除在库库存
     * @author ：c02483
     * @date ：Created in 2021/12/28 11:15
     */
    //bugid:10716 c14717 20230610
    @InvLog(apiName = "库存数量变动日志")
    public void deleteInventory(Long id, Long version, UserDto userDto) throws OpsException {
        OpsInventoryExample example = new OpsInventoryExample();
        OpsInventoryExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andVersionEqualTo(version).andInventoryIdEqualTo(id);
        OpsInventory record = new OpsInventory();
        record.setDelflag(1);
        record.setVersion(version + 1);
        record.setModifyTime(new Date());
        record.setModifier(userDto.getUserName());
        int rows = opsInventoryMapper.updateByExampleSelective(record, example);
        if (rows != 1) {
            throw Exceptions.OpsException("库存更新异常ID" + id, id);
        }
    }

    /**
     * @description 逻辑删除在库或在途库存
     * @author C12961
     * @date 2022/4/2 10:45
     */
    //bugid:10716 c14717 20230610
    @InvLog(apiName = "库存数量变动日志")
    public void delInventoryById(InventoryTableTypeEnum tableType, Long id, Long version, UserDto userDto)
            throws OpsException {
        if (InventoryTableTypeEnum.NORMAL.equals(tableType)) {
            deleteInventory(id, version, userDto);
        }
        if (InventoryTableTypeEnum.TRANS.equals(tableType)) {
            deleteInventoryMove(id, version, userDto);
        }
    }

    /*********************** 变更库存状态 ***************************/

    /**
     * @description 变更在途库存状态，通用方法
     * @author C12961
     * @date 2022/3/31 17:15
     */
    public void updateInventoryStatus(OpsInventoryMove opsInventoryMove, InventoryStatusEnum statusEnum,
                                      OpsPurchaseinvoice invoice) throws OpsException {
        OpsInventoryMoveExample example = new OpsInventoryMoveExample();
        example.createCriteria().andDelflagEqualTo(0).andInventoryIdEqualTo(opsInventoryMove.getInventoryId())
                .andVersionEqualTo(opsInventoryMove.getVersion());
        OpsInventoryMove update = new OpsInventoryMove();
        update.setInventoryStatus(statusEnum.getCode());
        // P转T1的时候调用
        if (!Objects.isNull(invoice)) {
            update.setSupplierid(invoice.getSupplierid());
            update.setInvoiceno(invoice.getInvoiceno());
            update.setInvoiceid(invoice.getInvoiceid());
            update.setPrereceivedate(opsInventoryMove.getPrereceivedate());
            update.setAssociateNo(null);
            update.setAssociateNoItem(null);
            update.setOptStatus(OptStatusEnum.INVOICE_INIT.getCode());
        }
        update.setVersion(opsInventoryMove.getVersion() + 1);
        update.setModifier(UserDto.AUTO.getUserName());
        update.setModifyTime(new Date());
        int i = opsInventoryMoveMapper.updateByExampleSelective(update, example);
        if (i > 0) {
            return;
        } else {
            throw Exceptions.OpsException("变更库存状态失败");
        }
    }

    /**
     * @description：变更为采购在途状态 添加供应商，预计到货日期，采购单号 采购发货调用此方法
     * @author ：c02483
     * @date ：Created in 2021/11/15 11:48
     */
    public void UpdateStatusInventoryMoveForCG(OpsPurchaseinvoice invoice, OpsInventoryMove opsInventoryMove)
            throws OpsException {
        updateInventoryStatus(opsInventoryMove, InventoryStatusEnum.CGTRANS, invoice);
    }

    /**
     * @description：变更为到货未入库状态 到货签收调用此方法
     * @author ：c02483
     * @date ：Created in 2022/1/15 10:08
     */
    public void UpdateStatusInventoryMoveForSign(OpsInventoryMove opsInventoryMove) throws OpsException {
        updateInventoryStatus(opsInventoryMove, InventoryStatusEnum.W, null);
    }

    /*********************** 变更库存数量 ***************************/
    /**
     * @description：变更库存数量 qty 正数为增加库存，负数为扣减库存
     * @author ：c02483
     * @date ：Created in 2021/10/1 11:11
     */
    //bugid:10716 c14717 20230610
    @InvLog(apiName = "库存数量变动日志")
    public void UpdateQtyInventory(Long id, int qty, String tableType, UserDto userDto) throws OpsException {
        if (InventoryTableTypeEnum.TRANS.getType().equals(tableType)) {
            OpsInventoryMove inventory = opsInventoryMoveMapper.selectByPrimaryKey(id);
            log.info("更新前：" + JSONUtil.toJsonPrettyStr(inventory));
            int rows = opsInventoryDao.updateQtyInventoryMove(qty, inventory.getInventoryId(), inventory.getVersion(),
                    userDto.getUserName());
            if (rows < 1) {
                throw Exceptions.OpsException("库存更新异常:" + inventory);
            }
            OpsInventoryMove inventoryAfter = opsInventoryMoveMapper.selectByPrimaryKey(id);
            log.info("更新后：" + JSONUtil.toJsonPrettyStr(inventoryAfter));
        } else if (InventoryTableTypeEnum.NORMAL.getType().equals(tableType)) {
            OpsInventory inventory = opsInventoryMapper.selectByPrimaryKey(id);
            log.info("更新前：" + JSONUtil.toJsonPrettyStr(inventory));
            int rows = 0;
            if (qty > 0) {
                rows = opsInventoryDao.updateQtyInventoryAddInTime(qty, inventory.getInventoryId(),
                        inventory.getVersion(), userDto.getUserName());
            } else {
                rows = opsInventoryDao.updateQtyInventoryAddOutTime(qty, inventory.getInventoryId(),
                        inventory.getVersion(), userDto.getUserName());
            }
            if (rows < 1) {
                throw Exceptions.OpsException("库存更新异常" + inventory);
            }
            OpsInventory inventoryAfter = opsInventoryMapper.selectByPrimaryKey(id);
            log.info("更新后：" + JSONUtil.toJsonPrettyStr(inventoryAfter));
        } else {
            throw Exceptions.OpsException("库存更新异常tableType异常" + tableType);
        }
    }

    /**
     * @description：风险在库变存预占和预约方式
     * @author ：c02483
     * @date ：Created in 2021/10/1 11:11
     */
    public void UpdatePreQtyRiskInv(Long inventoryId, int qty, String tableType, UserDto userDto,String orderFno)
            throws OpsException {
        if (InventoryTableTypeEnum.NORMAL.getType().equals(tableType)) {
            InventoryRiskDTO invRisk = inventoryRiskDao.getInvRiskByInvId(inventoryId);
            log.info("更新前：" + JSONUtil.toJsonPrettyStr(invRisk));
            //bugid:8638 C14717 20221111 解决并发问题 20221121 注释掉，影响入库
            if (qty > 0 && (invRisk.getQuantity() - invRisk.getPrepareQuantity() - qty) < 0) {
                throw Exceptions.OpsException("库存更新异常" + invRisk);
            }
            if (invRisk.getPrepareQuantity() + qty < 0) {
                int target = invRisk.getPrepareQuantity() + qty;
                qty = (qty - target);// 预占数量为0
            }
            // 添加预占信息
            JSONObject jsonObject = new JSONObject();
            String bookingDetails = invRisk.getBookingDetails();
            if(StringUtils.isNotBlank(bookingDetails)){
                jsonObject = JSON.parseObject(bookingDetails);
            }
            jsonObject.put(orderFno,qty);
            int rows = inventoryRiskDao.updatePreQtyRiskInv(qty, userDto.getUserName(), invRisk.getVersion(),
                    inventoryId,jsonObject.toJSONString());
            if (rows != 1) {
                throw Exceptions.OpsException("库存更新异常" + invRisk);
            }
            invRisk = inventoryRiskDao.getInvRiskByInvId(inventoryId);
            log.info("更新后：" + JSONUtil.toJsonPrettyStr(invRisk));
        } else {
            throw Exceptions.OpsException("库存更新异常,tableType异常:" + tableType);
        }
    }

    /**
     * @description：变更库存预占 qty 正数为占用库存，负数为释放库存
     * @author ：c02483
     * @date ：Created in 2021/10/1 11:11
     */
    //bugid:10716 c14717 20230610
    @InvLog(apiName = "库存数量变动日志")
    public void UpdatePreQtyInventory(Long inventoryId, int qty, String tableType, UserDto userDto)
            throws OpsException {
        if (InventoryTableTypeEnum.TRANS.getType().equals(tableType)) {
            OpsInventoryMove inventory = opsInventoryMoveMapper.selectByPrimaryKey(inventoryId);
            log.info("更新前：" + JSONUtil.toJsonPrettyStr(inventory));
            // bugid:8638 C14717 20221111 解决并发问题 20221121 注释掉，影响入库
            if (qty > 0 && (inventory.getQuantity() - inventory.getPrepareQuantity() - qty) < 0) {
                throw Exceptions.OpsException("库存更新异常" + inventory);
            }
            if (inventory.getPrepareQuantity() + qty < 0) {
                int target = inventory.getPrepareQuantity() + qty;
                qty = (qty - target);// 预占数量为0
                // 期望结果 inventory.getPrepareQuantity() + qty = 0;
            }
            int rows = opsInventoryDao.updatePreQtyInventoryMove(qty, userDto.getUserName(), inventory.getVersion(),
                    inventory.getInventoryId());
            if (rows != 1) {
                throw Exceptions.OpsException("库存更新异常" + inventory);
            }
            inventory = opsInventoryMoveMapper.selectByPrimaryKey(inventoryId);
            log.info("更新后：" + JSONUtil.toJsonPrettyStr(inventory));
        } else if (InventoryTableTypeEnum.NORMAL.getType().equals(tableType)) {
            OpsInventory inventory = opsInventoryMapper.selectByPrimaryKey(inventoryId);
            log.info("更新前：" + JSONUtil.toJsonPrettyStr(inventory));
            //bugid:8638 C14717 20221111 解决并发问题 20221121 注释掉，影响入库
            if (qty > 0 && (inventory.getQuantity() - inventory.getPrepareQuantity() - qty) < 0) {
                throw Exceptions.OpsException("库存更新异常" + inventory);
            }
            if (inventory.getPrepareQuantity() + qty < 0) {
                int target = inventory.getPrepareQuantity() + qty;
                qty = (qty - target);// 预占数量为0
                // 期望结果 inventory.getPrepareQuantity() + qty = 0;
            }
            int rows = opsInventoryDao.updatePreQtyInventory(qty, userDto.getUserName(), inventory.getVersion(),
                    inventory.getInventoryId());
            if (rows != 1) {
                throw Exceptions.OpsException("库存更新异常" + inventory);
            }
            inventory = opsInventoryMapper.selectByPrimaryKey(inventoryId);
            log.info("更新后：" + JSONUtil.toJsonPrettyStr(inventory));
        } else {
            throw Exceptions.OpsException("库存更新异常,tableType异常:" + tableType);
        }
    }

    /**
     * @description：库存数量和预占一起变更
     * @author ：c02483
     * @date ：Created in 2021/10/29 10:33
     */
    //bugid:10716 c14717 20230610
    @Retryable
    @InvLog(apiName = "库存数量变动日志")
    public void UpdateQtyInventoryWithPreQty(Long id, int qty, int preQty, String tableType, UserDto userDto)
            throws OpsException {
        if (InventoryTableTypeEnum.TRANS.getType().equals(tableType)) {
            OpsInventoryMove inventory = opsInventoryMoveMapper.selectByPrimaryKey(id);
            log.info("更新前:" + JSONUtil.toJsonStr(inventory));
            if (inventory.getPrepareQuantity() + preQty < 0) {
                int target = inventory.getPrepareQuantity() + preQty;
                preQty = (preQty - target);// 预占数量为0
                // 期望结果 inventory.getPrepareQuantity() + preQty = 0;
            }
            //bugid:10716
            updateQtyInventoryMoveWithPre(inventory.getInventoryId(), qty, preQty, inventory.getVersion(), userDto);
            OpsInventoryMove inventoryAfter = opsInventoryMoveMapper.selectByPrimaryKey(id);
            log.info("更新后:" + JSONUtil.toJsonStr(inventoryAfter));
        } else if (InventoryTableTypeEnum.NORMAL.getType().equals(tableType)) {
            OpsInventory inventory = opsInventoryMapper.selectByPrimaryKey(id);
            log.info("更新前:" + JSONUtil.toJsonStr(inventory));
            int rows = 0;
            if (qty > 0) {
                rows = opsInventoryDao.updateQtyInventoryWithPreAddInTime(qty, preQty, inventory.getInventoryId(),
                        inventory.getVersion(), userDto.getUserName());
            } else {
                if (inventory.getPrepareQuantity() + preQty < 0) {
                    int target = inventory.getPrepareQuantity() + preQty;
                    preQty = (preQty - target);// 预占数量为0
                    // 期望结果 inventory.getPrepareQuantity() + preQty = 0;
                }
                rows = opsInventoryDao.updateQtyInventoryWithPreAddOutTime(qty, preQty, inventory.getInventoryId(),
                        inventory.getVersion(), userDto.getUserName());
            }
            if (rows < 1) {
                throw Exceptions.OpsException("库存更新异常" + id);
            }
            OpsInventory inventoryAfter = opsInventoryMapper.selectByPrimaryKey(id);
            log.info("更新后:" + JSONUtil.toJsonStr(inventoryAfter));
        } else {
            throw Exceptions.OpsException("库存更新异常tableType异常" + tableType);
        }
    }

    /**
     * @description：扣减库存数量
     * @author ：c02483
     * @date ：Created in 2021/10/1 11:21
     */
    //bugid:10716 c14717 20230610
    @InvLog(apiName = "库存数量变动日志")
    public void subQtyInventory(Long id, int qty, String tableType, UserDto userDto) throws OpsException {
        UpdateQtyInventory(id, -1 * qty, tableType, userDto);
    }

    /**
     * @description：增加库存数量
     * @author ：c02483
     * @date ：Created in 2021/10/1 11:21
     */
    //bugid:10716 c14717 20230610
    @InvLog(apiName = "库存数量变动日志")
    public void addQtyInventory(Long id, int qty, String tableType, UserDto userDto) throws OpsException {
        UpdateQtyInventory(id, qty, tableType, userDto);
    }

    /**
     * @description：释放库存预占
     * @author ：c02483
     * @date ：Created in 2021/10/1 11:21
     */
    //bugid:10716 c14717 20230610
    @InvLog(apiName = "库存数量变动日志")
    public void releasePreQtyInventory(Long id, int qty, String tableType, UserDto userDto) throws OpsException {
        log.info("释放库存预占,库存id:{},释放预占数:{}", id, qty);
        UpdatePreQtyInventory(id, -1 * qty, tableType, userDto);
    }

    /**
     * @description：增加库存预占
     * @author ：c02483
     * @date ：Created in 2021/10/1 11:21
     */
    @InvLog(apiName = "库存数量变动日志")
    public void addPreQtyInventory(Long id, int qty, String tableType, UserDto userDto) throws OpsException {
        log.info("增加库存预占,库存id:{},增加预占数:{}", id, qty);
        UpdatePreQtyInventory(id, qty, tableType, userDto);
    }

    /**
            * @description：增加库存预占
     * @author ：c02483
     * @date ：Created in 2021/10/1 11:21
            */
    public void addPreQtyRiskInv(Long id, int qty, String tableType, UserDto userDto,String orderFno) throws OpsException {
        log.info("增加风险预占,库存id:{},增加预占数:{}", id, qty);
        UpdatePreQtyRiskInv(id, qty, tableType, userDto,orderFno);
    }

    /**
     * 扣减库存数
     */
    //bugid:10716 c14717 20230610
    @InvLog(apiName = "库存数量变动日志")
    public void subPreQtyInventory(Long id, int preQty, String tableType, UserDto userDto) throws OpsException {
        log.info("扣减库存预占,库存id:{},扣减预占数:{}", id, preQty);
        UpdatePreQtyInventory(id, -preQty, tableType, userDto);
    }

    /**
     * @description：库存数量和预占数量一起扣减
     * @author ：c02483
     * @date ：Created in 2021/10/29 10:40
     */
    //bugid:10716 c14717 20230610
    @InvLog(apiName = "库存数量变动日志")
    public void subQtyInventoryForPre(Long id, int qty, int preQty, String tableType, UserDto userDto)
            throws OpsException {
        UpdateQtyInventoryWithPreQty(id, -1 * qty, -1 * preQty, tableType, userDto);
    }

    /**
     * @description： 在途库存数量的增加和预占
     * @author ：c02483
     * @date ：Created in 2021/12/10 11:12
     */
    //bugid:10716 c14717 20230610
    @InvLog(apiName = "库存数量变动日志")
    public void updateQtyInventoryMoveWithPre(Long id, int qty, int preqty, Long version, UserDto userDto)
            throws OpsException {
        int rows = opsInventoryDao.updateQtyInventoryMoveWithPre(qty, preqty, id, version, userDto.getUserName());
        if (rows < 1) {
            throw Exceptions.OpsException("库存更新异常ID" + id);
        }
    }

    /**************** 查询出库配置 **************************/
    /**
     * @description：查询匹配库存的维度
     * @author ：c02483
     * @date ：Created in 2021/9/10 16:48
     */
    protected OpsInventoryMatchConfig getOpsInventoryMatchConfig(String inventoryMatchCode) {
        /*OpsInventoryMatchConfig opsInventoryMatchConfig = opsInventoryMatchConfigMapper
                .selectByPrimaryKey(inventoryMatchCode);
        if (Objects.nonNull(opsInventoryMatchConfig)) {
            return opsInventoryMatchConfig;
        }
        return null;*/
        return cacheService.getMatchConfig(inventoryMatchCode);
    }

    /**
     * @description：根据ruleCode获取出库规则
     * @author ：c02483
     * @date ：Created in 2021/9/12 19:56
     */
    protected OpsInventoryRuleConfig getOpsInventoryRuleConfigList(String ruleCode) {
        /*OpsInventoryRuleConfig opsInventoryRuleConfig = opsInventoryRuleConfigMapper.selectByPrimaryKey(ruleCode);
        if (Objects.nonNull(opsInventoryRuleConfig)) {
            return opsInventoryRuleConfig;
        }*/
        return cacheService.getRuleConfig(ruleCode);
    }

    /**
     * @description：根据ruleCode获取出库规则明细
     * @author ：c02483
     * @date ：Created in 2021/9/21 16:03
     */
    protected List<OpsInventoryRuleConfigItem> getInventoryRuleConfigItemList(String ruleCode) {
        /*List<OpsInventoryRuleConfigItem> list = new ArrayList<>();
        OpsInventoryRuleConfigItemExample example = new OpsInventoryRuleConfigItemExample();
        example.createCriteria().andRuleCodeEqualTo(ruleCode).andDelflagEqualTo(0);
        example.setOrderByClause("rule_sort ASC");
        list = opsInventoryRuleConfigItemMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        return null;*/
        return cacheService.getRuleConfigItem(ruleCode);
    }


    /**
     * bugid: 20641 C14717 20260422     *
     * @param orderType
     * @param tag
     * @param propertyType
     * @param cktypeEnum
     * @return
     */
     List<OpsOrderInventoryRuleConfig> getOrderRuleList(String orderType, String tag,
                                                                                String propertyType, CKTYPEEnum cktypeEnum) {
        List<OpsOrderInventoryRuleConfig> list = new ArrayList<>();
        String cktype = "0";
        if (CKTYPEEnum.ITEM_UNLIMIT.equals(cktypeEnum)) {
            cktype = "2";
        } else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.equals(cktypeEnum) //货齐单仓 随发单仓 通知发货 都按单仓发货
                || CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.equals(cktypeEnum)
                || CKTYPEEnum.NOTIFY_UNLIMIT.equals(cktypeEnum)
        ) {
            cktype = "1";
        }
        list = cacheService.getOrderRuleConfig(orderType, tag, propertyType, cktype);
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        return null;
    }

    /**
     * @description：获取单据类型和单据标记对应的出库规
     * @author ：c02483
     * @date ：Created in 2021/9/12 19:56
     */
    public List<OpsOrderInventoryRuleConfig> getOpsOrderInventoryRuleConfigList(String orderType, String tag,
                                                                                String propertyType, CKTYPEEnum cktypeEnum) {// todo 增加出库方式判断 随到随发判断 如果是随到随发 分库和委托在库可拆分数量

        List<OpsOrderInventoryRuleConfig> list = new ArrayList<>();
        OpsOrderInventoryRuleConfigExample example = new OpsOrderInventoryRuleConfigExample();
        OpsOrderInventoryRuleConfigExample.Criteria criterion = example.createCriteria();
        criterion.andDelflagEqualTo(0).andOrderTypeEqualTo(orderType);
        if (!org.apache.commons.lang3.StringUtils.isBlank(tag)) {
            criterion.andOrderTagEqualTo(tag);
        }
        if (!org.apache.commons.lang3.StringUtils.isBlank(propertyType)) {
            criterion.andPropertyTypeEqualTo(propertyType);
        }
        if (CKTYPEEnum.ITEM_UNLIMIT.equals(cktypeEnum)) {
            criterion.andDlvEntireEqualTo(CKTYPEEnum.ITEM_UNLIMIT.getCode());// "2"
        } else if (CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.equals(cktypeEnum) //货齐单仓 随发单仓 通知发货 都按单仓发货
                || CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.equals(cktypeEnum)
                || CKTYPEEnum.NOTIFY_UNLIMIT.equals(cktypeEnum)
        ) {
            criterion.andDlvEntireEqualTo(CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode());// "1"
        } else {
            criterion.andDlvEntireEqualTo("0");// "0" 其他
        }
        // 还有其他
        example.setOrderByClause(" sort ASC");
        list = opsOrderInventoryRuleConfigMapper.selectByExample(example);
        // CommonResult<List<OpsOrderInventoryRuleConfig>> result =
        // opsInventoryBaseFeignApi.getOpsOrderInventoryRuleConfigList(orderType, tag,
        // propertyType, cktypeEnum);
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        return null;
    }

    protected List<OpsInventoryProperty> getInvPropertyV1(OpsInventoryMatchConfig matchConfig,
                                                        InventoryCkByOrderInputDto inputDto,String modelNo) throws OpsException {
        List<OpsInventoryProperty> result = new ArrayList<>();
        if(matchConfig.getMatchInventoryRisk()){
            List<RiskDto> riskDtos = inputDto.getRiskMap().get(modelNo);
            if(CollectionUtils.isEmpty(riskDtos)){
                return null;
            }
            for(RiskDto riskDto:riskDtos){
                if(matchConfig.getInventoryTypeCode().equals(riskDto.getInventoryTypeCode())){
                    OpsInventoryProperty p = new OpsInventoryProperty();
                    p.setInventoryPropertyId(riskDto.getInventoryPropertyId());
                    result.add(p);
                }
            }
            return result;
        }
        OpsInventoryType invType = cacheService.getInvType(matchConfig.getInventoryTypeCode());
        // 判断预约情报
        List<OpsInventoryProperty> invProperties = inputDto.getInvPropertyMap().get(modelNo);
        if ("QB_NO".equals(invType.getInventoryTypeCode())){
            for (OpsInventoryProperty invProperty : invProperties){
                if(StringUtils.isNotBlank(invProperty.getSalesInfoNo())){
                    result.add(invProperty);
                }
            }
            return result;
        }
        for(OpsInventoryProperty invProperty : invProperties){
            if(!matchConfig.getInventoryTypeCode().equals(invProperty.getInventoryTypeCode())){
                continue;
            }
            // 营业情报不为空跳过
            if(StringUtils.isNotBlank(invProperty.getSalesInfoNo())){
                continue;
            }
            boolean match = false;
            if(invType.getMatchProjectCode()){
                if(StringUtils.isBlank(inputDto.getProjectCode())){
                    return null;
                }
                if(inputDto.getProjectCode().equals(invProperty.getProjectCode())){
                    match = true;
                }else {
                    continue;
                }
            }else {
                if(StringUtils.isBlank(invProperty.getProjectCode())){
                    match = true;
                } else {
                    continue;
                }
            }

            if (invType.getMathchPpl()) {
                if (StringUtils.isBlank((inputDto.getPpl()))){
                    return null;
                }
                if (inputDto.getPpl().equals(invProperty.getPpl())) {
                    match = true;
                } else {
                    continue;
                }
            }else {
                if(StringUtils.isBlank(invProperty.getPpl())){
                    match = true;
                } else {
                    continue;
                }
            }
            if (invType.getMatchGroupCustomerNo()) {
                if (StringUtils.isBlank(inputDto.getGroupCustomerNo())) {
                    return null;
                }
                List<String> gourpCustomerNo = Arrays.asList(inputDto.getGroupCustomerNo().split(","));
                if (gourpCustomerNo.contains(invProperty.getGroupCustomerNo())) {
                    match = true;
                } else {
                    continue;
                }
            }else {
                if(StringUtils.isBlank(invProperty.getGroupCustomerNo())){
                    match = true;
                } else {
                    continue;
                }
            }

            if (invType.getMatchCustomerNo()) {
                if (Objects.isNull(inputDto.getUserNo()) && Objects.isNull(inputDto.getCustomer())) {
                    return null;
                }
                String customerNo = StringUtils.isNotBlank(inputDto.getUserNo()) ? inputDto.getUserNo() : inputDto.getCustomer();
                if (customerNo.equals(invProperty.getCustomerNo())) {
                    match = true;
                } else {
                    continue;
                }

            }else {
                if(StringUtils.isBlank(invProperty.getCustomerNo())){
                    match = true;
                } else {
                    continue;
                }
            }
            if(match){
                result.add(invProperty);
            }
        }
        return result;

    }

    protected List<OpsInventoryProperty> getInvProperty(OpsInventoryMatchConfig matchConfig,
                                                        InventoryCkByOrderInputDto inputDto,String modelNo) throws OpsException {
        List<OpsInventoryProperty> result = new ArrayList<>();
        if(matchConfig.getMatchInventoryRisk()){
            List<RiskDto> riskDtos = inputDto.getRiskMap().get(modelNo);
            if(CollectionUtils.isEmpty(riskDtos)){
                return null;
            }
            for(RiskDto riskDto:riskDtos){
                if(matchConfig.getInventoryTypeCode().equals(riskDto.getInventoryTypeCode())){
                    OpsInventoryProperty p = new OpsInventoryProperty();
                    p.setInventoryPropertyId(riskDto.getInventoryPropertyId());
                    result.add(p);
                }
            }
            return result;
        }
        OpsInventoryType invType = cacheService.getInvType(matchConfig.getInventoryTypeCode());
        // 判断预约情报
        List<OpsInventoryProperty> invProperties = inputDto.getInvPropertyMap().get(modelNo);
        if(CollectionUtils.isEmpty(invProperties)){
            return null;
        }
        if ("QB_NO".equals(invType.getInventoryTypeCode())){
            for (OpsInventoryProperty invProperty : invProperties){
                if(StringUtils.isNotBlank(invProperty.getSalesInfoNo())){
                    result.add(invProperty);
                }
            }
            return result;
        }
        for (OpsInventoryProperty invProperty : invProperties) {
            // 1. 基础过滤：库存类型必须匹配
            if (!matchConfig.getInventoryTypeCode().equals(invProperty.getInventoryTypeCode())) {
                continue;
            }
            // 2. 基础过滤：营业情报不为空则跳过
            if (StringUtils.isNotBlank(invProperty.getSalesInfoNo())) {
                continue;
            }
            // --- 动态字段匹配逻辑 ---
            // 3. ProjectCode 匹配
            if (invType.getMatchProjectCode()) {
                // 需要匹配，但输入为空 -> 无法匹配，跳过
                if (StringUtils.isBlank(inputDto.getProjectCode())) continue;
                // 值不相等 -> 跳过
                if (!inputDto.getProjectCode().equals(invProperty.getProjectCode())) continue;
            } else {
                // 不需要匹配，但数据库字段不为空 -> 跳过（确保选中“无ProjectCode”的记录）
                if (StringUtils.isNotBlank(invProperty.getProjectCode())) continue;
            }

            // 4. PPL 匹配
            if (invType.getMathchPpl()) {
                if (StringUtils.isBlank(inputDto.getPpl())) continue;
                if (!inputDto.getPpl().equals(invProperty.getPpl())) continue;
            } else {
                if (StringUtils.isNotBlank(invProperty.getPpl())) continue;
            }

            // 5. GroupCustomerNo 匹配
            if (invType.getMatchGroupCustomerNo()) {
                if (StringUtils.isBlank(inputDto.getGroupCustomerNo())) continue;

                // 建议在循环外拆分，这里为了逻辑展示保留在内部，实际使用建议提取到循环外
                List<String> groupCustomerNoList = Arrays.asList(inputDto.getGroupCustomerNo().split(","));
                if (!groupCustomerNoList.contains(invProperty.getGroupCustomerNo())) continue;
            } else {
                if (StringUtils.isNotBlank(invProperty.getGroupCustomerNo())) continue;
            }

            // 6. CustomerNo 匹配
            if (invType.getMatchCustomerNo()) {
                // 判断输入是否为空
                if (Objects.isNull(inputDto.getUserNo()) && Objects.isNull(inputDto.getCustomer())) continue;

                String customerNo = StringUtils.isNotBlank(inputDto.getUserNo())
                        ? inputDto.getUserNo()
                        : inputDto.getCustomer();

                if (!customerNo.equals(invProperty.getCustomerNo())) continue;
            } else {
                if (StringUtils.isNotBlank(invProperty.getCustomerNo())) continue;
            }

            // --- 如果代码能运行到这里，说明所有条件都通过了 ---
            result.add(invProperty);
        }
        return result;
    }


    /**
     * @description：匹配库存属性
     * @author ：c02483
     * @date ：Created in 2021/9/12 21:17
     */
    protected List<OpsInventoryProperty> getOpsInventoryProperty(OpsInventoryMatchConfig matchConfig,
                                                                 InventoryCkByOrderInputDto inputDto) throws OpsException {

        OpsInventoryType opsInventoryType = opsInventoryTypeService
                .findInventoryTypeByCode(matchConfig.getInventoryTypeCode());
        if (Objects.isNull(opsInventoryType)) {
            throw Exceptions.OpsException(String.format("库存维度的属性未维护不存在" + matchConfig.getInventoryTypeCode()));
        }
        OpsInventoryPropertyExample example = new OpsInventoryPropertyExample();
        OpsInventoryPropertyExample.Criteria criteria = example.createCriteria();

        // 判断预约情报
        if ("QB_NO".equals(opsInventoryType.getInventoryTypeCode())) {
            if (StrUtil.isNotBlank(inputDto.getPreSaleNo())) {// 有预约情报号
                criteria.andSalesInfoNoEqualTo(inputDto.getPreSaleNo());
            } else {// 无预约情报号 直接返回null 继续
                return null;
            }
        } else {
            if (opsInventoryType.getMatchProjectCode()) {
                if (Objects.isNull(inputDto.getProjectCode())) {
                    return null;
                }
                criteria.andProjectCodeEqualTo(inputDto.getProjectCode());
            } else {
                criteria.andProjectCodeIsNull();
            }
            if (opsInventoryType.getMathchPpl()) {
                if (Objects.isNull(inputDto.getPpl())) {
                    return null;
                }
                criteria.andPplEqualTo(inputDto.getPpl());
            } else {
                criteria.andPplIsNull();
            }
            if (opsInventoryType.getMatchGroupCustomerNo()) {
                if (Objects.isNull(inputDto.getGroupCustomerNo())) {
                    return null;
                }
                //集团客户号处理 多个用逗号分隔
                List<String> gourpCustomerNo = Arrays.asList(inputDto.getGroupCustomerNo().split(","));
                criteria.andGroupCustomerNoIn(gourpCustomerNo);
                // criteria.andGroupCustomerNoEqualTo(inputDto.getGroupCustomerNo());
            } else {
                criteria.andGroupCustomerNoIsNull();
            }
            criteria.andSalesInfoNoIsNull();// 预约情报为空 通用库存
            //修改查询库存属性语句where的顺序C14717 begin -----
            if (opsInventoryType.getMatchCustomerNo()) {
                if (Objects.isNull(inputDto.getUserNo()) && Objects.isNull(inputDto.getCustomer())) {
                    return null;
                }
                if (StringUtils.isNotBlank(inputDto.getUserNo())) {
                    criteria.andCustomerNoEqualTo(inputDto.getUserNo());
                } else if (StringUtils.isNotBlank(inputDto.getCustomer())) {
                    criteria.andCustomerNoEqualTo(inputDto.getCustomer());
                }
            } else {
                criteria.andCustomerNoIsNull();
            }
            criteria.andInventoryTypeCodeEqualTo(matchConfig.getInventoryTypeCode());
        }
        criteria.andDelflagEqualTo(0);//C14717 调整顺序 end ------
        List<OpsInventoryProperty> list = opsInventoryPropertyMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (opsInventoryType.getMatchGroupCustomerNo()) {
            return list;
        } else {
            if (list.size() == 1) {
                return list;
            } else {
                throw Exceptions.OpsException("存在多行库存属性记录" + list.toString());
            }
        }
    }

    /**
     * 在途库存按发票号
     */
    public List<OpsInventoryMove> findOpsInventoryMoveByInvoiceNo(String invoiceNo) throws OpsException {
        OpsInventoryMoveExample ex = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = ex.createCriteria();
        criteria.andDelflagEqualTo(0);
        if (StrUtil.isNotBlank(invoiceNo)) {
            criteria.andInvoicenoEqualTo(invoiceNo);
        }
        return opsInventoryMoveMapper.selectByExample(ex);
    }

    /**
     * @description： 在途库存操作状态 1，采购接单（写入在途是默认写入1） 2.预到货，预到货更变发票号默认写入2 3.发票到货 按发票更变
     * 4.发票签收，按发票更变 5.到货确认，按发票更变
     * @author ：B28029
     * @date ：Created in 2022/5/24 11:12
     */
    public void updateInventoryMoveOptStatusByInvoiceNo(String invoiceNo, Long invoiceId, OptStatusEnum statusEnum)
            throws OpsException {
        if (StringUtil.isEmpty(invoiceNo)) {
            throw Exceptions.OpsException("发票号不可为空值");
        }
        int rows = 0;
        //发票确认（关务导入，发票导入）
        if (statusEnum == OptStatusEnum.INVOICE_CONFIRM) {
            rows = opsInventoryDao.updateOptStatusInventoryMoveConfirm(invoiceNo, invoiceId, statusEnum.getCode());
        } else {
            rows = opsInventoryDao.updateOptStatusInventoryMoveByInvoiceNo(invoiceNo, invoiceId, statusEnum.getCode());
        }
        if (rows < 1) {
            throw Exceptions.OpsException("更变操作状态错误。发票号：" + invoiceNo, invoiceNo);
        }
    }

    /**
     * 修改签收仓库
     */
    public void updateSignWarehouseByInvoiceNo(String invoiceNo, Long invoiceId, String signWarehouseCode) throws OpsException {
        if (StringUtil.isEmpty(invoiceNo)) {
            throw Exceptions.OpsException("发票号不可为空值");
        }
        OpsInventoryMoveExample e = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = e.createCriteria();
        criteria.andDelflagEqualTo(0).andInvoicenoEqualTo(invoiceNo);
        if (invoiceId != null) {
            criteria.andInvoiceidEqualTo(invoiceId);
        }
        criteria.andInventoryStatusEqualTo(InventoryStatusEnum.CGTRANS.getCode());
        OpsInventoryMove update = new OpsInventoryMove();
        update.setSignWarehouseCode(signWarehouseCode);
        update.setModifyTime(new Date());
        int rows = opsInventoryMoveMapper.updateByExampleSelective(update, e);
        // int rows = opsInventoryDao.updateInventoryMoveSignWarehouseByInvoiceNo(invoiceNo, signWarehouseCode);
        if (rows < 1) {
            throw Exceptions.OpsException("库存更新异常ID" + invoiceNo);
        }
    }

    /**
     * @description：通过发票ID查询在途库存
     * @author ：c02483
     * @date ：Created in 2021/11/12 15:38
     */
    public Long countOpsInventoryMoveListByInvoiceNo(String invoiceNo) {
        OpsInventoryMoveExample example = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = example.createCriteria();
        criteria.andInvoicenoEqualTo(invoiceNo).andDelflagEqualTo(0);
        return opsInventoryMoveMapper.countByExample(example);
    }

    /**
     * PO转定（仅在供应商接单的状态）
     *
     * @param inventoryId       在途表ID
     * @param supplierId        供应商ID
     * @param signWarehouseCode 签收仓库代码
     */
    public void updateInventoryMoveConversionByInventoryId(Long inventoryId, String supplierId, String signWarehouseCode, Long version) throws OpsException {
        int rows = opsInventoryDao.updateInventoryMoveConversionByInventoryId(inventoryId, supplierId, signWarehouseCode, version);
        if (rows < 1) {
            throw Exceptions.OpsException("更新在途库存更新异常ID" + inventoryId);
        }
    }

    /**
     * 按PO查在途表数据(发票号)
     */
    public List<OpsInventoryMove> getOpsInventoryMoveTransList(String invoiceNo, String associateNo,
                                                               Integer associateNoItem, Integer splititemno) {
        OpsInventoryMoveExample example = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = example.createCriteria();
        criteria.andInvoicenoEqualTo(invoiceNo).andAssociateNoEqualTo(associateNo)
                .andAssociateNoItemEqualTo(associateNoItem).andDelflagEqualTo(0);
        if (Objects.nonNull(splititemno)) {
            criteria.andSplititemnoEqualTo(splititemno);
        }
        return opsInventoryMoveMapper.selectByExample(example);
    }

    /**
     * P转T1
     */
    public void updateInventoryMovePToT(Long inventoryId, String invoiceNo, Long invoiceId, String inventoryStatus,
                                        String supplierId, Integer optStatus, Long version) throws OpsException {
        OpsInventoryMove opsInventoryMove = new OpsInventoryMove();
        opsInventoryMove.setInventoryId(inventoryId);
        opsInventoryMove.setInvoiceno(invoiceNo);
        opsInventoryMove.setInvoiceid(invoiceId);
        opsInventoryMove.setInventoryStatus(inventoryStatus);
        opsInventoryMove.setSupplierid(supplierId);
        opsInventoryMove.setOptStatus(optStatus);
        opsInventoryMove.setVersion(version + 1);
        int rows = opsInventoryMoveMapper.updateByPrimaryKeySelective(opsInventoryMove);
        if (rows < 1) {
            throw Exceptions.OpsException("更新在途库存更新异常ID" + inventoryId);
        }
    }

    /**
     * 预到货存在PO数据，发票导入没PO数据，把T1改成P（还回P池里）
     */
    public void updateInventoryMoveTToP(Long invoiceId, String invoiceNo, String orderNo, Integer itemNo, Integer split) throws OpsException {
        OpsInventoryMoveExample example = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = example.createCriteria();
        criteria.andInvoiceidEqualTo(invoiceId).andInvoicenoEqualTo(invoiceNo)
                .andAssociateNoEqualTo(orderNo).andAssociateNoItemEqualTo(itemNo).andAssociateNoSplitnoEqualTo(split)
                .andDelflagEqualTo(0);
        OpsInventoryMove opsInventoryMove = new OpsInventoryMove();
        opsInventoryMove.setInvoiceid(0l);
        opsInventoryMove.setInvoiceno("");
        opsInventoryMove.setInventoryStatus(InventoryStatusEnum.PRODUCE.getCode());
        opsInventoryMove.setOptStatus(1);
        opsInventoryMove.setModifier(UserDto.WMS.getUserName());
        opsInventoryMove.setModifyTime(new Date());
        int rows = opsInventoryMoveMapper.updateByExampleSelective(opsInventoryMove, example);
        if (rows < 1) {
            throw Exceptions.OpsException("更新在途T1转P错误.po:" + orderNo + "-" + itemNo + "-" + split);
        }
    }

    public int updatePreReceiveDateByInvoiceId(Long invoiceId, Date preReceiveDate) {
        OpsInventoryMoveExample example = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = example.createCriteria();
        criteria.andInvoiceidEqualTo(invoiceId).andDelflagEqualTo(0);
        OpsInventoryMove opsInventoryMove = new OpsInventoryMove();
        opsInventoryMove.setPrereceivedate(preReceiveDate);
        opsInventoryMove.setModifier(UserDto.WMS.getUserName());
        opsInventoryMove.setModifyTime(new Date());
        return opsInventoryMoveMapper.updateByExampleSelective(opsInventoryMove, example);
    }

    /**
     * C14717 doConfirm 扣减库存
     */
    //bugid:10716 c14717 20230610
    @InvLog(apiName = "库存数量变动日志")
    public void subQtyAndPreDoConfirm(Long id, int qty, int preQty, String tableType, UserDto userDto)
            throws OpsException {
        int flag = 0;
        //重试次数读取配置文件3次
        // bugid:13647 20240304 c14717
        qty = -1 * qty;
        preQty = -1 * preQty;
        for (int i = 0; i < opsCommonConfig.getCoInvRetryFrequency(); i++) {
            //删除剩余在途库存
            try {
                if (InventoryTableTypeEnum.TRANS.getType().equals(tableType)) {
                    throw Exceptions.OpsException("在途库存只记录，不扣库存");
                } else if (InventoryTableTypeEnum.NORMAL.getType().equals(tableType)) {
                    //bugid:10716 c14717 20230610
                    OpsInventory inventory = opsInventoryMapper.selectByPrimaryKey(id);
                    int rows = 0;
                    if (inventory.getPrepareQuantity() + preQty < 0) {
                        int target = inventory.getPrepareQuantity() + preQty;
                        preQty = (preQty - target);// 预占数量为0  期望结果 inventory.getPrepareQuantity() + preQty = 0;
                    }
                    rows = opsInventoryDao.updateQtyInventoryWithPreAddOutTime(qty, preQty, inventory.getInventoryId(),
                            inventory.getVersion(), userDto.getUserName());
                    if (rows < 1) {
                        throw Exceptions.OpsException("doconfirm库存更新异常");
                    }
                } else {
                    throw Exceptions.OpsException("库存更新异常tableType异常", tableType);
                }
                //处理成功
                flag = 1;
                break;
            } catch (OpsException e) {
                log.error("doconfirm", e);
            }
        }
        //处理失败抛出异常
        if (flag == 0) {
            throw Exceptions.OpsException("doconfirm时库存更新异常");
        }
    }

}
