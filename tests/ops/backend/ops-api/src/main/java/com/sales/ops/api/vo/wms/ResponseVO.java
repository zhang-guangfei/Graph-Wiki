package com.sales.ops.api.vo.wms;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2021/12/10 14:09
 */
public class ResponseVO<T> implements Serializable {

    private static final long serialVersionUID = -1230402171499414001L;

    @JSONField(name = "return")
    private ReturnVO<T> result;

    public ReturnVO<T> getResult() {
        return result;
    }

    public void setResult(ReturnVO<T> result) {
        this.result = result;
    }
}
