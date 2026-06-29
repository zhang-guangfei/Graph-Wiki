package com.sales.ops.dto.inventory;

import com.sales.ops.dto.flux.RoConfirmItem;
import com.sales.ops.dto.order.OpsSendPcoAndDoMidIDDto;

import java.util.List;
import java.util.Map;

public class GoodsConfirmDto {
    private List<RoConfirmItem> list;
    private Map<String, String> doSendMap;
    private Map<String, OpsSendPcoAndDoMidIDDto> pcoSendMap;


    public GoodsConfirmDto(List<RoConfirmItem> list, Map<String, String> doSendMap, Map<String, OpsSendPcoAndDoMidIDDto> pcoSendMap) {
        this.list = list;
        this.doSendMap = doSendMap;
        this.pcoSendMap = pcoSendMap;
    }

    public List<RoConfirmItem> getList() {
        return list;
    }

    public Map<String, String> getDoSendMap() {
        return doSendMap;
    }

    public Map<String, OpsSendPcoAndDoMidIDDto> getPcoSendMap() {
        return pcoSendMap;
    }
}
