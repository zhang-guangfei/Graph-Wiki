package com.sales.ops.dto.inventory;

import com.alibaba.fastjson.JSON;
import com.sales.ops.db.entity.ProductBom;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 库存分配过程中拆分数据用到
 * @date 2021/11/29 18:54
 */
public class AssBom {
    private ProductBom productBom;
    private Integer qty;//订单要出的成品数量
    private Boolean IsEnough;
    private List<AssBomDetail> detailList;

    // 深拷贝
    public AssBom deepCopy(){
        AssBom copy = new AssBom();
        if(this.productBom != null){
            String jsonString = JSON.toJSONString(this.productBom);
            copy.productBom =  JSON.parseObject(jsonString, ProductBom.class);
        }
        if(this.qty != null){
            copy.qty = this.qty;
        }
        if(this.IsEnough != null){
            copy.IsEnough = this.IsEnough;
        }
        if(this.detailList != null){
            if(!CollectionUtils.isEmpty(this.detailList)){
                List<AssBomDetail> copyDetails = new ArrayList<>();
                for(AssBomDetail ass : this.detailList){
                    copyDetails.add(ass.deepCopy());
                }
            }
        }
        return copy;

    }

    public List<AssBomDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<AssBomDetail> detailList) {
        this.detailList = detailList;
        if (!CollectionUtils.isEmpty(detailList)) {
            IsEnough = true;
            for (AssBomDetail bomDetail : detailList) {
                if (!bomDetail.IsEnough()) {
                    IsEnough = false;
                }
            }
        }else {
            IsEnough = false;
        }
    }

    public ProductBom getProductBom() {
        return productBom;
    }

    public void setProductBom(ProductBom productBom) {
        this.productBom = productBom;
    }

    public Boolean getEnough() {
        return IsEnough;
    }

    public void setEnough(Boolean enough) {
        IsEnough = enough;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
