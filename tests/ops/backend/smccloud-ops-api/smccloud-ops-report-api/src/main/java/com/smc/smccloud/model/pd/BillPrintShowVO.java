package com.smc.smccloud.model.pd;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2023/6/20 10:56
 * @Descripton TODO
 */
@Data
public class BillPrintShowVO {

    private String warehouseCode;

    private String warehouseCodeName;

    // 型号种类总数
    private int modelTypeCount;

    // 型号数量总和
    private int modelNoQtyCount;

    // 现品票型号总数量
    private int xpModelNoCount;

    // 正式过渡库位型号总数量
    private int gdModelNoCount;

    // 集约待交接区型号总数量
    private int jyModelNoCount;

    // 预计生成现品票
    private int creXPBillCount;

    // 预计生成立会票
    private int creLhBillCount;

    // 预计生成数据票
    private int creDataBillCount;

    // 预计生成到货未入清单票
    private int creArrveNotInCount;


}
