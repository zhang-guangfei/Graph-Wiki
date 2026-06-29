package com.smc.ops.delivery.service.branch.impl;

import com.sales.ops.db.entity.OpsExceptionHandle;
import com.sales.ops.dto.util.DateUtil;
import com.sales.ops.enums.DoConfirmErrorCodeEnum;
import com.smc.ops.delivery.mapper.branch.BranchDao;
import com.smc.ops.delivery.mapper.branch.BranchZHDao;
import com.smc.ops.delivery.model.branch.*;
import com.smc.ops.delivery.service.branch.BranchService;
import com.smc.ops.delivery.service.branch.BranchZHService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/1/3 14:54
 */
@Service
public class BranchZHServiceImpl implements BranchZHService {

    @Autowired
    private BranchZHDao branchZHDao;

    @Autowired
    private BranchDao branchDao;

    @Autowired
    private BranchService branchService;


    /**
     * 16354
     */
    @Override
    public void handleBranch(){
        //1.查询组换视图 where branch_flag is null and ApplyType =1 and OptCode =6 and create_time > '2025-01-01'
        List<StockAssemblyDetailViewDo> stockAssemblyDetailViewDos = queryStockAssemblyDetailViewDo();

        //2.计算优先出自动化资产标识
        boolean outCNFlag = true;
        for(StockAssemblyDetailViewDo viewDo : stockAssemblyDetailViewDos){
            if(viewDo.getIsTransOut().equals(1) && viewDo.getAssembleType().equals(1)){
                Integer subQty = branchDao.queryBranchSumQty(viewDo.getModelNo());
                if(Objects.isNull(subQty)){
                    subQty = 0;
                }
                //当前库存
                int totalQty = getInvByModel(viewDo.getModelNo());
                //加上扣减库存
                totalQty = totalQty - viewDo.getQuantity();
                //自动化资产
                totalQty = totalQty - subQty;
                if ((totalQty + viewDo.getQuantity()) < 0) {
                   //自动化资产不满足
                    outCNFlag = false;
                    break;
                }
            }
        }

        for(StockAssemblyDetailViewDo viewDo : stockAssemblyDetailViewDos){
            //3.记账
            int subCompany = handleBranchSub(viewDo,outCNFlag);
            //4.多公司记录异常
            if(subCompany > 0){
                Boolean moreCompany = queryDelivyerCheckMoreCompany(viewDo.getApplyId());
                if(moreCompany){
                    //记录异常
                    saveExceptionHandle(viewDo.getApplyId());
                }
            }
        }
    }
    //记录异常
    @Transactional(rollbackFor = Exception.class)
    public void saveExceptionHandle(Long applyId){
        OpsExceptionHandle opsExceptionHandle = new OpsExceptionHandle();
        opsExceptionHandle.setBusinessType("Do");
        opsExceptionHandle.setErrType(DoConfirmErrorCodeEnum.ZHCK_MORE_COMPANY.getCode());//组换出库
        opsExceptionHandle.setApiName("组换记账job"); //接口名字
        opsExceptionHandle.setStatus(9);//状态（0：待处理，1：已处理，9：无需处理）
        opsExceptionHandle.setOutputMsg("stock_assembly_detail.applyId:组换出库包含多个子公司");//返回报文
        opsExceptionHandle.setParameter1(applyId.toString());
        opsExceptionHandle.setParameter3("");
        opsExceptionHandle.setCreateTime(DateUtil.getNow());
        opsExceptionHandle.setCreateUser("saveStockAssembly");
        branchDao.insertOpsExceptionHandle(opsExceptionHandle);
    }

