package com.sales.ops.serviceimpl.wmOrder;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.dao.OpsInventoryLogMapper;
import com.sales.ops.db.dao.OpsInventoryMoveMapper;
import com.sales.ops.db.dao.OpsSysLogMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OpsDoPostDao;
import com.sales.ops.db.extdao.OpsInventoryDao;
import com.sales.ops.dto.order.FinishPoListForDto;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.enums.InventoryStatusEnum;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.sales.ops.service.wm.WmFinishInvMoveService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：采购完纳扣减InvMove数量
 * @date ：Created in 2023/8/22 10:57
 */

@Service
public class WmFinishInvMoveServiceImpl implements WmFinishInvMoveService {

    @Resource
    private OpsInventoryLogMapper inventoryLogMapper;

    @Resource
    private OpsInventoryMoveMapper invMoveMapper;

    @Autowired
    private OpsInventoryDao opsInventoryDao;

    @Autowired
    private OpsSysLogMapper logMapper;

    @Autowired
    private OpsDoPostDao opsDoPostDao;

    /**
     * bugid :11836 c14717 20230822
     * 采购完纳 扣减move表P状态的数量
     * @action 扣减move数量 记录日志
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exeFinishInvMoveQty(FinishPoListForDto paramDto) throws OpsException {
        if(Objects.isNull(paramDto.getpSplitNo())){
            paramDto.setpSplitNo(0);
        }
        OpsInventoryMoveExample ex = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = ex.createCriteria();
        criteria.andDelflagEqualTo(0);
        criteria.andAssociateNoEqualTo(paramDto.getpOrderNo());
        criteria.andAssociateNoItemEqualTo(paramDto.getpOrderItem());
        criteria.andAssociateNoSplitnoEqualTo(paramDto.getpSplitNo());
        criteria.andInventoryStatusEqualTo(InventoryStatusEnum.PRODUCE.getCode());
        List<OpsInventoryMove> invMoveList = invMoveMapper.selectByExample(ex);
        if(CollectionUtils.isEmpty(invMoveList)){
            throw Exceptions.OpsException("无生产中在途数据");
        }
        List<OpsInventoryMove> opsInventoryMoves = handleFDCDoNew(invMoveList, paramDto);
        if(CollectionUtils.isNotEmpty(opsInventoryMoves)){
            invMoveList = opsInventoryMoves;
        }
        int sumInvMovePQty = 0;
        for (OpsInventoryMove move : invMoveList) {
            int targetQty =  move.getQuantity() - move.getPrepareQuantity();
            sumInvMovePQty = sumInvMovePQty + targetQty;
        }
        //move表扣减数量 = 采购数量 - 完纳数量
        int uQty = paramDto.getpQty() - paramDto.getFinishPoQty();

        if((sumInvMovePQty - uQty) < 0){
            throw Exceptions.OpsException("生产中在途数据预占数量大于完纳数量");
        }
        for(OpsInventoryMove move : invMoveList){
            int targetQty =  move.getQuantity() - move.getPrepareQuantity();
            int invQty = 0;
            //当前在途数表可用数量大于（采购数量-完纳数量 ）
            if(targetQty - uQty >= 0){
                invQty = uQty;
            } else {
                //当前在途表可用数量小于（采购数量-完纳数量）
                invQty = targetQty;
                uQty = uQty - targetQty;
            }
            int rows = opsInventoryDao.updateQtyInventoryMove(-1*invQty, move.getInventoryId(), move.getVersion(), paramDto.getOperator());
            if (rows < 1) {
                throw Exceptions.OpsException("在途表库存更新异常:" + move.getInventoryId());
            }
            //bugid:12171 po完纳 move.qty=0 删除move
            if(move.getQuantity() - invQty == 0){
                move.setVersion(move.getVersion()+1);
                OpsInventoryMoveExample example = new OpsInventoryMoveExample();
                OpsInventoryMoveExample.Criteria cri = example.createCriteria();
                cri.andDelflagEqualTo(0).andVersionEqualTo( move.getVersion()).andInventoryIdEqualTo(move.getInventoryId());
                OpsInventoryMove record = new OpsInventoryMove();
                record.setVersion( move.getVersion() + 1);
                record.setModifyTime(DateUtil.getNow());
                record.setModifier(paramDto.getOperator());
                record.setDelflag(1);
                record.setDelTime(DateUtil.getNow());
                int rowsInvs = invMoveMapper.updateByExampleSelective(record, example);
                if (rowsInvs != 1) {
                    throw Exceptions.OpsException("delete库存更新异常ID:" + move.getInventoryId());
                }
                move.setDelflag(1);
            }
            //记录日志
            saveLog(invQty,move,paramDto.getOperator());
            //当前在途数表可用数量大于（采购数量-完纳数量 ） 结束循环
            if(invQty == uQty){
                break;
            }
        }
    }

    /**
     * 处理分库补库调拨单
     * @param invMoves
     */
    @Transactional(rollbackFor = Exception.class)
    public List<OpsInventoryMove> handleFDCDoNew(List<OpsInventoryMove> invMoves, FinishPoListForDto paramDto)  throws OpsException{
        // 验证采购数量和完纳数量
        if(Objects.isNull(paramDto) || Objects.isNull(paramDto.getpQty())
                || Objects.isNull(paramDto.getFinishPoQty()) || paramDto.getFinishPoQty() < 0
                || paramDto.getpQty() <= 0 || paramDto.getpQty() - paramDto.getFinishPoQty() <=0){
            return null;
        }
        int releaseQty = paramDto.getpQty() - paramDto.getFinishPoQty();
        for(OpsInventoryMove invMove:invMoves){
            if(releaseQty == 0){
                break;
            }
            // 处理分库指令
            Integer result =  handleDetail(invMove,paramDto,releaseQty);
            if(Objects.isNull(result)){
                continue;
            }
            releaseQty = result;
        }
        // 查询新的move
        OpsInventoryMoveExample ex = new OpsInventoryMoveExample();
        OpsInventoryMoveExample.Criteria criteria = ex.createCriteria();
        criteria.andDelflagEqualTo(0);
        criteria.andAssociateNoEqualTo(paramDto.getpOrderNo());
        criteria.andAssociateNoItemEqualTo(paramDto.getpOrderItem());
        criteria.andAssociateNoSplitnoEqualTo(paramDto.getpSplitNo());
        criteria.andInventoryStatusEqualTo(InventoryStatusEnum.PRODUCE.getCode());
        return invMoveMapper.selectByExample(ex);
    }

