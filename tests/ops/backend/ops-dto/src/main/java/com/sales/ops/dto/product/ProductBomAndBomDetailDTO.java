package com.sales.ops.dto.product;

import com.sales.ops.db.entity.ProductBom;
import com.sales.ops.db.entity.ProductBomDetail;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：bom和bomDetail
 * @date ：Created in 2021/12/13 14:01
 */
public class ProductBomAndBomDetailDTO implements Serializable {

    private static final long serialVersionUID = -6087542652584151746L;

    private ProductBom pb;

    private List<ProductBomDetail> list;

    public ProductBom getPb() {
        return pb;
    }

    public void setPb(ProductBom pb) {
        this.pb = pb;
    }

    public List<ProductBomDetail> getList() {
        return list;
    }

    public void setList(List<ProductBomDetail> list) {
        this.list = list;
    }
}
