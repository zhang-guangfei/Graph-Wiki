package com.sales.ops.serviceimpl.wmOrder;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.ExpdetailMapper;
import com.sales.ops.db.dao.OpsDoItemInventoryMapper;
import com.sales.ops.db.dao.OpsDoItemMapper;
import com.sales.ops.db.dao.OpsDoMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.OpsDoDto;
import com.sales.ops.enums.DoSourceEnum;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.enums.InventoryTableTypeEnum;
import com.sales.ops.service.inventory.OpsInventoryService;
import com.sales.ops.service.log.OpsInventoryLogService;
import com.sales.ops.service.wmOrder.BaseDoService;
import com.sales.ops.serviceimpl.inventory.BaseInventoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author C12961
 * @date 2022/3/31 17:52
 */

@Service
public class BaseDoServiceImpl implements BaseDoService {


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
    private OpsInventoryLogService opsInventoryLogService;
    @Resource
    private ExpdetailMapper expdetailMapper;

    @Override
    public boolean existsDoByDoId(String doId) {
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDoIdEqualTo(doId);
        Long count = opsDoMapper.countByExample(example);
        return count > 0;
    }

    @Override
    public OpsDo getDoByDoId(String doId) throws OpsException {
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDoIdEqualTo(doId);
        criteria.andDelflagEqualTo(0);
        List<OpsDo> doList = opsDoMapper.selectByExample(example);
        if (doList.size() < 1) {
            return null;
        }
        if (doList.size() > 1) {
            throw Exceptions.OpsException("查询出" + doList.size() + "条出库指令" + doId);
        }
        return doList.get(0);
    }

