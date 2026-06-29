package com.sales.ops.dto.product;

import com.sales.ops.db.entity.Product;
import com.sales.ops.db.entity.ProductPrice;
import com.sales.ops.enums.ProductValidateEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2021/11/22 11:33
 */
public class ProductBeanDTO implements Serializable {
    private static final long serialVersionUID = 1568032344566210826L;
    /**
     * 产品信息
     */
    private ProductMdmDTO product ;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 产品状态
     */
    private List<ProductValidateResult> productValidateResultList;

    /**
     * 产品多段价格
     */
    private List<ProductPrice> productPriceList;


    /**
     * 产品价格
     */
    private ProductPrice productPrice;



    /**
     *
     * 默认常规型号
     *
     */
    public ProductBeanDTO() {
    }

    /**
     *
     * setProductValidateResultList:(设置单种产品分类信息). <br/>
     *
     * @author B74718
     * @param
     */
    public void setProductValidateResultList(ProductValidateEnum productValidateEnum) {
        this.productValidateResultList = new ArrayList<ProductValidateResult>();
        this.productValidateResultList.add(new ProductValidateResult(productValidateEnum));
    }

    /**
     *
     * addProductValidateResultList:(添加型号分类信息). <br/>
     *
     * @author B74718
     * @param
     */
    public void addProductValidateResult(ProductValidateEnum productValidateEnum) {

        if (this.productValidateResultList == null) {
            this.productValidateResultList = new ArrayList<ProductValidateResult>();
        }
        this.productValidateResultList.add(new ProductValidateResult(productValidateEnum));
    }


    public ProductMdmDTO getProduct() {
        return product;
    }

    public void setProduct(ProductMdmDTO product) {
        this.product = product;
    }

    public List<ProductValidateResult> getProductValidateResultList() {
        return productValidateResultList;
    }

    public void setProductValidateResultList(List<ProductValidateResult> productValidateResultList) {
        this.productValidateResultList = productValidateResultList;
    }



    public List<ProductPrice> getProductPriceList() {
        return productPriceList;
    }

    public void setProductPriceList(List<ProductPrice> productPriceList) {
        this.productPriceList = productPriceList;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }

}
