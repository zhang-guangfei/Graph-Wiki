package com.sales.ops.serviceimpl.wmOrder;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.common.until.PriceCompute;
import com.sales.ops.common.until.StringPhoneUtil;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.*;
import com.sales.ops.dto.flux.CancelDocOrderDto;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.order.*;
import com.sales.ops.dto.query.OpsDeliveryOrderQO;
import com.sales.ops.dto.util.*;
import com.sales.ops.enums.*;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.feign.OPSWarehouseFeignApi;
import com.sales.ops.feign.OpsCallWmsFeignApi;
import com.sales.ops.service.ba.OpsWarehouseService;
import com.sales.ops.service.inventory.AllotInvenToryService;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.inventory.OpsInventoryService;
import com.sales.ops.service.inventory.WmRouterOrderService;
import com.sales.ops.service.log.OpsInventoryLogService;
import com.sales.ops.service.log.OpsOrderModiDataService;
import com.sales.ops.service.order.BaseCustomerOrderService;
import com.sales.ops.service.order.ModifyCustomerOrderService;
import com.sales.ops.service.order.TransOrderService;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.po.FinishPoService;
import com.sales.ops.service.wm.WmCommonService;
import com.sales.ops.service.wmOrder.*;
import com.sales.ops.serviceimpl.event.v3.CustomerEventPublisher;
import com.sales.ops.serviceimpl.event.v3.TransferEventPublisher;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import com.sales.ops.utils.DoBuilder;
import com.sales.ops.utils.WmOrderNoFactory;
import com.smc.smccloud.core.model.enums.CarrierEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


