package com.sales.ops.serviceimpl.wmOrder;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsRoItemInventoryMapper;
import com.sales.ops.db.dao.OpsRoItemMapper;
import com.sales.ops.db.dao.OpsRoMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.OpsRoDto;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.enums.RoSourceEnum;
import com.sales.ops.enums.RoTypeEnum;
import com.sales.ops.enums.SendStatusEnum;
import com.sales.ops.service.wmOrder.BaseRoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author C12961
 * @date 2022/3/31 17:52
 */
@Slf4j
@Service
public class BaseRoServiceImpl implements BaseRoService {


    @Autowired
    private OpsRoMapper opsRoMapper;
    @Autowired
    private OpsRoItemMapper opsRoItemMapper;
    @Autowired
    private OpsRoItemInventoryMapper opsRoItemInventoryMapper;


    /**
     * @description 根据发票号查询是否为虚拟组换单
     * @author C12961
     * @date 2023/5/24 10:27
     */
    @Override
    public List<OpsRo> isExchangeOrder(String invoiceNo) {
        List<OpsRo> roList = new ArrayList<>();
        if (invoiceNo.startsWith("ZH")) {
            List<OpsRo> opsRoList = findRoByInvoiceNo(invoiceNo);
            for (OpsRo opsRo : opsRoList) {
                if (StringUtils.equals(RoTypeEnum.ZHRKOW.getType(), opsRo.getRoType())) {
                    log.info("此发票为组换入库仅调wms库存:{},{}", invoiceNo, opsRo.getRoId());
                    roList.add(opsRo);
                }
            }
        }
        return roList;
    }

    @Override
    public boolean existsRoByRoId(String roId) {
        OpsRoExample example = new OpsRoExample();
        OpsRoExample.Criteria cri = example.createCriteria();
        cri.andRoIdEqualTo(roId);
        long count = opsRoMapper.countByExample(example);
        return count > 0;
    }

    @Override
    public Long countRoForNotSendToWmsByInvoiceNo(String invoiceNo, Long invoiceId) {
        OpsRoExample exa = new OpsRoExample();
        Integer init = Integer.valueOf(SendStatusEnum.INIT.getType());// 0:未下发到物流
        Integer fail = Integer.valueOf(SendStatusEnum.FAIR.getType());// 2:下发失败
        List<Integer> wmsFlags = Arrays.asList(init, fail);
        OpsRoExample.Criteria criteria = exa.createCriteria();
        if (invoiceId != null) {
            criteria.andInvoiceIdEqualTo(invoiceId);
        }
        criteria.andInvoiceNoEqualTo(invoiceNo).andIsWmsIn(wmsFlags).andDelflagEqualTo(0);
        return opsRoMapper.countByExample(exa);
    }
    @Override
    public List<OpsRo> findRoByOrderNo(String orderId, Integer orderItem) {
        return findRoByOrderNo(orderId, orderItem.toString());
    }

    @Override
    public List<OpsRo> findRoByOrderNo(String orderId, String orderItem) {
        OpsRoExample exa = new OpsRoExample();
        OpsRoExample.Criteria cri = exa.createCriteria();
        cri.andOrderIdEqualTo(orderId);
        cri.andOrderItemEqualTo(orderItem.toString());
        cri.andDelflagEqualTo(0);
        return opsRoMapper.selectByExample(exa);
    }

    @Override
    public List<OpsRo> findRoByOrderNo(String orderId) {
        OpsRoExample exa = new OpsRoExample();
        OpsRoExample.Criteria cri = exa.createCriteria();
        cri.andOrderIdEqualTo(orderId);
        cri.andDelflagEqualTo(0);
        return opsRoMapper.selectByExample(exa);
    }
    @Override
    public List<OpsRo> findRoByOrderNoAndTHType(String orderId, String orderItem, Integer num) {
        OpsRoExample roExample = new OpsRoExample();
        roExample.createCriteria().andDelflagEqualTo(0)
                .andOrderIdEqualTo(orderId).andOrderItemEqualTo(orderItem)
                .andNumEqualTo(num).andRoTypeEqualTo(RoTypeEnum.THRK.getType()).andRoSourceEqualTo(RoSourceEnum.RETURN.getType());
        return opsRoMapper.selectByExample(roExample);
    }

