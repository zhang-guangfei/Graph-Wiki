package com.sales.ops.api.dto.wms;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：barcode_to_wms 接口文档4.4
 * @date ：Created in 2021/10/28 16:42
 */
public class DocAsnDetailsDto implements Serializable {
    private static final long serialVersionUID = -4675267463582704973L;

    private String  referenceNo;//上游来源备注号FALSEint
    private String  lineNo;//上游来源订单行号FALSEint
    private String  sku;//产品TRUEString(50)
    private String  expectedQty;//预期数量TRUEdecimal(18, 8)
    private String  totalPrice;//总价FALSEdecimal(18, 4)
    private String  lotAtt01;//生产日期FALSEString(20)
    private String  lotAtt02;//失效日期FALSEString(20)
    private String  lotAtt03;//入库日期FALSEString(20)
    private String  lotAtt04;//批次属性04FALSEString(100)
    private String  lotAtt05;//批次属性05FALSEString(100)
    private String  lotAtt06;//批次属性06FALSEString(100)
    private String  lotAtt07;//批次属性07FALSEString(100)
    private String  lotAtt08;//是否不良品FALSEString(100)
    private String  lotAtt09;//批次属性09FALSEString(100)
    private String  lotAtt10;//批次属性10FALSEString(100)
    private String  lotAtt11;//批次属性11FALSEString(100)
    private String  lotAtt12;//多包装管理信息FALSEString(100)
    private String  lotAtt13;//批次属性13FALSEString(20)
    private String  lotAtt14;//批次属性14FALSEString(20)
    private String  lotAtt15;//批次属性15FALSEString(20)
    private String  lotAtt16;//批次属性16FALSEString(100)
    private String  lotAtt17;//批次属性17FALSEString(100)
    private String  lotAtt18;//批次属性18FALSEString(100)
    private String  lotAtt19;//批次属性19FALSEString(100)
    private String  lotAtt20;//批次属性20FALSEString(100)
    private String  lotAtt21;//批次属性21FALSEString(100)
    private String  lotAtt22;//批次属性22FALSEString(100)
    private String  lotAtt23;//批次属性23FALSEString(100)
    private String  lotAtt24;//批次属性24FALSEString(100)
    private String  dedi04;//EDI相关信息04FALSEString(200)
    private String  dedi05;//EDI相关信息05FALSEString(200)
    private String  dedi06;//EDI相关信息06FALSEString(200)
    private String  dedi07;//EDI相关信息07FALSEString(200)
    private String  dedi08;//EDI相关信息08FALSEString(200)
    private String  dedi09;//EDI相关信息09FALSEdecimal(18, 8)
    private String  dedi10;//EDI相关信息10FALSEdecimal(18, 8)
    private String  dedi11;//EDI相关信息11FALSEString(200)
    private String  dedi12;//EDI相关信息12FALSEString(200)
    private String  dedi13;//EDI相关信息13FALSEString(200)
    private String  dedi14;//EDI相关信息14FALSEString(200)
    private String  dedi15;//EDI相关信息15FALSEString(200)
    private String  dedi16;//EDI相关信息16FALSEString(200)
    private String  userDefine1;//用户自定义1 FALSEString(200)
    private String  userDefine2;//用户自定义2FALSEString(200)
    private String  userDefine3;//用户自定义3FALSEString(200)
    private String  userDefine4;//用户自定义4FALSEString(200)
    private String  userDefine5;//用户自定义5FALSEString(200)
    private String  userDefine6;//用户自定义6FALSEString(200)
    private String  notes;//备注FALSEtext
    private String  totalNetWeight;//型号的净重

    public String getTotalNetWeight() {
        return totalNetWeight;
    }