    //3.记账
    public int handleBranchSub(StockAssemblyDetailViewDo viewDo,boolean outCNFlag){
        int branchFlag = 0;
        if(viewDo.getIsTransOut().equals(1) && viewDo.getAssembleType().equals(1)){//出库 且同仓组换
            if(!outCNFlag){
                List<BranchSumDo> branchSumDos = branchDao.queryBranchSumDo(viewDo.getModelNo());
                viewDo.setQuantity(viewDo.getQuantity() * (-1));
                int expdetailQty = viewDo.getQuantity();
                for(BranchSumDo sumDo : branchSumDos){
                    int qty = 0;
                    if( sumDo.getOverQty() == 0 || sumDo.getOverQty() < 0){
                        continue;
                    }
                    if(viewDo.getQuantity() == 0){
                        break;
                    }
                    branchFlag = 1;
                    if(viewDo.getQuantity() <= sumDo.getOverQty()){
                        qty = viewDo.getQuantity();
                        viewDo.setQuantity(0);
                    }else {
                        qty = sumDo.getOverQty();
                        viewDo.setQuantity(viewDo.getQuantity() - sumDo.getOverQty());
                    }
                    DoDoItemDo doInfo = branchDao.queryDoDoItemInfo(viewDo.getApplyNo(), viewDo.getModelNo());
                    //查询invID
                    List<Long> collect = branchDao.queryDoItemInvIds(doInfo.getDoId());
                    String fromInvId = collect.stream().distinct().map(String::valueOf).collect(Collectors.joining(","));
                    StockAssemblyDetailPropertyDo ep = new StockAssemblyDetailPropertyDo(viewDo.getDetailId(),viewDo.getModelNo(),sumDo.getCompanyId(),expdetailQty,qty);

                    Date shipDate = branchZHDao.queryStockAssemblyOfTransTime(viewDo.getApplyId());
                    if(Objects.isNull(shipDate)){
                        shipDate = DateUtil.getNow();
                    }
                    BranchInvTransDo bt = new BranchInvTransDo(sumDo.getCompanyId(), viewDo.getModelNo(),viewDo.getWarehouseCode(),doInfo.getDoId(),
                            qty,fromInvId,doInfo.getOrderId(),doInfo.getOrderItem(), shipDate,"ZHCK");
                    branchService.insertBranchInvTrans(bt);
                    insertProperty(ep);
                }
            }
        }
        //更新detail表
        updateStockAssemblyDetail(viewDo.getDetailId(),branchFlag);
        return branchFlag;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStockAssemblyDetail(Long detailId , Integer branchFlag){
        branchZHDao.updateStockAssemblyDetail(detailId,branchFlag);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public int getInvByModel(String modelNo) {
        InventoryVO   inventoryVOS = branchDao.getInvByModel(modelNo);
        int quantity = 0;
        if (ObjectUtils.isNotEmpty(inventoryVOS) ) {
            quantity = Optional.of(inventoryVOS .getQuantity()).orElse(0) -Optional.of(inventoryVOS .getPrepareQuantity()).orElse(0) ;
        }
        return quantity;
    }

    /**
     * 查询StockAssemblyDetailViewDo
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<StockAssemblyDetailViewDo> queryStockAssemblyDetailViewDo(){
        return branchZHDao.queryStockAssemblyDetailViewDo();
    }

    //queryDelivyerCheckMoreCompany
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Boolean queryDelivyerCheckMoreCompany(Long applyId){
        //IsTransOut = 1
        List<DelivyerCheckMoreCompanyDto> delivyerCheckMoreCompanyDtos = branchZHDao.queryDelivyerCheckMoreCompany(applyId);
        for (DelivyerCheckMoreCompanyDto dto : delivyerCheckMoreCompanyDtos){
            if(Objects.isNull(dto.getDetailBranchFlag())){
                return false;// 存在没处理完的数据
            }
        }

        // 删除delflag =1
        if(CollectionUtils.isNotEmpty(delivyerCheckMoreCompanyDtos)){
            delivyerCheckMoreCompanyDtos.removeIf(dto -> Objects.nonNull(dto.getPropertyDelFlag()) && dto.getPropertyDelFlag().equals(1));
        }
        if(CollectionUtils.isNotEmpty(delivyerCheckMoreCompanyDtos)){
            DelivyerCheckMoreCompanyDto firstDto = delivyerCheckMoreCompanyDtos.get(0);
            for (DelivyerCheckMoreCompanyDto dto : delivyerCheckMoreCompanyDtos){
                if(!firstDto.getDetailBranchFlag().equals(dto.getDetailBranchFlag())){
                    return true;// 存在多公司 有0 有1
                }
            }
            if (firstDto.getDetailBranchFlag().equals(1)){
                for (DelivyerCheckMoreCompanyDto dto : delivyerCheckMoreCompanyDtos){
                    if(!firstDto.getPropertyCompanyId().equals(dto.getPropertyCompanyId())){
                        return true;// 存在多公司 companyId不同
                    }
                    if(dto.getDetailQty() + dto.getPropertyQty() !=0 ){
                        return true;// 存在多公司 qty不同
                    }
                }
            }
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertProperty(StockAssemblyDetailPropertyDo obj){
        branchZHDao.insertProperty(obj);
    }

}
