package com.sales.ops.serviceimpl.inventory;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.dao.StockTransferPlanItemMapper;
import com.sales.ops.db.dao.StockTransferPlanMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.AdjustInventoryQtyDao;
import com.sales.ops.dto.inventory.AdjustItemDTO;
import com.sales.ops.dto.inventory.AdjustParam;
import com.sales.ops.dto.inventory.OpsDoDto;
import com.sales.ops.dto.inventory.OpsRoDto;
import com.sales.ops.dto.query.OpsStockTransferPlanQO;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.service.inventory.AdjustInventoryService;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.order.TransOrderService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.service.wmOrder.BaseRoService;
import com.sales.ops.service.wmOrder.OpsDoService;
import com.sales.ops.service.wmOrder.OpsRoService;
import com.sales.ops.utils.WmOrderFactory;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.CommonServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author C12961
 * @date 2023/4/7 14:48
 */
@Slf4j
@Service
public class AdjustInventoryServiceImpl implements AdjustInventoryService {


    @Autowired
    private OpsInventoryPropertyService opsInventoryPropertyService;
    @Autowired
    private BaseInventoryService baseInventoryService;
    @Autowired
    private OpsRoService opsRoService;
    @Autowired
    private OpsDoService opsDoService;
    @Autowired
    private BaseDoService baseDoService;
    @Autowired
    private BaseRoService baseRoService;

    @Autowired
    private CommonServiceFeignApi commonServiceFeignApi;
    @Autowired
    private BasePoService basePoService;
    @Autowired
    private StockTransferPlanMapper stockTransferPlanMapper;
    @Autowired
    private StockTransferPlanItemMapper stockTransferPlanItemMapper;
    @Autowired
    private AdjustInventoryQtyDao adjustInventoryQtyDao;
    @Autowired
    private TransOrderService transOrderService;


