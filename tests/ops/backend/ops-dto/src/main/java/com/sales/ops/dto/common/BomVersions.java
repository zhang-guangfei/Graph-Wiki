package com.sales.ops.dto.common;

import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.db.entity.ProductBomDetail;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: $ bugid:17799
 * @description：
 * @date ：Created in 2025/6/13 10:43
 */
public class BomVersions implements Serializable {
    private static final long serialVersionUID = 9174979101174298220L;

    private Boolean bomType;//是否拆分 1 是 0否

    private ProductBom productBom;

    private List<ProductBomDetail> bomDetails;

    public ProductBom getProductBom() {
        return productBom;
    }

    public void setProductBom(ProductBom productBom) {
        this.productBom = productBom;
    }

    public List<ProductBomDetail> getBomDetails() {
        return bomDetails;
    }

    public void setBomDetails(List<ProductBomDetail> bomDetails) {
        this.bomDetails = bomDetails;
    }

    public Boolean getBomType() {
        return bomType;
    }

    public void setBomType(Boolean bomType) {
        this.bomType = bomType;
    }
}
