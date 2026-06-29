package com.sales.ops.dto.inventory;

import com.alibaba.fastjson.JSON;
import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.ProductBomDetail;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author C02483
 * @version 1.0
 * @description: 拆分明细数据，拆分过程中用到
 * @date 2021/11/29 18:54
 */
public class AssBomDetail {

    private ProductBomDetail productBomDetail;
    private Integer assitem = 0;
    private Integer assQty = 0;//订单数量 * bomdetail数量 Integer assQty = productBomDetail.getQuantity() * (inputDto.getQuantity() - inputDto.getAllotQuantity());
    private Integer assAllotQty = 0;//库存数量
    private Integer extitem=0;
    private Map<OpsInventory, Integer> mapqty=new HashMap<>();
    private Map<OpsInventoryMove, Integer> mapMoveqty=new HashMap<>();

    // 深拷贝
    public AssBomDetail deepCopy(){
        AssBomDetail copy = new AssBomDetail();
        if(this.productBomDetail != null){
            String jsonString = JSON.toJSONString(this.productBomDetail);
            copy.productBomDetail =  JSON.parseObject(jsonString, ProductBomDetail.class);;
        }
        if(this.assitem != null){
            copy.assitem = assitem;
        }
        if(this.assQty != null){
            copy.assQty = assQty;
        }
        if(this.assAllotQty != null){
            copy.assAllotQty = assAllotQty;
        }
        if(this.extitem != null){
            copy.extitem = extitem;
        }
        if (this.mapqty != null) {
            // 深拷贝 key
            copy.mapqty = new HashMap<>();
            for (Map.Entry<OpsInventory, Integer> entry : this.mapqty.entrySet()) {
                String jsonString = JSON.toJSONString(entry.getKey());
                OpsInventory keyCopy  =  JSON.parseObject(jsonString, OpsInventory.class);
                copy.mapqty.put(keyCopy, entry.getValue());
            }
        }
        if (this.mapMoveqty != null) {
            copy.mapMoveqty = new HashMap<>();
            for (Map.Entry<OpsInventoryMove, Integer> entry : this.mapMoveqty.entrySet()) {
                String jsonString = JSON.toJSONString(entry.getKey());
                OpsInventoryMove keyCopy  =  JSON.parseObject(jsonString, OpsInventoryMove.class);
                copy.mapMoveqty.put(keyCopy, entry.getValue());
            }
        }
        return copy;
    }



    public ProductBomDetail getProductBomDetail() {
        return productBomDetail;
    }

    public void setProductBomDetail(ProductBomDetail productBomDetail) {
        this.productBomDetail = productBomDetail;
    }


    public Integer getAssitem() {
        return assitem;
    }

    public void setAssitem(Integer assitem) {
        this.assitem = assitem;
    }

    public Integer getExtitem() {
        return extitem;
    }

    public void addExtitem() {
        this.extitem = extitem+1;
    }

    public Integer getAssQty() {
        return assQty;
    }

    public void setAssQty(Integer assQty) {
        this.assQty = assQty;
    }

    public Integer getAssAllotQty() {
        return assAllotQty;
    }

    public void setAssAllotQty(Integer assAllotQty) {
        this.assAllotQty = assAllotQty;
    }

    public Map<OpsInventory, Integer> getMapqty() {
        return mapqty;
    }

    public void setMapqty(Map<OpsInventory, Integer> mapqty) {
        this.mapqty = mapqty;
    }

    public Boolean IsEnough() {
        return Objects.equals(assAllotQty, assQty);
    }

    public Map<OpsInventoryMove, Integer> getMapMoveqty() {
        return mapMoveqty;
    }

    public void setMapMoveqty(Map<OpsInventoryMove, Integer> mapMoveqty) {
        this.mapMoveqty = mapMoveqty;
    }
}