    @Override
    public OpsDoItem getDoItemByDoId(String doId) throws OpsException {
        List<OpsDoItem> list = findDoItemByDoId(doId);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() == 0) {
            return null;
        } else {
            throw Exceptions.OpsException("查询出多条DoItem");
        }
    }

    public List<OpsDoItem> findDoItemByDoId(String doId) {
        OpsDoItemExample example = new OpsDoItemExample();
        example.createCriteria().andDoIdEqualTo(doId).andDelflagEqualTo(0);
        return opsDoItemMapper.selectByExample(example);
    }

    @Override
    public Long countDoItemInvByDoId(String doId) {
        OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
        example.createCriteria().andDoIdEqualTo(doId).andDelflagEqualTo(0);
        return opsDoItemInventoryMapper.countByExample(example);
    }

    @Override
    public OpsDoItemInventory getDoItemInventoryById(long id) {
        return opsDoItemInventoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OpsDoItemInventory> getDoItemInventoryByDoId(String doId) {
        OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
        example.createCriteria().andDoIdEqualTo(doId).andDelflagEqualTo(0);
        return opsDoItemInventoryMapper.selectByExample(example);
    }

    @Override
    public List<OpsDoItemInventory> getDoItemInventoryByDoIdOrderByInventoryId(String doId) {
        OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
        example.createCriteria().andDoIdEqualTo(doId).andDelflagEqualTo(0);
        example.setOrderByClause(" inventory_id  ASC");
        return opsDoItemInventoryMapper.selectByExample(example);
    }

    @Override
    public List<OpsDoItemInventory> getDoItemInventoryByDoId(String doId, InventoryTableTypeEnum inventoryTableEnum) {
        OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
        OpsDoItemInventoryExample.Criteria criteria = example.createCriteria().andDoIdEqualTo(doId).andDelflagEqualTo(0);
        if (Objects.nonNull(inventoryTableEnum)) {
            criteria.andInventoryTableTypeEqualTo(inventoryTableEnum.getType());
        }
        example.setOrderByClause(" use_qty asc ");
        return opsDoItemInventoryMapper.selectByExample(example);
    }

    @Override
    public List<OpsDoItemInventory> getDoItemInventoryByDoId(String doId, String invTableType, Integer greaterThanUseQty) {
        OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
        example.createCriteria().andDoIdEqualTo(doId).andDelflagEqualTo(0)
                .andUseQtyGreaterThan(greaterThanUseQty).andInventoryTableTypeEqualTo(invTableType);
        return opsDoItemInventoryMapper.selectByExample(example);
    }

    @Override
    public List<OpsDoItemInventory> getDoItemInventoryByInventoryId(long InventoryId, InventoryTableTypeEnum inventoryTableEnum) {
        OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
        OpsDoItemInventoryExample.Criteria criteria = example.createCriteria().andDelflagEqualTo(0)
                .andInventoryIdEqualTo(InventoryId);
        if (Objects.nonNull(inventoryTableEnum)) {
            criteria.andInventoryTableTypeEqualTo(inventoryTableEnum.getType());
        }
        example.setOrderByClause(" use_qty asc ");
        return opsDoItemInventoryMapper.selectByExample(example);
    }

    @Override
    public OpsDoDto getOpsDoDto(String doId) throws OpsException {
        OpsDo opsDo = getDoByDoId(doId);
        OpsDoItem doItem = getDoItemByDoId(doId);
        List<OpsDoItemInventory> doItemInventoryList = getDoItemInventoryByDoId(doId);
        if (opsDo == null || doItem == null) {
            throw Exceptions.OpsException("未查询到do信息");
        }
        OpsDoDto opsDoDto = new OpsDoDto(opsDo, doItem, doItemInventoryList);
        return opsDoDto;
    }

    /**
     * 订单号(7位数)查DO
     */
    @Override
    public List<OpsDo> findDoListByOrderNo(String orderNo) {
        OpsDoExample example = new OpsDoExample();
        example.createCriteria().andOrderIdEqualTo(orderNo).andDelflagEqualTo(0);
        example.setOrderByClause(" id asc ");
        return opsDoMapper.selectByExample(example);
    }


    @Override
    public List<OpsDo> findDoListByOrder(String orderNo, Integer item) {
        OpsDoExample example = new OpsDoExample();
        example.createCriteria().andOrderIdEqualTo(orderNo).andOrderItemEqualTo(String.valueOf(item)).andDelflagEqualTo(0);
        example.setOrderByClause(" id asc ");
        return opsDoMapper.selectByExample(example);
    }

    @Override
    public List<OpsDo> findAllJYCKByOrder(String OrderId, String OrderItem) {
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andOrderIdEqualTo(OrderId);
        if (StringUtils.hasText(OrderItem)) {
            criteria.andOrderItemEqualTo(OrderItem);
        }
        criteria.andDoTypeEqualTo(DoTypeEnum.JYCK.getType());
        return opsDoMapper.selectByExample(example);
    }

    @Override
    public List<OpsDo> findAllDBCKByOrder(String OrderId, String OrderItem) {
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andOrderIdEqualTo(OrderId);
        if (StringUtils.hasText(OrderItem)) {
            criteria.andOrderItemEqualTo(OrderItem);
        }
        criteria.andDoTypeEqualTo(DoTypeEnum.DBCK.getType());
        return opsDoMapper.selectByExample(example);
    }

    @Override
    public OpsDo findByOrderOrderByNumMaxOne(String OrderId, String OrderItem, DoTypeEnum doTypeEnum) throws OpsException {
        OpsDoExample example = new OpsDoExample();
        example.setOrderByClause("NUM DESC");
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(OrderId);
        criteria.andOrderItemEqualTo(OrderItem);
        criteria.andDoTypeEqualTo(doTypeEnum.getType());
        List<OpsDo> doList = opsDoMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(doList)) {
            return doList.get(0);
        } else {
            throw Exceptions.OpsException("不存在物流指令");
        }
    }


    /**
     * @description 查询出库指令，根据指令类型
     * @author C12961
     * @date 2022/4/1 13:56
     */
    @Override
    public List<OpsDo> findDoListByOrder(String OrderId, String OrderItem, Integer num, DoTypeEnum doTypeEnum) throws OpsException {
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andOrderIdEqualTo(OrderId);
        if (StringUtils.hasText(OrderItem)) {
            criteria.andOrderItemEqualTo(OrderItem);
        }
        if (Objects.nonNull(num)) {
            criteria.andNumEqualTo(num);
        }
        if (Objects.nonNull(doTypeEnum)) {
            criteria.andDoTypeEqualTo(doTypeEnum.getType());
        }
        return opsDoMapper.selectByExample(example);
    }

    @Deprecated
    @Override
    public List<OpsDo> findDoListByOrder(String orderId, String orderItem, Integer num, Boolean assModelFlag, DoTypeEnum doTypeEnum) throws OpsException {
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0);
        criteria.andOrderIdEqualTo(orderId);
        if (org.apache.commons.lang.StringUtils.isNotBlank(orderItem)) {
            criteria.andOrderItemEqualTo(orderItem);
        }
        if (Objects.nonNull(doTypeEnum)) {
            criteria.andDoTypeEqualTo(doTypeEnum.getType());
        }
        if (Objects.nonNull(num)) {
            // 拆分型号
            if (Objects.nonNull(doTypeEnum) && DoTypeEnum.JYCK.getType().equals(doTypeEnum.getType()) && assModelFlag) {
                criteria.andNumEqualTo(1);
                criteria.andAssNumEqualTo(0);
            } else if (assModelFlag) {
                criteria.andNumEqualTo(1);
                criteria.andAssNumEqualTo(num);
            } else {// 整出和拆分数量
                criteria.andNumEqualTo(num);
                criteria.andAssNumEqualTo(0);
            }
        }
        return opsDoMapper.selectByExample(example);
    }

    /**
     * sourceEnum 无用，因修改引用成本过大，暂不修改，只去掉查询条件
     */
    @Override
    public OpsDo findJYCKByOrder(String OrderId, String OrderItem, Integer num, DoSourceEnum sourceEnum) throws OpsException {
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andOrderIdEqualTo(OrderId).andOrderItemEqualTo(OrderItem).andNumEqualTo(num);
        criteria.andDoTypeEqualTo(DoTypeEnum.JYCK.getType());
        List<OpsDo> doList = opsDoMapper.selectByExample(example);
        if (doList.isEmpty()) {
            return null;
        } else if (doList.size() > 1) {
            throw Exceptions.OpsException("查询出" + doList.size() + "条交易出库单");
        }
        return doList.get(0);
    }

    @Override
    public OpsDo getJYCKByOrder(String OrderId, String OrderItem, Integer num, DoSourceEnum sourceEnum) throws OpsException {
        OpsDo jyck = findJYCKByOrder(OrderId, OrderItem, num, sourceEnum);
        if (jyck == null) {
            throw Exceptions.OpsException("查询出0条交易出库单");
        }
        return jyck;
    }

    /**
     * 非通知发货用，查询一条调拨单
     * splitNo可能是num,也可能是ass_num，得看拆分类型
     */
    @Override
    public OpsDo findDBCKByOrder(String OrderId, String OrderItem, Integer splitNo, DoSourceEnum sourceEnum) throws OpsException {
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andOrderIdEqualTo(OrderId);
        criteria.andOrderItemEqualTo(OrderItem);
        criteria.andDoTypeEqualTo(DoTypeEnum.DBCK.getType());
        if (Objects.nonNull(splitNo)) {
            // 整出和拆分数量
            if (DoSourceEnum.ALL.equals(sourceEnum) || DoSourceEnum.CG.equals(sourceEnum)) {
                criteria.andNumEqualTo(splitNo);
                criteria.andAssNumEqualTo(0);
            }
            if (DoSourceEnum.ASSQTY.equals(sourceEnum)) {
                criteria.andNumEqualTo(splitNo);
                criteria.andAssNumEqualTo(0);
            }
            // 拆分型号
            if (DoSourceEnum.ASSModelNo.equals(sourceEnum)) {
                criteria.andNumEqualTo(1);
                criteria.andAssNumEqualTo(splitNo);
            }
        }
        List<OpsDo> doList = opsDoMapper.selectByExample(example);
        if (doList.isEmpty()) {
            return null;
        } else if (doList.size() == 1) {
            return doList.get(0);
        } else {
            throw Exceptions.OpsException("查询出" + doList.size() + "条调拨出库单");
        }
    }

    @Override
    public OpsDo getDBCKByOrder(String OrderId, String OrderItem, Integer splitNo, DoSourceEnum sourceEnum) throws OpsException {
        OpsDo dbck = findDBCKByOrder(OrderId, OrderItem, splitNo, sourceEnum);
        if (dbck == null) {
            throw Exceptions.OpsException("查询出0条调拨出库单");
        } else {
            return dbck;
        }
    }

    @Override
    public OpsDo findCGDBCKByOrder(String OrderId, String OrderItem, Integer num) throws OpsException {
        if (Objects.isNull(num)) {
            num = 0;
        }
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andDoTypeEqualTo(DoTypeEnum.CGDBCK.getType())
                .andOrderIdEqualTo(OrderId).andOrderItemEqualTo(OrderItem).andNumEqualTo(num);
        List<OpsDo> doList = opsDoMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(doList)) {
            return null;
        } else if (doList.size() == 1) {
            return doList.get(0);
        } else {
            throw Exceptions.OpsException("查询出" + doList.size() + "条采购调拨出库单");
        }
    }

    /***************** 5.十四位单号+指令类型 通知发货查询*****************************/

    /**
     * 通知发货查询，通用查询
     *
     * @param doTypeEnum JYCK DBCK CGDBCK TKCK ....
     */
    @Override
    public List<OpsDo> findDoListByOrder(String OrderId, String OrderItem, Integer num, Integer assNum, DoTypeEnum... doTypeEnum) throws OpsException {
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andOrderIdEqualTo(OrderId);
        if (StringUtils.hasText(OrderItem)) {
            criteria.andOrderItemEqualTo(OrderItem);
        }
        if (Objects.nonNull(num)) {
            criteria.andNumEqualTo(num);
        }
        if (Objects.nonNull(assNum)) {
            criteria.andAssNumEqualTo(assNum);
        }
        if (Objects.nonNull(doTypeEnum)) {
            List<String> list = Arrays.stream(doTypeEnum).map(DoTypeEnum::getType).collect(Collectors.toList());
            criteria.andDoTypeIn(list);
        }
        return opsDoMapper.selectByExample(example);
    }

    /**
     * 通知发货查询调拨单
     */
    @Override
    public List<OpsDo> findDBCKByOrder(String OrderId, String OrderItem, Integer num, Integer assNum, Boolean assModelFlag) {
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andOrderIdEqualTo(OrderId).andOrderItemEqualTo(OrderItem).andNumEqualTo(num).andAssNumEqualTo(assNum);
        criteria.andDoTypeEqualTo(DoTypeEnum.DBCK.getType());
        if (assModelFlag != null) {
            if (assModelFlag) {
                criteria.andDoSourceEqualTo(DoSourceEnum.ASSModelNo.getType());
            } else {
                List<String> list = Arrays.asList(DoSourceEnum.CG.getType(), DoSourceEnum.ALL.getType(), DoSourceEnum.ASSQTY.getType());
                criteria.andDoSourceIn(list);
            }
        }
        return opsDoMapper.selectByExample(example);
    }


    /**
     * bugid: 16290 c14717 20250212
     * 特殊型号，固定仓库
     *
     * @param modelNo
     * @return
     * @throws OpsException
     */
    @Override
    public Boolean checkSpeModelNoSpeWarehouse(String modelNo) throws OpsException {
//        int i = productSpecialModelDao.countSpecialModelNo(modelNo);
//        //查询备案型号
//        return i != 0;

        return false;
    }


    @Override
    public List<OpsDo> findDBCKListByOrder(String OrderId, String OrderItem, Integer num, DoSourceEnum sourceEnum) throws OpsException {
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andOrderIdEqualTo(OrderId);
        criteria.andOrderItemEqualTo(OrderItem);
        criteria.andDoTypeEqualTo(DoTypeEnum.DBCK.getType());
        if (Objects.nonNull(num)) {
            // 整出和拆分数量
            if (DoSourceEnum.ALL.equals(sourceEnum) || DoSourceEnum.CG.equals(sourceEnum)) {
                criteria.andNumEqualTo(num);
                criteria.andAssNumEqualTo(0);
            }
            if (DoSourceEnum.ASSQTY.equals(sourceEnum)) {
                criteria.andNumEqualTo(num);
                criteria.andAssNumEqualTo(0);
            }
            // 拆分型号
            if (DoSourceEnum.ASSModelNo.equals(sourceEnum)) {
                criteria.andNumEqualTo(1);
                criteria.andAssNumEqualTo(num);
            }
        }
        return opsDoMapper.selectByExample(example);
    }

    @Override
    public List<OpsDo> findAllTypeDBCKListByOrder(String OrderId, String OrderItem, Integer num, DoSourceEnum sourceEnum) throws OpsException {
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andOrderIdEqualTo(OrderId);
        criteria.andOrderItemEqualTo(OrderItem);
        List<String> doTypeList = Arrays.asList(DoTypeEnum.DBCK.getType(), DoTypeEnum.CGDBCK.getType(), DoTypeEnum.TKCK.getType());
        criteria.andDoTypeIn(doTypeList);
        if (Objects.nonNull(num)) {
            // 整出和拆分数量
            if (DoSourceEnum.ALL.equals(sourceEnum) || DoSourceEnum.CG.equals(sourceEnum)) {
                criteria.andNumEqualTo(num);
                criteria.andAssNumEqualTo(0);
            }
            if (DoSourceEnum.ASSQTY.equals(sourceEnum)) {
                criteria.andNumEqualTo(num);
                criteria.andAssNumEqualTo(0);
            }
            // 拆分型号
            if (DoSourceEnum.ASSModelNo.equals(sourceEnum)) {
                criteria.andNumEqualTo(1);
                criteria.andAssNumEqualTo(num);
            }
        }
        return opsDoMapper.selectByExample(example);
    }

}
