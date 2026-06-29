package com.sales.ops.serviceimpl.wmOrder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.CrePcoByInventoryDto;
import com.sales.ops.dto.inventory.WmPCOConfirmDto;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.order.OpsPcoAddItemDto;
import com.sales.ops.dto.order.OpsPcoAndItemAndItemInventoryDto;
import com.sales.ops.dto.order.OpsWarehousePcoDto;
import com.sales.ops.dto.util.CreateInfoDto;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.*;
import com.sales.ops.feign.OpsCallWmsFeignApi;
import com.sales.ops.service.inventory.OpsInventoryService;
import com.sales.ops.service.wmOrder.OpsDoService;
import com.sales.ops.service.wmOrder.OpsPcoService;
import com.sales.ops.service.wmOrder.WmOrderTaskService;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：加工指令
 * @date ：Created in 2021/9/28 11:48
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OpsPcoServiceImpl implements OpsPcoService {


    @Autowired
    private OpsPcoMapper opsPcoMapper;

    @Autowired
    private OpsPcoItemMapper opsPcoItemMapper;
    @Autowired
    private OpsPcoItemInventoryMapper pcoItemInventoryMapper;

    @Autowired
    private OpsCallWmsFeignApi opsCallWmsFeignApi;

    @Autowired
    private WmOrderTaskService wmOrderTaskService;
    @Autowired
    private BaseInventoryService baseInventoryService;

    @Resource
    private OpsInventoryService opsInventoryService;

    @Autowired
    private OpsDoService opsDoService;

    @Autowired
    private OpsDoItemInventoryMapper opsDoItemInventoryMapper;

    @Autowired
    private OpsPcoPostMapper opsPcoPostMapper;

    //查询加工子型号
    @Override
    public List<OpsPcoItem> getPcoItem(String orderId, String orderItem, Integer num) {
        OpsPcoExample opsPcoExample = new OpsPcoExample();
        opsPcoExample.createCriteria().andDelflagEqualTo(0).andOrderIdEqualTo(orderId).andOrderItemEqualTo(orderItem).andNumEqualTo(num);
        List<OpsPco> opsPcos = opsPcoMapper.selectByExample(opsPcoExample);
        if (opsPcos.size() == 1) {
            OpsPco opsPco = opsPcos.get(0);
            OpsPcoItemExample opsPcoItemExample = new OpsPcoItemExample();
            opsPcoItemExample.createCriteria().andDelflagEqualTo(0)
                    .andPcoIdEqualTo(opsPco.getPcoId());
            return opsPcoItemMapper.selectByExample(opsPcoItemExample);
        }
        return Collections.emptyList();
    }
    public List<OpsPcoItemInventory> deletePcoItemInventoryByInventoryId(Long inventoryId, String inventoryTable) throws OpsException {
        List<OpsPcoItemInventory> pcoInvList = getOpsPcoItemInventoryByInventoryId(inventoryId, InventoryTableTypeEnum.getEnumByType(inventoryTable));
        for (OpsPcoItemInventory inv : pcoInvList) {
            OpsPcoItemInventory deleteInv = new OpsPcoItemInventory();
            deleteInv.setId(inv.getId());
            deleteInv.setDelflag(1);
            deleteInv.setModifyTime(new Date());
            pcoItemInventoryMapper.updateByPrimaryKeySelective(deleteInv);
        }
        return pcoInvList;
    }

    /**
     * 根据查询对象，分页查询符合条件的收货指令
     *
     * @param pageModel 分页三参数  加工来源 加工标识 加工指令号 仓库编码 10位订单号 开始时间 结束时间
     * @return 返回一个符合查询条件的集合，并且封装分页
     */
    @Override
    public PageInfo<OpsPco> findByExample(PageModel<OpsWarehousePcoDto> pageModel) {
        OpsPcoExample example = new OpsPcoExample();
        OpsPcoExample.Criteria criteria = example.createCriteria();
        OpsWarehousePcoDto condition = pageModel.getCondition();
        if (condition.getPcoSource() != null && !"".equals(condition.getPcoSource())) {//加工来源
            criteria.andPcoSourceEqualTo(condition.getPcoSource());
        }
        if (condition.getPcoStatus() != null && !"".equals(condition.getPcoStatus())) {//加工标识
            criteria.andPcoStatusEqualTo(condition.getPcoStatus());
        }
        if (condition.getPcoId() != null && !"".equals(condition.getPcoId())) {//加工指令号
            criteria.andPcoIdEqualTo(condition.getPcoId());
        }

        if (condition.getWarehouseCode() != null && !"".equals(condition.getWarehouseCode())) {//仓库编码
            criteria.andWarehouseCodeEqualTo(condition.getWarehouseCode());
        }
        if (condition.getSubOrderId() != null && !"".equals(condition.getSubOrderId())) {//10位订单号
            //criteria.andSubOrderIdLike("%" + condition.getSubOrderId() + "%");
        }
        if (condition.getBeginTime() != null && !"".equals(condition.getBeginTime())) {
            criteria.andCreTimeGreaterThanOrEqualTo(condition.getBeginTime());
        }
        if (condition.getEndTime() != null && !"".equals(condition.getEndTime())) {
            criteria.andCreTimeLessThanOrEqualTo(condition.getEndTime());
        }
        criteria.andDelflagEqualTo(0);//删除标志位
        PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize(), pageModel.getOrderBy());
        return new PageInfo(opsPcoMapper.selectByExample(example));
    }

    /**
     * 通过指令id获取指令明细表
     */
    @Override
    public List<OpsPcoItem> selectItemBypcoId(String pcoId) {
        OpsPcoItemExample example = new OpsPcoItemExample();
        OpsPcoItemExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(pcoId);
        criteria.andDelflagEqualTo(0);//删除标
        return opsPcoItemMapper.selectByExample(example);
    }

    @Override
    public OpsPco selectPcoBypcoId(String pcoId) {
        OpsPcoExample example = new OpsPcoExample();
        OpsPcoExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(pcoId);
        criteria.andDelflagEqualTo(0);//删除标
        List<OpsPco> pcoList = opsPcoMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(pcoList)) {
            return null;
        }
        return pcoList.get(0);
    }

    @Override
    public OpsPco findPcoByOrder(String OrderId, String OrderItem, Integer num) throws OpsException {
        OpsPcoExample example = new OpsPcoExample();
        OpsPcoExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(OrderId).andOrderItemEqualTo(OrderItem).andDelflagEqualTo(0);
        criteria.andNumEqualTo(num);
        List<OpsPco> opsPcoList = opsPcoMapper.selectByExample(example);
        if (opsPcoList.size() == 1) {
            return opsPcoList.get(0);
        } else {
            throw Exceptions.OpsException("查询出" + opsPcoList.size() + "条加工单。" + OrderId + "-" + OrderItem);
        }
    }
    @Deprecated
    @Override
    public OpsPco findPcoByOrder(String OrderId, String OrderItem) throws OpsException {
        OpsPcoExample example = new OpsPcoExample();
        example.createCriteria().andOrderIdEqualTo(OrderId).andOrderItemEqualTo(OrderItem).andDelflagEqualTo(0);
        List<OpsPco> opsPcoList = opsPcoMapper.selectByExample(example);
        if (opsPcoList.size() == 1) {
            return opsPcoList.get(0);
        } else {
            throw Exceptions.OpsException("查询出" + opsPcoList.size() + "条加工单。" + OrderId + "-" + OrderItem);
        }
    }


    @Override
    public List<OpsPcoItemInventory> selectItemInventoryBypcoId(String pcoId, Integer pcoItem, InventoryTableTypeEnum inventoryTableTypeEnum) {
        OpsPcoItemInventoryExample example = new OpsPcoItemInventoryExample();
        OpsPcoItemInventoryExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(pcoId);
        criteria.andPcoItemEqualTo(pcoItem);
        criteria.andDelflagEqualTo(0);//删除标
        if (Objects.nonNull(inventoryTableTypeEnum)) {
            criteria.andInventoryTableTypeEqualTo(inventoryTableTypeEnum.getType());
        }
        return pcoItemInventoryMapper.selectByExample(example);
    }


    @Override
    public OpsPcoItem getPcoItemByPcoIdAndItem(String pcoId, Integer pcoItem) throws OpsException {
        OpsPcoItemExample example = new OpsPcoItemExample();
        OpsPcoItemExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(pcoId);
        criteria.andPcoItemEqualTo(pcoItem);
        criteria.andDelflagEqualTo(0);//删除标
        List<OpsPcoItem> pcoItemList = opsPcoItemMapper.selectByExample(example);
        if (pcoItemList.size() == 1) {
            return pcoItemList.get(0);
        } else {
            throw Exceptions.OpsException("查询到" + pcoItemList.size() + "条PcoItem");
        }
    }

    @Override
    public List<OpsPcoItemInventory> findPcoItemInventoryByPcoIdAndItem(String pcoId, Integer pcoItem) throws OpsException {
        OpsPcoItemInventoryExample example = new OpsPcoItemInventoryExample();
        OpsPcoItemInventoryExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(pcoId);
        criteria.andPcoItemEqualTo(pcoItem);
        criteria.andDelflagEqualTo(0);//删除标
        return pcoItemInventoryMapper.selectByExample(example);
    }

    @Override
    public List<OpsPcoItemInventory> findPcoItemInventoryByPcoIdAndItemAndTableType(String pcoId, Integer pcoItem, InventoryTableTypeEnum inventoryTableTypeEnum) throws OpsException {
        OpsPcoItemInventoryExample example = new OpsPcoItemInventoryExample();
        OpsPcoItemInventoryExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(pcoId);
        criteria.andPcoItemEqualTo(pcoItem);
        criteria.andInventoryTableTypeEqualTo(inventoryTableTypeEnum.getType());
        criteria.andDelflagEqualTo(0);//删除标
        return pcoItemInventoryMapper.selectByExample(example);
    }

    @Override
    public OpsPcoItemInventory findPcoItemInventory(String pcoId, Integer pcoItem, Integer sortnum) throws OpsException {
        OpsPcoItemInventoryExample example = new OpsPcoItemInventoryExample();
        OpsPcoItemInventoryExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(pcoId);
        criteria.andPcoItemEqualTo(pcoItem);
        criteria.andSortnumEqualTo(sortnum);
        criteria.andDelflagEqualTo(0);//删除标
        List<OpsPcoItemInventory> opsPcoItemInventoryList = pcoItemInventoryMapper.selectByExample(example);
        if (opsPcoItemInventoryList.size() == 1) {
            return opsPcoItemInventoryList.get(0);
        } else {
            throw Exceptions.OpsException("查询到" + opsPcoItemInventoryList.size() + "条PcoItemInventory");
        }
    }

    @Override
    public OpsPcoItemInventory findPcoItemInventoryById(Long id) throws OpsException {
        return pcoItemInventoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void releasePreQtyInventoryAndDelPcoItemInventoryId(Long id, UserDto userDto) throws OpsException {
        OpsPcoItemInventory opsPcoItemInventory = pcoItemInventoryMapper.selectByPrimaryKey(id);
        if (Objects.nonNull(opsPcoItemInventory) && opsPcoItemInventory.getDelflag() != 1) {
            deletePcoItemInventoryByPrimaryKeySelective(opsPcoItemInventory.getId(), userDto);
            baseInventoryService.releasePreQtyInventory(opsPcoItemInventory.getInventoryId(),
                    opsPcoItemInventory.getUseQty(), opsPcoItemInventory.getInventoryTableType(), userDto);
        }
    }


    /**
     * @description：创建加工单无库存变更
     *
     */
    @Override
    public OpsPco crePco(OpsPco opsPco, List<OpsPcoItem> opsPcoItemList, List<OpsPcoItemInventory> pcoInventoryList, UserDto userDto) throws OpsException {
        if (Objects.nonNull(opsPco)) {
            opsPco.setCreator(userDto.getUserName());
            opsPco.setCreTime(DateUtil.getNow());
            opsPco.setModifier(userDto.getUserName());
            opsPco.setModifyTime(DateUtil.getNow());
            opsPco.setOutQty(0);
            opsPcoMapper.insertSelective(opsPco);
        }
        if (!CollectionUtils.isEmpty(opsPcoItemList)) {
            for (OpsPcoItem item : opsPcoItemList) {
                item.setCreTime(DateUtil.getNow());
                item.setCreator(userDto.getUserName());
                item.setModifyTime(DateUtil.getNow());
                item.setModifier(userDto.getUserName());
                opsPcoItemMapper.insertSelective(item);
            }
        }
        if (!CollectionUtils.isEmpty(pcoInventoryList)) {
            for (OpsPcoItemInventory item : pcoInventoryList) {
                item.setCreTime(DateUtil.getNow());
                item.setCreator(userDto.getUserName());
                item.setModifyTime(DateUtil.getNow());
                item.setModifier(userDto.getUserName());
                pcoItemInventoryMapper.insertSelective(item);
            }
        }
        return opsPco;
    }
    /**
     * @author ：c02483
     * @date ：Created in 2021/10/14 11:10
     * @description：创建加工单
     */
    @Override
    public OpsPco CreatePco(OpsPco opsPco, List<OpsPcoItem> opsPcoItemList, List<OpsPcoItemInventory> pcoInventoryList, UserDto userDto,Map<Long, Boolean> riskMap) throws OpsException {
        if (Objects.nonNull(opsPco)) {
            opsPco.setCreator(userDto.getUserName());
            opsPco.setCreTime(new Date());
            opsPco.setModifier(userDto.getUserName());
            opsPco.setModifyTime(new Date());
            opsPco.setOutQty(0);
            opsPcoMapper.insertSelective(opsPco);
        }
        if (!CollectionUtils.isEmpty(opsPcoItemList)) {
            for (OpsPcoItem item : opsPcoItemList) {
                item.setCreTime(new Date());
                item.setCreator(userDto.getUserName());
                item.setModifyTime(new Date());
                item.setModifier(userDto.getUserName());
                opsPcoItemMapper.insertSelective(item);
            }
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
        Confirm(opsPco, pcoInventoryList, userDto, riskMap);
        return opsPco;
    }

    @Override
    public List<OpsPco> GetPcolistByOrder(String OrderId, String OrderItem) throws OpsException {
        OpsPcoExample example = new OpsPcoExample();
        OpsPcoExample.Criteria criteriaItem = example.createCriteria();
        criteriaItem.andOrderIdEqualTo(OrderId).andDelflagEqualTo(0);
        if (!StringUtils.isEmpty(OrderItem)) {
            criteriaItem.andOrderItemEqualTo(OrderItem);
        }
        return opsPcoMapper.selectByExample(example);
    }


    /**
     * @author ：c02483
     * @date ：Created in 2021/11/11 18:32
     * @description：插入库存关系表
     */
    @Override
    public void insertInventoryItem(OpsPcoItemInventory itemInventory) throws OpsException {
        itemInventory.setVersion(0L);
        itemInventory.setDelflag(0);
        itemInventory.setCreTime(new Date());
        itemInventory.setModifyTime(new Date());
        pcoItemInventoryMapper.insertSelective(itemInventory);
    }

    /**
     * @author ：c02483
     * @date ：Created in 2021/10/29 11:46
     * @description：确认占用库存
     */
    @Override
    public void Confirm(OpsPco opsPco, List<OpsPcoItemInventory> pcoInventoryList, UserDto userDto,Map<Long, Boolean> riskMap) throws OpsException {

        //校验状态
        //变更库存
        if (!CollectionUtils.isEmpty(pcoInventoryList)) {
            for (OpsPcoItemInventory itemInventory : pcoInventoryList) {
                //bugid:20641 风险判断
                if(Objects.nonNull(riskMap) &&Objects.nonNull(riskMap.get(itemInventory.getInventoryId())) && riskMap.get(itemInventory.getInventoryId())){
                    String orderFno = opsPco.getOrderId()+"-"+opsPco.getOrderItem();
                    baseInventoryService.addPreQtyRiskInv(itemInventory.getInventoryId(), itemInventory.getUseQty(), itemInventory.getInventoryTableType(),  userDto,orderFno);
                }
                baseInventoryService.addPreQtyInventory(itemInventory.getInventoryId(), itemInventory.getUseQty(), itemInventory.getInventoryTableType(), userDto);
            }
            //变更状态
            if (Objects.nonNull(opsPco)) {
                OpsPco record = new OpsPco();
                record.setPcoId(opsPco.getPcoId());
                record.setModifier(userDto.getUserName());
                record.setModifyTime(new Date());
                record.setPcoStatus(2);
                opsPcoMapper.updateByPrimaryKeySelective(record);
            }
        }

    }

    @Override
    public void updatePreQtyForPco(OpsPco opsPco, List<OpsPcoItemInventory> pcoInventoryList, UserDto userDto) throws OpsException {
        for (OpsPcoItemInventory itemInventory : pcoInventoryList) {
            //无需判断风险在库
            baseInventoryService.addPreQtyInventory(itemInventory.getInventoryId(), itemInventory.getUseQty(), itemInventory.getInventoryTableType(), userDto);
        }
    }


    @Override
    public void delPcoByPrimaryKey(Long id, String pcoId, String orderId, String orderItem, UserDto userDto) throws OpsException {
        //变更OpsPco状态为取消
        OpsPco updatePco = new OpsPco();
        updatePco.setId(id);
        updatePco.setPcoStatus(DoStatusEnum.CANCEL.getStatus());
        updatePco.setDelflag(1);
        updatePco.setPcoStatus(DoStatusEnum.CANCEL.getStatus());
        opsPcoMapper.updateByPrimaryKeySelective(updatePco);
        OpsPcoItemExample example = new OpsPcoItemExample();
        OpsPcoItemExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(pcoId);
        criteria.andDelflagEqualTo(0);//删除标
        List<OpsPcoItem> listItem = opsPcoItemMapper.selectByExample(example);
        for (OpsPcoItem opsPcoItem : listItem) {
            OpsPcoItem updatePcoItem = new OpsPcoItem();
            updatePcoItem.setId(opsPcoItem.getId());
            updatePcoItem.setDelflag(1);
            opsPcoItemMapper.updateByPrimaryKeySelective(updatePcoItem);
        }

        OpsPcoItemInventoryExample examplePcoItemInventory = new OpsPcoItemInventoryExample();
        examplePcoItemInventory.createCriteria().andDelflagEqualTo(0).andPcoIdEqualTo(pcoId);
        List<OpsPcoItemInventory> opsPcoItemInventoryList = pcoItemInventoryMapper.selectByExample(examplePcoItemInventory);
        for (OpsPcoItemInventory item : opsPcoItemInventoryList) {
            OpsPcoItemInventory updateOpsPcoItemInventory = new OpsPcoItemInventory();
            updateOpsPcoItemInventory.setId(item.getId());
            updateOpsPcoItemInventory.setDelflag(1);
            updateOpsPcoItemInventory.setModifier(userDto.getUserName());
            updateOpsPcoItemInventory.setModifyTime(new Date());
            pcoItemInventoryMapper.updateByPrimaryKeySelective(updateOpsPcoItemInventory);
            baseInventoryService.releasePreQtyInventory(item.getInventoryId(), item.getUseQty(), item.getInventoryTableType(), new UserDto());
        }
    }

    @Override
    public void CancelPco(Long Id, String pcoId, CancelForOrderDto orderDto) throws OpsException {
        delPcoByPrimaryKey(Id, pcoId, orderDto.getOrderId(), orderDto.getOrderItem(), orderDto.getUserDto());
    }


    /**
     * @author ：c02483
     * @date ：Created in 2021/10/14 11:09
     * @description：按照调度要求,创建加工单
     */
    @Override
    public List<OpsPco> CreatePcoForDispatch(List<CrePcoByInventoryDto> pcoList, UserDto userDto, String dlvEntire ,Map<Long, Boolean> riskMap ) throws OpsException {
        List<OpsPco> list = new ArrayList<>();
        List<OpsWmOrderTask> taskList = new ArrayList<OpsWmOrderTask>();

        for (CrePcoByInventoryDto dto : pcoList) {
            if (Objects.nonNull(dto.getOpsPco())) {
                OpsPco pco = CreatePco(dto.getOpsPco(), dto.getOpsPcoItems(), dto.getItemInventorys(), userDto,riskMap);
                OpsWmOrderTask wmTaskPco = wmOrderTaskService.createWmTaskPo(pco.getPcoId(), WmOrderTaskEnum.PCO,
                        WmOrderTaskEnum.ORDER,SendStatusEnum.SUSPENDED,new CreateInfoDto(userDto.getUserName()), null,0,"");
                //通知发货不创建task
                if(!CKTYPEEnum.NOTIFY_UNLIMIT.getCode().equals(dlvEntire)){
                    taskList.add(wmTaskPco);
                }
                list.add(pco);
            } else {
                CreatePco(null, dto.getOpsPcoItems(), dto.getItemInventorys(), userDto,riskMap);
            }
        }
        //批量更新task
        wmOrderTaskService.addBatchOpsWmOrderTask(taskList);
        return list;
    }

    /**
     * ops提交wmsPco和Pcoitem
     */
    @Override
    public OpsPcoAddItemDto findPcoToWms(String pcoId) {
        //add pco to adapter
        OpsPcoAddItemDto opsPcoAddItemDto = new OpsPcoAddItemDto();
        OpsPcoExample ex = new OpsPcoExample();
        OpsPcoExample.Criteria cri = ex.createCriteria();
        cri.andPcoIdEqualTo(pcoId);
        cri.andDelflagEqualTo(0);
        List<OpsPco> list = opsPcoMapper.selectByExample(ex);
        if (!CollectionUtils.isEmpty(list)) {
            opsPcoAddItemDto.setOpsPco(list.get(0));//add prc
        }
        //addItemList to adapter
        OpsPcoItemExample example = new OpsPcoItemExample();
        OpsPcoItemExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(pcoId);
        criteria.andDelflagEqualTo(0);//删除标
        List<OpsPcoItem> listItem = opsPcoItemMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(listItem)) {
            opsPcoAddItemDto.setList(listItem);
        }

        if (CollectionUtils.isEmpty(listItem) || CollectionUtils.isEmpty(list)) {
            return null;
        }
        return opsPcoAddItemDto;
    }


    @Override
    public OpsPcoAndItemAndItemInventoryDto findPcoAndPcoItemByOrderIdAndOrderItem(String orderId, String orderItem) {
        //add pco to adapter
        OpsPcoAndItemAndItemInventoryDto opsPcoAndItemAndItemInventoryDto = new OpsPcoAndItemAndItemInventoryDto();
        OpsPcoExample ex = new OpsPcoExample();
        OpsPcoExample.Criteria cri = ex.createCriteria();
        cri.andOrderIdEqualTo(orderId);
        cri.andOrderItemEqualTo(orderItem);
        cri.andDelflagEqualTo(0);
        List<OpsPco> list = opsPcoMapper.selectByExample(ex);
        if (!CollectionUtils.isEmpty(list)) {
            opsPcoAndItemAndItemInventoryDto.setOpsPco(list.get(0));//add prc
        } else {
            return null;
        }
        //addItemList to adapter
        OpsPcoItemExample example = new OpsPcoItemExample();
        OpsPcoItemExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(opsPcoAndItemAndItemInventoryDto.getOpsPco().getPcoId());
        criteria.andDelflagEqualTo(0);//删除标
        List<OpsPcoItem> listItem = opsPcoItemMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(listItem)) {
            opsPcoAndItemAndItemInventoryDto.setItemList(listItem);
        }
        if (CollectionUtils.isEmpty(listItem) || CollectionUtils.isEmpty(list)) {
            return null;
        }

        //addItemList to adapter
        OpsPcoItemInventoryExample itemInventoryExample = new OpsPcoItemInventoryExample();
        OpsPcoItemInventoryExample.Criteria itemInventoryCriteria = itemInventoryExample.createCriteria();
        itemInventoryCriteria.andPcoIdEqualTo(opsPcoAndItemAndItemInventoryDto.getOpsPco().getPcoId());
        itemInventoryCriteria.andDelflagEqualTo(0);//删除标
        List<OpsPcoItemInventory> listItemInventory = pcoItemInventoryMapper.selectByExample(itemInventoryExample);
        if (!CollectionUtils.isEmpty(listItemInventory)) {
            opsPcoAndItemAndItemInventoryDto.setItemInventoryList(listItemInventory);
        } else {
            opsPcoAndItemAndItemInventoryDto.setItemInventoryList(Collections.emptyList());
        }
        return opsPcoAndItemAndItemInventoryDto;
    }

    @Override
    public OpsPcoAndItemAndItemInventoryDto findPcoAndPcoItemByPcoId(String pcoId) {
        //add pco to adapter
        OpsPcoAndItemAndItemInventoryDto opsPcoAndItemAndItemInventoryDto = new OpsPcoAndItemAndItemInventoryDto();
        OpsPcoExample ex = new OpsPcoExample();
        OpsPcoExample.Criteria cri = ex.createCriteria();
        cri.andPcoIdEqualTo(pcoId);
        cri.andDelflagEqualTo(0);
        List<OpsPco> list = opsPcoMapper.selectByExample(ex);
        if (!CollectionUtils.isEmpty(list)) {
            opsPcoAndItemAndItemInventoryDto.setOpsPco(list.get(0));//add prc
        } else {
            return null;
        }
        //addItemList to adapter
        OpsPcoItemExample example = new OpsPcoItemExample();
        OpsPcoItemExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(pcoId);
        criteria.andDelflagEqualTo(0);//删除标
        List<OpsPcoItem> listItem = opsPcoItemMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(listItem)) {
            opsPcoAndItemAndItemInventoryDto.setItemList(listItem);
        }
        if (CollectionUtils.isEmpty(listItem) || CollectionUtils.isEmpty(list)) {
            return null;
        }

        //addItemList to adapter
        OpsPcoItemInventoryExample itemInventoryExample = new OpsPcoItemInventoryExample();
        OpsPcoItemInventoryExample.Criteria itemInventoryCriteria = itemInventoryExample.createCriteria();
        itemInventoryCriteria.andPcoIdEqualTo(pcoId);
        itemInventoryCriteria.andDelflagEqualTo(0);//删除标
        List<OpsPcoItemInventory> listItemInventory = pcoItemInventoryMapper.selectByExample(itemInventoryExample);
        if (!CollectionUtils.isEmpty(listItemInventory)) {
            opsPcoAndItemAndItemInventoryDto.setItemInventoryList(listItemInventory);
        } else {
            opsPcoAndItemAndItemInventoryDto.setItemInventoryList(Collections.emptyList());
        }
        return opsPcoAndItemAndItemInventoryDto;
    }


    @Override
    public List<OpsPcoItemInventory> getOpsPcoItemInventoryByInventoryId(long InventoryId, InventoryTableTypeEnum inventoryTableTypeEnum) {
        OpsPcoItemInventoryExample example = new OpsPcoItemInventoryExample();
        example.createCriteria().andDelflagEqualTo(0).andInventoryIdEqualTo(InventoryId).andInventoryTableTypeEqualTo(inventoryTableTypeEnum.getType());
        example.setOrderByClause(" use_qty asc ");
        return pcoItemInventoryMapper.selectByExample(example);
    }

    @Override
    public List<OpsPcoItemInventory> getOpsPcoItemInventoryByPcoId(String pcoId, Integer pcoItem) {
        OpsPcoItemInventoryExample example = new OpsPcoItemInventoryExample();
        example.createCriteria().andDelflagEqualTo(0).andPcoIdEqualTo(pcoId).andPcoItemEqualTo(pcoItem);
        example.setOrderByClause(" use_qty asc ");
        return pcoItemInventoryMapper.selectByExample(example);
    }


    @Override
    public int updatetOpsPcoItemInventory(Long id, Long version, Integer useQty, Long inventoryId, InventoryTableTypeEnum inventoryTableTypeEnum, UserDto userDto) {
        OpsPcoItemInventoryExample example = new OpsPcoItemInventoryExample();
        example.createCriteria().andIdEqualTo(id).andVersionEqualTo(version).andDelflagEqualTo(0);
        OpsPcoItemInventory record = new OpsPcoItemInventory();
        record.setInventoryId(inventoryId);
        record.setVersion(version + 1);
        record.setModifyTime(new Date());
        record.setModifier(userDto.getUserName());
        record.setUseQty(useQty);
        record.setInventoryTableType(inventoryTableTypeEnum.getType());
        return pcoItemInventoryMapper.updateByExampleSelective(record, example);
    }

    @Override
    public void updateOpsPcoItemInv(Long id, Long version, Integer useQty, Long inventoryId, String tableType, UserDto userDto) throws OpsException {
        OpsPcoItemInventoryExample example = new OpsPcoItemInventoryExample();
        example.createCriteria().andIdEqualTo(id).andVersionEqualTo(version).andDelflagEqualTo(0);
        OpsPcoItemInventory record = new OpsPcoItemInventory();
        record.setInventoryId(inventoryId);
        record.setVersion(version + 1);
        record.setModifyTime(new Date());
        record.setModifier(userDto.getUserName());
        record.setUseQty(useQty);
        record.setInventoryTableType(tableType);
        int rows = pcoItemInventoryMapper.updateByExampleSelective(record, example);
        if (rows < 1) {
            throw Exceptions.OpsException("更新itemInv异常" + id);
        }

    }

    @Override
    public void updatetOpsPcoItemInventoryRcv(Long id, Long version, Long inventoryId, InventoryTableTypeEnum inventoryTableTypeEnum, Long fromInventoryId, InventoryTableTypeEnum fromInventoryTableTypeEnum, String userName) {
        OpsPcoItemInventoryExample example = new OpsPcoItemInventoryExample();
        example.createCriteria().andIdEqualTo(id).andVersionEqualTo(version).andDelflagEqualTo(0);
        OpsPcoItemInventory record = new OpsPcoItemInventory();
        record.setInventoryId(inventoryId);
        record.setInventoryTableType(inventoryTableTypeEnum.getType());
        record.setSrcInventoryId(fromInventoryId);
        record.setSrcInventoryTableType(fromInventoryTableTypeEnum.getType());
        record.setVersion(version + 1);
        record.setModifier(userName);
        record.setModifyTime(new Date());
        pcoItemInventoryMapper.updateByExampleSelective(record, example);

    }


    @Override
    public void updatetOpsPcoItemInventoryByKey(OpsPcoItemInventory opsPcoItemInventory) {
        pcoItemInventoryMapper.updateByPrimaryKeySelective(opsPcoItemInventory);

    }

    @Override
    public void updatetOpsPcoItem(Long id, String waitType, UserDto userDto) {
        OpsPcoItem example = new OpsPcoItem();
        example.setId(id);
        example.setWaitType(waitType);
        example.setModifier(userDto.getUserName());
        example.setModifyTime(new Date());
        opsPcoItemMapper.updateByPrimaryKeySelective(example);

    }
    @Override
    public void updatePcoItemQty(Long id, Integer subQty, UserDto userDto) throws OpsException{
        OpsPcoItem example = new OpsPcoItem();
        example.setId(id);
        if(Objects.isNull(subQty) || subQty <= 0){
            throw Exceptions.OpsException("PcoItemQty数量更新异常:"+ subQty);
        }
        example.setSubQty(subQty);
        example.setModifier(userDto.getUserName());
        example.setModifyTime(DateUtil.getNow());
        opsPcoItemMapper.updateByPrimaryKeySelective(example);

    }

    @Override
    public void updatePcoItemInvQty(Long id, Integer subQty, UserDto userDto) throws OpsException{
        OpsPcoItemInventory example = new OpsPcoItemInventory();
        example.setId(id);
        if(Objects.isNull(subQty) || subQty <= 0){
            throw Exceptions.OpsException("PcoItemInvQty数量更新异常:"+ subQty);
        }
        example.setUseQty(subQty);
        example.setModifier(userDto.getUserName());
        example.setModifyTime(DateUtil.getNow());
        pcoItemInventoryMapper.updateByPrimaryKeySelective(example);

    }

    @Override
    public void updatetOpsPco(Long id, Integer isWms, UserDto userDto) throws OpsException{
        OpsPco example = new OpsPco();
        example.setId(id);
        example.setIsWms(isWms);
        example.setModifyTime(new Date());
        example.setModifier(userDto.getUserName());
        int rows =opsPcoMapper.updateByPrimaryKeySelective(example);
        if (rows < 1) {
            throw Exceptions.OpsException("更新pco异常" + id);
        }

    }


    /**
     * 5.6 组装确认回传
     */
    @Override
    public String wmPCOConfirm(WmPCOConfirmDto wmPCOConfirmDto) throws OpsException {
        if (Objects.isNull(wmPCOConfirmDto)) {
            throw Exceptions.OpsException("参数解析失败--WmPCOConfirmDto");
        }
        log.info("加工确认回传: " + wmPCOConfirmDto.getPcoOrderCode());

        String pcoId = wmPCOConfirmDto.getPcoOrderCode();
        OpsPcoExample ex = new OpsPcoExample();
        OpsPcoExample.Criteria cri = ex.createCriteria();
        cri.andPcoIdEqualTo(pcoId);
        cri.andDelflagEqualTo(0);
        List<OpsPco> list = opsPcoMapper.selectByExample(ex);
        if (CollectionUtils.isEmpty(list)) {
            throw Exceptions.OpsException("无此加工单" + pcoId);
        }

        OpsPco opsPco = list.get(0);
        if (DoStatusEnum.FINISH.getStatus().equals(opsPco.getPcoStatus())) {
            throw Exceptions.OpsException("此加工单单已完成，不允许继续过账处理" + opsPco.getPcoId());
        }
        if (opsPco.getOutQty() == null) {
            opsPco.setOutQty(wmPCOConfirmDto.getQty());

        } else {
            opsPco.setOutQty(opsPco.getOutQty() + wmPCOConfirmDto.getQty());

        }

        //插入对账表数据；
        OpsPcoPost opsPcoPost = new OpsPcoPost();
        opsPcoPost.setWarehouseCode(opsPco.getWarehouseCode());
        opsPcoPost.setPcoId(opsPco.getPcoId());
        opsPcoPost.setQty(opsPco.getOutQty());
        opsPcoPost.setDelflag(0);//删除标志
        opsPcoPost.setCreTime(new Date());
        opsPcoPost.setCreator("wms");
        opsPcoPost.setModelno(opsPco.getModelNo());
        //opsDoPost.setMsgId(param.getMsgId());
        opsPcoPostMapper.insertSelective(opsPcoPost);

        if (!opsPco.getQty().equals(opsPco.getOutQty())) {//货不齐

            //更新状态
            opsPco.setPcoStatus(PcoStatusEnum.PART.getStatus());//加工完成
            opsPco.setModifier("wms");
            opsPco.setModifyTime(new Date());
            opsPcoMapper.updateByPrimaryKey(opsPco);

        } else { // 货齐
            //更新状态
            opsPco.setPcoStatus(PcoStatusEnum.FINISH.getStatus());//加工完成
            opsPco.setModifier("wms");
            opsPco.setModifyTime(new Date());
            opsPcoMapper.updateByPrimaryKey(opsPco);

            //4.ops_do_item_inventory 按照此表对应的库存关系，直接扣减库存
            /*OpsPcoItemInventoryExample examplePcoItemInventory = new OpsPcoItemInventoryExample();
            examplePcoItemInventory.createCriteria().andDelflagEqualTo(0).andPcoIdEqualTo(pcoId);
            List<OpsPcoItemInventory> opsPcoItemInventoryList = pcoItemInventoryMapper.selectByExample(examplePcoItemInventory);
            //out_qty 不够不做扣减
            if (opsPcoItemInventoryList == null || opsPcoItemInventoryList.isEmpty()) {
                throw Exceptions.OpsException("库存无占用" + opsPco.getPcoId());
            } else {
                //直接扣减库存
                for (OpsPcoItemInventory item : opsPcoItemInventoryList) {
                    baseInventoryService.subQtyInventory(item.getInventoryId(), item.getUseQty(), item.getInventoryTableType(), new UserDto());
                }
            }*/
        }


        //成品型号增加库存
        //更加子型号获得库存属性
       /* OpsInventory pcoItemInventory =  baseInventoryService.GetInventoryById(opsPcoItemInventoryList.get(0).getInventoryId());
        OpsInventory inv = new OpsInventory();
        inv.setWarehouseCode(opsPco.getWarehouseCode());
        inv.setModelno(opsPco.getModelNo());
        inv.setInventoryPropertyId(pcoItemInventory.getInventoryPropertyId());
        List<OpsInventory> invList = opsInventoryService.getOpsInventory(inv);
        Long invId = null;
        if (invList.size() == 0) {//有库存
            inv.setInventoryStatus(InventoryStatusEnum.NORMAL.getCode());
            inv.setQuantity(opsPco.getQty());
            inv.setPrepareQuantity(opsPco.getQty());
            invId = baseInventoryService.createInv(inv, UserDto.AUTO);
        } else {//没有库存
            invId = invList.get(0).getInventoryId();
            baseInventoryService.UpdateQtyInventoryWithPreQty(invId, opsPco.getQty(),opsPco.getQty(), InventoryTableTypeEnum.NORMAL.getType(), UserDto.AUTO);
        }
        List<OpsDo> opsDos = opsDoService.GetDolistByOrder(opsPco.getOrderId(), opsPco.getOrderItem(), null, DoTypeEnum.JYCK);

        OpsDoItemInventory opsDoItemInventory = new OpsDoItemInventory();
        opsDoItemInventory.setDoId(opsDos.get(0).getDoId());
        opsDoItemInventory.setDoItem(1);
        opsDoItemInventory.setInventoryId(invId);
        opsDoItemInventory.setInventoryTableType("N");
        opsDoItemInventory.setUseQty(opsPco.getQty());
        opsDoItemInventory.setVersion(0L);
        opsDoItemInventory.setDelflag(0);
        opsDoItemInventory.setCreTime(new Date());
        opsDoItemInventory.setCreator(UserDto.AUTO.getUserName());
        opsDoItemInventory.setSortnum(1);
        opsDoItemInventoryMapper.insertSelective(opsDoItemInventory);*/

        return "加工";
    }


    @Override
    public void updatePcoItemWaitTypeForItem(String pcoId, int item, String waitType, String modifier) throws OpsException {
        OpsPcoItem opsPcoItem = new OpsPcoItem();
        opsPcoItem.setId(null);
        opsPcoItem.setModifier(modifier);
        opsPcoItem.setModifyTime(new Date());
        opsPcoItem.setWaitType(waitType);
        OpsPcoItemExample opsPcoItemUpdateExample = new OpsPcoItemExample();
        OpsPcoItemExample.Criteria opsPcoItemUpdateCriteria = opsPcoItemUpdateExample.createCriteria();
        opsPcoItemUpdateCriteria.andPcoIdEqualTo(pcoId);//出库指令
        opsPcoItemUpdateCriteria.andPcoItemEqualTo(item);//项
//        opsPcoItemUpdateCriteria.andVersionEqualTo(version);//version 并发条件
        opsPcoItemUpdateCriteria.andDelflagEqualTo(0);//删除标识
        opsPcoItemMapper.updateByExampleSelective(opsPcoItem, opsPcoItemUpdateExample);
    }

    @Override
    public int deletePcoItemInventoryByPrimaryKeySelective(Long id, UserDto userDto) throws OpsException {
        OpsPcoItemInventory updateOpsPcoItemInventory = new OpsPcoItemInventory();
        updateOpsPcoItemInventory.setId(id);
        updateOpsPcoItemInventory.setDelflag(1);
        updateOpsPcoItemInventory.setModifier(userDto.getUserName());
        updateOpsPcoItemInventory.setModifyTime(new Date());
        return pcoItemInventoryMapper.updateByPrimaryKeySelective(updateOpsPcoItemInventory);
    }

    @Override
    public void updatePcoItemToExcepiton(Long id) {
        OpsPcoItem updateOpsPcoItem = new OpsPcoItem();
        updateOpsPcoItem.setWaitType(DoWaitTypeEnum.EXCEPTION.getType());
        updateOpsPcoItem.setId(id);
        opsPcoItemMapper.updateByPrimaryKeySelective(updateOpsPcoItem);
    }

    @Override
    public List<OpsPcoItem> findPcoItemByPcoId(String pcoId) throws OpsException {
        //一个PCOID 可以多个ITEM
        OpsPcoItemExample example = new OpsPcoItemExample();
        example.createCriteria().andDelflagEqualTo(0).andPcoIdEqualTo(pcoId);
        return opsPcoItemMapper.selectByExample(example);
    }
    //可用
    @Override
    public OpsPcoItem getPcoItemByPcoId(String pcoId, Integer item) throws OpsException {
        //一个PCOID 可以多个ITEM
        OpsPcoItemExample example = new OpsPcoItemExample();
        example.createCriteria().andDelflagEqualTo(0).andPcoIdEqualTo(pcoId).andPcoItemEqualTo(item);
        List<OpsPcoItem> list = opsPcoItemMapper.selectByExample(example);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() == 0) {
            return null;
        } else {
            throw Exceptions.OpsException("查询出多条PcoItem");
        }
    }


    @Override
    public OpsPco getPcoByPcoId(String pcoId) throws OpsException {
        OpsPcoExample example = new OpsPcoExample();
        example.createCriteria().andDelflagEqualTo(0).andPcoIdEqualTo(pcoId);
        List<OpsPco> list = opsPcoMapper.selectByExample(example);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() == 0) {
            return null;
        } else {
            throw Exceptions.OpsException("查询出多条Pco");
        }
    }

    @Override
    public List<OpsPcoItemInventory> getPcoItemInventoryByPcoId(String pcoId) throws OpsException {
        OpsPcoItemInventoryExample example = new OpsPcoItemInventoryExample();
        OpsPcoItemInventoryExample.Criteria criteria = example.createCriteria().andDelflagEqualTo(0).andPcoIdEqualTo(pcoId);
        return pcoItemInventoryMapper.selectByExample(example);
    }

    @Override
    public Long countPcoItemInvByPcoIdItem(String pcoId,Integer pcoItem) throws OpsException {
        OpsPcoItemInventoryExample example = new OpsPcoItemInventoryExample();
        OpsPcoItemInventoryExample.Criteria criteria = example.createCriteria().andDelflagEqualTo(0).andPcoIdEqualTo(pcoId).andPcoItemEqualTo(pcoItem);
        return pcoItemInventoryMapper.countByExample(example);
    }

    @Override
    public void updatePcoItemIsCrossByPcoItem(String pcoId, Integer item, boolean cross) {
        OpsPcoItemExample example = new OpsPcoItemExample();
        example.createCriteria().andDelflagEqualTo(0).andPcoIdEqualTo(pcoId).andPcoItemEqualTo(item);
        OpsPcoItem updateOpsPcoItem = new OpsPcoItem();
        updateOpsPcoItem.setIsCross(cross);
        updateOpsPcoItem.setModifier("wms");
        updateOpsPcoItem.setModifyTime(new Date());
        opsPcoItemMapper.updateByExampleSelective(updateOpsPcoItem, example);
    }

    @Override
    public void updateOpsPcoForCrossRoId(String pcoId, String roId) {
        OpsPcoExample example = new OpsPcoExample();
        example.createCriteria().andDelflagEqualTo(0).andPcoIdEqualTo(pcoId);
        OpsPco updateOpsPco = new OpsPco();
        updateOpsPco.setRoId(roId);
        updateOpsPco.setModifier("wms");
        updateOpsPco.setModifyTime(new Date());
        opsPcoMapper.updateByExampleSelective(updateOpsPco, example);
    }

    @Override
    public void insertPcoItemInventory(OpsPcoItemInventory pcoItemInventory) throws OpsException {
        pcoItemInventory.setDelflag(0);
        pcoItemInventory.setVersion(0L);
        pcoItemInventory.setCreTime(new Date());
        pcoItemInventory.setCreator(UserDto.ADMIN.getUserName());
        pcoItemInventoryMapper.insertSelective(pcoItemInventory);
    }

    /**
     * 获取OpsDoItemInventory 按来源ID
     */
    @Override
    public OpsPcoItemInventory getOpsPcoItemInventoryBySrcInventoryId(String pcoId,Integer pcoItem, Long srcInventoryId, InventoryTableTypeEnum inventoryTableTypeEnum) throws OpsException {
        OpsPcoItemInventoryExample example = new OpsPcoItemInventoryExample();
        example.createCriteria().andPcoIdEqualTo(pcoId).andPcoItemEqualTo(pcoItem).andDelflagEqualTo(0)
                .andSrcInventoryIdEqualTo(srcInventoryId).andInventoryTableTypeEqualTo(inventoryTableTypeEnum.getType());
        List<OpsPcoItemInventory> list = pcoItemInventoryMapper.selectByExample(example);
        if( CollectionUtils.isEmpty(list)){
            return null;
        }
        if(list.size()>1){
            throw Exceptions.OpsException("pcoID在途来源ID不允许多行，PCOID:"+pcoId+"，来源srcInventoryId："+srcInventoryId);
        }
        return list.get(0);
    }
}
