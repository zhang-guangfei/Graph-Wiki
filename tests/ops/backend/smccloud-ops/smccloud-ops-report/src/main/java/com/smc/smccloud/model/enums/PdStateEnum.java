package com.smc.smccloud.model.enums;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2023/6/27 8:59
 * @Descripton TODO
 */
public enum  PdStateEnum {

    xjpd("0","新建盘点"),
    sjcq("1","数据抽取中"),
    sjcqOk("2","数据抽取完毕"),
    dhsjzb("3","到货未入数据准备中"),
    dhsjzbOK("4","到货未入数据已就绪"),
    pdBillscz("5","盘点票生成中"),
    pdBillysc("6","盘点票已生成"),
    pdlrz("7","盘点录入中"),
    pdcyqrz("8","盘点差异确认中"),
    pdtz("9","盘点调整中"),
    generateReport("10","已生成盘点报表"),
    sctzd("11","生成调账单"),
    qrtzd("12","确认调账单");

    private String code;
    private String codeName;

    PdStateEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public static String getCodeNameByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (PdStateEnum item : PdStateEnum.values()) {
            if (code.equals(item.getCode())) {
                return item.getCodeName();
            }
        }
        return null;
    }
}
