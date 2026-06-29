package com.sales.ops.dto.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.sales.ops.db.entity.OpsRequestpurchase;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class OpsRequestpurchaseDto extends OpsRequestpurchase {

    private String uuid;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hopeExportDateMerge;

    private String transTypeMerge;

    private String purchaseWarehouseIdMerge;

    private int  QuantitySum;
    // 是否为BIN品
    private boolean isBin;
    //bin数量
    private int binCount;
    //bin频率
    private int binFrequency;
    
    private BigDecimal stdpriceMerge;
    
    // 进口FOB价（原币种）
    private BigDecimal importFobPriceOriginalMerge;
    
    private String inqnoMerge;
    
    @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hopedeliverydateMerge;
    
    private String  smccodeMerge;

    /**
     * 合并后的重量
     */
    private BigDecimal netweightMerge;

//	public OpsRequestpurchase getOpsRequestpurchase() {
//		return opsRequestpurchase;
//	}
//
//	public void setOpsRequestpurchase(OpsRequestpurchase opsRequestpurchase) {
//		this.opsRequestpurchase = opsRequestpurchase;
//	}


    public BigDecimal getNetweightMerge() {
        return netweightMerge;
    }

    public void setNetweightMerge(BigDecimal netweightMerge) {
        this.netweightMerge = netweightMerge;
    }

    public Date getHopedeliverydateMerge() {
		return hopedeliverydateMerge;
	}

	public BigDecimal getImportFobPriceOriginalMerge() {
		return importFobPriceOriginalMerge;
	}

	public void setImportFobPriceOriginalMerge(BigDecimal importFobPriceOriginalMerge) {
		this.importFobPriceOriginalMerge = importFobPriceOriginalMerge;
	}

	public String getPurchaseWarehouseIdMerge() {
		return purchaseWarehouseIdMerge;
	}

	public void setPurchaseWarehouseIdMerge(String purchaseWarehouseIdMerge) {
		this.purchaseWarehouseIdMerge = purchaseWarehouseIdMerge;
	}

	public void setHopedeliverydateMerge(Date hopedeliverydateMerge) {
		this.hopedeliverydateMerge = hopedeliverydateMerge;
	}

	public String getInqnoMerge() {
		return inqnoMerge;
	}

	public void setInqnoMerge(String inqnoMerge) {
		this.inqnoMerge = inqnoMerge;
	}


	public String getSmccodeMerge() {
		return smccodeMerge;
	}

	public void setSmccodeMerge(String smccodeMerge) {
		this.smccodeMerge = smccodeMerge;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getStdpriceMerge() {
		return stdpriceMerge;
	}

	public void setStdpriceMerge(BigDecimal stdpriceMerge) {
		this.stdpriceMerge = stdpriceMerge;
	}

	public boolean isBin() {
        return isBin;
    }

    public void setBin(boolean bin) {
        isBin = bin;
    }

    public int getBinCount() {
        return binCount;
    }

    public void setBinCount(int binCount) {
        this.binCount = binCount;
    }

    public int getBinFrequency() {
        return binFrequency;
    }

    public void setBinFrequency(int binFrequency) {
        this.binFrequency = binFrequency;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getHopeExportDateMerge() {
        return hopeExportDateMerge;
    }

    public void setHopeExportDateMerge(Date hopeExportDateMerge) {
        this.hopeExportDateMerge = hopeExportDateMerge;
    }

    public String getTransTypeMerge() {
        return transTypeMerge;
    }

    public void setTransTypeMerge(String transTypeMerge) {
        this.transTypeMerge = transTypeMerge;
    }

    public int getQuantitySum() {
        return QuantitySum;
    }

    public void setQuantitySum(int quantitySum) {
        QuantitySum = quantitySum;
    }

    private static final long serialVersionUID = 1L;


}