/**
 * @description 出库指令服务
 * @date 2021/9/23 11:18
 * @auther C12961
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OpsDoServiceImpl implements OpsDoService {

    @Resource
    private BaseDoService baseDoService;
    @Resource
    private OpsDoMapper opsDoMapper;
    @Resource
    private OpsDoItemMapper opsDoItemMapper;
    @Resource
    private OpsDoItemInventoryMapper opsDoItemInventoryMapper;
    @Resource
    private OpsInventoryService opsInventoryService;
    @Resource
    private BaseInventoryService baseInventoryService;
    @Resource
    private WmOrderTaskService wmOrderTaskService;
    @Resource
    private OpsOrderCancelDao opsOrderCancelDao;
    @Resource
    private OpsOrderCancelItemMapper opsOrderCancelItemMapper;
    @Resource
    private OpsOrderUpdateMapper opsOrderUpdateMapper;
    @Resource
    private RcvViewMapper rcvViewMapper;

    @Resource
    private OpsDoPostMapper opsDoPostMapper;//物流对账表
    @Resource
    private ExpdetailMapper expdetailMapper;
    @Resource
    private AllotInvenToryService allotInvenToryService;
    @Resource
    private OpsInventoryLogService opsInventoryLogService;
    @Resource
    private OPSWarehouseFeignApi opsWarehouseFeignApi;
    @Resource
    private OpsCallWmsFeignApi opsCallWmsFeignApi;
    @Resource
    private OpsPcoMapper opsPcoMapper;
    @Resource
    private OpsPcoItemInventoryMapper pcoItemInventoryMapper;
    @Resource
    private OpsPcoService opsPcoService;
    @Resource
    private WmRouterOrderService wmRouterOrderService;
    @Resource
    private OpsWmOrderTaskMapper opsWmOrderTaskMapper;

    @Resource
    private OpsPcoItemMapper opsPcoItemMapper;
    @Resource
    private OpsPcoItemInventoryMapper opsPcoItemInventoryMapper;
    @Resource
    private OpsInventoryPropertyService opsInventoryPropertyService;

    @Resource
    private OpsRoService opsRoService;

    @Autowired
    private OpsWarehouseService opsWarehouseService;
    @Autowired
    private OPSWarehouseDao opsWarehouseDao;

    @Resource
    OpsOrderUpdateLogMapper opsOrderUpdateLogMapper;

    @Autowired
    private OpsRequestpurchaseMapper opsRequestpurchaseMapper;

    @Autowired
    private WmDoService wmDoService;
    @Autowired
    private WmOrderTaskFindService findWmsOrderTaskService;

    @Autowired
    private OpsCoordinateMapper opsCoordinateMapper;

    @Resource
    private OpsInventoryMoveMapper opsInventoryMoveMapper;

    @Resource
    private OpsInventoryDao opsInventoryDao;

    @Resource
    private OpsInventoryMapper opsInventoryMapper;

    @Resource
    private OpsDoPostDao opsDoPostDao;

    @Resource
    private OpsExceptionHandleMapper opsExceptionHandleMapper;

    @Resource
    private WmCommonService wmCommonService;
    @Resource
    private ModifyCustomerOrderService modifyCustomerOrderService;
    @Autowired
    private BasePoService basePoService;
    @Autowired
    private BaseRoService baseRoService;

    @Resource
    private TblWorkDayYearDao tblWorkDayYearDao;

    @Resource
    private RcvdetailMapper rcvdetailMapper;

    @Resource
    private OpsOrderModiDataService opsOrderModiDataService;

    @Autowired
    private FinishPoService finishPoService;
    @Autowired
    private TransOrderService transOrderService;
    @Autowired
    private BaseCustomerOrderService baseCustomerOrderService;
    @Autowired
    private OpsDoStatusDao opsDoStatusDao;

    @Autowired
    private DoPcoLogicCenterService doPcoLogicCenterService;
    @Autowired
    private BaseWMOrderService baseWMOrderService;
    @Autowired
    private TransferEventPublisher transferEventPublisher;
    @Autowired
    private CustomerEventPublisher customerEventPublisher;


    @Override
    public void calculateDoReadyDate(String doId) throws OpsException {
        OpsDo opsDo = baseDoService.getDoByDoId(doId);
        if (opsDo.getDoReadyTime() == null) {
            //型号拆分
            if (DoSourceEnum.isAssModel(opsDo.getDoSource())) {
                OpsPco pco = opsPcoService.findPcoByOrder(opsDo.getOrderId(), opsDo.getOrderItem());
                List<OpsPcoItem> pcoItemList = opsPcoService.findPcoItemByPcoId(pco.getPcoId());
                boolean doReady = true;
                boolean doEntry = true;
                for (OpsPcoItem pcoItem : pcoItemList) {
                    boolean pcoItemReady = false;
                    //判断pcoItem是否全为N
                    List<OpsPcoItemInventory> pcoItemInvs = opsPcoService.getOpsPcoItemInventoryByPcoId(pcoItem.getPcoId(), pcoItem.getPcoItem());
                    int qty = 0;
                    for (OpsPcoItemInventory pcoItemInv : pcoItemInvs) {
                        if (InventoryTableTypeEnum.isNormal(pcoItemInv.getInventoryTableType())) {
                            Integer useQty = pcoItemInv.getUseQty();
                            qty += useQty;
                        }
                    }
                    //如果pcoItemList中有一项数量不够，则判断调拨是否货齐
                    if (pcoItem.getSubQty() - qty == 0) {
                        pcoItemReady = true;
                    }
                    if (!pcoItemReady) {
                        doReady = false;
                    }

                    //判断是否入库
                    boolean pcoItemEntry = false;
                    //如果没货齐，而且没有入库时间，则判断是否入库
                    if (opsDo.getDbReadyTime() == null && !pcoItemReady) {
                        //如果wait类型是DB，则找到调拨单的关联关系，如果关联关系全部为N，则在do上的入库时间记录为此时时间。
                        if (DoWaitTypeEnum.WaitDB.getType().equals(pcoItem.getWaitType())) {
                            OpsDo dbck = null;
                            List<OpsDo> dbckList = baseDoService.findDBCKListByOrder(pco.getOrderId(), pco.getOrderItem(), pcoItem.getPcoItem(), DoSourceEnum.ASSModelNo);
                            if (!dbckList.isEmpty()) {
                                dbckList.sort(Comparator.comparing(OpsDo::getExtNum).reversed());
                                dbck = dbckList.get(0);
                            }
                            if (dbck != null) {
                                int dbQty = 0;
                                OpsDoItem dbckItem = baseDoService.getDoItemByDoId(dbck.getDoId());
                                List<OpsDoItemInventory> dbckItemInventoryList = baseDoService.getDoItemInventoryByDoId(dbck.getDoId());
                                for (OpsDoItemInventory opsDoItemInventory : dbckItemInventoryList) {
                                    if (InventoryTableTypeEnum.isNormal(opsDoItemInventory.getInventoryTableType())) {
                                        Integer useQty = opsDoItemInventory.getUseQty();
                                        dbQty += useQty;
                                    }
                                }
                                //如果调拨货齐，则记录
                                if (dbckItem.getQty() - dbQty == 0) {
                                    pcoItemEntry = true;
                                }
                            }
                        }
                    } else {
                        pcoItemEntry = true;
                    }
                    //如果此item没入库齐则，do记录没有入库
                    if (!pcoItemEntry) {
                        doEntry = false;
                    }
                }
                //pcoItemList的数量全够了，则货齐时间填当前时间
                if (doReady && opsDo.getDoReadyTime() == null) {
                    opsDoStatusDao.updateDoReadyTime(opsDo.getDoId(), new Date());
                }
                //pcoItemList的
                if (doEntry && opsDo.getDbReadyTime() == null) {
                    opsDoStatusDao.updateDoEntryTime(opsDo.getDoId(), new Date());
                }
            }
            //数量拆分
            else {
                OpsDoItem doItem = baseDoService.getDoItemByDoId(opsDo.getDoId());
                List<OpsDoItemInventory> doItemInventoryList = baseDoService.getDoItemInventoryByDoId(opsDo.getDoId());
                int qty = 0;
                for (OpsDoItemInventory opsDoItemInventory : doItemInventoryList) {
                    if (InventoryTableTypeEnum.isNormal(opsDoItemInventory.getInventoryTableType())) {
                        Integer useQty = opsDoItemInventory.getUseQty();
                        qty += useQty;
                    }
                }
                //如果指令数量和货齐数量一致，则货齐时间填当前时间
                if (doItem.getQty() == qty) {
                    opsDoStatusDao.updateDoReadyTime(opsDo.getDoId(), new Date());
                    if (opsDo.getDbReadyTime() == null){
                        opsDoStatusDao.updateDoEntryTime(opsDo.getDoId(), new Date());
                    }
                }
                //如果不一致，则判断调拨是否货齐
                else {
                    opsDoStatusDao.updateDoReadyTime(opsDo.getDoId(), null);
                    //如果wait类型是DB，则找到调拨单的关联关系，如果关联关系全部为N，则在do上的入库时间记录为此时时间。
                    if (DoWaitTypeEnum.WaitDB.getType().equals(opsDo.getWaitType()) && opsDo.getDbReadyTime() == null) {
                        OpsDo dbck = null;
                        List<OpsDo> dbckList = baseDoService.findDBCKListByOrder(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum(), DoSourceEnum.ASSQTY);
                        if (!dbckList.isEmpty()) {
                            dbckList.sort(Comparator.comparing(OpsDo::getExtNum).reversed());
                            dbck = dbckList.get(0);
                        }
                        if (dbck != null) {
                            int dbQty = 0;
                            OpsDoItem dbckItem = baseDoService.getDoItemByDoId(dbck.getDoId());
                            List<OpsDoItemInventory> dbckItemInventoryList = baseDoService.getDoItemInventoryByDoId(dbck.getDoId());
                            for (OpsDoItemInventory opsDoItemInventory : dbckItemInventoryList) {
                                if (InventoryTableTypeEnum.isNormal(opsDoItemInventory.getInventoryTableType())) {
                                    Integer useQty = opsDoItemInventory.getUseQty();
                                    dbQty += useQty;
                                }
                            }
                            if (dbckItem.getQty() == dbQty) {
                                opsDoStatusDao.updateDoEntryTime(opsDo.getDoId(), new Date());
                            }
                        }
                    }

                }
            }
        }
    }

    /**
     * 判断一条do的关联关系是否都是N并且数量齐
     * @param dbDoId
     * @throws OpsException
     */
    public boolean isDoQtyEnough(String dbDoId) throws OpsException {
        int dbQty = 0;
        OpsDoItem dbckItem = baseDoService.getDoItemByDoId(dbDoId);
        List<OpsDoItemInventory> dbckItemInventoryList = baseDoService.getDoItemInventoryByDoId(dbDoId);
        for (OpsDoItemInventory opsDoItemInventory : dbckItemInventoryList) {
            if (InventoryTableTypeEnum.isNormal(opsDoItemInventory.getInventoryTableType())) {
                Integer useQty = opsDoItemInventory.getUseQty();
                dbQty += useQty;
            }
        }
        //只要一个调拨单不货齐，则do记录没有货齐
        if (dbckItem.getQty() == dbQty) {
            return true;
        }
        return false;
    }
    public boolean isPcoItemQtyEnough(OpsPcoItem pcoItem){
        List<OpsPcoItemInventory> pcoItemInvs = opsPcoService.getOpsPcoItemInventoryByPcoId(pcoItem.getPcoId(), pcoItem.getPcoItem());
        int qty = 0;
        for (OpsPcoItemInventory pcoItemInv : pcoItemInvs) {
            if (InventoryTableTypeEnum.isNormal(pcoItemInv.getInventoryTableType())) {
                Integer useQty = pcoItemInv.getUseQty();
                qty += useQty;
            }
        }
        if (pcoItem.getSubQty() - qty == 0) {
            return true;
        }
        return false;
    }



    @Override
    public void updateDOisWMSbyID(Long id) {
        OpsDo up = new OpsDo();
        up.setId(id);
        up.setIsWms(1);// 更新成功
        up.setModifyTime(DateUtil.getNow());
        up.setModifier("wms");
        //bugid 13785 下发记录下发时间 20230325 下发时间
        up.setWmsTime(DateUtil.getNow());
        opsDoMapper.updateByPrimaryKeySelective(up);
    }


    /**
     * 分页条件查询出库指令
     * 前端接口
     * @auther C12961
     * @date 2021/9/23 16:30
     */
    @Override
    public PageInfo<OpsDo> searchDoByPage(PageModel<OpsDeliveryOrderQO> pageModel) throws OpsException {
        OpsDeliveryOrderQO condition = pageModel.getCondition();
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0);
        if (!StringUtils.isEmpty(condition.getCustomerNo())) {
            criteria.andCustomerNoEqualTo(condition.getCustomerNo());
        }
        if (!StringUtils.isEmpty(condition.getWarehouseCode())) {
            criteria.andWarehouseCodeEqualTo(condition.getWarehouseCode());
        }
        if (!StringUtils.isEmpty(condition.getDoId())) {
            criteria.andDoIdEqualTo(condition.getDoId());
        }
        if (!StringUtils.isEmpty(condition.getOrderId())) {
            criteria.andOrderIdEqualTo(condition.getOrderId());
        }
        if (!StringUtils.isEmpty(condition.getDoType())) {
            criteria.andDoTypeEqualTo(condition.getDoType());
        }
        if (condition.getDoStatus() != null) {
            criteria.andDoStatusEqualTo(condition.getDoStatus());
        }
        if (condition.getBeginTime() != null) {
            criteria.andCreTimeGreaterThanOrEqualTo(condition.getBeginTime());
        }
        if (condition.getEndTime() != null) {
            criteria.andCreTimeLessThanOrEqualTo(condition.getEndTime());
        }
        PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize());
        return new PageInfo(opsDoMapper.selectByExample(example));
    }


    /**
     * @description： 调度生成物流出库
     * @author ：c02483
     * @date ：Created in 2021/10/14 11:04
     */
    @Override
    public List<OpsDo> CreateDoForDispatch(List<CreDoByInventoryDto> doList, UserDto userDto,Map<Long, Boolean> riskMap) throws OpsException {
        List<OpsDo> list = new ArrayList<>();
        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
        for (CreDoByInventoryDto dto : doList) {
            if (DoWaitTypeEnum.OK.getType().equals(dto.getOpsDo().getWaitType())) {
                if (!CollectionUtils.isEmpty(dto.getItemInventorys())) {
                    dto.getOpsDo().setDoStatus(DoStatusEnum.WAIT.getStatus());
                    updatePreQtyForDo(dto.getOpsDo(), dto.getItemInventorys(), userDto,riskMap);
                } else {
                    dto.getOpsDo().setDoStatus(DoStatusEnum.INIT.getStatus());
                }
            }
            //通知发货调拨单物流指定发货日为今天
            if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(dto.getOpsDo().getDlvEntire())){
                if(DoTypeEnum.DBCK.getType().equals(dto.getOpsDo().getDoType())){
                    dto.getOpsDo().setWlDate(DateUtil.getNow);
                }
            }
            OpsDo opsDo = insertDo(dto.getOpsDo(), dto.getOpsDoItem(), dto.getItemInventorys(), userDto);
            OpsWmOrderTask wmTaskdo = wmOrderTaskService.createWmTaskPo(opsDo.getDoId()  , WmOrderTaskEnum.DO,
                    WmOrderTaskEnum.ORDER,SendStatusEnum.SUSPENDED,new CreateInfoDto(userDto.getUserName()), null,0,"");
            //越库直接下发DO BUGID:9965 20230331 随发分批 越库直接下发
            if(Objects.nonNull(dto.getOpsDo().getRoCrossType()) && dto.getOpsDo().getRoCrossType() == 2){
                wmTaskdo.setFlag(Integer.valueOf(SendStatusEnum.INIT.getType()));
            }else {
                wmTaskdo.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.getType()));
            }
            //通知发货调拨单才创建task
            if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(dto.getOpsDo().getDlvEntire())){
                if(DoTypeEnum.DBCK.getType().equals(dto.getOpsDo().getDoType())){
                    taskList.add(wmTaskdo);
                }
            }else {
                taskList.add(wmTaskdo);
            }
            list.add(opsDo);
        }
        //批量更新task
        wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
        return list;
    }


    /**
     * 生成调账出库指令
     * @auther C12961
     * @date 2021/10/20 17:38
     */
    @Override
    public List<OpsDo> createDoForAdjust(List<InventoryForAdjustDto> dtoList, UserDto userDto) throws OpsException {
        List<OpsDo> doList = new ArrayList<>();
        for (InventoryForAdjustDto adjustDto : dtoList) {
            OpsInventory inv = new OpsInventory();
            inv.setWarehouseCode(adjustDto.getWarehouseCode());
            inv.setModelno(adjustDto.getModelno());
            OpsInventoryProperty property = new OpsInventoryProperty();
            property.setCustomerNo(adjustDto.getCustomerNo());
            property.setPpl(adjustDto.getPpl());
            property.setProjectCode(adjustDto.getProjectCode());
            property.setGroupCustomerNo(adjustDto.getGroupCustomerNo());
            List<OpsInventory> invList = baseInventoryService.findOpsInventory(inv, property);
            invList.sort(Comparator.comparing(OpsInventory::getQuantity));
            Integer quantity = adjustDto.getQty();
            Map<Long, Integer> invqtyMap = new HashMap<>();
            for (OpsInventory opsInventory : invList) {
                int hasqty = opsInventory.getQuantity() - opsInventory.getPrepareQuantity();
                if (hasqty > 0) {
                    if (hasqty < quantity) {
                        invqtyMap.put(opsInventory.getInventoryId(), hasqty);
                        quantity -= hasqty;
                    } else {
                        invqtyMap.put(opsInventory.getInventoryId(), quantity);
                        quantity = 0;
                        break;
                    }
                }
            }
            if (quantity > 0) {
                throw Exceptions.OpsException("可用数量不足");
            }

            OpsDo opsDo = new OpsDo();
            OpsDoItem opsDoItem = new OpsDoItem();
            List<OpsDoItemInventory> doItemInventoryList = new ArrayList<>();
            opsDo.setOrderId(adjustDto.getOrderId());
            opsDo.setOrderItem(String.valueOf(doList.size() + 1));
            opsDo.setDoId(adjustDto.getOrderId() + opsDo.getOrderItem());
            opsDo.setNum(0);
            opsDo.setAssNum(0);
            opsDo.setDoSource(DoSourceEnum.ALL.getType());
            opsDo.setDoType(DoTypeEnum.TZCK.getType());
            opsDo.setWarehouseCode(adjustDto.getWarehouseCode());
            opsDo.setGatherWarehouseCode(adjustDto.getWarehouseCode());

            opsDo.setCustomerNo(adjustDto.getCustomerNo());
            opsDo.setDoStatus(DoStatusEnum.FINISH.getStatus());
            opsDo.setWaitType(DoWaitTypeEnum.OK.getType());
            opsDoItem.setDoId(opsDo.getDoId());
            opsDoItem.setDoItem(1);
            opsDoItem.setModelno(adjustDto.getModelno());
            opsDoItem.setQty(adjustDto.getQty());
            opsDoItem.setOutQty(adjustDto.getQty());
            for (Long invId : invqtyMap.keySet()) {
                OpsDoItemInventory itemInventory = new OpsDoItemInventory();
                itemInventory.setDoId(opsDo.getDoId());
                itemInventory.setDoItem(opsDoItem.getDoItem());
                itemInventory.setInventoryId(invId);
                itemInventory.setInventoryTableType(InventoryTableTypeEnum.NORMAL.getType());
                itemInventory.setUseQty(invqtyMap.get(invId));
                itemInventory.setOutQty(invqtyMap.get(invId));
                doItemInventoryList.add(itemInventory);
            }
            //IO操作
            //插入调库指令
            insertDo(opsDo, opsDoItem, doItemInventoryList, userDto);

            //扣减库存数量
            updateQtyForDo(opsDo, doItemInventoryList, userDto);
            doList.add(opsDo);
        }
        return doList;
    }


    /**
     * WMS 生成调账出库指令
     * @auther C12961
     * @date 2021/10/20 17:38
     */
    @Override
    public List<OpsDo> createDoForWMSAdjust(List<InventoryForAdjustDto> dtoList, UserDto userDto) throws OpsException {
        List<OpsDo> doList = new ArrayList<>();
        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
        for (InventoryForAdjustDto adjustDto : dtoList) {
            OpsDo opsDo = new OpsDo();
            OpsDoItem opsDoItem = new OpsDoItem();
            List<OpsDoItemInventory> doItemInventoryList = new ArrayList<>();
            opsDo.setOrderId(adjustDto.getOrderId());
            opsDo.setOrderItem(String.valueOf(doList.size() + 1));
            String doId = String.format(OrderIDFormatEnum.DO_FORMAT.getFormat(), opsDo.getOrderId(),
                    String.format("%03d", doList.size() + 1),
                    String.format("%03d", 0), String.format("%03d", 0));
            opsDo.setDoId(doId);
            opsDo.setNum(0);
            opsDo.setAssNum(0);
            opsDo.setDoSource(DoSourceEnum.ALL.getType());
            opsDo.setDoType(DoTypeEnum.PKCK.getType());//todo 更改出库类型
            opsDo.setWarehouseCode(adjustDto.getWarehouseCode());
            opsDo.setGatherWarehouseCode(adjustDto.getWarehouseCode());

            opsDo.setCustomerNo(adjustDto.getCustomerNo());
            opsDo.setDoStatus(DoStatusEnum.FINISH.getStatus());
            opsDo.setWaitType(DoWaitTypeEnum.OK.getType());
            opsDoItem.setDoId(opsDo.getDoId());
            opsDoItem.setDoItem(1);
            opsDoItem.setModelno(adjustDto.getModelno());
            opsDoItem.setQty(adjustDto.getQty());
            opsDoItem.setOutQty(0);
            //IO操作
            //插入调库指令
            insertDo(opsDo, opsDoItem, doItemInventoryList, userDto);
            OpsWmOrderTask opsWmOrderTask = new OpsWmOrderTask();
            opsWmOrderTask.setWmOrderId(opsDo.getDoId());
            opsWmOrderTask.setWmOrderType(WmOrderTaskEnum.DO.getType());
            opsWmOrderTask.setTaskType(WmOrderTaskEnum.ORDER.getType());
            opsWmOrderTask.setFlag(Integer.valueOf(SendStatusEnum.INIT.getType()));
            opsWmOrderTask.setCreator(userDto.getUserName());
            opsWmOrderTask.setCreTime(new Date());
            taskList.add(opsWmOrderTask);
            //wmOrderTaskService.addOpsWmOrderTask(opsWmOrderTask);
            doList.add(opsDo);
        }
        //批量更新task
        wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
        return doList;
    }

    /**
     * 生成调库出库指令
     * @auther C12961
     * @date 2021/10/19 15:27
     */
    @Override
    public List<OpsDoDto> createDoForTrans(InventoryForTransInputDto inputDto) throws OpsException {
        List<OpsDoDto> doList = new ArrayList<>();
        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
        for (InventoryForTransDto transDto : inputDto.getDtoList()) {
            // 1.校验物流指令是否存在
            List<OpsDo> doS = baseDoService.findDoListByOrder(transDto.getOrderId(), transDto.getItemNo().toString(), null, null);
            if (!CollectionUtils.isEmpty(doS)) {
                throw Exceptions.OpsException("调拨出库指令已存在");
            }
            // 2.查询并分配可用库存
            Map<OpsInventory, Integer> invqtyMap = opsInventoryService.findInventoryListByTransOrderInfo(transDto);
            // 3.准备前置数据
            // (1)查询仓库和地址信息
            OpsWarehouse opsWarehouse  = opsWarehouseService.findById(transDto.getTwarehouseCode());
            if (Objects.isNull(opsWarehouse)) {
                throw Exceptions.OpsException("不存在此仓库号：" + transDto.getTwarehouseCode());
            }
            //bugid:11445 c14717 2023/07/26 仓库多地址
            opsWarehouseService.getMultAdressSetAddress(opsWarehouse,DoTypeEnum.TKCK.getType());
            // (2)计算指定物流交货期
            Date wlDate = transDto.getSwarehouseCode().equals(transDto.getTwarehouseCode()) ? transDto.getWlDdate() :
                    tkckWlDay(transDto.getWlDdate(), transDto.getSwarehouseCode(), transDto.getTwarehouseCode(), transDto.getOrderId() + "-" + transDto.getItemNo());
            inputDto.setWmsDlvDate(wlDate);
            Date HopeDate = Objects.isNull(transDto.getWlDdate()) ? DateUtil.getNow() : transDto.getWlDdate();

            // 4.创建物流指令
            OpsDoDto opsDoDto = DoBuilder.createTKCK(transDto.getOrderId(), transDto.getItemNo())
                    .warehouseInfo(transDto.getSwarehouseCode(), transDto.getTwarehouseCode())
                    .addressInfo(opsWarehouse)
                    .addInventoryInfo(invqtyMap)
                    .statusInfo(DoStatusEnum.INIT,DoWaitTypeEnum.WaitDB,0)
                    .build();
            opsDoDto.getOpsDo().setWlDate(wlDate);
            opsDoDto.getOpsDo().setHopeDate(HopeDate);//增加指定物流交货期
            //服务备库生成do指令时需要写入PO号和物料号
            if (!StringUtils.isEmpty(transDto.getCorderNo())) {
                opsDoDto.getOpsDo().setCorderNo(transDto.getCorderNo());
            }
            if (!StringUtils.isEmpty(transDto.getCproductNo())) {
                opsDoDto.getDoItem().setCproductNo(transDto.getCproductNo());
            }
            // 判断是否同仓调拨
            boolean sameWarehouse = StrUtil.equalsIgnoreCase(transDto.getSwarehouseCode(), transDto.getTwarehouseCode());
            if (!sameWarehouse) {
                // 异仓调拨
                // 1.修改Do状态为初始化
                //bug16290 特殊型号不可异仓调库
                Boolean speModelNo = baseDoService.checkSpeModelNoSpeWarehouse(transDto.getTmodelno());
                if (speModelNo) {
                    throw Exceptions.OpsException("特殊型号不可调库");
                }
                opsDoDto.getOpsDo().setDoStatus(DoStatusEnum.INIT.getStatus());
                opsDoDto.getOpsDo().setWaitType(DoWaitTypeEnum.OK.getType());
                opsDoDto.getDoItem().setOutQty(0);
                // 2.插入指令
                insertDo(opsDoDto.getOpsDo(), opsDoDto.getDoItem(), opsDoDto.getDoItemInventoryList(), inputDto.getUserDto());
                // 3.修改库存预约数
                updatePreQtyForDo(opsDoDto.getOpsDo(), opsDoDto.getDoItemInventoryList(), inputDto.getUserDto(),null);
                // 4.创建task
                OpsWmOrderTask opsWmOrderTask = new OpsWmOrderTask();
                opsWmOrderTask.setWmOrderId(opsDoDto.getOpsDo().getDoId());
                opsWmOrderTask.setWmOrderType(WmOrderTaskEnum.DO.getType());
                opsWmOrderTask.setTaskType(WmOrderTaskEnum.ORDER.getType());
                opsWmOrderTask.setFlag(Integer.valueOf(SendStatusEnum.INIT.getType()));
                opsWmOrderTask.setCreator("");
                opsWmOrderTask.setCreTime(new Date());
                taskList.add(opsWmOrderTask);
                doList.add(opsDoDto);
            } else {
                // 1.修改Do状态为已完成
                opsDoDto.getOpsDo().setDoStatus(DoStatusEnum.FINISH.getStatus());
                opsDoDto.getOpsDo().setWaitType(DoWaitTypeEnum.OK.getType());
                opsDoDto.getDoItem().setOutQty(opsDoDto.getDoItem().getQty());
                for (OpsDoItemInventory itemInventory : opsDoDto.getDoItemInventoryList()) {
                    itemInventory.setOutQty(itemInventory.getUseQty());
                }
                // 2.插入指令
                insertDo(opsDoDto.getOpsDo(), opsDoDto.getDoItem(), opsDoDto.getDoItemInventoryList(), inputDto.getUserDto());
                // 3.修改库存数量
                updateQtyForDo(opsDoDto.getOpsDo(), opsDoDto.getDoItemInventoryList(), inputDto.getUserDto());
                doList.add(opsDoDto);
            }
        }
        // 5.批量更新task
        wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
        return doList;
    }


    @Override
    public void createDoForTransMove(TransOrderDtoForMove dto, OpsInventoryMove move) throws OpsException {
        List<OpsDo> opsDoList = baseDoService.findDoListByOrder(dto.getTransNo(), dto.getItemNo().toString(), null, null);
        if (!CollectionUtils.isEmpty(opsDoList)) {
            throw Exceptions.OpsException("调拨出库指令已存在");
        }
        // 仓库号和地址
        OpsWarehouse opsWarehouse = opsWarehouseService.findById(dto.getToWarehouseCode());
        if (Objects.isNull(opsWarehouse)) {
            throw Exceptions.OpsException("不存在此仓库号：" + dto.getToWarehouseCode());
        }
        opsWarehouseService.getMultAdressSetAddress(opsWarehouse,DoTypeEnum.TKCK.getType());
        // 创建物流指令
        OpsDoDto opsDoDto = DoBuilder.createTKCK(dto.getTransNo(), dto.getItemNo())
                .warehouseInfo(move.getWarehouseCode(),dto.getToWarehouseCode())
                .addressInfo(opsWarehouse)
                .addInventoryInfo(move, dto.getHopeQty())
                .statusInfo(DoStatusEnum.INIT)
                .build();
        UserDto userDto = new UserDto("transOrder");
        //服务备库生成do指令时需要写入PO号和物料号
        if (!StringUtils.isEmpty(dto.getCorderNo())) {
            opsDoDto.getOpsDo().setCorderNo(dto.getCorderNo());
        }
        if (!StringUtils.isEmpty(dto.getCproductNo())) {
            opsDoDto.getDoItem().setCproductNo(dto.getCproductNo());
        }
        // 插入调库指令
        insertDo(opsDoDto.getOpsDo(), opsDoDto.getDoItem(), opsDoDto.getDoItemInventoryList(), userDto);
        // 预占库存数量
        updatePreQtyForDo(opsDoDto.getOpsDo(), opsDoDto.getDoItemInventoryList(), userDto,null);
    }

    // 替代方案，用来给预到货时采购分纳的调库单拆分指令，目前调用的是刘工的通用拆分方法
    public String splitFNDoForTransMove(OpsDo oldTKCK,int qty) throws OpsException {
        OpsDo opsDoMaxOne = baseDoService.findByOrderOrderByNumMaxOne(oldTKCK.getOrderId(), oldTKCK.getOrderItem(), DoTypeEnum.TKCK);
        int newNum = opsDoMaxOne.getNum() + 1;
        // 1.创建新Do
        OpsDo newTKCK = new OpsDo();
        BeanUtils.copyProperties(oldTKCK,newTKCK);
        newTKCK.setId(null);
        // 生成新doId
        String newTKCKId = String.format(OrderIDFormatEnum.DBC_FORMAT.getFormat(),
                newTKCK.getOrderId(), String.format("%04d", Integer.valueOf(newTKCK.getOrderItem())), String.format("%03d", newNum), String.format("%03d", newTKCK.getAssNum()));
        newTKCK.setDoId(newTKCKId);
        newTKCK.setDoSource(DoSourceEnum.ASSQTY.getType());
        newTKCK.setNum(newNum);
        // 2.创建新DoItem
        OpsDoItem oldDoItem = baseDoService.getDoItemByDoId(oldTKCK.getDoId());
        OpsDoItem newDoItem = new OpsDoItem();
        BeanUtils.copyProperties(oldDoItem,newDoItem);
        newDoItem.setId(null);
        newDoItem.setDoId(newTKCKId);
        newDoItem.setQty(qty);
        newDoItem.setOutQty(0);
        UserDto userDto = new UserDto("Po分纳拆分调库出库");
        insertDo(newTKCK, newDoItem, null, userDto);
        // 3.修改旧Do
        OpsDo updateDo = new OpsDo();
        updateDo.setId(oldTKCK.getId());
        updateDo.setDoSource(DoSourceEnum.ASSQTY.getType());
        updateDo.setModifyTime(new Date());
        updateDo.setModifier(userDto.getUserName());
        updateDoByDo(updateDo);
        // 4.修改旧DoItem
        updateDoItemQtyByDoItemId(oldDoItem.getId(), oldDoItem.getQty() - qty, oldDoItem.getVersion());
        return newTKCKId;
    }



    /**
     * 生成组换出库指令
     * @auther C12961
     * @date 2021/10/19 15:27
     */
    @Override
    public List<OpsDo> createDoForProduceChange(List<InventoryForAdjustDto> ckList, String warehouseCode, UserDto userDto, Boolean onlyWms) throws OpsException {
        List<OpsDo> doList = new ArrayList<>();
        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();

        for (InventoryForAdjustDto adjustDto : ckList) {
            Map<Long, Integer> invqtyMap = new HashMap<>();
            if(!onlyWms){
                OpsInventory inv = new OpsInventory();
                inv.setWarehouseCode(adjustDto.getWarehouseCode());
                inv.setModelno(adjustDto.getModelno());
                OpsInventoryProperty property = new OpsInventoryProperty();
                property.setCustomerNo(adjustDto.getCustomerNo());
                property.setPpl(adjustDto.getPpl());
                property.setProjectCode(adjustDto.getProjectCode());
                property.setGroupCustomerNo(adjustDto.getGroupCustomerNo());
                List<OpsInventory> invList = baseInventoryService.findOpsInventory(inv, property);
                invList.sort(Comparator.comparing(OpsInventory::getQuantity));
                Integer quantity = adjustDto.getQty();
                for (OpsInventory opsInventory : invList) {
                    int hasqty = opsInventory.getQuantity() - opsInventory.getPrepareQuantity();
                    if (hasqty > 0) {
                        if (hasqty < quantity) {
                            invqtyMap.put(opsInventory.getInventoryId(), hasqty);
                            quantity -= hasqty;
                        } else {
                            invqtyMap.put(opsInventory.getInventoryId(), quantity);
                            quantity = 0;
                            break;
                        }
                    }
                }
                if (quantity > 0) {
                    throw Exceptions.OpsException("可用数量不足");
                }
            }
            OpsDo opsDo = new OpsDo();
            OpsDoItem opsDoItem = new OpsDoItem();
            List<OpsDoItemInventory> doItemInventoryList = new ArrayList<>();
            opsDo.setOrderId(adjustDto.getOrderId());
            opsDo.setOrderItem(String.valueOf(doList.size() + 1));
            String doId = String.format(OrderIDFormatEnum.DO_FORMAT.getFormat(), adjustDto.getOrderId(), String.format("%03d", Integer.parseInt(opsDo.getOrderItem())),
                    String.format("%03d", 0), String.format("%03d", 0));
            opsDo.setDoId(doId);
            opsDo.setNum(1);
            opsDo.setAssNum(0);
            opsDo.setDoType(DoTypeEnum.ZHCK.getType());
            if(onlyWms){
                opsDo.setDoType(DoTypeEnum.ZHCKOW.getType());
            }
            OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(adjustDto.getWarehouseCode());
            if (Objects.isNull(opsWarehouse)) {
                throw Exceptions.OpsException("不存在此仓库号：" + adjustDto.getWarehouseCode());
            }
            //bugid:11445 c14717 2023/07/26 仓库多地址
            opsWarehouseService.getMultAdressSetAddress(opsWarehouse,opsDo.getDoType());
            opsDo.setProvince(opsWarehouse.getProvince());
            opsDo.setCity(opsWarehouse.getCity());
            opsDo.setDistrict(opsWarehouse.getDistrict());
            opsDo.setStreet(opsWarehouse.getAdress());
            opsDo.setAddress(opsWarehouse.getAdress());
            opsDo.setLinkman(opsWarehouse.getLinkman());
            //组换备注
            if (!StringUtils.isEmpty(adjustDto.getRemark())) {
                if (adjustDto.getRemark().length() > 100) {
                    opsDo.setRemark(adjustDto.getRemark().substring(0, 99));//组换单传备注信息
                } else {
                    opsDo.setRemark(adjustDto.getRemark());//组换单传备注信息
                }
            }
            if (!StringUtils.isEmpty(opsWarehouse.getLinkMobile())) {//手机号
                if (StringPhoneUtil.isMobil(opsWarehouse.getLinkMobile())) {
                    opsDo.setMobile(opsWarehouse.getLinkMobile());
                }
            }
            if (!StringUtils.isEmpty(opsWarehouse.getLinkPhone())) {
                //是电话 且不是手机号 存电话
                if (StringPhoneUtil.isPhone(opsWarehouse.getLinkPhone()) && !StringPhoneUtil.isMobil(opsWarehouse.getLinkPhone())) {
                    opsDo.setPhone(opsWarehouse.getLinkPhone());
                }
            }
            opsDo.setPostcode(opsWarehouse.getPostCode() + "");
            opsDo.setDoSource(DoSourceEnum.ALL.getType());
            opsDo.setWarehouseCode(adjustDto.getWarehouseCode());
            opsDo.setGatherWarehouseCode(adjustDto.getWarehouseCode());
            opsDo.setCustomerNo(adjustDto.getCustomerNo());
            opsDo.setDoStatus(DoStatusEnum.INIT.getStatus());
            opsDo.setWaitType(DoWaitTypeEnum.WaitJG.getType());
            opsDo.setIsWms(0);
            opsDoItem.setDoId(opsDo.getDoId());
            opsDoItem.setDoItem(1);
            opsDoItem.setModelno(adjustDto.getModelno());
            opsDoItem.setQty(adjustDto.getQty());
            opsDoItem.setOutQty(0);
            for (Long invId : invqtyMap.keySet()) {
                OpsDoItemInventory itemInventory = new OpsDoItemInventory();
                itemInventory.setDoId(opsDo.getDoId());
                itemInventory.setDoItem(opsDoItem.getDoItem());
                itemInventory.setInventoryId(invId);
                itemInventory.setInventoryTableType(InventoryTableTypeEnum.NORMAL.getType());
                itemInventory.setUseQty(invqtyMap.get(invId));
                itemInventory.setOutQty(0);
                doItemInventoryList.add(itemInventory);
            }
            //IO操作
            //插入调库指令
            insertDo(opsDo, opsDoItem, doItemInventoryList, userDto);
            updatePreQtyForDo(opsDo, doItemInventoryList, userDto,null);//占用库存
            OpsWmOrderTask opsWmOrderTask = new OpsWmOrderTask();
            opsWmOrderTask.setWmOrderId(opsDo.getDoId());
            opsWmOrderTask.setWmOrderType(WmOrderTaskEnum.DO.getType());
            opsWmOrderTask.setTaskType(WmOrderTaskEnum.ORDER.getType());
            opsWmOrderTask.setFlag(Integer.valueOf(SendStatusEnum.INIT.getType()));
            opsWmOrderTask.setCreator("");
            opsWmOrderTask.setCreTime(new Date());
            taskList.add(opsWmOrderTask);
            doList.add(opsDo);
        }
        //批量更新task
        wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
        return doList;
    }


    public OpsDoDto CreateDoAndFinishForSalesInfo(InventoryForAdjustDto adjustDto, String orderType, DoSourceEnum doSourceEnum, DoTypeEnum doTypeEnum) throws OpsException {
        OpsDo opsDo = new OpsDo();
        opsDo.setDoId("QBC" + adjustDto.getOrderId());
        opsDo.setOrderId(adjustDto.getOrderId());
        opsDo.setOrderItem("1");
        opsDo.setNum(1);
        opsDo.setAssNum(0);

        opsDo.setDoSource(doSourceEnum.getType());
        opsDo.setDoType(doTypeEnum.getType());
        opsDo.setDoStatus(DoStatusEnum.FINISH.getStatus());
        opsDo.setWarehouseCode(adjustDto.getWarehouseCode());
        opsDo.setCustomerNo(adjustDto.getCustomerNo());

        OpsDoItem opsDoItem = new OpsDoItem();
        opsDoItem.setDoId(opsDo.getDoId());
        opsDoItem.setDoItem(1);
        opsDoItem.setModelno(adjustDto.getModelno());
        opsDoItem.setQty(adjustDto.getQty());
        opsDoItem.setOutQty(adjustDto.getQty());

        OpsInventory inv = new OpsInventory();
        inv.setWarehouseCode(adjustDto.getWarehouseCode());
        inv.setModelno(adjustDto.getModelno());
        inv.setInventoryPropertyId(1L);

        List<OpsDoItemInventory> doItemInventoryList = new ArrayList<>();
        List<OpsInventory> invList = baseInventoryService.findOpsInventory(inv);
        if (invList.size() > 0) {
            inv = invList.stream().max(Comparator.comparing(item -> item.getQuantity() - item.getPrepareQuantity())).get();
            int availableQty = inv.getQuantity() - inv.getPrepareQuantity();
            if (availableQty >= adjustDto.getQty()) {
                OpsDoItemInventory opsDoItemInventory = new OpsDoItemInventory();
                opsDoItemInventory.setDoId(opsDoItem.getDoId());
                opsDoItemInventory.setDoItem(opsDoItem.getDoItem());
                opsDoItemInventory.setInventoryId(inv.getInventoryId());
                opsDoItemInventory.setInventoryTableType(InventoryTableTypeEnum.NORMAL.getType());
                opsDoItemInventory.setUseQty(adjustDto.getQty());
                opsDoItemInventory.setOutQty(adjustDto.getQty());
                opsDoItemInventory.setDelflag(0);
                opsDoItemInventory.setCreTime(new Date());
                opsDoItemInventory.setCreator(UserDto.ADMIN.getUserName());
                doItemInventoryList.add(opsDoItemInventory);
            } else {
                throw Exceptions.OpsException("在库不足，不允许预约");
            }
        } else {
            throw Exceptions.OpsException("在库不足，不允许预约");
        }
        insertDo(opsDo, opsDoItem, doItemInventoryList, UserDto.ADMIN);

        updateQtyForDo(opsDo, doItemInventoryList, UserDto.ADMIN);
        OpsDoDto doDto = new OpsDoDto(opsDo, opsDoItem, doItemInventoryList);
        return doDto;
    }

    @Override
    public OpsDoDto createDoForCancelSalesInfo(InventoryForAdjustDto adjustDto) throws OpsException {
        //出库指令
        OpsDo opsDo = new OpsDo();
        opsDo.setOrderId(adjustDto.getOrderId());
        opsDo.setOrderItem(adjustDto.getOrderItem().toString());
        opsDo.setDoId("QBQX" + adjustDto.getOrderId() + "-" + adjustDto.getOrderItem().toString());
        opsDo.setNum(1);
        opsDo.setAssNum(0);
        opsDo.setDoSource(DoSourceEnum.SALESINFOUNDO.getType());
        opsDo.setDoType(DoTypeEnum.QBQX.getType());
        opsDo.setDoStatus(DoStatusEnum.FINISH.getStatus());
        opsDo.setWarehouseCode(adjustDto.getWarehouseCode());
        opsDo.setRemark("情报占用到期取消");
        //出库指令项
        OpsDoItem opsDoItem = new OpsDoItem();
        opsDoItem.setDoId(opsDo.getDoId());
        opsDoItem.setDoItem(1);
        opsDoItem.setModelno(adjustDto.getModelno());
        opsDoItem.setQty(adjustDto.getQty());
        opsDoItem.setOutQty(adjustDto.getQty());
        // 出库指令-库存关联表
        OpsDoItemInventory opsDoItemInventory = new OpsDoItemInventory();
        opsDoItemInventory.setDoId(opsDoItem.getDoId());
        opsDoItemInventory.setDoItem(opsDoItem.getDoItem());
        opsDoItemInventory.setInventoryId(adjustDto.getInventoryId());
        opsDoItemInventory.setInventoryTableType(InventoryTableTypeEnum.NORMAL.getType());
        opsDoItemInventory.setUseQty(opsDoItem.getQty());
        opsDoItemInventory.setOutQty(opsDoItem.getQty());
        //插入指令表
        insertDo(opsDo, opsDoItem, Collections.singletonList(opsDoItemInventory), UserDto.AUTO);
        OpsInventory inventory = baseInventoryService.getInventoryById(adjustDto.getInventoryId());
        //组装返回对象
        InventoryCkByOrderOutDto inventoryCkByOrderOutDto = new InventoryCkByOrderOutDto();
        inventoryCkByOrderOutDto.getInventoryMap().put(inventory, opsDoItemInventory.getOutQty());
        OpsDoDto doDto = new OpsDoDto(opsDo, opsDoItem, Collections.singletonList(opsDoItemInventory), inventoryCkByOrderOutDto);
        return doDto;
    }

    /**
     * 获取完纳页面采购列表
     * @param doList
     * @param poPageList
     */
    @Override
    public void getFinishPo(List<FinishOrderWmsReqDto> doList, List<FinishPoListForDto> poPageList,FinishPageForOrderDto pageObj) throws OpsException {
        List<FinishPoDto> poNoList = new ArrayList<FinishPoDto>();
        //采购单号去重 存在一个采购单多个move的情况
        HashMap<String,Object> repectCheck = new HashMap<>();
        for(FinishOrderWmsReqDto obj: doList){
            List<OpsDoItemInventory> itemInvList = baseDoService.getDoItemInventoryByDoId(obj.getDocNo(),InventoryTableTypeEnum.TRANS);
            //有关联关系
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(itemInvList)){
                for(OpsDoItemInventory itemInv : itemInvList){
                    //在途关联关系
                    if(InventoryTableTypeEnum.TRANS.getType().equals(itemInv.getInventoryTableType())){
                        OpsInventoryMove invMove = baseInventoryService.getInventoryMoveById(itemInv.getInventoryId());
                        StringBuilder orderKey = new StringBuilder();
                        orderKey.append(invMove.getAssociateNo()).append("-").append(invMove.getAssociateNoItem()).append("-").append(invMove.getAssociateNoSplitno());
                        if(!repectCheck.containsKey(orderKey.toString())){
                            repectCheck.put(orderKey.toString(),orderKey.toString());
                            Integer splitNo = invMove.getAssociateNoSplitno()== 0 ? null : invMove.getAssociateNoSplitno();
                            FinishPoDto finishPoDto = new FinishPoDto(invMove.getAssociateNo(),invMove.getAssociateNoItem(),splitNo);
                            poNoList.add(finishPoDto);
                        }
                    }
                }
            }else{ //采购没有关联关系
                OpsDo doByDoId = baseDoService.getDoByDoId(obj.getDocNo());
                // 采购状态 无关联关系
                if(DoWaitTypeEnum.WaitCG.getType().equals(doByDoId.getWaitType())){
                    Integer splitNo = doByDoId.getNum() == 0 ? null : doByDoId.getNum();
                    FinishPoDto finishPoDto = new FinishPoDto(doByDoId.getOrderId(),Integer.valueOf(doByDoId.getOrderItem()) ,splitNo);
                    //无关联关系的采购单
                    poNoList.add(finishPoDto);
                }
            }
        }
        //调用采购接口获得列表
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(poNoList)){
            poPageList = finishPoService.getPoListByPoNo(poNoList);
        }
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(poPageList)){
            for (FinishPoListForDto poReDto : poPageList){
                poReDto.setInputDisabled(true);//input框
                //合并采购或补库单
                if(!pageObj.getOrderNo().equals(poReDto.getpOrderNo())){
                    poReDto.setCanDelete(false);
                    poReDto.setCanFinish(false);
                    poReDto.setMsg("请到采购查询进行采购操作");
                }
            }
            //返回页面数据
            pageObj.setPoList(poPageList);
        }
    }
    /**
     * 收集可完纳do bugid:14094 20240506 c14717
     * @param opsDoList
     * @param reqList
     * @throws OpsException
     */
    @Override
    public Integer getTKCKFinishDo(List<OpsDo> opsDoList, List<FinishOrderWmsReqDto> reqList, FinishPageForOrderDto pageObj) throws OpsException {
        int jyckOutQty = 0;
        int jyckUseQty = 0;
        String orderNo = "";
        String orderItem = "";
        for(OpsDo opsDo : opsDoList){
            orderNo = opsDo.getOrderId();
            orderItem = opsDo.getOrderItem();
            if (!DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())){
                throw Exceptions.OpsException("需完纳调库单");
            }
            if(opsDo.getWarehouseCode().equals(opsDo.getGatherWarehouseCode())){
                throw Exceptions.OpsException("需完纳异常调库单");
            }
            List<OpsDoItemInventory> opsDoItemInventoryList = baseDoService.getDoItemInventoryByDoId(opsDo.getDoId());
            for(OpsDoItemInventory obj : opsDoItemInventoryList) {
                if(InventoryStatusEnum.T.getCode().equals(obj.getInventoryTableType())){
                    throw Exceptions.OpsException("异常，调库单关联在途");
                }
            }
            OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(opsDo.getWarehouseCode());
            if (Objects.nonNull(opsWarehouse)
                    && WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsWarehouse.getWarehouseType())) {
                throw Exceptions.OpsException("服务备库，无此功能");
            }
            OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(opsDo.getDoId());
            if(Objects.isNull(opsDoItem.getOutQty())){
                opsDoItem.setOutQty(0);
            }
            jyckUseQty = jyckUseQty + opsDoItem.getQty();
            jyckOutQty = jyckOutQty + opsDoItem.getOutQty();
        }
        if(jyckOutQty == 0 ){
            throw Exceptions.OpsException("无出库数据不可完纳");
        }
        if(jyckOutQty == jyckUseQty){
            throw Exceptions.OpsException("已全部出库不可完纳");
        }
        //bugid:14582   c14717 20240701
        TransOrder transOrderByTransNo = transOrderService.getTransOrderByTransNo(orderNo, Integer.valueOf(orderItem));
        if(Objects.isNull(transOrderByTransNo)){
            throw Exceptions.OpsException("调库单表无数据不可完纳");
        }
        if(Objects.isNull(transOrderByTransNo.getShipQty())){
            transOrderByTransNo.setShipQty(0);
        }
        if(jyckOutQty != transOrderByTransNo.getShipQty()){
            throw Exceptions.OpsException("调库单表出库数量与指令出库数量不一致不可完纳");
        }

        for(OpsDo opsDo : opsDoList){
            OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(opsDo.getDoId());
            if(Objects.isNull(opsDoItem.getOutQty())){
                opsDoItem.setOutQty(0);
            }
            //bugid:14585 c14717 20240701
            if(!opsDoItem.getQty().equals(opsDoItem.getOutQty())){
                String warehouseId = opsDo.getWarehouseCode();
                FinishOrderWmsReqDto req = new FinishOrderWmsReqDto(opsDo.getDoId(),warehouseId,opsDoItem.getOutQty(),"");
                reqList.add(req);
            }
        }
        if(Objects.nonNull(pageObj)){
            pageObj.setOrderQty(jyckUseQty);
            pageObj.setOutQty(jyckOutQty);
            pageObj.setFinishQty(jyckOutQty);
        }
        return jyckOutQty;
    }

    /**
     * @description：强制调库出库完纳  bugid:14094 20240506
     * @author ：C14717
     * @date ：Created in 2023/8/14
     */
    @Override
    public Integer finishTKCKDo(List<OpsDo> finishDoList, String OrderId, String OrderItem, StringBuffer logBuf, String userName) throws OpsException {
        //计算出的订单完纳数量
        int orderFinQty = 0;
        for(OpsDo finishDo : finishDoList){
            //更新 doItem useQty
            OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(finishDo.getDoId());

            OpsDoItem updateDoItem = new OpsDoItem();
            updateDoItem.setId(opsDoItem.getId());
            if(Objects.isNull(opsDoItem.getOutQty())){
                opsDoItem.setOutQty(0);
            }
            // sum交易出库outQty
            orderFinQty += opsDoItem.getOutQty();
            // 无需修改数量
            if(opsDoItem.getQty().equals(opsDoItem.getOutQty())){
                continue;
            }
            // 修改doItem qty数量
            updateDoItem.setQty(opsDoItem.getOutQty());
            updateDoItem.setModifyTime(DateUtil.getNow());
            updateDoItem.setModifier(userName);
            OpsDo upOpsDo = new OpsDo();
            //bugid:12482 c14717 2023/10/30
            if(updateDoItem.getQty() == 0){
                updateDoItem.setDelflag(1);
                upOpsDo.setDelflag(1);
                wmOrderTaskService.delWmOrderTask(finishDo.getDoId(),"完纳");
            }
            opsDoItemMapper.updateByPrimaryKeySelective(updateDoItem);
            //更新do status

            upOpsDo.setId(finishDo.getId());
            upOpsDo.setDoStatus(DoStatusEnum.FINISH.getStatus());
            upOpsDo.setModifyTime(DateUtil.getNow());
            upOpsDo.setModifier(userName);
            opsDoMapper.updateByPrimaryKeySelective(upOpsDo);
            //记录变更日期
            logBuf.append("-doid=").append(finishDo.getDoId());
            logBuf.append("-hItmQty:").append(opsDoItem.getQty());
            logBuf.append("-uItmQty:").append(updateDoItem.getQty());
            //通过doid获得出库明细

            List<OpsDoItemInventory> opsDoItemInventoryList = baseDoService.getDoItemInventoryByDoId(finishDo.getDoId());
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(opsDoItemInventoryList)) {
                for (OpsDoItemInventory item : opsDoItemInventoryList) {
                    if(Objects.isNull(item.getOutQty())){
                        item.setOutQty(0);
                    }
                    //释放预占数量
                    baseInventoryService.releasePreQtyInventory(item.getInventoryId(), item.getUseQty() - item.getOutQty(), item.getInventoryTableType(), new UserDto(userName));
                    //记录变更日期
                    logBuf.append("-invId:").append(item.getInventoryId());
                    logBuf.append("-invType:").append(item.getInventoryTableType());
                    logBuf.append("-invPreQty:").append(item.getUseQty() - item.getOutQty());
                    //更新itemInventory outqty数量
                    item.setUseQty(item.getOutQty());
                    item.setModifier(userName);
                    item.setModifyTime(DateUtil.getNow());
                    if(item.getUseQty() == 0){
                        item.setDelflag(1);
                    }
                    opsDoItemInventoryMapper.updateByPrimaryKey(item);
                    //记录变更日期
                    logBuf.append("-hItmInvQty:").append(item.getUseQty());
                    logBuf.append("-uItmInvQty:").append(item.getOutQty());
                }
            }
        }
        //更新trans_order  orderFinQty
        TransOrder transOrderByTransNo = transOrderService.getTransOrderByTransNo(OrderId, Integer.valueOf(OrderItem));
        logBuf.append("-hQuantity:").append(transOrderByTransNo.getQuantity());
        TransOrder updateTransOrder = new TransOrder();
        updateTransOrder.setId(transOrderByTransNo.getId());
        updateTransOrder.setQuantity(orderFinQty);// 已出库
        logBuf.append("-uQuantity:").append(updateTransOrder.getQuantity());
        if(Objects.nonNull(transOrderByTransNo.getInQuantity()) && transOrderByTransNo.getInQuantity().equals(orderFinQty)){
            updateTransOrder.setStatus(OrderTransStatusEnum.DB_FINISHED.getStatus());
            logBuf.append("-hStatus:").append(transOrderByTransNo.getStatus());
            logBuf.append("-uStatus:").append(updateTransOrder.getStatus());
        }
        updateTransOrder.setUpdateUser("完纳");
        transOrderService.updateTransOrderById(updateTransOrder);
        return orderFinQty;
    }

    /**
     * 收集可完纳do bugid:11758 20230814 c14717
     * @param opsDoList
     * @param reqList
     * @throws OpsException
     */
    @Override
    public Integer getFinishDo(List<OpsDo> opsDoList, List<FinishOrderWmsReqDto> reqList,FinishPageForOrderDto pageObj) throws OpsException {
        int jyckOutQty = 0;
        int jyckUseQty = 0;
        String modelNoFlag = "";
        String orderNo = "";
        String orderItem = "";
        for(OpsDo opsDo : opsDoList){
            orderNo = opsDo.getOrderId();
            orderItem = opsDo.getOrderItem();
            if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())){
                if(DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())){
                    throw Exceptions.OpsException("加工单不可完纳");
                }
            }
            OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(opsDo.getDoId());
            if(Objects.isNull(opsDoItem.getOutQty())){
                opsDoItem.setOutQty(0);
            }
            // 型号是否一致，判断是否是子项特发
            if(org.apache.commons.lang3.StringUtils.isBlank(modelNoFlag)){
                modelNoFlag = opsDoItem.getModelno();
            }
            if(!modelNoFlag.equals(opsDoItem.getModelno())){
                throw Exceptions.OpsException("子项特发不可完纳");
            }

            if(DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())){
                jyckUseQty = jyckUseQty + opsDoItem.getQty();
                jyckOutQty = jyckOutQty + opsDoItem.getOutQty();
            }
        }
        if(jyckOutQty == 0 ){
            throw Exceptions.OpsException("无出库数据不可完纳");
        }
        if(jyckOutQty == jyckUseQty){
            throw Exceptions.OpsException("已全部出库不可完纳");
        }
        //验证rcvdetail exp_qty
        Rcvdetail rcvdetail = null;
        try {
            rcvdetail = wmRouterOrderService.getRcvdetail(orderNo, orderItem);
        } catch (OpsException e) {
            log.error("完纳",e);
            throw Exceptions.OpsException("查询订单表失败不可完纳");
        }
        if(Objects.isNull(rcvdetail)){
            throw Exceptions.OpsException("订单表无数据不可完纳");
        }
        if(Objects.isNull(rcvdetail.getExpQty())){
            rcvdetail.setExpQty(0);
        }
        if(jyckOutQty != rcvdetail.getExpQty()){
            throw Exceptions.OpsException("订单表出库数量与指令出库数量不一致不可完纳");
        }
        for(OpsDo opsDo : opsDoList){
            OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(opsDo.getDoId());
            if(Objects.isNull(opsDoItem.getOutQty())){
                opsDoItem.setOutQty(0);
            }
            if(!opsDoItem.getQty().equals(opsDoItem.getOutQty())){
                //bugid:14585  c14717 20240701
                String warehouseId = opsDo.getWarehouseCode();
                FinishOrderWmsReqDto req = new FinishOrderWmsReqDto(opsDo.getDoId(),warehouseId,opsDoItem.getOutQty(),"");
                reqList.add(req);
            }
        }
        if(Objects.nonNull(pageObj)){
            pageObj.setOrderQty(jyckUseQty);
            pageObj.setOutQty(jyckOutQty);
            pageObj.setFinishQty(jyckOutQty);
        }
        return jyckOutQty;
    }

    /**
     * bugid:11758 20230814 c14717
     * @param reqList
     * @param askRepList
     * @return
     * @throws OpsException
     */
    @Override
    public HashMap<String,Integer> checkFinishDo(List<FinishOrderWmsReqDto> reqList, List<FinishOrderWmsResDto> askRepList) throws OpsException {
        if(reqList.size() != askRepList.size()){
            throw Exceptions.OpsException("指令不一致");
        }
        HashMap<String,Integer> resMap = new HashMap<String,Integer>();
        for(FinishOrderWmsReqDto obj : reqList){
            resMap.put(obj.getDocNo(),obj.getFinishQty());
        }
        for(FinishOrderWmsResDto obj : askRepList){
            if("A001".equals(obj.getErrorType())) { //单据不存在不校验
                continue;
            }
            if(!resMap.get(obj.getDocNo()).equals(obj.getFinishQty())){
                throw Exceptions.OpsException("完纳数量不一致");
            }
        }
        for(FinishOrderWmsResDto obj : askRepList){
            if("A001".equals(obj.getErrorType())){ //单据不存在不校验
                continue;
            }
            OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(obj.getDocNo());
            if(Objects.isNull(opsDoItem.getOutQty())){
                opsDoItem.setOutQty(0);
            }
            Integer finishQty = opsDoItem.getOutQty();
            if(!((finishQty).equals(obj.getFinishQty()))){
                throw Exceptions.OpsException("完纳数量不一致：" + obj.toString());
            }
        }
        return resMap;
    }



    /**
     * @description：强制完纳 bugid:11758 20230814 c14717
     * @author ：C14717
     * @date ：Created in 2023/8/14
     */
    @Override
    public Integer finishDo(List<OpsDo> finishDoList,String OrderId, String OrderItem,StringBuffer logBuf, String userName ) throws OpsException {
        //计算出的订单完纳数量
        int orderFinQty = 0;
        for(OpsDo finishDo : finishDoList){
            //更新 doItem useQty
            OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(finishDo.getDoId());

            OpsDoItem updateDoItem = new OpsDoItem();
            updateDoItem.setId(opsDoItem.getId());
            if(Objects.isNull(opsDoItem.getOutQty())){
                opsDoItem.setOutQty(0);
            }
            // sum交易出库outQty
            if (DoTypeEnum.JYCK.getType().equals(finishDo.getDoType())){
                orderFinQty += opsDoItem.getOutQty();
            }
            // 无需修改数量
            if(opsDoItem.getQty().equals(opsDoItem.getOutQty())){
                continue;
            }
            // 修改doItem qty数量
            updateDoItem.setQty(opsDoItem.getOutQty());
            updateDoItem.setModifyTime(DateUtil.getNow());
            updateDoItem.setModifier(userName);
            OpsDo upOpsDo = new OpsDo();
            //bugid:12482 c14717 2023/10/30
            if(updateDoItem.getQty() == 0){
                updateDoItem.setDelflag(1);
                upOpsDo.setDelflag(1);
                wmOrderTaskService.delWmOrderTask(finishDo.getDoId(),"完纳");
            }
            opsDoItemMapper.updateByPrimaryKeySelective(updateDoItem);
            //更新do status

            upOpsDo.setId(finishDo.getId());
            upOpsDo.setDoStatus(DoStatusEnum.FINISH.getStatus());
            upOpsDo.setModifyTime(DateUtil.getNow());
            upOpsDo.setModifier(userName);
            opsDoMapper.updateByPrimaryKeySelective(upOpsDo);
            //记录变更日期
            logBuf.append("-doid=").append(finishDo.getDoId());
            logBuf.append("-hItmQty:").append(opsDoItem.getQty());
            logBuf.append("-uItmQty:").append(updateDoItem.getQty());
            //通过doid获得出库明细
            OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
            example.createCriteria().andDelflagEqualTo(0).andDoIdEqualTo(finishDo.getDoId());
            List<OpsDoItemInventory> opsDoItemInventoryList = opsDoItemInventoryMapper.selectByExample(example);
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(opsDoItemInventoryList)) {
                for (OpsDoItemInventory item : opsDoItemInventoryList) {
                    if(Objects.isNull(item.getOutQty())){
                        item.setOutQty(0);
                    }
                    //释放预占数量
                    baseInventoryService.releasePreQtyInventory(item.getInventoryId(), item.getUseQty() - item.getOutQty(), item.getInventoryTableType(), new UserDto(userName));
                    //记录变更日期
                    logBuf.append("-invId:").append(item.getInventoryId());
                    logBuf.append("-invType:").append(item.getInventoryTableType());
                    logBuf.append("-invPreQty:").append(item.getUseQty() - item.getOutQty());
                    //更新itemInventory outqty数量
                    item.setUseQty(item.getOutQty());
                    item.setModifier(userName);
                    item.setModifyTime(DateUtil.getNow());
                    if(item.getUseQty() == 0){
                        item.setDelflag(1);
                    }
                    opsDoItemInventoryMapper.updateByPrimaryKey(item);
                    //记录变更日期
                    logBuf.append("-hItmInvQty:").append(item.getUseQty());
                    logBuf.append("-uItmInvQty:").append(item.getOutQty());
                }
            }
        }
        //更新rcvdetail

        RcvdetailKey rcvKey = new RcvdetailKey();
        rcvKey.setRorderNo(OrderId);
        rcvKey.setRorderItem(Integer.parseInt(OrderItem));
        Rcvdetail rcvDInfo =  rcvdetailMapper.selectByPrimaryKey(rcvKey);
        Rcvdetail upRcv = new Rcvdetail();
        //rcvdetail.setId(id);
        upRcv.setRorderNo(rcvDInfo.getRorderNo());
        upRcv.setRorderItem(rcvDInfo.getRorderItem());
        upRcv.setUpdateUser(userName);
        upRcv.setUpdateTime(DateUtil.getNow());
        // 含税金额
        upRcv.setAmount(rcvDInfo.getPrice().multiply(new BigDecimal(orderFinQty)));
        // 税率
        upRcv.setTaxRate(new BigDecimal("0.13"));
        //不含税单价
        BigDecimal ntaxPrice = PriceCompute.unitPriceExcludingTax(rcvDInfo.getPrice(), upRcv.getTaxRate());
        upRcv.setNtaxPice(ntaxPrice);
        // 不含税金额
        BigDecimal ntaxAMount = PriceCompute.ntaxAmount(upRcv.getAmount(), upRcv.getTaxRate());
        upRcv.setNtaxAmount(ntaxAMount);
        // 税额
        BigDecimal taxAmount = PriceCompute.taxAmount(upRcv.getAmount(),upRcv.getNtaxAmount());
        upRcv.setTaxAmount(taxAmount);
        // 订单数量
        upRcv.setQuantity(orderFinQty);
        rcvdetailMapper.updateByPrimaryKeySelective(upRcv);

        //插入modidata表
        opsOrderModiDataService.insertModiDataForFinishOrder(upRcv,rcvDInfo,userName);

        //记录变更日期
        logBuf.append("-hrcvAmount:").append(rcvDInfo.getAmount());
        logBuf.append("-urcvAmount:").append(upRcv.getTaxAmount());

        logBuf.append("-hrcvNtaxPice:").append(rcvDInfo.getNtaxPice());
        logBuf.append("-urcvNtaxPice:").append(upRcv.getNtaxPice());

        logBuf.append("-hrcvNtaxAmount:").append(rcvDInfo.getNtaxAmount());
        logBuf.append("-urcvNtaxAmount:").append(upRcv.getNtaxAmount());

        logBuf.append("-hrcvTaxAmount:").append(rcvDInfo.getTaxAmount());
        logBuf.append("-urcvTaxAmount:").append(upRcv.getTaxAmount());

        logBuf.append("-hrcvQty:").append(rcvDInfo.getQuantity());
        logBuf.append("-urcvQty:").append(orderFinQty);
        return orderFinQty;
    }

    /**
     * 订单变更 订单修改
     * <p>
     * 拆分型号需求取消子项 改变为do下发
     * 调拨单如何处理o
     */
    @Override
    public Map<String, String> UpdateDoForOrder(UpdateForOrderDto dto) throws OpsException {

        //通知发货 分支
        Boolean aBoolean = doPcoLogicCenterService.checkDlvEntireAssShipment(dto.getOrderId());
        if(aBoolean){
            return doPcoLogicCenterService.modifyDoPcoForOrder(dto);
        }

        List<OpsDo> doList = null;
        if (Objects.isNull(dto.getOrderItem())) {
            doList = baseDoService.findDoListByOrder(dto.getOrderId(), null, null, null);//bug号 8460 C14717 20221026
        } else {
            doList = baseDoService.findDoListByOrder(dto.getOrderId(), dto.getOrderItem().toString(), null, null);//bug号 8460 C14717 20221026
        }
        Map<String, String> result = new HashMap<String, String>();
        //查询指令
        if (CollectionUtils.isEmpty(doList)) {
            throw Exceptions.OpsException("物流指令变更失败,改订单没有查到对应物流指令：" + dto.getOrderId() + "-" + dto.getOrderItem());
        }
        //修改客户交货期走单独的方法
        dto.setDlvDate(null);
        dto.setWmsDlvDate(null);
        //发货方式更改 //随发分批 子项拆分特发
        if (dto.getAssModelChangeDo()) {
            //bugid: 12058 c14717 20230908 4修改子项特发 物流已经部分发运
            for (OpsDo opsDo : doList){
                if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {//交易客单
                    //物流已经部分发运
                    if (DoStatusEnum.PART.getStatus().equals(opsDo.getDoStatus())) {
                        result.put(opsDo.getDoId(), "物流已经作业不能修改");
                        return result;
                    }
                    //物流已经发运完成
                    if (DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())) {
                        result.put(opsDo.getDoId(), "物流已经作业不能修改");
                        return result;
                    }
                }
            }
            for (OpsDo opsDo : doList) {
                String oldDoId = opsDo.getDoId();
                if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {//交易单
                    if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
                        OpsPcoAndItemAndItemInventoryDto opsPcoAndItemDto = opsPcoService.findPcoAndPcoItemByOrderIdAndOrderItem(opsDo.getOrderId(), opsDo.getOrderItem());
                        if (opsPcoAndItemDto != null) {
                            //bugid:9965 C14717 20230331  子项特发修改，如果是越库不允许子项特发修改
                            if(org.apache.commons.lang3.StringUtils.isNotEmpty(opsPcoAndItemDto.getOpsPco().getRoId())){
                                //此字段如果有值就是子项越库不可以变更
                                result.put(opsDo.getDoId(), "此单已越库，无法变更子项拆分特发");
                            }else {//非越库
                                String oldPcoId = opsPcoAndItemDto.getOpsPco().getPcoId();
                                //是否下发wms
                                if (1 == opsDo.getIsWms()) {//已经下发wms 不存在调拨 看是否能删除
                                    //1.删除pco和do 组装wms删除报文
                                    Long cancelId = null;
                                    List<CancelDocOrderDto> cancelWmsJYCKOrder = new ArrayList<>();
                                    getCancelOrder(opsDo, dto.getRemark1(), cancelWmsJYCKOrder);
                                    if (!CollectionUtils.isEmpty(cancelWmsJYCKOrder)) {//有下发成功的交易出库单
                                        CommonResult wmJYCKResult = opsCallWmsFeignApi.cancelDocOrder(cancelWmsJYCKOrder);//查看是否可删除
                                        if (wmJYCKResult.isSuccess()) {
                                            CancelForOrderDto inputDto = new CancelForOrderDto();
                                            inputDto.setOrderId(opsDo.getOrderId());
                                            inputDto.setOrderItem(opsDo.getOrderItem());
                                            inputDto.setOrderType("1");
                                            inputDto.setUserDto(dto.getUserDto());
                                            inputDto.setReason(dto.getRemark1());
                                            cancelId = insertOrderCancel(inputDto);
                                            //生成新的物流单据
                                            createOpsWmOrder(opsPcoAndItemDto, opsDo, dto);
                                            //删除旧的物流单据
                                            delOpsWmOrder(cancelId, oldPcoId, oldDoId, dto);
                                            result.put(opsDo.getDoId(), "1");
                                        } else {//物流已经作业删除失败
                                            result.put(opsDo.getDoId(), "物流已经作业不能修改");
                                            //throw Exceptions.OpsException("物流已经作业不能修改：" + opsDo.getDoId());
                                        }
                                    }
                                } else if (2 == opsDo.getIsWms()) {
                                    result.put(opsDo.getDoId(), "数据异常，请联系it人员处理");
                                } else {//没有下发wms 说明货不齐 判断是否有调拨 是否是调拨的在途
                                    //生成新的物流单据
                                    createOpsWmOrder(opsPcoAndItemDto, opsDo, dto);
                                    CancelForOrderDto inputDto = new CancelForOrderDto();
                                    inputDto.setOrderId(opsDo.getOrderId());
                                    inputDto.setOrderItem(opsDo.getOrderItem());
                                    inputDto.setOrderType("1");
                                    inputDto.setUserDto(dto.getUserDto());
                                    inputDto.setReason(dto.getRemark1());
                                    long cancelId = insertOrderCancel(inputDto);
                                    //删除旧的物流单据
                                    delOpsWmOrder(cancelId, oldPcoId, oldDoId, dto);
                                    result.put(opsDo.getDoId(), "1");
                                }
                            }
                        } else {//opsPcoAndItemDto 为空
                            result.put(opsDo.getDoId(), "该型号无拆分数据");
                        }
                    } else {//非交易单
                        result.put(opsDo.getDoId(), "该单不是拆分型号");
                    }
                }
            }
        } else {//其他更改
            for (OpsDo opsDo : doList) {
                if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                    //是否下发wms
                    if (1 == opsDo.getIsWms()) {//拆分型号
                        //调用WMS变更接口
                        if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
                            OpsPco opsPco = opsPcoService.findPcoByOrder(opsDo.getOrderId(), opsDo.getOrderItem());
                            if (opsPco.getIsWms() != 1) {
                                result.put(opsDo.getDoId(), "数据异常，请联系it人员处理");
                                continue;
                            }
                            OpsSendPcoAndDoMidIDDto pcoAndDoMidIDDto = new OpsSendPcoAndDoMidIDDto();
                            pcoAndDoMidIDDto.setDoId(opsDo.getDoId());
                            pcoAndDoMidIDDto.setPcoId(opsPco.getPcoId());
                            pcoAndDoMidIDDto.setUpdateOpsDo(fillInUpdateInfo(opsDo, dto));
                            pcoAndDoMidIDDto.setUdateOpsPco(fillInUpdatePcoInfo(opsPco, dto));
                            //bugid:11447 c14717 20230809
                            if(org.apache.commons.lang3.StringUtils.isNotBlank(dto.getCproductNo())){
                                pcoAndDoMidIDDto.setCproductNo(dto.getCproductNo());
                            }
                            //bugid:12294 c14717 20231019
                            CommonResult<String> resWm = wmDoService.postWmsDoAndPcoNew(pcoAndDoMidIDDto);
                            if (resWm.isSuccess()) {
                                updateDo(opsDo, dto);
                                updatePco(opsPco, dto);
                                result.put(opsDo.getDoId(), "1");
                            } else {
                                result.put(opsDo.getDoId(), "WMS已经处理，不允许变更，如需变更，请联系WMS担当处理");
                            }
                        } else {//非拆分型号
                            OpsDoAndItemDto doDto = findDoToWms(opsDo.getDoId());
                            doDto.setOpsDo(fillInUpdateInfo(opsDo, dto));
                            //bugid:11447 c14717 20230809
                            if(org.apache.commons.lang3.StringUtils.isNotBlank(dto.getCproductNo())){
                                doDto.getList().get(0).setCproductNo(dto.getCproductNo());
                            }
                            //bugid:12294 c14717 20231019
                            CommonResult<String> resWm = wmDoService.postWmsDoNew(doDto);
                            if (resWm.isSuccess()) {
                                updateDo(opsDo, dto);
                                result.put(opsDo.getDoId(), "1");
                            } else {
                                result.put(opsDo.getDoId(), "WMS已经处理，不允许变更，如需变更，请联系WMS担当处理");
                            }
                        }
                    } else if (2 == opsDo.getIsWms()) {
                        result.put(opsDo.getDoId(), "数据异常，请联系it人员处理");
                    } else {
                        if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource())) {
                            OpsPco opsPco = opsPcoService.findPcoByOrder(opsDo.getOrderId(), opsDo.getOrderItem());
                            updatePco(opsPco, dto);
                            //发货方式
                            if (Objects.nonNull(dto.getDlvEntire())
                                    && !CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(dto.getDlvEntire())
                                    && !CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(dto.getDlvEntire())
                                    // bugid:12843 21楼 c14717 20240204 货齐单仓修改随发单仓触发货齐计算
                                    //&& !CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.getCode().equals(dto.getDlvEntire())
                            ) {
                                if (!opsDo.getDlvEntire().equals(dto.getDlvEntire())) {
                                    wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(opsPco.getPcoId(), "修改发货方式", 3);
                                }
                            }
                        } else {
                            //发货方式
                            if (Objects.nonNull(dto.getDlvEntire())
                                    && !CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(dto.getDlvEntire())
                                    && !CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(dto.getDlvEntire())
                                    // bugid:12843 21楼 c14717 20240204 货齐单仓修改随发单仓触发货齐计算
                                    //&& !CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE_ITEM_WMS.getCode().equals(dto.getDlvEntire())
                            ) {
                                if (!opsDo.getDlvEntire().equals(dto.getDlvEntire())) {
                                    if (CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(dto.getDlvEntire())) {
                                        //bugid :9426 c14717 20230216
                                        createAssDo(opsDo, 0, dto.getUserDto(), DoTypeEnum.JYCK, dto.getDlvEntire());
                                        //bugid:9965 随发分批 c14717 20230331
                                    }
                                    // bugid:13385 c14717 20240118 修改随发分批优化-整出-计算集约仓
                                    if (CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(dto.getDlvEntire())
                                            && DoWaitTypeEnum.WaitDB.getType().equals(opsDo.getWaitType())
                                            && (DoSourceEnum.ALL.getType().equals(opsDo.getDoSource()) || DoSourceEnum.CG.getType().equals(opsDo.getDoSource()))) {
                                        //计算集约仓
                                        updateGatherWarehouse(opsDo);
                                    }
                                    wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(opsDo.getDoId(), "修改发货方式", 3);
                                }
                            }
                        }
                        //bugid 9406 c14717 20230119 实时计算 修改内容：调整位置
                        updateDo(opsDo, dto);
                        result.put(opsDo.getDoId(), "1");
                    }
                }
            }
        }
        return result;
    }

    /**
     * 订单修改 发货邮箱 bugid:12391 c14717 2023/10/27
     * @param dto
     * @return
     * @throws OpsException
     */
    @Override
    public Map<String, String> updateDoEmail(UpdateForOrderDto dto) throws OpsException{
        List<OpsDo> doList = baseDoService.findDoListByOrder(dto.getOrderId(), dto.getOrderItem().toString(), null, DoTypeEnum.JYCK);
        //查询指令
        if (CollectionUtils.isEmpty(doList)) {
            throw Exceptions.OpsException("订单修改失败，没有查到相应指令");
        }
        if(StringUtils.isEmpty(dto.getEmail())){
            throw Exceptions.OpsException("订单修改失败，邮箱内容为空");
        }
        //发货邮箱验证 bugid:12391 c14717 2023/10/27
        // if(org.apache.commons.lang3.StringUtils.isNotBlank(dto.getEmail())){
        //     String[] mails = dto.getEmail().split(",");
        //     for(String mailAddress : mails){
        //         if(!StringMailUtil.verifyMail(mailAddress)){
        //             throw Exceptions.OpsException("订单修改失败，邮箱格式不正确");
        //         }
        //     }
        // }
        Map<String, String> result = new HashMap<String, String>();
        for (OpsDo opsDo : doList) {
            if (!DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())) {
                UpdateForOrderDto mailDto = new UpdateForOrderDto();
                mailDto.setEmail(dto.getEmail());
                updateDo(opsDo, mailDto);
                result.put(opsDo.getDoId(), "1");
            }

        }
        return result;
    }

    /**
     * bugid 9471 c14717 20230206
     * 修改地址  特发，自提item 为单位批量修改（注意针对拆分数量）
     */
    @Override
    public Map<String, String> updateDoAddressNew(UpdateForOrderDto dto, Boolean specialFlag) throws OpsException {
        if (dto.getOrderItem() == null) {
            throw Exceptions.OpsException("订单修改失败，不能按整单修改");
        }
        List<OpsDo> doList = baseDoService.findDoListByOrder(dto.getOrderId(), dto.getOrderItem().toString(), null, DoTypeEnum.JYCK);
        //查询指令
        if (CollectionUtils.isEmpty(doList)) {
            throw Exceptions.OpsException("订单修改失败，没有查到相应指令");
        }
        OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(doList.get(0).getWarehouseCode());
        if (Objects.nonNull(opsWarehouse)) {
            if (WarehouseTypeEnum.WT.getHouseTypeCode().equals(opsWarehouse.getWarehouseType())) {
                throw Exceptions.OpsException("订单修改失败，无法修改服务备库");
            } else {
                Map<String, String> result = new HashMap<String, String>();
                List<OpsDoAndItemDto> wmsDos = new ArrayList<OpsDoAndItemDto>();
                for (OpsDo opsDo : doList) {
                    if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                        //物流已经部分发运
                        if (DoStatusEnum.PART.getStatus().equals(opsDo.getDoStatus())) {
                            throw Exceptions.OpsException("订单修改失败，此状态无法修改地址P");
                        }
                        //物流已经发运完成
                        if (DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())) {
                            throw Exceptions.OpsException("订单修改失败，此状态无法修改地址F");
                        }
                        if (Objects.nonNull(specialFlag)) {//设置是否特发
                            if (specialFlag) {
                                dto.setSpecialFlag(1);
                            } else {
                                dto.setSpecialFlag(2);
                            }
                        }
                        //是否下发wms
                        if (1 == opsDo.getIsWms()) {
                            updateDo(opsDo, dto);
                            OpsDoAndItemDto doDto = findDoToWms(opsDo.getDoId());
                            if (Objects.isNull(doDto)) {
                                throw Exceptions.OpsException("订单修改失败，无对应指令");
                            }
                            /*OpsWmOrderTask opsWmOrderTaskDo = findWmsOrderTaskService.findWmsOrderTaskByWmOrderId(opsDo.getDoId(), WmOrderTaskEnum.DO);
                            doDto.setWmOrderTaskId(opsWmOrderTaskDo.getId());*/
                            wmsDos.add(doDto);
                        } else if (2 == opsDo.getIsWms()) {
                            throw Exceptions.OpsException("订单修改失败，此状态无法修改地址-iswms2");
                        } else {
                            updateDo(opsDo, dto);
                        }
                    }
                    result.put(opsDo.getDoId(), "1");
                }
                if (CollectionUtil.isNotEmpty(wmsDos)) {//请求do
                    //bugid:12294 c14717 20231019
                    CommonResult<String> resWm = opsCallWmsFeignApi.updateDoToWmsBatch(wmsDos);
                    List<OpsWmOrderTask> wmTaskDoList = new ArrayList<>();
                    if (resWm.isSuccess()) {
                        for(OpsDoAndItemDto doItemDto : wmsDos){
                            OpsWmOrderTask wmTaskdo = wmOrderTaskService.createWmTaskPo(doItemDto.getOpsDo().getDoId(), WmOrderTaskEnum.DO,
                                    WmOrderTaskEnum.ORDER,SendStatusEnum.SUCCESS,new CreateInfoDto("二次下发"), DateUtil.getNow(),1,"下发成功");
                            wmTaskDoList.add(wmTaskdo);
                        }
                        wmOrderTaskService.addBatchOpsWmOrderTask(wmTaskDoList);
                        return result;
                    } else {
                        throw Exceptions.OpsException("订单修改失败，此状态无法修改地址");
                    }
                }
                return result;
            }
        } else {
            throw Exceptions.OpsException("订单修改失败，无仓库代码");
        }
    }


    //创建新的物流单据
    private void createOpsWmOrder(OpsPcoAndItemAndItemInventoryDto opsPcoAddItemDto, OpsDo opsDo, UpdateForOrderDto dto) throws OpsException {
        //创建新的单据；
        List<OpsPcoItem> opsPcoItemList = opsPcoAddItemDto.getItemList();
        OpsDoItem doItem = baseWMOrderService.getDoItem(opsDo.getDoId());
        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
        for (OpsPcoItem opsPcoItem : opsPcoItemList) {
            String doid = String.format(OrderIDFormatEnum.DO_PCO_CHANGE_DO_FORMAT.getFormat(), opsDo.getOrderId(),
                    String.format("%03d", Integer.parseInt(opsDo.getOrderItem())),
                    String.format("%03d", opsPcoItem.getPcoItem()), String.format("%03d", opsDo.getAssNum()));
            //创建do
            if (DoWaitTypeEnum.WaitCG.getType().equals(opsPcoItem.getWaitType())) {
                OpsRequestpurchase request = new OpsRequestpurchase();
                request.setWmtag(WMPurchaseTagEnum.WHOLE.getType());
                request.setUpdatetime(new Date());
                OpsRequestpurchaseExample example = new OpsRequestpurchaseExample();
                example.createCriteria().andOrdernoEqualTo(opsDo.getOrderId()).andItemnoEqualTo(Integer.parseInt(opsDo.getOrderItem())).andSplititemnoEqualTo(opsPcoItem.getPcoItem());
                opsRequestpurchaseMapper.updateByExampleSelective(request, example);
            }
            //改变调拨单的num号 ass_num号
            if(DoWaitTypeEnum.WaitDB.getType().equals(opsPcoItem.getWaitType())){
                List<OpsDo> dbckListByOrder = baseDoService.findDBCKListByOrder(opsDo.getOrderId(), opsDo.getOrderItem(), opsPcoItem.getPcoItem(), DoSourceEnum.ASSModelNo);
                OpsDo pcoDbckDo= null;
                if(org.apache.commons.collections.CollectionUtils.isNotEmpty(dbckListByOrder)){
                    dbckListByOrder.sort(Comparator.comparing(OpsDo::getExtNum,Comparator.reverseOrder()));
                    pcoDbckDo =  dbckListByOrder.get(0);
                }
                if(Objects.nonNull(pcoDbckDo)){
                    List<OpsRo> opsRos = baseRoService.findDBRoByDBDo(pcoDbckDo);
                    if(org.apache.commons.collections.CollectionUtils.isNotEmpty(opsRos)){
                        //修改do num
                        pcoDbckDo.setNum(opsPcoItem.getPcoItem());
                        pcoDbckDo.setAssNum(0);
                        pcoDbckDo.setExtNum(pcoDbckDo.getExtNum());
                        pcoDbckDo.setDoSource(DoSourceEnum.ASSQTY.getType());
                        updateDoByDo(pcoDbckDo);
                        //修改ro num
                        opsRos.get(0).setNum(opsPcoItem.getPcoItem());
                        opsRos.get(0).setAssNum(0);
                        baseRoService.updateRoById(opsRos.get(0));
                    }
                }
            }
            OpsDo opsUpdateDo = fillInUpdateInfo(opsDo, dto);
            opsUpdateDo.setId(null);
            opsUpdateDo.setDoId(doid);
            opsUpdateDo.setWaitType(opsPcoItem.getWaitType());
            opsUpdateDo.setDoSource(DoSourceEnum.ASSQTY.getType());//num 不为0默认按照拆分数量搞
            opsUpdateDo.setNum(opsPcoItem.getPcoItem());//num 不为0默认按照拆分数量搞 不然入库找不到对应的do
            opsUpdateDo.setAssNum(0);
            //创建doitem
            OpsDoItem opsDoItem = new OpsDoItem();
            // bugid:20854 C14717 20260509
            opsDoItem.setCproductNo(doItem.getCproductNo());
            opsDoItem.setContractNo(doItem.getContractNo());
            opsDoItem.setProductName(doItem.getProductName());

            opsDoItem.setDoId(doid);
            opsDoItem.setOutQty(0);
            opsDoItem.setVersion(0);
            opsDoItem.setQty(opsPcoItem.getSubQty());
            opsDoItem.setDoItem(1);
            opsDoItem.setModelno(opsPcoItem.getSubModelno());
            opsDoItem.setDelflag(0);
            opsDoItem.setCreator(dto.getUserDto().getUserName());
            opsDoItem.setCreTime(new Date());
            List<OpsPcoItemInventory> opsPcoItemInventoryList = opsPcoAddItemDto.getItemInventoryList();
            List<OpsDoItemInventory> doItemInventoryList = new ArrayList<>();
            for (OpsPcoItemInventory opsPcoItemInventory : opsPcoItemInventoryList) {
                if (opsPcoItemInventory.getPcoId().equals(opsPcoItem.getPcoId()) && opsPcoItemInventory.getPcoItem().equals(opsPcoItem.getPcoItem())) {
                    //创建doItemInventory
                    OpsDoItemInventory opsDoItemInventory = new OpsDoItemInventory();
                    opsDoItemInventory.setDoId(doid);
                    opsDoItemInventory.setDoItem(1);
                    opsDoItemInventory.setInventoryId(opsPcoItemInventory.getInventoryId());
                    opsDoItemInventory.setInventoryTableType(opsPcoItemInventory.getInventoryTableType());
                    opsDoItemInventory.setSrcInventoryId(opsPcoItemInventory.getSrcInventoryId());
                    opsDoItemInventory.setSrcInventoryTableType(opsPcoItemInventory.getSrcInventoryTableType());
                    opsDoItemInventory.setUseQty(opsPcoItemInventory.getUseQty());
                    opsDoItemInventory.setVersion(0L);
                    opsDoItemInventory.setDelflag(0);
                    opsDoItemInventory.setCreTime(new Date());
                    opsDoItemInventory.setCreator(dto.getUserDto().getUserName());
                    opsDoItemInventory.setSortnum(opsPcoItemInventory.getSortnum());
                    doItemInventoryList.add(opsDoItemInventory);
                }
            }
            //bugid:9965 20230315 c14717
            //越库和上预约都带上此字段
            opsUpdateDo.setRelocation("");
            if (Objects.nonNull(opsPcoItem.getIsCross()) && opsPcoItem.getIsCross()) {
                opsUpdateDo.setRelocation(opsPcoItem.getPcoId());
            }
            insertDo(opsUpdateDo, opsDoItem, doItemInventoryList, dto.getUserDto());
            //生成Ordertask
            OpsWmOrderTask opsWmOrderTask = new OpsWmOrderTask();
            opsWmOrderTask.setWmOrderId(opsUpdateDo.getDoId());
            opsWmOrderTask.setWmOrderType(WmOrderTaskEnum.DO.getType());
            opsWmOrderTask.setTaskType(WmOrderTaskEnum.ORDER.getType());
            opsWmOrderTask.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.getType()));
            opsWmOrderTask.setCreator(dto.getUserDto().getUserName());
            opsWmOrderTask.setCreTime(new Date());
            //wmOrderTaskService.addOpsWmOrderTask(opsWmOrderTask);
            taskList.add(opsWmOrderTask);

        }
        //拆分型号子项特发增加拆分do
        List<OpsDo> newDoList = baseDoService.findAllJYCKByOrder(opsDo.getOrderId(), opsDo.getOrderItem());
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(newDoList)){
            for(OpsDo newDo : newDoList){
                createAssDo(newDo, 0, dto.getUserDto(), DoTypeEnum.JYCK, "2");
            }
        }
        //判断是否删除调拨单 如果拆do 注释此方法
        /*List<OpsDo> newDoList = baseDoService.findAllJYCKByOrder(opsDo.getOrderId(), opsDo.getOrderItem());
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(newDoList)){
            for(OpsDo newDo : newDoList){
                OpsDo delDBDo= updateDoDlvEntryDelDB(newDo);
                if (Objects.nonNull(delDBDo)) {//添加上架类型
                    if (org.apache.commons.lang3.StringUtils.isNotEmpty(delDBDo.getRoId())) {
                        updateDoToRoId(newDo.getId(), delDBDo.getRoId(), delDBDo.getRoCrossType(),delDBDo.getDoId());
                    }
                    List<OpsDoItemInventory> doItemInventorieList = baseDoService.getDoItemInventoryByDoId(newDo.getDoId());
                    int qty = 0 ;
                    for (OpsDoItemInventory obj : doItemInventorieList) {
                        if (InventoryTableTypeEnum.NORMAL.getType().equals(obj.getInventoryTableType())) {
                            qty += obj.getUseQty();
                        }
                    }
                    //bugid:9805 c14717 20230228 此方法修改集运仓时候传，数量传doitemInventory 状态为N的情况
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_WAREHOUSE, newDo);
                }
            }
        }*/
        //批量更新task
        wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
    }

    //子项特发删除旧的物流单据
    @Override
    public void delOpsWmOrder(Long cancelId, String pcoId, String doId, UpdateForOrderDto dto) throws OpsException {
        OpsPcoAndItemAndItemInventoryDto pcoDto = opsPcoService.findPcoAndPcoItemByPcoId(pcoId);
        OpsPco updatePco = new OpsPco();
        updatePco.setId(pcoDto.getOpsPco().getId());
        updatePco.setPcoStatus(DoStatusEnum.CANCEL.getStatus());
        updatePco.setDelflag(1);
        updatePco.setModifyTime(new Date());
        updatePco.setModifier(dto.getUserDto().getUserName());
        opsPcoMapper.updateByPrimaryKeySelective(updatePco);
        insertOrderCancelItem(cancelId, pcoId, PcoTypeEnum.PTJG.getDesc(), "取消成功", dto.getUserDto());

        /*OpsPcoItemExample example = new OpsPcoItemExample();
        OpsPcoItemExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(opsPcoAddItemDto.getOpsPco().getPcoId());
        criteria.andDelflagEqualTo(0);//删除标*/
        List<OpsPcoItem> OpsPcoItemList = pcoDto.getItemList();//opsPcoItemMapper.selectByExample(example);
        for (OpsPcoItem opsPcoItem : OpsPcoItemList) {
            OpsPcoItem updatePcoItem = new OpsPcoItem();
            updatePcoItem.setId(opsPcoItem.getId());
            updatePcoItem.setDelflag(1);
            updatePcoItem.setModifyTime(new Date());
            updatePcoItem.setModifier(dto.getUserDto().getUserName());
            opsPcoItemMapper.updateByPrimaryKeySelective(updatePcoItem);
        }
        List<OpsPcoItemInventory> opsPcoItemInventoryList = pcoDto.getItemInventoryList();
        for (OpsPcoItemInventory obj : opsPcoItemInventoryList) {
            OpsPcoItemInventory updateObj = new OpsPcoItemInventory();
            updateObj.setId(obj.getId());
            updateObj.setDelflag(1);
            updateObj.setModifyTime(new Date());
            updateObj.setModifier(dto.getUserDto().getUserName());
            opsPcoItemInventoryMapper.updateByPrimaryKeySelective(updateObj);
        }
        OpsDo oldDo = baseDoService.getDoByDoId(doId);
        OpsDo updateDo = new OpsDo();
        updateDo.setId(oldDo.getId());
        updateDo.setDelflag(1);
        updateDo.setDoStatus(DoStatusEnum.CANCEL.getStatus());
        updateDo.setWaitType(DoWaitTypeEnum.CANCEL.getType());
        updateDo.setModifyTime(new Date());
        updateDo.setModifier(dto.getUserDto().getUserName());
        opsDoMapper.updateByPrimaryKeySelective(updateDo);
        OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(doId);
        OpsDoItem updateDoItem = new OpsDoItem();
        updateDoItem.setId(opsDoItem.getId());
        updateDoItem.setDelflag(1);
        updateDoItem.setModifyTime(new Date());
        updateDoItem.setModifier(dto.getUserDto().getUserName());
        opsDoItemMapper.updateByPrimaryKeySelective(updateDoItem);
        insertOrderCancelItem(cancelId, doId, DoTypeEnum.JYCK.getType(), "取消成功", dto.getUserDto());

        //更新task bugid:11249 c14717 20230629 task中对应的do/pco是否设置为delflag=1
        wmOrderTaskService.delWmOrderTask(doId, "子项发货");
        wmOrderTaskService.delWmOrderTask(pcoId, "子项发货");
    }

    //整备wms删单报文
    private void getCancelOrder(OpsDo opsDo, String msg, List<CancelDocOrderDto> doAndPcoList) throws OpsException {
        if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
            if (opsDo.getDoStatus() > DoStatusEnum.WAIT.getStatus()) {
                throw Exceptions.OpsException("发货中和发货完成不可取消", opsDo.getDoId());
            }
            CancelDocOrderDto doParam = new CancelDocOrderDto();
            doParam.setWmId(opsDo.getId());
            doParam.setCustomerId("SMC");//固定不变
            doParam.setDocNo(opsDo.getDoId());//do_id 或pco_id
            if (StringUtils.isEmpty(msg)) {
                //throw Exceptions.OpsException("wms需要取消订单原因", opsDo.getDoId());
                //bugid : 8811 c14717 20221130
                msg = "OPS取消指令";
            }
            doParam.setErpCancelReason(msg);//取消原因
            doParam.setOrderType("CM");  //如果是pco 固定kt; 如果是do CM参加DoTypeEnum枚举对应关系的wltype
            doParam.setWarehouseId(opsDo.getWarehouseCode());
            doAndPcoList.add(doParam);
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && !DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                List<OpsPco> pcoList = opsPcoService.GetPcolistByOrder(opsDo.getOrderId(), opsDo.getOrderItem());
                if (!CollectionUtils.isEmpty(pcoList)) {
                    for (OpsPco opsPco : pcoList) {
                        CancelDocOrderDto param = new CancelDocOrderDto();
                        param.setWmId(opsPco.getId());
                        param.setCustomerId("SMC");//固定不变
                        param.setDocNo(opsPco.getPcoId());//do_id 或pco_id
                        param.setErpCancelReason(msg);//取消原因
                        param.setOrderType("KT");  //如果是pco 固定kt; 如果是do 参加DoTypeEnum枚举对应关系的wltype
                        param.setWarehouseId(opsPco.getWarehouseCode());
                        doAndPcoList.add(param);
                    }
                }
            }
        }
    }

    //更新task
    private void updateWmOrderTask(String wmOrderId, String msg) throws OpsException {
        wmOrderTaskService.updateOpsWmOrderTaskFlagToFlagAndMsgByOrderId(wmOrderId, msg, 2);
    }

    @Override
    public OpsPco fillInUpdatePcoInfo(OpsPco updatePco, UpdateForOrderDto dto) {
        //特发标识
        if (Objects.nonNull(dto.getSpecialFlag())) {//修改pco
            updatePco.setSpceialFlag(dto.getSpecialFlag());
        }

        if (Objects.nonNull(dto.getDlvDate())) {//客户交货 修改pco
            updatePco.setHopeDate(dto.getDlvDate());
        }
        if (Objects.nonNull(dto.getWmsDlvDate())) {//指定物流交货期 修改pco
            updatePco.setWlDate(dto.getWmsDlvDate());
        }
        return updatePco;
    }

    //填充要变更的信息下发wms
    @Override
    public OpsDo fillInUpdateInfo(OpsDo upOpsDo, UpdateForOrderDto dto) {
        if (StringUtils.hasText(dto.getDlvSite())) {//交货地点 1：直发客户 2：直发营业所 3：所自提(这个不包括自提)
            upOpsDo.setDlvSite(dto.getDlvSite());
        }
        if (Objects.nonNull(dto.getSpecialFlag())) {
            upOpsDo.setSpceialFlag(dto.getSpecialFlag());
        }
        //分包方式
        if (Objects.nonNull(dto.getDlvType())) {
            upOpsDo.setPakageType(dto.getDlvType());
        }
        if (Objects.nonNull(dto.getDlvDate())) {//客户交货期
            upOpsDo.setHopeDate(dto.getDlvDate());
        }
        if (Objects.nonNull(dto.getWmsDlvDate())) {//指定物流交货期
            upOpsDo.setWlDate(dto.getWmsDlvDate());
        }
        if (StringUtils.hasText(dto.getDlvEntire())) {
            upOpsDo.setDlvEntire(dto.getDlvEntire());
        }
        if (Objects.nonNull(dto.getCarried())) {
            upOpsDo.setCarried(dto.getCarried());
        }

        if (StringUtils.hasText(dto.getProvince())) {
            upOpsDo.setProvince(dto.getProvince());
        }
        if (StringUtils.hasText(dto.getCity())) {
            upOpsDo.setCity(dto.getCity());
        }
        if (StringUtils.hasText(dto.getDlvAddress())) {
            upOpsDo.setStreet(dto.getDlvAddress());
        }
        if (StringUtils.hasText(dto.getDlvAddress())) {
            upOpsDo.setAddress(dto.getDlvAddress());
        }
        if (StringUtils.hasText(dto.getWarehouseCode())) {
            upOpsDo.setWarehouseCode(dto.getWarehouseCode());
        }
        if (StringUtils.hasText(dto.getLinkman())) {
            upOpsDo.setLinkman(dto.getLinkman());
        }
        if (StringUtils.hasText(dto.getMobile())) {
            if (StringPhoneUtil.isMobil(dto.getMobile())) {
                upOpsDo.setMobile(dto.getMobile());
            }
        }
        if (StringUtils.hasText(dto.getPhone())) {
            //是电话 且不是手机号 存电话
            if (StringPhoneUtil.isPhone(dto.getPhone()) && !StringPhoneUtil.isMobil(dto.getPhone())) {
                upOpsDo.setPhone(dto.getPhone());
            }
        }
        //邮编
        if (StringUtils.hasText(dto.getPostcode())) {
            upOpsDo.setPostcode(dto.getPostcode());
        }
        //bugid:11447 c14717 20230809
        if(StringUtils.hasText(dto.getCorderNo())){
            upOpsDo.setCorderNo(dto.getCorderNo());
        }
        return upOpsDo;
    }

    @Override
    public void updatePcoDeliveryDay(OpsPco opsPco, UpdateForOrderDto dto) throws OpsException {
        OpsPco updatePco = new OpsPco();
        if (Objects.nonNull(dto.getDlvDate())) {//客户交货 修改pco
            updatePco.setHopeDate(dto.getDlvDate());
        }
        if (Objects.nonNull(dto.getWmsDlvDate())) {//指定物流交货期 修改pco
            updatePco.setWlDate(dto.getWmsDlvDate());
        }
        updatePco.setId(opsPco.getId());
        updatePco.setModifyTime(new Date());
        updatePco.setModifier("物流货期");
        opsPcoMapper.updateByPrimaryKeySelective(updatePco);
    }

    @Override
    public void updatePco(OpsPco opsPco, UpdateForOrderDto dto) throws OpsException {
        OpsPco updatePco = null;
        if (Objects.nonNull(dto.getSpecialFlag()) || Objects.nonNull(dto.getDlvDate()) || Objects.nonNull(dto.getWmsDlvDate())) {
            updatePco = new OpsPco();
        }
        //特发标识
        if (Objects.nonNull(dto.getSpecialFlag())) {//修改pco
            updatePco.setSpceialFlag(dto.getSpecialFlag());
        }
        if (Objects.nonNull(updatePco)) {
            updatePco.setId(opsPco.getId());
            updatePco.setModifyTime(new Date());
            updatePco.setModifier("门户修改");
            opsPcoMapper.updateByPrimaryKeySelective(updatePco);
        }
    }

    @Override
    public void updateDoDeliveryDay(OpsDo opsDo, UpdateForOrderDto dto) throws OpsException {//isWms 是否下发WSM
        OpsDo upOpsDo = new OpsDo();
        if (Objects.nonNull(dto.getDlvDate())) {//客户交货 修改pco
            upOpsDo.setHopeDate(dto.getDlvDate());
        }
        if (Objects.nonNull(dto.getWmsDlvDate())) {//指定物流交货期 修改pco
            upOpsDo.setWlDate(dto.getWmsDlvDate());
        }
        upOpsDo.setId(opsDo.getId());
        upOpsDo.setModifyTime(new Date());
        upOpsDo.setModifier("物流货期");
        opsDoMapper.updateByPrimaryKeySelective(upOpsDo);
    }
    @Override
    public void updateDo(OpsDo opsDo, UpdateForOrderDto dto) throws OpsException {//isWms 是否下发WSM
        OpsDo upOpsDo = new OpsDo();
        if (StringUtils.hasText(dto.getDlvSite())) {//交货地点 1：直发客户 2：直发营业所 3：所自提
            upOpsDo.setDlvSite(dto.getDlvSite());
        }
        //特发标识
        if (Objects.nonNull(dto.getSpecialFlag())) {//修改pco
            upOpsDo.setSpceialFlag(dto.getSpecialFlag());
        }
        //集约方式
        if (Objects.nonNull(dto.getDlvEntire())) {
            upOpsDo.setDlvEntire(dto.getDlvEntire());
        }
        //分包方式
        if (Objects.nonNull(dto.getDlvType())) {
            upOpsDo.setPakageType(dto.getDlvType());
        }
        if (Objects.nonNull(dto.getCarried())) {
            upOpsDo.setCarried(dto.getCarried());
        }
        if (StringUtils.hasText(dto.getProvince())) {
            upOpsDo.setProvince(dto.getProvince());
        }
        if (StringUtils.hasText(dto.getCity())) {
            upOpsDo.setCity(dto.getCity());
        }
        if (StringUtils.hasText(dto.getDlvAddress())) {
            upOpsDo.setStreet(dto.getDlvAddress());
        }
        if (StringUtils.hasText(dto.getDlvAddress())) {
            upOpsDo.setAddress(dto.getDlvAddress());
        }
        if (StringUtils.hasText(dto.getWarehouseCode())) {
            upOpsDo.setWarehouseCode(dto.getWarehouseCode());
        }
        if (StringUtils.hasText(dto.getLinkman())) {
            upOpsDo.setLinkman(dto.getLinkman());
        }
        if (StringUtils.hasText(dto.getMobile())) {
            if (StringPhoneUtil.isMobil(dto.getMobile())) {
                upOpsDo.setMobile(dto.getMobile());
            }
        }
        if (StringUtils.hasText(dto.getPhone())) {
            //是电话 且不是手机号 存电话
            if (StringPhoneUtil.isPhone(dto.getPhone()) && !StringPhoneUtil.isMobil(dto.getPhone())) {
                upOpsDo.setPhone(dto.getPhone());
            }
        }
        //邮编
        if (StringUtils.hasText(dto.getPostcode())) {
            upOpsDo.setPostcode(dto.getPostcode());
        }
        //bugid:11447 c14717 20230809
        if(StringUtils.hasText(dto.getCorderNo())){
            upOpsDo.setCorderNo(dto.getCorderNo());
        }
        if(StringUtils.hasText(dto.getCproductNo())){
            OpsDoItemExample example = new OpsDoItemExample();
            example.createCriteria().andDelflagEqualTo(0).andDoIdEqualTo(opsDo.getDoId());
            OpsDoItem update = new OpsDoItem();
            update.setCproductNo(dto.getCproductNo());
            opsDoItemMapper.updateByExampleSelective(update,example);
        }
        //发货邮箱 bugid:12391 c14717 2023/10/27
        if(StringUtils.hasText(dto.getEmail())){
            upOpsDo.setEmail(dto.getEmail());
        }
        upOpsDo.setId(opsDo.getId());
        upOpsDo.setModifyTime(new Date());
        upOpsDo.setModifier("门户修改");
        opsDoMapper.updateByPrimaryKeySelective(upOpsDo);
    }

    @Override
    public void updateDoByDo(OpsDo opsDo) {
        opsDoMapper.updateByPrimaryKeySelective(opsDo);

    }

    /**
     * 分库补库采购仓库和实际入库仓库不一致，修改调拨出仓库号
     */
    @Override
    public void updateDoByCGDBCKDo(OpsDo opsCGDBCKDo) {
        if (DoTypeEnum.CGDBCK.getType().equals(opsCGDBCKDo.getDoType())) {
            OpsDo updateDo = new OpsDo();
            updateDo.setId(opsCGDBCKDo.getId());
            updateDo.setWarehouseCode(opsCGDBCKDo.getWarehouseCode());
            updateDo.setModifyTime(new Date());
            opsDoMapper.updateByPrimaryKeySelective(opsCGDBCKDo);
        }
    }


    /**
     * @description：删除DO
     * @author ：c02483
     * @date ：Created in 2022/1/7 10:07
     */
    @Override
    public void delDoByDoId(Long id, String doId, String orderId, String orderItem, UserDto userDto) throws OpsException {
        OpsDo updateDo = new OpsDo();
        updateDo.setId(id);
        //updateDo.setDoId(doId);
        updateDo.setDelflag(1);
        updateDo.setDoStatus(DoStatusEnum.CANCEL.getStatus());
        updateDo.setWaitType(DoWaitTypeEnum.CANCEL.getType());
        updateDo.setModifier(userDto.getUserName());
        updateDo.setModifyTime(new Date());
        opsDoMapper.updateByPrimaryKeySelective(updateDo);
        OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(doId);
        OpsDoItem updateDoItem = new OpsDoItem();
        updateDoItem.setId(opsDoItem.getId());
        updateDoItem.setDelflag(1);
        updateDoItem.setModifier(userDto.getUserName());
        updateDoItem.setModifyTime(new Date());
        opsDoItemMapper.updateByPrimaryKeySelective(updateDoItem);
        List<OpsDoItemInventory> subdoItemInventoryList = baseDoService.getDoItemInventoryByDoId(doId);
        if(CollectionUtil.isNotEmpty(subdoItemInventoryList)){
            for (OpsDoItemInventory subdoItemInventory : subdoItemInventoryList) {
                //释放库存 在途表  在库表
                OpsDoItemInventory updateOpsDoItemInventory = new OpsDoItemInventory();
                updateOpsDoItemInventory.setId(subdoItemInventory.getId());
                updateOpsDoItemInventory.setDelflag(1);
                updateOpsDoItemInventory.setModifier(userDto.getUserName());
                updateOpsDoItemInventory.setModifyTime(new Date());
                opsDoItemInventoryMapper.updateByPrimaryKeySelective(updateOpsDoItemInventory);
                baseInventoryService.releasePreQtyInventory(subdoItemInventory.getInventoryId(), subdoItemInventory.getUseQty(), subdoItemInventory.getInventoryTableType(), userDto);
            }
        }

    }


    /**
     * 取消
     */
    /**
     * 软删除 do doitem doIteminventory，释放占用预占
     * @param id do表的id
     */
    @Override
    public void CancelDo(Long id, String doId, String orderId, String orderItem, UserDto userDto) throws OpsException {
        //变更OpsDo状态为取消
        delDoByDoId(id, doId, orderId, orderItem, userDto);
    }

    public void CancelOnlyDo(String doId, String reason, UserDto userDto) throws OpsException {

    }

    /**
     * ops提交wmsDO和DOite
     */
    @Override
    public OpsDoAndItemDto findDoToWms(String doId) throws OpsException {
        OpsDoAndItemDto opsDoAndItemDto = new OpsDoAndItemDto();
        OpsDo opsDo = baseDoService.getDoByDoId(doId);
        if (opsDo == null) return null;
        opsDoAndItemDto.setOpsDo(opsDo);

        //add doItem to adapter
        OpsDoItem doItem = baseDoService.getDoItemByDoId(doId);
        if (doItem == null) return null;
        List<OpsDoItem> doItemList = new ArrayList<>();
        doItemList.add(doItem);
        opsDoAndItemDto.setList(doItemList);
        //do-doitem 1对1
        return opsDoAndItemDto;
    }


    @Override
    public Long insertOrderCancel(CancelForOrderDto orderDto) {
        OpsOrderCancel cancel = new OpsOrderCancel();
        cancel.setOrderid(orderDto.getOrderId());
        cancel.setOrderItem(orderDto.getOrderItem());
        cancel.setOrderType(orderDto.getOrderType());
        cancel.setReason(orderDto.getReason());
        cancel.setCreTime(new Date());
        cancel.setCreator(orderDto.getUserDto().getUserName());
        opsOrderCancelDao.insertCancelReturnId(cancel);
        return cancel.getId();
    }

    @Override
    public Integer insertOrderCancelItem(Long cancelId, String wlOrderId, String wlType, String result, UserDto userDto) {
        OpsOrderCancelItem cancelItem = new OpsOrderCancelItem();
        cancelItem.setCancelId(cancelId);
        cancelItem.setWlOrderId(wlOrderId);
        cancelItem.setWlType(wlType);
        cancelItem.setResult(result);
        cancelItem.setCreTime(new Date());
        cancelItem.setCreator(userDto.getUserName());
        return opsOrderCancelItemMapper.insertSelective(cancelItem);
    }

    /**
     * 存在数量不一致的情况 数量不一致时退还库存  区分交易出库和dbck确认
     * 位图出库确认
     */
    public String wmWTDoConfirm(WmWTDoConfirmDto param) throws OpsException {
        if (Objects.isNull(param)) {
            throw Exceptions.OpsException("参数解析失败--WmDoConfirmDto");
        }
        WmWTDoItemConfirmDto wmWTDoItemConfirmDto = param.getItems();//doItem
        if (wmWTDoItemConfirmDto == null) {
            throw Exceptions.OpsException("参数解析失败--wmDoItemConfirmDto或为空");
        }
        String doid = param.getDeliveryOrderCode();
        if (StringUtils.isEmpty(doid)) {
            throw Exceptions.OpsException("出库指令为空");
        }
        log.info("WT出库确认回传: " + doid);
        //查询do表
        OpsDo opsDo = baseDoService.getDoByDoId(doid);
        if (opsDo == null) {
            throw Exceptions.OpsException("无此发货单", doid);
        }
        if (DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())) {
            throw Exceptions.OpsException("此发货单已完成，不允许继续过账处理", doid);
        }
        //查询doItem
        OpsDoItemExample doItemSearchExample = new OpsDoItemExample();
        OpsDoItemExample.Criteria doItemSearchCriteria = doItemSearchExample.createCriteria();
        doItemSearchCriteria.andDoIdEqualTo(doid);
        doItemSearchCriteria.andDelflagEqualTo(0);
        List<OpsDoItem> opsDoItems = opsDoItemMapper.selectByExample(doItemSearchExample);
        //确认过只有一条记录
        OpsDoItem opsDoItem = opsDoItems.get(0);
        //计算 累加outQty数量
        if (Objects.isNull(opsDoItem.getOutQty())) {
            opsDoItem.setOutQty(0);
        } else {
            if (opsDoItem.getOutQty() > 0) {
                throw Exceptions.OpsException("此发货单数量不对,不能重复分纳出，不允许继续", doid);
            }

        }
        opsDoItem.setOutQty(opsDoItem.getOutQty() + wmWTDoItemConfirmDto.getQty());
        if (!opsDoItem.getQty().equals(opsDoItem.getOutQty())) {//数量不齐部分出库 目前没有这个场景
            //throw Exceptions.OpsException("此发货单数量不对，不允许继续", doid);
            if (!DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())) {//非退货不能分纳出委托
                throw Exceptions.OpsException("此发货单数量不对，非退货不能分纳出委托。不允许继续出库", doid);
            }
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType()))) {
                throw Exceptions.OpsException("此拆分型号发货单数量不等于应发数量，不允许继续", doid);
            } else {
                if (opsDoItem.getQty() < opsDoItem.getOutQty()) {
                    throw Exceptions.OpsException("此发货单数量大于应发数量，不允许继续", DoConfirmErrorCodeEnum.ABNORMAL_ISSUE_QUANTITY.getCode());
                } else {

                    //更新do表状态以及承运商 单号
                    opsDo.setDoStatus(DoStatusEnum.PART.getStatus());//变更状态发货中
                    //委托在库退货修改doItem qty bugid:8764 c14717 20221123
                    opsDoItem.setQty(wmWTDoItemConfirmDto.getQty());
                    //bugid:8764 --------end------------------------------
                    doComfirmCommon(true,wmWTDoItemConfirmDto.getQty(), opsDo, opsDoItem, wmWTDoItemConfirmDto.getBarCode(),
                            wmWTDoItemConfirmDto.getPackageCode(), param.getVolume(), param.getWeight(), param.getSender(),
                            wmWTDoItemConfirmDto.getExpressCode(), wmWTDoItemConfirmDto.getLogisticsCode(), param.getBoxType(), null, "");
                    //数量齐 1.变更状态出库完成 2.扣减库存
                    //ops_do_item_inventory 按照此表对应的库存关系，直接扣减库存
                    OpsDoItemInventoryExample exampleDoItemInventory = new OpsDoItemInventoryExample();
                    exampleDoItemInventory.createCriteria().andDelflagEqualTo(0).andDoIdEqualTo(doid);
                    exampleDoItemInventory.setOrderByClause("inventory_table_type asc");//BUGID:8696 2022-11-17 C14717
                    List<OpsDoItemInventory> opsDoItemInventoryList = opsDoItemInventoryMapper.selectByExample(exampleDoItemInventory);
                    //out_qty 不够不做扣减
                    if (opsDoItemInventoryList == null || opsDoItemInventoryList.isEmpty()) {
                        throw Exceptions.OpsException("库存无占用", doid);
                    } else {
                        //直接扣减库存  加工单整型号不扣减库存
                        Integer wmOutQty = wmWTDoItemConfirmDto.getQty();
                        for (OpsDoItemInventory item : opsDoItemInventoryList) {
                            //物流出库数量大于 当前item 继续循环
                            if (Objects.isNull(item.getOutQty())) {
                                item.setOutQty(0);
                            }
                            if (wmOutQty - (item.getUseQty() - item.getOutQty()) > 0) {
                                wmOutQty = wmOutQty - (item.getUseQty() - item.getOutQty());
                                //bugid 8764
                                baseInventoryService.subQtyInventoryForPre(item.getInventoryId(), item.getUseQty() - item.getOutQty(), item.getUseQty(), item.getInventoryTableType(), param.getUserDto());
                                //bugid:9911 20230307 c14717
                                opsDo.setDoId(doid);
                                opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, item.getInventoryId(), item.getInventoryTableType(), item.getUseQty() - item.getOutQty(), new UserDto());

                                item.setOutQty(item.getUseQty());
                                item.setModifyTime(new Date());
                                opsDoItemInventoryMapper.updateByPrimaryKey(item);

                            } else {//物流出库数量小于等于当前Item  结束循环 使用数量扣预占 outqty扣库存
                                baseInventoryService.subQtyInventoryForPre(item.getInventoryId(), wmOutQty, item.getUseQty(), item.getInventoryTableType(), param.getUserDto());
                                item.setOutQty(item.getOutQty() + wmOutQty);
                                //bugid:8764 c14717 20221123
                                item.setUseQty(item.getOutQty());
                                //bugid:8764 --------end-------------------
                                opsDoItemInventoryMapper.updateByPrimaryKey(item);
                                opsDo.setDoId(doid);
                                opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, item.getInventoryId(), item.getInventoryTableType(), wmOutQty, new UserDto());
                                break;
                            }
                        }
                        //写入状态变更事件表-调拨\交易出库完成
                        if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                            customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, opsDo, wmWTDoItemConfirmDto);
                        } else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                            TmsMessageDto dto = new TmsMessageDto();
                            dto.setCarrierid(wmWTDoItemConfirmDto.getLogisticsCode());
                            dto.setExpressno(wmWTDoItemConfirmDto.getExpressCode());
                            dto.setHandoverTime(new Date());
                            dto.setQuantity(wmWTDoItemConfirmDto.getQty());
                            customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, opsDo, dto);
                        } else if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())) {
                            transferEventPublisher.transferOrderEvent(EventSourceEnum.INVENTORY_TRANS_ORDER_SHIPPED, opsDo, param);
                        }
                    }
                }
            }
        } else {//数量齐
            opsDo.setDoStatus(DoStatusEnum.FINISH.getStatus());//变更状态发货完成
            doComfirmCommon(true,wmWTDoItemConfirmDto.getQty(), opsDo, opsDoItem, wmWTDoItemConfirmDto.getBarCode(),
                    wmWTDoItemConfirmDto.getPackageCode(), param.getVolume(), param.getWeight(), param.getSender(),
                    wmWTDoItemConfirmDto.getExpressCode(), wmWTDoItemConfirmDto.getLogisticsCode(), param.getBoxType(), null, "");
            //子型号扣减库存
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType()))) {//拆分型号，交易出库扣减子型号库存
                List<OpsPco> pcoList = opsPcoService.GetPcolistByOrder(opsDo.getOrderId(), opsDo.getOrderItem());
                if (CollectionUtils.isEmpty(pcoList)) {
                    throw Exceptions.OpsException("无此加工单");
                }
                OpsPcoItemInventoryExample examplePcoItemInventory = new OpsPcoItemInventoryExample();
                examplePcoItemInventory.createCriteria().andDelflagEqualTo(0).andPcoIdEqualTo(pcoList.get(0).getPcoId());
                List<OpsPcoItemInventory> opsPcoItemInventoryList = pcoItemInventoryMapper.selectByExample(examplePcoItemInventory);
                //out_qty 不够不做扣减
                if (opsPcoItemInventoryList == null || opsPcoItemInventoryList.isEmpty()) {
                    throw Exceptions.OpsException("库存无占用", pcoList.get(0).getPcoId());
                } else {
                    //直接扣减库存
                    for (OpsPcoItemInventory item : opsPcoItemInventoryList) {
                        baseInventoryService.subQtyInventoryForPre(item.getInventoryId(), item.getUseQty(), item.getUseQty(), item.getInventoryTableType(), new UserDto());
                        //bugid 8730 c14717 20221121
                        item.setOutQty(item.getUseQty());
                        opsPcoItemInventoryMapper.updateByPrimaryKey(item);//
                        opsDo.setDoId(doid);
                        opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, item.getInventoryId(), item.getInventoryTableType(), item.getUseQty(), new UserDto());
                    }
                }
            } else {//其他
                //数量齐 1.变更状态出库完成 2.扣减库存
                //ops_do_item_inventory 按照此表对应的库存关系，直接扣减库存

                OpsDoItemInventoryExample exampleDoItemInventory = new OpsDoItemInventoryExample();
                exampleDoItemInventory.createCriteria().andDelflagEqualTo(0).andDoIdEqualTo(doid);
                exampleDoItemInventory.setOrderByClause("inventory_table_type asc");//BUGID:8696 2022-11-17 C14717
                List<OpsDoItemInventory> opsDoItemInventoryList = opsDoItemInventoryMapper.selectByExample(exampleDoItemInventory);
                //out_qty 不够不做扣减
                if (opsDoItemInventoryList == null || opsDoItemInventoryList.isEmpty()) {
                    throw Exceptions.OpsException("库存无占用", doid);
                } else {
                    //直接扣减库存  加工单整型号不扣减库存
                    for (OpsDoItemInventory item : opsDoItemInventoryList) {
                        baseInventoryService.subQtyInventoryForPre(item.getInventoryId(), item.getUseQty(), item.getUseQty(), item.getInventoryTableType(), param.getUserDto());
                        //bugid 8730 c14717 20221121
                        item.setOutQty(item.getUseQty());
                        opsDoItemInventoryMapper.updateByPrimaryKey(item);
                        opsDo.setDoId(doid);
                        opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, item.getInventoryId(), item.getInventoryTableType(), item.getUseQty(), new UserDto());
                    }
                }
                //写入状态变更事件表-调拨\交易出库完成
                if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, opsDo, wmWTDoItemConfirmDto);
                } else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                    TmsMessageDto msg = new TmsMessageDto();
                    msg.setCarrierid(wmWTDoItemConfirmDto.getLogisticsCode());
                    msg.setExpressno(wmWTDoItemConfirmDto.getExpressCode());
                    msg.setHandoverTime(new Date());
                    msg.setQuantity(wmWTDoItemConfirmDto.getQty());
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, opsDo, msg);
                } else if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())) {
                    transferEventPublisher.transferOrderEvent(EventSourceEnum.INVENTORY_TRANS_ORDER_SHIPPED, opsDo, param);
                }
            }
        }
        return "成功";
    }
    /**
     * 出库发货确认接口
     */
    @Override
    public String wmDoConfirm(WmDoConfirmDto param) throws OpsException {
        if (Objects.isNull(param)) {
            throw Exceptions.OpsException("参数解析失败--WmDoConfirmDto", DoConfirmErrorCodeEnum.PARAMETER.getCode());
        }
        if (Objects.isNull(param.getUserDto())) {
            UserDto userDto = new UserDto();
            userDto.setUserName("wmUser");
            param.setUserDto(userDto);
        }
        WmDoItemConfirmDto wmDoItemConfirmDto = param.getItems();//doItem
        if (wmDoItemConfirmDto == null) {
            throw Exceptions.OpsException("参数解析失败--wmDoItemConfirmDto或为空", DoConfirmErrorCodeEnum.PARAMETER.getCode());
        }
        String doid = param.getDeliveryOrderCode();
        if (StringUtils.isEmpty(doid)) {
            throw Exceptions.OpsException("出库指令为空", DoConfirmErrorCodeEnum.PARAMETER.getCode());
        }
        log.info("出库确认回传: " + doid);
        //查询do表
        OpsDo opsDo = baseDoService.getDoByDoId(doid);
        if (opsDo == null) {
            throw Exceptions.OpsException("无此发货单", DoConfirmErrorCodeEnum.NONEXISTENT_DO.getCode());
        }
        // 通知发货 分支
        Boolean notifyShipmentFlag = doPcoLogicCenterService.checkDlvEntireAssShipment(opsDo.getOrderId());
        if(notifyShipmentFlag){
            return doPcoLogicCenterService.doConfirm(param);
        }

        if (DoStatusEnum.FINISH.getStatus().equals(opsDo.getDoStatus())) {
            throw Exceptions.OpsException("此发货单已完成，不允许继续过账处理", DoConfirmErrorCodeEnum.COMPLETED.getCode());
        }
        //查询doItem
        OpsDoItemExample doItemSearchExample = new OpsDoItemExample();
        OpsDoItemExample.Criteria doItemSearchCriteria = doItemSearchExample.createCriteria();
        doItemSearchCriteria.andDoIdEqualTo(doid);
        doItemSearchCriteria.andDelflagEqualTo(0);
        List<OpsDoItem> opsDoItems = opsDoItemMapper.selectByExample(doItemSearchExample);
        //确认过只有一条记录
        OpsDoItem opsDoItem = opsDoItems.get(0);
        //计算 累加outQty数量
        opsDoItem.setOutQty(opsDoItem.getOutQty() + wmDoItemConfirmDto.getQty());
        opsDo.setCarried(wmDoItemConfirmDto.getLogisticsCode());//成承运商
        //C14717 20221019 任务号2069 BUG号 8384
        if (org.apache.commons.lang3.StringUtils.isBlank(param.getExpressCodeChild())) {
            throw Exceptions.OpsException("出库确认回传失败，子运单号为空", DoConfirmErrorCodeEnum.PARAMETER.getCode());
        }
        //C14717 20221020 BUG号 8384
        List<String> doPostList = opsDoPostDao.getOpsDoPostExpressChildNoByDoId(opsDo.getDoId());
        if (!CollectionUtils.isEmpty(doPostList)) {
            boolean repeat = doPostList.contains(param.getExpressCodeChild());
            if (repeat) {
                throw Exceptions.OpsException("出库确认回传失败，重复调用，子运单号：" + param.getExpressCodeChild(), DoConfirmErrorCodeEnum.REPEAT_CALL.getCode());
            }
        }
        //组换单仅传回执;
        if(DoTypeEnum.ZHCKOW.getType().equals(opsDo.getDoType())){
            if (opsDoItem.getQty().equals(opsDoItem.getOutQty())){//变更状态发货完成
                opsDo.setDoStatus(DoStatusEnum.FINISH.getStatus());
            }else {
                opsDo.setDoStatus(DoStatusEnum.PART.getStatus());
            }
            doComfirmCommon(false,wmDoItemConfirmDto.getQty(), opsDo, opsDoItem, wmDoItemConfirmDto.getBarCode(),
                    wmDoItemConfirmDto.getPackageCode(), param.getVolume(), param.getWeight(), param.getSender(),
                    wmDoItemConfirmDto.getExpressCode(), wmDoItemConfirmDto.getLogisticsCode(), param.getBoxType(), param.getShipTime(), param.getExpressCodeChild());
            //bugid:12889 c14717 20240112 组换单创建ro
            opsRoService.createROForPorduceV2(opsDo.getOrderId(),opsDo.getWarehouseCode(),new UserDto("doConfirmApi"),true);
            return "成功";
        }

        opsDo.setExpressCode(param.getExpressCodeChild());//
        if (!opsDoItem.getQty().equals(opsDoItem.getOutQty())) {//数量不齐部分出库
            //throw Exceptions.OpsException("此发货单数量不对，不允许继续", doid);
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType()))) {
                //throw Exceptions.OpsException("此拆分型号发货单数量不等于应发数量，不允许继续", doid);
                if (opsDoItem.getQty() < opsDoItem.getOutQty()) {
                    throw Exceptions.OpsException("此发货单数量大于应发数量，不允许继续", DoConfirmErrorCodeEnum.ABNORMAL_ISSUE_QUANTITY.getCode());
                } else {
                    //更新do表状态以及承运商 单号
                    opsDo.setDoStatus(DoStatusEnum.PART.getStatus());//变更状态发货中
                    doComfirmCommon(false,wmDoItemConfirmDto.getQty(), opsDo, opsDoItem, wmDoItemConfirmDto.getBarCode(),
                            wmDoItemConfirmDto.getPackageCode(), param.getVolume(), param.getWeight(), param.getSender(),
                            wmDoItemConfirmDto.getExpressCode(), wmDoItemConfirmDto.getLogisticsCode(), param.getBoxType(), param.getShipTime(), param.getExpressCodeChild());

                    List<OpsPco> pcoList = opsPcoService.GetPcolistByOrder(opsDo.getOrderId(), opsDo.getOrderItem());
                    if (CollectionUtils.isEmpty(pcoList)) {
                        throw Exceptions.OpsException("无此加工单", DoConfirmErrorCodeEnum.NONEXISTENT_PCO.getCode());
                    }
                    OpsPcoItemInventoryExample examplePcoItemInventory = new OpsPcoItemInventoryExample();
                    examplePcoItemInventory.createCriteria().andDelflagEqualTo(0).andPcoIdEqualTo(pcoList.get(0).getPcoId());
                    examplePcoItemInventory.setOrderByClause("inventory_table_type asc");//BUGID:8696 2022-11-17 C14717
                    List<OpsPcoItemInventory> opsPcoItemInventoryList = pcoItemInventoryMapper.selectByExample(examplePcoItemInventory);
                    List<OpsPcoItem> opsPcoItemList = opsPcoService.selectItemBypcoId(pcoList.get(0).getPcoId());
                    HashMap<String, Integer> pcoItemMap = new HashMap<String, Integer>();

                    //计算子型号和整型号数量的倍数关系，计算出子型号出库数量
                    for (OpsPcoItem pcoItem : opsPcoItemList) {
                        int pcoItemQty = pcoItem.getSubQty();
                        int doQty = opsDoItem.getQty();
                        int multipleQty = pcoItemQty / doQty;
                        pcoItemMap.put(pcoItem.getPcoId() + "-" + pcoItem.getPcoItem(), multipleQty * wmDoItemConfirmDto.getQty());
                    }
                    //out_qty 不够不做扣减
                    if (opsPcoItemInventoryList == null || opsPcoItemInventoryList.isEmpty()) {
                        //无关联关系 DoConfirmErrorCodeEnum.NON_RELATION
                        saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.NON_RELATION.getCode(), 0,
                                param.getExpressCodeChild(), param.toString(), "无关联关系，没扣库存",
                                doid, opsDoItem.getModelno(), wmDoItemConfirmDto.getQty(), param.getWarehouseCode(), "", "", pcoList.get(0).getPcoId(), "");

                    } else {
                        //直接扣减库存
                        for (OpsPcoItemInventory item : opsPcoItemInventoryList) {
                            //当前子型号库存关系和数量 备注：正常出库存拆分型号不拆数量，可能存在两条相同子型号的记录的场景为分纳入库导致；
                            if (Objects.isNull(item.getOutQty())) {//避免空指针
                                item.setOutQty(0);
                            }
                            if (item.getUseQty().equals(item.getOutQty())) {//该型号已经出完 继续下一条
                                continue;
                            }
                            int pcoSuboutQty = pcoItemMap.get(item.getPcoId() + "-" + item.getPcoItem());
                            if (pcoSuboutQty == 0) {
                                continue;
                            }
                            //子型号出库数量 - （该条库存使用数量 - 该条库存已经出库数量）
                            if (pcoSuboutQty - (item.getUseQty() - item.getOutQty()) > 0) {
                                pcoItemMap.put(item.getPcoId() + "-" + item.getPcoItem(), pcoSuboutQty - (item.getUseQty() - item.getOutQty()));
                                try {//C14717 2022-10-17 在途库存不扣减，记录异常表 禅道任务号2068
                                    if (InventoryTableTypeEnum.NORMAL.getType().equals(item.getInventoryTableType())) {
                                        baseInventoryService.subQtyAndPreDoConfirm(item.getInventoryId(), item.getUseQty() - item.getOutQty(),
                                                item.getUseQty() - item.getOutQty(), item.getInventoryTableType(), new UserDto());
                                        //bugid:9911 20230307 c14717
                                        opsDo.setDoId(doid);
                                        opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, item.getInventoryId(), item.getInventoryTableType(),
                                                item.getUseQty() - item.getOutQty(), new UserDto());
                                        //bugid:9519  c14717 2023/02/06
                                        item.setOutQty(item.getUseQty());
                                        item.setModifyTime(new Date());
                                        opsPcoItemInventoryMapper.updateByPrimaryKey(item);
                                    } else {
                                        throw Exceptions.OpsException("在途库存只记录，不扣库存");
                                    }
                                } catch (OpsException e) {
                                    //记录日志继续进行
                                    saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.HAVE_RELATION.getCode(), 0,
                                            param.getExpressCodeChild(), param.toString(), "有关联关系，没扣库存",
                                            doid, opsDoItem.getModelno(), item.getUseQty() - item.getOutQty()
                                            , param.getWarehouseCode(), item.getInventoryId() + "", item.getInventoryTableType(), item.getPcoId(), item.getPcoItem() + "");
                                }


                            } else {//物流出库数量小于等于当前Item  结束循环
                                if ((pcoSuboutQty - (item.getUseQty() - item.getOutQty())) < 0) {
                                    pcoItemMap.put(item.getPcoId() + "-" + item.getPcoItem(), 0);
                                } else {
                                    pcoItemMap.put(item.getPcoId() + "-" + item.getPcoItem(), pcoSuboutQty - (item.getUseQty() - item.getOutQty()));
                                }
                                try {//C14717 2022-10-17 在途库存不扣减，记录异常表 禅道任务号2068
                                    if (InventoryTableTypeEnum.NORMAL.getType().equals(item.getInventoryTableType())) {
                                        baseInventoryService.subQtyAndPreDoConfirm(item.getInventoryId(), pcoSuboutQty, pcoSuboutQty,
                                                item.getInventoryTableType(), param.getUserDto());
                                        //bugid:9911 20230307 c14717
                                        opsDo.setDoId(doid);
                                        opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, item.getInventoryId(), item.getInventoryTableType(), pcoSuboutQty, new UserDto());
                                        //bugid:9519  c14717 2023/02/06
                                        item.setOutQty(item.getOutQty() + pcoSuboutQty);
                                        item.setModifyTime(new Date());
                                        opsPcoItemInventoryMapper.updateByPrimaryKey(item);
                                    } else {
                                        throw Exceptions.OpsException("在途库存只记录，不扣库存");
                                    }
                                } catch (OpsException e) {
                                    //记录日志继续进行
                                    saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.HAVE_RELATION.getCode(), 0,
                                            param.getExpressCodeChild(), param.toString(), "有关联关系，没扣库存",
                                            doid, opsDoItem.getModelno(), pcoSuboutQty, param.getWarehouseCode(),
                                            item.getInventoryId() + "", item.getInventoryTableType(), item.getPcoId(), item.getPcoItem() + "");
                                }
                                continue;
                            }
                        }
                    }
                    //写入状态变更事件表-调拨\交易出库完成
                    if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                        customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, opsDo, wmDoItemConfirmDto);
                    } else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                        Integer splitNo = DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) ? null : opsDo.getNum();
                        TmsMessageDto msg = new TmsMessageDto();
                        msg.setCarrierid(opsDo.getCarried());
                        msg.setExpressno(wmDoItemConfirmDto.getExpressCode());
                        //bugid:9284 c14717 20230107
                        msg.setHandoverTime(param.getShipTime());
                        msg.setQuantity(wmDoItemConfirmDto.getQty());
                        customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, opsDo, msg);
                    } else if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())) {
                        transferEventPublisher.transferOrderEvent(EventSourceEnum.INVENTORY_TRANS_ORDER_SHIPPED, opsDo, param);
                    }
                }
            } else {
                if (opsDoItem.getQty() < opsDoItem.getOutQty()) {
                    throw Exceptions.OpsException("此发货单数量大于应发数量，不允许继续", DoConfirmErrorCodeEnum.ABNORMAL_ISSUE_QUANTITY.getCode());
                } else {
                    //更新do表状态以及承运商 单号
                    opsDo.setDoStatus(DoStatusEnum.PART.getStatus());//变更状态发货中
                    doComfirmCommon(false,wmDoItemConfirmDto.getQty(), opsDo, opsDoItem, wmDoItemConfirmDto.getBarCode(),
                            wmDoItemConfirmDto.getPackageCode(), param.getVolume(), param.getWeight(), param.getSender(),
                            wmDoItemConfirmDto.getExpressCode(), wmDoItemConfirmDto.getLogisticsCode(), param.getBoxType(), param.getShipTime(), param.getExpressCodeChild());
                    //数量齐 1.变更状态出库完成 2.扣减库存
                    //ops_do_item_inventory 按照此表对应的库存关系，直接扣减库存
                    OpsDoItemInventoryExample exampleDoItemInventory = new OpsDoItemInventoryExample();
                    exampleDoItemInventory.createCriteria().andDelflagEqualTo(0).andDoIdEqualTo(doid);
                    if(DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())){
                        exampleDoItemInventory.setOrderByClause("inventory_table_type asc");//BUGID:8696 2022-11-17 C14717
                    }else {
                        // bugid:15013 2024-08-28 c14717
                        exampleDoItemInventory.setOrderByClause("inventory_table_type asc, inventory_id asc");
                    }
                    List<OpsDoItemInventory> opsDoItemInventoryList = opsDoItemInventoryMapper.selectByExample(exampleDoItemInventory);
                    //out_qty 不够不做扣减
                    if (opsDoItemInventoryList == null || opsDoItemInventoryList.isEmpty()) {
                        //记录日志继续进行
                        saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.NON_RELATION.getCode(), 0,
                                param.getExpressCodeChild(), param.toString(), "无关联关系，没扣库存",
                                doid, opsDoItem.getModelno(), wmDoItemConfirmDto.getQty(), param.getWarehouseCode(), "", "", "", "");
                    } else {
                        //直接扣减库存  加工单整型号不扣减库存
                        Integer wmOutQty = wmDoItemConfirmDto.getQty();
                        for (OpsDoItemInventory item : opsDoItemInventoryList) {
                            //物流出库数量大于 当前item 继续循环
                            if (Objects.isNull(item.getOutQty())) {
                                item.setOutQty(0);
                            }
                            if (item.getUseQty().equals(item.getOutQty())) {//该型号已经出完 继续下一条
                                continue;
                            }
                            //wms 出库数量 减 （当前关联关系未出数量）
                            if (wmOutQty - (item.getUseQty() - item.getOutQty()) > 0) {
                                wmOutQty = wmOutQty - (item.getUseQty() - item.getOutQty());
                                try {//C14717 2022-10-17 在途库存不扣减，记录异常表 禅道任务号2068
                                    if (InventoryTableTypeEnum.NORMAL.getType().equals(item.getInventoryTableType())) {
                                        baseInventoryService.subQtyAndPreDoConfirm(item.getInventoryId(), item.getUseQty() - item.getOutQty(),
                                                item.getUseQty() - item.getOutQty(), item.getInventoryTableType(), param.getUserDto());
                                        //bugid:9911 20230307 c14717
                                        opsDo.setDoId(doid);
                                        opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, item.getInventoryId(), item.getInventoryTableType(), item.getUseQty() - item.getOutQty(), new UserDto());
                                        //bugid:9519  c14717 2023/02/06
                                        item.setOutQty(item.getUseQty());
                                        item.setModifyTime(new Date());
                                        opsDoItemInventoryMapper.updateByPrimaryKey(item);
                                    } else {
                                        throw Exceptions.OpsException("在途库存只记录，不扣库存");
                                    }
                                } catch (OpsException e) {
                                    //记录日志继续进行
                                    saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.HAVE_RELATION.getCode(), 0,
                                            param.getExpressCodeChild(), param.toString(), "有关联关系，没扣库存",
                                            doid, opsDoItem.getModelno(), item.getUseQty() - item.getOutQty()
                                            , param.getWarehouseCode(), item.getInventoryId() + "", item.getInventoryTableType(), "", "");
                                }
                            } else {//物流出库数量小于等于当前Item  结束循环
                                if (wmOutQty == 0) {//todo test数量已经出完
                                    break;
                                }
                                try {//C14717 2022-10-17 在途库存不扣减，记录异常表 禅道任务号2068
                                    if (InventoryTableTypeEnum.NORMAL.getType().equals(item.getInventoryTableType())) {
                                        baseInventoryService.subQtyAndPreDoConfirm(item.getInventoryId(), wmOutQty, wmOutQty, item.getInventoryTableType(), param.getUserDto());
                                        //bugid:9911 20230307 c14717
                                        opsDo.setDoId(doid);
                                        opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, item.getInventoryId(), item.getInventoryTableType(), wmOutQty, new UserDto());
                                        //bugid:9519  c14717 2023/02/06
                                        item.setOutQty(item.getOutQty() + wmOutQty);
                                        item.setModifyTime(new Date());
                                        opsDoItemInventoryMapper.updateByPrimaryKey(item);
                                    } else {
                                        throw Exceptions.OpsException("在途库存只记录，不扣库存");
                                    }
                                } catch (OpsException e) {
                                    //记录日志继续进行
                                    saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.HAVE_RELATION.getCode(), 0,
                                            param.getExpressCodeChild(), param.toString(), "有关联关系，没扣库存",
                                            doid, opsDoItem.getModelno(), wmOutQty, param.getWarehouseCode(),
                                            item.getInventoryId() + "", item.getInventoryTableType(), "", "");
                                }
                                break;
                            }
                        }
                    }
                    //写入状态变更事件表-调拨\交易出库完成
                    if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                        customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, opsDo, wmDoItemConfirmDto);
                    } else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                        Integer splitNo = DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) ? null : opsDo.getNum();
                        TmsMessageDto msg = new TmsMessageDto();
                        msg.setCarrierid(wmDoItemConfirmDto.getLogisticsCode());
                        msg.setExpressno(wmDoItemConfirmDto.getExpressCode());
                        //bugid:9284 c14717 20230107
                        msg.setHandoverTime(param.getShipTime());
                        msg.setQuantity(wmDoItemConfirmDto.getQty());
                        customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, opsDo, msg);
                    } else if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())) {
                        transferEventPublisher.transferOrderEvent(EventSourceEnum.INVENTORY_TRANS_ORDER_SHIPPED, opsDo, param);
                    }
                }
            }
        } else {//数量齐
            opsDo.setDoStatus(DoStatusEnum.FINISH.getStatus());//变更状态发货完成
            doComfirmCommon(false,wmDoItemConfirmDto.getQty(), opsDo, opsDoItem, wmDoItemConfirmDto.getBarCode(),
                    wmDoItemConfirmDto.getPackageCode(), param.getVolume(), param.getWeight(), param.getSender(),
                    wmDoItemConfirmDto.getExpressCode(), wmDoItemConfirmDto.getLogisticsCode(), param.getBoxType(), param.getShipTime(), param.getExpressCodeChild());
            //子型号扣减库存
            if (DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) && (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType()))) {//拆分型号，交易出库扣减子型号库存
                List<OpsPco> pcoList = opsPcoService.GetPcolistByOrder(opsDo.getOrderId(), opsDo.getOrderItem());
                if (CollectionUtils.isEmpty(pcoList)) {
                    throw Exceptions.OpsException("无此加工单", DoConfirmErrorCodeEnum.NONEXISTENT_PCO.getCode());
                }
                OpsPcoItemInventoryExample examplePcoItemInventory = new OpsPcoItemInventoryExample();
                examplePcoItemInventory.createCriteria().andDelflagEqualTo(0).andPcoIdEqualTo(pcoList.get(0).getPcoId());
                examplePcoItemInventory.setOrderByClause("inventory_table_type asc");//BUGID:8696 2022-11-17 C14717
                List<OpsPcoItemInventory> opsPcoItemInventoryList = pcoItemInventoryMapper.selectByExample(examplePcoItemInventory);
                List<OpsPcoItem> opsPcoItemList = opsPcoService.selectItemBypcoId(pcoList.get(0).getPcoId());
                HashMap<String, Integer> pcoItemMap = new HashMap<String, Integer>();
                //计算子型号和整型号数量的倍数关系，计算出子型号出库数量
                for (OpsPcoItem pcoItem : opsPcoItemList) {
                    int pcoItemQty = pcoItem.getSubQty();
                    int doQty = opsDoItem.getQty();
                    int multipleQty = pcoItemQty / doQty;
                    pcoItemMap.put(pcoItem.getPcoId() + "-" + pcoItem.getPcoItem(), multipleQty * wmDoItemConfirmDto.getQty());
                }
                //out_qty 不够不做扣减
                if (opsPcoItemInventoryList == null || opsPcoItemInventoryList.isEmpty()) {
                    //记录日志继续进行 //无关联关系 DoConfirmErrorCodeEnum.NON_RELATION
                    saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.NON_RELATION.getCode(), 0,
                            param.getExpressCodeChild(), param.toString(), "无关联关系，没扣库存",
                            doid, opsDoItem.getModelno(), wmDoItemConfirmDto.getQty(), param.getWarehouseCode(), "", "", pcoList.get(0).getPcoId(), "");
                } else {
                    //直接扣减库存
                    for (OpsPcoItemInventory item : opsPcoItemInventoryList) {
                        //当前子型号库存关系和数量 备注：正常出库存拆分型号不拆数量，可能存在两条相同子型号的记录的场景为分纳入库导致；
                        if (Objects.isNull(item.getOutQty())) {//避免空指针
                            item.setOutQty(0);
                        }
                        if (item.getUseQty().equals(item.getOutQty())) {//该型号已经出完 继续下一条
                            continue;
                        }
                        int pcoSuboutQty = pcoItemMap.get(item.getPcoId() + "-" + item.getPcoItem());
                        if (pcoSuboutQty == 0) {
                            continue;
                        }
                        //子型号出库数量 - （该条库存使用数量 - 该条库存已经出库数量）
                        if (pcoSuboutQty - (item.getUseQty() - item.getOutQty()) > 0) {
                            pcoItemMap.put(item.getPcoId() + "-" + item.getPcoItem(), pcoSuboutQty - (item.getUseQty() - item.getOutQty()));
                            try {//C14717 2022-10-17 在途库存不扣减，记录异常表 禅道任务号2068
                                if (InventoryTableTypeEnum.NORMAL.getType().equals(item.getInventoryTableType())) {
                                    baseInventoryService.subQtyAndPreDoConfirm(item.getInventoryId(), item.getUseQty() - item.getOutQty(),
                                            item.getUseQty() - item.getOutQty(), item.getInventoryTableType(), new UserDto());
                                    //bugid:9911 20230307 c14717
                                    opsDo.setDoId(doid);
                                    opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, item.getInventoryId(), item.getInventoryTableType(),
                                            item.getUseQty() - item.getOutQty(), new UserDto());
                                    //bugid:9519  c14717 2023/02/06
                                    item.setOutQty(item.getUseQty());
                                    item.setModifyTime(new Date());
                                    opsPcoItemInventoryMapper.updateByPrimaryKey(item);
                                } else {
                                    throw Exceptions.OpsException("在途库存只记录，不扣库存");
                                }
                            } catch (OpsException e) {
                                //记录日志继续进行
                                saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.HAVE_RELATION.getCode(), 0,
                                        param.getExpressCodeChild(), param.toString(), "有关联关系，没扣库存",
                                        doid, opsDoItem.getModelno(), item.getUseQty() - item.getOutQty(), param.getWarehouseCode(),
                                        item.getInventoryId() + "", item.getInventoryTableType(), item.getPcoId(), item.getPcoItem() + "");
                            }
                        } else {//物流出库数量小于等于当前Item  结束循环
                            if ((pcoSuboutQty - (item.getUseQty() - item.getOutQty())) < 0) {
                                pcoItemMap.put(item.getPcoId() + "-" + item.getPcoItem(), 0);
                            } else {
                                pcoItemMap.put(item.getPcoId() + "-" + item.getPcoItem(), pcoSuboutQty - (item.getUseQty() - item.getOutQty()));
                            }
                            try {//C14717 2022-10-17 在途库存不扣减，记录异常表 禅道任务号2068
                                if (InventoryTableTypeEnum.NORMAL.getType().equals(item.getInventoryTableType())) {
                                    baseInventoryService.subQtyAndPreDoConfirm(item.getInventoryId(), pcoSuboutQty, pcoSuboutQty,
                                            item.getInventoryTableType(), param.getUserDto());
                                    //bugid:9911 20230307 c14717
                                    opsDo.setDoId(doid);
                                    opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, item.getInventoryId(), item.getInventoryTableType(), pcoSuboutQty, new UserDto());
                                    //bugid:9519  c14717 2023/02/06
                                    item.setOutQty(item.getOutQty() + pcoSuboutQty);
                                    item.setModifyTime(new Date());
                                    opsPcoItemInventoryMapper.updateByPrimaryKey(item);
                                } else {
                                    throw Exceptions.OpsException("在途库存只记录，不扣库存");
                                }
                            } catch (OpsException e) {
                                //记录日志继续进行
                                saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.HAVE_RELATION.getCode(), 0,
                                        param.getExpressCodeChild(), param.toString(), "有关联关系，没扣库存",
                                        doid, item.getPcoItem() + "", pcoSuboutQty, param.getWarehouseCode(),
                                        item.getInventoryId() + "", item.getInventoryTableType(), item.getPcoId(), item.getPcoItem() + "");
                            }
                            continue;
                        }
                    }
                }
                //写入状态变更事件表-调拨\交易出库完成
                if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, opsDo, wmDoItemConfirmDto);
                } else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                    Integer splitNo = DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) ? null : opsDo.getNum();
                    TmsMessageDto msg = new TmsMessageDto();
                    msg.setCarrierid(opsDo.getCarried());
                    msg.setExpressno(wmDoItemConfirmDto.getExpressCode());
                    //bugid:9284 c14717 20230107
                    msg.setHandoverTime(param.getShipTime());
                    msg.setQuantity(wmDoItemConfirmDto.getQty());
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, opsDo, msg);
                } else if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())) {
                    transferEventPublisher.transferOrderEvent(EventSourceEnum.INVENTORY_TRANS_ORDER_SHIPPED, opsDo, param);
                }
            } else {//其他
                //数量齐 1.变更状态出库完成 2.扣减库存
                //ops_do_item_inventory 按照此表对应的库存关系，直接扣减库存
                OpsDoItemInventoryExample exampleDoItemInventory = new OpsDoItemInventoryExample();
                exampleDoItemInventory.createCriteria().andDelflagEqualTo(0).andDoIdEqualTo(doid);
                if(DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())){
                    exampleDoItemInventory.setOrderByClause("inventory_table_type asc");//BUGID:8696 2022-11-17 C14717
                }else {
                    // bugid:15013 2024-08-28 c14717
                    exampleDoItemInventory.setOrderByClause("inventory_table_type asc, inventory_id asc");
                }
                List<OpsDoItemInventory> opsDoItemInventoryList = opsDoItemInventoryMapper.selectByExample(exampleDoItemInventory);
                //out_qty 不够不做扣减
                if (opsDoItemInventoryList == null || opsDoItemInventoryList.isEmpty()) {
                    //throw Exceptions.OpsException("库存无占用", doid);
                    //无关联关系 DoConfirmErrorCodeEnum.NON_RELATION
                    saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.NON_RELATION.getCode(), 0,
                            param.getExpressCodeChild(), param.toString(), "无关联关系，没扣库存",
                            doid, opsDoItem.getModelno(), wmDoItemConfirmDto.getQty(), param.getWarehouseCode(), "", "", "", "");
                } else {
                    //直接扣减库存  加工单整型号不扣减库存
                    Integer wmOutQty = wmDoItemConfirmDto.getQty();
                    for (OpsDoItemInventory item : opsDoItemInventoryList) {
                        if (Objects.isNull(item.getOutQty())) {
                            item.setOutQty(0);
                        }
                        //bugid:9911 20230307 c14717
                        if (item.getUseQty().equals(item.getOutQty())) {//该型号已经出完 继续下一条
                            continue;
                        }
                        //物流出库数量大于 当前item 继续循环
                        if (wmOutQty - (item.getUseQty() - item.getOutQty()) > 0) {
                            wmOutQty = wmOutQty - (item.getUseQty() - item.getOutQty());
                            try {//C14717 2022-10-17 在途库存不扣减，记录异常表 禅道任务号2068
                                if (InventoryTableTypeEnum.NORMAL.getType().equals(item.getInventoryTableType())) {
                                    baseInventoryService.subQtyAndPreDoConfirm(item.getInventoryId(), item.getUseQty() - item.getOutQty(),
                                            item.getUseQty() - item.getOutQty(), item.getInventoryTableType(), param.getUserDto());
                                    //bugid:9911 20230307 c14717
                                    opsDo.setDoId(doid);
                                    opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, item.getInventoryId(), item.getInventoryTableType(), item.getUseQty() - item.getOutQty(), new UserDto());
                                    //bugid:9519  c14717 2023/02/06
                                    item.setOutQty(item.getUseQty());
                                    item.setModifyTime(new Date());
                                    opsDoItemInventoryMapper.updateByPrimaryKey(item);
                                } else {
                                    throw Exceptions.OpsException("在途库存只记录，不扣库存");
                                }
                            } catch (OpsException e) {
                                //记录日志继续进行
                                saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.HAVE_RELATION.getCode(), 0,
                                        param.getExpressCodeChild(), param.toString(), "出库确认回传成功，有关联关系，没扣库存",
                                        doid, opsDoItem.getModelno(), item.getUseQty() - item.getOutQty(), param.getWarehouseCode(),
                                        item.getInventoryId() + "", item.getInventoryTableType(), "", "");
                            }
                        } else {//物流出库数量小于等于当前Item  结束循环
                            if (wmOutQty == 0) {//todo test数量已经出完
                                break;
                            }
                            try {//C14717 2022-10-17 在途库存不扣减，记录异常表 禅道任务号2068
                                if (InventoryTableTypeEnum.NORMAL.getType().equals(item.getInventoryTableType())) {
                                    baseInventoryService.subQtyAndPreDoConfirm(item.getInventoryId(), wmOutQty, wmOutQty, item.getInventoryTableType(), param.getUserDto());
                                    //bugid:9911 20230307 c14717
                                    opsDo.setDoId(doid);
                                    opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, item.getInventoryId(), item.getInventoryTableType(), wmOutQty, new UserDto());
                                    //bugid:9519  c14717 2023/02/06
                                    item.setOutQty(item.getOutQty() + wmOutQty);
                                    item.setModifyTime(new Date());
                                    opsDoItemInventoryMapper.updateByPrimaryKey(item);
                                } else {
                                    throw Exceptions.OpsException("在途库存只记录，不扣库存");
                                }
                            } catch (OpsException e) {
                                //记录日志继续进行
                                saveDOopsExceptionHandle("doConfirm", DoConfirmErrorCodeEnum.HAVE_RELATION.getCode(), 0,
                                        param.getExpressCodeChild(), param.toString(), "有关联关系，没扣库存",
                                        doid, opsDoItem.getModelno(), wmOutQty, param.getWarehouseCode(),
                                        item.getInventoryId() + "", item.getInventoryTableType(), "", "");
                            }
                            break;
                        }
                    }
                }
                //写入状态变更事件表-调拨\交易出库完成
                if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, opsDo, wmDoItemConfirmDto);
                } else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                    Integer splitNo = DoSourceEnum.ASSModelNo.getType().equals(opsDo.getDoSource()) ? null : opsDo.getNum();
                    TmsMessageDto msg = new TmsMessageDto();
                    msg.setCarrierid(opsDo.getCarried());
                    msg.setExpressno(wmDoItemConfirmDto.getExpressCode());//20221018 传运单号
                    //bugid:9284 c14717 20230107
                    msg.setHandoverTime(param.getShipTime());
                    msg.setQuantity(wmDoItemConfirmDto.getQty());
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_FINISHED, opsDo, msg);
                } else if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())) {
                    transferEventPublisher.transferOrderEvent(EventSourceEnum.INVENTORY_TRANS_ORDER_SHIPPED, opsDo, param);
                }
            }
        }
        if(DoTypeEnum.ZHCK.getType().equals(opsDo.getDoType())){
            //bugid:12889 c14717 20240112 组换单创建ro
            opsRoService.createROForPorduceV2(opsDo.getOrderId(),opsDo.getWarehouseCode(),new UserDto("doConfirmApi"),false);
        }
        return "成功";
    }


    // 5.4状态回传
    @Override
    public String wmDoStatus(WmDoStatusDto param) throws OpsException {
        Date createDate = new Date();
        if (Objects.isNull(param)) {
            throw Exceptions.OpsException("参数解析失败--WmDoConfirmDto");
        }
        log.info("发运确认回传: " + param.getDeliveryOrderCode());

        String doid = param.getDeliveryOrderCode();
        if (StringUtils.isEmpty(doid)) {
            throw Exceptions.OpsException("物流指令号不能为空");
        }
        String subId = doid.substring(0, 3);
        if ("PCO".equals(subId)) {
            //查询do表
            OpsPco opsPco = opsPcoService.getPcoByPcoId(doid);
            if(opsPco==null){
                throw Exceptions.OpsException("无此加工单", doid);
            }

            //更新do表状态
            OpsDo opsDo = baseDoService.getJYCKByOrder(opsPco.getOrderId(), opsPco.getOrderItem(), opsPco.getNum(), DoSourceEnum.ASSModelNo);
            OpsDo updateDo = new OpsDo();
            updateDo.setId(opsDo.getId());
            WmStatusEnum wmsStatusEnum = WmStatusEnum.parse(param.getProcessStatus());
            if(wmsStatusEnum!=null){
                updateDo.setWmsStatus(wmsStatusEnum.getCode());
            }
            updateDo.setModifier("wmDoStatus");
            updateDo.setModifyTime(new Date());
            updateDo.setVersion(opsDo.getVersion() + 1);
            opsDoMapper.updateByPrimaryKeySelective(updateDo);
            // 插入事件
            customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_OPERATION, opsDo, param);
        } else {
            //查询do表
            OpsDo opsDo = baseDoService.getDoByDoId(doid);
            if (opsDo == null) {
                throw Exceptions.OpsException("无此发货单", doid);
            }

            //更新do表状态
            OpsDo updateDo = new OpsDo();
            updateDo.setId(opsDo.getId());
            WmStatusEnum wmsStatusEnum = WmStatusEnum.parse(param.getProcessStatus());
            if(wmsStatusEnum!=null){
                updateDo.setWmsStatus(wmsStatusEnum.getCode());
            }
            updateDo.setModifier("wmDoStatus");
            updateDo.setModifyTime(new Date());
            updateDo.setVersion(opsDo.getVersion() + 1);
            opsDoMapper.updateByPrimaryKeySelective(updateDo);
            //写入状态变更事件表-调拨\交易出库中
            if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                transferEventPublisher.transferOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_OPERATION, opsDo, param);
            } else if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                transferEventPublisher.transferOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_OPERATION, opsDo, param);
            } else if (DoTypeEnum.TKCK.getType().equals(opsDo.getDoType())) {
                transferEventPublisher.transferOrderEvent(EventSourceEnum.INVENTORY_TRANS_ORDER_OUTING, opsDo, param);
            }
        }
        return "成功";
    }


    /**
     * 出库确认回传异常表
     */
    @Override
    public void saveDOopsExceptionHandle(String apiName, String errType, int status, String expChildNo, String inputMsg, String outputMsg,
                                         String doId, String modelNo, int qty, String warehouseCode, String inventoryId,
                                         String inventoryType, String pcoId, String pcoItem) {

        OpsExceptionHandle opsExceptionHandle = new OpsExceptionHandle();
        opsExceptionHandle.setBusinessType("Do");
        /*1.参数错误
        2.无此法货单
        3.发货单已完成，不允许继续过账
        4.发货单数量大于应发数量，不允许继续
        5.无此加工单
        6.重复调用接口
        7.出库确认回传成功，无库存关联关系
        8.出库确认成功，有关联关系无法扣库存，a.在途库存；b.并发扣库存异常；*/
        opsExceptionHandle.setErrType(errType);//错误类型
        opsExceptionHandle.setApiName(apiName); //接口名字
        opsExceptionHandle.setStatus(status);//状态（0：待处理，1：已处理，9：无需处理）
        opsExceptionHandle.setMsgId(expChildNo);//承运商子单号
        opsExceptionHandle.setInputMsg(inputMsg);//输入报文
        opsExceptionHandle.setOutputMsg(outputMsg);//返回报文
        opsExceptionHandle.setParameter1(doId);
        opsExceptionHandle.setParameter2(modelNo);
        opsExceptionHandle.setParameter3(qty + "");
        opsExceptionHandle.setParameter4(warehouseCode);
        opsExceptionHandle.setParameter5(inventoryId);
        opsExceptionHandle.setParameter6(inventoryType);
        opsExceptionHandle.setParameter7(pcoId);
        opsExceptionHandle.setParameter8(pcoItem);
        opsExceptionHandle.setCreateTime(new Date());
        opsExceptionHandle.setCreateUser(apiName);
        opsExceptionHandleMapper.insertSelective(opsExceptionHandle);
    }


    /**
     * 出库准备中，指令已下发，但是物流还没有动作
     * 供指令下发接口调用
     * @author C12961
     * @date 2022/3/5 10:01
     */
    @Override
    public int prepareOperation(String doId) throws OpsException {
        OpsDo opsDo = baseDoService.getDoByDoId(doId);
        if (opsDo != null) {
            if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_COMMAND_DISPATCHED, opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()));
                return 1;
            } else if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_COMMAND_DISPATCHED, opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()));
                return 1;
            }
        }
        return 0;
    }

    /**
     * 物流开始出库作业，波次号，物流组波次时间，物流预计出库时间
     *
     * @author C12961
     * @date 2022/3/5 10:01
     */
    @Override
    public void startOperation(String doId, String waveNo, String waveDateStr, String expectedDeliveryDateStr) throws OpsException {
        OpsDo opsDo = baseDoService.getDoByDoId(doId);
        if (opsDo == null) {
            return;
        }
        Date waveDate = null;
        Date expectedDeliveryDate = null;
        if(!StringUtils.isEmpty(waveDateStr)){
            waveDate = DateUtil.stringToDate(waveDateStr);
        }
        if(!StringUtils.isEmpty(expectedDeliveryDateStr)){
            expectedDeliveryDate = DateUtil.stringToDate(expectedDeliveryDateStr);
        }
        // 加保护，如果有预计出库时间，则填预计出库时间，如果没有预计出库时间，则填组波次时间代填
        else{
            expectedDeliveryDate = waveDate;
        }
        OpsDoExample example = new OpsDoExample();
        example.createCriteria().andDelflagEqualTo(0).andDoIdEqualTo(doId);
        OpsDo update = new OpsDo();
        update.setWmsStatus(WmStatusEnum.GROUP_BATCH.getCode());
        update.setWaveTime(waveDate);
        update.setWmsExpectedDeliveryDate(expectedDeliveryDate);
        opsDoMapper.updateByExampleSelective(update, example);
        StatusParamDto paramDto = new StatusParamDto();
        paramDto.setDoId(opsDo.getDoId());
        paramDto.setDoType(opsDo.getDoType());
        paramDto.setDate(expectedDeliveryDate == null ? null : DateUtil.dateToDateString(expectedDeliveryDate));
        paramDto.setInvoiceNo(waveNo);
        if (DoTypeEnum.JYCK.getType().equals(paramDto.getDoType())) {
            paramDto.setWarehouseCode(opsDo.getGatherWarehouseCode());
            customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_WAVE_CONFIRM, opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()), paramDto);
        }
        if (DoTypeEnum.DBCK.getType().equals(paramDto.getDoType())) {
            paramDto.setWarehouseCode(opsDo.getWarehouseCode());
            customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_WAVE_CONFIRM, opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()), paramDto);
        }
    }

    @Override
    public void releaseOperation(String doId, String optTime, String waveNo) throws OpsException {
        OpsDo opsDo = baseDoService.getDoByDoId(doId);
        if (opsDo == null) {
            throw Exceptions.OpsException("无法查询到Do");
        }
        // 解析doStatus
        DoStatusEnum doStatusEnum = DoStatusEnum.parse(opsDo.getDoStatus());
        if (DoStatusEnum.WAIT != doStatusEnum && DoStatusEnum.INIT != doStatusEnum) {
            String status = "";
            if (doStatusEnum != null) {
                status = doStatusEnum.getDesc();
            }
            throw Exceptions.OpsException("指令状态异常:" + status + "不允许退波次");
        }
        opsDoStatusDao.updateDoStatus(doId, WmStatusEnum.SENT_TASK.getCode(), null,null);
        if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
            StatusParamDto paramDto = new StatusParamDto();
            paramDto.setDoId(opsDo.getDoId());
            paramDto.setDoType(opsDo.getDoType());
            paramDto.setDate(optTime);
            paramDto.setInvoiceNo(waveNo);
            customerEventPublisher.customerOrderEvent(EventSourceEnum.WAREHOUSE_DELIVERY_WAVE_CANCEL, opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()), paramDto);
        }
    }

    /**
     * 写入expdetail dopost 变更 opsDo opsDoPost
     * @param isWT
     * @param wmOutQty
     * @param opsDo
     * @param opsDoItem
     * @param barcode
     * @param packageCode
     * @param volume
     * @param weight
     * @param sender
     * @param expressCode
     * @param logisticsCode
     * @param boxType
     * @param shipDate
     * @param expressChildNo
     * @throws OpsException
     */
    @Override
    public void doComfirmCommon(boolean isWT, Integer wmOutQty, OpsDo opsDo, OpsDoItem opsDoItem, String barcode,
                                 String packageCode, String volume, String weight, String sender, String expressCode, String logisticsCode,
                                 String boxType, Date shipDate, String expressChildNo) throws OpsException {

        //插入对账表数据；
        OpsDoPost opsDoPost = new OpsDoPost();
        opsDoPost.setWarehouseCode(opsDo.getWarehouseCode());
        opsDoPost.setDoId(opsDo.getDoId());
        opsDoPost.setCarried(opsDo.getCarried());
        opsDoPost.setExpressCode(opsDo.getExpressCode());
        opsDoPost.setQty(wmOutQty);
        opsDoPost.setDoType(opsDo.getDoType());
        //拼接字符串

        opsDoPost.setBarcode(barcode);
        opsDoPost.setPackageCode(packageCode);
        opsDoPost.setDelflag(0);//删除标志
        opsDoPost.setCreTime(new Date());
        opsDoPost.setCreator("wms");
        opsDoPost.setModelno(opsDoItem.getModelno());
        opsDoPost.setExpressChildNo(expressChildNo);//C14717 20221020 BUG号 8384

        //opsDoPost.setMsgId(param.getMsgId());
        opsDoPostMapper.insertSelective(opsDoPost);

        if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
            Rcvdetail rcvdetail = null;
            try {
                rcvdetail = wmRouterOrderService.getRcvdetail(opsDo.getOrderId(), opsDo.getOrderItem());
            } catch (OpsException e) {
                log.debug("发货确认存expdetail", e);
            }
            Rcvmaster rcvmaster = wmRouterOrderService.getRcvmaster(opsDo.getOrderId());
            //存表 expdetail 广州订单模块用表
            Expdetail expdetail = new Expdetail();
            expdetail.setInvoiceNo(expressCode);  //opsDo
            expdetail.setDeliveryNo(opsDo.getDoId());
            expdetail.setOrderNo(opsDo.getOrderId());
            expdetail.setItemNo(Integer.parseInt(opsDo.getOrderItem()));
            if (Objects.nonNull(rcvdetail)) {
                expdetail.setOrderFno(rcvdetail.getRorderFno());
                expdetail.setOrderType((int) rcvdetail.getOrderType());
                expdetail.setStockCode(rcvdetail.getStockCode());//出库区分
                expdetail.setCorderNo(rcvdetail.getCorderNo());//客户订单号
                expdetail.setCmodelNo(rcvdetail.getCproductNo());//客户型号
                expdetail.setDlvDate(rcvdetail.getDlvDate());//客户纳期
                //todo SET ENDUSER
            }
            expdetail.setModelNo(opsDoItem.getModelno());
            expdetail.setQuantity(wmOutQty);
            expdetail.setCustomerNo(opsDo.getCustomerNo());
            expdetail.setUserNo(opsDo.getUserNo());
            expdetail.setOptCode(1);//写入状态1 --
            expdetail.setBarcode(barcode);//物流条码
            expdetail.setCaseNo(packageCode);//箱号
            if (!StringUtils.isEmpty(weight) && !"null".equals(weight)) {
                expdetail.setWeight(new BigDecimal(weight));//todo 重量KG
            }
            expdetail.setVolume(volume);//todo 长 宽 高
            expdetail.setSender(sender);//todo 发货当担
            expdetail.setBoxType(boxType);
            if (Objects.nonNull(rcvmaster)) {
                expdetail.setEndUser(rcvmaster.getEndUser());
                expdetail.setOrorderno(rcvmaster.getContractno());//合同订单号
            }
            expdetail.setSignStatus((short) 1);//签收状态 1-未签收，2已签收
            expdetail.setDlvSite(opsDo.getDlvSite());
            //expdetail.setShipDate();
            expdetail.setExpressNo(expressCode);
            expdetail.setExpressCompany(logisticsCode);
            expdetail.setWarehouseCode(opsDo.getGatherWarehouseCode());
            //expdetail.setOptCode();
            expdetail.setCreateTime(new Date());
            expdetail.setCreateUser("wms");
            if (Objects.nonNull(shipDate)) {
                expdetail.setShipDate(shipDate);//todo 发货日期
            } else {
                expdetail.setShipDate(new Date());//todo 发货日期
            }

            expdetail.setPrice(opsDoItem.getPrice());
            expdetail.setDeptNo(opsDo.getDeptNo());

            expdetail.setSignOrderNo(expressCode);//签收单号
            expdetail.setDlvAddress(opsDo.getAddress());//收货地址
            expdetail.setContactpsn(opsDo.getLinkman());//收货人
            //发货邮箱 bugid:12391 c14717 2023/10/27
            expdetail.setEmail(opsDo.getEmail());

            //bugid:12717 c14717 2023-11-28 处理zl和zt 变更签收时间，更新签收状态
            //BUGID:12900 c14717 2023-12-19 委托在库 变更签收时间，更新签收状态
            if(isWT || CarrierEnum.ZT.getCode().equals(opsDo.getCarried()) || CarrierEnum.ZL.getCode().equals(opsDo.getCarried())){
                expdetail.setSignStatus((short) 2);
                expdetail.setSignTime(expdetail.getShipDate());
            }

            expdetailMapper.insertSelective(expdetail);
        }

        //更新doItem状态以及outQty数量
        opsDoItem.setVersion(opsDoItem.getVersion() + 1);
        OpsDoItemExample opsDoItemUpdateExample = new OpsDoItemExample();
        OpsDoItemExample.Criteria opsDoItemUpdateCriteria = opsDoItemUpdateExample.createCriteria();
        opsDoItemUpdateCriteria.andDoIdEqualTo(opsDo.getDoId());//出库指令
        opsDoItemUpdateCriteria.andDelflagEqualTo(0);//删除标识
        opsDoItemUpdateCriteria.andVersionEqualTo(opsDoItem.getVersion() - 1);//并发控制
        opsDoItem.setId(null);
        opsDoItem.setDoId(null);
        int doItemRows = opsDoItemMapper.updateByExampleSelective(opsDoItem, opsDoItemUpdateExample);
        // bugid:14064 c14717 2024-04-25
        if (doItemRows < 1) {
            throw Exceptions.OpsException("doconfirm更新doItem并发异常:" + opsDoItem.getDoId(), DoConfirmErrorCodeEnum.UPDATE_DO_FAIR.getCode());
        }

        //更新do表状态以及承运商 单号
        opsDo.setModifier("doconfirm");
        opsDo.setModifyTime(new Date());
        opsDo.setVersion(opsDo.getVersion() + 1);
        OpsDoExample opsDoUpdateExample = new OpsDoExample();
        OpsDoExample.Criteria opsDoUpdateCriteria = opsDoUpdateExample.createCriteria();
        opsDoUpdateCriteria.andDoIdEqualTo(opsDo.getDoId());//出库指令
        opsDoUpdateCriteria.andVersionEqualTo(opsDo.getVersion() - 1);//version 并发条件
        opsDoUpdateCriteria.andDelflagEqualTo(0);//删除标识
        opsDo.setId(null);
        opsDo.setDoId(null);
        // bugid:14064 c14717 2024-04-25
        int doRows = opsDoMapper.updateByExampleSelective(opsDo, opsDoUpdateExample);
        if (doRows < 1) {
            throw Exceptions.OpsException("doconfirm更新do并发异常:" + opsDoItem.getDoId(), DoConfirmErrorCodeEnum.UPDATE_DO_FAIR.getCode());
        }
    }

    @Override
    public void updateDoItemQtyByDoItemId(Long opsDoItemId, Integer qty,Integer version) throws OpsException{
        OpsDoItem opsDoItem = new OpsDoItem();
        opsDoItem.setId(opsDoItemId);
        opsDoItem.setQty(qty);
        //bugid:12538 c14717 2023/11/06
        if(Objects.isNull(qty) || qty <= 0){
            throw Exceptions.OpsException("doItem数量更新异常:"+ qty);
        }
        opsDoItem.setVersion(version+1);
        opsDoItem.setModifyTime(DateUtil.getNow());
        opsDoItemMapper.updateByPrimaryKeySelective(opsDoItem);
    }

    @Override
    public void updateDoItemInvQtyById(Long opsDoItemInvId, Integer qty, Long version) throws OpsException{
        OpsDoItemInventory opsDoItemInv = new OpsDoItemInventory();
        opsDoItemInv.setId(opsDoItemInvId);
        opsDoItemInv.setUseQty(qty);
        if(Objects.isNull(qty) || qty <= 0){
            throw Exceptions.OpsException("doItemInv数量更新异常:"+ qty);
        }
        opsDoItemInv.setVersion(version+1);
        opsDoItemInv.setModifyTime(DateUtil.getNow());

        opsDoItemInventoryMapper.updateByPrimaryKeySelective(opsDoItemInv);
    }

    @Override
    public void updateDoWaitTypeForDoId(String doId, String waitType, Integer version, String modifier) throws OpsException {
        OpsDo opsDo = new OpsDo();
        opsDo.setId(null);
        opsDo.setModifier(modifier);
        opsDo.setModifyTime(new Date());
        opsDo.setVersion(version + 1);
        opsDo.setWaitType(waitType);
        OpsDoExample opsDoUpdateExample = new OpsDoExample();
        OpsDoExample.Criteria opsDoUpdateCriteria = opsDoUpdateExample.createCriteria();
        opsDoUpdateCriteria.andDoIdEqualTo(doId);//出库指令
        opsDoUpdateCriteria.andVersionEqualTo(version);//version 并发条件
        opsDoUpdateCriteria.andDelflagEqualTo(0);//删除标识
        opsDoMapper.updateByExampleSelective(opsDo, opsDoUpdateExample);
    }

    @Override
    public void updateDoWaitTypeAndWareHouseCodeForId(Long id, String doSource, String gatherWarehouseCode, String warehouseCode,
                                                      String waitType, Integer version, String modifier, Integer isWms, Integer delflag) throws OpsException {
        OpsDo opsDo = new OpsDo();
        if (!StringUtils.isEmpty(gatherWarehouseCode)) {
            opsDo.setGatherWarehouseCode(gatherWarehouseCode);
        }
        if (!StringUtils.isEmpty(warehouseCode)) {
            opsDo.setWarehouseCode(warehouseCode);
        }
        if (!StringUtils.isEmpty(waitType)) {
            opsDo.setWaitType(waitType);
        }
        if (!StringUtils.isEmpty(doSource)) {
            opsDo.setDoSource(doSource);
        }
        if (null != isWms) {
            opsDo.setIsWms(isWms);
        }
        if (null != delflag) {
            opsDo.setDelflag(delflag);
        }
        opsDo.setModifier(modifier);
        opsDo.setModifyTime(new Date());
        opsDo.setVersion(version + 1);
        OpsDoExample opsDoUpdateExample = new OpsDoExample();
        OpsDoExample.Criteria opsDoUpdateCriteria = opsDoUpdateExample.createCriteria();
        opsDoUpdateCriteria.andIdEqualTo(id);
        opsDoUpdateCriteria.andVersionEqualTo(version);//version 并发条件
        opsDoUpdateCriteria.andDelflagEqualTo(0);//删除标识
        opsDoMapper.updateByExampleSelective(opsDo, opsDoUpdateExample);
    }

    @Override
    public int deleteDoItemInventoryByPrimaryKeySelective(Long id, UserDto userDto) throws OpsException {
        OpsDoItemInventory updateOpsDoItemInventory = new OpsDoItemInventory();
        updateOpsDoItemInventory.setId(id);
        updateOpsDoItemInventory.setDelflag(1);
        updateOpsDoItemInventory.setModifier(userDto.getUserName());
        updateOpsDoItemInventory.setModifyTime(new Date());
        return opsDoItemInventoryMapper.updateByPrimaryKeySelective(updateOpsDoItemInventory);
    }

    @Override
    public void updateDoItemInventoryByPrimaryKeySelective(Long id, Long inventoryId, String inventoryTableType, UserDto userDto) throws OpsException {
        OpsDoItemInventory updateOpsDoItemInventory = new OpsDoItemInventory();
        updateOpsDoItemInventory.setId(id);
        updateOpsDoItemInventory.setInventoryId(inventoryId);
        updateOpsDoItemInventory.setInventoryTableType(inventoryTableType);
        updateOpsDoItemInventory.setModifier(userDto.getUserName());
        updateOpsDoItemInventory.setModifyTime(new Date());
        opsDoItemInventoryMapper.updateByPrimaryKeySelective(updateOpsDoItemInventory);
    }

    /**
     * 变更过异常
     */
    @Override
    public void updateDoToException(Long id) {
        OpsDo updateDo = new OpsDo();
        updateDo.setId(id);
        updateDo.setWaitType(DoWaitTypeEnum.EXCEPTION.getType());
        updateDoByDo(updateDo);
    }

    /**
     * 变更过异常
     */
    @Override
    public void updateDoToRoId(Long id, String roId, Integer roCrossType,String relocation) {
        OpsDo updateDo = new OpsDo();
        updateDo.setId(id);
        updateDo.setRoCrossType(roCrossType);
        updateDo.setRoId(roId);
        updateDo.setRelocation(relocation);
        updateDoByDo(updateDo);
    }

    /**
     * bugid:13385 c14717 20240118
     * 1.随到随发 非拆分指令 重新计算集约仓
     * 2.判断调拨单是否可删；
     * 3.修改jyck的集约仓，绑定关联关联；
     * 4.判断是否下预约；
     * 5.发送变更集约仓事件,和货齐数量（itemInv为N的数量）；
     * 6.重新计算物流交货期
     * @param oldOpsDo
     * @throws OpsException
     */
    @Override
    public void updateGatherWarehouse(OpsDo oldOpsDo) throws OpsException{
        if (!DoSourceEnum.ASSModelNo.getType().equals(oldOpsDo.getDoSource()) && DoTypeEnum.JYCK.getType().equals(oldOpsDo.getDoType())){
            // 删调拨单，不为空代表返回成功；
            OpsDo delDBDo = updateDoDlvEntryDelDB(oldOpsDo);
            if (Objects.nonNull(delDBDo)) {//添加上架类型
                if (org.apache.commons.lang3.StringUtils.isNotEmpty(delDBDo.getRoId())) {
                    updateDoToRoId(oldOpsDo.getId(), delDBDo.getRoId(), delDBDo.getRoCrossType(),delDBDo.getDoId());
                }
                //bugid:9805 c14717 20230228 此方法修改集运仓时候传，数量传doitemInventory 状态为N的情况
                //计算货齐数
                List<OpsDoItemInventory> doItemInventorieList = baseDoService.getDoItemInventoryByDoId(oldOpsDo.getDoId());
                int qty = 0;
                for (OpsDoItemInventory obj : doItemInventorieList) {
                    if (InventoryTableTypeEnum.NORMAL.getType().equals(obj.getInventoryTableType())) {
                        qty += obj.getUseQty();
                    }
                }
                // 发送事件
                customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_WAREHOUSE, oldOpsDo);
                //bugid: 10345 20230410 c14717 重新计算物流交货期
                updateDoPcoWlday(oldOpsDo.getOrderId(),oldOpsDo.getOrderItem(),null,true,UserDto.ADMIN);
            }
        }
    }

    /**
     * //bugid :9426 c14717 20230216
     * 订单修改发货方式为随发分批
     * 拆分do单
     * @param oldOpsDo 需要是交易出库
     */
    @Override
    public String createAssDo(OpsDo oldOpsDo, int qty, UserDto userDto, DoTypeEnum doTypeEnum, String dlvEntire) throws OpsException {
        //1.处理非拆分型号 交易出库的do
        if (!DoSourceEnum.ASSModelNo.getType().equals(oldOpsDo.getDoSource()) && DoTypeEnum.JYCK.getType().equals(oldOpsDo.getDoType())) {
            //调用是否删除调拨单
            OpsDo delDBDo = updateDoDlvEntryDelDB(oldOpsDo);
            List<OpsDoItemInventory> doItemInventorieList = baseDoService.getDoItemInventoryByDoId(oldOpsDo.getDoId());
            for (OpsDoItemInventory obj : doItemInventorieList) {
                if (InventoryTableTypeEnum.NORMAL.getType().equals(obj.getInventoryTableType())) {
                    qty += obj.getUseQty();
                }
            }
            OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(oldOpsDo.getDoId());
            //qyt=do_item_qty
            if (opsDoItem.getQty().equals(qty)) {
                if (Objects.nonNull(delDBDo)) {//添加上架类型
                    if (org.apache.commons.lang3.StringUtils.isNotEmpty(delDBDo.getRoId())) {
                        updateDoToRoId(oldOpsDo.getId(), delDBDo.getRoId(), delDBDo.getRoCrossType(),delDBDo.getDoId());
                    }
                    //bugid:9805 c14717 20230228 此方法修改集运仓时候传，数量传doitemInventory 状态为N的情况
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_WAREHOUSE, oldOpsDo);
                    //bugid: 10345 20230410 c14717 重新计算物流交货期
                    updateDoPcoWlday(oldOpsDo.getOrderId(),oldOpsDo.getOrderItem(),null,true,UserDto.ADMIN);

                }
                return "";
            }
            //获取新的num
            OpsDo opsDoMaxOne = baseDoService.findByOrderOrderByNumMaxOne(oldOpsDo.getOrderId(), oldOpsDo.getOrderItem(), doTypeEnum);
            //创建新的do 和doItem
            oldOpsDo.setDlvEntire(dlvEntire);//随发分批
            WmOrderByInventoryDto wmOrderByInventoryDto = new WmOrderByInventoryDto();
            //处理do_item_inventory N的情况
            if (qty != 0) {
                opsDoMaxOne.setNum(opsDoMaxOne.getNum() + 1);
                String formatDoId = "";
                if (DoTypeEnum.CGDBCK.getType().equals(oldOpsDo.getDoType()) || DoTypeEnum.DBCK.getType().equals(oldOpsDo.getDoType()) || DoTypeEnum.TKCK.getType().equals(oldOpsDo.getDoType())) {
                    formatDoId = OrderIDFormatEnum.DBC_FN_FORMAT.getFormat();
                }else {
                    formatDoId = OrderIDFormatEnum.DO_FN_FORMAT.getFormat();
                }
                CreDoByInventoryDto creDoByInventoryDto = createAssQtyDo(formatDoId,opsDoMaxOne.getNum(), oldOpsDo, opsDoItem, qty, userDto, doTypeEnum,DoWaitTypeEnum.OK.getType());
                if (Objects.nonNull(delDBDo)) {//添加上架类型
                    if (org.apache.commons.lang3.StringUtils.isNotEmpty(delDBDo.getRoId())) {
                        creDoByInventoryDto.getOpsDo().setRoId(delDBDo.getRoId());
                        creDoByInventoryDto.getOpsDo().setRoCrossType(delDBDo.getRoCrossType());
                        //bugid:9965 c14717 20230315
                        creDoByInventoryDto.getOpsDo().setRelocation(delDBDo.getDoId());
                    }
                    //bugid:9805 c14717 20230228 此方法修改集运仓时候传，数量传doitemInventory 状态为N的情况
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_WAREHOUSE, oldOpsDo);
                } else if (org.apache.commons.lang3.StringUtils.isNotEmpty(oldOpsDo.getRoId())) {
                    creDoByInventoryDto.getOpsDo().setRoId(oldOpsDo.getRoId());
                    creDoByInventoryDto.getOpsDo().setRoCrossType(oldOpsDo.getRoCrossType());
                    updateDoToRoId(oldOpsDo.getId(), "", 0,"");//清空旧的上架类型
                    /*oldOpsDo.setRoId("");
                    oldOpsDo.setRoCrossType(0);
                    oldOpsDo.setRelocation("");*/
                    //bugid:9965 c14717 20230315
                    creDoByInventoryDto.getOpsDo().setRelocation(oldOpsDo.getDoId());
                } else if(org.apache.commons.lang3.StringUtils.isNotEmpty(oldOpsDo.getRelocation())){
                    updateDoToRoId(oldOpsDo.getId(), "", 0,"");//清空旧的上架类型
                    /*oldOpsDo.setRoId("");
                    oldOpsDo.setRoCrossType(0);
                    oldOpsDo.setRelocation("");*/
                    creDoByInventoryDto.getOpsDo().setRelocation(oldOpsDo.getRelocation());
                }
                wmOrderByInventoryDto.getDolist().add(creDoByInventoryDto);
                //n的绑定一条
                for (OpsDoItemInventory obj : doItemInventorieList) {
                    if (InventoryTableTypeEnum.NORMAL.getType().equals(obj.getInventoryTableType())) {
                        updateOpsDoItemInventoryDoId(obj.getId(), obj.getVersion(), creDoByInventoryDto.getOpsDo().getDoId(), userDto);
                    }
                }
            }
            //变更发货仓 ，传事件
            if (qty == 0 && Objects.nonNull(delDBDo)) {
                customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_WAREHOUSE, oldOpsDo);
            }
            //bugid:9874 c14717 20230308
            List<OpsDoItemInventory> oldDoItemInventorieList = baseDoService.getDoItemInventoryByDoIdOrderByInventoryId(oldOpsDo.getDoId());
            if (oldDoItemInventorieList.size() > 1) {
                int addMaxNum = opsDoMaxOne.getNum();
                for (OpsDoItemInventory obj : oldDoItemInventorieList) {
                    //判断是否下预约
                    boolean relocationFlag = false;
                    if(InventoryTableTypeEnum.TRANS.getType().equals(obj.getInventoryTableType())){
                        OpsInventoryMove inventoryMove = opsInventoryMoveMapper.selectByPrimaryKey(obj.getInventoryId());
                        if(Objects.nonNull(inventoryMove)){
                            //查询move opt_code=5 需要下预约
                            if(InventoryStatusEnum.W.getCode().equalsIgnoreCase(inventoryMove.getInventoryStatus())
                                    && OptStatusEnum.GOODS_CONFIRM.getCode().equals(inventoryMove.getOptStatus())){
                                relocationFlag = true;
                            }
                        }
                    }
                    //旧do
                    if (opsDoMaxOne.getNum() == addMaxNum) {
                        if(relocationFlag){
                            if (Objects.nonNull(delDBDo)) {//添加上架类型
                                if (org.apache.commons.lang3.StringUtils.isNotEmpty(delDBDo.getRoId())) {
                                    updateDoToRoId(oldOpsDo.getId(), delDBDo.getRoId(), delDBDo.getRoCrossType(),delDBDo.getDoId());
                                }
                            }
                        }
                    }else {//产生新do
                        qty += obj.getUseQty();
                        String formatDoId = "";
                        if (DoTypeEnum.CGDBCK.getType().equals(oldOpsDo.getDoType()) || DoTypeEnum.DBCK.getType().equals(oldOpsDo.getDoType()) || DoTypeEnum.TKCK.getType().equals(oldOpsDo.getDoType())) {
                            formatDoId = OrderIDFormatEnum.DBC_FN_FORMAT.getFormat();
                        }else {
                            formatDoId = OrderIDFormatEnum.DO_FN_FORMAT.getFormat();
                        }
                        CreDoByInventoryDto newDto = createAssQtyDo(formatDoId,opsDoMaxOne.getNum(), oldOpsDo, opsDoItem, obj.getUseQty(), userDto, doTypeEnum,DoWaitTypeEnum.OK.getType());
                        if (relocationFlag) {
                            if (Objects.nonNull(delDBDo)) {//添加上架类型
                                if (org.apache.commons.lang3.StringUtils.isNotEmpty(delDBDo.getRoId())) {
                                    newDto.getOpsDo().setRoId(delDBDo.getRoId());
                                    newDto.getOpsDo().setRoCrossType(delDBDo.getRoCrossType());
                                    //bugid:9965 c14717 20230315
                                    newDto.getOpsDo().setRelocation(delDBDo.getDoId());
                                }
                            } else if (org.apache.commons.lang3.StringUtils.isNotEmpty(oldOpsDo.getRoId())) {
                                newDto.getOpsDo().setRoId(oldOpsDo.getRoId());
                                newDto.getOpsDo().setRoCrossType(oldOpsDo.getRoCrossType());
                                updateDoToRoId(oldOpsDo.getId(), "", 0,"");//清空旧的上架类型
                                //bugid:9965 c14717 20230315
                                newDto.getOpsDo().setRelocation(oldOpsDo.getDoId());
                            }else if(org.apache.commons.lang3.StringUtils.isNotEmpty(oldOpsDo.getRelocation())){
                                updateDoToRoId(oldOpsDo.getId(), "", 0,"");//清空旧的上架类型
                                newDto.getOpsDo().setRelocation(oldOpsDo.getRelocation());
                            }
                        }
                        wmOrderByInventoryDto.getDolist().add(newDto);
                        updateOpsDoItemInventoryDoId(obj.getId(), obj.getVersion(), newDto.getOpsDo().getDoId(), userDto);
                    }
                    opsDoMaxOne.setNum(opsDoMaxOne.getNum() + 1);
                }
            }else if(oldDoItemInventorieList.size() == 1){//调拨单上预约
                if(InventoryTableTypeEnum.TRANS.getType().equals(oldDoItemInventorieList.get(0).getInventoryTableType())){
                    OpsInventoryMove inventoryMove = opsInventoryMoveMapper.selectByPrimaryKey(oldDoItemInventorieList.get(0).getInventoryId());
                    if(Objects.nonNull(inventoryMove)){
                        //查询move opt_code=5 需要下预约
                        if(InventoryStatusEnum.W.getCode().equalsIgnoreCase(inventoryMove.getInventoryStatus())
                                && OptStatusEnum.GOODS_CONFIRM.getCode().equals(inventoryMove.getOptStatus())){
                            if (Objects.nonNull(delDBDo)) {//添加上架类型
                                if (org.apache.commons.lang3.StringUtils.isNotEmpty(delDBDo.getRoId())) {
                                    updateDoToRoId(oldOpsDo.getId(), delDBDo.getRoId(), delDBDo.getRoCrossType(),delDBDo.getDoId());
                                    /*if(opsDoItem.getQty().equals(oldDoItemInventorieList.get(0).getUseQty())){
                                        updateDoToRoId(oldOpsDo.getId(), delDBDo.getRoId(), delDBDo.getRoCrossType(),delDBDo.getDoId());
                                    }*/
                                }
                            }

                        }
                    }
                }
            }
            //bugid:11568 c14717 20230918 oldDOItemInvSumQty>0 && opsDoItemQty>oldDOItemInvSumQty 拆分指令
            int assQty =  assDoByQtySumItemInv(opsDoMaxOne.getNum() + 1,opsDoItem.getDoId(),opsDoItem.getQty() - qty,
                    opsDoItem,userDto,doTypeEnum,wmOrderByInventoryDto);
            qty = qty + assQty;
            if (qty != 0) {
                //如果是all 需要更改 assQty
                if (DoSourceEnum.ALL.getType().equals(oldOpsDo.getDoSource()) || DoSourceEnum.CG.getType().equals(oldOpsDo.getDoSource())) {
                    updateDoWaitTypeAndWareHouseCodeForId(oldOpsDo.getId(), DoSourceEnum.ASSQTY.getType(), null,
                            null, null, oldOpsDo.getVersion(), userDto.getUserName(), 0, null);
                }
                //更改旧的doItem qty
                updateDoItemQtyByDoItemId(opsDoItem.getId(),  opsDoItem.getQty() - qty,opsDoItem.getVersion());
                ////持久化do
                CreateDoForDispatch(wmOrderByInventoryDto.getDolist(), userDto,null);
                //记录事件
                if (DoTypeEnum.JYCK.getType().equals(oldOpsDo.getDoType())) {
                    for (CreDoByInventoryDto doDto : wmOrderByInventoryDto.getDolist()) {
                        List<OpsDoItemInventory> doItemInventoryByDoId = baseDoService.getDoItemInventoryByDoId(doDto.getOpsDo().getDoId());
                        doDto.setItemInventorys(doItemInventoryByDoId);
                        customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_DELIVERY_TYPE, oldOpsDo,doDto);
                    }
                }
            }
            //bugid: 10345 20230410 c14717 重新计算物流交货期
            if (Objects.nonNull(delDBDo)){
                updateDoPcoWlday(oldOpsDo.getOrderId(),oldOpsDo.getOrderItem(),null,true,UserDto.ADMIN);
            }
        }
        return "";
    }


    /**
     * 拆分指令 doItem的数据大于doItemInv的数据 且doItemInv的数量大于0 继续拆分指令
     * @param maxNum 最大拆分num
     * @param oldJyDoId doId
     * @param doItemQty 当前doItem数量
     * @param opsDoItem doItem实体
     * @param userDto  修改人
     * @param doTypeEnum 单据类型
     * @param wmOrderByInventoryDto 实体化报文
     * @return 返回拆分的数量
     * @throws OpsException
     */
    //oldDOItemInvSumQty > 0 && opsDoItemQty > oldDOItemInvSumQty 拆分指令
    @Override
    public Integer assDoByQtySumItemInv(int maxNum, String oldJyDoId, int doItemQty, OpsDoItem opsDoItem, UserDto userDto, DoTypeEnum doTypeEnum, WmOrderByInventoryDto wmOrderByInventoryDto) throws OpsException{
        List<OpsDoItemInventory> oldDoItemInventorieList = baseDoService.getDoItemInventoryByDoIdOrderByInventoryId(oldJyDoId);
        int itemInvQty = 0;
        for(OpsDoItemInventory doInv : oldDoItemInventorieList){
            itemInvQty += doInv.getUseQty();
        }
        if(itemInvQty != 0 && doItemQty - itemInvQty > 0){
            OpsDo oldOpsDo = baseDoService.getDoByDoId(oldJyDoId);
            String formatDoId = "";
            if (DoTypeEnum.CGDBCK.getType().equals(oldOpsDo.getDoType())
                    || DoTypeEnum.DBCK.getType().equals(oldOpsDo.getDoType())
                    || DoTypeEnum.TKCK.getType().equals(oldOpsDo.getDoType())) {
                formatDoId = OrderIDFormatEnum.DBC_FN_FORMAT.getFormat();
            }else {
                formatDoId = OrderIDFormatEnum.DO_FN_FORMAT.getFormat();
            }
            CreDoByInventoryDto newDto = createAssQtyDo(formatDoId,maxNum, oldOpsDo, opsDoItem, itemInvQty, userDto, doTypeEnum,DoWaitTypeEnum.OK.getType());
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(oldOpsDo.getRoId())) {
                newDto.getOpsDo().setRoId(oldOpsDo.getRoId());
                newDto.getOpsDo().setRoCrossType(oldOpsDo.getRoCrossType());
                updateDoToRoId(oldOpsDo.getId(), "", 0,"");//清空旧的上架类型
                newDto.getOpsDo().setRelocation(oldOpsDo.getDoId());
            }else if(org.apache.commons.lang3.StringUtils.isNotEmpty(oldOpsDo.getRelocation())){
                updateDoToRoId(oldOpsDo.getId(), "", 0,"");//清空旧的上架类型
                newDto.getOpsDo().setRelocation(oldOpsDo.getRelocation());
            }
            wmOrderByInventoryDto.getDolist().add(newDto);
            updateOpsDoItemInventoryDoId(oldDoItemInventorieList.get(0).getId(), oldDoItemInventorieList.get(0).getVersion(), newDto.getOpsDo().getDoId(), userDto);
        } else {
            itemInvQty = 0;
        }
        return itemInvQty;
    }


    /**
     * 随发分批 删除没下发的调拨单
     * 关联交易出库单的doid
     * 修改发货仓
     * oldOpsDo为交易出库do
     */
    @Override
    public OpsDo updateDoDlvEntryDelDB(OpsDo oldOpsDo) throws OpsException {//todo 删除调拨单
        //非拆分型号，未下发do,等待类型为等待调拨
        if (!DoSourceEnum.ASSModelNo.getType().equals(oldOpsDo.getDoSource()) && DoWaitTypeEnum.WaitDB.getType().equals(oldOpsDo.getWaitType())) {
            OpsDo dbDo = null;
            List<OpsDo> dbckListByOrder = baseDoService.findDBCKListByOrder(oldOpsDo.getOrderId(), oldOpsDo.getOrderItem(), oldOpsDo.getNum(), DoSourceEnum.ALL);
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(dbckListByOrder)){
                dbckListByOrder.sort(Comparator.comparing(OpsDo::getExtNum,Comparator.reverseOrder()));
                dbDo =  dbckListByOrder.get(0);
            }
            if (Objects.isNull(dbDo)){
                return null;
            }
            //bugid: 12058 c14717 20230908 5修改随发分批 删调拨单 物流已经部分发运 包含调拨单
            if (DoStatusEnum.PART.getStatus().equals(dbDo.getDoStatus())) {
                return null;
            }
            //物流已经发运完成
            if (DoStatusEnum.FINISH.getStatus().equals(dbDo.getDoStatus())) {
                return null;
            }
            //组装删单报文
            List<CancelDocOrderDto> cancelWmsDBCKOrder = new ArrayList<>();
            CancelDocOrderDto param = new CancelDocOrderDto();
            param.setWmId(dbDo.getId());
            param.setCustomerId("SMC");// 固定不变
            param.setDocNo(dbDo.getDoId());// do_id
            param.setErpCancelReason("OPS取消指令");// 取消原因
            param.setWarehouseId(dbDo.getWarehouseCode());//分区 reducer
            param.setOrderType(DoTypeEnum.DBCK.getWltype());
            cancelWmsDBCKOrder.add(param);
            //调用取消订单接口
            CommonResult<List<JSONObject>> wmDBCKResult = opsCallWmsFeignApi.cancelDocOrder(cancelWmsDBCKOrder);
            if (wmDBCKResult.isSuccess()) {
                //1.变更仓库号
                updateDoWaitTypeAndWareHouseCodeForId(oldOpsDo.getId(), null, dbDo.getWarehouseCode(), dbDo.getWarehouseCode(),
                        DoWaitTypeEnum.OK.getType(), oldOpsDo.getVersion(), "admin", 0, null);
                //变更集约仓事件
                oldOpsDo.setWarehouseCode(dbDo.getWarehouseCode());
                oldOpsDo.setGatherWarehouseCode(dbDo.getWarehouseCode());
                //2.删除db单
                Long cancelId = delOrderReturnCancelId(dbDo.getOrderId(), dbDo.getOrderItem(), "1", "随发分批变更", UserDto.ADMIN);
                //删除调拨do 关联doItemInventory 把调拨单的do_item_inventory更新为交易出库的doid
                delDoChangeItemInventory(dbDo, cancelId, DoTypeEnum.DBCK, null, oldOpsDo);
                //删除调拨ro
                delDBRo(dbDo, cancelId, RoTypeEnum.DBRK.getType());
                return dbDo;
            }
        }
        return null;
    }


    /**
     * 采购分那到货，随发分批需要拆分do 返回doId
     * @param oldOpsDo 旧的do
     * @param qty      到货数量
     * @return 新的DoId
     * @throws OpsException 还需要生成的doItemInventory绑定do wmOrdertask我已经生成
     */
    @Override
    public String createAssDoForRo(OpsDo oldOpsDo, int qty, UserDto userDto, DoTypeEnum doTypeEnum) throws OpsException {
        //1.处理非拆分型号 交易出库的do
        if (!DoSourceEnum.ASSModelNo.getType().equals(oldOpsDo.getDoSource())) {
            //bugid :9426 c14717 20230216
            OpsDo delDBDo = null;
            if (DoTypeEnum.JYCK.getType().equals(oldOpsDo.getDoType())) {
                //调用是否删除调拨单
                delDBDo = updateDoDlvEntryDelDB(oldOpsDo);
            }
            //获取新的num
            OpsDo opsDoMaxOne = baseDoService.findByOrderOrderByNumMaxOne(oldOpsDo.getOrderId(), oldOpsDo.getOrderItem(), doTypeEnum);
            List<OpsDoItemInventory> doItemInventorieList = baseDoService.getDoItemInventoryByDoId(oldOpsDo.getDoId());
            OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(oldOpsDo.getDoId());
            //bugid:9187 c14717 20230106
            //如果是all 需要更改 assQty
            if (DoSourceEnum.ALL.getType().equals(oldOpsDo.getDoSource()) || DoSourceEnum.CG.getType().equals(oldOpsDo.getDoSource())) {
                updateDoWaitTypeAndWareHouseCodeForId(oldOpsDo.getId(), DoSourceEnum.ASSQTY.getType(), null,
                        null, null, oldOpsDo.getVersion(), userDto.getUserName(), 0, null);
            }
            if (DoTypeEnum.CGDBCK.getType().equals(oldOpsDo.getDoType()) || DoTypeEnum.DBCK.getType().equals(oldOpsDo.getDoType()) || DoTypeEnum.TKCK.getType().equals(oldOpsDo.getDoType())) {
                opsRoService.splitDBRo(oldOpsDo, qty, opsDoMaxOne.getNum() + 1);
            }
            //创建新的do 和doItem
            opsDoMaxOne.setNum(opsDoMaxOne.getNum() + 1);
            String formatDoId1 = "";
            if (DoTypeEnum.CGDBCK.getType().equals(oldOpsDo.getDoType()) || DoTypeEnum.DBCK.getType().equals(oldOpsDo.getDoType()) || DoTypeEnum.TKCK.getType().equals(oldOpsDo.getDoType())) {
                formatDoId1 = OrderIDFormatEnum.DBC_FN_FORMAT.getFormat();
            }else {
                formatDoId1 = OrderIDFormatEnum.DO_FN_FORMAT.getFormat();
            }
            CreDoByInventoryDto creDoByInventoryDto = createAssQtyDo(formatDoId1,opsDoMaxOne.getNum(), oldOpsDo, opsDoItem, qty, userDto, doTypeEnum,DoWaitTypeEnum.OK.getType());
            WmOrderByInventoryDto wmOrderByInventoryDto = new WmOrderByInventoryDto();
            String returnDoId = creDoByInventoryDto.getOpsDo().getDoId();
            wmOrderByInventoryDto.getDolist().add(creDoByInventoryDto);
            //bugid :9874 c14717 20230308
            if (DoTypeEnum.JYCK.getType().equals(oldOpsDo.getDoType())) {
                for (OpsDoItemInventory obj : doItemInventorieList) {//无法处理TRANS 情况，这个时候doItemInventory还没有拆
                    if (InventoryTableTypeEnum.NORMAL.getType().equals(obj.getInventoryTableType())) {
                        qty += obj.getUseQty();
                        opsDoMaxOne.setNum(opsDoMaxOne.getNum() + 1);
                        String formatDoId = "";
                        if (DoTypeEnum.CGDBCK.getType().equals(oldOpsDo.getDoType()) || DoTypeEnum.DBCK.getType().equals(oldOpsDo.getDoType()) || DoTypeEnum.TKCK.getType().equals(oldOpsDo.getDoType())) {
                            formatDoId = OrderIDFormatEnum.DBC_FN_FORMAT.getFormat();
                        }else {
                            formatDoId = OrderIDFormatEnum.DO_FN_FORMAT.getFormat();
                        }
                        CreDoByInventoryDto newDto = createAssQtyDo(formatDoId,opsDoMaxOne.getNum(), oldOpsDo, opsDoItem, obj.getUseQty(), userDto, doTypeEnum,DoWaitTypeEnum.OK.getType());
                        if (Objects.nonNull(delDBDo)) {//添加上架类型
                            if (org.apache.commons.lang3.StringUtils.isNotEmpty(delDBDo.getRoId())) {
                                newDto.getOpsDo().setRoId(delDBDo.getRoId());
                                newDto.getOpsDo().setRoCrossType(delDBDo.getRoCrossType());
                                //bugid:9965 c14717 20230315
                                newDto.getOpsDo().setRelocation(delDBDo.getDoId());
                            }
                            //bugid:9805 c14717 20230228 此方法修改集运仓时候传，数量传doitemInventory 状态为N的情况
                            customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_WAREHOUSE, oldOpsDo);
                        } else if (org.apache.commons.lang3.StringUtils.isNotEmpty(oldOpsDo.getRoId())) {
                            newDto.getOpsDo().setRoId(oldOpsDo.getRoId());
                            newDto.getOpsDo().setRoCrossType(oldOpsDo.getRoCrossType());
                            updateDoToRoId(oldOpsDo.getId(), "", 0,"");//清空旧的上架类型
                            //bugid:9965 c14717 20230315
                            newDto.getOpsDo().setRelocation(oldOpsDo.getDoId());
                        }
                        wmOrderByInventoryDto.getDolist().add(newDto);
                        updateOpsDoItemInventoryDoId(obj.getId(), obj.getVersion(), newDto.getOpsDo().getDoId(), userDto);
                    }
                }
            }
            //更改旧的doItem qty
            updateDoItemQtyByDoItemId(opsDoItem.getId(),  opsDoItem.getQty() - qty,opsDoItem.getVersion());
            ////持久化do
            CreateDoForDispatch(wmOrderByInventoryDto.getDolist(), userDto,null);
            //bugid: 10345 20230410 c14717 重新计算物流交货期
            if (Objects.nonNull(delDBDo)){
                updateDoPcoWlday(oldOpsDo.getOrderId(),oldOpsDo.getOrderItem(),null,true,UserDto.ADMIN);
            }
            return returnDoId;
        }
        throw Exceptions.OpsException("生成do失败");
    }

    /**
     * 补库单实际到货仓库号已请购仓不符
     * different warehouse
     * 到货仓与请购仓不一致 (此数量无do)
     * @param actualQty       实际补库数量 （不包括客单占用数量）
     * @param actualWarehouse 实际到货仓库号
     * @param orderno         请购单号
     * @param itemno          请购项号
     * @param splititemno     请购拆分单号
     * @throws OpsException 业务异常
     * @return 返回是否创建成功
     */
    @Override
    public Boolean handRoChangeWarehouseCreateDB(Integer actualQty, String actualWarehouse, String orderno, Integer itemno, Integer splititemno, List<Long> inventoryMoveIds) throws OpsException {
        log.info("实际到货仓和请购仓不符-补库单 qty:" + actualQty + " actualwarehouse:" + actualWarehouse + " orderno:" + orderno + " itemno:" + itemno + " splititemno:" + splititemno);
        //1.记录日志
        OpsOrderUpdateLog updateLog = new OpsOrderUpdateLog();
        updateLog.setOrderid(orderno);
        updateLog.setOrderItem(itemno);
        updateLog.setCreateTime(new Date());
        if (Objects.nonNull(splititemno)) {
            updateLog.setParams("生成补库调拨单" + splititemno + "--warehouseCode:" + actualWarehouse + "--actualQty:" + actualQty);
        } else {
            updateLog.setParams("生成补库调拨单" + actualWarehouse + "--actualQty:" + actualQty);
        }
        updateLog.setResult(JSONArray.toJSONString(inventoryMoveIds));//从库存id
        opsOrderUpdateLogMapper.insertSelective(updateLog);
        if (CollectionUtils.isEmpty(inventoryMoveIds)) {
            throw Exceptions.OpsException("补库单没有提供库存id");
        }
        //2.创建调拨单
        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
        if (actualQty > 0) {
            //2.1获取请购数据
            OpsRequestpurchase opsRequestpurchase = basePoService.getRequestPurchase(orderno, itemno, splititemno);
            if (Objects.isNull(opsRequestpurchase)) {
                return false;
            }
            //2.2是否创建调拨单
            if (!Objects.equals(actualWarehouse,
                    opsRequestpurchase.getRequestwarehouseid())) {
                //2.3 获取交易单数据 bugid:10651 C14717 20230511 优化挪动位置
                List<OpsDo> doList = baseDoService.findDoListByOrder(opsRequestpurchase.getOrderno(),
                        opsRequestpurchase.getItemno().toString(), null, null);
                int num = 0;
                if (!CollectionUtils.isEmpty(doList)) {
                    Integer maxint = doList.stream().map(OpsDo::getNum).max(Integer::compareTo).get();
                    num = maxint + 1;
                }
                //查询集约仓发货信息
                OpsWarehouse opsWarehouse = opsWarehouseService.findById(opsRequestpurchase.getRequestwarehouseid());// 请购仓
                opsRequestpurchase.setQuantity(actualQty);//实际调拨数据
                //bugid:11642 发票签收异常 c14717 20230801
                String dbckid = String.format(OrderIDFormatEnum.DBC_CG_FORMAT.getFormat(),
                        opsRequestpurchase.getOrderno(),
                        String.format("%03d", (opsRequestpurchase.getItemno())), String.format("%03d", num),
                        String.format("%03d", 0));
                String dbrkid = String.format(OrderIDFormatEnum.DBR_CG_FORMAT.getFormat(),
                        opsRequestpurchase.getOrderno(),
                        String.format("%03d", (opsRequestpurchase.getItemno())), String.format("%03d", num),
                        String.format("%03d", 0));
                //初始化do
                OpsDo opsDo = initOpsDo(dbckid, opsRequestpurchase, opsWarehouse,
                        actualWarehouse);
                opsDo.setNum(num);//分纳情况
                //初始化doItem数据
                OpsDoItem opsDoItem = initOpsDoItem(dbckid, opsRequestpurchase);
                opsDoItem.setQty(opsRequestpurchase.getQuantity());
                //初始化ro数据
                OpsRo opsRo = opsRoService.initOpsRo(dbrkid, opsRequestpurchase);
                opsRo.setNum(num);//分纳情况
                //初始化roItem数据
                OpsRoItem roItem = opsRoService.initOpsRoItem(dbrkid, opsRequestpurchase);
                //初始化doItemInventory
                List<OpsDoItemInventory> doItemInventoryList = new ArrayList<>();
                for (Long invID : inventoryMoveIds) {
                    OpsDoItemInventory opsDoItemInventory = new OpsDoItemInventory();
                    opsDoItemInventory.setDoId(dbckid);
                    opsDoItemInventory.setDoItem(1);
                    opsDoItemInventory.setInventoryId(invID);
                    opsDoItemInventory.setInventoryTableType("T");
                    opsDoItemInventory.setUseQty(actualQty);
                    opsDoItemInventory.setVersion(0L);
                    opsDoItemInventory.setDelflag(0);
                    opsDoItemInventory.setCreTime(new Date());
                    opsDoItemInventory.setCreator("补库调拨");
                    opsDoItemInventory.setSortnum(1);
                    doItemInventoryList.add(opsDoItemInventory);
                    //此处加预占不妥，客单占用补库单
                    //baseInventoryService.addPreQtyInventory(invID, actualQty, InventoryTableTypeEnum.TRANS.getType(), UserDto.WMS);
                }
                //实例化do
                insertDo(opsDo, opsDoItem, doItemInventoryList,
                        new UserDto("补库调拨", "0.0.0.0"));
                //实例化ro
                opsRoService.insertRo(opsRo, roItem, new ArrayList<OpsRoItemInventory>(),
                        new UserDto("补库调拨", "0.0.0.0"));
                //初始化task数据
                OpsWmOrderTask opsWmOrderTaskDO = new OpsWmOrderTask();
                opsWmOrderTaskDO.setWmOrderId(dbckid);
                opsWmOrderTaskDO.setWmOrderType(WmOrderTaskEnum.DO.getType());
                opsWmOrderTaskDO.setTaskType(WmOrderTaskEnum.ORDER.getType());
                opsWmOrderTaskDO.setFlag(Integer.valueOf(SendStatusEnum.READ.getType()));
                opsWmOrderTaskDO.setCreator("补库调拨");
                opsWmOrderTaskDO.setCreTime(new Date());
                // wmOrderTaskService.addOpsWmOrderTask(opsWmOrderTaskDO);
                taskList.add(opsWmOrderTaskDO);
                OpsWmOrderTask opsWmOrderTaskRO = new OpsWmOrderTask();
                opsWmOrderTaskRO.setWmOrderId(dbrkid);
                opsWmOrderTaskRO.setWmOrderType(WmOrderTaskEnum.RO.getType());
                opsWmOrderTaskRO.setTaskType(WmOrderTaskEnum.ORDER.getType());
                opsWmOrderTaskRO.setFlag(Integer.valueOf(SendStatusEnum.READ.getType()));
                opsWmOrderTaskRO.setCreator("补库调拨");
                opsWmOrderTaskRO.setCreTime(new Date());
                taskList.add(opsWmOrderTaskRO);
            }
            //持久化task数据 批量更新task
            wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
        }
        //bugid:10651 C14717 20230511 返回创建调拨单是否成功
        if(CollectionUtil.isEmpty(taskList)){
            return false;
        }else {
            return true;
        }
    }


    /**
     * different warehouse
     * 仓库和实际入库仓库不一致，生成调拨单出和入 修改等待类型
     * 1.删除调拨单 交易出库单改为在库 包括加工单和交易出库单
     * 2.修改调拨单的发货仓库号
     * 3.生成调拨单 修改等待类型 包括加工单和交易出库单
     * 4.货齐多仓，随到随发和随发分批直接从到货仓库发给客户 既修改仓库号
     * @param cgDo 必填 pcoId 可不填
     */
    @Override
    public void handROChangeDoWarehouseCodeByCGDo(OpsDo cgDo, String pcoId, Integer pcoItem, String modelNo) throws OpsException {//cgDo 已经修改发货仓
        //特殊型号不可以异仓收货
        Boolean speFlag = baseDoService.checkSpeModelNoSpeWarehouse(modelNo);
        if(speFlag){
            throw Exceptions.OpsException("型号："+modelNo+"不可异仓收货");
        }
        //通知发货 分支
        Boolean aBoolean = doPcoLogicCenterService.checkDlvEntireAssShipment(cgDo.getOrderId());
        if(aBoolean){
            doPcoLogicCenterService.cgDiffWarehouseModifyDO(cgDo,pcoId,pcoItem);
            return;
        }
        log.info("实际到货仓和请购仓不符orderId:" + cgDo.getOrderId() + "-" + cgDo.getOrderItem() + " dotype:" + cgDo.getDoType() + " WarehouseCode: " + cgDo.getWarehouseCode() + "pcoItem:" + pcoItem);
        OpsOrderUpdateLog updateLog = new OpsOrderUpdateLog();
        updateLog.setOrderid(cgDo.getOrderId());
        updateLog.setOrderItem(Integer.parseInt(cgDo.getOrderItem()));
        updateLog.setCreateTime(new Date());
        updateLog.setParams(cgDo.getDoId());
        if (Objects.nonNull(pcoItem)) {
            updateLog.setResult("变更交易出库单" + cgDo.getDoId() + " " + cgDo.getWarehouseCode() + "--pcoItem:" + pcoItem + "--pcoId" + pcoId);
        } else {
            updateLog.setResult("变更交易出库单" + cgDo.getDoId() + " " + cgDo.getWarehouseCode());
        }
        opsOrderUpdateLogMapper.insertSelective(updateLog);
        // bugid：12563 调库单修改仓库代码 2023-12-06 C12961
        if (DoTypeEnum.TKCK.getType().equals(cgDo.getDoType())){
            // 1.更新do的warehouse为签收仓
            OpsDo updateDo = new OpsDo();
            updateDo.setId(cgDo.getId());
            updateDo.setWarehouseCode(cgDo.getWarehouseCode());
            // 2.判断transFlag==0,则更新do的gather_warehouse为签收仓
            TransOrder transOrder = transOrderService.getTransOrderByTransNo(cgDo.getOrderId(), Integer.valueOf(cgDo.getOrderItem()));
            if(transOrder==null){
                throw Exceptions.OpsException("查询不到transOrder==>{}-{}", cgDo.getOrderId(), Integer.valueOf(cgDo.getOrderItem()));
            }
            Boolean transFlag = transOrder.getTransFlag();
            if (transFlag != null && !transFlag) {
                updateDo.setGatherWarehouseCode(cgDo.getWarehouseCode());
                cgDo.setGatherWarehouseCode(cgDo.getWarehouseCode());
                // 修改ro的仓库号
                List<OpsRo> roList = baseRoService.findRoByOrderItemNum(cgDo.getOrderId(), Integer.valueOf(cgDo.getOrderItem()),
                        cgDo.getNum(), cgDo.getAssNum(),cgDo.getExtNum(), RoTypeEnum.TKRK.getType());
                if (!CollectionUtils.isEmpty(roList)) {
                    OpsRo ro = roList.get(0);
                    baseRoService.updateOpsRoWarehouse(ro.getRoId(), ro.getVersion(), cgDo.getGatherWarehouseCode(), "异仓调库改入库仓");
                }
            }
            updateDo.setModifyTime(new Date());
            opsDoMapper.updateByPrimaryKeySelective(updateDo);
            // 更新transOrder
            transOrderService.updateWarehouse(transOrder.getId(), cgDo.getWarehouseCode(), cgDo.getGatherWarehouseCode());

            // 3.判断do是否为同仓
            if (!cgDo.getGatherWarehouseCode().equals(cgDo.getWarehouseCode())) {
                // 如果是易仓调拨，则生成task下发
                // 创建task前先查询是否已经存在，如果存在则不创建
                OpsWmOrderTask task = wmOrderTaskService.createWmTaskPo(cgDo.getDoId(), WmOrderTaskEnum.DO,
                        WmOrderTaskEnum.ORDER, SendStatusEnum.SUSPENDED, new CreateInfoDto("异仓调库"), DateUtil.getNow(), 0, "");
                Integer taskFlag = wmOrderTaskService.getTaskFlag(task.getWmOrderId());
                if (taskFlag == null) {
                    wmOrderTaskService.addOpsWmOrderTask(task);
                }
            }
            return;
        }

        if (DoTypeEnum.CGDBCK.getType().equals(cgDo.getDoType())) {//采购补库调拨直接修改仓库号 此处必须有do
            //bugid:9708 c14717 20230222 常州仓升仓位物流中心 ，可以直接接收采购收货 调拨单的ro_id 和 上架类型标记不用考虑
            if (cgDo.getGatherWarehouseCode().equals(cgDo.getWarehouseCode())) {//仓库号和交易出库单一致 需要删除调拨单
                //删除表
                Long cancelId = delOrderReturnCancelId(cgDo.getOrderId(), cgDo.getOrderItem(), "1", "收货仓变更", UserDto.ADMIN);
                //删除调拨入
                delDBRo(cgDo, cancelId, RoTypeEnum.CGDBRK.getType());
                //删除调拨出
                insertOrderCancelItem(cancelId, cgDo.getDoId(), cgDo.getDoType(), "收货仓变更", UserDto.ADMIN);
                wmOrderTaskService.delWmOrderTask(cgDo.getDoId(), "收货仓变更");
                CancelDo(cgDo.getId(), cgDo.getDoId(), cgDo.getOrderId(),
                        cgDo.getOrderItem(), UserDto.ADMIN);
            } else {
                //修改等待类型
                OpsDo updateDo = new OpsDo();
                updateDo.setId(cgDo.getId());
                updateDo.setWarehouseCode(cgDo.getWarehouseCode());
                updateDo.setModifyTime(new Date());
                opsDoMapper.updateByPrimaryKeySelective(updateDo);
            }
            return;

        }
        if (CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(cgDo.getDlvEntire())//整单多仓货齐
                || CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(cgDo.getDlvEntire())//随到随发
                || CKTYPEEnum.ITEM_UNLIMIT.getCode().equals(cgDo.getDlvEntire())) {//随发分批
            //是否是调拨单
            if (DoTypeEnum.DBCK.getType().equals(cgDo.getDoType())) {
                if (cgDo.getGatherWarehouseCode().equals(cgDo.getWarehouseCode())) {//仓库号和交易出库单一致 需要删除调拨单
                    //1.删除调拨单 出和入 和task
                    //2.直接关联doItemInventory 或pcoItemInventory
                    if (DoSourceEnum.ASSModelNo.getType().equals(cgDo.getDoSource())) {
                        OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoId, pcoItem);
                        opsPcoService.updatetOpsPcoItem(opsPcoItem.getId(), DoWaitTypeEnum.OK.getType(), new UserDto("ADMIN", ""));
                        //2.删除db单
                        Long cancelId = delOrderReturnCancelId(cgDo.getOrderId(), cgDo.getOrderItem(), "1", "采购仓库变更", UserDto.ADMIN);
                        //删除调拨do 关联交易出库单或加工单doItemInventory
                        delDoChangeItemInventory(cgDo, cancelId, DoTypeEnum.DBCK, opsPcoItem, null);
                        //删除调拨ro
                        delDBRo(cgDo, cancelId, RoTypeEnum.DBRK.getType());

                    } else {
                        DoSourceEnum doSourceEnum = null;
                        if (DoSourceEnum.ALL.getType().equals(cgDo.getDoSource())) {
                            doSourceEnum = DoSourceEnum.ALL;
                        } else if (DoSourceEnum.ASSQTY.getType().equals(cgDo.getDoSource())) {
                            doSourceEnum = DoSourceEnum.ASSQTY;
                        }
                        OpsDo jyckDo = baseDoService.getJYCKByOrder(cgDo.getOrderId(), cgDo.getOrderItem(), cgDo.getNum(), doSourceEnum);
                        updateDoWaitTypeAndWareHouseCodeForId(jyckDo.getId(), null, cgDo.getWarehouseCode(), cgDo.getWarehouseCode(),
                                DoWaitTypeEnum.OK.getType(), jyckDo.getVersion(), "admin", 0, null);
                        //2.删除db单
                        Long cancelId = delOrderReturnCancelId(cgDo.getOrderId(), cgDo.getOrderItem(), "1", "采购仓库变更", UserDto.ADMIN);
                        //删除调拨do 关联doItemInventory
                        delDoChangeItemInventory(cgDo, cancelId, DoTypeEnum.DBCK, null, jyckDo);
                        //删除调拨ro
                        delDBRo(cgDo, cancelId, RoTypeEnum.DBRK.getType());
                    }
                } else {//仓库号不一致需要修改仓库号
                    //1.加工单继续调拨 修改仓库号
                    if (DoSourceEnum.ASSModelNo.getType().equals(cgDo.getDoSource())) {//拆分继续调拨
                        OpsDo updateDo = new OpsDo();
                        updateDo.setId(cgDo.getId());
                        updateDo.setWarehouseCode(cgDo.getWarehouseCode());
                        updateDo.setModifyTime(new Date());
                        opsDoMapper.updateByPrimaryKeySelective(updateDo);
                    } else if (DoSourceEnum.ASSQTY.getType().equals(cgDo.getDoSource())) {//拆分继续调拨
                        if (CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(cgDo.getDlvEntire())//整单多仓货齐
                                || CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(cgDo.getDlvEntire())) {
                            OpsDo updateDo = new OpsDo();
                            updateDo.setId(cgDo.getId());
                            updateDo.setWarehouseCode(cgDo.getWarehouseCode());
                            updateDo.setModifyTime(new Date());
                            opsDoMapper.updateByPrimaryKeySelective(updateDo);
                        } else {
                            DoSourceEnum doSourceEnum = null;
                            if (DoSourceEnum.ALL.getType().equals(cgDo.getDoSource())) {
                                doSourceEnum = DoSourceEnum.ALL;
                            } else if (DoSourceEnum.ASSQTY.getType().equals(cgDo.getDoSource())) {
                                doSourceEnum = DoSourceEnum.ASSQTY;
                            }
                            OpsDo jyckDo = baseDoService.getJYCKByOrder(cgDo.getOrderId(), cgDo.getOrderItem(), cgDo.getNum(), doSourceEnum);
                            updateDoWaitTypeAndWareHouseCodeForId(jyckDo.getId(), null, cgDo.getWarehouseCode(), cgDo.getWarehouseCode(),
                                    DoWaitTypeEnum.OK.getType(), jyckDo.getVersion(), "admin", 0, null);
                            //2.删除db单
                            Long cancelId = delOrderReturnCancelId(cgDo.getOrderId(), cgDo.getOrderItem(), "1", "采购仓库变更", UserDto.ADMIN);
                            //删除调拨do 关联doItemInventory
                            delDoChangeItemInventory(cgDo, cancelId, DoTypeEnum.DBCK, null, jyckDo);
                            //删除调拨ro
                            delDBRo(cgDo, cancelId, RoTypeEnum.DBRK.getType());
                        }

                    } else {//2.其他直接修改交易单出库仓库号
                        DoSourceEnum doSourceEnum = null;
                        if (DoSourceEnum.ALL.getType().equals(cgDo.getDoSource())) {
                            doSourceEnum = DoSourceEnum.ALL;
                        } else if (DoSourceEnum.ASSQTY.getType().equals(cgDo.getDoSource())) {
                            doSourceEnum = DoSourceEnum.ASSQTY;
                        }
                        OpsDo jyckDo = baseDoService.getJYCKByOrder(cgDo.getOrderId(), cgDo.getOrderItem(), cgDo.getNum(), doSourceEnum);
                        updateDoWaitTypeAndWareHouseCodeForId(jyckDo.getId(), null, cgDo.getWarehouseCode(), cgDo.getWarehouseCode(),
                                DoWaitTypeEnum.OK.getType(), jyckDo.getVersion(), "admin", 0, null);
                        //2.删除db单
                        Long cancelId = delOrderReturnCancelId(cgDo.getOrderId(), cgDo.getOrderItem(), "1", "采购仓库变更", UserDto.ADMIN);
                        //删除调拨do 关联doItemInventory
                        delDoChangeItemInventory(cgDo, cancelId, DoTypeEnum.DBCK, null, jyckDo);
                        //删除调拨ro
                        delDBRo(cgDo, cancelId, RoTypeEnum.DBRK.getType());
                    }
                }
            } else if (DoTypeEnum.JYCK.getType().equals(cgDo.getDoType())) {
                if (cgDo.getGatherWarehouseCode().equals(cgDo.getWarehouseCode())) {
                    throw Exceptions.OpsException("异常收货do表仓库号一致，不属于异仓收货,请联系it人员；warehouseCode:" + cgDo.getWarehouseCode() + "-doid:" + cgDo.getDoId());
                }
                //修改等待类型
                if (DoSourceEnum.ASSModelNo.getType().equals(cgDo.getDoSource())) {//拆分生成调拨单
                    OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoId, pcoItem);
                    opsPcoService.updatetOpsPcoItem(opsPcoItem.getId(), DoWaitTypeEnum.WaitDB.getType(), new UserDto("ADMIN", ""));
                    //生成调拨单 task flag = 4 更改pcoItemInventory 和 doItemInventory 更改到调拨出上面
                    createDBOrder(cgDo, opsPcoItem, new UserDto("admin"));
                } else if (DoSourceEnum.ASSQTY.getType().equals(cgDo.getDoSource())) {//拆分生成调拨单
                    if (CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(cgDo.getDlvEntire())//整单多仓货齐
                            || CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE.getCode().equals(cgDo.getDlvEntire())) {
                        updateDoWaitTypeAndWareHouseCodeForId(cgDo.getId(), null, cgDo.getGatherWarehouseCode(), cgDo.getWarehouseCode(),
                                DoWaitTypeEnum.WaitDB.getType(), cgDo.getVersion(), "admin", 0, null);
                        //生成调拨单 task flag = 4 更改pcoItemInventory 和 doItemInventory 更改到调拨出上面
                        createDBOrder(cgDo, null,new UserDto("admin"));
                    } else {//随发分批 修改集约仓和发货仓
                        //1.修改仓库号
                        OpsDo updateDo = new OpsDo();
                        updateDo.setId(cgDo.getId());
                        updateDo.setWarehouseCode(cgDo.getWarehouseCode());
                        updateDo.setGatherWarehouseCode(cgDo.getWarehouseCode());
                        updateDo.setModifyTime(new Date());
                        opsDoMapper.updateByPrimaryKeySelective(updateDo);
                        customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_WAREHOUSE, cgDo);
                    }
                } else {//全数量采购 多仓 随发 随发分批 直接发客户
                    //1.修改发货仓和集约仓库号
                    OpsDo updateDo = new OpsDo();
                    updateDo.setId(cgDo.getId());
                    updateDo.setWarehouseCode(cgDo.getWarehouseCode());
                    updateDo.setGatherWarehouseCode(cgDo.getWarehouseCode());
                    updateDo.setModifyTime(new Date());
                    opsDoMapper.updateByPrimaryKeySelective(updateDo);
                    customerEventPublisher.customerOrderEvent(EventSourceEnum.CUSTOMER_ORDER_MODIFY_WAREHOUSE, cgDo);
                }
            }
        } else {//整单单仓货齐 必须整单集约
            //是否是调拨单
            if (DoTypeEnum.DBCK.getType().equals(cgDo.getDoType())) {
                if (cgDo.getGatherWarehouseCode().equals(cgDo.getWarehouseCode())) {//仓库号和交易出库单一致 需要删除调拨单
                    //1.删除调拨单 出和入 和task
                    //2.直接关联doItemInventory 或pcoItemInventory
                    if (DoSourceEnum.ASSModelNo.getType().equals(cgDo.getDoSource())) {
                        OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoId, pcoItem);
                        opsPcoService.updatetOpsPcoItem(opsPcoItem.getId(), DoWaitTypeEnum.OK.getType(), new UserDto("ADMIN", ""));
                        //2.删除db单
                        Long cancelId = delOrderReturnCancelId(cgDo.getOrderId(), cgDo.getOrderItem(), "1", "采购仓库变更", UserDto.ADMIN);
                        //删除调拨do 关联doItemInventory
                        delDoChangeItemInventory(cgDo, cancelId, DoTypeEnum.DBCK, opsPcoItem, null);
                        //删除调拨ro
                        delDBRo(cgDo, cancelId, RoTypeEnum.DBRK.getType());

                    } else {
                        DoSourceEnum doSourceEnum = null;
                        if (DoSourceEnum.ALL.getType().equals(cgDo.getDoSource())) {
                            doSourceEnum = DoSourceEnum.ALL;
                        } else if (DoSourceEnum.ASSQTY.getType().equals(cgDo.getDoSource())) {
                            doSourceEnum = DoSourceEnum.ASSQTY;
                        }
                        OpsDo jyckDo = baseDoService.getJYCKByOrder(cgDo.getOrderId(), cgDo.getOrderItem(), cgDo.getNum(), doSourceEnum);
                        updateDoWaitTypeAndWareHouseCodeForId(jyckDo.getId(), null, cgDo.getWarehouseCode(), cgDo.getWarehouseCode(),
                                DoWaitTypeEnum.OK.getType(), jyckDo.getVersion(), "admin", 0, null);
                        //2.删除db单
                        Long cancelId = delOrderReturnCancelId(cgDo.getOrderId(), cgDo.getOrderItem(), "1", "采购仓库变更", UserDto.ADMIN);
                        //删除调拨do 关联doItemInventory
                        delDoChangeItemInventory(cgDo, cancelId, DoTypeEnum.DBCK, null, jyckDo);
                        //删除调拨ro
                        delDBRo(cgDo, cancelId, RoTypeEnum.DBRK.getType());
                    }
                } else {//仓库号不一致需要修改仓库号
                    //1.修改调拨单仓库号
                    OpsDo updateDo = new OpsDo();
                    updateDo.setId(cgDo.getId());
                    updateDo.setWarehouseCode(cgDo.getWarehouseCode());
                    updateDo.setModifyTime(new Date());
                    opsDoMapper.updateByPrimaryKeySelective(updateDo);
                }
            } else if (DoTypeEnum.JYCK.getType().equals(cgDo.getDoType())) {
                if (cgDo.getGatherWarehouseCode().equals(cgDo.getWarehouseCode())) {
                    throw Exceptions.OpsException("异常收货do表仓库号一致，不属于异仓收货,请联系it人员；warehouseCode:" + cgDo.getWarehouseCode() + "-doid:" + cgDo.getDoId());
                }
                //修改等待类型
                if (DoSourceEnum.ASSModelNo.getType().equals(cgDo.getDoSource())) {
                    OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoId, pcoItem);
                    opsPcoService.updatetOpsPcoItem(opsPcoItem.getId(), DoWaitTypeEnum.WaitDB.getType(), new UserDto("ADMIN", ""));
                    //生成调拨单 task flag = 4 更改pcoItemInventory 和 doItemInventory 更改到调拨出上面
                    createDBOrder(cgDo, opsPcoItem,new UserDto("admin"));
                } else {
                    updateDoWaitTypeAndWareHouseCodeForId(cgDo.getId(), null, cgDo.getGatherWarehouseCode(), cgDo.getWarehouseCode(),
                            DoWaitTypeEnum.WaitDB.getType(), cgDo.getVersion(), "admin", 0, null);
                    //生成调拨单 task flag = 4 更改pcoItemInventory 和 doItemInventory 更改到调拨出上面
                    createDBOrder(cgDo, null,new UserDto("admin"));
                }
            }
        }
        //bugid: 10345 20230410 c14717 重新计算物流交货期
        updateDoPcoWlday(cgDo.getOrderId(),cgDo.getOrderItem(),null,true,UserDto.ADMIN);
    }


    /**
     * @description：初始化数据
     * @author ：
     * @date ：Created in 2021/10/1 15:11
     */
    @Override
    public OpsDo initOpsDo(String doId, OpsRequestpurchase opsRequestpurchase, OpsWarehouse opsWarehouse,
                           String receivewarehouseid) {
        OpsDo opsDo = new OpsDo();
        opsDo.setDoId(doId);
        opsDo.setOrderId(opsRequestpurchase.getOrderno());
        opsDo.setOrderItem(opsRequestpurchase.getItemno() + "");
        if (null != opsRequestpurchase.getSplititemno()) {
            opsDo.setNum(opsRequestpurchase.getSplititemno());
        } else {
            opsDo.setNum(0);
        }

        opsDo.setAssNum(0);
        opsDo.setExtNum(0);
        opsDo.setDoSource(DoSourceEnum.ASSQTY.getType());
        opsDo.setDoType(DoTypeEnum.CGDBCK.getType());

        opsDo.setWarehouseCode(receivewarehouseid);
        opsDo.setGatherWarehouseCode(opsWarehouse.getWarehouseCode());
        //bugid:11445 c14717 2023/07/26 仓库多地址
        opsWarehouseService.getMultAdressSetAddress(opsWarehouse,opsDo.getDoType());
        opsDo.setProvince(opsWarehouse.getProvince());
        opsDo.setCity(opsWarehouse.getCity());
        opsDo.setDistrict(opsWarehouse.getDistrict());
        opsDo.setStreet(opsWarehouse.getAdress());
        opsDo.setAddress(opsWarehouse.getAdress());
        opsDo.setLinkman(opsWarehouse.getLinkman());
        if (!StringUtils.isEmpty(opsWarehouse.getLinkMobile())) {//手机号
            if (StringPhoneUtil.isMobil(opsWarehouse.getLinkMobile())) {
                opsDo.setMobile(opsWarehouse.getLinkMobile());
            }
        }
        if (!StringUtils.isEmpty(opsWarehouse.getLinkPhone())) {
            //是电话 且不是手机号 存电话
            if (StringPhoneUtil.isPhone(opsWarehouse.getLinkPhone()) && !StringPhoneUtil.isMobil(opsWarehouse.getLinkPhone())) {
                opsDo.setPhone(opsWarehouse.getLinkPhone());
            }
        }
        opsDo.setPostcode(opsWarehouse.getPostCode() + "");

        opsDo.setCustomerNo(opsRequestpurchase.getCustomerno());

        opsDo.setCorderNo(opsRequestpurchase.getCorderno());
        opsDo.setDoStatus(0);// 初始
        opsDo.setCarried("");// 指定承运商 dbck不指定承运商
        opsDo.setRemark(opsRequestpurchase.getRemark()); // 指定备注内容
        opsDo.setVersion(0);
        opsDo.setDelflag(0);
        opsDo.setCreator("补库调拨");
        opsDo.setCreTime(new Date());
        opsDo.setWaitType(DoWaitTypeEnum.WaitCG.getType());
        opsDo.setHopeDate(opsRequestpurchase.getHopeexportdate());
        opsDo.setWlDate(opsRequestpurchase.getHopeexportdate());
        opsDo.setDeptNo(opsRequestpurchase.getDeptno());// 营业所代码
        return opsDo;
    }


    /**
     * @description：采购单接单回执
     * @author ：c02483
     * @date ：Created in 2021/10/1 15:11
     * @description：初始化数据
     */
    @Override
    public OpsDoItem initOpsDoItem(String doId, OpsRequestpurchase opsRequestpurchase) {
        OpsDoItem opsDoItem = new OpsDoItem();
        opsDoItem.setDoId(doId);
        opsDoItem.setDoItem(1);
        opsDoItem.setModelno(opsRequestpurchase.getModelno());
        opsDoItem.setQty(opsRequestpurchase.getQuantity());
        //bugid:10651 C14717 20230511 优化判断绿标
        if(org.apache.commons.lang.StringUtils.isNotBlank(opsRequestpurchase.getProducttag()) && "0".equals(opsRequestpurchase.getProducttag())){
            opsDoItem.setGreenCode("H");
        }
        opsDoItem.setOutQty(0);
        opsDoItem.setVersion(0);
        opsDoItem.setDelflag(0);
        opsDoItem.setCreator("补库调拨");
        opsDoItem.setCreTime(new Date());
        opsDoItem.setModifyTime(new Date());
        return opsDoItem;
    }

    /**
     * @description：向数据库插入发货指令
     * @author ：c02483
     * @date ：Created in 2021/10/1 15:10
     */
    @Override
    public OpsDo insertDo(OpsDo opsDo, OpsDoItem opsDoItem, List<OpsDoItemInventory> doItemInventoryList, UserDto userDto) throws OpsException {
        if (DoSourceEnum.ALL.getType().equals(opsDo.getDoSource()) || DoSourceEnum.CG.getType().equals(opsDo.getDoSource())) {
            opsDo.setNum(0);
        }
        opsDo.setDelflag(0);
        opsDo.setVersion(0);
        opsDo.setCreTime(new Date());
        opsDo.setModifyTime(new Date());
        opsDo.setCreator(userDto.getUserName());
        opsDo.setModifier(userDto.getUserName());
        opsDoMapper.insertSelective(opsDo);
        opsDoItem.setDelflag(0);
        opsDoItem.setVersion(0);
        opsDoItem.setCreTime(DateUtil.getNow());
        opsDoItem.setModifyTime(DateUtil.getNow());
        opsDoItem.setCreator(userDto.getUserName());
        opsDoItem.setModifier(userDto.getUserName());
        opsDoItemMapper.insertSelective(opsDoItem);
        if (!CollectionUtils.isEmpty(doItemInventoryList)) {
            for (OpsDoItemInventory doItemInventory : doItemInventoryList) {
                doItemInventory.setDelflag(0);
                doItemInventory.setVersion(0L);
                doItemInventory.setCreTime(DateUtil.getNow());
                doItemInventory.setCreator(userDto.getUserName());
                doItemInventory.setModifyTime(DateUtil.getNow());
                doItemInventory.setModifier(userDto.getUserName());
                opsDoItemInventoryMapper.insertSelective(doItemInventory);
            }
        }
        return opsDo;
    }

    @Override
    public OpsDo createDo(OpsDo opsDo, OpsDoItem opsDoItem, List<OpsDoItemInventory> doItemInventoryList, UserDto userDto) throws OpsException {

        opsDo.setDelflag(0);
        opsDo.setVersion(0);
        opsDo.setCreTime(DateUtil.getNow());
        opsDo.setModifyTime(DateUtil.getNow());
        opsDo.setCreator(userDto.getUserName());
        opsDo.setModifier(userDto.getUserName());
        opsDoMapper.insertSelective(opsDo);
        opsDoItem.setDelflag(0);
        opsDoItem.setVersion(0);
        opsDoItem.setCreTime(DateUtil.getNow());
        opsDoItem.setModifyTime(DateUtil.getNow());
        opsDoItem.setCreator(userDto.getUserName());
        opsDoItem.setModifier(userDto.getUserName());
        opsDoItemMapper.insertSelective(opsDoItem);
        if (!CollectionUtils.isEmpty(doItemInventoryList)) {
            for (OpsDoItemInventory doItemInventory : doItemInventoryList) {
                doItemInventory.setDelflag(0);
                doItemInventory.setVersion(0L);
                doItemInventory.setCreTime(DateUtil.getNow());
                doItemInventory.setCreator(userDto.getUserName());
                doItemInventory.setModifyTime(DateUtil.getNow());
                doItemInventory.setModifier(userDto.getUserName());
                opsDoItemInventoryMapper.insertSelective(doItemInventory);
            }
        }
        return opsDo;
    }

    @Override
    public void insertDoItemInventory(OpsDoItemInventory doItemInventory) throws OpsException {
        doItemInventory.setDelflag(0);
        doItemInventory.setVersion(0L);
        doItemInventory.setCreTime(new Date());
        doItemInventory.setCreator(UserDto.ADMIN.getUserName());
        opsDoItemInventoryMapper.insertSelective(doItemInventory);
    }

    @Override
    public void insertDoItemInv(OpsDoItemInventory doItemInventory, UserDto userDto) throws OpsException {
        doItemInventory.setDelflag(0);
        doItemInventory.setVersion(0L);
        doItemInventory.setCreTime(new Date());
        doItemInventory.setCreator(userDto.getUserName());
        opsDoItemInventoryMapper.insertSelective(doItemInventory);
    }

    @Override
    public void insertDoItemInventoryList(List<OpsDoItemInventory> doItemInventoryList) throws OpsException {
        for (OpsDoItemInventory doItemInventory : doItemInventoryList) {
            insertDoItemInventory(doItemInventory);
        }
    }


    @Override
    public void updateOpsDoItemInventoryRcv(Long id, Long version, Long inventoryId, InventoryTableTypeEnum inventoryTableTypeEnum, Long fromInventoryId, InventoryTableTypeEnum fromInventoryTableTypeEnum, String userName) {
        OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
        example.createCriteria().andIdEqualTo(id).andVersionEqualTo(version).andDelflagEqualTo(0);
        OpsDoItemInventory record = new OpsDoItemInventory();
        record.setInventoryId(inventoryId);
        record.setInventoryTableType(inventoryTableTypeEnum.getType());
        record.setSrcInventoryId(fromInventoryId);
        record.setSrcInventoryTableType(fromInventoryTableTypeEnum.getType());
        record.setVersion(version + 1);
        record.setModifyTime(new Date());
        record.setModifier(userName);
        opsDoItemInventoryMapper.updateByExampleSelective(record, example);
    }

    /**
     * @description 查询库存ID删除订单库存关联
     * 取消订单调用
     * @author C12961
     * @date 2022/3/10 14:54
     */
    @Override
    public List<OpsDoItemInventory> deleteDoItemInventoryByInventoryId(Long inventoryId, String inventoryTable) throws OpsException {
        List<OpsDoItemInventory> invList = baseDoService.getDoItemInventoryByInventoryId(inventoryId, InventoryTableTypeEnum.getEnumByType(inventoryTable));
        for (OpsDoItemInventory inv : invList) {
            OpsDoItemInventory deleteInv = new OpsDoItemInventory();
            deleteInv.setId(inv.getId());
            deleteInv.setDelflag(1);
            deleteInv.setModifyTime(new Date());
            opsDoItemInventoryMapper.updateByPrimaryKeySelective(deleteInv);
        }
        return invList;
    }

    /**
     * @description：更新占用库存关系表
     * @author ：c02483
     * @date ：Created in 2021/12/28 16:32
     */
    @Override
    public int updateOpsDoItemInventory(Long id, Long version, Integer useQty, Long inventoryId, InventoryTableTypeEnum inventoryTableTypeEnum, UserDto userDto) {
        OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
        example.createCriteria().andIdEqualTo(id).andVersionEqualTo(version).andDelflagEqualTo(0);
        OpsDoItemInventory record = new OpsDoItemInventory();
        record.setInventoryId(inventoryId);
        record.setVersion(version + 1);
        record.setModifyTime(new Date());
        record.setModifier(userDto.getUserName());
        record.setInventoryTableType(inventoryTableTypeEnum.getType());
        record.setUseQty(useQty);
        return opsDoItemInventoryMapper.updateByExampleSelective(record, example);
    }

    /**
     * @description：更新占用库存关系表
     * @author ：c02483
     * @date ：Created in 2021/12/28 16:32
     */
    @Override
    public void updateOpsDoItemInventory(OpsDoItemInventory doItemInventory, UserDto userDto) throws OpsException{
        OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
        example.createCriteria().andIdEqualTo(doItemInventory.getId()).andVersionEqualTo(doItemInventory.getVersion()).andDelflagEqualTo(0);
        OpsDoItemInventory record = new OpsDoItemInventory();
        record.setInventoryId(doItemInventory.getInventoryId());
        record.setInventoryTableType(doItemInventory.getInventoryTableType());
        record.setVersion(doItemInventory.getVersion() + 1);
        record.setModifyTime(new Date());
        record.setModifier(userDto.getUserName());
        int rows = opsDoItemInventoryMapper.updateByExampleSelective(record, example);
        if (rows < 1) {
            throw Exceptions.OpsException("更新itemInv异常" + doItemInventory.getId());
        }
    }

    @Override
    public void updateOpsDo(OpsDo opsDo, UserDto userDto) throws OpsException{
        OpsDoExample example = new OpsDoExample();
        example.createCriteria().andIdEqualTo(opsDo.getId()).andVersionEqualTo(opsDo.getVersion()).andDelflagEqualTo(0);
        OpsDo record = new OpsDo();
        record.setWarehouseCode(opsDo.getWarehouseCode());
        record.setGatherWarehouseCode(opsDo.getGatherWarehouseCode());
        record.setIsWms(opsDo.getIsWms());
        record.setVersion(opsDo.getVersion() + 1);
        record.setWaitType(opsDo.getWaitType());
        record.setModifyTime(new Date());
        record.setModifier(userDto.getUserName());
        int rows = opsDoMapper.updateByExampleSelective(record, example);
        if (rows < 1) {
            throw Exceptions.OpsException("更新do异常" + opsDo.getId());
        }
    }

    /**
     * @description：更新doID
     * @author ：c02483
     * @date ：Created in 2021/12/28 16:32
     */
    @Override
    public void updateOpsDoItemInventoryDoId(Long id, Long version, String doId, UserDto userDto) {
        OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
        example.createCriteria().andIdEqualTo(id).andVersionEqualTo(version).andDelflagEqualTo(0);
        OpsDoItemInventory record = new OpsDoItemInventory();
        record.setDoId(doId);
        record.setVersion(version + 1);
        record.setModifyTime(new Date());
        record.setModifier(userDto.getUserName());
        opsDoItemInventoryMapper.updateByExampleSelective(record, example);
    }

    /**
     * @description：占用或释放库存数量,根据DOItemInventory数量
     * @author ：c02483
     * @date ：Created in 2022/1/4 13:53
     */
    @Override
    public void updatePreQtyForDo(OpsDo opsDo, List<OpsDoItemInventory> opsDoItemInventoryList, UserDto userDto,Map<Long, Boolean> riskMap) throws OpsException {
        for (OpsDoItemInventory inv : opsDoItemInventoryList) {
            //bugid:20641  风险判断
            if(Objects.nonNull(riskMap) && Objects.nonNull(riskMap.get(inv.getInventoryId())) && riskMap.get(inv.getInventoryId())){
                String orderFno = opsDo.getOrderId()+"-"+opsDo.getOrderItem();
                baseInventoryService.addPreQtyRiskInv(inv.getInventoryId(), inv.getUseQty(), inv.getInventoryTableType(), userDto,orderFno);
            }
            baseInventoryService.addPreQtyInventory(inv.getInventoryId(), inv.getUseQty(), inv.getInventoryTableType(), userDto);
        }
    }

    /**
     * @description：增添或减少库存数量,根据DOItemInventory数量
     * @author ：c02483
     * @date ：Created in 2022/1/4 13:53
     */
    @Override
    public void updateQtyForDo(OpsDo opsDo, List<OpsDoItemInventory> opsDoItemInventoryList, UserDto userDto) throws OpsException {
        for (OpsDoItemInventory inv : opsDoItemInventoryList) {
            baseInventoryService.subQtyInventory(inv.getInventoryId(), inv.getOutQty(), inv.getInventoryTableType(), userDto);
            opsInventoryLogService.insertOpsInventoryLogForDo(opsDo, inv.getInventoryId(), inv.getInventoryTableType(), inv.getOutQty(), userDto);
        }
    }



    /**
     * 更改doWaitType
     * 生成调拨单
     */
    @Override
    public void createDBOrder(OpsDo cgDo, OpsPcoItem opsPcoItem,UserDto userDto) throws OpsException {
        log.info("创建调拨单");
        String dbckid = "";
        String dbrkid = "";

        if (Objects.isNull(opsPcoItem)) {
            dbckid = String.format(OrderIDFormatEnum.DBC_FORMAT.getFormat(), cgDo.getOrderId(), String.format("%03d", (Integer.parseInt(cgDo.getOrderItem()))), String.format("%03d", cgDo.getNum()), String.format("%03d", cgDo.getAssNum()));
            dbrkid = String.format(OrderIDFormatEnum.DBR_FORMAT.getFormat(), cgDo.getOrderId(), String.format("%03d", (Integer.parseInt(cgDo.getOrderItem()))), String.format("%03d", cgDo.getNum()), String.format("%03d", cgDo.getAssNum()));

        } else {
            dbckid = String.format(OrderIDFormatEnum.DBC_FORMAT.getFormat(), cgDo.getOrderId(), String.format("%03d", (Integer.parseInt(cgDo.getOrderItem()))), String.format("%03d", 0), String.format("%03d", opsPcoItem.getPcoItem()));
            dbrkid = String.format(OrderIDFormatEnum.DBR_FORMAT.getFormat(), cgDo.getOrderId(), String.format("%03d", (Integer.parseInt(cgDo.getOrderItem()))), String.format("%03d", 0), String.format("%03d", opsPcoItem.getPcoItem()));
        }
        OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(cgDo.getDoId());
        WmOrderByInventoryDto wmOrderByInventoryDto = new WmOrderByInventoryDto();
        //生成调拨单
        initOpsDBDoAndRo(dbckid, dbrkid, cgDo,opsDoItem, opsPcoItem, wmOrderByInventoryDto);
        insertDo(wmOrderByInventoryDto.getDolist().get(0).getOpsDo(), wmOrderByInventoryDto.getDolist().get(0).getOpsDoItem(), null, new UserDto("采购仓库变更", "0.0.0.0"));
        opsRoService.insertRo(wmOrderByInventoryDto.getRoList().get(0).getOpsRo(), wmOrderByInventoryDto.getRoList().get(0).getOpsRoItemList().get(0), null, new UserDto("采购仓库变更", "0.0.0.0"));

        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();

        OpsWmOrderTask opsWmOrderTaskDO = new OpsWmOrderTask();
        opsWmOrderTaskDO.setWmOrderId(dbckid);
        opsWmOrderTaskDO.setWmOrderType(WmOrderTaskEnum.DO.getType());
        opsWmOrderTaskDO.setTaskType(WmOrderTaskEnum.ORDER.getType());
        opsWmOrderTaskDO.setFlag(Integer.valueOf(SendStatusEnum.READ.getType()));
        opsWmOrderTaskDO.setCreator(userDto.getUserName());
        opsWmOrderTaskDO.setCreTime(new Date());
        taskList.add(opsWmOrderTaskDO);
        OpsWmOrderTask opsWmOrderTaskRO = new OpsWmOrderTask();
        opsWmOrderTaskRO.setWmOrderId(dbrkid);
        opsWmOrderTaskRO.setWmOrderType(WmOrderTaskEnum.RO.getType());
        opsWmOrderTaskRO.setTaskType(WmOrderTaskEnum.ORDER.getType());
        opsWmOrderTaskRO.setFlag(Integer.valueOf(SendStatusEnum.READ.getType()));
        opsWmOrderTaskRO.setCreator(userDto.getUserName());
        opsWmOrderTaskRO.setCreTime(new Date());
        taskList.add(opsWmOrderTaskRO);
        //批量更新task
        wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
    }

    @Override
    public void initOpsDBDoAndRo(String dbckid, String dbrkid, OpsDo jyckDo, OpsDoItem jyckDoItem, OpsPcoItem opsPcoItem, WmOrderByInventoryDto wmOrderByInventoryDto) throws OpsException {
        //生成doItem
        OpsDoItem opsDoDBCKItem = new OpsDoItem();
        opsDoDBCKItem.setDoId(dbckid);
        opsDoDBCKItem.setDoItem(1);


        opsDoDBCKItem.setProductName(jyckDoItem.getProductName());
        opsDoDBCKItem.setCproductNo(jyckDoItem.getCproductNo());
        //bugid:9563 c14717 20230208
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(jyckDoItem.getGreenCode())) {
            opsDoDBCKItem.setGreenCode(jyckDoItem.getGreenCode());
        }
        opsDoDBCKItem.setOutQty(0);
        opsDoDBCKItem.setVersion(0);
        opsDoDBCKItem.setDelflag(0);
        opsDoDBCKItem.setCreator("admin");
        opsDoDBCKItem.setModifier("admin");
        opsDoDBCKItem.setCreTime(new Date());
        opsDoDBCKItem.setModifyTime(new Date());

        OpsDo opsDBCKDo = new OpsDo();
        opsDBCKDo.setDoId(dbckid);
        opsDBCKDo.setOrderId(jyckDo.getOrderId());
        opsDBCKDo.setOrderItem(jyckDo.getOrderItem());
        if (Objects.isNull(opsPcoItem)) {
            opsDBCKDo.setNum(jyckDo.getNum());
            opsDBCKDo.setAssNum(jyckDo.getAssNum());
            opsDoDBCKItem.setModelno(jyckDoItem.getModelno());
            opsDoDBCKItem.setQty(jyckDoItem.getQty());
        } else {
            opsDBCKDo.setNum(jyckDo.getNum());
            opsDBCKDo.setAssNum(opsPcoItem.getPcoItem());
            opsDoDBCKItem.setModelno(opsPcoItem.getSubModelno());
            opsDoDBCKItem.setQty(opsPcoItem.getSubQty());
        }

        opsDBCKDo.setExtNum(jyckDo.getExtNum());
        opsDBCKDo.setDoSource(jyckDo.getDoSource());
        opsDBCKDo.setDoType(DoTypeEnum.DBCK.getType());
        opsDBCKDo.setWarehouseCode(jyckDo.getWarehouseCode());
        opsDBCKDo.setGatherWarehouseCode(jyckDo.getGatherWarehouseCode());
        opsDBCKDo.setCustomerNo(jyckDo.getCustomerNo());

        opsDBCKDo.setPakageType(jyckDo.getPakageType());
        opsDBCKDo.setCorderNo(jyckDo.getCorderNo());
        opsDBCKDo.setDoStatus(0);// 初始
        opsDBCKDo.setCarried("");// 指定承运商
        opsDBCKDo.setRemark(jyckDo.getRemark()); // 指定备注内容
        opsDBCKDo.setVersion(0);
        opsDBCKDo.setDelflag(0);
        opsDBCKDo.setCreator("admin");
        opsDBCKDo.setCreTime(new Date());
        opsDBCKDo.setWaitType(DoWaitTypeEnum.OK.getType());
        if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(jyckDo.getDlvEntire())){
            opsDBCKDo.setWlDate(DateUtil.getNow());
        }
        opsDBCKDo.setHopeDate(jyckDo.getHopeDate());
        //CommonResult<OpsWarehouse> result = opsWarehouseFeignApi.searchWarehouse(jyckDo.getGatherWarehouseCode());
        //OpsWarehouse opsWarehouse = result.getData();
        OpsWarehouse opsWarehouse = opsWarehouseDao.queryWarehouseByWarehouseCode(jyckDo.getGatherWarehouseCode());
        if (Objects.isNull(opsWarehouse)) {
            throw Exceptions.OpsException("不存在此仓库号：" + jyckDo.getGatherWarehouseCode());
        }
        //bugid:11445 c14717 2023/07/26 仓库多地址
        opsWarehouseService.getMultAdressSetAddress(opsWarehouse,opsDBCKDo.getDoType());
        opsDBCKDo.setProvince(opsWarehouse.getProvince());
        opsDBCKDo.setCity(opsWarehouse.getCity());
        opsDBCKDo.setDistrict(opsWarehouse.getDistrict());
        opsDBCKDo.setStreet(opsWarehouse.getAdress());
        opsDBCKDo.setAddress(opsWarehouse.getAdress());
        opsDBCKDo.setLinkman(opsWarehouse.getLinkman());
        if (!StringUtils.isEmpty(opsWarehouse.getLinkMobile())) {//手机号
            if (StringPhoneUtil.isMobil(opsWarehouse.getLinkMobile())) {
                opsDBCKDo.setMobile(opsWarehouse.getLinkMobile());
            }
        }
        if (!StringUtils.isEmpty(opsWarehouse.getLinkPhone())) {
            //是电话 且不是手机号 存电话
            if (StringPhoneUtil.isPhone(opsWarehouse.getLinkPhone()) && !StringPhoneUtil.isMobil(opsWarehouse.getLinkPhone())) {
                opsDBCKDo.setPhone(opsWarehouse.getLinkPhone());
            }
        }
        opsDBCKDo.setPostcode(opsWarehouse.getPostCode() + "");
        opsDBCKDo.setDlvSite(jyckDo.getDlvSite());// 是否直发
        opsDBCKDo.setDeptNo(jyckDo.getDeptNo());// 营业所代码
        opsDBCKDo.setExpDlvType(jyckDo.getExpDlvType());
        opsDBCKDo.setExpLinkNo(jyckDo.getExpLinkNo());
        opsDBCKDo.setDlvEntire(jyckDo.getDlvEntire());
        opsDBCKDo.setUserNo(jyckDo.getUserNo());// 专备客户代码

        if (Objects.isNull(opsPcoItem)) {
            List<OpsDoItemInventory> subdoItemInventoryList = baseDoService.getDoItemInventoryByDoId(jyckDo.getDoId());
            for (OpsDoItemInventory subdoItemInventory : subdoItemInventoryList) {
                //
                OpsDoItemInventory updateOpsDoItemInventory = new OpsDoItemInventory();
                updateOpsDoItemInventory.setId(subdoItemInventory.getId());
                updateOpsDoItemInventory.setDoId(dbckid);
                updateOpsDoItemInventory.setModifier("仓库变更");
                updateOpsDoItemInventory.setModifyTime(new Date());
                opsDoItemInventoryMapper.updateByPrimaryKeySelective(updateOpsDoItemInventory);
            }
        } else {
            List<OpsPcoItemInventory> opsPcoItemInventoryList = opsPcoService.selectItemInventoryBypcoId(opsPcoItem.getPcoId(), opsPcoItem.getPcoItem(), null);
            for (OpsPcoItemInventory opsPcoItemInventory : opsPcoItemInventoryList) {
                //删除  OpsPcoItemInventory
                OpsPcoItemInventory opsPcoItemInventoryUpdate = new OpsPcoItemInventory();
                opsPcoItemInventoryUpdate.setId(opsPcoItemInventory.getId());
                opsPcoItemInventoryUpdate.setDelflag(1);
                opsPcoItemInventoryUpdate.setModifier("采购仓库变更");
                opsPcoItemInventoryUpdate.setModifyTime(new Date());
                pcoItemInventoryMapper.updateByPrimaryKeySelective(opsPcoItemInventoryUpdate);
            }
            initOpsDoItemInventorys(dbckid, opsPcoItemInventoryList, new UserDto("采购仓库变更", ""));//生成doItemInventory 并持久化
        }
        //处理doItemInventory
        wmOrderByInventoryDto.getDolist().add(new CreDoByInventoryDto(opsDBCKDo, opsDoDBCKItem, null));
        OpsRo opsRo = new OpsRo();
        opsRo.setRoId(dbrkid);
        opsRo.setRoSource("");// todo 来源待定
        opsRo.setOrderId(jyckDo.getOrderId());
        opsRo.setOrderItem(jyckDo.getOrderItem());
        if (Objects.isNull(opsPcoItem)) {
            opsRo.setNum(jyckDo.getNum());
            opsRo.setAssNum(jyckDo.getAssNum());
        } else {
            opsRo.setNum(jyckDo.getNum());
            opsRo.setAssNum(opsPcoItem.getPcoItem());
        }
        opsRo.setExtNum(jyckDo.getExtNum());
        opsRo.setRoType(RoTypeEnum.DBRK.getType());
        opsRo.setWarehouseCode(jyckDo.getGatherWarehouseCode());
        opsRo.setRoStatus(0);// 初始
        opsRo.setTransType("");// 运输方式
        opsRo.setCarried("");// 到货承运商
        opsRo.setExpressCode("");// 到货承运商
        opsRo.setCustomerNo(jyckDo.getCustomerNo());
        opsRo.setInvoiceNo("");// 到货发票号
        opsRo.setSupplierId("");// 到货供应商
        opsRo.setDelflag(0);
        opsRo.setCreator("admin");
        opsRo.setCreTime(new Date());
        List<OpsRoItem> roItemList = new ArrayList<>();
        OpsRoItem roItem = new OpsRoItem();
        roItem.setRoId(dbrkid);
        roItem.setRoItem(1);
        if (Objects.isNull(opsPcoItem)) {
            roItem.setQty(jyckDoItem.getQty());
            roItem.setModelno(jyckDoItem.getModelno());
        } else {
            roItem.setQty(opsPcoItem.getSubQty());
            roItem.setModelno(opsPcoItem.getSubModelno());
        }
        roItem.setVersion(0);
        roItem.setDelflag(0);
        //bugid:9563 c14717 20230208
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(jyckDoItem.getGreenCode())) {
            roItem.setGreenCode(jyckDoItem.getGreenCode());
        }
        roItem.setCreator("admin");
        roItem.setCreTime(new Date());
        roItemList.add(roItem);
        wmOrderByInventoryDto.getRoList().add(new CreRoByInventoryDto(opsRo, roItemList, null));

    }

    /**
     * 根据pcoItemInventory 生成调拨doItemInventory
     */
    private void initOpsDoItemInventorys(String doid, List<OpsPcoItemInventory> opsPcoItemInventoryList, UserDto userDto) {

        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(opsPcoItemInventoryList)) {
            for (OpsPcoItemInventory opsPcoItemInventory : opsPcoItemInventoryList) {
                OpsDoItemInventory opsDoItemInventory = new OpsDoItemInventory();
                opsDoItemInventory.setDoId(doid);
                opsDoItemInventory.setDoItem(1);
                opsDoItemInventory.setInventoryId(opsPcoItemInventory.getInventoryId());
                opsDoItemInventory.setInventoryTableType(opsPcoItemInventory.getInventoryTableType());
                opsDoItemInventory.setUseQty(opsPcoItemInventory.getUseQty());
                opsDoItemInventory.setVersion(0L);
                opsDoItemInventory.setDelflag(0);
                opsDoItemInventory.setCreTime(new Date());
                opsDoItemInventory.setCreator(userDto.getUserName());
                opsDoItemInventory.setSortnum(opsPcoItemInventory.getSortnum());
                opsDoItemInventoryMapper.insertSelective(opsDoItemInventory);
            }
        }
    }

    //bugid:12601 12836 c14717 20231212 优化 软删除旧itemInv 新增ItemInv
    public void delDoByDoIdChangeItemInventory(Long id, String doId, String orderId, String orderItem, OpsPcoItem opsPcoItem, OpsDo opsJYCKDo, UserDto userDto) throws OpsException {
        OpsDo updateDo = new OpsDo();
        updateDo.setId(id);
        updateDo.setDelflag(1);
        updateDo.setDoStatus(DoStatusEnum.CANCEL.getStatus());
        updateDo.setWaitType(DoWaitTypeEnum.CANCEL.getType());
        updateDo.setModifier(userDto.getUserName());
        updateDo.setModifyTime(new Date());
        opsDoMapper.updateByPrimaryKeySelective(updateDo);

        OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(doId);
        OpsDoItem updateDoItem = new OpsDoItem();
        updateDoItem.setId(opsDoItem.getId());
        updateDoItem.setDelflag(1);
        updateDoItem.setModifier(userDto.getUserName());
        updateDoItem.setModifyTime(new Date());
        opsDoItemMapper.updateByPrimaryKeySelective(updateDoItem);
        List<OpsDoItemInventory> subdoItemInventoryList = baseDoService.getDoItemInventoryByDoId(doId);
        if (Objects.isNull(opsPcoItem)) {
            for (OpsDoItemInventory subdoItemInventory : subdoItemInventoryList) {
                //释放库存 在途表  在库表
                OpsDoItemInventory updateOpsDoItemInventory = new OpsDoItemInventory();
                updateOpsDoItemInventory.setId(subdoItemInventory.getId());
                //updateOpsDoItemInventory.setDoId(opsJYCKDo.getDoId());
                updateOpsDoItemInventory.setDelflag(1);
                updateOpsDoItemInventory.setModifier(userDto.getUserName());
                updateOpsDoItemInventory.setModifyTime(new Date());
                opsDoItemInventoryMapper.updateByPrimaryKeySelective(updateOpsDoItemInventory);

                OpsDoItemInventory newDoItemInv = new OpsDoItemInventory();
                newDoItemInv.setDoId(opsJYCKDo.getDoId());
                newDoItemInv.setDoItem(subdoItemInventory.getDoItem());
                newDoItemInv.setInventoryId(subdoItemInventory.getInventoryId());
                newDoItemInv.setInventoryTableType(subdoItemInventory.getInventoryTableType());
                newDoItemInv.setUseQty(subdoItemInventory.getUseQty());
                newDoItemInv.setOutQty(subdoItemInventory.getOutQty());
                newDoItemInv.setSrcInventoryId(subdoItemInventory.getSrcInventoryId());
                newDoItemInv.setSrcInventoryTableType(subdoItemInventory.getSrcInventoryTableType());
                newDoItemInv.setDelflag(0);
                newDoItemInv.setCreTime(new Date());
                newDoItemInv.setCreator(userDto.getUserName());
                opsDoItemInventoryMapper.insertSelective(newDoItemInv);
            }
        } else {
            List<OpsPcoItemInventory> pcoInventoryList = new ArrayList<OpsPcoItemInventory>();
            for (OpsDoItemInventory subdoItemInventory : subdoItemInventoryList) {
                //释放库存 在途表  在库表
                OpsDoItemInventory updateOpsDoItemInventory = new OpsDoItemInventory();
                updateOpsDoItemInventory.setId(subdoItemInventory.getId());
                updateOpsDoItemInventory.setDelflag(1);
                updateOpsDoItemInventory.setModifier(userDto.getUserName());
                updateOpsDoItemInventory.setModifyTime(new Date());
                opsDoItemInventoryMapper.updateByPrimaryKeySelective(updateOpsDoItemInventory);
                OpsPcoItemInventory pcoItemInventory = initOpsPcoItemInventory(opsPcoItem.getPcoId(), opsPcoItem.getPcoItem(), subdoItemInventory.getUseQty(), subdoItemInventory.getSortnum(),
                        subdoItemInventory.getInventoryId(), subdoItemInventory.getInventoryTableType(), "admin");
                pcoInventoryList.add(pcoItemInventory);
            }
            if (!CollectionUtils.isEmpty(pcoInventoryList)) {
                for (OpsPcoItemInventory item : pcoInventoryList) {
                    item.setCreTime(new Date());
                    item.setCreator(userDto.getUserName());
                    item.setModifyTime(new Date());
                    item.setModifier(userDto.getUserName());
                    pcoItemInventoryMapper.insertSelective(item);
                }
            }
        }


    }

    private OpsPcoItemInventory initOpsPcoItemInventory(String pcoId, int pcoItem, Integer useQty, Integer sortnum,
                                                        Long inventoryId, String inventoryTableTypeEnum, String creator) {
        OpsPcoItemInventory pcoItemInventory = new OpsPcoItemInventory();
        pcoItemInventory.setPcoId(pcoId);
        pcoItemInventory.setPcoItem(pcoItem);
        pcoItemInventory.setPcoType(1);
        pcoItemInventory.setUseQty(useQty);
        pcoItemInventory.setInventoryId(inventoryId);
        pcoItemInventory.setInventoryTableType(inventoryTableTypeEnum);
        pcoItemInventory.setVersion(0L);
        pcoItemInventory.setDelflag(0);
        pcoItemInventory.setCreTime(new Date());
        pcoItemInventory.setCreator(creator);
        pcoItemInventory.setSortnum(sortnum);
        return pcoItemInventory;
    }

    /**
     * 删除订单物流指令日志主表
     */
    private Long delOrderReturnCancelId(String orderId, String orderItem, String orderType, String msg, UserDto userDto) throws OpsException {
        CancelForOrderDto inputDto = new CancelForOrderDto();
        inputDto.setOrderId(orderId);
        inputDto.setOrderItem(orderItem);
        inputDto.setOrderType(orderType);
        inputDto.setReason(msg);
        inputDto.setUserDto(userDto);
        return insertOrderCancel(inputDto);

    }

    /**
     * 删除do
     */
    @Override
    public void delDoChangeItemInventory(OpsDo opsDo, Long cancelId, DoTypeEnum doTypeEnum, OpsPcoItem opsPcoItem, OpsDo opsJYCKDo) throws OpsException {
        wmOrderTaskService.delWmOrderTask(opsDo.getDoId(), "仓库变更");
        delDoByDoIdChangeItemInventory(opsDo.getId(), opsDo.getDoId(), opsDo.getOrderId(), opsDo.getOrderItem(), opsPcoItem, opsJYCKDo, UserDto.ADMIN);
        insertOrderCancelItem(cancelId, opsDo.getDoId(), doTypeEnum.getType(), "仓库变更", UserDto.ADMIN);
    }

    /**
     * 删除ro调拨单
     */
    @Override
    public void delDBRo(OpsDo opsDo, Long cancelId, String roType) throws OpsException {
        List<OpsRo> opsRos = baseRoService.findRoByOrderItemNum(opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()),
                opsDo.getNum(), opsDo.getAssNum(),opsDo.getExtNum(), roType);
        if (org.springframework.util.CollectionUtils.isEmpty(opsRos)) {
            throw Exceptions.OpsException(opsDo.getDoId() + "，无RO数据");
        }
        if (opsRos.size() > 1) {
            throw Exceptions.OpsException(opsDo.getDoId() + "，RO数据重复" + opsRos.get(0).getRoId());
        }
        opsRoService.delOpsRoOrder(cancelId, opsRos.get(0), "采购仓库变更", UserDto.ADMIN);
    }

    //todo 无引用方法 更新task
    private void updateWmOrderTask(Long wmOrderTaskId, String msg, Integer flag) throws OpsException {
        OpsWmOrderTask record = new OpsWmOrderTask();
        record.setId(wmOrderTaskId);
        record.setFlag(flag);
        record.setMsg(msg);
        record.setModifyTime(new Date());
        record.setModifier("wms");
        opsWmOrderTaskMapper.updateByPrimaryKeySelective(record);
    }


    @Override
    public CreDoByInventoryDto createAssQtyDo(String formatDoId,Integer splitNum, OpsDo oldOpsDo, OpsDoItem oldOpsDoItem, int qty, UserDto userDto, DoTypeEnum doTypeEnum,String waitType) throws OpsException {
        String doid = String.format(formatDoId, oldOpsDo.getOrderId(),
                    String.format("%03d", Integer.parseInt(oldOpsDo.getOrderItem())),
                    String.format("%03d", splitNum), String.format("%03d", 0));
        // 调库出库TKCK的分纳，OrderItem是四位
        if (DoTypeEnum.TKCK.getType().equalsIgnoreCase(doTypeEnum.getType())) {
            doid = WmOrderNoFactory.TKCK_FN(oldOpsDo.getOrderId(),Integer.parseInt(oldOpsDo.getOrderItem()),splitNum,0);
        }
        OpsDoItem opsDoItem = new OpsDoItem();
        opsDoItem.setDoId(doid);
        opsDoItem.setDoItem(1);
        opsDoItem.setModelno(oldOpsDoItem.getModelno());
        opsDoItem.setProductName(oldOpsDoItem.getProductName());
        opsDoItem.setCproductNo(oldOpsDoItem.getCproductNo());
        opsDoItem.setGreenCode(oldOpsDoItem.getGreenCode());
        opsDoItem.setQty(qty);
        opsDoItem.setOutQty(0);
        opsDoItem.setVersion(0);
        opsDoItem.setDelflag(0);
        opsDoItem.setCreator(userDto.getUserName());
        opsDoItem.setCreTime(new Date());
        DoSourceEnum doSourceEnum = DoSourceEnum.ASSQTY;
        OpsDo opsDo = new OpsDo();
        opsDo.setDoId(doid);
        opsDo.setOrderId(oldOpsDo.getOrderId());
        opsDo.setOrderItem(oldOpsDo.getOrderItem());
        opsDo.setNum(splitNum);
        opsDo.setAssNum(0);
        opsDo.setExtNum(0);
        opsDo.setDoSource(doSourceEnum.getType());
        opsDo.setDoType(doTypeEnum.getType());
        opsDo.setWarehouseCode(oldOpsDo.getWarehouseCode());
        opsDo.setSpceialFlag(oldOpsDo.getSpceialFlag());
        opsDo.setGatherWarehouseCode(oldOpsDo.getGatherWarehouseCode());
        opsDo.setCustomerNo(oldOpsDo.getCustomerNo());

        opsDo.setPakageType(oldOpsDo.getPakageType());
        opsDo.setCorderNo(oldOpsDo.getCorderNo());
        opsDo.setDoStatus(0);// 初始
        opsDo.setCarried(oldOpsDo.getCarried());// 指定承运商
        opsDo.setRemark(oldOpsDo.getRemark()); // 指定备注内容
        opsDo.setVersion(0);
        opsDo.setDelflag(0);
        opsDo.setCreator(userDto.getUserName());
        opsDo.setCreTime(new Date());
        opsDo.setWaitType(waitType);
        opsDo.setHopeDate(oldOpsDo.getHopeDate());
        opsDo.setWlDate(oldOpsDo.getWlDate());

        opsDo.setProvince(oldOpsDo.getProvince());
        opsDo.setCity(oldOpsDo.getCity());
        opsDo.setDistrict(oldOpsDo.getDistrict());
        opsDo.setStreet(oldOpsDo.getStreet());
        opsDo.setAddress(oldOpsDo.getAddress());
        opsDo.setLinkman(oldOpsDo.getLinkman());
        opsDo.setMobile(oldOpsDo.getMobile());
        opsDo.setPhone(oldOpsDo.getPhone());
        opsDo.setPostcode(oldOpsDo.getPostcode());

        opsDo.setDlvSite(oldOpsDo.getDlvSite());// 是否直发
        opsDo.setDeptNo(oldOpsDo.getDeptNo());// 营业所代码
        opsDo.setExpDlvType(oldOpsDo.getExpDlvType());
        opsDo.setExpLinkNo(oldOpsDo.getExpLinkNo());
        opsDo.setDlvEntire(oldOpsDo.getDlvEntire());
        opsDo.setUserNo(oldOpsDo.getUserNo());// 专备客户代码
        return new CreDoByInventoryDto(opsDo, opsDoItem, null);
    }

    @Override
    public void updateOpsDoForCrossRoId(String doId, int roCrossType, String roId)  {
        OpsDoExample example = new OpsDoExample();
        example.createCriteria().andDelflagEqualTo(0).andDoIdEqualTo(doId);
        OpsDo updateOpsDo = new OpsDo();
        updateOpsDo.setRoId(roId);
        updateOpsDo.setRoCrossType(roCrossType);
        updateOpsDo.setModifier("wms");
        updateOpsDo.setModifyTime(new Date());
        opsDoMapper.updateByExampleSelective(updateOpsDo, example);
    }

    /**
     * 处理异常数据 生成调拨单
     */
    @Override
    public String handleAbnormalData(String doId, String pcoId, Integer pcoItem, String signWarehouseCode, String roId) throws OpsException {
        OpsDo cgDo = baseDoService.getDoByDoId(doId);

        if (Objects.isNull(cgDo)) {
            throw Exceptions.OpsException("无此交易出库单");
        }
        if (!DoTypeEnum.JYCK.getType().equals(cgDo.getDoType())) {
            throw Exceptions.OpsException("非交易出库单");
        }
        if (signWarehouseCode.equals(cgDo.getGatherWarehouseCode())) {
            throw Exceptions.OpsException("实际仓库和集约仓库一致,不需要生成调拨单");
        }
        if (StringUtils.isEmpty(signWarehouseCode)) {
            throw Exceptions.OpsException("发货仓库号空");
        }
        /*if(1==cgDo.getIsWms()){
            throw Exceptions.OpsException("do已下发wms");
        }*/
        if (DoStatusEnum.FINISH.getStatus().equals(cgDo.getDoStatus())) {
            throw Exceptions.OpsException("物流已出库", cgDo.getDoId());
        }
        //
        /*if (DoStatusEnum.PART.getStatus().equals(cgDo.getDoStatus())) {
            throw Exceptions.OpsException("物流已出库", cgDo.getDoId());
        }*/
        //设置发货仓仓库号
        cgDo.setWarehouseCode(signWarehouseCode);
        //修改等待类型
        if (DoSourceEnum.ASSModelNo.getType().equals(cgDo.getDoSource())) {

            if (StringUtils.isEmpty(pcoId) || Objects.isNull(pcoItem)) {
                throw Exceptions.OpsException("pcoId不存在");
            }
            OpsPco opsPco = opsPcoService.getPcoByPcoId(pcoId);
            if (Objects.isNull(opsPco)) {
                throw Exceptions.OpsException("无此交易出库单");
            }
            /*if(1==opsPco.getIsWms()){
                throw Exceptions.OpsException("pco已下发wms");
            }*/
            OpsPcoItem opsPcoItem = opsPcoService.getPcoItemByPcoIdAndItem(pcoId, pcoItem);
            opsPcoService.updatetOpsPcoItem(opsPcoItem.getId(), DoWaitTypeEnum.WaitDB.getType(), new UserDto("异常数据生成调拨单", ""));
            //生成调拨单 task flag = 4 更改pcoItemInventory 和 doItemInventory 更改到调拨出上面
            return createAbnormalDataDBOrder(cgDo, opsPcoItem, roId);
        } else {
            updateDoWaitTypeAndWareHouseCodeForId(cgDo.getId(), null, cgDo.getGatherWarehouseCode(), cgDo.getWarehouseCode(),
                    DoWaitTypeEnum.WaitDB.getType(), cgDo.getVersion(), "异常数据生成调拨单", 0, null);
            //生成调拨单 task flag = 4 更改pcoItemInventory 和 doItemInventory 更改到调拨出上面
            return createAbnormalDataDBOrder(cgDo, null, roId);
        }
    }

    /**
     * 异常数据生成调拨单
     */
    private String createAbnormalDataDBOrder(OpsDo cgDo, OpsPcoItem opsPcoItem, String roId) throws OpsException {
        log.info("创建调拨单");
        String dbckid = "";
        String dbrkid = "";
        if (Objects.isNull(opsPcoItem)) {
            dbckid = String.format(OrderIDFormatEnum.DBC_YC_FORMAT.getFormat(), cgDo.getOrderId(), String.format("%03d", (Integer.parseInt(cgDo.getOrderItem()))), String.format("%03d", cgDo.getNum()), String.format("%03d", 0));
            dbrkid = String.format(OrderIDFormatEnum.DBR_YC_FORMAT.getFormat(), cgDo.getOrderId(), String.format("%03d", (Integer.parseInt(cgDo.getOrderItem()))), String.format("%03d", cgDo.getNum()), String.format("%03d", 0));

        } else {
            dbckid = String.format(OrderIDFormatEnum.DBC_YC_FORMAT.getFormat(), cgDo.getOrderId(), String.format("%03d", (Integer.parseInt(cgDo.getOrderItem()))), String.format("%03d", 0), String.format("%03d", opsPcoItem.getPcoItem()));
            dbrkid = String.format(OrderIDFormatEnum.DBR_YC_FORMAT.getFormat(), cgDo.getOrderId(), String.format("%03d", (Integer.parseInt(cgDo.getOrderItem()))), String.format("%03d", 0), String.format("%03d", opsPcoItem.getPcoItem()));
        }
        OpsDoItem opsDoItem = baseDoService.getDoItemByDoId(cgDo.getDoId());
        WmOrderByInventoryDto wmOrderByInventoryDto = new WmOrderByInventoryDto();
        /**
         * 验证仓库号
         */
        if (Objects.isNull(opsPcoItem)) {
            List<OpsDoItemInventory> subdoItemInventoryList = baseDoService.getDoItemInventoryByDoId(cgDo.getDoId());
            for (OpsDoItemInventory subdoItemInventory : subdoItemInventoryList) {
                if (InventoryTableTypeEnum.NORMAL.getType().equals(subdoItemInventory.getInventoryTableType())) {
                    OpsInventory inventory = baseInventoryService.getInventoryById(subdoItemInventory.getInventoryId());
                    if (ObjectUtil.isNotNull(inventory)) {
                        if (!inventory.getWarehouseCode().equals(cgDo.getWarehouseCode())) {
                            throw Exceptions.OpsException("关联库存仓库号和货物实际仓库号不一致");
                        }
                    }
                }
                if (InventoryTableTypeEnum.TRANS.getType().equals(subdoItemInventory.getInventoryTableType())) {
                    OpsInventoryMove inventory = baseInventoryService.getInventoryMoveById(subdoItemInventory.getInventoryId());
                    if (ObjectUtil.isNotNull(inventory)) {
                        if (!inventory.getSignWarehouseCode().equals(cgDo.getWarehouseCode())) {
                            throw Exceptions.OpsException("关联库存仓库号和货物实际仓库号不一致");
                        }
                    }
                }
            }
        } else {
            List<OpsPcoItemInventory> opsPcoItemInventoryList = opsPcoService.selectItemInventoryBypcoId(opsPcoItem.getPcoId(), opsPcoItem.getPcoItem(), null);
            for (OpsPcoItemInventory opsPcoItemInventory : opsPcoItemInventoryList) {
                if (InventoryTableTypeEnum.NORMAL.getType().equals(opsPcoItemInventory.getInventoryTableType())) {
                    OpsInventory inventory = baseInventoryService.getInventoryById(opsPcoItemInventory.getInventoryId());
                    if (ObjectUtil.isNotNull(inventory)) {
                        if (!inventory.getWarehouseCode().equals(cgDo.getWarehouseCode())) {
                            throw Exceptions.OpsException("关联库存仓库号和货物实际仓库号不一致");
                        }
                    }
                }
                if (InventoryTableTypeEnum.TRANS.getType().equals(opsPcoItemInventory.getInventoryTableType())) {
                    OpsInventoryMove inventory = baseInventoryService.getInventoryMoveById(opsPcoItemInventory.getInventoryId());
                    if (ObjectUtil.isNotNull(inventory)) {
                        if (!inventory.getSignWarehouseCode().equals(cgDo.getWarehouseCode())) {
                            throw Exceptions.OpsException("关联库存仓库号和货物实际仓库号不一致");
                        }
                    }
                }
            }
        }

        //生成调拨单
        initOpsDBDoAndRo(dbckid, dbrkid, cgDo, opsDoItem, opsPcoItem, wmOrderByInventoryDto);
        if (!StringUtils.isEmpty(roId)) {
            wmOrderByInventoryDto.getDolist().get(0).getOpsDo().setRoCrossType(1);//上预约
        }
        insertDo(wmOrderByInventoryDto.getDolist().get(0).getOpsDo(), wmOrderByInventoryDto.getDolist().get(0).getOpsDoItem(), null, new UserDto("异常数据生成调拨单", "0.0.0.0"));
        opsRoService.insertRo(wmOrderByInventoryDto.getRoList().get(0).getOpsRo(), wmOrderByInventoryDto.getRoList().get(0).getOpsRoItemList().get(0), null, new UserDto("异常数据生成调拨单", "0.0.0.0"));

        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();
        OpsWmOrderTask opsWmOrderTaskDO = new OpsWmOrderTask();
        opsWmOrderTaskDO.setWmOrderId(dbckid);
        opsWmOrderTaskDO.setWmOrderType(WmOrderTaskEnum.DO.getType());
        opsWmOrderTaskDO.setTaskType(WmOrderTaskEnum.ORDER.getType());
        opsWmOrderTaskDO.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.getType()));
        opsWmOrderTaskDO.setCreator("异常数据生成调拨单");
        opsWmOrderTaskDO.setCreTime(new Date());
        taskList.add(opsWmOrderTaskDO);
        //wmOrderTaskService.addOpsWmOrderTask(opsWmOrderTaskDO);
        OpsWmOrderTask opsWmOrderTaskRO = new OpsWmOrderTask();
        opsWmOrderTaskRO.setWmOrderId(dbrkid);
        opsWmOrderTaskRO.setWmOrderType(WmOrderTaskEnum.RO.getType());
        opsWmOrderTaskRO.setTaskType(WmOrderTaskEnum.ORDER.getType());
        opsWmOrderTaskRO.setFlag(Integer.valueOf(SendStatusEnum.SUSPENDED.getType()));
        opsWmOrderTaskRO.setCreator("异常数据生成调拨单");
        opsWmOrderTaskRO.setCreTime(new Date());
        taskList.add(opsWmOrderTaskRO);
        //批量更新task
        wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
        //wmOrderTaskService.addOpsWmOrderTask(opsWmOrderTaskRO);
        return dbckid;
    }

    /**
     * @description：更新出库数量
     */
    @Override
    public void updateOpsDoItemInventoryOutQty(OpsDoItemInventory opsDoItemInventory, Integer outQty, String userName) throws OpsException {
        OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
        example.createCriteria().andIdEqualTo(opsDoItemInventory.getId()).andVersionEqualTo(opsDoItemInventory.getVersion()).andDelflagEqualTo(0);
        OpsDoItemInventory doItemInventory = new OpsDoItemInventory();
        doItemInventory.setVersion(opsDoItemInventory.getVersion() + 1);
        doItemInventory.setModifyTime(new Date());
        doItemInventory.setModifier(userName);
        doItemInventory.setOutQty(outQty);
        //完全转移后，改为已删除（outQty=opsDoItemInventory.getOutQty() +传入数量 ）
        int i = opsDoItemInventoryMapper.updateByExampleSelective(doItemInventory, example);
        if(i<1){
            throw Exceptions.OpsException("系统并发异常，Do出库数修改失败");
        }
    }
    /**
     * @description：更新出库数量
     * @author ：
     * @date ：Created in 2022/10/23 16:32
     */
    @Override
    public void updateOpsDoItemOutQty(String doId, Integer outQty,Integer version, String userName) throws OpsException {
        OpsDoItemExample example = new OpsDoItemExample();
        example.createCriteria().andDoIdEqualTo(doId).andVersionEqualTo(version).andDelflagEqualTo(0);
        OpsDoItem doItem = new OpsDoItem();
        doItem.setModifyTime(new Date());
        doItem.setModifier(userName);
        doItem.setOutQty(outQty);
        int i = opsDoItemMapper.updateByExampleSelective(doItem, example);
        if(i<1){
            throw Exceptions.OpsException("系统并发异常，Do出库数修改失败");
        }
    }
    @Override
    public int updateOpsDoStatus(String doId, int status, int version, String userName)  {
        OpsDo updateDo = new OpsDo();
        updateDo.setDoStatus(status);
        updateDo.setModifier(userName);
        updateDo.setModifyTime(new Date());
        updateDo.setVersion(version + 1);
        OpsDoExample ex = new OpsDoExample();
        OpsDoExample.Criteria criteria = ex.createCriteria();
        criteria.andDelflagEqualTo(0).andVersionEqualTo(version).andDoIdEqualTo(doId);// 收货指令
        return opsDoMapper.updateByExampleSelective(updateDo, ex);
    }

    /**
     * 获取OpsDoItemInventory 按来源ID
     * @param inventoryTableTypeEnum 当前行类型（不是src_InventoryTableType)
     */
    @Override
    public OpsDoItemInventory getOpsDoItemInventoryBySrcInventoryId(String doId, Long srcInventoryId, InventoryTableTypeEnum inventoryTableTypeEnum) throws OpsException {
        OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
        example.createCriteria().andDoIdEqualTo(doId).andDelflagEqualTo(0)
                .andSrcInventoryIdEqualTo(srcInventoryId).andInventoryTableTypeEqualTo(inventoryTableTypeEnum.getType());

        List<OpsDoItemInventory> list = opsDoItemInventoryMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (list.size() > 1) {
            throw Exceptions.OpsException("doID在途来源ID不允许多行，DOID:" + doId + "，来源srcInventoryId：" + srcInventoryId);
        }
        return list.get(0);
    }


    /**
     *  bugid：17339 c14717 20250403 20250536 代码回退
     * bugid:10582 c14717 20230621
     * 计算物流交货期
     * @param dlvDateParam
     * @param fWareHoseCode
     * @param tWareHouseCode
     * @param orderNo
     * @return
     * @throws OpsException
     */
    @Override
    public Date tkckWlDay(Date dlvDateParam, String fWareHoseCode, String tWareHouseCode, String orderNo) throws OpsException{
        /**
         * 1 客户订单物流发货日=客户交货期-国内运输时间
         * 2 客户订单物流发货日<系统日期时，客户订单物流发货日=工作日期 +2
         * 3 客户订单物流发货日>=系统日期时,客户订单物流发货日=1中计算的客户订单物流发货日
         */
        if(Objects.isNull(dlvDateParam)){
            TblWorkdayYear workDay = tblWorkDayYearDao.getWorkDay(DateUtil.dateToString(DateUtil.getNow())).get(2);
            return workDay.getWorkdayDate();//物流交货期 工作日+2两个工作日
        }
        CommonResult<Integer> findDBCKDDResult =  wmCommonService.getWarehouseDeliveryDayByCode(fWareHoseCode,tWareHouseCode);
        if(!findDBCKDDResult.isSuccess()){
            throw Exceptions.OpsException(orderNo, findDBCKDDResult.getMessage());
        }
        Date countDate = DateUtil.addDay(dlvDateParam,-findDBCKDDResult.getData());
        Long diffDay =  DateUtil.getDiffDay(DateUtil.getNow(),countDate);//计算时间-系统时间
        if(diffDay < 0){
            // 工作日+2
            TblWorkdayYear workDay = tblWorkDayYearDao.getWorkDay(DateUtil.dateToString(DateUtil.getNow())).get(2);
            return workDay.getWorkdayDate();//物流交货期 系统时间+2
        }else {
            return countDate;//物流交货期
        }
    }


    /**
     * bugid:16197 c14717 2024/12/18
     * 更新物流交货期和客户期望交货期
     * @param orderId
     * @param orderItem
     * @param dlvDateParam
     * @param updateRcvFlag
     * @param userDto
     * @return
     * @throws OpsException
     */
    public  Map<String, String> updateDoPcoWlday(String orderId, String orderItem, Date dlvDateParam, Boolean updateRcvFlag, UserDto userDto)throws OpsException{
        //非订单分配
        if(updateRcvFlag){
            Rcvmaster rcvMaster = baseCustomerOrderService.findRcvMaster(orderId);
            Map<String, String> result = new HashMap<String, String>();
            //1.通知发货 不修改货期
            if(CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(rcvMaster.getDlvEntire())){
                result.put(orderId+"-"+orderItem, "通知发货不需修改，仅按计划日期发货");
                //通知发货不修改货期
                return result;
            }
            // 2.货齐 追条重新计算  bugid:16197 c14717 2024/12/25 非门户修改期望货期 考虑发货方式更新物流指定发货日
            if(Objects.isNull(dlvDateParam)){
                if(CKTYPEEnum.ORDER_GETHER_SIGNLE_HOUSE.getCode().equals(rcvMaster.getDlvEntire())
                        || CKTYPEEnum.ORDER_GETHER_MULTI_HOUSE.getCode().equals(rcvMaster.getDlvEntire())){
                    List<Rcvdetail> rcvList = baseCustomerOrderService.findRcvDetail(orderId);
                    for(Rcvdetail rcv : rcvList) {
                        Map<String, String> stringStringMap = updateDoPcoWldayV1(rcv.getRorderNo(), rcv.getRorderItem().toString(), dlvDateParam, updateRcvFlag, userDto);
                        result.putAll(stringStringMap);
                    }
                    return result;
                }
            }
        }
        // 3.其他仅计算当前
        return updateDoPcoWldayV1(orderId,orderItem,dlvDateParam,updateRcvFlag,userDto);
    }

    /**
     * bugid：17339 c14717 20250403 20250526 代码回退
     * bugid:10345 20230410 c14717 更新物流交货期和客户期望交货期
     * 订单分配；订单修改，客户货期修改，随发分批（集约仓变更），子项特发（集约仓变更）；外部到货(集约仓变更，包括异仓收货)；
     * @param orderId
     * @param orderItem
     * @param dlvDateParam 客户交货期  如果不为空是订单修改 物流交货期
     * @throws OpsException
     * 1.十位订单号查找jyck_do_list;
     * 2.计算交易单的物流期望交货期;
     * 3.拆分型号需要同时修改do和pco以及wms;
     * 4.非拆分型号修改do以及wms;
     * 5.计算非拆分调拨单物流期望交货期;
     * 6.非拆分型号需要修改调拨do以及wms;
     * 7.计算拆分型号调拨单物流期望交货期;
     * 8.修改拆分型号调拨do以及wms;
     */
    @Override
    public Map<String, String> updateDoPcoWldayV1(String orderId, String orderItem, Date dlvDateParam, Boolean updateRcvFlag, UserDto userDto) throws OpsException{
        Map<String, String> result = new HashMap<String, String>();
        //1.十位订单号查找do 获取交易出库列表
        List<OpsDo> selectJYCKDoList = baseDoService.findAllJYCKByOrder(orderId,orderItem);
        //加工单物流交货期
        Date pcoWmsDate = null;
        //客户交货期
        Date dlvDate = null;
        //获取交易出库单的物流发货期
        HashSet<Date> rcvWmsDlvDateSet = new HashSet<Date>();
        //交易出库单物流发货日是系统时间标记
        boolean wmsDateIsSystemTime = false;
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(selectJYCKDoList)){
            for(OpsDo jyckDo : selectJYCKDoList){
                UpdateForOrderDto dto = new UpdateForOrderDto();
                //是否更新客户交货期
                if(Objects.nonNull(dlvDateParam)){
                    dto.setDlvDate(dlvDateParam);//客户交货期
                }else {
                    dto.setDlvDate(jyckDo.getHopeDate());
                }
                dlvDate = dto.getDlvDate();
                ////2.计算交易单的物流期望交货期
                CommonResult<Integer> findDoDDResult = wmCommonService.getWarehouseSalesbranchConfigByCode(jyckDo.getDeptNo(),jyckDo.getGatherWarehouseCode());
                if(findDoDDResult.isSuccess()){
                    /**
                     * 1 客户订单物流发货日=客户交货期-国内运输时间
                     * 2 客户订单物流发货日<系统日期时，客户订单物流发货日=系统日期
                     * 3 客户订单物流发货日>=系统日期时,客户订单物流发货日=1中计算的客户订单物流发货日
                     */
                    Date countDate = DateUtil.addDay(dto.getDlvDate(),-findDoDDResult.getData());
                    Long diffDay =  DateUtil.getDiffDay(DateUtil.getNow(),countDate);//计算时间-系统时间
                    if(diffDay < 0){
                        dto.setWmsDlvDate(DateUtil.getNow());//物流交货期 系统时间
                        wmsDateIsSystemTime = true;//无需计算调拨单的物流交货期
                    }else {
                        dto.setWmsDlvDate(countDate);//物流交货期
                    }
                }else {
                    throw Exceptions.OpsException(jyckDo.getDoId(), findDoDDResult.getMessage());
                }
                //3.拆分型号需要同时修改do和pco 以及wms;
                if(DoSourceEnum.ASSModelNo.getType().equals(jyckDo.getDoSource())){
                    pcoWmsDate = dto.getWmsDlvDate();
                    //获取加工单
                    OpsPco selectPcoReslut = opsPcoService.findPcoByOrder(orderId,orderItem);
                    //是否下发wms
                    if (1 == jyckDo.getIsWms()) {//拆分型号
                        //调用WMS变更接口
                        if (selectPcoReslut.getIsWms() != 1) {
                            result.put(jyckDo.getDoId(), "数据异常，请联系it人员处理");
                            continue;
                        }
                        OpsSendPcoAndDoMidIDDto pcoAndDoMidIDDto = new OpsSendPcoAndDoMidIDDto();
                        pcoAndDoMidIDDto.setDoId(jyckDo.getDoId());
                        pcoAndDoMidIDDto.setPcoId(selectPcoReslut.getPcoId());
                        pcoAndDoMidIDDto.setUpdateOpsDo(fillInUpdateInfo(jyckDo, dto));
                        pcoAndDoMidIDDto.setUdateOpsPco(fillInUpdatePcoInfo(selectPcoReslut, dto));
                        //bugids:12187 c14717 20230921
                        //CommonResult resWm = wmDoService.updateWMSPcoAddDoTwo(pcoAndDoMidIDDto);
                        CommonResult<String> resWm = wmDoService.postWmsDoAndPcoNew(pcoAndDoMidIDDto);
                        if (resWm.isSuccess()) {
                            updateDoDeliveryDay(jyckDo, dto);
                            updatePcoDeliveryDay(selectPcoReslut, dto);
                            //获取交易出库单的物流发货期
                            rcvWmsDlvDateSet.add(dto.getWmsDlvDate());
                            result.put(jyckDo.getDoId(), "1");
                        } else {
                            result.put(jyckDo.getDoId(), "WMS已经处理，不允许变更，如需变更，请联系WMS担当处理");
                        }

                    } else if (2 == jyckDo.getIsWms()) {
                        throw Exceptions.OpsException(jyckDo.getDoId(), "数据异常，请联系it人员处理");
                    } else {
                        updatePcoDeliveryDay(selectPcoReslut, dto);
                        updateDoDeliveryDay(jyckDo, dto);
                        //获取交易出库单的物流发货期
                        rcvWmsDlvDateSet.add(dto.getWmsDlvDate());
                        result.put(jyckDo.getDoId(), "1");
                    }
                }else {//4.非拆分型号修改do以及wms
                    //是否下发wms
                    if (1 == jyckDo.getIsWms()) {//拆分型号
                        //调用WMS变更接口
                        //非拆分型号
                        OpsDoAndItemDto doDto = findDoToWms(jyckDo.getDoId());
                        doDto.setOpsDo(fillInUpdateInfo(jyckDo, dto));
                        /*OpsWmOrderTask opsWmOrderTaskDo = findWmsOrderTaskService.findWmsOrderTaskByWmOrderId(jyckDo.getDoId(), WmOrderTaskEnum.DO);
                        doDto.setWmOrderTaskId(opsWmOrderTaskDo.getId());*/
                        //bugids:12187 c14717 20230921
                        //CommonResult resWm = opsCallWmsFeignApi.updateDoToWms(doDto);
                        CommonResult<String> resWm = wmDoService.postWmsDoNew(doDto);
                        if (resWm.isSuccess()) {
                            updateDoDeliveryDay(jyckDo, dto);
                            //获取交易出库单的物流发货期
                            rcvWmsDlvDateSet.add(dto.getWmsDlvDate());
                            result.put(jyckDo.getDoId(), "1");
                        } else {
                            result.put(jyckDo.getDoId(), "WMS已经处理，不允许变更，如需变更，请联系WMS担当处理");
                        }
                    } else if (2 == jyckDo.getIsWms()) {
                        result.put(jyckDo.getDoId(), "数据异常，请联系it人员处理");
                    } else {
                        updateDoDeliveryDay(jyckDo, dto);
                        //获取交易出库单的物流发货期
                        rcvWmsDlvDateSet.add(dto.getWmsDlvDate());
                        result.put(jyckDo.getDoId(), "1");
                    }
                    //获取非拆分型号的调拨单
                    List<OpsDo> dbckListByOrder = baseDoService.findDBCKListByOrder(orderId, orderItem, jyckDo.getNum(), DoSourceEnum.ALL);
                    if(!CollectionUtils.isEmpty(dbckListByOrder)){
                        for(OpsDo selectDBCKDo: dbckListByOrder){
                            if (DoStatusEnum.PART.getStatus().equals(selectDBCKDo.getDoStatus())
                                    || DoStatusEnum.FINISH.getStatus().equals(selectDBCKDo.getDoStatus())){
                                continue;
                            }
                            UpdateForOrderDto dbDto = new UpdateForOrderDto();
                            //5.计算非拆分调拨单物流期望交货期 bugid:17714 c14717 20250910
                            if(wmsDateIsSystemTime || CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(selectDBCKDo.getDlvEntire())){
                                dbDto.setWmsDlvDate(DateUtil.getNow());//物流交货期 系统时间
                            }else {
                                CommonResult<Integer> findDBCKDDResult =  wmCommonService.getWarehouseDeliveryDayByCode(selectDBCKDo.getWarehouseCode(),selectDBCKDo.getGatherWarehouseCode());
                                if(findDBCKDDResult.isSuccess()){
                                    /**
                                     * 当客户订单存在调拨单时
                                     * 1. 调拨单物流发货日 = 客户订单物流发货日 - 仓间调拨时间
                                     * 2  调拨单物流发货日<系统日期时，调拨单物流发货日=系统日期
                                     * 3  调拨单物流发货日>=系统日期时, 调拨单物流发货日=1中计算的调拨单物流发货日
                                     */
                                    Date countDate = DateUtil.addDay(dto.getWmsDlvDate(),-findDBCKDDResult.getData());
                                    Long diffDay =  DateUtil.getDiffDay(DateUtil.getNow(),countDate);//计算时间-系统时间
                                    if(diffDay < 0){
                                        dbDto.setWmsDlvDate(DateUtil.getNow());//物流交货期 系统时间
                                    }else {
                                        dbDto.setWmsDlvDate(countDate);//物流交货期
                                    }
                                }else {
                                    throw Exceptions.OpsException(jyckDo.getDoId(), findDBCKDDResult.getMessage());
                                }
                            }
                            dbDto.setDlvDate(dlvDate);
                            //6.非拆分型号需要修改调拨do以及wms; 是否下发wms
                            if (1 == selectDBCKDo.getIsWms()) {//拆分型号
                                //调用WMS变更接口
                                OpsDoAndItemDto doDto = findDoToWms(selectDBCKDo.getDoId());
                                doDto.setOpsDo(fillInUpdateInfo(selectDBCKDo, dbDto));
                            /*OpsWmOrderTask opsWmOrderTaskDo = findWmsOrderTaskService.findWmsOrderTaskByWmOrderId(selectDBCKDo.getDoId(), WmOrderTaskEnum.DO);
                            doDto.setWmOrderTaskId(opsWmOrderTaskDo.getId());*/
                                //bugids:12187 c14717 20230921
                                //CommonResult resWm = opsCallWmsFeignApi.updateDoToWms(doDto);
                                CommonResult<String> resWm = wmDoService.postWmsDoNew(doDto);
                                if (resWm.isSuccess()) {
                                    updateDoDeliveryDay(selectDBCKDo, dbDto);
                                    result.put(selectDBCKDo.getDoId(), "1");
                                } else {
                                    result.put(selectDBCKDo.getDoId(), "调拨单物流已作业");
                                }
                            } else {
                                updateDoDeliveryDay(selectDBCKDo, dbDto);
                                result.put(selectDBCKDo.getDoId(), "1");
                            }
                        }
                    }
                }
            }
        }
        //计算拆分型号的调拨单物流交货期
        if(Objects.nonNull(pcoWmsDate)){
            //获取调拨出库列表
            List<OpsDo> selectDBCKPcoList = baseDoService.findAllDBCKByOrder(orderId,orderItem);
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(selectDBCKPcoList)){
                for(OpsDo dbckPcoDo : selectDBCKPcoList){
                    if (DoStatusEnum.PART.getStatus().equals(dbckPcoDo.getDoStatus())
                            || DoStatusEnum.FINISH.getStatus().equals(dbckPcoDo.getDoStatus())){
                        continue;
                    }
                    UpdateForOrderDto dbDto = new UpdateForOrderDto();
                    //7.计算拆分型号调拨单物流期望交货期; bugid:17714 c14717 20250910
                    if(wmsDateIsSystemTime || CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(dbckPcoDo.getDlvEntire())){
                        //物流交货期 系统时间
                        dbDto.setWmsDlvDate(DateUtil.getNow());
                    }else {
                        CommonResult<Integer> findDBCKDDResult =  wmCommonService.getWarehouseDeliveryDayByCode(dbckPcoDo.getWarehouseCode(),dbckPcoDo.getGatherWarehouseCode());
                        if(findDBCKDDResult.isSuccess()){
                            /**
                             * 当客户订单存在调拨单时
                             * 1. 调拨单物流发货日 = 客户订单物流发货日 - 仓间调拨时间
                             * 2  调拨单物流发货日<系统日期时，调拨单物流发货日=系统日期
                             * 3  调拨单物流发货日>=系统日期时, 调拨单物流发货日=1中计算的调拨单物流发货日
                             */
                            Date countDate = DateUtil.addDay(pcoWmsDate,-findDBCKDDResult.getData());
                            //计算时间-系统时间
                            Long diffDay =  DateUtil.getDiffDay(DateUtil.getNow(),countDate);
                            if(diffDay < 0){
                                //物流交货期 系统时间
                                dbDto.setWmsDlvDate(DateUtil.getNow());
                            }else {
                                //物流交货期
                                dbDto.setWmsDlvDate(countDate);
                            }
                        }else {
                            throw Exceptions.OpsException(dbckPcoDo.getDoId(), findDBCKDDResult.getMessage());
                        }
                    }
                    //客户交货期
                    dbDto.setDlvDate(dlvDate);
                    //8.修改拆分型号调拨do以及wms; 是否下发wms
                    if (1 == dbckPcoDo.getIsWms()) {//拆分型号
                        //调用WMS变更接口
                        OpsDoAndItemDto doDto = findDoToWms(dbckPcoDo.getDoId());
                        doDto.setOpsDo(fillInUpdateInfo(dbckPcoDo, dbDto));
                        /*OpsWmOrderTask opsWmOrderTaskDo = findWmsOrderTaskService.findWmsOrderTaskByWmOrderId(dbckPcoDo.getDoId(), WmOrderTaskEnum.DO);
                        doDto.setWmOrderTaskId(opsWmOrderTaskDo.getId());*/
                        //bugids:12187 c14717 20230921
                        //CommonResult resWm = opsCallWmsFeignApi.updateDoToWms(doDto);
                        CommonResult<String> resWm = wmDoService.postWmsDoNew(doDto);
                        if (resWm.isSuccess()) {
                            updateDoDeliveryDay(dbckPcoDo, dbDto);
                            result.put(dbckPcoDo.getDoId(), "1");
                        } else {
                            result.put(dbckPcoDo.getDoId(), "调拨单物流已作业");
                        }
                    } else {
                        updateDoDeliveryDay(dbckPcoDo, dbDto);
                        result.put(dbckPcoDo.getDoId(), "1");
                    }
                }
            }
        }
        //变更rcvdetail
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(rcvWmsDlvDateSet)){
            if(updateRcvFlag){
                //变更订单表
                Rcvdetail detail = new Rcvdetail();
                detail.setRorderNo(orderId);
                detail.setRorderItem(Integer.parseInt(orderItem));
                detail.setDlvDate(dlvDate);
                //获取最大物流交货期
                detail.setWmsDlvDate(Collections.max(rcvWmsDlvDateSet));
                detail.setUpdateTime(DateUtil.getNow());
                modifyCustomerOrderService.modifyRcvDetail(detail, userDto);
            }
            result.put("maxWmDlvDate",DateUtil.dateToString(Collections.max(rcvWmsDlvDateSet)));
        }
        return result;
    }

    /**
     * bugid：17339 c14717 20250407
     * 计算订单物流交货期
     * bugid:11487 2023-07-25 C14717 根据订单号和客户期望交货期计算最大物流交货期
     * @param orderId
     * @param orderItem
     * @param dlvDateParam
     * @return 返回物流交货期或空
     * @throws OpsException
     */
    @Override
    public String getOrderMaxWlday(String orderId, String orderItem, Date dlvDateParam) throws OpsException{
        //1.十位订单号查找do 获取交易出库列表
        List<OpsDo> selectJYCKDoList = baseDoService.findAllJYCKByOrder(orderId,orderItem);
        //获取交易出库单的物流发货期
        HashSet<Date> rcvWmsDlvDateSet = new HashSet<Date>();
        //交易出库单物流发货日是系统时间标记
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(selectJYCKDoList)){
            for(OpsDo jyckDo : selectJYCKDoList){
                UpdateForOrderDto dto = new UpdateForOrderDto();
                //是否更新客户交货期
                if(Objects.nonNull(dlvDateParam)){
                    dto.setDlvDate(dlvDateParam);//客户交货期
                }else {
                    dto.setDlvDate(jyckDo.getHopeDate());
                }
                //2.计算交易单的物流期望交货期
                CommonResult<Integer> findDoDDResult = wmCommonService.getWarehouseSalesbranchConfigByCode(jyckDo.getDeptNo(),jyckDo.getGatherWarehouseCode());
                if(findDoDDResult.isSuccess()){
                    /**
                     * 1 客户订单物流发货日=客户交货期-国内运输时间
                     * 2 客户订单物流发货日<系统日期时，客户订单物流发货日=系统日期
                     * 3 客户订单物流发货日>=系统日期时,客户订单物流发货日=1中计算的客户订单物流发货日
                     */
                    Date countDate = DateUtil.addDay(dto.getDlvDate(),-findDoDDResult.getData());
                    Long diffDay =  DateUtil.getDiffDay(DateUtil.getNow(),countDate);//计算时间-系统时间
                    if(diffDay < 0){
                        dto.setWmsDlvDate(DateUtil.getNow());//物流交货期 系统时间
                    }else {
                        dto.setWmsDlvDate(countDate);//物流交货期
                    }
                }else {
                    throw Exceptions.OpsException(jyckDo.getDoId(), findDoDDResult.getMessage());
                }
                rcvWmsDlvDateSet.add(dto.getWmsDlvDate());
            }
        }
        if(CollectionUtil.isNotEmpty(rcvWmsDlvDateSet)){
            return DateUtil.dateToString(Collections.max(rcvWmsDlvDateSet));
        }else {
            return null;
        }
    }


    @Override
    public List<Rcvdetail> findRcvDetailsByMove(Long moveId) throws OpsException {
        List<Rcvdetail> rcvdetailList = new ArrayList<>();
        List<OpsDoItemInventory> doItemInventorys = baseDoService.getDoItemInventoryByInventoryId(moveId, InventoryTableTypeEnum.TRANS);
        for (OpsDoItemInventory doItemInventory : doItemInventorys) {
            OpsDo opsDo = baseDoService.getDoByDoId(doItemInventory.getDoId());
            if (opsDo != null) {
                if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType())) {
                    Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByKey(opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()));
                    if (rcvdetail != null) {
                        rcvdetailList.add(rcvdetail);
                    }
                } else if (DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                    log.info("间接关联关系{}：{}",opsDo.getDoType(),opsDo.getDoId());
                    OpsDo jyck = baseDoService.findJYCKByOrder(opsDo.getOrderId(), opsDo.getOrderItem(), opsDo.getNum(), DoSourceEnum.parse(opsDo.getDoSource()));
                    if (jyck != null) {
                        Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByKey(jyck.getOrderId(), Integer.valueOf(jyck.getOrderItem()));
                        if (rcvdetail != null) {
                            rcvdetailList.add(rcvdetail);
                        }
                    }

                }
            }
        }

        List<OpsPcoItemInventory> pcoItemInventoryList = opsPcoService.getOpsPcoItemInventoryByInventoryId(moveId, InventoryTableTypeEnum.TRANS);
        for (OpsPcoItemInventory opsPcoItemInventory : pcoItemInventoryList) {
            OpsPco pco = opsPcoService.selectPcoBypcoId(opsPcoItemInventory.getPcoId());
            if (pco != null) {
                OpsDo jyck = baseDoService.findJYCKByOrder(pco.getOrderId(), pco.getOrderItem(), pco.getNum(), DoSourceEnum.ASSModelNo);
                if (jyck != null) {
                    Rcvdetail rcvdetail = baseCustomerOrderService.findRcvDetailByKey(jyck.getOrderId(), Integer.valueOf(jyck.getOrderItem()));
                    if (rcvdetail != null) {
                        rcvdetailList.add(rcvdetail);
                    }
                }
            }
        }
        return rcvdetailList;
    }

    @Override
    public List<OrderNoInfo> findOrderNoInfoListByMove(Long moveId) throws OpsException {
        List<OrderNoInfo> OrderNoInfoList = new ArrayList<>();
        List<OpsDoItemInventory> doItemInventorys = baseDoService.getDoItemInventoryByInventoryId(moveId, InventoryTableTypeEnum.TRANS);
        for (OpsDoItemInventory doItemInventory : doItemInventorys) {
            OpsDo opsDo = baseDoService.getDoByDoId(doItemInventory.getDoId());
            if (opsDo != null) {
                if (DoTypeEnum.JYCK.getType().equals(opsDo.getDoType()) || DoTypeEnum.DBCK.getType().equals(opsDo.getDoType())) {
                    boolean exist = baseCustomerOrderService.isRcvDetailExist(opsDo.getOrderId(), Integer.valueOf(opsDo.getOrderItem()));
                    if (exist) {
                        OrderNoInfoList.add(OrderNoInfo.getFromDo(opsDo));
                    }
                }
            }
        }
        List<OpsPcoItemInventory> pcoItemInventoryList = opsPcoService.getOpsPcoItemInventoryByInventoryId(moveId, InventoryTableTypeEnum.TRANS);
        for (OpsPcoItemInventory opsPcoItemInventory : pcoItemInventoryList) {
            OpsPco pco = opsPcoService.selectPcoBypcoId(opsPcoItemInventory.getPcoId());
            if (pco != null) {
                OpsDo jyck = baseDoService.findJYCKByOrder(pco.getOrderId(), pco.getOrderItem(), pco.getNum(), DoSourceEnum.ASSModelNo);
                if (jyck != null) {
                    boolean exist = baseCustomerOrderService.isRcvDetailExist(pco.getOrderId(), Integer.valueOf(pco.getOrderItem()));
                    if (exist) {
                        OrderNoInfoList.add(OrderNoInfo.getFromPco(pco, opsPcoItemInventory));
                    }
                }
            }
        }
        return OrderNoInfoList;
    }



}



