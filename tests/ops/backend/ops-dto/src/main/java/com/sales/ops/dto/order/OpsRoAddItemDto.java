package com.sales.ops.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sales.ops.db.entity.OpsRo;
import com.sales.ops.db.entity.OpsRoItem;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：收货指令和明细
 * @date ：Created in 2021/10/13 10:57
 */
public class OpsRoAddItemDto implements Serializable {

    private static final long serialVersionUID = 213845183628174146L;
    private OpsRo ro;
    private Long opsWmOrderTaskId;

    private  List<OpsRoItem> list;

    public OpsRo getRo() {
        return ro;
    }

    public void setRo(OpsRo ro) {
        this.ro = ro;
    }

    public List<OpsRoItem> getList() {
        return list;
    }

    public void setList(List<OpsRoItem> list) {
        this.list = list;
    }

    public Long getOpsWmOrderTaskId() {
        return opsWmOrderTaskId;
    }

    public void setOpsWmOrderTaskId(Long opsWmOrderTaskId) {
        this.opsWmOrderTaskId = opsWmOrderTaskId;
    }

    @Override
    public String toString() {
        return "OpsRoAddItemDto{" +
                "ro=" + ro.getRoId() +
                ", opsWmOrderTaskId=" + opsWmOrderTaskId +
                '}';
    }
}
