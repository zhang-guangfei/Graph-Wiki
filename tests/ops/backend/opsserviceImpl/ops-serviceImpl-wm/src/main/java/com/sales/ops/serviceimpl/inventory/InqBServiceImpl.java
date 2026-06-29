package com.sales.ops.serviceimpl.inventory;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.common.until.DateUtil;
import com.sales.ops.db.dao.OpsInventoryPropertyMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inqb.OpsInqbOrderAllotRequest;
import com.sales.ops.dto.inqb.OpsInqbOrderAllotResult;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.enums.*;
import com.sales.ops.service.inventory.AllotInvenToryService;
import com.sales.ops.service.inventory.InqBService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/6/13 13:10
 */
@Service
public class InqBServiceImpl implements InqBService {

    @Autowired
    private AllotInvenToryService allotInvenToryService;
    @Autowired
    private OpsInventoryPropertyMapper opsInventoryPropertyMapper;

    /**
     * bugid:14454 inq-b 订单分配接口
     * @param param
     * @return
     * @throws OpsException
     */
    @Override
    public List<OpsInqbOrderAllotResult> inqBGetCKRule(OpsInqbOrderAllotRequest param) throws OpsException{
        List<OpsInqbOrderAllotResult> allotRusetDtolist = new ArrayList<>();
        InventoryCkByOrderInputDto inputDto = getInputDto(param);
        InventoryCkByOrderOutDto outDto = allotInvenToryService.getOpsInventoryListByCk(inputDto);
        getResultList(outDto, inputDto,allotRusetDtolist);
        return allotRusetDtolist;
    }

    /**
     * 初始化库存分配规则实体
     * @param obj
     * @return
     */

    public InventoryCkByOrderInputDto getInputDto(OpsInqbOrderAllotRequest obj){
        InventoryCkByOrderInputDto inputDto = new InventoryCkByOrderInputDto();
        //需要传入的值
        inputDto.setModelno(obj.getModelNo());
        inputDto.setUserNo(obj.getEndUser());
        inputDto.setCustomer(obj.getEndUser());
        inputDto.setDeptno(obj.getDeptNo());
        inputDto.setQuantity(obj.getQuantity());

        inputDto.setHopeDate(DateUtil.getNow());// 客户期望交货期 今天
        inputDto.setOrderType(OrderTypeEnum.KEHU);//客户订单
        inputDto.setTag(CKTagEnum.NOT.getCode());//无特殊标识
        inputDto.setAllotQuantity(0);
        inputDto.setAllotStatus(false);
        inputDto.setPropertyType(InventoryPropertyTypeEnum.NOMAL.getType());
        inputDto.setCKType(CKTYPEEnum.ITEM_GETHER_SIGNLE_HOUSE);//随到随发
        return inputDto;
    }


    public void getResultList(InventoryCkByOrderOutDto outDto, InventoryCkByOrderInputDto inputDto,
                                                 List<OpsInqbOrderAllotResult> allotRusetDtolist) throws OpsException {
        // 特殊订单不采购判断
        if (!inputDto.isAllotStatus()) {// 不满足出库判断优先整型号采购
            if (DoSourceEnum.ASSModelNo.equals(outDto.getDoSourceEnum())) {// 拆分型号不拆数量
                if (outDto.getAssBom().getProductBom().getPriorityComplete()) {// 优先能型号采购
                    outDto.setAssBom(null);
                    outDto.setDoSourceEnum(DoSourceEnum.CG);
                    inputDto.setAllotQuantity(0);
                } else {
                    for (AssBomDetail assBomDetail : outDto.getAssBom().getDetailList()) {// 子型号不拆数量
                        if (!assBomDetail.IsEnough()) {
                            assBomDetail.setAssAllotQty(0);
                            assBomDetail.setMapMoveqty(null);
                            assBomDetail.setMapqty(null);
                        }
                    }
                }
            }
        }
        InventoryDispatchDto dispatchDto = new InventoryDispatchDto(outDto);
        //1.生成出库存结果
        getInvResultDto(outDto,allotRusetDtolist,inputDto,dispatchDto);
        //2.生成采购结果
        getCGResult(allotRusetDtolist,inputDto,dispatchDto);

    }

    public void getCGResult( List<OpsInqbOrderAllotResult> allotRusetDtolist,
                               InventoryCkByOrderInputDto inputDto,InventoryDispatchDto dispatchDto){
        if (DoSourceEnum.ASSModelNo.equals(dispatchDto.getDoSourceEnum())) {
            for (AssBomDetail assBomDetail : dispatchDto.getAssBom().getDetailList()) {
                if (!assBomDetail.IsEnough()) {
                    inputDto.addAssItem();
                    Integer qty = assBomDetail.getAssQty() - assBomDetail.getAssAllotQty();
                    String subModelno = assBomDetail.getProductBomDetail().getModelno();
                    OpsInqbOrderAllotResult cgResult = initCGResult(inputDto.getAssItem(), subModelno, qty,
                            assBomDetail.getProductBomDetail().getBomid(), dispatchDto.getDoSourceEnum());
                    allotRusetDtolist.add(cgResult);
                }
            }
        } else if (inputDto.getQuantity() > inputDto.getAllotQuantity()) {
            if (inputDto.getAllotQuantity() == 0) {
                dispatchDto.setDoSourceEnum(DoSourceEnum.CG);
            }
            inputDto.addQtyItem();
            Integer qty = inputDto.getQuantity() - inputDto.getAllotQuantity();
            OpsInqbOrderAllotResult cgResult = initCGResult(inputDto.getQtyItem(),inputDto.getModelno(),qty,null,dispatchDto.getDoSourceEnum());
            allotRusetDtolist.add(cgResult);
        }
    }

