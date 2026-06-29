package com.smc.smccloud.model.binorder;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class BinOrderDetailDTO {
    private Long id;
    private CacheComponent cache;
    private Long calcId;
    private Integer calcType;

    private String warehouseCode;
    private Boolean isMasterWarehouse = false;

    private String modelNo;

    private Integer orderQty;
    private Integer recommMonths;
    private Integer preStockFlag;
    private Integer replenishmentMethod;

    private Integer qtybin;

    private Integer bincell;
    private Integer freq;
    private Integer avgQty8;


    private Integer directpurchase;

    private Integer centerFlag;
    private String centerWarehouse;

    private String client;
    private List<String> clientWarehouse = new ArrayList<>();
    private String supplierCode;

    //下面为计算结果字段
    private Integer qtybinAll;

    private Integer bincellAll;
    //额定数量
    private Integer ratedQty;

    // List 保留业务顺序，Set 专用于高频 contains 判断，避免每条 detail 反复线性扫描 warehouseRange。
    private List<String> warehouseRange = new ArrayList<>();
    private Set<String> warehouseRangeSet = new HashSet<>();

    private Integer stockQty;

    private Integer ordingQty;

    private Integer preQty;

    private Integer moveQty;
    private Integer availbleQty;

    private Integer specStockQty;

    private Integer specOrdingQty;

    private Integer stockQtyAll;

    private String excessQty;
    private Integer otherExcessQty;

    private String orderType;

    private Double mean;

    private Double meanAll;

    private Double months;

    private Double stockMonths;

    private Double stockMonthsAll;
    //最大总可用月数
    private Double MaxStockAllMonths;
    //最大控制月数
    private Double MaxControllMonths;


    private Integer binDiffQty;

    private Double binDiffNum;

    private Integer needQty;


    private List<String> transWarehouseSequence = new ArrayList<>();
    private Map<String, Integer> excessQtyMap = new HashMap<>();
    private Map<String, Integer> transQtyMap = new HashMap<>();
    private List<BinOrderDetailOrdingInfoDO> ordingInfoDOList = new ArrayList<>();
    private List<BinOrderDetailPreInfoDO> preInfoDOList = new ArrayList<>();


    private Integer poQty;

    private Integer transQty;

    private Integer confirmQty;


    public void initQty() {
        stockQty = 0;
        ordingQty = 0;
        preQty = 0;
        moveQty = 0;
        availbleQty = 0;
        specStockQty = 0;
        specOrdingQty = 0;
        stockQtyAll = 0;
        otherExcessQty = 0;
        months = 0.0;
        stockMonths = 0.0;
        stockMonthsAll = 0.0;
        MaxStockAllMonths = 0.0;
        MaxControllMonths = 0.0;

        binDiffQty = 0;
        binDiffNum = 0.0;
        needQty = 0;
        poQty = 0;
        transQty = 0;
        confirmQty = 0;
    }
}
