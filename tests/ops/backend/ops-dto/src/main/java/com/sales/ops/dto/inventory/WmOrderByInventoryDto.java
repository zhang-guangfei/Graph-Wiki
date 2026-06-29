package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.OpsRequestpurchase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 库存调度算法生成对应的指令
 * @date 2021/10/2 20:22
 */
public class WmOrderByInventoryDto {

    private List<CreDoByInventoryDto> dolist = new ArrayList<>();
    private List<CreRoByInventoryDto> roList = new ArrayList<>();
    private List<CrePcoByInventoryDto> pcoList = new ArrayList<>();



    public WmOrderByInventoryDto(List<CreDoByInventoryDto> dolist, List<CreRoByInventoryDto> roList, List<CrePcoByInventoryDto> pcoList ) {
        this.dolist = dolist;
        this.roList = roList;
        this.pcoList = pcoList;

    }

    public WmOrderByInventoryDto() {
    }

    public List<CreDoByInventoryDto> getDolist() {
        return dolist;
    }

    public void setDolist(List<CreDoByInventoryDto> dolist) {
        this.dolist = dolist;
    }

    public List<CreRoByInventoryDto> getRoList() {
        return roList;
    }

    public void setRoList(List<CreRoByInventoryDto> roList) {
        this.roList = roList;
    }

    public List<CrePcoByInventoryDto> getPcoList() {
        return pcoList;
    }

    public void setPcoList(List<CrePcoByInventoryDto> pcoList) {
        this.pcoList = pcoList;
    }


}