    @Override
    public List<OpsRo> findRoByInvoiceNo(String invoiceNo) {
        OpsRoExample roExample = new OpsRoExample();
        roExample.createCriteria().andDelflagEqualTo(0).andInvoiceNoEqualTo(invoiceNo);
        return opsRoMapper.selectByExample(roExample);
    }

    @Override
    public List<OpsRo> findRoByInvoiceNoAndInvoiceId(String invoiceNo, Long invoiceId) {
        OpsRoExample roExample = new OpsRoExample();
        OpsRoExample.Criteria criteria = roExample.createCriteria();
        criteria.andDelflagEqualTo(0).andInvoiceNoEqualTo(invoiceNo);
        if(invoiceId != null){
            criteria.andInvoiceIdEqualTo(invoiceId);
        }
        return opsRoMapper.selectByExample(roExample);
    }

    @Override
    public OpsRo findRoByRoId(String roId) throws OpsException {
        OpsRoExample exa = new OpsRoExample();
        OpsRoExample.Criteria cri = exa.createCriteria();
        cri.andRoIdEqualTo(roId);
        cri.andDelflagEqualTo(0);
        List<OpsRo> opsRos = opsRoMapper.selectByExample(exa);
        if (CollectionUtils.isEmpty(opsRos)) {
            return null;
        } else if (opsRos.size() == 1) {
            return opsRos.get(0);
        } else {
            throw Exceptions.OpsException("查询出多条Ro.RoId:" + roId);
        }
    }

    @Override
    public OpsRo getRoByRoId(String roId) throws OpsException {
        OpsRoExample example = new OpsRoExample();
        example.createCriteria().andDelflagEqualTo(0).andRoIdEqualTo(roId);
        List<OpsRo> opsRos = opsRoMapper.selectByExample(example);
        if (opsRos.size() == 1) {
            return opsRos.get(0);
        } else {
            throw Exceptions.OpsException("查询出" + opsRos.size() + "条ro" + roId);
        }
    }


    @Override
    public List<OpsRo> findRoByOrderItemNum(String orderId, Integer item, Integer num, Integer assNum) {
        OpsRoExample exa = new OpsRoExample();
        OpsRoExample.Criteria cri = exa.createCriteria();
        cri.andOrderIdEqualTo(orderId);
        if (Objects.nonNull(item)) {
            cri.andOrderItemEqualTo(item.toString());
        }
        if (Objects.nonNull(num)) {
            cri.andNumEqualTo(num);
        }
        if (Objects.nonNull(assNum)) {
            cri.andAssNumEqualTo(assNum);
        }
        cri.andDelflagEqualTo(0);
        return opsRoMapper.selectByExample(exa);
    }

    @Override
    public List<OpsRo> findRoByOrderItemNum(String orderId, Integer item, Integer num, Integer assNum, Integer extNum) {
        OpsRoExample exa = new OpsRoExample();
        OpsRoExample.Criteria cri = exa.createCriteria();
        cri.andOrderIdEqualTo(orderId);
        if (Objects.nonNull(item)) {
            cri.andOrderItemEqualTo(item.toString());
        }
        if (Objects.nonNull(num)) {
            cri.andNumEqualTo(num);
        }
        if (Objects.nonNull(assNum)) {
            cri.andAssNumEqualTo(assNum);
        }
        if(Objects.nonNull(extNum)){
            cri.andExtNumEqualTo(extNum);
        }
        cri.andDelflagEqualTo(0);
        return opsRoMapper.selectByExample(exa);
    }

    @Override
    public List<OpsRo> findRoByOrderItemNum(String orderId, Integer item, Integer num, Integer assNum, String roType) {
        OpsRoExample exa = new OpsRoExample();
        OpsRoExample.Criteria cri = exa.createCriteria();
        cri.andOrderIdEqualTo(orderId);
        if (Objects.nonNull(item)) {
            cri.andOrderItemEqualTo(item.toString());
        }
        if (Objects.nonNull(num)) {
            cri.andNumEqualTo(num);
        }
        if (Objects.nonNull(assNum)) {
            cri.andAssNumEqualTo(assNum);
        }
        cri.andRoTypeEqualTo(roType);
        cri.andDelflagEqualTo(0);
        return opsRoMapper.selectByExample(exa);
    }

