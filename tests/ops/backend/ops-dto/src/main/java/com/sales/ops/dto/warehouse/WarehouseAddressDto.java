package com.sales.ops.dto.warehouse;

import com.sales.ops.db.entity.OpsWarehouseAddress;

import javax.naming.directory.SearchResult;
import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/7/24 9:15
 */
public class WarehouseAddressDto implements Serializable {
    private static final long serialVersionUID = -9043893801842645603L;

    private String warehouseCode;

    private Long addressId;

    private String creater;

    private List<String> doTypeList;


    public List<String> getDoTypeList() {
        return doTypeList;
    }

    public void setDoTypeList(List<String> doTypeList) {
        this.doTypeList = doTypeList;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }
}
