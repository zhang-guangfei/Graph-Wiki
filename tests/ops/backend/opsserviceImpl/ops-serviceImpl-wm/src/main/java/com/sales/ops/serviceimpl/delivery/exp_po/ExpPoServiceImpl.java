package com.sales.ops.serviceimpl.delivery.exp_po;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;

import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OpsDeliveryPlanDao;
import com.sales.ops.db.extdao.TblWorkDayYearDao;
import com.sales.ops.dto.delivery.ExpPoDto;
import com.sales.ops.dto.purchase.PurchaseDayDto;

import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.InventoryStatusEnum;
import com.sales.ops.service.delivery.exp_po.ExpPoService;
import com.sales.ops.service.po.OrderPurchaseService;
import com.sales.ops.service.wm.WmCommonService;
import com.sales.ops.serviceimpl.event.v3.order.entity.*;
import com.sales.ops.serviceimpl.event.v3.order.service.OrderRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

/**
 * bugid: 13685
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/8/27 9:53
 */
@Slf4j
@Service
public class ExpPoServiceImpl implements ExpPoService {

    @Resource
    private OrderPurchaseService orderPurchaseService;
    @Resource
    private WmCommonService wmCommonService;
    @Resource
    private TblWorkDayYearDao tblWorkDayYearDao;
    @Resource
    private OpsDeliveryPlanDao opsDeliveryPlanDao;
    @Resource
    private OrderRelationService orderRelationService;

    /**
     * 14912 20240827 c14717
     * inq-a 根据输入的客户期望货期倒算相应的采购单的期望发货日
     * @param expDate 客户期望货期
     * @param orderNo 订单号
     * @param orderItem 订单项号
     */
    @Override
    public List<ExpPoDto> calculatePoExpDate(Date expDate, String orderNo, Integer orderItem) throws OpsException {
        Order order = orderRelationService.getOrder(orderNo, orderItem);
        List<ExpPoDto> expPoDtos = new ArrayList<>();
        // 1.获取详情
        getDetails(order, expDate, expPoDtos);
        return expPoDtos;
    }

    /**
     *
     * @param order 订单关联关系
     */
    void getDetails(Order order, Date expDate, List<ExpPoDto> expPoDtos){
        for (JYCK jyck : order.getJycks()) {
            // 解析关联关系
            for (Relation relation : jyck.getRelations()) {
                if (relation instanceof CGRelation) {
                    // 供应商生产周期 P1 供应商运输周期P2 采购收货周期T1
                    getCGRelation(jyck, (CGRelation)relation, expDate, expPoDtos);
                }
            }
        }
    }


    /**
     *  invStatus.code = "P" 生产在途 || 0.虚拟库存 invStatus.code = "T0" 虚拟请购
     * @param jyck
     * @param relation
     * @param expDate
     * @param expPoDtos
     */
    void getCGRelation(JYCK jyck , CGRelation relation, Date expDate, List<ExpPoDto> expPoDtos){
        if(relation.getInvStatus().getCode().equals(InventoryStatusEnum.PRODUCE.getCode())
                || relation.getInvStatus().getCode().equals(InventoryStatusEnum.REQUEST.getCode())){
            //没有考虑调拨，如果有调拨需要此 T6->T5->T4->T2->T1->P2
            // T6->T5->T1->P2
            Date t6 = getT6(jyck, expDate);
            Date t5 = getT5(t6);
            Date t1 = getT1(t5);
            Date poExpDate = getP2(relation, t1);
            log.info("t6={},t5={},t1={},poExpDate={}",t6,t5,t1,poExpDate);
            ExpPoDto expPoDto = new ExpPoDto(jyck.getModelno(), poExpDate);
            expPoDtos.add(expPoDto);
        }
    }

    /**
     * 供应商运输周期P2
     * @param relation
     * @param expDate
     * @return
     */
    Date getP2(CGRelation relation, Date expDate){
        int cycleDays = 0; // 周期天数
        boolean workDayFalg = false;// 工作日标志
        //1. 获取供应商运输周期(P2)天数及是否是工作日计算
        PurchaseDayDto purchaseDay = orderPurchaseService.getPurchaseDay(relation.getRequestNo(), relation.getRequestItemNo(), relation.getRequestSplitNo());
        if(Objects.nonNull(purchaseDay)){
            cycleDays= purchaseDay.getTransDay();//供应商运输周期天数 P2
            if(purchaseDay.getTransWorkDay()){
                workDayFalg = true;
            }
        }
        // 计算差值
        return getExpDate(expDate, cycleDays, workDayFalg);
    }

