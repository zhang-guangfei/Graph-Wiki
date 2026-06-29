package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.mapper.BinOrderCalcMapper;
import com.smc.smccloud.mapper.binorder.BinorderDetailRepository;
import com.smc.smccloud.model.Constants;
import com.smc.smccloud.model.bindata.BindataDO;
import com.smc.smccloud.model.binorder.BinOrderCalcDO;
import com.smc.smccloud.model.binorder.BinOrderCalcRequestDTO;
import com.smc.smccloud.model.binorder.BinOrderDetailDO;
import com.smc.smccloud.model.binorder.RedisMessageVO;
import com.smc.smccloud.service.impl.bincalc.calculator.ReplenishmentCalcManager;
import com.smc.smccloud.service.impl.bincalc.creator.CustomerBinProcessor;
import com.smc.smccloud.service.impl.bincalc.creator.WarehouseBinProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class NewBinOrderCalcServiceImpl {

    @Resource
    private RedissonUtil redissonUtil;
    @Resource
    private RedisManager redisUtil;
    @Resource
    private BinOrderCalcMapper binOrderCalcMapper;
    @Resource
    private CustomerBinProcessor customerBinProcessor;
    @Resource
    private WarehouseBinProcessor warehouseBinProcessor;
    @Resource
    private ReplenishmentCalcManager replenishmentCalcManager;
    @Resource
    private BinorderDetailRepository binorderDetailRepository;

    enum CalcType {
        WAREHOUSE_BIN(1, "仓库BIN计算"),
        CUSTOMER_BIN(4, "客户BIN计算"),
        ;


        private Integer code;
        private String name;

        CalcType(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    enum CalcStatus {
        CALCULATING(1, "待计算"),
        APPROVING(2, "审批中"),
        APPROVED(3, "审批已通过"),
        APPROVAL_NOT_GRANTED(4, "审批未通过"),
        CREATING_ORDER(5, "订单生成中"),
        CREATED_ORDER(6, "订单已生成"),
        ;


        private Integer code;
        private String name;

        CalcStatus(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static CalcStatus getByCode(Integer code) {
            for (CalcStatus value : values()) {
                if (value.getCode().equals(code)) {
                    return value;
                }
            }
            return null;
        }
    }


    public ResultVo<String> calcBinOrderSync(BinOrderCalcRequestDTO dto) {
        log.info("开始bin计算，计算号：" + dto.getCalcId()+"，仓库号："+dto.getWarehouseCode());
        try {
            long startTimer = System.currentTimeMillis();
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            //## 计算方法 ##
            processBinOrderDetail(dto);
            log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " 处理结束，耗时(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);
        } catch (Exception e) {
            log.error("BIN补库计算: ", e);
            return ResultVo.failure(e.getMessage());
        } finally {
            ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        }
        return ResultVo.success("计算完成");
    }

    @Async
    public void calcBinOrder(BinOrderCalcRequestDTO dto) {
        //分布式锁：上锁
        String lockKey = Constants.REDIS_KEY_BIN_CALC_LOCK + dto.getCalcId();
        if (!redissonUtil.tryLock(lockKey, 1, 60 * 20, TimeUnit.SECONDS)) {
            log.info("bin补库计算中 asycalcBinOrder " + lockKey);
            return;
        }
        //redis状态锁，记录计算状态，防止重复计算
        RedisMessageVO messageVO = new RedisMessageVO();
        messageVO.setNo(dto.getCalcId().toString());
        log.info("开始bin计算，计算号：" + dto.getCalcId()+"，仓库号："+dto.getWarehouseCode());
        try {
            long startTimer = System.currentTimeMillis();
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            //## 计算方法 ##
            processBinOrderDetail(dto);
            messageVO.setContent(messageVO.getNo() + "已计算完成");
            messageVO.setStatus(2);
            saveCalcState(messageVO);
            log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " 处理结束，耗时(s): " + (System.currentTimeMillis() - startTimer) / 1000.0);
        } catch (Exception e) {
            messageVO.setStatus(4);
            messageVO.setContent(messageVO.getNo() + "计算失败;" + e.getMessage());
            log.error("BIN补库计算: {}", messageVO.getContent(), e);
            saveCalcState(messageVO);
        } finally {
            //分布式锁：解锁
            redisUtil.delete(Constants.REDIS_KEY_BIN_CALC_ING);
            redissonUtil.unlock(lockKey);
            ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        }

    }

    private void processBinOrderDetail(BinOrderCalcRequestDTO dto) {
        // 1.查询detail中是否有已计算过的，detail.status>1
        // 如果有非待计算的，则直接跳过
        Integer count = binorderDetailRepository.findBinOrderDetailNotCalcuing(dto.getCalcId());
        if (count == null || count != 0) {
            log.error("该计算号状态不为待计算，计算不能开始：{}", dto.getCalcId());
            return;
        }
        // 2.开始当前计算号的计算
        // 更新计算表的计算时间为当前时间，计算结束时间为空
        this.updateCalcBeginTime(dto.getCalcId());
        long startTimer = System.currentTimeMillis();
        // 3.1.如果是客户bin,根据型号查询所有bindata，然后组装
        if (CalcType.CUSTOMER_BIN.getCode().equals(dto.getCalcType())) {
            //更新频率和月用量
            customerBinProcessor.processCustomerBin(dto.getCalcId(), dto.getCalcType(), dto.getWarehouseCode());
            //todo 计算逻辑 未设计
        }
        // 3.2.如果是仓库bin,则不应该有detail，如果有就跳过查询，直接开始计算
        else if (CalcType.WAREHOUSE_BIN.getCode().equals(dto.getCalcType())) {
            //4. bindetail基础数据生成
            log.info("1.bindetail基础数据开始生成");
            warehouseBinProcessor.processWarehouseBin(dto.getCalcId(), dto.getCalcType(), dto.getWarehouseCode());
            long Timer1 = System.currentTimeMillis();
            log.info("bindetail数据生成完成，用时：{}s", (Timer1 - startTimer) / 1000.0);
            // 5. 计算补货数量
            log.info("2.开始补库计算程序");
            replenishmentCalcManager.calculateWarehouseInventory(dto.getCalcId(), dto.getCalcType());
            long Timer2 = System.currentTimeMillis();
            log.info("bindetail补库数量计算完成，总共用时：{}s", (Timer2 - startTimer) / 1000.0);
        }
        // 6. 更新计算结束日期
        this.updateCalcFinishTime(dto.getCalcId());
    }


    public void saveCalcState(RedisMessageVO messageVO) {
        messageVO.setOptDate(new Date());
        messageVO.setContent(messageVO.getContent() + " " + DateUtil.getFormatDate(new Date(), "hh:mm"));
        String json = JSON.toJSONString(messageVO);

        redisUtil.set(Constants.REDIS_KEY_BIN_CALC_ING, json);
        redisUtil.expire(Constants.REDIS_KEY_BIN_CALC_ING, 1800);
        if (StringUtils.isNotBlank(messageVO.getNo())) {
            String keyCalcById = Constants.REDIS_KEY_BIN_CALC_STATUS + messageVO.getNo();
            redisUtil.set(keyCalcById, json);
            redisUtil.expire(keyCalcById, 1800);
        }
    }

    private int updateCalcFinishTime(long calcId) {
        BinOrderCalcDO calcDO = new BinOrderCalcDO();
        calcDO.setId(calcId);
        calcDO.setUpdateTime(DateUtil.getNow());
        calcDO.setUpdateUser(SMCApp.getLoginAuthDtoForSysUser().getUserNo());
        calcDO.setCalcFinishTime(DateUtil.getNow());
        return binOrderCalcMapper.updateById(calcDO);
    }

    public int updateCalcBeginTime(Long calcId) {
        LambdaUpdateWrapper<BinOrderCalcDO> wq = new LambdaUpdateWrapper<>();
        wq.eq(BinOrderCalcDO::getId, calcId)
                .set(BinOrderCalcDO::getCalcTime, cn.hutool.core.date.DateUtil.date())
                .set(BinOrderCalcDO::getCalcFinishTime, null);
        return binOrderCalcMapper.update(null, wq);
    }


    //格式转换bindata->binorderDetail
    public static List<BinOrderDetailDO> convertToBinOrderDetailList(Long calcId, List<BindataDO> bindataList) {
        List<BinOrderDetailDO> list = new ArrayList<>();
        for (BindataDO bindataDO : bindataList) {
            BinOrderDetailDO detail = new BinOrderDetailDO();
            list.add(detail);
            detail.setCalcId(calcId);
            detail.setWarehouseCode(bindataDO.getWarehouseCode());
            detail.setCalcType(bindataDO.getStockType().toString());
            detail.setModelNo(bindataDO.getModelNo());

            detail.setDirectpurchase(bindataDO.getDirectPurchase());
            detail.setCenterFlag(bindataDO.getCentreFlag());

            detail.setBincell(bindataDO.getBinCell());
            detail.setQtybin(bindataDO.getQtyBin());
            detail.setPoType(bindataDO.getPoType());
            //库存属性
            detail.setPropertyId(bindataDO.getPropertyID());
            detail.setInventoryTypeCode(bindataDO.getInventoryTypeCode());
            detail.setCustomerNo(bindataDO.getCustomerNo());
            detail.setGroupCustomerNo(bindataDO.getGroupCustomerNo());
            detail.setPpl(bindataDO.getPpl());
            detail.setProjectNo(bindataDO.getProjectNo());

            //下面是否可以分开写，存疑，需要考虑预设detail和更新顺序，和字段是否真的有必要用到
            if (detail.getCalcType().equals(NewBinOrderCalcServiceImpl.CalcType.CUSTOMER_BIN.getCode().toString())) {
                detail.setPreQty(0);
            }
            if (detail.getCalcType().equals(NewBinOrderCalcServiceImpl.CalcType.WAREHOUSE_BIN.getCode().toString())) {
                detail.setOrderType(bindataDO.getOrderType());
                detail.setMainSupplierCode(bindataDO.getSupplierCode());
                detail.setSetOrderType(bindataDO.getOrderType());
                detail.setSupplierCode(bindataDO.getSupplierCode());
                if (bindataDO.getAdjustable() != null && bindataDO.getAdjustable().equals("1")) {
                    detail.setRecommMonths(-1);
                } else {
                    detail.setRecommMonths(0);
                }
            }
        }
        return list;
    }


}
