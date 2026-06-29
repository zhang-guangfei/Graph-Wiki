package com.smc.smccloud.model.binorder;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BinOrderBatchUpdateDTO {

    private Long calcId;
    private List<String> modelNos;
    private List<Long> ids;
    private String orderType;
    private Integer poQty;
    private  Integer transQty;
    private String transType;
    private Date dlvDate;
    private  Integer status;
    private String supplierCode;

    private  String moreWhere;


}