    /**
     * 处理分库补库调拨单 详情
     * @param
     */
    public Integer handleDetail(OpsInventoryMove invMove,FinishPoListForDto paramDto,Integer releaseQty) throws OpsException {
        if(releaseQty == 0){
            return 0;
        }
        // 验证是否是分库补库
        if(Objects.isNull(invMove.getSignWarehouseCode()) || Objects.isNull(invMove.getWarehouseCode())
                || invMove.getWarehouseCode().equals(invMove.getSignWarehouseCode())){
            return null;
        }
        String warehouseType = opsDoPostDao.getWarehouseType(invMove.getWarehouseCode());
        if(StringUtils.isBlank(warehouseType) || !warehouseType.equals(WarehouseTypeEnum.FDC.getHouseTypeCode())){
            return null;
        }
        // 预占是否占满
        if(!invMove.getPrepareQuantity().equals(invMove.getQuantity())){
            return null;
        }
        // doItemInv 需仅1条
        List<OpsDoItemInventory> doItemInvByTList = opsDoPostDao.getDoItemInvByTList(invMove.getInventoryId());
        if(CollectionUtils.isEmpty(doItemInvByTList) || doItemInvByTList.size() !=1){
            return null;
        }
        // doType 需是 CGDBCK
        OpsDo aDo = opsDoPostDao.getDo(doItemInvByTList.get(0).getDoId());
        if(Objects.isNull(aDo) || !DoTypeEnum.CGDBCK.getType().equals(aDo.getDoType())){
            return null;
        }
        // 采购数量需等于 doItem数量 且outQty > 0
        OpsDoItem doItem = opsDoPostDao.getDoItem(aDo.getDoId());

        if(Objects.isNull(doItem)){
            return null;
        }
        if (!doItem.getModelno().equals(invMove.getModelno())){
            return null;
        }
        if(Objects.nonNull(doItem.getOutQty()) &&  doItem.getOutQty() > 0){
            return null;
        }
        OpsRo ro = opsDoPostDao.getRo(aDo.getOrderId(), aDo.getOrderItem(), aDo.getNum(), aDo.getAssNum());
        if(releaseQty >= invMove.getQuantity()){
            if(doItem.getQty().equals(invMove.getQuantity())){
                opsDoPostDao.updateDoToDel(aDo.getDoId());
                opsDoPostDao.updateDoItemToDel(aDo.getDoId());
                opsDoPostDao.updateDoToItemInvDel(doItemInvByTList.get(0).getId());
                opsDoPostDao.updateROToDel(ro.getRoId());
                opsDoPostDao.updateRoItemDel(ro.getRoId());
            }else if(doItem.getQty() > invMove.getQuantity()){
                opsDoPostDao.updateDoItemQTY(aDo.getDoId(),doItem.getQty() - invMove.getQuantity());
                opsDoPostDao.updateRoItemQTY(ro.getRoId(),doItem.getQty() - invMove.getQuantity());
                opsDoPostDao.updateDoToItemInvDel(doItemInvByTList.get(0).getId());
                if(checkQty(aDo.getDoId(),doItem.getQty() - invMove.getQuantity())){
                    opsDoPostDao.updateDoWaitTypeOk(aDo.getDoId());
                }
            }
            // 释放预占数量
            opsInventoryDao.updatePreQtyInventoryMove(-1*invMove.getQuantity(),paramDto.getOperator(),invMove.getVersion(),invMove.getInventoryId());
            return releaseQty - invMove.getQuantity();
        }else {
            opsDoPostDao.updateDoItemQTY(aDo.getDoId(),doItem.getQty() - releaseQty);
            opsDoPostDao.updateRoItemQTY(ro.getRoId(),doItem.getQty() - releaseQty);
            opsDoPostDao.updateDoToItemInvQTY(doItemInvByTList.get(0).getId(),invMove.getQuantity() - releaseQty);
            // 是否变更waitType
            if(checkQty(aDo.getDoId(),doItem.getQty() - releaseQty)){
                opsDoPostDao.updateDoWaitTypeOk(aDo.getDoId());
            }
            // 释放预占数量
            opsInventoryDao.updatePreQtyInventoryMove(-1*releaseQty,paramDto.getOperator(),invMove.getVersion(),invMove.getInventoryId());
            return 0;
        }
    }

