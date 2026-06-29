package com.sales.ops.api.vo.wms;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：富勒返回值
 * @date ：Created in 2021/10/29 13:16
 */
public class WmsResultVO<T> implements Serializable {

    private static final long serialVersionUID = -4173118605365776779L;
    private ResponseVO<T> Response;

    public ResponseVO<T> getResponse() {
        return Response;
    }

    public void setResponse(ResponseVO<T> response) {
        Response = response;
    }

    public boolean isSuccess() {
        return Response.getResult().getReturnFlag().equals("1");
    }

}


