package com.sales.ops.dto.test;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/9/4 12:44
 */
public class CalDlvInfoResultDto implements Serializable {

    private Long id;

    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    private String transtype;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date hopedeliverydate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date hopeexportdate;

    private String supplierid;

    private String infojson;

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getHopedeliverydate() {
        return hopedeliverydate;
    }

    public void setHopedeliverydate(Date hopedeliverydate) {
        this.hopedeliverydate = hopedeliverydate;
    }

    public Date getHopeexportdate() {
        return hopeexportdate;
    }

    public void setHopeexportdate(Date hopeexportdate) {
        this.hopeexportdate = hopeexportdate;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getInfojson() {
        return infojson;
    }

    public void setInfojson(String infojson) {
        this.infojson = infojson;
    }
}