    /**
     * 对比doItemQty和 doItemInvQty 在库数量是否相等
     * @param doId
     * @param doItemQty
     * @return
     */
    public Boolean checkQty(String doId, Integer doItemQty){
        List<Integer> qtys = opsDoPostDao.getDoItemInvByNList(doId);
        if(CollectionUtils.isEmpty(qtys)){
            return false;
        }
        // 累加数量求和
        int sumQty = qtys.stream()
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
        return sumQty == doItemQty;

    }



    /**
     * 记录日志
     */
    public void saveLog( int qty, OpsInventoryMove invMove,String userName) throws OpsException {
        OpsSysLog opsLog = new OpsSysLog();
        opsLog.setTitle("库存变动日志");
        //调用链路
        StringBuilder buff = new StringBuilder();
        // 请求的参数
        buff.append("WmFinishInvMoveServiceImpl");
        buff.append(".");
        buff.append("exeFinishInvMoveQty");
        buff.append("(");
        buff.append("invId=");
        buff.append(invMove.getInventoryId());
        buff.append("qty=");
        buff.append(invMove.getQuantity());
        buff.append("finQty=");
        buff.append(qty);
        buff.append(")");
        opsLog.setParams(buff.toString());
        opsLog.setCreatetime(DateUtil.getNow());
        // 保存系统日志
    }
}