    /**
     * 采购收货周期T1 cg
     * @param expDate
     * @return
     */
    Date getT1(Date expDate){
        int cycleDays = 0; // 周期天数
        boolean workDayFalg = false;// 工作日标志
        //1. 获取采购收货周期(T1)天数及是否是工作日计算
        OpsDeliveryPlanConfig configT1 = opsDeliveryPlanDao.getOpsDeliveryPlanConfig("T1");
        if(Objects.nonNull(configT1)){
            cycleDays = configT1.getDays();
            if(Objects.nonNull(configT1.getWorkday()) && configT1.getWorkday().equals(1)) {
                workDayFalg = true;
            }
        }
        // 计算差值
        return getExpDate(expDate, cycleDays, workDayFalg);
    }


    /**
     * 调拨周期T2
     * @param jyck
     * @param relation
     * @param expDate
     * @return
     */
    Date getT2(JYCK jyck, CGRelation relation, Date expDate){
        int cycleDays = 0; // 周期天数
        boolean workDayFalg = false;// 工作日标志
        //1. 获取调拨周期(T2)天数及是否是工作日计算
        OpsDeliveryPlanConfig configT2 = opsDeliveryPlanDao.getOpsDeliveryPlanConfig("T2");
        if(Objects.nonNull(configT2)){
            if(Objects.nonNull(configT2.getWorkday()) && configT2.getWorkday().equals(1)) {
                workDayFalg = true;
            }
        }
        CommonResult<Integer> findDBCKDDResult =  wmCommonService.getWarehouseDeliveryDayByCode(jyck.getWarehouseCode(),relation.getWarehouseCode());
        if(findDBCKDDResult.isSuccess()){
            cycleDays = findDBCKDDResult.getData();
        }
        // 计算差值
        return getExpDate(expDate, cycleDays, workDayFalg);
    }


    /**
     * 调拨收货周期T4（调拨入库）
     * @param expDate
     * @return
     */
    Date getT4(Date expDate){
        int cycleDays = 0; // 周期天数
        boolean workDayFalg = false;// 工作日标志
        //1. 获取采购收货周期(T4)天数及是否是工作日计算
        OpsDeliveryPlanConfig configT4 = opsDeliveryPlanDao.getOpsDeliveryPlanConfig("T4");
        if(Objects.nonNull(configT4)){
            cycleDays = configT4.getDays();
            if(Objects.nonNull(configT4.getWorkday()) && configT4.getWorkday().equals(1)) {
                workDayFalg = true;
            }
        }
        //计算差值
        return getExpDate(expDate, cycleDays, workDayFalg);
    }

    /**
     * 发货仓发货周期T5
     * @param expDate
     * @return
     */
    Date getT5(Date expDate){
        int cycleDays = 0; // 周期天数
        boolean workDayFalg = false;// 工作日标志
        // 获取采购收货周期(T5)天数及是否是工作日计算
        OpsDeliveryPlanConfig configT5 = opsDeliveryPlanDao.getOpsDeliveryPlanConfig("T5");
        if(Objects.nonNull(configT5)){
            cycleDays = configT5.getDays();
            if(Objects.nonNull(configT5.getWorkday()) && configT5.getWorkday().equals(1)) {
                workDayFalg = true;
            }
        }
        //计算差值
        return getExpDate(expDate, cycleDays, workDayFalg);
    }


    /**
     * 送达客户运输周期T6
     * @param jyck
     * @param expDate
     * @return
     */
    Date getT6(JYCK jyck, Date expDate){
        int cycleDays = 0; // 周期天数
        boolean workDayFalg = false;// 工作日标志
        //1. 获取采购收货周期(T6)天数及是否是工作日计算
        OpsDeliveryPlanConfig configT6 = opsDeliveryPlanDao.getOpsDeliveryPlanConfig("T6");
        if(Objects.nonNull(configT6)){
            if(Objects.nonNull(configT6.getWorkday()) && configT6.getWorkday().equals(1)) {
                workDayFalg = true;
            }
        }
        // 2.货期仓库到营业所配置时间
        CommonResult<Integer> warehouseToDeptConfig = wmCommonService.getWarehouseSalesbranchConfigByCode(jyck.getJyckDo().getDeptNo(), jyck.getWarehouseCode());
        if(warehouseToDeptConfig.isSuccess()){
            cycleDays = warehouseToDeptConfig.getData();
        }
        // 3. 计算差值
        return getExpDate(expDate, cycleDays, workDayFalg);
    }

    /**
     * tempDate - cycleDays 或 工作日
     * @param tempDate
     * @param cycleDays
     * @param workDayFlag
     * @return
     */
    public Date getExpDate(Date tempDate, int cycleDays, boolean workDayFlag){
        if(cycleDays>0) {
            Date beginDateResult = DateUtil.addDay(tempDate,cycleDays*(-1));
            if(workDayFlag){
                TblWorkdayYear workDay = tblWorkDayYearDao.getSubDaysWorkDay(DateUtil.dateToString(tempDate),cycleDays+1)
                        .get(cycleDays);
                beginDateResult = workDay.getWorkdayDate();
            }
            return beginDateResult;
        }
        return tempDate;
    }
}
