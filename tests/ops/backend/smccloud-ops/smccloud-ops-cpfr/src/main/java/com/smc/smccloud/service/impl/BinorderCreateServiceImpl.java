package com.smc.smccloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sales.ops.db.entity.OpsPoTranstype;
import com.sales.ops.dto.po.core.TransTypeParam;
import com.sales.ops.dto.purchase.RequestPurchaseInfoDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.sales.ops.feign.RequestPurchaseFeignApi;
import com.sales.ops.feign.purchase.PurchaseTransFeignApi;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.BinOrderCalcMapper;
import com.smc.smccloud.mapper.binorder.BinOrderAppMapper;
import com.smc.smccloud.mapper.binorder.BinOrderDetailMapper;
import com.smc.smccloud.mapper.binorder.BinOrderDetailSplitMapper;
import com.smc.smccloud.model.binorder.*;
import com.smc.smccloud.model.orderstate.OrderStateEnum;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.model.trans.TransOrderVO;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author edp02 @Date 2022/7/15 13:17
 */
@Service
@Slf4j
public class BinorderCreateServiceImpl implements BinorderCreateService {

    @Resource
    private BinOrderCalcMapper binOrderCalcMapper;
    @Resource
    private BinOrderDetailSplitMapper binOrderDetailSplitMapper;
    @Resource
    private BinOrderAppMapper binOrderAppMapper;
    @Resource
    private BinOrderDetailMapper binOrderDetailMapper;

    @Resource
    private BinOrderCalcService binOrderCalcService;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private RequestPurchaseFeignApi requestPurchaseFeignApi;
    @Resource
    private RedisManager redisUtil;
    @Resource
    private RedissonUtil redissonUtil;
    @Resource
    private OrderStateServiceFeignApi orderStateServiceFeignApi;
    @Resource
    private TransStockService transStockService;
    @Resource
    private PurchaseTransFeignApi purchaseTransFeignApi;

    @Override
    @Async
    @Transactional
    public void createOrderByAppId(Long appId) {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        int retry = 3;
        while (retry-- > 0) {
            ResultVo<Boolean> resultVo = onCreateOrderByAppId(appId);
            if (resultVo.isSuccess()) {
                return;
            }
            log.error("生成Bin订单失败：" + resultVo.getMessage() + appId);
        }
    }

