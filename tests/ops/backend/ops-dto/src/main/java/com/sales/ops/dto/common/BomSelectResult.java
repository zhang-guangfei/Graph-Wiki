package com.sales.ops.dto.common;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: $ bugid:17799
 * @description：
 * @date ：Created in 2025/6/13 10:41
 */
public class BomSelectResult implements Serializable {
    private static final long serialVersionUID = 9202225070777150321L;

    private Boolean specialBom;

    private List<BomVersions> bomVersionsList;

    public Boolean getSpecialBom() {
        return specialBom;
    }

    public void setSpecialBom(Boolean specialBom) {
        this.specialBom = specialBom;
    }

    public List<BomVersions> getBomVersionsList() {
        return bomVersionsList;
    }

    public void setBomVersionsList(List<BomVersions> bomVersionsList) {
        this.bomVersionsList = bomVersionsList;
    }
}
