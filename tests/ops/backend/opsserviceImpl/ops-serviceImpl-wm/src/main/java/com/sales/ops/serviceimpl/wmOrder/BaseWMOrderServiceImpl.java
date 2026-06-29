package com.sales.ops.serviceimpl.wmOrder;

import com.alibaba.nacos.common.utils.Objects;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.order.OpsPcoAndItemAndItemInventoryDto;
import com.sales.ops.enums.DoStatusEnum;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.service.wmOrder.BaseWMOrderService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询指令服务 应对拆分pco指令 新方法
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/11/4 8:16
 */
@Service
public class BaseWMOrderServiceImpl implements BaseWMOrderService {

    @Autowired
    private OpsDoMapper opsDoMapper;
    @Autowired
    private OpsDoItemMapper opsDoItemMapper;
    @Autowired
    private OpsDoItemInventoryMapper opsDoItemInventoryMapper;
    @Autowired
    private OpsPcoMapper opsPcoMapper;
    @Autowired
    private OpsPcoItemMapper opsPcoItemMapper;
    @Autowired
    private OpsPcoItemInventoryMapper pcoItemInventoryMapper;

    @Autowired
    private RcvdetailMapper rcvdetailMapper;

    /**
     * 筛选do类型
     * @param list doList
     * @param doTypeEnum doType
     * @return 返回do
     */
    @Override
    public List<OpsDo> getDos(@Nonnull List<OpsDo> list, @Nonnull DoTypeEnum doTypeEnum){
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.stream().filter(item -> doTypeEnum.getType().equals(item.getDoType())).collect(Collectors.toList());
    }

    /**
     * 筛选do类型
     * @param list doList
     * @param doTypeEnum doType jyck dbck
     * @param num 对比num
     * @return
     */
    @Override
    public List<OpsDo> getDos(@Nonnull List<OpsDo> list, @Nonnull DoTypeEnum doTypeEnum, @Nonnull Integer num){
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.stream().filter(item -> doTypeEnum.getType().equals(item.getDoType()) && item.getNum().equals(num) ).collect(Collectors.toList());
    }

    /**
     * 筛选do类型
     * @param list doList
     * @param doTypeEnum doType jyck dbck
     * @param num 对比num
     * @return
     */
    @Override
    public List<OpsDo> getDos(@Nonnull List<OpsDo> list, @Nonnull DoTypeEnum doTypeEnum, @Nonnull Integer num, @Nonnull Integer assNum){
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.stream().filter(item -> doTypeEnum.getType().equals(item.getDoType()) && item.getNum().equals(num) && item.getAssNum().equals(assNum) ).collect(Collectors.toList());
    }

    /**
     * 筛选do已发货
     * @param list
     * @return
     */
    @Override
    public List<OpsDo> getShippedDos(@Nonnull List<OpsDo> list){
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.stream().filter(item -> DoStatusEnum.PART.getStatus().equals(item.getDoStatus()) || DoStatusEnum.FINISH.getStatus().equals(item.getDoStatus()) ).collect(Collectors.toList());
    }


