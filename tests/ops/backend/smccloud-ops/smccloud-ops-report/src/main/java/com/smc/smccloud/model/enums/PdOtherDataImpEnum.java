package com.smc.smccloud.model.enums;

/**
 * @Author lyc
 * @Date 2023/6/26 9:16
 * @Descripton TODO
 */
public enum  PdOtherDataImpEnum {
    yp("yp","样品未结转"),
    yck("yck","已出库未推财务"),
    cwbc("cwbc","财务补偿数据"),
    db("db","OPS调拨在途"),
    zz("zz","OPS制造在途"),
    wmszz("wmszz","WMS制造在途"),
    opsData("opsData","OPS库存数据"),
    cwjc("cwjc","财务结存数据"),
    cgdhwr("cgdhwr","OPS采购到货未入"),
    thdhwr("thdhwr","OPS退货到货未入"),
    dbdhwr("dbdhwr","OPS调拨到货未入"),
    wmsbc("wmsbc","WMS补偿数据"),
    opsReturnData("opsReturnData","ops退货在途数据"),
    wmsReturnData("wmsReturnData","wms退货在途数据"),
    wmszhzt("wmszhzt","WMS组换在途数据"),
    wmszhdhwr("wmszhdhwr","WMS组换到货未入"),
    opszhzt("opszhzt","OPS组换在途数据"),
    opszhdhwr("opszhdhwr","OPS组换到货未入数据");

    private String codeName;
    private String code;

    PdOtherDataImpEnum(String code, String codeName) {
        this.codeName = codeName;
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
