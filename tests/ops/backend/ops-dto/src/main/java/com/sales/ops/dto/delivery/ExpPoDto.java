package com.sales.ops.dto.delivery;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/8/27 11:39
 */
public class ExpPoDto implements Serializable {
    private static final long serialVersionUID = -179159374031095765L;

    private String modelNo;

    private Date expPoDate;
    public ExpPoDto(){}

    public ExpPoDto(String modelNo, Date expPoDate){
        this.modelNo = modelNo;
        this.expPoDate = expPoDate;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Date getExpPoDate() {
        return expPoDate;
    }

    public void setExpPoDate(Date expPoDate) {
        this.expPoDate = expPoDate;
    }
}