    @Override
    public List<OpsRo> findRoByOrderItemNum(String orderId, Integer item, Integer num, Integer assNum, Integer extNum, String roType) {
        OpsRoExample exa = new OpsRoExample();
        OpsRoExample.Criteria cri = exa.createCriteria();
        cri.andOrderIdEqualTo(orderId);
        if (Objects.nonNull(item)) {
            cri.andOrderItemEqualTo(item.toString());
        }
        if (Objects.nonNull(num)) {
            cri.andNumEqualTo(num);
        }
        if (Objects.nonNull(assNum)) {
            cri.andAssNumEqualTo(assNum);
        }
        if(Objects.nonNull(extNum)){
            cri.andExtNumEqualTo(extNum);
        }
        cri.andRoTypeEqualTo(roType);
        cri.andDelflagEqualTo(0);
        return opsRoMapper.selectByExample(exa);
    }

    @Override
    public List<OpsRo> findRoByOrderItemNumExtNUM(String orderId, Integer item, Integer num, Integer assNum,Integer extNum, String roType) {
        OpsRoExample exa = new OpsRoExample();
        OpsRoExample.Criteria cri = exa.createCriteria();
        cri.andOrderIdEqualTo(orderId);
        if (Objects.nonNull(item)) {
            cri.andOrderItemEqualTo(item.toString());
        }
        if (Objects.nonNull(num)) {
            cri.andNumEqualTo(num);
        }
        if (Objects.nonNull(assNum)) {
            cri.andAssNumEqualTo(assNum);
        }
        if(Objects.nonNull(extNum)){
            cri.andExtNumEqualTo(extNum);
        }
        cri.andRoTypeEqualTo(roType);
        cri.andDelflagEqualTo(0);
        return opsRoMapper.selectByExample(exa);
    }

    @Override
    public List<OpsRo> findDBRoByDBDo(OpsDo opsDo) {
        boolean isdb = DoTypeEnum.isDB(opsDo.getDoType());
        if (isdb) {
            DoTypeEnum doTypeEnum = DoTypeEnum.getType(opsDo.getDoType());
            OpsRoExample exa = new OpsRoExample();
            OpsRoExample.Criteria cri = exa.createCriteria();
            cri.andOrderIdEqualTo(opsDo.getOrderId());
            if (Objects.nonNull(opsDo.getOrderItem())) {
                cri.andOrderItemEqualTo(opsDo.getOrderItem());
            }
            if (Objects.nonNull(opsDo.getNum())) {
                cri.andNumEqualTo(opsDo.getNum());
            }
            if (Objects.nonNull(opsDo.getAssNum())) {
                cri.andAssNumEqualTo(opsDo.getAssNum());
            }
            cri.andRoTypeEqualTo(doTypeEnum.getRoTypeEnum().getType());
            cri.andDelflagEqualTo(0);
            return opsRoMapper.selectByExample(exa);
        }
        return null;
    }