    public ResultVo<Boolean> onCreateOrderByAppId(Long appId) {
        String lockKey = "ops:cpfr:binorder:order:" + appId;
        if (!redissonUtil.tryLock(lockKey, 1, 60 * 30, TimeUnit.SECONDS)) {
            log.error("lock :" + lockKey);
            return ResultVo.failure("正在生成订单中");
        }
        try {
            BinOrderAppDO appDO = binOrderAppMapper.selectById(appId);
            if (appDO == null) {
                return ResultVo.failure("该申请不存在" + appId);
            }
            if (appDO.getStatus() != 7) {
                return ResultVo.failure("当前状态不能生成订单" + appDO.getStatus());
            }
            ResultVo<WarehouseVO> warehouseVOResultVo = commonServiceFeignApi.getWarehouseInfoByCode(appDO.getWarehouseCode());

            if (!warehouseVOResultVo.isSuccess()) {
                return ResultVo.failure(appDO.getWarehouseCode() + warehouseVOResultVo.getMessage());
            }
            WarehouseVO warehouseVO = warehouseVOResultVo.getData();
            //先判断计算仓是否有补库能力，没有则直接返回“该仓不可补库”
            if (warehouseVO.getPrestockFlag().compareTo(0) == 0) {
                return ResultVo.failure(warehouseVO.getWarehouseCode() + "此仓库不可补库");
            }
            //若有补库能力，则判断是分库还是物流中心
            if (WarehouseTypeEnum.FDC.getHouseTypeCode().equalsIgnoreCase(warehouseVO.getWarehouseType())) {
                //若是分库，则判断是否有采购能力，有则使用该分库采购
                if (warehouseVO.getPurchaseFlag() == null || warehouseVO.getPurchaseFlag().compareTo(0) == 0) {
                    //没有则寻找上级物流中心
                    warehouseVOResultVo = commonServiceFeignApi.getWarehouseInfoByCode(warehouseVOResultVo.getData().getParentCode());
                    if (!warehouseVOResultVo.isSuccess()) {
                        return ResultVo.failure(appDO.getWarehouseCode() + warehouseVOResultVo.getMessage());
                    }
                    warehouseVO = warehouseVOResultVo.getData();
                    if (warehouseVO.getPurchaseFlag() == null || warehouseVO.getPurchaseFlag().compareTo(0) == 0) {
                        return ResultVo.failure(warehouseVO.getWarehouseCode() + "该仓不可采购");
                    }
                }
            }
            //warehouseVO 用于生成采购单的采购仓库

            //如果是分库，则找上级物流中心，如果物流中心没有补库和调拨能力，则报错
            //if ("SUB".equalsIgnoreCase(warehouseVOResultVo.getData().getWarehouseType())) {
            //    warehouseVOResultVo = commonServiceFeignApi.getWarehouseInfoByCode(warehouseVOResultVo.getData().getParentCode());
            //    if (!warehouseVOResultVo.isSuccess()) {
            //        return ResultVo.failure(appDO.getWarehouseCode() + warehouseVOResultVo.getMessage());
            //    }
            //}
            //WarehouseVO warehouseVO =  warehouseVOResultVo.getData() ;
            //String warehouseCode = warehouseVO.getWarehouseCode();
            //if (warehouseVO.getTransferFlag().compareTo(0) == 0 && warehouseVO.getPrestockFlag().compareTo(0) == 0) {
            //    return ResultVo.failure(warehouseCode + "此仓库不可补库和调库。");
            //}

            RedisMessageVO redisMessageVO;
            Object obj = redisUtil.get("ops:cpfr:binorder:creorder:" + appId);
            if (obj == null) {
                redisMessageVO = new RedisMessageVO();
                redisMessageVO.setNo(appId.toString());
                redisMessageVO.setContent("开始生成订单");
                redisMessageVO.setStatus(1);
                redisMessageVO.setKey("ops:cpfr:binorder:creorder:" + appId);
                saveCreateOrderState(redisMessageVO);
            } else {
                redisMessageVO = JSON.parseObject(obj.toString(), RedisMessageVO.class);
            }
            LambdaQueryWrapper<BinOrderDetailDO> query = new LambdaQueryWrapper<>();
            query.eq(BinOrderDetailDO::getAppId, appId)
                    .eq(BinOrderDetailDO::getStatus, 3);
            List<BinOrderDetailDO> listOrderDetail = binOrderDetailMapper.selectList(query);

            boolean hasFault = false;
            int count = listOrderDetail.size();
            int index = 0;
            //调拨交货期
            Date transDlvDate = getcalculDate(new Date(), 2);

            // 2每项生成采购单和调拨单
            BinOrderDetailVO binOrderDetailVO;
            ResultVo<String> itemResult;
            for (BinOrderDetailDO item : listOrderDetail) {
                /**
                 * 16125bug  lyc
                 * 点击bin补货-审批界面，“生成订单”按钮，增加保护
                 * 1.当order_type=1（调拨单）时，trans_qty=0时提示“型号XXXX调拨单数量为0，未生成调拨单”，跳过不生成调拨单，其他项正常处理
                 * 2.当order_type=A、B、K（采购单），po_qty=0时提示“型号XXXX采购数量为0，未生成采购单”，跳过不发出采购单，其他项正常处理
                 */
                if (StringUtils.isEmpty(item.getOrderType())) {
                    continue;
                }
                if (item.getOrderType().equals("A") || item.getOrderType().equals("B") || item.getOrderType().equals("K")) {
                    if (item.getPoQty() == null || item.getPoQty() == 0) {
                        redisMessageVO.setContent("型号" + item.getModelNo() + "采购数量为0，未生成采购单");
                        redisMessageVO.setStatus(4);
                        saveCreateOrderState(redisMessageVO);
                        continue;
                    }
                }
                if (item.getOrderType().equals("1")) {
                    if (item.getTransQty() == null || item.getTransQty() == 0) {
                        redisMessageVO.setContent("型号" + item.getModelNo() + "调拨单数量为0，未生成调拨单");
                        redisMessageVO.setStatus(4);
                        saveCreateOrderState(redisMessageVO);
                        continue;
                    }
                }
                String lockKeyItem = "binorderitem:" + item.getId();
                if (redissonUtil.tryLock(lockKeyItem, 1, 300, TimeUnit.SECONDS)) {
                    binOrderDetailVO = BeanCopyUtil.copy(item, BinOrderDetailVO.class);
                    try {
                        itemResult = this.createOrderByDetail(binOrderDetailVO, warehouseVO, transDlvDate);
                        if (!itemResult.isSuccess()) {
                            log.error(itemResult.getMessage());
                            hasFault = true;
                        }
                        redisMessageVO.setContent("生成订单中" + index + "/" + count);
                        redisMessageVO.setStatus(1);
                        saveCreateOrderState(redisMessageVO);
                    } catch (Exception e) {
                        hasFault = true;
                        log.error("生成订单失败: {}", item, e, e);
                        redisMessageVO.setContent(e.getMessage());
                        redisMessageVO.setStatus(4);
                        saveCreateOrderState(redisMessageVO);
                    } finally {
                        redissonUtil.unlock(lockKeyItem);
                    }
                }
            }
            redisMessageVO.setContent("生成订单完成");

            //3 发送生成的采购
            ResultVo<String> poResult = addToPurchaseOrder(appId, appDO.getWarehouseCode());
            if (!poResult.isSuccess()) {
                redisMessageVO.setContent("发布到请购表失败：" + poResult.getMessage());
                return ResultVo.failure(redisMessageVO.getContent());
            }

            //更新申请主表状态
            if (false == hasFault) {
                updateBinOrderAppStatus(appId, 5);
                saveCreateOrderState(redisMessageVO);
                return ResultVo.success(true, "生成订单成功");
            } else {
                redisMessageVO.setContent(redisMessageVO.getContent() + " 有错误");
                redisMessageVO.setStatus(4);
                saveCreateOrderState(redisMessageVO);
                return ResultVo.failure("生成失败");
            }
        } catch (Exception ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + "->错误：" , ex);
            return ResultVo.failure("生成失败");
        } finally {
            redissonUtil.unlock(lockKey);
        }
    }

    private ResultVo<String> addToPurchaseOrder(Long appId, String warehouseCode) {
        LambdaQueryWrapper<BinOrderDetailSplitDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BinOrderDetailSplitDO::getAppId, appId);
        queryWrapper.eq(BinOrderDetailSplitDO::getStatus, 3);
        queryWrapper.ne(BinOrderDetailSplitDO::getOrderType, "1");
        List<BinOrderDetailSplitDO> list = binOrderDetailSplitMapper.selectList(queryWrapper);
        if (list.isEmpty()) {
            return ResultVo.success();
        }

        List<List<BinOrderDetailSplitDO>> listList = ListUtils.partition(list, 10);
        List<RequestPurchaseInfoDto> poOrders;
        List<Long> ids;
        RequestPurchaseInfoDto poOrderInfo;
        BinOrderDetailSplitDO updSplitDO = new BinOrderDetailSplitDO();
        updSplitDO.setStatus(5);
        updSplitDO.setUpdateUser("addPO");

        UpdateWrapper<BinOrderDetailSplitDO> updateWrapper = new UpdateWrapper<>();
        CommonResult<String> poResult;

        for (List<BinOrderDetailSplitDO> subList : listList) {
            poOrders = new ArrayList<>(subList.size());
            ids = new ArrayList<>(subList.size());
            for (BinOrderDetailSplitDO splitItem : subList) {
                poOrderInfo = new RequestPurchaseInfoDto();
                setPOOrderInfo(poOrderInfo, splitItem);
                poOrderInfo.setRequestwarehouseid(warehouseCode);
                poOrderInfo.setOrderno(splitItem.getOrderNo());
                poOrderInfo.setItemno(splitItem.getItemNo());
                poOrderInfo.setWmtag("W");
                poOrders.add(poOrderInfo);
                ids.add(splitItem.getId());
            }
            //1.写入采购表
            poResult = requestPurchaseFeignApi.addRequest(poOrders);
            if (!poResult.isSuccess()) {
                throw new RuntimeException(appId + "生成采购单失败： " + poResult.getMessage());
                //return ResultVo.failure(appId + "生成采购单失败： "+ poResult.getMessage());
            }

            //更新状态
            updSplitDO.setUpdateTime(new Date());
            updateWrapper.clear();
            updateWrapper.in("id", ids);
            binOrderDetailSplitMapper.update(updSplitDO, updateWrapper);

            sendOrderState(poOrders);
        }
        return ResultVo.success("", "已推送采购");
    }

    private void sendOrderState(List<RequestPurchaseInfoDto> poOrders) {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        for (RequestPurchaseInfoDto poOrderInfo : poOrders) {
            try {
                String fullOrderNo = poOrderInfo.getOrderno() + "-" + poOrderInfo.getItemno();
                OrderStateVO orderStateVO = new OrderStateVO();
                orderStateVO.setOrderNo(fullOrderNo);
                orderStateVO.setModelNo(poOrderInfo.getModelno());
                orderStateVO.setQuantity(poOrderInfo.getQuantity());
                orderStateVO.setOrderDate(new Date());
                orderStateVO.setStateDate(orderStateVO.getOrderDate());
                orderStateVO.setReceiveDate(orderStateVO.getOrderDate());
                orderStateVO.setSupplierCode(poOrderInfo.getSupplierid());
                orderStateVO.setOrderType(20);
                orderStateVO.setPurchaseType(poOrderInfo.getPurchasetype());
                orderStateVO.setCustDlvDate(poOrderInfo.getHopedeliverydate());
                orderStateVO.setPoDlvDate(poOrderInfo.getHopedeliverydate());
                orderStateVO.setStateCode(OrderStateEnum.Purchareing.getCode());
                orderStateVO.setStateType(2);
                orderStateVO.setDeptNo(poOrderInfo.getDeptno());
                orderStateVO.setProvider("BIN");
                orderStateVO.setCorderNo(poOrderInfo.getCorderno());
                orderStateVO.setTransType(poOrderInfo.getTranstype());
                orderStateVO.setRorderNo(poOrderInfo.getOrderno());
                orderStateVO.setWarehouseCode(poOrderInfo.getRequestwarehouseid());
                orderStateVO.setTransType(poOrderInfo.getTranstype());
                orderStateVO.setPoTransType(poOrderInfo.getTranstype());
                orderStateVO.setItemNo(poOrderInfo.getItemno());
                orderStateVO.setStateDes("BIN补货生成请购单,纳期:" + DateUtil.dateToDateString(poOrderInfo.getHopedeliverydate()));
                orderStateVO.setWarehouseCode(poOrderInfo.getRequestwarehouseid());
                orderStateServiceFeignApi.addOrderState(orderStateVO);
            } catch (Exception ex) {
                log.error("add order state error");
                log.info(poOrderInfo.toString());
                log.error(ex.getMessage());
            }
        }
    }

    public enum OrderTypeEnum {
        CG("ABKU"), //采购
        DB("1"), //调拨
        SPLIT("9"); //手工拆分

        private String type;

        OrderTypeEnum(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    private ResultVo<String> createOrderByDetail(BinOrderDetailVO detail, WarehouseVO warehouseVO, Date transDlvDate) {
        //采购单号、调库单号
        List<String> listNos = new ArrayList<>();

        //获取调库单号，根据apply_id
        String transOrderNo = this.createBinTransNo(detail.getAppId());
        //获取调库单项号，根据apply_id
        String redisKeyTransItem = "ops:binorder:mxitem:" + detail.getAppId();
        Object objRedisValue = redisUtil.get(redisKeyTransItem);
        int tansItemNo = objRedisValue == null ? 0 : Integer.parseInt(objRedisValue.toString());

        //detail中的采购数量要更新成多少
        Integer poQty = 0;
        Integer transQty = 0;
        Date firstDlvDate = null;
        String firstTransType = null;
        //拆分类型，9为手工拆分，1为调拨，ABKU为采购
        boolean isSplitItem = OrderTypeEnum.SPLIT.getType().equals(detail.getOrderType());
        //有手工拆分，查询split表
        List<BinOrderDetailSplitDO> splitlist = getBinOrderDetailSplitDOS(detail);
        if (CollectionUtils.isEmpty(splitlist)) {
            return ResultVo.failure("拆分数量明细不存在");
        }
        for (BinOrderDetailSplitDO splitDO : splitlist) {
            // 如果是调拨
            if (OrderTypeEnum.DB.getType().equals(splitDO.getOrderType())) {
                BinOrderDetailVO detailVOSplit = BeanCopyUtil.copy(detail, BinOrderDetailVO.class);
                detailVOSplit.setId(splitDO.getId());
                detailVOSplit.setFromId(detail.getId());
                detailVOSplit.setConfirmQty(splitDO.getConfirmQty());
                detailVOSplit.setOrderType("1");
                detailVOSplit.setSupplierCode(splitDO.getSupplierCode());
                detailVOSplit.setOrderNo(transOrderNo);
                //四个字段，优先使用splitDO的数据，如果为空再使用detail
                if(StringUtils.isNotBlank(splitDO.getOrderType())){
                    detailVOSplit.setOrderType(splitDO.getOrderType());
                }
                if(StringUtils.isNotBlank(splitDO.getSupplierCode())){
                    detailVOSplit.setSupplierCode(splitDO.getSupplierCode());
                }
                if(StringUtils.isNotBlank(splitDO.getTransType())){
                    detailVOSplit.setTransType(splitDO.getTransType());
                }
                if(splitDO.getDlvDate()!=null){
                    detailVOSplit.setDlvDate(splitDO.getDlvDate());
                }
                tansItemNo = tansItemNo + 1;
                ResultVo<String> resultItem = createTransOrder(detailVOSplit, tansItemNo);
                if (resultItem.isSuccess()) {
                    transQty += detailVOSplit.getConfirmQty();
                    listNos.add(resultItem.getData());
                }
            }
            //如果是采购
            else if (!OrderTypeEnum.DB.getType().equals(splitDO.getOrderType())) {
                splitDO.setAppId(detail.getAppId());
                //缺省供应商则用detail的
                if (StringUtils.isEmpty(splitDO.getSupplierCode())) {
                    splitDO.setSupplierCode(detail.getSupplierCode());
                    if (StringUtils.isEmpty(splitDO.getSupplierCode())) {
                        splitDO.setSupplierCode(detail.getMainSupplierCode());
                    }
                }
                splitDO.setWarehouseCode(detail.getWarehouseCode());
                //请购单的采购仓
                detail.setPoLogisWarehouseCode(warehouseVO.getWarehouseCode());
                //此方法会拆分split
                ResultVo<List<String>> poResult = createPoOrderBySplit(splitDO, detail);
                if (poResult.isSuccess()) {
                    poQty += splitDO.getConfirmQty();
                    listNos.addAll(0, poResult.getData());
                    //splitDO中的交货期是当前split的第一个交货期
                    if (firstDlvDate == null) {
                        firstDlvDate = splitDO.getDlvDate();
                    }
                    if (StringUtils.isBlank(firstTransType)) {
                        firstTransType = splitDO.getTransType();
                    }
                }
            }
        }
        //记录订单号，分号隔开
        String strOrderNo = StringUtils.join(listNos, ";");
        if (strOrderNo.length() >= 200) {
            strOrderNo = strOrderNo.substring(0, 200);
        }
        //生成订单失败
        if ((poQty + transQty) <= 0) {
            BinOrderDetailDO detailDO = new BinOrderDetailDO();
            detailDO.setId(detail.getId());
            detailDO.setOrderNo(strOrderNo);
            detailDO.setStatus(4);
            detailDO.setUpdateTime(new Date());
            binOrderDetailMapper.updateById(detailDO);
            return ResultVo.failure("生成订单失败");
        }
        //更新detail的 状态订单号，调拨数量采购数量，交货期 运输方式，更新时间
        BinOrderDetailDO detailDO = new BinOrderDetailDO();
        detailDO.setId(detail.getId());
        detailDO.setOrderNo(strOrderNo);
        detailDO.setStatus(5);
        detailDO.setUpdateTime(new Date());
        detailDO.setDlvDate(firstDlvDate);
        detailDO.setTransType(firstTransType);
        LambdaUpdateWrapper<BinOrderDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BinOrderDetailDO::getId, detail.getId());
        updateWrapper.eq(BinOrderDetailDO::getStatus, 3);
        if (1 != binOrderDetailMapper.update(detailDO, updateWrapper)) {
            log.error("createOrderByItem 更新项状态失败 ");
            throw new BusinessException("更新项状态失败");
        }
        //更新redis的调拨项号
        redisUtil.set(redisKeyTransItem, tansItemNo, 86400);
        return ResultVo.success("", "生成采购单数量" + poQty + ",调拨单" + transQty);
    }
    /**
     * 1.获取初始采购单号和项号，订单类型
     * 2.获取初始交货期
     * 3.采购仓库
     * 4.生成运输方式
     * 5.设置订单状态
     * 拆分项
     * 1.设置采购单项号
     * 2.设置数量
     * 3.设置交货期
     */
    /**
     * 根据拆分明细创建采购订单
     * 当采购数量超过单箱数量时，会自动拆分成多个订单项，每个项号递增，交货期依次延后28天
     *
     * @param splitDO  拆分明细对象，包含请购数量、仓库、供应商等信息
     * @param detailVO 订单明细对象，用于获取箱数、calcType、上次交货期等补充信息
     * @return ResultVo<List<String>> 返回生成的采购订单号列表，格式为 "订单号-项号"，例如 ["9900005143-1", "9900005143-2"]
     */
    public ResultVo<List<String>> createPoOrderBySplit(BinOrderDetailSplitDO splitDO, BinOrderDetailVO detailVO) {
        List<String> poOrderNos = new ArrayList<>();
        
        // ==================== 第一步：获取初始采购单号和项号,订单类型 ====================
        // 从Redis中获取当前应用的采购订单号缓存，格式为 "订单号-项号"
        String poOrderNo;
        int poItemNo;
        String redisKeyPOItem = "ops:cpfr:binorderno:" + detailVO.getAppId();
        Object objRedisValue = redisUtil.get(redisKeyPOItem);
        
        if (PublicUtil.isEmpty(objRedisValue)) {
            // Redis中无缓存，说明是首次生成，需要调用单据号生成服务获取新单号
            // calcType="4" 使用单据类型"3"，否则使用单据类型"2"
            if ("4".equals(detailVO.getCalcType())) {
                poOrderNo = commonServiceFeignApi.generatorBillNo("3").getData();
            } else {
                poOrderNo = commonServiceFeignApi.generatorBillNo("2").getData();
            }
            if (PublicUtil.isEmpty(poOrderNo)) {
                return ResultVo.failure("生成采购订单失败，请检查数据字典服务");
            }
            poItemNo = 0; // 初始项号为0，后续会从1开始递增
        } else {
            // Redis中有缓存，解析上次的订单号和项号
            String orderItemNo = objRedisValue.toString();
            String[] arrs = orderItemNo.split("-", 2);
            poOrderNo = arrs[0];      // 订单号，例如 "9900005143"
            poItemNo = Integer.parseInt(arrs[1]); // 上次使用的项号
        }
        splitDO.setOrderNo(poOrderNo);
        splitDO.setItemNo(poItemNo);
        // 订单类型默认为"K"
        if (StringUtils.isBlank(splitDO.getOrderType())) {
            splitDO.setOrderType("K");
        }
        //供应商，默认为detail中的供应商
        if(StringUtils.isBlank(splitDO.getSupplierCode())){
            splitDO.setSupplierCode(detailVO.getSupplierCode());
        }

        // ==================== 第二步：获取初始交货期 ====================
        // 交货期优先级：splitDO预设交货期 > 本条detail预设交货期 > 上次交货期+4周 > 从历史采购记录计算
        Date dlvDate = splitDO.getDlvDate();
        if (dlvDate == null) {
            dlvDate = detailVO.getDlvDate();
            if (dlvDate == null) {
                // 没有预设交货期，尝试使用detail中的上次交货期
                dlvDate = detailVO.getLastDlvdate();
                if (dlvDate == null) {
                    // 从历史采购记录中获取该物料在该仓库的最后一次交货期
                    dlvDate = getFromLastPurchaseDlvdate(detailVO.getModelNo(),
                            splitDO.getWarehouseCode(), splitDO.getSupplierCode());
                } else {
                    // 基于上次交货期计算下次交货期（+28天并转换为工作日）
                    dlvDate = addNextMonthFromLastDate(dlvDate, splitDO.getSupplierCode());
                }
            }
        }
        splitDO.setDlvDate(dlvDate);

        // ==================== 第三步：设置采购仓库 ====================
        //bug 19313-27楼
        //设置请购表的采购仓库，不区分分库和物流中心，detail中的PoLogisWarehouseCode为计算后的值
        splitDO.setWarehouseCode(detailVO.getPoLogisWarehouseCode());

        // ==================== 第四步：获取/设置运输方式 ====================
        String transType = splitDO.getTransType();
        if (StringUtils.isBlank(transType)) {
            if (StringUtils.isNotBlank(detailVO.getTransType())) {
                transType = detailVO.getTransType();
            }
            //使用默认规则
            else {
                ////使用运输方式接口
                //TransTypeParam transTypeParam = new TransTypeParam();
                //transTypeParam.setModelNo(splitDO.getModelNo());
                //transTypeParam.setOrdType(splitDO.getOrderType());
                //transTypeParam.setWarehouse(splitDO.getWarehouseCode());
                //transTypeParam.setSupplierId(splitDO.getSupplierCode());
                //transTypeParam.setOrderQty(splitDO.getConfirmQty());
                //CommonResult<List<OpsPoTranstype>> transIdResultVO = purchaseTransFeignApi.getTransIds(transTypeParam);
                //if (transIdResultVO.isSuccess()) {
                //    List<OpsPoTranstype> transTypeList = transIdResultVO.getData();
                //    if (CollectionUtils.isNotEmpty(transTypeList)) {
                //        if (transTypeList.size() == 1) {
                //            transType = transTypeList.get(0).getId();
                //        }
                //    }
                //}
                //如果还是为空，则取默认值
                if (StringUtils.isBlank(transType)) {
                    // 国内供应商默认使用陆运（代码"3"）
                    if (PublicUtil.isInCountry(splitDO.getSupplierCode())) {
                        transType = "3";
                    }
                    // 非国内供应商默认使用海运（代码"0"）
                    if (StringUtils.isBlank(transType)) {
                        transType = "0";
                    }
                }
            }
        }
        splitDO.setTransType(transType);
        

        // ==================== 第五步：设置订单状态 ====================
        splitDO.setStatus(3); // 状态3表示"待发单给请购"

        
        // ==================== 第六步：循环拆分采购订单 ====================
        // 当请购数量超过单箱数量时，需要拆分成多个订单项
        // 第一条记录使用update操作（保留原ID），后续记录使用insert操作（ID设为0）
        boolean isFirst = true;
        Integer leftQty = splitDO.getConfirmQty(); // 剩余待拆分的请购数量
        String poFno = poOrderNo + "-" + poItemNo; // 用于记录最后一个完整订单号
        
        while (leftQty > 0) {
            // 创建新的拆分对象，复制基础信息
            BinOrderDetailSplitDO split = new BinOrderDetailSplitDO();
            BeanUtil.copyProperties(splitDO, split);
            
            // 计算本条拆分记录的数量：如果剩余数量>=单箱数量，则按单箱数量拆分；否则使用剩余数量
            if (detailVO.getQtybin() != null && leftQty >= detailVO.getQtybin() && detailVO.getQtybin() > 0) {
                split.setConfirmQty(detailVO.getQtybin());
            } else {
                split.setConfirmQty(leftQty);
            }
            
            // 更新剩余待拆分数量
            leftQty = leftQty - split.getConfirmQty();
            
            // 订单项号递增（从1开始）
            poItemNo = poItemNo + 1;
            split.setItemNo(poItemNo);
            
            // 设置本条拆分的交货期
            split.setDlvDate(dlvDate);
            // 保存当前交货期到detail中，供下次拆分时使用
            detailVO.setLastDlvdate(dlvDate);
            // 下一条拆分的交货期延后28天
            dlvDate = DateUtil.addDay(dlvDate, 28);
            
            // 第一条记录保留原ID用于update，后续记录ID设为0用于insert
            if (isFirst) {
                isFirst = false;
            } else {
                split.setId(0L);
            }
            
            // 根据ID判断是新增还是更新
            if (split.getId() == null || split.getId() == 0) {
                split.setCreateUser("BINORD");
                binOrderDetailSplitMapper.insert(split);
            } else {
                binOrderDetailSplitMapper.updateById(split);
            }
            
            // 记录完整订单号（订单号-项号）
            poFno = poOrderNo + "-" + poItemNo;
            poOrderNos.add(poFno);
        }
        
        // 将最后一个完整订单号保存到Redis，有效期24小时（86400秒）
        // 用于下次生成采购订单时续编号
        redisUtil.set(redisKeyPOItem, poFno, 86400);
        return ResultVo.success(poOrderNos);
    }



    private List<BinOrderDetailSplitDO> getBinOrderDetailSplitDOS(BinOrderDetailVO detail) {
        LambdaQueryWrapper<BinOrderDetailSplitDO> query = new LambdaQueryWrapper<>();
        query.eq(BinOrderDetailSplitDO::getFromId, detail.getId())
                .ne(BinOrderDetailSplitDO::getDelFlag, 1)
                .gt(BinOrderDetailSplitDO::getConfirmQty, 0);
        List<BinOrderDetailSplitDO> splitlist = binOrderDetailSplitMapper.selectList(query);
        return splitlist;
    }

    @Deprecated
    @Transactional
    public ResultVo<List<String>> createPOOrderByItem(BinOrderDetailVO poItem) {
        List<String> poOrderNos = new ArrayList<>();
        //String redisKeyPOItem = "ops:cpfr:binorderno:" + poItem.getAppId();
        //
        //Object objRedisValue = redisUtil.get(redisKeyPOItem);
        //int poItemNo;
        //String orderNo;
        //if (PublicUtil.isEmpty(objRedisValue)) {
        //    if ("4".equals(poItem.getCalcType())) {
        //        orderNo = commonServiceFeignApi.generatorBillNo("3").getData();
        //    } else {
        //        orderNo = commonServiceFeignApi.generatorBillNo("2").getData();
        //    }
        //    if (PublicUtil.isEmpty(orderNo)) {
        //        return ResultVo.failure("生成采购订单失败，请检查数据字典服务");
        //    }
        //    poItem.setOrderNo(orderNo);
        //    poItemNo = 0;
        //} else {
        //    String orderitemNo = objRedisValue.toString();
        //    String[] arrs = orderitemNo.split("-", 2);
        //    poItem.setOrderNo(arrs[0]);
        //    poItemNo = Integer.parseInt(arrs[1]);
        //}
        //// int poItemNo = objRedisValue == null ? 0 : Integer.parseInt(objRedisValue.toString());
        //
        ////获取上次交货期
        //Date lastDlvDate = poItem.getDlvDate();
        //if (lastDlvDate == null) {
        //    lastDlvDate = poItem.getLastDlvdate();
        //    if (lastDlvDate == null) {
        //        lastDlvDate = getFromLastPurchaseDlvdate(poItem.getModelNo(),
        //                poItem.getWarehouseCode(), poItem.getSupplierCode());
        //    } else {
        //        lastDlvDate = addNextMonthFromLastDate(lastDlvDate, poItem.getSupplierCode());
        //    }
        //}
        //
        //Integer poQty = 0;
        //
        //BinOrderDetailSplitDO splitItemDO = BeanCopyUtil.copy(poItem, BinOrderDetailSplitDO.class);
        //splitItemDO.setStatus(3);//待发单给请购
        //if (StringUtils.isBlank(splitItemDO.getTransType())) {
        //    if (PublicUtil.isInCountry(splitItemDO.getSupplierCode())) {  //国内供应商运输方式改成陆运
        //        splitItemDO.setTransType("3");
        //    }
        //    if (StringUtils.isBlank(splitItemDO.getTransType())) {
        //        splitItemDO.setTransType("0");
        //    }
        //}
        //
        ////分仓不能直接的使用物流中心采购
        //ResultVo<String> resultVo =  commonServiceFeignApi.getWarehouseType(poItem.getWarehouseCode());
        //if(!resultVo.isSuccess()){
        //    return ResultVo.failure(resultVo.getMessage());
        //}
        //String warehouseType =resultVo.getMessage()  ; //commonService.getWarehouseType(poItem.getWarehouseCode());
        //if (poItem.getDirectpurchase() != null && poItem.getDirectpurchase() != 1
        //        && "SUB".equalsIgnoreCase(warehouseType)) {
        //    splitItemDO.setWarehouseCode(poItem.getPoLogisWarehouseCode());
        //}
        //if (poItem.getQtybin() == null) {
        //    poItem.setQtybin(0);
        //}
        //
        //
        //Integer leftQty = poItem.getPoQty();
        //boolean firstItem = true;
        ////生成多条采购单时，按采购单项号排序，detail交货期字段记录第一条采购单的交货期和运输方式，
        //Date firstDlvDate = null;
        //while (leftQty > 0) {
        //    if (leftQty >= poItem.getQtybin() && poItem.getQtybin() > 0) {
        //        splitItemDO.setConfirmQty(poItem.getQtybin());
        //    } else {
        //        splitItemDO.setConfirmQty(leftQty);
        //    }
        //    leftQty = leftQty - splitItemDO.getConfirmQty();
        //    splitItemDO.setDlvDate(lastDlvDate);
        //    if(firstDlvDate==null){
        //        firstDlvDate = splitItemDO.getDlvDate();
        //    }
        //    poItemNo = poItemNo + 1;
        //
        //    if (firstItem == false) {
        //        splitItemDO.setId(0L);
        //    } else {
        //        firstItem = false;
        //    }
        //    splitItemDO.setItemNo(poItemNo);
        //    if (PublicUtil.isEmpty(splitItemDO.getOrderType()) || PublicUtil.isEmpty(splitItemDO.getOrderType().trim())) {
        //        splitItemDO.setOrderType("K");
        //    }
        //    if (splitItemDO.getId() == null || splitItemDO.getId() == 0) {
        //        splitItemDO.setCreateUser("BINORD");
        //        binOrderDetailSplitMapper.insert(splitItemDO);
        //    } else {
        //        binOrderDetailSplitMapper.updateById(splitItemDO);
        //    }
        //
        //    poQty += poItem.getPoQty();
        //    poOrderNos.add(splitItemDO.getOrderNo() + "-" + splitItemDO.getItemNo());
        //    //生成采购，供应商为VN（越南）时交货期不再按照每个月10日，改为和其他供应商一样，按照当天+28天，每多生成一个，增加28天
        //    //if ("VN".equalsIgnoreCase(poItem.getSupplierCode())) {
        //    //    lastDlvDate = DateUtil.addMonth(lastDlvDate, 1);
        //    //} else {
        //    lastDlvDate = DateUtil.addDay(lastDlvDate, 28);
        //    //}
        //}
        ////生成多条采购单时，按采购单项号排序，detail交货期字段记录第一条采购单的交货期和运输方式，
        //poItem.setDlvDate(firstDlvDate);
        //poItem.setTransType(splitItemDO.getTransType());
        //redisUtil.set(redisKeyPOItem, poItem.getOrderNo() + "-" + poItemNo, 86400);
        return ResultVo.success(poOrderNos);
    }

    private Date convertWorkDate(Date date) {
        int weekDay = DateUtil.getWeekDay(date);
        if (weekDay == 6) {
            return DateUtil.addDay(date, 2);
        }
        if (weekDay == 7) {
            return DateUtil.addDay(date, 1);
        }
        return date;
    }

    public Date getFromLastPurchaseDlvdate(String modelNo, String wareHouseCode, String supplierCode) {
        Date lastDlvDate = binOrderCalcService.getLastPurchaseDlvDate(modelNo, wareHouseCode);

        lastDlvDate = addNextMonthFromLastDate(lastDlvDate, supplierCode);
        return lastDlvDate;
    }

    public Date addNextMonthFromLastDate(Date lastDlvDate, String supplierCode) {
        if (lastDlvDate == null || lastDlvDate.before(DateUtil.getToday())) {
            lastDlvDate = DateUtil.getToday();
        }
        lastDlvDate = DateUtil.addDay(lastDlvDate, 28);
        lastDlvDate = convertWorkDate(lastDlvDate);
        return lastDlvDate;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> createTransOrder(BinOrderDetailVO itemVO, Integer itemNo) {
        TransOrderVO transDto = new TransOrderVO();
        this.setInventoryForTrans(transDto, itemVO);
        transDto.setFromNo(itemVO.getOrderNo());
        transDto.setTransNo(itemVO.getOrderNo());
        transDto.setItemNo(itemNo);
        transDto.setFromId(itemVO.getId());
        transDto.setWmsDlvDate(itemVO.getDlvDate());
        ResultVo<String> transResult = transStockService.transStock(Collections.singletonList(transDto));
        boolean transRes;
        if (transResult.isSuccess()) {
            Map resultMap = JSONUtil.toBean(transResult.getData(), Map.class);
            transRes = "成功".equals(resultMap.get(transDto.getTransNo() + "-" + transDto.getItemNo()));
        } else {
            transRes = false;
        }
        if (!transRes) {
            log.error("调库失败" + transResult.getMessage());
            if (itemVO.getId() != null && itemVO.getId() > 0) {
                BinOrderDetailSplitDO splitDO = new BinOrderDetailSplitDO();
                splitDO.setId(itemVO.getId());
                splitDO.setUpdateTime(new Date());
                splitDO.setStatus(4);//申请失败
                binOrderDetailSplitMapper.updateById(splitDO);
            }
            return ResultVo.failure(transResult.getMessage());
        }
        BinOrderDetailSplitDO splitDO = BeanCopyUtil.copy(itemVO, BinOrderDetailSplitDO.class);
        splitDO.setStatus(5);
        splitDO.setUpdateTime(new Date());
        splitDO.setUpdateUser("BINORD");
        splitDO.setItemNo(itemNo);
        splitDO.setOrderNo(itemVO.getOrderNo());
        splitDO.setDlvDate(transDto.getWmsDlvDate());
        String fullOrderNo = splitDO.getOrderNo() + "-" + itemNo;
        if (splitDO.getId() == null || splitDO.getId() == 0) {
            splitDO.setCreateTime(new Date());
            splitDO.setCreateUser("BINORD");
            binOrderDetailSplitMapper.insert(splitDO);
        } else {
            binOrderDetailSplitMapper.updateById(splitDO);
        }
        return ResultVo.success(fullOrderNo, "生成调拨单" + fullOrderNo);
    }

    /**
     * 求增加工作日后的日期
     *
     * @param optDate days
     */
    private Date getcalculDate(Date optDate, Integer days) {
        while (days > 0) {
            optDate = DateUtil.addDay(optDate, 1);
            Integer workType = binOrderCalcMapper.GetWorkDateType(DateUtil.getFormatDate(optDate, "yyyy-MM-dd"));
            if (workType == 0) {
                days--;
            }
        }
        return optDate;
    }

    /**
     * 设置采购数据OpsRequestpurchase
     */
    private void setPOOrderInfo(RequestPurchaseInfoDto info, BinOrderDetailSplitDO item) {
        info.setModelno(item.getModelNo());
        info.setQuantity(item.getConfirmQty());
        info.setStatecode("0");
        info.setSplititemno(null);
        info.setHopedeliverydate(item.getDlvDate());
        info.setHopeexportdate(item.getDlvDate());
        info.setDeptno("200000");
        info.setApplyDeptNo("239810");
        info.setCustomerno("C1D7V");
        info.setEndUser("C1D7V");
        info.setUserno("");
        info.setSupplierid(item.getSupplierCode());
        //兜底使用供应商表
        if (PublicUtil.isEmpty(info.getSupplierid())) {
            info.setSupplierid(binOrderAppMapper.getSupplierid(info.getModelno()));
        }
        //供应商，越南和新加坡的供应商改为日本
        if ("VN".equalsIgnoreCase(info.getSupplierid()) || "SG".equalsIgnoreCase(info.getSupplierid())) {
            info.setSupplierid("JP");
        }

        //运输方式，国内供应商强制使用陆运
        info.setTranstype(item.getTransType());
        if (PublicUtil.isInCountry(info.getSupplierid())) {  //国内供应商运输方式改成陆运
            info.setTranstype("3");
        }
        //运输方式，兜底使用海运
        if (PublicUtil.isEmpty(info.getTranstype())) {
            info.setTranstype("0");
        }
        if (item.getOrderNo().startsWith("88")) {
            info.setOrdtype("21");
            info.setPurchasetype(StringUtils.isNotBlank(item.getOrderType()) ? item.getOrderType() : "K");
            BinOrderDetailDO detailDO = binOrderDetailMapper.selectById(item.getFromId());
            info.setInventorypropertyid(detailDO.getPropertyId());
            info.setInventorytypecode(detailDO.getInventoryTypeCode());
        } else {
            info.setOrdtype("20");
            info.setPurchasetype(StringUtils.isNotBlank(item.getOrderType()) ? item.getOrderType() : "K");
            info.setInventorypropertyid(1L);
            //info.setPpl(null);
            //info.setProjectcode(item.getProjectNo());
            info.setInventorytypecode("TY");
        }
        //info.setGroupcustomerno(item.getGroupCustomerNo());
        info.setRequesttime(new Date());
        info.setOrderdate(new Date());
        info.setCorderno("BIN" + item.getAppId().toString());

        info.setPurchasewarehouseid(item.getWarehouseCode());
    }

    /**
     * 设置调拨数据
     */
    private void setInventoryForTrans(TransOrderVO transDto, BinOrderDetailVO item) {
        transDto.setTransType(1);
        transDto.setStatus(0);
        transDto.setFromType(1);
        transDto.setModelNo(item.getModelNo());
        transDto.setQuantity(item.getConfirmQty());
        // 调出
        transDto.setFromWarehouseCode(item.getSupplierCode());
        transDto.setFromInventoryTypeCode("TY");
        transDto.setFromInventoryPropertyId(1L);
        // 调入
        transDto.setToWarehouseCode(item.getWarehouseCode());
        transDto.setToInventoryTypeCode("TY");
        transDto.setToInventoryPropertyId(1L);
    }

    public void saveCreateOrderState(RedisMessageVO vo) {
        String json = JSON.toJSONString(vo);
        redisUtil.set(vo.getKey(), json);
        redisUtil.expire(vo.getKey(), 60 * 20);
    }

    private boolean updateBinOrderAppStatus(Long appid, Integer status) {
        BinOrderAppDO updDO = new BinOrderAppDO();
        updDO.setStatus(status);
        updDO.setAppId(appid);
        return binOrderAppMapper.updateById(updDO) > 0;  //更新状态
    }

    /**
     * 创建BIN调拨单号
     *
     * @param applyId 申请id
     * @return transNo
     */
    private String createBinTransNo(int applyId) {
        // BN + 8位编号
        String binTransNo = "BN00000000";
        String no = String.valueOf(applyId);
        return binTransNo.substring(0, binTransNo.length() - no.length()) + no;
    }
}
