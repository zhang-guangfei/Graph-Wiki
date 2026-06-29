
package com.smc.smccloud.model.binorder;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wuweidong
 * @create 2023/6/8 13:42
 * @description
 */

@Data
public class ModelExpFreqByJobVO
{
    /**ID*/
    @TableId(type = IdType.AUTO)
    public long id ;

    public long jobId ;

    public String warehouseCode ;

    public String modelType ;

    public String modelNo ;

    public Integer monthsOf36 ;

    public Integer customersOf36 ;

    public Integer qtyOf36 ;

    public Integer avgQtyOf36 ;

    public String maxCustomerOf36 ;

    public String maxCustomerDeptOf36 ;

    public Integer maxCustomerQtyOf36 ;

    public BigDecimal maxCustomerRateOf36 ;

    public Integer ordersOf36 ;

    public Integer monthsOf24 ;

    public Integer customersOf24 ;

    public Integer ordersOf24 ;

    public Integer qtyOf24 ;

    public Integer avgQtyOf24 ;

    public String maxCustomerOf24 ;

    public String maxCustomerDeptOf24 ;

    public Integer maxCustomerQtyOf24 ;

    public BigDecimal maxCustomerRateOf24 ;

    public Integer monthsOf12 ;

    public Integer customersOf12 ;

    public Integer qtyOf12 ;

    public Integer avgQtyOf12 ;

    public Integer ordersOf12 ;

    public String maxCustomerOf12 ;

    public String maxCustomerDeptOf12 ;

    public Integer maxCustomerQtyOf12 ;

    public BigDecimal maxCustomerRateOf12 ;

    public Integer monthsOf8 ;

    public Integer customersOf8 ;

    public Integer ordersOf8 ;

    public Integer qtyOf8 ;

    public Integer avgQtyOf8 ;

    public String maxCustomerOf8 ;

    public String maxCustomerDeptOf8 ;

    public Integer maxCustomerQtyOf8 ;

    public BigDecimal maxCustomerRateOf8 ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

    public Date updateTime ;

    public String classCode ;

    public String modelClass ;

    public Integer m1 ;

    public Integer m2 ;

    public Integer m3 ;

    public Integer m4 ;

    public Integer m5 ;

    public Integer m6 ;

    public Integer m7 ;

    public Integer m8 ;

    public Integer m9 ;

    public Integer m10 ;

    public Integer m11 ;

    public Integer m12 ;

    public Integer m13 ;

    public Integer m14 ;

    public Integer m15 ;

    public Integer m16 ;

    public Integer m17 ;

    public Integer m18 ;

    public Integer m19 ;

    public Integer m20 ;

    public Integer m21 ;

    public Integer m22 ;

    public Integer m23 ;

    public Integer m24 ;

    public Integer m25 ;

    public Integer m26 ;

    public Integer m27 ;

    public Integer m28 ;

    public Integer m29 ;

    public Integer m30 ;

    public Integer m31 ;

    public Integer m32 ;

    public Integer m33 ;

    public Integer m34 ;

    public Integer m35 ;

    public Integer m36 ;

    public String designType ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date lastOrdMonth ;

    public String productSeries ;

    public String mainOrigin ;

    public String secondOrigin ;

    public BigDecimal ePrice ;

    public String eCode ;

    public BigDecimal variation ;

    public BigDecimal moveRate1 ;

    public BigDecimal moveRate2 ;

    public BigDecimal moveRate3 ;

    public Integer setMean ;

    public String deptNo ;

    public int productType ;

    public String setClassCode ;

    public Integer setAvgQty ;

}
