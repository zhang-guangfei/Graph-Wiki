package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

/**
 * @Author lyc
 * @Date 2023/7/25 15:24
 * @Descripton TODO
 */
public enum OrderModifyTypeEnum {
    ddzd("A","订单转订"),
    zdckc("A1","转订出库存"),
    zdczt("A2","转订出在途"),
    bggys("A3","变更供应商"),
    bgpo("B","变更Po号"),
    cancel_order("C","删除订单"),
    zktz("C1","在库调整"),
    zlwt("C2","质量问题"),
    hq("C3","货期"),
    xmbg("C4","项目变更"),
    dsth("C5","对手替换"),
    xxcw("C6","选型错误"),
    fkyy("C7","付款原因"),
    xjkwt("C8","新旧款问题"),
    khddcw("C9","客户订单错误"),
    bgccz("D","变更指定工厂出厂日"),
    gcwfmzjhq("D1","工厂无法满足交货期"),
    khjhqbg("D2","客户交货期变更"),
    bgwlh("E","变更物料号"),
    bgkpfs("F","变更开票方式"),
    bgkplx("G","变更开票类型"),
    ddhy("H","订单还原"),
    bgkhxh("M","变更客户型号"),
    bgkhddh("O","变更客户订单号"),
    bgdj("P","变更单价"),
    xdcw("P1","下单错误"),
    yytj("P2","已有特价"),
    dzwt("P3","对账问题"),
    tjyf("P4","添加运费"),
    other_price("P9","其它"),
    bgysfs("T","变更采购运输方式"),
    other("T1","其它"),
    bg_end_user("U","变更最终用户");


    private String code;
    private String codeName;



    OrderModifyTypeEnum(String code, String codeName) {
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

    public static String getNameByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (OrderModifyTypeEnum item : OrderModifyTypeEnum.values() ) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;

    }

    public static OrderModifyTypeEnum toType(String value) {
        return Stream.of(OrderModifyTypeEnum.values())
                .filter(p -> p.code.equals(value))
                .findAny()
                .orElse(null);
    }
}
