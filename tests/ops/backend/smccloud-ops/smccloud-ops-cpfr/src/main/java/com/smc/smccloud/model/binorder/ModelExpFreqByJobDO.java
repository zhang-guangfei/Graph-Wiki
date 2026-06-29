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
@TableName("model_exp_freq_by_job")
public class ModelExpFreqByJobDO
{
    /**ID*/
    @TableId(type = IdType.AUTO)
    @TableField("id")
    public long id ;

    @TableField("JobId")
    public long jobId ;

    @TableField("warehouseCode")
    public String warehouseCode ;

    @TableField("ModelType")
    public String modelType ;

    @TableField("ModelNo")
    public String modelNo ;

    @TableField("MonthsOf36")
    public Integer monthsOf36 ;

    @TableField("CustomersOf36")
    public Integer customersOf36 ;

    @TableField("QtyOf36")
    public Integer qtyOf36 ;

    @TableField("AvgQtyOf36")
    public Integer avgQtyOf36 ;

    @TableField("MaxCustomerOf36")
    public String maxCustomerOf36 ;

    @TableField("MaxCustomerDeptOf36")
    public String maxCustomerDeptOf36 ;

    @TableField("MaxCustomerQtyOf36")
    public Integer maxCustomerQtyOf36 ;

    @TableField("MaxCustomerRateOf36")
    public BigDecimal maxCustomerRateOf36 ;

    @TableField("OrdersOf36")
    public Integer ordersOf36 ;

    @TableField("MonthsOf24")
    public Integer monthsOf24 ;

    @TableField("CustomersOf24")
    public Integer customersOf24 ;

    @TableField("OrdersOf24")
    public Integer ordersOf24 ;

    @TableField("QtyOf24")
    public Integer qtyOf24 ;

    @TableField("AvgQtyOf24")
    public Integer avgQtyOf24 ;

    @TableField("MaxCustomerOf24")
    public String maxCustomerOf24 ;

    @TableField("MaxCustomerDeptOf24")
    public String maxCustomerDeptOf24 ;

    @TableField("MaxCustomerQtyOf24")
    public Integer maxCustomerQtyOf24 ;

    @TableField("MaxCustomerRateOf24")
    public BigDecimal maxCustomerRateOf24 ;

    @TableField("MonthsOf12")
    public Integer monthsOf12 ;

    @TableField("CustomersOf12")
    public Integer customersOf12 ;

    @TableField("QtyOf12")
    public Integer qtyOf12 ;

    @TableField("AvgQtyOf12")
    public Integer avgQtyOf12 ;

    @TableField("OrdersOf12")
    public Integer ordersOf12 ;

    @TableField("MaxCustomerOf12")
    public String maxCustomerOf12 ;

    @TableField("MaxCustomerDeptOf12")
    public String maxCustomerDeptOf12 ;

    @TableField("MaxCustomerQtyOf12")
    public Integer maxCustomerQtyOf12 ;

    @TableField("MaxCustomerRateOf12")
    public BigDecimal maxCustomerRateOf12 ;

    @TableField("MonthsOf8")
    public Integer monthsOf8 ;

    @TableField("CustomersOf8")
    public Integer customersOf8 ;

    @TableField("OrdersOf8")
    public Integer ordersOf8 ;

    @TableField("QtyOf8")
    public Integer qtyOf8 ;

    @TableField("AvgQtyOf8")
    public Integer avgQtyOf8 ;

    @TableField("MaxCustomerOf8")
    public String maxCustomerOf8 ;

    @TableField("MaxCustomerDeptOf8")
    public String maxCustomerDeptOf8 ;

    @TableField("MaxCustomerQtyOf8")
    public Integer maxCustomerQtyOf8 ;

    @TableField("MaxCustomerRateOf8")
    public BigDecimal maxCustomerRateOf8 ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("UpdateTime")
    public Date updateTime ;

    @TableField("classCode")
    public String classCode ;

    @TableField("modelClass")
    public String modelClass ;

    @TableField("M1")
    public Integer m1 ;

    @TableField("M2")
    public Integer m2 ;

    @TableField("M3")
    public Integer m3 ;

    @TableField("M4")
    public Integer m4 ;

    @TableField("M5")
    public Integer m5 ;

    @TableField("M6")
    public Integer m6 ;

    @TableField("M7")
    public Integer m7 ;

    @TableField("M8")
    public Integer m8 ;

    @TableField("M9")
    public Integer m9 ;

    @TableField("M10")
    public Integer m10 ;

    @TableField("M11")
    public Integer m11 ;

    @TableField("M12")
    public Integer m12 ;

    @TableField("M13")
    public Integer m13 ;

    @TableField("M14")
    public Integer m14 ;

    @TableField("M15")
    public Integer m15 ;

    @TableField("M16")
    public Integer m16 ;

    @TableField("M17")
    public Integer m17 ;

    @TableField("M18")
    public Integer m18 ;

    @TableField("M19")
    public Integer m19 ;

    @TableField("M20")
    public Integer m20 ;

    @TableField("M21")
    public Integer m21 ;

    @TableField("M22")
    public Integer m22 ;

    @TableField("M23")
    public Integer m23 ;

    @TableField("M24")
    public Integer m24 ;

    @TableField("M25")
    public Integer m25 ;

    @TableField("M26")
    public Integer m26 ;

    @TableField("M27")
    public Integer m27 ;

    @TableField("M28")
    public Integer m28 ;

    @TableField("M29")
    public Integer m29 ;

    @TableField("M30")
    public Integer m30 ;

    @TableField("M31")
    public Integer m31 ;

    @TableField("M32")
    public Integer m32 ;

    @TableField("M33")
    public Integer m33 ;

    @TableField("M34")
    public Integer m34 ;

    @TableField("M35")
    public Integer m35 ;

    @TableField("M36")
    public Integer m36 ;

    @TableField("DesignType")
    public String designType ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("LastOrdMonth")
    public Date lastOrdMonth ;

    @TableField("ProductSeries")
    public String productSeries ;

    @TableField("MainOrigin")
    public String mainOrigin ;

    @TableField("SecondOrigin")
    public String secondOrigin ;

    @TableField("EPrice")
    public BigDecimal ePrice ;

    @TableField("ECode")
    public String eCode ;

    @TableField("Variation")
    public BigDecimal variation ;

    @TableField("MoveRate1")
    public BigDecimal moveRate1 ;

    @TableField("MoveRate2")
    public BigDecimal moveRate2 ;

    @TableField("MoveRate3")
    public BigDecimal moveRate3 ;

    @TableField("SetMean")
    public Integer setMean ;

    @TableField("DeptNo")
    public String deptNo ;

    @TableField("ProductType")
    public int productType ;

    @TableField("SetClassCode")
    public String setClassCode ;

    @TableField("SetAvgQty")
    public Integer setAvgQty ;

}
