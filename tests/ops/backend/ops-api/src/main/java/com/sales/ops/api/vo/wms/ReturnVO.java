package com.sales.ops.api.vo.wms;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2021/12/10 14:07
 */
public class ReturnVO<T> implements Serializable {

    private static final long serialVersionUID = -1635007996763332772L;
    private String returnFlag;

    private String returnCode;

    private String returnDesc;

    private List<T> resultInfo ;

    public void setReturnFlag(String returnFlag){
        this.returnFlag = returnFlag;
    }
    public String getReturnFlag(){
        return this.returnFlag;
    }
    public void setReturnCode(String returnCode){
        this.returnCode = returnCode;
    }
    public String getReturnCode(){
        return this.returnCode;
    }
    public void setReturnDesc(String returnDesc){
        this.returnDesc = returnDesc;
    }
    public String getReturnDesc(){
        return this.returnDesc;
    }
    public void setResultInfo(List<T> resultInfo){
        this.resultInfo = resultInfo;
    }
    public List<T> getResultInfo(){
        return this.resultInfo;
    }
}
