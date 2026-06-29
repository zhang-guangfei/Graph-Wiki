package com.sales.ops.serviceimpl.dispatch.dodispatch.domain.goodsconfirm;

import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.dto.flux.RoConfirmItem;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author C12961
 * @date 2023/3/14 13:36
 */
@Data
public class GoodsConfirmContext {
    private Date startTime;
    private String invoiceNo;
    private Long invoiceId;
    private OpsWarehouse signWarehouse;
    private List<OpsInventoryMove> moves;
    // items：收集的 move和roId的关联关系信息
    // 其中包含move的出库预占信息
    // roId,moves
    private Map<String, List<GoodsConfirmContextItem>> items = new HashMap<>();
    // 处理过程中生成的结果的临时缓存
    private List<ResultDto> results = new ArrayList<>();
    // 用来写入日志表和用来函数返回的数据
    private List<RoConfirmItem> returnDtoList = new ArrayList<>();
    // 用来记录出库指令的crossFlag信息
    private List<ResultDto> dbResults = new ArrayList<>();
    private List<ResultDto> doResults = new ArrayList<>();
    private List<ResultDto> pcoResults = new ArrayList<>();

    // 用来生成到货确认事件的信息


    public GoodsConfirmContext(String invoiceNo, Long invoiceId, OpsWarehouse signWarehouse, List<OpsInventoryMove> moves) {
        this.invoiceNo = invoiceNo;
        this.invoiceId = invoiceId;
        this.signWarehouse = signWarehouse;
        this.moves = moves;
    }

    public void putItem(GoodsConfirmContextItem item) {
        String roId = item.getRoId();
        if (!items.containsKey(roId)) {
            items.put(roId, new ArrayList<>());
        }
        this.items.get(roId).add(item);
    }

    public String getWarehouseType() {
        return this.signWarehouse.getWarehouseType();
    }


    public void putResult(ResultDto result) {
        this.results.add(result);
    }


    public void createRoConfirmItem() {
        this.returnDtoList = results.stream().map(ResultDto::toReturnDto).collect(Collectors.toList());
    }

    public void filterResult() {
        this.dbResults = results.stream().filter(ResultDto::isDb).collect(Collectors.toList());
        this.doResults = results.stream().filter(ResultDto::isDo).collect(Collectors.toList());
        this.pcoResults = results.stream().filter(ResultDto::isPco).collect(Collectors.toList());
    }

}