    /**
     * budid:17771 c14717 20250630
     *  1.查询未完成计划列表
     *  2.查询采购表状态
     *  3.无采购单更新计划状态为完成
     *  4.采购单状态已完成，更新计划状态为完成
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finishPlanJob(){
        // 1.查询未完成计划列表
        List<StockTransferPlan> plans = adjustInventoryQtyDao.getStockTransferPlanNotFinish();
        if(CollectionUtils.isEmpty(plans)){
            return;
        }
        // 2.查询采购表状态
        for(StockTransferPlan plan : plans){
            OpsPurchaseorder purchase = basePoService.getPurchase(plan.getAssociateNo(), plan.getAssociateNoItem(), plan.getAssociateNoSplitno());
            if (Objects.isNull(purchase)){
                // 3.无采购单更新计划状态为完成
                adjustInventoryQtyDao.updateStockTransferPlanFinish("无采购单",plan.getAssociateNo(),plan.getAssociateNoItem(), plan.getAssociateNoSplitno());
                continue;
            }
            if(!StringUtils.equals(purchase.getStatecode(), PurchaseOrderStatusEnum.YIWANCHENG)){
                continue;
            }
            // 4.采购单状态已完成，更新计划状态为完成
            adjustInventoryQtyDao.updateStockTransferPlanFinish("采购单已完成",plan.getAssociateNo(),plan.getAssociateNoItem(), plan.getAssociateNoSplitno());
        }
    }




    /**
     * 处理采购单已完成状态 的调库计划
     * @param requestPurchase
     * @param purchaseorder
     * @param endUser
     * @param userName
     * 查验是否存在调拨单
     * flag=1 不存在调拨单            ：执行调库计划 warehouseCode = ro(cgrkbk) warehouseCode
     * flag=2 存在调拨单 且调拨单已入库 ：执行调库计划 warehouseCode = ro(dbrk)  warehouseCode
     * flag=3 存在调拨单 调拨单没入库  ：不执行调库计划，且创建调库计划时
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    public void handlePOFinishTransferPlan(OpsRequestpurchase requestPurchase,OpsPurchaseorder purchaseorder,String endUser,String userName) throws OpsException{
        int flag = 1;//1 不存在调拨单
        OpsRo oRo = null;
        //1.查询是否存在调拨单
        if(WMPurchaseTagEnum.WHOLE.getType().equals(requestPurchase.getWmtag())){
            List<OpsRo> roList = baseRoService.findRoByOrderItemNum(purchaseorder.getOrderno(), purchaseorder.getItemno(),
                    purchaseorder.getSplititemno(),null,null, RoTypeEnum.DBRK.getType());
            if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(roList)){
                oRo = roList.get(0);
                flag = 2;//存在调拨单,已收完货
                for(OpsRo opsRo : roList){
                    if(!RoStatusEnum.FINISH.getStatus().equals(opsRo.getRoStatus())){
                        flag = 3;// 存在调拨单，没收完货
                        break;
                    }
                }

            }
        }else {
            List<OpsRo> roList = baseRoService.findRoByOrderItemNum(purchaseorder.getOrderno(), purchaseorder.getItemno(),null
                    ,purchaseorder.getSplititemno(),null, RoTypeEnum.DBRK.getType());
            if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(roList)){
                oRo = roList.get(0);
                flag = 2;//存在调拨单,已收完货
                for(OpsRo opsRo : roList){
                    if(!RoStatusEnum.FINISH.getStatus().equals(opsRo.getRoStatus())){
                        flag = 3;// 存在调拨单，没收完货
                        break;
                    }
                }
            }
        }
        //2. 生成调库计划 或执行调库计划
        // 无调拨单
        if(flag == 1){
            String warehouseCode = requestPurchase.getPurchasewarehouseid();
            if(WMPurchaseTagEnum.WHOLE.getType().equals(requestPurchase.getWmtag())){
                List<OpsRo> roList = baseRoService.findRoByOrderItemNum(purchaseorder.getOrderno(), purchaseorder.getItemno(),
                        purchaseorder.getSplititemno(),null,null, RoTypeEnum.CGRKBK.getType());
                if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(roList)){
                    warehouseCode = roList.get(0).getWarehouseCode();
                }
            }else {
                List<OpsRo> roList = baseRoService.findRoByOrderItemNum(purchaseorder.getOrderno(), purchaseorder.getItemno(),null
                        ,purchaseorder.getSplititemno(),null, RoTypeEnum.CGRKBK.getType());
                if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(roList)){
                    warehouseCode = roList.get(0).getWarehouseCode();
                }
            }
            createTransferPlanForDelOrderPage(requestPurchase, purchaseorder, endUser, userName,null);
            exeStockTransferPlan(purchaseorder.getOrderno(), purchaseorder.getItemno(), purchaseorder.getSplititemno(),
                    requestPurchase.getQuantity(), true, warehouseCode);
        }
        // 存在调拨单 已收完货 存doid
        if(flag == 2){
            List<OpsDo> doList = baseDoService.findDBCKByOrder(oRo.getOrderId(), oRo.getOrderItem(), oRo.getNum(), oRo.getAssNum(), null);
            if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(doList)){
                // 存在调拨单 且调拨单已入库 ：执行调库计划 warehouseCode = ro(dbrk)  warehouseCode
                createTransferPlanForDelOrderPage(requestPurchase, purchaseorder, endUser, userName,doList.get(0).getDoId());
                exeStockTransferPlanByDoId(doList.get(0).getDoId(),requestPurchase.getQuantity(), true,doList.get(0).getGatherWarehouseCode());
            }
        }
        // 存在调拨单 没有收完货  存doid
        if(flag == 3){
            // * 存在调拨单 调拨单没入库  ：不执行调库计划，且创建调库计划时
            List<OpsDo> doList = baseDoService.findDBCKByOrder(oRo.getOrderId(), oRo.getOrderItem(), oRo.getNum(), oRo.getAssNum(), null);
            if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(doList)){
                // 存在调拨单 且调拨单已入库 ：执行调库计划 warehouseCode = ro(dbrk)  warehouseCode
                createTransferPlanForDelOrderPage(requestPurchase, purchaseorder, endUser, userName,doList.get(0).getDoId());
            }
        }
    }

    /**
     * 分页条件查询调库计划
     * 前端接口
     */
    @Override
    public PageInfo<StockTransferPlan> searchStockTransferPlanByPage(PageModel<OpsStockTransferPlanQO> pageModel) throws OpsException {
        OpsStockTransferPlanQO condition = pageModel.getCondition();
        StockTransferPlanExample example = new StockTransferPlanExample();
        StockTransferPlanExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("plan_no desc");
        criteria.andDelflagEqualTo(0);
        if (StringUtils.isNotBlank(condition.getPlanNo())) {
            criteria.andPlanNoEqualTo(condition.getPlanNo());
        }
        if (StringUtils.isNotBlank(condition.getPoNo())) {
            String[] poNos = condition.getPoNo().split("-");
            if(StringUtils.isNotBlank(poNos[0])){
                criteria.andAssociateNoEqualTo(poNos[0]);
            }
            if(poNos.length>1 && StringUtils.isNotBlank(poNos[1])){
                criteria.andAssociateNoItemEqualTo(Integer.parseInt(poNos[1]) );
            }
            if(poNos.length>2 && StringUtils.isNotBlank(poNos[2])){
                criteria.andAssociateNoSplitnoEqualTo(Integer.parseInt(poNos[2]) );
            }
        }
        if (StringUtils.isNotBlank(condition.getModelno())) {
            criteria.andModelnoEqualTo(condition.getModelno());
        }
        if (!Objects.isNull(condition.getStatus())) {
            criteria.andStatusEqualTo(condition.getStatus());
        }
        PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize());
        return new PageInfo<>(stockTransferPlanMapper.selectByExample(example));
    }

    /**
     * 获取计划明细
     * @param planNo
     * @return
     */
    @Override
    public List<StockTransferPlanItem> getStockTransferPlanItemList(String planNo){
        StockTransferPlanItemExample example = new StockTransferPlanItemExample();
        StockTransferPlanItemExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0);
        criteria.andPlanNoEqualTo(planNo);
        return stockTransferPlanItemMapper.selectByExample(example);
    }

    /**
     * 删除调库计划
     * @param
     * @return
     */
    @Override
    public Integer delPlan(List<String> planNoList, String userName) throws OpsException{
        int result = 0;
        // 验证调库计划状态 初始化可以
        int planNoListSize = planNoList.size();
        StockTransferPlanExample example = new StockTransferPlanExample();
        StockTransferPlanExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0);
        criteria.andPlanNoIn(planNoList);
        criteria.andStatusEqualTo(0);
        int size =  (int)stockTransferPlanMapper.countByExample(example);
        if(planNoListSize != size){
            throw Exceptions.OpsException("调库计划-状态不可删");
        }

        //删除调库计划
        StockTransferPlan planObj = new StockTransferPlan();
        StockTransferPlanExample exaPlan = new StockTransferPlanExample();
        StockTransferPlanExample.Criteria criPlan = exaPlan.createCriteria();
        criPlan.andDelflagEqualTo(0);
        criPlan.andPlanNoIn(planNoList);
        criPlan.andStatusEqualTo(0);
        planObj.setDelflag(1);
        planObj.setUpdateTime(DateUtil.getNow());
        planObj.setUpdator(userName);
        result = stockTransferPlanMapper.updateByExampleSelective(planObj,exaPlan);
        return result;
    }

    /**
     *  bugid:20149 发票入库优化-前端部分 c14717 20260209
     * 1.add采购单到货 创建调库计划
     * @throws OpsException
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Override
    public void createTransferPlanForADDOrder(String orderNo, Integer itemNo, Integer splitItemNo, String endUser, String modelNo, Integer qty, String creator) throws OpsException{
        Integer splitNo = 0;
        if(Objects.nonNull(splitItemNo)){
            splitNo = splitItemNo;
        }
        Integer count = adjustInventoryQtyDao.countStockTransferPlan(orderNo, itemNo, splitNo);
        if(count != 0){
            log.info("调库计划已存在");
            return;
        }

        StockTransferPlan obj = new StockTransferPlan();
        //创建人
        obj.setCreator(creator);
        //采购单号
        obj.setAssociateNo(orderNo);
        obj.setAssociateNoItem(itemNo);
        obj.setAssociateNoSplitno(splitNo);
        //调库计划数量 = 请购数量
        obj.setPlanQty(qty);
        obj.setModelno(modelNo);
        //目标库存类型
        obj.setGoalInvTypeCode(InventoryTypeEnum.GKTY.getType());
        obj.setGoalCustomerNo(endUser);
        //初始库存类型
        obj.setInitInvTypeCode(InventoryTypeEnum.TY.getType());

        //创建调库计划
        createStockTransferPlan(obj);
    }

    /**
     * bugid:12096 20231007 c14717 创建调库计划表
     * 1.删单页面 创建调库计划 ： a.请购实体;b.采购实体；c.创建人
     * @throws OpsException
     */
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    public void createTransferPlanForDelOrderPage(OpsRequestpurchase requestOrder, OpsPurchaseorder purchaseOrder,String endUser,
                                                  String creator, String doId) throws OpsException{
        Integer splitNo = 0;
        if(Objects.nonNull(purchaseOrder.getSplititemno())){
            splitNo = purchaseOrder.getSplititemno();
        }
        Integer count = adjustInventoryQtyDao.countStockTransferPlan(purchaseOrder.getOrderno(), purchaseOrder.getItemno(), splitNo);
        if(count != 0){
            if(Objects.nonNull(purchaseOrder.getMergeflag()) && purchaseOrder.getMergeflag()){
                // 合并采购单验证数量 bugid:15141 c14717 20240906
                Integer sumPlanQty = adjustInventoryQtyDao.sumStockTransferPlan(purchaseOrder.getOrderno(), purchaseOrder.getItemno(), splitNo);
                List<OpsRequestpurchase> requestPurchaseByPurchase = basePoService.getRequestPurchaseByPurchase(purchaseOrder.getOrderno(), purchaseOrder.getItemno(), splitNo);
                if(!CollectionUtils.isEmpty(requestPurchaseByPurchase)){
                    int requestTotalSum = requestPurchaseByPurchase.stream().mapToInt(OpsRequestpurchase::getQuantity).sum();//计算采购单查询 请购数量之和
                    // 合并采购数量小于等于计划数量之和 则报错已存在
                    if((requestTotalSum - sumPlanQty) <= 0){
                        throw Exceptions.OpsException("调库计划-计划已存在");
                    }
                }
            }else {
                //非合并采购单
                throw Exceptions.OpsException("调库计划-计划已存在");
            }

        }

        StockTransferPlan obj = new StockTransferPlan();
        //创建人
        obj.setCreator(creator);
        //采购单号
        obj.setAssociateNo(purchaseOrder.getOrderno());
        obj.setAssociateNoItem(purchaseOrder.getItemno());
        obj.setAssociateNoSplitno(purchaseOrder.getSplititemno());
        //调库计划数量 = 请购数量
        obj.setPlanQty(requestOrder.getQuantity());
        obj.setModelno(requestOrder.getModelno());
        //目标库存类型
        obj.setGoalInvTypeCode(InventoryTypeEnum.GKTY.getType());
        obj.setGoalCustomerNo(endUser);
        if(InventoryTypeEnum.GKTY.getType().equals(requestOrder.getInventorytypecode())){
            throw Exceptions.OpsException("调库计划-初始类型和目标类型一致");
        }
        //初始库存类型
        initInvTypeValue(requestOrder,obj);
        if(StringUtils.isNotBlank(doId)){
            obj.setDoid(doId);
        }
        //创建调库计划
        createStockTransferPlan(obj);
    }

    /**
     * 初始库存类型 赋值
     * @param requestOrder
     * @param obj
     * @throws OpsException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initInvTypeValue(OpsRequestpurchase requestOrder, StockTransferPlan obj) throws OpsException{
        obj.setInitInvTypeCode(requestOrder.getInventorytypecode());
        //顾客ppl
        if(InventoryTypeEnum.GKPPL.getType().equals(requestOrder.getInventorytypecode())){
            obj.setInitCustomerNo(requestOrder.getUserno());
            obj.setInitInvTypeValue(requestOrder.getPpl());
        }
        //顾客项目
        if(InventoryTypeEnum.GKPJ.getType().equals(requestOrder.getInventorytypecode())){
            obj.setInitCustomerNo(requestOrder.getUserno());
            obj.setInitInvTypeValue(requestOrder.getProjectcode());
        }
        //顾客通用
        if(InventoryTypeEnum.GKTY.getType().equals(requestOrder.getInventorytypecode())){
            obj.setInitInvTypeValue(requestOrder.getUserno());
        }
        //战略产品在库
        /*if(InventoryTypeEnum.ZLCP.getType().equals(plan.getGoalInvTypeCode())){
            obj.setFromInventoryTypeCode(InventoryTypeEnum.ZLCP.getType());
        }*/
        //战略行业
        if(InventoryTypeEnum.ZLHY.getType().equals(requestOrder.getInventorytypecode())){
            obj.setInitInvTypeValue(requestOrder.getGroupcustomerno());
        }
        //战略集团
        if(InventoryTypeEnum.ZLJT.getType().equals(requestOrder.getInventorytypecode())){
            obj.setInitInvTypeValue(requestOrder.getGroupcustomerno());
        }
        //战略PJ
        if(InventoryTypeEnum.ZLPJ.getType().equals(requestOrder.getInventorytypecode())){
            obj.setInitInvTypeValue(requestOrder.getProjectcode());
        }
    }

    /**
     * bugid: 12911
     * 20230104 c14717
     * 采购删单后删除采购计划
     * 软删除主表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delPoAfterDelTPlan(String poNo, Integer itemNo, Integer splitNo){
        if (Objects.isNull(splitNo)) {
            splitNo = 0;
        }
        Integer count = adjustInventoryQtyDao.countStockTransferPlan(poNo, itemNo, splitNo);
        // 无计划
        if( count == 0 ){
            return;
        }
        //删除调库计划
        StockTransferPlan planObj = new StockTransferPlan();
        StockTransferPlanExample exaPlan = new StockTransferPlanExample();
        StockTransferPlanExample.Criteria criPlan = exaPlan.createCriteria();
        criPlan.andDelflagEqualTo(0);
        criPlan.andAssociateNoEqualTo(poNo);
        criPlan.andAssociateNoItemEqualTo(itemNo);
        criPlan.andAssociateNoSplitnoEqualTo(splitNo);
        planObj.setDelflag(1);
        planObj.setUpdateTime(DateUtil.getNow());
        planObj.setUpdator("采购删单");
        stockTransferPlanMapper.updateByExampleSelective(planObj,exaPlan);
    }

    /**
     * bugid:12096 20231007 c14717 创建调库计划表
     * 1.删单页面需要传参数 ： a.采购单号 b.采购项号 c.采购拆分号 d.顾客号 e.计划数量=采购数量 f.型号 g.创建人
     * 2.其他创建形式传参数 ：... h.库存属性
     * @param obj
     * @throws OpsException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createStockTransferPlan(StockTransferPlan obj) throws OpsException{
        if(StringUtils.isBlank(obj.getCreator())){
            throw Exceptions.OpsException("调库计划-无创建人");
        }
        if(StringUtils.isBlank(obj.getModelno())){
            throw Exceptions.OpsException("调库计划-无型号");
        }
        if(Objects.isNull(obj.getPlanQty())){
            throw Exceptions.OpsException("调库计划-无计划调库数量");
        }
        if(obj.getPlanQty().equals(0)){
            throw Exceptions.OpsException("调库计划-调库数量为0");
        }
        if(StringUtils.isBlank(obj.getAssociateNo()) || Objects.isNull(obj.getAssociateNoItem())){
            throw Exceptions.OpsException("调库计划-无采购单号");
        }

        //------ 初始
        if(StringUtils.isBlank(obj.getInitInvTypeCode())){
            throw Exceptions.OpsException("调库计划-无目标库存类型");
        }
        //顾客ppl
        if(InventoryTypeEnum.GKPPL.getType().equals(obj.getInitInvTypeCode())){
            if(StringUtils.isBlank(obj.getInitCustomerNo())){
                throw Exceptions.OpsException("调库计划-无初始顾客号");
            }
            if(StringUtils.isBlank(obj.getInitInvTypeValue())){
                throw Exceptions.OpsException("调库计划-无初始PPL号");
            }
        }
        //顾客项目
        if(InventoryTypeEnum.GKPJ.getType().equals(obj.getInitInvTypeCode())){
            if(StringUtils.isBlank(obj.getInitCustomerNo())){
                throw Exceptions.OpsException("调库计划-无初始顾客号");
            }
            if(StringUtils.isBlank(obj.getInitInvTypeValue())){
                throw Exceptions.OpsException("调库计划-无初始项目号");
            }
        }
        //顾客通用
        if(InventoryTypeEnum.GKTY.getType().equals(obj.getInitInvTypeCode())){
            if(StringUtils.isBlank(obj.getInitCustomerNo())){
                throw Exceptions.OpsException("调库计划-无初始顾客号");
            }
        }
        //战略产品在库
        if(InventoryTypeEnum.ZLCP.getType().equals(obj.getInitInvTypeCode())){
            if(StringUtils.isBlank(obj.getInitInvTypeValue())){
                throw Exceptions.OpsException("调库计划-无初始战略产品号");
            }
        }
        //战略行业
        if(InventoryTypeEnum.ZLHY.getType().equals(obj.getInitInvTypeCode())){
            if(StringUtils.isBlank(obj.getInitInvTypeValue())){
                throw Exceptions.OpsException("调库计划-无初始战略行业号");
            }
        }
        //战略集团
        if(InventoryTypeEnum.ZLJT.getType().equals(obj.getInitInvTypeCode())){
            if(StringUtils.isBlank(obj.getInitInvTypeValue())){
                throw Exceptions.OpsException("调库计划-无初始战略集团号");
            }
        }
        //战略PJ
        if(InventoryTypeEnum.ZLPJ.getType().equals(obj.getInitInvTypeCode())){
            if(StringUtils.isBlank(obj.getInitCustomerNo())){
                throw Exceptions.OpsException("调库计划-无初始战略PJ号");
            }
        }

        //------------目标
        if(StringUtils.isBlank(obj.getGoalInvTypeCode())){
            throw Exceptions.OpsException("调库计划-无目标库存类型");
        }
        //顾客ppl
        if(InventoryTypeEnum.GKPPL.getType().equals(obj.getGoalInvTypeCode())){
            if(StringUtils.isBlank(obj.getGoalCustomerNo())){
                throw Exceptions.OpsException("调库计划-无目标顾客号");
            }
            if(StringUtils.isBlank(obj.getGoalInvTypeValue())){
                throw Exceptions.OpsException("调库计划-无目标PPL号");
            }
        }
        //顾客项目
        if(InventoryTypeEnum.GKPJ.getType().equals(obj.getGoalInvTypeCode())){
            if(StringUtils.isBlank(obj.getGoalCustomerNo())){
                throw Exceptions.OpsException("调库计划-无目标顾客号");
            }
            if(StringUtils.isBlank(obj.getGoalInvTypeValue())){
                throw Exceptions.OpsException("调库计划-无目标项目号");
            }
        }
        //顾客通用
        if(InventoryTypeEnum.GKTY.getType().equals(obj.getGoalInvTypeCode())){
            if(StringUtils.isBlank(obj.getGoalCustomerNo())){
                throw Exceptions.OpsException("调库计划-无目标顾客号");
            }
        }
        //战略产品在库
        if(InventoryTypeEnum.ZLCP.getType().equals(obj.getGoalInvTypeCode())){
            if(StringUtils.isBlank(obj.getGoalInvTypeValue())){
                throw Exceptions.OpsException("调库计划-无目标战略产品号");
            }
        }
        //战略行业
        if(InventoryTypeEnum.ZLHY.getType().equals(obj.getGoalInvTypeCode())){
            if(StringUtils.isBlank(obj.getGoalInvTypeValue())){
                throw Exceptions.OpsException("调库计划-无目标战略行业号");
            }
        }
        //战略集团
        if(InventoryTypeEnum.ZLJT.getType().equals(obj.getGoalInvTypeCode())){
            if(StringUtils.isBlank(obj.getGoalInvTypeValue())){
                throw Exceptions.OpsException("调库计划-无目标战略集团号");
            }
        }
        //战略PJ
        if(InventoryTypeEnum.ZLPJ.getType().equals(obj.getGoalInvTypeCode())){
            if(StringUtils.isBlank(obj.getGoalCustomerNo())){
                throw Exceptions.OpsException("调库计划-无目标战略PJ号");
            }
        }
        //采购拆分单面如果为空，填写0
        if(Objects.isNull(obj.getAssociateNoSplitno())){
            obj.setAssociateNoSplitno(0);
        }
        /*Integer sumPlanQty = adjustInventoryQtyDao.sumStockTransferPlanPlanQty(obj.getAssociateNo(), obj.getAssociateNoItem(),
                obj.getAssociateNoSplitno());
        if(Objects.nonNull(sumPlanQty) && sumPlanQty  > obj.getPlanQty() ){
            throw Exceptions.OpsException("调库计划-计划数量大于采购数量");
        }*/
        // 获取计划单号
        ResultVo<String> resultVo = commonServiceFeignApi.generatorBillNo("41");//1.写入调库计划表 生成调库计划单号
        if(Objects.isNull(resultVo) || !resultVo.isSuccess()){
            throw Exceptions.OpsException("调库计划-生成单号失败");
        }
        obj.setPlanNo(resultVo.getData());
        stockTransferPlanMapper.insertSelective(obj);
    }

    /**
     * bugid:12096 20231007 c14717 执行调库计划
     * @param poNo 采购单号
     * @param itemNo 采购项号
     * @param splitNo 采购拆分号 如果拆分单号为空 传值 0
     * @param roFinQty 收货数量
     * @param poFinish 采购是否完成
     * @param warehouseCode 收货仓库
     * @throws OpsException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exeStockTransferPlan(String poNo, Integer itemNo, Integer splitNo, Integer roFinQty, Boolean poFinish, String warehouseCode) throws OpsException{
        //校验数量
        if(Objects.isNull(splitNo)){
            splitNo = 0;
        }
        Integer count = adjustInventoryQtyDao.countStockTransferPlan(poNo, itemNo, splitNo);
        // 无计划
        if( count == 0 ){
            return;
        }
        List<StockTransferPlan> stockTransferPlans = adjustInventoryQtyDao.selectStockTransferPlan(poNo, itemNo, splitNo);
        // 获取计划单号
        ResultVo<String> resultVo = commonServiceFeignApi.generatorBillNo("14");//1.生成调库单号
        if(Objects.isNull(resultVo) || !resultVo.isSuccess()){
            throw Exceptions.OpsException("调库计划-生成单号失败");
        }
        Integer orderItem = 0;
        for (StockTransferPlan obj : stockTransferPlans){
            if(obj.getStatus() == 2){//已收货完成
                continue;
            }
            if (roFinQty == 0){
                break;
            }
            orderItem ++;
            //如采购完成 把调库计划表未完成数量全部调库
            Integer finishQty = obj.getPlanQty() - obj.getFinishQty();
            int status = 2;//调库完成
            //如果采购没有完成
            if(!poFinish){
                //如果收货数大于等于计划可调库数量，那么调库数量=计划可调库数量
                if(roFinQty >= finishQty){
                    roFinQty = roFinQty - finishQty;
                }else {
                    // 收货数小于计划可调库数量 ，那么调库数量 = 收货数
                    finishQty = roFinQty;
                    status = 1;//部分完成
                    roFinQty = 0;
                }
            }
            try {
                exeOneStockTransferPlan(obj,finishQty,status,poFinish,resultVo,orderItem,warehouseCode);
            } catch (Exception e) {
                log.error("调库计划失败"+e.getMessage());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    public void exeOneStockTransferPlan(StockTransferPlan obj, Integer finishQty, int status, Boolean poFinish,
                                        ResultVo<String> resultVo, Integer orderItem, String warehouseCode) throws OpsException{
        // 初始化数据
        StockTransferPlanItem stockTransferPlanItem = initStockTransferPlanItem(obj, finishQty, resultVo.getData(), orderItem.toString(),warehouseCode);
        TransOrder transOrder = initTransOrder(obj, stockTransferPlanItem, warehouseCode);
        // 验证库存数量 调库数量 = 实际库存数量 验证负数
        Integer invQty = checkInvQty(transOrder);
        transOrder.setQuantity(invQty);
        stockTransferPlanItem.setFinishQty(invQty);
        //bugid:12507 c14717 2023/10/31
            /*if( status == 2 && !finishQty.equals(invQty) ){
                status = 1;
            }*/
        finishQty = invQty;
        String updator = "采购部分入库";
        if(poFinish){
            updator = "采购完成";
        }
        stockTransferPlanItem.setCreator(updator);
        //1.写入调库计划子表
        stockTransferPlanItemMapper.insertSelective(stockTransferPlanItem);
        //2. 调用调库接口
        transOrderService.createTransOrderOld(transOrder, UserDto.AUTO);
        // 3. 更新调库主表
        adjustInventoryQtyDao.updateStockTransferPlan(status,finishQty+obj.getFinishQty(),obj.getPlanNo(),updator);
    }


    /**
     * bugid:12096 20231007 c14717 执行调库计划
     * @param doId 调拨单Id
     * @param roFinQty 收货数量
     * @param dbFinish 调拨是否完成
     * @param warehouseCode 收货仓库
     * @throws OpsException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exeStockTransferPlanByDoId(String doId, Integer roFinQty, Boolean dbFinish, String warehouseCode) throws OpsException{

        Integer count = adjustInventoryQtyDao.countStockTransferPlanByDoid(doId);
        // 无计划
        if( count == 0 ){
            return;
        }
        List<StockTransferPlan> stockTransferPlans = adjustInventoryQtyDao.selectStockTransferPlanByDoId(doId);
        // 获取计划单号
        ResultVo<String> resultVo = commonServiceFeignApi.generatorBillNo("14");//1.生成调库单号
        if(Objects.isNull(resultVo) || !resultVo.isSuccess()){
            throw Exceptions.OpsException("调库计划-生成单号失败");
        }
        Integer orderItem = 0;
        for (StockTransferPlan obj : stockTransferPlans){
            if(obj.getStatus() == 2){//已收货完成
                continue;
            }
            if (roFinQty == 0){
                break;
            }
            orderItem ++;
            //如采购完成 把调库计划表未完成数量全部调库
            Integer finishQty = obj.getPlanQty() - obj.getFinishQty();
            int status = 2;//调库完成
            //如果采购没有完成
            if(!dbFinish){
                //如果收货数大于等于计划可调库数量，那么调库数量=计划可调库数量
                if(roFinQty >= finishQty){
                    roFinQty = roFinQty - finishQty;
                }else {
                    // 收货数小于计划可调库数量 ，那么调库数量 = 收货数
                    finishQty = roFinQty;
                    status = 1;//部分完成
                }
            }
            try {
                exeOneStockTransferPlan(obj,finishQty,status,dbFinish,resultVo,orderItem,warehouseCode);
            } catch (OpsException e) {
                log.error("调库计划失败"+e.getMessage());
            }
        }
    }


    // 调库计划校验库存数量，返回可调数量
    public Integer checkInvQty(TransOrder transOrder) throws OpsException{
        OpsInventory inv = new OpsInventory();
        inv.setWarehouseCode(transOrder.getFromWarehouseCode());
        inv.setModelno(transOrder.getModelNo());
        OpsInventoryProperty property = new OpsInventoryProperty();
        property.setInventoryTypeCode(transOrder.getFromInventoryTypeCode());
        property.setCustomerNo(transOrder.getFromCustomerNo());
        property.setPpl(transOrder.getFromPpl());
        property.setProjectCode(transOrder.getFromProjectCode());
        property.setGroupCustomerNo(transOrder.getFromGroupCustomerNo());
        property.setSalesInfoNo(transOrder.getFromSalesInfoNo());
        List<OpsInventory> invList = baseInventoryService.findOpsInventory(inv, property);
        invList.sort(Comparator.comparing(OpsInventory::getQuantity));
        int sumInvQty = 0;
        for (OpsInventory opsInventory : invList) {
            //bugid:17944 c14717 20250616 跳过负库存
            if(opsInventory.getQuantity() <= opsInventory.getPrepareQuantity()){
                continue;
            }
            // 可用数量
            int hasqty = opsInventory.getQuantity() - opsInventory.getPrepareQuantity();
            sumInvQty = hasqty + sumInvQty;
        }
        if(sumInvQty <= 0){
            throw Exceptions.OpsException("无可用库存");
        }
        if(sumInvQty - transOrder.getQuantity() > 0){
            return transOrder.getQuantity();
        } else {
            return sumInvQty ;
        }
    }
    // 初始化调库计划表
    public StockTransferPlanItem initStockTransferPlanItem(StockTransferPlan stockTransferPlan,Integer finishQty
            ,String OrderNo ,String itemNo,String warehouseCode){
        StockTransferPlanItem obj = new StockTransferPlanItem();
        obj.setCreateTime(DateUtil.getNow());
        obj.setPlanNo(stockTransferPlan.getPlanNo());
        obj.setModelno(stockTransferPlan.getModelno());
        obj.setFinishQty(finishQty);
        obj.setTransferOrderNo(OrderNo);
        obj.setTransferOrderItem(itemNo);
        obj.setWarehouseCode(warehouseCode);
        obj.setDelflag(0);
        return obj;
    }

    //初始化表
    public TransOrder initTransOrder(StockTransferPlan plan,StockTransferPlanItem planItem,String warehouseCode){
        TransOrder obj = new TransOrder();
        //bugid:12507 c14717 2023/10/31
        obj.setTransType(1);//调库
        obj.setFromType(2);//调库申请
        obj.setFromNo(plan.getPlanNo());

        obj.setTransNo(planItem.getTransferOrderNo());
        obj.setItemNo(Integer.parseInt(planItem.getTransferOrderItem()));
        obj.setModelNo(planItem.getModelno());
        obj.setQuantity(planItem.getFinishQty());
        //init
        obj.setFromWarehouseCode(warehouseCode);

        obj.setFromInventoryTypeCode("TY");
        //顾客ppl
        if(InventoryTypeEnum.GKPPL.getType().equals(plan.getInitInvTypeCode())){
            obj.setFromInventoryTypeCode(InventoryTypeEnum.GKPPL.getType());
            obj.setFromCustomerNo(plan.getInitCustomerNo());
            obj.setFromPpl(plan.getInitInvTypeValue());
        }
        //顾客项目
        if(InventoryTypeEnum.GKPJ.getType().equals(plan.getInitInvTypeCode())){
            obj.setFromInventoryTypeCode(InventoryTypeEnum.GKPJ.getType());
            obj.setFromCustomerNo(plan.getInitCustomerNo());
            obj.setFromPpl(plan.getInitInvTypeValue());
        }
        //顾客通用
        if(InventoryTypeEnum.GKTY.getType().equals(plan.getInitInvTypeCode())){
            obj.setFromInventoryTypeCode(InventoryTypeEnum.GKTY.getType());
            obj.setFromCustomerNo(plan.getInitCustomerNo());
        }
        //战略产品在库
        /*if(InventoryTypeEnum.ZLCP.getType().equals(plan.getGoalInvTypeCode())){
            obj.setFromInventoryTypeCode(InventoryTypeEnum.ZLCP.getType());
        }*/
        //战略行业
        if(InventoryTypeEnum.ZLHY.getType().equals(plan.getInitInvTypeCode())){
            obj.setFromInventoryTypeCode(InventoryTypeEnum.ZLHY.getType());
            obj.setFromGroupCustomerNo(plan.getInitInvTypeValue());
        }
        //战略集团
        if(InventoryTypeEnum.ZLJT.getType().equals(plan.getInitInvTypeCode())){
            obj.setFromInventoryTypeCode(InventoryTypeEnum.ZLJT.getType());
            obj.setFromGroupCustomerNo(plan.getInitInvTypeValue());
        }
        //战略PJ
        if(InventoryTypeEnum.ZLPJ.getType().equals(plan.getInitInvTypeCode())){
            obj.setFromInventoryTypeCode(InventoryTypeEnum.ZLPJ.getType());
            obj.setFromProjectCode(plan.getInitInvTypeValue());
        }

        //goal 目标
        obj.setToWarehouseCode(warehouseCode);
        obj.setToInventoryTypeCode("TY");
        //顾客ppl
        if(InventoryTypeEnum.GKPPL.getType().equals(plan.getGoalInvTypeCode())){
            obj.setToInventoryTypeCode(InventoryTypeEnum.GKPPL.getType());
            obj.setToCustomerNo(plan.getGoalCustomerNo());
            obj.setToPpl(plan.getGoalInvTypeValue());
        }
        //顾客项目
        if(InventoryTypeEnum.GKPJ.getType().equals(plan.getGoalInvTypeCode())){
            obj.setToInventoryTypeCode(InventoryTypeEnum.GKPJ.getType());
            obj.setToCustomerNo(plan.getGoalCustomerNo());
            obj.setToPpl(plan.getGoalInvTypeValue());
        }
        //顾客通用
        if(InventoryTypeEnum.GKTY.getType().equals(plan.getGoalInvTypeCode())){
            obj.setToInventoryTypeCode(InventoryTypeEnum.GKTY.getType());
            obj.setToCustomerNo(plan.getGoalCustomerNo());
        }
        //战略产品在库
        /*if(InventoryTypeEnum.ZLCP.getType().equals(plan.getGoalInvTypeCode())){
            obj.setFromInventoryTypeCode(InventoryTypeEnum.ZLCP.getType());
        }*/
        //战略行业
        if(InventoryTypeEnum.ZLHY.getType().equals(plan.getGoalInvTypeCode())){
            obj.setToInventoryTypeCode(InventoryTypeEnum.ZLHY.getType());
            obj.setToGroupCustomerNo(plan.getGoalInvTypeValue());
        }
        //战略集团
        if(InventoryTypeEnum.ZLJT.getType().equals(plan.getGoalInvTypeCode())){
            obj.setToInventoryTypeCode(InventoryTypeEnum.ZLJT.getType());
            obj.setToGroupCustomerNo(plan.getGoalInvTypeValue());
        }
        //战略PJ
        if(InventoryTypeEnum.ZLPJ.getType().equals(plan.getGoalInvTypeCode())){
            obj.setToInventoryTypeCode(InventoryTypeEnum.ZLPJ.getType());
            obj.setToProjectCode(plan.getGoalInvTypeValue());
        }


        obj.setWmsDlvDate(DateUtil.getNow);
        return obj;
    }



    @Override
    @Transactional(rollbackFor = OpsException.class)
    public AdjustParam adjustInventory(AdjustParam param) {
        // 事务问题，废弃该方法
        return param;
    }


    @Override
    @Transactional(rollbackFor = OpsException.class)
    public void adjustItemForSub(AdjustItemDTO adjustItem, boolean isAvailable, UserDto userDto) throws OpsException {
        OpsInventory inventory = getInventory(adjustItem, userDto);
        if (adjustItem.getQty() <= 0) {
            throw Exceptions.OpsException("调账数量应该大于0");
        }
        Integer hasQty = inventory.getQuantity() - inventory.getPrepareQuantity();
        if (isAvailable && hasQty < adjustItem.getQty()) {
            throw Exceptions.OpsException("库存数量不足：" + hasQty + ",库存id:" + inventory.getInventoryId());
        }
        OpsDoDto opsDoDto = WmOrderFactory.createAdjustDoAndItem(adjustItem.getOrderId(), adjustItem.getOrderItem(), inventory, adjustItem.getQty());
        boolean exist = baseDoService.existsDoByDoId(opsDoDto.getOpsDo().getDoId());
        if (exist) {
            throw Exceptions.AdjustOrderException("该doId已存在：" + opsDoDto.getOpsDo().getDoId());
        }
        // 插入数据
        opsDoService.insertDo(opsDoDto.getOpsDo(), opsDoDto.getDoItem(), opsDoDto.getDoItemInventoryList(), userDto);
        // 给库存减少数量和记录库存变更日志
        opsDoService.updateQtyForDo(opsDoDto.getOpsDo(), opsDoDto.getDoItemInventoryList(), userDto);
    }


    @Override
    @Transactional(rollbackFor = OpsException.class)
    public void adjustItemForAdd(AdjustItemDTO adjustItem, UserDto userDto) throws OpsException {
        // 获取或创建inventory
        OpsInventory inventory = getInventory(adjustItem, userDto);
        // 创建ro、ro_item、ro_item_inventory
        OpsRoDto opsRoDto = WmOrderFactory.createAdjustRoAndItem(adjustItem.getInvoiceNo(), adjustItem.getOrderId(), adjustItem.getOrderItem(),
                inventory, adjustItem.getQty());
        boolean exist = baseRoService.existsRoByRoId(opsRoDto.getOpsRo().getRoId());
        if (exist) {
            throw Exceptions.AdjustOrderException("该ro已存在：" + opsRoDto.getOpsRo().getRoId());
        }
        // 插入数据
        opsRoService.insertRo(opsRoDto.getOpsRo(), opsRoDto.getOpsRoItem(), opsRoDto.getRoItemInventoryList(), userDto);
        // 给库存添加数量和记录库存变更日志
        opsRoService.updateQtyForRo(opsRoDto.getOpsRo(), opsRoDto.getRoItemInventoryList().get(0), userDto);
    }

    private OpsInventory getInventory(AdjustItemDTO adjustItem, UserDto userDto) throws OpsException {
        OpsInventory opsInventory = null;
        OpsInventory condition = getInventoryCondition(adjustItem, userDto);
        List<OpsInventory> opsInventoryList = baseInventoryService.findOpsInventory(condition);
        if (CollectionUtils.isEmpty(opsInventoryList)) {
            condition.setInventoryStatus(InventoryStatusEnum.NORMAL.getCode());
            condition.setQuantity(0);
            condition.setPrepareQuantity(0);
            condition.setQaStatus(QAStatusEnum.NORMAL.getType());
            Long invId = baseInventoryService.createInvReturnId(condition, UserDto.AUTO);
            opsInventory = baseInventoryService.getInventoryById(invId);
        } else if (opsInventoryList.size() == 1) {
            opsInventory = opsInventoryList.get(0);
        } else if (opsInventoryList.size() > 1) {
            List<Long> ids = opsInventoryList.stream().map(OpsInventory::getInventoryId).collect(Collectors.toList());
            throw Exceptions.OpsException("查询出多条inventory:" + JSONUtil.toJsonStr(ids));
        }
        return opsInventory;
    }


    private OpsInventory getInventoryCondition(AdjustItemDTO item, UserDto userDto) throws OpsException {
        OpsInventoryProperty propertyCondition = getPropertyCondition(item);
        Long propertyId = opsInventoryPropertyService.findPropertyWithInsertByExample(propertyCondition, userDto);
        OpsInventory condition = new OpsInventory();
        condition.setWarehouseCode(item.getWarehouseCode());
        condition.setModelno(item.getModelNo());
        condition.setInventoryPropertyId(propertyId);
        return condition;
    }

    private OpsInventoryProperty getPropertyCondition(AdjustItemDTO item) {
        OpsInventoryProperty property = new OpsInventoryProperty();
        property.setInventoryTypeCode(item.getPropertyType().getType());
        property.setCustomerNo(StringUtils.isNotBlank(item.getCustomerNo()) ? item.getCustomerNo() : null);
        property.setGroupCustomerNo(StringUtils.isNotBlank(item.getGroupCustomerNo()) ? item.getGroupCustomerNo() : null);
        property.setPpl(StringUtils.isNotBlank(item.getPpl()) ? item.getPpl() : null);
        property.setProjectCode(StringUtils.isNotBlank(item.getProjectCode()) ? item.getProjectCode() : null);
        return property;
    }


}
