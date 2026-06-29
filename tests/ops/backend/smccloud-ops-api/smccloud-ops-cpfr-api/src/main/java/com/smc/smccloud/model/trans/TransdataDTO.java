package com.smc.smccloud.model.trans;

import com.sales.ops.dto.order.InventoryForTransInputDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TransdataDTO {

    private List<TransdataVO> voList;

    private InventoryForTransInputDto transDto;
}
