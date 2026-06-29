package com.smc.smccloud.model.bindata;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.List;

/**
 * @Author edp02 @Date 2021/10/6 15:06
 */
@Data
public class  BindataRequest extends BaseQuery {

    private Integer stockType;
    private List<String>  warehouseCodes;
    private String warehouseCode;

    private List<String> modelNos;

    private String modelNo;

    private String customerNo;

    private Integer[] ids;

    private Long propertyID;

    private Boolean isdelFlag;

    private String orderType;

    private String supplierCode;
    private  String binType;

}
