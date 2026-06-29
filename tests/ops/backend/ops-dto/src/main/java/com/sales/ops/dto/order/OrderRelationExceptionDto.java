package com.sales.ops.dto.order;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/10/16 15:00
 */
public class OrderRelationExceptionDto implements Serializable {

    private static final long serialVersionUID = -110557013731020395L;
    private String rorderFno;

    private String msg;

    public OrderRelationExceptionDto(){}

    public OrderRelationExceptionDto(String rorderFno, String msg){
        this.rorderFno = rorderFno;
        this.msg = msg;
    }

    public String getRorderFno() {
        return rorderFno;
    }

    public void setRorderFno(String rorderFno) {
        this.rorderFno = rorderFno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
