package com.sales.ops.serviceimpl.dispatch.dodispatch.domain.goodsconfirm;

import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsPco;
import com.sales.ops.db.entity.OpsPcoItem;
import com.sales.ops.dto.flux.RoConfirmItem;
import com.sales.ops.dto.inventory.OpsPcoItemDto;
import com.sales.ops.enums.DoSourceEnum;
import com.sales.ops.enums.RoConfirmRecTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class ResultDto {

    private String invoiceNo;
    private String roId;
    private RoConfirmRecTypeEnum confirmType;

    private boolean db;
    private String doId;
    private String pcoId;

    private String orderId;
    private String orderItem;
    private Integer splitNo;
    private String modelno;

    private GoodsConfirmReason reason;


    public static ResultDto INZK(GoodsConfirmContextItem item) {
        return ResultDto.builder()
                .invoiceNo(item.getInvoiceNo())
                .roId(item.getRoId())
                .confirmType(RoConfirmRecTypeEnum.INZK)
                .db(false)
                .reason(item.getReason())
                .build();
    }


    public static ResultDto DBYY(GoodsConfirmContextItem item) {
        OpsDo opsDo = item.getDB();
        Integer splitNo = DoSourceEnum.isAssModel(opsDo.getDoSource()) ? opsDo.getAssNum() : opsDo.getNum();
        return ResultDto.builder()
                .invoiceNo(item.getInvoiceNo()).roId(item.getRoId()).confirmType(RoConfirmRecTypeEnum.INYY)
                .db(true).doId(item.getDBKey())
                .orderId(opsDo.getOrderId()).orderItem(opsDo.getOrderItem()).splitNo(splitNo)
                .reason(item.getReason())
                .build();
    }

    public static ResultDto DBYK(GoodsConfirmContextItem item) {
        OpsDo opsDo = item.getDB();
        Integer splitNo = DoSourceEnum.isAssModel(opsDo.getDoSource()) ? opsDo.getAssNum() : opsDo.getNum();
        return ResultDto.builder()
                .invoiceNo(item.getInvoiceNo()).roId(item.getRoId()).confirmType(RoConfirmRecTypeEnum.YK)
                .db(true).doId(item.getDBKey())
                .orderId(opsDo.getOrderId()).orderItem(opsDo.getOrderItem()).splitNo(splitNo)
                .reason(item.getReason())
                .build();
    }

    public static ResultDto DoYY(GoodsConfirmContextItem item) {
        OpsDo opsDo = item.getDo();
        return ResultDto.builder()
                .invoiceNo(item.getInvoiceNo()).roId(item.getRoId()).confirmType(RoConfirmRecTypeEnum.INYY)
                .db(false).doId(item.getDoKey())
                .orderId(opsDo.getOrderId()).orderItem(opsDo.getOrderItem()).splitNo(opsDo.getNum())
                .reason(item.getReason())
                .build();
    }

    public static ResultDto DoYK(GoodsConfirmContextItem item) {
        OpsDo opsDo = item.getDo();
        return ResultDto.builder()
                .invoiceNo(item.getInvoiceNo()).roId(item.getRoId()).confirmType(RoConfirmRecTypeEnum.YK)
                .db(false).doId(item.getDoKey())
                .orderId(opsDo.getOrderId()).orderItem(opsDo.getOrderItem()).splitNo(opsDo.getNum())
                .reason(item.getReason())
                .build();
    }

    public static ResultDto PcoYY(GoodsConfirmContextItem item) {
        OpsPcoItemDto pcoDto = item.getPcoDto();
        OpsPco opsPco = pcoDto.getOpsPco();
        OpsPcoItem pcoItem = pcoDto.getPcoItem();
        OpsDo jyck = pcoDto.getJyck();
        return ResultDto.builder()
                .invoiceNo(item.getInvoiceNo()).roId(item.getRoId()).confirmType(RoConfirmRecTypeEnum.PCOYY)
                .db(false).doId(jyck.getDoId()).pcoId(opsPco.getPcoId()).modelno(pcoItem.getSubModelno())
                .orderId(opsPco.getOrderId()).orderItem(opsPco.getOrderItem()).splitNo(pcoItem.getPcoItem())
                .reason(item.getReason())
                .build();
    }

    public static ResultDto PcoYK(GoodsConfirmContextItem item) {
        OpsPcoItemDto pcoDto = item.getPcoDto();
        OpsPco opsPco = pcoDto.getOpsPco();
        OpsPcoItem pcoItem = pcoDto.getPcoItem();
        OpsDo jyck = pcoDto.getJyck();
        return ResultDto.builder()
                .invoiceNo(item.getInvoiceNo()).roId(item.getRoId()).confirmType(RoConfirmRecTypeEnum.PCOYK)
                .db(false).doId(jyck.getDoId()).pcoId(opsPco.getPcoId()).modelno(pcoItem.getSubModelno())
                .orderId(opsPco.getOrderId()).orderItem(opsPco.getOrderItem()).splitNo(pcoItem.getPcoItem())
                .reason(item.getReason())
                .build();
    }

    public RoConfirmItem toReturnDto() {
        RoConfirmItem roConfirmItem = new RoConfirmItem();
        roConfirmItem.setInvoiceNo(this.invoiceNo);
        roConfirmItem.setReceiveType(this.confirmType.getType());
        roConfirmItem.setRoId(this.roId);
        roConfirmItem.setDoid(this.doId);
        roConfirmItem.setPcoid(this.pcoId);
        return roConfirmItem;
    }


    public boolean isDo() {
        return !db && doId != null && pcoId == null;
    }

    public boolean isPco() {
        return doId != null && pcoId != null;
    }

    public boolean isCross() {
        return Objects.equals(this.confirmType.getCode(), 2);
    }
}
