package com.smc.ops.delivery.service.branch.impl;

import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inqb.InqbDeptProvinceEnum;
import com.smc.ops.delivery.mapper.branch.BranchDao;
import com.smc.ops.delivery.model.branch.BranchInvTransDo;
import com.smc.ops.delivery.model.branch.BranchSumDo;
import com.smc.ops.delivery.model.branch.ExpdetailInfoDo;
import com.smc.ops.delivery.model.branch.ExpdetailPropertyDo;
import com.smc.ops.delivery.service.branch.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/12/29 11:02
 */

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchDao branchDao;

    /**
     * bugid:16334 c14717   广州制造统合
     */
    @Override
    public void handleBranch(){
        //1.查询
        List<ExpdetailInfoDo> expdetailInfoDos = queryExpdetailInfo();
        //2.先处理cn0-g
        for(ExpdetailInfoDo expInfo : expdetailInfoDos){
            if(InqbDeptProvinceEnum.GZProvince.getType().equals(expInfo.getTradeCompanyid())){
                handleBranchSub(expInfo);
            }
        }
        //3.后处理非cn0-g
        for(ExpdetailInfoDo expInfo : expdetailInfoDos){
            if(!InqbDeptProvinceEnum.GZProvince.getType().equals(expInfo.getTradeCompanyid())){
                handleBranchSub(expInfo);
            }
        }
    }


    /**
     * 处理明细
     * @param obj
     */
    public void handleBranchSub(ExpdetailInfoDo obj){
        //查询pco
        Integer integer = branchDao.countPco(obj.getOrderNo(), obj.getItemNo().toString());
        if(Objects.nonNull(integer) && integer> 0){
            // 处理pco
            handlePco(obj);
        }else {
            // 处理do
            handleDo(obj);
        }
    }

    public void handleDo(ExpdetailInfoDo expdetail){
        //bugid:18574 C14717 20250820 关联关系存在在途 不处理当前一条
        Integer ztInvSize = branchDao.countDoItemInvType(expdetail.getDeliveryNo());
        if(ztInvSize != 0){
            return;
        }
        List<BranchSumDo> branchSumDos = branchDao.queryBranchSumDo(expdetail.getModelNo());
        int expdetailQty = expdetail.getQuantity();
        int flag = 0;
        for(BranchSumDo sumDo : branchSumDos){
            int qty = 0;
            if( sumDo.getOverQty() == 0 || sumDo.getOverQty() < 0){
                continue;
            }
            if(expdetail.getQuantity() == 0){
                break;
            }
            flag = 1;
            if(expdetail.getQuantity() <= sumDo.getOverQty()){
                qty = expdetail.getQuantity();
                expdetail.setQuantity(0);


            }else {
                qty = sumDo.getOverQty();
                expdetail.setQuantity(expdetail.getQuantity() - sumDo.getOverQty());
            }
            //查询invID
            List<Long> collect = branchDao.queryDoItemInvIds(expdetail.getDeliveryNo());
            String fromInvId = collect.stream().distinct().map(String::valueOf).collect(Collectors.joining(","));
            ExpdetailPropertyDo ep = new ExpdetailPropertyDo(expdetail.getId(),expdetail.getModelNo(),sumDo.getCompanyId(),expdetailQty,qty);
            BranchInvTransDo bt = new BranchInvTransDo(sumDo.getCompanyId(), expdetail.getModelNo(),expdetail.getWarehouseCode(),expdetail.getDeliveryNo(),
                    qty,fromInvId,expdetail.getOrderNo(),expdetail.getItemNo(),expdetail.getShipDate(),"JYCK");
            insertBranchInvTrans(bt);
            insertExpdetailProperty(ep);
        }
        //更新expdetail
        updateExp(flag, expdetail.getId());
    }

    public void handlePco(ExpdetailInfoDo expdetail){
        //1.查询do
        Integer num = branchDao.queryDoNum(expdetail.getDeliveryNo());
        //2.查询doItem
        Integer doItemQty = branchDao.queryDoItemQty(expdetail.getDeliveryNo());
        //3.查询pco
        String pcoId = branchDao.queryPcoId(expdetail.getOrderNo(), expdetail.getItemNo().toString(), num);

        //bugid:18574 C14717 20250820 关联关系存在在途 不处理当前一条
        Integer ztInvSize = branchDao.countPcoItemInvType(pcoId);
        if(ztInvSize != 0){
            return;
        }
        //4.查询pcoItem
        List<OpsPcoItem> pcoItems = branchDao.queryPcoItems(pcoId);
        //5.计算比例
        HashMap<String, Integer> pcoItemMap = new HashMap<String, Integer>();
        for (OpsPcoItem pcoItem : pcoItems) {
            int pcoItemQty = pcoItem.getSubQty();
            int doQty = doItemQty;
            int multipleQty = pcoItemQty / doQty;
            pcoItemMap.put(pcoItem.getPcoId() + "-" + pcoItem.getPcoItem(), multipleQty * expdetail.getQuantity());
        }

        //6.抽取过账表
        HashMap<String, Integer> map = new HashMap<String,Integer>();
        int flag = 0;
        for(OpsPcoItem pcoItem : pcoItems){
            List<BranchSumDo> branchSumDos = branchDao.queryBranchSumDo(pcoItem.getSubModelno());
            for(BranchSumDo sumDo : branchSumDos){
                int qty = 0;
                if( sumDo.getOverQty() == 0 || sumDo.getOverQty() < 0){
                    continue;
                }
                int pcoSuboutQty = pcoItemMap.get(pcoItem.getPcoId() + "-" + pcoItem.getPcoItem());
                if (pcoSuboutQty == 0) {
                    continue;
                }
                //全部出的子公司
                if(pcoSuboutQty <= sumDo.getOverQty()){
                    pcoItemMap.put(pcoItem.getPcoId() + "-" + pcoItem.getPcoItem(), 0);
                    qty = pcoSuboutQty;
                    //计算所有子型号是否出一个子公司
                    if(map.containsKey(sumDo.getCompanyId())){
                        map.put(sumDo.getCompanyId(),map.get(sumDo.getCompanyId()) + 1);
                    }else {
                        map.put(sumDo.getCompanyId(),1);
                    }
                }else {//一半子公司
                    pcoItemMap.put(pcoItem.getPcoId() + "-" + pcoItem.getPcoItem(), pcoSuboutQty - sumDo.getOverQty());
                    qty = sumDo.getOverQty();
                }

                //查询pcoItemInv
                List<Long> collect =  branchDao.queryPcoItemInvIds(pcoItem.getPcoId(),pcoItem.getPcoItem());
                //写入两表
                String fromInvId = collect.stream().distinct().map(String::valueOf).collect(Collectors.joining(","));
                ExpdetailPropertyDo ep = new ExpdetailPropertyDo(expdetail.getId(),pcoItem.getSubModelno(),sumDo.getCompanyId(),pcoItem.getSubQty(),qty);
                BranchInvTransDo bt = new BranchInvTransDo(sumDo.getCompanyId(), pcoItem.getSubModelno(),expdetail.getWarehouseCode(),expdetail.getDeliveryNo(),
                        qty,fromInvId,expdetail.getOrderNo(),expdetail.getItemNo(),expdetail.getShipDate(),"JYCK");
                insertBranchInvTrans(bt);
                insertExpdetailProperty(ep);
            }
        }
        //7.计算子型号是否全部出分库
        if(!map.isEmpty()){
            for(Map.Entry<String,Integer> entry : map.entrySet()){
                if (entry.getValue() == pcoItems.size()) {
                    flag = 1;
                    break;
                }
            }
        }
        //8.更新expdetail
        updateExp(flag, expdetail.getId());
    }

    /**
     * 查询expdetail
     * @return
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<ExpdetailInfoDo> queryExpdetailInfo(){
        return branchDao.queryExpdetailInfoDo();
    }

    /**
     * 查询过账记录表
     * @param modelNo
     * @return
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<BranchSumDo> queryBranchSumDo(String modelNo){
        return branchDao.queryBranchSumDo(modelNo);
    }

    /**
     * 写入过账表
     * @param obj
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBranchInvTrans(BranchInvTransDo obj){
        branchDao.insertBranchInvTrans(obj);
    }

    /**
     * 写入expdetail子表
     * @param obj
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertExpdetailProperty(ExpdetailPropertyDo obj){
        branchDao.insertExpdetailProperty(obj);
    }

    /**
     * 更新expdetail 状态
     * @param branchFlag
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateExp(Integer branchFlag , Long id){
        branchDao.updateExpdetail(id,branchFlag);
    }
}