    public void setTotalNetWeight(String totalNetWeight) {
        this.totalNetWeight = totalNetWeight;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public void setExpectedQty(String expectedQty) {
        this.expectedQty = expectedQty;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getExpectedQty() {
        return expectedQty;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getLotAtt01() {
        return lotAtt01;
    }

    public void setLotAtt01(String lotAtt01) {
        this.lotAtt01 = lotAtt01;
    }

    public String getLotAtt02() {
        return lotAtt02;
    }

    public void setLotAtt02(String lotAtt02) {
        this.lotAtt02 = lotAtt02;
    }

    public String getLotAtt03() {
        return lotAtt03;
    }

    public void setLotAtt03(String lotAtt03) {
        this.lotAtt03 = lotAtt03;
    }

    public String getLotAtt04() {
        return lotAtt04;
    }

    public void setLotAtt04(String lotAtt04) {
        this.lotAtt04 = lotAtt04;
    }

    public String getLotAtt05() {
        return lotAtt05;
    }

    public void setLotAtt05(String lotAtt05) {
        this.lotAtt05 = lotAtt05;
    }

    public String getLotAtt06() {
        return lotAtt06;
    }

    public void setLotAtt06(String lotAtt06) {
        this.lotAtt06 = lotAtt06;
    }

    public String getLotAtt07() {
        return lotAtt07;
    }

    public void setLotAtt07(String lotAtt07) {
        this.lotAtt07 = lotAtt07;
    }

    public String getLotAtt08() {
        return lotAtt08;
    }

    public void setLotAtt08(String lotAtt08) {
        this.lotAtt08 = lotAtt08;
    }

    public String getLotAtt09() {
        return lotAtt09;
    }

    public void setLotAtt09(String lotAtt09) {
        this.lotAtt09 = lotAtt09;
    }

    public String getLotAtt10() {
        return lotAtt10;
    }

    public void setLotAtt10(String lotAtt10) {
        this.lotAtt10 = lotAtt10;
    }

    public String getLotAtt11() {
        return lotAtt11;
    }

    public void setLotAtt11(String lotAtt11) {
        this.lotAtt11 = lotAtt11;
    }

    public String getLotAtt12() {
        return lotAtt12;
    }

    public void setLotAtt12(String lotAtt12) {
        this.lotAtt12 = lotAtt12;
    }

    public String getLotAtt13() {
        return lotAtt13;
    }

    public void setLotAtt13(String lotAtt13) {
        this.lotAtt13 = lotAtt13;
    }

    public String getLotAtt14() {
        return lotAtt14;
    }

    public void setLotAtt14(String lotAtt14) {
        this.lotAtt14 = lotAtt14;
    }

    public String getLotAtt15() {
        return lotAtt15;
    }

    public void setLotAtt15(String lotAtt15) {
        this.lotAtt15 = lotAtt15;
    }

    public String getLotAtt16() {
        return lotAtt16;
    }

    public void setLotAtt16(String lotAtt16) {
        this.lotAtt16 = lotAtt16;
    }

    public String getLotAtt17() {
        return lotAtt17;
    }

    public void setLotAtt17(String lotAtt17) {
        this.lotAtt17 = lotAtt17;
    }

    public String getLotAtt18() {
        return lotAtt18;
    }

    public void setLotAtt18(String lotAtt18) {
        this.lotAtt18 = lotAtt18;
    }

    public String getLotAtt19() {
        return lotAtt19;
    }

    public void setLotAtt19(String lotAtt19) {
        this.lotAtt19 = lotAtt19;
    }

    public String getLotAtt20() {
        return lotAtt20;
    }

    public void setLotAtt20(String lotAtt20) {
        this.lotAtt20 = lotAtt20;
    }

    public String getLotAtt21() {
        return lotAtt21;
    }

    public void setLotAtt21(String lotAtt21) {
        this.lotAtt21 = lotAtt21;
    }

    public String getLotAtt22() {
        return lotAtt22;
    }

    public void setLotAtt22(String lotAtt22) {
        this.lotAtt22 = lotAtt22;
    }

    public String getLotAtt23() {
        return lotAtt23;
    }

    public void setLotAtt23(String lotAtt23) {
        this.lotAtt23 = lotAtt23;
    }

    public String getLotAtt24() {
        return lotAtt24;
    }

    public void setLotAtt24(String lotAtt24) {
        this.lotAtt24 = lotAtt24;
    }

    public String getDedi04() {
        return dedi04;
    }

    public void setDedi04(String dedi04) {
        this.dedi04 = dedi04;
    }

    public String getDedi05() {
        return dedi05;
    }

    public void setDedi05(String dedi05) {
        this.dedi05 = dedi05;
    }

    public String getDedi06() {
        return dedi06;
    }

    public void setDedi06(String dedi06) {
        this.dedi06 = dedi06;
    }

    public String getDedi07() {
        return dedi07;
    }

    public void setDedi07(String dedi07) {
        this.dedi07 = dedi07;
    }

    public String getDedi08() {
        return dedi08;
    }

    public void setDedi08(String dedi08) {
        this.dedi08 = dedi08;
    }

    public String getDedi09() {
        return dedi09;
    }

    public void setDedi09(String dedi09) {
        this.dedi09 = dedi09;
    }

    public String getDedi10() {
        return dedi10;
    }

    public void setDedi10(String dedi10) {
        this.dedi10 = dedi10;
    }

    public String getDedi11() {
        return dedi11;
    }

    public void setDedi11(String dedi11) {
        this.dedi11 = dedi11;
    }

    public String getDedi12() {
        return dedi12;
    }

    public void setDedi12(String dedi12) {
        this.dedi12 = dedi12;
    }

    public String getDedi13() {
        return dedi13;
    }

    public void setDedi13(String dedi13) {
        this.dedi13 = dedi13;
    }

    public String getDedi14() {
        return dedi14;
    }

    public void setDedi14(String dedi14) {
        this.dedi14 = dedi14;
    }

    public String getDedi15() {
        return dedi15;
    }

    public void setDedi15(String dedi15) {
        this.dedi15 = dedi15;
    }

    public String getDedi16() {
        return dedi16;
    }

    public void setDedi16(String dedi16) {
        this.dedi16 = dedi16;
    }

    public String getUserDefine1() {
        return userDefine1;
    }

    public void setUserDefine1(String userDefine1) {
        this.userDefine1 = userDefine1;
    }

    public String getUserDefine2() {
        return userDefine2;
    }

    public void setUserDefine2(String userDefine2) {
        this.userDefine2 = userDefine2;
    }

    public String getUserDefine3() {
        return userDefine3;
    }

    public void setUserDefine3(String userDefine3) {
        this.userDefine3 = userDefine3;
    }

    public String getUserDefine4() {
        return userDefine4;
    }

    public void setUserDefine4(String userDefine4) {
        this.userDefine4 = userDefine4;
    }

    public String getUserDefine5() {
        return userDefine5;
    }

    public void setUserDefine5(String userDefine5) {
        this.userDefine5 = userDefine5;
    }

    public String getUserDefine6() {
        return userDefine6;
    }

    public void setUserDefine6(String userDefine6) {
        this.userDefine6 = userDefine6;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
