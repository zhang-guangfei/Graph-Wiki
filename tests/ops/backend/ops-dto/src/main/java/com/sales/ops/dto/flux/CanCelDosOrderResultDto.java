package com.sales.ops.dto.flux;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：删除发运单返回部分错误
 * @date ：Created in 2022/3/7 8:32
 */
public class CanCelDosOrderResultDto implements Serializable {
    private static final long serialVersionUID = 7103205756316397279L;

    private String docNo;
    private String errorcode;
    private String errordescr;

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
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
}
