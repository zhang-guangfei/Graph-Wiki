package com.sales.ops.dto.order;

/**
 * @author C12961
 * @date 2023/1/3 8:46
 */

public class ExpressInfoItemDto {
    private String courierno;
    private String time;
    private String content;

    public String getCourierno() {
        return courierno;
    }

    public void setCourierno(String courierno) {
        this.courierno = courierno;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