    /**
     * 生成出在库 allotResult
     * @param outDto
     * @param list
     * @param inputDto
     * @param dispatchDto
     */
    public void getInvResultDto(InventoryCkByOrderOutDto outDto, List<OpsInqbOrderAllotResult> list,
                                  InventoryCkByOrderInputDto inputDto,InventoryDispatchDto dispatchDto) {
        //拆分型号
        if (!outDto.getInventoryMapSorted().isEmpty()) {
            if (DoSourceEnum.ASSModelNo.equals(outDto.getDoSourceEnum())) {
                AssBom assBom = dispatchDto.getAssBom();
                if(Objects.nonNull(assBom)){
                    int item = 0;
                    if (org.apache.commons.collections.CollectionUtils.isNotEmpty(assBom.getDetailList())) {
                        for (AssBomDetail bomDetail : assBom.getDetailList()) {
                            if (bomDetail.getMapMoveqty() != null) {
                                for (Map.Entry<OpsInventoryMove, Integer> entry : bomDetail.getMapMoveqty().entrySet()) {
                                    inputDto.addAssItem();
                                    int useqty = entry.getValue();
                                    OpsInventoryMove opsInventoryMove = entry.getKey();
                                    OpsInqbOrderAllotResult result = initInvResult(null, opsInventoryMove, useqty,
                                            inputDto.getAssItem(), assBom.getProductBom().getBomid(),dispatchDto.getDoSourceEnum());
                                    list.add(result);
                                }
                                for (Map.Entry<OpsInventory, Integer> entry : bomDetail.getMapqty().entrySet()) {
                                    inputDto.addAssItem();
                                    int useqty = entry.getValue();
                                    OpsInventory opsInventory = entry.getKey();
                                    OpsInqbOrderAllotResult result = initInvResult(opsInventory,null,useqty,
                                            inputDto.getAssItem(),assBom.getProductBom().getBomid(),dispatchDto.getDoSourceEnum());
                                    list.add(result);
                                }
                            }
                        }
                    }
                }
            }

            // 非拆分型号
            if (!dispatchDto.getInvQtyMap().isEmpty()) {
                for (String warehouseCode : dispatchDto.getInvQtyMap().keySet()) {
                    List<InventoryDispatchDto.InvQty> invQtyList = dispatchDto.getInvQtyMap().get(warehouseCode);
                    if (CollectionUtils.isNotEmpty(invQtyList)) {
                        for (InventoryDispatchDto.InvQty inqty : invQtyList) {
                            inputDto.addQtyItem();
                            int useqty = inqty.getUseqty();
                            OpsInventory opsInventory = inqty.getInventory();
                            OpsInqbOrderAllotResult result = initInvResult(opsInventory,null,useqty,
                                    inputDto.getQtyItem(),null,dispatchDto.getDoSourceEnum());
                            list.add(result);
                        }
                    }
                }
            }
            if (!dispatchDto.getInvMoveQtyMap().isEmpty()) {
                for (String warehouseCode : dispatchDto.getInvMoveQtyMap().keySet()) {
                    List<InventoryDispatchDto.InvMoveQty> invMoveQtyList = dispatchDto.getInvMoveQtyMap()
                            .get(warehouseCode);
                    if (CollectionUtils.isNotEmpty(invMoveQtyList)) {
                        for (InventoryDispatchDto.InvMoveQty inMoveqty : invMoveQtyList) {
                            inputDto.addQtyItem();
                            int useqty = inMoveqty.getUseqty();
                            OpsInventoryMove opsInventoryMove = inMoveqty.getInventory();
                            OpsInqbOrderAllotResult result = initInvResult(null,opsInventoryMove,useqty,
                                    inputDto.getQtyItem(),null,dispatchDto.getDoSourceEnum());
                            list.add(result);
                        }
                    }
                }
            }
        }
    }

    public OpsInqbOrderAllotResult initCGResult( Integer splitNo, String modelNo, Integer qty,Long bomId,DoSourceEnum doSourceEnum){
        OpsInqbOrderAllotResult obj = new OpsInqbOrderAllotResult();
        obj.setSeqno(splitNo);
        obj.setBomid(bomId);
        obj.setProdFlag(doSourceEnum.splitType().getSplitType());
        obj.setStockType("CG");
        obj.setModelNo(modelNo);
        obj.setQuantity(qty);
        return obj;
    };

    public OpsInqbOrderAllotResult initInvResult(OpsInventory opsInventory , OpsInventoryMove opsInvMove, Integer useqty, Integer splitNo, Long bomId,DoSourceEnum doSourceEnum){
        OpsInqbOrderAllotResult obj = new OpsInqbOrderAllotResult();
        if(Objects.nonNull(opsInventory)){
            obj.setSeqno(splitNo);
            obj.setBomid(bomId);
            obj.setProdFlag(doSourceEnum.splitType().getSplitType());
            obj.setStockType(opsInventory.getInventoryStatus());
            obj.setModelNo(opsInventory.getModelno());
            obj.setQuantity(useqty);
        }
        if(Objects.nonNull(opsInvMove)){
            obj.setSeqno(splitNo);
            obj.setBomid(bomId);
            obj.setProdFlag(doSourceEnum.splitType().getSplitType());
            obj.setStockType(opsInvMove.getInventoryStatus());
            obj.setModelNo(opsInvMove.getModelno());
            obj.setQuantity(useqty);
        }
        return obj;
    }

}
