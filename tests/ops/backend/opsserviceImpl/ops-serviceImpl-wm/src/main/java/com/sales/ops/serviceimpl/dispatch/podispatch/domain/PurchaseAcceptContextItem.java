package com.sales.ops.serviceimpl.dispatch.podispatch.domain;

import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.OpsDoDto;
import com.sales.ops.dto.inventory.OpsPcoItemDto;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.utils.PoNoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author C12961
 * @date 2023/3/3 14:48
 */

public class PurchaseAcceptContextItem {

    //请购单
    private OpsRequestpurchase request;
    //采购单
    private OpsPurchaseorder purchase;

    //旧的生产库存
    private OpsInventoryMove oldProduct;
    //新的生产库存
    private OpsInventoryMove product;
    //生产库存被预占的数量
    private Integer prepareQuantity;


    private OpsDoDto jyck;
    private List<OpsPcoItemDto> pco = new ArrayList<>();

    private OpsDoDto cgdbck;


    private Boolean hasRelation;

    //创建人（接口名）
    private UserDto creator;


    public static PurchaseAcceptContextItem create(OpsRequestpurchase request, OpsPurchaseorder purchase, UserDto creator) {
        return new PurchaseAcceptContextItem(request, purchase, creator);
    }

    public PurchaseAcceptContextItem(OpsRequestpurchase request, OpsPurchaseorder purchase, UserDto creator) {
        this.request = request;
        this.purchase = purchase;
        this.prepareQuantity = 0;
        this.creator = creator;
    }

    // 自定义getter\setter
    //是否为型号拆分


    public OpsDoDto getJYCK() {
        return jyck;
    }

    public void setJYCK(OpsDo opsDo, OpsDoItem doItem, List<OpsDoItemInventory> doItemInventoryList) {
        this.jyck = new OpsDoDto(opsDo, doItem, doItemInventoryList);
    }

    public OpsDoDto getCGDBCK() {
        return cgdbck;
    }

    public void setCGDBCK(OpsDo opsDo, OpsDoItem doItem, List<OpsDoItemInventory> doItemInventoryList) {
        this.cgdbck = new OpsDoDto(opsDo, doItem, doItemInventoryList);
    }

    public List<OpsPcoItemDto> getPco() {
        return pco;
    }

    public void addPco(OpsPco opsPco, OpsPcoItem pcoItem, List<OpsPcoItemInventory> pcoItemInventoryList) {
        this.pco.add(new OpsPcoItemDto(opsPco, pcoItem, pcoItemInventoryList));
    }

    public Boolean hasRelation() {
        return hasRelation;
    }

    public void setHasRelation(Boolean hasRelation) {
        this.hasRelation = hasRelation;
    }

    public String getWmTag() {
        return this.request.getWmtag();
    }


    public OpsInventoryMove getOldProduct() {
        return oldProduct;
    }

    public void setOldProduct(OpsInventoryMove oldProduct) {
        this.oldProduct = oldProduct;
    }

    public String getOrderNo() {
        return this.request.getOrderno();
    }

    public Integer getItemNo() {
        return this.request.getItemno();
    }

    public Integer getSplitNo() {
        return PoNoUtil.getSplitNo(request);
    }

    public String getFullRePoNo() {
        return PoNoUtil.getFullRePoNo(request);
    }

    public String getFullPoNo() {
        return PoNoUtil.getFullPoNo(purchase);
    }

    public Integer getRequestQty() {
        return this.request.getQuantity();
    }
    // getter\setter

    public OpsRequestpurchase getRequest() {
        return request;
    }

    public void setRequest(OpsRequestpurchase request) {
        this.request = request;
    }

    public OpsPurchaseorder getPurchase() {
        return purchase;
    }

    public void setPurchase(OpsPurchaseorder purchase) {
        this.purchase = purchase;
    }

    public OpsInventoryMove getProduct() {
        return product;
    }

    public void setProduct(OpsInventoryMove product) {
        this.product = product;
    }

    public Integer getPrepareQuantity() {
        return prepareQuantity;
    }

    public void setPrepareQuantity(Integer prepareQuantity) {
        this.prepareQuantity = prepareQuantity;
    }

    public UserDto getCreator() {
        return creator;
    }

    public void setCreator(UserDto creator) {
        this.creator = creator;
    }
}
