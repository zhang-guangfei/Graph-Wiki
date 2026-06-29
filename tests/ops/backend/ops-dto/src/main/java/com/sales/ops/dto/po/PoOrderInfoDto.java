package com.sales.ops.dto.po;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $ bugid:17646 20250526 c14717
 * @description：
 * @date ：Created in 2025/5/26 9:40
 */
public class PoOrderInfoDto implements Serializable {

    private static final long serialVersionUID = 2053009184701250723L;
    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    // 拆分类型 WHOLE("W","整型号采购"),
    //    ASS("A","拆分型号");
    private String wmtag;

    // 采购配置表的id列表
    private String infojson;

    //是否自动出库存
    private Boolean autoInventory;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
    }

    public Integer getSplititemno() {
        return splititemno;
    }

    public void setSplititemno(Integer splititemno) {
        this.splititemno = splititemno;
    }

    public String getWmtag() {
        return wmtag;
    }

    public void setWmtag(String wmtag) {
        this.wmtag = wmtag;
    }

    public String getInfojson() {
        return infojson;
    }

    public void setInfojson(String infojson) {
        this.infojson = infojson;
    }

    public Boolean getAutoInventory() {
        return autoInventory;
    }

    public void setAutoInventory(Boolean autoInventory) {
        this.autoInventory = autoInventory;
    }
}
