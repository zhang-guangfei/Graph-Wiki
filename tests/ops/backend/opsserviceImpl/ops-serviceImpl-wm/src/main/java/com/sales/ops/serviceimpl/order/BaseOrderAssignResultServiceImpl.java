package com.sales.ops.serviceimpl.order;

import cn.hutool.json.JSONUtil;
import com.github.javaparser.utils.Log;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsOrderAssignResultMapper;
import com.sales.ops.db.dao.SupplierMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.CommonMqpper;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.service.ba.OpsWarehouseSalesbranchService;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.order.BaseOrderAssignResultService;
import com.sales.ops.serviceimpl.order.result.NewResult;
import com.sales.ops.serviceimpl.order.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author C12961
 * @date 2022/4/16 10:48
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseOrderAssignResultServiceImpl implements BaseOrderAssignResultService {

    @Resource
    private OpsOrderAssignResultMapper opsOrderAssignResultMapper;
    @Resource
    private SupplierMapper supplierMapper;
    @Resource
    private BaseCustomerOrderService baseCustomerOrderService;
    @Resource
    private OpsWarehouseSalesbranchService opsWarehouseSalesbranchService;

    @Resource
    private CommonMqpper commonMqpper;

    // P,T1,T3,W
    private static final List<String> MOVE_STATUS_LIST = Arrays.asList(InventoryStatusEnum.PRODUCE.getCode(), InventoryStatusEnum.CGTRANS.getCode(),
            InventoryStatusEnum.DBTRANS.getCode(), InventoryStatusEnum.W.getCode());

    // CG,T,N，其他未知类型放最后
    private static final List<String> STOCK_TYPE_PRIORITY = Arrays.asList(OrderStockTypeEnum.CG.getType(), OrderStockTypeEnum.ZT.getType(), OrderStockTypeEnum.ZK.getType());

    private static final Comparator<OpsOrderAssignResult> STOCK_TYPE_COMPARATOR = Comparator.comparingInt(item -> getPriorityIndex(item.getStockType(), STOCK_TYPE_PRIORITY));

    private static int getPriorityIndex(String type, List<String> priorityList) {
        int index = priorityList.indexOf(type);
        return index < 0 ? Integer.MAX_VALUE : index;
    }

    // 记录订单分配结果，如果存在，则先删除
    @Override
    public void insertOrderAssignResult(List<OpsOrderAssignResult> list, Date allotStartTime, Date allotEndTime) throws OpsException {
        if (CollectionUtils.isNotEmpty(list)) {
            OpsOrderAssignResult result = list.get(0);
            OpsOrderAssignResultExample example = new OpsOrderAssignResultExample();
            example.createCriteria()
                    .andOrderNoEqualTo(result.getOrderNo())
                    .andOrderItemEqualTo(result.getOrderItem());
            long count = opsOrderAssignResultMapper.countByExample(example);
            if (count != 0) {
                deleteResultOrder(result.getOrderNo(), result.getOrderItem());
            }
        }
        for (OpsOrderAssignResult result : list) {
            result.setDelflag(0);
            result.setSourceType(0);
            result.setCreateTime(new Date());
            result.setUpdateTime(new Date());
            result.setCreateUser(UserDto.AUTO.getUserName());
            result.setUpdateUser(UserDto.AUTO.getUserName());
            opsOrderAssignResultMapper.insertSelective(result);
        }
        List<OpsOrderAssignResult> orderAssignList = list;
        if (CollectionUtils.isEmpty(orderAssignList)) {
            return;
        }
        String stockType = null;
        String stockCode = null;
        String inventoryTypeCode = null;

        OpsOrderAssignResult result = orderAssignList.get(0);
        String orderNo = result.getOrderNo();
        Integer orderItem = result.getOrderItem();

        // 1.将所有"P", "T1", "T3", "W"转换成T,只对CG,T,N排序
        List<OpsOrderAssignResult> assignList = orderAssignList.stream().map(item -> {
            List<String> moveStatus = MOVE_STATUS_LIST;
            if (moveStatus.contains(item.getStockType())) {
                item.setStockType(OrderStockTypeEnum.ZT.getType());
            }
            return item;
        }).collect(Collectors.toList());
        if (assignList.size() == 1) {
            OpsOrderAssignResult assign = assignList.get(0);
            stockType = assign.getStockType();
            stockCode = assign.getStockCode();
            inventoryTypeCode = assign.getInventoryTypeCode();
            Rcvdetail update = new Rcvdetail();
            update.setStockType(stockType);
            update.setStockCode(stockCode);
            update.setInventoryTypeCode(inventoryTypeCode);
            update.setProdFlag(OrderSplitTypeEnum.ALL.getSplitType());
            update.setAllotStartTime(allotStartTime);
            update.setAllotTime(allotEndTime);
            //默认值
            if (allotEndTime == null) {
                Date allotTime = orderAssignList.stream().map(OpsOrderAssignResult::getCreateTime).sorted().findFirst().orElse(null);
                update.setAllotTime(allotTime);
            }
            baseCustomerOrderService.updateRcvDetail(orderNo, orderItem, update);
            return;
        }
        // 2.定义排序规则 CG、T、N
        Comparator<OpsOrderAssignResult> stockTypeComparator = STOCK_TYPE_COMPARATOR;
        // 使用排序规则进行排序 选出最靠前的出库区分
        stockType = assignList.stream().sorted(stockTypeComparator).collect(Collectors.toList()).get(0).getStockType();
        // 3.选出要二次排序的数据
        String finalStockType = stockType;
        List<OpsOrderAssignResult> targetAssigns = assignList.stream().filter(item -> item.getStockType().equals(finalStockType)).collect(Collectors.toList());
        // 定义排序规则
        // 采购：null(supplier), max(supplier=>Stddeliveryday), otherData
        // 在途在库：max((warehouse,deptNo)=> DeliveryDay), null(warehouse), null(dept), null(config), otherData
        Comparator<OpsOrderAssignResult> stockCodeComparator = Comparator.comparing((Function<OpsOrderAssignResult, Integer>) item -> {
            // 如果采购，则按照供应商发货日期排序，选出最大发货日期
            if (OrderStockTypeEnum.CG.getType().equals(item.getStockType())) {
                // 如果无供应商，则放在最前边
                if (org.apache.commons.lang3.StringUtils.isBlank(item.getStockCode())) {
                    return Integer.MAX_VALUE;
                }
                Supplier supplier = getSupplier(item.getStockCode());
                // 在表中找不到，丢弃
                if (supplier == null) {
                    return Integer.MIN_VALUE;
                }
                return supplier.getStddeliveryday();
            } else if (OrderStockTypeEnum.ZT.getType().equals(item.getStockType())
                    || OrderStockTypeEnum.ZK.getType().equals(item.getStockType())) {
                // 如果部门代码为空，丢弃
                RcvView rcv = baseCustomerOrderService.findRcvViewByNo(orderNo, orderItem);
                if (rcv == null) {
                    return Integer.MIN_VALUE;
                }
                String deptNo = rcv.getDeliveryDeptNo();
                if (deptNo == null) {
                    return Integer.MIN_VALUE;
                }
                // 在配置表中找不到，丢弃
                List<OpsWarehouseSalesbranchConfig> configs = opsWarehouseSalesbranchService.getConfigBySalesBranchIdAndWarehouse(deptNo, item.getStockCode());
                if (CollectionUtils.isEmpty(configs)) {
                    return Integer.MIN_VALUE;
                }
                OpsWarehouseSalesbranchConfig config = configs.get(0);
                return config.getDeliveryDay();
            } else {
                return Integer.MIN_VALUE;
            }
        }).reversed();
        assignList = targetAssigns.stream().sorted(stockCodeComparator).collect(Collectors.toList());
        OpsOrderAssignResult assign = assignList.get(0);
        stockCode = assign.getStockCode();
        inventoryTypeCode = assign.getInventoryTypeCode();
        //bug20654 字段取值规则废弃,使用更标准的分配开始时间和结束时间
        //Date allotTime = orderAssignList.stream().map(OpsOrderAssignResult::getCreateTime).sorted().findFirst().orElse(null);
        String pordFlag = null;
        if (orderAssignList.size() == 1) {
            pordFlag = OrderSplitTypeEnum.ALL.getSplitType();
        } else {
            boolean assModelNo = orderAssignList.stream().map(OpsOrderAssignResult::getModelno).distinct().count() > 1;
            pordFlag = assModelNo ? OrderSplitTypeEnum.ASSModelNo.getSplitType() : OrderSplitTypeEnum.ASSQTY.getSplitType();
        }
        Rcvdetail update = new Rcvdetail();
        update.setStockType(stockType);
        update.setStockCode(stockCode);
        update.setInventoryTypeCode(inventoryTypeCode);
        update.setProdFlag(pordFlag);
        update.setAllotStartTime(allotStartTime);
        update.setAllotTime(allotEndTime);
        //默认值
        if (allotEndTime == null) {
            Date allotTime = orderAssignList.stream().map(OpsOrderAssignResult::getCreateTime).sorted().findFirst().orElse(null);
            update.setAllotTime(allotTime);
        }
        baseCustomerOrderService.updateRcvDetail(orderNo, orderItem, update);
    }

    @Override
    public void updateRcvStockInfoByResult(List<OpsOrderAssignResult> orderAssignList) throws OpsException {
        if (CollectionUtils.isEmpty(orderAssignList)) {
            return;
        }
        String stockType = null;
        String stockCode = null;
        String inventoryTypeCode = null;

        OpsOrderAssignResult result = orderAssignList.get(0);
        String orderNo = result.getOrderNo();
        Integer orderItem = result.getOrderItem();

        // 1.将所有"P", "T1", "T3", "W"转换成T,只对CG,T,N排序
        List<OpsOrderAssignResult> list = orderAssignList.stream().map(item -> {
            //"P", "T1", "T3", "W"
            if (MOVE_STATUS_LIST.contains(item.getStockType())) {
                item.setStockType(OrderStockTypeEnum.ZT.getType());
            }
            return item;
        }).collect(Collectors.toList());
        if (list.size() == 1) {
            OpsOrderAssignResult assign = list.get(0);
            stockType = assign.getStockType();
            stockCode = assign.getStockCode();
            inventoryTypeCode = assign.getInventoryTypeCode();
            baseCustomerOrderService.updateStockInfo(orderNo, orderItem, stockType, stockCode, inventoryTypeCode);
            return;
        }
        // 2.定义排序规则 CG、T、N
        Comparator<OpsOrderAssignResult> stockTypeComparator = STOCK_TYPE_COMPARATOR;
        // 使用排序规则进行排序 选出最靠前的出库区分
        stockType = list.stream().sorted(stockTypeComparator).collect(Collectors.toList()).get(0).getStockType();
        // 3.选出要二次排序的数据
        String finalStockType = stockType;
        List<OpsOrderAssignResult> targetAssigns = list.stream().filter(item -> item.getStockType().equals(finalStockType)).collect(Collectors.toList());


        // 定义排序规则
        // 采购：null(supplier), max(supplier=>Stddeliveryday), otherData
        // 在途在库：max((warehouse,deptNo)=> DeliveryDay), null(warehouse), null(dept), null(config), otherData
        Comparator<OpsOrderAssignResult> stockCodeComparator = Comparator.comparing((Function<OpsOrderAssignResult, Integer>) item -> {
            // 如果采购，则按照供应商发货日期排序，选出最大发货日期
            if (OrderStockTypeEnum.CG.getType().equals(item.getStockType())) {
                // 如果无供应商，则放在最前边
                if (org.apache.commons.lang3.StringUtils.isBlank(item.getStockCode())) {
                    return Integer.MAX_VALUE;
                }
                Supplier supplier = getSupplier(item.getStockCode());
                // 在表中找不到，丢弃
                if (supplier == null) {
                    return Integer.MIN_VALUE;
                }
                return supplier.getStddeliveryday();
            } else if (OrderStockTypeEnum.ZT.getType().equals(item.getStockType())
                    || OrderStockTypeEnum.ZK.getType().equals(item.getStockType())) {
                // 如果部门代码为空，丢弃
                RcvView rcv = baseCustomerOrderService.findRcvViewByNo(orderNo, orderItem);
                if (rcv == null) {
                    return Integer.MIN_VALUE;
                }
                String deptNo = rcv.getDeliveryDeptNo();
                if (deptNo == null) {
                    return Integer.MIN_VALUE;
                }
                // 在配置表中找不到，丢弃
                List<OpsWarehouseSalesbranchConfig> configs = opsWarehouseSalesbranchService.getConfigBySalesBranchIdAndWarehouse(deptNo, item.getStockCode());
                if (CollectionUtils.isEmpty(configs)) {
                    return Integer.MIN_VALUE;
                }
                OpsWarehouseSalesbranchConfig config = configs.get(0);
                return config.getDeliveryDay();
            } else {
                return Integer.MIN_VALUE;
            }
        }).reversed();
        list = targetAssigns.stream().sorted(stockCodeComparator).collect(Collectors.toList());
        OpsOrderAssignResult assign = list.get(0);
        stockCode = assign.getStockCode();
        inventoryTypeCode = assign.getInventoryTypeCode();
        if (StringUtils.isNotBlank(stockType) && StringUtils.equals(stockType, "EXCEPTION")) {
            stockType = "NA";
        }
        baseCustomerOrderService.updateStockInfo(orderNo, orderItem, stockType, stockCode, inventoryTypeCode);
    }

    private Supplier getSupplier(String supplierId) {
        SupplierExample supplierExample = new SupplierExample();
        supplierExample.createCriteria().andIdEqualTo(supplierId);
        List<Supplier> suppliers = supplierMapper.selectByExample(supplierExample);
        if (CollectionUtils.isNotEmpty(suppliers)) {
            return suppliers.get(0);
        }
        return null;
    }

    @Override
    public List<OpsOrderAssignResult> findByOrder(String orderId, Integer orderItem) {
        OpsOrderAssignResultExample ex = new OpsOrderAssignResultExample();
        ex.createCriteria().andDelflagEqualTo(0).andOrderNoEqualTo(orderId).andOrderItemEqualTo(orderItem);
        return opsOrderAssignResultMapper.selectByExample(ex);
    }

    @Override
    public List<OpsOrderAssignResult> findDeletedByOrder(String orderId, Integer orderItem) {
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByKey(orderId, orderItem);
        if (rcvdetail.getStatus().equals(RcvOrderStatusEnum.CANCEL.getType())) {
            OpsOrderAssignResultExample ex = new OpsOrderAssignResultExample();
            ex.createCriteria().andOrderNoEqualTo(orderId).andOrderItemEqualTo(orderItem);
            ex.setOrderByClause("update_time desc");
            List<OpsOrderAssignResult> list = opsOrderAssignResultMapper.selectByExample(ex);
            //非型号拆分时，如果result总数量大于rcv数量，则按照更新时间取足够数量
            if (OrderSplitTypeEnum.notAssModel(rcvdetail.getProdFlag())) {
                Integer resultQty = list.stream().map(item -> item.getQuantity()).reduce((a, b) -> a + b).orElse(0);
                if (resultQty > rcvdetail.getQuantity()) {
                    List<OpsOrderAssignResult> resultList = new ArrayList<>();
                    int qty = 0;
                    //获取足够rcv数量的result条数
                    for (OpsOrderAssignResult result : list) {
                        resultList.add(result);
                        qty += result.getQuantity();
                        if (qty >= rcvdetail.getQuantity()) {
                            break;
                        }
                    }
                    return resultList;
                }
                return list;
            } else {
                //查询最大的更新时间，并筛选出此时间的result
                if (CollectionUtils.isNotEmpty(list)) {
                    Date maxUpdateTime = list.get(0).getUpdateTime();
                    return list.stream().filter(item -> item.getUpdateTime().equals(maxUpdateTime)).collect(Collectors.toList());
                }
            }
        }
        return new ArrayList<>();
    }


    @Override
    public List<OpsOrderAssignResult> findByPo(String poNo, Integer poItem, Integer poSplitNo) {
        OpsOrderAssignResultExample ex = new OpsOrderAssignResultExample();
        OpsOrderAssignResultExample.Criteria criteria = ex.createCriteria();
        criteria.andDelflagEqualTo(0).andAssociateNoEqualTo(poNo).andAssociateNoItemEqualTo(poItem);
        if (poSplitNo == null) {
            criteria.andAssociateNoSplitNoEqualTo(0);
        } else {
            criteria.andAssociateNoSplitNoEqualTo(poSplitNo);
        }
        List<OpsOrderAssignResult> opsOrderAssignResults = opsOrderAssignResultMapper.selectByExample(ex);

        // 采购详情界面追加显示先行在库补库单预约情况
        String fullOrderNo = "";
        if (poSplitNo == null) {
            fullOrderNo = poNo+"-"+poItem;
        } else {
            fullOrderNo = poNo+"-"+poItem+"-"+poSplitNo;
        }
        List<OpsOrderAssignResult> resultList = commonMqpper.queryPreStockResult(fullOrderNo);
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (OpsOrderAssignResult item : resultList) {
                com.smc.smccloud.model.order.OrderNoInfo orderNoInfo = new com.smc.smccloud.model.order.OrderNoInfo().convertFullOrderNo(item.getOrderNo());
                item.setOrderNo(orderNoInfo.getOrderNo());
                item.setOrderItem(orderNoInfo.getItemNo());
            }
            opsOrderAssignResults.addAll(resultList);
        }
        return opsOrderAssignResults;
    }

    @Override
    public OpsOrderAssignResult findByOrderAndPo(String orderId, Integer orderItem, String modelno, String poNo, Integer poItem, Integer poSplitNo) throws OpsException {
        OpsOrderAssignResultExample ex = new OpsOrderAssignResultExample();
        OpsOrderAssignResultExample.Criteria criteria = ex.createCriteria();
        criteria.andDelflagEqualTo(0).andOrderNoEqualTo(orderId).andOrderItemEqualTo(orderItem)
        ;
        if (StringUtils.isNotBlank(poNo)) {
            criteria.andAssociateNoEqualTo(poNo).andAssociateNoItemEqualTo(poItem);
            criteria.andAssociateNoSplitNoEqualTo(poSplitNo);
        } else {
            if (StringUtils.isNotBlank(modelno)) {
                criteria.andModelnoEqualTo(modelno);
            }
            criteria.andAssociateNoIsNull();
            criteria.andStockTypeEqualTo(OrderStockTypeEnum.CG.getType());
        }
        List<OpsOrderAssignResult> results = opsOrderAssignResultMapper.selectByExample(ex);
        if (results.size() == 1) {
            return results.get(0);
        } else {
            throw Exceptions.OpsException("查询出" + results.size() + "条AssignResult");
        }
    }

    @Override
    public int updateByPurchaseOrder(String orderId, Integer orderItem, String modelno, String poNo, Integer poItem, Integer poSplitNo, String supplier) throws OpsException {
        OpsOrderAssignResultExample ex = new OpsOrderAssignResultExample();
        OpsOrderAssignResultExample.Criteria criteria = ex.createCriteria();
        criteria.andOrderNoEqualTo(orderId)
                .andOrderItemEqualTo(orderItem);
        criteria.andDelflagEqualTo(0).andModelnoEqualTo(modelno).andStockTypeEqualTo(OrderStockTypeEnum.CG.getType());
        OpsOrderAssignResult update = new OpsOrderAssignResult();
        update.setStockCode(supplier);
        update.setSupplierid(supplier);
        update.setAssociateNo(poNo);
        update.setAssociateNoItem(poItem);
        update.setAssociateNoSplitNo(Optional.ofNullable(poSplitNo).orElse(0));
        update.setUpdateUser("采购更新");
        update.setUpdateTime(new Date());
        opsOrderAssignResultMapper.updateByExampleSelective(update, ex);
        List<OpsOrderAssignResult> list = findByOrder(orderId, orderItem);
        updateRcvStockInfoByResult(list);
        return 1;
    }


    @Override
    public int updateByPurchaseOrderUpdateSupplier(String orderId, Integer orderItem, String modelno, String poNo, Integer poItem, Integer poSplitNo, String supplier) throws OpsException {
        OpsOrderAssignResultExample ex = new OpsOrderAssignResultExample();
        OpsOrderAssignResultExample.Criteria criteria = ex.createCriteria();
        criteria.andOrderNoEqualTo(orderId).andOrderItemEqualTo(orderItem);
        criteria.andDelflagEqualTo(0).andModelnoEqualTo(modelno).andStockTypeEqualTo(OrderStockTypeEnum.CG.getType());
        criteria.andAssociateNoEqualTo(poNo).andAssociateNoItemEqualTo(poItem).andAssociateNoSplitNoEqualTo(Optional.ofNullable(poSplitNo).orElse(0));
        List<OpsOrderAssignResult> target = opsOrderAssignResultMapper.selectByExample(ex);
        if (CollectionUtils.isNotEmpty(target)) {
            //只要有一个供应商不一致，就更新
            boolean enableUpdate = false;
            for (OpsOrderAssignResult result : target) {
                if (!StringUtils.equals(result.getSupplierid(), supplier) || !StringUtils.equals(result.getStockCode(), supplier)) {
                    enableUpdate = true;
                    break;
                }
            }
            if (enableUpdate) {
                OpsOrderAssignResult update = new OpsOrderAssignResult();
                update.setStockCode(supplier);
                update.setSupplierid(supplier);
                update.setUpdateUser("采购更新供应商");
                update.setUpdateTime(new Date());
                opsOrderAssignResultMapper.updateByExampleSelective(update, ex);
            }
            List<OpsOrderAssignResult> list = findByOrder(orderId, orderItem);
            updateRcvStockInfoByResult(list);
        }
        return 1;
    }

    @Override
    public void deleteById(Long id) {
        OpsOrderAssignResult update = new OpsOrderAssignResult();
        update.setId(id);
        update.setDelflag(1);
        update.setUpdateUser("删除result");
        update.setUpdateTime(new Date());
        opsOrderAssignResultMapper.updateByPrimaryKeySelective(update);
    }

    /**
     * 庞然大雾，没有任何人能读懂了，暂时能用，建议替换掉
     *
     * @param oldResult
     * @param newResult
     * @return
     * @throws OpsException
     */
    @Override
    public int updateResultForOrderAdjust(Result oldResult, NewResult newResult) throws OpsException {
        log.info("转订变更result:{},oldResult:{}\nnewResult:{}", oldResult.getOrderId() + "-" + oldResult.getOrderItem(),
                JSONUtil.toJsonPrettyStr(oldResult), JSONUtil.toJsonPrettyStr(newResult));
        OpsOrderAssignResultExample ex = new OpsOrderAssignResultExample();
        OpsOrderAssignResultExample.Criteria criteria = ex.createCriteria();
        criteria.andDelflagEqualTo(0).andOrderNoEqualTo(oldResult.getOrderId()).andOrderItemEqualTo(oldResult.getOrderItem())// 用十位单号查询
                .andModelnoEqualTo(oldResult.getModelno())// 且查询型号
        ;
        // 有关联单号
        OrderNoInfo po = oldResult.getPo();
        if (oldResult.isDB()) {
            // todo: 先采购，后调拨，调拨W时转订，此时result可能会查错，因为此时给的关联单号可能不是当初采购的单号，之前的采购单号已经获取不到了
            log.info("此转订为调拨单转订为其他，无法定位result");
            throw Exceptions.OpsException("此转订为调拨单转订为其他，无法定位result");
        } else {
            // 转订之前是采购
            if (oldResult.isCG()) {// 查询出库区分：采购
                criteria.andStockTypeIn(Arrays.asList(OrderStockTypeEnum.CG.getType(), DoWaitTypeEnum.EXCEPTION.getType()));
            } else {// 查询出库区分：预约采购在途
                criteria.andStockTypeIn(Arrays.asList(
                        InventoryStatusEnum.PRODUCE.getCode(),
                        InventoryStatusEnum.CGTRANS.getCode(),
                        InventoryStatusEnum.W.getCode()));
            }
            // 转订之前有关联单号
            boolean hasAssociate = StringUtils.isNotBlank(po.getOrderNo());
            if (hasAssociate) {
                criteria.andAssociateNoEqualTo(po.getOrderNo()).andAssociateNoItemEqualTo(po.getItemNo())// 且查询关联单号
                        .andAssociateNoSplitNoEqualTo(po.getSplitNoNotNull());
            }
        }
        List<OpsOrderAssignResult> results = opsOrderAssignResultMapper.selectByExample(ex);
        Log.info("查询到result数据：{}", JSONUtil.toJsonStr(results));
        if (CollectionUtils.isNotEmpty(results)) {
            boolean success = false;
            for (OpsOrderAssignResult result : results) {
                if (Objects.equals(oldResult.getQty(), result.getQuantity())) {
                    log.info("删除原result:{}", JSONUtil.toJsonPrettyStr(result));
                    deleteById(result.getId());
                    log.info("创建新result:{}", JSONUtil.toJsonPrettyStr(newResult.toOps()));
                    opsOrderAssignResultMapper.insertSelective(newResult.toOps());
                    success = true;
                    break;
                } else if (oldResult.getQty() < result.getQuantity()) {
                    log.info("拆分原result:{}", JSONUtil.toJsonPrettyStr(result));
                    updateResultSubQtyForOldResult(result, oldResult.getQty());
                    log.info("创建新result:{}", JSONUtil.toJsonPrettyStr(newResult.toOps()));
                    opsOrderAssignResultMapper.insertSelective(newResult.toOps());
                    success = true;
                    break;
                } else if (oldResult.getQty() > result.getQuantity()) {
                    log.info("要拆分的数量大于原采购的数量{}：{}", oldResult.getQty(), JSONUtil.toJsonPrettyStr(result));
                    success = false;
                }
            }
            if (!success) {
                throw Exceptions.OpsException("要拆分的数量大于原采购的数量：" + JSONUtil.toJsonPrettyStr(results));
            }
        } else {
            // 放宽查询条件，再次查询，
            // 如果查出多条，比较数量，
            // 如果没查出来，抛异常
            ex.clear();
            OpsOrderAssignResultExample.Criteria cri2 = ex.createCriteria();
            cri2.andDelflagEqualTo(0).andOrderNoEqualTo(oldResult.getOrderId()).andOrderItemEqualTo(oldResult.getOrderItem())// 用十位单号查询
                    .andModelnoEqualTo(oldResult.getModelno())
                    .andQuantityLessThanOrEqualTo(oldResult.getQty())
            ;// 且查询型号
            List<OpsOrderAssignResult> resultList = opsOrderAssignResultMapper.selectByExample(ex);
            if (CollectionUtils.isNotEmpty(resultList) && resultList.size() == 1) {
                OpsOrderAssignResult result = resultList.get(0);
                log.info("二次查询：查询到一条result:{}", JSONUtil.toJsonPrettyStr(result));
                if (Objects.equals(oldResult.getQty(), result.getQuantity())) {
                    log.info("删除原result:{}", JSONUtil.toJsonPrettyStr(result));
                    deleteById(result.getId());
                    log.info("创建新result:{}", JSONUtil.toJsonPrettyStr(newResult.toOps()));
                    opsOrderAssignResultMapper.insertSelective(newResult.toOps());
                } else if (oldResult.getQty() < result.getQuantity()) {
                    log.info("拆分原result:{}", JSONUtil.toJsonPrettyStr(result));
                    updateResultSubQtyForOldResult(result, oldResult.getQty());
                    log.info("创建新result:{}", JSONUtil.toJsonPrettyStr(newResult.toOps()));
                    opsOrderAssignResultMapper.insertSelective(newResult.toOps());
                }
            } else if (resultList.size() > 1) {
                log.info("result查询失败：二次查询，查询到多条result数据：{}", JSONUtil.toJsonPrettyStr(resultList));
                throw Exceptions.OpsException("result查询失败，二次查询，查询到多条result数据：" + JSONUtil.toJsonStr(resultList));
            } else {
                log.info("没有查询到result数据：{}", JSONUtil.toJsonPrettyStr(oldResult));
                throw Exceptions.OpsException("没有查询到result数据");
            }
        }
        List<OpsOrderAssignResult> list = findByOrder(oldResult.getOrderId(), oldResult.getOrderItem());
        updateRcvStockInfoByResult(list);
        return 0;
    }

    // 减去转订的数量
    private void updateResultSubQtyForOldResult(OpsOrderAssignResult result, int qty) {
        OpsOrderAssignResult update = new OpsOrderAssignResult();
        update.setId(result.getId());
        update.setQuantity(result.getQuantity() - qty);
        update.setSourceType(2);
        update.setUpdateTime(new Date());
        opsOrderAssignResultMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public int updateToException(String orderId, Integer orderItem, String modelno, String poNo, Integer poItem, Integer poSplitNo) throws OpsException {
        OpsOrderAssignResult update = findByOrderAndPo(orderId, orderItem, modelno, poNo, poItem, poSplitNo);
        update.setStockType("EXCEPTION");
        update.setStockCode(null);
        update.setInventoryTypeCode(null);
        update.setAssociateNo(null);
        update.setAssociateNoItem(null);
        update.setAssociateNoSplitNo(null);
        update.setSupplierid(null);
        update.setSourceType(1);
        update.setUpdateTime(new Date());
        update.setUpdateUser("采购单取消");
        int i = opsOrderAssignResultMapper.updateByPrimaryKey(update);
        return i;
    }


    @Override
    public int deleteResultOrder(String orderId, Integer orderItem) throws OpsException {
        OpsOrderAssignResultExample ex = new OpsOrderAssignResultExample();
        OpsOrderAssignResultExample.Criteria criteria = ex.createCriteria();
        criteria.andOrderNoEqualTo(orderId).andOrderItemEqualTo(orderItem).andDelflagEqualTo(0);
        OpsOrderAssignResult result = new OpsOrderAssignResult();
        result.setDelflag(1);
        result.setUpdateTime(new Date());
        result.setUpdateUser("删除result");
        return opsOrderAssignResultMapper.updateByExampleSelective(result, ex);
    }

    @Override
    public void updateResultForForceFinish(String orderId, Integer orderItem) throws OpsException {
        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByNo(orderId, orderItem);
        String prodFlag = rcvdetail.getProdFlag();
        if (OrderSplitTypeEnum.notAssModel(prodFlag)) {
            List<OpsOrderAssignResult> list = findByOrder(orderId, orderItem);
            OpsOrderAssignResult result = list.get(0);
            if (list.size() == 1) {
                if (result.getQuantity() > rcvdetail.getQuantity()) {
                    OpsOrderAssignResult update = new OpsOrderAssignResult();
                    update.setId(result.getId());
                    update.setQuantity(rcvdetail.getQuantity());
                    opsOrderAssignResultMapper.updateByPrimaryKeySelective(update);
                }

            } else {
                List<String> stockTypeEnumList = Arrays.asList(OrderStockTypeEnum.CG.getType(), InventoryStatusEnum.PRODUCE.getCode(), InventoryStatusEnum.CGTRANS.getCode(),
                        InventoryStatusEnum.DBTRANS.getCode(), InventoryStatusEnum.W.getCode(), OrderStockTypeEnum.ZT.getType(), OrderStockTypeEnum.ZK.getType());
                Comparator<OpsOrderAssignResult> comparator = Comparator.comparingInt((OpsOrderAssignResult item) -> getPriorityIndex(item.getStockType(), stockTypeEnumList))
                        .thenComparing(OpsOrderAssignResult::getInventoryTypeCode, Comparator.comparingInt(type -> Arrays.asList(InventoryTypeEnum.TY.getType()).indexOf(type)));
                Integer sumQty = list.stream().map(OpsOrderAssignResult::getQuantity).reduce(Integer::sum).orElse(0);
                int subQty = sumQty - rcvdetail.getQuantity();
                if (subQty > 0) {
                    list.sort(comparator);
                    for (OpsOrderAssignResult assignResult : list) {
                        if (subQty > 0) {
                            Integer quantity = assignResult.getQuantity();
                            if (quantity > subQty) {
                                //扣减数量
                                OpsOrderAssignResult update = new OpsOrderAssignResult();
                                update.setId(assignResult.getId());
                                update.setQuantity(quantity - subQty);
                                opsOrderAssignResultMapper.updateByPrimaryKeySelective(update);
                                subQty = 0;
                            } else {
                                //数量归0，并删除
                                OpsOrderAssignResult update = new OpsOrderAssignResult();
                                update.setId(assignResult.getId());
                                update.setQuantity(0);
                                update.setDelflag(1);
                                opsOrderAssignResultMapper.updateByPrimaryKeySelective(update);
                                subQty = subQty - quantity;
                            }
                        }
                    }
                }
            }
        }
        // bug 18457 2025-08-15 订单完纳的最后，增加操作：计算并更新rcvdetail的出库区分
        List<OpsOrderAssignResult> list = findByOrder(orderId, orderItem);
        updateRcvStockInfoByResult(list);
    }
}
