package com.sales.ops.dto.inqb;

import com.sales.ops.db.entity.OpsInqbApply;
import com.sales.ops.db.entity.OpsInqbDetail;

import java.util.List;

/**
 * @author B91717
 * INQB-返回给订单使用的主子表信息集合
 */
public class OpsInqbQueryInfo extends OpsInqbApply {
    /**
     * 申请子项分配明细信息
     */
    private List<OpsInqbDetail> opsInqbDetailList;

    public List<OpsInqbDetail> getOpsInqbDetailList() {
        return opsInqbDetailList;
    }

    public void setOpsInqbDetailList(List<OpsInqbDetail> opsInqbDetailList) {
        this.opsInqbDetailList = opsInqbDetailList;
    }
}
