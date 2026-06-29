package com.sales.ops.dto.order;

import java.util.List;

/**
 * @author C12961
 * @date 2023/1/3 8:44
 */
public class ExpressInfoDto {

    private String code;
    private String courierno;
    private String type;
    private List<ExpressInfoItemDto> expressContentList;
    private String state;
    private String name;
    private String site;
    private String phone;
    private String logo;
    private String recordtime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCourierno() {
        return courierno;
    }

    public void setCourierno(String courierno) {
        this.courierno = courierno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ExpressInfoItemDto> getExpressContentList() {
        return expressContentList;
    }

    public void setExpressContentList(List<ExpressInfoItemDto> expressContentList) {
        this.expressContentList = expressContentList;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }
}
