package com.sales.ops.dto.order;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/8/14 10:24
 */
public class FinishOrderWmsResDto implements Serializable {

    private static final long serialVersionUID = 6173046498280184654L;
    private String docNo;
    //A001 单据不存在
    private String errorType;
    private Integer finishQty;
    private String errorcode;
    private String errordescr;

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public Integer getFinishQty() {
        return finishQty;
    }

    public void setFinishQty(Integer finishQty) {
        this.finishQty = finishQty;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrordescr() {
        return errordescr;
    }

    public void setErrordescr(String errordescr) {
        this.errordescr = errordescr;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    @Override
    public String toString() {
        return "{" +
                "docNo='" + docNo + '\'' +
                ", finishQty=" + finishQty +
                ", errorcode='" + errorcode + '\'' +
                ", errorType='" + errorType + '\'' +
                ", errordescr='" + errordescr + '\'' +
                '}';
    }
}