    /**
     * 查询do
     * @param orderId 不可为空
     * @param orderItem 可为空
     * @return List<OpsDo>
     */
    @Override
    public List<OpsDo> getDos(@Nonnull String orderId, String orderItem){
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0).andOrderIdEqualTo(orderId);
        if (StringUtils.hasText(orderItem)) {
            criteria.andOrderItemEqualTo(orderItem);
        }
        return opsDoMapper.selectByExample(example);
    }

    /**
     * 查询do
     * @param orderId 不可为空
     * @param orderItem 不可为空
     * @param num 不可为空
     * @return List<OpsDo>
     */
    @Override
    public List<OpsDo> getDos(@Nonnull String orderId, @Nonnull String orderItem, @Nonnull Integer num){
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0)
                .andOrderIdEqualTo(orderId)
                .andOrderItemEqualTo(orderItem)
                .andNumEqualTo(num);
        return opsDoMapper.selectByExample(example);
    }

    /**
     * 查询do
     * @param orderId 不可为空
     * @param orderItem 不可为空
     * @param num 不可为空
     * @param assNum 不可为空
     * @return List<OpsDo>
     */
    @Override
    public List<OpsDo> getDos(@Nonnull String orderId, @Nonnull String orderItem, @Nonnull Integer num, @Nonnull Integer assNum){
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDelflagEqualTo(0)
                .andOrderIdEqualTo(orderId)
                .andOrderItemEqualTo(orderItem)
                .andNumEqualTo(num)
                .andAssNumEqualTo(assNum);
        return opsDoMapper.selectByExample(example);
    }

    /**
     * 查询do
     * @param doId 不可为空
     * @return OpsDo
     * @throws OpsException 查询出多条Do抛异常
     */
    @Override
    public OpsDo getDo(@Nonnull String doId) throws OpsException {
        OpsDoExample example = new OpsDoExample();
        OpsDoExample.Criteria criteria = example.createCriteria();
        criteria.andDoIdEqualTo(doId);
        criteria.andDelflagEqualTo(0);
        List<OpsDo> list = opsDoMapper.selectByExample(example);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() == 0) {
            return null;
        } else {
            throw Exceptions.OpsException("查询出多条Do");
        }
    }

    /**
     * 查询doItem
     * @param doId 不可为空
     * @return OpsDoItem
     * @throws OpsException 查询出多条DoItem抛异常
     */
    @Override
    public OpsDoItem getDoItem(@Nonnull String doId) throws OpsException{
        OpsDoItemExample example = new OpsDoItemExample();
        example.createCriteria().andDoIdEqualTo(doId).andDelflagEqualTo(0);
        List<OpsDoItem> list = opsDoItemMapper.selectByExample(example);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() == 0) {
            return null;
        } else {
            throw Exceptions.OpsException("查询出多条DoItem");
        }
    }

    /**
     * 查询doItemInv
     * @param doId 不可为空
     * @return List<OpsDoItemInventory>
     */
    @Override
    public List<OpsDoItemInventory> getDoItemInv(@Nonnull String doId){
        OpsDoItemInventoryExample example = new OpsDoItemInventoryExample();
        example.createCriteria().andDoIdEqualTo(doId).andDelflagEqualTo(0);
        return opsDoItemInventoryMapper.selectByExample(example);
    }

    /**
     * 查询pco
     * @param orderId 不可为空
     * @param orderItem 可为空
     * @return List<OpsPco>
     */
    @Override
    public List<OpsPco> getPcos(@Nonnull String orderId, String orderItem){
        OpsPcoExample example = new OpsPcoExample();
        OpsPcoExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderId).andDelflagEqualTo(0);
        if (StringUtils.hasText(orderItem)) {
            criteria.andOrderItemEqualTo(orderItem);
        }
        return opsPcoMapper.selectByExample(example);
    }

    /**
     * 查询pco
     * @param orderId 不可为空
     * @param orderItem 可为空
     * @return List<OpsPco>
     */
    @Override
    public List<OpsPco> getPcos(@Nonnull String orderId, @Nonnull String orderItem, @Nonnull Integer num){
        OpsPcoExample example = new OpsPcoExample();
        OpsPcoExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderId)
                .andDelflagEqualTo(0)
                .andOrderItemEqualTo(orderItem)
                .andNumEqualTo(num);

        return opsPcoMapper.selectByExample(example);
    }

    /**
     * 查询pco
     * @param pcoId 不可为空
     * @return OpsPco
     * @throws OpsException 查询出多条Pco抛异常
     */
    @Override
    public OpsPco getPco(@Nonnull String pcoId) throws OpsException{
        OpsPcoExample example = new OpsPcoExample();
        OpsPcoExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(pcoId);
        criteria.andDelflagEqualTo(0);//删除标
        List<OpsPco> list = opsPcoMapper.selectByExample(example);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() == 0) {
            return null;
        } else {
            throw Exceptions.OpsException("查询出多条pco");
        }
    }

    /**
     * 查询pcoItem
     * @param pcoId 不可为空
     * @param pcoItem 不可为空
     * @return OpsPcoItem
     * @throws OpsException
     */
    @Override
    public List<OpsPcoItem> getPcoItem(@Nonnull String pcoId, Integer pcoItem){
        OpsPcoItemExample example = new OpsPcoItemExample();
        OpsPcoItemExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(pcoId);
        criteria.andDelflagEqualTo(0);//删除标
        if(Objects.nonNull(pcoItem)){
            criteria.andPcoItemEqualTo(pcoItem);
        }
        return  opsPcoItemMapper.selectByExample(example);
    }


    /**
     * 查询pcoItemInv
     * @param pcoId 不可为空
     * @param pcoItem 可为空
     * @return List<OpsPcoItemInventory>
     */
    @Override
    public List<OpsPcoItemInventory> getPcoItemInv(@Nonnull String pcoId, Integer pcoItem){
        OpsPcoItemInventoryExample example = new OpsPcoItemInventoryExample();
        OpsPcoItemInventoryExample.Criteria criteria = example.createCriteria();
        criteria.andPcoIdEqualTo(pcoId);
        criteria.andDelflagEqualTo(0);//删除标
        if(Objects.nonNull(pcoItem)){
            criteria.andPcoItemEqualTo(pcoItem);
        }
        return pcoItemInventoryMapper.selectByExample(example);
    }

    @Override
    public OpsPcoAndItemAndItemInventoryDto getPcoDto(@Nonnull String orderId, @Nonnull String orderItem, @Nonnull Integer num){
        OpsPcoAndItemAndItemInventoryDto pcoDto = new OpsPcoAndItemAndItemInventoryDto();
        List<OpsPco> pcos = getPcos(orderId, orderItem, num);
        if(CollectionUtils.isEmpty(pcos)){
            return null;
        }
        OpsPco opsPco = pcos.get(0);
        List<OpsPcoItem> pcoItems = getPcoItem(opsPco.getPcoId(), null);
        if(CollectionUtils.isEmpty(pcoItems)){
            return null;
        }
        List<OpsPcoItemInventory> pcoItemInvs = getPcoItemInv(opsPco.getPcoId(), null);
        pcoDto.setOpsPco(opsPco);
        pcoDto.setItemList(pcoItems);
        pcoDto.setItemInventoryList(pcoItemInvs);
        return pcoDto;
    }


    @Override
    public Rcvdetail findRcvDetail(String orderId, Integer orderItem) throws OpsException {
        RcvdetailExample example = new RcvdetailExample();
        example.createCriteria().andDeleteStatusEqualTo((short) 0)
                .andRorderNoEqualTo(orderId).andRorderItemEqualTo(orderItem);
        List<Rcvdetail> rcvDetailList = rcvdetailMapper.selectByExample(example);
        return getOne(rcvDetailList);
    }

    @Override
    public List<Rcvdetail> findRcvDetail(String orderId) throws OpsException {
        RcvdetailExample example = new RcvdetailExample();
        example.createCriteria().andDeleteStatusEqualTo((short) 0)
                .andRorderNoEqualTo(orderId);
        return rcvdetailMapper.selectByExample(example);
    }

    @Override
    public Rcvdetail findRcvDetailByFno(String orderFno) throws OpsException {
        RcvdetailExample example = new RcvdetailExample();
        example.createCriteria().andDeleteStatusEqualTo((short) 0).andRorderFnoEqualTo(orderFno);
        List<Rcvdetail> rcvDetailList = rcvdetailMapper.selectByExample(example);
        return getOne(rcvDetailList);
    }

    private Rcvdetail getOne(List<Rcvdetail> list) throws OpsException {
        if (list.size() == 1) {
            return list.get(0);
        } else {
            throw Exceptions.OpsException("查询出" + list.size() + "条订单");
        }
    }
}
