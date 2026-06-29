package com.sales.ops.dto.eta;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/12/6 11:11
 */
public class ETAPageDto implements Serializable {
    private static final long serialVersionUID = -3314498387183131950L;

    private List<ETAInfoListPageDto> resultListData;

    private String cost;

    public ETAPageDto(){};

    public ETAPageDto(List<ETAInfoListPageDto> list , String cost){
        this.resultListData = list;
        this.cost = cost;

    };

    public List<ETAInfoListPageDto> getResultListData() {
        return resultListData;
    }

    public void setResultListData(List<ETAInfoListPageDto> resultListData) {
        this.resultListData = resultListData;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