    @Override
    public List<OpsRoItem> findRoItemsByRoId(String roId) {
        OpsRoItemExample example = new OpsRoItemExample();
        OpsRoItemExample.Criteria criteria = example.createCriteria();
        criteria.andRoIdEqualTo(roId);
        criteria.andDelflagEqualTo(0);// 删除标
        List<OpsRoItem> opsRoItems = opsRoItemMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(opsRoItems)) {
            return null;
        }
        return opsRoItems;
    }

    @Override
    public OpsRoItem findRoItemByRoId(String roId) throws OpsException {
        List<OpsRoItem> opsRoItems = findRoItemsByRoId(roId);
        if (CollectionUtils.isEmpty(opsRoItems)) {
            return null;
        } else if (opsRoItems.size() == 1) {
            return opsRoItems.get(0);
        } else {
            throw Exceptions.OpsException("查询出多条RoItem.RoId:" + roId);
        }
    }

    @Override
    public List<OpsRoItemInventory> findRoItemInventoryByRoId(String roId) {
        OpsRoItemInventoryExample example = new OpsRoItemInventoryExample();
        example.createCriteria().andDelflagEqualTo(0).andRoIdEqualTo(roId);
        return opsRoItemInventoryMapper.selectByExample(example);
    }

    @Override
    public List<OpsRoItemInventory> findRoItemInventoryByInventoryId(long InventoryId) {
        OpsRoItemInventoryExample example = new OpsRoItemInventoryExample();
        example.createCriteria().andDelflagEqualTo(0).andInventoryIdEqualTo(InventoryId);
        return opsRoItemInventoryMapper.selectByExample(example);
    }


    @Override
    public int insertOpsRo(OpsRo ro) {
        Date date = new Date();
        if (ro.getCreTime() == null) {
            ro.setCreTime(date);
        }
        if (ro.getModifyTime() == null) {
            ro.setModifyTime(date);
        }
        if (ro.getVersion() == null) {
            ro.setVersion(0);
        }
        if (ro.getDelflag() == null) {
            ro.setDelflag(0);
        }
        return opsRoMapper.insertSelective(ro);
    }

    @Override
    public int insertOpsRoItem(OpsRoItem roItem) {
        Date date = new Date();
        if (roItem.getCreTime() == null) {
            roItem.setCreTime(date);
        }
        if (roItem.getModifyTime() == null) {
            roItem.setModifyTime(date);
        }
        if (roItem.getVersion() == null) {
            roItem.setVersion(0);
        }
        if (roItem.getDelflag() == null) {
            roItem.setDelflag(0);
        }
        return opsRoItemMapper.insertSelective(roItem);
    }

    @Override
    public int insertOpsRoItemInventory(OpsRoItemInventory roItemInventory) {
        Date date = new Date();
        if (roItemInventory.getCreTime() == null) {
            roItemInventory.setCreTime(date);
        }
        if (roItemInventory.getModifyTime() == null) {
            roItemInventory.setModifyTime(date);
        }
        if (roItemInventory.getVersion() == null) {
            roItemInventory.setVersion(0L);
        }
        if (roItemInventory.getDelflag() == null) {
            roItemInventory.setDelflag(0);
        }
        return opsRoItemInventoryMapper.insertSelective(roItemInventory);
    }

    @Override
    public OpsRoDto insertRo(OpsRoDto opsRoDto) {
        OpsRo opsRo = opsRoDto.getOpsRo();
        OpsRoItem opsRoItem = opsRoDto.getOpsRoItem();
        List<OpsRoItemInventory> roItemInventoryList = opsRoDto.getRoItemInventoryList();
        insertOpsRo(opsRo);
        insertOpsRoItem(opsRoItem);
        if (CollectionUtils.isNotEmpty(roItemInventoryList)) {
            for (OpsRoItemInventory roItemInventory : roItemInventoryList) {
                insertOpsRoItemInventory(roItemInventory);
            }
        }
        return opsRoDto;
    }

    @Override
    public int deleteRoItem(Long id, UserDto user) throws OpsException {
        OpsRoItem updateRoItem = new OpsRoItem();
        updateRoItem.setId(id);
        updateRoItem.setDelflag(1);
        updateRoItem.setModifyTime(new Date());
        updateRoItem.setModifier(user.getUserName());
        return updateRoItemById(updateRoItem);
    }
    @Override
    public int deleteRoItemInventoryByRoId(String roId) {
        OpsRoItemInventory updateInventory = new OpsRoItemInventory();
        updateInventory.setModifier("删除");
        updateInventory.setModifyTime(new Date());
        updateInventory.setDelflag(1);// 删除标志
        OpsRoItemInventoryExample roInventoryUpdateExample = new OpsRoItemInventoryExample();
        OpsRoItemInventoryExample.Criteria roBarcodeUpdateCriteria = roInventoryUpdateExample.createCriteria();
        roBarcodeUpdateCriteria.andRoIdEqualTo(roId);
        roBarcodeUpdateCriteria.andDelflagEqualTo(0);
        return opsRoItemInventoryMapper.updateByExampleSelective(updateInventory, roInventoryUpdateExample);
    }

    @Override
    public void updateRoById(OpsRo opsRo) throws OpsException {
        if (opsRo.getId() == null) {
            throw Exceptions.OpsException("更新OpsRo失败");
        }
        opsRo.setModifyTime(new Date());
        opsRoMapper.updateByPrimaryKeySelective(opsRo);
    }

    @Override
    public int updateRoItemById(OpsRoItem opsRoItem) throws OpsException {
        if (opsRoItem.getId() == null) {
            throw Exceptions.OpsException("更新OpsRoItem失败");
        }
        opsRoItem.setModifyTime(new Date());
        return opsRoItemMapper.updateByPrimaryKeySelective(opsRoItem);
    }



    /**
     * 更新收货数量记录
     */
    @Override
    public int updateOpsRoItemRecQty(String roId, int recQty, int version, String userName) throws OpsException {
        OpsRoItem updateRoItem = new OpsRoItem();
        updateRoItem.setModifier(userName);
        updateRoItem.setModifyTime(new Date());
        updateRoItem.setRecQty(recQty);// 已收货数量+现收
        updateRoItem.setVersion(version + 1);// 幂等版本变更
        OpsRoItemExample roItemUpdateExample = new OpsRoItemExample();
        OpsRoItemExample.Criteria criteria = roItemUpdateExample.createCriteria();
        criteria.andRoIdEqualTo(roId);// 收货指令
        criteria.andVersionEqualTo(version);// 幂等变更查询条件
        criteria.andDelflagEqualTo(0);// 删除标识
        int i = opsRoItemMapper.updateByExampleSelective(updateRoItem, roItemUpdateExample);
        if(i<1){
            throw Exceptions.OpsException("系统并发异常，Ro入库数修改失败");
        }
        return i;
    }

    /**
     * 更新OpsRoStatus状态
     * @param status 2收货中 3收完
     */
    @Override
    public int updateOpsRoStatus(String roId, int status, int version, String userName)  {
        OpsRo updateRo = new OpsRo();
        updateRo.setRoStatus(status);// 收货中
        updateRo.setModifier(userName);
        updateRo.setModifyTime(new Date());
        updateRo.setVersion(version + 1);
        OpsRoExample opsRoUpdateExample = new OpsRoExample();
        OpsRoExample.Criteria criteria = opsRoUpdateExample.createCriteria();
        criteria.andDelflagEqualTo(0).andVersionEqualTo(version).andRoIdEqualTo(roId);// 收货指令
        return opsRoMapper.updateByExampleSelective(updateRo, opsRoUpdateExample);
    }

    @Override
    public int updateOpsRoWarehouse(String roId,int version,String warehouseCode, String userDto)  {
        OpsRo updateRo = new OpsRo();
        updateRo.setWarehouseCode(warehouseCode);// 收货中
        updateRo.setModifier(userDto);
        updateRo.setModifyTime(new Date());
        updateRo.setVersion(version + 1);
        OpsRoExample opsRoUpdateExample = new OpsRoExample();
        OpsRoExample.Criteria criteria = opsRoUpdateExample.createCriteria();
        criteria.andDelflagEqualTo(0).andVersionEqualTo(version).andRoIdEqualTo(roId);// 收货指令
        return opsRoMapper.updateByExampleSelective(updateRo, opsRoUpdateExample);
    }



    @Override
    public void updateOpsRoSignForInvoiceNo(String invoiceNo, Long invoiceId, String userName) {
        OpsRo updateRo = new OpsRo();
        updateRo.setIsSign(true);
        updateRo.setSignTime(new Date());
        updateRo.setSignUsername(userName);
        updateRo.setModifier(userName);
        updateRo.setModifyTime(new Date());
        OpsRoExample roUpdateExample = new OpsRoExample();
        OpsRoExample.Criteria criteria = roUpdateExample.createCriteria();
        criteria.andInvoiceNoEqualTo(invoiceNo);// 发票号
        if (invoiceId != null) {
            criteria.andInvoiceIdEqualTo(invoiceId);// 发票ID
        }
        criteria.andDelflagEqualTo(0);// 删除标识
        opsRoMapper.updateByExampleSelective(updateRo, roUpdateExample);
    }


}
